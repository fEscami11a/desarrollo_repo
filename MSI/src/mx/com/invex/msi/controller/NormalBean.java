package mx.com.invex.msi.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import mx.com.invex.msi.model.Compra;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;

import org.apache.log4j.Logger;
@ManagedBean(name="normal")
@SessionScoped
public class NormalBean extends MessagesMBean implements Serializable{
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(NormalBean.class);
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
 
	
	
	 public void init(List<Compra> compras) throws JRException{  
		
		 logger.info("num compras compras listener "+compras.size());
		
	        JRBeanCollectionDataSource beanCollectionDataSource=new JRBeanCollectionDataSource(compras);  
	String  reportPath=  FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reports/ReporteComprasMSI.jasper");        
	jasperPrint=JasperFillManager.fillReport(reportPath, new HashMap<String, Object>(),beanCollectionDataSource);  
	    }  
	
	 public void XLSX(List<Compra> lcomp) throws JRException, IOException{  
	     logger.info("XLSLS");   
		 try {
			init(lcomp);  
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