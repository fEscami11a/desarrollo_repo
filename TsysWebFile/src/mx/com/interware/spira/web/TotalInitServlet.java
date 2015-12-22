package mx.com.interware.spira.web;

import java.io.File;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import mx.com.interware.spira.message.TPacketImpl;
import mx.com.interware.xml.XMLString;

import org.w3c.dom.Element;

/**
 * @version 	1.0
 * @author
 */
@WebServlet(
        urlPatterns = "/TotalInitServlet",
        loadOnStartup = 1,
        asyncSupported = true
)
public class TotalInitServlet extends HttpServlet implements Servlet {

  
	/**
	 *
	 */
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		String confFileLoc=config.getServletContext().getRealPath("WEB-INF/XML/packet.config.xml");
		File configFile=new File(confFileLoc);
		System.out.println(configFile.getAbsolutePath());
		
		
		
		try {
			XMLString xml = new XMLString(configFile);
			
			TPacketImpl.init((Element) xml.getDocument().getFirstChild());
			
		}
		catch (Exception e) {
			
			throw new ServletException("Problemas de inicializacion",e);
		}
		//log.info();
		AbstractTSYSMessage.setQM_HOST("172.16.18.139");
		AbstractTSYSMessage.setPORT("1414");
		AbstractTSYSMessage.setQM_NAME("SPIRAPROD");		
		AbstractTSYSMessage.setCHANNEL("JAVA.CHANNEL");
		AbstractTSYSMessage.setQ_PREGUNTA("PREGUNTA");
		AbstractTSYSMessage.setQ_RESPUESTA("RESPUESTA");
		AbstractTSYSMessage.setCACHE_SIZE("100");
		AbstractTSYSMessage.setRECEIVER_MAX_WAIT("6000");
		AbstractTSYSMessage.setSENDER_TIME_TO_LIVE("5000");
//		try {
//			InitialContext ctx=new InitialContext();
//			Object o=ctx.lookup("java:comp/env/send_queue");
//			log.info(o);
//		}
//		catch (NamingException e1) {
//	log.error(e1);
//		}
	}

}
