package mx.com.invex.seguridad.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Helper {
	static List<BeanProductosTs2> productos;

	static{
		productos = new ArrayList<BeanProductosTs2>();
		
       
       productos.add( new BeanProductosTs2("Movistar MC Clasica",    "546804", "MC", "MSC") );
       productos.add( new BeanProductosTs2("ITAUCARD MC Clasica",    "546804", "MC", "IMC") );
       productos.add( new BeanProductosTs2("Movistar MC Oro",        "546759", "MG", "MSG") );
       productos.add( new BeanProductosTs2("ITAUCARD MC Oro",        "546759", "MG", "IMG") );
       productos.add( new BeanProductosTs2("ITAUCARD VISA Oro",      "419691", "VG", "IVG") );
       productos.add( new BeanProductosTs2("ITAUCARD Standard Oro",  "419691", "VG", "STA") );
       productos.add( new BeanProductosTs2("DECOMPRAS VISA Clasica", "419690", "VS", "PZA") );
       productos.add( new BeanProductosTs2("ITAUCARD VISA Clasica",  "419690", "VS", "IVC") );
       
       productos.add( new BeanProductosTs2("VOLARIS VISA PLATINUM",  "419691", "VP", "VL1") );
       productos.add( new BeanProductosTs2("VOLARIS VISA PLATINUM",  "419691", "VP", "VL2") );
       productos.add( new BeanProductosTs2("VOLARIS VISA PLATINUM",  "419691", "VG", "VL1") );
       productos.add( new BeanProductosTs2("VOLARIS VISA PLATINUM",  "419691", "VG", "VL2") );
       
       productos.add( new BeanProductosTs2("Radio Shack MC", "546804", "MC", "RDS") );
       productos.add( new BeanProductosTs2("SiCardPlatinum","463186","VP","SIC"));
    		   productos.add( new BeanProductosTs2("SiCardPlus","446138","VS","SIP"));
    				   productos.add( new BeanProductosTs2("Mi Banco","406130","VS","BAM"));
    						   productos.add( new BeanProductosTs2("VolarisINVEX","463186","VP","VL1"));
    								   productos.add( new BeanProductosTs2("INVEX Manchester United","406130","VS","MAN"));
       
       
	}
	
	

	
	
	public static boolean cuentaITAU(String cuenta){
        if(cuenta.length() >= 6 ){
        	
        	boolean cuentaEmpresarial = cuenta.startsWith("547736") || cuenta.startsWith("463187"); 
			
			return !cuentaEmpresarial;
            
        }
		return false;
	}
	
	public static String getProductoItau(String cuenta, String tpc, String cpc){
		if(cuenta.length() >= 6 ){
        	for (BeanProductosTs2 prod : productos) {
    			if(prod.getBin().equals(cuenta.substring(0,6)) &&
    					prod.getTpc().equals(tpc) &&
    					prod.getCpc().equals(cpc)){
    				return prod.getProd();
    			}
    		}
            
        }
		return null;
	}
}
