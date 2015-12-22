package mx.com.invex.msi.model;

public class RepDto {
private String cuenta,folio;

public RepDto(String cuenta,String folio) {
	this.cuenta=cuenta;
	this.folio=folio;
}

public String getCuenta() {
	return cuenta;
}

public void setCuenta(String cuenta) {
	this.cuenta = cuenta;
}

public String getFolio() {
	return folio;
}

public void setFolio(String folio) {
	this.folio = folio;
}

}
