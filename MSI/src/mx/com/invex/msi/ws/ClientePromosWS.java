package mx.com.invex.msi.ws;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import mx.com.interware.spira.web.mxp.MXPWSDelegate;
import mx.com.interware.spira.web.mxp.MXPWSServiceLocator;
import mx.com.interware.spira.web.mxp.MxpParam;
import mx.com.invex.msi.util.MSIConstants;

public class ClientePromosWS {
	public ClientePromosWS(String endPoint) {
		this.endPoint = endPoint;
	}
	private String endPoint;

	public String getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}
public String aplicarPromocion(MxpParam param) throws ServiceException, RemoteException{
	MXPWSServiceLocator service = new MXPWSServiceLocator();
	service.setMXPWSPortEndpointAddress(this.endPoint);
	MXPWSDelegate del =service.getMXPWSPort();
	
	String res = del.updateMXP(param);
	return res;
}
}
