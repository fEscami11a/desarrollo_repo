package mx.com.invex.msi.controller;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tsys.xmlmessaging.ch.IRTretailTransResponseDataType;

import mx.com.invex.msi.model.Compra;
import mx.com.invex.msi.model.InfoEnviadaDTO;
import mx.com.invex.msi.service.CompraService;
import mx.com.invex.msi.util.ComparadorCompraMonto;
import mx.com.invex.msi.util.MSIException;
import mx.com.invex.msi.ws.ClientTS2;

@Component
@Scope("session")
public class ResPromosBean extends MessagesMBean implements Serializable{

	private static final Logger logger = Logger.getLogger(ResPromosBean.class);
	private static final long serialVersionUID = 1L;
	@Autowired
	CompraService compraService;
	
	private List<Compra> compras;
	
	private List<InfoEnviadaDTO> mesesPromos;
	
	private int comprasTot;
	private double montoTotal;
	private double totMensualidad;
	private Double sldoNoInt;
	private Integer folio;
	

	public Integer getFolio() {
		return folio;
	}

	public void setFolio(Integer folio) {
		this.folio = folio;
	}

	public Double getSldoNoInt() {
		return sldoNoInt;
	}

	public void setSldoNoInt(Double sldoNoInt) {
		this.sldoNoInt = sldoNoInt;
	}

	public List<InfoEnviadaDTO> getMesesPromos() {
		return mesesPromos;
	}

	public void setMesesPromos(List<InfoEnviadaDTO> mesesPromos) {
		this.mesesPromos = mesesPromos;
	}

	public int getComprasTot() {
		return comprasTot;
	}

	public void setComprasTot(int comprasTot) {
		this.comprasTot = comprasTot;
	}

	public double getMontoTotal() {
		return montoTotal;
	}

	public void setMontoTotal(double montoTotal) {
		this.montoTotal = montoTotal;
	}

	public double getTotMensualidad() {
		return totMensualidad;
	}

	public void setTotMensualidad(double totMensualidad) {
		this.totMensualidad = totMensualidad;
	}

	public List<Compra> getCompras() {
		return compras;
	}

	public void setCompras(List<Compra> compras) {
		this.compras = compras;
	}

	private String msgAnualidad;
	private String msgICM="",msgICM2="";
	
	
	

	public String getMsgAnualidad() {
		return msgAnualidad;
	}

	public void setMsgAnualidad(String msgAnualidad) {
		this.msgAnualidad = msgAnualidad;
	}

	public String getMsgICM() {
		return msgICM;
	}

	public void setMsgICM(String msgICM) {
		this.msgICM = msgICM;
	}

	public String getMsgICM2() {
		return msgICM2;
	}

	public void setMsgICM2(String msgICM2) {
		this.msgICM2 = msgICM2;
	}

