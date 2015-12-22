package mx.com.interware.spira.message.util;

import mx.com.interware.spira.message.exceptions.InvalidFormatException;


public class OperNineDecimalFormatterBuilder implements FormatterBuilder {

	/**
	 *
	 * Contruye e inicializa una instacia del objeto OperNineDecimalFormatter
	 */

	public Formatter create(String fmt) throws InvalidFormatException {
		Formatter formatter = new OperNineDecimalFormatter();
		formatter.init(fmt);
		return formatter;
	}

}
