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
import com.universal.milestone.CorporateObjectSearchObj;
import com.universal.milestone.CorporateStructureManager;
import com.universal.milestone.Environment;
import com.universal.milestone.Family;
import com.universal.milestone.FamilyHandler;
import com.universal.milestone.FamilyManager;
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

public class FamilyHandler extends SecureHandler {
  public static final String COMPONENT_CODE = "hBom";
  
  public GeminiApplication application;
  
  public ComponentLog log;
  
  public FamilyHandler(GeminiApplication application) {
    this.application = application;
    this.log = application.getLog("hBom");
  }
  
  public String getDescription() { return "Family Handler"; }
  
  public boolean acceptRequest(Dispatcher dispatcher, Context context, String command) {
    if (super.acceptRequest(dispatcher, context, command))
      if (command.startsWith("family"))
        return handleRequest(dispatcher, context, command);  
    return false;
  }
  
  public boolean handleRequest(Dispatcher dispatcher, Context context, String command) {
    if (command.equalsIgnoreCase("family-search")) {
      search(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("family-editor")) {
      edit(context);
    } else if (command.equalsIgnoreCase("family-edit-save")) {
      save(context);
    } else if (command.equalsIgnoreCase("family-edit-new")) {
      editNew(context);
    } else if (command.equalsIgnoreCase("family-edit-save-new")) {
      editSaveNew(context);
    } else if (command.equalsIgnoreCase("family-edit-delete")) {
      delete(context);
    } 
    return true;
  }
  
  private boolean search(Dispatcher dispatcher, Context context, String command) {
    Notepad notepad = MilestoneHelper.getNotepadFromSession(9, context);
    notepad.setAllContents(null);
    notepad.setSelected(null);
    String descriptionSearch = "";
    if (context.getParameter("FamilyDescriptionSearch") != null)
      descriptionSearch = context.getParameter("FamilyDescriptionSearch"); 
    CorporateObjectSearchObj corpSearch = new CorporateObjectSearchObj();
    corpSearch.setFamilySearch(descriptionSearch);
    notepad.setCorporateObjectSearchObj(corpSearch);
    FamilyManager.getInstance().setNotepadQuery(notepad);
    dispatcher.redispatch(context, "family-editor");
    return true;
  }
  
  public Notepad getFamilyNotepad(Context context, int userId) {
    Vector contents = new Vector();
    if (MilestoneHelper.getNotepadFromSession(9, context) != null) {
      Notepad notepad = MilestoneHelper.getNotepadFromSession(9, context);
      if (notepad.getAllContents() == null) {
        contents = MilestoneHelper.sortCorporateVectorByName(FamilyManager.getInstance().getFamilyNotepadList(userId, notepad));
        notepad.setAllContents(contents);
      } 
      return notepad;
    } 
    String[] columnNames = { "Family" };
    contents = MilestoneHelper.sortCorporateVectorByName(FamilyManager.getInstance().getFamilyNotepadList(userId, null));
    return new Notepad(contents, 0, 7, "Family", 9, columnNames);
  }
  
  private boolean edit(Context context) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getFamilyNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Family family = MilestoneHelper.getScreenFamily(context);
    if (family != null) {
      Form form = null;
      if (family != null) {
        form = buildForm(context, family);
      } else {
        form = buildNewForm(context);
      } 
      context.putDelivery("Form", form);
      return context.includeJSP("family-editor.jsp");
    } 
    return goToBlank(context);
  }
  
  private Form buildForm(Context context, Family family) {
    this.log.debug("buildForm");
    Form familyForm = new Form(this.application, "FamilyForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    String name = "";
    name = family.getName();
    boolean boolVal = MilestoneHelper.getActiveCorporateStructure(family.getStructureID());
    family.setActive(boolVal);
    FormTextField corporateDescription = new FormTextField("CorporateDescription", name, true, 50, 25);
    corporateDescription.addFormEvent("onBlur", "Javascript:checkField(this);fillAbbreviation()");
    corporateDescription.setTabIndex(1);
    familyForm.addElement(corporateDescription);
    context.putDelivery("isArchiId", String.valueOf(MilestoneHelper_2.getIsStructureArchimedes(family.getStructureID())));
    FormCheckBox active = new FormCheckBox("active", "", true, family.getActive());
    active.setTabIndex(3);
    familyForm.addElement(active);
    String abbreviation = "";
    abbreviation = family.getStructureAbbreviation();
    FormTextField corporateAbbreviation = new FormTextField("CorporateAbbreviation", abbreviation, true, 8, 8);
    corporateAbbreviation.addFormEvent("onBlur", "Javascript:checkField(this)");
    corporateAbbreviation.setTabIndex(2);
    familyForm.addElement(corporateAbbreviation);
    Vector environments = family.getEnvironments();
    StringList environmentString = new StringList(",");
    Environment environment = null;
    for (int j = 0; j < environments.size(); j++) {
      environment = (Environment)environments.get(j);
      environmentString.add(environment.getName());
    } 
    FormDropDownMenu children = new FormDropDownMenu("children", "", environmentString.toString(), false);
    children.addFormEvent("style", "background-color:lightgrey;");
    children.addFormEvent("size", "5");
    children.setTabIndex(3);
    familyForm.addElement(children);
    FormTextField lastUpdated = new FormTextField("lastUpdatedDate", false, 50);
    if (family.getLastUpdateDate() != null)
      lastUpdated.setValue(MilestoneHelper.getCustomFormatedDate(family.getLastUpdateDate(), "M/d/yyyy hh:mm:ss a 'ET'")); 
    familyForm.addElement(lastUpdated);
    FormTextField lastUpdatedBy = new FormTextField("lastUpdatedBy", false, 50);
    if (UserManager.getInstance().getUser(family.getLastUpdatingUser()) != null)
      lastUpdatedBy.setValue(UserManager.getInstance().getUser(family.getLastUpdatingUser()).getName()); 
    familyForm.addElement(lastUpdatedBy);
    FormTextField FamilyDescriptionSearch = new FormTextField("FamilyDescriptionSearch", "", false, 20);
    FamilyDescriptionSearch.setId("FamilyDescriptionSearch");
    familyForm.addElement(FamilyDescriptionSearch);
    familyForm.addElement(new FormHidden("cmd", "family-editor"));
    context.putSessionValue("Family", family);
    if (context.getSessionValue("NOTEPAD_FAMILY_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_FAMILY_VISIBLE")); 
    return familyForm;
  }
  
  private Form buildNewForm(Context context) {
    Form familyForm = new Form(this.application, "FamilyForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    FormTextField corporateDescription = new FormTextField("CorporateDescription", "", true, 50, 25);
    corporateDescription.addFormEvent("onBlur", "Javascript:checkField(this);fillAbbreviation()");
    corporateDescription.setTabIndex(1);
    familyForm.addElement(corporateDescription);
    FormCheckBox active = new FormCheckBox("active", "", true, true);
    active.setTabIndex(3);
    familyForm.addElement(active);
    FormTextField corporateAbbreviation = new FormTextField("CorporateAbbreviation", "", true, 8, 8);
    corporateAbbreviation.addFormEvent("onBlur", "Javascript:checkField(this)");
    corporateAbbreviation.setTabIndex(2);
    familyForm.addElement(corporateAbbreviation);
    FormTextField lastUpdated = new FormTextField("lastUpdatedDate", false, 50);
    familyForm.addElement(lastUpdated);
    FormTextField lastUpdatedBy = new FormTextField("lastUpdatedBy", false, 50);
    familyForm.addElement(lastUpdatedBy);
    FormTextField FamilyDescriptionSearch = new FormTextField("FamilyDescriptionSearch", "", false, 20);
    FamilyDescriptionSearch.setId("FamilyDescriptionSearch");
    familyForm.addElement(FamilyDescriptionSearch);
    familyForm.addElement(new FormHidden("cmd", "family-edit-new"));
    if (context.getSessionValue("NOTEPAD_FAMILY_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_FAMILY_VISIBLE")); 
    return familyForm;
  }
  
  private boolean save(Context context) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getFamilyNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Family family = MilestoneHelper.getScreenFamily(context);
    Family timestampFamily = (Family)context.getSessionValue("Family");
    Form form = buildForm(context, family);
    if (timestampFamily == null || (timestampFamily != null && FamilyManager.getInstance().isTimestampValid(timestampFamily))) {
      form.setValues(context);
      String descriptionString = form.getStringValue("CorporateDescription");
      String abbreviationString = form.getStringValue("CorporateAbbreviation");
      if (!CorporateStructureManager.getInstance().isDuplicate(descriptionString, 1, family.getStructureID())) {
        family.setName(descriptionString);
        family.setStructureAbbreviation(abbreviationString);
        family.setActive(((FormCheckBox)form.getElement("active")).isChecked());
        if (!form.isUnchanged()) {
          FormValidation formValidation = form.validate();
          if (formValidation.isGood()) {
            Family savedFamily = FamilyManager.getInstance().saveFamily(family, user.getUserId());
            FormElement lastUpdated = form.getElement("lastupdateddate");
            if (savedFamily.getLastUpdateDate() != null)
              lastUpdated.setValue(MilestoneHelper.getLongDate(savedFamily.getLastUpdateDate())); 
            Cache.flushCorporateStructure();
            context.removeSessionValue("user-companies");
            context.removeSessionValue("user-environments");
            notepad.setAllContents(null);
            notepad = getFamilyNotepad(context, user.getUserId());
            notepad.setSelected(savedFamily);
            family = (Family)notepad.validateSelected();
            context.putSessionValue("Family", savedFamily);
            if (family == null)
              return goToBlank(context); 
            form = buildForm(context, family);
            context.putDelivery("Form", form);
          } else {
            context.putDelivery("FormValidation", formValidation);
            form.addElement(new FormHidden("OrderBy", "", true));
            context.putDelivery("Form", form);
            return context.includeJSP("family-editor.jsp");
          } 
        } 
      } else {
        context.putSessionValue("Family", family);
        context.putDelivery("AlertMessage", 
            MilestoneMessage.getMessage(5, 
              
              new String[] { "Family", descriptionString }));
      } 
    } else {
      context.putDelivery("AlertMessage", "The record is unavailable for editing at this time.  Please refresh your screen and edit it at a later time.");
    } 
    return edit(context);
  }
  
  private boolean delete(Context context) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getFamilyNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Family family = MilestoneHelper.getScreenFamily(context);
    if (family != null) {
      String errorMsg = "";
      errorMsg = CorporateStructureManager.getInstance().delete(family, user.getUserId());
      if (errorMsg != null) {
        context.putDelivery("AlertMessage", errorMsg);
      } else {
        Cache.flushCorporateStructure();
        context.removeSessionValue("user-companies");
        context.removeSessionValue("user-environments");
        notepad.setAllContents(null);
        notepad = getFamilyNotepad(context, user.getUserId());
        notepad.setSelected(null);
      } 
      if (context.getSessionValue("NOTEPAD_FAMILY_VISIBLE") != null)
        context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_FAMILY_VISIBLE")); 
    } 
    return edit(context);
  }
  
  private boolean editNew(Context context) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getFamilyNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Form form = buildNewForm(context);
    context.putDelivery("Form", form);
    return context.includeJSP("family-editor.jsp");
  }
  
  private boolean editSaveNew(Context context) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getFamilyNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Family family = new Family();
    Form form = buildNewForm(context);
    form.setValues(context);
    family.setStructureID(-2);
    String descriptionString = form.getStringValue("CorporateDescription");
    String abbreviationString = form.getStringValue("CorporateAbbreviation");
    if (!CorporateStructureManager.getInstance().isDuplicate(descriptionString, 1, -2)) {
      family.setName(descriptionString);
      family.setStructureAbbreviation(abbreviationString);
      family.setActive(((FormCheckBox)form.getElement("active")).isChecked());
      family.setStructureType(1);
      if (!form.isUnchanged()) {
        FormValidation formValidation = form.validate();
        if (formValidation.isGood()) {
          Family savedFamily = FamilyManager.getInstance().saveFamily(family, user.getUserId());
          FormElement lastUpdated = form.getElement("lastupdateddate");
          if (savedFamily.getLastUpdateDate() != null)
            lastUpdated.setValue(MilestoneHelper.getLongDate(savedFamily.getLastUpdateDate())); 
          context.putSessionValue("Family", savedFamily);
          context.putDelivery("Form", form);
          if (notepad.getAllContents() == null || notepad.getAllContents().size() < 1)
            notepad.setCorporateObjectSearchObj(null); 
          Cache.flushCorporateStructure();
          context.removeSessionValue("user-companies");
          context.removeSessionValue("user-environments");
          notepad.setAllContents(null);
          notepad.newSelectedReset();
          notepad = getFamilyNotepad(context, user.getUserId());
          notepad.setSelected(savedFamily);
        } else {
          context.putDelivery("FormValidation", formValidation);
          form.addElement(new FormHidden("OrderBy", "", true));
          context.putDelivery("Form", form);
          return context.includeJSP("division-editor.jsp");
        } 
      } 
    } else {
      context.putDelivery("AlertMessage", 
          MilestoneMessage.getMessage(5, 
            
            new String[] { "Family", descriptionString }));
    } 
    return edit(context);
  }
  
  private boolean goToBlank(Context context) {
    context.putDelivery("isNotePadVisible", new Boolean(MilestoneHelper.getNotePadVisiblitiy(9, context)));
    Form form = new Form(this.application, "form", this.application.getInfrastructure().getServletURL(), "Post");
    form.addElement(new FormHidden("cmd", "family-editor"));
    form.addElement(new FormHidden("OrderBy", "", true));
    FormTextField FamilyDescriptionSearch = new FormTextField("FamilyDescriptionSearch", "", true, 20);
    FamilyDescriptionSearch.setId("FamilyDescriptionSearch");
    form.addElement(FamilyDescriptionSearch);
    context.putDelivery("Form", form);
    return context.includeJSP("blank-family-editor.jsp");
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\FamilyHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */