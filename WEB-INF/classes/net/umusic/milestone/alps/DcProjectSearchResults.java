package WEB-INF.classes.net.umusic.milestone.alps;

import java.io.Serializable;
import java.util.Calendar;
import javax.xml.namespace.QName;
import net.umusic.milestone.alps.DcProjectSearchResults;
import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

public class DcProjectSearchResults implements Serializable {
  private String artist_First_Name;
  
  private String artist_Last_Name_Group_Name;
  
  private Integer project_ID;
  
  private String project_Title;
  
  private String project_Description;
  
  private Calendar create_Date;
  
  private Integer archimedes_ID;
  
  private String JDE_Project_;
  
  private String RMS_Project_;
  
  private String imprint;
  
  private Boolean active;
  
  private Integer artistRosterActivity;
  
  private String SAPProjectNumber;
  
  private String financialLabelDescription;
  
  public DcProjectSearchResults(String artist_First_Name, String artist_Last_Name_Group_Name, Integer project_ID, String project_Title, String project_Description, Calendar create_Date, Integer archimedes_ID, String JDE_Project_, String RMS_Project_, String imprint, Boolean active, Integer artistRosterActivity, String SAPProjectNumber, String financialLabelDescription) {
    this.artist_First_Name = artist_First_Name;
    this.artist_Last_Name_Group_Name = artist_Last_Name_Group_Name;
    this.project_ID = project_ID;
    this.project_Title = project_Title;
    this.project_Description = project_Description;
    this.create_Date = create_Date;
    this.archimedes_ID = archimedes_ID;
    this.JDE_Project_ = JDE_Project_;
    this.RMS_Project_ = RMS_Project_;
    this.imprint = imprint;
    this.active = active;
    this.artistRosterActivity = artistRosterActivity;
    this.SAPProjectNumber = SAPProjectNumber;
    this.financialLabelDescription = financialLabelDescription;
  }
  
  public String getArtist_First_Name() { return this.artist_First_Name; }
  
  public void setArtist_First_Name(String artist_First_Name) { this.artist_First_Name = artist_First_Name; }
  
  public String getArtist_Last_Name_Group_Name() { return this.artist_Last_Name_Group_Name; }
  
  public void setArtist_Last_Name_Group_Name(String artist_Last_Name_Group_Name) { this.artist_Last_Name_Group_Name = artist_Last_Name_Group_Name; }
  
  public Integer getProject_ID() { return this.project_ID; }
  
  public void setProject_ID(Integer project_ID) { this.project_ID = project_ID; }
  
  public String getProject_Title() { return this.project_Title; }
  
  public void setProject_Title(String project_Title) { this.project_Title = project_Title; }
  
  public String getProject_Description() { return this.project_Description; }
  
  public void setProject_Description(String project_Description) { this.project_Description = project_Description; }
  
  public Calendar getCreate_Date() { return this.create_Date; }
  
  public void setCreate_Date(Calendar create_Date) { this.create_Date = create_Date; }
  
  public Integer getArchimedes_ID() { return this.archimedes_ID; }
  
  public void setArchimedes_ID(Integer archimedes_ID) { this.archimedes_ID = archimedes_ID; }
  
  public String getJDE_Project_() { return this.JDE_Project_; }
  
  public void setJDE_Project_(String JDE_Project_) { this.JDE_Project_ = JDE_Project_; }
  
  public String getRMS_Project_() { return this.RMS_Project_; }
  
  public void setRMS_Project_(String RMS_Project_) { this.RMS_Project_ = RMS_Project_; }
  
  public String getImprint() { return this.imprint; }
  
  public void setImprint(String imprint) { this.imprint = imprint; }
  
  public Boolean getActive() { return this.active; }
  
  public void setActive(Boolean active) { this.active = active; }
  
  public Integer getArtistRosterActivity() { return this.artistRosterActivity; }
  
  public void setArtistRosterActivity(Integer artistRosterActivity) { this.artistRosterActivity = artistRosterActivity; }
  
  public String getSAPProjectNumber() { return this.SAPProjectNumber; }
  
  public void setSAPProjectNumber(String SAPProjectNumber) { this.SAPProjectNumber = SAPProjectNumber; }
  
  public String getFinancialLabelDescription() { return this.financialLabelDescription; }
  
  public void setFinancialLabelDescription(String financialLabelDescription) { this.financialLabelDescription = financialLabelDescription; }
  
  private Object __equalsCalc = null;
  
  public boolean equals(Object obj) {
    if (!(obj instanceof DcProjectSearchResults))
      return false; 
    DcProjectSearchResults other = (DcProjectSearchResults)obj;
    if (obj == null)
      return false; 
    if (this == obj)
      return true; 
    if (this.__equalsCalc != null)
      return (this.__equalsCalc == obj); 
    this.__equalsCalc = obj;
    boolean _equals = 
      (((this.artist_First_Name == null && other.getArtist_First_Name() == null) || (
      this.artist_First_Name != null && 
      this.artist_First_Name.equals(other.getArtist_First_Name()))) && ((
      this.artist_Last_Name_Group_Name == null && other.getArtist_Last_Name_Group_Name() == null) || (
      this.artist_Last_Name_Group_Name != null && 
      this.artist_Last_Name_Group_Name.equals(other.getArtist_Last_Name_Group_Name()))) && ((
      this.project_ID == null && other.getProject_ID() == null) || (
      this.project_ID != null && 
      this.project_ID.equals(other.getProject_ID()))) && ((
      this.project_Title == null && other.getProject_Title() == null) || (
      this.project_Title != null && 
      this.project_Title.equals(other.getProject_Title()))) && ((
      this.project_Description == null && other.getProject_Description() == null) || (
      this.project_Description != null && 
      this.project_Description.equals(other.getProject_Description()))) && ((
      this.create_Date == null && other.getCreate_Date() == null) || (
      this.create_Date != null && 
      this.create_Date.equals(other.getCreate_Date()))) && ((
      this.archimedes_ID == null && other.getArchimedes_ID() == null) || (
      this.archimedes_ID != null && 
      this.archimedes_ID.equals(other.getArchimedes_ID()))) && ((
      this.JDE_Project_ == null && other.getJDE_Project_() == null) || (
      this.JDE_Project_ != null && 
      this.JDE_Project_.equals(other.getJDE_Project_()))) && ((
      this.RMS_Project_ == null && other.getRMS_Project_() == null) || (
      this.RMS_Project_ != null && 
      this.RMS_Project_.equals(other.getRMS_Project_()))) && ((
      this.imprint == null && other.getImprint() == null) || (
      this.imprint != null && 
      this.imprint.equals(other.getImprint()))) && ((
      this.active == null && other.getActive() == null) || (
      this.active != null && 
      this.active.equals(other.getActive()))) && ((
      this.artistRosterActivity == null && other.getArtistRosterActivity() == null) || (
      this.artistRosterActivity != null && 
      this.artistRosterActivity.equals(other.getArtistRosterActivity()))) && ((
      this.SAPProjectNumber == null && other.getSAPProjectNumber() == null) || (
      this.SAPProjectNumber != null && 
      this.SAPProjectNumber.equals(other.getSAPProjectNumber()))) && ((
      this.financialLabelDescription == null && other.getFinancialLabelDescription() == null) || (
      this.financialLabelDescription != null && 
      this.financialLabelDescription.equals(other.getFinancialLabelDescription()))));
    this.__equalsCalc = null;
    return _equals;
  }
  
  private boolean __hashCodeCalc = false;
  
  public int hashCode() {
    if (this.__hashCodeCalc)
      return 0; 
    this.__hashCodeCalc = true;
    int _hashCode = 1;
    if (getArtist_First_Name() != null)
      _hashCode += getArtist_First_Name().hashCode(); 
    if (getArtist_Last_Name_Group_Name() != null)
      _hashCode += getArtist_Last_Name_Group_Name().hashCode(); 
    if (getProject_ID() != null)
      _hashCode += getProject_ID().hashCode(); 
    if (getProject_Title() != null)
      _hashCode += getProject_Title().hashCode(); 
    if (getProject_Description() != null)
      _hashCode += getProject_Description().hashCode(); 
    if (getCreate_Date() != null)
      _hashCode += getCreate_Date().hashCode(); 
    if (getArchimedes_ID() != null)
      _hashCode += getArchimedes_ID().hashCode(); 
    if (getJDE_Project_() != null)
      _hashCode += getJDE_Project_().hashCode(); 
    if (getRMS_Project_() != null)
      _hashCode += getRMS_Project_().hashCode(); 
    if (getImprint() != null)
      _hashCode += getImprint().hashCode(); 
    if (getActive() != null)
      _hashCode += getActive().hashCode(); 
    if (getArtistRosterActivity() != null)
      _hashCode += getArtistRosterActivity().hashCode(); 
    if (getSAPProjectNumber() != null)
      _hashCode += getSAPProjectNumber().hashCode(); 
    if (getFinancialLabelDescription() != null)
      _hashCode += getFinancialLabelDescription().hashCode(); 
    this.__hashCodeCalc = false;
    return _hashCode;
  }
  
  private static TypeDesc typeDesc = new TypeDesc(DcProjectSearchResults.class, true);
  
  static  {
    typeDesc.setXmlType(new QName("http://alps.milestone.umusic.net/", "dcProjectSearchResults"));
    elemField = new ElementDesc();
    elemField.setFieldName("artist_First_Name");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "Artist_First_Name"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("artist_Last_Name_Group_Name");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "Artist_Last_Name_Group_Name"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("project_ID");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "Project_ID"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
    elemField.setMinOccurs(0);
    elemField.setNillable(false);
    typeDesc.addFieldDesc(elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("project_Title");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "Project_Title"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("project_Description");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "Project_Description"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("create_Date");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "Create_Date"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
    elemField.setMinOccurs(0);
    elemField.setNillable(false);
    typeDesc.addFieldDesc(elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("archimedes_ID");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "Archimedes_ID"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
    elemField.setMinOccurs(0);
    elemField.setNillable(false);
    typeDesc.addFieldDesc(elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("JDE_Project_");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "JDE_Project_"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("RMS_Project_");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "RMS_Project_"));
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
    elemField.setFieldName("active");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "Active"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
    elemField.setMinOccurs(0);
    elemField.setNillable(false);
    typeDesc.addFieldDesc(elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("artistRosterActivity");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "ArtistRosterActivity"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
    elemField.setMinOccurs(0);
    elemField.setNillable(false);
    typeDesc.addFieldDesc(elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("SAPProjectNumber");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "SAPProjectNumber"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("financialLabelDescription");
    elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "FinancialLabelDescription"));
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
  
  public DcProjectSearchResults() {}
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\ne\\umusic\milestone\alps\DcProjectSearchResults.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */