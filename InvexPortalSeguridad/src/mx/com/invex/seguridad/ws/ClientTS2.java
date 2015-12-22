package mx.com.invex.seguridad.ws;

import javax.xml.ws.Holder;

import com.tsys.xmlmessaging.ch.InqAcctStatus;
import com.tsys.xmlmessaging.ch.InqAcctStatusResponse;
import com.tsys.xmlmessaging.ch.InqCustInfo;
import com.tsys.xmlmessaging.ch.InqCustInfoResponse;
import com.tsys.xmlmessaging.ch.InqGeneralAcct;
import com.tsys.xmlmessaging.ch.InqGeneralAcctResponse;
import com.tsys.xmlmessaging.ch.TSYSXMLMessagingInquiry;
import com.tsys.xmlmessaging.ch.TSYSXMLMessagingSoapInquiry;
import com.tsys.xmlmessaging.ch.TSYSprofileType;

public class ClientTS2 {
	static TSYSXMLMessagingInquiry servInq;
    static TSYSXMLMessagingSoapInquiry inqPort;
	static{
		
	       servInq = new TSYSXMLMessagingInquiry();
	       inqPort = servInq.getTSYSXMLMessagingSoapInquiry03();
	}
	

	public InqAcctStatusResponse inqAcctStatus(
	TSYSprofileType profile, InqAcctStatus inqAcctStatus) {
		Holder<com.tsys.xmlmessaging.ch.TSYSprofileType> holder = new Holder<com.tsys.xmlmessaging.ch.TSYSprofileType>();
		holder.value=profile;
		return inqPort.inqAcctStatus(inqAcctStatus, holder);
	}


	public InqCustInfoResponse inqCustInfo(TSYSprofileType profile,
			InqCustInfo inqCustInfo) {
		
			Holder<com.tsys.xmlmessaging.ch.TSYSprofileType> holder = new Holder<com.tsys.xmlmessaging.ch.TSYSprofileType>();
			holder.value=profile;
			return inqPort.inqCustInfo(inqCustInfo, holder);
	}


	public InqGeneralAcctResponse InqGeneralAcct(TSYSprofileType profile,
			InqGeneralAcct inqGeneralAcct) {

		Holder<com.tsys.xmlmessaging.ch.TSYSprofileType> holder = new Holder<com.tsys.xmlmessaging.ch.TSYSprofileType>();
		holder.value=profile;
		return inqPort.inqGeneralAcct(inqGeneralAcct, holder);
	}
}
