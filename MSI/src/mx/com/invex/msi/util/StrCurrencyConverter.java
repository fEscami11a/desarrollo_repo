package mx.com.invex.msi.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
@FacesConverter("strCurrency")
public class StrCurrencyConverter implements Converter {

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
			Double monto = new Double((String)obj);
			NumberFormat nf = new DecimalFormat("#,##0.00");
			return "$"+nf.format(monto);
		 } 
	     catch (Exception exception) {
	     throw new ConverterException(exception);
	   }

	}

}
