package mx.com.invex.msi.controller;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.rpc.ServiceException;

import mx.com.interware.spira.ls.facade.igbinaenca.ResultadoIGBINAEnca;
import mx.com.invex.msi.model.Parametro;
import mx.com.invex.msi.service.ParametroService;
import mx.com.invex.msi.util.MSIConstants;
import mx.com.invex.msi.util.P0FileWriter;
import mx.com.invex.msi.ws.ClientLSWS;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class Prog0Bean extends MessagesMBean implements Serializable{
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(Prog0Bean.class);
	@Autowired
	ParametroService parametroService;
	
	private String cuenta;
	private Double monto;
	private String nombre;
	@NotNull(message = "El numero de cuenta es requerido")
	@Size(min = 10, max = 10, message = "El numero de telefono celular debe ser de 10 dígitos")
	@Digits(fraction = 0, integer = 10)
	private String telcel;
	private boolean showInfoPanel,inscrito;
	
	
	public boolean isInscrito() {
		return inscrito;
	}

	public void setInscrito(boolean inscrito) {
		this.inscrito = inscrito;
	}

	public boolean isShowInfoPanel() {
		return showInfoPanel;
	}

	public void setShowInfoPanel(boolean showInfoPanel) {
		this.showInfoPanel = showInfoPanel;
	}

	public String getCuenta() {
		return cuenta;
	}

	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	public Double getMonto() {
		return monto;
	}

	public void setMonto(Double monto) {
		this.monto = monto;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelcel() {
		return telcel;
	}

	public void setTelcel(String telcel) {
		this.telcel = telcel;
	}

	public String buscarPorNoCuenta(String cuenta){
		
	Parametro param= parametroService.getParamById(MSIConstants.TSYSWS_ENDPOINT);
	
	ClientLSWS cliLSWS = new ClientLSWS(param.getValor());
	ResultadoIGBINAEnca infoCliente = null;
	try {
		infoCliente= cliLSWS.getResultadoIGBINAEnca(cuenta, MSIConstants.TSYSWS_SECURITY_USERNAME,MSIConstants.TSYSWS_ACCESS_KEY);
	} catch (RemoteException e) {
		sendErrorMessageToUser("Error Servicio Tsys "+e.getMessage());
		logger.error("Error Servicio Tsys "+e.getMessage());
		e.printStackTrace();
	} catch (ServiceException e) {
		sendErrorMessageToUser("Error Servicio Tsys "+e.getMessage());
		logger.error("Error Servicio Tsys "+e.getMessage());
		e.printStackTrace();
	}
	if(infoCliente == null){
		sendErrorMessageToUser("No se encontraron datos con la cuenta dada");
		return null;
	}
	showInfoPanel = true;
	this.cuenta=cuenta;
	telcel=infoCliente.getInfoEncabezado().getTelCelular();
	if(telcel.length()>10){
		telcel = telcel.substring(telcel.length()-10);
	}
	nombre =infoCliente.getInfoDemograficaINA().getNombreCompleto();
	nombre = nombre.replace('*', ' ');
	String credLim=infoCliente.getInfoEncabezado().getCreditoLimite();
	double credl=Double.parseDouble(credLim);
	if(credl>=0 && credl<=20000){
		monto=800.00;
	}else if(credl>20000 && credl<=60000){
		monto=1000.00;
	}else if(credl>60000){
		monto=1500.00;
	}
		return "Prog0Form";
	}
	
public String inscribir(){
		
		logger.info("inscribir "+monto +" nombre "+nombre+ " cel "+telcel);
		
		 try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			 P0FileWriter aLogger = P0FileWriter.getLog(File.separatorChar+"csvsProgCero", "ProgCero_MSI_"+new SimpleDateFormat("ddMMyyyy").format(new Date())+".csv");
			 String line = sdf.format(new Date())+","+nombre+","+cuenta+","+telcel+","+monto;
			 aLogger.log(line);
			 
	    
   } catch (UnsupportedEncodingException e) {
           e.printStackTrace();
   } catch (IOException e) {
           e.printStackTrace();
   }
		inscrito=true;
		  sendInfoMessageToUser("El cliente se ha registrado para inscribirse al programa Cero");
		return "Prog0Form";
	}
	
}
