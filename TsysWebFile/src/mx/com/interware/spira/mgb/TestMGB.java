/*
 * Created on Nov 16, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package mx.com.interware.spira.mgb;
import org.apache.log4j.Logger;
/**
 * @author mdiaz
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TestMGB {
	private static Logger log = Logger.getLogger(TestMGB.class);

	public static void main(String[] args) {
		MGB mgb = new MGBImpl();
		ActualizacionMGB actualizacionMGB = new ActualizacionMGB();
		
//		actualizacionMGB.setAccount("6046442100000071");
		actualizacionMGB.setAccount("4461380000000257");
/*		actualizacionMGB.setShortName("PRUEBAS DATOS2");
		actualizacionMGB.setCreditRating(" ");
		actualizacionMGB.setCycleCode("12");
		actualizacionMGB.setAgentBank("5988");
		actualizacionMGB.setInsuranceType("N");
		actualizacionMGB.setCardHolderBirthDay("199001");
		actualizacionMGB.setAnualFee("N");
		actualizacionMGB.setIsBureau("N");
		actualizacionMGB.setUserMiscOne("N");
		actualizacionMGB.setUserAccountOne("");*/
//		actualizacionMGB.setCardHolderBirthDay("197909");

		// Nuevo 01 Septiembre 2009
		/*actualizacionMGB.setShortName("PRUEBAS DATOS2");
		actualizacionMGB.setTypeCode("");
		*/
		actualizacionMGB.setCreditRating("F1");
		//actualizacionMGB.setCycleCode("12");
		
		//actualizacionMGB.setAgentBank("0036");
		/*
		actualizacionMGB.setIssuingBank("5988");
		actualizacionMGB.setBranchAccount("0000");
		actualizacionMGB.setOfficer("1018");
		actualizacionMGB.setInsuranceType("N");
		actualizacionMGB.setMonthInsurance("00");
		actualizacionMGB.setCardHolderBirthDay("199001");
		actualizacionMGB.setDateLastCreditReportCheck("200805");
		actualizacionMGB.setAnualFee("N");
		actualizacionMGB.setStatementsProduced("00017");
		actualizacionMGB.setIsVisaphone("N");*/
		/*actualizacionMGB.setOutstandingCards("0");
		actualizacionMGB.setCardsReissued("0");
		actualizacionMGB.setExpirationDate("200912");
		actualizacionMGB.setTypePlastic("V");
		actualizacionMGB.setIsEncodeAllAccounts("Y");
		actualizacionMGB.setUserMiscOne("N");
		actualizacionMGB.setUserCodeOne("");
		actualizacionMGB.setUserCodeTwo("");
		actualizacionMGB.setUserCodeThree("");
		actualizacionMGB.setUserAccountOne("");
		actualizacionMGB.setUserAccountTwo("");
		actualizacionMGB.setUserAccountThree("");
		actualizacionMGB.setDateAccountOpened("20080513");*/
		//actualizacionMGB.setIsBureau("N");
		// Nuevo 01 Septiembre 2009
		
		actualizacionMGB.setUserName("LineaSpira");
		actualizacionMGB.setPassword("prodLS615#");

		String resp = "";

		try {
			resp = mgb.updateMGB(actualizacionMGB);
			log.info("Respuesta :" + resp);
		} catch (Exception e) {
			log.error(e);
		}

	}
}
