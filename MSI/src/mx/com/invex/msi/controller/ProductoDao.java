package mx.com.invex.msi.controller;

import java.util.List;

import mx.com.invex.msi.model.Producto;

public interface ProductoDao {
Producto getProductoCuenta(String cuenta, String ucode3);

List<Producto> getProductCatalog();

Producto getById(Integer id);
}
