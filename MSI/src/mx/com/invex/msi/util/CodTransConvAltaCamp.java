package mx.com.invex.msi.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import mx.com.invex.msi.controller.AltaCampaniaBean;
import mx.com.invex.msi.model.CodTrans;
import mx.com.invex.msi.model.Promocion;


@FacesConverter(forClass=Promocion.class, value="codTransConvAC")
public class CodTransConvAltaCamp implements Converter{

private AltaCampaniaBean ctrl;

    // Actions ------------------------------------------------------------------------------------
private static final Logger logger = Logger.getLogger(CodTransConvAltaCamp.class);

@Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
		  logger.info("valor converter "+value);
		  if("".equals(value)){
			  return null;
		  }
		  ctrl = (AltaCampaniaBean) context.getApplication().getELResolver().getValue(
	                context.getELContext(), null, "altaCampaniaBean"); 
		  
		  
		  for (SelectItem item :  ctrl.getCodTransList()) {
			  CodTrans codTrans = (CodTrans) item.getValue();
				if(String.valueOf(codTrans.getCodTrans()).equals(value)){
					return codTrans;
				}
		  }
		  return null;
     
		 
    }
	  @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value instanceof String){
        	return (String) value;
        }
		  CodTrans codTrans = (CodTrans) value;
        return codTrans.getCodTrans().toString();
    }


	}
