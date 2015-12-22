package mx.com.invex.msi.service;

import java.util.List;

import mx.com.invex.msi.dao.ProductoTs2Dao;
import mx.com.invex.msi.model.ProductoTs2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("productoTs2ServiceImpl")
public class ProductoTs2ServiceImpl implements ProductoTs2Service{
@Autowired
ProductoTs2Dao productoTs2Dao;
@Transactional(readOnly=true)
public boolean cuentaITAU(String cuenta){
	return productoTs2Dao.cuentaITAU(cuenta);
}
@Transactional(readOnly=true)
public ProductoTs2 getProductoItau(String cuenta,String tpc, String cpc){
	return productoTs2Dao.getProductoItau(cuenta, tpc, cpc);
}
@Transactional(readOnly=true)
public List<ProductoTs2> getProductCatalog(){
	return productoTs2Dao.getProductCatalog();
}

@Transactional(readOnly=true)
public ProductoTs2 getById(Integer integer) {
	return productoTs2Dao.getById(integer);
}
}
