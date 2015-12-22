package mx.com.invex.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import mx.com.interware.spira.dto.CuentaTs2Info;
import mx.com.invex.exception.InvexServiceException;
@WebService
public interface PUTs2WebInt {
	
	@WebMethod CuentaTs2Info[] getOtrosProductosTs2(@WebParam(name="account") String cuenta)throws InvexServiceException ;
//	String getCuentaByUsername(String username) throws InvexServiceException;
}
