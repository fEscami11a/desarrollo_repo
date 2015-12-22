package mx.com.interware.spira.message.util;

import mx.com.interware.spira.message.exceptions.InvalidFormatException;


public class NineFormatterBuilder implements FormatterBuilder {

	/**
	 *
	 * Contruye e inicializa una instacia del objeto NineFormatter
	 */

	public Formatter create(String fmt) throws InvalidFormatException {
		Formatter formatter = new NineFormatter();
		formatter.init(fmt);
		return formatter;
	}

}
