package mx.com.invex.msi.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
/**
 * Quita el asterisco al nombre
 * @author fernando.escamillaa
 *
 */
@FacesConverter("nameConverter")
public class NameConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, 
			   UIComponent uiComponent, 
			   String param) {
		 try {
			
			    return new Double(param);
			   }
			    catch (Exception exception) {
			   throw new ConverterException(exception);
			   }

	}

	@Override
	public String getAsString(FacesContext facesContext, 
			   UIComponent uiComponent, 
			   Object obj) {
		try{
			String nombre = (String)obj;
		
			String res =nombre.replace('*',' ');
			return res;
		 } 
	     catch (Exception exception) {
	     throw new ConverterException(exception);
	   }

	}

}
