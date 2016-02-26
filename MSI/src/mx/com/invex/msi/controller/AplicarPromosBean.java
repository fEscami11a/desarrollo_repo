package mx.com.invex.msi.controller;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.xml.rpc.ServiceException;

import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tsys.xmlmessaging.ch.ICIcustInfoResponseDataType;
import com.tsys.xmlmessaging.ch.IGAacctGeneralInfoResponseDataType;
import com.tsys.xmlmessaging.ch.IGBgeneralBalInfoResponseDataType;

import mx.com.interware.spira.web.mxp.MxpParam;
import mx.com.invex.msi.mail.CompraEmailDto;
import mx.com.invex.msi.mail.EmailSender;
import mx.com.invex.msi.mail.MailDto;
import mx.com.invex.msi.model.Compra;
import mx.com.invex.msi.service.CompraService;
import mx.com.invex.msi.service.ParametroService;
import mx.com.invex.msi.service.ProductoTs2Service;
import mx.com.invex.msi.util.ComparadorFecha;
import mx.com.invex.msi.util.EnviarMail;
import mx.com.invex.msi.util.MSIConstants;
import mx.com.invex.msi.util.MSIException;
import mx.com.invex.msi.ws.ClientTS2;
import mx.com.invex.msi.ws.ClientePromosWS;
import mx.com.invex.msi.ws.Ts2MsgWsClient;

@Component
@Scope("request")
public class AplicarPromosBean extends MessagesMBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(AplicarPromosBean.class);
	@Autowired
	ParametroService parametroService;
	@Autowired
	CompraService compraService;
	@Autowired
	ProductoTs2Service productoTs2Service;
	
	private boolean comprasEnviadas;
	private double saldoNoInt;
	private String tipoProd;
	 @Autowired
	    private EmailSender emailSender;
	    @Autowired
	    private VelocityEngine velocityEngine;



	public String getTipoProd() {
		return tipoProd;
	}

	public void setTipoProd(String tipoProd) {
		this.tipoProd = tipoProd;
	}

	public double getSaldoNoInt() {
		return saldoNoInt;
	}

	public void setSaldoNoInt(double saldoNoInt) {
		this.saldoNoInt = saldoNoInt;
	}

	public boolean isComprasEnviadas() {
		return comprasEnviadas;
	}

	public void setComprasEnviadas(boolean comprasEnviadas) {
		this.comprasEnviadas = comprasEnviadas;
	}

	public String aplicarPromos(List<Compra> compras,Integer folio,String email,String nombreCompleto,String msgICM,String msgICM2) throws MSIException{
		logger.info("aplicar compras");
		
		try {
			ClientePromosWS cliMXP = new ClientePromosWS(parametroService.getParamById(MSIConstants.MXP_SERVICE_ENDPOINT).getValor());

			String cuenta=null;
			String username= getUsername();
			for(Compra compra:compras){
				cuenta= compra.getCuenta();
				compra.setUsername(username);
				compra.setOrigen("PU");
				compra.setIdEdoPromocion(MSIConstants.PROM_ESTATUS_PENDIENTE);
				if(compra.getDescripcion()== null || "".equals(compra.getDescripcion().trim())){
					compra.setDescripcion("NEGOCIO GENERICO");
				}
				String desc= compra.getDescripcion();
				compra.setDescripcion("TRASPASO MSI "+  String.format("%-17s",desc).substring(0,17)  +  ((compra.getNumRefTran()!=null)?(" "+compra.getNumRefTran().substring(13)):""));
				if(compra.getNumRefTran()==null){
					compra.setNumRefTran("0");
				}
				
				String statusCom=compraService.getStatusCompra(compra);
				if(statusCom!= null){
					sendErrorMessageToUser("La compra "+compra.getDescripcion()+ " ya ha sido enviada a promocion");
					return null;
				}
			}

			String res=null;
			for(Compra compra:compras){
				compra.setMontoOriginal(compra.getMonto());
				compra.setMonto(compra.getMontoPromo());
				compraService.save(compra);
				
				MxpParam param = new MxpParam();
				param.setAccount(compra.getCuentaFacturadora());

				param.setAmount(""+Math.round(compra.getMontoPromo()*100));
				if("ITA".equals(compra.getTipoTransaccion().trim())){
					param.setBalanceId(MSIConstants.BALANCE_ID_CURRENT_PURCHASE);
				}else if ("IPS".equals(compra.getTipoTransaccion().trim())){
					param.setBalanceId(MSIConstants.ONE_CYCLE_OLD_PURCHASE);
				}
				param.setPromoId(compra.getPromocion().getIdPromocion()+"");
				param.setUserName("MSI "+username);
				param.setDescription(compra.getDescripcion());

				//res = MSIConstants.desa?"OK":cliMXP.aplicarPromocion(param);
				res = cliMXP.aplicarPromocion(param);
				
				if("OK".equalsIgnoreCase(res)){
					compra.setEnPromocion(true);
					compra.setFechaAplicacionPromocion(new Date());
					compra.setIdEdoPromocion(MSIConstants.PROM_ESTATUS_ENVIADO);
					compraService.update(compra);
				}else{
					compra.setEnPromocion(false);
					sendErrorMessageToUser("Error Tsys al aplicar la promocion cuenta "+compra.getCuenta() +" monto "+ compra.getMontoPromo()+" a  fecha "+compra.getFechaCompra()+" msg: "+res);
					logger.error("Error Tsys al aplicar la promocion cuenta "+compra.getCuenta()
							+" monto "+ compra.getMonto()+" meses  fecha "+compra.getFechaCompra() +" res "+res );
					return "error";
				}
			}
			
			

			sendInfoMessageToUser("Las compras han sido enviadas a promoción con éxito");
			comprasEnviadas = true;
			
			
				
				//ClientLSWS clienteTsysWS= new ClientLSWS(parametroService.getParamById(MSIConstants.TSYSWS_ENDPOINT).getValor());
				
				
				Ts2MsgWsClient msgWS = new Ts2MsgWsClient();
				String wsMsgEndPoint= parametroService.getParamById(MSIConstants.TS2_MSG_ENDPOINT).getValor();
				logger.info("ws "+wsMsgEndPoint+" usr "+username+" custID 0 cuenta "+cuenta+" msg1 "+msgICM);
				msgWS.setEndPoint(wsMsgEndPoint);
				msgWS.insertaMsgTs2(username, "0", cuenta, msgICM);
				msgWS.insertaMsgTs2(username, "0", cuenta, msgICM2);

				
	
			
			MailDto mailParams = new MailDto();
			String nombre="";
			if(nombreCompleto!= null && !nombreCompleto.isEmpty()){
				String ar[] = nombreCompleto.replace('*', ' ').split(" ");

				for (String string : ar) {
					nombre+= string.charAt(0)+string.substring(1).toLowerCase()+" ";
				}
			}
			mailParams.setNombre(nombre);
			SimpleDateFormat formateador = new SimpleDateFormat(
					   "MMMM',' yyyy", new Locale("es"));
			Date fechaDate = new Date();
			String fecha = formateador.format(fechaDate);
			mailParams.setFecha(Character.toUpperCase(fecha.charAt(0))+fecha.substring(1));
			mailParams.setFolio(""+folio);
			
			Collections.sort(compras, new ComparadorFecha());

			Double montoAPagar= new Double(0);
			SimpleDateFormat format = new SimpleDateFormat(
					"dd/MM/yyyy");
		
			double montoTotal =0.0;
			int index=0;
			
			NumberFormat formatter = NumberFormat.getCurrencyInstance();
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
			Map<String,Object> model = new HashMap<String,Object>();	             
			model.put("mailParams", mailParams);		             
			
		//	emailSender.sendEmail("fescamilla@invex.com","boletin@invextarjetas.com.mx",  "Test",model);
			enviarMensaje(compras,email, folio,nombreCompleto,cuenta,null);

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
		return null;
	}
	
	
	public String aplicarPromos2(List<Compra> compras,Integer folio,String email,String nombreCompleto,String msgICM,String msgICM2) throws MSIException{
		logger.info("aplicarPromos2");
		logger.info("msg 1 "+msgICM.toString());
		logger.info("msg 2 "+msgICM2.toString());
		try {
			//ClientePromosWS cliMXP = new ClientePromosWS(parametroService.getParamById(MSIConstants.MXP_SERVICE_ENDPOINT).getValor());
			if(compras == null || compras.isEmpty()){
				sendErrorMessageToUser("No hay compras seleccionadas");
				return null;
			}
			String cuenta=null;
			String username= getUsername();
			for(Compra compra:compras){
				cuenta= compra.getCuenta();
				compra.setUsername(username);
				compra.setOrigen("PU");
				//compra.setIdEdoPromocion(MSIConstants.PROM_ESTATUS_PENDIENTE);
				if(compra.getDescripcion()== null || "".equals(compra.getDescripcion().trim())){
					compra.setDescripcion("NEGOCIO GENERICO");
				}
				
				
				String descr ="TRASPASO MSI "+  String.format("%-17s",compra.getDescripcion()).substring(0,17)  +  ((compra.getNumRefTran()!=null)?(" "+compra.getNumRefTran().substring(13)):"");
				if(compra.getNumRefTran()==null){
					compra.setNumRefTran("0");
				}
				//String descr = compra.getDescripcion();
				logger.info("descripcion compra "+descr);
				if(descr.length()>40){
					compra.setDescripcion(descr.substring(0,40));
				}else{
					compra.setDescripcion(descr);
				}
				
				String statusCom=compraService.getStatusCompraItau(compra);
				if(statusCom!= null){
					sendErrorMessageToUser("La compra "+compra.getDescripcion()+ " ya ha sido enviada a promocion");
					return null;
				}
			}
			ClientTS2 cts2= new ClientTS2();
			String res=null;
			//String pattern = "###.##";
			//DecimalFormat decimalFormat = new DecimalFormat(pattern);
			for(Compra compra:compras){
				compra.setMontoOriginal(compra.getMonto());
				compra.setMonto(compra.getMontoPromo());
				compra.setFechaAplicacionPromocion(new Date());
				//compraService.save(compra);
				
					logger.info("inqAcctAvailTLPOpt assesfee false tlptype Installment tbal code 0001 amtToMove "+compra.getMontoPromo());
					try {
						cts2.aplicarCompra(compra);
						res="OK";
					} catch (Exception e) {
						logger.error("ERROR al aplicar compra "+compra.getCuenta() +" monto "+ compra.getMonto() +" err: "+e.getMessage());
						e.printStackTrace();
						sendErrorMessageToUser("ERROR al aplicar compra "+compra.getCuenta() +" monto "+ compra.getMonto() +" err: "+e.getMessage());
						return null;
					}
					
					
					
				if("OK".equalsIgnoreCase(res)){
					compra.setEnPromocion(true);
					compra.setFechaAplicacionPromocion(new Date());
					compra.setIdEdoPromocion(MSIConstants.PROM_ESTATUS_ENVIADO);
					compraService.save(compra);

				}else{
					compra.setEnPromocion(false);
					sendErrorMessageToUser("Error Tsys al aplicar la promocion cuenta "+compra.getCuenta() +" monto "+ compra.getMontoPromo()+" a  fecha "+compra.getFechaCompra()+" msg: "+res);
					logger.error("Error Tsys al aplicar la promocion cuenta "+compra.getCuenta()
							+" monto "+ compra.getMonto()+" meses  fecha "+compra.getFechaCompra() +" res "+res );
					return null;
				}

						
					}
					
			
			 String custId= null;
			 List<ICIcustInfoResponseDataType> listCustInfos =cts2.getCustInfo(cuenta);
			 logger.info("tam custinfos "+listCustInfos.size());
            for (ICIcustInfoResponseDataType custInfo : listCustInfos) {
                            logger.info("custInfo custiyer "+custInfo.getCustType());
                            if(custInfo.getCustType().equals("Primary") ){
                           	 custId=custInfo.getCustID();
                            }
            }
			Ts2MsgWsClient msgWS = new Ts2MsgWsClient();
			String wsMsgEndPoint= parametroService.getParamById(MSIConstants.TS2_MSG_ENDPOINT).getValor();
			logger.info("ws "+wsMsgEndPoint+" usr "+username+" custID "+custId+" cuenta "+cuenta+" msg1 "+msgICM);
			msgWS.setEndPoint(wsMsgEndPoint);
			msgWS.insertaMsgTs2(username, custId, cuenta, msgICM);
			msgWS.insertaMsgTs2(username, custId, cuenta, msgICM2);

			sendInfoMessageToUser("Las compras han sido enviadas a promoción con éxito");
			comprasEnviadas = true;
			
			
			//emailSender.sendEmail("fescamilla@invex.com","boletin@invextarjetas.com.mx",  "Test");
			if(MSIConstants.desa){
				email="fescamilla@invex.com";
			}
			
				IGBgeneralBalInfoResponseDataType gralBal=cts2.getGeneralBal(cuenta);
				
				String msg= null;
				 if(gralBal.getPmtInfo()!= null){
						if(gralBal.getPmtInfo().getValue().getStmtMin() == null){
							msg="Su pago m&iacute;nimo se ver&aacute; reflejado hasta el siguiente corte.";
									sendInfoMessageToUser("Su pago mínimo se verá reflejado hasta el siguiente corte.");
						}
						   
		            }
			
			enviarMensaje(compras,email, folio,nombreCompleto.trim(),cuenta,msg);

		
		}catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new MSIException(e.getMessage());
		}
		return null;
	}
	
	


	public String enviarMensaje(List<Compra> compras,String email,Integer folio,String nombreCompleto,String cuenta, String msg){

		//		FacesContext context = FacesContext.getCurrentInstance();
		//		ConsultaClienteBean conBean = (ConsultaClienteBean) context.getExternalContext().getSessionMap().get("ConsultaClienteBean");		

		try{						
			//String toAddress="fescamilla@invex.com";
			String destinatario = email;
			//destinatario = "fescamilla@invex.com";
			logger.info("destinatario: "+destinatario);
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

			StringBuffer body = new StringBuffer();
			SimpleDateFormat formateador = new SimpleDateFormat(
					   "MMMM',' yyyy", new Locale("es"));
			Date fechaDate = new Date();
			String fecha = formateador.format(fechaDate);
			//System.out.println(fecha);
//si es itau
			if(productoTs2Service.cuentaITAU(cuenta)){
				
			ClientTS2 cts2 = new ClientTS2();
						IGAacctGeneralInfoResponseDataType acctGral=cts2.getGeneralAcct(cuenta);
						
					String cpc = acctGral.getTSYSProductCode() == null?"": acctGral.getClientProductCode().getValue() ;
					//if("VL2".equals(cpc)){
						//volaris
						//crear correo volaris 2
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
						if(msg!= null){
							mailParams.setMsgPagoMin(msg);
						}
						Map<String,Object> model = new HashMap<String,Object>();	             
						model.put("mailParams", mailParams);		             
						
						emailSender.sendEmail(email,"boletin@invextarjetas.com.mx",  "Promociones MSI",model,cpc);
						return null;
					
					//}else if("VL1".equals(cpc)){}else{}
//				logger.info(body.toString());
//				EnviarMail.send(destinatario, ccAddress, bccAddress, MSIConstants.SUBJECT, isHTMLFormat, body, false);
//				
				//return null;
			}
			
			
			if(!tipoProd.startsWith("Volaris")){

				body.append("<html>\n");
				body.append("<head>\n");
				body.append("<title>mailing</title>\n");
				body.append("<meta http-equiv='Content-Type' content='text/html; charset='utf-8'>\n");
				body.append("</head>\n");
				body.append("<body bgcolor='#FFFFFF' topmargin='0'>\n");
				body.append("\n");


				body.append("<table width='628' border='0' align='center' cellpadding='0' cellspacing='0'>\n");
				body.append("	<tr>\n");
				body.append("		<td width='650' align='right' bgcolor='#a30234' colspan='3'>\n");
				body.append("			<table width='650' border='0' cellspacing='0' cellpadding='0'>\n");
				body.append("				<tr>\n");
				body.append("					<td width='299' bgcolor='#a30234'><img src='http://www.invextarjetas.com.mx/msi/head02.jpg' width='299' height='87' border='0'></td>\n");
				body.append("					<td width='351' valign='top'>" +
						"<table width='351' border='0' cellspacing='0' cellpadding='0'>\n");
				body.append("							<tr>\n");
				body.append("								<td height='87' valign='middle' bgcolor='#a30234'><p style='font-size:15px; text-align:right; color:#FFF; font-family:Arial, Helvetica, sans-serif; margin-right:20px; margin-top:5px; margin-bottom:4px;'>"+Character.toUpperCase(fecha.charAt(0))+fecha.substring(1)+"</p>\n");
				body.append("									<p style='font-size:15px; text-align:right; color:#FFF; font-family:Arial, Helvetica, sans-serif; margin-right:20px; margin-top:0px; margin-bottom:1px'>Apreciable "+nombre.trim()+":</p></td>\n");
				body.append("							</tr>\n");
				body.append("						</table>\n");
				body.append("					</td>\n");
				body.append("				</tr>\n");
				body.append("			</table>\n");

				body.append("		</td>\n");
				body.append("	</tr>\n");

				body.append("	<tr>\n");
				body.append("		<td bgcolor='#68000b' colspan='3' height='8'></td>\n");
				body.append("	</tr>\n");

				body.append("	<tr>\n");

				body.append("		<td bgcolor='#D0D1D3' width='10'></td>\n");
				body.append("		<td bgcolor='#ffffff' width='630'>\n");
				body.append("			<table width='630' border='0' cellspacing='0' cellpadding='0'>\n");
				body.append("				<tr>\n");
				body.append("					<td><img src='http://www.invextarjetas.com.mx/msi/foto-invex.jpg' width='630' height='234'></td>\n");
				body.append("				</tr>\n");
				body.append("				<tr>\n");
				
				body.append("<td><p style='color:000; font-family:Arial, Helvetica, sans-serif; font-size:26px; margin:30 0px 25 0px; text-align:center;'>Se han registrado las compras de su<br> tarjeta terminaci&oacute;n " + cuenta.substring(cuenta.length()-4) + " a<br> <span style='color:#A30234;'><b>meses sin intereses</b></span> con el folio: "+folio +"</p></td>\n");
				//body.append("					<td><p style='color:000; font-family:Arial, Helvetica, sans-serif; font-size:15px; margin:30 0px 25 0px; text-align:center;'>Se han registrado sus compras a<br /><span style='color:#a30234; font-size:15px; font-weight:bold;'>meses sin intereses</span><br />con el folio: "+folio +"</p></td>\n");
				body.append("				</tr>\n");
				body.append("				<tr>\n");
				body.append("					<td align='center'><table width='500' border='0' cellspacing='0' cellpadding='0'>\n");
				body.append("				<tr>\n");
				body.append("					<td height='30' width='90' style='background:#A30134;'>\n");
				body.append("						<p style='color: #FFF; font-family:Arial, Helvetica, sans-serif; font-size:16px; margin:5 0px 5 0px; text-align:center;'>Fecha</p>\n");
				body.append("					</td>\n");
				body.append("					<td height='30' width='90' style='background:#A30134;'>\n");
				body.append("						<p style='color: #FFF; font-family:Arial, Helvetica, sans-serif; font-size:16px; margin:5 0px 5 0px; text-align:center;'>Descripci&oacute;n</p>\n");
				body.append("					</td>\n");
				body.append("					<td height='30' width='90' style='background:#A30134;'>\n");
				body.append("						<p style='color: #FFF; font-family:Arial, Helvetica, sans-serif; font-size:16px; margin:5 0px 5 0px; text-align:center;'>Monto</p>\n");
				body.append("					</td>\n");
				body.append("					<td height='30' width='90' style='background:#A30134;'>\n");
				body.append("						<p style='color: #FFF; font-family:Arial, Helvetica, sans-serif; font-size:16px; margin:5 0px 5 0px; text-align:center;'>Monto a promoci&oacute;n</p>\n");
				body.append("					</td>\n");
				body.append("					<td height='30' width='90' style='background:#A30134;'>\n");
				body.append("						<p style='color: #FFF; font-family:Arial, Helvetica, sans-serif; font-size:16px; margin:5 0px 5 0px; text-align:center;'>Plazo</p>\n");
				body.append("					</td>\n");
				body.append("					<td height='30' width='140' style='background:#A30134;'>\n");
				body.append("						<p style='color: #FFF; font-family:Arial, Helvetica, sans-serif; font-size:16px; margin:5 0px 5 0px; text-align:center;'>Pago mensual</p>\n");
				body.append("					</td>\n");
				body.append("				</tr>\n");

				Collections.sort(compras, new ComparadorFecha());

				Double montoAPagar= new Double(0);
				SimpleDateFormat format = new SimpleDateFormat(
						"dd/MM/yyyy");
				
				double montoTotal =0.0;
				int index =0;
				for (Compra tx : compras) {
					
					String meses =tx.getPromocion().getPlazoMeses().toString();
					NumberFormat nf = NumberFormat.getCurrencyInstance();
					montoTotal+=tx.getMontoPromo();
					String monto = nf.format(tx.getMontoPromo());
					Double montoCompra =  (tx.getMontoPromo()/Integer.parseInt(meses));
					body.append("<tr>\n");
					body.append("	<td>\n");
					body.append("		<p style='color:#000; font-family:Arial, Helvetica, sans-serif; font-size:14px; margin:5 0px 5 0px; text-align:center;'>"+format.format(tx.getFechaCompra())+"</p>\n");
					body.append("	</td>\n");
					body.append("	<td>\n");
					body.append("		<p style='color:#000; font-family:Arial, Helvetica, sans-serif; font-size:14px; margin:5 0px 5 0px; text-align:center;'>"+tx.getDescripcion()+"</p>\n");
					body.append("	</td>\n");
					body.append("	<td>\n");
					body.append("		<p style='color:#000; font-family:Arial, Helvetica, sans-serif; font-size:14px; margin:5 0px 5 0px; text-align:center;'>"+nf.format(tx.getMontoOriginal())+"</p>\n");
					body.append("	</td>\n");
					body.append("	<td>\n");
					body.append("		<p style='color:#000; font-family:Arial, Helvetica, sans-serif; font-size:14px; margin:5 0px 5 0px; text-align:center;'>"+monto+"</p>\n");
					body.append("	</td>\n");        
					body.append("	<td>\n");
					body.append("		<p style='color:#000; font-family:Arial, Helvetica, sans-serif; font-size:14px; margin:5 0px 5 0px; text-align:center;'>"+meses+"</p>\n");
					body.append("	</td>\n");
					body.append("	<td>\n");
					body.append("		<p style='color:#000; font-family:Arial, Helvetica, sans-serif; font-size:14px; margin:5 0px 5 0px; text-align:center;'>"+formatter.format(montoCompra)+"</p>\n");
					body.append("	</td>\n");
					body.append("</tr>\n");
					index++;
					montoAPagar += montoCompra;
					logger.info(montoAPagar);
				}

				body.append("<tr>\n");
				body.append("	<td>\n");
				body.append("		<p style='color:#000; font-family:Arial, Helvetica, sans-serif; font-size:14px; margin:5 0px 5 0px; text-align:center;'>TOTALES</p>\n");
				body.append("	</td>\n");
				body.append("	<td>\n");
				body.append("		<p style='color:#000; font-family:Arial, Helvetica, sans-serif; font-size:15px; margin:5 0px 5 0px; text-align:center;'></p>\n");
				body.append("	</td>\n");
				body.append("	<td>\n");
				body.append("		<p style='color:#000; font-family:Arial, Helvetica, sans-serif; font-size:15px; margin:5 0px 5 0px; text-align:center;'></p>\n");
				body.append("	</td>\n");
				body.append("	<td>\n");
				body.append("		<p style='color:#000; font-family:Arial, Helvetica, sans-serif; font-size:14px; margin:5 0px 5 0px; text-align:center;'>"+formatter.format(montoTotal)+"</p>\n");
				body.append("	</td>\n");        
				body.append("	<td>\n");
				body.append("		<p style='color:#000; font-family:Arial, Helvetica, sans-serif; font-size:15px; margin:5 0px 5 0px; text-align:center;'></p>\n");
				body.append("	</td>\n");
				body.append("	<td>\n");
				body.append("		<p style='color:#000; font-family:Arial, Helvetica, sans-serif; font-size:14px; margin:5 0px 5 0px; text-align:center;'>"+formatter.format(montoAPagar)+"</p>\n");
				body.append("	</td>\n");
				body.append("</tr>\n");
				body.append("</table>\n");

				body.append("	</td>\n");
				body.append("</tr>\n");

				body.append("<tr>\n");
				body.append("	<td>\n");
				//<p style="color:000; font-family:Arial, Helvetica, sans-serif; 	font-size:19px; margin:30 0px 30 0px; text-align:center;"><b>Su pago para no generar intereses ahora es de XXXX</b>
				body.append("		<p style='color:000; font-family:Arial, Helvetica, sans-serif; 	font-size:19px; margin:30 0px 30 0px; text-align:center;'><b>Su pago para no generar intereses ahora es de "+formatter.format(saldoNoInt)+"</b><br />Para cualquier duda o aclaraci&oacute;n de la notificaci&oacute;n<br />comuniquese con nosotros.\n");
				body.append("			<br /><br />\n");
				body.append("			&iexcl;Aproveche todos los beneficios que su tarjeta le ofrece!\n");
				body.append("		</p>\n");

				body.append("	</td>\n");
				body.append("</tr>\n");
				body.append("</table>\n");


				body.append("</td>\n");
				body.append("<td bgcolor='#D0D1D3' width='10'></td>\n");
				body.append("</tr>\n");

				body.append("<tr>\n");

				body.append("<td bgcolor='#FFFFFF' colspan='3'><img src='https://www.invextarjetas.com.mx/msi/footer%20invex.jpg' width='650' height='202' border='0'></td>\n");
				body.append("</tr>\n");

				body.append("<tr>\n");
				body.append("<td bgcolor='#FFFFFF' colspan='3'>\n");

				body.append("</td>\n");
				body.append("</tr>\n");

				body.append("<tr>\n");
				body.append("<td>\n");
				body.append("</td>\n");
				body.append("<td>\n");
				body.append("<p style='color:#000; font-family:Arial, Helvetica, sans-serif; font-size:7.5px;'>\n");
				body.append("<b>CAT Promedio 0.0% sin IVA</b> para meses sin intereses. Informativo. Fecha de c&aacute;lculo: abril 2014. Tasa Variable. Tasa Anual. Tasa de inter&eacute;s promedio ponderada por saldo para tarjetas SíCard Plus 58.6%, comisi&oacute;n anual $610 pesos sin IVA. Tasa de inter&eacute;s promedio ponderada por saldo para tarjetas S&iacute;Card Plus INVEX 47.2%, comisi&oacute;n anual $610 pesos sin IVA. Tasa de inter&eacute;s promedio ponderada por saldo para tarjetas S&iacute;Card Platinum 57.3%, comisi&oacute;n anual $1,350 pesos sin IVA. Tasa de inter&eacute;s promedio ponderada por saldo para tarjetas S&iacute;Card Platinum INVEX 39.3%, comisi&oacute;n anual $1,350 pesos sin IVA. Tasa de inter&eacute;s promedio ponderada por saldo para tarjetas SiCard Plus MC 46.5%, comisi&oacute;n anual $610 pesos sin IVA. Tasa de inter&eacute;s promedio ponderada por saldo para tarjetas BAM 32.4%, comisi&oacute;n anual $595 pesos sin IVA. Tasa de inter&eacute;s promedio ponderada por saldo para tarjetas Manchester United INVEX 30.5%, comisi&oacute;n anual $595 pesos sin IVA Tasa de inter&eacute;s promedio ponderada por saldo para tarjetas Volaris INVEX 45.9%, comisi&oacute;n anual $1,200 pesos sin IVA. Consulta requisitos de contrataci&oacute;n y comisiones en www.invextarjetas.com.mx\n");
				body.append("<br /><br />\n");
				body.append("Banco INVEX S.A., Instituci&oacute;n de Banca M&uacute;ltiple, INVEX Grupo Financiero.\n");
				body.append("</p>\n");
				body.append("</td>\n");
				body.append("</tr>\n");

				body.append("</table>\n");
				body.append("</body>\n");
				body.append("</html>\n");

			}else{
				//crear correo volaris

				body.append("<html>\n");
				body.append("<head>\n");
				body.append("<title>mailing</title>\n");
				body.append("<meta http-equiv='Content-Type' content='text/html; charset=utf-8'>\n");
				body.append("</head>\n");
				body.append("<body bgcolor='#FFFFFF' topmargin='0'>\n");
				body.append("<table width='650' border='0' align='center' cellpadding='0' cellspacing='0'>\n");
				body.append("<tr>\n");
				body.append("<td width='650' align='right' bgcolor='#000' colspan='3'>\n");
				body.append("<table width='650' border='0' cellspacing='0' cellpadding='0'>\n");
				body.append("<tr>\n");
				body.append("<td width='287' height='110' bgcolor='#000000'>\n");
				body.append("<img src='http://www.invextarjetas.com.mx/msi/head02v.jpg' width='287' height='110' border='0'></td>\n");
				body.append("<td width='361' valign='top'>\n");
				body.append("<table width='361' border='0' cellspacing='0' cellpadding='0'>\n");
				body.append("<tr>\n");
				body.append("<td width='361' bgcolor='#000000'>\n");
				body.append("<img src='http://www.invextarjetas.com.mx/msi/head01.jpg' width='361' height='49' border='0'></td>\n");
				body.append("</tr>\n");
				body.append("<tr>\n");
				body.append("<td bgcolor='#000000'>\n");
				body.append("<p style='font-size: 15px; text-align: right; color: #FFF; font-family: Arial, Helvetica, sans-serif; margin-right: 20px; margin-top: 5px;'>"+Character.toUpperCase(fecha.charAt(0))+fecha.substring(1)+"</p>\n");
				body.append("<p style='font-size: 15px; text-align: right; color: #FFF; font-family: Arial, Helvetica, sans-serif; margin-right: 20px; margin-top: 0px; margin-bottom: 1px'>Apreciable "+nombre.trim()+":</p></td>\n");
				body.append("</tr>\n");
				body.append("</table>\n");
				body.append("</td>\n");
				body.append("</tr>\n");
				body.append("</table>\n");
				body.append("</td>\n");
				body.append("</tr>\n");

				body.append("<tr>\n");
				body.append("<td bgcolor='#B319AB' colspan='3' height='8'></td>\n");
				body.append("</tr>\n");
				body.append("<tr>\n");
				body.append("<td bgcolor='#D0D1D3' colspan='3'></td>\n");
				body.append("</tr>\n");
				body.append("<tr>\n");
				body.append("<td bgcolor='#D0D1D3' width='10'>\n");
				
				body.append("</td>\n");
				body.append("<td bgcolor='#ffffff' width='630'>\n");
				body.append("<table width='630' border='0' cellspacing='0' cellpadding='0'>\n");
				body.append("<tr>\n");
				body.append("<td><img src='http://www.invextarjetas.com.mx/msi/foto-volaris.jpg' width='630' height='234'></td>\n");
				body.append("</tr>\n");
				body.append("<tr>\n");
				body.append("<td>\n");
				body.append("<p style='color: 000; font-family: Arial, Helvetica, sans-serif; font-size: 26px; margin: 20 0px 20 0px; text-align: center;'>Se han registrado sus compras a<br />\n");
				body.append("<span style='color: #B319AB; font-size: 28px; font-weight: bold;'>meses sin intereses</span><br />con el folio: "+folio +"</p>\n");
				body.append("</td>\n");
				body.append("</tr>\n");
				body.append("<tr>\n");
				body.append("<td align='center'></td>\n");
				body.append("</tr>\n");
				body.append("<tr>\n");
				body.append("<td align='center'>\n");
				body.append("<table width='500' border='0' cellspacing='0' cellpadding='0'>\n");
				body.append("<tr>\n");
				body.append("<td height='30' width='90' style='background: #B319AB;'>\n");
				body.append("<p style='color: #FFF; font-family: Arial, Helvetica, sans-serif; font-size: 16px; margin: 5 0px 5 0px; text-align: center;'>Fecha</p>\n");
				body.append("</td>\n");
				body.append("<td height='30' width='90' style='background: #B319AB;'>\n");
				body.append("<p style='color: #FFF; font-family: Arial, Helvetica, sans-serif; font-size: 16px; margin: 5 0px 5 0px; text-align: center;'>Descripci&oacute;n</p>\n");
				body.append("</td>\n");
				body.append("<td height='30' width='90' style='background: #B319AB;'>\n");
				body.append("<p style='color: #FFF; font-family: Arial, Helvetica, sans-serif; font-size: 16px; margin: 5 0px 5 0px; text-align: center;'>Monto</p>\n");
				body.append("</td>\n");
				body.append("<td height='30' width='90' style='background: #B319AB;'>\n");
				body.append("<p style='color: #FFF; font-family: Arial, Helvetica, sans-serif; font-size: 16px; margin: 5 0px 5 0px; text-align: center;'>Monto a promoci&oacute;n</p>\n");
				body.append("</td>\n");
				body.append("<td height='30' width='90' style='background: #B319AB;'>\n");
				body.append("<p style='color: #FFF; font-family: Arial, Helvetica, sans-serif; font-size: 16px; margin: 5 0px 5 0px; text-align: center;'>Plazo</p>\n");
				body.append("</td>\n");
				body.append("<td height='30' width='140' style='background: #B319AB;'>\n");
				body.append("<p style='color: #FFF; font-family: Arial, Helvetica, sans-serif; font-size: 16px; margin: 5 0px 5 0px; text-align: center;'>Pago mensual</p>\n");
				body.append("</td>\n");
				body.append("</tr>\n");
				Collections.sort(compras, new ComparadorFecha());

				Double montoAPagar= new Double(0);
				SimpleDateFormat format = new SimpleDateFormat(
						"dd/MM/yyyy");
			
				double montoTotal =0.0;
				int index=0;
				for (Compra tx : compras) {
					
					String meses =tx.getPromocion().getPlazoMeses().toString();
					NumberFormat nf = NumberFormat.getCurrencyInstance();
					montoTotal+=tx.getMontoPromo();
					String monto = nf.format(tx.getMontoPromo());
					Double montoCompra =  (tx.getMontoPromo()/Integer.parseInt(meses));								

					body.append("<tr>\n");
					body.append("<td>\n");
					body.append("<p style='color: #000; font-family: Arial, Helvetica, sans-serif; font-size: 14px; margin: 5 0px 5 0px; text-align: center;'>"+format.format(tx.getFechaCompra())+"</p>\n");
					body.append("</td>\n");
					body.append("<td>\n");
					body.append("	<p style='color: #000; font-family: Arial, Helvetica, sans-serif; font-size: 14px; margin: 5 0px 5 0px; text-align: center;'>"+tx.getDescripcion()+"</p>\n");
					body.append("</td>\n");
					body.append("	<td>\n");
					body.append("		<p style='color:#000; font-family:Arial, Helvetica, sans-serif; font-size:14px; margin:5 0px 5 0px; text-align:center;'>"+nf.format(tx.getMontoOriginal())+"</p>\n");
					body.append("	</td>\n");
					body.append("<td>\n");
					body.append("	<p style='color: #000; font-family: Arial, Helvetica, sans-serif; font-size: 14px; margin: 5 0px 5 0px; text-align: center;'>"+monto+"</p>\n");
					body.append("</td>\n");
					body.append("<td>\n");
					body.append("	<p style='color: #000; font-family: Arial, Helvetica, sans-serif; font-size: 14px; margin: 5 0px 5 0px; text-align: center;'>"+meses+"</p>\n");
					body.append("</td>\n");
					body.append("<td>\n");
					body.append("	<p style='color: #000; font-family: Arial, Helvetica, sans-serif; font-size: 14px; margin: 5 0px 5 0px; text-align: center;'>"+formatter.format(montoCompra)+"</p>\n");
					body.append("</td>\n");
					body.append("</tr>\n");		
					montoAPagar += montoCompra;
					index++;
				}									
				body.append("<tr>\n");
				body.append("<td>\n");
				body.append("<p style='color: #000; font-family: Arial, Helvetica, sans-serif; font-size: 14px; margin: 5 0px 5 0px; text-align: center;'>TOTALES</p>\n");
				body.append("</td>\n");
				body.append("<td>\n");
				body.append("	<p style='color: #000; font-family: Arial, Helvetica, sans-serif; font-size: 14px; margin: 5 0px 5 0px; text-align: center;'></p>\n");
				body.append("</td>\n");
				body.append("	<td>\n");
				body.append("		<p style='color:#000; font-family:Arial, Helvetica, sans-serif; font-size:15px; margin:5 0px 5 0px; text-align:center;'></p>\n");
				body.append("	</td>\n");
				body.append("<td>\n");
				body.append("	<p style='color: #000; font-family: Arial, Helvetica, sans-serif; font-size: 14px; margin: 5 0px 5 0px; text-align: center;'>"+formatter.format(montoTotal)+"</p>\n");
				body.append("</td>\n");
				body.append("<td>\n");
				body.append("	<p style='color: #000; font-family: Arial, Helvetica, sans-serif; font-size: 14px; margin: 5 0px 5 0px; text-align: center;'></p>\n");
				body.append("</td>\n");
				body.append("<td>\n");
				body.append("	<p style='color: #000; font-family: Arial, Helvetica, sans-serif; font-size: 14px; margin: 5 0px 5 0px; text-align: center;'>"+formatter.format(montoAPagar)+"</p>\n");
				body.append("</td>\n");
				body.append("</tr>\n");

				body.append("</table>\n");
				body.append("</td>\n");
				body.append("</tr>\n");
				body.append("<tr>\n");
				body.append("<td>\n");
				body.append("<p style='color: 000; font-family: Arial, Helvetica, sans-serif; font-size: 15px; margin: 30 0px 0 0px; text-align: center;'>\n");
				body.append("<b>Su pago para no generar intereses ahora es de "+formatter.format(saldoNoInt)+"</b><br />\n");
				body.append("Para cualquier duda o aclaraci&oacute;n de la notificaci&oacute;n<br />comuniquese con nosotros. <br />\n");
				body.append("<br /> &iexcl;Aproveche todos los beneficios que su tarjeta le ofrece!\n");
				body.append("</p>\n");
				body.append("</td>\n");
				body.append("</tr>\n");
				body.append("</table>\n");
				body.append("</td>\n");
				body.append("<td bgcolor='#D0D1D3' width='10'></td>\n");
				body.append("</tr>\n");

				body.append("<tr>\n");

				body.append("<td bgcolor='#FFFFFF' colspan='3'><img src='https://www.invextarjetas.com.mx/msi/footer%20volaris.jpg' width='650' height='270'></td>\n");
				body.append("</tr>\n");

				body.append("<tr>\n");
				body.append("<td colspan='3'>&nbsp;</td>\n");
				body.append("</tr>\n");
				body.append("<tr>\n");
				body.append("<td>\n");
				body.append("</td>\n");
				body.append("<td>\n");
				body.append("<p style='color:#000; font-family:Arial, Helvetica, sans-serif; font-size:7.5px;'>\n");
				body.append("<b>CAT Promedio 0.0% sin IVA</b> para meses sin intereses. Informativo. Fecha de c&aacute;lculo: abril 2014. Tasa Variable. Tasa Anual. Tasa de inter&eacute;s promedio ponderada por saldo para tarjetas SíCard Plus 58.6%, comisi&oacute;n anual $610 pesos sin IVA. Tasa de inter&eacute;s promedio ponderada por saldo para tarjetas S&iacute;Card Plus INVEX 47.2%, comisi&oacute;n anual $610 pesos sin IVA. Tasa de inter&eacute;s promedio ponderada por saldo para tarjetas S&iacute;Card Platinum 57.3%, comisi&oacute;n anual $1,350 pesos sin IVA. Tasa de inter&eacute;s promedio ponderada por saldo para tarjetas S&iacute;Card Platinum INVEX 39.3%, comisi&oacute;n anual $1,350 pesos sin IVA. Tasa de inter&eacute;s promedio ponderada por saldo para tarjetas SiCard Plus MC 46.5%, comisi&oacute;n anual $610 pesos sin IVA. Tasa de inter&eacute;s promedio ponderada por saldo para tarjetas BAM 32.4%, comisi&oacute;n anual $595 pesos sin IVA. Tasa de inter&eacute;s promedio ponderada por saldo para tarjetas Manchester United INVEX 30.5%, comisi&oacute;n anual $595 pesos sin IVA Tasa de inter&eacute;s promedio ponderada por saldo para tarjetas Volaris INVEX 45.9%, comisi&oacute;n anual $1,200 pesos sin IVA. Consulta requisitos de contrataci&oacute;n y comisiones en www.invextarjetas.com.mx\n");
				body.append("<br /><br />\n");
				body.append("Banco INVEX S.A., Instituci&oacute;n de Banca M&uacute;ltiple, INVEX Grupo Financiero.\n");
				body.append("</p>\n");
				body.append("</td>\n");
				body.append("</tr>\n");

				body.append("</table>\n");
				body.append("</body>\n");
				body.append("</html>\n");

			}
			logger.info(body.toString());

			EnviarMail.send(destinatario, ccAddress, bccAddress, MSIConstants.SUBJECT, isHTMLFormat, body, false);

		}catch(Exception e){

			sendErrorMessageToUser("ERROR al enviar el email");
			e.printStackTrace();
			logger.error(e);			
		}		


		return null;
	}


}
