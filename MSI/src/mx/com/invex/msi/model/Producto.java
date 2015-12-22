package mx.com.invex.msi.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
@Entity
@Table(name="MATRIZ_PRODUCTOS",schema="USR_CATALOGOS")
public class Producto implements Serializable{
	
	
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="ID_AGENTE")
	private Integer id;
	@Column(name="BIN_BANCO")
	private String bin;
	@Column(name="U_CODE3_ACT")
	private String ucode3;
	private String campania;
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "productos")
	private Set<Campania> campanias = new HashSet<Campania>();
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public String getBin() {
	return bin;
}
public void setBin(String bin) {
	this.bin = bin;
}
public String getUcode3() {
	return ucode3;
}
public void setUcode3(String ucode3) {
	this.ucode3 = ucode3;
}
public String getCampania() {
	return campania;
}
public void setCampania(String campania) {
	this.campania = campania;
}
public Set<Campania> getCampanias() {
	return campanias;
}
public void setCampanias(Set<Campania> campanias) {
	this.campanias = campanias;
}


}
