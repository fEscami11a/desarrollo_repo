package mx.com.invex.msi.service;

import java.util.List;

import mx.com.invex.msi.model.ArchivoDetalle;

public interface ArchivoDetalleService {
	public List<ArchivoDetalle> getArDetalleByCuenta(String cuenta); 
}
