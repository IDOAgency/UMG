package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.gemini.Context;
import com.techempower.gemini.Dispatcher;
import com.techempower.gemini.FormCheckBox;
import com.techempower.gemini.FormDropDownMenu;
import com.techempower.gemini.FormRadioButtonGroup;
import com.techempower.gemini.FormTextField;
import com.techempower.gemini.FormValidation;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.Form;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.ReleasingFamily;
import com.universal.milestone.ReportConfigManager;
import com.universal.milestone.SecureHandler;
import com.universal.milestone.SelectionHandler;
import com.universal.milestone.User;
import com.universal.milestone.UserManager;
import com.universal.milestone.UserPreferences;
import com.universal.milestone.UserPreferencesHandler;
import java.util.Vector;

public class UserPreferencesHandler extends SecureHandler {
  public static final String COMPONENT_CODE = "hSec";
  
  public GeminiApplication application;
  
  public ComponentLog log;
  
  public UserPreferencesHandler(GeminiApplication application) {
    this.application = application;
    this.log = application.getLog("hSec");
  }
  
  public String getDescription() { return "UserPreferences"; }
  
  public boolean acceptRequest(Dispatcher dispatcher, Context context, String command) {
    if (super.acceptRequest(dispatcher, context, command))
      if (command.startsWith("user-preferences"))
        return handleRequest(dispatcher, context, command);  
    return false;
  }
  
  public boolean handleRequest(Dispatcher dispatcher, Context context, String command) {
    if (command.equalsIgnoreCase("user-preferences-save")) {
      userPreferencesSave(context);
    } else if (command.equalsIgnoreCase("user-preferences-editor")) {
      userPreferencesEditor(context);
    } 
    return true;
  }
  
  public Form userPreferencesEditor(Context context) {
    User user = (User)context.getSessionValue("user");
    if (user != null) {
      Form form = null;
      if (user != null) {
        form = buildForm(context, user);
      } else {
        form = buildNewForm(context);
      } 
      return form;
    } 
    return null;
  }
  
  public Form userPreferencesSave(Context context) {
    User user = (User)context.getSessionValue("securityUser");
    Form form = buildForm(context, user);
    if (UserManager.getInstance().isTimestampValid(user)) {
      form.setValues(context);
      String login = form.getStringValue("login");
      user.setLogin(login);
      if (!UserManager.getInstance().isDuplicate(user) && 
        !form.isUnchanged()) {
        FormValidation formValidation = form.validate();
        if (formValidation.isGood()) {
          User savedUser = null;
          form = buildForm(context, savedUser);
          context.putSessionValue("securityUser", savedUser);
        } 
      } 
    } else {
      context.putDelivery("AlertMessage", "Duplicate.");
      form = buildForm(context, user);
    } 
    return form;
  }
  
