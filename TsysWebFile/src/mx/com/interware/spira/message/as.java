/*
 * Created on Nov 7, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package mx.com.interware.spira.message;

import java.io.ByteArrayOutputStream;

import org.apache.log4j.Logger;
class as{
	private static Logger log = Logger.getLogger(as.class);
		public static void main(String[] args)  {
		TPacketImpl packet = new TPacketImpl();
			String as=null;
			
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream(); 
			String cosa="";
					
		try{
			packet.convertToDefaultFLDC();
			packet.writeRequestTo(byteOut);
		
			cosa = byteOut.toString();
			packet.writeRequestTo(System.out);
			log.info(cosa);

		}
		catch(Exception e){
		 log.error(e);
		}
			
	
		}
	}

