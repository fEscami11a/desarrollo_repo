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

public class DateFormatter extends XFormatter implements Formatter {
	private static Logger log = Logger.getLogger(DateFormatter.class);

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	public String asString(Object value) {
		if (value == null)
			return "        ";
		if (!(value instanceof Date)) {
			throw new RuntimeException("para el DateFormater se requiere un Date en el metodo asString");
		}
		return sdf.format(value);
	}

	public Object fromString(String str) {
		Date date;
		try {
			date = sdf.parse(str);
		}
		catch (ParseException e) {
			throw new RuntimeException(str + " no representa una fecha valida en el formato YYYYMMDD");
		}
		return date;
	}

	public Object readFrom(InputStream in) throws IOException {
		String result = "";
		try {
			result = (String) (super.readFrom(in));
			return sdf.parse(result);
		}
		catch (ParseException e) {
			throw new RuntimeException(result + " no es una fecha valida");
		}
	}

	public void init(String fmt) throws InvalidFormatException {
		fmt = fmt.toUpperCase();
		Pattern pattern = Pattern.compile(PatternConstant.PATTERN_DATE);
		Matcher matcher = pattern.matcher(fmt);
		if (!matcher.matches()) {
			throw new InvalidFormatException("Para el DateFormatter el formato tiene que ser " + PatternConstant.PATTERN_DATE);
		}
		len = 8;
		//buff = new byte[len];
	}

	public static void main(String[] args) throws Exception {
		DateFormatter x = new DateFormatter();
		x.init("YYYYMMDD");
		log.info(x.asString(new Date()));
		log.info(x.fromString("19801107"));
	}

}
