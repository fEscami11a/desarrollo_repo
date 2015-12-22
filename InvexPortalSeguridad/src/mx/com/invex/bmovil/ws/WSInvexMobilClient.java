package mx.com.invex.bmovil.ws;

import mx.com.invex.seguridad.utils.SegConstants;


public class WSInvexMobilClient {
	
public static void main(String[] args) {
	ServiciosPortalImplServiceLocator sl = new ServiciosPortalImplServiceLocator();
	sl.setServiciosPortalImplPortEndpointAddress("http://172.16.18.13:9080/BancaMovilServices/ServiciosPortalImplService");
	try {
		ServiciosPortalImplDelegate port= sl.getServiciosPortalImplPort();
		
			String[] arProd=port.getProductosByWebUsr("azaelg");
			for (String prod : arProd) {
				System.out.println(prod);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}

public static String[] getLuciProdsByWebUsr(String webUsr) throws Exception{
	String[] res= null;
	ServiciosPortalImplServiceLocator sl = new ServiciosPortalImplServiceLocator();
	sl.setServiciosPortalImplPortEndpointAddress(SegConstants.WEB_SERVICE_INVEX_MOVIL);
	
		ServiciosPortalImplDelegate port= sl.getServiciosPortalImplPort();
		
			res=port.getProductosByWebUsr(webUsr);
//			for (String prod : arProd) {
//				System.out.println(prod);
//			}
	
	return res;
}
	
}


