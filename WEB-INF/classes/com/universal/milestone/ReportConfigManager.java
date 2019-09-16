package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.EnhancedProperties;
import com.techempower.gemini.Context;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.Environment;
import com.universal.milestone.JdbcConnector;
import com.universal.milestone.MilestoneConstants;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.Notepad;
import com.universal.milestone.ProductionGroupCode;
import com.universal.milestone.Report;
import com.universal.milestone.ReportConfigManager;
import com.universal.milestone.Template;
import com.universal.milestone.User;
import com.universal.milestone.UserManager;
import java.util.Collections;
import java.util.Vector;

public class ReportConfigManager implements MilestoneConstants {
  protected static ReportConfigManager reportConfigManager = null;
  
  public static final String COMPONENT_CODE = "rcfm";
  
  public static final String DEFAULT_QUERY = "SELECT DISTINCT report_id, report_name, file_name, description, report_status, report_owner FROM vi_Report_Config";
  
  public static final String DEFAULT_ORDER = " ORDER BY report_name";
  
  protected static ComponentLog log;
  
  public static void configure(EnhancedProperties props, GeminiApplication application) { log = application.getLog("rcfm"); }
  
  public static ReportConfigManager getInstance() {
    if (reportConfigManager == null)
      reportConfigManager = new ReportConfigManager(); 
    return reportConfigManager;
  }
  
  public Report getReportConfig(int reportId, boolean getTimestamp) {
    String reportConfigQuery = "SELECT report_id, type, description,        report_path, file_name,subreport_name, report_format, report_owner,        flag_begin_str_date, flag_end_str_date, flag_region,        flag_family, flag_environment, flag_company, flag_label,flag_contact,        flag_complete_task, flag_task_owner,        flag_key_task, flag_artist,        flag_release_type, flag_status, flag_roll_up, flag_future_2, flag_future_1 ,       report_status, report_name, last_updated_by, last_updated_on,        last_updated_ck, flag_begin_due_date, flag_end_due_date, flag_begin_effective_date,        flag_end_effective_date, flag_config, flag_scheduled_releases,        flag_adds_moves_both, flag_subconfig,        flag_product_type, flag_physical_product_activity,        flag_dist_co, flag_production_group_code  FROM vi_Report_Config   WHERE ( report_id = " + 
      
      reportId + ") ORDER BY description ";
    Report reportConfig = null;
    JdbcConnector connector = MilestoneHelper.getConnector(reportConfigQuery);
    connector.runQuery();
    if (connector.more()) {
      reportConfig = new Report(connector.getIntegerField("report_id"));
      reportConfig.setDescription(connector.getField("description"));
      reportConfig.setType(connector.getIntegerField("type"));
      reportConfig.setFormat(connector.getIntegerField("report_format"));
      reportConfig.setReportName(connector.getField("report_name"));
      reportConfig.setSubName(connector.getField("subreport_name"));
      reportConfig.setReportStatus(connector.getField("report_status"));
      reportConfig.setPath(connector.getField("report_path"));
      reportConfig.setFileName(connector.getField("file_name"));
      reportConfig.setSubName(connector.getField("subreport_name"));
      reportConfig.setReportOwner(connector.getIntegerField("report_owner"));
      reportConfig.setBeginDateFlag(connector.getBoolean("flag_begin_str_date"));
      reportConfig.setEndDateFlag(connector.getBoolean("flag_end_str_date"));
      reportConfig.setRegionFlag(connector.getBoolean("flag_region"));
      reportConfig.setFamilyFlag(connector.getBoolean("flag_family"));
      reportConfig.setEnvironmentFlag(connector.getBoolean("flag_environment"));
      reportConfig.setCompanyFlag(connector.getBoolean("flag_company"));
      reportConfig.setLabelFlag(connector.getBoolean("flag_label"));
      reportConfig.setContactFlag(connector.getBoolean("flag_contact"));
      reportConfig.setCompleteKeyTaskFlag(connector.getBoolean("flag_complete_task"));
      reportConfig.setUmlKeyTaskFlag(connector.getBoolean("flag_task_owner"));
      reportConfig.setReleaseTypeFlag(connector.getBoolean("flag_release_type"));
      reportConfig.setStatusFlag(connector.getBoolean("flag_status"));
      reportConfig.setArtistFlag(connector.getBoolean("flag_artist"));
      reportConfig.setFuture1Flag(connector.getBoolean("flag_future_1"));
      reportConfig.setFuture2Flag(connector.getBoolean("flag_future_2"));
      reportConfig.setKeyTaskFlag(connector.getBoolean("flag_key_task"));
      reportConfig.setEndDueDateFlag(connector.getBoolean("flag_end_due_date"));
      reportConfig.setBeginDueDateFlag(connector.getBoolean("flag_begin_due_date"));
      reportConfig.setEndDueDateFlag(connector.getBoolean("flag_end_due_date"));
      reportConfig.setParentsOnlyFlag(connector.getBoolean("flag_roll_up"));
      reportConfig.setSubName(connector.getField("subreport_name"));
      reportConfig.setBeginEffectiveDateFlag(connector.getBoolean("flag_begin_effective_date"));
      reportConfig.setEndEffectiveDateFlag(connector.getBoolean("flag_end_effective_date"));
      String lastDateString = connector.getField("last_updated_on");
      if (!lastDateString.equalsIgnoreCase("[none]"))
        reportConfig.setLastUpdatedDate(MilestoneHelper.getDatabaseDate(lastDateString)); 
      reportConfig.setLastUpdatingUser(connector.getIntegerField("last_updated_by"));
      if (getTimestamp) {
        long lastUpdatedLong = Long.parseLong(connector.getField("last_updated_ck"), 16);
        reportConfig.setLastUpdatedCk(lastUpdatedLong);
      } 
      reportConfig.setConfiguration(connector.getBoolean("flag_config"));
      reportConfig.setScheduledReleasesFlag(connector.getBoolean("flag_scheduled_releases"));
      reportConfig.setAddsMovesBoth(connector.getBoolean("flag_adds_moves_both"));
      reportConfig.setPhysicalProductActivity(connector.getBoolean("flag_physical_product_activity"));
      reportConfig.setSubconfigFlag(connector.getBoolean("flag_subconfig"));
      reportConfig.setProductType(connector.getIntegerField("flag_product_type"));
      reportConfig.setDistCo(connector.getBoolean("flag_dist_co"));
      reportConfig.setProductGroupCodeFlag(connector.getBoolean("flag_production_group_code"));
      connector.close();
      return reportConfig;
    } 
    try {
      connector.close();
    } catch (Exception exception) {}
    return null;
  }
  
  private Report getNotepadReportConfig(JdbcConnector connector) {
    Report reportConfig = null;
    if (connector != null) {
      reportConfig = new Report(connector.getIntegerField("report_id"));
      reportConfig.setReportName(connector.getFieldByName("report_name"));
      reportConfig.setDescription(connector.getFieldByName("description"));
      reportConfig.setFileName(connector.getFieldByName("file_name"));
    } 
    return reportConfig;
  }
  
  public Report saveReportConfig(Report reportConfig, int userID) {
    long timestamp = reportConfig.getLastUpdatedCk();
    int beginDate = 0;
    if (reportConfig.getBeginDateFlag())
      beginDate = 1; 
    int endDate = 0;
    if (reportConfig.getEndDateFlag())
      endDate = 1; 
    int region = 0;
    if (reportConfig.getRegionFlag())
      region = 1; 
    int family = 0;
    if (reportConfig.getFamilyFlag())
      family = 1; 
    int environment = 0;
    if (reportConfig.getEnvironmentFlag())
      environment = 1; 
    int company = 0;
    if (reportConfig.getCompanyFlag())
      company = 1; 
    int label = 0;
    if (reportConfig.getLabelFlag())
      label = 1; 
    int contact = 0;
    if (reportConfig.getContactFlag())
      contact = 1; 
    int complete = 0;
    if (reportConfig.getCompleteKeyTaskFlag())
      complete = 1; 
    int taskOwner = 0;
    if (reportConfig.getUmlKeyTaskFlag())
      taskOwner = 1; 
    int umlKeyTask = 0;
    if (reportConfig.getKeyTaskFlag())
      umlKeyTask = 1; 
    int artist = 0;
    if (reportConfig.getArtistFlag())
      artist = 1; 
    int releaseType = 0;
    if (reportConfig.getReleaseTypeFlag())
      releaseType = 1; 
    int status = 0;
    if (reportConfig.getStatusFlag())
      status = 1; 
    int parentsOnly = 0;
    if (reportConfig.getParentsOnlyFlag())
      parentsOnly = 1; 
    int beginDueDate = 0;
    if (reportConfig.getBeginDueDateFlag())
      beginDueDate = 1; 
    int endDueDate = 0;
    if (reportConfig.getEndDueDateFlag())
      endDueDate = 1; 
    int future2 = 0;
    if (reportConfig.getFuture2Flag())
      future2 = 1; 
    int future1 = 0;
    if (reportConfig.getFuture1Flag())
      future1 = 1; 
    int beginEffectiveDate = 0;
    if (reportConfig.getBeginEffectiveDateFlag())
      beginEffectiveDate = 1; 
    int endEffectiveDate = 0;
    if (reportConfig.getEndEffectiveDateFlag())
      endEffectiveDate = 1; 
    int config = 0;
    if (reportConfig.getConfiguration())
      config = 1; 
    int scheduledReleases = 0;
    if (reportConfig.getScheduledReleasesFlag())
      scheduledReleases = 1; 
    int addsMovesBoth = 0;
    if (reportConfig.getAddsMovesBoth())
      addsMovesBoth = 1; 
    int physicalProductActivity = 0;
    if (reportConfig.getPhysicalProductActivity())
      physicalProductActivity = 1; 
    int distCo = reportConfig.getDistCo() ? 1 : 0;
    int subconfig = 0;
    if (reportConfig.getSubconfigFlag())
      subconfig = 1; 
    String query = "sp_sav_Report_Config " + 
      reportConfig.getReportID() + "," + 
      reportConfig.getType() + ",'" + 
      MilestoneHelper.escapeSingleQuotes(reportConfig.getReportName()) + "','" + 
      reportConfig.getDescription() + "','" + 
      MilestoneHelper.escapeSingleQuotes(reportConfig.getPath()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(reportConfig.getFileName()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(reportConfig.getSubName()) + "'," + 
      reportConfig.getFormat() + "," + 
      reportConfig.getReportOwner() + "," + 
      
      beginDate + "," + 
      endDate + "," + 
      region + "," + 
      family + "," + 
      environment + "," + 
      company + "," + 
      label + "," + 
      contact + "," + 
      complete + "," + 
      taskOwner + "," + 
      umlKeyTask + "," + 
      artist + "," + 
      releaseType + "," + 
      status + "," + 
      parentsOnly + "," + 
      beginDueDate + "," + 
      endDueDate + "," + 
      beginEffectiveDate + "," + 
      endEffectiveDate + "," + 
      future2 + "," + 
      future1 + ",'" + 
      reportConfig.getReportStatus() + "'," + 
      reportConfig.getLastUpdatingUser() + "," + 
      timestamp + "," + 
      config + "," + 
      scheduledReleases + "," + 
      addsMovesBoth + "," + 
      subconfig + "," + 
      reportConfig.getProductType() + "," + 
      physicalProductActivity + "," + 
      distCo;
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    if (connector.getIntegerField("ReturnId") > 0)
      reportConfig.setReportID(connector.getIntegerField("ReturnId")); 
    connector.close();
    String timestampQuery = "Select last_updated_ck, last_updated_on from vi_Report_Config where report_id = " + reportConfig.getReportID();
    JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
    connectorTimestamp.runQuery();
    if (connectorTimestamp.more()) {
      reportConfig.setLastUpdatedCk(Long.parseLong(connectorTimestamp.getField("last_updated_ck"), 16));
      reportConfig.setLastUpdatedDate(MilestoneHelper.getDatabaseDate(connectorTimestamp.getField("last_updated_on")));
    } 
    connectorTimestamp.close();
    return reportConfig;
  }
  
  public void deleteReportConfig(Report reportConfig, int userID) {
    String query = "sp_del_Report_Config " + 
      reportConfig.getReportID();
    JdbcConnector connector1 = MilestoneHelper.getConnector(query);
    connector1.runQuery();
    connector1.close();
  }
  
  public void setNotepadQuery(Context context, int UserId, Notepad notepad) {
    if (notepad != null) {
      String reportNameSearch = context.getParameter("ReportNameSearch");
      String fileNameSearch = context.getParameter("FileNameSearch");
      String reportConfigQuery = "SELECT DISTINCT report_id, report_name, file_name, description FROM vi_Report_Config";
      if (MilestoneHelper.isStringNotEmpty(reportNameSearch))
        reportConfigQuery = String.valueOf(reportConfigQuery) + MilestoneHelper.addQueryParams(reportConfigQuery, " report_name " + MilestoneHelper.setWildCardsEscapeSingleQuotes(reportNameSearch)); 
      if (MilestoneHelper.isStringNotEmpty(fileNameSearch))
        reportConfigQuery = String.valueOf(reportConfigQuery) + MilestoneHelper.addQueryParams(reportConfigQuery, " file_name " + MilestoneHelper.setWildCardsEscapeSingleQuotes(fileNameSearch)); 
      String order = " ORDER BY report_name";
      notepad.setSearchQuery(reportConfigQuery);
      notepad.setOrderBy(order);
    } 
  }
  
  public Vector getReportConfigNotepadList(Notepad notepad) {
    String reportConfigQuery = "";
    if (notepad != null && !notepad.getSearchQuery().equals("")) {
      reportConfigQuery = notepad.getSearchQuery();
      reportConfigQuery = String.valueOf(reportConfigQuery) + notepad.getOrderBy();
    } else {
      reportConfigQuery = "SELECT DISTINCT report_id, report_name, file_name, description, report_status, report_owner FROM vi_Report_Config ORDER BY report_name";
    } 
    Report reportConfig = null;
    Vector precache = new Vector();
    JdbcConnector connector = MilestoneHelper.getConnector(reportConfigQuery);
    connector.runQuery();
    while (connector.more()) {
      reportConfig = getNotepadReportConfig(connector);
      precache.addElement(reportConfig);
      reportConfig = null;
      connector.next();
    } 
    connector.close();
    return precache;
  }
  
  public Vector getReportNotepadList(Notepad notepad, Context context) {
    String reportQuery = "";
    Vector environments = MilestoneHelper.getUserEnvironments(context);
    String familyWhere = "";
    familyWhere = String.valueOf(familyWhere) + " AND ( report_owner IS NULL OR  report_owner = 0 OR report_owner = -1 ";
    for (int i = 0; i < environments.size(); i++) {
      Environment environment = (Environment)environments.get(i);
      familyWhere = String.valueOf(familyWhere) + " OR report_owner = " + environment.getParent().getStructureID();
    } 
    familyWhere = String.valueOf(familyWhere) + " )";
    if (notepad != null && !notepad.getSearchQuery().equals("")) {
      reportQuery = String.valueOf(notepad.getSearchQuery()) + familyWhere;
      reportQuery = String.valueOf(reportQuery) + notepad.getOrderBy();
    } else {
      reportQuery = "SELECT DISTINCT report_id, report_name, file_name, description, report_status, report_owner FROM vi_Report_Config where report_status = 'ACTIVE'" + familyWhere + " ORDER By Description";
    } 
    Report reportConfig = null;
    Vector precache = new Vector();
    JdbcConnector connector = MilestoneHelper.getConnector(reportQuery);
    connector.runQuery();
    while (connector.more()) {
      reportConfig = getNotepadReportConfig(connector);
      int owner = connector.getInt("report_owner", 0);
      if (owner == 12)
        reportConfig.order = owner; 
      precache.addElement(reportConfig);
      reportConfig = null;
      connector.next();
    } 
    connector.close();
    Collections.sort(precache);
    return precache;
  }
  
  public boolean isTimestampValid(Report reportConfig) {
    if (reportConfig != null) {
      String timestampQuery = "Select last_updated_ck, last_updated_on from vi_Report_Config where report_id = " + reportConfig.getReportID();
      JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
      connectorTimestamp.runQuery();
      if (connectorTimestamp.more())
        if (reportConfig.getLastUpdatedCk() != Long.parseLong(connectorTimestamp.getField("last_updated_ck", ""), 16)) {
          connectorTimestamp.close();
          return false;
        }  
      connectorTimestamp.close();
      return true;
    } 
    return false;
  }
  
  public static Vector getLabelContacts() {
    labelUsers = new Vector();
    String query = "SELECT DISTINCT vi_All_User.full_Name as name, vi_All_User.User_Id FROM vi_All_User WITH (NOLOCK), vi_User_Company WITH (NOLOCK) WHERE (vi_All_User.User_ID = vi_User_Company.User_ID) AND (menu_access LIKE '2%') AND (vi_All_User.employed_by IS NOT NULL) AND (vi_All_User.employed_by <> 12) AND (vi_All_User.employed_by <> 0) AND (vi_All_user.employed_by <> -1) AND (vi_All_User.employed_by <> 417) AND exists (select top 1 'x' from dbo.release_header rh WITH (NOLOCK) where rh.contact_id = vi_All_User.[user_id]) ORDER BY Name;";
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    while (connector.more()) {
      User labelUser = UserManager.getInstance().getUser(connector.getIntegerField("user_id"));
      labelUsers.add(labelUser);
      connector.next();
    } 
    connector.close();
    return labelUsers;
  }
  
  public static Vector getATLASProductionGroupCodes() {
    productionGroupCodes = new Vector();
    String query = "SELECT id, name FROM [atlas].[archimedes].[dbo].[tblProductionGroupCode] ORDER BY name";
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    while (connector.more()) {
      ProductionGroupCode productionGroupCode = new ProductionGroupCode();
      productionGroupCode.setId(connector.getIntegerField("id"));
      productionGroupCode.setName(connector.getField("name"));
      productionGroupCodes.addElement(productionGroupCode);
      productionGroupCode = null;
      connector.next();
    } 
    connector.close();
    return productionGroupCodes;
  }
  
  public static Vector getUmlContacts() {
    labelUsers = new Vector();
    String query = "SELECT DISTINCT vi_User.full_Name as name, vi_User.User_Id FROM vi_User, vi_User_Company WHERE (vi_User.User_ID = vi_User_Company.User_ID) AND (vi_User.employed_by IS NOT NULL) AND (vi_User.employed_by = 417) AND (vi_User.employed_by <> 0) AND (vi_user.employed_by <> -1) ORDER BY Name;";
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    while (connector.more()) {
      User labelUser = UserManager.getInstance().getUser(connector.getIntegerField("user_id"));
      labelUsers.add(labelUser);
      connector.next();
    } 
    connector.close();
    return labelUsers;
  }
  
  public boolean isDuplicate(Report reportConfig) {
    boolean isDuplicate = false;
    if (reportConfig != null) {
      String query = "SELECT * FROM vi_report_config  WHERE report_name = '" + 
        MilestoneHelper.escapeSingleQuotes(reportConfig.getReportName()) + "'";
      JdbcConnector connector = MilestoneHelper.getConnector(query);
      connector.runQuery();
      if (connector.more())
        isDuplicate = true; 
      connector.close();
    } 
    return isDuplicate;
  }
  
  public static Vector getTemplateNames(Context context) {
    Vector templates = new Vector();
    String query = "SELECT * FROM vi_Template_Header a LEFT JOIN vi_structure b \ton a.[owner] = b.[structure_id] ";
    query = String.valueOf(query) + MilestoneHelper.getOwnerCompanyWhereClause(context);
    query = String.valueOf(query) + " ORDER BY a.owner, a.name ";
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    while (connector.more()) {
      Template template = new Template();
      template.setTemplateID(connector.getIntegerField("template_id"));
      template.setTemplateName(connector.getField("name", ""));
      templates.add(template);
      template = null;
      connector.next();
    } 
    connector.close();
    return templates;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\ReportConfigManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */