package mx.com.invex.msi.ws;

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
import mx.com.interware.spira.web.si07.SI07WSServiceLocator;
import mx.com.interware.spira.web.si07.Si07RequestDTO;
import mx.com.interware.spira.web.si07.Si07ResponseDTO;
import mx.com.interware.spira.web.si07.Si07VariableAreaDTO;
//import mx.com.invex.msi.util.MSIConstants;
/**
 * Cliente de ws de TsysWS para obtener los datos el cliente
 * @author fernando.escamillaa
 *
 */
public class ClientLSWS {
	private String endPoint;
	public ClientLSWS() {
		// TODO Auto-generated constructor stub
	}
	
	private String si07EndPoint;
	
	

	public String getSi07EndPoint() {
		return si07EndPoint;
	}

	public void setSi07EndPoint(String si07EndPoint) {
		this.si07EndPoint = si07EndPoint;
	}

	public String getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}
	public ClientLSWS(String endPoint) {
		this.endPoint = endPoint;
	}

	/**
	 * Otiene los datos del cliente por numero de cuenta
	 * @param account
	 * @param username
	 * @param acceskey
	 * @return objeto con los datos demograficos y de la cuenta del cliente
	 * @throws ServiceException
	 * @throws RemoteException
	 */
	public ResultadoIGBINAEnca getResultadoIGBINAEnca(String account, String username, String acceskey) throws ServiceException, RemoteException{
		LSWSServiceLocator service = new LSWSServiceLocator();
		//service.setLSWSEndpointAddress("http://172.16.18.13:9080/TsysWS/services/LSWS");
		service.setLSWSEndpointAddress(this.endPoint);
		ResultadoIGBINAEnca res= null;
		
			LSWS lsws=service.getLSWS();
			//cuenta,LineaSpira,prodLS615#
			
		
				res =lsws.getResultadoIGBINAEnca(account,username,acceskey);
		
		return res;
	}
	
	public String updateEmail(String account, String username, String acceskey, String email) throws ServiceException, RemoteException{
		LSWSServiceLocator service = new LSWSServiceLocator();
		//service.setLSWSEndpointAddress("http://172.16.18.13:9080/TsysWS/services/LSWS");
		service.setLSWSEndpointAddress(this.endPoint);
		String res= null;
		
			LSWS lsws=service.getLSWS();
			//cuenta,LineaSpira,prodLS615#

				res =lsws.updateAccount(account, username, acceskey, null, null, null, null, null, null, null, 
						null, null, null, null, null, null, null, null, null, email, null, null, null, null, null, null, null, null, null, null);
		
		return res;
	}
	
	public String updateTelCel(String account, String username, String acceskey, String telCel) throws ServiceException, RemoteException{
		LSWSServiceLocator service = new LSWSServiceLocator();
		//service.setLSWSEndpointAddress("http://172.16.18.13:9080/TsysWS/services/LSWS");
		service.setLSWSEndpointAddress(this.endPoint);
		String res= null;
		
			LSWS lsws=service.getLSWS();
			//cuenta,LineaSpira,prodLS615#

				res =lsws.updateAccount(account, username, acceskey, null, null, null, null, null, null, null, 
						null, null, null, null, null, null, null, telCel, null, null, null, null, null, null, null, null, null, null, null, null);
		
		return res;
	}
	
	public String enviarMensajeICM(String account, String username, String acceskey, String msgText) throws ServiceException, RemoteException{
		
		LSWSServiceLocator service = new LSWSServiceLocator();
		//service.setLSWSEndpointAddress("http://172.16.18.13:9080/TsysWS/services/LSWS");
		service.setLSWSEndpointAddress(this.endPoint);
		String res= null;
		
			LSWS lsws=service.getLSWS();
			//cuenta,LineaSpira,prodLS615#
			//public String addCollectionMessage(String account, String userName, String accessKey, String date, String time, String collNum, String msgType, String actionCode, String msgText) throws TotalMessageException{
			 String msgType = "01";
			 String collNum = "999999";
			 String actionCode = "CS";
		
				res =lsws.addCollectionMessage(account, username, acceskey,null, null, collNum, msgType, actionCode,msgText);
		
		return res;
	}
	
	public SI02FinalResponseDTO obtenerSaldoRevolvente(String cuenta)throws ServiceException, RemoteException{
		SI02WSServiceLocator service = new SI02WSServiceLocator();
		service.setSI02WSEndpointAddress(this.endPoint);
		SI02WS si02=service.getSI02WS();
		SI02RequestDTO req = new SI02RequestDTO();
		req.setAccount(cuenta);
		SI02FinalResponseDTO resp =si02.getSI02(req, new SI02RequestSecurityDTO());
		
		
		return resp;
//		if("ITA".equalsIgnoreCase(tipo)){
//			double saldoRev = new Double(resp.getResponseDTO1().getCURR_PUR_BAL_OS());
//			return saldoRev;
//		}else{
//			double saldoRev = new Double(resp.getResponseDTO1().getONE_CYC_OLDPUR_BAL_OS());
//			return saldoRev;
//		}
	}
	
	
	public Si07VariableAreaDTO[] getIthInfo(String cuenta)throws ServiceException, RemoteException{
		SI07WSServiceLocator si07sl = new SI07WSServiceLocator();
		si07sl.setSI07WSPortEndpointAddress(this.si07EndPoint);
		//si07sl.setSI07WSPortEndpointAddress("http://172.16.18.13:9080/TsysWS/SI07WSService");
		//System.out.println(si07sl.getSI07WSPortAddress());
		Si07RequestDTO req = new Si07RequestDTO();
		req.setAccount(cuenta);
		req.setBROWSE_IND("F");
		req.setSTARTING_SEQ_NBR("1");
		req.setNBR_OF_TRANS("84");
		Si07ResponseDTO resp= si07sl.getSI07WSPort().getSI07(req);
//		Si07VariableAreaDTO[] arr =resp.getVariableAreaDTO();
//		for (Si07VariableAreaDTO si07VariableAreaDTO : arr) {
//			si07VariableAreaDTO.getINC_TRAN_CODE();
//			si07VariableAreaDTO.getDATE_TRAN();
//			si07VariableAreaDTO.getPROMOTION_BALANCE();
//			si07VariableAreaDTO.getTYPE_RECORD();
//			si07VariableAreaDTO.getPROMOTION_ID();
//			si07VariableAreaDTO.getPROMO_DESC();
//			si07VariableAreaDTO.getMIN_PAY_DUE();
//			si07VariableAreaDTO.getFIXED_PAYMENT_AMOUNT();
//		}
		return resp.getVariableAreaDTO();
	}
	
	
	
	
}
