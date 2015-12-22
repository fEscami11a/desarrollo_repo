package mx.com.invex.ws;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.jws.WebParam;
import javax.jws.WebService;

import mx.com.interware.spira.dto.CuentaAD;
import mx.com.interware.spira.web.dao.DWHDAO;
import mx.com.interware.spira.web.si01.SI01;
import mx.com.interware.spira.web.si01.SI01Impl;
import mx.com.invex.exception.InvexServiceException;

import org.apache.log4j.Logger;
@WebService(endpointInterface = "mx.com.invex.ws.CallcenterWServiceInt")
public class CallcenterWService implements CallcenterWServiceInt{
	private static final Logger logger = Logger.getLogger(CallcenterWService.class);
	
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
	 * obtiene las cuentas relacionadas de un cliente de tsl por nombre y rfc
	 */
	@Override
	public CuentaAD[] getCuentasAD( String account) throws InvexServiceException {
		logger.info("getCuentasADDDD");
		//obtener save acct
		SI01 si01 = new SI01Impl();
		CuentaAD[]cuentasAD= null;
		  Pattern p = Pattern.compile("^\\d{16}$");
		     Matcher m = p.matcher(account);
		     
			if(!m.matches()){
				logger.error("La cuenta debe ser de 16 digitos");
				throw new InvexServiceException("La cuenta debe ser de 16 digitos");
			}
			try {
				String longName;
				String rfc;
				//obtner nombre y rfc
				//si es adicional se obtiene la cba
				if("SCA".equals(si01.getSI01ResponseDTO(account).getJVTYPE())){
					String cba =si01.getSI01ResponseDTO(account).getOTH_ACCT_NUMBER();
					 longName =si01.getSI01ResponseDTO(cba).getLONG_NAME();
					 rfc= si01.getSI01ResponseDTO(cba).getTAXID();
				}else{
					longName =si01.getSI01ResponseDTO(account).getLONG_NAME();
					rfc= si01.getSI01ResponseDTO(account).getTAXID();
				}
				logger.info("long name "+longName +" rfc "+rfc);
				
				//obtener cuentas de dwh
				//reemplaza por _ porque asi a de venir en la base me imagino y para buscarlo en la base daily dwh
					Set<CuentaAD> lcuentas=DWHDAO.getCuentasByUserId(longName.replace("*", "_").replace("\\", "_"),rfc);
					int lon = lcuentas.size();
					cuentasAD = new CuentaAD[lon];
					int i= 0;
					for (CuentaAD cuentasAD2 : lcuentas) {
						cuentasAD[i]= cuentasAD2;
						i++;
					}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("ERROR "+e.getMessage());
				throw new InvexServiceException("ERROR "+e.getMessage());
			} 
			
		
		return cuentasAD;
	}


	
}
