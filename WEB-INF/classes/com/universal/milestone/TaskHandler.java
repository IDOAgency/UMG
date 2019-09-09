/*      */ package WEB-INF.classes.com.universal.milestone;
/*      */ 
/*      */ import com.techempower.ComponentLog;
/*      */ import com.techempower.gemini.Context;
/*      */ import com.techempower.gemini.Dispatcher;
/*      */ import com.techempower.gemini.FormCheckBox;
/*      */ import com.techempower.gemini.FormDropDownMenu;
/*      */ import com.techempower.gemini.FormElement;
/*      */ import com.techempower.gemini.FormHidden;
/*      */ import com.techempower.gemini.FormIntegerField;
/*      */ import com.techempower.gemini.FormRadioButtonGroup;
/*      */ import com.techempower.gemini.FormTextArea;
/*      */ import com.techempower.gemini.FormTextField;
/*      */ import com.techempower.gemini.FormValidation;
/*      */ import com.techempower.gemini.GeminiApplication;
/*      */ import com.universal.milestone.Cache;
/*      */ import com.universal.milestone.Company;
/*      */ import com.universal.milestone.CompanyAcl;
/*      */ import com.universal.milestone.Day;
/*      */ import com.universal.milestone.Environment;
/*      */ import com.universal.milestone.Family;
/*      */ import com.universal.milestone.Form;
/*      */ import com.universal.milestone.MilestoneConstants;
/*      */ import com.universal.milestone.MilestoneHelper;
/*      */ import com.universal.milestone.MilestoneSecurity;
/*      */ import com.universal.milestone.Notepad;
/*      */ import com.universal.milestone.SecureHandler;
/*      */ import com.universal.milestone.Task;
/*      */ import com.universal.milestone.TaskHandler;
/*      */ import com.universal.milestone.TaskManager;
/*      */ import com.universal.milestone.User;
/*      */ import com.universal.milestone.UserManager;
/*      */ import java.util.Calendar;
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
/*      */ public class TaskHandler
/*      */   extends SecureHandler
/*      */ {
/*      */   public static final String COMPONENT_CODE = "hTas";
/*      */   public GeminiApplication application;
/*      */   public ComponentLog log;
/*      */   
/*      */   public TaskHandler(GeminiApplication application) {
/*   60 */     this.application = application;
/*   61 */     this.log = application.getLog("hTas");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   69 */   public String getDescription() { return "Task"; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean acceptRequest(Dispatcher dispatcher, Context context, String command) {
/*   79 */     if (super.acceptRequest(dispatcher, context, command))
/*      */     {
/*   81 */       if (command.startsWith("task"))
/*      */       {
/*   83 */         return handleRequest(dispatcher, context, command);
/*      */       }
/*      */     }
/*   86 */     return false;
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
/*      */   public boolean handleRequest(Dispatcher dispatcher, Context context, String command) {
/*   98 */     if (command.equalsIgnoreCase("task-search"))
/*      */     {
/*  100 */       search(dispatcher, context, command);
/*      */     }
/*  102 */     if (command.equalsIgnoreCase("task-sort")) {
/*      */       
/*  104 */       sort(dispatcher, context);
/*      */     }
/*  106 */     else if (command.equalsIgnoreCase("task-editor")) {
/*      */       
/*  108 */       edit(context, command);
/*      */     }
/*  110 */     else if (command.equalsIgnoreCase("task-edit-save")) {
/*      */       
/*  112 */       editSave(dispatcher, context, command);
/*      */     
/*      */     }
/*  115 */     else if (command.equalsIgnoreCase("task-edit-copy")) {
/*      */       
/*  117 */       editCopy(dispatcher, context, command);
/*      */     
/*      */     }
/*  120 */     else if (command.equalsIgnoreCase("task-edit-save-new")) {
/*      */       
/*  122 */       editSaveNew(dispatcher, context, command);
/*      */     }
/*  124 */     else if (command.equalsIgnoreCase("task-edit-new")) {
/*      */       
/*  126 */       editNew(dispatcher, context, command);
/*      */     }
/*  128 */     else if (command.equalsIgnoreCase("task-edit-delete")) {
/*      */       
/*  130 */       delete(context, command);
/*      */     } 
/*      */     
/*  133 */     return true;
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
/*      */   private boolean search(Dispatcher dispatcher, Context context, String command) {
/*  146 */     Notepad notepad = MilestoneHelper.getNotepadFromSession(4, context);
/*      */ 
/*      */     
/*  149 */     notepad.setAllContents(null);
/*  150 */     notepad.setSelected(null);
/*      */     
/*  152 */     TaskManager.getInstance().setNotepadQuery(context, MilestoneSecurity.getUser(context).getUserId(), notepad);
/*  153 */     dispatcher.redispatch(context, "task-editor");
/*      */     
/*  155 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean sort(Dispatcher dispatcher, Context context) {
/*  165 */     int sort = Integer.parseInt(context.getParameter("OrderBy"));
/*  166 */     int userId = MilestoneSecurity.getUser(context).getUserId();
/*      */     
/*  168 */     Notepad notepad = getTaskNotepad(context, userId);
/*      */     
/*  170 */     if (notepad.getSearchQuery() == null || notepad.getSearchQuery().equals("")) {
/*  171 */       notepad.setSearchQuery(TaskManager.getInstance().getDefaultSearchQuery(context, true));
/*      */     }
/*  173 */     notepad.setOrderBy(" ORDER BY " + MilestoneConstants.SORT_TASK_EDITOR[sort]);
/*      */ 
/*      */     
/*  176 */     notepad.setAllContents(null);
/*  177 */     notepad = getTaskNotepad(context, userId);
/*  178 */     notepad.goToSelectedPage();
/*      */     
/*  180 */     dispatcher.redispatch(context, "task-editor");
/*      */     
/*  182 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean edit(Context context, String command) {
/*  190 */     User user = MilestoneSecurity.getUser(context);
/*      */     
/*  192 */     Notepad notepad = getTaskNotepad(context, user.getUserId());
/*  193 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */     
/*  195 */     Task task = MilestoneHelper.getScreenTask(context);
/*      */     
/*  197 */     if (task != null) {
/*      */       
/*  199 */       Form form = null;
/*      */       
/*  201 */       if (task != null) {
/*  202 */         form = buildForm(context, task, command);
/*      */       } else {
/*  204 */         form = buildNewForm(context, command);
/*      */       } 
/*  206 */       boolean isMultiple = TaskManager.getInstance().isOnMultipleTemplates(task);
/*      */       
/*  208 */       if (isMultiple)
/*      */       {
/*  210 */         context.putDelivery("MultipleMessage", new String("Task is assigned to multiple templates.  \\nDo you still wish to save?"));
/*      */       }
/*      */       
/*  213 */       context.putDelivery("Form", form);
/*      */       
/*  215 */       return context.includeJSP("task-editor.jsp");
/*      */     } 
/*      */ 
/*      */     
/*  219 */     return goToBlank(context);
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
/*      */   private boolean editCopy(Dispatcher dispatcher, Context context, String command) {
/*  233 */     User user = MilestoneSecurity.getUser(context);
/*      */     
/*  235 */     Notepad notepad = getTaskNotepad(context, user.getUserId());
/*  236 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */     
/*  238 */     Task targetTask = MilestoneHelper.getScreenTask(context);
/*  239 */     Vector templateNames = new Vector();
/*      */     
/*  241 */     String taskName = targetTask.getTaskName();
/*  242 */     boolean keyTask = targetTask.getIsKeyTask();
/*  243 */     boolean activeTask = targetTask.getActiveFlag();
/*  244 */     int taskAbbrev = targetTask.getTaskAbbreviation();
/*  245 */     String category = targetTask.getCategory();
/*  246 */     String department = targetTask.getDepartment();
/*  247 */     int wksToRls = targetTask.getWeeksToRelease();
/*  248 */     Day dayOfWeek = targetTask.getDayOfTheWeek();
/*      */ 
/*      */     
/*  251 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */ 
/*      */     
/*  254 */     Task copiedTask = null;
/*      */ 
/*      */     
/*      */     try {
/*  258 */       copiedTask = (Task)targetTask.clone();
/*      */     }
/*  260 */     catch (CloneNotSupportedException cloneNotSupportedException) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  270 */     copiedTask.setTaskID(-1);
/*      */     
/*  272 */     copiedTask.setTaskName(taskName);
/*  273 */     copiedTask.setComments("");
/*  274 */     copiedTask.setCategory(category);
/*  275 */     copiedTask.setDayOfTheWeek(dayOfWeek);
/*  276 */     copiedTask.setIsKeyTask(keyTask);
/*  277 */     copiedTask.setOwner(null);
/*  278 */     copiedTask.setTemplates(templateNames);
/*  279 */     copiedTask.setWeekAdjustment(0);
/*  280 */     copiedTask.setWeeksToRelease(wksToRls);
/*  281 */     copiedTask.setTaskDescription("");
/*  282 */     copiedTask.setTaskAbbreviation(taskAbbrev);
/*  283 */     copiedTask.setDepartment(department);
/*  284 */     copiedTask.setActiveFlag(true);
/*  285 */     copiedTask.setAllowMultCompleteDatesFlag(false);
/*      */     
/*  287 */     Form form = null;
/*  288 */     form = buildForm(context, copiedTask, command);
/*  289 */     form.addElement(new FormHidden("cmd", "task-edit-copy"));
/*      */ 
/*      */     
/*  292 */     context.putSessionValue("Task", copiedTask);
/*  293 */     context.putDelivery("Form", form);
/*      */ 
/*      */     
/*  296 */     if (context.getSessionValue("NOTEPAD_TASKS_VISIBLE") != null) {
/*  297 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_TASKS_VISIBLE"));
/*      */     }
/*  299 */     return context.includeJSP("task-editor.jsp");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean editSave(Dispatcher dispatcher, Context context, String command) {
/*  308 */     User user = MilestoneSecurity.getUser(context);
/*      */ 
/*      */     
/*  311 */     Notepad notepad = getTaskNotepad(context, user.getUserId());
/*  312 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */     
/*  314 */     Task task = (Task)context.getSessionValue("Task");
/*      */     
/*  316 */     Form form = buildForm(context, task, command);
/*      */ 
/*      */     
/*  319 */     if (TaskManager.getInstance().isTimestampValid(task)) {
/*      */       
/*  321 */       form.setValues(context);
/*      */ 
/*      */       
/*  324 */       String taskNameString = form.getStringValue("taskName");
/*      */ 
/*      */       
/*  327 */       boolean activeFlagBool = ((FormCheckBox)form.getElement("activeFlag")).isChecked();
/*      */ 
/*      */       
/*  330 */       String commentsString = form.getStringValue("comments");
/*      */ 
/*      */       
/*  333 */       boolean keyTaskBool = ((FormCheckBox)form.getElement("keyTask")).isChecked();
/*      */ 
/*      */       
/*  336 */       String wksToReleaseString = form.getStringValue("weeks2Rel");
/*      */ 
/*      */       
/*  339 */       String dayOfWeekString = form.getStringValue("dayOfWeek");
/*      */ 
/*      */       
/*  342 */       String weekAdjustmentString = form.getStringValue("weekAdjustment");
/*      */ 
/*      */       
/*  345 */       String departmentString = "";
/*      */       
/*  347 */       if (!form.getStringValue("department").equalsIgnoreCase("-1")) {
/*  348 */         departmentString = form.getStringValue("department");
/*      */       }
/*      */       
/*  351 */       String categoryString = form.getStringValue("category");
/*      */ 
/*      */       
/*  354 */       String ownerString = form.getStringValue("owner");
/*  355 */       int ownerID = 0;
/*  356 */       if (ownerString != null) {
/*      */         
/*      */         try {
/*      */           
/*  360 */           ownerID = Integer.parseInt(ownerString);
/*      */         }
/*  362 */         catch (NumberFormatException e) {
/*      */           
/*  364 */           ownerID = 0;
/*      */         } 
/*      */       }
/*      */ 
/*      */       
/*  369 */       int allowMultInt = 0;
/*      */       try {
/*  371 */         allowMultInt = Integer.parseInt(form.getStringValue("allowMultCompleteDatesFlag"));
/*      */       }
/*  373 */       catch (NumberFormatException e) {
/*  374 */         allowMultInt = 0;
/*      */       } 
/*  376 */       boolean allowMultBool = (allowMultInt == 1);
/*  377 */       task.setAllowMultCompleteDatesFlag(allowMultBool);
/*      */ 
/*      */       
/*  380 */       String taskAbbreviationString = form.getStringValue("taskAbbreviation");
/*      */ 
/*      */       
/*  383 */       String taskDescriptionString = form.getStringValue("taskDescription");
/*      */       
/*  385 */       task.setTaskName(taskNameString);
/*  386 */       task.setComments(commentsString);
/*  387 */       task.setIsKeyTask(keyTaskBool);
/*  388 */       task.setActiveFlag(activeFlagBool);
/*      */       
/*  390 */       int wks2Rel = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/*  396 */         if (wksToReleaseString.equalsIgnoreCase("SOL") || dayOfWeekString.equalsIgnoreCase("9"))
/*      */         {
/*  398 */           wks2Rel = -10;
/*  399 */           dayOfWeekString = "9";
/*      */         }
/*      */         else
/*      */         {
/*  403 */           wks2Rel = Integer.parseInt(wksToReleaseString);
/*      */         }
/*      */       
/*  406 */       } catch (NumberFormatException e) {
/*      */         
/*  408 */         wks2Rel = 0;
/*      */       } 
/*  410 */       task.setWeeksToRelease(wks2Rel);
/*      */       
/*  412 */       task.setDayOfTheWeek(new Day(dayOfWeekString));
/*      */       
/*  414 */       int weekAdjust = 0;
/*      */       
/*      */       try {
/*  417 */         weekAdjust = Integer.parseInt(weekAdjustmentString);
/*      */       }
/*  419 */       catch (NumberFormatException e) {
/*      */         
/*  421 */         weekAdjust = 0;
/*      */       } 
/*  423 */       task.setWeekAdjustment(weekAdjust);
/*      */       
/*  425 */       task.setDepartment(departmentString);
/*  426 */       task.setCategory(categoryString);
/*  427 */       task.setOwner((Family)MilestoneHelper.getStructureObject(ownerID));
/*      */       
/*  429 */       int taskAbbreviation = -1;
/*  430 */       if (taskAbbreviationString != null && !taskAbbreviationString.equals("")) {
/*      */         
/*      */         try {
/*      */           
/*  434 */           taskAbbreviation = Integer.parseInt(taskAbbreviationString);
/*      */         }
/*  436 */         catch (NumberFormatException numberFormatException) {}
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  442 */       task.setTaskAbbreviation(taskAbbreviation);
/*  443 */       task.setTaskDescription(taskDescriptionString);
/*  444 */       task.setLastUpdatingUser(user.getUserId());
/*      */ 
/*      */       
/*  447 */       task.setTaskAbbrStr(MilestoneHelper.getTaskAbbreivationNameById(taskAbbreviation));
/*      */ 
/*      */ 
/*      */       
/*  451 */       if (!TaskManager.getInstance().isDuplicateTask(task)) {
/*      */         
/*  453 */         if (!form.isUnchanged()) {
/*      */           
/*  455 */           FormValidation formValidation = form.validate();
/*  456 */           if (formValidation.isGood()) {
/*      */             
/*  458 */             Task savedTask = TaskManager.getInstance().saveTask(task, user.getUserId());
/*      */ 
/*      */             
/*  461 */             FormElement lastUpdated = form.getElement("lastupdateddate");
/*  462 */             lastUpdated.setValue(MilestoneHelper.getLongDate(savedTask.getLastUpdateDate()));
/*      */ 
/*      */             
/*  465 */             notepad.setAllContents(null);
/*  466 */             notepad = getTaskNotepad(context, user.getUserId());
/*  467 */             notepad.setSelected(savedTask);
/*  468 */             task = (Task)notepad.validateSelected();
/*  469 */             context.putSessionValue("Selection", task);
/*      */             
/*  471 */             if (task == null) {
/*  472 */               return goToBlank(context);
/*      */             }
/*  474 */             return edit(context, command);
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/*  479 */           context.putDelivery("FormValidation", formValidation);
/*      */         } 
/*      */         
/*  482 */         form.addElement(new FormHidden("OrderBy", "", true));
/*  483 */         context.putDelivery("Form", form);
/*  484 */         return edit(context, command);
/*      */       } 
/*      */ 
/*      */       
/*  488 */       context.putDelivery("AlertMessage", "Task already exists in database. Change the name,weeks to release or day of week.");
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  493 */       context.putDelivery("AlertMessage", "The record is unavailable for editing at this time.  Please refresh your screen and edit it at a later time.");
/*      */     } 
/*      */     
/*  496 */     context.putDelivery("Form", form);
/*  497 */     return context.includeJSP("task-editor.jsp");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean delete(Context context, String command) {
/*  503 */     User user = MilestoneSecurity.getUser(context);
/*      */     
/*  505 */     Notepad notepad = getTaskNotepad(context, user.getUserId());
/*  506 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */     
/*  508 */     Task task = MilestoneHelper.getScreenTask(context);
/*      */ 
/*      */     
/*  511 */     if (task != null) {
/*      */       
/*  513 */       this.log.debug("TTTTTTTTTTTt is task being used ? " + MilestoneHelper.isTaskUsed(task));
/*  514 */       if (!MilestoneHelper.isTaskUsed(task)) {
/*      */         
/*  516 */         TaskManager.getInstance().deleteTask(task, user.getUserId());
/*      */ 
/*      */         
/*  519 */         notepad.setAllContents(null);
/*  520 */         notepad = getTaskNotepad(context, user.getUserId());
/*  521 */         notepad.setSelected(null);
/*      */       }
/*      */       else {
/*      */         
/*  525 */         context.putDelivery("AlertMessage", "This task is in use, therefore you can not delete it!");
/*      */       } 
/*      */     } 
/*  528 */     return edit(context, command);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean editNew(Dispatcher dispatcher, Context context, String command) {
/*  537 */     User user = MilestoneSecurity.getUser(context);
/*      */     
/*  539 */     Notepad notepad = getTaskNotepad(context, user.getUserId());
/*  540 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */     
/*  542 */     Form form = buildNewForm(context, command);
/*  543 */     context.putDelivery("Form", form);
/*  544 */     context.putDelivery("isNewTask", "true");
/*      */     
/*  546 */     return context.includeJSP("task-editor.jsp");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean editSaveNew(Dispatcher dispatcher, Context context, String command) {
/*  554 */     User user = MilestoneSecurity.getUser(context);
/*      */ 
/*      */ 
/*      */     
/*  558 */     Notepad notepad = getTaskNotepad(context, user.getUserId());
/*  559 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */     
/*  561 */     Task task = new Task();
/*      */     
/*  563 */     Form form = buildNewForm(context, command);
/*      */     
/*  565 */     form.setValues(context);
/*      */ 
/*      */ 
/*      */     
/*  569 */     task.setTaskID(-1);
/*      */ 
/*      */     
/*  572 */     String taskNameString = "";
/*  573 */     if (form.getStringValue("taskName") != null)
/*      */     {
/*  575 */       taskNameString = form.getStringValue("taskName");
/*      */     }
/*  577 */     task.setTaskName(taskNameString);
/*      */ 
/*      */     
/*  580 */     String taskDescriptionString = "";
/*  581 */     if (form.getStringValue("taskDescription") != null)
/*      */     {
/*  583 */       taskDescriptionString = form.getStringValue("taskDescription");
/*      */     }
/*  585 */     task.setTaskDescription(taskDescriptionString);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  590 */     String taskAbbreviationString = form.getStringValue("taskAbbreviation");
/*  591 */     int taskAbbreviation = -1;
/*  592 */     if (taskAbbreviationString != null && !taskAbbreviationString.equals("")) {
/*      */       
/*      */       try {
/*      */         
/*  596 */         taskAbbreviation = Integer.parseInt(taskAbbreviationString);
/*      */       }
/*  598 */       catch (NumberFormatException numberFormatException) {}
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  604 */     task.setTaskAbbreviation(taskAbbreviation);
/*      */ 
/*      */     
/*  607 */     task.setTaskAbbrStr(MilestoneHelper.getTaskAbbreivationNameById(taskAbbreviation));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  612 */     String dayOfWeekString = "";
/*  613 */     if (form.getStringValue("dayOfWeek") != null)
/*      */     {
/*  615 */       dayOfWeekString = form.getStringValue("dayOfWeek");
/*      */     }
/*  617 */     task.setDayOfTheWeek(new Day(dayOfWeekString));
/*      */ 
/*      */     
/*  620 */     String wksToReleaseString = "";
/*  621 */     if (form.getStringValue("weeks2Rel") != null)
/*      */     {
/*  623 */       wksToReleaseString = form.getStringValue("weeks2Rel");
/*      */     }
/*  625 */     int wks2Rel = 0;
/*      */     
/*      */     try {
/*  628 */       if (wksToReleaseString.equalsIgnoreCase("SOL") || dayOfWeekString.equalsIgnoreCase("9")) {
/*  629 */         wks2Rel = -10;
/*      */       } else {
/*  631 */         wks2Rel = Integer.parseInt(wksToReleaseString);
/*      */       } 
/*  633 */     } catch (NumberFormatException e) {
/*      */       
/*  635 */       wks2Rel = 0;
/*      */     } 
/*  637 */     task.setWeeksToRelease(wks2Rel);
/*      */ 
/*      */     
/*  640 */     String departmentString = "";
/*  641 */     if (form.getStringValue("department") != null && !form.getStringValue("department").equalsIgnoreCase("-1"))
/*      */     {
/*  643 */       departmentString = form.getStringValue("department");
/*      */     }
/*  645 */     task.setDepartment(departmentString);
/*      */ 
/*      */     
/*  648 */     String categoryString = "";
/*  649 */     if (form.getStringValue("category") != null)
/*      */     {
/*  651 */       categoryString = form.getStringValue("category");
/*      */     }
/*  653 */     task.setCategory(categoryString);
/*      */ 
/*      */     
/*  656 */     boolean keyTaskBool = ((FormCheckBox)form.getElement("keyTask")).isChecked();
/*  657 */     task.setIsKeyTask(keyTaskBool);
/*      */ 
/*      */     
/*  660 */     boolean activeFlagBool = ((FormCheckBox)form.getElement("activeFlag")).isChecked();
/*  661 */     task.setActiveFlag(activeFlagBool);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  667 */     String ownerString = "";
/*  668 */     int ownerID = 0;
/*  669 */     if (form.getStringValue("owner") != null)
/*      */     {
/*  671 */       ownerString = form.getStringValue("owner");
/*      */     }
/*      */     
/*      */     try {
/*  675 */       ownerID = Integer.parseInt(ownerString);
/*      */     }
/*  677 */     catch (NumberFormatException e) {
/*      */       
/*  679 */       ownerID = 0;
/*      */     } 
/*      */     
/*  682 */     task.setOwner((Family)MilestoneHelper.getStructureObject(ownerID));
/*      */ 
/*      */     
/*  685 */     String weekAdjustmentString = "";
/*  686 */     if (form.getStringValue("weekAdjustment") != null)
/*      */     {
/*  688 */       weekAdjustmentString = form.getStringValue("weekAdjustment");
/*      */     }
/*  690 */     task.setDayOfTheWeek(new Day(dayOfWeekString));
/*  691 */     int weekAdjust = 0;
/*      */     
/*      */     try {
/*  694 */       weekAdjust = Integer.parseInt(weekAdjustmentString);
/*      */     }
/*  696 */     catch (NumberFormatException e) {
/*      */       
/*  698 */       weekAdjust = 0;
/*      */     } 
/*  700 */     task.setWeekAdjustment(weekAdjust);
/*      */ 
/*      */     
/*  703 */     String commentsString = "";
/*  704 */     if (form.getStringValue("comments") != null)
/*      */     {
/*  706 */       commentsString = form.getStringValue("comments");
/*      */     }
/*  708 */     task.setComments(commentsString);
/*      */ 
/*      */     
/*  711 */     int allowMultInt = 0;
/*      */     try {
/*  713 */       allowMultInt = Integer.parseInt(form.getStringValue("allowMultCompleteDatesFlag"));
/*      */     }
/*  715 */     catch (NumberFormatException e) {
/*  716 */       allowMultInt = 0;
/*      */     } 
/*  718 */     boolean allowMultBool = (allowMultInt == 1);
/*  719 */     task.setAllowMultCompleteDatesFlag(allowMultBool);
/*      */ 
/*      */     
/*  722 */     task.setLastUpdatingUser(user.getUserId());
/*      */     
/*  724 */     if (!TaskManager.getInstance().isDuplicateTask(task)) {
/*      */       
/*  726 */       if (!form.isUnchanged()) {
/*      */         
/*  728 */         FormValidation formValidation = form.validate();
/*  729 */         if (formValidation.isGood())
/*      */         {
/*  731 */           Task savedTask = TaskManager.getInstance().saveTask(task, user.getUserId());
/*      */           
/*  733 */           context.putSessionValue("Task", savedTask);
/*      */           
/*  735 */           context.putDelivery("Form", form);
/*      */ 
/*      */           
/*  738 */           notepad.setAllContents(null);
/*  739 */           notepad.newSelectedReset();
/*  740 */           notepad = getTaskNotepad(context, user.getUserId());
/*  741 */           notepad.setSelected(savedTask);
/*      */         
/*      */         }
/*      */         else
/*      */         {
/*  746 */           context.putDelivery("FormValidation", formValidation);
/*  747 */           form.addElement(new FormHidden("OrderBy", "", true));
/*  748 */           context.putDelivery("Form", form);
/*  749 */           return context.includeJSP("task-editor.jsp");
/*      */         }
/*      */       
/*      */       } 
/*      */     } else {
/*      */       
/*  755 */       context.putDelivery("AlertMessage", "Task already exists in database. Change the name,weeks to release or day of week.");
/*      */     } 
/*  757 */     return edit(context, "task-editor");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Form buildForm(Context context, Task task, String command) {
/*  767 */     Calendar testDate = Calendar.getInstance();
/*  768 */     User sessionUser = MilestoneSecurity.getUser(context);
/*      */     
/*  770 */     Form form = new Form(this.application, "TaskForm", 
/*  771 */         this.application.getInfrastructure().getServletURL(), "POST");
/*      */     
/*  773 */     int secureLevel = getTaskPermissions(task, sessionUser);
/*  774 */     setButtonVisibilities(task, sessionUser, context, secureLevel, command);
/*      */ 
/*      */     
/*  777 */     FormTextField name = new FormTextField("taskName", true, 50);
/*  778 */     name.setValue(task.getTaskName());
/*  779 */     form.addElement(name);
/*      */ 
/*      */     
/*  782 */     FormCheckBox activeFlag = new FormCheckBox("activeFlag", "", false, task.getActiveFlag());
/*      */     
/*  784 */     form.addElement(activeFlag);
/*      */ 
/*      */ 
/*      */     
/*  788 */     String comments = "";
/*  789 */     if (task.getComments().equals("[none]")) {
/*      */       
/*  791 */       comments = "";
/*      */     }
/*      */     else {
/*      */       
/*  795 */       comments = task.getComments();
/*      */     } 
/*  797 */     FormTextArea commentsTextArea = new FormTextArea("comments", "", false, 2, 44, "virtual");
/*  798 */     commentsTextArea.setId("comments");
/*  799 */     commentsTextArea.setValue(comments);
/*  800 */     commentsTextArea.addFormEvent("onBlur", "Javascript:checkField(this)");
/*  801 */     form.addElement(commentsTextArea);
/*      */ 
/*      */     
/*  804 */     FormCheckBox keyTaskIndicator = new FormCheckBox("keyTask", "", false, task.getIsKeyTask());
/*      */     
/*  806 */     form.addElement(keyTaskIndicator);
/*      */ 
/*      */     
/*  809 */     FormTextField wksToRelease = new FormTextField("weeks2Rel", false, 5);
/*  810 */     if (task.getWeeksToRelease() == -10) {
/*  811 */       wksToRelease.setValue("SOL");
/*      */     } else {
/*  813 */       wksToRelease.setValue(String.valueOf(task.getWeeksToRelease()));
/*      */     } 
/*  815 */     form.addElement(wksToRelease);
/*      */ 
/*      */ 
/*      */     
/*  819 */     FormDropDownMenu dayOfWeekDropdown = MilestoneHelper.getLookupDropDown("dayOfWeek", Cache.getLookupDetailValuesFromDatabase(5), "", false, true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  827 */     if (task.getDayOfTheWeek() != null) {
/*  828 */       dayOfWeekDropdown.setValue(task.getDayOfTheWeek().getDay());
/*      */     }
/*  830 */     form.addElement(dayOfWeekDropdown);
/*      */ 
/*      */     
/*  833 */     FormIntegerField weekAdjustment = new FormIntegerField("weekAdjustment", "", false, 1, 99, 5);
/*  834 */     weekAdjustment.setValue(String.valueOf(task.getWeekAdjustment()));
/*  835 */     form.addElement(weekAdjustment);
/*      */ 
/*      */     
/*  838 */     String holdDepartment = "";
/*  839 */     if (task.getDepartment() != null)
/*      */     {
/*  841 */       holdDepartment = task.getDepartment();
/*      */     }
/*  843 */     FormDropDownMenu department = MilestoneHelper.getDepartmentDropDown("department", holdDepartment, false);
/*  844 */     form.addElement(department);
/*      */ 
/*      */     
/*  847 */     String category = "";
/*  848 */     if (!task.getCategory().equals("[none]"))
/*      */     {
/*  850 */       category = task.getCategory();
/*      */     }
/*  852 */     FormTextField Category = new FormTextField("category", false, 20);
/*  853 */     Category.setValue(category);
/*  854 */     form.addElement(Category);
/*      */ 
/*      */     
/*  857 */     Vector families = MilestoneHelper.getNonSecureUserFamilies(context);
/*  858 */     String owner = "";
/*  859 */     if (task.getOwner() != null) {
/*      */       
/*      */       try {
/*      */         
/*  863 */         owner = Integer.toString(task.getOwner().getStructureID());
/*      */       }
/*  865 */       catch (NumberFormatException numberFormatException) {}
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  870 */     FormDropDownMenu Owner = MilestoneHelper.getCorporateStructureDropDown("owner", families, owner, true, false);
/*  871 */     form.addElement(Owner);
/*      */ 
/*      */     
/*  874 */     String[] allowLabels = new String[2];
/*  875 */     allowLabels[0] = "No";
/*  876 */     allowLabels[1] = "Yes";
/*      */     
/*  878 */     String[] allowValues = new String[2];
/*  879 */     allowValues[0] = "0";
/*  880 */     allowValues[1] = "1";
/*      */     
/*  882 */     String allowMultCompleteDatesFlagSel = task.getAllowMultCompleteDatesFlag() ? "1" : "0";
/*  883 */     FormRadioButtonGroup allowMultCompleteDatesFlag = new FormRadioButtonGroup("allowMultCompleteDatesFlag", 
/*  884 */         allowMultCompleteDatesFlagSel, allowValues, allowLabels, false);
/*  885 */     form.addElement(allowMultCompleteDatesFlag);
/*      */ 
/*      */     
/*  888 */     String holdAbbrev = "";
/*  889 */     if (task.getTaskAbbreviation() > 0)
/*      */     {
/*  891 */       holdAbbrev = Integer.toString(task.getTaskAbbreviation());
/*      */     }
/*  893 */     FormDropDownMenu taskAbbrev = MilestoneHelper.getTaskAbbreviationsDropDown("taskAbbreviation", holdAbbrev, false);
/*  894 */     form.addElement(taskAbbrev);
/*      */ 
/*      */     
/*  897 */     String description = "";
/*  898 */     if (!task.getTaskDescription().equals("[none]"))
/*      */     {
/*  900 */       description = task.getTaskDescription();
/*      */     }
/*  902 */     FormTextField taskDescription = new FormTextField("taskDescription", false, 50);
/*  903 */     taskDescription.setValue(description);
/*  904 */     form.addElement(taskDescription);
/*      */ 
/*      */     
/*  907 */     Vector templatesList = task.getTemplates();
/*  908 */     String templates = "";
/*  909 */     String templatesStringList = "";
/*      */     
/*  911 */     for (int i = 0; i < templatesList.size(); i++) {
/*      */       
/*  913 */       templates = (String)templatesList.get(i);
/*      */       
/*  915 */       if (i == 0) {
/*  916 */         templatesStringList = "," + templates;
/*      */       } else {
/*  918 */         templatesStringList = String.valueOf(templatesStringList) + "," + templates;
/*      */       } 
/*      */     } 
/*  921 */     FormDropDownMenu templateNames = new FormDropDownMenu("templateNames", "", templatesStringList, false);
/*  922 */     templateNames.addFormEvent("style", "background-color:lightgrey;");
/*  923 */     templateNames.addFormEvent("size", "3");
/*  924 */     form.addElement(templateNames);
/*      */ 
/*      */     
/*  927 */     FormTextField lastUpdated = new FormTextField("lastUpdatedDate", false, 50);
/*  928 */     lastUpdated.setValue(MilestoneHelper.getCustomFormatedDate(task.getLastUpdateDate(), "M/d/yyyy hh:mm:ss a 'ET'"));
/*  929 */     form.addElement(lastUpdated);
/*      */     
/*  931 */     FormTextField lastUpdatedBy = new FormTextField("lastUpdatedBy", false, 50);
/*      */     
/*  933 */     if (UserManager.getInstance().getUser(task.getLastUpdatingUser()) != null) {
/*  934 */       lastUpdatedBy.setValue(UserManager.getInstance().getUser(task.getLastUpdatingUser()).getName());
/*      */     }
/*  936 */     form.addElement(lastUpdatedBy);
/*      */     
/*  938 */     addSelectionSearchElements(context, form);
/*      */ 
/*      */     
/*  941 */     context.putSessionValue("task", task);
/*      */     
/*  943 */     form.addElement(new FormHidden("cmd", "task-editor"));
/*  944 */     form.addElement(new FormHidden("OrderBy", "", true));
/*      */ 
/*      */     
/*  947 */     if (context.getSessionValue("NOTEPAD_TASKS_VISIBLE") != null) {
/*  948 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_TASKS_VISIBLE"));
/*      */     }
/*  950 */     return form;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Form buildNewForm(Context context, String command) {
/*  958 */     Form form = new Form(this.application, "TaskForm", 
/*  959 */         this.application.getInfrastructure().getServletURL(), "POST");
/*  960 */     User sessionUser = MilestoneSecurity.getUser(context);
/*      */     
/*  962 */     Task task = new Task();
/*  963 */     task.setTaskID(-1);
/*      */ 
/*      */     
/*  966 */     int secureLevel = getTaskPermissions(task, sessionUser);
/*  967 */     setButtonVisibilities(task, sessionUser, context, secureLevel, command);
/*      */ 
/*      */     
/*  970 */     FormTextField name = new FormTextField("taskName", true, 50);
/*  971 */     form.addElement(name);
/*      */ 
/*      */     
/*  974 */     FormCheckBox activeFlag = new FormCheckBox("activeFlag", "", false, true);
/*      */     
/*  976 */     form.addElement(activeFlag);
/*      */ 
/*      */ 
/*      */     
/*  980 */     FormTextArea commentsTextArea = new FormTextArea("comments", "", false, 2, 44, "virtual");
/*  981 */     commentsTextArea.setId("comments");
/*  982 */     commentsTextArea.addFormEvent("onBlur", "Javascript:checkField(this)");
/*  983 */     form.addElement(commentsTextArea);
/*      */ 
/*      */ 
/*      */     
/*  987 */     FormCheckBox keyTaskIndicator = new FormCheckBox("keyTask", "", false, false);
/*  988 */     form.addElement(keyTaskIndicator);
/*      */ 
/*      */     
/*  991 */     FormTextField wksToRelease = new FormTextField("weeks2Rel", false, 5);
/*  992 */     form.addElement(wksToRelease);
/*      */ 
/*      */     
/*  995 */     FormDropDownMenu dayOfWeekDropdown = MilestoneHelper.getLookupDropDown("dayOfWeek", Cache.getLookupDetailValuesFromDatabase(5), "", false, true);
/*      */     
/*  997 */     form.addElement(dayOfWeekDropdown);
/*      */ 
/*      */     
/* 1000 */     FormIntegerField weekAdjustment = new FormIntegerField("weekAdjustment", "", false, 1, 99, 5);
/* 1001 */     form.addElement(weekAdjustment);
/*      */ 
/*      */     
/* 1004 */     FormDropDownMenu department = MilestoneHelper.getDepartmentDropDown("department", "", false);
/* 1005 */     form.addElement(department);
/*      */ 
/*      */     
/* 1008 */     FormTextField category = new FormTextField("category", false, 20);
/* 1009 */     form.addElement(category);
/*      */ 
/*      */     
/* 1012 */     Vector families = MilestoneHelper.getSecureUserFamilies(sessionUser, 2, context);
/* 1013 */     FormDropDownMenu Owner = MilestoneHelper.getCorporateStructureDropDown("owner", families, "0", false, false);
/* 1014 */     form.addElement(Owner);
/*      */ 
/*      */     
/* 1017 */     String[] allowLabels = new String[2];
/* 1018 */     allowLabels[0] = "No";
/* 1019 */     allowLabels[1] = "Yes";
/*      */     
/* 1021 */     String[] allowValues = new String[2];
/* 1022 */     allowValues[0] = "0";
/* 1023 */     allowValues[1] = "1";
/*      */     
/* 1025 */     String allowMultCompleteDatesFlagSel = task.getAllowMultCompleteDatesFlag() ? "1" : "0";
/* 1026 */     FormRadioButtonGroup allowMultCompleteDatesFlag = new FormRadioButtonGroup("allowMultCompleteDatesFlag", 
/* 1027 */         allowMultCompleteDatesFlagSel, allowValues, allowLabels, false);
/* 1028 */     form.addElement(allowMultCompleteDatesFlag);
/*      */ 
/*      */     
/* 1031 */     FormDropDownMenu taskAbbrev = MilestoneHelper.getTaskAbbreviationsDropDown("taskAbbreviation", "", false);
/* 1032 */     form.addElement(taskAbbrev);
/*      */ 
/*      */     
/* 1035 */     FormTextField taskDescription = new FormTextField("taskDescription", false, 50);
/* 1036 */     form.addElement(taskDescription);
/*      */ 
/*      */     
/* 1039 */     FormDropDownMenu templateNames = new FormDropDownMenu("templateNames", "", "", false);
/* 1040 */     templateNames.addFormEvent("style", "background-color:lightgrey;");
/* 1041 */     templateNames.addFormEvent("size", "3");
/* 1042 */     form.addElement(templateNames);
/*      */ 
/*      */     
/* 1045 */     FormTextField lastUpdated = new FormTextField("lastUpdatedDate", false, 50);
/* 1046 */     form.addElement(lastUpdated);
/*      */     
/* 1048 */     FormTextField lastUpdatedBy = new FormTextField("lastUpdatedBy", false, 50);
/* 1049 */     form.addElement(lastUpdatedBy);
/*      */     
/* 1051 */     addSelectionSearchElements(context, form);
/* 1052 */     form.addElement(new FormHidden("cmd", "task-edit-new"));
/* 1053 */     form.addElement(new FormHidden("OrderBy", "", true));
/*      */ 
/*      */     
/* 1056 */     if (context.getSessionValue("NOTEPAD_TASKS_VISIBLE") != null) {
/* 1057 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_TASKS_VISIBLE"));
/*      */     }
/* 1059 */     return form;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addSelectionSearchElements(Context context, Form form) {
/* 1068 */     FormTextField taskNameSrch = new FormTextField("taskNameSrch", "", false, 20, 20);
/* 1069 */     taskNameSrch.setId("taskNameSrch");
/* 1070 */     form.addElement(taskNameSrch);
/*      */ 
/*      */     
/* 1073 */     FormTextField taskAbbrevSrch = new FormTextField("taskAbbrevSrch", "", false, 4, 4);
/* 1074 */     taskAbbrevSrch.setId("taskAbbrevSrch");
/* 1075 */     form.addElement(taskAbbrevSrch);
/*      */ 
/*      */     
/* 1078 */     FormCheckBox keyTaskSrch = new FormCheckBox("keyTaskSrch", "", "", false);
/* 1079 */     keyTaskSrch.setId("keyTaskSrch");
/* 1080 */     form.addElement(keyTaskSrch);
/*      */ 
/*      */     
/* 1083 */     User sessionUser = MilestoneSecurity.getUser(context);
/* 1084 */     Vector families = MilestoneHelper.getNonSecureUserFamilies(context);
/* 1085 */     FormDropDownMenu TaskOwnerSearch = MilestoneHelper.getCorporateStructureDropDown("ownerSrch", families, "0", false, true);
/* 1086 */     form.addElement(TaskOwnerSearch);
/*      */ 
/*      */ 
/*      */     
/* 1090 */     FormDropDownMenu TaskDepartmentSearch = MilestoneHelper.getDepartmentDropDown("departmentSrch", "", false);
/* 1091 */     form.addElement(TaskDepartmentSearch);
/*      */ 
/*      */     
/* 1094 */     FormCheckBox inactiveSrch = new FormCheckBox("inactiveSrch", "", "", false);
/* 1095 */     inactiveSrch.setId("inactiveSrch");
/* 1096 */     form.addElement(inactiveSrch);
/*      */ 
/*      */     
/* 1099 */     String[] allowLabels = new String[3];
/* 1100 */     allowLabels[0] = "No";
/* 1101 */     allowLabels[1] = "Yes";
/* 1102 */     allowLabels[2] = "All";
/*      */     
/* 1104 */     String[] allowValues = new String[3];
/* 1105 */     allowValues[0] = "0";
/* 1106 */     allowValues[1] = "1";
/* 1107 */     allowValues[2] = "2";
/* 1108 */     FormRadioButtonGroup allowMultCompleteDatesSrch = new FormRadioButtonGroup("allowMultCompleteDatesSrch", 
/* 1109 */         "2", allowValues, allowLabels, false);
/* 1110 */     form.addElement(allowMultCompleteDatesSrch);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Notepad getTaskNotepad(Context context, int userId) {
/* 1119 */     Vector contents = new Vector();
/*      */     
/* 1121 */     if (MilestoneHelper.getNotepadFromSession(4, context) != null) {
/*      */       
/* 1123 */       Notepad notepad = MilestoneHelper.getNotepadFromSession(4, context);
/*      */       
/* 1125 */       if (notepad.getAllContents() == null) {
/*      */         
/* 1127 */         this.log.debug("---------Reseting note pad contents------------");
/* 1128 */         contents = TaskManager.getInstance().getTaskNotepadList(context, notepad);
/* 1129 */         notepad.setAllContents(contents);
/*      */       } 
/*      */       
/* 1132 */       return notepad;
/*      */     } 
/*      */ 
/*      */     
/* 1136 */     String[] columnNames = { "Task Name", "Wks to Rls", "Own", "Dpt" };
/* 1137 */     contents = TaskManager.getInstance().getTaskNotepadList(context, null);
/* 1138 */     return new Notepad(contents, 0, 15, "Tasks", 4, columnNames);
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
/*      */   private boolean goToBlank(Context context) {
/* 1155 */     context.putDelivery("isNotePadVisible", new Boolean(MilestoneHelper.getNotePadVisiblitiy(4, context)));
/*      */     
/* 1157 */     Form form = new Form(this.application, "form", this.application.getInfrastructure().getServletURL(), "Post");
/* 1158 */     form.addElement(new FormHidden("cmd", "task-editor"));
/* 1159 */     form.addElement(new FormHidden("OrderBy", "", true));
/*      */ 
/*      */     
/* 1162 */     addSelectionSearchElements(context, form);
/*      */     
/* 1164 */     context.putDelivery("Form", form);
/*      */     
/* 1166 */     return context.includeJSP("blank-task-editor.jsp");
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
/*      */   public static int getTaskPermissions(Task task, User user) {
/* 1214 */     int level = 0;
/* 1215 */     int familyId = 0;
/*      */     
/* 1217 */     if (task != null && task.getTaskID() > -1) {
/*      */       
/* 1219 */       Family family = task.getOwner();
/*      */       
/* 1221 */       Vector environments = family.getEnvironments();
/*      */       
/* 1223 */       for (int i = 0; i < environments.size(); i++) {
/*      */         
/* 1225 */         Environment environment = (Environment)environments.get(i);
/* 1226 */         if (environment != null) {
/* 1227 */           familyId = environment.getParentFamily().getStructureID();
/*      */         }
/* 1229 */         if (familyId == family.getStructureID()) {
/*      */ 
/*      */           
/* 1232 */           Vector companies = environment.getCompanies();
/* 1233 */           Company company = null;
/*      */ 
/*      */           
/* 1236 */           if (companies != null && companies.size() > 0) {
/* 1237 */             company = (Company)companies.get(0);
/*      */           }
/* 1239 */           CompanyAcl companyAcl = null;
/* 1240 */           if (company != null) {
/* 1241 */             companyAcl = MilestoneHelper.getScreenPermissions(company, user);
/*      */           }
/* 1243 */           if (companyAcl != null && companyAcl.getAccessTask() > level)
/*      */           {
/* 1245 */             level = companyAcl.getAccessTask();
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1251 */     return level;
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
/*      */   public static int getTaskNewButtonPermissions(User user) {
/* 1264 */     int level = 0;
/*      */     
/* 1266 */     Vector companies = MilestoneHelper.getUserCompanies(user.getUserId());
/*      */     
/* 1268 */     if (companies != null)
/*      */     {
/* 1270 */       for (int i = 0; i < companies.size(); i++) {
/*      */         
/* 1272 */         Company company = (Company)companies.get(i);
/* 1273 */         if (company != null) {
/*      */           
/* 1275 */           CompanyAcl companyAcl = MilestoneHelper.getScreenPermissions(company, user);
/* 1276 */           if (companyAcl != null && companyAcl.getAccessTask() > level)
/*      */           {
/* 1278 */             level = companyAcl.getAccessTask();
/*      */           }
/*      */         } 
/*      */       } 
/*      */     }
/* 1283 */     return level;
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
/*      */   public void setButtonVisibilities(Task task, User user, Context context, int level, String command) {
/* 1305 */     String copyVisible = "true";
/* 1306 */     String saveVisible = "false";
/* 1307 */     String deleteVisible = "false";
/* 1308 */     String newVisible = "false";
/*      */     
/* 1310 */     if (level > 1) {
/*      */       
/* 1312 */       saveVisible = "true";
/* 1313 */       deleteVisible = "true";
/*      */     } 
/*      */     
/* 1316 */     int newButtonPermission = getTaskNewButtonPermissions(user);
/*      */     
/* 1318 */     if (newButtonPermission > 1)
/*      */     {
/* 1320 */       newVisible = "true";
/*      */     }
/*      */     
/* 1323 */     if (command.indexOf("new") > -1 || command.indexOf("copy") > -1) {
/*      */       
/* 1325 */       saveVisible = "true";
/* 1326 */       copyVisible = "false";
/* 1327 */       deleteVisible = "false";
/* 1328 */       newVisible = "false";
/*      */     } 
/*      */ 
/*      */     
/* 1332 */     context.putDelivery("saveVisible", saveVisible);
/*      */     
/* 1334 */     context.putDelivery("copyVisible", copyVisible);
/*      */     
/* 1336 */     context.putDelivery("deleteVisible", deleteVisible);
/*      */     
/* 1338 */     context.putDelivery("newVisible", newVisible);
/*      */   }
/*      */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\TaskHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */