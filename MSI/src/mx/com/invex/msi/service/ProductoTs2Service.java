package mx.com.invex.msi.service;

import java.util.List;

import mx.com.invex.msi.model.ProductoTs2;

public interface ProductoTs2Service {
	boolean cuentaITAU(String cuenta);
	ProductoTs2 getProductoItau(String cuenta,String tpc, String cpc);
	List<ProductoTs2> getProductCatalog();
	ProductoTs2 getById(Integer integer);
}
