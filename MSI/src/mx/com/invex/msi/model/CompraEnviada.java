package mx.com.invex.msi.model;

import java.io.Serializable;

public class CompraEnviada implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private String fechaCompra;
private String descripcion;
private String montoOriginal;
private String montoaPromo;
private String meses;
private String pagoMensual;
public String getFechaCompra() {
	return fechaCompra;
}
public void setFechaCompra(String fechaCompra) {
	this.fechaCompra = fechaCompra;
}
public String getDescripcion() {
	return descripcion;
}
public void setDescripcion(String descripcion) {
	this.descripcion = descripcion;
}
public String getMontoOriginal() {
	return montoOriginal;
}
public void setMontoOriginal(String montoOriginal) {
	this.montoOriginal = montoOriginal;
}
public String getMontoaPromo() {
	return montoaPromo;
}
public void setMontoaPromo(String montoaPromo) {
	this.montoaPromo = montoaPromo;
}
public String getMeses() {
	return meses;
}
public void setMeses(String meses) {
	this.meses = meses;
}
public String getPagoMensual() {
	return pagoMensual;
}
public void setPagoMensual(String pagoMensual) {
	this.pagoMensual = pagoMensual;
}


}
