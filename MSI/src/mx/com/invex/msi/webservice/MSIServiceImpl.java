package mx.com.invex.msi.webservice;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mx.com.interware.spira.ls.facade.igbinaenca.ResultadoIGBINAEnca;
import mx.com.interware.spira.tsysws.si02.SI02FinalResponseDTO;
import mx.com.invex.msi.model.Compra;
import mx.com.invex.msi.model.Parametro;
import mx.com.invex.msi.service.CompraService;
import mx.com.invex.msi.service.ParametroService;
import mx.com.invex.msi.util.MSIConstants;
import mx.com.invex.msi.ws.ClientLSWS;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/PPNGIService")
public class MSIServiceImpl{

	@Autowired
	CompraService compraService;
	
	@Autowired
	ParametroService parametroService;
	
	private static final Logger logger = Logger.getLogger(MSIServiceImpl.class);
	
	@RequestMapping(value = "/{cuenta}", method = RequestMethod.GET)
	@ResponseBody
	public String getSPNGI(@PathVariable("cuenta") String cuenta) {
		Parametro param= parametroService.getParamById(MSIConstants.TSYSWS_ENDPOINT);
		ClientLSWS cliLSWS = new ClientLSWS(param.getValor());
		ResultadoIGBINAEnca infoCliente= null;
		double saldoNoIntereses=0.0;
		
		try {
			logger.info("cuenta "+cuenta);
			
			infoCliente= cliLSWS.getResultadoIGBINAEnca(cuenta, MSIConstants.TSYSWS_SECURITY_USERNAME,MSIConstants.TSYSWS_ACCESS_KEY);
			
			String tipoCuenta =infoCliente.getInfoGeneralTHIGB().getTipoCuenta();
			
			logger.info(" tipo cuenta "+tipoCuenta);
			if("PCA".equalsIgnoreCase(tipoCuenta.trim())){
				cuenta=infoCliente.getInfoGeneralTHIGB().getCuentaCBA();
				
				logger.info("cuenta cba "+cuenta);
			}
			
//			if("ICA".equalsIgnoreCase(tipoCuenta.trim()) || "CBA".equalsIgnoreCase(tipoCuenta.trim())){
//				cuentaCBA=cuenta;
//				if("CBA".equalsIgnoreCase(tipoCuenta.trim())){
//					cuenta = infoCliente.getInfoGeneralTHIGB().getCuentaCBA();		
//				}
//			}else{
//				cuentaCBA=infoCliente.getInfoGeneralTHIGB().getCuentaCBA();
//			}
			logger.info("nueva cuenta "+cuenta);
			ClientLSWS clienteSI02= new ClientLSWS();
			clienteSI02.setEndPoint(parametroService.getParamById(MSIConstants.TSYSWS_SI02_ENDPOINT).getValor());
			SI02FinalResponseDTO respSI02 = clienteSI02.obtenerSaldoRevolvente(cuenta);
			
			String diasAgregados = respSI02.getResponseDTO1().getAGGR_NUM_DAYS_BILL();
			logger.info("dias agregados "+diasAgregados);
			if(diasAgregados!= null && !diasAgregados.isEmpty()){
				
				if("0".equals(diasAgregados)){
					if("PCA".equalsIgnoreCase(tipoCuenta.trim())){
						infoCliente= cliLSWS.getResultadoIGBINAEnca(cuenta, MSIConstants.TSYSWS_SECURITY_USERNAME,MSIConstants.TSYSWS_ACCESS_KEY);
					}
					saldoNoIntereses=Double.parseDouble(infoCliente.getInfoEncabezado().getSaldoNoIntereses());
				}else{
					
					if(respSI02.getResponseDTO1().getCUR_PUR_FIN_CHG_OS()!= null && !respSI02.getResponseDTO1().getCUR_PUR_FIN_CHG_OS().isEmpty()){
						saldoNoIntereses+= new Double(respSI02.getResponseDTO1().getCUR_PUR_FIN_CHG_OS());
					}
					if(respSI02.getResponseDTO1().getCURR_CA_FIN_CHG_OS()!= null && !respSI02.getResponseDTO1().getCURR_CA_FIN_CHG_OS().isEmpty()){
						saldoNoIntereses+= new Double(respSI02.getResponseDTO1().getCURR_CA_FIN_CHG_OS());
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
					
					if(respSI02.getResponseDTO1().getTWO_CYC_OLD_CA_BAL_OS()!=null && !respSI02.getResponseDTO1().getTWO_CYC_OLD_CA_BAL_OS().isEmpty()){
						saldoNoIntereses+= new Double(respSI02.getResponseDTO1().getTWO_CYC_OLD_CA_BAL_OS());
					}
					
				}
			}
			
			logger.info("saldo no int por campos tsys" + saldoNoIntereses);
			
//			if(saldoNoIntereses==Double.parseDouble(infoCliente.getInfoEncabezado().getSaldoDia())){
//				saldoNoIntereses=Double.parseDouble(infoCliente.getInfoEncabezado().getSaldoNoIntereses());
//			}
			
			Double montoComprasHoyIPS=0.0;
			DetachedCriteria criteria = DetachedCriteria.forClass(Compra.class);
			criteria.add(Restrictions.eq("cuentaFacturadora", cuenta));
			criteria.add(Restrictions.eq("tipoTransaccion", "IPS"));
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			Date fromDate = calendar.getTime();
			criteria.add(Restrictions.ge("fechaAplicacionPromocion",fromDate));
			List<Compra> compras =compraService.findByCriteria(criteria);
			for (Compra compra : compras) {
				
					montoComprasHoyIPS+=compra.getMonto();
				
			}
			logger.info("montoCompras ips hoy" + montoComprasHoyIPS);
			if(montoComprasHoyIPS!= null){
				saldoNoIntereses -=montoComprasHoyIPS;
			}
			
			logger.info("saldo no int despues de compras ips de hoy "+saldoNoIntereses);
			logger.info("pago min "+infoCliente.getInfoEncabezado().getPagoMinimo());
			
			if(infoCliente.getInfoEncabezado().getPagoMinimo()!=null && !infoCliente.getInfoEncabezado().getPagoMinimo().isEmpty()){
				Double pagoMin = Double.parseDouble(infoCliente.getInfoEncabezado().getPagoMinimo());
				if(saldoNoIntereses < pagoMin.doubleValue()){
					saldoNoIntereses = pagoMin;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		return ""+saldoNoIntereses;
	}

}
