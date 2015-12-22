package mx.com.interware.spira.message.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mx.com.interware.spira.message.exceptions.InvalidFormatException;

import org.apache.log4j.Logger;

public class TimeFormatter extends XFormatter implements Formatter {

	private static Logger log = Logger.getLogger(TimeFormatter.class);

	private SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");

	/**
	 * Convierte el objeto Date a String
	 */
	public String asString(Object value) {
		if (value == null)
			return "      ";
		if (!(value instanceof Date)) {
			throw new RuntimeException("para el TimeFormater se requiere un Date en el metodo asString");
		}
		return sdf.format(value);
	}

	/**
	 * Preguntar que pasa si se le pasa un numero mas grande que el que tiene establecido el formato
	 */
	public Object fromString(String str) {
		Date date;
		try {
			date = sdf.parse(str);
		}
		catch (ParseException e) {
			throw new RuntimeException(str + " no representa una hora valida en el formato HHMMSS");
		}
		return date;
	}

	public Object readFrom(InputStream in) throws IOException {
		String result="";
		try {
			result=(String) (super.readFrom(in));
			return sdf.parse(result);
		}
		catch (ParseException e) {
			throw new RuntimeException(result + " no es una hora valida");
		}
	}

	/**
	 * Este método acepta la siguiente expresion regular X([0-9]*)
	 * Ejemplo: X(01), X(00), X(2222).
	 */

	public void init(String fmt) throws InvalidFormatException {
		fmt = fmt.toUpperCase();
		Pattern pattern = Pattern.compile(PatternConstant.PATTERN_TIME);
		Matcher matcher = pattern.matcher(fmt);
		log.debug(fmt + "--" + PatternConstant.PATTERN_TIME);
		if (!matcher.matches()) {
			throw new InvalidFormatException("Para el DateFormatter el formato tiene que ser " + PatternConstant.PATTERN_TIME);
		}
		len = 6;
		//buff = new byte[len];
	}

	public static void main(String[] args) throws Exception {
		TimeFormatter x = new TimeFormatter();
		x.init("HHMMSS");
		log.info(x.asString(new Date()));
		log.info(x.fromString("103000"));
	}

}
