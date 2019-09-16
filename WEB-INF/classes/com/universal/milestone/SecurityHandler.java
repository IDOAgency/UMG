package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.gemini.Context;
import com.techempower.gemini.Dispatcher;
import com.techempower.gemini.FormCheckBox;
import com.techempower.gemini.FormDropDownMenu;
import com.techempower.gemini.FormElement;
import com.techempower.gemini.FormHidden;
import com.techempower.gemini.FormPasswordField;
import com.techempower.gemini.FormRadioButtonGroup;
import com.techempower.gemini.FormTextField;
import com.techempower.gemini.FormValidation;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.Acl;
import com.universal.milestone.Cache;
import com.universal.milestone.Company;
import com.universal.milestone.CompanyAcl;
import com.universal.milestone.Environment;
import com.universal.milestone.Form;
import com.universal.milestone.MilestoneConstants;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.MilestoneSecurity;
import com.universal.milestone.Notepad;
import com.universal.milestone.ReleasingFamily;
import com.universal.milestone.SecureHandler;
import com.universal.milestone.SecurityHandler;
import com.universal.milestone.User;
import com.universal.milestone.UserCompaniesTableManager;
import com.universal.milestone.UserManager;
import com.universal.milestone.UserPreferences;
import com.universal.milestone.UserPreferencesHandler;
import com.universal.milestone.UserPreferencesManager;
import java.util.Vector;

public class SecurityHandler extends SecureHandler {
  public static final String COMPONENT_CODE = "hSec";
  
  public static final String deptFilterFormat = "department.filter.";
  
  public GeminiApplication application;
  
  public ComponentLog log;
  
  protected long lCopiedUserId;
  
  public SecurityHandler(GeminiApplication application) {
    this.lCopiedUserId = 0L;
    this.application = application;
    this.log = application.getLog("hSec");
    ReleasingFamily.setDebugLog(this.log);
  }
  
  public String getDescription() { return "Security"; }
  
  public boolean acceptRequest(Dispatcher dispatcher, Context context, String command) {
    if (super.acceptRequest(dispatcher, context, command))
      if (command.startsWith("user-security") || 
        command.startsWith("user-environment-security") || 
        command.startsWith("family") || 
        command.startsWith("company") || 
        command.startsWith("division") || 
        command.startsWith("label"))
        return handleRequest(dispatcher, context, command);  
    return false;
  }
  
  public boolean handleRequest(Dispatcher dispatcher, Context context, String command) {
    if (command.equalsIgnoreCase("user-security-search")) {
      userSecuritySearch(context);
    } else if (command.equalsIgnoreCase("user-security-save")) {
      userSecuritySave(context);
    } else if (command.equalsIgnoreCase("user-security-copy")) {
      userSecurityCopy(context);
    } else if (command.equalsIgnoreCase("user-security-delete")) {
      userSecurityDelete(context);
    } else if (command.equalsIgnoreCase("user-security-search-results")) {
      userSecuritySearchResults(context);
    } else if (command.equalsIgnoreCase("user-security-editor")) {
      userSecurityEditor(context);
    } else if (command.equalsIgnoreCase("user-security-editor-info")) {
      userSecurityEditorInfo(context);
    } else if (command.equalsIgnoreCase("user-security-new")) {
      userSecurityNew(context);
    } else if (command.equalsIgnoreCase("user-security-sort")) {
      userSecuritySort(context);
    } else if (command.equalsIgnoreCase("user-environment-security-search")) {
      userCompanySecuritySearch(context);
    } else if (command.equalsIgnoreCase("user-environment-security-search-results")) {
      userCompanySecuritySearchResults(context);
    } else if (command.equalsIgnoreCase("user-environment-security-editor")) {
      userCompanySecurityEditor(context);
    } else if (command.equalsIgnoreCase("user-environment-security-save")) {
      userCompanySecuritySave(context);
    } else if (command.equalsIgnoreCase("user-environment-security-sort")) {
      userCompanySecuritySort(context);
    } else if (command.equalsIgnoreCase("user-security-save-releasing-family")) {
      userReleasingFamilySave(dispatcher, context);
    } 
    return true;
  }
  
  private boolean userSecuritySearch(Context context) {
    Notepad notepad = MilestoneHelper.getNotepadFromSession(7, context);
    UserManager.getInstance().setUserNotepadQuery(context, notepad);
    notepad.setAllContents(null);
    notepad.setSelected(null);
    userSecurityEditor(context);
    return true;
  }
  
  private boolean userSecuritySearchResults(Context context) { return context.includeJSP("user-security-search-results.jsp"); }
  
  private boolean userSecurityEditor(Context context) {
    context.removeSessionValue("copiedUserObj");
    Notepad notepad = getUsersNotepad(context);
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    context.removeSessionValue(NOTEPAD_SESSION_NAMES[21]);
    User user = MilestoneHelper.getScreenUser(context);
    context.putSessionValue("userInfo", "false");
    this.lCopiedUserId = 0L;
    if (user != null) {
      Form form = null;
      Form formUserPrefs = null;
      UserPreferencesHandler uph = new UserPreferencesHandler(this.application);
      if (user != null) {
        form = buildForm(context, user);
        formUserPrefs = uph.buildForm(context, user);
      } else {
        form = buildNewForm(context);
        notepad.setSwitchToCompaniesVisible(false);
        formUserPrefs = uph.buildNewForm(context);
      } 
      return context.includeJSP("user-security-editor.jsp");
    } 
    notepad.setSwitchToCompaniesVisible(false);
    return userGoToBlank(context);
  }
  
  private boolean userSecurityEditorInfo(Context context) {
    Notepad notepad = getUsersNotepad(context);
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    context.removeSessionValue(NOTEPAD_SESSION_NAMES[21]);
    User user = (User)context.getSessionValue("user");
    context.putSessionValue("securityUser", user);
    notepad.setSelected(user);
    context.putSessionValue("userInfo", "true");
    this.lCopiedUserId = 0L;
    if (user != null) {
      Form form = null;
      Form formUserPrefs = null;
      UserPreferencesHandler uph = new UserPreferencesHandler(this.application);
      if (user != null) {
        form = buildForm(context, user);
        formUserPrefs = uph.buildForm(context, user);
      } else {
        form = buildNewForm(context);
        formUserPrefs = uph.buildNewForm(context);
        notepad.setSwitchToCompaniesVisible(false);
      } 
      return context.includeJSP("user-security-editor.jsp");
    } 
    notepad.setSwitchToCompaniesVisible(false);
    return userGoToBlank(context);
  }
  
  private boolean userSecurityCopy(Context context) {
    User users = MilestoneSecurity.getUser(context);
    User sessionUser = (User)context.getSessionValue("securityUser");
    User user = MilestoneHelper.getScreenUser(context);
    Acl acl = user.getAcl();
    String aclString = user.getAclString();
    String filter = user.getFilter();
    String location = user.getLocation();
    User copiedUserObj = null;
    context.removeSessionValue("copiedUserObj");
    try {
      copiedUserObj = (User)user.clone();
      context.putSessionValue("copiedUserObj", copiedUserObj);
    } catch (CloneNotSupportedException cloneNotSupportedException) {}
    int userId = user.getUserId();
    user.setUserId(-1);
    this.lCopiedUserId = userId;
    String reportsTo = user.getReportsTo();
    int employedBy = user.getEmployedBy();
    Notepad notepad = getUsersNotepad(context);
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    User copiedUser = new User();
    try {
      copiedUser = (User)user.clone();
    } catch (CloneNotSupportedException cloneNotSupportedException) {}
    copiedUser.setPassword("");
    copiedUser.setReportsTo(reportsTo);
    copiedUser.setName(null);
    copiedUser.setLocation(location);
    copiedUser.setEmployedBy(employedBy);
    copiedUser.setReleasingFamily(sessionUser.getReleasingFamily());
    copiedUser.setAssignedEnvironments(sessionUser.getAssignedEnvironments());
    copiedUser.setFilter(filter);
    String[] filterArray = MilestoneHelper.parseFilter(filter);
    copiedUser.setAclString(aclString);
    copiedUser.setAcl(acl);
    Form form = null;
    copiedUser.setEmail("");
    copiedUser.setFax("");
    copiedUser.setPhone("");
    form = buildCopyForm(context, copiedUser, userId);
    form.addElement(new FormHidden("cmd", "user-security-copy"));
    copiedUser.setUserId(-1);
    copiedUser.setLogin("");
    context.putSessionValue("User", copiedUser);
    context.putDelivery("Form", form);
    Form formUserPrefs = null;
    UserPreferencesHandler uph = new UserPreferencesHandler(this.application);
    formUserPrefs = uph.buildNewForm(context);
    if (context.getSessionValue("NOTEPAD_USER_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_USER_VISIBLE")); 
    return context.includeJSP("user-security-editor.jsp");
  }
  
