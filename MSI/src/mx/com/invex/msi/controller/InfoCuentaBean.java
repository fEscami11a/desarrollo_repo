package mx.com.invex.msi.controller;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.component.html.HtmlSelectOneListbox;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.rpc.ServiceException;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tsys.xmlmessaging.ch.ITRtranDetailResponseDataType;

import mx.com.interware.spira.ls.facade.igbinaenca.ArrayUDataDTO;
import mx.com.interware.spira.ls.facade.igbinaenca.InfoDemograficaINA;
import mx.com.interware.spira.ls.facade.igbinaenca.InfoEncabezado;
import mx.com.interware.spira.ls.facade.igbinaenca.InfoGeneralTHIGB;
import mx.com.interware.spira.ls.facade.igbinaenca.ResultadoIGBINAEnca;
import mx.com.interware.spira.tsysws.si02.SI02FinalResponseDTO;
import mx.com.interware.spira.web.si07.Si07VariableAreaDTO;
import mx.com.invex.msi.model.ArchivoDetalle;
import mx.com.invex.msi.model.Campania;
import mx.com.invex.msi.model.Comercio;
import mx.com.invex.msi.model.Compra;
import mx.com.invex.msi.model.Parametro;
import mx.com.invex.msi.model.Producto;
import mx.com.invex.msi.model.ProductoTs2;
import mx.com.invex.msi.model.Promocion;
import mx.com.invex.msi.service.ArchivoDetalleService;
import mx.com.invex.msi.service.BitacoraService;
import mx.com.invex.msi.service.CampaniaService;
import mx.com.invex.msi.service.CompraService;
import mx.com.invex.msi.service.ParametroService;
import mx.com.invex.msi.service.ProductoService;
import mx.com.invex.msi.service.ProductoTs2Service;
import mx.com.invex.msi.util.CryptoAES;
import mx.com.invex.msi.util.MSIConstants;
import mx.com.invex.msi.util.MSIException;
import mx.com.invex.msi.util.MSIHelper;
import mx.com.invex.msi.ws.ClientLSWS;
import mx.com.invex.msi.ws.ClientTS2;
import mx.com.invex.msi.ws.ClienteGetMovs;
import mx.com.invex.msi.ws.InfoCuentaDto;
@Component
@Scope("session")
public class InfoCuentaBean extends MessagesMBean implements Serializable{

