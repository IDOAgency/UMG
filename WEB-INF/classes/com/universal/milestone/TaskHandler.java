package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.gemini.Context;
import com.techempower.gemini.Dispatcher;
import com.techempower.gemini.FormCheckBox;
import com.techempower.gemini.FormDropDownMenu;
import com.techempower.gemini.FormElement;
import com.techempower.gemini.FormHidden;
import com.techempower.gemini.FormIntegerField;
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
import com.universal.milestone.MilestoneConstants;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.MilestoneSecurity;
import com.universal.milestone.Notepad;
import com.universal.milestone.SecureHandler;
import com.universal.milestone.Task;
import com.universal.milestone.TaskHandler;
import com.universal.milestone.TaskManager;
import com.universal.milestone.User;
import com.universal.milestone.UserManager;
import java.util.Calendar;
import java.util.Vector;

public class TaskHandler extends SecureHandler {
  public static final String COMPONENT_CODE = "hTas";
  
  public GeminiApplication application;
  
  public ComponentLog log;
  
  public TaskHandler(GeminiApplication application) {
    this.application = application;
    this.log = application.getLog("hTas");
  }
  
  public String getDescription() { return "Task"; }
  
  public boolean acceptRequest(Dispatcher dispatcher, Context context, String command) {
    if (super.acceptRequest(dispatcher, context, command))
      if (command.startsWith("task"))
        return handleRequest(dispatcher, context, command);  
    return false;
  }
  
  public boolean handleRequest(Dispatcher dispatcher, Context context, String command) {
    if (command.equalsIgnoreCase("task-search"))
      search(dispatcher, context, command); 
    if (command.equalsIgnoreCase("task-sort")) {
      sort(dispatcher, context);
    } else if (command.equalsIgnoreCase("task-editor")) {
      edit(context, command);
    } else if (command.equalsIgnoreCase("task-edit-save")) {
      editSave(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("task-edit-copy")) {
      editCopy(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("task-edit-save-new")) {
      editSaveNew(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("task-edit-new")) {
      editNew(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("task-edit-delete")) {
      delete(context, command);
    } 
    return true;
  }
  
  private boolean search(Dispatcher dispatcher, Context context, String command) {
    Notepad notepad = MilestoneHelper.getNotepadFromSession(4, context);
    notepad.setAllContents(null);
    notepad.setSelected(null);
    TaskManager.getInstance().setNotepadQuery(context, MilestoneSecurity.getUser(context).getUserId(), notepad);
    dispatcher.redispatch(context, "task-editor");
    return true;
  }
  
  private boolean sort(Dispatcher dispatcher, Context context) {
    int sort = Integer.parseInt(context.getParameter("OrderBy"));
    int userId = MilestoneSecurity.getUser(context).getUserId();
    Notepad notepad = getTaskNotepad(context, userId);
    if (notepad.getSearchQuery() == null || notepad.getSearchQuery().equals(""))
      notepad.setSearchQuery(TaskManager.getInstance().getDefaultSearchQuery(context, true)); 
    notepad.setOrderBy(" ORDER BY " + MilestoneConstants.SORT_TASK_EDITOR[sort]);
    notepad.setAllContents(null);
    notepad = getTaskNotepad(context, userId);
    notepad.goToSelectedPage();
    dispatcher.redispatch(context, "task-editor");
    return true;
  }
  
  private boolean edit(Context context, String command) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getTaskNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Task task = MilestoneHelper.getScreenTask(context);
    if (task != null) {
      Form form = null;
      if (task != null) {
        form = buildForm(context, task, command);
      } else {
        form = buildNewForm(context, command);
      } 
      boolean isMultiple = TaskManager.getInstance().isOnMultipleTemplates(task);
      if (isMultiple)
        context.putDelivery("MultipleMessage", new String("Task is assigned to multiple templates.  \\nDo you still wish to save?")); 
      context.putDelivery("Form", form);
      return context.includeJSP("task-editor.jsp");
    } 
    return goToBlank(context);
  }
  
  private boolean editCopy(Dispatcher dispatcher, Context context, String command) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getTaskNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Task targetTask = MilestoneHelper.getScreenTask(context);
    Vector templateNames = new Vector();
    String taskName = targetTask.getTaskName();
    boolean keyTask = targetTask.getIsKeyTask();
    boolean activeTask = targetTask.getActiveFlag();
    int taskAbbrev = targetTask.getTaskAbbreviation();
    String category = targetTask.getCategory();
    String department = targetTask.getDepartment();
    int wksToRls = targetTask.getWeeksToRelease();
    Day dayOfWeek = targetTask.getDayOfTheWeek();
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Task copiedTask = null;
    try {
      copiedTask = (Task)targetTask.clone();
    } catch (CloneNotSupportedException cloneNotSupportedException) {}
    copiedTask.setTaskID(-1);
    copiedTask.setTaskName(taskName);
    copiedTask.setComments("");
    copiedTask.setCategory(category);
    copiedTask.setDayOfTheWeek(dayOfWeek);
    copiedTask.setIsKeyTask(keyTask);
    copiedTask.setOwner(null);
    copiedTask.setTemplates(templateNames);
    copiedTask.setWeekAdjustment(0);
    copiedTask.setWeeksToRelease(wksToRls);
    copiedTask.setTaskDescription("");
    copiedTask.setTaskAbbreviation(taskAbbrev);
    copiedTask.setDepartment(department);
    copiedTask.setActiveFlag(true);
    copiedTask.setAllowMultCompleteDatesFlag(false);
    Form form = null;
    form = buildForm(context, copiedTask, command);
    form.addElement(new FormHidden("cmd", "task-edit-copy"));
    context.putSessionValue("Task", copiedTask);
    context.putDelivery("Form", form);
    if (context.getSessionValue("NOTEPAD_TASKS_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_TASKS_VISIBLE")); 
    return context.includeJSP("task-editor.jsp");
  }
  
  private boolean editSave(Dispatcher dispatcher, Context context, String command) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getTaskNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Task task = (Task)context.getSessionValue("Task");
    Form form = buildForm(context, task, command);
    if (TaskManager.getInstance().isTimestampValid(task)) {
      form.setValues(context);
      String taskNameString = form.getStringValue("taskName");
      boolean activeFlagBool = ((FormCheckBox)form.getElement("activeFlag")).isChecked();
      String commentsString = form.getStringValue("comments");
      boolean keyTaskBool = ((FormCheckBox)form.getElement("keyTask")).isChecked();
      String wksToReleaseString = form.getStringValue("weeks2Rel");
      String dayOfWeekString = form.getStringValue("dayOfWeek");
      String weekAdjustmentString = form.getStringValue("weekAdjustment");
      String departmentString = "";
      if (!form.getStringValue("department").equalsIgnoreCase("-1"))
        departmentString = form.getStringValue("department"); 
      String categoryString = form.getStringValue("category");
      String ownerString = form.getStringValue("owner");
      int ownerID = 0;
      if (ownerString != null)
        try {
          ownerID = Integer.parseInt(ownerString);
        } catch (NumberFormatException e) {
          ownerID = 0;
        }  
      int allowMultInt = 0;
      try {
        allowMultInt = Integer.parseInt(form.getStringValue("allowMultCompleteDatesFlag"));
      } catch (NumberFormatException e) {
        allowMultInt = 0;
      } 
      boolean allowMultBool = (allowMultInt == 1);
      task.setAllowMultCompleteDatesFlag(allowMultBool);
      String taskAbbreviationString = form.getStringValue("taskAbbreviation");
      String taskDescriptionString = form.getStringValue("taskDescription");
      task.setTaskName(taskNameString);
      task.setComments(commentsString);
      task.setIsKeyTask(keyTaskBool);
      task.setActiveFlag(activeFlagBool);
      int wks2Rel = 0;
      try {
        if (wksToReleaseString.equalsIgnoreCase("SOL") || dayOfWeekString.equalsIgnoreCase("9")) {
          wks2Rel = -10;
          dayOfWeekString = "9";
        } else {
          wks2Rel = Integer.parseInt(wksToReleaseString);
        } 
      } catch (NumberFormatException e) {
        wks2Rel = 0;
      } 
      task.setWeeksToRelease(wks2Rel);
      task.setDayOfTheWeek(new Day(dayOfWeekString));
      int weekAdjust = 0;
      try {
        weekAdjust = Integer.parseInt(weekAdjustmentString);
      } catch (NumberFormatException e) {
        weekAdjust = 0;
      } 
      task.setWeekAdjustment(weekAdjust);
      task.setDepartment(departmentString);
      task.setCategory(categoryString);
      task.setOwner((Family)MilestoneHelper.getStructureObject(ownerID));
      int taskAbbreviation = -1;
      if (taskAbbreviationString != null && !taskAbbreviationString.equals(""))
        try {
          taskAbbreviation = Integer.parseInt(taskAbbreviationString);
        } catch (NumberFormatException numberFormatException) {} 
      task.setTaskAbbreviation(taskAbbreviation);
      task.setTaskDescription(taskDescriptionString);
      task.setLastUpdatingUser(user.getUserId());
      task.setTaskAbbrStr(MilestoneHelper.getTaskAbbreivationNameById(taskAbbreviation));
      if (!TaskManager.getInstance().isDuplicateTask(task)) {
        if (!form.isUnchanged()) {
          FormValidation formValidation = form.validate();
          if (formValidation.isGood()) {
            Task savedTask = TaskManager.getInstance().saveTask(task, user.getUserId());
            FormElement lastUpdated = form.getElement("lastupdateddate");
            lastUpdated.setValue(MilestoneHelper.getLongDate(savedTask.getLastUpdateDate()));
            notepad.setAllContents(null);
            notepad = getTaskNotepad(context, user.getUserId());
            notepad.setSelected(savedTask);
            task = (Task)notepad.validateSelected();
            context.putSessionValue("Selection", task);
            if (task == null)
              return goToBlank(context); 
            return edit(context, command);
          } 
          context.putDelivery("FormValidation", formValidation);
        } 
        form.addElement(new FormHidden("OrderBy", "", true));
        context.putDelivery("Form", form);
        return edit(context, command);
      } 
      context.putDelivery("AlertMessage", "Task already exists in database. Change the name,weeks to release or day of week.");
    } else {
      context.putDelivery("AlertMessage", "The record is unavailable for editing at this time.  Please refresh your screen and edit it at a later time.");
    } 
    context.putDelivery("Form", form);
    return context.includeJSP("task-editor.jsp");
  }
  
  private boolean delete(Context context, String command) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getTaskNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Task task = MilestoneHelper.getScreenTask(context);
    if (task != null) {
      this.log.debug("TTTTTTTTTTTt is task being used ? " + MilestoneHelper.isTaskUsed(task));
      if (!MilestoneHelper.isTaskUsed(task)) {
        TaskManager.getInstance().deleteTask(task, user.getUserId());
        notepad.setAllContents(null);
        notepad = getTaskNotepad(context, user.getUserId());
        notepad.setSelected(null);
      } else {
        context.putDelivery("AlertMessage", "This task is in use, therefore you can not delete it!");
      } 
    } 
    return edit(context, command);
  }
  
