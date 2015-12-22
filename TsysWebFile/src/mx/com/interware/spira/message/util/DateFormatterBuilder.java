package mx.com.interware.spira.message.util;

import mx.com.interware.spira.message.exceptions.InvalidFormatException;


public class DateFormatterBuilder implements FormatterBuilder {

	/**
	 * Contruye e inicializa una instacia del objeto DateFormatter
	 */
	public Formatter create(String fmt) throws InvalidFormatException {
		Formatter formatter=new DateFormatter();
		formatter.init(PatternConstant.PATTERN_DATE);
		return formatter;
	}

}
