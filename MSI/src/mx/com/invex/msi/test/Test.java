package mx.com.invex.msi.test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.tsys.xmlmessaging.ch2.MntCustServiceAdjRequestType;
import com.tsys.xmlmessaging.ch2.MntCustServiceAdjRequestType.ActionInfo;
import com.tsys.xmlmessaging.ch2.MntCustServiceAdjRequestType.ActionInfo.Adj;
import com.tsys.xmlmessaging.ch2.MntCustServiceAdjRequestType.ActionInfo.AmtTran;
import com.tsys.xmlmessaging.ch2.MntCustServiceAdjRequestType.ActionInfo.MerchantInfo;
import com.tsys.xmlmessaging.ch2.MntCustServiceAdjRequestType.TranKey;
import com.tsys.xmlmessaging.ch2.MntCustServiceAdjResponse;

import mx.com.invex.msi.ws.ClientTS2;

public class Test {
public static void main(String[] args) {

	MntCustServiceAdjRequestType mntCustServiceAdjReq = new MntCustServiceAdjRequestType();
	mntCustServiceAdjReq.setVersion("2.12.0");
	mntCustServiceAdjReq.setKeyType("cardNbr");
	mntCustServiceAdjReq.setKey("4196910690228388");
	TranKey trankey = new TranKey();
	
	
	trankey.setDateStmtBegin(toxmlgreg("2015-12-12"));

	trankey.setDatePost(toxmlgreg("2015-12-29"));
	trankey.setTimePost(new BigInteger("2206043"));
	mntCustServiceAdjReq.setTranKey(trankey);
	
	ActionInfo actionInfo = new ActionInfo();
	actionInfo.setAction("Adjustment");
	actionInfo.setType("Initial");
	actionInfo.setTranCode("0106");
	AmtTran amtTran = new AmtTran();
	amtTran.setCode("MXN");
	amtTran.setValue(new BigDecimal(4538.00));
	actionInfo.setAmtTran(amtTran);
	MerchantInfo merchantInfo = new MerchantInfo();
	merchantInfo.setDBAName(String.format("%1$.25s", "AJUSTE A MSI BESAME PRENDAS INTIMAS MEXICO DF"));
	actionInfo.setMerchantInfo(merchantInfo);
	Adj adj = new Adj();
	com.tsys.xmlmessaging.ch2.Boolean b = new com.tsys.xmlmessaging.ch2.Boolean();
	b.setValue(false);
	adj.setForcePost(b);
	actionInfo.setAdj(adj);
	
	ActionInfo actInfo2 = new ActionInfo();
	actInfo2.setAction("Adjustment Offset");
	actInfo2.setType("Offset");
	actInfo2.setTranCode("0101");
	actInfo2.setAcctNbr("4196910690228388");
	actInfo2.setAmtTran(amtTran);
	actInfo2.setMerchantInfo(merchantInfo);
	Adj adj2= new Adj();
	adj2.setForcePost(b);
	adj2.setTLPType("S");
	String tlpopt= null;
	int meses =9;
	switch(meses){
		case 3:
			tlpopt ="1539";
			break;
		case 6:
			tlpopt="1540";
			break;
		case 9:
			tlpopt="1541";
			break;
		case 12:	
			tlpopt="1542";
			break;
		case 7:	
			tlpopt="1545";
			break;
		case 11:	
			tlpopt="1546";
			break;
		case 18:	
			tlpopt="1542";
			break;
	}
	System.out.println(tlpopt);
	adj2.setTLPOptSet(tlpopt);
	actInfo2.setAdj(adj2);
	
	List<ActionInfo> actInfos=mntCustServiceAdjReq.getActionInfo();
	actInfos.add(actionInfo);
	actInfos.add(actInfo2);
	
	ClientTS2 cts2 = new ClientTS2();
		MntCustServiceAdjResponse mntCustServiceAdjResp= cts2.mntCustServiceAdj(mntCustServiceAdjReq);
		String status = mntCustServiceAdjResp.getMntCustServiceAdjResult().getStatus();
		if(!"000".equals(status)){
			String msg = mntCustServiceAdjResp.getMntCustServiceAdjResult().getStatusMsg();
			System.out.println(msg);
			com.tsys.xmlmessaging.ch2.TSYSfaultType fault =mntCustServiceAdjResp.getMntCustServiceAdjResult().getFaults();
			List<com.tsys.xmlmessaging.ch2.TSYSfault> lfaulta =fault.getFault();
			for (com.tsys.xmlmessaging.ch2.TSYSfault sfault : lfaulta) {
				System.out.println(sfault.getStatus()+" "+ sfault.getFaultDesc());
			}
			
		}
		
	
}

public static com.tsys.xmlmessaging.ch2.Date toxmlgreg(String datestr){
	Date sd= new Date();
	SimpleDateFormat sdf= new SimpleDateFormat("yyyy-mm-dd");
	com.tsys.xmlmessaging.ch2.Date dateStmBegin = new com.tsys.xmlmessaging.ch2.Date();
	GregorianCalendar c = new GregorianCalendar();
	try {
		c.setTime(sdf.parse(datestr));
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	com.tsys.xmlmessaging.ch2.Date dt = new com.tsys.xmlmessaging.ch2.Date();
	XMLGregorianCalendar dtStmBegin;
	try {
		dtStmBegin = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		dt.setValue(dtStmBegin);
	} catch (DatatypeConfigurationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return dt;
}

}
