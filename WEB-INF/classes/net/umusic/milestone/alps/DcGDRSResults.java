package WEB-INF.classes.net.umusic.milestone.alps;

import java.io.Serializable;
import javax.xml.namespace.QName;
import net.umusic.milestone.alps.DcGDRSResults;
import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

public class DcGDRSResults implements Serializable {
  private Integer releaseID;
  
  private String status;
  
  private Boolean forceNoDigitalRelease;
  
  private String exceptionMessage;
  
  public DcGDRSResults(Integer releaseID, String status, Boolean forceNoDigitalRelease, String exceptionMessage) {
    this.releaseID = releaseID;
    this.status = status;
    this.forceNoDigitalRelease = forceNoDigitalRelease;
    this.exceptionMessage = exceptionMessage;
  }
  
  public Integer getReleaseID() { return this.releaseID; }
  
  public void setReleaseID(Integer releaseID) { this.releaseID = releaseID; }
  
  public String getStatus() { return this.status; }
  
  public void setStatus(String status) { this.status = status; }
  
  public Boolean getForceNoDigitalRelease() { return this.forceNoDigitalRelease; }
  
  public void setForceNoDigitalRelease(Boolean forceNoDigitalRelease) { this.forceNoDigitalRelease = forceNoDigitalRelease; }
  
  public String getExceptionMessage() { return this.exceptionMessage; }
  
  public void setExceptionMessage(String exceptionMessage) { this.exceptionMessage = exceptionMessage; }
  
  private Object __equalsCalc = null;
  
  public boolean equals(Object obj) {
    if (!(obj instanceof DcGDRSResults))
      return false; 
    DcGDRSResults other = (DcGDRSResults)obj;
    if (obj == null)
      return false; 
    if (this == obj)
      return true; 
    if (this.__equalsCalc != null)
      return (this.__equalsCalc == obj); 
    this.__equalsCalc = obj;
    boolean _equals = 
      (((this.releaseID == null && other.getReleaseID() == null) || (
      this.releaseID != null && 
      this.releaseID.equals(other.getReleaseID()))) && ((
      this.status == null && other.getStatus() == null) || (
      this.status != null && 
      this.status.equals(other.getStatus()))) && ((
      this.forceNoDigitalRelease == null && other.getForceNoDigitalRelease() == null) || (
      this.forceNoDigitalRelease != null && 
      this.forceNoDigitalRelease.equals(other.getForceNoDigitalRelease()))) && ((
      this.exceptionMessage == null && other.getExceptionMessage() == null) || (
      this.exceptionMessage != null && 
      this.exceptionMessage.equals(other.getExceptionMessage()))));
    this.__equalsCalc = null;
    return _equals;
  }
  
  private boolean __hashCodeCalc = false;
  
  public int hashCode() {
    if (this.__hashCodeCalc)
      return 0; 
    this.__hashCodeCalc = true;
    int _hashCode = 1;
    if (getReleaseID() != null)
      _hashCode += getReleaseID().hashCode(); 
    if (getStatus() != null)
      _hashCode += getStatus().hashCode(); 
    if (getForceNoDigitalRelease() != null)
      _hashCode += getForceNoDigitalRelease().hashCode(); 
    if (getExceptionMessage() != null)
      _hashCode += getExceptionMessage().hashCode(); 
    this.__hashCodeCalc = false;
    return _hashCode;
  }
  
  private static TypeDesc typeDesc = new TypeDesc(DcGDRSResults.class, true);
  
  static  {
    typeDesc.setXmlType(new QName("http://alps.milestone.umusic.net/", "dcGDRSResults"));
    elemField = new ElementDesc();
    elemField.setFieldName("releaseID");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "ReleaseID"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
    elemField.setMinOccurs(0);
    elemField.setNillable(false);
    typeDesc.addFieldDesc(elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("status");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "Status"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("forceNoDigitalRelease");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "ForceNoDigitalRelease"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
    elemField.setMinOccurs(0);
    elemField.setNillable(false);
    typeDesc.addFieldDesc(elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("exceptionMessage");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "ExceptionMessage"));
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
  
  public DcGDRSResults() {}
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\ne\\umusic\milestone\alps\DcGDRSResults.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */