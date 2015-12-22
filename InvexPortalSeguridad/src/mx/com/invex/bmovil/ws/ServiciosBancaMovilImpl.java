package mx.com.invex.bmovil.ws;

import java.rmi.RemoteException;

import javax.jws.WebService;
import javax.xml.rpc.ServiceException;

import mx.com.invex.seguridad.utils.SegConstants;
@WebService
public class ServiciosBancaMovilImpl {
	public java.lang.String[] getMovsByDay(java.lang.String arg0, java.lang.String arg1) throws InvexWSException, RemoteException {
		String[] res = null;
		ServiciosBancaMovilImplServiceLocator sl = new ServiciosBancaMovilImplServiceLocator();
		sl.setServiciosBancaMovilImplPortEndpointAddress(SegConstants.WEB_SERVICE_BANCA_MOVIL);
		ServiciosBancaMovilImplDelegate port= null;
		try {
			port = sl.getServiciosBancaMovilImplPort();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		res=port.getMovsByDay(arg0, arg1);
		return res;
	}
    public java.lang.String[] obtenerMovsAntesCorte(java.lang.String arg0) throws java.rmi.RemoteException, mx.com.invex.bmovil.ws.InvexWSException{
    	String[] res = null;
		ServiciosBancaMovilImplServiceLocator sl = new ServiciosBancaMovilImplServiceLocator();
		sl.setServiciosBancaMovilImplPortEndpointAddress(SegConstants.WEB_SERVICE_BANCA_MOVIL);
		ServiciosBancaMovilImplDelegate port= null;
		try {
			port = sl.getServiciosBancaMovilImplPort();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		res=port.obtenerMovsAntesCorte(arg0);
		return res;
    }
    public java.lang.String[] obtenerMovsDespuesCorte(java.lang.String arg0) throws java.rmi.RemoteException, mx.com.invex.bmovil.ws.InvexWSException{
    	String[] res = null;
		ServiciosBancaMovilImplServiceLocator sl = new ServiciosBancaMovilImplServiceLocator();
		sl.setServiciosBancaMovilImplPortEndpointAddress(SegConstants.WEB_SERVICE_BANCA_MOVIL);
		ServiciosBancaMovilImplDelegate port= null;
		try {
			port = sl.getServiciosBancaMovilImplPort();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		res=port.obtenerMovsDespuesCorte(arg0);
		return res;
    }
    public java.lang.String[] getProductosByWebUsr(java.lang.String arg0) throws java.rmi.RemoteException, mx.com.invex.bmovil.ws.InvexWSException{
    	String[] res = null;
		ServiciosBancaMovilImplServiceLocator sl = new ServiciosBancaMovilImplServiceLocator();
		sl.setServiciosBancaMovilImplPortEndpointAddress(SegConstants.WEB_SERVICE_BANCA_MOVIL);
		ServiciosBancaMovilImplDelegate port= null;
		try {
			port = sl.getServiciosBancaMovilImplPort();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		res=port.getProductosByWebUsr(arg0);
		return res;
    }
    public java.lang.String getNumLuciByWebUsr(java.lang.String arg0) throws java.rmi.RemoteException, mx.com.invex.bmovil.ws.InvexWSException{
    	String res = null;
		ServiciosBancaMovilImplServiceLocator sl = new ServiciosBancaMovilImplServiceLocator();
		sl.setServiciosBancaMovilImplPortEndpointAddress(SegConstants.WEB_SERVICE_BANCA_MOVIL);
		ServiciosBancaMovilImplDelegate port= null;
		try {
			port = sl.getServiciosBancaMovilImplPort();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		res=port.getNumLuciByWebUsr(arg0);
		return res;
    }
    public java.lang.String getCuentaCBA(java.lang.String arg0) throws java.rmi.RemoteException, mx.com.invex.bmovil.ws.InvexWSException{
    	String res = null;
		ServiciosBancaMovilImplServiceLocator sl = new ServiciosBancaMovilImplServiceLocator();
		sl.setServiciosBancaMovilImplPortEndpointAddress(SegConstants.WEB_SERVICE_BANCA_MOVIL);
		ServiciosBancaMovilImplDelegate port= null;
		try {
			port = sl.getServiciosBancaMovilImplPort();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		res=port.getCuentaCBA(arg0);
		return res;
    }
    
    public java.lang.String getPCA(java.lang.String arg0) throws java.rmi.RemoteException, mx.com.invex.bmovil.ws.InvexWSException{
    	String res = null;
		ServiciosBancaMovilImplServiceLocator sl = new ServiciosBancaMovilImplServiceLocator();
		sl.setServiciosBancaMovilImplPortEndpointAddress(SegConstants.WEB_SERVICE_BANCA_MOVIL);
		ServiciosBancaMovilImplDelegate port= null;
		try {
			port = sl.getServiciosBancaMovilImplPort();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		res=port.getPCA(arg0);
		return res;
    }
}
