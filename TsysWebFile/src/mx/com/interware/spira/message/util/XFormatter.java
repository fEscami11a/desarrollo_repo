package mx.com.interware.spira.message.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mx.com.interware.spira.message.exceptions.InvalidFormatException;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;

public class XFormatter implements Formatter {

	private static Logger log = Logger.getLogger(XFormatter.class);
	public static final int MAX_XFORMATTER_LENGTH = 1024;
	public static String BLANCOS;
	protected int len = 0;
	//protected byte[] buff;

	static {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < MAX_XFORMATTER_LENGTH; i++) {
			sb.append(' ');
		}
		BLANCOS = sb.toString();
	}

	/**
	 * Convierte un <code>Object</code> en un <code>String</code> de acuerdo al formato <code>X([0-9]+)</code>.
	 * 
	 * @param      El <code>Object</code> será convertido en un <code>String</code>
   * @return     Un <code>String</code> que representa el <code>Object</code>.		  
   * @exception  Ninguna.
	 */
	public String asString(Object value) {
		if (value == null)
			value = "";
		String temValue = value.toString();
		if (len>1) temValue=temValue.trim(); // los que son de longitud 1 no se trimean!!
		int temLen = temValue.length();
		if (temLen > len) {
			return temValue.substring(0, len);
		}
		if (temLen < len) {
			temValue = temValue + BLANCOS.substring(0, len - temLen);
		}
		return temValue;
	}

	/**
	 * Convierte un <code>String</code> en un <code>Object</code> en este caso el <code>Object</code> 
	 * regresado será un <code>String</code> 
	 * 
	 * @param      El <code>String</code> será convertido en un <code>String</code>
	 * @return     Un <code>Object</code> representado por el <code>String</code>.		  
	 * @exception  Ninguna.
	 */
	public Object fromString(String str) {
		if (len>1) return str.trim(); // los que son de longitud 1 no se trimean!!
		return str;
	}

	/**
	 * Coloca un <code>Object</code> en un <code>OutputStream</code>, donde el <code>Object</code> se ajustara al formato establecido. 
	 * 
	 * @param      El <code>Object</code> será colocado en el <code>OutputStream</code>.
	 * @return     Ninguno.		  
	 * @exception  Lanza <code>IOException.</code>
	 */
	public void writeTo(Object value, OutputStream out) throws IOException {
		log.debug("VMBR HASTA LOS BYTES FORMATTER: "+new String(asString(value).getBytes()));
		out.write(asString(value).getBytes());
	}

	/**
	 * Recupera un <code>Object</code> en un <code>InputStream</code>. 
	 * 
	 * @param      Un <code>InputStream</code> del cual se obtiene un objecto.
	 * @return     Un <code>Object</code> obtenido del <code>InputStream</code>.		  
	 * @exception  Lanza <code>IOException</code>.
	 */
	public Object readFrom(InputStream in) throws IOException {
		byte[] buff=new byte[len];
		int n = in.read(buff);
		if (n != len) {
			throw new IOException("No existe informacion suficiente en el stream para obtener " + len + " bytes solo se obtubieron " + n);
		}
		log.debug("XFormater leyo[" + new String(buff) + "] len:" + len + " n:" + n+" string.length:"+(new String(buff)).length());
		return String.valueOf(new String(buff));
	}

	/**
	 * Inicializa el Objeto XFormatter y establece los parametos del formato actual de acuerdo al patron X([0-9]+). 
	 * 
	 * @param      Un <code>String</code> que representa el formato a establecer.
	 * @return     Ninguno.		  
	 * @exception  Lanza <code>InvalidFormatException</code> en caso de que el formato a establecer no coincida con el patron.
	 */
	public void init(String fmt) throws InvalidFormatException {
		fmt = fmt.toUpperCase();
		Pattern pattern = Pattern.compile(PatternConstant.PATTERN_X);
		Matcher matcher = pattern.matcher(fmt);
		if (!matcher.matches()) {
			throw new InvalidFormatException("Para el XFormatter el formato tiene que ser " + PatternConstant.PATTERN_X);
		}
		pattern = Pattern.compile(PatternConstant.PATTERN_NUMBER);
		matcher = pattern.matcher(fmt);
		matcher.find();
		len = Integer.parseInt(matcher.group());
		if (len > MAX_XFORMATTER_LENGTH) {
			throw new InvalidFormatException("Este formato es mayor que el maximo permitido de :" + MAX_XFORMATTER_LENGTH);
		}
		//buff = new byte[len];
	}

	public static void main(String[] args) throws Exception {
		XFormatter x = new XFormatter();
		x.init("X(015)");
		log.info("[" + x.asString("Felipe de Jesus Gerard Contreras") + "]");
		log.info("[" + x.asString("Felipe") + "]");
		log.info("[" + x.asString(new Long("45")) + "]");
		log.info("[" + x.fromString("45") + "]");
	}


	public void postInit(Element element) {
		// no hace nada por default solo el VariableFormater usa este metodo
	}

}
