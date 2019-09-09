/*      */ package WEB-INF.classes.com.universal.milestone;
/*      */ 
/*      */ import com.techempower.ComponentLog;
/*      */ import com.techempower.EnhancedProperties;
/*      */ import com.techempower.gemini.Context;
/*      */ import com.techempower.gemini.GeminiApplication;
/*      */ import com.universal.milestone.Cache;
/*      */ import com.universal.milestone.Company;
/*      */ import com.universal.milestone.CompanyAcl;
/*      */ import com.universal.milestone.DatePeriod;
/*      */ import com.universal.milestone.Day;
/*      */ import com.universal.milestone.Family;
/*      */ import com.universal.milestone.JdbcConnector;
/*      */ import com.universal.milestone.MilestoneConstants;
/*      */ import com.universal.milestone.MilestoneHelper;
/*      */ import com.universal.milestone.MultCompleteDate;
/*      */ import com.universal.milestone.Notepad;
/*      */ import com.universal.milestone.Schedule;
/*      */ import com.universal.milestone.ScheduleManager;
/*      */ import com.universal.milestone.ScheduledTask;
/*      */ import com.universal.milestone.Selection;
/*      */ import com.universal.milestone.SelectionManager;
/*      */ import com.universal.milestone.SelectionStatus;
/*      */ import com.universal.milestone.Task;
/*      */ import com.universal.milestone.Template;
/*      */ import com.universal.milestone.TemplateManager;
/*      */ import com.universal.milestone.User;
/*      */ import java.util.Calendar;
/*      */ import java.util.Collections;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Vector;
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
/*      */ public class ScheduleManager
/*      */   implements MilestoneConstants
/*      */ {
/*      */   public static final String COMPONENT_CODE = "mSched";
/*      */   public static final String DEFAULT_TASK_ORDER = " ORDER BY name";
/*   54 */   protected static ScheduleManager scheduleManager = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static ComponentLog log;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   66 */   public static void configure(EnhancedProperties props, GeminiApplication application) { log = application.getLog("mSched"); }
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
/*      */   public static ScheduleManager getInstance() {
/*   84 */     if (scheduleManager == null)
/*      */     {
/*   86 */       scheduleManager = new ScheduleManager();
/*      */     }
/*   88 */     return scheduleManager;
/*      */   }
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
/*      */   public Schedule getSchedule(int id) {
/*  110 */     String scheduledtasksQuery = "sp_get_Schedule " + id;
/*      */     
/*  112 */     JdbcConnector connector = MilestoneHelper.getConnector(scheduledtasksQuery);
/*  113 */     connector.runQuery();
/*      */     
/*  115 */     if (connector.more()) {
/*      */       
/*  117 */       Schedule schedule = new Schedule();
/*  118 */       schedule.setSelectionID(id);
/*      */       
/*  120 */       Vector precache = new Vector();
/*  121 */       ScheduledTask scheduledTask = null;
/*      */       
/*  123 */       while (connector.more()) {
/*      */         
/*  125 */         scheduledTask = new ScheduledTask();
/*      */ 
/*      */         
/*  128 */         scheduledTask.setReleaseID(connector.getIntegerField("release_Id"));
/*      */ 
/*      */         
/*  131 */         scheduledTask.setTaskID(connector.getIntegerField("task_id"));
/*      */ 
/*      */ 
/*      */         
/*  135 */         scheduledTask.setOwner((Family)MilestoneHelper.getStructureObject(connector.getIntegerField("owner")));
/*      */ 
/*      */         
/*  138 */         int weeks = connector.getIntegerField("weeks_to_release");
/*  139 */         if (weeks > 0 || weeks == -10) {
/*  140 */           scheduledTask.setWeeksToRelease(connector.getIntegerField("weeks_to_release"));
/*      */         }
/*      */         
/*  143 */         String dueDateString = connector.getField("due_date", "");
/*  144 */         if (dueDateString.length() > 0) {
/*  145 */           scheduledTask.setDueDate(MilestoneHelper.getDatabaseDate(dueDateString));
/*      */         }
/*      */         
/*  148 */         String completionDateString = connector.getField("completion_date", "");
/*  149 */         if (completionDateString.length() > 0) {
/*  150 */           scheduledTask.setCompletionDate(MilestoneHelper.getDatabaseDate(completionDateString));
/*      */         }
/*      */         
/*  153 */         int selNo = scheduledTask.getReleaseID();
/*  154 */         int taskId = scheduledTask.getTaskID();
/*  155 */         scheduledTask.setMultCompleteDates(getMultCompleteDates(selNo, taskId));
/*      */ 
/*      */         
/*  158 */         scheduledTask.setAllowMultCompleteDatesFlag(connector.getBoolean("allow_mult_complete_dates_flag"));
/*      */ 
/*      */         
/*  161 */         String taskStatus = connector.getField("status", "");
/*  162 */         if (taskStatus.length() > 1) {
/*  163 */           scheduledTask.setScheduledTaskStatus(taskStatus);
/*      */         }
/*      */         
/*  166 */         int day = connector.getIntegerField("day_of_week");
/*  167 */         if (day > 0) {
/*  168 */           scheduledTask.setDayOfTheWeek(new Day(connector.getIntegerField("day_of_week")));
/*      */         }
/*      */         
/*  171 */         String vendorString = connector.getField("vendor", "");
/*  172 */         if (vendorString.length() > 0) {
/*  173 */           scheduledTask.setVendor(vendorString);
/*      */         }
/*      */         
/*  176 */         int taskAbbrevID = connector.getIntegerField("abbrev_id");
/*  177 */         scheduledTask.setTaskAbbreviationID(taskAbbrevID);
/*      */ 
/*      */         
/*  180 */         int taskID = connector.getIntegerField("task_id");
/*  181 */         scheduledTask.setScheduledTaskID(taskID);
/*      */ 
/*      */         
/*  184 */         String taskDept = connector.getField("department", "");
/*  185 */         scheduledTask.setDepartment(taskDept);
/*      */ 
/*      */         
/*  188 */         scheduledTask.setKeyTask(connector.getBoolean("key_task_indicator"));
/*      */ 
/*      */         
/*  191 */         scheduledTask.setAuthorizationName(connector.getField("authorization_name", ""));
/*      */ 
/*      */         
/*  194 */         String authDateString = connector.getField("authorization_date", "");
/*  195 */         if (authDateString.length() > 0) {
/*  196 */           scheduledTask.setAuthorizationDate(MilestoneHelper.getDatabaseDate(authDateString));
/*      */         }
/*      */         
/*  199 */         String comments = connector.getField("comments", "");
/*  200 */         scheduledTask.setComments(comments);
/*      */ 
/*      */         
/*  203 */         scheduledTask.setName(connector.getField("name", ""));
/*      */ 
/*      */         
/*  206 */         scheduledTask.setTaskWeeksToRelease(connector.getIntegerField("task_weeks_to_release"));
/*      */ 
/*      */ 
/*      */         
/*  210 */         long lastUpdatedLong = Long.parseLong(connector.getField("last_updated_ck", ""), 16);
/*  211 */         scheduledTask.setLastUpdatedCk(lastUpdatedLong);
/*      */         
/*  213 */         precache.add(scheduledTask);
/*      */         
/*  215 */         scheduledTask = null;
/*      */         
/*  217 */         connector.next();
/*      */       } 
/*  219 */       connector.close();
/*  220 */       schedule.setTasks(precache);
/*  221 */       return schedule;
/*      */     } 
/*      */     
/*      */     try {
/*  225 */       connector.close();
/*  226 */     } catch (Exception exception) {}
/*      */     
/*  228 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Schedule saveSchedule(Selection selection, Schedule schedule, User user) {
/*  236 */     Vector tasks = schedule.getTasks();
/*      */     
/*  238 */     String dueDateString = "";
/*  239 */     String completeString = "";
/*  240 */     String keyTask = "0";
/*  241 */     String lastUpdated = "";
/*  242 */     int dayOfTheWeek = -1;
/*      */     
/*  244 */     for (int i = 0; i < tasks.size(); i++) {
/*      */       
/*  246 */       ScheduledTask task = (ScheduledTask)tasks.get(i);
/*  247 */       dueDateString = "";
/*  248 */       completeString = "";
/*      */       
/*  250 */       if (task.getDueDate() != null) {
/*  251 */         dueDateString = MilestoneHelper.getFormatedDate(task.getDueDate());
/*      */       }
/*  253 */       if (task.getCompletionDate() != null) {
/*  254 */         completeString = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*      */       }
/*  256 */       keyTask = "0";
/*  257 */       if (task.getIsKeytask()) {
/*  258 */         keyTask = "1";
/*      */       }
/*  260 */       long timestamp = task.setLastUpdatedCk();
/*      */       
/*  262 */       String spString = "sp_upd_Release_Detail ";
/*  263 */       int releaseId = task.getReleaseID();
/*      */       
/*  265 */       if (task.getReleaseID() < 0) {
/*      */         
/*  267 */         spString = "sp_ins_Release_Detail ";
/*  268 */         releaseId = selection.getSelectionID();
/*      */       } 
/*      */       
/*  271 */       if (task.getDayOfTheWeek() != null) {
/*  272 */         dayOfTheWeek = task.getDayOfTheWeek().getDayID();
/*      */       }
/*  274 */       int ownerId = -1;
/*  275 */       if (task.getOwner() != null) {
/*  276 */         ownerId = task.getOwner().getStructureID();
/*      */       }
/*      */       
/*  279 */       if (task.getScheduledTaskStatus().equals("Auto")) {
/*      */         
/*  281 */         String autoCloseDateStr = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*  282 */         System.out.println("<< Auto Close date check " + autoCloseDateStr);
/*  283 */         if (!autoCloseDateStr.equals("9/9/99")) {
/*  284 */           task.setScheduledTaskStatus("");
/*      */         }
/*      */       } 
/*  287 */       String query = String.valueOf(spString) + 
/*  288 */         releaseId + "," + 
/*  289 */         task.getTaskID() + "," + 
/*  290 */         ownerId + "," + 
/*  291 */         "'" + dueDateString + "'," + 
/*  292 */         "0" + "," + 
/*  293 */         "'" + MilestoneHelper.escapeSingleQuotes(completeString) + "'," + 
/*  294 */         "'" + MilestoneHelper.escapeSingleQuotes(task.getScheduledTaskStatus()) + "'," + 
/*  295 */         dayOfTheWeek + "," + 
/*  296 */         task.getWeeksToRelease() + "," + 
/*  297 */         "'" + MilestoneHelper.escapeSingleQuotes(task.getVendor()) + "'," + 
/*  298 */         keyTask + "," + 
/*  299 */         "'" + MilestoneHelper.escapeSingleQuotes(task.getAuthorizationName()) + "'," + 
/*  300 */         "'" + "'," + 
/*  301 */         "'" + MilestoneHelper.escapeSingleQuotes(task.getComments()) + "'," + 
/*  302 */         user.getUserId();
/*      */       
/*  304 */       if (task.getReleaseID() > 0) {
/*  305 */         query = String.valueOf(query) + "," + timestamp;
/*      */       }
/*  307 */       JdbcConnector connector = MilestoneHelper.getConnector(query);
/*  308 */       connector.runQuery();
/*  309 */       connector.close();
/*      */       
/*  311 */       String timestampQuery = "Select last_updated_ck, last_updated_on from release_detail where release_id = " + task.getReleaseID() + " and task_id =" + task.getTaskID();
/*  312 */       JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
/*  313 */       connectorTimestamp.runQuery();
/*      */       
/*  315 */       if (connectorTimestamp.more()) {
/*      */         
/*  317 */         task.setLastUpdatedCk(Long.parseLong(connectorTimestamp.getField("last_updated_ck", ""), 16));
/*  318 */         lastUpdated = connectorTimestamp.getField("last_updated_on", "");
/*  319 */         if (lastUpdated.length() > 0)
/*      */         {
/*      */ 
/*      */           
/*  323 */           task.setLastUpdateDate(
/*  324 */               MilestoneHelper.getDatabaseDate(connectorTimestamp.getField("last_updated_on", ""))); } 
/*      */       } 
/*  326 */       connectorTimestamp.close();
/*      */       
/*  328 */       if (spString.equalsIgnoreCase("sp_ins_Release_Detail ")) {
/*      */         
/*  330 */         String selectionTimestampQuery = "SELECT last_updated_ck, last_updated_on FROM release_header WHERE release_id = " + 
/*      */           
/*  332 */           selection.getSelectionID() + 
/*  333 */           ";";
/*      */         
/*  335 */         JdbcConnector selectionConnectorTimestamp = MilestoneHelper.getConnector(selectionTimestampQuery);
/*  336 */         selectionConnectorTimestamp.runQuery();
/*      */         
/*  338 */         if (selectionConnectorTimestamp.more()) {
/*      */           
/*  340 */           selection.setLastUpdatedCheck(Long.parseLong(selectionConnectorTimestamp.getField("last_updated_ck"), 16));
/*  341 */           selection.setLastUpdateDate(MilestoneHelper.getDatabaseDate(selectionConnectorTimestamp.getField("last_updated_on")));
/*      */         } 
/*  343 */         selectionConnectorTimestamp.close();
/*      */       } 
/*      */     } 
/*      */     
/*  347 */     return schedule;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void copySchedule(Schedule schedule, Selection selection, User user) {
/*  355 */     Vector tasks = schedule.getTasks();
/*      */ 
/*      */     
/*  358 */     recalculateDueDates(schedule, selection);
/*      */     
/*  360 */     String dueDateString = "";
/*  361 */     String completeString = "";
/*  362 */     String keyTask = "0";
/*  363 */     String lastUpdated = "";
/*      */     
/*  365 */     for (int i = 0; i < tasks.size(); i++) {
/*      */       
/*  367 */       ScheduledTask task = (ScheduledTask)tasks.get(i);
/*  368 */       dueDateString = "";
/*  369 */       completeString = "";
/*      */       
/*  371 */       if (task.getDueDate() != null) {
/*  372 */         dueDateString = MilestoneHelper.getFormatedDate(task.getDueDate());
/*      */       }
/*  374 */       if (task.getCompletionDate() != null) {
/*  375 */         completeString = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*      */       }
/*  377 */       keyTask = "0";
/*  378 */       if (task.getIsKeytask()) {
/*  379 */         keyTask = "1";
/*      */       }
/*  381 */       int owner = -1;
/*  382 */       int familyID = -1;
/*      */       
/*  384 */       if (selection != null && selection.getEnvironment() != null)
/*      */       {
/*      */         
/*  387 */         familyID = selection.getEnvironment().getParentID();
/*      */       }
/*      */ 
/*      */       
/*  391 */       if (task.getOwner() != null && !task.getOwner().getName().equalsIgnoreCase("Enterprise")) {
/*  392 */         owner = task.getOwner().getStructureID();
/*  393 */       } else if (task.getOwner() != null && task.getOwner().getName().equalsIgnoreCase("Enterprise")) {
/*  394 */         owner = familyID;
/*      */       } 
/*  396 */       String query = "sp_ins_Release_Detail " + 
/*  397 */         selection.getSelectionID() + "," + 
/*  398 */         task.getTaskID() + "," + 
/*  399 */         owner + "," + 
/*  400 */         "'" + dueDateString + "'," + 
/*  401 */         "0" + "," + 
/*  402 */         "'" + completeString + "'," + 
/*  403 */         "'" + task.getScheduledTaskStatus() + "'," + 
/*  404 */         task.getDayOfTheWeek().getDayID() + "," + 
/*  405 */         task.getWeeksToRelease() + "," + 
/*  406 */         "'" + task.getVendor() + "'," + 
/*  407 */         keyTask + "," + 
/*  408 */         "'" + MilestoneHelper.escapeSingleQuotes(task.getAuthorizationName()) + "'," + 
/*  409 */         "'" + "'," + 
/*  410 */         "'" + MilestoneHelper.escapeSingleQuotes(task.getComments()) + "'," + 
/*  411 */         user.getUserId();
/*      */       
/*  413 */       JdbcConnector connector = MilestoneHelper.getConnector(query);
/*  414 */       connector.runQuery();
/*  415 */       connector.close();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTaskEditAccess(User user, int familyID) {
/*  425 */     Vector companyAcls = user.getAcl().getCompanyAcl();
/*  426 */     int access = 0;
/*  427 */     for (int i = 0; i < companyAcls.size(); i++) {
/*      */ 
/*      */       
/*  430 */       CompanyAcl companyAcl = (CompanyAcl)companyAcls.get(i);
/*  431 */       Company company = (Company)MilestoneHelper.getStructureObject(companyAcl.getCompanyId());
/*      */ 
/*      */       
/*  434 */       int familyIdCk = -1;
/*  435 */       if (company != null) {
/*  436 */         familyIdCk = company.getParentEnvironment().getParentFamily().getStructureID();
/*      */       }
/*  438 */       if (familyIdCk == familyID && companyAcl != null)
/*      */       {
/*      */         
/*  441 */         if (access < companyAcl.getAccessSchedule()) {
/*      */           
/*  443 */           access = companyAcl.getAccessSchedule();
/*      */ 
/*      */           
/*  446 */           if (companyAcl.getAccessSchedule() == 2) {
/*      */             break;
/*      */           }
/*      */         } 
/*      */       }
/*      */     } 
/*  452 */     return access;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTaskEditAccess(User user, int familyID, Hashtable aclFamilyHash) {
/*  462 */     String accessLevel = (String)aclFamilyHash.get(String.valueOf(familyID));
/*      */     
/*  464 */     if (accessLevel != null && !accessLevel.equals("")) {
/*  465 */       return Integer.parseInt(accessLevel);
/*      */     }
/*  467 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Hashtable buildTaskEditAccess(Vector companyAcls, User user) {
/*  477 */     Hashtable familyHash = new Hashtable();
/*      */     
/*  479 */     if (companyAcls != null)
/*      */     {
/*  481 */       for (int i = 0; i < companyAcls.size(); i++) {
/*      */         
/*  483 */         CompanyAcl companyAcl = (CompanyAcl)companyAcls.get(i);
/*  484 */         Company company = (Company)MilestoneHelper.getStructureObject(companyAcl.getCompanyId());
/*      */         
/*  486 */         int familyIdCk = -1;
/*  487 */         if (company != null) {
/*  488 */           familyIdCk = company.getParentEnvironment().getParentFamily()
/*  489 */             .getStructureID();
/*      */ 
/*      */           
/*  492 */           if (!familyHash.containsKey(String.valueOf(familyIdCk))) {
/*      */             
/*  494 */             familyHash.put(String.valueOf(familyIdCk), 
/*  495 */                 String.valueOf(companyAcl.getAccessSchedule()));
/*      */           
/*      */           }
/*      */           else {
/*      */             
/*  500 */             String accessStr = (String)familyHash.get(String.valueOf(
/*  501 */                   familyIdCk));
/*  502 */             int access = Integer.parseInt(accessStr);
/*      */             
/*  504 */             if (access < companyAcl.getAccessSchedule()) {
/*  505 */               familyHash.put(String.valueOf(familyIdCk), 
/*  506 */                   String.valueOf(companyAcl.getAccessSchedule()));
/*      */             }
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/*  512 */         if (!user.newSelectionEditAccess && companyAcl.getAccessSelection() == 2) {
/*  513 */           user.newSelectionEditAccess = true;
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*  518 */     return familyHash;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addTask(Task task, User user, Selection selection) {
/*  527 */     String keyTask = "0";
/*  528 */     keyTask = task.getIsKeyTask() ? "1" : "0";
/*  529 */     int dayId = 8;
/*  530 */     int weeks = -1;
/*  531 */     int activeFlag = 0;
/*      */     
/*  533 */     if (task.getDayOfTheWeek() != null) {
/*  534 */       dayId = task.getDayOfTheWeek().getDayID();
/*      */     }
/*  536 */     if (task.getActiveFlag()) {
/*      */       
/*  538 */       activeFlag = 0;
/*      */     }
/*      */     else {
/*      */       
/*  542 */       activeFlag = 1;
/*      */     } 
/*      */     
/*  545 */     weeks = task.getWeeksToRelease();
/*  546 */     if (weeks > 0 && dayId < 0) {
/*  547 */       dayId = 8;
/*  548 */     } else if (weeks == -10) {
/*  549 */       dayId = 9;
/*      */     } 
/*  551 */     Calendar dueDate = null;
/*      */     
/*  553 */     if (selection.getStreetDate() != null && weeks != -10) {
/*  554 */       dueDate = MilestoneHelper.getDueDate(selection.getStreetDate(), dayId, weeks);
/*      */     }
/*  556 */     String dueDateString = "";
/*  557 */     if (dueDate != null && weeks != -10) {
/*      */       
/*  559 */       dueDateString = MilestoneHelper.getFormatedDate(dueDate);
/*      */     }
/*  561 */     else if (weeks == -10) {
/*      */       
/*  563 */       DatePeriod dp = MilestoneHelper.getReleaseWeek(selection);
/*      */ 
/*      */ 
/*      */       
/*  567 */       if (dp != null) {
/*  568 */         dueDateString = MilestoneHelper.getFormatedDate(dp.getSolDate());
/*      */       }
/*      */     } 
/*  571 */     int owner = -1;
/*  572 */     int familyID = -1;
/*      */     
/*  574 */     if (selection != null && selection.getEnvironment() != null)
/*      */     {
/*      */       
/*  577 */       familyID = selection.getEnvironment().getParentID();
/*      */     }
/*      */ 
/*      */     
/*  581 */     if (task.getOwner() != null && !task.getOwner().getName().equalsIgnoreCase("Enterprise")) {
/*      */       
/*  583 */       owner = task.getOwner().getStructureID();
/*      */     }
/*  585 */     else if (task.getOwner() != null && task.getOwner().getName().equalsIgnoreCase("Enterprise")) {
/*      */       
/*  587 */       owner = familyID;
/*      */     } 
/*      */     
/*  590 */     String query = "sp_ins_Release_Detail " + 
/*  591 */       selection.getSelectionID() + "," + 
/*  592 */       task.getTaskID() + "," + 
/*  593 */       owner + "," + 
/*  594 */       "'" + dueDateString + "'," + 
/*  595 */       "0" + "," + 
/*  596 */       "NULL" + "," + 
/*  597 */       "''" + "," + 
/*  598 */       dayId + "," + 
/*  599 */       weeks + "," + 
/*  600 */       "''" + "," + 
/*  601 */       keyTask + "," + 
/*  602 */       "'" + MilestoneHelper.escapeSingleQuotes(user.getName()) + "'," + 
/*  603 */       "'" + MilestoneHelper.getFormatedDate(Calendar.getInstance()) + "'," + 
/*  604 */       "'" + MilestoneHelper.escapeSingleQuotes(task.getComments()) + "'," + 
/*  605 */       user.getUserId();
/*      */     
/*  607 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/*  608 */     connector.runQuery();
/*  609 */     connector.close();
/*      */ 
/*      */     
/*  612 */     String selectionTimestampQuery = "SELECT last_updated_ck, last_updated_on FROM release_header WHERE release_id = " + 
/*      */       
/*  614 */       selection.getSelectionID() + 
/*  615 */       ";";
/*      */     
/*  617 */     JdbcConnector selectionConnectorTimestamp = MilestoneHelper.getConnector(selectionTimestampQuery);
/*  618 */     selectionConnectorTimestamp.runQuery();
/*  619 */     if (selectionConnectorTimestamp.more()) {
/*      */       
/*  621 */       selection.setLastUpdatedCheck(Long.parseLong(selectionConnectorTimestamp.getField("last_updated_ck"), 16));
/*  622 */       selection.setLastUpdateDate(MilestoneHelper.getDatabaseDate(selectionConnectorTimestamp.getField("last_updated_on")));
/*      */     } 
/*  624 */     selectionConnectorTimestamp.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void deleteTask(String taskId, Selection selection, User updatingUser) {
/*  632 */     String query = "sp_del_Release_Detail " + 
/*  633 */       selection.getSelectionID() + "," + 
/*  634 */       taskId + "," + 
/*  635 */       updatingUser.getUserId();
/*      */     
/*  637 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/*  638 */     connector.runQuery();
/*  639 */     connector.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void deleteAllTasks(Selection selection, User updatingUser) {
/*  647 */     String query = "sp_del_Release_Details " + 
/*  648 */       selection.getSelectionID() + "," + 
/*  649 */       updatingUser.getUserId();
/*      */     
/*  651 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/*  652 */     connector.runQuery();
/*  653 */     connector.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTaskNotepadQuery(Context context, Notepad notepad) {
/*  665 */     if (notepad != null) {
/*      */ 
/*      */       
/*  668 */       int releaseId = ((Selection)context.getSessionValue("Selection")).getSelectionID();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  673 */       String taskNameSearch = context.getParameter("TaskNameSearch");
/*  674 */       String keyTaskSearch = context.getParameter("KeyTaskSearch");
/*  675 */       String taskOwnerSearch = context.getParameter("TaskOwnerSearch");
/*  676 */       String taskDepartmentSearch = context.getParameter("TaskDepartmentSearch");
/*      */ 
/*      */       
/*  679 */       int keyTask = -1;
/*      */       
/*  681 */       if (keyTaskSearch != null && keyTaskSearch.equalsIgnoreCase("yes")) {
/*      */         
/*  683 */         keyTask = 1;
/*      */       }
/*  685 */       else if (keyTaskSearch != null && keyTaskSearch.equalsIgnoreCase("no")) {
/*      */         
/*  687 */         keyTask = 0;
/*      */       } 
/*      */ 
/*      */       
/*  691 */       String query = "SELECT * FROM vi_Task WHERE task_id  not in (select task_id from vi_Release_Detail  where release_id = " + 
/*      */         
/*  693 */         releaseId + ")";
/*      */ 
/*      */ 
/*      */       
/*  697 */       if (keyTask > -1) {
/*  698 */         query = String.valueOf(query) + " AND key_task_indicator = " + keyTask;
/*      */       }
/*  700 */       if (MilestoneHelper.isStringNotEmpty(taskNameSearch)) {
/*  701 */         query = String.valueOf(query) + " AND name " + MilestoneHelper.setWildCardsEscapeSingleQuotes(taskNameSearch);
/*      */       }
/*  703 */       if (MilestoneHelper.isStringNotEmpty(taskOwnerSearch) && !taskOwnerSearch.equals("0")) {
/*  704 */         query = String.valueOf(query) + " AND owner = '" + taskOwnerSearch + "'";
/*      */       }
/*  706 */       if (MilestoneHelper.isStringNotEmpty(taskDepartmentSearch) && !taskDepartmentSearch.equals("0") && !taskDepartmentSearch.equals("-1")) {
/*  707 */         query = String.valueOf(query) + " AND department like '%" + taskDepartmentSearch + "%'";
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  712 */       String order = " ORDER BY name";
/*      */       
/*  714 */       notepad.setSearchQuery(query);
/*  715 */       notepad.setOrderBy(order);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Vector getScheduleTaskNotepadList(int releaseId, int userId, Notepad notepad, Context context) {
/*  722 */     String query = "";
/*      */ 
/*      */ 
/*      */     
/*  726 */     if (notepad != null && !notepad.getSearchQuery().equals("")) {
/*      */       
/*  728 */       query = notepad.getSearchQuery();
/*  729 */       query = String.valueOf(query) + notepad.getOrderBy();
/*      */     }
/*      */     else {
/*      */       
/*  733 */       query = String.valueOf(getDefaultTaskSearchQuery(releaseId)) + " ORDER BY name";
/*      */     } 
/*      */     
/*  736 */     Task task = null;
/*  737 */     Vector precache = new Vector();
/*  738 */     Vector companies = MilestoneHelper.getUserCompanies(context);
/*      */     
/*  740 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/*  741 */     connector.runQuery();
/*      */ 
/*      */ 
/*      */     
/*  745 */     while (connector.more()) {
/*      */ 
/*      */       
/*  748 */       task = getScheduleTaskNotepadObject(connector);
/*      */       
/*  750 */       if (task != null) {
/*      */         
/*  752 */         Company company = null;
/*  753 */         if (companies != null)
/*      */         {
/*  755 */           for (int j = 0; j < companies.size(); j++) {
/*      */             
/*  757 */             company = (Company)companies.get(j);
/*  758 */             if (company != null && company.getParentEnvironment().getParentFamily() != null && task.getOwner() != null)
/*      */             {
/*  760 */               if (company.getParentEnvironment().getParentFamily().getStructureID() == task.getOwner().getStructureID()) {
/*      */                 
/*  762 */                 if (task.getActiveFlag())
/*      */                 {
/*  764 */                   precache.addElement(task);
/*      */                 }
/*      */                 break;
/*      */               } 
/*      */             }
/*      */           } 
/*      */         }
/*      */       } 
/*  772 */       task = null;
/*  773 */       connector.next();
/*      */     } 
/*  775 */     connector.close();
/*      */     
/*  777 */     return precache;
/*      */   }
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
/*      */   public Task getScheduleTaskNotepadObject(JdbcConnector connector) {
/*  791 */     Task task = null;
/*      */     
/*  793 */     if (connector != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  798 */       task = new Task();
/*  799 */       task.setTaskID(connector.getIntegerField("task_id"));
/*      */ 
/*      */       
/*  802 */       task.setTaskName(connector.getField("name", ""));
/*      */ 
/*      */       
/*  805 */       task.setWeeksToRelease(connector.getIntegerField("weeks_to_release"));
/*      */ 
/*      */       
/*  808 */       task.setDayOfTheWeek(new Day(connector.getIntegerField("day_of_week")));
/*      */ 
/*      */       
/*  811 */       task.setDepartment(connector.getField("department", ""));
/*      */ 
/*      */       
/*  814 */       task.setOwner((Family)MilestoneHelper.getStructureObject(connector.getIntegerField("owner")));
/*      */ 
/*      */       
/*  817 */       task.setTaskAbbreviation(connector.getIntegerField("abbrev_id"));
/*      */ 
/*      */       
/*  820 */       int taskID = connector.getIntegerField("task_id");
/*  821 */       task.setTaskID(taskID);
/*      */ 
/*      */ 
/*      */       
/*  825 */       task.setIsKeyTask(connector.getBoolean("key_task_indicator"));
/*      */ 
/*      */ 
/*      */       
/*  829 */       task.setActiveFlag(connector.getBoolean("active_flag"));
/*      */     } 
/*      */     
/*  832 */     return task;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Vector getSuggestedTemplates(User user, Selection selection, Context context) {
/*  838 */     Vector precache = new Vector();
/*  839 */     if (selection != null) {
/*      */       
/*  841 */       String query = "SELECT *   FROM vi_Template_Header th with (nolock) WHERE product_line = '" + 
/*      */         
/*  843 */         SelectionManager.getLookupObjectValue(selection.getProductCategory()) + "'" + 
/*  844 */         " AND release_type = '" + SelectionManager.getLookupObjectValue(selection.getReleaseType()) + "'" + 
/*  845 */         " AND configuration = '" + selection.getSelectionConfig().getSelectionConfigurationAbbreviation() + "'" + 
/*  846 */         " AND sub_configuration = '" + selection.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation() + "'" + 
/*  847 */         " AND (productType = " + (selection.getIsDigital() ? "1" : "0") + 
/*  848 */         " OR productType = 2) " + 
/*  849 */         " AND ( SELECT Count(*) " + 
/*  850 */         "         FROM vi_Template_Detail td with(nolock) " + 
/*  851 */         "        WHERE td.Template_Id = th.Template_Id ) > 0 ";
/*      */       
/*  853 */       Vector companies = MilestoneHelper.getUserCompanies(context);
/*  854 */       Company company = null;
/*      */       
/*  856 */       if (companies.size() > 0) {
/*      */         
/*  858 */         query = String.valueOf(query) + " AND (";
/*  859 */         for (int i = 0; i < companies.size(); i++) {
/*      */           
/*  861 */           company = (Company)companies.get(i);
/*  862 */           if (company != null && company.getParentEnvironment().getParentFamily() != null)
/*  863 */             query = String.valueOf(query) + " owner = " + String.valueOf((company.getParentEnvironment().getParentFamily()).structureID); 
/*  864 */           if (i < companies.size() - 1)
/*  865 */             query = String.valueOf(query) + " OR "; 
/*      */         } 
/*  867 */         query = String.valueOf(query) + " )";
/*      */       } 
/*      */ 
/*      */       
/*  871 */       query = String.valueOf(query) + " ORDER BY name ";
/*      */       
/*  873 */       Template template = null;
/*      */       
/*  875 */       JdbcConnector connector = MilestoneHelper.getConnector(query);
/*  876 */       connector.runQuery();
/*      */ 
/*      */ 
/*      */       
/*  880 */       while (connector.more()) {
/*      */         
/*  882 */         template = new Template();
/*      */         
/*  884 */         template.setTemplateID(connector.getIntegerField("template_id"));
/*  885 */         template.setTemplateName(connector.getField("name", ""));
/*  886 */         template.setOwner((Family)MilestoneHelper.getStructureObject(connector.getIntegerField("owner")));
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  891 */         int owner = connector.getInt("owner", 0);
/*  892 */         if (owner == 12)
/*      */         {
/*  894 */           template.order = owner;
/*      */         }
/*      */ 
/*      */         
/*  898 */         precache.addElement(template);
/*      */         
/*  900 */         template = null;
/*  901 */         connector.next();
/*      */       } 
/*  903 */       connector.close();
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  908 */     Collections.sort(precache);
/*      */ 
/*      */     
/*  911 */     return precache;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Vector getTemplatesSearch(User user, Selection selection, String name, String config, String productLine, Context context) {
/*  917 */     String query = "SELECT *   FROM vi_Template_Header th WHERE";
/*      */ 
/*      */     
/*  920 */     String whereClause = "";
/*      */     
/*  922 */     if (productLine.length() > 0) {
/*  923 */       whereClause = String.valueOf(whereClause) + " product_line = '" + productLine + "'";
/*      */     }
/*  925 */     if (config.length() > 0) {
/*      */       
/*  927 */       if (whereClause.length() > 0)
/*  928 */         whereClause = String.valueOf(whereClause) + " AND "; 
/*  929 */       whereClause = String.valueOf(whereClause) + " configuration = '" + config + "'";
/*      */     } 
/*      */     
/*  932 */     if (name.length() > 0) {
/*      */       
/*  934 */       if (whereClause.length() > 0)
/*  935 */         whereClause = String.valueOf(whereClause) + " AND "; 
/*  936 */       whereClause = String.valueOf(whereClause) + " name like '%" + name + "%'";
/*      */     } 
/*      */     
/*  939 */     if (whereClause.length() > 0)
/*  940 */       whereClause = String.valueOf(whereClause) + " AND "; 
/*  941 */     whereClause = String.valueOf(whereClause) + " (productType = " + (selection.getIsDigital() ? "1" : "0") + " or productType = 2) ";
/*      */     
/*  943 */     if (whereClause.length() > 0) {
/*  944 */       whereClause = String.valueOf(whereClause) + " AND ";
/*      */     }
/*  946 */     whereClause = String.valueOf(whereClause) + " ( SELECT Count(*)          FROM vi_Template_Detail td         WHERE td.Template_Id = th.Template_Id ) > 0 ";
/*      */ 
/*      */ 
/*      */     
/*  950 */     query = String.valueOf(query) + whereClause;
/*      */     
/*  952 */     Vector companies = MilestoneHelper.getUserCompanies(context);
/*  953 */     Company company = null;
/*  954 */     if (companies.size() > 0) {
/*      */       
/*  956 */       query = String.valueOf(query) + " AND (";
/*  957 */       for (int i = 0; i < companies.size(); i++) {
/*      */         
/*  959 */         company = (Company)companies.get(i);
/*  960 */         if (company != null && company.getParentEnvironment().getParentFamily() != null) {
/*      */           
/*  962 */           query = String.valueOf(query) + " owner = " + String.valueOf((company.getParentEnvironment().getParentFamily()).structureID);
/*  963 */           if (i < companies.size() - 1)
/*  964 */             query = String.valueOf(query) + " OR "; 
/*      */         } 
/*      */       } 
/*  967 */       query = String.valueOf(query) + " )";
/*      */     } 
/*      */ 
/*      */     
/*  971 */     query = String.valueOf(query) + " ORDER BY name ";
/*      */ 
/*      */     
/*  974 */     Template template = null;
/*  975 */     Vector precache = new Vector();
/*      */     
/*  977 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/*  978 */     connector.runQuery();
/*      */ 
/*      */ 
/*      */     
/*  982 */     while (connector.more()) {
/*      */       
/*  984 */       template = new Template();
/*      */       
/*  986 */       template.setTemplateID(connector.getIntegerField("template_id"));
/*  987 */       template.setTemplateName(connector.getField("name", ""));
/*  988 */       template.setOwner((Family)MilestoneHelper.getStructureObject(connector.getIntegerField("owner")));
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  993 */       int owner = connector.getInt("owner", 0);
/*  994 */       if (owner == 12) {
/*  995 */         template.order = owner;
/*      */       }
/*      */ 
/*      */       
/*  999 */       precache.addElement(template);
/*      */       
/* 1001 */       template = null;
/* 1002 */       connector.next();
/*      */     } 
/* 1004 */     connector.close();
/*      */ 
/*      */ 
/*      */     
/* 1008 */     Collections.sort(precache);
/*      */ 
/*      */     
/* 1011 */     return precache;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void assignTemplate(User user, Selection selection, String templateId) {
/* 1019 */     Template template = TemplateManager.getInstance().getTemplate(Integer.parseInt(templateId), true);
/* 1020 */     Vector tasks = template.getTasks();
/* 1021 */     Task task = null;
/* 1022 */     Schedule schedule = new Schedule();
/* 1023 */     Vector scheduleTasks = new Vector();
/*      */     
/* 1025 */     schedule.setSelectionID(selection.getSelectionID());
/*      */ 
/*      */     
/* 1028 */     Family family = null;
/* 1029 */     int familyID = -1;
/* 1030 */     if (selection != null && selection.getEnvironment() != null) {
/*      */ 
/*      */       
/* 1033 */       familyID = selection.getEnvironment().getParentID();
/* 1034 */       family = (Family)MilestoneHelper.getStructureObject(familyID);
/*      */     } 
/*      */     
/* 1037 */     for (int i = 0; i < tasks.size(); i++) {
/*      */       
/* 1039 */       task = (Task)tasks.get(i);
/* 1040 */       ScheduledTask scheduledTask = getScheduledTask(task);
/*      */ 
/*      */       
/* 1043 */       if (task.getOwner() != null && task.getOwner().getName().equalsIgnoreCase("Enterprise"))
/*      */       {
/* 1045 */         if (family != null) {
/* 1046 */           scheduledTask.setOwner(family);
/*      */         }
/*      */       }
/*      */ 
/*      */       
/* 1051 */       scheduleTasks.add(scheduledTask);
/*      */       
/* 1053 */       task = null;
/*      */     } 
/*      */     
/* 1056 */     schedule.setTasks(scheduleTasks);
/*      */ 
/*      */     
/* 1059 */     recalculateDueDates(schedule, selection);
/*      */     
/* 1061 */     selection.setSchedule(schedule);
/*      */   }
/*      */ 
/*      */   
/*      */   public ScheduledTask getScheduledTask(Task task) {
/* 1066 */     ScheduledTask scheduledTask = new ScheduledTask();
/* 1067 */     scheduledTask.setReleaseID(-1);
/* 1068 */     scheduledTask.setTaskID(task.getTaskID());
/* 1069 */     scheduledTask.setComments(task.getComments());
/* 1070 */     scheduledTask.setName(task.getTaskName());
/* 1071 */     scheduledTask.setWeeksToRelease(task.getWeeksToRelease());
/* 1072 */     scheduledTask.setDayOfTheWeek(task.getDayOfTheWeek());
/* 1073 */     scheduledTask.setOwner(task.getOwner());
/* 1074 */     scheduledTask.setKeyTask(task.getIsKeyTask());
/* 1075 */     scheduledTask.setTaskAbbreviationID(task.getTaskAbbreviation());
/*      */ 
/*      */     
/* 1078 */     scheduledTask.setScheduledTaskID(task.getTaskID());
/* 1079 */     scheduledTask.setDepartment(task.getDepartment());
/*      */     
/* 1081 */     scheduledTask.setAllowMultCompleteDatesFlag(task.getAllowMultCompleteDatesFlag());
/*      */     
/* 1083 */     return scheduledTask;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ScheduledTask getScheduledTask(int selectionNo, int taskId) {
/* 1092 */     ScheduledTask scheduledTask = null;
/*      */     
/* 1094 */     String scheduledtaskQuery = "SELECT b.weeks_to_release as task_weeks_to_release, a.*, b.*   FROM vi_Release_Detail a,        vi_Task b  WHERE a.release_Id = " + 
/*      */ 
/*      */       
/* 1097 */       selectionNo + 
/* 1098 */       "   AND a.task_Id = " + taskId + 
/* 1099 */       "   AND a.task_Id = b.task_Id ";
/*      */     
/* 1101 */     JdbcConnector connector = MilestoneHelper.getConnector(scheduledtaskQuery);
/* 1102 */     connector.runQuery();
/*      */     
/* 1104 */     if (connector.more()) {
/* 1105 */       scheduledTask = new ScheduledTask();
/*      */ 
/*      */       
/* 1108 */       scheduledTask.setReleaseID(connector.getIntegerField("release_Id"));
/*      */ 
/*      */       
/* 1111 */       scheduledTask.setTaskID(connector.getIntegerField("task_id"));
/*      */ 
/*      */       
/* 1114 */       scheduledTask.setOwner((Family)MilestoneHelper.getStructureObject(connector.getIntegerField(
/* 1115 */               "owner")));
/*      */ 
/*      */       
/* 1118 */       int weeks = connector.getIntegerField("weeks_to_release");
/* 1119 */       if (weeks > 0 || weeks == -10) {
/* 1120 */         scheduledTask.setWeeksToRelease(connector.getIntegerField("weeks_to_release"));
/*      */       }
/*      */       
/* 1123 */       String dueDateString = connector.getField("due_date", "");
/* 1124 */       if (dueDateString.length() > 0) {
/* 1125 */         scheduledTask.setDueDate(MilestoneHelper.getDatabaseDate(dueDateString));
/*      */       }
/*      */       
/* 1128 */       String completionDateString = connector.getField("completion_date", "");
/* 1129 */       if (completionDateString.length() > 0) {
/* 1130 */         scheduledTask.setCompletionDate(MilestoneHelper.getDatabaseDate(completionDateString));
/*      */       }
/*      */       
/* 1133 */       scheduledTask.setMultCompleteDates(getMultCompleteDates(selectionNo, taskId));
/*      */ 
/*      */       
/* 1136 */       scheduledTask.setAllowMultCompleteDatesFlag(connector.getBoolean("allow_mult_complete_dates_flag"));
/*      */ 
/*      */       
/* 1139 */       String taskStatus = connector.getField("status", "");
/* 1140 */       if (taskStatus.length() > 1) {
/* 1141 */         scheduledTask.setScheduledTaskStatus(taskStatus);
/*      */       }
/*      */       
/* 1144 */       int day = connector.getIntegerField("day_of_week");
/* 1145 */       if (day > 0) {
/* 1146 */         scheduledTask.setDayOfTheWeek(new Day(connector.getIntegerField("day_of_week")));
/*      */       }
/*      */       
/* 1149 */       String vendorString = connector.getField("vendor", "");
/* 1150 */       if (vendorString.length() > 0) {
/* 1151 */         scheduledTask.setVendor(vendorString);
/*      */       }
/*      */       
/* 1154 */       int taskAbbrevID = connector.getIntegerField("abbrev_id");
/* 1155 */       scheduledTask.setTaskAbbreviationID(taskAbbrevID);
/*      */ 
/*      */       
/* 1158 */       int taskID = connector.getIntegerField("task_id");
/* 1159 */       scheduledTask.setScheduledTaskID(taskID);
/*      */ 
/*      */       
/* 1162 */       String taskDept = connector.getField("department", "");
/* 1163 */       scheduledTask.setDepartment(taskDept);
/*      */ 
/*      */       
/* 1166 */       scheduledTask.setKeyTask(connector.getBoolean("key_task_indicator"));
/*      */ 
/*      */       
/* 1169 */       scheduledTask.setAuthorizationName(connector.getField("authorization_name", ""));
/*      */ 
/*      */       
/* 1172 */       String authDateString = connector.getField("authorization_date", "");
/* 1173 */       if (authDateString.length() > 0) {
/* 1174 */         scheduledTask.setAuthorizationDate(MilestoneHelper.getDatabaseDate(authDateString));
/*      */       }
/*      */       
/* 1177 */       String comments = connector.getField("comments", "");
/* 1178 */       scheduledTask.setComments(comments);
/*      */ 
/*      */       
/* 1181 */       scheduledTask.setName(connector.getField("name", ""));
/*      */       
/* 1183 */       scheduledTask.setTaskWeeksToRelease(connector.getIntegerField("task_weeks_to_release"));
/*      */       
/* 1185 */       long lastUpdatedLong = Long.parseLong(connector.getField("last_updated_ck", ""), 16);
/* 1186 */       scheduledTask.setLastUpdatedCk(lastUpdatedLong);
/*      */     } 
/*      */     
/* 1189 */     connector.close();
/*      */     
/* 1191 */     return scheduledTask;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void assignSchedule(User user, Schedule schedule, String releaseId) {
/* 1200 */     Selection copySelection = SelectionManager.getInstance().getSelectionHeader(Integer.parseInt(releaseId));
/* 1201 */     copySchedule(schedule, copySelection, user);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Vector getScheduleSearch(User user, Selection selection, String artist, String title, String selectionString, String UPC, String prefix, String streetDate, String label, Context context) {
/* 1207 */     String query = " SELECT DISTINCT rh.artist_first_name, rh.artist_last_name, rh.release_id, rh.project_No, rh.artist, rh.title, rh.UPC,         rh.Selection_No, rh.Street_Date, rh.label_id    FROM vi_Release_Header rh,         vi_Release_Detail rd   WHERE rh.release_id = rd.release_id ";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1213 */     Company company = null;
/* 1214 */     Vector companies = MilestoneHelper.getUserCompanies(context);
/*      */     
/* 1216 */     String whereClause = " AND (";
/* 1217 */     for (int i = 0; i < companies.size(); i++) {
/*      */       
/* 1219 */       company = (Company)companies.get(i);
/* 1220 */       if (company != null)
/*      */       {
/* 1222 */         if (i < companies.size() - 1) {
/* 1223 */           whereClause = String.valueOf(whereClause) + " company_id = " + company.getStructureID() + " OR ";
/*      */         } else {
/* 1225 */           whereClause = String.valueOf(whereClause) + " company_id = " + company.getStructureID();
/*      */         } 
/*      */       }
/*      */     } 
/* 1229 */     whereClause = String.valueOf(whereClause) + ") ";
/*      */     
/* 1231 */     query = String.valueOf(query) + whereClause;
/*      */     
/* 1233 */     whereClause = "";
/*      */     
/* 1235 */     if (artist.length() > 0)
/*      */     {
/* 1237 */       whereClause = String.valueOf(whereClause) + "AND rh.artist LIKE '%" + artist + "%' ";
/*      */     }
/* 1239 */     if (title.length() > 0)
/*      */     {
/* 1241 */       whereClause = String.valueOf(whereClause) + "AND rh.title LIKE '%" + title + "%' ";
/*      */     }
/* 1243 */     if (selectionString.length() > 0)
/*      */     {
/* 1245 */       whereClause = String.valueOf(whereClause) + "AND rh.selection_No LIKE '%" + selectionString + "%' ";
/*      */     }
/* 1247 */     if (UPC.length() > 0)
/*      */     {
/* 1249 */       whereClause = String.valueOf(whereClause) + "AND rh.UPC LIKE '%" + UPC + "%' ";
/*      */     }
/* 1251 */     if (prefix.length() > 0)
/*      */     {
/* 1253 */       whereClause = String.valueOf(whereClause) + "AND rh.prefix LIKE '%" + prefix + "%' ";
/*      */     }
/*      */     
/* 1256 */     if (streetDate.length() > 0) {
/*      */       
/* 1258 */       Calendar x = MilestoneHelper.getDate(streetDate);
/* 1259 */       x.add(5, 1);
/*      */       
/* 1261 */       String nextDay = MilestoneHelper.getFormatedDate(x);
/* 1262 */       whereClause = String.valueOf(whereClause) + "AND rh.street_date >= '" + streetDate + "' AND rh.street_date < '" + nextDay + "' ";
/*      */     } 
/*      */     
/* 1265 */     if (label.length() > 0 && Integer.parseInt(label) > 0)
/*      */     {
/* 1267 */       whereClause = String.valueOf(whereClause) + " AND rh.label_id = " + label;
/*      */     }
/*      */ 
/*      */     
/* 1271 */     whereClause = String.valueOf(whereClause) + " AND rh.digital_flag = " + (selection.getIsDigital() ? "1" : "0");
/*      */     
/* 1273 */     query = String.valueOf(query) + whereClause;
/*      */     
/* 1275 */     query = String.valueOf(query) + whereClause + " ORDER BY artist";
/*      */     
/* 1277 */     Selection currentSelection = null;
/* 1278 */     Vector precache = new Vector();
/*      */     
/* 1280 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 1281 */     connector.runQuery();
/*      */ 
/*      */ 
/*      */     
/* 1285 */     while (connector.more()) {
/*      */ 
/*      */       
/* 1288 */       currentSelection = new Selection();
/*      */       
/* 1290 */       currentSelection.setSelectionID(connector.getIntegerField("release_id"));
/* 1291 */       currentSelection.setTitle(connector.getField("title", ""));
/*      */       
/* 1293 */       currentSelection.setArtistFirstName(connector.getField("artist_first_name", ""));
/* 1294 */       currentSelection.setArtistLastName(connector.getField("artist_last_name", ""));
/* 1295 */       currentSelection.setArtist(connector.getField("artist", ""));
/*      */       
/* 1297 */       String selectionNo = "";
/* 1298 */       if (connector.getFieldByName("selection_no") != null) {
/* 1299 */         selectionNo = connector.getFieldByName("selection_no");
/*      */       }
/* 1301 */       currentSelection.setSelectionNo(selectionNo);
/* 1302 */       currentSelection.setUpc(connector.getField("upc", ""));
/*      */       
/* 1304 */       String streetDateString = connector.getFieldByName("street_date");
/* 1305 */       if (streetDateString != null) {
/* 1306 */         currentSelection.setStreetDate(MilestoneHelper.getDatabaseDate(streetDateString));
/*      */       }
/* 1308 */       precache.addElement(currentSelection);
/* 1309 */       currentSelection = null;
/* 1310 */       connector.next();
/*      */     } 
/* 1312 */     connector.close();
/*      */     
/* 1314 */     return precache;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void recalculateDueDates(Schedule schedule, Selection selection) {
/* 1322 */     if (schedule != null) {
/*      */       
/* 1324 */       Vector tasks = schedule.getTasks();
/* 1325 */       Vector precache = new Vector();
/* 1326 */       ScheduledTask task = null;
/*      */       
/* 1328 */       for (int i = 0; i < tasks.size(); i++) {
/*      */         
/* 1330 */         task = (ScheduledTask)tasks.get(i);
/*      */         
/* 1332 */         if (task.getWeeksToRelease() == -10 && task.getCompletionDate() == null) {
/*      */           
/* 1334 */           DatePeriod dp = MilestoneHelper.getReleaseWeek(selection);
/*      */           
/* 1336 */           if (dp != null) {
/* 1337 */             task.setDueDate(dp.getSolDate());
/*      */           } else {
/* 1339 */             task.setDueDate(null);
/*      */           } 
/* 1341 */         } else if (task.getDayOfTheWeek() != null && task.getWeeksToRelease() >= 0 && task.getCompletionDate() == null) {
/*      */ 
/*      */           
/* 1344 */           if (selection.getIsDigital()) {
/* 1345 */             task.setDueDate(MilestoneHelper.getDueDate(selection.getDigitalRlsDate(), task.getDayOfTheWeek().getDayID(), task.getWeeksToRelease()));
/*      */           } else {
/* 1347 */             task.setDueDate(MilestoneHelper.getDueDate(selection.getStreetDate(), task.getDayOfTheWeek().getDayID(), task.getWeeksToRelease()));
/*      */           } 
/* 1349 */         } else if (task.getWeeksToRelease() >= 0 && task.getCompletionDate() == null) {
/*      */ 
/*      */           
/* 1352 */           if (selection.getIsDigital()) {
/* 1353 */             task.setDueDate(MilestoneHelper.getDueDate(selection.getDigitalRlsDate(), 8, task.getWeeksToRelease()));
/*      */           } else {
/* 1355 */             task.setDueDate(MilestoneHelper.getDueDate(selection.getStreetDate(), 8, task.getWeeksToRelease()));
/*      */           } 
/*      */         } 
/*      */         
/* 1359 */         if ((!selection.getIsDigital() && selection.getStreetDate() == null) || (
/* 1360 */           selection.getIsDigital() && selection.getDigitalRlsDate() == null)) {
/* 1361 */           task.setDueDate(null);
/*      */         }
/*      */ 
/*      */         
/* 1365 */         precache.add(task);
/* 1366 */         task = null;
/*      */       } 
/* 1368 */       schedule.setTasks(precache);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void recalculateAllDueDates(Schedule schedule, Selection selection) {
/* 1377 */     if (schedule != null) {
/*      */       
/* 1379 */       Vector tasks = schedule.getTasks();
/* 1380 */       Vector precache = new Vector();
/* 1381 */       ScheduledTask task = null;
/*      */       
/* 1383 */       for (int i = 0; i < tasks.size(); i++) {
/*      */         
/* 1385 */         task = (ScheduledTask)tasks.get(i);
/*      */         
/* 1387 */         if (task.getWeeksToRelease() == -10) {
/*      */           
/* 1389 */           DatePeriod dp = MilestoneHelper.getReleaseWeek(selection);
/*      */           
/* 1391 */           if (dp != null) {
/* 1392 */             task.setDueDate(dp.getSolDate());
/*      */           } else {
/* 1394 */             task.setDueDate(null);
/*      */           } 
/* 1396 */         } else if (task.getDayOfTheWeek() != null && task.getWeeksToRelease() >= 0) {
/*      */ 
/*      */           
/* 1399 */           if (selection.getIsDigital()) {
/* 1400 */             task.setDueDate(MilestoneHelper.getDueDate(selection.getDigitalRlsDate(), task.getDayOfTheWeek().getDayID(), task.getWeeksToRelease()));
/*      */           } else {
/* 1402 */             task.setDueDate(MilestoneHelper.getDueDate(selection.getStreetDate(), task.getDayOfTheWeek().getDayID(), task.getWeeksToRelease()));
/*      */           } 
/* 1404 */         } else if (task.getWeeksToRelease() >= 0) {
/*      */ 
/*      */           
/* 1407 */           if (selection.getIsDigital()) {
/* 1408 */             task.setDueDate(MilestoneHelper.getDueDate(selection.getDigitalRlsDate(), 8, task.getWeeksToRelease()));
/*      */           } else {
/* 1410 */             task.setDueDate(MilestoneHelper.getDueDate(selection.getStreetDate(), 8, task.getWeeksToRelease()));
/*      */           } 
/*      */         } 
/*      */         
/* 1414 */         if ((!selection.getIsDigital() && selection.getStreetDate() == null) || (
/* 1415 */           selection.getIsDigital() && selection.getDigitalRlsDate() == null)) {
/* 1416 */           task.setDueDate(null);
/*      */         }
/* 1418 */         precache.add(task);
/* 1419 */         task = null;
/*      */       } 
/* 1421 */       schedule.setTasks(precache);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean closeSchedule(Schedule schedule, Selection selection, User user) {
/* 1430 */     boolean close = true;
/* 1431 */     if (schedule != null) {
/*      */       
/* 1433 */       Vector tasks = schedule.getTasks();
/* 1434 */       Vector precache = new Vector();
/* 1435 */       ScheduledTask task = null;
/*      */       
/* 1437 */       for (int i = 0; i < tasks.size(); i++) {
/*      */         
/* 1439 */         task = (ScheduledTask)tasks.get(i);
/* 1440 */         if (task.getCompletionDate() == null)
/*      */         {
/*      */ 
/*      */           
/* 1444 */           if (MilestoneHelper.isUml(task) || MilestoneHelper.isEcommerce(task)) {
/*      */             
/* 1446 */             close = false;
/*      */             break;
/*      */           } 
/*      */         }
/* 1450 */         task = null;
/*      */       } 
/*      */ 
/*      */       
/* 1454 */       if (close) {
/*      */ 
/*      */         
/* 1457 */         selection.setSelectionStatus((SelectionStatus)SelectionManager.getLookupObject("CLOSED", Cache.getSelectionStatusList()));
/* 1458 */         SelectionManager.getInstance().updateStatusToClose(selection);
/*      */ 
/*      */ 
/*      */         
/* 1462 */         Calendar labelCmpDt = MilestoneHelper.getDate("9/9/99");
/* 1463 */         for (int i = 0; i < tasks.size(); i++) {
/*      */           
/* 1465 */           task = (ScheduledTask)tasks.get(i);
/*      */           
/* 1467 */           if (task.getCompletionDate() == null && !MilestoneHelper.isUml(task) && !MilestoneHelper.isEcommerce(task))
/* 1468 */             UpdateCompletionDate(task.releaseID, task.taskID, user.userId, labelCmpDt); 
/* 1469 */           task = null;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1474 */     return close;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearDates(Schedule schedule) {
/* 1482 */     Vector tasks = schedule.getTasks();
/* 1483 */     Vector precache = new Vector();
/* 1484 */     ScheduledTask task = null;
/*      */     
/* 1486 */     for (int i = 0; i < tasks.size(); i++) {
/*      */       
/* 1488 */       task = (ScheduledTask)tasks.get(i);
/* 1489 */       task.setDueDate(null);
/* 1490 */       task.setWeeksToRelease(-1);
/* 1491 */       task.setDayOfTheWeek(null);
/* 1492 */       precache.add(task);
/* 1493 */       task = null;
/*      */     } 
/* 1495 */     schedule.setTasks(precache);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDefaultTaskSearchQuery(int releaseId) {
/* 1505 */     query = "";
/*      */     
/* 1507 */     return "SELECT * FROM vi_Task WHERE task_id  not in (select task_id from vi_Release_Detail  where release_id = " + 
/*      */       
/* 1509 */       releaseId + ")";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Schedule filterSchedule(Schedule schedule, String filter) {
/* 1521 */     if (schedule != null && schedule.getTasks().size() > 0) {
/*      */       
/* 1523 */       filter = filter.trim();
/* 1524 */       if (filter.length() > 0) {
/*      */         
/* 1526 */         Vector tasks = schedule.getTasks();
/* 1527 */         Vector precache = new Vector();
/* 1528 */         if (filter.equalsIgnoreCase("Only UML Tasks")) {
/*      */           
/* 1530 */           for (int i = 0; i < tasks.size(); i++) {
/*      */             
/* 1532 */             if (MilestoneHelper.isUml((ScheduledTask)tasks.get(i)))
/* 1533 */               precache.add((ScheduledTask)tasks.get(i)); 
/*      */           } 
/* 1535 */           schedule.setTasks(precache);
/*      */         }
/* 1537 */         else if (filter.equalsIgnoreCase("Only Label Tasks")) {
/*      */           
/* 1539 */           for (int j = 0; j < tasks.size(); j++) {
/*      */             
/* 1541 */             if (!MilestoneHelper.isUml((ScheduledTask)tasks.get(j)) && 
/* 1542 */               !MilestoneHelper.isEcommerce((ScheduledTask)tasks.get(j)))
/* 1543 */               precache.add((ScheduledTask)tasks.get(j)); 
/*      */           } 
/* 1545 */           schedule.setTasks(precache);
/*      */         }
/* 1547 */         else if (filter.equalsIgnoreCase("Only eCommerce Tasks")) {
/*      */           
/* 1549 */           for (int j = 0; j < tasks.size(); j++) {
/* 1550 */             if (MilestoneHelper.isEcommerce((ScheduledTask)tasks.get(j)))
/* 1551 */               precache.add((ScheduledTask)tasks.get(j)); 
/*      */           } 
/* 1553 */           schedule.setTasks(precache);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1557 */     return schedule;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Schedule deptFilterSchedule(Schedule schedule, String filter, Context context) {
/* 1568 */     if (schedule != null && schedule.getTasks().size() > 0) {
/*      */       
/* 1570 */       filter = filter.trim();
/* 1571 */       if (filter.length() > 0) {
/*      */         
/* 1573 */         Vector tasks = schedule.getTasks();
/* 1574 */         Vector precache = new Vector();
/* 1575 */         for (int i = 0; i < tasks.size(); i++) {
/*      */           
/* 1577 */           ScheduledTask task = (ScheduledTask)tasks.get(i);
/* 1578 */           if (filter.equalsIgnoreCase("All")) {
/* 1579 */             precache.add(task);
/*      */           }
/* 1581 */           else if (filter.equalsIgnoreCase(task.getDepartment().trim())) {
/* 1582 */             precache.add(task);
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/* 1587 */         if (precache.size() > 0) {
/* 1588 */           schedule.setTasks(precache);
/*      */         } else {
/* 1590 */           context.putDelivery("AlertMessage", "No tasks found for this department!");
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1595 */     return schedule;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isTimestampValid(Schedule schedule) {
/* 1606 */     boolean isValid = false;
/* 1607 */     if (schedule != null) {
/*      */       
/* 1609 */       Vector tasks = schedule.getTasks();
/*      */       
/* 1611 */       for (int i = 0; i < tasks.size(); i++) {
/*      */         
/* 1613 */         ScheduledTask task = (ScheduledTask)tasks.get(i);
/*      */         
/* 1615 */         String timestampQuery = "Select last_updated_ck, last_updated_on from release_detail where release_id = " + task.getReleaseID() + " and task_id =" + task.getTaskID();
/* 1616 */         JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
/* 1617 */         connectorTimestamp.runQuery();
/* 1618 */         if (connectorTimestamp.more())
/*      */         {
/* 1620 */           if (task.setLastUpdatedCk() != Long.parseLong(connectorTimestamp.getField("last_updated_ck", ""), 16)) {
/*      */             
/* 1622 */             connectorTimestamp.close();
/* 1623 */             return false;
/*      */           } 
/*      */         }
/* 1626 */         connectorTimestamp.close();
/*      */       } 
/*      */     } 
/* 1629 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vector getMultCompleteDates(int selectionNo, int taskId) {
/* 1639 */     Vector multCompleteDates = null;
/* 1640 */     if (selectionNo > 0) {
/* 1641 */       StringBuffer sql = new StringBuffer();
/*      */       
/* 1643 */       sql.append("select release_id, task_id, order_no, completion_date, last_updated_on, last_updated_by from MultCompleteDates with (nolock) where release_id = " + 
/* 1644 */           selectionNo + " and task_id = " + taskId + 
/* 1645 */           " order by order_no desc");
/*      */ 
/*      */       
/* 1648 */       JdbcConnector connector = MilestoneHelper.getConnector(sql.toString());
/* 1649 */       connector.setForwardOnly(false);
/* 1650 */       connector.runQuery();
/*      */       
/* 1652 */       multCompleteDates = new Vector();
/* 1653 */       if (connector != null) {
/* 1654 */         while (connector.more()) {
/* 1655 */           MultCompleteDate mcd = new MultCompleteDate();
/* 1656 */           mcd.setReleaseID(connector.getIntegerField("release_id"));
/* 1657 */           mcd.setTaskID(connector.getIntegerField("task_id"));
/* 1658 */           mcd.setOrderNo(connector.getIntegerField("order_no"));
/*      */           
/* 1660 */           String completionDateStr = connector.getField("completion_date", "");
/* 1661 */           if (completionDateStr.length() > 0) {
/* 1662 */             mcd.setCompletionDate(MilestoneHelper.getDatabaseDate(completionDateStr));
/*      */           }
/* 1664 */           String lastUpdatedOnStr = connector.getField("last_updated_on");
/* 1665 */           if (lastUpdatedOnStr.length() > 0) {
/* 1666 */             mcd.setLastUpdatedOn(MilestoneHelper.getDatabaseDate(lastUpdatedOnStr));
/*      */           }
/* 1668 */           mcd.setLastUpdatedBy(connector.getIntegerField("last_updated_by"));
/*      */           
/* 1670 */           multCompleteDates.addElement(mcd);
/*      */           
/* 1672 */           connector.next();
/*      */         } 
/* 1674 */         connector.close();
/*      */       } 
/*      */     } 
/* 1677 */     return multCompleteDates;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void saveMultCompleteDates(ScheduledTask newTask, User user) {
/* 1687 */     Vector subMCDates = null;
/* 1688 */     Vector dbMCDates = null;
/*      */     
/* 1690 */     if (newTask != null) {
/* 1691 */       int relId = newTask.getReleaseID();
/* 1692 */       int taskId = newTask.getTaskID();
/*      */ 
/*      */       
/* 1695 */       ScheduledTask dbTask = getInstance().getScheduledTask(relId, taskId);
/* 1696 */       dbMCDates = dbTask.getMultCompleteDates();
/*      */ 
/*      */ 
/*      */       
/* 1700 */       Calendar subCompDt = newTask.getCompletionDate();
/* 1701 */       Calendar dbCompDt = dbTask.getCompletionDate();
/*      */ 
/*      */       
/* 1704 */       if ((subCompDt == null && dbCompDt != null) || (subCompDt != null && dbCompDt == null) || (
/* 1705 */         subCompDt != null && dbCompDt != null && !subCompDt.equals(dbCompDt))) {
/* 1706 */         UpdateCompletionDate(relId, taskId, user.getUserId(), subCompDt);
/*      */       }
/*      */ 
/*      */       
/* 1710 */       subMCDates = newTask.getMultCompleteDates();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1715 */       boolean refreshDb = false;
/* 1716 */       boolean subHasVals = (subMCDates != null && !subMCDates.isEmpty());
/* 1717 */       boolean dbHasVals = (dbMCDates != null && !dbMCDates.isEmpty());
/*      */       
/* 1719 */       if (subHasVals && dbHasVals) {
/*      */         
/* 1721 */         int subCt = subMCDates.size();
/* 1722 */         int dbCt = dbMCDates.size();
/* 1723 */         int subIdx = subCt - 1;
/* 1724 */         int dbIdx = dbCt - 1;
/*      */ 
/*      */         
/* 1727 */         Vector updateDates = new Vector();
/* 1728 */         while (subIdx >= 0 && dbIdx >= 0 && !refreshDb) {
/* 1729 */           MultCompleteDate subMcd = (MultCompleteDate)subMCDates.get(subIdx);
/* 1730 */           MultCompleteDate dbMcd = (MultCompleteDate)dbMCDates.get(dbIdx);
/* 1731 */           if (subMcd.getOrderNo() == dbMcd.getOrderNo()) {
/* 1732 */             if (!subMcd.getCompletionDate().equals(dbMcd.getCompletionDate())) {
/* 1733 */               updateDates.add(subMcd);
/*      */             }
/*      */           }
/*      */           else {
/*      */             
/* 1738 */             refreshDb = true;
/*      */           } 
/* 1740 */           subIdx--;
/* 1741 */           dbIdx--;
/*      */         } 
/*      */         
/* 1744 */         if (!refreshDb)
/*      */         {
/* 1746 */           if (updateDates != null && !updateDates.isEmpty()) {
/* 1747 */             UpdateMultCompleteDates(relId, taskId, user.getUserId(), updateDates);
/*      */           }
/*      */           
/* 1750 */           if (subCt != dbCt) {
/* 1751 */             int minCt = Math.min(subCt, dbCt);
/* 1752 */             if (minCt == subCt)
/*      */             {
/* 1754 */               Vector deleteDates = new Vector();
/* 1755 */               for (int j = dbIdx; j >= 0; j--) {
/* 1756 */                 deleteDates.add(dbMCDates.get(j));
/*      */               }
/* 1758 */               DeleteMultCompleteDates(relId, taskId, deleteDates);
/*      */             }
/* 1760 */             else if (minCt == dbCt)
/*      */             {
/* 1762 */               Vector insertDates = new Vector();
/* 1763 */               for (int j = subIdx; j >= 0; j--) {
/* 1764 */                 insertDates.add(subMCDates.get(j));
/*      */               }
/* 1766 */               InsertMultCompleteDates(relId, taskId, user.getUserId(), insertDates);
/*      */             }
/*      */           
/*      */           }
/*      */         
/*      */         }
/*      */         else
/*      */         {
/* 1774 */           DeleteMultCompleteDates(relId, taskId);
/* 1775 */           InsertMultCompleteDates(relId, taskId, user.getUserId(), subMCDates);
/*      */         }
/*      */       
/*      */       }
/* 1779 */       else if (!subHasVals && dbHasVals) {
/*      */         
/* 1781 */         DeleteMultCompleteDates(relId, taskId);
/*      */       }
/* 1783 */       else if (subHasVals && !dbHasVals) {
/*      */         
/* 1785 */         InsertMultCompleteDates(relId, taskId, user.getUserId(), subMCDates);
/*      */       } 
/*      */     } 
/*      */   }
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
/*      */   private void InsertMultCompleteDates(int releaseId, int taskId, int userId, Vector multCompleteDates) {
/* 1803 */     String sqlInsBase = "sp_ins_MultCompleteDate " + String.valueOf(releaseId) + "," + String.valueOf(taskId);
/*      */     
/* 1805 */     for (int i = 0; i < multCompleteDates.size(); i++) {
/* 1806 */       MultCompleteDate mcd = (MultCompleteDate)multCompleteDates.get(i);
/* 1807 */       if (mcd != null && mcd.getCompletionDate() != null) {
/* 1808 */         String sqlIns = String.valueOf(sqlInsBase) + "," + String.valueOf(mcd.getOrderNo()) + 
/* 1809 */           ",'" + MilestoneHelper.getFormatedDate(mcd.getCompletionDate()) + "', " + userId;
/*      */         
/* 1811 */         JdbcConnector connectorInsMultCompleteDate = MilestoneHelper.getConnector(sqlIns);
/* 1812 */         connectorInsMultCompleteDate.runQuery();
/* 1813 */         connectorInsMultCompleteDate.close();
/*      */       } 
/*      */     } 
/*      */   }
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
/*      */   private void UpdateMultCompleteDates(int releaseId, int taskId, int userId, Vector multCompleteDates) {
/* 1829 */     if (multCompleteDates != null && !multCompleteDates.isEmpty()) {
/*      */       
/* 1831 */       String sqlUpdBase = "sp_upd_MultCompleteDate " + String.valueOf(releaseId) + "," + String.valueOf(taskId);
/*      */       
/* 1833 */       for (int i = 0; i < multCompleteDates.size(); i++) {
/* 1834 */         MultCompleteDate mcd = (MultCompleteDate)multCompleteDates.get(i);
/* 1835 */         if (mcd != null && mcd.getCompletionDate() != null) {
/* 1836 */           String sqlUpd = String.valueOf(sqlUpdBase) + "," + String.valueOf(mcd.getOrderNo()) + 
/* 1837 */             ",'" + MilestoneHelper.getFormatedDate(mcd.getCompletionDate()) + "', " + userId;
/*      */           
/* 1839 */           JdbcConnector connectorInsMultCompleteDate = MilestoneHelper.getConnector(sqlUpd);
/* 1840 */           connectorInsMultCompleteDate.runQuery();
/* 1841 */           connectorInsMultCompleteDate.close();
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
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
/*      */   private void DeleteMultCompleteDates(int releaseId, int taskId) {
/* 1857 */     String sqlDel = "sp_del_task_MultCompleteDates " + releaseId + "," + taskId;
/*      */     
/* 1859 */     JdbcConnector connectorInsMultCompleteDate = MilestoneHelper.getConnector(sqlDel);
/* 1860 */     connectorInsMultCompleteDate.runQuery();
/* 1861 */     connectorInsMultCompleteDate.close();
/*      */   }
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
/*      */   private void DeleteMultCompleteDates(int releaseId, int taskId, Vector deleteDates) {
/* 1874 */     if (deleteDates != null) {
/* 1875 */       for (int i = 0; i < deleteDates.size(); i++) {
/*      */         
/* 1877 */         int orderNo = ((MultCompleteDate)deleteDates.get(i)).getOrderNo();
/* 1878 */         String sqlDel = "sp_del_MultCompleteDate " + releaseId + "," + taskId + "," + String.valueOf(orderNo);
/*      */         
/* 1880 */         JdbcConnector connectorInsMultCompleteDate = MilestoneHelper.getConnector(sqlDel);
/* 1881 */         connectorInsMultCompleteDate.runQuery();
/* 1882 */         connectorInsMultCompleteDate.close();
/*      */       } 
/*      */     }
/*      */   }
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
/*      */   public void UpdateCompletionDate(int releaseId, int taskId, int userId, Calendar submittedDate) {
/* 1899 */     String sqlUpd = "sp_upd_completion_date " + releaseId + " ," + taskId + 
/* 1900 */       ", '" + MilestoneHelper.getFormatedDate(submittedDate) + "', " + userId;
/*      */     
/* 1902 */     JdbcConnector connectorUpdMultCompleteDate = MilestoneHelper.getConnector(sqlUpd);
/* 1903 */     connectorUpdMultCompleteDate.runQuery();
/* 1904 */     connectorUpdMultCompleteDate.close();
/*      */   }
/*      */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\ScheduleManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */