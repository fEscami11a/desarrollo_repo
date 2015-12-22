package mx.com.invex.bmovil.exception;

import javax.xml.ws.WebFault;

@WebFault
public class WSSeguridadException extends Exception{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public WSSeguridadException(String message) {
		super(message);
		}
		 
		public String getFaultInfo() {
		return this.getMessage();
		}

}

