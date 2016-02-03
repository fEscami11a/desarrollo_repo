package mx.com.invex.ws.client;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import javax.jws.WebParam;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.Holder;

import com.tsys.xmlmessaging.ch.InqAcctStatus;
import com.tsys.xmlmessaging.ch.InqAcctStatusResponse;
import com.tsys.xmlmessaging.ch.InqCurrBalByTBAL;
import com.tsys.xmlmessaging.ch.InqCurrBalByTBALRequestType;
import com.tsys.xmlmessaging.ch.InqCurrBalByTBALResponse;
import com.tsys.xmlmessaging.ch.InqCustInfoResponse;
import com.tsys.xmlmessaging.ch.InqCustomDataResponse;
import com.tsys.xmlmessaging.ch.InqGeneralAcct;
import com.tsys.xmlmessaging.ch.InqGeneralAcctResponse;
import com.tsys.xmlmessaging.ch.TSYSXMLMessagingInquiry;
import com.tsys.xmlmessaging.ch.TSYSXMLMessagingSoapInquiry;
import com.tsys.xmlmessaging.ch2.AddCustIdentity;
import com.tsys.xmlmessaging.ch2.AddCustIdentityResponse;
import com.tsys.xmlmessaging.ch2.AddCustNotes;
import com.tsys.xmlmessaging.ch2.AddCustNotesResponse;
import com.tsys.xmlmessaging.ch2.MntAcctMiscInfo;
import com.tsys.xmlmessaging.ch2.MntAcctMiscInfoResponse;
import com.tsys.xmlmessaging.ch2.MntAcctStatus;
import com.tsys.xmlmessaging.ch2.MntAcctStatusResponse;
import com.tsys.xmlmessaging.ch2.MntBillingCycle;
import com.tsys.xmlmessaging.ch2.MntBillingCycleResponse;
import com.tsys.xmlmessaging.ch2.MntCreditLmt;
import com.tsys.xmlmessaging.ch2.MntCreditLmtResponse;
import com.tsys.xmlmessaging.ch2.MntCustAddr;
import com.tsys.xmlmessaging.ch2.MntCustAddrResponse;
import com.tsys.xmlmessaging.ch2.MntCustEmployerInfo;
import com.tsys.xmlmessaging.ch2.MntCustEmployerInfoResponse;
import com.tsys.xmlmessaging.ch2.MntCustIdentity;
import com.tsys.xmlmessaging.ch2.MntCustIdentityResponse;
import com.tsys.xmlmessaging.ch2.MntCustInfo;
import com.tsys.xmlmessaging.ch2.MntCustInfoResponse;
import com.tsys.xmlmessaging.ch2.MntCustPhnNbr;
import com.tsys.xmlmessaging.ch2.MntCustPhnNbrResponse;
import com.tsys.xmlmessaging.ch2.MntCustSecureInfo;
import com.tsys.xmlmessaging.ch2.MntCustSecureInfoResponse;
import com.tsys.xmlmessaging.ch2.MntCustomData;
import com.tsys.xmlmessaging.ch2.MntCustomDataResponse;
import com.tsys.xmlmessaging.ch2.MntDisclosureGrp;
import com.tsys.xmlmessaging.ch2.MntDisclosureGrpResponse;
import com.tsys.xmlmessaging.ch2.MntEmailAddr;
import com.tsys.xmlmessaging.ch2.MntEmailAddrResponse;
import com.tsys.xmlmessaging.ch2.MntEmbossingInfo;
import com.tsys.xmlmessaging.ch2.MntEmbossingInfoResponse;
import com.tsys.xmlmessaging.ch2.MntName;
import com.tsys.xmlmessaging.ch2.MntNameResponse;
import com.tsys.xmlmessaging.ch2.ReqAcctTermTransfer;
import com.tsys.xmlmessaging.ch2.ReqAcctTermTransferResponse;
import com.tsys.xmlmessaging.ch2.ReqCreditBureau;
import com.tsys.xmlmessaging.ch2.ReqCreditBureauResponse;
import com.tsys.xmlmessaging.ch2.ReqPINMailer;
import com.tsys.xmlmessaging.ch2.ReqPINMailerResponse;
import com.tsys.xmlmessaging.ch2.TSYSXMLMessagingMaintenance;
import com.tsys.xmlmessaging.ch2.TSYSXMLMessagingSoapMaintenance;
import com.tsys.xmlmessaging.ch2.TSYSprofileType;

public class ClientTs2 {


	
	
	public InqAcctStatusResponse inqAcctStatus(
			com.tsys.xmlmessaging.ch.TSYSprofileType profile, InqAcctStatus inqAcctStatus) {
				Holder<com.tsys.xmlmessaging.ch.TSYSprofileType> holder = new Holder<com.tsys.xmlmessaging.ch.TSYSprofileType>();
				holder.value=profile;
				return inqPort.inqAcctStatus(inqAcctStatus, holder);
			}
	
//	public static String getXMLString(Class type,Object obj,String alias){
//		 QNameMap qmap = new QNameMap();
//		 qmap.setDefaultNamespace("urn://tsys.com/xmlmessaging/CH");
//		 qmap.setDefaultPrefix("ch");
//		 StaxDriver staxDriver = new StaxDriver(qmap); 
//		 XStream xstream = new XStream(staxDriver);
//		 xstream.autodetectAnnotations(true);
//		 xstream.alias(alias, type);
//		 StringWriter stringWriter = new StringWriter();
//		    XMLStreamWriter writer=null;
//			try {
//				writer = XMLOutputFactory.newFactory().createXMLStreamWriter(stringWriter);
//			} catch (XMLStreamException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (FactoryConfigurationError e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		    StaxWriter sWriter=null;
//			try {
//				sWriter = staxDriver .createStaxWriter(writer, false);
//			} catch (XMLStreamException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		    xstream.marshal(obj, sWriter);
//		    sWriter.close();
//		    String xml = stringWriter.toString();
//		return xml;
//	}
	
	public ReqCreditBureauResponse reqCreditBureau(TSYSprofileType profile, ReqCreditBureau reqCreditBureau){
		       Holder<TSYSprofileType> holder = new Holder<TSYSprofileType>();
	       holder.value=profile;
	        return port.reqCreditBureau(reqCreditBureau, holder);
		
	}
	static TSYSXMLMessagingMaintenance service;
    static TSYSXMLMessagingSoapMaintenance port;
    
    static TSYSXMLMessagingInquiry servInq;
    static TSYSXMLMessagingSoapInquiry inqPort;
	static{
		 service= new TSYSXMLMessagingMaintenance();
	       port=service.getTSYSXMLMessagingSoapMaintenance04();
	       servInq = new TSYSXMLMessagingInquiry();
	       inqPort = servInq.getTSYSXMLMessagingSoapInquiry03();
	}
	
	public InqCustInfoResponse inqCustInfo(com.tsys.xmlmessaging.ch.TSYSprofileType profile,com.tsys.xmlmessaging.ch.InqCustInfo inqCustInfo){
		Holder<com.tsys.xmlmessaging.ch.TSYSprofileType> holder = new Holder<com.tsys.xmlmessaging.ch.TSYSprofileType>();
		holder.value=profile;
		return inqPort.inqCustInfo(inqCustInfo, holder);
	}
	
	public InqCurrBalByTBALResponse inqCurrBalByTBAL(String cuenta){
		 com.tsys.xmlmessaging.ch.TSYSprofileType tp2 = new com.tsys.xmlmessaging.ch.TSYSprofileType();
		 tp2.setClientID("7401");
		 tp2.setUserID("invdev");
		 tp2.setVendorID("00000000");
		 InqCurrBalByTBAL inqCurrBalByTBAL = new InqCurrBalByTBAL();
		 InqCurrBalByTBALRequestType req = new InqCurrBalByTBALRequestType();
		 req.setKey(cuenta);
		 req.setKeyType("cardNbr");
		 req.setVersion("3.0.0");
		 inqCurrBalByTBAL.setInqCurrBalByTBALRequest(req);
		Holder<com.tsys.xmlmessaging.ch.TSYSprofileType> holder = new Holder<com.tsys.xmlmessaging.ch.TSYSprofileType>();
		holder.value=tp2;
		return inqPort.inqCurrBalByTBAL(inqCurrBalByTBAL, holder);
	}
	public InqCustomDataResponse inqCustomData(com.tsys.xmlmessaging.ch.TSYSprofileType profile,com.tsys.xmlmessaging.ch.InqCustomData inqCustomData){
		Holder<com.tsys.xmlmessaging.ch.TSYSprofileType> holder = new Holder<com.tsys.xmlmessaging.ch.TSYSprofileType>();
		holder.value=profile;
		return inqPort.inqCustomData(inqCustomData,  holder);
	}
	
	public InqGeneralAcctResponse inqGeneralAcct(com.tsys.xmlmessaging.ch.TSYSprofileType profile, InqGeneralAcct inqGeneralAcct){
		Holder<com.tsys.xmlmessaging.ch.TSYSprofileType> holder = new Holder<com.tsys.xmlmessaging.ch.TSYSprofileType>();
		holder.value=profile;
		return inqPort.inqGeneralAcct(inqGeneralAcct, holder);
	}
	
	public MntCustAddrResponse mntCustAddr(TSYSprofileType profile,MntCustAddr mntCustAddr){
		 Holder<TSYSprofileType> holder = new Holder<TSYSprofileType>();
	       holder.value=profile;
	       return port.mntCustAddr(mntCustAddr, holder);
	}

	public MntCustPhnNbrResponse mntCustPhnNbr(TSYSprofileType profile,MntCustPhnNbr mntCustPhnNbr){
		 Holder<TSYSprofileType> holder = new Holder<TSYSprofileType>();
	       holder.value=profile;
	       return port.mntCustPhnNbr(mntCustPhnNbr, holder);
	}
	
	public MntCustEmployerInfoResponse mntCustEmployerInfo(TSYSprofileType profile,MntCustEmployerInfo mntCustEmployerInfo){
		Holder<TSYSprofileType> holder = new Holder<TSYSprofileType>();
	       holder.value=profile;
	       return port.mntCustEmployerInfo(mntCustEmployerInfo, holder);
	}
	
	public ReqPINMailerResponse reqPINMailer(TSYSprofileType profile,ReqPINMailer reqPINMailer){
		Holder<TSYSprofileType> holder = new Holder<TSYSprofileType>();
	       holder.value=profile;
	       return port.reqPINMailer(reqPINMailer, holder);
	}
	
	public MntCreditLmtResponse mntCreditLmt(TSYSprofileType profile, MntCreditLmt mntCreditLmt){
		Holder<TSYSprofileType> holder = new Holder<TSYSprofileType>();
	       holder.value=profile;
	       return port.mntCreditLmt(mntCreditLmt, holder);
	}
	
	public MntBillingCycleResponse mntBillingCycle(TSYSprofileType profile, MntBillingCycle mntBillingCycle){
		Holder<TSYSprofileType> holder = new Holder<TSYSprofileType>();
	       holder.value=profile;
	       return port.mntBillingCycle(mntBillingCycle, holder);
	}
	
	public MntAcctMiscInfoResponse mntAcctMiscInfo(TSYSprofileType profile, MntAcctMiscInfo mntAcctMiscInfo){
		Holder<TSYSprofileType> holder = new Holder<TSYSprofileType>();
	       holder.value=profile;
	       return port.mntAcctMiscInfo(mntAcctMiscInfo, holder);
	}
	
	public MntCustomDataResponse mntCustomData(TSYSprofileType profile, MntCustomData mntCustomData){
		Holder<TSYSprofileType> holder = new Holder<TSYSprofileType>();
	       holder.value=profile;
	       return port.mntCustomData(mntCustomData, holder);
	}
	
	public MntCustInfoResponse mntCustInfo(TSYSprofileType profile, MntCustInfo mntCustInfo){
		Holder<TSYSprofileType> holder = new Holder<TSYSprofileType>();
	       holder.value=profile;
	       return port.mntCustInfo(mntCustInfo, holder);
	}
	
	public AddCustIdentityResponse addCustIdentity(TSYSprofileType profile, AddCustIdentity addCustIdentity){
		Holder<TSYSprofileType> holder = new Holder<TSYSprofileType>();
	       holder.value=profile;
	       return port.addCustIdentity(addCustIdentity, holder);
	}
	
	public MntEmailAddrResponse mntEmailAddr(TSYSprofileType profile, MntEmailAddr mntEmailAddr){
		Holder<TSYSprofileType> holder = new Holder<TSYSprofileType>();
	       holder.value=profile;
	       return port.mntEmailAddr(mntEmailAddr, holder);
	}
	
	public MntAcctStatusResponse mntAcctStatus(TSYSprofileType profile,MntAcctStatus mntAcctStatus){
		Holder<TSYSprofileType> holder = new Holder<TSYSprofileType>();
	       holder.value=profile;
	       return port.mntAcctStatus(mntAcctStatus, holder);
	}
	
	public MntNameResponse mntName(TSYSprofileType profile,MntName mntName){
		Holder<TSYSprofileType> holder = new Holder<TSYSprofileType>();
	       holder.value=profile;
	       return port.mntName(mntName, holder);
	}
	
	public MntCustSecureInfoResponse mntCustSecureInfo(TSYSprofileType profile,MntCustSecureInfo mntCustSecureInfo){
		Holder<TSYSprofileType> holder = new Holder<TSYSprofileType>();
	       holder.value=profile;
	       return port.mntCustSecureInfo(mntCustSecureInfo, holder);
	}
	
	public MntDisclosureGrpResponse mntDisclosureGrp(TSYSprofileType profile,MntDisclosureGrp mntDisclosureGrp){
		Holder<TSYSprofileType> holder = new Holder<TSYSprofileType>();
	       holder.value=profile;
	       return port.mntDisclosureGrp(mntDisclosureGrp, holder);
	}
	
	public MntEmbossingInfoResponse mntEmbossingInfo(TSYSprofileType profile, MntEmbossingInfo mntEmbossingInfo){
		try {   
		      Holder<TSYSprofileType> holder = new Holder<TSYSprofileType>();
		       holder.value=profile;
		        return port.mntEmbossingInfo(mntEmbossingInfo, holder);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public AddCustNotesResponse addCustNotes(TSYSprofileType profile, AddCustNotes addCustNotes){
		try {   
		      Holder<TSYSprofileType> holder = new Holder<TSYSprofileType>();
		       holder.value=profile;
		        return port.addCustNotes(addCustNotes, holder);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public ReqAcctTermTransferResponse reqAcctTermTransfer(com.tsys.xmlmessaging.ch2.TSYSprofileType profile, ReqAcctTermTransfer reqAcctTermTransfer){
		Holder<com.tsys.xmlmessaging.ch2.TSYSprofileType> holder = new Holder<com.tsys.xmlmessaging.ch2.TSYSprofileType>();
		holder.value=profile;
		return port.reqAcctTermTransfer(reqAcctTermTransfer, holder);
	}

}