  private boolean editNew(Dispatcher dispatcher, Context context, String command) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getTaskNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Form form = buildNewForm(context, command);
    context.putDelivery("Form", form);
    context.putDelivery("isNewTask", "true");
    return context.includeJSP("task-editor.jsp");
  }
  
  private boolean editSaveNew(Dispatcher dispatcher, Context context, String command) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getTaskNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Task task = new Task();
    Form form = buildNewForm(context, command);
    form.setValues(context);
    task.setTaskID(-1);
    String taskNameString = "";
    if (form.getStringValue("taskName") != null)
      taskNameString = form.getStringValue("taskName"); 
    task.setTaskName(taskNameString);
    String taskDescriptionString = "";
    if (form.getStringValue("taskDescription") != null)
      taskDescriptionString = form.getStringValue("taskDescription"); 
    task.setTaskDescription(taskDescriptionString);
    String taskAbbreviationString = form.getStringValue("taskAbbreviation");
    int taskAbbreviation = -1;
    if (taskAbbreviationString != null && !taskAbbreviationString.equals(""))
      try {
        taskAbbreviation = Integer.parseInt(taskAbbreviationString);
      } catch (NumberFormatException numberFormatException) {} 
    task.setTaskAbbreviation(taskAbbreviation);
    task.setTaskAbbrStr(MilestoneHelper.getTaskAbbreivationNameById(taskAbbreviation));
    String dayOfWeekString = "";
    if (form.getStringValue("dayOfWeek") != null)
      dayOfWeekString = form.getStringValue("dayOfWeek"); 
    task.setDayOfTheWeek(new Day(dayOfWeekString));
    String wksToReleaseString = "";
    if (form.getStringValue("weeks2Rel") != null)
      wksToReleaseString = form.getStringValue("weeks2Rel"); 
    int wks2Rel = 0;
    try {
      if (wksToReleaseString.equalsIgnoreCase("SOL") || dayOfWeekString.equalsIgnoreCase("9")) {
        wks2Rel = -10;
      } else {
        wks2Rel = Integer.parseInt(wksToReleaseString);
      } 
    } catch (NumberFormatException e) {
      wks2Rel = 0;
    } 
    task.setWeeksToRelease(wks2Rel);
    String departmentString = "";
    if (form.getStringValue("department") != null && !form.getStringValue("department").equalsIgnoreCase("-1"))
      departmentString = form.getStringValue("department"); 
    task.setDepartment(departmentString);
    String categoryString = "";
    if (form.getStringValue("category") != null)
      categoryString = form.getStringValue("category"); 
    task.setCategory(categoryString);
    boolean keyTaskBool = ((FormCheckBox)form.getElement("keyTask")).isChecked();
    task.setIsKeyTask(keyTaskBool);
    boolean activeFlagBool = ((FormCheckBox)form.getElement("activeFlag")).isChecked();
    task.setActiveFlag(activeFlagBool);
    String ownerString = "";
    int ownerID = 0;
    if (form.getStringValue("owner") != null)
      ownerString = form.getStringValue("owner"); 
    try {
      ownerID = Integer.parseInt(ownerString);
    } catch (NumberFormatException e) {
      ownerID = 0;
    } 
    task.setOwner((Family)MilestoneHelper.getStructureObject(ownerID));
    String weekAdjustmentString = "";
    if (form.getStringValue("weekAdjustment") != null)
      weekAdjustmentString = form.getStringValue("weekAdjustment"); 
    task.setDayOfTheWeek(new Day(dayOfWeekString));
    int weekAdjust = 0;
    try {
      weekAdjust = Integer.parseInt(weekAdjustmentString);
    } catch (NumberFormatException e) {
      weekAdjust = 0;
    } 
    task.setWeekAdjustment(weekAdjust);
    String commentsString = "";
    if (form.getStringValue("comments") != null)
      commentsString = form.getStringValue("comments"); 
    task.setComments(commentsString);
    int allowMultInt = 0;
    try {
      allowMultInt = Integer.parseInt(form.getStringValue("allowMultCompleteDatesFlag"));
    } catch (NumberFormatException e) {
      allowMultInt = 0;
    } 
    boolean allowMultBool = (allowMultInt == 1);
    task.setAllowMultCompleteDatesFlag(allowMultBool);
    task.setLastUpdatingUser(user.getUserId());
    if (!TaskManager.getInstance().isDuplicateTask(task)) {
      if (!form.isUnchanged()) {
        FormValidation formValidation = form.validate();
        if (formValidation.isGood()) {
          Task savedTask = TaskManager.getInstance().saveTask(task, user.getUserId());
          context.putSessionValue("Task", savedTask);
          context.putDelivery("Form", form);
          notepad.setAllContents(null);
          notepad.newSelectedReset();
          notepad = getTaskNotepad(context, user.getUserId());
          notepad.setSelected(savedTask);
        } else {
          context.putDelivery("FormValidation", formValidation);
          form.addElement(new FormHidden("OrderBy", "", true));
          context.putDelivery("Form", form);
          return context.includeJSP("task-editor.jsp");
        } 
      } 
    } else {
      context.putDelivery("AlertMessage", "Task already exists in database. Change the name,weeks to release or day of week.");
    } 
    return edit(context, "task-editor");
  }
  
  protected Form buildForm(Context context, Task task, String command) {
    Calendar testDate = Calendar.getInstance();
    User sessionUser = MilestoneSecurity.getUser(context);
    Form form = new Form(this.application, "TaskForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    int secureLevel = getTaskPermissions(task, sessionUser);
    setButtonVisibilities(task, sessionUser, context, secureLevel, command);
    FormTextField name = new FormTextField("taskName", true, 50);
    name.setValue(task.getTaskName());
    form.addElement(name);
    FormCheckBox activeFlag = new FormCheckBox("activeFlag", "", false, task.getActiveFlag());
    form.addElement(activeFlag);
    String comments = "";
    if (task.getComments().equals("[none]")) {
      comments = "";
    } else {
      comments = task.getComments();
    } 
    FormTextArea commentsTextArea = new FormTextArea("comments", "", false, 2, 44, "virtual");
    commentsTextArea.setId("comments");
    commentsTextArea.setValue(comments);
    commentsTextArea.addFormEvent("onBlur", "Javascript:checkField(this)");
    form.addElement(commentsTextArea);
    FormCheckBox keyTaskIndicator = new FormCheckBox("keyTask", "", false, task.getIsKeyTask());
    form.addElement(keyTaskIndicator);
    FormTextField wksToRelease = new FormTextField("weeks2Rel", false, 5);
    if (task.getWeeksToRelease() == -10) {
      wksToRelease.setValue("SOL");
    } else {
      wksToRelease.setValue(String.valueOf(task.getWeeksToRelease()));
    } 
    form.addElement(wksToRelease);
    FormDropDownMenu dayOfWeekDropdown = MilestoneHelper.getLookupDropDown("dayOfWeek", Cache.getLookupDetailValuesFromDatabase(5), "", false, true);
    if (task.getDayOfTheWeek() != null)
      dayOfWeekDropdown.setValue(task.getDayOfTheWeek().getDay()); 
    form.addElement(dayOfWeekDropdown);
    FormIntegerField weekAdjustment = new FormIntegerField("weekAdjustment", "", false, 1, 99, 5);
    weekAdjustment.setValue(String.valueOf(task.getWeekAdjustment()));
    form.addElement(weekAdjustment);
    String holdDepartment = "";
    if (task.getDepartment() != null)
      holdDepartment = task.getDepartment(); 
    FormDropDownMenu department = MilestoneHelper.getDepartmentDropDown("department", holdDepartment, false);
    form.addElement(department);
    String category = "";
    if (!task.getCategory().equals("[none]"))
      category = task.getCategory(); 
    FormTextField Category = new FormTextField("category", false, 20);
    Category.setValue(category);
    form.addElement(Category);
    Vector families = MilestoneHelper.getNonSecureUserFamilies(context);
    String owner = "";
    if (task.getOwner() != null)
      try {
        owner = Integer.toString(task.getOwner().getStructureID());
      } catch (NumberFormatException numberFormatException) {} 
    FormDropDownMenu Owner = MilestoneHelper.getCorporateStructureDropDown("owner", families, owner, true, false);
    form.addElement(Owner);
    String[] allowLabels = new String[2];
    allowLabels[0] = "No";
    allowLabels[1] = "Yes";
    String[] allowValues = new String[2];
    allowValues[0] = "0";
    allowValues[1] = "1";
    String allowMultCompleteDatesFlagSel = task.getAllowMultCompleteDatesFlag() ? "1" : "0";
    FormRadioButtonGroup allowMultCompleteDatesFlag = new FormRadioButtonGroup("allowMultCompleteDatesFlag", 
        allowMultCompleteDatesFlagSel, allowValues, allowLabels, false);
    form.addElement(allowMultCompleteDatesFlag);
    String holdAbbrev = "";
    if (task.getTaskAbbreviation() > 0)
      holdAbbrev = Integer.toString(task.getTaskAbbreviation()); 
    FormDropDownMenu taskAbbrev = MilestoneHelper.getTaskAbbreviationsDropDown("taskAbbreviation", holdAbbrev, false);
    form.addElement(taskAbbrev);
    String description = "";
    if (!task.getTaskDescription().equals("[none]"))
      description = task.getTaskDescription(); 
    FormTextField taskDescription = new FormTextField("taskDescription", false, 50);
    taskDescription.setValue(description);
    form.addElement(taskDescription);
    Vector templatesList = task.getTemplates();
    String templates = "";
    String templatesStringList = "";
    for (int i = 0; i < templatesList.size(); i++) {
      templates = (String)templatesList.get(i);
      if (i == 0) {
        templatesStringList = "," + templates;
      } else {
        templatesStringList = String.valueOf(templatesStringList) + "," + templates;
      } 
    } 
    FormDropDownMenu templateNames = new FormDropDownMenu("templateNames", "", templatesStringList, false);
    templateNames.addFormEvent("style", "background-color:lightgrey;");
    templateNames.addFormEvent("size", "3");
    form.addElement(templateNames);
    FormTextField lastUpdated = new FormTextField("lastUpdatedDate", false, 50);
    lastUpdated.setValue(MilestoneHelper.getCustomFormatedDate(task.getLastUpdateDate(), "M/d/yyyy hh:mm:ss a 'ET'"));
    form.addElement(lastUpdated);
    FormTextField lastUpdatedBy = new FormTextField("lastUpdatedBy", false, 50);
    if (UserManager.getInstance().getUser(task.getLastUpdatingUser()) != null)
      lastUpdatedBy.setValue(UserManager.getInstance().getUser(task.getLastUpdatingUser()).getName()); 
    form.addElement(lastUpdatedBy);
    addSelectionSearchElements(context, form);
    context.putSessionValue("task", task);
    form.addElement(new FormHidden("cmd", "task-editor"));
    form.addElement(new FormHidden("OrderBy", "", true));
    if (context.getSessionValue("NOTEPAD_TASKS_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_TASKS_VISIBLE")); 
    return form;
  }
  
  protected Form buildNewForm(Context context, String command) {
    Form form = new Form(this.application, "TaskForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    User sessionUser = MilestoneSecurity.getUser(context);
    Task task = new Task();
    task.setTaskID(-1);
    int secureLevel = getTaskPermissions(task, sessionUser);
    setButtonVisibilities(task, sessionUser, context, secureLevel, command);
    FormTextField name = new FormTextField("taskName", true, 50);
    form.addElement(name);
    FormCheckBox activeFlag = new FormCheckBox("activeFlag", "", false, true);
    form.addElement(activeFlag);
    FormTextArea commentsTextArea = new FormTextArea("comments", "", false, 2, 44, "virtual");
    commentsTextArea.setId("comments");
    commentsTextArea.addFormEvent("onBlur", "Javascript:checkField(this)");
    form.addElement(commentsTextArea);
    FormCheckBox keyTaskIndicator = new FormCheckBox("keyTask", "", false, false);
    form.addElement(keyTaskIndicator);
    FormTextField wksToRelease = new FormTextField("weeks2Rel", false, 5);
    form.addElement(wksToRelease);
    FormDropDownMenu dayOfWeekDropdown = MilestoneHelper.getLookupDropDown("dayOfWeek", Cache.getLookupDetailValuesFromDatabase(5), "", false, true);
    form.addElement(dayOfWeekDropdown);
    FormIntegerField weekAdjustment = new FormIntegerField("weekAdjustment", "", false, 1, 99, 5);
    form.addElement(weekAdjustment);
    FormDropDownMenu department = MilestoneHelper.getDepartmentDropDown("department", "", false);
    form.addElement(department);
    FormTextField category = new FormTextField("category", false, 20);
    form.addElement(category);
    Vector families = MilestoneHelper.getSecureUserFamilies(sessionUser, 2, context);
    FormDropDownMenu Owner = MilestoneHelper.getCorporateStructureDropDown("owner", families, "0", false, false);
    form.addElement(Owner);
    String[] allowLabels = new String[2];
    allowLabels[0] = "No";
    allowLabels[1] = "Yes";
    String[] allowValues = new String[2];
    allowValues[0] = "0";
    allowValues[1] = "1";
    String allowMultCompleteDatesFlagSel = task.getAllowMultCompleteDatesFlag() ? "1" : "0";
    FormRadioButtonGroup allowMultCompleteDatesFlag = new FormRadioButtonGroup("allowMultCompleteDatesFlag", 
        allowMultCompleteDatesFlagSel, allowValues, allowLabels, false);
    form.addElement(allowMultCompleteDatesFlag);
    FormDropDownMenu taskAbbrev = MilestoneHelper.getTaskAbbreviationsDropDown("taskAbbreviation", "", false);
    form.addElement(taskAbbrev);
    FormTextField taskDescription = new FormTextField("taskDescription", false, 50);
    form.addElement(taskDescription);
    FormDropDownMenu templateNames = new FormDropDownMenu("templateNames", "", "", false);
    templateNames.addFormEvent("style", "background-color:lightgrey;");
    templateNames.addFormEvent("size", "3");
    form.addElement(templateNames);
    FormTextField lastUpdated = new FormTextField("lastUpdatedDate", false, 50);
    form.addElement(lastUpdated);
    FormTextField lastUpdatedBy = new FormTextField("lastUpdatedBy", false, 50);
    form.addElement(lastUpdatedBy);
    addSelectionSearchElements(context, form);
    form.addElement(new FormHidden("cmd", "task-edit-new"));
    form.addElement(new FormHidden("OrderBy", "", true));
    if (context.getSessionValue("NOTEPAD_TASKS_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_TASKS_VISIBLE")); 
    return form;
  }
  
  protected void addSelectionSearchElements(Context context, Form form) {
    FormTextField taskNameSrch = new FormTextField("taskNameSrch", "", false, 20, 20);
    taskNameSrch.setId("taskNameSrch");
    form.addElement(taskNameSrch);
    FormTextField taskAbbrevSrch = new FormTextField("taskAbbrevSrch", "", false, 4, 4);
    taskAbbrevSrch.setId("taskAbbrevSrch");
    form.addElement(taskAbbrevSrch);
    FormCheckBox keyTaskSrch = new FormCheckBox("keyTaskSrch", "", "", false);
    keyTaskSrch.setId("keyTaskSrch");
    form.addElement(keyTaskSrch);
    User sessionUser = MilestoneSecurity.getUser(context);
    Vector families = MilestoneHelper.getNonSecureUserFamilies(context);
    FormDropDownMenu TaskOwnerSearch = MilestoneHelper.getCorporateStructureDropDown("ownerSrch", families, "0", false, true);
    form.addElement(TaskOwnerSearch);
    FormDropDownMenu TaskDepartmentSearch = MilestoneHelper.getDepartmentDropDown("departmentSrch", "", false);
    form.addElement(TaskDepartmentSearch);
    FormCheckBox inactiveSrch = new FormCheckBox("inactiveSrch", "", "", false);
    inactiveSrch.setId("inactiveSrch");
    form.addElement(inactiveSrch);
    String[] allowLabels = new String[3];
    allowLabels[0] = "No";
    allowLabels[1] = "Yes";
    allowLabels[2] = "All";
    String[] allowValues = new String[3];
    allowValues[0] = "0";
    allowValues[1] = "1";
    allowValues[2] = "2";
    FormRadioButtonGroup allowMultCompleteDatesSrch = new FormRadioButtonGroup("allowMultCompleteDatesSrch", 
        "2", allowValues, allowLabels, false);
    form.addElement(allowMultCompleteDatesSrch);
  }
  
  public Notepad getTaskNotepad(Context context, int userId) {
    Vector contents = new Vector();
    if (MilestoneHelper.getNotepadFromSession(4, context) != null) {
      Notepad notepad = MilestoneHelper.getNotepadFromSession(4, context);
      if (notepad.getAllContents() == null) {
        this.log.debug("---------Reseting note pad contents------------");
        contents = TaskManager.getInstance().getTaskNotepadList(context, notepad);
        notepad.setAllContents(contents);
      } 
      return notepad;
    } 
    String[] columnNames = { "Task Name", "Wks to Rls", "Own", "Dpt" };
    contents = TaskManager.getInstance().getTaskNotepadList(context, null);
    return new Notepad(contents, 0, 15, "Tasks", 4, columnNames);
  }
  
  private boolean goToBlank(Context context) {
    context.putDelivery("isNotePadVisible", new Boolean(MilestoneHelper.getNotePadVisiblitiy(4, context)));
    Form form = new Form(this.application, "form", this.application.getInfrastructure().getServletURL(), "Post");
    form.addElement(new FormHidden("cmd", "task-editor"));
    form.addElement(new FormHidden("OrderBy", "", true));
    addSelectionSearchElements(context, form);
    context.putDelivery("Form", form);
    return context.includeJSP("blank-task-editor.jsp");
  }
  
  public static int getTaskPermissions(Task task, User user) {
    int level = 0;
    int familyId = 0;
    if (task != null && task.getTaskID() > -1) {
      Family family = task.getOwner();
      Vector environments = family.getEnvironments();
      for (int i = 0; i < environments.size(); i++) {
        Environment environment = (Environment)environments.get(i);
        if (environment != null)
          familyId = environment.getParentFamily().getStructureID(); 
        if (familyId == family.getStructureID()) {
          Vector companies = environment.getCompanies();
          Company company = null;
          if (companies != null && companies.size() > 0)
            company = (Company)companies.get(0); 
          CompanyAcl companyAcl = null;
          if (company != null)
            companyAcl = MilestoneHelper.getScreenPermissions(company, user); 
          if (companyAcl != null && companyAcl.getAccessTask() > level)
            level = companyAcl.getAccessTask(); 
        } 
      } 
    } 
    return level;
  }
  
  public static int getTaskNewButtonPermissions(User user) {
    int level = 0;
    Vector companies = MilestoneHelper.getUserCompanies(user.getUserId());
    if (companies != null)
      for (int i = 0; i < companies.size(); i++) {
        Company company = (Company)companies.get(i);
        if (company != null) {
          CompanyAcl companyAcl = MilestoneHelper.getScreenPermissions(company, user);
          if (companyAcl != null && companyAcl.getAccessTask() > level)
            level = companyAcl.getAccessTask(); 
        } 
      }  
    return level;
  }
  
  public void setButtonVisibilities(Task task, User user, Context context, int level, String command) {
    String copyVisible = "true";
    String saveVisible = "false";
    String deleteVisible = "false";
    String newVisible = "false";
    if (level > 1) {
      saveVisible = "true";
      deleteVisible = "true";
    } 
    int newButtonPermission = getTaskNewButtonPermissions(user);
    if (newButtonPermission > 1)
      newVisible = "true"; 
    if (command.indexOf("new") > -1 || command.indexOf("copy") > -1) {
      saveVisible = "true";
      copyVisible = "false";
      deleteVisible = "false";
      newVisible = "false";
    } 
    context.putDelivery("saveVisible", saveVisible);
    context.putDelivery("copyVisible", copyVisible);
    context.putDelivery("deleteVisible", deleteVisible);
    context.putDelivery("newVisible", newVisible);
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\TaskHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */