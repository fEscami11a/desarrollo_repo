package mx.com.invex.bmovil.ws;

public class ServiciosPortalImplDelegateProxy implements mx.com.invex.bmovil.ws.ServiciosPortalImplDelegate {
  private String _endpoint = null;
  private mx.com.invex.bmovil.ws.ServiciosPortalImplDelegate serviciosPortalImplDelegate = null;
  
  public ServiciosPortalImplDelegateProxy() {
    _initServiciosPortalImplDelegateProxy();
  }
  
  public ServiciosPortalImplDelegateProxy(String endpoint) {
    _endpoint = endpoint;
    _initServiciosPortalImplDelegateProxy();
  }
  
  private void _initServiciosPortalImplDelegateProxy() {
    try {
      serviciosPortalImplDelegate = (new mx.com.invex.bmovil.ws.ServiciosPortalImplServiceLocator()).getServiciosPortalImplPort();
      if (serviciosPortalImplDelegate != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)serviciosPortalImplDelegate)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)serviciosPortalImplDelegate)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (serviciosPortalImplDelegate != null)
      ((javax.xml.rpc.Stub)serviciosPortalImplDelegate)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public mx.com.invex.bmovil.ws.ServiciosPortalImplDelegate getServiciosPortalImplDelegate() {
    if (serviciosPortalImplDelegate == null)
      _initServiciosPortalImplDelegateProxy();
    return serviciosPortalImplDelegate;
  }
  
  public java.lang.String getSiguienteDiaHabil(java.lang.String arg0) throws java.rmi.RemoteException, mx.com.invex.bmovil.ws.InvexWSException{
    if (serviciosPortalImplDelegate == null)
      _initServiciosPortalImplDelegateProxy();
    return serviciosPortalImplDelegate.getSiguienteDiaHabil(arg0);
  }
  
  public java.lang.String[] obtenerMovsDosCortesAnteriores(java.lang.String arg0) throws java.rmi.RemoteException, mx.com.invex.bmovil.ws.InvexWSException{
    if (serviciosPortalImplDelegate == null)
      _initServiciosPortalImplDelegateProxy();
    return serviciosPortalImplDelegate.obtenerMovsDosCortesAnteriores(arg0);
  }
  
  public java.lang.String[] obtenerMovsAntesCorte(java.lang.String arg0) throws java.rmi.RemoteException, mx.com.invex.bmovil.ws.InvexWSException{
    if (serviciosPortalImplDelegate == null)
      _initServiciosPortalImplDelegateProxy();
    return serviciosPortalImplDelegate.obtenerMovsAntesCorte(arg0);
  }
  
  public java.lang.String[] obtenerMovsDespuesCorte(java.lang.String arg0) throws java.rmi.RemoteException, mx.com.invex.bmovil.ws.InvexWSException{
    if (serviciosPortalImplDelegate == null)
      _initServiciosPortalImplDelegateProxy();
    return serviciosPortalImplDelegate.obtenerMovsDespuesCorte(arg0);
  }
  
  public java.lang.String[] getProductosByWebUsr(java.lang.String arg0) throws java.rmi.RemoteException, mx.com.invex.bmovil.ws.InvexWSException{
    if (serviciosPortalImplDelegate == null)
      _initServiciosPortalImplDelegateProxy();
    return serviciosPortalImplDelegate.getProductosByWebUsr(arg0);
  }
  
  public java.lang.String[] webMovEdoCtaFech(java.lang.String arg0, java.lang.String arg1, java.lang.String arg2) throws java.rmi.RemoteException, mx.com.invex.bmovil.ws.InvexWSException{
    if (serviciosPortalImplDelegate == null)
      _initServiciosPortalImplDelegateProxy();
    return serviciosPortalImplDelegate.webMovEdoCtaFech(arg0, arg1, arg2);
  }
  
  
}