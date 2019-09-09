/*      */ package WEB-INF.classes.com.universal.milestone;
/*      */ 
/*      */ import com.techempower.DataEntity;
/*      */ import com.universal.milestone.NotepadContentObject;
/*      */ import com.universal.milestone.Report;
/*      */ import java.util.Calendar;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Report
/*      */   extends DataEntity
/*      */   implements NotepadContentObject, Comparable
/*      */ {
/*      */   protected int reportID;
/*      */   protected int reportType;
/*      */   protected int reportFormat;
/*      */   protected String reportName;
/*      */   protected String reportDescription;
/*      */   protected String reportStatus;
/*      */   protected String reportPath;
/*      */   protected String reportFileName;
/*      */   protected String subName;
/*      */   protected int reportOwner;
/*      */   protected boolean beginDate;
/*      */   protected boolean endDate;
/*      */   protected boolean region;
/*      */   protected boolean family;
/*      */   protected boolean environment;
/*      */   protected boolean company;
/*      */   protected boolean label;
/*      */   protected boolean contact;
/*      */   protected boolean productCodeGroup;
/*      */   protected boolean umlContact;
/*      */   protected boolean completeKeyTask;
/*      */   protected boolean umlKeyTask;
/*      */   protected boolean releaseType;
/*      */   protected boolean status;
/*      */   protected boolean artist;
/*      */   protected boolean umlDates;
/*      */   protected boolean future1;
/*      */   protected boolean future2;
/*      */   protected boolean keyTask;
/*      */   protected boolean endDueDate;
/*      */   protected boolean beginDueDate;
/*      */   protected boolean parentsOnly;
/*      */   protected boolean scheduledReleases;
/*      */   protected boolean configuration;
/*      */   protected boolean addsMovesBoth;
/*      */   protected boolean subconfigFlag;
/*      */   protected int productType;
/*      */   protected boolean endEffectiveDate;
/*      */   protected boolean beginEffectiveDate;
/*      */   protected boolean physicalProductActivity;
/*      */   protected Calendar lastUpdatedDate;
/*      */   protected int lastUpdatingUser;
/*      */   protected long lastUpdatedCk;
/*      */   protected int order;
/*      */   protected boolean distCo;
/*      */   
/*      */   public Report(int id) {
/*  135 */     this.order = 0; this.reportID = id; } public Report() { this.order = 0; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  152 */   public int getReportID() { return this.reportID; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  162 */   public void setReportID(int reportID) { this.reportID = reportID; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  172 */   public String getReportName() { return this.reportName; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  182 */   public void setReportName(String reportName) { this.reportName = reportName; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  192 */   public int getReportOwner() { return this.reportOwner; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  202 */   public void setReportOwner(int reportOwner) { this.reportOwner = reportOwner; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  212 */   public String getDescription() { return this.reportDescription; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  222 */   public void setDescription(String reportDescription) { this.reportDescription = reportDescription; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  232 */   public int getType() { return this.reportType; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  242 */   public void setType(int reportType) { this.reportType = reportType; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  252 */   public int getFormat() { return this.reportFormat; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  262 */   public void setFormat(int reportFormat) { this.reportFormat = reportFormat; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  272 */   public String getReportStatus() { return this.reportStatus; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  282 */   public void setReportStatus(String reportStatus) { this.reportStatus = reportStatus; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  292 */   public String getPath() { return this.reportPath; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  302 */   public void setPath(String reportPath) { this.reportPath = reportPath; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  312 */   public String getFileName() { return this.reportFileName; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  322 */   public void setFileName(String reportFileName) { this.reportFileName = reportFileName; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  332 */   public Calendar getLastUpdatedDate() { return this.lastUpdatedDate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  342 */   public void setLastUpdatedDate(Calendar lastUpdatedDate) { this.lastUpdatedDate = lastUpdatedDate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  352 */   public int getLastUpdatingUser() { return this.lastUpdatingUser; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  362 */   public void setLastUpdatingUser(int lastUpdatingUser) { this.lastUpdatingUser = lastUpdatingUser; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  372 */   public long getLastUpdatedCk() { return this.lastUpdatedCk; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  383 */   public void setLastUpdatedCk(long lastUpdatedCk) { this.lastUpdatedCk = lastUpdatedCk; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  396 */   public boolean getBeginDateFlag() { return this.beginDate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  406 */   public void setBeginDateFlag(boolean beginDate) { this.beginDate = beginDate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  416 */   public boolean getEndDateFlag() { return this.endDate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  426 */   public void setEndDateFlag(boolean endDate) { this.endDate = endDate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  436 */   public boolean getRegionFlag() { return this.region; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  446 */   public void setRegionFlag(boolean region) { this.region = region; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  456 */   public boolean getFamilyFlag() { return this.family; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  466 */   public void setFamilyFlag(boolean family) { this.family = family; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  477 */   public boolean getEnvironmentFlag() { return this.environment; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  487 */   public void setEnvironmentFlag(boolean environment) { this.environment = environment; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  499 */   public boolean getCompanyFlag() { return this.company; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  509 */   public void setCompanyFlag(boolean company) { this.company = company; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  519 */   public boolean getLabelFlag() { return this.label; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  529 */   public void setLabelFlag(boolean label) { this.label = label; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  539 */   public boolean getContactFlag() { return this.contact; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  549 */   public void setContactFlag(boolean contact) { this.contact = contact; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  559 */   public boolean getProductGroupCodeFlag() { return this.productCodeGroup; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  569 */   public void setProductGroupCodeFlag(boolean productCodeGroup) { this.productCodeGroup = productCodeGroup; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  581 */   public boolean getUMLContactFlag() { return this.umlContact; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  591 */   public void setUMLContactFlag(boolean umlContact) { this.umlContact = umlContact; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  602 */   public boolean getCompleteKeyTaskFlag() { return this.completeKeyTask; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  612 */   public void setCompleteKeyTaskFlag(boolean completeKeyTask) { this.completeKeyTask = completeKeyTask; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  622 */   public boolean getUmlKeyTaskFlag() { return this.umlKeyTask; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  632 */   public void setUmlKeyTaskFlag(boolean umlKeyTask) { this.umlKeyTask = umlKeyTask; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  642 */   public boolean getReleaseTypeFlag() { return this.releaseType; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  652 */   public void setReleaseTypeFlag(boolean releaseType) { this.releaseType = releaseType; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  662 */   public boolean getStatusFlag() { return this.status; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  672 */   public void setStatusFlag(boolean status) { this.status = status; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  682 */   public boolean getArtistFlag() { return this.artist; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  692 */   public void setArtistFlag(boolean artist) { this.artist = artist; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  702 */   public boolean getUmlDatesFlag() { return this.umlDates; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  712 */   public void setUmlDatesFlag(boolean umlDates) { this.umlDates = umlDates; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  722 */   public boolean getFuture1Flag() { return this.future1; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  732 */   public void setFuture1Flag(boolean future1) { this.future1 = future1; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  742 */   public boolean getFuture2Flag() { return this.future2; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  752 */   public void setFuture2Flag(boolean future2) { this.future2 = future2; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  766 */   public boolean getKeyTaskFlag() { return this.keyTask; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  776 */   public void setKeyTaskFlag(boolean keyTask) { this.keyTask = keyTask; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  786 */   public boolean getEndDueDateFlag() { return this.endDueDate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  796 */   public void setEndDueDateFlag(boolean endDueDate) { this.endDueDate = endDueDate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  807 */   public boolean getBeginDueDateFlag() { return this.beginDueDate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  817 */   public void setBeginDueDateFlag(boolean beginDueDate) { this.beginDueDate = beginDueDate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  827 */   public boolean getBeginEffectiveDateFlag() { return this.beginEffectiveDate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  837 */   public void setBeginEffectiveDateFlag(boolean beginEffectiveDate) { this.beginEffectiveDate = beginEffectiveDate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  847 */   public boolean getEndEffectiveDateFlag() { return this.endEffectiveDate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  857 */   public void setEndEffectiveDateFlag(boolean endEffectiveDate) { this.endEffectiveDate = endEffectiveDate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  868 */   public String getSubName() { return this.subName; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  878 */   public void setSubName(String subName) { this.subName = subName; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  888 */   public boolean getParentsOnlyFlag() { return this.parentsOnly; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  898 */   public void setParentsOnlyFlag(boolean parentsOnly) { this.parentsOnly = parentsOnly; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  908 */   public String getNotepadContentObjectId() { return Integer.toString(this.reportID); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  918 */   public void setConfiguration(boolean configuration) { this.configuration = configuration; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  926 */   public boolean getConfiguration() { return this.configuration; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  935 */   public void setAddsMovesBoth(boolean addsMovesBoth) { this.addsMovesBoth = addsMovesBoth; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  943 */   public boolean getAddsMovesBoth() { return this.addsMovesBoth; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  952 */   public void setSubconfigFlag(boolean subconfigFlag) { this.subconfigFlag = subconfigFlag; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  960 */   public boolean getSubconfigFlag() { return this.subconfigFlag; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  970 */   public boolean getScheduledReleasesFlag() { return this.scheduledReleases; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  980 */   public void setScheduledReleasesFlag(boolean scheduledReleases) { this.scheduledReleases = scheduledReleases; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  989 */   public void setProductType(int productTypeInt) { this.productType = productTypeInt; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  997 */   public int getProductType() { return this.productType; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1006 */   public boolean getPhysicalProductActivity() { return this.physicalProductActivity; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1015 */   public void setPhysicalProductActivity(boolean val) { this.physicalProductActivity = val; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1026 */   public int compareTo(Object report) throws ClassCastException { return compareByOrder(report); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int compareByOrder(Object report) throws ClassCastException {
/* 1037 */     Integer thisReport = new Integer(this.order);
/*      */ 
/*      */     
/* 1040 */     Integer thatReport = new Integer(((Report)report).order);
/*      */     
/* 1042 */     return thisReport.compareTo(thatReport);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1052 */   public boolean getDistCo() { return this.distCo; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1062 */   public void setDistCo(boolean distCo) { this.distCo = distCo; }
/*      */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\Report.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */