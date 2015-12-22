/**
 * ServiciosBancaMovilImplServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package mx.com.invex.bmovil.ws;

public class ServiciosBancaMovilImplServiceLocator extends org.apache.axis.client.Service implements mx.com.invex.bmovil.ws.ServiciosBancaMovilImplService {

    public ServiciosBancaMovilImplServiceLocator() {
    }


    public ServiciosBancaMovilImplServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ServiciosBancaMovilImplServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ServiciosBancaMovilImplPort
    private java.lang.String ServiciosBancaMovilImplPort_address = "http://localhost:9080/BancaMovilServices/ServiciosBancaMovilImplService";

    public java.lang.String getServiciosBancaMovilImplPortAddress() {
        return ServiciosBancaMovilImplPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ServiciosBancaMovilImplPortWSDDServiceName = "ServiciosBancaMovilImplPort";

    public java.lang.String getServiciosBancaMovilImplPortWSDDServiceName() {
        return ServiciosBancaMovilImplPortWSDDServiceName;
    }

    public void setServiciosBancaMovilImplPortWSDDServiceName(java.lang.String name) {
        ServiciosBancaMovilImplPortWSDDServiceName = name;
    }

    public mx.com.invex.bmovil.ws.ServiciosBancaMovilImplDelegate getServiciosBancaMovilImplPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ServiciosBancaMovilImplPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getServiciosBancaMovilImplPort(endpoint);
    }

    public mx.com.invex.bmovil.ws.ServiciosBancaMovilImplDelegate getServiciosBancaMovilImplPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            mx.com.invex.bmovil.ws.ServiciosBancaMovilImplPortBindingStub _stub = new mx.com.invex.bmovil.ws.ServiciosBancaMovilImplPortBindingStub(portAddress, this);
            _stub.setPortName(getServiciosBancaMovilImplPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setServiciosBancaMovilImplPortEndpointAddress(java.lang.String address) {
        ServiciosBancaMovilImplPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (mx.com.invex.bmovil.ws.ServiciosBancaMovilImplDelegate.class.isAssignableFrom(serviceEndpointInterface)) {
                mx.com.invex.bmovil.ws.ServiciosBancaMovilImplPortBindingStub _stub = new mx.com.invex.bmovil.ws.ServiciosBancaMovilImplPortBindingStub(new java.net.URL(ServiciosBancaMovilImplPort_address), this);
                _stub.setPortName(getServiciosBancaMovilImplPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("ServiciosBancaMovilImplPort".equals(inputPortName)) {
            return getServiciosBancaMovilImplPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://ws.bmovil.invex.com.mx/", "ServiciosBancaMovilImplService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://ws.bmovil.invex.com.mx/", "ServiciosBancaMovilImplPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ServiciosBancaMovilImplPort".equals(portName)) {
            setServiciosBancaMovilImplPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
