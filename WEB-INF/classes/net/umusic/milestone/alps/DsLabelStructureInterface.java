package WEB-INF.classes.net.umusic.milestone.alps;

import java.io.Serializable;
import javax.xml.namespace.QName;
import net.umusic.milestone.alps.DsLabelStructureInterface;
import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

public class DsLabelStructureInterface implements Serializable {
  private String company;
  
  private String division;
  
  private String entityName;
  
  private String entityType;
  
  private String environmentName;
  
  private String familyName;
  
  private Integer ID;
  
  private String JDEReportingCo;
  
  private String JDEReportingUnit;
  
  private String label;
  
  private String legacyOperatingCo;
  
  private String legacyOperatingUnit;
  
  private String legacySublabel;
  
  private String legacySuperlabel;
  
  public DsLabelStructureInterface(String company, String division, String entityName, String entityType, String environmentName, String familyName, Integer ID, String JDEReportingCo, String JDEReportingUnit, String label, String legacyOperatingCo, String legacyOperatingUnit, String legacySublabel, String legacySuperlabel) {
    this.company = company;
    this.division = division;
    this.entityName = entityName;
    this.entityType = entityType;
    this.environmentName = environmentName;
    this.familyName = familyName;
    this.ID = ID;
    this.JDEReportingCo = JDEReportingCo;
    this.JDEReportingUnit = JDEReportingUnit;
    this.label = label;
    this.legacyOperatingCo = legacyOperatingCo;
    this.legacyOperatingUnit = legacyOperatingUnit;
    this.legacySublabel = legacySublabel;
    this.legacySuperlabel = legacySuperlabel;
  }
  
  public String getCompany() { return this.company; }
  
  public void setCompany(String company) { this.company = company; }
  
  public String getDivision() { return this.division; }
  
  public void setDivision(String division) { this.division = division; }
  
  public String getEntityName() { return this.entityName; }
  
  public void setEntityName(String entityName) { this.entityName = entityName; }
  
  public String getEntityType() { return this.entityType; }
  
  public void setEntityType(String entityType) { this.entityType = entityType; }
  
  public String getEnvironmentName() { return this.environmentName; }
  
  public void setEnvironmentName(String environmentName) { this.environmentName = environmentName; }
  
  public String getFamilyName() { return this.familyName; }
  
  public void setFamilyName(String familyName) { this.familyName = familyName; }
  
  public Integer getID() { return this.ID; }
  
  public void setID(Integer ID) { this.ID = ID; }
  
  public String getJDEReportingCo() { return this.JDEReportingCo; }
  
  public void setJDEReportingCo(String JDEReportingCo) { this.JDEReportingCo = JDEReportingCo; }
  
  public String getJDEReportingUnit() { return this.JDEReportingUnit; }
  
  public void setJDEReportingUnit(String JDEReportingUnit) { this.JDEReportingUnit = JDEReportingUnit; }
  
  public String getLabel() { return this.label; }
  
  public void setLabel(String label) { this.label = label; }
  
  public String getLegacyOperatingCo() { return this.legacyOperatingCo; }
  
  public void setLegacyOperatingCo(String legacyOperatingCo) { this.legacyOperatingCo = legacyOperatingCo; }
  
  public String getLegacyOperatingUnit() { return this.legacyOperatingUnit; }
  
  public void setLegacyOperatingUnit(String legacyOperatingUnit) { this.legacyOperatingUnit = legacyOperatingUnit; }
  
  public String getLegacySublabel() { return this.legacySublabel; }
  
  public void setLegacySublabel(String legacySublabel) { this.legacySublabel = legacySublabel; }
  
  public String getLegacySuperlabel() { return this.legacySuperlabel; }
  
  public void setLegacySuperlabel(String legacySuperlabel) { this.legacySuperlabel = legacySuperlabel; }
  
  private Object __equalsCalc = null;
  
  public boolean equals(Object obj) {
    if (!(obj instanceof DsLabelStructureInterface))
      return false; 
    DsLabelStructureInterface other = (DsLabelStructureInterface)obj;
    if (obj == null)
      return false; 
    if (this == obj)
      return true; 
    if (this.__equalsCalc != null)
      return (this.__equalsCalc == obj); 
    this.__equalsCalc = obj;
    boolean _equals = 
      (((this.company == null && other.getCompany() == null) || (
      this.company != null && 
      this.company.equals(other.getCompany()))) && ((
      this.division == null && other.getDivision() == null) || (
      this.division != null && 
      this.division.equals(other.getDivision()))) && ((
      this.entityName == null && other.getEntityName() == null) || (
      this.entityName != null && 
      this.entityName.equals(other.getEntityName()))) && ((
      this.entityType == null && other.getEntityType() == null) || (
      this.entityType != null && 
      this.entityType.equals(other.getEntityType()))) && ((
      this.environmentName == null && other.getEnvironmentName() == null) || (
      this.environmentName != null && 
      this.environmentName.equals(other.getEnvironmentName()))) && ((
      this.familyName == null && other.getFamilyName() == null) || (
      this.familyName != null && 
      this.familyName.equals(other.getFamilyName()))) && ((
      this.ID == null && other.getID() == null) || (
      this.ID != null && 
      this.ID.equals(other.getID()))) && ((
      this.JDEReportingCo == null && other.getJDEReportingCo() == null) || (
      this.JDEReportingCo != null && 
      this.JDEReportingCo.equals(other.getJDEReportingCo()))) && ((
      this.JDEReportingUnit == null && other.getJDEReportingUnit() == null) || (
      this.JDEReportingUnit != null && 
      this.JDEReportingUnit.equals(other.getJDEReportingUnit()))) && ((
      this.label == null && other.getLabel() == null) || (
      this.label != null && 
      this.label.equals(other.getLabel()))) && ((
      this.legacyOperatingCo == null && other.getLegacyOperatingCo() == null) || (
      this.legacyOperatingCo != null && 
      this.legacyOperatingCo.equals(other.getLegacyOperatingCo()))) && ((
      this.legacyOperatingUnit == null && other.getLegacyOperatingUnit() == null) || (
      this.legacyOperatingUnit != null && 
      this.legacyOperatingUnit.equals(other.getLegacyOperatingUnit()))) && ((
      this.legacySublabel == null && other.getLegacySublabel() == null) || (
      this.legacySublabel != null && 
      this.legacySublabel.equals(other.getLegacySublabel()))) && ((
      this.legacySuperlabel == null && other.getLegacySuperlabel() == null) || (
      this.legacySuperlabel != null && 
      this.legacySuperlabel.equals(other.getLegacySuperlabel()))));
    this.__equalsCalc = null;
    return _equals;
  }
  
  private boolean __hashCodeCalc = false;
  
  public int hashCode() {
    if (this.__hashCodeCalc)
      return 0; 
    this.__hashCodeCalc = true;
    int _hashCode = 1;
    if (getCompany() != null)
      _hashCode += getCompany().hashCode(); 
    if (getDivision() != null)
      _hashCode += getDivision().hashCode(); 
    if (getEntityName() != null)
      _hashCode += getEntityName().hashCode(); 
    if (getEntityType() != null)
      _hashCode += getEntityType().hashCode(); 
    if (getEnvironmentName() != null)
      _hashCode += getEnvironmentName().hashCode(); 
    if (getFamilyName() != null)
      _hashCode += getFamilyName().hashCode(); 
    if (getID() != null)
      _hashCode += getID().hashCode(); 
    if (getJDEReportingCo() != null)
      _hashCode += getJDEReportingCo().hashCode(); 
    if (getJDEReportingUnit() != null)
      _hashCode += getJDEReportingUnit().hashCode(); 
    if (getLabel() != null)
      _hashCode += getLabel().hashCode(); 
    if (getLegacyOperatingCo() != null)
      _hashCode += getLegacyOperatingCo().hashCode(); 
    if (getLegacyOperatingUnit() != null)
      _hashCode += getLegacyOperatingUnit().hashCode(); 
    if (getLegacySublabel() != null)
      _hashCode += getLegacySublabel().hashCode(); 
    if (getLegacySuperlabel() != null)
      _hashCode += getLegacySuperlabel().hashCode(); 
    this.__hashCodeCalc = false;
    return _hashCode;
  }
  
  private static TypeDesc typeDesc = new TypeDesc(DsLabelStructureInterface.class, true);
  
  static  {
    typeDesc.setXmlType(new QName("http://alps.milestone.umusic.net/", "dsLabelStructureInterface"));
    elemField = new ElementDesc();
    elemField.setFieldName("company");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "Company"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("division");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "Division"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("entityName");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "EntityName"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("entityType");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "EntityType"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("environmentName");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "EnvironmentName"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("familyName");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "FamilyName"));
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
    elemField.setFieldName("JDEReportingCo");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "JDEReportingCo"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("JDEReportingUnit");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "JDEReportingUnit"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("label");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "Label"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("legacyOperatingCo");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "LegacyOperatingCo"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("legacyOperatingUnit");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "LegacyOperatingUnit"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("legacySublabel");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "LegacySublabel"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("legacySuperlabel");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "LegacySuperlabel"));
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
  
  public DsLabelStructureInterface() {}
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\ne\\umusic\milestone\alps\DsLabelStructureInterface.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */