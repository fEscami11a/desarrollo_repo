/**
 * ServiciosPortalImplDelegate.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package mx.com.invex.bmovil.ws;

public interface ServiciosPortalImplDelegate extends java.rmi.Remote {
    public java.lang.String getSiguienteDiaHabil(java.lang.String arg0) throws java.rmi.RemoteException, mx.com.invex.bmovil.ws.InvexWSException;
    public java.lang.String[] obtenerMovsDosCortesAnteriores(java.lang.String arg0) throws java.rmi.RemoteException, mx.com.invex.bmovil.ws.InvexWSException;
    public java.lang.String[] obtenerMovsAntesCorte(java.lang.String arg0) throws java.rmi.RemoteException, mx.com.invex.bmovil.ws.InvexWSException;
    public java.lang.String[] obtenerMovsDespuesCorte(java.lang.String arg0) throws java.rmi.RemoteException, mx.com.invex.bmovil.ws.InvexWSException;
    public java.lang.String[] getProductosByWebUsr(java.lang.String arg0) throws java.rmi.RemoteException, mx.com.invex.bmovil.ws.InvexWSException;
    public java.lang.String[] webMovEdoCtaFech(java.lang.String arg0, java.lang.String arg1, java.lang.String arg2) throws java.rmi.RemoteException, mx.com.invex.bmovil.ws.InvexWSException;
}
