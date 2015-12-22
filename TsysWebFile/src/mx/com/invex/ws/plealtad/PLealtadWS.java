package mx.com.invex.ws.plealtad;

import java.sql.SQLException;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.apache.log4j.Logger;

import mx.com.interware.spira.web.dao.CommonDAO;
import mx.com.invex.exception.InvexWSException;
@WebService
public class PLealtadWS {
	
	private static final Logger logger = Logger.getLogger(PLealtadWS.class);

	/**
	 * Servicio para obtener puntos de banca movil
	 * @param numCliente
	 * @return
	 * @throws InvexWSException
	 */
	@WebMethod(operationName = "getPuntosEdoCuenta")
	public String getPuntosEdoCuenta(@WebParam(name="numCliente") String numCliente)throws InvexWSException{
		logger.info("getPuntosEdoCuenta ->"+numCliente);
		if(numCliente==null || numCliente.isEmpty()){
			throw new InvexWSException("El parametro numcliente no es valido");
		}
		String res= null;
		try {
			res = CommonDAO.getPuntosEdoCuenta(numCliente);
			if(res==null){
				throw new InvexWSException("No se encontrados datos para el numero de cliente: "+numCliente);
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
			throw new InvexWSException("ERROR "+e.getMessage());
		}
		return res;
	}
}
