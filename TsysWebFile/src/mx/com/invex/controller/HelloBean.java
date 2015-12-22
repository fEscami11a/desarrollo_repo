package mx.com.invex.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;

import com.tsys.xmlmessaging.ch.InqCurrBalByTBALResponseType;
import com.tsys.xmlmessaging.ch.NTBTBALsResponseDataType;
import com.tsys.xmlmessaging.ch.TSYSfault;
import com.tsys.xmlmessaging.ch.TSYSfaultType;

import mx.com.invex.ws.client.ClientTs2;

@ManagedBean
@SessionScoped
public class HelloBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HelloBean.class);
	private String name;

	public String getName() {
	   return name;
	}
	public void setName(String name) {
	   this.name = name;
	}
	public String getSayWelcome(){
		
		  if("".equals(name) || name ==null){
				return "";
			   }
		  logger.info("say ewsewlk cuenta "+name);
		ClientTs2 cts2 = new ClientTs2();
		InqCurrBalByTBALResponseType resp=cts2.inqCurrBalByTBAL(name).getInqCurrBalByTBALResult();
		if (!"000".equals(resp.getStatus())) {
			
			TSYSfaultType fault = resp.getFaults();
			List<TSYSfault> lfaulta = fault.getFault();
			for (TSYSfault sfault : lfaulta) {
				name=sfault.getStatus() + " "
						+ sfault.getFaultDesc();
			}
			return name;
		}
		
		List<NTBTBALsResponseDataType.TBAL> tbals=resp.getTBALs().getValue().getTBAL();
		double ppngi=0;
		try {
			
			for (NTBTBALsResponseDataType.TBAL tbal : tbals) {
				String id =tbal.getId();
				if("0001".equals(id) || "0002".equals(id)){
					ppngi+=tbal.getAmtPrevCycleUnpaid().getValue().doubleValue();
					ppngi+=tbal.getBalPrev().getFinChrg().getValue().doubleValue();
					//ppngi+=tbal.getAmtOutstanding().getTtl().getValue().doubleValue();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ""+ppngi;
	
	}
}