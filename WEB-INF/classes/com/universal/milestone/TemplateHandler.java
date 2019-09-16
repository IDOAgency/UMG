package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.gemini.Context;
import com.techempower.gemini.Dispatcher;
import com.techempower.gemini.FormCheckBox;
import com.techempower.gemini.FormDropDownMenu;
import com.techempower.gemini.FormElement;
import com.techempower.gemini.FormHidden;
import com.techempower.gemini.FormRadioButtonGroup;
import com.techempower.gemini.FormTextField;
import com.techempower.gemini.FormValidation;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.Cache;
import com.universal.milestone.Company;
import com.universal.milestone.CompanyAcl;
import com.universal.milestone.Environment;
import com.universal.milestone.Family;
import com.universal.milestone.Form;
import com.universal.milestone.MilestoneConstants;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.MilestoneMessage;
import com.universal.milestone.MilestoneSecurity;
import com.universal.milestone.Notepad;
import com.universal.milestone.NotepadSortOrder;
import com.universal.milestone.ProductCategory;
import com.universal.milestone.ReleaseType;
import com.universal.milestone.SecureHandler;
import com.universal.milestone.SelectionConfiguration;
import com.universal.milestone.SelectionManager;
import com.universal.milestone.SelectionSubConfiguration;
import com.universal.milestone.Task;
import com.universal.milestone.TaskAbbrComparator;
import com.universal.milestone.TaskNameComparator;
import com.universal.milestone.TaskOwnerComparator;
import com.universal.milestone.TaskWksToReleaseComparator;
import com.universal.milestone.Template;
import com.universal.milestone.TemplateHandler;
import com.universal.milestone.TemplateManager;
import com.universal.milestone.User;
import com.universal.milestone.UserManager;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Vector;

public class TemplateHandler extends SecureHandler {
  public static final String COMPONENT_CODE = "hTemp";
  
  public GeminiApplication application;
  
  public ComponentLog log;
  
  public TemplateHandler(GeminiApplication application) {
    this.application = application;
    this.log = application.getLog("hTemp");
  }
  
  public TemplateHandler() {}
  
  public String getDescription() { return "Template"; }
  
  public boolean acceptRequest(Dispatcher dispatcher, Context context, String command) {
    if (super.acceptRequest(dispatcher, context, command))
      if (command.startsWith("template"))
        return handleRequest(dispatcher, context, command);  
    return false;
  }
  
