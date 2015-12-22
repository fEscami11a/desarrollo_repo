package mx.com.interware.spira.message.util;

import mx.com.interware.spira.message.exceptions.InvalidFormatException;


public class NumberDecimalFormatterBuilder implements FormatterBuilder {

	/**
	 *
	 * Contruye e inicializa una instacia del objeto NumberDecimalFormatter
	 */

	public Formatter create(String fmt) throws InvalidFormatException {
		Formatter formatter = new NumberDecimalFormatter();
		formatter.init(fmt);
		return formatter;
	}

}
