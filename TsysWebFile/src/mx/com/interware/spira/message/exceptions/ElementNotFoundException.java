package mx.com.interware.spira.message.exceptions;


public class ElementNotFoundException extends TotalMessageException {
	public ElementNotFoundException() {
		super();
	}

	public ElementNotFoundException(String message) {
		super(message);
	}
	
	public ElementNotFoundException(String message,Throwable cause) {
		super(message,cause);
	}
}
