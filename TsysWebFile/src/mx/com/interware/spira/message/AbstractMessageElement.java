package mx.com.interware.spira.message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import mx.com.interware.spira.message.exceptions.InvalidFormatException;
import mx.com.interware.spira.message.util.FmtUtil;
import mx.com.interware.spira.message.util.Formatter;
import mx.com.interware.spira.message.util.VariableFormatter;
import mx.com.interware.xml.XMLString;


public abstract class AbstractMessageElement implements MessageElement {
	private static Logger log=Logger.getLogger(AbstractMessageElement.class);
	
	private TotalMessage parentMessage;
	
	protected String name;
	protected Object value;
	private Formatter formatter;
	protected String depends;

	public String getName() {
		return name;
	}

	public Object getValue() {
		return value;
	}

	public void setName(String string) {
		name = string;
	}

	public void setValue(Object object) throws InvalidFormatException{		
		value = object;
	}

	public void setFormatter(Formatter formatter) {
		this.formatter = formatter;
	}

	public Formatter getFormatter() {
		if (sizeElement!=null) {
			((VariableFormatter)formatter).setLength(sizeElement.getValue()); 
		}
		return formatter;
	}

	public void init(Element element) throws InvalidFormatException, InstantiationException, IllegalAccessException {
		//try {
				//log.info("Nombre del Elemento :"+element.getAttribute("name").toString());
				//log.info("Formato del Elemento: "+element.getAttribute("fmt").toString());
			setName(element.getAttribute("name"));
			String fmt = element.getAttribute("fmt").trim();
			if (fmt.length() > 0) {
				Formatter formatter = FmtUtil.getFormatter(element.getAttribute("fmt"));
				//log.debug("Formatter for:" + element.getAttribute("fmt") + " is :" + formatter);
				setFormatter(formatter);
			}
			if (element.hasAttribute("depends")) {
				depends = element.getAttribute("depends");
			}
//		}
//		catch (Exception e) {
//			log.debug(e.fillInStackTrace());
//			log.debug("VMBR Element: "+element);
//			log.debug("VMBR Name: "+getName());
//			log.debug("VMBR Formatter: "+getFormatter());
//		}
	}
	
	public static void main(String[] args) {
		Document doc=XMLString.newDocument();
		Element e=doc.createElement("struct");
		String s=e.getAttribute("noexiste");
		log.info("["+s+"]");
	}

	public void writeTo(OutputStream out) throws IOException {
//		if (getName().trim().equals("length") || getName().trim().equals("id") 
//		|| getName().trim().equals("version") || getName().trim().equals("error")
//		|| getName().trim().equals("packetID") || getName().trim().equals("clientBankID")
//		|| getName().trim().equals("accountID") || getName().trim().equals("accountType")
//		|| getName().trim().equals("applicationID")) { // ELIMINAR
//			log.info("writeTo(OutputStream)  Name & Value: "+Thread.currentThread()+" :: "+getName()+" :: "+getValue()); // ELIMINAR
//		} // ELIMINAR
		
		getFormatter().writeTo(getValue(),out);
	}
	
	public void readFrom(InputStream in) throws InvalidFormatException, IOException {
		setValue(getFormatter().readFrom(in));
	}
	
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	private FieldMessageElement sizeElement;

	public void resolveReferences(Map siblings) {
		if ((depends!=null) && (this instanceof FieldMessageElement) && (formatter!=null) && (formatter instanceof VariableFormatter)) {
			sizeElement=(FieldMessageElement)siblings.get(depends);
		}
	}

}
