package mx.com.invex.msi.ws;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Holder;

import org.apache.log4j.Logger;

import com.tsys.xmlmessaging.ch.IASstatusCodeResponseDataType;
import com.tsys.xmlmessaging.ch.ICAaddrInfoResponseDataType;
import com.tsys.xmlmessaging.ch.ICIcustInfoResponseDataType;
import com.tsys.xmlmessaging.ch.IGAacctGeneralInfoResponseDataType;
import com.tsys.xmlmessaging.ch.IGBgeneralBalInfoResponseDataType;
import com.tsys.xmlmessaging.ch.IPIpmtInfoResponseDataType;
import com.tsys.xmlmessaging.ch.IRTretailTransResponseDataType;
import com.tsys.xmlmessaging.ch.ITAcustomDataResponseDataType;
import com.tsys.xmlmessaging.ch.ITRtranDetailResponseDataType;
import com.tsys.xmlmessaging.ch.InqAcctAvailTLPOpt;
import com.tsys.xmlmessaging.ch.InqAcctAvailTLPOptResponse;
import com.tsys.xmlmessaging.ch.InqAcctStatus;
import com.tsys.xmlmessaging.ch.InqAcctStatusRequestType;
import com.tsys.xmlmessaging.ch.InqAcctStatusResponse;
import com.tsys.xmlmessaging.ch.InqAcctStatusResponseType;
import com.tsys.xmlmessaging.ch.InqCreditLmt;
import com.tsys.xmlmessaging.ch.InqCreditLmtRequestType;
import com.tsys.xmlmessaging.ch.InqCreditLmtResponse;
import com.tsys.xmlmessaging.ch.InqCreditLmtResponseType;
import com.tsys.xmlmessaging.ch.InqCustAddr;
import com.tsys.xmlmessaging.ch.InqCustAddrRequestType;
import com.tsys.xmlmessaging.ch.InqCustAddrResponse;
import com.tsys.xmlmessaging.ch.InqCustAddrResponseType;
import com.tsys.xmlmessaging.ch.InqCustInfo;
import com.tsys.xmlmessaging.ch.InqCustInfoRequestType;
import com.tsys.xmlmessaging.ch.InqCustInfoResponse;
import com.tsys.xmlmessaging.ch.InqCustInfoResponseType;
import com.tsys.xmlmessaging.ch.InqCustomData;
import com.tsys.xmlmessaging.ch.InqCustomDataRequestType;
import com.tsys.xmlmessaging.ch.InqCustomDataRequestType.CustomDataCodes;
import com.tsys.xmlmessaging.ch.InqCustomDataResponse;
import com.tsys.xmlmessaging.ch.InqCustomDataResponseType;
import com.tsys.xmlmessaging.ch.InqGeneralAcct;
import com.tsys.xmlmessaging.ch.InqGeneralAcctRequestType;
import com.tsys.xmlmessaging.ch.InqGeneralAcctResponse;
import com.tsys.xmlmessaging.ch.InqGeneralAcctResponseType;
import com.tsys.xmlmessaging.ch.InqGeneralBal;
import com.tsys.xmlmessaging.ch.InqGeneralBalRequestType;
import com.tsys.xmlmessaging.ch.InqGeneralBalResponse;
import com.tsys.xmlmessaging.ch.InqGeneralBalResponseType;
import com.tsys.xmlmessaging.ch.InqPmtInfo;
import com.tsys.xmlmessaging.ch.InqPmtInfoRequestType;
import com.tsys.xmlmessaging.ch.InqPmtInfoResponse;
import com.tsys.xmlmessaging.ch.InqPmtInfoResponseType;
import com.tsys.xmlmessaging.ch.InqRealTimeAuthSys;
import com.tsys.xmlmessaging.ch.InqRealTimeAuthSysRequestType;
import com.tsys.xmlmessaging.ch.InqRealTimeAuthSysResponse;
import com.tsys.xmlmessaging.ch.InqRealTimeAuthSysResponseType;
import com.tsys.xmlmessaging.ch.InqRetailTrans;
import com.tsys.xmlmessaging.ch.InqRetailTransRequestType;
import com.tsys.xmlmessaging.ch.InqRetailTransResponse;
import com.tsys.xmlmessaging.ch.InqRetailTransResponseType;
import com.tsys.xmlmessaging.ch.InqTrans;
import com.tsys.xmlmessaging.ch.InqTransRequestType;
import com.tsys.xmlmessaging.ch.InqTransResponse;
import com.tsys.xmlmessaging.ch.InqTransResponseType;
import com.tsys.xmlmessaging.ch.TSYSXMLMessagingInquiry;
import com.tsys.xmlmessaging.ch.TSYSXMLMessagingSoapInquiry;
import com.tsys.xmlmessaging.ch.TSYSfault;
import com.tsys.xmlmessaging.ch.TSYSfaultType;
import com.tsys.xmlmessaging.ch.TSYSprofileType;
import com.tsys.xmlmessaging.ch2.MntCustServiceAdj;
import com.tsys.xmlmessaging.ch2.MntCustServiceAdjRequestType;
import com.tsys.xmlmessaging.ch2.MntCustServiceAdjResponse;
import com.tsys.xmlmessaging.ch2.ReqAcctTermTransfer;
import com.tsys.xmlmessaging.ch2.ReqAcctTermTransferResponse;
import com.tsys.xmlmessaging.ch2.TSYSXMLMessagingMaintenance;
import com.tsys.xmlmessaging.ch2.TSYSXMLMessagingSoapMaintenance;
import com.tsys.xmlmessaging.ch2.MntCustServiceAdjRequestType.ActionInfo;
import com.tsys.xmlmessaging.ch2.MntCustServiceAdjRequestType.TranKey;
import com.tsys.xmlmessaging.ch2.MntCustServiceAdjRequestType.ActionInfo.Adj;
import com.tsys.xmlmessaging.ch2.MntCustServiceAdjRequestType.ActionInfo.AmtTran;
import com.tsys.xmlmessaging.ch2.MntCustServiceAdjRequestType.ActionInfo.MerchantInfo;

import mx.com.invex.msi.model.Compra;
import mx.com.invex.msi.util.MSIConstants;

public class ClientTS2 {
	static TSYSXMLMessagingInquiry servInq;
    static TSYSXMLMessagingSoapInquiry inqPort;
    static TSYSXMLMessagingMaintenance servMnt;
    static TSYSXMLMessagingSoapMaintenance mntPort;
    static TSYSprofileType tp;
	static{
		
	       servInq = new TSYSXMLMessagingInquiry();
	       inqPort = servInq.getTSYSXMLMessagingSoapInquiry03();
	       servMnt = new TSYSXMLMessagingMaintenance();
	       mntPort= servMnt.getTSYSXMLMessagingSoapMaintenance04();
	        tp = new TSYSprofileType();
			 tp.setClientID("7401");
			 tp.setUserID("op0009");
			 tp.setVendorID("00000000");
	}
	
	final static private Logger logger = Logger.getLogger(ClientTS2.class);
	
	public static IASstatusCodeResponseDataType getStatusCode(String cuenta) throws Exception{  
	InqAcctStatus inqAcctStatus = new InqAcctStatus();
	 InqAcctStatusRequestType inqAcctStatusReq = new InqAcctStatusRequestType();
	 //key="4196910074855913" keyType="cardNbr" version="1.0.0"/>
	 inqAcctStatusReq.setKey(cuenta);
	 inqAcctStatusReq.setKeyType("cardNbr");
	 inqAcctStatusReq.setVersion("1.0.0");
	 inqAcctStatus.setInqAcctStatusRequest(inqAcctStatusReq);
		InqAcctStatusResponseType resSt= inqAcctStatus(tp,inqAcctStatus).getInqAcctStatusResult();
		logger.info("inqAcctStatus");
		if(!"000".equals(resSt.getStatus())){
			logger.info(resSt.getStatusMsg());
			TSYSfaultType fault = resSt.getFaults();
			List<TSYSfault> lfaulta =fault.getFault();
			for (TSYSfault sfault : lfaulta) {
				logger.info(sfault.getStatus()+" "+ sfault.getFaultDesc());
				throw new Exception("ERROR TSYS getStatusCode "+sfault.getFaultDesc());
				
			}
		}
		return resSt.getStatusCode().getValue();
	}
	
	public static BigDecimal getCreditLimit(String cuenta) throws Exception{
		InqCreditLmt inqCreditLmt = new InqCreditLmt();
  		 InqCreditLmtRequestType inqCreditLmtReq = new InqCreditLmtRequestType();
  		 
  		 inqCreditLmtReq.setKey(cuenta);
  		 inqCreditLmtReq.setKeyType("cardNbr");
  		 inqCreditLmtReq.setVersion("1.0.0");
  		 inqCreditLmt.setInqCreditLmtRequest(inqCreditLmtReq);
           InqCreditLmtResponseType lmtResponse= inqCreditLmt(tp,inqCreditLmt).getInqCreditLmtResult();
           logger.info("lmtResponse");
			if(!"000".equals(lmtResponse.getStatus())){
				logger.info(lmtResponse.getStatusMsg());
				TSYSfaultType fault =lmtResponse.getFaults();
				List<TSYSfault> lfaulta =fault.getFault();
				for (TSYSfault sfault : lfaulta) {
					logger.info(sfault.getStatus()+" "+ sfault.getFaultDesc());
					throw new Exception("ERROR TSYS getCreditLimit "+sfault.getFaultDesc());
					
				}
			}
			return lmtResponse.getCreditLmtInfo().getValue().getCreditLmt().getValue().getAmt().getValue();
	}
	
	public static List<IRTretailTransResponseDataType> getNumPromos(String cuenta)throws Exception{
		 InqRetailTrans inqRetailTrans = new InqRetailTrans();
		 InqRetailTransRequestType req= new InqRetailTransRequestType();
		 req.setKey(cuenta);
		 req.setKeyType("cardNbr");
		 req.setVersion("1.0.0");
		 inqRetailTrans.setInqRetailTransRequest(req);
		 InqRetailTransResponseType res= inqRetailTrans(tp, inqRetailTrans).getInqRetailTransResult();
		 if(!"000".equals(res.getStatus())){
				logger.info(res.getStatusMsg());
				TSYSfaultType fault = res.getFaults();
				List<TSYSfault> lfaulta =fault.getFault();
				for (TSYSfault sfault : lfaulta) {
					logger.info(sfault.getStatus()+" "+ sfault.getFaultDesc());
					throw new Exception("ERROR TSYS inqRetailTrans "+sfault.getFaultDesc());
				}
			}
		 return res.getRetailTrans();
	}
	
	public static  List<IPIpmtInfoResponseDataType> getPmtInfo(String cuenta) throws Exception{
		   InqPmtInfo inqPmtInfo = new InqPmtInfo();
     		 InqPmtInfoRequestType inqPmtInfoReq = new InqPmtInfoRequestType();
     		
     		 inqPmtInfoReq.setKey(cuenta);
     		 inqPmtInfoReq.setKeyType("cardNbr");
     		 inqPmtInfoReq.setVersion("1.2.0");
     		 inqPmtInfoReq.setCycleType("Current");
     		 inqPmtInfo.setInqPmtInfoRequest(inqPmtInfoReq);
              InqPmtInfoResponseType pmtInfoResponse = inqPmtInfo(tp,inqPmtInfo).getInqPmtInfoResult();
              logger.info("pmtInfoResponse");
				if(!"000".equals(pmtInfoResponse.getStatus())){
					logger.info(pmtInfoResponse.getStatusMsg());
					TSYSfaultType fault =pmtInfoResponse.getFaults();
					List<TSYSfault> lfaulta =fault.getFault();
					for (TSYSfault sfault : lfaulta) {
						logger.info(sfault.getStatus()+" "+ sfault.getFaultDesc());
						throw new Exception("ERROR TSYS getPmtInfo "+sfault.getFaultDesc());
					}
				}
	            return pmtInfoResponse.getPmtInfo();
	}
	
	public static String getCreditoDisponible(String cuenta) throws Exception{
		 InqRealTimeAuthSys inqRealTimeAuthSys = new InqRealTimeAuthSys();
         InqRealTimeAuthSysRequestType inqRealTimeAuthSysReq= new InqRealTimeAuthSysRequestType();
         inqRealTimeAuthSysReq.setKey(cuenta);
         inqRealTimeAuthSysReq.setKeyType("cardNbr");
         inqRealTimeAuthSysReq.setVersion("1.0.0");
         inqRealTimeAuthSys.setInqRealTimeAuthSysRequest(inqRealTimeAuthSysReq);
         InqRealTimeAuthSysResponseType inqRealTimeAuthSysResp = inqRealTimeAuthSys(tp, inqRealTimeAuthSys).getInqRealTimeAuthSysResult();
       
         if(!"000".equals( inqRealTimeAuthSysResp.getStatus())){
				logger.info( inqRealTimeAuthSysResp.getStatusMsg());
				TSYSfaultType fault = inqRealTimeAuthSysResp.getFaults();
				List<TSYSfault> lfaulta =fault.getFault();
				for (TSYSfault sfault : lfaulta) {
					logger.info(sfault.getStatus()+" "+ sfault.getFaultDesc());
					throw new Exception("ERROR TSYS getCreditoDisponoble "+sfault.getFaultDesc());
					
				}
			}
				BigDecimal amtMoney =inqRealTimeAuthSysResp.getRealTimeAuthInfo().getValue().getAvail().getAmtMoney().getValue();
				String creditoDisponible=""+amtMoney.doubleValue();
			return creditoDisponible;
	}
	
	public static IGBgeneralBalInfoResponseDataType getGeneralBal(String cuenta) throws Exception{
	 	 InqGeneralBal inqGeneralBal = new InqGeneralBal();
		 InqGeneralBalRequestType inqGeneralBalReq = new InqGeneralBalRequestType();
		 
		 inqGeneralBalReq.setKey(cuenta);
		 inqGeneralBalReq.setKeyType("cardNbr");
		 inqGeneralBalReq.setVersion("2.5.0");
		 inqGeneralBal.setInqGeneralBalRequest(inqGeneralBalReq);
            InqGeneralBalResponseType gralBalRes = inqGeneralBal(tp,inqGeneralBal).getInqGeneralBalResult();
       
            logger.info("gralBalRes status "+gralBalRes.getStatus());
			if(!"000".equals(gralBalRes.getStatus())){
				logger.info(gralBalRes.getStatusMsg());
				TSYSfaultType fault =gralBalRes.getFaults();
				List<TSYSfault> lfaulta =fault.getFault();
				for (TSYSfault sfault : lfaulta) {
					logger.info(sfault.getStatus()+" "+ sfault.getFaultDesc());
					throw new Exception("ERROR TSYS getGeneralBal "+sfault.getFaultDesc());
				}
			}
		return gralBalRes.getGeneralBalInfo().getValue();
	}
	
	public static List<ITRtranDetailResponseDataType> getTrans(String cuenta,boolean onlyCurr,GregorianCalendar fechaIn,GregorianCalendar fechaFin) throws Exception{
		InqTrans inqTrans = new InqTrans();
		InqTransRequestType reqInqTrans = new InqTransRequestType();
		
		reqInqTrans.setOnlyForeign(false);
		reqInqTrans.setOnlyCurr(onlyCurr);
		reqInqTrans.setVersion("1.9.0");
		reqInqTrans.setKey(cuenta);
		reqInqTrans.setKeyType("cardNbr");
		reqInqTrans.setPageItems(300);
		
		if(fechaIn!= null && fechaFin!= null){
			com.tsys.xmlmessaging.ch.InqTransRequestType.CycleRange cycleRange = new  com.tsys.xmlmessaging.ch.InqTransRequestType.CycleRange();
			XMLGregorianCalendar calendarIn = DatatypeFactory.newInstance().newXMLGregorianCalendar(fechaIn);
			XMLGregorianCalendar calendarFin = DatatypeFactory.newInstance().newXMLGregorianCalendar(fechaFin);
			cycleRange.setFrom(calendarIn);
			cycleRange.setTo(calendarFin);          
			reqInqTrans.setCycleRange(cycleRange);
		}
		inqTrans.setInqTransRequest(reqInqTrans);
		InqTransResponseType respExt =inqTrans(tp, inqTrans).getInqTransResult();
		
		if(!"000".equals( respExt.getStatus())){
			logger.info( respExt.getStatusMsg());
			TSYSfaultType fault = respExt.getFaults();
			List<TSYSfault> lfaulta =fault.getFault();
			for (TSYSfault sfault : lfaulta) {
				logger.info(sfault.getStatus()+" "+ sfault.getFaultDesc());
				throw new Exception("ERROR TSYS getTrans "+sfault.getFaultDesc());
			}
		}
		return respExt.getTranDetail();
	}
	
	public static List<ITRtranDetailResponseDataType> getTrans(String cuenta,XMLGregorianCalendar calendarIn,XMLGregorianCalendar calendarFin) throws Exception{
		InqTrans inqTrans = new InqTrans();
		InqTransRequestType reqInqTrans = new InqTransRequestType();
		
		reqInqTrans.setOnlyForeign(false);
		
		reqInqTrans.setVersion("1.9.0");
		reqInqTrans.setKey(cuenta);
		reqInqTrans.setKeyType("cardNbr");
		reqInqTrans.setPageItems(300);
		
		
			com.tsys.xmlmessaging.ch.InqTransRequestType.CycleRange cycleRange = new  com.tsys.xmlmessaging.ch.InqTransRequestType.CycleRange();
//			XMLGregorianCalendar calendarIn = DatatypeFactory.newInstance().newXMLGregorianCalendar(fechaIn);
//			XMLGregorianCalendar calendarFin = DatatypeFactory.newInstance().newXMLGregorianCalendar(fechaFin);
			cycleRange.setFrom(calendarIn);
			cycleRange.setTo(calendarFin);          
			reqInqTrans.setCycleRange(cycleRange);
		
		inqTrans.setInqTransRequest(reqInqTrans);
		InqTransResponseType respExt =inqTrans(tp, inqTrans).getInqTransResult();
		
		if(!"000".equals( respExt.getStatus())){
			logger.info( respExt.getStatusMsg());
			TSYSfaultType fault = respExt.getFaults();
			List<TSYSfault> lfaulta =fault.getFault();
			for (TSYSfault sfault : lfaulta) {
				logger.info(sfault.getStatus()+" "+ sfault.getFaultDesc());
				throw new Exception("ERROR TSYS getTrans "+sfault.getFaultDesc());
			}
		}
		return respExt.getTranDetail();
	}
	
	public static JAXBElement<ITAcustomDataResponseDataType> getCustomDatas(String cuenta) throws Exception{
		InqCustomData inqCustomData = new InqCustomData();
		InqCustomDataRequestType inqCustomDataReq = new InqCustomDataRequestType();
		inqCustomDataReq.setKey(cuenta);
		inqCustomDataReq.setKeyType("cardNbr");
		inqCustomDataReq.setVersion("1.0.0");
		
		CustomDataCodes customDataCodes = new CustomDataCodes();
		List<String> custDatas =customDataCodes.getCustomDataCode();
		//fecha vencimiento
		custDatas.add("61");
		//fecha inscrtipcion prgo cero
		custDatas.add("81");
		inqCustomDataReq.setCustomDataCodes(customDataCodes);
		inqCustomData.setInqCustomDataRequest(inqCustomDataReq);
		InqCustomDataResponseType customDataResp= inqCustomData(tp, inqCustomData).getInqCustomDataResult();
		if(!"000".equals( customDataResp.getStatus())){
			logger.info( customDataResp.getStatusMsg());
			TSYSfaultType fault = customDataResp.getFaults();
			List<TSYSfault> lfaulta =fault.getFault();
			for (TSYSfault sfault : lfaulta) {
				logger.info(sfault.getStatus()+" "+ sfault.getFaultDesc());
				throw new Exception("ERROR TSYS getCustomDatas "+sfault.getFaultDesc());
			}
		}
		return customDataResp.getCustomData();
	}
	
	public static void aplicarCompra(Compra compra) throws Exception{
		 TSYSprofileType tp = new TSYSprofileType();
		 tp.setClientID("7401");
		 tp.setUserID("invdev");
		 tp.setVendorID("00000000");
		
		
		logger.info("inqAcctAvailTLPOpt assesfee false tlptype Installment tbal code 0001 amtToMove "+compra.getMontoPromo());
		String pattern = "###.##";
		DecimalFormat decimalFormat = new DecimalFormat(pattern);
		MntCustServiceAdjRequestType mntCustServiceAdjReq = new MntCustServiceAdjRequestType();
		mntCustServiceAdjReq.setVersion("2.12.0");
		mntCustServiceAdjReq.setKeyType("cardNbr");
		mntCustServiceAdjReq.setKey(compra.getCuenta());
		TranKey trankey = new TranKey();
		com.tsys.xmlmessaging.ch2.Date dateStmBegin = new com.tsys.xmlmessaging.ch2.Date();
		dateStmBegin.setValue(compra.getDateStmtBegin());
		trankey.setDateStmtBegin(dateStmBegin);
		com.tsys.xmlmessaging.ch2.Date datePost = new com.tsys.xmlmessaging.ch2.Date();
		datePost.setValue(compra.getDatePost());
		trankey.setDatePost(datePost);
		trankey.setTimePost(compra.getTimePost());
		mntCustServiceAdjReq.setTranKey(trankey);
		ActionInfo actionInfo = new ActionInfo();
		actionInfo.setAction("Adjustment");
		actionInfo.setType("Initial");
		actionInfo.setTranCode("0106");
		AmtTran amtTran = new AmtTran();
		amtTran.setCode("MXN");
		amtTran.setValue(new BigDecimal(decimalFormat.format(compra.getMontoPromo())));
		actionInfo.setAmtTran(amtTran);
		MerchantInfo merchantInfo = new MerchantInfo();
		merchantInfo.setDBAName(String.format("%1$.25s", compra.getDescripcion()));
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
		actInfo2.setAcctNbr(compra.getCuenta());
		actInfo2.setAmtTran(amtTran);
		actInfo2.setMerchantInfo(merchantInfo);
		Adj adj2= new Adj();
		adj2.setForcePost(b);
		adj2.setTLPType("S");
		String tlpopt= null;
		int meses =compra.getPromocion().getPlazoMeses();
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
		adj2.setTLPOptSet(tlpopt);
		actInfo2.setAdj(adj2);
		
		List<ActionInfo> actInfos=mntCustServiceAdjReq.getActionInfo();
		actInfos.add(actionInfo);
		actInfos.add(actInfo2);
		
		
		if (!MSIConstants.desa) {
			MntCustServiceAdjResponse mntCustServiceAdjResp= mntCustServiceAdj(mntCustServiceAdjReq);
			String status = mntCustServiceAdjResp.getMntCustServiceAdjResult().getStatus();
			if(!"000".equals(status)){
				String msg = mntCustServiceAdjResp.getMntCustServiceAdjResult().getStatusMsg();
				logger.info(msg);
				com.tsys.xmlmessaging.ch2.TSYSfaultType fault =mntCustServiceAdjResp.getMntCustServiceAdjResult().getFaults();
				List<com.tsys.xmlmessaging.ch2.TSYSfault> lfaulta =fault.getFault();
				for (com.tsys.xmlmessaging.ch2.TSYSfault sfault : lfaulta) {
					logger.info(sfault.getStatus()+" "+ sfault.getFaultDesc());
					throw new Exception("ERROR TSYS aplicar promocion mntCustServiceAdj "+sfault.getFaultDesc());
				}
				
			}
			
		}
	}
	
	public static IGAacctGeneralInfoResponseDataType getGeneralAcct(String cuenta) throws Exception{
		 InqGeneralAcctRequestType req = new InqGeneralAcctRequestType();
       	 req.setVersion("2.19.0");
       	 req.setKey(cuenta);
       	 req.setKeyType("cardNbr");
       	 InqGeneralAcct inqGeneralAcct = new InqGeneralAcct();
       	 inqGeneralAcct.setInqGeneralAcctRequest(req);
			InqGeneralAcctResponseType res= inqGeneralAcct(tp,inqGeneralAcct).getInqGeneralAcctResult();
			 logger.info("InqGeneralAcct");
				if(!"000".equals(res.getStatus())){
					logger.info(res.getStatusMsg());
					TSYSfaultType fault =res.getFaults();
					List<TSYSfault> lfaulta =fault.getFault();
					for (TSYSfault sfault : lfaulta) {
						logger.info(sfault.getStatus()+" "+ sfault.getFaultDesc());
						throw new Exception("ERROR TSYS getGeneralAcct "+sfault.getFaultDesc());
					}
				}
				
				return res.getAcctGeneralInfo().getValue();
	}
	
	public static List<ICAaddrInfoResponseDataType> getAddrInfo(String cuenta)throws Exception{
		   InqCustAddr inqCustAddr = new InqCustAddr();
     		 InqCustAddrRequestType inqCustAddrReq = new InqCustAddrRequestType();
     		
     		 inqCustAddrReq.setKey(cuenta);
     		 inqCustAddrReq.setKeyType("cardNbr");
     		 inqCustAddrReq.setVersion("1.0.0");
     		 inqCustAddr.setInqCustAddrRequest(inqCustAddrReq);
             InqCustAddrResponseType custAdrrResponse = inqCustAddr(tp,inqCustAddr).getInqCustAddrResult();
             logger.info("custAdrrResponse");
				if(!"000".equals(custAdrrResponse.getStatus())){
					logger.info(custAdrrResponse.getStatusMsg());
					TSYSfaultType fault = custAdrrResponse.getFaults();
					List<TSYSfault> lfaulta =fault.getFault();
					for (TSYSfault sfault : lfaulta) {
						logger.info(sfault.getStatus()+" "+ sfault.getFaultDesc());
						throw new Exception("ERROR TSYS getAddrInfo "+sfault.getFaultDesc());
					}
				}
		return custAdrrResponse.getAddrInfo();
	}
	
	public static List<ICIcustInfoResponseDataType> getCustInfo(String cuenta) throws Exception{
		 InqCustInfo inqCustInfo = new InqCustInfo();
		 InqCustInfoRequestType inqCustInfoReq = new InqCustInfoRequestType();
		 //key="4196910074855913" keyType="cardNbr" version="1.0.0"/>
		 inqCustInfoReq.setKey(cuenta);
		 inqCustInfoReq.setKeyType("cardNbr");
		 inqCustInfoReq.setVersion("2.10.0");
		 inqCustInfo.setInqCustInfoRequest(inqCustInfoReq);
		
		InqCustInfoResponseType custInfoResponse = inqCustInfo(tp,inqCustInfo).getInqCustInfoResult();
		logger.info("custInfoResponse");
		if(!"000".equals(custInfoResponse.getStatus())){
			logger.info(custInfoResponse.getStatusMsg());
			TSYSfaultType fault = custInfoResponse.getFaults();
			List<TSYSfault> lfaulta =fault.getFault();
			for (TSYSfault sfault : lfaulta) {
				logger.info(sfault.getStatus()+" "+ sfault.getFaultDesc());
				throw new Exception("ERROR TSYS getCustInfo "+sfault.getFaultDesc());
			}
		}
		return custInfoResponse.getCustInfo();
	
	}
	
	public static MntCustServiceAdjResponse mntCustServiceAdj(MntCustServiceAdjRequestType req ){
		Holder<com.tsys.xmlmessaging.ch2.TSYSprofileType> holder = new Holder<com.tsys.xmlmessaging.ch2.TSYSprofileType>();
		MntCustServiceAdj mntCustServiceAdj = new MntCustServiceAdj();
		mntCustServiceAdj.setMntCustServiceAdjRequest(req);
		//MntCustServiceAdjRequestType req = new MntCustServiceAdjRequestType();
		//req.se
		 com.tsys.xmlmessaging.ch2.TSYSprofileType tp = new com.tsys.xmlmessaging.ch2.TSYSprofileType();
		 tp.setClientID("7401");
		 tp.setUserID("invdev");
		 tp.setVendorID("00000000");
		holder.value=tp;
		 logger.info("MntCustServiceAdj REQUEST:");
	       try {
	            JAXBContext context = JAXBContext.newInstance(MntCustServiceAdj.class);
	            Marshaller m = context.createMarshaller();
	            //for pretty-print XML in JAXB
	            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, java.lang.Boolean.TRUE);
	 
	            // Write to System.out for debugging
	             m.marshal(mntCustServiceAdj, System.out);
	 
	            // Write to File
	           // m.marshal(emp, new File(FILE_NAME));
	        } catch (JAXBException e) {
	            e.printStackTrace();
	        }
	       logger.info("MntCustServiceAdj RESPONSE:");
	       MntCustServiceAdjResponse resp =mntPort.mntCustServiceAdj(mntCustServiceAdj, holder);
	       try {
	            JAXBContext context = JAXBContext.newInstance(MntCustServiceAdjResponse.class);
	            Marshaller m = context.createMarshaller();
	            //for pretty-print XML in JAXB
	            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, java.lang.Boolean.TRUE);
	 
	            // Write to System.out for debugging
	             m.marshal(resp, System.out);
	 
	            // Write to File
	           // m.marshal(emp, new File(FILE_NAME));
	        } catch (JAXBException e) {
	            e.printStackTrace();
	        }
	       
		return resp;
	}
	
	private static InqAcctStatusResponse inqAcctStatus(
	TSYSprofileType profile, InqAcctStatus inqAcctStatus) {
		Holder<com.tsys.xmlmessaging.ch.TSYSprofileType> holder = new Holder<com.tsys.xmlmessaging.ch.TSYSprofileType>();
		holder.value=profile;
		return inqPort.inqAcctStatus(inqAcctStatus, holder);
	}


	private static InqCustInfoResponse inqCustInfo(TSYSprofileType profile,
			InqCustInfo inqCustInfo) {
		
			Holder<com.tsys.xmlmessaging.ch.TSYSprofileType> holder = new Holder<com.tsys.xmlmessaging.ch.TSYSprofileType>();
			holder.value=profile;
			return inqPort.inqCustInfo(inqCustInfo, holder);
	}


	private static InqGeneralAcctResponse inqGeneralAcct(TSYSprofileType profile,
			InqGeneralAcct inqGeneralAcct) {

		Holder<com.tsys.xmlmessaging.ch.TSYSprofileType> holder = new Holder<com.tsys.xmlmessaging.ch.TSYSprofileType>();
		holder.value=profile;
		return inqPort.inqGeneralAcct(inqGeneralAcct, holder);
	}
	
	private static InqCustAddrResponse inqCustAddr(TSYSprofileType profile, InqCustAddr inqCustAddr){
		Holder<com.tsys.xmlmessaging.ch.TSYSprofileType> holder = new Holder<com.tsys.xmlmessaging.ch.TSYSprofileType>();
		holder.value=profile;
		return inqPort.inqCustAddr(inqCustAddr, holder);
	}
	
	private static InqGeneralBalResponse inqGeneralBal(TSYSprofileType profile, InqGeneralBal inqGeneralBal){
		Holder<com.tsys.xmlmessaging.ch.TSYSprofileType> holder = new Holder<com.tsys.xmlmessaging.ch.TSYSprofileType>();
		holder.value=profile;
		return inqPort.inqGeneralBal(inqGeneralBal, holder);
	}
	
	private static InqCreditLmtResponse inqCreditLmt(TSYSprofileType profile, InqCreditLmt inqCreditLmt){
		Holder<com.tsys.xmlmessaging.ch.TSYSprofileType> holder = new Holder<com.tsys.xmlmessaging.ch.TSYSprofileType>();
		holder.value=profile;
		return inqPort.inqCreditLmt(inqCreditLmt, holder);
	}
	
	private static InqPmtInfoResponse inqPmtInfo(TSYSprofileType profile, InqPmtInfo inqPmtInfo){
		Holder<com.tsys.xmlmessaging.ch.TSYSprofileType> holder = new Holder<com.tsys.xmlmessaging.ch.TSYSprofileType>();
		holder.value=profile;
		return inqPort.inqPmtInfo(inqPmtInfo, holder);
	}
	
	private static InqTransResponse inqTrans(TSYSprofileType profile,InqTrans inqTrans ){
		Holder<com.tsys.xmlmessaging.ch.TSYSprofileType> holder = new Holder<com.tsys.xmlmessaging.ch.TSYSprofileType>();
		holder.value=profile;
		return inqPort.inqTrans(inqTrans, holder);
	}
	
	private static InqRealTimeAuthSysResponse inqRealTimeAuthSys(TSYSprofileType profile, InqRealTimeAuthSys inqRealTimeAuthSys){
		Holder<com.tsys.xmlmessaging.ch.TSYSprofileType> holder = new Holder<com.tsys.xmlmessaging.ch.TSYSprofileType>();
		holder.value=profile;
		return inqPort.inqRealTimeAuthSys(inqRealTimeAuthSys, holder);
	}
	
	private static InqCustomDataResponse inqCustomData(TSYSprofileType profile,InqCustomData inqCustomData ){
		Holder<com.tsys.xmlmessaging.ch.TSYSprofileType> holder = new Holder<com.tsys.xmlmessaging.ch.TSYSprofileType>();
		holder.value=profile;
		return inqPort.inqCustomData(inqCustomData, holder);
	}
	
	private InqAcctAvailTLPOptResponse inqAcctAvailTLPOpt(TSYSprofileType profile, InqAcctAvailTLPOpt inqAcctAvailTLPOpt){
		Holder<com.tsys.xmlmessaging.ch.TSYSprofileType> holder = new Holder<com.tsys.xmlmessaging.ch.TSYSprofileType>();
		holder.value=profile;
		return inqPort.inqAcctAvailTLPOpt(inqAcctAvailTLPOpt, holder);
	}
	
	private ReqAcctTermTransferResponse reqAcctTermTransfer(com.tsys.xmlmessaging.ch2.TSYSprofileType profile, ReqAcctTermTransfer reqAcctTermTransfer){
		Holder<com.tsys.xmlmessaging.ch2.TSYSprofileType> holder = new Holder<com.tsys.xmlmessaging.ch2.TSYSprofileType>();
		holder.value=profile;
		return mntPort.reqAcctTermTransfer(reqAcctTermTransfer, holder);
	}
	
	private static InqRetailTransResponse inqRetailTrans(TSYSprofileType profile,InqRetailTrans inqRetailTrans){
		Holder<com.tsys.xmlmessaging.ch.TSYSprofileType> holder = new Holder<com.tsys.xmlmessaging.ch.TSYSprofileType>();
		holder.value=profile;
		return inqPort.inqRetailTrans(inqRetailTrans, holder);
	}
}
