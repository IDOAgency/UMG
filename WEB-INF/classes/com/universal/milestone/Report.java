package WEB-INF.classes.com.universal.milestone;

import com.techempower.DataEntity;
import com.universal.milestone.NotepadContentObject;
import com.universal.milestone.Report;
import java.util.Calendar;

public class Report extends DataEntity implements NotepadContentObject, Comparable {
  protected int reportID;
  
  protected int reportType;
  
  protected int reportFormat;
  
  protected String reportName;
  
  protected String reportDescription;
  
  protected String reportStatus;
  
  protected String reportPath;
  
  protected String reportFileName;
  
  protected String subName;
  
  protected int reportOwner;
  
  protected boolean beginDate;
  
  protected boolean endDate;
  
  protected boolean region;
  
  protected boolean family;
  
  protected boolean environment;
  
  protected boolean company;
  
  protected boolean label;
  
  protected boolean contact;
  
  protected boolean productCodeGroup;
  
  protected boolean umlContact;
  
  protected boolean completeKeyTask;
  
  protected boolean umlKeyTask;
  
  protected boolean releaseType;
  
  protected boolean status;
  
  protected boolean artist;
  
  protected boolean umlDates;
  
  protected boolean future1;
  
  protected boolean future2;
  
  protected boolean keyTask;
  
  protected boolean endDueDate;
  
  protected boolean beginDueDate;
  
  protected boolean parentsOnly;
  
  protected boolean scheduledReleases;
  
  protected boolean configuration;
  
  protected boolean addsMovesBoth;
  
  protected boolean subconfigFlag;
  
  protected int productType;
  
  protected boolean endEffectiveDate;
  
  protected boolean beginEffectiveDate;
  
  protected boolean physicalProductActivity;
  
  protected Calendar lastUpdatedDate;
  
  protected int lastUpdatingUser;
  
  protected long lastUpdatedCk;
  
  protected int order;
  
  protected boolean distCo;
  
  public Report(int id) {
    this.order = 0;
    this.reportID = id;
  }
  
  public Report() { this.order = 0; }
  
  public int getReportID() { return this.reportID; }
  
  public void setReportID(int reportID) { this.reportID = reportID; }
  
  public String getReportName() { return this.reportName; }
  
  public void setReportName(String reportName) { this.reportName = reportName; }
  
  public int getReportOwner() { return this.reportOwner; }
  
  public void setReportOwner(int reportOwner) { this.reportOwner = reportOwner; }
  
  public String getDescription() { return this.reportDescription; }
  
  public void setDescription(String reportDescription) { this.reportDescription = reportDescription; }
  
  public int getType() { return this.reportType; }
  
  public void setType(int reportType) { this.reportType = reportType; }
  
  public int getFormat() { return this.reportFormat; }
  
  public void setFormat(int reportFormat) { this.reportFormat = reportFormat; }
  
  public String getReportStatus() { return this.reportStatus; }
  
  public void setReportStatus(String reportStatus) { this.reportStatus = reportStatus; }
  
  public String getPath() { return this.reportPath; }
  
  public void setPath(String reportPath) { this.reportPath = reportPath; }
  
  public String getFileName() { return this.reportFileName; }
  
  public void setFileName(String reportFileName) { this.reportFileName = reportFileName; }
  
  public Calendar getLastUpdatedDate() { return this.lastUpdatedDate; }
  
  public void setLastUpdatedDate(Calendar lastUpdatedDate) { this.lastUpdatedDate = lastUpdatedDate; }
  
  public int getLastUpdatingUser() { return this.lastUpdatingUser; }
  
  public void setLastUpdatingUser(int lastUpdatingUser) { this.lastUpdatingUser = lastUpdatingUser; }
  
  public long getLastUpdatedCk() { return this.lastUpdatedCk; }
  
  public void setLastUpdatedCk(long lastUpdatedCk) { this.lastUpdatedCk = lastUpdatedCk; }
  
  public boolean getBeginDateFlag() { return this.beginDate; }
  
  public void setBeginDateFlag(boolean beginDate) { this.beginDate = beginDate; }
  
  public boolean getEndDateFlag() { return this.endDate; }
  
  public void setEndDateFlag(boolean endDate) { this.endDate = endDate; }
  
  public boolean getRegionFlag() { return this.region; }
  
  public void setRegionFlag(boolean region) { this.region = region; }
  
  public boolean getFamilyFlag() { return this.family; }
  
  public void setFamilyFlag(boolean family) { this.family = family; }
  
  public boolean getEnvironmentFlag() { return this.environment; }
  
  public void setEnvironmentFlag(boolean environment) { this.environment = environment; }
  
  public boolean getCompanyFlag() { return this.company; }
  
  public void setCompanyFlag(boolean company) { this.company = company; }
  
  public boolean getLabelFlag() { return this.label; }
  
  public void setLabelFlag(boolean label) { this.label = label; }
  
  public boolean getContactFlag() { return this.contact; }
  
  public void setContactFlag(boolean contact) { this.contact = contact; }
  
  public boolean getProductGroupCodeFlag() { return this.productCodeGroup; }
  
  public void setProductGroupCodeFlag(boolean productCodeGroup) { this.productCodeGroup = productCodeGroup; }
  
  public boolean getUMLContactFlag() { return this.umlContact; }
  
  public void setUMLContactFlag(boolean umlContact) { this.umlContact = umlContact; }
  
  public boolean getCompleteKeyTaskFlag() { return this.completeKeyTask; }
  
  public void setCompleteKeyTaskFlag(boolean completeKeyTask) { this.completeKeyTask = completeKeyTask; }
  
  public boolean getUmlKeyTaskFlag() { return this.umlKeyTask; }
  
  public void setUmlKeyTaskFlag(boolean umlKeyTask) { this.umlKeyTask = umlKeyTask; }
  
  public boolean getReleaseTypeFlag() { return this.releaseType; }
  
  public void setReleaseTypeFlag(boolean releaseType) { this.releaseType = releaseType; }
  
  public boolean getStatusFlag() { return this.status; }
  
  public void setStatusFlag(boolean status) { this.status = status; }
  
  public boolean getArtistFlag() { return this.artist; }
  
  public void setArtistFlag(boolean artist) { this.artist = artist; }
  
  public boolean getUmlDatesFlag() { return this.umlDates; }
  
  public void setUmlDatesFlag(boolean umlDates) { this.umlDates = umlDates; }
  
  public boolean getFuture1Flag() { return this.future1; }
  
  public void setFuture1Flag(boolean future1) { this.future1 = future1; }
  
  public boolean getFuture2Flag() { return this.future2; }
  
  public void setFuture2Flag(boolean future2) { this.future2 = future2; }
  
  public boolean getKeyTaskFlag() { return this.keyTask; }
  
  public void setKeyTaskFlag(boolean keyTask) { this.keyTask = keyTask; }
  
  public boolean getEndDueDateFlag() { return this.endDueDate; }
  
  public void setEndDueDateFlag(boolean endDueDate) { this.endDueDate = endDueDate; }
  
  public boolean getBeginDueDateFlag() { return this.beginDueDate; }
  
  public void setBeginDueDateFlag(boolean beginDueDate) { this.beginDueDate = beginDueDate; }
  
  public boolean getBeginEffectiveDateFlag() { return this.beginEffectiveDate; }
  
  public void setBeginEffectiveDateFlag(boolean beginEffectiveDate) { this.beginEffectiveDate = beginEffectiveDate; }
  
  public boolean getEndEffectiveDateFlag() { return this.endEffectiveDate; }
  
  public void setEndEffectiveDateFlag(boolean endEffectiveDate) { this.endEffectiveDate = endEffectiveDate; }
  
  public String getSubName() { return this.subName; }
  
  public void setSubName(String subName) { this.subName = subName; }
  
  public boolean getParentsOnlyFlag() { return this.parentsOnly; }
  
  public void setParentsOnlyFlag(boolean parentsOnly) { this.parentsOnly = parentsOnly; }
  
  public String getNotepadContentObjectId() { return Integer.toString(this.reportID); }
  
  public void setConfiguration(boolean configuration) { this.configuration = configuration; }
  
  public boolean getConfiguration() { return this.configuration; }
  
  public void setAddsMovesBoth(boolean addsMovesBoth) { this.addsMovesBoth = addsMovesBoth; }
  
  public boolean getAddsMovesBoth() { return this.addsMovesBoth; }
  
  public void setSubconfigFlag(boolean subconfigFlag) { this.subconfigFlag = subconfigFlag; }
  
  public boolean getSubconfigFlag() { return this.subconfigFlag; }
  
  public boolean getScheduledReleasesFlag() { return this.scheduledReleases; }
  
  public void setScheduledReleasesFlag(boolean scheduledReleases) { this.scheduledReleases = scheduledReleases; }
  
  public void setProductType(int productTypeInt) { this.productType = productTypeInt; }
  
  public int getProductType() { return this.productType; }
  
  public boolean getPhysicalProductActivity() { return this.physicalProductActivity; }
  
  public void setPhysicalProductActivity(boolean val) { this.physicalProductActivity = val; }
  
  public int compareTo(Object report) throws ClassCastException { return compareByOrder(report); }
  
  protected int compareByOrder(Object report) throws ClassCastException {
    Integer thisReport = new Integer(this.order);
    Integer thatReport = new Integer(((Report)report).order);
    return thisReport.compareTo(thatReport);
  }
  
  public boolean getDistCo() { return this.distCo; }
  
  public void setDistCo(boolean distCo) { this.distCo = distCo; }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\Report.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */