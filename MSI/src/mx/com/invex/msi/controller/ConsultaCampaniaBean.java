package mx.com.invex.msi.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import mx.com.invex.msi.model.Campania;
import mx.com.invex.msi.service.BitacoraService;
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
public class ConsultaCampaniaBean extends MessagesMBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ConsultaCampaniaBean.class);
	private Integer idCamp;
	private Date fechaIn;
	private Date fechaFin;
	private String tipoCamp;
	@Autowired
	CampaniaService campaniaService;
	@Autowired
	BitacoraService bitacoraService;
	private ArrayList<SelectItem> camps = new ArrayList<SelectItem>();
	private List<Campania> listCamp=new ArrayList<Campania>();
	
	public String init(){
		idCamp= null;
		fechaIn = null;
		fechaFin= null;
		tipoCamp = null;
		camps.clear();
		listCamp.clear();
		return "/admin/ConsultaCampaniaForm";
	}
	
	public List<Campania> getListCamp() {
		return listCamp;
	}



	public void setListCamp(List<Campania> listCamp) {
		this.listCamp = listCamp;
	}



	public ArrayList<SelectItem> getCamps() {
		return camps;
	}



	public void setCamps(ArrayList<SelectItem> camps) {
		this.camps = camps;
	}



	public Integer getIdCamp() {
		return idCamp;
	}



	public void setIdCamp(Integer idCamp) {
		this.idCamp = idCamp;
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



	public String getTipoCamp() {
		return tipoCamp;
	}



	public void setTipoCamp(String tipoCamp) {
		this.tipoCamp = tipoCamp;
	}
	
	public void tipoCampChanged(ValueChangeEvent event) {
		try {
			String comboTipoVal =(String)event.getNewValue();
			
			camps.clear();
			if (comboTipoVal != null && !comboTipoVal.isEmpty()) {
				List<Object[]> list = campaniaService.getCampComboByTipo(comboTipoVal);
				for (Object[] objects : list) {
					camps.add(new SelectItem(objects[0],objects[1].toString()));
					
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			
		}
	}

	public String buscarCampanias() throws MSIException{
try {
	
	DetachedCriteria criteria = DetachedCriteria.forClass(Campania.class);
	listCamp.clear();
			if(idCamp.intValue()!=0){
				
				Campania camp =campaniaService.getCampaniaById(idCamp.longValue());
				logger.info("camp obtenida "+camp.getNombre());
				listCamp.add(camp);
			}else{
				if(tipoCamp.equals("0")){
			
					criteria.add(Restrictions.in("tipoCampania", new String[]{"masiva","segmentada"})); 
				}else{
					if("permanente".equals(tipoCamp)){
						
						criteria.add(
								  Restrictions.not(
								    Restrictions.in("tipoCampania", new String[] {"masiva","segmentada","comercio"})
								  )
								);
					}else{
						criteria.add(Restrictions.eq("tipoCampania", tipoCamp));
					}
				}
				 
				if(fechaIn!= null){
					criteria.add(Restrictions.ge("fechaInicial", fechaIn));
				}
				if(fechaFin!=null){
					criteria.add(Restrictions.le("fechaFinal", fechaFin));
				}
			
				listCamp =campaniaService.findByCriteria(criteria);
				if(!listCamp.isEmpty()){
					Set<Campania> setCamp = new HashSet<Campania>();
					setCamp.addAll(listCamp);
						listCamp= new ArrayList<Campania>(setCamp);
					}
			}
			if(listCamp.isEmpty()){
				sendErrorMessageToUser("No se encontraron resultados");
			}
} catch (Exception e) {
	logger.error(e.getMessage());
	e.printStackTrace();
	throw new MSIException(e.getMessage());
}
		
		return null;
	}
}
