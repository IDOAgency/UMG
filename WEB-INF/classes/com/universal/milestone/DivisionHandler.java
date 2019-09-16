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
import com.universal.milestone.CorporateObjectSearchObj;
import com.universal.milestone.CorporateStructureManager;
import com.universal.milestone.Division;
import com.universal.milestone.DivisionHandler;
import com.universal.milestone.DivisionManager;
import com.universal.milestone.Environment;
import com.universal.milestone.Family;
import com.universal.milestone.Form;
import com.universal.milestone.Label;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.MilestoneHelper_2;
import com.universal.milestone.MilestoneMessage;
import com.universal.milestone.MilestoneSecurity;
import com.universal.milestone.Notepad;
import com.universal.milestone.SecureHandler;
import com.universal.milestone.User;
import com.universal.milestone.UserManager;
import java.util.HashMap;
import java.util.Vector;

public class DivisionHandler extends SecureHandler {
  public static final String COMPONENT_CODE = "hDiv";
  
  public GeminiApplication application;
  
  public ComponentLog log;
  
  public DivisionHandler(GeminiApplication application) {
    this.application = application;
    this.log = application.getLog("hDiv");
  }
  
  public String getDescription() { return "Division Handler"; }
  
  public boolean acceptRequest(Dispatcher dispatcher, Context context, String command) {
    if (super.acceptRequest(dispatcher, context, command))
      if (command.startsWith("division"))
        return handleRequest(dispatcher, context, command);  
    return false;
  }
  
  public boolean handleRequest(Dispatcher dispatcher, Context context, String command) {
    if (command.equalsIgnoreCase("division-search"))
      search(dispatcher, context, command); 
    if (command.equalsIgnoreCase("division-sort")) {
      sort(dispatcher, context);
    } else if (command.equalsIgnoreCase("division-editor")) {
      edit(context);
    } else if (command.equalsIgnoreCase("division-edit-save")) {
      save(context);
    } else if (command.equalsIgnoreCase("division-edit-save-new")) {
      saveNew(context);
    } else if (command.equalsIgnoreCase("division-edit-delete")) {
      delete(context);
    } else if (command.equalsIgnoreCase("division-edit-new")) {
      newForm(context);
    } 
    return true;
  }
  
  private boolean search(Dispatcher dispatcher, Context context, String command) {
    Notepad notepad = MilestoneHelper.getNotepadFromSession(11, context);
    notepad.setAllContents(null);
    notepad.setSelected(null);
    String familyDescriptionSearch = "";
    String environmentDescriptionSearch = "";
    String companyDescriptionSearch = "";
    String divisionDescriptionSearch = "";
    if (context.getParameter("FamilyDescriptionSearch") != null)
      familyDescriptionSearch = context.getParameter("FamilyDescriptionSearch"); 
    if (context.getParameter("EnvironmentDescriptionSearch") != null)
      environmentDescriptionSearch = context.getParameter("EnvironmentDescriptionSearch"); 
    if (context.getParameter("CompanyDescriptionSearch") != null)
      companyDescriptionSearch = context.getParameter("CompanyDescriptionSearch"); 
    if (context.getParameter("DivisionDescriptionSearch") != null)
      divisionDescriptionSearch = context.getParameter("DivisionDescriptionSearch"); 
    CorporateObjectSearchObj corpSearch = new CorporateObjectSearchObj();
    corpSearch.setEnvironmentSearch(environmentDescriptionSearch);
    corpSearch.setFamilySearch(familyDescriptionSearch);
    corpSearch.setCompanySearch(companyDescriptionSearch);
    corpSearch.setDivisionSearch(divisionDescriptionSearch);
    notepad.setCorporateObjectSearchObj(corpSearch);
    DivisionManager.getInstance().setNotepadQuery(notepad);
    dispatcher.redispatch(context, "division-editor");
    return true;
  }
  
  private boolean sort(Dispatcher dispatcher, Context context) {
    int sort = Integer.parseInt(context.getParameter("OrderBy"));
    Notepad notepad = getDivisionNotepad(context, MilestoneSecurity.getUser(context).getUserId());
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
    dispatcher.redispatch(context, "division-editor");
    return true;
  }
  
  public Notepad getDivisionNotepad(Context context, int userId) {
    Vector contents = new Vector();
    if (MilestoneHelper.getNotepadFromSession(11, context) != null) {
      Notepad notepad = MilestoneHelper.getNotepadFromSession(11, context);
      if (notepad.getAllContents() == null) {
        contents = DivisionManager.getInstance().getDivisionNotepadList(userId, notepad);
        notepad.setAllContents(contents);
      } 
      return notepad;
    } 
    String[] columnNames = { "Division", "Company" };
    contents = MilestoneHelper.sortCorporateVectorByName(DivisionManager.getInstance().getDivisionNotepadList(userId, null));
    return new Notepad(contents, 0, 15, "Division", 11, columnNames);
  }
  
  private boolean edit(Context context) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getDivisionNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Division division = MilestoneHelper.getScreenDivision(context);
    if (division != null) {
      Form form = null;
      if (division != null) {
        form = buildForm(context, division);
      } else {
        context.putDelivery("CorporateArrays", getJavaScriptCorporateArray(context));
        form = buildNewForm(context);
      } 
      form.addElement(new FormHidden("OrderBy", "", true));
      context.putDelivery("Form", form);
      return context.includeJSP("division-editor.jsp");
    } 
    return goToBlank(context);
  }
  
  private Form buildForm(Context context, Division division) {
    this.log.debug("Build form.");
    Form divisionForm = new Form(this.application, "divisionForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    Environment companyEnvironment = division.getParentCompany().getParentEnvironment();
    Family parentFamily = companyEnvironment.getParentFamily();
    boolean boolVal = MilestoneHelper.getActiveCorporateStructure(division.getStructureID());
    division.setActive(boolVal);
    Vector families = Cache.getFamilies();
    FormDropDownMenu Parent2Selection = MilestoneHelper.getCorporateStructureDropDown("Parent2Selection", families, Integer.toString(parentFamily.getStructureID()), true, false);
    Parent2Selection.addFormEvent("onChange", "adjustSelection(this)");
    Parent2Selection.setTabIndex(1);
    divisionForm.addElement(Parent2Selection);
    FormCheckBox active = new FormCheckBox("active", "", true, division.getActive());
    active.setTabIndex(5);
    divisionForm.addElement(active);
    Vector companies = companyEnvironment.getCompanies();
    StringList companyString = new StringList(",");
    Company company = null;
    for (int j = 0; j < companies.size(); j++) {
      company = (Company)companies.get(j);
      companyString.add(company.getName());
    } 
    Company parent = division.getParentCompany();
    FormDropDownMenu parent1Selection = MilestoneHelper.getCorporateStructureDropDown("Parent1Selection", 
        companies, 
        Integer.toString(parent.getStructureID()), 
        true, false);
    parent1Selection.setTabIndex(2);
    parent1Selection.addFormEvent("onChange", "adjustSelection(this)");
    divisionForm.addElement(parent1Selection);
    Vector environments = parentFamily.getEnvironments();
    StringList environmentString = new StringList(",");
    Environment environment = null;
    for (int j = 0; j < environments.size(); j++) {
      environment = (Environment)environments.get(j);
      environmentString.add(environment.getName());
    } 
    Environment parentEnvironment = parent.getParentEnvironment();
    FormDropDownMenu parent3Selection = MilestoneHelper.getCorporateStructureDropDown("Parent3Selection", 
        environments, Integer.toString(parentEnvironment.getStructureID()), true, false);
    parent3Selection.setTabIndex(3);
    parent3Selection.addFormEvent("onChange", "adjustSelection(this)");
    divisionForm.addElement(parent3Selection);
    FormTextField corporateDescription = new FormTextField("CorporateDescription", division.getName(), true, 50, 50);
    corporateDescription.addFormEvent("onBlur", "Javascript:checkField(this);fillAbbreviation()");
    corporateDescription.setTabIndex(4);
    divisionForm.addElement(corporateDescription);
    context.putDelivery("isArchiId", String.valueOf(MilestoneHelper_2.getIsStructureArchimedes(division.getStructureID())));
    FormTextField corporateAbbreviation = new FormTextField("CorporateAbbreviation", division.getStructureAbbreviation(), true, 3, 3);
    corporateAbbreviation.addFormEvent("onBlur", "Javascript:checkField(this)");
    corporateAbbreviation.setTabIndex(5);
    divisionForm.addElement(corporateAbbreviation);
    Vector labels = division.getLabels();
    StringList labelString = new StringList(",");
    Label label = null;
    for (int j = 0; j < labels.size(); j++) {
      label = (Label)labels.get(j);
      labelString.add(label.getName());
    } 
    FormDropDownMenu children = new FormDropDownMenu("children", "", labelString.toString(), false);
    children.addFormEvent("style", "background-color:lightgrey;");
    children.addFormEvent("size", "5");
    children.setTabIndex(4);
    divisionForm.addElement(children);
    FormTextField lastUpdated = new FormTextField("lastUpdatedDate", false, 50);
    if (division.getLastUpdateDate() != null)
      lastUpdated.setValue(MilestoneHelper.getCustomFormatedDate(division.getLastUpdateDate(), "M/d/yyyy hh:mm:ss a 'ET'")); 
    divisionForm.addElement(lastUpdated);
    FormTextField lastUpdatedBy = new FormTextField("lastUpdatedBy", false, 50);
    if (UserManager.getInstance().getUser(division.getLastUpdatingUser()) != null)
      lastUpdatedBy.setValue(UserManager.getInstance().getUser(division.getLastUpdatingUser()).getName()); 
    divisionForm.addElement(lastUpdatedBy);
    FormTextField familyDescriptionSearch = new FormTextField("FamilyDescriptionSearch", "", false, 20);
    familyDescriptionSearch.setId("FamilyDescriptionSearch");
    divisionForm.addElement(familyDescriptionSearch);
    FormTextField environmentDescriptionSearch = new FormTextField("EnvironmentDescriptionSearch", "", false, 20);
    environmentDescriptionSearch.setId("EnvironmentDescriptionSearch");
    divisionForm.addElement(environmentDescriptionSearch);
    FormTextField companyDescriptionSearch = new FormTextField("CompanyDescriptionSearch", "", false, 20);
    companyDescriptionSearch.setId("CompanyDescriptionSearch");
    divisionForm.addElement(companyDescriptionSearch);
    FormTextField divisionDescriptionSearch = new FormTextField("DivisionDescriptionSearch", "", false, 20);
    divisionDescriptionSearch.setId("DivisionDescriptionSearch");
    divisionForm.addElement(divisionDescriptionSearch);
    divisionForm.addElement(new FormHidden("cmd", "division-editor"));
    divisionForm.addElement(new FormHidden("OrderBy", "", true));
    if (context.getSessionValue("NOTEPAD_DIVISION_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_DIVISION_VISIBLE")); 
    context.putDelivery("CorporateArraysNew", getJavaScriptCorporateArrayNew(context));
    context.putDelivery("CorporateArrays", getJavaScriptCorporateArray(context));
    return divisionForm;
  }
  
  private boolean save(Context context) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getDivisionNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Division division = MilestoneHelper.getScreenDivision(context);
    Form form = buildForm(context, division);
    Division timestampDivision = (Division)context.getSessionValue("Division");
    if (timestampDivision == null || (timestampDivision != null && DivisionManager.getInstance().isTimestampValid(timestampDivision))) {
      form.setValues(context);
      String descriptionString = form.getStringValue("CorporateDescription");
      String parentString = form.getStringValue("Parent1Selection");
      String abbreviationString = form.getStringValue("CorporateAbbreviation");
      if (!CorporateStructureManager.getInstance().isDuplicate(descriptionString, 3, division.getStructureID())) {
        division.setName(descriptionString);
        division.setStructureAbbreviation(abbreviationString);
        division.setActive(((FormCheckBox)form.getElement("active")).isChecked());
        if (!parentString.equalsIgnoreCase(""))
          division.setParentCompany((Company)MilestoneHelper.getStructureObject(Integer.parseInt(parentString))); 
        if (!form.isUnchanged()) {
          FormValidation formValidation = form.validate();
          if (formValidation.isGood()) {
            Division savedDivision = DivisionManager.getInstance().saveDivision(division, user.getUserId());
            FormElement lastUpdated = form.getElement("lastupdateddate");
            if (savedDivision.getLastUpdateDate() != null)
              lastUpdated.setValue(MilestoneHelper.getLongDate(savedDivision.getLastUpdateDate())); 
            Cache.flushCorporateStructure();
            context.removeSessionValue("user-companies");
            context.removeSessionValue("user-environments");
            notepad.setAllContents(null);
            notepad = getDivisionNotepad(context, user.getUserId());
            notepad.setSelected(savedDivision);
            division = (Division)notepad.validateSelected();
            if (division == null)
              return goToBlank(context); 
            form = buildForm(context, division);
            context.putSessionValue("Division", division);
            context.putDelivery("Form", form);
          } else {
            context.putDelivery("FormValidation", formValidation);
          } 
        } 
        form.addElement(new FormHidden("OrderBy", "", true));
        context.putDelivery("Form", form);
        return edit(context);
      } 
      context.putSessionValue("Division", division);
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
    Notepad notepad = getDivisionNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Division division = MilestoneHelper.getScreenDivision(context);
    if (division != null) {
      String errorMsg = "";
      errorMsg = CorporateStructureManager.getInstance().delete(division, user.getUserId());
      if (errorMsg != null) {
        context.putDelivery("AlertMessage", errorMsg);
      } else {
        Cache.flushCorporateStructure();
        context.removeSessionValue("user-companies");
        context.removeSessionValue("user-environments");
        notepad.setAllContents(null);
        notepad = getDivisionNotepad(context, user.getUserId());
        notepad.setSelected(null);
      } 
    } 
    return edit(context);
  }
  
  private boolean newForm(Context context) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getDivisionNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    context.putDelivery("CorporateArrays", getJavaScriptCorporateArray(context));
    Form form = buildNewForm(context);
    context.putDelivery("Form", form);
    if (context.getSessionValue("NOTEPAD_DIVISION_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_DIVISION_VISIBLE")); 
    return context.includeJSP("division-editor.jsp");
  }
  
  protected Form buildNewForm(Context context) {
    Form divisionForm = new Form(this.application, "divisionForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    User user = MilestoneSecurity.getUser(context);
    Vector families = Cache.getFamilies();
    FormDropDownMenu Parent2Selection = MilestoneHelper.getCorporateStructureDropDown("Parent2Selection", families, "0", true, false);
    Parent2Selection.addFormEvent("onChange", "adjustSelection(this)");
    Parent2Selection.setTabIndex(1);
    divisionForm.addElement(Parent2Selection);
    Vector environments = Cache.getEnvironments();
    FormDropDownMenu Parent3Selection = MilestoneHelper.getCorporateStructureDropDown("Parent3Selection", environments, "0", true, true);
    Parent3Selection.addFormEvent("onChange", "adjustSelection(this)");
    Parent3Selection.setTabIndex(2);
    divisionForm.addElement(Parent3Selection);
    FormCheckBox active = new FormCheckBox("active", "", true, true);
    active.setTabIndex(5);
    divisionForm.addElement(active);
    Vector companies = Cache.getCompanies();
    FormDropDownMenu Parent1Selection = MilestoneHelper.getCorporateStructureDropDown("Parent1Selection", companies, "0", true, true);
    Parent1Selection.addFormEvent("onChange", "adjustSelection(this)");
    Parent1Selection.setTabIndex(2);
    divisionForm.addElement(Parent1Selection);
    FormTextField corporateDescription = new FormTextField("CorporateDescription", "", true, 50, 50);
    corporateDescription.addFormEvent("onBlur", "checkField(this);fillAbbreviation()");
    corporateDescription.setTabIndex(3);
    divisionForm.addElement(corporateDescription);
    FormTextField corporateAbbreviation = new FormTextField("CorporateAbbreviation", "", true, 3, 3);
    corporateAbbreviation.addFormEvent("onBlur", "checkField(this)");
    corporateAbbreviation.setTabIndex(4);
    divisionForm.addElement(corporateAbbreviation);
    FormTextField familyDescriptionSearch = new FormTextField("FamilyDescriptionSearch", "", false, 20);
    familyDescriptionSearch.setId("FamilyDescriptionSearch");
    divisionForm.addElement(familyDescriptionSearch);
    FormTextField environmentDescriptionSearch = new FormTextField("EnvironmentDescriptionSearch", "", false, 20);
    environmentDescriptionSearch.setId("EnvironmentDescriptionSearch");
    divisionForm.addElement(environmentDescriptionSearch);
    FormTextField companyDescriptionSearch = new FormTextField("CompanyDescriptionSearch", "", false, 20);
    companyDescriptionSearch.setId("CompanyDescriptionSearch");
    divisionForm.addElement(companyDescriptionSearch);
    FormTextField divisionDescriptionSearch = new FormTextField("DivisionDescriptionSearch", "", false, 20);
    divisionDescriptionSearch.setId("DivisionDescriptionSearch");
    divisionForm.addElement(divisionDescriptionSearch);
    divisionForm.addElement(new FormHidden("cmd", "division-edit-new", true));
    divisionForm.addElement(new FormHidden("OrderBy", "", true));
    if (context.getSessionValue("NOTEPAD_DIVISION_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_DIVISION_VISIBLE")); 
    return divisionForm;
  }
  
  private boolean saveNew(Context context) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getDivisionNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Division division = new Division();
    Form form = buildNewForm(context);
    form.setValues(context);
    String parentString = form.getStringValue("Parent1Selection");
    if (!parentString.equalsIgnoreCase(""))
      division.setParentCompany((Company)MilestoneHelper.getStructureObject(Integer.parseInt(parentString))); 
    String descriptionString = form.getStringValue("CorporateDescription");
    division.setName(descriptionString);
    division.setStructureType(3);
    String abbreviationString = form.getStringValue("CorporateAbbreviation");
    division.setStructureAbbreviation(abbreviationString);
    division.setStructureID(-2);
    division.setActive(((FormCheckBox)form.getElement("active")).isChecked());
    if (!CorporateStructureManager.getInstance().isDuplicate(descriptionString, 3, division.getStructureID())) {
      if (!form.isUnchanged()) {
        FormValidation formValidation = form.validate();
        if (formValidation.isGood()) {
          Division saveNewDivision = DivisionManager.getInstance().saveNewDivision(division, user.getUserId());
          if (notepad.getAllContents() == null || notepad.getAllContents().size() < 1)
            notepad.setCorporateObjectSearchObj(null); 
          Cache.flushCorporateStructure();
          context.removeSessionValue("user-companies");
          context.removeSessionValue("user-environments");
          notepad.setAllContents(null);
          notepad.newSelectedReset();
          notepad = getDivisionNotepad(context, user.getUserId());
          notepad.setSelected(saveNewDivision);
          context.putSessionValue("Division", saveNewDivision);
        } else {
          context.putDelivery("FormValidation", formValidation);
          form.addElement(new FormHidden("OrderBy", "", true));
          context.putDelivery("Form", form);
          context.putDelivery("CorporateArrays", getJavaScriptCorporateArray(context));
          return context.includeJSP("division-editor.jsp");
        } 
      } 
    } else {
      context.putSessionValue("Division", division);
      context.putDelivery("Form", form);
      context.putDelivery("AlertMessage", 
          MilestoneMessage.getMessage(5, 
            
            new String[] { "Division", division.getName() }));
    } 
    return edit(context);
  }
  
  private String getJavaScriptCorporateArray(Context context) {
    Vector families = Cache.getFamilies();
    StringBuffer buffer = new StringBuffer("function createChildren()\n{\n  lRoot = ");
    buffer.append("new Node(0, 'Root', [\n");
    for (int i = 0; i < families.size(); i++) {
      Family family = (Family)families.elementAt(i);
      if (family != null) {
        buffer.append("new Node(");
        buffer.append(family.getStructureID());
        buffer.append(", '");
        buffer.append(MilestoneHelper.urlEncode(family.getName()));
        buffer.append("',[\n");
        Vector environments = family.getEnvironments();
        for (int e = 0; e < environments.size(); e++) {
          Environment environment = (Environment)environments.elementAt(e);
          if (environment != null) {
            buffer.append("new Node(");
            buffer.append(environment.getStructureID());
            buffer.append(", '");
            buffer.append(MilestoneHelper.urlEncode(environment.getName()));
            buffer.append("',[\n");
            Vector companies = environment.getCompanies();
            for (int j = 0; j < companies.size(); j++) {
              Company company = (Company)companies.elementAt(j);
              if (company != null) {
                buffer.append("new Node(");
                buffer.append(company.getStructureID());
                buffer.append(",'");
                buffer.append(MilestoneHelper.urlEncode(company.getName()));
                buffer.append("',[\n");
                if (j == companies.size() - 1) {
                  buffer.append("])");
                } else {
                  buffer.append("]),");
                } 
              } 
            } 
            if (e == environments.size() - 1) {
              buffer.append("])");
            } else {
              buffer.append("]),");
            } 
          } 
        } 
        if (i == families.size() - 1) {
          buffer.append("])");
        } else {
          buffer.append("]),");
        } 
      } 
    } 
    buffer.append("]);\n");
    Vector allEnvironments = Cache.getEnvironments();
    buffer.append("\n  eRoot = ");
    buffer.append("new Node(0, 'Root', [\n");
    for (int x = 0; x < allEnvironments.size(); x++) {
      Environment thisEnvironment = (Environment)allEnvironments.elementAt(x);
      if (thisEnvironment != null) {
        buffer.append("new Node(");
        buffer.append(thisEnvironment.getStructureID());
        buffer.append(", '");
        buffer.append(MilestoneHelper.urlEncode(thisEnvironment.getName()));
        buffer.append("',[\n");
        Vector allCompanies = thisEnvironment.getCompanies();
        for (int z = 0; z < allCompanies.size(); z++) {
          Company thisCompany = (Company)allCompanies.elementAt(z);
          if (thisCompany != null) {
            buffer.append("new Node(");
            buffer.append(thisCompany.getStructureID());
            buffer.append(",'");
            buffer.append(MilestoneHelper.urlEncode(thisCompany.getName()));
            buffer.append("',[\n");
            if (z == allCompanies.size() - 1) {
              buffer.append("])");
            } else {
              buffer.append("]),");
            } 
          } 
        } 
        if (x == allEnvironments.size() - 1) {
          buffer.append("])");
        } else {
          buffer.append("]),");
        } 
      } 
    } 
    buffer.append("]);\n");
    buffer.append("\n }//end function createChildren");
    return buffer.toString();
  }
  
  public String getJavaScriptCorporateArrayNew(Context context) {
    StringBuffer result = new StringBuffer(100);
    String str = "";
    String value = new String();
    User user = (User)context.getSessionValue("user");
    Vector vUserCompanies = (Vector)MilestoneHelper.getUserCompanies(context).clone();
    Vector vUserEnvironments = Cache.getEnvironments();
    result.append("\n");
    result.append("var a = new Array();\n");
    result.append("var b = new Array();\n");
    result.append("var c = new Array();\n");
    int arrayIndex = 0;
    result.append("a[0] = new Array( 0, '-- [nothing selected] --');\n");
    HashMap corpHashMap = MilestoneHelper.buildActiveCorporateStructureHashMap();
    for (int i = 0; i < vUserEnvironments.size(); i++) {
      Environment ue = (Environment)vUserEnvironments.elementAt(i);
      if (ue != null) {
        result.append("a[");
        result.append(ue.getStructureID());
        result.append("] = new Array(");
        boolean foundFirst = false;
        Vector tmpArray = new Vector();
        Vector companies = Cache.getInstance().getCompanies();
        for (int j = 0; j < companies.size(); j++) {
          Company node = (Company)companies.elementAt(j);
          if (node.getParentID() == ue.getStructureID() && !corpHashMap.containsKey(new Integer(node.getStructureID()))) {
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
        Vector tmpDivisionArray = new Vector();
        for (int j = 0; j < tmpArray.size(); j++) {
          Company node1 = (Company)tmpArray.elementAt(j);
          result.append("b[");
          result.append(node1.getStructureID());
          result.append("] = new Array(");
          Vector divisions = Cache.getInstance().getDivisions();
          boolean foundSecond = false;
          for (int k = 0; k < divisions.size(); k++) {
            Division node2 = (Division)divisions.elementAt(k);
            if (node2.getParentID() == node1.getStructureID() && !corpHashMap.containsKey(new Integer(node2.getStructureID()))) {
              if (foundSecond)
                result.append(','); 
              result.append(' ');
              result.append(node2.getStructureID());
              result.append(", '");
              result.append(MilestoneHelper.urlEncode(node2.getName()));
              result.append('\'');
              foundSecond = true;
              tmpDivisionArray.add(node2);
            } 
          } 
          if (foundSecond) {
            result.append(");\n");
          } else {
            result.append(" 0, '[none available]');\n");
          } 
        } 
        for (int j = 0; j < tmpDivisionArray.size(); j++) {
          Division node1 = (Division)tmpDivisionArray.elementAt(j);
          result.append("c[");
          result.append(node1.getStructureID());
          result.append("] = new Array(");
          Vector labels = Cache.getInstance().getLabels();
          boolean foundSecond = false;
          for (int k = 0; k < labels.size(); k++) {
            Label node2 = (Label)labels.elementAt(k);
            if (node2.getParentID() == node1.getStructureID() && !corpHashMap.containsKey(new Integer(node2.getStructureID()))) {
              if (foundSecond)
                result.append(','); 
              result.append(' ');
              result.append(node2.getStructureID());
              result.append(", '");
              result.append(MilestoneHelper.urlEncode(node2.getName()));
              result.append('\'');
              foundSecond = true;
            } 
          } 
          if (foundSecond) {
            result.append(");\n");
          } else {
            result.append(" 0, '[none available]');\n");
          } 
        } 
      } 
    } 
    corpHashMap = null;
    return result.toString();
  }
  
  private boolean goToBlank(Context context) {
    context.putDelivery("isNotePadVisible", new Boolean(MilestoneHelper.getNotePadVisiblitiy(11, context)));
    Form form = new Form(this.application, "form", this.application.getInfrastructure().getServletURL(), "Post");
    form.addElement(new FormHidden("cmd", "division-editor"));
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
    FormTextField divisionDescriptionSearch = new FormTextField("DivisionDescriptionSearch", "", true, 20);
    divisionDescriptionSearch.setId("DivisionDescriptionSearch");
    form.addElement(divisionDescriptionSearch);
    context.putDelivery("Form", form);
    return context.includeJSP("blank-division-editor.jsp");
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\DivisionHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */