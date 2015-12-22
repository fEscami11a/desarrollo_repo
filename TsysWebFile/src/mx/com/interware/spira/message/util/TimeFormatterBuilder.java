package mx.com.interware.spira.message.util;

import mx.com.interware.spira.message.exceptions.InvalidFormatException;


public class TimeFormatterBuilder implements FormatterBuilder {

	/**
	 * Contruye e inicializa una instacia del objeto XFormatter
	 */
	public Formatter create(String fmt) throws InvalidFormatException {
		Formatter formatter=new TimeFormatter();
		formatter.init("HHMMSS");
		return formatter;
	}

}
