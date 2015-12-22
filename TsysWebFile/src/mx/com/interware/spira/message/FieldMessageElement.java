package mx.com.interware.spira.message;

import java.util.HashMap;
import java.util.Map;

import mx.com.interware.spira.message.exceptions.InvalidFormatException;
import mx.com.interware.util.Throwable2String;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;


public class FieldMessageElement extends AbstractMessageElement {
	
	private static Logger log=Logger.getLogger(FieldMessageElement.class);	
	private Map optionMap;

	public static void main(String[] args) {
	}
	/**
	 *
	 */

	public void init(Element element) throws InvalidFormatException, InstantiationException, IllegalAccessException {
		super.init(element);
		NodeList optionList=element.getElementsByTagName("option");
		for (int i=0; i<optionList.getLength(); i++) {
			if (optionMap==null) {
				optionMap=new HashMap();
			}
			Element optionElem=(Element)optionList.item(i);
			optionMap.put(optionElem.getAttribute("value"),optionElem.getAttribute("name"));
		}
	}
	

	/**
	 *
	 */

	public void setValue(Object object) throws InvalidFormatException {
		if ((optionMap!=null) && (object.toString().trim().length()>0)) {
//			if (!optionMap.keySet().contains(object)) {
//				InvalidFormatException ex = new InvalidFormatException("El valor: "+object+" no esta en el set: "+optionMap.keySet()+" --> El valor se regresara como [null]");
//				log.warn(Throwable2String.throwable2String(ex));
//				object = null; // Solo en los invalidos
//			}
		}
		super.setValue(getFormatter().fromString(getFormatter().asString(object)));		
	}
	
	public Object getOptionValue() {
		Object object=getValue();
		if ((optionMap!=null) && (object !=null) &&(object.toString().length()>0)) {
			if (optionMap.keySet().contains(object)) {
				object=optionMap.get(object);	
			}
		}
		return object;
	}
	

	/**
	 *
	 */

	public Element asElement(Document doc) {
		String name=getName().trim();
		if (Character.isDigit(name.charAt(0))) {
			name="_"+name;
		}
		Element elem=doc.createElement(name);
		Text value=doc.createTextNode(String.valueOf(getValue()));
		elem.appendChild(value);
		return elem;
	}

}
