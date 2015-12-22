package mx.com.invex.tsys.web.servlet;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import com.tsys.xmlmessaging.ch.InqCurrBalByTBALResponseType;
import com.tsys.xmlmessaging.ch.NTBTBALsResponseDataType;
import com.tsys.xmlmessaging.ch.TSYSfault;
import com.tsys.xmlmessaging.ch.TSYSfaultType;

import mx.com.invex.ws.client.ClientTs2;
@WebServlet("/UploadPPNGI")
public class UploadPPNGI extends HttpServlet{
	private static final Logger log = Logger.getLogger(UploadPPNGI.class);
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.info("actualizar agente");
		log.info("remopte suser"+request.getRemoteUser());
		log.info("remopte host"+request.getRemoteHost());
		  //PrintWriter out = response.getWriter();
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
				                 
				                
				                //   out.println("Archivo recibido :"+saveFile); 
				                   String linea;
				                  
				              	 //String resp="";
				              List<String> lcuentas = new ArrayList<String>();
				              	 
				                   int regsEnviados = 0;
				                   HSSFWorkbook workbook = new HSSFWorkbook();
				            		// ...
				            		// Now populate workbook the usual way.
				            		HSSFSheet worksheet = workbook.createSheet("Hoja 1");
				                   do{
				                	   linea = reader.readLine();
				                	   log.info(linea);
				                	   if(linea!= null && !linea.trim().isEmpty()){
//				                		StringTokenizer tokens=new StringTokenizer(linea, "|");
//					                  	 int nDatos=tokens.countTokens();
//					                  	 //con mesnaje icm son 5
//					                  	 if(nDatos==2){
//						                     String[] datos=new String[nDatos];
//						                     int i=0;
//						                     while(tokens.hasMoreTokens()){
//						                         datos[i]=tokens.nextToken();
//						            
//						                         i++;
//						                     }
						                  String cuenta = linea;
						                  lcuentas.add(cuenta);
						                  Pattern p = Pattern.compile("^\\d{16}$");
						     		     Matcher m = p.matcher(cuenta);
						     		     
						     			if(!m.matches()){
						     				log.error("La cuenta debe ser de 16 digitos");
						     				throw new Exception("La cuenta debe ser de 16 digitos");
						     			}
				                	   }
				                	   }while (linea != null && !linea.trim().isEmpty());
						       
//						                  	 log.info("res mgb update agent "+resp);
//						                  	 if("OK".equalsIgnoreCase(resp)){
//						                  		 ++regsEnviados;
//						                  	 }
						                  	
						                  	 
						                  	response.setContentType("application/vnd.ms-excel");
						            		response.setHeader("Content-Disposition", "attachment; filename=filename.xls");
						            		
						            		for (String string : lcuentas) {
						            			log.info("cuenta str"+string);
							            		HSSFRow row1 = worksheet.createRow(regsEnviados);
							            		++regsEnviados;
							            		HSSFCell cellA1 = row1.createCell((short) 0);
							            		cellA1.setCellValue( string);
//							            		HSSFCellStyle cellStyle = workbook.createCellStyle();
//							            		cellStyle.setFillForegroundColor(HSSFColor.GOLD.index);
//							            		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//							            		cellA1.setCellStyle(cellStyle);
							            		
							            		ClientTs2 cts2 = new ClientTs2();
							            		InqCurrBalByTBALResponseType resp=cts2.inqCurrBalByTBAL(string).getInqCurrBalByTBALResult();
							            		String respStr="";

							            		if (!"000".equals(resp.getStatus())) {
							            			
							            			TSYSfaultType fault = resp.getFaults();
							            			List<TSYSfault> lfaulta = fault.getFault();
							            			for (TSYSfault sfault : lfaulta) {
							            				respStr=sfault.getStatus() + " "
							            						+ sfault.getFaultDesc();
							            			}
							            			
							            		}else{
							            		
							            		
							            		double ppngi=0;
							            		
													try {
														List<NTBTBALsResponseDataType.TBAL> tbals = resp.getTBALs()
																.getValue().getTBAL();
														for (NTBTBALsResponseDataType.TBAL tbal : tbals) {
															String id = tbal.getId();
															if ("0001".equals(id) || "0002".equals(id)) {
																ppngi += tbal.getAmtPrevCycleUnpaid().getValue()
																		.doubleValue();
																ppngi += tbal.getBalPrev().getFinChrg().getValue()
																		.doubleValue();
															}
														}
													} catch (Exception e) {
														// TODO Auto-generated catch block
														e.printStackTrace();
													} 
												
												respStr=""+ppngi;
							            		}
	
							            		HSSFCell cellB1 = row1.createCell((short) 1);
							            		cellB1.setCellValue(respStr);
//							            		cellStyle = workbook.createCellStyle();
//							            		cellStyle.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
//							            		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//							            		cellB1.setCellStyle(cellStyle);
						            		}
					                    
				                	 
				                   
				                   workbook.write(response.getOutputStream()); // Write workbook to response.
				           		workbook.close();
				                   
				                   
				                   
				               //    out.println("Registros enviados exitosas: "+regsEnviados);
				                  
				                 }
		}catch(Exception e){
			 //out.println("Error: "+e.getMessage());
			 log.error(e.getMessage());
			e.printStackTrace();
		}
		 
	}
	
	
	
}