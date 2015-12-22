package mx.com.invex.seguridad.actionbean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import mx.com.invex.seguridad.entidades.Intentosesion;
import mx.com.invex.seguridad.entidades.Usuario;
import mx.com.invex.seguridad.sevice.IntentoSesionService;
import mx.com.invex.seguridad.sevice.UsuarioService;
import mx.com.invex.seguridad.utils.CryptoAES;
import mx.com.invex.seguridad.utils.InvexPasswordEncoder;
import mx.com.invex.seguridad.utils.SegConstants;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class EditarPassUsuarioBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(EditarPassUsuarioBean.class);

	private String password,confPassword;
	private Usuario usrSeleccionado;
	private String webUsr;
	private boolean usrBloqueado=false;
	
	@Autowired
	UsuarioService usuarioService;
	@Autowired
	IntentoSesionService intentoSesionService;
	public boolean isUsrBloqueado() {
		return usrBloqueado;
	}
	public void setUsrBloqueado(boolean usrBloqueado) {
		this.usrBloqueado = usrBloqueado;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfPassword() {
		return confPassword;
	}
	public void setConfPassword(String confPassword) {
		this.confPassword = confPassword;
	}
	
	public String getWebUsr() {
		return webUsr;
	}


	public void setWebUsr(String webUsr) {
		this.webUsr = webUsr;
	}


	public Usuario getUsrSeleccionado() {
		return usrSeleccionado;
	}


	public void setUsrSeleccionado(Usuario usrSeleccionado) {
		this.usrSeleccionado = usrSeleccionado;
	}


	public String usuarioSeleccionado(BigDecimal id){
		logger.info("uauior "+id);
		Calendar tiempo = Calendar.getInstance();
		tiempo.add(Calendar.MINUTE, -SegConstants.MINS_PSW_BOLQUEADO);
		
		//UsuarioDAO usrDao = (UsuarioDAO) context.getBean("userDao");
		DetachedCriteria usrCrit = DetachedCriteria.forClass(Usuario.class);
		usrCrit.add(Restrictions.eq("identificador",id));
		List<Usuario> lusr =usuarioService.findByCriteria(usrCrit);
		usrSeleccionado =lusr.isEmpty()?null:lusr.get(0);
		
		
		DetachedCriteria intsesCrit = DetachedCriteria.forClass(Intentosesion.class);
		intsesCrit.add(Restrictions.eq("identificadorusuario", id));
		intsesCrit.add(Restrictions.eq("esbloqueada", true));
		intsesCrit.add(Restrictions.eq("tiemporegistro", tiempo.getTime()));
		List<Intentosesion> lintses =intentoSesionService.findByCriteria(intsesCrit);
		Intentosesion intSes=lintses.isEmpty()?null:lintses.get(0);
		if(intSes!= null){
			usrBloqueado = true;
			//TODO aqgregar mesnaje y pintal boton para desbloquear
			addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "El usuario se encuentra bloqueado actualmente!!", null));
		}
		return "editarPswUsuario";
	}
 

	private void addMessage(FacesMessage message){

        FacesContext.getCurrentInstance().addMessage(null, message);

    }
	
	public String guardarPassword(){
	if(!password.equals(confPassword)){
		password="";
		confPassword="";
		addMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR, "La contraseña y la confirmacion son diferentes!!", null));
		return null;
	}else{
		InvexPasswordEncoder encoder = new InvexPasswordEncoder();
    	String result = encoder.encode(password);
    	usrSeleccionado.setContrasenia(result);
    	
	
    	usuarioService.merge(usrSeleccionado);
    	
    	addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "La contraseña ha sido cambiada exitosamente!!", null));
    	return "consultaUsuario";
	}
	}
	
	
	public String borrarUsuario(){
		
	
		usuarioService.delete(usrSeleccionado);
	    	
//	    	String usr =null;
//	    	try {
//				usr = CryptoAES.decrypt(usrSeleccionado.getNombreusuario());
//			} catch (Exception e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//	    	logger.info("usuario "+usr);
//	    	
//	    	try {
//	    		if(usr!=  null){
//	    			String endPoint=MessageFormat.format(
//	    	    			SegConstants.URL_SERVICIO_REMOVE_USUARIO_WEB,
//	    	    			URLEncoder.encode(usr,"UTF-8"));
//				URL httpUrl = new URL(endPoint);
//				InputStream istream = httpUrl.openStream();
//				InputStreamReader ir = new InputStreamReader(istream, "ISO-8859-1");
//				BufferedReader reader = new BufferedReader(ir);
//				StringBuffer buf = new StringBuffer();
//				String resp = reader.readLine();
//	    		
//				logger.info("resp borrar usaurio ods: "+resp);
//	    		}
//			} catch (MalformedURLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (UnsupportedEncodingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
	    	addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "El usuario ha sido borrado!!", null));
	    	return "consultaUsuario";
		
		}
	
	public String desbloquearUsuario(){
		
		
		Calendar tiempo = Calendar.getInstance();
		tiempo.add(Calendar.MINUTE, -SegConstants.MINS_PSW_BOLQUEADO);
		DetachedCriteria intsesCrit = DetachedCriteria.forClass(Intentosesion.class);
		intsesCrit.add(Restrictions.eq("identificadorusuario", usrSeleccionado.getIdentificador()));
		intsesCrit.add(Restrictions.eq("esbloqueada", true));
		intsesCrit.add(Restrictions.eq("tiemporegistro", tiempo.getTime()));
		List<Intentosesion> lintses=intentoSesionService.findByCriteria(intsesCrit);
		
		Intentosesion intSes=lintses.isEmpty()?null:lintses.get(0);
		if(intSes!= null){
			intSes.setEsbloqueada(false);
			intentoSesionService.merge(intSes);
		}
		logger.info("usuario desbloqueado ");
    	addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "El usuario ha sido desbloqueado!!", null));
    	return "consultaUsuario";
	}
	
	
}
