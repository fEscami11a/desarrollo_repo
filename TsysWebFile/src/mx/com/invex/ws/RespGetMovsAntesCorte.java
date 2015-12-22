package mx.com.invex.ws;

import java.io.Serializable;

public class RespGetMovsAntesCorte implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int status;
	private String msgError;
	private Trans[] movs;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMsgError() {
		return msgError;
	}
	public void setMsgError(String msgError) {
		this.msgError = msgError;
	}
	public Trans[] getMovs() {
		return movs;
	}
	public void setMovs(Trans[] movs) {
		this.movs = movs;
	}
	
	
}