	private static final Logger logger = Logger.getLogger(InfoCuentaBean.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	ArchivoDetalleService archivoDetalleService;

	@Autowired
	BitacoraService bitacoraService;

	@Autowired
	CompraService compraService;

	@Autowired
	CampaniaService campaniaService;

	@Autowired
	ParametroService parametroService;

	@Autowired
	ProductoService productoService;

	@Autowired
	ProductoTs2Service productoTs2Service;


	private HtmlSelectBooleanCheckbox chkSelect;

	private HtmlSelectOneListbox cmbPromos;

	private List<Compra> compras;

	private String cuenta;
	private String cuentaPU;

	private Double dispCompras;

	private InfoGeneralTHIGB infoCuenta;
	private InfoDemograficaINA infoDemo;

	private InfoEncabezado infoEnca;
	private Si07VariableAreaDTO[] movsITH;
	
	private String pagoNoIntereses;

	private boolean pantallaUnica;


	private boolean prog0;

	

	private double saldoNoIntereses;

	private double saldoRev;

	private double saldoRevAnterior;
	private  InfoCuentaDto info;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	

	private String tipoProd;

	public String buscarPorNoCuenta(String cuentaParam) throws MSIException{
		try {

			cuenta=cuentaParam;
			logger.info("buscarPorNoCuenta "+cuenta);

			if(productoTs2Service.cuentaITAU(cuenta)){

				HttpSession session = (HttpSession)  getContext().getExternalContext().getSession(false);
				session.setAttribute("cuenta", cuenta);
				ClientTS2 cts2= new ClientTS2();

				if(cts2.isCuentaBloqueada(cuenta)){
					sendErrorMessageToUser("La cuenta presenta un bloqueo ");
					return null;
				}

				info = cts2.getInfoCuenta(cuenta);
				info.setCuenta(cuenta);
				if(info.getPagoMinimo()== null){
					sendInfoMessageToUser("Su pago mínimo se verá reflejado hasta el siguiente corte.");
				}

				ProductoTs2 prodts2 = productoTs2Service.getProductoItau(cuenta, info.getTpc(), info.getCpc());
				if(prodts2!= null){
					tipoProd =prodts2.getProducto();
				}
				logger.info("tipo prod "+tipoProd);
				prog0=cts2.isCuentaProg0(cuenta);

				//DecimalFormat df2 = new DecimalFormat( "00" );
				//calcular corte anterior
				Calendar diaCorte = Calendar.getInstance();
				diaCorte.set(Calendar.DAY_OF_MONTH, info.getCycle());
				Date fechaFin = new Date();
				Date fechaIn = new Date();
				//Date fechaUltimoCorte = new Date();
				if(new GregorianCalendar().after(diaCorte)){
					fechaFin = diaCorte.getTime();

					diaCorte.add(Calendar.MONTH, -1);
					fechaIn = diaCorte.getTime();
				}else{
					diaCorte.add(Calendar.MONTH, -1);
					fechaFin = diaCorte.getTime();
					diaCorte.add(Calendar.MONTH, -1);
					fechaIn = diaCorte.getTime();
				}

				pagoNoIntereses = info.getPagoNoIntereses();
				//fechaUltimoCorte =fechaFin;
				Double montoComprasMes = compraService.getMontoComprasByDateRange(cuenta, fechaIn, fechaFin,"IPS");
				if(montoComprasMes!= null){
					pagoNoIntereses=""+(new Double(pagoNoIntereses)-montoComprasMes);
				}

				GregorianCalendar hoy = new GregorianCalendar();
				String hoyStr=sdf.format(hoy.getTime());
				hoy.setTime(sdf.parse(hoyStr));


				//trae campania compras internacionales para no pro cero
				

				compras = new ArrayList<Compra>();
				
				//inicio camps comercio
				GregorianCalendar  antes= null;
				List<Campania> campComercios = campaniaService.getCampaniaByParams(null,null, "comercio", null);
				//List<Compra> compras = new ArrayList<Compra>();
				List<Double> montos=null;

				Set<Campania> setCamps = new HashSet<Campania>();

				setCamps.addAll(campComercios);
				logger.info("camp comercios "+setCamps.size());
				List<ITRtranDetailResponseDataType> lmovs= cts2.getMovs(cuenta, true, null, null);
				for (Campania campania : setCamps) {
					montos= new ArrayList<Double>();
					
					antes = new GregorianCalendar();
					antes.add(Calendar.DAY_OF_YEAR, -campania.getNumMaxDiasRegistro());
					Set<Promocion> promosCamp= campania.getPromociones();
					logger.info("camp comercios promos "+promosCamp.size());
					for (Promocion promo : promosCamp) {
						if(promo.getMonto()!= null){
							logger.info("camp comercios promo monto "+promo.getMonto());
							montos.add(promo.getMonto());
						}

					}
					
					Set<Comercio> comercios=campania.getComercios();
					Collections.sort(montos);
					
				for (Comercio comercio : comercios) {

								List<Compra> comprasComer = new ArrayList<Compra>();
								for (ITRtranDetailResponseDataType td : lmovs) {
									
									if(td.getTranCode().equals(campania.getCodigoTransaccion1())){
										if(montos.get(0)<= td.getAmtTran().getValue().getValue().doubleValue()){
											if((td.getDateTran().getValue().getValue().toGregorianCalendar().after(antes) || td.getDateTran().getValue().getValue().toGregorianCalendar().compareTo(antes)==0)
													&& (td.getDateTran().getValue().getValue().toGregorianCalendar().before(hoy) || td.getDateTran().getValue().getValue().toGregorianCalendar().compareTo(hoy)==0)){
												
												if (td.getMerchantInfo().getValue()
														.getDBAName().getValue().contains(comercio.getNombre())) {
													Compra compra = new Compra();
													compra.setCuenta(cuenta);
													compra.setCodigoTransaccion(Integer.parseInt(td
															.getTranCode()));
													compra.setFechaCompra(td.getDateTran().getValue()
															.getValue().toGregorianCalendar().getTime());
													compra.setMonto(td.getAmtTran().getValue().getValue()
															.doubleValue());
													compra.setDescripcion(td.getMerchantInfo().getValue()
															.getDBAName().getValue());
													compra.setNumRefTran(td.getRefNbr().getValue());
													compra.setTipoTransaccion("ITA");
													compra.setIdEdoPromocion(compraService
															.getStatusCompraItau(compra));
													compra.setDateStmtBegin(td.getDateStmtBegin());
													compra.setDatePost(td.getDatePost());
													compra.setTimePost(td.getTimePost());
													if(td.getTLPType()!= null){
														if("S".equals(td.getTLPType().getValue())){
															compra.setIdEdoPromocion(MSIConstants.PROM_COMERCIO_APLICADO);
														}
													}
													for (Promocion promo : campania.getPromociones()) {

														if (compra.getMonto().doubleValue() >= promo
																.getMonto().doubleValue()) {
															compra.addComboPromo(promo.getPlazoMeses()
																	+ " " + promo.getDescripcion(), promo);
														}

													}
													comprasComer.add(compra);
												}

											}
										}
									}
								}//for compras corte actual


						XMLGregorianCalendar calendarIn=null;
								try {
									calendarIn = DatatypeFactory.newInstance().newXMLGregorianCalendar(antes);
								} catch (DatatypeConfigurationException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								XMLGregorianCalendar calendarFin=null;
								try {
									calendarFin = DatatypeFactory.newInstance().newXMLGregorianCalendar(hoy);
								} catch (DatatypeConfigurationException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								for (ITRtranDetailResponseDataType td : cts2.getMovs(cuenta, false, calendarIn, calendarFin)) {

									if(campania.getCodigoTransaccion1().equals(td.getTranCode())){
										if(montos.get(0)<= td.getAmtTran().getValue().getValue().doubleValue()){
											if((td.getDateTran().getValue().getValue().toGregorianCalendar().after(antes) || td.getDateTran().getValue().getValue().toGregorianCalendar().compareTo(antes)==0) 
													&& (td.getDateTran().getValue().getValue().toGregorianCalendar().before(hoy) || td.getDateTran().getValue().getValue().toGregorianCalendar().compareTo(hoy)==0)){

												if (td.getMerchantInfo().getValue()
														.getDBAName().getValue().contains(comercio.getNombre())) {
													Compra compra = new Compra();
													compra.setCuenta(cuenta);
													compra.setCodigoTransaccion(Integer.parseInt(td
															.getTranCode()));
													compra.setFechaCompra(td.getDateTran().getValue()
															.getValue().toGregorianCalendar().getTime());
													compra.setMonto(td.getAmtTran().getValue().getValue()
															.doubleValue());
													compra.setDescripcion(td.getMerchantInfo().getValue()
															.getDBAName().getValue());
													compra.setNumRefTran(td.getRefNbr().getValue());
													compra.setTipoTransaccion("IPS");
													compra.setIdEdoPromocion(compraService
															.getStatusCompraItau(compra));
													compra.setDateStmtBegin(td.getDateStmtBegin());
													compra.setDatePost(td.getDatePost());
													compra.setTimePost(td.getTimePost());
													if(td.getTLPType()!= null){
														if("S".equals(td.getTLPType().getValue())){
															compra.setIdEdoPromocion(MSIConstants.PROM_COMERCIO_APLICADO);
														}
													}
													for (Promocion promo : campania.getPromociones()) {

														if (compra.getMonto().doubleValue() >= promo
																.getMonto().doubleValue()) {
															compra.addComboPromo(promo.getPlazoMeses()
																	+ " " + promo.getDescripcion(), promo);
														}

													}
													comprasComer.add(compra);
												}

											}	 


										}

									}
								}//for compras corte anteriorr
								
								
								compras.addAll(comprasComer);
							
							}
				}
				//fin camps comercios
			
				//inicio camps Ext
				List<Campania> campExt=campaniaService.getCampaniaByTipo("campania.ext.ts2");

				Set<Promocion> promosCampExt=null;
				double montoMinCompras = 0;
				int numDiasCampExt=0;
				for (Campania campania : campExt) {

					numDiasCampExt=campania.getNumMaxDiasRegistro();

					promosCampExt= campania.getPromociones();


					for (Promocion promo : promosCampExt) {
						montoMinCompras = promo.getMonto();
						break;
					}

				}

				//obternr compras extr
				antes = new GregorianCalendar();
				antes.set(Calendar.HOUR_OF_DAY, 0);
				antes.set(Calendar.MINUTE, 0);
				antes.set(Calendar.SECOND, 0);
				antes.set(Calendar.MILLISECOND, 0);


				antes.add(Calendar.DAY_OF_YEAR, -numDiasCampExt);
				for (ITRtranDetailResponseDataType td : lmovs) {
					if("3001".equals(td.getTranCode()) || "1001".equals(td.getTranCode())){
						if(montoMinCompras<= td.getAmtTran().getValue().getValue().doubleValue()){
							if((td.getDateTran().getValue().getValue().toGregorianCalendar().after(antes) || td.getDateTran().getValue().getValue().toGregorianCalendar().compareTo(antes)==0) ){
								Compra compra = new Compra();
								compra.setCuenta(cuenta);
								compra.setCodigoTransaccion(Integer.parseInt(td.getTranCode()));
								compra.setFechaCompra(td.getDateTran().getValue().getValue().toGregorianCalendar().getTime());
								compra.setMonto(td.getAmtTran().getValue().getValue().doubleValue());
								compra.setDescripcion(td.getMerchantInfo().getValue().getDBAName().getValue());
								compra.setNumRefTran(td.getRefNbr().getValue());
								compra.setTipoTransaccion("ITA");
								compra.setIdEdoPromocion(compraService.getStatusCompraItau(compra));
								compra.setDateStmtBegin(td.getDateStmtBegin());
								compra.setDatePost(td.getDatePost());
								compra.setTimePost(td.getTimePost());
								if(td.getTLPType()!= null){
									if("S".equals(td.getTLPType().getValue())){
										compra.setIdEdoPromocion(MSIConstants.PROM_COMERCIO_APLICADO);
									}
								}
								for (Promocion promo : promosCampExt) {
									if("si".equalsIgnoreCase(promo.getProgramaCero())){
										if(prog0 && compra.getMonto().doubleValue()>= promo.getMonto().doubleValue()){
											compra.addComboPromo(promo.getPlazoMeses()+" "+promo.getDescripcion(), promo);
										}
									}else{
										if(compra.getMonto().doubleValue()>= promo.getMonto().doubleValue()){

											compra.addComboPromo(promo.getPlazoMeses()+" "+promo.getDescripcion(), promo);
										}
									}
								}
								compras.add(compra);

							}
						}
					}
				}//for compras corte actual


				XMLGregorianCalendar calendarIn = DatatypeFactory.newInstance().newXMLGregorianCalendar(antes);
				XMLGregorianCalendar calendarFin = DatatypeFactory.newInstance().newXMLGregorianCalendar(hoy);

				for (ITRtranDetailResponseDataType td : cts2.getMovs(cuenta, false, calendarIn, calendarFin)) {

					if("3001".equals(td.getTranCode()) || "1001".equals(td.getTranCode()) ){
						if(montoMinCompras<= td.getAmtTran().getValue().getValue().doubleValue()){
							if((td.getDateTran().getValue().getValue().toGregorianCalendar().after(antes) || td.getDateTran().getValue().getValue().toGregorianCalendar().compareTo(antes)==0) ){
								Compra compra = new Compra();
								compra.setCuenta(cuenta);
								compra.setCodigoTransaccion(Integer.parseInt(td.getTranCode()));
								compra.setFechaCompra(td.getDateTran().getValue().getValue().toGregorianCalendar().getTime());
								compra.setMonto(td.getAmtTran().getValue().getValue().doubleValue());
								compra.setDescripcion(td.getMerchantInfo().getValue().getDBAName().getValue());
								compra.setNumRefTran(td.getRefNbr().getValue());
								compra.setTipoTransaccion("IPS");
								compra.setIdEdoPromocion(compraService.getStatusCompraItau(compra));
								compra.setDateStmtBegin(td.getDateStmtBegin());
								compra.setDatePost(td.getDatePost());
								compra.setTimePost(td.getTimePost());
								if(td.getTLPType()!= null){
									if("S".equals(td.getTLPType().getValue())){
										compra.setIdEdoPromocion(MSIConstants.PROM_COMERCIO_APLICADO);
									}
								}
								for (Promocion promo : promosCampExt) {

									if(compra.getMonto().doubleValue()>= promo.getMonto().doubleValue()){
										if("si".equalsIgnoreCase(promo.getProgramaCero())){
											if(prog0 && compra.getMonto().doubleValue()>= promo.getMonto().doubleValue()){
												compra.addComboPromo(promo.getPlazoMeses()+" "+promo.getDescripcion(), promo);
											}
										}else{

											compra.addComboPromo(promo.getPlazoMeses()+" "+promo.getDescripcion(), promo);
										}


									}
								}
								compras.add(compra);

							}
						}

					}
				}//for compras corte anteriorr

				//fin compras Ext



				Date diahoy = new Date();
				List<Campania> lcampMasivas=campaniaService.getCampaniaProdTs2(diahoy,diahoy,"masiva",prodts2);
				if(lcampMasivas!= null && !lcampMasivas.isEmpty()){
					logger.info("tamMasivas "+lcampMasivas.size());
					Set<Campania>  setMasivas = new HashSet<Campania>(lcampMasivas);
					logger.info("setMasivas "+setMasivas.size());
					lcampMasivas = new ArrayList<Campania>(setMasivas);
				}

				 montos=new ArrayList<Double>();
				for (Campania camp : lcampMasivas) {
					Set<Promocion> setPromos=camp.getPromociones();
					for (Promocion promocion : setPromos) {
						montos.add(promocion.getMonto());
					}
				}
				Collections.sort(montos);

				if(prog0){
					//obtener cmapaña prog 0 compras nacionales
					List<Campania> campProgCero=campaniaService.getCampaniaByTipo("campania.prog0.ts2");
					Set<Promocion> promosProg0=null;
					montoMinCompras = 0;

					montos=new ArrayList<Double>();
					int numDiasProg0 = 0;
					for (Campania campania1 : campProgCero) {

						numDiasProg0=campania1.getNumMaxDiasRegistro();

						promosProg0= campania1.getPromociones();


						for (Promocion promo : promosProg0) {
							montos.add(promo.getMonto());
							//montoMinCompras = promo.getMonto();
							//break;
						}
						if (!montos.isEmpty()) {
							Collections.sort(montos);
							for (Promocion promo : promosProg0) {

								montoMinCompras = promo.getMonto();
								break;
							}
						}

					}

					//obtener compras nacionales prog 0
					antes = new GregorianCalendar();
					antes.set(Calendar.HOUR_OF_DAY, 0);
					antes.set(Calendar.MINUTE, 0);
					antes.set(Calendar.SECOND, 0);
					antes.set(Calendar.MILLISECOND, 0);
					antes.add(Calendar.DAY_OF_YEAR, -numDiasProg0);

					for (ITRtranDetailResponseDataType td : lmovs) {
						if("7146".equals(td.getTranCode())){
							if(montoMinCompras<= td.getAmtTran().getValue().getValue().doubleValue()){
								if((td.getDateTran().getValue().getValue().toGregorianCalendar().after(antes) || td.getDateTran().getValue().getValue().toGregorianCalendar().compareTo(antes)==0) ){
									Compra compra = new Compra();
									compra.setCuenta(cuenta);
									compra.setCodigoTransaccion(Integer.parseInt(td.getTranCode()));
									compra.setFechaCompra(td.getDateTran().getValue().getValue().toGregorianCalendar().getTime());
									compra.setMonto(td.getAmtTran().getValue().getValue().doubleValue());
									compra.setDescripcion(td.getMerchantInfo().getValue().getDBAName().getValue());
									compra.setNumRefTran(td.getRefNbr().getValue());
									compra.setTipoTransaccion("ITA");
									compra.setIdEdoPromocion(compraService.getStatusCompraItau(compra));
									compra.setDateStmtBegin(td.getDateStmtBegin());
									compra.setDatePost(td.getDatePost());
									compra.setTimePost(td.getTimePost());
									if(td.getTLPType()!= null){
										if("S".equals(td.getTLPType().getValue())){
											compra.setIdEdoPromocion(MSIConstants.PROM_COMERCIO_APLICADO);
										}
									}
									for (Promocion promo : promosProg0) {

										if(compra.getMonto().doubleValue()>= promo.getMonto().doubleValue()){

											compra.addComboPromo(promo.getPlazoMeses()+" "+promo.getDescripcion(), promo);

											//ver masivas
											if(lcampMasivas!= null && !lcampMasivas.isEmpty()){
												//si hay cmap masivas actuales com promo pra prog 0 hay que agragarlas a las compras
												for (Campania camp : lcampMasivas) {
													Calendar calFechaIn = Calendar.getInstance();
													calFechaIn.setTime(camp.getFechaInicial());
													Calendar calFechaFin = Calendar.getInstance();
													calFechaFin.setTime(camp.getFechaFinal());
													Calendar calFechaCompra = td.getDateTran().getValue().getValue().toGregorianCalendar();
													if(calFechaCompra.compareTo(calFechaIn)==0 || calFechaCompra.compareTo(calFechaFin)==0||(calFechaCompra.after(calFechaIn) && calFechaCompra.before(calFechaFin))){
														for(Promocion promoMas:camp.getPromociones()){
															if("si".equalsIgnoreCase(promoMas.getProgramaCero())){
																if(compra.getMonto().doubleValue()>= promoMas.getMonto().doubleValue()){
																	compra.addComboPromo(promoMas.getPlazoMeses()+" "+promoMas.getDescripcion(), promoMas);
																}
															}
														}

													}
												}
											}
											compras.add(compra);
										}
									}


								}
							}
						}
					}//for compras corte actual


					for (ITRtranDetailResponseDataType td : cts2.getMovs(cuenta, false, calendarIn, calendarFin)) {
						
						if("7146".equals(td.getTranCode())){
							if(montoMinCompras<= td.getAmtTran().getValue().getValue().doubleValue()){
								if((td.getDateTran().getValue().getValue().toGregorianCalendar().after(antes) || td.getDateTran().getValue().getValue().toGregorianCalendar().compareTo(antes)==0) ){

									Compra compra = new Compra();
									compra.setCuenta(cuenta);
									compra.setCodigoTransaccion(Integer.parseInt(td.getTranCode()));
									compra.setFechaCompra(td.getDateTran().getValue().getValue().toGregorianCalendar().getTime());
									compra.setMonto(td.getAmtTran().getValue().getValue().doubleValue());
									compra.setDescripcion(td.getMerchantInfo().getValue().getDBAName().getValue());
									compra.setNumRefTran(td.getRefNbr().getValue());
									compra.setTipoTransaccion("IPS");
									compra.setIdEdoPromocion(compraService.getStatusCompraItau(compra));
									compra.setDateStmtBegin(td.getDateStmtBegin());
									compra.setDatePost(td.getDatePost());
									compra.setTimePost(td.getTimePost());
									if(td.getTLPType()!= null){
										if("S".equals(td.getTLPType().getValue())){
											compra.setIdEdoPromocion(MSIConstants.PROM_COMERCIO_APLICADO);
										}
									}
									for (Promocion promo : promosProg0) {

										if(compra.getMonto().doubleValue()>= promo.getMonto().doubleValue()){


											compra.addComboPromo(promo.getPlazoMeses()+" "+promo.getDescripcion(), promo);
											//ver masivas
											if(lcampMasivas!= null && !lcampMasivas.isEmpty()){
												//si hay cmap masivas actuales com promo pra prog 0 hay que agragarlas a las compras
												for (Campania camp : lcampMasivas) {
													Calendar calFechaIn = Calendar.getInstance();
													calFechaIn.setTime(camp.getFechaInicial());
													Calendar calFechaFin = Calendar.getInstance();
													calFechaFin.setTime(camp.getFechaFinal());
													Calendar calFechaCompra = td.getDateTran().getValue().getValue().toGregorianCalendar();
													if(calFechaCompra.compareTo(calFechaIn)==0 || calFechaCompra.compareTo(calFechaFin)==0||(calFechaCompra.after(calFechaIn) && calFechaCompra.before(calFechaFin))){
														for(Promocion promoMas:camp.getPromociones()){
															if("si".equalsIgnoreCase(promoMas.getProgramaCero())){
																if(compra.getMonto().doubleValue()>= promoMas.getMonto().doubleValue()){
																	compra.addComboPromo(promoMas.getPlazoMeses()+" "+promoMas.getDescripcion(), promoMas);
																}
															}
														}

													}
												}
											}
											compras.add(compra);
										}
									}

								}
							}

						}
					}//for compras corte anteriorr



				}//if prog cero

				else{



					//obtener compras ita nacionales 
					for (Campania camp : lcampMasivas) {
						logger.info("camp activa "+camp.getNombre());
						fechaIn= camp.getFechaInicial();
						fechaFin= camp.getFechaFinal();
						Calendar calIn= Calendar.getInstance();
						calIn.setTime(fechaIn);
						Calendar calFin = Calendar.getInstance();
						calFin.setTime(fechaFin);

						montos.clear();
						Set<Promocion> promos = camp.getPromociones();
						for (Promocion promo : promos) {
							montos.add(promo.getMonto());
							//montoMinCompras = promo.getMonto();
							//break;
						}
						if (!montos.isEmpty()) {
							Collections.sort(montos);
							for (Promocion promo : promos) {

								montoMinCompras = promo.getMonto();
								break;
							}
						}


						for (ITRtranDetailResponseDataType td : lmovs) {
							

							if("7146".equals(td.getTranCode())){
								Compra compra = new Compra();
								compra.setCuenta(cuenta);
								compra.setCodigoTransaccion(Integer.parseInt(td.getTranCode()));
								compra.setFechaCompra(td.getDateTran().getValue().getValue().toGregorianCalendar().getTime());
								compra.setMonto(td.getAmtTran().getValue().getValue().doubleValue());
								compra.setDescripcion(td.getMerchantInfo().getValue().getDBAName().getValue());
								compra.setNumRefTran(td.getRefNbr().getValue());
								compra.setTipoTransaccion("ITA");
								compra.setIdEdoPromocion(compraService.getStatusCompraItau(compra));
								compra.setDateStmtBegin(td.getDateStmtBegin());
								compra.setDatePost(td.getDatePost());
								compra.setTimePost(td.getTimePost());
								if(td.getTLPType()!= null){
									if("S".equals(td.getTLPType().getValue())){
										compra.setIdEdoPromocion(MSIConstants.PROM_COMERCIO_APLICADO);
									}
								}
								for (Campania cam : lcampMasivas) {
									//obtener rango fecha
									fechaIn= cam.getFechaInicial();
									fechaFin= cam.getFechaFinal();
									Calendar calIn2= Calendar.getInstance();
									calIn2.setTime(fechaIn);
									Calendar calFin2 = Calendar.getInstance();
									calFin2.setTime(fechaFin);
									//ver si esta en rango
									if(compra.getMonto().doubleValue()>= montoMinCompras && (td.getDateTran().getValue().getValue().toGregorianCalendar().after(calIn2) || td.getDateTran().getValue().getValue().toGregorianCalendar().compareTo(calIn2)==0) 
											&& (td.getDateTran().getValue().getValue().toGregorianCalendar().before(calFin2) || td.getDateTran().getValue().getValue().toGregorianCalendar().compareTo(calFin2)==0)){
										//obtener promos
										for (Promocion promo : cam.getPromociones()) {
											if("no".equalsIgnoreCase(promo.getProgramaCero()) && compra.getMonto().doubleValue()>= promo.getMonto().doubleValue()){
												compra.addComboPromo(promo.getPlazoMeses()+" "+promo.getDescripcion(), promo);
											}
										}
										compras.add(compra);
									}

								}


							}


						}


						XMLGregorianCalendar calendarIn2 = DatatypeFactory.newInstance().newXMLGregorianCalendar();
						calendarIn2.setYear(calIn.get(Calendar.YEAR));
						calendarIn2.setMonth(calIn.get(Calendar.MONTH)+1);

						XMLGregorianCalendar calendarFin2 = DatatypeFactory.newInstance().newXMLGregorianCalendar();
						calendarFin2.setYear(calFin.get(Calendar.YEAR));
						calendarFin2.setMonth(calFin.get(Calendar.MONTH)+1);

						//obtener nacionales ips
						for (ITRtranDetailResponseDataType td : cts2.getMovs(cuenta, false, calendarIn2, calendarFin2)) {
							
							if("7146".equals(td.getTranCode())){
								Compra compra = new Compra();
								compra.setCuenta(cuenta);
								compra.setCodigoTransaccion(Integer.parseInt(td.getTranCode()));
								compra.setFechaCompra(td.getDateTran().getValue().getValue().toGregorianCalendar().getTime());
								compra.setMonto(td.getAmtTran().getValue().getValue().doubleValue());
								compra.setDescripcion(td.getMerchantInfo().getValue().getDBAName().getValue());
								compra.setNumRefTran(td.getRefNbr().getValue());
								compra.setTipoTransaccion("IPS");
								compra.setIdEdoPromocion(compraService.getStatusCompraItau(compra));
								compra.setDateStmtBegin(td.getDateStmtBegin());
								compra.setDatePost(td.getDatePost());
								compra.setTimePost(td.getTimePost());
								if(td.getTLPType()!= null){
									if("S".equals(td.getTLPType().getValue())){
										compra.setIdEdoPromocion(MSIConstants.PROM_COMERCIO_APLICADO);
									}
								}
								for (Campania cam : lcampMasivas) {
									//obtener rango fecha
									fechaIn= cam.getFechaInicial();
									fechaFin= cam.getFechaFinal();
									Calendar calIn2= Calendar.getInstance();
									calIn2.setTime(fechaIn);
									Calendar calFin2 = Calendar.getInstance();
									calFin2.setTime(fechaFin);
									//ver si esta en rango
									if(compra.getMonto().doubleValue()>= montoMinCompras && (td.getDateTran().getValue().getValue().toGregorianCalendar().after(calIn2) || td.getDateTran().getValue().getValue().toGregorianCalendar().compareTo(calIn2)==0) 
											&& (td.getDateTran().getValue().getValue().toGregorianCalendar().before(calFin2) || td.getDateTran().getValue().getValue().toGregorianCalendar().compareTo(calFin2)==0)){
										//obtener promos
										for (Promocion promo : cam.getPromociones()) {
											if("no".equalsIgnoreCase(promo.getProgramaCero()) && compra.getMonto().doubleValue()>= promo.getMonto().doubleValue()){
												compra.addComboPromo(promo.getPlazoMeses()+" "+promo.getDescripcion(), promo);
											}
										}
										compras.add(compra);
									}

								}


							}


						}



					}
				}
				//se quitan compras repetidas con el set
				Set<Compra> setCompras = new HashSet<Compra>();
				setCompras.addAll(compras);
				compras= new ArrayList<Compra>();
				compras.addAll(setCompras);
				logger.info("compras "+compras.size());
				for (Compra compra : compras) {
					logger.info(compra.getCodigoTransaccion()+""+compra.getFechaCompra()+ " "+compra.getMonto()+ " "+compra.getMonto());
				}

				return "ClienteInfoMovs2";
			}else{
				Parametro param= parametroService.getParamById(MSIConstants.TSYSWS_ENDPOINT);
				logger.info("parma obtenido "+param.getDescripcion());
				ClientLSWS cliLSWS = new ClientLSWS(param.getValor());
				ResultadoIGBINAEnca infoCliente= null;


				try {
					infoCliente= cliLSWS.getResultadoIGBINAEnca(cuenta, MSIConstants.TSYSWS_SECURITY_USERNAME,MSIConstants.TSYSWS_ACCESS_KEY);
				} catch (Exception e) {
					sendErrorMessageToUser("Error al obtener informacion de la cuenta :"+ e.getMessage());
					e.printStackTrace();
					return null;
				}


				if(infoCliente == null){
					sendErrorMessageToUser("No se encontraron datos con la cuenta dada");
					return null;
				}
				infoDemo= infoCliente.getInfoDemograficaINA();
				infoCuenta= infoCliente.getInfoGeneralTHIGB();
				if(!infoCuenta.getTipoBloqueo().trim().isEmpty() 
						&& !("MX".equalsIgnoreCase(infoCuenta.getTipoBloqueo().trim()) || "TV".equalsIgnoreCase(infoCuenta.getTipoBloqueo().trim()) || "V5".equalsIgnoreCase(infoCuenta.getTipoBloqueo().trim()) )){
					logger.info("La cuenta tiene Credit raiting "+infoCuenta.getTipoBloqueo());
					sendErrorMessageToUser("La cuenta tiene Credit raiting "+infoCuenta.getTipoBloqueo());
					return null;
				}

				String tipoCuenta =infoCliente.getInfoGeneralTHIGB().getTipoCuenta();		
				if("SCA".equalsIgnoreCase(tipoCuenta.trim())){
					sendErrorMessageToUser("Las cuentas adicionales no estan permitidas");
					logger.info("La cuenta es SCA");
					return null;
				}

				String cuentaCBA= null;

				if(!"ICA".equalsIgnoreCase(tipoCuenta.trim())){


					if("PCA".equalsIgnoreCase(tipoCuenta.trim())){
						//usar los datos de la cba
						cuentaCBA=infoCliente.getInfoGeneralTHIGB().getCuentaCBA();
						infoCliente= cliLSWS.getResultadoIGBINAEnca(cuentaCBA, MSIConstants.TSYSWS_SECURITY_USERNAME,MSIConstants.TSYSWS_ACCESS_KEY);

						this.infoDemo = infoCliente.getInfoDemograficaINA();
						this.infoCuenta= infoCliente.getInfoGeneralTHIGB();

					}else if("CBA".equalsIgnoreCase(tipoCuenta.trim())){
						logger.info("usaron cuenta CBA!!!!");
						cuentaCBA =cuenta;
						//obtiene la pca
						cuenta = infoCliente.getInfoGeneralTHIGB().getCuentaCBA();


					}

				}else{
					cuentaCBA=cuenta;
				}


				cliLSWS.setEndPoint(parametroService.getParamById(MSIConstants.TSYSWS_SI02_ENDPOINT).getValor());
				saldoRev=0;
				saldoRevAnterior=0;
				infoEnca = infoCliente.getInfoEncabezado();
				SI02FinalResponseDTO respSI02 = cliLSWS.obtenerSaldoRevolvente(cuentaCBA);
				if(respSI02.getResponseDTO1().getCURR_PUR_BAL_OS()!= null && !respSI02.getResponseDTO1().getCURR_PUR_BAL_OS().isEmpty())
					saldoRev = new Double(respSI02.getResponseDTO1().getCURR_PUR_BAL_OS());

				if(respSI02.getResponseDTO1().getONE_CYC_OLDPUR_BAL_OS()!= null  && !respSI02.getResponseDTO1().getONE_CYC_OLDPUR_BAL_OS().isEmpty())
					saldoRevAnterior = new Double(respSI02.getResponseDTO1().getONE_CYC_OLDPUR_BAL_OS());

				String diasAgregados = respSI02.getResponseDTO1().getAGGR_NUM_DAYS_BILL();
				if("0".equals(diasAgregados)){
					saldoNoIntereses=Double.parseDouble(infoCliente.getInfoEncabezado().getSaldoNoIntereses());
				}else{

					if(respSI02.getResponseDTO1().getCUR_PUR_FIN_CHG_OS()!= null && !respSI02.getResponseDTO1().getCUR_PUR_FIN_CHG_OS().isEmpty()){
						saldoNoIntereses+= new Double(respSI02.getResponseDTO1().getCUR_PUR_FIN_CHG_OS());
					}
					if(respSI02.getResponseDTO1().getONE_CYC_OLDPUR_BAL_OS()!=null && !respSI02.getResponseDTO1().getONE_CYC_OLDPUR_BAL_OS().isEmpty()){
						saldoNoIntereses+= new Double(respSI02.getResponseDTO1().getONE_CYC_OLDPUR_BAL_OS());
					}
					if(respSI02.getResponseDTO1().getTWO_CYC_OLDPUR_BAL_OS()!= null && !respSI02.getResponseDTO1().getTWO_CYC_OLDPUR_BAL_OS().isEmpty()){
						saldoNoIntereses+= new Double(respSI02.getResponseDTO1().getTWO_CYC_OLDPUR_BAL_OS());
					}
					if(respSI02.getResponseDTO1().getONE_CYC_OLD_CA_BAL_OS()!=null && !respSI02.getResponseDTO1().getONE_CYC_OLD_CA_BAL_OS().isEmpty()){
						saldoNoIntereses+= new Double(respSI02.getResponseDTO1().getONE_CYC_OLD_CA_BAL_OS());
					}
					if(respSI02.getResponseDTO1().getCURR_CA_FIN_CHG_OS()!= null && !respSI02.getResponseDTO1().getCURR_CA_FIN_CHG_OS().isEmpty()){
						saldoNoIntereses+= new Double(respSI02.getResponseDTO1().getCURR_CA_FIN_CHG_OS());
					}

					if(respSI02.getResponseDTO1().getTWO_CYC_OLD_CA_BAL_OS()!=null && !respSI02.getResponseDTO1().getTWO_CYC_OLD_CA_BAL_OS().isEmpty()){
						saldoNoIntereses+= new Double(respSI02.getResponseDTO1().getTWO_CYC_OLD_CA_BAL_OS());
					}
				}


				ArrayUDataDTO[] arrUData=infoCuenta.getUData();
				//5.- validar si el cliente es programa cero
				String ucode3= arrUData[2].getToString();
				logger.info("ucode 3 (prog 0)? "+ucode3);

				if(ucode3 != null 
						&& ucode3.length()>0 ){

					if( 'G'==ucode3.charAt(3)){

						prog0=true;
					}else{

						prog0=false;
					}


				}
				logger.info("prog0? "+prog0);



				logger.info("saldoNoIntereses "+saldoNoIntereses);
				//calcular corte anterior
				Calendar diaCorte = new GregorianCalendar();
				diaCorte.set(Calendar.DAY_OF_MONTH, Integer.parseInt(infoCuenta.getDiaCorte()));
				if("0".equals(infoEnca.getFechaLimPago())){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					if(diaCorte.before(new GregorianCalendar())){
						Calendar siguienteCorte = new GregorianCalendar();
						siguienteCorte.add(Calendar.MONTH, 1);
						siguienteCorte.set(Calendar.DAY_OF_MONTH, Integer.parseInt(infoCuenta.getDiaCorte()));
						infoEnca.setFechaLimPago(sdf.format(siguienteCorte.getTime()));
					}else{

						infoEnca.setFechaLimPago(sdf.format(diaCorte.getTime()));
					}
				}


				DetachedCriteria criteria = DetachedCriteria.forClass(Compra.class);

				criteria.add(Restrictions.eq("cuenta", cuenta));

				Calendar calendar = Calendar.getInstance();
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.MILLISECOND, 0);
				Date fromDate = calendar.getTime();
				criteria.add(Restrictions.ge("fechaAplicacionPromocion",fromDate));
				compras =compraService.findByCriteria(criteria);
				Double montoComprasHoyITA=0.0;
				Double montoComprasHoyIPS=0.0;
				for (Compra compra : compras) {
					if("ITA".equals(compra.getTipoTransaccion())){
						montoComprasHoyITA+=compra.getMonto();
					}else if("IPS".equals(compra.getTipoTransaccion())){
						montoComprasHoyIPS+=compra.getMonto();
					}
				}
				logger.info("monto compras hoy ita "+montoComprasHoyITA);
				logger.info("monto compras hoy ips "+montoComprasHoyIPS);
				if(montoComprasHoyITA!= null){
					saldoRev-=montoComprasHoyITA;
				}


				if(montoComprasHoyIPS!= null){
					saldoNoIntereses -=montoComprasHoyIPS;
					saldoRevAnterior-=montoComprasHoyIPS;
				}

				if(infoEnca.getPagoMinimo()!=null && !infoEnca.getPagoMinimo().isEmpty()){
					Double pagoMin = Double.parseDouble(infoEnca.getPagoMinimo());
					if(saldoNoIntereses < pagoMin.doubleValue()){
						saldoNoIntereses = pagoMin;
					}
				}

				if(saldoRev <0){
					saldoRev=0;
				}
				if(saldoRevAnterior <0){
					saldoRevAnterior=0;
				}

				ClienteGetMovs movclient = new ClienteGetMovs(parametroService.getParamById(MSIConstants.SERVICIOS_MSIMOVS_ENDPOINT).getValor());
				logger.info("prams ucode3 "+ucode3.charAt(0)+"%  bin " +cuenta.substring(0, 6)+"%");
				Producto producto = productoService.getProductoCuenta(cuentaCBA, ucode3);

				if(producto!= null){
					tipoProd = producto.getCampania();
				}
				logger.info("producto "+tipoProd);
				List<Compra> comprasExt =comprasExt(cuenta,cuentaCBA,movclient,prog0);
				List<Compra> comprasCamSeg= comprasCampSeg(cuenta, cuentaCBA, movclient);
				List<Compra> comprasComercios = comprasCampComercios(cuenta, cuentaCBA, movclient,false);
				if(comprasComercios!=null){
					logger.info("compras comercio "+comprasComercios.size());
				}else{
					logger.info("no hay compras comercios");
				}
				Date diahoy = new Date();
				logger.info("obtener masivas "+ producto.getId());
				List<Campania> lcampMasivas=campaniaService.getCampaniaByParams(diahoy,diahoy,"masiva",producto);
				if(lcampMasivas!= null && !lcampMasivas.isEmpty()){
					logger.info("tamMasivas "+lcampMasivas.size());
					Set<Campania>  setMasivas = new HashSet<Campania>(lcampMasivas);
					logger.info("setMasivas "+setMasivas.size());
					lcampMasivas = new ArrayList<Campania>(setMasivas);
				}
				logger.info("masivas obtenidas "+lcampMasivas.size());
				List<Compra> comprasNacionales=null;
				if(prog0){
					comprasNacionales = comprasNacionalesProg0(cuenta, cuentaCBA, movclient,lcampMasivas);

				}else{
					comprasNacionales =comprasNalCampMasivas(cuenta, cuentaCBA, movclient, lcampMasivas);
				}

				Set<Compra> setCompras = new HashSet<Compra>();
				setCompras.addAll(comprasCamSeg);
				setCompras.addAll(comprasComercios);
				setCompras.addAll(comprasExt);
				setCompras.addAll(comprasNacionales);

				if(setCompras.isEmpty()){
					logger.info("No se encontraton compras");
					sendErrorMessageToUser("No se encontraron compras para MSI");
					//return null;
				}
				compras= new ArrayList<Compra>();
				compras.addAll(setCompras);

				cliLSWS.setSi07EndPoint(parametroService.getParamById(MSIConstants.ITH_SERVICE_ENDPOINT).getValor());

				movsITH=cliLSWS.getIthInfo(cuentaCBA);
				logger.info("ir a clienteInfoMovs");
				return "ClienteInfoMovs";
			}//fin else itau


		} catch (NumberFormatException e) {
			logger.error(e.getMessage());
			e.printStackTrace();

			throw new MSIException(e.getMessage());
		} catch (RemoteException e) {
			logger.error(e.getMessage());
			e.printStackTrace();

			throw new MSIException(e.getMessage());
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			e.printStackTrace();

			throw new MSIException(e.getMessage());
		}catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();

			throw new MSIException(e.getMessage());
		}

	}


	private List<Compra> comprasCampComercios(String cuenta,String cuentaCBA,ClienteGetMovs movclient,boolean isItau){
		
		List<Campania> campComercios = campaniaService.getCampaniaByParams(null,null, "comercio", null);
		List<Compra> compras = new ArrayList<Compra>();
		List<Double> montos=null;

		Set<Campania> setCamps = new HashSet<Campania>();

		setCamps.addAll(campComercios);
		logger.info("camp comercios "+setCamps.size());
		for (Campania campania : setCamps) {
			montos= new ArrayList<Double>();
			GregorianCalendar hoy = new GregorianCalendar();
			GregorianCalendar antes = new GregorianCalendar();
			antes.add(Calendar.DAY_OF_YEAR, -campania.getNumMaxDiasRegistro());
			Set<Promocion> promosCamp= campania.getPromociones();
			logger.info("camp comercios promos "+promosCamp.size());
			for (Promocion promo : promosCamp) {
				if(promo.getMonto()!= null){
					logger.info("camp comercios promo monto "+promo.getMonto());
					montos.add(promo.getMonto());
				}

			}

			Set<Comercio> comercios=campania.getComercios();
			Collections.sort(montos);
			//int numdias=campania.getNumMaxDiasRegistro();
			//TODO calcula hoy y antes

			for (Comercio comercio : comercios) {
				String[] codigos;
				if(campania.getCodigoTransaccion2()!= null){
					codigos= new String[]{campania.getCodigoTransaccion1(),campania.getCodigoTransaccion2()};
				}else{
					codigos= new String[]{campania.getCodigoTransaccion1().toString()};
				}

				


					String[] movs=null;
					try {
						movs = movclient.getMovsCampComercios(sdf.format(antes.getTime()),sdf.format(hoy.getTime()), cuentaCBA, codigos, montos.get(0), comercio.getNombre());
					} catch (RemoteException e) {
						sendErrorMessageToUser("Error Servicio Tsys "+e.getMessage());
						logger.error("Error Servicio Tsys "+e.getMessage());
						e.printStackTrace();
					} catch (ServiceException e) {
						sendErrorMessageToUser("Error Servicio Tsys "+e.getMessage());
						logger.error("Error Servicio Tsys "+e.getMessage());
						e.printStackTrace();
					}
					if(movs!= null){
						for (String strMov : movs) {
							Compra compra=MSIHelper.fromStringToCompra(strMov);
							compra.setCuenta(cuenta);
							compra.setCuentaFacturadora(cuentaCBA);
							promosCamp= campania.getPromociones();
							for (Promocion promo : promosCamp) {

								if(compra.getMonto().doubleValue()>= promo.getMonto().doubleValue()){

									compra.addComboPromo(promo.getPlazoMeses()+" "+promo.getDescripcion(),promo);
								}

							}

							compra.setIdEdoPromocion(compraService.getStatusCompra(compra));
							compras.add(compra);

						}
					}

				
			}

		}
		return compras;
	}

	private List<Compra> comprasCampSeg(String cuenta,String cuentaCBA,ClienteGetMovs movclient){
		List<ArchivoDetalle> listArD=archivoDetalleService.getArDetalleByCuenta(cuenta);

		List<Compra> compras = new ArrayList<Compra>();
		if(listArD!= null && !listArD.isEmpty()){
			Campania campSeg;
			for (ArchivoDetalle archivoDetalle : listArD) {
				campSeg = archivoDetalle.getCampania();
				if(campSeg != null){
					Calendar fechMax = Calendar.getInstance();
					fechMax.setTime(campSeg.getFechaMaximaRegistro());
					if(Calendar.getInstance().after(fechMax)){
						continue;
					}else{
						List<Double> montos= new ArrayList<Double>();
						boolean isItau=productoTs2Service.cuentaITAU(cuenta);
						for (Promocion promo : campSeg.getPromociones()) {
							if(promo.getMonto()!= null){
								montos.add(promo.getMonto());
							}
						}

						Collections.sort(montos);
						logger.info("campSeg id "+campSeg.getIdCampania());
						String[] movs=null;
						try {
							movs = movclient.webMovEdoCtaFech(sdf.format(archivoDetalle.getFechaInicio()),sdf.format(archivoDetalle.getFechaFin()), cuentaCBA, 
									new String[]{campSeg.getCodigoTransaccion1().toString()},montos.get(0));
						} catch (RemoteException e) {
							sendErrorMessageToUser("Error Servicio Tsys "+e.getMessage());
							logger.error("Error Servicio Tsys "+e.getMessage());
							e.printStackTrace();
						} catch (ServiceException e) {
							sendErrorMessageToUser("Error Servicio Tsys "+e.getMessage());
							logger.error("Error Servicio Tsys "+e.getMessage());
							e.printStackTrace();
						}
						if(movs!= null){	
							for (String strMov : movs) {
								Compra compra=MSIHelper.fromStringToCompra(strMov);
								compra.setCuenta(cuenta);
								compra.setCuentaFacturadora(cuentaCBA);
								Set<Promocion> promosCamp= campSeg.getPromociones();

								for (Promocion promo : promosCamp) {	
									if(compra.getMonto().doubleValue()>= promo.getMonto().doubleValue()){

										compra.addComboPromo(promo.getPlazoMeses()+" "+promo.getDescripcion(), promo);
									}
								}

								compra.setIdEdoPromocion(compraService.getStatusCompra(compra));
								compras.add(compra);

							}
						}
						break;
					}
				}



			}
		}
		return compras;
	}

	private List<Compra> comprasExt(String cuenta,String cuentaCBA,ClienteGetMovs movclient,boolean prog0){
		//1.-Obtern Datos CampExt
		List<Campania> campsExt=campaniaService.getCampaniaByTipo("compras.internal");
		List<Compra> compras = new ArrayList<Compra>();
		Double montoMinComprasExt=0.0;
		int numDiasCampExt=35;
		Set<Promocion> promosCamp = null;

		for (Campania campania : campsExt) {

			numDiasCampExt=campania.getNumMaxDiasRegistro();
			promosCamp= campania.getPromociones();


			for (Promocion promo : promosCamp) {
				montoMinComprasExt = promo.getMonto();
				break;
			}

		}
		Calendar hoy = new GregorianCalendar();
		Calendar antes = new GregorianCalendar();
		antes.add(Calendar.DAY_OF_YEAR, -numDiasCampExt);
		//2.-Obtener compras
		String[] movs=null;
		try {

			movs = movclient.webMovEdoCtaFech(sdf.format(antes.getTime()),sdf.format(hoy.getTime()), cuentaCBA, 
					new String[]{MSIConstants.TRANS_CODIGO_COMPRAS_INTER_10001,MSIConstants.TRANS_CODIGO_COMPRAS_INTER_30001},montoMinComprasExt);
		} catch (RemoteException e) {
			sendErrorMessageToUser("Error Servicio Tsys "+e.getMessage());
			logger.error("Error Servicio Tsys "+e.getMessage());
			e.printStackTrace();
		} catch (ServiceException e) {
			sendErrorMessageToUser("Error Servicio Tsys "+e.getMessage());
			logger.error("Error Servicio Tsys "+e.getMessage());
			e.printStackTrace();
		}
		if(movs!= null){
			//boolean isItau=productoTs2Service.cuentaITAU(cuenta);
			for (String mov : movs) {
				Compra compra=MSIHelper.fromStringToCompra(mov);
				compra.setCuenta(cuenta);
				compra.setCuentaFacturadora(cuentaCBA);
				for (Promocion promo : promosCamp) {
					if("si".equalsIgnoreCase(promo.getProgramaCero())){
						if(prog0 && compra.getMonto().doubleValue()>= promo.getMonto().doubleValue()){
							compra.addComboPromo(promo.getPlazoMeses()+" "+promo.getDescripcion(), promo);
						}
					}else{
						if(compra.getMonto().doubleValue()>= promo.getMonto().doubleValue()){
							compra.addComboPromo(promo.getPlazoMeses()+" "+promo.getDescripcion(), promo);
						}
					}
				}
				//compra.setPromosCombo(promosMesesExt);

				compra.setIdEdoPromocion(compraService.getStatusCompra(compra));
				compras.add(compra);
			}
		}
		return compras;
	}

	private List<Compra> comprasNacionalesProg0(String cuenta,String cuentaCBA,ClienteGetMovs movclient,List<Campania> campMasivas){
		List<Compra> compras = new ArrayList<Compra>();
		List<Campania> campProg0Nals =campaniaService.getCampaniaByTipo("compras.nal.prog.cero");

		List<Double> montos=null;

		int numDiasCamp=35;
		if(!campProg0Nals.isEmpty()){
			Campania progCeroNal=campProg0Nals.get(0);
			montos= new ArrayList<Double>();


			numDiasCamp=progCeroNal.getNumMaxDiasRegistro();

			Calendar hoy = new GregorianCalendar();
			Calendar antes = new GregorianCalendar();
			antes.add(Calendar.DAY_OF_YEAR, -numDiasCamp);
			String[] movs=null;
			for (Promocion promo : progCeroNal.getPromociones()) {
				if(promo.getMonto()!= null){
					montos.add(promo.getMonto());
				}
			}
			Collections.sort(montos);

			try {
				movs =movclient.webMovEdoCtaFech(sdf.format(antes.getTime()),sdf.format(hoy.getTime()), cuentaCBA, 
						new String[]{MSIConstants.TRANS_CODIGO_COMPRAS_NACIONALES},montos.get(0));
			} catch (RemoteException e) {
				sendErrorMessageToUser("Error Servicio Tsys "+e.getMessage());
				logger.error("Error Servicio Tsys "+e.getMessage());
				e.printStackTrace();
			} catch (ServiceException e) {
				sendErrorMessageToUser("Error Servicio Tsys "+e.getMessage());
				logger.error("Error Servicio Tsys "+e.getMessage());
				e.printStackTrace();
			}
			if(movs!= null){	

				for (String strMov : movs) {
					Compra compra=MSIHelper.fromStringToCompra(strMov);
					compra.setCuenta(cuenta);
					compra.setCuentaFacturadora(cuentaCBA);
					logger.info("compra prog 0 "+compra.getDescripcion());

					for (Promocion promo : progCeroNal.getPromociones()) {

						if(compra.getMonto().doubleValue()>= promo.getMonto().doubleValue()){

							compra.addComboPromo(promo.getPlazoMeses()+" "+promo.getDescripcion(), promo);
						}

					}
					Calendar calFechaCompra = Calendar.getInstance();
					calFechaCompra.setTime(compra.getFechaCompra());
					//si hay cmap masivas actuales com promo pra prog 0 hay que agragarlas a las compras
					for (Campania camp : campMasivas) {
						Calendar calFechaIn = Calendar.getInstance();
						calFechaIn.setTime(camp.getFechaInicial());
						Calendar calFechaFin = Calendar.getInstance();
						calFechaFin.setTime(camp.getFechaFinal());
						if(calFechaCompra.equals(calFechaIn) || calFechaCompra.equals(calFechaFin)||calFechaCompra.after(calFechaIn) && calFechaCompra.before(calFechaFin)){
							for(Promocion promo:camp.getPromociones()){

								if("si".equalsIgnoreCase(promo.getProgramaCero())){
									logger.info("se agrega promo al combo camp masiva "+promo.getDescripcion());
									if(compra.getMonto().doubleValue()>= promo.getMonto().doubleValue()){
										compra.addComboPromo(promo.getPlazoMeses()+" "+promo.getDescripcion(), promo);
									}
								}
							}

						}
					}

					compra.setIdEdoPromocion(compraService.getStatusCompra(compra));
					compras.add(compra);

				}
			}

		}


		return compras;
	}

	private List<Compra> comprasNalCampMasivas(String cuenta,String cuentaCBA,ClienteGetMovs movclient,List<Campania> campMasivas){
		List<Compra> compras = new ArrayList<Compra>();
		Date fechaIn= null;
		Date fechaFin= null;
		List<Double> montos=null;
		//pora cada campania obtener sus compras respectivas
		for (Campania camp : campMasivas) {
			logger.info("camp activa "+camp.getNombre());
			montos= new ArrayList<Double>();

			fechaIn= camp.getFechaInicial();
			fechaFin= camp.getFechaFinal();
			String[] movs= null;
			boolean isItau=productoTs2Service.cuentaITAU(cuenta);
			//si hay alguna cmapania acutalmente te traes todas las compras
			if(fechaIn!= null && fechaFin != null){
				//Map<String,Promocion> promosMeses= new HashMap<String,Promocion>();
				for (Promocion promo : camp.getPromociones()) {
					if(promo.getMonto()!= null){
						montos.add(promo.getMonto());
					}

				}
				Collections.sort(montos);
				logger.info("Hay una campania activa hay que traer compras nacionales");
				try {
					movs = movclient.webMovEdoCtaFech(sdf.format(fechaIn),sdf.format(fechaFin), cuentaCBA, 
							new String[]{MSIConstants.TRANS_CODIGO_COMPRAS_NACIONALES},montos.get(0));
				} catch (RemoteException e) {
					sendErrorMessageToUser("Error Servicio Tsys "+e.getMessage());
					logger.error("Error Servicio Tsys "+e.getMessage());
					e.printStackTrace();
				} catch (ServiceException e) {
					sendErrorMessageToUser("Error Servicio Tsys "+e.getMessage());
					logger.error("Error Servicio Tsys "+e.getMessage());
					e.printStackTrace();
				}
				if(movs!= null){
					for (String strMov : movs) {
						Compra compra=MSIHelper.fromStringToCompra(strMov);
						compra.setCuenta(cuenta);
						compra.setCuentaFacturadora(cuentaCBA);

						//iterar camps
						for (Campania cam : campMasivas) {
							//obtener rango fecha
							fechaIn= cam.getFechaInicial();
							fechaFin= cam.getFechaFinal();
							Calendar calIn2= Calendar.getInstance();
							calIn2.setTime(fechaIn);
							Calendar calFin2 = Calendar.getInstance();
							calFin2.setTime(fechaFin);
							//ver si esta en rango
							//compra.getFechaCompra().
							Calendar fchCompra = Calendar.getInstance();
							fchCompra.setTime(compra.getFechaCompra());
							if((fchCompra.after(calIn2) || fchCompra.equals(calIn2)) 
									&& (fchCompra.before(calFin2) || fchCompra.equals(calFin2))){
								//obtener promos
								for (Promocion promo : cam.getPromociones()) {
									if("no".equalsIgnoreCase(promo.getProgramaCero()) && compra.getMonto().doubleValue()>= promo.getMonto().doubleValue()){
										compra.addComboPromo(promo.getPlazoMeses()+" "+promo.getDescripcion(), promo);
									}
								}
							}

						}

						compra.setIdEdoPromocion(compraService.getStatusCompra(compra));
						compras.add(compra);

					}
				}

			}

		}//fin for campanias actulaes


		return compras;
	}

	

	public HtmlSelectBooleanCheckbox getChkSelect() {
		return chkSelect;
	}

	public HtmlSelectOneListbox getCmbPromos() {
		return cmbPromos;
	}



	public List<Compra> getCompras() {
		return compras;
	}

	


	public String getCuenta() {
		return cuenta;
	}
	public String getCuentaPU() {
		return cuentaPU;
	}
	public Double getDispCompras() {
		return dispCompras;
	}
	
	public InfoGeneralTHIGB getInfoCuenta() {
		return infoCuenta;
	}
	public InfoDemograficaINA getInfoDemo() {
		return infoDemo;
	}
	public InfoEncabezado getInfoEnca() {
		return infoEnca;
	}
	public Si07VariableAreaDTO[] getMovsITH() {
		return movsITH;
	}
	
	public String getPagoNoIntereses() {
		return pagoNoIntereses;
	}
	
	public double getSaldoNoIntereses() {
		return saldoNoIntereses;
	}

	public double getSaldoRev() {
		return saldoRev;
	}

	public double getSaldoRevAnterior() {
		return saldoRevAnterior;
	}


	public String getTipoProd() {
		return tipoProd;
	}

	public void habilita(int index) { 
		logger.info("compras index "+index);
		Compra compra = compras.get(index);
		compra.setSelected((Boolean)chkSelect.getValue());
		logger.info("compra selected ? "+compra.isSelected());
		if ("ITA".equals(compra.getTipoTransaccion())){
			if(compra.isSelected()){
				saldoRev -=compra.getMonto();
				if (saldoRev < 0){
					compra.setMontoPromo(saldoRev+compra.getMonto());
					saldoRev =0;
				}else{
					compra.setMontoPromo(compra.getMonto());
				}
			}else{
				saldoRev +=compra.getMontoPromo();
				compra.setMontoPromo(0.0);
			}


		}else{
			if(compra.isSelected()){
				saldoRevAnterior -=compra.getMonto();
				if (saldoRevAnterior < 0){
					compra.setMontoPromo(saldoRevAnterior+compra.getMonto());
					saldoRevAnterior =0;
				}else{
					compra.setMontoPromo(compra.getMonto());
				}
			}else{
				saldoRevAnterior +=compra.getMontoPromo();
				compra.setMontoPromo(0.0);
			}

		}
		logger.info("montoPromo "+compra.getMontoPromo());

		logger.info("saldo corte actual "+ saldoRev+ " saldo corte anterior "+saldoRevAnterior) ;
	}

	public void habilita2(int index) { 
		logger.info("compras index "+index);
		Compra compra = compras.get(index);
		compra.setSelected((Boolean)chkSelect.getValue());
		logger.info("compra selected ? "+compra.isSelected());

		if(compra.isSelected()){

			compra.setMontoPromo(compra.getMonto());
			if ("ITA".equals(compra.getTipoTransaccion())){
				this.saldoRev+=compra.getMonto();
			}else{
				this.saldoRevAnterior+=compra.getMonto();
			}

		}else{

			compra.setMontoPromo(0.0);
			if ("ITA".equals(compra.getTipoTransaccion())){
				this.saldoRev-=compra.getMonto();
			}else{
				this.saldoRevAnterior-=compra.getMonto();
			}
		}



		logger.info("montoPromo "+compra.getMontoPromo());

		logger.info("saldo corte actual "+ saldoRev+ " saldo corte anterior "+saldoRevAnterior) ;
	}

	public boolean isPantallaUnica() {
		return pantallaUnica;
	}

	public boolean isProg0() {
		return prog0;
	}

	public void preRenderEvent() throws MSIException{
		logger.info("cuenta "+cuentaPU);

		if(cuentaPU!=null){
			try {
				String cuentaDesEnc= CryptoAES.decrypt2(cuentaPU);
				logger.info("post bacck"+ FacesContext.getCurrentInstance().isPostback());
				if(!FacesContext.getCurrentInstance().isPostback()){
					this.buscarPorNoCuenta(cuentaDesEnc);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	
	public void setChkSelect(HtmlSelectBooleanCheckbox chkSelect) {
		this.chkSelect = chkSelect;
	}

	public void setCmbPromos(HtmlSelectOneListbox cmbPromos) {
		this.cmbPromos = cmbPromos;
	}

	

	public void setCompras(List<Compra> compras) {
		this.compras = compras;
	}

	
	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	public void setCuentaPU(String cuentaPU) {
		this.cuentaPU = cuentaPU;
	}

	public void setDispCompras(Double dispCompras) {
		this.dispCompras = dispCompras;
	}

	

	public void setInfoCuenta(InfoGeneralTHIGB infoCuenta) {
		this.infoCuenta = infoCuenta;
	}

	public void setInfoDemo(InfoDemograficaINA infoDemo) {
		this.infoDemo = infoDemo;
	}

	public void setInfoEnca(InfoEncabezado infoEnca) {
		this.infoEnca = infoEnca;
	}

	public void setMovsITH(Si07VariableAreaDTO[] movsITH) {
		this.movsITH = movsITH;
	}

	
	public void setPagoNoIntereses(String pagoNoIntereses) {
		this.pagoNoIntereses = pagoNoIntereses;
	}

	public void setPantallaUnica(boolean pantallaUnica) {
		this.pantallaUnica = pantallaUnica;
	}

	public void setProg0(boolean prog0) {
		this.prog0 = prog0;
	}

	

	public void setSaldoNoIntereses(double saldoNoIntereses) {
		this.saldoNoIntereses = saldoNoIntereses;
	}

	public void setSaldoRev(double saldoRev) {
		this.saldoRev = saldoRev;
	}

	public void setSaldoRevAnterior(double saldoRevAnterior) {
		this.saldoRevAnterior = saldoRevAnterior;
	}

	
	public void setTipoProd(String tipoProd) {
		this.tipoProd = tipoProd;
	}

	public void setValorComboPromo(int index){
		System.out.println("setValorcombp");
		Promocion promo = (Promocion) cmbPromos.getValue();
		if(promo !=null){
			Compra compra = compras.get(index);
			compra.setPromocion(promo);
		}

	}


	public InfoCuentaDto getInfo() {
		return info;
	}


	public void setInfo(InfoCuentaDto info) {
		this.info = info;
	}

}

