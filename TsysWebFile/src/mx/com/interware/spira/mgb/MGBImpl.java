/*
 * Created on Nov 10, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package mx.com.interware.spira.mgb;

import mx.com.interware.spira.message.TPacket;
import mx.com.interware.spira.message.TPacketImpl;
import mx.com.interware.spira.message.exceptions.TotalMessageException;
import mx.com.interware.spira.message.util.Formatter;
import mx.com.interware.spira.message.util.NineFormatter;
import mx.com.interware.spira.message.util.XFormatter;
import mx.com.interware.spira.web.AbstractTSYSMessage;
import mx.com.interware.spira.web.util.PacketErrorCode;
//import mx.com.interware.spira.web.util.Classes;
//import mx.com.interware.spira.web.util.PacketErrorCode;
import org.apache.log4j.Logger;

public class MGBImpl extends AbstractTSYSMessage implements MGB {
	private static Logger log = Logger.getLogger(MGBImpl.class);

	protected String getMessageName() {
		return "    ";
	}

	public synchronized String updateMGB(ActualizacionMGB actualizacionMGB) throws TotalMessageException {
		log.debug("Servicio MQ tsys MGB");
		PacketErrorCode errorCode = new PacketErrorCode();
		// Modificado 01 Septiembre 2009 antes int lenght = 11
		int lenght = 31;
		// Modificado 01 Septiembre 2009
		boolean FLDC100014 = true;
		boolean FLDC100028 = true;
		boolean FLDC100034 = true;
		boolean FLDC100035 = true;
		boolean FLDC100040 = true;
		boolean FLDC100013 = true;
		boolean FLDC100048 = true;
		// Modificado 26 Octubre 2009 antes FLDC100125
		boolean FLDC100300 = true;
		// Modificado 26 Octubre 2009
		boolean FLDC100016 = true;
		boolean FLDC100066 = true;
		boolean FLDC100071 = true;
		// Nuevo 01 Septiembre 2009
		boolean FLDC100029 = true;
		boolean FLDC100036 = true;
		boolean FLDC100037 = true;
		boolean FLDC100038 = true;
		boolean FLDC100041 = true;
		boolean FLDC100043 = true;
		boolean FLDC100044 = true;
		boolean FLDC100050 = true;
		boolean FLDC100030 = true;
		boolean FLDC100026 = true;
		boolean FLDC100027 = true;
		boolean FLDC100051 = true;
		boolean FLDC100056 = true;
		boolean FLDC100058 = true;
		boolean FLDC100062 = true;
		boolean FLDC100063 = true;
		boolean FLDC100064 = true;
		boolean FLDC100065 = true;
		boolean FLDC100067 = true;
		boolean FLDC100033 = true;
		// Nuevo 01 Septiembre 2009
		if (actualizacionMGB.getInstLoanAcctNum() == null || actualizacionMGB.getInstLoanAcctNum().equals("")) {
			FLDC100043 = false;
			lenght--;
		}

		if (actualizacionMGB.getCreditScore() == null || actualizacionMGB.getCreditScore().equals("")) {
			FLDC100033 = false;
			lenght--;
		}
		
		
		if (actualizacionMGB.getShortName() == null || actualizacionMGB.getShortName().equals("")) {
			FLDC100028 = false;
			lenght--;
		}
		if (actualizacionMGB.getCreditRating() == null ) {
			FLDC100014 = false;
			lenght--;
		}
		if (actualizacionMGB.getCycleCode() == null || actualizacionMGB.getCycleCode().equals("")) {
			FLDC100034 = false;
			lenght--;
		}
		if (actualizacionMGB.getAgentBank() == null || actualizacionMGB.getAgentBank().equals("")) {
			FLDC100035 = false;
			lenght--;
		}
		if (actualizacionMGB.getInsuranceType() == null || actualizacionMGB.getInsuranceType().equals("")) {
			FLDC100040 = false;
			lenght--;
		}
		if (actualizacionMGB.getCardHolderBirthDay() == null ) {
			FLDC100013 = false;
			lenght--;
		}
		if (actualizacionMGB.getAnualFee() == null ) {
			FLDC100048 = false;
			lenght--;
		}
		if (actualizacionMGB.getUserMiscOne() == null || actualizacionMGB.getUserMiscOne().equals("")) {
		// Modificado 26 Octubre 2009 antes FLDC100125
			FLDC100300 = false;
			lenght--;
		}

		if (actualizacionMGB.getUserAccountOne() == null || actualizacionMGB.getUserAccountOne().equals("")) {
			FLDC100016 = false;
			lenght--;
		}
		if (actualizacionMGB.getUserAccountThree() == null || actualizacionMGB.getUserAccountThree().equals("")) {
			FLDC100066 = false;
			lenght--;
		}
		if (actualizacionMGB.getIsBureau() == null || actualizacionMGB.getIsBureau().equals("")) {
			FLDC100071 = false;
			lenght--;
		}
		
		// Nuevo 01 Septiembre 2009
		if (actualizacionMGB.getTypeCode() == null ) {
			FLDC100029 = false;
			lenght--;
		}
		if (actualizacionMGB.getIssuingBank() == null || actualizacionMGB.getIssuingBank().equals("")) {
			FLDC100036 = false;
			lenght--;
		}
		if (actualizacionMGB.getBranchAccount() == null || actualizacionMGB.getBranchAccount().equals("")) {
			FLDC100037 = false;
			lenght--;
		}
		if (actualizacionMGB.getOfficer() == null || actualizacionMGB.getOfficer().equals("")) {
			FLDC100038 = false;
			lenght--;
		}
		if (actualizacionMGB.getMonthInsurance() == null || actualizacionMGB.getMonthInsurance().equals("")) {
			FLDC100041 = false;
			lenght--;
		}
		if (actualizacionMGB.getDateLastCreditReportCheck() == null || actualizacionMGB.getDateLastCreditReportCheck().equals("")) {
			FLDC100044 = false;
			lenght--;
		}
		if (actualizacionMGB.getStatementsProduced() == null || actualizacionMGB.getStatementsProduced().equals("")) {
			FLDC100050 = false;
			lenght--;
		}
		if (actualizacionMGB.getIsVisaphone() == null || actualizacionMGB.getIsVisaphone().equals("")) {
			FLDC100030 = false;
			lenght--;
		}
		if (actualizacionMGB.getOutstandingCards() == null || actualizacionMGB.getOutstandingCards().equals("")) {
			FLDC100026 = false;
			lenght--;
		}
		if (actualizacionMGB.getCardsReissued() == null || actualizacionMGB.getCardsReissued().equals("")) {
			FLDC100027 = false;
			lenght--;
		}
		if (actualizacionMGB.getExpirationDate() == null || actualizacionMGB.getExpirationDate().equals("")) {
			FLDC100051 = false;
			lenght--;
		}
		if (actualizacionMGB.getTypePlastic() == null || actualizacionMGB.getTypePlastic().equals("")) {
			FLDC100056 = false;
			lenght--;
		}
		if (actualizacionMGB.getIsEncodeAllAccounts() == null || actualizacionMGB.getIsEncodeAllAccounts().equals("")) {
			FLDC100058 = false;
			lenght--;
		}
		if (actualizacionMGB.getUserCodeOne() == null || actualizacionMGB.getUserCodeOne().equals("")) {
			FLDC100062 = false;
			lenght--;
		}
		if (actualizacionMGB.getUserCodeTwo() == null || actualizacionMGB.getUserCodeTwo().equals("")) {
			FLDC100063 = false;
			lenght--;
		}
		if (actualizacionMGB.getUserCodeThree() == null || actualizacionMGB.getUserCodeThree().equals("")) {
			FLDC100064 = false;
			lenght--;
		}
		if (actualizacionMGB.getUserAccountTwo() == null || actualizacionMGB.getUserAccountTwo().equals("")) {
			FLDC100065 = false;
			lenght--;
		}
		if (actualizacionMGB.getDateAccountOpened() == null || actualizacionMGB.getDateAccountOpened().equals("")) {
			FLDC100067 = false;
			lenght--;
		}
		// Nuevo 01 Septiembre 2009

		try {
			log.info("" + lenght);
			int current = 2;
			Formatter fmtX = new XFormatter();
			Formatter fmt9 = new NineFormatter();
			TPacket[] packet = new TPacket[lenght + 4];
			packet[0] = new TPacketImpl();
			packet[0].convertToDefaultHeader();
			packet[0].getElementRequest().getElement("accountID").setValue(actualizacionMGB.getAccount());
			packet[1] = new TPacketImpl();
			packet[1].convertToDefaultMaintHeader("CANU", "NYXXY");
			packet[1].getElementRequest().getElement("processID").setValue(getMessageName());

			if (FLDC100014) {
				fmtX.init("X(02)");
				packet[current] = new TPacketImpl();
				packet[current].convertToFLDC("100014", actualizacionMGB.getCreditRating(), fmtX); // Credit Rating ; 2 character	
				current++;
			}

			if (FLDC100028) {
				fmtX.init("X(15)");
				packet[current] = new TPacketImpl();
				packet[current].convertToFLDC("100028", actualizacionMGB.getShortName(), fmtX); // Short Name; 15 character	
				current++;
			}
			//credit score
			if (FLDC100033) {
				fmt9.init("9(04)");
				packet[current] = new TPacketImpl();
				packet[current].convertToFLDC("100033", actualizacionMGB.getCreditScore(), fmt9); // credit score
				current++;
			}

			if (FLDC100034) {
				fmt9.init("9(02)");
				packet[current] = new TPacketImpl();
				packet[current].convertToFLDC("100034", actualizacionMGB.getCycleCode(), fmt9); // Cycle Code ( 1- 35 vallid values) 2 numeric	
				current++;
			}

			if (FLDC100035) {
				fmt9.init("9(04)");
				packet[current] = new TPacketImpl();
				packet[current].convertToFLDC("100035", actualizacionMGB.getAgentBank(), fmt9); // Agent Bank 4 numeric	
				current++;
			}

			if (FLDC100040) {
				fmtX.init("X(01)");
				packet[current] = new TPacketImpl();
				packet[current].convertToFLDC("100040", actualizacionMGB.getInsuranceType(), fmtX); // Insurance Type valid values 1-5 , Y and N 1 char	
				current++;
			}

			if (FLDC100013) {
				fmtX.init("X(06)");
				packet[current] = new TPacketImpl();
				packet[current].convertToFLDC("100013", actualizacionMGB.getCardHolderBirthDay(), fmtX); //Card Holder Birth Date YYYYMM	
				current++;
			}

			if (FLDC100048) {
				fmtX.init("X(01)");
				packet[current] = new TPacketImpl();
				packet[current].convertToFLDC("100048", actualizacionMGB.getAnualFee(), fmtX); //Anual Fee 1 char	
				current++;
			}

			// Modificado 26 Octubre 2009 antes FLDC100125
			if (FLDC100300) {
				fmtX.init("X(01)");
				packet[current] = new TPacketImpl();
				// Modificado 26 Octubre 2009 antes 100125
				packet[current].convertToFLDC("100300", actualizacionMGB.getUserMiscOne(), fmtX); //User Misc 1; 1 char	
				current++;
			}

			if (FLDC100016) {
				fmt9.init("9(14)");
				packet[current] = new TPacketImpl();
				packet[current].convertToFLDC("100016", actualizacionMGB.getUserAccountOne(), fmt9); //User Account One; 14 char numeric	
				current++;
			}

			if (FLDC100066) {
				fmt9.init("9(14)");
				packet[current] = new TPacketImpl();
				packet[current].convertToFLDC("100066", actualizacionMGB.getUserAccountThree(), fmt9); //User Account Three; 14 char numeric
				current++;
			}
						
			if (FLDC100071) {
				fmtX.init("X(01)");
				packet[current] = new TPacketImpl();
				packet[current].convertToFLDC("100071", actualizacionMGB.getIsBureau(), fmtX); //Send Account to Credit Bureau; 1 char	
				current++;
			}
			
			// Nuevo 01 Septiembre 2009
			if (FLDC100029) {
				fmtX.init("X(01)");
				packet[current] = new TPacketImpl();
				packet[current].convertToFLDC("100029", actualizacionMGB.getTypeCode(), fmtX); //Type Code for Account (possible values are 1-9 and A-Z); 1 char
				current++;
			}
			
			if (FLDC100036) {
				fmt9.init("9(04)");
				packet[current] = new TPacketImpl();
				packet[current].convertToFLDC("100036", actualizacionMGB.getIssuingBank(), fmt9); //Issuing Bank; 4 char numeric
				current++;
			}
			
			if (FLDC100037) {
				fmt9.init("9(04)");
				packet[current] = new TPacketImpl();
				packet[current].convertToFLDC("100037", actualizacionMGB.getBranchAccount(), fmt9); //Branch for Account; 4 char numeric
				current++;
			}
			
			if (FLDC100038) {
				fmt9.init("9(04)");
				packet[current] = new TPacketImpl();
				packet[current].convertToFLDC("100038", actualizacionMGB.getOfficer(), fmt9); //Officer Code; 4 char numeric
				current++;
			}
			
			if (FLDC100041) {
				fmtX.init("X(02)");
				packet[current] = new TPacketImpl();
				packet[current].convertToFLDC("100041", actualizacionMGB.getMonthInsurance(), fmtX); //Month Insurance Due (MM; possible values are 01-12); 2 char
				current++;
			}
			
			if (FLDC100043) {
				fmt9.init("9(14)");
				packet[current] = new TPacketImpl();
				packet[current].convertToFLDC("100043", actualizacionMGB.getInstLoanAcctNum(), fmt9); //Date of Last Credit Report Check; 6 char
				current++;
			}
			
			if (FLDC100044) {
				fmtX.init("X(06)");
				packet[current] = new TPacketImpl();
				packet[current].convertToFLDC("100044", actualizacionMGB.getDateLastCreditReportCheck(), fmtX); //Date of Last Credit Report Check; 6 char
				current++;
			}
			
			if (FLDC100050) {
				fmtX.init("X(05)");
				packet[current] = new TPacketImpl();
				packet[current].convertToFLDC("100050", actualizacionMGB.getStatementsProduced(), fmtX); //Number of Statements Produced; 5 char
				current++;
			}
			
			if (FLDC100030) {
				fmtX.init("X(01)");
				packet[current] = new TPacketImpl();
				packet[current].convertToFLDC("100030", actualizacionMGB.getIsVisaphone(), fmtX); //Account has VISAPHONE (possible values are Y and N); 1 char
				current++;
			}
			
			if (FLDC100026) {
				fmt9.init("9(03)");
				packet[current] = new TPacketImpl();
				packet[current].convertToFLDC("100026", actualizacionMGB.getOutstandingCards(), fmt9); //Number of Outstanding Cards (depending on storage this field must be 1-9 or 1-255); 3 char numeric
				current++;
			}
			
			if (FLDC100027) {
				fmt9.init("9(03)");
				packet[current] = new TPacketImpl();
				packet[current].convertToFLDC("100027", actualizacionMGB.getCardsReissued(), fmt9); //Number of Cards to be Reissued (depending on storage this field must be 1-9 and 1-255); 3 char numeric
				current++;
			}
			
			if (FLDC100051) {
				fmtX.init("X(06)");
				packet[current] = new TPacketImpl();
				packet[current].convertToFLDC("100051", actualizacionMGB.getExpirationDate(), fmtX); //Expiration Date for Current Cards (YYYYMM); 6 char
				current++;
			}
			
			if (FLDC100056) {
				fmtX.init("X(01)");
				packet[current] = new TPacketImpl();
				packet[current].convertToFLDC("100056", actualizacionMGB.getTypePlastic(), fmtX); //Type of Plastic (possible values are SPACE, D, M, and V); 1 char
				current++;
			}
			
			if (FLDC100058) {
				fmtX.init("X(01)");
				packet[current] = new TPacketImpl();
				packet[current].convertToFLDC("100058", actualizacionMGB.getIsEncodeAllAccounts(), fmtX); //Encode All Accounts (possible values are Y and N); 1 char
				current++;
			}
			
			if (FLDC100062) {
				fmtX.init("X(04)");
				packet[current] = new TPacketImpl();
				packet[current].convertToFLDC("100062", actualizacionMGB.getUserCodeOne(), fmtX); //User Code 1; 4 char
				current++;
			}
			
			if (FLDC100063) {
				fmtX.init("X(04)");
				packet[current] = new TPacketImpl();
				packet[current].convertToFLDC("100063", actualizacionMGB.getUserCodeTwo(), fmtX); //User Code 2; 4 char
				current++;
			}
			
			if (FLDC100064) {
				fmtX.init("X(04)");
				packet[current] = new TPacketImpl();
				packet[current].convertToFLDC("100064", actualizacionMGB.getUserCodeThree(), fmtX); //User Code 3; 4 char
				current++;
			}
			
			if (FLDC100065) {
				fmt9.init("9(14)");
				packet[current] = new TPacketImpl();
				packet[current].convertToFLDC("100065", actualizacionMGB.getUserAccountTwo(), fmt9); //User Account Number Two; 14 char numeric
				current++;
			}

			if (FLDC100067) {
				fmtX.init("X(08)");
				packet[current] = new TPacketImpl();
				packet[current].convertToFLDC("100067", actualizacionMGB.getDateAccountOpened(), fmtX); //Date Account Opened (YYYYMMDD); 8 char
				current++;
			}			
			
			// Nuevo 01 Septiembre 2009
			
			packet[current] = new TPacketImpl();
			packet[current].convertToDefaultMEnd();
			current++;
			packet[current] = new TPacketImpl();
			packet[current].convertToDefaultTEnd();

			sendMessage(packet);
//			invalidatePacket(Classes.getClassArray(), actualizacionMGB.getAccount()); // Hasta que se dejen de usar pantallas negras para actualización
			return errorCode.getStatusMQResponse(packet, "MGB", actualizacionMGB.getUserName());
		}
		catch (Exception e) {
			log.error(e);
			throw new TotalMessageException("Problemas al ejecutar mantenimiento MGB", e);
		}
	}

}
