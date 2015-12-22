package mx.com.invex.msi.controller;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

public abstract class MessagesMBean {
	 protected void sendInfoMessageToUser(String message){
		  FacesContext context = getContext();
		  context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, message));
		 }

		protected void sendErrorMessageToUser(String message){
		  FacesContext context = getContext();
		  context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
		 }

		 protected FacesContext getContext() {
		  FacesContext context = FacesContext.getCurrentInstance();
		  return context;
		 }
		 protected String getUsername(){
			 String username=getContext().getExternalContext().getRemoteUser();
			 return username;
		 }
		 
		 protected String getRemoteIp(){
			
				HttpServletRequest httpServletRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();  
				String ip = httpServletRequest.getRemoteAddr(); 
				return ip;
		 }

}
