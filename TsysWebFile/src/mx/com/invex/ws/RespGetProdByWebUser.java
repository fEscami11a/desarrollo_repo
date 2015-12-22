package mx.com.invex.ws;

import java.io.Serializable;

public class RespGetProdByWebUser implements Serializable{

	private int status;
	private String msgError;
	private String[] arrCuentas;
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
	public String[] getArrCuentas() {
		return arrCuentas;
	}
	public void setArrCuentas(String[] arrCuentas) {
		this.arrCuentas = arrCuentas;
	}
	
	
}
