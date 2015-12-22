package mx.com.invex.ws;

public class CallcenterWServiceProxy implements mx.com.invex.ws.CallcenterWService {
  private String _endpoint = null;
  private mx.com.invex.ws.CallcenterWService callcenterWService = null;
  
  public CallcenterWServiceProxy() {
    _initCallcenterWServiceProxy();
  }
  
  public CallcenterWServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initCallcenterWServiceProxy();
  }
  
  private void _initCallcenterWServiceProxy() {
    try {
      callcenterWService = (new mx.com.invex.ws.CallcenterWServiceServiceLocator()).getCallcenterWServicePort();
      if (callcenterWService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)callcenterWService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)callcenterWService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (callcenterWService != null)
      ((javax.xml.rpc.Stub)callcenterWService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public mx.com.invex.ws.CallcenterWService getCallcenterWService() {
    if (callcenterWService == null)
      _initCallcenterWServiceProxy();
    return callcenterWService;
  }
  
  public mx.com.invex.ws.CuentaAD[] getCuentasAD(java.lang.String account) throws java.rmi.RemoteException, mx.com.invex.ws.InvexServiceException{
    if (callcenterWService == null)
      _initCallcenterWServiceProxy();
    return callcenterWService.getCuentasAD(account);
  }
  
  
}