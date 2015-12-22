package mx.com.interware.spira.message;

import java.util.HashMap;
import java.util.Map;

import mx.com.interware.spira.message.exceptions.InvalidFormatException;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;


public class MessageElementFactory {
	
	private static Logger log=Logger.getLogger(MessageElementFactory.class);
	
	private static Map classMap;
	
	private Map createdElements=new HashMap();
	
	static {
		classMap=new HashMap();
		classMap.put("array",mx.com.interware.spira.message.ArrayMessageElement.class);
		classMap.put("struct",mx.com.interware.spira.message.StructMessageElement.class);
		classMap.put("field",mx.com.interware.spira.message.FieldMessageElement.class);
	}
	
	public Map getCreatedElements() {
		return createdElements;
	}
	
	public MessageElement create(Element element) 
	    throws InstantiationException, IllegalAccessException, InvalidFormatException {
		Class elementClass=(Class)classMap.get(element.getNodeName());
		MessageElement msgElem=(MessageElement)elementClass.newInstance();
		msgElem.init(element);
		msgElem.resolveReferences(createdElements);
		createdElements.put(msgElem.getName(),msgElem);
		return msgElem;
	}
	
	public MessageElement get(String name) {
		return (MessageElement)createdElements.get(name);
	}

}
