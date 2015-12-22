package mx.com.invex.msi.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.log4j.Logger;
import org.hibernate.validator.constraints.CreditCardNumber;

@ManagedBean
@ViewScoped
public class CuentaFormBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CuentaFormBean.class);
	
	@PostConstruct
	public void init(){
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("infoCuentaBean");
	}
	
	@NotNull(message = "El numero de cuenta es requerido")
	@Size(min = 16, max = 16, message = "La cuenta debe tener 16 digitos de longitud")
	@Digits(fraction = 0, integer = 16)
	@CreditCardNumber
	private String cuenta;

	public String getCuenta() {
		return cuenta;
	}

	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}
	
	public String movimeintos() { 
		logger.info("Estoy en movimeintos");
		return "ClienteInfoMovs";
		}
	
	

}
