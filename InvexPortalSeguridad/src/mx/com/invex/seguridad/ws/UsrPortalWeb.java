package mx.com.invex.seguridad.ws;

import java.util.List;

import javax.annotation.Resource;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.servlet.ServletContext;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import mx.com.invex.bmovil.exception.WSSeguridadException;
import mx.com.invex.seguridad.entidades.Usuario;
import mx.com.invex.seguridad.sevice.UsuarioService;
import mx.com.invex.seguridad.utils.CryptoAES;
import mx.com.invex.seguridad.utils.SegConstants;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;
@WebService
@Component
public class UsrPortalWeb {

	@Resource    // Step 1
	private WebServiceContext wsContext;   
	
	String getCuentaByUsername(@WebParam(name="username") String username)throws WSSeguridadException{
		ServletContext servletContext= (ServletContext) wsContext.getMessageContext().get(MessageContext.SERVLET_CONTEXT);
		ApplicationContext contexta = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		UsuarioService usrDao = (UsuarioService) contexta.getBean("usuarioServiceImpl");
		String usrnamecrypt = null;
		try {
			usrnamecrypt = ClientHSM.encript(CryptoAES.encrypt(username.trim()),SegConstants.HSM_KEY_USR);
		} catch (Exception e) {
			e.printStackTrace();
			throw new WSSeguridadException("Error al encriptar username "
					+ e.getMessage());
		}
		DetachedCriteria usrCrit = DetachedCriteria.forClass(Usuario.class);
		usrCrit.add(Restrictions.eq("nombreusuario", usrnamecrypt));
		usrCrit.add(Restrictions.eq("estatus",true));
		List<Usuario> users =usrDao.findByCriteria(usrCrit);
		
		Usuario usr = users.isEmpty()?null:users.get(0);
		try {
			String account = CryptoAES.decrypt2(usr.getData4u1());
			return account;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	
}
