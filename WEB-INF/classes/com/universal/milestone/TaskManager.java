/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.ComponentLog;
/*     */ import com.techempower.EnhancedProperties;
/*     */ import com.techempower.gemini.Context;
/*     */ import com.techempower.gemini.GeminiApplication;
/*     */ import com.universal.milestone.Cache;
/*     */ import com.universal.milestone.Day;
/*     */ import com.universal.milestone.Family;
/*     */ import com.universal.milestone.JdbcConnector;
/*     */ import com.universal.milestone.MilestoneConstants;
/*     */ import com.universal.milestone.MilestoneHelper;
/*     */ import com.universal.milestone.Notepad;
/*     */ import com.universal.milestone.ProductCategory;
/*     */ import com.universal.milestone.ReleaseType;
/*     */ import com.universal.milestone.SelectionManager;
/*     */ import com.universal.milestone.Task;
/*     */ import com.universal.milestone.TaskManager;
/*     */ import java.util.Vector;
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
/*     */ public class TaskManager
/*     */   implements MilestoneConstants
/*     */ {
/*     */   public static final String COMPONENT_CODE = "mTsk";
/*     */   public static final String DEFAULT_ORDER = " ORDER BY a.[name]";
/*  46 */   protected static TaskManager taskManager = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static ComponentLog log;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  58 */   public static void configure(EnhancedProperties props, GeminiApplication application) { log = application.getLog("mTsk"); }
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
/*     */   public static TaskManager getInstance() {
/*  76 */     if (taskManager == null)
/*     */     {
/*  78 */       taskManager = new TaskManager();
/*     */     }
/*  80 */     return taskManager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Task getTask(int id, boolean getTimestamp) {
/*  88 */     String taskQuery = "SELECT * FROM vi_Task WHERE task_id = " + 
/*     */       
/*  90 */       id + 
/*  91 */       ";";
/*     */     
/*  93 */     Task task = null;
/*     */     
/*  95 */     JdbcConnector connector = MilestoneHelper.getConnector(taskQuery);
/*  96 */     connector.runQuery();
/*     */     
/*  98 */     if (connector.more()) {
/*     */       
/* 100 */       task = new Task();
/* 101 */       task.setTaskID(connector.getIntegerField("task_id"));
/*     */ 
/*     */       
/* 104 */       task.setTaskName(connector.getField("name", ""));
/*     */ 
/*     */       
/* 107 */       task.setComments(connector.getField("comments", ""));
/*     */ 
/*     */       
/* 110 */       task.setIsKeyTask(connector.getBoolean("key_task_indicator"));
/*     */ 
/*     */ 
/*     */       
/* 114 */       task.setActiveFlag(connector.getBoolean("active_flag"));
/*     */ 
/*     */       
/* 117 */       task.setWeeksToRelease(connector.getIntegerField("weeks_to_release"));
/*     */ 
/*     */       
/* 120 */       task.setDayOfTheWeek(new Day(connector.getIntegerField("day_of_week")));
/*     */ 
/*     */       
/* 123 */       task.setWeekAdjustment(connector.getIntegerField("week_adjustment"));
/*     */ 
/*     */       
/* 126 */       String dept = "";
/* 127 */       if (connector.getFieldByName("department") != null)
/* 128 */         dept = connector.getFieldByName("department"); 
/* 129 */       task.setDepartment(dept);
/*     */ 
/*     */       
/* 132 */       String category = "";
/* 133 */       if (connector.getFieldByName("reporting_category") != null)
/* 134 */         category = connector.getFieldByName("reporting_category"); 
/* 135 */       task.setCategory(category);
/*     */ 
/*     */       
/* 138 */       if (connector.getIntegerField("owner") > 0) {
/* 139 */         task.setOwner((Family)MilestoneHelper.getStructureObject(connector.getIntegerField("owner")));
/*     */       }
/*     */       
/* 142 */       task.setAllowMultCompleteDatesFlag(connector.getBoolean("allow_mult_complete_dates_flag"));
/*     */ 
/*     */       
/* 145 */       task.setTaskAbbreviation(connector.getIntegerField("abbrev_id"));
/*     */ 
/*     */       
/* 148 */       task.setTaskAbbrStr(MilestoneHelper.getTaskAbbreivationNameById(connector.getIntegerField("abbrev_id")));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 153 */       String desc = "";
/* 154 */       if (connector.getFieldByName("description") != null)
/* 155 */         desc = connector.getFieldByName("description"); 
/* 156 */       task.setTaskDescription(desc);
/*     */ 
/*     */       
/* 159 */       String templateListQuery = "SELECT * FROM vi_Template_Header WHERE template_id IN (SELECT template_id FROM vi_Template_Detail  WHERE task_id = " + 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 164 */         task.getTaskID() + ")" + 
/* 165 */         ";";
/*     */       
/* 167 */       JdbcConnector templateConnector = MilestoneHelper.getConnector(templateListQuery);
/* 168 */       templateConnector.runQuery();
/*     */       
/* 170 */       Vector precache = new Vector();
/*     */       
/* 172 */       while (templateConnector.more()) {
/*     */         
/* 174 */         String templateInfo = new String(templateConnector.getField("name", ""));
/* 175 */         templateInfo = String.valueOf(templateInfo) + "&nbsp;:&nbsp;";
/* 176 */         if (templateConnector.getField("product_line", "").length() > 0)
/* 177 */           templateInfo = String.valueOf(templateInfo) + ((ProductCategory)SelectionManager.getLookupObject(templateConnector.getField("product_line", ""), Cache.getProductCategories())).getName(); 
/* 178 */         templateInfo = String.valueOf(templateInfo) + "/";
/* 179 */         if (templateConnector.getField("release_type", "").length() > 0)
/* 180 */           templateInfo = String.valueOf(templateInfo) + ((ReleaseType)SelectionManager.getLookupObject(templateConnector.getField("release_type", ""), Cache.getReleaseTypes())).getName(); 
/* 181 */         templateInfo = String.valueOf(templateInfo) + "/";
/* 182 */         templateInfo = String.valueOf(templateInfo) + templateConnector.getField("configuration", "");
/* 183 */         templateInfo = String.valueOf(templateInfo) + "/";
/* 184 */         templateInfo = String.valueOf(templateInfo) + templateConnector.getField("sub_configuration", "");
/*     */         
/* 186 */         precache.addElement(templateInfo);
/* 187 */         templateInfo = null;
/* 188 */         templateConnector.next();
/*     */       } 
/*     */       
/* 191 */       task.setTemplates(precache);
/*     */       
/* 193 */       templateConnector.close();
/*     */ 
/*     */       
/* 196 */       String lastDateString = connector.getFieldByName("last_updated_on");
/* 197 */       if (lastDateString != null) {
/* 198 */         task.setLastUpdateDate(MilestoneHelper.getDatabaseDate(lastDateString));
/*     */       }
/*     */       
/* 201 */       task.setLastUpdatingUser(connector.getIntegerField("last_updated_by"));
/*     */ 
/*     */       
/* 204 */       if (getTimestamp) {
/*     */         
/* 206 */         long lastUpdatedLong = Long.parseLong(connector.getField("last_updated_ck"), 16);
/* 207 */         task.setLastUpdatedCk(lastUpdatedLong);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 212 */     connector.close();
/*     */     
/* 214 */     return task;
/*     */   }
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
/*     */   public Task getNotepadTask(JdbcConnector connector) {
/* 228 */     Task task = null;
/*     */     
/* 230 */     if (connector != null) {
/*     */       
/* 232 */       task = new Task();
/* 233 */       task.setTaskID(connector.getIntegerField("task_id"));
/*     */ 
/*     */       
/* 236 */       task.setTaskName(connector.getField("name"));
/*     */ 
/*     */       
/* 239 */       task.setWeeksToRelease(connector.getIntegerField("weeks_to_release"));
/*     */ 
/*     */       
/* 242 */       task.setDayOfTheWeek(new Day(connector.getIntegerField("day_of_week")));
/*     */ 
/*     */       
/* 245 */       String dept = "";
/* 246 */       if (connector.getFieldByName("department") != null)
/* 247 */         dept = connector.getFieldByName("department"); 
/* 248 */       if (dept.equals("-1"))
/*     */       {
/* 250 */         dept = "";
/*     */       }
/*     */       
/* 253 */       if (!dept.equals("")) {
/* 254 */         dept = Cache.getDepartmentSubDescription(dept);
/*     */       }
/* 256 */       task.setDepartment(dept);
/*     */ 
/*     */       
/* 259 */       task.setOwner((Family)MilestoneHelper.getStructureObject(connector.getIntegerField("owner")));
/*     */ 
/*     */       
/* 262 */       task.setIsKeyTask(connector.getBoolean("key_task_indicator"));
/*     */     } 
/*     */ 
/*     */     
/* 266 */     return task;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Task saveTask(Task task, int userID) {
/* 274 */     log.debug("Task save query:\n" + task);
/* 275 */     long timestamp = task.getLastUpdatedCk();
/* 276 */     int isKeyTask = 0;
/* 277 */     if (task.getIsKeyTask())
/*     */     {
/* 279 */       isKeyTask = 1;
/*     */     }
/*     */     
/* 282 */     int isActiveFlag = 0;
/* 283 */     if (task.getActiveFlag())
/*     */     {
/* 285 */       isActiveFlag = 1;
/*     */     }
/*     */ 
/*     */     
/* 289 */     task.flushAudits(userID);
/*     */     
/* 291 */     int ownerId = -1;
/* 292 */     if (task.getOwner() != null)
/*     */     {
/* 294 */       ownerId = task.getOwner().getStructureID();
/*     */     }
/* 296 */     int dayId = 0;
/* 297 */     if (task.getDayOfTheWeek() != null)
/*     */     {
/* 299 */       dayId = task.getDayOfTheWeek().getDayID();
/*     */     }
/* 301 */     if (dayId == -1) dayId = 0;
/*     */     
/* 303 */     int allowMultCompleteDates = task.getAllowMultCompleteDatesFlag() ? 1 : 0;
/*     */     
/* 305 */     String query = "sp_sav_Task " + 
/* 306 */       task.getTaskID() + "," + 
/* 307 */       "'" + MilestoneHelper.escapeSingleQuotes(task.getTaskName()) + "'," + 
/* 308 */       "'" + MilestoneHelper.escapeSingleQuotes(task.getTaskDescription()) + "'," + 
/* 309 */       task.getTaskAbbreviation() + "," + 
/* 310 */       dayId + "," + 
/* 311 */       task.getWeeksToRelease() + "," + 
/* 312 */       "'" + task.getDepartment() + "'," + 
/* 313 */       "'" + MilestoneHelper.escapeSingleQuotes(task.getCategory()) + "'," + 
/* 314 */       isKeyTask + "," + 
/* 315 */       '\001' + "," + 
/* 316 */       isActiveFlag + "," + 
/* 317 */       ownerId + "," + 
/* 318 */       task.getWeekAdjustment() + "," + 
/* 319 */       "'" + MilestoneHelper.escapeSingleQuotes(task.getComments()) + "'," + 
/* 320 */       allowMultCompleteDates + "," + 
/* 321 */       task.getLastUpdatingUser() + "," + 
/* 322 */       timestamp;
/*     */     
/* 324 */     log.debug("Save task query:\n" + query);
/* 325 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 326 */     connector.runQuery();
/*     */     
/* 328 */     if (connector.getIntegerField("ReturnId") > 0) {
/* 329 */       task.setTaskID(connector.getIntegerField("ReturnId"));
/*     */     }
/* 331 */     connector.close();
/*     */     
/* 333 */     String timestampQuery = "SELECT last_updated_ck, last_updated_on FROM vi_task WHERE task_id = " + 
/*     */       
/* 335 */       task.getTaskID() + 
/* 336 */       ";";
/*     */     
/* 338 */     JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
/* 339 */     connectorTimestamp.runQuery();
/* 340 */     if (connectorTimestamp.more()) {
/*     */       
/* 342 */       task.setLastUpdatedCk(Long.parseLong(connectorTimestamp.getField("last_updated_ck"), 16));
/* 343 */       task.setLastUpdateDate(MilestoneHelper.getDatabaseDate(connectorTimestamp.getField("last_updated_on")));
/*     */     } 
/* 345 */     connectorTimestamp.close();
/*     */     
/* 347 */     return task;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void deleteTask(Task task, int userID) {
/* 358 */     String query = "sp_del_Task " + 
/* 359 */       task.getTaskID();
/*     */     
/* 361 */     log.debug("Delete task query:\n" + query);
/* 362 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 363 */     connector.runQuery();
/* 364 */     connector.close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDuplicateTask(Task task) {
/* 376 */     boolean isDuplicate = false;
/*     */     
/* 378 */     if (task != null) {
/*     */       
/* 380 */       int ownerId = -1;
/* 381 */       if (task.getOwner() != null)
/*     */       {
/* 383 */         ownerId = task.getOwner().getStructureID();
/*     */       }
/*     */       
/* 386 */       String query = "SELECT * FROM vi_task WHERE  name = '" + 
/* 387 */         MilestoneHelper.escapeSingleQuotes(task.getTaskName()) + "'" + 
/* 388 */         " AND weeks_to_release = " + task.getWeeksToRelease() + 
/* 389 */         " AND day_of_week = " + task.getDayOfTheWeek().getDayID() + 
/* 390 */         " AND task_id <> " + task.getTaskID() + 
/* 391 */         " AND owner = " + ownerId;
/*     */       
/* 393 */       log.debug("TTTTTTTTTTTTTTTT duplicate query = " + query);
/*     */       
/* 395 */       JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 396 */       connector.runQuery();
/*     */       
/* 398 */       if (connector.more()) {
/* 399 */         isDuplicate = true;
/*     */       }
/* 401 */       connector.close();
/*     */     } 
/*     */ 
/*     */     
/* 405 */     return isDuplicate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNotepadQuery(Context context, int UserId, Notepad notepad) {
/* 416 */     if (notepad != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 422 */       boolean activeFlag = true;
/*     */ 
/*     */       
/* 425 */       if (context.getParameter("inactiveSrch") != null) {
/* 426 */         activeFlag = false;
/*     */       }
/* 428 */       String query = getDefaultSearchQuery(context, activeFlag);
/* 429 */       String taskNameSrch = context.getParameter("taskNameSrch");
/* 430 */       String taskAbbrevSrch = context.getParameter("taskAbbrevSrch");
/* 431 */       String ownerSrch = context.getParameter("ownerSrch");
/* 432 */       String departmentSrch = context.getParameter("departmentSrch");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 438 */       if (context.getParameter("keyTaskSrch") != null) {
/* 439 */         query = String.valueOf(query) + MilestoneHelper.addQueryParams(query, " a.[key_task_indicator] = 1");
/*     */       }
/* 441 */       if (taskNameSrch != null && !taskNameSrch.equals("")) {
/* 442 */         query = String.valueOf(query) + MilestoneHelper.addQueryParams(query, " a.[name] " + MilestoneHelper.setWildCardsEscapeSingleQuotes(taskNameSrch));
/*     */       }
/*     */ 
/*     */       
/* 446 */       if (MilestoneHelper.isStringNotEmpty(taskAbbrevSrch)) {
/*     */ 
/*     */ 
/*     */         
/* 450 */         int abbrevId = MilestoneHelper.getTaskAbbreviationID(taskAbbrevSrch);
/* 451 */         query = String.valueOf(query) + MilestoneHelper.addQueryParams(query, " a.[abbrev_id] = " + abbrevId);
/*     */       } 
/*     */       
/* 454 */       if (ownerSrch != null && !ownerSrch.equals("0")) {
/* 455 */         query = String.valueOf(query) + MilestoneHelper.addQueryParams(query, " a.[owner] like '" + ownerSrch + "'");
/*     */       }
/* 457 */       if (departmentSrch != null && !departmentSrch.equals("0") && !departmentSrch.equals("-1")) {
/* 458 */         query = String.valueOf(query) + MilestoneHelper.addQueryParams(query, " a.[department] like '%" + departmentSrch + "%'");
/*     */       }
/*     */       
/* 461 */       if (context.getParameter("inactiveSrch") != null) {
/* 462 */         query = String.valueOf(query) + MilestoneHelper.addQueryParams(query, " a.[active_flag] <> 1");
/*     */       } else {
/* 464 */         query = String.valueOf(query) + MilestoneHelper.addQueryParams(query, " a.[active_flag] = 1");
/*     */       } 
/*     */       
/* 467 */       String allowMCDFlag = context.getParameter("allowMultCompleteDatesSrch");
/* 468 */       if (allowMCDFlag != null)
/*     */       {
/* 470 */         if (allowMCDFlag.equals("1")) {
/* 471 */           query = String.valueOf(query) + MilestoneHelper.addQueryParams(query, " a.[allow_mult_complete_dates_flag] = 1");
/* 472 */         } else if (allowMCDFlag.equals("0")) {
/* 473 */           query = String.valueOf(query) + MilestoneHelper.addQueryParams(query, " a.[allow_mult_complete_dates_flag] = 0");
/*     */         } 
/*     */       }
/* 476 */       notepad.setSearchQuery(query);
/* 477 */       notepad.setOrderBy(" ORDER BY a.[name]");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector getTaskNotepadList(Context context, Notepad notepad) {
/* 483 */     String query = "";
/*     */     
/* 485 */     if (notepad != null && !notepad.getSearchQuery().equals("")) {
/*     */       
/* 487 */       query = notepad.getSearchQuery();
/* 488 */       query = String.valueOf(query) + notepad.getOrderBy();
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 493 */       query = String.valueOf(getDefaultSearchQuery(context, true)) + " ORDER BY a.[name]";
/*     */     } 
/*     */ 
/*     */     
/* 497 */     log.debug("++++++++++++++++++++ query " + query);
/*     */     
/* 499 */     Task task = null;
/* 500 */     Vector precache = new Vector();
/*     */     
/* 502 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 503 */     connector.runQuery();
/*     */     
/* 505 */     while (connector.more()) {
/*     */       
/* 507 */       task = getNotepadTask(connector);
/*     */       
/* 509 */       precache.addElement(task);
/* 510 */       task = null;
/* 511 */       connector.next();
/*     */     } 
/* 513 */     connector.close();
/*     */     
/* 515 */     return precache;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getDefaultSearchQuery(Context context, boolean activeFlag) {
/* 526 */     StringBuffer query = new StringBuffer();
/*     */ 
/*     */ 
/*     */     
/* 530 */     query.append("SELECT * FROM vi_Task a LEFT JOIN vi_structure b on a.[owner] = b.[structure_id] ");
/*     */ 
/*     */     
/* 533 */     query.append(MilestoneHelper.getOwnerCompanyWhereClause(context));
/*     */ 
/*     */     
/* 536 */     if (MilestoneHelper.getOwnerCompanyWhereClause(context).length() > 0) {
/* 537 */       query.append(" AND ");
/*     */     } else {
/* 539 */       query.append(" WHERE ");
/*     */     } 
/*     */     
/* 542 */     if (activeFlag) {
/* 543 */       query.append(" a.[active_flag]  = 1 ");
/*     */     } else {
/* 545 */       query.append(" a.[active_flag] <> 1 ");
/*     */     } 
/*     */ 
/*     */     
/* 549 */     return query.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTimestampValid(Task task) {
/* 558 */     if (task != null) {
/*     */       
/* 560 */       String timestampQuery = "SELECT last_updated_ck  FROM vi_task WHERE task_id = " + 
/*     */         
/* 562 */         task.getTaskID() + 
/* 563 */         ";";
/* 564 */       JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
/* 565 */       connectorTimestamp.runQuery();
/* 566 */       if (connectorTimestamp.more())
/*     */       {
/* 568 */         if (task.getLastUpdatedCk() != Long.parseLong(connectorTimestamp.getField("last_updated_ck", ""), 16)) {
/*     */           
/* 570 */           connectorTimestamp.close();
/* 571 */           return false;
/*     */         } 
/*     */       }
/* 574 */       connectorTimestamp.close();
/* 575 */       return true;
/*     */     } 
/* 577 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOnMultipleTemplates(Task task) {
/* 587 */     if (task != null) {
/*     */       
/* 589 */       String query = "SELECT *  FROM vi_Template_Detail WHERE task_id = " + 
/*     */         
/* 591 */         task.getTaskID() + 
/* 592 */         ";";
/* 593 */       JdbcConnector connector = MilestoneHelper.getConnector(query);
/*     */ 
/*     */       
/* 596 */       connector.setForwardOnly(false);
/* 597 */       connector.runQuery();
/* 598 */       if (connector.more())
/*     */       {
/* 600 */         if (connector.getRowCount() > 1) {
/*     */           
/* 602 */           connector.close();
/* 603 */           return true;
/*     */         } 
/*     */       }
/* 606 */       connector.close();
/*     */     } 
/* 608 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\TaskManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */