package mx.com.invex.seguridad.entidades;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the HISTORIALCONTRASENIAS database table.
 * 
 */
@Entity
@Table(name="HISTORIALCONTRASENIAS")
public class Historialcontrasenia implements Serializable {
	private static final long serialVersionUID = 1L;

	private String contrasenia;

    @Temporal( TemporalType.TIMESTAMP)
	private Date fecharegistro;
    @Id
	@GeneratedValue
	private String identificador;

	private BigDecimal identificadorusuario;

    public Historialcontrasenia() {
    }

	public String getContrasenia() {
		return this.contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public Date getFecharegistro() {
		return this.fecharegistro;
	}

	public void setFecharegistro(Date fecharegistro) {
		this.fecharegistro = fecharegistro;
	}

	public String getIdentificador() {
		return this.identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public BigDecimal getIdentificadorusuario() {
		return this.identificadorusuario;
	}

	public void setIdentificadorusuario(BigDecimal identificadorusuario) {
		this.identificadorusuario = identificadorusuario;
	}

}