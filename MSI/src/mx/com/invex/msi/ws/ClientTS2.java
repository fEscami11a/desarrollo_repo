package mx.com.invex.msi.ws;

import javax.xml.ws.Holder;

import com.tsys.xmlmessaging.ch.InqAcctAvailTLPOpt;
import com.tsys.xmlmessaging.ch.InqAcctAvailTLPOptResponse;
import com.tsys.xmlmessaging.ch.InqAcctStatus;
import com.tsys.xmlmessaging.ch.InqAcctStatusResponse;
import com.tsys.xmlmessaging.ch.InqCreditLmt;
import com.tsys.xmlmessaging.ch.InqCreditLmtResponse;
import com.tsys.xmlmessaging.ch.InqCustAddr;
import com.tsys.xmlmessaging.ch.InqCustAddrResponse;
import com.tsys.xmlmessaging.ch.InqCustInfo;
import com.tsys.xmlmessaging.ch.InqCustInfoResponse;
import com.tsys.xmlmessaging.ch.InqCustomData;
import com.tsys.xmlmessaging.ch.InqCustomDataResponse;
import com.tsys.xmlmessaging.ch.InqGeneralAcct;
import com.tsys.xmlmessaging.ch.InqGeneralAcctResponse;
import com.tsys.xmlmessaging.ch.InqGeneralBal;
import com.tsys.xmlmessaging.ch.InqGeneralBalResponse;
import com.tsys.xmlmessaging.ch.InqPmtInfo;
import com.tsys.xmlmessaging.ch.InqPmtInfoResponse;
import com.tsys.xmlmessaging.ch.InqRealTimeAuthSys;
import com.tsys.xmlmessaging.ch.InqRealTimeAuthSysResponse;
import com.tsys.xmlmessaging.ch.InqRetailTrans;
import com.tsys.xmlmessaging.ch.InqRetailTransResponse;
import com.tsys.xmlmessaging.ch.InqTrans;
import com.tsys.xmlmessaging.ch.InqTransResponse;
import com.tsys.xmlmessaging.ch.TSYSXMLMessagingInquiry;
import com.tsys.xmlmessaging.ch.TSYSXMLMessagingSoapInquiry;
import com.tsys.xmlmessaging.ch.TSYSprofileType;
import com.tsys.xmlmessaging.ch2.ReqAcctTermTransfer;
import com.tsys.xmlmessaging.ch2.ReqAcctTermTransferResponse;
import com.tsys.xmlmessaging.ch2.TSYSXMLMessagingMaintenance;
import com.tsys.xmlmessaging.ch2.TSYSXMLMessagingSoapMaintenance;

public class ClientTS2 {
	static TSYSXMLMessagingInquiry servInq;
    static TSYSXMLMessagingSoapInquiry inqPort;
    static TSYSXMLMessagingMaintenance servMnt;
    static TSYSXMLMessagingSoapMaintenance mntPort;
	static{
		
	       servInq = new TSYSXMLMessagingInquiry();
	       inqPort = servInq.getTSYSXMLMessagingSoapInquiry03();
	       servMnt = new TSYSXMLMessagingMaintenance();
	       mntPort= servMnt.getTSYSXMLMessagingSoapMaintenance04();
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


	public InqGeneralAcctResponse inqGeneralAcct(TSYSprofileType profile,
			InqGeneralAcct inqGeneralAcct) {

		Holder<com.tsys.xmlmessaging.ch.TSYSprofileType> holder = new Holder<com.tsys.xmlmessaging.ch.TSYSprofileType>();
		holder.value=profile;
		return inqPort.inqGeneralAcct(inqGeneralAcct, holder);
	}
	
	public InqCustAddrResponse inqCustAddr(TSYSprofileType profile, InqCustAddr inqCustAddr){
		Holder<com.tsys.xmlmessaging.ch.TSYSprofileType> holder = new Holder<com.tsys.xmlmessaging.ch.TSYSprofileType>();
		holder.value=profile;
		return inqPort.inqCustAddr(inqCustAddr, holder);
	}
	
	public InqGeneralBalResponse inqGeneralBal(TSYSprofileType profile, InqGeneralBal inqGeneralBal){
		Holder<com.tsys.xmlmessaging.ch.TSYSprofileType> holder = new Holder<com.tsys.xmlmessaging.ch.TSYSprofileType>();
		holder.value=profile;
		return inqPort.inqGeneralBal(inqGeneralBal, holder);
	}
	
	public InqCreditLmtResponse inqCreditLmt(TSYSprofileType profile, InqCreditLmt inqCreditLmt){
		Holder<com.tsys.xmlmessaging.ch.TSYSprofileType> holder = new Holder<com.tsys.xmlmessaging.ch.TSYSprofileType>();
		holder.value=profile;
		return inqPort.inqCreditLmt(inqCreditLmt, holder);
	}
	
	public InqPmtInfoResponse inqPmtInfo(TSYSprofileType profile, InqPmtInfo inqPmtInfo){
		Holder<com.tsys.xmlmessaging.ch.TSYSprofileType> holder = new Holder<com.tsys.xmlmessaging.ch.TSYSprofileType>();
		holder.value=profile;
		return inqPort.inqPmtInfo(inqPmtInfo, holder);
	}
	
	public InqTransResponse inqTrans(TSYSprofileType profile,InqTrans inqTrans ){
		Holder<com.tsys.xmlmessaging.ch.TSYSprofileType> holder = new Holder<com.tsys.xmlmessaging.ch.TSYSprofileType>();
		holder.value=profile;
		return inqPort.inqTrans(inqTrans, holder);
	}
	
	public InqRealTimeAuthSysResponse inqRealTimeAuthSys(TSYSprofileType profile, InqRealTimeAuthSys inqRealTimeAuthSys){
		Holder<com.tsys.xmlmessaging.ch.TSYSprofileType> holder = new Holder<com.tsys.xmlmessaging.ch.TSYSprofileType>();
		holder.value=profile;
		return inqPort.inqRealTimeAuthSys(inqRealTimeAuthSys, holder);
	}
	
	public InqCustomDataResponse inqCustomData(TSYSprofileType profile,InqCustomData inqCustomData ){
		Holder<com.tsys.xmlmessaging.ch.TSYSprofileType> holder = new Holder<com.tsys.xmlmessaging.ch.TSYSprofileType>();
		holder.value=profile;
		return inqPort.inqCustomData(inqCustomData, holder);
	}
	
	public InqAcctAvailTLPOptResponse inqAcctAvailTLPOpt(TSYSprofileType profile, InqAcctAvailTLPOpt inqAcctAvailTLPOpt){
		Holder<com.tsys.xmlmessaging.ch.TSYSprofileType> holder = new Holder<com.tsys.xmlmessaging.ch.TSYSprofileType>();
		holder.value=profile;
		return inqPort.inqAcctAvailTLPOpt(inqAcctAvailTLPOpt, holder);
	}
	
	public ReqAcctTermTransferResponse reqAcctTermTransfer(com.tsys.xmlmessaging.ch2.TSYSprofileType profile, ReqAcctTermTransfer reqAcctTermTransfer){
		Holder<com.tsys.xmlmessaging.ch2.TSYSprofileType> holder = new Holder<com.tsys.xmlmessaging.ch2.TSYSprofileType>();
		holder.value=profile;
		return mntPort.reqAcctTermTransfer(reqAcctTermTransfer, holder);
	}
	
	public InqRetailTransResponse inqRetailTrans(TSYSprofileType profile,InqRetailTrans inqRetailTrans){
		Holder<com.tsys.xmlmessaging.ch.TSYSprofileType> holder = new Holder<com.tsys.xmlmessaging.ch.TSYSprofileType>();
		holder.value=profile;
		return inqPort.inqRetailTrans(inqRetailTrans, holder);
	}
}
