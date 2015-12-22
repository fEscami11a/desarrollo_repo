package mx.com.invex.msi.webservice;

import java.io.InputStream;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.rpc.ServiceException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tsys.xmlmessaging.ch.Boolean;
import com.tsys.xmlmessaging.ch.ICIcustInfoResponseDataType;
import com.tsys.xmlmessaging.ch.IPIpmtInfoResponseDataType;
import com.tsys.xmlmessaging.ch.ITRtranDetailResponseDataType;
import com.tsys.xmlmessaging.ch.InqAcctAvailTLPOpt;
import com.tsys.xmlmessaging.ch.InqAcctAvailTLPOptRequestType;
import com.tsys.xmlmessaging.ch.InqAcctAvailTLPOptRequestType.TransferInfo.TBALs.TBAL;
import com.tsys.xmlmessaging.ch.InqAcctAvailTLPOptRequestType.TransferInfo.TBALs.TBAL.AmtToMove;
import com.tsys.xmlmessaging.ch.InqAcctAvailTLPOptResponse;
import com.tsys.xmlmessaging.ch.InqCustInfo;
import com.tsys.xmlmessaging.ch.InqCustInfoRequestType;
import com.tsys.xmlmessaging.ch.InqCustInfoResponse;
import com.tsys.xmlmessaging.ch.InqCustomData;
import com.tsys.xmlmessaging.ch.InqCustomDataRequestType;
import com.tsys.xmlmessaging.ch.InqCustomDataRequestType.CustomDataCodes;
import com.tsys.xmlmessaging.ch.InqCustomDataResponseType;
import com.tsys.xmlmessaging.ch.InqGeneralAcct;
import com.tsys.xmlmessaging.ch.InqGeneralAcctRequestType;
import com.tsys.xmlmessaging.ch.InqGeneralAcctResponseType;
import com.tsys.xmlmessaging.ch.InqPmtInfo;
import com.tsys.xmlmessaging.ch.InqPmtInfoRequestType;
import com.tsys.xmlmessaging.ch.InqPmtInfoResponseType;
import com.tsys.xmlmessaging.ch.InqTrans;
import com.tsys.xmlmessaging.ch.InqTransRequestType;
import com.tsys.xmlmessaging.ch.InqTransResponse;
import com.tsys.xmlmessaging.ch.InqTransResponseType;
import com.tsys.xmlmessaging.ch.TSYSfault;
import com.tsys.xmlmessaging.ch.TSYSfaultType;
import com.tsys.xmlmessaging.ch.TSYSprofileType;
import com.tsys.xmlmessaging.ch2.ReqAcctTermTransfer;
import com.tsys.xmlmessaging.ch2.ReqAcctTermTransferRequestType;
import com.tsys.xmlmessaging.ch2.ReqAcctTermTransferRequestType.TermTransfer;
import com.tsys.xmlmessaging.ch2.ReqAcctTermTransferRequestType.TransferFrom;

import mx.com.interware.spira.ls.facade.igbinaenca.ArrayUDataDTO;
import mx.com.interware.spira.ls.facade.igbinaenca.ResultadoIGBINAEnca;
import mx.com.interware.spira.tsysws.si02.SI02FinalResponseDTO;
import mx.com.interware.spira.web.mxp.MxpParam;
import mx.com.invex.msi.mail.CompraEmailDto;
import mx.com.invex.msi.mail.EmailSender;
import mx.com.invex.msi.mail.MailDto;
import mx.com.invex.msi.model.AplicarComprasWSRespDTO;
import mx.com.invex.msi.model.ArchivoDetalle;
import mx.com.invex.msi.model.Campania;
import mx.com.invex.msi.model.Comercio;
import mx.com.invex.msi.model.Compra;
import mx.com.invex.msi.model.CompraEnviada;
import mx.com.invex.msi.model.CompraWSDTO;
import mx.com.invex.msi.model.ComprasWSRespDTO;
import mx.com.invex.msi.model.InfoEnviadaDTO;
import mx.com.invex.msi.model.Parametro;
import mx.com.invex.msi.model.Producto;
import mx.com.invex.msi.model.ProductoTs2;
import mx.com.invex.msi.model.Promocion;
import mx.com.invex.msi.service.ArchivoDetalleService;
import mx.com.invex.msi.service.CampaniaService;
import mx.com.invex.msi.service.CompraService;
import mx.com.invex.msi.service.ParametroService;
import mx.com.invex.msi.service.ProductoService;
import mx.com.invex.msi.service.ProductoTs2Service;
import mx.com.invex.msi.service.PromocionService;
import mx.com.invex.msi.util.ComparadorCompraMonto;
import mx.com.invex.msi.util.ComparadorFecha;
import mx.com.invex.msi.util.EnviarMail;
import mx.com.invex.msi.util.MSIConstants;
import mx.com.invex.msi.util.MSIHelper;
import mx.com.invex.msi.ws.ClientLSWS;
import mx.com.invex.msi.ws.ClientTS2;
import mx.com.invex.msi.ws.ClienteGetMovs;
import mx.com.invex.msi.ws.ClientePromosWS;
import mx.com.invex.msi.ws.Ts2MsgWsClient;

@Controller
@RequestMapping("/ComprasService")
public class ComprasServiceImpl{

	@Autowired
	CampaniaService campaniaService;
	@Autowired
	CompraService compraService;
	@Autowired
	ParametroService parametroService;
	@Autowired
	ArchivoDetalleService archivoDetalleService;
	@Autowired
	PromocionService promocionService;
	@Autowired
	ProductoService productoService;
	
	@Autowired
	ProductoTs2Service productoTs2Service;
	 @Autowired
	    private EmailSender emailSender;
	
	private static final Logger logger = Logger.getLogger(ComprasServiceImpl.class);
	
	@RequestMapping(method=RequestMethod.POST, value="/compras")
	@ResponseBody
	public AplicarComprasWSRespDTO aplicarCompras(@RequestBody List<CompraWSDTO> comprasWSDTO) {
		logger.info("aplicar compras ");
		AplicarComprasWSRespDTO resp = new AplicarComprasWSRespDTO();
		try {
		
		if(comprasWSDTO != null && !comprasWSDTO.isEmpty()){
			String cuenta = comprasWSDTO.get(0).getCuenta();
			String username = comprasWSDTO.get(0).getUsername();
			boolean ists2=productoTs2Service.cuentaITAU(cuenta);
			
	
		String res=null;
		
			
			Integer folio=compraService.getFolio();
			List<Compra> compras = new ArrayList<Compra>();
		
			
			for (CompraWSDTO compraWSDTO : comprasWSDTO) {
				Compra compra =MSIHelper.getCompraFromCompraWSDTO(compraWSDTO);
				logger.info("compra id edo promo "+compra.getIdEdoPromocion());
				compra.setFolio(folio);
				compra.setOrigen("WSPortal");
				if(compraWSDTO.getPromocion()!= null){
					Promocion promo=promocionService.getPromoById(new Long(compraWSDTO.getPromocion()));
					compra.setPromocion(promo);
				}
				
				if(compra.getDescripcion()== null || "".equals(compra.getDescripcion().trim())){
					compra.setDescripcion("NEGOCIO GENERICO");
				}
				String desc= compra.getDescripcion();
				compra.setDescripcion("TRASPASO MSI "+  String.format("%-17s",desc).substring(0,17)  +  ((compra.getNumRefTran()!=null)?(" "+compra.getNumRefTran().substring(13)):""));
				if(compra.getNumRefTran()==null){
					compra.setNumRefTran("0");
				}
				
				//si la comnpra ya se aplico
				String statusCom=compraService.getStatusCompra(compra);
				if(statusCom!= null){
					resp.setStatus(1);
					resp.setMsgError("La compra "+compra.getDescripcion()+ " ya ha sido enviada a promocion");

				}
				if(ists2){
					compra.setMontoPromo(compra.getMonto());
					compra.setMontoOriginal(compra.getMonto());
					compraService.save(compra);
					res=enviarPromoTs2(compra);
				}else{
					MxpParam param = new MxpParam();
					param.setAccount(compra.getCuentaFacturadora());
	
					param.setAmount(""+Math.round(compra.getMontoPromo()*100));
					if("ITA".equals(compra.getTipoTransaccion().trim())){
						param.setBalanceId(MSIConstants.BALANCE_ID_CURRENT_PURCHASE);
					}else if ("IPS".equals(compra.getTipoTransaccion().trim())){
						param.setBalanceId(MSIConstants.ONE_CYCLE_OLD_PURCHASE);
					}
					param.setPromoId(compraWSDTO.getPromocion());
					param.setUserName(compra.getUsername());
					param.setDescription(compra.getDescripcion());
					compra.setMontoOriginal(compra.getMonto());
					compra.setMonto(compra.getMontoPromo());
					compraService.save(compra);
					ClientePromosWS cliMXP = new ClientePromosWS(parametroService.getParamById(MSIConstants.MXP_SERVICE_ENDPOINT).getValor());
						res = MSIConstants.desa?"OK":cliMXP.aplicarPromocion(param);
				}
					if("OK".equalsIgnoreCase(res)){
						compra.setEnPromocion(true);
						compra.setIdEdoPromocion(MSIConstants.PROM_ESTATUS_ENVIADO);
						
					}else{
						compra.setEnPromocion(false);
						compra.setIdEdoPromocion(MSIConstants.PROM_ESTATUS_PENDIENTE);
					}
					compra.setFechaAplicacionPromocion(new Date());
					compraService.update(compra);
				
				compras.add(compra);
			}
			
			if(compras.isEmpty()){
				resp.setStatus(1);
				resp.setMsgError("No se encontraron resultados");
				return resp;
			}
			
			
			Collections.sort(compras,new ComparadorCompraMonto());
			double montoCompraMenor=compras.get(0).getMonto();
			List<InfoEnviadaDTO> mesesPromos = new ArrayList<InfoEnviadaDTO>();
			
			
			if (!compras.isEmpty()) {
				//realizar calculo de ,mensualidades
				//set con los tipos de meses diferentes
				Set<Integer> mesesExistentes = new HashSet<Integer>();
				List<CompraEnviada> comprasEnviadas = new ArrayList<CompraEnviada>();
				double montoTotal =0.0;
				int index=0;
				SimpleDateFormat format = new SimpleDateFormat(
						"dd/MM/yyyy");
				NumberFormat formatter = NumberFormat.getCurrencyInstance();
				Double montoAPagar= new Double(0);
				for(Compra compra : compras){
					
					String meses =compra.getPromocion().getPlazoMeses().toString();
					NumberFormat nf = NumberFormat.getCurrencyInstance();
					montoTotal+=compra.getMontoPromo();
					String monto = nf.format(compra.getMontoPromo());
					Double montoCompra =  (compra.getMontoPromo()/Integer.parseInt(meses));								
					CompraEnviada compEnviada = new CompraEnviada();
					compEnviada.setFechaCompra(format.format(compra.getFechaCompra()));
					compEnviada.setDescripcion(compra.getDescripcion());
					compEnviada.setMontoOriginal(nf.format(compra.getMontoOriginal()));
					compEnviada.setMontoaPromo(monto);
					compEnviada.setMeses(meses);
					compEnviada.setPagoMensual(formatter.format(montoCompra));
						comprasEnviadas.add(compEnviada);
					montoAPagar += montoCompra;
					index++;
				
					if(compra.getPromocion()!= null)
						mesesExistentes.add(new Integer(compra.getPromocion().getPlazoMeses()));
				}
				resp.setComprasEnviadas(comprasEnviadas);
			
				 int comprasTot =0;
				 montoTotal=0.0;
				 double totMensualidad=0.0;
				 mesesPromos = new ArrayList<InfoEnviadaDTO>();
				
				
				boolean haycompras=false;
				
				
				for(Integer tipoMes : mesesExistentes){
					int numElem = 0;
					double monto=0;
					
					for(Compra compra : compras){
						
						Integer plzo = new Integer(compra.getPromocion().getPlazoMeses());
						//caso especial anualidad
						haycompras=true;
					
						
						if(tipoMes.intValue() == plzo.intValue()){
							monto+=compra.getMontoPromo();
							numElem++;
							
						}
					}
					comprasTot +=numElem;
					montoTotal += monto;
					totMensualidad += monto/tipoMes;
					mesesPromos.add(new InfoEnviadaDTO(tipoMes,numElem,monto));
				}//finf for tipo meses
				resp.setMesesPromos(mesesPromos);
				resp.setMontoTotal(montoTotal);
				resp.setMontoAPagar(totMensualidad);
				resp.setCuenta(cuenta);
				resp.setFolio(folio);
				if(haycompras){
					StringBuffer sbICM = new StringBuffer();
					//String userName =FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
					
					sbICM.append("MSI: Se ingresó un total de ".toUpperCase()+comprasTot 
							+" compras con el número de folio ".toUpperCase()+folio
							+" "+username
							+" "+sdf.format(new Date()));
					
					String sldoNoInt = null;
					if(!ists2){
						HttpClient webIstemci = new DefaultHttpClient();
						Parametro paramWS = parametroService
								.getParamById(MSIConstants.MSI_PPNGI_SERVICE_ENDPOINT);
						HttpGet webdenGetir = new HttpGet(paramWS.getValor()
								+ cuenta);
						HttpResponse donenCevap;
						
						donenCevap = webIstemci.execute(webdenGetir);
						HttpEntity birim = donenCevap.getEntity();
						if (birim != null) {
							InputStream gelenVeri = birim.getContent();
							sldoNoInt = MSIHelper
									.convertStreamToString(gelenVeri);
							logger.info("sldoNoInt ..."
									+ new Double(sldoNoInt.trim()) + "...");
							gelenVeri.close();
						}
					}else{
						
				                	 sldoNoInt=sadloNoIntTs2(cuenta);
								
					}
					resp.setSaldoNoInt(new Double(sldoNoInt.trim()));
					StringBuffer sbICM2 = new StringBuffer();
					for (InfoEnviadaDTO infoEnviadaDTO : mesesPromos) {
						logger.info("monto " + infoEnviadaDTO.getMonto()
								+ " meses " + infoEnviadaDTO.getMeses());
						String icm = "MSI "
								+ infoEnviadaDTO.getMeses()
								+ "M: "
								+ infoEnviadaDTO.getNumCompras()
								+ " compras por un monto de ".toUpperCase()
								+ formatter.format(infoEnviadaDTO
										.getMonto())
								+ " y pagos mensuales de ".toUpperCase()
								+ formatter.format(infoEnviadaDTO
										.getMonto()
										/ infoEnviadaDTO.getMeses());
//								+ ". SU PAGO PARA NO GENERAR INTERESES SERA DE "
//								+ formatter.format(new Double(sldoNoInt
//										.trim()));

						logger.info("icm " + icm);
						sbICM2.append(icm);

					}
					
					 if (!ists2) {
						
						
						
						ClientLSWS clienteTsysWS = new ClientLSWS(
								parametroService.getParamById(
										MSIConstants.TSYSWS_ENDPOINT)
										.getValor());
						ResultadoIGBINAEnca info = clienteTsysWS
								.getResultadoIGBINAEnca(cuenta,
										MSIConstants.TSYSWS_SECURITY_USERNAME,
										MSIConstants.TSYSWS_ACCESS_KEY);
						ArrayUDataDTO[] arrUData = info.getInfoGeneralTHIGB()
								.getUData();
						String ucode3 = arrUData[2].getToString();
						logger.info("ucode 3 (prog 0)? " + ucode3);
						boolean prog0 = false;
						if (ucode3 != null && ucode3.length() > 0) {

							if ('G' == ucode3.charAt(3)) {

								prog0 = true;
							} else {

								prog0 = false;
							}

							Producto producto = productoService
									.getProductoCuenta(cuenta, ucode3);
							String tipoProd = null;

							if (producto != null) {
								tipoProd = producto.getCampania();
							}

							String email = info.getInfoDemograficaINA()
									.getEmail();
							String nombreCompleto = info
									.getInfoDemograficaINA()
									.getNombreCompleto();
							if (info != null) {
								logger.info("compras " + compras + " email "
										+ email + " folio " + folio
										+ " nombreCompleto " + nombreCompleto
										+ " cuenta " + cuenta + " sldonoint "
										+ sldoNoInt + " tipoProd " + tipoProd);
								if (MSIConstants.desa) {
									email = "fescamilla@invex.com";
								}
								
								MSIHelper.enviarMensaje(compras, email, folio,
										nombreCompleto, cuenta, new Double(
												sldoNoInt.trim()), tipoProd,
										ists2);

							}
						}
						
						
						Ts2MsgWsClient msgWS = new Ts2MsgWsClient();
						String wsMsgEndPoint= parametroService.getParamById(MSIConstants.TS2_MSG_ENDPOINT).getValor();
						logger.info("ws "+wsMsgEndPoint+" usr "+username+" custID 0 cuenta "+cuenta+" msg1 "+sbICM.toString());
						logger.info("ws "+wsMsgEndPoint+" usr "+username+" custID 0 cuenta "+cuenta+" msg2 "+sbICM2.toString());
						msgWS.setEndPoint(wsMsgEndPoint);
						msgWS.insertaMsgTs2(username, "0", cuenta, sbICM.toString());
						msgWS.insertaMsgTs2(username, "0", cuenta, sbICM2.toString());
						
					}else{
						 InqCustInfo inqCustInfo = new InqCustInfo();
						 InqCustInfoRequestType inqCustInfoReq = new InqCustInfoRequestType();
						 inqCustInfoReq.setKey(cuenta);
						 inqCustInfoReq.setKeyType("cardNbr");
						 inqCustInfoReq.setVersion("2.0.0");
						 inqCustInfo.setInqCustInfoRequest(inqCustInfoReq);
						 com.tsys.xmlmessaging.ch.TSYSprofileType tp2 = new com.tsys.xmlmessaging.ch.TSYSprofileType();
						 tp2.setClientID("7401");
						 tp2.setUserID("gp5rwf");
						 tp2.setVendorID("00000000");
						 logger.info("custInfoResp");
						 ClientTS2 cts2= new ClientTS2();

						 InqCustInfoResponse custInfoResp= cts2.inqCustInfo(tp2, inqCustInfo);
						 logger.info("despues");
						 List<ICIcustInfoResponseDataType> listCustInfos = custInfoResp.getInqCustInfoResult().getCustInfo();
						 String custId=null;
						 logger.info("tam custinfos "+listCustInfos.size());
						 String nombreCompleto= null;
						 String email=null;
			            for (ICIcustInfoResponseDataType custInfo : listCustInfos) {
			                            logger.info("custInfo custiyer "+custInfo.getCustType());
			                            if(custInfo.getCustType().equals("Primary") ){
			                           	 custId=custInfo.getCustID();
			                             nombreCompleto= custInfo.getCustName().getWhole() == null?"": custInfo.getCustName().getWhole()  ;
		                                    email= custInfo.getEmailAddr()  == null?"":  custInfo.getEmailAddr().getValue() ;

			                            }
			            }
						Ts2MsgWsClient msgWS = new Ts2MsgWsClient();
						String wsMsgEndPoint= parametroService.getParamById(MSIConstants.TS2_MSG_ENDPOINT).getValor();
						logger.info("ws "+wsMsgEndPoint+" usr "+username+" custID "+custId+" cuenta "+cuenta+" msg1 "+sbICM.toString());
						logger.info("ws "+wsMsgEndPoint+" usr "+username+" custID "+custId+" cuenta "+cuenta+" msg2 "+sbICM2.toString());
						msgWS.setEndPoint(wsMsgEndPoint);
						msgWS.insertaMsgTs2(username, custId, cuenta, sbICM.toString());
						msgWS.insertaMsgTs2(username, custId, cuenta, sbICM2.toString());
						String tipoProd = obtenerProductoTs2(cuenta);
						
						
					
						
						enviarMensaje(compras, email, folio,
								nombreCompleto, cuenta, new Double(
										sldoNoInt.trim()), tipoProd,
								ists2);
						
					}
				
					
				}//fin if compras
			
			}//if emtio comprea
		}
		} catch (Exception e) {
			resp.setStatus(2);
			resp.setMsgError("ERROR "+e.getMessage());
			e.printStackTrace();
		}
	return resp;
	}
	
	
	private String enviarMensaje(List<Compra> compras,String email,Integer folio,String nombreCompleto,String cuenta,double saldoNoInt,String tipoProd,boolean isItau){

		//		FacesContext context = FacesContext.getCurrentInstance();
		//		ConsultaClienteBean conBean = (ConsultaClienteBean) context.getExternalContext().getSessionMap().get("ConsultaClienteBean");		
		StringBuffer body = new StringBuffer();
		try{						
			//String toAddress="fescamilla@invex.com";
			String destinatario = email;
			if(MSIConstants.desa){
			destinatario = "dflores@invex.com";
			}
			String ccAddress=""; 
			String bccAddress="";
			boolean isHTMLFormat = true;			
			String nombre="";
			if(nombreCompleto!= null && !nombreCompleto.isEmpty()){
				String ar[] = nombreCompleto.replace('*', ' ').split(" ");

				for (String string : ar) {
					nombre+= string.charAt(0)+string.substring(1).toLowerCase()+" ";
				}
			}


			NumberFormat formatter = NumberFormat.getCurrencyInstance(); 										

			
			SimpleDateFormat formateador = new SimpleDateFormat(
					   "MMMM',' yyyy", new Locale("es"));
			Date fechaDate = new Date();
			String fecha = formateador.format(fechaDate);
			//System.out.println(fecha);
//si es itau
			
			if(isItau){
				
				ClientTS2 cts2= new ClientTS2();
				 TSYSprofileType tp = new TSYSprofileType();
				 tp.setClientID("7401");
				 tp.setUserID("gp5rwf");
				 tp.setVendorID("00000000");
				InqGeneralAcctRequestType req = new InqGeneralAcctRequestType();
	           	 req.setVersion("2.19.0");
	           	 req.setKey(cuenta);
	           	 req.setKeyType("cardNbr");
	           	 InqGeneralAcct inqGeneralAcct = new InqGeneralAcct();
	           	 inqGeneralAcct.setInqGeneralAcctRequest(req);
					InqGeneralAcctResponseType res= cts2.inqGeneralAcct(tp,inqGeneralAcct).getInqGeneralAcctResult();
				
						if(!"000".equals(res.getStatus())){
						
							TSYSfaultType fault =res.getFaults();
							List<TSYSfault> lfaulta =fault.getFault();
							for (TSYSfault sfault : lfaulta) {
								//logger.info(sfault.getStatus()+" "+ sfault.getFaultDesc());
							}
						}
					
					String cpc = res.getAcctGeneralInfo().getValue().getClientProductCode() == null?"": res.getAcctGeneralInfo().getValue().getClientProductCode().getValue() ;
					MailDto mailParams = new MailDto();
					mailParams.setNombre(nombre.trim());
					mailParams.setFecha(Character.toUpperCase(fecha.charAt(0))+fecha.substring(1));
					mailParams.setFolio(""+folio);

					Collections.sort(compras, new ComparadorFecha());

					Double montoAPagar= new Double(0);
					SimpleDateFormat format = new SimpleDateFormat(
							"dd/MM/yyyy");
				
					double montoTotal =0.0;
					int index=0;
					List<CompraEmailDto> lcompras = new ArrayList<CompraEmailDto>();
					for (Compra tx : compras) {
						
						String meses =tx.getPromocion().getPlazoMeses().toString();
						NumberFormat nf = NumberFormat.getCurrencyInstance();
						montoTotal+=tx.getMontoPromo();
						String monto = nf.format(tx.getMontoPromo());
						Double montoCompra =  (tx.getMontoPromo()/Integer.parseInt(meses));								


						CompraEmailDto ced = new CompraEmailDto(); 
						ced.setFechaCompra(format.format(tx.getFechaCompra()));
						ced.setDescripcion(tx.getDescripcion());
						ced.setMontoOriginal(nf.format(tx.getMontoOriginal()));
						ced.setMonto(monto);
						ced.setMeses(meses);
						ced.setPagoMensual(formatter.format(montoCompra));
						lcompras.add(ced);
						
						montoAPagar += montoCompra;
						index++;
					}									

					mailParams.setCompras(lcompras);
					mailParams.setMontoTotal(formatter.format(montoTotal));
					mailParams.setMontoAPagar(formatter.format(montoAPagar));
					mailParams.setSaldoNoInt(formatter.format(saldoNoInt));
					mailParams.setTerminacion(cuenta.substring(cuenta.length()-4));
					
					mailParams.setMsgPagoMin(formatter.format(saldoNoInt));
					
					Map<String,Object> model = new HashMap<String,Object>();	             
					model.put("mailParams", mailParams);		             
					emailSender.sendEmail(email,"boletin@invextarjetas.com.mx",  "Promociones MSI",model,cpc);
					return null;
				
			}

		}catch(Exception e){

		
			e.printStackTrace();
					
		}		


		return body.toString();
	}
	
	
	private String sadloNoIntTs2(String cuenta){
		ClientTS2 cts2= new ClientTS2();
		 TSYSprofileType tp = new TSYSprofileType();
		 tp.setClientID("7401");
		 tp.setUserID("gp5rwf");
		 tp.setVendorID("00000000");
		InqPmtInfo inqPmtInfo = new InqPmtInfo();
  		 InqPmtInfoRequestType inqPmtInfoReq = new InqPmtInfoRequestType();
  		
  		 inqPmtInfoReq.setKey(cuenta);
  		 inqPmtInfoReq.setKeyType("cardNbr");
  		 inqPmtInfoReq.setVersion("1.2.0");
  		 inqPmtInfoReq.setCycleType("Current");
  		 inqPmtInfo.setInqPmtInfoRequest(inqPmtInfoReq);
           InqPmtInfoResponseType pmtInfoResponse = cts2.inqPmtInfo(tp,inqPmtInfo).getInqPmtInfoResult();
           logger.info("pmtInfoResponse");
			if("999".equals(pmtInfoResponse.getStatus())){
				logger.info(pmtInfoResponse.getStatusMsg());
				TSYSfaultType fault =pmtInfoResponse.getFaults();
				List<TSYSfault> lfaulta =fault.getFault();
				for (TSYSfault sfault : lfaulta) {
					logger.info(sfault.getStatus()+" "+ sfault.getFaultDesc());
				}
			}else{
               List<IPIpmtInfoResponseDataType> pmtInfo=pmtInfoResponse.getPmtInfo();
               for (IPIpmtInfoResponseDataType ipIpmtInfoResp : pmtInfo) {
               	BigDecimal ppNI =ipIpmtInfoResp.getAmtProjectedPaidInFull().getValue().getValue();
               	 return ppNI.toString();
				}
			}
			return null;
	}
	
