package mx.com.invex.seguridad.ws;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import com.conecta.services.ConectaCryptographyService_PortType;
import com.conecta.services.ConectaCryptographyService_ServiceLocator;

public class ClientHSM {
	public static String encript(String cadena, String key) {
		ConectaCryptographyService_ServiceLocator sl = new ConectaCryptographyService_ServiceLocator();
		//sl.setConectaCryptographyPortEndpointAddress("");
		try {
			ConectaCryptographyService_PortType proxy=sl.getConectaCryptographyPort();
			String resp = null;
			try {
				resp = proxy.enctyptionKey(key, cadena);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "ERROR "+e.getMessage();
			}
			return resp;
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "ERROR"+e.getMessage();
		}
	}
	
	public static String decript(String cadena, String key) {
		ConectaCryptographyService_ServiceLocator sl = new ConectaCryptographyService_ServiceLocator();
		//sl.setConectaCryptographyPortEndpointAddress("");
		try {
			ConectaCryptographyService_PortType proxy=sl.getConectaCryptographyPort();
			String resp = null;
			try {
				resp = proxy.decryptionKey(key, cadena);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "ERROR "+e.getMessage();
			}
			return resp;
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "ERROR"+e.getMessage();
		}
	}
}
