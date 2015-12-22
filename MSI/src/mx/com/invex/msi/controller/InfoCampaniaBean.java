package mx.com.invex.msi.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import mx.com.invex.msi.model.Campania;
import mx.com.invex.msi.model.Producto;
import mx.com.invex.msi.model.ProductoTs2;
import mx.com.invex.msi.model.Promocion;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
@Component
@Scope("request")
public class InfoCampaniaBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger= Logger.getLogger(InfoCampaniaBean.class);
	public InfoCampaniaBean() {
		logger.info("InfoCampnia beanm");
		campania = new Campania();
		}
	
	private Campania campania;
	
	private List<Promocion> promosCamp;
	private List<Producto> prods;
	
	
	public List<Promocion> getPromosCamp() {
		promosCamp = new ArrayList<Promocion>();
		
		if(!campania.getPromociones().isEmpty()){
			promosCamp.addAll(campania.getPromociones());
		}
		return promosCamp;
	}

	public void setPromosCamp(List<Promocion> promosCamp) {
		this.promosCamp = promosCamp;
	}

	public String obtenerCampInfo(){
		promosCamp = new ArrayList<Promocion>();
			
			if(!campania.getPromociones().isEmpty()){
				promosCamp.addAll(campania.getPromociones());
			}
	        return "infoCampania";
	}
	
	public Campania getCampania() {
		return campania;
	}

	public void setCampania(Campania campania) {
		this.campania = campania;
	}

	public List<ProductoTs2> getProdsTs2(){
		List<ProductoTs2> prodsTs2= new ArrayList<ProductoTs2>();
		if(campania!= null){
			if(campania.getProductosts2()!= null && !campania.getProductosts2().isEmpty()){
				prodsTs2.addAll(campania.getProductosts2());
			}
			}
			return prodsTs2;
	}

	public List<Producto> getProds() {
		prods= new ArrayList<Producto>();
		if(campania!= null){
		if(campania.getProductos()!= null && !campania.getProductos().isEmpty()){
			prods.addAll(campania.getProductos());
		}
		}
		return prods;
	}
	
	
	
}
