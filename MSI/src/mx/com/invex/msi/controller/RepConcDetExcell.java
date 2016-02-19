package mx.com.invex.msi.controller;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import mx.com.invex.msi.model.RepConcDetalleDto;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
@ManagedBean(name="repConcDetExcell")
@SessionScoped
public class RepConcDetExcell extends MessagesMBean implements Serializable{
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(RepConcDetExcell.class);
	public String buttonId; 
	JasperPrint jasperPrint;  
//	public void printIt(List<Compra> lcomp){
//		System.out.println("hola listener");
//		//Get submit button id
//		if(lcomp == null){
//			System.out.println("lcom null");
//		}else{
//			System.out.println("llego compras");
//		}
// 
//	}
 
	
	
	 public void init(List<RepConcDetalleDto> compras,Date fechaIn,Date fechaFin) throws JRException{  
		
		 logger.info("num compras compras listener "+compras.size());
		
	        JRBeanCollectionDataSource beanCollectionDataSource=new JRBeanCollectionDataSource(compras);  
	String  reportPath=  FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reports/ReporteConcDetalle.jasper");        
	Map<String, Object> params = new HashMap<String,Object>();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	params.put("fechaInicio", sdf.format(fechaIn));
	params.put("fechaFin",sdf.format(fechaFin));
	jasperPrint=JasperFillManager.fillReport(reportPath, params,beanCollectionDataSource);  
	    }  
	
	 public void XLSX(List<RepConcDetalleDto> lcomp,Date fechaIn,Date fechaFin) throws JRException, IOException{  
	     logger.info("XLSLS");   
		 try {
			init(lcomp,fechaIn,fechaFin);  
			   HttpServletResponse httpServletResponse=(HttpServletResponse)getContext().getExternalContext().getResponse();  
			  httpServletResponse.addHeader("Content-disposition", "attachment; filename=report.xlsx");  
			   ServletOutputStream servletOutputStream=httpServletResponse.getOutputStream();  
			   JRXlsxExporter docxExporter=new JRXlsxExporter();  
			   docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);  
			   docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);  
			   docxExporter.exportReport();  
			   getContext().responseComplete();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	   }
}