package mx.com.interware.spira.message.exceptions;

import java.rmi.RemoteException;


public class TotalMessageException extends RemoteException {
	
	public TotalMessageException() {
		super();
	}

	public TotalMessageException(String message) {
		super(message);
	}
	
	public TotalMessageException(String message,Throwable cause) {
		super(message,cause);
	}
	
}
