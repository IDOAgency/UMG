package WEB-INF.classes.net.umusic.milestone.alps;

import java.io.Serializable;
import java.util.Calendar;
import javax.xml.namespace.QName;
import net.umusic.milestone.alps.DsProjectStructureInterface;
import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

public class DsProjectStructureInterface implements Serializable {
  private Integer ARLevelOfActivityID;
  
  private Boolean active;
  
  private Calendar completedDate;
  
  private String firstName;
  
  private String imprint;
  
  private Calendar initiatedDate;
  
  private String JDEProjectNumber;
  
  private Integer labelID;
  
  private String lastName;
  
  private String name;
  
  private Integer projectID;
  
  private String RMSProjectNumber;
  
  private String serviceException;
  
  private String title;
  
  public DsProjectStructureInterface(Integer ARLevelOfActivityID, Boolean active, Calendar completedDate, String firstName, String imprint, Calendar initiatedDate, String JDEProjectNumber, Integer labelID, String lastName, String name, Integer projectID, String RMSProjectNumber, String serviceException, String title) {
    this.ARLevelOfActivityID = ARLevelOfActivityID;
    this.active = active;
    this.completedDate = completedDate;
    this.firstName = firstName;
    this.imprint = imprint;
    this.initiatedDate = initiatedDate;
    this.JDEProjectNumber = JDEProjectNumber;
    this.labelID = labelID;
    this.lastName = lastName;
    this.name = name;
    this.projectID = projectID;
    this.RMSProjectNumber = RMSProjectNumber;
    this.serviceException = serviceException;
    this.title = title;
  }
  
  public Integer getARLevelOfActivityID() { return this.ARLevelOfActivityID; }
  
  public void setARLevelOfActivityID(Integer ARLevelOfActivityID) { this.ARLevelOfActivityID = ARLevelOfActivityID; }
  
  public Boolean getActive() { return this.active; }
  
  public void setActive(Boolean active) { this.active = active; }
  
  public Calendar getCompletedDate() { return this.completedDate; }
  
  public void setCompletedDate(Calendar completedDate) { this.completedDate = completedDate; }
  
  public String getFirstName() { return this.firstName; }
  
  public void setFirstName(String firstName) { this.firstName = firstName; }
  
  public String getImprint() { return this.imprint; }
  
  public void setImprint(String imprint) { this.imprint = imprint; }
  
  public Calendar getInitiatedDate() { return this.initiatedDate; }
  
  public void setInitiatedDate(Calendar initiatedDate) { this.initiatedDate = initiatedDate; }
  
  public String getJDEProjectNumber() { return this.JDEProjectNumber; }
  
  public void setJDEProjectNumber(String JDEProjectNumber) { this.JDEProjectNumber = JDEProjectNumber; }
  
  public Integer getLabelID() { return this.labelID; }
  
  public void setLabelID(Integer labelID) { this.labelID = labelID; }
  
  public String getLastName() { return this.lastName; }
  
  public void setLastName(String lastName) { this.lastName = lastName; }
  
  public String getName() { return this.name; }
  
  public void setName(String name) { this.name = name; }
  
  public Integer getProjectID() { return this.projectID; }
  
  public void setProjectID(Integer projectID) { this.projectID = projectID; }
  
  public String getRMSProjectNumber() { return this.RMSProjectNumber; }
  
  public void setRMSProjectNumber(String RMSProjectNumber) { this.RMSProjectNumber = RMSProjectNumber; }
  
  public String getServiceException() { return this.serviceException; }
  
  public void setServiceException(String serviceException) { this.serviceException = serviceException; }
  
  public String getTitle() { return this.title; }
  
  public void setTitle(String title) { this.title = title; }
  
  private Object __equalsCalc = null;
  
  public boolean equals(Object obj) {
    if (!(obj instanceof DsProjectStructureInterface))
      return false; 
    DsProjectStructureInterface other = (DsProjectStructureInterface)obj;
    if (obj == null)
      return false; 
    if (this == obj)
      return true; 
    if (this.__equalsCalc != null)
      return (this.__equalsCalc == obj); 
    this.__equalsCalc = obj;
    boolean _equals = 
      (((this.ARLevelOfActivityID == null && other.getARLevelOfActivityID() == null) || (
      this.ARLevelOfActivityID != null && 
      this.ARLevelOfActivityID.equals(other.getARLevelOfActivityID()))) && ((
      this.active == null && other.getActive() == null) || (
      this.active != null && 
      this.active.equals(other.getActive()))) && ((
      this.completedDate == null && other.getCompletedDate() == null) || (
      this.completedDate != null && 
      this.completedDate.equals(other.getCompletedDate()))) && ((
      this.firstName == null && other.getFirstName() == null) || (
      this.firstName != null && 
      this.firstName.equals(other.getFirstName()))) && ((
      this.imprint == null && other.getImprint() == null) || (
      this.imprint != null && 
      this.imprint.equals(other.getImprint()))) && ((
      this.initiatedDate == null && other.getInitiatedDate() == null) || (
      this.initiatedDate != null && 
      this.initiatedDate.equals(other.getInitiatedDate()))) && ((
      this.JDEProjectNumber == null && other.getJDEProjectNumber() == null) || (
      this.JDEProjectNumber != null && 
      this.JDEProjectNumber.equals(other.getJDEProjectNumber()))) && ((
      this.labelID == null && other.getLabelID() == null) || (
      this.labelID != null && 
      this.labelID.equals(other.getLabelID()))) && ((
      this.lastName == null && other.getLastName() == null) || (
      this.lastName != null && 
      this.lastName.equals(other.getLastName()))) && ((
      this.name == null && other.getName() == null) || (
      this.name != null && 
      this.name.equals(other.getName()))) && ((
      this.projectID == null && other.getProjectID() == null) || (
      this.projectID != null && 
      this.projectID.equals(other.getProjectID()))) && ((
      this.RMSProjectNumber == null && other.getRMSProjectNumber() == null) || (
      this.RMSProjectNumber != null && 
      this.RMSProjectNumber.equals(other.getRMSProjectNumber()))) && ((
      this.serviceException == null && other.getServiceException() == null) || (
      this.serviceException != null && 
      this.serviceException.equals(other.getServiceException()))) && ((
      this.title == null && other.getTitle() == null) || (
      this.title != null && 
      this.title.equals(other.getTitle()))));
    this.__equalsCalc = null;
    return _equals;
  }
  
  private boolean __hashCodeCalc = false;
  
  public int hashCode() {
    if (this.__hashCodeCalc)
      return 0; 
    this.__hashCodeCalc = true;
    int _hashCode = 1;
    if (getARLevelOfActivityID() != null)
      _hashCode += getARLevelOfActivityID().hashCode(); 
    if (getActive() != null)
      _hashCode += getActive().hashCode(); 
    if (getCompletedDate() != null)
      _hashCode += getCompletedDate().hashCode(); 
    if (getFirstName() != null)
      _hashCode += getFirstName().hashCode(); 
    if (getImprint() != null)
      _hashCode += getImprint().hashCode(); 
    if (getInitiatedDate() != null)
      _hashCode += getInitiatedDate().hashCode(); 
    if (getJDEProjectNumber() != null)
      _hashCode += getJDEProjectNumber().hashCode(); 
    if (getLabelID() != null)
      _hashCode += getLabelID().hashCode(); 
    if (getLastName() != null)
      _hashCode += getLastName().hashCode(); 
    if (getName() != null)
      _hashCode += getName().hashCode(); 
    if (getProjectID() != null)
      _hashCode += getProjectID().hashCode(); 
    if (getRMSProjectNumber() != null)
      _hashCode += getRMSProjectNumber().hashCode(); 
    if (getServiceException() != null)
      _hashCode += getServiceException().hashCode(); 
    if (getTitle() != null)
      _hashCode += getTitle().hashCode(); 
    this.__hashCodeCalc = false;
    return _hashCode;
  }
  
  private static TypeDesc typeDesc = new TypeDesc(DsProjectStructureInterface.class, true);
  
  static  {
    typeDesc.setXmlType(new QName("http://alps.milestone.umusic.net/", "dsProjectStructureInterface"));
    elemField = new ElementDesc();
    elemField.setFieldName("ARLevelOfActivityID");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "ARLevelOfActivityID"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("active");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "Active"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("completedDate");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "CompletedDate"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("firstName");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "FirstName"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("imprint");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "Imprint"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("initiatedDate");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "InitiatedDate"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("JDEProjectNumber");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "JDEProjectNumber"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("labelID");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "LabelID"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("lastName");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "LastName"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("name");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "Name"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("projectID");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "ProjectID"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
    elemField.setMinOccurs(0);
    elemField.setNillable(false);
    typeDesc.addFieldDesc(elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("RMSProjectNumber");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "RMSProjectNumber"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("serviceException");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "ServiceException"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("title");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "Title"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
  }
  
  public static TypeDesc getTypeDesc() { return typeDesc; }
  
  public static Serializer getSerializer(String mechType, Class _javaType, QName _xmlType) {
    return 
      new BeanSerializer(
        _javaType, _xmlType, typeDesc);
  }
  
  public static Deserializer getDeserializer(String mechType, Class _javaType, QName _xmlType) {
    return 
      new BeanDeserializer(
        _javaType, _xmlType, typeDesc);
  }
  
  public DsProjectStructureInterface() {}
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\ne\\umusic\milestone\alps\DsProjectStructureInterface.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */