package mx.com.invex.seguridad.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the PREGUNTAS database table.
 * 
 */
@Entity
@Table(name="PREGUNTAS")
public class Pregunta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String identificador;

    @Temporal( TemporalType.TIMESTAMP)
	private Date fecharegistro;

	private String texto;

	//bi-directional many-to-one association to Usuariopregunta
	@OneToMany(mappedBy="pregunta")
	private Set<Usuariopregunta> usuariopreguntas;

    public Pregunta() {
    }

	public String getIdentificador() {
		return this.identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public Date getFecharegistro() {
		return this.fecharegistro;
	}

	public void setFecharegistro(Date fecharegistro) {
		this.fecharegistro = fecharegistro;
	}

	public String getTexto() {
		return this.texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Set<Usuariopregunta> getUsuariopreguntas() {
		return this.usuariopreguntas;
	}

	public void setUsuariopreguntas(Set<Usuariopregunta> usuariopreguntas) {
		this.usuariopreguntas = usuariopreguntas;
	}
	
}