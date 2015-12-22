package mx.com.interware.spira.message.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import mx.com.interware.spira.message.exceptions.InvalidFormatException;

public class CharFormatter extends XFormatter implements Formatter {
	private static Logger log = Logger.getLogger(CharFormatter.class);

	/**
	 * Convierte un <code>String</code> en un <code>Object</code> en este caso el <code>Object</code> 
	 * regresado será un <code>String</code> 
	 * 
	 * @param      El <code>String</code> será convertido en un <code>String</code>
	 * @return     Un String que representa el <code>Object</code>.		  
	 * @exception  <code>RuntimeException</code> si la longitud del <code>String</code> es mayor a la establecida en el formato.
	 */
	public Object fromString(String str) {
		if (str.length() != len) {
			throw new RuntimeException("Este formato indica que debe medir " + len + " caracteres y [" + str + "] mide:" + str.length());
		}
		return str;
	}

	/**
	 * Inicializa el Objeto CharFormatter y verifica que el formato se ajuste al patron X+. 
	 * 
	 * @param      Un <code>String</code> que representa el formato a establecer.
	 * @return     Ninguno.		  
	 * @exception  Lanza <code>InvalidFormatException</code> en caso de que el formato no coincida con el patron.
	 */
	public void init(String fmt) throws InvalidFormatException {
		fmt = fmt.toUpperCase();
		Pattern pattern = Pattern.compile(PatternConstant.PATTERN_VARCHAR);
		Matcher matcher = pattern.matcher(fmt);
		if (!matcher.matches()) {
			throw new InvalidFormatException("Para el CharFormatter el formato tiene que ser " + PatternConstant.PATTERN_VARCHAR);
		}
		len = fmt.length();
		//buff = new byte[len];
	}

	/**
	 * Utilicese para fines de prueba
	 */
	public static void main(String[] args) throws Exception {
		CharFormatter x = new CharFormatter();
		x.init("XX");
		log.info(x.asString("AA"));
		log.info(x.fromString("45"));
		log.info(x.fromString("  "));
	}

}
