package mx.com.invex.msi.service;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.invex.msi.dao.CompraDao;
import mx.com.invex.msi.model.Compra;
@Service("compraServiceImpl")
public class CompraServiceImpl implements CompraService{

	@Autowired
	CompraDao compraDao;
	
	
	
	public CompraDao getCompraDao() {
		return compraDao;
	}



	public void setCompraDao(CompraDao compraDao) {
		this.compraDao = compraDao;
	}



	@Transactional(readOnly=true)
	public String getStatusCompra(Compra compra) {
		return compraDao.getStatusCompra(compra);
	}
	
	@Override
	@Transactional(readOnly=true)
	public String getStatusCompraItau(Compra compra) {
		return compraDao.getStatusCompraItau(compra);
	}



	@Transactional(readOnly=true)
	public Integer getFolio() {
		return compraDao.getFolio();
	}



	@Transactional
	public void save(Compra compra) {
		compraDao.save(compra);
	}



	@Transactional
	public void update(Compra compra) {
		compraDao.update(compra);
	}


	@Transactional(readOnly=true)
	public List<Compra> findByCriteria(DetachedCriteria criteria) {
		return compraDao.findByCriteria(criteria);
	}

	@Transactional(readOnly=true)
	public List<Object[]> getReporte(DetachedCriteria criteria) {
		return compraDao.getReporte(criteria);
	}



	@Transactional(readOnly=true)
	public Double getMontoComprasByDateRange(String cuenta, Date fechaIn,
			Date fechaFin) {
		return compraDao.getMontoComprasByDateRange(cuenta, fechaIn, fechaFin);
	}
	
	@Transactional(readOnly=true)
	public Double getMontoComprasByDateRange(String cuenta, Date fechaIn,
			Date fechaFin,String tipoTrans) {
		return compraDao.getMontoComprasByDateRange(cuenta, fechaIn, fechaFin, tipoTrans);
	}



	@Transactional(readOnly=true)
	public List<Object[]> getReporteDias(Date fechaIn, Date fechaFin) {
		return compraDao.getReporteDias(fechaIn, fechaFin);
	}

}
