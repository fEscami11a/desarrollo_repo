/*
 * Created on Feb 22, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package mx.com.interware.spira.web.util;

import java.io.ByteArrayOutputStream;
import java.sql.SQLException;

import mx.com.interware.spira.message.CompoundMessageElement;
import mx.com.interware.spira.message.TPacket;
import mx.com.interware.spira.message.exceptions.ElementNotFoundException;
import mx.com.interware.spira.web.dao.CommonDAO;

import org.apache.log4j.Logger;

/**
 * @author mdiaz
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PacketErrorCode {
	private static Logger log = Logger.getLogger(PacketErrorCode.class);

	public String PacketToString(TPacket[] packet) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		String msg = "";
		try {
			for (int i = 0; i < packet.length; i++) {
				packet[i].writeResponseTo(baos);
			}
			msg = baos.toString();
		}
		catch (Exception e) {
			log.error(e);
		}
		return msg;
	}

	public String getStatusMQResponse(TPacket[] packet, String processID, String userName) throws ElementNotFoundException, SQLException {
		

		CommonDAO dao = new CommonDAO();
		StringBuffer result = new StringBuffer();
		CompoundMessageElement elem = null;
		String errorCode = "";

		if (packet[0].getElementResponse().getElement("passFail").getValue().equals("F")) {
			if (packet[1].getElementResponse().getElement("packetID").getValue().equals("E900")) {
				return packet[1].getElementResponse().getElement("errorCode").getValue().toString()
					+ ":"
					+ packet[1].getElementResponse().getElement("message").getValue().toString();
				//return "sigue sin jalar";
			}
			for (int j = 1; j < packet.length; j++) {
				elem = packet[j].getElementResponse();
				if (elem != null) {
					if (elem.getElement("packetID").getValue().equals("MEND") || elem.getElement("packetID").getValue().equals("TEND"))
						continue;
					if (elem.getElement("passFail").getValue().equals("F")) {
						if (elem.getElement("packetID").getValue().equals("FLDC")) {
							if (result.length() > 0)
								result.append(",");
							errorCode = elem.getElement("errorCode").getValue().toString();
							result.append(
								"Field :"
									+ elem.getElement("fieldIdentifier").getValue()
									+ "["
									+ "Error Code :"
									+ errorCode
									+ "==>"
									+ CommonDAO.getErrorDesc(processID, errorCode)
									+ "]");
						}
						else {
							if (result.length() > 0)
								result.append(",");
							errorCode = elem.getElement("errorCode").getValue().toString();
							result.append(
								"Packet :"
									+ elem.getElement("packetID").getValue()
									+ "["
									+ "Error Code :"
									+ errorCode
									+ "==>"
									+ CommonDAO.getErrorDesc(processID, errorCode)
									+ "]");
						}
					}
				}
			}
			//return "sigue sin jalar";
			log.info("mandando error");
			return result.toString();
		}
		log.info("mandando alta bitacora");
		String strPacket = PacketToString(packet);
		if (strPacket.length() > 1499) // Restringe el tamaño del paquete
			{
			strPacket = strPacket.substring(0, 1499);
		}
		try{
		dao.setBitacora(processID, userName, strPacket);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "OK";
	}

}
