package mx.com.invex.seguridad.entidades;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SESION database table.
 * 
 */
@Entity
public class Sesion implements Serializable {
	private static final long serialVersionUID = 1L;

	private boolean escaduca;

    @Temporal( TemporalType.TIMESTAMP)
	private Date fecharegistro;

	private String idaplicacionsolicitante;
	@Id
	private String identificador;

	private BigDecimal identificadorusuario;

    @Temporal( TemporalType.TIMESTAMP)
	private Date tiempoexpiracion;

    @Temporal( TemporalType.TIMESTAMP)
	private Date ultimoacceso;

    public Sesion() {
    }

	public boolean getEscaduca() {
		return this.escaduca;
	}

	public void setEscaduca(boolean escaduca) {
		this.escaduca = escaduca;
	}

	public Date getFecharegistro() {
		return this.fecharegistro;
	}

	public void setFecharegistro(Date fecharegistro) {
		this.fecharegistro = fecharegistro;
	}

	public String getIdaplicacionsolicitante() {
		return this.idaplicacionsolicitante;
	}

	public void setIdaplicacionsolicitante(String idaplicacionsolicitante) {
		this.idaplicacionsolicitante = idaplicacionsolicitante;
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

	public Date getTiempoexpiracion() {
		return this.tiempoexpiracion;
	}

	public void setTiempoexpiracion(Date tiempoexpiracion) {
		this.tiempoexpiracion = tiempoexpiracion;
	}

	public Date getUltimoacceso() {
		return this.ultimoacceso;
	}

	public void setUltimoacceso(Date ultimoacceso) {
		this.ultimoacceso = ultimoacceso;
	}

}