package mx.com.interware.spira.message.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mx.com.interware.spira.message.exceptions.InvalidFormatException;

import org.apache.log4j.Logger;

public class OperNineFormatter implements Formatter {

	private static Logger log = Logger.getLogger(OperNineFormatter.class);
	protected int lenInteger;
	//protected byte[] buff;
	protected int len;
	protected MessageFormat format;

	static {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < NineFormatter.MAX_FORMATTER_LENGTH; i++) {
			sb.append('0');
		}
		NineFormatter.CEROS = sb.toString();
	}

	public void check(Object value) {
		String tem=value.toString();
		Pattern pattern = Pattern.compile("[\\+\\-]");
		Matcher	matcher = pattern.matcher(tem);
		if(matcher.find() == true) {
			if((tem.substring(1,tem.length()).length()-1) > lenInteger) {
				throw new RuntimeException(value + " es demasiado grande para este formato.");
			}			
		}		 						
	}
		
	/**
	 * Convierte un <code>Object</code> en un <code>String</code> de acuerdo al formato <code>9([0-9]+)</code>.
	 * 
	 * @param      El <code>Object</code> será convertido en un <code>String</code>
	 * @return     Un String que representa el <code>Object</code>.		  
	 * @exception  Lanza un <code>RuntimeException</code> si la cadena es demasiado grande que lo establecido en el formato.
	 */
	public String asString(Object value) { 
		if (value == null || value.toString().length()==0) 
			return XFormatter.BLANCOS.substring(0,len);
		check(value);
		Double[] temp = new Double[1];
		temp[0] = new Double(value.toString());
		String result = format.format(temp);
		if (temp[0].doubleValue() >= 0)
			result = "+" + result;
		if (result.length() > len)
			throw new RuntimeException(value + " es demasiado grande para este formato");
		return result;
	}

	/**
	 * Convierte un <code>String</code> en un <code>Object</code> en este caso el <code>Object</code> 
	 * regresado será un <code>Long</code> 
	 * 
	 * @param      El <code>String</code> será convertido en un <code>String</code>
	 * @return     Un Long que representa el <code>Object</code>.		  
	 * @exception  Ninguna.
	 */
	public Object fromString(String str) {
		check(str);
		if (str == null || str.trim().length() == 0 || str.length() > len)
			return null;
		Double value = new Double(str);
		if (value.equals("0")) return new Long("0");
		//log.info("ocuurs yun ¡");
		if (str.indexOf("+") == 0) return new Long(str.substring(1,str.length()));  
		return new Long(str);
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
			String temp = "+" + NineFormatter.CEROS.substring(0, lenInteger);
			out.write(temp.getBytes());
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
		log.debug("OperNineFormater leyo[" + new String(buff) + "] len:" + len + " n:" + n);
		if (val.length() == 0)
			return null;
		else if (val.indexOf(' ') != -1)  //En ocasiones de la entrada viene valores de la siguiente forma 000  0
			return new Long("0");

		return fromString(val);
	}

	/**
	 * Inicializa el Objeto OperNineFormatter y establece los parametos del formato actual de acuerdo al patron +9([0-9]+). 
	 * 
	 * @param      Un <code>String</code> que representa el formato a establecer.
	 * @return     Ninguno.		  
	 * @exception  Lanza <code>InvalidFormatException</code> en caso de que el formato a establecer no coincida con el patron.
	 */
	public void init(String fmt) throws InvalidFormatException {
		fmt = fmt.toUpperCase();
		Pattern pattern = Pattern.compile(PatternConstant.PATTERN_OPER_NINE);
		Matcher matcher = pattern.matcher(fmt);
		if (!matcher.matches()) {
			throw new InvalidFormatException("Para el OperNineFormatter el formato tiene que ser " + PatternConstant.PATTERN_OPER_NINE);
		}
		pattern = Pattern.compile(PatternConstant.PATTERN_NUMBER);
		matcher = pattern.matcher(fmt);
		matcher.find(2); // se pone uno para que no tome en cuenta el primer '+9' del formato			
		lenInteger = Integer.parseInt(matcher.group());
		len = lenInteger + 1;
		String formato = "{0,number," + NineFormatter.CEROS.substring(0, lenInteger) + "}";
		format = new MessageFormat(formato);
		//buff = new byte[len];

	}

	public static void main(String[] args) throws Exception {
		OperNineFormatter x = new OperNineFormatter();
		x.init("+9(05)");
		log.info(x.asString(new Double("78")));
		log.info(x.asString(new Double("+0")));
		log.info(x.asString(new Double("-123")));
		log.info(x.asString("-12345"));
		//	lanza excepcion
		//log.info(x.asString(new Double("-123456"))); 
		log.info(x.fromString("78"));
		log.info(x.fromString("-0"));
		log.info(x.fromString("+123"));
		log.info(x.fromString("-12345"));
		//  lanza excepcion
		//log.info(x.fromString("+1234567"));	
	}
	
}
