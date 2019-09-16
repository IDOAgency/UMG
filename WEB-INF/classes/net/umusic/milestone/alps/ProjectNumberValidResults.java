package WEB-INF.classes.net.umusic.milestone.alps;

import java.io.Serializable;
import javax.xml.namespace.QName;
import net.umusic.milestone.alps.ProjectNumberValidResults;
import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

public class ProjectNumberValidResults implements Serializable {
  private String firstName;
  
  private String lastName;
  
  private Integer labelID;
  
  private Integer divisionID;
  
  private Integer companyID;
  
  private Integer environmentID;
  
  private Integer familyID;
  
  private String operatingCo;
  
  private String superLabel;
  
  private String subLabel;
  
  private String imprint;
  
  private String projectNumber;
  
  private Integer archieID;
  
  private String description;
  
  private String title;
  
  private Boolean isValid;
  
  public ProjectNumberValidResults(String firstName, String lastName, Integer labelID, Integer divisionID, Integer companyID, Integer environmentID, Integer familyID, String operatingCo, String superLabel, String subLabel, String imprint, String projectNumber, Integer archieID, String description, String title, Boolean isValid) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.labelID = labelID;
    this.divisionID = divisionID;
    this.companyID = companyID;
    this.environmentID = environmentID;
    this.familyID = familyID;
    this.operatingCo = operatingCo;
    this.superLabel = superLabel;
    this.subLabel = subLabel;
    this.imprint = imprint;
    this.projectNumber = projectNumber;
    this.archieID = archieID;
    this.description = description;
    this.title = title;
    this.isValid = isValid;
  }
  
  public String getFirstName() { return this.firstName; }
  
  public void setFirstName(String firstName) { this.firstName = firstName; }
  
  public String getLastName() { return this.lastName; }
  
  public void setLastName(String lastName) { this.lastName = lastName; }
  
  public Integer getLabelID() { return this.labelID; }
  
  public void setLabelID(Integer labelID) { this.labelID = labelID; }
  
  public Integer getDivisionID() { return this.divisionID; }
  
  public void setDivisionID(Integer divisionID) { this.divisionID = divisionID; }
  
  public Integer getCompanyID() { return this.companyID; }
  
  public void setCompanyID(Integer companyID) { this.companyID = companyID; }
  
  public Integer getEnvironmentID() { return this.environmentID; }
  
  public void setEnvironmentID(Integer environmentID) { this.environmentID = environmentID; }
  
  public Integer getFamilyID() { return this.familyID; }
  
  public void setFamilyID(Integer familyID) { this.familyID = familyID; }
  
  public String getOperatingCo() { return this.operatingCo; }
  
  public void setOperatingCo(String operatingCo) { this.operatingCo = operatingCo; }
  
  public String getSuperLabel() { return this.superLabel; }
  
  public void setSuperLabel(String superLabel) { this.superLabel = superLabel; }
  
  public String getSubLabel() { return this.subLabel; }
  
  public void setSubLabel(String subLabel) { this.subLabel = subLabel; }
  
  public String getImprint() { return this.imprint; }
  
  public void setImprint(String imprint) { this.imprint = imprint; }
  
  public String getProjectNumber() { return this.projectNumber; }
  
  public void setProjectNumber(String projectNumber) { this.projectNumber = projectNumber; }
  
  public Integer getArchieID() { return this.archieID; }
  
  public void setArchieID(Integer archieID) { this.archieID = archieID; }
  
  public String getDescription() { return this.description; }
  
  public void setDescription(String description) { this.description = description; }
  
  public String getTitle() { return this.title; }
  
  public void setTitle(String title) { this.title = title; }
  
  public Boolean getIsValid() { return this.isValid; }
  
  public void setIsValid(Boolean isValid) { this.isValid = isValid; }
  
  private Object __equalsCalc = null;
  
  public boolean equals(Object obj) {
    if (!(obj instanceof ProjectNumberValidResults))
      return false; 
    ProjectNumberValidResults other = (ProjectNumberValidResults)obj;
    if (obj == null)
      return false; 
    if (this == obj)
      return true; 
    if (this.__equalsCalc != null)
      return (this.__equalsCalc == obj); 
    this.__equalsCalc = obj;
    boolean _equals = 
      (((this.firstName == null && other.getFirstName() == null) || (
      this.firstName != null && 
      this.firstName.equals(other.getFirstName()))) && ((
      this.lastName == null && other.getLastName() == null) || (
      this.lastName != null && 
      this.lastName.equals(other.getLastName()))) && ((
      this.labelID == null && other.getLabelID() == null) || (
      this.labelID != null && 
      this.labelID.equals(other.getLabelID()))) && ((
      this.divisionID == null && other.getDivisionID() == null) || (
      this.divisionID != null && 
      this.divisionID.equals(other.getDivisionID()))) && ((
      this.companyID == null && other.getCompanyID() == null) || (
      this.companyID != null && 
      this.companyID.equals(other.getCompanyID()))) && ((
      this.environmentID == null && other.getEnvironmentID() == null) || (
      this.environmentID != null && 
      this.environmentID.equals(other.getEnvironmentID()))) && ((
      this.familyID == null && other.getFamilyID() == null) || (
      this.familyID != null && 
      this.familyID.equals(other.getFamilyID()))) && ((
      this.operatingCo == null && other.getOperatingCo() == null) || (
      this.operatingCo != null && 
      this.operatingCo.equals(other.getOperatingCo()))) && ((
      this.superLabel == null && other.getSuperLabel() == null) || (
      this.superLabel != null && 
      this.superLabel.equals(other.getSuperLabel()))) && ((
      this.subLabel == null && other.getSubLabel() == null) || (
      this.subLabel != null && 
      this.subLabel.equals(other.getSubLabel()))) && ((
      this.imprint == null && other.getImprint() == null) || (
      this.imprint != null && 
      this.imprint.equals(other.getImprint()))) && ((
      this.projectNumber == null && other.getProjectNumber() == null) || (
      this.projectNumber != null && 
      this.projectNumber.equals(other.getProjectNumber()))) && ((
      this.archieID == null && other.getArchieID() == null) || (
      this.archieID != null && 
      this.archieID.equals(other.getArchieID()))) && ((
      this.description == null && other.getDescription() == null) || (
      this.description != null && 
      this.description.equals(other.getDescription()))) && ((
      this.title == null && other.getTitle() == null) || (
      this.title != null && 
      this.title.equals(other.getTitle()))) && ((
      this.isValid == null && other.getIsValid() == null) || (
      this.isValid != null && 
      this.isValid.equals(other.getIsValid()))));
    this.__equalsCalc = null;
    return _equals;
  }
  
  private boolean __hashCodeCalc = false;
  
  public int hashCode() {
    if (this.__hashCodeCalc)
      return 0; 
    this.__hashCodeCalc = true;
    int _hashCode = 1;
    if (getFirstName() != null)
      _hashCode += getFirstName().hashCode(); 
    if (getLastName() != null)
      _hashCode += getLastName().hashCode(); 
    if (getLabelID() != null)
      _hashCode += getLabelID().hashCode(); 
    if (getDivisionID() != null)
      _hashCode += getDivisionID().hashCode(); 
    if (getCompanyID() != null)
      _hashCode += getCompanyID().hashCode(); 
    if (getEnvironmentID() != null)
      _hashCode += getEnvironmentID().hashCode(); 
    if (getFamilyID() != null)
      _hashCode += getFamilyID().hashCode(); 
    if (getOperatingCo() != null)
      _hashCode += getOperatingCo().hashCode(); 
    if (getSuperLabel() != null)
      _hashCode += getSuperLabel().hashCode(); 
    if (getSubLabel() != null)
      _hashCode += getSubLabel().hashCode(); 
    if (getImprint() != null)
      _hashCode += getImprint().hashCode(); 
    if (getProjectNumber() != null)
      _hashCode += getProjectNumber().hashCode(); 
    if (getArchieID() != null)
      _hashCode += getArchieID().hashCode(); 
    if (getDescription() != null)
      _hashCode += getDescription().hashCode(); 
    if (getTitle() != null)
      _hashCode += getTitle().hashCode(); 
    if (getIsValid() != null)
      _hashCode += getIsValid().hashCode(); 
    this.__hashCodeCalc = false;
    return _hashCode;
  }
  
  private static TypeDesc typeDesc = new TypeDesc(ProjectNumberValidResults.class, true);
  
  static  {
    typeDesc.setXmlType(new QName("http://alps.milestone.umusic.net/", "ProjectNumberValidResults"));
    elemField = new ElementDesc();
    elemField.setFieldName("firstName");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "FirstName"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
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
    elemField.setFieldName("labelID");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "LabelID"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
    elemField.setMinOccurs(0);
    elemField.setNillable(false);
    typeDesc.addFieldDesc(elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("divisionID");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "DivisionID"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
    elemField.setMinOccurs(0);
    elemField.setNillable(false);
    typeDesc.addFieldDesc(elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("companyID");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "CompanyID"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
    elemField.setMinOccurs(0);
    elemField.setNillable(false);
    typeDesc.addFieldDesc(elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("environmentID");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "EnvironmentID"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
    elemField.setMinOccurs(0);
    elemField.setNillable(false);
    typeDesc.addFieldDesc(elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("familyID");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "FamilyID"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
    elemField.setMinOccurs(0);
    elemField.setNillable(false);
    typeDesc.addFieldDesc(elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("operatingCo");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "OperatingCo"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("superLabel");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "SuperLabel"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("subLabel");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "SubLabel"));
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
    elemField.setFieldName("projectNumber");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "ProjectNumber"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("archieID");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "ArchieID"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("description");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "Description"));
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
    elemField = new ElementDesc();
    elemField.setFieldName("isValid");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "IsValid"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
  
  public ProjectNumberValidResults() {}
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\ne\\umusic\milestone\alps\ProjectNumberValidResults.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */