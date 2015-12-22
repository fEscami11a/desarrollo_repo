package mx.com.invex.msi.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedProperty;

import mx.com.invex.msi.model.Promocion;
import mx.com.invex.msi.service.PromocionService;
import mx.com.invex.msi.util.MSIException;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
@Component
@Scope("request")
public class AltaPromoBean extends MessagesMBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(AltaPromoBean.class);
	
	private Promocion promo;
	private boolean saved;
	private boolean modifica;
	
	
	
	
	public boolean isModifica() {
		return modifica;
	}

	public void setModifica(boolean modifica) {
		this.modifica = modifica;
	}


	
	@Autowired
	PromocionService promoService;
	
	public AltaPromoBean() {
		promo = new Promocion();
	}
	
	public Promocion getPromo() {
		return promo;
	}

	public void setPromo(Promocion promo) {
		this.promo = promo;
	}
	
	public String modificarPromo(Promocion promocion){
		modifica=true;
		logger.info("modificarPromo "+promocion.getIdPromocion());
		this.promo=promocion;
//		ConsultaPromoBean consultaPromoBean =(ConsultaPromoBean) getContext().getApplication().getELResolver().getValue(
//                getContext().getELContext(), null, "consultaPromoBean");   
//		consultaPromoBean.getPromos().clear();
		return "EditPromocion";
	}
	
	public String borrarPromo(){
		
		logger.info("borrar promo "+promo.getIdPromocion());
		
		DetachedCriteria criteria = DetachedCriteria.forClass(Promocion.class);
		criteria.add(Restrictions.isEmpty("campanias")).add(Restrictions.eq("idPromocion", promo.getIdPromocion()));
		List<Promocion> promos =promoService.finbByCriteria(criteria);
		if(promos== null || promos.isEmpty()){
			
			sendErrorMessageToUser("La promocion esta asignada a una campaña y no es posible eliminarla");
			return null;
		}else{
			promoService.deletePromo(promo);
		}
		ConsultaPromoBean consultaPromoBean =(ConsultaPromoBean) getContext().getApplication().getELResolver().getValue(
                getContext().getELContext(), null, "consultaPromoBean");   
		consultaPromoBean.getPromos().clear();
		return "ConsultaPromo";
	}
	
	public String guardarPromo() throws MSIException{
		try {
			logger.info("guarda promo id "+ promo.getIdPromocion());
			Promocion promo2 =promoService.getPromoById(promo.getIdPromocion());
			if(promo2!= null){
				sendErrorMessageToUser("Ya existe una promocion con ese id");
				promo.setIdPromocion(0);
				
			}else{
				promo.setDbaName("PROMOCION S/ INT "+promo.getPlazoMeses()+"M");
				promoService.savePromo(promo);
				sendInfoMessageToUser("La promocion ha sido guardada");
				saved=true;
			}
				
			
		
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			
			throw new MSIException(e.getMessage());
		}
		return null;
	}
	
	public String modificarPromo() throws MSIException{
		try {
			logger.info("cambia promo id "+this.promo.getIdPromocion());
				promo.setDbaName("PROMOCION S/ INT "+promo.getPlazoMeses()+"M");
				promoService.saveOrUpdatePromo(promo);
				ConsultaPromoBean consultaPromoBean =(ConsultaPromoBean) getContext().getApplication().getELResolver().getValue(
		                getContext().getELContext(), null, "consultaPromoBean");   
				consultaPromoBean.getPromos().clear();
				sendInfoMessageToUser("La promocion ha sido guardada");
				saved=true;
			
		
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			
			throw new MSIException(e.getMessage());
		}
		return null;
	}

	public boolean isSaved() {
		return saved;
	}

	public void setSaved(boolean saved) {
		this.saved = saved;
	}
	
}
