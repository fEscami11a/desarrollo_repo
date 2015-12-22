/*
 * Created on Dec 20, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package mx.com.interware.spira.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author aalvarez
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Util {

	public static String parseFormatFileDate(Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		return dateFormat.format(date);
	}

	public static Date parseFormatFileDate(String date) {
		if (date == null) {
			return null;
		}
		Date dateParsed = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_hh:mm:ss");
		try {
			dateParsed = dateFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateParsed;
	}
}
