package mx.com.invex.msi.ws;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import mx.com.invex.ws.msi.ServiciosMSIImplDelegate;
import mx.com.invex.ws.msi.ServiciosMSIImplServiceLocator;

import org.apache.log4j.Logger;
/**
 * Cliente para ws que trae lso movimientos de una cuenta por rango de fecha
 * @author fernando.escamillaa
 *
 */
public class ClienteGetMovs {

private  final static Logger logger= Logger.getLogger(ClienteGetMovs.class);
public ClienteGetMovs(String endPoint) {
	this.endPoint=endPoint;
}

private String endPoint;

public String getEndPoint() {
	return endPoint;
}

public void setEndPoint(String endPoint) {
	this.endPoint = endPoint;
}

/**
 * dado una cuenta y un rango regresa un arary de string con los movs separados por pipes
 * de la forma codigo|fecha|tipo|monto|desc|num ref|em promo
 * @param fechain
 * @param fechafin
 * @param account
 * @return string de movimientos
 * @throws ServiceException
 * @throws RemoteException
 */
public String[] webMovEdoCtaFech(String fechain,String fechafin,String account,String[] codigos,double montoMin) throws ServiceException, RemoteException{
	ServiciosMSIImplServiceLocator service = new ServiciosMSIImplServiceLocator();
	service.setServiciosMSIImplPortEndpointAddress(this.endPoint);
	String[] arrMovs=null;
	
		ServiciosMSIImplDelegate del= service.getServiciosMSIImplPort();
		
		// arrMovs=del.webMovEdoCtaFech("20120301", "20120511", "4631869000451424");
			logger.info("fecha in "+fechain +" fecha fin "+fechafin +" cuenta "+account);
			arrMovs=del.webMovEdoCtaFech(fechain, fechafin, account,codigos,montoMin);
			if(arrMovs!= null){
				for (String string : arrMovs) {
					logger.info(string);
				}
			}else{
				logger.info("no se encontraron resultados");
			}
	
	
	return arrMovs;
}

public String[] getMovsCampComercios(String fechain,String fechafin,String account,String[] codigos,Double monto,String descripcion) throws ServiceException, RemoteException{
	ServiciosMSIImplServiceLocator service = new ServiciosMSIImplServiceLocator();
	service.setServiciosMSIImplPortEndpointAddress(this.endPoint);
	String[] arrMovs=null;
	ServiciosMSIImplDelegate del= service.getServiciosMSIImplPort();
	logger.info("getMovsByCodigoRangoFechaMontoDescripcion: "+fechain+"|"+fechafin+"|"+account+"|"+codigos[0]+"|"+monto+"|"+descripcion);
	arrMovs = del.getMovsByCodigoRangoFechaMontoDescripcion(fechain,fechafin, account, codigos, monto, descripcion);
	return arrMovs;
}

public String getTipoProducto(String cuenta, String ucode3){
	String tipoProd = null;
	try {
		ServiciosMSIImplServiceLocator service = new ServiciosMSIImplServiceLocator();
		service.setServiciosMSIImplPortEndpointAddress(this.endPoint);
		ServiciosMSIImplDelegate del = service.getServiciosMSIImplPort();
		tipoProd = del.getTipoProducto(cuenta, ucode3);
	} catch (Exception e) {
		logger.error("ERROR WS getTipoProducto"+e.getMessage());
		e.printStackTrace();
	}
	return tipoProd;
}

public static void main(String[] args) {
	try {
		//new ClienteGetMovs().getMovsPeriodo("4631869000451424");
	
	
	} catch (
			Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
