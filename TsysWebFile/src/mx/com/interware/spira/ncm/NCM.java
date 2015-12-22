/*
 * Created on Oct 6, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package mx.com.interware.spira.ncm;

import mx.com.interware.spira.message.exceptions.TotalMessageException;

public interface NCM
{
	String addCollectionMessage(String account, String userName, String date, String time, String collNum, String msgType, String actionCode, String msgText) throws TotalMessageException;
}
