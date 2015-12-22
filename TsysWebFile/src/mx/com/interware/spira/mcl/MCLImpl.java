package mx.com.interware.spira.mcl;

import mx.com.interware.spira.message.TPacket;
import mx.com.interware.spira.message.TPacketImpl;
import mx.com.interware.spira.message.exceptions.TotalMessageException;
import mx.com.interware.spira.message.util.Formatter;
import mx.com.interware.spira.message.util.NineFormatter;
import mx.com.interware.spira.message.util.XFormatter;
import mx.com.interware.spira.web.AbstractTSYSMessage;
import mx.com.interware.spira.web.util.PacketErrorCode;


import org.apache.log4j.Logger;

/**
 * 
 * Class MCLImpl.java
 * Package mx.com.interware.spira.web.mcl
 * Created on Jan 18, 2006
 * @author Víctor Báez
 * @version 1.0
 * 
 * 
 * 
 * @param
 * @return
 * @see
 * 
 * @throws IOException  If an input or output exception occurred
 * @throws ServletException  If an servlet exception occurred
 * 
 */
public class MCLImpl extends AbstractTSYSMessage implements MCL {
	private static Logger log = Logger.getLogger(MCLImpl.class);

	protected String getMessageName() {
		return "MCL";
	}

	public synchronized String updateMCL(ActualizacionLimiteCreditoRequest actualizacionLimiteCreditoRequest) throws TotalMessageException {
		PacketErrorCode errorCode = new PacketErrorCode();
		int lenght = 5;

		boolean FLDC100101 = true;
		boolean FLDC100102 = true;
		boolean FLDC500101 = true;
		boolean FLDC100103 = true;
		boolean FLDC500102 = true;
		//boolean FLDC500103 = true;

		//if(cashPercentageCurrent==null || cashPercentageNew==null || cashPercentageCurrent.equals("") || cashPercentageNew.equals("")){		FLDC100103=false;lenght--;}
		//if(availableMoneyCurrent==null || availableMoneyNew==null || availableMoneyCurrent.equals("") || availableMoneyNew.equals("")){		FLDC500102=false;lenght--;}

		if (actualizacionLimiteCreditoRequest.getPorcentajeEfectivoActual() == null
			|| actualizacionLimiteCreditoRequest.getPorcentajeEfectivoNuevo() == null
			|| actualizacionLimiteCreditoRequest.getPorcentajeEfectivoActual().equals("")
			|| actualizacionLimiteCreditoRequest.getPorcentajeEfectivoNuevo().equals("")) {
			FLDC100103 = false;
			lenght--;
		}
		if (actualizacionLimiteCreditoRequest.getDineroDisponibleActual() == null
			|| actualizacionLimiteCreditoRequest.getDineroDisponibleNuevo() == null
			|| actualizacionLimiteCreditoRequest.getDineroDisponibleActual().equals("")
			|| actualizacionLimiteCreditoRequest.getDineroDisponibleNuevo().equals("")) {
			FLDC500102 = false;
			lenght--;
		}

		if (actualizacionLimiteCreditoRequest.getLimiteCreditoActual() == null) {
			actualizacionLimiteCreditoRequest.setLimiteCreditoActual("");
		}
		//if(availableCashCurrent==null || availableCashNew==null || availableCashCurrent.equals("") || availableCashNew.equals("")){		FLDC500103=false;lenght--;}

		try {
			log.info("" + lenght);
			int current = 2;
			Formatter fmtX = new XFormatter();
			Formatter fmt9 = new NineFormatter();
			TPacket[] packet = new TPacket[lenght + 4];

			packet[0] = new TPacketImpl();
			packet[0].convertToDefaultHeader();
			packet[0].getElementRequest().getElement("accountID").setValue(actualizacionLimiteCreditoRequest.getNumeroCuenta());
			packet[1] = new TPacketImpl();
			packet[1].convertToDefaultMaintHeader("CANU", "NYXXY");
			packet[1].getElementRequest().getElement("processID").setValue(getMessageName());

			if (FLDC100101) {
				fmt9.init("9(07)");
				packet[current] = new TPacketImpl();
				packet[current].convertToFLDCCurrentValue(
					"100101",
					actualizacionLimiteCreditoRequest.getLimiteCreditoActual(),
					actualizacionLimiteCreditoRequest.getLimiteCreditoNuevo(),
					fmt9);
				// Monetary amount of the credit limit  7 numeric	
				current++;
			}

			if (FLDC100102) {
				fmt9.init("9(04)");
				packet[current] = new TPacketImpl();
				packet[current].convertToFLDC("100102", actualizacionLimiteCreditoRequest.getNumeroOfficer(), fmt9);
				// Identification number of the officer authorizing the change 4 numeric	
				current++;
			}

			if (FLDC500101) {
				fmtX.init("X(01)");
				packet[current] = new TPacketImpl();
				packet[current].convertToFLDC("500101", actualizacionLimiteCreditoRequest.getResetDeclines(), fmtX);
				// Indicates whetever TSYS authorizations reset the numbers of declines (Y or N) 1 char	
				current++;
			}

			if (FLDC100103) {
				fmt9.init("9(03)");
				packet[current] = new TPacketImpl();
				packet[current].convertToFLDCCurrentValue(
					"100103",
					actualizacionLimiteCreditoRequest.getPorcentajeEfectivoActual(),
					actualizacionLimiteCreditoRequest.getPorcentajeEfectivoNuevo(),
					fmt9);
				// Percentage of the commercial account that is cash 3 numeric	
				current++;
			}
			
			if (FLDC500102) {
				fmt9.init("9(07)");
				packet[current] = new TPacketImpl();
				packet[current].convertToFLDCCurrentValue(
					"500102",
					actualizacionLimiteCreditoRequest.getDineroDisponibleActual(),
					actualizacionLimiteCreditoRequest.getDineroDisponibleNuevo(),
					fmt9);
				// Whole monetary amount that is available money 7 numeric	
				current++;
			}

			//			if(FLDC500103){
			//				fmt9.init("9(07)");
			//				packet[current] = new TPacketImpl();
			//				packet[current].convertToFLDCCurrentValue("500103",availableCashCurrent,availableCashNew,fmt9);// Whole monetary amount that is available cash 7 numeric	
			//				current++;				
			//			}

			packet[current] = new TPacketImpl();
			packet[current].convertToDefaultMEnd();
			current++;
			packet[current] = new TPacketImpl();
			packet[current].convertToDefaultTEnd();
			sendMessage(packet);
		//	invalidatePacket(Classes.getClassArray(), actualizacionLimiteCreditoRequest.getNumeroCuenta());
			// invalidatePacket(actualizacionLimiteCreditoRequest.getNumeroCuenta()); // Hasta que se dejen de usar pantallas negras para actualización
			
			return errorCode.getStatusMQResponse(packet, "MCL", actualizacionLimiteCreditoRequest.getUsuario());

		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			throw new TotalMessageException("Problemas al ejecutar mantenimiento MCL", e);
		}
	}

}
