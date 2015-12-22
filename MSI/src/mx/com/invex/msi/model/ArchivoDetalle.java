package mx.com.invex.msi.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the ARCHIVO_DETALLE database table.
 * 
 */
@Entity
@Table(name="ARCHIVO_DETALLE")
public class ArchivoDetalle implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ARCHIVO_DETALLE_IDDETALLE_GENERATOR", sequenceName="MSI_CAMPANIA_SEQ",allocationSize=1,initialValue=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ARCHIVO_DETALLE_IDDETALLE_GENERATOR")
	@Column(name="ID_DETALLE")
	private long idDetalle;

	@Column(name="CODIGO_CAMPANIA")
	private String codigoCampania;

	@Column(name="CODIGO_PROMOCION")
	private String codigoPromocion;

	private String cuenta;

	private String estado;

    @Temporal( TemporalType.DATE)
	@Column(name="FECHA_FIN")
	private Date fechaFin;

    @Temporal( TemporalType.DATE)
	@Column(name="FECHA_INICIO")
	private Date fechaInicio;



	private Integer shot;

	//bi-directional many-to-one association to Archivo
    @ManyToOne
	@JoinColumn(name="ID_ARCHIVO")
	private Archivo archivo;

	//bi-directional many-to-one association to Campania
    @ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_CAMPANIA")
	private Campania campania;

    public ArchivoDetalle() {
    }

	public long getIdDetalle() {
		return this.idDetalle;
	}

	public void setIdDetalle(long idDetalle) {
		this.idDetalle = idDetalle;
	}

	public String getCodigoCampania() {
		return this.codigoCampania;
	}

	public void setCodigoCampania(String codigoCampania) {
		this.codigoCampania = codigoCampania;
	}

	public String getCodigoPromocion() {
		return this.codigoPromocion;
	}

	public void setCodigoPromocion(String codigoPromocion) {
		this.codigoPromocion = codigoPromocion;
	}

	public String getCuenta() {
		return this.cuenta;
	}

	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaFin() {
		return this.fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Date getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}


	public Integer getShot() {
		return this.shot;
	}

	public void setShot(Integer shot) {
		this.shot = shot;
	}

	public Archivo getArchivo() {
		return this.archivo;
	}

	public void setArchivo(Archivo archivo) {
		this.archivo = archivo;
	}
	
	public Campania getCampania() {
		return this.campania;
	}

	public void setCampania(Campania campania) {
		this.campania = campania;
	}
	
}