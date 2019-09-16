package WEB-INF.classes.com.universal.milestone;

import com.universal.milestone.Label;
import com.universal.milestone.MilestoneDataEntity;
import com.universal.milestone.ProjectSearch;
import java.util.Calendar;

public class ProjectSearch extends MilestoneDataEntity implements Cloneable {
  protected String projectID = "";
  
  protected String artistFirstName = "";
  
  protected String artistLastName = "";
  
  protected String projectDesc = "";
  
  protected String title = "";
  
  protected Label label;
  
  protected String operCompany = "";
  
  protected String superLabel = "";
  
  protected String subLabel = "";
  
  protected Calendar createDate = null;
  
  protected int pd_indicator;
  
  protected String archimedesID;
  
  protected int msFamilyId;
  
  protected int msEnvironmentId;
  
  protected int msCompanyId;
  
  protected int msDivisionId;
  
  protected int msLabelId;
  
  protected String SAPProjectNo = "";
  
  protected String financialLabelDescription = "";
  
  protected String JDEProjectNo = "";
  
  protected String RMSProjectNo = "";
  
  protected String imprint = "";
  
  public String getTableName() { return "ProjectSearch"; }
  
  public int getIdentity() { return 0; }
  
  public String getProjectID() { return this.projectID; }
  
  public void setProjectID(String idNumber) { this.projectID = idNumber; }
  
  public int getPD_Indicator() { return this.pd_indicator; }
  
  public void setPD_Indicator(int pdIndicator) { this.pd_indicator = pdIndicator; }
  
  public String getArtistFirstName() { return this.artistFirstName; }
  
  public void setArtistFirstName(String firstName) { this.artistFirstName = firstName; }
  
  public String getArchimedesID() { return this.archimedesID; }
  
  public void setArchimedesID(String archi) { this.archimedesID = archi; }
  
  public String getArtistLastName() { return this.artistLastName; }
  
  public void setArtistLastName(String lastName) { this.artistLastName = lastName; }
  
  public int getMSFamilyId() { return this.msFamilyId; }
  
  public void setMSFamilyId(int msFamilyInt) { this.msFamilyId = msFamilyInt; }
  
  public int getMSEnvironmentId() { return this.msEnvironmentId; }
  
  public void setMSEnvironmentId(int msEnvId) { this.msEnvironmentId = msEnvId; }
  
  public int getMSCompanyId() { return this.msCompanyId; }
  
  public void setMSCompanyId(int msComId) { this.msCompanyId = msComId; }
  
  public int getMSDivisionId() { return this.msDivisionId; }
  
  public void setMSDivisionId(int msDivisionInt) { this.msDivisionId = msDivisionInt; }
  
  public int getMSLabelId() { return this.msLabelId; }
  
  public void setMSLabelId(int labelInt) { this.msLabelId = labelInt; }
  
  public String getProjectDesc() { return this.projectDesc; }
  
  public void setProjectDesc(String pDesc) { this.projectDesc = pDesc; }
  
  public String getTitle() { return this.title; }
  
  public void setTitle(String theTitle) { this.title = theTitle; }
  
  public String getOperCompany() { return this.operCompany; }
  
  public void setOperCompany(String oper_company) { this.operCompany = oper_company; }
  
  public String getSuperLabel() { return this.superLabel; }
  
  public void setSuperLabel(String super_label) { this.superLabel = super_label; }
  
  public String getSubLabel() { return this.subLabel; }
  
  public void setSubLabel(String sub_label) { this.subLabel = sub_label; }
  
  public Calendar getCreateDate() { return this.createDate; }
  
  public void setCreateDate(Calendar date) { this.createDate = date; }
  
  public String getJDEProjectNo() { return this.JDEProjectNo; }
  
  public void setJDEProjectNo(String JDEProjectNo) { this.JDEProjectNo = JDEProjectNo; }
  
  public String getRMSProjectNo() { return this.RMSProjectNo; }
  
  public void setRMSProjectNo(String RMSProjectNo) { this.RMSProjectNo = RMSProjectNo; }
  
  public String getImprint() { return this.imprint; }
  
  public void setImprint(String imprint) { this.imprint = imprint; }
  
  public void setSAPProjectNo(String sapProjectNo) { this.SAPProjectNo = sapProjectNo; }
  
  public String getSAPProjectNo() { return this.SAPProjectNo; }
  
  public void setfinancialLabelDescription(String financialLabel) { this.financialLabelDescription = financialLabel; }
  
  public String getfinancialLabelDescription() { return this.financialLabelDescription; }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\ProjectSearch.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */