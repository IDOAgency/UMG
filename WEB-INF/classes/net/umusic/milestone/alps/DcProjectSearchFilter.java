package WEB-INF.classes.net.umusic.milestone.alps;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;
import javax.xml.namespace.QName;
import net.umusic.milestone.alps.DcProjectSearchFilter;
import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

public class DcProjectSearchFilter implements Serializable {
  private String artistFirstName;
  
  private String artistLastNameGroupName;
  
  private String projectID;
  
  private String projectTitle;
  
  private String projectDescription;
  
  private int[] archimedesIDs;
  
  private boolean active;
  
  private int[] levelOfActivityID;
  
  private int rowsToReturn;
  
  public DcProjectSearchFilter(String artistFirstName, String artistLastNameGroupName, String projectID, String projectTitle, String projectDescription, int[] archimedesIDs, boolean active, int[] levelOfActivityID, int rowsToReturn) {
    this.artistFirstName = artistFirstName;
    this.artistLastNameGroupName = artistLastNameGroupName;
    this.projectID = projectID;
    this.projectTitle = projectTitle;
    this.projectDescription = projectDescription;
    this.archimedesIDs = archimedesIDs;
    this.active = active;
    this.levelOfActivityID = levelOfActivityID;
    this.rowsToReturn = rowsToReturn;
  }
  
  public String getArtistFirstName() { return this.artistFirstName; }
  
  public void setArtistFirstName(String artistFirstName) { this.artistFirstName = artistFirstName; }
  
  public String getArtistLastNameGroupName() { return this.artistLastNameGroupName; }
  
  public void setArtistLastNameGroupName(String artistLastNameGroupName) { this.artistLastNameGroupName = artistLastNameGroupName; }
  
  public String getProjectID() { return this.projectID; }
  
  public void setProjectID(String projectID) { this.projectID = projectID; }
  
  public String getProjectTitle() { return this.projectTitle; }
  
  public void setProjectTitle(String projectTitle) { this.projectTitle = projectTitle; }
  
  public String getProjectDescription() { return this.projectDescription; }
  
  public void setProjectDescription(String projectDescription) { this.projectDescription = projectDescription; }
  
  public int[] getArchimedesIDs() { return this.archimedesIDs; }
  
  public void setArchimedesIDs(int[] archimedesIDs) { this.archimedesIDs = archimedesIDs; }
  
  public boolean isActive() { return this.active; }
  
  public void setActive(boolean active) { this.active = active; }
  
  public int[] getLevelOfActivityID() { return this.levelOfActivityID; }
  
  public void setLevelOfActivityID(int[] levelOfActivityID) { this.levelOfActivityID = levelOfActivityID; }
  
  public int getRowsToReturn() { return this.rowsToReturn; }
  
  public void setRowsToReturn(int rowsToReturn) { this.rowsToReturn = rowsToReturn; }
  
  private Object __equalsCalc = null;
  
  public boolean equals(Object obj) {
    if (!(obj instanceof DcProjectSearchFilter))
      return false; 
    DcProjectSearchFilter other = (DcProjectSearchFilter)obj;
    if (obj == null)
      return false; 
    if (this == obj)
      return true; 
    if (this.__equalsCalc != null)
      return (this.__equalsCalc == obj); 
    this.__equalsCalc = obj;
    boolean _equals = 
      (((this.artistFirstName == null && other.getArtistFirstName() == null) || (
      this.artistFirstName != null && 
      this.artistFirstName.equals(other.getArtistFirstName()))) && ((
      this.artistLastNameGroupName == null && other.getArtistLastNameGroupName() == null) || (
      this.artistLastNameGroupName != null && 
      this.artistLastNameGroupName.equals(other.getArtistLastNameGroupName()))) && ((
      this.projectID == null && other.getProjectID() == null) || (
      this.projectID != null && 
      this.projectID.equals(other.getProjectID()))) && ((
      this.projectTitle == null && other.getProjectTitle() == null) || (
      this.projectTitle != null && 
      this.projectTitle.equals(other.getProjectTitle()))) && ((
      this.projectDescription == null && other.getProjectDescription() == null) || (
      this.projectDescription != null && 
      this.projectDescription.equals(other.getProjectDescription()))) && ((
      this.archimedesIDs == null && other.getArchimedesIDs() == null) || (
      this.archimedesIDs != null && 
      Arrays.equals(this.archimedesIDs, other.getArchimedesIDs()))) && 
      this.active == other.isActive() && ((
      this.levelOfActivityID == null && other.getLevelOfActivityID() == null) || (
      this.levelOfActivityID != null && 
      Arrays.equals(this.levelOfActivityID, other.getLevelOfActivityID()))) && 
      this.rowsToReturn == other.getRowsToReturn());
    this.__equalsCalc = null;
    return _equals;
  }
  
  private boolean __hashCodeCalc = false;
  
  public int hashCode() {
    if (this.__hashCodeCalc)
      return 0; 
    this.__hashCodeCalc = true;
    int _hashCode = 1;
    if (getArtistFirstName() != null)
      _hashCode += getArtistFirstName().hashCode(); 
    if (getArtistLastNameGroupName() != null)
      _hashCode += getArtistLastNameGroupName().hashCode(); 
    if (getProjectID() != null)
      _hashCode += getProjectID().hashCode(); 
    if (getProjectTitle() != null)
      _hashCode += getProjectTitle().hashCode(); 
    if (getProjectDescription() != null)
      _hashCode += getProjectDescription().hashCode(); 
    if (getArchimedesIDs() != null)
      for (int i = 0; 
        i < Array.getLength(getArchimedesIDs()); 
        i++) {
        Object obj = Array.get(getArchimedesIDs(), i);
        if (obj != null && 
          !obj.getClass().isArray())
          _hashCode += obj.hashCode(); 
      }  
    _hashCode += (isActive() ? Boolean.TRUE : Boolean.FALSE).hashCode();
    if (getLevelOfActivityID() != null)
      for (int i = 0; 
        i < Array.getLength(getLevelOfActivityID()); 
        i++) {
        Object obj = Array.get(getLevelOfActivityID(), i);
        if (obj != null && 
          !obj.getClass().isArray())
          _hashCode += obj.hashCode(); 
      }  
    _hashCode += getRowsToReturn();
    this.__hashCodeCalc = false;
    return _hashCode;
  }
  
  private static TypeDesc typeDesc = new TypeDesc(DcProjectSearchFilter.class, true);
  
  static  {
    typeDesc.setXmlType(new QName("http://alps.milestone.umusic.net/", "dcProjectSearchFilter"));
    elemField = new ElementDesc();
    elemField.setFieldName("artistFirstName");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "ArtistFirstName"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("artistLastNameGroupName");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "ArtistLastNameGroupName"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("projectID");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "ProjectID"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("projectTitle");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "ProjectTitle"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("projectDescription");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "ProjectDescription"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("archimedesIDs");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "ArchimedesIDs"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
    elemField.setNillable(true);
    elemField.setItemQName(new QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "int"));
    typeDesc.addFieldDesc(elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("active");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "Active"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
    elemField.setNillable(false);
    typeDesc.addFieldDesc(elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("levelOfActivityID");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "LevelOfActivityID"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
    elemField.setNillable(true);
    elemField.setItemQName(new QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "int"));
    typeDesc.addFieldDesc(elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("rowsToReturn");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "rowsToReturn"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
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
  
  public DcProjectSearchFilter() {}
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\ne\\umusic\milestone\alps\DcProjectSearchFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */