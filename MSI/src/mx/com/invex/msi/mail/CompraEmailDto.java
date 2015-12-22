package mx.com.invex.msi.mail;

import java.io.Serializable;

public class CompraEmailDto implements Serializable{
	
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private String fechaCompra;
private String descripcion;
private String montoOriginal;
private String monto;
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
public String getMonto() {
	return monto;
}
public void setMonto(String monto) {
	this.monto = monto;
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
