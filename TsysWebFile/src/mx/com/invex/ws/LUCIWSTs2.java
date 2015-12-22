package mx.com.invex.ws;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import mx.com.interware.spira.dto.InfoLuciActivacionDTO;
import mx.com.interware.spira.web.dao.LUCIDAO;
import mx.com.invex.ws.client.ClientTs2;

import org.apache.log4j.Logger;

import com.tsys.xmlmessaging.ch.ICIcustInfoResponseDataType;
import com.tsys.xmlmessaging.ch.InqCustInfo;
import com.tsys.xmlmessaging.ch.InqCustInfoRequestType;
import com.tsys.xmlmessaging.ch.InqCustInfoResponse;
import com.tsys.xmlmessaging.ch2.AddCustIdentity;
import com.tsys.xmlmessaging.ch2.AddCustIdentityRequestType;
import com.tsys.xmlmessaging.ch2.AddCustIdentityResponse;
import com.tsys.xmlmessaging.ch2.AddCustNotes;
import com.tsys.xmlmessaging.ch2.AddCustNotesRequestType;
import com.tsys.xmlmessaging.ch2.AddCustNotesRequestType.NotesText.NoteText;
import com.tsys.xmlmessaging.ch2.AddCustNotesResponse;
import com.tsys.xmlmessaging.ch2.MntAcctMiscInfo;
import com.tsys.xmlmessaging.ch2.MntAcctMiscInfoRequestType;
import com.tsys.xmlmessaging.ch2.MntAcctMiscInfoRequestType.AcquisitionStrategy;
import com.tsys.xmlmessaging.ch2.MntAcctMiscInfoRequestType.BranchCode;
import com.tsys.xmlmessaging.ch2.MntAcctMiscInfoResponse;
import com.tsys.xmlmessaging.ch2.MntAcctStatus;
import com.tsys.xmlmessaging.ch2.MntAcctStatusRequestType;
import com.tsys.xmlmessaging.ch2.MntAcctStatusRequestType.MntAcctStatusReq;
import com.tsys.xmlmessaging.ch2.MntAcctStatusRequestType.MntAcctStatusReq.Status;
import com.tsys.xmlmessaging.ch2.MntAcctStatusRequestType.MntAcctStatusReq.Status.Reason;
import com.tsys.xmlmessaging.ch2.MntAcctStatusResponse;
import com.tsys.xmlmessaging.ch2.MntBillingCycle;
import com.tsys.xmlmessaging.ch2.MntBillingCycleRequestType;
import com.tsys.xmlmessaging.ch2.MntBillingCycleRequestType.BillingCycle;
import com.tsys.xmlmessaging.ch2.MntBillingCycleResponse;
import com.tsys.xmlmessaging.ch2.MntCreditLmt;
import com.tsys.xmlmessaging.ch2.MntCreditLmtRequestType;
import com.tsys.xmlmessaging.ch2.MntCreditLmtRequestType.UpdateCreditLmtInfo;
import com.tsys.xmlmessaging.ch2.MntCreditLmtRequestType.UpdateCreditLmtInfo.AmtNew;
import com.tsys.xmlmessaging.ch2.MntCreditLmtResponse;
import com.tsys.xmlmessaging.ch2.MntCustAddr;
import com.tsys.xmlmessaging.ch2.MntCustAddrRequestType;
import com.tsys.xmlmessaging.ch2.MntCustAddrRequestType.Code1Override;
import com.tsys.xmlmessaging.ch2.MntCustAddrRequestType.CustAddrInfo;
import com.tsys.xmlmessaging.ch2.MntCustAddrRequestType.CustAddrInfo.AddrLine1;
import com.tsys.xmlmessaging.ch2.MntCustAddrRequestType.CustAddrInfo.AddrLine2;
import com.tsys.xmlmessaging.ch2.MntCustAddrRequestType.CustAddrInfo.AddrLine3;
import com.tsys.xmlmessaging.ch2.MntCustAddrRequestType.CustAddrInfo.City;
import com.tsys.xmlmessaging.ch2.MntCustAddrRequestType.CustAddrInfo.PostalCode;
import com.tsys.xmlmessaging.ch2.MntCustAddrRequestType.CustAddrInfo.StateProv;
import com.tsys.xmlmessaging.ch2.MntCustAddrResponse;
import com.tsys.xmlmessaging.ch2.MntCustEmployerInfo;
import com.tsys.xmlmessaging.ch2.MntCustEmployerInfoRequestType;
import com.tsys.xmlmessaging.ch2.MntCustEmployerInfoRequestType.Name;
import com.tsys.xmlmessaging.ch2.MntCustEmployerInfoResponse;
import com.tsys.xmlmessaging.ch2.MntCustInfo;
import com.tsys.xmlmessaging.ch2.MntCustInfoRequestType;
import com.tsys.xmlmessaging.ch2.MntCustInfoRequestType.CustDetail;
import com.tsys.xmlmessaging.ch2.MntCustInfoRequestType.CustDetail.Financial;
import com.tsys.xmlmessaging.ch2.MntCustInfoRequestType.CustDetail.Financial.MonthlyIncome;
import com.tsys.xmlmessaging.ch2.MntCustInfoRequestType.CustDetail.Personal;
import com.tsys.xmlmessaging.ch2.MntCustInfoRequestType.CustDetail.Personal.CustBirthName;
import com.tsys.xmlmessaging.ch2.MntCustInfoRequestType.CustDetail.Personal.HomeType;
import com.tsys.xmlmessaging.ch2.MntCustInfoRequestType.CustDetail.Personal.MaritalStatus;
import com.tsys.xmlmessaging.ch2.MntCustInfoRequestType.CustDetail.Personal.Nationality;
import com.tsys.xmlmessaging.ch2.MntCustInfoRequestcustDetailpersonalhomeTypeEnums;
import com.tsys.xmlmessaging.ch2.MntCustInfoRequestcustDetailpersonalmaritalStatusEnums;
import com.tsys.xmlmessaging.ch2.MntCustInfoResponse;
import com.tsys.xmlmessaging.ch2.MntCustPhnNbr;
import com.tsys.xmlmessaging.ch2.MntCustPhnNbrRequestType;
import com.tsys.xmlmessaging.ch2.MntCustPhnNbrResponse;
import com.tsys.xmlmessaging.ch2.MntCustSecureInfo;
import com.tsys.xmlmessaging.ch2.MntCustSecureInfoRequestType;
import com.tsys.xmlmessaging.ch2.MntCustSecureInfoRequestType.DOB;
import com.tsys.xmlmessaging.ch2.MntCustSecureInfoResponse;
import com.tsys.xmlmessaging.ch2.MntCustomData;
import com.tsys.xmlmessaging.ch2.MntCustomDataRequestType;
import com.tsys.xmlmessaging.ch2.MntCustomDataRequestType.CustomDataUpdates;
import com.tsys.xmlmessaging.ch2.MntCustomDataRequestType.CustomDataUpdates.CustomData;
import com.tsys.xmlmessaging.ch2.MntCustomDataRequestType.CustomDataUpdates.CustomData.IntegerValue;
import com.tsys.xmlmessaging.ch2.MntCustomDataRequestType.CustomDataUpdates.CustomData.StringValue;
import com.tsys.xmlmessaging.ch2.MntCustomDataResponse;
import com.tsys.xmlmessaging.ch2.MntDisclosureGrp;
import com.tsys.xmlmessaging.ch2.MntDisclosureGrpRequestType;
import com.tsys.xmlmessaging.ch2.MntDisclosureGrpRequestType.DisclosureGrpID;
import com.tsys.xmlmessaging.ch2.MntDisclosureGrpResponse;
import com.tsys.xmlmessaging.ch2.MntEmailAddr;
import com.tsys.xmlmessaging.ch2.MntEmailAddrRequestType;
import com.tsys.xmlmessaging.ch2.MntEmailAddrRequestType.AddrDesc;
import com.tsys.xmlmessaging.ch2.MntEmailAddrRequestType.EmailAddr;
import com.tsys.xmlmessaging.ch2.MntEmailAddrResponse;
import com.tsys.xmlmessaging.ch2.MntEmbossingInfo;
import com.tsys.xmlmessaging.ch2.MntEmbossingInfoRequestType;
import com.tsys.xmlmessaging.ch2.MntEmbossingInfoRequestType.PersonalEmbossInfo;
import com.tsys.xmlmessaging.ch2.MntEmbossingInfoResponse;
import com.tsys.xmlmessaging.ch2.MntName;
import com.tsys.xmlmessaging.ch2.MntNameRequestType;
import com.tsys.xmlmessaging.ch2.MntNameRequestType.PersonalCust;
import com.tsys.xmlmessaging.ch2.MntNameRequestType.PersonalCust.First;
import com.tsys.xmlmessaging.ch2.MntNameRequestType.PersonalCust.Last;
import com.tsys.xmlmessaging.ch2.MntNameRequestType.PersonalCust.Middle;
import com.tsys.xmlmessaging.ch2.MntNameResponse;
import com.tsys.xmlmessaging.ch2.MntbooleanType;
import com.tsys.xmlmessaging.ch2.MntdateType;
import com.tsys.xmlmessaging.ch2.MntphoneNumberType;
import com.tsys.xmlmessaging.ch2.ReqPINMailer;
import com.tsys.xmlmessaging.ch2.ReqPINMailerRequestType;
import com.tsys.xmlmessaging.ch2.ReqPINMailerResponse;
import com.tsys.xmlmessaging.ch2.TSYSfault;
import com.tsys.xmlmessaging.ch2.TSYSfaultType;
import com.tsys.xmlmessaging.ch2.TSYSprofileType;
public class LUCIWSTs2 {
	
	private static final Logger logger = Logger.getLogger(LUCIWSTs2.class);
	
	public String actualizarDatosDemograficos(String folio,String account){
		try {
			
		
		InfoLuciActivacionDTO info =LUCIDAO.getInfoTsys2(folio);
		if(info==null){
			return "No se encontraron datos con el folio "+folio;
		}
		 TSYSprofileType tp = new TSYSprofileType();
		 tp.setClientID("7401");
		 tp.setUserID("invdev");
		 tp.setVendorID("00000000");
		 logger.info("instaciar cliente");
		 ClientTs2 cliente = new ClientTs2();
		 logger.info("despues de");
		 InqCustInfo inqCustInfo = new InqCustInfo();
		 InqCustInfoRequestType inqCustInfoReq = new InqCustInfoRequestType();
		 inqCustInfoReq.setKey(account);
		 inqCustInfoReq.setKeyType("cardNbr");
		 inqCustInfoReq.setVersion("2.0.0");
		 inqCustInfo.setInqCustInfoRequest(inqCustInfoReq);
		 com.tsys.xmlmessaging.ch.TSYSprofileType tp2 = new com.tsys.xmlmessaging.ch.TSYSprofileType();
		 tp2.setClientID("7401");
		 tp2.setUserID("gp5rwf");
		 tp2.setVendorID("00000000");
		 logger.info("custInfoResp");
		 InqCustInfoResponse custInfoResp= cliente.inqCustInfo(tp2, inqCustInfo);
		 logger.info("despues");
		 List<ICIcustInfoResponseDataType> listCustInfos = custInfoResp.getInqCustInfoResult().getCustInfo();
		 String custId=null;
		 logger.info("tam custinfos "+listCustInfos.size());
         for (ICIcustInfoResponseDataType custInfo : listCustInfos) {
                         logger.info("custInfo custiyer "+custInfo.getCustType());
                         if(custInfo.getCustType().equals("Primary") ){
                        	 custId=custInfo.getCustID();
                         }
         }
		 
		// String custId="000016828";
         logger.info("custId "+custId);
         if(custId==null){
        	 return "CustId es Null";
         }
		
			if (info.getNameLine1()!= null) {
				//cambiar ñ por N
				//String ntar=nombreTarjeta.replace('Ñ', 'N').replace('ñ', 'N');
				MntEmbossingInfo mntEmbossingInfo = new MntEmbossingInfo();
				MntEmbossingInfoRequestType embossingInfoReq = new MntEmbossingInfoRequestType();
				embossingInfoReq.setKeyType("cardNbr");
				embossingInfoReq.setVersion("1.0.0");
				embossingInfoReq.setCustID(custId);
				embossingInfoReq.setKey(account);
				PersonalEmbossInfo personalEmbossInfo = new PersonalEmbossInfo();
				if(info.getNameLine1().length() > 26){
					personalEmbossInfo.setValue(info.getNameLine1().substring(0,26));
				}else{
					personalEmbossInfo.setValue(info.getNameLine1());
				}
				embossingInfoReq
						.setPersonalEmbossInfo(personalEmbossInfo);
				mntEmbossingInfo
						.setMntEmbossingInfoRequest(embossingInfoReq);
				logger.info("mntEmbossingInfo "
						+ info.getNameLine1());
				MntEmbossingInfoResponse mntEmbossingInfoResponse = cliente
						.mntEmbossingInfo(tp, mntEmbossingInfo);
				logger.info("despoes ");
				//logger.info("statsu "+mntEmbossingInfoResponse.getMntEmbossingInfoResult().getStatus());
				if ("999".equals(mntEmbossingInfoResponse
						.getMntEmbossingInfoResult().getStatus())) {
					logger.info(mntEmbossingInfoResponse
							.getMntEmbossingInfoResult()
							.getStatus());
					TSYSfaultType fault = mntEmbossingInfoResponse
							.getMntEmbossingInfoResult()
							.getFaults();
					List<TSYSfault> lfaulta = fault.getFault();
					for (TSYSfault sfault : lfaulta) {
						logger.info(sfault.getStatus() + " "
								+ sfault.getFaultDesc());
					}
				}
			}
			//param.setName(ntar.replace('ñ', 'N'));
			MntCustInfoRequestType mntCustInfoReq = new MntCustInfoRequestType();
			mntCustInfoReq.setKey(account);
			mntCustInfoReq.setKeyType("cardNbr");
			mntCustInfoReq.setCustID(custId);
			mntCustInfoReq.setVersion("1.0.0");
			
			CustDetail custDetail = new CustDetail();
			logger.info("Edo civil " +info.getMaritalStatus());
			
			Personal per = new Personal();
			if (info.getMaritalStatus()!= null) {
				String edoCivil=null;
				if("Soltero".equals(info.getMaritalStatus().trim())){
					edoCivil="Single";
				}else if("Casado".equals(info.getMaritalStatus().trim())){
					edoCivil="Married";
				}else if("Divorciado".equals(info.getMaritalStatus().trim())){
					edoCivil="Divorced";
				}else if("Viudo".equals(info.getMaritalStatus().trim())){
					edoCivil="Widowed";
				}
				else if("Separado".equals(info.getMaritalStatus().trim())){
					edoCivil="Separated";
				}
				MaritalStatus mstatus = new MaritalStatus();
				mstatus
						.setValue(MntCustInfoRequestcustDetailpersonalmaritalStatusEnums
								.fromValue(edoCivil));
				per.setMaritalStatus(mstatus);
			}
//			if (info.getNacionalidad()!= null) {
				Nationality nat = new Nationality();
				nat.setValue("MEX");
				per.setNationality(nat);
//			}
			CustBirthName bname = new CustBirthName();
			bname.setValue(info.getBirthname());
			per.setCustBirthName(bname);
			logger.info("tipo vivienda "+info.getHomeType());
			if (info.getHomeType()!= null)
			{
				String tipoVivienda = null;
				if("PROPIA".equals(info.getHomeType())){
					tipoVivienda="Owns Home";
				}
				HomeType homeType = new HomeType();
				homeType
						.setValue(MntCustInfoRequestcustDetailpersonalhomeTypeEnums
								.fromValue(tipoVivienda));
				per.setHomeType(homeType);
			}
			custDetail.setPersonal(per);
			if (info.getIngresoMensual()!= null) {
				Financial fin = new Financial();
				MonthlyIncome mincome = new MonthlyIncome();
				mincome.setValue(new BigDecimal(info
						.getIngresoMensual()));
				mincome.setCode("MXN");
				fin.setMonthlyIncome(mincome);
				custDetail.setFinancial(fin);
			}
			if (info.getSexo() != null || info.getAntiguedadEmp() != null) {
				List<MntCustInfoRequestType.CustDetail.CustomData> listCusData = custDetail
						.getCustomData();
				if (info.getSexo() != null) {
					MntCustInfoRequestType.CustDetail.CustomData cusData1 = new MntCustInfoRequestType.CustDetail.CustomData();
					cusData1.setCode("1");
					MntCustInfoRequestType.CustDetail.CustomData.Value valueCD1 = new MntCustInfoRequestType.CustDetail.CustomData.Value();
					valueCD1.setValue(info.getSexo());
					cusData1.setValue(valueCD1);
					listCusData.add(cusData1);
				}
				if (info.getAntiguedadEmp() != null) {
					MntCustInfoRequestType.CustDetail.CustomData cusData2 = new MntCustInfoRequestType.CustDetail.CustomData();
					cusData2.setCode("2");
					MntCustInfoRequestType.CustDetail.CustomData.Value valueCD2 = new MntCustInfoRequestType.CustDetail.CustomData.Value();
					valueCD2.setValue(info.getAntiguedadEmp());
					cusData2.setValue(valueCD2);
					listCusData.add(cusData2);
				}
				
				if (info.getGradoEstudio() != null) {
					MntCustInfoRequestType.CustDetail.CustomData cusData3 = new MntCustInfoRequestType.CustDetail.CustomData();
					cusData3.setCode("3");
					MntCustInfoRequestType.CustDetail.CustomData.Value valueCD3 = new MntCustInfoRequestType.CustDetail.CustomData.Value();
					valueCD3.setValue(info.getGradoEstudio());
					cusData3.setValue(valueCD3);
					listCusData.add(cusData3);
				}
				
			}
			mntCustInfoReq.setCustDetail(custDetail);
			
			MntCustInfo mntCustInfo = new MntCustInfo();
			mntCustInfo.setMntCustInfoRequest(mntCustInfoReq);
			logger.info("MntCustInfo");
			MntCustInfoResponse respCustInfo= cliente.mntCustInfo(tp, mntCustInfo);
			if("999".equals(respCustInfo.getMntCustInfoResult().getStatus())){
				logger.info(respCustInfo.getMntCustInfoResult().getStatusMsg());
				TSYSfaultType fault = respCustInfo.getMntCustInfoResult().getFaults();
				List<TSYSfault> lfaulta =fault.getFault();
				for (TSYSfault sfault : lfaulta) {
					logger.info(sfault.getStatus()+" "+ sfault.getFaultDesc() +" fault path "+sfault.getFaultPath());
				}
			}
		
		MntName mntName = new MntName();
		MntNameRequestType mntNameReq = new MntNameRequestType();
		mntNameReq.setKey(account);
		mntNameReq.setKeyType("cardNbr");
		mntNameReq.setVersion("1.0.0");
		mntNameReq.setCustID(custId);
		MntNameRequestType.Emboss emboss2 = new MntNameRequestType.Emboss();
		MntNameRequestType.Emboss.Name name = new MntNameRequestType.Emboss.Name();
		name.setValue(info.getLongName());
		emboss2.setName(name);
		MntbooleanType mntbooleanType = new MntbooleanType();
		mntbooleanType.setValue(true);
		emboss2.setOverrideEmbossName(mntbooleanType);
		
		mntNameReq.setEmboss(emboss2);
		First first = new First();
		first.setValue(info.getNombre());
		PersonalCust personalCust = new PersonalCust();
		personalCust.setFirst(first);
		///mntNameReq.getPersonalCust().setFirst(first);
		Middle middle = new Middle();
		middle.setValue(info.getApaterno());
		personalCust.setMiddle(middle);
		//mntNameReq.getPersonalCust().setMiddle(middle);
		if(info.getAmaterno()!= null){
		Last last = new Last();
		last.setValue(info.getAmaterno());
		personalCust.setLast(last);
		}
		mntNameReq.setPersonalCust(personalCust);
		//mntNameReq.getPersonalCust().setLast(last);
		mntName.setMntNameRequest(mntNameReq);
		logger.info("mntName "+info.getNombre() +" "+info.getApaterno() +" "+info.getAmaterno());
		MntNameResponse mntNameResp= cliente.mntName(tp, mntName);
		logger.info("despoes");
		logger.info("statsu "+mntNameResp.getMntNameResult().getStatus());
		if("999".equals(mntNameResp.getMntNameResult().getStatus())){
			logger.info("MSG:"+mntNameResp.getMntNameResult().getStatusMsg());
			TSYSfaultType fault = mntNameResp.getMntNameResult().getFaults();
				List<TSYSfault> lfaulta =fault.getFault();
				for (TSYSfault sfault : lfaulta) {
					logger.info(sfault.getStatus()+" "+ sfault.getFaultDesc() + " code "+sfault.getCode());
				}
			
		}
		
		
		MntCustAddr mntCustAddr = new MntCustAddr();
		MntCustAddrRequestType custAddrReq = new MntCustAddrRequestType();
		custAddrReq.setKey(account);
		custAddrReq.setKeyType("cardNbr");
		custAddrReq.setVersion("1.2.0");
		custAddrReq.setCustID(custId);
		Code1Override code1Override= new Code1Override();
		code1Override.setValue("Y");
		custAddrReq.setCode1Override(code1Override);
		CustAddrInfo custAddrInfo = new CustAddrInfo();
		//info.getCalle().replace("ñ", "n").replace("Ñ", "N") +" "+info.getNoExterior()+" "+(info.getNoInterior()==null?"":info.getNoInterior()));
		//param.setAddress2(info.getColonia().replace("ñ", "n").replace("Ñ", "N"));
		if(info.getCalle()!= null){
			AddrLine1 addrLine1 = new AddrLine1();
			addrLine1.setValue(info.getCalle().replace("ñ", "n").replace("Ñ", "N") +" "+info.getNoExterior()+" "+(info.getNoInterior()==null?"":info.getNoInterior()));
			custAddrInfo.setAddrCode("01");
			custAddrInfo.setAddrLine1(addrLine1);
		}
		
		if(info.getColonia()!= null){
			
			AddrLine2 addrLine2 = new AddrLine2();
			addrLine2.setValue(info.getColonia().replace("ñ", "n").replace("Ñ", "N"));
			custAddrInfo.setAddrLine2(addrLine2);
		}
		
		if(info.getMunicipio()!=null){
			AddrLine3 addrLine3 = new AddrLine3();
			addrLine3.setValue(info.getMunicipio().replace("ñ", "n").replace("Ñ", "N"));
			custAddrInfo.setAddrLine3(addrLine3);
		}
		
		if(info.getCpostal()!=null){
			PostalCode postalCode = new PostalCode();
			postalCode.setValue(info.getCpostal());
			custAddrInfo.setPostalCode(postalCode);
		}
		//param.setCityState(info.getMunicipio().replace("ñ", "n").replace("Ñ", "N") +", "+info.getEdo());
	
			City city = new City();
			city.setValue(info.getEdo());
			custAddrInfo.setCity(city);
		
		StateProv stateProv = new StateProv();
		stateProv.setValue(info.getEdo());
		custAddrInfo.setStateProv(stateProv);
		custAddrReq.setCustAddrInfo(custAddrInfo);
		mntCustAddr.setMntCustAddrRequest(custAddrReq);
		logger.info("mntCustAddr "+info.getEdo()+" "+ info.getCpostal()+" "+ info.getColonia()+" "+info.getCalle()+" "+info.getNoInterior()+" "+ info.getNoExterior());
		MntCustAddrResponse mntCustAddrResp= cliente.mntCustAddr(tp, mntCustAddr);
		logger.info("despoes");
		logger.info("satatsu "+mntCustAddrResp.getMntCustAddrResult().getStatus());
		if("999".equals(mntCustAddrResp.getMntCustAddrResult().getStatus())){
			logger.info("MSG:"+mntCustAddrResp.getMntCustAddrResult().getStatusMsg());
			TSYSfaultType fault = mntCustAddrResp.getMntCustAddrResult().getFaults();
				List<TSYSfault> lfaulta =fault.getFault();
				for (TSYSfault sfault : lfaulta) {
					logger.info(sfault.getStatus()+" "+ sfault.getFaultDesc() + " code "+sfault.getCode());
				}
			
		}
		MntphoneNumberType mntphoneNumber = new MntphoneNumberType();
		if(info.getTel()!= null || info.getTelCel()!= null){
			MntCustPhnNbrRequestType custPhnNbrReq = new MntCustPhnNbrRequestType();
			custPhnNbrReq.setKey(account);
			custPhnNbrReq.setKeyType("cardNbr");
			custPhnNbrReq.setVersion("1.1.0");
			custPhnNbrReq.setType("Alt1");
			custPhnNbrReq.setCustID(custId);
			
			MntCustPhnNbrResponse mntCustPhnResp=null;
			MntCustPhnNbr mntCustPhnNbr = new MntCustPhnNbr();
			if(info.getTelCel()!= null){
				mntphoneNumber.setValue(info.getTelCel());
				custPhnNbrReq.setPhnNbr(mntphoneNumber);
				mntCustPhnNbr.setMntCustPhnNbrRequest(custPhnNbrReq);
				logger.info("mntCustPhnNbr cel "+info.getTelCel() +" tel "+info.getTel());
				 mntCustPhnResp= cliente.mntCustPhnNbr(tp, mntCustPhnNbr);
				logger.info("despues");
				logger.info("statsus "+mntCustPhnResp.getMntCustPhnNbrResult().getStatus());
				if("999".equals(mntCustPhnResp.getMntCustPhnNbrResult().getStatus())){
					logger.info(mntCustPhnResp.getMntCustPhnNbrResult().getStatusMsg());
					TSYSfaultType fault = mntCustPhnResp.getMntCustPhnNbrResult().getFaults();
					List<TSYSfault> lfaulta =fault.getFault();
					for (TSYSfault sfault : lfaulta) {
						logger.info(sfault.getStatus()+" "+ sfault.getFaultDesc());
					}
				}
			}
			if(info.getTel()!=  null){
				custPhnNbrReq.setType("Home");
				mntphoneNumber.setValue(info.getTel());
				custPhnNbrReq.setPhnNbr(mntphoneNumber);
				mntCustPhnNbr.setMntCustPhnNbrRequest(custPhnNbrReq);
				logger.info("mntCustPhnNbr");
				mntCustPhnResp =cliente.mntCustPhnNbr(tp, mntCustPhnNbr);
				logger.info("despues");
				logger.info("statsus "+mntCustPhnResp.getMntCustPhnNbrResult().getStatus());
				if("999".equals(mntCustPhnResp.getMntCustPhnNbrResult().getStatus())){
					logger.info(mntCustPhnResp.getMntCustPhnNbrResult().getStatusMsg());
					TSYSfaultType fault = mntCustPhnResp.getMntCustPhnNbrResult().getFaults();
					List<TSYSfault> lfaulta =fault.getFault();
					for (TSYSfault sfault : lfaulta) {
						logger.info(sfault.getStatus()+" "+ sfault.getFaultDesc());
					}
				}
			}
		}
		//custID="000016828" key="5467590344860155" keyType="cardNbr" version="1.1.0"
		
		if(info.getPlaceOfEmp()!= null || info.getTelEmp()!= null){
			MntCustEmployerInfo mntCustEmployerInfo = new MntCustEmployerInfo();
			MntCustEmployerInfoRequestType empInfo = new MntCustEmployerInfoRequestType();
			empInfo.setKey(account);
			empInfo.setCustID(custId);
			empInfo.setKeyType("cardNbr");
			empInfo.setVersion("1.1.0");
			logger.info("Place of emp ");
			if(info.getPlaceOfEmp()!= null){
				Name name2= new Name();
				name2.setValue(info.getPlaceOfEmp());
				empInfo.setName(name2);
			}
			logger.info("tel emp "+info.getTelEmp()+" extemp " +info.getExtEmp());
			if(info.getTelEmp()!= null){
				mntphoneNumber.setValue(info.getTelEmp());
				empInfo.setBusinessPhn(mntphoneNumber);
			}
			MntCustEmployerInfoRequestType.Addr addr= new MntCustEmployerInfoRequestType.Addr();
			//CountryCode
			MntCustEmployerInfoRequestType.Addr.CountryCode countryCode = new MntCustEmployerInfoRequestType.Addr.CountryCode();
			countryCode.setValue("52");//info.getCodigoPais()
			addr.setCountryCode(countryCode);
			if (info.getEmpCalle()!= null) {
				MntCustEmployerInfoRequestType.Addr.AddrLine1 empCalle = new MntCustEmployerInfoRequestType.Addr.AddrLine1();
				empCalle.setValue(info.getEmpCalle() + " "
						+ info.getEmpNum());
				addr.setAddrLine1(empCalle);
			}
			if (info.getEmpColonia()!= null) {
				MntCustEmployerInfoRequestType.Addr.AddrLine2 empColonia = new MntCustEmployerInfoRequestType.Addr.AddrLine2();
				empColonia.setValue(info.getEmpColonia());
				addr.setAddrLine2(empColonia);
			}
			if (info.getEmpNum()!= null) {
				MntCustEmployerInfoRequestType.Addr.City empCity = new MntCustEmployerInfoRequestType.Addr.City();
				empCity.setValue(info.getEmpMun());
				addr.setCity(empCity);
			}
			if (info.getEmpEdo()!= null) {
				MntCustEmployerInfoRequestType.Addr.StateProv empStateProv = new MntCustEmployerInfoRequestType.Addr.StateProv();
				empStateProv.setValue(info.getEmpEdo());
				addr.setStateProv(empStateProv);
			}
			if (info.getEmpCP()!= null) {
				MntCustEmployerInfoRequestType.Addr.PostalCode emppd = new MntCustEmployerInfoRequestType.Addr.PostalCode();
				emppd.setValue(info.getEmpCP());
				addr.setPostalCode(emppd);
			}
			empInfo.setAddr(addr);
			mntCustEmployerInfo.setMntCustEmployerInfoRequest(empInfo);
			logger.info("mntCustEmployerInfo "+info.getPlaceOfEmp() +" "+info.getTelEmp() +" "+info.getExtEmp());
			MntCustEmployerInfoResponse mntCustEmployerInfoResp= cliente.mntCustEmployerInfo(tp, mntCustEmployerInfo);
			logger.info("dsepues");
			logger.info("statsiu "+mntCustEmployerInfoResp.getMntCustEmployerInfoResult().getStatus());
			if("999".equals(mntCustEmployerInfoResp.getMntCustEmployerInfoResult().getStatus())){
				logger.info(mntCustEmployerInfoResp.getMntCustEmployerInfoResult().getStatusMsg());
				TSYSfaultType fault = mntCustEmployerInfoResp.getMntCustEmployerInfoResult().getFaults();
				List<TSYSfault> lfaulta =fault.getFault();
				for (TSYSfault sfault : lfaulta) {
					logger.info(sfault.getStatus()+" "+ sfault.getFaultDesc()+" "+sfault.getFaultPath().getValue());
				}
			}
		}
			if (info.getDisclosureGroup()!= null) {
				MntDisclosureGrp mntDisclosureGrp = new MntDisclosureGrp();
				MntDisclosureGrpRequestType mntDisclosureGrpReq = new MntDisclosureGrpRequestType();
				mntDisclosureGrpReq.setKey(account);
				mntDisclosureGrpReq.setKeyType("cardNbr");
				mntDisclosureGrpReq.setVersion("1.0.0");
				DisclosureGrpID disclosureGrpID = new DisclosureGrpID();
				disclosureGrpID.setValue(info.getDisclosureGroup());
				mntDisclosureGrpReq
						.setDisclosureGrpID(disclosureGrpID);
				mntDisclosureGrp
						.setMntDisclosureGrpRequest(mntDisclosureGrpReq);
				MntDisclosureGrpResponse mntDisclosureGrpResp = cliente
						.mntDisclosureGrp(tp, mntDisclosureGrp);
				if ("999".equals(mntDisclosureGrpResp
						.getMntDisclosureGrpResult().getStatus())) {
					logger.info(mntDisclosureGrpResp
							.getMntDisclosureGrpResult()
							.getStatusMsg());
					TSYSfaultType fault = mntDisclosureGrpResp
							.getMntDisclosureGrpResult()
							.getFaults();
					List<TSYSfault> lfaulta = fault.getFault();
					for (TSYSfault sfault : lfaulta) {
						logger.info(sfault.getStatus() + " "
								+ sfault.getFaultDesc());
					}
				}
			}
		
		/**
		 *  key="5467590344860155" keyType="cardNbr" version="1.1.0">
			<ch:custID>000016828</ch:custID>
			<!--Optional:-->
			<ch:altDelivery>FedEx Next Day</ch:altDelivery>
		 */
		if("S".equalsIgnoreCase(info.getGeneraNIP())){
			ReqPINMailer reqPINMailer = new ReqPINMailer();
			ReqPINMailerRequestType reqPINMailReq = new ReqPINMailerRequestType();
			reqPINMailReq.setKey(account);
			reqPINMailReq.setKeyType("cardNbr");
			reqPINMailReq.setVersion("1.1.0");
			reqPINMailReq.setCustID(custId);
			reqPINMailer.setReqPINMailerRequest(reqPINMailReq);
			logger.info("reqPINMailer "+info.getGeneraNIP());
			ReqPINMailerResponse reqPINMailerRes= cliente.reqPINMailer(tp, reqPINMailer);
			logger.info("desñpues");
			logger.info("statsus "+reqPINMailerRes.getReqPINMailerResult().getStatus());
		}
		if(info.getEmail()!=null){
			MntEmailAddrRequestType emailReq= new MntEmailAddrRequestType();
			emailReq.setKey(account);
			emailReq.setKeyType("cardNbr");
			emailReq.setVersion("1.2.0");
			emailReq.setCustID(custId);
			AddrDesc addrDesc = new AddrDesc();
			addrDesc.setValue("PRIMARY");
			emailReq.setAddrDesc(addrDesc);
			EmailAddr emailAddr= new EmailAddr();
			emailAddr.setValue(info.getEmail());
			emailReq.setEmailAddr(emailAddr);
			MntEmailAddr mntEmailAddr = new MntEmailAddr();
			mntEmailAddr.setMntEmailAddrRequest(emailReq);
			logger.info("mntEmailAddrRes "+info.getEmail());
			MntEmailAddrResponse mntEmailAddrRes= cliente.mntEmailAddr(tp, mntEmailAddr);
			logger.info("desapues");
			logger.info("stasu "+mntEmailAddrRes.getMntEmailAddrResult().getStatus());
			if("999".equals(mntEmailAddrRes.getMntEmailAddrResult().getStatus())){
				logger.info(mntEmailAddrRes.getMntEmailAddrResult().getStatusMsg());
				TSYSfaultType fault = mntEmailAddrRes.getMntEmailAddrResult().getFaults();
				List<TSYSfault> lfaulta =fault.getFault();
				for (TSYSfault sfault : lfaulta) {
					logger.info(sfault.getStatus()+" "+ sfault.getFaultDesc());
				}
			}
		}
		MntCustomData mntCustomData = new MntCustomData();
		MntCustomDataRequestType customDataReq = new MntCustomDataRequestType();
		customDataReq.setKey(account);
		customDataReq.setKeyType("cardNbr");
		customDataReq.setVersion("1.1.0");
		CustomDataUpdates cdu = new CustomDataUpdates();
		List<CustomData> ldata=cdu.getCustomData();
		if(info.getSavAcctNum()!= null){
			CustomData cd = new CustomData();
			//actualizar cliente - officer
			cd.setCode("91");
			IntegerValue sv91= new IntegerValue();
			logger.info("savacct "+info.getSavAcctNum());
			sv91.setValue(new BigInteger(info.getSavAcctNum()));
			
			cd.setIntegerValue(sv91);
			ldata.add(cd);
		}
		if(info.getPercentCashLimit()!= null){
			CustomData cd2 = new CustomData();
			//actualizar cliente - officer
			cd2.setCode("90");
			IntegerValue sv90 = new IntegerValue();
			logger.info("perent cash lim "+info.getPercentCashLimit());
			sv90.setValue(new BigInteger(info.getPercentCashLimit()));
			cd2.setIntegerValue(sv90);
			ldata.add(cd2);
		}
		if(info.getFolio()!= null){
			CustomData cd3 = new CustomData();
			StringValue sv94 = new StringValue();
			logger.info("folio "+info.getFolio());
			sv94.setValue(info.getFolio());
			//actualizar cliente - officer
			cd3.setCode("94");
			cd3.setStringValue(sv94);
			ldata.add(cd3);
		}
		
		
		if(info.getUcode2()!= null && "S".equals(info.getUcode2().trim())){
			CustomData cd10 = new CustomData();
			cd10.setCode("10");
			StringValue sv10 = new StringValue();
			sv10.setValue("T");
			cd10.setStringValue(sv10);
			ldata.add(cd10);
			
			
				CustomData cd11 = new CustomData();
				cd11.setCode("11");
				StringValue sv11 = new StringValue();
				sv11.setValue("5");
				cd11.setStringValue(sv11);
				ldata.add(cd11);
			
		}
		customDataReq.setCustomDataUpdates(cdu);
		mntCustomData.setMntCustomDataRequest(customDataReq);
		logger.info("mntCustomDAta");
		MntCustomDataResponse mntCustomDataRes= cliente.mntCustomData(tp, mntCustomData);
		logger.info("despues");
		logger.info("stastu "+mntCustomDataRes.getMntCustomDataResult().getStatus());
		if("999".equals(mntCustomDataRes.getMntCustomDataResult().getStatus())){
			logger.info(mntCustomDataRes.getMntCustomDataResult().getStatusMsg());
			TSYSfaultType fault = mntCustomDataRes.getMntCustomDataResult().getFaults();
			List<TSYSfault> lfaulta =fault.getFault();
			for (TSYSfault sfault : lfaulta) {
				logger.info(sfault.getStatus()+" "+ sfault.getFaultDesc());
			}
		}
		
		cdu = new CustomDataUpdates();
		ldata=cdu.getCustomData();
		logger.info("info.getUcode3() "+info.getUcode3());
		if(info.getUcode3()!= null){
			CustomData cd4 = new CustomData();
			cd4.setCode("80");
			StringValue sv80 = new StringValue();
			logger.info("ucode3 "+info.getUcode3());
			if(info.getUcode3().charAt(1)=='V'){
				sv80.setValue("Vendida");
				
			}else if(info.getUcode3().charAt(1)=='C'){
				sv80.setValue("Colocada");
			}
			cd4.setStringValue(sv80);
			ldata.add(cd4);
		}
		logger.info("info.getFchAsignacion() "+info.getFchAsignacion());
		if(info.getFchAsignacion()!= null){
			CustomData cd64 = new CustomData();
			cd64.setCode("64");
			StringValue sv64 = new StringValue();
			sv64.setValue(info.getFchAsignacion());
			cd64.setStringValue(sv64);
			ldata.add(cd64);
		}
		logger.info("info.getActividadLaboral()" +info.getActividadLaboral());
		if(info.getActividadLaboral()!= null){
			CustomData cd83 = new CustomData();
			cd83.setCode("83");
			StringValue sv83 = new StringValue();
			sv83.setValue(info.getActividadLaboral());
			cd83.setStringValue(sv83);
			ldata.add(cd83);
		}
		logger.info("info.getCoincidenciaPF() "+info.getCoincidenciaPF());
		if(info.getCoincidenciaPF()!= null){
			CustomData cd14 = new CustomData();
			cd14.setCode("14");
			StringValue sv14 = new StringValue();
			sv14.setValue(info.getCoincidenciaPF());
			cd14.setStringValue(sv14);
			ldata.add(cd14);
		}
		customDataReq.setCustomDataUpdates(cdu);
		mntCustomData.setMntCustomDataRequest(customDataReq);
		logger.info("mntCustomData 2");
		mntCustomDataRes= cliente.mntCustomData(tp, mntCustomData);
		logger.info("despues");
		logger.info("stastu "+mntCustomDataRes.getMntCustomDataResult().getStatus());
		if("999".equals(mntCustomDataRes.getMntCustomDataResult().getStatus())){
			logger.info(mntCustomDataRes.getMntCustomDataResult().getStatusMsg());
			TSYSfaultType fault = mntCustomDataRes.getMntCustomDataResult().getFaults();
			List<TSYSfault> lfaulta =fault.getFault();
			for (TSYSfault sfault : lfaulta) {
				logger.info(sfault.getStatus()+" "+ sfault.getFaultDesc() +" "+sfault.getFaultPath());
			}
		}
		
		if(info.getLimiteCredito()!= null){
			MntCreditLmtRequestType mntCreditLmtReq = new MntCreditLmtRequestType();
			mntCreditLmtReq.setKey(account);
			mntCreditLmtReq.setKeyType("cardNbr");
			mntCreditLmtReq.setVersion("1.6.0");
			logger.info("lim cred "+info.getLimiteCredito());
			if(info.getLimiteCredito()!= null){
//				AdjCreditLmtInfo adjCreditLmtInfo = new AdjCreditLmtInfo();
//				Amt amt = new Amt();
//				amt.setCode("MXN");
//				amt.setValue(new BigDecimal(info.getLimiteCredito()));
//				adjCreditLmtInfo.setIncrDecr("Increase");
//				adjCreditLmtInfo.setAmt(amt);
//				mntCreditLmtReq.setAdjCreditLmtInfo(adjCreditLmtInfo);
				UpdateCreditLmtInfo updateCreditLmtInfo = new UpdateCreditLmtInfo();
				AmtNew amtNew = new AmtNew();
				amtNew.setCode("MXN");
				amtNew.setValue(new BigDecimal(info.getLimiteCredito()));
				updateCreditLmtInfo.setAmtNew(amtNew);
				mntCreditLmtReq.setUpdateCreditLmtInfo(updateCreditLmtInfo);
			}
			
			//com.tsys.xmlmessaging.ch2.Boolean b = new com.tsys.xmlmessaging.ch2.Boolean();
			logger.info("buro "+info.getBureauStatus());
//			b.setValue(new Boolean(info.getBureauStatus()));
//			mntCreditLmtReq.setUpdateCreditBureauFlag(b);
			
			//mntCreditLmtReq.get
			MntCreditLmt mntCreditLmt= new MntCreditLmt();
			mntCreditLmt.setMntCreditLmtRequest(mntCreditLmtReq);
			logger.info("mntCreditLmt");
			MntCreditLmtResponse mntCreditLmtRes =cliente.mntCreditLmt(tp, mntCreditLmt);
			logger.info("despues");
			logger.info("statis "+mntCreditLmtRes.getMntCreditLmtResult().getStatus());
			if("999".equals(mntCreditLmtRes.getMntCreditLmtResult().getStatus())){
				logger.info(mntCreditLmtRes.getMntCreditLmtResult().getStatusMsg());
				TSYSfaultType fault = mntCreditLmtRes.getMntCreditLmtResult().getFaults();
				List<TSYSfault> lfaulta =fault.getFault();
				for (TSYSfault sfault : lfaulta) {
					logger.info(sfault.getStatus()+" "+ sfault.getFaultDesc());
				}
			}
		}
			logger.info("cicle "+info.getCicle());
			if(info.getCicle()!= null){
				MntBillingCycle mntBillingCycle = new MntBillingCycle();
				MntBillingCycleRequestType mntBillingCycleReq = new MntBillingCycleRequestType();
				mntBillingCycleReq.setKey(account);
				mntBillingCycleReq.setKeyType("cardNbr");
				mntBillingCycleReq.setVersion("1.1.0");
				BillingCycle billingCycle = new BillingCycle();
				billingCycle.setValue(new BigInteger(info.getCicle()));
				mntBillingCycleReq.setBillingCycle(billingCycle);
				mntBillingCycle.setMntBillingCycleRequest(mntBillingCycleReq);
				logger.info("mntBillingCyle");
				MntBillingCycleResponse mntBillingRes= cliente.mntBillingCycle(tp, mntBillingCycle);
				logger.info("despues");
				logger.info("stasu "+mntBillingRes.getMntBillingCycleResult().getStatus());
				if("999".equals(mntBillingRes.getMntBillingCycleResult().getStatus())){
					logger.info(mntBillingRes.getMntBillingCycleResult().getStatusMsg());
					TSYSfaultType fault = mntBillingRes.getMntBillingCycleResult().getFaults();
					List<TSYSfault> lfaulta =fault.getFault();
					for (TSYSfault sfault : lfaulta) {
						logger.info(sfault.getStatus()+" "+ sfault.getFaultDesc());
					}
				}
			}
			logger.info("bartch "+info.getBranch());
			logger.info("acq "+info.getAcqStrategy());
		if(info.getBranch()!= null){
			MntAcctMiscInfo mntAcctMiscInfo = new MntAcctMiscInfo();
			MntAcctMiscInfoRequestType mntAcctMiscInfoReq = new MntAcctMiscInfoRequestType();
			mntAcctMiscInfoReq.setKey(account);
			mntAcctMiscInfoReq.setKeyType("cardNbr");
			mntAcctMiscInfoReq.setVersion("2.10.0");
			BranchCode bc = new BranchCode();
			bc.setValue(info.getBranch());
			mntAcctMiscInfoReq.setBranchCode(bc);
			if(info.getAcqStrategy()!= null){
				AcquisitionStrategy acq = new AcquisitionStrategy();
				acq.setValue(info.getAcqStrategy());
				mntAcctMiscInfoReq.setAcquisitionStrategy(acq);
			}
			mntAcctMiscInfo.setMntAcctMiscInfoRequest(mntAcctMiscInfoReq);
			logger.info("mntAcctMiscInfo");
			MntAcctMiscInfoResponse mntAcctMiscInfoRes=cliente.mntAcctMiscInfo(tp, mntAcctMiscInfo);
			logger.info("despues");
			logger.info("stataus "+mntAcctMiscInfoRes.getMntAcctMiscInfoResult().getStatus());
			if("999".equals(mntAcctMiscInfoRes.getMntAcctMiscInfoResult().getStatus())){
				logger.info(mntAcctMiscInfoRes.getMntAcctMiscInfoResult().getStatusMsg());
				TSYSfaultType fault = mntAcctMiscInfoRes.getMntAcctMiscInfoResult().getFaults();
				List<TSYSfault> lfaulta =fault.getFault();
				for (TSYSfault sfault : lfaulta) {
					logger.info(sfault.getStatus()+" "+ sfault.getFaultDesc());
				}
			}
		}
		
		logger.info("rfc "+info.getRfc());
		if(info.getBirthDate()!= null){
			MntCustSecureInfo mntCustSecureInfo = new MntCustSecureInfo();
			MntCustSecureInfoRequestType mntCustSecureInfoReq = new MntCustSecureInfoRequestType();
			mntCustSecureInfoReq.setKeyType("cardNbr");
			mntCustSecureInfoReq.setKey(account);
			mntCustSecureInfoReq.setCustID(custId);
			mntCustSecureInfoReq.setVersion("2.2.0");
			DOB dob = new DOB();
			MntdateType mntDate = new MntdateType();
			XMLGregorianCalendar calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar();
	       logger.info("fecha nac "+info.getBirthDate());
			calendar.setYear(Integer.parseInt(info.getBirthDate().substring(4,8)));
			calendar.setMonth(Integer.parseInt(info.getBirthDate().substring(2,4)));
			calendar.setDay(Integer.parseInt(info.getBirthDate().substring(0,2)));
			
			mntDate.setValue(calendar);
			dob.setDate(mntDate);
			mntCustSecureInfoReq.setDOB(dob);
			mntCustSecureInfo.setMntCustSecureInfoRequest(mntCustSecureInfoReq);
			logger.info("mntCustSecureInfo");
			MntCustSecureInfoResponse mntCustSecureInfoRes= cliente.mntCustSecureInfo(tp, mntCustSecureInfo);
			logger.info("despues");
			logger.info("statsu "+mntCustSecureInfoRes.getMntCustSecureInfoResult().getStatus());
			if("999".equals(mntCustSecureInfoRes.getMntCustSecureInfoResult().getStatus())){
				logger.info(mntCustSecureInfoRes.getMntCustSecureInfoResult().getStatusMsg());
				TSYSfaultType fault = mntCustSecureInfoRes.getMntCustSecureInfoResult().getFaults();
				List<TSYSfault> lfaulta =fault.getFault();
				for (TSYSfault sfault : lfaulta) {
					logger.info(sfault.getStatus()+" "+ sfault.getFaultDesc());
				}
			}
		}
		//RFC
		if(info.getRfc()!= null || info.getCurp()!= null){
			//MntCustIdentity mntCustIdentity = new MntCustIdentity();
			
			AddCustIdentity addCustIdentity = new AddCustIdentity();
			AddCustIdentityRequestType addCustIdentityReq = new AddCustIdentityRequestType();
			addCustIdentityReq.setKey(account);
			addCustIdentityReq.setKeyType("cardNbr");
			addCustIdentityReq.setVersion("1.0.0");
			addCustIdentityReq.setCustID(custId);
			addCustIdentityReq.setCatCode("01");
			if(info.getRfc()!= null){
				
				addCustIdentityReq.setID(info.getRfc());
			}
			if(info.getCurp()!= null){
				
				logger.info("curp "+info.getCurp());
				
				addCustIdentityReq.setIssuingEntity(info.getCurp());
			}
			addCustIdentity.setAddCustIdentityRequest(addCustIdentityReq);
			logger.info("addCustIndentity");
			AddCustIdentityResponse mntCustIdentityRes= cliente.addCustIdentity(tp, addCustIdentity);
			logger.info("despues");
			logger.info("status "+mntCustIdentityRes.getAddCustIdentityResult().getStatus());
			if("999".equals(mntCustIdentityRes.getAddCustIdentityResult().getStatus())){
				logger.info(mntCustIdentityRes.getAddCustIdentityResult().getStatusMsg());
				TSYSfaultType fault = mntCustIdentityRes.getAddCustIdentityResult().getFaults();
				List<TSYSfault> lfaulta =fault.getFault();
				for (TSYSfault sfault : lfaulta) {
					logger.info(sfault.getStatus()+" "+ sfault.getFaultDesc() +" "+sfault.getFaultTSYSInfo());
				}
			}
		}
		logger.info("credit status code  "+info.getStatusCode() + " reason  code "+info.getReasonCode() );
		if(info.getStatusCode() !=null&& info.getReasonCode()!= null){
		MntAcctStatus mntAcctStatus = new MntAcctStatus();
		MntAcctStatusRequestType mntAcctStatusReq= new MntAcctStatusRequestType();
		MntAcctStatusReq statusReq = new MntAcctStatusReq();
		mntAcctStatusReq.setKey(account);
		mntAcctStatusReq.setKeyType("cardNbr");
		mntAcctStatusReq.setVersion("1.6.0");
		
		Status status = new Status();
		status.setCode(info.getStatusCode());
		Reason reason = new Reason();
		reason.setValue(info.getReasonCode());
		status.setReason(reason);
		statusReq.setStatus(status);
		mntAcctStatusReq.setMntAcctStatusReq(statusReq);
		mntAcctStatus.setMntAcctStatusRequest(mntAcctStatusReq);
		MntAcctStatusResponse mntAcctStatusResp= cliente.mntAcctStatus(tp, mntAcctStatus);
		logger.info("status "+mntAcctStatusResp.getMntAcctStatusResult().getStatus());
		if("999".equals(mntAcctStatusResp.getMntAcctStatusResult().getStatus())){
			logger.info(mntAcctStatusResp.getMntAcctStatusResult().getStatusMsg());
			TSYSfaultType fault = mntAcctStatusResp.getMntAcctStatusResult().getFaults();
			List<TSYSfault> lfaulta =fault.getFault();
			for (TSYSfault sfault : lfaulta) {
				logger.info(sfault.getStatus()+" "+ sfault.getFaultDesc());
			}
		}
		}
		
		AddCustNotes addCustNotes = new AddCustNotes();
		AddCustNotesRequestType addCustNotesReq = new AddCustNotesRequestType();
		addCustNotesReq.setKey(account);
		addCustNotesReq.setKeyType("cardNbr");
		addCustNotesReq.setVersion("1.0.0");
		addCustNotesReq.setCustType("Primary");
		AddCustNotesRequestType.NotesText noteText = new AddCustNotesRequestType.NotesText();
		List<NoteText> lNoteText =noteText.getNoteText();
		NoteText nt = new NoteText();
		nt.setSeqNbr(1);
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yy");
		String mensaje="SE ASIGNA " + info.getProducto()+" "+dateFormat2.format(new Date())+ " CON FOLIO "+info.getClienteUnico();
		nt.setText(mensaje);
		lNoteText.add(nt);
		addCustNotesReq.setNotesText(noteText);
		addCustNotes.setAddCustNotesRequest(addCustNotesReq);
		AddCustNotesResponse addCustNotesResp= cliente.addCustNotes(tp, addCustNotes);
		if("999".equals(addCustNotesResp.getAddCustNotesResult().getStatus())){
			logger.info(addCustNotesResp.getAddCustNotesResult().getStatusMsg());
			TSYSfaultType fault = addCustNotesResp.getAddCustNotesResult().getFaults();
			List<TSYSfault> lfaulta =fault.getFault();
			for (TSYSfault sfault : lfaulta) {
				logger.info(sfault.getStatus()+" "+ sfault.getFaultDesc());
			}
		}
		return "Los datos demograficos han sido actualizados";
	
		} catch (Exception e) {
			logger.error("ERROR al mandar mensaje en servicio NCM (CollectionMessage) account "+ account + " - " +e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
		
}