		private String obtenerProductoTs2(String cuenta){
			ClientTS2 cts2= new ClientTS2();
			 TSYSprofileType tp = new TSYSprofileType();
			 tp.setClientID("7401");
			 tp.setUserID("gp5rwf");
			 tp.setVendorID("00000000");
			 InqGeneralAcctRequestType req = new InqGeneralAcctRequestType();
           	 req.setVersion("2.19.0");
           	 req.setKey(cuenta);
           	 req.setKeyType("cardNbr");
           	 InqGeneralAcct inqGeneralAcct = new InqGeneralAcct();
           	 inqGeneralAcct.setInqGeneralAcctRequest(req);
				InqGeneralAcctResponseType res= cts2.inqGeneralAcct(tp,inqGeneralAcct).getInqGeneralAcctResult();
				 logger.info("InqGeneralAcct");
					if("999".equals(res.getStatus())){
						logger.info(res.getStatusMsg());
						TSYSfaultType fault =res.getFaults();
						List<TSYSfault> lfaulta =fault.getFault();
						for (TSYSfault sfault : lfaulta) {
							logger.info(sfault.getStatus()+" "+ sfault.getFaultDesc());
						}
					}
				
				String tpc = res.getAcctGeneralInfo().getValue().getTSYSProductCode() == null?"": res.getAcctGeneralInfo().getValue().getTSYSProductCode() ;
				String cpc = res.getAcctGeneralInfo().getValue().getClientProductCode() == null?"": res.getAcctGeneralInfo().getValue().getClientProductCode().getValue();
				logger.info("tpc "+tpc +" cpc " +cpc );
				ProductoTs2 prodts2 = productoTs2Service.getProductoItau(cuenta, tpc, cpc);
				String tipoProd=null;
				if(prodts2!= null){
					tipoProd =prodts2.getProducto();
				}
			     logger.info("tipo prod "+tipoProd);
			     return tipoProd;
		}
	
	private String enviarPromoTs2(Compra compra) {
		ClientTS2 cts2= new ClientTS2();

		 TSYSprofileType tp = new TSYSprofileType();
		 tp.setClientID("7401");
		 tp.setUserID("gp5rwf");
		 tp.setVendorID("00000000");
		String res=null;
		logger.info("inqAcctAvailTLPOpt assesfee false tlptype Installment tbal code 0001 amtToMove "+compra.getMontoPromo());
		InqAcctAvailTLPOpt inqAcctAvailTLPOpt = new InqAcctAvailTLPOpt();
		InqAcctAvailTLPOptRequestType inqAcctAvailTLPOptReq= new InqAcctAvailTLPOptRequestType();
		
		inqAcctAvailTLPOptReq.setKey(compra.getCuenta());
		inqAcctAvailTLPOptReq.setKeyType("cardNbr");
		inqAcctAvailTLPOptReq.setVersion("2.0.0");
		inqAcctAvailTLPOptReq.setTLPType("Installment");
		Boolean assessFee = new Boolean();
		assessFee.setValue(false);
		inqAcctAvailTLPOptReq.setAssessFee(assessFee);
		InqAcctAvailTLPOptRequestType.TransferInfo transferInfo = new InqAcctAvailTLPOptRequestType.TransferInfo();
		InqAcctAvailTLPOptRequestType.TransferInfo.TBALs tbals= new InqAcctAvailTLPOptRequestType.TransferInfo.TBALs();
		List<TBAL> ltbal=tbals.getTBAL();
		TBAL tbal = new TBAL();
		AmtToMove amtToMove = new AmtToMove();
		amtToMove.setCode("MXN");
		amtToMove.setValue(new BigDecimal(""+compra.getMontoPromo()));
		tbal.setAmtToMove(amtToMove);
		tbal.setCode("0001");
		ltbal.add(tbal);
		transferInfo.setTBALs(tbals);
		inqAcctAvailTLPOptReq.setTransferInfo(transferInfo);
		
		inqAcctAvailTLPOpt.setInqAcctAvailTLPOptRequest(inqAcctAvailTLPOptReq);
		InqAcctAvailTLPOptResponse inqAcctAvailTLPOptResp= cts2.inqAcctAvailTLPOpt(tp, inqAcctAvailTLPOpt);
		String status=inqAcctAvailTLPOptResp.getInqAcctAvailTLPOptResult().getStatus();
		if(!"000".equals(status)){
			String msg = inqAcctAvailTLPOptResp.getInqAcctAvailTLPOptResult().getStatusMsg();
			logger.info(msg);
			TSYSfaultType fault =inqAcctAvailTLPOptResp.getInqAcctAvailTLPOptResult().getFaults();
			List<TSYSfault> lfaulta =fault.getFault();
			for (TSYSfault sfault : lfaulta) {
				logger.info(sfault.getStatus()+" "+ sfault.getFaultDesc());
			}
		}
		logger.info("reqAcctTermTransfer transfer to type installment assessFee fales meses "+compra.getPromocion().getPlazoMeses()+" tbal monto "+compra.getMontoPromo());
		ReqAcctTermTransfer reqAcctTermTransfer = new ReqAcctTermTransfer();
		ReqAcctTermTransferRequestType reqAcctTermTransferReq = new ReqAcctTermTransferRequestType();
		reqAcctTermTransferReq.setKeyType("cardNbr");
		reqAcctTermTransferReq.setKey(compra.getCuenta());
		reqAcctTermTransferReq.setVersion("2.1.0");
		TermTransfer termTransf= new TermTransfer();
		termTransf.setTransferToType("Installment");
		com.tsys.xmlmessaging.ch2.Boolean assessFee2 = new com.tsys.xmlmessaging.ch2.Boolean();
		assessFee2.setValue(false);
		termTransf.setAssessFee(assessFee2);
		int meses =compra.getPromocion().getPlazoMeses();
		if(meses==3){
			termTransf.setTransferToTLPOpt("1420");
		}else if(meses==6){
			termTransf.setTransferToTLPOpt("1421");
		}else if(meses==9){
			termTransf.setTransferToTLPOpt("1422");
		}else if(meses==12){
			termTransf.setTransferToTLPOpt("1423");
		}else if(meses==11){
			termTransf.setTransferToTLPOpt("1450");
		}else if(meses==18){
			termTransf.setTransferToTLPOpt("1453");
		}else if(meses==24){
			termTransf.setTransferToTLPOpt("1455");
		}else if(meses==7){
			termTransf.setTransferToTLPOpt("1448");
		}
		
		reqAcctTermTransferReq.setTermTransfer(termTransf);
		TransferFrom transferFrom = new TransferFrom();
		List<ReqAcctTermTransferRequestType.TransferFrom.TBAL> tbals2=transferFrom.getTBAL();
		
		ReqAcctTermTransferRequestType.TransferFrom.TBAL tbal2 = new ReqAcctTermTransferRequestType.TransferFrom.TBAL();
		tbal2.setCode("0001");
		ReqAcctTermTransferRequestType.TransferFrom.TBAL.AmtToMove amtToMove2 = new ReqAcctTermTransferRequestType.TransferFrom.TBAL.AmtToMove();
		amtToMove2.setCode("MXN");
		amtToMove2.setValue(new BigDecimal(""+compra.getMontoPromo()));
		tbal2.setAmtToMove(amtToMove2);
		
		tbals2.add(tbal2);
		reqAcctTermTransferReq.setTransferFrom(transferFrom);
		reqAcctTermTransfer.setReqAcctTermTransferRequest(reqAcctTermTransferReq);
		com.tsys.xmlmessaging.ch2.TSYSprofileType profile = new com.tsys.xmlmessaging.ch2.TSYSprofileType();
		 profile.setClientID("7401");
		 profile.setUserID("gp5rwf");
		 profile.setVendorID("00000000");
		if (!MSIConstants.desa) {
//			ReqAcctTermTransferResponseType reqAcctTermTransferRes = cts2
//					.reqAcctTermTransfer(profile, reqAcctTermTransfer)
//					.getReqAcctTermTransferResult();
//			status = reqAcctTermTransferRes.getStatus();
//			if (!"000".equals(status)) {
//				String msg = reqAcctTermTransferRes.getStatusMsg();
//				logger.info(msg);
//				com.tsys.xmlmessaging.ch2.TSYSfaultType fault = reqAcctTermTransferRes
//						.getFaults();
//				List<com.tsys.xmlmessaging.ch2.TSYSfault> lfaulta = fault
//						.getFault();
//				for (com.tsys.xmlmessaging.ch2.TSYSfault sfault : lfaulta) {
//					logger.info(sfault.getStatus() + " "
//							+ sfault.getFaultDesc());
//				}
//				return "error";
//			}
		}
		return "OK";
	
		
	}


