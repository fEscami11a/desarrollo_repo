/*
 * Created on Nov 10, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package mx.com.interware.spira.mna;

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
 * Class MNAImpl.java
 * Package mx.com.interware.spira.web.MNA
 * Created on Oct 18, 2005
 * @author Marcos Díaz
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
public class MNAImpl extends AbstractTSYSMessage implements MNA {
	private static Logger log = Logger.getLogger(MNAImpl.class);

	protected String getMessageName() {
		return "    ";
	}
	
	public synchronized String updateAccount(MNAParam param) throws TotalMessageException{
		PacketErrorCode errorCode= new PacketErrorCode();
		log.debug("Servicio MQ Tsys MNA");

		int lenght = 27;	
		
		boolean FLDC001001 = true;
		boolean FLDC001002 = true;
		boolean FLDC001003 = true;
		boolean FLDC001004 = true;
		//boolean FLDC001005 = true;
		boolean FLDC001044 = true;
		boolean FLDC001006 = true;
		boolean FLDC001007 = true;
		boolean FLDC001008 = true;
		boolean FLDC001030 = true;
		
		boolean FLDC001009 = true;
		boolean FLDC001010 = true;
		boolean FLDC001014 = true;
		boolean FLDC001015 = true;
		boolean FLDC001020 = true;
		
		// nuevo 
		boolean FLDC001032 = true;
		
		boolean FLDC001033 = true;
		boolean FLDC001034 = true;
		boolean FLDC001035 = true;
		boolean FLDC001036 = true;
		boolean FLDC001037 = true;
		boolean FLDC001038 = true;
		boolean FLDC001039 = true;
		boolean FLDC001040 = true;
		boolean FLDC001041 = true;
		boolean FLDC001042 = true;
		boolean FLDC001043 = true;
		boolean FLDC001045 = true;		
		
		
		if(param.getLongName()==null || param.getLongName().equals("")){			FLDC001030=false;lenght--;}
		if(param.getName()==null || param.getName().equals("")){						FLDC001001=false;lenght--;}
		if(param.getName2()==null || param.getName2().equals("")){					FLDC001002=false;lenght--;}
		if(param.getAddress()==null || param.getAddress().equals("")){				FLDC001003=false;lenght--;}
		if(param.getAddress2()==null || param.getAddress2().equals("")){			FLDC001004=false;lenght--;}
		if(param.getCityState()==null || param.getCityState().equals("")){			FLDC001044=false;lenght--;}
		if(param.getZipCode()==null || param.getZipCode().equals("")){				FLDC001006=false;lenght--;}
		if(param.getHomePhone()==null || param.getHomePhone().equals("")){	FLDC001007=false;lenght--;}
		if(param.getBusiPhone()==null || param.getBusiPhone().equals("")){		FLDC001008=false;lenght--;}

		if(param.getSpouse()==null || param.getSpouse().equals("")){					FLDC001009=false;lenght--;}
		if(param.getEmployer()==null || param.getEmployer().equals("")){			FLDC001010=false;lenght--;}
		if(param.getSocSecNum()==null || param.getSocSecNum().equals("")){	FLDC001014=false;lenght--;}
		if(param.getStmtHoldCode()==null || param.getStmtHoldCode().equals("")){	FLDC001015=false;lenght--;}
		if(param.getHoldPlastic()==null || param.getHoldPlastic().equals("")){		FLDC001020=false;lenght--;}
		if(param.getCellPhone()==null || param.getCellPhone().equals("")){		FLDC001033=false;lenght--;}
		if(param.getCurpIDNumber()==null || param.getCurpIDNumber().equals("")){		FLDC001034=false;lenght--;}
		if(param.getEmailAddress()==null || param.getEmailAddress().equals("")){		FLDC001035=false;lenght--;}
		if(param.getGuarantorOne()==null || param.getGuarantorOne().equals("")){		FLDC001036=false;lenght--;}
		if(param.getRfcGuarantorOne()==null || param.getRfcGuarantorOne().equals("")){		FLDC001037=false;lenght--;}
		if(param.getGuarantorTwo()==null || param.getGuarantorTwo().equals("")){		FLDC001038=false;lenght--;}
		if(param.getRfcGuarantorTwo()==null || param.getRfcGuarantorTwo().equals("")){		FLDC001039=false;lenght--;}
		if(param.getGuarantorThree()==null || param.getGuarantorThree().equals("")){		FLDC001040=false;lenght--;}
		if(param.getRfcGuarantorThree()==null || param.getRfcGuarantorThree().equals("")){		FLDC001041=false;lenght--;}
		if(param.getEmbossName()==null || param.getEmbossName().equals("")){		FLDC001042=false;lenght--;}
		if(param.getPersonalRFC()==null || param.getPersonalRFC().equals("")){		FLDC001043=false;lenght--;}
		if(param.getTaxID()==null || param.getTaxID().equals("")){		FLDC001045=false;lenght--;}
		if (param.getBusiExt()==null || param.getBusiExt().equals("")){	FLDC001032=false;lenght--;}
		
		try {
			log.info(""+lenght);
			int current=2;
			Formatter fmtX = new XFormatter();
			Formatter fmt9 = new NineFormatter();
			TPacket[] packet = new TPacket[lenght+4];
			packet[0] = new TPacketImpl();
			packet[0].convertToDefaultHeader();
			packet[0].getElementRequest().getElement("accountID").setValue(param.getAccount());
			packet[1] = new TPacketImpl();
			packet[1].convertToDefaultMaintHeader("CANU","YYYYN");
			packet[1].getElementRequest().getElement("processID").setValue(getMessageName());
	
			if(FLDC001001){
				fmtX.init("X(25)");
				packet[current] = new TPacketImpl();
				packet[current].convertToFLDC("001001",param.getName(),fmtX);// Name Line One; 25 character	
				current++;				
			}

			if(FLDC001002){
				fmtX.init("X(25)");
				packet[current] = new TPacketImpl();
				packet[current].convertToFLDC("001002",param.getName2(),fmtX);// Name Line Two; 25 character	
				current++;				
			}

			if(FLDC001003){
				fmtX.init("X(36)");
				packet[current] = new TPacketImpl();
				packet[current].convertToFLDC("001003",param.getAddress(),fmtX);// Address Line One; 36 character	
				current++;				
			}

			if(FLDC001004){
				fmtX.init("X(36)");
				packet[current] = new TPacketImpl();
				packet[current].convertToFLDC("001004",param.getAddress2(),fmtX);// Address Line Two; 36 character	
				current++;				
			}

			if(FLDC001044){
				fmtX.init("X(27)");
				packet[current] = new TPacketImpl();
				packet[current].convertToFLDC("001044",param.getCityState(),fmtX);// City State ; 36 character	
				current++;				
			}


			if(FLDC001006){
				fmt9.init("9(09)");
				packet[current] = new TPacketImpl();
				packet[current].convertToFLDC("001006",param.getZipCode(),fmt9);// Zip Code ; 9 Numeric	
				current++;				
			}

			if(FLDC001007){
				fmt9.init("9(10)");
				packet[current] = new TPacketImpl();
				packet[current].convertToFLDC("001007",param.getHomePhone(),fmt9);// Home Phone ; 10 Numeric	
				current++;				
			}

			if(FLDC001008){
				fmt9.init("9(10)");
				packet[current] = new TPacketImpl();
				packet[current].convertToFLDC("001008",param.getBusiPhone(),fmt9);// Business Phone ; 10 Numeric	
				current++;				
			}

			if(FLDC001009){
				fmtX.init("X(15)");
				packet[current] = new TPacketImpl();
				packet[current].convertToFLDC("001009",param.getSpouse(),fmtX);// Spouse ; 15 Char	
				current++;				
			}

			if(FLDC001010){
				fmtX.init("X(15)");
				packet[current] = new TPacketImpl();
				packet[current].convertToFLDC("001010",param.getEmployer(),fmtX);// Employer ; 15 Chars	
				current++;				
			}

			if(FLDC001014){
				fmt9.init("9(09)");
				packet[current] = new TPacketImpl();
				packet[current].convertToFLDC("001014",param.getSocSecNum(),fmt9);// Social Security Number ; 9 Numeric	
				current++;				
			}

			if(FLDC001015){
				fmt9.init("9(03)");
				packet[current] = new TPacketImpl();
				packet[current].convertToFLDC("001015",param.getStmtHoldCode(),fmt9);// Statement Hold Code (0-255) ; 9 Numeric	
				current++;				
			}

			if(FLDC001020){
				fmtX.init("X(01)");
				packet[current] = new TPacketImpl();
				packet[current].convertToFLDC("001020",param.getHoldPlastic(),fmtX);// Hold Plastic (Y or N) ; 1 Char	
				current++;				
			}
			
//			if(FLDC001032){
//			fmtX.init("X(05)");
//			packet[current] = new TPacketImpl();
//			packet[current].convertToFLDC("001032",busiExt,fmtX);// Business Extension ; 05 Char		
//			current++;				
//		}		

		if(FLDC001032){
		fmt9.init("9(05)");
		packet[current] = new TPacketImpl();
		packet[current].convertToFLDC("001032",param.getBusiExt(),fmt9);// Business Extension ; 05 Char		
		current++;				
	}								

			if(FLDC001030){
				fmtX.init("X(40)");
				packet[current] = new TPacketImpl();
				packet[current].convertToFLDC("001030",param.getLongName(),fmtX);// Long Name ; 30 Numeric	
				current++;				
			}		
			
//			if(FLDC001033){
//				fmtX.init("X(13)");
//				packet[current] = new TPacketImpl();
//				packet[current].convertToFLDC("001033",cellPhone,fmtX);// Cell Phone Number ; 13 Char	
//				current++;				
//			}		

			if(FLDC001033){
				fmt9.init("9(13)");
				packet[current] = new TPacketImpl();
				packet[current].convertToFLDC("001033",param.getCellPhone(),fmt9);// Cell Phone Number ; 13 Char	
				current++;
			}				

			if(FLDC001034){
				fmtX.init("X(18)");
				packet[current] = new TPacketImpl();
				packet[current].convertToFLDC("001034",param.getCurpIDNumber(),fmtX);// CURP ID Number ; 18 Char	
				current++;				
			}				

			if(FLDC001035){
				fmtX.init("X(60)");
				packet[current] = new TPacketImpl();
				packet[current].convertToFLDC("001035",param.getEmailAddress(),fmtX);// Email Address ; 60 Char	
				current++;				
			}				

			if(FLDC001036){
				fmtX.init("X(25)");
				packet[current] = new TPacketImpl();
				packet[current].convertToFLDC("001036",param.getGuarantorOne(),fmtX);// Guarantor 1 ; 25 Char	
				current++;				
			}				

			if(FLDC001037){
				fmtX.init("X(13)");
				packet[current] = new TPacketImpl();
				packet[current].convertToFLDC("001037",param.getRfcGuarantorOne(),fmtX);// Guarantor RFC 1 ; 13 Char	
				current++;				
			}				

			if(FLDC001038){
				fmtX.init("X(25)");
				packet[current] = new TPacketImpl();
				packet[current].convertToFLDC("001038",param.getGuarantorTwo(),fmtX);// Guarantor 2 ; 25 Char	
				current++;				
			}				

			if(FLDC001039){
				fmtX.init("X(13)");
				packet[current] = new TPacketImpl();
				packet[current].convertToFLDC("001039",param.getRfcGuarantorTwo(),fmtX);// Guarantor RFC 2 ; 13 Char	
				current++;				
			}				

			if(FLDC001040){
				fmtX.init("X(25)");
				packet[current] = new TPacketImpl();
				packet[current].convertToFLDC("001040",param.getGuarantorThree(),fmtX);// Guarantor 3 ; 25 Char	
				current++;				
			}				

			if(FLDC001041){
				fmtX.init("X(13)");
				packet[current] = new TPacketImpl();
				packet[current].convertToFLDC("001041",param.getRfcGuarantorThree(),fmtX);// Guarantor RFC 3 ; 13 Char	
				current++;				
			}				

			if(FLDC001042){
				fmtX.init("X(26)");
				packet[current] = new TPacketImpl();
				packet[current].convertToFLDC("001042",param.getEmbossName(),fmtX);// Emboss Name ; 26 Char	
				current++;				
			}				

			if(FLDC001043){
				fmtX.init("X(13)");
				packet[current] = new TPacketImpl();
				packet[current].convertToFLDC("001043",param.getPersonalRFC(),fmtX);// RFC Personal ; 13 Char	
				current++;				
			}				

			if(FLDC001045){
				fmtX.init("X(13)");
				packet[current] = new TPacketImpl();
				packet[current].convertToFLDC("001045",param.getTaxID(),fmtX);// Tax ID ; 13 Char	
				current++;				
			}				
																													
			packet[current] = new TPacketImpl();
			packet[current].convertToDefaultMEnd();
			current++;
			packet[current] = new TPacketImpl();
			packet[current].convertToDefaultTEnd();

					
			sendMessage(packet);
//			invalidatePacket(Classes.getClassArray(), account);			
			return errorCode.getStatusMQResponse(packet,"MNA",param.getUserName());
		}
		catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw new TotalMessageException("Problemas al ejecutar mantenimiento MNA",e);
		}
	}

}