package mx.com.invex.msi.dao;

import java.util.List;

import mx.com.invex.msi.model.CodTrans;

public interface CodTransDao {
	List<CodTrans> getAllCodTrans();
	
	CodTrans getById(Long id);

	void update(CodTrans codTrans);

	void save(CodTrans codTrans);

	void delete(String codTrans);

}