	@RequestMapping(value = "/{cuenta}", method = RequestMethod.GET)
	@ResponseBody
	public ComprasWSRespDTO getCompra(@PathVariable("cuenta") String cuenta) {
		ComprasWSRespDTO comprasWSRespDTO = new ComprasWSRespDTO(); 
		if(cuenta == null){
			comprasWSRespDTO.setStatus(1);
			comprasWSRespDTO.setMsgError("La cuenta es NULL");
			return comprasWSRespDTO;
		}
		
		Pattern pat = Pattern.compile("^\\d{16}$");
	     Matcher mat = pat.matcher(cuenta);
	     if (!mat.matches()) {
	    	comprasWSRespDTO.setStatus(2);
			comprasWSRespDTO.setMsgError("La cuenta es no es valida");
			return comprasWSRespDTO;
	     }
	     
	 	
			if(productoTs2Service.cuentaITAU(cuenta)){
				//validar bloqueo
				ClientTS2 cts2= new ClientTS2();
				 TSYSprofileType tp = new TSYSprofileType();
				 tp.setClientID("7401");
				 tp.setUserID("gp5rwf");
				 tp.setVendorID("00000000");
				 InqGeneralAcctRequestType req = new InqGeneralAcctRequestType();
	           	 req.setVersion("2.19.0");
	           	 req.setKey(cuenta);
	           	 req.setKeyType("cardNbr");
	           	 InqGeneralAcct inqGeneralAcct = new InqGeneralAcct();
	           	 inqGeneralAcct.setInqGeneralAcctRequest(req);
					InqGeneralAcctResponseType res= cts2.inqGeneralAcct(tp,inqGeneralAcct).getInqGeneralAcctResult();
					 logger.info("InqGeneralAcct");
						if(!"000".equals(res.getStatus())){
							logger.info(res.getStatusMsg());
							TSYSfaultType fault =res.getFaults();
							List<TSYSfault> lfaulta =fault.getFault();
							for (TSYSfault sfault : lfaulta) {
								logger.info(sfault.getStatus()+" "+ sfault.getFaultDesc());
							}
						}
					
					String tpc = res.getAcctGeneralInfo().getValue().getTSYSProductCode() == null?"": res.getAcctGeneralInfo().getValue().getTSYSProductCode() ;
					String cpc = res.getAcctGeneralInfo().getValue().getClientProductCode() == null?"": res.getAcctGeneralInfo().getValue().getClientProductCode().getValue();
					logger.info("tpc "+tpc +" cpc " +cpc );
					ProductoTs2 prodts2 = productoTs2Service.getProductoItau(cuenta, tpc, cpc);
					
	               
					int cycle=res.getAcctGeneralInfo().getValue().getBillingCycle().getValue();

					DecimalFormat df2 = new DecimalFormat( "00" );
					Calendar diaCorte = Calendar.getInstance();
					diaCorte.set(Calendar.DAY_OF_MONTH, cycle);
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
					
					GregorianCalendar hoy = new GregorianCalendar();
					String hoyStr=sdf.format(hoy.getTime());
					try {
						hoy.setTime(sdf.parse(hoyStr));
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					// calcular prog
					boolean prog0=false;
					int numDiasProg0=0;
					InqCustomData inqCustomData = new InqCustomData();
					InqCustomDataRequestType inqCustomDataReq = new InqCustomDataRequestType();
					inqCustomDataReq.setKey(cuenta);
					inqCustomDataReq.setKeyType("cardNbr");
					inqCustomDataReq.setVersion("1.0.0");
					
					CustomDataCodes customDataCodes = new CustomDataCodes();
					List<String> custDatas =customDataCodes.getCustomDataCode();
					//fecha vencimiento
					custDatas.add("61");
					//fecha inscrtipcion prgo cero
					custDatas.add("81");
					inqCustomDataReq.setCustomDataCodes(customDataCodes);
					inqCustomData.setInqCustomDataRequest(inqCustomDataReq);
					InqCustomDataResponseType customDataResp= cts2.inqCustomData(tp, inqCustomData).getInqCustomDataResult();
					if(!"000".equals( customDataResp.getStatus())){
						logger.info( customDataResp.getStatusMsg());
						TSYSfaultType fault = customDataResp.getFaults();
						List<TSYSfault> lfaulta =fault.getFault();
						for (TSYSfault sfault : lfaulta) {
							logger.info(sfault.getStatus()+" "+ sfault.getFaultDesc());
						}
					}else{
						if(customDataResp.getCustomData()!= null){
							if(customDataResp.getCustomData().getValue().getCode61()!= null){
								String strFchVenProgCero =customDataResp.getCustomData().getValue().getCode61().getValue();
								logger.info("fecha vencimiento prog cero "+strFchVenProgCero);
								SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");
								Date dateFchVen=null;
								try {
									dateFchVen = sdf.parse(strFchVenProgCero);
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								Calendar calFchVen = Calendar.getInstance();
								calFchVen.setTime(dateFchVen);
								
								if(!hoy.after(calFchVen)){
									logger.info("tien prog cero");
									prog0=true;
									//15 dias o menos desde que contrato el programa cero?
									if(customDataResp.getCustomData().getValue().getCode81()!= null){
										String strFchInProgCero =customDataResp.getCustomData().getValue().getCode81().getValue();
										logger.info("fecha in prog cero "+strFchInProgCero);
										Date dateFchIn=null;
										try {
											dateFchIn = sdf.parse(strFchInProgCero);
										} catch (ParseException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										Calendar fechaInProgCero = Calendar.getInstance();
										fechaInProgCero.setTime(dateFchIn);
										long diasDesdeQueTieneProgCero=(hoy.getTimeInMillis()-fechaInProgCero.getTimeInMillis())/(1000*60*60*24);
										if(diasDesdeQueTieneProgCero<=15){
											//numdias es igual al numero de dias
											numDiasProg0=(int) (diasDesdeQueTieneProgCero+2);
										}
										
									}
									
								}
							}
						}
						
					}
					//fin prog0
					//inciia cmapañas compras
					//compras Extr
					//trae campania compras internacionales para no pro cero
					List<Campania> campExt=campaniaService.getCampaniaByTipo("campania.ext.ts2");
					
					List<Double> montosExt=new ArrayList<Double>();
					for (Campania camp : campExt) {
						Set<Promocion> setPromos=camp.getPromociones();
						for (Promocion promocion : setPromos) {
							montosExt.add(promocion.getMonto());
						}
					}
					Collections.sort(montosExt);
					
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
					GregorianCalendar  antes = new GregorianCalendar();
					antes.set(Calendar.HOUR_OF_DAY, 0);
					antes.set(Calendar.MINUTE, 0);
					antes.set(Calendar.SECOND, 0);
					antes.set(Calendar.MILLISECOND, 0);
					

					antes.add(Calendar.DAY_OF_YEAR, -numDiasCampExt);
					logger.info("antes "+antes.getTime());
					InqTrans inqTrans = new InqTrans();
					InqTransRequestType reqInqTrans = new InqTransRequestType();

					reqInqTrans.setOnlyForeign(false);
					reqInqTrans.setOnlyCurr(true);
					reqInqTrans.setVersion("1.9.0");
					reqInqTrans.setKey(cuenta);
					reqInqTrans.setKeyType("cardNbr");
					reqInqTrans.setPageItems(300);
					inqTrans.setInqTransRequest(reqInqTrans);
					InqTransResponseType respExt =cts2.inqTrans(tp, inqTrans).getInqTransResult();
					logger.info("InqTrans 1 Obtener compras ext corte actual");
					if(!"000".equals( respExt.getStatus())){
						logger.info( respExt.getStatusMsg());
						TSYSfaultType fault = respExt.getFaults();
						List<TSYSfault> lfaulta =fault.getFault();
						for (TSYSfault sfault : lfaulta) {
							logger.info(sfault.getStatus()+" "+ sfault.getFaultDesc());
						}
					}
					List<Compra> compras = new ArrayList<Compra>();
					logger.info("Compras extranjero corte actual");
					for (ITRtranDetailResponseDataType td : respExt.getTranDetail()) {
						logger.info("CT "+td.getTranCode() + " monto "+td.getAmtTran().getValue().getValue().doubleValue()+" tran date "+td.getDateTran().getValue().getValue() +" desc "+td.getMerchantInfo().getValue().getDBAName().getValue());
						if("3001".equals(td.getTranCode()) || "1001".equals(td.getTranCode())){
							if(montoMinCompras<= td.getAmtTran().getValue().getValue().doubleValue()){
								if((td.getDateTran().getValue().getValue().toGregorianCalendar().after(antes) || td.getDateTran().getValue().getValue().toGregorianCalendar().compareTo(antes)==0) ){
									logger.info("agregar compra");
									Compra compra = new Compra();
									compra.setCuenta(cuenta);
									compra.setCodigoTransaccion(Integer.parseInt(td.getTranCode()));
									compra.setFechaCompra(td.getDateTran().getValue().getValue().toGregorianCalendar().getTime());
									compra.setMonto(td.getAmtTran().getValue().getValue().doubleValue());
									compra.setDescripcion(td.getMerchantInfo().getValue().getDBAName().getValue());
									compra.setNumRefTran(td.getRefNbr().getValue());
									compra.setTipoTransaccion("ITA");
									compra.setIdEdoPromocion(compraService.getStatusCompraItau(compra));
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

					InqTransRequestType reqInqTrans2 = new InqTransRequestType();
					InqTrans igrala = new InqTrans();
					reqInqTrans2.setVersion("1.9.0");
					reqInqTrans2.setKey(cuenta);
					reqInqTrans2.setKeyType("cardNbr");
					reqInqTrans2.setPageItems(300);
					com.tsys.xmlmessaging.ch.InqTransRequestType.CycleRange cycleRange = new  com.tsys.xmlmessaging.ch.InqTransRequestType.CycleRange();
					XMLGregorianCalendar calendarIn;
					try {
						calendarIn = DatatypeFactory.newInstance().newXMLGregorianCalendar(antes);
						cycleRange.setFrom(calendarIn);
					} catch (DatatypeConfigurationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					XMLGregorianCalendar calendarFin;
					try {
						calendarFin = DatatypeFactory.newInstance().newXMLGregorianCalendar(hoy);
						cycleRange.setTo(calendarFin);     
					} catch (DatatypeConfigurationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					     
					reqInqTrans2.setCycleRange(cycleRange);
					reqInqTrans2.setOnlyForeign(false);
					igrala.setInqTransRequest(reqInqTrans2);

					InqTransResponse resp= cts2.inqTrans(tp, igrala);
					logger.info("Compras ext corte anterior");
					for (ITRtranDetailResponseDataType td : resp.getInqTransResult().getTranDetail()) {
						logger.info("CT "+td.getTranCode() + " monto "+td.getAmtTran().getValue().getValue().doubleValue()+" tran date "+td.getDateTran().getValue().getValue() +" desc "+td.getMerchantInfo().getValue().getDBAName().getValue());

						if("3001".equals(td.getTranCode()) || "1001".equals(td.getTranCode()) ){
							if(montoMinCompras<= td.getAmtTran().getValue().getValue().doubleValue()){
								logger.info("fecha compra "+td.getDateTran().getValue().getValue().toGregorianCalendar().getTime());
								if((td.getDateTran().getValue().getValue().toGregorianCalendar().after(antes) || td.getDateTran().getValue().getValue().toGregorianCalendar().compareTo(antes)==0) ){
									logger.info("agregar compra");
									Compra compra = new Compra();
									compra.setCuenta(cuenta);
									compra.setCodigoTransaccion(Integer.parseInt(td.getTranCode()));
									compra.setFechaCompra(td.getDateTran().getValue().getValue().toGregorianCalendar().getTime());
									compra.setMonto(td.getAmtTran().getValue().getValue().doubleValue());
									compra.setDescripcion(td.getMerchantInfo().getValue().getDBAName().getValue());
									compra.setNumRefTran(td.getRefNbr().getValue());
									compra.setTipoTransaccion("IPS");
									compra.setIdEdoPromocion(compraService.getStatusCompraItau(compra));

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
				
					List<Double> montos=new ArrayList<Double>();
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
						for (Campania campania : campProgCero) {
							
							numDiasProg0=campania.getNumMaxDiasRegistro();
							
							promosProg0= campania.getPromociones();


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
						

						//obternr compras prog 0
						antes = new GregorianCalendar();
						antes.set(Calendar.HOUR_OF_DAY, 0);
						antes.set(Calendar.MINUTE, 0);
						antes.set(Calendar.SECOND, 0);
						antes.set(Calendar.MILLISECOND, 0);
						

						antes.add(Calendar.DAY_OF_YEAR, -numDiasProg0);
						logger.info("antes "+antes.getTime());
						inqTrans = new InqTrans();
						reqInqTrans = new InqTransRequestType();

						
						reqInqTrans.setOnlyCurr(true);
						reqInqTrans.setVersion("1.9.0");
						reqInqTrans.setKey(cuenta);
						reqInqTrans.setKeyType("cardNbr");
						reqInqTrans.setPageItems(300);
						inqTrans.setInqTransRequest(reqInqTrans);
						InqTransResponseType respPro0 =cts2.inqTrans(tp, inqTrans).getInqTransResult();
						logger.info("InqTrans 1 Obtener compras prog cero corte actual");
						if("999".equals( respPro0.getStatus())){
							logger.info( respPro0.getStatusMsg());
							TSYSfaultType fault = respPro0.getFaults();
							List<TSYSfault> lfaulta =fault.getFault();
							for (TSYSfault sfault : lfaulta) {
								logger.info(sfault.getStatus()+" "+ sfault.getFaultDesc());
							}
						}
						//compras = new ArrayList<Compra>();
						logger.info("Compras nal corte actual prog 0");
						for (ITRtranDetailResponseDataType td : respPro0.getTranDetail()) {
							logger.info("CT "+td.getTranCode() + " monto "+td.getAmtTran().getValue().getValue().doubleValue()+" tran date "+td.getDateTran().getValue().getValue() +" desc "+td.getMerchantInfo().getValue().getDBAName().getValue());
							if("7146".equals(td.getTranCode()) ){
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
														logger.info("fecha compra "+calFechaCompra.getTime() +" fecha in "+calFechaIn.getTime() +" fecha fin "+ calFechaFin.getTime() );
														if(calFechaCompra.compareTo(calFechaIn)==0 || calFechaCompra.compareTo(calFechaFin)==0||(calFechaCompra.after(calFechaIn) && calFechaCompra.before(calFechaFin))){
															logger.info("en tra en promo masiva");
															for(Promocion promoMas:camp.getPromociones()){
																 logger.info("promoMas progCero "+promoMas.getProgramaCero());
																if("si".equalsIgnoreCase(promoMas.getProgramaCero())){
																	logger.info("se agrega promo al combo camp masiva "+promoMas.getDescripcion());
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
						
						
						 reqInqTrans2 = new InqTransRequestType();
						 igrala = new InqTrans();
		                 reqInqTrans2.setVersion("1.9.0");
		                 reqInqTrans2.setKey(cuenta);
		                 reqInqTrans2.setKeyType("cardNbr");
		                 reqInqTrans2.setPageItems(300);
							cycleRange = new  com.tsys.xmlmessaging.ch.InqTransRequestType.CycleRange();
							try {
								calendarIn = DatatypeFactory.newInstance().newXMLGregorianCalendar(antes);
								cycleRange.setFrom(calendarIn);
							} catch (DatatypeConfigurationException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							try {
								calendarFin = DatatypeFactory.newInstance().newXMLGregorianCalendar(hoy);
								cycleRange.setTo(calendarFin);  
							} catch (DatatypeConfigurationException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							        
						reqInqTrans2.setCycleRange(cycleRange);
		                 igrala.setInqTransRequest(reqInqTrans2);
		                 
		                 resp= cts2.inqTrans(tp, igrala);
		                 logger.info("Compras prog 0 corte actual");
		                 for (ITRtranDetailResponseDataType td : resp.getInqTransResult().getTranDetail()) {
		                	 logger.info("CT "+td.getTranCode() + " monto "+td.getAmtTran().getValue().getValue().doubleValue()+" tran date "+td.getDateTran().getValue().getValue() +" desc "+td.getMerchantInfo().getValue().getDBAName().getValue());

		                	 if("7146".equals(td.getTranCode())){
		                		 if(montoMinCompras<= td.getAmtTran().getValue().getValue().doubleValue()){
		                			 logger.info("fecha compra "+td.getDateTran().getValue().getValue().toGregorianCalendar().getTime());
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
		                								 logger.info("fecha compra "+calFechaCompra.getTime() +" fecha in "+calFechaIn.getTime() +" fecha fin "+ calFechaFin.getTime() );
		                									if(calFechaCompra.compareTo(calFechaIn)==0 || calFechaCompra.compareTo(calFechaFin)==0||(calFechaCompra.after(calFechaIn) && calFechaCompra.before(calFechaFin))){
		                									 logger.info("en tra en promo masiva");
		                									 for(Promocion promoMas:camp.getPromociones()){
		                										 logger.info("promoMas progCero "+promoMas.getProgramaCero());
		                										 if("si".equalsIgnoreCase(promoMas.getProgramaCero())){
		                											 logger.info("se agrega promo al combo camp masiva "+promoMas.getDescripcion());
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
					
//					else{
//						
//
//
//						//obtener compras ita nacionales y extranjeras
//						for (Campania camp : lcampMasivas) {
//							logger.info("camp activa "+camp.getNombre());
//							fechaIn= camp.getFechaInicial();
//							fechaFin= camp.getFechaFinal();
//							Calendar calIn= Calendar.getInstance();
//							calIn.setTime(fechaIn);
//							Calendar calFin = Calendar.getInstance();
//							calFin.setTime(fechaFin);
//
//							InqTransRequestType reqInqTrans4 = new InqTransRequestType();
//							reqInqTrans4.setVersion("1.9.0");
//							reqInqTrans4.setKey(cuenta);
//							reqInqTrans4.setKeyType("cardNbr");
//							reqInqTrans4.setPageItems(300);
//							reqInqTrans4.setOnlyCurr(true);
//							InqTrans inqTrans4 = new InqTrans();
//							inqTrans4.setInqTransRequest(reqInqTrans4);
//							InqTransResponse respInqTrans4= cts2.inqTrans(tp, inqTrans4);
//
//
//
//							if(!"000".equals( respInqTrans4.getInqTransResult().getStatus())){
//								logger.info( respInqTrans4.getInqTransResult().getStatusMsg());
//								TSYSfaultType fault = respInqTrans4.getInqTransResult().getFaults();
//								List<TSYSfault> lfaulta =fault.getFault();
//								for (TSYSfault sfault : lfaulta) {
//									logger.info(sfault.getStatus()+" "+ sfault.getFaultDesc());
//								}
//							}
//
//							montos.clear();
//							Set<Promocion> promos = camp.getPromociones();
//							for (Promocion promo : promos) {
//								montos.add(promo.getMonto());
//								//montoMinCompras = promo.getMonto();
//								//break;
//							}
//							if (!montos.isEmpty()) {
//								Collections.sort(montos);
//								for (Promocion promo : promos) {
//
//									montoMinCompras = promo.getMonto();
//									break;
//								}
//							}
//							
//							
//							for (ITRtranDetailResponseDataType td : respInqTrans4.getInqTransResult().getTranDetail()) {
//								logger.info("CT "+td.getTranCode() + " monto "+td.getAmtTran().getValue().getValue().doubleValue()+" tran date "+td.getDateTran().getValue().getValue() +" desc "+td.getMerchantInfo().getValue().getDBAName().getValue());
//
//
//								if("7146".equals(td.getTranCode()) ){
//									Compra compra = new Compra();
//									compra.setCuenta(cuenta);
//									compra.setCodigoTransaccion(Integer.parseInt(td.getTranCode()));
//									compra.setFechaCompra(td.getDateTran().getValue().getValue().toGregorianCalendar().getTime());
//									compra.setMonto(td.getAmtTran().getValue().getValue().doubleValue());
//									compra.setDescripcion(td.getMerchantInfo().getValue().getDBAName().getValue());
//									compra.setNumRefTran(td.getRefNbr().getValue());
//									compra.setTipoTransaccion("ITA");
//									compra.setIdEdoPromocion(compraService.getStatusCompraItau(compra));
//									for (Campania cam : lcampMasivas) {
//										//obtener rango fecha
//										fechaIn= cam.getFechaInicial();
//										fechaFin= cam.getFechaFinal();
//										Calendar calIn2= Calendar.getInstance();
//										calIn2.setTime(fechaIn);
//										Calendar calFin2 = Calendar.getInstance();
//										calFin2.setTime(fechaFin);
//										//ver si esta en rango
//										if(compra.getMonto().doubleValue()>= montoMinCompras && (td.getDateTran().getValue().getValue().toGregorianCalendar().after(calIn2) || td.getDateTran().getValue().getValue().toGregorianCalendar().compareTo(calIn2)==0) 
//												&& (td.getDateTran().getValue().getValue().toGregorianCalendar().before(calFin2) || td.getDateTran().getValue().getValue().toGregorianCalendar().compareTo(calFin2)==0)){
//											//obtener promos
//											for (Promocion promo : cam.getPromociones()) {
//												if("no".equalsIgnoreCase(promo.getProgramaCero()) && compra.getMonto().doubleValue()>= promo.getMonto().doubleValue()){
//													compra.addComboPromo(promo.getPlazoMeses()+" "+promo.getDescripcion(), promo);
//												}
//											}
//											compras.add(compra);
//										}
//
//									}
//
//
//								}
//
//
//							}
//
//
//							InqTransRequestType reqInqTrans3 = new InqTransRequestType();
//							InqTrans igrala2 = new InqTrans();
//
//							reqInqTrans3.setVersion("1.9.0");
//							reqInqTrans3.setKey(cuenta);
//							reqInqTrans3.setKeyType("cardNbr");
//
//							com.tsys.xmlmessaging.ch.InqTransRequestType.CycleRange cycleRange2 = new  com.tsys.xmlmessaging.ch.InqTransRequestType.CycleRange();
//							XMLGregorianCalendar calendarIn2=null;
//							try {
//								calendarIn2 = DatatypeFactory.newInstance().newXMLGregorianCalendar();
//							} catch (DatatypeConfigurationException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//							calendarIn2.setYear(calIn.get(Calendar.YEAR));
//							calendarIn2.setMonth(calIn.get(Calendar.MONTH)+1);
//
//							XMLGregorianCalendar calendarFin2=null;
//							try {
//								calendarFin2 = DatatypeFactory.newInstance().newXMLGregorianCalendar();
//							} catch (DatatypeConfigurationException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//							calendarFin2.setYear(calFin.get(Calendar.YEAR));
//							calendarFin2.setMonth(calFin.get(Calendar.MONTH)+1);
//
//							cycleRange2.setFrom(calendarIn2);
//							cycleRange2.setTo(calendarFin2);          
//							reqInqTrans3.setCycleRange(cycleRange2);
//							reqInqTrans3.setOnlyForeign(false);
//							reqInqTrans3.setPageItems(300);
//							igrala2.setInqTransRequest(reqInqTrans3);
//
//							InqTransResponse respInqTrans= cts2.inqTrans(tp, igrala2);
//							logger.info("InqTrans 3");
//							if(!"000".equals( respInqTrans.getInqTransResult().getStatus())){
//								logger.info( respInqTrans.getInqTransResult().getStatusMsg());
//								TSYSfaultType fault = respInqTrans.getInqTransResult().getFaults();
//								List<TSYSfault> lfaulta =fault.getFault();
//								for (TSYSfault sfault : lfaulta) {
//									logger.info(sfault.getStatus()+" "+ sfault.getFaultDesc());
//								}
//							}
//
//							//obtener nacionales ips
//							logger.info("comrpas nacionales corte anterior");
//							for (ITRtranDetailResponseDataType td : respInqTrans.getInqTransResult().getTranDetail()) {
//								logger.info("CT "+td.getTranCode() + " monto "+td.getAmtTran().getValue().getValue().doubleValue()+" tran date "+td.getDateTran().getValue().getValue() +" desc "+td.getMerchantInfo().getValue().getDBAName().getValue());
//
//
//								if("7146".equals(td.getTranCode()) ){
//									Compra compra = new Compra();
//									compra.setCuenta(cuenta);
//									compra.setCodigoTransaccion(Integer.parseInt(td.getTranCode()));
//									compra.setFechaCompra(td.getDateTran().getValue().getValue().toGregorianCalendar().getTime());
//									compra.setMonto(td.getAmtTran().getValue().getValue().doubleValue());
//									compra.setDescripcion(td.getMerchantInfo().getValue().getDBAName().getValue());
//									compra.setNumRefTran(td.getRefNbr().getValue());
//									compra.setTipoTransaccion("IPS");
//									compra.setIdEdoPromocion(compraService.getStatusCompraItau(compra));
//									for (Campania cam : lcampMasivas) {
//										//obtener rango fecha
//										fechaIn= cam.getFechaInicial();
//										fechaFin= cam.getFechaFinal();
//										Calendar calIn2= Calendar.getInstance();
//										calIn2.setTime(fechaIn);
//										Calendar calFin2 = Calendar.getInstance();
//										calFin2.setTime(fechaFin);
//										//ver si esta en rango
//										if(compra.getMonto().doubleValue()>= montoMinCompras && (td.getDateTran().getValue().getValue().toGregorianCalendar().after(calIn2) || td.getDateTran().getValue().getValue().toGregorianCalendar().compareTo(calIn2)==0) 
//												&& (td.getDateTran().getValue().getValue().toGregorianCalendar().before(calFin2) || td.getDateTran().getValue().getValue().toGregorianCalendar().compareTo(calFin2)==0)){
//											//obtener promos
//											for (Promocion promo : cam.getPromociones()) {
//												if("no".equalsIgnoreCase(promo.getProgramaCero()) && compra.getMonto().doubleValue()>= promo.getMonto().doubleValue()){
//													compra.addComboPromo(promo.getPlazoMeses()+" "+promo.getDescripcion(), promo);
//												}
//											}
//											compras.add(compra);
//										}
//
//									}
//
//
//								}
//
//
//							}
//
//
//
//						}//fin fot
//					}//fin if prog cero
						Set<Compra> setCompras = new HashSet<Compra>();
						setCompras.addAll(compras);
						compras= new ArrayList<Compra>();
						compras.addAll(setCompras);
						logger.info("compras "+compras.size());
						for (Compra compra : compras) {
							logger.info(compra.getCodigoTransaccion()+""+compra.getFechaCompra()+ " "+compra.getMonto()+ " "+compra.getMonto());
						}

						List<CompraWSDTO> lcomprasDto= new ArrayList<CompraWSDTO>();
						for (Compra compra : setCompras) {
							lcomprasDto.add(MSIHelper.getCompraWSDTO(compra));
						}
						comprasWSRespDTO.setCompras(lcomprasDto);
			}else{
		//obtener si es prog 0
		Parametro param= parametroService.getParamById(MSIConstants.TSYSWS_ENDPOINT);
		ClientLSWS cliLSWS = new ClientLSWS(param.getValor());
		ResultadoIGBINAEnca infoCliente= null;
		try {
			infoCliente= cliLSWS.getResultadoIGBINAEnca(cuenta, MSIConstants.TSYSWS_SECURITY_USERNAME,MSIConstants.TSYSWS_ACCESS_KEY);
			if(infoCliente== null){
				comprasWSRespDTO.setStatus(3);
				comprasWSRespDTO.setMsgError("La cuenta no existe");
				return comprasWSRespDTO;
			}
			String tipoCuenta =infoCliente.getInfoGeneralTHIGB().getTipoCuenta();		
			if("SCA".equalsIgnoreCase(tipoCuenta.trim())){
				comprasWSRespDTO.setStatus(4);
				comprasWSRespDTO.setMsgError("No se permite usar cuentas adicionales");
				return comprasWSRespDTO;
			}
			//obtener CBA
			String cuentaCBA=null;
			if(!"ICA".equalsIgnoreCase(tipoCuenta.trim())){
				if("PCA".equalsIgnoreCase(tipoCuenta.trim())){
					//usar los datos de la cba
					cuentaCBA=infoCliente.getInfoGeneralTHIGB().getCuentaCBA();
					infoCliente= cliLSWS.getResultadoIGBINAEnca(cuentaCBA, MSIConstants.TSYSWS_SECURITY_USERNAME,MSIConstants.TSYSWS_ACCESS_KEY);
					
				}else if("CBA".equalsIgnoreCase(tipoCuenta.trim())){
					cuentaCBA =cuenta;
					//obtiene la pca
					cuenta = infoCliente.getInfoGeneralTHIGB().getCuentaCBA();
				}
				
			}else{
				cuentaCBA=cuenta;
			}
			
			//obtener saldoRev y salDoRev anterior
			cliLSWS.setEndPoint(parametroService.getParamById(MSIConstants.TSYSWS_SI02_ENDPOINT).getValor());
			double saldoRev=0;
			double saldoRevAnterior=0;
			
				SI02FinalResponseDTO respSI02 = cliLSWS.obtenerSaldoRevolvente(cuentaCBA);
				if(respSI02.getResponseDTO1().getCURR_PUR_BAL_OS()!= null && !respSI02.getResponseDTO1().getCURR_PUR_BAL_OS().isEmpty())
					saldoRev = new Double(respSI02.getResponseDTO1().getCURR_PUR_BAL_OS());
				if(respSI02.getResponseDTO1().getONE_CYC_OLDPUR_BAL_OS()!= null  && !respSI02.getResponseDTO1().getONE_CYC_OLDPUR_BAL_OS().isEmpty())
					saldoRevAnterior = new Double(respSI02.getResponseDTO1().getONE_CYC_OLDPUR_BAL_OS());
			
			ArrayUDataDTO[] arrUData=infoCliente.getInfoGeneralTHIGB().getUData();
			//5.- validar si el cliente es programa cero
			String ucode3= arrUData[2].getToString();
			boolean prog0=false; 
			if(ucode3 != null 
					&& ucode3.length()>0 ){
					
			if( 'G'==ucode3.charAt(3)){
				
				prog0=true;
			}else{
				
				prog0=false;
			}
			}
			
			//traer compras cuenta
			DetachedCriteria criteria = DetachedCriteria.forClass(Compra.class);
			criteria.add(Restrictions.eq("cuenta", cuenta));
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			Date fromDate = calendar.getTime();
			criteria.add(Restrictions.ge("fechaAplicacionPromocion",fromDate));
			java.util.List<Compra> compras = compraService.findByCriteria(criteria);
			Double montoComprasHoyITA=0.0;
			Double montoComprasHoyIPS=0.0;
			for (Compra compra : compras) {
				if("ITA".equals(compra.getTipoTransaccion())){
					montoComprasHoyITA+=compra.getMonto();
				}else if("IPS".equals(compra.getTipoTransaccion())){
					montoComprasHoyIPS+=compra.getMonto();
				}
			}
			
			if(montoComprasHoyITA!= null){
				saldoRev-=montoComprasHoyITA;
			}
			
			
			if(montoComprasHoyIPS!= null){
			
				saldoRevAnterior-=montoComprasHoyIPS;
			}
			comprasWSRespDTO.setSaldoRevActual(saldoRev);
			comprasWSRespDTO.setSaldoCorteAnterior(saldoRevAnterior);
			
			ClienteGetMovs movclient = new ClienteGetMovs(parametroService.getParamById(MSIConstants.SERVICIOS_MSIMOVS_ENDPOINT).getValor());
			List<Compra> comprasExt =comprasExt(cuenta,cuentaCBA,movclient,prog0);
			List<Compra> comprasCamSeg= comprasCampSeg(cuenta, cuentaCBA, movclient);
			List<Compra> comprasComercios = comprasCampComercios(cuenta, cuentaCBA, movclient);
			Date diahoy = new Date();
			
			List<Campania> lcampMasivas=campaniaService.getCampaniaByParams(diahoy,diahoy,"masiva",null);
			if(lcampMasivas!= null && !lcampMasivas.isEmpty()){
				logger.info("tamMasivas "+lcampMasivas.size());
				Set<Campania>  setMasivas = new HashSet<Campania>(lcampMasivas);
				logger.info("setMasivas "+setMasivas.size());
				lcampMasivas = new ArrayList<Campania>(setMasivas);
			}
			List<Compra> comprasNacionales=null;
			if(prog0){
						comprasNacionales = comprasNacionalesProg0(cuenta, cuentaCBA, movclient,lcampMasivas);
							
			}else{
						//comprasNacionales =comprasNalCampMasivas(cuenta, cuentaCBA, movclient, lcampMasivas);
			}
			Set<Compra> setCompras = new HashSet<Compra>();
			setCompras.addAll(comprasCamSeg);
			setCompras.addAll(comprasComercios);
			setCompras.addAll(comprasExt);
			if(comprasNacionales!= null)
				setCompras.addAll(comprasNacionales);
			if(setCompras.isEmpty()){
				comprasWSRespDTO.setStatus(5);
				comprasWSRespDTO.setMsgError("No se encontraron compras");
				return comprasWSRespDTO;
				}
			compras.clear();
			compras.addAll(setCompras);
			
		
			
			List<CompraWSDTO> lcomprasDto= new ArrayList<CompraWSDTO>();
			for (Compra compra : setCompras) {
				lcomprasDto.add(MSIHelper.getCompraWSDTO(compra));
			}
			comprasWSRespDTO.setCompras(lcomprasDto);
		} catch (Exception e) {
			
			e.printStackTrace();
			comprasWSRespDTO.setStatus(6);
			comprasWSRespDTO.setMsgError("ERROR "+e.getMessage());
			return comprasWSRespDTO;
			//return null;
		}
	}//fin else
		return comprasWSRespDTO;
	}
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	
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
			
			e.printStackTrace();
		} catch (ServiceException e) {
			
			e.printStackTrace();
		}
		if(movs!= null){
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
	
	private List<Compra> comprasCampComercios(String cuenta,String cuentaCBA,ClienteGetMovs movclient){
		Date diahoy = new Date();
		List<Campania> campComercios = campaniaService.getCampaniaByParams(diahoy,diahoy, "comercio", null);
		List<Compra> compras = new ArrayList<Compra>();
		List<Double> montos=null;
	
		for (Campania campania : campComercios) {
			montos= new ArrayList<Double>();
			
			Set<Promocion> promosCamp= campania.getPromociones();
			
			for (Promocion promo : promosCamp) {
				if(promo.getMonto()!= null){
					
					montos.add(promo.getMonto());
				}
				
			}
			
			Set<Comercio> comercios=campania.getComercios();
			Collections.sort(montos);
			for (Comercio comercio : comercios) {
				String[] codigos;
				if(campania.getCodigoTransaccion2()!= null){
		 			codigos= new String[]{campania.getCodigoTransaccion1(),campania.getCodigoTransaccion2()};
		 		}else{
		 			codigos= new String[]{campania.getCodigoTransaccion1().toString()};
		 		}
				
					String[] movs=null;
					try {
						movs = movclient.getMovsCampComercios(sdf.format(campania.getFechaInicial()),sdf.format(campania.getFechaMaximaRegistro()), cuentaCBA, codigos, montos.get(0), comercio.getNombre());
					} catch (RemoteException e) {
						
						e.printStackTrace();
					} catch (ServiceException e) {
						
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

	private List<Compra> comprasNalCampMasivas(String cuenta,String cuentaCBA,ClienteGetMovs movclient,List<Campania> campMasivas){
		List<Compra> compras = new ArrayList<Compra>();
		Date fechaIn= null;
		Date fechaFin= null;
		List<Double> montos=null;
		//pora cada campania obtener sus compras respectivas
		for (Campania camp : campMasivas) {
			
			montos= new ArrayList<Double>();
			
			fechaIn= camp.getFechaInicial();
			fechaFin= camp.getFechaFinal();
			String[] movs= null;
			
			//si hay alguna cmapania acutalmente te traes todas las compras
			if(fechaIn!= null && fechaFin != null){
				//Map<String,Promocion> promosMeses= new HashMap<String,Promocion>();
				for (Promocion promo : camp.getPromociones()) {
					if(promo.getMonto()!= null){
						montos.add(promo.getMonto());
					}

				}
				Collections.sort(montos);
			
				 try {
					movs = movclient.webMovEdoCtaFech(sdf.format(fechaIn),sdf.format(fechaFin), cuentaCBA, 
							new String[]{MSIConstants.TRANS_CODIGO_COMPRAS_NACIONALES},montos.get(0));
				 } catch (RemoteException e) {
						
						e.printStackTrace();
					} catch (ServiceException e) {
						
						e.printStackTrace();
					}
				 if(movs!= null){
					 for (String strMov : movs) {
							Compra compra=MSIHelper.fromStringToCompra(strMov);
							compra.setCuenta(cuenta);
							compra.setCuentaFacturadora(cuentaCBA);
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
					
					e.printStackTrace();
				} catch (ServiceException e) {
					
					e.printStackTrace();
				}
			if(movs!= null){	
				for (String strMov : movs) {
					Compra compra=MSIHelper.fromStringToCompra(strMov);
					compra.setCuenta(cuenta);
					compra.setCuentaFacturadora(cuentaCBA);
				
					
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
						
						for (Promocion promo : campSeg.getPromociones()) {
							if(promo.getMonto()!= null){
								montos.add(promo.getMonto());
							}
						}
						
						Collections.sort(montos);
							
							String[] movs=null;
							try {
								movs = movclient.webMovEdoCtaFech(sdf.format(archivoDetalle.getFechaInicio()),sdf.format(archivoDetalle.getFechaFin()), cuentaCBA, 
										new String[]{campSeg.getCodigoTransaccion1().toString()},montos.get(0));
							} catch (RemoteException e) {
								
								e.printStackTrace();
							} catch (ServiceException e) {
								
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

	
}
