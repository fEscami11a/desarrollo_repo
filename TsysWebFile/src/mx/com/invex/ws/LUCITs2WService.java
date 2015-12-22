package mx.com.invex.ws;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class LUCITs2WService {
	@WebMethod
	public String actualizarDatosDemograficos(String folio,String account){
		LUCIWSTs2 service = new LUCIWSTs2();
		return service.actualizarDatosDemograficos(folio, account);
	}
}
