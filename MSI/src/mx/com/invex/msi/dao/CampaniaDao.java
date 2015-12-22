package mx.com.invex.msi.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import mx.com.invex.msi.model.Campania;
import mx.com.invex.msi.model.Producto;
import mx.com.invex.msi.model.ProductoTs2;

public interface CampaniaDao {
public List<Campania> getCampaniasMasivas();

Campania getCampaniaInternal();

List<Campania> getCampaniasComercios();

public Campania getCampaniaNalProg0();
public Campania getCampaniaById(Long id);

public List<Campania> getCampaniaByParams(Date fechaIn, Date fechaMaxReg,
		String tipoCampania, Producto producto);

public List<Campania> getCampaniaByTipo(String tipoCamp);

public void saveOrUpdate(Campania campania);

public void updatePromosCamp(Campania campania);

List<Object[]> getCampComboByTipo(String tipoCamp);

List<Campania> findByCriteria(DetachedCriteria criteria);

public List<Campania> getCampaniaProdTs2(Date fechaIn, Date fechaMaxReg,
		String tipoCampania, ProductoTs2 producto);
}
