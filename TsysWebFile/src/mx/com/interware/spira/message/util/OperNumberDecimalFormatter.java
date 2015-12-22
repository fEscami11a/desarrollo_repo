package mx.com.interware.spira.message.util;

import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import mx.com.interware.spira.message.exceptions.InvalidFormatException;

public class OperNumberDecimalFormatter extends OperNineDecimalFormatter implements Formatter {
	private static Logger log = Logger.getLogger(OperNumberDecimalFormatter.class);

	/**
	 * Inicializa el Objeto NineFormatter y establece los parametos del formato actual de acuerdo al patron +9([0-9]+.[0-9]+). 
	 * 
	 * @param      Un <code>String</code> que representa el formato a establecer.
	 * @return     Ninguno.		  
	 * @exception  Lanza <code>InvalidFormatException</code> en caso de que el formato a establecer no coincida con el patron.
	 */
	public void init(String fmt) throws InvalidFormatException {
		fmt = fmt.toUpperCase();
		Pattern pattern = Pattern.compile(PatternConstant.PATTERN_OPER_NUMBER_DECIMAL);
		Matcher matcher = pattern.matcher(fmt);
		if (!matcher.matches()) {
			throw new InvalidFormatException("Para el NineDecimalFormatter el formato tiene que ser " + PatternConstant.PATTERN_NINE_DECIMAL);
		}
		lenDecimal = fmt.length() - fmt.indexOf(".") - 1;
		lenInteger = fmt.length() - lenDecimal - 2;
		len = lenInteger + lenDecimal + 2; // Es la longitud tomando en  cuenta el '.' 
		if (len > NineFormatter.MAX_FORMATTER_LENGTH) {
			throw new InvalidFormatException("Este formato es mayor que el maximo permitido de :" + NineFormatter.MAX_FORMATTER_LENGTH);
		}
		String formato = "{0,number," + NineFormatter.CEROS.substring(0, lenInteger) + "." + NineFormatter.CEROS.substring(0, lenDecimal) + "}";
		format = new MessageFormat(formato);
		//buff = new byte[len];

	}

	public static void main(String[] args) throws Exception {

		OperNumberDecimalFormatter x = new OperNumberDecimalFormatter();
		x.init("+99999.99");
		
		//	+00078.00
		log.info(x.asString(new Double("78")));
		//	+00000.00
		log.info(x.asString(new Double("+0")));
		//  -00123.00
		log.info(x.asString(new Double("-123")));
		//	+12345.00
		log.info(x.asString(new Double("+12345")));
		//	lanza excepcion
		//log.info(x.asString(new Double("-123456"))); 
		log.info(x.fromString("+78"));
		log.info(x.fromString("+0"));
		log.info(x.fromString("+123"));
		log.info(x.fromString("-12345"));
		//  lanza excepcion
		//log.info(x.fromString("+1234567"));
		//	+00078.00
		log.info(x.asString(new Long("78")));
		//	+00078.00
		log.info(x.asString(new Double("78")));
		//	+00078.99
		log.info(x.asString(new Double("78.99")));
		//	+00000.88
		log.info(x.asString(new Double("0.88")));
		//	+00123.32
		log.info(x.asString(new Double("123.32")));
		//	+12345.40
		log.info(x.asString(new Double("12345.40")));
		//	lanza excepcion demasiados decimales
		//log.info(x.asString(new Double("12345.4444"))); 
		//	lanza excepcion demasiado grande para el formato
		//log.info(x.asString(new Double("1234567.40")));
		//  lanza excepcion demasidos decimales
		//log.info(x.asString(new Double("1234.044")));
		//	regresa double 
		log.info(x.fromString("78"));
		log.info(x.fromString("78.99"));
		log.info(x.fromString("0.88"));
		log.info(x.fromString("12332"));
		log.info(x.fromString("12345.40"));
		//	lanza excepcion demasiados decimales
		//log.info(x.fromString("12345.444")); 
		//	lanza excepcion demasiado grande opara el formato
		//log.info(x.fromString("1234567.40"));
		//  lanza excepcion demasiados decimales
		//log.info(x.fromString("1234.444")); 
		// salida
		log.info(x.asString("+78.48"));
		log.info(x.asString("-78.12"));
		// 	lanza una excepcion
		//log.info(x.asString("-78.000"));
		// 	lanza una excepcion
		//log.info(x.asString("+780000.00"));
		log.info(x.fromString("+78.48"));
		log.info(x.fromString("-78.12"));

	}

}