  public Form buildForm(Context context, User user) {
    Form userSecurityForm = new Form(this.application, "userSecurityForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    UserPreferences up = null;
    if (user == null || user.getPreferences() == null) {
      up = new UserPreferences();
    } else {
      up = user.getPreferences();
    } 
    String[] values = new String[7];
    values[0] = "2";
    values[1] = "3";
    values[2] = "4";
    values[3] = "5";
    values[4] = "6";
    values[5] = "1";
    values[6] = "7";
    String[] labels = new String[7];
    labels[0] = "Schedule";
    labels[1] = "Selection";
    labels[2] = "Manufacturing";
    labels[3] = "PFM";
    labels[4] = "BOM";
    labels[5] = "Release Calendar";
    labels[6] = "Reports";
    FormRadioButtonGroup openingScreen = new FormRadioButtonGroup("openingScreen", String.valueOf(up.getOpeningScreen()), values, labels, false);
    openingScreen.setTabIndex(50);
    userSecurityForm.addElement(openingScreen);
    values = new String[3];
    values[0] = "1";
    values[1] = "2";
    values[2] = "3";
    labels = new String[3];
    labels[0] = "Do not auto-close";
    labels[1] = "Close my completed products";
    labels[2] = "Close my completed products when all UML / eCommerce tasks are completed";
    FormRadioButtonGroup autoCloseRadio = new FormRadioButtonGroup("autoCloseRadio", String.valueOf(up.getAutoClose()), values, labels, false);
    autoCloseRadio.setTabIndex(51);
    userSecurityForm.addElement(autoCloseRadio);
    FormTextField autoCloseDays = new FormTextField("autoCloseDays", String.valueOf(up.getAutoCloseDays()), false, 10, 100);
    autoCloseDays.setTabIndex(52);
    userSecurityForm.addElement(autoCloseDays);
    values = new String[3];
    values[0] = "1";
    values[1] = "2";
    values[2] = "3";
    labels = new String[3];
    labels[0] = "Artist";
    labels[1] = "Title";
    labels[2] = "Street Date";
    FormRadioButtonGroup sortBy = new FormRadioButtonGroup("sortBy", String.valueOf(up.getNotepadSortBy()), values, labels, false);
    sortBy.setTabIndex(53);
    userSecurityForm.addElement(sortBy);
    values = new String[2];
    values[0] = "1";
    values[1] = "2";
    labels = new String[2];
    labels[0] = "Ascending";
    labels[1] = "Descending";
    FormRadioButtonGroup order = new FormRadioButtonGroup("order", String.valueOf(up.getNotepadOrder()), values, labels, false);
    order.setTabIndex(54);
    userSecurityForm.addElement(order);
    values = new String[3];
    values[0] = "1";
    values[1] = "2";
    values[2] = "3";
    labels = new String[3];
    labels[0] = "Physical";
    labels[1] = "Digital";
    labels[2] = "Both";
    FormRadioButtonGroup productType = new FormRadioButtonGroup("productType", String.valueOf(up.getNotepadProductType()), values, labels, false);
    productType.setTabIndex(55);
    userSecurityForm.addElement(productType);
    Vector families = SelectionHandler.filterCSO(ReleasingFamily.getUserReleasingFamiliesVectorOfFamilies(context));
    FormDropDownMenu releasingFamilies = MilestoneHelper.getCorporateStructureDropDown("releasingFamilies", families, String.valueOf(up.getSelectionReleasingFamily()), false, true);
    releasingFamilies.addFormEvent("onChange", "return(clickReleasingFamily(this, document.all.envMenu))");
    releasingFamilies.setTabIndex(56);
    userSecurityForm.addElement(releasingFamilies);
    Vector environments = MilestoneHelper.getUserEnvironments(context);
    Vector companies = MilestoneHelper.getUserCompanies(context);
    environments = SelectionHandler.filterSelectionEnvironments(companies);
    companies = MilestoneHelper.removeUnusedCSO(companies, context, -1);
    FormDropDownMenu envMenu = MilestoneHelper.getCorporateStructureDropDown("envMenu", SelectionHandler.filterSelectionEnvironments(companies), String.valueOf(up.getSelectionEnvironment()), false, true);
    envMenu.setTabIndex(58);
    userSecurityForm.addElement(envMenu);
    Vector contactVector = ReportConfigManager.getLabelContacts();
    String contactList = "All,";
    String idList = "0,";
    if (contactVector != null) {
      for (int i = 0; i < contactVector.size(); i++) {
        User userContact = (User)contactVector.get(i);
        if (userContact == null || userContact.getInactive() == 0)
          if (i < contactVector.size() - 1) {
            if (userContact != null) {
              contactList = String.valueOf(contactList) + userContact.getName() + ",";
              idList = String.valueOf(idList) + userContact.getUserId() + ",";
            } 
          } else if (userContact != null) {
            contactList = String.valueOf(contactList) + userContact.getName();
            idList = String.valueOf(idList) + userContact.getUserId();
          }  
      } 
    } else {
      contactList = "All";
      idList = "0";
    } 
    FormDropDownMenu ContactList = new FormDropDownMenu("ContactList", String.valueOf(up.getSelectionLabelContact()), idList, contactList, false);
    ContactList.setTabIndex(59);
    userSecurityForm.addElement(ContactList);
    values = new String[3];
    values[0] = "0";
    values[1] = "1";
    values[2] = "2";
    labels = new String[3];
    labels[0] = "Physical";
    labels[1] = "Digital";
    labels[2] = "Both";
    FormRadioButtonGroup selectionProductType = new FormRadioButtonGroup("selectionProductType", String.valueOf(up.getSelectionProductType()), values, labels, false);
    selectionProductType.setTabIndex(60);
    userSecurityForm.addElement(selectionProductType);
    FormCheckBox status = new FormCheckBox("status", "Include Closed/Cancelled", false, false);
    status.setId("status");
    if (up.getSelectionStatus() > 0)
      status.setChecked(true); 
    status.setTabIndex(61);
    userSecurityForm.addElement(status);
    FormCheckBox priorCriteria = new FormCheckBox("priorCriteria", "Override User Preferences with manually selected search and sort criteria", false, false);
    priorCriteria.setId("priorCriteria");
    if (up.getSelectionPriorCriteria() > 0)
      priorCriteria.setChecked(true); 
    priorCriteria.setTabIndex(62);
    userSecurityForm.addElement(priorCriteria);
    values = new String[6];
    values[0] = "1";
    values[1] = "2";
    values[2] = "3";
    values[3] = "4";
    values[4] = "5";
    values[5] = "6";
    labels = new String[6];
    labels[0] = "Task";
    labels[1] = "Weeks to Release";
    labels[2] = "Due Date";
    labels[3] = "Completion Date";
    labels[4] = "Status";
    labels[5] = "Vendor";
    FormRadioButtonGroup sortBySchedule = new FormRadioButtonGroup("sortBySchedule", String.valueOf(up.getSchedulePhysicalSortBy()), values, labels, false);
    sortBySchedule.setTabIndex(63);
    userSecurityForm.addElement(sortBySchedule);
    values = new String[3];
    values[0] = "1";
    values[1] = "2";
    values[2] = "3";
    labels = new String[3];
    labels[0] = "All";
    labels[1] = "Only UML Tasks";
    labels[2] = "Only Label Tasks";
    FormRadioButtonGroup ownerSchedule = new FormRadioButtonGroup("ownerSchedule", String.valueOf(up.getSchedulePhysicalOwner()), values, labels, false);
    ownerSchedule.setTabIndex(64);
    userSecurityForm.addElement(ownerSchedule);
    values = new String[6];
    values[0] = "1";
    values[1] = "2";
    values[2] = "3";
    values[3] = "4";
    values[4] = "5";
    values[5] = "6";
    labels = new String[6];
    labels[0] = "Task";
    labels[1] = "Weeks to Release";
    labels[2] = "Due Date";
    labels[3] = "Completion Date";
    labels[4] = "Status";
    labels[5] = "Vendor";
    FormRadioButtonGroup sortByDigitalSchedule = new FormRadioButtonGroup("sortByDigitalSchedule", String.valueOf(up.getScheduleDigitalSortBy()), values, labels, false);
    sortByDigitalSchedule.setTabIndex(65);
    userSecurityForm.addElement(sortByDigitalSchedule);
    values = new String[3];
    values[0] = "1";
    values[1] = "2";
    values[2] = "3";
    labels = new String[3];
    labels[0] = "All";
    labels[1] = "Only eCommerce Tasks";
    labels[2] = "Only Label Tasks";
    FormRadioButtonGroup ownerDigitalSchedule = new FormRadioButtonGroup("ownerDigitalSchedule", String.valueOf(up.getScheduleDigitalOwner()), values, labels, false);
    ownerDigitalSchedule.setTabIndex(66);
    userSecurityForm.addElement(ownerDigitalSchedule);
    values = new String[3];
    values[0] = "1";
    values[1] = "2";
    values[2] = "3";
    labels = new String[3];
    labels[0] = "Commercial";
    labels[1] = "Promotional";
    labels[2] = "All";
    FormRadioButtonGroup releaseType = new FormRadioButtonGroup("releaseType", String.valueOf(up.getReportsReleaseType()), values, labels, false);
    releaseType.setTabIndex(67);
    userSecurityForm.addElement(releaseType);
    FormDropDownMenu releasingFamiliesReports = MilestoneHelper.getCorporateStructureDropDown("releasingFamiliesReports", families, String.valueOf(up.getReportsReleasingFamily()), false, true);
    releasingFamiliesReports.addFormEvent("onChange", "return(clickReleasingFamily(this, document.all.envMenuReports))");
    releasingFamiliesReports.setTabIndex(68);
    userSecurityForm.addElement(releasingFamiliesReports);
    FormDropDownMenu envMenuReports = MilestoneHelper.getCorporateStructureDropDown("envMenuReports", SelectionHandler.filterSelectionEnvironments(companies), String.valueOf(up.getReportsEnvironment()), false, true);
    envMenuReports.setTabIndex(70);
    userSecurityForm.addElement(envMenuReports);
    FormDropDownMenu ContactListReports = new FormDropDownMenu("ContactListReports", String.valueOf(up.getReportsLabelContact()), idList, contactList, false);
    ContactListReports.setTabIndex(71);
    userSecurityForm.addElement(ContactListReports);
    Vector contactVector1 = ReportConfigManager.getUmlContacts();
    String contactList1 = "All,";
    idList = "0,";
    if (contactVector1 != null) {
      for (int i = 0; i < contactVector1.size(); i++) {
        User userContact1 = (User)contactVector1.get(i);
        if (i < contactVector1.size() - 1) {
          if (userContact1 != null) {
            contactList1 = String.valueOf(contactList1) + userContact1.getName() + ",";
            idList = String.valueOf(idList) + userContact1.getUserId() + ",";
          } 
        } else if (userContact1 != null) {
          contactList1 = String.valueOf(contactList1) + userContact1.getName();
          idList = String.valueOf(idList) + userContact1.getUserId();
        } 
      } 
    } else {
      contactList1 = "All";
      idList = "0";
    } 
    FormDropDownMenu ContactList1 = new FormDropDownMenu("umlContact", String.valueOf(up.getReportsUMLContact()), idList, contactList1, false);
    ContactList1.setTabIndex(72);
    userSecurityForm.addElement(ContactList1);
    FormCheckBox statusReportsAll = new FormCheckBox("statusReportsAll", "All", false, false);
    statusReportsAll.setId("statusReportsAll");
    if (up.getReportsStatusAll() == 1)
      statusReportsAll.setChecked(true); 
    statusReportsAll.addFormEvent("onClick", "checkAll()");
    statusReportsAll.setTabIndex(73);
    userSecurityForm.addElement(statusReportsAll);
    FormCheckBox statusReportsActive = new FormCheckBox("statusReportsActive", "Active", false, false);
    statusReportsActive.setId("statusReportsActive");
    if (up.getReportsStatusActive() == 1)
      statusReportsActive.setChecked(true); 
    statusReportsActive.addFormEvent("onClick", "processCheck()");
    statusReportsActive.setTabIndex(74);
    userSecurityForm.addElement(statusReportsActive);
    FormCheckBox statusReportsTBS = new FormCheckBox("statusReportsTBS", "TBS", false, false);
    statusReportsTBS.setId("statusReportsTBS");
    if (up.getReportsStatusTBS() == 1)
      statusReportsTBS.setChecked(true); 
    statusReportsTBS.addFormEvent("onClick", "processCheck()");
    statusReportsTBS.setTabIndex(75);
    userSecurityForm.addElement(statusReportsTBS);
    FormCheckBox statusReportsClosed = new FormCheckBox("statusReportsClosed", "Closed", false, false);
    statusReportsClosed.setId("statusReportsClosed");
    if (up.getReportsStatusClosed() == 1)
      statusReportsClosed.setChecked(true); 
    statusReportsClosed.addFormEvent("onClick", "processCheck()");
    statusReportsClosed.setTabIndex(76);
    userSecurityForm.addElement(statusReportsClosed);
    FormCheckBox statusReportsCancelled = new FormCheckBox("statusReportsCancelled", "Cancelled", false, false);
    statusReportsCancelled.setId("statusReportsCancelled");
    if (up.getReportsStatusCancelled() == 1)
      statusReportsCancelled.setChecked(true); 
    statusReportsCancelled.addFormEvent("onClick", "processCheck()");
    statusReportsCancelled.setTabIndex(77);
    userSecurityForm.addElement(statusReportsCancelled);
    context.putDelivery("UserPrefsForm", userSecurityForm);
    context.putDelivery("corporate-array", ReleasingFamily.getJavaScriptCorporateArrayReleasingFamily(context));
    return userSecurityForm;
  }
  
  public Form buildNewForm(Context context) { return buildForm(context, null); }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\UserPreferencesHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */