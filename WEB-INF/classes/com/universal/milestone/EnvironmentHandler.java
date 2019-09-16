package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.StringList;
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
import com.universal.milestone.CorporateObjectSearchObj;
import com.universal.milestone.CorporateStructureManager;
import com.universal.milestone.Environment;
import com.universal.milestone.EnvironmentHandler;
import com.universal.milestone.EnvironmentManager;
import com.universal.milestone.Family;
import com.universal.milestone.Form;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.MilestoneHelper_2;
import com.universal.milestone.MilestoneMessage;
import com.universal.milestone.MilestoneSecurity;
import com.universal.milestone.Notepad;
import com.universal.milestone.PrefixCode;
import com.universal.milestone.SecureHandler;
import com.universal.milestone.User;
import com.universal.milestone.UserManager;
import java.util.Vector;

public class EnvironmentHandler extends SecureHandler {
  public static final String COMPONENT_CODE = "hEnv";
  
  public GeminiApplication application;
  
  public ComponentLog log;
  
  public EnvironmentHandler(GeminiApplication application) {
    this.application = application;
    this.log = application.getLog("hEnv");
  }
  
  public String getDescription() { return "Environment Handler"; }
  
  public boolean acceptRequest(Dispatcher dispatcher, Context context, String command) {
    if (super.acceptRequest(dispatcher, context, command))
      if (command.startsWith("environment"))
        return handleRequest(dispatcher, context, command);  
    return false;
  }
  
  public boolean handleRequest(Dispatcher dispatcher, Context context, String command) {
    if (command.equalsIgnoreCase("environment-search"))
      search(dispatcher, context, command); 
    if (command.equalsIgnoreCase("environment-sort")) {
      sort(dispatcher, context);
    } else if (command.equalsIgnoreCase("environment-editor")) {
      edit(context);
    } else if (command.equalsIgnoreCase("environment-edit-save")) {
      save(context);
    } else if (command.equalsIgnoreCase("environment-edit-save-new")) {
      saveNew(context);
    } else if (command.equalsIgnoreCase("environment-edit-delete")) {
      delete(context);
    } else if (command.equalsIgnoreCase("environment-edit-new")) {
      newForm(context);
    } 
    return true;
  }
  
  private boolean search(Dispatcher dispatcher, Context context, String command) {
    Notepad notepad = MilestoneHelper.getNotepadFromSession(21, context);
    notepad.setAllContents(null);
    notepad.setSelected(null);
    String descriptionSearch = "";
    String environmentDescriptionSearch = "";
    if (context.getParameter("FamilyDescriptionSearch") != null)
      descriptionSearch = context.getParameter("FamilyDescriptionSearch"); 
    if (context.getParameter("EnvironmentDescriptionSearch") != null)
      environmentDescriptionSearch = context.getParameter("EnvironmentDescriptionSearch"); 
    CorporateObjectSearchObj corpSearch = new CorporateObjectSearchObj();
    corpSearch.setFamilySearch(descriptionSearch);
    corpSearch.setEnvironmentSearch(environmentDescriptionSearch);
    notepad.setCorporateObjectSearchObj(corpSearch);
    EnvironmentManager.getInstance().setNotepadQuery(notepad);
    dispatcher.redispatch(context, "environment-editor");
    return true;
  }
  
  private boolean sort(Dispatcher dispatcher, Context context) {
    int sort = context.getIntRequestValue("OrderBy", 0);
    Notepad notepad = getEnvironmentNotepad(context, MilestoneSecurity.getUser(context).getUserId());
    if (notepad.getAllContents() != null) {
      Vector sortedVector = notepad.getAllContents();
      if (sort == 0) {
        sortedVector = MilestoneHelper.sortCorporateVectorByName(notepad.getAllContents());
      } else {
        sortedVector = MilestoneHelper.sortCorporateVectorByParentName(notepad.getAllContents());
      } 
      notepad.setAllContents(sortedVector);
    } 
    notepad.goToSelectedPage();
    dispatcher.redispatch(context, "environment-editor");
    return true;
  }
  
