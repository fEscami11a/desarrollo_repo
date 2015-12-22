package mx.com.invex.seguridad.ws;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import mx.com.interware.spira.ls.LSWS;
import mx.com.interware.spira.ls.LSWSServiceLocator;
import mx.com.interware.spira.ls.facade.igbinaenca.ResultadoIGBINAEnca;
import mx.com.interware.spira.tsysws.si02.SI02FinalResponseDTO;
import mx.com.interware.spira.tsysws.si02.SI02RequestDTO;
import mx.com.interware.spira.tsysws.si02.SI02RequestSecurityDTO;
import mx.com.interware.spira.tsysws.si02.SI02WS;
import mx.com.interware.spira.tsysws.si02.SI02WSServiceLocator;
import mx.com.invex.seguridad.utils.SegConstants;

public class ClientTSL {

	public ResultadoIGBINAEnca getResultadoIGBINAEnca(String account, String username, String acceskey) throws ServiceException, RemoteException{
		LSWSServiceLocator service = new LSWSServiceLocator();
		service.setLSWSEndpointAddress(SegConstants.LSWS_END_POINT);
		//service.setLSWSEndpointAddress(this.endPoint);
		ResultadoIGBINAEnca res= null;
		
			LSWS lsws=service.getLSWS();
			//cuenta,LineaSpira,prodLS615#
			
		
				res =lsws.getResultadoIGBINAEnca(account,username,acceskey);
		
		return res;
	}
	
	public SI02FinalResponseDTO obtenerSI02(String cuenta)throws ServiceException, RemoteException{
		SI02WSServiceLocator service = new SI02WSServiceLocator();
		service.setSI02WSEndpointAddress(SegConstants.SI02WS);
		SI02WS si02=service.getSI02WS();
		SI02RequestDTO req = new SI02RequestDTO();
		req.setAccount(cuenta);
		SI02FinalResponseDTO resp =si02.getSI02(req, new SI02RequestSecurityDTO());
		
		
		return resp;

	}

}
