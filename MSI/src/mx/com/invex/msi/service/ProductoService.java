package mx.com.invex.msi.service;

import java.util.List;

import mx.com.invex.msi.model.Producto;

public interface ProductoService {
	Producto getProductoCuenta(String cuenta, String ucode3);
	List<Producto> getProductCatalog();
	Producto getById(Integer integer);
}
