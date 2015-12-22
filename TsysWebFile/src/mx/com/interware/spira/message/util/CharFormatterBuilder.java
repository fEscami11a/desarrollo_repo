package mx.com.interware.spira.message.util;

import mx.com.interware.spira.message.exceptions.InvalidFormatException;


public class CharFormatterBuilder implements FormatterBuilder {

	/**
	 * 
	 * Contruye e inicializa una instacia del objeto CharFormatter
	 */

	public Formatter create(String fmt) throws InvalidFormatException {
		Formatter formatter = new CharFormatter();
		formatter.init(fmt);
		return formatter;
	}

}