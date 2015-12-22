package mx.com.invex.msi.util;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import mx.com.invex.msi.controller.InfoCuentaBean;
import mx.com.invex.msi.model.Compra;
import mx.com.invex.msi.model.Promocion;


@FacesConverter(forClass=Promocion.class, value="promosConverter")
public class PromosConverter implements Converter{

private InfoCuentaBean ctrl;
    // Actions ------------------------------------------------------------------------------------
	  @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
		  
		  if("----".equals(value)){
			  return null;
		  }
		  ctrl = (InfoCuentaBean) context.getApplication().getELResolver().getValue(
	                context.getELContext(), null, "infoCuentaBean");        

	        List<Compra> compras=  ctrl.getCompras();
	        for (Compra compra : compras) {
				Promocion promo  =compra.getPromosCombo().get(value);
				if(promo != null){
					return promo;
				}
			}

        return null;
    }
	  @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value instanceof String){
        	return (String) value;
        }
		  Promocion promo = (Promocion) value;
        return promo.getPlazoMeses()+" "+promo.getDescripcion();
    }


	}
