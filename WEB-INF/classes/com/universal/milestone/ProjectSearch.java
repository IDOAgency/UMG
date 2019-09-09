/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.universal.milestone.Label;
/*     */ import com.universal.milestone.MilestoneDataEntity;
/*     */ import com.universal.milestone.ProjectSearch;
/*     */ import java.util.Calendar;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ProjectSearch
/*     */   extends MilestoneDataEntity
/*     */   implements Cloneable
/*     */ {
/*  34 */   protected String projectID = "";
/*  35 */   protected String artistFirstName = "";
/*  36 */   protected String artistLastName = "";
/*  37 */   protected String projectDesc = "";
/*  38 */   protected String title = "";
/*     */   protected Label label;
/*  40 */   protected String operCompany = "";
/*  41 */   protected String superLabel = "";
/*  42 */   protected String subLabel = "";
/*  43 */   protected Calendar createDate = null;
/*     */   
/*     */   protected int pd_indicator;
/*     */   
/*     */   protected String archimedesID;
/*     */   protected int msFamilyId;
/*     */   protected int msEnvironmentId;
/*     */   protected int msCompanyId;
/*     */   protected int msDivisionId;
/*     */   protected int msLabelId;
/*  53 */   protected String SAPProjectNo = "";
/*  54 */   protected String financialLabelDescription = "";
/*     */ 
/*     */   
/*  57 */   protected String JDEProjectNo = "";
/*  58 */   protected String RMSProjectNo = "";
/*  59 */   protected String imprint = "";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  72 */   public String getTableName() { return "ProjectSearch"; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  87 */   public int getIdentity() { return 0; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  96 */   public String getProjectID() { return this.projectID; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 106 */   public void setProjectID(String idNumber) { this.projectID = idNumber; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 115 */   public int getPD_Indicator() { return this.pd_indicator; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 125 */   public void setPD_Indicator(int pdIndicator) { this.pd_indicator = pdIndicator; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 133 */   public String getArtistFirstName() { return this.artistFirstName; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 143 */   public void setArtistFirstName(String firstName) { this.artistFirstName = firstName; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 151 */   public String getArchimedesID() { return this.archimedesID; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 161 */   public void setArchimedesID(String archi) { this.archimedesID = archi; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 170 */   public String getArtistLastName() { return this.artistLastName; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 180 */   public void setArtistLastName(String lastName) { this.artistLastName = lastName; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 188 */   public int getMSFamilyId() { return this.msFamilyId; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 198 */   public void setMSFamilyId(int msFamilyInt) { this.msFamilyId = msFamilyInt; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 206 */   public int getMSEnvironmentId() { return this.msEnvironmentId; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 216 */   public void setMSEnvironmentId(int msEnvId) { this.msEnvironmentId = msEnvId; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 224 */   public int getMSCompanyId() { return this.msCompanyId; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 234 */   public void setMSCompanyId(int msComId) { this.msCompanyId = msComId; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 242 */   public int getMSDivisionId() { return this.msDivisionId; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 252 */   public void setMSDivisionId(int msDivisionInt) { this.msDivisionId = msDivisionInt; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 260 */   public int getMSLabelId() { return this.msLabelId; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 270 */   public void setMSLabelId(int labelInt) { this.msLabelId = labelInt; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 278 */   public String getProjectDesc() { return this.projectDesc; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 288 */   public void setProjectDesc(String pDesc) { this.projectDesc = pDesc; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 296 */   public String getTitle() { return this.title; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 306 */   public void setTitle(String theTitle) { this.title = theTitle; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 315 */   public String getOperCompany() { return this.operCompany; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 324 */   public void setOperCompany(String oper_company) { this.operCompany = oper_company; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 333 */   public String getSuperLabel() { return this.superLabel; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 342 */   public void setSuperLabel(String super_label) { this.superLabel = super_label; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 351 */   public String getSubLabel() { return this.subLabel; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 360 */   public void setSubLabel(String sub_label) { this.subLabel = sub_label; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 370 */   public Calendar getCreateDate() { return this.createDate; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 380 */   public void setCreateDate(Calendar date) { this.createDate = date; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 392 */   public String getJDEProjectNo() { return this.JDEProjectNo; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 402 */   public void setJDEProjectNo(String JDEProjectNo) { this.JDEProjectNo = JDEProjectNo; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 410 */   public String getRMSProjectNo() { return this.RMSProjectNo; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 419 */   public void setRMSProjectNo(String RMSProjectNo) { this.RMSProjectNo = RMSProjectNo; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 427 */   public String getImprint() { return this.imprint; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 436 */   public void setImprint(String imprint) { this.imprint = imprint; }
/*     */ 
/*     */ 
/*     */   
/* 440 */   public void setSAPProjectNo(String sapProjectNo) { this.SAPProjectNo = sapProjectNo; }
/*     */ 
/*     */ 
/*     */   
/* 444 */   public String getSAPProjectNo() { return this.SAPProjectNo; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 449 */   public void setfinancialLabelDescription(String financialLabel) { this.financialLabelDescription = financialLabel; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 454 */   public String getfinancialLabelDescription() { return this.financialLabelDescription; }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\ProjectSearch.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */