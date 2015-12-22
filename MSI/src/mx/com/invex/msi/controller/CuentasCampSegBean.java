package mx.com.invex.msi.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;

import mx.com.invex.msi.model.Archivo;
import mx.com.invex.msi.model.ArchivoDetalle;
import mx.com.invex.msi.model.Campania;
import mx.com.invex.msi.service.ArchivoService;
import mx.com.invex.msi.service.CampaniaService;
import mx.com.invex.msi.util.MSIException;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
@Component
@Scope("session")
public class CuentasCampSegBean extends MessagesMBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CuentasCampSegBean.class);
	@Autowired
	CampaniaService campaniaService;
	@Autowired
	ArchivoService archivoService;
	private Integer idCamp;
	private ArrayList<SelectItem> camps = new ArrayList<SelectItem>();
	private boolean guardado;
	
	@PostConstruct
	public void init(){
		if(camps.isEmpty()){
			List<Object[]> list = campaniaService.getCampComboByTipo("segmentada");
			for (Object[] objects : list) {
				camps.add(new SelectItem(objects[0],objects[1].toString()));
				
			}
		}
	}
	
	
	public boolean isGuardado() {
		return guardado;
	}




	public void setGuardado(boolean guardado) {
		this.guardado = guardado;
	}




	public Integer getIdCamp() {
		return idCamp;
	}




	public void setIdCamp(Integer idCamp) {
		this.idCamp = idCamp;
	}




	public ArrayList<SelectItem> getCamps() {
		
		return camps;
	}




	public void setCamps(ArrayList<SelectItem> camps) {
		this.camps = camps;
	}


	public String guardarArchivo(List<ArchivoDetalle> detalles,String nombreArchivo) throws MSIException{
		logger.info("nombre archivo "+nombreArchivo);
		try {
			if(nombreArchivo ==null){
				sendErrorMessageToUser("Favor de cargar el archivo txt");
				return null;
			}
			int idex=nombreArchivo.lastIndexOf("\\");
			logger.info("index "+idex);
			if(detalles.isEmpty()){
				sendErrorMessageToUser("No se encontraron registros validos para cargar en el archivo");
				return null;
			}
			
			if(idCamp == null){
				sendErrorMessageToUser("Favor de seleccionar la campaña segmentada");
				return null;
			}
			Campania camp =campaniaService.getCampaniaById(idCamp.longValue());
			Archivo archivo = new Archivo();
			DetachedCriteria criteria = DetachedCriteria.forClass(Archivo.class);
			criteria.add(Restrictions.eq("nombre", nombreArchivo));
			List<Archivo> larchivo =archivoService.findByCriteria(criteria);
			if(!larchivo.isEmpty()){
				sendErrorMessageToUser("Ya existe un archivo cargado con ese mismo nombre");
				return null;
			}
			
			//archivo.setArchivoDetalles(arDet);
			archivo.setNombre(nombreArchivo.substring(idex+1));
			archivo.setFechaCarga(new Date());
			
			//archivoService.saveArchivo(archivo);
			for (ArchivoDetalle archivoDetalle : detalles) {
				archivoDetalle.setCampania(camp);
				archivoDetalle.setArchivo(archivo);
				archivoDetalle.setEstado("A");
				archivoDetalle.setCodigoCampania(camp.getIdCampania().toString());
			}
			Set<ArchivoDetalle> arDet = new HashSet<ArchivoDetalle>();
			arDet.addAll(detalles);
			archivo.setArchivoDetalles(arDet);
			//archivoService.mergeArchivo(archivo);
			archivoService.saveArchivo(archivo);
			sendInfoMessageToUser("El archivo "+nombreArchivo.substring(idex+1) + " ha sido cargado con "+detalles.size() + " registros");
			this.guardado=true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new MSIException(e.getMessage());
		}
		return null;
	}
	
	
}
