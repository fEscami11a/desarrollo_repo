package mx.com.interware.spira.message.exceptions;


public class InvalidFormatException extends TotalMessageException {
	
	public InvalidFormatException() {
		super();
	}

	public InvalidFormatException(String message) {
		super(message);
	}
	
	public InvalidFormatException(String message,Throwable cause) {
		super(message,cause);
	}

}
