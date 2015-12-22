package mx.com.interware.spira.web.mxp;

import mx.com.invex.exception.InvexServiceException;
/**
 * Interface de servicio de promociones MQ MXP de tsys
 * @author fernando.escamilla
 *
 */
public interface MXP {
	/**
	 * Aplica una promoción en tsys mediante un mesnaje de MQ
	 * al usando el servcio MXP
	 * @param param
	 * @return mesnaje de error o exito
	 * @throws TotalMessageException
	 */
String updateMXP(MXPParam param)throws InvexServiceException;
}
