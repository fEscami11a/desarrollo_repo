package mx.com.invex.msi.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.invex.msi.controller.ProductoDao;
import mx.com.invex.msi.model.Producto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service("productoServiceImpl")
public class ProductoServiceImpl implements ProductoService {
	
	@Autowired
	ProductoDao productoDao;
	
	@Transactional(readOnly=true)
	public Producto getProductoCuenta(String cuenta, String ucode3) {
		return productoDao.getProductoCuenta(cuenta, ucode3);
	}

	@Transactional(readOnly=true)
	public List<Producto> getProductCatalog() {
		Map<String,Producto> mapProd = new HashMap<String,Producto>();
		List<Producto> lprod =productoDao.getProductCatalog();
		for (Producto producto : lprod) {
			mapProd.put(producto.getCampania(), producto);
		}
		lprod.clear();
		for (String key : mapProd.keySet()) {
			lprod.add(mapProd.get(key));
		}
		return lprod;
	}

	@Transactional(readOnly=true)
	public Producto getById(Integer id) {
		return productoDao.getById(id);
	}

}
