package mx.com.invex.ws;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.tsys.xmlmessaging.ch.InqCustomData;
import com.tsys.xmlmessaging.ch.InqCustomDataRequestType;
import com.tsys.xmlmessaging.ch.InqCustomDataRequestType.CustomDataCodes;
import com.tsys.xmlmessaging.ch.InqCustomDataResponseType;
import com.tsys.xmlmessaging.ch.TSYSfault;
import com.tsys.xmlmessaging.ch.TSYSfaultType;
import com.tsys.xmlmessaging.ch.TSYSprofileType;

import mx.com.interware.spira.dto.CuentaAD;
import mx.com.interware.spira.dto.CuentaTs2Info;
import mx.com.interware.spira.web.dao.CommonDAO;
import mx.com.interware.spira.web.dao.Ts2dwhDAO;
import mx.com.invex.seguridad.utils.CryptoAES;
import mx.com.invex.ws.client.ClientTs2;

@WebService(endpointInterface = "mx.com.invex.ws.MobilWeb")
public class MobilImplWeb implements MobilWeb{

	private static final Logger logger = Logger.getLogger(MobilImplWeb.class);
	@Override
	public String getNumLuciByWebUser(String webUsuario){
		try {
			
			Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
					+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
			if(pattern.matcher(webUsuario).matches()){
				webUsuario = webUsuario.toUpperCase();
			}
			String usrCifrado = CryptoAES.encrypt(webUsuario);
			String cuentaEnc = CommonDAO.getCuentaByWebUser(usrCifrado);
			if(cuentaEnc==null){
				return null;
			}
			String cuenta = CryptoAES.decrypt2(cuentaEnc);
			ClientTs2 cts2= new ClientTs2();
			
			 TSYSprofileType tp = new TSYSprofileType();
			 tp.setClientID("7401");
			 tp.setUserID("Invex");
			 tp.setVendorID("00000000");
			InqCustomData inqCustomData = new InqCustomData();
			InqCustomDataRequestType inqCustomDataReq = new InqCustomDataRequestType();
			inqCustomDataReq.setKey(cuenta);
			inqCustomDataReq.setKeyType("cardNbr");
			inqCustomDataReq.setVersion("1.0.0");
			
			CustomDataCodes customDataCodes = new CustomDataCodes();
			List<String> custDatas =customDataCodes.getCustomDataCode();
			//fecha vencimiento
			custDatas.add("94");
			
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
			}
			if(customDataResp.getCustomData()!= null){
				if(customDataResp.getCustomData().getValue().getCode94()!= null){
					String numLuci =customDataResp.getCustomData().getValue().getCode94().getValue();
					return numLuci;
				}
			}
			
//			InqCustomData inqCustomData = new InqCustomData();
//			TSYSprofileType tpt = new TSYSprofileType();
//			cts2.inqCustomData(tpt, inqCustomData);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public RespGetProdByWebUser getProductosByWebUsr(String webUsuario){
		logger.info("getProductosByWebUsr "+webUsuario);
		Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		if(pattern.matcher(webUsuario).matches()){
			webUsuario = webUsuario.toUpperCase();
		}
		RespGetProdByWebUser resp = new RespGetProdByWebUser();
		
		try {
			String cifrado = CryptoAES.encrypt(webUsuario);
			logger.info("cifrado "+cifrado);
			
			String cuentaEnc= CommonDAO.getCuentaByWebUser(cifrado);
			if(cuentaEnc== null){
				resp.setStatus(2);
				resp.setMsgError("No se encontraron resultados");
				return resp;
			}
			
			logger.info("cuenta enc "+cuentaEnc);
			String cuenta=CryptoAES.decrypt2(cuentaEnc);
			logger.info("cuenta "+cuenta);
			String[] arrCuentas;
			//si es itau
			
			if(CommonDAO.isCuentaTs2(cuenta)){
				int i= 0;
				List<CuentaTs2Info> lcuentas=Ts2dwhDAO.getCuentasRelacionadas(cuenta);
				List<CuentaTs2Info> cuentasSinAds = new ArrayList<CuentaTs2Info>();
				for (CuentaTs2Info cuentaTs2Info : lcuentas) {
					if(!"Authorized User".equals(cuentaTs2Info.getTipoCuenta())){
						cuentasSinAds.add(cuentaTs2Info);
					}
				}
				
				arrCuentas= new String[cuentasSinAds.size()];
				for (CuentaTs2Info cts2 : cuentasSinAds) {
					
					String[] arr =cts2.getProducto().split("\\|")[0].split(",");
					String tpc= arr[0];
					String cpc= arr[1];
					
					String producto = CommonDAO.getProductoTs2(cts2.getCuenta(), tpc, cpc);
					if(producto == null){
						producto="desconocido";
					}
					
					
					arrCuentas[i]=cts2.getCuenta()+"|"+producto;
					//ProductoTs2 prodts2 = productoTs2Service.getProductoItau(cuenta, tpc, cpc);
					i++;
					
				}
				
				resp.setArrCuentas(arrCuentas);
			
				
				
			}else{
			
				CallcenterWServiceInt cuentasADService = new CallcenterWService();
				CuentaAD[] cuentas= cuentasADService.getCuentasAD(cuenta);
				if(cuentas.length==0){
					resp.setStatus(2);
					resp.setMsgError("No se encontraron resultados");
					return resp;
				}
				String[] prods = new String[cuentas.length];
				
				int i=0;
				for (CuentaAD cuentaAD : cuentas) {
					logger.info(cuentaAD.getNumCuenta());
					//filtrar cuentas facturadoras y empresariales para mobil
					if(cuentaAD.getTipoCuenta()==null || "B".equals(cuentaAD.getTipoCuenta())){
						continue;
					}
					String prod =CommonDAO.getProductoCuenta(cuentaAD.getNumCuenta(), cuentaAD.getUcode3());
					prods[i] = cuentaAD.getNumCuenta()+"|"+prod;
					i++;
				}
				ArrayList<String> lcuentas = new ArrayList<String>();
				
				for (String string : prods) {
					if(string!=null){
						lcuentas.add(string);
					}
				}
				prods=new String[lcuentas.size()];
				int a=0;
				for (String string : lcuentas) {
					prods[a]=string;
					a++;
				}
				resp.setArrCuentas(prods);
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp.setStatus(1);
			resp.setMsgError("Error "+e.getMessage());
			return resp;
		}
		
		resp.setStatus(0);
		return resp;
	}

	@Override
	public RespGetProdByWebUser getMovsAntesCorte(String cuenta) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
