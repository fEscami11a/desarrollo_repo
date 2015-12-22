/*
 * Created on Oct 7, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package mx.com.interware.spira.ncm;
import org.apache.log4j.Logger;
/**
 * 
 * Class TestCOLL.java
 * Package mx.com.interware.spira.web.coll
 * Created on Oct 7, 2005
 * @author Marcos Díaz
 * @version 1.0
 * 
 * 
 * 
 * @param
 * @return
 * @see
 * 
 * @throws IOException  If an input or output exception occurred
 * @throws ServletException  If an servlet exception occurred
 * 
 */
public class TestNCM {
	private static Logger log = Logger.getLogger(TestNCM.class);

	public static void main(String[] args) {
     NCM ncmws = new NCMImpl();

   String account = "4461380000218610";
	 String msgType = "01";
	 String collNum = "999999";
	 String actionCode = "CS";
	 String msgText = "DOCUMENTOS COMPLETOS MESA DE CONTROL";
	 String userName="LineaSpira"; 
	 String accessKey ="prodLS615#";
	 String resp="";
	 String date = "20060830";
	 String time = "101500";
	 
	 try{
	 resp = ncmws.addCollectionMessage(account, userName,date, time, collNum, msgType, actionCode, msgText);
	 log.info("Respuesta: "+ resp);
	 }
	 catch(Exception ex){
	 	log.error(ex.toString());
	 }
     
	}
}
