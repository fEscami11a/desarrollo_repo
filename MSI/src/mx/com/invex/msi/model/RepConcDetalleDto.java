package mx.com.invex.msi.model;

import java.io.Serializable;

public class RepConcDetalleDto implements Serializable{
	
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private Integer total;
private String fecha;
private Integer totPU;
private Integer totPortal;
private Integer totApl;
private Integer totEnv;
public Integer getTotal() {
	return total;
}
public void setTotal(Integer total) {
	this.total = total;
}
public String getFecha() {
	return fecha;
}
public void setFecha(String fecha) {
	this.fecha = fecha;
}
public Integer getTotPU() {
	return totPU;
}
public void setTotPU(Integer totPU) {
	this.totPU = totPU;
}
public Integer getTotPortal() {
	return totPortal;
}
public void setTotPortal(Integer totPortal) {
	this.totPortal = totPortal;
}
public Integer getTotApl() {
	return totApl;
}
public void setTotApl(Integer totApl) {
	this.totApl = totApl;
}
public Integer getTotEnv() {
	return totEnv;
}
public void setTotEnv(Integer totEnv) {
	this.totEnv = totEnv;
}

}
