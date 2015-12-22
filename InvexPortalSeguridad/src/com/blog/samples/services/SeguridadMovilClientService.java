package com.blog.samples.services;

import mx.com.invex.bmovil.exception.WSSeguridadException;

public interface SeguridadMovilClientService {

	
	public  String autentificarUsuario(String username)
			throws WSSeguridadException;

	
	public  String autentificar(
			String username,
			 String contrasenia)
			throws WSSeguridadException;
	
	public  String autentificarWeb(
			String username,
			 String contrasenia)
			throws WSSeguridadException;

	
	public  String Autorizar(
			 String idSesion)
			throws WSSeguridadException;
	
	public  String AutorizarWeb(
			 String idSesion)
			throws WSSeguridadException;

	
	public  String CerrarSession(
			 String idSesion);
	
	public  String CerrarSessionWeb(
			 String idSesion);

}