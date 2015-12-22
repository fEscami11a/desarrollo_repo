package mx.com.invex.seguridad.entidades;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the INTENTOSESION database table.
 * 
 */
@Entity
public class Intentosesion implements Serializable {
	private static final long serialVersionUID = 1L;

	private boolean esbloqueada;

	@Id
	private String identificador;

	private BigDecimal identificadorusuario;

	private int numerointentos;

    @Temporal( TemporalType.TIMESTAMP)
	private Date tiemporegistro;

    public Intentosesion() {
    }

	

	public boolean isEsbloqueada() {
		return esbloqueada;
	}



	public void setEsbloqueada(boolean esbloqueada) {
		this.esbloqueada = esbloqueada;
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

	public int getNumerointentos() {
		return this.numerointentos;
	}

	public void setNumerointentos(int numerointentos) {
		this.numerointentos = numerointentos;
	}

	public Date getTiemporegistro() {
		return this.tiemporegistro;
	}

	public void setTiemporegistro(Date tiemporegistro) {
		this.tiemporegistro = tiemporegistro;
	}

}