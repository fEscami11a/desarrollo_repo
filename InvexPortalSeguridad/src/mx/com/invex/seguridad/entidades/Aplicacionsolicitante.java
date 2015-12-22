package mx.com.invex.seguridad.entidades;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the APLICACIONSOLICITANTE database table.
 * 
 */
@Entity
public class Aplicacionsolicitante implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private String identificador;

	private String nombre;

    public Aplicacionsolicitante() {
    }

	public String getIdentificador() {
		return this.identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}