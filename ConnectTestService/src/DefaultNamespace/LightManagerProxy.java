package DefaultNamespace;

public class LightManagerProxy implements DefaultNamespace.LightManager {
  private String _endpoint = null;
  private DefaultNamespace.LightManager lightManager = null;
  
  public LightManagerProxy() {
    _initLightManagerProxy();
  }
  
  public LightManagerProxy(String endpoint) {
    _endpoint = endpoint;
    _initLightManagerProxy();
  }
  
  private void _initLightManagerProxy() {
    try {
      lightManager = (new DefaultNamespace.LightManagerServiceLocator()).getLightManager();
      if (lightManager != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)lightManager)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)lightManager)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (lightManager != null)
      ((javax.xml.rpc.Stub)lightManager)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public DefaultNamespace.LightManager getLightManager() {
    if (lightManager == null)
      _initLightManagerProxy();
    return lightManager;
  }
  
  public void changeLight1() throws java.rmi.RemoteException{
    if (lightManager == null)
      _initLightManagerProxy();
    lightManager.changeLight1();
  }
  
  public void changeLight2() throws java.rmi.RemoteException{
    if (lightManager == null)
      _initLightManagerProxy();
    lightManager.changeLight2();
  }
  
  
}