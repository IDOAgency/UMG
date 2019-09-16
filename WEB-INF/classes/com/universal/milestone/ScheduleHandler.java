package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.gemini.Context;
import com.techempower.gemini.Dispatcher;
import com.techempower.gemini.FormCheckBox;
import com.techempower.gemini.FormDropDownMenu;
import com.techempower.gemini.FormHidden;
import com.techempower.gemini.FormRadioButtonGroup;
import com.techempower.gemini.FormTextArea;
import com.techempower.gemini.FormTextField;
import com.techempower.gemini.FormValidation;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.Cache;
import com.universal.milestone.Company;
import com.universal.milestone.CompanyAcl;
import com.universal.milestone.Day;
import com.universal.milestone.Environment;
import com.universal.milestone.Family;
import com.universal.milestone.Form;
import com.universal.milestone.JdbcConnector;
import com.universal.milestone.Label;
import com.universal.milestone.LookupObject;
import com.universal.milestone.MilestoneConstants;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.MilestoneHelper_2;
import com.universal.milestone.MultCompleteDate;
import com.universal.milestone.Notepad;
import com.universal.milestone.NotepadSortOrder;
import com.universal.milestone.ProductCategory;
import com.universal.milestone.ReleasingFamily;
import com.universal.milestone.SchedTaskCompleteComparator;
import com.universal.milestone.SchedTaskDueDateComparator;
import com.universal.milestone.SchedTaskNameComparator;
import com.universal.milestone.SchedTaskStatusComparator;
import com.universal.milestone.SchedTaskVendorComparator;
import com.universal.milestone.SchedTaskWksToReleaseComparator;
import com.universal.milestone.Schedule;
import com.universal.milestone.ScheduleHandler;
import com.universal.milestone.ScheduleManager;
import com.universal.milestone.ScheduledTask;
import com.universal.milestone.SecureHandler;
import com.universal.milestone.Selection;
import com.universal.milestone.SelectionHandler;
import com.universal.milestone.SelectionManager;
import com.universal.milestone.Task;
import com.universal.milestone.Template;
import com.universal.milestone.User;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

public class ScheduleHandler extends SecureHandler {
  public static final String COMPONENT_CODE = "hSched";
  
  public GeminiApplication application;
  
  public ComponentLog log;
  
  public static final String deptFilterFormat = "department.filter.";
  
  public static final String filterPhysical = "All,Only Label Tasks,Only UML Tasks";
  
  public static final String filterDigital = "All,Only Label Tasks,Only eCommerce Tasks";
  
  public ScheduleHandler(GeminiApplication application) {
    this.application = application;
    this.log = application.getLog("hSched");
  }
  
  public ScheduleHandler() {}
  
  public String getDescription() { return "Schedule"; }
  
  public boolean acceptRequest(Dispatcher dispatcher, Context context, String command) {
    if (super.acceptRequest(dispatcher, context, command))
      if (command.startsWith("schedule"))
        return handleRequest(dispatcher, context, command);  
    return false;
  }
  
  public boolean handleRequest(Dispatcher dispatcher, Context context, String command) {
    if (command.equalsIgnoreCase("schedule-editor")) {
      edit(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("schedule-recalc")) {
      recalc(dispatcher, context, command, 0);
    } else if (command.equalsIgnoreCase("schedule-recalc-all")) {
      recalcAll(dispatcher, context, command, 0);
    } else if (command.equalsIgnoreCase("schedule-close")) {
      close(dispatcher, context, command, 0);
    } else if (command.equalsIgnoreCase("schedule-clear")) {
      clear(dispatcher, context, command, 0);
    } else if (command.equalsIgnoreCase("schedule-task-recalc")) {
      recalc(dispatcher, context, command, 1);
    } else if (command.equalsIgnoreCase("schedule-task-recalc-all")) {
      recalcAll(dispatcher, context, command, 1);
    } else if (command.equalsIgnoreCase("schedule-task-close")) {
      close(dispatcher, context, command, 1);
    } else if (command.equalsIgnoreCase("schedule-task-clear")) {
      clear(dispatcher, context, command, 1);
    } else if (command.equalsIgnoreCase("schedule-save")) {
      editSave(dispatcher, context, command, 0);
    } else if (command.equalsIgnoreCase("schedule-task-save")) {
      editSave(dispatcher, context, command, 1);
    } else if (command.equalsIgnoreCase("schedule-task-editor")) {
      editTasks(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("schedule-task-search")) {
      scheduleTaskSearch(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("schedule-task-sort")) {
      scheduleTaskSort(dispatcher, context);
    } else if (command.equalsIgnoreCase("schedule-screen_task-sort")) {
      sortScheduleScreenTasks(dispatcher, context);
    } else if (command.equalsIgnoreCase("schedule-task-screen-task-sort")) {
      sortScheduleTaskScreenTasks(dispatcher, context);
    } else if (command.equalsIgnoreCase("schedule-add-task")) {
      addTask(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("schedule-delete-task-editor")) {
      deleteTaskInTaskEditor(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("schedule-delete-task")) {
      deleteTask(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("schedule-delete-all-tasks")) {
      deleteAllTasks(dispatcher, context, command, 0);
    } else if (command.equalsIgnoreCase("schedule-delete-all-tasks-in-task-editor")) {
      deleteAllTasks(dispatcher, context, command, 1);
    } else if (command.equalsIgnoreCase("schedule-selection-search")) {
      scheduleSelectionSearch(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("schedule-sort")) {
      scheduleSelectionSort(dispatcher, context);
    } else if (command.equalsIgnoreCase("schedule-selection-release-search")) {
      selectionSearch(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("schedule-temlate-search")) {
      templateSearch(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("schedule-select-template")) {
      selectTemplate(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("schedule-assign-template")) {
      assignTemplate(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("schedule-copy-release-schedule")) {
      scheduleSelectionSearch(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("schedule-copy-release-assign-schedule")) {
      copySchedule(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("schedule-filter")) {
      filter(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("schedule-task-filter")) {
      filterTask(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("schedule-dept-filter")) {
      deptFilter(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("schedule-task-dept-filter")) {
      deptFilterTask(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("schedule-group")) {
      scheduleSelectionGroup(dispatcher, context);
    } else if (command.equalsIgnoreCase("schedule-save-selection-comments")) {
      saveSelectionComments(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("schedule-multCompleteDates-frame")) {
      multCompleteDateFrame(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("schedule-multCompleteDates-editor")) {
      multCompleteDateEditor(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("schedule-multCompleteDates-cancel")) {
      multCompleteDateEditorCancel(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("schedule-multCompleteDates-save")) {
      multCompleteDateEditorSave(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("schedule-multCompleteDates-add")) {
      multCompleteDateEditorModify(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("schedule-multCompleteDates-delete")) {
      multCompleteDateEditorModify(dispatcher, context, command);
    } 
    return true;
  }
  
  private boolean filter(Dispatcher dispatcher, Context context, String command) {
    Form form = buildForm(context, null, null, command);
    form.setValues(context);
    String filterValue = form.getStringValue("filter");
    context.putSessionValue("filter", filterValue);
    return edit(dispatcher, context, command);
  }
  
  private boolean deptFilter(Dispatcher dispatcher, Context context, String command) {
    Form form = buildForm(context, null, null, command);
    form.setValues(context);
    String filterValue = form.getStringValue("deptFilter");
    context.putSessionValue("deptFilter", filterValue);
    return edit(dispatcher, context, command);
  }
  
  private boolean filterTask(Dispatcher dispatcher, Context context, String command) {
    Form form = buildForm(context, null, null, command);
    form.setValues(context);
    String filterValue = form.getStringValue("filter");
    context.putSessionValue("filter", filterValue);
    return editTasks(dispatcher, context, command);
  }
  
  private boolean deptFilterTask(Dispatcher dispatcher, Context context, String command) {
    Form form = buildForm(context, null, null, command);
    form.setValues(context);
    String filterValue = form.getStringValue("deptFilter");
    context.putSessionValue("deptFilter", filterValue);
    return editTasks(dispatcher, context, command);
  }
  
  private boolean edit(Dispatcher dispatcher, Context context, String command) {
    Notepad notepadTask = MilestoneHelper.getNotepadFromSession(2, context);
    this.log.log("<<< NotepadTask " + notepadTask);
    if (notepadTask != null) {
      ScheduleManager.getInstance().setTaskNotepadQuery(context, notepadTask);
      notepadTask.setAllContents(null);
      notepadTask.setSelected(null);
    } 
    Selection selection = null;
    User user = (User)context.getSessionValue("user");
    Notepad notepad = null;
    if (context.getRequestValue("releaseCalendar") != null) {
      user.SS_searchInitiated = true;
      notepad = (Notepad)context.getSessionValue(MilestoneConstants.NOTEPAD_SESSION_NAMES[1]);
      if (notepad == null) {
        notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 1);
        MilestoneHelper.putNotepadIntoSession(notepad, context);
      } 
      if (notepad != null) {
        int selectionID = -1;
        if (context.getRequestValue("selection-id") != null) {
          notepad.setMaxRecords(1);
          notepad.setAllContents(null);
          selectionID = Integer.parseInt(context.getRequestValue("selection-id"));
          notepad.setSearchQuery(SelectionManager.getDefaultQuery(context));
          if (notepad.getOrderBy() == null || notepad.getOrderBy().equals("")) {
            NotepadSortOrder notepadSortOrder = NotepadSortOrder.getNotepadSortOrderFromSession(context);
            notepadSortOrder.setSelectionOrderCol("Artist");
            notepadSortOrder.setShowGroupButtons(true);
            if (notepad.getOrderBy().indexOf(" DESC ") == -1) {
              notepad.setOrderBy(" ORDER BY artist, title, selection_no, street_date ");
              notepadSortOrder.setSelectionOrderBy(" ORDER BY artist, title, selection_no, street_date ");
              notepadSortOrder.setSelectionOrderColNo(0);
            } else {
              notepad.setOrderBy(" ORDER BY artist DESC , title, selection_no, street_date ");
              notepadSortOrder.setSelectionOrderBy(" ORDER BY artist DESC , title, selection_no, street_date ");
              notepadSortOrder.setSelectionOrderColNo(7);
            } 
          } 
          String closeStr = "NOT ( status = 'CLOSED' OR  status = 'CANCEL' )";
          int pos = notepad.getSearchQuery().indexOf(closeStr);
          if (pos != -1) {
            StringBuffer query = new StringBuffer(notepad.getSearchQuery());
            StringBuffer newQuery = query.delete(pos, pos + closeStr.length());
            newQuery.insert(pos, "(release_id = " + selectionID + ") ");
            notepad.setSearchQuery(newQuery.toString());
          } 
        } 
      } 
    } 
    notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 1);
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    String recalcDate = (String)context.getDelivery("recalc-date");
    if (recalcDate != null && recalcDate.equalsIgnoreCase("true") && context.getSessionValue("Selection") != null) {
      int selectionId = ((Selection)context.getSessionValue("Selection")).getSelectionID();
      selection = SelectionManager.getInstance().getSelectionHeader(selectionId);
      notepad.setSelected(selection);
    } else {
      selection = MilestoneHelper.getScreenSelection(context);
    } 
    Schedule schedule = null;
    if (selection != null) {
      schedule = ScheduleManager.getInstance().getSchedule(selection.getSelectionID());
      selection.setSchedule(schedule);
      setDeptFilter(schedule, context);
      String sessionFilter = (String)context.getSessionValue("filter");
      if (!command.equalsIgnoreCase("schedule-filter")) {
        String filterValue = "All";
        if (selection.getIsDigital()) {
          if (user.getPreferences().getScheduleDigitalOwner() == 2)
            filterValue = "Only eCommerce Tasks"; 
          if (user.getPreferences().getScheduleDigitalOwner() == 3)
            filterValue = "Only Label Tasks"; 
        } else {
          if (user.getPreferences().getSchedulePhysicalOwner() == 2)
            filterValue = "Only UML Tasks"; 
          if (user.getPreferences().getSchedulePhysicalOwner() == 3)
            filterValue = "Only Label Tasks"; 
        } 
        sessionFilter = filterValue;
        context.putSessionValue("filter", filterValue);
      } 
      sessionFilter = clearFilterIfProdChange(sessionFilter, selection, context);
      if (sessionFilter != null && sessionFilter.length() > 0)
        selection.setSchedule(ScheduleManager.getInstance().filterSchedule(schedule, sessionFilter)); 
      sessionFilter = (String)context.getSessionValue("deptFilter");
      if (sessionFilter != null && sessionFilter.length() > 0)
        selection.setSchedule(ScheduleManager.getInstance().deptFilterSchedule(selection.getSchedule(), sessionFilter, context)); 
      int sortType = -1;
      if (context.getSessionValue("ScheduleTaskSort") != null)
        sortType = Integer.parseInt((String)context.getSessionValue("ScheduleTaskSort")); 
      if (sortType == -1 || context.getParameter("OrderTasksBy") == null) {
        if (selection.getIsDigital()) {
          switch (user.getPreferences().getSchedulePhysicalSortBy()) {
            case 1:
              sortType = 0;
              break;
            case 2:
              sortType = 1;
              break;
            case 3:
              sortType = 2;
              break;
            case 4:
              sortType = 3;
              break;
            case 5:
              sortType = 4;
              break;
            case 6:
              sortType = 5;
              break;
          } 
        } else {
          switch (user.getPreferences().getSchedulePhysicalSortBy()) {
            case 1:
              sortType = 0;
              break;
            case 2:
              sortType = 1;
              break;
            case 3:
              sortType = 2;
              break;
            case 4:
              sortType = 3;
              break;
            case 5:
              sortType = 4;
              break;
            case 6:
              sortType = 5;
              break;
          } 
        } 
        context.putDelivery("ScheduleTaskSort", Integer.toString(sortType));
        context.putSessionValue("ScheduleTaskSort", Integer.toString(sortType));
      } 
      if (schedule != null && schedule.getTasks() != null)
        schedule.setTasks(sortScreenScheduledTaskVector(sortType, schedule.getTasks())); 
      selection.setSchedule(schedule);
      Form form = buildForm(context, selection, schedule, command);
      form.addElement(new FormHidden("cmd", "schedule-editor", true));
      form.addElement(new FormHidden("OrderTasksBy", "", true));
      context.putDelivery("Form", form);
      context.putSessionValue("Selection", selection);
      if (schedule != null)
        context.putSessionValue("Schedule", schedule); 
      if (selection != null && (schedule == null || (schedule.getTasks().size() == 0 && isNewSchedule(schedule)))) {
        suggestedTemplate(form, selection, context, command);
        return true;
      } 
      recalcDate = (String)context.getDelivery("recalc-date");
      if (recalcDate != null && recalcDate.equalsIgnoreCase("true"))
        context.putDelivery("recalc-date", "true"); 
      context.putDelivery("templateName", SelectionManager.getInstance().getAssignedTemplateName(selection));
      return context.includeJSP("schedule-editor.jsp");
    } 
    context.putDelivery("isNotePadVisible", new Boolean(MilestoneHelper.getNotePadVisiblitiy(0, context)));
    Form form = new Form(this.application, "form", this.application.getInfrastructure().getServletURL(), "Post");
    form.addElement(new FormHidden("cmd", "schedule-editor"));
    form.addElement(new FormHidden("OrderBy", "", true));
    notepad.setSwitchToTaskVisible(false);
    addSelectionSearchElements(context, selection, form);
    context.putDelivery("Form", form);
    return context.includeJSP("blank-schedule-editor.jsp");
  }
  
  private boolean selectionSearch(Dispatcher dispatcher, Context context, String command) {
    String isSelectionSearchResults = context.getParameter("isSelectionSearchResults");
    if (isSelectionSearchResults == null || !isSelectionSearchResults.equals("true")) {
      Notepad notepad = MilestoneHelper.getNotepadFromSession(1, 
          context);
      notepad.setAllContents(null);
      notepad.setSelected(null);
      notepad.setMaxRecords(105);
      Form form = new Form(this.application, "selectionForm", 
          this.application.getInfrastructure().getServletURL(), 
          "POST");
      addSelectionSearchElements(context, null, form);
      form.setValues(context);
      SelectionManager.getInstance().setSelectionNotepadQuery(context, (
          (User)context.getSessionValue("user")).getUserId(), notepad, form);
    } 
    dispatcher.redispatch(context, "schedule-editor");
    return true;
  }
  
  protected Form buildForm(Context context, Selection selection, Schedule schedule, String command) {
    String filterProdType;
    Calendar testDate = Calendar.getInstance();
    User sessionUser = (User)context.getSessionValue("user");
    Form form = new Form(this.application, "ScheduleForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    form = addSelectionSearchElements(context, selection, form);
    int secureLevel = getSchedulePermissions(selection, sessionUser);
    setButtonVisibilities(selection, sessionUser, context, secureLevel, command);
    String selDepartment = "All";
    if ((String)context.getSessionValue("deptFilter") != null)
      selDepartment = (String)context.getSessionValue("deptFilter"); 
    FormDropDownMenu deptFilterDD = getDepartmentFilterDropDown("deptFilter", selDepartment, false, schedule, context);
    String[] values = deptFilterDD.getValueList();
    String[] menuText = deptFilterDD.getMenuTextList();
    values[0] = "All";
    menuText[0] = "All";
    deptFilterDD.setValueList(values);
    deptFilterDD.setMenuTextList(menuText);
    deptFilterDD.setTabIndex(1);
    deptFilterDD.addFormEvent("onChange", "Javascript:clickDeptFilter(this)");
    form.addElement(deptFilterDD);
    String filterString = "All";
    if ((String)context.getSessionValue("filter") != null)
      filterString = (String)context.getSessionValue("filter"); 
    if (selection != null && selection.getIsDigital()) {
      filterProdType = "All,Only Label Tasks,Only eCommerce Tasks";
    } else {
      filterProdType = "All,Only Label Tasks,Only UML Tasks";
    } 
    FormDropDownMenu filterDropdown = new FormDropDownMenu("filter", filterString, filterProdType, true);
    String prefixId = "";
    String selectionNo = "";
    if (selection != null) {
      if (SelectionManager.getLookupObjectValue(selection.getPrefixID()) != null)
        prefixId = SelectionManager.getLookupObjectValue(selection.getPrefixID()); 
      selectionNo = String.valueOf(selection.getSelectionNo());
      prefixId = String.valueOf(prefixId) + selectionNo;
    } 
    FormTextField prefix = new FormTextField("prefixId", prefixId, false, 13, 10);
    filterDropdown.addFormEvent("onChange", "Javascript:clickCurrentFilter(this)");
    form.addElement(filterDropdown);
    Vector scheduleRights = new Vector();
    if (selection != null && schedule != null) {
      Hashtable familyAclHash = sessionUser.getAcl().getFamilyAccessHash();
      Vector scheduledTasks = schedule.getTasks();
      for (int i = 0; i < scheduledTasks.size(); i++) {
        ScheduledTask scheduledTask = (ScheduledTask)scheduledTasks.get(i);
        FormTextField duedate = new FormTextField("DueDate" + i, "", false, 10);
        duedate.setLength(7);
        FormTextField wksToRelease = new FormTextField("wksToRelease" + i, false, 5);
        wksToRelease.setLength(4);
        wksToRelease.addFormEvent("onBlur", "Javascript:validateWks2Rel(this," + (
            new Integer(scheduledTask.getTaskWeeksToRelease())).toString() + "," + (
            new Integer(-10)).toString() + ")");
        String wksToReleaseString = "";
        if (scheduledTask.getWeeksToRelease() >= 0) {
          wksToReleaseString = (scheduledTask.getDayOfTheWeek() != null) ? scheduledTask.getDayOfTheWeek().getDay() : "";
          wksToReleaseString = String.valueOf(wksToReleaseString) + " " + scheduledTask.getWeeksToRelease();
          wksToRelease.setValue(wksToReleaseString);
          wksToRelease.setStartingValue(wksToRelease.getStringValue());
        } else if (scheduledTask.getWeeksToRelease() == -10) {
          wksToRelease.setValue("SOL");
          wksToRelease.setStartingValue(wksToRelease.getStringValue());
        } 
        FormDropDownMenu status = new FormDropDownMenu("status" + i);
        status.setValue(scheduledTask.getScheduledTaskStatus());
        status.setStartingValue(status.getStringValue());
        if (scheduledTask.getScheduledTaskStatus().equals("Auto")) {
          status.setValueList("&nbsp;,Auto,Done,Hold,N/A,Pend,Today");
          status.setMenuTextList("&nbsp;,Auto,Done,Hold,N/A,Pend,Today");
        } else {
          status.setValueList("&nbsp;,Done,Hold,N/A,Pend,Today");
          status.setMenuTextList("&nbsp;,Done,Hold,N/A,Pend,Today");
        } 
        FormTextField vendor = new FormTextField("vendor" + i, false, 50);
        vendor.setValue(scheduledTask.getVendor());
        vendor.setStartingValue(vendor.getStringValue());
        vendor.setLength(8);
        vendor.addFormEvent("onDblClick", "showDetailData( this, 'vendorLayer', 'vendorText' )");
        FormHidden comments = new FormHidden("comments" + i);
        if (scheduledTask.getComments() != null && !scheduledTask.getComments().equalsIgnoreCase("[none]") && !scheduledTask.getComments().equalsIgnoreCase("null") && 
          scheduledTask.getComments().length() > 0) {
          comments.setValue(scheduledTask.getComments());
        } else {
          comments.setValue("");
        } 
        if (scheduledTask.getDueDate() != null) {
          duedate.setValue(MilestoneHelper.getFormatedDate(scheduledTask.getDueDate()));
          duedate.setStartingValue(duedate.getStringValue());
        } 
        duedate.addFormEvent("onBlur", "JavaScript:validateDate(this);");
        if (scheduledTask.getCompletionDate() == null && (
          scheduledTask.getDueDate() == null || (
          scheduledTask.getDueDate() != null && Calendar.getInstance().after(scheduledTask.getDueDate()) && 
          !scheduledTask.getScheduledTaskStatus().equalsIgnoreCase("Done") && 
          !scheduledTask.getScheduledTaskStatus().equalsIgnoreCase("N/A") && 
          !scheduledTask.getScheduledTaskStatus().equalsIgnoreCase("Today"))))
          duedate.addFormEvent("style", "background-color: mistyrose"); 
        duedate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
        Day dayType = null;
        FormHidden dayTypeElement = new FormHidden("dayType" + i);
        dayTypeElement.setValue(MilestoneHelper.getDayType(selection.getCalendarGroup(), scheduledTask));
        dayTypeElement.setStartingValue(dayTypeElement.getStringValue());
        FormTextField complete = new FormTextField("completion" + i, "", false, 10);
        if (scheduledTask.getCompletionDate() == null && 
          scheduledTask.getScheduledTaskStatus().equalsIgnoreCase("today")) {
          scheduledTask.setCompletionDate(Calendar.getInstance());
        } else if ((scheduledTask.getCompletionDate() == null && 
          scheduledTask.getScheduledTaskStatus().equalsIgnoreCase("Done") && 
          !scheduledTask.getAllowMultCompleteDatesFlag() && 
          !MilestoneHelper.isUml(scheduledTask)) || 
          scheduledTask.getScheduledTaskStatus().equalsIgnoreCase("N/A")) {
          scheduledTask.setCompletionDate(MilestoneHelper.getDate("9/9/99"));
        } 
        if (scheduledTask.getCompletionDate() != null) {
          complete.setValue(MilestoneHelper.getFormatedDate(scheduledTask.getCompletionDate()));
          complete.setStartingValue(complete.getStringValue());
        } 
        complete.setLength(7);
        if (scheduledTask.getAllowMultCompleteDatesFlag() && scheduledTask.getMultCompleteDates() != null && scheduledTask.getMultCompleteDates().size() > 0) {
          complete.setReadOnly(true);
        } else {
          complete.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
          complete.addFormEvent("onBlur", "JavaScript:validateDate(this);");
        } 
        if (scheduledTask.getCompletionDate() != null && 
          !scheduledTask.getCompletionDate().equals(MilestoneHelper.getDate("9/9/99")) && 
          scheduledTask.getDueDate() != null && scheduledTask.getCompletionDate().after(scheduledTask.getDueDate()))
          complete.addFormEvent("style", "background-color: mistyrose"); 
        form.addElement(duedate);
        form.addElement(wksToRelease);
        form.addElement(complete);
        form.addElement(status);
        form.addElement(vendor);
        form.addElement(comments);
        form.addElement(dayTypeElement);
        int access = 0;
        if (scheduledTask.getOwner() != null) {
          access = ScheduleManager.getInstance().getTaskEditAccess(sessionUser, 
              scheduledTask.getOwner().getStructureID(), 
              familyAclHash);
          scheduleRights.add(new String(Integer.toString(access)));
        } else {
          scheduleRights.add("0");
        } 
        scheduledTask = null;
        status = null;
        vendor = null;
        comments = null;
      } 
    } 
    context.putDelivery("scheduleRights", scheduleRights);
    if (selection != null) {
      context.putDelivery("releaseWeek", MilestoneHelper.getReleaseWeekString(selection));
      ProductCategory productCategory = selection.getProductCategory();
      String productCategoryString = "";
      if (productCategory != null)
        productCategoryString = productCategory.getName(); 
      String typeConfig = String.valueOf(productCategoryString) + " / " + selection.getReleaseType().getName() + " / " + selection.getSelectionConfig().getSelectionConfigurationName() + " / " + selection.getSelectionSubConfig().getSelectionSubConfigurationName();
      context.putDelivery("typeConfig", typeConfig);
      context.putDelivery("selection", selection);
      selection.setSchedule(schedule);
    } 
    form.addElement(new FormHidden("OrderBy", "", true));
    form.addElement(new FormHidden("OrderTasksBy", "", true));
    if (selection != null) {
      String lastSchedUpdatedDateText = "";
      if (selection.getLastSchedUpdateDate() != null)
        lastSchedUpdatedDateText = MilestoneHelper.getCustomFormatedDate(selection.getLastSchedUpdateDate(), "M/d/yyyy hh:mm:ss a 'ET'"); 
      FormTextField lastSchedUpdatedDate = new FormTextField("lastSchedUpdatedDate", lastSchedUpdatedDateText, false, 50);
      form.addElement(lastSchedUpdatedDate);
      String lastUpdateUser = "";
      if (selection.getLastSchedUpdatingUser() != null)
        lastUpdateUser = selection.getLastSchedUpdatingUser().getName(); 
      context.putDelivery("lastSchedUpdateUser", lastUpdateUser);
      String autoCloseDateText = "";
      if (selection.getAutoCloseDate() != null)
        autoCloseDateText = MilestoneHelper.getCustomFormatedDate(selection.getAutoCloseDate(), "M/d/yyyy hh:mm:ss a 'ET'"); 
      FormTextField autoCloseDate = new FormTextField("autoCloseDate", autoCloseDateText, false, 40);
      form.addElement(autoCloseDate);
      FormTextField lastLegacyUpdateDate = new FormTextField("lastLegacyUpdateDate", false, 50);
      lastLegacyUpdateDate.setValue(MilestoneHelper.getCustomFormatedDate(selection.getLastLegacyUpdateDate(), "M/d/yyyy hh:mm:ss a 'ET'"));
      form.addElement(lastLegacyUpdateDate);
    } 
    context.putDelivery("templateName", SelectionManager.getInstance().getAssignedTemplateName(selection));
    if (context.getSessionValue("NOTEPAD_SCHEDULE_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_SCHEDULE_VISIBLE")); 
    return form;
  }
  
  protected Form buildFormWithTasks(Context context, Selection selection, Schedule schedule, Notepad notepadTasks, String command) {
    String filterProdType;
    User sessionUser = (User)context.getSessionValue("user");
    int secureLevel = getSchedulePermissions(selection, sessionUser);
    setButtonVisibilities(selection, sessionUser, context, secureLevel, command);
    Form form = new Form(this.application, "ScheduleForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    String selDepartment = "All";
    if ((String)context.getSessionValue("deptFilter") != null)
      selDepartment = (String)context.getSessionValue("deptFilter"); 
    FormDropDownMenu deptFilterDD = getDepartmentFilterDropDown("deptFilter", selDepartment, false, schedule, context);
    String[] values = deptFilterDD.getValueList();
    String[] menuText = deptFilterDD.getMenuTextList();
    values[0] = "All";
    menuText[0] = "All";
    deptFilterDD.setValueList(values);
    deptFilterDD.setMenuTextList(menuText);
    deptFilterDD.setTabIndex(1);
    deptFilterDD.addFormEvent("onChange", "Javascript:clickDeptFilter(this)");
    form.addElement(deptFilterDD);
    String filterString = "All";
    if ((String)context.getSessionValue("filter") != null)
      filterString = (String)context.getSessionValue("filter"); 
    if (selection != null && selection.getIsDigital()) {
      filterProdType = "All,Only Label Tasks,Only eCommerce Tasks";
    } else {
      filterProdType = "All,Only Label Tasks,Only UML Tasks";
    } 
    FormDropDownMenu filterDropdown = new FormDropDownMenu("filter", filterString, filterProdType, true);
    filterDropdown.addFormEvent("onChange", "Javascript:clickCurrentFilter(this)");
    form.addElement(filterDropdown);
    form.addElement(new FormHidden("OrderBy", "", true));
    form.addElement(new FormHidden("OrderTasksBy", "", true));
    Vector scheduleRights = new Vector();
    if (selection != null && schedule != null) {
      Hashtable familyAclHash = sessionUser.getAcl().getFamilyAccessHash();
      Vector scheduledTasks = schedule.getTasks();
      for (int i = 0; i < scheduledTasks.size(); i++) {
        ScheduledTask scheduledTask = (ScheduledTask)scheduledTasks.get(i);
        FormTextField duedate = new FormTextField("DueDate" + i, "", false, 10);
        duedate.setLength(7);
        FormTextField wksToRelease = new FormTextField("wksToRelease" + i, false, 5);
        wksToRelease.addFormEvent("onBlur", "Javascript:validateWks2Rel(this," + (
            new Integer(scheduledTask.getTaskWeeksToRelease())).toString() + "," + (
            new Integer(-10)).toString() + ")");
        wksToRelease.setLength(4);
        String wksToReleaseString = "";
        if (scheduledTask.getWeeksToRelease() >= 0) {
          wksToReleaseString = (scheduledTask.getDayOfTheWeek() != null) ? scheduledTask.getDayOfTheWeek().getDay() : "";
          wksToReleaseString = String.valueOf(wksToReleaseString) + " " + scheduledTask.getWeeksToRelease();
          wksToRelease.setValue(wksToReleaseString);
          wksToRelease.setStartingValue(wksToRelease.getStringValue());
        } else if (scheduledTask.getWeeksToRelease() == -10) {
          wksToRelease.setValue("SOL");
          wksToRelease.setStartingValue(wksToRelease.getStringValue());
        } 
        FormDropDownMenu status = new FormDropDownMenu("status" + i);
        status.setValue(scheduledTask.getScheduledTaskStatus());
        status.setStartingValue(status.getStringValue());
        if (scheduledTask.getScheduledTaskStatus().equals("Auto")) {
          status.setValueList("&nbsp;,Auto,Done,Hold,N/A,Pend,Today");
          status.setMenuTextList("&nbsp;,Auto,Done,Hold,N/A,Pend,Today");
        } else {
          status.setValueList("&nbsp;,Done,Hold,N/A,Pend,Today");
          status.setMenuTextList("&nbsp;,Done,Hold,N/A,Pend,Today");
        } 
        FormTextField vendor = new FormTextField("vendor" + i, false, 50);
        vendor.setValue(scheduledTask.getVendor());
        vendor.setStartingValue(vendor.getStringValue());
        vendor.setLength(7);
        vendor.addFormEvent("onDblClick", "showDetailData( this, 'vendorLayer', 'vendorText' )");
        FormHidden comments = new FormHidden("comments" + i);
        if (scheduledTask.getComments() != null && !scheduledTask.getComments().equalsIgnoreCase("[none]") && !scheduledTask.getComments().equalsIgnoreCase("null") && 
          scheduledTask.getComments().length() > 0) {
          comments.setValue(scheduledTask.getComments());
        } else {
          comments.setValue("");
        } 
        if (scheduledTask.getDueDate() != null) {
          duedate.setValue(MilestoneHelper.getFormatedDate(scheduledTask.getDueDate()));
          duedate.setStartingValue(duedate.getStringValue());
        } 
        duedate.addFormEvent("onBlur", "JavaScript:validateDate(this);");
        if (scheduledTask.getCompletionDate() == null && (
          scheduledTask.getDueDate() == null || (
          scheduledTask.getDueDate() != null && Calendar.getInstance().after(scheduledTask.getDueDate()) && 
          !scheduledTask.getScheduledTaskStatus().equalsIgnoreCase("Done") && 
          !scheduledTask.getScheduledTaskStatus().equalsIgnoreCase("N/A") && 
          !scheduledTask.getScheduledTaskStatus().equalsIgnoreCase("Today"))))
          duedate.addFormEvent("style", "background-color: mistyrose"); 
        duedate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
        duedate.addFormEvent("onBlur", "JavaScript:validateDate(this);");
        Day dayType = null;
        FormHidden dayTypeElement = new FormHidden("dayType" + i);
        dayTypeElement.setValue(MilestoneHelper.getDayType(selection.getCalendarGroup(), scheduledTask));
        dayTypeElement.setStartingValue(dayTypeElement.getStringValue());
        FormTextField complete = new FormTextField("completion" + i, "", false, 10);
        if (scheduledTask.getCompletionDate() != null) {
          complete.setValue(MilestoneHelper.getFormatedDate(scheduledTask.getCompletionDate()));
          complete.setStartingValue(complete.getStringValue());
        } 
        complete.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
        complete.addFormEvent("onBlur", "JavaScript:validateDate(this);");
        complete.setLength(7);
        if (scheduledTask.getAllowMultCompleteDatesFlag() && 
          scheduledTask.getMultCompleteDates() != null && 
          scheduledTask.getMultCompleteDates().size() > 0) {
          complete.setReadOnly(true);
        } else {
          complete.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
          complete.addFormEvent("onBlur", "JavaScript:validateDate(this);");
        } 
        if (scheduledTask.getCompletionDate() == null && 
          scheduledTask.getScheduledTaskStatus().equalsIgnoreCase("Done") && 
          !scheduledTask.getAllowMultCompleteDatesFlag() && 
          !MilestoneHelper.isUml(scheduledTask))
          scheduledTask.setCompletionDate(MilestoneHelper.getDate("9/9/99")); 
        int access = 0;
        if (scheduledTask.getCompletionDate() != null && 
          !scheduledTask.getCompletionDate().equals(MilestoneHelper.getDate("9/9/99")) && 
          scheduledTask.getDueDate() != null && scheduledTask.getCompletionDate().after(scheduledTask.getDueDate()))
          complete.addFormEvent("style", "background-color: mistyrose"); 
        form.addElement(duedate);
        form.addElement(wksToRelease);
        form.addElement(complete);
        form.addElement(status);
        form.addElement(vendor);
        form.addElement(comments);
        form.addElement(dayTypeElement);
        if (scheduledTask.getOwner() != null) {
          access = ScheduleManager.getInstance().getTaskEditAccess(sessionUser, 
              scheduledTask.getOwner().getStructureID(), 
              familyAclHash);
          scheduleRights.add(new String(Integer.toString(access)));
        } else {
          scheduleRights.add("0");
        } 
        scheduledTask = null;
        status = null;
        vendor = null;
        comments = null;
      } 
    } 
    context.putDelivery("scheduleRights", scheduleRights);
    if (selection != null) {
      context.putDelivery("releaseWeek", MilestoneHelper.getReleaseWeekString(selection));
      ProductCategory productCategory = selection.getProductCategory();
      String productCategoryString = "";
      if (productCategory != null)
        productCategoryString = productCategory.getName(); 
      String typeConfig = String.valueOf(productCategoryString) + " / " + selection.getReleaseType().getName() + " / " + selection.getSelectionConfig().getSelectionConfigurationName() + " / " + selection.getSelectionSubConfig().getSelectionSubConfigurationName();
      context.putDelivery("typeConfig", typeConfig);
    } 
    Vector notepadPageContents = notepadTasks.getPageContents();
    Task task = null;
    FormCheckBox notepadCheckbox = null;
    for (int j = 0; j < notepadPageContents.size(); j++) {
      task = (Task)notepadPageContents.get(j);
      notepadCheckbox = new FormCheckBox(String.valueOf(task.getTaskID()), "", false, false);
      form.addElement(notepadCheckbox);
      task = null;
      notepadCheckbox = null;
    } 
    addTaskSearchElements(context, selection, form);
    addSelectionSearchElements(context, selection, form);
    if (selection != null) {
      String lastSchedUpdatedDateText = "";
      if (selection.getLastSchedUpdateDate() != null)
        lastSchedUpdatedDateText = MilestoneHelper.getCustomFormatedDate(selection.getLastSchedUpdateDate(), "M/d/yyyy hh:mm:ss a 'ET'"); 
      FormTextField lastSchedUpdatedDate = new FormTextField("lastSchedUpdatedDate", lastSchedUpdatedDateText, false, 50);
      form.addElement(lastSchedUpdatedDate);
      String lastUpdateUser = "";
      if (selection.getLastSchedUpdatingUser() != null)
        lastUpdateUser = selection.getLastSchedUpdatingUser().getName(); 
      context.putDelivery("lastSchedUpdateUser", lastUpdateUser);
      String autoCloseDateText = "";
      if (selection.getAutoCloseDate() != null)
        autoCloseDateText = MilestoneHelper.getCustomFormatedDate(selection.getAutoCloseDate(), "M/d/yyyy hh:mm:ss a 'ET'"); 
      FormTextField autoCloseDate = new FormTextField("autoCloseDate", autoCloseDateText, false, 40);
      form.addElement(autoCloseDate);
      FormTextField lastLegacyUpdateDate = new FormTextField("lastLegacyUpdateDate", false, 50);
      lastLegacyUpdateDate.setValue(MilestoneHelper.getCustomFormatedDate(selection.getLastLegacyUpdateDate(), "M/d/yyyy hh:mm:ss a 'ET'"));
      form.addElement(lastLegacyUpdateDate);
    } 
    form.addElement(new FormHidden("OrderTasksBy", "", true));
    context.putDelivery("templateName", SelectionManager.getInstance().getAssignedTemplateName(selection));
    if (context.getSessionValue("NOTEPAD_SCHEDULE_TASKS_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_SCHEDULE_TASKS_VISIBLE")); 
    return form;
  }
  
  protected Form addSelectionSearchElements(Context context, Selection selection, Form form) {
    context.putDelivery("selectionArrays", String.valueOf(Cache.getJavaScriptConfigArray("")) + Cache.getJavaScriptSubConfigArray("") + " " + ReleasingFamily.getJavaScriptCorporateArrayReleasingFamilySearch(context));
    FormCheckBox showAllSearch = new FormCheckBox("ShowAllSearch", "", "", false);
    showAllSearch.setId("ShowAllSearch");
    form.addElement(showAllSearch);
    Vector companies = MilestoneHelper.getUserCompanies(context);
    Vector labels = MilestoneHelper.getUserLabels(companies);
    labels = MilestoneHelper.removeUnusedCSO(labels, context, -1);
    FormDropDownMenu labelSearch = MilestoneHelper.getCorporateStructureDropDownDuplicates("LabelSearch", labels, "", false, true, false);
    labelSearch.setId("LabelSearch");
    form.addElement(labelSearch);
    Vector searchCompanies = null;
    searchCompanies = MilestoneHelper.getUserCompanies(context);
    searchCompanies = MilestoneHelper.removeUnusedCSO(searchCompanies, context, -1);
    FormDropDownMenu companySearch = MilestoneHelper.getCorporateStructureDropDown("CompanySearch", searchCompanies, "", false, true);
    companySearch.setId("CompanySearch");
    companySearch.addFormEvent("onChange", "return(clickCompanySearch(this))");
    form.addElement(companySearch);
    Vector labelContacts = SelectionManager.getLabelContactsExcludeUml(context);
    FormDropDownMenu searchContact = MilestoneHelper.getContactsDropDown(context, "ContactSearch", labelContacts, null, true);
    form.addElement(searchContact);
    Vector families = SelectionHandler.filterCSO(ReleasingFamily.getUserReleasingFamiliesVectorOfFamilies(context));
    FormDropDownMenu Family = MilestoneHelper.getCorporateStructureDropDown("FamilySearch", families, "-1", false, true);
    Family.addFormEvent("onChange", "return(clickFamilySearch(this))");
    Family.setId("FamilySearch");
    form.addElement(Family);
    Vector environments = MilestoneHelper.getUserEnvironments(context);
    Vector myCompanies = MilestoneHelper.getUserCompanies(context);
    environments = SelectionHandler.filterSelectionEnvironments(myCompanies);
    environments = MilestoneHelper.removeUnusedCSO(environments, context, -1);
    FormDropDownMenu envMenu = MilestoneHelper.getCorporateStructureDropDown("EnvironmentSearch", environments, "-1", false, true);
    envMenu.addFormEvent("onChange", "return(clickEnvironmentSearch(this))");
    envMenu.setId("EnvironmentSearch");
    form.addElement(envMenu);
    FormTextField streetDateSearch = new FormTextField("StreetDateSearch", "", false, 14, 10);
    streetDateSearch.addFormEvent("onBlur", "JavaScript:removeSpacesInDate(document.forms[0].StreetDateSearch.value,this)");
    streetDateSearch.setId("StreetDateSearch");
    form.addElement(streetDateSearch);
    FormTextField streetEndDateSearch = new FormTextField("StreetEndDateSearch", "", false, 14, 10);
    streetEndDateSearch.addFormEvent("onBlur", "JavaScript:removeSpacesInDate(document.forms[0].StreetEndDateSearch.value,this)");
    streetEndDateSearch.setId("StreetEndDateSearch");
    form.addElement(streetEndDateSearch);
    String[] dvalues = new String[3];
    dvalues[0] = "physical";
    dvalues[1] = "digital";
    dvalues[2] = "both";
    String[] dlabels = new String[3];
    dlabels[0] = "Physical";
    dlabels[1] = "Digital";
    dlabels[2] = "Both";
    FormRadioButtonGroup prodType = new FormRadioButtonGroup("ProdType", "both", dvalues, dlabels, false);
    prodType.addFormEvent("onClick", "buildSearchConfigs(this)");
    form.addElement(prodType);
    Vector searchConfigs = null;
    searchConfigs = Cache.getSelectionConfigs();
    FormDropDownMenu configSearch = MilestoneHelper.getSelectionConfigurationDropDown("ConfigSearch", "", false);
    configSearch.setId("ConfigSearch");
    configSearch.addFormEvent("onChange", "buildSearchSubConfigs(this.selectedIndex)");
    form.addElement(configSearch);
    FormDropDownMenu subconfigSearch = new FormDropDownMenu("SubconfigSearch", "");
    subconfigSearch.setId("SubconfigSearch");
    subconfigSearch.setEnabled(false);
    form.addElement(subconfigSearch);
    FormTextField upcSearch = new FormTextField("UPCSearch", "", false, 20, 20);
    upcSearch.setId("UPCSearch");
    form.addElement(upcSearch);
    FormTextField prefixSearch = new FormTextField("PrefixSearch", "", false, 6, 5);
    prefixSearch.setId("PrefixSearch");
    form.addElement(prefixSearch);
    FormTextField selectionSearch = new FormTextField("SelectionSearch", "", false, 12, 20);
    selectionSearch.setId("SelectionSearch");
    selectionSearch.setClassName("ctrlMedium");
    form.addElement(selectionSearch);
    FormTextField titleSearch = new FormTextField("TitleSearch", "", false, 20);
    titleSearch.setId("TitleSearch");
    form.addElement(titleSearch);
    FormTextField artistSearch = new FormTextField("ArtistSearch", "", false, 20);
    artistSearch.setId("ArtistSearch");
    form.addElement(artistSearch);
    FormTextArea Comments = new FormTextArea("comments", "", false, 6, 44, "virtual");
    Comments.setId("comments");
    Comments.addFormEvent("onBlur", "Javascript:checkField(this)");
    form.addElement(Comments);
    FormTextArea viewComments = new FormTextArea("viewComments", "", false, 2, 44, "virtual");
    viewComments.setId("viewComments");
    form.addElement(viewComments);
    String releaseCommentString = "";
    if (selection != null && selection.getSelectionComments() != null)
      releaseCommentString = selection.getSelectionComments(); 
    FormTextArea releaseComment = new FormTextArea("releaseComment", releaseCommentString, false, 4, 44, "virtual");
    releaseComment.addFormEvent("onBlur", "Javascript:checkField(this)");
    form.addElement(releaseComment);
    String holdReasonString = "";
    if (selection != null && selection.getHoldReason() != null)
      holdReasonString = selection.getHoldReason(); 
    FormTextArea holdReason = new FormTextArea("holdReason", holdReasonString, false, 2, 44, "virtual");
    form.addElement(holdReason);
    FormTextField projectIDSearch = new FormTextField("ProjectIDSearch", "", false, 20);
    projectIDSearch.setId("ProjectIDSearch");
    form.addElement(projectIDSearch);
    SelectionHandler.getUserPreferences(form, context);
    return form;
  }
  
  protected void addTemplateSearchElements(Context context, Selection selection, Form form) {
    FormTextField templateNameSearch = new FormTextField("TemplateNameSearch", "", false, 20);
    templateNameSearch.setId("TemplateNameSearch");
    templateNameSearch.setTabIndex(1);
    form.addElement(templateNameSearch);
    FormDropDownMenu configuration = MilestoneHelper.getSelectionConfigurationDropDown("TemplateConfigurationSearch", "", false, selection.getIsDigital() ? 1 : 0);
    configuration.setTabIndex(2);
    form.addElement(configuration);
    FormDropDownMenu productLine = MilestoneHelper.getLookupDropDown("TemplateProductLineSearch", Cache.getProductCategories(selection.getIsDigital() ? 1 : 0), "", false, true);
    productLine.setTabIndex(3);
    form.addElement(productLine);
    FormCheckBox showAllSearch = new FormCheckBox("ShowAllSearchSource", "", "", false);
    showAllSearch.setId("ShowAllSearchSource");
    form.addElement(showAllSearch);
    Vector companies = MilestoneHelper.getUserCompanies(context);
    Vector labels = MilestoneHelper.getUserLabels(companies);
    FormDropDownMenu labelSearch = MilestoneHelper.getCorporateStructureDropDown("LabelSearchSource", labels, "", false, true);
    labelSearch.setId("LabelSearchSource");
    form.addElement(labelSearch);
    FormTextField streetDateSearch = new FormTextField("StreetDateSearchSource", "", false, 12, 10);
    streetDateSearch.setId("StreetDateSearchSource");
    form.addElement(streetDateSearch);
    FormTextField upcSearch = new FormTextField("UPCSearchSource", "", false, 12, 10);
    upcSearch.setId("UPCSearchSource");
    form.addElement(upcSearch);
    FormTextField prefixSearch = new FormTextField("PrefixSearchSource", "", false, 12, 12);
    prefixSearch.setId("PrefixSearchSource");
    form.addElement(prefixSearch);
    FormTextField selectionSearch = new FormTextField("SelectionSearchSource", "", false, 12, 10);
    selectionSearch.setId("SelectionSearchSource");
    form.addElement(selectionSearch);
    FormTextField titleSearch = new FormTextField("TitleSearchSource", "", false, 20);
    titleSearch.setId("TitleSearchSource");
    form.addElement(titleSearch);
    FormTextField artistSearch = new FormTextField("ArtistSearchSource", "", false, 20);
    artistSearch.setId("ArtistSearchSource");
    form.addElement(artistSearch);
  }
  
  protected void addTaskSearchElements(Context context, Selection selection, Form form) {
    User sessionUser = (User)context.getSessionValue("user");
    FormTextField TaskNameSearch = new FormTextField("TaskNameSearch", "", false, 20);
    TaskNameSearch.setId("TaskNameSearch");
    TaskNameSearch.setTabIndex(1);
    form.addElement(TaskNameSearch);
    FormRadioButtonGroup KeyTaskSearch = new FormRadioButtonGroup("KeyTaskSearch", "", "Yes,No", false);
    KeyTaskSearch.setTabIndex(2);
    form.addElement(KeyTaskSearch);
    Vector families = MilestoneHelper.getNonSecureUserFamilies(context);
    FormDropDownMenu TaskOwnerSearch = MilestoneHelper.getCorporateStructureDropDown("TaskOwnerSearch", families, "0", false, true);
    TaskOwnerSearch.setTabIndex(3);
    form.addElement(TaskOwnerSearch);
    Vector deptList = MilestoneHelper.getDepartmentList();
    FormDropDownMenu TaskDepartmentSearch = MilestoneHelper.getDepartmentDropDown("TaskDepartmentSearch", "", false);
    TaskDepartmentSearch.setTabIndex(4);
    form.addElement(TaskDepartmentSearch);
  }
  
  public Notepad getTaskNotepad(Context context, int userId, int releaseId) {
    Vector contents = new Vector();
    if (MilestoneHelper.getNotepadFromSession(2, context) != null) {
      Notepad notepad = MilestoneHelper.getNotepadFromSession(2, context);
      if (notepad.getAllContents() == null) {
        contents = ScheduleManager.getInstance().getScheduleTaskNotepadList(releaseId, userId, notepad, context);
        notepad.setAllContents(contents);
      } 
      return notepad;
    } 
    String[] columnNames = { "Task Name", "Wks to Rls", "Own", "Dpt" };
    contents = ScheduleManager.getInstance().getScheduleTaskNotepadList(releaseId, userId, null, context);
    return new Notepad(contents, 0, 7, "Tasks", 2, columnNames);
  }
  
  private boolean editSave(Dispatcher dispatcher, Context context, String command, int screen) {
    Selection selection = (Selection)context.getSessionValue("Selection");
    Schedule schedule = selection.getSchedule();
    User user = (User)context.getSessionValue("user");
    Notepad notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 1);
    Notepad notepadTasks = getTaskNotepad(context, user.getUserId(), selection.getSelectionID());
    SelectionManager.getInstance().updateTemplateId(selection, user);
    Form form = null;
    if (screen == 0) {
      form = buildForm(context, selection, schedule, command);
    } else {
      form = buildFormWithTasks(context, selection, schedule, notepadTasks, command);
    } 
    if (ScheduleManager.getInstance().isTimestampValid(schedule)) {
      form.setValues(context);
      Vector tasks = schedule.getTasks();
      Vector precache = new Vector();
      ScheduledTask task = null;
      for (int i = 0; i < tasks.size(); i++) {
        task = (ScheduledTask)tasks.get(i);
        int originalWTR = task.getTaskWeeksToRelease();
        String weeksTR = form.getStringValue("wksToRelease" + i);
        int index = weeksTR.lastIndexOf(" ");
        String day = "";
        String weeks = "0";
        if (weeksTR.equalsIgnoreCase("sol")) {
          if (originalWTR == -10) {
            weeks = String.valueOf(-10);
            day = "9";
          } else {
            weeks = String.valueOf(task.getWeeksToRelease());
            day = String.valueOf(task.getDayOfTheWeek().getDayID());
          } 
        } else if (index > 0) {
          day = weeksTR.substring(0, index);
          day = day.trim();
          weeks = weeksTR.substring(index, weeksTR.length());
          weeks = weeks.trim();
        } else if (weeksTR.length() > 0) {
          try {
            weeks = weeksTR.trim();
            int weeksToRelease = Integer.parseInt(weeks);
            weeks = String.valueOf(weeksToRelease);
            day = "D";
          } catch (NumberFormatException e) {
            weeks = "0";
          } 
        } 
        String due = form.getStringValue("DueDate" + i);
        String complete = form.getStringValue("Completion" + i);
        String status = form.getStringValue("status" + i);
        String vendor = form.getStringValue("vendor" + i);
        String comments = form.getStringValue("comments" + i);
        task.setWeeksToRelease(Integer.parseInt(weeks));
        task.setDayOfTheWeek(new Day(day));
        if (due.length() > 0) {
          due = due.trim();
          if (task.getDueDate() != null && !due.equalsIgnoreCase(MilestoneHelper.getFormatedDate(task.getDueDate()))) {
            task.setWeeksToRelease(-1);
            task.setDayOfTheWeek(null);
          } else if (task.getDueDate() == null) {
            task.setWeeksToRelease(-1);
            task.setDayOfTheWeek(null);
          } 
          task.setDueDate(MilestoneHelper.getDate(due));
        } else {
          if (task.getDueDate() != null) {
            task.setWeeksToRelease(-1);
            task.setDayOfTheWeek(null);
          } 
          task.setDueDate(null);
        } 
        if (complete.length() > 0) {
          task.setCompletionDate(MilestoneHelper.getDate(complete));
        } else {
          task.setCompletionDate(null);
        } 
        if (status.equalsIgnoreCase("today") && complete.length() == 0) {
          task.setCompletionDate(Calendar.getInstance());
        } else if (task.getCompletionDate() == null && ((
          status.equalsIgnoreCase("done") && 
          !task.getAllowMultCompleteDatesFlag() && 
          !MilestoneHelper.isUml(task)) || 
          status.equalsIgnoreCase("n/a"))) {
          task.setCompletionDate(MilestoneHelper.getDate("9/9/99"));
        } 
        task.setScheduledTaskStatus(status);
        task.setVendor(vendor);
        task.setComments(comments);
        precache.add(task);
      } 
      schedule.setTasks(precache);
      String releaseComments = form.getStringValue("releaseComment");
      selection.setComments(releaseComments);
    } else {
      context.putDelivery("AlertMessage", "The record is unavailable for editing at this time.  Please refresh your screen and edit it at a later time.");
    } 
    form.addElement(new FormHidden("cmd", "schedule-editor", true));
    form.addElement(new FormHidden("OrderTasksBy", "", true));
    schedule.setNew(isNewSchedule(schedule));
    if (!form.isUnchanged() || schedule.isNew()) {
      FormValidation formValidation = form.validate();
      if (formValidation.isGood()) {
        Schedule savedSchedule = ScheduleManager.getInstance().saveSchedule(selection, schedule, user);
        selection.setSchedule(savedSchedule);
        SelectionManager.getInstance().updateComment(selection);
        String sessionFilter = (String)context.getSessionValue("filter");
        if (sessionFilter != null && sessionFilter.length() > 0)
          selection.setSchedule(ScheduleManager.getInstance().filterSchedule(schedule, sessionFilter)); 
        sessionFilter = (String)context.getSessionValue("deptFilter");
        if (sessionFilter != null && sessionFilter.length() > 0)
          selection.setSchedule(ScheduleManager.getInstance().deptFilterSchedule(selection.getSchedule(), sessionFilter, context)); 
        int sortType = -1;
        if (context.getSessionValue("ScheduleTaskSort") != null)
          sortType = Integer.parseInt((String)context.getSessionValue("ScheduleTaskSort")); 
        if (schedule != null && schedule.getTasks() != null)
          savedSchedule.setTasks(sortScreenScheduledTaskVector(sortType, savedSchedule.getTasks())); 
        context.putSessionValue("Schedule", savedSchedule);
        selection.setSchedule(savedSchedule);
        context.putSessionValue("Selection", selection);
        context.putDelivery("Form", form);
        if (screen == 0)
          return edit(dispatcher, context, command); 
        return editTasks(dispatcher, context, command);
      } 
      context.putDelivery("FormValidation", formValidation);
    } 
    form.addElement(new FormHidden("OrderBy", "", true));
    context.putDelivery("Form", form);
    if (screen == 0)
      return context.includeJSP("schedule-editor.jsp"); 
    return context.includeJSP("schedule-task-editor.jsp");
  }
  
  private boolean recalc(Dispatcher dispatcher, Context context, String command, int screen) {
    User user = (User)context.getSessionValue("user");
    Selection selection = (Selection)context.getSessionValue("Selection");
    Schedule schedule = null;
    if (selection != null) {
      int secureLevel = getSchedulePermissions(selection, user);
      setButtonVisibilities(selection, user, context, secureLevel, command);
      schedule = ScheduleManager.getInstance().getSchedule(selection.getSelectionID());
      ScheduleManager.getInstance().recalculateDueDates(schedule, selection);
      selection.setSchedule(schedule);
      context.putSessionValue("Schedule", schedule);
      context.putSessionValue("Selection", selection);
      Notepad notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 1);
      notepad.setSelected(selection);
      MilestoneHelper.putNotepadIntoSession(notepad, context);
      Notepad notepadTasks = getTaskNotepad(context, user.getUserId(), selection.getSelectionID());
      Form form = null;
      if (screen == 0) {
        form = buildForm(context, selection, schedule, command);
      } else {
        form = buildFormWithTasks(context, selection, schedule, notepadTasks, command);
      } 
      form.addElement(new FormHidden("cmd", "schedule-editor", true));
      form.addElement(new FormHidden("OrderTasksBy", "", true));
      context.putDelivery("Form", form);
      context.putSessionValue("Selection", selection);
      if (schedule == null || (schedule.getTasks().size() == 0 && isNewSchedule(schedule))) {
        suggestedTemplate(form, selection, context, command);
        return false;
      } 
      int sortType = -1;
      if (context.getSessionValue("ScheduleTaskSort") != null)
        sortType = Integer.parseInt((String)context.getSessionValue("ScheduleTaskSort")); 
      if (schedule != null && schedule.getTasks() != null)
        schedule.setTasks(sortScreenScheduledTaskVector(sortType, schedule.getTasks())); 
    } 
    return editSave(dispatcher, context, command, screen);
  }
  
  private boolean recalcAll(Dispatcher dispatcher, Context context, String command, int screen) {
    User user = (User)context.getSessionValue("user");
    Selection selection = (Selection)context.getSessionValue("Selection");
    Schedule schedule = null;
    if (selection != null) {
      int secureLevel = getSchedulePermissions(selection, user);
      setButtonVisibilities(selection, user, context, secureLevel, command);
      schedule = ScheduleManager.getInstance().getSchedule(selection.getSelectionID());
      ScheduleManager.getInstance().recalculateAllDueDates(schedule, selection);
      selection.setSchedule(schedule);
      context.putSessionValue("Schedule", schedule);
      context.putSessionValue("Selection", selection);
      Notepad notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 1);
      notepad.setSelected(selection);
      MilestoneHelper.putNotepadIntoSession(notepad, context);
      Notepad notepadTasks = getTaskNotepad(context, user.getUserId(), selection.getSelectionID());
      Form form = null;
      if (screen == 0) {
        form = buildForm(context, selection, schedule, command);
      } else {
        form = buildFormWithTasks(context, selection, schedule, notepadTasks, command);
      } 
      form.addElement(new FormHidden("cmd", "schedule-editor", true));
      form.addElement(new FormHidden("OrderTasksBy", "", true));
      context.putDelivery("Form", form);
      context.putSessionValue("Selection", selection);
      if (schedule == null || (schedule.getTasks().size() == 0 && isNewSchedule(schedule))) {
        suggestedTemplate(form, selection, context, command);
        return false;
      } 
      int sortType = -1;
      if (context.getSessionValue("ScheduleTaskSort") != null)
        sortType = Integer.parseInt((String)context.getSessionValue("ScheduleTaskSort")); 
      if (schedule != null && schedule.getTasks() != null)
        schedule.setTasks(sortScreenScheduledTaskVector(sortType, schedule.getTasks())); 
    } 
    return editSave(dispatcher, context, command, screen);
  }
  
  private boolean close(Dispatcher dispatcher, Context context, String command, int screen) {
    User user = (User)context.getSessionValue("user");
    Selection selection = (Selection)context.getSessionValue("Selection");
    Schedule schedule = null;
    if (selection != null) {
      int secureLevel = getSchedulePermissions(selection, user);
      setButtonVisibilities(selection, user, context, secureLevel, command);
      schedule = ScheduleManager.getInstance().getSchedule(selection.getSelectionID());
      boolean closed = ScheduleManager.getInstance().closeSchedule(schedule, selection, user);
      Notepad notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 1);
      if (!closed) {
        context.putDelivery("AlertMessage", new String("All UML/eCommerce tasks must have completion dates before a selection can be closed."));
        notepad.setSelected(selection);
        selection.setSchedule(schedule);
        context.putSessionValue("Schedule", schedule);
        context.putSessionValue("Selection", selection);
      } else {
        notepad.setAllContents(null);
        notepad.setSelected(null);
        if (screen == 0)
          return edit(dispatcher, context, command); 
        return editTasks(dispatcher, context, command);
      } 
      MilestoneHelper.putNotepadIntoSession(notepad, context);
      Notepad notepadTasks = getTaskNotepad(context, user.getUserId(), selection.getSelectionID());
      Form form = null;
      if (screen == 0) {
        form = buildForm(context, selection, schedule, command);
      } else {
        form = buildFormWithTasks(context, selection, schedule, notepadTasks, command);
      } 
      form.addElement(new FormHidden("cmd", "schedule-editor", true));
      form.addElement(new FormHidden("OrderTasksBy", "", true));
      context.putDelivery("Form", form);
      if (schedule == null || (schedule.getTasks().size() == 0 && isNewSchedule(schedule))) {
        suggestedTemplate(form, selection, context, command);
        return false;
      } 
      int sortType = -1;
      if (context.getSessionValue("ScheduleTaskSort") != null)
        sortType = Integer.parseInt((String)context.getSessionValue("ScheduleTaskSort")); 
      if (schedule != null && schedule.getTasks() != null)
        schedule.setTasks(sortScreenScheduledTaskVector(sortType, schedule.getTasks())); 
    } 
    if (screen == 0)
      return context.includeJSP("schedule-editor.jsp"); 
    return context.includeJSP("schedule-task-editor.jsp");
  }
  
  private boolean clear(Dispatcher dispatcher, Context context, String command, int screen) {
    User user = (User)context.getSessionValue("user");
    Selection selection = new Selection();
    selection = MilestoneHelper.getScreenSelection(context);
    Schedule schedule = null;
    int secureLevel = getSchedulePermissions(selection, user);
    setButtonVisibilities(selection, user, context, secureLevel, command);
    if (selection != null)
      schedule = ScheduleManager.getInstance().getSchedule(selection.getSelectionID()); 
    if (schedule != null)
      ScheduleManager.getInstance().clearDates(schedule); 
    selection.setSchedule(schedule);
    context.putSessionValue("Schedule", schedule);
    context.putSessionValue("selection", selection);
    Notepad notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 1);
    notepad.setSelected(selection);
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Notepad notepadTasks = getTaskNotepad(context, user.getUserId(), selection.getSelectionID());
    Form form = null;
    if (screen == 0) {
      form = buildForm(context, selection, schedule, command);
    } else {
      form = buildFormWithTasks(context, selection, schedule, notepadTasks, command);
    } 
    form.addElement(new FormHidden("cmd", "schedule-editor", true));
    form.addElement(new FormHidden("OrderTasksBy", "", true));
    context.putDelivery("Form", form);
    context.putSessionValue("selection", selection);
    if (schedule == null || (schedule.getTasks().size() == 0 && isNewSchedule(schedule))) {
      suggestedTemplate(form, selection, context, command);
      return false;
    } 
    int sortType = -1;
    if (context.getSessionValue("ScheduleTaskSort") != null)
      sortType = Integer.parseInt((String)context.getSessionValue("ScheduleTaskSort")); 
    if (schedule != null && schedule.getTasks() != null)
      schedule.setTasks(sortScreenScheduledTaskVector(sortType, schedule.getTasks())); 
    if (screen == 0)
      return context.includeJSP("schedule-editor.jsp"); 
    return context.includeJSP("schedule-task-editor.jsp");
  }
  
  private boolean editTasks(Dispatcher dispatcher, Context context, String command) {
    Selection selection = null;
    User user = (User)context.getSessionValue("user");
    selection = (Selection)context.getSessionValue("Selection");
    int secureLevel = getSchedulePermissions(selection, user);
    setButtonVisibilities(selection, user, context, secureLevel, command);
    if (selection != null) {
      Vector contents = new Vector();
      Notepad notepadTasks = getTaskNotepad(context, user.getUserId(), selection.getSelectionID());
      MilestoneHelper.putNotepadIntoSession(notepadTasks, context);
      Schedule schedule = null;
      schedule = ScheduleManager.getInstance().getSchedule(selection.getSelectionID());
      selection.setSchedule(schedule);
      setDeptFilter(schedule, context);
      String sessionFilter = (String)context.getSessionValue("filter");
      if (sessionFilter != null && sessionFilter.length() > 0)
        selection.setSchedule(ScheduleManager.getInstance().filterSchedule(selection.getSchedule(), sessionFilter)); 
      sessionFilter = (String)context.getSessionValue("deptFilter");
      if (sessionFilter != null && sessionFilter.length() > 0)
        selection.setSchedule(ScheduleManager.getInstance().deptFilterSchedule(selection.getSchedule(), sessionFilter, context)); 
      int sortType = -1;
      if (context.getSessionValue("ScheduleTaskSort") != null)
        sortType = Integer.parseInt((String)context.getSessionValue("ScheduleTaskSort")); 
      if (schedule != null && schedule.getTasks() != null)
        schedule.setTasks(sortScreenScheduledTaskVector(sortType, schedule.getTasks())); 
      Form form = buildFormWithTasks(context, selection, schedule, notepadTasks, command);
      form.addElement(new FormHidden("cmd", "schedule-task-editor", true));
      form.addElement(new FormHidden("OrderTasksBy", "", true));
      context.putDelivery("Form", form);
      if (schedule == null || (schedule.getTasks().size() == 0 && isNewSchedule(schedule))) {
        suggestedTemplate(form, selection, context, command);
        return true;
      } 
      context.putDelivery("templateName", SelectionManager.getInstance().getAssignedTemplateName(selection));
      context.putSessionValue("Selection", selection);
      return context.includeJSP("schedule-task-editor.jsp");
    } 
    return dispatcher.redispatch(context, "schedule-editor");
  }
  
  private boolean addTask(Dispatcher dispatcher, Context context, String command) {
    Selection selection = new Selection();
    User user = (User)context.getSessionValue("user");
    selection = MilestoneHelper.getScreenSelection(context);
    int secureLevel = getSchedulePermissions(selection, user);
    setButtonVisibilities(selection, user, context, secureLevel, command);
    Notepad notepadTasks = getTaskNotepad(context, user.getUserId(), selection.getSelectionID());
    MilestoneHelper.putNotepadIntoSession(notepadTasks, context);
    Schedule schedule = null;
    if (selection != null)
      schedule = ScheduleManager.getInstance().getSchedule(selection.getSelectionID()); 
    Form form = buildFormWithTasks(context, selection, schedule, notepadTasks, command);
    form.setValues(context);
    Task task = null;
    Vector notepadPageContents = notepadTasks.getPageContents();
    for (int j = 0; j < notepadPageContents.size(); j++) {
      task = (Task)notepadPageContents.get(j);
      if (((FormCheckBox)form.getElement(String.valueOf(task.getTaskID()))).isChecked())
        if (task.getActiveFlag())
          ScheduleManager.getInstance().addTask(task, user, selection);  
      task = null;
    } 
    schedule = ScheduleManager.getInstance().getSchedule(selection.getSelectionID());
    selection.setSchedule(schedule);
    context.putSessionValue("Selection", selection);
    notepadTasks.setAllContents(null);
    notepadTasks = getTaskNotepad(context, user.getUserId(), selection.getSelectionID());
    notepadTasks.goToSelectedPage();
    return editTasks(dispatcher, context, command);
  }
  
  private boolean deleteTaskInTaskEditor(Dispatcher dispatcher, Context context, String command) {
    String taskId = context.getRequestValue("taskId");
    Selection selection = MilestoneHelper.getScreenSelection(context);
    User user = (User)context.getSessionValue("user");
    int secureLevel = getSchedulePermissions(selection, user);
    setButtonVisibilities(selection, user, context, secureLevel, command);
    if (selection != null)
      ScheduleManager.getInstance().deleteTask(taskId, selection, user); 
    Notepad notepadTasks = getTaskNotepad(context, user.getUserId(), selection.getSelectionID());
    notepadTasks.setAllContents(null);
    notepadTasks = getTaskNotepad(context, user.getUserId(), selection.getSelectionID());
    notepadTasks.goToSelectedPage();
    return editTasks(dispatcher, context, command);
  }
  
  private boolean deleteTask(Dispatcher dispatcher, Context context, String command) {
    String taskId = context.getRequestValue("taskId");
    User user = (User)context.getSessionValue("user");
    Selection selection = MilestoneHelper.getScreenSelection(context);
    if (selection != null)
      ScheduleManager.getInstance().deleteTask(taskId, selection, user); 
    int secureLevel = getSchedulePermissions(selection, user);
    setButtonVisibilities(selection, user, context, secureLevel, command);
    context.removeSessionValue(MilestoneConstants.NOTEPAD_SESSION_NAMES[4]);
    return edit(dispatcher, context, command);
  }
  
  private boolean deleteAllTasks(Dispatcher dispatcher, Context context, String command, int screen) {
    Selection selection = (Selection)context.getSessionValue("Selection");
    boolean deleteAll = true;
    Schedule schedule = selection.getSchedule();
    if (schedule != null && schedule.getTasks().size() > 0) {
      Vector tasks = schedule.getTasks();
      for (int i = 0; i < tasks.size(); i++) {
        ScheduledTask scheduledTask = (ScheduledTask)tasks.get(i);
        if (scheduledTask.getCompletionDate() != null || 
          scheduledTask.getScheduledTaskStatus().length() > 0 || 
          scheduledTask.getComments().length() > 0 || 
          scheduledTask.getVendor().length() > 0) {
          deleteAll = false;
          context.putDelivery("AlertMessage", "All tasks can only be deleted when there is no data entered for any of the tasks on the schedule.");
        } 
      } 
      User user = (User)context.getSessionValue("user");
      if (selection != null && deleteAll)
        ScheduleManager.getInstance().deleteAllTasks(selection, user); 
      int secureLevel = getSchedulePermissions(selection, user);
      setButtonVisibilities(selection, user, context, secureLevel, command);
      context.removeSessionValue(MilestoneConstants.NOTEPAD_SESSION_NAMES[4]);
      selection.setTemplateId(-1);
      SelectionManager.getInstance().updateTemplateId(selection, user);
    } else {
      context.putDelivery("AlertMessage", "There are no tasks to delete. To delete this release, select the delete key on the Selection screen.");
    } 
    if (screen == 0)
      return edit(dispatcher, context, command); 
    return editTasks(dispatcher, context, command);
  }
  
  private boolean deleteAllTasksInTaskEditor(Dispatcher dispatcher, Context context, String command) {
    User user = (User)context.getSessionValue("user");
    Selection selection = MilestoneHelper.getScreenSelection(context);
    if (selection != null)
      ScheduleManager.getInstance().deleteAllTasks(selection, user); 
    int secureLevel = getSchedulePermissions(selection, user);
    setButtonVisibilities(selection, user, context, secureLevel, command);
    context.removeSessionValue(MilestoneConstants.NOTEPAD_SESSION_NAMES[4]);
    return editTasks(dispatcher, context, command);
  }
  
  private boolean suggestedTemplate(Form form, Selection selection, Context context, String command) {
    String filterProdType;
    addSelectionSearchElements(context, selection, form);
    User user = (User)context.getSessionValue("user");
    String filterString = "All";
    if ((String)context.getSessionValue("filter") != null)
      filterString = (String)context.getSessionValue("filter"); 
    if (selection != null && selection.getIsDigital()) {
      filterProdType = "All,Only Label Tasks,Only eCommerce Tasks";
    } else {
      filterProdType = "All,Only Label Tasks,Only UML Tasks";
    } 
    FormDropDownMenu filterDropdown = new FormDropDownMenu("filter", filterString, filterProdType, true);
    filterDropdown.addFormEvent("onChange", "Javascript:parent.top.bottomFrame.location = 'home?cmd=schedule-suggested-template'");
    filterDropdown.setId("filter");
    form.addElement(filterDropdown);
    Vector suggestedTemplates = ScheduleManager.getInstance().getSuggestedTemplates(user, selection, context);
    Template template = null;
    FormRadioButtonGroup templateRadioBox = null;
    FormTextField templateName = null;
    FormTextField templateOwner = null;
    context.putDelivery("suggestedTemplates", suggestedTemplates);
    for (int i = 0; i < suggestedTemplates.size(); i++) {
      template = (Template)suggestedTemplates.get(i);
      templateRadioBox = new FormRadioButtonGroup("TemplateRadio" + String.valueOf(i), "", String.valueOf(template.getTemplateID()), false);
      templateRadioBox.addFormEvent("onClick", "getTemplateTasks('" + template.getTemplateID() + "')");
      templateRadioBox.setId(String.valueOf(template.getTemplateID()));
      templateRadioBox.setShowLabels(false);
      form.addElement(templateRadioBox);
      templateName = new FormTextField("TemplateName" + String.valueOf(i), template.getTempateName(), false, 20);
      form.addElement(templateName);
      Family family = template.getOwner();
      int familyId = -1;
      if (family != null)
        familyId = family.getStructureID(); 
      templateOwner = new FormTextField("TemplateOwner" + String.valueOf(i), MilestoneHelper.getStructureName(familyId), false, 20);
      form.addElement(templateOwner);
      templateRadioBox = null;
      templateOwner = null;
      templateName = null;
    } 
    FormTextField templateNameSearch = new FormTextField("TemplateNameSearch", "", false, 20);
    templateNameSearch.setId("TemplateNameSearch");
    templateNameSearch.setTabIndex(1);
    form.addElement(templateNameSearch);
    FormDropDownMenu configuration = MilestoneHelper.getSelectionConfigurationDropDown("TemplateConfigurationSearch", "", false, selection.getIsDigital() ? 1 : 0);
    configuration.setTabIndex(2);
    form.addElement(configuration);
    FormDropDownMenu productLine = MilestoneHelper.getLookupDropDown("TemplateProductLineSearch", Cache.getProductCategories(selection.getIsDigital() ? 1 : 0), "", false, true);
    productLine.setTabIndex(3);
    form.addElement(productLine);
    FormCheckBox showAllSearch = new FormCheckBox("ShowAllSearchSource", "", "", false);
    showAllSearch.setId("ShowAllSearchSource");
    form.addElement(showAllSearch);
    Vector companies = MilestoneHelper.getUserCompanies(context);
    Vector labels = MilestoneHelper.getUserLabels(companies);
    FormDropDownMenu labelSearch = MilestoneHelper.getCorporateStructureDropDown("LabelSearchSource", labels, "", false, true);
    labelSearch.setId("LabelSearchSource");
    form.addElement(labelSearch);
    FormTextField streetDateSearch = new FormTextField("StreetDateSearchSource", "", false, 12, 10);
    streetDateSearch.setId("StreetDateSearchSource");
    form.addElement(streetDateSearch);
    FormTextField upcSearch = new FormTextField("UPCSearchSource", "", false, 12, 10);
    upcSearch.setId("UPCSearchSource");
    form.addElement(upcSearch);
    FormTextField prefixSearch = new FormTextField("PrefixSearchSource", "", false, 6, 5);
    prefixSearch.setId("PrefixSearchSource");
    form.addElement(prefixSearch);
    FormTextField selectionSearch = new FormTextField("SelectionSearchSource", "", false, 12, 10);
    selectionSearch.setId("SelectionSearchSource");
    form.addElement(selectionSearch);
    FormTextField titleSearch = new FormTextField("TitleSearchSource", "", false, 20);
    titleSearch.setId("TitleSearchSource");
    form.addElement(titleSearch);
    FormTextField artistSearch = new FormTextField("ArtistSearchSource", "", false, 20);
    artistSearch.setId("ArtistSearchSource");
    form.addElement(artistSearch);
    int secureLevel = getSchedulePermissions(selection, user);
    setButtonVisibilities(selection, user, context, secureLevel, command);
    form.addElement(new FormHidden("cmd", "schedule-editor", true));
    form.addElement(new FormHidden("OrderTasksBy", "", true));
    form.addElement(new FormHidden("OrderBy", "", true));
    context.putDelivery("Form", form);
    if (context.getSessionValue("NOTEPAD_SCHEDULE_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_SCHEDULE_VISIBLE")); 
    return context.includeJSP("schedule-suggested-template.jsp");
  }
  
  private boolean scheduleSelectionSearch(Dispatcher dispatcher, Context context, String command) {
    String filterProdType;
    Form form = new Form(this.application, "ScheduleSelectTemplateForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    Selection selection = new Selection();
    User user = (User)context.getSessionValue("user");
    Notepad notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 1);
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    selection = MilestoneHelper.getScreenSelection(context);
    addSelectionSearchElements(context, selection, form);
    addTemplateSearchElements(context, selection, form);
    form.setValues(context);
    String filterString = "All";
    if ((String)context.getSessionValue("filter") != null)
      filterString = (String)context.getSessionValue("filter"); 
    if (selection != null && selection.getIsDigital()) {
      filterProdType = "All,Only Label Tasks,Only eCommerce Tasks";
    } else {
      filterProdType = "All,Only Label Tasks,Only UML Tasks";
    } 
    FormDropDownMenu filterDropdown = new FormDropDownMenu("filter", filterString, filterProdType, true);
    filterDropdown.addFormEvent("onChange", "Javascript:parent.top.bottomFrame.location = 'home?cmd=schedule-editor'");
    filterDropdown.setId("filter");
    form.addElement(filterDropdown);
    String artist = form.getStringValue("ArtistSearchSource");
    String title = form.getStringValue("TitleSearchSource");
    String selectionString = form.getStringValue("SelectionSearchSource");
    String UPC = form.getStringValue("UPCSearchSource");
    String prefix = form.getStringValue("PrefixSearchSource");
    String streetDate = form.getStringValue("StreetDateSearchSource");
    String label = form.getStringValue("LabelSearchSource");
    Vector suggestedTemplates = ScheduleManager.getInstance().getScheduleSearch(user, selection, artist, title, selectionString, UPC, prefix, streetDate, label, context);
    Selection currentSelection = null;
    FormRadioButtonGroup selectionRadioBox = null;
    FormTextField selectionArtist = null;
    FormTextField selectionTitle = null;
    FormTextField selectionUpc = null;
    FormTextField selectionNo = null;
    FormTextField selectionStreet = null;
    context.putDelivery("suggestedTemplates", suggestedTemplates);
    for (int i = 0; i < suggestedTemplates.size(); i++) {
      currentSelection = (Selection)suggestedTemplates.get(i);
      selectionRadioBox = new FormRadioButtonGroup("SelectionRadio" + String.valueOf(i), "", String.valueOf(currentSelection.getSelectionID()), false);
      selectionRadioBox.addFormEvent("onClick", "getReleaseTasks(" + String.valueOf(currentSelection.getSelectionID()) + ")");
      selectionRadioBox.setId(String.valueOf(currentSelection.getSelectionID()));
      selectionRadioBox.setShowLabels(false);
      form.addElement(selectionRadioBox);
      String artistString = currentSelection.getArtist();
      selectionArtist = new FormTextField("selectionArtist" + String.valueOf(i), artistString, false, 20);
      form.addElement(selectionArtist);
      String titleString = currentSelection.getTitle();
      if (titleString.length() > 16)
        titleString = titleString.substring(0, 15); 
      selectionTitle = new FormTextField("selectionTitle" + String.valueOf(i), titleString, false, 20);
      form.addElement(selectionTitle);
      selectionUpc = new FormTextField("selectionUpc" + String.valueOf(i), currentSelection.getUpc(), false, 20);
      form.addElement(selectionUpc);
      selectionNo = new FormTextField("selectionNo" + String.valueOf(i), currentSelection.getSelectionNo(), false, 20);
      form.addElement(selectionNo);
      selectionStreet = new FormTextField("selectionStreet" + String.valueOf(i), MilestoneHelper.getFormatedDate(currentSelection.getStreetDate()), false, 20);
      form.addElement(selectionStreet);
      selectionArtist = null;
      selectionRadioBox = null;
      selectionTitle = null;
      selectionUpc = null;
      selectionNo = null;
      selectionStreet = null;
      currentSelection = null;
    } 
    FormTextField templateNameSearch = new FormTextField("TemplateNameSearch", "", false, 20);
    templateNameSearch.setId("TemplateNameSearch");
    templateNameSearch.setTabIndex(1);
    form.addElement(templateNameSearch);
    FormDropDownMenu configuration = MilestoneHelper.getSelectionConfigurationDropDown("TemplateConfigurationSearch", "", true);
    configuration.setTabIndex(2);
    form.addElement(configuration);
    FormDropDownMenu productLine = MilestoneHelper.getLookupDropDown("TemplateProductLineSearch", Cache.getProductCategories(), "", true, true);
    productLine.setTabIndex(3);
    form.addElement(productLine);
    FormCheckBox showAllSearch = new FormCheckBox("ShowAllSearchSource", "", "", false);
    showAllSearch.setId("ShowAllSearchSource");
    form.addElement(showAllSearch);
    Vector companies = MilestoneHelper.getUserCompanies(context);
    Vector labels = MilestoneHelper.getUserLabels(companies);
    FormDropDownMenu labelSearch = MilestoneHelper.getCorporateStructureDropDown("LabelSearchSource", labels, "", false, true);
    labelSearch.setId("LabelSearchSource");
    form.addElement(labelSearch);
    FormTextField streetDateSearch = new FormTextField("StreetDateSearchSource", "", false, 14, 10);
    streetDateSearch.setId("StreetDateSearchSource");
    form.addElement(streetDateSearch);
    FormTextField upcSearch = new FormTextField("UPCSearchSource", "", false, 15, 15);
    upcSearch.setId("UPCSearchSource");
    form.addElement(upcSearch);
    FormTextField prefixSearch = new FormTextField("PrefixSearchSource", "", false, 6, 5);
    prefixSearch.setId("PrefixSearchSource");
    form.addElement(prefixSearch);
    FormTextField selectionSearch = new FormTextField("SelectionSearchSource", "", false, 15, 15);
    selectionSearch.setId("SelectionSearchSource");
    form.addElement(selectionSearch);
    FormTextField titleSearch = new FormTextField("TitleSearchSource", "", false, 20);
    titleSearch.setId("TitleSearchSource");
    form.addElement(titleSearch);
    FormTextField artistSearch = new FormTextField("ArtistSearchSource", "", false, 20);
    artistSearch.setId("ArtistSearchSource");
    form.addElement(artistSearch);
    int secureLevel = getSchedulePermissions(selection, user);
    setButtonVisibilities(selection, user, context, secureLevel, command);
    if (context.getSessionValue("NOTEPAD_SCHEDULE_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_SCHEDULE_VISIBLE")); 
    if (selection != null) {
      context.putDelivery("releaseWeek", MilestoneHelper.getReleaseWeekString(selection));
      ProductCategory productCategory = selection.getProductCategory();
      String productCategoryString = "";
      if (productCategory != null)
        productCategoryString = productCategory.getName(); 
      String typeConfig = String.valueOf(productCategoryString) + " / " + selection.getReleaseType().getName() + " / " + selection.getSelectionConfig().getSelectionConfigurationName() + " / " + selection.getSelectionSubConfig().getSelectionSubConfigurationName();
      context.putDelivery("typeConfig", typeConfig);
    } 
    form.addElement(new FormHidden("cmd", "schedule-copy-release-schedule", true));
    form.addElement(new FormHidden("OrderTasksBy", "", true));
    form.addElement(new FormHidden("OrderBy", "", true));
    context.putDelivery("Form", form);
    return context.includeJSP("schedule-copy-release-schedule.jsp");
  }
  
  private boolean scheduleTaskSearch(Dispatcher dispatcher, Context context, String command) {
    Notepad notepad = MilestoneHelper.getNotepadFromSession(2, context);
    notepad.setAllContents(null);
    notepad.setSelected(null);
    ScheduleManager.getInstance().setTaskNotepadQuery(context, notepad);
    dispatcher.redispatch(context, "schedule-task-editor");
    return true;
  }
  
  private boolean sortScheduleScreenTasks(Dispatcher dispatcher, Context context) {
    int sort = Integer.parseInt(context.getParameter("OrderTasksBy"));
    context.putDelivery("ScheduleTaskSort", Integer.toString(sort));
    context.putSessionValue("ScheduleTaskSort", Integer.toString(sort));
    dispatcher.redispatch(context, "schedule-editor");
    return true;
  }
  
  private boolean sortScheduleTaskScreenTasks(Dispatcher dispatcher, Context context) {
    int sort = Integer.parseInt(context.getParameter("OrderTasksBy"));
    context.putDelivery("ScheduleTaskSort", Integer.toString(sort));
    context.putSessionValue("ScheduleTaskSort", Integer.toString(sort));
    dispatcher.redispatch(context, "schedule-task-editor");
    return true;
  }
  
  private boolean scheduleTaskSort(Dispatcher dispatcher, Context context) {
    int sort = Integer.parseInt(context.getParameter("OrderBy"));
    int releaseId = ((Selection)context.getSessionValue("Selection")).getSelectionID();
    int userId = ((User)context.getSessionValue("user")).getUserId();
    Notepad notepad = getTaskNotepad(context, userId, releaseId);
    if (notepad.getSearchQuery() == null || notepad.getSearchQuery().equals(""))
      notepad.setSearchQuery(ScheduleManager.getInstance().getDefaultTaskSearchQuery(releaseId)); 
    notepad.setOrderBy(" ORDER BY " + MilestoneConstants.SORT_TASK[sort]);
    notepad.setAllContents(null);
    notepad = getTaskNotepad(context, userId, releaseId);
    notepad.goToSelectedPage();
    dispatcher.redispatch(context, "schedule-task-editor");
    return true;
  }
  
  private boolean scheduleSelectionSort(Dispatcher dispatcher, Context context) {
    int userId = ((User)context.getSessionValue("user")).getUserId();
    Notepad notepad = SelectionManager.getInstance().getSelectionNotepad(context, userId, 0);
    NotepadSortOrder.getNotepadSortOrderFromSession(context).sortHelper(dispatcher, context, notepad);
    dispatcher.redispatch(context, "schedule-editor");
    return true;
  }
  
  private boolean templateSearch(Dispatcher dispatcher, Context context, String command) { return context.includeJSP("schedule-select-template.jsp"); }
  
  private boolean selectTemplate(Dispatcher dispatcher, Context context, String command) {
    String filterProdType;
    Form form = new Form(this.application, "ScheduleSelectTemplateForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    Selection selection = new Selection();
    User user = (User)context.getSessionValue("user");
    Notepad notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 1);
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    selection = MilestoneHelper.getScreenSelection(context);
    addSelectionSearchElements(context, selection, form);
    addTemplateSearchElements(context, selection, form);
    form.setValues(context);
    String templateNameSearch = form.getStringValue("TemplateNameSearch");
    String templateConfigSearch = form.getStringValue("TemplateConfigurationSearch").equals("0") ? "" : form.getStringValue("TemplateConfigurationSearch");
    String templateProductLineSearch = form.getStringValue("TemplateProductLineSearch").equals("0") ? "" : form.getStringValue("TemplateProductLineSearch");
    String filterString = "All";
    if ((String)context.getSessionValue("filter") != null)
      filterString = (String)context.getSessionValue("filter"); 
    if (selection != null && selection.getIsDigital()) {
      filterProdType = "All,Only Label Tasks,Only eCommerce Tasks";
    } else {
      filterProdType = "All,Only Label Tasks,Only UML Tasks";
    } 
    FormDropDownMenu filterDropdown = new FormDropDownMenu("filter", filterString, filterProdType, true);
    filterDropdown.addFormEvent("onChange", "Javascript:parent.top.bottomFrame.location = 'home?cmd=schedule-editor'");
    form.addElement(filterDropdown);
    Vector suggestedTemplates = ScheduleManager.getInstance().getTemplatesSearch(user, selection, templateNameSearch, templateConfigSearch, templateProductLineSearch, context);
    Template template = null;
    FormRadioButtonGroup templateRadioBox = null;
    FormTextField templateName = null;
    FormTextField templateOwner = null;
    context.putDelivery("suggestedTemplates", suggestedTemplates);
    for (int i = 0; i < suggestedTemplates.size(); i++) {
      template = (Template)suggestedTemplates.get(i);
      templateRadioBox = new FormRadioButtonGroup("TemplateRadio" + String.valueOf(i), "", String.valueOf(template.getTemplateID()), false);
      templateRadioBox.addFormEvent("onClick", "getTemplateTasks(" + String.valueOf(template.getTemplateID()) + ")");
      templateRadioBox.setId(String.valueOf(template.getTemplateID()));
      templateRadioBox.setShowLabels(false);
      form.addElement(templateRadioBox);
      templateName = new FormTextField("TemplateName" + String.valueOf(i), template.getTempateName(), false, 20);
      form.addElement(templateName);
      Family family = template.getOwner();
      String familyString = "";
      if (family != null)
        familyString = family.getName(); 
      templateOwner = new FormTextField("TemplateOwner" + String.valueOf(i), familyString, false, 20);
      form.addElement(templateOwner);
      templateRadioBox = null;
      templateOwner = null;
      templateName = null;
    } 
    int secureLevel = getSchedulePermissions(selection, user);
    setButtonVisibilities(selection, user, context, secureLevel, command);
    if (context.getSessionValue("NOTEPAD_SCHEDULE_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_SCHEDULE_VISIBLE")); 
    form.addElement(new FormHidden("cmd", "schedule-select-template", true));
    if (selection != null) {
      context.putDelivery("releaseWeek", MilestoneHelper.getReleaseWeekString(selection));
      ProductCategory productCategory = selection.getProductCategory();
      String productCategoryString = "";
      if (productCategory != null)
        productCategoryString = productCategory.getName(); 
      String typeConfig = String.valueOf(productCategoryString) + " / " + selection.getReleaseType().getName() + " / " + selection.getSelectionConfig().getSelectionConfigurationName() + " / " + selection.getSelectionSubConfig().getSelectionSubConfigurationName();
      context.putDelivery("typeConfig", typeConfig);
    } 
    form.addElement(new FormHidden("OrderBy", "", true));
    context.putDelivery("Form", form);
    return context.includeJSP("schedule-select-template.jsp");
  }
  
  private boolean assignTemplate(Dispatcher dispatcher, Context context, String command) {
    Form form = new Form(this.application, "ScheduleSelectTemplateForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    User user = (User)context.getSessionValue("user");
    String templateId = "";
    if (context.getRequestValue("templateId") != null)
      templateId = context.getRequestValue("templateId"); 
    Selection selection = (Selection)context.getSessionValue("Selection");
    if (selection != null) {
      ScheduleManager.getInstance().assignTemplate(user, selection, templateId);
      int assignedTemplateId = -1;
      try {
        assignedTemplateId = Integer.parseInt(templateId);
      } catch (Exception exception) {}
      selection.setTemplateId(assignedTemplateId);
      Schedule schedule = selection.getSchedule();
      int secureLevel = getSchedulePermissions(selection, user);
      setButtonVisibilities(selection, user, context, secureLevel, command);
      Notepad notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 1);
      MilestoneHelper.putNotepadIntoSession(notepad, context);
      if (selection != null) {
        ScheduledTask task = null;
        for (int i = 0; i < schedule.getTasks().size(); i++) {
          task = (ScheduledTask)schedule.getTasks().elementAt(i);
          task.setReleaseID(-1);
          task.setComments("");
          task.setCompletionDate(null);
          task.setDueDate(null);
          task.setScheduledTaskStatus("");
          task = null;
        } 
        ScheduleManager.getInstance().recalculateDueDates(schedule, selection);
        schedule.setTasks(sortScreenScheduledTaskVector(0, schedule.getTasks()));
        schedule.setTasks(sortScreenScheduledTaskVector(1, schedule.getTasks()));
        schedule.setTasks(sortScreenScheduledTaskVector(2, schedule.getTasks()));
        int sortType = -1;
        if (context.getSessionValue("ScheduleTaskSort") != null)
          sortType = Integer.parseInt((String)context.getSessionValue("ScheduleTaskSort")); 
        if (schedule != null && schedule.getTasks() != null)
          schedule.setTasks(sortScreenScheduledTaskVector(sortType, schedule.getTasks())); 
        selection.setSchedule(schedule);
      } 
      form = buildForm(context, selection, schedule, command);
      form.addElement(new FormHidden("cmd", "schedule-editor", true));
      form.addElement(new FormHidden("OrderTasksBy", "", true));
      context.putDelivery("Form", form);
      if (selection != null) {
        context.putDelivery("releaseWeek", MilestoneHelper.getReleaseWeekString(selection));
        ProductCategory productCategory = selection.getProductCategory();
        String productCategoryString = "";
        if (productCategory != null)
          productCategoryString = productCategory.getName(); 
        String typeConfig = String.valueOf(productCategoryString) + " / " + selection.getReleaseType().getName() + " / " + selection.getSelectionConfig().getSelectionConfigurationName() + " / " + selection.getSelectionSubConfig().getSelectionSubConfigurationName();
        context.putDelivery("typeConfig", typeConfig);
      } 
      context.putSessionValue("Selection", selection);
      if (selection != null && (schedule == null || (schedule.getTasks().size() == 0 && isNewSchedule(schedule)))) {
        suggestedTemplate(form, selection, context, command);
        return true;
      } 
      return context.includeJSP("schedule-editor.jsp");
    } 
    return true;
  }
  
  private boolean copySchedule(Dispatcher dispatcher, Context context, String command) {
    Form form = new Form(this.application, "ScheduleSelectTemplateForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    Selection selection = new Selection();
    selection = MilestoneHelper.getScreenSelection(context);
    User user = (User)context.getSessionValue("user");
    int secureLevel = getSchedulePermissions(selection, user);
    setButtonVisibilities(selection, user, context, secureLevel, command);
    String releaseId = "";
    if (context.getRequestValue("releaseId") != null)
      releaseId = context.getRequestValue("releaseId"); 
    Schedule schedule = ScheduleManager.getInstance().getSchedule(Integer.parseInt(releaseId));
    Notepad notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 1);
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    if (selection != null) {
      ScheduledTask task = null;
      for (int i = 0; i < schedule.getTasks().size(); i++) {
        task = (ScheduledTask)schedule.getTasks().elementAt(i);
        task.setReleaseID(-1);
        task.setComments("");
        task.setCompletionDate(null);
        task.setDueDate(null);
        task.setScheduledTaskStatus("");
        task.setVendor("");
        task = null;
      } 
      ScheduleManager.getInstance().recalculateDueDates(schedule, selection);
      schedule.setTasks(sortScreenScheduledTaskVector(0, schedule.getTasks()));
      schedule.setTasks(sortScreenScheduledTaskVector(1, schedule.getTasks()));
      schedule.setTasks(sortScreenScheduledTaskVector(2, schedule.getTasks()));
      selection.setSchedule(schedule);
      int sortType = -1;
      if (context.getSessionValue("ScheduleTaskSort") != null)
        sortType = Integer.parseInt((String)context.getSessionValue("ScheduleTaskSort")); 
      if (schedule != null && schedule.getTasks() != null)
        schedule.setTasks(sortScreenScheduledTaskVector(sortType, schedule.getTasks())); 
    } 
    form = buildForm(context, selection, schedule, command);
    form.addElement(new FormHidden("cmd", "schedule-editor", true));
    form.addElement(new FormHidden("OrderTasksBy", "", true));
    form.addElement(new FormHidden("OrderBy", "", true));
    context.putDelivery("Form", form);
    context.putSessionValue("Selection", selection);
    if (selection != null && (schedule == null || (schedule.getTasks().size() == 0 && isNewSchedule(schedule)))) {
      suggestedTemplate(form, selection, context, command);
      return true;
    } 
    return context.includeJSP("schedule-editor.jsp");
  }
  
  public void setButtonVisibilities(Selection selection, User user, Context context, int level, String command) {
    String copyVisible = "false";
    String saveVisible = "false";
    String saveCommentVisible = "false";
    String deleteVisible = "false";
    String newVisible = "false";
    String recalcClearCloseVisible = "0";
    Notepad notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 1);
    notepad.setSwitchToTaskVisible(false);
    int commentSecureLevel = SelectionHandler.getSelectionPermissions(selection, user);
    if (commentSecureLevel > 1)
      saveCommentVisible = "true"; 
    if (level > 1) {
      saveVisible = "true";
      copyVisible = "true";
      deleteVisible = "true";
      recalcClearCloseVisible = "1";
      notepad.setSwitchToTaskVisible(true);
      if (selection.getSelectionID() > 0)
        newVisible = "true"; 
    } 
    if (command.indexOf("new") > -1 || command.indexOf("copy") > -1) {
      saveVisible = "true";
      copyVisible = "false";
      deleteVisible = "false";
      newVisible = "false";
      recalcClearCloseVisible = "0";
    } 
    if (selection != null && selection.getSchedule() != null && selection.getSchedule().getTasks().size() == 0) {
      notepad.setSwitchToTaskVisible(false);
    } else if (selection != null && selection.getSchedule() == null) {
      notepad.setSwitchToTaskVisible(false);
    } 
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    context.putDelivery("saveVisible", saveVisible);
    context.putDelivery("copyVisible", copyVisible);
    context.putDelivery("deleteVisible", deleteVisible);
    context.putDelivery("newVisible", newVisible);
    context.putDelivery("saveCommentVisible", saveCommentVisible);
    context.putDelivery("recalcClearCloseVisible", recalcClearCloseVisible);
  }
  
  public static int getSchedulePermissions(Selection selection, User user) {
    int level = 0;
    int familyId = 0;
    if (selection != null && selection.getSelectionID() > -1) {
      Environment environment = selection.getEnvironment();
      CompanyAcl selectionCompanyAcl = MilestoneHelper.getScreenPermissions(environment, user);
      if (selectionCompanyAcl != null)
        level = selectionCompanyAcl.getAccessSchedule(); 
      if (level == 2)
        return level; 
      Schedule schedule = selection.getSchedule();
      if (schedule != null && schedule.getTasks().size() > 0) {
        Vector tasks = schedule.getTasks();
        ScheduledTask scheduledTask = null;
        for (int i = 0; i < tasks.size(); i++) {
          scheduledTask = (ScheduledTask)tasks.get(i);
          Family family = (scheduledTask.getOwner() != null) ? scheduledTask.getOwner() : null;
          if (family != null) {
            Hashtable familyAclHash = user.getAcl().getFamilyAccessHash();
            if (familyAclHash != null) {
              int accessInt = -1;
              String accessStr = (String)familyAclHash.get(String.valueOf(family.getStructureID()));
              if (accessStr != null && !accessStr.equals(""))
                accessInt = Integer.parseInt(accessStr); 
              if (accessInt > level) {
                level = accessInt;
                if (level == 2)
                  return level; 
              } 
            } 
          } 
          scheduledTask = null;
        } 
      } 
    } 
    return level;
  }
  
  public static Vector sortScreenScheduledTaskVector(int sortBy, Vector taskVector) {
    Vector sortedVector = new Vector();
    Object[] taskArray = taskVector.toArray();
    if (taskVector != null) {
      switch (sortBy) {
        case 0:
          Arrays.sort(taskArray, new SchedTaskNameComparator());
          break;
        case 1:
          Arrays.sort(taskArray, new SchedTaskWksToReleaseComparator());
          break;
        case 2:
          Arrays.sort(taskArray, new SchedTaskDueDateComparator());
          break;
        case 3:
          Arrays.sort(taskArray, new SchedTaskCompleteComparator());
          break;
        case 4:
          Arrays.sort(taskArray, new SchedTaskStatusComparator());
          break;
        case 5:
          Arrays.sort(taskArray, new SchedTaskVendorComparator());
          break;
        case -1:
          Arrays.sort(taskArray, new SchedTaskNameComparator());
          Arrays.sort(taskArray, new SchedTaskWksToReleaseComparator());
          Arrays.sort(taskArray, new SchedTaskDueDateComparator());
          break;
        default:
          Arrays.sort(taskArray, new SchedTaskNameComparator());
          Arrays.sort(taskArray, new SchedTaskWksToReleaseComparator());
          Arrays.sort(taskArray, new SchedTaskDueDateComparator());
          break;
      } 
      for (int i = 0; i < taskArray.length; i++) {
        ScheduledTask task = (ScheduledTask)taskArray[i];
        sortedVector.add(task);
      } 
    } 
    return sortedVector;
  }
  
  private String getSearchJavaScriptCorporateArray(Context context) {
    StringBuffer result = new StringBuffer(100);
    String str = "";
    String value = new String();
    boolean foundFirstTemp = false;
    User user = (User)context.getSessionValue("user");
    Vector vUserCompanies = MilestoneHelper.getUserCompanies(context);
    HashMap corpHashMap = MilestoneHelper.buildActiveCorporateStructureHashMap();
    result.append("\n");
    result.append("var aSearch = new Array();\n");
    int arrayIndex = 0;
    result.append("aSearch[0] = new Array(");
    result.append(0);
    result.append(", '");
    result.append(" ");
    result.append('\'');
    foundFirstTemp = true;
    for (int a = 0; a < vUserCompanies.size(); a++) {
      Company ueTemp = (Company)vUserCompanies.elementAt(a);
      if (ueTemp != null) {
        Vector labels = Cache.getInstance().getLabels();
        for (int b = 0; b < labels.size(); b++) {
          Label node = (Label)labels.elementAt(b);
          if (node.getParent().getParentID() == ueTemp.getStructureID() && 
            !corpHashMap.containsKey(new Integer(node.getStructureID()))) {
            if (foundFirstTemp)
              result.append(','); 
            result.append(' ');
            result.append(node.getStructureID());
            result.append(", '");
            result.append(MilestoneHelper.urlEncode(node.getName()));
            result.append('\'');
            foundFirstTemp = true;
          } 
        } 
      } 
    } 
    if (!foundFirstTemp) {
      result.append("'[none available]');\n");
    } else {
      result.append(");\n");
    } 
    for (int i = 0; i < vUserCompanies.size(); i++) {
      Company ue = (Company)vUserCompanies.elementAt(i);
      if (ue != null) {
        result.append("aSearch[");
        result.append(ue.getStructureID());
        result.append("] = new Array(");
        boolean foundFirst = false;
        result.append(0);
        result.append(", '");
        result.append(" ");
        result.append('\'');
        foundFirst = true;
        Vector tmpArray = new Vector();
        Vector labels = Cache.getInstance().getLabels();
        for (int j = 0; j < labels.size(); j++) {
          Label node = (Label)labels.elementAt(j);
          if (node.getParent().getParentID() == ue.getStructureID() && 
            !corpHashMap.containsKey(new Integer(node.getStructureID()))) {
            if (foundFirst)
              result.append(','); 
            result.append(' ');
            result.append(node.getStructureID());
            result.append(", '");
            result.append(MilestoneHelper.urlEncode(node.getName()));
            result.append('\'');
            foundFirst = true;
            tmpArray.addElement(node);
          } 
        } 
        if (foundFirst) {
          result.append(");\n");
        } else {
          result.append(" 0, '[none available]');\n");
        } 
      } 
    } 
    return result.toString();
  }
  
  public static FormDropDownMenu getDepartmentFilterDropDown(String name, String selectedOption, boolean required, Schedule schedule, Context context) {
    Vector values = new Vector();
    Vector menuText = new Vector();
    values.addElement("-1");
    menuText.addElement("&nbsp;&nbsp;");
    FormDropDownMenu dropDown = null;
    if (selectedOption == null)
      selectedOption = ""; 
    Vector deptList = Cache.getLookupDetailValuesFromDatabase(18);
    if (deptList != null) {
      HashMap taskDepts = (HashMap)context.getSessionValue("scheduledTasksDepts");
      for (int i = 0; i < deptList.size(); i++) {
        LookupObject department = (LookupObject)deptList.elementAt(i);
        if (department != null)
          if (taskDepts != null && taskDepts.containsKey(department.getAbbreviation().trim())) {
            values.addElement(department.getAbbreviation());
            menuText.addElement(department.getName());
          }  
      } 
      String[] arrayValues = new String[values.size()];
      String[] arrayMenuText = new String[menuText.size()];
      arrayValues = (String[])values.toArray(arrayValues);
      arrayMenuText = (String[])menuText.toArray(arrayMenuText);
      dropDown = new FormDropDownMenu(name, 
          selectedOption, 
          arrayValues, 
          arrayMenuText, 
          required);
    } 
    return dropDown;
  }
  
  public static void setDeptFilter(Schedule schedule, Context context) {
    HashMap taskDepts = new HashMap();
    if (schedule != null && schedule.getTasks().size() > 0) {
      Vector tasks = schedule.getTasks();
      for (int i = 0; i < tasks.size(); i++) {
        ScheduledTask task = (ScheduledTask)tasks.get(i);
        if (!taskDepts.containsKey(task.getDepartment().trim()))
          taskDepts.put(task.getDepartment().trim(), new Integer(i)); 
      } 
    } 
    context.putSessionValue("scheduledTasksDepts", taskDepts);
  }
  
  private boolean scheduleSelectionGroup(Dispatcher dispatcher, Context context) {
    int sort = Integer.parseInt(context.getParameter("OrderBy"));
    String alphaGroupChr = context.getParameter("alphaGroupChr");
    Notepad notepad = (Notepad)context.getSessionValue(MilestoneConstants.NOTEPAD_SESSION_NAMES[0]);
    User user = (User)context.getSession().getAttribute("user");
    if (notepad.getAllContents() != null && notepad.getAllContents().size() < notepad.getTotalRecords()) {
      notepad.setMaxRecords(0);
      notepad.setAllContents(null);
      notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 0);
    } 
    SelectionManager.getInstance().getAlphaGroupPosition(context, notepad, alphaGroupChr, sort);
    notepad.goToSelectedPage();
    dispatcher.redispatch(context, "schedule-editor");
    return true;
  }
  
  private String clearFilterIfProdChange(String sessionFilter, Selection selection, Context context) {
    if (sessionFilter.indexOf("eCommerce") != -1 && !selection.getIsDigital()) {
      context.putSessionValue("filter", "All");
      return "All";
    } 
    if (sessionFilter.indexOf("UML") != -1 && selection.getIsDigital()) {
      context.putSessionValue("filter", "All");
      return "All";
    } 
    return sessionFilter;
  }
  
  private boolean saveSelectionComments(Dispatcher dispatcher, Context context, String command) {
    Selection selection = (Selection)context.getSessionValue("Selection");
    Form form = buildForm(context, selection, selection.getSchedule(), command);
    form.setValues(context);
    String comments = form.getStringValue("releaseComment");
    selection.setComments(comments);
    SelectionManager.getInstance().updateComment(selection);
    return edit(dispatcher, context, command);
  }
  
  public boolean isNewSchedule(Schedule schedule) {
    boolean retVar = true;
    if (schedule != null) {
      String sqlQuery = "Select top 1 release_id from release_detail where release_id = " + schedule.getSelectionID();
      JdbcConnector connector = MilestoneHelper.getConnector(sqlQuery);
      connector.runQuery();
      if (connector.more())
        retVar = false; 
      connector.close();
    } 
    System.out.println("<<< Schedule.isNew() " + retVar + " release Id " + schedule.getSelectionID());
    return retVar;
  }
  
  public ScheduledTask getScheduledTask(Schedule schedule, int taskId) {
    ScheduledTask task = null;
    if (schedule != null && taskId > 0) {
      Vector tasks = schedule.getTasks();
      if (tasks != null && tasks.size() > 0) {
        int i = getScheduledTaskIndex(tasks, taskId);
        if (i >= 0 && i < tasks.size())
          task = (ScheduledTask)tasks.get(i); 
      } 
    } 
    return task;
  }
  
  public int getScheduledTaskIndex(Vector tasks, int taskID) {
    boolean tgtTaskFound = false;
    int index = 0;
    if (tasks != null && taskID > 0 && 
      tasks != null && tasks.size() > 0)
      while (index < tasks.size() && !tgtTaskFound) {
        if (((ScheduledTask)tasks.get(index)).getTaskID() == taskID)
          tgtTaskFound = true; 
        index++;
      }  
    if (tgtTaskFound)
      return index - 1; 
    return -1;
  }
  
  private boolean multCompleteDateFrame(Dispatcher dispatcher, Context context, String command) {
    Selection selection = (Selection)context.getSessionValue("Selection");
    if (MilestoneHelper_2.hasSchedule(selection.getSelectionID())) {
      int rowNo = getMultCompleteDateFormRowNo(context);
      int taskID = getMultCompleteDateFormTaskID(context);
      String strCompDt = getMultCompleteDateFormCompletionDate(context);
      String strInitCall = context.getRequestValue("init");
      String strTaskDesc = context.getRequestValue("taskDesc");
      User user = (User)context.getSessionValue("user");
      int secureLevel = getSchedulePermissions(selection, user);
      setButtonVisibilities(selection, user, context, secureLevel, command);
      context.putIntDelivery("rowNo", rowNo);
      context.putIntDelivery("taskID", taskID);
      context.putDelivery("schedCompDt", strCompDt);
      context.putDelivery("init", strInitCall);
      context.putDelivery("taskDesc", strTaskDesc);
      return context.includeJSP("multCompleteDateFrame.jsp");
    } 
    return false;
  }
  
  private boolean multCompleteDateEditor(Dispatcher dispatcher, Context context, String command) {
    Selection selection = (Selection)context.getSessionValue("Selection");
    if (MilestoneHelper_2.hasSchedule(selection.getSelectionID())) {
      int rowNo = getMultCompleteDateFormRowNo(context);
      int taskID = getMultCompleteDateFormTaskID(context);
      String strCompDt = getMultCompleteDateFormCompletionDate(context);
      String strInitCall = context.getRequestValue("init");
      User user = (User)context.getSession().getAttribute("user");
      int secureLevel = getSchedulePermissions(selection, user);
      setButtonVisibilities(selection, user, context, secureLevel, command);
      Schedule schedule = (Schedule)context.getSessionValue("Schedule");
      ScheduledTask task = null;
      if (schedule != null) {
        task = getScheduledTask(schedule, taskID);
        if (task != null) {
          Vector formDates = new Vector();
          MultCompleteDate mcd = new MultCompleteDate();
          mcd.setReleaseID(task.getReleaseID());
          mcd.setTaskID(task.getTaskID());
          mcd.setOrderNo(0);
          if (strInitCall != null && strInitCall.equals("1")) {
            mcd.setCompletionDate(MilestoneHelper.getDate(strCompDt));
          } else {
            mcd.setCompletionDate(task.getCompletionDate());
          } 
          formDates.add(mcd);
          Vector multCompleteDates = task.getMultCompleteDates();
          if (multCompleteDates != null)
            formDates.addAll(1, multCompleteDates); 
          buildMultCompleteDateForm(context, command, formDates);
        } 
      } 
      context.putIntDelivery("rowNo", rowNo);
      context.putIntDelivery("taskID", taskID);
      context.putDelivery("schedCompDt", strCompDt);
      return context.includeJSP("multCompleteDate-editor.jsp");
    } 
    return false;
  }
  
  private boolean multCompleteDateEditorCancel(Dispatcher dispatcher, Context context, String command) {
    buildMultCompleteDateForm(context, command, null);
    return context.includeJSP("multCompleteDate-editor.jsp");
  }
  
  private boolean multCompleteDateEditorSave(Dispatcher dispatcher, Context context, String command) {
    int rowNo = getMultCompleteDateFormRowNo(context);
    int taskID = getMultCompleteDateFormTaskID(context);
    String strCompDt = getMultCompleteDateFormCompletionDate(context);
    int rows = getMultCompleteDateFormRows(context);
    boolean compDtAfterDueDt = false;
    String strCurCompDt = "";
    Vector submittedMultCompleteDates = null;
    if (taskID > 0 && rows >= 0) {
      Schedule sessionSchedule = (Schedule)context.getSessionValue("Schedule");
      if (sessionSchedule != null) {
        ScheduledTask sessionTask = getScheduledTask(sessionSchedule, taskID);
        if (sessionTask != null) {
          String aCompletionDate = context.getRequestValue("completeDate0");
          if (MilestoneHelper.isStringNotEmpty(aCompletionDate)) {
            sessionTask.setCompletionDate(MilestoneHelper.getDate(aCompletionDate));
          } else {
            sessionTask.setCompletionDate(null);
          } 
          submittedMultCompleteDates = new Vector();
          int orderNo = rows - 1;
          for (int i = 1; i < rows; i++) {
            String aCompleteDate = context.getRequestValue("completeDate" + i);
            if (MilestoneHelper.isStringNotEmpty(aCompleteDate)) {
              MultCompleteDate mcd = new MultCompleteDate();
              mcd.setReleaseID(sessionSchedule.getSelectionID());
              mcd.setTaskID(taskID);
              mcd.setOrderNo(orderNo);
              mcd.setCompletionDate(MilestoneHelper.getDate(aCompleteDate));
              submittedMultCompleteDates.add(mcd);
              orderNo--;
            } 
          } 
          sessionTask.setMultCompleteDates(submittedMultCompleteDates);
          User user = (User)context.getSession().getAttribute("user");
          ScheduleManager.getInstance().saveMultCompleteDates(sessionTask, user);
          ScheduledTask dbTask = ScheduleManager.getInstance().getScheduledTask(
              sessionSchedule.getSelectionID(), taskID);
          int taskIndex = getScheduledTaskIndex(sessionSchedule.getTasks(), taskID);
          if (taskIndex >= 0)
            sessionSchedule.getTasks().set(taskIndex, dbTask); 
          Vector formDates = new Vector();
          MultCompleteDate mcd = new MultCompleteDate();
          mcd.setReleaseID(dbTask.getReleaseID());
          mcd.setTaskID(dbTask.getTaskID());
          mcd.setOrderNo(0);
          mcd.setCompletionDate(dbTask.getCompletionDate());
          formDates.add(mcd);
          Vector multCompleteDates = dbTask.getMultCompleteDates();
          if (multCompleteDates != null)
            formDates.addAll(1, multCompleteDates); 
          buildMultCompleteDateForm(context, command, formDates);
          strCurCompDt = MilestoneHelper.getFormatedDate(dbTask.getCompletionDate());
          if (dbTask.getCompletionDate() != null && 
            !dbTask.getCompletionDate().equals(MilestoneHelper.getDate("9/9/99")) && 
            dbTask.getDueDate() != null && 
            dbTask.getCompletionDate().after(dbTask.getDueDate()))
            compDtAfterDueDt = true; 
          context.putSessionValue("Schedule", sessionSchedule);
        } 
      } 
    } 
    context.putIntDelivery("compDtAfterDueDt", compDtAfterDueDt ? 1 : 0);
    context.putIntDelivery("rowNo", rowNo);
    context.putIntDelivery("taskID", taskID);
    context.putDelivery("schedCompDt", strCompDt);
    context.putDelivery("curCompDt", strCurCompDt);
    return context.includeJSP("multCompleteDate-editor.jsp");
  }
  
  private boolean multCompleteDateEditorModify(Dispatcher dispatcher, Context context, String command) {
    int rowNo = getMultCompleteDateFormRowNo(context);
    int taskID = getMultCompleteDateFormTaskID(context);
    String strCompDt = getMultCompleteDateFormCompletionDate(context);
    int rows = getMultCompleteDateFormRows(context);
    int dateIndex = -1;
    if (command.equalsIgnoreCase("schedule-multCompleteDates-delete")) {
      String strDateIndex = context.getRequestValue("dateIndex");
      if (strDateIndex != null)
        dateIndex = Integer.parseInt(strDateIndex); 
    } 
    Schedule sessionSchedule = (Schedule)context.getSessionValue("Schedule");
    Vector submittedCompleteDates = new Vector();
    for (int i = 0; i < rows; i++) {
      String aCompleteDate = context.getRequestValue("completeDate" + i);
      if (MilestoneHelper.isStringNotEmpty(aCompleteDate)) {
        boolean keepDate = false;
        if (command.equalsIgnoreCase("schedule-multCompleteDates-delete")) {
          if (i != dateIndex)
            keepDate = true; 
        } else {
          keepDate = true;
        } 
        if (keepDate) {
          MultCompleteDate mcd = new MultCompleteDate();
          mcd.setReleaseID(sessionSchedule.getSelectionID());
          mcd.setTaskID(taskID);
          mcd.setOrderNo(i);
          mcd.setCompletionDate(MilestoneHelper.getDate(aCompleteDate));
          submittedCompleteDates.add(mcd);
        } 
      } 
    } 
    if (command.equalsIgnoreCase("schedule-multCompleteDates-add"))
      submittedCompleteDates.add(0, null); 
    buildMultCompleteDateForm(context, command, submittedCompleteDates);
    context.putIntDelivery("rowNo", rowNo);
    context.putIntDelivery("taskID", taskID);
    context.putDelivery("schedCompDt", strCompDt);
    return context.includeJSP("multCompleteDate-editor.jsp");
  }
  
  private void buildMultCompleteDateForm(Context context, String command, Vector completeDates) {
    Form multCompleteDateForm = new Form(this.application, "multCompleteDateForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    int rows = 0;
    if (completeDates != null && completeDates.size() > 0) {
      for (int j = 0; j < completeDates.size(); j++) {
        MultCompleteDate mcd = (MultCompleteDate)completeDates.get(j);
        String aCompleteDate = "";
        if (mcd != null && mcd.getCompletionDate() != null)
          aCompleteDate = MilestoneHelper.getFormatedDate(mcd.getCompletionDate()); 
        FormTextField completeDateField = new FormTextField("completeDate" + j, aCompleteDate, false, 7, 10);
        completeDateField.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
        completeDateField.addFormEvent("onBlur", "JavaScript:validateDate(this);");
        multCompleteDateForm.addElement(completeDateField);
      } 
      rows = completeDates.size();
    } 
    multCompleteDateForm.addElement(new FormHidden("cmd", command, true));
    context.putDelivery("Form", multCompleteDateForm);
    context.putIntDelivery("rows", rows);
  }
  
  private int getMultCompleteDateFormRowNo(Context context) {
    String strRowNo = context.getRequestValue("rowNo");
    int rowNo = -1;
    if (MilestoneHelper.isStringNotEmpty(strRowNo))
      rowNo = Integer.parseInt(strRowNo); 
    return rowNo;
  }
  
  private int getMultCompleteDateFormTaskID(Context context) {
    String strTaskID = context.getRequestValue("taskID");
    int taskID = -1;
    if (MilestoneHelper.isStringNotEmpty(strTaskID))
      taskID = Integer.parseInt(strTaskID); 
    return taskID;
  }
  
  private String getMultCompleteDateFormCompletionDate(Context context) { return context.getRequestValue("schedCompDt"); }
  
  private int getMultCompleteDateFormRows(Context context) {
    int rows = -1;
    String strRows = context.getRequestValue("rows");
    if (MilestoneHelper.isStringNotEmpty(strRows))
      rows = Integer.parseInt(strRows); 
    return rows;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\ScheduleHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */