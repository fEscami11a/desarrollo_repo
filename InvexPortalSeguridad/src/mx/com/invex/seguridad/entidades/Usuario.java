package mx.com.invex.seguridad.entidades;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the USUARIO database table.
 * 
 */
@Entity
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	private String apellidomaterno;

	private String apellidopaterno;

	private String contrasenia;

	private String correoelectronico;

	private boolean estatus;

	private BigDecimal expirasesion;
	
	private String data4u1;

    @Temporal( TemporalType.TIMESTAMP)
	private Date fechaexpiracioncontrasenia;

    @Temporal( TemporalType.TIMESTAMP)
	private Date fechanacimiento;

    @Temporal( TemporalType.TIMESTAMP)
	private Date fecharegistro;
    @Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private BigDecimal identificador;

	private BigDecimal idrol;

	private String nombre;

	private String nombretarjeta;

	private String nombreusuario;

	private String rfc;

    @Temporal( TemporalType.TIMESTAMP)
	private Date ultimoacceso;

    public Usuario() {
    }

	public String getApellidomaterno() {
		return this.apellidomaterno;
	}

	public void setApellidomaterno(String apellidomaterno) {
		this.apellidomaterno = apellidomaterno;
	}

	public String getApellidopaterno() {
		return this.apellidopaterno;
	}

	public void setApellidopaterno(String apellidopaterno) {
		this.apellidopaterno = apellidopaterno;
	}

	public String getContrasenia() {
		return this.contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public String getCorreoelectronico() {
		return this.correoelectronico;
	}

	public void setCorreoelectronico(String correoelectronico) {
		this.correoelectronico = correoelectronico;
	}

	

	public boolean isEstatus() {
		return estatus;
	}

	public void setEstatus(boolean estatus) {
		this.estatus = estatus;
	}

	public BigDecimal getExpirasesion() {
		return this.expirasesion;
	}

	public void setExpirasesion(BigDecimal expirasesion) {
		this.expirasesion = expirasesion;
	}

	public Date getFechaexpiracioncontrasenia() {
		return this.fechaexpiracioncontrasenia;
	}

	public void setFechaexpiracioncontrasenia(Date fechaexpiracioncontrasenia) {
		this.fechaexpiracioncontrasenia = fechaexpiracioncontrasenia;
	}

	public Date getFechanacimiento() {
		return this.fechanacimiento;
	}

	public void setFechanacimiento(Date fechanacimiento) {
		this.fechanacimiento = fechanacimiento;
	}

	public Date getFecharegistro() {
		return this.fecharegistro;
	}

	public void setFecharegistro(Date fecharegistro) {
		this.fecharegistro = fecharegistro;
	}

	public BigDecimal getIdentificador() {
		return this.identificador;
	}

	public void setIdentificador(BigDecimal identificador) {
		this.identificador = identificador;
	}

	public BigDecimal getIdrol() {
		return this.idrol;
	}

	public void setIdrol(BigDecimal idrol) {
		this.idrol = idrol;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombretarjeta() {
		return this.nombretarjeta;
	}

	public void setNombretarjeta(String nombretarjeta) {
		this.nombretarjeta = nombretarjeta;
	}

	public String getNombreusuario() {
		return this.nombreusuario;
	}

	public void setNombreusuario(String nombreusuario) {
		this.nombreusuario = nombreusuario;
	}

	public String getRfc() {
		return this.rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public Date getUltimoacceso() {
		return this.ultimoacceso;
	}

	public void setUltimoacceso(Date ultimoacceso) {
		this.ultimoacceso = ultimoacceso;
	}

	public String getData4u1() {
		return data4u1;
	}

	public void setData4u1(String data4u1) {
		this.data4u1 = data4u1;
	}
	
	

}