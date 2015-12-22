package mx.com.interware.spira.message.util;

import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mx.com.interware.spira.message.exceptions.InvalidFormatException;

import org.apache.log4j.Logger;

public class NumberFormatter extends NineFormatter implements Formatter {

	private static Logger log = Logger.getLogger(NumberFormatter.class);

	/**
	 * Inicializa el Objeto NumberFormatter y establece los parametos del formato actual de acuerdo al patron 9([0-9]+). 
	 * 
	 * @param      Un <code>String</code> que representa el formato a establecer.
	 * @return     Ninguno.		  
	 * @exception  Lanza <code>InvalidFormatException</code> en caso de que el formato a establecer no coincida con el patron.
	 */
	public void init(String fmt) throws InvalidFormatException {
		fmt = fmt.toUpperCase();
		Pattern pattern = Pattern.compile(PatternConstant.PATTERN_NUMBER);
		Matcher matcher = pattern.matcher(fmt);
		if (!matcher.matches()) {
			throw new InvalidFormatException("Para el NineDecimalFormatter el formato tiene que ser " + PatternConstant.PATTERN_NUMBER);
		}
		len = fmt.length();
		String formato = "{0,number," + NineFormatter.CEROS.substring(0, len) + "}";
		format = new MessageFormat(formato);
		//buff = new byte[len];
	}

	public static void main(String[] args) throws Exception {
		NumberFormatter x = new NumberFormatter();
		x.init("999");
		
		log.info(x.asString(new Long("0")));
		log.info(x.asString(new Long("123")));
		//	lanza una excepcion
		//log.info(x.asString(new Long("1234567"))); 
		log.info(x.fromString("0"));
		log.info(x.fromString("123"));
		//	Regresa un null
		log.info(x.fromString("1234567"));   
	}

}
