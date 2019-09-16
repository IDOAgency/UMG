package WEB-INF.classes.net.umusic.milestone.alps;

import java.io.Serializable;
import javax.xml.namespace.QName;
import net.umusic.milestone.alps.DcLabelData;
import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

public class DcLabelData implements Serializable {
  private String distCoName;
  
  private Integer distCoID;
  
  private Boolean APNGInd;
  
  private Integer archimedesID;
  
  private String entityName;
  
  private String legacyOperatingCo;
  
  private String legacyOperatingUnit;
  
  private String legacySuperlabel;
  
  private String legacySublabel;
  
  private String levelOfActivity;
  
  private String productionGroupCode;
  
  private String entityType;
  
  public DcLabelData(String distCoName, Integer distCoID, Boolean APNGInd, Integer archimedesID, String entityName, String legacyOperatingCo, String legacyOperatingUnit, String legacySuperlabel, String legacySublabel, String levelOfActivity, String productionGroupCode, String entityType) {
    this.distCoName = distCoName;
    this.distCoID = distCoID;
    this.APNGInd = APNGInd;
    this.archimedesID = archimedesID;
    this.entityName = entityName;
    this.legacyOperatingCo = legacyOperatingCo;
    this.legacyOperatingUnit = legacyOperatingUnit;
    this.legacySuperlabel = legacySuperlabel;
    this.legacySublabel = legacySublabel;
    this.levelOfActivity = levelOfActivity;
    this.productionGroupCode = productionGroupCode;
    this.entityType = entityType;
  }
  
  public String getDistCoName() { return this.distCoName; }
  
  public void setDistCoName(String distCoName) { this.distCoName = distCoName; }
  
  public Integer getDistCoID() { return this.distCoID; }
  
  public void setDistCoID(Integer distCoID) { this.distCoID = distCoID; }
  
  public Boolean getAPNGInd() { return this.APNGInd; }
  
  public void setAPNGInd(Boolean APNGInd) { this.APNGInd = APNGInd; }
  
  public Integer getArchimedesID() { return this.archimedesID; }
  
  public void setArchimedesID(Integer archimedesID) { this.archimedesID = archimedesID; }
  
  public String getEntityName() { return this.entityName; }
  
  public void setEntityName(String entityName) { this.entityName = entityName; }
  
  public String getLegacyOperatingCo() { return this.legacyOperatingCo; }
  
  public void setLegacyOperatingCo(String legacyOperatingCo) { this.legacyOperatingCo = legacyOperatingCo; }
  
  public String getLegacyOperatingUnit() { return this.legacyOperatingUnit; }
  
  public void setLegacyOperatingUnit(String legacyOperatingUnit) { this.legacyOperatingUnit = legacyOperatingUnit; }
  
  public String getLegacySuperlabel() { return this.legacySuperlabel; }
  
  public void setLegacySuperlabel(String legacySuperlabel) { this.legacySuperlabel = legacySuperlabel; }
  
  public String getLegacySublabel() { return this.legacySublabel; }
  
  public void setLegacySublabel(String legacySublabel) { this.legacySublabel = legacySublabel; }
  
  public String getLevelOfActivity() { return this.levelOfActivity; }
  
  public void setLevelOfActivity(String levelOfActivity) { this.levelOfActivity = levelOfActivity; }
  
  public String getProductionGroupCode() { return this.productionGroupCode; }
  
  public void setProductionGroupCode(String productionGroupCode) { this.productionGroupCode = productionGroupCode; }
  
  public String getEntityType() { return this.entityType; }
  
  public void setEntityType(String entityType) { this.entityType = entityType; }
  
  private Object __equalsCalc = null;
  
  public boolean equals(Object obj) {
    if (!(obj instanceof DcLabelData))
      return false; 
    DcLabelData other = (DcLabelData)obj;
    if (obj == null)
      return false; 
    if (this == obj)
      return true; 
    if (this.__equalsCalc != null)
      return (this.__equalsCalc == obj); 
    this.__equalsCalc = obj;
    boolean _equals = 
      (((this.distCoName == null && other.getDistCoName() == null) || (
      this.distCoName != null && 
      this.distCoName.equals(other.getDistCoName()))) && ((
      this.distCoID == null && other.getDistCoID() == null) || (
      this.distCoID != null && 
      this.distCoID.equals(other.getDistCoID()))) && ((
      this.APNGInd == null && other.getAPNGInd() == null) || (
      this.APNGInd != null && 
      this.APNGInd.equals(other.getAPNGInd()))) && ((
      this.archimedesID == null && other.getArchimedesID() == null) || (
      this.archimedesID != null && 
      this.archimedesID.equals(other.getArchimedesID()))) && ((
      this.entityName == null && other.getEntityName() == null) || (
      this.entityName != null && 
      this.entityName.equals(other.getEntityName()))) && ((
      this.legacyOperatingCo == null && other.getLegacyOperatingCo() == null) || (
      this.legacyOperatingCo != null && 
      this.legacyOperatingCo.equals(other.getLegacyOperatingCo()))) && ((
      this.legacyOperatingUnit == null && other.getLegacyOperatingUnit() == null) || (
      this.legacyOperatingUnit != null && 
      this.legacyOperatingUnit.equals(other.getLegacyOperatingUnit()))) && ((
      this.legacySuperlabel == null && other.getLegacySuperlabel() == null) || (
      this.legacySuperlabel != null && 
      this.legacySuperlabel.equals(other.getLegacySuperlabel()))) && ((
      this.legacySublabel == null && other.getLegacySublabel() == null) || (
      this.legacySublabel != null && 
      this.legacySublabel.equals(other.getLegacySublabel()))) && ((
      this.levelOfActivity == null && other.getLevelOfActivity() == null) || (
      this.levelOfActivity != null && 
      this.levelOfActivity.equals(other.getLevelOfActivity()))) && ((
      this.productionGroupCode == null && other.getProductionGroupCode() == null) || (
      this.productionGroupCode != null && 
      this.productionGroupCode.equals(other.getProductionGroupCode()))) && ((
      this.entityType == null && other.getEntityType() == null) || (
      this.entityType != null && 
      this.entityType.equals(other.getEntityType()))));
    this.__equalsCalc = null;
    return _equals;
  }
  
  private boolean __hashCodeCalc = false;
  
  public int hashCode() {
    if (this.__hashCodeCalc)
      return 0; 
    this.__hashCodeCalc = true;
    int _hashCode = 1;
    if (getDistCoName() != null)
      _hashCode += getDistCoName().hashCode(); 
    if (getDistCoID() != null)
      _hashCode += getDistCoID().hashCode(); 
    if (getAPNGInd() != null)
      _hashCode += getAPNGInd().hashCode(); 
    if (getArchimedesID() != null)
      _hashCode += getArchimedesID().hashCode(); 
    if (getEntityName() != null)
      _hashCode += getEntityName().hashCode(); 
    if (getLegacyOperatingCo() != null)
      _hashCode += getLegacyOperatingCo().hashCode(); 
    if (getLegacyOperatingUnit() != null)
      _hashCode += getLegacyOperatingUnit().hashCode(); 
    if (getLegacySuperlabel() != null)
      _hashCode += getLegacySuperlabel().hashCode(); 
    if (getLegacySublabel() != null)
      _hashCode += getLegacySublabel().hashCode(); 
    if (getLevelOfActivity() != null)
      _hashCode += getLevelOfActivity().hashCode(); 
    if (getProductionGroupCode() != null)
      _hashCode += getProductionGroupCode().hashCode(); 
    if (getEntityType() != null)
      _hashCode += getEntityType().hashCode(); 
    this.__hashCodeCalc = false;
    return _hashCode;
  }
  
  private static TypeDesc typeDesc = new TypeDesc(DcLabelData.class, true);
  
  static  {
    typeDesc.setXmlType(new QName("http://alps.milestone.umusic.net/", "dcLabelData"));
    elemField = new ElementDesc();
    elemField.setFieldName("distCoName");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "DistCoName"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("distCoID");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "DistCoID"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
    elemField.setMinOccurs(0);
    elemField.setNillable(false);
    typeDesc.addFieldDesc(elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("APNGInd");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "APNGInd"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
    elemField.setMinOccurs(0);
    elemField.setNillable(false);
    typeDesc.addFieldDesc(elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("archimedesID");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "ArchimedesID"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
    elemField.setMinOccurs(0);
    elemField.setNillable(false);
    typeDesc.addFieldDesc(elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("entityName");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "EntityName"));
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
    elemField.setFieldName("legacySuperlabel");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "LegacySuperlabel"));
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
    elemField.setFieldName("levelOfActivity");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "LevelOfActivity"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("productionGroupCode");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "ProductionGroupCode"));
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
  
  public DcLabelData() {}
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\ne\\umusic\milestone\alps\DcLabelData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */