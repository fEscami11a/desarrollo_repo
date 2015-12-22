package mx.com.interware.spira.message.util;

import mx.com.interware.spira.message.exceptions.InvalidFormatException;


public class OperNineFormatterBuilder implements FormatterBuilder {

	/**
	 *
	 * Contruye e inicializa una instacia del objeto OperNineFormatter
	 */

	public Formatter create(String fmt) throws InvalidFormatException {
		Formatter formatter = new OperNineFormatter();
		formatter.init(fmt);
		return formatter;
	}

}
