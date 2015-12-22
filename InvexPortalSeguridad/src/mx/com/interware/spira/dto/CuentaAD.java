/**
 * CuentaAD.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package mx.com.interware.spira.dto;

public class CuentaAD  implements java.io.Serializable {
    private java.lang.String clasificacion;

    private java.lang.String nombreCompleto;

    private java.lang.String numCuenta;

    private java.lang.String tipoCuenta;

    private java.lang.String ucode3;

    public CuentaAD() {
    }

    public CuentaAD(
           java.lang.String clasificacion,
           java.lang.String nombreCompleto,
           java.lang.String numCuenta,
           java.lang.String tipoCuenta,
           java.lang.String ucode3) {
           this.clasificacion = clasificacion;
           this.nombreCompleto = nombreCompleto;
           this.numCuenta = numCuenta;
           this.tipoCuenta = tipoCuenta;
           this.ucode3 = ucode3;
    }


    /**
     * Gets the clasificacion value for this CuentaAD.
     * 
     * @return clasificacion
     */
    public java.lang.String getClasificacion() {
        return clasificacion;
    }


    /**
     * Sets the clasificacion value for this CuentaAD.
     * 
     * @param clasificacion
     */
    public void setClasificacion(java.lang.String clasificacion) {
        this.clasificacion = clasificacion;
    }


    /**
     * Gets the nombreCompleto value for this CuentaAD.
     * 
     * @return nombreCompleto
     */
    public java.lang.String getNombreCompleto() {
        return nombreCompleto;
    }


    /**
     * Sets the nombreCompleto value for this CuentaAD.
     * 
     * @param nombreCompleto
     */
    public void setNombreCompleto(java.lang.String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }


    /**
     * Gets the numCuenta value for this CuentaAD.
     * 
     * @return numCuenta
     */
    public java.lang.String getNumCuenta() {
        return numCuenta;
    }


    /**
     * Sets the numCuenta value for this CuentaAD.
     * 
     * @param numCuenta
     */
    public void setNumCuenta(java.lang.String numCuenta) {
        this.numCuenta = numCuenta;
    }


    /**
     * Gets the tipoCuenta value for this CuentaAD.
     * 
     * @return tipoCuenta
     */
    public java.lang.String getTipoCuenta() {
        return tipoCuenta;
    }


    /**
     * Sets the tipoCuenta value for this CuentaAD.
     * 
     * @param tipoCuenta
     */
    public void setTipoCuenta(java.lang.String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }


    /**
     * Gets the ucode3 value for this CuentaAD.
     * 
     * @return ucode3
     */
    public java.lang.String getUcode3() {
        return ucode3;
    }


    /**
     * Sets the ucode3 value for this CuentaAD.
     * 
     * @param ucode3
     */
    public void setUcode3(java.lang.String ucode3) {
        this.ucode3 = ucode3;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CuentaAD)) return false;
        CuentaAD other = (CuentaAD) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.clasificacion==null && other.getClasificacion()==null) || 
             (this.clasificacion!=null &&
              this.clasificacion.equals(other.getClasificacion()))) &&
            ((this.nombreCompleto==null && other.getNombreCompleto()==null) || 
             (this.nombreCompleto!=null &&
              this.nombreCompleto.equals(other.getNombreCompleto()))) &&
            ((this.numCuenta==null && other.getNumCuenta()==null) || 
             (this.numCuenta!=null &&
              this.numCuenta.equals(other.getNumCuenta()))) &&
            ((this.tipoCuenta==null && other.getTipoCuenta()==null) || 
             (this.tipoCuenta!=null &&
              this.tipoCuenta.equals(other.getTipoCuenta()))) &&
            ((this.ucode3==null && other.getUcode3()==null) || 
             (this.ucode3!=null &&
              this.ucode3.equals(other.getUcode3())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getClasificacion() != null) {
            _hashCode += getClasificacion().hashCode();
        }
        if (getNombreCompleto() != null) {
            _hashCode += getNombreCompleto().hashCode();
        }
        if (getNumCuenta() != null) {
            _hashCode += getNumCuenta().hashCode();
        }
        if (getTipoCuenta() != null) {
            _hashCode += getTipoCuenta().hashCode();
        }
        if (getUcode3() != null) {
            _hashCode += getUcode3().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CuentaAD.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://dto.spira.interware.com.mx", "CuentaAD"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clasificacion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.spira.interware.com.mx", "clasificacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nombreCompleto");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.spira.interware.com.mx", "nombreCompleto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numCuenta");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.spira.interware.com.mx", "numCuenta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoCuenta");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.spira.interware.com.mx", "tipoCuenta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ucode3");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.spira.interware.com.mx", "ucode3"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
