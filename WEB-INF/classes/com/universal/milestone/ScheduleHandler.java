/*      */ package WEB-INF.classes.com.universal.milestone;
/*      */ 
/*      */ import com.techempower.ComponentLog;
/*      */ import com.techempower.gemini.Context;
/*      */ import com.techempower.gemini.Dispatcher;
/*      */ import com.techempower.gemini.FormCheckBox;
/*      */ import com.techempower.gemini.FormDropDownMenu;
/*      */ import com.techempower.gemini.FormHidden;
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
/*      */ import com.universal.milestone.JdbcConnector;
/*      */ import com.universal.milestone.Label;
/*      */ import com.universal.milestone.LookupObject;
/*      */ import com.universal.milestone.MilestoneConstants;
/*      */ import com.universal.milestone.MilestoneHelper;
/*      */ import com.universal.milestone.MilestoneHelper_2;
/*      */ import com.universal.milestone.MultCompleteDate;
/*      */ import com.universal.milestone.Notepad;
/*      */ import com.universal.milestone.NotepadSortOrder;
/*      */ import com.universal.milestone.ProductCategory;
/*      */ import com.universal.milestone.ReleasingFamily;
/*      */ import com.universal.milestone.SchedTaskCompleteComparator;
/*      */ import com.universal.milestone.SchedTaskDueDateComparator;
/*      */ import com.universal.milestone.SchedTaskNameComparator;
/*      */ import com.universal.milestone.SchedTaskStatusComparator;
/*      */ import com.universal.milestone.SchedTaskVendorComparator;
/*      */ import com.universal.milestone.SchedTaskWksToReleaseComparator;
/*      */ import com.universal.milestone.Schedule;
/*      */ import com.universal.milestone.ScheduleHandler;
/*      */ import com.universal.milestone.ScheduleManager;
/*      */ import com.universal.milestone.ScheduledTask;
/*      */ import com.universal.milestone.SecureHandler;
/*      */ import com.universal.milestone.Selection;
/*      */ import com.universal.milestone.SelectionHandler;
/*      */ import com.universal.milestone.SelectionManager;
/*      */ import com.universal.milestone.Task;
/*      */ import com.universal.milestone.Template;
/*      */ import com.universal.milestone.User;
/*      */ import java.util.Arrays;
/*      */ import java.util.Calendar;
/*      */ import java.util.HashMap;
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
/*      */ public class ScheduleHandler
/*      */   extends SecureHandler
/*      */ {
/*      */   public static final String COMPONENT_CODE = "hSched";
/*      */   public GeminiApplication application;
/*      */   public ComponentLog log;
/*      */   public static final String deptFilterFormat = "department.filter.";
/*      */   public static final String filterPhysical = "All,Only Label Tasks,Only UML Tasks";
/*      */   public static final String filterDigital = "All,Only Label Tasks,Only eCommerce Tasks";
/*      */   
/*      */   public ScheduleHandler(GeminiApplication application) {
/*   93 */     this.application = application;
/*   94 */     this.log = application.getLog("hSched");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ScheduleHandler() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  110 */   public String getDescription() { return "Schedule"; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean acceptRequest(Dispatcher dispatcher, Context context, String command) {
/*  120 */     if (super.acceptRequest(dispatcher, context, command))
/*      */     {
/*  122 */       if (command.startsWith("schedule"))
/*      */       {
/*  124 */         return handleRequest(dispatcher, context, command);
/*      */       }
/*      */     }
/*  127 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean handleRequest(Dispatcher dispatcher, Context context, String command) {
/*  135 */     if (command.equalsIgnoreCase("schedule-editor")) {
/*      */       
/*  137 */       edit(dispatcher, context, command);
/*      */     }
/*  139 */     else if (command.equalsIgnoreCase("schedule-recalc")) {
/*      */       
/*  141 */       recalc(dispatcher, context, command, 0);
/*      */     }
/*  143 */     else if (command.equalsIgnoreCase("schedule-recalc-all")) {
/*      */       
/*  145 */       recalcAll(dispatcher, context, command, 0);
/*      */     }
/*  147 */     else if (command.equalsIgnoreCase("schedule-close")) {
/*      */       
/*  149 */       close(dispatcher, context, command, 0);
/*      */     }
/*  151 */     else if (command.equalsIgnoreCase("schedule-clear")) {
/*      */       
/*  153 */       clear(dispatcher, context, command, 0);
/*      */     }
/*  155 */     else if (command.equalsIgnoreCase("schedule-task-recalc")) {
/*      */       
/*  157 */       recalc(dispatcher, context, command, 1);
/*      */     }
/*  159 */     else if (command.equalsIgnoreCase("schedule-task-recalc-all")) {
/*      */       
/*  161 */       recalcAll(dispatcher, context, command, 1);
/*      */     }
/*  163 */     else if (command.equalsIgnoreCase("schedule-task-close")) {
/*      */       
/*  165 */       close(dispatcher, context, command, 1);
/*      */     }
/*  167 */     else if (command.equalsIgnoreCase("schedule-task-clear")) {
/*      */       
/*  169 */       clear(dispatcher, context, command, 1);
/*      */     }
/*  171 */     else if (command.equalsIgnoreCase("schedule-save")) {
/*      */       
/*  173 */       editSave(dispatcher, context, command, 0);
/*      */     }
/*  175 */     else if (command.equalsIgnoreCase("schedule-task-save")) {
/*      */       
/*  177 */       editSave(dispatcher, context, command, 1);
/*      */     }
/*  179 */     else if (command.equalsIgnoreCase("schedule-task-editor")) {
/*      */       
/*  181 */       editTasks(dispatcher, context, command);
/*      */     }
/*  183 */     else if (command.equalsIgnoreCase("schedule-task-search")) {
/*      */       
/*  185 */       scheduleTaskSearch(dispatcher, context, command);
/*      */     }
/*  187 */     else if (command.equalsIgnoreCase("schedule-task-sort")) {
/*      */       
/*  189 */       scheduleTaskSort(dispatcher, context);
/*      */     }
/*  191 */     else if (command.equalsIgnoreCase("schedule-screen_task-sort")) {
/*      */       
/*  193 */       sortScheduleScreenTasks(dispatcher, context);
/*      */     }
/*  195 */     else if (command.equalsIgnoreCase("schedule-task-screen-task-sort")) {
/*      */       
/*  197 */       sortScheduleTaskScreenTasks(dispatcher, context);
/*      */     }
/*  199 */     else if (command.equalsIgnoreCase("schedule-add-task")) {
/*      */       
/*  201 */       addTask(dispatcher, context, command);
/*      */     }
/*  203 */     else if (command.equalsIgnoreCase("schedule-delete-task-editor")) {
/*      */       
/*  205 */       deleteTaskInTaskEditor(dispatcher, context, command);
/*      */     }
/*  207 */     else if (command.equalsIgnoreCase("schedule-delete-task")) {
/*      */       
/*  209 */       deleteTask(dispatcher, context, command);
/*      */     }
/*  211 */     else if (command.equalsIgnoreCase("schedule-delete-all-tasks")) {
/*      */       
/*  213 */       deleteAllTasks(dispatcher, context, command, 0);
/*      */     }
/*  215 */     else if (command.equalsIgnoreCase("schedule-delete-all-tasks-in-task-editor")) {
/*      */       
/*  217 */       deleteAllTasks(dispatcher, context, command, 1);
/*      */     }
/*  219 */     else if (command.equalsIgnoreCase("schedule-selection-search")) {
/*      */       
/*  221 */       scheduleSelectionSearch(dispatcher, context, command);
/*      */     }
/*  223 */     else if (command.equalsIgnoreCase("schedule-sort")) {
/*      */       
/*  225 */       scheduleSelectionSort(dispatcher, context);
/*      */     }
/*  227 */     else if (command.equalsIgnoreCase("schedule-selection-release-search")) {
/*      */       
/*  229 */       selectionSearch(dispatcher, context, command);
/*      */     }
/*  231 */     else if (command.equalsIgnoreCase("schedule-temlate-search")) {
/*      */       
/*  233 */       templateSearch(dispatcher, context, command);
/*      */     }
/*  235 */     else if (command.equalsIgnoreCase("schedule-select-template")) {
/*      */       
/*  237 */       selectTemplate(dispatcher, context, command);
/*      */     }
/*  239 */     else if (command.equalsIgnoreCase("schedule-assign-template")) {
/*      */       
/*  241 */       assignTemplate(dispatcher, context, command);
/*      */     }
/*  243 */     else if (command.equalsIgnoreCase("schedule-copy-release-schedule")) {
/*      */       
/*  245 */       scheduleSelectionSearch(dispatcher, context, command);
/*      */     }
/*  247 */     else if (command.equalsIgnoreCase("schedule-copy-release-assign-schedule")) {
/*      */       
/*  249 */       copySchedule(dispatcher, context, command);
/*      */     }
/*  251 */     else if (command.equalsIgnoreCase("schedule-filter")) {
/*      */       
/*  253 */       filter(dispatcher, context, command);
/*      */     }
/*  255 */     else if (command.equalsIgnoreCase("schedule-task-filter")) {
/*      */       
/*  257 */       filterTask(dispatcher, context, command);
/*      */     }
/*  259 */     else if (command.equalsIgnoreCase("schedule-dept-filter")) {
/*      */       
/*  261 */       deptFilter(dispatcher, context, command);
/*      */     }
/*  263 */     else if (command.equalsIgnoreCase("schedule-task-dept-filter")) {
/*      */       
/*  265 */       deptFilterTask(dispatcher, context, command);
/*      */     }
/*  267 */     else if (command.equalsIgnoreCase("schedule-group")) {
/*      */       
/*  269 */       scheduleSelectionGroup(dispatcher, context);
/*      */     }
/*  271 */     else if (command.equalsIgnoreCase("schedule-save-selection-comments")) {
/*      */       
/*  273 */       saveSelectionComments(dispatcher, context, command);
/*      */     }
/*  275 */     else if (command.equalsIgnoreCase("schedule-multCompleteDates-frame")) {
/*      */       
/*  277 */       multCompleteDateFrame(dispatcher, context, command);
/*      */     }
/*  279 */     else if (command.equalsIgnoreCase("schedule-multCompleteDates-editor")) {
/*      */       
/*  281 */       multCompleteDateEditor(dispatcher, context, command);
/*      */     }
/*  283 */     else if (command.equalsIgnoreCase("schedule-multCompleteDates-cancel")) {
/*  284 */       multCompleteDateEditorCancel(dispatcher, context, command);
/*      */     }
/*  286 */     else if (command.equalsIgnoreCase("schedule-multCompleteDates-save")) {
/*  287 */       multCompleteDateEditorSave(dispatcher, context, command);
/*      */     }
/*  289 */     else if (command.equalsIgnoreCase("schedule-multCompleteDates-add")) {
/*  290 */       multCompleteDateEditorModify(dispatcher, context, command);
/*      */     }
/*  292 */     else if (command.equalsIgnoreCase("schedule-multCompleteDates-delete")) {
/*  293 */       multCompleteDateEditorModify(dispatcher, context, command);
/*      */     } 
/*      */     
/*  296 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean filter(Dispatcher dispatcher, Context context, String command) {
/*  301 */     Form form = buildForm(context, null, null, command);
/*  302 */     form.setValues(context);
/*      */ 
/*      */     
/*  305 */     String filterValue = form.getStringValue("filter");
/*  306 */     context.putSessionValue("filter", filterValue);
/*      */     
/*  308 */     return edit(dispatcher, context, command);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean deptFilter(Dispatcher dispatcher, Context context, String command) {
/*  314 */     Form form = buildForm(context, null, null, command);
/*  315 */     form.setValues(context);
/*      */ 
/*      */     
/*  318 */     String filterValue = form.getStringValue("deptFilter");
/*  319 */     context.putSessionValue("deptFilter", filterValue);
/*      */     
/*  321 */     return edit(dispatcher, context, command);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean filterTask(Dispatcher dispatcher, Context context, String command) {
/*  328 */     Form form = buildForm(context, null, null, command);
/*  329 */     form.setValues(context);
/*      */ 
/*      */     
/*  332 */     String filterValue = form.getStringValue("filter");
/*  333 */     context.putSessionValue("filter", filterValue);
/*      */     
/*  335 */     return editTasks(dispatcher, context, command);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean deptFilterTask(Dispatcher dispatcher, Context context, String command) {
/*  341 */     Form form = buildForm(context, null, null, command);
/*  342 */     form.setValues(context);
/*      */ 
/*      */     
/*  345 */     String filterValue = form.getStringValue("deptFilter");
/*  346 */     context.putSessionValue("deptFilter", filterValue);
/*      */     
/*  348 */     return editTasks(dispatcher, context, command);
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
/*      */   private boolean edit(Dispatcher dispatcher, Context context, String command) {
/*  369 */     Notepad notepadTask = MilestoneHelper.getNotepadFromSession(2, context);
/*  370 */     this.log.log("<<< NotepadTask " + notepadTask);
/*  371 */     if (notepadTask != null) {
/*  372 */       ScheduleManager.getInstance().setTaskNotepadQuery(context, notepadTask);
/*      */       
/*  374 */       notepadTask.setAllContents(null);
/*  375 */       notepadTask.setSelected(null);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  382 */     Selection selection = null;
/*  383 */     User user = (User)context.getSessionValue("user");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  388 */     Notepad notepad = null;
/*      */ 
/*      */ 
/*      */     
/*  392 */     if (context.getRequestValue("releaseCalendar") != null) {
/*      */       
/*  394 */       user.SS_searchInitiated = true;
/*  395 */       notepad = (Notepad)context.getSessionValue(MilestoneConstants.NOTEPAD_SESSION_NAMES[1]);
/*  396 */       if (notepad == null) {
/*      */         
/*  398 */         notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 1);
/*  399 */         MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */       } 
/*      */       
/*  402 */       if (notepad != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  408 */         int selectionID = -1;
/*  409 */         if (context.getRequestValue("selection-id") != null) {
/*      */ 
/*      */ 
/*      */           
/*  413 */           notepad.setMaxRecords(1);
/*  414 */           notepad.setAllContents(null);
/*      */ 
/*      */           
/*  417 */           selectionID = Integer.parseInt(context.getRequestValue("selection-id"));
/*      */ 
/*      */           
/*  420 */           notepad.setSearchQuery(SelectionManager.getDefaultQuery(context));
/*      */ 
/*      */           
/*  423 */           if (notepad.getOrderBy() == null || notepad.getOrderBy().equals("")) {
/*      */ 
/*      */             
/*  426 */             NotepadSortOrder notepadSortOrder = NotepadSortOrder.getNotepadSortOrderFromSession(context);
/*  427 */             notepadSortOrder.setSelectionOrderCol("Artist");
/*  428 */             notepadSortOrder.setShowGroupButtons(true);
/*      */ 
/*      */ 
/*      */             
/*  432 */             if (notepad.getOrderBy().indexOf(" DESC ") == -1) {
/*  433 */               notepad.setOrderBy(" ORDER BY artist, title, selection_no, street_date ");
/*  434 */               notepadSortOrder.setSelectionOrderBy(" ORDER BY artist, title, selection_no, street_date ");
/*  435 */               notepadSortOrder.setSelectionOrderColNo(0);
/*      */             } else {
/*      */               
/*  438 */               notepad.setOrderBy(" ORDER BY artist DESC , title, selection_no, street_date ");
/*  439 */               notepadSortOrder.setSelectionOrderBy(" ORDER BY artist DESC , title, selection_no, street_date ");
/*  440 */               notepadSortOrder.setSelectionOrderColNo(7);
/*      */             } 
/*      */           } 
/*      */ 
/*      */           
/*  445 */           String closeStr = "NOT ( status = 'CLOSED' OR  status = 'CANCEL' )";
/*  446 */           int pos = notepad.getSearchQuery().indexOf(closeStr);
/*  447 */           if (pos != -1) {
/*      */             
/*  449 */             StringBuffer query = new StringBuffer(notepad.getSearchQuery());
/*  450 */             StringBuffer newQuery = query.delete(pos, pos + closeStr.length());
/*  451 */             newQuery.insert(pos, "(release_id = " + selectionID + ") ");
/*  452 */             notepad.setSearchQuery(newQuery.toString());
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
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
/*  508 */     notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 1);
/*  509 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */ 
/*      */     
/*  512 */     String recalcDate = (String)context.getDelivery("recalc-date");
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
/*  526 */     if (recalcDate != null && recalcDate.equalsIgnoreCase("true") && context.getSessionValue("Selection") != null) {
/*      */       
/*  528 */       int selectionId = ((Selection)context.getSessionValue("Selection")).getSelectionID();
/*  529 */       selection = SelectionManager.getInstance().getSelectionHeader(selectionId);
/*  530 */       notepad.setSelected(selection);
/*      */     }
/*      */     else {
/*      */       
/*  534 */       selection = MilestoneHelper.getScreenSelection(context);
/*      */     } 
/*      */     
/*  537 */     Schedule schedule = null;
/*      */     
/*  539 */     if (selection != null) {
/*      */       
/*  541 */       schedule = ScheduleManager.getInstance().getSchedule(selection.getSelectionID());
/*  542 */       selection.setSchedule(schedule);
/*      */ 
/*      */       
/*  545 */       setDeptFilter(schedule, context);
/*      */       
/*  547 */       String sessionFilter = (String)context.getSessionValue("filter");
/*      */ 
/*      */       
/*  550 */       if (!command.equalsIgnoreCase("schedule-filter")) {
/*      */         
/*  552 */         String filterValue = "All";
/*      */         
/*  554 */         if (selection.getIsDigital()) {
/*      */           
/*  556 */           if (user.getPreferences().getScheduleDigitalOwner() == 2)
/*  557 */             filterValue = "Only eCommerce Tasks"; 
/*  558 */           if (user.getPreferences().getScheduleDigitalOwner() == 3) {
/*  559 */             filterValue = "Only Label Tasks";
/*      */           }
/*      */         } else {
/*      */           
/*  563 */           if (user.getPreferences().getSchedulePhysicalOwner() == 2)
/*  564 */             filterValue = "Only UML Tasks"; 
/*  565 */           if (user.getPreferences().getSchedulePhysicalOwner() == 3) {
/*  566 */             filterValue = "Only Label Tasks";
/*      */           }
/*      */         } 
/*  569 */         sessionFilter = filterValue;
/*  570 */         context.putSessionValue("filter", filterValue);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  576 */       sessionFilter = clearFilterIfProdChange(sessionFilter, selection, context);
/*  577 */       if (sessionFilter != null && sessionFilter.length() > 0)
/*      */       {
/*  579 */         selection.setSchedule(ScheduleManager.getInstance().filterSchedule(schedule, sessionFilter));
/*      */       }
/*      */ 
/*      */       
/*  583 */       sessionFilter = (String)context.getSessionValue("deptFilter");
/*  584 */       if (sessionFilter != null && sessionFilter.length() > 0)
/*      */       {
/*  586 */         selection.setSchedule(ScheduleManager.getInstance().deptFilterSchedule(selection.getSchedule(), sessionFilter, context));
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  591 */       int sortType = -1;
/*  592 */       if (context.getSessionValue("ScheduleTaskSort") != null) {
/*  593 */         sortType = Integer.parseInt((String)context.getSessionValue("ScheduleTaskSort"));
/*      */       }
/*      */ 
/*      */       
/*  597 */       if (sortType == -1 || context.getParameter("OrderTasksBy") == null) {
/*      */         
/*  599 */         if (selection.getIsDigital()) {
/*      */ 
/*      */ 
/*      */           
/*  603 */           switch (user.getPreferences().getSchedulePhysicalSortBy()) {
/*      */             
/*      */             case 1:
/*  606 */               sortType = 0;
/*      */               break;
/*      */             case 2:
/*  609 */               sortType = 1;
/*      */               break;
/*      */             case 3:
/*  612 */               sortType = 2;
/*      */               break;
/*      */             case 4:
/*  615 */               sortType = 3;
/*      */               break;
/*      */             case 5:
/*  618 */               sortType = 4;
/*      */               break;
/*      */             case 6:
/*  621 */               sortType = 5;
/*      */               break;
/*      */           } 
/*      */ 
/*      */         
/*      */         } else {
/*  627 */           switch (user.getPreferences().getSchedulePhysicalSortBy()) {
/*      */             
/*      */             case 1:
/*  630 */               sortType = 0;
/*      */               break;
/*      */             case 2:
/*  633 */               sortType = 1;
/*      */               break;
/*      */             case 3:
/*  636 */               sortType = 2;
/*      */               break;
/*      */             case 4:
/*  639 */               sortType = 3;
/*      */               break;
/*      */             case 5:
/*  642 */               sortType = 4;
/*      */               break;
/*      */             case 6:
/*  645 */               sortType = 5;
/*      */               break;
/*      */           } 
/*      */         
/*      */         } 
/*  650 */         context.putDelivery("ScheduleTaskSort", Integer.toString(sortType));
/*  651 */         context.putSessionValue("ScheduleTaskSort", Integer.toString(sortType));
/*      */       } 
/*      */       
/*  654 */       if (schedule != null && schedule.getTasks() != null) {
/*  655 */         schedule.setTasks(sortScreenScheduledTaskVector(sortType, schedule.getTasks()));
/*      */       }
/*      */       
/*  658 */       selection.setSchedule(schedule);
/*      */ 
/*      */       
/*  661 */       Form form = buildForm(context, selection, schedule, command);
/*  662 */       form.addElement(new FormHidden("cmd", "schedule-editor", true));
/*  663 */       form.addElement(new FormHidden("OrderTasksBy", "", true));
/*  664 */       context.putDelivery("Form", form);
/*      */       
/*  666 */       context.putSessionValue("Selection", selection);
/*      */       
/*  668 */       if (schedule != null) {
/*  669 */         context.putSessionValue("Schedule", schedule);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  676 */       if (selection != null && (schedule == null || (schedule.getTasks().size() == 0 && isNewSchedule(schedule)))) {
/*      */ 
/*      */ 
/*      */         
/*  680 */         suggestedTemplate(form, selection, context, command);
/*  681 */         return true;
/*      */       } 
/*      */ 
/*      */       
/*  685 */       recalcDate = (String)context.getDelivery("recalc-date");
/*  686 */       if (recalcDate != null && recalcDate.equalsIgnoreCase("true"))
/*      */       {
/*  688 */         context.putDelivery("recalc-date", "true");
/*      */       }
/*      */ 
/*      */       
/*  692 */       context.putDelivery("templateName", SelectionManager.getInstance().getAssignedTemplateName(selection));
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  697 */       return context.includeJSP("schedule-editor.jsp");
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  702 */     context.putDelivery("isNotePadVisible", new Boolean(MilestoneHelper.getNotePadVisiblitiy(0, context)));
/*      */     
/*  704 */     Form form = new Form(this.application, "form", this.application.getInfrastructure().getServletURL(), "Post");
/*  705 */     form.addElement(new FormHidden("cmd", "schedule-editor"));
/*  706 */     form.addElement(new FormHidden("OrderBy", "", true));
/*      */     
/*  708 */     notepad.setSwitchToTaskVisible(false);
/*      */ 
/*      */     
/*  711 */     addSelectionSearchElements(context, selection, form);
/*      */     
/*  713 */     context.putDelivery("Form", form);
/*      */     
/*  715 */     return context.includeJSP("blank-schedule-editor.jsp");
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
/*      */   private boolean selectionSearch(Dispatcher dispatcher, Context context, String command) {
/*  727 */     String isSelectionSearchResults = context.getParameter("isSelectionSearchResults");
/*  728 */     if (isSelectionSearchResults == null || !isSelectionSearchResults.equals("true")) {
/*      */ 
/*      */       
/*  731 */       Notepad notepad = MilestoneHelper.getNotepadFromSession(1, 
/*  732 */           context);
/*      */ 
/*      */       
/*  735 */       notepad.setAllContents(null);
/*  736 */       notepad.setSelected(null);
/*      */ 
/*      */       
/*  739 */       notepad.setMaxRecords(225);
/*      */ 
/*      */       
/*  742 */       Form form = new Form(this.application, "selectionForm", 
/*  743 */           this.application.getInfrastructure().getServletURL(), 
/*  744 */           "POST");
/*  745 */       addSelectionSearchElements(context, null, form);
/*  746 */       form.setValues(context);
/*      */       
/*  748 */       SelectionManager.getInstance().setSelectionNotepadQuery(context, (
/*  749 */           (User)context.getSessionValue("user")).getUserId(), notepad, form);
/*      */     } 
/*  751 */     dispatcher.redispatch(context, "schedule-editor");
/*      */     
/*  753 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Form buildForm(Context context, Selection selection, Schedule schedule, String command) {
/*      */     String filterProdType;
/*  761 */     Calendar testDate = Calendar.getInstance();
/*      */     
/*  763 */     User sessionUser = (User)context.getSessionValue("user");
/*      */     
/*  765 */     Form form = new Form(this.application, "ScheduleForm", 
/*  766 */         this.application.getInfrastructure().getServletURL(), "POST");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  772 */     form = addSelectionSearchElements(context, selection, form);
/*      */     
/*  774 */     int secureLevel = getSchedulePermissions(selection, sessionUser);
/*  775 */     setButtonVisibilities(selection, sessionUser, context, secureLevel, command);
/*      */ 
/*      */ 
/*      */     
/*  779 */     String selDepartment = "All";
/*  780 */     if ((String)context.getSessionValue("deptFilter") != null) {
/*  781 */       selDepartment = (String)context.getSessionValue("deptFilter");
/*      */     }
/*  783 */     FormDropDownMenu deptFilterDD = getDepartmentFilterDropDown("deptFilter", selDepartment, false, schedule, context);
/*  784 */     String[] values = deptFilterDD.getValueList();
/*  785 */     String[] menuText = deptFilterDD.getMenuTextList();
/*      */     
/*  787 */     values[0] = "All";
/*  788 */     menuText[0] = "All";
/*  789 */     deptFilterDD.setValueList(values);
/*  790 */     deptFilterDD.setMenuTextList(menuText);
/*  791 */     deptFilterDD.setTabIndex(1);
/*  792 */     deptFilterDD.addFormEvent("onChange", "Javascript:clickDeptFilter(this)");
/*  793 */     form.addElement(deptFilterDD);
/*      */ 
/*      */ 
/*      */     
/*  797 */     String filterString = "All";
/*  798 */     if ((String)context.getSessionValue("filter") != null) {
/*  799 */       filterString = (String)context.getSessionValue("filter");
/*      */     }
/*  801 */     if (selection != null && selection.getIsDigital()) {
/*  802 */       filterProdType = "All,Only Label Tasks,Only eCommerce Tasks";
/*      */     } else {
/*  804 */       filterProdType = "All,Only Label Tasks,Only UML Tasks";
/*  805 */     }  FormDropDownMenu filterDropdown = new FormDropDownMenu("filter", filterString, filterProdType, true);
/*      */ 
/*      */     
/*  808 */     String prefixId = "";
/*  809 */     String selectionNo = "";
/*      */     
/*  811 */     if (selection != null) {
/*      */       
/*  813 */       if (SelectionManager.getLookupObjectValue(selection.getPrefixID()) != null) {
/*  814 */         prefixId = SelectionManager.getLookupObjectValue(selection.getPrefixID());
/*      */       }
/*  816 */       selectionNo = String.valueOf(selection.getSelectionNo());
/*  817 */       prefixId = String.valueOf(prefixId) + selectionNo;
/*      */     } 
/*      */     
/*  820 */     FormTextField prefix = new FormTextField("prefixId", prefixId, false, 13, 10);
/*      */ 
/*      */     
/*  823 */     filterDropdown.addFormEvent("onChange", "Javascript:clickCurrentFilter(this)");
/*  824 */     form.addElement(filterDropdown);
/*      */     
/*  826 */     Vector scheduleRights = new Vector();
/*      */     
/*  828 */     if (selection != null && schedule != null) {
/*      */ 
/*      */ 
/*      */       
/*  832 */       Hashtable familyAclHash = sessionUser.getAcl().getFamilyAccessHash();
/*      */       
/*  834 */       Vector scheduledTasks = schedule.getTasks();
/*      */       
/*  836 */       for (int i = 0; i < scheduledTasks.size(); i++) {
/*      */         
/*  838 */         ScheduledTask scheduledTask = (ScheduledTask)scheduledTasks.get(i);
/*      */         
/*  840 */         FormTextField duedate = new FormTextField("DueDate" + i, "", false, 10);
/*      */         
/*  842 */         duedate.setLength(7);
/*      */         
/*  844 */         FormTextField wksToRelease = new FormTextField("wksToRelease" + i, false, 5);
/*  845 */         wksToRelease.setLength(4);
/*      */ 
/*      */         
/*  848 */         wksToRelease.addFormEvent("onBlur", "Javascript:validateWks2Rel(this," + (
/*  849 */             new Integer(scheduledTask.getTaskWeeksToRelease())).toString() + "," + (
/*  850 */             new Integer(-10)).toString() + ")");
/*      */ 
/*      */ 
/*      */         
/*  854 */         String wksToReleaseString = "";
/*      */ 
/*      */         
/*  857 */         if (scheduledTask.getWeeksToRelease() >= 0) {
/*      */           
/*  859 */           wksToReleaseString = (scheduledTask.getDayOfTheWeek() != null) ? scheduledTask.getDayOfTheWeek().getDay() : "";
/*  860 */           wksToReleaseString = String.valueOf(wksToReleaseString) + " " + scheduledTask.getWeeksToRelease();
/*  861 */           wksToRelease.setValue(wksToReleaseString);
/*      */           
/*  863 */           wksToRelease.setStartingValue(wksToRelease.getStringValue());
/*      */         }
/*  865 */         else if (scheduledTask.getWeeksToRelease() == -10) {
/*      */           
/*  867 */           wksToRelease.setValue("SOL");
/*      */           
/*  869 */           wksToRelease.setStartingValue(wksToRelease.getStringValue());
/*      */         } 
/*      */ 
/*      */         
/*  873 */         FormDropDownMenu status = new FormDropDownMenu("status" + i);
/*  874 */         status.setValue(scheduledTask.getScheduledTaskStatus());
/*      */         
/*  876 */         status.setStartingValue(status.getStringValue());
/*      */         
/*  878 */         if (scheduledTask.getScheduledTaskStatus().equals("Auto")) {
/*      */           
/*  880 */           status.setValueList("&nbsp;,Auto,Done,Hold,N/A,Pend,Today");
/*  881 */           status.setMenuTextList("&nbsp;,Auto,Done,Hold,N/A,Pend,Today");
/*      */         } else {
/*      */           
/*  884 */           status.setValueList("&nbsp;,Done,Hold,N/A,Pend,Today");
/*  885 */           status.setMenuTextList("&nbsp;,Done,Hold,N/A,Pend,Today");
/*      */         } 
/*      */ 
/*      */         
/*  889 */         FormTextField vendor = new FormTextField("vendor" + i, false, 50);
/*  890 */         vendor.setValue(scheduledTask.getVendor());
/*      */         
/*  892 */         vendor.setStartingValue(vendor.getStringValue());
/*  893 */         vendor.setLength(8);
/*  894 */         vendor.addFormEvent("onDblClick", "showDetailData( this, 'vendorLayer', 'vendorText' )");
/*      */ 
/*      */         
/*  897 */         FormHidden comments = new FormHidden("comments" + i);
/*  898 */         if (scheduledTask.getComments() != null && !scheduledTask.getComments().equalsIgnoreCase("[none]") && !scheduledTask.getComments().equalsIgnoreCase("null") && 
/*  899 */           scheduledTask.getComments().length() > 0) {
/*      */           
/*  901 */           comments.setValue(scheduledTask.getComments());
/*      */         }
/*      */         else {
/*      */           
/*  905 */           comments.setValue("");
/*      */         } 
/*      */ 
/*      */         
/*  909 */         if (scheduledTask.getDueDate() != null) {
/*      */           
/*  911 */           duedate.setValue(MilestoneHelper.getFormatedDate(scheduledTask.getDueDate()));
/*      */           
/*  913 */           duedate.setStartingValue(duedate.getStringValue());
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  918 */         duedate.addFormEvent("onBlur", "JavaScript:validateDate(this);");
/*      */ 
/*      */         
/*  921 */         if (scheduledTask.getCompletionDate() == null && (
/*  922 */           scheduledTask.getDueDate() == null || (
/*  923 */           scheduledTask.getDueDate() != null && Calendar.getInstance().after(scheduledTask.getDueDate()) && 
/*  924 */           !scheduledTask.getScheduledTaskStatus().equalsIgnoreCase("Done") && 
/*  925 */           !scheduledTask.getScheduledTaskStatus().equalsIgnoreCase("N/A") && 
/*  926 */           !scheduledTask.getScheduledTaskStatus().equalsIgnoreCase("Today"))))
/*      */         {
/*  928 */           duedate.addFormEvent("style", "background-color: mistyrose");
/*      */         }
/*  930 */         duedate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
/*      */ 
/*      */ 
/*      */         
/*  934 */         Day dayType = null;
/*  935 */         FormHidden dayTypeElement = new FormHidden("dayType" + i);
/*  936 */         dayTypeElement.setValue(MilestoneHelper.getDayType(selection.getCalendarGroup(), scheduledTask));
/*      */         
/*  938 */         dayTypeElement.setStartingValue(dayTypeElement.getStringValue());
/*      */ 
/*      */         
/*  941 */         FormTextField complete = new FormTextField("completion" + i, "", false, 10);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  946 */         if (scheduledTask.getCompletionDate() == null && 
/*  947 */           scheduledTask.getScheduledTaskStatus().equalsIgnoreCase("today")) {
/*      */           
/*  949 */           scheduledTask.setCompletionDate(Calendar.getInstance());
/*      */         }
/*  951 */         else if ((scheduledTask.getCompletionDate() == null && 
/*  952 */           scheduledTask.getScheduledTaskStatus().equalsIgnoreCase("Done") && 
/*  953 */           !scheduledTask.getAllowMultCompleteDatesFlag() && 
/*  954 */           !MilestoneHelper.isUml(scheduledTask)) || 
/*  955 */           scheduledTask.getScheduledTaskStatus().equalsIgnoreCase("N/A")) {
/*      */           
/*  957 */           scheduledTask.setCompletionDate(MilestoneHelper.getDate("9/9/99"));
/*      */         } 
/*      */         
/*  960 */         if (scheduledTask.getCompletionDate() != null) {
/*      */           
/*  962 */           complete.setValue(MilestoneHelper.getFormatedDate(scheduledTask.getCompletionDate()));
/*      */           
/*  964 */           complete.setStartingValue(complete.getStringValue());
/*      */         } 
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
/*  986 */         complete.setLength(7);
/*      */ 
/*      */         
/*  989 */         if (scheduledTask.getAllowMultCompleteDatesFlag() && scheduledTask.getMultCompleteDates() != null && scheduledTask.getMultCompleteDates().size() > 0) {
/*      */           
/*  991 */           complete.setReadOnly(true);
/*      */         }
/*      */         else {
/*      */           
/*  995 */           complete.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
/*      */           
/*  997 */           complete.addFormEvent("onBlur", "JavaScript:validateDate(this);");
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1002 */         if (scheduledTask.getCompletionDate() != null && 
/* 1003 */           !scheduledTask.getCompletionDate().equals(MilestoneHelper.getDate("9/9/99")) && 
/* 1004 */           scheduledTask.getDueDate() != null && scheduledTask.getCompletionDate().after(scheduledTask.getDueDate()))
/*      */         {
/* 1006 */           complete.addFormEvent("style", "background-color: mistyrose");
/*      */         }
/*      */ 
/*      */         
/* 1010 */         form.addElement(duedate);
/* 1011 */         form.addElement(wksToRelease);
/* 1012 */         form.addElement(complete);
/* 1013 */         form.addElement(status);
/* 1014 */         form.addElement(vendor);
/* 1015 */         form.addElement(comments);
/* 1016 */         form.addElement(dayTypeElement);
/*      */ 
/*      */         
/* 1019 */         int access = 0;
/* 1020 */         if (scheduledTask.getOwner() != null) {
/*      */ 
/*      */ 
/*      */           
/* 1024 */           access = ScheduleManager.getInstance().getTaskEditAccess(sessionUser, 
/* 1025 */               scheduledTask.getOwner().getStructureID(), 
/* 1026 */               familyAclHash);
/* 1027 */           scheduleRights.add(new String(Integer.toString(access)));
/*      */         }
/*      */         else {
/*      */           
/* 1031 */           scheduleRights.add("0");
/*      */         } 
/*      */         
/* 1034 */         scheduledTask = null;
/* 1035 */         status = null;
/* 1036 */         vendor = null;
/* 1037 */         comments = null;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1042 */     context.putDelivery("scheduleRights", scheduleRights);
/*      */     
/* 1044 */     if (selection != null) {
/*      */       
/* 1046 */       context.putDelivery("releaseWeek", MilestoneHelper.getReleaseWeekString(selection));
/* 1047 */       ProductCategory productCategory = selection.getProductCategory();
/*      */       
/* 1049 */       String productCategoryString = "";
/*      */       
/* 1051 */       if (productCategory != null) {
/* 1052 */         productCategoryString = productCategory.getName();
/*      */       }
/* 1054 */       String typeConfig = String.valueOf(productCategoryString) + " / " + selection.getReleaseType().getName() + " / " + selection.getSelectionConfig().getSelectionConfigurationName() + " / " + selection.getSelectionSubConfig().getSelectionSubConfigurationName();
/* 1055 */       context.putDelivery("typeConfig", typeConfig);
/* 1056 */       context.putDelivery("selection", selection);
/*      */       
/* 1058 */       selection.setSchedule(schedule);
/*      */     } 
/*      */ 
/*      */     
/* 1062 */     form.addElement(new FormHidden("OrderBy", "", true));
/* 1063 */     form.addElement(new FormHidden("OrderTasksBy", "", true));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1068 */     if (selection != null) {
/* 1069 */       String lastSchedUpdatedDateText = "";
/* 1070 */       if (selection.getLastSchedUpdateDate() != null)
/* 1071 */         lastSchedUpdatedDateText = MilestoneHelper.getCustomFormatedDate(selection.getLastSchedUpdateDate(), "M/d/yyyy hh:mm:ss a 'ET'"); 
/* 1072 */       FormTextField lastSchedUpdatedDate = new FormTextField("lastSchedUpdatedDate", lastSchedUpdatedDateText, false, 50);
/* 1073 */       form.addElement(lastSchedUpdatedDate);
/* 1074 */       String lastUpdateUser = "";
/* 1075 */       if (selection.getLastSchedUpdatingUser() != null)
/* 1076 */         lastUpdateUser = selection.getLastSchedUpdatingUser().getName(); 
/* 1077 */       context.putDelivery("lastSchedUpdateUser", lastUpdateUser);
/*      */ 
/*      */       
/* 1080 */       String autoCloseDateText = "";
/* 1081 */       if (selection.getAutoCloseDate() != null)
/* 1082 */         autoCloseDateText = MilestoneHelper.getCustomFormatedDate(selection.getAutoCloseDate(), "M/d/yyyy hh:mm:ss a 'ET'"); 
/* 1083 */       FormTextField autoCloseDate = new FormTextField("autoCloseDate", autoCloseDateText, false, 40);
/* 1084 */       form.addElement(autoCloseDate);
/*      */ 
/*      */       
/* 1087 */       FormTextField lastLegacyUpdateDate = new FormTextField("lastLegacyUpdateDate", false, 50);
/* 1088 */       lastLegacyUpdateDate.setValue(MilestoneHelper.getCustomFormatedDate(selection.getLastLegacyUpdateDate(), "M/d/yyyy hh:mm:ss a 'ET'"));
/* 1089 */       form.addElement(lastLegacyUpdateDate);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1096 */     context.putDelivery("templateName", SelectionManager.getInstance().getAssignedTemplateName(selection));
/*      */ 
/*      */ 
/*      */     
/* 1100 */     if (context.getSessionValue("NOTEPAD_SCHEDULE_VISIBLE") != null) {
/* 1101 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_SCHEDULE_VISIBLE"));
/*      */     }
/* 1103 */     return form;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Form buildFormWithTasks(Context context, Selection selection, Schedule schedule, Notepad notepadTasks, String command) {
/*      */     String filterProdType;
/* 1111 */     User sessionUser = (User)context.getSessionValue("user");
/*      */     
/* 1113 */     int secureLevel = getSchedulePermissions(selection, sessionUser);
/* 1114 */     setButtonVisibilities(selection, sessionUser, context, secureLevel, command);
/*      */     
/* 1116 */     Form form = new Form(this.application, "ScheduleForm", 
/* 1117 */         this.application.getInfrastructure().getServletURL(), "POST");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1122 */     String selDepartment = "All";
/* 1123 */     if ((String)context.getSessionValue("deptFilter") != null) {
/* 1124 */       selDepartment = (String)context.getSessionValue("deptFilter");
/*      */     }
/* 1126 */     FormDropDownMenu deptFilterDD = getDepartmentFilterDropDown("deptFilter", selDepartment, false, schedule, context);
/* 1127 */     String[] values = deptFilterDD.getValueList();
/* 1128 */     String[] menuText = deptFilterDD.getMenuTextList();
/*      */     
/* 1130 */     values[0] = "All";
/* 1131 */     menuText[0] = "All";
/* 1132 */     deptFilterDD.setValueList(values);
/* 1133 */     deptFilterDD.setMenuTextList(menuText);
/* 1134 */     deptFilterDD.setTabIndex(1);
/* 1135 */     deptFilterDD.addFormEvent("onChange", "Javascript:clickDeptFilter(this)");
/* 1136 */     form.addElement(deptFilterDD);
/*      */ 
/*      */ 
/*      */     
/* 1140 */     String filterString = "All";
/* 1141 */     if ((String)context.getSessionValue("filter") != null) {
/* 1142 */       filterString = (String)context.getSessionValue("filter");
/*      */     }
/* 1144 */     if (selection != null && selection.getIsDigital()) {
/* 1145 */       filterProdType = "All,Only Label Tasks,Only eCommerce Tasks";
/*      */     } else {
/* 1147 */       filterProdType = "All,Only Label Tasks,Only UML Tasks";
/* 1148 */     }  FormDropDownMenu filterDropdown = new FormDropDownMenu("filter", filterString, filterProdType, true);
/*      */ 
/*      */     
/* 1151 */     filterDropdown.addFormEvent("onChange", "Javascript:clickCurrentFilter(this)");
/* 1152 */     form.addElement(filterDropdown);
/* 1153 */     form.addElement(new FormHidden("OrderBy", "", true));
/* 1154 */     form.addElement(new FormHidden("OrderTasksBy", "", true));
/*      */     
/* 1156 */     Vector scheduleRights = new Vector();
/*      */     
/* 1158 */     if (selection != null && schedule != null) {
/*      */ 
/*      */       
/* 1161 */       Hashtable familyAclHash = sessionUser.getAcl().getFamilyAccessHash();
/*      */       
/* 1163 */       Vector scheduledTasks = schedule.getTasks();
/* 1164 */       for (int i = 0; i < scheduledTasks.size(); i++) {
/*      */         
/* 1166 */         ScheduledTask scheduledTask = (ScheduledTask)scheduledTasks.get(i);
/*      */         
/* 1168 */         FormTextField duedate = new FormTextField("DueDate" + i, "", false, 10);
/* 1169 */         duedate.setLength(7);
/*      */         
/* 1171 */         FormTextField wksToRelease = new FormTextField("wksToRelease" + i, false, 5);
/*      */ 
/*      */ 
/*      */         
/* 1175 */         wksToRelease.addFormEvent("onBlur", "Javascript:validateWks2Rel(this," + (
/* 1176 */             new Integer(scheduledTask.getTaskWeeksToRelease())).toString() + "," + (
/* 1177 */             new Integer(-10)).toString() + ")");
/*      */ 
/*      */ 
/*      */         
/* 1181 */         wksToRelease.setLength(4);
/*      */         
/* 1183 */         String wksToReleaseString = "";
/*      */ 
/*      */         
/* 1186 */         if (scheduledTask.getWeeksToRelease() >= 0) {
/*      */           
/* 1188 */           wksToReleaseString = (scheduledTask.getDayOfTheWeek() != null) ? scheduledTask.getDayOfTheWeek().getDay() : "";
/* 1189 */           wksToReleaseString = String.valueOf(wksToReleaseString) + " " + scheduledTask.getWeeksToRelease();
/* 1190 */           wksToRelease.setValue(wksToReleaseString);
/*      */           
/* 1192 */           wksToRelease.setStartingValue(wksToRelease.getStringValue());
/*      */         }
/* 1194 */         else if (scheduledTask.getWeeksToRelease() == -10) {
/*      */           
/* 1196 */           wksToRelease.setValue("SOL");
/*      */           
/* 1198 */           wksToRelease.setStartingValue(wksToRelease.getStringValue());
/*      */         } 
/*      */ 
/*      */         
/* 1202 */         FormDropDownMenu status = new FormDropDownMenu("status" + i);
/* 1203 */         status.setValue(scheduledTask.getScheduledTaskStatus());
/*      */         
/* 1205 */         status.setStartingValue(status.getStringValue());
/*      */         
/* 1207 */         if (scheduledTask.getScheduledTaskStatus().equals("Auto")) {
/*      */           
/* 1209 */           status.setValueList("&nbsp;,Auto,Done,Hold,N/A,Pend,Today");
/* 1210 */           status.setMenuTextList("&nbsp;,Auto,Done,Hold,N/A,Pend,Today");
/*      */         } else {
/*      */           
/* 1213 */           status.setValueList("&nbsp;,Done,Hold,N/A,Pend,Today");
/* 1214 */           status.setMenuTextList("&nbsp;,Done,Hold,N/A,Pend,Today");
/*      */         } 
/*      */ 
/*      */         
/* 1218 */         FormTextField vendor = new FormTextField("vendor" + i, false, 50);
/* 1219 */         vendor.setValue(scheduledTask.getVendor());
/*      */         
/* 1221 */         vendor.setStartingValue(vendor.getStringValue());
/* 1222 */         vendor.setLength(7);
/* 1223 */         vendor.addFormEvent("onDblClick", "showDetailData( this, 'vendorLayer', 'vendorText' )");
/*      */ 
/*      */         
/* 1226 */         FormHidden comments = new FormHidden("comments" + i);
/* 1227 */         if (scheduledTask.getComments() != null && !scheduledTask.getComments().equalsIgnoreCase("[none]") && !scheduledTask.getComments().equalsIgnoreCase("null") && 
/* 1228 */           scheduledTask.getComments().length() > 0) {
/*      */           
/* 1230 */           comments.setValue(scheduledTask.getComments());
/*      */         }
/*      */         else {
/*      */           
/* 1234 */           comments.setValue("");
/*      */         } 
/*      */ 
/*      */         
/* 1238 */         if (scheduledTask.getDueDate() != null) {
/*      */           
/* 1240 */           duedate.setValue(MilestoneHelper.getFormatedDate(scheduledTask.getDueDate()));
/*      */           
/* 1242 */           duedate.setStartingValue(duedate.getStringValue());
/*      */         } 
/*      */         
/* 1245 */         duedate.addFormEvent("onBlur", "JavaScript:validateDate(this);");
/*      */ 
/*      */         
/* 1248 */         if (scheduledTask.getCompletionDate() == null && (
/* 1249 */           scheduledTask.getDueDate() == null || (
/* 1250 */           scheduledTask.getDueDate() != null && Calendar.getInstance().after(scheduledTask.getDueDate()) && 
/* 1251 */           !scheduledTask.getScheduledTaskStatus().equalsIgnoreCase("Done") && 
/* 1252 */           !scheduledTask.getScheduledTaskStatus().equalsIgnoreCase("N/A") && 
/* 1253 */           !scheduledTask.getScheduledTaskStatus().equalsIgnoreCase("Today"))))
/*      */         {
/* 1255 */           duedate.addFormEvent("style", "background-color: mistyrose");
/*      */         }
/* 1257 */         duedate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
/*      */         
/* 1259 */         duedate.addFormEvent("onBlur", "JavaScript:validateDate(this);");
/*      */ 
/*      */ 
/*      */         
/* 1263 */         Day dayType = null;
/* 1264 */         FormHidden dayTypeElement = new FormHidden("dayType" + i);
/* 1265 */         dayTypeElement.setValue(MilestoneHelper.getDayType(selection.getCalendarGroup(), scheduledTask));
/*      */         
/* 1267 */         dayTypeElement.setStartingValue(dayTypeElement.getStringValue());
/*      */         
/* 1269 */         FormTextField complete = new FormTextField("completion" + i, "", false, 10);
/* 1270 */         if (scheduledTask.getCompletionDate() != null) {
/*      */           
/* 1272 */           complete.setValue(MilestoneHelper.getFormatedDate(scheduledTask.getCompletionDate()));
/*      */           
/* 1274 */           complete.setStartingValue(complete.getStringValue());
/*      */         } 
/* 1276 */         complete.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
/*      */         
/* 1278 */         complete.addFormEvent("onBlur", "JavaScript:validateDate(this);");
/* 1279 */         complete.setLength(7);
/*      */ 
/*      */         
/* 1282 */         if (scheduledTask.getAllowMultCompleteDatesFlag() && 
/* 1283 */           scheduledTask.getMultCompleteDates() != null && 
/* 1284 */           scheduledTask.getMultCompleteDates().size() > 0) {
/* 1285 */           complete.setReadOnly(true);
/*      */         } else {
/*      */           
/* 1288 */           complete.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
/*      */           
/* 1290 */           complete.addFormEvent("onBlur", "JavaScript:validateDate(this);");
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1296 */         if (scheduledTask.getCompletionDate() == null && 
/* 1297 */           scheduledTask.getScheduledTaskStatus().equalsIgnoreCase("Done") && 
/* 1298 */           !scheduledTask.getAllowMultCompleteDatesFlag() && 
/* 1299 */           !MilestoneHelper.isUml(scheduledTask))
/*      */         {
/* 1301 */           scheduledTask.setCompletionDate(MilestoneHelper.getDate("9/9/99"));
/*      */         }
/*      */ 
/*      */         
/* 1305 */         int access = 0;
/* 1306 */         if (scheduledTask.getCompletionDate() != null && 
/* 1307 */           !scheduledTask.getCompletionDate().equals(MilestoneHelper.getDate("9/9/99")) && 
/* 1308 */           scheduledTask.getDueDate() != null && scheduledTask.getCompletionDate().after(scheduledTask.getDueDate()))
/*      */         {
/* 1310 */           complete.addFormEvent("style", "background-color: mistyrose");
/*      */         }
/*      */ 
/*      */         
/* 1314 */         form.addElement(duedate);
/* 1315 */         form.addElement(wksToRelease);
/* 1316 */         form.addElement(complete);
/* 1317 */         form.addElement(status);
/* 1318 */         form.addElement(vendor);
/* 1319 */         form.addElement(comments);
/* 1320 */         form.addElement(dayTypeElement);
/*      */ 
/*      */         
/* 1323 */         if (scheduledTask.getOwner() != null) {
/*      */ 
/*      */ 
/*      */           
/* 1327 */           access = ScheduleManager.getInstance().getTaskEditAccess(sessionUser, 
/* 1328 */               scheduledTask.getOwner().getStructureID(), 
/* 1329 */               familyAclHash);
/*      */           
/* 1331 */           scheduleRights.add(new String(Integer.toString(access)));
/*      */         }
/*      */         else {
/*      */           
/* 1335 */           scheduleRights.add("0");
/*      */         } 
/*      */         
/* 1338 */         scheduledTask = null;
/* 1339 */         status = null;
/* 1340 */         vendor = null;
/* 1341 */         comments = null;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1346 */     context.putDelivery("scheduleRights", scheduleRights);
/*      */     
/* 1348 */     if (selection != null) {
/*      */       
/* 1350 */       context.putDelivery("releaseWeek", MilestoneHelper.getReleaseWeekString(selection));
/* 1351 */       ProductCategory productCategory = selection.getProductCategory();
/* 1352 */       String productCategoryString = "";
/* 1353 */       if (productCategory != null) {
/* 1354 */         productCategoryString = productCategory.getName();
/*      */       }
/* 1356 */       String typeConfig = String.valueOf(productCategoryString) + " / " + selection.getReleaseType().getName() + " / " + selection.getSelectionConfig().getSelectionConfigurationName() + " / " + selection.getSelectionSubConfig().getSelectionSubConfigurationName();
/* 1357 */       context.putDelivery("typeConfig", typeConfig);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1363 */     Vector notepadPageContents = notepadTasks.getPageContents();
/* 1364 */     Task task = null;
/* 1365 */     FormCheckBox notepadCheckbox = null;
/*      */     
/* 1367 */     for (int j = 0; j < notepadPageContents.size(); j++) {
/*      */       
/* 1369 */       task = (Task)notepadPageContents.get(j);
/* 1370 */       notepadCheckbox = new FormCheckBox(String.valueOf(task.getTaskID()), "", false, false);
/* 1371 */       form.addElement(notepadCheckbox);
/* 1372 */       task = null;
/* 1373 */       notepadCheckbox = null;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1379 */     addTaskSearchElements(context, selection, form);
/* 1380 */     addSelectionSearchElements(context, selection, form);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1385 */     if (selection != null) {
/* 1386 */       String lastSchedUpdatedDateText = "";
/* 1387 */       if (selection.getLastSchedUpdateDate() != null)
/* 1388 */         lastSchedUpdatedDateText = MilestoneHelper.getCustomFormatedDate(selection.getLastSchedUpdateDate(), "M/d/yyyy hh:mm:ss a 'ET'"); 
/* 1389 */       FormTextField lastSchedUpdatedDate = new FormTextField("lastSchedUpdatedDate", lastSchedUpdatedDateText, false, 50);
/* 1390 */       form.addElement(lastSchedUpdatedDate);
/* 1391 */       String lastUpdateUser = "";
/* 1392 */       if (selection.getLastSchedUpdatingUser() != null)
/* 1393 */         lastUpdateUser = selection.getLastSchedUpdatingUser().getName(); 
/* 1394 */       context.putDelivery("lastSchedUpdateUser", lastUpdateUser);
/*      */ 
/*      */       
/* 1397 */       String autoCloseDateText = "";
/* 1398 */       if (selection.getAutoCloseDate() != null)
/* 1399 */         autoCloseDateText = MilestoneHelper.getCustomFormatedDate(selection.getAutoCloseDate(), "M/d/yyyy hh:mm:ss a 'ET'"); 
/* 1400 */       FormTextField autoCloseDate = new FormTextField("autoCloseDate", autoCloseDateText, false, 40);
/* 1401 */       form.addElement(autoCloseDate);
/*      */ 
/*      */       
/* 1404 */       FormTextField lastLegacyUpdateDate = new FormTextField("lastLegacyUpdateDate", false, 50);
/* 1405 */       lastLegacyUpdateDate.setValue(MilestoneHelper.getCustomFormatedDate(selection.getLastLegacyUpdateDate(), "M/d/yyyy hh:mm:ss a 'ET'"));
/* 1406 */       form.addElement(lastLegacyUpdateDate);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1413 */     form.addElement(new FormHidden("OrderTasksBy", "", true));
/*      */ 
/*      */ 
/*      */     
/* 1417 */     context.putDelivery("templateName", SelectionManager.getInstance().getAssignedTemplateName(selection));
/*      */ 
/*      */     
/* 1420 */     if (context.getSessionValue("NOTEPAD_SCHEDULE_TASKS_VISIBLE") != null) {
/* 1421 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_SCHEDULE_TASKS_VISIBLE"));
/*      */     }
/* 1423 */     return form;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Form addSelectionSearchElements(Context context, Selection selection, Form form) {
/* 1434 */     context.putDelivery("selectionArrays", String.valueOf(Cache.getJavaScriptConfigArray("")) + Cache.getJavaScriptSubConfigArray("") + " " + ReleasingFamily.getJavaScriptCorporateArrayReleasingFamilySearch(context));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1440 */     FormCheckBox showAllSearch = new FormCheckBox("ShowAllSearch", "", "", false);
/* 1441 */     showAllSearch.setId("ShowAllSearch");
/* 1442 */     form.addElement(showAllSearch);
/*      */ 
/*      */     
/* 1445 */     Vector companies = MilestoneHelper.getUserCompanies(context);
/* 1446 */     Vector labels = MilestoneHelper.getUserLabels(companies);
/*      */ 
/*      */     
/* 1449 */     labels = MilestoneHelper.removeUnusedCSO(labels, context, -1);
/*      */     
/* 1451 */     FormDropDownMenu labelSearch = MilestoneHelper.getCorporateStructureDropDownDuplicates("LabelSearch", labels, "", false, true, false);
/* 1452 */     labelSearch.setId("LabelSearch");
/* 1453 */     form.addElement(labelSearch);
/*      */ 
/*      */     
/* 1456 */     Vector searchCompanies = null;
/*      */ 
/*      */ 
/*      */     
/* 1460 */     searchCompanies = MilestoneHelper.getUserCompanies(context);
/*      */ 
/*      */     
/* 1463 */     searchCompanies = MilestoneHelper.removeUnusedCSO(searchCompanies, context, -1);
/*      */     
/* 1465 */     FormDropDownMenu companySearch = MilestoneHelper.getCorporateStructureDropDown("CompanySearch", searchCompanies, "", false, true);
/* 1466 */     companySearch.setId("CompanySearch");
/* 1467 */     companySearch.addFormEvent("onChange", "return(clickCompanySearch(this))");
/* 1468 */     form.addElement(companySearch);
/*      */ 
/*      */     
/* 1471 */     Vector labelContacts = SelectionManager.getLabelContactsExcludeUml(context);
/* 1472 */     FormDropDownMenu searchContact = MilestoneHelper.getContactsDropDown(context, "ContactSearch", labelContacts, null, true);
/* 1473 */     form.addElement(searchContact);
/*      */ 
/*      */     
/* 1476 */     Vector families = SelectionHandler.filterCSO(ReleasingFamily.getUserReleasingFamiliesVectorOfFamilies(context));
/* 1477 */     FormDropDownMenu Family = MilestoneHelper.getCorporateStructureDropDown("FamilySearch", families, "-1", false, true);
/* 1478 */     Family.addFormEvent("onChange", "return(clickFamilySearch(this))");
/* 1479 */     Family.setId("FamilySearch");
/* 1480 */     form.addElement(Family);
/*      */ 
/*      */     
/* 1483 */     Vector environments = MilestoneHelper.getUserEnvironments(context);
/* 1484 */     Vector myCompanies = MilestoneHelper.getUserCompanies(context);
/* 1485 */     environments = SelectionHandler.filterSelectionEnvironments(myCompanies);
/*      */ 
/*      */     
/* 1488 */     environments = MilestoneHelper.removeUnusedCSO(environments, context, -1);
/*      */ 
/*      */     
/* 1491 */     FormDropDownMenu envMenu = MilestoneHelper.getCorporateStructureDropDown("EnvironmentSearch", environments, "-1", false, true);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1496 */     envMenu.addFormEvent("onChange", "return(clickEnvironmentSearch(this))");
/* 1497 */     envMenu.setId("EnvironmentSearch");
/* 1498 */     form.addElement(envMenu);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1503 */     FormTextField streetDateSearch = new FormTextField("StreetDateSearch", "", false, 14, 10);
/* 1504 */     streetDateSearch.addFormEvent("onBlur", "JavaScript:removeSpacesInDate(document.forms[0].StreetDateSearch.value,this)");
/* 1505 */     streetDateSearch.setId("StreetDateSearch");
/* 1506 */     form.addElement(streetDateSearch);
/*      */ 
/*      */     
/* 1509 */     FormTextField streetEndDateSearch = new FormTextField("StreetEndDateSearch", "", false, 14, 10);
/* 1510 */     streetEndDateSearch.addFormEvent("onBlur", "JavaScript:removeSpacesInDate(document.forms[0].StreetEndDateSearch.value,this)");
/* 1511 */     streetEndDateSearch.setId("StreetEndDateSearch");
/* 1512 */     form.addElement(streetEndDateSearch);
/*      */ 
/*      */     
/* 1515 */     String[] dvalues = new String[3];
/* 1516 */     dvalues[0] = "physical";
/* 1517 */     dvalues[1] = "digital";
/* 1518 */     dvalues[2] = "both";
/*      */     
/* 1520 */     String[] dlabels = new String[3];
/* 1521 */     dlabels[0] = "Physical";
/* 1522 */     dlabels[1] = "Digital";
/* 1523 */     dlabels[2] = "Both";
/*      */     
/* 1525 */     FormRadioButtonGroup prodType = new FormRadioButtonGroup("ProdType", "both", dvalues, dlabels, false);
/* 1526 */     prodType.addFormEvent("onClick", "buildSearchConfigs(this)");
/* 1527 */     form.addElement(prodType);
/*      */ 
/*      */ 
/*      */     
/* 1531 */     Vector searchConfigs = null;
/* 1532 */     searchConfigs = Cache.getSelectionConfigs();
/* 1533 */     FormDropDownMenu configSearch = MilestoneHelper.getSelectionConfigurationDropDown("ConfigSearch", "", false);
/* 1534 */     configSearch.setId("ConfigSearch");
/* 1535 */     configSearch.addFormEvent("onChange", "buildSearchSubConfigs(this.selectedIndex)");
/* 1536 */     form.addElement(configSearch);
/*      */ 
/*      */ 
/*      */     
/* 1540 */     FormDropDownMenu subconfigSearch = new FormDropDownMenu("SubconfigSearch", "");
/* 1541 */     subconfigSearch.setId("SubconfigSearch");
/* 1542 */     subconfigSearch.setEnabled(false);
/* 1543 */     form.addElement(subconfigSearch);
/*      */ 
/*      */ 
/*      */     
/* 1547 */     FormTextField upcSearch = new FormTextField("UPCSearch", "", false, 20, 20);
/* 1548 */     upcSearch.setId("UPCSearch");
/* 1549 */     form.addElement(upcSearch);
/*      */ 
/*      */     
/* 1552 */     FormTextField prefixSearch = new FormTextField("PrefixSearch", "", false, 6, 5);
/* 1553 */     prefixSearch.setId("PrefixSearch");
/* 1554 */     form.addElement(prefixSearch);
/*      */ 
/*      */     
/* 1557 */     FormTextField selectionSearch = new FormTextField("SelectionSearch", "", false, 12, 20);
/* 1558 */     selectionSearch.setId("SelectionSearch");
/* 1559 */     selectionSearch.setClassName("ctrlMedium");
/* 1560 */     form.addElement(selectionSearch);
/*      */ 
/*      */     
/* 1563 */     FormTextField titleSearch = new FormTextField("TitleSearch", "", false, 20);
/* 1564 */     titleSearch.setId("TitleSearch");
/* 1565 */     form.addElement(titleSearch);
/*      */ 
/*      */     
/* 1568 */     FormTextField artistSearch = new FormTextField("ArtistSearch", "", false, 20);
/* 1569 */     artistSearch.setId("ArtistSearch");
/* 1570 */     form.addElement(artistSearch);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1577 */     FormTextArea Comments = new FormTextArea("comments", "", false, 6, 44, "virtual");
/* 1578 */     Comments.setId("comments");
/* 1579 */     Comments.addFormEvent("onBlur", "Javascript:checkField(this)");
/* 1580 */     form.addElement(Comments);
/*      */ 
/*      */     
/* 1583 */     FormTextArea viewComments = new FormTextArea("viewComments", "", false, 2, 44, "virtual");
/* 1584 */     viewComments.setId("viewComments");
/* 1585 */     form.addElement(viewComments);
/*      */ 
/*      */     
/* 1588 */     String releaseCommentString = "";
/*      */     
/* 1590 */     if (selection != null && selection.getSelectionComments() != null)
/* 1591 */       releaseCommentString = selection.getSelectionComments(); 
/* 1592 */     FormTextArea releaseComment = new FormTextArea("releaseComment", releaseCommentString, false, 4, 44, "virtual");
/* 1593 */     releaseComment.addFormEvent("onBlur", "Javascript:checkField(this)");
/* 1594 */     form.addElement(releaseComment);
/*      */     
/* 1596 */     String holdReasonString = "";
/* 1597 */     if (selection != null && selection.getHoldReason() != null)
/* 1598 */       holdReasonString = selection.getHoldReason(); 
/* 1599 */     FormTextArea holdReason = new FormTextArea("holdReason", holdReasonString, false, 2, 44, "virtual");
/* 1600 */     form.addElement(holdReason);
/*      */ 
/*      */ 
/*      */     
/* 1604 */     FormTextField projectIDSearch = new FormTextField("ProjectIDSearch", "", false, 20);
/* 1605 */     projectIDSearch.setId("ProjectIDSearch");
/* 1606 */     form.addElement(projectIDSearch);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1611 */     SelectionHandler.getUserPreferences(form, context);
/*      */     
/* 1613 */     return form;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addTemplateSearchElements(Context context, Selection selection, Form form) {
/* 1624 */     FormTextField templateNameSearch = new FormTextField("TemplateNameSearch", "", false, 20);
/* 1625 */     templateNameSearch.setId("TemplateNameSearch");
/* 1626 */     templateNameSearch.setTabIndex(1);
/* 1627 */     form.addElement(templateNameSearch);
/*      */ 
/*      */     
/* 1630 */     FormDropDownMenu configuration = MilestoneHelper.getSelectionConfigurationDropDown("TemplateConfigurationSearch", "", false, selection.getIsDigital() ? 1 : 0);
/* 1631 */     configuration.setTabIndex(2);
/* 1632 */     form.addElement(configuration);
/*      */ 
/*      */     
/* 1635 */     FormDropDownMenu productLine = MilestoneHelper.getLookupDropDown("TemplateProductLineSearch", Cache.getProductCategories(selection.getIsDigital() ? 1 : 0), "", false, true);
/* 1636 */     productLine.setTabIndex(3);
/* 1637 */     form.addElement(productLine);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1643 */     FormCheckBox showAllSearch = new FormCheckBox("ShowAllSearchSource", "", "", false);
/* 1644 */     showAllSearch.setId("ShowAllSearchSource");
/* 1645 */     form.addElement(showAllSearch);
/*      */ 
/*      */     
/* 1648 */     Vector companies = MilestoneHelper.getUserCompanies(context);
/* 1649 */     Vector labels = MilestoneHelper.getUserLabels(companies);
/* 1650 */     FormDropDownMenu labelSearch = MilestoneHelper.getCorporateStructureDropDown("LabelSearchSource", labels, "", false, true);
/* 1651 */     labelSearch.setId("LabelSearchSource");
/* 1652 */     form.addElement(labelSearch);
/*      */ 
/*      */ 
/*      */     
/* 1656 */     FormTextField streetDateSearch = new FormTextField("StreetDateSearchSource", "", false, 12, 10);
/* 1657 */     streetDateSearch.setId("StreetDateSearchSource");
/* 1658 */     form.addElement(streetDateSearch);
/*      */ 
/*      */     
/* 1661 */     FormTextField upcSearch = new FormTextField("UPCSearchSource", "", false, 12, 10);
/* 1662 */     upcSearch.setId("UPCSearchSource");
/* 1663 */     form.addElement(upcSearch);
/*      */ 
/*      */     
/* 1666 */     FormTextField prefixSearch = new FormTextField("PrefixSearchSource", "", false, 12, 12);
/* 1667 */     prefixSearch.setId("PrefixSearchSource");
/* 1668 */     form.addElement(prefixSearch);
/*      */ 
/*      */     
/* 1671 */     FormTextField selectionSearch = new FormTextField("SelectionSearchSource", "", false, 12, 10);
/* 1672 */     selectionSearch.setId("SelectionSearchSource");
/* 1673 */     form.addElement(selectionSearch);
/*      */ 
/*      */     
/* 1676 */     FormTextField titleSearch = new FormTextField("TitleSearchSource", "", false, 20);
/* 1677 */     titleSearch.setId("TitleSearchSource");
/* 1678 */     form.addElement(titleSearch);
/*      */ 
/*      */     
/* 1681 */     FormTextField artistSearch = new FormTextField("ArtistSearchSource", "", false, 20);
/* 1682 */     artistSearch.setId("ArtistSearchSource");
/* 1683 */     form.addElement(artistSearch);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addTaskSearchElements(Context context, Selection selection, Form form) {
/* 1692 */     User sessionUser = (User)context.getSessionValue("user");
/*      */ 
/*      */     
/* 1695 */     FormTextField TaskNameSearch = new FormTextField("TaskNameSearch", "", false, 20);
/* 1696 */     TaskNameSearch.setId("TaskNameSearch");
/* 1697 */     TaskNameSearch.setTabIndex(1);
/* 1698 */     form.addElement(TaskNameSearch);
/*      */ 
/*      */     
/* 1701 */     FormRadioButtonGroup KeyTaskSearch = new FormRadioButtonGroup("KeyTaskSearch", "", "Yes,No", false);
/* 1702 */     KeyTaskSearch.setTabIndex(2);
/* 1703 */     form.addElement(KeyTaskSearch);
/*      */ 
/*      */     
/* 1706 */     Vector families = MilestoneHelper.getNonSecureUserFamilies(context);
/* 1707 */     FormDropDownMenu TaskOwnerSearch = MilestoneHelper.getCorporateStructureDropDown("TaskOwnerSearch", families, "0", false, true);
/* 1708 */     TaskOwnerSearch.setTabIndex(3);
/* 1709 */     form.addElement(TaskOwnerSearch);
/*      */ 
/*      */     
/* 1712 */     Vector deptList = MilestoneHelper.getDepartmentList();
/* 1713 */     FormDropDownMenu TaskDepartmentSearch = MilestoneHelper.getDepartmentDropDown("TaskDepartmentSearch", "", false);
/* 1714 */     TaskDepartmentSearch.setTabIndex(4);
/* 1715 */     form.addElement(TaskDepartmentSearch);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Notepad getTaskNotepad(Context context, int userId, int releaseId) {
/* 1726 */     Vector contents = new Vector();
/*      */ 
/*      */     
/* 1729 */     if (MilestoneHelper.getNotepadFromSession(2, context) != null) {
/*      */       
/* 1731 */       Notepad notepad = MilestoneHelper.getNotepadFromSession(2, context);
/*      */ 
/*      */ 
/*      */       
/* 1735 */       if (notepad.getAllContents() == null) {
/*      */         
/* 1737 */         contents = ScheduleManager.getInstance().getScheduleTaskNotepadList(releaseId, userId, notepad, context);
/* 1738 */         notepad.setAllContents(contents);
/*      */       } 
/*      */       
/* 1741 */       return notepad;
/*      */     } 
/*      */ 
/*      */     
/* 1745 */     String[] columnNames = { "Task Name", "Wks to Rls", "Own", "Dpt" };
/* 1746 */     contents = ScheduleManager.getInstance().getScheduleTaskNotepadList(releaseId, userId, null, context);
/* 1747 */     return new Notepad(contents, 0, 15, "Tasks", 2, columnNames);
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
/*      */   private boolean editSave(Dispatcher dispatcher, Context context, String command, int screen) {
/* 1761 */     Selection selection = (Selection)context.getSessionValue("Selection");
/* 1762 */     Schedule schedule = selection.getSchedule();
/* 1763 */     User user = (User)context.getSessionValue("user");
/* 1764 */     Notepad notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 1);
/* 1765 */     Notepad notepadTasks = getTaskNotepad(context, user.getUserId(), selection.getSelectionID());
/*      */     
/* 1767 */     SelectionManager.getInstance().updateTemplateId(selection, user);
/*      */ 
/*      */ 
/*      */     
/* 1771 */     Form form = null;
/* 1772 */     if (screen == 0) {
/* 1773 */       form = buildForm(context, selection, schedule, command);
/*      */     } else {
/* 1775 */       form = buildFormWithTasks(context, selection, schedule, notepadTasks, command);
/*      */     } 
/* 1777 */     if (ScheduleManager.getInstance().isTimestampValid(schedule)) {
/*      */       
/* 1779 */       form.setValues(context);
/*      */ 
/*      */       
/* 1782 */       Vector tasks = schedule.getTasks();
/* 1783 */       Vector precache = new Vector();
/* 1784 */       ScheduledTask task = null;
/*      */       
/* 1786 */       for (int i = 0; i < tasks.size(); i++) {
/*      */         
/* 1788 */         task = (ScheduledTask)tasks.get(i);
/*      */ 
/*      */         
/* 1791 */         int originalWTR = task.getTaskWeeksToRelease();
/*      */         
/* 1793 */         String weeksTR = form.getStringValue("wksToRelease" + i);
/*      */         
/* 1795 */         int index = weeksTR.lastIndexOf(" ");
/* 1796 */         String day = "";
/* 1797 */         String weeks = "0";
/*      */         
/* 1799 */         if (weeksTR.equalsIgnoreCase("sol")) {
/*      */           
/* 1801 */           if (originalWTR == -10)
/*      */           {
/* 1803 */             weeks = String.valueOf(-10);
/* 1804 */             day = "9";
/*      */           }
/*      */           else
/*      */           {
/* 1808 */             weeks = String.valueOf(task.getWeeksToRelease());
/* 1809 */             day = String.valueOf(task.getDayOfTheWeek().getDayID());
/*      */           
/*      */           }
/*      */         
/*      */         }
/* 1814 */         else if (index > 0) {
/*      */           
/* 1816 */           day = weeksTR.substring(0, index);
/* 1817 */           day = day.trim();
/* 1818 */           weeks = weeksTR.substring(index, weeksTR.length());
/* 1819 */           weeks = weeks.trim();
/*      */ 
/*      */         
/*      */         }
/* 1823 */         else if (weeksTR.length() > 0) {
/*      */ 
/*      */           
/*      */           try {
/* 1827 */             weeks = weeksTR.trim();
/* 1828 */             int weeksToRelease = Integer.parseInt(weeks);
/* 1829 */             weeks = String.valueOf(weeksToRelease);
/* 1830 */             day = "D";
/*      */           }
/* 1832 */           catch (NumberFormatException e) {
/*      */             
/* 1834 */             weeks = "0";
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1840 */         String due = form.getStringValue("DueDate" + i);
/* 1841 */         String complete = form.getStringValue("Completion" + i);
/* 1842 */         String status = form.getStringValue("status" + i);
/* 1843 */         String vendor = form.getStringValue("vendor" + i);
/* 1844 */         String comments = form.getStringValue("comments" + i);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1849 */         task.setWeeksToRelease(Integer.parseInt(weeks));
/* 1850 */         task.setDayOfTheWeek(new Day(day));
/*      */         
/* 1852 */         if (due.length() > 0) {
/*      */ 
/*      */           
/* 1855 */           due = due.trim();
/* 1856 */           if (task.getDueDate() != null && !due.equalsIgnoreCase(MilestoneHelper.getFormatedDate(task.getDueDate()))) {
/*      */             
/* 1858 */             task.setWeeksToRelease(-1);
/* 1859 */             task.setDayOfTheWeek(null);
/*      */           }
/* 1861 */           else if (task.getDueDate() == null) {
/*      */             
/* 1863 */             task.setWeeksToRelease(-1);
/* 1864 */             task.setDayOfTheWeek(null);
/*      */           } 
/*      */           
/* 1867 */           task.setDueDate(MilestoneHelper.getDate(due));
/*      */         
/*      */         }
/*      */         else {
/*      */           
/* 1872 */           if (task.getDueDate() != null) {
/*      */             
/* 1874 */             task.setWeeksToRelease(-1);
/* 1875 */             task.setDayOfTheWeek(null);
/*      */           } 
/*      */           
/* 1878 */           task.setDueDate(null);
/*      */         } 
/*      */         
/* 1881 */         if (complete.length() > 0) {
/* 1882 */           task.setCompletionDate(MilestoneHelper.getDate(complete));
/*      */         } else {
/* 1884 */           task.setCompletionDate(null);
/*      */         } 
/*      */ 
/*      */         
/* 1888 */         if (status.equalsIgnoreCase("today") && complete.length() == 0) {
/*      */           
/* 1890 */           task.setCompletionDate(Calendar.getInstance());
/*      */         }
/* 1892 */         else if (task.getCompletionDate() == null && ((
/* 1893 */           status.equalsIgnoreCase("done") && 
/* 1894 */           !task.getAllowMultCompleteDatesFlag() && 
/* 1895 */           !MilestoneHelper.isUml(task)) || 
/* 1896 */           status.equalsIgnoreCase("n/a"))) {
/*      */           
/* 1898 */           task.setCompletionDate(MilestoneHelper.getDate("9/9/99"));
/*      */         } 
/* 1900 */         task.setScheduledTaskStatus(status);
/* 1901 */         task.setVendor(vendor);
/* 1902 */         task.setComments(comments);
/*      */         
/* 1904 */         precache.add(task);
/*      */       } 
/*      */       
/* 1907 */       schedule.setTasks(precache);
/*      */       
/* 1909 */       String releaseComments = form.getStringValue("releaseComment");
/*      */       
/* 1911 */       selection.setComments(releaseComments);
/*      */     }
/*      */     else {
/*      */       
/* 1915 */       context.putDelivery("AlertMessage", "The record is unavailable for editing at this time.  Please refresh your screen and edit it at a later time.");
/*      */     } 
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
/* 1928 */     form.addElement(new FormHidden("cmd", "schedule-editor", true));
/* 1929 */     form.addElement(new FormHidden("OrderTasksBy", "", true));
/*      */ 
/*      */     
/* 1932 */     schedule.setNew(isNewSchedule(schedule));
/*      */ 
/*      */ 
/*      */     
/* 1936 */     if (!form.isUnchanged() || schedule.isNew()) {
/*      */       
/* 1938 */       FormValidation formValidation = form.validate();
/* 1939 */       if (formValidation.isGood()) {
/*      */ 
/*      */         
/* 1942 */         Schedule savedSchedule = ScheduleManager.getInstance().saveSchedule(selection, schedule, user);
/* 1943 */         selection.setSchedule(savedSchedule);
/*      */ 
/*      */         
/* 1946 */         SelectionManager.getInstance().updateComment(selection);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1954 */         String sessionFilter = (String)context.getSessionValue("filter");
/* 1955 */         if (sessionFilter != null && sessionFilter.length() > 0)
/*      */         {
/* 1957 */           selection.setSchedule(ScheduleManager.getInstance().filterSchedule(schedule, sessionFilter));
/*      */         }
/*      */ 
/*      */         
/* 1961 */         sessionFilter = (String)context.getSessionValue("deptFilter");
/* 1962 */         if (sessionFilter != null && sessionFilter.length() > 0)
/*      */         {
/* 1964 */           selection.setSchedule(ScheduleManager.getInstance().deptFilterSchedule(selection.getSchedule(), sessionFilter, context));
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1970 */         int sortType = -1;
/* 1971 */         if (context.getSessionValue("ScheduleTaskSort") != null) {
/* 1972 */           sortType = Integer.parseInt((String)context.getSessionValue("ScheduleTaskSort"));
/*      */         }
/* 1974 */         if (schedule != null && schedule.getTasks() != null) {
/* 1975 */           savedSchedule.setTasks(sortScreenScheduledTaskVector(sortType, savedSchedule.getTasks()));
/*      */         }
/* 1977 */         context.putSessionValue("Schedule", savedSchedule);
/*      */ 
/*      */         
/* 1980 */         selection.setSchedule(savedSchedule);
/* 1981 */         context.putSessionValue("Selection", selection);
/*      */         
/* 1983 */         context.putDelivery("Form", form);
/*      */         
/* 1985 */         if (screen == 0) {
/* 1986 */           return edit(dispatcher, context, command);
/*      */         }
/* 1988 */         return editTasks(dispatcher, context, command);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1993 */       context.putDelivery("FormValidation", formValidation);
/*      */     } 
/*      */     
/* 1996 */     form.addElement(new FormHidden("OrderBy", "", true));
/* 1997 */     context.putDelivery("Form", form);
/*      */     
/* 1999 */     if (screen == 0)
/*      */     {
/* 2001 */       return context.includeJSP("schedule-editor.jsp");
/*      */     }
/*      */ 
/*      */     
/* 2005 */     return context.includeJSP("schedule-task-editor.jsp");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean recalc(Dispatcher dispatcher, Context context, String command, int screen) {
/* 2016 */     User user = (User)context.getSessionValue("user");
/*      */ 
/*      */     
/* 2019 */     Selection selection = (Selection)context.getSessionValue("Selection");
/* 2020 */     Schedule schedule = null;
/*      */     
/* 2022 */     if (selection != null) {
/*      */       
/* 2024 */       int secureLevel = getSchedulePermissions(selection, user);
/* 2025 */       setButtonVisibilities(selection, user, context, secureLevel, command);
/* 2026 */       schedule = ScheduleManager.getInstance().getSchedule(selection.getSelectionID());
/*      */ 
/*      */       
/* 2029 */       ScheduleManager.getInstance().recalculateDueDates(schedule, selection);
/*      */       
/* 2031 */       selection.setSchedule(schedule);
/* 2032 */       context.putSessionValue("Schedule", schedule);
/* 2033 */       context.putSessionValue("Selection", selection);
/*      */       
/* 2035 */       Notepad notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 1);
/* 2036 */       notepad.setSelected(selection);
/* 2037 */       MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */ 
/*      */       
/* 2040 */       Notepad notepadTasks = getTaskNotepad(context, user.getUserId(), selection.getSelectionID());
/* 2041 */       Form form = null;
/* 2042 */       if (screen == 0) {
/* 2043 */         form = buildForm(context, selection, schedule, command);
/*      */       } else {
/* 2045 */         form = buildFormWithTasks(context, selection, schedule, notepadTasks, command);
/*      */       } 
/* 2047 */       form.addElement(new FormHidden("cmd", "schedule-editor", true));
/* 2048 */       form.addElement(new FormHidden("OrderTasksBy", "", true));
/* 2049 */       context.putDelivery("Form", form);
/*      */       
/* 2051 */       context.putSessionValue("Selection", selection);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2058 */       if (schedule == null || (schedule.getTasks().size() == 0 && isNewSchedule(schedule))) {
/*      */         
/* 2060 */         suggestedTemplate(form, selection, context, command);
/* 2061 */         return false;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 2066 */       int sortType = -1;
/* 2067 */       if (context.getSessionValue("ScheduleTaskSort") != null) {
/* 2068 */         sortType = Integer.parseInt((String)context.getSessionValue("ScheduleTaskSort"));
/*      */       }
/* 2070 */       if (schedule != null && schedule.getTasks() != null) {
/* 2071 */         schedule.setTasks(sortScreenScheduledTaskVector(sortType, schedule.getTasks()));
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2077 */     return editSave(dispatcher, context, command, screen);
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
/*      */   private boolean recalcAll(Dispatcher dispatcher, Context context, String command, int screen) {
/* 2096 */     User user = (User)context.getSessionValue("user");
/*      */ 
/*      */     
/* 2099 */     Selection selection = (Selection)context.getSessionValue("Selection");
/* 2100 */     Schedule schedule = null;
/*      */     
/* 2102 */     if (selection != null) {
/*      */       
/* 2104 */       int secureLevel = getSchedulePermissions(selection, user);
/* 2105 */       setButtonVisibilities(selection, user, context, secureLevel, command);
/* 2106 */       schedule = ScheduleManager.getInstance().getSchedule(selection.getSelectionID());
/*      */ 
/*      */       
/* 2109 */       ScheduleManager.getInstance().recalculateAllDueDates(schedule, selection);
/*      */       
/* 2111 */       selection.setSchedule(schedule);
/* 2112 */       context.putSessionValue("Schedule", schedule);
/* 2113 */       context.putSessionValue("Selection", selection);
/*      */       
/* 2115 */       Notepad notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 1);
/* 2116 */       notepad.setSelected(selection);
/* 2117 */       MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */ 
/*      */       
/* 2120 */       Notepad notepadTasks = getTaskNotepad(context, user.getUserId(), selection.getSelectionID());
/* 2121 */       Form form = null;
/* 2122 */       if (screen == 0) {
/* 2123 */         form = buildForm(context, selection, schedule, command);
/*      */       } else {
/* 2125 */         form = buildFormWithTasks(context, selection, schedule, notepadTasks, command);
/*      */       } 
/* 2127 */       form.addElement(new FormHidden("cmd", "schedule-editor", true));
/* 2128 */       form.addElement(new FormHidden("OrderTasksBy", "", true));
/* 2129 */       context.putDelivery("Form", form);
/*      */       
/* 2131 */       context.putSessionValue("Selection", selection);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2138 */       if (schedule == null || (schedule.getTasks().size() == 0 && isNewSchedule(schedule))) {
/*      */         
/* 2140 */         suggestedTemplate(form, selection, context, command);
/* 2141 */         return false;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 2146 */       int sortType = -1;
/* 2147 */       if (context.getSessionValue("ScheduleTaskSort") != null) {
/* 2148 */         sortType = Integer.parseInt((String)context.getSessionValue("ScheduleTaskSort"));
/*      */       }
/* 2150 */       if (schedule != null && schedule.getTasks() != null) {
/* 2151 */         schedule.setTasks(sortScreenScheduledTaskVector(sortType, schedule.getTasks()));
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2157 */     return editSave(dispatcher, context, command, screen);
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
/*      */   private boolean close(Dispatcher dispatcher, Context context, String command, int screen) {
/* 2175 */     User user = (User)context.getSessionValue("user");
/*      */ 
/*      */     
/* 2178 */     Selection selection = (Selection)context.getSessionValue("Selection");
/* 2179 */     Schedule schedule = null;
/*      */     
/* 2181 */     if (selection != null) {
/*      */       
/* 2183 */       int secureLevel = getSchedulePermissions(selection, user);
/* 2184 */       setButtonVisibilities(selection, user, context, secureLevel, command);
/* 2185 */       schedule = ScheduleManager.getInstance().getSchedule(selection.getSelectionID());
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2190 */       boolean closed = ScheduleManager.getInstance().closeSchedule(schedule, selection, user);
/*      */       
/* 2192 */       Notepad notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 1);
/*      */       
/* 2194 */       if (!closed) {
/*      */ 
/*      */ 
/*      */         
/* 2198 */         context.putDelivery("AlertMessage", new String("All UML/eCommerce tasks must have completion dates before a selection can be closed."));
/* 2199 */         notepad.setSelected(selection);
/* 2200 */         selection.setSchedule(schedule);
/* 2201 */         context.putSessionValue("Schedule", schedule);
/* 2202 */         context.putSessionValue("Selection", selection);
/*      */       }
/*      */       else {
/*      */         
/* 2206 */         notepad.setAllContents(null);
/* 2207 */         notepad.setSelected(null);
/*      */         
/* 2209 */         if (screen == 0) {
/* 2210 */           return edit(dispatcher, context, command);
/*      */         }
/* 2212 */         return editTasks(dispatcher, context, command);
/*      */       } 
/*      */       
/* 2215 */       MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */ 
/*      */       
/* 2218 */       Notepad notepadTasks = getTaskNotepad(context, user.getUserId(), selection.getSelectionID());
/* 2219 */       Form form = null;
/* 2220 */       if (screen == 0) {
/* 2221 */         form = buildForm(context, selection, schedule, command);
/*      */       } else {
/* 2223 */         form = buildFormWithTasks(context, selection, schedule, notepadTasks, command);
/*      */       } 
/* 2225 */       form.addElement(new FormHidden("cmd", "schedule-editor", true));
/* 2226 */       form.addElement(new FormHidden("OrderTasksBy", "", true));
/* 2227 */       context.putDelivery("Form", form);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2234 */       if (schedule == null || (schedule.getTasks().size() == 0 && isNewSchedule(schedule))) {
/*      */         
/* 2236 */         suggestedTemplate(form, selection, context, command);
/* 2237 */         return false;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 2242 */       int sortType = -1;
/* 2243 */       if (context.getSessionValue("ScheduleTaskSort") != null) {
/* 2244 */         sortType = Integer.parseInt((String)context.getSessionValue("ScheduleTaskSort"));
/*      */       }
/* 2246 */       if (schedule != null && schedule.getTasks() != null) {
/* 2247 */         schedule.setTasks(sortScreenScheduledTaskVector(sortType, schedule.getTasks()));
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2255 */     if (screen == 0) {
/* 2256 */       return context.includeJSP("schedule-editor.jsp");
/*      */     }
/* 2258 */     return context.includeJSP("schedule-task-editor.jsp");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean clear(Dispatcher dispatcher, Context context, String command, int screen) {
/* 2267 */     User user = (User)context.getSessionValue("user");
/*      */ 
/*      */     
/* 2270 */     Selection selection = new Selection();
/* 2271 */     selection = MilestoneHelper.getScreenSelection(context);
/* 2272 */     Schedule schedule = null;
/*      */     
/* 2274 */     int secureLevel = getSchedulePermissions(selection, user);
/* 2275 */     setButtonVisibilities(selection, user, context, secureLevel, command);
/*      */     
/* 2277 */     if (selection != null) {
/* 2278 */       schedule = ScheduleManager.getInstance().getSchedule(selection.getSelectionID());
/*      */     }
/* 2280 */     if (schedule != null) {
/* 2281 */       ScheduleManager.getInstance().clearDates(schedule);
/*      */     }
/* 2283 */     selection.setSchedule(schedule);
/* 2284 */     context.putSessionValue("Schedule", schedule);
/* 2285 */     context.putSessionValue("selection", selection);
/*      */     
/* 2287 */     Notepad notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 1);
/* 2288 */     notepad.setSelected(selection);
/* 2289 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */ 
/*      */     
/* 2292 */     Notepad notepadTasks = getTaskNotepad(context, user.getUserId(), selection.getSelectionID());
/* 2293 */     Form form = null;
/* 2294 */     if (screen == 0) {
/* 2295 */       form = buildForm(context, selection, schedule, command);
/*      */     } else {
/* 2297 */       form = buildFormWithTasks(context, selection, schedule, notepadTasks, command);
/* 2298 */     }  form.addElement(new FormHidden("cmd", "schedule-editor", true));
/* 2299 */     form.addElement(new FormHidden("OrderTasksBy", "", true));
/* 2300 */     context.putDelivery("Form", form);
/* 2301 */     context.putSessionValue("selection", selection);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2308 */     if (schedule == null || (schedule.getTasks().size() == 0 && isNewSchedule(schedule))) {
/*      */       
/* 2310 */       suggestedTemplate(form, selection, context, command);
/* 2311 */       return false;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2316 */     int sortType = -1;
/* 2317 */     if (context.getSessionValue("ScheduleTaskSort") != null) {
/* 2318 */       sortType = Integer.parseInt((String)context.getSessionValue("ScheduleTaskSort"));
/*      */     }
/* 2320 */     if (schedule != null && schedule.getTasks() != null) {
/* 2321 */       schedule.setTasks(sortScreenScheduledTaskVector(sortType, schedule.getTasks()));
/*      */     }
/*      */     
/* 2324 */     if (screen == 0) {
/* 2325 */       return context.includeJSP("schedule-editor.jsp");
/*      */     }
/* 2327 */     return context.includeJSP("schedule-task-editor.jsp");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean editTasks(Dispatcher dispatcher, Context context, String command) {
/* 2336 */     Selection selection = null;
/*      */     
/* 2338 */     User user = (User)context.getSessionValue("user");
/*      */     
/* 2340 */     selection = (Selection)context.getSessionValue("Selection");
/*      */     
/* 2342 */     int secureLevel = getSchedulePermissions(selection, user);
/* 2343 */     setButtonVisibilities(selection, user, context, secureLevel, command);
/*      */     
/* 2345 */     if (selection != null) {
/*      */ 
/*      */       
/* 2348 */       Vector contents = new Vector();
/* 2349 */       Notepad notepadTasks = getTaskNotepad(context, user.getUserId(), selection.getSelectionID());
/* 2350 */       MilestoneHelper.putNotepadIntoSession(notepadTasks, context);
/*      */       
/* 2352 */       Schedule schedule = null;
/* 2353 */       schedule = ScheduleManager.getInstance().getSchedule(selection.getSelectionID());
/* 2354 */       selection.setSchedule(schedule);
/*      */       
/* 2356 */       setDeptFilter(schedule, context);
/*      */       
/* 2358 */       String sessionFilter = (String)context.getSessionValue("filter");
/* 2359 */       if (sessionFilter != null && sessionFilter.length() > 0)
/*      */       {
/* 2361 */         selection.setSchedule(ScheduleManager.getInstance().filterSchedule(selection.getSchedule(), sessionFilter));
/*      */       }
/*      */ 
/*      */       
/* 2365 */       sessionFilter = (String)context.getSessionValue("deptFilter");
/* 2366 */       if (sessionFilter != null && sessionFilter.length() > 0)
/*      */       {
/* 2368 */         selection.setSchedule(ScheduleManager.getInstance().deptFilterSchedule(selection.getSchedule(), sessionFilter, context));
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2374 */       int sortType = -1;
/* 2375 */       if (context.getSessionValue("ScheduleTaskSort") != null) {
/* 2376 */         sortType = Integer.parseInt((String)context.getSessionValue("ScheduleTaskSort"));
/*      */       }
/* 2378 */       if (schedule != null && schedule.getTasks() != null) {
/* 2379 */         schedule.setTasks(sortScreenScheduledTaskVector(sortType, schedule.getTasks()));
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2387 */       Form form = buildFormWithTasks(context, selection, schedule, notepadTasks, command);
/* 2388 */       form.addElement(new FormHidden("cmd", "schedule-task-editor", true));
/* 2389 */       form.addElement(new FormHidden("OrderTasksBy", "", true));
/* 2390 */       context.putDelivery("Form", form);
/*      */ 
/*      */       
/* 2393 */       if (schedule == null || (schedule.getTasks().size() == 0 && isNewSchedule(schedule))) {
/*      */         
/* 2395 */         suggestedTemplate(form, selection, context, command);
/* 2396 */         return true;
/*      */       } 
/*      */ 
/*      */       
/* 2400 */       context.putDelivery("templateName", SelectionManager.getInstance().getAssignedTemplateName(selection));
/*      */       
/* 2402 */       context.putSessionValue("Selection", selection);
/*      */       
/* 2404 */       return context.includeJSP("schedule-task-editor.jsp");
/*      */     } 
/*      */ 
/*      */     
/* 2408 */     return dispatcher.redispatch(context, "schedule-editor");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean addTask(Dispatcher dispatcher, Context context, String command) {
/* 2418 */     Selection selection = new Selection();
/* 2419 */     User user = (User)context.getSessionValue("user");
/* 2420 */     selection = MilestoneHelper.getScreenSelection(context);
/*      */     
/* 2422 */     int secureLevel = getSchedulePermissions(selection, user);
/* 2423 */     setButtonVisibilities(selection, user, context, secureLevel, command);
/*      */ 
/*      */     
/* 2426 */     Notepad notepadTasks = getTaskNotepad(context, user.getUserId(), selection.getSelectionID());
/* 2427 */     MilestoneHelper.putNotepadIntoSession(notepadTasks, context);
/*      */     
/* 2429 */     Schedule schedule = null;
/*      */     
/* 2431 */     if (selection != null) {
/* 2432 */       schedule = ScheduleManager.getInstance().getSchedule(selection.getSelectionID());
/*      */     }
/*      */     
/* 2435 */     Form form = buildFormWithTasks(context, selection, schedule, notepadTasks, command);
/*      */     
/* 2437 */     form.setValues(context);
/*      */     
/* 2439 */     Task task = null;
/* 2440 */     Vector notepadPageContents = notepadTasks.getPageContents();
/* 2441 */     for (int j = 0; j < notepadPageContents.size(); j++) {
/*      */       
/* 2443 */       task = (Task)notepadPageContents.get(j);
/* 2444 */       if (((FormCheckBox)form.getElement(String.valueOf(task.getTaskID()))).isChecked())
/*      */       {
/* 2446 */         if (task.getActiveFlag())
/*      */         {
/* 2448 */           ScheduleManager.getInstance().addTask(task, user, selection);
/*      */         }
/*      */       }
/* 2451 */       task = null;
/*      */     } 
/*      */ 
/*      */     
/* 2455 */     schedule = ScheduleManager.getInstance().getSchedule(selection.getSelectionID());
/* 2456 */     selection.setSchedule(schedule);
/*      */ 
/*      */     
/* 2459 */     context.putSessionValue("Selection", selection);
/*      */ 
/*      */     
/* 2462 */     notepadTasks.setAllContents(null);
/* 2463 */     notepadTasks = getTaskNotepad(context, user.getUserId(), selection.getSelectionID());
/* 2464 */     notepadTasks.goToSelectedPage();
/*      */     
/* 2466 */     return editTasks(dispatcher, context, command);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean deleteTaskInTaskEditor(Dispatcher dispatcher, Context context, String command) {
/* 2474 */     String taskId = context.getRequestValue("taskId");
/*      */ 
/*      */     
/* 2477 */     Selection selection = MilestoneHelper.getScreenSelection(context);
/*      */     
/* 2479 */     User user = (User)context.getSessionValue("user");
/*      */     
/* 2481 */     int secureLevel = getSchedulePermissions(selection, user);
/* 2482 */     setButtonVisibilities(selection, user, context, secureLevel, command);
/*      */     
/* 2484 */     if (selection != null) {
/* 2485 */       ScheduleManager.getInstance().deleteTask(taskId, selection, user);
/*      */     }
/*      */     
/* 2488 */     Notepad notepadTasks = getTaskNotepad(context, user.getUserId(), selection.getSelectionID());
/* 2489 */     notepadTasks.setAllContents(null);
/* 2490 */     notepadTasks = getTaskNotepad(context, user.getUserId(), selection.getSelectionID());
/* 2491 */     notepadTasks.goToSelectedPage();
/*      */     
/* 2493 */     return editTasks(dispatcher, context, command);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean deleteTask(Dispatcher dispatcher, Context context, String command) {
/* 2501 */     String taskId = context.getRequestValue("taskId");
/*      */     
/* 2503 */     User user = (User)context.getSessionValue("user");
/*      */ 
/*      */     
/* 2506 */     Selection selection = MilestoneHelper.getScreenSelection(context);
/* 2507 */     if (selection != null) {
/* 2508 */       ScheduleManager.getInstance().deleteTask(taskId, selection, user);
/*      */     }
/*      */     
/* 2511 */     int secureLevel = getSchedulePermissions(selection, user);
/* 2512 */     setButtonVisibilities(selection, user, context, secureLevel, command);
/*      */     
/* 2514 */     context.removeSessionValue(MilestoneConstants.NOTEPAD_SESSION_NAMES[4]);
/*      */     
/* 2516 */     return edit(dispatcher, context, command);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean deleteAllTasks(Dispatcher dispatcher, Context context, String command, int screen) {
/* 2525 */     Selection selection = (Selection)context.getSessionValue("Selection");
/*      */     
/* 2527 */     boolean deleteAll = true;
/* 2528 */     Schedule schedule = selection.getSchedule();
/*      */     
/* 2530 */     if (schedule != null && schedule.getTasks().size() > 0) {
/*      */       
/* 2532 */       Vector tasks = schedule.getTasks();
/*      */       
/* 2534 */       for (int i = 0; i < tasks.size(); i++) {
/*      */         
/* 2536 */         ScheduledTask scheduledTask = (ScheduledTask)tasks.get(i);
/*      */         
/* 2538 */         if (scheduledTask.getCompletionDate() != null || 
/* 2539 */           scheduledTask.getScheduledTaskStatus().length() > 0 || 
/* 2540 */           scheduledTask.getComments().length() > 0 || 
/* 2541 */           scheduledTask.getVendor().length() > 0) {
/*      */           
/* 2543 */           deleteAll = false;
/* 2544 */           context.putDelivery("AlertMessage", "All tasks can only be deleted when there is no data entered for any of the tasks on the schedule.");
/*      */         } 
/*      */       } 
/*      */       
/* 2548 */       User user = (User)context.getSessionValue("user");
/*      */       
/* 2550 */       if (selection != null && deleteAll) {
/* 2551 */         ScheduleManager.getInstance().deleteAllTasks(selection, user);
/*      */       }
/*      */       
/* 2554 */       int secureLevel = getSchedulePermissions(selection, user);
/* 2555 */       setButtonVisibilities(selection, user, context, secureLevel, command);
/*      */       
/* 2557 */       context.removeSessionValue(MilestoneConstants.NOTEPAD_SESSION_NAMES[4]);
/*      */ 
/*      */       
/* 2560 */       selection.setTemplateId(-1);
/* 2561 */       SelectionManager.getInstance().updateTemplateId(selection, user);
/*      */     }
/*      */     else {
/*      */       
/* 2565 */       context.putDelivery("AlertMessage", "There are no tasks to delete. To delete this release, select the delete key on the Selection screen.");
/*      */     } 
/*      */     
/* 2568 */     if (screen == 0) {
/* 2569 */       return edit(dispatcher, context, command);
/*      */     }
/* 2571 */     return editTasks(dispatcher, context, command);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean deleteAllTasksInTaskEditor(Dispatcher dispatcher, Context context, String command) {
/* 2580 */     User user = (User)context.getSessionValue("user");
/*      */ 
/*      */     
/* 2583 */     Selection selection = MilestoneHelper.getScreenSelection(context);
/* 2584 */     if (selection != null) {
/* 2585 */       ScheduleManager.getInstance().deleteAllTasks(selection, user);
/*      */     }
/*      */ 
/*      */     
/* 2589 */     int secureLevel = getSchedulePermissions(selection, user);
/* 2590 */     setButtonVisibilities(selection, user, context, secureLevel, command);
/*      */     
/* 2592 */     context.removeSessionValue(MilestoneConstants.NOTEPAD_SESSION_NAMES[4]);
/*      */     
/* 2594 */     return editTasks(dispatcher, context, command);
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
/*      */   private boolean suggestedTemplate(Form form, Selection selection, Context context, String command) {
/*      */     String filterProdType;
/* 2609 */     addSelectionSearchElements(context, selection, form);
/*      */     
/* 2611 */     User user = (User)context.getSessionValue("user");
/*      */ 
/*      */     
/* 2614 */     String filterString = "All";
/* 2615 */     if ((String)context.getSessionValue("filter") != null) {
/* 2616 */       filterString = (String)context.getSessionValue("filter");
/*      */     }
/* 2618 */     if (selection != null && selection.getIsDigital()) {
/* 2619 */       filterProdType = "All,Only Label Tasks,Only eCommerce Tasks";
/*      */     } else {
/* 2621 */       filterProdType = "All,Only Label Tasks,Only UML Tasks";
/* 2622 */     }  FormDropDownMenu filterDropdown = new FormDropDownMenu("filter", filterString, filterProdType, true);
/* 2623 */     filterDropdown.addFormEvent("onChange", "Javascript:parent.top.bottomFrame.location = 'home?cmd=schedule-suggested-template'");
/* 2624 */     filterDropdown.setId("filter");
/* 2625 */     form.addElement(filterDropdown);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2631 */     Vector suggestedTemplates = ScheduleManager.getInstance().getSuggestedTemplates(user, selection, context);
/* 2632 */     Template template = null;
/* 2633 */     FormRadioButtonGroup templateRadioBox = null;
/* 2634 */     FormTextField templateName = null;
/* 2635 */     FormTextField templateOwner = null;
/*      */     
/* 2637 */     context.putDelivery("suggestedTemplates", suggestedTemplates);
/* 2638 */     for (int i = 0; i < suggestedTemplates.size(); i++) {
/*      */       
/* 2640 */       template = (Template)suggestedTemplates.get(i);
/*      */       
/* 2642 */       templateRadioBox = new FormRadioButtonGroup("TemplateRadio" + String.valueOf(i), "", String.valueOf(template.getTemplateID()), false);
/* 2643 */       templateRadioBox.addFormEvent("onClick", "getTemplateTasks('" + template.getTemplateID() + "')");
/* 2644 */       templateRadioBox.setId(String.valueOf(template.getTemplateID()));
/* 2645 */       templateRadioBox.setShowLabels(false);
/* 2646 */       form.addElement(templateRadioBox);
/*      */ 
/*      */       
/* 2649 */       templateName = new FormTextField("TemplateName" + String.valueOf(i), template.getTempateName(), false, 20);
/* 2650 */       form.addElement(templateName);
/*      */ 
/*      */       
/* 2653 */       Family family = template.getOwner();
/* 2654 */       int familyId = -1;
/* 2655 */       if (family != null) {
/* 2656 */         familyId = family.getStructureID();
/*      */       }
/* 2658 */       templateOwner = new FormTextField("TemplateOwner" + String.valueOf(i), MilestoneHelper.getStructureName(familyId), false, 20);
/* 2659 */       form.addElement(templateOwner);
/*      */       
/* 2661 */       templateRadioBox = null;
/* 2662 */       templateOwner = null;
/* 2663 */       templateName = null;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2671 */     FormTextField templateNameSearch = new FormTextField("TemplateNameSearch", "", false, 20);
/* 2672 */     templateNameSearch.setId("TemplateNameSearch");
/* 2673 */     templateNameSearch.setTabIndex(1);
/* 2674 */     form.addElement(templateNameSearch);
/*      */ 
/*      */     
/* 2677 */     FormDropDownMenu configuration = MilestoneHelper.getSelectionConfigurationDropDown("TemplateConfigurationSearch", "", false, selection.getIsDigital() ? 1 : 0);
/* 2678 */     configuration.setTabIndex(2);
/* 2679 */     form.addElement(configuration);
/*      */ 
/*      */     
/* 2682 */     FormDropDownMenu productLine = MilestoneHelper.getLookupDropDown("TemplateProductLineSearch", Cache.getProductCategories(selection.getIsDigital() ? 1 : 0), "", false, true);
/* 2683 */     productLine.setTabIndex(3);
/* 2684 */     form.addElement(productLine);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2690 */     FormCheckBox showAllSearch = new FormCheckBox("ShowAllSearchSource", "", "", false);
/* 2691 */     showAllSearch.setId("ShowAllSearchSource");
/* 2692 */     form.addElement(showAllSearch);
/*      */     
/* 2694 */     Vector companies = MilestoneHelper.getUserCompanies(context);
/* 2695 */     Vector labels = MilestoneHelper.getUserLabels(companies);
/* 2696 */     FormDropDownMenu labelSearch = MilestoneHelper.getCorporateStructureDropDown("LabelSearchSource", labels, "", false, true);
/* 2697 */     labelSearch.setId("LabelSearchSource");
/* 2698 */     form.addElement(labelSearch);
/*      */ 
/*      */     
/* 2701 */     FormTextField streetDateSearch = new FormTextField("StreetDateSearchSource", "", false, 12, 10);
/* 2702 */     streetDateSearch.setId("StreetDateSearchSource");
/* 2703 */     form.addElement(streetDateSearch);
/*      */ 
/*      */     
/* 2706 */     FormTextField upcSearch = new FormTextField("UPCSearchSource", "", false, 12, 10);
/* 2707 */     upcSearch.setId("UPCSearchSource");
/* 2708 */     form.addElement(upcSearch);
/*      */ 
/*      */     
/* 2711 */     FormTextField prefixSearch = new FormTextField("PrefixSearchSource", "", false, 6, 5);
/* 2712 */     prefixSearch.setId("PrefixSearchSource");
/* 2713 */     form.addElement(prefixSearch);
/*      */ 
/*      */     
/* 2716 */     FormTextField selectionSearch = new FormTextField("SelectionSearchSource", "", false, 12, 10);
/* 2717 */     selectionSearch.setId("SelectionSearchSource");
/* 2718 */     form.addElement(selectionSearch);
/*      */ 
/*      */     
/* 2721 */     FormTextField titleSearch = new FormTextField("TitleSearchSource", "", false, 20);
/* 2722 */     titleSearch.setId("TitleSearchSource");
/* 2723 */     form.addElement(titleSearch);
/*      */ 
/*      */     
/* 2726 */     FormTextField artistSearch = new FormTextField("ArtistSearchSource", "", false, 20);
/* 2727 */     artistSearch.setId("ArtistSearchSource");
/* 2728 */     form.addElement(artistSearch);
/*      */ 
/*      */ 
/*      */     
/* 2732 */     int secureLevel = getSchedulePermissions(selection, user);
/* 2733 */     setButtonVisibilities(selection, user, context, secureLevel, command);
/*      */     
/* 2735 */     form.addElement(new FormHidden("cmd", "schedule-editor", true));
/* 2736 */     form.addElement(new FormHidden("OrderTasksBy", "", true));
/* 2737 */     form.addElement(new FormHidden("OrderBy", "", true));
/*      */ 
/*      */     
/* 2740 */     context.putDelivery("Form", form);
/*      */     
/* 2742 */     if (context.getSessionValue("NOTEPAD_SCHEDULE_VISIBLE") != null) {
/* 2743 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_SCHEDULE_VISIBLE"));
/*      */     }
/* 2745 */     return context.includeJSP("schedule-suggested-template.jsp");
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
/*      */   private boolean scheduleSelectionSearch(Dispatcher dispatcher, Context context, String command) {
/*      */     String filterProdType;
/* 2759 */     Form form = new Form(this.application, "ScheduleSelectTemplateForm", 
/* 2760 */         this.application.getInfrastructure().getServletURL(), "POST");
/*      */     
/* 2762 */     Selection selection = new Selection();
/*      */     
/* 2764 */     User user = (User)context.getSessionValue("user");
/*      */ 
/*      */     
/* 2767 */     Notepad notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 1);
/* 2768 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */     
/* 2770 */     selection = MilestoneHelper.getScreenSelection(context);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2775 */     addSelectionSearchElements(context, selection, form);
/* 2776 */     addTemplateSearchElements(context, selection, form);
/*      */     
/* 2778 */     form.setValues(context);
/*      */ 
/*      */     
/* 2781 */     String filterString = "All";
/* 2782 */     if ((String)context.getSessionValue("filter") != null) {
/* 2783 */       filterString = (String)context.getSessionValue("filter");
/*      */     }
/* 2785 */     if (selection != null && selection.getIsDigital()) {
/* 2786 */       filterProdType = "All,Only Label Tasks,Only eCommerce Tasks";
/*      */     } else {
/* 2788 */       filterProdType = "All,Only Label Tasks,Only UML Tasks";
/* 2789 */     }  FormDropDownMenu filterDropdown = new FormDropDownMenu("filter", filterString, filterProdType, true);
/* 2790 */     filterDropdown.addFormEvent("onChange", "Javascript:parent.top.bottomFrame.location = 'home?cmd=schedule-editor'");
/* 2791 */     filterDropdown.setId("filter");
/* 2792 */     form.addElement(filterDropdown);
/*      */     
/* 2794 */     String artist = form.getStringValue("ArtistSearchSource");
/* 2795 */     String title = form.getStringValue("TitleSearchSource");
/* 2796 */     String selectionString = form.getStringValue("SelectionSearchSource");
/* 2797 */     String UPC = form.getStringValue("UPCSearchSource");
/* 2798 */     String prefix = form.getStringValue("PrefixSearchSource");
/* 2799 */     String streetDate = form.getStringValue("StreetDateSearchSource");
/* 2800 */     String label = form.getStringValue("LabelSearchSource");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2806 */     Vector suggestedTemplates = ScheduleManager.getInstance().getScheduleSearch(user, selection, artist, title, selectionString, UPC, prefix, streetDate, label, context);
/* 2807 */     Selection currentSelection = null;
/* 2808 */     FormRadioButtonGroup selectionRadioBox = null;
/* 2809 */     FormTextField selectionArtist = null;
/* 2810 */     FormTextField selectionTitle = null;
/* 2811 */     FormTextField selectionUpc = null;
/* 2812 */     FormTextField selectionNo = null;
/* 2813 */     FormTextField selectionStreet = null;
/*      */     
/* 2815 */     context.putDelivery("suggestedTemplates", suggestedTemplates);
/* 2816 */     for (int i = 0; i < suggestedTemplates.size(); i++) {
/*      */       
/* 2818 */       currentSelection = (Selection)suggestedTemplates.get(i);
/*      */       
/* 2820 */       selectionRadioBox = new FormRadioButtonGroup("SelectionRadio" + String.valueOf(i), "", String.valueOf(currentSelection.getSelectionID()), false);
/* 2821 */       selectionRadioBox.addFormEvent("onClick", "getReleaseTasks(" + String.valueOf(currentSelection.getSelectionID()) + ")");
/* 2822 */       selectionRadioBox.setId(String.valueOf(currentSelection.getSelectionID()));
/* 2823 */       selectionRadioBox.setShowLabels(false);
/* 2824 */       form.addElement(selectionRadioBox);
/*      */ 
/*      */       
/* 2827 */       String artistString = currentSelection.getArtist();
/* 2828 */       selectionArtist = new FormTextField("selectionArtist" + String.valueOf(i), artistString, false, 20);
/* 2829 */       form.addElement(selectionArtist);
/*      */ 
/*      */       
/* 2832 */       String titleString = currentSelection.getTitle();
/* 2833 */       if (titleString.length() > 16)
/* 2834 */         titleString = titleString.substring(0, 15); 
/* 2835 */       selectionTitle = new FormTextField("selectionTitle" + String.valueOf(i), titleString, false, 20);
/* 2836 */       form.addElement(selectionTitle);
/*      */ 
/*      */       
/* 2839 */       selectionUpc = new FormTextField("selectionUpc" + String.valueOf(i), currentSelection.getUpc(), false, 20);
/* 2840 */       form.addElement(selectionUpc);
/*      */ 
/*      */       
/* 2843 */       selectionNo = new FormTextField("selectionNo" + String.valueOf(i), currentSelection.getSelectionNo(), false, 20);
/* 2844 */       form.addElement(selectionNo);
/*      */ 
/*      */       
/* 2847 */       selectionStreet = new FormTextField("selectionStreet" + String.valueOf(i), MilestoneHelper.getFormatedDate(currentSelection.getStreetDate()), false, 20);
/* 2848 */       form.addElement(selectionStreet);
/*      */       
/* 2850 */       selectionArtist = null;
/* 2851 */       selectionRadioBox = null;
/* 2852 */       selectionTitle = null;
/* 2853 */       selectionUpc = null;
/* 2854 */       selectionNo = null;
/* 2855 */       selectionStreet = null;
/* 2856 */       currentSelection = null;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2863 */     FormTextField templateNameSearch = new FormTextField("TemplateNameSearch", "", false, 20);
/* 2864 */     templateNameSearch.setId("TemplateNameSearch");
/* 2865 */     templateNameSearch.setTabIndex(1);
/* 2866 */     form.addElement(templateNameSearch);
/*      */ 
/*      */     
/* 2869 */     FormDropDownMenu configuration = MilestoneHelper.getSelectionConfigurationDropDown("TemplateConfigurationSearch", "", true);
/* 2870 */     configuration.setTabIndex(2);
/* 2871 */     form.addElement(configuration);
/*      */ 
/*      */     
/* 2874 */     FormDropDownMenu productLine = MilestoneHelper.getLookupDropDown("TemplateProductLineSearch", Cache.getProductCategories(), "", true, true);
/* 2875 */     productLine.setTabIndex(3);
/* 2876 */     form.addElement(productLine);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2882 */     FormCheckBox showAllSearch = new FormCheckBox("ShowAllSearchSource", "", "", false);
/* 2883 */     showAllSearch.setId("ShowAllSearchSource");
/* 2884 */     form.addElement(showAllSearch);
/*      */     
/* 2886 */     Vector companies = MilestoneHelper.getUserCompanies(context);
/* 2887 */     Vector labels = MilestoneHelper.getUserLabels(companies);
/* 2888 */     FormDropDownMenu labelSearch = MilestoneHelper.getCorporateStructureDropDown("LabelSearchSource", labels, "", false, true);
/* 2889 */     labelSearch.setId("LabelSearchSource");
/* 2890 */     form.addElement(labelSearch);
/*      */ 
/*      */     
/* 2893 */     FormTextField streetDateSearch = new FormTextField("StreetDateSearchSource", "", false, 14, 10);
/* 2894 */     streetDateSearch.setId("StreetDateSearchSource");
/* 2895 */     form.addElement(streetDateSearch);
/*      */ 
/*      */     
/* 2898 */     FormTextField upcSearch = new FormTextField("UPCSearchSource", "", false, 15, 15);
/* 2899 */     upcSearch.setId("UPCSearchSource");
/* 2900 */     form.addElement(upcSearch);
/*      */ 
/*      */     
/* 2903 */     FormTextField prefixSearch = new FormTextField("PrefixSearchSource", "", false, 6, 5);
/* 2904 */     prefixSearch.setId("PrefixSearchSource");
/* 2905 */     form.addElement(prefixSearch);
/*      */ 
/*      */     
/* 2908 */     FormTextField selectionSearch = new FormTextField("SelectionSearchSource", "", false, 15, 15);
/* 2909 */     selectionSearch.setId("SelectionSearchSource");
/* 2910 */     form.addElement(selectionSearch);
/*      */ 
/*      */     
/* 2913 */     FormTextField titleSearch = new FormTextField("TitleSearchSource", "", false, 20);
/* 2914 */     titleSearch.setId("TitleSearchSource");
/* 2915 */     form.addElement(titleSearch);
/*      */ 
/*      */     
/* 2918 */     FormTextField artistSearch = new FormTextField("ArtistSearchSource", "", false, 20);
/* 2919 */     artistSearch.setId("ArtistSearchSource");
/* 2920 */     form.addElement(artistSearch);
/*      */     
/* 2922 */     int secureLevel = getSchedulePermissions(selection, user);
/* 2923 */     setButtonVisibilities(selection, user, context, secureLevel, command);
/*      */     
/* 2925 */     if (context.getSessionValue("NOTEPAD_SCHEDULE_VISIBLE") != null) {
/* 2926 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_SCHEDULE_VISIBLE"));
/*      */     }
/* 2928 */     if (selection != null) {
/*      */       
/* 2930 */       context.putDelivery("releaseWeek", MilestoneHelper.getReleaseWeekString(selection));
/* 2931 */       ProductCategory productCategory = selection.getProductCategory();
/* 2932 */       String productCategoryString = "";
/* 2933 */       if (productCategory != null) {
/* 2934 */         productCategoryString = productCategory.getName();
/*      */       }
/* 2936 */       String typeConfig = String.valueOf(productCategoryString) + " / " + selection.getReleaseType().getName() + " / " + selection.getSelectionConfig().getSelectionConfigurationName() + " / " + selection.getSelectionSubConfig().getSelectionSubConfigurationName();
/* 2937 */       context.putDelivery("typeConfig", typeConfig);
/*      */     } 
/*      */     
/* 2940 */     form.addElement(new FormHidden("cmd", "schedule-copy-release-schedule", true));
/* 2941 */     form.addElement(new FormHidden("OrderTasksBy", "", true));
/* 2942 */     form.addElement(new FormHidden("OrderBy", "", true));
/* 2943 */     context.putDelivery("Form", form);
/*      */     
/* 2945 */     return context.includeJSP("schedule-copy-release-schedule.jsp");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean scheduleTaskSearch(Dispatcher dispatcher, Context context, String command) {
/* 2954 */     Notepad notepad = MilestoneHelper.getNotepadFromSession(2, context);
/*      */ 
/*      */     
/* 2957 */     notepad.setAllContents(null);
/* 2958 */     notepad.setSelected(null);
/*      */     
/* 2960 */     ScheduleManager.getInstance().setTaskNotepadQuery(context, notepad);
/* 2961 */     dispatcher.redispatch(context, "schedule-task-editor");
/*      */     
/* 2963 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean sortScheduleScreenTasks(Dispatcher dispatcher, Context context) {
/* 2973 */     int sort = Integer.parseInt(context.getParameter("OrderTasksBy"));
/* 2974 */     context.putDelivery("ScheduleTaskSort", Integer.toString(sort));
/* 2975 */     context.putSessionValue("ScheduleTaskSort", Integer.toString(sort));
/* 2976 */     dispatcher.redispatch(context, "schedule-editor");
/*      */     
/* 2978 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean sortScheduleTaskScreenTasks(Dispatcher dispatcher, Context context) {
/* 2988 */     int sort = Integer.parseInt(context.getParameter("OrderTasksBy"));
/* 2989 */     context.putDelivery("ScheduleTaskSort", Integer.toString(sort));
/* 2990 */     context.putSessionValue("ScheduleTaskSort", Integer.toString(sort));
/* 2991 */     dispatcher.redispatch(context, "schedule-task-editor");
/*      */     
/* 2993 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean scheduleTaskSort(Dispatcher dispatcher, Context context) {
/* 3004 */     int sort = Integer.parseInt(context.getParameter("OrderBy"));
/*      */     
/* 3006 */     int releaseId = ((Selection)context.getSessionValue("Selection")).getSelectionID();
/* 3007 */     int userId = ((User)context.getSessionValue("user")).getUserId();
/*      */     
/* 3009 */     Notepad notepad = getTaskNotepad(context, userId, releaseId);
/*      */     
/* 3011 */     if (notepad.getSearchQuery() == null || notepad.getSearchQuery().equals("")) {
/* 3012 */       notepad.setSearchQuery(ScheduleManager.getInstance().getDefaultTaskSearchQuery(releaseId));
/*      */     }
/* 3014 */     notepad.setOrderBy(" ORDER BY " + MilestoneConstants.SORT_TASK[sort]);
/*      */ 
/*      */ 
/*      */     
/* 3018 */     notepad.setAllContents(null);
/* 3019 */     notepad = getTaskNotepad(context, userId, releaseId);
/* 3020 */     notepad.goToSelectedPage();
/*      */     
/* 3022 */     dispatcher.redispatch(context, "schedule-task-editor");
/*      */     
/* 3024 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean scheduleSelectionSort(Dispatcher dispatcher, Context context) {
/* 3034 */     int userId = ((User)context.getSessionValue("user")).getUserId();
/*      */     
/* 3036 */     Notepad notepad = SelectionManager.getInstance().getSelectionNotepad(context, userId, 0);
/*      */ 
/*      */     
/* 3039 */     NotepadSortOrder.getNotepadSortOrderFromSession(context).sortHelper(dispatcher, context, notepad);
/*      */     
/* 3041 */     dispatcher.redispatch(context, "schedule-editor");
/*      */     
/* 3043 */     return true;
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
/* 3054 */   private boolean templateSearch(Dispatcher dispatcher, Context context, String command) { return context.includeJSP("schedule-select-template.jsp"); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean selectTemplate(Dispatcher dispatcher, Context context, String command) {
/*      */     String filterProdType;
/* 3065 */     Form form = new Form(this.application, "ScheduleSelectTemplateForm", 
/* 3066 */         this.application.getInfrastructure().getServletURL(), "POST");
/*      */ 
/*      */     
/* 3069 */     Selection selection = new Selection();
/*      */     
/* 3071 */     User user = (User)context.getSessionValue("user");
/*      */ 
/*      */     
/* 3074 */     Notepad notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 1);
/* 3075 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */     
/* 3077 */     selection = MilestoneHelper.getScreenSelection(context);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3082 */     addSelectionSearchElements(context, selection, form);
/* 3083 */     addTemplateSearchElements(context, selection, form);
/* 3084 */     form.setValues(context);
/*      */     
/* 3086 */     String templateNameSearch = form.getStringValue("TemplateNameSearch");
/* 3087 */     String templateConfigSearch = form.getStringValue("TemplateConfigurationSearch").equals("0") ? "" : form.getStringValue("TemplateConfigurationSearch");
/* 3088 */     String templateProductLineSearch = form.getStringValue("TemplateProductLineSearch").equals("0") ? "" : form.getStringValue("TemplateProductLineSearch");
/*      */ 
/*      */     
/* 3091 */     String filterString = "All";
/* 3092 */     if ((String)context.getSessionValue("filter") != null) {
/* 3093 */       filterString = (String)context.getSessionValue("filter");
/*      */     }
/* 3095 */     if (selection != null && selection.getIsDigital()) {
/* 3096 */       filterProdType = "All,Only Label Tasks,Only eCommerce Tasks";
/*      */     } else {
/* 3098 */       filterProdType = "All,Only Label Tasks,Only UML Tasks";
/* 3099 */     }  FormDropDownMenu filterDropdown = new FormDropDownMenu("filter", filterString, filterProdType, true);
/* 3100 */     filterDropdown.addFormEvent("onChange", "Javascript:parent.top.bottomFrame.location = 'home?cmd=schedule-editor'");
/* 3101 */     form.addElement(filterDropdown);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3107 */     Vector suggestedTemplates = ScheduleManager.getInstance().getTemplatesSearch(user, selection, templateNameSearch, templateConfigSearch, templateProductLineSearch, context);
/* 3108 */     Template template = null;
/* 3109 */     FormRadioButtonGroup templateRadioBox = null;
/* 3110 */     FormTextField templateName = null;
/* 3111 */     FormTextField templateOwner = null;
/*      */     
/* 3113 */     context.putDelivery("suggestedTemplates", suggestedTemplates);
/* 3114 */     for (int i = 0; i < suggestedTemplates.size(); i++) {
/*      */       
/* 3116 */       template = (Template)suggestedTemplates.get(i);
/* 3117 */       templateRadioBox = new FormRadioButtonGroup("TemplateRadio" + String.valueOf(i), "", String.valueOf(template.getTemplateID()), false);
/* 3118 */       templateRadioBox.addFormEvent("onClick", "getTemplateTasks(" + String.valueOf(template.getTemplateID()) + ")");
/* 3119 */       templateRadioBox.setId(String.valueOf(template.getTemplateID()));
/* 3120 */       templateRadioBox.setShowLabels(false);
/* 3121 */       form.addElement(templateRadioBox);
/*      */ 
/*      */       
/* 3124 */       templateName = new FormTextField("TemplateName" + String.valueOf(i), template.getTempateName(), false, 20);
/* 3125 */       form.addElement(templateName);
/*      */ 
/*      */       
/* 3128 */       Family family = template.getOwner();
/* 3129 */       String familyString = "";
/*      */       
/* 3131 */       if (family != null)
/* 3132 */         familyString = family.getName(); 
/* 3133 */       templateOwner = new FormTextField("TemplateOwner" + String.valueOf(i), familyString, false, 20);
/* 3134 */       form.addElement(templateOwner);
/*      */       
/* 3136 */       templateRadioBox = null;
/* 3137 */       templateOwner = null;
/* 3138 */       templateName = null;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 3143 */     int secureLevel = getSchedulePermissions(selection, user);
/* 3144 */     setButtonVisibilities(selection, user, context, secureLevel, command);
/*      */ 
/*      */     
/* 3147 */     if (context.getSessionValue("NOTEPAD_SCHEDULE_VISIBLE") != null) {
/* 3148 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_SCHEDULE_VISIBLE"));
/*      */     }
/* 3150 */     form.addElement(new FormHidden("cmd", "schedule-select-template", true));
/*      */     
/* 3152 */     if (selection != null) {
/*      */       
/* 3154 */       context.putDelivery("releaseWeek", MilestoneHelper.getReleaseWeekString(selection));
/*      */       
/* 3156 */       ProductCategory productCategory = selection.getProductCategory();
/* 3157 */       String productCategoryString = "";
/* 3158 */       if (productCategory != null) {
/* 3159 */         productCategoryString = productCategory.getName();
/*      */       }
/* 3161 */       String typeConfig = String.valueOf(productCategoryString) + " / " + selection.getReleaseType().getName() + " / " + selection.getSelectionConfig().getSelectionConfigurationName() + " / " + selection.getSelectionSubConfig().getSelectionSubConfigurationName();
/* 3162 */       context.putDelivery("typeConfig", typeConfig);
/*      */     } 
/*      */     
/* 3165 */     form.addElement(new FormHidden("OrderBy", "", true));
/* 3166 */     context.putDelivery("Form", form);
/*      */     
/* 3168 */     return context.includeJSP("schedule-select-template.jsp");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean assignTemplate(Dispatcher dispatcher, Context context, String command) {
/* 3174 */     Form form = new Form(this.application, "ScheduleSelectTemplateForm", 
/* 3175 */         this.application.getInfrastructure().getServletURL(), "POST");
/*      */ 
/*      */     
/* 3178 */     User user = (User)context.getSessionValue("user");
/*      */     
/* 3180 */     String templateId = "";
/* 3181 */     if (context.getRequestValue("templateId") != null) {
/* 3182 */       templateId = context.getRequestValue("templateId");
/*      */     }
/*      */     
/* 3185 */     Selection selection = (Selection)context.getSessionValue("Selection");
/*      */     
/* 3187 */     if (selection != null) {
/*      */       
/* 3189 */       ScheduleManager.getInstance().assignTemplate(user, selection, templateId);
/*      */       
/* 3191 */       int assignedTemplateId = -1;
/*      */ 
/*      */       
/*      */       try {
/* 3195 */         assignedTemplateId = Integer.parseInt(templateId);
/*      */       }
/* 3197 */       catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */       
/* 3201 */       selection.setTemplateId(assignedTemplateId);
/*      */       
/* 3203 */       Schedule schedule = selection.getSchedule();
/*      */       
/* 3205 */       int secureLevel = getSchedulePermissions(selection, user);
/* 3206 */       setButtonVisibilities(selection, user, context, secureLevel, command);
/*      */ 
/*      */       
/* 3209 */       Notepad notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 1);
/* 3210 */       MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */       
/* 3212 */       if (selection != null) {
/*      */         
/* 3214 */         ScheduledTask task = null;
/* 3215 */         for (int i = 0; i < schedule.getTasks().size(); i++) {
/*      */           
/* 3217 */           task = (ScheduledTask)schedule.getTasks().elementAt(i);
/* 3218 */           task.setReleaseID(-1);
/* 3219 */           task.setComments("");
/* 3220 */           task.setCompletionDate(null);
/* 3221 */           task.setDueDate(null);
/* 3222 */           task.setScheduledTaskStatus("");
/* 3223 */           task = null;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 3228 */         ScheduleManager.getInstance().recalculateDueDates(schedule, selection);
/*      */         
/* 3230 */         schedule.setTasks(sortScreenScheduledTaskVector(0, schedule.getTasks()));
/* 3231 */         schedule.setTasks(sortScreenScheduledTaskVector(1, schedule.getTasks()));
/* 3232 */         schedule.setTasks(sortScreenScheduledTaskVector(2, schedule.getTasks()));
/*      */ 
/*      */         
/* 3235 */         int sortType = -1;
/* 3236 */         if (context.getSessionValue("ScheduleTaskSort") != null) {
/* 3237 */           sortType = Integer.parseInt((String)context.getSessionValue("ScheduleTaskSort"));
/*      */         }
/* 3239 */         if (schedule != null && schedule.getTasks() != null) {
/* 3240 */           schedule.setTasks(sortScreenScheduledTaskVector(sortType, schedule.getTasks()));
/*      */         }
/* 3242 */         selection.setSchedule(schedule);
/*      */       } 
/*      */ 
/*      */       
/* 3246 */       form = buildForm(context, selection, schedule, command);
/* 3247 */       form.addElement(new FormHidden("cmd", "schedule-editor", true));
/* 3248 */       form.addElement(new FormHidden("OrderTasksBy", "", true));
/* 3249 */       context.putDelivery("Form", form);
/*      */       
/* 3251 */       if (selection != null) {
/*      */         
/* 3253 */         context.putDelivery("releaseWeek", MilestoneHelper.getReleaseWeekString(selection));
/* 3254 */         ProductCategory productCategory = selection.getProductCategory();
/* 3255 */         String productCategoryString = "";
/* 3256 */         if (productCategory != null) {
/* 3257 */           productCategoryString = productCategory.getName();
/*      */         }
/* 3259 */         String typeConfig = String.valueOf(productCategoryString) + " / " + selection.getReleaseType().getName() + " / " + selection.getSelectionConfig().getSelectionConfigurationName() + " / " + selection.getSelectionSubConfig().getSelectionSubConfigurationName();
/* 3260 */         context.putDelivery("typeConfig", typeConfig);
/*      */       } 
/*      */       
/* 3263 */       context.putSessionValue("Selection", selection);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3270 */       if (selection != null && (schedule == null || (schedule.getTasks().size() == 0 && isNewSchedule(schedule)))) {
/*      */         
/* 3272 */         suggestedTemplate(form, selection, context, command);
/*      */         
/* 3274 */         return true;
/*      */       } 
/*      */       
/* 3277 */       return context.includeJSP("schedule-editor.jsp");
/*      */     } 
/*      */     
/* 3280 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean copySchedule(Dispatcher dispatcher, Context context, String command) {
/* 3291 */     Form form = new Form(this.application, "ScheduleSelectTemplateForm", 
/* 3292 */         this.application.getInfrastructure().getServletURL(), "POST");
/*      */ 
/*      */     
/* 3295 */     Selection selection = new Selection();
/* 3296 */     selection = MilestoneHelper.getScreenSelection(context);
/*      */     
/* 3298 */     User user = (User)context.getSessionValue("user");
/*      */     
/* 3300 */     int secureLevel = getSchedulePermissions(selection, user);
/* 3301 */     setButtonVisibilities(selection, user, context, secureLevel, command);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3307 */     String releaseId = "";
/* 3308 */     if (context.getRequestValue("releaseId") != null) {
/* 3309 */       releaseId = context.getRequestValue("releaseId");
/*      */     }
/* 3311 */     Schedule schedule = ScheduleManager.getInstance().getSchedule(Integer.parseInt(releaseId));
/*      */ 
/*      */ 
/*      */     
/* 3315 */     Notepad notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 1);
/* 3316 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */     
/* 3318 */     if (selection != null) {
/*      */       
/* 3320 */       ScheduledTask task = null;
/* 3321 */       for (int i = 0; i < schedule.getTasks().size(); i++) {
/*      */         
/* 3323 */         task = (ScheduledTask)schedule.getTasks().elementAt(i);
/* 3324 */         task.setReleaseID(-1);
/* 3325 */         task.setComments("");
/* 3326 */         task.setCompletionDate(null);
/* 3327 */         task.setDueDate(null);
/* 3328 */         task.setScheduledTaskStatus("");
/* 3329 */         task.setVendor("");
/* 3330 */         task = null;
/*      */       } 
/*      */ 
/*      */       
/* 3334 */       ScheduleManager.getInstance().recalculateDueDates(schedule, selection);
/*      */ 
/*      */       
/* 3337 */       schedule.setTasks(sortScreenScheduledTaskVector(0, schedule.getTasks()));
/* 3338 */       schedule.setTasks(sortScreenScheduledTaskVector(1, schedule.getTasks()));
/* 3339 */       schedule.setTasks(sortScreenScheduledTaskVector(2, schedule.getTasks()));
/*      */ 
/*      */       
/* 3342 */       selection.setSchedule(schedule);
/*      */ 
/*      */       
/* 3345 */       int sortType = -1;
/* 3346 */       if (context.getSessionValue("ScheduleTaskSort") != null) {
/* 3347 */         sortType = Integer.parseInt((String)context.getSessionValue("ScheduleTaskSort"));
/*      */       }
/* 3349 */       if (schedule != null && schedule.getTasks() != null) {
/* 3350 */         schedule.setTasks(sortScreenScheduledTaskVector(sortType, schedule.getTasks()));
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 3355 */     form = buildForm(context, selection, schedule, command);
/* 3356 */     form.addElement(new FormHidden("cmd", "schedule-editor", true));
/* 3357 */     form.addElement(new FormHidden("OrderTasksBy", "", true));
/* 3358 */     form.addElement(new FormHidden("OrderBy", "", true));
/*      */     
/* 3360 */     context.putDelivery("Form", form);
/*      */     
/* 3362 */     context.putSessionValue("Selection", selection);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3369 */     if (selection != null && (schedule == null || (schedule.getTasks().size() == 0 && isNewSchedule(schedule)))) {
/*      */       
/* 3371 */       suggestedTemplate(form, selection, context, command);
/* 3372 */       return true;
/*      */     } 
/*      */     
/* 3375 */     return context.includeJSP("schedule-editor.jsp");
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
/*      */   public void setButtonVisibilities(Selection selection, User user, Context context, int level, String command) {
/* 3397 */     String copyVisible = "false";
/* 3398 */     String saveVisible = "false";
/* 3399 */     String saveCommentVisible = "false";
/* 3400 */     String deleteVisible = "false";
/* 3401 */     String newVisible = "false";
/* 3402 */     String recalcClearCloseVisible = "0";
/*      */     
/* 3404 */     Notepad notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 1);
/* 3405 */     notepad.setSwitchToTaskVisible(false);
/*      */     
/* 3407 */     int commentSecureLevel = SelectionHandler.getSelectionPermissions(selection, user);
/*      */     
/* 3409 */     if (commentSecureLevel > 1) {
/* 3410 */       saveCommentVisible = "true";
/*      */     }
/* 3412 */     if (level > 1) {
/*      */       
/* 3414 */       saveVisible = "true";
/* 3415 */       copyVisible = "true";
/* 3416 */       deleteVisible = "true";
/* 3417 */       recalcClearCloseVisible = "1";
/*      */       
/* 3419 */       notepad.setSwitchToTaskVisible(true);
/*      */       
/* 3421 */       if (selection.getSelectionID() > 0) {
/* 3422 */         newVisible = "true";
/*      */       }
/*      */     } 
/* 3425 */     if (command.indexOf("new") > -1 || command.indexOf("copy") > -1) {
/*      */       
/* 3427 */       saveVisible = "true";
/* 3428 */       copyVisible = "false";
/* 3429 */       deleteVisible = "false";
/* 3430 */       newVisible = "false";
/* 3431 */       recalcClearCloseVisible = "0";
/*      */     } 
/*      */ 
/*      */     
/* 3435 */     if (selection != null && selection.getSchedule() != null && selection.getSchedule().getTasks().size() == 0) {
/* 3436 */       notepad.setSwitchToTaskVisible(false);
/* 3437 */     } else if (selection != null && selection.getSchedule() == null) {
/* 3438 */       notepad.setSwitchToTaskVisible(false);
/*      */     } 
/*      */     
/* 3441 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */ 
/*      */     
/* 3444 */     context.putDelivery("saveVisible", saveVisible);
/*      */     
/* 3446 */     context.putDelivery("copyVisible", copyVisible);
/*      */     
/* 3448 */     context.putDelivery("deleteVisible", deleteVisible);
/*      */     
/* 3450 */     context.putDelivery("newVisible", newVisible);
/*      */ 
/*      */     
/* 3453 */     context.putDelivery("saveCommentVisible", saveCommentVisible);
/*      */ 
/*      */     
/* 3456 */     context.putDelivery("recalcClearCloseVisible", recalcClearCloseVisible);
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
/*      */   public static int getSchedulePermissions(Selection selection, User user) {
/* 3469 */     int level = 0;
/* 3470 */     int familyId = 0;
/*      */     
/* 3472 */     if (selection != null && selection.getSelectionID() > -1) {
/*      */ 
/*      */ 
/*      */       
/* 3476 */       Environment environment = selection.getEnvironment();
/*      */       
/* 3478 */       CompanyAcl selectionCompanyAcl = MilestoneHelper.getScreenPermissions(environment, user);
/* 3479 */       if (selectionCompanyAcl != null) {
/* 3480 */         level = selectionCompanyAcl.getAccessSchedule();
/*      */       }
/* 3482 */       if (level == 2)
/*      */       {
/*      */         
/* 3485 */         return level;
/*      */       }
/*      */ 
/*      */       
/* 3489 */       Schedule schedule = selection.getSchedule();
/*      */       
/* 3491 */       if (schedule != null && schedule.getTasks().size() > 0) {
/*      */         
/* 3493 */         Vector tasks = schedule.getTasks();
/* 3494 */         ScheduledTask scheduledTask = null;
/*      */         
/* 3496 */         for (int i = 0; i < tasks.size(); i++) {
/*      */           
/* 3498 */           scheduledTask = (ScheduledTask)tasks.get(i);
/*      */           
/* 3500 */           Family family = (scheduledTask.getOwner() != null) ? scheduledTask.getOwner() : null;
/*      */           
/* 3502 */           if (family != null) {
/*      */ 
/*      */ 
/*      */             
/* 3506 */             Hashtable familyAclHash = user.getAcl().getFamilyAccessHash();
/* 3507 */             if (familyAclHash != null) {
/*      */               
/* 3509 */               int accessInt = -1;
/* 3510 */               String accessStr = (String)familyAclHash.get(String.valueOf(family.getStructureID()));
/* 3511 */               if (accessStr != null && !accessStr.equals(""))
/* 3512 */                 accessInt = Integer.parseInt(accessStr); 
/* 3513 */               if (accessInt > level) {
/* 3514 */                 level = accessInt;
/*      */                 
/* 3516 */                 if (level == 2)
/*      */                 {
/*      */                   
/* 3519 */                   return level;
/*      */                 }
/*      */               } 
/*      */             } 
/*      */           } 
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
/* 3567 */           scheduledTask = null;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 3572 */     return level;
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
/*      */   public static Vector sortScreenScheduledTaskVector(int sortBy, Vector taskVector) {
/* 3589 */     Vector sortedVector = new Vector();
/* 3590 */     Object[] taskArray = taskVector.toArray();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3595 */     if (taskVector != null) {
/*      */       
/* 3597 */       switch (sortBy) {
/*      */         
/*      */         case 0:
/* 3600 */           Arrays.sort(taskArray, new SchedTaskNameComparator());
/*      */           break;
/*      */         
/*      */         case 1:
/* 3604 */           Arrays.sort(taskArray, new SchedTaskWksToReleaseComparator());
/*      */           break;
/*      */         
/*      */         case 2:
/* 3608 */           Arrays.sort(taskArray, new SchedTaskDueDateComparator());
/*      */           break;
/*      */         
/*      */         case 3:
/* 3612 */           Arrays.sort(taskArray, new SchedTaskCompleteComparator());
/*      */           break;
/*      */         
/*      */         case 4:
/* 3616 */           Arrays.sort(taskArray, new SchedTaskStatusComparator());
/*      */           break;
/*      */         
/*      */         case 5:
/* 3620 */           Arrays.sort(taskArray, new SchedTaskVendorComparator());
/*      */           break;
/*      */         
/*      */         case -1:
/* 3624 */           Arrays.sort(taskArray, new SchedTaskNameComparator());
/* 3625 */           Arrays.sort(taskArray, new SchedTaskWksToReleaseComparator());
/* 3626 */           Arrays.sort(taskArray, new SchedTaskDueDateComparator());
/*      */           break;
/*      */         
/*      */         default:
/* 3630 */           Arrays.sort(taskArray, new SchedTaskNameComparator());
/* 3631 */           Arrays.sort(taskArray, new SchedTaskWksToReleaseComparator());
/* 3632 */           Arrays.sort(taskArray, new SchedTaskDueDateComparator());
/*      */           break;
/*      */       } 
/*      */ 
/*      */       
/* 3637 */       for (int i = 0; i < taskArray.length; i++) {
/*      */         
/* 3639 */         ScheduledTask task = (ScheduledTask)taskArray[i];
/* 3640 */         sortedVector.add(task);
/*      */       } 
/*      */     } 
/*      */     
/* 3644 */     return sortedVector;
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
/*      */   private String getSearchJavaScriptCorporateArray(Context context) {
/* 3656 */     StringBuffer result = new StringBuffer(100);
/* 3657 */     String str = "";
/* 3658 */     String value = new String();
/* 3659 */     boolean foundFirstTemp = false;
/*      */     
/* 3661 */     User user = (User)context.getSessionValue("user");
/* 3662 */     Vector vUserCompanies = MilestoneHelper.getUserCompanies(context);
/*      */ 
/*      */     
/* 3665 */     HashMap corpHashMap = MilestoneHelper.buildActiveCorporateStructureHashMap();
/*      */ 
/*      */ 
/*      */     
/* 3669 */     result.append("\n");
/* 3670 */     result.append("var aSearch = new Array();\n");
/* 3671 */     int arrayIndex = 0;
/*      */     
/* 3673 */     result.append("aSearch[0] = new Array(");
/* 3674 */     result.append(0);
/* 3675 */     result.append(", '");
/* 3676 */     result.append(" ");
/* 3677 */     result.append('\'');
/* 3678 */     foundFirstTemp = true;
/*      */     
/* 3680 */     for (int a = 0; a < vUserCompanies.size(); a++) {
/*      */       
/* 3682 */       Company ueTemp = (Company)vUserCompanies.elementAt(a);
/* 3683 */       if (ueTemp != null) {
/*      */ 
/*      */         
/* 3686 */         Vector labels = Cache.getInstance().getLabels();
/* 3687 */         for (int b = 0; b < labels.size(); b++) {
/*      */           
/* 3689 */           Label node = (Label)labels.elementAt(b);
/*      */           
/* 3691 */           if (node.getParent().getParentID() == ueTemp.getStructureID() && 
/* 3692 */             !corpHashMap.containsKey(new Integer(node.getStructureID()))) {
/*      */             
/* 3694 */             if (foundFirstTemp)
/* 3695 */               result.append(','); 
/* 3696 */             result.append(' ');
/* 3697 */             result.append(node.getStructureID());
/* 3698 */             result.append(", '");
/* 3699 */             result.append(MilestoneHelper.urlEncode(node.getName()));
/* 3700 */             result.append('\'');
/* 3701 */             foundFirstTemp = true;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 3708 */     if (!foundFirstTemp) {
/* 3709 */       result.append("'[none available]');\n");
/*      */     } else {
/* 3711 */       result.append(");\n");
/*      */     } 
/*      */     
/* 3714 */     for (int i = 0; i < vUserCompanies.size(); i++) {
/*      */       
/* 3716 */       Company ue = (Company)vUserCompanies.elementAt(i);
/* 3717 */       if (ue != null) {
/*      */         
/* 3719 */         result.append("aSearch[");
/* 3720 */         result.append(ue.getStructureID());
/* 3721 */         result.append("] = new Array(");
/*      */         
/* 3723 */         boolean foundFirst = false;
/*      */         
/* 3725 */         result.append(0);
/* 3726 */         result.append(", '");
/* 3727 */         result.append(" ");
/* 3728 */         result.append('\'');
/* 3729 */         foundFirst = true;
/*      */         
/* 3731 */         Vector tmpArray = new Vector();
/*      */         
/* 3733 */         Vector labels = Cache.getInstance().getLabels();
/* 3734 */         for (int j = 0; j < labels.size(); j++) {
/*      */           
/* 3736 */           Label node = (Label)labels.elementAt(j);
/*      */           
/* 3738 */           if (node.getParent().getParentID() == ue.getStructureID() && 
/* 3739 */             !corpHashMap.containsKey(new Integer(node.getStructureID()))) {
/*      */             
/* 3741 */             if (foundFirst)
/* 3742 */               result.append(','); 
/* 3743 */             result.append(' ');
/* 3744 */             result.append(node.getStructureID());
/* 3745 */             result.append(", '");
/* 3746 */             result.append(MilestoneHelper.urlEncode(node.getName()));
/* 3747 */             result.append('\'');
/* 3748 */             foundFirst = true;
/* 3749 */             tmpArray.addElement(node);
/*      */           } 
/*      */         } 
/*      */         
/* 3753 */         if (foundFirst) {
/*      */           
/* 3755 */           result.append(");\n");
/*      */         }
/*      */         else {
/*      */           
/* 3759 */           result.append(" 0, '[none available]');\n");
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 3764 */     return result.toString();
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
/*      */   public static FormDropDownMenu getDepartmentFilterDropDown(String name, String selectedOption, boolean required, Schedule schedule, Context context) {
/* 3783 */     Vector values = new Vector();
/* 3784 */     Vector menuText = new Vector();
/*      */     
/* 3786 */     values.addElement("-1");
/* 3787 */     menuText.addElement("&nbsp;&nbsp;");
/*      */     
/* 3789 */     FormDropDownMenu dropDown = null;
/*      */     
/* 3791 */     if (selectedOption == null) {
/* 3792 */       selectedOption = "";
/*      */     }
/*      */     
/* 3795 */     Vector deptList = Cache.getLookupDetailValuesFromDatabase(18);
/*      */     
/* 3797 */     if (deptList != null) {
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
/* 3815 */       HashMap taskDepts = (HashMap)context.getSessionValue("scheduledTasksDepts");
/*      */       
/* 3817 */       for (int i = 0; i < deptList.size(); i++) {
/*      */         
/* 3819 */         LookupObject department = (LookupObject)deptList.elementAt(i);
/*      */         
/* 3821 */         if (department != null)
/*      */         {
/*      */ 
/*      */           
/* 3825 */           if (taskDepts != null && taskDepts.containsKey(department.getAbbreviation().trim())) {
/* 3826 */             values.addElement(department.getAbbreviation());
/* 3827 */             menuText.addElement(department.getName());
/*      */           } 
/*      */         }
/*      */       } 
/*      */       
/* 3832 */       String[] arrayValues = new String[values.size()];
/* 3833 */       String[] arrayMenuText = new String[menuText.size()];
/*      */ 
/*      */       
/* 3836 */       arrayValues = (String[])values.toArray(arrayValues);
/* 3837 */       arrayMenuText = (String[])menuText.toArray(arrayMenuText);
/*      */       
/* 3839 */       dropDown = new FormDropDownMenu(name, 
/* 3840 */           selectedOption, 
/* 3841 */           arrayValues, 
/* 3842 */           arrayMenuText, 
/* 3843 */           required);
/*      */     } 
/*      */     
/* 3846 */     return dropDown;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setDeptFilter(Schedule schedule, Context context) {
/* 3853 */     HashMap taskDepts = new HashMap();
/* 3854 */     if (schedule != null && schedule.getTasks().size() > 0) {
/*      */       
/* 3856 */       Vector tasks = schedule.getTasks();
/* 3857 */       for (int i = 0; i < tasks.size(); i++) {
/*      */         
/* 3859 */         ScheduledTask task = (ScheduledTask)tasks.get(i);
/*      */ 
/*      */         
/* 3862 */         if (!taskDepts.containsKey(task.getDepartment().trim()))
/* 3863 */           taskDepts.put(task.getDepartment().trim(), new Integer(i)); 
/*      */       } 
/*      */     } 
/* 3866 */     context.putSessionValue("scheduledTasksDepts", taskDepts);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean scheduleSelectionGroup(Dispatcher dispatcher, Context context) {
/* 3877 */     int sort = Integer.parseInt(context.getParameter("OrderBy"));
/*      */     
/* 3879 */     String alphaGroupChr = context.getParameter("alphaGroupChr");
/*      */ 
/*      */     
/* 3882 */     Notepad notepad = (Notepad)context.getSessionValue(MilestoneConstants.NOTEPAD_SESSION_NAMES[0]);
/*      */ 
/*      */     
/* 3885 */     User user = (User)context.getSession().getAttribute("user");
/* 3886 */     if (notepad.getAllContents() != null && notepad.getAllContents().size() < notepad.getTotalRecords()) {
/*      */       
/* 3888 */       notepad.setMaxRecords(0);
/* 3889 */       notepad.setAllContents(null);
/* 3890 */       notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 0);
/*      */     } 
/*      */     
/* 3893 */     SelectionManager.getInstance().getAlphaGroupPosition(context, notepad, alphaGroupChr, sort);
/*      */     
/* 3895 */     notepad.goToSelectedPage();
/*      */     
/* 3897 */     dispatcher.redispatch(context, "schedule-editor");
/*      */     
/* 3899 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String clearFilterIfProdChange(String sessionFilter, Selection selection, Context context) {
/* 3910 */     if (sessionFilter.indexOf("eCommerce") != -1 && !selection.getIsDigital()) {
/*      */ 
/*      */       
/* 3913 */       context.putSessionValue("filter", "All");
/* 3914 */       return "All";
/*      */     } 
/*      */     
/* 3917 */     if (sessionFilter.indexOf("UML") != -1 && selection.getIsDigital()) {
/*      */ 
/*      */       
/* 3920 */       context.putSessionValue("filter", "All");
/* 3921 */       return "All";
/*      */     } 
/* 3923 */     return sessionFilter;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean saveSelectionComments(Dispatcher dispatcher, Context context, String command) {
/* 3929 */     Selection selection = (Selection)context.getSessionValue("Selection");
/* 3930 */     Form form = buildForm(context, selection, selection.getSchedule(), command);
/* 3931 */     form.setValues(context);
/*      */     
/* 3933 */     String comments = form.getStringValue("releaseComment");
/* 3934 */     selection.setComments(comments);
/* 3935 */     SelectionManager.getInstance().updateComment(selection);
/*      */     
/* 3937 */     return edit(dispatcher, context, command);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isNewSchedule(Schedule schedule) {
/* 3947 */     boolean retVar = true;
/*      */     
/* 3949 */     if (schedule != null) {
/*      */       
/* 3951 */       String sqlQuery = "Select top 1 release_id from release_detail where release_id = " + schedule.getSelectionID();
/* 3952 */       JdbcConnector connector = MilestoneHelper.getConnector(sqlQuery);
/* 3953 */       connector.runQuery();
/*      */       
/* 3955 */       if (connector.more())
/* 3956 */         retVar = false; 
/* 3957 */       connector.close();
/*      */     } 
/* 3959 */     System.out.println("<<< Schedule.isNew() " + retVar + " release Id " + schedule.getSelectionID());
/* 3960 */     return retVar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ScheduledTask getScheduledTask(Schedule schedule, int taskId) {
/* 3969 */     ScheduledTask task = null;
/* 3970 */     if (schedule != null && taskId > 0) {
/*      */       
/* 3972 */       Vector tasks = schedule.getTasks();
/* 3973 */       if (tasks != null && tasks.size() > 0) {
/*      */         
/* 3975 */         int i = getScheduledTaskIndex(tasks, taskId);
/* 3976 */         if (i >= 0 && i < tasks.size())
/*      */         {
/* 3978 */           task = (ScheduledTask)tasks.get(i);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 3983 */     return task;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getScheduledTaskIndex(Vector tasks, int taskID) {
/* 3992 */     boolean tgtTaskFound = false;
/* 3993 */     int index = 0;
/* 3994 */     if (tasks != null && taskID > 0 && 
/* 3995 */       tasks != null && tasks.size() > 0) {
/* 3996 */       while (index < tasks.size() && !tgtTaskFound) {
/*      */         
/* 3998 */         if (((ScheduledTask)tasks.get(index)).getTaskID() == taskID) {
/* 3999 */           tgtTaskFound = true;
/*      */         }
/* 4001 */         index++;
/*      */       } 
/*      */     }
/*      */     
/* 4005 */     if (tgtTaskFound) {
/* 4006 */       return index - 1;
/*      */     }
/*      */     
/* 4009 */     return -1;
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
/*      */   private boolean multCompleteDateFrame(Dispatcher dispatcher, Context context, String command) {
/* 4027 */     Selection selection = (Selection)context.getSessionValue("Selection");
/*      */     
/* 4029 */     if (MilestoneHelper_2.hasSchedule(selection.getSelectionID())) {
/*      */ 
/*      */       
/* 4032 */       int rowNo = getMultCompleteDateFormRowNo(context);
/* 4033 */       int taskID = getMultCompleteDateFormTaskID(context);
/* 4034 */       String strCompDt = getMultCompleteDateFormCompletionDate(context);
/* 4035 */       String strInitCall = context.getRequestValue("init");
/* 4036 */       String strTaskDesc = context.getRequestValue("taskDesc");
/*      */       
/* 4038 */       User user = (User)context.getSessionValue("user");
/* 4039 */       int secureLevel = getSchedulePermissions(selection, user);
/* 4040 */       setButtonVisibilities(selection, user, context, secureLevel, command);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 4045 */       context.putIntDelivery("rowNo", rowNo);
/* 4046 */       context.putIntDelivery("taskID", taskID);
/* 4047 */       context.putDelivery("schedCompDt", strCompDt);
/* 4048 */       context.putDelivery("init", strInitCall);
/* 4049 */       context.putDelivery("taskDesc", strTaskDesc);
/* 4050 */       return context.includeJSP("multCompleteDateFrame.jsp");
/*      */     } 
/*      */ 
/*      */     
/* 4054 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean multCompleteDateEditor(Dispatcher dispatcher, Context context, String command) {
/* 4065 */     Selection selection = (Selection)context.getSessionValue("Selection");
/*      */     
/* 4067 */     if (MilestoneHelper_2.hasSchedule(selection.getSelectionID())) {
/*      */ 
/*      */       
/* 4070 */       int rowNo = getMultCompleteDateFormRowNo(context);
/* 4071 */       int taskID = getMultCompleteDateFormTaskID(context);
/* 4072 */       String strCompDt = getMultCompleteDateFormCompletionDate(context);
/* 4073 */       String strInitCall = context.getRequestValue("init");
/*      */       
/* 4075 */       User user = (User)context.getSession().getAttribute("user");
/* 4076 */       int secureLevel = getSchedulePermissions(selection, user);
/* 4077 */       setButtonVisibilities(selection, user, context, secureLevel, command);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 4086 */       Schedule schedule = (Schedule)context.getSessionValue("Schedule");
/* 4087 */       ScheduledTask task = null;
/* 4088 */       if (schedule != null) {
/*      */         
/* 4090 */         task = getScheduledTask(schedule, taskID);
/* 4091 */         if (task != null) {
/*      */ 
/*      */ 
/*      */           
/* 4095 */           Vector formDates = new Vector();
/*      */           
/* 4097 */           MultCompleteDate mcd = new MultCompleteDate();
/* 4098 */           mcd.setReleaseID(task.getReleaseID());
/* 4099 */           mcd.setTaskID(task.getTaskID());
/* 4100 */           mcd.setOrderNo(0);
/*      */ 
/*      */ 
/*      */           
/* 4104 */           if (strInitCall != null && strInitCall.equals("1")) {
/*      */             
/* 4106 */             mcd.setCompletionDate(MilestoneHelper.getDate(strCompDt));
/*      */           }
/*      */           else {
/*      */             
/* 4110 */             mcd.setCompletionDate(task.getCompletionDate());
/*      */           } 
/*      */           
/* 4113 */           formDates.add(mcd);
/*      */           
/* 4115 */           Vector multCompleteDates = task.getMultCompleteDates();
/* 4116 */           if (multCompleteDates != null)
/*      */           {
/* 4118 */             formDates.addAll(1, multCompleteDates);
/*      */           }
/* 4120 */           buildMultCompleteDateForm(context, command, formDates);
/*      */         } 
/*      */       } 
/*      */       
/* 4124 */       context.putIntDelivery("rowNo", rowNo);
/* 4125 */       context.putIntDelivery("taskID", taskID);
/* 4126 */       context.putDelivery("schedCompDt", strCompDt);
/*      */       
/* 4128 */       return context.includeJSP("multCompleteDate-editor.jsp");
/*      */     } 
/*      */     
/* 4131 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean multCompleteDateEditorCancel(Dispatcher dispatcher, Context context, String command) {
/* 4140 */     buildMultCompleteDateForm(context, command, null);
/* 4141 */     return context.includeJSP("multCompleteDate-editor.jsp");
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
/*      */   private boolean multCompleteDateEditorSave(Dispatcher dispatcher, Context context, String command) {
/* 4154 */     int rowNo = getMultCompleteDateFormRowNo(context);
/* 4155 */     int taskID = getMultCompleteDateFormTaskID(context);
/* 4156 */     String strCompDt = getMultCompleteDateFormCompletionDate(context);
/* 4157 */     int rows = getMultCompleteDateFormRows(context);
/*      */ 
/*      */     
/* 4160 */     boolean compDtAfterDueDt = false;
/* 4161 */     String strCurCompDt = "";
/* 4162 */     Vector submittedMultCompleteDates = null;
/* 4163 */     if (taskID > 0 && rows >= 0) {
/*      */       
/* 4165 */       Schedule sessionSchedule = (Schedule)context.getSessionValue("Schedule");
/* 4166 */       if (sessionSchedule != null) {
/*      */         
/* 4168 */         ScheduledTask sessionTask = getScheduledTask(sessionSchedule, taskID);
/* 4169 */         if (sessionTask != null) {
/*      */           
/* 4171 */           String aCompletionDate = context.getRequestValue("completeDate0");
/*      */ 
/*      */           
/* 4174 */           if (MilestoneHelper.isStringNotEmpty(aCompletionDate)) {
/* 4175 */             sessionTask.setCompletionDate(MilestoneHelper.getDate(aCompletionDate));
/*      */           } else {
/*      */             
/* 4178 */             sessionTask.setCompletionDate(null);
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/* 4183 */           submittedMultCompleteDates = new Vector();
/* 4184 */           int orderNo = rows - 1;
/* 4185 */           for (int i = 1; i < rows; i++) {
/* 4186 */             String aCompleteDate = context.getRequestValue("completeDate" + i);
/*      */             
/* 4188 */             if (MilestoneHelper.isStringNotEmpty(aCompleteDate)) {
/* 4189 */               MultCompleteDate mcd = new MultCompleteDate();
/* 4190 */               mcd.setReleaseID(sessionSchedule.getSelectionID());
/* 4191 */               mcd.setTaskID(taskID);
/* 4192 */               mcd.setOrderNo(orderNo);
/* 4193 */               mcd.setCompletionDate(MilestoneHelper.getDate(aCompleteDate));
/*      */               
/* 4195 */               submittedMultCompleteDates.add(mcd);
/* 4196 */               orderNo--;
/*      */             } 
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/* 4202 */           sessionTask.setMultCompleteDates(submittedMultCompleteDates);
/* 4203 */           User user = (User)context.getSession().getAttribute("user");
/* 4204 */           ScheduleManager.getInstance().saveMultCompleteDates(sessionTask, user);
/*      */ 
/*      */ 
/*      */           
/* 4208 */           ScheduledTask dbTask = ScheduleManager.getInstance().getScheduledTask(sessionSchedule
/* 4209 */               .getSelectionID(), taskID);
/* 4210 */           int taskIndex = getScheduledTaskIndex(sessionSchedule.getTasks(), taskID);
/* 4211 */           if (taskIndex >= 0) {
/* 4212 */             sessionSchedule.getTasks().set(taskIndex, dbTask);
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 4218 */           Vector formDates = new Vector();
/*      */           
/* 4220 */           MultCompleteDate mcd = new MultCompleteDate();
/* 4221 */           mcd.setReleaseID(dbTask.getReleaseID());
/* 4222 */           mcd.setTaskID(dbTask.getTaskID());
/* 4223 */           mcd.setOrderNo(0);
/* 4224 */           mcd.setCompletionDate(dbTask.getCompletionDate());
/* 4225 */           formDates.add(mcd);
/*      */           
/* 4227 */           Vector multCompleteDates = dbTask.getMultCompleteDates();
/* 4228 */           if (multCompleteDates != null) {
/* 4229 */             formDates.addAll(1, multCompleteDates);
/*      */           }
/*      */           
/* 4232 */           buildMultCompleteDateForm(context, command, formDates);
/*      */ 
/*      */           
/* 4235 */           strCurCompDt = MilestoneHelper.getFormatedDate(dbTask.getCompletionDate());
/*      */ 
/*      */           
/* 4238 */           if (dbTask.getCompletionDate() != null && 
/* 4239 */             !dbTask.getCompletionDate().equals(MilestoneHelper.getDate("9/9/99")) && 
/* 4240 */             dbTask.getDueDate() != null && 
/* 4241 */             dbTask.getCompletionDate().after(dbTask.getDueDate())) {
/* 4242 */             compDtAfterDueDt = true;
/*      */           }
/*      */ 
/*      */           
/* 4246 */           context.putSessionValue("Schedule", sessionSchedule);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4254 */     context.putIntDelivery("compDtAfterDueDt", compDtAfterDueDt ? 1 : 0);
/* 4255 */     context.putIntDelivery("rowNo", rowNo);
/* 4256 */     context.putIntDelivery("taskID", taskID);
/* 4257 */     context.putDelivery("schedCompDt", strCompDt);
/* 4258 */     context.putDelivery("curCompDt", strCurCompDt);
/* 4259 */     return context.includeJSP("multCompleteDate-editor.jsp");
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
/*      */   private boolean multCompleteDateEditorModify(Dispatcher dispatcher, Context context, String command) {
/* 4274 */     int rowNo = getMultCompleteDateFormRowNo(context);
/* 4275 */     int taskID = getMultCompleteDateFormTaskID(context);
/* 4276 */     String strCompDt = getMultCompleteDateFormCompletionDate(context);
/* 4277 */     int rows = getMultCompleteDateFormRows(context);
/*      */ 
/*      */     
/* 4280 */     int dateIndex = -1;
/* 4281 */     if (command.equalsIgnoreCase("schedule-multCompleteDates-delete")) {
/* 4282 */       String strDateIndex = context.getRequestValue("dateIndex");
/* 4283 */       if (strDateIndex != null) {
/* 4284 */         dateIndex = Integer.parseInt(strDateIndex);
/*      */       }
/*      */     } 
/*      */     
/* 4288 */     Schedule sessionSchedule = (Schedule)context.getSessionValue("Schedule");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4295 */     Vector submittedCompleteDates = new Vector();
/* 4296 */     for (int i = 0; i < rows; i++) {
/* 4297 */       String aCompleteDate = context.getRequestValue("completeDate" + i);
/*      */       
/* 4299 */       if (MilestoneHelper.isStringNotEmpty(aCompleteDate)) {
/*      */         
/* 4301 */         boolean keepDate = false;
/* 4302 */         if (command.equalsIgnoreCase("schedule-multCompleteDates-delete")) {
/* 4303 */           if (i != dateIndex) {
/* 4304 */             keepDate = true;
/*      */           }
/*      */         } else {
/*      */           
/* 4308 */           keepDate = true;
/*      */         } 
/*      */         
/* 4311 */         if (keepDate) {
/* 4312 */           MultCompleteDate mcd = new MultCompleteDate();
/* 4313 */           mcd.setReleaseID(sessionSchedule.getSelectionID());
/* 4314 */           mcd.setTaskID(taskID);
/* 4315 */           mcd.setOrderNo(i);
/* 4316 */           mcd.setCompletionDate(MilestoneHelper.getDate(aCompleteDate));
/*      */           
/* 4318 */           submittedCompleteDates.add(mcd);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 4324 */     if (command.equalsIgnoreCase("schedule-multCompleteDates-add")) {
/* 4325 */       submittedCompleteDates.add(0, null);
/*      */     }
/*      */ 
/*      */     
/* 4329 */     buildMultCompleteDateForm(context, command, submittedCompleteDates);
/*      */     
/* 4331 */     context.putIntDelivery("rowNo", rowNo);
/* 4332 */     context.putIntDelivery("taskID", taskID);
/* 4333 */     context.putDelivery("schedCompDt", strCompDt);
/* 4334 */     return context.includeJSP("multCompleteDate-editor.jsp");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void buildMultCompleteDateForm(Context context, String command, Vector completeDates) {
/* 4343 */     Form multCompleteDateForm = new Form(this.application, "multCompleteDateForm", 
/* 4344 */         this.application.getInfrastructure().getServletURL(), "POST");
/* 4345 */     int rows = 0;
/* 4346 */     if (completeDates != null && completeDates.size() > 0) {
/*      */ 
/*      */       
/* 4349 */       for (int j = 0; j < completeDates.size(); j++) {
/* 4350 */         MultCompleteDate mcd = (MultCompleteDate)completeDates.get(j);
/* 4351 */         String aCompleteDate = "";
/* 4352 */         if (mcd != null && mcd.getCompletionDate() != null) {
/* 4353 */           aCompleteDate = MilestoneHelper.getFormatedDate(mcd.getCompletionDate());
/*      */         }
/* 4355 */         FormTextField completeDateField = new FormTextField("completeDate" + j, aCompleteDate, false, 7, 10);
/* 4356 */         completeDateField.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
/*      */         
/* 4358 */         completeDateField.addFormEvent("onBlur", "JavaScript:validateDate(this);");
/*      */ 
/*      */         
/* 4361 */         multCompleteDateForm.addElement(completeDateField);
/*      */       } 
/* 4363 */       rows = completeDates.size();
/*      */     } 
/* 4365 */     multCompleteDateForm.addElement(new FormHidden("cmd", command, true));
/* 4366 */     context.putDelivery("Form", multCompleteDateForm);
/* 4367 */     context.putIntDelivery("rows", rows);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getMultCompleteDateFormRowNo(Context context) {
/* 4376 */     String strRowNo = context.getRequestValue("rowNo");
/* 4377 */     int rowNo = -1;
/* 4378 */     if (MilestoneHelper.isStringNotEmpty(strRowNo)) {
/* 4379 */       rowNo = Integer.parseInt(strRowNo);
/*      */     }
/* 4381 */     return rowNo;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getMultCompleteDateFormTaskID(Context context) {
/* 4390 */     String strTaskID = context.getRequestValue("taskID");
/* 4391 */     int taskID = -1;
/* 4392 */     if (MilestoneHelper.isStringNotEmpty(strTaskID)) {
/* 4393 */       taskID = Integer.parseInt(strTaskID);
/*      */     }
/* 4395 */     return taskID;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 4404 */   private String getMultCompleteDateFormCompletionDate(Context context) { return context.getRequestValue("schedCompDt"); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getMultCompleteDateFormRows(Context context) {
/* 4414 */     int rows = -1;
/* 4415 */     String strRows = context.getRequestValue("rows");
/* 4416 */     if (MilestoneHelper.isStringNotEmpty(strRows)) {
/* 4417 */       rows = Integer.parseInt(strRows);
/*      */     }
/* 4419 */     return rows;
/*      */   }
/*      */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\ScheduleHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */