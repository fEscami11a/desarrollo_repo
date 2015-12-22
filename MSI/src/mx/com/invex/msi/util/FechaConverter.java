package mx.com.invex.msi.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
/**
 * Coinvierte String fecha a espaniol
 * @author fernando.escamillaa
 *
 */
@FacesConverter("fechaConverter")
public class FechaConverter implements Converter {

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
			String date = (String)obj;
			if(date.length()==6){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
				Date fecha= sdf.parse((String)obj);
				SimpleDateFormat formateador = new SimpleDateFormat("MMMM 'de' yyyy", new Locale("ES"));
				return formateador.format(fecha);
			}else if(date.length()==8){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				Date fecha= sdf.parse((String)obj);
				SimpleDateFormat formateador = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", new Locale("ES"));
				return formateador.format(fecha);
			}else{
			return (String)obj;
			}
			
		 } 
	     catch (Exception exception) {
	     throw new ConverterException(exception);
	   }

	}

}
