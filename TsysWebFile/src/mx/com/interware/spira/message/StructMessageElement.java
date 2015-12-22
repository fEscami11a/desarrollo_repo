package mx.com.interware.spira.message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import mx.com.interware.spira.message.exceptions.ElementNotFoundException;
import mx.com.interware.spira.message.exceptions.InvalidFormatException;
import mx.com.interware.xml.XMLConfig;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class StructMessageElement extends AbstractMessageElement implements CompoundMessageElement  {
	
	private static Logger log=Logger.getLogger(StructMessageElement.class);
	
	protected Map elements=new LinkedHashMap();
	
	private MessageElementFactory factory=new MessageElementFactory();
	private Element confElement;
	private String confTag;
	
	private String packetID;
	
	public String getPacketID() {
		return packetID;
	}

	public void setPacketID(String packetID) {
		this.packetID = packetID;
	}

	
	void init(Element element,String tag) throws InvalidFormatException, InstantiationException, IllegalAccessException {
		//log.info("VMBR VMBR ELEMENT: "+element);
		super.init(element);
		confElement=element;
		confTag=tag;
		XMLConfig config=new XMLConfig();
		config.initWithRoot(element);
		for (Iterator elemIter=config.getChildElementIterator(tag); elemIter.hasNext(); ) {
			Element elem=(Element)elemIter.next();
			MessageElement msgElem=factory.create(elem);
			elements.put(msgElem.getName(),msgElem);
		}
	}


	public void readFrom(InputStream in) throws InvalidFormatException, IOException {
		for (Iterator elemIter=elements.keySet().iterator(); elemIter.hasNext();) {
			String name=(String)elemIter.next();
			MessageElement element=(MessageElement)elements.get(name);
			element.readFrom(in);
		}
	}

	public void writeTo(OutputStream out) throws IOException {
		for (Iterator elemIter=elements.keySet().iterator(); elemIter.hasNext();) {
			String name=(String)elemIter.next();
			MessageElement element=(MessageElement)elements.get(name);
			element.writeTo(out);
		}
	}


	public Collection getElementNames() {
		return elements.keySet();
	}


	public MessageElement getElement(String name) throws ElementNotFoundException {
		MessageElement element=(MessageElement)elements.get(name);
		if (element==null) {
			Set keys=elements.keySet();
			StringBuffer buf=new StringBuffer("\n***");
			buf.append(keys);
			
			for (Iterator iter = keys.iterator(); iter.hasNext();) {
				String key=(String)iter.next();
				MessageElement elem = (MessageElement)elements.get(key);
				buf.append("\n***");
				buf.append(elem.getValue());
			}
			log.info("throwing exception (2)");
			throw new ElementNotFoundException(name+" en el tag de configuracion:"+confTag+buf.toString());
		}
		return element;
	}

	public void set(String name, Object value) throws ElementNotFoundException, InvalidFormatException {
		MessageElement element=(MessageElement)elements.get(name);
		if (element==null) {
			throw new ElementNotFoundException(name);
		}
		element.setValue(value);
	}

	/**
	 *
	 */

	public void init(Element element) throws InvalidFormatException, InstantiationException, IllegalAccessException {
		init(element,"struct");
	}

	/**
	 *
	 */

	public Object clone() throws CloneNotSupportedException {
		StructMessageElement clone=new StructMessageElement();
		try {
			clone.init(confElement,confTag);
		}
		catch (InvalidFormatException e) {
			log.error(e);
		}
		catch (InstantiationException e) {
			log.error(e);
		}
		catch (IllegalAccessException e) {
			log.error(e);
		}
		return clone;
	}
	
	public Object getValue() {
		throw new RuntimeException("Meto getValue() no existe para este tipo de objetos");
	}


	public void setValue(Object value) throws InvalidFormatException {
		throw new RuntimeException("Meto setValue(Object value) no existe para este tipo de objetos");
	}

	public Element asElement(Document factory) {
		Element structElem=factory.createElement(getName());
		for (Iterator childIter=elements.keySet().iterator(); childIter.hasNext();) {
			MessageElement elem=(MessageElement)elements.get(childIter.next());
			Element childElem=elem.asElement(factory);
			structElem.appendChild(childElem);
		}
		return structElem;
	}

}
