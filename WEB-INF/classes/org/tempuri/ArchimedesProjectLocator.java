/*     */ package WEB-INF.classes.org.tempuri;
/*     */ import com.universal.milestone.projectSearchSvcClient;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.rmi.Remote;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.rpc.ServiceException;
/*     */ import net.umusic.milestone.alps.IArchimedesProject;
/*     */ import org.apache.axis.AxisFault;
/*     */ import org.apache.axis.EngineConfiguration;
/*     */ import org.apache.axis.client.Service;
/*     */ import org.apache.axis.client.Stub;
/*     */ import org.tempuri.ArchimedesProjectLocator;
/*     */ import org.tempuri.BasicAPStub;
/*     */ 
/*     */ public class ArchimedesProjectLocator extends Service implements ArchimedesProject {
/*  19 */   public ArchimedesProjectLocator(EngineConfiguration config) { super(config); }
/*     */   
/*     */   public ArchimedesProjectLocator() {}
/*     */   
/*  23 */   public ArchimedesProjectLocator(String wsdlLoc, QName sName) throws ServiceException { super(wsdlLoc, sName); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  28 */   private String basicAP_address = projectSearchSvcClient.ProjSearchPropertStrGet("BASICAP_ADDRESS");
/*     */ 
/*     */   
/*  31 */   public String getbasicAPAddress() { return this.basicAP_address; }
/*     */ 
/*     */ 
/*     */   
/*  35 */   private String basicAPWSDDServiceName = "basicAP";
/*     */ 
/*     */   
/*  38 */   public String getbasicAPWSDDServiceName() { return this.basicAPWSDDServiceName; }
/*     */ 
/*     */ 
/*     */   
/*  42 */   public void setbasicAPWSDDServiceName(String name) { this.basicAPWSDDServiceName = name; }
/*     */ 
/*     */   
/*     */   public IArchimedesProject getbasicAP() throws ServiceException {
/*     */     URL endpoint;
/*     */     try {
/*  48 */       endpoint = new URL(this.basicAP_address);
/*     */     }
/*  50 */     catch (MalformedURLException e) {
/*  51 */       throw new ServiceException(e);
/*     */     } 
/*  53 */     return getbasicAP(endpoint);
/*     */   }
/*     */   
/*     */   public IArchimedesProject getbasicAP(URL portAddress) throws ServiceException {
/*     */     try {
/*  58 */       BasicAPStub _stub = new BasicAPStub(portAddress, this);
/*  59 */       _stub.setPortName(getbasicAPWSDDServiceName());
/*  60 */       return _stub;
/*     */     }
/*  62 */     catch (AxisFault e) {
/*  63 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*  68 */   public void setbasicAPEndpointAddress(String address) { this.basicAP_address = address; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Remote getPort(Class serviceEndpointInterface) throws ServiceException {
/*     */     try {
/*  78 */       if (IArchimedesProject.class.isAssignableFrom(serviceEndpointInterface)) {
/*  79 */         BasicAPStub _stub = new BasicAPStub(new URL(this.basicAP_address), this);
/*  80 */         _stub.setPortName(getbasicAPWSDDServiceName());
/*  81 */         return _stub;
/*     */       }
/*     */     
/*  84 */     } catch (Throwable t) {
/*  85 */       throw new ServiceException(t);
/*     */     } 
/*  87 */     throw new ServiceException("There is no stub implementation for the interface:  " + ((serviceEndpointInterface == null) ? "null" : serviceEndpointInterface.getName()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Remote getPort(QName portName, Class serviceEndpointInterface) throws ServiceException {
/*  96 */     if (portName == null) {
/*  97 */       return getPort(serviceEndpointInterface);
/*     */     }
/*  99 */     String inputPortName = portName.getLocalPart();
/* 100 */     if ("basicAP".equals(inputPortName)) {
/* 101 */       return getbasicAP();
/*     */     }
/*     */     
/* 104 */     Remote _stub = getPort(serviceEndpointInterface);
/* 105 */     ((Stub)_stub).setPortName(portName);
/* 106 */     return _stub;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 111 */   public QName getServiceName() { return new QName("http://tempuri.org/", "ArchimedesProject"); }
/*     */ 
/*     */   
/* 114 */   private HashSet ports = null;
/*     */   
/*     */   public Iterator getPorts() {
/* 117 */     if (this.ports == null) {
/* 118 */       this.ports = new HashSet();
/* 119 */       this.ports.add(new QName("http://tempuri.org/", "basicAP"));
/*     */     } 
/* 121 */     return this.ports.iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEndpointAddress(String portName, String address) throws ServiceException {
/* 129 */     if ("basicAP".equals(portName)) {
/* 130 */       setbasicAPEndpointAddress(address);
/*     */     }
/*     */     else {
/*     */       
/* 134 */       throw new ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 142 */   public void setEndpointAddress(QName portName, String address) throws ServiceException { setEndpointAddress(portName.getLocalPart(), address); }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\org\tempuri\ArchimedesProjectLocator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */