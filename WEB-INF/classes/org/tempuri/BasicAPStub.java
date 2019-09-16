package WEB-INF.classes.org.tempuri;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.Vector;
import javax.xml.namespace.QName;
import javax.xml.rpc.Service;
import net.umusic.milestone.alps.DcDistributionCoData;
import net.umusic.milestone.alps.DcGDRSResults;
import net.umusic.milestone.alps.DcLabelData;
import net.umusic.milestone.alps.DcMilestoneProjectSearchFilter;
import net.umusic.milestone.alps.DcProjectSearchFilter;
import net.umusic.milestone.alps.DcProjectSearchResults;
import net.umusic.milestone.alps.DsLabelStructureInterface;
import net.umusic.milestone.alps.DsProjectStructureInterface;
import net.umusic.milestone.alps.IArchimedesProject;
import net.umusic.milestone.alps.ProjectNumberValidResults;
import org.apache.axis.AxisFault;
import org.apache.axis.NoEndPointException;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.client.Stub;
import org.apache.axis.constants.Style;
import org.apache.axis.constants.Use;
import org.apache.axis.description.OperationDesc;
import org.apache.axis.description.ParameterDesc;
import org.apache.axis.encoding.DeserializerFactory;
import org.apache.axis.encoding.SerializerFactory;
import org.apache.axis.encoding.ser.ArrayDeserializerFactory;
import org.apache.axis.encoding.ser.ArraySerializerFactory;
import org.apache.axis.soap.SOAPConstants;
import org.apache.axis.utils.JavaUtils;
import org.tempuri.BasicAPStub;

public class BasicAPStub extends Stub implements IArchimedesProject {
  private Vector cachedSerClasses = new Vector();
  
  private Vector cachedSerQNames = new Vector();
  
  private Vector cachedSerFactories = new Vector();
  
  private Vector cachedDeserFactories = new Vector();
  
  static OperationDesc[] _operations = new OperationDesc[12];
  
  static  {
    _initOperationDesc1();
    _initOperationDesc2();
  }
  
  private static void _initOperationDesc1() {
    oper = new OperationDesc();
    oper.setName("ProjectSearchGetDataContract");
    ParameterDesc param = new ParameterDesc(new QName("http://alps.milestone.umusic.net/", "searchParam"), (byte)1, new QName("http://alps.milestone.umusic.net/", "dcProjectSearchFilter"), DcProjectSearchFilter.class, false, false);
    param.setOmittable(true);
    param.setNillable(true);
    oper.addParameter(param);
    oper.setReturnType(new QName("http://alps.milestone.umusic.net/", "ArrayOfdcProjectSearchResults"));
    oper.setReturnClass(DcProjectSearchResults[].class);
    oper.setReturnQName(new QName("http://alps.milestone.umusic.net/", "ProjectSearchGetDataContractResult"));
    param = oper.getReturnParamDesc();
    param.setItemQName(new QName("http://alps.milestone.umusic.net/", "dcProjectSearchResults"));
    oper.setStyle(Style.WRAPPED);
    oper.setUse(Use.LITERAL);
    _operations[0] = oper;
    oper = new OperationDesc();
    oper.setName("ProjectSearchUMVDGetDataContract");
    param = new ParameterDesc(new QName("http://alps.milestone.umusic.net/", "searchParam"), (byte)1, new QName("http://alps.milestone.umusic.net/", "dcProjectSearchFilter"), DcProjectSearchFilter.class, false, false);
    param.setOmittable(true);
    param.setNillable(true);
    oper.addParameter(param);
    oper.setReturnType(new QName("http://alps.milestone.umusic.net/", "ArrayOfdcProjectSearchResults"));
    oper.setReturnClass(DcProjectSearchResults[].class);
    oper.setReturnQName(new QName("http://alps.milestone.umusic.net/", "ProjectSearchUMVDGetDataContractResult"));
    param = oper.getReturnParamDesc();
    param.setItemQName(new QName("http://alps.milestone.umusic.net/", "dcProjectSearchResults"));
    oper.setStyle(Style.WRAPPED);
    oper.setUse(Use.LITERAL);
    _operations[1] = oper;
    oper = new OperationDesc();
    oper.setName("ProjectSearchMilestoneGetDataContract");
    param = new ParameterDesc(new QName("http://alps.milestone.umusic.net/", "searchParam"), (byte)1, new QName("http://alps.milestone.umusic.net/", "dcMilestoneProjectSearchFilter"), DcMilestoneProjectSearchFilter.class, false, false);
    param.setOmittable(true);
    param.setNillable(true);
    oper.addParameter(param);
    oper.setReturnType(new QName("http://alps.milestone.umusic.net/", "ArrayOfdcProjectSearchResults"));
    oper.setReturnClass(DcProjectSearchResults[].class);
    oper.setReturnQName(new QName("http://alps.milestone.umusic.net/", "ProjectSearchMilestoneGetDataContractResult"));
    param = oper.getReturnParamDesc();
    param.setItemQName(new QName("http://alps.milestone.umusic.net/", "dcProjectSearchResults"));
    oper.setStyle(Style.WRAPPED);
    oper.setUse(Use.LITERAL);
    _operations[2] = oper;
    oper = new OperationDesc();
    oper.setName("ProjectNumberValidationGet");
    param = new ParameterDesc(new QName("http://alps.milestone.umusic.net/", "projectNumber"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    param.setNillable(true);
    oper.addParameter(param);
    oper.setReturnType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
    oper.setReturnClass(Boolean.class);
    oper.setReturnQName(new QName("http://alps.milestone.umusic.net/", "ProjectNumberValidationGetResult"));
    oper.setStyle(Style.WRAPPED);
    oper.setUse(Use.LITERAL);
    _operations[3] = oper;
    oper = new OperationDesc();
    oper.setName("LabelDataGetDataContractById");
    param = new ParameterDesc(new QName("http://alps.milestone.umusic.net/", "labelID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "int"), Integer.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    oper.setReturnType(new QName("http://alps.milestone.umusic.net/", "dcLabelData"));
    oper.setReturnClass(DcLabelData.class);
    oper.setReturnQName(new QName("http://alps.milestone.umusic.net/", "LabelDataGetDataContractByIdResult"));
    oper.setStyle(Style.WRAPPED);
    oper.setUse(Use.LITERAL);
    _operations[4] = oper;
    oper = new OperationDesc();
    oper.setName("LabelDataGetDataContract");
    oper.setReturnType(new QName("http://alps.milestone.umusic.net/", "ArrayOfdcLabelData"));
    oper.setReturnClass(DcLabelData[].class);
    oper.setReturnQName(new QName("http://alps.milestone.umusic.net/", "LabelDataGetDataContractResult"));
    param = oper.getReturnParamDesc();
    param.setItemQName(new QName("http://alps.milestone.umusic.net/", "dcLabelData"));
    oper.setStyle(Style.WRAPPED);
    oper.setUse(Use.LITERAL);
    _operations[5] = oper;
    oper = new OperationDesc();
    oper.setName("ProjectGetByID");
    param = new ParameterDesc(new QName("http://alps.milestone.umusic.net/", "ArchieID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "int"), Integer.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    oper.setReturnType(new QName("http://alps.milestone.umusic.net/", "dsProjectStructureInterface"));
    oper.setReturnClass(DsProjectStructureInterface.class);
    oper.setReturnQName(new QName("http://alps.milestone.umusic.net/", "ProjectGetByIDResult"));
    oper.setStyle(Style.WRAPPED);
    oper.setUse(Use.LITERAL);
    _operations[6] = oper;
    oper = new OperationDesc();
    oper.setName("LabelGetByID");
    param = new ParameterDesc(new QName("http://alps.milestone.umusic.net/", "archieID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "int"), Integer.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    oper.setReturnType(new QName("http://alps.milestone.umusic.net/", "dsLabelStructureInterface"));
    oper.setReturnClass(DsLabelStructureInterface.class);
    oper.setReturnQName(new QName("http://alps.milestone.umusic.net/", "LabelGetByIDResult"));
    oper.setStyle(Style.WRAPPED);
    oper.setUse(Use.LITERAL);
    _operations[7] = oper;
    oper = new OperationDesc();
    oper.setName("ProjectSearchValidation");
    param = new ParameterDesc(new QName("http://alps.milestone.umusic.net/", "projectNumber"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    param.setNillable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://alps.milestone.umusic.net/", "IsUMVD"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "boolean"), Boolean.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    oper.setReturnType(new QName("http://alps.milestone.umusic.net/", "ProjectNumberValidResults"));
    oper.setReturnClass(ProjectNumberValidResults.class);
    oper.setReturnQName(new QName("http://alps.milestone.umusic.net/", "ProjectSearchValidationResult"));
    oper.setStyle(Style.WRAPPED);
    oper.setUse(Use.LITERAL);
    _operations[8] = oper;
    oper = new OperationDesc();
    oper.setName("DistributionCoGetDataContract");
    oper.setReturnType(new QName("http://alps.milestone.umusic.net/", "ArrayOfdcDistributionCoData"));
    oper.setReturnClass(DcDistributionCoData[].class);
    oper.setReturnQName(new QName("http://alps.milestone.umusic.net/", "DistributionCoGetDataContractResult"));
    param = oper.getReturnParamDesc();
    param.setItemQName(new QName("http://alps.milestone.umusic.net/", "dcDistributionCoData"));
    oper.setStyle(Style.WRAPPED);
    oper.setUse(Use.LITERAL);
    _operations[9] = oper;
  }
  
  private static void _initOperationDesc2() {
    oper = new OperationDesc();
    oper.setName("MilestoneSnapshotProjectInsert");
    ParameterDesc param = new ParameterDesc(new QName("http://alps.milestone.umusic.net/", "projectID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    param.setNillable(true);
    oper.addParameter(param);
    oper.setReturnType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
    oper.setReturnClass(Boolean.class);
    oper.setReturnQName(new QName("http://alps.milestone.umusic.net/", "MilestoneSnapshotProjectInsertResult"));
    oper.setStyle(Style.WRAPPED);
    oper.setUse(Use.LITERAL);
    _operations[10] = oper;
    oper = new OperationDesc();
    oper.setName("GDRSProductStatusGet");
    param = new ParameterDesc(new QName("http://alps.milestone.umusic.net/", "releaseID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "int"), Integer.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://alps.milestone.umusic.net/", "releaseFamilyID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "int"), Integer.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://alps.milestone.umusic.net/", "environmentID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "int"), Integer.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    oper.setReturnType(new QName("http://alps.milestone.umusic.net/", "dcGDRSResults"));
    oper.setReturnClass(DcGDRSResults.class);
    oper.setReturnQName(new QName("http://alps.milestone.umusic.net/", "GDRSProductStatusGetResult"));
    oper.setStyle(Style.WRAPPED);
    oper.setUse(Use.LITERAL);
    _operations[11] = oper;
  }
  
  public BasicAPStub() { this(null); }
  
  public BasicAPStub(URL endpointURL, Service service) throws AxisFault {
    this(service);
    this.cachedEndpoint = endpointURL;
  }
  
  public BasicAPStub(Service service) throws AxisFault {
    if (service == null) {
      this.service = new Service();
    } else {
      this.service = service;
    } 
    ((Service)this.service).setTypeMappingVersion("1.2");
    Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
    Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
    Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
    Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
    Class arraysf = ArraySerializerFactory.class;
    Class arraydf = ArrayDeserializerFactory.class;
    Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
    Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
    Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
    Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
    QName qName = new QName("http://alps.milestone.umusic.net/", "ArrayOfdcDistributionCoData");
    this.cachedSerQNames.add(qName);
    Class cls = DcDistributionCoData[].class;
    this.cachedSerClasses.add(cls);
    qName = new QName("http://alps.milestone.umusic.net/", "dcDistributionCoData");
    QName qName2 = new QName("http://alps.milestone.umusic.net/", "dcDistributionCoData");
    this.cachedSerFactories.add(new ArraySerializerFactory(qName, qName2));
    this.cachedDeserFactories.add(new ArrayDeserializerFactory());
    qName = new QName("http://alps.milestone.umusic.net/", "ArrayOfdcLabelData");
    this.cachedSerQNames.add(qName);
    cls = DcLabelData[].class;
    this.cachedSerClasses.add(cls);
    qName = new QName("http://alps.milestone.umusic.net/", "dcLabelData");
    qName2 = new QName("http://alps.milestone.umusic.net/", "dcLabelData");
    this.cachedSerFactories.add(new ArraySerializerFactory(qName, qName2));
    this.cachedDeserFactories.add(new ArrayDeserializerFactory());
    qName = new QName("http://alps.milestone.umusic.net/", "ArrayOfdcProjectSearchResults");
    this.cachedSerQNames.add(qName);
    cls = DcProjectSearchResults[].class;
    this.cachedSerClasses.add(cls);
    qName = new QName("http://alps.milestone.umusic.net/", "dcProjectSearchResults");
    qName2 = new QName("http://alps.milestone.umusic.net/", "dcProjectSearchResults");
    this.cachedSerFactories.add(new ArraySerializerFactory(qName, qName2));
    this.cachedDeserFactories.add(new ArrayDeserializerFactory());
    qName = new QName("http://alps.milestone.umusic.net/", "dcDistributionCoData");
    this.cachedSerQNames.add(qName);
    cls = DcDistributionCoData.class;
    this.cachedSerClasses.add(cls);
    this.cachedSerFactories.add(beansf);
    this.cachedDeserFactories.add(beandf);
    qName = new QName("http://alps.milestone.umusic.net/", "dcGDRSResults");
    this.cachedSerQNames.add(qName);
    cls = DcGDRSResults.class;
    this.cachedSerClasses.add(cls);
    this.cachedSerFactories.add(beansf);
    this.cachedDeserFactories.add(beandf);
    qName = new QName("http://alps.milestone.umusic.net/", "dcLabelData");
    this.cachedSerQNames.add(qName);
    cls = DcLabelData.class;
    this.cachedSerClasses.add(cls);
    this.cachedSerFactories.add(beansf);
    this.cachedDeserFactories.add(beandf);
    qName = new QName("http://alps.milestone.umusic.net/", "dcMilestoneProjectSearchFilter");
    this.cachedSerQNames.add(qName);
    cls = DcMilestoneProjectSearchFilter.class;
    this.cachedSerClasses.add(cls);
    this.cachedSerFactories.add(beansf);
    this.cachedDeserFactories.add(beandf);
    qName = new QName("http://alps.milestone.umusic.net/", "dcProjectSearchFilter");
    this.cachedSerQNames.add(qName);
    cls = DcProjectSearchFilter.class;
    this.cachedSerClasses.add(cls);
    this.cachedSerFactories.add(beansf);
    this.cachedDeserFactories.add(beandf);
    qName = new QName("http://alps.milestone.umusic.net/", "dcProjectSearchResults");
    this.cachedSerQNames.add(qName);
    cls = DcProjectSearchResults.class;
    this.cachedSerClasses.add(cls);
    this.cachedSerFactories.add(beansf);
    this.cachedDeserFactories.add(beandf);
    qName = new QName("http://alps.milestone.umusic.net/", "dsLabelStructureInterface");
    this.cachedSerQNames.add(qName);
    cls = DsLabelStructureInterface.class;
    this.cachedSerClasses.add(cls);
    this.cachedSerFactories.add(beansf);
    this.cachedDeserFactories.add(beandf);
    qName = new QName("http://alps.milestone.umusic.net/", "dsProjectStructureInterface");
    this.cachedSerQNames.add(qName);
    cls = DsProjectStructureInterface.class;
    this.cachedSerClasses.add(cls);
    this.cachedSerFactories.add(beansf);
    this.cachedDeserFactories.add(beandf);
    qName = new QName("http://alps.milestone.umusic.net/", "ProjectNumberValidResults");
    this.cachedSerQNames.add(qName);
    cls = ProjectNumberValidResults.class;
    this.cachedSerClasses.add(cls);
    this.cachedSerFactories.add(beansf);
    this.cachedDeserFactories.add(beandf);
    qName = new QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "ArrayOfint");
    this.cachedSerQNames.add(qName);
    cls = int[].class;
    this.cachedSerClasses.add(cls);
    qName = new QName("http://www.w3.org/2001/XMLSchema", "int");
    qName2 = new QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "int");
    this.cachedSerFactories.add(new ArraySerializerFactory(qName, qName2));
    this.cachedDeserFactories.add(new ArrayDeserializerFactory());
  }
  
  protected Call createCall() throws RemoteException {
    try {
      Call _call = _createCall();
      if (this.maintainSessionSet)
        _call.setMaintainSession(this.maintainSession); 
      if (this.cachedUsername != null)
        _call.setUsername(this.cachedUsername); 
      if (this.cachedPassword != null)
        _call.setPassword(this.cachedPassword); 
      if (this.cachedEndpoint != null)
        _call.setTargetEndpointAddress(this.cachedEndpoint); 
      if (this.cachedTimeout != null)
        _call.setTimeout(this.cachedTimeout); 
      if (this.cachedPortName != null)
        _call.setPortName(this.cachedPortName); 
      Enumeration keys = this.cachedProperties.keys();
      while (keys.hasMoreElements()) {
        String key = (String)keys.nextElement();
        _call.setProperty(key, this.cachedProperties.get(key));
      } 
      synchronized (this) {
        if (firstCall()) {
          _call.setEncodingStyle(null);
          for (int i = 0; i < this.cachedSerFactories.size(); i++) {
            Class cls = (Class)this.cachedSerClasses.get(i);
            QName qName = 
              (QName)this.cachedSerQNames.get(i);
            Object x = this.cachedSerFactories.get(i);
            if (x instanceof Class) {
              Class sf = 
                (Class)this.cachedSerFactories.get(i);
              Class df = 
                (Class)this.cachedDeserFactories.get(i);
              _call.registerTypeMapping(cls, qName, sf, df, false);
            } else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
              SerializerFactory sf = 
                (SerializerFactory)this.cachedSerFactories.get(i);
              DeserializerFactory df = 
                (DeserializerFactory)this.cachedDeserFactories.get(i);
              _call.registerTypeMapping(cls, qName, sf, df, false);
            } 
          } 
        } 
      } 
      return _call;
    } catch (Throwable _t) {
      throw new AxisFault("Failure trying to get the Call object", _t);
    } 
  }
  
  public DcProjectSearchResults[] projectSearchGetDataContract(DcProjectSearchFilter searchParam) throws RemoteException {
    if (this.cachedEndpoint == null)
      throw new NoEndPointException(); 
    Call _call = createCall();
    _call.setOperation(_operations[0]);
    _call.setUseSOAPAction(true);
    _call.setSOAPActionURI("http://alps.milestone.umusic.net/IArchimedesProject/ProjectSearchGetDataContract");
    _call.setEncodingStyle(null);
    _call.setProperty("sendXsiTypes", Boolean.FALSE);
    _call.setProperty("sendMultiRefs", Boolean.FALSE);
    _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
    _call.setOperationName(new QName("http://alps.milestone.umusic.net/", "ProjectSearchGetDataContract"));
    setRequestHeaders(_call);
    setAttachments(_call);
    try {
      Object _resp = _call.invoke(new Object[] { searchParam });
      if (_resp instanceof RemoteException)
        throw (RemoteException)_resp; 
      extractAttachments(_call);
      try {
        return (DcProjectSearchResults[])_resp;
      } catch (Exception _exception) {
        return (DcProjectSearchResults[])JavaUtils.convert(_resp, DcProjectSearchResults[].class);
      } 
    } catch (AxisFault axisFaultException) {
      throw axisFaultException;
    } 
  }
  
  public DcProjectSearchResults[] projectSearchUMVDGetDataContract(DcProjectSearchFilter searchParam) throws RemoteException {
    if (this.cachedEndpoint == null)
      throw new NoEndPointException(); 
    Call _call = createCall();
    _call.setOperation(_operations[1]);
    _call.setUseSOAPAction(true);
    _call.setSOAPActionURI("http://alps.milestone.umusic.net/IArchimedesProject/ProjectSearchUMVDGetDataContract");
    _call.setEncodingStyle(null);
    _call.setProperty("sendXsiTypes", Boolean.FALSE);
    _call.setProperty("sendMultiRefs", Boolean.FALSE);
    _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
    _call.setOperationName(new QName("http://alps.milestone.umusic.net/", "ProjectSearchUMVDGetDataContract"));
    setRequestHeaders(_call);
    setAttachments(_call);
    try {
      Object _resp = _call.invoke(new Object[] { searchParam });
      if (_resp instanceof RemoteException)
        throw (RemoteException)_resp; 
      extractAttachments(_call);
      try {
        return (DcProjectSearchResults[])_resp;
      } catch (Exception _exception) {
        return (DcProjectSearchResults[])JavaUtils.convert(_resp, DcProjectSearchResults[].class);
      } 
    } catch (AxisFault axisFaultException) {
      throw axisFaultException;
    } 
  }
  
  public DcProjectSearchResults[] projectSearchMilestoneGetDataContract(DcMilestoneProjectSearchFilter searchParam) throws RemoteException {
    if (this.cachedEndpoint == null)
      throw new NoEndPointException(); 
    Call _call = createCall();
    _call.setOperation(_operations[2]);
    _call.setUseSOAPAction(true);
    _call.setSOAPActionURI("http://alps.milestone.umusic.net/IArchimedesProject/ProjectSearchMilestoneGetDataContract");
    _call.setEncodingStyle(null);
    _call.setProperty("sendXsiTypes", Boolean.FALSE);
    _call.setProperty("sendMultiRefs", Boolean.FALSE);
    _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
    _call.setOperationName(new QName("http://alps.milestone.umusic.net/", "ProjectSearchMilestoneGetDataContract"));
    setRequestHeaders(_call);
    setAttachments(_call);
    try {
      Object _resp = _call.invoke(new Object[] { searchParam });
      if (_resp instanceof RemoteException)
        throw (RemoteException)_resp; 
      extractAttachments(_call);
      try {
        return (DcProjectSearchResults[])_resp;
      } catch (Exception _exception) {
        return (DcProjectSearchResults[])JavaUtils.convert(_resp, DcProjectSearchResults[].class);
      } 
    } catch (AxisFault axisFaultException) {
      throw axisFaultException;
    } 
  }
  
  public Boolean projectNumberValidationGet(String projectNumber) throws RemoteException {
    if (this.cachedEndpoint == null)
      throw new NoEndPointException(); 
    Call _call = createCall();
    _call.setOperation(_operations[3]);
    _call.setUseSOAPAction(true);
    _call.setSOAPActionURI("http://alps.milestone.umusic.net/IArchimedesProject/ProjectNumberValidationGet");
    _call.setEncodingStyle(null);
    _call.setProperty("sendXsiTypes", Boolean.FALSE);
    _call.setProperty("sendMultiRefs", Boolean.FALSE);
    _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
    _call.setOperationName(new QName("http://alps.milestone.umusic.net/", "ProjectNumberValidationGet"));
    setRequestHeaders(_call);
    setAttachments(_call);
    try {
      Object _resp = _call.invoke(new Object[] { projectNumber });
      if (_resp instanceof RemoteException)
        throw (RemoteException)_resp; 
      extractAttachments(_call);
      try {
        return (Boolean)_resp;
      } catch (Exception _exception) {
        return (Boolean)JavaUtils.convert(_resp, Boolean.class);
      } 
    } catch (AxisFault axisFaultException) {
      throw axisFaultException;
    } 
  }
  
  public DcLabelData labelDataGetDataContractById(Integer labelID) throws RemoteException {
    if (this.cachedEndpoint == null)
      throw new NoEndPointException(); 
    Call _call = createCall();
    _call.setOperation(_operations[4]);
    _call.setUseSOAPAction(true);
    _call.setSOAPActionURI("http://alps.milestone.umusic.net/IArchimedesProject/LabelDataGetDataContractById");
    _call.setEncodingStyle(null);
    _call.setProperty("sendXsiTypes", Boolean.FALSE);
    _call.setProperty("sendMultiRefs", Boolean.FALSE);
    _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
    _call.setOperationName(new QName("http://alps.milestone.umusic.net/", "LabelDataGetDataContractById"));
    setRequestHeaders(_call);
    setAttachments(_call);
    try {
      Object _resp = _call.invoke(new Object[] { labelID });
      if (_resp instanceof RemoteException)
        throw (RemoteException)_resp; 
      extractAttachments(_call);
      try {
        return (DcLabelData)_resp;
      } catch (Exception _exception) {
        return (DcLabelData)JavaUtils.convert(_resp, DcLabelData.class);
      } 
    } catch (AxisFault axisFaultException) {
      throw axisFaultException;
    } 
  }
  
  public DcLabelData[] labelDataGetDataContract() throws RemoteException {
    if (this.cachedEndpoint == null)
      throw new NoEndPointException(); 
    Call _call = createCall();
    _call.setOperation(_operations[5]);
    _call.setUseSOAPAction(true);
    _call.setSOAPActionURI("http://alps.milestone.umusic.net/IArchimedesProject/LabelDataGetDataContract");
    _call.setEncodingStyle(null);
    _call.setProperty("sendXsiTypes", Boolean.FALSE);
    _call.setProperty("sendMultiRefs", Boolean.FALSE);
    _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
    _call.setOperationName(new QName("http://alps.milestone.umusic.net/", "LabelDataGetDataContract"));
    setRequestHeaders(_call);
    setAttachments(_call);
    try {
      Object _resp = _call.invoke(new Object[0]);
      if (_resp instanceof RemoteException)
        throw (RemoteException)_resp; 
      extractAttachments(_call);
      try {
        return (DcLabelData[])_resp;
      } catch (Exception _exception) {
        return (DcLabelData[])JavaUtils.convert(_resp, DcLabelData[].class);
      } 
    } catch (AxisFault axisFaultException) {
      throw axisFaultException;
    } 
  }
  
  public DsProjectStructureInterface projectGetByID(Integer archieID) throws RemoteException {
    if (this.cachedEndpoint == null)
      throw new NoEndPointException(); 
    Call _call = createCall();
    _call.setOperation(_operations[6]);
    _call.setUseSOAPAction(true);
    _call.setSOAPActionURI("http://alps.milestone.umusic.net/IArchimedesProject/ProjectGetByID");
    _call.setEncodingStyle(null);
    _call.setProperty("sendXsiTypes", Boolean.FALSE);
    _call.setProperty("sendMultiRefs", Boolean.FALSE);
    _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
    _call.setOperationName(new QName("http://alps.milestone.umusic.net/", "ProjectGetByID"));
    setRequestHeaders(_call);
    setAttachments(_call);
    try {
      Object _resp = _call.invoke(new Object[] { archieID });
      if (_resp instanceof RemoteException)
        throw (RemoteException)_resp; 
      extractAttachments(_call);
      try {
        return (DsProjectStructureInterface)_resp;
      } catch (Exception _exception) {
        return (DsProjectStructureInterface)JavaUtils.convert(_resp, DsProjectStructureInterface.class);
      } 
    } catch (AxisFault axisFaultException) {
      throw axisFaultException;
    } 
  }
  
  public DsLabelStructureInterface labelGetByID(Integer archieID) throws RemoteException {
    if (this.cachedEndpoint == null)
      throw new NoEndPointException(); 
    Call _call = createCall();
    _call.setOperation(_operations[7]);
    _call.setUseSOAPAction(true);
    _call.setSOAPActionURI("http://alps.milestone.umusic.net/IArchimedesProject/LabelGetByID");
    _call.setEncodingStyle(null);
    _call.setProperty("sendXsiTypes", Boolean.FALSE);
    _call.setProperty("sendMultiRefs", Boolean.FALSE);
    _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
    _call.setOperationName(new QName("http://alps.milestone.umusic.net/", "LabelGetByID"));
    setRequestHeaders(_call);
    setAttachments(_call);
    try {
      Object _resp = _call.invoke(new Object[] { archieID });
      if (_resp instanceof RemoteException)
        throw (RemoteException)_resp; 
      extractAttachments(_call);
      try {
        return (DsLabelStructureInterface)_resp;
      } catch (Exception _exception) {
        return (DsLabelStructureInterface)JavaUtils.convert(_resp, DsLabelStructureInterface.class);
      } 
    } catch (AxisFault axisFaultException) {
      throw axisFaultException;
    } 
  }
  
  public ProjectNumberValidResults projectSearchValidation(String projectNumber, Boolean isUMVD) throws RemoteException {
    if (this.cachedEndpoint == null)
      throw new NoEndPointException(); 
    Call _call = createCall();
    _call.setOperation(_operations[8]);
    _call.setUseSOAPAction(true);
    _call.setSOAPActionURI("http://alps.milestone.umusic.net/IArchimedesProject/ProjectSearchValidation");
    _call.setEncodingStyle(null);
    _call.setProperty("sendXsiTypes", Boolean.FALSE);
    _call.setProperty("sendMultiRefs", Boolean.FALSE);
    _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
    _call.setOperationName(new QName("http://alps.milestone.umusic.net/", "ProjectSearchValidation"));
    setRequestHeaders(_call);
    setAttachments(_call);
    try {
      Object _resp = _call.invoke(new Object[] { projectNumber, isUMVD });
      if (_resp instanceof RemoteException)
        throw (RemoteException)_resp; 
      extractAttachments(_call);
      try {
        return (ProjectNumberValidResults)_resp;
      } catch (Exception _exception) {
        return (ProjectNumberValidResults)JavaUtils.convert(_resp, ProjectNumberValidResults.class);
      } 
    } catch (AxisFault axisFaultException) {
      throw axisFaultException;
    } 
  }
  
  public DcDistributionCoData[] distributionCoGetDataContract() throws RemoteException {
    if (this.cachedEndpoint == null)
      throw new NoEndPointException(); 
    Call _call = createCall();
    _call.setOperation(_operations[9]);
    _call.setUseSOAPAction(true);
    _call.setSOAPActionURI("http://alps.milestone.umusic.net/IArchimedesProject/DistributionCoGetDataContract");
    _call.setEncodingStyle(null);
    _call.setProperty("sendXsiTypes", Boolean.FALSE);
    _call.setProperty("sendMultiRefs", Boolean.FALSE);
    _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
    _call.setOperationName(new QName("http://alps.milestone.umusic.net/", "DistributionCoGetDataContract"));
    setRequestHeaders(_call);
    setAttachments(_call);
    try {
      Object _resp = _call.invoke(new Object[0]);
      if (_resp instanceof RemoteException)
        throw (RemoteException)_resp; 
      extractAttachments(_call);
      try {
        return (DcDistributionCoData[])_resp;
      } catch (Exception _exception) {
        return (DcDistributionCoData[])JavaUtils.convert(_resp, DcDistributionCoData[].class);
      } 
    } catch (AxisFault axisFaultException) {
      throw axisFaultException;
    } 
  }
  
  public Boolean milestoneSnapshotProjectInsert(String projectID) throws RemoteException {
    if (this.cachedEndpoint == null)
      throw new NoEndPointException(); 
    Call _call = createCall();
    _call.setOperation(_operations[10]);
    _call.setUseSOAPAction(true);
    _call.setSOAPActionURI("http://alps.milestone.umusic.net/IArchimedesProject/MilestoneSnapshotProjectInsert");
    _call.setEncodingStyle(null);
    _call.setProperty("sendXsiTypes", Boolean.FALSE);
    _call.setProperty("sendMultiRefs", Boolean.FALSE);
    _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
    _call.setOperationName(new QName("http://alps.milestone.umusic.net/", "MilestoneSnapshotProjectInsert"));
    setRequestHeaders(_call);
    setAttachments(_call);
    try {
      Object _resp = _call.invoke(new Object[] { projectID });
      if (_resp instanceof RemoteException)
        throw (RemoteException)_resp; 
      extractAttachments(_call);
      try {
        return (Boolean)_resp;
      } catch (Exception _exception) {
        return (Boolean)JavaUtils.convert(_resp, Boolean.class);
      } 
    } catch (AxisFault axisFaultException) {
      throw axisFaultException;
    } 
  }
  
  public DcGDRSResults GDRSProductStatusGet(Integer releaseID, Integer releaseFamilyID, Integer environmentID) throws RemoteException {
    if (this.cachedEndpoint == null)
      throw new NoEndPointException(); 
    Call _call = createCall();
    _call.setOperation(_operations[11]);
    _call.setUseSOAPAction(true);
    _call.setSOAPActionURI("http://alps.milestone.umusic.net/IArchimedesProject/GDRSProductStatusGet");
    _call.setEncodingStyle(null);
    _call.setProperty("sendXsiTypes", Boolean.FALSE);
    _call.setProperty("sendMultiRefs", Boolean.FALSE);
    _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
    _call.setOperationName(new QName("http://alps.milestone.umusic.net/", "GDRSProductStatusGet"));
    setRequestHeaders(_call);
    setAttachments(_call);
    try {
      Object _resp = _call.invoke(new Object[] { releaseID, releaseFamilyID, environmentID });
      if (_resp instanceof RemoteException)
        throw (RemoteException)_resp; 
      extractAttachments(_call);
      try {
        return (DcGDRSResults)_resp;
      } catch (Exception _exception) {
        return (DcGDRSResults)JavaUtils.convert(_resp, DcGDRSResults.class);
      } 
    } catch (AxisFault axisFaultException) {
      throw axisFaultException;
    } 
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\org\tempuri\BasicAPStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */