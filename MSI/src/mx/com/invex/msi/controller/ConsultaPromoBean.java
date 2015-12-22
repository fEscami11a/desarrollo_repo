package mx.com.invex.msi.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import mx.com.invex.msi.model.Promocion;
import mx.com.invex.msi.service.PromocionService;
import mx.com.invex.msi.util.MSIException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
@Component
@Scope("session")
public class ConsultaPromoBean extends MessagesMBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ConsultaPromoBean.class);
	
	private List<Promocion> promos;
	private int promoId;
	private int meses;

	@Autowired
	PromocionService promoService;
	
	public ConsultaPromoBean() {
		
	}
	
	
	
	public List<Promocion> getPromos() {
		return promos;
	}



	public void setPromos(List<Promocion> promos) {
		this.promos = promos;
	}



	public int getPromoId() {
		return promoId;
	}



	public void setPromoId(int promoId) {
		this.promoId = promoId;
	}



	public int getMeses() {
		return meses;
	}



	public void setMeses(int meses) {
		this.meses = meses;
	}



	public String buscarPromo() throws MSIException{
		try {
			if(promoId>0){
				Promocion promo = promoService.getPromoById(promoId);
				if(promo!= null){
					promos = new ArrayList<Promocion>();
					promos.add(promo);
				}else{
					sendErrorMessageToUser("Promocion no encontrada");
				}
			}else if(meses >0){
				promos =promoService.getPromosByMes(meses+"");
				if(promos == null || promos.isEmpty()){
					sendErrorMessageToUser("Promociones no encontradas");
				}
			}else{
				sendErrorMessageToUser("Favor de ingresar valores en los campos para realizar la busqueda");
			}
		} catch (Exception e) {
logger.error(e.getMessage());
			
			throw new MSIException(e.getMessage());
		}
		return null;
	}


	
}
