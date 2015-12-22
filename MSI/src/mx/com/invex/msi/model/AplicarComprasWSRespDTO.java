package mx.com.invex.msi.model;

import java.io.Serializable;
import java.util.List;

public class AplicarComprasWSRespDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int status;
	private String msgError;
	
	private List<InfoEnviadaDTO> mesesPromos;
	private double saldoNoInt;
	private int folio;
	private String cuenta;
	private double montoTotal;
	private double montoAPagar;

	private List<CompraEnviada> comprasEnviadas;
	
	public AplicarComprasWSRespDTO() {
		msgError="";
	}
	

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



	public List<InfoEnviadaDTO> getMesesPromos() {
		return mesesPromos;
	}



	public void setMesesPromos(List<InfoEnviadaDTO> mesesPromos) {
		this.mesesPromos = mesesPromos;
	}



	public double getSaldoNoInt() {
		return saldoNoInt;
	}



	public void setSaldoNoInt(double saldoNoInt) {
		this.saldoNoInt = saldoNoInt;
	}



	public int getFolio() {
		return folio;
	}



	public void setFolio(int folio) {
		this.folio = folio;
	}



	public String getCuenta() {
		return cuenta;
	}



	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}



	public double getMontoTotal() {
		return montoTotal;
	}



	public void setMontoTotal(double montoTotal) {
		this.montoTotal = montoTotal;
	}



	public double getMontoAPagar() {
		return montoAPagar;
	}



	public void setMontoAPagar(double montoAPagar) {
		this.montoAPagar = montoAPagar;
	}


	public List<CompraEnviada> getComprasEnviadas() {
		return comprasEnviadas;
	}


	public void setComprasEnviadas(List<CompraEnviada> comprasEnviadas) {
		this.comprasEnviadas = comprasEnviadas;
	}


	
	
	
	
}
