/*
 * Created on Oct 6, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package mx.com.interware.spira.ncm;

import java.text.SimpleDateFormat;
import java.util.Date;

import mx.com.interware.spira.message.TPacket;
import mx.com.interware.spira.message.TPacketImpl;
import mx.com.interware.spira.message.exceptions.TotalMessageException;
import mx.com.interware.spira.message.util.Formatter;
import mx.com.interware.spira.message.util.NineFormatter;
import mx.com.interware.spira.message.util.XFormatter;
import mx.com.interware.spira.web.AbstractTSYSMessage;
import mx.com.interware.spira.web.util.PacketErrorCode;

import org.apache.log4j.Logger;

public class NCMImpl extends AbstractTSYSMessage implements NCM {
	private static Logger log = Logger.getLogger(NCMImpl.class);

	protected String getMessageName() {
		return "    ";
	}

	public synchronized String addCollectionMessage(
		String account,
		String userName,
		String date,
		String time,
		String collNum,
		String msgType,
		String actionCode,
		String msgText)
		throws TotalMessageException {
		log.debug("addCollectionMessage");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat hourFormat = new SimpleDateFormat("kkmmss");
		Date now = new Date();
		PacketErrorCode errorCode = new PacketErrorCode();
	//	PropertiesReader rp = new PropertiesReader("mx/com/interware/spira/web/crvmpnconfig.properties");
		String Fecha = dateFormat.format(now);
		String Hora = hourFormat.format(now);
		Formatter fmtX = new XFormatter();
		Formatter fmt9 = new NineFormatter();
		try {

			TPacket[] packet = new TPacket[10]; //TSYS,BOSS,FLDC,FLDC,FLDC,FLDC,FLDC,FLDC,FLDC,FLDC,MEND,TEND
			packet[0] = new TPacketImpl();
			packet[0].convertToDefaultHeader();
			packet[0].getElementRequest().getElement("accountID").setValue(account);
			packet[1] = new TPacketImpl();
			packet[1].convertToDefaultMaintHeader("CANA", "NYXNY");
			packet[1].getElementRequest().getElement("processID").setValue(getMessageName());
			packet[2] = new TPacketImpl();
			fmtX.init("X(08)");
			packet[2].convertToFLDC("006101", Fecha, fmtX); // Message Date
			packet[3] = new TPacketImpl();
			fmtX.init("X(06)");
			packet[3].convertToFLDC("006102", Hora, fmtX); // Message Time
			packet[4] = new TPacketImpl();
			fmt9.init("9(06)");
			packet[4].convertToFLDC("006103", collNum, fmt9); // Collector Number
			packet[5] = new TPacketImpl();
			fmtX.init("X(02)");
			packet[5].convertToFLDC("006104", msgType, fmtX); // Message Type 01 Hardcode
			packet[6] = new TPacketImpl();
			fmtX.init("X(02)");
			packet[6].convertToFLDC("006105", actionCode, fmtX); // Action Code
			packet[7] = new TPacketImpl();
			packet[7].convertToFLDC("006107", msgText, null); // Message Text
			packet[8] = new TPacketImpl();
			packet[8].convertToDefaultMEnd();
			packet[9] = new TPacketImpl();
			packet[9].convertToDefaultTEnd();
			sendMessage(packet);
			//invalidatePacket(Classes.getClassArray(), account); // Hasta que se dejen de usar pantallas negras para actualización
			return errorCode.getStatusMQResponse(packet, "NCM", userName);
		}
		catch (Exception e) {
			log.error(e);
			throw new TotalMessageException("Problemas al ejecutar mantenimiento NCM", e);
		}

	}

}
