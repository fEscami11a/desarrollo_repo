package mx.com.invex.msi.mail;

import java.io.Serializable;
import java.util.List;

public class MailDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nombre;
	private String fecha;
	private String folio;
	private List<CompraEmailDto> compras;
	private String montoTotal;
	private String montoAPagar;
	private String saldoNoInt;
	private String terminacion;
	private String msgPagoMin;
	
	
	public String getMsgPagoMin() {
		return msgPagoMin;
	}
	public void setMsgPagoMin(String msgPagoMin) {
		this.msgPagoMin = msgPagoMin;
	}
	public String getTerminacion() {
		return terminacion;
	}
	public void setTerminacion(String terminacion) {
		this.terminacion = terminacion;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	public String getFolio() {
		return folio;
	}
	public void setFolio(String folio) {
		this.folio = folio;
	}
	public List<CompraEmailDto> getCompras() {
		return compras;
	}
	public void setCompras(List<CompraEmailDto> compras) {
		this.compras = compras;
	}
	public String getMontoTotal() {
		return montoTotal;
	}
	public void setMontoTotal(String montoTotal) {
		this.montoTotal = montoTotal;
	}
	public String getMontoAPagar() {
		return montoAPagar;
	}
	public void setMontoAPagar(String montoAPagar) {
		this.montoAPagar = montoAPagar;
	}
	public String getSaldoNoInt() {
		return saldoNoInt;
	}
	public void setSaldoNoInt(String saldoNoInt) {
		this.saldoNoInt = saldoNoInt;
	}
	
	
}
