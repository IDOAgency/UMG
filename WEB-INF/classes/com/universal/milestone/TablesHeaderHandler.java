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
import com.universal.milestone.Form;
import com.universal.milestone.LookupObject;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.MilestoneSecurity;
import com.universal.milestone.Notepad;
import com.universal.milestone.PrefixCode;
import com.universal.milestone.SecureHandler;
import com.universal.milestone.Table;
import com.universal.milestone.TableManager;
import com.universal.milestone.TablesHeaderHandler;
import com.universal.milestone.User;
import com.universal.milestone.UserManager;
import java.util.Vector;

public class TablesHeaderHandler extends SecureHandler {
  public static final String COMPONENT_CODE = "hTbH";
  
  public GeminiApplication application;
  
  public ComponentLog log;
  
  public TablesHeaderHandler(GeminiApplication application) {
    this.application = application;
    this.log = application.getLog("hTbH");
  }
  
  public String getDescription() { return "Table Header Handler"; }
  
  public boolean acceptRequest(Dispatcher dispatcher, Context context, String command) {
    if (super.acceptRequest(dispatcher, context, command))
      if (command.startsWith("tables"))
        return handleRequest(dispatcher, context, command);  
    return false;
  }
  
  public boolean handleRequest(Dispatcher dispatcher, Context context, String command) {
    if (command.equalsIgnoreCase("tables-header-search"))
      searchHeader(dispatcher, context, command); 
    if (command.equalsIgnoreCase("tables-detail-search")) {
      searchDetail(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("tables-header-editor")) {
      edit(context);
    } else if (command.equalsIgnoreCase("tables-detail-editor")) {
      editDetail(context);
    } else if (command.equalsIgnoreCase("tables-header-edit-save")) {
      saveHeader(context);
    } else if (command.equalsIgnoreCase("tables-detail-edit-save")) {
      saveDetail(context);
    } else if (command.equalsIgnoreCase("tables-detail-edit-save-new")) {
      saveDetailNew(context);
    } else if (command.equalsIgnoreCase("tables-header-edit-delete")) {
      deleteHeader(context);
    } else if (command.equalsIgnoreCase("tables-detail-edit-delete")) {
      deleteDetail(context);
    } else if (command.equalsIgnoreCase("tables-header-edit-new")) {
      editNewHeader(context);
    } else if (command.equalsIgnoreCase("tables-detail-edit-new")) {
      editNewDetail(context);
    } 
    return true;
  }
  
  private boolean saveHeader(Context context) {
    User user = MilestoneSecurity.getUser(context);
    Table table = (Table)context.getSessionValue("Table");
    if (table.getDetail() != null) {
      Notepad notepad = getTableNotepad(context, user.getUserId());
      MilestoneHelper.putNotepadIntoSession(notepad, context);
      context.removeSessionValue(NOTEPAD_SESSION_NAMES[18]);
      Form form = buildForm(context, table);
      if (table.getName().equals("Prefix Code")) {
        Vector environments = Cache.getInstance().getEnvironments();
        FormDropDownMenu environmentsDropDown = MilestoneHelper.getCorporateStructureDropDown("environments", environments, table.getSubDetail().getAbbreviation(), true, false);
        form.addElement(environmentsDropDown);
      } 
      if (table.getName().equals("Sub-Format")) {
        boolean parentChecked = false;
        if (table.getSubDetail() != null && table.getSubDetail().getName().equalsIgnoreCase("Y"))
          parentChecked = true; 
        FormCheckBox Parent = new FormCheckBox("SubConfiguration", "SubConfiguratioin", "", false);
        Parent.setChecked(parentChecked);
        Parent.setId("SubConfiguration");
        form.addElement(Parent);
        String configValue = "";
        if (table.getSubDetail() != null)
          configValue = TableManager.getInstance().getAssocConfig(3, table.getSubDetail().getDetValue()); 
        FormDropDownMenu configuration = MilestoneHelper.getSelectionConfigurationDropDown("configuration", configValue, true);
        form.addElement(configuration);
      } 
      if (table == null || (table != null && TableManager.getInstance().isTimestampValid(table))) {
        form.setValues(context);
        String descriptionString = form.getStringValue("Description");
        String subdescriptionString = form.getStringValue("SubDescription");
        String environmentString = "";
        if (form.getStringValue("environments") != null)
          environmentString = form.getStringValue("environments"); 
        String assocConfig = "";
        String parent = "";
        if (form.getStringValue("SubConfiguration") != null) {
          if (((FormCheckBox)form.getElement("SubConfiguration")).isChecked()) {
            parent = "Y";
          } else {
            parent = "N";
          } 
          assocConfig = form.getStringValue("configuration");
        } 
        LookupObject detail = table.getDetail();
        PrefixCode subdetail = table.getSubDetail();
        boolean isThereSubDetail = false;
        if (table.getSubDetail().getName() != null || 
          table.getSubDetail().getAbbreviation() != null || 
          table.getSubDetail().getDetValue() != null) {
          isThereSubDetail = true;
        } else {
          isThereSubDetail = false;
        } 
        if (form.getStringValue("SubConfiguration") != null)
          isThereSubDetail = true; 
        detail.setName(descriptionString);
        detail.setInactive(((FormCheckBox)form.getElement("inactive")).isChecked());
        if (isThereSubDetail && form.getElement("subdetinactive") != null)
          subdetail.setInactive(((FormCheckBox)form.getElement("subdetinactive")).isChecked()); 
        if (!environmentString.equals("")) {
          subdetail.setAbbreviation(environmentString);
        } else {
          subdetail.setAbbreviation(detail.getName());
        } 
        subdetail.setDetValue(detail.getAbbreviation());
        if (!parent.equals("")) {
          subdetail.setName(parent);
          subdetail.setAbbreviation("Parent");
        } else {
          subdetail.setName(subdescriptionString);
        } 
        Table savedTable = new Table();
        boolean isNew = false;
        if (!form.isUnchanged()) {
          FormValidation formValidation = form.validate();
          if (formValidation.isGood()) {
            if (subdetail.getAbbreviation() != null && subdetail.getName() != null) {
              table.setDetail(detail);
              table.setSubDetail(subdetail);
              savedTable = TableManager.getInstance().saveTableDetail(table, user.getUserId(), isThereSubDetail, assocConfig);
              if (savedTable == null)
                savedTable = new Table(); 
            } else {
              table.setDetail(detail);
              table.setSubDetail(subdetail);
              String detailString = (detail != null) ? detail.getAbbreviation() : "";
              if (!TableManager.getInstance().isDuplicate(table.getId(), detailString)) {
                savedTable = TableManager.getInstance().insertTableDetail(table, user.getUserId(), assocConfig);
                isNew = true;
              } else {
                context.putDelivery("AlertMessage", "The record has been inserted by another user.\\nTo resubmit your information and view this web page, click the Refresh button.\\nChanges can be printed before refreshing to assist in entering data after refresh.");
                context.putDelivery("Form", form);
                return context.includeJSP("tables-header-editor.jsp");
              } 
            } 
            Cache.flushTableVariables(String.valueOf(table.getId()));
            FormElement lastUpdated = form.getElement("lastupdateddate");
            if (savedTable.getLastUpdateDate() != null)
              lastUpdated.setValue(MilestoneHelper.getLongDate(savedTable.getLastUpdateDate())); 
            context.putSessionValue("Table", savedTable);
            context.putDelivery("Form", form);
            notepad.setAllContents(null);
            if (isNew)
              notepad.newSelectedReset(); 
            notepad = getTableNotepad(context, user.getUserId());
          } else {
            context.putDelivery("FormValidation", formValidation);
            context.putDelivery("Form", form);
            return context.includeJSP("tables-header-editor.jsp");
          } 
        } 
      } else {
        context.putDelivery("AlertMessage", "The record is unavailable for editing at this time.  Please refresh your screen and edit it at a later time.");
      } 
      context.putDelivery("Form", form);
      return edit(context);
    } 
    Form form = buildNewHeaderForm(context);
    form.addElement(new FormHidden("cmd", "tables-header-edit-save"));
    Notepad notepad = getTableNotepad(context, user.getUserId());
    Table selected = (Table)notepad.getSelected();
    if (selected.getName().equals("Prefix Code")) {
      Vector environments = Cache.getInstance().getEnvironments();
      FormDropDownMenu environmentsDropDown = MilestoneHelper.getCorporateStructureDropDown("environments", environments, "0", true, false);
      form.addElement(environmentsDropDown);
    } 
    String assocConfig = "";
    form.setValues(context);
    table.setId(selected.getId());
    String valueString = form.getStringValue("Value");
    String descriptionString = form.getStringValue("Description");
    String subdescriptionString = form.getStringValue("SubDescription");
    String environmentString = "";
    if (form.getStringValue("environments") != null)
      environmentString = form.getStringValue("environments"); 
    LookupObject detail = new LookupObject();
    PrefixCode subdetail = new PrefixCode();
    detail.setAbbreviation(valueString);
    detail.setName(descriptionString);
    subdetail.setName(subdescriptionString);
    if (!environmentString.equals("")) {
      subdetail.setAbbreviation(environmentString);
    } else {
      subdetail.setAbbreviation(detail.getName());
    } 
    subdetail.setDetValue(detail.getAbbreviation());
    detail.setInactive(((FormCheckBox)form.getElement("inactive")).isChecked());
    if (form.getElement("subdetinactive") != null)
      subdetail.setInactive(((FormCheckBox)form.getElement("subdetinactive")).isChecked()); 
    table.setDetail(detail);
    table.setSubDetail(subdetail);
    if (!form.isUnchanged()) {
      FormValidation formValidation = form.validate();
      if (formValidation.isGood()) {
        String detailString = (detail != null) ? detail.getAbbreviation() : "";
        if (!TableManager.getInstance().isDuplicate(table.getId(), detailString)) {
          Table savedTable = TableManager.getInstance().insertTableDetail(table, user.getUserId(), assocConfig);
          Cache.flushTableVariables(String.valueOf(table.getId()));
          FormElement lastUpdated = form.getElement("LastUpdatedDate");
          context.putSessionValue("Table", savedTable);
          context.putDelivery("Form", form);
          if (notepad.getAllContents() == null || notepad.getAllContents().size() < 1)
            notepad.setSearchQuery(""); 
          notepad.setAllContents(null);
          notepad.newSelectedReset();
          notepad = getTableNotepad(context, user.getUserId());
          notepad.goToSelectedPage();
        } else {
          context.putDelivery("AlertMessage", "The record has been inserted by another user.\\nTo resubmit your information and view this web page, click the Refresh button.\\nChanges can be printed before refreshing to assist in entering data after refresh.");
          form.addElement(new FormHidden("OrderBy", "", true));
          form.addElement(new FormHidden("cmd", "tables-header-edit-new"));
          context.putDelivery("Form", form);
          return context.includeJSP("tables-header-editor.jsp");
        } 
      } else {
        context.putDelivery("FormValidation", formValidation);
        form.addElement(new FormHidden("OrderBy", "", true));
        form.addElement(new FormHidden("cmd", "tables-header-edit-new"));
        context.putDelivery("Form", form);
        return context.includeJSP("tables-header-editor.jsp");
      } 
    } 
    return edit(context);
  }
  
  private boolean deleteHeader(Context context) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getTableNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    context.removeSessionValue(NOTEPAD_SESSION_NAMES[18]);
    Table table = MilestoneHelper.getScreenTable(context);
    TableManager.getInstance().deleteTable(table, user.getUserId());
    Cache.flushTableVariables(String.valueOf(table.getId()));
    notepad.setAllContents(null);
    notepad = getTableNotepad(context, user.getUserId());
    notepad.goToSelectedPage();
    return edit(context);
  }
  
  private boolean deleteDetail(Context context) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getTableNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Table selected = (Table)notepad.getSelected();
    Notepad notepadDetail = getTableDetailNotepad(context, selected.getId());
    MilestoneHelper.putNotepadIntoSession(notepadDetail, context);
    Table tableDetail = MilestoneHelper.getScreenTableDetail(context);
    TableManager.getInstance().deleteTableDetail(tableDetail, user.getUserId());
    Cache.flushTableVariables(String.valueOf(tableDetail.getSubDetail().getId()));
    notepadDetail.setAllContents(null);
    notepadDetail = getTableDetailNotepad(context, selected.getId());
    notepadDetail.setSelected(null);
    return editDetail(context);
  }
  
  private boolean saveDetail(Context context) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getTableNotepad(context, user.getUserId());
    Table selected = (Table)context.getSessionValue("Table");
    Notepad notepadDetail = getTableDetailNotepad(context, selected.getId());
    MilestoneHelper.putNotepadIntoSession(notepadDetail, context);
    Table tableDetail = MilestoneHelper.getScreenTableDetail(context);
    if (selected != null && tableDetail.getDetail() != null) {
      Form form = buildDetailForm(context, tableDetail);
      if (selected.getName().equals("Prefix Code")) {
        Vector environments = Cache.getInstance().getEnvironments();
        FormDropDownMenu environmentsDropDown = MilestoneHelper.getCorporateStructureDropDown("environments", environments, tableDetail.getSubDetail().getDetValue(), true, false);
        form.addElement(environmentsDropDown);
      } 
      if (selected.getName().equals("Sub-Format")) {
        boolean parentChecked = false;
        if (selected.getSubDetail() != null && selected.getSubDetail().getName().equalsIgnoreCase("Y"))
          parentChecked = true; 
        FormCheckBox Parent = new FormCheckBox("SubConfiguration", "SubConfiguration", "", false);
        Parent.setChecked(parentChecked);
        Parent.setId("SubConfiguration");
        form.addElement(Parent);
        String configValue = "";
        if (selected.getSubDetail() != null)
          configValue = TableManager.getInstance().getAssocConfig(3, selected.getSubDetail().getDetValue()); 
        FormDropDownMenu configuration = MilestoneHelper.getSelectionConfigurationDropDown("configuration", configValue, true);
        form.addElement(configuration);
      } 
      Table timestampTable = (Table)context.getSessionValue("TableDetail");
      if (timestampTable == null || (timestampTable != null && TableManager.getInstance().isTimestampValid(timestampTable))) {
        form.setValues(context);
        String descriptionString = form.getStringValue("Description");
        String subdescriptionString = form.getStringValue("SubDescription");
        String environmentString = "";
        if (form.getStringValue("environments") != null)
          environmentString = form.getStringValue("environments"); 
        String assocConfig = "";
        String parent = "";
        if (form.getStringValue("SubConfiguration") != null) {
          if (((FormCheckBox)form.getElement("SubConfiguration")).isChecked()) {
            parent = "Y";
          } else {
            parent = "N";
          } 
          assocConfig = form.getStringValue("configuration");
        } 
        LookupObject detail = tableDetail.getDetail();
        PrefixCode subdetail = tableDetail.getSubDetail();
        boolean isThereSubDetail = false;
        if (tableDetail.getSubDetail().getName() != null || 
          tableDetail.getSubDetail().getAbbreviation() != null || 
          tableDetail.getSubDetail().getDetValue() != null) {
          isThereSubDetail = true;
        } else {
          isThereSubDetail = false;
        } 
        detail.setName(descriptionString);
        detail.setInactive(((FormCheckBox)form.getElement("inactive")).isChecked());
        if (isThereSubDetail && form.getElement("subdetinactive") != null)
          subdetail.setInactive(((FormCheckBox)form.getElement("subdetinactive")).isChecked()); 
        if (selected.getId() == 3 || selected.getId() == 1 || selected.getId() == 29) {
          if (form.getElement("prodType") != null)
            detail.setProdType(Integer.parseInt(form.getStringValue("prodType"))); 
          if (selected.getId() == 29)
            if (form.getElement("isDigitalEquivalent") != null)
              detail.setIsDigitalEquivalent(((FormCheckBox)form.getElement("isDigitalEquivalent")).isChecked());  
        } 
        if (!environmentString.equals("")) {
          subdetail.setAbbreviation(environmentString);
        } else if (selected.getId() == 22) {
          subdetail.setAbbreviation("Abbrev");
        } else {
          subdetail.setAbbreviation(detail.getName());
        } 
        subdetail.setDetValue(detail.getAbbreviation());
        if (!parent.equals("")) {
          subdetail.setName(parent);
          subdetail.setAbbreviation("Parent");
        } else {
          subdetail.setName(subdescriptionString);
        } 
        tableDetail.setDetail(detail);
        tableDetail.setSubDetail(subdetail);
        if (!form.isUnchanged()) {
          FormValidation formValidation = form.validate();
          if (formValidation.isGood()) {
            Table savedTable = TableManager.getInstance().saveTableDetail(tableDetail, user.getUserId(), isThereSubDetail, assocConfig);
            Cache.flushTableVariables(String.valueOf(tableDetail.getId()));
            FormElement lastUpdated = form.getElement("lastupdateddate");
            if (savedTable.getLastUpdateDate() != null)
              lastUpdated.setValue(MilestoneHelper.getLongDate(savedTable.getLastUpdateDate())); 
            context.putSessionValue("TableDetail", savedTable);
            context.putDelivery("Form", form);
            notepad.setAllContents(null);
            notepad = getTableNotepad(context, user.getUserId());
            notepad.goToSelectedPage();
            notepadDetail.setAllContents(null);
            notepadDetail = getTableDetailNotepad(context, selected.getId());
            notepadDetail.goToSelectedPage();
            notepadDetail.setSelected(savedTable);
          } else {
            context.putDelivery("FormValidation", formValidation);
            context.putDelivery("Form", form);
            return context.includeJSP("tables-detail-editor.jsp");
          } 
        } 
      } else {
        context.putDelivery("AlertMessage", "The record is unavailable for editing at this time.  Please refresh your screen and edit it at a later time.");
      } 
      context.putDelivery("Form", form);
    } 
    return editDetail(context);
  }
  
  private boolean saveDetailNew(Context context) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getTableNotepad(context, user.getUserId());
    Table selected = (Table)context.getSessionValue("Table");
    Notepad notepadDetail = getTableDetailNotepad(context, selected.getId());
    MilestoneHelper.putNotepadIntoSession(notepadDetail, context);
    Table tableDetail = MilestoneHelper.getScreenTableDetail(context);
    Form form = buildNewDetailForm(context);
    if (selected.getName().equals("Prefix Code")) {
      Vector environments = Cache.getInstance().getEnvironments();
      FormDropDownMenu environmentsDropDown = MilestoneHelper.getCorporateStructureDropDown("environments", environments, "0", true, false);
      form.addElement(environmentsDropDown);
    } 
    if (selected.getName().equals("Sub-Format")) {
      boolean parentChecked = false;
      if (selected.getSubDetail() != null && selected.getSubDetail().getName().equalsIgnoreCase("Y"))
        parentChecked = true; 
      FormCheckBox Parent = new FormCheckBox("SubConfiguration", "SubConfiguration", "", false);
      Parent.setChecked(parentChecked);
      Parent.setId("SubConfiguration");
      form.addElement(Parent);
      String configValue = "";
      if (selected.getSubDetail() != null)
        configValue = TableManager.getInstance().getAssocConfig(3, selected.getSubDetail().getDetValue()); 
      FormDropDownMenu configuration = MilestoneHelper.getSelectionConfigurationDropDown("configuration", configValue, true);
      form.addElement(configuration);
    } 
    form.setValues(context);
    tableDetail.setId(selected.getId());
    String valueString = form.getStringValue("Value");
    String descriptionString = form.getStringValue("Description");
    String subdescriptionString = form.getStringValue("SubDescription");
    String environmentString = "";
    if (form.getStringValue("environments") != null)
      environmentString = form.getStringValue("environments"); 
    String assocConfig = "";
    String parent = "";
    if (form.getStringValue("SubConfiguration") != null) {
      if (((FormCheckBox)form.getElement("SubConfiguration")).isChecked()) {
        parent = "Y";
      } else {
        parent = "N";
      } 
      assocConfig = form.getStringValue("configuration");
    } 
    LookupObject detail = new LookupObject();
    PrefixCode subdetail = new PrefixCode();
    detail.setAbbreviation(valueString);
    detail.setName(descriptionString);
    detail.setInactive(((FormCheckBox)form.getElement("inactive")).isChecked());
    if (form.getElement("subdetinactive") != null)
      subdetail.setInactive(((FormCheckBox)form.getElement("subdetinactive")).isChecked()); 
    if (selected.getId() == 3 || selected.getId() == 1 || selected.getId() == 29) {
      if (form.getElement("prodType") != null)
        detail.setProdType(Integer.parseInt(form.getStringValue("prodType"))); 
      if (selected.getId() == 29)
        if (form.getElement("isDigitalEquivalent") != null)
          detail.setIsDigitalEquivalent(((FormCheckBox)form.getElement("isDigitalEquivalent")).isChecked());  
    } 
    if (!parent.equals("")) {
      subdetail.setName(parent);
      subdetail.setAbbreviation("Parent");
    } else {
      subdetail.setName(subdescriptionString);
    } 
    if (!environmentString.equals("")) {
      subdetail.setAbbreviation(environmentString);
    } else {
      subdetail.setAbbreviation(detail.getName());
    } 
    subdetail.setDetValue(detail.getAbbreviation());
    tableDetail.setDetail(detail);
    tableDetail.setSubDetail(subdetail);
    if (!form.isUnchanged()) {
      FormValidation formValidation = form.validate();
      if (formValidation.isGood()) {
        String detailString = (detail != null) ? detail.getAbbreviation() : "";
        if (!TableManager.getInstance().isDuplicate(tableDetail.getId(), detailString)) {
          Table savedTable = TableManager.getInstance().insertTableDetail(tableDetail, user.getUserId(), assocConfig);
          Cache.flushTableVariables(String.valueOf(tableDetail.getId()));
          FormElement lastUpdated = form.getElement("LastUpdatedDate");
          context.putSessionValue("TableDetail", savedTable);
          context.putDelivery("Form", form);
          notepadDetail.setAllContents(null);
          notepadDetail.newSelectedReset();
          notepadDetail = getTableDetailNotepad(context, selected.getId());
          notepadDetail.goToSelectedPage();
          notepadDetail.setSelected(savedTable);
          form.addElement(new FormHidden("cmd", "tables-detail-edit-save"));
        } else {
          context.putDelivery("AlertMessage", "The record has been inserted by another user.\\nTo resubmit your information and view this web page, click the Refresh button.\\nChanges can be printed before refreshing to assist in entering data after refresh.");
          form.addElement(new FormHidden("OrderBy", "", true));
          form.addElement(new FormHidden("cmd", "tables-detail-edit-save"));
          context.putDelivery("Form", form);
          return context.includeJSP("tables-detail-editor.jsp");
        } 
      } else {
        context.putDelivery("FormValidation", formValidation);
        form.addElement(new FormHidden("OrderBy", "", true));
        form.addElement(new FormHidden("cmd", "tables-detail-edit-new"));
        context.putDelivery("Form", form);
        return context.includeJSP("tables-detail-editor.jsp");
      } 
    } 
    return editDetail(context);
  }
  
  private boolean searchHeader(Dispatcher dispatcher, Context context, String command) {
    Notepad notepad = MilestoneHelper.getNotepadFromSession(14, context);
    notepad.setAllContents(null);
    notepad.setSelected(null);
    TableManager.getInstance().setNotepadQuery(context, MilestoneSecurity.getUser(context).getUserId(), notepad);
    dispatcher.redispatch(context, "tables-header-editor");
    return true;
  }
  
  private boolean searchDetail(Dispatcher dispatcher, Context context, String command) {
    Notepad notepadHeader = MilestoneHelper.getNotepadFromSession(14, context);
    Notepad notepad = MilestoneHelper.getNotepadFromSession(18, context);
    Table selected = null;
    Table selectedHeader = (Table)notepadHeader.getSelected();
    int fieldId = -1;
    try {
      selected = (Table)notepad.getSelected();
      if (selected != null) {
        fieldId = selected.getId();
      } else {
        fieldId = selectedHeader.getId();
      } 
    } catch (Exception ex) {
      if (selectedHeader != null)
        fieldId = selectedHeader.getId(); 
    } 
    notepad.setAllContents(null);
    notepad.setSelected(null);
    TableManager.getInstance().setDetailNotepadQuery(context, fieldId, notepad);
    dispatcher.redispatch(context, "tables-detail-editor");
    return true;
  }
  
  private boolean searchResults(Dispatcher dispatcher, Context context, String command) { return context.includeJSP("tables-header-search-results.jsp"); }
  
  public Notepad getTableNotepad(Context context, int userId) {
    Vector contents = new Vector();
    if (MilestoneHelper.getNotepadFromSession(14, context) != null) {
      Notepad notepad = MilestoneHelper.getNotepadFromSession(14, context);
      if (notepad.getAllContents() == null) {
        contents = TableManager.getInstance().getTableNotepadList(userId, notepad);
        notepad.setAllContents(contents);
      } 
      return notepad;
    } 
    String[] columnNames = { "Description" };
    contents = TableManager.getInstance().getTableNotepadList(userId, null);
    return new Notepad(contents, 0, 15, "Tables", 14, columnNames);
  }
  
  public Notepad getTableDetailNotepad(Context context, int fieldID) {
    Vector contents = new Vector();
    if (MilestoneHelper.getNotepadFromSession(18, context) != null) {
      Notepad notepad = MilestoneHelper.getNotepadFromSession(18, context);
      if (notepad.getAllContents() == null) {
        contents = TableManager.getInstance().getTableDetailNotepadList(fieldID, notepad);
        notepad.setAllContents(contents);
      } 
      return notepad;
    } 
    String[] columnNames = { "Description" };
    contents = TableManager.getInstance().getTableDetailNotepadList(fieldID, null);
    return new Notepad(contents, 0, 15, "Table", 18, columnNames);
  }
  
  private boolean edit(Context context) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getTableNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    context.removeSessionValue(NOTEPAD_SESSION_NAMES[18]);
    Table table = MilestoneHelper.getScreenTable(context);
    if (table != null) {
      if (table.getDetail() != null && table.getDetail().getId() > 0) {
        Form form = buildForm(context, table);
        if (table.getName().equals("Prefix Code")) {
          Vector environments = Cache.getInstance().getEnvironments();
          FormDropDownMenu environmentsDropDown = MilestoneHelper.getCorporateStructureDropDown("environments", environments, table.getSubDetail().getAbbreviation(), true, false);
          form.addElement(environmentsDropDown);
        } 
        if (table.getName().equals("Sub-Format")) {
          boolean parentChecked = false;
          if (table.getSubDetail().getName().equalsIgnoreCase("Y"))
            parentChecked = true; 
          FormCheckBox Parent = new FormCheckBox("SubConfiguration", "SubConfiguration", "", false);
          Parent.setChecked(parentChecked);
          Parent.setId("SubConfiguration");
          form.addElement(Parent);
          String configValue = "";
          if (table.getSubDetail() != null)
            configValue = TableManager.getInstance().getAssocConfig(3, table.getSubDetail().getDetValue()); 
          FormDropDownMenu configuration = MilestoneHelper.getSelectionConfigurationDropDown("configuration", configValue, true);
          form.addElement(configuration);
        } 
        context.putSessionValue("Table", table);
        context.putDelivery("Form", form);
        return context.includeJSP("tables-header-editor.jsp");
      } 
      return editNewHeader(context);
    } 
    Vector contents = null;
    contents = notepad.getAllContents();
    notepad.setSwitchToDetailVisible(false);
    if (contents != null && contents.size() > 0) {
      Form form = buildNewHeaderForm(context);
      if (table.getName().equals("Prefix Code")) {
        Vector environments = Cache.getInstance().getEnvironments();
        FormDropDownMenu environmentsDropDown = MilestoneHelper.getCorporateStructureDropDown("environments", environments, table.getSubDetail().getAbbreviation(), true, false);
        form.addElement(environmentsDropDown);
      } 
      if (table.getName().equals("Sub-Format")) {
        boolean parentChecked = false;
        if (table.getSubDetail().getName().equalsIgnoreCase("Y"))
          parentChecked = true; 
        FormCheckBox Parent = new FormCheckBox("SubConfiguration", "SubConfiguration", "", false);
        Parent.setChecked(parentChecked);
        Parent.setId("SubConfiguration");
        form.addElement(Parent);
        String configValue = "";
        if (table.getSubDetail() != null)
          configValue = TableManager.getInstance().getAssocConfig(3, table.getSubDetail().getDetValue()); 
        FormDropDownMenu configuration = MilestoneHelper.getSelectionConfigurationDropDown("configuration", configValue, true);
        form.addElement(configuration);
      } 
      context.putDelivery("Form", form);
      return context.includeJSP("tables-header-editor.jsp");
    } 
    context.putDelivery("isNotePadVisible", new Boolean(MilestoneHelper.getNotePadVisiblitiy(14, context)));
    Form form = new Form(this.application, "form", this.application.getInfrastructure().getServletURL(), "Post");
    form.addElement(new FormHidden("cmd", "tables-header-editor"));
    form.addElement(new FormHidden("OrderBy", "", true));
    FormTextField DescriptionSearch = new FormTextField("DescriptionSearch", "", true, 20);
    DescriptionSearch.setId("DescriptionSearch");
    form.addElement(DescriptionSearch);
    FormCheckBox isDigitalEquivalentSearch = new FormCheckBox("isDigitalEquivalentSearch", "Digital Equivalent", false, false);
    isDigitalEquivalentSearch.setId("isDigitalEquivalentSearch");
    isDigitalEquivalentSearch.setText("");
    form.addElement(isDigitalEquivalentSearch);
    context.putDelivery("Form", form);
    return context.includeJSP("blank-tables-header-editor.jsp");
  }
  
  private boolean editDetail(Context context) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getTableNotepad(context, user.getUserId());
    Table selected = (Table)notepad.getSelected();
    int selectedId = -1;
    if (selected != null)
      selectedId = selected.getId(); 
    Notepad notepadDetail = getTableDetailNotepad(context, selectedId);
    MilestoneHelper.putNotepadIntoSession(notepadDetail, context);
    Table tableDetail = MilestoneHelper.getScreenTableDetail(context);
    Form form = null;
    if (selected != null) {
      if (tableDetail != null) {
        form = buildDetailForm(context, tableDetail);
        if (selected.getName().equals("Prefix Code")) {
          Vector environments = Cache.getInstance().getEnvironments();
          FormDropDownMenu environmentsDropDown = MilestoneHelper.getCorporateStructureDropDown("environments", environments, tableDetail.getSubDetail().getAbbreviation(), true, false);
          form.addElement(environmentsDropDown);
        } 
        if (selected.getName().equals("Sub-Format")) {
          boolean parentChecked = false;
          if (tableDetail.getSubDetail() != null && 
            tableDetail.getSubDetail().getName() != null && 
            tableDetail.getSubDetail().getName().equalsIgnoreCase("Y"))
            parentChecked = true; 
          FormCheckBox Parent = new FormCheckBox("SubConfiguration", "SubConfiguration", "", false);
          Parent.setChecked(parentChecked);
          Parent.setId("SubConfiguration");
          form.addElement(Parent);
          String configValue = "";
          if (tableDetail.getSubDetail() != null)
            configValue = TableManager.getInstance().getAssocConfig(3, tableDetail.getSubDetail().getDetValue()); 
          FormDropDownMenu configuration = MilestoneHelper.getSelectionConfigurationDropDown("configuration", configValue, true);
          form.addElement(configuration);
        } 
        context.putSessionValue("TableDetail", tableDetail);
        context.putDelivery("Form", form);
        return context.includeJSP("tables-detail-editor.jsp");
      } 
      context.putDelivery("isNotePadVisible", new Boolean(MilestoneHelper.getNotePadVisiblitiy(14, context)));
      form = new Form(this.application, "form", this.application.getInfrastructure().getServletURL(), "Post");
      form.addElement(new FormHidden("cmd", "tables-detail-editor"));
      form.addElement(new FormHidden("OrderBy", "", true));
      FormTextField DescriptionSearch = new FormTextField("DescriptionSearch", "", true, 20);
      DescriptionSearch.setId("DescriptionSearch");
      form.addElement(DescriptionSearch);
      FormCheckBox isDigitalEquivalentSearch = new FormCheckBox("isDigitalEquivalentSearch", "Digital Equivalent", false, false);
      isDigitalEquivalentSearch.setId("isDigitalEquivalentSearch");
      isDigitalEquivalentSearch.setText("");
      form.addElement(isDigitalEquivalentSearch);
      context.putDelivery("Form", form);
      return context.includeJSP("blank-tables-detail-editor.jsp");
    } 
    context.putDelivery("isNotePadVisible", new Boolean(MilestoneHelper.getNotePadVisiblitiy(14, context)));
    form = new Form(this.application, "form", this.application.getInfrastructure().getServletURL(), "Post");
    form.addElement(new FormHidden("cmd", "tables-detail-editor"));
    form.addElement(new FormHidden("OrderBy", "", true));
    FormTextField DescriptionSearch = new FormTextField("DescriptionSearch", "", true, 20);
    DescriptionSearch.setId("DescriptionSearch");
    form.addElement(DescriptionSearch);
    FormCheckBox isDigitalEquivalentSearch = new FormCheckBox("isDigitalEquivalentSearch", "Digital Equivalent", false, false);
    isDigitalEquivalentSearch.setId("isDigitalEquivalentSearch");
    isDigitalEquivalentSearch.setText("");
    form.addElement(isDigitalEquivalentSearch);
    context.putDelivery("Form", form);
    return context.includeJSP("blank-tables-detail-editor.jsp");
  }
  
  private Form buildForm(Context context, Table table) {
    Form tablesHeaderForm = new Form(this.application, "tablesHeaderForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    tablesHeaderForm.addElement(new FormHidden("cmd", "tables-header-editor", true));
    FormTextField Id = new FormTextField("id", Integer.toString(table.getId()), false, 50, 50);
    tablesHeaderForm.addElement(Id);
    FormTextField Title = new FormTextField("Title", "", false, 50, 50);
    Title.setValue(table.getName());
    tablesHeaderForm.addElement(Title);
    LookupObject detail = table.getDetail();
    tablesHeaderForm.addElement(new FormHidden("Value", detail.getAbbreviation(), true));
    FormTextField Description = new FormTextField("Description", "", true, 50, 50);
    Description.addFormEvent("onBlur", "Javascript:checkField(this)");
    Description.setTabIndex(1);
    Description.setValue(detail.getName());
    tablesHeaderForm.addElement(Description);
    FormTextField SubDescription = new FormTextField("SubDescription", "", false, 50, 255);
    SubDescription.setTabIndex(2);
    PrefixCode subdetail = table.getSubDetail();
    if (subdetail.getName() != null)
      SubDescription.setValue(subdetail.getName()); 
    tablesHeaderForm.addElement(SubDescription);
    FormTextField lastUpdated = new FormTextField("lastUpdatedDate", false, 50);
    if (table.getLastUpdateDate() != null)
      lastUpdated.setValue(MilestoneHelper.getLongDate(table.getLastUpdateDate())); 
    tablesHeaderForm.addElement(lastUpdated);
    FormTextField lastUpdatedBy = new FormTextField("lastUpdatedBy", false, 50);
    if (UserManager.getInstance().getUser(table.getLastUpdatingUser()) != null)
      lastUpdatedBy.setValue(UserManager.getInstance().getUser(table.getLastUpdatingUser()).getLogin()); 
    tablesHeaderForm.addElement(lastUpdatedBy);
    FormTextField DescriptionSearch = new FormTextField("DescriptionSearch", "", false, 20);
    DescriptionSearch.setId("DescriptionSearch");
    tablesHeaderForm.addElement(DescriptionSearch);
    FormCheckBox isDigitalEquivalentSearch = new FormCheckBox("isDigitalEquivalentSearch", "Digital Equivalent", false, false);
    isDigitalEquivalentSearch.setId("isDigitalEquivalentSearch");
    isDigitalEquivalentSearch.setText("");
    tablesHeaderForm.addElement(isDigitalEquivalentSearch);
    FormCheckBox inactive = new FormCheckBox("inactive", "Inactive", false, !!detail.getInactive());
    inactive.setId("inactive");
    tablesHeaderForm.addElement(inactive);
    tablesHeaderForm.addElement(new FormHidden("cmd", "tables-header"));
    context.putSessionValue("Table", table);
    if (context.getSessionValue("NOTEPAD_TABLE_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_TABLE_VISIBLE")); 
    return tablesHeaderForm;
  }
  
  private Form buildDetailForm(Context context, Table table) {
    Form tablesHeaderForm = new Form(this.application, "tablesDetailForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    tablesHeaderForm.addElement(new FormHidden("cmd", "tables-detail-editor", true));
    Notepad notepad = MilestoneHelper.getNotepadFromSession(14, context);
    Table selected = (Table)notepad.getSelected();
    FormTextField Id = new FormTextField("id", Integer.toString(table.getId()), false, 50, 50);
    tablesHeaderForm.addElement(Id);
    FormTextField Title = new FormTextField("Title", "", false, 50, 50);
    Table tableName = (Table)context.getSessionValue("Table");
    if (tableName != null)
      Title.setValue(tableName.getName()); 
    tablesHeaderForm.addElement(Title);
    LookupObject detail = table.getDetail();
    tablesHeaderForm.addElement(new FormHidden("Value", detail.getAbbreviation(), true));
    FormTextField Description = new FormTextField("Description", "", true, 50, 50);
    Description.addFormEvent("onBlur", "Javascript:checkField(this)");
    Description.setTabIndex(1);
    Description.setValue(detail.getName());
    tablesHeaderForm.addElement(Description);
    FormTextField SubDescription = new FormTextField("SubDescription", "", false, 50, 255);
    SubDescription.setTabIndex(2);
    PrefixCode prefixCode = table.getSubDetail();
    int ps = 2;
    String[] dvalues = new String[ps];
    dvalues[0] = "0";
    dvalues[1] = "1";
    String[] dlabels = new String[ps];
    dlabels[0] = "Physical";
    dlabels[1] = "Digital";
    if (detail.getId() == 3 || detail.getId() == 1 || detail.getId() == 29) {
      FormRadioButtonGroup prodType = new FormRadioButtonGroup("ProdType", 
          String.valueOf(detail.getProdType()), dvalues, dlabels, false);
      if (selected.getId() == 29)
        prodType.addFormEvent("onclick", "SetDigEquiv();"); 
      tablesHeaderForm.addElement(prodType);
      if (detail.getId() == 29) {
        FormCheckBox isDigitalEquivalent = new FormCheckBox("isDigitalEquivalent", "Digital Equivalent", false, !!detail.getIsDigitalEquivalent());
        isDigitalEquivalent.setId("isDigitalEquivalent");
        isDigitalEquivalent.setText("");
        tablesHeaderForm.addElement(isDigitalEquivalent);
      } 
    } else if (detail.getId() == 4) {
      if (TableManager.getInstance().getProdType(detail.getAbbreviation()) >= 0) {
        FormHidden prodType = new FormHidden("prodType", String.valueOf(TableManager.getInstance().getProdType(detail.getAbbreviation())));
        FormHidden prodTypeLabel = new FormHidden("prodTypeLabel", dlabels[TableManager.getInstance().getProdType(detail.getAbbreviation())], false);
        tablesHeaderForm.addElement(prodType);
        tablesHeaderForm.addElement(prodTypeLabel);
      } 
    } 
    if (prefixCode.getName() != null)
      SubDescription.setValue(prefixCode.getName()); 
    tablesHeaderForm.addElement(SubDescription);
    FormTextField lastUpdated = new FormTextField("lastUpdatedDate", false, 50);
    if (table.getLastUpdateDate() != null)
      lastUpdated.setValue(MilestoneHelper.getLongDate(table.getLastUpdateDate())); 
    tablesHeaderForm.addElement(lastUpdated);
    FormTextField lastUpdatedBy = new FormTextField("lastUpdatedBy", false, 50);
    if (UserManager.getInstance().getUser(table.getLastUpdatingUser()) != null)
      lastUpdatedBy.setValue(UserManager.getInstance().getUser(table.getLastUpdatingUser()).getLogin()); 
    FormTextField DescriptionSearch = new FormTextField("DescriptionSearch", "", false, 20);
    DescriptionSearch.setId("DescriptionSearch");
    tablesHeaderForm.addElement(DescriptionSearch);
    FormCheckBox isDigitalEquivalentSearch = new FormCheckBox("isDigitalEquivalentSearch", "Digital Equivalent", false, false);
    isDigitalEquivalentSearch.setId("isDigitalEquivalentSearch");
    isDigitalEquivalentSearch.setText("");
    tablesHeaderForm.addElement(isDigitalEquivalentSearch);
    FormCheckBox inactive = new FormCheckBox("inactive", "Inactive", false, !!detail.getInactive());
    inactive.setId("inactive");
    tablesHeaderForm.addElement(inactive);
    tablesHeaderForm.addElement(new FormHidden("cmd", "tables-header"));
    context.putSessionValue("TableDetail", table);
    if (context.getSessionValue("NOTEPAD_TABLE_DETAIL_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_TABLE_DETAIL_VISIBLE")); 
    return tablesHeaderForm;
  }
  
  private boolean editNewHeader(Context context) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getTableNotepad(context, user.getUserId());
    Table table = (Table)context.getSessionValue("Table");
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    context.removeSessionValue(NOTEPAD_SESSION_NAMES[18]);
    Form form = buildNewHeaderForm(context);
    if (table.getName().equals("Prefix Code")) {
      Vector environments = Cache.getInstance().getEnvironments();
      FormDropDownMenu environmentsDropDown = MilestoneHelper.getCorporateStructureDropDown("environments", environments, "0", true, false);
      form.addElement(environmentsDropDown);
    } 
    if (table != null) {
      table.setDetail(null);
      table.setSubDetail(null);
      notepad.setSelected(table);
    } 
    context.putDelivery("Form", form);
    if (context.getSessionValue("NOTEPAD_TABLE_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_TABLE_VISIBLE")); 
    return context.includeJSP("tables-header-editor.jsp");
  }
  
  private boolean editNewDetail(Context context) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getTableNotepad(context, user.getUserId());
    Table selected = (Table)notepad.getSelected();
    Notepad notepadDetail = null;
    if (selected != null) {
      notepadDetail = getTableDetailNotepad(context, selected.getId());
    } else {
      notepadDetail = getTableDetailNotepad(context, -1);
    } 
    MilestoneHelper.putNotepadIntoSession(notepadDetail, context);
    if (selected != null) {
      selected.setDetail(null);
      selected.setSubDetail(null);
      notepad.setSelected(selected);
    } 
    Form form = buildNewDetailForm(context);
    if (selected.getName().equals("Prefix Code")) {
      Vector environments = Cache.getInstance().getEnvironments();
      FormDropDownMenu environmentsDropDown = MilestoneHelper.getCorporateStructureDropDown("environments", environments, "0", true, false);
      form.addElement(environmentsDropDown);
    } 
    context.putDelivery("Form", form);
    if (context.getSessionValue("NOTEPAD_TABLE_DETAIL_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_TABLE_DETAIL_VISIBLE")); 
    return context.includeJSP("tables-detail-editor.jsp");
  }
  
  protected Form buildNewHeaderForm(Context context) {
    User sessionUser = MilestoneSecurity.getUser(context);
    Form tablesHeaderForm = new Form(this.application, "TablesHeaderForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    FormTextField Id = new FormTextField("id", Integer.toString(-1), false, 50, 50);
    tablesHeaderForm.addElement(Id);
    Notepad notepad = MilestoneHelper.getNotepadFromSession(14, context);
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Table selected = (Table)notepad.getSelected();
    FormTextField Title = new FormTextField("Title", "", true, 50, 50);
    Title.setValue(selected.getName());
    tablesHeaderForm.addElement(Title);
    if (selected.getId() != 33) {
      FormTextField Value = new FormTextField("Value", "", true, 5, 50);
      Value.setTabIndex(1);
      tablesHeaderForm.addElement(Value);
    } 
    FormTextField Description = new FormTextField("Description", "", true, 50, 50);
    Description.addFormEvent("onBlur", "Javascript:checkField(this)");
    Description.setTabIndex(2);
    tablesHeaderForm.addElement(Description);
    if (!selected.getName().equals("Sub-Format")) {
      FormTextField SubDescription = new FormTextField("SubDescription", "", false, 50, 255);
      SubDescription.setTabIndex(3);
      tablesHeaderForm.addElement(SubDescription);
    } 
    FormTextField DescriptionSearch = new FormTextField("DescriptionSearch", "", false, 20);
    DescriptionSearch.setId("DescriptionSearch");
    tablesHeaderForm.addElement(DescriptionSearch);
    FormCheckBox isDigitalEquivalentSearch = new FormCheckBox("isDigitalEquivalentSearch", "Digital Equivalent", false, false);
    isDigitalEquivalentSearch.setId("isDigitalEquivalentSearch");
    isDigitalEquivalentSearch.setText("");
    tablesHeaderForm.addElement(isDigitalEquivalentSearch);
    FormCheckBox inactive = new FormCheckBox("inactive", "Inactive", false, false);
    inactive.setId("inactive");
    tablesHeaderForm.addElement(inactive);
    tablesHeaderForm.addElement(new FormHidden("cmd", "tables-header-edit-new"));
    if (context.getSessionValue("NOTEPAD_TABLE_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_TABLE_VISIBLE")); 
    return tablesHeaderForm;
  }
  
  protected Form buildNewDetailForm(Context context) {
    User sessionUser = MilestoneSecurity.getUser(context);
    Form tablesHeaderForm = new Form(this.application, "TablesHeaderForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    FormTextField Id = new FormTextField("id", Integer.toString(-1), false, 50, 50);
    tablesHeaderForm.addElement(Id);
    Notepad notepad = MilestoneHelper.getNotepadFromSession(14, context);
    Table selected = (Table)notepad.getSelected();
    FormTextField Title = new FormTextField("Title", "", true, 50, 50);
    Title.setValue(selected.getName());
    tablesHeaderForm.addElement(Title);
    if (selected.getId() != 33) {
      FormTextField Value = new FormTextField("Value", "", true, 5, 50);
      Value.setTabIndex(1);
      tablesHeaderForm.addElement(Value);
    } 
    FormTextField Description = new FormTextField("Description", "", true, 50, 50);
    Description.addFormEvent("onBlur", "Javascript:checkField(this)");
    Description.setTabIndex(2);
    tablesHeaderForm.addElement(Description);
    if (selected.getName().equals("Sub-Format")) {
      boolean parentChecked = false;
      FormCheckBox Parent = new FormCheckBox("SubConfiguration", "SubConfiguratioin", "", false);
      Parent.setChecked(parentChecked);
      Parent.setId("SubConfiguration");
      tablesHeaderForm.addElement(Parent);
      String configValue = "";
      FormDropDownMenu configuration = MilestoneHelper.getSelectionConfigurationDropDown("configuration", configValue, true);
      tablesHeaderForm.addElement(configuration);
    } else {
      FormTextField SubDescription = new FormTextField("SubDescription", "", false, 50, 255);
      SubDescription.setTabIndex(3);
      tablesHeaderForm.addElement(SubDescription);
    } 
    int ps = 2;
    String[] dvalues = new String[ps];
    dvalues[0] = "0";
    dvalues[1] = "1";
    String[] dlabels = new String[2];
    dlabels[0] = "Physical";
    dlabels[1] = "Digital";
    if (selected.getId() == 3 || selected.getId() == 1 || selected.getId() == 29) {
      FormRadioButtonGroup prodType = new FormRadioButtonGroup("ProdType", "", dvalues, dlabels, false);
      if (selected.getId() == 29)
        prodType.addFormEvent("onclick", "SetDigEquiv();"); 
      tablesHeaderForm.addElement(prodType);
      if (selected.getId() == 29) {
        FormCheckBox isDigitalEquivalent = new FormCheckBox("isDigitalEquivalent", "Digital Equivalent", false, false);
        isDigitalEquivalent.setId("isDigitalEquivalent");
        isDigitalEquivalent.setText("");
        tablesHeaderForm.addElement(isDigitalEquivalent);
      } 
    } else if (selected.getId() == 4) {
      FormTextField prodType = new FormTextField("prodType", "", false, 50);
      tablesHeaderForm.addElement(prodType);
    } 
    FormTextField DescriptionSearch = new FormTextField("DescriptionSearch", "", false, 20);
    DescriptionSearch.setId("DescriptionSearch");
    tablesHeaderForm.addElement(DescriptionSearch);
    FormCheckBox isDigitalEquivalentSearch = new FormCheckBox("isDigitalEquivalentSearch", "Digital Equivalent", false, false);
    isDigitalEquivalentSearch.setId("isDigitalEquivalentSearch");
    isDigitalEquivalentSearch.setText("");
    tablesHeaderForm.addElement(isDigitalEquivalentSearch);
    FormCheckBox inactive = new FormCheckBox("inactive", "Inactive", false, false);
    inactive.setId("inactive");
    tablesHeaderForm.addElement(inactive);
    tablesHeaderForm.addElement(new FormHidden("cmd", "tables-detail-edit-new"));
    if (context.getSessionValue("NOTEPAD_TABLE_DETAIL_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_TABLE_DETAIL_VISIBLE")); 
    return tablesHeaderForm;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\TablesHeaderHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */