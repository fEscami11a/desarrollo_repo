/*
 * Created on Jul 22, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package mx.com.interware.spira.message.util;

import mx.com.interware.spira.message.exceptions.InvalidFormatException;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class NumberFormatterBuilder {
	/**
	 *
	 * Contruye e inicializa una instacia del objeto NineDecimalFormatter
	 */

	public Formatter create(String fmt) throws InvalidFormatException {
		Formatter formatter = new NumberFormatter();
		formatter.init(fmt);
		return formatter;
	}

}
