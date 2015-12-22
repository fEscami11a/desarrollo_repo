package mx.com.interware.spira.dto;

import java.io.Serializable;

public class CuentaTs2Info implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String cuenta;
	private String producto;
	private String[] bloqueos;
	private String nombre;
	private String tipoCuenta;
	public String getCuenta() {
		return cuenta;
	}
	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}
	public String getProducto() {
		return producto;
	}
	public void setProducto(String producto) {
		this.producto = producto;
	}
	public String[] getBloqueos() {
		return bloqueos;
	}
	public void setBloqueos(String[] bloqueos) {
		this.bloqueos = bloqueos;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getTipoCuenta() {
		return tipoCuenta;
	}
	public void setTipoCuenta(String tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}
	

}
