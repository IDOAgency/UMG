package WEB-INF.classes.net.umusic.milestone.alps;

import java.io.Serializable;
import javax.xml.namespace.QName;
import net.umusic.milestone.alps.DcDistributionCoData;
import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

public class DcDistributionCoData implements Serializable {
  private String name;
  
  private Integer ID;
  
  private Integer sortOrder;
  
  public DcDistributionCoData(String name, Integer ID, Integer sortOrder) {
    this.name = name;
    this.ID = ID;
    this.sortOrder = sortOrder;
  }
  
  public String getName() { return this.name; }
  
  public void setName(String name) { this.name = name; }
  
  public Integer getID() { return this.ID; }
  
  public void setID(Integer ID) { this.ID = ID; }
  
  public Integer getSortOrder() { return this.sortOrder; }
  
  public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }
  
  private Object __equalsCalc = null;
  
  public boolean equals(Object obj) {
    if (!(obj instanceof DcDistributionCoData))
      return false; 
    DcDistributionCoData other = (DcDistributionCoData)obj;
    if (obj == null)
      return false; 
    if (this == obj)
      return true; 
    if (this.__equalsCalc != null)
      return (this.__equalsCalc == obj); 
    this.__equalsCalc = obj;
    boolean _equals = 
      (((this.name == null && other.getName() == null) || (
      this.name != null && 
      this.name.equals(other.getName()))) && ((
      this.ID == null && other.getID() == null) || (
      this.ID != null && 
      this.ID.equals(other.getID()))) && ((
      this.sortOrder == null && other.getSortOrder() == null) || (
      this.sortOrder != null && 
      this.sortOrder.equals(other.getSortOrder()))));
    this.__equalsCalc = null;
    return _equals;
  }
  
  private boolean __hashCodeCalc = false;
  
  public int hashCode() {
    if (this.__hashCodeCalc)
      return 0; 
    this.__hashCodeCalc = true;
    int _hashCode = 1;
    if (getName() != null)
      _hashCode += getName().hashCode(); 
    if (getID() != null)
      _hashCode += getID().hashCode(); 
    if (getSortOrder() != null)
      _hashCode += getSortOrder().hashCode(); 
    this.__hashCodeCalc = false;
    return _hashCode;
  }
  
  private static TypeDesc typeDesc = new TypeDesc(DcDistributionCoData.class, true);
  
  static  {
    typeDesc.setXmlType(new QName("http://alps.milestone.umusic.net/", "dcDistributionCoData"));
    elemField = new ElementDesc();
    elemField.setFieldName("name");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "Name"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("ID");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "ID"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
    elemField.setMinOccurs(0);
    elemField.setNillable(false);
    typeDesc.addFieldDesc(elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("sortOrder");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "SortOrder"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
    elemField.setMinOccurs(0);
    elemField.setNillable(false);
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
  
  public DcDistributionCoData() {}
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\ne\\umusic\milestone\alps\DcDistributionCoData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */