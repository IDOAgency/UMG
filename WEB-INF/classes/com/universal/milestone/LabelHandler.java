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
import com.universal.milestone.CorporateObjectSearchObj;
import com.universal.milestone.CorporateStructureManager;
import com.universal.milestone.Division;
import com.universal.milestone.Environment;
import com.universal.milestone.Family;
import com.universal.milestone.Form;
import com.universal.milestone.Label;
import com.universal.milestone.LabelHandler;
import com.universal.milestone.LabelManager;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.MilestoneHelper_2;
import com.universal.milestone.MilestoneMessage;
import com.universal.milestone.MilestoneSecurity;
import com.universal.milestone.Notepad;
import com.universal.milestone.SecureHandler;
import com.universal.milestone.User;
import com.universal.milestone.UserManager;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class LabelHandler extends SecureHandler {
  public static final String COMPONENT_CODE = "hLab";
  
  public GeminiApplication application;
  
  public ComponentLog log;
  
  public LabelHandler(GeminiApplication application) {
    this.application = application;
    this.log = application.getLog("hLab");
  }
  
  public String getDescription() { return "Label Handler"; }
  
  public boolean acceptRequest(Dispatcher dispatcher, Context context, String command) {
    if (super.acceptRequest(dispatcher, context, command))
      if (command.startsWith("label"))
        return handleRequest(dispatcher, context, command);  
    return false;
  }
  
  public boolean handleRequest(Dispatcher dispatcher, Context context, String command) {
    if (command.equalsIgnoreCase("label-search"))
      search(dispatcher, context, command); 
    if (command.equalsIgnoreCase("label-sort")) {
      sort(dispatcher, context);
    } else if (command.equalsIgnoreCase("label-editor")) {
      edit(context);
    } else if (command.equalsIgnoreCase("label-edit-save")) {
      save(context);
    } else if (command.equalsIgnoreCase("label-edit-save-new")) {
      saveNew(context);
    } else if (command.equalsIgnoreCase("label-edit-delete")) {
      delete(context);
    } else if (command.equalsIgnoreCase("label-edit-new")) {
      newForm(context);
    } 
    return true;
  }
  
  private boolean search(Dispatcher dispatcher, Context context, String command) {
    Notepad notepad = MilestoneHelper.getNotepadFromSession(12, context);
    notepad.setAllContents(null);
    notepad.setSelected(null);
    String environmentDescriptionSearch = "";
    String familyDescriptionSearch = "";
    String companyDescriptionSearch = "";
    String divisionDescriptionSearch = "";
    String labelDescriptionSearch = "";
    String entityDescriptionSearch = "";
    if (context.getParameter("EnvironmentDescriptionSearch") != null)
      environmentDescriptionSearch = context.getParameter("EnvironmentDescriptionSearch"); 
    if (context.getParameter("FamilyDescriptionSearch") != null)
      familyDescriptionSearch = context.getParameter("FamilyDescriptionSearch"); 
    if (context.getParameter("CompanyDescriptionSearch") != null)
      companyDescriptionSearch = context.getParameter("CompanyDescriptionSearch"); 
    if (context.getParameter("DivisionDescriptionSearch") != null)
      divisionDescriptionSearch = context.getParameter("DivisionDescriptionSearch"); 
    if (context.getParameter("LabelDescriptionSearch") != null)
      labelDescriptionSearch = context.getParameter("LabelDescriptionSearch"); 
    if (context.getParameter("EntityDescriptionSearch") != null)
      entityDescriptionSearch = context.getParameter("EntityDescriptionSearch"); 
    CorporateObjectSearchObj corpSearch = new CorporateObjectSearchObj();
    corpSearch.setEnvironmentSearch(environmentDescriptionSearch);
    corpSearch.setFamilySearch(familyDescriptionSearch);
    corpSearch.setCompanySearch(companyDescriptionSearch);
    corpSearch.setDivisionSearch(divisionDescriptionSearch);
    corpSearch.setLabelSearch(labelDescriptionSearch);
    corpSearch.setEntitySearch(entityDescriptionSearch);
    notepad.setCorporateObjectSearchObj(corpSearch);
    LabelManager.getInstance().setNotepadQuery(notepad);
    dispatcher.redispatch(context, "label-editor");
    return true;
  }
  
  private boolean sort(Dispatcher dispatcher, Context context) {
    int sort = Integer.parseInt(context.getParameter("OrderBy"));
    Notepad notepad = getLabelNotepad(context, MilestoneSecurity.getUser(context).getUserId());
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
    dispatcher.redispatch(context, "label-editor");
    return true;
  }
  
  public Notepad getLabelNotepad(Context context, int userId) {
    Vector contents = new Vector();
    if (MilestoneHelper.getNotepadFromSession(12, context) != null) {
      Notepad notepad = MilestoneHelper.getNotepadFromSession(12, context);
      if (notepad.getAllContents() == null) {
        contents = LabelManager.getInstance().getLabelNotepadList(userId, notepad);
        notepad.setAllContents(contents);
      } 
      return notepad;
    } 
    String[] columnNames = { "Label", "Division" };
    contents = MilestoneHelper.sortCorporateVectorByName(LabelManager.getInstance().getLabelNotepadList(userId, null));
    return new Notepad(contents, 0, 15, "Label", 12, columnNames);
  }
  
  private boolean edit(Context context) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getLabelNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Label label = MilestoneHelper.getScreenLabel(context);
    if (label != null) {
      Form form = null;
      if (label != null) {
        form = buildForm(context, label);
      } else {
        form = buildNewForm(context);
      } 
      form.addElement(new FormHidden("OrderBy", "", true));
      context.putDelivery("CorporateArrays", getJavaScriptCorporateArray(context));
      context.putDelivery("Form", form);
      return context.includeJSP("label-editor.jsp");
    } 
    return goToBlank(context);
  }
  
  private Form buildForm(Context context, Label label) {
    String selectedDistCo;
    this.log.debug("Building form.");
    Form labelForm = new Form(this.application, "labelForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    Division division = label.getParentDivision();
    Company company = null;
    Family family = null;
    Environment environment = null;
    String environmentString = "";
    String familyString = "";
    String companyString = "";
    String divisionString = "";
    boolean boolVal = MilestoneHelper.getActiveCorporateStructure(label.getStructureID());
    label.setActive(boolVal);
    if (division != null) {
      company = (Company)MilestoneHelper.getStructureObject(division.getParentCompany().getStructureID());
      environment = (Environment)MilestoneHelper.getStructureObject(company.getParentEnvironment().getStructureID());
      environmentString = environment.getName();
      family = (Family)MilestoneHelper.getStructureObject(environment.getParentFamily().getStructureID());
      familyString = family.getName();
      companyString = company.getName();
      divisionString = Integer.toString(division.getStructureID());
    } 
    Vector environments = family.getEnvironments();
    FormDropDownMenu parent4Selection = MilestoneHelper.getCorporateStructureDropDown("Parent4Selection", environments, Integer.toString(environment.getStructureID()), true, false);
    parent4Selection.setTabIndex(3);
    parent4Selection.addFormEvent("onChange", "adjustSelection(this)");
    labelForm.addElement(parent4Selection);
    Vector families = Cache.getFamilies();
    FormDropDownMenu Parent3Selection = MilestoneHelper.getCorporateStructureDropDown("Parent3Selection", families, Integer.toString(family.getStructureID()), true, false);
    Parent3Selection.addFormEvent("onChange", "adjustSelection(this)");
    Parent3Selection.setTabIndex(1);
    labelForm.addElement(Parent3Selection);
    FormCheckBox active = new FormCheckBox("active", "", true, label.getActive());
    active.setTabIndex(2);
    labelForm.addElement(active);
    Vector companies = environment.getCompanies();
    FormDropDownMenu parent2Selection = MilestoneHelper.getCorporateStructureDropDown("Parent2Selection", 
        companies, 
        Integer.toString(company.getStructureID()), 
        true, false);
    parent2Selection.setTabIndex(4);
    parent2Selection.addFormEvent("onChange", "adjustSelection(this)");
    labelForm.addElement(parent2Selection);
    Vector divisions = company.getDivisions();
    FormDropDownMenu Parent1Selection = MilestoneHelper.getCorporateStructureDropDown("Parent1Selection", divisions, divisionString, true, false);
    Parent1Selection.setTabIndex(4);
    labelForm.addElement(Parent1Selection);
    String westEast = "West";
    if (label.getDistribution() == 1) {
      westEast = "East";
    } else if (label.getDistribution() == 2) {
      westEast = "Unassigned";
    } 
    FormRadioButtonGroup distribution = new FormRadioButtonGroup("Distribution", westEast, "West, East, Unassigned", false);
    distribution.setTabIndex(4);
    labelForm.addElement(distribution);
    int labelDistCo = label.getDistCoId();
    if (labelDistCo == -1) {
      selectedDistCo = "1";
    } else {
      selectedDistCo = String.valueOf(labelDistCo);
    } 
    Hashtable distCoHash = MilestoneHelper_2.getDistCoNames();
    String[] arrayIds = new String[distCoHash.size()];
    String[] arrayText = new String[distCoHash.size()];
    Enumeration distCoKeys = distCoHash.keys();
    int counter = 0;
    while (distCoKeys.hasMoreElements()) {
      Integer currentKey = (Integer)distCoKeys.nextElement();
      arrayIds[counter] = currentKey.toString();
      arrayText[counter] = (String)distCoHash.get(currentKey);
      counter++;
    } 
    FormDropDownMenu distCo = new FormDropDownMenu("DistributionCompany", selectedDistCo, arrayIds, arrayText, true);
    labelForm.addElement(distCo);
    FormTextField corporateDescription = new FormTextField("CorporateDescription", label.getName(), true, 50, 50);
    corporateDescription.addFormEvent("onBlur", "Javascript:checkField(this);fillAbbreviation()");
    corporateDescription.setTabIndex(2);
    labelForm.addElement(corporateDescription);
    FormTextField corporateAbbreviation = new FormTextField("CorporateAbbreviation", label.getStructureAbbreviation(), true, 3, 3);
    corporateAbbreviation.addFormEvent("onBlur", "Javascript:checkField(this)");
    corporateAbbreviation.setTabIndex(3);
    labelForm.addElement(corporateAbbreviation);
    String archimedesIdStr = Integer.toString(label.getArchimedesId());
    FormTextField archimedesId = new FormTextField("archimedesId", archimedesIdStr, false, 10);
    labelForm.addElement(archimedesId);
    FormTextField entityName = new FormTextField("entityName", label.getEntityName(), false, 50);
    labelForm.addElement(entityName);
    FormTextField legacyOpCo = new FormTextField("legacyOpCo", label.getLegacyOpCo(), false, 50);
    labelForm.addElement(legacyOpCo);
    FormTextField legacyOpUnit = new FormTextField("legacyOpUnit", label.getLegacyOpUnit(), false, 50);
    labelForm.addElement(legacyOpUnit);
    FormTextField legacySuperLabel = new FormTextField("legacySuperLabel", label.getLegacySuperLabel(), false, 50);
    labelForm.addElement(legacySuperLabel);
    FormTextField legacySubLabel = new FormTextField("legacySubLabel", label.getLegacySubLabel(), false, 50);
    labelForm.addElement(legacySubLabel);
    FormTextField levelOfActivity = new FormTextField("levelOfActivity", label.getLevelOfActivity(), false, 50);
    labelForm.addElement(levelOfActivity);
    FormTextField productionGroupCode = new FormTextField("productionGroupCode", label.getProductionGroupCode(), false, 50);
    labelForm.addElement(productionGroupCode);
    FormTextField entityType = new FormTextField("entityType", label.getEntityType(), false, 50);
    labelForm.addElement(entityType);
    FormCheckBox apngInd = new FormCheckBox("APNGInd", "", false, label.getAPGNInd());
    apngInd.setEnabled(false);
    apngInd.setStartingChecked(label.getAPGNInd());
    labelForm.addElement(apngInd);
    FormTextField distCoName = new FormTextField("DistCoName", label.getDistCoName(), false, 100);
    distCo.setId("");
    labelForm.addElement(distCoName);
    FormTextField lastUpdated = new FormTextField("lastUpdatedDate", false, 50);
    if (label.getLastUpdateDate() != null)
      lastUpdated.setValue(MilestoneHelper.getCustomFormatedDate(label.getLastUpdateDate(), "M/d/yyyy hh:mm:ss a 'ET'")); 
    labelForm.addElement(lastUpdated);
    FormTextField lastUpdatedBy = new FormTextField("lastUpdatedBy", false, 50);
    if (UserManager.getInstance().getUser(label.getLastUpdatingUser()) != null)
      lastUpdatedBy.setValue(UserManager.getInstance().getUser(label.getLastUpdatingUser()).getName()); 
    labelForm.addElement(lastUpdatedBy);
    FormTextField familyDescriptionSearch = new FormTextField("FamilyDescriptionSearch", "", false, 20);
    familyDescriptionSearch.setId("FamilyDescriptionSearch");
    labelForm.addElement(familyDescriptionSearch);
    FormTextField environmentDescriptionSearch = new FormTextField("EnvironmentDescriptionSearch", "", false, 20);
    environmentDescriptionSearch.setId("EnvironmentDescriptionSearch");
    labelForm.addElement(environmentDescriptionSearch);
    FormTextField companyDescriptionSearch = new FormTextField("CompanyDescriptionSearch", "", false, 20);
    companyDescriptionSearch.setId("CompanyDescriptionSearch");
    labelForm.addElement(companyDescriptionSearch);
    FormTextField divisionDescriptionSearch = new FormTextField("DivisionDescriptionSearch", "", false, 20);
    divisionDescriptionSearch.setId("DivisionDescriptionSearch");
    labelForm.addElement(divisionDescriptionSearch);
    FormTextField labelDescriptionSearch = new FormTextField("LabelDescriptionSearch", "", false, 20);
    labelDescriptionSearch.setId("LabelDescriptionSearch");
    labelForm.addElement(labelDescriptionSearch);
    FormTextField entityDescriptionSearch = new FormTextField("EntityDescriptionSearch", "", false, 20);
    entityDescriptionSearch.setId("EntityDescriptionSearch");
    labelForm.addElement(entityDescriptionSearch);
    labelForm.addElement(new FormHidden("cmd", "label-editor"));
    labelForm.addElement(new FormHidden("OrderBy", "", true));
    if (context.getSessionValue("NOTEPAD_LABEL_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_LABEL_VISIBLE")); 
    return labelForm;
  }
  
  private boolean save(Context context) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getLabelNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Label label = MilestoneHelper.getScreenLabel(context);
    Form form = buildForm(context, label);
    Label timestampLabel = (Label)context.getSessionValue("Label");
    if (timestampLabel == null || (timestampLabel != null && LabelManager.getInstance().isTimestampValid(timestampLabel))) {
      form.setValues(context);
      String descriptionString = form.getStringValue("CorporateDescription");
      String parentStr = form.getStringValue("Parent1Selection");
      int pId = Integer.parseInt(parentStr);
      if (!LabelManager.getInstance().isDuplicate(descriptionString, 4, label.getStructureID(), 
          pId)) {
        String parentString = form.getStringValue("Parent1Selection");
        int parentId = -1;
        parentId = Integer.parseInt(parentString);
        if (parentId > 0)
          label.setParentDivision((Division)MilestoneHelper.getStructureObject(parentId)); 
        label.setName(descriptionString);
        label.setActive(((FormCheckBox)form.getElement("active")).isChecked());
        String westString = form.getStringValue("Distribution");
        if (westString.equalsIgnoreCase("West")) {
          label.setDistribution(0);
        } else if (westString.equalsIgnoreCase("East")) {
          label.setDistribution(1);
        } else {
          label.setDistribution(2);
        } 
        String disCoString = form.getStringValue("DistributionCompany");
        label.setDistCoId(Integer.parseInt(disCoString));
        label.setDistCoName(getDistCoName((FormDropDownMenu)form.getElement("DistributionCompany")));
        String abbString = form.getStringValue("CorporateAbbreviation");
        label.setStructureAbbreviation(abbString);
        if (!form.isUnchanged()) {
          FormValidation formValidation = form.validate();
          if (formValidation.isGood()) {
            Label savedLabel = LabelManager.getInstance().saveLabel(label, user.getUserId());
            FormElement lastUpdated = form.getElement("lastupdateddate");
            if (savedLabel.getLastUpdateDate() != null)
              lastUpdated.setValue(MilestoneHelper.getLongDate(savedLabel.getLastUpdateDate())); 
            Cache.flushCorporateStructure();
            context.removeSessionValue("user-companies");
            context.removeSessionValue("user-environments");
            notepad.setAllContents(null);
            notepad = getLabelNotepad(context, user.getUserId());
            notepad.setSelected(savedLabel);
            label = (Label)notepad.validateSelected();
            if (label == null)
              return goToBlank(context); 
            form = buildForm(context, label);
            context.putDelivery("Form", form);
            context.putSessionValue("Label", label);
          } else {
            context.putDelivery("FormValidation", formValidation);
          } 
        } 
        form.addElement(new FormHidden("OrderBy", "", true));
        context.putDelivery("Form", form);
        return edit(context);
      } 
      context.putSessionValue("Label", label);
      context.putDelivery("AlertMessage", 
          MilestoneMessage.getMessage(5, 
            
            new String[] { "Label", descriptionString }));
      return edit(context);
    } 
    context.putDelivery("AlertMessage", "The record is unavailable for editing at this time.  Please refresh your screen and edit it at a later time.");
    return edit(context);
  }
  
  private boolean delete(Context context) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getLabelNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Label label = MilestoneHelper.getScreenLabel(context);
    if (label != null) {
      String errorMsg = "";
      errorMsg = CorporateStructureManager.getInstance().delete(label, user.getUserId());
      if (errorMsg != null) {
        context.putDelivery("AlertMessage", errorMsg);
      } else {
        context.removeSessionValue("Label");
        Cache.flushCorporateStructure();
        context.removeSessionValue("user-companies");
        context.removeSessionValue("user-environments");
        notepad.setAllContents(null);
        notepad = getLabelNotepad(context, user.getUserId());
        notepad.setSelected(null);
      } 
    } 
    return edit(context);
  }
  
  private boolean newForm(Context context) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getLabelNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    context.putDelivery("CorporateArrays", getJavaScriptCorporateArray(context));
    Form form = buildNewForm(context);
    context.putDelivery("Form", form);
    if (context.getSessionValue("NOTEPAD_LABEL_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_LABEL_VISIBLE")); 
    return context.includeJSP("label-editor.jsp");
  }
  
  protected Form buildNewForm(Context context) {
    Form labelForm = new Form(this.application, "labelForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    User user = MilestoneSecurity.getUser(context);
    Vector families = Cache.getFamilies();
    FormDropDownMenu family = MilestoneHelper.getCorporateStructureDropDown("Parent3Selection", families, "0", true, false);
    family.addFormEvent("onChange", "adjustSelection(this)");
    family.setTabIndex(1);
    labelForm.addElement(family);
    Vector environments = Cache.getEnvironments();
    FormDropDownMenu environment = MilestoneHelper.getCorporateStructureDropDown("Parent4Selection", environments, "0", true, true);
    environment.addFormEvent("onChange", "adjustSelection(this)");
    environment.setTabIndex(1);
    labelForm.addElement(environment);
    FormCheckBox active = new FormCheckBox("active", "", true, true);
    active.setTabIndex(6);
    labelForm.addElement(active);
    Vector companies = Cache.getCompanies();
    FormDropDownMenu company = MilestoneHelper.getCorporateStructureDropDown("Parent2Selection", companies, "0", true, true);
    company.addFormEvent("onChange", "adjustSelection(this)");
    company.setTabIndex(2);
    labelForm.addElement(company);
    Vector divisions = Cache.getDivisions();
    FormDropDownMenu division = MilestoneHelper.getCorporateStructureDropDown("Parent1Selection", divisions, "0", true, true);
    division.addFormEvent("onChange", "adjustSelection(this)");
    division.setTabIndex(3);
    labelForm.addElement(division);
    String westEast = "West";
    FormRadioButtonGroup distribution = new FormRadioButtonGroup("Distribution", westEast, "West, East, Unassigned", false);
    distribution.setTabIndex(4);
    labelForm.addElement(distribution);
    Hashtable distCoHash = MilestoneHelper_2.getDistCoNames();
    String[] arrayIds = new String[distCoHash.size()];
    String[] arrayText = new String[distCoHash.size()];
    Enumeration distCoKeys = distCoHash.keys();
    int counter = 0;
    while (distCoKeys.hasMoreElements()) {
      Integer currentKey = (Integer)distCoKeys.nextElement();
      arrayIds[counter] = currentKey.toString();
      arrayText[counter] = (String)distCoHash.get(currentKey);
      counter++;
    } 
    FormDropDownMenu distCo = new FormDropDownMenu("DistributionCompany", "1", arrayIds, arrayText, true);
    labelForm.addElement(distCo);
    FormTextField corporateDescription = new FormTextField("CorporateDescription", "", true, 50, 50);
    corporateDescription.addFormEvent("onBlur", "Javascript:checkField(this);fillAbbreviation()");
    corporateDescription.setTabIndex(4);
    labelForm.addElement(corporateDescription);
    FormTextField corporateAbbreviation = new FormTextField("CorporateAbbreviation", "", true, 3, 3);
    corporateAbbreviation.addFormEvent("onBlur", "checkField(this)");
    corporateAbbreviation.setTabIndex(5);
    labelForm.addElement(corporateAbbreviation);
    FormTextField familyDescriptionSearch = new FormTextField("FamilyDescriptionSearch", "", false, 20);
    familyDescriptionSearch.setId("FamilyDescriptionSearch");
    labelForm.addElement(familyDescriptionSearch);
    FormTextField environmentDescriptionSearch = new FormTextField("EnvironmentDescriptionSearch", "", false, 20);
    environmentDescriptionSearch.setId("EnvironmentDescriptionSearch");
    labelForm.addElement(environmentDescriptionSearch);
    FormTextField companyDescriptionSearch = new FormTextField("CompanyDescriptionSearch", "", false, 20);
    companyDescriptionSearch.setId("CompanyDescriptionSearch");
    labelForm.addElement(companyDescriptionSearch);
    FormTextField divisionDescriptionSearch = new FormTextField("DivisionDescriptionSearch", "", false, 20);
    divisionDescriptionSearch.setId("DivisionDescriptionSearch");
    labelForm.addElement(divisionDescriptionSearch);
    FormTextField labelDescriptionSearch = new FormTextField("LabelDescriptionSearch", "", false, 20);
    labelDescriptionSearch.setId("LabelDescriptionSearch");
    labelForm.addElement(labelDescriptionSearch);
    FormTextField entityDescriptionSearch = new FormTextField("EntityDescriptionSearch", "", false, 20);
    entityDescriptionSearch.setId("EntityDescriptionSearch");
    labelForm.addElement(entityDescriptionSearch);
    labelForm.addElement(new FormHidden("cmd", "label-edit-new", true));
    labelForm.addElement(new FormHidden("OrderBy", "", true));
    if (context.getSessionValue("NOTEPAD_LABEL_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_LABEL_VISIBLE")); 
    return labelForm;
  }
  
  private boolean saveNew(Context context) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getLabelNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Label label = new Label();
    Form form = buildNewForm(context);
    form.setValues(context);
    String descriptionString = form.getStringValue("CorporateDescription");
    String parentString = form.getStringValue("Parent1Selection");
    int parentID = -1;
    try {
      parentID = Integer.parseInt(parentString);
    } catch (NumberFormatException numberFormatException) {}
    if (!parentString.equalsIgnoreCase("") && parentID > 0)
      label.setParentDivision((Division)MilestoneHelper.getStructureObject(parentID)); 
    label.setName(descriptionString);
    String westString = form.getStringValue("Distribution");
    if (westString.equalsIgnoreCase("West")) {
      label.setDistribution(0);
    } else {
      label.setDistribution(1);
    } 
    String disCoString = form.getStringValue("DistributionCompany");
    label.setDistCoId(Integer.parseInt(disCoString));
    label.setDistCoName(getDistCoName((FormDropDownMenu)form.getElement("DistributionCompany")));
    String abbString = form.getStringValue("CorporateAbbreviation");
    label.setStructureAbbreviation(abbString);
    label.setStructureID(-2);
    label.setActive(((FormCheckBox)form.getElement("active")).isChecked());
    int pId = label.getParentDivision().getStructureID();
    if (!LabelManager.getInstance().isDuplicate(descriptionString, 4, label.getStructureID(), 
        pId)) {
      if (!form.isUnchanged()) {
        FormValidation formValidation = form.validate();
        if (formValidation.isGood()) {
          Label savedNewLabel = LabelManager.getInstance().saveNewLabel(label, user.getUserId());
          if (notepad.getAllContents() == null || notepad.getAllContents().size() < 1)
            notepad.setCorporateObjectSearchObj(null); 
          Cache.flushCorporateStructure();
          context.removeSessionValue("user-companies");
          context.removeSessionValue("user-environments");
          notepad.setAllContents(null);
          notepad.newSelectedReset();
          notepad = getLabelNotepad(context, user.getUserId());
          notepad.setSelected(savedNewLabel);
          context.putSessionValue("Label", savedNewLabel);
        } else {
          context.putDelivery("FormValidation", formValidation);
          form.addElement(new FormHidden("OrderBy", "", true));
          context.putDelivery("Form", form);
          context.putDelivery("CorporateArrays", getJavaScriptCorporateArray(context));
          return context.includeJSP("label-editor.jsp");
        } 
      } 
    } else {
      context.putDelivery("AlertMessage", 
          MilestoneMessage.getMessage(5, 
            
            new String[] { "Environment", descriptionString }));
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
                Vector divisions = company.getDivisions();
                for (int y = 0; y < divisions.size(); y++) {
                  Division division = (Division)divisions.elementAt(y);
                  if (division != null) {
                    buffer.append("new Node(");
                    buffer.append(division.getStructureID());
                    buffer.append(",'");
                    buffer.append(MilestoneHelper.urlEncode(division.getName()));
                    buffer.append("',[\n");
                    if (y == divisions.size() - 1) {
                      buffer.append("])");
                    } else {
                      buffer.append("]),");
                    } 
                  } 
                } 
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
    buffer.append("\n }//end function createChildren");
    return buffer.toString();
  }
  
  private boolean goToBlank(Context context) {
    context.putDelivery("isNotePadVisible", new Boolean(MilestoneHelper.getNotePadVisiblitiy(12, context)));
    Form form = new Form(this.application, "form", this.application.getInfrastructure().getServletURL(), "Post");
    form.addElement(new FormHidden("cmd", "label-edit-new"));
    form.addElement(new FormHidden("OrderBy", "", true));
    FormTextField environmentDescriptionSearch = new FormTextField("EnvironmentDescriptionSearch", "", true, 20);
    environmentDescriptionSearch.setId("EnvironmentDescriptionSearch");
    form.addElement(environmentDescriptionSearch);
    FormTextField familyDescriptionSearch = new FormTextField("FamilyDescriptionSearch", "", true, 20);
    familyDescriptionSearch.setId("FamilyDescriptionSearch");
    form.addElement(familyDescriptionSearch);
    FormTextField companyDescriptionSearch = new FormTextField("CompanyDescriptionSearch", "", true, 20);
    companyDescriptionSearch.setId("CompanyDescriptionSearch");
    form.addElement(companyDescriptionSearch);
    FormTextField divisionDescriptionSearch = new FormTextField("DivisionDescriptionSearch", "", true, 20);
    divisionDescriptionSearch.setId("DivisionDescriptionSearch");
    form.addElement(divisionDescriptionSearch);
    FormTextField labelDescriptionSearch = new FormTextField("LabelDescriptionSearch", "", true, 20);
    labelDescriptionSearch.setId("LabelDescriptionSearch");
    form.addElement(labelDescriptionSearch);
    FormTextField entityDescriptionSearch = new FormTextField("EntityDescriptionSearch", "", false, 20);
    entityDescriptionSearch.setId("EntityDescriptionSearch");
    form.addElement(entityDescriptionSearch);
    context.putDelivery("Form", form);
    return context.includeJSP("blank-label-editor.jsp");
  }
  
  public String getDistCoName(FormDropDownMenu disCoElement) {
    String[] menuItems = disCoElement.getMenuTextList();
    String[] valueVector = disCoElement.getValueList();
    for (int i = 0; i < menuItems.length; i++) {
      if (valueVector[i].equals(disCoElement.getStringValue()))
        return menuItems[i]; 
    } 
    return "";
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\LabelHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */