package mx.com.invex.msi.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import mx.com.invex.msi.model.CodTrans;
import mx.com.invex.msi.service.BitacoraService;
import mx.com.invex.msi.service.CodTransService;
import mx.com.invex.msi.util.MSIException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
@Component
@Scope("session")
public class CodTransBean extends MessagesMBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private List<CodTrans> codTransList = new ArrayList<CodTrans>();
	private static final Logger logger = Logger.getLogger(CodTransBean.class);
	private boolean update;
	private CodTrans codTrans;
	@Autowired
	CodTransService codTransService;
	@Autowired
	BitacoraService bitacoraService;
	@PostConstruct
	public void init(){
		populateCodTrans();
	}
	
	private void populateCodTrans() {
		codTransList = codTransService.getAllCodTrans();
	}

	public String newButtonPressed(){
		
		update = false;
		codTrans = new CodTrans();
		return "editCodeTrans";
	}
	
	public String editButtonPressed(){
		update= true;
		return "editCodeTrans";
	}
	
	public String saveCodTrans() throws MSIException{
		try {
		
			if(update){
				codTransService.update(codTrans);
			}else{
				if(codTransService.getById(codTrans.getCodTrans())!= null){
					sendErrorMessageToUser("El codigo de trasaccion ya existe.");
					return null;
				}else{
					codTransService.save(codTrans);
				}
			}
			populateCodTrans();
			sendInfoMessageToUser("El codigo de transaccion fue registrado con exito");
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new MSIException(e.getMessage());
		}
		return "codTrans";
	}
	
	public List<CodTrans> getCodTransList() {
		return codTransList;
	}
	public void setCodTransList(List<CodTrans> codTransList) {
		this.codTransList = codTransList;
	}
	public CodTrans getCodTrans() {
		return codTrans;
	}
	public void setCodTrans(CodTrans codTrans) {
		this.codTrans = codTrans;
	}
	
	public void delete() throws MSIException {
		try {
			codTransService.delete(codTrans.getCodTrans().toString());
			populateCodTrans();
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			
			throw new MSIException(e.getMessage());
		}
	}
}
