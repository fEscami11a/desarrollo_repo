package mx.com.invex.tsys.web.servlet;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.tsys.xmlmessaging.ch2.ReqAcctTermTransfer;
import com.tsys.xmlmessaging.ch2.ReqAcctTermTransferRequestType;
import com.tsys.xmlmessaging.ch2.ReqAcctTermTransferRequestType.TermTransfer;
import com.tsys.xmlmessaging.ch2.ReqAcctTermTransferRequestType.TransferFrom;
import com.tsys.xmlmessaging.ch2.ReqAcctTermTransferResponseType;
import com.tsys.xmlmessaging.ch2.TSYSprofileType;

import mx.com.invex.ws.client.ClientTs2;
@WebServlet("/UploadReqAcctTermTransferFile")
public class UploadReqAcctTermTransfer extends HttpServlet{
	
	
private static final Logger log = Logger.getLogger(UploadReqAcctTermTransfer.class);
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.info("reqAcctTemr");
		log.info("remopte suser"+request.getRemoteUser());
		log.info("remopte host"+request.getRemoteHost());
		  PrintWriter out = response.getWriter();
		try{
			
		 String contentType = request.getContentType();
		
		 if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0)) {
				                 DataInputStream in = new DataInputStream(request.
				 getInputStream());
				               
				                 //we are taking the length of Content type data
				                 int formDataLength = request.getContentLength();
				                 byte dataBytes[] = new byte[formDataLength];
				                 int byteRead = 0;
				                 int totalBytesRead = 0;
				                 //this loop converting the uploaded file into byte code
				                 while (totalBytesRead < formDataLength) {
				                         byteRead = in.read(dataBytes, totalBytesRead, 
				 formDataLength);
				                         totalBytesRead += byteRead;
				                         }
				                                         String file = new String(dataBytes);
				                 //for saving the file name
				                 String saveFile = file.substring(file.indexOf("filename=\"") + 10);
				               
				                 
				                 saveFile = saveFile.substring(0, saveFile.indexOf("\n"));
				                 saveFile = saveFile.substring(saveFile.lastIndexOf("\\")
				  + 1,saveFile.indexOf("\""));
				                 int lastIndex = contentType.lastIndexOf("=");
				                 String boundary = contentType.substring(lastIndex + 1,
				 contentType.length());
				                 int pos;
				                 //extracting the index of file 
				                 pos = file.indexOf("filename=\"");
				                 pos = file.indexOf("\n", pos) + 1;
				                 pos = file.indexOf("\n", pos) + 1;
				                 pos = file.indexOf("\n", pos) + 1;
				                 int boundaryLocation = file.indexOf(boundary, pos) - 4;
				                 int startPos = ((file.substring(0, pos)).getBytes()).length;
				                 int endPos = ((file.substring(0, boundaryLocation)).getBytes()).length;
				                 // creating a new file with the same name and writing the content in new file
				                 FileOutputStream fileOut = new FileOutputStream("\\"+saveFile);
				                 fileOut.write(dataBytes, startPos, (endPos - startPos));
				                 fileOut.flush();
				                 fileOut.close();
				                 
				                 //leer archivo
				                 BufferedReader reader = new BufferedReader(new FileReader("\\"+saveFile));
				                 
				                
				                   out.println("Archivo recibido :"+saveFile); 
				                   String linea;
				                  
				              	 String resp="";
				              
				              	 
				                   int regsEnviados = 0;
				                   do{
				                	   linea = reader.readLine();
				                	   log.info(linea);
				                	   if(linea!= null){
				                		StringTokenizer tokens=new StringTokenizer(linea, "|");
					                  	 int nDatos=tokens.countTokens();
					                  	 //con mesnaje icm son 5
					                  	 if(nDatos==2){
						                     String[] datos=new String[nDatos];
						                     int i=0;
						                     while(tokens.hasMoreTokens()){
						                         datos[i]=tokens.nextToken();
						            
						                         i++;
						                     }
						                  String cuenta = datos[0];
						                  
						                  Pattern p = Pattern.compile("^\\d{16}$");
						     		     Matcher m = p.matcher(cuenta);
						     		     
						     			if(!m.matches()){
						     				log.error("La cuenta debe ser de 16 digitos");
						     				throw new Exception("La cuenta debe ser de 16 digitos");
						     			}
						                  
						                  String idPromo= datos[1];
						                  String monto = datos[2];
						                 if( reqAcctTermTransfer(cuenta,idPromo,monto)){
						                	 ++regsEnviados;
						                 }else{
						                	 log.error("ERROR cuenta "+ cuenta + " promo "+ idPromo+" monto "+monto);
						                	 out.println("ERROR cuenta "+ cuenta + " promo "+ idPromo +" monto "+monto);
						                 }
						                  
//						                  ActualizacionMGB actMGB = new ActualizacionMGB();
//						                  actMGB.setAccount(cuenta);
//						                  actMGB.setAgentBank(agente);
//						                  actMGB.setUserName(request.getRemoteUser());
//						                  MGB mgb = new MGBImpl();
//						                  resp = mgb.updateMGB(actMGB);
						       
						                  	// log.info("res mgb update agent "+resp);
						                  	// if("OK".equalsIgnoreCase(resp)){
						                  		
						                  	 //}
						                  	
						                  	 
						                  	 
					                  	 }//fin if ndato
					                  	 
					                      //out.println(linea);
				                	   }//fin if
				                	   }//fin do
				                   while (linea != null);
				                   
				                   out.println("Registros enviados exitosas: "+regsEnviados);
				                  
				                 }
		}catch(Exception e){
			 out.println("Error: "+e.getMessage());
			 log.error(e.getMessage());
			e.printStackTrace();
		}finally{
			 out.flush();
             out.close();
		}
		 
	}
	
	
	private boolean reqAcctTermTransfer(String account,String idPromo,String monto){
		TSYSprofileType tp = new TSYSprofileType();
		tp.setClientID("7401");
		tp.setUserID("invdev");
		tp.setVendorID("00000000");
		log.info("instaciar cliente");
		ClientTs2 cts2 = new ClientTs2();

		ReqAcctTermTransfer reqAcctTermTransfer = new ReqAcctTermTransfer();
		ReqAcctTermTransferRequestType reqAcctTermTransferReq = new ReqAcctTermTransferRequestType();
		reqAcctTermTransferReq.setKeyType("cardNbr");
		reqAcctTermTransferReq.setKey(account);
		reqAcctTermTransferReq.setVersion("2.1.0");


		TermTransfer termTransf= new TermTransfer();
		termTransf.setTransferToType("Installment");
		com.tsys.xmlmessaging.ch2.Boolean assessFee2 = new com.tsys.xmlmessaging.ch2.Boolean();
		assessFee2.setValue(false);
		termTransf.setAssessFee(assessFee2);

		termTransf.setTransferToTLPOpt(idPromo);

		//		TranDesc tdesc = new TranDesc();
		//		tdesc.setCreditDesc(compra.getDescripcion());
		//		tdesc.setDebitDesc(compra.getDescripcion());
		//		termTransf.setTranDesc(tdesc);

		reqAcctTermTransferReq.setTermTransfer(termTransf);
		TransferFrom transferFrom = new TransferFrom();
		List<ReqAcctTermTransferRequestType.TransferFrom.TBAL> tbals2=transferFrom.getTBAL();

		ReqAcctTermTransferRequestType.TransferFrom.TBAL tbal2 = new ReqAcctTermTransferRequestType.TransferFrom.TBAL();
		tbal2.setCode("0001");
		ReqAcctTermTransferRequestType.TransferFrom.TBAL.AmtToMove amtToMove2 = new ReqAcctTermTransferRequestType.TransferFrom.TBAL.AmtToMove();
		amtToMove2.setCode("MXN");
		amtToMove2.setValue(new BigDecimal(monto));
		tbal2.setAmtToMove(amtToMove2);

		tbals2.add(tbal2);
		reqAcctTermTransferReq.setTransferFrom(transferFrom);
		reqAcctTermTransfer.setReqAcctTermTransferRequest(reqAcctTermTransferReq);



		ReqAcctTermTransferResponseType reqAcctTermTransferRes = cts2.reqAcctTermTransfer(tp,
				reqAcctTermTransfer)
				.getReqAcctTermTransferResult();
		String status = reqAcctTermTransferRes.getStatus();
		if (!"000".equals(status)) {
			String msg = reqAcctTermTransferRes.getStatusMsg();
			log.info(msg);
			com.tsys.xmlmessaging.ch2.TSYSfaultType fault = reqAcctTermTransferRes
					.getFaults();
			List<com.tsys.xmlmessaging.ch2.TSYSfault> lfaulta = fault
					.getFault();
			for (com.tsys.xmlmessaging.ch2.TSYSfault sfault : lfaulta) {
				log.info(sfault.getStatus() + " "
						+ sfault.getFaultDesc());
			}
			return false;

		}
		return true;
}
}
