package mx.com.interware.spira.message.util;

import mx.com.interware.spira.message.exceptions.InvalidFormatException;


public class OperNumberDecimalFormatterBuilder implements FormatterBuilder {

	/**
	 *
	 * Contruye e inicializa una instacia del objeto NineSimpleFormatter
	 */

	public Formatter create(String fmt) throws InvalidFormatException {
		Formatter formatter = new OperNumberDecimalFormatter();
		formatter.init(fmt);
		return formatter;
	}

}