  private boolean userSecuritySave(Context context) {
    User sessionUser = MilestoneSecurity.getUser(context);
    User user = (User)context.getSessionValue("securityUser");
    boolean isNewUser = false;
    if (user == null) {
      user = new User();
      isNewUser = true;
    } 
    boolean isRelFamilyNewUser = false;
    if (isNewUser || user.getUserId() == -1)
      isRelFamilyNewUser = true; 
    Form form = buildForm(context, user);
    UserPreferencesHandler uph = new UserPreferencesHandler(this.application);
    Form userPrefForm = uph.buildForm(context, user);
    if (UserManager.getInstance().isTimestampValid(user)) {
      form.setValues(context);
      userPrefForm.setValues(context);
      Acl acl = user.getAcl();
      Vector cAcl = acl.getCompanyAcl();
      String login = form.getStringValue("login");
      user.setLogin(login);
      String password = form.getStringValue("password");
      user.setPassword(password);
      String reportto = form.getStringValue("reportto");
      user.setReportsTo(reportto);
      String name = form.getStringValue("fullname");
      user.setName(name);
      String location = form.getStringValue("location");
      user.setLocation(location);
      String employedby = form.getStringValue("employedby");
      user.setEmployedBy(Integer.parseInt(employedby));
      user.setEmail(form.getStringValue("email"));
      user.setPhone(form.getStringValue("phone"));
      user.setFax(form.getStringValue("fax"));
      user.setInactive(((FormCheckBox)form.getElement("inactive")).isChecked() ? -1 : 0);
      user.setAdministrator(((FormCheckBox)form.getElement("administrator")).isChecked() ? 1 : 0);
      String department = form.getStringValue("deptFilter");
      String deptFilterFlag = form.getStringValue("IsModifyDept");
      String deptFilter = deptFilterFlag.equalsIgnoreCase("Yes") ? ("trueßdepartment.filter." + department) : ("falseßdepartment.filter." + department);
      user.setDeptFilter(deptFilter);
      context.putSessionValue("deptFilterFlag", deptFilterFlag);
      context.putSessionValue("deptFilter", department);
      String filter = form.getStringValue("filter");
      String filterFlag = form.getStringValue("IsModify");
      if (filter.equalsIgnoreCase("All")) {
        filter = filterFlag.equalsIgnoreCase("Yes") ? "trueßmilestone.filter.FilterNone" : "falseßmilestone.filter.FilterNone";
      } else if (filter.equalsIgnoreCase("Only Label Tasks")) {
        filter = filterFlag.equalsIgnoreCase("Yes") ? "trueßmilestone.filter.FilterExcludeOwnerUML" : "falseßmilestone.filter.FilterExcludeOwnerUML";
      } else if (filter.equalsIgnoreCase("Only UML Tasks")) {
        filter = filterFlag.equalsIgnoreCase("Yes") ? "trueßmilestone.filter.FilterIncludeOwnerUML" : "falseßmilestone.filter.FilterIncludeOwnerUML";
      } 
      user.setFilter(filter);
      String[] filterArray = MilestoneHelper.parseFilter(filter);
      context.putSessionValue("filterFlag", filterArray[0]);
      context.putSessionValue("filter", filterArray[1]);
      Vector checkedEnvironments = new Vector();
      Vector environments = Cache.getInstance().getEnvironments();
      if (environments != null)
        for (int i = 0; i < environments.size(); i++) {
          Environment environment = (Environment)environments.get(i);
          if (environment != null && form.getElement("ue" + environment.getStructureID()) != null)
            if (((FormCheckBox)form.getElement("ue" + environment.getStructureID())).isChecked())
              checkedEnvironments.add(environment);  
        }  
      String selectionValue = form.getStringValue("1");
      boolean selectionBool = false;
      if (selectionValue.equalsIgnoreCase("Available"))
        selectionBool = true; 
      acl.setAccessSelection(selectionBool);
      String scheduleValue = form.getStringValue("2");
      boolean scheduleBool = false;
      if (scheduleValue.equalsIgnoreCase("Available"))
        scheduleBool = true; 
      acl.setAccessSchedule(scheduleBool);
      String manufacturingValue = form.getStringValue("3");
      boolean manufacturingBool = false;
      if (manufacturingValue.equalsIgnoreCase("Available"))
        manufacturingBool = true; 
      acl.setAccessManufacturing(manufacturingBool);
      String pfmValue = form.getStringValue("4");
      boolean pfmBool = false;
      if (pfmValue.equalsIgnoreCase("Available"))
        pfmBool = true; 
      acl.setAccessPfmForm(pfmBool);
      String bomValue = form.getStringValue("5");
      boolean bomBool = false;
      if (bomValue.equalsIgnoreCase("Available"))
        bomBool = true; 
      acl.setAccessBomForm(bomBool);
      String reportValue = form.getStringValue("6");
      boolean reportBool = false;
      if (reportValue.equalsIgnoreCase("Available"))
        reportBool = true; 
      acl.setAccessReport(reportBool);
      String templateValue = form.getStringValue("7");
      boolean templateBool = false;
      if (templateValue.equalsIgnoreCase("Available"))
        templateBool = true; 
      acl.setAccessTemplate(templateBool);
      String taskValue = form.getStringValue("8");
      boolean taskBool = false;
      if (taskValue.equalsIgnoreCase("Available"))
        taskBool = true; 
      acl.setAccessTask(taskBool);
      String dayValue = form.getStringValue("9");
      boolean dayBool = false;
      if (dayValue.equalsIgnoreCase("Available"))
        dayBool = true; 
      acl.setAccessDayType(dayBool);
      String userValue = form.getStringValue("10");
      boolean userBool = false;
      if (userValue.equalsIgnoreCase("Available"))
        userBool = true; 
      acl.setAccessUser(userBool);
      String familyValue = form.getStringValue("11");
      boolean familyBool = false;
      if (familyValue.equalsIgnoreCase("Available"))
        familyBool = true; 
      acl.setAccessFamily(familyBool);
      String environmentValue = form.getStringValue("20");
      boolean environmentBool = false;
      if (environmentValue.equalsIgnoreCase("Available"))
        environmentBool = true; 
      acl.setAccessEnvironment(environmentBool);
      String companyValue = form.getStringValue("12");
      boolean companyBool = false;
      if (companyValue.equalsIgnoreCase("Available"))
        companyBool = true; 
      acl.setAccessCompany(companyBool);
      String divisionValue = form.getStringValue("13");
      boolean divisionBool = false;
      if (divisionValue.equalsIgnoreCase("Available"))
        divisionBool = true; 
      acl.setAccessDivision(divisionBool);
      String labelValue = form.getStringValue("14");
      boolean labelBool = false;
      if (labelValue.equalsIgnoreCase("Available"))
        labelBool = true; 
      acl.setAccessLabel(labelBool);
      String tableValue = form.getStringValue("15");
      boolean tableBool = false;
      if (tableValue.equalsIgnoreCase("Available"))
        tableBool = true; 
      acl.setAccessTable(tableBool);
      String parameterValue = form.getStringValue("16");
      boolean parameterBool = false;
      if (parameterValue.equalsIgnoreCase("Available"))
        parameterBool = true; 
      acl.setAccessParameter(parameterBool);
      String auditValue = form.getStringValue("17");
      boolean auditBool = false;
      if (auditValue.equalsIgnoreCase("Available"))
        auditBool = true; 
      acl.setAccessAuditTrail(auditBool);
      String configValue = form.getStringValue("18");
      boolean configBool = false;
      if (configValue.equalsIgnoreCase("Available"))
        configBool = true; 
      acl.setAccessReportConfig(configBool);
      String priceValue = form.getStringValue("19");
      boolean priceBool = false;
      if (priceValue.equalsIgnoreCase("Available"))
        priceBool = true; 
      acl.setAccessPriceCode(priceBool);
      UserPreferences up = new UserPreferences();
      String openingScreen = userPrefForm.getStringValue("openingScreen");
      up.setOpeningScreen(Integer.parseInt(openingScreen));
      String autoCloseRadio = userPrefForm.getStringValue("autoCloseRadio");
      up.setAutoClose(Integer.parseInt(autoCloseRadio));
      String autoCloseDays = userPrefForm.getStringValue("autoCloseDays");
      up.setAutoCloseDays(Integer.parseInt(autoCloseDays));
      String generalSortBy = userPrefForm.getStringValue("sortBy");
      up.setNotepadSortBy(Integer.parseInt(generalSortBy));
      String generalOrder = userPrefForm.getStringValue("order");
      up.setNotepadOrder(Integer.parseInt(generalOrder));
      String generalProdType = userPrefForm.getStringValue("productType");
      up.setNotepadProductType(Integer.parseInt(generalProdType));
      String selectionReleasingFamilies = userPrefForm.getStringValue("releasingFamilies");
      up.setSelectionReleasingFamily(Integer.parseInt(selectionReleasingFamilies));
      String selectionEnvironment = userPrefForm.getStringValue("envMenu");
      up.setSelectionEnvironment(Integer.parseInt(selectionEnvironment));
      String selectionContactList = userPrefForm.getStringValue("ContactList");
      up.setSelectionLabelContact(Integer.parseInt(selectionContactList));
      String selectionProductType = userPrefForm.getStringValue("selectionProductType");
      up.setSelectionProductType(Integer.parseInt(selectionProductType));
      FormCheckBox selectionStatus = (FormCheckBox)userPrefForm.getElement("status");
      up.setSelectionStatus(0);
      if (selectionStatus.isChecked())
        up.setSelectionStatus(1); 
      FormCheckBox selectionPriorCriteria = (FormCheckBox)userPrefForm.getElement("priorCriteria");
      up.setSelectionPriorCriteria(0);
      if (selectionPriorCriteria.isChecked())
        up.setSelectionPriorCriteria(1); 
      String sortBySchedule = userPrefForm.getStringValue("sortBySchedule");
      up.setSchedulePhysicalSortBy(Integer.parseInt(sortBySchedule));
      String ownerSchedule = userPrefForm.getStringValue("ownerSchedule");
      up.setSchedulePhysicalOwner(Integer.parseInt(ownerSchedule));
      String sortByDigitalSchedule = userPrefForm.getStringValue("sortByDigitalSchedule");
      up.setScheduleDigitalSortBy(Integer.parseInt(sortByDigitalSchedule));
      String ownerDigitalSchedule = userPrefForm.getStringValue("ownerDigitalSchedule");
      up.setScheduleDigitalOwner(Integer.parseInt(ownerDigitalSchedule));
      String releaseType = userPrefForm.getStringValue("releaseType");
      up.setReportsReleaseType(Integer.parseInt(releaseType));
      String releasingFamiliesReports = userPrefForm.getStringValue("releasingFamiliesReports");
      up.setReportsReleasingFamily(Integer.parseInt(releasingFamiliesReports));
      String envMenuReports = userPrefForm.getStringValue("envMenuReports");
      up.setReportsEnvironment(Integer.parseInt(envMenuReports));
      String ContactListReports = userPrefForm.getStringValue("ContactListReports");
      up.setReportsLabelContact(Integer.parseInt(ContactListReports));
      String umlContact = userPrefForm.getStringValue("umlContact");
      up.setReportsUMLContact(Integer.parseInt(umlContact));
      FormCheckBox spAll = (FormCheckBox)userPrefForm.getElement("statusReportsAll");
      FormCheckBox spActive = (FormCheckBox)userPrefForm.getElement("statusReportsActive");
      FormCheckBox spTBS = (FormCheckBox)userPrefForm.getElement("statusReportsTBS");
      FormCheckBox spClosed = (FormCheckBox)userPrefForm.getElement("statusReportsClosed");
      FormCheckBox spCancelled = (FormCheckBox)userPrefForm.getElement("statusReportsCancelled");
      up.setReportsStatusAll(0);
      if (spAll.isChecked())
        up.setReportsStatusAll(1); 
      up.setReportsStatusActive(0);
      if (spActive.isChecked())
        up.setReportsStatusActive(1); 
      up.setReportsStatusTBS(0);
      if (spTBS.isChecked())
        up.setReportsStatusTBS(1); 
      up.setReportsStatusClosed(0);
      if (spClosed.isChecked())
        up.setReportsStatusClosed(1); 
      up.setReportsStatusCancelled(0);
      if (spCancelled.isChecked())
        up.setReportsStatusCancelled(1); 
      user.setPreferences(up);
      if (!UserManager.getInstance().isDuplicate(user)) {
        if (!form.isUnchanged()) {
          FormValidation formValidation = form.validate();
          if (formValidation.isGood()) {
            Vector checkedCompanies = new Vector();
            for (int j = 0; j < checkedEnvironments.size(); j++) {
              Environment currentEnvironment = (Environment)checkedEnvironments.elementAt(j);
              Vector environmentCompanies = currentEnvironment.getChildren();
              for (int k = 0; k < environmentCompanies.size(); k++) {
                Company envCompany = (Company)environmentCompanies.elementAt(k);
                if (!checkedCompanies.contains(envCompany))
                  checkedCompanies.add(envCompany); 
              } 
            } 
            User savedUser = UserManager.getInstance().save(user, sessionUser, checkedCompanies, context);
            UserPreferencesManager.getInstance().savePreferences(user, context);
            form = buildForm(context, savedUser);
            userPrefForm = uph.buildForm(context, savedUser);
            FormElement lastUpdated = form.getElement("lastupdateddate");
            if (savedUser.getLastUpdateDate() != null)
              lastUpdated.setValue(MilestoneHelper.getLongDate(savedUser.getLastUpdateDate())); 
            savedUser = UserManager.getInstance().getUser(savedUser.getUserId(), true);
            context.putSessionValue("securityUser", savedUser);
            if (savedUser.getUserId() == sessionUser.getUserId()) {
              context.removeSessionValue("user");
              context.removeSessionValue("user-companies");
              context.removeSessionValue("user-environments");
              context.putSessionValue("UserDefaultsApplied", "true");
              context.putSessionValue("user", savedUser);
              makeDynamic(savedUser, context, sessionUser);
            } 
            UserCompaniesTableManager.getInstance().setUpdateFlag(savedUser.getUserId(), true);
            Cache.flushCachedVariableAllUsers(savedUser.getUserId());
            Notepad notepad = getUsersNotepad(context);
            notepad.setAllContents(null);
            if (isNewUser) {
              notepad.newSelectedReset();
              notepad = getUsersNotepad(context);
              notepad.setSelected(savedUser);
              notepad = getUsersNotepad(context);
            } else {
              notepad = getUsersNotepad(context);
              notepad.setSelected(savedUser);
              user = (User)notepad.validateSelected();
              if (user == null) {
                notepad.setSwitchToCompaniesVisible(false);
                return userGoToBlank(context);
              } 
              form = buildForm(context, user);
              userPrefForm = uph.buildForm(context, user);
            } 
          } else {
            context.putDelivery("FormValidation", formValidation);
          } 
        } 
        context.removeSessionValue(NOTEPAD_SESSION_NAMES[3]);
        if (isRelFamilyNewUser)
          context.putSessionValue("showAssigned", "ASSIGNED"); 
      } else {
        Notepad notepad = getUsersNotepad(context);
        context.putDelivery("AlertMessage", "Duplicate.");
        user = (User)notepad.validateSelected();
        form = buildForm(context, user);
        userPrefForm = uph.buildForm(context, user);
      } 
    } else {
      context.putDelivery("AlertMessage", "The record is unavailable for editing at this time.  Please refresh your screen and edit it at a later time.");
    } 
    context.putDelivery("Form", form);
    this.lCopiedUserId = 0L;
    return context.includeJSP("user-security-editor.jsp");
  }
  
