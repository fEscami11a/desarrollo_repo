package mx.com.invex.exception;

import javax.xml.ws.WebFault;

@WebFault
public class InvexWSException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvexWSException(String message) {
		super(message);
		}
		 
		public String getFaultInfo() {
		return this.getMessage();
		}

}
