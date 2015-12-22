package mx.com.invex.msi.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;


/**
 * The persistent class for the CAMPANIA database table.
 * 
 */
@Entity
public class Campania implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CAMPANIA_ID_GENERATOR", sequenceName="MSI_CAMPANIA_SEQ",allocationSize=1,initialValue=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CAMPANIA_ID_GENERATOR")
	@Column(name="ID_CAMPANIA")
	private Long idCampania;

	@Column(name="ACTIVACION_TDC")
	private String activacionTdc;

	@Column(name="APLICA_CONDICIONES")
	private String aplicaCondiciones;

	@Column(name="APLICA_NAC_INTERNAC")
	private String aplicaNacInternac;

	@Column(name="BASE_CARGA")
	private String baseCarga;
	@Column(name="CODIGO_TRANSACCION1")
	private String codigoTransaccion1;

	@Column(name="CODIGO_TRANSACCION2")
	private String codigoTransaccion2;

	@Column(name="DIAS_ACTIVACION_TDC")
	private BigDecimal diasActivacionTdc;

	@Column(name="DIAS_MSI_PRIMERA_COMPRA")
	private BigDecimal diasMsiPrimeraCompra;

	private String eliminarv4;

    @Temporal( TemporalType.DATE)
	@Column(name="FECHA_FINAL")
	private Date fechaFinal;

    @Temporal( TemporalType.DATE)
	@Column(name="FECHA_INICIAL")
	private Date fechaInicial;

    @Temporal( TemporalType.DATE)
	@Column(name="FECHA_MAXIMA_REGISTRO")
	private Date fechaMaximaRegistro;

	
	@Column(name="MONTO_O_FACTURACION")
	private String montoOFacturacion;

	@Column(name="MSI_PRIMERA_COMPRA")
	private String msiPrimeraCompra;
	
	@NotNull
	private String nombre;

	@Column(name="NUM_MAX_DIAS_REGISTRO")
	private Integer numMaxDiasRegistro;

	@Column(name="PLAZO_ACTIVACIONTDC")
	private String plazoActivaciontdc;

	@Column(name="PLAZO_PRIMERA_COMPRA")
	private String plazoPrimeraCompra;

	@Column(name="TIPO_CADUCIDAD_REGISTRO")
	private String tipoCaducidadRegistro;

	@Column(name="TIPO_CAMPANIA")
	private String tipoCampania;

	@Column(name="TIPO_MONTO_MINIMO")
	private String tipoMontoMinimo;

	@Column(name="TOTAL_MONTO_O_FACTURACION")
	private BigDecimal totalMontoOFacturacion;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "PRODUCTOS_CAMPANIA", joinColumns = { 
			@JoinColumn(name = "ID_CAMPANIA", nullable = false, updatable = false) }, 
			inverseJoinColumns = { @JoinColumn(name = "ID_PRODUCTO", 
					nullable = false, updatable = false) })
	private Set<Producto> productos = new HashSet<Producto>();
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "PRODUCTOTS2_CAMPANIA", joinColumns = { 
			@JoinColumn(name = "ID_CAMPANIA", nullable = false, updatable = false) }, 
			inverseJoinColumns = { @JoinColumn(name = "ID_PRODUCTO", 
					nullable = false, updatable = false) })
	private Set<ProductoTs2> productosts2 = new HashSet<ProductoTs2>();
	
	 @ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.ALL})
	 @JoinTable(name="ASIGNACION_PROMOS_CAMPANIA", 
	                joinColumns={@JoinColumn(name="ID_CAMPANIA")}, 
	                inverseJoinColumns={@JoinColumn(name="ID_PROMOCION")})
	 private Set<Promocion> promociones = new HashSet<Promocion>();
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "campanias")
	private Set<Comercio> comercios = new HashSet<Comercio>();
	
	
	
	 
    public Set<ProductoTs2> getProductosts2() {
		return productosts2;
	}


	public void setProductosts2(Set<ProductoTs2> productosts2) {
		this.productosts2 = productosts2;
	}


	public Set<Comercio> getComercios() {
		return comercios;
	}


	public void setComercios(Set<Comercio> comercios) {
		this.comercios = comercios;
	}



	public Set<Promocion> getPromociones() {
		return promociones;
	}


	public void setPromociones(Set<Promocion> promociones) {
		this.promociones = promociones;
	}


	public Campania() {
    }
    
    
	public Set<Producto> getProductos() {
		return productos;
	}


	public void setProductos(Set<Producto> productos) {
		this.productos = productos;
	}


	public Long getIdCampania() {
		return this.idCampania;
	}

	public void setIdCampania(Long idCampania) {
		this.idCampania = idCampania;
	}

	public String getActivacionTdc() {
		return this.activacionTdc;
	}

	public void setActivacionTdc(String activacionTdc) {
		this.activacionTdc = activacionTdc;
	}

	public String getAplicaCondiciones() {
		return this.aplicaCondiciones;
	}

	public void setAplicaCondiciones(String aplicaCondiciones) {
		this.aplicaCondiciones = aplicaCondiciones;
	}

	public String getAplicaNacInternac() {
		return this.aplicaNacInternac;
	}

	public void setAplicaNacInternac(String aplicaNacInternac) {
		this.aplicaNacInternac = aplicaNacInternac;
	}

	public String getBaseCarga() {
		return this.baseCarga;
	}

	public void setBaseCarga(String baseCarga) {
		this.baseCarga = baseCarga;
	}

	public String getCodigoTransaccion1() {
		return this.codigoTransaccion1;
	}

	public void setCodigoTransaccion1(String codigoTransaccion1) {
		this.codigoTransaccion1 = codigoTransaccion1;
	}

	public String getCodigoTransaccion2() {
		return this.codigoTransaccion2;
	}

	public void setCodigoTransaccion2(String codigoTransaccion2) {
		this.codigoTransaccion2 = codigoTransaccion2;
	}

	public BigDecimal getDiasActivacionTdc() {
		return this.diasActivacionTdc;
	}

	public void setDiasActivacionTdc(BigDecimal diasActivacionTdc) {
		this.diasActivacionTdc = diasActivacionTdc;
	}

	public BigDecimal getDiasMsiPrimeraCompra() {
		return this.diasMsiPrimeraCompra;
	}

	public void setDiasMsiPrimeraCompra(BigDecimal diasMsiPrimeraCompra) {
		this.diasMsiPrimeraCompra = diasMsiPrimeraCompra;
	}

	public String getEliminarv4() {
		return this.eliminarv4;
	}

	public void setEliminarv4(String eliminarv4) {
		this.eliminarv4 = eliminarv4;
	}

	public Date getFechaFinal() {
		return this.fechaFinal;
	}

	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public Date getFechaInicial() {
		return this.fechaInicial;
	}

	public void setFechaInicial(Date fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	public Date getFechaMaximaRegistro() {
		return this.fechaMaximaRegistro;
	}

	public void setFechaMaximaRegistro(Date fechaMaximaRegistro) {
		this.fechaMaximaRegistro = fechaMaximaRegistro;
	}



	public String getMontoOFacturacion() {
		return this.montoOFacturacion;
	}

	public void setMontoOFacturacion(String montoOFacturacion) {
		this.montoOFacturacion = montoOFacturacion;
	}

	public String getMsiPrimeraCompra() {
		return this.msiPrimeraCompra;
	}

	public void setMsiPrimeraCompra(String msiPrimeraCompra) {
		this.msiPrimeraCompra = msiPrimeraCompra;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getNumMaxDiasRegistro() {
		return this.numMaxDiasRegistro;
	}

	public void setNumMaxDiasRegistro(Integer numMaxDiasRegistro) {
		this.numMaxDiasRegistro = numMaxDiasRegistro;
	}

	public String getPlazoActivaciontdc() {
		return this.plazoActivaciontdc;
	}

	public void setPlazoActivaciontdc(String plazoActivaciontdc) {
		this.plazoActivaciontdc = plazoActivaciontdc;
	}

	public String getPlazoPrimeraCompra() {
		return this.plazoPrimeraCompra;
	}

	public void setPlazoPrimeraCompra(String plazoPrimeraCompra) {
		this.plazoPrimeraCompra = plazoPrimeraCompra;
	}

	public String getTipoCaducidadRegistro() {
		return this.tipoCaducidadRegistro;
	}

	public void setTipoCaducidadRegistro(String tipoCaducidadRegistro) {
		this.tipoCaducidadRegistro = tipoCaducidadRegistro;
	}

	public String getTipoCampania() {
		return this.tipoCampania;
	}

	public void setTipoCampania(String tipoCampania) {
		this.tipoCampania = tipoCampania;
	}

	public String getTipoMontoMinimo() {
		return this.tipoMontoMinimo;
	}

	public void setTipoMontoMinimo(String tipoMontoMinimo) {
		this.tipoMontoMinimo = tipoMontoMinimo;
	}

	public BigDecimal getTotalMontoOFacturacion() {
		return this.totalMontoOFacturacion;
	}

	public void setTotalMontoOFacturacion(BigDecimal totalMontoOFacturacion) {
		this.totalMontoOFacturacion = totalMontoOFacturacion;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idCampania == null) ? 0 : idCampania.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Campania other = (Campania) obj;
		if (idCampania == null) {
			if (other.idCampania != null)
				return false;
		} else if (!idCampania.equals(other.idCampania))
			return false;
		return true;
	}


	
}