  public boolean handleRequest(Dispatcher dispatcher, Context context, String command) {
    if (command.equalsIgnoreCase("template-editor"))
      edit(dispatcher, context, command); 
    if (command.equalsIgnoreCase("template-search"))
      search(dispatcher, context, command); 
    if (command.equalsIgnoreCase("template-task-search"))
      templateTaskSearch(dispatcher, context, command); 
    if (command.equalsIgnoreCase("template-copy-sort"))
      sort(dispatcher, context); 
    if (command.equalsIgnoreCase("template-notepad-tasks-sort"))
      sortTaskNotepad(dispatcher, context); 
    if (command.equalsIgnoreCase("template-sort-tasks"))
      sortTasks(dispatcher, context); 
    if (command.equalsIgnoreCase("template-sort-task-tasks")) {
      sortTaskTasks(dispatcher, context);
    } else if (command.equalsIgnoreCase("template-locator")) {
      locator(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("template-locator-copy")) {
      locatorCopy(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("template-assign-to-schedule")) {
      assignToSchedule(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("template-edit-new")) {
      editNew(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("template-edit-save-new")) {
      editSaveNew(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("template-edit-save") || command.equalsIgnoreCase("template-copy-save")) {
      editSave(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("template-edit-delete")) {
      editDelete(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("template-edit-copy")) {
      editCopy(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("template-edit-assign-task")) {
      editAssignTask(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("template-edit-delete-task")) {
      editDeleteTask(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("template-recommended")) {
      recommended(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("template-task-editor")) {
      editTemplateTasks(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("template-task-edit-save") || command.equalsIgnoreCase("template-task-copy-save") || command.equalsIgnoreCase("template-copy-save")) {
      editTemplateTaskSave(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("template-add-task")) {
      addTask(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("template-task-edit-new")) {
      editTemplateTaskNew(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("template-task-edit-save-new")) {
      editTemplateTaskSaveNew(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("template-task-edit-delete")) {
      editTemplateTaskDelete(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("template-task-edit-copy")) {
      editTemplateTaskCopy(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("template-task-edit-delete-task")) {
      editTemplateTaskDeleteTask(dispatcher, context, command);
    } 
    return true;
  }
  
  private boolean search(Dispatcher dispatcher, Context context, String command) {
    Notepad notepad = MilestoneHelper.getNotepadFromSession(5, context);
    notepad.setAllContents(null);
    notepad.setSelected(null);
    TemplateManager.getInstance().setNotepadQuery(context, MilestoneSecurity.getUser(context).getUserId(), notepad);
    dispatcher.redispatch(context, "template-editor");
    return true;
  }
  
  private boolean templateTaskSearch(Dispatcher dispatcher, Context context, String command) {
    Notepad notepad = MilestoneHelper.getNotepadFromSession(19, context);
    notepad.setAllContents(null);
    notepad.setSelected(null);
    TemplateManager.getInstance().setTaskNotepadQuery(context, notepad);
    dispatcher.redispatch(context, "template-task-editor");
    return true;
  }
  
  private boolean sort(Dispatcher dispatcher, Context context) {
    int userId = MilestoneSecurity.getUser(context).getUserId();
    Notepad notepad = getTemplateNotepad(context, userId);
    if (notepad.getSearchQuery() == null || notepad.getSearchQuery().equals(""))
      notepad.setSearchQuery(TemplateManager.getInstance().getDefaultSearchQuery(context)); 
    NotepadSortOrder.getNotepadSortOrderFromSession(context).getNotepadSortOrderTemplate().sortHelper(dispatcher, context, notepad);
    notepad.setAllContents(null);
    notepad = getTemplateNotepad(context, userId);
    notepad.goToSelectedPage();
    dispatcher.redispatch(context, "template-editor");
    return true;
  }
  
  private boolean sortTaskNotepad(Dispatcher dispatcher, Context context) {
    int sort = Integer.parseInt(context.getParameter("OrderBy"));
    int userId = MilestoneSecurity.getUser(context).getUserId();
    Template template = MilestoneHelper.getScreenTemplate(context);
    Notepad notepad = getTaskNotepad(context, userId, template.getTemplateID());
    if (notepad.getSearchQuery() == null || notepad.getSearchQuery().equals(""))
      notepad.setSearchQuery(TemplateManager.getInstance().getDefaultTaskSearchQuery(template.getTemplateID())); 
    notepad.setOrderBy(" ORDER BY " + MilestoneConstants.SORT_TASK[sort]);
    notepad.setAllContents(null);
    notepad = getTaskNotepad(context, userId, template.getTemplateID());
    notepad.goToSelectedPage();
    dispatcher.redispatch(context, "template-task-editor");
    return true;
  }
  
  private boolean sortTasks(Dispatcher dispatcher, Context context) {
    int sort = Integer.parseInt(context.getParameter("OrderTasksBy"));
    context.putDelivery("TemplateTaskSort", Integer.toString(sort));
    context.putSessionValue("TemplateTaskSort", Integer.toString(sort));
    dispatcher.redispatch(context, "template-editor");
    return true;
  }
  
  private boolean sortTaskTasks(Dispatcher dispatcher, Context context) {
    int sort = Integer.parseInt(context.getParameter("OrderTasksBy"));
    context.putDelivery("TemplateTaskSort", Integer.toString(sort));
    context.putSessionValue("TemplateTaskSort", Integer.toString(sort));
    dispatcher.redispatch(context, "template-task-editor");
    return true;
  }
  
  public Notepad getTemplateNotepad(Context context, int userId) {
    Vector contents = new Vector();
    if (MilestoneHelper.getNotepadFromSession(5, context) != null) {
      Notepad notepad = MilestoneHelper.getNotepadFromSession(5, context);
      if (notepad.getAllContents() == null) {
        contents = TemplateManager.getInstance().getTemplateNotepadList(context, notepad);
        notepad.setAllContents(contents);
      } 
      return notepad;
    } 
    String[] columnNames = { "Template", "Format", "Owner" };
    contents = TemplateManager.getInstance().getTemplateNotepadList(context, null);
    return new Notepad(contents, 0, 15, "Templates", 5, columnNames);
  }
  
  private boolean edit(Dispatcher dispatcher, Context context, String command) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getTemplateNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Template template = MilestoneHelper.getScreenTemplate(context);
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Form form = null;
    if (template != null) {
      int secureLevel = getTemplatePermissions(template, user);
      if (secureLevel < 2)
        notepad.setSwitchToTaskVisible(false); 
      if (context.getSessionValue("TemplateTaskSort") != null)
        template.setTasks(sortScreenTaskVector(Integer.parseInt((String)context.getSessionValue("TemplateTaskSort")), template.getTasks())); 
      form = buildForm(context, template, command);
      form.addElement(new FormHidden("cmd", "template-editor"));
      context.putDelivery("Form", form);
      return context.includeJSP("template-editor.jsp");
    } 
    Vector contents = null;
    contents = notepad.getAllContents();
    notepad.setSwitchToTaskVisible(false);
    if (contents != null && contents.size() > 0) {
      form = buildNewForm(context, command);
      context.putDelivery("Form", form);
      return context.includeJSP("template-editor.jsp");
    } 
    form = buildNewForm(context, command);
    context.putDelivery("Form", form);
    if (context.getSessionValue("NOTEPAD_TEMPLATES_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_TEMPLATES_VISIBLE")); 
    return context.includeJSP("blank-template-editor.jsp");
  }
  
  private boolean editNew(Dispatcher dispatcher, Context context, String command) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getTemplateNotepad(context, user.getUserId());
    notepad.setSwitchToTaskVisible(false);
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Form form = buildNewForm(context, command);
    context.putDelivery("Form", form);
    return context.includeJSP("template-editor.jsp");
  }
  
  private boolean editSaveNew(Dispatcher dispatcher, Context context, String command) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getTemplateNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Template template = new Template();
    Form form = buildNewForm(context, command);
    form.addElement(new FormHidden("cmd", "template-edit-save-new"));
    form.setValues(context);
    template.setTemplateID(-1);
    template.setTasks(null);
    String name = form.getStringValue("templateName");
    template.setTemplateName(name);
    String owner = form.getStringValue("owner");
    int ownerInt = 0;
    if (owner != null && !owner.equals(""))
      try {
        ownerInt = Integer.parseInt(owner);
      } catch (NumberFormatException numberFormatException) {} 
    template.setOwner((Family)MilestoneHelper.getStructureObject(ownerInt));
    template.setProdType(Integer.parseInt(form.getStringValue("ProdType")));
    String productLine = form.getStringValue("productLine");
    if (productLine.length() > 0)
      template.setProductCategory((ProductCategory)SelectionManager.getLookupObject(productLine, Cache.getProductCategories())); 
    String configuration = form.getStringValue("Configuration");
    if (configuration.length() > 0)
      template.setSelectionConfig(SelectionManager.getSelectionConfigObject(configuration, Cache.getSelectionConfigs())); 
    String subConfiguration = form.getStringValue("SubConfiguration");
    if (subConfiguration.length() > 0)
      template.setSelectionSubConfig(SelectionManager.getSelectionSubConfigObject(subConfiguration, template.getSelectionConfig())); 
    String releaseType = form.getStringValue("releaseType");
    if (releaseType.length() > 0)
      template.setReleaseType((ReleaseType)SelectionManager.getLookupObject(releaseType, Cache.getReleaseTypes())); 
    if (!TemplateManager.getInstance().isDuplicate(template)) {
      if (!form.isUnchanged()) {
        FormValidation formValidation = form.validate();
        if (formValidation.isGood()) {
          Template savedTemplate = TemplateManager.getInstance().saveTemplate(template, user.getUserId());
          if (context.getSessionValue("TemplateTaskSort") != null)
            savedTemplate.setTasks(sortScreenTaskVector(Integer.parseInt((String)context.getSessionValue("TemplateTaskSort")), savedTemplate.getTasks())); 
          context.putDelivery("Form", form);
          notepad.setAllContents(null);
          notepad.newSelectedReset();
          notepad = getTemplateNotepad(context, user.getUserId());
          notepad.setSelected(savedTemplate);
          template = (Template)notepad.validateSelected();
          context.putSessionValue("Template", template);
        } else {
          context.putDelivery("FormValidation", formValidation);
          form.addElement(new FormHidden("OrderBy", "", true));
          context.putDelivery("Form", form);
        } 
      } 
    } else {
      context.putDelivery("AlertMessage", MilestoneMessage.getMessage(5, 
            new String[] { "Template", template.getTempateName() }));
      return edit(dispatcher, context, command);
    } 
    return edit(dispatcher, context, "template-editor");
  }
  
  private boolean editSave(Dispatcher dispatcher, Context context, String command) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getTemplateNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Template template = (Template)context.getSessionValue("Template");
    int secureLevel = getTemplatePermissions(template, user);
    if (secureLevel < 2)
      notepad.setSwitchToTaskVisible(false); 
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Form form = buildForm(context, template, command);
    form.addElement(new FormHidden("cmd", "template-editor"));
    if (TemplateManager.getInstance().isTimestampValid(template)) {
      form.setValues(context);
      String name = form.getStringValue("templateName");
      template.setTemplateName(name);
      String owner = form.getStringValue("owner");
      if (owner.length() > 0)
        template.setOwner((Family)MilestoneHelper.getStructureObject(Integer.parseInt(owner))); 
      template.setProdType(Integer.parseInt(form.getStringValue("ProdType")));
      String productLine = form.getStringValue("productLine");
      if (productLine.length() > 0)
        template.setProductCategory((ProductCategory)SelectionManager.getLookupObject(productLine, Cache.getProductCategories())); 
      String configuration = form.getStringValue("Configuration");
      if (configuration.length() > 0)
        template.setSelectionConfig(SelectionManager.getSelectionConfigObject(configuration, Cache.getSelectionConfigs())); 
      String subConfiguration = form.getStringValue("SubConfiguration");
      if (subConfiguration.length() > 0)
        template.setSelectionSubConfig(SelectionManager.getSelectionSubConfigObject(subConfiguration, template.getSelectionConfig())); 
      String releaseType = form.getStringValue("releaseType");
      if (releaseType.length() > 0)
        template.setReleaseType((ReleaseType)SelectionManager.getLookupObject(releaseType, Cache.getReleaseTypes())); 
      Template savedTemplate = null;
      if (!TemplateManager.getInstance().isDuplicate(template)) {
        if (!form.isUnchanged()) {
          FormValidation formValidation = form.validate();
          if (formValidation.isGood()) {
            if (command.equalsIgnoreCase("template-copy-save")) {
              savedTemplate = TemplateManager.getInstance().saveTemplateCopiedTemplate(template, user.getUserId());
            } else {
              savedTemplate = TemplateManager.getInstance().saveTemplate(template, user.getUserId());
            } 
            FormElement lastUpdated = form.getElement("lastupdateddate");
            lastUpdated.setValue(MilestoneHelper.getLongDate(savedTemplate.getLastUpdateDate()));
            if (context.getSessionValue("TemplateTaskSort") != null)
              savedTemplate.setTasks(sortScreenTaskVector(Integer.parseInt((String)context.getSessionValue("TemplateTaskSort")), savedTemplate.getTasks())); 
            notepad.setAllContents(null);
            notepad = getTemplateNotepad(context, user.getUserId());
            notepad.setSelected(savedTemplate);
            template = (Template)notepad.validateSelected();
            context.putSessionValue("Template", template);
            if (template == null) {
              form = buildNewForm(context, command);
              context.putDelivery("Form", form);
              if (context.getSessionValue("NOTEPAD_TEMPLATES_VISIBLE") != null)
                context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_TEMPLATES_VISIBLE")); 
              return context.includeJSP("blank-template-editor.jsp");
            } 
            if (template == savedTemplate) {
              form = buildForm(context, template, command);
              form.addElement(new FormHidden("cmd", "template-editor"));
            } else {
              edit(dispatcher, context, command);
              return true;
            } 
          } else {
            context.putDelivery("FormValidation", formValidation);
          } 
        } 
      } else {
        context.putDelivery("AlertMessage", MilestoneMessage.getMessage(5, 
              new String[] { "Template", template.getTempateName() }));
      } 
    } else {
      context.putDelivery("AlertMessage", "The record is unavailable for editing at this time.  Please refresh your screen and edit it at a later time.");
    } 
    context.putDelivery("Form", form);
    return context.includeJSP("template-editor.jsp");
  }
  
  private boolean editDelete(Dispatcher dispatcher, Context context, String command) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getTemplateNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Template template = MilestoneHelper.getScreenTemplate(context);
    if (template != null) {
      int secureLevel = getTemplatePermissions(template, user);
      if (secureLevel < 2)
        notepad.setSwitchToTaskVisible(false); 
      MilestoneHelper.putNotepadIntoSession(notepad, context);
      Vector tasks = template.getTasks();
      for (int i = 0; i < tasks.size(); i++) {
        Task task = (Task)tasks.elementAt(i);
        TemplateManager.getInstance().deleteTemplateDetail(template, task, user.getUserId());
      } 
      TemplateManager.getInstance().deleteTemplate(template, user.getUserId());
      notepad.setAllContents(null);
      notepad = getTemplateNotepad(context, user.getUserId());
      notepad.setSelected(null);
    } 
    return edit(dispatcher, context, command);
  }
  
  private boolean locator(Dispatcher dispatcher, Context context, String command) { return context.includeJSP("template-locator.jsp"); }
  
  private boolean locatorCopy(Dispatcher dispatcher, Context context, String command) { return context.includeJSP("template-locator-copy.jsp"); }
  
  private boolean assignToSchedule(Dispatcher dispatcher, Context context, String command) { return context.includeJSP("schedule-editor.jsp"); }
  
  private boolean editAssignTask(Dispatcher dispatcher, Context context, String command) { return context.includeJSP("template-editor.jsp"); }
  
  private boolean editDeleteTask(Dispatcher dispatcher, Context context, String command) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getTemplateNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Template template = MilestoneHelper.getScreenTemplate(context);
    Task task = new Task();
    int taskID = 0;
    try {
      taskID = Integer.parseInt(context.getParameter("taskID"));
    } catch (NumberFormatException e) {
      taskID = -1;
    } 
    task.setTaskID(taskID);
    TemplateManager.getInstance().deleteTemplateDetail(template, task, user.getUserId());
    template = TemplateManager.getInstance().getTemplate(template.getTemplateID(), true);
    context.putSessionValue("Template", template);
    notepad.setAllContents(null);
    notepad = getTemplateNotepad(context, user.getUserId());
    notepad.setSelected(template);
    return edit(dispatcher, context, command);
  }
  
  private boolean recommended(Dispatcher dispatcher, Context context, String command) { return context.includeJSP("template-recommended.jsp"); }
  
  protected Form buildForm(Context context, Template template, String command) {
    Calendar testDate = Calendar.getInstance();
    User sessionUser = MilestoneSecurity.getUser(context);
    Form form = new Form(this.application, "TemplateForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    context.putDelivery("Configuration", getJavaScriptTemplateConfig(context));
    int secureLevel = getTemplatePermissions(template, sessionUser);
    setButtonVisibilities(template, sessionUser, context, secureLevel, command);
    FormTextField templateName = new FormTextField("templateName", "", true, 20);
    templateName.setId("templateName");
    templateName.setMaxLength(30);
    templateName.addFormEvent("onBlur", "Javascript:checkField(this)");
    templateName.setValue(template.getTempateName());
    templateName.setTabIndex(1);
    form.addElement(templateName);
    Vector families = null;
    String familyId = "";
    if (template.getOwner() != null) {
      familyId = Integer.toString(template.getOwner().getStructureID());
      families = MilestoneHelper.getNonSecureUserFamilies(context);
    } else {
      familyId = "";
    } 
    if (command.equalsIgnoreCase("template-edit-copy")) {
      familyId = "";
      families = null;
      families = MilestoneHelper.getSecureUserFamilies(sessionUser, 1, context);
    } 
    FormDropDownMenu owner = MilestoneHelper.getCorporateStructureDropDown("owner", families, familyId, true, true);
    owner.setTabIndex(2);
    form.addElement(owner);
    String[] dvalues = new String[3];
    dvalues[0] = "0";
    dvalues[1] = "1";
    dvalues[2] = "2";
    String[] dlabels = new String[3];
    dlabels[0] = "Physical";
    dlabels[1] = "Digital";
    dlabels[2] = "Both";
    FormRadioButtonGroup prodType = new FormRadioButtonGroup("ProdType", 
        String.valueOf(template.getProdType()), dvalues, dlabels, false);
    prodType.addFormEvent("onClick", "initConfigs()");
    form.addElement(prodType);
    FormDropDownMenu productLine = MilestoneHelper.getLookupDropDown("productLine", Cache.getProductCategories(template.getProdType()), MilestoneHelper.getLookupObjectValue(template.getProductCategory()), true, true);
    productLine.setId("productLine");
    productLine.setTabIndex(3);
    form.addElement(productLine);
    FormDropDownMenu releaseType = MilestoneHelper.getLookupDropDown("releaseType", Cache.getReleaseTypes(), MilestoneHelper.getLookupObjectValue(template.getReleaseType()), true, true);
    releaseType.setTabIndex(4);
    form.addElement(releaseType);
    String configValue = "";
    if (template.getSelectionConfig() != null)
      configValue = template.getSelectionConfig().getSelectionConfigurationAbbreviation(); 
    FormDropDownMenu configuration = MilestoneHelper.getSelectionConfigurationDropDown("Configuration", configValue.trim(), true);
    configuration.setId("Configuration");
    configuration.setTabIndex(5);
    configuration.addFormEvent("onChange", "adjustSelection(this)");
    form.addElement(configuration);
    String subConfigValue = "";
    if (template.getSelectionSubConfig() != null)
      subConfigValue = template.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation(); 
    FormDropDownMenu subConfiguration = MilestoneHelper.getSelectionSubConfigurationDropDown("SubConfiguration", template.getSelectionConfig(), subConfigValue.trim(), true);
    subConfiguration.setTabIndex(6);
    form.addElement(subConfiguration);
    FormTextField lastUpdated = new FormTextField("lastUpdatedDate", false, 50);
    if (template.getLastUpdateDate() != null)
      lastUpdated.setValue(MilestoneHelper.getCustomFormatedDate(template.getLastUpdateDate(), "M/d/yyyy hh:mm:ss a 'ET'")); 
    form.addElement(lastUpdated);
    FormTextField lastUpdatedBy = new FormTextField("lastUpdatedBy", false, 50);
    if (UserManager.getInstance().getUser(template.getLastUpdatingUser()) != null)
      lastUpdatedBy.setValue(UserManager.getInstance().getUser(template.getLastUpdatingUser()).getName()); 
    form.addElement(lastUpdatedBy);
    Vector templateRights = new Vector();
    Vector tasks = template.getTasks();
    for (int i = 0; i < tasks.size(); i++) {
      Task task = (Task)tasks.get(i);
      if (task.getOwner() != null) {
        templateRights.add(new String(Integer.toString(TemplateManager.getInstance().getTaskEditAccess(sessionUser, task.getOwner().getStructureID()))));
      } else {
        templateRights.add("0");
      } 
      task = null;
    } 
    context.putDelivery("templateRights", templateRights);
    FormTextField templateNameSearch = new FormTextField("templateNameSrch", "", false, 20);
    templateNameSearch.setId("templateNameSrch");
    form.addElement(templateNameSearch);
    String[] dsearchvalues = new String[3];
    dsearchvalues[0] = "0";
    dsearchvalues[1] = "1";
    dsearchvalues[2] = "2";
    String[] dsearchlabels = new String[3];
    dsearchlabels[0] = "Physical";
    dsearchlabels[1] = "Digital";
    dsearchlabels[2] = "Both";
    FormRadioButtonGroup searchProdType = new FormRadioButtonGroup("ProdTypeSearch", "2", dsearchvalues, dsearchlabels, false);
    searchProdType.addFormEvent("onClick", "initSearchConfigs()");
    form.addElement(searchProdType);
    FormDropDownMenu configurationSearch = MilestoneHelper.getSelectionConfigurationDropDown("configurationSrch", "", false);
    configurationSearch.setId("configurationSrch");
    form.addElement(configurationSearch);
    FormDropDownMenu ownerSearch = MilestoneHelper.getCorporateStructureDropDown("ownerSrch", families, "0", false, true);
    ownerSearch.setId("ownerSrch");
    form.addElement(ownerSearch);
    form.addElement(new FormHidden("OrderBy", ""));
    form.addElement(new FormHidden("OrderTasksBy", ""));
    context.putSessionValue("Template", template);
    if (context.getSessionValue("NOTEPAD_TEMPLATES_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_TEMPLATES_VISIBLE")); 
    return form;
  }
  
  protected Form buildNewForm(Context context, String command) {
    Calendar testDate = Calendar.getInstance();
    User sessionUser = MilestoneSecurity.getUser(context);
    Form form = new Form(this.application, "TemplateForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    Template template = new Template();
    template.setTemplateID(-1);
    int secureLevel = getTemplatePermissions(template, sessionUser);
    setButtonVisibilities(template, sessionUser, context, secureLevel, command);
    context.putDelivery("Configuration", getJavaScriptTemplateConfig(context));
    FormTextField templateName = new FormTextField("templateName", "", true, 20);
    templateName.setId("templateName");
    templateName.setMaxLength(30);
    templateName.addFormEvent("onBlur", "Javascript:checkField(this)");
    templateName.setTabIndex(1);
    form.addElement(templateName);
    Vector families = null;
    this.log.debug("before getSecureUserFamilies...");
    families = MilestoneHelper.getSecureUserFamilies(sessionUser, 1, context);
    this.log.debug("families...");
    FormDropDownMenu owner = MilestoneHelper.getCorporateStructureDropDown("owner", families, "0", true, true);
    owner.setTabIndex(2);
    form.addElement(owner);
    String[] dvalues = new String[3];
    dvalues[0] = "0";
    dvalues[1] = "1";
    dvalues[2] = "2";
    String[] dlabels = new String[3];
    dlabels[0] = "Physical";
    dlabels[1] = "Digital";
    dlabels[2] = "Both";
    FormRadioButtonGroup prodType = new FormRadioButtonGroup("ProdType", 
        "", dvalues, dlabels, false);
    prodType.addFormEvent("onChange", "initConfigs()");
    form.addElement(prodType);
    FormDropDownMenu productLine = MilestoneHelper.getLookupDropDown("productLine", Cache.getProductCategories(), "0", true, true);
    productLine.setId("productLine");
    productLine.setTabIndex(3);
    form.addElement(productLine);
    FormDropDownMenu releaseType = MilestoneHelper.getLookupDropDown("releaseType", Cache.getReleaseTypes(), "0", true, true);
    releaseType.setTabIndex(4);
    form.addElement(releaseType);
    FormDropDownMenu configuration = MilestoneHelper.getSelectionConfigurationDropDown("Configuration", "0", true);
    configuration.setId("Configuration");
    configuration.setTabIndex(5);
    configuration.addFormEvent("onChange", "adjustSelection(this)");
    form.addElement(configuration);
    FormDropDownMenu subConfiguration = new FormDropDownMenu("SubConfiguration", "&nbsp;", "&nbsp;", true);
    subConfiguration.setTabIndex(6);
    form.addElement(subConfiguration);
    FormTextField lastUpdated = new FormTextField("lastUpdatedDate", false, 50);
    form.addElement(lastUpdated);
    FormTextField lastUpdatedBy = new FormTextField("lastUpdatedBy", false, 50);
    form.addElement(lastUpdatedBy);
    Vector templateRights = new Vector();
    templateRights.add("0");
    context.putDelivery("templateRights", templateRights);
    FormTextField templateNameSearch = new FormTextField("templateNameSrch", "", false, 20);
    templateNameSearch.setId("templateNameSrch");
    form.addElement(templateNameSearch);
    String[] dsearchvalues = new String[3];
    dsearchvalues[0] = "0";
    dsearchvalues[1] = "1";
    dsearchvalues[2] = "2";
    String[] dsearchlabels = new String[3];
    dsearchlabels[0] = "Physical";
    dsearchlabels[1] = "Digital";
    dsearchlabels[2] = "Both";
    FormRadioButtonGroup searchProdType = new FormRadioButtonGroup("ProdTypeSearch", "2", dsearchvalues, dsearchlabels, false);
    searchProdType.addFormEvent("onClick", "initSearchConfigs()");
    form.addElement(searchProdType);
    FormDropDownMenu configurationSearch = MilestoneHelper.getSelectionConfigurationDropDown("configurationSrch", "", false);
    configurationSearch.setId("configurationSrch");
    form.addElement(configurationSearch);
    FormDropDownMenu ownerSearch = MilestoneHelper.getCorporateStructureDropDown("ownerSrch", families, "0", false, true);
    ownerSearch.setId("ownerSrch");
    form.addElement(ownerSearch);
    form.addElement(new FormHidden("cmd", "template-edit-new"));
    form.addElement(new FormHidden("OrderBy", ""));
    form.addElement(new FormHidden("OrderTasksBy", ""));
    if (context.getSessionValue("NOTEPAD_TEMPLATES_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_TEMPLATES_VISIBLE")); 
    return form;
  }
  
  private boolean editCopy(Dispatcher dispatcher, Context context, String command) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getTemplateNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Template targetTemplate = MilestoneHelper.getScreenTemplate(context);
    int secureLevel = getTemplatePermissions(targetTemplate, user);
    if (secureLevel < 2)
      notepad.setSwitchToTaskVisible(false); 
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Template copiedTemplate = null;
    try {
      copiedTemplate = (Template)targetTemplate.clone();
    } catch (CloneNotSupportedException cloneNotSupportedException) {}
    copiedTemplate.setTemplateID(-1);
    copiedTemplate.setTemplateName("");
    copiedTemplate.setReleaseType(null);
    copiedTemplate.setProductCategory(null);
    copiedTemplate.setSelectionConfig(null);
    copiedTemplate.setSelectionSubConfig(null);
    Form form = null;
    form = buildForm(context, copiedTemplate, command);
    form.addElement(new FormHidden("cmd", "template-edit-copy"));
    if (context.getSessionValue("TemplateTaskSort") != null)
      copiedTemplate.setTasks(sortScreenTaskVector(Integer.parseInt((String)context.getSessionValue("TemplateTaskSort")), copiedTemplate.getTasks())); 
    context.putSessionValue("Template", copiedTemplate);
    context.putDelivery("Form", form);
    if (context.getSessionValue("NOTEPAD_TEMPLATES_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_TEMPLATES_VISIBLE")); 
    return context.includeJSP("template-editor.jsp");
  }
  
  public Notepad getTaskNotepad(Context context, int userId, int templateId) {
    Vector contents = new Vector();
    if (MilestoneHelper.getNotepadFromSession(19, context) != null) {
      Notepad notepad = MilestoneHelper.getNotepadFromSession(19, context);
      if (notepad.getAllContents() == null) {
        contents = TemplateManager.getInstance().getTemplateTaskNotepadList(templateId, userId, notepad, context);
        notepad.setAllContents(contents);
      } 
      return notepad;
    } 
    String[] columnNames = { "Task Name", "Wks", "Own", "Dpt" };
    contents = TemplateManager.getInstance().getTemplateTaskNotepadList(templateId, userId, null, context);
    return new Notepad(contents, 0, 15, "Unassigned Tasks", 19, columnNames);
  }
  
  private boolean editTemplateTasks(Dispatcher dispatcher, Context context, String command) {
    User user = MilestoneSecurity.getUser(context);
    Template template = MilestoneHelper.getScreenTemplate(context);
    Notepad notepad = getTaskNotepad(context, user.getUserId(), template.getTemplateID());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Form form = null;
    if (template != null) {
      form = buildTaskForm(context, template, notepad, command);
      form.addElement(new FormHidden("cmd", "template-task-editor"));
      context.putDelivery("Form", form);
      return context.includeJSP("template-task-editor.jsp");
    } 
    return dispatcher.redispatch(context, "template-editor");
  }
  
  private boolean editTemplateTaskSave(Dispatcher dispatcher, Context context, String command) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getTemplateNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Template template = (Template)context.getSessionValue("Template");
    Notepad taskNotepad = getTaskNotepad(context, user.getUserId(), template.getTemplateID());
    MilestoneHelper.putNotepadIntoSession(taskNotepad, context);
    Form form = buildTaskForm(context, template, taskNotepad, command);
    form.addElement(new FormHidden("cmd", "template-task-editor", true));
    form.setValues(context);
    String name = form.getStringValue("templateName");
    template.setTemplateName(name);
    String owner = form.getStringValue("owner");
    template.setOwner((Family)MilestoneHelper.getStructureObject(Integer.parseInt(owner)));
    template.setProdType(Integer.parseInt(form.getStringValue("ProdType")));
    String productLine = form.getStringValue("productLine");
    template.setProductCategory((ProductCategory)SelectionManager.getLookupObject(productLine, Cache.getProductCategories()));
    String configuration = form.getStringValue("Configuration");
    template.setSelectionConfig(SelectionManager.getSelectionConfigObject(configuration, Cache.getSelectionConfigs()));
    String subConfiguration = form.getStringValue("SubConfiguration");
    template.setSelectionSubConfig(SelectionManager.getSelectionSubConfigObject(subConfiguration, template.getSelectionConfig()));
    String releaseType = form.getStringValue("releaseType");
    template.setReleaseType((ReleaseType)SelectionManager.getLookupObject(releaseType, Cache.getReleaseTypes()));
    Template savedTemplate = null;
    if (!form.isUnchanged()) {
      FormValidation formValidation = form.validate();
      if (formValidation.isGood()) {
        if (command.equalsIgnoreCase("template-task-copy-save")) {
          savedTemplate = TemplateManager.getInstance().saveTemplateCopiedTemplate(template, user.getUserId());
        } else {
          savedTemplate = TemplateManager.getInstance().saveTemplate(template, user.getUserId());
        } 
        FormElement lastUpdated = form.getElement("lastupdateddate");
        lastUpdated.setValue(MilestoneHelper.getLongDate(savedTemplate.getLastUpdateDate()));
        if (context.getSessionValue("TemplateTaskSort") != null)
          savedTemplate.setTasks(sortScreenTaskVector(Integer.parseInt((String)context.getSessionValue("TemplateTaskSort")), savedTemplate.getTasks())); 
        context.putSessionValue("Template", savedTemplate);
        context.putDelivery("Form", form);
        notepad.setAllContents(null);
        notepad = getTemplateNotepad(context, user.getUserId());
        notepad.setSelected(savedTemplate);
      } else {
        context.putDelivery("FormValidation", formValidation);
      } 
    } 
    context.putDelivery("Form", form);
    return context.includeJSP("template-task-editor.jsp");
  }
  
  protected Form buildTaskForm(Context context, Template template, Notepad notepadTask, String command) {
    Calendar testDate = Calendar.getInstance();
    User sessionUser = MilestoneSecurity.getUser(context);
    Form form = new Form(this.application, "TemplateForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    int secureLevel = getTemplatePermissions(template, sessionUser);
    setButtonVisibilities(template, sessionUser, context, secureLevel, command);
    if (context.getSessionValue("TemplateTaskSort") != null)
      template.setTasks(sortScreenTaskVector(Integer.parseInt((String)context.getSessionValue("TemplateTaskSort")), template.getTasks())); 
    context.putDelivery("Configuration", getJavaScriptTemplateConfig(context));
    Vector notepadPageContents = notepadTask.getPageContents();
    Task task = null;
    FormCheckBox notepadCheckbox = null;
    for (int j = 0; j < notepadPageContents.size(); j++) {
      task = (Task)notepadPageContents.get(j);
      notepadCheckbox = new FormCheckBox(String.valueOf(task.getTaskID()), "", false, false);
      form.addElement(notepadCheckbox);
      task = null;
      notepadCheckbox = null;
    } 
    FormTextField templateName = new FormTextField("templateName", "", true, 20);
    templateName.setId("templateName");
    templateName.setTabIndex(1);
    templateName.setMaxLength(30);
    templateName.addFormEvent("onBlur", "Javascript:checkField(this)");
    templateName.setValue(template.getTempateName());
    form.addElement(templateName);
    Vector families = null;
    String familyId = "";
    if (template.getOwner() != null) {
      familyId = Integer.toString(template.getOwner().getStructureID());
      families = MilestoneHelper.getNonSecureUserFamilies(context);
    } else {
      familyId = "";
    } 
    if (command.equalsIgnoreCase("template-task-edit-copy")) {
      familyId = "";
      families = null;
      families = MilestoneHelper.getSecureUserFamilies(sessionUser, 1, context);
    } 
    FormDropDownMenu owner = MilestoneHelper.getCorporateStructureDropDown("owner", families, familyId, true, true);
    owner.setTabIndex(2);
    form.addElement(owner);
    String[] dvalues = new String[3];
    dvalues[0] = "0";
    dvalues[1] = "1";
    dvalues[2] = "2";
    String[] dlabels = new String[3];
    dlabels[0] = "Physical";
    dlabels[1] = "Digital";
    dlabels[2] = "Both";
    FormRadioButtonGroup prodType = new FormRadioButtonGroup("ProdType", 
        String.valueOf(template.getProdType()), dvalues, dlabels, false);
    prodType.addFormEvent("onClick", "initConfigs()");
    form.addElement(prodType);
    FormDropDownMenu productLine = MilestoneHelper.getLookupDropDown("productLine", Cache.getProductCategories(template.getProdType()), MilestoneHelper.getLookupObjectValue(template.getProductCategory()), true, true);
    productLine.setId("productLine");
    productLine.setTabIndex(3);
    form.addElement(productLine);
    String configValue = "";
    if (template.getSelectionConfig() != null)
      configValue = template.getSelectionConfig().getSelectionConfigurationAbbreviation(); 
    FormDropDownMenu configuration = MilestoneHelper.getSelectionConfigurationDropDown("Configuration", configValue.trim(), true);
    configuration.setId("Configuration");
    configuration.setTabIndex(4);
    configuration.addFormEvent("onChange", "adjustSelection(this)");
    form.addElement(configuration);
    String subConfigValue = "";
    if (template.getSelectionSubConfig() != null)
      subConfigValue = template.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation(); 
    FormDropDownMenu subConfiguration = MilestoneHelper.getSelectionSubConfigurationDropDown("SubConfiguration", template.getSelectionConfig(), subConfigValue.trim(), true);
    subConfiguration.setTabIndex(6);
    form.addElement(subConfiguration);
    FormDropDownMenu releaseType = MilestoneHelper.getLookupDropDown("releaseType", Cache.getReleaseTypes(), MilestoneHelper.getLookupObjectValue(template.getReleaseType()), true, true);
    releaseType.setTabIndex(5);
    form.addElement(releaseType);
    FormTextField lastUpdated = new FormTextField("lastUpdatedDate", false, 50);
    if (template.getLastUpdateDate() != null)
      lastUpdated.setValue(MilestoneHelper.getLongDate(template.getLastUpdateDate())); 
    form.addElement(lastUpdated);
    FormTextField lastUpdatedBy = new FormTextField("lastUpdatedBy", true, 50);
    if (UserManager.getInstance().getUser(template.getLastUpdatingUser()) != null)
      lastUpdatedBy.setValue(UserManager.getInstance().getUser(template.getLastUpdatingUser()).getLogin()); 
    form.addElement(lastUpdatedBy);
    Vector templateRights = new Vector();
    Vector tasks = template.getTasks();
    for (int i = 0; i < tasks.size(); i++) {
      Task templateTask = (Task)tasks.get(i);
      if (templateTask.getOwner() != null) {
        templateRights.add(new String(Integer.toString(TemplateManager.getInstance().getTaskEditAccess(sessionUser, templateTask.getOwner().getStructureID()))));
      } else {
        templateRights.add("0");
      } 
      templateTask = null;
    } 
    context.putDelivery("templateRights", templateRights);
    FormTextField TaskNameSearch = new FormTextField("TaskNameSearch", "", false, 20);
    TaskNameSearch.setId("TaskNameSearch");
    form.addElement(TaskNameSearch);
    FormRadioButtonGroup KeyTaskSearch = new FormRadioButtonGroup("KeyTaskSearch", "", "Yes,No", false);
    form.addElement(KeyTaskSearch);
    FormDropDownMenu TaskOwnerSearch = MilestoneHelper.getCorporateStructureDropDown("TaskOwnerSearch", families, "0", false, true);
    form.addElement(TaskOwnerSearch);
    FormDropDownMenu TaskDepartmentSearch = MilestoneHelper.getDepartmentDropDown("TaskDepartmentSearch", "", false);
    form.addElement(TaskDepartmentSearch);
    form.addElement(new FormHidden("OrderBy", ""));
    form.addElement(new FormHidden("OrderTasksBy", ""));
    context.putSessionValue("Template", template);
    if (context.getSessionValue("NOTEPAD_UNASSIGNED_TASKS_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_UNASSIGNED_TASKS_VISIBLE")); 
    return form;
  }
  
  protected Form buildNewTaskForm(Context context, Notepad notepadTasks, String command) {
    Calendar testDate = Calendar.getInstance();
    User sessionUser = MilestoneSecurity.getUser(context);
    Form form = new Form(this.application, "TemplateForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    Template template = new Template();
    template.setTemplateID(-1);
    int secureLevel = getTemplatePermissions(template, sessionUser);
    setButtonVisibilities(template, sessionUser, context, secureLevel, command);
    context.putDelivery("Configuration", getJavaScriptTemplateConfig(context));
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
    FormTextField templateName = new FormTextField("templateName", "", true, 20);
    templateName.setId("templateName");
    templateName.setTabIndex(1);
    templateName.setMaxLength(30);
    templateName.addFormEvent("onBlur", "Javascript:checkField(this)");
    form.addElement(templateName);
    Vector families = null;
    families = MilestoneHelper.getSecureUserFamilies(sessionUser, 1, context);
    FormDropDownMenu owner = MilestoneHelper.getCorporateStructureDropDown("owner", families, "", true, true);
    owner.setTabIndex(2);
    form.addElement(owner);
    String[] dvalues = new String[3];
    dvalues[0] = "0";
    dvalues[1] = "1";
    dvalues[2] = "2";
    String[] dlabels = new String[3];
    dlabels[0] = "Physical";
    dlabels[1] = "Digital";
    dlabels[2] = "Both";
    FormRadioButtonGroup prodType = new FormRadioButtonGroup("ProdType", 
        "", dvalues, dlabels, false);
    prodType.addFormEvent("onChange", "initConfigs()");
    form.addElement(prodType);
    FormDropDownMenu productLine = MilestoneHelper.getLookupDropDown("productLine", Cache.getProductCategories(), "0", true, true);
    productLine.setId("productLine");
    productLine.setTabIndex(3);
    form.addElement(productLine);
    FormDropDownMenu configuration = MilestoneHelper.getSelectionConfigurationDropDown("Configuration", "0", true);
    configuration.setId("Configuration");
    configuration.setTabIndex(4);
    configuration.addFormEvent("onChange", "adjustSelection(this)");
    form.addElement(configuration);
    FormDropDownMenu subConfiguration = new FormDropDownMenu("SubConfiguration", "", "", true);
    subConfiguration.setTabIndex(6);
    form.addElement(subConfiguration);
    FormDropDownMenu releaseType = MilestoneHelper.getLookupDropDown("releaseType", Cache.getReleaseTypes(), "0", true, true);
    releaseType.setTabIndex(5);
    form.addElement(releaseType);
    FormTextField lastUpdated = new FormTextField("lastUpdatedDate", false, 50);
    form.addElement(lastUpdated);
    FormTextField lastUpdatedBy = new FormTextField("lastUpdatedBy", false, 50);
    form.addElement(lastUpdatedBy);
    Vector templateRights = new Vector();
    templateRights.add("0");
    context.putDelivery("templateRights", templateRights);
    FormTextField TaskNameSearch = new FormTextField("TaskNameSearch", "", false, 20);
    TaskNameSearch.setId("TaskNameSearch");
    form.addElement(TaskNameSearch);
    FormRadioButtonGroup KeyTaskSearch = new FormRadioButtonGroup("KeyTaskSearch", "", "Yes,No", false);
    form.addElement(KeyTaskSearch);
    FormDropDownMenu TaskOwnerSearch = MilestoneHelper.getCorporateStructureDropDown("TaskOwnerSearch", families, "0", false, true);
    form.addElement(TaskOwnerSearch);
    Vector deptList = MilestoneHelper.getDepartmentList();
    String dept = "";
    String deptStringList = "";
    for (int i = 0; i < deptList.size(); i++) {
      dept = (String)deptList.get(i);
      if (i == 0) {
        deptStringList = "," + dept;
      } else {
        deptStringList = String.valueOf(deptStringList) + "," + dept;
      } 
    } 
    FormDropDownMenu TaskDepartmentSearch = new FormDropDownMenu("TaskDepartmentSearch", "", "0," + deptStringList, "&nbsp;," + deptStringList, false);
    form.addElement(TaskDepartmentSearch);
    form.addElement(new FormHidden("cmd", "template-task-edit-new"));
    form.addElement(new FormHidden("OrderBy", ""));
    form.addElement(new FormHidden("OrderTasksBy", ""));
    if (context.getSessionValue("NOTEPAD_UNASSIGNED_TASKS_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_UNASSIGNED_TASKS_VISIBLE")); 
    return form;
  }
  
  private boolean addTask(Dispatcher dispatcher, Context context, String command) {
    User user = MilestoneSecurity.getUser(context);
    Template template = MilestoneHelper.getScreenTemplate(context);
    Notepad notepad = getTaskNotepad(context, user.getUserId(), template.getTemplateID());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Form form = buildTaskForm(context, template, notepad, command);
    form.setValues(context);
    Task task = null;
    Vector notepadPageContents = notepad.getPageContents();
    for (int j = 0; j < notepadPageContents.size(); j++) {
      task = (Task)notepadPageContents.get(j);
      if (((FormCheckBox)form.getElement(String.valueOf(task.getTaskID()))).isChecked())
        if (task.getActiveFlag())
          TemplateManager.getInstance().addTask(template, task, user.getUserId());  
      task = null;
    } 
    notepad.setAllContents(null);
    notepad = getTaskNotepad(context, user.getUserId(), template.getTemplateID());
    notepad.goToSelectedPage();
    return editTemplateTasks(dispatcher, context, command);
  }
  
  private boolean editTemplateTaskNew(Dispatcher dispatcher, Context context, String command) {
    User user = MilestoneSecurity.getUser(context);
    Template template = MilestoneHelper.getScreenTemplate(context);
    Notepad notepad = getTaskNotepad(context, user.getUserId(), template.getTemplateID());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Form form = buildNewTaskForm(context, notepad, command);
    context.putDelivery("Form", form);
    return context.includeJSP("template-task-editor.jsp");
  }
  
  private boolean editTemplateTaskSaveNew(Dispatcher dispatcher, Context context, String command) {
    User user = MilestoneSecurity.getUser(context);
    Template oldTemplate = MilestoneHelper.getScreenTemplate(context);
    Notepad notepad = getTaskNotepad(context, user.getUserId(), oldTemplate.getTemplateID());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Notepad templateNotepad = getTemplateNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Template template = new Template();
    Form form = buildNewTaskForm(context, notepad, command);
    form.addElement(new FormHidden("cmd", "template-task-editor"));
    form.setValues(context);
    template.setTemplateID(-1);
    template.setTasks(null);
    String name = form.getStringValue("templateName");
    template.setTemplateName(name);
    String owner = form.getStringValue("owner");
    template.setOwner((Family)MilestoneHelper.getStructureObject(Integer.parseInt(owner)));
    template.setProdType(Integer.parseInt(form.getStringValue("ProdType")));
    String productLine = form.getStringValue("productLine");
    template.setProductCategory((ProductCategory)SelectionManager.getLookupObject(productLine, Cache.getProductCategories()));
    String configuration = form.getStringValue("Configuration");
    template.setSelectionConfig(SelectionManager.getSelectionConfigObject(configuration, Cache.getSelectionConfigs()));
    String subConfiguration = form.getStringValue("SubConfiguration");
    template.setSelectionSubConfig(SelectionManager.getSelectionSubConfigObject(subConfiguration, template.getSelectionConfig()));
    String releaseType = form.getStringValue("releaseType");
    template.setReleaseType((ReleaseType)SelectionManager.getLookupObject(releaseType, Cache.getReleaseTypes()));
    form.addElement(new FormHidden("cmd", "template-task-edit-save-new"));
    if (!form.isUnchanged()) {
      FormValidation formValidation = form.validate();
      if (formValidation.isGood()) {
        Template savedTemplate = TemplateManager.getInstance().saveTemplate(template, user.getUserId());
        if (context.getSessionValue("TemplateTaskSort") != null)
          savedTemplate.setTasks(sortScreenTaskVector(Integer.parseInt((String)context.getSessionValue("TemplateTaskSort")), savedTemplate.getTasks())); 
        context.putSessionValue("Template", savedTemplate);
        context.putDelivery("Form", form);
        templateNotepad.setAllContents(null);
        templateNotepad = getTemplateNotepad(context, user.getUserId());
        templateNotepad.setSelected(savedTemplate);
      } else {
        context.putDelivery("FormValidation", formValidation);
        form.addElement(new FormHidden("OrderBy", "", true));
        context.putDelivery("Form", form);
      } 
    } 
    return context.includeJSP("template-task-editor.jsp");
  }
  
  private boolean editTemplateTaskDelete(Dispatcher dispatcher, Context context, String command) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getTemplateNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Template template = MilestoneHelper.getScreenTemplate(context);
    Notepad taskNotepad = getTaskNotepad(context, user.getUserId(), template.getTemplateID());
    MilestoneHelper.putNotepadIntoSession(taskNotepad, context);
    Vector tasks = template.getTasks();
    for (int i = 0; i < tasks.size(); i++) {
      Task task = (Task)tasks.elementAt(i);
      TemplateManager.getInstance().deleteTemplateDetail(template, task, user.getUserId());
    } 
    TemplateManager.getInstance().deleteTemplate(template, user.getUserId());
    notepad.setAllContents(null);
    notepad = getTemplateNotepad(context, user.getUserId());
    notepad.setSelected(null);
    return editTemplateTasks(dispatcher, context, command);
  }
  
  private boolean editTemplateTaskCopy(Dispatcher dispatcher, Context context, String command) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getTemplateNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Template targetTemplate = MilestoneHelper.getScreenTemplate(context);
    Template copiedTemplate = null;
    Notepad taskNotepad = getTaskNotepad(context, user.getUserId(), targetTemplate.getTemplateID());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    try {
      copiedTemplate = (Template)targetTemplate.clone();
    } catch (CloneNotSupportedException cloneNotSupportedException) {}
    copiedTemplate.setTemplateID(-1);
    copiedTemplate.setTemplateName("");
    copiedTemplate.setReleaseType(null);
    copiedTemplate.setProductCategory(null);
    copiedTemplate.setSelectionConfig(null);
    Vector tasks = targetTemplate.getTasks();
    copiedTemplate.setTasks(targetTemplate.getTasks());
    Form form = buildTaskForm(context, copiedTemplate, taskNotepad, command);
    form.addElement(new FormHidden("cmd", "template-task-edit-copy"));
    if (context.getSessionValue("TemplateTaskSort") != null)
      copiedTemplate.setTasks(sortScreenTaskVector(Integer.parseInt((String)context.getSessionValue("TemplateTaskSort")), copiedTemplate.getTasks())); 
    context.putSessionValue("Template", copiedTemplate);
    context.putDelivery("Form", form);
    if (context.getSessionValue("NOTEPAD_UNASSIGNED_TASKS_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_UNASSIGNED_TASKS_VISIBLE")); 
    return context.includeJSP("template-task-editor.jsp");
  }
  
  private boolean editTemplateTaskDeleteTask(Dispatcher dispatcher, Context context, String command) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getTemplateNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Template template = MilestoneHelper.getScreenTemplate(context);
    Notepad taskNotepad = getTaskNotepad(context, user.getUserId(), template.getTemplateID());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Task task = new Task();
    int taskID = 0;
    try {
      taskID = Integer.parseInt(context.getParameter("taskID"));
    } catch (NumberFormatException e) {
      taskID = -1;
    } 
    task.setTaskID(taskID);
    TemplateManager.getInstance().deleteTemplateDetail(template, task, user.getUserId());
    template = TemplateManager.getInstance().getTemplate(template.getTemplateID(), true);
    context.putSessionValue("Template", template);
    notepad.setAllContents(null);
    notepad = getTemplateNotepad(context, user.getUserId());
    notepad.setSelected(template);
    return editTemplateTasks(dispatcher, context, command);
  }
  
  public static int getTemplatePermissions(Template template, User user) {
    int level = 0;
    int familyId = 0;
    if (template != null && template.getTemplateID() > -1) {
      Family family = template.getOwner();
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
          if (companyAcl != null && companyAcl.getAccessTemplate() > level)
            level = companyAcl.getAccessTemplate(); 
        } 
      } 
    } 
    return level;
  }
  
  public static int getTemplateNewButtonPermissions(User user) {
    int level = 0;
    Vector companies = MilestoneHelper.getUserCompanies(user.getUserId());
    if (companies != null)
      for (int i = 0; i < companies.size(); i++) {
        Company company = (Company)companies.get(i);
        if (company != null) {
          CompanyAcl companyAcl = MilestoneHelper.getScreenPermissions(company, user);
          if (companyAcl != null && companyAcl.getAccessTemplate() > level)
            level = companyAcl.getAccessTemplate(); 
        } 
      }  
    return level;
  }
  
  public void setButtonVisibilities(Template template, User user, Context context, int level, String command) {
    String copyVisible = "true";
    String saveVisible = "false";
    String deleteVisible = "false";
    String newVisible = "false";
    if (level > 1) {
      saveVisible = "true";
      deleteVisible = "true";
    } 
    int newButtonPermission = getTemplateNewButtonPermissions(user);
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
  
  private String getJavaScriptTemplateConfig(Context context) {
    Vector configs = Cache.getSelectionConfigs();
    Vector prodCats = Cache.getProductCategories();
    s1 = "function createChildren()\n{\n  lRoot = ";
    s1 = String.valueOf(s1) + "new Node(0, 'Root',2,[\n";
    for (int i = 0; i < configs.size(); i++) {
      SelectionConfiguration config = (SelectionConfiguration)configs.elementAt(i);
      if (config != null && !config.getInactive()) {
        s1 = String.valueOf(s1) + "new Node('" + config.getSelectionConfigurationAbbreviation() + "', '" + config.getSelectionConfigurationName() + "'," + config.getProdType() + ",[\n";
        Vector subConfigs = config.getSubConfigurations();
        for (int j = 0; j < subConfigs.size(); j++) {
          SelectionSubConfiguration subConfig = (SelectionSubConfiguration)subConfigs.elementAt(j);
          if (subConfig != null) {
            s1 = String.valueOf(s1) + "new Node('" + subConfig.getSelectionSubConfigurationAbbreviation() + "','" + subConfig.getSelectionSubConfigurationName() + "'," + subConfig.getProdType() + ",[\n";
            if (j == subConfigs.size() - 1) {
              s1 = String.valueOf(s1) + "])";
            } else {
              s1 = String.valueOf(s1) + "]),";
            } 
          } 
        } 
        if (i == configs.size() - 1) {
          s1 = String.valueOf(s1) + "])";
        } else {
          s1 = String.valueOf(s1) + "]),";
        } 
      } 
    } 
    s1 = String.valueOf(s1) + "]);\n }//end function createChildren";
    s1 = String.valueOf(s1) + "\n\nvar productCategories = ";
    s1 = String.valueOf(s1) + "new Node(0, 'Root',2,[\n";
    int activeCount = 0;
    for (int i = 0; i < prodCats.size(); i++) {
      ProductCategory prod = (ProductCategory)prodCats.elementAt(i);
      if (prod != null && !prod.getInactive())
        activeCount++; 
    } 
    int currentCount = 0;
    for (int i = 0; i < prodCats.size(); i++) {
      ProductCategory prod = (ProductCategory)prodCats.elementAt(i);
      if (prod != null && !prod.getInactive()) {
        s1 = String.valueOf(s1) + "new Node('" + prod.getAbbreviation() + "', '" + prod.getName() + "'," + prod.getProdType() + ",[\n";
        if (currentCount == activeCount - 1) {
          s1 = String.valueOf(s1) + "])";
        } else {
          s1 = String.valueOf(s1) + "]),";
        } 
        currentCount++;
      } 
    } 
    return String.valueOf(s1) + "]);\n ";
  }
  
  public static Vector sortScreenTaskVector(int sortBy, Vector taskVector) {
    Vector sortedVector = new Vector();
    Object[] taskArray = taskVector.toArray();
    if (taskVector != null) {
      switch (sortBy) {
        case 0:
          Arrays.sort(taskArray, new TaskNameComparator());
          break;
        case 1:
          Arrays.sort(taskArray, new TaskWksToReleaseComparator());
          break;
        case 2:
          Arrays.sort(taskArray, new TaskOwnerComparator());
          break;
        case 3:
          Arrays.sort(taskArray, new TaskAbbrComparator());
          break;
      } 
      for (int i = 0; i < taskArray.length; i++) {
        Task task = (Task)taskArray[i];
        sortedVector.add(task);
      } 
    } 
    return sortedVector;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\TemplateHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */