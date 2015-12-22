package mx.com.invex.seguridad.entidades;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the USUARIOPREGUNTAS database table.
 * 
 */
@Entity
@Table(name="USUARIOPREGUNTAS")
public class Usuariopregunta implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private UsuariopreguntaPK id;

	private String respuesta;

	//bi-directional many-to-one association to Pregunta
    @ManyToOne
	@JoinColumn(name="IDPREGUNTA")
	private Pregunta pregunta;

    public Usuariopregunta() {
    }

	public UsuariopreguntaPK getId() {
		return this.id;
	}

	public void setId(UsuariopreguntaPK id) {
		this.id = id;
	}
	
	public String getRespuesta() {
		return this.respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public Pregunta getPregunta() {
		return this.pregunta;
	}

	public void setPregunta(Pregunta pregunta) {
		this.pregunta = pregunta;
	}
	
}