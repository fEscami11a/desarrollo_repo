package mx.com.invex.msi.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.constraints.Pattern;

import mx.com.invex.msi.model.Compra;
import mx.com.invex.msi.service.CompraService;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
@Component
@Scope("session")
public class ReporteBean2 extends MessagesMBean  implements Serializable{
	
	public ReporteBean2() {
		this.fechaFin= new Date();
		
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ReporteBean2.class);
	
	private Date fechaInicio, fechaFin;
	@Pattern(regexp="(\\d{16})?")
	private String cuenta;
	
	private String status;
	@Pattern(regexp="(\\d{1,10})?")
	private String folio;
	
	@Autowired
	CompraService compraService;
	
	private List<Compra> compras;
	
	

	public String getCuenta() {
		return cuenta;
	}

	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Compra> getCompras() {
		return compras;
	}

	public void setCompras(List<Compra> compras) {
		this.compras = compras;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	
	
	
	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String consultarCompras(){
		if("".equals(status) && cuenta.isEmpty() && folio.isEmpty()){
			sendErrorMessageToUser("Favor de ingresar cuenta, folio o estatus de promocion");
			return null;
		}
		DetachedCriteria criteria = DetachedCriteria.forClass(Compra.class);
		
		if(!"".equals(status)){
			criteria.add(Restrictions.eq("idEdoPromocion", status));
			if(fechaInicio == null || fechaFin== null){
				sendErrorMessageToUser("Fecha de Inicio y fecha Final son requeridas");
				return null;
				
			}else{
				Date maxDate = new Date(fechaFin.getTime()+TimeUnit.DAYS.toMillis(1));
				criteria.add(Restrictions.between("fechaAplicacionPromocion", getFechaInicio(), maxDate));
			}
		}
		if(cuenta != null && !cuenta.isEmpty()){
			criteria.add(Restrictions.eq("cuenta", cuenta));
			if(fechaInicio == null || fechaFin== null){
				sendErrorMessageToUser("Fecha de Inicio y fecha Final son requeridas");
				return null;
				
			}else{
				Date maxDate = new Date(fechaFin.getTime()+TimeUnit.DAYS.toMillis(1));
				criteria.add(Restrictions.between("fechaAplicacionPromocion", getFechaInicio(), maxDate));
			}
		}
		if(folio != null && !folio.isEmpty()){
			criteria.add(Restrictions.eq("folio", new Integer(folio)));
		}
		criteria.addOrder(Order.desc("fechaCompra"));
		compras =compraService.findByCriteria(criteria);
		
		if(compras == null || compras.isEmpty()){
			sendErrorMessageToUser("No se encontraron resultados");
			return null;
		}
		logger.info("num compras compras "+compras.size());
		for (Compra compra : compras) {
			compra.setCuenta("***********"+compra.getCuenta().substring(compra.getCuenta().length()-4));
		}
		return "RepComprasList2";  
	}
	
//	public static void main(String[] args) {
//		 java.util.regex.Pattern pat = java.util.regex.Pattern.compile("(\\d{1,10})?");
//	     Matcher mat = pat.matcher("12346579086");
//	     if (mat.matches()) {
//	         System.out.println("SI");
//	     } else {
//	         System.out.println("NO");
//	     }
//	}

	  
}
