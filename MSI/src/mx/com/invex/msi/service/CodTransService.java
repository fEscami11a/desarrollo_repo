package mx.com.invex.msi.service;

import java.util.List;

import mx.com.invex.msi.model.CodTrans;


public interface CodTransService {
 List<CodTrans> getAllCodTrans();
 CodTrans getById(Long id);
void update(CodTrans codTrans);
void save(CodTrans codTrans);
void delete(String string);
}
