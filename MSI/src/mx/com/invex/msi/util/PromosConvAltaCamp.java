package mx.com.invex.msi.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import mx.com.invex.msi.controller.AltaCampaniaBean;
import mx.com.invex.msi.model.Promocion;


@FacesConverter(forClass=Promocion.class, value="promosConvAC")
public class PromosConvAltaCamp implements Converter{

private AltaCampaniaBean ctrl;

    // Actions ------------------------------------------------------------------------------------
private static final Logger logger = Logger.getLogger(PromosConvAltaCamp.class);

@Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
		  logger.info("valor converter "+value);
		  if("".equals(value)){
			  return null;
		  }
		  ctrl = (AltaCampaniaBean) context.getApplication().getELResolver().getValue(
	                context.getELContext(), null, "altaCampaniaBean");        
		  
          for (SelectItem item :  ctrl.getPromos()) {
			Promocion promo = (Promocion) item.getValue();
			if(String.valueOf(promo.getIdPromocion()).equals(value)){
				return promo;
			}
		}
//	        List<Compra> compras=  ctrl.;
//	        for (Compra compra : compras) {
//				Promocion promo  =compra.getPromosCombo().get(value);
//				if(promo != null){
//					return promo;
//				}
//			}
//
       return null;
		 
    }
	  @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value instanceof String){
        	return (String) value;
        }
		  Promocion promo = (Promocion) value;
        return promo.getIdPromocion()+"";
    }


	}