  private boolean userSecurityDelete(Context context) {
    User sessionUser = MilestoneSecurity.getUser(context);
    Notepad notepad = getUsersNotepad(context);
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    User user = MilestoneHelper.getScreenUser(context);
    if (user != null) {
      UserManager.getInstance().deleteUser(user, sessionUser);
      Cache.flushCachedVariableAllUsers(user.getUserId());
      notepad.setAllContents(null);
      notepad.setSelected(null);
    } 
    return userSecurityEditor(context);
  }
  
  private boolean userSecurityNew(Context context) {
    Notepad notepad = getUsersNotepad(context);
    notepad.setSelected(null);
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    context.removeSessionValue(NOTEPAD_SESSION_NAMES[21]);
    context.removeSessionValue("securityUser");
    context.removeSessionValue("User");
    Form form = buildNewForm(context);
    Form formUserPrefs = null;
    UserPreferencesHandler uph = new UserPreferencesHandler(this.application);
    formUserPrefs = uph.buildNewForm(context);
    notepad.setSwitchToCompaniesVisible(false);
    return context.includeJSP("user-security-editor.jsp");
  }
  
  private Form buildCopyForm(Context context, User user, int userId) {
    context.putSessionValue("showAssigned", "NEW");
    Form userSecurityForm = new Form(this.application, "userSecurityForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    Acl acl = user.getAcl();
    Vector cAcl = null;
    if (acl != null)
      acl.getCompanyAcl(); 
    FormTextField login = new FormTextField("login", "", true, 30, 100);
    login.setTabIndex(1);
    userSecurityForm.addElement(login);
    FormPasswordField password = new FormPasswordField("password", user.getPassword(), true, 30, 30);
    password.setTabIndex(2);
    userSecurityForm.addElement(password);
    String reportsToString = "";
    if (user.getReportsTo() != null)
      reportsToString = user.getReportsTo(); 
    FormTextField reportto = new FormTextField("reportto", reportsToString, false, 30, 30);
    reportto.setTabIndex(3);
    userSecurityForm.addElement(reportto);
    FormTextField fullname = new FormTextField("fullname", user.getName(), true, 30, 100);
    fullname.setTabIndex(4);
    userSecurityForm.addElement(fullname);
    String locationString = "";
    locationString = user.getLocation();
    Vector userLocations = Cache.getLookupDetailValuesFromDatabase(53);
    FormDropDownMenu location = MilestoneHelper.getLookupDropDown("location", userLocations, locationString, false, true);
    location.setTabIndex(5);
    userSecurityForm.addElement(location);
    FormTextField email = new FormTextField("email", user.getEmail(), false, 30, 50);
    email.setTabIndex(6);
    userSecurityForm.addElement(email);
    FormTextField phone = new FormTextField("phone", user.getPhone(), false, 30, 30);
    phone.setTabIndex(7);
    userSecurityForm.addElement(phone);
    FormTextField fax = new FormTextField("fax", user.getFax(), false, 30, 30);
    fax.setTabIndex(8);
    userSecurityForm.addElement(fax);
    Vector families = Cache.getInstance().getFamilies();
    String employedByString = "";
    if (user.getEmployedBy() > 0)
      employedByString = String.valueOf(user.getEmployedBy()); 
    FormDropDownMenu employedby = MilestoneHelper.getCorporateStructureDropDown("employedby", families, employedByString, true, true);
    employedby.setTabIndex(9);
    userSecurityForm.addElement(employedby);
    FormCheckBox inactive = new FormCheckBox("inactive", "Inactive", false, !(user.getInactive() == 0));
    inactive.setId("inactive");
    inactive.setTabIndex(11);
    userSecurityForm.addElement(inactive);
    FormCheckBox administrator = new FormCheckBox("administrator", "Administrator", false, !(user.getAdministrator() == 0));
    administrator.setTabIndex(12);
    userSecurityForm.addElement(administrator);
    String selDepartment = "";
    String selStr = "";
    if (user.getDeptFilter() != null)
      selStr = user.getDeptFilter(); 
    int sel = selStr.indexOf("department.filter.");
    if (sel != -1)
      try {
        selDepartment = selStr.substring(sel + "department.filter.".length());
      } catch (Exception exception) {} 
    FormDropDownMenu deptFilterDD = MilestoneHelper.getDepartmentDropDown("deptFilter", selDepartment, false);
    String[] values = deptFilterDD.getValueList();
    String[] menuText = deptFilterDD.getMenuTextList();
    values[0] = "All";
    menuText[0] = "All";
    deptFilterDD.setValueList(values);
    deptFilterDD.setMenuTextList(menuText);
    deptFilterDD.setTabIndex(11);
    userSecurityForm.addElement(deptFilterDD);
    String filterFlagDept = "Yes";
    String deptFilter = user.getDeptFilter();
    if (deptFilter != null && deptFilter.length() > 0)
      try {
        if (deptFilter.substring(0, 4).equalsIgnoreCase("true")) {
          filterFlagDept = "Yes";
        } else {
          filterFlagDept = "No";
        } 
      } catch (Exception exception) {} 
    FormRadioButtonGroup IsModifyDept = new FormRadioButtonGroup("IsModifyDept", filterFlagDept, "Yes, No", false);
    IsModifyDept.setTabIndex(11);
    userSecurityForm.addElement(IsModifyDept);
    String filterString = "All";
    String filterFlag = "Yes";
    String userFilter = user.getFilter();
    if (userFilter != null && userFilter.length() > 0) {
      if (userFilter.substring(0, 0).equalsIgnoreCase("t")) {
        filterFlag = userFilter.substring(0, 3);
      } else {
        filterFlag = userFilter.substring(0, 4);
      } 
      if (filterFlag.equalsIgnoreCase("true")) {
        filterString = userFilter.substring(5, userFilter.length());
      } else {
        filterString = userFilter.substring(6, userFilter.length());
      } 
      if (filterFlag.equalsIgnoreCase("true")) {
        filterFlag = "Yes";
      } else {
        filterFlag = "No";
      } 
      if (filterString.equalsIgnoreCase("milestone.filter.FilterNone")) {
        filterString = "All";
      } else if (filterString.equalsIgnoreCase("milestone.filter.FilterExcludeOwnerUML")) {
        filterString = "Only Label Tasks";
      } else if (filterString.equalsIgnoreCase("milestone.filter.FilterIncludeOwnerUML")) {
        filterString = "Only UML Tasks";
      } 
    } 
    FormDropDownMenu filter = new FormDropDownMenu("filter", filterString, "All,Only Label Tasks, Only UML Tasks", true);
    filter.setTabIndex(8);
    userSecurityForm.addElement(filter);
    FormRadioButtonGroup IsModify = new FormRadioButtonGroup("IsModify", filterFlag, "Yes, No", false);
    IsModify.setTabIndex(9);
    userSecurityForm.addElement(IsModify);
    Vector environments = Cache.getInstance().getEnvironments();
    environments = Cache.getInstance().getEnvironmentsByFamily();
    int userid = userId;
    Vector userEnvironments = MilestoneHelper.getUserEnvironments(userid);
    if (userEnvironments != null)
      for (int a = 0; a < userEnvironments.size(); a++) {
        Environment userEnvironment = (Environment)userEnvironments.elementAt(a);
        if (userEnvironment != null) {
          FormCheckBox environmentCheckbox = new FormCheckBox("ue" + userEnvironment.getStructureID(), "ue" + userEnvironment.getStructureID(), userEnvironment.getName(), false, true);
          environmentCheckbox.setId("ue" + userEnvironment.getStructureID());
          userSecurityForm.addElement(environmentCheckbox);
        } 
      }  
    for (int i = 0; i < environments.size(); i++) {
      Environment environment = (Environment)environments.elementAt(i);
      if (environment != null) {
        FormCheckBox environmentCheckbox = new FormCheckBox("ue" + environment.getStructureID(), "ue" + environment.getStructureID(), environment.getName(), false, false);
        environmentCheckbox.setId("ue" + environment.getStructureID());
        userSecurityForm.addElement(environmentCheckbox);
      } 
    } 
    String selectionValue = "Not Available";
    if (acl != null)
      if (acl.getAccessSelection())
        selectionValue = "Available";  
    FormRadioButtonGroup selectionAccess = new FormRadioButtonGroup("1", selectionValue, "Not Available, Available", false);
    selectionAccess.setTabIndex(10);
    userSecurityForm.addElement(selectionAccess);
    String scheduleValue = "Not Available";
    if (acl != null)
      if (acl.getAccessSchedule())
        scheduleValue = "Available";  
    FormRadioButtonGroup scheduleAccess = new FormRadioButtonGroup("2", scheduleValue, "Not Available, Available", false);
    scheduleAccess.setTabIndex(11);
    userSecurityForm.addElement(scheduleAccess);
    String manufacturingValue = "Not Available";
    if (acl != null)
      if (acl.getAccessManufacturing())
        manufacturingValue = "Available";  
    FormRadioButtonGroup manufacturingAccess = new FormRadioButtonGroup("3", manufacturingValue, "Not Available, Available", false);
    manufacturingAccess.setTabIndex(12);
    userSecurityForm.addElement(manufacturingAccess);
    String pfmValue = "Not Available";
    if (acl != null)
      if (acl.getAccessPfmForm())
        pfmValue = "Available";  
    FormRadioButtonGroup pfmAccess = new FormRadioButtonGroup("4", pfmValue, "Not Available, Available", false);
    pfmAccess.setTabIndex(13);
    userSecurityForm.addElement(pfmAccess);
    String bomValue = "Not Available";
    if (acl != null)
      if (acl.getAccessBomForm())
        bomValue = "Available";  
    FormRadioButtonGroup bomAccess = new FormRadioButtonGroup("5", bomValue, "Not Available, Available", false);
    bomAccess.setTabIndex(14);
    userSecurityForm.addElement(bomAccess);
    String reportValue = "Not Available";
    if (acl != null)
      if (acl.getAccessReport())
        reportValue = "Available";  
    FormRadioButtonGroup reportAccess = new FormRadioButtonGroup("6", reportValue, "Not Available, Available", false);
    reportAccess.setTabIndex(15);
    userSecurityForm.addElement(reportAccess);
    String templateValue = "Not Available";
    if (acl != null)
      if (acl.getAccessTemplate())
        templateValue = "Available";  
    FormRadioButtonGroup templateAccess = new FormRadioButtonGroup("7", templateValue, "Not Available, Available", false);
    templateAccess.setTabIndex(16);
    userSecurityForm.addElement(templateAccess);
    String taskValue = "Not Available";
    if (acl != null)
      if (acl.getAccessTask())
        taskValue = "Available";  
    FormRadioButtonGroup taskAccess = new FormRadioButtonGroup("8", taskValue, "Not Available, Available", false);
    taskAccess.setTabIndex(17);
    userSecurityForm.addElement(taskAccess);
    String dayTypeValue = "Not Available";
    if (acl != null)
      if (acl.getAccessDayType())
        dayTypeValue = "Available";  
    FormRadioButtonGroup dayTypeAccess = new FormRadioButtonGroup("9", dayTypeValue, "Not Available, Available", false);
    dayTypeAccess.setTabIndex(18);
    userSecurityForm.addElement(dayTypeAccess);
    String userValue = "Not Available";
    if (acl != null)
      if (acl.getAccessUser())
        userValue = "Available";  
    FormRadioButtonGroup userAccess = new FormRadioButtonGroup("10", userValue, "Not Available, Available", false);
    userAccess.setTabIndex(19);
    userSecurityForm.addElement(userAccess);
    String familyValue = "Not Available";
    if (acl != null)
      if (acl.getAccessFamily())
        familyValue = "Available";  
    FormRadioButtonGroup familyAccess = new FormRadioButtonGroup("11", familyValue, "Not Available, Available", false);
    familyAccess.setTabIndex(20);
    userSecurityForm.addElement(familyAccess);
    String environmentValue = "Not Available";
    if (acl != null)
      if (acl.getAccessEnvironment())
        environmentValue = "Available";  
    FormRadioButtonGroup environmentAccess = new FormRadioButtonGroup("20", environmentValue, "Not Available, Available", false);
    environmentAccess.setTabIndex(21);
    userSecurityForm.addElement(environmentAccess);
    String companyValue = "Not Available";
    if (acl != null)
      if (acl.getAccessCompany())
        companyValue = "Available";  
    FormRadioButtonGroup companyAccess = new FormRadioButtonGroup("12", companyValue, "Not Available, Available", false);
    companyAccess.setTabIndex(22);
    userSecurityForm.addElement(companyAccess);
    String divisionValue = "Not Available";
    if (acl != null)
      if (acl.getAccessDivision())
        divisionValue = "Available";  
    FormRadioButtonGroup divisionAccess = new FormRadioButtonGroup("13", divisionValue, "Not Available, Available", false);
    divisionAccess.setTabIndex(23);
    userSecurityForm.addElement(divisionAccess);
    String labelValue = "Not Available";
    if (acl != null)
      if (acl.getAccessLabel())
        labelValue = "Available";  
    FormRadioButtonGroup labelAccess = new FormRadioButtonGroup("14", labelValue, "Not Available, Available", false);
    labelAccess.setTabIndex(24);
    userSecurityForm.addElement(labelAccess);
    String tableValue = "Not Available";
    if (acl != null)
      if (acl.getAccessTable())
        tableValue = "Available";  
    FormRadioButtonGroup tableAccess = new FormRadioButtonGroup("15", tableValue, "Not Available, Available", false);
    tableAccess.setTabIndex(25);
    userSecurityForm.addElement(tableAccess);
    String parameterValue = "Not Available";
    if (acl != null)
      if (acl.getAccessParameter())
        parameterValue = "Available";  
    FormRadioButtonGroup parameterAccess = new FormRadioButtonGroup("16", parameterValue, "Not Available, Available", false);
    parameterAccess.setTabIndex(26);
    userSecurityForm.addElement(parameterAccess);
    String auditTrailValue = "Not Available";
    if (acl != null)
      if (acl.getAccessAuditTrail())
        auditTrailValue = "Available";  
    FormRadioButtonGroup auditTrailAccess = new FormRadioButtonGroup("17", auditTrailValue, "Not Available, Available", false);
    auditTrailAccess.setTabIndex(27);
    userSecurityForm.addElement(auditTrailAccess);
    String reportConfigValue = "Not Available";
    if (acl != null)
      if (acl.getAccessReportConfig())
        reportConfigValue = "Available";  
    FormRadioButtonGroup reportConfigAccess = new FormRadioButtonGroup("18", reportConfigValue, "Not Available, Available", false);
    reportConfigAccess.setTabIndex(28);
    userSecurityForm.addElement(reportConfigAccess);
    String priceCodeValue = "Not Available";
    if (acl != null)
      if (acl.getAccessPriceCode())
        priceCodeValue = "Available";  
    FormRadioButtonGroup priceCodeAccess = new FormRadioButtonGroup("19", priceCodeValue, "Not Available, Available", false);
    priceCodeAccess.setTabIndex(29);
    userSecurityForm.addElement(priceCodeAccess);
    FormTextField lastUpdated = new FormTextField("lastUpdatedDate", false, 50);
    if (user.getLastUpdateDate() != null)
      lastUpdated.setValue(MilestoneHelper.getLongDate(user.getLastUpdateDate())); 
    userSecurityForm.addElement(lastUpdated);
    FormTextField lastUpdatedBy = new FormTextField("lastUpdatedBy", false, 50);
    if (UserManager.getInstance().getUser(user.getLastUpdatingUser(), true) != null)
      lastUpdatedBy.setValue(UserManager.getInstance().getUser(user.getLastUpdatingUser(), true).getLogin()); 
    userSecurityForm.addElement(lastUpdatedBy);
    FormTextField nameSrch = new FormTextField("nameSrch", "", false, 20);
    nameSrch.setId("nameSrch");
    userSecurityForm.addElement(nameSrch);
    FormTextField userNameSrch = new FormTextField("userNameSrch", "", false, 20);
    userNameSrch.setId("userNameSrch");
    userSecurityForm.addElement(userNameSrch);
    String employedBySrcString = "";
    FormDropDownMenu employedBySrc = MilestoneHelper.getCorporateStructureDropDown("employedBySrc", families, employedBySrcString, true, true);
    employedBySrc.setId("employedBySrc");
    userSecurityForm.addElement(employedBySrc);
    FormDropDownMenu environmentDescriptionSearch = MilestoneHelper.getCorporateStructureDropDown("EnvironmentDescriptionSearch", environments, "", false, true);
    environmentDescriptionSearch.setId("EnvironmentDescriptionSearch");
    userSecurityForm.addElement(environmentDescriptionSearch);
    userSecurityForm.addElement(new FormHidden("cmd", "user-security-copy"));
    context.putDelivery("Form", userSecurityForm);
    if (context.getSessionValue("NOTEPAD_USER_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_USER_VISIBLE")); 
    return userSecurityForm;
  }
  
  private Form buildForm(Context context, User user) {
    Form userSecurityForm = new Form(this.application, "userSecurityForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    Acl acl = user.getAcl();
    Vector cAcl = null;
    if (acl != null)
      acl.getCompanyAcl(); 
    String showAssigned = context.getRequest().getParameter("showAssigned");
    if (showAssigned != null)
      if (showAssigned.equals("NEW") && user.getUserId() != -1) {
        context.putSessionValue("showAssigned", "ASSIGNED");
      } else {
        context.putSessionValue("showAssigned", showAssigned);
      }  
    FormPasswordField password = new FormPasswordField("password", user.getPassword(), true, 30, 30);
    password.setTabIndex(1);
    userSecurityForm.addElement(password);
    String reportsToString = "";
    if (user.getReportsTo() != null)
      reportsToString = user.getReportsTo(); 
    FormTextField reportto = new FormTextField("reportto", reportsToString, false, 30, 30);
    reportto.setTabIndex(2);
    userSecurityForm.addElement(reportto);
    FormTextField login = new FormTextField("login", user.getLogin(), true, 30, 100);
    userSecurityForm.addElement(login);
    FormTextField fullname = new FormTextField("fullname", user.getName(), true, 30, 100);
    fullname.setTabIndex(3);
    userSecurityForm.addElement(fullname);
    String locationString = "";
    locationString = user.getLocation();
    Vector userLocations = Cache.getLookupDetailValuesFromDatabase(53);
    FormDropDownMenu location = MilestoneHelper.getLookupDropDown("location", userLocations, locationString, false, true);
    location.setTabIndex(4);
    userSecurityForm.addElement(location);
    Vector families = Cache.getInstance().getFamilies();
    String employedByString = "";
    if (user.getEmployedBy() > 0)
      employedByString = String.valueOf(user.getEmployedBy()); 
    FormDropDownMenu employedby = MilestoneHelper.getCorporateStructureDropDown("employedby", families, employedByString, true, true);
    employedby.setTabIndex(5);
    userSecurityForm.addElement(employedby);
    FormTextField email = new FormTextField("email", user.getEmail(), false, 30, 50);
    email.setTabIndex(6);
    userSecurityForm.addElement(email);
    FormTextField phone = new FormTextField("phone", user.getPhone(), false, 30, 30);
    phone.setTabIndex(7);
    userSecurityForm.addElement(phone);
    FormTextField fax = new FormTextField("fax", user.getFax(), false, 30, 30);
    fax.setTabIndex(10);
    userSecurityForm.addElement(fax);
    FormCheckBox inactive = new FormCheckBox("inactive", "Inactive", false, !(user.getInactive() == 0));
    inactive.setId("inactive");
    inactive.setTabIndex(11);
    userSecurityForm.addElement(inactive);
    FormCheckBox administrator = new FormCheckBox("administrator", "Administrator", false, !(user.getAdministrator() == 0));
    administrator.setTabIndex(12);
    userSecurityForm.addElement(administrator);
    String selDepartment = "";
    String selStr = "";
    if (user.getDeptFilter() != null)
      selStr = user.getDeptFilter(); 
    int sel = selStr.indexOf("department.filter.");
    if (sel != -1)
      try {
        selDepartment = selStr.substring(sel + "department.filter.".length());
      } catch (Exception exception) {} 
    FormDropDownMenu deptFilterDD = MilestoneHelper.getDepartmentDropDown("deptFilter", selDepartment, false);
    String[] values = deptFilterDD.getValueList();
    String[] menuText = deptFilterDD.getMenuTextList();
    values[0] = "All";
    menuText[0] = "All";
    deptFilterDD.setValueList(values);
    deptFilterDD.setMenuTextList(menuText);
    deptFilterDD.setTabIndex(11);
    userSecurityForm.addElement(deptFilterDD);
    String filterFlagDept = "Yes";
    String deptFilter = user.getDeptFilter();
    if (deptFilter != null && deptFilter.length() > 0)
      try {
        if (deptFilter.substring(0, 4).equalsIgnoreCase("true")) {
          filterFlagDept = "Yes";
        } else {
          filterFlagDept = "No";
        } 
      } catch (Exception exception) {} 
    FormRadioButtonGroup IsModifyDept = new FormRadioButtonGroup("IsModifyDept", filterFlagDept, "Yes, No", false);
    IsModifyDept.setTabIndex(11);
    userSecurityForm.addElement(IsModifyDept);
    String filterString = "All";
    String filterFlag = "Yes";
    String userFilter = user.getFilter();
    if (userFilter != null && userFilter.length() > 0) {
      if (userFilter.substring(0, 0).equalsIgnoreCase("t")) {
        filterFlag = userFilter.substring(0, 3);
      } else {
        filterFlag = userFilter.substring(0, 4);
      } 
      if (filterFlag.equalsIgnoreCase("true")) {
        filterString = userFilter.substring(5, userFilter.length());
      } else {
        filterString = userFilter.substring(6, userFilter.length());
      } 
      if (filterFlag.equalsIgnoreCase("true")) {
        filterFlag = "Yes";
      } else {
        filterFlag = "No";
      } 
      if (filterString.equalsIgnoreCase("milestone.filter.FilterNone")) {
        filterString = "All";
      } else if (filterString.equalsIgnoreCase("milestone.filter.FilterExcludeOwnerUML")) {
        filterString = "Only Label Tasks";
      } else if (filterString.equalsIgnoreCase("milestone.filter.FilterIncludeOwnerUML")) {
        filterString = "Only UML Tasks";
      } 
    } 
    FormDropDownMenu filter = new FormDropDownMenu("filter", filterString, "All,Only Label Tasks, Only UML Tasks", true);
    filter.setTabIndex(8);
    userSecurityForm.addElement(filter);
    FormRadioButtonGroup IsModify = new FormRadioButtonGroup("IsModify", filterFlag, "Yes, No", false);
    IsModify.setTabIndex(9);
    userSecurityForm.addElement(IsModify);
    Vector environments = Cache.getInstance().getEnvironments();
    environments = Cache.getInstance().getEnvironmentsByFamily();
    int userid = user.getUserId();
    Vector userEnvironments = MilestoneHelper.getUserEnvironments(userid);
    if (userEnvironments != null)
      for (int a = 0; a < userEnvironments.size(); a++) {
        Environment userEnvironment = (Environment)userEnvironments.elementAt(a);
        if (userEnvironment != null) {
          FormCheckBox environmentCheckbox = new FormCheckBox("ue" + userEnvironment.getStructureID(), "ue" + userEnvironment.getStructureID(), userEnvironment.getName(), false, true);
          environmentCheckbox.setId("ue" + userEnvironment.getStructureID());
          userSecurityForm.addElement(environmentCheckbox);
        } 
      }  
    for (int i = 0; i < environments.size(); i++) {
      Environment environment = (Environment)environments.elementAt(i);
      if (environment != null) {
        FormCheckBox environmentCheckbox = new FormCheckBox("ue" + environment.getStructureID(), "ue" + environment.getStructureID(), environment.getName(), false, false);
        environmentCheckbox.setId("ue" + environment.getStructureID());
        userSecurityForm.addElement(environmentCheckbox);
      } 
    } 
    String selectionValue = "Not Available";
    if (acl != null)
      if (acl.getAccessSelection())
        selectionValue = "Available";  
    FormRadioButtonGroup selectionAccess = new FormRadioButtonGroup("1", selectionValue, "Not Available, Available", false);
    selectionAccess.setTabIndex(10);
    userSecurityForm.addElement(selectionAccess);
    String scheduleValue = "Not Available";
    if (acl != null)
      if (acl.getAccessSchedule())
        scheduleValue = "Available";  
    FormRadioButtonGroup scheduleAccess = new FormRadioButtonGroup("2", scheduleValue, "Not Available, Available", false);
    scheduleAccess.setTabIndex(11);
    userSecurityForm.addElement(scheduleAccess);
    String manufacturingValue = "Not Available";
    if (acl != null)
      if (acl.getAccessManufacturing())
        manufacturingValue = "Available";  
    FormRadioButtonGroup manufacturingAccess = new FormRadioButtonGroup("3", manufacturingValue, "Not Available, Available", false);
    manufacturingAccess.setTabIndex(12);
    userSecurityForm.addElement(manufacturingAccess);
    String pfmValue = "Not Available";
    if (acl != null)
      if (acl.getAccessPfmForm())
        pfmValue = "Available";  
    FormRadioButtonGroup pfmAccess = new FormRadioButtonGroup("4", pfmValue, "Not Available, Available", false);
    pfmAccess.setTabIndex(13);
    userSecurityForm.addElement(pfmAccess);
    String bomValue = "Not Available";
    if (acl != null)
      if (acl.getAccessBomForm())
        bomValue = "Available";  
    FormRadioButtonGroup bomAccess = new FormRadioButtonGroup("5", bomValue, "Not Available, Available", false);
    bomAccess.setTabIndex(14);
    userSecurityForm.addElement(bomAccess);
    String reportValue = "Not Available";
    if (acl != null)
      if (acl.getAccessReport())
        reportValue = "Available";  
    FormRadioButtonGroup reportAccess = new FormRadioButtonGroup("6", reportValue, "Not Available, Available", false);
    reportAccess.setTabIndex(15);
    userSecurityForm.addElement(reportAccess);
    String templateValue = "Not Available";
    if (acl != null)
      if (acl.getAccessTemplate())
        templateValue = "Available";  
    FormRadioButtonGroup templateAccess = new FormRadioButtonGroup("7", templateValue, "Not Available, Available", false);
    templateAccess.setTabIndex(16);
    userSecurityForm.addElement(templateAccess);
    String taskValue = "Not Available";
    if (acl != null)
      if (acl.getAccessTask())
        taskValue = "Available";  
    FormRadioButtonGroup taskAccess = new FormRadioButtonGroup("8", taskValue, "Not Available, Available", false);
    taskAccess.setTabIndex(17);
    userSecurityForm.addElement(taskAccess);
    String dayTypeValue = "Not Available";
    if (acl != null)
      if (acl.getAccessDayType())
        dayTypeValue = "Available";  
    FormRadioButtonGroup dayTypeAccess = new FormRadioButtonGroup("9", dayTypeValue, "Not Available, Available", false);
    dayTypeAccess.setTabIndex(18);
    userSecurityForm.addElement(dayTypeAccess);
    String userValue = "Not Available";
    if (acl != null)
      if (acl.getAccessUser())
        userValue = "Available";  
    FormRadioButtonGroup userAccess = new FormRadioButtonGroup("10", userValue, "Not Available, Available", false);
    userAccess.setTabIndex(19);
    userSecurityForm.addElement(userAccess);
    String familyValue = "Not Available";
    if (acl != null)
      if (acl.getAccessFamily())
        familyValue = "Available";  
    FormRadioButtonGroup familyAccess = new FormRadioButtonGroup("11", familyValue, "Not Available, Available", false);
    familyAccess.setTabIndex(20);
    userSecurityForm.addElement(familyAccess);
    String environmentValue = "Not Available";
    if (acl != null)
      if (acl.getAccessEnvironment())
        environmentValue = "Available";  
    FormRadioButtonGroup environmentAccess = new FormRadioButtonGroup("20", environmentValue, "Not Available, Available", false);
    environmentAccess.setTabIndex(21);
    userSecurityForm.addElement(environmentAccess);
    String companyValue = "Not Available";
    if (acl != null)
      if (acl.getAccessCompany())
        companyValue = "Available";  
    FormRadioButtonGroup companyAccess = new FormRadioButtonGroup("12", companyValue, "Not Available, Available", false);
    companyAccess.setTabIndex(22);
    userSecurityForm.addElement(companyAccess);
    String divisionValue = "Not Available";
    if (acl != null)
      if (acl.getAccessDivision())
        divisionValue = "Available";  
    FormRadioButtonGroup divisionAccess = new FormRadioButtonGroup("13", divisionValue, "Not Available, Available", false);
    divisionAccess.setTabIndex(23);
    userSecurityForm.addElement(divisionAccess);
    String labelValue = "Not Available";
    if (acl != null)
      if (acl.getAccessLabel())
        labelValue = "Available";  
    FormRadioButtonGroup labelAccess = new FormRadioButtonGroup("14", labelValue, "Not Available, Available", false);
    labelAccess.setTabIndex(24);
    userSecurityForm.addElement(labelAccess);
    String tableValue = "Not Available";
    if (acl != null)
      if (acl.getAccessTable())
        tableValue = "Available";  
    FormRadioButtonGroup tableAccess = new FormRadioButtonGroup("15", tableValue, "Not Available, Available", false);
    tableAccess.setTabIndex(25);
    userSecurityForm.addElement(tableAccess);
    String parameterValue = "Not Available";
    if (acl != null)
      if (acl.getAccessParameter())
        parameterValue = "Available";  
    FormRadioButtonGroup parameterAccess = new FormRadioButtonGroup("16", parameterValue, "Not Available, Available", false);
    parameterAccess.setTabIndex(26);
    userSecurityForm.addElement(parameterAccess);
    String auditTrailValue = "Not Available";
    if (acl != null)
      if (acl.getAccessAuditTrail())
        auditTrailValue = "Available";  
    FormRadioButtonGroup auditTrailAccess = new FormRadioButtonGroup("17", auditTrailValue, "Not Available, Available", false);
    auditTrailAccess.setTabIndex(27);
    userSecurityForm.addElement(auditTrailAccess);
    String reportConfigValue = "Not Available";
    if (acl != null)
      if (acl.getAccessReportConfig())
        reportConfigValue = "Available";  
    FormRadioButtonGroup reportConfigAccess = new FormRadioButtonGroup("18", reportConfigValue, "Not Available, Available", false);
    reportConfigAccess.setTabIndex(28);
    userSecurityForm.addElement(reportConfigAccess);
    String priceCodeValue = "Not Available";
    if (acl != null)
      if (acl.getAccessPriceCode())
        priceCodeValue = "Available";  
    FormRadioButtonGroup priceCodeAccess = new FormRadioButtonGroup("19", priceCodeValue, "Not Available, Available", false);
    priceCodeAccess.setTabIndex(29);
    userSecurityForm.addElement(priceCodeAccess);
    FormTextField lastUpdated = new FormTextField("lastUpdatedDate", false, 50);
    if (user.getLastUpdateDate() != null)
      lastUpdated.setValue(MilestoneHelper.getLongDate(user.getLastUpdateDate())); 
    userSecurityForm.addElement(lastUpdated);
    FormTextField lastUpdatedBy = new FormTextField("lastUpdatedBy", false, 50);
    if (UserManager.getInstance().getUser(user.getLastUpdatingUser(), true) != null)
      lastUpdatedBy.setValue(UserManager.getInstance().getUser(user.getLastUpdatingUser(), true).getLogin()); 
    userSecurityForm.addElement(lastUpdatedBy);
    FormTextField nameSrch = new FormTextField("nameSrch", "", false, 20);
    nameSrch.setId("nameSrch");
    userSecurityForm.addElement(nameSrch);
    FormTextField userNameSrch = new FormTextField("userNameSrch", "", false, 20);
    userNameSrch.setId("userNameSrch");
    userSecurityForm.addElement(userNameSrch);
    String employedBySrcString = "";
    FormDropDownMenu employedBySrc = MilestoneHelper.getCorporateStructureDropDown("employedBySrc", families, employedBySrcString, true, true);
    employedBySrc.setId("employedBySrc");
    userSecurityForm.addElement(employedBySrc);
    FormDropDownMenu environmentDescriptionSearch = MilestoneHelper.getCorporateStructureDropDown("EnvironmentDescriptionSearch", environments, "", false, true);
    environmentDescriptionSearch.setId("EnvironmentDescriptionSearch");
    userSecurityForm.addElement(environmentDescriptionSearch);
    userSecurityForm.addElement(new FormHidden("cmd", "user-security-editor"));
    context.putDelivery("Form", userSecurityForm);
    if (context.getSessionValue("NOTEPAD_USER_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_USER_VISIBLE")); 
    return userSecurityForm;
  }
  
  private Form buildBlankCompanyForm(Context context) {
    Form userCompanySecurityForm = new Form(this.application, "userCompanySecurityForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    userCompanySecurityForm.addElement(new FormHidden("cmd", "user-environment-security-editor"));
    context.putDelivery("Form", userCompanySecurityForm);
    if (context.getSessionValue("NOTEPAD_USER_ENVIRONMENTS_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_USER_ENVIRONMENTS_VISIBLE")); 
    return userCompanySecurityForm;
  }
  
  private Form buildCompanyForm(Context context, User currentUser, Environment currentEnvironment) {
    Form userCompanySecurityForm = new Form(this.application, "userCompanySecurityForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    Acl currentAcl = currentUser.getAcl();
    Vector companyAcl = currentAcl.getCompanyAcl();
    CompanyAcl company = null;
    CompanyAcl acl = null;
    Vector childrenCompanies = currentEnvironment.getChildren();
    Vector companyIDs = new Vector();
    for (int k = 0; k < childrenCompanies.size(); k++) {
      Company company1 = (Company)childrenCompanies.elementAt(k);
      if (company1 != null)
        companyIDs.add(new Integer(company1.getStructureID())); 
    } 
    for (int i = 0; i < companyAcl.size(); i++) {
      company = (CompanyAcl)companyAcl.get(i);
      if (company != null) {
        Environment aclEnvironment = 
          (Environment)MilestoneHelper.getStructureObject(company.getParentID());
        Integer companyID = new Integer(company.getCompanyId());
        if (companyIDs.contains(companyID))
          acl = company; 
      } 
    } 
    String selectionValue = "View";
    if (acl != null)
      if (acl.getAccessSelection() == 2)
        selectionValue = "Edit";  
    FormRadioButtonGroup selectionAccess = new FormRadioButtonGroup("1", selectionValue, "View, Edit", false);
    selectionAccess.setTabIndex(10);
    userCompanySecurityForm.addElement(selectionAccess);
    String scheduleValue = "View";
    if (acl != null)
      if (acl.getAccessSchedule() == 2)
        scheduleValue = "Edit";  
    FormRadioButtonGroup scheduleAccess = new FormRadioButtonGroup("2", scheduleValue, "View, Edit", false);
    scheduleAccess.setTabIndex(11);
    userCompanySecurityForm.addElement(scheduleAccess);
    String manufacturingValue = "View";
    if (acl != null)
      if (acl.getAccessManufacturing() == 2)
        manufacturingValue = "Edit";  
    FormRadioButtonGroup manufacturingAccess = new FormRadioButtonGroup("3", manufacturingValue, "View, Edit", false);
    manufacturingAccess.setTabIndex(12);
    userCompanySecurityForm.addElement(manufacturingAccess);
    String pfmValue = "View";
    if (acl != null)
      if (acl.getAccessPfmForm() == 2)
        pfmValue = "Edit";  
    FormRadioButtonGroup pfmAccess = new FormRadioButtonGroup("4", pfmValue, "View, Edit", false);
    pfmAccess.setTabIndex(13);
    userCompanySecurityForm.addElement(pfmAccess);
    String bomValue = "View";
    if (acl != null)
      if (acl.getAccessBomForm() == 2)
        bomValue = "Edit";  
    FormRadioButtonGroup bomAccess = new FormRadioButtonGroup("5", bomValue, "View, Edit", false);
    bomAccess.setTabIndex(14);
    userCompanySecurityForm.addElement(bomAccess);
    String reportValue = "View";
    if (acl != null)
      if (acl.getAccessReport() == 2)
        reportValue = "Edit";  
    FormRadioButtonGroup reportAccess = new FormRadioButtonGroup("6", reportValue, "View, Edit", false);
    reportAccess.setTabIndex(15);
    userCompanySecurityForm.addElement(reportAccess);
    String templateValue = "View";
    if (acl != null)
      if (acl.getAccessTemplate() == 2)
        templateValue = "Edit";  
    FormRadioButtonGroup templateAccess = new FormRadioButtonGroup("7", templateValue, "View, Edit", false);
    templateAccess.setTabIndex(16);
    userCompanySecurityForm.addElement(templateAccess);
    String taskValue = "View";
    if (acl != null)
      if (acl.getAccessTask() == 2)
        taskValue = "Edit";  
    FormRadioButtonGroup taskAccess = new FormRadioButtonGroup("8", taskValue, "View, Edit", false);
    taskAccess.setTabIndex(17);
    userCompanySecurityForm.addElement(taskAccess);
    String dayTypeValue = "View";
    if (acl != null)
      if (acl.getAccessDayType() == 2)
        dayTypeValue = "Edit";  
    FormRadioButtonGroup dayTypeAccess = new FormRadioButtonGroup("9", dayTypeValue, "View, Edit", false);
    dayTypeAccess.setTabIndex(18);
    userCompanySecurityForm.addElement(dayTypeAccess);
    String userValue = "View";
    if (acl != null)
      if (acl.getAccessUser() == 2)
        userValue = "Edit";  
    FormRadioButtonGroup userAccess = new FormRadioButtonGroup("10", userValue, "View, Edit", false);
    userAccess.setTabIndex(19);
    userCompanySecurityForm.addElement(userAccess);
    String familyValue = "View";
    if (acl != null)
      if (acl.getAccessFamily() == 2)
        familyValue = "Edit";  
    FormRadioButtonGroup familyAccess = new FormRadioButtonGroup("11", familyValue, "View, Edit", false);
    familyAccess.setTabIndex(20);
    userCompanySecurityForm.addElement(familyAccess);
    String environmentValue = "View";
    if (acl != null)
      if (acl.getAccessEnvironment() == 2)
        environmentValue = "Edit";  
    FormRadioButtonGroup environmentAccess = new FormRadioButtonGroup("20", environmentValue, "View, Edit", false);
    environmentAccess.setTabIndex(21);
    userCompanySecurityForm.addElement(environmentAccess);
    String companyValue = "View";
    if (acl != null)
      if (acl.getAccessCompany() == 2)
        companyValue = "Edit";  
    FormRadioButtonGroup companyAccess = new FormRadioButtonGroup("12", companyValue, "View, Edit", false);
    companyAccess.setTabIndex(22);
    userCompanySecurityForm.addElement(companyAccess);
    String divisionValue = "View";
    if (acl != null)
      if (acl.getAccessDivision() == 2)
        divisionValue = "Edit";  
    FormRadioButtonGroup divisionAccess = new FormRadioButtonGroup("13", divisionValue, "View, Edit", false);
    divisionAccess.setTabIndex(23);
    userCompanySecurityForm.addElement(divisionAccess);
    String labelValue = "View";
    if (acl != null)
      if (acl.getAccessLabel() == 2)
        labelValue = "Edit";  
    FormRadioButtonGroup labelAccess = new FormRadioButtonGroup("14", labelValue, "View, Edit", false);
    labelAccess.setTabIndex(24);
    userCompanySecurityForm.addElement(labelAccess);
    String tableValue = "View";
    if (acl != null)
      if (acl.getAccessTable() == 2)
        tableValue = "Edit";  
    FormRadioButtonGroup tableAccess = new FormRadioButtonGroup("15", tableValue, "View, Edit", false);
    tableAccess.setTabIndex(25);
    userCompanySecurityForm.addElement(tableAccess);
    String parameterValue = "View";
    if (acl != null)
      if (acl.getAccessParameter() == 2)
        parameterValue = "Edit";  
    FormRadioButtonGroup parameterAccess = new FormRadioButtonGroup("16", parameterValue, "View, Edit", false);
    parameterAccess.setTabIndex(26);
    userCompanySecurityForm.addElement(parameterAccess);
    String auditTrailValue = "View";
    if (acl != null)
      if (acl.getAccessAuditTrail() == 2)
        auditTrailValue = "Edit";  
    FormRadioButtonGroup auditTrailAccess = new FormRadioButtonGroup("17", auditTrailValue, "View, Edit", false);
    auditTrailAccess.setTabIndex(27);
    userCompanySecurityForm.addElement(auditTrailAccess);
    String reportConfigValue = "View";
    if (acl != null)
      if (acl.getAccessReportConfig() == 2)
        reportConfigValue = "Edit";  
    FormRadioButtonGroup reportConfigAccess = new FormRadioButtonGroup("18", reportConfigValue, "View, Edit", false);
    reportConfigAccess.setTabIndex(28);
    userCompanySecurityForm.addElement(reportConfigAccess);
    String priceCodeValue = "View";
    if (acl != null)
      if (acl.getAccessPriceCode() == 2)
        priceCodeValue = "Edit";  
    FormRadioButtonGroup priceCodeAccess = new FormRadioButtonGroup("19", priceCodeValue, "View, Edit", false);
    priceCodeAccess.setTabIndex(29);
    userCompanySecurityForm.addElement(priceCodeAccess);
    FormTextField nameSrch = new FormTextField("nameSrch", "", false, 20);
    nameSrch.setId("nameSrch");
    userCompanySecurityForm.addElement(nameSrch);
    userCompanySecurityForm.addElement(new FormHidden("cmd", "user-environment-security-editor"));
    context.putDelivery("Form", userCompanySecurityForm);
    context.putDelivery("currentUser", currentUser);
    context.putDelivery("currentEnvironment", currentEnvironment);
    if (context.getSessionValue("NOTEPAD_USER_ENVIRONMENTS_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_USER_ENVIRONMENTS_VISIBLE")); 
    return userCompanySecurityForm;
  }
  
  public Notepad getUsersNotepad(Context context) {
    Vector contents = new Vector();
    if (MilestoneHelper.getNotepadFromSession(7, context) != null) {
      Notepad notepad = MilestoneHelper.getNotepadFromSession(7, context);
      if (notepad.getAllContents() == null) {
        this.log.debug("---------Reseting note pad contents------------");
        contents = UserManager.getInstance().getUserNotepadList(notepad);
        notepad.setAllContents(contents);
      } 
      return notepad;
    } 
    String[] columnNames = { "Login Name", "User Name", "Employed by" };
    contents = UserManager.getInstance().getUserNotepadList(null);
    Notepad notepad = new Notepad(contents, 0, 7, "Users", 7, columnNames);
    UserManager.getInstance().setUserNotepadQuery(context, notepad);
    return notepad;
  }
  
  public Notepad getUserCompanyNotepad(Vector contents, Context context, int userId) {
    if (MilestoneHelper.getNotepadFromSession(21, context) != null) {
      Notepad notepad = MilestoneHelper.getNotepadFromSession(21, context);
      contents = UserManager.getInstance().getUserEnvironmentNotepadList(userId, notepad);
      notepad.setAllContents(contents);
      return notepad;
    } 
    String[] columnNames = { "Environment Name", "Family Name" };
    contents = UserManager.getInstance().getUserEnvironmentNotepadList(userId, null);
    return new Notepad(contents, 0, 7, "User-Environments", 21, columnNames);
  }
  
  private boolean userCompanySecuritySearch(Context context) {
    Notepad notepad = MilestoneHelper.getNotepadFromSession(21, context);
    UserManager.getInstance().setUserCompanyNotepadQuery(context, notepad);
    userCompanySecurityEditor(context);
    return true;
  }
  
  private boolean userCompanySecuritySearchResults(Context context) { return context.includeJSP("user-company-security-search-results.jsp"); }
  
  private boolean userCompanySecurityEditor(Context context) {
    User user = MilestoneHelper.getScreenUser(context);
    Company company = null;
    Environment environment = null;
    Vector contents = new Vector();
    if (user == null)
      return userSecurityEditor(context); 
    Notepad notepad = getUserCompanyNotepad(contents, context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    environment = MilestoneHelper.getScreenUserEnvironment(context);
    Vector environments = MilestoneHelper.getUserEnvironments(user.getUserId());
    if (environments.size() > 0) {
      Form form = null;
      if (environment != null) {
        form = buildCompanyForm(context, user, environment);
      } else {
        form = buildBlankCompanyForm(context);
        if (context.getSessionValue("NOTEPAD_USER_ENVIRONMENTS_VISIBLE") != null)
          context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_USER_ENVIRONMENTS_VISIBLE")); 
        return context.includeJSP("blank-user-company-security-editor.jsp");
      } 
      return context.includeJSP("user-company-security-editor.jsp");
    } 
    return userSecurityEditor(context);
  }
  
  private boolean userCompanySecuritySave(Context context) {
    User sessionUser = MilestoneSecurity.getUser(context);
    Environment currentEnvironment = null;
    currentEnvironment = MilestoneHelper.getScreenUserEnvironment(context);
    User currentUser = (User)context.getSessionValue("securityUser");
    Acl currentAcl = currentUser.getAcl();
    Vector companyAcl = currentAcl.getCompanyAcl();
    CompanyAcl acl = null;
    CompanyAcl tempAcl = null;
    Vector childrenCompanies = currentEnvironment.getChildren();
    Vector companyIDs = new Vector();
    for (int k = 0; k < childrenCompanies.size(); k++) {
      Company company1 = (Company)childrenCompanies.elementAt(k);
      companyIDs.add(new Integer(company1.getStructureID()));
    } 
    for (int i = 0; i < companyAcl.size(); i++) {
      tempAcl = (CompanyAcl)companyAcl.get(i);
      if (tempAcl != null) {
        Environment aclEnvironment = 
          (Environment)MilestoneHelper.getStructureObject(tempAcl.getParentID());
        Integer companyID = new Integer(tempAcl.getCompanyId());
        if (companyIDs.contains(companyID))
          acl = tempAcl; 
      } 
    } 
    Form form = buildCompanyForm(context, currentUser, currentEnvironment);
    form.setValues(context);
    String selectionValue = form.getStringValue("1");
    int selectionBool = 0;
    if (currentAcl.getAccessSelection() && selectionValue.equalsIgnoreCase("Edit")) {
      selectionBool = 2;
    } else if (currentAcl.getAccessSelection()) {
      selectionBool = 1;
    } 
    acl.setAccessSelection(selectionBool);
    String scheduleValue = form.getStringValue("2");
    int scheduleBool = 0;
    if (currentAcl.getAccessSchedule() && scheduleValue.equalsIgnoreCase("Edit")) {
      scheduleBool = 2;
    } else if (currentAcl.getAccessSchedule()) {
      scheduleBool = 1;
    } 
    acl.setAccessSchedule(scheduleBool);
    String manufacturingValue = form.getStringValue("3");
    int manufacturingBool = 0;
    if (currentAcl.getAccessManufacturing() && manufacturingValue.equalsIgnoreCase("Edit")) {
      manufacturingBool = 2;
    } else if (currentAcl.getAccessManufacturing()) {
      manufacturingBool = 1;
    } 
    acl.setAccessManufacturing(manufacturingBool);
    String pfmValue = form.getStringValue("4");
    int pfmBool = 0;
    if (currentAcl.getAccessPfmForm() && pfmValue.equalsIgnoreCase("Edit")) {
      pfmBool = 2;
    } else if (currentAcl.getAccessPfmForm()) {
      pfmBool = 1;
    } 
    acl.setAccessPfmForm(pfmBool);
    String bomValue = form.getStringValue("5");
    int bomBool = 0;
    if (currentAcl.getAccessBomForm() && bomValue.equalsIgnoreCase("Edit")) {
      bomBool = 2;
    } else if (currentAcl.getAccessBomForm()) {
      bomBool = 1;
    } 
    acl.setAccessBomForm(bomBool);
    String reportValue = form.getStringValue("6");
    int reportBool = 0;
    if (currentAcl.getAccessReport() && reportValue.equalsIgnoreCase("Edit")) {
      reportBool = 2;
    } else if (currentAcl.getAccessReport()) {
      reportBool = 1;
    } 
    acl.setAccessReport(reportBool);
    String templateValue = form.getStringValue("7");
    int templateBool = 0;
    if (currentAcl.getAccessTemplate() && templateValue.equalsIgnoreCase("Edit")) {
      templateBool = 2;
    } else if (currentAcl.getAccessTemplate()) {
      templateBool = 1;
    } 
    acl.setAccessTemplate(templateBool);
    String taskValue = form.getStringValue("8");
    int taskBool = 0;
    if (currentAcl.getAccessTask() && taskValue.equalsIgnoreCase("Edit")) {
      taskBool = 2;
    } else if (currentAcl.getAccessTask()) {
      taskBool = 1;
    } 
    acl.setAccessTask(taskBool);
    String dayValue = form.getStringValue("9");
    int dayBool = 0;
    if (currentAcl.getAccessDayType() && dayValue.equalsIgnoreCase("Edit")) {
      dayBool = 2;
    } else if (currentAcl.getAccessDayType()) {
      dayBool = 1;
    } 
    acl.setAccessDayType(dayBool);
    String userValue = form.getStringValue("10");
    int userBool = 0;
    if (currentAcl.getAccessUser() && userValue.equalsIgnoreCase("Edit")) {
      userBool = 2;
    } else if (currentAcl.getAccessUser()) {
      userBool = 1;
    } 
    acl.setAccessUser(userBool);
    String familyValue = form.getStringValue("11");
    int familyBool = 0;
    if (currentAcl.getAccessFamily() && familyValue.equalsIgnoreCase("Edit")) {
      familyBool = 2;
    } else if (currentAcl.getAccessFamily()) {
      familyBool = 1;
    } 
    acl.setAccessFamily(familyBool);
    String environmentValue = form.getStringValue("20");
    int environmentBool = 0;
    if (currentAcl.getAccessEnvironment() && environmentValue.equalsIgnoreCase("Edit")) {
      environmentBool = 2;
    } else if (currentAcl.getAccessEnvironment()) {
      environmentBool = 1;
    } 
    acl.setAccessEnvironment(environmentBool);
    String companyValue = form.getStringValue("12");
    int companyBool = 0;
    if (currentAcl.getAccessCompany() && companyValue.equalsIgnoreCase("Edit")) {
      companyBool = 2;
    } else if (currentAcl.getAccessCompany()) {
      companyBool = 1;
    } 
    acl.setAccessCompany(companyBool);
    String divisionValue = form.getStringValue("13");
    int divisionBool = 0;
    if (currentAcl.getAccessDivision() && divisionValue.equalsIgnoreCase("Edit")) {
      divisionBool = 2;
    } else if (currentAcl.getAccessDivision()) {
      divisionBool = 1;
    } 
    acl.setAccessDivision(divisionBool);
    String labelValue = form.getStringValue("14");
    int labelBool = 0;
    if (currentAcl.getAccessLabel() && labelValue.equalsIgnoreCase("Edit")) {
      labelBool = 2;
    } else if (currentAcl.getAccessLabel()) {
      labelBool = 1;
    } 
    acl.setAccessLabel(labelBool);
    String tableValue = form.getStringValue("15");
    int tableBool = 0;
    if (currentAcl.getAccessTable() && tableValue.equalsIgnoreCase("Edit")) {
      tableBool = 2;
    } else if (currentAcl.getAccessTable()) {
      tableBool = 1;
    } 
    acl.setAccessTable(tableBool);
    String parameterValue = form.getStringValue("16");
    int parameterBool = 0;
    if (currentAcl.getAccessParameter() && parameterValue.equalsIgnoreCase("Edit")) {
      parameterBool = 2;
    } else if (currentAcl.getAccessParameter()) {
      parameterBool = 1;
    } 
    acl.setAccessParameter(parameterBool);
    String auditValue = form.getStringValue("17");
    int auditBool = 0;
    if (currentAcl.getAccessAuditTrail() && auditValue.equalsIgnoreCase("Edit")) {
      auditBool = 2;
    } else if (currentAcl.getAccessAuditTrail()) {
      auditBool = 1;
    } 
    acl.setAccessAuditTrail(auditBool);
    String configValue = form.getStringValue("18");
    int configBool = 0;
    if (currentAcl.getAccessReportConfig() && configValue.equalsIgnoreCase("Edit")) {
      configBool = 2;
    } else if (currentAcl.getAccessReportConfig()) {
      configBool = 1;
    } 
    acl.setAccessReportConfig(configBool);
    String priceValue = form.getStringValue("19");
    int priceBool = 0;
    if (currentAcl.getAccessPriceCode() && priceValue.equalsIgnoreCase("Edit")) {
      priceBool = 2;
    } else if (currentAcl.getAccessPriceCode()) {
      priceBool = 1;
    } 
    acl.setAccessPriceCode(priceBool);
    if (!form.isUnchanged()) {
      FormValidation formValidation = form.validate();
      if (formValidation.isGood()) {
        Vector companyVector = currentEnvironment.getCompanies();
        for (int j = 0; j < companyVector.size(); j++) {
          Company environmentCompany = (Company)companyVector.elementAt(j);
          UserManager.getInstance().saveCompany(currentUser, sessionUser, acl, environmentCompany);
        } 
        Cache.flushCachedVariableAllUsers(currentUser.getUserId());
        this.log.debug("===============================clearUserCompaniesFromSession called.");
        MilestoneHelper.clearUserCompaniesFromSession(context);
        form = buildCompanyForm(context, currentUser, currentEnvironment);
        context.putDelivery("Form", form);
        if (currentUser.getUserId() == sessionUser.getUserId()) {
          context.removeSessionValue("user");
          context.putSessionValue("UserDefaultsApplied", "true");
          context.putSessionValue("user", currentUser);
        } 
        Vector contents = new Vector();
        Notepad notepad = getUserCompanyNotepad(contents, context, currentUser.getUserId());
        MilestoneHelper.putNotepadIntoSession(notepad, context);
      } else {
        context.putDelivery("FormValidation", formValidation);
      } 
    } 
    context.putDelivery("Form", form);
    return context.includeJSP("user-company-security-editor.jsp");
  }
  
  private Form buildNewForm(Context context) {
    context.putSessionValue("showAssigned", "NEW");
    Form userSecurityForm = new Form(this.application, "userSecurityForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    FormTextField login = new FormTextField("login", "", true, 30, 100);
    login.setTabIndex(1);
    userSecurityForm.addElement(login);
    FormPasswordField password = new FormPasswordField("password", "", true, 30, 30);
    password.setTabIndex(2);
    userSecurityForm.addElement(password);
    FormTextField reportto = new FormTextField("reportto", "", false, 30, 30);
    reportto.setTabIndex(3);
    userSecurityForm.addElement(reportto);
    FormTextField fullname = new FormTextField("fullname", "", true, 30, 100);
    fullname.setTabIndex(4);
    userSecurityForm.addElement(fullname);
    Vector userLocations = Cache.getLookupDetailValuesFromDatabase(53);
    FormDropDownMenu location = MilestoneHelper.getLookupDropDown("location", userLocations, "", false, true);
    location.setTabIndex(5);
    userSecurityForm.addElement(location);
    Vector families = Cache.getInstance().getFamilies();
    FormDropDownMenu employedby = MilestoneHelper.getCorporateStructureDropDown("employedby", families, "", false, true);
    employedby.setTabIndex(6);
    userSecurityForm.addElement(employedby);
    FormTextField email = new FormTextField("email", "", false, 30, 50);
    email.setTabIndex(7);
    userSecurityForm.addElement(email);
    FormTextField phone = new FormTextField("phone", "", false, 30, 30);
    phone.setTabIndex(8);
    userSecurityForm.addElement(phone);
    FormTextField fax = new FormTextField("fax", "", false, 30, 30);
    fax.setTabIndex(11);
    userSecurityForm.addElement(fax);
    FormCheckBox inactive = new FormCheckBox("inactive", "Inactive", false, false);
    inactive.setId("inactive");
    inactive.setTabIndex(12);
    userSecurityForm.addElement(inactive);
    FormCheckBox administrator = new FormCheckBox("administrator", "Administrator", false, false);
    administrator.setId("adminstrator");
    administrator.setTabIndex(13);
    userSecurityForm.addElement(administrator);
    FormDropDownMenu deptFilter = MilestoneHelper.getDepartmentDropDown("deptFilter", "All", false);
    String[] values = deptFilter.getValueList();
    String[] menuText = deptFilter.getMenuTextList();
    values[0] = "All";
    menuText[0] = "All";
    deptFilter.setValueList(values);
    deptFilter.setMenuTextList(menuText);
    deptFilter.setTabIndex(12);
    userSecurityForm.addElement(deptFilter);
    FormRadioButtonGroup IsModifyDept = new FormRadioButtonGroup("IsModifyDept", "Yes", "Yes, No", false);
    IsModifyDept.setTabIndex(13);
    userSecurityForm.addElement(IsModifyDept);
    FormDropDownMenu filter = new FormDropDownMenu("filter", "All", "All,Only Label Tasks, Only UML Tasks", true);
    filter.setTabIndex(9);
    userSecurityForm.addElement(filter);
    FormRadioButtonGroup IsModify = new FormRadioButtonGroup("IsModify", "Yes", "Yes, No", false);
    IsModify.setTabIndex(10);
    userSecurityForm.addElement(IsModify);
    Vector environments = Cache.getInstance().getEnvironments();
    for (int i = 0; i < environments.size(); i++) {
      Environment environment = (Environment)environments.elementAt(i);
      if (environment != null) {
        FormCheckBox environmentCheckbox = new FormCheckBox("ue" + environment.getStructureID(), "ue" + environment.getStructureID(), environment.getName(), false, false);
        environmentCheckbox.setId("ue" + environment.getStructureID());
        userSecurityForm.addElement(environmentCheckbox);
      } 
    } 
    String selectionValue = "Not Available";
    FormRadioButtonGroup selectionAccess = new FormRadioButtonGroup("1", selectionValue, "Not Available, Available", false);
    selectionAccess.setTabIndex(10);
    userSecurityForm.addElement(selectionAccess);
    String scheduleValue = "Not Available";
    FormRadioButtonGroup scheduleAccess = new FormRadioButtonGroup("2", scheduleValue, "Not Available, Available", false);
    scheduleAccess.setTabIndex(11);
    userSecurityForm.addElement(scheduleAccess);
    String manufacturingValue = "Not Available";
    FormRadioButtonGroup manufacturingAccess = new FormRadioButtonGroup("3", manufacturingValue, "Not Available, Available", false);
    manufacturingAccess.setTabIndex(12);
    userSecurityForm.addElement(manufacturingAccess);
    String pfmValue = "Not Available";
    FormRadioButtonGroup pfmAccess = new FormRadioButtonGroup("4", pfmValue, "Not Available, Available", false);
    pfmAccess.setTabIndex(13);
    userSecurityForm.addElement(pfmAccess);
    String bomValue = "Not Available";
    FormRadioButtonGroup bomAccess = new FormRadioButtonGroup("5", bomValue, "Not Available, Available", false);
    bomAccess.setTabIndex(14);
    userSecurityForm.addElement(bomAccess);
    String reportValue = "Not Available";
    FormRadioButtonGroup reportAccess = new FormRadioButtonGroup("6", reportValue, "Not Available, Available", false);
    reportAccess.setTabIndex(15);
    userSecurityForm.addElement(reportAccess);
    String templateValue = "Not Available";
    FormRadioButtonGroup templateAccess = new FormRadioButtonGroup("7", templateValue, "Not Available, Available", false);
    templateAccess.setTabIndex(16);
    userSecurityForm.addElement(templateAccess);
    String taskValue = "Not Available";
    FormRadioButtonGroup taskAccess = new FormRadioButtonGroup("8", taskValue, "Not Available, Available", false);
    taskAccess.setTabIndex(17);
    userSecurityForm.addElement(taskAccess);
    String dayTypeValue = "Not Available";
    FormRadioButtonGroup dayTypeAccess = new FormRadioButtonGroup("9", dayTypeValue, "Not Available, Available", false);
    dayTypeAccess.setTabIndex(18);
    userSecurityForm.addElement(dayTypeAccess);
    String userValue = "Not Available";
    FormRadioButtonGroup userAccess = new FormRadioButtonGroup("10", userValue, "Not Available, Available", false);
    userAccess.setTabIndex(19);
    userSecurityForm.addElement(userAccess);
    String familyValue = "Not Available";
    FormRadioButtonGroup familyAccess = new FormRadioButtonGroup("11", familyValue, "Not Available, Available", false);
    familyAccess.setTabIndex(20);
    userSecurityForm.addElement(familyAccess);
    String environmentValue = "Not Available";
    FormRadioButtonGroup environmentAccess = new FormRadioButtonGroup("20", environmentValue, "Not Available, Available", false);
    environmentAccess.setTabIndex(21);
    userSecurityForm.addElement(environmentAccess);
    String companyValue = "Not Available";
    FormRadioButtonGroup companyAccess = new FormRadioButtonGroup("12", companyValue, "Not Available, Available", false);
    companyAccess.setTabIndex(22);
    userSecurityForm.addElement(companyAccess);
    String divisionValue = "Not Available";
    FormRadioButtonGroup divisionAccess = new FormRadioButtonGroup("13", divisionValue, "Not Available, Available", false);
    divisionAccess.setTabIndex(23);
    userSecurityForm.addElement(divisionAccess);
    String labelValue = "Not Available";
    FormRadioButtonGroup labelAccess = new FormRadioButtonGroup("14", labelValue, "Not Available, Available", false);
    labelAccess.setTabIndex(24);
    userSecurityForm.addElement(labelAccess);
    String tableValue = "Not Available";
    FormRadioButtonGroup tableAccess = new FormRadioButtonGroup("15", tableValue, "Not Available, Available", false);
    tableAccess.setTabIndex(25);
    userSecurityForm.addElement(tableAccess);
    String parameterValue = "Not Available";
    FormRadioButtonGroup parameterAccess = new FormRadioButtonGroup("16", parameterValue, "Not Available, Available", false);
    parameterAccess.setTabIndex(26);
    userSecurityForm.addElement(parameterAccess);
    String auditTrailValue = "Not Available";
    FormRadioButtonGroup auditTrailAccess = new FormRadioButtonGroup("17", auditTrailValue, "Not Available, Available", false);
    auditTrailAccess.setTabIndex(27);
    userSecurityForm.addElement(auditTrailAccess);
    String reportConfigValue = "Not Available";
    FormRadioButtonGroup reportConfigAccess = new FormRadioButtonGroup("18", reportConfigValue, "Not Available, Available", false);
    reportConfigAccess.setTabIndex(28);
    userSecurityForm.addElement(reportConfigAccess);
    String priceCodeValue = "Not Available";
    FormRadioButtonGroup priceCodeAccess = new FormRadioButtonGroup("19", priceCodeValue, "Not Available, Available", false);
    priceCodeAccess.setTabIndex(29);
    userSecurityForm.addElement(priceCodeAccess);
    FormTextField lastUpdated = new FormTextField("lastUpdatedDate", false, 50);
    userSecurityForm.addElement(lastUpdated);
    FormTextField lastUpdatedBy = new FormTextField("lastUpdatedBy", false, 50);
    userSecurityForm.addElement(lastUpdatedBy);
    FormTextField nameSrch = new FormTextField("nameSrch", "", false, 20);
    nameSrch.setId("nameSrch");
    userSecurityForm.addElement(nameSrch);
    FormTextField userNameSrch = new FormTextField("userNameSrch", "", false, 20);
    userNameSrch.setId("userNameSrch");
    userSecurityForm.addElement(userNameSrch);
    String employedBySrcString = "";
    FormDropDownMenu employedBySrc = MilestoneHelper.getCorporateStructureDropDown("employedBySrc", families, employedBySrcString, true, true);
    employedBySrc.setId("employedBySrc");
    userSecurityForm.addElement(employedBySrc);
    FormDropDownMenu environmentDescriptionSearch = MilestoneHelper.getCorporateStructureDropDown("EnvironmentDescriptionSearch", environments, "", false, true);
    environmentDescriptionSearch.setId("EnvironmentDescriptionSearch");
    userSecurityForm.addElement(environmentDescriptionSearch);
    userSecurityForm.addElement(new FormHidden("cmd", "user-security-new"));
    context.putDelivery("Form", userSecurityForm);
    if (context.getSessionValue("NOTEPAD_USER_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_USER_VISIBLE")); 
    return userSecurityForm;
  }
  
  private boolean userSecuritySort(Context context) {
    int sort = Integer.parseInt(context.getParameter("OrderBy"));
    int userId = ((User)context.getSessionValue("user")).getUserId();
    Notepad notepad = MilestoneHelper.getNotepadFromSession(7, context);
    if (notepad.getSearchQuery() == null || notepad.getSearchQuery().equals(""))
      notepad.setSearchQuery(UserManager.getInstance().getDefaultQuery()); 
    notepad.setOrderBy(" ORDER BY vi_all_user.[" + MilestoneConstants.SORT_USER[sort] + "]");
    notepad.setAllContents(null);
    notepad = getUsersNotepad(context);
    notepad.goToSelectedPage();
    userSecurityEditor(context);
    return true;
  }
  
  private boolean userCompanySecuritySort(Context context) {
    int sort = Integer.parseInt(context.getParameter("OrderBy"));
    User user = MilestoneHelper.getScreenUser(context);
    int userId = user.getUserId();
    Notepad notepad = MilestoneHelper.getNotepadFromSession(21, context);
    if (notepad.getSearchQuery() == null || notepad.getSearchQuery().equals(""))
      notepad.setSearchQuery(UserManager.getInstance().getDefaultUserCompanyQuery(userId)); 
    notepad.setOrderBy(" ORDER BY " + MilestoneConstants.SORT_COMPANY_USER[sort]);
    notepad.setAllContents(null);
    notepad = getUsersNotepad(context);
    notepad.goToSelectedPage();
    userCompanySecurityEditor(context);
    return true;
  }
  
  private boolean userGoToBlank(Context context) {
    context.putDelivery("isNotePadVisible", new Boolean(MilestoneHelper.getNotePadVisiblitiy(7, context)));
    Form form = new Form(this.application, "form", this.application.getInfrastructure().getServletURL(), "Post");
    form.addElement(new FormHidden("cmd", "daytype-editor"));
    form.addElement(new FormHidden("OrderBy", "", true));
    String employedBySrcString = "";
    Vector families = Cache.getInstance().getFamilies();
    FormDropDownMenu employedBySrc = MilestoneHelper.getCorporateStructureDropDown("employedBySrc", families, employedBySrcString, true, true);
    employedBySrc.setId("employedBySrc");
    form.addElement(employedBySrc);
    Vector environments = Cache.getInstance().getEnvironments();
    FormDropDownMenu environmentDescriptionSearch = MilestoneHelper.getCorporateStructureDropDown("EnvironmentDescriptionSearch", environments, "", false, true);
    environmentDescriptionSearch.setId("EnvironmentDescriptionSearch");
    form.addElement(environmentDescriptionSearch);
    context.putDelivery("Form", form);
    return context.includeJSP("blank-user-security-editor.jsp");
  }
  
  private boolean userReleasingFamilySave(Dispatcher dispatcher, Context context) {
    ReleasingFamily.save(context);
    return userSecurityEditor(context);
  }
  
  public static void makeDynamic(User savedUser, Context context, User sessionUser) {
    context.putSessionValue("ScheduleTaskSort", "-1");
    context.putSessionValue("ScheduleTaskSortDigital", "-1");
    context.putSessionValue("ResetOpeningScreen", "true");
    User userClone = null;
    try {
      userClone = (User)sessionUser.clone();
    } catch (CloneNotSupportedException ex) {
      userClone = sessionUser;
    } 
    userClone.SS_searchInitiated = false;
    context.putSessionValue("ResetSearchVariables", userClone);
    context.putSessionValue("ResetSelectionSortOrder", "true");
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\SecurityHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */