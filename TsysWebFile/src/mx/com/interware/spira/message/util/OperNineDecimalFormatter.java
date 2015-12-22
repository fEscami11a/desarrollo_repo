package mx.com.interware.spira.message.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import mx.com.interware.spira.message.exceptions.InvalidFormatException;

public class OperNineDecimalFormatter extends OperNineFormatter implements Formatter {

	private static Logger log = Logger.getLogger(OperNineDecimalFormatter.class);

	protected int lenDecimal;
	protected String strDecimal;
	protected String fmt;

	static {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < NineFormatter.MAX_FORMATTER_LENGTH; i++) {
			sb.append('0');
		}
		NineFormatter.CEROS = sb.toString();
	}

	public void check(Object value) {
		String tem = value.toString();
		int index = 0;
		Pattern pattern = Pattern.compile("[\\+\\-]");
		Matcher matcher = pattern.matcher(tem);
		if (matcher.find() == true) {
			index = 1;
		}
		
		if (tem.indexOf("E")!=-1)
		{
			NumberFormat decimalFormat = new DecimalFormat("0.00");
			tem = decimalFormat.format(Double.parseDouble(tem));
		}
		
		if (tem.indexOf(".") != -1) {
//			log.info("VMBR size1: "+(tem.substring(tem.indexOf("."), tem.length()).length() - 1));
//			log.info("VMBR size2: "+lenDecimal);
//			log.info("VMBR value: "+tem.toString());
			if ((tem.substring(tem.indexOf("."), tem.length()).length() - 1) > lenDecimal) {
				throw new RuntimeException(value + " tiene mas decimales que los establecidos en el formato.");
			}
			if ((tem.substring(0,tem.indexOf(".")).length() - 1) > lenInteger) {
				throw new RuntimeException(value + " es demasiado grande para este formato 1.");
			}
		} else if((tem.substring(index,tem.length()).length()-1) > lenInteger){
				throw new RuntimeException(value + " es demasiado grande para este formato 2.");
		}
	}

	/**
	 * Convierte un <code>String</code> en un <code>Object</code> en este caso el <code>Object</code> 
	 * regresado será un <code>Double</code> 
	 * 
	 * @param      El <code>String</code> será convertido en un <code>String</code>
	 * @return     Un Long que representa el <code>Object</code>.		  
	 * @exception  Ninguna.
	 */
	public Object fromString(String str) {
		//check(str);
		if (str == null || str.trim().length() == 0)
			return null;
		return new Double(str);
	}

	/**
	 * Coloca un <code>Object</code> en un <code>OutputStream</code>, donde el <code>Object</code> se ajustara al formato establecido. 
	 * 
	 * @param      El <code>Object</code> será colocado en el <code>OutputStream</code>.
	 * @return     Ninguno.		  
	 * @exception  Lanza <code>IOException.</code>
	 */
	public void writeTo(Object value, OutputStream out) throws IOException {
		if (value == null) {
			String tem="+"+NineFormatter.CEROS.substring(0, lenInteger)+"."+NineFormatter.CEROS.substring(0, lenDecimal);
			out.write(tem.getBytes());
		}
		else {
			out.write(asString(value).getBytes());
		}

	}

	/**
	 * Recupera un <code>Object</code> en un <code>InputStream</code>. 
	 * 
	 * @param      Un <code>InputStream</code> del cual se obtiene un objecto.
	 * @return     Un <code>Object</code> obtenido del <code>InputStream</code>.		  
	 * @exception  Lanza <code>IOException</code>.
	 */
	public Object readFrom(InputStream in) throws IOException {
		byte[] buff = new byte[len];
		int n = in.read(buff);
		if (n != len) {
			throw new IOException("No existe informacion sufuciente en el stream para obtener " + len + " bytes solo se obtubieron " + n);
		}
		String val = (new String(buff)).trim();
		log.debug("OperNineDecimalFormater leyo[" + new String(buff) + "] len:" + len + " n:" + n);
		if (val.length() == 0)
			return null;
		else if (val.indexOf(' ') != -1)
			return new Double("0");

		return fromString(val);

	}

	/**
	 * Inicializa el Objeto OperNineDecimalFormatter y establece los parametos del formato actual de acuerdo al patron +9([0-9]+.[0-9]+). 
	 * 
	 * @param      Un <code>String</code> que representa el formato a establecer.
	 * @return     Ninguno.		  
	 * @exception  Lanza <code>InvalidFormatException</code> en caso de que el formato a establecer no coincida con el patron.
	 */
	public void init(String fmt) throws InvalidFormatException {
		fmt = fmt.toUpperCase();
		this.fmt = fmt;
		Pattern pattern = Pattern.compile(PatternConstant.PATTERN_OPER_NINE_DECIMAL);
		Matcher matcher = pattern.matcher(fmt);
		if (!matcher.matches()) {
			throw new InvalidFormatException("Para el OperNineDecimalFormatter el formato tiene que ser " + PatternConstant.PATTERN_OPER_NINE_DECIMAL);
		}
		pattern = Pattern.compile(PatternConstant.PATTERN_NUMBER);
		matcher = pattern.matcher(fmt);
		matcher.find(2);
		lenInteger = Integer.parseInt(matcher.group());
		lenDecimal = fmt.length() - fmt.indexOf(".") - 1;
		len = lenInteger + lenDecimal + 2; // Es la longitud tomando en  cuenta el '+' y el "." 
		if (len > NineFormatter.MAX_FORMATTER_LENGTH) {
			throw new InvalidFormatException("Este formato es mayor que el maximo permitido de :" + NineFormatter.MAX_FORMATTER_LENGTH);
		}
		String formato = "{0,number," + NineFormatter.CEROS.substring(0, lenInteger) + "." + NineFormatter.CEROS.substring(0, lenDecimal) + "}";
		//Falta modificar el formato
		format = new MessageFormat(formato);
		//buff = new byte[len];
	}

	public static void main(String[] args) throws Exception {

		OperNineDecimalFormatter x = new OperNineDecimalFormatter();
		x.init("-9(05).99");
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
