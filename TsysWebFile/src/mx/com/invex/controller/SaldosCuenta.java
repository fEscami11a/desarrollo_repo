package mx.com.invex.controller;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SaldosCuenta {
private double ppngi,revolvente,msi,mci;
private double revolventeAlcorte,msiAlcorte,mciAlcorte,sacosRev,sacoRevAlCorte;
@XmlElement
public double getPpngi() {
	return ppngi;
}

public void setPpngi(double ppngi) {
	this.ppngi = ppngi;
}
@XmlElement
public double getRevolvente() {
	return revolvente;
}

public void setRevolvente(double revolvente) {
	this.revolvente = revolvente;
}
@XmlElement
public double getMsi() {
	return msi;
}

public void setMsi(double msi) {
	this.msi = msi;
}
@XmlElement
public double getMci() {
	return mci;
}

public void setMci(double mci) {
	this.mci = mci;
}

@XmlElement
public double getRevolventeAlcorte() {
	return revolventeAlcorte;
}

public void setRevolventeAlcorte(double revolventeAlcorte) {
	this.revolventeAlcorte = revolventeAlcorte;
}
@XmlElement
public double getMsiAlcorte() {
	return msiAlcorte;
}

public void setMsiAlcorte(double msiAlcorte) {
	this.msiAlcorte = msiAlcorte;
}
@XmlElement
public double getMciAlcorte() {
	return mciAlcorte;
}

public void setMciAlcorte(double mciAlcorte) {
	this.mciAlcorte = mciAlcorte;
}
@XmlElement
public double getSacosRev() {
	return sacosRev;
}

public void setSacosRev(double sacosRev) {
	this.sacosRev = sacosRev;
}
@XmlElement
public double getSacoRevAlCorte() {
	return sacoRevAlCorte;
}

public void setSacoRevAlCorte(double sacoRevAlCorte) {
	this.sacoRevAlCorte = sacoRevAlCorte;
}



}
