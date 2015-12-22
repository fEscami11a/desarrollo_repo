/**
 * InvexServiceException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package mx.com.invex.ws;

public class InvexServiceException extends org.apache.axis.AxisFault {
    public java.lang.String invexServiceException;
    public java.lang.String getInvexServiceException() {
        return this.invexServiceException;
    }

    public InvexServiceException() {
    }

    public InvexServiceException(java.lang.Exception target) {
        super(target);
    }

    public InvexServiceException(java.lang.String message, java.lang.Throwable t) {
        super(message, t);
    }

      public InvexServiceException(java.lang.String invexServiceException) {
        this.invexServiceException = invexServiceException;
    }

    /**
     * Writes the exception data to the faultDetails
     */
    public void writeDetails(javax.xml.namespace.QName qname, org.apache.axis.encoding.SerializationContext context) throws java.io.IOException {
        context.serialize(qname, null, invexServiceException);
    }
}
