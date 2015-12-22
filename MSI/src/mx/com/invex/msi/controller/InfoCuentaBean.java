package mx.com.invex.msi.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.component.html.HtmlSelectOneListbox;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.rpc.ServiceException;

import mx.com.interware.spira.ls.facade.igbinaenca.ArrayUDataDTO;
import mx.com.interware.spira.ls.facade.igbinaenca.InfoDemograficaINA;
import mx.com.interware.spira.ls.facade.igbinaenca.InfoEncabezado;
import mx.com.interware.spira.ls.facade.igbinaenca.InfoGeneralTHIGB;
import mx.com.interware.spira.ls.facade.igbinaenca.ResultadoIGBINAEnca;
import mx.com.interware.spira.tsysws.si02.SI02FinalResponseDTO;
import mx.com.interware.spira.web.si07.Si07VariableAreaDTO;
import mx.com.invex.msi.model.ArchivoDetalle;
import mx.com.invex.msi.model.Campania;
import mx.com.invex.msi.model.Comercio;
import mx.com.invex.msi.model.Compra;
import mx.com.invex.msi.model.Parametro;
import mx.com.invex.msi.model.Producto;
import mx.com.invex.msi.model.ProductoTs2;
import mx.com.invex.msi.model.Promocion;
import mx.com.invex.msi.service.ArchivoDetalleService;
import mx.com.invex.msi.service.BitacoraService;
import mx.com.invex.msi.service.CampaniaService;
import mx.com.invex.msi.service.CompraService;
import mx.com.invex.msi.service.ParametroService;
import mx.com.invex.msi.service.ProductoService;
import mx.com.invex.msi.service.ProductoTs2Service;
import mx.com.invex.msi.util.CryptoAES;
import mx.com.invex.msi.util.MSIConstants;
import mx.com.invex.msi.util.MSIException;
import mx.com.invex.msi.util.MSIHelper;
import mx.com.invex.msi.ws.ClientLSWS;
import mx.com.invex.msi.ws.ClientTS2;
import mx.com.invex.msi.ws.ClienteGetMovs;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tsys.xmlmessaging.ch.IASstatusCodeResponseDataType;
import com.tsys.xmlmessaging.ch.ICAaddrInfoResponseDataType;
import com.tsys.xmlmessaging.ch.ICIcustInfoResponseDataType;
import com.tsys.xmlmessaging.ch.IPIpmtInfoResponseDataType;
import com.tsys.xmlmessaging.ch.ITRtranDetailResponseDataType;
import com.tsys.xmlmessaging.ch.InqAcctStatus;
import com.tsys.xmlmessaging.ch.InqAcctStatusRequestType;
import com.tsys.xmlmessaging.ch.InqAcctStatusResponseType;
import com.tsys.xmlmessaging.ch.InqCreditLmt;
import com.tsys.xmlmessaging.ch.InqCreditLmtRequestType;
import com.tsys.xmlmessaging.ch.InqCreditLmtResponseType;
import com.tsys.xmlmessaging.ch.InqCustAddr;
import com.tsys.xmlmessaging.ch.InqCustAddrRequestType;
import com.tsys.xmlmessaging.ch.InqCustAddrResponseType;
import com.tsys.xmlmessaging.ch.InqCustInfo;
import com.tsys.xmlmessaging.ch.InqCustInfoRequestType;
import com.tsys.xmlmessaging.ch.InqCustInfoResponseType;
import com.tsys.xmlmessaging.ch.InqCustomData;
import com.tsys.xmlmessaging.ch.InqCustomDataRequestType;
import com.tsys.xmlmessaging.ch.InqCustomDataRequestType.CustomDataCodes;
import com.tsys.xmlmessaging.ch.InqCustomDataResponseType;
import com.tsys.xmlmessaging.ch.InqGeneralAcct;
import com.tsys.xmlmessaging.ch.InqGeneralAcctRequestType;
import com.tsys.xmlmessaging.ch.InqGeneralAcctResponseType;
import com.tsys.xmlmessaging.ch.InqGeneralBal;
import com.tsys.xmlmessaging.ch.InqGeneralBalRequestType;
import com.tsys.xmlmessaging.ch.InqGeneralBalResponseType;
import com.tsys.xmlmessaging.ch.InqPmtInfo;
import com.tsys.xmlmessaging.ch.InqPmtInfoRequestType;
import com.tsys.xmlmessaging.ch.InqPmtInfoResponseType;
import com.tsys.xmlmessaging.ch.InqRealTimeAuthSys;
import com.tsys.xmlmessaging.ch.InqRealTimeAuthSysRequestType;
import com.tsys.xmlmessaging.ch.InqRealTimeAuthSysResponseType;
import com.tsys.xmlmessaging.ch.InqTrans;
import com.tsys.xmlmessaging.ch.InqTransRequestType;
import com.tsys.xmlmessaging.ch.InqTransResponse;
import com.tsys.xmlmessaging.ch.InqTransResponseType;
import com.tsys.xmlmessaging.ch.TSYSfault;
import com.tsys.xmlmessaging.ch.TSYSfaultType;
import com.tsys.xmlmessaging.ch.TSYSprofileType;
@Component
@Scope("session")
public class InfoCuentaBean extends MessagesMBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(InfoCuentaBean.class);
	@Autowired
	ParametroService parametroService;
	
	@Autowired
	CampaniaService campaniaService;
	
	@Autowired
	CompraService compraService;
	
	@Autowired
	ProductoService productoService;
	
	@Autowired
	ArchivoDetalleService archivoDetalleService;
	
	@Autowired
	ProductoTs2Service productoTs2Service;
	
	private InfoDemograficaINA infoDemo;
	private InfoGeneralTHIGB infoCuenta;
	private InfoEncabezado infoEnca;
	private String tipoProd;
	private String cuenta;
	private boolean prog0;
	private String cuentaPU;
	public String getCuentaPU() {
		return cuentaPU;
	}

	public void setCuentaPU(String cuentaPU) {
		this.cuentaPU = cuentaPU;
	}

	private HtmlSelectBooleanCheckbox chkSelect;
	private HtmlSelectOneListbox cmbPromos;
	private List<Compra> compras;
	private double saldoRev;
	private double saldoRevAnterior;
	
	private double saldoNoIntereses;
	private Si07VariableAreaDTO[] movsITH;
	private boolean pantallaUnica;
	
	
	
	
	public boolean isPantallaUnica() {
		return pantallaUnica;
	}

	public void setPantallaUnica(boolean pantallaUnica) {
		this.pantallaUnica = pantallaUnica;
	}

	public Si07VariableAreaDTO[] getMovsITH() {
		return movsITH;
	}

	public void setMovsITH(Si07VariableAreaDTO[] movsITH) {
		this.movsITH = movsITH;
	}

	public HtmlSelectOneListbox getCmbPromos() {
		return cmbPromos;
	}

	public void setCmbPromos(HtmlSelectOneListbox cmbPromos) {
		this.cmbPromos = cmbPromos;
	}

	@Autowired
	BitacoraService bitacoraService;

	public double getSaldoRevAnterior() {
		return saldoRevAnterior;
	}

	public void setSaldoRevAnterior(double saldoRevAnterior) {
		this.saldoRevAnterior = saldoRevAnterior;
	}

	public double getSaldoRev() {
		return saldoRev;
	}

	public void setSaldoRev(double saldoRev) {
		this.saldoRev = saldoRev;
	}

	public InfoDemograficaINA getInfoDemo() {
		return infoDemo;
	}

	public void setInfoDemo(InfoDemograficaINA infoDemo) {
		this.infoDemo = infoDemo;
	}
	
	
	public InfoGeneralTHIGB getInfoCuenta() {
		return infoCuenta;
	}

	public void setInfoCuenta(InfoGeneralTHIGB infoCuenta) {
		this.infoCuenta = infoCuenta;
	}

	public InfoEncabezado getInfoEnca() {
		return infoEnca;
	}

	public void setInfoEnca(InfoEncabezado infoEnca) {
		this.infoEnca = infoEnca;
	}

	
	public String getCuenta() {
		return cuenta;
	}

	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	public String getTipoProd() {
		return tipoProd;
	}

	public void setTipoProd(String tipoProd) {
		this.tipoProd = tipoProd;
	}

	public boolean isProg0() {
		return prog0;
	}

	public void setProg0(boolean prog0) {
		this.prog0 = prog0;
	}
	
	
	public List<Compra> getCompras() {
		return compras;
	}

	public void setCompras(List<Compra> compras) {
		this.compras = compras;
	}
	
	
	public HtmlSelectBooleanCheckbox getChkSelect() {
		return chkSelect;
	}

	public void setChkSelect(HtmlSelectBooleanCheckbox chkSelect) {
		this.chkSelect = chkSelect;
	}

	
	public double getSaldoNoIntereses() {
		return saldoNoIntereses;
	}

	public void setSaldoNoIntereses(double saldoNoIntereses) {
		this.saldoNoIntereses = saldoNoIntereses;
	}
	
	public void preRenderEvent() throws MSIException{
		logger.info("cuenta "+cuentaPU);
		
		if(cuentaPU!=null){
			try {
				String cuentaDesEnc= CryptoAES.decrypt2(cuentaPU);
				logger.info("post bacck"+ FacesContext.getCurrentInstance().isPostback());
				if(!FacesContext.getCurrentInstance().isPostback()){
					this.buscarPorNoCuenta(cuentaDesEnc);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}

	private String nombreCompleto;
	private  String rfc;
	private String fechaNacimiento;
	private  String calleNum;
	private String colonia;
	private String munEdo;
	private String codigoPostal;
	private String email;
	private String telCel;
	private String creditLimit;
	private String creditoDisponible;
	private String pagoMinimo;
	private String saldoAlDia;
	private String pagoNoIntereses;
	private  String fechaLimitePago;
	private Double dispCompras;
	
	
	public Double getDispCompras() {
		return dispCompras;
	}

	public void setDispCompras(Double dispCompras) {
		this.dispCompras = dispCompras;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getCalleNum() {
		return calleNum;
	}

	public void setCalleNum(String calleNum) {
		this.calleNum = calleNum;
	}

	public String getColonia() {
		return colonia;
	}

	public void setColonia(String colonia) {
		this.colonia = colonia;
	}

	public String getMunEdo() {
		return munEdo;
	}

	public void setMunEdo(String munEdo) {
		this.munEdo = munEdo;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelCel() {
		return telCel;
	}

	public void setTelCel(String telCel) {
		this.telCel = telCel;
	}

	public String getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(String creditLimit) {
		this.creditLimit = creditLimit;
	}

	public String getCreditoDisponible() {
		return creditoDisponible;
	}

	public void setCreditoDisponible(String creditoDisponible) {
		this.creditoDisponible = creditoDisponible;
	}

	public String getPagoMinimo() {
		return pagoMinimo;
	}

	public void setPagoMinimo(String pagoMinimo) {
		this.pagoMinimo = pagoMinimo;
	}

	public String getSaldoAlDia() {
		return saldoAlDia;
	}

	public void setSaldoAlDia(String saldoAlDia) {
		this.saldoAlDia = saldoAlDia;
	}

	public String getPagoNoIntereses() {
		return pagoNoIntereses;
	}

	public void setPagoNoIntereses(String pagoNoIntereses) {
		this.pagoNoIntereses = pagoNoIntereses;
	}

	public String getFechaLimitePago() {
		return fechaLimitePago;
	}

	public void setFechaLimitePago(String fechaLimitePago) {
		this.fechaLimitePago = fechaLimitePago;
	}

	public String buscarPorNoCuenta(String cuentaParam) throws MSIException{
		try {
			
			cuenta=cuentaParam;
			logger.info("buscarPorNoCuenta "+cuenta);
			
			if(productoTs2Service.cuentaITAU(cuenta)){
				
				HttpSession session = (HttpSession)  getContext().getExternalContext().getSession(false);
				session.setAttribute("cuenta", cuenta);
				ClientTS2 cts2= new ClientTS2();
				 TSYSprofileType tp = new TSYSprofileType();
				 tp.setClientID("7401");
				 tp.setUserID("Invex");
				 tp.setVendorID("00000000");
				 InqAcctStatus inqAcctStatus = new InqAcctStatus();
				 InqAcctStatusRequestType inqAcctStatusReq = new InqAcctStatusRequestType();
				 //key="4196910074855913" keyType="cardNbr" version="1.0.0"/>
				 inqAcctStatusReq.setKey(cuenta);
				 inqAcctStatusReq.setKeyType("cardNbr");
				 inqAcctStatusReq.setVersion("1.0.0");
				 inqAcctStatus.setInqAcctStatusRequest(inqAcctStatusReq);
				
				
				InqAcctStatusResponseType resSt= cts2.inqAcctStatus(tp,inqAcctStatus).getInqAcctStatusResult();
				logger.info("inqAcctStatus");
				if(!"000".equals(resSt.getStatus())){
					logger.info(resSt.getStatusMsg());
					TSYSfaultType fault = resSt.getFaults();
					List<TSYSfault> lfaulta =fault.getFault();
					for (TSYSfault sfault : lfaulta) {
						logger.info(sfault.getStatus()+" "+ sfault.getFaultDesc());
						sendErrorMessageToUser(sfault.getFaultDesc());
						return null;
					}
				}
				
				
				IASstatusCodeResponseDataType statusCode =resSt.getStatusCode().getValue();
				for (IASstatusCodeResponseDataType.Status status : statusCode.getStatus()) {
					if(MSIHelper.statusCodes.contains(status.getCode()+" "+status.getReason())){
					
						//no lo voa deja pasar
						logger.info("Tiene reason code "+status.getCode() + " " +status.getReason());
						sendErrorMessageToUser("La cuenta presenta un bloqueo ");
						return null;
						
					}
					//saca tipo de cuenta Primary, AUTOHARIZED(ADICIONAL)
					//cts2.inqCustInfo(cuenta).getCustInfo().get(0).getCustType();
				}
				
				 InqCustInfo inqCustInfo = new InqCustInfo();
				 InqCustInfoRequestType inqCustInfoReq = new InqCustInfoRequestType();
				 //key="4196910074855913" keyType="cardNbr" version="1.0.0"/>
				 inqCustInfoReq.setKey(cuenta);
				 inqCustInfoReq.setKeyType("cardNbr");
				 inqCustInfoReq.setVersion("2.10.0");
				 inqCustInfo.setInqCustInfoRequest(inqCustInfoReq);
				
				InqCustInfoResponseType custInfoResponse = cts2.inqCustInfo(tp,inqCustInfo).getInqCustInfoResult();
				logger.info("custInfoResponse");
				if("999".equals(custInfoResponse.getStatus())){
					logger.info(custInfoResponse.getStatusMsg());
					TSYSfaultType fault = custInfoResponse.getFaults();
					List<TSYSfault> lfaulta =fault.getFault();
					for (TSYSfault sfault : lfaulta) {
						logger.info(sfault.getStatus()+" "+ sfault.getFaultDesc());
					}
				}
				
				SimpleDateFormat sdf2 = new SimpleDateFormat("dd MMMM 'de' yyyy", new Locale("ES"));
                List<ICIcustInfoResponseDataType> listCustInfos = custInfoResponse.getCustInfo();
                for (ICIcustInfoResponseDataType custInfo : listCustInfos) {
                    
                    if(custInfo.getCustType().equals("Primary") ){                
                                                                   
                                    
                                   nombreCompleto= custInfo.getCustName().getWhole() == null?"": custInfo.getCustName().getWhole()  ;
                                    email= custInfo.getEmailAddr()  == null?"":  custInfo.getEmailAddr().getValue() ;
                               fechaNacimiento= custInfo.getDateBirth() == null?"":  sdf2.format(custInfo.getDateBirth().getValue().getValue().toGregorianCalendar().getTime());
                                    if(custInfo.getPhnNbrs().getValue().getAlt1()!= null){
                                    telCel= ""+custInfo.getPhnNbrs().getValue().getAlt1().getValue();  
                                    }
                                    
                                             
                                    rfc=custInfo.getVerifID().getValue()  == null?"": custInfo.getVerifID().getValue();
                                    
                    }
                }
                
                InqCustAddr inqCustAddr = new InqCustAddr();
       		 InqCustAddrRequestType inqCustAddrReq = new InqCustAddrRequestType();
       		
       		 inqCustAddrReq.setKey(cuenta);
       		 inqCustAddrReq.setKeyType("cardNbr");
       		 inqCustAddrReq.setVersion("1.0.0");
       		 inqCustAddr.setInqCustAddrRequest(inqCustAddrReq);
               InqCustAddrResponseType custAdrrResponse = cts2.inqCustAddr(tp,inqCustAddr).getInqCustAddrResult();
               logger.info("custAdrrResponse");
				if("999".equals(custAdrrResponse.getStatus())){
					logger.info(custAdrrResponse.getStatusMsg());
					TSYSfaultType fault = custAdrrResponse.getFaults();
					List<TSYSfault> lfaulta =fault.getFault();
					for (TSYSfault sfault : lfaulta) {
						logger.info(sfault.getStatus()+" "+ sfault.getFaultDesc());
					}
				}
                List<ICAaddrInfoResponseDataType> listaAddr =  custAdrrResponse.getAddrInfo();
                if (listaAddr.size() > 0 ){
                    
                    for (ICAaddrInfoResponseDataType myCustAddrList : listaAddr) {
                                    
                                    if( "01".equals(myCustAddrList.getAddrCode())){
                                                   calleNum=myCustAddrList.getCustAddr().getAddrLine1() == null?"": myCustAddrList.getCustAddr().getAddrLine1();
                                                   
                                                   colonia= myCustAddrList.getCustAddr().getAddrLine2() == null?"": myCustAddrList.getCustAddr().getAddrLine2();
                                                   munEdo= myCustAddrList.getCustAddr().getCity() == null?"": myCustAddrList.getCustAddr().getCity();
                                                   codigoPostal=""+ myCustAddrList.getCustAddr().getPostalCode();
                                    
                                    }
                                    
                    }
    
    }
                

           	 InqGeneralBal inqGeneralBal = new InqGeneralBal();
    		 InqGeneralBalRequestType inqGeneralBalReq = new InqGeneralBalRequestType();
    		 
    		 inqGeneralBalReq.setKey(cuenta);
    		 inqGeneralBalReq.setKeyType("cardNbr");
    		 inqGeneralBalReq.setVersion("2.5.0");
    		 inqGeneralBal.setInqGeneralBalRequest(inqGeneralBalReq);
                InqGeneralBalResponseType gralBalRes = cts2.inqGeneralBal(tp,inqGeneralBal).getInqGeneralBalResult();
           
                logger.info("gralBalRes status "+gralBalRes.getStatus());
				if(!"000".equals(gralBalRes.getStatus())){
					logger.info(gralBalRes.getStatusMsg());
					TSYSfaultType fault =gralBalRes.getFaults();
					List<TSYSfault> lfaulta =fault.getFault();
					for (TSYSfault sfault : lfaulta) {
						logger.info(sfault.getStatus()+" "+ sfault.getFaultDesc());
					}
				}
                
               saldoAlDia= ""+ (gralBalRes.getGeneralBalInfo().getValue().getBalInfo().getValue().getCurr() == null ?"0": gralBalRes.getGeneralBalInfo().getValue().getBalInfo().getValue().getCurr().getValue() ) ;
            if(gralBalRes.getGeneralBalInfo().getValue().getPmtInfo()!= null){
				fechaLimitePago= sdf.format(gralBalRes.getGeneralBalInfo().getValue().getPmtInfo().getValue().getDateDue().getValue().getValue().toGregorianCalendar().getTime());
				if(gralBalRes.getGeneralBalInfo().getValue().getPmtInfo().getValue().getStmtMin() == null){
					sendInfoMessageToUser("Su pago mínimo se verá reflejado hasta el siguiente corte.");
				}
				pagoMinimo= "" +(gralBalRes.getGeneralBalInfo().getValue().getPmtInfo().getValue().getStmtMin() == null?"0": gralBalRes.getGeneralBalInfo().getValue().getPmtInfo().getValue().getStmtMin().getValue().getValue().doubleValue());
		           
            }
                
               
                InqCreditLmt inqCreditLmt = new InqCreditLmt();
       		 InqCreditLmtRequestType inqCreditLmtReq = new InqCreditLmtRequestType();
       		 
       		 inqCreditLmtReq.setKey(cuenta);
       		 inqCreditLmtReq.setKeyType("cardNbr");
       		 inqCreditLmtReq.setVersion("1.0.0");
       		 inqCreditLmt.setInqCreditLmtRequest(inqCreditLmtReq);
                InqCreditLmtResponseType lmtResponse= cts2.inqCreditLmt(tp,inqCreditLmt).getInqCreditLmtResult();
                logger.info("lmtResponse");
				if(!"000".equals(lmtResponse.getStatus())){
					logger.info(lmtResponse.getStatusMsg());
					TSYSfaultType fault =lmtResponse.getFaults();
					List<TSYSfault> lfaulta =fault.getFault();
					for (TSYSfault sfault : lfaulta) {
						logger.info(sfault.getStatus()+" "+ sfault.getFaultDesc());
					}
				}
                
				creditLimit="" + (lmtResponse.getCreditLmtInfo().getValue().getCreditLmt().getValue().getAmt().getValue() == null?"0": lmtResponse.getCreditLmtInfo().getValue().getCreditLmt().getValue().getAmt().getValue().doubleValue() );
                //creditLimit="" + (lmtResponse.getCreditLmtInfo().getValue().getCashLmt().getValue().getAmt().getValue() == null?"0": lmtResponse.getCreditLmtInfo().getValue().getCashLmt().getValue().getAmt().getValue().getValue().doubleValue() );
                
                InqRealTimeAuthSys inqRealTimeAuthSys = new InqRealTimeAuthSys();
                InqRealTimeAuthSysRequestType inqRealTimeAuthSysReq= new InqRealTimeAuthSysRequestType();
                inqRealTimeAuthSysReq.setKey(cuenta);
                inqRealTimeAuthSysReq.setKeyType("cardNbr");
                inqRealTimeAuthSysReq.setVersion("1.0.0");
                inqRealTimeAuthSys.setInqRealTimeAuthSysRequest(inqRealTimeAuthSysReq);
                InqRealTimeAuthSysResponseType inqRealTimeAuthSysResp = cts2.inqRealTimeAuthSys(tp, inqRealTimeAuthSys).getInqRealTimeAuthSysResult();
              
                if(!"000".equals( inqRealTimeAuthSysResp.getStatus())){
					logger.info( inqRealTimeAuthSysResp.getStatusMsg());
					TSYSfaultType fault = inqRealTimeAuthSysResp.getFaults();
					List<TSYSfault> lfaulta =fault.getFault();
					for (TSYSfault sfault : lfaulta) {
						logger.info(sfault.getStatus()+" "+ sfault.getFaultDesc());
					}
				}else{
					BigDecimal amtMoney =inqRealTimeAuthSysResp.getRealTimeAuthInfo().getValue().getAvail().getAmtMoney().getValue();
					creditoDisponible=""+amtMoney.doubleValue();
				}
                
                InqPmtInfo inqPmtInfo = new InqPmtInfo();
       		 InqPmtInfoRequestType inqPmtInfoReq = new InqPmtInfoRequestType();
       		
       		 inqPmtInfoReq.setKey(cuenta);
       		 inqPmtInfoReq.setKeyType("cardNbr");
       		 inqPmtInfoReq.setVersion("1.2.0");
       		 inqPmtInfoReq.setCycleType("Current");
       		 inqPmtInfo.setInqPmtInfoRequest(inqPmtInfoReq);
                InqPmtInfoResponseType pmtInfoResponse = cts2.inqPmtInfo(tp,inqPmtInfo).getInqPmtInfoResult();
                logger.info("pmtInfoResponse");
				if(!"000".equals(pmtInfoResponse.getStatus())){
					logger.info(pmtInfoResponse.getStatusMsg());
					TSYSfaultType fault =pmtInfoResponse.getFaults();
					List<TSYSfault> lfaulta =fault.getFault();
					for (TSYSfault sfault : lfaulta) {
						logger.info(sfault.getStatus()+" "+ sfault.getFaultDesc());
					}
				}else{
	                List<IPIpmtInfoResponseDataType> pmtInfo=pmtInfoResponse.getPmtInfo();
	                for (IPIpmtInfoResponseDataType ipIpmtInfoResp : pmtInfo) {
	                	BigDecimal ppNI =ipIpmtInfoResp.getAmtProjectedPaidInFull().getValue().getValue();
	                	 pagoNoIntereses=ppNI.toString();
					}
				}
                
                
                

                InqGeneralAcctRequestType req = new InqGeneralAcctRequestType();
           	 req.setVersion("2.19.0");
           	 req.setKey(cuenta);
           	 req.setKeyType("cardNbr");
           	 InqGeneralAcct inqGeneralAcct = new InqGeneralAcct();
           	 inqGeneralAcct.setInqGeneralAcctRequest(req);
				InqGeneralAcctResponseType res= cts2.inqGeneralAcct(tp,inqGeneralAcct).getInqGeneralAcctResult();
				 logger.info("InqGeneralAcct");
					if("999".equals(res.getStatus())){
						logger.info(res.getStatusMsg());
						TSYSfaultType fault =res.getFaults();
						List<TSYSfault> lfaulta =fault.getFault();
						for (TSYSfault sfault : lfaulta) {
							logger.info(sfault.getStatus()+" "+ sfault.getFaultDesc());
						}
					}
				
				String tpc = res.getAcctGeneralInfo().getValue().getTSYSProductCode() == null?"": res.getAcctGeneralInfo().getValue().getTSYSProductCode() ;
				String cpc = res.getAcctGeneralInfo().getValue().getClientProductCode() == null?"": res.getAcctGeneralInfo().getValue().getClientProductCode().getValue();
				logger.info("tpc "+tpc +" cpc " +cpc );
				ProductoTs2 prodts2 = productoTs2Service.getProductoItau(cuenta, tpc, cpc);
				if(prodts2!= null){
					tipoProd =prodts2.getProducto();
				}
			     logger.info("tipo prod "+tipoProd);
               
				int cycle=res.getAcctGeneralInfo().getValue().getBillingCycle().getValue();

				DecimalFormat df2 = new DecimalFormat( "00" );
				//calcular corte anterior
				Calendar diaCorte = Calendar.getInstance();
				diaCorte.set(Calendar.DAY_OF_MONTH, cycle);
				Date fechaFin = new Date();
				Date fechaIn = new Date();
				//Date fechaUltimoCorte = new Date();
				if(new GregorianCalendar().after(diaCorte)){
					fechaFin = diaCorte.getTime();
					
					diaCorte.add(Calendar.MONTH, -1);
					fechaIn = diaCorte.getTime();
				}else{
					diaCorte.add(Calendar.MONTH, -1);
					fechaFin = diaCorte.getTime();
					diaCorte.add(Calendar.MONTH, -1);
					fechaIn = diaCorte.getTime();
				}
				//fechaUltimoCorte =fechaFin;
				Double montoComprasMes = compraService.getMontoComprasByDateRange(cuenta, fechaIn, fechaFin,"IPS");
				if(montoComprasMes!= null){
					pagoNoIntereses=""+(new Double(pagoNoIntereses)-montoComprasMes);
					}
				
				GregorianCalendar hoy = new GregorianCalendar();
				String hoyStr=sdf.format(hoy.getTime());
				hoy.setTime(sdf.parse(hoyStr));
				
				// calcular prog
				int numDiasProg0=0;
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
				InqCustomDataResponseType customDataResp= cts2.inqCustomData(tp, inqCustomData).getInqCustomDataResult();
				if(!"000".equals( customDataResp.getStatus())){
					logger.info( customDataResp.getStatusMsg());
					TSYSfaultType fault = customDataResp.getFaults();
					List<TSYSfault> lfaulta =fault.getFault();
					for (TSYSfault sfault : lfaulta) {
						logger.info(sfault.getStatus()+" "+ sfault.getFaultDesc());
					}
				}else{
					if(customDataResp.getCustomData()!= null){
						if(customDataResp.getCustomData().getValue().getCode61()!= null){
							String strFchVenProgCero =customDataResp.getCustomData().getValue().getCode61().getValue();
							logger.info("fecha vencimiento prog cero "+strFchVenProgCero);
							SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");
							Date dateFchVen = sdf.parse(strFchVenProgCero);
							
							Calendar calFchVen = Calendar.getInstance();
							calFchVen.setTime(dateFchVen);
							
							if(!hoy.after(calFchVen)){
								logger.info("tien prog cero");
								this.prog0=true;
								//15 dias o menos desde que contrato el programa cero?
								if(customDataResp.getCustomData().getValue().getCode81()!= null){
									String strFchInProgCero =customDataResp.getCustomData().getValue().getCode81().getValue();
									logger.info("fecha in prog cero "+strFchInProgCero);
									Date dateFchIn = sdf.parse(strFchInProgCero);
									Calendar fechaInProgCero = Calendar.getInstance();
									fechaInProgCero.setTime(dateFchIn);
									long diasDesdeQueTieneProgCero=(hoy.getTimeInMillis()-fechaInProgCero.getTimeInMillis())/(1000*60*60*24);
									if(diasDesdeQueTieneProgCero<=15){
										//numdias es igual al numero de dias
										numDiasProg0=(int) (diasDesdeQueTieneProgCero+2);
									}
									
								}
								
							}
						}
					}
					
				}
				//fin prog0
				//inciia cmapañas compras
				//compras Extr
				//trae campania compras internacionales para no pro cero
				List<Campania> campExt=campaniaService.getCampaniaByTipo("campania.ext.ts2");
				
				List<Double> montosExt=new ArrayList<Double>();
				for (Campania camp : campExt) {
					Set<Promocion> setPromos=camp.getPromociones();
					for (Promocion promocion : setPromos) {
						montosExt.add(promocion.getMonto());
					}
				}
				Collections.sort(montosExt);
				
				Set<Promocion> promosCampExt=null;
				double montoMinCompras = 0;
				int numDiasCampExt=0;
				for (Campania campania : campExt) {
					
						numDiasCampExt=campania.getNumMaxDiasRegistro();
					
					promosCampExt= campania.getPromociones();


					for (Promocion promo : promosCampExt) {
						montoMinCompras = promo.getMonto();
						break;
					}

				}

				//obternr compras extr
				GregorianCalendar  antes = new GregorianCalendar();
				antes.set(Calendar.HOUR_OF_DAY, 0);
				antes.set(Calendar.MINUTE, 0);
				antes.set(Calendar.SECOND, 0);
				antes.set(Calendar.MILLISECOND, 0);
				

				antes.add(Calendar.DAY_OF_YEAR, -numDiasCampExt);
				logger.info("antes "+antes.getTime());
				InqTrans inqTrans = new InqTrans();
				InqTransRequestType reqInqTrans = new InqTransRequestType();
				
				reqInqTrans.setOnlyForeign(false);
				reqInqTrans.setOnlyCurr(true);
				reqInqTrans.setVersion("1.9.0");
				reqInqTrans.setKey(cuenta);
				reqInqTrans.setKeyType("cardNbr");
				reqInqTrans.setPageItems(300);
				inqTrans.setInqTransRequest(reqInqTrans);
				InqTransResponseType respExt =cts2.inqTrans(tp, inqTrans).getInqTransResult();
				
				if(!"000".equals( respExt.getStatus())){
					logger.info( respExt.getStatusMsg());
					TSYSfaultType fault = respExt.getFaults();
					List<TSYSfault> lfaulta =fault.getFault();
					for (TSYSfault sfault : lfaulta) {
						logger.info(sfault.getStatus()+" "+ sfault.getFaultDesc());
					}
				}
				compras = new ArrayList<Compra>();
				List<Compra> comprasComercios = comprasCampComercios(cuenta, null,null,true);
				if(comprasComercios!= null && !comprasComercios.isEmpty()){
					logger.info("compras comercios "+comprasComercios.size());
					compras.addAll(comprasComercios);
				}else{
					logger.info("no hay compras comercios");
				}
				logger.info("buscar compras extranjero corte actual monto min "+montoMinCompras +" de fecha >=  "+ antes.getTime());
				for (ITRtranDetailResponseDataType td : respExt.getTranDetail()) {
					logger.info("CT "+td.getTranCode() + " monto "+td.getAmtTran().getValue().getValue().doubleValue()+" tran date "+td.getDateTran().getValue().getValue() +" desc "+td.getMerchantInfo().getValue().getDBAName().getValue());
					if("3001".equals(td.getTranCode()) || "1001".equals(td.getTranCode())){
						if(montoMinCompras<= td.getAmtTran().getValue().getValue().doubleValue()){
							if((td.getDateTran().getValue().getValue().toGregorianCalendar().after(antes) || td.getDateTran().getValue().getValue().toGregorianCalendar().compareTo(antes)==0) ){
								logger.info("la compra se cumplio condiciones y se agrego");
								Compra compra = new Compra();
								compra.setCuenta(cuenta);
								compra.setCodigoTransaccion(Integer.parseInt(td.getTranCode()));
								compra.setFechaCompra(td.getDateTran().getValue().getValue().toGregorianCalendar().getTime());
								compra.setMonto(td.getAmtTran().getValue().getValue().doubleValue());
								compra.setDescripcion(td.getMerchantInfo().getValue().getDBAName().getValue());
								compra.setNumRefTran(td.getRefNbr().getValue());
								compra.setTipoTransaccion("ITA");
								compra.setIdEdoPromocion(compraService.getStatusCompraItau(compra));
								for (Promocion promo : promosCampExt) {
									if("si".equalsIgnoreCase(promo.getProgramaCero())){
										if(prog0 && compra.getMonto().doubleValue()>= promo.getMonto().doubleValue()){
											compra.addComboPromo(promo.getPlazoMeses()+" "+promo.getDescripcion(), promo);
										}
									}else{
										if(compra.getMonto().doubleValue()>= promo.getMonto().doubleValue()){
	
											compra.addComboPromo(promo.getPlazoMeses()+" "+promo.getDescripcion(), promo);
										}
									}
								}
								compras.add(compra);

							}
						}
					}
				}//for compras corte actual
				
			
				InqTransRequestType reqInqTrans2 = new InqTransRequestType();
				InqTrans igrala = new InqTrans();
				reqInqTrans2.setVersion("1.9.0");
				reqInqTrans2.setKey(cuenta);
				reqInqTrans2.setKeyType("cardNbr");
				reqInqTrans2.setPageItems(300);
				com.tsys.xmlmessaging.ch.InqTransRequestType.CycleRange cycleRange = new  com.tsys.xmlmessaging.ch.InqTransRequestType.CycleRange();
				XMLGregorianCalendar calendarIn = DatatypeFactory.newInstance().newXMLGregorianCalendar(antes);
				XMLGregorianCalendar calendarFin = DatatypeFactory.newInstance().newXMLGregorianCalendar(hoy);
				cycleRange.setFrom(calendarIn);
				cycleRange.setTo(calendarFin);          
				reqInqTrans2.setCycleRange(cycleRange);
				reqInqTrans2.setOnlyForeign(false);
				igrala.setInqTransRequest(reqInqTrans2);

				InqTransResponse resp= cts2.inqTrans(tp, igrala);
				logger.info("se traen compras entre el "+antes.getTime() +" y el "+ hoy.getTime());
				logger.info("buascar extranjero monto min"+montoMinCompras +" de fecha >=  "+ antes.getTime() );
				for (ITRtranDetailResponseDataType td : resp.getInqTransResult().getTranDetail()) {
					logger.info("CT "+td.getTranCode() + " monto "+td.getAmtTran().getValue().getValue().doubleValue()+" tran date "+td.getDateTran().getValue().getValue() +" desc "+td.getMerchantInfo().getValue().getDBAName().getValue());

					if("3001".equals(td.getTranCode()) || "1001".equals(td.getTranCode()) ){
						if(montoMinCompras<= td.getAmtTran().getValue().getValue().doubleValue()){
							logger.info("fecha compra "+td.getDateTran().getValue().getValue().toGregorianCalendar().getTime());
							if((td.getDateTran().getValue().getValue().toGregorianCalendar().after(antes) || td.getDateTran().getValue().getValue().toGregorianCalendar().compareTo(antes)==0) ){
								logger.info("La compra entro en lista");
								Compra compra = new Compra();
								compra.setCuenta(cuenta);
								compra.setCodigoTransaccion(Integer.parseInt(td.getTranCode()));
								compra.setFechaCompra(td.getDateTran().getValue().getValue().toGregorianCalendar().getTime());
								compra.setMonto(td.getAmtTran().getValue().getValue().doubleValue());
								compra.setDescripcion(td.getMerchantInfo().getValue().getDBAName().getValue());
								compra.setNumRefTran(td.getRefNbr().getValue());
								compra.setTipoTransaccion("IPS");
								compra.setIdEdoPromocion(compraService.getStatusCompraItau(compra));

								for (Promocion promo : promosCampExt) {

									if(compra.getMonto().doubleValue()>= promo.getMonto().doubleValue()){
										if("si".equalsIgnoreCase(promo.getProgramaCero())){
											if(prog0 && compra.getMonto().doubleValue()>= promo.getMonto().doubleValue()){
												compra.addComboPromo(promo.getPlazoMeses()+" "+promo.getDescripcion(), promo);
											}
										}else{

											compra.addComboPromo(promo.getPlazoMeses()+" "+promo.getDescripcion(), promo);
										}

										
									}
								}
								compras.add(compra);

							}
						}

					}
				}//for compras corte anteriorr

				//fin compras Ext
				
				
				
				Date diahoy = new Date();
				List<Campania> lcampMasivas=campaniaService.getCampaniaProdTs2(diahoy,diahoy,"masiva",prodts2);
				if(lcampMasivas!= null && !lcampMasivas.isEmpty()){
					logger.info("tamMasivas "+lcampMasivas.size());
					Set<Campania>  setMasivas = new HashSet<Campania>(lcampMasivas);
					logger.info("setMasivas "+setMasivas.size());
					lcampMasivas = new ArrayList<Campania>(setMasivas);
				}
			
				List<Double> montos=new ArrayList<Double>();
				for (Campania camp : lcampMasivas) {
					Set<Promocion> setPromos=camp.getPromociones();
					for (Promocion promocion : setPromos) {
						montos.add(promocion.getMonto());
					}
				}
				Collections.sort(montos);
				
			
				
				if(prog0){
					//obtener cmapaña prog 0 compras nacionales
					List<Campania> campProgCero=campaniaService.getCampaniaByTipo("campania.prog0.ts2");
					Set<Promocion> promosProg0=null;
					montoMinCompras = 0;
					
					montos=new ArrayList<Double>();
					for (Campania campania : campProgCero) {
						
						numDiasProg0=campania.getNumMaxDiasRegistro();
						
						promosProg0= campania.getPromociones();


						for (Promocion promo : promosProg0) {
							montos.add(promo.getMonto());
							//montoMinCompras = promo.getMonto();
							//break;
						}
						if (!montos.isEmpty()) {
							Collections.sort(montos);
							for (Promocion promo : promosProg0) {

								montoMinCompras = promo.getMonto();
								break;
							}
						}

					}
					

					//obternr compras prog 0
					antes = new GregorianCalendar();
					antes.set(Calendar.HOUR_OF_DAY, 0);
					antes.set(Calendar.MINUTE, 0);
					antes.set(Calendar.SECOND, 0);
					antes.set(Calendar.MILLISECOND, 0);
					

					antes.add(Calendar.DAY_OF_YEAR, -numDiasProg0);
					logger.info("antes "+antes.getTime());
					inqTrans = new InqTrans();
					reqInqTrans = new InqTransRequestType();
					reqInqTrans.setOnlyCurr(true);
					reqInqTrans.setVersion("1.9.0");
					reqInqTrans.setKey(cuenta);
					reqInqTrans.setKeyType("cardNbr");
					reqInqTrans.setPageItems(300);
					inqTrans.setInqTransRequest(reqInqTrans);
					InqTransResponseType respPro0 =cts2.inqTrans(tp, inqTrans).getInqTransResult();
					logger.info("InqTrans 1 Obtener compras prog cero corte actual");
					if("999".equals( respPro0.getStatus())){
						logger.info( respPro0.getStatusMsg());
						TSYSfaultType fault = respPro0.getFaults();
						List<TSYSfault> lfaulta =fault.getFault();
						for (TSYSfault sfault : lfaulta) {
							logger.info(sfault.getStatus()+" "+ sfault.getFaultDesc());
						}
					}
					//compras = new ArrayList<Compra>();
					logger.info("Compras nal corte actual prog 0");
					for (ITRtranDetailResponseDataType td : respPro0.getTranDetail()) {
						logger.info("CT "+td.getTranCode() + " monto "+td.getAmtTran().getValue().getValue().doubleValue()+" tran date "+td.getDateTran().getValue().getValue() +" desc "+td.getMerchantInfo().getValue().getDBAName().getValue());
						if("7146".equals(td.getTranCode())){
							if(montoMinCompras<= td.getAmtTran().getValue().getValue().doubleValue()){
								if((td.getDateTran().getValue().getValue().toGregorianCalendar().after(antes) || td.getDateTran().getValue().getValue().toGregorianCalendar().compareTo(antes)==0) ){
									logger.info("se agrego compra");
									Compra compra = new Compra();
									compra.setCuenta(cuenta);
									compra.setCodigoTransaccion(Integer.parseInt(td.getTranCode()));
									compra.setFechaCompra(td.getDateTran().getValue().getValue().toGregorianCalendar().getTime());
									compra.setMonto(td.getAmtTran().getValue().getValue().doubleValue());
									compra.setDescripcion(td.getMerchantInfo().getValue().getDBAName().getValue());
									compra.setNumRefTran(td.getRefNbr().getValue());
									compra.setTipoTransaccion("ITA");
									compra.setIdEdoPromocion(compraService.getStatusCompraItau(compra));
									for (Promocion promo : promosProg0) {

										if(compra.getMonto().doubleValue()>= promo.getMonto().doubleValue()){

											compra.addComboPromo(promo.getPlazoMeses()+" "+promo.getDescripcion(), promo);

											//ver masivas
											if(lcampMasivas!= null && !lcampMasivas.isEmpty()){
												//si hay cmap masivas actuales com promo pra prog 0 hay que agragarlas a las compras
												for (Campania camp : lcampMasivas) {
													Calendar calFechaIn = Calendar.getInstance();
													calFechaIn.setTime(camp.getFechaInicial());
													Calendar calFechaFin = Calendar.getInstance();
													calFechaFin.setTime(camp.getFechaFinal());
													Calendar calFechaCompra = td.getDateTran().getValue().getValue().toGregorianCalendar();
													logger.info("fecha compra "+calFechaCompra.getTime() +" fecha in "+calFechaIn.getTime() +" fecha fin "+ calFechaFin.getTime() );
													if(calFechaCompra.compareTo(calFechaIn)==0 || calFechaCompra.compareTo(calFechaFin)==0||(calFechaCompra.after(calFechaIn) && calFechaCompra.before(calFechaFin))){
														logger.info("en tra en promo masiva");
														for(Promocion promoMas:camp.getPromociones()){
															 logger.info("promoMas progCero "+promoMas.getProgramaCero());
															if("si".equalsIgnoreCase(promoMas.getProgramaCero())){
																logger.info("se agrega promo al combo camp masiva "+promoMas.getDescripcion());
																if(compra.getMonto().doubleValue()>= promoMas.getMonto().doubleValue()){
																	compra.addComboPromo(promoMas.getPlazoMeses()+" "+promoMas.getDescripcion(), promoMas);
																}
															}
														}

													}
												}
											}
											compras.add(compra);
										}
									}


								}
							}
						}
					}//for compras corte actual
					
					
					 reqInqTrans2 = new InqTransRequestType();
					 igrala = new InqTrans();
	                 reqInqTrans2.setVersion("1.9.0");
	                 reqInqTrans2.setKey(cuenta);
	                 reqInqTrans2.setKeyType("cardNbr");
	                 reqInqTrans2.setPageItems(300);
						cycleRange = new  com.tsys.xmlmessaging.ch.InqTransRequestType.CycleRange();
						calendarIn = DatatypeFactory.newInstance().newXMLGregorianCalendar(antes);
						calendarFin = DatatypeFactory.newInstance().newXMLGregorianCalendar(hoy);
						cycleRange.setFrom(calendarIn);
						cycleRange.setTo(calendarFin);          
					reqInqTrans2.setCycleRange(cycleRange);
	                 igrala.setInqTransRequest(reqInqTrans2);
	                 
	                 resp= cts2.inqTrans(tp, igrala);
	                 logger.info("Compras prog 0 corte actual");
	                 for (ITRtranDetailResponseDataType td : resp.getInqTransResult().getTranDetail()) {
	                	 logger.info("CT "+td.getTranCode() + " monto "+td.getAmtTran().getValue().getValue().doubleValue()+" tran date "+td.getDateTran().getValue().getValue() +" desc "+td.getMerchantInfo().getValue().getDBAName().getValue());

	                	 if("7146".equals(td.getTranCode())){
	                		 if(montoMinCompras<= td.getAmtTran().getValue().getValue().doubleValue()){
	                			 logger.info("fecha compra "+td.getDateTran().getValue().getValue().toGregorianCalendar().getTime());
	                			 if((td.getDateTran().getValue().getValue().toGregorianCalendar().after(antes) || td.getDateTran().getValue().getValue().toGregorianCalendar().compareTo(antes)==0) ){

	                				 Compra compra = new Compra();
	                				 compra.setCuenta(cuenta);
	                				 compra.setCodigoTransaccion(Integer.parseInt(td.getTranCode()));
	                				 compra.setFechaCompra(td.getDateTran().getValue().getValue().toGregorianCalendar().getTime());
	                				 compra.setMonto(td.getAmtTran().getValue().getValue().doubleValue());
	                				 compra.setDescripcion(td.getMerchantInfo().getValue().getDBAName().getValue());
	                				 compra.setNumRefTran(td.getRefNbr().getValue());
	                				 compra.setTipoTransaccion("IPS");
	                				 compra.setIdEdoPromocion(compraService.getStatusCompraItau(compra));

	                				 for (Promocion promo : promosProg0) {

	                					 if(compra.getMonto().doubleValue()>= promo.getMonto().doubleValue()){


	                						 compra.addComboPromo(promo.getPlazoMeses()+" "+promo.getDescripcion(), promo);
	                						 //ver masivas
	                						 if(lcampMasivas!= null && !lcampMasivas.isEmpty()){
	                							 //si hay cmap masivas actuales com promo pra prog 0 hay que agragarlas a las compras
	                							 for (Campania camp : lcampMasivas) {
	                								 Calendar calFechaIn = Calendar.getInstance();
	                								 calFechaIn.setTime(camp.getFechaInicial());
	                								 Calendar calFechaFin = Calendar.getInstance();
	                								 calFechaFin.setTime(camp.getFechaFinal());
	                								 Calendar calFechaCompra = td.getDateTran().getValue().getValue().toGregorianCalendar();
	                								 logger.info("fecha compra "+calFechaCompra.getTime() +" fecha in "+calFechaIn.getTime() +" fecha fin "+ calFechaFin.getTime() );
	                									if(calFechaCompra.compareTo(calFechaIn)==0 || calFechaCompra.compareTo(calFechaFin)==0||(calFechaCompra.after(calFechaIn) && calFechaCompra.before(calFechaFin))){
	                									 logger.info("en tra en promo masiva");
	                									 for(Promocion promoMas:camp.getPromociones()){
	                										 logger.info("promoMas progCero "+promoMas.getProgramaCero());
	                										 if("si".equalsIgnoreCase(promoMas.getProgramaCero())){
	                											 logger.info("se agrega promo al combo camp masiva "+promoMas.getDescripcion());
	                											 if(compra.getMonto().doubleValue()>= promoMas.getMonto().doubleValue()){
	                												 compra.addComboPromo(promoMas.getPlazoMeses()+" "+promoMas.getDescripcion(), promoMas);
	                											 }
	                										 }
	                									 }

	                								 }
	                							 }
	                						 }
	                						 compras.add(compra);
	                					 }
	                				 }

	                			 }
	                		 }

	                	 }
	                 }//for compras corte anteriorr
					
					

				}//if prog cero
				
				else{
					


					//obtener compras ita nacionales y extranjeras
					for (Campania camp : lcampMasivas) {
						logger.info("camp activa "+camp.getNombre());
						fechaIn= camp.getFechaInicial();
						fechaFin= camp.getFechaFinal();
						Calendar calIn= Calendar.getInstance();
						calIn.setTime(fechaIn);
						Calendar calFin = Calendar.getInstance();
						calFin.setTime(fechaFin);

						InqTransRequestType reqInqTrans4 = new InqTransRequestType();
						reqInqTrans4.setVersion("1.9.0");
						reqInqTrans4.setKey(cuenta);
						reqInqTrans4.setKeyType("cardNbr");
						reqInqTrans4.setPageItems(300);
						reqInqTrans4.setOnlyCurr(true);
						InqTrans inqTrans4 = new InqTrans();
						inqTrans4.setInqTransRequest(reqInqTrans4);
						InqTransResponse respInqTrans4= cts2.inqTrans(tp, inqTrans4);



						if("999".equals( respInqTrans4.getInqTransResult().getStatus())){
							logger.info( respInqTrans4.getInqTransResult().getStatusMsg());
							TSYSfaultType fault = respInqTrans4.getInqTransResult().getFaults();
							List<TSYSfault> lfaulta =fault.getFault();
							for (TSYSfault sfault : lfaulta) {
								logger.info(sfault.getStatus()+" "+ sfault.getFaultDesc());
							}
						}

						montos.clear();
						Set<Promocion> promos = camp.getPromociones();
						for (Promocion promo : promos) {
							montos.add(promo.getMonto());
							//montoMinCompras = promo.getMonto();
							//break;
						}
						if (!montos.isEmpty()) {
							Collections.sort(montos);
							for (Promocion promo : promos) {

								montoMinCompras = promo.getMonto();
								break;
							}
						}
						
						
						for (ITRtranDetailResponseDataType td : respInqTrans4.getInqTransResult().getTranDetail()) {
							logger.info("CT "+td.getTranCode() + " monto "+td.getAmtTran().getValue().getValue().doubleValue()+" tran date "+td.getDateTran().getValue().getValue() +" desc "+td.getMerchantInfo().getValue().getDBAName().getValue());


							if("7146".equals(td.getTranCode())){
								Compra compra = new Compra();
								compra.setCuenta(cuenta);
								compra.setCodigoTransaccion(Integer.parseInt(td.getTranCode()));
								compra.setFechaCompra(td.getDateTran().getValue().getValue().toGregorianCalendar().getTime());
								compra.setMonto(td.getAmtTran().getValue().getValue().doubleValue());
								compra.setDescripcion(td.getMerchantInfo().getValue().getDBAName().getValue());
								compra.setNumRefTran(td.getRefNbr().getValue());
								compra.setTipoTransaccion("ITA");
								compra.setIdEdoPromocion(compraService.getStatusCompraItau(compra));
								for (Campania cam : lcampMasivas) {
									//obtener rango fecha
									fechaIn= cam.getFechaInicial();
									fechaFin= cam.getFechaFinal();
									Calendar calIn2= Calendar.getInstance();
									calIn2.setTime(fechaIn);
									Calendar calFin2 = Calendar.getInstance();
									calFin2.setTime(fechaFin);
									//ver si esta en rango
									if(compra.getMonto().doubleValue()>= montoMinCompras && (td.getDateTran().getValue().getValue().toGregorianCalendar().after(calIn2) || td.getDateTran().getValue().getValue().toGregorianCalendar().compareTo(calIn2)==0) 
											&& (td.getDateTran().getValue().getValue().toGregorianCalendar().before(calFin2) || td.getDateTran().getValue().getValue().toGregorianCalendar().compareTo(calFin2)==0)){
										//obtener promos
										for (Promocion promo : cam.getPromociones()) {
											if("no".equalsIgnoreCase(promo.getProgramaCero()) && compra.getMonto().doubleValue()>= promo.getMonto().doubleValue()){
												compra.addComboPromo(promo.getPlazoMeses()+" "+promo.getDescripcion(), promo);
											}
										}
										compras.add(compra);
									}

								}


							}


						}


						InqTransRequestType reqInqTrans3 = new InqTransRequestType();
						InqTrans igrala2 = new InqTrans();

						reqInqTrans3.setVersion("1.9.0");
						reqInqTrans3.setKey(cuenta);
						reqInqTrans3.setKeyType("cardNbr");

						com.tsys.xmlmessaging.ch.InqTransRequestType.CycleRange cycleRange2 = new  com.tsys.xmlmessaging.ch.InqTransRequestType.CycleRange();
						XMLGregorianCalendar calendarIn2 = DatatypeFactory.newInstance().newXMLGregorianCalendar();
						calendarIn2.setYear(calIn.get(Calendar.YEAR));
						calendarIn2.setMonth(calIn.get(Calendar.MONTH)+1);

						XMLGregorianCalendar calendarFin2 = DatatypeFactory.newInstance().newXMLGregorianCalendar();
						calendarFin2.setYear(calFin.get(Calendar.YEAR));
						calendarFin2.setMonth(calFin.get(Calendar.MONTH)+1);

						cycleRange2.setFrom(calendarIn2);
						cycleRange2.setTo(calendarFin2);          
						reqInqTrans3.setCycleRange(cycleRange2);
						reqInqTrans3.setOnlyForeign(false);
						reqInqTrans3.setPageItems(300);
						igrala2.setInqTransRequest(reqInqTrans3);

						InqTransResponse respInqTrans= cts2.inqTrans(tp, igrala2);
						logger.info("InqTrans 3");
						if("999".equals( respInqTrans.getInqTransResult().getStatus())){
							logger.info( respInqTrans.getInqTransResult().getStatusMsg());
							TSYSfaultType fault = respInqTrans.getInqTransResult().getFaults();
							List<TSYSfault> lfaulta =fault.getFault();
							for (TSYSfault sfault : lfaulta) {
								logger.info(sfault.getStatus()+" "+ sfault.getFaultDesc());
							}
						}

						//obtener nacionales ips
						logger.info("comrpas nacionales corte anterior");
						for (ITRtranDetailResponseDataType td : respInqTrans.getInqTransResult().getTranDetail()) {
							logger.info("CT "+td.getTranCode() + " monto "+td.getAmtTran().getValue().getValue().doubleValue()+" tran date "+td.getDateTran().getValue().getValue() +" desc "+td.getMerchantInfo().getValue().getDBAName().getValue());


							if("7146".equals(td.getTranCode())){
								Compra compra = new Compra();
								compra.setCuenta(cuenta);
								compra.setCodigoTransaccion(Integer.parseInt(td.getTranCode()));
								compra.setFechaCompra(td.getDateTran().getValue().getValue().toGregorianCalendar().getTime());
								compra.setMonto(td.getAmtTran().getValue().getValue().doubleValue());
								compra.setDescripcion(td.getMerchantInfo().getValue().getDBAName().getValue());
								compra.setNumRefTran(td.getRefNbr().getValue());
								compra.setTipoTransaccion("IPS");
								compra.setIdEdoPromocion(compraService.getStatusCompraItau(compra));
								for (Campania cam : lcampMasivas) {
									//obtener rango fecha
									fechaIn= cam.getFechaInicial();
									fechaFin= cam.getFechaFinal();
									Calendar calIn2= Calendar.getInstance();
									calIn2.setTime(fechaIn);
									Calendar calFin2 = Calendar.getInstance();
									calFin2.setTime(fechaFin);
									//ver si esta en rango
									if(compra.getMonto().doubleValue()>= montoMinCompras && (td.getDateTran().getValue().getValue().toGregorianCalendar().after(calIn2) || td.getDateTran().getValue().getValue().toGregorianCalendar().compareTo(calIn2)==0) 
											&& (td.getDateTran().getValue().getValue().toGregorianCalendar().before(calFin2) || td.getDateTran().getValue().getValue().toGregorianCalendar().compareTo(calFin2)==0)){
										//obtener promos
										for (Promocion promo : cam.getPromociones()) {
											if("no".equalsIgnoreCase(promo.getProgramaCero()) && compra.getMonto().doubleValue()>= promo.getMonto().doubleValue()){
												compra.addComboPromo(promo.getPlazoMeses()+" "+promo.getDescripcion(), promo);
											}
										}
										compras.add(compra);
									}

								}


							}


						}



					}
				}
				//se quitan compras repetidas con el set
					Set<Compra> setCompras = new HashSet<Compra>();
					setCompras.addAll(compras);
					compras= new ArrayList<Compra>();
					compras.addAll(setCompras);
					logger.info("compras "+compras.size());
					for (Compra compra : compras) {
						logger.info(compra.getCodigoTransaccion()+""+compra.getFechaCompra()+ " "+compra.getMonto()+ " "+compra.getMonto());
					}
				
				return "ClienteInfoMovs2";
			}else{
			Parametro param= parametroService.getParamById(MSIConstants.TSYSWS_ENDPOINT);
			logger.info("parma obtenido "+param.getDescripcion());
			ClientLSWS cliLSWS = new ClientLSWS(param.getValor());
			ResultadoIGBINAEnca infoCliente= null;

			
				try {
					infoCliente= cliLSWS.getResultadoIGBINAEnca(cuenta, MSIConstants.TSYSWS_SECURITY_USERNAME,MSIConstants.TSYSWS_ACCESS_KEY);
				} catch (Exception e) {
					sendErrorMessageToUser("Error al obtener informacion de la cuenta :"+ e.getMessage());
					e.printStackTrace();
					return null;
				}
				
			
			if(infoCliente == null){
				sendErrorMessageToUser("No se encontraron datos con la cuenta dada");
				return null;
			}
			infoDemo= infoCliente.getInfoDemograficaINA();
			infoCuenta= infoCliente.getInfoGeneralTHIGB();
			if(!infoCuenta.getTipoBloqueo().trim().isEmpty() 
					&& !("MX".equalsIgnoreCase(infoCuenta.getTipoBloqueo().trim()) || "TV".equalsIgnoreCase(infoCuenta.getTipoBloqueo().trim()) || "V5".equalsIgnoreCase(infoCuenta.getTipoBloqueo().trim()) )){
				  logger.info("La cuenta tiene Credit raiting "+infoCuenta.getTipoBloqueo());
				sendErrorMessageToUser("La cuenta tiene Credit raiting "+infoCuenta.getTipoBloqueo());
				return null;
			}
			
			String tipoCuenta =infoCliente.getInfoGeneralTHIGB().getTipoCuenta();		
			if("SCA".equalsIgnoreCase(tipoCuenta.trim())){
				sendErrorMessageToUser("Las cuentas adicionales no estan permitidas");
				  logger.info("La cuenta es SCA");
				return null;
			}
			
			String cuentaCBA= null;
			
			if(!"ICA".equalsIgnoreCase(tipoCuenta.trim())){
				
				
				if("PCA".equalsIgnoreCase(tipoCuenta.trim())){
					//usar los datos de la cba
					cuentaCBA=infoCliente.getInfoGeneralTHIGB().getCuentaCBA();
					infoCliente= cliLSWS.getResultadoIGBINAEnca(cuentaCBA, MSIConstants.TSYSWS_SECURITY_USERNAME,MSIConstants.TSYSWS_ACCESS_KEY);
					
					this.infoDemo = infoCliente.getInfoDemograficaINA();
					this.infoCuenta= infoCliente.getInfoGeneralTHIGB();
					
				}else if("CBA".equalsIgnoreCase(tipoCuenta.trim())){
					logger.info("usaron cuenta CBA!!!!");
					cuentaCBA =cuenta;
					//obtiene la pca
					cuenta = infoCliente.getInfoGeneralTHIGB().getCuentaCBA();
					
						
				}
				
			}else{
				cuentaCBA=cuenta;
			}
			
			
			cliLSWS.setEndPoint(parametroService.getParamById(MSIConstants.TSYSWS_SI02_ENDPOINT).getValor());
			saldoRev=0;
			saldoRevAnterior=0;
			infoEnca = infoCliente.getInfoEncabezado();
				SI02FinalResponseDTO respSI02 = cliLSWS.obtenerSaldoRevolvente(cuentaCBA);
				if(respSI02.getResponseDTO1().getCURR_PUR_BAL_OS()!= null && !respSI02.getResponseDTO1().getCURR_PUR_BAL_OS().isEmpty())
					saldoRev = new Double(respSI02.getResponseDTO1().getCURR_PUR_BAL_OS());
				
				if(respSI02.getResponseDTO1().getONE_CYC_OLDPUR_BAL_OS()!= null  && !respSI02.getResponseDTO1().getONE_CYC_OLDPUR_BAL_OS().isEmpty())
					saldoRevAnterior = new Double(respSI02.getResponseDTO1().getONE_CYC_OLDPUR_BAL_OS());
				
				String diasAgregados = respSI02.getResponseDTO1().getAGGR_NUM_DAYS_BILL();
				if("0".equals(diasAgregados)){
					saldoNoIntereses=Double.parseDouble(infoCliente.getInfoEncabezado().getSaldoNoIntereses());
				}else{
				
					if(respSI02.getResponseDTO1().getCUR_PUR_FIN_CHG_OS()!= null && !respSI02.getResponseDTO1().getCUR_PUR_FIN_CHG_OS().isEmpty()){
						saldoNoIntereses+= new Double(respSI02.getResponseDTO1().getCUR_PUR_FIN_CHG_OS());
					}
					if(respSI02.getResponseDTO1().getONE_CYC_OLDPUR_BAL_OS()!=null && !respSI02.getResponseDTO1().getONE_CYC_OLDPUR_BAL_OS().isEmpty()){
						saldoNoIntereses+= new Double(respSI02.getResponseDTO1().getONE_CYC_OLDPUR_BAL_OS());
					}
					if(respSI02.getResponseDTO1().getTWO_CYC_OLDPUR_BAL_OS()!= null && !respSI02.getResponseDTO1().getTWO_CYC_OLDPUR_BAL_OS().isEmpty()){
						saldoNoIntereses+= new Double(respSI02.getResponseDTO1().getTWO_CYC_OLDPUR_BAL_OS());
					}
					if(respSI02.getResponseDTO1().getONE_CYC_OLD_CA_BAL_OS()!=null && !respSI02.getResponseDTO1().getONE_CYC_OLD_CA_BAL_OS().isEmpty()){
						saldoNoIntereses+= new Double(respSI02.getResponseDTO1().getONE_CYC_OLD_CA_BAL_OS());
					}
					if(respSI02.getResponseDTO1().getCURR_CA_FIN_CHG_OS()!= null && !respSI02.getResponseDTO1().getCURR_CA_FIN_CHG_OS().isEmpty()){
						saldoNoIntereses+= new Double(respSI02.getResponseDTO1().getCURR_CA_FIN_CHG_OS());
					}
					
					if(respSI02.getResponseDTO1().getTWO_CYC_OLD_CA_BAL_OS()!=null && !respSI02.getResponseDTO1().getTWO_CYC_OLD_CA_BAL_OS().isEmpty()){
						saldoNoIntereses+= new Double(respSI02.getResponseDTO1().getTWO_CYC_OLD_CA_BAL_OS());
					}
				}

				
			ArrayUDataDTO[] arrUData=infoCuenta.getUData();
			//5.- validar si el cliente es programa cero
			String ucode3= arrUData[2].getToString();
			logger.info("ucode 3 (prog 0)? "+ucode3);
			
			if(ucode3 != null 
					&& ucode3.length()>0 ){
					
			if( 'G'==ucode3.charAt(3)){
				
				prog0=true;
			}else{
				
				prog0=false;
			}
			
			
			}
			logger.info("prog0? "+prog0);
		
			
			
			logger.info("saldoNoIntereses "+saldoNoIntereses);
			//calcular corte anterior
			Calendar diaCorte = new GregorianCalendar();
			diaCorte.set(Calendar.DAY_OF_MONTH, Integer.parseInt(infoCuenta.getDiaCorte()));
			if("0".equals(infoEnca.getFechaLimPago())){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				if(diaCorte.before(new GregorianCalendar())){
					Calendar siguienteCorte = new GregorianCalendar();
					siguienteCorte.add(Calendar.MONTH, 1);
					siguienteCorte.set(Calendar.DAY_OF_MONTH, Integer.parseInt(infoCuenta.getDiaCorte()));
					infoEnca.setFechaLimPago(sdf.format(siguienteCorte.getTime()));
				}else{
					
					infoEnca.setFechaLimPago(sdf.format(diaCorte.getTime()));
				}
			}
			
			
			DetachedCriteria criteria = DetachedCriteria.forClass(Compra.class);
			
			criteria.add(Restrictions.eq("cuenta", cuenta));
			
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			Date fromDate = calendar.getTime();
			criteria.add(Restrictions.ge("fechaAplicacionPromocion",fromDate));
			compras =compraService.findByCriteria(criteria);
			Double montoComprasHoyITA=0.0;
			Double montoComprasHoyIPS=0.0;
			for (Compra compra : compras) {
				if("ITA".equals(compra.getTipoTransaccion())){
					montoComprasHoyITA+=compra.getMonto();
				}else if("IPS".equals(compra.getTipoTransaccion())){
					montoComprasHoyIPS+=compra.getMonto();
				}
			}
			logger.info("monto compras hoy ita "+montoComprasHoyITA);
			logger.info("monto compras hoy ips "+montoComprasHoyIPS);
			if(montoComprasHoyITA!= null){
				saldoRev-=montoComprasHoyITA;
			}
			
			
			if(montoComprasHoyIPS!= null){
				saldoNoIntereses -=montoComprasHoyIPS;
				saldoRevAnterior-=montoComprasHoyIPS;
			}
			
			if(infoEnca.getPagoMinimo()!=null && !infoEnca.getPagoMinimo().isEmpty()){
				Double pagoMin = Double.parseDouble(infoEnca.getPagoMinimo());
				if(saldoNoIntereses < pagoMin.doubleValue()){
					saldoNoIntereses = pagoMin;
				}
			}
			
			if(saldoRev <0){
				saldoRev=0;
			}
			if(saldoRevAnterior <0){
				saldoRevAnterior=0;
			}
			
			ClienteGetMovs movclient = new ClienteGetMovs(parametroService.getParamById(MSIConstants.SERVICIOS_MSIMOVS_ENDPOINT).getValor());
			logger.info("prams ucode3 "+ucode3.charAt(0)+"%  bin " +cuenta.substring(0, 6)+"%");
			Producto producto = productoService.getProductoCuenta(cuentaCBA, ucode3);
			
			if(producto!= null){
				tipoProd = producto.getCampania();
			}
			logger.info("producto "+tipoProd);
			List<Compra> comprasExt =comprasExt(cuenta,cuentaCBA,movclient,prog0);
			List<Compra> comprasCamSeg= comprasCampSeg(cuenta, cuentaCBA, movclient);
			List<Compra> comprasComercios = comprasCampComercios(cuenta, cuentaCBA, movclient,false);
			if(comprasComercios!=null){
				logger.info("compras comercio "+comprasComercios.size());
			}else{
				logger.info("no hay compras comercios");
			}
			Date diahoy = new Date();
			logger.info("obtener masivas "+ producto.getId());
			List<Campania> lcampMasivas=campaniaService.getCampaniaByParams(diahoy,diahoy,"masiva",producto);
			if(lcampMasivas!= null && !lcampMasivas.isEmpty()){
				logger.info("tamMasivas "+lcampMasivas.size());
				Set<Campania>  setMasivas = new HashSet<Campania>(lcampMasivas);
				logger.info("setMasivas "+setMasivas.size());
				lcampMasivas = new ArrayList<Campania>(setMasivas);
			}
			logger.info("masivas obtenidas "+lcampMasivas.size());
			List<Compra> comprasNacionales=null;
if(prog0){
			comprasNacionales = comprasNacionalesProg0(cuenta, cuentaCBA, movclient,lcampMasivas);
				
}else{
			comprasNacionales =comprasNalCampMasivas(cuenta, cuentaCBA, movclient, lcampMasivas);
}

Set<Compra> setCompras = new HashSet<Compra>();
setCompras.addAll(comprasCamSeg);
setCompras.addAll(comprasComercios);
setCompras.addAll(comprasExt);
setCompras.addAll(comprasNacionales);

if(setCompras.isEmpty()){
			logger.info("No se encontraton compras");
			sendErrorMessageToUser("No se encontraron compras para MSI");
			//return null;
}
compras= new ArrayList<Compra>();
compras.addAll(setCompras);

cliLSWS.setSi07EndPoint(parametroService.getParamById(MSIConstants.ITH_SERVICE_ENDPOINT).getValor());

movsITH=cliLSWS.getIthInfo(cuentaCBA);
logger.info("ir a clienteInfoMovs");
return "ClienteInfoMovs";
			}//fin else itau


		} catch (NumberFormatException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			
			throw new MSIException(e.getMessage());
		} catch (RemoteException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			
			throw new MSIException(e.getMessage());
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			
			throw new MSIException(e.getMessage());
		}catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			
			throw new MSIException(e.getMessage());
		}
	
}

private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

private List<Compra> comprasExt(String cuenta,String cuentaCBA,ClienteGetMovs movclient,boolean prog0){
	//1.-Obtern Datos CampExt
	List<Campania> campsExt=campaniaService.getCampaniaByTipo("compras.internal");
	List<Compra> compras = new ArrayList<Compra>();
	Double montoMinComprasExt=0.0;
	int numDiasCampExt=35;
	Set<Promocion> promosCamp = null;
	
	for (Campania campania : campsExt) {
		
		numDiasCampExt=campania.getNumMaxDiasRegistro();
		 promosCamp= campania.getPromociones();
		 
		
		for (Promocion promo : promosCamp) {
			montoMinComprasExt = promo.getMonto();
			break;
		}
		
	}
	Calendar hoy = new GregorianCalendar();
	Calendar antes = new GregorianCalendar();
	antes.add(Calendar.DAY_OF_YEAR, -numDiasCampExt);
	//2.-Obtener compras
	String[] movs=null;
	try {
		
		movs = movclient.webMovEdoCtaFech(sdf.format(antes.getTime()),sdf.format(hoy.getTime()), cuentaCBA, 
				new String[]{MSIConstants.TRANS_CODIGO_COMPRAS_INTER_10001,MSIConstants.TRANS_CODIGO_COMPRAS_INTER_30001},montoMinComprasExt);
	} catch (RemoteException e) {
		sendErrorMessageToUser("Error Servicio Tsys "+e.getMessage());
		logger.error("Error Servicio Tsys "+e.getMessage());
		e.printStackTrace();
	} catch (ServiceException e) {
		sendErrorMessageToUser("Error Servicio Tsys "+e.getMessage());
		logger.error("Error Servicio Tsys "+e.getMessage());
		e.printStackTrace();
	}
	if(movs!= null){
		//boolean isItau=productoTs2Service.cuentaITAU(cuenta);
		for (String mov : movs) {
			Compra compra=MSIHelper.fromStringToCompra(mov);
			compra.setCuenta(cuenta);
			compra.setCuentaFacturadora(cuentaCBA);
			for (Promocion promo : promosCamp) {
				if("si".equalsIgnoreCase(promo.getProgramaCero())){
					if(prog0 && compra.getMonto().doubleValue()>= promo.getMonto().doubleValue()){
						compra.addComboPromo(promo.getPlazoMeses()+" "+promo.getDescripcion(), promo);
					}
				}else{
					if(compra.getMonto().doubleValue()>= promo.getMonto().doubleValue()){
						compra.addComboPromo(promo.getPlazoMeses()+" "+promo.getDescripcion(), promo);
					}
				}
			}
			//compra.setPromosCombo(promosMesesExt);
			
			compra.setIdEdoPromocion(compraService.getStatusCompra(compra));
			compras.add(compra);
		}
	}
	return compras;
}

private List<Compra> comprasCampComercios(String cuenta,String cuentaCBA,ClienteGetMovs movclient,boolean isItau){
	Date diahoy = new Date();
	List<Campania> campComercios = campaniaService.getCampaniaByParams(null,null, "comercio", null);
	List<Compra> compras = new ArrayList<Compra>();
	List<Double> montos=null;
	
	Set<Campania> setCamps = new HashSet<Campania>();
	
	setCamps.addAll(campComercios);
	logger.info("camp comercios "+setCamps.size());
	for (Campania campania : setCamps) {
		montos= new ArrayList<Double>();
		GregorianCalendar hoy = new GregorianCalendar();
		GregorianCalendar antes = new GregorianCalendar();
		antes.add(Calendar.DAY_OF_YEAR, -campania.getNumMaxDiasRegistro());
		Set<Promocion> promosCamp= campania.getPromociones();
		logger.info("camp comercios promos "+promosCamp.size());
		for (Promocion promo : promosCamp) {
			if(promo.getMonto()!= null){
				logger.info("camp comercios promo monto "+promo.getMonto());
				montos.add(promo.getMonto());
			}
			
		}
		
		Set<Comercio> comercios=campania.getComercios();
		Collections.sort(montos);
		//int numdias=campania.getNumMaxDiasRegistro();
		//TODO calcula hoy y antes
		
		for (Comercio comercio : comercios) {
			String[] codigos;
			if(campania.getCodigoTransaccion2()!= null){
	 			codigos= new String[]{campania.getCodigoTransaccion1(),campania.getCodigoTransaccion2()};
	 		}else{
	 			codigos= new String[]{campania.getCodigoTransaccion1().toString()};
	 		}
			
			if(isItau){
				try {
					compras.addAll(this.getComprasts2(cuenta, montos.get(0), campania.getCodigoTransaccion1(), sdf.parse(sdf.format(antes.getTime())),sdf.parse(sdf.format(hoy.getTime())), campania, comercio.getNombre()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}else{
			
			
				String[] movs=null;
				try {
					movs = movclient.getMovsCampComercios(sdf.format(antes.getTime()),sdf.format(hoy.getTime()), cuentaCBA, codigos, montos.get(0), comercio.getNombre());
				} catch (RemoteException e) {
					sendErrorMessageToUser("Error Servicio Tsys "+e.getMessage());
					logger.error("Error Servicio Tsys "+e.getMessage());
					e.printStackTrace();
				} catch (ServiceException e) {
					sendErrorMessageToUser("Error Servicio Tsys "+e.getMessage());
					logger.error("Error Servicio Tsys "+e.getMessage());
					e.printStackTrace();
				}
				if(movs!= null){
					for (String strMov : movs) {
						Compra compra=MSIHelper.fromStringToCompra(strMov);
						compra.setCuenta(cuenta);
						compra.setCuentaFacturadora(cuentaCBA);
						promosCamp= campania.getPromociones();
						for (Promocion promo : promosCamp) {
							
							 if(compra.getMonto().doubleValue()>= promo.getMonto().doubleValue()){
								
								compra.addComboPromo(promo.getPlazoMeses()+" "+promo.getDescripcion(),promo);
							}
							
						}
						
						compra.setIdEdoPromocion(compraService.getStatusCompra(compra));
						compras.add(compra);
					
					}
				}
			
			}
		}
		
	}
	return compras;
}

private List<Compra> getComprasts2(String account,double monto,String codigo,Date fechaAntes,Date fechaDespues,Campania camp,String descripcion){
	ClientTS2 cts2= new ClientTS2();
	 TSYSprofileType tp = new TSYSprofileType();
	 tp.setClientID("7401");
	 tp.setUserID("gp5rwf");
	 tp.setVendorID("00000000");
	InqTrans inqTrans = new InqTrans();
	InqTransRequestType reqInqTrans = new InqTransRequestType();

	//reqInqTrans.setOnlyForeign(true);
	reqInqTrans.setOnlyCurr(true);
	reqInqTrans.setVersion("1.9.0");
	reqInqTrans.setKey(account);
	reqInqTrans.setKeyType("cardNbr");

	inqTrans.setInqTransRequest(reqInqTrans);
	InqTransResponseType respExt =cts2.inqTrans(tp, inqTrans).getInqTransResult();
	
	if(!"000".equals( respExt.getStatus())){
		logger.info( respExt.getStatusMsg());
		TSYSfaultType fault = respExt.getFaults();
		List<TSYSfault> lfaulta =fault.getFault();
		for (TSYSfault sfault : lfaulta) {
			logger.info(sfault.getStatus()+" "+ sfault.getFaultDesc());
		}
	}
	List<Compra> comprasComer = new ArrayList<Compra>();
	logger.info("Compras comercios");
	GregorianCalendar antes = new GregorianCalendar();
	antes.setTime(fechaAntes);
	GregorianCalendar despues = new GregorianCalendar();
	despues.setTime(fechaDespues);
	
	for (ITRtranDetailResponseDataType td : respExt.getTranDetail()) {
		logger.info("CT "+td.getTranCode() + " monto "+td.getAmtTran().getValue().getValue().doubleValue()+" tran date "+td.getDateTran().getValue().getValue() +" desc "+td.getMerchantInfo().getValue().getDBAName().getValue());
		
		if(td.getTranCode().equals(codigo)){
			if(monto<= td.getAmtTran().getValue().getValue().doubleValue()){
				if((td.getDateTran().getValue().getValue().toGregorianCalendar().after(antes) || td.getDateTran().getValue().getValue().toGregorianCalendar().compareTo(antes)==0)
						&& (td.getDateTran().getValue().getValue().toGregorianCalendar().before(despues) || td.getDateTran().getValue().getValue().toGregorianCalendar().compareTo(despues)==0)){
					logger.info("agregar compra");
					if (td.getMerchantInfo().getValue()
								.getDBAName().getValue().contains(descripcion)) {
						Compra compra = new Compra();
						compra.setCuenta(account);
						compra.setCodigoTransaccion(Integer.parseInt(td
								.getTranCode()));
						compra.setFechaCompra(td.getDateTran().getValue()
								.getValue().toGregorianCalendar().getTime());
						compra.setMonto(td.getAmtTran().getValue().getValue()
								.doubleValue());
						compra.setDescripcion(td.getMerchantInfo().getValue()
								.getDBAName().getValue());
						compra.setNumRefTran(td.getRefNbr().getValue());
						compra.setTipoTransaccion("ITA");
						compra.setIdEdoPromocion(compraService
								.getStatusCompraItau(compra));
						for (Promocion promo : camp.getPromociones()) {

							if (compra.getMonto().doubleValue() >= promo
									.getMonto().doubleValue()) {
								compra.addComboPromo(promo.getPlazoMeses()
										+ " " + promo.getDescripcion(), promo);
							}

						}
						comprasComer.add(compra);
					}

				}
			}
		}
	}//for compras corte actual

	
	InqTransRequestType reqInqTrans2 = new InqTransRequestType();
	InqTrans igrala = new InqTrans();
    reqInqTrans2.setVersion("1.9.0");
    reqInqTrans2.setKey(cuenta);
    reqInqTrans2.setKeyType("cardNbr");
    reqInqTrans2.setPageItems(300);
    com.tsys.xmlmessaging.ch.InqTransRequestType.CycleRange	cycleRange = new  com.tsys.xmlmessaging.ch.InqTransRequestType.CycleRange();
		XMLGregorianCalendar calendarIn=null;
		try {
			calendarIn = DatatypeFactory.newInstance().newXMLGregorianCalendar(antes);
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		XMLGregorianCalendar calendarFin=null;
		try {
			calendarFin = DatatypeFactory.newInstance().newXMLGregorianCalendar(despues);
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cycleRange.setFrom(calendarIn);
		cycleRange.setTo(calendarFin);          
	reqInqTrans2.setCycleRange(cycleRange);
    igrala.setInqTransRequest(reqInqTrans2);
    
    InqTransResponse resp= cts2.inqTrans(tp, igrala);
    logger.info("Compras prog 0 corte actual");
    for (ITRtranDetailResponseDataType td : resp.getInqTransResult().getTranDetail()) {
   	 logger.info("CT "+td.getTranCode() + " monto "+td.getAmtTran().getValue().getValue().doubleValue()+" tran date "+td.getDateTran().getValue().getValue() +" desc "+td.getMerchantInfo().getValue().getDBAName().getValue());

   	 if(codigo.equals(td.getTranCode())){
   		 if(monto<= td.getAmtTran().getValue().getValue().doubleValue()){
   			 logger.info("fecha compra "+td.getDateTran().getValue().getValue().toGregorianCalendar().getTime());
   			 if((td.getDateTran().getValue().getValue().toGregorianCalendar().after(antes) || td.getDateTran().getValue().getValue().toGregorianCalendar().compareTo(antes)==0) 
   					 && (td.getDateTran().getValue().getValue().toGregorianCalendar().before(despues) || td.getDateTran().getValue().getValue().toGregorianCalendar().compareTo(despues)==0)){

   				if (td.getMerchantInfo().getValue()
						.getDBAName().getValue().contains(descripcion)) {
				Compra compra = new Compra();
				compra.setCuenta(account);
				compra.setCodigoTransaccion(Integer.parseInt(td
						.getTranCode()));
				compra.setFechaCompra(td.getDateTran().getValue()
						.getValue().toGregorianCalendar().getTime());
				compra.setMonto(td.getAmtTran().getValue().getValue()
						.doubleValue());
				compra.setDescripcion(td.getMerchantInfo().getValue()
						.getDBAName().getValue());
				compra.setNumRefTran(td.getRefNbr().getValue());
				compra.setTipoTransaccion("IPS");
				compra.setIdEdoPromocion(compraService
						.getStatusCompraItau(compra));
				for (Promocion promo : camp.getPromociones()) {

					if (compra.getMonto().doubleValue() >= promo
							.getMonto().doubleValue()) {
						compra.addComboPromo(promo.getPlazoMeses()
								+ " " + promo.getDescripcion(), promo);
					}

				}
				comprasComer.add(compra);
			}
   				 
   			 }	 
   				 
   			
   		 }

   	 }
    }//for compras corte anteriorr
	return comprasComer;
}

private List<Compra> comprasNalCampMasivas(String cuenta,String cuentaCBA,ClienteGetMovs movclient,List<Campania> campMasivas){
	List<Compra> compras = new ArrayList<Compra>();
	Date fechaIn= null;
	Date fechaFin= null;
	List<Double> montos=null;
	//pora cada campania obtener sus compras respectivas
	for (Campania camp : campMasivas) {
		logger.info("camp activa "+camp.getNombre());
		montos= new ArrayList<Double>();
		
		fechaIn= camp.getFechaInicial();
		fechaFin= camp.getFechaFinal();
		String[] movs= null;
		boolean isItau=productoTs2Service.cuentaITAU(cuenta);
		//si hay alguna cmapania acutalmente te traes todas las compras
		if(fechaIn!= null && fechaFin != null){
			//Map<String,Promocion> promosMeses= new HashMap<String,Promocion>();
			for (Promocion promo : camp.getPromociones()) {
				if(promo.getMonto()!= null){
					montos.add(promo.getMonto());
				}

			}
			Collections.sort(montos);
			logger.info("Hay una campania activa hay que traer compras nacionales");
			 try {
				movs = movclient.webMovEdoCtaFech(sdf.format(fechaIn),sdf.format(fechaFin), cuentaCBA, 
						new String[]{MSIConstants.TRANS_CODIGO_COMPRAS_NACIONALES},montos.get(0));
			 } catch (RemoteException e) {
					sendErrorMessageToUser("Error Servicio Tsys "+e.getMessage());
					logger.error("Error Servicio Tsys "+e.getMessage());
					e.printStackTrace();
				} catch (ServiceException e) {
					sendErrorMessageToUser("Error Servicio Tsys "+e.getMessage());
					logger.error("Error Servicio Tsys "+e.getMessage());
					e.printStackTrace();
				}
			 if(movs!= null){
				 for (String strMov : movs) {
						Compra compra=MSIHelper.fromStringToCompra(strMov);
						compra.setCuenta(cuenta);
						compra.setCuentaFacturadora(cuentaCBA);
						
						//iterar camps
						for (Campania cam : campMasivas) {
							//obtener rango fecha
							fechaIn= cam.getFechaInicial();
							fechaFin= cam.getFechaFinal();
							Calendar calIn2= Calendar.getInstance();
							calIn2.setTime(fechaIn);
							Calendar calFin2 = Calendar.getInstance();
							calFin2.setTime(fechaFin);
							//ver si esta en rango
							//compra.getFechaCompra().
							Calendar fchCompra = Calendar.getInstance();
							fchCompra.setTime(compra.getFechaCompra());
								if((fchCompra.after(calIn2) || fchCompra.equals(calIn2)) 
										&& (fchCompra.before(calFin2) || fchCompra.equals(calFin2))){
									//obtener promos
									for (Promocion promo : cam.getPromociones()) {
										if("no".equalsIgnoreCase(promo.getProgramaCero()) && compra.getMonto().doubleValue()>= promo.getMonto().doubleValue()){
											compra.addComboPromo(promo.getPlazoMeses()+" "+promo.getDescripcion(), promo);
										}
									}
								}
							
						}
						
						
//						for (Promocion promo : camp.getPromociones()) {
//							
//							if("no".equalsIgnoreCase(promo.getProgramaCero()) ){
//								 if(compra.getMonto().doubleValue()>= promo.getMonto().doubleValue()){
//									compra.addComboPromo(promo.getPlazoMeses()+" "+promo.getDescripcion(), promo);
//								}
//							}
//														
//						}

						compra.setIdEdoPromocion(compraService.getStatusCompra(compra));
						compras.add(compra);
					
					}
			 }
			
		}
		
	}//fin for campanias actulaes
	
	
	return compras;
}

private List<Compra> comprasNacionalesProg0(String cuenta,String cuentaCBA,ClienteGetMovs movclient,List<Campania> campMasivas){
	List<Compra> compras = new ArrayList<Compra>();
	List<Campania> campProg0Nals =campaniaService.getCampaniaByTipo("compras.nal.prog.cero");

	List<Double> montos=null;
	
	int numDiasCamp=35;
	if(!campProg0Nals.isEmpty()){
		Campania progCeroNal=campProg0Nals.get(0);
		montos= new ArrayList<Double>();
		
		
		numDiasCamp=progCeroNal.getNumMaxDiasRegistro();
		
		Calendar hoy = new GregorianCalendar();
		Calendar antes = new GregorianCalendar();
		antes.add(Calendar.DAY_OF_YEAR, -numDiasCamp);
		String[] movs=null;
		for (Promocion promo : progCeroNal.getPromociones()) {
			if(promo.getMonto()!= null){
				montos.add(promo.getMonto());
			}
		}
		Collections.sort(montos);
	
			try {
				movs =movclient.webMovEdoCtaFech(sdf.format(antes.getTime()),sdf.format(hoy.getTime()), cuentaCBA, 
							new String[]{MSIConstants.TRANS_CODIGO_COMPRAS_NACIONALES},montos.get(0));
			} catch (RemoteException e) {
				sendErrorMessageToUser("Error Servicio Tsys "+e.getMessage());
				logger.error("Error Servicio Tsys "+e.getMessage());
				e.printStackTrace();
			} catch (ServiceException e) {
				sendErrorMessageToUser("Error Servicio Tsys "+e.getMessage());
				logger.error("Error Servicio Tsys "+e.getMessage());
				e.printStackTrace();
			}
		if(movs!= null){	
		
			for (String strMov : movs) {
				Compra compra=MSIHelper.fromStringToCompra(strMov);
				compra.setCuenta(cuenta);
				compra.setCuentaFacturadora(cuentaCBA);
				logger.info("compra prog 0 "+compra.getDescripcion());
				
				for (Promocion promo : progCeroNal.getPromociones()) {
				
					 if(compra.getMonto().doubleValue()>= promo.getMonto().doubleValue()){
						
						compra.addComboPromo(promo.getPlazoMeses()+" "+promo.getDescripcion(), promo);
					}
					
				}
				Calendar calFechaCompra = Calendar.getInstance();
				calFechaCompra.setTime(compra.getFechaCompra());
				//si hay cmap masivas actuales com promo pra prog 0 hay que agragarlas a las compras
				for (Campania camp : campMasivas) {
					Calendar calFechaIn = Calendar.getInstance();
					calFechaIn.setTime(camp.getFechaInicial());
					Calendar calFechaFin = Calendar.getInstance();
					calFechaFin.setTime(camp.getFechaFinal());
					if(calFechaCompra.equals(calFechaIn) || calFechaCompra.equals(calFechaFin)||calFechaCompra.after(calFechaIn) && calFechaCompra.before(calFechaFin)){
						for(Promocion promo:camp.getPromociones()){
							
							if("si".equalsIgnoreCase(promo.getProgramaCero())){
								logger.info("se agrega promo al combo camp masiva "+promo.getDescripcion());
								 if(compra.getMonto().doubleValue()>= promo.getMonto().doubleValue()){
									compra.addComboPromo(promo.getPlazoMeses()+" "+promo.getDescripcion(), promo);
								}
							}
						}
						
					}
				}
				
				compra.setIdEdoPromocion(compraService.getStatusCompra(compra));
				compras.add(compra);
			
			}
		}
		
	}
	
	
	return compras;
}

private List<Compra> comprasCampSeg(String cuenta,String cuentaCBA,ClienteGetMovs movclient){
	List<ArchivoDetalle> listArD=archivoDetalleService.getArDetalleByCuenta(cuenta);
	
	List<Compra> compras = new ArrayList<Compra>();
	if(listArD!= null && !listArD.isEmpty()){
		Campania campSeg;
		for (ArchivoDetalle archivoDetalle : listArD) {
			campSeg = archivoDetalle.getCampania();
			if(campSeg != null){
				Calendar fechMax = Calendar.getInstance();
				fechMax.setTime(campSeg.getFechaMaximaRegistro());
				if(Calendar.getInstance().after(fechMax)){
					continue;
				}else{
					List<Double> montos= new ArrayList<Double>();
					boolean isItau=productoTs2Service.cuentaITAU(cuenta);
					for (Promocion promo : campSeg.getPromociones()) {
						if(promo.getMonto()!= null){
							montos.add(promo.getMonto());
						}
					}
					
					Collections.sort(montos);
						logger.info("campSeg id "+campSeg.getIdCampania());
						String[] movs=null;
						try {
							movs = movclient.webMovEdoCtaFech(sdf.format(archivoDetalle.getFechaInicio()),sdf.format(archivoDetalle.getFechaFin()), cuentaCBA, 
									new String[]{campSeg.getCodigoTransaccion1().toString()},montos.get(0));
						} catch (RemoteException e) {
							sendErrorMessageToUser("Error Servicio Tsys "+e.getMessage());
							logger.error("Error Servicio Tsys "+e.getMessage());
							e.printStackTrace();
						} catch (ServiceException e) {
							sendErrorMessageToUser("Error Servicio Tsys "+e.getMessage());
							logger.error("Error Servicio Tsys "+e.getMessage());
							e.printStackTrace();
						}
					if(movs!= null){	
						for (String strMov : movs) {
							Compra compra=MSIHelper.fromStringToCompra(strMov);
							compra.setCuenta(cuenta);
							compra.setCuentaFacturadora(cuentaCBA);
							Set<Promocion> promosCamp= campSeg.getPromociones();
							
							for (Promocion promo : promosCamp) {	
								 if(compra.getMonto().doubleValue()>= promo.getMonto().doubleValue()){
									
									compra.addComboPromo(promo.getPlazoMeses()+" "+promo.getDescripcion(), promo);
								}
							}
							
							compra.setIdEdoPromocion(compraService.getStatusCompra(compra));
							compras.add(compra);
						
						}
					}
					break;
				}
			}
		

			
		}
	}
	return compras;
}

public void habilita(int index) { 
	 logger.info("compras index "+index);
	Compra compra = compras.get(index);
	compra.setSelected((Boolean)chkSelect.getValue());
	logger.info("compra selected ? "+compra.isSelected());
	if ("ITA".equals(compra.getTipoTransaccion())){
		if(compra.isSelected()){
			saldoRev -=compra.getMonto();
			if (saldoRev < 0){
				compra.setMontoPromo(saldoRev+compra.getMonto());
				saldoRev =0;
			}else{
				compra.setMontoPromo(compra.getMonto());
			}
		}else{
			saldoRev +=compra.getMontoPromo();
			compra.setMontoPromo(0.0);
		}
		
		
	}else{
		if(compra.isSelected()){
			saldoRevAnterior -=compra.getMonto();
			if (saldoRevAnterior < 0){
				compra.setMontoPromo(saldoRevAnterior+compra.getMonto());
				saldoRevAnterior =0;
			}else{
				compra.setMontoPromo(compra.getMonto());
			}
		}else{
			saldoRevAnterior +=compra.getMontoPromo();
			compra.setMontoPromo(0.0);
		}
		
	}
	logger.info("montoPromo "+compra.getMontoPromo());
	
	logger.info("saldo corte actual "+ saldoRev+ " saldo corte anterior "+saldoRevAnterior) ;
	}

public void habilita2(int index) { 
	 logger.info("compras index "+index);
	Compra compra = compras.get(index);
	compra.setSelected((Boolean)chkSelect.getValue());
	logger.info("compra selected ? "+compra.isSelected());
	
		if(compra.isSelected()){
			
				compra.setMontoPromo(compra.getMonto());
				if ("ITA".equals(compra.getTipoTransaccion())){
					this.saldoRev+=compra.getMonto();
				}else{
					this.saldoRevAnterior+=compra.getMonto();
				}
			
		}else{
			
			compra.setMontoPromo(0.0);
			if ("ITA".equals(compra.getTipoTransaccion())){
				this.saldoRev-=compra.getMonto();
			}else{
				this.saldoRevAnterior-=compra.getMonto();
			}
		}
		
		
	
	logger.info("montoPromo "+compra.getMontoPromo());
	
	logger.info("saldo corte actual "+ saldoRev+ " saldo corte anterior "+saldoRevAnterior) ;
	}

public void setValorComboPromo(int index){
	System.out.println("setValorcombp");
	Promocion promo = (Promocion) cmbPromos.getValue();
	if(promo !=null){
		Compra compra = compras.get(index);
		compra.setPromocion(promo);
	}
	
}

}

