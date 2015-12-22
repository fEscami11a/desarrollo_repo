package mx.com.invex.msi.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;


/**
 * The persistent class for the COMERCIO database table.
 * 
 */
@Entity
public class Comercio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COMERCIO")
	private long idComercio;

	private String nombre;
	
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "COMERCIOS_CAMPANIA", joinColumns = { 
			@JoinColumn(name = "ID_COMERCIO", nullable = false, updatable = false) }, 
			inverseJoinColumns = { @JoinColumn(name = "ID_CAMPANIA", 
					nullable = false, updatable = false) })
	private Set<Campania> campanias = new HashSet<Campania>();

    public Comercio() {
    }
    
    

	public Set<Campania> getCampanias() {
		return campanias;
	}



	public void setCampanias(Set<Campania> campanias) {
		this.campanias = campanias;
	}



	public long getIdComercio() {
		return this.idComercio;
	}

	public void setIdComercio(long idComercio) {
		this.idComercio = idComercio;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}