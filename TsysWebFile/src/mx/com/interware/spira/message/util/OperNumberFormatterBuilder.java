package mx.com.interware.spira.message.util;

import mx.com.interware.spira.message.exceptions.InvalidFormatException;


public class OperNumberFormatterBuilder implements FormatterBuilder {

	/**
	 *
	 * Contruye e inicializa una instacia del objeto OpenNumberFormatter
	 */

	public Formatter create(String fmt) throws InvalidFormatException {
		Formatter formatter = new OperNumberFormatter();
		formatter.init(fmt);
		return formatter;
	}

}
