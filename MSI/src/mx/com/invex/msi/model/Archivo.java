package mx.com.invex.msi.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the ARCHIVO database table.
 * 
 */
@Entity
public class Archivo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ARCHIVO_IDARCHIVO_GENERATOR", sequenceName="SEQ_ARCHIVO",allocationSize=1,initialValue=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ARCHIVO_IDARCHIVO_GENERATOR")
	@Column(name="ID_ARCHIVO")
	private long idArchivo;

    @Temporal( TemporalType.DATE)
	@Column(name="FECHA_CARGA")
	private Date fechaCarga;

	private String nombre;

	//bi-directional many-to-one association to ArchivoDetalle
	@OneToMany(mappedBy="archivo",cascade=CascadeType.ALL,orphanRemoval=true)
	private Set<ArchivoDetalle> archivoDetalles;

    public Archivo() {
    }

	public long getIdArchivo() {
		return this.idArchivo;
	}

	public void setIdArchivo(long idArchivo) {
		this.idArchivo = idArchivo;
	}

	public Date getFechaCarga() {
		return this.fechaCarga;
	}

	public void setFechaCarga(Date fechaCarga) {
		this.fechaCarga = fechaCarga;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set<ArchivoDetalle> getArchivoDetalles() {
		return this.archivoDetalles;
	}

	public void setArchivoDetalles(Set<ArchivoDetalle> archivoDetalles) {
		this.archivoDetalles = archivoDetalles;
	}
	
}