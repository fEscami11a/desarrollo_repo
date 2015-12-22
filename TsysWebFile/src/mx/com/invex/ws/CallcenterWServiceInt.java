package mx.com.invex.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import mx.com.interware.spira.dto.CuentaAD;
import mx.com.invex.exception.InvexServiceException;
@WebService
public interface CallcenterWServiceInt {
	@WebMethod CuentaAD[] getCuentasAD(@WebParam(name="account") String account) throws InvexServiceException;
	

}
