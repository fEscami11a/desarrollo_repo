package mx.com.interware.spira.message;

import java.util.Collection;

import mx.com.interware.spira.message.exceptions.ElementNotFoundException;
import mx.com.interware.spira.message.exceptions.InvalidFormatException;


public interface CompoundMessageElement extends MessageElement {
	Collection getElementNames();	
	MessageElement getElement(String name) throws ElementNotFoundException ;
	void set(String name, Object value) throws ElementNotFoundException,InvalidFormatException;
	//CompoundMessageElement	
}
