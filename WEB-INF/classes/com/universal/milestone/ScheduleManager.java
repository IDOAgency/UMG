package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.EnhancedProperties;
import com.techempower.gemini.Context;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.Cache;
import com.universal.milestone.Company;
import com.universal.milestone.CompanyAcl;
import com.universal.milestone.DatePeriod;
import com.universal.milestone.Day;
import com.universal.milestone.Family;
import com.universal.milestone.JdbcConnector;
import com.universal.milestone.MilestoneConstants;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.MultCompleteDate;
import com.universal.milestone.Notepad;
import com.universal.milestone.Schedule;
import com.universal.milestone.ScheduleManager;
import com.universal.milestone.ScheduledTask;
import com.universal.milestone.Selection;
import com.universal.milestone.SelectionManager;
import com.universal.milestone.SelectionStatus;
import com.universal.milestone.Task;
import com.universal.milestone.Template;
import com.universal.milestone.TemplateManager;
import com.universal.milestone.User;
import java.util.Calendar;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Vector;

public class ScheduleManager implements MilestoneConstants {
  public static final String COMPONENT_CODE = "mSched";
  
  public static final String DEFAULT_TASK_ORDER = " ORDER BY name";
  
  protected static ScheduleManager scheduleManager = null;
  
  protected static ComponentLog log;
  
  public static void configure(EnhancedProperties props, GeminiApplication application) { log = application.getLog("mSched"); }
  
  public static ScheduleManager getInstance() {
    if (scheduleManager == null)
      scheduleManager = new ScheduleManager(); 
    return scheduleManager;
  }
  
  public Schedule getSchedule(int id) {
    String scheduledtasksQuery = "sp_get_Schedule " + id;
    JdbcConnector connector = MilestoneHelper.getConnector(scheduledtasksQuery);
    connector.runQuery();
    if (connector.more()) {
      Schedule schedule = new Schedule();
      schedule.setSelectionID(id);
      Vector precache = new Vector();
      ScheduledTask scheduledTask = null;
      while (connector.more()) {
        scheduledTask = new ScheduledTask();
        scheduledTask.setReleaseID(connector.getIntegerField("release_Id"));
        scheduledTask.setTaskID(connector.getIntegerField("task_id"));
        scheduledTask.setOwner((Family)MilestoneHelper.getStructureObject(connector.getIntegerField("owner")));
        int weeks = connector.getIntegerField("weeks_to_release");
        if (weeks > 0 || weeks == -10)
          scheduledTask.setWeeksToRelease(connector.getIntegerField("weeks_to_release")); 
        String dueDateString = connector.getField("due_date", "");
        if (dueDateString.length() > 0)
          scheduledTask.setDueDate(MilestoneHelper.getDatabaseDate(dueDateString)); 
        String completionDateString = connector.getField("completion_date", "");
        if (completionDateString.length() > 0)
          scheduledTask.setCompletionDate(MilestoneHelper.getDatabaseDate(completionDateString)); 
        int selNo = scheduledTask.getReleaseID();
        int taskId = scheduledTask.getTaskID();
        scheduledTask.setMultCompleteDates(getMultCompleteDates(selNo, taskId));
        scheduledTask.setAllowMultCompleteDatesFlag(connector.getBoolean("allow_mult_complete_dates_flag"));
        String taskStatus = connector.getField("status", "");
        if (taskStatus.length() > 1)
          scheduledTask.setScheduledTaskStatus(taskStatus); 
        int day = connector.getIntegerField("day_of_week");
        if (day > 0)
          scheduledTask.setDayOfTheWeek(new Day(connector.getIntegerField("day_of_week"))); 
        String vendorString = connector.getField("vendor", "");
        if (vendorString.length() > 0)
          scheduledTask.setVendor(vendorString); 
        int taskAbbrevID = connector.getIntegerField("abbrev_id");
        scheduledTask.setTaskAbbreviationID(taskAbbrevID);
        int taskID = connector.getIntegerField("task_id");
        scheduledTask.setScheduledTaskID(taskID);
        String taskDept = connector.getField("department", "");
        scheduledTask.setDepartment(taskDept);
        scheduledTask.setKeyTask(connector.getBoolean("key_task_indicator"));
        scheduledTask.setAuthorizationName(connector.getField("authorization_name", ""));
        String authDateString = connector.getField("authorization_date", "");
        if (authDateString.length() > 0)
          scheduledTask.setAuthorizationDate(MilestoneHelper.getDatabaseDate(authDateString)); 
        String comments = connector.getField("comments", "");
        scheduledTask.setComments(comments);
        scheduledTask.setName(connector.getField("name", ""));
        scheduledTask.setTaskWeeksToRelease(connector.getIntegerField("task_weeks_to_release"));
        long lastUpdatedLong = Long.parseLong(connector.getField("last_updated_ck", ""), 16);
        scheduledTask.setLastUpdatedCk(lastUpdatedLong);
        precache.add(scheduledTask);
        scheduledTask = null;
        connector.next();
      } 
      connector.close();
      schedule.setTasks(precache);
      return schedule;
    } 
    try {
      connector.close();
    } catch (Exception exception) {}
    return null;
  }
  
  public Schedule saveSchedule(Selection selection, Schedule schedule, User user) {
    Vector tasks = schedule.getTasks();
    String dueDateString = "";
    String completeString = "";
    String keyTask = "0";
    String lastUpdated = "";
    int dayOfTheWeek = -1;
    for (int i = 0; i < tasks.size(); i++) {
      ScheduledTask task = (ScheduledTask)tasks.get(i);
      dueDateString = "";
      completeString = "";
      if (task.getDueDate() != null)
        dueDateString = MilestoneHelper.getFormatedDate(task.getDueDate()); 
      if (task.getCompletionDate() != null)
        completeString = MilestoneHelper.getFormatedDate(task.getCompletionDate()); 
      keyTask = "0";
      if (task.getIsKeytask())
        keyTask = "1"; 
      long timestamp = task.setLastUpdatedCk();
      String spString = "sp_upd_Release_Detail ";
      int releaseId = task.getReleaseID();
      if (task.getReleaseID() < 0) {
        spString = "sp_ins_Release_Detail ";
        releaseId = selection.getSelectionID();
      } 
      if (task.getDayOfTheWeek() != null)
        dayOfTheWeek = task.getDayOfTheWeek().getDayID(); 
      int ownerId = -1;
      if (task.getOwner() != null)
        ownerId = task.getOwner().getStructureID(); 
      if (task.getScheduledTaskStatus().equals("Auto")) {
        String autoCloseDateStr = MilestoneHelper.getFormatedDate(task.getCompletionDate());
        System.out.println("<< Auto Close date check " + autoCloseDateStr);
        if (!autoCloseDateStr.equals("9/9/99"))
          task.setScheduledTaskStatus(""); 
      } 
      String query = String.valueOf(spString) + 
        releaseId + "," + 
        task.getTaskID() + "," + 
        ownerId + "," + 
        "'" + dueDateString + "'," + 
        "0" + "," + 
        "'" + MilestoneHelper.escapeSingleQuotes(completeString) + "'," + 
        "'" + MilestoneHelper.escapeSingleQuotes(task.getScheduledTaskStatus()) + "'," + 
        dayOfTheWeek + "," + 
        task.getWeeksToRelease() + "," + 
        "'" + MilestoneHelper.escapeSingleQuotes(task.getVendor()) + "'," + 
        keyTask + "," + 
        "'" + MilestoneHelper.escapeSingleQuotes(task.getAuthorizationName()) + "'," + 
        "'" + "'," + 
        "'" + MilestoneHelper.escapeSingleQuotes(task.getComments()) + "'," + 
        user.getUserId();
      if (task.getReleaseID() > 0)
        query = String.valueOf(query) + "," + timestamp; 
      JdbcConnector connector = MilestoneHelper.getConnector(query);
      connector.runQuery();
      connector.close();
      String timestampQuery = "Select last_updated_ck, last_updated_on from release_detail where release_id = " + task.getReleaseID() + " and task_id =" + task.getTaskID();
      JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
      connectorTimestamp.runQuery();
      if (connectorTimestamp.more()) {
        task.setLastUpdatedCk(Long.parseLong(connectorTimestamp.getField("last_updated_ck", ""), 16));
        lastUpdated = connectorTimestamp.getField("last_updated_on", "");
        if (lastUpdated.length() > 0)
          task.setLastUpdateDate(
              MilestoneHelper.getDatabaseDate(connectorTimestamp.getField("last_updated_on", ""))); 
      } 
      connectorTimestamp.close();
      if (spString.equalsIgnoreCase("sp_ins_Release_Detail ")) {
        String selectionTimestampQuery = "SELECT last_updated_ck, last_updated_on FROM release_header WHERE release_id = " + 
          
          selection.getSelectionID() + 
          ";";
        JdbcConnector selectionConnectorTimestamp = MilestoneHelper.getConnector(selectionTimestampQuery);
        selectionConnectorTimestamp.runQuery();
        if (selectionConnectorTimestamp.more()) {
          selection.setLastUpdatedCheck(Long.parseLong(selectionConnectorTimestamp.getField("last_updated_ck"), 16));
          selection.setLastUpdateDate(MilestoneHelper.getDatabaseDate(selectionConnectorTimestamp.getField("last_updated_on")));
        } 
        selectionConnectorTimestamp.close();
      } 
    } 
    return schedule;
  }
  
  public void copySchedule(Schedule schedule, Selection selection, User user) {
    Vector tasks = schedule.getTasks();
    recalculateDueDates(schedule, selection);
    String dueDateString = "";
    String completeString = "";
    String keyTask = "0";
    String lastUpdated = "";
    for (int i = 0; i < tasks.size(); i++) {
      ScheduledTask task = (ScheduledTask)tasks.get(i);
      dueDateString = "";
      completeString = "";
      if (task.getDueDate() != null)
        dueDateString = MilestoneHelper.getFormatedDate(task.getDueDate()); 
      if (task.getCompletionDate() != null)
        completeString = MilestoneHelper.getFormatedDate(task.getCompletionDate()); 
      keyTask = "0";
      if (task.getIsKeytask())
        keyTask = "1"; 
      int owner = -1;
      int familyID = -1;
      if (selection != null && selection.getEnvironment() != null)
        familyID = selection.getEnvironment().getParentID(); 
      if (task.getOwner() != null && !task.getOwner().getName().equalsIgnoreCase("Enterprise")) {
        owner = task.getOwner().getStructureID();
      } else if (task.getOwner() != null && task.getOwner().getName().equalsIgnoreCase("Enterprise")) {
        owner = familyID;
      } 
      String query = "sp_ins_Release_Detail " + 
        selection.getSelectionID() + "," + 
        task.getTaskID() + "," + 
        owner + "," + 
        "'" + dueDateString + "'," + 
        "0" + "," + 
        "'" + completeString + "'," + 
        "'" + task.getScheduledTaskStatus() + "'," + 
        task.getDayOfTheWeek().getDayID() + "," + 
        task.getWeeksToRelease() + "," + 
        "'" + task.getVendor() + "'," + 
        keyTask + "," + 
        "'" + MilestoneHelper.escapeSingleQuotes(task.getAuthorizationName()) + "'," + 
        "'" + "'," + 
        "'" + MilestoneHelper.escapeSingleQuotes(task.getComments()) + "'," + 
        user.getUserId();
      JdbcConnector connector = MilestoneHelper.getConnector(query);
      connector.runQuery();
      connector.close();
    } 
  }
  
  public int getTaskEditAccess(User user, int familyID) {
    Vector companyAcls = user.getAcl().getCompanyAcl();
    int access = 0;
    for (int i = 0; i < companyAcls.size(); i++) {
      CompanyAcl companyAcl = (CompanyAcl)companyAcls.get(i);
      Company company = (Company)MilestoneHelper.getStructureObject(companyAcl.getCompanyId());
      int familyIdCk = -1;
      if (company != null)
        familyIdCk = company.getParentEnvironment().getParentFamily().getStructureID(); 
      if (familyIdCk == familyID && companyAcl != null)
        if (access < companyAcl.getAccessSchedule()) {
          access = companyAcl.getAccessSchedule();
          if (companyAcl.getAccessSchedule() == 2)
            break; 
        }  
    } 
    return access;
  }
  
  public int getTaskEditAccess(User user, int familyID, Hashtable aclFamilyHash) {
    String accessLevel = (String)aclFamilyHash.get(String.valueOf(familyID));
    if (accessLevel != null && !accessLevel.equals(""))
      return Integer.parseInt(accessLevel); 
    return -1;
  }
  
  public Hashtable buildTaskEditAccess(Vector companyAcls, User user) {
    Hashtable familyHash = new Hashtable();
    if (companyAcls != null)
      for (int i = 0; i < companyAcls.size(); i++) {
        CompanyAcl companyAcl = (CompanyAcl)companyAcls.get(i);
        Company company = (Company)MilestoneHelper.getStructureObject(companyAcl.getCompanyId());
        int familyIdCk = -1;
        if (company != null) {
          familyIdCk = company.getParentEnvironment().getParentFamily()
            .getStructureID();
          if (!familyHash.containsKey(String.valueOf(familyIdCk))) {
            familyHash.put(String.valueOf(familyIdCk), 
                String.valueOf(companyAcl.getAccessSchedule()));
          } else {
            String accessStr = (String)familyHash.get(String.valueOf(
                  familyIdCk));
            int access = Integer.parseInt(accessStr);
            if (access < companyAcl.getAccessSchedule())
              familyHash.put(String.valueOf(familyIdCk), 
                  String.valueOf(companyAcl.getAccessSchedule())); 
          } 
        } 
        if (!user.newSelectionEditAccess && companyAcl.getAccessSelection() == 2)
          user.newSelectionEditAccess = true; 
      }  
    return familyHash;
  }
  
  public void addTask(Task task, User user, Selection selection) {
    String keyTask = "0";
    keyTask = task.getIsKeyTask() ? "1" : "0";
    int dayId = 8;
    int weeks = -1;
    int activeFlag = 0;
    if (task.getDayOfTheWeek() != null)
      dayId = task.getDayOfTheWeek().getDayID(); 
    if (task.getActiveFlag()) {
      activeFlag = 0;
    } else {
      activeFlag = 1;
    } 
    weeks = task.getWeeksToRelease();
    if (weeks > 0 && dayId < 0) {
      dayId = 8;
    } else if (weeks == -10) {
      dayId = 9;
    } 
    Calendar dueDate = null;
    if (selection.getStreetDate() != null && weeks != -10)
      dueDate = MilestoneHelper.getDueDate(selection.getStreetDate(), dayId, weeks); 
    String dueDateString = "";
    if (dueDate != null && weeks != -10) {
      dueDateString = MilestoneHelper.getFormatedDate(dueDate);
    } else if (weeks == -10) {
      DatePeriod dp = MilestoneHelper.getReleaseWeek(selection);
      if (dp != null)
        dueDateString = MilestoneHelper.getFormatedDate(dp.getSolDate()); 
    } 
    int owner = -1;
    int familyID = -1;
    if (selection != null && selection.getEnvironment() != null)
      familyID = selection.getEnvironment().getParentID(); 
    if (task.getOwner() != null && !task.getOwner().getName().equalsIgnoreCase("Enterprise")) {
      owner = task.getOwner().getStructureID();
    } else if (task.getOwner() != null && task.getOwner().getName().equalsIgnoreCase("Enterprise")) {
      owner = familyID;
    } 
    String query = "sp_ins_Release_Detail " + 
      selection.getSelectionID() + "," + 
      task.getTaskID() + "," + 
      owner + "," + 
      "'" + dueDateString + "'," + 
      "0" + "," + 
      "NULL" + "," + 
      "''" + "," + 
      dayId + "," + 
      weeks + "," + 
      "''" + "," + 
      keyTask + "," + 
      "'" + MilestoneHelper.escapeSingleQuotes(user.getName()) + "'," + 
      "'" + MilestoneHelper.getFormatedDate(Calendar.getInstance()) + "'," + 
      "'" + MilestoneHelper.escapeSingleQuotes(task.getComments()) + "'," + 
      user.getUserId();
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    connector.close();
    String selectionTimestampQuery = "SELECT last_updated_ck, last_updated_on FROM release_header WHERE release_id = " + 
      
      selection.getSelectionID() + 
      ";";
    JdbcConnector selectionConnectorTimestamp = MilestoneHelper.getConnector(selectionTimestampQuery);
    selectionConnectorTimestamp.runQuery();
    if (selectionConnectorTimestamp.more()) {
      selection.setLastUpdatedCheck(Long.parseLong(selectionConnectorTimestamp.getField("last_updated_ck"), 16));
      selection.setLastUpdateDate(MilestoneHelper.getDatabaseDate(selectionConnectorTimestamp.getField("last_updated_on")));
    } 
    selectionConnectorTimestamp.close();
  }
  
  public void deleteTask(String taskId, Selection selection, User updatingUser) {
    String query = "sp_del_Release_Detail " + 
      selection.getSelectionID() + "," + 
      taskId + "," + 
      updatingUser.getUserId();
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    connector.close();
  }
  
  public void deleteAllTasks(Selection selection, User updatingUser) {
    String query = "sp_del_Release_Details " + 
      selection.getSelectionID() + "," + 
      updatingUser.getUserId();
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    connector.close();
  }
  
  public void setTaskNotepadQuery(Context context, Notepad notepad) {
    if (notepad != null) {
      int releaseId = ((Selection)context.getSessionValue("Selection")).getSelectionID();
      String taskNameSearch = context.getParameter("TaskNameSearch");
      String keyTaskSearch = context.getParameter("KeyTaskSearch");
      String taskOwnerSearch = context.getParameter("TaskOwnerSearch");
      String taskDepartmentSearch = context.getParameter("TaskDepartmentSearch");
      int keyTask = -1;
      if (keyTaskSearch != null && keyTaskSearch.equalsIgnoreCase("yes")) {
        keyTask = 1;
      } else if (keyTaskSearch != null && keyTaskSearch.equalsIgnoreCase("no")) {
        keyTask = 0;
      } 
      String query = "SELECT * FROM vi_Task WHERE task_id  not in (select task_id from vi_Release_Detail  where release_id = " + 
        
        releaseId + ")";
      if (keyTask > -1)
        query = String.valueOf(query) + " AND key_task_indicator = " + keyTask; 
      if (MilestoneHelper.isStringNotEmpty(taskNameSearch))
        query = String.valueOf(query) + " AND name " + MilestoneHelper.setWildCardsEscapeSingleQuotes(taskNameSearch); 
      if (MilestoneHelper.isStringNotEmpty(taskOwnerSearch) && !taskOwnerSearch.equals("0"))
        query = String.valueOf(query) + " AND owner = '" + taskOwnerSearch + "'"; 
      if (MilestoneHelper.isStringNotEmpty(taskDepartmentSearch) && !taskDepartmentSearch.equals("0") && !taskDepartmentSearch.equals("-1"))
        query = String.valueOf(query) + " AND department like '%" + taskDepartmentSearch + "%'"; 
      String order = " ORDER BY name";
      notepad.setSearchQuery(query);
      notepad.setOrderBy(order);
    } 
  }
  
  public Vector getScheduleTaskNotepadList(int releaseId, int userId, Notepad notepad, Context context) {
    String query = "";
    if (notepad != null && !notepad.getSearchQuery().equals("")) {
      query = notepad.getSearchQuery();
      query = String.valueOf(query) + notepad.getOrderBy();
    } else {
      query = String.valueOf(getDefaultTaskSearchQuery(releaseId)) + " ORDER BY name";
    } 
    Task task = null;
    Vector precache = new Vector();
    Vector companies = MilestoneHelper.getUserCompanies(context);
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    while (connector.more()) {
      task = getScheduleTaskNotepadObject(connector);
      if (task != null) {
        Company company = null;
        if (companies != null)
          for (int j = 0; j < companies.size(); j++) {
            company = (Company)companies.get(j);
            if (company != null && company.getParentEnvironment().getParentFamily() != null && task.getOwner() != null)
              if (company.getParentEnvironment().getParentFamily().getStructureID() == task.getOwner().getStructureID()) {
                if (task.getActiveFlag())
                  precache.addElement(task); 
                break;
              }  
          }  
      } 
      task = null;
      connector.next();
    } 
    connector.close();
    return precache;
  }
  
  public Task getScheduleTaskNotepadObject(JdbcConnector connector) {
    Task task = null;
    if (connector != null) {
      task = new Task();
      task.setTaskID(connector.getIntegerField("task_id"));
      task.setTaskName(connector.getField("name", ""));
      task.setWeeksToRelease(connector.getIntegerField("weeks_to_release"));
      task.setDayOfTheWeek(new Day(connector.getIntegerField("day_of_week")));
      task.setDepartment(connector.getField("department", ""));
      task.setOwner((Family)MilestoneHelper.getStructureObject(connector.getIntegerField("owner")));
      task.setTaskAbbreviation(connector.getIntegerField("abbrev_id"));
      int taskID = connector.getIntegerField("task_id");
      task.setTaskID(taskID);
      task.setIsKeyTask(connector.getBoolean("key_task_indicator"));
      task.setActiveFlag(connector.getBoolean("active_flag"));
    } 
    return task;
  }
  
  public Vector getSuggestedTemplates(User user, Selection selection, Context context) {
    Vector precache = new Vector();
    if (selection != null) {
      String query = "SELECT *   FROM vi_Template_Header th with (nolock) WHERE product_line = '" + 
        
        SelectionManager.getLookupObjectValue(selection.getProductCategory()) + "'" + 
        " AND release_type = '" + SelectionManager.getLookupObjectValue(selection.getReleaseType()) + "'" + 
        " AND configuration = '" + selection.getSelectionConfig().getSelectionConfigurationAbbreviation() + "'" + 
        " AND sub_configuration = '" + selection.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation() + "'" + 
        " AND (productType = " + (selection.getIsDigital() ? "1" : "0") + 
        " OR productType = 2) " + 
        " AND ( SELECT Count(*) " + 
        "         FROM vi_Template_Detail td with(nolock) " + 
        "        WHERE td.Template_Id = th.Template_Id ) > 0 ";
      Vector companies = MilestoneHelper.getUserCompanies(context);
      Company company = null;
      if (companies.size() > 0) {
        query = String.valueOf(query) + " AND (";
        for (int i = 0; i < companies.size(); i++) {
          company = (Company)companies.get(i);
          if (company != null && company.getParentEnvironment().getParentFamily() != null)
            query = String.valueOf(query) + " owner = " + String.valueOf((company.getParentEnvironment().getParentFamily()).structureID); 
          if (i < companies.size() - 1)
            query = String.valueOf(query) + " OR "; 
        } 
        query = String.valueOf(query) + " )";
      } 
      query = String.valueOf(query) + " ORDER BY name ";
      Template template = null;
      JdbcConnector connector = MilestoneHelper.getConnector(query);
      connector.runQuery();
      while (connector.more()) {
        template = new Template();
        template.setTemplateID(connector.getIntegerField("template_id"));
        template.setTemplateName(connector.getField("name", ""));
        template.setOwner((Family)MilestoneHelper.getStructureObject(connector.getIntegerField("owner")));
        int owner = connector.getInt("owner", 0);
        if (owner == 12)
          template.order = owner; 
        precache.addElement(template);
        template = null;
        connector.next();
      } 
      connector.close();
    } 
    Collections.sort(precache);
    return precache;
  }
  
  public Vector getTemplatesSearch(User user, Selection selection, String name, String config, String productLine, Context context) {
    String query = "SELECT *   FROM vi_Template_Header th WHERE";
    String whereClause = "";
    if (productLine.length() > 0)
      whereClause = String.valueOf(whereClause) + " product_line = '" + productLine + "'"; 
    if (config.length() > 0) {
      if (whereClause.length() > 0)
        whereClause = String.valueOf(whereClause) + " AND "; 
      whereClause = String.valueOf(whereClause) + " configuration = '" + config + "'";
    } 
    if (name.length() > 0) {
      if (whereClause.length() > 0)
        whereClause = String.valueOf(whereClause) + " AND "; 
      whereClause = String.valueOf(whereClause) + " name like '%" + name + "%'";
    } 
    if (whereClause.length() > 0)
      whereClause = String.valueOf(whereClause) + " AND "; 
    whereClause = String.valueOf(whereClause) + " (productType = " + (selection.getIsDigital() ? "1" : "0") + " or productType = 2) ";
    if (whereClause.length() > 0)
      whereClause = String.valueOf(whereClause) + " AND "; 
    whereClause = String.valueOf(whereClause) + " ( SELECT Count(*)          FROM vi_Template_Detail td         WHERE td.Template_Id = th.Template_Id ) > 0 ";
    query = String.valueOf(query) + whereClause;
    Vector companies = MilestoneHelper.getUserCompanies(context);
    Company company = null;
    if (companies.size() > 0) {
      query = String.valueOf(query) + " AND (";
      for (int i = 0; i < companies.size(); i++) {
        company = (Company)companies.get(i);
        if (company != null && company.getParentEnvironment().getParentFamily() != null) {
          query = String.valueOf(query) + " owner = " + String.valueOf((company.getParentEnvironment().getParentFamily()).structureID);
          if (i < companies.size() - 1)
            query = String.valueOf(query) + " OR "; 
        } 
      } 
      query = String.valueOf(query) + " )";
    } 
    query = String.valueOf(query) + " ORDER BY name ";
    Template template = null;
    Vector precache = new Vector();
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    while (connector.more()) {
      template = new Template();
      template.setTemplateID(connector.getIntegerField("template_id"));
      template.setTemplateName(connector.getField("name", ""));
      template.setOwner((Family)MilestoneHelper.getStructureObject(connector.getIntegerField("owner")));
      int owner = connector.getInt("owner", 0);
      if (owner == 12)
        template.order = owner; 
      precache.addElement(template);
      template = null;
      connector.next();
    } 
    connector.close();
    Collections.sort(precache);
    return precache;
  }
  
  public void assignTemplate(User user, Selection selection, String templateId) {
    Template template = TemplateManager.getInstance().getTemplate(Integer.parseInt(templateId), true);
    Vector tasks = template.getTasks();
    Task task = null;
    Schedule schedule = new Schedule();
    Vector scheduleTasks = new Vector();
    schedule.setSelectionID(selection.getSelectionID());
    Family family = null;
    int familyID = -1;
    if (selection != null && selection.getEnvironment() != null) {
      familyID = selection.getEnvironment().getParentID();
      family = (Family)MilestoneHelper.getStructureObject(familyID);
    } 
    for (int i = 0; i < tasks.size(); i++) {
      task = (Task)tasks.get(i);
      ScheduledTask scheduledTask = getScheduledTask(task);
      if (task.getOwner() != null && task.getOwner().getName().equalsIgnoreCase("Enterprise"))
        if (family != null)
          scheduledTask.setOwner(family);  
      scheduleTasks.add(scheduledTask);
      task = null;
    } 
    schedule.setTasks(scheduleTasks);
    recalculateDueDates(schedule, selection);
    selection.setSchedule(schedule);
  }
  
  public ScheduledTask getScheduledTask(Task task) {
    ScheduledTask scheduledTask = new ScheduledTask();
    scheduledTask.setReleaseID(-1);
    scheduledTask.setTaskID(task.getTaskID());
    scheduledTask.setComments(task.getComments());
    scheduledTask.setName(task.getTaskName());
    scheduledTask.setWeeksToRelease(task.getWeeksToRelease());
    scheduledTask.setDayOfTheWeek(task.getDayOfTheWeek());
    scheduledTask.setOwner(task.getOwner());
    scheduledTask.setKeyTask(task.getIsKeyTask());
    scheduledTask.setTaskAbbreviationID(task.getTaskAbbreviation());
    scheduledTask.setScheduledTaskID(task.getTaskID());
    scheduledTask.setDepartment(task.getDepartment());
    scheduledTask.setAllowMultCompleteDatesFlag(task.getAllowMultCompleteDatesFlag());
    return scheduledTask;
  }
  
  public ScheduledTask getScheduledTask(int selectionNo, int taskId) {
    ScheduledTask scheduledTask = null;
    String scheduledtaskQuery = "SELECT b.weeks_to_release as task_weeks_to_release, a.*, b.*   FROM vi_Release_Detail a,        vi_Task b  WHERE a.release_Id = " + 
      
      selectionNo + 
      "   AND a.task_Id = " + taskId + 
      "   AND a.task_Id = b.task_Id ";
    JdbcConnector connector = MilestoneHelper.getConnector(scheduledtaskQuery);
    connector.runQuery();
    if (connector.more()) {
      scheduledTask = new ScheduledTask();
      scheduledTask.setReleaseID(connector.getIntegerField("release_Id"));
      scheduledTask.setTaskID(connector.getIntegerField("task_id"));
      scheduledTask.setOwner(
          (Family)MilestoneHelper.getStructureObject(connector.getIntegerField("owner")));
      int weeks = connector.getIntegerField("weeks_to_release");
      if (weeks > 0 || weeks == -10)
        scheduledTask.setWeeksToRelease(connector.getIntegerField("weeks_to_release")); 
      String dueDateString = connector.getField("due_date", "");
      if (dueDateString.length() > 0)
        scheduledTask.setDueDate(MilestoneHelper.getDatabaseDate(dueDateString)); 
      String completionDateString = connector.getField("completion_date", "");
      if (completionDateString.length() > 0)
        scheduledTask.setCompletionDate(MilestoneHelper.getDatabaseDate(completionDateString)); 
      scheduledTask.setMultCompleteDates(getMultCompleteDates(selectionNo, taskId));
      scheduledTask.setAllowMultCompleteDatesFlag(connector.getBoolean("allow_mult_complete_dates_flag"));
      String taskStatus = connector.getField("status", "");
      if (taskStatus.length() > 1)
        scheduledTask.setScheduledTaskStatus(taskStatus); 
      int day = connector.getIntegerField("day_of_week");
      if (day > 0)
        scheduledTask.setDayOfTheWeek(new Day(connector.getIntegerField("day_of_week"))); 
      String vendorString = connector.getField("vendor", "");
      if (vendorString.length() > 0)
        scheduledTask.setVendor(vendorString); 
      int taskAbbrevID = connector.getIntegerField("abbrev_id");
      scheduledTask.setTaskAbbreviationID(taskAbbrevID);
      int taskID = connector.getIntegerField("task_id");
      scheduledTask.setScheduledTaskID(taskID);
      String taskDept = connector.getField("department", "");
      scheduledTask.setDepartment(taskDept);
      scheduledTask.setKeyTask(connector.getBoolean("key_task_indicator"));
      scheduledTask.setAuthorizationName(connector.getField("authorization_name", ""));
      String authDateString = connector.getField("authorization_date", "");
      if (authDateString.length() > 0)
        scheduledTask.setAuthorizationDate(MilestoneHelper.getDatabaseDate(authDateString)); 
      String comments = connector.getField("comments", "");
      scheduledTask.setComments(comments);
      scheduledTask.setName(connector.getField("name", ""));
      scheduledTask.setTaskWeeksToRelease(connector.getIntegerField("task_weeks_to_release"));
      long lastUpdatedLong = Long.parseLong(connector.getField("last_updated_ck", ""), 16);
      scheduledTask.setLastUpdatedCk(lastUpdatedLong);
    } 
    connector.close();
    return scheduledTask;
  }
  
  public void assignSchedule(User user, Schedule schedule, String releaseId) {
    Selection copySelection = SelectionManager.getInstance().getSelectionHeader(Integer.parseInt(releaseId));
    copySchedule(schedule, copySelection, user);
  }
  
  public Vector getScheduleSearch(User user, Selection selection, String artist, String title, String selectionString, String UPC, String prefix, String streetDate, String label, Context context) {
    String query = " SELECT DISTINCT rh.artist_first_name, rh.artist_last_name, rh.release_id, rh.project_No, rh.artist, rh.title, rh.UPC,         rh.Selection_No, rh.Street_Date, rh.label_id    FROM vi_Release_Header rh,         vi_Release_Detail rd   WHERE rh.release_id = rd.release_id ";
    Company company = null;
    Vector companies = MilestoneHelper.getUserCompanies(context);
    String whereClause = " AND (";
    for (int i = 0; i < companies.size(); i++) {
      company = (Company)companies.get(i);
      if (company != null)
        if (i < companies.size() - 1) {
          whereClause = String.valueOf(whereClause) + " company_id = " + company.getStructureID() + " OR ";
        } else {
          whereClause = String.valueOf(whereClause) + " company_id = " + company.getStructureID();
        }  
    } 
    whereClause = String.valueOf(whereClause) + ") ";
    query = String.valueOf(query) + whereClause;
    whereClause = "";
    if (artist.length() > 0)
      whereClause = String.valueOf(whereClause) + "AND rh.artist LIKE '%" + artist + "%' "; 
    if (title.length() > 0)
      whereClause = String.valueOf(whereClause) + "AND rh.title LIKE '%" + title + "%' "; 
    if (selectionString.length() > 0)
      whereClause = String.valueOf(whereClause) + "AND rh.selection_No LIKE '%" + selectionString + "%' "; 
    if (UPC.length() > 0)
      whereClause = String.valueOf(whereClause) + "AND rh.UPC LIKE '%" + UPC + "%' "; 
    if (prefix.length() > 0)
      whereClause = String.valueOf(whereClause) + "AND rh.prefix LIKE '%" + prefix + "%' "; 
    if (streetDate.length() > 0) {
      Calendar x = MilestoneHelper.getDate(streetDate);
      x.add(5, 1);
      String nextDay = MilestoneHelper.getFormatedDate(x);
      whereClause = String.valueOf(whereClause) + "AND rh.street_date >= '" + streetDate + "' AND rh.street_date < '" + nextDay + "' ";
    } 
    if (label.length() > 0 && Integer.parseInt(label) > 0)
      whereClause = String.valueOf(whereClause) + " AND rh.label_id = " + label; 
    whereClause = String.valueOf(whereClause) + " AND rh.digital_flag = " + (selection.getIsDigital() ? "1" : "0");
    query = String.valueOf(query) + whereClause;
    query = String.valueOf(query) + whereClause + " ORDER BY artist";
    Selection currentSelection = null;
    Vector precache = new Vector();
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    while (connector.more()) {
      currentSelection = new Selection();
      currentSelection.setSelectionID(connector.getIntegerField("release_id"));
      currentSelection.setTitle(connector.getField("title", ""));
      currentSelection.setArtistFirstName(connector.getField("artist_first_name", ""));
      currentSelection.setArtistLastName(connector.getField("artist_last_name", ""));
      currentSelection.setArtist(connector.getField("artist", ""));
      String selectionNo = "";
      if (connector.getFieldByName("selection_no") != null)
        selectionNo = connector.getFieldByName("selection_no"); 
      currentSelection.setSelectionNo(selectionNo);
      currentSelection.setUpc(connector.getField("upc", ""));
      String streetDateString = connector.getFieldByName("street_date");
      if (streetDateString != null)
        currentSelection.setStreetDate(MilestoneHelper.getDatabaseDate(streetDateString)); 
      precache.addElement(currentSelection);
      currentSelection = null;
      connector.next();
    } 
    connector.close();
    return precache;
  }
  
  public void recalculateDueDates(Schedule schedule, Selection selection) {
    if (schedule != null) {
      Vector tasks = schedule.getTasks();
      Vector precache = new Vector();
      ScheduledTask task = null;
      for (int i = 0; i < tasks.size(); i++) {
        task = (ScheduledTask)tasks.get(i);
        if (task.getWeeksToRelease() == -10 && task.getCompletionDate() == null) {
          DatePeriod dp = MilestoneHelper.getReleaseWeek(selection);
          if (dp != null) {
            task.setDueDate(dp.getSolDate());
          } else {
            task.setDueDate(null);
          } 
        } else if (task.getDayOfTheWeek() != null && task.getWeeksToRelease() >= 0 && task.getCompletionDate() == null) {
          if (selection.getIsDigital()) {
            task.setDueDate(MilestoneHelper.getDueDate(selection.getDigitalRlsDate(), task.getDayOfTheWeek().getDayID(), task.getWeeksToRelease()));
          } else {
            task.setDueDate(MilestoneHelper.getDueDate(selection.getStreetDate(), task.getDayOfTheWeek().getDayID(), task.getWeeksToRelease()));
          } 
        } else if (task.getWeeksToRelease() >= 0 && task.getCompletionDate() == null) {
          if (selection.getIsDigital()) {
            task.setDueDate(MilestoneHelper.getDueDate(selection.getDigitalRlsDate(), 8, task.getWeeksToRelease()));
          } else {
            task.setDueDate(MilestoneHelper.getDueDate(selection.getStreetDate(), 8, task.getWeeksToRelease()));
          } 
        } 
        if ((!selection.getIsDigital() && selection.getStreetDate() == null) || (
          selection.getIsDigital() && selection.getDigitalRlsDate() == null))
          task.setDueDate(null); 
        precache.add(task);
        task = null;
      } 
      schedule.setTasks(precache);
    } 
  }
  
  public void recalculateAllDueDates(Schedule schedule, Selection selection) {
    if (schedule != null) {
      Vector tasks = schedule.getTasks();
      Vector precache = new Vector();
      ScheduledTask task = null;
      for (int i = 0; i < tasks.size(); i++) {
        task = (ScheduledTask)tasks.get(i);
        if (task.getWeeksToRelease() == -10) {
          DatePeriod dp = MilestoneHelper.getReleaseWeek(selection);
          if (dp != null) {
            task.setDueDate(dp.getSolDate());
          } else {
            task.setDueDate(null);
          } 
        } else if (task.getDayOfTheWeek() != null && task.getWeeksToRelease() >= 0) {
          if (selection.getIsDigital()) {
            task.setDueDate(MilestoneHelper.getDueDate(selection.getDigitalRlsDate(), task.getDayOfTheWeek().getDayID(), task.getWeeksToRelease()));
          } else {
            task.setDueDate(MilestoneHelper.getDueDate(selection.getStreetDate(), task.getDayOfTheWeek().getDayID(), task.getWeeksToRelease()));
          } 
        } else if (task.getWeeksToRelease() >= 0) {
          if (selection.getIsDigital()) {
            task.setDueDate(MilestoneHelper.getDueDate(selection.getDigitalRlsDate(), 8, task.getWeeksToRelease()));
          } else {
            task.setDueDate(MilestoneHelper.getDueDate(selection.getStreetDate(), 8, task.getWeeksToRelease()));
          } 
        } 
        if ((!selection.getIsDigital() && selection.getStreetDate() == null) || (
          selection.getIsDigital() && selection.getDigitalRlsDate() == null))
          task.setDueDate(null); 
        precache.add(task);
        task = null;
      } 
      schedule.setTasks(precache);
    } 
  }
  
  public boolean closeSchedule(Schedule schedule, Selection selection, User user) {
    boolean close = true;
    if (schedule != null) {
      Vector tasks = schedule.getTasks();
      Vector precache = new Vector();
      ScheduledTask task = null;
      for (int i = 0; i < tasks.size(); i++) {
        task = (ScheduledTask)tasks.get(i);
        if (task.getCompletionDate() == null)
          if (MilestoneHelper.isUml(task) || MilestoneHelper.isEcommerce(task)) {
            close = false;
            break;
          }  
        task = null;
      } 
      if (close) {
        selection.setSelectionStatus((SelectionStatus)SelectionManager.getLookupObject("CLOSED", Cache.getSelectionStatusList()));
        SelectionManager.getInstance().updateStatusToClose(selection);
        Calendar labelCmpDt = MilestoneHelper.getDate("9/9/99");
        for (int i = 0; i < tasks.size(); i++) {
          task = (ScheduledTask)tasks.get(i);
          if (task.getCompletionDate() == null && !MilestoneHelper.isUml(task) && !MilestoneHelper.isEcommerce(task))
            UpdateCompletionDate(task.releaseID, task.taskID, user.userId, labelCmpDt); 
          task = null;
        } 
      } 
    } 
    return close;
  }
  
  public void clearDates(Schedule schedule) {
    Vector tasks = schedule.getTasks();
    Vector precache = new Vector();
    ScheduledTask task = null;
    for (int i = 0; i < tasks.size(); i++) {
      task = (ScheduledTask)tasks.get(i);
      task.setDueDate(null);
      task.setWeeksToRelease(-1);
      task.setDayOfTheWeek(null);
      precache.add(task);
      task = null;
    } 
    schedule.setTasks(precache);
  }
  
  public String getDefaultTaskSearchQuery(int releaseId) {
    query = "";
    return "SELECT * FROM vi_Task WHERE task_id  not in (select task_id from vi_Release_Detail  where release_id = " + 
      
      releaseId + ")";
  }
  
  public Schedule filterSchedule(Schedule schedule, String filter) {
    if (schedule != null && schedule.getTasks().size() > 0) {
      filter = filter.trim();
      if (filter.length() > 0) {
        Vector tasks = schedule.getTasks();
        Vector precache = new Vector();
        if (filter.equalsIgnoreCase("Only UML Tasks")) {
          for (int i = 0; i < tasks.size(); i++) {
            if (MilestoneHelper.isUml((ScheduledTask)tasks.get(i)))
              precache.add((ScheduledTask)tasks.get(i)); 
          } 
          schedule.setTasks(precache);
        } else if (filter.equalsIgnoreCase("Only Label Tasks")) {
          for (int j = 0; j < tasks.size(); j++) {
            if (!MilestoneHelper.isUml((ScheduledTask)tasks.get(j)) && 
              !MilestoneHelper.isEcommerce((ScheduledTask)tasks.get(j)))
              precache.add((ScheduledTask)tasks.get(j)); 
          } 
          schedule.setTasks(precache);
        } else if (filter.equalsIgnoreCase("Only eCommerce Tasks")) {
          for (int j = 0; j < tasks.size(); j++) {
            if (MilestoneHelper.isEcommerce((ScheduledTask)tasks.get(j)))
              precache.add((ScheduledTask)tasks.get(j)); 
          } 
          schedule.setTasks(precache);
        } 
      } 
    } 
    return schedule;
  }
  
  public Schedule deptFilterSchedule(Schedule schedule, String filter, Context context) {
    if (schedule != null && schedule.getTasks().size() > 0) {
      filter = filter.trim();
      if (filter.length() > 0) {
        Vector tasks = schedule.getTasks();
        Vector precache = new Vector();
        for (int i = 0; i < tasks.size(); i++) {
          ScheduledTask task = (ScheduledTask)tasks.get(i);
          if (filter.equalsIgnoreCase("All")) {
            precache.add(task);
          } else if (filter.equalsIgnoreCase(task.getDepartment().trim())) {
            precache.add(task);
          } 
        } 
        if (precache.size() > 0) {
          schedule.setTasks(precache);
        } else {
          context.putDelivery("AlertMessage", "No tasks found for this department!");
        } 
      } 
    } 
    return schedule;
  }
  
  public boolean isTimestampValid(Schedule schedule) {
    boolean isValid = false;
    if (schedule != null) {
      Vector tasks = schedule.getTasks();
      for (int i = 0; i < tasks.size(); i++) {
        ScheduledTask task = (ScheduledTask)tasks.get(i);
        String timestampQuery = "Select last_updated_ck, last_updated_on from release_detail where release_id = " + task.getReleaseID() + " and task_id =" + task.getTaskID();
        JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
        connectorTimestamp.runQuery();
        if (connectorTimestamp.more())
          if (task.setLastUpdatedCk() != Long.parseLong(connectorTimestamp.getField("last_updated_ck", ""), 16)) {
            connectorTimestamp.close();
            return false;
          }  
        connectorTimestamp.close();
      } 
    } 
    return true;
  }
  
  public Vector getMultCompleteDates(int selectionNo, int taskId) {
    Vector multCompleteDates = null;
    if (selectionNo > 0) {
      StringBuffer sql = new StringBuffer();
      sql.append("select release_id, task_id, order_no, completion_date, last_updated_on, last_updated_by from MultCompleteDates where release_id = " + 
          selectionNo + " and task_id = " + taskId + 
          " order by order_no desc");
      JdbcConnector connector = MilestoneHelper.getConnector(sql.toString());
      connector.setForwardOnly(false);
      connector.runQuery();
      multCompleteDates = new Vector();
      if (connector != null) {
        while (connector.more()) {
          MultCompleteDate mcd = new MultCompleteDate();
          mcd.setReleaseID(connector.getIntegerField("release_id"));
          mcd.setTaskID(connector.getIntegerField("task_id"));
          mcd.setOrderNo(connector.getIntegerField("order_no"));
          String completionDateStr = connector.getField("completion_date", "");
          if (completionDateStr.length() > 0)
            mcd.setCompletionDate(MilestoneHelper.getDatabaseDate(completionDateStr)); 
          String lastUpdatedOnStr = connector.getField("last_updated_on");
          if (lastUpdatedOnStr.length() > 0)
            mcd.setLastUpdatedOn(MilestoneHelper.getDatabaseDate(lastUpdatedOnStr)); 
          mcd.setLastUpdatedBy(connector.getIntegerField("last_updated_by"));
          multCompleteDates.addElement(mcd);
          connector.next();
        } 
        connector.close();
      } 
    } 
    return multCompleteDates;
  }
  
  public void saveMultCompleteDates(ScheduledTask newTask, User user) {
    Vector subMCDates = null;
    Vector dbMCDates = null;
    if (newTask != null) {
      int relId = newTask.getReleaseID();
      int taskId = newTask.getTaskID();
      ScheduledTask dbTask = getInstance().getScheduledTask(relId, taskId);
      dbMCDates = dbTask.getMultCompleteDates();
      Calendar subCompDt = newTask.getCompletionDate();
      Calendar dbCompDt = dbTask.getCompletionDate();
      if ((subCompDt == null && dbCompDt != null) || (subCompDt != null && dbCompDt == null) || (
        subCompDt != null && dbCompDt != null && !subCompDt.equals(dbCompDt)))
        UpdateCompletionDate(relId, taskId, user.getUserId(), subCompDt); 
      subMCDates = newTask.getMultCompleteDates();
      boolean refreshDb = false;
      boolean subHasVals = (subMCDates != null && !subMCDates.isEmpty());
      boolean dbHasVals = (dbMCDates != null && !dbMCDates.isEmpty());
      if (subHasVals && dbHasVals) {
        int subCt = subMCDates.size();
        int dbCt = dbMCDates.size();
        int subIdx = subCt - 1;
        int dbIdx = dbCt - 1;
        Vector updateDates = new Vector();
        while (subIdx >= 0 && dbIdx >= 0 && !refreshDb) {
          MultCompleteDate subMcd = (MultCompleteDate)subMCDates.get(subIdx);
          MultCompleteDate dbMcd = (MultCompleteDate)dbMCDates.get(dbIdx);
          if (subMcd.getOrderNo() == dbMcd.getOrderNo()) {
            if (!subMcd.getCompletionDate().equals(dbMcd.getCompletionDate()))
              updateDates.add(subMcd); 
          } else {
            refreshDb = true;
          } 
          subIdx--;
          dbIdx--;
        } 
        if (!refreshDb) {
          if (updateDates != null && !updateDates.isEmpty())
            UpdateMultCompleteDates(relId, taskId, user.getUserId(), updateDates); 
          if (subCt != dbCt) {
            int minCt = Math.min(subCt, dbCt);
            if (minCt == subCt) {
              Vector deleteDates = new Vector();
              for (int j = dbIdx; j >= 0; j--)
                deleteDates.add(dbMCDates.get(j)); 
              DeleteMultCompleteDates(relId, taskId, deleteDates);
            } else if (minCt == dbCt) {
              Vector insertDates = new Vector();
              for (int j = subIdx; j >= 0; j--)
                insertDates.add(subMCDates.get(j)); 
              InsertMultCompleteDates(relId, taskId, user.getUserId(), insertDates);
            } 
          } 
        } else {
          DeleteMultCompleteDates(relId, taskId);
          InsertMultCompleteDates(relId, taskId, user.getUserId(), subMCDates);
        } 
      } else if (!subHasVals && dbHasVals) {
        DeleteMultCompleteDates(relId, taskId);
      } else if (subHasVals && !dbHasVals) {
        InsertMultCompleteDates(relId, taskId, user.getUserId(), subMCDates);
      } 
    } 
  }
  
  private void InsertMultCompleteDates(int releaseId, int taskId, int userId, Vector multCompleteDates) {
    String sqlInsBase = "sp_ins_MultCompleteDate " + String.valueOf(releaseId) + "," + String.valueOf(taskId);
    for (int i = 0; i < multCompleteDates.size(); i++) {
      MultCompleteDate mcd = (MultCompleteDate)multCompleteDates.get(i);
      if (mcd != null && mcd.getCompletionDate() != null) {
        String sqlIns = String.valueOf(sqlInsBase) + "," + String.valueOf(mcd.getOrderNo()) + 
          ",'" + MilestoneHelper.getFormatedDate(mcd.getCompletionDate()) + "', " + userId;
        JdbcConnector connectorInsMultCompleteDate = MilestoneHelper.getConnector(sqlIns);
        connectorInsMultCompleteDate.runQuery();
        connectorInsMultCompleteDate.close();
      } 
    } 
  }
  
  private void UpdateMultCompleteDates(int releaseId, int taskId, int userId, Vector multCompleteDates) {
    if (multCompleteDates != null && !multCompleteDates.isEmpty()) {
      String sqlUpdBase = "sp_upd_MultCompleteDate " + String.valueOf(releaseId) + "," + String.valueOf(taskId);
      for (int i = 0; i < multCompleteDates.size(); i++) {
        MultCompleteDate mcd = (MultCompleteDate)multCompleteDates.get(i);
        if (mcd != null && mcd.getCompletionDate() != null) {
          String sqlUpd = String.valueOf(sqlUpdBase) + "," + String.valueOf(mcd.getOrderNo()) + 
            ",'" + MilestoneHelper.getFormatedDate(mcd.getCompletionDate()) + "', " + userId;
          JdbcConnector connectorInsMultCompleteDate = MilestoneHelper.getConnector(sqlUpd);
          connectorInsMultCompleteDate.runQuery();
          connectorInsMultCompleteDate.close();
        } 
      } 
    } 
  }
  
  private void DeleteMultCompleteDates(int releaseId, int taskId) {
    String sqlDel = "sp_del_task_MultCompleteDates " + releaseId + "," + taskId;
    JdbcConnector connectorInsMultCompleteDate = MilestoneHelper.getConnector(sqlDel);
    connectorInsMultCompleteDate.runQuery();
    connectorInsMultCompleteDate.close();
  }
  
  private void DeleteMultCompleteDates(int releaseId, int taskId, Vector deleteDates) {
    if (deleteDates != null)
      for (int i = 0; i < deleteDates.size(); i++) {
        int orderNo = ((MultCompleteDate)deleteDates.get(i)).getOrderNo();
        String sqlDel = "sp_del_MultCompleteDate " + releaseId + "," + taskId + "," + String.valueOf(orderNo);
        JdbcConnector connectorInsMultCompleteDate = MilestoneHelper.getConnector(sqlDel);
        connectorInsMultCompleteDate.runQuery();
        connectorInsMultCompleteDate.close();
      }  
  }
  
  public void UpdateCompletionDate(int releaseId, int taskId, int userId, Calendar submittedDate) {
    String sqlUpd = "sp_upd_completion_date " + releaseId + " ," + taskId + 
      ", '" + MilestoneHelper.getFormatedDate(submittedDate) + "', " + userId;
    JdbcConnector connectorUpdMultCompleteDate = MilestoneHelper.getConnector(sqlUpd);
    connectorUpdMultCompleteDate.runQuery();
    connectorUpdMultCompleteDate.close();
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\ScheduleManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */