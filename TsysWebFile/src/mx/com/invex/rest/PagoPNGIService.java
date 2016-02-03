package mx.com.invex.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.tsys.xmlmessaging.ch.InqCurrBalByTBALResponseType;
import com.tsys.xmlmessaging.ch.NTBTBALsResponseDataType;
import com.tsys.xmlmessaging.ch.TSYSfault;
import com.tsys.xmlmessaging.ch.TSYSfaultType;

import mx.com.invex.controller.SaldosCuenta;
import mx.com.invex.ws.client.ClientTs2;

@Path("/ppngi")
public class PagoPNGIService {
	
	@GET
	@Path("/{param}")
	public Response calcularPago(@PathParam("param") String cuenta) {

		//String result = "Restful example : " + msg;
		
		ClientTs2 cts2 = new ClientTs2();
		InqCurrBalByTBALResponseType resp=cts2.inqCurrBalByTBAL(cuenta).getInqCurrBalByTBALResult();
		String respStr="";

		if (!"000".equals(resp.getStatus())) {
			
			TSYSfaultType fault = resp.getFaults();
			List<TSYSfault> lfaulta = fault.getFault();
			for (TSYSfault sfault : lfaulta) {
				respStr=sfault.getStatus() + " "
						+ sfault.getFaultDesc();
			}
			
		}else{
		
		
		double ppngi=0;
		
			try {
				List<NTBTBALsResponseDataType.TBAL> tbals = resp.getTBALs()
						.getValue().getTBAL();
				for (NTBTBALsResponseDataType.TBAL tbal : tbals) {
					String id = tbal.getId();
					if ("0001".equals(id) || "0002".equals(id)) {
						ppngi += tbal.getAmtPrevCycleUnpaid().getValue()
								.doubleValue();
						ppngi += tbal.getBalPrev().getFinChrg().getValue()
								.doubleValue();
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		
		respStr=""+ppngi;
		}


		return Response.status(200).entity(respStr).build();

	}
	
	@GET
	@Path("/saldos/{param}")
	@Produces(MediaType.APPLICATION_JSON)
	public SaldosCuenta calcularSaldos(@PathParam("param") String cuenta) {

	
		SaldosCuenta sc= new SaldosCuenta();
		ClientTs2 cts2 = new ClientTs2();
		InqCurrBalByTBALResponseType resp=cts2.inqCurrBalByTBAL(cuenta).getInqCurrBalByTBALResult();
		String respStr="";

		if (!"000".equals(resp.getStatus())) {
			
			TSYSfaultType fault = resp.getFaults();
			List<TSYSfault> lfaulta = fault.getFault();
			for (TSYSfault sfault : lfaulta) {
				respStr=sfault.getStatus() + " "
						+ sfault.getFaultDesc();
			}
			
		}else{
		
		
		double ppngi=0;
		
		try {
			List<NTBTBALsResponseDataType.TBAL> tbals = resp.getTBALs()
					.getValue().getTBAL();
			double revolvente=0;
			double msi=0;
			double mci=0;
			List<String>idsMCI= new ArrayList<String>();
			idsMCI.add("0054");
			idsMCI.add("0056");
			idsMCI.add("0057");
			idsMCI.add("0059");
			idsMCI.add("0060");
			idsMCI.add("0061");
			idsMCI.add("0062");
			idsMCI.add("0063");
			idsMCI.add("0064");
			idsMCI.add("0065");
			idsMCI.add("0066");
			idsMCI.add("0067");
			idsMCI.add("0068");
			idsMCI.add("0069");
			idsMCI.add("0070");
			idsMCI.add("0071");
			idsMCI.add("0072");
			idsMCI.add("0073");
			idsMCI.add("0074");
			idsMCI.add("0075");
			idsMCI.add("0076");
			idsMCI.add("0077");
			idsMCI.add("0078");
			idsMCI.add("0079");
			double revAlCorte =0;
			double msiAlCorte=0;
			double mciAlCorte=0;
			double sacoRev=0;
			double sacoRevAlCorte=0;
			for (NTBTBALsResponseDataType.TBAL tbal : tbals) {
				String id = tbal.getId();
				if ("0001".equals(id) || "0002".equals(id)) {
					try {
						ppngi += tbal.getAmtPrevCycleUnpaid().getValue()
								.doubleValue();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						ppngi += tbal.getBalPrev().getFinChrg().getValue()
								.doubleValue();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						revolvente+=tbal.getAmtOutstanding().getTtl().getValue().doubleValue();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					try {
						revAlCorte+=tbal.getBalPrev().getPrincipal().getValue().doubleValue();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						revAlCorte+=tbal.getBalPrev().getFinChrg().getValue().doubleValue();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//msi
				}else if("0050".equals(id) || "0051".equals(id) || "0052".equals(id) || "0053".equals(id) || "0058".equals(id)){
					try {
						msi+=tbal.getAmtOutstanding().getTtl().getValue().doubleValue();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						msiAlCorte+=tbal.getBalPrev().getPrincipal().getValue().doubleValue();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						msiAlCorte+=tbal.getBalPrev().getFinChrg().getValue().doubleValue();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else if(idsMCI.contains(id)){
					try {
						mci +=tbal.getAmtOutstanding().getTtl().getValue().doubleValue();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						mciAlCorte+=tbal.getBalPrev().getPrincipal().getValue().doubleValue();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						mciAlCorte+=tbal.getBalPrev().getFinChrg().getValue().doubleValue();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else if(Integer.parseInt(id)>80){
					
					try {
						sacoRev +=tbal.getAmtOutstanding().getTtl().getValue().doubleValue();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						sacoRevAlCorte+=tbal.getBalPrev().getPrincipal().getValue().doubleValue();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						sacoRevAlCorte+=tbal.getBalPrev().getFinChrg().getValue().doubleValue();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			sc.setMciAlcorte(mciAlCorte);
			sc.setMsiAlcorte(msiAlCorte);
			sc.setRevolventeAlcorte(revAlCorte);
			sc.setMci(mci);
			sc.setMsi(msi);
			sc.setPpngi(ppngi);
			sc.setRevolvente(revolvente);
			sc.setSacosRev(sacoRev);
			sc.setSacoRevAlCorte(sacoRevAlCorte);
		} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		
		
		}
		return sc;
		//return Response.status(200).entity(respStr).build();

	}
}
