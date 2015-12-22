package mx.com.interware.spira.message.util;

import mx.com.interware.spira.message.exceptions.InvalidFormatException;


public interface FormatterBuilder {
	
	Formatter create(String fmt) throws InvalidFormatException;

}
