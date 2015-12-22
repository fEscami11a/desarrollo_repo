package mx.com.interware.spira.message.util;

import mx.com.interware.spira.message.exceptions.InvalidFormatException;


public class XFormatterBuilder implements FormatterBuilder {

	/**
	 * Contruye e inicializa una instacia del objeto XFormatter
	 */
	public Formatter create(String fmt) throws InvalidFormatException {
		Formatter formatter=new XFormatter();
		formatter.init(fmt);
		return formatter;
	}

}
