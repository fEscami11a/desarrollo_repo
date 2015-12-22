package mx.com.invex.msi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.invex.msi.dao.CodTransDao;
import mx.com.invex.msi.model.CodTrans;
@Service("codTransServiceImpl")
public class CodTransServiceImpl implements CodTransService {

	@Autowired
	CodTransDao codTransDao;
	
	@Transactional(readOnly=true)
	public List<CodTrans> getAllCodTrans() {
		return codTransDao.getAllCodTrans();
	}

	@Transactional(readOnly=true)
	public CodTrans getById(Long id) {
		return codTransDao.getById(id);
	}

	@Transactional
	public void update(CodTrans codTrans) {
		codTransDao.update(codTrans);
		
	}

	@Transactional
	public void save(CodTrans codTrans) {
		codTransDao.save(codTrans);
		
	}
	
	

	@Transactional
	public void delete(String codTrans) {
		codTransDao.delete(codTrans);
		
	}

}
