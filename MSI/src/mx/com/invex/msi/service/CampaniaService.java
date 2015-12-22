package mx.com.invex.msi.service;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import mx.com.invex.msi.model.Campania;
import mx.com.invex.msi.model.Producto;
import mx.com.invex.msi.model.ProductoTs2;

public interface CampaniaService {
	public List<Campania> getCampaniasMasivas();

	Campania getCampaniaInternal();

	List<Campania> getCampaniasComercios();

	public Campania getCampNalProg0();
	public Campania getCampaniaById(Long id);

	public List<Campania> getCampaniaByParams(Date fechaIn, Date fechaMaxReg, String tipoCampania,
			Producto producto);

	public List<Campania> getCampaniaByTipo(String tipoCamp);

	public void saveOrUpdate(Campania campania);

	public void updatePromosCamp(Campania campania);

	List<Object[]> getCampComboByTipo(String tipoCamp);

	List<Campania> findByCriteria(DetachedCriteria criteria);

	public List<Campania> getCampaniaProdTs2(Date diahoy, Date diahoy2,
			String string, ProductoTs2 prodts2);
}
