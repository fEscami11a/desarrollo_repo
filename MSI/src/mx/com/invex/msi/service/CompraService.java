package mx.com.invex.msi.service;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import mx.com.invex.msi.model.Compra;

public interface CompraService {
	String getStatusCompra(Compra compra);

	public Integer getFolio();

	public void save(Compra compra);

	public void update(Compra compra);

	public List<Compra> findByCriteria(DetachedCriteria criteria);

	public Double getMontoComprasByDateRange(String cuenta, Date fechaIn,
			Date fechaFin);
	
	public Double getMontoComprasByDateRange(String cuenta, Date fechaIn,
			Date fechaFin,String tipoTrans);

	String getStatusCompraItau(Compra compra);
}
