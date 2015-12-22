package mx.com.invex.msi.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Pattern;

import mx.com.invex.msi.model.Archivo;
import mx.com.invex.msi.model.ArchivoDetalle;
import mx.com.invex.msi.model.Campania;
import mx.com.invex.msi.service.ArchivoService;
import mx.com.invex.msi.service.BitacoraService;
import mx.com.invex.msi.service.CampaniaService;
import mx.com.invex.msi.util.MSIException;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
@Component
@Scope("session")
public class ConsultaArchivoBean extends MessagesMBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ConsultaArchivoBean.class);
	
	@Autowired
	ArchivoService archivoService;
	private Date fechaIn,fechaFin;
	@Pattern(regexp="(\\d{16})?")
	private String cuenta;
	private List<Archivo> archivos;
	private List<ArchivoDetalle>  detalles;
	private Archivo archivo;
	@Autowired
	BitacoraService bitacoraService;
	@Autowired
	CampaniaService campaniaService;
	private String nombreCamp;
	
	public String getNombreCamp() {
		return nombreCamp;
	}
	public void setNombreCamp(String nombreCamp) {
		this.nombreCamp = nombreCamp;
	}
	public Archivo getArchivo() {
		return archivo;
	}
	public void setArchivo(Archivo archivo) {
		this.archivo = archivo;
	}
	public List<ArchivoDetalle> getDetalles() {
		return detalles;
	}
	public void setDetalles(List<ArchivoDetalle> detalles) {
		this.detalles = detalles;
	}
	public Date getFechaIn() {
		return fechaIn;
	}
	public void setFechaIn(Date fechaIn) {
		this.fechaIn = fechaIn;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	
	public List<Archivo> getArchivos() {
		return archivos;
	}
	public void setArchivos(List<Archivo> archivos) {
		this.archivos = archivos;
	}
	
	
	
	public String getCuenta() {
		return cuenta;
	}
	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}
	public String buscarArchivo() throws MSIException{
		try {
			
			DetachedCriteria criteria = DetachedCriteria.forClass(Archivo.class);
			if(cuenta != null && !cuenta.trim().isEmpty()){
				criteria.createAlias("archivoDetalles","ad", JoinType.LEFT_OUTER_JOIN).add(Restrictions.eq("ad.cuenta",cuenta));
			}
			if(fechaIn != null){
				criteria.add(Restrictions.ge("fechaCarga", fechaIn));
			}
			if(fechaFin!= null){
				criteria.add(Restrictions.le("fechaCarga", fechaFin));
			}
			//criteria.add(Restrictions.and(Restrictions.ge("fechaCarga", fechaIn), Restrictions.le("fechaCarga", fechaFin)));
			archivos=archivoService.findByCriteria(criteria);
			if(!archivos.isEmpty()){
				for (Archivo archivo : archivos) {
					logger.info("idArchivo "+archivo.getIdArchivo());
				}
				}else{
					sendErrorMessageToUser("Archivo no encontrado");
				}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new MSIException(e.getMessage());
		}
			
		return null;
	}
	
	public String detalleArchivo(Archivo ar) throws MSIException{
		try {
			archivo =ar;
			
			DetachedCriteria criteria = DetachedCriteria.forClass(ArchivoDetalle.class);  
			criteria.add(Restrictions.eq("archivo.idArchivo",ar.getIdArchivo()));
			 detalles =archivoService.findDetallesByCriteria(criteria);
			 
			 if(detalles == null || detalles.isEmpty()){
				 sendErrorMessageToUser("El archivo no tiene registros cargados");
				 return null;
			 }else{
				 ArchivoDetalle detalle=detalles.get(0);
				 Campania camp=campaniaService.getCampaniaById(new Long(detalle.getCodigoCampania()));
				 nombreCamp = camp.getNombre();
			 }
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new MSIException(e.getMessage());
		}
		
		return "ArchivoDetalleList";
	}
	
	public String borrarArchivo() throws MSIException{
		try {
			archivoService.deleteArchivo(archivo);
			this.detalles.clear();
			archivos.clear();
			sendInfoMessageToUser("El archivo ha sido borrado");
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new MSIException(e.getMessage());
		}
		return "ConsultaArchivo";
	}
	
}
