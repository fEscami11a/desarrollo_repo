/*
 * Created on Nov 10, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package mx.com.interware.spira.mna;
import org.apache.log4j.Logger;
/**
 * @author mdiaz
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TestsMNA {
	private static Logger log = Logger.getLogger(TestsMNA.class);
	public static void main(String[] args) {
		MNA mna= new MNAImpl();
		    String account="5477360000006636"; //Mi cuenta								 
			//String account="6046442100000022";
			//rfc PAAE530601
			//4218110010845888
			String longName="";
   			String name="";
   			String name2="";
   			String address="";
			String address2="";
			String cityState="";//IZTACALCO, EM";
			String zipCode="";
			String homePhone="";
			String busiPhone="";
			String spouse="";
			String employer="";
			String socSecNum="";
			String stmtHoldCode="";
			String holdPlastic="";
			String cellPhone="";
			String curpIDNumber="";
			String emailAddress=""; 
			String guarantorOne=""; 
			String rfcGuarantorOne="";
			String guarantorTwo=""; 
			String rfcGuarantorTwo="";
			String guarantorThree="";
			String rfcGuarantorThree="";
			String embossName="";
			String personalRFC="";
			String taxID="";
			String busiExt = "";
			
			String userName = "LineaSpira";
			String accessKey ="prodLS615#" ;
			String resp="";
	 
		try{
			MNAParam param = new MNAParam();
			param.setAccount(account);
			//param.setPersonalRFC(personalRFC);
			param.setTaxID(personalRFC);
			//PAAE530601MYNTML
			//param.setName("ROBERTO*NEGRETE");
			mna.updateAccount(param);
			 
			 log.info("Respuesta :"+resp);
		}
		catch(Exception e){
			log.error(e);
		}
	}
}
