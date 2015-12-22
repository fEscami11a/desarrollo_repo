package mx.com.interware.spira.dto;

import java.io.Serializable;

public class CuentaAD  implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private String numCuenta,ucode3,tipoCuenta,clasificacion,nombreCompleto;

public String getNumCuenta() {
	return numCuenta;
}

public void setNumCuenta(String numCuenta) {
	this.numCuenta = numCuenta;
}

public String getUcode3() {
	return ucode3;
}

public void setUcode3(String ucode3) {
	this.ucode3 = ucode3;
}

public String getTipoCuenta() {
	return tipoCuenta;
}

public void setTipoCuenta(String tipoCuenta) {
	this.tipoCuenta = tipoCuenta;
}

public String getClasificacion() {
	return clasificacion;
}

public void setClasificacion(String clasificacion) {
	this.clasificacion = clasificacion;
}

public String getNombreCompleto() {
	return nombreCompleto;
}

public void setNombreCompleto(String nombreCompleto) {
	this.nombreCompleto = nombreCompleto;
}

@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((numCuenta == null) ? 0 : numCuenta.hashCode());
	return result;
}

@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	CuentaAD other = (CuentaAD) obj;
	if (numCuenta == null) {
		if (other.numCuenta != null)
			return false;
	} else if (!numCuenta.equals(other.numCuenta))
		return false;
	return true;
}



}
