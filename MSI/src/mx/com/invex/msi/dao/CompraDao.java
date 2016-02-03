package mx.com.invex.msi.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import mx.com.invex.msi.model.Compra;

public interface CompraDao {
	
	Integer getFolio();

	public void save(Compra compra);

	public void update(Compra compra);

	public List<Compra> findByCriteria(DetachedCriteria criteria);

	public Double getMontoComprasByDateRange(String cuenta, Date fechaIn,
			Date fechaFin);

	String getStatusCompra(Compra compra);

	Double getMontoComprasByDateRange(String cuenta, Date fechaIn,
			Date fechaFin, String tipoTrans);

	String getStatusCompraItau(Compra compra);

	List<Object[]> getReporte(DetachedCriteria criteria);
}
