package mx.com.interware.spira.message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import mx.com.interware.spira.message.exceptions.InvalidFormatException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


public interface MessageElement extends Cloneable {
	
	String getName();
	
	void setName(String name);
		
	void init(Element element)  throws InvalidFormatException, InstantiationException, IllegalAccessException;
	
	void resolveReferences(Map siblings);
	
	void writeTo(OutputStream out) throws IOException;

	void readFrom(InputStream in) throws IOException,InvalidFormatException;

	Object getValue();
	
	void setValue(Object value) throws InvalidFormatException;
	
	Element asElement(Document factory);

}