  public Notepad getEnvironmentNotepad(Context context, int userId) {
    Vector contents = new Vector();
    if (MilestoneHelper.getNotepadFromSession(21, context) != null) {
      Notepad notepad = MilestoneHelper.getNotepadFromSession(21, context);
      if (notepad.getAllContents() == null) {
        contents = EnvironmentManager.getInstance().getEnvironmentNotepadList(userId, notepad);
        notepad.setAllContents(contents);
      } 
      return notepad;
    } 
    String[] columnNames = { "Environment", "Family" };
    contents = MilestoneHelper.sortCorporateVectorByName(EnvironmentManager.getInstance().getEnvironmentNotepadList(userId, null));
    return new Notepad(contents, 0, 7, "Environment", 21, columnNames);
  }
  
  private boolean edit(Context context) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getEnvironmentNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Environment environment = MilestoneHelper.getScreenEnvironment(context);
    if (environment != null) {
      Form form = null;
      if (environment != null) {
        form = buildForm(context, environment);
      } else {
        form = buildNewForm(context);
      } 
      context.putDelivery("Form", form);
      return context.includeJSP("environment-editor.jsp");
    } 
    return goToBlank(context);
  }
  
  private Form buildForm(Context context, Environment environment) {
    Form environmentForm = new Form(this.application, "environmentForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    Family parent = environment.getParentFamily();
    boolean boolVal = MilestoneHelper.getActiveCorporateStructure(environment.getStructureID());
    environment.setActive(boolVal);
    Vector families = Cache.getFamilies();
    String[] familyArray = { "" };
    if (families.size() > 0) {
      familyArray = new String[families.size()];
      for (int j = 0; j < families.size(); j++) {
        Family family = (Family)families.get(j);
        familyArray[j] = family.getName();
      } 
    } 
    FormDropDownMenu parent1Selection = new FormDropDownMenu("Parent1Selection", 
        parent.getName(), familyArray, true);
    parent1Selection.setTabIndex(1);
    environmentForm.addElement(parent1Selection);
    FormCheckBox active = new FormCheckBox("active", "", true, environment.getActive());
    active.setTabIndex(6);
    environmentForm.addElement(active);
    String name = environment.getName();
    FormTextField corporateDescription = new FormTextField("CorporateDescription", name, true, 50, 50);
    corporateDescription.addFormEvent("onBlur", "Javascript:checkField(this);fillAbbreviation()");
    corporateDescription.setTabIndex(2);
    environmentForm.addElement(corporateDescription);
    context.putDelivery("isArchiId", String.valueOf(MilestoneHelper_2.getIsStructureArchimedes(environment.getStructureID())));
    String abbreviation = environment.getStructureAbbreviation();
    FormTextField corporateAbbreviation = new FormTextField("CorporateAbbreviation", abbreviation, true, 3, 3);
    corporateAbbreviation.addFormEvent("onBlur", "Javascript:checkField(this)");
    corporateAbbreviation.setTabIndex(3);
    environmentForm.addElement(corporateAbbreviation);
    String westEast = "West";
    if (environment.getDistribution() == 1)
      westEast = "East"; 
    FormRadioButtonGroup distribution = new FormRadioButtonGroup("Distribution", westEast, "West, East", false);
    distribution.setTabIndex(4);
    environmentForm.addElement(distribution);
    FormDropDownMenu calendarGroup = new FormDropDownMenu("CalendarGroup", Integer.toString(environment.getCalendarGroup()), "1,0", "Canada, United States", true);
    calendarGroup.setTabIndex(5);
    environmentForm.addElement(calendarGroup);
    Vector companies = environment.getCompanies();
    StringList companyString = new StringList();
    Company company = null;
    for (int j = 0; j < companies.size(); j++) {
      company = (Company)companies.get(j);
      companyString.add(company.getName());
    } 
    FormDropDownMenu children = new FormDropDownMenu("children", "", companyString.toString(), false);
    children.addFormEvent("style", "background-color:lightgrey;");
    children.addFormEvent("size", "5");
    children.setTabIndex(7);
    environmentForm.addElement(children);
    Vector prefixes = MilestoneHelper.getPrefixCodes(environment.getStructureID());
    StringList prefixString = new StringList();
    PrefixCode prefixCode = null;
    for (int y = 0; y < prefixes.size(); y++) {
      prefixCode = (PrefixCode)prefixes.get(y);
      prefixString.add(prefixCode.getAbbreviation());
    } 
    FormDropDownMenu prefix = new FormDropDownMenu("Prefix", "", prefixString.toString(), false);
    prefix.addFormEvent("style", "background-color:lightgrey;");
    prefix.addFormEvent("size", "5");
    prefix.setTabIndex(8);
    environmentForm.addElement(prefix);
    FormTextField lastUpdated = new FormTextField("lastUpdatedDate", false, 50);
    if (environment.getLastUpdateDate() != null)
      lastUpdated.setValue(MilestoneHelper.getCustomFormatedDate(environment.getLastUpdateDate(), "M/d/yyyy hh:mm:ss a 'ET'")); 
    environmentForm.addElement(lastUpdated);
    FormTextField lastUpdatedBy = new FormTextField("lastUpdatedBy", false, 50);
    if (UserManager.getInstance().getUser(environment.getLastUpdatingUser()) != null)
      lastUpdatedBy.setValue(UserManager.getInstance().getUser(environment.getLastUpdatingUser()).getName()); 
    environmentForm.addElement(lastUpdatedBy);
    FormTextField familyDescriptionSearch = new FormTextField("FamilyDescriptionSearch", "", false, 20);
    familyDescriptionSearch.setId("FamilyDescriptionSearch");
    environmentForm.addElement(familyDescriptionSearch);
    FormTextField environmentDescriptionSearch = new FormTextField("EnvironmentDescriptionSearch", "", false, 20);
    environmentDescriptionSearch.setId("EnvironmentDescriptionSearch");
    environmentForm.addElement(environmentDescriptionSearch);
    environmentForm.addElement(new FormHidden("cmd", "environment-editor"));
    environmentForm.addElement(new FormHidden("OrderBy", "", true));
    if (context.getSessionValue("NOTEPAD_ENVIRONMENT_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_ENVIRONMENT_VISIBLE")); 
    return environmentForm;
  }
  
  private boolean save(Context context) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getEnvironmentNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Environment environment = MilestoneHelper.getScreenEnvironment(context);
    Form form = buildForm(context, environment);
    Environment timestampEnvironment = (Environment)context.getSessionValue("Environment");
    if (timestampEnvironment == null || (timestampEnvironment != null && EnvironmentManager.getInstance().isTimestampValid(timestampEnvironment))) {
      form.setValues(context);
      String descriptionString = form.getStringValue("CorporateDescription");
      String parentString = form.getStringValue("Parent1Selection");
      if (!CorporateStructureManager.getInstance().isDuplicate(descriptionString, 5, environment.getStructureID())) {
        if (!parentString.equalsIgnoreCase(""))
          environment.setParentFamily((Family)MilestoneHelper.getStructureObject(MilestoneHelper.getStructureId(parentString, 1))); 
        environment.setName(descriptionString);
        String abbreviationString = form.getStringValue("CorporateAbbreviation");
        environment.setStructureAbbreviation(abbreviationString);
        environment.setActive(((FormCheckBox)form.getElement("active")).isChecked());
        String westString = form.getStringValue("Distribution");
        if (westString.equalsIgnoreCase("West")) {
          environment.setDistribution(0);
        } else {
          environment.setDistribution(1);
        } 
        try {
          environment.calendarGroup = form.getIntegerValue("CalendarGroup");
        } catch (Exception exception) {}
        if (!form.isUnchanged()) {
          FormValidation formValidation = form.validate();
          if (formValidation.isGood()) {
            Environment savedEnvironment = EnvironmentManager.getInstance().saveEnvironment(environment, user.getUserId());
            FormElement lastUpdated = form.getElement("lastupdateddate");
            if (savedEnvironment.getLastUpdateDate() != null)
              lastUpdated.setValue(MilestoneHelper.getLongDate(savedEnvironment.getLastUpdateDate())); 
            Cache.flushCorporateStructure();
            context.removeSessionValue("user-companies");
            context.removeSessionValue("user-environments");
            notepad.setAllContents(null);
            notepad = getEnvironmentNotepad(context, user.getUserId());
            notepad.setSelected(savedEnvironment);
            environment = (Environment)notepad.validateSelected();
            if (environment == null)
              return goToBlank(context); 
            form = buildForm(context, environment);
            context.putSessionValue("Environment", environment);
            context.putDelivery("Form", form);
          } else {
            context.putDelivery("FormValidation", formValidation);
          } 
        } 
        form.addElement(new FormHidden("OrderBy", "", true));
        context.putDelivery("Form", form);
        return edit(context);
      } 
      context.putSessionValue("Environment", environment);
      context.putDelivery("AlertMessage", 
          MilestoneMessage.getMessage(5, 
            
            new String[] { "Environment", descriptionString }));
      return edit(context);
    } 
    context.putDelivery("AlertMessage", "The record is unavailable for editing at this time.  Please refresh your screen and edit it at a later time.");
    return edit(context);
  }
  
  private boolean delete(Context context) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getEnvironmentNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Environment environment = MilestoneHelper.getScreenEnvironment(context);
    if (environment != null) {
      String errorMsg = "";
      errorMsg = CorporateStructureManager.getInstance().delete(environment, user.getUserId());
      if (errorMsg != null) {
        context.putDelivery("AlertMessage", errorMsg);
      } else {
        Cache.flushCorporateStructure();
        context.removeSessionValue("user-companies");
        context.removeSessionValue("user-environments");
        notepad.setAllContents(null);
        notepad.setSelected(null);
      } 
    } 
    return edit(context);
  }
  
  private boolean newForm(Context context) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getEnvironmentNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Form form = buildNewForm(context);
    context.putDelivery("Form", form);
    if (context.getSessionValue("NOTEPAD_ENVIRONMENT_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_ENVIRONMENT_VISIBLE")); 
    return context.includeJSP("environment-editor.jsp");
  }
  
  protected Form buildNewForm(Context context) {
    Form environmentForm = new Form(this.application, "environmentForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    User user = MilestoneSecurity.getUser(context);
    Vector families = Cache.getFamilies();
    FormDropDownMenu parent1Selection = MilestoneHelper.getCorporateStructureDropDown("Parent1Selection", families, "0", true, false);
    parent1Selection.setTabIndex(1);
    environmentForm.addElement(parent1Selection);
    FormCheckBox active = new FormCheckBox("active", "", true, true);
    active.setTabIndex(4);
    environmentForm.addElement(active);
    FormTextField corporateDescription = new FormTextField("CorporateDescription", "", true, 50, 50);
    corporateDescription.addFormEvent("onBlur", "Javascript:checkField(this);fillAbbreviation()");
    corporateDescription.setTabIndex(2);
    environmentForm.addElement(corporateDescription);
    FormTextField corporateAbbreviation = new FormTextField("CorporateAbbreviation", "", true, 3, 3);
    corporateAbbreviation.addFormEvent("onBlur", "Javascript:checkField(this)");
    corporateAbbreviation.setTabIndex(3);
    environmentForm.addElement(corporateAbbreviation);
    FormTextField familyDescriptionSearch = new FormTextField("FamilyDescriptionSearch", "", false, 20);
    familyDescriptionSearch.setId("FamilyDescriptionSearch");
    environmentForm.addElement(familyDescriptionSearch);
    FormTextField environmentDescriptionSearch = new FormTextField("EnvironmentDescriptionSearch", "", false, 20);
    environmentDescriptionSearch.setId("EnvironmentDescriptionSearch");
    environmentForm.addElement(environmentDescriptionSearch);
    environmentForm.addElement(new FormHidden("cmd", "environment-edit-new", true));
    environmentForm.addElement(new FormHidden("OrderBy", "", true));
    if (context.getSessionValue("NOTEPAD_ENVIRONMENT_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_ENVIRONMENT_VISIBLE")); 
    return environmentForm;
  }
  
  private boolean saveNew(Context context) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getEnvironmentNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Environment environment = new Environment();
    Form form = buildNewForm(context);
    form.setValues(context);
    String descriptionString = form.getStringValue("CorporateDescription");
    environment.setStructureID(-2);
    String parentString = form.getStringValue("Parent1Selection");
    if (!parentString.equalsIgnoreCase(""))
      environment.setParentFamily((Family)MilestoneHelper.getStructureObject(Integer.parseInt(parentString))); 
    environment.setName(descriptionString);
    environment.setStructureType(5);
    String abbreviationString = form.getStringValue("CorporateAbbreviation");
    environment.setActive(((FormCheckBox)form.getElement("active")).isChecked());
    if (!CorporateStructureManager.getInstance().isDuplicate(descriptionString, 5, environment.getStructureID())) {
      environment.setStructureAbbreviation(abbreviationString);
      if (!form.isUnchanged()) {
        FormValidation formValidation = form.validate();
        if (formValidation.isGood()) {
          Environment saveNewEnvironment = EnvironmentManager.getInstance().saveNewEnvironment(environment, user.getUserId());
          context.putSessionValue("Environment", saveNewEnvironment);
          if (notepad.getAllContents() == null || notepad.getAllContents().size() < 1)
            notepad.setCorporateObjectSearchObj(null); 
          Cache.flushCorporateStructure();
          context.removeSessionValue("user-companies");
          context.removeSessionValue("user-environments");
          notepad.setAllContents(null);
          notepad.newSelectedReset();
          notepad = getEnvironmentNotepad(context, user.getUserId());
          notepad.setSelected(saveNewEnvironment);
        } else {
          context.putDelivery("FormValidation", formValidation);
          form.addElement(new FormHidden("OrderBy", "", true));
          context.putDelivery("Form", form);
          return context.includeJSP("environment-editor.jsp");
        } 
      } 
    } else {
      context.putDelivery("AlertMessage", 
          MilestoneMessage.getMessage(5, 
            
            new String[] { "Environment", descriptionString }));
    } 
    return edit(context);
  }
  
  private boolean goToBlank(Context context) {
    context.putDelivery("isNotePadVisible", new Boolean(MilestoneHelper.getNotePadVisiblitiy(21, context)));
    Form form = new Form(this.application, "form", this.application.getInfrastructure().getServletURL(), "Post");
    form.addElement(new FormHidden("cmd", "environment-editor"));
    form.addElement(new FormHidden("OrderBy", "", true));
    FormTextField familyDescriptionSearch = new FormTextField("FamilyDescriptionSearch", "", true, 20);
    familyDescriptionSearch.setId("FamilyDescriptionSearch");
    form.addElement(familyDescriptionSearch);
    FormTextField environmentDescriptionSearch = new FormTextField("EnvironmentDescriptionSearch", "", true, 20);
    environmentDescriptionSearch.setId("EnvironmentDescriptionSearch");
    form.addElement(environmentDescriptionSearch);
    context.putDelivery("Form", form);
    return context.includeJSP("blank-environment-editor.jsp");
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\EnvironmentHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */