package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.EnhancedProperties;
import com.techempower.gemini.Context;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.Cache;
import com.universal.milestone.Day;
import com.universal.milestone.Family;
import com.universal.milestone.JdbcConnector;
import com.universal.milestone.MilestoneConstants;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.Notepad;
import com.universal.milestone.ProductCategory;
import com.universal.milestone.ReleaseType;
import com.universal.milestone.SelectionManager;
import com.universal.milestone.Task;
import com.universal.milestone.TaskManager;
import java.util.Vector;

public class TaskManager implements MilestoneConstants {
  public static final String COMPONENT_CODE = "mTsk";
  
  public static final String DEFAULT_ORDER = " ORDER BY a.[name]";
  
  protected static TaskManager taskManager = null;
  
  protected static ComponentLog log;
  
  public static void configure(EnhancedProperties props, GeminiApplication application) { log = application.getLog("mTsk"); }
  
  public static TaskManager getInstance() {
    if (taskManager == null)
      taskManager = new TaskManager(); 
    return taskManager;
  }
  
  public Task getTask(int id, boolean getTimestamp) {
    String taskQuery = "SELECT * FROM vi_Task WHERE task_id = " + 
      
      id + 
      ";";
    Task task = null;
    JdbcConnector connector = MilestoneHelper.getConnector(taskQuery);
    connector.runQuery();
    if (connector.more()) {
      task = new Task();
      task.setTaskID(connector.getIntegerField("task_id"));
      task.setTaskName(connector.getField("name", ""));
      task.setComments(connector.getField("comments", ""));
      task.setIsKeyTask(connector.getBoolean("key_task_indicator"));
      task.setActiveFlag(connector.getBoolean("active_flag"));
      task.setWeeksToRelease(connector.getIntegerField("weeks_to_release"));
      task.setDayOfTheWeek(new Day(connector.getIntegerField("day_of_week")));
      task.setWeekAdjustment(connector.getIntegerField("week_adjustment"));
      String dept = "";
      if (connector.getFieldByName("department") != null)
        dept = connector.getFieldByName("department"); 
      task.setDepartment(dept);
      String category = "";
      if (connector.getFieldByName("reporting_category") != null)
        category = connector.getFieldByName("reporting_category"); 
      task.setCategory(category);
      if (connector.getIntegerField("owner") > 0)
        task.setOwner((Family)MilestoneHelper.getStructureObject(connector.getIntegerField("owner"))); 
      task.setAllowMultCompleteDatesFlag(connector.getBoolean("allow_mult_complete_dates_flag"));
      task.setTaskAbbreviation(connector.getIntegerField("abbrev_id"));
      task.setTaskAbbrStr(MilestoneHelper.getTaskAbbreivationNameById(connector.getIntegerField("abbrev_id")));
      String desc = "";
      if (connector.getFieldByName("description") != null)
        desc = connector.getFieldByName("description"); 
      task.setTaskDescription(desc);
      String templateListQuery = "SELECT * FROM vi_Template_Header WHERE template_id IN (SELECT template_id FROM vi_Template_Detail  WHERE task_id = " + 
        
        task.getTaskID() + ")" + 
        ";";
      JdbcConnector templateConnector = MilestoneHelper.getConnector(templateListQuery);
      templateConnector.runQuery();
      Vector precache = new Vector();
      while (templateConnector.more()) {
        String templateInfo = new String(templateConnector.getField("name", ""));
        templateInfo = String.valueOf(templateInfo) + "&nbsp;:&nbsp;";
        if (templateConnector.getField("product_line", "").length() > 0)
          templateInfo = String.valueOf(templateInfo) + ((ProductCategory)SelectionManager.getLookupObject(templateConnector.getField("product_line", ""), Cache.getProductCategories())).getName(); 
        templateInfo = String.valueOf(templateInfo) + "/";
        if (templateConnector.getField("release_type", "").length() > 0)
          templateInfo = String.valueOf(templateInfo) + ((ReleaseType)SelectionManager.getLookupObject(templateConnector.getField("release_type", ""), Cache.getReleaseTypes())).getName(); 
        templateInfo = String.valueOf(templateInfo) + "/";
        templateInfo = String.valueOf(templateInfo) + templateConnector.getField("configuration", "");
        templateInfo = String.valueOf(templateInfo) + "/";
        templateInfo = String.valueOf(templateInfo) + templateConnector.getField("sub_configuration", "");
        precache.addElement(templateInfo);
        templateInfo = null;
        templateConnector.next();
      } 
      task.setTemplates(precache);
      templateConnector.close();
      String lastDateString = connector.getFieldByName("last_updated_on");
      if (lastDateString != null)
        task.setLastUpdateDate(MilestoneHelper.getDatabaseDate(lastDateString)); 
      task.setLastUpdatingUser(connector.getIntegerField("last_updated_by"));
      if (getTimestamp) {
        long lastUpdatedLong = Long.parseLong(connector.getField("last_updated_ck"), 16);
        task.setLastUpdatedCk(lastUpdatedLong);
      } 
    } 
    connector.close();
    return task;
  }
  
  public Task getNotepadTask(JdbcConnector connector) {
    Task task = null;
    if (connector != null) {
      task = new Task();
      task.setTaskID(connector.getIntegerField("task_id"));
      task.setTaskName(connector.getField("name"));
      task.setWeeksToRelease(connector.getIntegerField("weeks_to_release"));
      task.setDayOfTheWeek(new Day(connector.getIntegerField("day_of_week")));
      String dept = "";
      if (connector.getFieldByName("department") != null)
        dept = connector.getFieldByName("department"); 
      if (dept.equals("-1"))
        dept = ""; 
      if (!dept.equals(""))
        dept = Cache.getDepartmentSubDescription(dept); 
      task.setDepartment(dept);
      task.setOwner((Family)MilestoneHelper.getStructureObject(connector.getIntegerField("owner")));
      task.setIsKeyTask(connector.getBoolean("key_task_indicator"));
    } 
    return task;
  }
  
  public Task saveTask(Task task, int userID) {
    log.debug("Task save query:\n" + task);
    long timestamp = task.getLastUpdatedCk();
    int isKeyTask = 0;
    if (task.getIsKeyTask())
      isKeyTask = 1; 
    int isActiveFlag = 0;
    if (task.getActiveFlag())
      isActiveFlag = 1; 
    task.flushAudits(userID);
    int ownerId = -1;
    if (task.getOwner() != null)
      ownerId = task.getOwner().getStructureID(); 
    int dayId = 0;
    if (task.getDayOfTheWeek() != null)
      dayId = task.getDayOfTheWeek().getDayID(); 
    if (dayId == -1)
      dayId = 0; 
    int allowMultCompleteDates = task.getAllowMultCompleteDatesFlag() ? 1 : 0;
    String query = "sp_sav_Task " + 
      task.getTaskID() + "," + 
      "'" + MilestoneHelper.escapeSingleQuotes(task.getTaskName()) + "'," + 
      "'" + MilestoneHelper.escapeSingleQuotes(task.getTaskDescription()) + "'," + 
      task.getTaskAbbreviation() + "," + 
      dayId + "," + 
      task.getWeeksToRelease() + "," + 
      "'" + task.getDepartment() + "'," + 
      "'" + MilestoneHelper.escapeSingleQuotes(task.getCategory()) + "'," + 
      isKeyTask + "," + 
      '\001' + "," + 
      isActiveFlag + "," + 
      ownerId + "," + 
      task.getWeekAdjustment() + "," + 
      "'" + MilestoneHelper.escapeSingleQuotes(task.getComments()) + "'," + 
      allowMultCompleteDates + "," + 
      task.getLastUpdatingUser() + "," + 
      timestamp;
    log.debug("Save task query:\n" + query);
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    if (connector.getIntegerField("ReturnId") > 0)
      task.setTaskID(connector.getIntegerField("ReturnId")); 
    connector.close();
    String timestampQuery = "SELECT last_updated_ck, last_updated_on FROM vi_task WHERE task_id = " + 
      
      task.getTaskID() + 
      ";";
    JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
    connectorTimestamp.runQuery();
    if (connectorTimestamp.more()) {
      task.setLastUpdatedCk(Long.parseLong(connectorTimestamp.getField("last_updated_ck"), 16));
      task.setLastUpdateDate(MilestoneHelper.getDatabaseDate(connectorTimestamp.getField("last_updated_on")));
    } 
    connectorTimestamp.close();
    return task;
  }
  
  public void deleteTask(Task task, int userID) {
    String query = "sp_del_Task " + 
      task.getTaskID();
    log.debug("Delete task query:\n" + query);
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    connector.close();
  }
  
  public boolean isDuplicateTask(Task task) {
    boolean isDuplicate = false;
    if (task != null) {
      int ownerId = -1;
      if (task.getOwner() != null)
        ownerId = task.getOwner().getStructureID(); 
      String query = "SELECT * FROM vi_task WHERE  name = '" + 
        MilestoneHelper.escapeSingleQuotes(task.getTaskName()) + "'" + 
        " AND weeks_to_release = " + task.getWeeksToRelease() + 
        " AND day_of_week = " + task.getDayOfTheWeek().getDayID() + 
        " AND task_id <> " + task.getTaskID() + 
        " AND owner = " + ownerId;
      log.debug("TTTTTTTTTTTTTTTT duplicate query = " + query);
      JdbcConnector connector = MilestoneHelper.getConnector(query);
      connector.runQuery();
      if (connector.more())
        isDuplicate = true; 
      connector.close();
    } 
    return isDuplicate;
  }
  
  public void setNotepadQuery(Context context, int UserId, Notepad notepad) {
    if (notepad != null) {
      boolean activeFlag = true;
      if (context.getParameter("inactiveSrch") != null)
        activeFlag = false; 
      String query = getDefaultSearchQuery(context, activeFlag);
      String taskNameSrch = context.getParameter("taskNameSrch");
      String taskAbbrevSrch = context.getParameter("taskAbbrevSrch");
      String ownerSrch = context.getParameter("ownerSrch");
      String departmentSrch = context.getParameter("departmentSrch");
      if (context.getParameter("keyTaskSrch") != null)
        query = String.valueOf(query) + MilestoneHelper.addQueryParams(query, " a.[key_task_indicator] = 1"); 
      if (taskNameSrch != null && !taskNameSrch.equals(""))
        query = String.valueOf(query) + MilestoneHelper.addQueryParams(query, " a.[name] " + MilestoneHelper.setWildCardsEscapeSingleQuotes(taskNameSrch)); 
      if (MilestoneHelper.isStringNotEmpty(taskAbbrevSrch)) {
        int abbrevId = MilestoneHelper.getTaskAbbreviationID(taskAbbrevSrch);
        query = String.valueOf(query) + MilestoneHelper.addQueryParams(query, " a.[abbrev_id] = " + abbrevId);
      } 
      if (ownerSrch != null && !ownerSrch.equals("0"))
        query = String.valueOf(query) + MilestoneHelper.addQueryParams(query, " a.[owner] like '" + ownerSrch + "'"); 
      if (departmentSrch != null && !departmentSrch.equals("0") && !departmentSrch.equals("-1"))
        query = String.valueOf(query) + MilestoneHelper.addQueryParams(query, " a.[department] like '%" + departmentSrch + "%'"); 
      if (context.getParameter("inactiveSrch") != null) {
        query = String.valueOf(query) + MilestoneHelper.addQueryParams(query, " a.[active_flag] <> 1");
      } else {
        query = String.valueOf(query) + MilestoneHelper.addQueryParams(query, " a.[active_flag] = 1");
      } 
      String allowMCDFlag = context.getParameter("allowMultCompleteDatesSrch");
      if (allowMCDFlag != null)
        if (allowMCDFlag.equals("1")) {
          query = String.valueOf(query) + MilestoneHelper.addQueryParams(query, " a.[allow_mult_complete_dates_flag] = 1");
        } else if (allowMCDFlag.equals("0")) {
          query = String.valueOf(query) + MilestoneHelper.addQueryParams(query, " a.[allow_mult_complete_dates_flag] = 0");
        }  
      notepad.setSearchQuery(query);
      notepad.setOrderBy(" ORDER BY a.[name]");
    } 
  }
  
  public Vector getTaskNotepadList(Context context, Notepad notepad) {
    String query = "";
    if (notepad != null && !notepad.getSearchQuery().equals("")) {
      query = notepad.getSearchQuery();
      query = String.valueOf(query) + notepad.getOrderBy();
    } else {
      query = String.valueOf(getDefaultSearchQuery(context, true)) + " ORDER BY a.[name]";
    } 
    log.debug("++++++++++++++++++++ query " + query);
    Task task = null;
    Vector precache = new Vector();
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    while (connector.more()) {
      task = getNotepadTask(connector);
      precache.addElement(task);
      task = null;
      connector.next();
    } 
    connector.close();
    return precache;
  }
  
  public static String getDefaultSearchQuery(Context context, boolean activeFlag) {
    StringBuffer query = new StringBuffer();
    query.append("SELECT * FROM vi_Task a LEFT JOIN vi_structure b on a.[owner] = b.[structure_id] ");
    query.append(MilestoneHelper.getOwnerCompanyWhereClause(context));
    if (MilestoneHelper.getOwnerCompanyWhereClause(context).length() > 0) {
      query.append(" AND ");
    } else {
      query.append(" WHERE ");
    } 
    if (activeFlag) {
      query.append(" a.[active_flag]  = 1 ");
    } else {
      query.append(" a.[active_flag] <> 1 ");
    } 
    return query.toString();
  }
  
  public boolean isTimestampValid(Task task) {
    if (task != null) {
      String timestampQuery = "SELECT last_updated_ck  FROM vi_task WHERE task_id = " + 
        
        task.getTaskID() + 
        ";";
      JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
      connectorTimestamp.runQuery();
      if (connectorTimestamp.more())
        if (task.getLastUpdatedCk() != Long.parseLong(connectorTimestamp.getField("last_updated_ck", ""), 16)) {
          connectorTimestamp.close();
          return false;
        }  
      connectorTimestamp.close();
      return true;
    } 
    return false;
  }
  
  public boolean isOnMultipleTemplates(Task task) {
    if (task != null) {
      String query = "SELECT *  FROM vi_Template_Detail WHERE task_id = " + 
        
        task.getTaskID() + 
        ";";
      JdbcConnector connector = MilestoneHelper.getConnector(query);
      connector.setForwardOnly(false);
      connector.runQuery();
      if (connector.more())
        if (connector.getRowCount() > 1) {
          connector.close();
          return true;
        }  
      connector.close();
    } 
    return false;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\TaskManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */