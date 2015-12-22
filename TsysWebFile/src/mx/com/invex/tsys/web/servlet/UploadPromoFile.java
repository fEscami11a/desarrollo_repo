package mx.com.invex.tsys.web.servlet;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.com.interware.spira.ncm.NCM;
import mx.com.interware.spira.ncm.NCMImpl;
import mx.com.interware.spira.web.mxp.MXPParam;
import mx.com.interware.spira.web.mxp.MXPWS;

import org.apache.log4j.Logger;
@WebServlet("/UploadPromoFile")
@ServletSecurity(
@HttpConstraint(
    rolesAllowed = {"USR_TSYS"}))
public class UploadPromoFile extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(UploadPromoFile.class);
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 PrintWriter out = response.getWriter();
		try{
			log.info("enviar promos");
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
				                   MXPWS ws = new MXPWS();
				                   NCM ncmws = new NCMImpl();
				                   
				              	 String msgType = "01";
				              	 String collNum = "999999";
				              	 String actionCode = "CS";

				              	 String resp="";
				              
				              	 
				                   int promosEnviadas = 0;
				                   do{
				                	   linea = reader.readLine();
				                	   log.info(linea);
				                	   if(linea!= null){
				                		StringTokenizer tokens=new StringTokenizer(linea, "|");
					                  	 int nDatos=tokens.countTokens();
					                  	 //con mesnaje icm son 5
					                  	 if(nDatos>=4){
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
						                  	 MXPParam param = new MXPParam();
						                  	 param.setAccount(cuenta);
						                  	 param.setBalanceId(datos[1]);
						                  	 param.setPromoId(datos[2]);
						                  	 param.setAmount(datos[3]);
						                  	 param.setUserName(request.getRemoteUser());
						                  	 if(nDatos==5){
						                  	 	param.setDescription(datos[4]);
						                  	 }
						                  	 //llamar al aservicio de mxp
						                  	 String res =ws.updateMXP(param);
						                  	 log.info("res mxp "+res);
						                  	 if("OK".equalsIgnoreCase(res)){
						                  		 ++promosEnviadas;
						                  	 }else{
						                  		out.println("Error: "+res);
						                  	 } 	 
						                  	 
					                  	 }//fin if ndato
					                  	 
					                      //out.println(linea);
				                	   }//fin if
				                	   }//fin do
				                   while (linea != null);
				                   
				                   out.println("Promociones enviadas exitosas: "+promosEnviadas);
				                  
				                 }
		}catch(Exception e){
			e.printStackTrace();
			out.print("Error: "+e.getMessage());
			log.error(e.getMessage());
		}finally{
			 out.flush();
             out.close();
		}
		 
	}
}