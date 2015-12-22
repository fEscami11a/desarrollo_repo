/*
 * Created on Nov 1, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package mx.com.interware.spira.web;
import java.io.File;

import mx.com.interware.util.Throwable2String;
import mx.com.interware.xml.XMLString;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
/**
 * @author mdiaz
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class read {
	private static Logger log = Logger.getLogger(read.class);

	public static void main(String[] args) {
		File defaultXML=new File("WebContent/WEB-INF/XML/config.xml");
		try {
			XMLString xml =new XMLString(defaultXML);
			Element uno = (Element)xml.getDocument().getFirstChild();
				 Node          node;
				 Element       element;
				 NamedNodeMap  nnm = null;
			String attrname;
			String attrval;
			int    i, len;
			nnm= uno.getAttributes();
			if (nnm != null) {
				for (i=0; i<nnm.getLength(); i++) {
					node = nnm.item(i);
					attrname = node.getNodeName();
					attrval  = node.getNodeValue();
					log.info(" " + attrname + " = " + attrval);
				}
			}


		}	catch (Exception e) {
			log.error("problemas al tratar de usar configuracion default "+e);
			throw new RuntimeException("problemas al tratar de usar configuracion default \n"+Throwable2String.throwable2String(e));
		}
	}
		

}

