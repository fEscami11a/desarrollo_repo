package mx.com.invex.msi.dao;

import java.util.List;

import mx.com.invex.msi.model.ArchivoDetalle;

public interface ArchivoDetalleDao {

	public List<ArchivoDetalle> getArDetalleByCuenta(String cuenta);
}
