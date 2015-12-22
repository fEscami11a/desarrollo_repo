package mx.com.interware.spira.message.util;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mx.com.interware.spira.message.exceptions.InvalidFormatException;

import org.apache.log4j.Logger;

public class VariableFormatter  extends XFormatter implements Formatter {
	
	private static Logger log = Logger.getLogger(VariableFormatter.class);
	
	public void setLength(Object val) {
		int xlen=0;
		if (val instanceof Long) {
			xlen=(int)((Long)val).longValue();
		}
		else if (val instanceof String) {
			xlen=Integer.parseInt((String)val);
		}
		len=xlen;
		//buff=new byte[len];
	}

	public String asString(Object value) {
		if (value == null) {
			value = "";
		}
		String temValue = value.toString().trim();
		int temLen = temValue.length();
		if (temLen > len) {
			return temValue.substring(0, len);
		}
		if (temLen < len) {
			temValue = temValue + BLANCOS.substring(0, len - temLen);
		}
		return temValue;
	}

	public Object fromString(String str) {
		return str.trim();
	}

	public void writeTo(Object value, OutputStream out) throws IOException {
		if (len>0) {
			out.write(asString(value).getBytes());
		}
	}

	public Object readFrom(InputStream in) throws IOException {
		if (len>0) {
			byte[] buff = new byte[len];
			int n = in.read(buff);
			if (n != len) {
				throw new IOException("No existe informacion suficiente en el stream para obtener " + len + " bytes solo se obtubieron " + n);
			}
			log.debug("XFormater leyo[" + new String(buff) + "] len:" + len + " n:" + n);
			return new String(buff);
		}
		else {
			return "";
		}
	}

	public void init(String fmt) throws InvalidFormatException {
		fmt = fmt.toUpperCase();
		Pattern pattern = Pattern.compile(PatternConstant.PATTERN_VARIABLE);
		Matcher matcher = pattern.matcher(fmt);
		if (!matcher.matches()) {
			throw new InvalidFormatException("Para el VariableFormatter el formato tiene que ser " + PatternConstant.PATTERN_VARIABLE);
		}
		len = 0;
		//buff = null;
	}

	public static void main(String[] args) throws Exception {
		XFormatter x = new XFormatter();
		x.init("X(015)");
		log.info("[" + x.asString("Felipe de Jesus Gerard Contreras") + "]");
		log.info("[" + x.asString("Felipe") + "]");
		log.info("[" + x.asString(new Long("45")) + "]");
		log.info("[" + x.fromString("45") + "]");
	}

//	public void postInit(Element element) {
//		depends=element.getAttribute("depends");
//	}

}
