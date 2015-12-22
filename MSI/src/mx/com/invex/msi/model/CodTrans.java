package mx.com.invex.msi.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
@Entity
@Table(name="CODIGO_TRANS")
public class CodTrans implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="COD_TRANS")
	private Long codTrans;
	
	@Column
	private String descripcion;
	
	
	public Long getCodTrans() {
		return codTrans;
	}

	public void setCodTrans(Long codTrans) {
		this.codTrans = codTrans;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
}
