package mx.com.invex.msi.model;

import java.io.Serializable;

public class RepConcentradoDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer numCompras;
	private String origen;
	private String status;
	public Integer getNumCompras() {
		return numCompras;
	}
	public void setNumCompras(Integer numCompras) {
		this.numCompras = numCompras;
	}
	public String getOrigen() {
		return origen;
	}
	public void setOrigen(String origen) {
		this.origen = origen;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
