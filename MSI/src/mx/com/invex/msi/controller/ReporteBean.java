package mx.com.invex.msi.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.constraints.Pattern;

import mx.com.invex.msi.model.Compra;
import mx.com.invex.msi.model.RepConcentradoDTO;
import mx.com.invex.msi.service.CompraService;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
@Component
@Scope("session")
public class ReporteBean extends MessagesMBean  implements Serializable{
	
	public ReporteBean() {
		this.fechaFin= new Date();
		
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ReporteBean.class);
	
	private Date fechaInicio, fechaFin;
	
	private String status;

	
	@Autowired
	CompraService compraService;
	
	private List<RepConcentradoDTO> repRegs;
	
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	

	public List<RepConcentradoDTO> getRepRegs() {
		return repRegs;
	}

	public void setRepRegs(List<RepConcentradoDTO> repRegs) {
		this.repRegs = repRegs;
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
	
	

	public String consultarCompras(){
		DetachedCriteria criteria = DetachedCriteria.forClass(Compra.class);
		criteria.setProjection(Projections.projectionList()
				.add(Projections.rowCount(),"countCompras")
				.add( Projections.property("origen"), "origen" )
				.add( Projections.property("idEdoPromocion"), "status" )
				.add( Projections.groupProperty("origen") )
				.add( Projections.groupProperty("idEdoPromocion") )
				);
		criteria.add(Restrictions.or(Restrictions.like("origen", "PU%"), Restrictions.like("origen", "WSPortal%")));
		criteria.add(Restrictions.or(Restrictions.eq("idEdoPromocion", "A"), Restrictions.eq("idEdoPromocion", "E")));
		if(!"T".equals(status)){
			criteria.add(Restrictions.eq("idEdoPromocion", status));
		}
			if(fechaInicio == null || fechaFin== null){
				sendErrorMessageToUser("Fecha de Inicio y fecha Final son requeridas");
				return null;
				
			}else{
				Date maxDate = new Date(fechaFin.getTime()+TimeUnit.DAYS.toMillis(1));
				criteria.add(Restrictions.between("fechaAplicacionPromocion", getFechaInicio(), maxDate));
			}
		
	
		List<Object[]>reporte =compraService.getReporte(criteria);
		 repRegs = new ArrayList<RepConcentradoDTO>();
		for (Object[] objects : reporte) {
			RepConcentradoDTO repReg = new RepConcentradoDTO();
			repReg.setNumCompras(((Long) objects[0]).intValue());
			repReg.setOrigen("PU".equals(objects[1].toString().trim())?"Pantalla Unica":"Portal");
			repReg.setStatus("A".equals(objects[2].toString().trim())?"Aplicadas":"Enviadas");
			repRegs.add(repReg);
			logger.info("ob0 "+objects[0] +" ob1 "+ objects[1] +" ob2 "+objects[2]);
		}
		
		if(repRegs.isEmpty()){
			sendErrorMessageToUser("No se encontraron resultados");
			return null;
		}
		
		return "RepComprasList";  
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
