/*     */ package WEB-INF.classes.org.tempuri;
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
/*     */ import org.tempuri.ArchimedesProjectLocator;
/*     */ import org.tempuri.BasicAPStub;
/*     */ 
/*     */ public class ArchimedesProjectLocator extends Service implements ArchimedesProject {
/*  17 */   public ArchimedesProjectLocator(EngineConfiguration config) { super(config); }
/*     */   
/*     */   public ArchimedesProjectLocator() {}
/*     */   
/*  21 */   public ArchimedesProjectLocator(String wsdlLoc, QName sName) throws ServiceException { super(wsdlLoc, sName); }
/*     */ 
/*     */ 
/*     */   
/*  25 */   private String basicAP_address = "http://alps.global.umusic.net/MilestoneWCF_ProjSrch/ArchimedesProject.svc/basic";
/*     */ 
/*     */   
/*  28 */   public String getbasicAPAddress() { return this.basicAP_address; }
/*     */ 
/*     */ 
/*     */   
/*  32 */   private String basicAPWSDDServiceName = "basicAP";
/*     */ 
/*     */   
/*  35 */   public String getbasicAPWSDDServiceName() { return this.basicAPWSDDServiceName; }
/*     */ 
/*     */ 
/*     */   
/*  39 */   public void setbasicAPWSDDServiceName(String name) { this.basicAPWSDDServiceName = name; }
/*     */ 
/*     */   
/*     */   public IArchimedesProject getbasicAP() throws ServiceException {
/*     */     URL endpoint;
/*     */     try {
/*  45 */       endpoint = new URL(this.basicAP_address);
/*     */     }
/*  47 */     catch (MalformedURLException e) {
/*  48 */       throw new ServiceException(e);
/*     */     } 
/*  50 */     return getbasicAP(endpoint);
/*     */   }
/*     */   
/*     */   public IArchimedesProject getbasicAP(URL portAddress) throws ServiceException {
/*     */     try {
/*  55 */       BasicAPStub _stub = new BasicAPStub(portAddress, this);
/*  56 */       _stub.setPortName(getbasicAPWSDDServiceName());
/*  57 */       return _stub;
/*     */     }
/*  59 */     catch (AxisFault e) {
/*  60 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*  65 */   public void setbasicAPEndpointAddress(String address) { this.basicAP_address = address; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Remote getPort(Class serviceEndpointInterface) throws ServiceException {
/*     */     try {
/*  75 */       if (IArchimedesProject.class.isAssignableFrom(serviceEndpointInterface)) {
/*  76 */         BasicAPStub _stub = new BasicAPStub(new URL(this.basicAP_address), this);
/*  77 */         _stub.setPortName(getbasicAPWSDDServiceName());
/*  78 */         return _stub;
/*     */       }
/*     */     
/*  81 */     } catch (Throwable t) {
/*  82 */       throw new ServiceException(t);
/*     */     } 
/*  84 */     throw new ServiceException("There is no stub implementation for the interface:  " + ((serviceEndpointInterface == null) ? "null" : serviceEndpointInterface.getName()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Remote getPort(QName portName, Class serviceEndpointInterface) throws ServiceException {
/*  93 */     if (portName == null) {
/*  94 */       return getPort(serviceEndpointInterface);
/*     */     }
/*  96 */     String inputPortName = portName.getLocalPart();
/*  97 */     if ("basicAP".equals(inputPortName)) {
/*  98 */       return getbasicAP();
/*     */     }
/*     */     
/* 101 */     Remote _stub = getPort(serviceEndpointInterface);
/* 102 */     ((Stub)_stub).setPortName(portName);
/* 103 */     return _stub;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 108 */   public QName getServiceName() { return new QName("http://tempuri.org/", "ArchimedesProject"); }
/*     */ 
/*     */   
/* 111 */   private HashSet ports = null;
/*     */   
/*     */   public Iterator getPorts() {
/* 114 */     if (this.ports == null) {
/* 115 */       this.ports = new HashSet();
/* 116 */       this.ports.add(new QName("http://tempuri.org/", "basicAP"));
/*     */     } 
/* 118 */     return this.ports.iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEndpointAddress(String portName, String address) throws ServiceException {
/* 126 */     if ("basicAP".equals(portName)) {
/* 127 */       setbasicAPEndpointAddress(address);
/*     */     }
/*     */     else {
/*     */       
/* 131 */       throw new ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 139 */   public void setEndpointAddress(QName portName, String address) throws ServiceException { setEndpointAddress(portName.getLocalPart(), address); }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\org\tempuri\ArchimedesProjectLocator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */