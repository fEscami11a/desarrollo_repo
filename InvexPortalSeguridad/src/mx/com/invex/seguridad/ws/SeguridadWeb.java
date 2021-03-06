package mx.com.invex.seguridad.ws;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.servlet.ServletContext;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import mx.com.interware.spira.ls.facade.igbinaenca.ArrayUDataDTO;
import mx.com.interware.spira.ls.facade.igbinaenca.ResultadoIGBINAEnca;
import mx.com.invex.bmovil.exception.WSSeguridadException;
import mx.com.invex.seguridad.entidades.Intentosesion;
import mx.com.invex.seguridad.entidades.Sesion;
import mx.com.invex.seguridad.entidades.Usuario;
import mx.com.invex.seguridad.sevice.IntentoSesionService;
import mx.com.invex.seguridad.sevice.SesionService;
import mx.com.invex.seguridad.sevice.UsuarioService;
import mx.com.invex.seguridad.utils.CryptoAES;
import mx.com.invex.seguridad.utils.Helper;
import mx.com.invex.seguridad.utils.SegConstants;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tsys.xmlmessaging.ch.IASstatusCodeResponseDataType;
import com.tsys.xmlmessaging.ch.ICIcustInfoResponseDataType;
import com.tsys.xmlmessaging.ch.InqAcctStatus;
import com.tsys.xmlmessaging.ch.InqAcctStatusRequestType;
import com.tsys.xmlmessaging.ch.InqAcctStatusResponse;
import com.tsys.xmlmessaging.ch.InqCustInfo;
import com.tsys.xmlmessaging.ch.InqCustInfoRequestType;
import com.tsys.xmlmessaging.ch.InqCustInfoResponse;
import com.tsys.xmlmessaging.ch.InqGeneralAcct;
import com.tsys.xmlmessaging.ch.InqGeneralAcctRequestType;
import com.tsys.xmlmessaging.ch.InqGeneralAcctResponse;
import com.tsys.xmlmessaging.ch.TSYSprofileType;

@WebService
public class SeguridadWeb {

	private static final Logger logger = Logger.getLogger(SeguridadWeb.class);
	
@Resource    // Step 1
private WebServiceContext wsContext;   

public boolean updatePassword (String username,String contrasenia){
	ServletContext servletContext= (ServletContext) wsContext.getMessageContext().get(MessageContext.SERVLET_CONTEXT);
	ApplicationContext contexta = WebApplicationContextUtils.getWebApplicationContext(servletContext);
	UsuarioService usrDao = (UsuarioService) contexta.getBean("usuarioServiceImpl");
	
		try {
			if (username == null || username.trim().isEmpty()) {
				throw new WSSeguridadException(
						"Imposible utilizar valor nulo en variable: usuario");
			}
			if (contrasenia == null || contrasenia.trim().isEmpty()) {
				throw new WSSeguridadException(
						"Imposible utilizar valor nulo en variable: contrase�a");
			}
			//1.- Validamos que el usuario exista en base al usuario y contrase�a
			String usrnamecrypt = null;
			try {
				usrnamecrypt = ClientHSM.encript(CryptoAES.encrypt(username.trim()), SegConstants.HSM_KEY_USR);
			} catch (Exception e) {
				e.printStackTrace();
				throw new WSSeguridadException("Error al encriptar username "
						+ e.getMessage());
			}
			DetachedCriteria usrCrit = DetachedCriteria.forClass(Usuario.class);
			usrCrit.add(Restrictions.eq("nombreusuario", usrnamecrypt));
			usrCrit.add(Restrictions.eq("estatus", true));
			List<Usuario> users = usrDao.findByCriteria(usrCrit);
			Usuario usr = users.isEmpty() ? null : users.get(0);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return true;
}

@WebMethod(operationName = "AutentificarPorUsuario")
public String autentificarUsuario(@WebParam(name="username") String username)throws WSSeguridadException {
	logger.info("Autentifica por username");
	if (username == null || username.trim().isEmpty()) {
		throw new WSSeguridadException(
				"Imposible utilizar valor nulo en variable: usuario");
	}

	ServletContext servletContext= (ServletContext) wsContext.getMessageContext().get(MessageContext.SERVLET_CONTEXT);
	ApplicationContext contexta = WebApplicationContextUtils.getWebApplicationContext(servletContext);
	UsuarioService usrDao = (UsuarioService) contexta.getBean("usuarioServiceImpl");
	//1.- Validamos que el usuario exista en base al usuario y contrase�a
	String usrnamecrypt = null;
	try {
		usrnamecrypt = ClientHSM.encript(CryptoAES.encrypt(username.trim()), SegConstants.HSM_KEY_USR);
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

		if(usr== null){
			throw new WSSeguridadException("1|Usuario � Contrase�a incorrectos");
		}else{
			return MessageFormat.format("Ok|{0}", usr.getNombre());
		}	
}

@WebMethod(operationName = "Autentificar")
public String autentificar(@WebParam(name="username") String username, 
		@WebParam(name="contrasenia") String contrasenia)throws WSSeguridadException {
	logger.info("Autentifica por username y contrasenia");
	String res=null;
try {
	if (username == null || username.trim().isEmpty()) {
		throw new WSSeguridadException(
				"Imposible utilizar valor nulo en variable: usuario");
	}
	if (contrasenia == null || contrasenia.trim().isEmpty()) {
		throw new WSSeguridadException(
				"Imposible utilizar valor nulo en variable: contrase�a");
	}
	//1.- Validamos que el usuario exista en base al usuario y contrase�a
	String usrnamecrypt = null;
	try {
		usrnamecrypt = ClientHSM.encript(CryptoAES.encrypt(username.trim()), SegConstants.HSM_KEY_USR);
	} catch (Exception e) {
		e.printStackTrace();
		throw new WSSeguridadException("Error al encriptar username "
				+ e.getMessage());
	}
	ServletContext servletContext= (ServletContext) wsContext.getMessageContext().get(MessageContext.SERVLET_CONTEXT);
	ApplicationContext contexta = WebApplicationContextUtils.getWebApplicationContext(servletContext);
	UsuarioService usrDao = (UsuarioService) contexta.getBean("usuarioServiceImpl");
	IntentoSesionService intSesDao = (IntentoSesionService) contexta.getBean("intentoSesionServiceImpl");
	
	DetachedCriteria usrCrit = DetachedCriteria.forClass(Usuario.class);
	usrCrit.add(Restrictions.eq("nombreusuario", usrnamecrypt));
	usrCrit.add(Restrictions.eq("estatus",true));
	usrCrit.add(Restrictions.eq("contrasenia",ClientHSM.encript(contrasenia.trim(), SegConstants.HSM_KEY_PSW)));
	Calendar haceUnAnio = Calendar.getInstance();
	haceUnAnio.add(Calendar.YEAR, -1);
	usrCrit.add(Restrictions.gt("ultimoacceso", haceUnAnio.getTime()));
	logger.info("obtener usuario "+usrnamecrypt+ " pws "+contrasenia);
	List<Usuario> users =usrDao.findByCriteria(usrCrit);
	
	Usuario usr = users.isEmpty()?null:users.get(0);
	//usrDao.refreshUser(usr);
	//logger.info("usr email "+ usr.getCorreoelectronico() +" psw "+usr.getContrasenia());

		if(usr== null){
			usrCrit = DetachedCriteria.forClass(Usuario.class);
			usrCrit.add(Restrictions.eq("nombreusuario", usrnamecrypt));
			usrCrit.add(Restrictions.eq("estatus",true));
			users =usrDao.findByCriteria(usrCrit);
			usr = users.isEmpty()?null:users.get(0);
			if (usr!= null){
				Calendar tiempo = Calendar.getInstance();
				tiempo.add(Calendar.MINUTE, -SegConstants.MINS_PSW_BOLQUEADO);
				
				DetachedCriteria intsesCrit = DetachedCriteria.forClass(Intentosesion.class);
				intsesCrit.add(Restrictions.eq("identificadorusuario", usr.getIdentificador()));
				intsesCrit.add(Restrictions.eq("esbloqueada", false));
				intsesCrit.add(Restrictions.eq("tiemporegistro", tiempo.getTime()));
				List<Intentosesion> lintses =intSesDao.findByCriteria(intsesCrit);
				
				Intentosesion inses = lintses.isEmpty()?null:lintses.get(0);
				if(inses == null){
					inses = new Intentosesion();
					inses.setEsbloqueada(false);
					inses.setIdentificador(UUID.randomUUID().toString());
					inses.setIdentificadorusuario(usr.getIdentificador());
					inses.setNumerointentos(1);
					inses.setTiemporegistro(new Date());
					intSesDao.persist(inses);
				}else{
					
					int intentos = inses.getNumerointentos();
					//inses.setNumerointentos(new BigDecimal(intentos++));
					intentos++;
			                if (intentos == 3) //Se bloquea
			                {
			                	
			                    inses.setNumerointentos(3);
			                    inses.setEsbloqueada(true);
			                    //insesionMgr.programarDesbloqueo(inses.getIdentificador());
			                }
			                else{
			                	 inses.setNumerointentos(intentos);
			                }
			                intSesDao.merge(inses);
			           
				}
			}
			throw new WSSeguridadException("1|Usuario � Contrase�a incorrectos");
		}
		
	//2.- Ahora validamos si no esta bloqueado el usuario
		Calendar tiempo = Calendar.getInstance();
		tiempo.add(Calendar.MINUTE, -SegConstants.MINS_PSW_BOLQUEADO);
	
		DetachedCriteria intsesCrit = DetachedCriteria.forClass(Intentosesion.class);
		intsesCrit.add(Restrictions.eq("identificadorusuario", usr.getIdentificador()));
		intsesCrit.add(Restrictions.eq("esbloqueada", true));
		intsesCrit.add(Restrictions.eq("tiemporegistro", tiempo.getTime()));
		List<Intentosesion> lintses =intSesDao.findByCriteria(intsesCrit);
		
	Intentosesion inses = lintses.isEmpty()?null:lintses.get(0);
	
	if (inses != null) {
		throw new WSSeguridadException(
				"2|El usuario se encuentra bloqueado por exceder los 3 intentos de sesi�n, espera 20 min");
	}
	//3.- Validar si tiene una sesion abierta
	tiempo = Calendar.getInstance();
	tiempo.add(Calendar.MINUTE, -SegConstants.TIEMPO_EXP_SESION_MINS_MOVIL);
	DetachedCriteria sesCrit = DetachedCriteria.forClass(Sesion.class);
	sesCrit.add(Restrictions.eq("identificadorusuario", usr.getIdentificador()));
	sesCrit.add(Restrictions.eq("escaduca", false));
	sesCrit.add(Restrictions.gt("tiempoexpiracion", tiempo.getTime()));
	SesionService sesDao = (SesionService) contexta.getBean("sesionServiceImpl");
	List<Sesion> lses =sesDao.findByCriteria(sesCrit);
	Sesion sesActual =lses.isEmpty()?null:lses.get(0);
	if (sesActual != null) {
		
			throw new WSSeguridadException(
					"3|El usuario no puede tener dos sesiones abiertas al mismo tiempo");
		
	}
	//odsvalidacion
	//obtener info
	
	
	
	
	ClientTSL client= new ClientTSL();
	logger.info("usr email "+ usr.getCorreoelectronico() +" psw "+usr.getContrasenia());

	String account = CryptoAES.decrypt2(usr.getData4u1());
	String producto="";
	if(Helper.cuentaITAU(account)){
		ClientTS2 cts2= new ClientTS2();
				 TSYSprofileType tp = new TSYSprofileType();
				 tp.setClientID("7401");
				 tp.setUserID("gp5rwf");
				 tp.setVendorID("00000000");
				 InqCustInfoRequestType reqCustInfo = new InqCustInfoRequestType();
				 reqCustInfo.setKey(account);
				 reqCustInfo.setKeyType("cardNbr");
				 reqCustInfo.setVersion("2.0.0");
				 InqCustInfo inqCustInfo = new InqCustInfo();
				 inqCustInfo.setInqCustInfoRequest(reqCustInfo);
				 InqCustInfoResponse respCustInfo= cts2.inqCustInfo(tp, inqCustInfo);
				 List<ICIcustInfoResponseDataType> lcustInfo=respCustInfo.getInqCustInfoResult().getCustInfo();
				//si es tanscodificada, obtenemos la final
				 for (ICIcustInfoResponseDataType custInfo : lcustInfo) {
					if("Perpetrator".equals(custInfo.getCustType())){
						account=custInfo.getTransferInfo().getValue().getToAcctNbr().getValue();
						usr.setData4u1(CryptoAES.encrypt2(account));
						usrDao.merge(usr);
					}
				 }
					
					 InqGeneralAcctRequestType req = new InqGeneralAcctRequestType();
					 req.setVersion("2.19.0");
					 req.setKey(account);
					 req.setKeyType("cardNbr");
					 InqGeneralAcct inqGeneralAcct = new InqGeneralAcct();
					 inqGeneralAcct.setInqGeneralAcctRequest(req);
					InqGeneralAcctResponse resga= cts2.InqGeneralAcct(tp,inqGeneralAcct);
					String tpc = resga.getInqGeneralAcctResult().getAcctGeneralInfo().getValue().getTSYSProductCode() == null?"": resga.getInqGeneralAcctResult().getAcctGeneralInfo().getValue().getTSYSProductCode() ;
					String cpc = resga.getInqGeneralAcctResult().getAcctGeneralInfo().getValue().getClientProductCode() == null?"": resga.getInqGeneralAcctResult().getAcctGeneralInfo().getValue().getClientProductCode().getValue();

					 producto = Helper.getProductoItau(account,tpc,cpc);
					 InqAcctStatus inqAcctStatus = new InqAcctStatus();
					 InqAcctStatusRequestType inqAcctStatusReq = new InqAcctStatusRequestType();
					 //key="4196910074855913" keyType="cardNbr" version="1.0.0"/>
					 inqAcctStatusReq.setKey(account);
					 inqAcctStatusReq.setKeyType("cardNbr");
					 inqAcctStatusReq.setVersion("1.0.0");
					 inqAcctStatus.setInqAcctStatusRequest(inqAcctStatusReq);
					InqAcctStatusResponse resSt= cts2.inqAcctStatus(tp,inqAcctStatus);
					List<IASstatusCodeResponseDataType.Status> statusCode =resSt.getInqAcctStatusResult().getStatusCode().getValue().getStatus();
					for (IASstatusCodeResponseDataType.Status status : statusCode) {
						//vaalidar cr V9
						if("CL".equals(status.getCode()) && "V9".equals(status.getReason())){
							throw new WSSeguridadException(
									"5|Es probable que usted est�; intentando ingresar a la secci�n privada con usuario y/o contrase�a bloqueados o deshabilitados. Si se ha registrado m�s de una ocasi�n, los anteriores quedan invalidados. Le sugerimos entrar a �olvidaste tu usuario y/o contrase�a? Para recuperar sus datos con la informaci�n que usted proporcion�. De no ser as� lo invitamos a llamar a l�nea S�Card para que un ejecutivo pueda asesorarlo.");

						}
						//validar pagos vencidos
						if("PD".equals(status.getCode()) ){
							if(!"01".equals(status.getReason()) && !"02".equals(status.getReason()) && !"03".equals(status.getReason())){
								throw new WSSeguridadException(
										"5|Es probable que usted est�; intentando ingresar a la secci�n privada con usuario y/o contrase�a bloqueados o deshabilitados. Si se ha registrado m�s de una ocasi�n, los anteriores quedan invalidados. Le sugerimos entrar a �olvidaste tu usuario y/o contrase�a? Para recuperar sus datos con la informaci�n que usted proporcion�. De no ser as� lo invitamos a llamar a l�nea S�Card para que un ejecutivo pueda asesorarlo.");

							}
						}
					}
	}else{
	ResultadoIGBINAEnca infoCliente=null;
   String cuentaInicial=account;
    do{
    	infoCliente=client.getResultadoIGBINAEnca(account, "LineaSpira", "prodLS615#");
    	logger.info("estatus trans "+infoCliente.getInfoGeneralTHIGB().getEstatusTranscod()+" motivo trans "+infoCliente.getInfoGeneralTHIGB().getMotivoTranscod());
    	infoCliente=client.getResultadoIGBINAEnca(account, "LineaSpira", "prodLS615#");
    	 if(!("F".equals(infoCliente.getInfoGeneralTHIGB().getEstatusTranscod()) ||
    			    ("".equals(infoCliente.getInfoGeneralTHIGB().getEstatusTranscod().trim()) 
    			                    && "".equals(infoCliente.getInfoGeneralTHIGB().getMotivoTranscod().trim())))){
    			                    //traer transcodificada
    		 
    		 String cuentaTrans=account.substring(0,6)+infoCliente.getInfoGeneralTHIGB().getTranscod();
    		 account=cuentaTrans;
    	 }
    	
    	//mientras no sea la cuenta final
    }while((!("F".equals(infoCliente.getInfoGeneralTHIGB().getEstatusTranscod()) ||
    	    ("".equals(infoCliente.getInfoGeneralTHIGB().getEstatusTranscod().trim()) 
            && "".equals(infoCliente.getInfoGeneralTHIGB().getMotivoTranscod().trim())))));
	
    if(!cuentaInicial.equals(account)){
		String cuentaEncr=CryptoAES.encrypt2(account);
		usr.setData4u1(cuentaEncr);
		usrDao.merge(usr);
	}
	
    String creditRating=infoCliente.getInfoGeneralTHIGB().getTipoBloqueo();
    
    if(!"0.0".equals(client.obtenerSI02(account).getResponseDTO1().getAMT_CURR_PD_61_90()) || "V9".equals(creditRating)){
    	//mandar error bucket 3
    	throw new WSSeguridadException(
				"5|Es probable que usted est�; intentando ingresar a la secci�n privada con usuario y/o contrase�a bloqueados o deshabilitados. Si se ha registrado m�s de una ocasi�n, los anteriores quedan invalidados. Le sugerimos entrar a �olvidaste tu usuario y/o contrase�a? Para recuperar sus datos con la informaci�n que usted proporcion�. De no ser as� lo invitamos a llamar a l�nea S�Card para que un ejecutivo pueda asesorarlo.");
    }
    
    ArrayUDataDTO[] arrUData=infoCliente.getInfoGeneralTHIGB().getUData();
	//5.- validar si el cliente es programa cero
	String ucode3= arrUData[2].getToString();
   
    if (ucode3!= null && !ucode3.isEmpty()) {
		switch (ucode3.charAt(0)) {
		case 'A':
			producto = "BASICA";
			break;
		case 'B':
			producto = "MI BANCO";
			break;
		case 'D':
			producto = "MI BANCO";
			break;
		case 'E':
			producto = "EMPRESARIAL";
			break;
		case 'I':
			producto = "INVEX PLATINUM";
			break;
		case 'M':
			producto = "PLUS MC";
			break;
		case 'P':
			producto = "PLATINUM";
			break;
		case 'S':
			producto = "INVEX PLUS";
			break;
		case 'U':
			producto = "MANCHESTER";
			break;
		case 'V':
			producto = "VOLARIS";
			break;
		case 'C':
			producto = "EMPRESARIAL";
			break;
		default:
			producto = "OTRO";
			break;
		}
	}else{
		producto="desconocido";
	}
	}//fin else
    //fin ods validacion
	
	//Obtenemos datos de la validaci�n a trav�s de la VPN de invex, invocando un Servicio Web
//	String servicio=null;
//	try {
//		servicio = MessageFormat.format(
//				SegConstants.URL_SERVICIO_VALIDA_CUENTA_ACTIVA,
//				URLEncoder.encode(username,"UTF-8"));
//	} catch (UnsupportedEncodingException e1) {
//		// TODO Auto-generated catch block
//		e1.printStackTrace();
//	}
//	Document doc = null;
//	try {
//		logger.info("URLENCODE "+URLEncoder.encode(servicio,"UTF-8"));
//		doc = ReadXMLFile.readFile(servicio);
//	} catch (Exception e) {
//		e.printStackTrace();
//		throw new WSSeguridadException(
//				"5|No esta respondiendo el Servicio, servicio de validaci�n de la VPN por usuario");
//	}
//	if (doc == null) {
//		throw new WSSeguridadException(
//				"5|No esta respondiendo el Servicio, servicio de validaci�n de la VPN por usuario");
//	}
//
//	String producto= null;
//	if (doc.getDocumentElement().getNodeName().equals("OUT")) {
//		NodeList nList = doc.getElementsByTagName("user");
//		if (nList.getLength() > 0) {
//			
//				Node nNode = nList.item(0);
//				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
//					Element eElement = (Element) nNode;
//					String estatus = eElement.getElementsByTagName("status")
//							.item(0).getTextContent();
//					if ("no".equalsIgnoreCase(estatus.trim())) {
//						throw new WSSeguridadException(
//								"5|Es probable que usted est�; intentando ingresar a la secci�n privada con usuario y/o contrase�a bloqueados o deshabilitados. Si se ha registrado m�s de una ocasi�n, los anteriores quedan invalidados. Le sugerimos entrar a �olvidaste tu usuario y/o contrase�a? Para recuperar sus datos con la informaci�n que usted proporcion�. De no ser as� lo invitamos a llamar a l�nea S�Card para que un ejecutivo pueda asesorarlo.");
//					}
//					String mensaje = eElement.getElementsByTagName("mensaje")
//							.item(0).getTextContent();
//					producto = eElement.getElementsByTagName("producto")
//							.item(0).getTextContent();
//				}
//									
//			}//fin if lencgyh > 0
//	}//fin if dcuemnto
//	
logger.info("is usuario "+usr.getIdentificador().intValue() );
Date fechaUltimo =(Date)usr.getUltimoacceso().clone();
	//actuliza ultimo acceso
	usr.setUltimoacceso(new Date());
	usrDao.merge(usr);
	//UUID apSolNom = UUID.fromString("35397790-2A9E-476F-B4F9-4F8697163B59");
	UUID nuevaSesId = UUID.randomUUID();
	Sesion sesion = new Sesion();
//	Aplicacionsolicitante apsol = appSolMgr.findAppsolicitante("Web");
	sesion.setIdentificador(nuevaSesId.toString());
	sesion.setIdaplicacionsolicitante("D740129C-AB75-4178-BF5C-FE82B261C23E");
	sesion.setEscaduca(false);
	sesion.setFecharegistro(new Date());
	sesion.setIdentificadorusuario(usr.getIdentificador());
	sesion.setTiempoexpiracion(new Date());
	sesion.setUltimoacceso(new Date());
	sesDao.persist(sesion);
	res = MessageFormat.format("OK|{0}|{1}", nuevaSesId,
			fechaUltimo);
	res+="|"+producto;
	tiempo = Calendar.getInstance();
	tiempo.add(Calendar.MINUTE, -SegConstants.MINS_PSW_BOLQUEADO);
	
	intsesCrit = DetachedCriteria.forClass(Intentosesion.class);
	intsesCrit.add(Restrictions.eq("identificadorusuario", usr.getIdentificador()));
	intsesCrit.add(Restrictions.eq("esbloqueada", false));
	intsesCrit.add(Restrictions.eq("tiemporegistro", tiempo.getTime()));
	lintses =intSesDao.findByCriteria(intsesCrit);
	inses = lintses.isEmpty()?null:lintses.get(0);
	if(inses != null){
		inses.setNumerointentos(0);
		intSesDao.merge(inses);
	}
} catch (WSSeguridadException e) {
	
		
	logger.error(e.getMessage());
	
	throw e;
} catch (Exception e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
return res;
}

@WebMethod
public String Autorizar(@WebParam(name="idSesion") String idSesion)throws WSSeguridadException{
	logger.info("Autorizar");
	String resultado;
	try{
		ServletContext servletContext= (ServletContext) wsContext.getMessageContext().get(MessageContext.SERVLET_CONTEXT);
		ApplicationContext contexta = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		SesionService sesDao = (SesionService) contexta.getBean("sesionServiceImpl");
		//SesionDAO sesDao=(SesionDAO)context.getBean("sesionDao");
		DetachedCriteria sesCrit = DetachedCriteria.forClass(Sesion.class);
		sesCrit.add(Restrictions.eq("identificador", idSesion));
		sesCrit.add(Restrictions.eq("escaduca", false));
		List<Sesion> lses =sesDao.findByCriteria(sesCrit);
		Sesion ses =lses.isEmpty()?null:lses.get(0);
	logger.info("Sesion obtenida "+ ses==null?false:true);
	if(ses==null){
		//no se obtuvo sesion no caduca con ese id regresar false
		return MessageFormat.format("Ok|{0}", false);
	}
	int tiempoSesMovil=SegConstants.TIEMPO_EXP_SESION_MINS_WEB;
	long timepoSes = new Date().getTime()- ses.getTiempoexpiracion().getTime();
	//sin no se asuperado el tiempo de expiracion de la ses hay que actualizarlo
	if(timepoSes/(1000*60) <= tiempoSesMovil){
		boolean res= false;
		try{
		ses.setTiempoexpiracion(new Date());
		sesDao.merge(ses);
		res= true;
		 }catch (Exception ex)
        {
            throw new WSSeguridadException(ex.getMessage());
        }
		return MessageFormat.format("Ok|{0}", res);
	}
	//si ya expiro la sesion manda false
	return MessageFormat.format("Ok|{0}", false);
	 }catch (Exception ex)
     {
		 resultado = MessageFormat.format("Error|{0}|Error Inesperado. Detalles: {1}", UUID.randomUUID(), ex.getMessage());
     }
	return resultado;
}
@WebMethod
public String CerrarSession(@WebParam(name="idSesion") String idSesion){
	logger.info("CerrarSession");
	String resultado="";
	try{
		//obtener sesion activa (no caduca por el id)
		ServletContext servletContext= (ServletContext) wsContext.getMessageContext().get(MessageContext.SERVLET_CONTEXT);
		ApplicationContext contexta = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		//SesionDAO sesDao=(SesionDAO)context.getBean("sesionDao");
		SesionService sesDao = (SesionService) contexta.getBean("sesionServiceImpl");
		DetachedCriteria sesCrit = DetachedCriteria.forClass(Sesion.class);
		sesCrit.add(Restrictions.eq("identificador", idSesion));
		sesCrit.add(Restrictions.eq("escaduca", false));
		List<Sesion> lses =sesDao.findByCriteria(sesCrit);
		Sesion ses =lses.isEmpty()?null:lses.get(0);
		if(ses==null){
			return resultado;
		}
		ses.setEscaduca(true);
		sesDao.merge(ses);
		return "Ok|La sesi�n cerr� exitosamente";
	 }catch (Exception ex)
     {
		 resultado = MessageFormat.format("Error|{0}|Error Inesperado. Detalles: {1}", UUID.randomUUID(), ex.getMessage());
     }
	return resultado;
	}

//public static final void main(String... aArgs){
//    //generate random UUIDs
//    UUID idOne = UUID.randomUUID();
//    UUID idTwo = UUID.randomUUID();
//    System.out.println("UUID One: " + idOne);
//    System.out.println("UUID Two: " + idTwo);
//    UUID apSolNom = UUID.fromString("35397790-2A9E-476F-B4F9-4F8697163B59");
//    System.out.println("UUID 3: " + apSolNom);
//  }


}
