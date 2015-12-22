package mx.com.invex.msi.ws;

import org.invex.message.Msgts2Ws_PortType;
import org.invex.message.Msgts2Ws_ServiceLocator;

public class Ts2MsgWsClient {
	
	private String endPoint;
	
	
	
	public String getEndPoint() {
		return endPoint;
	}



	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}



	public String insertaMsgTs2(String userId,String custId,String cuenta,String msg) {
		Msgts2Ws_ServiceLocator sl = new Msgts2Ws_ServiceLocator();
		if(endPoint!= null){
			sl.setMsgts2WsPortEndpointAddress(endPoint);
		}
		
		try {
			Msgts2Ws_PortType proxy= sl.getMsgts2WsPort();
			String res=proxy.insertaMsg(userId, "Mensajes AT", msg, custId, cuenta, "1", "0", "4");
			return res;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
