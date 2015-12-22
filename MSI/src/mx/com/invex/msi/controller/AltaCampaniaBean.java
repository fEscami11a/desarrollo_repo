package mx.com.invex.msi.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import mx.com.invex.msi.model.Campania;
import mx.com.invex.msi.model.CodTrans;
import mx.com.invex.msi.model.Producto;
import mx.com.invex.msi.model.ProductoTs2;
import mx.com.invex.msi.model.Promocion;
import mx.com.invex.msi.service.CampaniaService;
import mx.com.invex.msi.service.CodTransService;
import mx.com.invex.msi.service.ProductoService;
import mx.com.invex.msi.service.ProductoTs2Service;
import mx.com.invex.msi.service.PromocionService;
import mx.com.invex.msi.util.MSIException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class AltaCampaniaBean extends MessagesMBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(AltaCampaniaBean.class);
	
	private Campania campania = new Campania();
	private List<Promocion> promosCamp= new ArrayList<Promocion>();
	private ArrayList<SelectItem> parcialidades = new ArrayList<SelectItem>();
	private List<SelectItem> mesesList = new ArrayList<SelectItem>();
	private String parcialidad;
	private ArrayList<SelectItem> promos = new ArrayList<SelectItem>();
	private Promocion promoVal;
	@NotNull(message = "El monto es requerido")
	@Size(min = 3, max = 5, message = "Monto fuera de rango")
	@Digits(fraction = 0, integer = 5)
	private String montoPromo;
	@Autowired
	PromocionService promocionService;
	@Autowired
	ProductoService productoService;
	@Autowired
	ProductoTs2Service productots2Service;
	@Autowired
	CampaniaService campaniaService;
	@Autowired
	CodTransService codTransService;
	
	private int currentPromoIndex;
	private String[] listProdValues;
	private List<SelectItem> prodList = new ArrayList<SelectItem>();
	
	private String[] listProdts2Values;
	private List<SelectItem> prodts2List = new ArrayList<SelectItem>();
	
	private boolean campGuardada;
	private List<SelectItem> codTransList = new ArrayList<SelectItem>();
	private String codTrans;
	
	
	
	public String[] getListProdts2Values() {
		return listProdts2Values;
	}



	public void setListProdts2Values(String[] listProdts2Values) {
		this.listProdts2Values = listProdts2Values;
	}



	public List<SelectItem> getProdts2List() {
		return prodts2List;
	}



	public void setProdts2List(List<SelectItem> prodts2List) {
		this.prodts2List = prodts2List;
	}



	public String getCodTrans() {
		return codTrans;
	}



	public void setCodTrans(String codTrans) {
		this.codTrans = codTrans;
	}



	public List<SelectItem> getCodTransList() {
		return codTransList;
	}



	public void setCodTransList(List<SelectItem> codTransList) {
		this.codTransList = codTransList;
	}



	public boolean isCampGuardada() {
		return campGuardada;
	}



	public void setCampGuardada(boolean campGuardada) {
		this.campGuardada = campGuardada;
	}



	public String[] getListProdValues() {
		return listProdValues;
	}



	public void setListProdValues(String[] listProdValues) {
		this.listProdValues = listProdValues;
		
	}



	public List<SelectItem> getProdList() {
		
		
		return prodList;
	}



	public void setProdList(List<SelectItem> prodList) {
		this.prodList = prodList;
	}



	public int getCurrentPromoIndex() {
		return currentPromoIndex;
	}



	public void setCurrentPromoIndex(int currentPromoIndex) {
		this.currentPromoIndex = currentPromoIndex;
	}



	public String getMontoPromo() {
		return montoPromo;
	}



	public void setMontoPromo(String montoPromo) {
		this.montoPromo = montoPromo;
	}



	public Campania getCampania() {
		return campania;
	}



	public Promocion getPromoVal() {
		return promoVal;
	}



	public void setPromoVal(Promocion promoVal) {
		this.promoVal = promoVal;
	}



	public String getParcialidad() {
		return parcialidad;
	}



	public ArrayList<SelectItem> getPromos() {
		return promos;
	}



	public void setPromos(ArrayList<SelectItem> promos) {
		this.promos = promos;
	}



	public void setParcialidad(String parcialidad) {
		this.parcialidad = parcialidad;
	}



	public void setCampania(Campania campania) {
		this.campania = campania;
	}



	public List<Promocion> getPromosCamp() {
		return promosCamp;
	}



	public void setPromosCamp(List<Promocion> promosCamp) {
		this.promosCamp = promosCamp;
	}



	public ArrayList<SelectItem> getParcialidades() {
		return parcialidades;
	}



	public void setParcialidades(ArrayList<SelectItem> parcialidades) {
		this.parcialidades = parcialidades;
	}



	public String nuevaCamp() throws MSIException{
		try {
			
			campania = new Campania();
			promosCamp.clear();
			listProdValues = null;
			listProdts2Values = null;
			campGuardada= false;
			codTrans="";
			if(prodts2List.isEmpty()){
				List<ProductoTs2> list=productots2Service.getProductCatalog();
				for (ProductoTs2 prod : list) {
					prodts2List.add( new SelectItem(prod.getId().toString(), prod.getProducto()));
				}
			}
			
			 if(prodList.isEmpty()){
					List<Producto> list=productoService.getProductCatalog();
					for (Producto producto : list) {
						prodList.add( new SelectItem(producto.getId().toString(), producto.getCampania()));
					}
				}
			 if(codTransList.isEmpty()){
				 List<CodTrans> list= codTransService.getAllCodTrans();
				 for (CodTrans codTrans : list) {
					codTransList.add(new SelectItem(codTrans.getCodTrans().toString(),codTrans.getDescripcion()));
				}
			 }
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			
			throw new MSIException(e.getMessage());
		}
		return "/admin/AltaCampaniaForm";
	}

	public List<SelectItem> getMesesList() {
		if(mesesList.isEmpty()){
			List<Integer> meses=promocionService.getMesesPromos();
			SelectItem item = new SelectItem("", "");
			mesesList.add(item);
			for (Integer parcialidad : meses) {
				item = new SelectItem(parcialidad,parcialidad+ " meses");
				mesesList.add(item);
			}
		}
		return mesesList;
	}
	public void mesesChanged(ValueChangeEvent event) {
		String comboMesesVal =(String)event.getNewValue();
		//logger.info("valor combo meses: "+comboMesesVal);
		promos.clear();
		if (null != comboMesesVal) {
			List<Promocion> lpromos =promocionService.getPromosByMes(comboMesesVal);
			for (Promocion promocion : lpromos) {
				logger.info("promo "+promocion.getDescripcion());
				promos.add( new SelectItem( promocion,promocion.getIdPromocion()+" "+ promocion.getDescripcion()));
			}
		}
	}
	
	
	public String agregarPromocion(){
		logger.info("agergar "+promoVal.getDescripcion() + " con monto " + montoPromo);
		
		Promocion promo = promoVal;
		//promo.setMonto(new Double(montoPromo));
		

		for (Promocion promoCampania : promosCamp) {
			if(promoCampania.equals(promoVal)){
				logger.info("promo duplicada");
				sendErrorMessageToUser("La promocion esta duplicada");
				return null;
			}
		}
		promosCamp.add(promo);
		return null;
	}
	
	 public void remove() {
		 logger.info("remove index "+currentPromoIndex);
	        promosCamp.remove(promosCamp.get(currentPromoIndex));
	    }
	 
	 public String guardarCampania() throws MSIException{
		 
		logger.info("se guarda campana "+campania.getNombre() +" creada por "+getUsername());
		 try {
			
			//if(!"segmentada".equals(campania.getTipoCampania())){
					if(campania.getFechaInicial()!= null && campania.getFechaFinal()!= null){
						if(campania.getFechaInicial().after(campania.getFechaFinal())){
						sendErrorMessageToUser("La fecha inicial deber ser anterior a la fecha final");
						return null;
						}

						if(campania.getFechaMaximaRegistro() != null && campania.getFechaFinal().after(campania.getFechaMaximaRegistro())){
							 sendErrorMessageToUser("La fecha final no puede ser posterior a la fecha maxima de registro");
								return null;
							}
						
					}
					
				//}
			 logger.info("camp final "+campania.getFechaFinal()+" fecha max "+campania.getFechaMaximaRegistro());
			 
			 
			 if(promosCamp.isEmpty()){
				 sendErrorMessageToUser("Favor de agregar las promociones de la campaña");
				 return null;
			 }
			 
			 if(listProdts2Values !=null && listProdts2Values.length > 0){
					Set<ProductoTs2> productos = new HashSet<ProductoTs2>();
					for(String cveProd: listProdts2Values){
						ProductoTs2 prod = productots2Service.getById(new Integer(cveProd));
						productos.add(prod);
						
					}
					campania.setProductosts2(productos);
				}
			 
			 if(listProdValues !=null && listProdValues.length > 0){
					Set<Producto> productos = new HashSet<Producto>();
					for(String cveProd: listProdValues){
						Producto prod = productoService.getById(new Integer(cveProd));
						productos.add(prod);
						
					}
					campania.setProductos(productos);
				}
			 campania.setTipoCaducidadRegistro("dinamico");
			 Set<Promocion> pcs = new HashSet<Promocion>();
			 pcs.addAll(promosCamp);
			 campania.setPromociones(pcs);
			
			 campania.setCodigoTransaccion1(codTrans);
			 campaniaService.saveOrUpdate(campania);
			
			
			 //campaniaService.updatePromosCamp(campania);
			 campGuardada = true;
			 sendInfoMessageToUser("La campaña ha sido creada/modificada con exito");
		} catch (NumberFormatException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			
			throw new MSIException("NumberFormatException "+e.getMessage());
		}catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			
			throw new MSIException(e.getMessage());
		}
		 return null;
	 }
	
	 public String modificarCamp() throws MSIException{
		 try {
			 logger.info("se modifica campana "+campania.getNombre() +" creada por "+getUsername());
			promosCamp.clear();
			 listProdValues= null;
			 listProdts2Values= null;
			 
			 campGuardada= false;
			 if(prodts2List.isEmpty()){
					List<ProductoTs2> list=productots2Service.getProductCatalog();
					for (ProductoTs2 producto : list) {
						prodts2List.add( new SelectItem(producto.getId().toString(), producto.getProducto()));
					}
				}
			 
			 if(prodList.isEmpty()){
					List<Producto> list=productoService.getProductCatalog();
					for (Producto producto : list) {
						prodList.add( new SelectItem(producto.getId().toString(), producto.getCampania()));
					}
				}
			 
			 
			 if(codTransList.isEmpty()){
				 List<CodTrans> list= codTransService.getAllCodTrans();
				 for (CodTrans codTrans : list) {
					codTransList.add(new SelectItem(codTrans.getCodTrans().toString(),codTrans.getDescripcion()));
				}
			 }
			
			 this.codTrans= campania.getCodigoTransaccion1();
			 
			 if(campania != null && campania.getProductos() != null){
					Set<Producto> setProductos= campania.getProductos();
					promosCamp.addAll(campania.getPromociones());
					if(!setProductos.isEmpty()){
						listProdValues = new String[setProductos.size()];
						int i= 0;
						for (Producto prod : setProductos) {
							logger.info("producto camp "+prod.getCampania());
							listProdValues[i]=prod.getId().toString();
							i++;		
						}
					}
				}
			 
			 if(campania != null && campania.getProductosts2() != null){
					Set<ProductoTs2> setProductos= campania.getProductosts2();
					
					if(!setProductos.isEmpty()){
						listProdts2Values = new String[setProductos.size()];
						int i= 0;
						for (ProductoTs2 prod : setProductos) {
							logger.info("producto camp "+prod.getProducto());
							listProdts2Values[i]=prod.getId().toString();
							i++;		
						}
					}
				}
			 
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			
			throw new MSIException(e.getMessage());
		}
			return "EditCampaniaForm";
		}
}
