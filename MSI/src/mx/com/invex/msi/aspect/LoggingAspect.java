package mx.com.invex.msi.aspect;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;

import mx.com.invex.msi.controller.MessagesMBean;
import mx.com.invex.msi.model.Bitacora;
import mx.com.invex.msi.service.BitacoraService;

@Aspect
public class LoggingAspect extends MessagesMBean implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	BitacoraService bitacoraService;
	
	@Before("execution(* nuevaCamp(..))")
	public void nuevaCampAccessCheck(JoinPoint joinPoint) {
		Bitacora bitacora= new Bitacora();
		bitacora.setFecha(new Date());
		bitacora.setIpHost(this.getRemoteIp());
		bitacora.setUsername(this.getUsername());
		bitacora.setTipo("INFO");
		bitacora.setMensaje("Ingresa a " +joinPoint.getTarget().getClass().getName() + "->"+joinPoint.getSignature().getName());
		bitacoraService.saveBitacora(bitacora);
		
//		System.out.println("Join point kind : " + joinPoint.getKind());
//		System.out.println("Signature declaring type : "+ joinPoint.getSignature().getDeclaringTypeName());
//		System.out.println("Signature name : " + joinPoint.getSignature().getName());
//		//System.out.println("Arguments : " + Arrays.toString(joinPoint.getArgs()));
//		System.out.println("Target class : "+ joinPoint.getTarget().getClass().getName());
//		System.out.println("This class : " + joinPoint.getThis().getClass().getName());
	  }
	
	@Before("execution(* guardarCampania(..))")
	public void guardarCampaniaAccessCheck(JoinPoint joinPoint) {
		Bitacora bitacora= new Bitacora();
		bitacora.setFecha(new Date());
		bitacora.setIpHost(this.getRemoteIp());
		bitacora.setUsername(this.getUsername());
		bitacora.setTipo("INFO");
		bitacora.setMensaje("Ingresa a " +joinPoint.getTarget().getClass().getName() + "->"+joinPoint.getSignature().getName());
		bitacoraService.saveBitacora(bitacora);
	}
	
	@Before("execution(* modificarCamp(..))")
	public void modificarCampAccessCheck(JoinPoint joinPoint) {
		Bitacora bitacora= new Bitacora();
		bitacora.setFecha(new Date());
		bitacora.setIpHost(this.getRemoteIp());
		bitacora.setUsername(this.getUsername());
		bitacora.setTipo("INFO");
		bitacora.setMensaje("Ingresa a " +joinPoint.getTarget().getClass().getName() + "->"+joinPoint.getSignature().getName());
		bitacoraService.saveBitacora(bitacora);
	}
	
	@Before("execution(* guardarPromo(..))")
	public void guardarPromoAccessCheck(JoinPoint joinPoint) {
		Bitacora bitacora= new Bitacora();
		bitacora.setFecha(new Date());
		bitacora.setIpHost(this.getRemoteIp());
		bitacora.setUsername(this.getUsername());
		bitacora.setTipo("INFO");
		bitacora.setMensaje("Ingresa a " +joinPoint.getTarget().getClass().getName() + "->"+joinPoint.getSignature().getName());
		bitacoraService.saveBitacora(bitacora);
	}
	
	@Before("execution(* aplicarPromos(..))")
	public void aplicarPromosAccessCheck(JoinPoint joinPoint) {
		Bitacora bitacora= new Bitacora();
		bitacora.setFecha(new Date());
		bitacora.setIpHost(this.getRemoteIp());
		bitacora.setUsername(this.getUsername());
		bitacora.setTipo("INFO");
		bitacora.setMensaje("Ingresa a " +joinPoint.getTarget().getClass().getName() + "->"+joinPoint.getSignature().getName());
		bitacoraService.saveBitacora(bitacora);
		System.out.println("Arguments : " + Arrays.toString(joinPoint.getArgs()));
	}
	
	@Before("execution(* enviarMensaje(..))")
	public void enviarMensajeAccessCheck(JoinPoint joinPoint) {
		Bitacora bitacora= new Bitacora();
		bitacora.setFecha(new Date());
		bitacora.setIpHost(this.getRemoteIp());
		bitacora.setUsername(this.getUsername());
		bitacora.setTipo("INFO");
		bitacora.setMensaje("Ingresa a " +joinPoint.getTarget().getClass().getName() + "->"+joinPoint.getSignature().getName() +" params: "+ Arrays.toString(joinPoint.getArgs()));
		bitacoraService.saveBitacora(bitacora);
		System.out.println("Arguments : " + Arrays.toString(joinPoint.getArgs()));
	}
	
	@Before("execution(* newButtonPressed(..))")
	public void newButtonPressedAccessCheck(JoinPoint joinPoint) {
		Bitacora bitacora= new Bitacora();
		bitacora.setFecha(new Date());
		bitacora.setIpHost(this.getRemoteIp());
		bitacora.setUsername(this.getUsername());
		bitacora.setTipo("INFO");
		bitacora.setMensaje("Ingresa a " +joinPoint.getTarget().getClass().getName() + "->"+joinPoint.getSignature().getName() +" params: "+ Arrays.toString(joinPoint.getArgs()));
		bitacoraService.saveBitacora(bitacora);
		System.out.println("Arguments : " + Arrays.toString(joinPoint.getArgs()));
	}
	
	@Before("execution(* saveCodTrans(..))")
	public void saveCodTransCheck(JoinPoint joinPoint) {
		Bitacora bitacora= new Bitacora();
		bitacora.setFecha(new Date());
		bitacora.setIpHost(this.getRemoteIp());
		bitacora.setUsername(this.getUsername());
		bitacora.setTipo("INFO");
		bitacora.setMensaje("Ingresa a " +joinPoint.getTarget().getClass().getName() + "->"+joinPoint.getSignature().getName() +" params: "+ Arrays.toString(joinPoint.getArgs()));
		bitacoraService.saveBitacora(bitacora);
		System.out.println("Arguments : " + Arrays.toString(joinPoint.getArgs()));
	}
	
	@Before("execution(* buscarArchivo(..))")
	public void buscarArchivoCheck(JoinPoint joinPoint) {
		Bitacora bitacora= new Bitacora();
		bitacora.setFecha(new Date());
		bitacora.setIpHost(this.getRemoteIp());
		bitacora.setUsername(this.getUsername());
		bitacora.setTipo("INFO");
		bitacora.setMensaje("Ingresa a " +joinPoint.getTarget().getClass().getName() + "->"+joinPoint.getSignature().getName() +" params: "+ Arrays.toString(joinPoint.getArgs()));
		bitacoraService.saveBitacora(bitacora);
		System.out.println("Arguments : " + Arrays.toString(joinPoint.getArgs()));
	}
	
	@Before("execution(* detalleArchivo(..))")
	public void detalleArchivoCheck(JoinPoint joinPoint) {
		Bitacora bitacora= new Bitacora();
		bitacora.setFecha(new Date());
		bitacora.setIpHost(this.getRemoteIp());
		bitacora.setUsername(this.getUsername());
		bitacora.setTipo("INFO");
		bitacora.setMensaje("Ingresa a " +joinPoint.getTarget().getClass().getName() + "->"+joinPoint.getSignature().getName() +" params: "+ Arrays.toString(joinPoint.getArgs()));
		bitacoraService.saveBitacora(bitacora);
		System.out.println("Arguments : " + Arrays.toString(joinPoint.getArgs()));
	}
	
	@Before("execution(* borrarArchivo(..))")
	public void borrarArchivoCheck(JoinPoint joinPoint) {
		Bitacora bitacora= new Bitacora();
		bitacora.setFecha(new Date());
		bitacora.setIpHost(this.getRemoteIp());
		bitacora.setUsername(this.getUsername());
		bitacora.setTipo("INFO");
		bitacora.setMensaje("Ingresa a " +joinPoint.getTarget().getClass().getName() + "->"+joinPoint.getSignature().getName() +" params: "+ Arrays.toString(joinPoint.getArgs()));
		bitacoraService.saveBitacora(bitacora);
		System.out.println("Arguments : " + Arrays.toString(joinPoint.getArgs()));
	}
	
	@Before("execution(* buscarCampanias(..))")
	public void buscarCampaniasCheck(JoinPoint joinPoint) {
		Bitacora bitacora= new Bitacora();
		bitacora.setFecha(new Date());
		bitacora.setIpHost(this.getRemoteIp());
		bitacora.setUsername(this.getUsername());
		bitacora.setTipo("INFO");
		bitacora.setMensaje("Ingresa a " +joinPoint.getTarget().getClass().getName() + "->"+joinPoint.getSignature().getName() +" params: "+ Arrays.toString(joinPoint.getArgs()));
		bitacoraService.saveBitacora(bitacora);
		
	}
	
	@Before("execution(* buscarPromo(..))")
	public void buscarPromoCheck(JoinPoint joinPoint) {
		Bitacora bitacora= new Bitacora();
		bitacora.setFecha(new Date());
		bitacora.setIpHost(this.getRemoteIp());
		bitacora.setUsername(this.getUsername());
		bitacora.setTipo("INFO");
		bitacora.setMensaje("Ingresa a " +joinPoint.getTarget().getClass().getName() + "->"+joinPoint.getSignature().getName() );
		bitacoraService.saveBitacora(bitacora);
		
	}
	@Before("execution(* guardarArchivo(..))")
	public void guardarArchivoCheck(JoinPoint joinPoint) {
		Bitacora bitacora= new Bitacora();
		bitacora.setFecha(new Date());
		bitacora.setIpHost(this.getRemoteIp());
		bitacora.setUsername(this.getUsername());
		bitacora.setTipo("INFO");
		bitacora.setMensaje("Ingresa a " +joinPoint.getTarget().getClass().getName() + "->"+joinPoint.getSignature().getName() +" params: "+ Arrays.toString(joinPoint.getArgs()));
		bitacoraService.saveBitacora(bitacora);
		
	}
	
	@Before("execution(* buscarPorNoCuenta(..))")
	public void buscarPorNoCuenta(JoinPoint joinPoint) {
		Bitacora bitacora= new Bitacora();
		bitacora.setFecha(new Date());
		bitacora.setIpHost(this.getRemoteIp());
		bitacora.setUsername(this.getUsername());
		bitacora.setTipo("INFO");
		bitacora.setMensaje("Ingresa a " +joinPoint.getTarget().getClass().getName() + "->"+joinPoint.getSignature().getName() +" params: "+ Arrays.toString(joinPoint.getArgs()));
		bitacoraService.saveBitacora(bitacora);
		
	}
	
	@Before("execution(* resumenCompras(..))")
	public void resumenCompras(JoinPoint joinPoint) {
		Bitacora bitacora= new Bitacora();
		bitacora.setFecha(new Date());
		bitacora.setIpHost(this.getRemoteIp());
		bitacora.setUsername(this.getUsername());
		bitacora.setTipo("INFO");
		bitacora.setMensaje("Ingresa a " +joinPoint.getTarget().getClass().getName() + "->"+joinPoint.getSignature().getName() );
		bitacoraService.saveBitacora(bitacora);
		
	}
	
	
	

}
