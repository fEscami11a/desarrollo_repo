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

import mx.com.interware.spira.mgb.ActualizacionMGB;
import mx.com.interware.spira.mgb.MGB;
import mx.com.interware.spira.mgb.MGBImpl;

import org.apache.log4j.Logger;
@WebServlet("/UploadUCode1File")
@ServletSecurity(
@HttpConstraint(
    rolesAllowed = {"USR_TSYS"}))
public class UploadUCode1File extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(UploadUCode1File.class);
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
							     			
							     			//consultar cuenta
//							     			SI01ResponseDTO responseDTO = servicesSI01.getSI01ResponseDTO(cuenta);
//							     			if(!"2".equals(responseDTO.getUSER_CODE_2_POS0()) && !"4".equals(responseDTO.getUSER_CODE_2_POS0())){
							     				 String ucode1= datos[1];
								                  ActualizacionMGB actMGB = new ActualizacionMGB();
								                  actMGB.setAccount(cuenta);
								                  actMGB.setUserCodeOne(ucode1);
								                  actMGB.setUserName(request.getRemoteUser());
								                  MGB mgb = new MGBImpl();
								                  resp = mgb.updateMGB(actMGB);
								       
								                  	 log.info("res mgb update ucode1 "+resp);
								                  	 if("OK".equalsIgnoreCase(resp)){
								                  		 ++regsEnviados;
								                  	 }else{
								                  		 throw new Exception("ERROR "+resp);
								                  	 }
							     			//}
							     			
						                 
						                  	
						                  	 
						                  	 
					                  	 }//fin if ndato
					                  	 
					                      //out.println(linea);
				                	   }//fin if
				                	   }//fin do
				                   while (linea != null);
				                   
				                   out.println("Registros enviados exitosas: "+regsEnviados);
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