package mx.com.invex.seguridad.actionbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import mx.com.invex.seguridad.dto.UsuarioDTO;
import mx.com.invex.seguridad.entidades.Usuario;
import mx.com.invex.seguridad.sevice.UsuarioService;
import mx.com.invex.seguridad.utils.CryptoAES;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class ConsultaUsuarioBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(ConsultaUsuarioBean.class);
	
	private String webuser;
	private String email;
	private String rfc;
	private String nombre,apaterno,amaterno;
	private String numTarjeta;
	private boolean primerPag,ultimaPag;
	@Autowired
	UsuarioService usuarioService;
	
	public boolean isPrimerPag() {
		return primerPag;
	}

	public void setPrimerPag(boolean primerPag) {
		this.primerPag = primerPag;
	}

	public boolean isUltimaPag() {
		return ultimaPag;
	}

	public void setUltimaPag(boolean ultimaPag) {
		this.ultimaPag = ultimaPag;
	}
	
	private List<UsuarioDTO> usersDTO;
	
	public List<UsuarioDTO> getUsersDTO() {
		return usersDTO;
	}
	public void setUsersDTO(List<UsuarioDTO> usersDTO) {
		this.usersDTO = usersDTO;
	}
	

	public String getAmaterno() {
		return amaterno;
	}
	public void setAmaterno(String amaterno) {
		this.amaterno = amaterno;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApaterno() {
		return apaterno;
	}
	public void setApaterno(String apaterno) {
		this.apaterno = apaterno;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRfc() {
		return rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	
	public String getNumTarjeta() {
		return numTarjeta;
	}
	public void setNumTarjeta(String numTarjeta) {
		this.numTarjeta = numTarjeta;
	}
	public String getWebuser() {
		return webuser;
	}
	public void setWebuser(String name) {
		this.webuser = name;
	}

	public String consultarUsuario(){
		logger.info("Estoy en consultarUsuario ");
		first=0;
		 usersDTO = new ArrayList<UsuarioDTO>();
//			ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
//			UsuarioDAO usrDao = (UsuarioDAO) context.getBean("userDao");
		if (webuser != null && !webuser.trim().isEmpty()) {
			try {
				String usrnamecrypt = CryptoAES.encrypt(webuser.trim());
				DetachedCriteria usrCrit = DetachedCriteria.forClass(Usuario.class);
				usrCrit.add(Restrictions.eq("nombreusuario", usrnamecrypt));
				usrCrit.add(Restrictions.eq("estatus",true));
				List<Usuario> users =usuarioService.findByCriteria(usrCrit);
				
				
				
				
				if (users!= null && !users.isEmpty()) {
					for (Usuario user : users) {
						UsuarioDTO usrDTO = new UsuarioDTO();
						usrDTO.setWebuser(webuser);
						usrDTO.setEmail(user.getCorreoelectronico());
						usrDTO.setNombre(user.getNombre() + " "
								+ user.getApellidopaterno() + " "
								+ user.getApellidomaterno());
						usrDTO.setRfc(user.getRfc());
						usrDTO.setIdentificador(user.getIdentificador());
						usersDTO.add(usrDTO);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Error al encriptar: "+e.getMessage());
				addMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Ha ocurrido un error en la aplicación!!", null));
				return null;
			}
			
		}else if (rfc != null && !rfc.trim().isEmpty()) {
			
			
			DetachedCriteria usrCrit = DetachedCriteria.forClass(Usuario.class);
			usrCrit.add(Restrictions.eq("rfc",rfc.trim()).ignoreCase());
			usrCrit.add(Restrictions.eq("estatus",true));
			List<Usuario> users =usuarioService.findByCriteria(usrCrit);

			
			if (users!= null && !users.isEmpty()) {
			for (Usuario usuario : users) {
				UsuarioDTO usrDTO = new UsuarioDTO();
				try {
					String usrnamedecrypt = CryptoAES.decrypt(usuario.getNombreusuario());
					usrDTO.setWebuser(usrnamedecrypt);
				} catch (Exception e) {
					logger.error("Error al desencriptar: "+e.getMessage());
					addMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Ha ocurrido un error en la aplicación!!", null));
					e.printStackTrace();
				}
				//usrDTO.setWebuser(webuser);
				usrDTO.setEmail(usuario.getCorreoelectronico());
				usrDTO.setNombre(usuario.getNombre() +" "+usuario.getApellidopaterno() +" "+usuario.getApellidomaterno());
				usrDTO.setRfc(usuario.getRfc());
				usrDTO.setIdentificador(usuario.getIdentificador());
				String numTarjeta=null;
				try {
					numTarjeta = CryptoAES.decrypt2(usuario.getData4u1());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				usrDTO.setCuenta(numTarjeta);
				usersDTO.add(usrDTO);
			}
			}
			
		}else if (email != null && !email.trim().isEmpty()) {
			logger.info("correo "+email);
			try{
			DetachedCriteria usrCrit = DetachedCriteria.forClass(Usuario.class);
			usrCrit.add(Restrictions.eq("correoelectronico",email.toLowerCase()).ignoreCase());
			usrCrit.add(Restrictions.eq("estatus",true));
			List<Usuario> users =usuarioService.findByCriteria(usrCrit);
			if (users!= null && !users.isEmpty()) {
			for (Usuario usuario : users) {
				UsuarioDTO usrDTO = new UsuarioDTO();
				try {
					String usrnamedecrypt = CryptoAES.decrypt(usuario.getNombreusuario());
					usrDTO.setWebuser(usrnamedecrypt);
				} catch (Exception e) {
					logger.error("Error al desencriptar: "+e.getMessage());
					addMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Ha ocurrido un error en la aplicación!!", null));
					e.printStackTrace();
				}
				//usrDTO.setWebuser(webuser);
				usrDTO.setEmail(usuario.getCorreoelectronico());
				usrDTO.setNombre(usuario.getNombre() +" "+usuario.getApellidopaterno() +" "+usuario.getApellidomaterno());
				usrDTO.setRfc(usuario.getRfc());
				usrDTO.setIdentificador(usuario.getIdentificador());
				String webuser = CryptoAES.decrypt(usuario.getNombreusuario());
				usrDTO.setWebuser(webuser);
				String numTarjeta= CryptoAES.decrypt2(usuario.getData4u1());
				usrDTO.setCuenta(numTarjeta);
				usersDTO.add(usrDTO);
			}
			}
			
//			Usuario user =users.isEmpty()?null:users.get(0);
//			String webuser = CryptoAES.decrypt(user.getNombreusuario());
//			UsuarioDTO usrDTO = new UsuarioDTO();
//			usrDTO.setWebuser(webuser);
//			usrDTO.setCuenta(numTarjeta);
//			usrDTO.setEmail(user.getCorreoelectronico());
//			usrDTO.setNombre(user.getNombre() +" "+user.getApellidopaterno() +" "+user.getApellidomaterno());
//			usrDTO.setRfc(user.getRfc());
//			usrDTO.setIdentificador(user.getIdentificador());
//			usersDTO.add(usrDTO);
		} catch (Exception e1) {
			e1.printStackTrace();
			logger.error("Error al encriptar: "+e1.getMessage());
			addMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Ha ocurrido un error en la aplicación!!", null));
			return null;
		}
			
//			String servicio = MessageFormat.format(
//					SegConstants.URL_SERVICIO_GET_USR_BY_EMAIL,
//					email);
//			Document doc = null;
//			try {
//				doc = ReadXMLFile.readFile(servicio);
//			} catch (Exception e) {
//				e.printStackTrace();
//				logger.error("Error en servicio getWebUser: "+e.getMessage());
//				addMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error en servicio getWebUser!!", null));				
//				return null;
//			}
//			if (doc == null) {
//				logger.error("Error no se obtuvo respuesta del servicio getWebUserByEmail");
//				addMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Usuario no encontrado!!", null));				
//				return null;	
//			}
			
			
			
//			if (doc.getDocumentElement().getNodeName().equals("OUT")) {
//				NodeList nList = doc.getElementsByTagName("users");
//				for(int i = 0;i<nList.getLength();i++) {				
//					Node nNode = nList.item(0);
//					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
//						Element eElement = (Element) nNode;
//						webuser = eElement.getElementsByTagName("usuario")
//								.item(0).getNodeValue();
//						logger.info("webuser "+webuser);
//						String cuenta = eElement.getElementsByTagName("cuenta")
//								.item(0).getNodeValue();
//						logger.info("cuenta obtenida "+cuenta);
//						UsuarioDTO usrDTO = new UsuarioDTO();
//						usrDTO.setCuenta("***"+cuenta.substring(12));
//						usrDTO.setWebuser(webuser);
//						
//						try {
//							
//							String usrnamecrypt = CryptoAES.encrypt(webuser.trim());
//							
//							Usuario usr = new Usuario();
//							usr.setNombreusuario(usrnamecrypt);
//							List<Usuario> users =usuarioService.findByExample(usr);
//							if(users.isEmpty()){
//								continue;
//							}
//							Usuario user = users.get(0);
//							usrDTO.setEmail(email);
//							usrDTO.setNombre(user.getNombre() +" "+user.getApellidopaterno() +" "+user.getApellidomaterno());
//							usrDTO.setRfc(user.getRfc());
//							usrDTO.setIdentificador(user.getIdentificador());
//							usersDTO.add(usrDTO);
//						} catch (Exception e) {
//							e.printStackTrace();
//							logger.error("Error al encriptar: "+e.getMessage());
//							addMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Ha ocurrido un error en la aplicación!!", null));
//							return null;
//						}
//					}
//				}
//
//			}//fin if dcuemnto
		}else
			if((nombre != null && !nombre.trim().isEmpty()) 
				|| (apaterno != null && !apaterno.trim().isEmpty()) 
				|| (amaterno != null && !amaterno.trim().isEmpty()) ){

					logger.info("getUsusrio por nombre "+nombre +" "+apaterno+ " "+amaterno);
					DetachedCriteria usrCrit = DetachedCriteria.forClass(Usuario.class);
					usrCrit.add(Restrictions.eq("estatus",true));
					if(nombre != null && !nombre.trim().isEmpty()){
						//sql+=" and UPPER(nombre) like :nombre";
						usrCrit.add(Restrictions.like( "nombre","%"+nombre.trim()+"%").ignoreCase());
					}
					if(apaterno != null && !apaterno.trim().isEmpty()){
						//sql+=" and UPPER(apellidopaterno) like :apaterno";
						usrCrit.add(Restrictions.like( "apellidopaterno","%"+apaterno.trim()+"%").ignoreCase());
					}
					
					if(amaterno != null && !amaterno.trim().isEmpty()){
						//sql+=" and UPPER(apellidomaterno) like :amaterno";
						usrCrit.add(Restrictions.like( "apellidomaterno","%"+amaterno.trim()+"%").ignoreCase());
					}
					usrCrit.addOrder( Order.desc("nombre") ).addOrder( Order.desc("apellidopaterno") ).addOrder( Order.desc("apellidomaterno") );
					List<Usuario> users =usuarioService.findByCriteria(usrCrit);
					if (!users.isEmpty()) {
						for (Usuario usuario : users) {
							UsuarioDTO usrDTO = new UsuarioDTO();
							try {
								String usrnamedecrypt = CryptoAES
										.decrypt(usuario.getNombreusuario());
								usrDTO.setWebuser(usrnamedecrypt);
							} catch (Exception e) {
								logger.error("Error al desencriptar: "
										+ e.getMessage());
								addMessage(new FacesMessage(
										FacesMessage.SEVERITY_ERROR,
										"Ha ocurrido un error en la aplicación!!",
										null));
								e.printStackTrace();
							}
							//usrDTO.setWebuser(webuser);
							usrDTO.setEmail(usuario.getCorreoelectronico());
							usrDTO.setNombre(usuario.getNombre() + " "
									+ usuario.getApellidopaterno() + " "
									+ usuario.getApellidomaterno());
							usrDTO.setRfc(usuario.getRfc());
							usrDTO.setIdentificador(usuario.getIdentificador());
							usersDTO.add(usrDTO);
						}
					}

			
			}else if (numTarjeta != null && !numTarjeta.trim().isEmpty()) {
				try {
					String cuentaEncr=CryptoAES.encrypt2(numTarjeta.trim());
					DetachedCriteria usrCrit = DetachedCriteria.forClass(Usuario.class);
					usrCrit.add(Restrictions.eq("data4u1",cuentaEncr));
					usrCrit.add(Restrictions.eq("estatus",true));
					List<Usuario> users =usuarioService.findByCriteria(usrCrit);
					if (!users.isEmpty()) {
					Usuario user =users.isEmpty()?null:users.get(0);
					String webuser = CryptoAES.decrypt(user.getNombreusuario());
					UsuarioDTO usrDTO = new UsuarioDTO();
					usrDTO.setWebuser(webuser);
					usrDTO.setCuenta(numTarjeta);
					usrDTO.setEmail(user.getCorreoelectronico());
					usrDTO.setNombre(user.getNombre() +" "+user.getApellidopaterno() +" "+user.getApellidomaterno());
					usrDTO.setRfc(user.getRfc());
					usrDTO.setIdentificador(user.getIdentificador());
					usersDTO.add(usrDTO);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
					logger.error("Error al encriptar: "+e1.getMessage());
					addMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Ha ocurrido un error en la aplicación!!", null));
					return null;
				}
			}
//				
//				String servicio = MessageFormat.format(
//						SegConstants.URL_SERVICIO_GET_WEB_USR,
//						numTarjeta);
//				Document doc = null;
//				try {
//					doc = ReadXMLFile.readFile(servicio);
//				} catch (Exception e) {
//					e.printStackTrace();
//					logger.error("Error en servicio getWebUser: "+e.getMessage());
//					addMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error en servicio getWebUser!!", null));				
//					return null;
//				}
//				if (doc == null) {
//					logger.error("Error no se obtubo respuesta del servicio getWebUser");
//					addMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Usuario no encontrado!!", null));				
//					return null;	
//				}
//				
//				if (doc.getDocumentElement().getNodeName().equals("OUT")) {
//					NodeList nList = doc.getElementsByTagName("user");
//					if (nList.getLength() > 0) {				
//						Node nNode = nList.item(0);
//						if (nNode.getNodeType() == Node.ELEMENT_NODE) {
//							Element eElement = (Element) nNode;
//							webuser = eElement.getElementsByTagName("usuario")
//									.item(0).getNodeValue();
//							try {
//								String usrnamecrypt = CryptoAES.encrypt(webuser.trim());
//								
//								DetachedCriteria usrCrit = DetachedCriteria.forClass(Usuario.class);
//								usrCrit.add(Restrictions.eq("nombreusuario", usrnamecrypt));
//								usrCrit.add(Restrictions.eq("estatus",true));
//								List<Usuario> users =usuarioService.findByCriteria(usrCrit);
								
//								Usuario user =users.isEmpty()?null:users.get(0);
//								//if(user!= null){
//									if(webuser!= null && !webuser.trim().isEmpty()){
//										UsuarioDTO usrDTO = new UsuarioDTO();
//										usrDTO.setWebuser(webuser);
//										usrDTO.setCuenta(numTarjeta);
//										if(user!= null){
//											usrDTO.setEmail(user.getCorreoelectronico());
//											usrDTO.setNombre(user.getNombre() +" "+user.getApellidopaterno() +" "+user.getApellidomaterno());
//											usrDTO.setRfc(user.getRfc());
//											usrDTO.setIdentificador(user.getIdentificador());
//										}else{
//											//borrar usr de la ods
//											logger.info("OJO el usuario existe en la ods pero no esta registrado en el portal!");
//											String endPoint=MessageFormat.format(
//							    	    			SegConstants.URL_SERVICIO_REMOVE_USUARIO_WEB,
//							    	    		URLEncoder.encode(webuser,"UTF-8"));
//											URL httpUrl = new URL(endPoint);
//											InputStream istream = httpUrl.openStream();
//											InputStreamReader ir = new InputStreamReader(istream, "ISO-8859-1");
//											BufferedReader reader = new BufferedReader(ir);
//											StringBuffer buf = new StringBuffer();
//											String resp = reader.readLine();
//											logger.info("resp borrar usaurio ods: "+resp);
//											addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO,"El usuario ahora ya puede registrarse en el portal, ha sido desbloqueado!!", null));
//											return null;
//										}
//										usersDTO.add(usrDTO);
//								   }
//							} catch (Exception e) {
//								e.printStackTrace();
//								logger.error("Error al encriptar: "+e.getMessage());
//								addMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Ha ocurrido un error en la aplicación!!", null));
//								return null;
//							}
//						}
//					}else{
//						String error = doc.getElementsByTagName("error").item(0).getNodeValue();
//						logger.error("Error obtenido de getWebUser: " +error);
//						addMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error obtenido de getWebUser: "+error, null)); 
//						return null;
//					}
//				}//fin if dcuemnto
//				
//		}
		
		 webuser="";
		 email="";
		rfc="";
		 nombre="";
		 apaterno="";
		 amaterno="";
		 numTarjeta="";
		if(usersDTO.isEmpty()){
			addMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario no encontrado!!", null)); 
			return null;
		}
		//logger.info("Usuario(s) encontrados? "+ usersDTO.size());
		primerPag= true;
		if(usersDTO.size()>50){
			ultimaPag=false;
		}else{
			ultimaPag= true;
		}
		return "listaUsuarios";
	}
	
	
	
	
	private void addMessage(FacesMessage message){

        FacesContext.getCurrentInstance().addMessage(null, message);

    }
	
	public String logout(){
		HttpServletRequest req =(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		logger.info(req.getRemoteHost()+
		" remote user "+req.getRemoteUser()+
		" aut typo " +req.getAuthType()+
		" prinicopal " +req.getUserPrincipal().getName());
		req.getSession().invalidate();
		return "logout";
	}
	private int first;
	
	
	
	public int getFirst() {
		return first;
	}
	public void setFirst(int first) {
		this.first = first;
	}
	public String pagSiguiente() {  
		primerPag=false;
		first = first + 50;    
		if (first > usersDTO.size()) {        
			first = usersDTO.size() - 50;
			ultimaPag=true;
		}else{
			ultimaPag=false;
		}
		return null;
	}
	
	public String pagAnterior() {
		ultimaPag = false;
		first = first - 50;    
		if (first <= 0) {        
			first = 1;
			primerPag = true;
		}else{
			primerPag= false;	
		}
		return null;
	}

}
