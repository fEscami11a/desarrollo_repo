package mx.com.invex.ws;

import java.util.List;

import javax.jws.WebService;

import mx.com.interware.spira.dto.CuentaTs2Info;
import mx.com.interware.spira.web.dao.Ts2dwhDAO;
import mx.com.invex.exception.InvexServiceException;

import org.apache.log4j.Logger;
@WebService(endpointInterface = "mx.com.invex.ws.PUTs2WebInt")
public class PUTs2Web implements PUTs2WebInt{
	private static final Logger logger = Logger.getLogger(PUTs2Web.class);
	
//	@Override
//	public String getCuentaByUsername(@WebParam(name="username") String username) throws InvexServiceException{
//		try {
//			return UptarjetaDAO.obtenerCuentasByUsername(username);
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new InvexServiceException(e.getMessage());
//			
//		}
//	}
	

	/**
	 * Obtiene las cuentas relacionadas ts2 por cliente unico
	 */
	public CuentaTs2Info[] getOtrosProductosTs2(String cuenta)throws InvexServiceException {
		
		 List<CuentaTs2Info> lcuentas= null;
      try {
    	    
		lcuentas=Ts2dwhDAO.getCuentasRelacionadas(cuenta);
		
	} catch (Exception e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
		throw new InvexServiceException(e1.getMessage());
	}
      return lcuentas.toArray(new CuentaTs2Info[lcuentas.size()]);
	}
	
	
		
		
	
}
