package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.StringList;
import com.techempower.gemini.Context;
import com.techempower.gemini.Dispatcher;
import com.techempower.gemini.FormCheckBox;
import com.techempower.gemini.FormDropDownMenu;
import com.techempower.gemini.FormElement;
import com.techempower.gemini.FormHidden;
import com.techempower.gemini.FormTextField;
import com.techempower.gemini.FormValidation;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.Cache;
import com.universal.milestone.Company;
import com.universal.milestone.CompanyHandler;
import com.universal.milestone.CompanyManager;
import com.universal.milestone.CorporateObjectSearchObj;
import com.universal.milestone.CorporateStructureManager;
import com.universal.milestone.Division;
import com.universal.milestone.Environment;
import com.universal.milestone.Family;
import com.universal.milestone.Form;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.MilestoneHelper_2;
import com.universal.milestone.MilestoneMessage;
import com.universal.milestone.MilestoneSecurity;
import com.universal.milestone.Notepad;
import com.universal.milestone.SecureHandler;
import com.universal.milestone.User;
import com.universal.milestone.UserManager;
import java.util.Vector;

public class CompanyHandler extends SecureHandler {
  public static final String COMPONENT_CODE = "hCom";
  
  public GeminiApplication application;
  
  public ComponentLog log;
  
  public CompanyHandler(GeminiApplication application) {
    this.application = application;
    this.log = application.getLog("hCom");
  }
  
  public String getDescription() { return "Company Handler"; }
  
  public boolean acceptRequest(Dispatcher dispatcher, Context context, String command) {
    if (super.acceptRequest(dispatcher, context, command))
      if (command.startsWith("company"))
        return handleRequest(dispatcher, context, command);  
    return false;
  }
  
  public boolean handleRequest(Dispatcher dispatcher, Context context, String command) {
    if (command.equalsIgnoreCase("company-search"))
      search(dispatcher, context, command); 
    if (command.equalsIgnoreCase("company-sort")) {
      sort(dispatcher, context);
    } else if (command.equalsIgnoreCase("company-editor")) {
      edit(context);
    } else if (command.equalsIgnoreCase("company-edit-save")) {
      save(context);
    } else if (command.equalsIgnoreCase("company-edit-save-new")) {
      saveNew(context);
    } else if (command.equalsIgnoreCase("company-edit-delete")) {
      delete(context);
    } else if (command.equalsIgnoreCase("company-edit-new")) {
      newForm(context);
    } 
    return true;
  }
  
  private boolean search(Dispatcher dispatcher, Context context, String command) {
    Notepad notepad = MilestoneHelper.getNotepadFromSession(10, context);
    notepad.setAllContents(null);
    notepad.setSelected(null);
    String familyDescriptionSearch = "";
    String environmentDescriptionSearch = "";
    String companyDescriptionSearch = "";
    if (context.getParameter("FamilyDescriptionSearch") != null)
      familyDescriptionSearch = context.getParameter("FamilyDescriptionSearch"); 
    if (context.getParameter("EnvironmentDescriptionSearch") != null)
      environmentDescriptionSearch = context.getParameter("EnvironmentDescriptionSearch"); 
    if (context.getParameter("CompanyDescriptionSearch") != null)
      companyDescriptionSearch = context.getParameter("CompanyDescriptionSearch"); 
    CorporateObjectSearchObj corpSearch = new CorporateObjectSearchObj();
    corpSearch.setFamilySearch(familyDescriptionSearch);
    corpSearch.setEnvironmentSearch(environmentDescriptionSearch);
    corpSearch.setCompanySearch(companyDescriptionSearch);
    notepad.setCorporateObjectSearchObj(corpSearch);
    CompanyManager.getInstance().setNotepadQuery(notepad);
    dispatcher.redispatch(context, "company-editor");
    return true;
  }
  
  private boolean sort(Dispatcher dispatcher, Context context) {
    int sort = context.getIntRequestValue("OrderBy", 0);
    Notepad notepad = getCompanyNotepad(context, MilestoneSecurity.getUser(context).getUserId());
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
    dispatcher.redispatch(context, "company-editor");
    return true;
  }
  
  public Notepad getCompanyNotepad(Context context, int userId) {
    Vector contents = new Vector();
    if (MilestoneHelper.getNotepadFromSession(10, context) != null) {
      Notepad notepad = MilestoneHelper.getNotepadFromSession(10, context);
      if (notepad.getAllContents() == null) {
        contents = CompanyManager.getInstance().getCompanyNotepadList(userId, notepad);
        notepad.setAllContents(contents);
      } 
      return notepad;
    } 
    String[] columnNames = { "Company", "Environment" };
    contents = MilestoneHelper.sortCorporateVectorByName(CompanyManager.getInstance().getCompanyNotepadList(userId, null));
    return new Notepad(contents, 0, 15, "Company", 10, columnNames);
  }
  
  private boolean edit(Context context) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getCompanyNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Company company = MilestoneHelper.getScreenCompany(context);
    if (company != null) {
      Form form = null;
      if (company != null) {
        form = buildForm(context, company);
      } else {
        form = buildNewForm(context);
      } 
      context.putDelivery("Form", form);
      return context.includeJSP("company-editor.jsp");
    } 
    return goToBlank(context);
  }
  
  private Form buildForm(Context context, Company company) {
    Form companyForm = new Form(this.application, "companyForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    Environment environment = null;
    Family family = null;
    String familyString = "";
    environment = (Environment)MilestoneHelper.getStructureObject(company.getParentEnvironment().getStructureID());
    family = (Family)MilestoneHelper.getStructureObject(environment.getParentFamily().getStructureID());
    familyString = family.getName();
    FormTextField familyTB = new FormTextField("Family", familyString, true, 50, 25);
    companyForm.addElement(familyTB);
    Environment parent = company.getParentEnvironment();
    boolean boolVal = MilestoneHelper.getActiveCorporateStructure(company.getStructureID());
    company.setActive(boolVal);
    Vector environments = Cache.getEnvironments();
    String[] environmentArray = { "" };
    if (environments.size() > 0) {
      environmentArray = new String[environments.size()];
      for (int j = 0; j < environments.size(); j++) {
        environment = (Environment)environments.get(j);
        environmentArray[j] = environment.getName();
      } 
    } 
    FormDropDownMenu parent1Selection = new FormDropDownMenu("Parent1Selection", 
        parent.getName(), environmentArray, true);
    parent1Selection.setTabIndex(1);
    companyForm.addElement(parent1Selection);
    FormCheckBox active = new FormCheckBox("active", "", true, company.getActive());
    active.setTabIndex(6);
    companyForm.addElement(active);
    String name = company.getName();
    FormTextField corporateDescription = new FormTextField("CorporateDescription", name, true, 50, 50);
    corporateDescription.addFormEvent("onBlur", "Javascript:checkField(this);fillAbbreviation()");
    corporateDescription.setTabIndex(2);
    companyForm.addElement(corporateDescription);
    context.putDelivery("isArchiId", String.valueOf(MilestoneHelper_2.getIsStructureArchimedes(company.getStructureID())));
    String abbreviation = company.getStructureAbbreviation();
    FormTextField corporateAbbreviation = new FormTextField("CorporateAbbreviation", abbreviation, true, 3, 3);
    corporateAbbreviation.addFormEvent("onBlur", "Javascript:checkField(this)");
    corporateAbbreviation.setTabIndex(3);
    companyForm.addElement(corporateAbbreviation);
    Vector divisions = company.getDivisions();
    StringList divisionString = new StringList();
    Division division = null;
    for (int j = 0; j < divisions.size(); j++) {
      division = (Division)divisions.get(j);
      divisionString.add(division.getName());
    } 
    FormDropDownMenu children = new FormDropDownMenu("children", "", divisionString.toString(), false);
    children.addFormEvent("style", "background-color:lightgrey;");
    children.addFormEvent("size", "5");
    children.setTabIndex(7);
    companyForm.addElement(children);
    FormTextField lastUpdated = new FormTextField("lastUpdatedDate", false, 50);
    if (company.getLastUpdateDate() != null)
      lastUpdated.setValue(MilestoneHelper.getCustomFormatedDate(company.getLastUpdateDate(), "M/d/yyyy hh:mm:ss a 'ET'")); 
    companyForm.addElement(lastUpdated);
    FormTextField lastUpdatedBy = new FormTextField("lastUpdatedBy", false, 50);
    if (UserManager.getInstance().getUser(company.getLastUpdatingUser()) != null)
      lastUpdatedBy.setValue(UserManager.getInstance().getUser(company.getLastUpdatingUser()).getName()); 
    companyForm.addElement(lastUpdatedBy);
    FormTextField familyDescriptionSearch = new FormTextField("FamilyDescriptionSearch", "", false, 20);
    familyDescriptionSearch.setId("FamilyDescriptionSearch");
    companyForm.addElement(familyDescriptionSearch);
    FormTextField environmentDescriptionSearch = new FormTextField("EnvironmentDescriptionSearch", "", false, 20);
    environmentDescriptionSearch.setId("EnvironmentDescriptionSearch");
    companyForm.addElement(environmentDescriptionSearch);
    FormTextField companyDescriptionSearch = new FormTextField("CompanyDescriptionSearch", "", false, 20);
    companyDescriptionSearch.setId("CompanyDescriptionSearch");
    companyForm.addElement(companyDescriptionSearch);
    companyForm.addElement(new FormHidden("cmd", "company-editor"));
    companyForm.addElement(new FormHidden("OrderBy", "", true));
    if (context.getSessionValue("NOTEPAD_COMPANY_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_COMPANY_VISIBLE")); 
    return companyForm;
  }
  
  private boolean save(Context context) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getCompanyNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Company company = MilestoneHelper.getScreenCompany(context);
    Form form = buildForm(context, company);
    Company timestampCompany = (Company)context.getSessionValue("Company");
    if (timestampCompany == null || (timestampCompany != null && CompanyManager.getInstance().isTimestampValid(timestampCompany))) {
      form.setValues(context);
      String descriptionString = form.getStringValue("CorporateDescription");
      String parentString = form.getStringValue("Parent1Selection");
      if (!CorporateStructureManager.getInstance().isDuplicate(descriptionString, 2, company.getStructureID())) {
        if (!parentString.equalsIgnoreCase(""))
          company.setParentEnvironment((Environment)MilestoneHelper.getStructureObject(MilestoneHelper.getStructureId(parentString, 5))); 
        company.setName(descriptionString);
        String abbreviationString = form.getStringValue("CorporateAbbreviation");
        company.setStructureAbbreviation(abbreviationString);
        company.setActive(((FormCheckBox)form.getElement("active")).isChecked());
        if (!form.isUnchanged()) {
          FormValidation formValidation = form.validate();
          if (formValidation.isGood()) {
            Company savedCompany = CompanyManager.getInstance().saveCompany(company, user.getUserId());
            FormElement lastUpdated = form.getElement("lastupdateddate");
            if (savedCompany.getLastUpdateDate() != null)
              lastUpdated.setValue(MilestoneHelper.getLongDate(savedCompany.getLastUpdateDate())); 
            Cache.flushCorporateStructure();
            context.removeSessionValue("user-companies");
            context.removeSessionValue("user-environments");
            notepad.setAllContents(null);
            notepad = getCompanyNotepad(context, user.getUserId());
            notepad.setSelected(savedCompany);
            company = (Company)notepad.validateSelected();
            if (company == null)
              return goToBlank(context); 
            form = buildForm(context, company);
            context.putSessionValue("Company", company);
            context.putDelivery("Form", form);
          } else {
            context.putDelivery("FormValidation", formValidation);
          } 
        } 
        form.addElement(new FormHidden("OrderBy", "", true));
        context.putDelivery("Form", form);
        return edit(context);
      } 
      context.putSessionValue("Company", company);
      context.putDelivery("AlertMessage", 
          MilestoneMessage.getMessage(5, 
            
            new String[] { "Company", descriptionString }));
      return edit(context);
    } 
    context.putDelivery("AlertMessage", "The record is unavailable for editing at this time.  Please refresh your screen and edit it at a later time.");
    return edit(context);
  }
  
  private boolean delete(Context context) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getCompanyNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Company company = MilestoneHelper.getScreenCompany(context);
    if (company != null) {
      String errorMsg = "";
      errorMsg = CorporateStructureManager.getInstance().delete(company, user.getUserId());
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
    Notepad notepad = getCompanyNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Form form = buildNewForm(context);
    context.putDelivery("Form", form);
    if (context.getSessionValue("NOTEPAD_COMPANY_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_COMPANY_VISIBLE")); 
    return context.includeJSP("company-editor.jsp");
  }
  
  protected Form buildNewForm(Context context) {
    Form companyForm = new Form(this.application, "companyForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    User user = MilestoneSecurity.getUser(context);
    FormTextField familyTB = new FormTextField("Family", "", false, 50, 25);
    companyForm.addElement(familyTB);
    Vector environments = Cache.getEnvironments();
    FormDropDownMenu parent1Selection = MilestoneHelper.getCorporateStructureDropDown("Parent1Selection", environments, "0", true, false);
    parent1Selection.setTabIndex(1);
    companyForm.addElement(parent1Selection);
    FormCheckBox active = new FormCheckBox("active", "", true, true);
    active.setTabIndex(2);
    companyForm.addElement(active);
    FormTextField corporateDescription = new FormTextField("CorporateDescription", "", true, 50, 50);
    corporateDescription.addFormEvent("onBlur", "Javascript:checkField(this);fillAbbreviation()");
    corporateDescription.setTabIndex(3);
    companyForm.addElement(corporateDescription);
    FormTextField corporateAbbreviation = new FormTextField("CorporateAbbreviation", "", true, 3, 3);
    corporateAbbreviation.addFormEvent("onBlur", "Javascript:checkField(this)");
    corporateAbbreviation.setTabIndex(4);
    companyForm.addElement(corporateAbbreviation);
    FormTextField familyDescriptionSearch = new FormTextField("FamilyDescriptionSearch", "", false, 20);
    familyDescriptionSearch.setId("FamilyDescriptionSearch");
    companyForm.addElement(familyDescriptionSearch);
    FormTextField environmentDescriptionSearch = new FormTextField("EnvironmentDescriptionSearch", "", false, 20);
    environmentDescriptionSearch.setId("EnvironmentDescriptionSearch");
    companyForm.addElement(environmentDescriptionSearch);
    FormTextField companyDescriptionSearch = new FormTextField("CompanyDescriptionSearch", "", false, 20);
    companyDescriptionSearch.setId("CompanyDescriptionSearch");
    companyForm.addElement(companyDescriptionSearch);
    companyForm.addElement(new FormHidden("cmd", "company-edit-new", true));
    companyForm.addElement(new FormHidden("OrderBy", "", true));
    if (context.getSessionValue("NOTEPAD_COMPANY_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_COMPANY_VISIBLE")); 
    return companyForm;
  }
  
  private boolean saveNew(Context context) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getCompanyNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Company company = new Company();
    Form form = buildNewForm(context);
    form.setValues(context);
    String descriptionString = form.getStringValue("CorporateDescription");
    company.setStructureID(-2);
    String parentString = form.getStringValue("Parent1Selection");
    if (!parentString.equalsIgnoreCase(""))
      company.setParentEnvironment((Environment)MilestoneHelper.getStructureObject(Integer.parseInt(parentString))); 
    company.setName(descriptionString);
    company.setStructureType(2);
    String abbreviationString = form.getStringValue("CorporateAbbreviation");
    company.setActive(((FormCheckBox)form.getElement("active")).isChecked());
    if (!CorporateStructureManager.getInstance().isDuplicate(descriptionString, 2, company.getStructureID())) {
      company.setStructureAbbreviation(abbreviationString);
      if (!form.isUnchanged()) {
        FormValidation formValidation = form.validate();
        if (formValidation.isGood()) {
          Company saveNewCompany = CompanyManager.getInstance().saveNewCompany(company, user.getUserId());
          context.putSessionValue("Company", saveNewCompany);
          if (notepad.getAllContents() == null || notepad.getAllContents().size() < 1)
            notepad.setCorporateObjectSearchObj(null); 
          Cache.flushCorporateStructure();
          context.removeSessionValue("user-companies");
          context.removeSessionValue("user-environments");
          notepad.setAllContents(null);
          notepad.newSelectedReset();
          notepad = getCompanyNotepad(context, user.getUserId());
          notepad.setSelected(saveNewCompany);
        } else {
          context.putDelivery("FormValidation", formValidation);
          form.addElement(new FormHidden("OrderBy", "", true));
          context.putDelivery("Form", form);
          return context.includeJSP("company-editor.jsp");
        } 
      } 
    } else {
      context.putDelivery("AlertMessage", 
          MilestoneMessage.getMessage(5, 
            
            new String[] { "Company", descriptionString }));
    } 
    return edit(context);
  }
  
  private boolean goToBlank(Context context) {
    context.putDelivery("isNotePadVisible", new Boolean(MilestoneHelper.getNotePadVisiblitiy(10, context)));
    Form form = new Form(this.application, "form", this.application.getInfrastructure().getServletURL(), "Post");
    form.addElement(new FormHidden("cmd", "company-editor"));
    form.addElement(new FormHidden("OrderBy", "", true));
    FormTextField familyDescriptionSearch = new FormTextField("FamilyDescriptionSearch", "", true, 20);
    familyDescriptionSearch.setId("FamilyDescriptionSearch");
    form.addElement(familyDescriptionSearch);
    FormTextField environmentDescriptionSearch = new FormTextField("EnvironmentDescriptionSearch", "", true, 20);
    environmentDescriptionSearch.setId("EnvironmentDescriptionSearch");
    form.addElement(environmentDescriptionSearch);
    FormTextField companyDescriptionSearch = new FormTextField("CompanyDescriptionSearch", "", true, 20);
    companyDescriptionSearch.setId("CompanyDescriptionSearch");
    form.addElement(companyDescriptionSearch);
    context.putDelivery("Form", form);
    return context.includeJSP("blank-company-editor.jsp");
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\CompanyHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */