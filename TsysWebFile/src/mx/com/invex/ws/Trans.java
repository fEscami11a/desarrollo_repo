package mx.com.invex.ws;

import java.io.Serializable;

public class Trans implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String codTrans,fechaCompra,tipo, monto, desc;

	public String getCodTrans() {
		return codTrans;
	}

	public void setCodTrans(String codTrans) {
		this.codTrans = codTrans;
	}

	public String getFechaCompra() {
		return fechaCompra;
	}

	public void setFechaCompra(String fechaCompra) {
		this.fechaCompra = fechaCompra;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getMonto() {
		return monto;
	}

	public void setMonto(String monto) {
		this.monto = monto;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}
