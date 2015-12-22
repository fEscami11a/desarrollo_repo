package mx.com.interware.spira.message.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mx.com.interware.spira.message.exceptions.InvalidFormatException;

import org.apache.log4j.Logger;

public class NineDecimalFormatter implements Formatter {

	private static Logger log = Logger.getLogger(NineDecimalFormatter.class);
	protected int len;
	protected int lenInteger;
	protected int lenDecimal;
	//protected byte[] buff;
	protected MessageFormat format;

	static {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < NineFormatter.MAX_FORMATTER_LENGTH; i++) {
			sb.append('0');
		}
		NineFormatter.CEROS = sb.toString();
	}
	
	/**
	 * Verifica que el <code>Object</code> recibido cumpla con los tamaños de los decimales y los enteros establecidos en el formato. 
	 * 
	 * @param value es el <code>Object</code> que representa la el numero recibido.
	 * @return <code>String</code> que representa el numero verificado.
	 */
	public String check(Object value) {
		String tem=value.toString();
		if(tem.indexOf(".") != -1) {
			if((tem.substring(0,tem.indexOf(".")).length()-1) > lenInteger) {
				throw new RuntimeException(value + " es demasiado grande para este formato.");
			}
			if((tem.substring(tem.indexOf("."),tem.length()).length()-1) > lenDecimal) {
				throw new RuntimeException(value + " tiene mas decimales que los establecidos en el formato.");
			} 						
		}
		return tem;
	}

	/**
	 * Convierte un <code>Object</code> en un <code>String</code> de acuerdo al formato <code>9([0-9]+.[0-9]+)</code>.
	 * 
	 * @param      El <code>Object</code> será convertido en un <code>String</code>
	 * @return     Un String que representa el <code>Object</code>.		  
	 * @exception  Lanza un <code>RuntimeException</code> si la cadena es demasiado grande que lo establecido en el formato
	 */

	public String asString(Object value) {
		if (value == null || value.toString().length()==0) {
			return XFormatter.BLANCOS.substring(0,len);
		}
		Double[] temp = new Double[1];
		temp[0] = new Double(check(value));
		String result = format.format(temp);
		if (result.length() > len)
				throw new RuntimeException(value + " es demasiado grande para este formato");
		return result;
	}

	/**
	 * Convierte un <code>String</code> en un <code>Object</code> en este caso el <code>Object</code> 
	 * regresado será un <code>Long</code> 
	 * 
	 * @param      El <code>String</code> será convertido en un <code>String</code>
	 * @return     Un Double que representa el <code>Object</code>.		  
	 * @exception  Ninguna.
	 */
	public Object fromString(String str) {
		if (str == null || str.trim().length() == 0)
			return null;
		return new Double(check(str));
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
			String tem=NineFormatter.CEROS.substring(0, lenInteger)+"."+NineFormatter.CEROS.substring(0, lenDecimal);
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
		byte[]buff = new byte[len];
		int n = in.read(buff);
		if (n != len) {
			throw new IOException("No existe informacion sufuciente en el stream para obtener " + len + " bytes solo se obtubieron " + n);
		}
		String val = (new String(buff)).trim();
		log.debug("NineDecimalFormater leyo[" + new String(buff) + "] len:" + len + " n:" + n);
		if (val.length() == 0)
			return null;
		else if (val.indexOf(' ') != -1)
			return new Double("0");

		return fromString(val);
	}
		
	/**
	 * Inicializa el Objeto NineDecimalFormatter y establece los parametos del formato actual de acuerdo al patron 9([0-9]+.[0-9]+). 
	 * 
	 * @param      Un <code>String</code> que representa el formato a establecer.
	 * @return     Ninguno.		  
	 * @exception  Lanza <code>InvalidFormatException</code> en caso de que el formato a establecer no coincida con el patron.
	 */

	public void init(String fmt) throws InvalidFormatException {
		fmt = fmt.toUpperCase();
		Pattern pattern = Pattern.compile(PatternConstant.PATTERN_NINE_DECIMAL);
		Matcher matcher = pattern.matcher(fmt);
		if (!matcher.matches()) {
			throw new InvalidFormatException("Para el NineDecimalFormatter el formato tiene que ser " + PatternConstant.PATTERN_NINE_DECIMAL);
		}
		pattern = Pattern.compile(PatternConstant.PATTERN_NUMBER);
		matcher = pattern.matcher(fmt);
		matcher.find(1);
		lenInteger = Integer.parseInt(matcher.group());
		lenDecimal = fmt.length() - fmt.indexOf(".") - 1;
		len = lenInteger + lenDecimal + 1; // Es la longitud tomando en  cuenta el '.' 
		if (len > NineFormatter.MAX_FORMATTER_LENGTH) {
			throw new InvalidFormatException("Este formato es mayor que el maximo permitido de :" + NineFormatter.MAX_FORMATTER_LENGTH);
		}
		String formato = "{0,number," + NineFormatter.CEROS.substring(0, lenInteger) + "." + NineFormatter.CEROS.substring(0, lenDecimal) + "}";
		format = new MessageFormat(formato);
		//buff = new byte[len];
	}

	public static void main(String[] args) throws Exception {

		NineDecimalFormatter x = new NineDecimalFormatter();
		x.init("9(07).99");
		
		
		//	le agrega .00
		log.info(x.asString(new Long("78")));
		log.info(x.asString(new Double("78"))); 
		log.info(x.asString(new Double("78.99")));
		log.info(x.asString(new Double("0.88")));
		log.info(x.asString(new Double("123.32")));
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
	}

}
