package mx.com.invex.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService
public interface MobilWeb {
	@WebMethod
	@WebResult(name="respGetProdByWebUser")
	RespGetProdByWebUser getProductosByWebUsr(@WebParam(name="webUsuario") String webUsuario);
	
	@WebMethod
	@WebResult(name="respGetMovsAntesCorte")
	RespGetProdByWebUser getMovsAntesCorte(@WebParam(name="cuenta") String cuenta);
	@WebMethod
	@WebResult(name="numLUCI")
	String getNumLuciByWebUser(@WebParam(name="webUsuario")String webUsuario);
}
