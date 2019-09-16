package WEB-INF.classes.org.tempuri;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Remote;
import java.util.HashSet;
import java.util.Iterator;
import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;
import net.umusic.milestone.alps.IArchimedesProject;
import org.apache.axis.AxisFault;
import org.apache.axis.EngineConfiguration;
import org.apache.axis.client.Service;
import org.apache.axis.client.Stub;
import org.tempuri.ArchimedesProject;
import org.tempuri.ArchimedesProjectLocator;
import org.tempuri.BasicAPStub;

public class ArchimedesProjectLocator extends Service implements ArchimedesProject {
  public ArchimedesProjectLocator() {}
  
  public ArchimedesProjectLocator(EngineConfiguration config) { super(config); }
  
  public ArchimedesProjectLocator(String wsdlLoc, QName sName) throws ServiceException { super(wsdlLoc, sName); }
  
  private String basicAP_address = "http://alps.global.umusic.net/MilestoneWCF_ProjSrch/ArchimedesProject.svc/basic";
  
  public String getbasicAPAddress() { return this.basicAP_address; }
  
  private String basicAPWSDDServiceName = "basicAP";
  
  public String getbasicAPWSDDServiceName() { return this.basicAPWSDDServiceName; }
  
  public void setbasicAPWSDDServiceName(String name) { this.basicAPWSDDServiceName = name; }
  
  public IArchimedesProject getbasicAP() throws ServiceException {
    URL endpoint;
    try {
      endpoint = new URL(this.basicAP_address);
    } catch (MalformedURLException e) {
      throw new ServiceException(e);
    } 
    return getbasicAP(endpoint);
  }
  
  public IArchimedesProject getbasicAP(URL portAddress) throws ServiceException {
    try {
      BasicAPStub _stub = new BasicAPStub(portAddress, this);
      _stub.setPortName(getbasicAPWSDDServiceName());
      return _stub;
    } catch (AxisFault e) {
      return null;
    } 
  }
  
  public void setbasicAPEndpointAddress(String address) { this.basicAP_address = address; }
  
  public Remote getPort(Class serviceEndpointInterface) throws ServiceException {
    try {
      if (IArchimedesProject.class.isAssignableFrom(serviceEndpointInterface)) {
        BasicAPStub _stub = new BasicAPStub(new URL(this.basicAP_address), this);
        _stub.setPortName(getbasicAPWSDDServiceName());
        return _stub;
      } 
    } catch (Throwable t) {
      throw new ServiceException(t);
    } 
    throw new ServiceException("There is no stub implementation for the interface:  " + ((serviceEndpointInterface == null) ? "null" : serviceEndpointInterface.getName()));
  }
  
  public Remote getPort(QName portName, Class serviceEndpointInterface) throws ServiceException {
    if (portName == null)
      return getPort(serviceEndpointInterface); 
    String inputPortName = portName.getLocalPart();
    if ("basicAP".equals(inputPortName))
      return getbasicAP(); 
    Remote _stub = getPort(serviceEndpointInterface);
    ((Stub)_stub).setPortName(portName);
    return _stub;
  }
  
  public QName getServiceName() { return new QName("http://tempuri.org/", "ArchimedesProject"); }
  
  private HashSet ports = null;
  
  public Iterator getPorts() {
    if (this.ports == null) {
      this.ports = new HashSet();
      this.ports.add(new QName("http://tempuri.org/", "basicAP"));
    } 
    return this.ports.iterator();
  }
  
  public void setEndpointAddress(String portName, String address) throws ServiceException {
    if ("basicAP".equals(portName)) {
      setbasicAPEndpointAddress(address);
    } else {
      throw new ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
    } 
  }
  
  public void setEndpointAddress(QName portName, String address) throws ServiceException { setEndpointAddress(portName.getLocalPart(), address); }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\org\tempuri\ArchimedesProjectLocator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */