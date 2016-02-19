package mx.com.invex.msi.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import mx.com.invex.msi.model.RepConcDetalleDto;
import mx.com.invex.msi.service.CompraService;
@Component
@Scope("session")
public class RepConcDetalle extends MessagesMBean  implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(RepConcDetalle.class);
	private List<RepConcDetalleDto> regs;
	
	
	





	public List<RepConcDetalleDto> getRegs() {
		return regs;
	}





	public void setRegs(List<RepConcDetalleDto> regs) {
		this.regs = regs;
	}





	@Autowired
	CompraService compraService;
	
	
	
	

	public String consultarCompras(Date fechaInicio,Date fechaFin){
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy/MM/dd");
		List<Object[]> repDias = compraService.getReporteDias(fechaInicio, fechaFin);
		regs= new ArrayList<RepConcDetalleDto>();
		logger.info("Reporte dias");
		for (Object[] objects : repDias) {
			RepConcDetalleDto dto = new RepConcDetalleDto();
			dto.setTotal(((BigDecimal)objects[0]).intValue());
			dto.setFecha(sdf.format((Date)objects[1]));
			dto.setTotPU(((BigDecimal)objects[2]).intValue());
			dto.setTotPortal(((BigDecimal)objects[3]).intValue());
			dto.setTotEnv(((BigDecimal)objects[4]).intValue());
			dto.setTotApl(((BigDecimal)objects[5]).intValue());
			regs.add(dto);
			for (Object object : objects) {
				
				logger.info(object);
			}
		}
		return "RepConcDetalle";  
	}
	
//	public static void main(String[] args) {
//		 java.util.regex.Pattern pat = java.util.regex.Pattern.compile("(\\d{1,10})?");
//	     Matcher mat = pat.matcher("12346579086");
//	     if (mat.matches()) {
//	         System.out.println("SI");
//	     } else {
//	         System.out.println("NO");
//	     }
//	}

	  
}
