package mx.com.invex.msi.model;

import java.io.Serializable;
import java.util.List;

public class ComprasWSRespDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int status;
	private String msgError;
	public ComprasWSRespDTO() {
		msgError="";
	}
	
	private List<CompraWSDTO> compras;
	
	private double saldoRevActual;
	private double saldoCorteAnterior;
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
	public List<CompraWSDTO> getCompras() {
		return compras;
	}
	public void setCompras(List<CompraWSDTO> compras) {
		this.compras = compras;
	}
	public double getSaldoRevActual() {
		return saldoRevActual;
	}
	public void setSaldoRevActual(double saldoRevActual) {
		this.saldoRevActual = saldoRevActual;
	}
	public double getSaldoCorteAnterior() {
		return saldoCorteAnterior;
	}
	public void setSaldoCorteAnterior(double saldoCorteAnterior) {
		this.saldoCorteAnterior = saldoCorteAnterior;
	}
	
	
}
