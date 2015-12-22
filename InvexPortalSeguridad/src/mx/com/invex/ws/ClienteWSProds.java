package mx.com.invex.ws;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

public class ClienteWSProds {
	public static CuentaAD[] getOtrosProductos(String tarjeta){
        long time = System.currentTimeMillis();        
        System.out.println( "Ejecuta getOtrosProductos---- time 1>>  " +  ( System.currentTimeMillis() - time )/1000  );
  time = System.currentTimeMillis();
  
        CuentaAD[] cuentas = null;
        CallcenterWServiceServiceLocator locator = new CallcenterWServiceServiceLocator();
        
       // locator.setCallcenterWServicePortEndpointAddress(p.getPropertiesFile("adicionales"));
        
        try {
               
               CallcenterWService service = locator.getCallcenterWServicePort();
               
               cuentas =  service.getCuentasAD(tarjeta);
               
               
               for (CuentaAD cuentaAD : cuentas) {                         
                      System.out.println("CUENTA: " + cuentaAD.getNumCuenta() + " UCODE: " + cuentaAD.getUcode3() + 
                                                    " TIPO DE CUENTA : " + cuentaAD.getTipoCuenta() + " CLASIFICACION: " + cuentaAD.getClasificacion());
                      
                      if(cuentaAD.getUcode3() != null){
                             if(!cuentaAD.getUcode3().trim().equals("CC") ){
                             System.out.println("   tarjeta: " + cuentaAD.getUcode3().substring(1, 2 )   
                                                       +  "   estatus: " + cuentaAD.getUcode3().substring(2, 3 ) );
                             
                             }else{
                                    System.out.println(cuentaAD.getUcode3());
                             }
                      }else{
                             System.out.println("UCODE3 -> null");
                      }
               }
               
               
               
        } catch (ServiceException e) {
               e.printStackTrace();
        } catch (InvexServiceException se){
               se.printStackTrace();
               
        } catch (RemoteException re){
               re.printStackTrace();
        }
        
        
        System.out.println( "같같같같같||------> YA ACABO!!!! time 2>>  " +  ( System.currentTimeMillis() - time )/1000  );
  time = System.currentTimeMillis(); 
        return cuentas;
  }
	
	public static void main(String[] args) {
		getOtrosProductos("4631869000451424");
	}

}
