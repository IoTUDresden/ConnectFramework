/**
 * LightManagerServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package DefaultNamespace;

public class LightManagerServiceLocator extends org.apache.axis.client.Service implements DefaultNamespace.LightManagerService {

    public LightManagerServiceLocator() {
    }


    public LightManagerServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public LightManagerServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for LightManager
    private java.lang.String LightManager_address = "http://localhost:8080/TestWebLight/services/LightManager";

    public java.lang.String getLightManagerAddress() {
        return LightManager_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String LightManagerWSDDServiceName = "LightManager";

    public java.lang.String getLightManagerWSDDServiceName() {
        return LightManagerWSDDServiceName;
    }

    public void setLightManagerWSDDServiceName(java.lang.String name) {
        LightManagerWSDDServiceName = name;
    }

    public DefaultNamespace.LightManager getLightManager() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(LightManager_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getLightManager(endpoint);
    }

    public DefaultNamespace.LightManager getLightManager(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            DefaultNamespace.LightManagerSoapBindingStub _stub = new DefaultNamespace.LightManagerSoapBindingStub(portAddress, this);
            _stub.setPortName(getLightManagerWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setLightManagerEndpointAddress(java.lang.String address) {
        LightManager_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (DefaultNamespace.LightManager.class.isAssignableFrom(serviceEndpointInterface)) {
                DefaultNamespace.LightManagerSoapBindingStub _stub = new DefaultNamespace.LightManagerSoapBindingStub(new java.net.URL(LightManager_address), this);
                _stub.setPortName(getLightManagerWSDDServiceName());
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
        if ("LightManager".equals(inputPortName)) {
            return getLightManager();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://DefaultNamespace", "LightManagerService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://DefaultNamespace", "LightManager"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("LightManager".equals(portName)) {
            setLightManagerEndpointAddress(address);
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
