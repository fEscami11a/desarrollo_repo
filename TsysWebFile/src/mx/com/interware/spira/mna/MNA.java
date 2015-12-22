/*
 * Created on Nov 10, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package mx.com.interware.spira.mna;


import mx.com.interware.spira.message.exceptions.TotalMessageException;

/**
 * Class MNA.java
 * Package mx.com.interware.spira.web.mna
 * Created on Nov 10, 2005
 * @author Marcos Díaz
 * @version 1.0
 * 
 * 
 */

public interface MNA {
	
	public String updateAccount(MNAParam param) throws TotalMessageException;
	
	
}
