/**
 * CallcenterWServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package mx.com.invex.ws;

public class CallcenterWServiceServiceLocator extends org.apache.axis.client.Service implements mx.com.invex.ws.CallcenterWServiceService {

    public CallcenterWServiceServiceLocator() {
    }


    public CallcenterWServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public CallcenterWServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for CallcenterWServicePort
    private java.lang.String CallcenterWServicePort_address = "http://172.16.18.52:8080/TsysWebFile/CallcenterWService";

    public java.lang.String getCallcenterWServicePortAddress() {
        return CallcenterWServicePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String CallcenterWServicePortWSDDServiceName = "CallcenterWServicePort";

    public java.lang.String getCallcenterWServicePortWSDDServiceName() {
        return CallcenterWServicePortWSDDServiceName;
    }

    public void setCallcenterWServicePortWSDDServiceName(java.lang.String name) {
        CallcenterWServicePortWSDDServiceName = name;
    }

    public mx.com.invex.ws.CallcenterWService getCallcenterWServicePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(CallcenterWServicePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getCallcenterWServicePort(endpoint);
    }

    public mx.com.invex.ws.CallcenterWService getCallcenterWServicePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            mx.com.invex.ws.CallcenterWServiceServiceSoapBindingStub _stub = new mx.com.invex.ws.CallcenterWServiceServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getCallcenterWServicePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setCallcenterWServicePortEndpointAddress(java.lang.String address) {
        CallcenterWServicePort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (mx.com.invex.ws.CallcenterWService.class.isAssignableFrom(serviceEndpointInterface)) {
                mx.com.invex.ws.CallcenterWServiceServiceSoapBindingStub _stub = new mx.com.invex.ws.CallcenterWServiceServiceSoapBindingStub(new java.net.URL(CallcenterWServicePort_address), this);
                _stub.setPortName(getCallcenterWServicePortWSDDServiceName());
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
        if ("CallcenterWServicePort".equals(inputPortName)) {
            return getCallcenterWServicePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://ws.invex.com.mx/", "CallcenterWServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://ws.invex.com.mx/", "CallcenterWServicePort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("CallcenterWServicePort".equals(portName)) {
            setCallcenterWServicePortEndpointAddress(address);
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
