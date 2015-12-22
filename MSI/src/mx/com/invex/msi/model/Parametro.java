package mx.com.invex.msi.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="PARAMETRO")
public class Parametro implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Parametro() {
		
	}
	@Id
	private String llave;
	
	private String valor;
	
	private String descripcion;
	
	public String getLlave() {
		return llave;
	}
	public void setLlave(String llave) {
		this.llave = llave;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
