package mx.com.invex.msi.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import mx.com.invex.msi.util.MSIConstants;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;


/**
 * The persistent class for the COMPRA database table.
 * 
 */

@XmlRootElement
public class CompraWSDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	
	private long idCompra;

	
	private Integer codigoTransaccion;

	private String cuenta;
	
	private String cuentaFacturadora;

	private String descripcion;

	
	private String draftCredito;

	
	private String draftDebito;

    
	private Date fechaAplicacionPromocion;

    
	private Date fechaAplicacionPromocion1;

    
	private Date fechaAplicacionPromocion2;

   
	private String fechaCompra;

   
	private Date fechaEnvioDrafts;

	private Integer folio;

	
	private String idEdoPromocion;

	private Double monto;

	
	private String numRefTran;

	
	private String tipoTransaccion;

	private String username;

	
	private String promocion;
    
   
    private List<String> promosCombo= new ArrayList<String>();
    
   
    private boolean enPromocion;

    
    private boolean selected;
    
    
    private String statusDesc;
    
   
    private Double montoPromo;
    
    
    private Double montoOriginal;
    
    private String dateStmtBegin;
   private String datePost; 
    private String timePost;
    
	public Double getMontoOriginal() {
		return montoOriginal;
	}

	public String getDateStmtBegin() {
		return dateStmtBegin;
	}

	public void setDateStmtBegin(String dateStmtBegin) {
		this.dateStmtBegin = dateStmtBegin;
	}

	public String getDatePost() {
		return datePost;
	}

	public void setDatePost(String datePost) {
		this.datePost = datePost;
	}

	public String getTimePost() {
		return timePost;
	}

	public void setTimePost(String timePost) {
		this.timePost = timePost;
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

	public CompraWSDTO() {
    }

	
	
	public boolean isSelected() {
		return selected;
	}



	public List<String> getPromosCombo() {
		return promosCombo;
	}

	public void setPromosCombo(List<String> promosCombo) {
		this.promosCombo = promosCombo;
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

	public String getDraftCredito() {
		return this.draftCredito;
	}

	public void setDraftCredito(String draftCredito) {
		this.draftCredito = draftCredito;
	}

	public String getDraftDebito() {
		return this.draftDebito;
	}

	public void setDraftDebito(String draftDebito) {
		this.draftDebito = draftDebito;
	}

	@JsonSerialize(using=DateSerializer.class)
	public Date getFechaAplicacionPromocion() {
		return this.fechaAplicacionPromocion;
	}

	public void setFechaAplicacionPromocion(Date fechaAplicacionPromocion) {
		this.fechaAplicacionPromocion = fechaAplicacionPromocion;
	}

	
	public String getFechaCompra() {
		return this.fechaCompra;
	}

	public void setFechaCompra(String fechaCompra) {
		this.fechaCompra = fechaCompra;
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

	public String getPromocion() {
		return this.promocion;
	}

	public void setPromocion(String promocion) {
		this.promocion = promocion;
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
		CompraWSDTO other = (CompraWSDTO) obj;
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