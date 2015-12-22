package mx.com.interware.spira.message.util;

import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mx.com.interware.spira.message.exceptions.InvalidFormatException;

import org.apache.log4j.Logger;

public class OperNumberFormatter extends OperNineFormatter implements Formatter {

	private static Logger log = Logger.getLogger(OperNumberFormatter.class);

	static {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < NineFormatter.MAX_FORMATTER_LENGTH; i++) {
			sb.append('0');
		}
		NineFormatter.CEROS = sb.toString();
	}

	/**
	 * Inicializa el Objeto NineFormatter y establece los parametos del formato actual de acuerdo al patron +9([0-9]+). 
	 * 
	 * @param      Un <code>String</code> que representa el formato a establecer.
	 * @return     Ninguno.		  
	 * @exception  Lanza <code>InvalidFormatException</code> en caso de que el formato a establecer no coincida con el patron.
	 */

	public void init(String fmt) throws InvalidFormatException {
		fmt = fmt.toUpperCase();
		Pattern pattern = Pattern.compile(PatternConstant.PATTERN_OPER_NUMBER);
		Matcher matcher = pattern.matcher(fmt);
		if (!matcher.matches()) {
			throw new InvalidFormatException("Para el NineFormatter el formato tiene que ser " + PatternConstant.PATTERN_NINE);
		}
		len = fmt.length();
		lenInteger = len-1; 
		String formato = "{0,number," + NineFormatter.CEROS.substring(0, len - 1) + "}";
		format = new MessageFormat(formato);
		//buff = new byte[len];
	}

	public static void main(String[] args) throws Exception {

		OperNumberFormatter x = new OperNumberFormatter();
		x.init("+999");
		
		log.info(x.asString(new Long("78")));
		log.info(x.asString(new Double("+0")));
		log.info(x.asString(new Long("-123")));
		//	lanza excepcion
		//log.info(x.asString(new Double("-123456"))); 
		log.info(x.fromString("78"));
		log.info(x.fromString("-0"));
		log.info(x.fromString("+123"));
		//  lanza excepcion
		//log.info(x.fromString("+1234567"));	
	}

}
