package mx.com.invex.seguridad.entidades;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the USUARIOPREGUNTAS database table.
 * 
 */
@Embeddable
public class UsuariopreguntaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String identificador;

	private long idusuario;

    public UsuariopreguntaPK() {
    }
	public String getIdentificador() {
		return this.identificador;
	}
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	public long getIdusuario() {
		return this.idusuario;
	}
	public void setIdusuario(long idusuario) {
		this.idusuario = idusuario;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof UsuariopreguntaPK)) {
			return false;
		}
		UsuariopreguntaPK castOther = (UsuariopreguntaPK)other;
		return 
			this.identificador.equals(castOther.identificador)
			&& (this.idusuario == castOther.idusuario);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.identificador.hashCode();
		hash = hash * prime + ((int) (this.idusuario ^ (this.idusuario >>> 32)));
		
		return hash;
    }
}