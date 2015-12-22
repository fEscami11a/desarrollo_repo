/**
 * ServiciosBancaMovilImplDelegate.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package mx.com.invex.bmovil.ws;

public interface ServiciosBancaMovilImplDelegate extends java.rmi.Remote {
    public java.lang.String[] getMovsByDay(java.lang.String arg0, java.lang.String arg1) throws java.rmi.RemoteException, mx.com.invex.bmovil.ws.InvexWSException;
    public java.lang.String[] obtenerMovsAntesCorte(java.lang.String arg0) throws java.rmi.RemoteException, mx.com.invex.bmovil.ws.InvexWSException;
    public java.lang.String[] obtenerMovsDespuesCorte(java.lang.String arg0) throws java.rmi.RemoteException, mx.com.invex.bmovil.ws.InvexWSException;
    public java.lang.String[] getProductosByWebUsr(java.lang.String arg0) throws java.rmi.RemoteException, mx.com.invex.bmovil.ws.InvexWSException;
    public java.lang.String getNumLuciByWebUsr(java.lang.String arg0) throws java.rmi.RemoteException, mx.com.invex.bmovil.ws.InvexWSException;
    public java.lang.String getCuentaCBA(java.lang.String arg0) throws java.rmi.RemoteException, mx.com.invex.bmovil.ws.InvexWSException;
    public java.lang.String getPCA(java.lang.String arg0) throws java.rmi.RemoteException, mx.com.invex.bmovil.ws.InvexWSException;
}
