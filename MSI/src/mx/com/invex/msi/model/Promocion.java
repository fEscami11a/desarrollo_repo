package mx.com.invex.msi.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;


/**
 * The persistent class for the PROMOCION database table.
 * 
 */
@Entity
public class Promocion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_PROMOCION")
	private long idPromocion;

	private String descripcion;

	@Column(name="PLAZO_MESES")
	private Integer plazoMeses;

	@Column(name="PROGRAMA_CERO")
	private String programaCero;
	
	@Column(name="DBA_NAME")
	private String dbaName;
	
	@Column(name="MONTO")
	private Double monto;
	
	 @ManyToMany(mappedBy="promociones")
	    private Set<Campania> campanias = new HashSet<Campania>();
	
    public Promocion() {
    }
    

	public Double getMonto() {
		return monto;
	}


	public void setMonto(Double monto) {
		this.monto = monto;
	}


	public long getIdPromocion() {
		return this.idPromocion;
	}

	public void setIdPromocion(long idPromocion) {
		this.idPromocion = idPromocion;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getPlazoMeses() {
		return this.plazoMeses;
	}

	public void setPlazoMeses(Integer plazoMeses) {
		this.plazoMeses = plazoMeses;
	}

	public String getProgramaCero() {
		return this.programaCero;
	}

	public void setProgramaCero(String programaCero) {
		this.programaCero = programaCero;
	}

	
	

	public String getDbaName() {
		return dbaName;
	}


	public void setDbaName(String dbaName) {
		this.dbaName = dbaName;
	}


	public Set<Campania> getCampanias() {
		return campanias;
	}


	public void setCampanias(Set<Campania> campanias) {
		this.campanias = campanias;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idPromocion ^ (idPromocion >>> 32));
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
		Promocion other = (Promocion) obj;
		if (idPromocion != other.idPromocion)
			return false;
		return true;
	}


	
}