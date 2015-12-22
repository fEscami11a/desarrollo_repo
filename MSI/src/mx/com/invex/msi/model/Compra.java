package mx.com.invex.msi.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import mx.com.invex.msi.util.MSIConstants;


/**
 * The persistent class for the COMPRA database table.
 * 
 */
@Entity
@XmlRootElement
public class Compra implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="COMPRA_IDCOMPRA_GENERATOR", sequenceName="SEQ_ID_COMPRA",allocationSize=1,initialValue=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="COMPRA_IDCOMPRA_GENERATOR")
	@Column(name="ID_COMPRA")
	private long idCompra;

	@Column(name="CODIGO_TRANSACCION")
	private Integer codigoTransaccion;

	private String cuenta;

	@Column(name="CUENTA_FACTURADORA")
	private String cuentaFacturadora;

	private String descripcion;

	
	@Column(name="DRAFT_DEBITO")
	private String draftDebito;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FECHA_APLICACION_PROMOCION")
	private Date fechaAplicacionPromocion;

    @Temporal( TemporalType.DATE)
	@Column(name="FECHA_APLICACION_PROMOCION1")
	private Date fechaAplicacionPromocion1;

    @Temporal( TemporalType.DATE)
	@Column(name="FECHA_APLICACION_PROMOCION2")
	private Date fechaAplicacionPromocion2;

    @Temporal( TemporalType.DATE)
	@Column(name="FECHA_COMPRA")
	private Date fechaCompra;

    @Temporal( TemporalType.DATE)
	@Column(name="FECHA_ENVIO_DRAFTS")
	private Date fechaEnvioDrafts;

	private Integer folio;

	@Column(name="ID_EDO_PROMOCION")
	private String idEdoPromocion;

	private Double monto;

	@Column(name="NUM_REF_TRAN")
	private String numRefTran;

	@Column(name="TIPO_TRANSACCION")
	private String tipoTransaccion;

	private String username;
	@Column(name="DRAFT_CREDITO")
	private String origen; 

	//bi-directional many-to-one association to Promocion
    @ManyToOne
	@JoinColumn(name="ID_PROMOCION")
	private Promocion promocion;
    
    @Transient
    private Map<String,Promocion> promosCombo= new HashMap<String, Promocion>();
    
    @Transient
    private boolean enPromocion;

    @Transient
    private boolean selected;
    
    @Transient 
    private String statusDesc;
    
    @Transient
    private Double montoPromo;
    
    @Transient
    private Double montoOriginal;
    
	public Double getMontoOriginal() {
		return montoOriginal;
	}

	public void setMontoOriginal(Double montoOriginal) {
		this.montoOriginal = montoOriginal;
	}

	public Double getMontoPromo() {
		return montoPromo;
	}

	public void setMontoPromo(Double montoPromo) {
		this.montoPromo = montoPromo;
	}

	public Compra() {
    }

	public void addComboPromo(String llave, Promocion promo){
		promosCombo.put(llave, promo);
	}
	
	public boolean isSelected() {
		return selected;
	}



	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}



	public String getStatusDesc() {
		if(MSIConstants.PROM_ESTATUS_ENVIADO.equalsIgnoreCase(idEdoPromocion) ){
			return "Enviada";
		}else if(MSIConstants.PROM_ESTATUS_APLICADO.equalsIgnoreCase(idEdoPromocion) ){
			return "Aplicada";
		}else if(MSIConstants.PROM_ESTATUS_PENDIENTE.equalsIgnoreCase(idEdoPromocion) ){
			return "Pendiente";
		}
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public long getIdCompra() {
		return this.idCompra;
	}

	public void setIdCompra(long idCompra) {
		this.idCompra = idCompra;
	}

	public Integer getCodigoTransaccion() {
		return this.codigoTransaccion;
	}

	public void setCodigoTransaccion(Integer codigoTransaccion) {
		this.codigoTransaccion = codigoTransaccion;
	}

	public String getCuenta() {
		return this.cuenta;
	}

	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	public String getCuentaFacturadora() {
		return this.cuentaFacturadora;
	}

	public void setCuentaFacturadora(String cuentaFacturadora) {
		this.cuentaFacturadora = cuentaFacturadora;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	
	public String getDraftDebito() {
		return this.draftDebito;
	}

	public void setDraftDebito(String draftDebito) {
		this.draftDebito = draftDebito;
	}

	public Date getFechaAplicacionPromocion() {
		return this.fechaAplicacionPromocion;
	}

	public void setFechaAplicacionPromocion(Date fechaAplicacionPromocion) {
		this.fechaAplicacionPromocion = fechaAplicacionPromocion;
	}

	public Date getFechaAplicacionPromocion1() {
		return this.fechaAplicacionPromocion1;
	}

	public void setFechaAplicacionPromocion1(Date fechaAplicacionPromocion1) {
		this.fechaAplicacionPromocion1 = fechaAplicacionPromocion1;
	}

	public Date getFechaAplicacionPromocion2() {
		return this.fechaAplicacionPromocion2;
	}

	public void setFechaAplicacionPromocion2(Date fechaAplicacionPromocion2) {
		this.fechaAplicacionPromocion2 = fechaAplicacionPromocion2;
	}

	public Date getFechaCompra() {
		return this.fechaCompra;
	}

	public void setFechaCompra(Date fechaCompra) {
		this.fechaCompra = fechaCompra;
	}

	public Date getFechaEnvioDrafts() {
		return this.fechaEnvioDrafts;
	}

	public void setFechaEnvioDrafts(Date fechaEnvioDrafts) {
		this.fechaEnvioDrafts = fechaEnvioDrafts;
	}

	public Integer getFolio() {
		return this.folio;
	}

	public void setFolio(Integer folio2) {
		this.folio = folio2;
	}

	public String getIdEdoPromocion() {
		return this.idEdoPromocion;
	}

	public void setIdEdoPromocion(String idEdoPromocion) {
		this.idEdoPromocion = idEdoPromocion;
		if(MSIConstants.PROM_ESTATUS_ENVIADO.equalsIgnoreCase(idEdoPromocion) ){
			this.statusDesc="Enviada";
		}else if(MSIConstants.PROM_ESTATUS_APLICADO.equalsIgnoreCase(idEdoPromocion) ){
			this.statusDesc="Aplicada";
		}else if(MSIConstants.PROM_ESTATUS_PENDIENTE.equalsIgnoreCase(idEdoPromocion) ){
			this.statusDesc="Pendiente";
		}
	}

	public Double getMonto() {
		return this.monto;
	}

	public void setMonto(Double monto) {
		this.monto = monto;
	}

	public String getNumRefTran() {
		return this.numRefTran;
	}

	public void setNumRefTran(String numRefTran) {
		this.numRefTran = numRefTran;
	}

	public String getTipoTransaccion() {
		return this.tipoTransaccion;
	}

	public void setTipoTransaccion(String tipoTransaccion) {
		this.tipoTransaccion = tipoTransaccion;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Promocion getPromocion() {
		return this.promocion;
	}

	public void setPromocion(Promocion promocion) {
		this.promocion = promocion;
	}

	public Map<String, Promocion> getPromosCombo() {
		return promosCombo;
	}

	public void setPromosCombo(Map<String, Promocion> promosCombo) {
		this.promosCombo = promosCombo;
	}
	
	public boolean isEnPromocion() {
			return enPromocion;
	}

	public void setEnPromocion(boolean enPromocion) {
			this.enPromocion = enPromocion;
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((codigoTransaccion == null) ? 0 : codigoTransaccion
						.hashCode());
		result = prime * result + ((cuenta == null) ? 0 : cuenta.hashCode());
		result = prime * result
				+ ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result
				+ ((fechaCompra == null) ? 0 : fechaCompra.hashCode());
		result = prime * result + ((monto == null) ? 0 : monto.hashCode());
		result = prime * result
				+ ((numRefTran == null) ? 0 : numRefTran.hashCode());
		result = prime * result
				+ ((tipoTransaccion == null) ? 0 : tipoTransaccion.hashCode());
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
		Compra other = (Compra) obj;
		if (codigoTransaccion == null) {
			if (other.codigoTransaccion != null)
				return false;
		} else if (!codigoTransaccion.equals(other.codigoTransaccion))
			return false;
		if (cuenta == null) {
			if (other.cuenta != null)
				return false;
		} else if (!cuenta.equals(other.cuenta))
			return false;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (fechaCompra == null) {
			if (other.fechaCompra != null)
				return false;
		} else if (!fechaCompra.equals(other.fechaCompra))
			return false;
		if (monto == null) {
			if (other.monto != null)
				return false;
		} else if (!monto.equals(other.monto))
			return false;
		if (numRefTran == null) {
			if (other.numRefTran != null)
				return false;
		} else if (!numRefTran.equals(other.numRefTran))
			return false;
		if (tipoTransaccion == null) {
			if (other.tipoTransaccion != null)
				return false;
		} else if (!tipoTransaccion.equals(other.tipoTransaccion))
			return false;
		return true;
	}
	
}