	public String resumenCompras(List<Compra> lcompras,double saldoNoInterese,String fechaLim) throws MSIException{
		logger.info("resumen compras");
		
		
		
		this.sldoNoInt= saldoNoInterese;
		try {
			folio=compraService.getFolio();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//			Calendar cal = Calendar.getInstance();
//			Calendar calLimPago = Calendar.getInstance();
			//sldoNoInt = new Double(saldoNoInt); 
			
				//calLimPago.setTime(sdf.parse(fechaLim));
			
			//boolean antesFechaLim = false;
//			if(cal.before(calLimPago)){
//				antesFechaLim = true;
//			}
			compras = new ArrayList<Compra>();
			for(Compra compra:lcompras){
				if(compra.isSelected() && compra.getPromocion()!= null){
					compra.setFolio(folio);
					logger.info("sldoNoInt" +sldoNoInt+ " montoPromo "+compra.getMontoPromo());
					if("IPS".equalsIgnoreCase(compra.getTipoTransaccion())){
						sldoNoInt -= compra.getMontoPromo();
					}
					compras.add(compra);
				}else if(compra.isSelected() && compra.getPromocion()== null){
					sendErrorMessageToUser("Falta seleccionar promocion para compra");
					return null;
				}
			}
			InfoCuentaBean conBean = (InfoCuentaBean) getContext()
					.getExternalContext().getSessionMap()
					.get("infoCuentaBean");
			
			if(conBean.getInfoEnca().getPagoMinimo()!=null && !conBean.getInfoEnca().getPagoMinimo().isEmpty()){
				Double pagoMin = Double.parseDouble(conBean.getInfoEnca().getPagoMinimo());
				if(sldoNoInt.doubleValue() < pagoMin.doubleValue()){
					sldoNoInt = pagoMin;
				}
			}
			
			if(compras.isEmpty()){
				sendErrorMessageToUser("Favor de seleccionar la(s) compra(s) y sus mensualidades");
				return null;
			}
			
			
			Collections.sort(compras,new ComparadorCompraMonto());
			double montoCompraMenor=compras.get(0).getMonto();
			logger.info("Monto compra pos 0: " + montoCompraMenor);
			if (!compras.isEmpty()) {
				//realizar calculo de ,mensualidades
				//set con los tipos de meses diferentes
				Set<Integer> mesesExistentes = new HashSet<Integer>();
				
				for(Compra compra : compras){
					if(compra.getPromocion()!= null)
						mesesExistentes.add(new Integer(compra.getPromocion().getPlazoMeses()));
				}
			
				 comprasTot =0;
				 montoTotal=0.0;
				 totMensualidad=0.0;
				mesesPromos = new ArrayList<InfoEnviadaDTO>();
				NumberFormat formatter = NumberFormat.getCurrencyInstance(); 
				
				boolean haycompras=false;
				for(Integer tipoMes : mesesExistentes){
					int numElem = 0;
					double monto=0;
					
					for(Compra compra : compras){
						logger.info("tipo mes "+tipoMes);
						Integer plzo = new Integer(compra.getPromocion().getPlazoMeses());
						//caso especial anualidad
						if(compra.getCodigoTransaccion().intValue()==405 ||compra.getCodigoTransaccion().intValue()==129){
							StringBuffer sbAnualidad= new StringBuffer();
							sbAnualidad.append("MSI"+plzo+"M:1 COBRO DE ANUALIDAD POR UN MONTO DE "+formatter.format(compra.getMonto()) 
									+ " Y PAGOS MENSUALES DE " + formatter.format(compra.getMonto()/plzo.intValue()));
							this.msgAnualidad=sbAnualidad.toString();
						}else{
							haycompras=true;
						}
					
						logger.info("plazo "+plzo);
						if(tipoMes.intValue() == plzo.intValue()){
							monto+=compra.getMontoPromo();
							numElem++;
							logger.info("monto "+monto +" numElem "+numElem);
						}
					}
					comprasTot +=numElem;
					montoTotal += monto;
					totMensualidad += monto/tipoMes;
					mesesPromos.add(new InfoEnviadaDTO(tipoMes,numElem,monto));
				}//finf for tipo meses
				logger.info("compras tot "+comprasTot+ " monto "+montoTotal +" mensualidad "+totMensualidad);
			
				
				if(haycompras){
					StringBuffer sbICM = new StringBuffer();
					String userName =FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
					
					sbICM.append("MSI: Se ingresó un total de ".toUpperCase()+comprasTot 
							+" compras con el número de folio ".toUpperCase()+folio
							+" "+userName
							+" "+sdf.format(new Date()));
					this.msgICM = sbICM.toString();
					
//					StringBuffer sbICM2 = new StringBuffer();
//					for (InfoEnviadaDTO infoEnviadaDTO : mesesPromos) {
//						logger.info("monto "+infoEnviadaDTO.getMonto() + "  meses " +infoEnviadaDTO.getMeses());
//						String icm="MSI "+infoEnviadaDTO.getMeses()+"M: "+infoEnviadaDTO.getNumCompras()+" compras por un monto de ".toUpperCase()
//						+formatter.format(infoEnviadaDTO.getMonto())
//						+" y pagos mensuales de ".toUpperCase()
//						+formatter.format(infoEnviadaDTO.getMonto()/infoEnviadaDTO.getMeses())+". SU PAGO PARA NO GENERAR INTERESES SERA DE "+formatter.format(sldoNoInt);
//					
//						logger.info("MSG ICM: "+icm);
//						sbICM2.append(icm);
//			
//					}
//					this.msgICM2=sbICM2.toString();
				}
				
				//ClientLSWS clienteTsysWS= new ClientLSWS(paramService.getParamById(MSIConstants.TSYSWS_ENDPOINT).getValor());
				
				return "ResumenCompras";
			} else {
				sendErrorMessageToUser("No hay compras seleccionadas");
				return null;
			}
			
		} catch (NumberFormatException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			
			throw new MSIException(e.getMessage());
//		} catch (ParseException e) {
//			logger.error(e.getMessage());
//			e.printStackTrace();
//			
//			throw new MSIException(e.getMessage());
		}catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			
			throw new MSIException(e.getMessage());
		}
		
		
	}
	
	public String resumenCompras2(List<Compra> lcompras,String sNoInt) throws MSIException{
		logger.info("resumen compras");
		
		
		double saldoNoInterese= new Double(sNoInt);
		
		try {
			folio=compraService.getFolio();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			HttpSession session = (HttpSession)  getContext().getExternalContext().getSession(false);
			String cuenta= (String) session.getAttribute("cuenta");
			logger.info("cuenta "+cuenta);
			compras = new ArrayList<Compra>();
			for(Compra compra:lcompras){
				//cuenta = compra.getCuenta();
				if(compra.isSelected() && compra.getPromocion()!= null){
					compra.setFolio(folio);
					if ("IPS".equals(compra.getTipoTransaccion())){
						saldoNoInterese -= compra.getMonto();
					}
					compras.add(compra);
				}else if(compra.isSelected() && compra.getPromocion()== null){
					sendErrorMessageToUser("Falta seleccionar promocion para compra");
					return null;
				}
			}
		
			this.sldoNoInt=saldoNoInterese;
			if(compras.isEmpty()){
				sendErrorMessageToUser("Favor de seleccionar la(s) compra(s) y sus mensualidades");
				return null;
			}
			int promos=0;
			List<IRTretailTransResponseDataType> retailTrans = ClientTS2.getNumPromos(cuenta);
			 if(retailTrans!= null && !retailTrans.isEmpty()){
				 promos += retailTrans.size();
			 }
			 promos+=compras.size();
			 logger.info("num promos "+promos);
			 //num maXIMO de promos
			 if(promos > 90){
				 sendErrorMessageToUser("No se permite tener mas de 90 promociones , tiene seleccionadas "+(promos-90) +" promociones de más.");
					return null;
			 }
			 
			Collections.sort(compras,new ComparadorCompraMonto());
			double montoCompraMenor=compras.get(0).getMonto();
			logger.info("Monto compra pos 0: " + montoCompraMenor);
			if (!compras.isEmpty()) {
				//realizar calculo de ,mensualidades
				//set con los tipos de meses diferentes
				Set<Integer> mesesExistentes = new HashSet<Integer>();
				
				for(Compra compra : compras){
					if(compra.getPromocion()!= null)
						mesesExistentes.add(new Integer(compra.getPromocion().getPlazoMeses()));
				}
			
				 comprasTot =0;
				 montoTotal=0.0;
				 totMensualidad=0.0;
				mesesPromos = new ArrayList<InfoEnviadaDTO>();
				NumberFormat formatter = NumberFormat.getCurrencyInstance(); 
				
				
				for(Integer tipoMes : mesesExistentes){
					int numElem = 0;
					double monto=0;
					
					for(Compra compra : compras){
						logger.info("tipo mes "+tipoMes);
						Integer plzo = new Integer(compra.getPromocion().getPlazoMeses());
						//caso especial anualidad
						
					
						logger.info("plazo "+plzo);
						if(tipoMes.intValue() == plzo.intValue()){
							monto+=compra.getMontoPromo();
							numElem++;
							logger.info("monto "+monto +" numElem "+numElem);
						}
					}
					comprasTot +=numElem;
					montoTotal += monto;
					totMensualidad += monto/tipoMes;
					mesesPromos.add(new InfoEnviadaDTO(tipoMes,numElem,monto));
				}//finf for tipo meses
				logger.info("compras tot "+comprasTot+ " monto "+montoTotal +" mensualidad "+totMensualidad);
				
				
				StringBuffer sbICM = new StringBuffer();
				String userName =FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
				
				sbICM.append("MSI: Se ingresó un total de ".toUpperCase()+comprasTot 
						+" compras con el número de folio ".toUpperCase()+folio
						+" "+userName
						+" "+sdf.format(new Date()));
				this.msgICM = sbICM.toString();
				
				StringBuffer sbICM2 = new StringBuffer();
				for (InfoEnviadaDTO infoEnviadaDTO : mesesPromos) {
					logger.info("monto "+infoEnviadaDTO.getMonto() + "  meses " +infoEnviadaDTO.getMeses());
					String icm="MSI "+infoEnviadaDTO.getMeses()+"M: "+infoEnviadaDTO.getNumCompras()+" compras por un monto de ".toUpperCase()
					+formatter.format(infoEnviadaDTO.getMonto())
					+" y pagos mensuales de ".toUpperCase()
					+formatter.format(infoEnviadaDTO.getMonto()/infoEnviadaDTO.getMeses());
					//+". SU PAGO PARA NO GENERAR INTERESES SERA DE "+formatter.format(sldoNoInt);
				
					logger.info("MSG ICM: "+icm);
					sbICM2.append(icm);
		
				}
				this.msgICM2=sbICM2.toString();
				
				return "ResumenCompras2";
			} else {
				sendErrorMessageToUser("No hay compras seleccionadas");
				return null;
			}
			
		} catch (NumberFormatException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			
			throw new MSIException(e.getMessage());
//		} catch (ParseException e) {
//			logger.error(e.getMessage());
//			e.printStackTrace();
//			
//			throw new MSIException(e.getMessage());
		}catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			
			throw new MSIException(e.getMessage());
		}
		
		
	}

}
