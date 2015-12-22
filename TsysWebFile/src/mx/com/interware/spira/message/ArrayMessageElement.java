package mx.com.interware.spira.message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import mx.com.interware.spira.message.exceptions.InvalidFormatException;
import mx.com.interware.xml.XMLConfig;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class ArrayMessageElement extends AbstractMessageElement {
	private static Logger log = Logger.getLogger(ArrayMessageElement.class);
	
	private int min,max;
	private String sizeElementName;
	private FieldMessageElement sizeElement;
	private StructMessageElement template;
	
	private StructMessageElement[] array; 
	
	private MessageElementFactory factory=new MessageElementFactory();
	
	public void init(Element element) throws InvalidFormatException, InstantiationException, IllegalAccessException {
		super.init(element);
		XMLConfig config=new XMLConfig();
		config.initWithRoot(element);
		template=new StructMessageElement();
		template.init(element,"array");
		min = Integer.parseInt(element.getAttribute("min"));
		max = Integer.parseInt(element.getAttribute("max"));
		sizeElementName=element.getAttribute("depends");
	}
	
	public void setSizeElement(FieldMessageElement sizeElement) {
		this.sizeElement=sizeElement;
	}
	
	public static void main(String[] args) {
	}
	
	public int getMax() {
		return max;
	}

	public int getMin() {
		return min;
	}

	public void fixSize() {
		array=new StructMessageElement[(int)((Long)sizeElement.getValue()).longValue()];
		for (int i = 0; i < array.length; i++) {
			try {
				array[i]=(StructMessageElement)template.clone();
			}
			catch (CloneNotSupportedException e) {
				//Esto no pasa
				log.error(e);
			}
		}
	}
	
	public int getSize() {
		return array.length;
	}

	public FieldMessageElement getSizeElement() {
		return sizeElement;
	}
	
	public StructMessageElement get(int index) {
		if (index>=((Long)sizeElement.getValue()).longValue()) {
			throw new ArrayIndexOutOfBoundsException(""+index+" no esta entre "+min+" y "+sizeElement.getValue());
		}
		return (StructMessageElement)array[index];
	}

	/**
	 *
	 */

	public void resolveReferences(Map siblings) {
		setSizeElement((FieldMessageElement)siblings.get(sizeElementName));
	}

	/**
	 *
	 */

	public void readFrom(InputStream in) throws InvalidFormatException, IOException {
		fixSize();
		for (int i = 0; i < array.length; i++) {
			array[i].readFrom(in);	
		}
	}

	/**
	 *
	 */

	public void writeTo(OutputStream out) throws IOException {
		for (int i = 0; i < array.length; i++) {
			array[i].writeTo(out);	
		}
		
	}

	public Element asElement(Document factory) {
		Element arrayElem=factory.createElement(getName());
		for (int i=0; i<array.length; i++) {
			MessageElement elem=(MessageElement)array[i];
			Element childElem=elem.asElement(factory);
			arrayElem.appendChild(childElem);
		}
		return arrayElem;
		
	}

}
