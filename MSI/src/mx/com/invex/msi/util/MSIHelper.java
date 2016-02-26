package mx.com.invex.msi.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.tsys.xmlmessaging.ch.IGAacctGeneralInfoResponseDataType;
import com.tsys.xmlmessaging.ch.InqGeneralAcct;
import com.tsys.xmlmessaging.ch.InqGeneralAcctRequestType;
import com.tsys.xmlmessaging.ch.InqGeneralAcctResponseType;
import com.tsys.xmlmessaging.ch.TSYSfault;
import com.tsys.xmlmessaging.ch.TSYSfaultType;
import com.tsys.xmlmessaging.ch.TSYSprofileType;

import mx.com.invex.msi.model.AplicarComprasWSRespDTO;
import mx.com.invex.msi.model.Compra;
import mx.com.invex.msi.model.CompraWSDTO;
import mx.com.invex.msi.model.InfoEnviadaDTO;
import mx.com.invex.msi.model.Promocion;
import mx.com.invex.msi.ws.ClientTS2;

public class MSIHelper {
	static List<BeanProductosTs2> productos;
	static public List<String> statusCodes ;

	static{
		productos = new ArrayList<BeanProductosTs2>();
		
       
       productos.add( new BeanProductosTs2("Movistar MC Clasica",    "546804", "MC", "MSC") );
       productos.add( new BeanProductosTs2("ITAUCARD MC Clasica",    "546804", "MC", "IMC") );
       productos.add( new BeanProductosTs2("Movistar MC Oro",        "546759", "MG", "MSG") );
       productos.add( new BeanProductosTs2("ITAUCARD MC Oro",        "546759", "MG", "IMG") );
       productos.add( new BeanProductosTs2("ITAUCARD VISA Oro",      "419691", "VG", "IVG") );
       productos.add( new BeanProductosTs2("ITAUCARD Standard Oro",  "419691", "VG", "STA") );
       productos.add( new BeanProductosTs2("DECOMPRAS VISA Clasica", "419690", "VS", "PZA") );
       productos.add( new BeanProductosTs2("ITAUCARD VISA Clasica",  "419690", "VS", "IVC") );
       
       productos.add( new BeanProductosTs2("VOLARIS VISA PLATINUM",  "419691", "VP", "VL1") );
       productos.add( new BeanProductosTs2("VOLARIS VISA PLATINUM",  "419691", "VP", "VL2") );
       productos.add( new BeanProductosTs2("VOLARIS VISA PLATINUM",  "419691", "VG", "VL1") );
       productos.add( new BeanProductosTs2("VOLARIS VISA PLATINUM",  "419691", "VG", "VL2") );
       
       productos.add( new BeanProductosTs2("Radio Shack MC", "546804", "MC", "RDS") );
       
       statusCodes = new ArrayList<String>();
       statusCodes.add("AP SE");
       statusCodes.add("CL BP");
       statusCodes.add("CL DC");
       statusCodes.add("CL PO");
       statusCodes.add("CL V9");
       statusCodes.add("CW 20");
       statusCodes.add("SF LS");
       statusCodes.add("SF ST");
       statusCodes.add("WA CS");
       statusCodes.add("WA OT");
       statusCodes.add("TW L3");
       statusCodes.add("TW L6");
       statusCodes.add("TW L7");
       statusCodes.add("TW L5");
       statusCodes.add("TW L4");
       statusCodes.add("TW L8");
       statusCodes.add("ST Y");
	}
	
	public static Compra fromStringToCompra(String strMov){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Compra compra= new Compra();
		String[] arr = strMov.split("\\|");
		compra.setCodigoTransaccion(new Integer(arr[0]));
		try {
			compra.setFechaCompra(sdf.parse(arr[1]));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		compra.setTipoTransaccion(arr[2]);
		compra.setMonto(new Double(arr[3]));
		compra.setDescripcion(arr[4]);
		if(arr.length>5)
			compra.setNumRefTran(arr[5]);
		return compra;
	}
	
	public static boolean cuentaITAU(String cuenta){
        if(cuenta.length() >= 6 ){
        	for (BeanProductosTs2 prod : productos) {
    			if(prod.getBin().equals(cuenta.substring(0,6))){
    				return true;
    			}
    		}
            
        }
		return false;
	}
	
	
	public static Compra getCompraFromCompraWSDTO(CompraWSDTO compraWSDTO){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Compra compra = new Compra();
		compra.setCodigoTransaccion(compraWSDTO.getCodigoTransaccion());
		compra.setCuenta(compraWSDTO.getCuenta());
    	compra.setCuentaFacturadora(compraWSDTO.getCuentaFacturadora());
    	compra.setDescripcion(compraWSDTO.getDescripcion());
    	compra.setEnPromocion(compraWSDTO.isEnPromocion());
    	compra.setFechaAplicacionPromocion(compraWSDTO.getFechaAplicacionPromocion());
    	compra.setIdEdoPromocion(MSIConstants.PROM_ESTATUS_ENVIADO);
    	try {
			compra.setFechaCompra(sdf.parse(compraWSDTO.getFechaCompra()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	compra.setFolio(compraWSDTO.getFolio());
    	compra.setIdCompra(compraWSDTO.getIdCompra());
    	//compra.setIdEdoPromocion(compraWSDTO.getIdEdoPromocion());
    	compra.setMonto(compraWSDTO.getMonto());
    	compra.setMontoOriginal(compraWSDTO.getMontoOriginal());
    	compra.setMontoPromo(compraWSDTO.getMontoPromo());
    	compra.setNumRefTran(compraWSDTO.getNumRefTran());
    	compra.setTipoTransaccion(compraWSDTO.getTipoTransaccion());
    	compra.setUsername(compraWSDTO.getUsername());
    	GregorianCalendar gc1 =new GregorianCalendar();
    	try {
			gc1.setTime(sdf.parse(compraWSDTO.getDateStmtBegin()));
			 XMLGregorianCalendar xmlgc1 = DatatypeFactory.newInstance()
				        .newXMLGregorianCalendar(gc1);
	    	compra.setDateStmtBegin(xmlgc1);
	    	GregorianCalendar gc2 =new GregorianCalendar();
	    	gc2.setTime(sdf.parse(compraWSDTO.getDatePost()));
	    	 XMLGregorianCalendar xmlgc2 = DatatypeFactory.newInstance()
				        .newXMLGregorianCalendar(gc2);
	    	 compra.setDatePost(xmlgc2);
	    	 compra.setTimePost(new BigInteger(compraWSDTO.getTimePost()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return compra;
}
	
	public static CompraWSDTO getCompraWSDTO(Compra compra){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		CompraWSDTO compraWSDTO = new CompraWSDTO();
		compraWSDTO.setCodigoTransaccion(compra.getCodigoTransaccion());
		compraWSDTO.setCuenta(compra.getCuenta());
    	compraWSDTO.setCuentaFacturadora(compra.getCuentaFacturadora());
    	compraWSDTO.setDescripcion(compra.getDescripcion());
    	compraWSDTO.setEnPromocion(compra.isEnPromocion());
    	compraWSDTO.setFechaAplicacionPromocion(compra.getFechaAplicacionPromocion());
    	compraWSDTO.setFechaCompra(sdf.format(compra.getFechaCompra()));
    	compraWSDTO.setFolio(compra.getFolio());
    	compraWSDTO.setIdCompra(compra.getIdCompra());
    	compraWSDTO.setIdEdoPromocion(compra.getIdEdoPromocion());
    	compraWSDTO.setMonto(compra.getMonto());
    	compraWSDTO.setMontoOriginal(compra.getMontoOriginal());
    	compraWSDTO.setMontoPromo(compra.getMontoPromo());
    	compraWSDTO.setNumRefTran(compra.getNumRefTran());
    	compraWSDTO.setTipoTransaccion(compra.getTipoTransaccion());
    	compraWSDTO.setDateStmtBegin(sdf.format(compra.getDateStmtBegin().toGregorianCalendar().getTime()));
    	compraWSDTO.setDatePost(sdf.format(compra.getDatePost().toGregorianCalendar().getTime()));
    	compraWSDTO.setTimePost(compra.getTimePost().toString());
    	Map<String,Promocion> mapPromos=compra.getPromosCombo();
    	List<String> lPromosString=new ArrayList<String>();
    	if(mapPromos!= null && !mapPromos.isEmpty()){
    		Set<String> setKeys=mapPromos.keySet();
    		for (String key : setKeys) {
    			Promocion prom =mapPromos.get(key);
				lPromosString.add(prom.getIdPromocion()+"|"+ prom.getDescripcion());
			}
    	}
    	compraWSDTO.setPromosCombo(lPromosString);
    	return compraWSDTO;
}

	
	public static String convertStreamToString(InputStream is) {
	     // The incoming input stream is accumulated in a String to be returned
	     BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	     StringBuilder sb = new StringBuilder();
	     String line = null;
	     try {
	         while ((line = reader.readLine()) != null) {
	             sb.append(line).append("\n");
	         }
	     } catch (IOException e) {
	     } finally {
	         try {
	             is.close();
	         } catch (IOException e) {
	         }
	     }
	     return sb.toString();
	 }
	
	
	public static String enviarMensaje(List<Compra> compras,String email,Integer folio,String nombreCompleto,String cuenta,double saldoNoInt,String tipoProd,boolean isItau){

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
				body.append("<b>CAT Promedio 0.0% sin IVA</b> para meses sin intereses. Informativo. Fecha de c&aacute;lculo: abril 2014. Tasa Variable. Tasa Anual. Tasa de inter&eacute;s promedio ponderada por saldo para tarjetas SÌCard Plus 58.6%, comisi&oacute;n anual $610 pesos sin IVA. Tasa de inter&eacute;s promedio ponderada por saldo para tarjetas S&iacute;Card Plus INVEX 47.2%, comisi&oacute;n anual $610 pesos sin IVA. Tasa de inter&eacute;s promedio ponderada por saldo para tarjetas S&iacute;Card Platinum 57.3%, comisi&oacute;n anual $1,350 pesos sin IVA. Tasa de inter&eacute;s promedio ponderada por saldo para tarjetas S&iacute;Card Platinum INVEX 39.3%, comisi&oacute;n anual $1,350 pesos sin IVA. Tasa de inter&eacute;s promedio ponderada por saldo para tarjetas SiCard Plus MC 46.5%, comisi&oacute;n anual $610 pesos sin IVA. Tasa de inter&eacute;s promedio ponderada por saldo para tarjetas BAM 32.4%, comisi&oacute;n anual $595 pesos sin IVA. Tasa de inter&eacute;s promedio ponderada por saldo para tarjetas Manchester United INVEX 30.5%, comisi&oacute;n anual $595 pesos sin IVA Tasa de inter&eacute;s promedio ponderada por saldo para tarjetas Volaris INVEX 45.9%, comisi&oacute;n anual $1,200 pesos sin IVA. Consulta requisitos de contrataci&oacute;n y comisiones en www.invextarjetas.com.mx\n");
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

				body.append("<td bgcolor='#FFFFFF' colspan='3'><img src='https://www.invextarjetas.com.mx/msi/footer%20invex.jpg' width='650' height='270'></td>\n");
				body.append("</tr>\n");

				body.append("<tr>\n");
				body.append("<td colspan='3'>&nbsp;</td>\n");
				body.append("</tr>\n");
				body.append("<tr>\n");
				body.append("<td>\n");
				body.append("</td>\n");
				body.append("<td>\n");
				body.append("<p style='color:#000; font-family:Arial, Helvetica, sans-serif; font-size:7.5px;'>\n");
				body.append("<b>CAT Promedio 0.0% sin IVA</b> para meses sin intereses. Informativo. Fecha de c&aacute;lculo: abril 2014. Tasa Variable. Tasa Anual. Tasa de inter&eacute;s promedio ponderada por saldo para tarjetas SÌCard Plus 58.6%, comisi&oacute;n anual $610 pesos sin IVA. Tasa de inter&eacute;s promedio ponderada por saldo para tarjetas S&iacute;Card Plus INVEX 47.2%, comisi&oacute;n anual $610 pesos sin IVA. Tasa de inter&eacute;s promedio ponderada por saldo para tarjetas S&iacute;Card Platinum 57.3%, comisi&oacute;n anual $1,350 pesos sin IVA. Tasa de inter&eacute;s promedio ponderada por saldo para tarjetas S&iacute;Card Platinum INVEX 39.3%, comisi&oacute;n anual $1,350 pesos sin IVA. Tasa de inter&eacute;s promedio ponderada por saldo para tarjetas SiCard Plus MC 46.5%, comisi&oacute;n anual $610 pesos sin IVA. Tasa de inter&eacute;s promedio ponderada por saldo para tarjetas BAM 32.4%, comisi&oacute;n anual $595 pesos sin IVA. Tasa de inter&eacute;s promedio ponderada por saldo para tarjetas Manchester United INVEX 30.5%, comisi&oacute;n anual $595 pesos sin IVA Tasa de inter&eacute;s promedio ponderada por saldo para tarjetas Volaris INVEX 45.9%, comisi&oacute;n anual $1,200 pesos sin IVA. Consulta requisitos de contrataci&oacute;n y comisiones en www.invextarjetas.com.mx\n");
				body.append("<br /><br />\n");
				body.append("Banco INVEX S.A., Instituci&oacute;n de Banca M&uacute;ltiple, INVEX Grupo Financiero.\n");
				body.append("</p>\n");
				body.append("</td>\n");
				body.append("</tr>\n");

				body.append("</table>\n");
				body.append("</body>\n");
				body.append("</html>\n");

			}
			

			EnviarMail.send(destinatario, ccAddress, bccAddress, MSIConstants.SUBJECT, isHTMLFormat, body, false);

		}catch(Exception e){

		
			e.printStackTrace();
					
		}		


		return body.toString();
	}
	
	

	public static String getInfoEmail(List<Compra> compras,String email,Integer folio,String nombreCompleto,String cuenta,double saldoNoInt,String tipoProd,boolean isItau){

		//		FacesContext context = FacesContext.getCurrentInstance();
		//		ConsultaClienteBean conBean = (ConsultaClienteBean) context.getExternalContext().getSessionMap().get("ConsultaClienteBean");		
		StringBuffer body = new StringBuffer();
		try{						
			//String toAddress="fescamilla@invex.com";
			String destinatario = email;
			//destinatario = "fescamilla@invex.com";
		
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
				
			ClientTS2 cts2 = new ClientTS2();
						IGAacctGeneralInfoResponseDataType acctGral=cts2.getGeneralAcct(cuenta);
					
					String tpc = acctGral.getTSYSProductCode() == null?"": acctGral.getTSYSProductCode() ;
					if("VP".equals(tpc) || "VG".equals(tpc)){
						//volaris
						//crear correo volaris

//						body.append("<html>\n");
//						body.append("<head>\n");
//						body.append("<title>mailing</title>\n");
//						body.append("<meta http-equiv='Content-Type' content='text/html; charset=utf-8'>\n");
//						body.append("</head>\n");
//						body.append("<body bgcolor='#FFFFFF' topmargin='0'>\n");
						body.append("<table width='650' border='0' align='center' cellpadding='0' cellspacing='0'>\n");
						body.append("<tr>\n");
						body.append("<td width='650' align='right' bgcolor='#000' colspan='3'>\n");
						body.append("<table width='650' border='0' cellspacing='0' cellpadding='0'>\n");
						body.append("<tr>\n");
						body.append("<td width='287' height='110' bgcolor='#000000'>\n");
						body.append("" +
								//"<img src='http://www.invextarjetas.com.mx/msi/head02v.jpg' width='287' height='110' border='0'>" +
								"</td>\n");
						body.append("<td width='361' valign='top'>\n");
//						body.append("<table width='361' border='0' cellspacing='0' cellpadding='0'>\n");
//						body.append("<tr>\n");
//						body.append("<td width='361' bgcolor='#000000'>\n");
//						body.append("" +
//								//"<img src='http://www.invextarjetas.com.mx/msi/head01.jpg' width='361' height='49' border='0'>" +
//								"</td>\n");
//						body.append("</tr>\n");
//						body.append("<tr>\n");
//						body.append("<td bgcolor='#000000'>\n");
//						body.append("<p style='font-size: 15px; text-align: right; color: #FFF; font-family: Arial, Helvetica, sans-serif; margin-right: 20px; margin-top: 5px;'>"+Character.toUpperCase(fecha.charAt(0))+fecha.substring(1)+"</p>\n");
//						body.append("<p style='font-size: 15px; text-align: right; color: #FFF; font-family: Arial, Helvetica, sans-serif; margin-right: 20px; margin-top: 0px; margin-bottom: 1px'>Estimado (a): "+nombre+"</p></td>\n");
//						body.append("</tr>\n");
//						body.append("</table>\n");
//						body.append("</td>\n");
//						body.append("</tr>\n");
//						body.append("</table>\n");
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
						body.append("<td>" +
								//("<img src='http://www.invextarjetas.com.mx/msi/foto-volaris.jpg' width='630' height='234'>" +
								"</td>\n");
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

						body.append("<td bgcolor='#FFFFFF' colspan='3'>" +
								//"<img src='http://www.invextarjetas.com.mx/msi/footes.jpg' width='650' height='270'>" +
								"</td>\n");
						body.append("</tr>\n");

						body.append("<tr>\n");
						body.append("<td colspan='3'>&nbsp;</td>\n");
						body.append("</tr>\n");
						body.append("<tr>\n");
						body.append("<td>\n");
						body.append("</td>\n");
						body.append("<td>\n");
//						body.append("<p style='color:#000; font-family:Arial, Helvetica, sans-serif; font-size:7.5px;'>\n");
//						body.append("<b>CAT Promedio 0.0% sin IVA</b> para meses sin intereses. Informativo. Fecha de c&aacute;lculo: abril 2014. Tasa Variable. Tasa Anual. Tasa de inter&eacute;s promedio ponderada por saldo para tarjetas SÌCard Plus 58.6%, comisi&oacute;n anual $610 pesos sin IVA. Tasa de inter&eacute;s promedio ponderada por saldo para tarjetas S&iacute;Card Plus INVEX 47.2%, comisi&oacute;n anual $610 pesos sin IVA. Tasa de inter&eacute;s promedio ponderada por saldo para tarjetas S&iacute;Card Platinum 57.3%, comisi&oacute;n anual $1,350 pesos sin IVA. Tasa de inter&eacute;s promedio ponderada por saldo para tarjetas S&iacute;Card Platinum INVEX 39.3%, comisi&oacute;n anual $1,350 pesos sin IVA. Tasa de inter&eacute;s promedio ponderada por saldo para tarjetas SiCard Plus MC 46.5%, comisi&oacute;n anual $610 pesos sin IVA. Tasa de inter&eacute;s promedio ponderada por saldo para tarjetas BAM 32.4%, comisi&oacute;n anual $595 pesos sin IVA. Tasa de inter&eacute;s promedio ponderada por saldo para tarjetas Manchester United INVEX 30.5%, comisi&oacute;n anual $595 pesos sin IVA Tasa de inter&eacute;s promedio ponderada por saldo para tarjetas Volaris INVEX 45.9%, comisi&oacute;n anual $1,200 pesos sin IVA. Consulta requisitos de contrataci&oacute;n y comisiones en www.invextarjetas.com.mx\n");
//						body.append("<br /><br />\n");
//						body.append("Banco INVEX S.A., Instituci&oacute;n de Banca M&uacute;ltiple, INVEX Grupo Financiero.\n");
//						body.append("</p>\n");
						body.append("</td>\n");
						body.append("</tr>\n");

						body.append("</table>\n");
//						body.append("</body>\n");
//						body.append("</html>\n");
						return body.toString();
					
					}else{
				

//				body.append("<html>\n");
//				body.append("<head>\n");
//				body.append("<title>mailing</title>\n");
//				body.append("<meta http-equiv='Content-Type' content='text/html; charset='utf-8'>\n");
//				body.append("</head>\n");
//				body.append("<body bgcolor='#FFFFFF' topmargin='0'>\n");
//				body.append("\n");


				body.append("<table width='628' border='0' align='center' cellpadding='0' cellspacing='0'>\n");
				body.append("	<tr>\n");
				body.append("		<td width='650' align='right' bgcolor='#a30234' colspan='3'>\n");
//				body.append("			<table width='650' border='0' cellspacing='0' cellpadding='0'>\n");
//				body.append("				<tr>\n");
//				body.append("					<td width='299' bgcolor='#ab092f'>" +
//						//"<img src='http://www.invextarjetas.com.mx/msi/headITAU.jpg' width='299' height='87' border='0'>" +
//						"</td>\n");
//				body.append("					<td width='351' valign='top'>" +
//						"<table width='351' border='0' cellspacing='0' cellpadding='0'>\n");
//				body.append("							<tr>\n");
//				body.append("								<td height='87' valign='middle' bgcolor='#ab092f'><p style='font-size:15px; text-align:right; color:#FFF; font-family:Arial, Helvetica, sans-serif; margin-right:20px; margin-top:5px; margin-bottom:4px;'>"+Character.toUpperCase(fecha.charAt(0))+fecha.substring(1)+"</p>\n");
//				body.append("									<p style='font-size:15px; text-align:right; color:#FFF; font-family:Arial, Helvetica, sans-serif; margin-right:20px; margin-top:0px; margin-bottom:1px'>Estimado (a): "+nombre+"</p></td>\n");
//				body.append("							</tr>\n");
//				body.append("						</table>\n");
//				body.append("					</td>\n");
//				body.append("				</tr>\n");
//				body.append("			</table>\n");

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
				body.append("					<td>" +
						//"<img src='http://www.invextarjetas.com.mx/msi/fotoITAU.jpg' width='630' height='234'>" +
						"</td>\n");
				body.append("				</tr>\n");
				body.append("				<tr>\n");
				
				body.append("<td><p style='color:000; font-family:Arial, Helvetica, sans-serif; font-size:26px; margin:30 0px 25 0px; text-align:center;'>Se han registrado las compras de su<br> tarjeta terminaci&oacute;n " + cuenta.substring(cuenta.length()-4) + " a<br> <span style='color:#A30234;'><b>meses sin intereses</b></span> con el folio: "+folio +"</p></td>\n");
				
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
					//logger.info(montoAPagar);
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

				body.append("<td bgcolor='#FFFFFF' colspan='3'>" +
						//"<img src='http://www.invextarjetas.com.mx/msi/footITAU.jpg' width='650' height='202' border='0'>" +
						"</td>\n");
				body.append("</tr>\n");

				body.append("<tr>\n");
				body.append("<td bgcolor='#FFFFFF' colspan='3'>\n");

				body.append("</td>\n");
				body.append("</tr>\n");

				body.append("<tr>\n");
				body.append("<td>\n");
				body.append("</td>\n");
				body.append("<td>\n");
//				body.append("<p style='color:#000; font-family:Arial, Helvetica, sans-serif; font-size:7.5px;'>\n");
//				body.append("<b>CAT Promedio 0.0% sin IVA</b> para meses sin intereses. Informativo. Fecha de c&aacute;lculo: abril 2014. Tasa Variable. Tasa Anual. Tasa de inter&eacute;s promedio ponderada por saldo para tarjetas SÌCard Plus 58.6%, comisi&oacute;n anual $610 pesos sin IVA. Tasa de inter&eacute;s promedio ponderada por saldo para tarjetas S&iacute;Card Plus INVEX 47.2%, comisi&oacute;n anual $610 pesos sin IVA. Tasa de inter&eacute;s promedio ponderada por saldo para tarjetas S&iacute;Card Platinum 57.3%, comisi&oacute;n anual $1,350 pesos sin IVA. Tasa de inter&eacute;s promedio ponderada por saldo para tarjetas S&iacute;Card Platinum INVEX 39.3%, comisi&oacute;n anual $1,350 pesos sin IVA. Tasa de inter&eacute;s promedio ponderada por saldo para tarjetas SiCard Plus MC 46.5%, comisi&oacute;n anual $610 pesos sin IVA. Tasa de inter&eacute;s promedio ponderada por saldo para tarjetas BAM 32.4%, comisi&oacute;n anual $595 pesos sin IVA. Tasa de inter&eacute;s promedio ponderada por saldo para tarjetas Manchester United INVEX 30.5%, comisi&oacute;n anual $595 pesos sin IVA Tasa de inter&eacute;s promedio ponderada por saldo para tarjetas Volaris INVEX 45.9%, comisi&oacute;n anual $1,200 pesos sin IVA. Consulta requisitos de contrataci&oacute;n y comisiones en www.invextarjetas.com.mx\n");
//				body.append("<br /><br />\n");
//				body.append("Banco INVEX S.A., Instituci&oacute;n de Banca M&uacute;ltiple, INVEX Grupo Financiero.\n");
//				body.append("</p>\n");
				body.append("</td>\n");
				body.append("</tr>\n");

				body.append("</table>\n");
//				body.append("</body>\n");
//				body.append("</html>\n");
					}
			
				//EnviarMail.send(destinatario, ccAddress, bccAddress, MSIConstants.SUBJECT, isHTMLFormat, body, false);
				
				return body.toString();
			}
			
			
			if(!tipoProd.startsWith("Volaris")){

//				body.append("<html>\n");
//				body.append("<head>\n");
//				body.append("<title>mailing</title>\n");
//				body.append("<meta http-equiv='Content-Type' content='text/html; charset='utf-8'>\n");
//				body.append("</head>\n");
//				body.append("<body bgcolor='#FFFFFF' topmargin='0'>\n");
			//	body.append("\n");


				body.append("<table width='628' border='0' align='center' cellpadding='0' cellspacing='0'>\n");
				body.append("	<tr>\n");
				body.append("		<td width='650' align='right' bgcolor='#a30234' colspan='3'>\n");
				body.append("			<table width='650' border='0' cellspacing='0' cellpadding='0'>\n");
				body.append("				<tr>\n");
				body.append("					<td width='299' bgcolor='#a30234'>" +
						//"<img src='http://www.invextarjetas.com.mx/msi/head02.jpg' width='299' height='87' border='0'>" +
						"</td>\n");
				body.append("					<td width='351' valign='top'>" );
//						"<table width='351' border='0' cellspacing='0' cellpadding='0'>\n");
//				body.append("							<tr>\n");
//				body.append("								<td height='87' valign='middle' bgcolor='#a30234'><p style='font-size:15px; text-align:right; color:#FFF; font-family:Arial, Helvetica, sans-serif; margin-right:20px; margin-top:5px; margin-bottom:4px;'>"+Character.toUpperCase(fecha.charAt(0))+fecha.substring(1)+"</p>\n");
//				body.append("									<p style='font-size:15px; text-align:right; color:#FFF; font-family:Arial, Helvetica, sans-serif; margin-right:20px; margin-top:0px; margin-bottom:1px'>Estimado (a): "+nombre+"</p></td>\n");
//				body.append("							</tr>\n");
//				body.append("						</table>\n");
//				body.append("					</td>\n");
//				body.append("				</tr>\n");
//				body.append("			</table>\n");

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
				body.append("					<td>" +
						//"<img src='http://www.invextarjetas.com.mx/msi/foto-invex.jpg' width='630' height='234'>" +
						"</td>\n");
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

				body.append("<td bgcolor='#FFFFFF' colspan='3'>" +
						//"<img src='http://www.invextarjetas.com.mx/msi/foot.jpg' width='650' height='202' border='0'>" +
						"</td>\n");
				body.append("</tr>\n");

				body.append("<tr>\n");
				body.append("<td bgcolor='#FFFFFF' colspan='3'>\n");

				body.append("</td>\n");
				body.append("</tr>\n");

				body.append("<tr>\n");
				body.append("<td>\n");
				body.append("</td>\n");
				body.append("<td>\n");
//				body.append("<p style='color:#000; font-family:Arial, Helvetica, sans-serif; font-size:7.5px;'>\n");
//				body.append("<b>CAT Promedio 0.0% sin IVA</b> para meses sin intereses. Informativo. Fecha de c&aacute;lculo: abril 2014. Tasa Variable. Tasa Anual. Tasa de inter&eacute;s promedio ponderada por saldo para tarjetas SÌCard Plus 58.6%, comisi&oacute;n anual $610 pesos sin IVA. Tasa de inter&eacute;s promedio ponderada por saldo para tarjetas S&iacute;Card Plus INVEX 47.2%, comisi&oacute;n anual $610 pesos sin IVA. Tasa de inter&eacute;s promedio ponderada por saldo para tarjetas S&iacute;Card Platinum 57.3%, comisi&oacute;n anual $1,350 pesos sin IVA. Tasa de inter&eacute;s promedio ponderada por saldo para tarjetas S&iacute;Card Platinum INVEX 39.3%, comisi&oacute;n anual $1,350 pesos sin IVA. Tasa de inter&eacute;s promedio ponderada por saldo para tarjetas SiCard Plus MC 46.5%, comisi&oacute;n anual $610 pesos sin IVA. Tasa de inter&eacute;s promedio ponderada por saldo para tarjetas BAM 32.4%, comisi&oacute;n anual $595 pesos sin IVA. Tasa de inter&eacute;s promedio ponderada por saldo para tarjetas Manchester United INVEX 30.5%, comisi&oacute;n anual $595 pesos sin IVA Tasa de inter&eacute;s promedio ponderada por saldo para tarjetas Volaris INVEX 45.9%, comisi&oacute;n anual $1,200 pesos sin IVA. Consulta requisitos de contrataci&oacute;n y comisiones en www.invextarjetas.com.mx\n");
//				body.append("<br /><br />\n");
//				body.append("Banco INVEX S.A., Instituci&oacute;n de Banca M&uacute;ltiple, INVEX Grupo Financiero.\n");
//				body.append("</p>\n");
				body.append("</td>\n");
				body.append("</tr>\n");

				body.append("</table>\n");
//				body.append("</body>\n");
//				body.append("</html>\n");
				return body.toString();

			}else{
				//crear correo volaris

//				body.append("<html>\n");
//				body.append("<head>\n");
//				body.append("<title>mailing</title>\n");
//				body.append("<meta http-equiv='Content-Type' content='text/html; charset=utf-8'>\n");
//				body.append("</head>\n");
//				body.append("<body bgcolor='#FFFFFF' topmargin='0'>\n");
				body.append("<table width='650' border='0' align='center' cellpadding='0' cellspacing='0'>\n");
				body.append("<tr>\n");
				body.append("<td width='650' align='right' bgcolor='#000' colspan='3'>\n");
				body.append("<table width='650' border='0' cellspacing='0' cellpadding='0'>\n");
				body.append("<tr>\n");
				body.append("<td width='287' height='110' bgcolor='#000000'>\n");
				body.append("" +
						//"<img src='http://www.invextarjetas.com.mx/msi/head02v.jpg' width='287' height='110' border='0'>" +
						"</td>\n");
				body.append("<td width='361' valign='top'>\n");
//				body.append("<table width='361' border='0' cellspacing='0' cellpadding='0'>\n");
//				body.append("<tr>\n");
//				body.append("<td width='361' bgcolor='#000000'>\n");
//				body.append("" +
//						//"<img src='http://www.invextarjetas.com.mx/msi/head01.jpg' width='361' height='49' border='0'>" +
//						"</td>\n");
//				body.append("</tr>\n");
//				body.append("<tr>\n");
//				body.append("<td bgcolor='#000000'>\n");
//				body.append("<p style='font-size: 15px; text-align: right; color: #FFF; font-family: Arial, Helvetica, sans-serif; margin-right: 20px; margin-top: 5px;'>"+Character.toUpperCase(fecha.charAt(0))+fecha.substring(1)+"</p>\n");
//				body.append("<p style='font-size: 15px; text-align: right; color: #FFF; font-family: Arial, Helvetica, sans-serif; margin-right: 20px; margin-top: 0px; margin-bottom: 1px'>Estimado (a): "+nombre+"</p></td>\n");
//				body.append("</tr>\n");
//				body.append("</table>\n");
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
				body.append("<td>" +
						//"<img src='http://www.invextarjetas.com.mx/msi/foto-volaris.jpg' width='630' height='234'>" +
						"</td>\n");
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

				body.append("<td bgcolor='#FFFFFF' colspan='3'>" +
						//"<img src='http://www.invextarjetas.com.mx/msi/footes.jpg' width='650' height='270'>" +
						"</td>\n");
				body.append("</tr>\n");

				body.append("<tr>\n");
				body.append("<td colspan='3'>&nbsp;</td>\n");
				body.append("</tr>\n");
				body.append("<tr>\n");
				body.append("<td>\n");
				body.append("</td>\n");
				body.append("<td>\n");
//				body.append("<p style='color:#000; font-family:Arial, Helvetica, sans-serif; font-size:7.5px;'>\n");
//				body.append("<b>CAT Promedio 0.0% sin IVA</b> para meses sin intereses. Informativo. Fecha de c&aacute;lculo: abril 2014. Tasa Variable. Tasa Anual. Tasa de inter&eacute;s promedio ponderada por saldo para tarjetas SÌCard Plus 58.6%, comisi&oacute;n anual $610 pesos sin IVA. Tasa de inter&eacute;s promedio ponderada por saldo para tarjetas S&iacute;Card Plus INVEX 47.2%, comisi&oacute;n anual $610 pesos sin IVA. Tasa de inter&eacute;s promedio ponderada por saldo para tarjetas S&iacute;Card Platinum 57.3%, comisi&oacute;n anual $1,350 pesos sin IVA. Tasa de inter&eacute;s promedio ponderada por saldo para tarjetas S&iacute;Card Platinum INVEX 39.3%, comisi&oacute;n anual $1,350 pesos sin IVA. Tasa de inter&eacute;s promedio ponderada por saldo para tarjetas SiCard Plus MC 46.5%, comisi&oacute;n anual $610 pesos sin IVA. Tasa de inter&eacute;s promedio ponderada por saldo para tarjetas BAM 32.4%, comisi&oacute;n anual $595 pesos sin IVA. Tasa de inter&eacute;s promedio ponderada por saldo para tarjetas Manchester United INVEX 30.5%, comisi&oacute;n anual $595 pesos sin IVA Tasa de inter&eacute;s promedio ponderada por saldo para tarjetas Volaris INVEX 45.9%, comisi&oacute;n anual $1,200 pesos sin IVA. Consulta requisitos de contrataci&oacute;n y comisiones en www.invextarjetas.com.mx\n");
//				body.append("<br /><br />\n");
//				body.append("Banco INVEX S.A., Instituci&oacute;n de Banca M&uacute;ltiple, INVEX Grupo Financiero.\n");
//				body.append("</p>\n");
				body.append("</td>\n");
				body.append("</tr>\n");

				body.append("</table>\n");
//				body.append("</body>\n");
//				body.append("</html>\n");

				return body.toString();
			}
			

			//EnviarMail.send(destinatario, ccAddress, bccAddress, MSIConstants.SUBJECT, isHTMLFormat, body, false);

		}catch(Exception e){

		
			e.printStackTrace();
					
		}		


		return body.toString();
	}

}
