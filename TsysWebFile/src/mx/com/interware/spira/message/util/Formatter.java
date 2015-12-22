package mx.com.interware.spira.message.util;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import mx.com.interware.spira.message.exceptions.InvalidFormatException;

public interface Formatter {
	
	/**
	 * 
	 * @param fmt
	 * @throws InvalidFormatException
	 */
	void init(String fmt) throws InvalidFormatException;

	/**
	 * Este metodo obtiene el valor del objeto value como string de acuerdo al formato por ejemplo
	 * si le mando un Double 3.14 y el formato es 9(05).99 regresa "00314.00"
	 */	
	String asString(Object value);
	/**
	 * Este metodo obtiene el objeto adecuando para el formato apartir de un string
	 * por ejemplo si el formato es 9(04) y mando "123" me regresa un Integer 123
	 * si por otra parte el formato es X(09) y mando un "  FELIPE GERARD  " entonces debe regresar
	 * "FELIPE GE" si le mando "12" al formato 9(05) regresa "00012" si mado
	 * "FELIPE" a X(20) debe regresar "FELIPE              " ojo debe medir 20
	 */
	Object fromString(String str) throws InvalidFormatException;
	
	void writeTo(Object value,OutputStream out) throws IOException;
	
	Object readFrom(InputStream in) throws IOException ;
	
	//void postInit(Element element);

}
