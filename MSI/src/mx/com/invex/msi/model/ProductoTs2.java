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
@Table(name="PRODUCTO_TS2",schema="USR_CATALOGOS")
public class ProductoTs2 implements Serializable{
	
	
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="ID_PRODUCTO")
	private Integer id;
	@Column(name="BIN")
	private String bin;
	@Column(name="PRODUCTO")
	private String producto;
	private String tpc;
	private String cpc;
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "productosts2")
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
public String getProducto() {
	return producto;
}
public void setProducto(String producto) {
	this.producto = producto;
}
public String getTpc() {
	return tpc;
}
public void setTpc(String tpc) {
	this.tpc = tpc;
}
public String getCpc() {
	return cpc;
}
public void setCpc(String cpc) {
	this.cpc = cpc;
}
public Set<Campania> getCampanias() {
	return campanias;
}
public void setCampanias(Set<Campania> campanias) {
	this.campanias = campanias;
}


}
