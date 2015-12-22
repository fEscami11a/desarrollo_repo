/*
 * Created on Dec 20, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package mx.com.interware.spira.web;

/**
 * @author aalvarez
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MsgDescription {
	private String instant;
	private String dmsType;
	private long delta;
	
	
	/**
	 * @return
	 */
	public long getDelta() {
		return delta;
	}

	/**
	 * @return
	 */
	public String getDmsType() {
		return dmsType;
	}

	/**
	 * @return
	 */
	public String getInstant() {
		return instant;
	}

	/**
	 * @param l
	 */
	public void setDelta(long l) {
		delta = l;
	}

	/**
	 * @param string
	 */
	public void setDmsType(String string) {
		dmsType = string;
	}

	/**
	 * @param string
	 */
	public void setInstant(String string) {
		instant = string;
	}

}
