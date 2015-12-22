package mx.com.invex.seguridad.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class UsuarioDTO implements Serializable{

	private static final long serialVersionUID = 1L;
private String nombre;
private String webuser;
private String cuenta;
private String rfc;
private String email;
private BigDecimal identificador;


public BigDecimal getIdentificador() {
	return identificador;
}
public void setIdentificador(BigDecimal identificador) {
	this.identificador = identificador;
}
public String getNombre() {
	return nombre;
}
public void setNombre(String nombre) {
	this.nombre = nombre;
}
public String getWebuser() {
	return webuser;
}
public void setWebuser(String webuser) {
	this.webuser = webuser;
}
public String getCuenta() {
	return cuenta;
}
public void setCuenta(String cuenta) {
	this.cuenta = cuenta;
}
public String getRfc() {
	return rfc;
}
public void setRfc(String rfc) {
	this.rfc = rfc;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}

}
