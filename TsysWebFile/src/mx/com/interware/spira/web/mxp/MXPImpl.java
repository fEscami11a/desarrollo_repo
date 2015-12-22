package mx.com.interware.spira.web.mxp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mx.com.interware.spira.message.TPacket;
import mx.com.interware.spira.message.TPacketImpl;
import mx.com.interware.spira.message.util.Formatter;
import mx.com.interware.spira.message.util.NineFormatter;
import mx.com.interware.spira.message.util.XFormatter;
import mx.com.interware.spira.web.AbstractTSYSMessage;
import mx.com.interware.spira.web.util.PacketErrorCode;
import mx.com.invex.exception.InvexServiceException;

import org.apache.log4j.Logger;

public class MXPImpl extends AbstractTSYSMessage implements MXP{

	private static Logger log = Logger.getLogger(MXPImpl.class);

	protected String getMessageName() {
		return "MXP";
	}
	/**
	 * Aplica promocion en Tsys mediante el servicio de MQ MXP
	 * de mantenimiento
	 */
	public String updateMXP(MXPParam param) throws InvexServiceException {
		try{
			
			 Pattern p = Pattern.compile("^\\d{16}$");
		     Matcher m = p.matcher(param.getAccount());
		     
			if(!m.matches()){
				log.error("La cuenta debe ser de 16 digitos");
				return "La cuenta debe ser de 16 digitos";
			}
			
			p= Pattern.compile("^\\D{2}$");
			m = p.matcher(param.getBalanceId());
			if(!m.matches()){
				log.error("El balance id deben ser 2 carateres alfabeticos");
				return "El balance id deben ser 2 carateres alfabeticos";
			}
			
			
			p= Pattern.compile("^\\w{8}$");
			m = p.matcher(param.getPromoId());
			if(!m.matches()){
				log.error("El promo id deben ser 8 carateres alfanumericos");
				return "El promo id deben ser 8 carateres alfanumericos";
			}
			
			p= Pattern.compile("^\\d{1,9}$");
			m = p.matcher(param.getAmount());
			if(!m.matches()){
				log.error("El monto deben ser de 9 digitos maximo");
				return "El monto deben ser de de 9 digitos maximo";
			}
			
			boolean desc = false;
			if(param.getDescription()!= null && !param.getDescription().isEmpty()){
				desc=true;
			}
			
			PacketErrorCode errorCode= new PacketErrorCode();
			Formatter fmtX = new XFormatter();
			TPacket[] packet = new TPacket[desc?8:7];
			int packInd=0;
			packet[packInd] = new TPacketImpl();
			packet[packInd].convertToDefaultHeader();
			packet[packInd].getElementRequest().getElement("accountID").setValue(param.getAccount());
			
			packInd++;
			packet[packInd] = new TPacketImpl();
			packet[packInd].convertToDefaultMaintHeader("CANU","YYXXY");
			packet[packInd].getElementRequest().getElement("processID").setValue(getMessageName());
			
			packInd++;
			fmtX.init("X(02)");
			packet[packInd] = new TPacketImpl();
			packet[packInd].convertToFLDC("500001",param.getBalanceId(), fmtX);// Enter personal identification number; 4 char
			
			packInd++;
			fmtX.init("X(08)");
			packet[packInd] = new TPacketImpl();
			packet[packInd].convertToFLDC("500002",param.getPromoId(), fmtX);// Enter personal identification number; 4 char
			
			packInd++;
			Formatter fmt = new NineFormatter();
			fmt.init("9(09)");
			packet[packInd] = new TPacketImpl();
			packet[packInd].convertToFLDC("500003",param.getAmount(), fmt);// Enter personal identification number; 4 char
			packInd++;
			if(desc){
				fmtX.init("X(41)");
				packet[packInd] = new TPacketImpl();
				packet[packInd].convertToFLDC("500004",param.getDescription(), fmtX);// Enter personal identification number; 4 char
				packInd++;
			}
			
			packet[packInd] = new TPacketImpl();
			packet[packInd].convertToDefaultMEnd();
			packInd++;
			packet[packInd] = new TPacketImpl();
			packet[packInd].convertToDefaultTEnd();
			sendMessage(packet);
			//invalidatePacket(Classes.getClassArray(), param.getAccount()); // Hasta que se dejen de usar pantallas negras para actualización
			return errorCode.getStatusMQResponse(packet,"MXP",param.getUserName());
		}catch (Exception e) {
			log.error(e);
			throw new InvexServiceException("Problemas al ejecutar mantenimiento MXP: "+e.getMessage());
		}
	}

}
