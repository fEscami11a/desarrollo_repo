package mx.com.invex.bmovil.ws;

public class ServiciosBancaMovilImplDelegateProxy implements mx.com.invex.bmovil.ws.ServiciosBancaMovilImplDelegate {
  private String _endpoint = null;
  private mx.com.invex.bmovil.ws.ServiciosBancaMovilImplDelegate serviciosBancaMovilImplDelegate = null;
  
  public ServiciosBancaMovilImplDelegateProxy() {
    _initServiciosBancaMovilImplDelegateProxy();
  }
  
  public ServiciosBancaMovilImplDelegateProxy(String endpoint) {
    _endpoint = endpoint;
    _initServiciosBancaMovilImplDelegateProxy();
  }
  
  private void _initServiciosBancaMovilImplDelegateProxy() {
    try {
      serviciosBancaMovilImplDelegate = (new mx.com.invex.bmovil.ws.ServiciosBancaMovilImplServiceLocator()).getServiciosBancaMovilImplPort();
      if (serviciosBancaMovilImplDelegate != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)serviciosBancaMovilImplDelegate)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)serviciosBancaMovilImplDelegate)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (serviciosBancaMovilImplDelegate != null)
      ((javax.xml.rpc.Stub)serviciosBancaMovilImplDelegate)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public mx.com.invex.bmovil.ws.ServiciosBancaMovilImplDelegate getServiciosBancaMovilImplDelegate() {
    if (serviciosBancaMovilImplDelegate == null)
      _initServiciosBancaMovilImplDelegateProxy();
    return serviciosBancaMovilImplDelegate;
  }
  
  public java.lang.String[] getMovsByDay(java.lang.String arg0, java.lang.String arg1) throws java.rmi.RemoteException, mx.com.invex.bmovil.ws.InvexWSException{
    if (serviciosBancaMovilImplDelegate == null)
      _initServiciosBancaMovilImplDelegateProxy();
    return serviciosBancaMovilImplDelegate.getMovsByDay(arg0, arg1);
  }
  
  public java.lang.String[] obtenerMovsAntesCorte(java.lang.String arg0) throws java.rmi.RemoteException, mx.com.invex.bmovil.ws.InvexWSException{
    if (serviciosBancaMovilImplDelegate == null)
      _initServiciosBancaMovilImplDelegateProxy();
    return serviciosBancaMovilImplDelegate.obtenerMovsAntesCorte(arg0);
  }
  
  public java.lang.String[] obtenerMovsDespuesCorte(java.lang.String arg0) throws java.rmi.RemoteException, mx.com.invex.bmovil.ws.InvexWSException{
    if (serviciosBancaMovilImplDelegate == null)
      _initServiciosBancaMovilImplDelegateProxy();
    return serviciosBancaMovilImplDelegate.obtenerMovsDespuesCorte(arg0);
  }
  
  public java.lang.String[] getProductosByWebUsr(java.lang.String arg0) throws java.rmi.RemoteException, mx.com.invex.bmovil.ws.InvexWSException{
    if (serviciosBancaMovilImplDelegate == null)
      _initServiciosBancaMovilImplDelegateProxy();
    return serviciosBancaMovilImplDelegate.getProductosByWebUsr(arg0);
  }
  
  public java.lang.String getNumLuciByWebUsr(java.lang.String arg0) throws java.rmi.RemoteException, mx.com.invex.bmovil.ws.InvexWSException{
    if (serviciosBancaMovilImplDelegate == null)
      _initServiciosBancaMovilImplDelegateProxy();
    return serviciosBancaMovilImplDelegate.getNumLuciByWebUsr(arg0);
  }
  
  public java.lang.String getCuentaCBA(java.lang.String arg0) throws java.rmi.RemoteException, mx.com.invex.bmovil.ws.InvexWSException{
    if (serviciosBancaMovilImplDelegate == null)
      _initServiciosBancaMovilImplDelegateProxy();
    return serviciosBancaMovilImplDelegate.getCuentaCBA(arg0);
  }
  
  public java.lang.String getPCA(java.lang.String arg0) throws java.rmi.RemoteException, mx.com.invex.bmovil.ws.InvexWSException{
    if (serviciosBancaMovilImplDelegate == null)
      _initServiciosBancaMovilImplDelegateProxy();
    return serviciosBancaMovilImplDelegate.getPCA(arg0);
  }
  
  
}