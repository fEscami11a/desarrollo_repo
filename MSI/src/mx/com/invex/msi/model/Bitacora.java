package mx.com.invex.msi.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Bitacora implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="IDBITACORA_GENERATOR", sequenceName="SEQ_BITACORA",allocationSize=1,initialValue=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="IDBITACORA_GENERATOR")
	@Column(name="ID_BITACORA")
	private long idBitacora;
	
	@Temporal( TemporalType.DATE)
	@Column(name="FECHA")
	private Date fecha;
	@Column(name="TIPO",length=10,nullable=false)
	private String tipo;
	@Column(name="MENSAJE",length=255,nullable=false)
	private String mensaje;
	@Column(name="USERNAME",length=50,nullable=false)
	private String username;
	@Column(name="IP_HOST",length=50,nullable=false)
	private String ipHost;
	public long getIdBitacora() {
		return idBitacora;
	}
	public void setIdBitacora(long idBitacora) {
		this.idBitacora = idBitacora;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getIpHost() {
		return ipHost;
	}
	public void setIpHost(String ipHost) {
		this.ipHost = ipHost;
	}
	
	

}
