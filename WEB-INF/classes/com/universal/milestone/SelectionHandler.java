package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.gemini.Context;
import com.techempower.gemini.Dispatcher;
import com.techempower.gemini.FormCheckBox;
import com.techempower.gemini.FormDateField;
import com.techempower.gemini.FormDropDownMenu;
import com.techempower.gemini.FormElement;
import com.techempower.gemini.FormHidden;
import com.techempower.gemini.FormRadioButtonGroup;
import com.techempower.gemini.FormTextArea;
import com.techempower.gemini.FormTextField;
import com.techempower.gemini.FormValidation;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.Bom;
import com.universal.milestone.BomHandler;
import com.universal.milestone.Cache;
import com.universal.milestone.Company;
import com.universal.milestone.CompanyAcl;
import com.universal.milestone.CorporateStructureObject;
import com.universal.milestone.Division;
import com.universal.milestone.EmailDistribution;
import com.universal.milestone.Environment;
import com.universal.milestone.EnvironmentAcl;
import com.universal.milestone.Form;
import com.universal.milestone.Genre;
import com.universal.milestone.ImpactDate;
import com.universal.milestone.Label;
import com.universal.milestone.LookupObject;
import com.universal.milestone.MilestoneConstants;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.MilestoneHelper_2;
import com.universal.milestone.MultOtherContact;
import com.universal.milestone.MultSelection;
import com.universal.milestone.Notepad;
import com.universal.milestone.NotepadSortOrder;
import com.universal.milestone.Pfm;
import com.universal.milestone.PfmHandler;
import com.universal.milestone.Plant;
import com.universal.milestone.PnrCommunication;
import com.universal.milestone.PrefixCode;
import com.universal.milestone.ProductCategory;
import com.universal.milestone.ProjectSearch;
import com.universal.milestone.ProjectSearchManager;
import com.universal.milestone.ReleaseType;
import com.universal.milestone.ReleasingFamily;
import com.universal.milestone.Schedule;
import com.universal.milestone.ScheduleManager;
import com.universal.milestone.ScheduledTask;
import com.universal.milestone.SecureHandler;
import com.universal.milestone.Selection;
import com.universal.milestone.SelectionConfiguration;
import com.universal.milestone.SelectionHandler;
import com.universal.milestone.SelectionManager;
import com.universal.milestone.SelectionStatus;
import com.universal.milestone.User;
import com.universal.milestone.UserManager;
import com.universal.milestone.projectSearchSvcClient;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;
import net.umusic.milestone.alps.DcGDRSResults;

public class SelectionHandler extends SecureHandler {
  public static final String COMPONENT_CODE = "hSel";
  
  public GeminiApplication application;
  
  public ComponentLog log;
  
  public SelectionHandler(GeminiApplication application) {
    this.application = application;
    this.log = application.getLog("hSel");
  }
  
  public String getDescription() { return "Selection"; }
  
  public boolean acceptRequest(Dispatcher dispatcher, Context context, String command) {
    if (super.acceptRequest(dispatcher, context, command))
      if (command.startsWith("selection"))
        return handleRequest(dispatcher, context, command);  
    return false;
  }
  
  public boolean handleRequest(Dispatcher dispatcher, Context context, String command) {
    if (command.equalsIgnoreCase("selection-impactDate-cancel"))
      impactEditorCancel(dispatcher, context, command); 
    if (command.equalsIgnoreCase("selection-impactDate-save"))
      impactEditorSave(dispatcher, context, command); 
    if (command.equalsIgnoreCase("selection-impactDate-delete"))
      impactEditorDelete(dispatcher, context, command); 
    if (command.equalsIgnoreCase("selection-impactDate-add"))
      impactEditorAdd(dispatcher, context, command); 
    if (command.equalsIgnoreCase("selection-impactDate-editor"))
      impactEditor(dispatcher, context, command); 
    if (command.equalsIgnoreCase("selection-impactDate-frame")) {
      User user = (User)context.getSession().getAttribute("user");
      Selection selection = (Selection)context.getSessionValue("Selection");
      int secureLevel = getSelectionPermissions(selection, user);
      setButtonVisibilities(selection, user, context, secureLevel, command);
      return context.includeJSP("impactDateFrame.jsp");
    } 
    if (command.equalsIgnoreCase("selection-multSelection-cancel"))
      multSelectionEditorCancel(dispatcher, context, command); 
    if (command.equalsIgnoreCase("selection-multSelection-save"))
      multSelectionEditorSave(dispatcher, context, command); 
    if (command.equalsIgnoreCase("selection-multSelection-delete"))
      multSelectionEditorDelete(dispatcher, context, command); 
    if (command.equalsIgnoreCase("selection-multSelection-add"))
      multSelectionEditorAdd(dispatcher, context, command); 
    if (command.equalsIgnoreCase("selection-multSelection-editor"))
      multSelectionEditor(dispatcher, context, command); 
    if (command.equalsIgnoreCase("selection-multSelection-frame")) {
      User user = (User)context.getSession().getAttribute("user");
      Selection selection = (Selection)context.getSessionValue("Selection");
      int secureLevel = getSelectionPermissions(selection, user);
      setButtonVisibilities(selection, user, context, secureLevel, command);
      return context.includeJSP("multSelectionFrame.jsp");
    } 
    if (command.equalsIgnoreCase("selection-multOtherContact-cancel"))
      multOtherContactEditorCancel(dispatcher, context, command); 
    if (command.equalsIgnoreCase("selection-multOtherContact-save"))
      multOtherContactEditorSave(dispatcher, context, command); 
    if (command.equalsIgnoreCase("selection-multOtherContact-delete"))
      multOtherContactEditorDelete(dispatcher, context, command); 
    if (command.equalsIgnoreCase("selection-multOtherContact-add"))
      multOtherContactEditorAdd(dispatcher, context, command); 
    if (command.equalsIgnoreCase("selection-multOtherContact-editor"))
      multOtherContactEditor(dispatcher, context, command); 
    if (command.equalsIgnoreCase("selection-multOtherContact-frame")) {
      User user = (User)context.getSession().getAttribute("user");
      Selection selection = (Selection)context.getSessionValue("Selection");
      int secureLevel = getSelectionPermissions(selection, user);
      setButtonVisibilities(selection, user, context, secureLevel, command);
      return context.includeJSP("multOtherContactFrame.jsp");
    } 
    if (command.equalsIgnoreCase("selection-search"))
      search(dispatcher, context, command); 
    if (command.equalsIgnoreCase("selection-sort"))
      sort(dispatcher, context, command); 
    if (command.equalsIgnoreCase("selection-group"))
      sortGroup(dispatcher, context, command); 
    if (command.equalsIgnoreCase("selection-manufacturing-sort")) {
      sort(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("selection-manufacturing-group")) {
      sortGroup(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("selection-editor")) {
      edit(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("selection-edit-save")) {
      editSave(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("selection-edit-delete")) {
      editDelete(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("selection-edit-copy") || command.equalsIgnoreCase("selection-edit-copy-digital")) {
      editCopy(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("selection-edit-new") || command.equalsIgnoreCase("selection-edit-new-digital")) {
      editNew(dispatcher, context, command);
    } 
    if (command.equalsIgnoreCase("selection-manufacturing-search")) {
      search(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("selection-manufacturing-editor")) {
      manufacturingEdit(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("selection-manufacturing-edit-save")) {
      manufacturingEditSave(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("selection-manufacturing-plant-add")) {
      manufacturingPlantAdd(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("selection-manufacturing-plant-delete")) {
      manufacturingPlantDelete(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("selection-send-pfmbom") || command.equalsIgnoreCase("selection-send-pfm") || command.equalsIgnoreCase("selection-send-bom") || command.equalsIgnoreCase("selection-send-cancel")) {
      sendPfmBom(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("selection-edit-new-physical-archie-project")) {
      editNew(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("selection-edit-new-digital-archie-project")) {
      editNew(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("selection-get-releasing-families")) {
      getReleasingFamilies(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("selection-search-results")) {
      getSelectionSearchResults(dispatcher, context, command);
    } 
    return true;
  }
  
  private boolean impactEditor(Dispatcher dispatcher, Context context, String command) {
    Form impactForm = new Form(this.application, "impactForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    Vector impactDates = null;
    User user = (User)context.getSession().getAttribute("user");
    Selection selection = (Selection)context.getSessionValue("Selection");
    int secureLevel = getSelectionPermissions(selection, user);
    setButtonVisibilities(selection, user, context, secureLevel, command);
    if (selection != null && selection.getImpactDates() != null) {
      impactDates = selection.getImpactDates();
      for (int j = 0; j < impactDates.size(); j++) {
        ImpactDate impact = (ImpactDate)impactDates.get(j);
        FormDropDownMenu format = MilestoneHelper.getLookupDropDown("format" + j, Cache.getFormats(), impact.getFormat(), true, true);
        FormDateField impactDate = new FormDateField("impactDate" + j, MilestoneHelper.getFormatedDate(impact.getImpactDate()), false, 10);
        impactDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
        FormCheckBox tbi = new FormCheckBox("tbi" + j, "", false, false);
        tbi.setChecked(impact.getTbi());
        impactForm.addElement(format);
        impactForm.addElement(impactDate);
        impactForm.addElement(tbi);
      } 
    } 
    impactForm.addElement(new FormHidden("cmd", command, true));
    context.putDelivery("Selection", selection);
    context.putDelivery("Form", impactForm);
    return context.includeJSP("impactDate-editor.jsp");
  }
  
  private boolean impactEditorAdd(Dispatcher dispatcher, Context context, String command) {
    Form impactForm = new Form(this.application, "impactForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    User user = (User)context.getSession().getAttribute("user");
    Selection selection = (Selection)context.getSessionValue("Selection");
    Vector impactDates = null;
    impactDates = selection.getImpactDates();
    if (impactDates == null) {
      impactDates = new Vector();
      ImpactDate temp = new ImpactDate();
      temp.setSelectionID(selection.getSelectionID());
      impactDates.add(temp);
      selection.setImpactDates(impactDates);
    } else {
      ImpactDate temp = new ImpactDate();
      temp.setSelectionID(selection.getSelectionID());
      impactDates.add(temp);
      selection.setImpactDates(impactDates);
    } 
    if (selection != null && selection.getImpactDates() != null)
      for (int j = 0; j < impactDates.size(); j++) {
        ImpactDate impact = (ImpactDate)impactDates.get(j);
        FormDropDownMenu format = MilestoneHelper.getLookupDropDown("format" + j, Cache.getFormats(), impact.getFormat(), true, true);
        FormDateField impactDate = new FormDateField("impactDate" + j, MilestoneHelper.getFormatedDate(impact.getImpactDate()), false, 10);
        impactDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
        FormCheckBox tbi = new FormCheckBox("tbi" + j, "", false, false);
        tbi.setChecked(impact.getTbi());
        impactForm.addElement(format);
        impactForm.addElement(impactDate);
        impactForm.addElement(tbi);
      }  
    impactForm.setValues(context);
    impactForm.addElement(new FormHidden("cmd", command, true));
    context.putSessionValue("Selection", selection);
    context.putDelivery("Form", impactForm);
    return context.includeJSP("impactDate-editor.jsp");
  }
  
  private boolean impactEditorDelete(Dispatcher dispatcher, Context context, String command) {
    Form impactForm = new Form(this.application, "impactForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    User user = (User)context.getSession().getAttribute("user");
    Selection selection = (Selection)context.getSessionValue("Selection");
    Vector impactDates = null;
    int impactDateId = -1;
    if (context.getRequestValue("impactDateID") != null)
      impactDateId = Integer.parseInt(context.getRequestValue("impactDateID")); 
    impactDates = selection.getImpactDates();
    if (selection != null && selection.getImpactDates() != null) {
      if (impactDateId > -1)
        impactDates.remove(impactDateId); 
      selection.setImpactDates(impactDates);
      for (int j = 0; j < impactDates.size(); j++) {
        ImpactDate impact = (ImpactDate)impactDates.get(j);
        FormDropDownMenu format = MilestoneHelper.getLookupDropDown("format" + j, Cache.getFormats(), impact.getFormat(), true, true);
        FormDateField impactDate = new FormDateField("impactDate" + j, MilestoneHelper.getFormatedDate(impact.getImpactDate()), false, 10);
        impactDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
        FormCheckBox tbi = new FormCheckBox("tbi" + j, "", false, false);
        tbi.setChecked(impact.getTbi());
        impactForm.addElement(format);
        impactForm.addElement(impactDate);
        impactForm.addElement(tbi);
      } 
    } 
    impactForm.setValues(context);
    impactForm.addElement(new FormHidden("cmd", command, true));
    context.putSessionValue("Selection", selection);
    context.putDelivery("Form", impactForm);
    return context.includeJSP("impactDate-editor.jsp");
  }
  
  private boolean impactEditorSave(Dispatcher dispatcher, Context context, String command) {
    Form impactForm = new Form(this.application, "impactForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    User user = (User)context.getSession().getAttribute("user");
    Selection selection = (Selection)context.getSessionValue("Selection");
    Vector impactDates = null;
    impactDates = selection.getImpactDates();
    if (selection != null && selection.getImpactDates() != null) {
      for (int j = 0; j < impactDates.size(); j++) {
        ImpactDate impact = (ImpactDate)impactDates.get(j);
        FormDropDownMenu format = MilestoneHelper.getLookupDropDown("format" + j, Cache.getFormats(), "", true, true);
        FormDateField impactDate = new FormDateField("impactDate" + j, "", false, 10);
        impactDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
        FormCheckBox tbi = new FormCheckBox("tbi" + j, "", false, false);
        impactForm.addElement(format);
        impactForm.addElement(impactDate);
        impactForm.addElement(tbi);
      } 
      impactForm.setValues(context);
      Vector newImpactDates = new Vector();
      for (int j = 0; j < impactDates.size(); j++) {
        ImpactDate impact = (ImpactDate)impactDates.get(j);
        impact.setFormat(impactForm.getStringValue("format" + j));
        impact.setImpactDate(MilestoneHelper.getDate(impactForm.getStringValue("impactDate" + j)));
        impact.setTbi(((FormCheckBox)impactForm.getElement("tbi" + j)).isChecked());
        newImpactDates.add(impact);
      } 
      selection.setImpactDates(newImpactDates);
      if (selection.getSelectionID() > 0)
        SelectionManager.getInstance().saveSelection(selection, user); 
    } 
    impactForm.addElement(new FormHidden("cmd", command, true));
    context.putSessionValue("Selection", selection);
    context.putDelivery("Form", impactForm);
    return true;
  }
  
  private boolean impactEditorCancel(Dispatcher dispatcher, Context context, String command) {
    Form impactForm = new Form(this.application, "impactForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    User user = (User)context.getSession().getAttribute("user");
    Selection sessionSelection = (Selection)context.getSessionValue("Selection");
    Selection selection = null;
    if (sessionSelection.getSelectionID() < 0) {
      selection = sessionSelection;
    } else {
      selection = SelectionManager.getInstance().getSelectionHeader(sessionSelection.getSelectionID());
    } 
    Vector impactDates = null;
    if (selection != null) {
      impactDates = selection.getImpactDates();
      if (selection != null && selection.getImpactDates() != null) {
        Vector newImpactDates = new Vector();
        for (int j = 0; j < impactDates.size(); j++) {
          ImpactDate impact = (ImpactDate)impactDates.get(j);
          if (sessionSelection.getSelectionID() >= 0 || 
            impact.getImpactDate() != null) {
            FormDropDownMenu format = MilestoneHelper.getLookupDropDown("format" + j, Cache.getFormats(), impact.getFormat(), true, true);
            FormDateField impactDate = new FormDateField("impactDate" + j, MilestoneHelper.getFormatedDate(impact.getImpactDate()), false, 10);
            impactDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
            FormCheckBox tbi = new FormCheckBox("tbi" + j, "", false, false);
            tbi.setChecked(impact.getTbi());
            impactForm.addElement(format);
            impactForm.addElement(impactDate);
            impactForm.addElement(tbi);
            newImpactDates.add(impact);
          } 
        } 
        selection.setImpactDates(newImpactDates);
      } 
    } 
    impactForm.addElement(new FormHidden("cmd", command, true));
    context.putSessionValue("Selection", selection);
    context.putDelivery("Form", impactForm);
    return true;
  }
  
  private boolean search(Dispatcher dispatcher, Context context, String command) {
    String isSelectionSearchResults = context.getParameter("isSelectionSearchResults");
    if (isSelectionSearchResults == null || !isSelectionSearchResults.equals("true")) {
      Notepad notepad = MilestoneHelper.getNotepadFromSession(0, context);
      notepad.setAllContents(null);
      notepad.setSelected(null);
      notepad.setMaxRecords(225);
      Form form = new Form(this.application, "selectionForm", 
          this.application.getInfrastructure().getServletURL(), 
          "POST");
      Vector companies = MilestoneHelper.getUserCompanies(context);
      addSelectionSearchElements(context, null, form, companies, false);
      form.setValues(context);
      SelectionManager.getInstance().setSelectionNotepadQuery(context, (
          (User)context.getSessionValue("user")).getUserId(), notepad, form);
    } 
    if (command.equals("selection-search")) {
      dispatcher.redispatch(context, "selection-editor");
    } else {
      dispatcher.redispatch(context, "selection-manufacturing-editor");
    } 
    return true;
  }
  
  private boolean sort(Dispatcher dispatcher, Context context, String command) {
    Notepad notepad = MilestoneHelper.getNotepadFromSession(0, context);
    NotepadSortOrder.getNotepadSortOrderFromSession(context).sortHelper(dispatcher, context, notepad);
    if (command.equals("selection-sort")) {
      dispatcher.redispatch(context, "selection-editor");
    } else {
      dispatcher.redispatch(context, "selection-manufacturing-editor");
    } 
    return true;
  }
  
  private boolean edit(Dispatcher dispatcher, Context context, String command) {
    Selection selection = new Selection();
    User user = (User)context.getSessionValue("user");
    context.removeSessionValue("searchResults");
    context.removeSessionValue("selectionScreenType");
    Notepad notepad = MilestoneHelper.getNotepadFromSession(0, context);
    if (notepad == null) {
      notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 0);
    } else {
      notepad.setAllContents(null);
      notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 0);
    } 
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    selection = MilestoneHelper.getScreenSelection(context);
    context.putSessionValue("impactSaveVisible", "false");
    Form form = null;
    if (selection != null) {
      context.putSessionValue("Selection", selection);
      context.putDelivery("isOkToClose", new Boolean(SelectionManager.getInstance().isSelectionOkToClose(context)));
      if (selection.getIsDigital()) {
        form = buildDigitalForm(context, selection, command);
        context.putDelivery("Form", form);
        return context.includeJSP("digital-selection-editor.jsp");
      } 
      form = buildForm(context, selection, command);
      context.putDelivery("Form", form);
      return context.includeJSP("selection-editor.jsp");
    } 
    return goToBlank(context, form, user);
  }
  
  private boolean editSave(Dispatcher dispatcher, Context context, String command) {
    context.removeSessionValue("searchResults");
    context.removeSessionValue("selectionScreenType");
    Selection selection = (Selection)context.getSessionValue("Selection");
    Notepad notepad = SelectionManager.getInstance().getSelectionNotepad(context, ((User)context.getSessionValue("user")).getUserId(), 0);
    int selectionId = -1;
    try {
      selectionId = selection.getSelectionID();
    } catch (NullPointerException nullPointerException) {}
    boolean isNewSelection = (selectionId < 1);
    Form form = null;
    if (isNewSelection) {
      if (selection.getIsDigital()) {
        form = buildNewDigitalForm(context, selection, command);
      } else {
        form = buildNewForm(context, selection, command);
      } 
    } else if (selection.getIsDigital()) {
      form = buildDigitalForm(context, selection, command);
    } else {
      form = buildForm(context, selection, command);
    } 
    if (isNewSelection || (!isNewSelection && SelectionManager.getInstance().isTimestampValid(selection))) {
      form.setValues(context);
      String streetDateString = form.getStringValue("streetDate");
      String internationalDateString = form.getStringValue("internationalDate");
      String impactDateString = form.getStringValue("impactdate");
      String selectionStatus = form.getStringValue("status");
      String holdReason = form.getStringValue("holdReason");
      String packagingHelper = form.getStringValue("PackagingHelper");
      String territoryHelper = form.getStringValue("TerritoryHelper");
      String projectId = form.getStringValue("projectId");
      String prefix = form.getStringValue("prefix");
      String titleId = "";
      String selectionNo = form.getStringValue("selectionNo");
      String upc = form.getStringValue("UPC");
      String artistFirstName = form.getStringValue("artistFirstName");
      String artistLastName = form.getStringValue("artistLastName");
      String artist = "";
      if (artistFirstName == null || artistFirstName.equals("")) {
        artist = artistLastName;
      } else if (artistLastName == null || artistLastName.equals("")) {
        artist = artistFirstName;
      } else {
        artist = String.valueOf(artistFirstName) + " " + artistLastName;
      } 
      String title = form.getStringValue("title");
      String sideATitle = "";
      if (form.getStringValue("sideATitle") != null)
        sideATitle = form.getStringValue("sideATitle"); 
      String sideBTitle = form.getStringValue("sideBTitle");
      String releaseType = form.getStringValue("releaseType");
      String configuration = form.getStringValue("configuration");
      String subConfiguration = form.getStringValue("subConfiguration");
      String productLine = form.getStringValue("productLine");
      String environment = form.getStringValue("environment");
      String company = form.getStringValue("company");
      String division = form.getStringValue("division");
      String label = form.getStringValue("label");
      String sellCode = form.getStringValue("priceCode");
      String sellCodeDPC = form.getStringValue("priceCodeDPC");
      String genre = form.getStringValue("genre");
      String pkg = form.getStringValue("package");
      String territory = form.getStringValue("territory");
      String contactlist = form.getStringValue("contactlist");
      String otherContact = form.getStringValue("contact");
      String productGroupCode = form.getStringValue("productGroupCode");
      String comments = form.getStringValue("comments");
      String priceCode = form.getStringValue("priceCode");
      int numOfUnits = 0;
      try {
        numOfUnits = Integer.parseInt(form.getStringValue("numOfUnits"));
      } catch (Exception exception) {}
      String digitalRlsDateString = form.getStringValue("digitalDate");
      String operCompany = form.getStringValue("opercompany");
      String superLabel = form.getStringValue("superlabel");
      String subLabel = form.getStringValue("sublabel");
      String configCode = form.getStringValue("configcode");
      String soundscanGrp = form.getStringValue("soundscan");
      String imprint = form.getStringValue("imprint");
      if (selection.getSellCode() != null && !selection.getSellCode().equalsIgnoreCase(sellCode))
        selection.setPriceCode(SelectionManager.getInstance().getPriceCode(sellCode)); 
      if (selection.getSellCodeDPC() != null && !selection.getSellCodeDPC().equalsIgnoreCase(sellCodeDPC))
        selection.setPriceCodeDPC(SelectionManager.getInstance().getPriceCode(sellCodeDPC)); 
      String releasingFamily = form.getStringValue("releasingFamily");
      selection.setNumberOfUnits(numOfUnits);
      selection.setSelectionNo(selectionNo);
      selection.setProjectID(projectId);
      selection.setTitleID(titleId);
      selection.setTitle(title);
      selection.setArtistFirstName(artistFirstName);
      selection.setArtistLastName(artistLastName);
      selection.setArtist(artist);
      selection.setASide(sideATitle);
      selection.setBSide(sideBTitle);
      selection.setProductCategory((ProductCategory)SelectionManager.getLookupObject(productLine, Cache.getProductCategories()));
      selection.setReleaseType((ReleaseType)SelectionManager.getLookupObject(releaseType, Cache.getReleaseTypes()));
      selection.setSelectionConfig(SelectionManager.getSelectionConfigObject(configuration, Cache.getSelectionConfigs()));
      selection.setSelectionSubConfig(SelectionManager.getSelectionSubConfigObject(subConfiguration, selection.getSelectionConfig()));
      selection.setUpc(upc);
      selection.setSellCode(sellCode);
      selection.setSellCodeDPC(sellCodeDPC);
      selection.setGenre((Genre)SelectionManager.getLookupObject(genre, Cache.getMusicTypes()));
      System.out.println(">>>>>>>>>>>>>>>>>>>>>>> Company: " + company);
      System.out.println(">>>>>>>>>>>>>>>>>>>>>>> enviroment: " + environment);
      selection.setEnvironment((Environment)MilestoneHelper.getStructureObject(Integer.parseInt(environment)));
      selection.setCompany((Company)MilestoneHelper.getStructureObject(Integer.parseInt(company)));
      selection.setDivision((Division)MilestoneHelper.getStructureObject(Integer.parseInt(division)));
      selection.setLabel((Label)MilestoneHelper.getStructureObject(Integer.parseInt(label)));
      try {
        selection.setReleaseFamilyId(Integer.parseInt(releasingFamily));
      } catch (Exception exception) {}
      Calendar oldStreetDate = null;
      if (selection.getIsDigital()) {
        oldStreetDate = selection.getDigitalRlsDate();
      } else {
        oldStreetDate = selection.getStreetDate();
      } 
      boolean isStreetDateDifferent = false;
      try {
        Calendar streetDate = null;
        if (selection.getIsDigital()) {
          streetDate = MilestoneHelper.getDate(digitalRlsDateString);
        } else {
          streetDate = MilestoneHelper.getDate(streetDateString);
        } 
        String oldStreetDateString = MilestoneHelper.getFormatedDate(oldStreetDate);
        String newStreetDateString = MilestoneHelper.getFormatedDate(streetDate);
        if ((oldStreetDate == null && streetDate != null) || (oldStreetDate != null && !oldStreetDateString.equals(newStreetDateString))) {
          selection.setLastStreetUpdateDate(Calendar.getInstance());
          isStreetDateDifferent = true;
        } 
        selection.setDigitalRlsDate(MilestoneHelper.getDate(digitalRlsDateString));
        selection.setDigitalRlsDateString(MilestoneHelper.getFormatedDate(MilestoneHelper.getDate(digitalRlsDateString)));
        selection.setStreetDate(MilestoneHelper.getDate(streetDateString));
        selection.setStreetDateString(MilestoneHelper.getFormatedDate(MilestoneHelper.getDate(streetDateString)));
      } catch (Exception e) {
        selection.setStreetDate(null);
        selection.setDigitalRlsDate(null);
      } 
      boolean isScheduleApplied = false;
      isScheduleApplied = SelectionManager.getInstance().isScheduleApplied(selection);
      Calendar internationalDate = Calendar.getInstance();
      try {
        internationalDate = MilestoneHelper.getDate(internationalDateString);
        selection.setInternationalDate(internationalDate);
      } catch (Exception e) {
        selection.setInternationalDate(null);
      } 
      selection.setOtherContact(otherContact);
      int contactUser = 0;
      if (contactlist != null && !contactlist.equals(""))
        contactUser = Integer.parseInt(contactlist); 
      selection.setLabelContact(UserManager.getInstance().getUser(contactUser));
      selection.setSelectionStatus((SelectionStatus)SelectionManager.getLookupObject(selectionStatus, Cache.getSelectionStatusList()));
      selection.setHoldReason(holdReason);
      selection.setComments(comments);
      selection.setSelectionPackaging(pkg);
      selection.setSelectionTerritory(territory);
      selection.setPrefixID((PrefixCode)SelectionManager.getLookupObject(prefix, Cache.getPrefixCodes()));
      selection.setPressAndDistribution(((FormCheckBox)form.getElement("pdIndicator")).isChecked());
      selection.setSpecialPackaging(((FormCheckBox)form.getElement("specialPkgIndicator")).isChecked());
      selection.setHoldSelection(((FormCheckBox)form.getElement("holdIndicator")).isChecked());
      if (!selection.getIsDigital())
        selection.setNoDigitalRelease(((FormCheckBox)form.getElement("noDigitalRelease")).isChecked()); 
      selection.setParentalGuidance(((FormCheckBox)form.getElement("parentalIndicator")).isChecked());
      Calendar impactDate = Calendar.getInstance();
      try {
        impactDate = MilestoneHelper.getDate(impactDateString);
        selection.setImpactDate(impactDate);
      } catch (Exception e) {
        selection.setImpactDate(null);
      } 
      Calendar digitalRlsDate = Calendar.getInstance();
      try {
        digitalRlsDate = MilestoneHelper.getDate(digitalRlsDateString);
        selection.setDigitalRlsDate(digitalRlsDate);
      } catch (Exception e) {
        selection.setDigitalRlsDate(null);
      } 
      selection.setInternationalFlag(((FormCheckBox)form.getElement("intlFlag")).isChecked());
      selection.setOperCompany(operCompany);
      selection.setSuperLabel(superLabel);
      selection.setSubLabel(subLabel);
      selection.setConfigCode(configCode);
      selection.setSoundScanGrp(soundscanGrp);
      selection.setImprint(imprint);
      if (selection.getIsDigital()) {
        String newBundleString = form.getStringValue("newBundle");
        if (newBundleString.equalsIgnoreCase("true")) {
          selection.setNewBundleFlag(true);
        } else {
          selection.setNewBundleFlag(false);
        } 
        String specialInstructions = form.getStringValue("specialInstructions");
        selection.setSpecialInstructions(specialInstructions);
        selection.setPriority(((FormCheckBox)form.getElement("priority")).isChecked());
      } 
      String gridNumber = form.getStringValue("gridNumber");
      selection.setGridNumber(gridNumber);
      boolean isPNR = false;
      if (context.getParameter("generateSelection") != null && (
        context.getParameter("generateSelection").equalsIgnoreCase("LPNG") || 
        context.getParameter("generateSelection").equalsIgnoreCase("TPNG")))
        isPNR = true; 
      String strResponse = "";
      strResponse = applyBusinessRules(form, context, selection);
      if (!strResponse.trim().equals("")) {
        context.putDelivery("AlertMessage", strResponse.trim());
        form.setValues(context);
        User user = (User)context.getSession().getAttribute("user");
        int userId = user.getUserId();
        int secureLevel = getSelectionPermissions(selection, user);
        setButtonVisibilities(selection, user, context, secureLevel, "new");
        FormHidden opCo = (FormHidden)form.getElement("opercompany");
        LookupObject oc = MilestoneHelper.getLookupObject(selection
            .getOperCompany(), Cache.getOperatingCompanies());
        String ocAbbr = "";
        String ocName = "";
        if (oc != null && oc.getAbbreviation() != null)
          ocAbbr = oc.getAbbreviation(); 
        if (oc != null && oc.getName() != null)
          ocName = oc.getName(); 
        opCo.setDisplayName(String.valueOf(ocAbbr) + ":" + ocName);
        if (ocAbbr.equals("ZZ"))
          opCo.setDisplayName(ocAbbr); 
        FormHidden projectID = (FormHidden)form.getElement("projectId");
        projectID.setDisplayName(String.valueOf(selection.getProjectID()));
        context.putDelivery("releaseWeek", MilestoneHelper.getReleaseWeekString(selection));
        Vector releaseFamilies = ReleasingFamily.getReleasingFamilies(userId, selection.getEnvironment().getParentFamily().getStructureID(), context);
        FormDropDownMenu myReleasingFamily = MilestoneHelper.getReleasingFamilyDropDown("releasingFamily", Integer.toString(selection.getReleaseFamilyId()), releaseFamilies, true, selection);
        form.removeElement("releasingFamily");
        myReleasingFamily.addFormEvent("onChange", "ReleasingFamilyChange(this)");
        form.addElement(myReleasingFamily);
        String envId = "";
        String envName = "";
        if (selection.getEnvironment() != null) {
          envId = Integer.toString(selection.getEnvironment().getStructureID());
          envName = selection.getEnvironment().getName();
        } 
        FormHidden myEnv = new FormHidden("environment", envId, false);
        myEnv.setDisplayName(envName);
        form.removeElement("environment");
        form.addElement(myEnv);
        String companyId = "";
        String companyName = "";
        if (selection.getCompany() != null) {
          companyId = Integer.toString(selection.getCompany().getStructureID());
          companyName = selection.getCompany().getName();
        } 
        FormHidden myCcompany = new FormHidden("company", companyId, false);
        myCcompany.setTabIndex(15);
        myCcompany.setDisplayName(companyName);
        myCcompany.addFormEvent("onClick", "return(clickCompany(this))");
        form.removeElement("company");
        form.addElement(myCcompany);
        String divisionId = "";
        String divisionName = "";
        if (selection.getDivision() != null) {
          divisionId = Integer.toString(selection.getDivision().getStructureID());
          divisionName = selection.getDivision().getName();
        } else {
          divisionId = "";
        } 
        FormHidden myDivision = new FormHidden("division", divisionId, false);
        myDivision.setTabIndex(16);
        myDivision.setDisplayName(divisionName);
        myDivision.addFormEvent("onChange", "return(clickDivision(this))");
        form.removeElement("division");
        form.addElement(myDivision);
        String labelId = "";
        String labelName = "";
        if (selection.getLabel() != null) {
          labelId = Integer.toString(selection.getLabel().getStructureID());
          labelName = selection.getLabel().getName();
        } else {
          labelId = "";
        } 
        FormHidden myLabel = new FormHidden("label", labelId, false);
        myLabel.setTabIndex(17);
        myLabel.setDisplayName(labelName);
        form.removeElement("label");
        form.addElement(myLabel);
        String subConfigValue = "";
        if (selection.getSelectionSubConfig() != null)
          subConfigValue = selection.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation(); 
        FormDropDownMenu mySubConfiguration = MilestoneHelper.getSelectionSubConfigurationDropDown("subConfiguration", selection.getSelectionConfig(), subConfigValue, true);
        mySubConfiguration.addFormEvent("onChange", "isSubConfigParent(this.selectedIndex)");
        mySubConfiguration.setTabIndex(30);
        mySubConfiguration.setClassName("ctrlMedium");
        form.removeElement("subConfiguration");
        form.addElement(mySubConfiguration);
        context.putDelivery("Form", form);
        if (selection.getIsDigital())
          return context.includeJSP("digital-selection-editor.jsp"); 
        return context.includeJSP("selection-editor.jsp");
      } 
      if (!form.isUnchanged()) {
        FormValidation formValidation = form.validate();
        if (formValidation.isGood()) {
          User user = (User)context.getSessionValue("user");
          System.out.println(">>>>>>>>>>>>>>>>>>>>>>> Company: " + selection.getCompanyId());
          System.out.println(">>>>>>>>>>>>>>>>>>>>>>> enviroment: " + selection.getEnvironmentId());
          Selection savedSelection = SelectionManager.getInstance().saveSelection(selection, user);
          if (!form.getElement("label").getStartingValue().equalsIgnoreCase(form.getElement("label").getStringValue()) || 
            !form.getElement("releasingFamily").getStartingValue().equalsIgnoreCase(form.getElement("releasingFamily").getStringValue()))
            Cache.flushUsedLabels(); 
          FormElement lastUpdated = form.getElement("lastupdateddate");
          lastUpdated.setValue(MilestoneHelper.getLongDate(savedSelection.getLastUpdateDate()));
          notepad.setAllContents(null);
          if (isNewSelection)
            notepad.newSelectedReset(); 
          notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 0);
          notepad.setSelected(savedSelection);
          selection = (Selection)notepad.validateSelected();
          if (selection == null && notepad.getMaxRecords() < notepad.getTotalRecords() && notepad.getMaxRecords() > 0) {
            notepad.setMaxRecords(0);
            notepad.setAllContents(null);
            notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 0);
            notepad.setSelected(savedSelection);
            selection = (Selection)notepad.validateSelected();
          } 
          context.putSessionValue("Selection", selection);
          if (form.getStringValue("UPC") != null) {
            String upcStripped = MilestoneHelper_2.reformat_UPC_SSG_SGC_forSave(form.getStringValue("UPC"), "UPC", savedSelection.getIsDigital(), true);
            form.getElement("UPC").setValue(upcStripped);
          } 
          if (form.getStringValue("soundscan") != null) {
            String ssgStripped = MilestoneHelper_2.reformat_UPC_SSG_SGC_forSave(form.getStringValue("soundscan"), "SSG", savedSelection.getIsDigital(), true);
            form.getElement("soundscan").setValue(ssgStripped);
          } 
          boolean isPfmChange = false;
          boolean isBomChange = false;
          if (!form.getStringValue("selectionNo").equalsIgnoreCase(form.getElement("selectionNo").getStartingValue())) {
            isPfmChange = true;
            isBomChange = true;
          } 
          String prefixStartingValue = "-1";
          if (!form.getElement("prefix").getStartingValue().equals(""))
            prefixStartingValue = form.getElement("prefix").getStartingValue(); 
          if (!form.getStringValue("prefix").equalsIgnoreCase(prefixStartingValue)) {
            isPfmChange = true;
            isBomChange = true;
          } 
          if (!form.getStringValue("UPC").equalsIgnoreCase(form.getElement("UPC").getStartingValue())) {
            isPfmChange = true;
            isBomChange = true;
          } 
          if (!savedSelection.getIsDigital() && !form.getStringValue("titleId").equalsIgnoreCase(form.getElement("titleId").getStartingValue()))
            isPfmChange = true; 
          if (!savedSelection.getIsDigital() && !form.getStringValue("streetDate").equalsIgnoreCase(form.getElement("streetDate").getStartingValue())) {
            isPfmChange = true;
            isBomChange = true;
          } 
          if (savedSelection.getIsDigital() && !form.getStringValue("digitalDate").equalsIgnoreCase(form.getElement("digitalDate").getStartingValue())) {
            isPfmChange = true;
            isBomChange = true;
          } 
          if (!form.getStringValue("status").equalsIgnoreCase(form.getElement("status").getStartingValue()) && !form.getStringValue("status").equalsIgnoreCase("Closed")) {
            isPfmChange = true;
            isBomChange = true;
          } 
          if (!form.getStringValue("status").equalsIgnoreCase(form.getElement("status").getStartingValue()) && form.getStringValue("status").equalsIgnoreCase("Closed")) {
            Schedule schedule = null;
            if (savedSelection != null)
              schedule = ScheduleManager.getInstance().getSchedule(savedSelection.getSelectionID()); 
            if (schedule != null) {
              Vector tasks = schedule.getTasks();
              if (tasks != null) {
                ScheduledTask task = null;
                Calendar labelCmpDt = MilestoneHelper.getDate("9/9/99");
                for (int i = 0; i < tasks.size(); i++) {
                  task = (ScheduledTask)tasks.get(i);
                  if (task.getCompletionDate() == null && !MilestoneHelper.isUml(task) && !MilestoneHelper.isEcommerce(task))
                    ScheduleManager.getInstance().UpdateCompletionDate(task.releaseID, task.taskID, user.userId, labelCmpDt); 
                  task = null;
                } 
              } 
            } 
          } 
          if (!form.getStringValue("projectId").equalsIgnoreCase(form.getElement("projectId").getStartingValue()))
            isPfmChange = true; 
          if (form.getStringValue("priceCode") != null) {
            if (form.getElement("priceCode").getStartingValue().equals(""))
              form.getElement("priceCode").setStartingValue("-1"); 
            if (!form.getStringValue("priceCode").equalsIgnoreCase(form.getElement("priceCode").getStartingValue()))
              isPfmChange = true; 
          } 
          if (form.getStringValue("priceCodeDPC") != null) {
            if (form.getElement("priceCodeDPC").getStartingValue().equals(""))
              form.getElement("priceCodeDPC").setStartingValue("-1"); 
            if (!form.getStringValue("priceCodeDPC").equalsIgnoreCase(form.getElement("priceCodeDPC").getStartingValue()))
              isPfmChange = true; 
          } 
          if (((FormCheckBox)form.getElement("parentalIndicator")).isChecked() != ((FormCheckBox)form.getElement("parentalIndicator")).getStartingChecked())
            isPfmChange = true; 
          if (!form.getStringValue("title").equalsIgnoreCase(form.getElement("title").getStartingValue())) {
            isPfmChange = true;
            isBomChange = true;
          } 
          if (!form.getStringValue("artistFirstName").equalsIgnoreCase(form.getElement("artistFirstName").getStartingValue())) {
            isPfmChange = true;
            isBomChange = true;
          } 
          if (!form.getStringValue("artistLastName").equalsIgnoreCase(form.getElement("artistLastName").getStartingValue())) {
            isPfmChange = true;
            isBomChange = true;
          } 
          if (!form.getStringValue("opercompany").equalsIgnoreCase(form.getElement("opercompany").getStartingValue()))
            isPfmChange = true; 
          if (!form.getStringValue("superlabel").equalsIgnoreCase(form.getElement("superlabel").getStartingValue()))
            isPfmChange = true; 
          if (!form.getStringValue("sublabel").equalsIgnoreCase(form.getElement("sublabel").getStartingValue()))
            isPfmChange = true; 
          if (!form.getStringValue("configcode").equalsIgnoreCase(form.getElement("configcode").getStartingValue()))
            isPfmChange = true; 
          if (!form.getStringValue("soundscan").equalsIgnoreCase(form.getElement("soundscan").getStartingValue()))
            isPfmChange = true; 
          if (!savedSelection.getIsDigital() && ((FormCheckBox)form.getElement("parentalIndicator")).isChecked() != ((FormCheckBox)form.getElement("parentalIndicator")).getStartingChecked())
            isPfmChange = true; 
          if (!form.getStringValue("numOfUnits").equalsIgnoreCase(form.getElement("numOfUnits").getStartingValue())) {
            isPfmChange = true;
            isBomChange = true;
          } 
          if (!form.getStringValue("label").equalsIgnoreCase(form.getElement("label").getStartingValue()))
            isBomChange = true; 
          if (!form.getStringValue("company").equalsIgnoreCase(form.getElement("company").getStartingValue()))
            isBomChange = true; 
          if (!form.getStringValue("releaseType").equalsIgnoreCase(form.getElement("releaseType").getStartingValue()))
            isBomChange = true; 
          if (form.getStringValue("genre").equals("-1"))
            form.getElement("genre").setValue(""); 
          if (!form.getStringValue("genre").equalsIgnoreCase(form.getElement("genre").getStartingValue()))
            isPfmChange = true; 
          Pfm pfm = null;
          Bom bom = null;
          boolean isBOMFinal = false;
          boolean isPFMFinal = false;
          if (savedSelection != null) {
            pfm = SelectionManager.getInstance().getPfm(savedSelection.getSelectionID());
            bom = SelectionManager.getInstance().getBom(savedSelection);
            isBOMFinal = (bom != null && bom.getPrintOption() != null && bom.getPrintOption().equalsIgnoreCase("Final"));
            isPFMFinal = (pfm != null && pfm.getPrintOption() != null && pfm.getPrintOption().equalsIgnoreCase("Final"));
            int igaId = MilestoneHelper.getStructureId("IGA", 1);
            int interscopeId = MilestoneHelper.getStructureId("Interscope", 5);
            int geffenId = MilestoneHelper.getStructureId("Geffen", 5);
            int amId = MilestoneHelper.getStructureId("A&M", 5);
            boolean isBomChangeSave = isBomChange;
            context.putSessionValue("pfmBomSelection", savedSelection);
          } 
          if (!isNewSelection && (isPfmChange || isBomChange) && (isPFMFinal || isBOMFinal)) {
            if (isPFMFinal && isPfmChange)
              context.putDelivery("pfmSend", "true"); 
            if (isBOMFinal && isBomChange)
              context.putDelivery("bomSend", "true"); 
            if (isNewSelection || (
              isStreetDateDifferent && isScheduleApplied) || 
              isPNR) {
              context.putSessionValue("sendToSchedule", "true");
              if (isStreetDateDifferent && isScheduleApplied) {
                context.putSessionValue("recalc-date", "true");
                context.putSessionValue("Selection", savedSelection);
              } 
            } 
          } else if (isNewSelection || (isStreetDateDifferent && isScheduleApplied) || isPNR) {
            if (isStreetDateDifferent && isScheduleApplied) {
              context.putDelivery("recalc-date", "true");
              context.putSessionValue("Selection", savedSelection);
            } 
            dispatcher.redispatch(context, "schedule-editor");
            return true;
          } 
          if (selection == null) {
            context.putDelivery("BlankAlertMessage", " Your changes have been saved successfully.  <br>The changes made to the selection cause the selection to no longer be part of the notepad.  <br>To view this selection, modify notepad search criteria according to the changes made.  <br>Otherwise, choose another selection from the notepad on the left.");
            return goToBlank(context, form, user);
          } 
          if (selection == savedSelection) {
            form = buildForm(context, selection, command);
          } else {
            edit(dispatcher, context, command);
            return true;
          } 
        } else {
          context.putDelivery("FormValidation", formValidation);
        } 
      } 
      form.addElement(new FormHidden("OrderBy", "", true));
      context.putDelivery("Form", form);
      if (isNewSelection) {
        if (selection.getIsDigital())
          return context.includeJSP("digital-selection-editor.jsp"); 
        return context.includeJSP("selection-editor.jsp");
      } 
      return edit(dispatcher, context, command);
    } 
    context.putDelivery("AlertMessage", "The record is unavailable for editing at this time.  Please refresh your screen and edit it at a later time.");
    context.putDelivery("Form", form);
    if (selection.getIsDigital())
      return context.includeJSP("digital-selection-editor.jsp"); 
    return context.includeJSP("selection-editor.jsp");
  }
  
  public static Vector filterSelectionActiveCompanies(int userId, Vector companies) {
    Vector activeResult = new Vector();
    boolean resultCompany = true;
    HashMap corpHashMap = MilestoneHelper.buildActiveCorporateStructureHashMap();
    if (companies != null && companies.size() > 0)
      for (int i = 0; i < companies.size(); i++) {
        Company company = (Company)companies.get(i);
        String name = company.getName();
        int structureId = company.getStructureID();
        resultCompany = !corpHashMap.containsKey(new Integer(structureId));
        if (!name.equalsIgnoreCase("UML") && 
          !name.equalsIgnoreCase("Enterprise") && resultCompany)
          activeResult.add(company); 
      }  
    corpHashMap = null;
    return activeResult;
  }
  
  private boolean editDelete(Dispatcher dispatcher, Context context, String command) {
    context.removeSessionValue("searchResults");
    context.removeSessionValue("selectionScreenType");
    User user = (User)context.getSessionValue("user");
    Selection selection = (Selection)context.getSessionValue("Selection");
    Notepad notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 0);
    if (selection.getSelectionID() > 0) {
      boolean isDeletable = false;
      isDeletable = SelectionManager.getInstance().deleteSelection(selection, user);
      if (isDeletable) {
        context.removeSessionValue("Selection");
        Vector contents = new Vector();
        notepad.setAllContents(null);
        notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 0);
        notepad.setSelected(null);
        selection = MilestoneHelper.getScreenSelection(context);
        Cache.flushUsedLabels();
        if (selection != null) {
          context.putSessionValue("Selection", selection);
        } else {
          Form form = null;
          form = new Form(this.application, "selectionForm", this.application.getInfrastructure().getServletURL(), "POST");
          form.addElement(new FormHidden("cmd", "selection-editor", true));
          form.addElement(new FormHidden("OrderBy", "", true));
          if (context.getSessionValue("NOTEPAD_SELECTION_VISIBLE") != null)
            context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_SELECTION_VISIBLE")); 
          Vector companies = MilestoneHelper.getUserCompanies(context);
          addSelectionSearchElements(context, null, form, companies, true);
          context.putDelivery("Form", form);
          return context.includeJSP("blank-selection-editor.jsp");
        } 
      } else {
        String alert = "Cannot delete this record.\\n There are other records that are dependent on it.";
        context.putDelivery("alert-box", alert);
      } 
    } 
    if (selection.getIsDigital()) {
      Form form = buildDigitalForm(context, selection, command);
      context.putDelivery("Form", form);
      return context.includeJSP("digital-selection-editor.jsp");
    } 
    Form form = buildForm(context, selection, command);
    context.putDelivery("Form", form);
    return context.includeJSP("selection-editor.jsp");
  }
  
  private boolean getReleasingFamilies(Dispatcher dispatcher, Context context, String command) {
    Selection selection = MilestoneHelper.getScreenSelection(context);
    User user = (User)context.getSession().getAttribute("user");
    int userId = user.getUserId();
    int environmentID = -1;
    try {
      environmentID = Integer.parseInt(context.getRequestValue("eID"));
    } catch (Exception exception) {}
    Environment env = MilestoneHelper.getEnvironmentById(environmentID);
    int familyID = -1;
    if (env != null && env.getParentFamily() != null)
      familyID = env.getParentFamily().getStructureID(); 
    Vector releaseFamilies = ReleasingFamily.getReleasingFamilies(userId, familyID, context);
    context.putDelivery("releasing_family_list", releaseFamilies);
    return context.includeJSP("selection-get-releasing-families.jsp");
  }
  
  private boolean getSelectionSearchResults(Dispatcher dispatcher, Context context, String command) {
    User user = (User)context.getSessionValue("user");
    if (user != null)
      user.SS_searchInitiated = true; 
    if (user == null || user.getPreferences() == null || user.getPreferences().getSelectionPriorCriteria() != 1)
      context.putSessionValue("ResetSelectionSortOrder", "true"); 
    context.putSessionValue("searchElementsInit", "true");
    if (SelectionManager.getInstance().getSelectionSearchResults(this.application, context)) {
      Notepad notepad = MilestoneHelper.getNotepadFromSession(0, context);
      notepad.setAllContents(null);
      notepad.setSelected(null);
      notepad.setMaxRecords(225);
      String searchCommand = context.getParameter("selectionSearchCommand");
      context.putDelivery("selectionSearchResults", "true");
      user = (User)context.getSessionValue("user");
      if (user != null && user.getPreferences() != null)
        user.getPreferences().getSelectionPriorCriteria(); 
      if (searchCommand.equals("bom-search"))
        dispatcher.redispatch(context, "bom-editor"); 
      if (searchCommand.equals("pfm-search"))
        dispatcher.redispatch(context, "pfm-editor"); 
      if (searchCommand.equals("schedule-selection-release-search"))
        dispatcher.redispatch(context, "schedule-editor"); 
      if (searchCommand.equals("selection-search"))
        dispatcher.redispatch(context, "selection-editor"); 
      if (searchCommand.equals("selection-manufacturing-search"))
        dispatcher.redispatch(context, "selection-manufacturing-editor"); 
      return true;
    } 
    String searchCommand = context.getParameter("selectionSearchCommand");
    context.putDelivery("selectionSearchResults", "false");
    if (searchCommand.equals("bom-search"))
      dispatcher.redispatch(context, "bom-editor"); 
    if (searchCommand.equals("pfm-search"))
      dispatcher.redispatch(context, "pfm-editor"); 
    if (searchCommand.equals("schedule-selection-release-search"))
      dispatcher.redispatch(context, "schedule-editor"); 
    if (searchCommand.equals("selection-search"))
      dispatcher.redispatch(context, "selection-editor"); 
    if (searchCommand.equals("selection-manufacturing-search"))
      dispatcher.redispatch(context, "selection-manufacturing-editor"); 
    return context.includeJSP("selection-search-results.jsp");
  }
  
  private boolean editNew(Dispatcher dispatcher, Context context, String command) {
    String selectionType = "";
    selectionType = (String)context.getSessionValue("selectionScreenType");
    User user = (User)context.getSessionValue("user");
    Notepad notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 0);
    notepad.setAllContents(null);
    notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 0);
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Selection newSelection = new Selection();
    if (command.equalsIgnoreCase("selection-edit-new-digital") || command.equalsIgnoreCase("selection-edit-new-digital-archie-project"))
      newSelection.setIsDigital(true); 
    context.putSessionValue("Selection", newSelection);
    context.putSessionValue("impactSaveVisible", "true");
    Form form = null;
    if (command.equalsIgnoreCase("selection-edit-new-digital") || command.equalsIgnoreCase("selection-edit-new-digital-archie-project")) {
      form = buildNewDigitalForm(context, newSelection, command);
    } else {
      form = buildNewForm(context, newSelection, command);
    } 
    context.putDelivery("Form", form);
    if (command.equalsIgnoreCase("selection-edit-new-digital") || command.equalsIgnoreCase("selection-edit-new-digital-archie-project"))
      return context.includeJSP("digital-selection-editor.jsp"); 
    return context.includeJSP("selection-editor.jsp");
  }
  
  private boolean editCopy(Dispatcher dispatcher, Context context, String command) {
    context.removeSessionValue("searchResults");
    context.removeSessionValue("selectionScreenType");
    User user = (User)context.getSessionValue("user");
    Notepad notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 0);
    Selection targetSelection = MilestoneHelper.getScreenSelection(context);
    String oldSelectionNumber = "";
    if (targetSelection.getSelectionNo() != null)
      oldSelectionNumber = targetSelection.getSelectionNo(); 
    Selection copiedSelection = null;
    try {
      copiedSelection = (Selection)targetSelection.clone();
    } catch (CloneNotSupportedException cloneNotSupportedException) {}
    copiedSelection.setSelectionID(-1);
    Vector copiedImpactDates = copiedSelection.getImpactDates();
    if (copiedImpactDates != null) {
      for (int i = 0; i < copiedImpactDates.size(); i++) {
        ImpactDate impact = (ImpactDate)copiedImpactDates.get(i);
        impact.setSelectionID(copiedSelection.getSelectionID());
        impact.setImpactDateID(-1);
      } 
      copiedSelection.setImpactDates(copiedImpactDates);
    } 
    copiedSelection.setSellCode("");
    copiedSelection.setSellCodeDPC("");
    copiedSelection.setPriceCode(null);
    copiedSelection.setNumberOfUnits(0);
    copiedSelection.setSpecialPackaging(false);
    copiedSelection.setUmlContact(null);
    copiedSelection.setPlant(null);
    copiedSelection.setDistribution(null);
    copiedSelection.setManufacturingComments("");
    copiedSelection.setPoQuantity(0);
    copiedSelection.setCompletedQuantity(0);
    copiedSelection.setMultSelections(null);
    Form form = null;
    if (!copiedSelection.getIsDigital()) {
      copiedSelection.setNoDigitalRelease(true);
    } else {
      copiedSelection.setNoDigitalRelease(false);
    } 
    if (command.equalsIgnoreCase("selection-edit-copy-digital")) {
      copiedSelection.setReleaseType(null);
      copiedSelection.setSelectionConfig(null);
      copiedSelection.setSelectionSubConfig(null);
      copiedSelection.setSoundScanGrp("");
      copiedSelection.setProductCategory(null);
      copiedSelection.setHoldReason("");
      copiedSelection.setHoldSelection(false);
      copiedSelection.setTitleID("");
      copiedSelection.setReleaseType((ReleaseType)SelectionManager.getLookupObject("CO", Cache.getReleaseTypes()));
      copiedSelection.setDigitalRlsDate(null);
      copiedSelection.setInternationalDate(null);
      copiedSelection.setInternationalFlag(false);
      copiedSelection.setSelectionPackaging("");
      copiedSelection.setSelectionTerritory("");
      if (copiedSelection.getIsDigital()) {
        copiedSelection.setSelectionNo("");
        copiedSelection.setPrefixID(null);
        copiedSelection.setUpc("");
        copiedSelection.setImpactDates(null);
        copiedSelection.setStreetDate(null);
        copiedSelection.setNewBundleFlag(true);
        copiedSelection.setImpactDates(null);
        copiedSelection.setImpactDate(null);
        copiedSelection.setSpecialInstructions("");
      } else {
        copiedSelection.setConfigCode("");
        copiedSelection.setNewBundleFlag(false);
      } 
      form = buildDigitalForm(context, copiedSelection, command);
      if (copiedSelection.getIsDigital()) {
        FormRadioButtonGroup newBundle = (FormRadioButtonGroup)form.getElement("newBundle");
        newBundle.setValue("");
      } 
      FormCheckBox priority = (FormCheckBox)form.getElement("priority");
      priority.setValue("");
      copiedSelection.setIsDigital(true);
      copiedSelection.setNoDigitalRelease(false);
    } else {
      copiedSelection.setConfigCode("");
      copiedSelection.setComments("");
      copiedSelection.setSelectionNo("");
      copiedSelection.setPrefixID(null);
      copiedSelection.setUpc("");
      form = buildForm(context, copiedSelection, command);
    } 
    context.putSessionValue("impactSaveVisible", "true");
    context.putSessionValue("Selection", copiedSelection);
    context.putDelivery("old-selection-no", oldSelectionNumber);
    context.putDelivery("Form", form);
    Selection updatedSelection = SelectionManager.isProjectNumberValid(copiedSelection);
    if (updatedSelection == null) {
      if (targetSelection.getIsDigital()) {
        form = buildDigitalForm(context, targetSelection, "selection-editor");
      } else {
        form = buildForm(context, targetSelection, "selection-editor");
      } 
      context.removeSessionValue("Selection");
      context.removeDelivery("Form");
      context.putSessionValue("Selection", targetSelection);
      context.putDelivery("Form", form);
      context.putDelivery("ProjectNumberMessage", 
          "***************************** COPY FAILED ****************************\\nThe Project Number for this Product is NOT contained within Archimedes.\\nIf the Project Number is incorrect, please set up a NEW product.\\nIf the Project Number is correct, please contact system administrator so it can be added to Archimedes.");
      if (targetSelection.getIsDigital()) {
        if (targetSelection != null)
          return context.includeJSP("digital-selection-editor.jsp"); 
        return context.includeJSP("blank-selection-editor.jsp");
      } 
      if (targetSelection != null)
        return context.includeJSP("selection-editor.jsp"); 
      return context.includeJSP("blank-selection-editor.jsp");
    } 
    String diffMessage = "";
    try {
      if (updatedSelection.getLabel() != null && updatedSelection.getLabel().getStructureID() != copiedSelection.getLabel().getStructureID())
        diffMessage = String.valueOf(diffMessage) + "<b>Archimedes Label:</b> " + updatedSelection.getLabel().getName() + "<BR>"; 
      if (!updatedSelection.getOperCompany().equalsIgnoreCase(copiedSelection.getOperCompany()))
        diffMessage = String.valueOf(diffMessage) + "<b>Archimedes Operating Company:</b> " + updatedSelection.getOperCompany() + "<BR>"; 
      if (!updatedSelection.getSuperLabel().equalsIgnoreCase(copiedSelection.getSuperLabel()))
        diffMessage = String.valueOf(diffMessage) + "<b>Archimedes Super Label:</b> " + updatedSelection.getSuperLabel() + "<BR>"; 
      if (!updatedSelection.getSubLabel().equalsIgnoreCase(copiedSelection.getSubLabel()))
        diffMessage = String.valueOf(diffMessage) + "<b>Archimedes Sub Label:</b> " + updatedSelection.getSubLabel() + "<BR>"; 
      if (!updatedSelection.getImprint().equalsIgnoreCase(copiedSelection.getImprint()))
        diffMessage = String.valueOf(diffMessage) + "<b>Archimedes Imprint:</b> " + updatedSelection.getImprint() + "<BR>"; 
    } catch (Exception e) {
      System.out.println("project number validation..." + e.toString());
    } 
    if (diffMessage.length() > 0) {
      context.putDelivery("DiffMessage", diffMessage);
      context.putDelivery("updatedSelection", updatedSelection);
    } 
    if (command.equalsIgnoreCase("selection-edit-copy-digital")) {
      if (copiedSelection != null)
        return context.includeJSP("digital-selection-editor.jsp"); 
      return context.includeJSP("blank-selection-editor.jsp");
    } 
    if (copiedSelection != null)
      return context.includeJSP("selection-editor.jsp"); 
    return context.includeJSP("blank-selection-editor.jsp");
  }
  
  private boolean manufacturingEdit(Dispatcher dispatcher, Context context, String command) {
    int selectionID = -1;
    User user = (User)context.getSessionValue("user");
    Selection selection = new Selection();
    Notepad notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 0);
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    selection = MilestoneHelper.getScreenSelection(context);
    boolean newFlag = SelectionManager.getInstance().getSelectionManufacturingSubDetail(selection);
    int mfgAccessLevel = 0;
    if (selection != null)
      mfgAccessLevel = getSelectionPermissions(selection, user); 
    Form form = null;
    if (selection != null) {
      form = buildManufacturingForm(context, selection, command, mfgAccessLevel, newFlag);
      context.putDelivery("Form", form);
      context.putSessionValue("Selection", selection);
      int secureLevel = getSelectionMfgPermissions(selection, user);
      if (selection.getIsDigital())
        return dispatcher.redispatch(context, "schedule-editor"); 
      return context.includeJSP("selection-manufacturing-editor.jsp");
    } 
    form = new Form(this.application, "selectionForm", this.application.getInfrastructure().getServletURL(), "POST");
    form.addElement(new FormHidden("cmd", "selection-manufacturing-editor", true));
    form.addElement(new FormHidden("OrderBy", "", true));
    if (context.getSessionValue("NOTEPAD_MFG_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_MFG_VISIBLE")); 
    Vector companies = MilestoneHelper.getUserCompanies(context);
    addSelectionSearchElements(context, null, form, companies, true);
    context.putDelivery("Form", form);
    return context.includeJSP("blank-selection-manufacturing-editor.jsp");
  }
  
  private boolean manufacturingEditSave(Dispatcher dispatcher, Context context, String command) {
    Selection selection = (Selection)context.getSessionValue("Selection");
    User user = (User)context.getSessionValue("user");
    Form form = buildManufacturingForm(context, selection, command, getSelectionMfgPermissions(selection, user), false);
    form.setValues(context);
    String mfgComments = form.getStringValue("orderCommentHelper");
    String comments = form.getStringValue("comments");
    String umlContact = form.getStringValue("umlcontact");
    String distribution = form.getStringValue("distribution");
    selection.setDistribution(SelectionManager.getLookupObject(distribution, Cache.getDistributionCodes()));
    selection.setManufacturingComments(mfgComments);
    selection.setComments(comments);
    Vector plants = new Vector();
    Vector newPlants = new Vector();
    if (selection.getManufacturingPlants() != null)
      plants = selection.getManufacturingPlants(); 
    for (int plantCount = 0; plantCount < plants.size(); plantCount++) {
      Plant p = (Plant)plants.get(plantCount);
      String vendor = form.getStringValue("plant" + plantCount);
      int poQty = 0;
      String poQtyString = form.getStringValue("po_qty" + plantCount);
      if (poQtyString.indexOf(",") > -1) {
        String newString = "";
        for (int i = 0; i < poQtyString.length(); i++) {
          if (poQtyString.charAt(i) != ',')
            newString = String.valueOf(newString) + poQtyString.charAt(i); 
        } 
        poQtyString = newString;
      } 
      try {
        poQty = Integer.parseInt(poQtyString);
      } catch (Exception exception) {}
      String completedQty = form.getStringValue("completed_qty" + plantCount);
      if (completedQty.indexOf(",") > -1) {
        String newString = "";
        for (int i = 0; i < completedQty.length(); i++) {
          if (completedQty.charAt(i) != ',')
            newString = String.valueOf(newString) + completedQty.charAt(i); 
        } 
        completedQty = newString;
      } 
      int completedQtyInt = 0;
      try {
        completedQtyInt = Integer.parseInt(completedQty);
      } catch (NumberFormatException numberFormatException) {}
      p.setSelectionID(selection.getSelectionNo());
      p.setReleaseID(selection.getSelectionID());
      p.setOrderQty(poQty);
      p.setCompletedQty(completedQtyInt);
      p.setPlant(SelectionManager.getLookupObject(vendor, Cache.getVendors()));
      newPlants.add(p);
    } 
    selection.setManufacturingPlants(newPlants);
    boolean newFlag = false;
    if (form.getStringValue("new").equals("true"))
      newFlag = true; 
    if (newFlag || (!newFlag && SelectionManager.getInstance().isManufacturingTimestampValid(selection))) {
      if (umlContact != null && !umlContact.equals("")) {
        int umlContactUserId = Integer.parseInt(umlContact);
        selection.setUmlContact(UserManager.getInstance().getUser(umlContactUserId));
      } 
      if (!form.isUnchanged()) {
        FormValidation formValidation = form.validate();
        if (formValidation.isGood()) {
          Selection savedSelection = SelectionManager.getInstance().saveManufacturingSelection(selection, user, newFlag);
          Notepad notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 0);
          MilestoneHelper.putNotepadIntoSession(notepad, context);
          SelectionManager.getInstance().getSelectionManufacturingSubDetail(savedSelection);
          String lastMfgUpdatedDateText = "";
          if (savedSelection.getLastMfgUpdateDate() != null)
            lastMfgUpdatedDateText = MilestoneHelper.getCustomFormatedDate(savedSelection.getLastMfgUpdateDate(), "M/d/yyyy hh:mm:ss a 'ET'"); 
          context.putDelivery("lastUpdateDate", lastMfgUpdatedDateText);
          String lastMfgUpdateUser = "";
          if (savedSelection.getLastMfgUpdatingUser() != null)
            lastMfgUpdateUser = savedSelection.getLastMfgUpdatingUser().getName(); 
          context.putDelivery("lastUpdateUser", lastMfgUpdateUser);
          notepad.setAllContents(null);
          notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 0);
          notepad.setSelected(savedSelection);
        } else {
          context.putDelivery("FormValidation", formValidation);
        } 
      } 
      form.addElement(new FormHidden("OrderBy", "", true));
      context.putDelivery("Form", form);
    } else {
      context.putDelivery("AlertMessage", "The record is unavailable for editing at this time.  Please refresh your screen and edit it at a later time.");
    } 
    context.putDelivery("Form", form);
    if (selection.getIsDigital())
      return dispatcher.redispatch(context, "schedule-editor"); 
    return context.includeJSP("selection-manufacturing-editor.jsp");
  }
  
  private boolean manufacturingPlantAdd(Dispatcher dispatcher, Context context, String command) {
    Selection selection = (Selection)context.getSessionValue("Selection");
    User user = (User)context.getSessionValue("user");
    Vector plants = new Vector();
    plants = selection.getManufacturingPlants();
    Plant p = new Plant();
    plants.add(p);
    selection.setManufacturingPlants(plants);
    context.putSessionValue("Selection", selection);
    Form form = buildManufacturingForm(context, selection, command, getSelectionMfgPermissions(selection, user), false);
    form.setValues(context);
    context.putDelivery("Form", form);
    if (selection.getIsDigital())
      return dispatcher.redispatch(context, "schedule-editor"); 
    return context.includeJSP("selection-manufacturing-editor.jsp");
  }
  
  private boolean manufacturingPlantDelete(Dispatcher dispatcher, Context context, String command) {
    Selection selection = (Selection)context.getSessionValue("Selection");
    User user = (User)context.getSessionValue("user");
    int id = Integer.parseInt(context.getRequestValue("plantId"));
    Vector plants = new Vector();
    plants = selection.getManufacturingPlants();
    plants.remove(id);
    selection.setManufacturingPlants(plants);
    context.putSessionValue("Selection", selection);
    Form form = buildManufacturingForm(context, selection, command, getSelectionMfgPermissions(selection, user), false);
    form.setValues(context);
    context.putDelivery("Form", form);
    if (selection.getIsDigital())
      return dispatcher.redispatch(context, "schedule-editor"); 
    return context.includeJSP("selection-manufacturing-editor.jsp");
  }
  
  protected Form buildForm(Context context, Selection selection, String command) {
    Form selectionForm = new Form(this.application, "selectionForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    User user = (User)context.getSession().getAttribute("user");
    int userId = user.getUserId();
    int secureLevel = getSelectionPermissions(selection, user);
    setButtonVisibilities(selection, user, context, secureLevel, command);
    boolean newFlag = (selection.getSelectionID() < 0);
    if (newFlag) {
      context.putDelivery("new-or-copy", "true");
    } else {
      context.putDelivery("new-or-copy", "false");
    } 
    selectionForm.addElement(new FormHidden("cmd", command, true));
    selectionForm.addElement(new FormHidden("OrderBy", "", true));
    selectionForm.addElement(new FormHidden("hidTitleId", "", true));
    selectionForm.addElement(new FormHidden("isFocus", "", true));
    selectionForm.addElement(new FormHidden("statusHidVal", "", true));
    selectionForm.addElement(new FormHidden("generateSelection", "", true));
    Vector companies = null;
    companies = MilestoneHelper.getUserCompanies(context);
    if (selection != null) {
      FormTextField artistFirstName = new FormTextField("artistFirstName", selection.getArtistFirstName(), false, 20, 50);
      artistFirstName.setTabIndex(1);
      artistFirstName.addFormEvent("onChange", "javaScript:removeSpaces(this);");
      artistFirstName.setClassName("ctrlMedium");
      selectionForm.addElement(artistFirstName);
      FormTextField artistLastName = new FormTextField("artistLastName", selection.getArtistLastName(), false, 20, 50);
      artistLastName.setTabIndex(2);
      artistLastName.addFormEvent("onChange", "javaScript:removeSpaces(this);");
      artistLastName.setClassName("ctrlMedium");
      selectionForm.addElement(artistLastName);
      FormTextField title = new FormTextField("title", selection.getTitle(), true, 73, 125);
      title.setTabIndex(3);
      title.addFormEvent("onChange", "javaScript:removeSpaces(this);");
      title.setClassName("ctrlXLarge");
      selectionForm.addElement(title);
      FormTextField sideATitle = new FormTextField("sideATitle", selection.getASide(), false, 20, 125);
      sideATitle.setTabIndex(4);
      sideATitle.setClassName("ctrlMedium");
      selectionForm.addElement(sideATitle);
      FormTextField sideBTitle = new FormTextField("sideBTitle", selection.getBSide(), false, 20, 125);
      sideBTitle.setTabIndex(5);
      sideBTitle.setClassName("ctrlMedium");
      selectionForm.addElement(sideBTitle);
      String GDRSProductStatusStr = "";
      DcGDRSResults dcGDRSResults = GDRSProductStatusGet(selection, selection.getCompany().getParentEnvironment().getStructureID());
      GDRSProductStatusStr = dcGDRSResults.getStatus();
      FormHidden GDRSProductStatus = new FormHidden("GDRSProductStatus", GDRSProductStatusStr, false);
      selectionForm.addElement(GDRSProductStatus);
      String streetDateText = "";
      if (selection.getStreetDate() != null)
        streetDateText = MilestoneHelper.getFormatedDate(selection.getStreetDate()); 
      FormTextField streetDate = new FormTextField("streetDate", streetDateText, false, 10);
      streetDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
      streetDate.addFormEvent("onBlur", "JavaScript:validateDate(document.forms[0].streetDate.value,this);populateNoDigitalRelease()");
      streetDate.addFormEvent("oldValue", streetDateText);
      streetDate.setTabIndex(6);
      streetDate.setClassName("ctrlShort");
      selectionForm.addElement(streetDate);
      FormTextField dayType = new FormTextField("dayType", MilestoneHelper.getDayType(selection.getCalendarGroup(), selection.getStreetDate()), false, 5);
      selectionForm.addElement(dayType);
      String digitalRlsDateText = "";
      if (selection.getDigitalRlsDate() != null)
        digitalRlsDateText = MilestoneHelper.getFormatedDate(selection.getDigitalRlsDate()); 
      FormTextField drDate = new FormTextField("digitalDateDisplay", digitalRlsDateText, false, 10);
      selectionForm.addElement(drDate);
      FormHidden digitalDate = new FormHidden("digitalDate", digitalRlsDateText, false);
      selectionForm.addElement(digitalDate);
      boolean noDigitalReleaseValue = dcGDRSResults.getForceNoDigitalRelease().booleanValue() ? true : selection.getNoDigitalRelease();
      FormHidden ForceNoDigitalRelease = new FormHidden("ForceNoDigitalRelease", dcGDRSResults.getForceNoDigitalRelease().toString(), false);
      selectionForm.addElement(ForceNoDigitalRelease);
      FormCheckBox noDigitalRelease = new FormCheckBox("noDigitalRelease", "", false, noDigitalReleaseValue);
      noDigitalRelease.addFormEvent("onChange", "JavaScript:noDigitalReleaseChanged();");
      noDigitalRelease.addFormEvent("oldValue", Boolean.toString(selection.getNoDigitalRelease()));
      noDigitalRelease.setTabIndex(7);
      selectionForm.addElement(noDigitalRelease);
      String intDateText = "";
      if (selection.getInternationalDate() != null)
        intDateText = MilestoneHelper.getFormatedDate(selection.getInternationalDate()); 
      FormDateField intDate = new FormDateField("internationalDate", intDateText, false, 10);
      intDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
      intDate.addFormEvent("onBlur", "JavaScript:validateDate(document.forms[0].internationalDate.value,this)");
      intDate.setTabIndex(8);
      intDate.setClassName("ctrlShort");
      selectionForm.addElement(intDate);
      context.putDelivery("releaseWeek", MilestoneHelper.getReleaseWeekString(selection));
      FormDropDownMenu status = MilestoneHelper.getLookupDropDown("status", Cache.getSelectionStatusList(), SelectionManager.getLookupObjectValue(selection.getSelectionStatus()), true, false);
      status.addFormEvent("oldValue", SelectionManager.getLookupObjectValue(selection.getSelectionStatus()));
      status.setTabIndex(9);
      status.setClassName("ctrlSmall");
      selectionForm.addElement(status);
      boolean boolHoldReason = true;
      if (selection.getHoldReason().equalsIgnoreCase(""))
        boolHoldReason = false; 
      FormCheckBox holdIndicator = new FormCheckBox("holdIndicator", "", false, boolHoldReason);
      holdIndicator.setTabIndex(10);
      selectionForm.addElement(holdIndicator);
      FormTextArea holdReason = new FormTextArea("holdReason", selection.getHoldReason(), false, 2, 44, "virtual");
      holdReason.addFormEvent("onBlur", "Javascript:checkField(this)");
      selectionForm.addElement(holdReason);
      FormCheckBox pdIndicator = new FormCheckBox("pdIndicator", "", false, selection.getPressAndDistribution());
      pdIndicator.setTabIndex(12);
      selectionForm.addElement(pdIndicator);
      FormCheckBox intlFlag = new FormCheckBox("intlFlag", "", false, selection.getInternationalFlag());
      intlFlag.setTabIndex(11);
      selectionForm.addElement(intlFlag);
      String impactDateText = "";
      if (selection.getImpactDate() != null)
        impactDateText = MilestoneHelper.getFormatedDate(selection.getImpactDate()); 
      FormDateField impactDate = new FormDateField("impactdate", impactDateText, false, 13);
      impactDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
      impactDate.addFormEvent("onBlur", "JavaScript:validateDate(document.forms[0].impactdate.value,this)");
      impactDate.setTabIndex(13);
      impactDate.setClassName("ctrlShort");
      selectionForm.addElement(impactDate);
      Vector releaseFamilies = ReleasingFamily.getReleasingFamilies(userId, selection.getFamily().getStructureID(), context);
      FormDropDownMenu releasingFamily = MilestoneHelper.getReleasingFamilyDropDown("releasingFamily", Integer.toString(selection.getReleaseFamilyId()), releaseFamilies, true, selection);
      releasingFamily.addFormEvent("onChange", "ReleasingFamilyChange(this)");
      selectionForm.addElement(releasingFamily);
      String evironmentId = "";
      String environmentName = "";
      Vector evironmentList = filterSelectionEnvironments(companies);
      if (selection.getCompany().getParentEnvironment() != null) {
        evironmentId = Integer.toString(selection.getCompany().getParentEnvironment().getStructureID());
        environmentName = selection.getCompany().getParentEnvironment().getName();
      } else {
        evironmentId = "";
      } 
      FormHidden evironment = new FormHidden("environment", evironmentId, false);
      FormHidden evironmentLabel = new FormHidden("environment", evironmentId, false);
      evironment.setTabIndex(14);
      evironment.setDisplayName(environmentName);
      selectionForm.addElement(evironment);
      String companyId = "";
      String companyName = "";
      if (selection.getCompany() != null) {
        companyId = Integer.toString(selection.getCompany().getStructureID());
        companyName = selection.getCompany().getName();
      } 
      FormHidden company = new FormHidden("company", companyId, false);
      company.setTabIndex(15);
      company.setDisplayName(companyName);
      selectionForm.addElement(company);
      String divisionId = "";
      String divisionName = "";
      if (selection.getDivision() != null) {
        divisionId = Integer.toString(selection.getDivision().getStructureID());
        divisionName = selection.getDivision().getName();
      } else {
        divisionId = "";
      } 
      FormHidden division = new FormHidden("division", divisionId, false);
      division.setTabIndex(16);
      division.setDisplayName(divisionName);
      selectionForm.addElement(division);
      String labelId = "";
      String labelName = "";
      if (selection.getLabel() != null) {
        labelId = Integer.toString(selection.getLabel().getStructureID());
        labelName = selection.getLabel().getName();
      } else {
        labelId = "";
      } 
      FormHidden label = new FormHidden("label", labelId, false);
      label.setTabIndex(17);
      label.setDisplayName(labelName);
      selectionForm.addElement(label);
      if (selection.getOperCompany().equals("***")) {
        FormHidden opercompany = new FormHidden("opercompany", "***", false);
        opercompany.setDisplayName("***");
        opercompany.setTabIndex(18);
        selectionForm.addElement(opercompany);
      } else {
        LookupObject oc = MilestoneHelper.getLookupObject(selection
            .getOperCompany(), Cache.getOperatingCompanies());
        String ocAbbr = "";
        String ocName = "";
        if (oc == null) {
          ocAbbr = selection.getOperCompany();
        } else {
          if (oc != null && oc.getAbbreviation() != null)
            ocAbbr = oc.getAbbreviation(); 
          if (oc != null && oc.getName() != null)
            ocName = ":" + oc.getName(); 
        } 
        FormHidden opercompany = new FormHidden("opercompany", ocAbbr, false);
        opercompany.setDisplayName(String.valueOf(ocAbbr) + ocName);
        if (ocAbbr.equals("ZZ"))
          opercompany.setDisplayName(ocAbbr); 
        opercompany.setTabIndex(18);
        selectionForm.addElement(opercompany);
      } 
      FormHidden superlabel = new FormHidden("superlabel", selection.getSuperLabel(), false);
      superlabel.setTabIndex(19);
      superlabel.setClassName("ctrlShort");
      superlabel.addFormEvent("onBlur", "javascript:this.value=this.value.toUpperCase();");
      selectionForm.addElement(superlabel);
      FormHidden sublabel = new FormHidden("sublabel", selection.getSubLabel(), false);
      sublabel.setTabIndex(20);
      sublabel.setClassName("ctrlShort");
      sublabel.addFormEvent("onBlur", "javascript:this.value=this.value.toUpperCase();");
      selectionForm.addElement(sublabel);
      FormTextField imprint = new FormTextField("imprint", selection.getImprint(), false, 50);
      imprint.setTabIndex(21);
      imprint.setClassName("ctrlMedium");
      imprint.addFormEvent("onChange", "javaScript:removeSpaces(this);");
      selectionForm.addElement(imprint);
      FormDropDownMenu configcode = MilestoneHelper.getPfmLookupDropDown("configcode", MilestoneHelper.getConfigCodes(0), selection.getConfigCode(), false, true);
      configcode.setTabIndex(21);
      configcode.addFormEvent("onKeyPress", "scanKey(window.event.keyCode, 'document.all.configcode.options', getMaxLength(document.all.configcode.options))");
      if (!newFlag)
        configcode.addFormEvent("onChange", "setNoDigitalRelease(this);"); 
      selectionForm.addElement(configcode);
      FormHidden projectId = new FormHidden("projectId", String.valueOf(selection.getProjectID()), false);
      projectId.setTabIndex(22);
      projectId.setDisplayName(String.valueOf(selection.getProjectID()));
      projectId.setClassName("ctrlMedium");
      selectionForm.addElement(projectId);
      FormTextField gridNumber = new FormTextField("gridNumber", selection.getGridNumber(), false, 50);
      gridNumber.setTabIndex(25);
      gridNumber.setEnabled(true);
      selectionForm.addElement(gridNumber);
      FormTextField upc = new FormTextField("UPC", selection.getUpc(), false, 17, 20);
      upc.setTabIndex(23);
      upc.setClassName("ctrlMedium");
      upc.addFormEvent("onKeyPress", "return checkNumbersOnly(window.event.keyCode);");
      selectionForm.addElement(upc);
      FormTextField soundscan = new FormTextField("soundscan", selection.getSoundScanGrp(), false, 17, 20);
      soundscan.setTabIndex(24);
      soundscan.setClassName("ctrlMedium");
      soundscan.addFormEvent("onKeyPress", "return checkNumbersOnly(window.event.keyCode);");
      selectionForm.addElement(soundscan);
      FormDropDownMenu prefix = MilestoneHelper.getPrefixCodeDropDown(userId, "prefix", Cache.getPrefixCodes(), SelectionManager.getLookupObjectValue(selection.getPrefixID()), true, context);
      prefix.setTabIndex(25);
      prefix.setClassName("ctrlShort");
      selectionForm.addElement(prefix);
      FormTextField selectionNo = new FormTextField("selectionNo", String.valueOf(selection.getSelectionNo()), false, 20, 20);
      selectionNo.setTabIndex(26);
      selectionNo.addFormEvent("onChange", "javaScript:removeSpaces(this);");
      selectionNo.setClassName("ctrlMedium");
      selectionForm.addElement(selectionNo);
      FormTextField titleId = new FormTextField("titleId", String.valueOf(selection.getTitleID()), false, 13, 24);
      titleId.setClassName("ctrlMedium");
      titleId.setTabIndex(27);
      selectionForm.addElement(titleId);
      FormDropDownMenu productLine = MilestoneHelper.getLookupDropDown("productLine", Cache.getProductCategories(0), SelectionManager.getLookupObjectValue(selection.getProductCategory()), true, true);
      productLine.setTabIndex(28);
      productLine.setClassName("ctrlMedium");
      selectionForm.addElement(productLine);
      FormDropDownMenu releaseType = MilestoneHelper.getLookupDropDown("releaseType", Cache.getReleaseTypes(), SelectionManager.getLookupObjectValue(selection.getReleaseType()), true, newFlag);
      releaseType.setTabIndex(29);
      releaseType.setClassName("ctrlMedium");
      releaseType.addFormEvent("onChange", "releaseTypeChanged()");
      selectionForm.addElement(releaseType);
      String configValue = "";
      if (selection.getSelectionConfig() != null)
        configValue = selection.getSelectionConfig().getSelectionConfigurationAbbreviation(); 
      FormDropDownMenu configuration = MilestoneHelper.getSelectionConfigurationDropDown("configuration", configValue, true, 0);
      configuration.setTabIndex(30);
      configuration.addFormEvent("onChange", "buildSubConfigs(this.selectedIndex)");
      selectionForm.addElement(configuration);
      String subConfigValue = "";
      if (selection.getSelectionSubConfig() != null)
        subConfigValue = selection.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation(); 
      FormDropDownMenu subConfiguration = MilestoneHelper.getSelectionSubConfigurationDropDown("subConfiguration", selection.getSelectionConfig(), subConfigValue, true);
      subConfiguration.addFormEvent("onChange", "isSubConfigParent(this.selectedIndex)");
      subConfiguration.setTabIndex(31);
      selectionForm.addElement(subConfiguration);
      FormTextField test = new FormTextField("test", "", false, 8, 8);
      test.setTabIndex(32);
      test.setClassName("ctrlShort");
      test.addFormEvent("onChange", "javaScript:clickSell(this,false);");
      selectionForm.addElement(test);
      String sellCode = "";
      if (selection.getSellCode() != null)
        sellCode = selection.getSellCode(); 
      FormDropDownMenu priceCode = new FormDropDownMenu("priceCode", sellCode, "-1" + getSellCodesString(), "&nbsp;" + getSellCodesString(), false);
      priceCode.setTabIndex(33);
      priceCode.setClassName("ctrlSmall");
      selectionForm.addElement(priceCode);
      FormTextField testDPC = new FormTextField("testDPC", "", false, 8, 8);
      testDPC.setTabIndex(39);
      testDPC.setClassName("ctrlShort");
      testDPC.addFormEvent("onChange", "javaScript:clickSellDPC(this);");
      selectionForm.addElement(testDPC);
      String sellCodeDPC = "";
      if (selection.getSellCodeDPC() != null)
        sellCodeDPC = selection.getSellCodeDPC(); 
      FormDropDownMenu priceCodeDPC = new FormDropDownMenu("priceCodeDPC", sellCodeDPC, "-1" + getSellCodesStringDPC(), "&nbsp;" + getSellCodesStringDPC(), false);
      priceCodeDPC.setTabIndex(39);
      priceCodeDPC.setClassName("ctrlSmall");
      selectionForm.addElement(priceCodeDPC);
      String numberOfUnits = "0";
      if (selection.getNumberOfUnits() > 0)
        numberOfUnits = Integer.toString(selection.getNumberOfUnits()); 
      FormTextField numOfUnits = new FormTextField("numOfUnits", numberOfUnits, false, 10, 10);
      numOfUnits.setTabIndex(34);
      numOfUnits.setClassName("ctrlShort");
      selectionForm.addElement(numOfUnits);
      User labelUserContact = selection.getLabelContact();
      Vector labelContacts = SelectionManager.getLabelContacts(selection);
      FormDropDownMenu contactList = MilestoneHelper.getContactsDropDown(context, "contactlist", labelContacts, labelUserContact, true);
      contactList.setTabIndex(35);
      contactList.setClassName("ctrlMedium");
      selectionForm.addElement(contactList);
      FormTextField contact = new FormTextField("contact", selection.getOtherContact(), false, 14, 30);
      contact.setTabIndex(36);
      contact.setClassName("ctrlMedium");
      selectionForm.addElement(contact);
      FormCheckBox parentalIndicator = new FormCheckBox("parentalIndicator", "", false, selection.getParentalGuidance());
      parentalIndicator.setTabIndex(37);
      selectionForm.addElement(parentalIndicator);
      FormCheckBox specPkgIndicator = new FormCheckBox("specialPkgIndicator", "", false, selection.getSpecialPackaging());
      specPkgIndicator.addFormEvent("onDblClick", "document.forms[0].PackagingHelper.value=document.forms[0].package.value;togglePackaging();");
      specPkgIndicator.setTabIndex(38);
      selectionForm.addElement(specPkgIndicator);
      FormTextField pkg = new FormTextField("package", selection.getSelectionPackaging(), false, 13, 100);
      pkg.setTabIndex(39);
      pkg.setClassName("ctrlMedium");
      pkg.addFormEvent("onDblClick", "document.forms[0].PackagingHelper.value=document.forms[0].package.value;togglePackaging();");
      selectionForm.addElement(pkg);
      FormDropDownMenu genre = MilestoneHelper.getPfmLookupDropDown("genre", Cache.getMusicTypes(), SelectionManager.getLookupObjectValue(selection.getGenre()), false, true);
      genre.setTabIndex(40);
      genre.setId("music_type");
      selectionForm.addElement(genre);
      FormTextField territory = new FormTextField("territory", selection.getSelectionTerritory(), false, 13, 255);
      territory.setTabIndex(41);
      territory.setClassName("ctrlMedium");
      territory.addFormEvent("onDblClick", "document.forms[0].TerritoryHelper.value=document.forms[0].territory.value;toggleTerritory();");
      selectionForm.addElement(territory);
      FormHidden productionGroupCode = new FormHidden("productGroupCode", selection.getProductionGroupCode(), false);
      productionGroupCode.setTabIndex(42);
      productionGroupCode.setDisplayName(selection.getProductionGroupCode());
      productionGroupCode.setClassName("ctrlMedium");
      selectionForm.addElement(productionGroupCode);
      String lastStreetDateText = "";
      if (selection.getLastStreetUpdateDate() != null)
        lastStreetDateText = MilestoneHelper.getFormatedDate(selection.getLastStreetUpdateDate()); 
      FormTextField lastStreetUpdatedDate = new FormTextField("laststreetupdateddate", lastStreetDateText, false, 13);
      selectionForm.addElement(lastStreetUpdatedDate);
      String lastUpdatedDateText = "";
      if (selection.getLastUpdateDate() != null)
        lastUpdatedDateText = MilestoneHelper.getCustomFormatedDate(selection.getLastUpdateDate(), "M/d/yyyy hh:mm:ss a 'ET'"); 
      FormTextField lastUpdatedDate = new FormTextField("lastupdateddate", lastUpdatedDateText, false, 50);
      selectionForm.addElement(lastUpdatedDate);
      String originDateText = "";
      if (selection.getOriginDate() != null)
        originDateText = MilestoneHelper.getFormatedDate(selection.getOriginDate()); 
      FormTextField originDate = new FormTextField("origindate", originDateText, false, 13);
      selectionForm.addElement(originDate);
      String archieDateText = "";
      if (selection.getArchieDate() != null)
        archieDateText = MilestoneHelper.getCustomFormatedDate(selection.getArchieDate(), "M/d/yyyy hh:mm:ss a 'ET'"); 
      FormTextField archieDate = new FormTextField("archieDate", archieDateText, false, 13);
      selectionForm.addElement(archieDate);
      String autoCloseDateText = "";
      if (selection.getAutoCloseDate() != null)
        autoCloseDateText = MilestoneHelper.getCustomFormatedDate(selection.getAutoCloseDate(), "M/d/yyyy hh:mm:ss a 'ET'"); 
      FormTextField autoCloseDate = new FormTextField("autoCloseDate", autoCloseDateText, false, 40);
      selectionForm.addElement(autoCloseDate);
      String lastLegacyUpdateDateText = "";
      if (selection.getLastLegacyUpdateDate() != null)
        lastLegacyUpdateDateText = MilestoneHelper.getCustomFormatedDate(selection.getLastLegacyUpdateDate(), "M/d/yyyy hh:mm:ss a 'ET'"); 
      FormTextField lastLegacyUpdateDate = new FormTextField("lastLegacyUpdateDate", lastLegacyUpdateDateText, false, 40);
      selectionForm.addElement(lastLegacyUpdateDate);
      FormTextArea packagingHelper = new FormTextArea("PackagingHelper", selection.getSelectionPackaging(), false, 2, 44, "virtual");
      selectionForm.addElement(packagingHelper);
      FormTextArea territoryHelper = new FormTextArea("TerritoryHelper", selection.getSelectionTerritory(), false, 2, 44, "virtual");
      selectionForm.addElement(territoryHelper);
      FormTextArea comments = new FormTextArea("comments", selection.getSelectionComments(), false, 6, 44, "virtual");
      comments.addFormEvent("onBlur", "Javascript:checkField(this)");
      selectionForm.addElement(comments);
    } 
    addSelectionSearchElements(context, selection, selectionForm, companies, true);
    if (context.getSessionValue("NOTEPAD_SELECTION_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_SELECTION_VISIBLE")); 
    boolean isParent = false;
    if (selection.getSelectionSubConfig() != null)
      isParent = selection.getSelectionSubConfig().isParent(); 
    context.putDelivery("is-parent", String.valueOf(isParent));
    context.putDelivery("old-selection-no", selection.getSelectionNo());
    String price = "0.00";
    if (selection.getPriceCode() != null && 
      selection.getPriceCode().getTotalCost() > 0.0F)
      price = MilestoneHelper.formatDollarPrice(selection.getPriceCode().getTotalCost()); 
    context.putDelivery("price", price);
    String lastUpdateUser = "";
    if (selection.getLastUpdatingUser() != null)
      lastUpdateUser = selection.getLastUpdatingUser().getName(); 
    context.putDelivery("lastUpdateUser", lastUpdateUser);
    return selectionForm;
  }
  
  protected Form buildNewForm(Context context, Selection selection, String command) {
    Vector projectList = (Vector)context.getSessionValue("searchResults");
    String resultsIndex = (String)context.getSessionValue("selectionScreenTypeIndex");
    ProjectSearch selectedProject = null;
    if (resultsIndex != null) {
      selectedProject = (ProjectSearch)projectList.elementAt(Integer.parseInt(resultsIndex));
    } else {
      selectedProject = new ProjectSearch();
    } 
    context.removeSessionValue("selectionScreenType");
    context.removeSessionValue("searchResults");
    context.removeSessionValue("selectionScreenTypeIndex");
    Form selectionForm = new Form(this.application, "selectionForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    User user = (User)context.getSession().getAttribute("user");
    int userId = user.getUserId();
    int secureLevel = getSelectionPermissions(selection, user);
    setButtonVisibilities(selection, user, context, secureLevel, command);
    selectionForm.addElement(new FormHidden("cmd", "selection-edit-new", true));
    selectionForm.addElement(new FormHidden("OrderBy", "", true));
    selectionForm.addElement(new FormHidden("hidTitleId", "", true));
    selectionForm.addElement(new FormHidden("isFocus", "", true));
    selectionForm.addElement(new FormHidden("statusHidVal", "", true));
    selectionForm.addElement(new FormHidden("generateSelection", "", true));
    selectionForm.addElement(new FormHidden("GDRSProductStatus", "", false));
    String strArtistFirstName = (selectedProject.getArtistFirstName() != null) ? selectedProject.getArtistFirstName() : "";
    FormTextField artistFirstName = new FormTextField("artistFirstName", strArtistFirstName, false, 20, 50);
    artistFirstName.setTabIndex(1);
    artistFirstName.setClassName("ctrlMedium");
    artistFirstName.addFormEvent("onChange", "javaScript:removeSpaces(this);");
    selectionForm.addElement(artistFirstName);
    String strArtistLastName = (selectedProject.getArtistLastName() != null) ? selectedProject.getArtistLastName() : "";
    FormTextField artistLastName = new FormTextField("artistLastName", strArtistLastName, false, 20, 50);
    artistLastName.setTabIndex(2);
    artistLastName.setClassName("ctrlMedium");
    artistLastName.addFormEvent("onChange", "javaScript:removeSpaces(this);");
    selectionForm.addElement(artistLastName);
    String strTitle = (selectedProject.getTitle() != null) ? selectedProject.getTitle() : "";
    FormTextField title = new FormTextField("title", strTitle, true, 73, 125);
    title.setTabIndex(3);
    title.setClassName("ctrlXLarge");
    title.addFormEvent("onChange", "javaScript:removeSpaces(this);");
    selectionForm.addElement(title);
    FormTextField sideATitle = new FormTextField("sideATitle", "", false, 20, 125);
    sideATitle.setTabIndex(4);
    sideATitle.setClassName("ctrlMedium");
    selectionForm.addElement(sideATitle);
    FormTextField sideBTitle = new FormTextField("sideBTitle", "", false, 20, 125);
    sideBTitle.setTabIndex(5);
    sideBTitle.setClassName("ctrlMedium");
    selectionForm.addElement(sideBTitle);
    FormTextField streetDate = new FormTextField("streetDate", "", false, 10);
    streetDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
    streetDate.addFormEvent("onBlur", "JavaScript:validateDate(document.forms[0].streetDate.value,this);populateNoDigitalRelease();");
    streetDate.addFormEvent("oldValue", "");
    streetDate.setTabIndex(6);
    streetDate.setClassName("ctrlShort");
    selectionForm.addElement(streetDate);
    FormTextField drDate = new FormTextField("digitalDateDisplay", "", false, 10);
    selectionForm.addElement(drDate);
    FormHidden digitalDate = new FormHidden("digitalDate", "", false);
    selectionForm.addElement(digitalDate);
    DcGDRSResults dcGDRSResults = GDRSProductStatusGet(selection, selectedProject.getMSEnvironmentId());
    boolean IsDefaultChecked = true;
    FormHidden ForceNoDigitalRelease = new FormHidden("ForceNoDigitalRelease", "true", false);
    selectionForm.addElement(ForceNoDigitalRelease);
    FormCheckBox noDigitalRelease = new FormCheckBox("noDigitalRelease", "", false, IsDefaultChecked);
    noDigitalRelease.addFormEvent("onChange", "JavaScript:noDigitalReleaseChanged();");
    noDigitalRelease.addFormEvent("oldValue", "");
    noDigitalRelease.setTabIndex(7);
    selectionForm.addElement(noDigitalRelease);
    FormDateField intDate = new FormDateField("internationalDate", "", false, 10);
    intDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
    intDate.addFormEvent("onBlur", "JavaScript:validateDate(document.forms[0].internationalDate.value,this)");
    intDate.setTabIndex(8);
    intDate.setClassName("ctrlShort");
    selectionForm.addElement(intDate);
    FormDropDownMenu status = MilestoneHelper.getLookupDropDown("status", Cache.getSelectionStatusList(), "Active", true, false);
    status.addFormEvent("oldValue", "Active");
    status.setTabIndex(9);
    status.setClassName("ctrlSmall");
    selectionForm.addElement(status);
    FormCheckBox holdIndicator = new FormCheckBox("holdIndicator", "", false, false);
    holdIndicator.setTabIndex(10);
    selectionForm.addElement(holdIndicator);
    FormTextArea holdReason = new FormTextArea("holdReason", "", false, 2, 44, "virtual");
    holdReason.addFormEvent("onBlur", "Javascript:checkField(this)");
    selectionForm.addElement(holdReason);
    int pd_indicator = selectedProject.getPD_Indicator();
    boolean pdBool = false;
    if (pd_indicator == 1)
      pdBool = true; 
    FormCheckBox pdIndicator = new FormCheckBox("pdIndicator", "", false, pdBool);
    pdIndicator.setTabIndex(12);
    selectionForm.addElement(pdIndicator);
    FormCheckBox intlFlag = new FormCheckBox("intlFlag", "", false, false);
    intlFlag.setTabIndex(11);
    selectionForm.addElement(intlFlag);
    FormDateField impactDate = new FormDateField("impactdate", "", false, 13);
    impactDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
    impactDate.addFormEvent("onBlur", "JavaScript:validateDate(document.forms[0].impactdate.value,this)");
    impactDate.setTabIndex(13);
    impactDate.setClassName("ctrlShort");
    selectionForm.addElement(impactDate);
    Vector releaseFamilies = ReleasingFamily.getReleasingFamilies(userId, selectedProject.getMSFamilyId(), context);
    ReleasingFamily defaultReleasingFamily = ReleasingFamily.getDefaultReleasingFamily(userId, selectedProject.getMSFamilyId(), context);
    FormDropDownMenu releasingFamily = MilestoneHelper.getReleasingFamilyDropDown("releasingFamily", String.valueOf(defaultReleasingFamily.getReleasingFamilyId()), releaseFamilies, true, selection);
    releasingFamily.setTabIndex(13);
    releasingFamily.addFormEvent("onChange", "ReleasingFamilyChange(this)");
    selectionForm.addElement(releasingFamily);
    String envId = String.valueOf(selectedProject.getMSEnvironmentId());
    String envName = MilestoneHelper.getStructureName(selectedProject.getMSEnvironmentId());
    String environmentName = "";
    FormHidden evironment = new FormHidden("environment", envId, false);
    evironment.setDisplayName(envName);
    selectionForm.addElement(evironment);
    String companyId = String.valueOf(selectedProject.getMSCompanyId());
    String companyName = MilestoneHelper.getStructureName(selectedProject.getMSCompanyId());
    FormHidden company = new FormHidden("company", companyId, false);
    company.setTabIndex(15);
    company.setDisplayName(companyName);
    selectionForm.addElement(company);
    String divisionId = String.valueOf(selectedProject.getMSDivisionId());
    String divisionName = MilestoneHelper.getStructureName(selectedProject.getMSDivisionId());
    FormHidden division = new FormHidden("division", divisionId, false);
    division.setTabIndex(16);
    division.setDisplayName(divisionName);
    selectionForm.addElement(division);
    String labelId = String.valueOf(selectedProject.getMSLabelId());
    String labelName = MilestoneHelper.getStructureName(selectedProject.getMSLabelId());
    FormHidden label = new FormHidden("label", labelId, false);
    label.setTabIndex(17);
    label.setDisplayName(labelName);
    selectionForm.addElement(label);
    if (selectedProject.getOperCompany().equals("***")) {
      FormHidden opercompany = new FormHidden("opercompany", "***", false);
      opercompany.setTabIndex(18);
      opercompany.setDisplayName("***");
      selectionForm.addElement(opercompany);
    } else {
      LookupObject oc = MilestoneHelper.getLookupObject(selectedProject
          .getOperCompany(), Cache.getOperatingCompanies());
      String ocAbbr = "";
      String ocName = "";
      if (oc == null) {
        ocAbbr = selectedProject.getOperCompany();
      } else {
        if (oc != null && oc.getAbbreviation() != null)
          ocAbbr = oc.getAbbreviation(); 
        if (oc != null && oc.getName() != null)
          ocName = ":" + oc.getName(); 
      } 
      FormHidden opercompany = new FormHidden("opercompany", ocAbbr, false);
      opercompany.setTabIndex(18);
      opercompany.setDisplayName(String.valueOf(ocAbbr) + ocName);
      if (ocAbbr.equals("ZZ"))
        opercompany.setDisplayName(ocAbbr); 
      selectionForm.addElement(opercompany);
    } 
    FormHidden superlabel = new FormHidden("superlabel", selectedProject.getSuperLabel(), false);
    superlabel.setTabIndex(19);
    superlabel.setClassName("ctrlShort");
    superlabel.addFormEvent("onBlur", "javascript:this.value=this.value.toUpperCase();");
    selectionForm.addElement(superlabel);
    FormHidden distCoLabelID = new FormHidden("distCoLabelID", labelId, false);
    distCoLabelID.setDisplayName(labelId);
    selectionForm.addElement(distCoLabelID);
    FormHidden sublabel = new FormHidden("sublabel", selectedProject.getSubLabel(), false);
    sublabel.setTabIndex(20);
    sublabel.setClassName("ctrlShort");
    sublabel.addFormEvent("onBlur", "javascript:this.value=this.value.toUpperCase();");
    selectionForm.addElement(sublabel);
    FormDropDownMenu configcode = MilestoneHelper.getPfmLookupDropDown("configcode", MilestoneHelper.getConfigCodes(0), "", false, true);
    configcode.setTabIndex(21);
    configcode.addFormEvent("onKeyPress", "scanKey(window.event.keyCode, 'document.all.configcode.options', getMaxLength(document.all.configcode.options))");
    configcode.addFormEvent("onChange", "setNoDigitalRelease(this);");
    selectionForm.addElement(configcode);
    Vector jdeExceptionFamilies = ProjectSearchManager.getInstance().getProjectSearchJDEFamilies();
    boolean isUmvdUser = jdeExceptionFamilies.contains(new Integer(selectedProject.getMSFamilyId()));
    String imprintStr = "";
    if (isUmvdUser) {
      imprintStr = labelName;
    } else {
      imprintStr = (selectedProject.getImprint() != null) ? selectedProject.getImprint() : "";
    } 
    FormTextField imprint = new FormTextField("imprint", imprintStr, false, 50);
    imprint.setTabIndex(21);
    imprint.setClassName("ctrlMedium");
    imprint.addFormEvent("onChange", "javaScript:removeSpaces(this);");
    selectionForm.addElement(imprint);
    String projectIdStr = "";
    projectIdStr = selectedProject.getRMSProjectNo();
    FormHidden projectId = new FormHidden("projectId", projectIdStr, false);
    projectId.setTabIndex(22);
    projectId.setClassName("ctrlMedium");
    projectId.setDisplayName(projectIdStr);
    selectionForm.addElement(projectId);
    FormTextField gridNumber = new FormTextField("gridNumber", selection.getGridNumber(), false, 50);
    gridNumber.setTabIndex(25);
    gridNumber.setEnabled(true);
    selectionForm.addElement(gridNumber);
    FormTextField upc = new FormTextField("UPC", "", false, 17, 20);
    upc.setTabIndex(23);
    upc.setClassName("ctrlMedium");
    upc.addFormEvent("onKeyPress", "return checkNumbersOnly(window.event.keyCode);");
    selectionForm.addElement(upc);
    FormTextField soundscan = new FormTextField("soundscan", "", false, 17, 20);
    soundscan.setTabIndex(24);
    soundscan.setClassName("ctrlMedium");
    soundscan.addFormEvent("onKeyPress", "return checkNumbersOnly(window.event.keyCode);");
    selectionForm.addElement(soundscan);
    FormDropDownMenu prefix = MilestoneHelper.getPrefixCodeDropDown(userId, "prefix", Cache.getPrefixCodes(), "", true, context);
    prefix.setTabIndex(25);
    prefix.setClassName("ctrlShort");
    selectionForm.addElement(prefix);
    FormTextField selectionNo = new FormTextField("selectionNo", "", false, 20, 20);
    selectionNo.setTabIndex(26);
    selectionNo.setClassName("ctrlMedium");
    selectionNo.addFormEvent("onChange", "javaScript:removeSpaces(this);");
    selectionForm.addElement(selectionNo);
    FormTextField titleId = new FormTextField("titleId", "", false, 13, 24);
    titleId.setClassName("ctrlMedium");
    titleId.setTabIndex(27);
    selectionForm.addElement(titleId);
    FormDropDownMenu productLine = MilestoneHelper.getLookupDropDown("productLine", Cache.getProductCategories(0), "", true, true);
    productLine.setTabIndex(28);
    productLine.setClassName("ctrlMedium");
    selectionForm.addElement(productLine);
    FormDropDownMenu releaseType = MilestoneHelper.getLookupDropDown("releaseType", Cache.getReleaseTypes(), "", true, true);
    releaseType.setTabIndex(29);
    releaseType.setClassName("ctrlMedium");
    releaseType.addFormEvent("onChange", "releaseTypeChanged()");
    selectionForm.addElement(releaseType);
    FormDropDownMenu configuration = MilestoneHelper.getSelectionConfigurationDropDown("configuration", "", true, 0);
    configuration.setTabIndex(30);
    configuration.addFormEvent("onChange", "buildSubConfigs(this.selectedIndex)");
    selectionForm.addElement(configuration);
    Vector configs = Cache.getSelectionConfigs();
    SelectionConfiguration config = (SelectionConfiguration)configs.get(0);
    FormDropDownMenu subConfiguration = new FormDropDownMenu("subConfiguration", "", "", "", true);
    subConfiguration.addFormEvent("onChange", "isSubConfigParent(this.selectedIndex)");
    subConfiguration.setTabIndex(31);
    subConfiguration.setEnabled(false);
    selectionForm.addElement(subConfiguration);
    FormTextField test = new FormTextField("test", "", false, 8, 8);
    test.setTabIndex(32);
    test.setClassName("ctrlShort");
    test.addFormEvent("onChange", "javaScript:clickSell(this,false);");
    selectionForm.addElement(test);
    FormDropDownMenu priceCode = new FormDropDownMenu("priceCode", "", "-1" + getSellCodesString(), "&nbsp;" + getSellCodesString(), true);
    priceCode.setTabIndex(33);
    priceCode.setClassName("ctrlSmall");
    selectionForm.addElement(priceCode);
    FormTextField testDPC = new FormTextField("testDPC", "", false, 8, 8);
    testDPC.setTabIndex(39);
    testDPC.setClassName("ctrlShort");
    testDPC.addFormEvent("onChange", "javaScript:clickSellDPC(this);");
    selectionForm.addElement(testDPC);
    FormDropDownMenu priceCodeDPC = new FormDropDownMenu("priceCodeDPC", "", "-1" + getSellCodesStringDPC(), "&nbsp;" + getSellCodesStringDPC(), false);
    priceCodeDPC.setTabIndex(39);
    priceCodeDPC.setClassName("ctrlSmall");
    selectionForm.addElement(priceCodeDPC);
    FormTextField numOfUnits = new FormTextField("numOfUnits", "0", false, 10, 10);
    numOfUnits.setTabIndex(34);
    numOfUnits.setClassName("ctrlShort");
    selectionForm.addElement(numOfUnits);
    FormDropDownMenu contactList = MilestoneHelper.getContactsDropDown(context, "contactlist", new Vector(), user, true);
    contactList.setTabIndex(35);
    contactList.setClassName("ctrlMedium");
    selectionForm.addElement(contactList);
    FormTextField contact = new FormTextField("contact", "", false, 14, 30);
    contact.setTabIndex(36);
    contact.setClassName("ctrlMedium");
    selectionForm.addElement(contact);
    FormCheckBox parentalIndicator = new FormCheckBox("parentalIndicator", "", false, false);
    parentalIndicator.setTabIndex(37);
    selectionForm.addElement(parentalIndicator);
    FormCheckBox specPkgIndicator = new FormCheckBox("specialPkgIndicator", "", false, false);
    specPkgIndicator.addFormEvent("onDblClick", "document.forms[0].PackagingHelper.value=document.forms[0].package.value;togglePackaging();");
    specPkgIndicator.setTabIndex(38);
    selectionForm.addElement(specPkgIndicator);
    FormTextField pkg = new FormTextField("package", "", false, 13, 100);
    pkg.setTabIndex(39);
    pkg.setClassName("ctrlMedium");
    pkg.addFormEvent("onDblClick", "document.forms[0].PackagingHelper.value=document.forms[0].package.value;togglePackaging();");
    selectionForm.addElement(pkg);
    FormDropDownMenu genre = MilestoneHelper.getPfmLookupDropDown("genre", Cache.getMusicTypes(), "", true, true);
    genre.setTabIndex(40);
    genre.setId("music_type");
    selectionForm.addElement(genre);
    FormTextField territory = new FormTextField("territory", "", false, 13, 255);
    territory.setTabIndex(41);
    territory.setClassName("ctrlMedium");
    territory.addFormEvent("onDblClick", "document.forms[0].TerritoryHelper.value=document.forms[0].territory.value;toggleTerritory();");
    selectionForm.addElement(territory);
    FormHidden productionGroupCode = new FormHidden("productGroupCode", selection.getProductionGroupCode(), false);
    productionGroupCode.setTabIndex(42);
    productionGroupCode.setDisplayName(selection.getProductionGroupCode());
    productionGroupCode.setClassName("ctrlMedium");
    selectionForm.addElement(productionGroupCode);
    FormTextField lastStreetUpdatedDate = new FormTextField("laststreetupdateddate", "", false, 13);
    selectionForm.addElement(lastStreetUpdatedDate);
    FormTextField lastUpdatedDate = new FormTextField("lastupdateddate", "", false, 50);
    selectionForm.addElement(lastUpdatedDate);
    FormTextField originDate = new FormTextField("origindate", "", false, 13);
    selectionForm.addElement(originDate);
    FormTextArea packagingHelper = new FormTextArea("PackagingHelper", "", false, 2, 44, "virtual");
    selectionForm.addElement(packagingHelper);
    FormTextArea territoryHelper = new FormTextArea("TerritoryHelper", selection.getSelectionTerritory(), false, 2, 44, "virtual");
    selectionForm.addElement(territoryHelper);
    FormTextArea comments = new FormTextArea("comments", "", false, 2, 44, "virtual");
    comments.addFormEvent("onBlur", "Javascript:checkField(this)");
    selectionForm.addElement(comments);
    addSelectionSearchElements(context, new Selection(), selectionForm, MilestoneHelper.getUserCompanies(context), true);
    if (context.getSessionValue("NOTEPAD_SELECTION_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_SELECTION_VISIBLE")); 
    context.putDelivery("releaseWeek", "");
    context.putDelivery("new-or-copy", "true");
    context.putDelivery("price", "0.00");
    boolean isParent = false;
    if (selection.getSelectionSubConfig() != null)
      isParent = selection.getSelectionSubConfig().isParent(); 
    context.putDelivery("is-parent", String.valueOf(isParent));
    String lastUpdateUser = "";
    if (selection.getLastUpdatingUser() != null)
      lastUpdateUser = selection.getLastUpdatingUser().getName(); 
    context.putDelivery("lastUpdateUser", lastUpdateUser);
    return selectionForm;
  }
  
  protected Form buildManufacturingForm(Context context, Selection selection, String command, int accessLevel, boolean newFlag) {
    String mainCommandString = "";
    String holdReasonString = "";
    String distributionString = "";
    String mfgCommentsString = "";
    String numberOfUnits = "0";
    User umlContactUser = null;
    String selectedConfig = "";
    String selectedSubConfig = "";
    if (selection.getSelectionConfig() != null && selection.getSelectionConfig().getSelectionConfigurationAbbreviation() != null)
      selectedConfig = selection.getSelectionConfig().getSelectionConfigurationAbbreviation(); 
    if (selection.getSelectionSubConfig() != null && selection.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation() != null)
      selectedSubConfig = selection.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation(); 
    Form selectionForm = new Form(this.application, "selectionForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    if (!newFlag) {
      if (selection != null) {
        mainCommandString = "selection-manufacturing-editor";
        if (selection.getUmlContact() != null)
          umlContactUser = selection.getUmlContact(); 
        if (selection.getManufacturingComments() != null && selection.getManufacturingComments() != null)
          mfgCommentsString = selection.getManufacturingComments(); 
        if (selection.getNumberOfUnits() > 0)
          numberOfUnits = Integer.toString(selection.getNumberOfUnits()); 
        if (selection.getDistribution() != null)
          distributionString = selection.getDistribution().getAbbreviation(); 
        selectionForm.addElement(new FormHidden("new", "false"));
      } 
    } else {
      if (selection.getNumberOfUnits() > 0)
        numberOfUnits = Integer.toString(selection.getNumberOfUnits()); 
      mainCommandString = "selection-manufacturing-edit-new";
      selectionForm.addElement(new FormHidden("new", "true"));
    } 
    if (selection != null) {
      String lastMfgUpdatedDateText = "";
      if (selection.getLastMfgUpdateDate() != null)
        lastMfgUpdatedDateText = MilestoneHelper.getCustomFormatedDate(selection.getLastMfgUpdateDate(), "M/d/yyyy hh:mm:ss a 'ET'"); 
      context.putDelivery("lastUpdateDate", lastMfgUpdatedDateText);
      String lastMfgUpdateUser = "";
      if (selection.getLastMfgUpdatingUser() != null)
        lastMfgUpdateUser = selection.getLastMfgUpdatingUser().getName(); 
      context.putDelivery("lastUpdateUser", lastMfgUpdateUser);
      if (numberOfUnits.equals("0"))
        numberOfUnits = ""; 
      context.putDelivery("numberOfUnits", numberOfUnits);
      context.putDelivery("upc", selection.getUpc());
      context.putDelivery("label", selection.getImprint());
      context.putDelivery("status", selection.getSelectionStatus().getName());
      String typeConfig = String.valueOf(selection.getProductCategory().getName()) + " / " + selection.getReleaseType().getName() + " / " + selection.getSelectionConfig().getSelectionConfigurationName() + " / " + selection.getSelectionSubConfig().getSelectionSubConfigurationName();
      context.putDelivery("typeConfig", typeConfig);
      FormTextArea comments = new FormTextArea("comments", selection.getSelectionComments(), false, 2, 44, "virtual");
      comments.addFormEvent("onBlur", "Javascript:checkField(this)");
      comments.setReadOnly(true);
      selectionForm.addElement(comments);
      context.putDelivery("releaseWeek", MilestoneHelper.getReleaseWeekString(selection));
      context.putDelivery("releasingFamily", ReleasingFamily.getName(selection.getReleaseFamilyId()));
      selectionForm.addElement(new FormHidden("cmd", "selection-manufacturing-editor", true));
      selectionForm.addElement(new FormHidden("OrderBy", "", true));
      FormTextArea holdReason = new FormTextArea("holdReason", selection.getHoldReason(), false, 2, 44, "virtual");
      holdReason.setReadOnly(true);
      holdReason.addFormEvent("onBlur", "Javascript:checkField(this)");
      FormDropDownMenu umlContact = MilestoneHelper.getContactsDropDown(context, "umlcontact", Cache.getUmlUsers(), umlContactUser, true);
      umlContact.setId("umlcontact");
      FormDropDownMenu distribution = MilestoneHelper.getLookupDropDown("distribution", Cache.getDistributionCodes(), distributionString, true, true);
      distribution.setId("distribution");
      FormTextArea mfgcommentsTextArea = new FormTextArea("orderCommentHelper", mfgCommentsString, false, 2, 44, "virtual");
      Vector vendors = new Vector();
      if (selection.getManufacturingPlants() != null)
        vendors = selection.getManufacturingPlants(); 
      String vendorString = "";
      String poQtyNumber = "0";
      String completedQtyNumber = "0";
      String explodedTotal = "0";
      for (int vendorCount = 0; vendorCount < vendors.size(); vendorCount++) {
        vendorString = "";
        poQtyNumber = "0";
        completedQtyNumber = "0";
        explodedTotal = "0";
        Plant plant = (Plant)vendors.get(vendorCount);
        if (plant.getOrderQty() > 0)
          poQtyNumber = Integer.toString(plant.getOrderQty()); 
        if (plant.getCompletedQty() > 0)
          completedQtyNumber = Integer.toString(plant.getCompletedQty()); 
        if (plant.getOrderQty() > 0 && selection.getNumberOfUnits() > 0)
          explodedTotal = Integer.toString(plant.getCompletedQty() * selection.getNumberOfUnits()); 
        if (plant.getPlant() != null)
          vendorString = plant.getPlant().getAbbreviation(); 
        FormDropDownMenu vendor = MilestoneHelper.getLookupDropDown("plant" + vendorCount, Cache.getVendors(), vendorString, true, true);
        vendor.setId("plant");
        FormTextField poQty = new FormTextField("po_qty" + vendorCount, poQtyNumber, true, 8);
        poQty.setId("po_qty");
        poQty.addFormEvent("align", "right");
        FormTextField completedQty = new FormTextField("completed_qty" + vendorCount, completedQtyNumber, true, 8);
        completedQty.setId("completed_qty");
        completedQty.addFormEvent("align", "right");
        FormTextField explode = new FormTextField("explode" + vendorCount, explodedTotal, true, 8);
        explode.setId("explode");
        explode.addFormEvent("align", "right");
        selectionForm.addElement(vendor);
        selectionForm.addElement(poQty);
        selectionForm.addElement(completedQty);
        selectionForm.addElement(explode);
      } 
      selectionForm.addElement(holdReason);
      selectionForm.addElement(umlContact);
      selectionForm.addElement(distribution);
      selectionForm.addElement(comments);
      selectionForm.addElement(mfgcommentsTextArea);
    } 
    User user = (User)context.getSessionValue("user");
    int secureLevel = getSelectionMfgPermissions(selection, user);
    setButtonVisibilities(selection, user, context, secureLevel, command);
    context.putDelivery("releaseWeek", MilestoneHelper.getReleaseWeekString(selection));
    Vector companies = MilestoneHelper.getUserCompanies(context);
    addSelectionSearchElements(context, selection, selectionForm, companies, true);
    if (context.getSessionValue("NOTEPAD_MFG_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_MFG_VISIBLE")); 
    return selectionForm;
  }
  
  protected static void addSelectionSearchElements(Context context, Selection selection, Form selectionForm, Vector companies, boolean includeJSArrays) {
    User user = (User)context.getSessionValue("user");
    if (includeJSArrays) {
      String selectedConfig = "";
      String selectedSubConfig = "";
      if (selection != null && selection.getSelectionConfig() != null && selection.getSelectionConfig().getSelectionConfigurationAbbreviation() != null)
        selectedConfig = selection.getSelectionConfig().getSelectionConfigurationAbbreviation(); 
      if (selection != null && selection.getSelectionSubConfig() != null && selection.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation() != null)
        selectedSubConfig = selection.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation(); 
      context.putDelivery("selectionArrays", String.valueOf(Cache.getJavaScriptConfigArray(selectedConfig)) + 
          
          " " + Cache.getJavaScriptPriceCodeArray() + " " + Cache.getJavaScriptPriceCodeDPCArray() + " " + Cache.getJavaScriptSubConfigArray(selectedSubConfig) + 
          " " + ReleasingFamily.getJavaScriptCorporateArrayReleasingFamilySearch(context) + 
          
          " " + Cache.getJavaScriptPFMConfigs());
    } 
    boolean defaultStatus = false;
    FormCheckBox showAllSearch = new FormCheckBox("ShowAllSearch", "", false, defaultStatus);
    showAllSearch.setId("ShowAllSearch");
    selectionForm.addElement(showAllSearch);
    Vector families = filterCSO(ReleasingFamily.getUserReleasingFamiliesVectorOfFamilies(context));
    String defaultReleasingFamily = "-1";
    FormDropDownMenu Family = MilestoneHelper.getCorporateStructureDropDown("FamilySearch", families, defaultReleasingFamily, false, true);
    Family.addFormEvent("onChange", "return(clickFamilySearch(this))");
    Family.setId("FamilySearch");
    selectionForm.addElement(Family);
    Vector environments = MilestoneHelper.getUserEnvironments(context);
    Vector myCompanies = MilestoneHelper.getUserCompanies(context);
    environments = filterSelectionEnvironments(myCompanies);
    environments = MilestoneHelper.removeUnusedCSO(environments, context, -1);
    String defaultEnvironment = "-1";
    FormDropDownMenu envMenu = MilestoneHelper.getCorporateStructureDropDown("EnvironmentSearch", environments, defaultEnvironment, false, true);
    envMenu.addFormEvent("onChange", "return(clickEnvironmentSearch(this))");
    envMenu.setId("EnvironmentSearch");
    selectionForm.addElement(envMenu);
    Vector searchCompanies = null;
    searchCompanies = MilestoneHelper.getUserCompanies(context);
    searchCompanies = MilestoneHelper.removeUnusedCSO(searchCompanies, context, -1);
    FormDropDownMenu companySearch = MilestoneHelper.getCorporateStructureDropDown("CompanySearch", searchCompanies, "", false, true);
    companySearch.setId("CompanySearch");
    companySearch.addFormEvent("onChange", "return(clickCompanySearch(this))");
    selectionForm.addElement(companySearch);
    Vector labels = MilestoneHelper.getUserLabels(companies);
    labels = MilestoneHelper.removeUnusedCSO(labels, context, -1);
    FormDropDownMenu labelSearch = MilestoneHelper.getCorporateStructureDropDownDuplicates("LabelSearch", labels, "", false, true, false);
    labelSearch.setId("LabelSearch");
    selectionForm.addElement(labelSearch);
    User defaultContact = null;
    Vector labelContacts = SelectionManager.getLabelContactsExcludeUml(context);
    FormDropDownMenu searchContact = MilestoneHelper.getContactsDropDown(context, "ContactSearch", labelContacts, defaultContact, true);
    selectionForm.addElement(searchContact);
    FormTextField streetDateSearch = new FormTextField("StreetDateSearch", "", false, 14, 10);
    streetDateSearch.addFormEvent("onBlur", "JavaScript:removeSpacesInDate(document.forms[0].StreetDateSearch.value,this)");
    streetDateSearch.setId("StreetDateSearch");
    selectionForm.addElement(streetDateSearch);
    FormTextField streetEndDateSearch = new FormTextField("StreetEndDateSearch", "", false, 14, 10);
    streetEndDateSearch.addFormEvent("onBlur", "JavaScript:removeSpacesInDate(document.forms[0].StreetEndDateSearch.value,this)");
    streetEndDateSearch.setId("StreetEndDateSearch");
    selectionForm.addElement(streetEndDateSearch);
    String[] dvalues = new String[3];
    dvalues[0] = "physical";
    dvalues[1] = "digital";
    dvalues[2] = "both";
    String[] dlabels = new String[3];
    dlabels[0] = "Physical";
    dlabels[1] = "Digital";
    dlabels[2] = "Both";
    String defaultProdType = "both";
    FormRadioButtonGroup prodType = new FormRadioButtonGroup("ProdType", defaultProdType, dvalues, dlabels, false);
    prodType.addFormEvent("onClick", "buildSearchConfigs(this)");
    selectionForm.addElement(prodType);
    Vector searchConfigs = null;
    searchConfigs = Cache.getSelectionConfigs();
    FormDropDownMenu configSearch = MilestoneHelper.getSelectionConfigurationDropDown("ConfigSearch", "", false);
    configSearch.setId("ConfigSearch");
    configSearch.addFormEvent("onChange", "buildSearchSubConfigs(this.selectedIndex)");
    selectionForm.addElement(configSearch);
    FormDropDownMenu subconfigSearch = new FormDropDownMenu("SubconfigSearch", "");
    subconfigSearch.setId("SubconfigSearch");
    subconfigSearch.setEnabled(false);
    selectionForm.addElement(subconfigSearch);
    FormTextField upcSearch = new FormTextField("UPCSearch", "", false, 20, 20);
    upcSearch.setId("UPCSearch");
    selectionForm.addElement(upcSearch);
    FormTextField prefixSearch = new FormTextField("PrefixSearch", "", false, 6, 5);
    prefixSearch.setId("PrefixSearch");
    selectionForm.addElement(prefixSearch);
    FormTextField selectionSearch = new FormTextField("SelectionSearch", "", false, 12, 20);
    selectionSearch.setId("SelectionSearch");
    selectionSearch.setClassName("ctrlMedium");
    selectionForm.addElement(selectionSearch);
    FormTextField titleSearch = new FormTextField("TitleSearch", "", false, 20);
    titleSearch.setId("TitleSearch");
    selectionForm.addElement(titleSearch);
    FormTextField artistSearch = new FormTextField("ArtistSearch", "", false, 20);
    artistSearch.setId("ArtistSearch");
    selectionForm.addElement(artistSearch);
    FormTextField projectIDSearch = new FormTextField("ProjectIDSearch", "", false, 20);
    projectIDSearch.setId("ProjectIDSearch");
    selectionForm.addElement(projectIDSearch);
    getUserPreferences(selectionForm, context);
  }
  
  public static void getUserPreferences(Form form, Context context) {
    User user = (User)context.getSessionValue("user");
    if (user != null && user.getPreferences() != null) {
      String defaultStr = "";
      User userSrch = (User)context.getSessionValue("ResetSearchVariables");
      if (userSrch != null)
        resetSearchVariables(user, userSrch, context); 
      if (!user.SS_searchInitiated) {
        if (user.getPreferences().getSelectionStatus() > 0) {
          ((FormCheckBox)form.getElement("ShowAllSearch")).setChecked(true);
          user.SS_showAllSearch = "true";
        } 
        if (user.getPreferences().getSelectionReleasingFamily() > 0) {
          defaultStr = String.valueOf(user.getPreferences().getSelectionReleasingFamily());
          ((FormDropDownMenu)form.getElement("FamilySearch")).setValue(defaultStr);
          user.SS_familySearch = defaultStr;
        } 
        if (user.getPreferences().getSelectionEnvironment() > 0) {
          defaultStr = String.valueOf(user.getPreferences().getSelectionEnvironment());
          ((FormDropDownMenu)form.getElement("EnvironmentSearch")).setValue(defaultStr);
          user.SS_environmentSearch = defaultStr;
        } 
        if (user.getPreferences().getSelectionLabelContact() > 0) {
          defaultStr = String.valueOf(user.getPreferences().getSelectionLabelContact());
          ((FormDropDownMenu)form.getElement("ContactSearch")).setValue(defaultStr);
          user.SS_contactSearch = defaultStr;
        } 
        if (user.getPreferences().getSelectionProductType() > -1) {
          if (user.getPreferences().getSelectionProductType() == 0)
            defaultStr = "physical"; 
          if (user.getPreferences().getSelectionProductType() == 1)
            defaultStr = "digital"; 
          if (user.getPreferences().getSelectionProductType() == 2)
            defaultStr = "both"; 
          ((FormRadioButtonGroup)form.getElement("ProdType")).setValue(defaultStr);
          user.SS_productTypeSearch = defaultStr;
        } 
      } else {
        if (user.SS_showAllSearch.equals("true"))
          ((FormCheckBox)form.getElement("ShowAllSearch")).setChecked(true); 
        if (!user.SS_familySearch.equals(""))
          ((FormDropDownMenu)form.getElement("FamilySearch")).setValue(user.SS_familySearch); 
        if (!user.SS_environmentSearch.equals(""))
          ((FormDropDownMenu)form.getElement("EnvironmentSearch")).setValue(user.SS_environmentSearch); 
        if (!user.SS_contactSearch.equals(""))
          ((FormDropDownMenu)form.getElement("ContactSearch")).setValue(user.SS_contactSearch); 
        if (!user.SS_productTypeSearch.equals(""))
          ((FormRadioButtonGroup)form.getElement("ProdType")).setValue(user.SS_productTypeSearch); 
      } 
      user.SS_searchInitiated = true;
    } 
  }
  
  public String getJavaScriptCorporateArray(Context context) {
    StringBuffer result = new StringBuffer(100);
    String str = "";
    String value = new String();
    User user = (User)context.getSessionValue("user");
    Vector vUserCompanies = (Vector)MilestoneHelper.getUserCompanies(context).clone();
    Vector vUserEnvironments = filterSelectionEnvironments(vUserCompanies);
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
  
  public static String getSearchJavaScriptCorporateArray(Context context) {
    StringBuffer result = new StringBuffer(100);
    String str = "";
    String value = new String();
    boolean foundFirstTemp = false;
    User user = (User)context.getSessionValue("user");
    Vector vUserCompanies = MilestoneHelper.getUserCompanies(context);
    Vector vUserEnvironments = filterSelectionEnvironments(vUserCompanies);
    HashMap corpHashMap = MilestoneHelper.buildActiveCorporateStructureHashMap();
    Hashtable labelsHash = new Hashtable();
    result.append("\n");
    result.append("var aSearch = new Array();\n");
    int arrayIndex = 0;
    result.append("aSearch[0] = new Array(");
    result.append(0);
    result.append(", '");
    result.append("All");
    result.append("'");
    foundFirstTemp = true;
    for (int a = 0; a < vUserCompanies.size(); a++) {
      Company ueTemp = (Company)vUserCompanies.elementAt(a);
      if (ueTemp != null) {
        Vector labels = Cache.getInstance().getLabels();
        for (int b = 0; b < labels.size(); b++) {
          Label node = (Label)labels.elementAt(b);
          if (node.getParent().getParentID() == ueTemp.getStructureID() && 
            !corpHashMap.containsKey(new Integer(node.getStructureID()))) {
            String labelName = MilestoneHelper.urlEncode(node.getName());
            if (!labelsHash.containsKey(labelName)) {
              labelsHash.put(labelName, Integer.toString(node.getStructureID()));
            } else {
              String hashValue = (String)labelsHash.get(labelName);
              hashValue = String.valueOf(hashValue) + "," + Integer.toString(node.getStructureID());
              labelsHash.put(labelName, hashValue);
            } 
            foundFirstTemp = true;
          } 
        } 
      } 
    } 
    if (!foundFirstTemp) {
      result.append("'[none available]');\n");
    } else {
      boolean firstPass = false;
      String[] labelKeys = new String[labelsHash.size()];
      int x = 0;
      for (Enumeration e = labelsHash.keys(); e.hasMoreElements(); x++) {
        String hashKey = (String)e.nextElement();
        labelKeys[x] = hashKey;
      } 
      for (int h = 0; h < labelKeys.length; h++) {
        String hashValue = (String)labelsHash.get(labelKeys[h]);
        result.append(',');
        result.append(' ');
        result.append("'" + hashValue + "'");
        result.append(", '");
        result.append(labelKeys[h]);
        result.append('\'');
        firstPass = true;
      } 
      result.append(");\n");
    } 
    for (int i = 0; i < vUserCompanies.size(); i++) {
      Company ue = (Company)vUserCompanies.elementAt(i);
      if (ue != null) {
        result.append("aSearch[");
        result.append(ue.getStructureID());
        result.append("] = new Array(");
        boolean foundFirst = false;
        result.append(0);
        result.append(", '");
        result.append("All");
        result.append("'");
        foundFirst = true;
        Vector tmpArray = new Vector();
        labelsHash.clear();
        Vector labels = Cache.getInstance().getLabels();
        for (int j = 0; j < labels.size(); j++) {
          Label node = (Label)labels.elementAt(j);
          if (node.getParent().getParentID() == ue.getStructureID() && 
            !corpHashMap.containsKey(new Integer(node.getStructureID()))) {
            String labelName = MilestoneHelper.urlEncode(node.getName());
            if (!labelsHash.containsKey(labelName)) {
              labelsHash.put(labelName, Integer.toString(node.getStructureID()));
            } else {
              String hashValue = (String)labelsHash.get(labelName);
              hashValue = String.valueOf(hashValue) + "," + Integer.toString(node.getStructureID());
              labelsHash.put(labelName, hashValue);
            } 
            foundFirst = true;
            tmpArray.addElement(node);
          } 
        } 
        if (foundFirst) {
          boolean firstPass = false;
          String[] labelKeys = new String[labelsHash.size()];
          int x = 0;
          for (Enumeration e = labelsHash.keys(); e.hasMoreElements(); x++) {
            String hashKey = (String)e.nextElement();
            labelKeys[x] = hashKey;
          } 
          for (int h = 0; h < labelKeys.length; h++) {
            String hashValue = (String)labelsHash.get(labelKeys[h]);
            result.append(',');
            result.append(' ');
            result.append("'" + hashValue + "'");
            result.append(", '");
            result.append(labelKeys[h]);
            result.append('\'');
            firstPass = true;
          } 
          result.append(");\n");
        } else {
          result.append(" 0, '[none available]');\n");
        } 
      } 
    } 
    return result.toString();
  }
  
  private Context addMfgInfo(Context context, Selection selection) {
    String explodedTotal = Integer.toString(selection.getPoQuantity() * selection.getNumberOfUnits());
    context.putDelivery("explodedtotal", explodedTotal);
    context.putDelivery("upc", selection.getUpc());
    context.putDelivery("label", selection.getLabel().getName());
    context.putDelivery("status", selection.getSelectionStatus().getName());
    String typeConfig = String.valueOf(selection.getProductCategory().getName()) + "/" + selection.getReleaseType().getName() + " / " + selection.getSelectionConfig().getSelectionConfigurationName() + " / " + selection.getSelectionSubConfig().getSelectionSubConfigurationName();
    context.putDelivery("typeConfig", typeConfig);
    String lastMfgUpdatedDateText = "";
    if (selection.getLastMfgUpdateDate() != null)
      lastMfgUpdatedDateText = MilestoneHelper.getCustomFormatedDate(selection.getLastMfgUpdateDate(), "M/d/yyyy hh:mm:ss a 'ET'"); 
    context.putDelivery("lastUpdateDate", lastMfgUpdatedDateText);
    String lastMfgUpdateUser = "";
    if (selection.getLastMfgUpdatingUser() != null)
      lastMfgUpdateUser = selection.getLastMfgUpdatingUser().getName(); 
    context.putDelivery("lastUpdateUser", lastMfgUpdateUser);
    return context;
  }
  
  private String getFilteredSellCodes(String selected) {
    sellCodeText = "";
    Vector vSellCodeList = Cache.getSellCodes();
    for (int j = 0; j < vSellCodeList.size(); j++) {
      String pc = (String)vSellCodeList.elementAt(j);
      if (pc.substring(0, selected.length()).equalsIgnoreCase(selected))
        sellCodeText = String.valueOf(sellCodeText) + pc + ","; 
    } 
    return "," + sellCodeText.substring(0, sellCodeText.length() - 1);
  }
  
  public String getSellCodesString() {
    sellCodeText = "";
    Vector vSellCodeList = Cache.getSellCodes();
    for (int i = 0; i < vSellCodeList.size(); i++)
      sellCodeText = String.valueOf(sellCodeText) + (String)vSellCodeList.get(i) + ","; 
    return "," + sellCodeText.substring(0, sellCodeText.length() - 1);
  }
  
  public String getSellCodesStringDPC() {
    String sellCodeText = "";
    Vector vSellCodeList = Cache.getSellCodesDPC();
    for (int i = 0; i < vSellCodeList.size(); i++)
      sellCodeText = String.valueOf(sellCodeText) + (String)vSellCodeList.get(i) + ","; 
    if (sellCodeText.length() > 1)
      sellCodeText = "," + sellCodeText.substring(0, sellCodeText.length() - 1); 
    return sellCodeText;
  }
  
  private String FormatForLegacy(String strIn) {
    String strOut = "";
    char[] arr = strIn.toCharArray();
    for (int i = 0; i < arr.length; i++) {
      if (arr[i] != '-' && arr[i] != ' ' && arr[i] != '/')
        strOut = String.valueOf(strOut) + arr[i]; 
    } 
    return strOut;
  }
  
  private String applyBusinessRules(Form form, Context context, Selection selection) {
    String RetValue = "";
    strError = "";
    String titleId = form.getStringValue("titleId");
    String selectionNo = form.getStringValue("selectionNo");
    String upc = form.getStringValue("upc");
    String prefix = "";
    String strDate = "";
    String status = (selection.getSelectionStatus() != null && selection.getSelectionStatus().getName() != null) ? 
      selection.getSelectionStatus().getName() : "";
    if (!form.getStringValue("prefix").equals("-1"))
      prefix = form.getStringValue("prefix"); 
    if (context.getParameter("generateSelection") != null && 
      context.getParameter("generateSelection").equalsIgnoreCase("LPNG")) {
      selection.setPrefixID(null);
      String strProjectID = FormatForLegacy(selection.getProjectID());
      String strUPC = FormatForLegacy(selection.getUpc());
      String strSoundScan = FormatForLegacy(selection.getSoundScanGrp());
      if (selection.getConfigCode().startsWith("S"))
        titleId = ""; 
      String strTitleID = "";
      for (int j = 0; j < titleId.length(); j++) {
        if (titleId.charAt(j) != '-')
          strTitleID = String.valueOf(strTitleID) + titleId.charAt(j); 
      } 
      if (selection.getStreetDateString().equals("") && 
        selection.getSelectionStatus().getAbbreviation().equalsIgnoreCase("TBS")) {
        strDate = "12/31/39";
      } else {
        strDate = selection.getStreetDateString().trim();
      } 
      PnrCommunication pnr = PnrCommunication.getInstance();
      String userIdStr = ((User)context.getSession().getAttribute("user")).getLogin();
      if (userIdStr == null || userIdStr.equals(""))
        userIdStr = "Mileston"; 
      String strReply = PnrCommunication.GetPNR(userIdStr, 
          selection.getOperCompany().trim(), 
          selection.getSuperLabel().trim(), 
          selection.getConfigCode().trim(), 
          strTitleID, 
          selection.getTitle().trim(), 
          selection.getArtistFirstName().trim(), 
          selection.getArtistLastName().trim(), 
          strDate, 
          selection.getSubLabel().trim(), 
          strProjectID.trim(), 
          strUPC.trim(), 
          strSoundScan.trim());
      if (strReply.indexOf("BROKER ERROR:") != -1) {
        strError = strReply.trim();
      } else {
        strError = strReply.substring(312, strReply.length());
      } 
      if (strError.trim().equals("")) {
        if (titleId.trim().equals("")) {
          titleId = strReply.substring(23, 33);
          titleId = String.valueOf(titleId.substring(0, titleId.length() - 2)) + "-" + titleId.substring(titleId.length() - 2, titleId.length());
        } 
        selectionNo = strReply.substring(33, 43);
      } else {
        return strError.replace('\'', ' ');
      } 
      selectionNo = String.valueOf(selectionNo.substring(0, selectionNo.length() - 2)) + "-" + selectionNo.substring(selectionNo.length() - 2, selectionNo.length());
    } else if (context.getParameter("generateSelection") != null && 
      context.getParameter("generateSelection").equalsIgnoreCase("TPNG")) {
      selectionNo = SelectionManager.getInstance().getSequencedSelectionNumber();
      if (selection.getReleaseType().getAbbreviation().equalsIgnoreCase("CO") && !selection.getConfigCode().startsWith("S") && (
        titleId.equals("") || titleId.startsWith("TEMP")))
        titleId = selectionNo; 
      if (selection.getReleaseType().getAbbreviation().equalsIgnoreCase("CO") && selection.getConfigCode().startsWith("S"))
        titleId = selectionNo; 
      if (selection.getReleaseType().getAbbreviation().equalsIgnoreCase("PR"))
        titleId = String.valueOf(prefix) + selectionNo; 
    } else {
      if (!status.equalsIgnoreCase("Cancelled") && SelectionManager.getInstance().isSelectionIDDuplicate(prefix, selectionNo, selection.getSelectionID(), selection.getIsDigital()))
        return "The Local Product No entered already exist in our database.  Please enter a new one and resubmit."; 
      if (!selection.getReleaseType().getAbbreviation().equalsIgnoreCase("PR")) {
        if (selection.getReleaseType().getAbbreviation().equalsIgnoreCase("CO") && selection.getConfigCode().startsWith("S"))
          titleId = String.valueOf(prefix) + selectionNo; 
        selection.setSelectionNo(selectionNo);
        selection.setTitleID(titleId);
        return RetValue;
      } 
      titleId = String.valueOf(prefix) + selectionNo;
    } 
    selection.setSelectionNo(selectionNo);
    selection.setTitleID(titleId);
    return RetValue;
  }
  
  public void setButtonVisibilities(Selection selection, User user, Context context, int level, String command) {
    String copyVisible = "false";
    String saveVisible = "false";
    String deleteVisible = "false";
    String newVisible = "false";
    if (level > 1) {
      saveVisible = "true";
      copyVisible = "true";
      deleteVisible = "true";
      if (selection.getSelectionID() > 0)
        newVisible = "true"; 
    } 
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
  
  public static int getSelectionPermissions(Selection selection, User user) {
    int level = 0;
    if (selection != null && selection.getSelectionID() > -1) {
      Environment env = selection.getEnvironment();
      CompanyAcl companyAcl = MilestoneHelper.getScreenPermissions(env, user);
      if (companyAcl != null)
        level = companyAcl.getAccessSelection(); 
    } 
    return level;
  }
  
  public static int getSelectionMfgPermissions(Selection selection, User user) {
    int level = 0;
    if (selection != null && selection.getSelectionID() > -1) {
      Environment env = selection.getEnvironment();
      CompanyAcl companyAcl = MilestoneHelper.getScreenPermissions(env, user);
      if (companyAcl != null)
        level = companyAcl.getAccessManufacturing(); 
    } 
    return level;
  }
  
  public static Vector filterSelectionCompanies(Vector companies) {
    Vector result = new Vector();
    if (companies != null && companies.size() > 0)
      for (int i = 0; i < companies.size(); i++) {
        Company company = (Company)companies.get(i);
        String name = company.getName();
        if (!name.equalsIgnoreCase("UML") && 
          !name.equalsIgnoreCase("Enterprise"))
          result.add(company); 
      }  
    return result;
  }
  
  public static Vector filterSelectionCompaniesWithEditRigthts(Vector companies, User user) {
    Vector result = new Vector();
    Vector cmpAclList = user.getAcl().getCompanyAcl();
    HashMap cmpEditRight = new HashMap();
    if (cmpAclList != null)
      for (int n = 0; n < cmpAclList.size(); n++) {
        CompanyAcl cmpAcl = (CompanyAcl)cmpAclList.get(n);
        if (cmpAcl.getAccessSelection() == 2)
          cmpEditRight.put(new Integer(cmpAcl.getCompanyId()), new Integer(n)); 
      }  
    if (companies != null && companies.size() > 0)
      for (int i = 0; i < companies.size(); i++) {
        Company company = (Company)companies.get(i);
        String name = company.getName();
        if (cmpAclList == null) {
          if (!name.equalsIgnoreCase("UML") && !name.equalsIgnoreCase("Enterprise"))
            result.add(company); 
        } else if (!name.equalsIgnoreCase("UML") && !name.equalsIgnoreCase("Enterprise") && 
          cmpEditRight.containsKey(new Integer(company.getStructureID()))) {
          result.add(company);
        } 
      }  
    return result;
  }
  
  public static Vector filterCSO(Vector csoVector) {
    Vector result = new Vector();
    if (csoVector != null && csoVector.size() > 0)
      for (int i = 0; i < csoVector.size(); i++) {
        CorporateStructureObject cso = (CorporateStructureObject)csoVector.get(i);
        String abbrev = cso.getStructureAbbreviation();
        if (!abbrev.equalsIgnoreCase("UML") && 
          !abbrev.equalsIgnoreCase("ENT"))
          result.add(cso); 
      }  
    return result;
  }
  
  private boolean goToBlank(Context context, Form form, User user) {
    form = new Form(this.application, "selectionForm", this.application.getInfrastructure().getServletURL(), "POST");
    form.addElement(new FormHidden("cmd", "selection-editor", true));
    form.addElement(new FormHidden("OrderBy", "", true));
    if (context.getSessionValue("NOTEPAD_SELECTION_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_SELECTION_VISIBLE")); 
    Vector companies = MilestoneHelper.getUserCompanies(context);
    addSelectionSearchElements(context, null, form, companies, true);
    context.putDelivery("Form", form);
    return context.includeJSP("blank-selection-editor.jsp");
  }
  
  private boolean multSelectionEditor(Dispatcher dispatcher, Context context, String command) {
    Form multSelectionForm = new Form(this.application, "multSelectionForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    Vector multSelections = null;
    User user = (User)context.getSession().getAttribute("user");
    Selection selection = (Selection)context.getSessionValue("Selection");
    int secureLevel = getSelectionPermissions(selection, user);
    setButtonVisibilities(selection, user, context, secureLevel, command);
    if (selection != null && selection.getMultSelections() != null) {
      multSelections = selection.getMultSelections();
      for (int j = 0; j < multSelections.size(); j++) {
        MultSelection multSel = (MultSelection)multSelections.get(j);
        FormTextField selectionNo = new FormTextField("selectionNo" + j, multSel.getSelectionNo(), false, 15, 20);
        FormTextField upc = new FormTextField("upc" + j, multSel.getUpc(), false, 18, 20);
        FormTextField description = new FormTextField("description" + j, multSel.getDescription(), false, 25, 100);
        upc.addFormEvent("onKeyPress", "return checkNumbersOnly(window.event.keyCode);");
        multSelectionForm.addElement(selectionNo);
        multSelectionForm.addElement(upc);
        multSelectionForm.addElement(description);
      } 
    } 
    multSelectionForm.addElement(new FormHidden("cmd", command, true));
    context.putDelivery("Selection", selection);
    context.putDelivery("Form", multSelectionForm);
    return context.includeJSP("multSelection-editor.jsp");
  }
  
  private boolean multSelectionEditorAdd(Dispatcher dispatcher, Context context, String command) {
    Form multSelectionForm = new Form(this.application, "multSelectionForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    User user = (User)context.getSession().getAttribute("user");
    Selection selection = (Selection)context.getSessionValue("Selection");
    Vector multSelections = null;
    multSelections = selection.getMultSelections();
    if (multSelections == null) {
      multSelections = new Vector();
      MultSelection temp = new MultSelection();
      temp.setRelease_id(selection.getSelectionID());
      multSelections.add(temp);
      selection.setMultSelections(multSelections);
    } else {
      MultSelection temp = new MultSelection();
      temp.setRelease_id(selection.getSelectionID());
      multSelections.add(temp);
      selection.setMultSelections(multSelections);
    } 
    if (selection != null && selection.getMultSelections() != null)
      for (int j = 0; j < multSelections.size(); j++) {
        MultSelection multSel = (MultSelection)multSelections.get(j);
        FormTextField selectionNo = new FormTextField("selectionNo" + j, multSel.getSelectionNo(), false, 15, 20);
        FormTextField upc = new FormTextField("upc" + j, multSel.getUpc(), false, 18, 20);
        FormTextField description = new FormTextField("description" + j, multSel.getDescription(), false, 25, 100);
        upc.addFormEvent("onKeyPress", "return checkNumbersOnly(window.event.keyCode);");
        multSelectionForm.addElement(selectionNo);
        multSelectionForm.addElement(upc);
        multSelectionForm.addElement(description);
      }  
    multSelectionForm.setValues(context);
    multSelectionForm.addElement(new FormHidden("cmd", command, true));
    context.putSessionValue("Selection", selection);
    context.putDelivery("Form", multSelectionForm);
    return context.includeJSP("multSelection-editor.jsp");
  }
  
  private boolean multSelectionEditorCancel(Dispatcher dispatcher, Context context, String command) {
    Form multSelectionForm = new Form(this.application, "multSelectionForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    User user = (User)context.getSession().getAttribute("user");
    Selection sessionSelection = (Selection)context.getSessionValue("Selection");
    Selection selection = null;
    if (sessionSelection.getSelectionID() < 0) {
      selection = sessionSelection;
    } else {
      selection = SelectionManager.getInstance().getSelectionHeader(sessionSelection.getSelectionID());
    } 
    Vector multSelections = null;
    if (selection != null) {
      multSelections = selection.getMultSelections();
      if (selection != null && selection.getMultSelections() != null) {
        Vector newMultSelections = new Vector();
        for (int j = 0; j < multSelections.size(); j++) {
          MultSelection multSel = (MultSelection)multSelections.get(j);
          if (sessionSelection.getSelectionID() >= 0 || 
            multSel.getSelectionNo() != null || multSel.getUpc() != null || 
            multSel.getDescription() != null) {
            FormTextField selectionNo = new FormTextField("selectionNo" + j, multSel.getSelectionNo(), false, 15, 20);
            FormTextField upc = new FormTextField("upc" + j, multSel.getUpc(), false, 18, 20);
            FormTextField description = new FormTextField("description" + j, multSel.getDescription(), false, 25, 100);
            upc.addFormEvent("onKeyPress", "return checkNumbersOnly(window.event.keyCode);");
            multSelectionForm.addElement(selectionNo);
            multSelectionForm.addElement(upc);
            multSelectionForm.addElement(description);
            newMultSelections.add(multSel);
          } 
        } 
        selection.setMultSelections(newMultSelections);
      } 
    } 
    multSelectionForm.addElement(new FormHidden("cmd", command, true));
    context.putSessionValue("Selection", selection);
    context.putDelivery("Form", multSelectionForm);
    return true;
  }
  
  private boolean multSelectionEditorDelete(Dispatcher dispatcher, Context context, String command) {
    Form multSelectionForm = new Form(this.application, "multSelectionForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    Form newMultSelectionForm = new Form(this.application, "multSelectionForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    User user = (User)context.getSession().getAttribute("user");
    Selection selection = (Selection)context.getSessionValue("Selection");
    Vector multSelections = null;
    int multSelectionsPK = -1;
    if (context.getRequestValue("multSelectionsPK") != null)
      multSelectionsPK = Integer.parseInt(context.getRequestValue("multSelectionsPK")); 
    multSelections = selection.getMultSelections();
    if (selection != null && selection.getMultSelections() != null) {
      for (int j = 0; j < multSelections.size(); j++) {
        MultSelection multSel = (MultSelection)multSelections.get(j);
        FormTextField selectionNo = new FormTextField("selectionNo" + j, "", false, 15, 20);
        FormTextField upc = new FormTextField("upc" + j, "", false, 18, 20);
        FormTextField description = new FormTextField("description" + j, "", false, 25, 100);
        upc.addFormEvent("onKeyPress", "return checkNumbersOnly(window.event.keyCode);");
        multSelectionForm.addElement(selectionNo);
        multSelectionForm.addElement(upc);
        multSelectionForm.addElement(description);
      } 
      multSelectionForm.setValues(context);
      Vector newMultSelections = new Vector();
      for (int j = 0; j < multSelections.size(); j++) {
        MultSelection multSel = (MultSelection)multSelections.get(j);
        multSel.setSelectionNo(multSelectionForm.getStringValue("selectionNo" + j));
        multSel.setUpc(multSelectionForm.getStringValue("upc" + j));
        multSel.setDescription(multSelectionForm.getStringValue("description" + j));
        newMultSelections.add(multSel);
      } 
      if (multSelectionsPK > -1)
        newMultSelections.remove(multSelectionsPK); 
      selection.setMultSelections(newMultSelections);
      for (int j = 0; j < newMultSelections.size(); j++) {
        MultSelection multSel = (MultSelection)newMultSelections.get(j);
        FormTextField selectionNo = new FormTextField("selectionNo" + j, multSel.getSelectionNo(), false, 15, 20);
        FormTextField upc = new FormTextField("upc" + j, multSel.getUpc(), false, 18, 20);
        FormTextField description = new FormTextField("description" + j, multSel.getDescription(), false, 25, 100);
        upc.addFormEvent("onKeyPress", "return checkNumbersOnly(window.event.keyCode);");
        newMultSelectionForm.addElement(selectionNo);
        newMultSelectionForm.addElement(upc);
        newMultSelectionForm.addElement(description);
      } 
    } 
    newMultSelectionForm.addElement(new FormHidden("cmd", command, true));
    context.putSessionValue("Selection", selection);
    context.putDelivery("Form", newMultSelectionForm);
    return context.includeJSP("multSelection-editor.jsp");
  }
  
  private boolean multSelectionEditorSave(Dispatcher dispatcher, Context context, String command) {
    Form multSelectionForm = new Form(this.application, "multSelectionForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    User user = (User)context.getSession().getAttribute("user");
    Selection selection = (Selection)context.getSessionValue("Selection");
    Vector multSelections = null;
    multSelections = selection.getMultSelections();
    if (selection != null && selection.getMultSelections() != null) {
      for (int j = 0; j < multSelections.size(); j++) {
        MultSelection multSel = (MultSelection)multSelections.get(j);
        FormTextField selectionNo = new FormTextField("selectionNo" + j, "", false, 15, 20);
        FormTextField upc = new FormTextField("upc" + j, "", false, 18, 20);
        FormTextField description = new FormTextField("description" + j, "", false, 25, 100);
        upc.addFormEvent("onKeyPress", "return checkNumbersOnly(window.event.keyCode);");
        multSelectionForm.addElement(selectionNo);
        multSelectionForm.addElement(upc);
        multSelectionForm.addElement(description);
      } 
      multSelectionForm.setValues(context);
      Vector newMultSelections = new Vector();
      for (int j = 0; j < multSelections.size(); j++) {
        MultSelection multSel = (MultSelection)multSelections.get(j);
        multSel.setSelectionNo(multSelectionForm.getStringValue("selectionNo" + j));
        multSel.setUpc(MilestoneHelper_2.reformat_UPC_SSG_SGC_forSave(multSelectionForm.getStringValue("upc" + j), "UPC", false, true));
        multSel.setDescription(multSelectionForm.getStringValue("description" + j));
        newMultSelections.add(multSel);
      } 
      selection.setMultSelections(newMultSelections);
      if (selection.getSelectionID() > 0)
        SelectionManager.getInstance().saveSelection(selection, user); 
    } 
    multSelectionForm.addElement(new FormHidden("cmd", command, true));
    context.putSessionValue("Selection", selection);
    context.putDelivery("Form", multSelectionForm);
    return true;
  }
  
  private boolean multOtherContactEditor(Dispatcher dispatcher, Context context, String command) {
    Form multOtherContactForm = new Form(this.application, "multOtherContactForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    Vector multOtherContacts = null;
    User user = (User)context.getSession().getAttribute("user");
    Selection selection = (Selection)context.getSessionValue("Selection");
    int secureLevel = getSelectionPermissions(selection, user);
    setButtonVisibilities(selection, user, context, secureLevel, command);
    if (selection != null && selection.getMultOtherContacts() != null) {
      multOtherContacts = selection.getMultOtherContacts();
      for (int j = 0; j < multOtherContacts.size(); j++) {
        MultOtherContact multOth = (MultOtherContact)multOtherContacts.get(j);
        FormTextField name = new FormTextField("name" + j, multOth.getName(), false, 25, 150);
        FormTextField description = new FormTextField("description" + j, multOth.getDescription(), false, 25, 150);
        multOtherContactForm.addElement(name);
        multOtherContactForm.addElement(description);
      } 
    } 
    multOtherContactForm.addElement(new FormHidden("cmd", command, true));
    context.putDelivery("Selection", selection);
    context.putDelivery("Form", multOtherContactForm);
    return context.includeJSP("multOtherContact-editor.jsp");
  }
  
  private boolean multOtherContactEditorAdd(Dispatcher dispatcher, Context context, String command) {
    Form multOtherContactForm = new Form(this.application, "multOtherContactForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    User user = (User)context.getSession().getAttribute("user");
    Selection selection = (Selection)context.getSessionValue("Selection");
    Vector multOtherContacts = null;
    multOtherContacts = selection.getMultOtherContacts();
    if (multOtherContacts == null) {
      multOtherContacts = new Vector();
      MultOtherContact temp = new MultOtherContact();
      temp.setRelease_id(selection.getSelectionID());
      multOtherContacts.add(temp);
      selection.setMultOtherContacts(multOtherContacts);
    } else {
      MultOtherContact temp = new MultOtherContact();
      temp.setRelease_id(selection.getSelectionID());
      multOtherContacts.add(temp);
      selection.setMultOtherContacts(multOtherContacts);
    } 
    if (selection != null && selection.getMultOtherContacts() != null)
      for (int j = 0; j < multOtherContacts.size(); j++) {
        MultOtherContact multOth = (MultOtherContact)multOtherContacts.get(j);
        FormTextField name = new FormTextField("name" + j, multOth.getName(), false, 25, 150);
        FormTextField description = new FormTextField("description" + j, multOth.getDescription(), false, 25, 150);
        multOtherContactForm.addElement(name);
        multOtherContactForm.addElement(description);
      }  
    multOtherContactForm.setValues(context);
    multOtherContactForm.addElement(new FormHidden("cmd", command, true));
    context.putSessionValue("Selection", selection);
    context.putDelivery("Form", multOtherContactForm);
    return context.includeJSP("multOtherContact-editor.jsp");
  }
  
  private boolean multOtherContactEditorCancel(Dispatcher dispatcher, Context context, String command) {
    Form multOtherContactForm = new Form(this.application, "multOtherContactForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    User user = (User)context.getSession().getAttribute("user");
    Selection sessionSelection = (Selection)context.getSessionValue("Selection");
    Selection selection = null;
    if (sessionSelection.getSelectionID() < 0) {
      selection = sessionSelection;
    } else {
      selection = SelectionManager.getInstance().getSelectionHeader(sessionSelection.getSelectionID());
    } 
    Vector multOtherContacts = null;
    if (selection != null) {
      multOtherContacts = selection.getMultOtherContacts();
      if (selection != null && selection.getMultOtherContacts() != null) {
        Vector newMultOtherContacts = new Vector();
        for (int j = 0; j < multOtherContacts.size(); j++) {
          MultOtherContact multOth = (MultOtherContact)multOtherContacts.get(j);
          if (sessionSelection.getSelectionID() >= 0 || 
            multOth.getName() != null || multOth.getDescription() != null) {
            FormTextField name = new FormTextField("name" + j, multOth.getName(), false, 25, 150);
            FormTextField description = new FormTextField("description" + j, multOth.getDescription(), false, 25, 150);
            multOtherContactForm.addElement(name);
            multOtherContactForm.addElement(description);
            newMultOtherContacts.add(multOth);
          } 
        } 
        selection.setMultOtherContacts(newMultOtherContacts);
      } 
    } 
    multOtherContactForm.addElement(new FormHidden("cmd", command, true));
    context.putSessionValue("Selection", selection);
    context.putDelivery("Form", multOtherContactForm);
    return true;
  }
  
  private boolean multOtherContactEditorDelete(Dispatcher dispatcher, Context context, String command) {
    Form multOtherContactForm = new Form(this.application, "multOtherContactForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    Form newMultOtherContactForm = new Form(this.application, "multOtherContactForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    User user = (User)context.getSession().getAttribute("user");
    Selection selection = (Selection)context.getSessionValue("Selection");
    Vector multOtherContacts = null;
    int multOtherContactsPK = -1;
    if (context.getRequestValue("multOtherContactsPK") != null)
      multOtherContactsPK = Integer.parseInt(context.getRequestValue("multOtherContactsPK")); 
    multOtherContacts = selection.getMultOtherContacts();
    if (selection != null && selection.getMultOtherContacts() != null) {
      for (int j = 0; j < multOtherContacts.size(); j++) {
        MultOtherContact multOth = (MultOtherContact)multOtherContacts.get(j);
        FormTextField name = new FormTextField("name" + j, "", false, 25, 150);
        FormTextField description = new FormTextField("description" + j, "", false, 25, 150);
        multOtherContactForm.addElement(name);
        multOtherContactForm.addElement(description);
      } 
      multOtherContactForm.setValues(context);
      Vector newMultOtherContacts = new Vector();
      for (int j = 0; j < multOtherContacts.size(); j++) {
        MultOtherContact multOth = (MultOtherContact)multOtherContacts.get(j);
        multOth.setName(multOtherContactForm.getStringValue("name" + j));
        multOth.setDescription(multOtherContactForm.getStringValue("description" + j));
        newMultOtherContacts.add(multOth);
      } 
      if (multOtherContactsPK > -1)
        newMultOtherContacts.remove(multOtherContactsPK); 
      selection.setMultOtherContacts(newMultOtherContacts);
      for (int j = 0; j < newMultOtherContacts.size(); j++) {
        MultOtherContact multOth = (MultOtherContact)newMultOtherContacts.get(j);
        FormTextField name = new FormTextField("name" + j, multOth.getName(), false, 25, 150);
        FormTextField description = new FormTextField("description" + j, multOth.getDescription(), false, 25, 150);
        newMultOtherContactForm.addElement(name);
        newMultOtherContactForm.addElement(description);
      } 
    } 
    newMultOtherContactForm.addElement(new FormHidden("cmd", command, true));
    context.putSessionValue("Selection", selection);
    context.putDelivery("Form", newMultOtherContactForm);
    return context.includeJSP("multOtherContact-editor.jsp");
  }
  
  private boolean multOtherContactEditorSave(Dispatcher dispatcher, Context context, String command) {
    Form multOtherContactForm = new Form(this.application, "multOtherContactForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    User user = (User)context.getSession().getAttribute("user");
    Selection selection = (Selection)context.getSessionValue("Selection");
    Vector multOtherContacts = null;
    multOtherContacts = selection.getMultOtherContacts();
    if (selection != null && selection.getMultOtherContacts() != null) {
      for (int j = 0; j < multOtherContacts.size(); j++) {
        MultOtherContact multOth = (MultOtherContact)multOtherContacts.get(j);
        FormTextField name = new FormTextField("name" + j, "", false, 25, 150);
        FormTextField description = new FormTextField("description" + j, "", false, 25, 150);
        multOtherContactForm.addElement(name);
        multOtherContactForm.addElement(description);
      } 
      multOtherContactForm.setValues(context);
      Vector newMultOtherContacts = new Vector();
      for (int j = 0; j < multOtherContacts.size(); j++) {
        MultOtherContact multOth = (MultOtherContact)multOtherContacts.get(j);
        multOth.setName(multOtherContactForm.getStringValue("name" + j));
        multOth.setDescription(multOtherContactForm.getStringValue("description" + j));
        newMultOtherContacts.add(multOth);
      } 
      selection.setMultOtherContacts(newMultOtherContacts);
      if (selection.getSelectionID() > 0)
        SelectionManager.getInstance().saveSelection(selection, user); 
    } 
    multOtherContactForm.addElement(new FormHidden("cmd", command, true));
    context.putSessionValue("Selection", selection);
    context.putDelivery("Form", multOtherContactForm);
    return true;
  }
  
  private boolean sortGroup(Dispatcher dispatcher, Context context, String command) {
    int sort = Integer.parseInt(context.getParameter("OrderBy"));
    String alphaGroupChr = context.getParameter("alphaGroupChr");
    Notepad notepad = (Notepad)context.getSessionValue(MilestoneConstants.NOTEPAD_SESSION_NAMES[0]);
    User user = (User)context.getSession().getAttribute("user");
    if (notepad.getAllContents() != null && notepad.getAllContents().size() < notepad.getTotalRecords()) {
      notepad.setMaxRecords(0);
      notepad.setAllContents(null);
      notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 0);
    } 
    SelectionManager.getInstance().getAlphaGroupPosition(context, notepad, alphaGroupChr, sort);
    notepad.goToSelectedPage();
    if (command.equals("selection-group")) {
      dispatcher.redispatch(context, "selection-editor");
    } else {
      dispatcher.redispatch(context, "selection-manufacturing-editor");
    } 
    return true;
  }
  
  public static Vector filterSelectionEnvironments(Vector companies) {
    Vector result = new Vector();
    if (companies != null && companies.size() > 0)
      for (int i = 0; i < companies.size(); i++) {
        Company company = (Company)companies.get(i);
        Environment environment = company.getParentEnvironment();
        String name = environment.getName();
        if (!name.equalsIgnoreCase("UML") && 
          !name.equalsIgnoreCase("Enterprise")) {
          boolean addFlag = true;
          for (int r = 0; r < result.size(); r++) {
            if (((Environment)result.get(r)).getName().equalsIgnoreCase(name))
              addFlag = false; 
          } 
          if (addFlag)
            result.add(environment); 
        } 
      }  
    return result;
  }
  
  public static Vector filterSelectionEnvironmentsWithEditRigthts(Vector environments, User user) {
    Vector result = new Vector();
    Vector envAclList = user.getAcl().getEnvironmentAcl();
    HashMap envEditRight = new HashMap();
    if (envAclList != null)
      for (int n = 0; n < envAclList.size(); n++) {
        EnvironmentAcl envAcl = (EnvironmentAcl)envAclList.get(n);
        if (envAcl.getAccessSelection() == 2)
          envEditRight.put(new Integer(envAcl.getEnvironmentId()), new Integer(n)); 
      }  
    if (environments != null && environments.size() > 0)
      for (int i = 0; i < environments.size(); i++) {
        Environment environment = (Environment)environments.get(i);
        String name = environment.getName();
        if (envAclList == null) {
          if (!name.equalsIgnoreCase("UML") && !name.equalsIgnoreCase("Enterprise"))
            result.add(environment); 
        } else if (!name.equalsIgnoreCase("UML") && !name.equalsIgnoreCase("Enterprise") && 
          envEditRight.containsKey(new Integer(environment.getStructureID()))) {
          result.add(environment);
        } 
      }  
    return result;
  }
  
  private boolean sendPfmBom(Dispatcher dispatcher, Context context, String command) {
    EmailDistribution.removeSessionValues(context);
    context.removeSessionValue("originalComment");
    if (command.equals("selection-send-pfm") || command.equals("selection-send-pfmbom")) {
      PfmHandler pfmHandler = new PfmHandler(this.application);
      pfmHandler.editSave(dispatcher, context, "selectionSave");
      EmailDistribution.removeSessionValues(context);
    } 
    if (command.equals("selection-send-bom") || command.equals("selection-send-pfmbom")) {
      BomHandler bomHandler = new BomHandler(this.application);
      bomHandler.save(dispatcher, context, "selectionSave");
      EmailDistribution.removeSessionValues(context);
    } 
    if (context.getSessionValue("sendToSchedule") != null && ((String)context.getSessionValue("sendToSchedule")).equals("true")) {
      if (context.getSessionValue("recalc-date") != null && ((String)context.getSessionValue("recalc-date")).equals("true"))
        context.putDelivery("recalc-date", "true"); 
      context.removeSessionValue("sendToSchedule");
      context.removeSessionValue("recalc-date");
      dispatcher.redispatch(context, "schedule-editor");
      return true;
    } 
    if (command.equals("selection-send-cancel")) {
      PfmHandler pfmHandler = new PfmHandler(this.application);
      pfmHandler.editSave(dispatcher, context, "selectionSave");
      BomHandler bomHandler = new BomHandler(this.application);
      bomHandler.save(dispatcher, context, "selectionSave");
      EmailDistribution.removeSessionValues(context);
    } 
    dispatcher.redispatch(context, "selection-editor");
    return true;
  }
  
  protected Form buildDigitalForm(Context context, Selection selection, String command) {
    Form selectionForm = new Form(this.application, "selectionForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    User user = (User)context.getSession().getAttribute("user");
    int userId = user.getUserId();
    int secureLevel = getSelectionPermissions(selection, user);
    setButtonVisibilities(selection, user, context, secureLevel, command);
    boolean newFlag = (selection.getSelectionID() < 0);
    if (newFlag) {
      context.putDelivery("new-or-copy", "true");
    } else {
      context.putDelivery("new-or-copy", "false");
    } 
    selectionForm.addElement(new FormHidden("cmd", command, true));
    selectionForm.addElement(new FormHidden("OrderBy", "", true));
    selectionForm.addElement(new FormHidden("hidTitleId", "", true));
    selectionForm.addElement(new FormHidden("isFocus", "", true));
    selectionForm.addElement(new FormHidden("statusHidVal", "", true));
    selectionForm.addElement(new FormHidden("generateSelection", "", true));
    Vector companies = null;
    companies = MilestoneHelper.getUserCompanies(context);
    if (selection != null) {
      FormTextField artistFirstName = new FormTextField("artistFirstName", selection.getArtistFirstName(), false, 20, 50);
      artistFirstName.setTabIndex(1);
      artistFirstName.setClassName("ctrlMedium");
      artistFirstName.addFormEvent("onChange", "javaScript:removeSpaces(this);");
      selectionForm.addElement(artistFirstName);
      FormTextField artistLastName = new FormTextField("artistLastName", selection.getArtistLastName(), false, 20, 50);
      artistLastName.setTabIndex(2);
      artistLastName.setClassName("ctrlMedium");
      artistLastName.addFormEvent("onChange", "javaScript:removeSpaces(this);");
      selectionForm.addElement(artistLastName);
      FormTextField title = new FormTextField("title", selection.getTitle(), true, 73, 125);
      title.setTabIndex(3);
      title.setClassName("ctrlXLarge");
      title.addFormEvent("onChange", "javaScript:removeSpaces(this);");
      selectionForm.addElement(title);
      FormTextField sideATitle = new FormTextField("sideATitle", selection.getASide(), false, 20, 125);
      sideATitle.setTabIndex(4);
      sideATitle.setClassName("ctrlMedium");
      selectionForm.addElement(sideATitle);
      FormTextField sideBTitle = new FormTextField("sideBTitle", selection.getBSide(), false, 20, 125);
      sideBTitle.setTabIndex(5);
      sideBTitle.setClassName("ctrlMedium");
      selectionForm.addElement(sideBTitle);
      String[] values = new String[2];
      values[0] = "true";
      values[1] = "false";
      String[] labels = new String[2];
      labels[0] = "New Bundle";
      labels[1] = "Exact Duplicate of Physical Product";
      FormRadioButtonGroup newBundle = new FormRadioButtonGroup("newBundle", String.valueOf(selection.getNewBundleFlag()), values, labels, false);
      newBundle.addFormEvent("onClick", "JavaScript:filterScheduleType(false)");
      newBundle.setTabIndex(6);
      selectionForm.addElement(newBundle);
      FormCheckBox priority = new FormCheckBox("priority", "", false, selection.getPriority());
      priority.setTabIndex(9);
      selectionForm.addElement(priority);
      String streetDateText = "";
      if (selection.getStreetDate() != null)
        streetDateText = MilestoneHelper.getFormatedDate(selection.getStreetDate()); 
      FormTextField streetDate = new FormTextField("streetDate", streetDateText, false, 10);
      streetDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
      streetDate.addFormEvent("onBlur", "JavaScript:validateDate(document.forms[0].streetDate.value,this)");
      streetDate.setTabIndex(11);
      streetDate.setClassName("ctrlShort");
      selectionForm.addElement(streetDate);
      FormTextField dayType = new FormTextField("dayType", MilestoneHelper.getDayType(selection.getCalendarGroup(), selection.getStreetDate()), false, 5);
      selectionForm.addElement(dayType);
      String digitalRlsDateText = "";
      if (selection.getDigitalRlsDate() != null)
        digitalRlsDateText = MilestoneHelper.getFormatedDate(selection.getDigitalRlsDate()); 
      FormTextField drDate = new FormTextField("digitalDate", digitalRlsDateText, false, 10);
      drDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
      drDate.addFormEvent("onBlur", "JavaScript:validateDate(document.forms[0].digitalDate.value,this)");
      drDate.setTabIndex(10);
      drDate.setClassName("ctrlShort");
      selectionForm.addElement(drDate);
      String intDateText = "";
      if (selection.getInternationalDate() != null)
        intDateText = MilestoneHelper.getFormatedDate(selection.getInternationalDate()); 
      FormDateField intDate = new FormDateField("internationalDate", intDateText, false, 10);
      intDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
      intDate.addFormEvent("onBlur", "JavaScript:validateDate(document.forms[0].internationalDate.value,this)");
      intDate.setTabIndex(12);
      intDate.setClassName("ctrlShort");
      selectionForm.addElement(intDate);
      context.putDelivery("releaseWeek", MilestoneHelper.getReleaseWeekString(selection));
      FormDropDownMenu status = MilestoneHelper.getLookupDropDown("status", Cache.getSelectionStatusList(), SelectionManager.getLookupObjectValue(selection.getSelectionStatus()), true, false);
      status.setTabIndex(13);
      status.setClassName("ctrlSmall");
      selectionForm.addElement(status);
      boolean boolHoldReason = true;
      if (selection.getHoldReason().equalsIgnoreCase(""))
        boolHoldReason = false; 
      FormCheckBox holdIndicator = new FormCheckBox("holdIndicator", "", false, boolHoldReason);
      holdIndicator.setTabIndex(10);
      selectionForm.addElement(holdIndicator);
      FormTextArea holdReason = new FormTextArea("holdReason", selection.getHoldReason(), false, 2, 44, "virtual");
      holdReason.addFormEvent("onBlur", "Javascript:checkField(this)");
      selectionForm.addElement(holdReason);
      FormCheckBox pdIndicator = new FormCheckBox("pdIndicator", "", false, selection.getPressAndDistribution());
      pdIndicator.setTabIndex(8);
      selectionForm.addElement(pdIndicator);
      FormCheckBox intlFlag = new FormCheckBox("intlFlag", "", false, selection.getInternationalFlag());
      intlFlag.setTabIndex(12);
      selectionForm.addElement(intlFlag);
      String impactDateText = "";
      if (selection.getImpactDate() != null)
        impactDateText = MilestoneHelper.getFormatedDate(selection.getImpactDate()); 
      FormDateField impactDate = new FormDateField("impactdate", impactDateText, false, 13);
      impactDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
      impactDate.addFormEvent("onBlur", "JavaScript:validateDate(document.forms[0].impactdate.value,this)");
      impactDate.setTabIndex(13);
      impactDate.setClassName("ctrlShort");
      selectionForm.addElement(impactDate);
      Vector releaseFamilies = ReleasingFamily.getReleasingFamilies(userId, selection.getFamily().getStructureID(), context);
      FormDropDownMenu releasingFamily = MilestoneHelper.getReleasingFamilyDropDown("releasingFamily", Integer.toString(selection.getReleaseFamilyId()), releaseFamilies, true, selection);
      releasingFamily.setTabIndex(14);
      releasingFamily.addFormEvent("onChange", "ReleasingFamilyChange(this)");
      selectionForm.addElement(releasingFamily);
      String evironmentId = "";
      String environmentName = "";
      Vector evironmentList = filterSelectionEnvironments(companies);
      if (selection.getCompany() != null && selection.getCompany().getParentEnvironment() != null) {
        evironmentId = Integer.toString(selection.getCompany().getParentEnvironment().getStructureID());
        environmentName = selection.getCompany().getParentEnvironment().getName();
      } else {
        evironmentId = "";
      } 
      FormHidden evironment = new FormHidden("environment", evironmentId, false);
      FormHidden evironmentLabel = new FormHidden("environment", evironmentId, false);
      evironment.setTabIndex(14);
      evironment.setDisplayName(environmentName);
      selectionForm.addElement(evironment);
      String companyId = "";
      String companyName = "";
      if (selection.getCompany() != null) {
        companyId = Integer.toString(selection.getCompany().getStructureID());
        companyName = selection.getCompany().getName();
      } 
      FormHidden company = new FormHidden("company", companyId, false);
      company.setTabIndex(15);
      company.setDisplayName(companyName);
      selectionForm.addElement(company);
      String divisionId = "";
      String divisionName = "";
      if (selection.getDivision() != null) {
        divisionId = Integer.toString(selection.getDivision().getStructureID());
        divisionName = selection.getDivision().getName();
      } else {
        divisionId = "";
      } 
      FormHidden division = new FormHidden("division", divisionId, false);
      division.setTabIndex(16);
      division.setDisplayName(divisionName);
      selectionForm.addElement(division);
      String labelId = "";
      String labelName = "";
      if (selection.getLabel() != null) {
        labelId = Integer.toString(selection.getLabel().getStructureID());
        labelName = selection.getLabel().getName();
      } else {
        labelId = "";
      } 
      FormHidden label = new FormHidden("label", labelId, false);
      label.setTabIndex(17);
      label.setDisplayName(labelName);
      selectionForm.addElement(label);
      if (selection.getOperCompany().equals("***")) {
        FormHidden opercompany = new FormHidden("opercompany", "***", false);
        opercompany.setTabIndex(18);
        opercompany.setDisplayName("***");
        selectionForm.addElement(opercompany);
      } else {
        LookupObject oc = MilestoneHelper.getLookupObject(selection
            .getOperCompany(), Cache.getOperatingCompanies());
        String ocAbbr = "";
        String ocName = "";
        if (oc == null) {
          ocAbbr = selection.getOperCompany();
        } else {
          if (oc != null && oc.getAbbreviation() != null)
            ocAbbr = oc.getAbbreviation(); 
          if (oc != null && oc.getName() != null)
            ocName = ":" + oc.getName(); 
        } 
        FormHidden opercompany = new FormHidden("opercompany", ocAbbr, false);
        opercompany.setTabIndex(18);
        opercompany.setDisplayName(String.valueOf(ocAbbr) + ocName);
        if (ocAbbr.equals("ZZ"))
          opercompany.setDisplayName(ocAbbr); 
        selectionForm.addElement(opercompany);
      } 
      FormHidden superlabel = new FormHidden("superlabel", selection.getSuperLabel(), false);
      superlabel.setTabIndex(19);
      superlabel.setClassName("ctrlShort");
      superlabel.addFormEvent("onBlur", "javascript:this.value=this.value.toUpperCase();");
      selectionForm.addElement(superlabel);
      FormHidden sublabel = new FormHidden("sublabel", selection.getSubLabel(), false);
      sublabel.setTabIndex(20);
      sublabel.setClassName("ctrlShort");
      sublabel.addFormEvent("onBlur", "javascript:this.value=this.value.toUpperCase();");
      selectionForm.addElement(sublabel);
      FormTextField imprint = new FormTextField("imprint", selection.getImprint(), false, 50);
      imprint.setTabIndex(21);
      imprint.setClassName("ctrlMedium");
      imprint.addFormEvent("onChange", "javaScript:removeSpaces(this);");
      selectionForm.addElement(imprint);
      FormDropDownMenu configcode = MilestoneHelper.getPfmLookupDropDown("configcode", MilestoneHelper.getConfigCodes(1), selection.getConfigCode(), false, true);
      configcode.setTabIndex(21);
      configcode.addFormEvent("onKeyPress", "scanKey(window.event.keyCode, 'document.all.configcode.options', getMaxLength(document.all.configcode.options))");
      selectionForm.addElement(configcode);
      FormHidden projectId = new FormHidden("projectId", String.valueOf(selection.getProjectID()), false);
      projectId.setTabIndex(22);
      projectId.setClassName("ctrlMedium");
      projectId.setDisplayName(String.valueOf(selection.getProjectID()));
      selectionForm.addElement(projectId);
      FormTextField upc = new FormTextField("UPC", selection.getUpc(), false, 17, 20);
      upc.setTabIndex(23);
      upc.setClassName("ctrlMedium");
      upc.addFormEvent("onKeyPress", "return checkNumbersOnly(window.event.keyCode);");
      selectionForm.addElement(upc);
      FormTextField soundscan = new FormTextField("soundscan", selection.getSoundScanGrp(), false, 17, 20);
      soundscan.setTabIndex(24);
      soundscan.setClassName("ctrlMedium");
      selectionForm.addElement(soundscan);
      FormTextField gridNumber = new FormTextField("gridNumber", selection.getGridNumber(), false, 50);
      gridNumber.setTabIndex(25);
      gridNumber.setEnabled(true);
      selectionForm.addElement(gridNumber);
      FormDropDownMenu prefix = MilestoneHelper.getPrefixCodeDropDown(userId, "prefix", Cache.getPrefixCodes(), SelectionManager.getLookupObjectValue(selection.getPrefixID()), true, context);
      prefix.setTabIndex(26);
      prefix.setClassName("ctrlShort");
      selectionForm.addElement(prefix);
      FormTextField selectionNo = new FormTextField("selectionNo", String.valueOf(selection.getSelectionNo()), false, 20, 20);
      selectionNo.setTabIndex(27);
      selectionNo.setClassName("ctrlMedium");
      selectionNo.addFormEvent("onChange", "javaScript:removeSpaces(this);");
      selectionForm.addElement(selectionNo);
      FormHidden titleId = new FormHidden("titleId", String.valueOf(selection.getTitleID()), false);
      titleId.setClassName("ctrlMedium");
      titleId.setTabIndex(28);
      selectionForm.addElement(titleId);
      FormDropDownMenu productLine = MilestoneHelper.getLookupDropDown("productLine", Cache.getProductCategories(1), SelectionManager.getLookupObjectValue(selection.getProductCategory()), true, true);
      productLine.setTabIndex(29);
      productLine.setClassName("ctrlMedium");
      selectionForm.addElement(productLine);
      FormDropDownMenu releaseType = MilestoneHelper.getLookupDropDown("releaseType", Cache.getReleaseTypes(), SelectionManager.getLookupObjectValue(selection.getReleaseType()), true, newFlag);
      releaseType.setTabIndex(30);
      releaseType.setClassName("ctrlMedium");
      releaseType.addFormEvent("onChange", "releaseTypeChanged()");
      selectionForm.addElement(releaseType);
      String configValue = "";
      boolean configNewBundle = false;
      if (selection.getSelectionConfig() != null) {
        configNewBundle = selection.getNewBundleFlag();
        configValue = selection.getSelectionConfig().getSelectionConfigurationAbbreviation();
      } 
      FormDropDownMenu configuration = null;
      if (command.equalsIgnoreCase("selection-edit-copy-digital")) {
        if (selection.getIsDigital()) {
          configuration = MilestoneHelper.getSelectionConfigurationDropDown("configuration", configValue, true, 1);
        } else {
          configuration = MilestoneHelper.getSelectionConfigurationDropDown("configuration", configValue, true, 1, configNewBundle);
        } 
      } else {
        configuration = MilestoneHelper.getSelectionConfigurationDropDown("configuration", configValue, true, 1, configNewBundle);
      } 
      configuration.setTabIndex(31);
      configuration.addFormEvent("onChange", "buildSubConfigs(this.selectedIndex)");
      selectionForm.addElement(configuration);
      String subConfigValue = "";
      if (selection.getSelectionSubConfig() != null)
        subConfigValue = selection.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation(); 
      FormDropDownMenu subConfiguration = MilestoneHelper.getSelectionSubConfigurationDropDown("subConfiguration", selection.getSelectionConfig(), subConfigValue, true);
      subConfiguration.addFormEvent("onChange", "isSubConfigParent(this.selectedIndex)");
      subConfiguration.setTabIndex(32);
      selectionForm.addElement(subConfiguration);
      FormTextField test = new FormTextField("test", "", false, 8, 8);
      test.setTabIndex(33);
      test.setClassName("ctrlShort");
      test.addFormEvent("onChange", "javaScript:clickSell(this,true);");
      selectionForm.addElement(test);
      String sellCode = "";
      if (selection.getSellCode() != null)
        sellCode = selection.getSellCode(); 
      FormDropDownMenu priceCode = new FormDropDownMenu("priceCode", sellCode, "-1" + getSellCodesStringDPC(), "&nbsp;" + getSellCodesStringDPC(), false);
      priceCode.setTabIndex(34);
      priceCode.setClassName("ctrlSmall");
      selectionForm.addElement(priceCode);
      String numberOfUnits = "0";
      if (selection.getNumberOfUnits() > 0)
        numberOfUnits = Integer.toString(selection.getNumberOfUnits()); 
      FormTextField numOfUnits = new FormTextField("numOfUnits", numberOfUnits, false, 10, 10);
      numOfUnits.setTabIndex(35);
      numOfUnits.setClassName("ctrlShort");
      selectionForm.addElement(numOfUnits);
      User labelUserContact = selection.getLabelContact();
      Vector labelContacts = SelectionManager.getLabelContacts(selection);
      FormDropDownMenu contactList = MilestoneHelper.getContactsDropDown(context, "contactlist", labelContacts, labelUserContact, true);
      contactList.setTabIndex(36);
      contactList.setClassName("ctrlMedium");
      selectionForm.addElement(contactList);
      FormTextField contact = new FormTextField("contact", selection.getOtherContact(), false, 14, 30);
      contact.setTabIndex(37);
      contact.setClassName("ctrlMedium");
      selectionForm.addElement(contact);
      FormCheckBox parentalIndicator = new FormCheckBox("parentalIndicator", "", false, selection.getParentalGuidance());
      parentalIndicator.setTabIndex(38);
      selectionForm.addElement(parentalIndicator);
      FormCheckBox specPkgIndicator = new FormCheckBox("specialPkgIndicator", "", false, selection.getSpecialPackaging());
      specPkgIndicator.addFormEvent("onDblClick", "document.forms[0].PackagingHelper.value=document.forms[0].package.value;togglePackaging();");
      specPkgIndicator.setTabIndex(39);
      selectionForm.addElement(specPkgIndicator);
      FormTextField pkg = new FormTextField("package", selection.getSelectionPackaging(), false, 13, 100);
      pkg.setTabIndex(40);
      pkg.setClassName("ctrlMedium");
      pkg.addFormEvent("onDblClick", "document.forms[0].PackagingHelper.value=document.forms[0].package.value;togglePackaging();");
      selectionForm.addElement(pkg);
      FormDropDownMenu genre = MilestoneHelper.getPfmLookupDropDown("genre", Cache.getMusicTypes(), SelectionManager.getLookupObjectValue(selection.getGenre()), false, true);
      genre.setTabIndex(41);
      genre.setId("music_type");
      selectionForm.addElement(genre);
      FormTextField territory = new FormTextField("territory", selection.getSelectionTerritory(), false, 13, 255);
      territory.setTabIndex(42);
      territory.setClassName("ctrlMedium");
      territory.addFormEvent("onDblClick", "document.forms[0].TerritoryHelper.value=document.forms[0].territory.value;toggleTerritory();");
      selectionForm.addElement(territory);
      FormHidden productionGroupCode = new FormHidden("productGroupCode", selection.getProductionGroupCode(), false);
      productionGroupCode.setTabIndex(42);
      productionGroupCode.setDisplayName(selection.getProductionGroupCode());
      productionGroupCode.setClassName("ctrlMedium");
      selectionForm.addElement(productionGroupCode);
      FormTextArea specialInstructions = new FormTextArea("specialInstructions", selection.getSpecialInstructions(), false, 3, 80, "virtual");
      specialInstructions.setTabIndex(43);
      selectionForm.addElement(specialInstructions);
      String lastStreetDateText = "";
      if (selection.getLastStreetUpdateDate() != null)
        lastStreetDateText = MilestoneHelper.getFormatedDate(selection.getLastStreetUpdateDate()); 
      FormTextField lastStreetUpdatedDate = new FormTextField("laststreetupdateddate", lastStreetDateText, false, 13);
      selectionForm.addElement(lastStreetUpdatedDate);
      String lastUpdatedDateText = "";
      if (selection.getLastUpdateDate() != null)
        lastUpdatedDateText = MilestoneHelper.getCustomFormatedDate(selection.getLastUpdateDate(), "M/d/yyyy hh:mm:ss a 'ET'"); 
      FormTextField lastUpdatedDate = new FormTextField("lastupdateddate", lastUpdatedDateText, false, 50);
      selectionForm.addElement(lastUpdatedDate);
      String originDateText = "";
      if (selection.getOriginDate() != null)
        originDateText = MilestoneHelper.getFormatedDate(selection.getOriginDate()); 
      FormTextField originDate = new FormTextField("origindate", originDateText, false, 13);
      selectionForm.addElement(originDate);
      String archieDateText = "";
      if (selection.getArchieDate() != null)
        archieDateText = MilestoneHelper.getCustomFormatedDate(selection.getArchieDate(), "M/d/yyyy hh:mm:ss a 'ET'"); 
      FormTextField archieDate = new FormTextField("archieDate", archieDateText, false, 13);
      selectionForm.addElement(archieDate);
      String autoCloseDateText = "";
      if (selection.getAutoCloseDate() != null)
        autoCloseDateText = MilestoneHelper.getCustomFormatedDate(selection.getAutoCloseDate(), "M/d/yyyy hh:mm:ss a 'ET'"); 
      FormTextField autoCloseDate = new FormTextField("autoCloseDate", autoCloseDateText, false, 40);
      selectionForm.addElement(autoCloseDate);
      String lastLegacyUpdateDateText = "";
      if (selection.getLastLegacyUpdateDate() != null)
        lastLegacyUpdateDateText = MilestoneHelper.getCustomFormatedDate(selection.getLastLegacyUpdateDate(), "M/d/yyyy hh:mm:ss a 'ET'"); 
      FormTextField lastLegacyUpdateDate = new FormTextField("lastLegacyUpdateDate", lastLegacyUpdateDateText, false, 40);
      selectionForm.addElement(lastLegacyUpdateDate);
      FormTextArea packagingHelper = new FormTextArea("PackagingHelper", selection.getSelectionPackaging(), false, 2, 44, "virtual");
      selectionForm.addElement(packagingHelper);
      FormTextArea territoryHelper = new FormTextArea("TerritoryHelper", selection.getSelectionTerritory(), false, 2, 44, "virtual");
      selectionForm.addElement(territoryHelper);
      FormTextArea comments = new FormTextArea("comments", selection.getSelectionComments(), false, 6, 44, "virtual");
      comments.addFormEvent("onBlur", "Javascript:checkField(this)");
      selectionForm.addElement(comments);
    } 
    addSelectionSearchElements(context, selection, selectionForm, companies, true);
    if (context.getSessionValue("NOTEPAD_SELECTION_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_SELECTION_VISIBLE")); 
    boolean isParent = false;
    if (selection.getSelectionSubConfig() != null)
      isParent = selection.getSelectionSubConfig().isParent(); 
    context.putDelivery("is-parent", String.valueOf(isParent));
    context.putDelivery("old-selection-no", selection.getSelectionNo());
    String price = "0.00";
    if (selection.getPriceCode() != null && 
      selection.getPriceCode().getTotalCost() > 0.0F)
      price = MilestoneHelper.formatDollarPrice(selection.getPriceCode().getTotalCost()); 
    context.putDelivery("price", price);
    String lastUpdateUser = "";
    if (selection.getLastUpdatingUser() != null)
      lastUpdateUser = selection.getLastUpdatingUser().getName(); 
    context.putDelivery("lastUpdateUser", lastUpdateUser);
    return selectionForm;
  }
  
  protected Form buildNewDigitalForm(Context context, Selection selection, String command) {
    Vector projectList = (Vector)context.getSessionValue("searchResults");
    String resultsIndex = (String)context.getSessionValue("selectionScreenTypeIndex");
    System.out.println("value of resultsIndex:[" + resultsIndex + "]");
    ProjectSearch selectedProject = null;
    if (resultsIndex != null) {
      selectedProject = (ProjectSearch)projectList.elementAt(Integer.parseInt(resultsIndex));
    } else {
      selectedProject = new ProjectSearch();
    } 
    context.removeSessionValue("selectionScreenType");
    context.removeSessionValue("searchResults");
    context.removeSessionValue("selectionScreenTypeIndex");
    Form selectionForm = new Form(this.application, "selectionForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    User user = (User)context.getSession().getAttribute("user");
    int userId = user.getUserId();
    int secureLevel = getSelectionPermissions(selection, user);
    setButtonVisibilities(selection, user, context, secureLevel, command);
    selectionForm.addElement(new FormHidden("cmd", "selection-edit-new", true));
    selectionForm.addElement(new FormHidden("OrderBy", "", true));
    selectionForm.addElement(new FormHidden("hidTitleId", "", true));
    selectionForm.addElement(new FormHidden("isFocus", "", true));
    selectionForm.addElement(new FormHidden("statusHidVal", "", true));
    selectionForm.addElement(new FormHidden("generateSelection", "", true));
    String selectedConfig = "";
    String strArtistFirstName = (selectedProject.getArtistFirstName() != null) ? selectedProject.getArtistFirstName() : "";
    FormTextField artistFirstName = new FormTextField("artistFirstName", strArtistFirstName, false, 20, 50);
    artistFirstName.setTabIndex(1);
    artistFirstName.setClassName("ctrlMedium");
    artistFirstName.addFormEvent("onChange", "javaScript:removeSpaces(this);");
    selectionForm.addElement(artistFirstName);
    String strArtistLastName = (selectedProject.getArtistLastName() != null) ? selectedProject.getArtistLastName() : "";
    FormTextField artistLastName = new FormTextField("artistLastName", strArtistLastName, false, 20, 50);
    artistLastName.setTabIndex(2);
    artistLastName.setClassName("ctrlMedium");
    artistLastName.addFormEvent("onChange", "javaScript:removeSpaces(this);");
    selectionForm.addElement(artistLastName);
    String strTitle = (selectedProject.getTitle() != null) ? selectedProject.getTitle() : "";
    FormTextField title = new FormTextField("title", strTitle, true, 73, 125);
    title.setTabIndex(3);
    title.setClassName("ctrlXLarge");
    title.addFormEvent("onChange", "javaScript:removeSpaces(this);");
    selectionForm.addElement(title);
    FormTextField sideATitle = new FormTextField("sideATitle", "", false, 20, 125);
    sideATitle.setTabIndex(4);
    sideATitle.setClassName("ctrlMedium");
    selectionForm.addElement(sideATitle);
    FormTextField sideBTitle = new FormTextField("sideBTitle", "", false, 20, 125);
    sideBTitle.setTabIndex(5);
    sideBTitle.setClassName("ctrlMedium");
    selectionForm.addElement(sideBTitle);
    String[] values = new String[2];
    values[0] = "true";
    values[1] = "false";
    String[] labels = new String[2];
    labels[0] = "New Bundle";
    labels[1] = "Exact Duplicate of Physical Product";
    FormRadioButtonGroup newBundle = new FormRadioButtonGroup("newBundle", "true", values, labels, false);
    newBundle.addFormEvent("onClick", "JavaScript:filterScheduleType(true)");
    newBundle.setTabIndex(5);
    selectionForm.addElement(newBundle);
    FormCheckBox priority = new FormCheckBox("priority", "", false, false);
    priority.setTabIndex(6);
    selectionForm.addElement(priority);
    FormTextField streetDate = new FormTextField("streetDate", "", false, 10);
    streetDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
    streetDate.addFormEvent("onBlur", "JavaScript:validateDate(document.forms[0].streetDate.value,this)");
    streetDate.setTabIndex(7);
    streetDate.setClassName("ctrlShort");
    selectionForm.addElement(streetDate);
    FormTextField drDate = new FormTextField("digitalDate", "", false, 10);
    drDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
    drDate.addFormEvent("onBlur", "JavaScript:validateDate(document.forms[0].digitalDate.value,this)");
    drDate.setTabIndex(6);
    drDate.setClassName("ctrlShort");
    selectionForm.addElement(drDate);
    FormDateField intDate = new FormDateField("internationalDate", "", false, 10);
    intDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
    intDate.addFormEvent("onBlur", "JavaScript:validateDate(document.forms[0].internationalDate.value,this)");
    intDate.setTabIndex(8);
    intDate.setClassName("ctrlShort");
    selectionForm.addElement(intDate);
    FormDropDownMenu status = MilestoneHelper.getLookupDropDown("status", Cache.getSelectionStatusList(), "Active", true, false);
    status.setTabIndex(9);
    status.setClassName("ctrlSmall");
    selectionForm.addElement(status);
    FormCheckBox holdIndicator = new FormCheckBox("holdIndicator", "", false, false);
    holdIndicator.setTabIndex(10);
    selectionForm.addElement(holdIndicator);
    FormTextArea holdReason = new FormTextArea("holdReason", "", false, 2, 44, "virtual");
    holdReason.addFormEvent("onBlur", "Javascript:checkField(this)");
    selectionForm.addElement(holdReason);
    int pd_indicator = selectedProject.getPD_Indicator();
    boolean pdBool = false;
    if (pd_indicator == 1)
      pdBool = true; 
    FormCheckBox pdIndicator = new FormCheckBox("pdIndicator", "", false, 
        pdBool);
    pdIndicator.setTabIndex(6);
    selectionForm.addElement(pdIndicator);
    FormCheckBox intlFlag = new FormCheckBox("intlFlag", "", false, false);
    intlFlag.setTabIndex(12);
    selectionForm.addElement(intlFlag);
    FormDateField impactDate = new FormDateField("impactdate", "", false, 13);
    impactDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
    impactDate.addFormEvent("onBlur", "JavaScript:validateDate(document.forms[0].impactdate.value,this)");
    impactDate.setTabIndex(13);
    impactDate.setClassName("ctrlShort");
    selectionForm.addElement(impactDate);
    Vector releaseFamilies = ReleasingFamily.getReleasingFamilies(userId, selectedProject.getMSFamilyId(), context);
    ReleasingFamily defaultReleasingFamily = ReleasingFamily.getDefaultReleasingFamily(userId, selectedProject.getMSFamilyId(), context);
    FormDropDownMenu releasingFamily = MilestoneHelper.getReleasingFamilyDropDown("releasingFamily", String.valueOf(defaultReleasingFamily.getReleasingFamilyId()), releaseFamilies, true, selection);
    releasingFamily.setTabIndex(13);
    releasingFamily.addFormEvent("onChange", "ReleasingFamilyChange(this)");
    selectionForm.addElement(releasingFamily);
    String envId = String.valueOf(selectedProject.getMSEnvironmentId());
    String envName = MilestoneHelper.getStructureName(selectedProject.getMSEnvironmentId());
    String environmentName = "";
    FormHidden evironment = new FormHidden("environment", envId, false);
    evironment.setDisplayName(envName);
    selectionForm.addElement(evironment);
    String companyId = String.valueOf(selectedProject.getMSCompanyId());
    String companyName = MilestoneHelper.getStructureName(selectedProject.getMSCompanyId());
    FormHidden company = new FormHidden("company", companyId, false);
    company.setTabIndex(15);
    company.setDisplayName(companyName);
    selectionForm.addElement(company);
    String divisionId = String.valueOf(selectedProject.getMSDivisionId());
    String divisionName = MilestoneHelper.getStructureName(selectedProject.getMSDivisionId());
    FormHidden division = new FormHidden("division", divisionId, false);
    division.setTabIndex(16);
    division.setDisplayName(divisionName);
    selectionForm.addElement(division);
    String labelId = String.valueOf(selectedProject.getMSLabelId());
    String labelName = MilestoneHelper.getStructureName(selectedProject.getMSLabelId());
    FormHidden label = new FormHidden("label", labelId, false);
    label.setTabIndex(17);
    label.setDisplayName(labelName);
    selectionForm.addElement(label);
    if (selectedProject.getOperCompany().equals("***")) {
      FormHidden opercompany = new FormHidden("opercompany", "***", false);
      opercompany.setDisplayName("***");
      selectionForm.addElement(opercompany);
    } else {
      LookupObject oc = MilestoneHelper.getLookupObject(selectedProject
          .getOperCompany(), Cache.getOperatingCompanies());
      String ocAbbr = "";
      String ocName = "";
      if (oc == null) {
        ocAbbr = selectedProject.getOperCompany();
      } else {
        if (oc != null && oc.getAbbreviation() != null)
          ocAbbr = oc.getAbbreviation(); 
        if (oc != null && oc.getName() != null)
          ocName = ":" + oc.getName(); 
      } 
      FormHidden opercompany = new FormHidden("opercompany", ocAbbr, false);
      opercompany.setDisplayName(String.valueOf(ocAbbr) + ocName);
      if (ocAbbr.equals("ZZ"))
        opercompany.setDisplayName(ocAbbr); 
      selectionForm.addElement(opercompany);
    } 
    FormHidden superlabel = new FormHidden("superlabel", selectedProject.getSuperLabel(), false);
    superlabel.setClassName("ctrlShort");
    superlabel.addFormEvent("onBlur", "javascript:this.value=this.value.toUpperCase();");
    selectionForm.addElement(superlabel);
    FormHidden sublabel = new FormHidden("sublabel", selectedProject.getSubLabel(), false);
    sublabel.setClassName("ctrlShort");
    sublabel.addFormEvent("onBlur", "javascript:this.value=this.value.toUpperCase();");
    selectionForm.addElement(sublabel);
    Vector jdeExceptionFamilies = ProjectSearchManager.getInstance().getProjectSearchJDEFamilies();
    boolean isUmvdUser = jdeExceptionFamilies.contains(new Integer(selectedProject.getMSFamilyId()));
    String imprintStr = "";
    if (isUmvdUser) {
      imprintStr = labelName;
    } else {
      imprintStr = (selectedProject.getImprint() != null) ? selectedProject.getImprint() : "";
    } 
    FormTextField imprint = new FormTextField("imprint", imprintStr, false, 50);
    imprint.setTabIndex(21);
    imprint.setClassName("ctrlMedium");
    imprint.addFormEvent("onChange", "javaScript:removeSpaces(this);");
    selectionForm.addElement(imprint);
    FormDropDownMenu configcode = MilestoneHelper.getPfmLookupDropDown("configcode", MilestoneHelper.getConfigCodes(1), "", false, true);
    configcode.setTabIndex(21);
    configcode.addFormEvent("onKeyPress", "scanKey(window.event.keyCode, 'document.all.configcode.options', getMaxLength(document.all.configcode.options))");
    selectionForm.addElement(configcode);
    String projectIdStr = "";
    projectIdStr = selectedProject.getRMSProjectNo();
    FormHidden projectId = new FormHidden("projectId", projectIdStr, false);
    projectId.setTabIndex(22);
    projectId.setClassName("ctrlMedium");
    projectId.setDisplayName(projectIdStr);
    selectionForm.addElement(projectId);
    FormTextField upc = new FormTextField("UPC", "", false, 17, 20);
    upc.setTabIndex(23);
    upc.setClassName("ctrlMedium");
    upc.addFormEvent("onKeyPress", "return checkNumbersOnly(window.event.keyCode);");
    selectionForm.addElement(upc);
    FormTextField soundscan = new FormTextField("soundscan", "", false, 17, 20);
    soundscan.setTabIndex(24);
    soundscan.setClassName("ctrlMedium");
    selectionForm.addElement(soundscan);
    FormTextField gridNumber = new FormTextField("gridNumber", "", false, 50);
    gridNumber.setTabIndex(24);
    gridNumber.setEnabled(true);
    selectionForm.addElement(gridNumber);
    FormDropDownMenu prefix = MilestoneHelper.getPrefixCodeDropDown(userId, "prefix", Cache.getPrefixCodes(), "", true, context);
    prefix.setTabIndex(25);
    prefix.setClassName("ctrlShort");
    selectionForm.addElement(prefix);
    FormTextField selectionNo = new FormTextField("selectionNo", "", false, 20, 20);
    selectionNo.setTabIndex(26);
    selectionNo.setClassName("ctrlMedium");
    selectionNo.addFormEvent("onChange", "javaScript:removeSpaces(this);");
    selectionForm.addElement(selectionNo);
    FormTextField titleId = new FormTextField("titleId", "", false, 13, 24);
    titleId.setClassName("ctrlMedium");
    titleId.setTabIndex(27);
    selectionForm.addElement(titleId);
    FormDropDownMenu productLine = MilestoneHelper.getLookupDropDown("productLine", Cache.getProductCategories(1), "", true, true);
    productLine.setTabIndex(28);
    productLine.setClassName("ctrlMedium");
    selectionForm.addElement(productLine);
    FormDropDownMenu releaseType = MilestoneHelper.getLookupDropDown("releaseType", Cache.getReleaseTypes(), "CO", true, true);
    releaseType.setTabIndex(29);
    releaseType.setClassName("ctrlMedium");
    releaseType.addFormEvent("onChange", "releaseTypeChanged()");
    selectionForm.addElement(releaseType);
    FormDropDownMenu configuration = MilestoneHelper.getSelectionConfigurationDropDown("configuration", "", true, 1);
    configuration.setTabIndex(30);
    configuration.addFormEvent("onChange", "buildSubConfigs(this.selectedIndex)");
    selectionForm.addElement(configuration);
    Vector configs = Cache.getSelectionConfigs();
    SelectionConfiguration config = (SelectionConfiguration)configs.get(0);
    FormDropDownMenu subConfiguration = new FormDropDownMenu("subConfiguration", "", "", "", true);
    subConfiguration.addFormEvent("onChange", "isSubConfigParent(this.selectedIndex)");
    subConfiguration.setTabIndex(31);
    subConfiguration.setEnabled(false);
    selectionForm.addElement(subConfiguration);
    FormTextField test = new FormTextField("test", "", false, 8, 8);
    test.setTabIndex(32);
    test.setClassName("ctrlShort");
    test.addFormEvent("onChange", "javaScript:clickSell(this,true);");
    selectionForm.addElement(test);
    FormDropDownMenu priceCode = new FormDropDownMenu("priceCode", "", "-1" + getSellCodesStringDPC(), "&nbsp;" + getSellCodesStringDPC(), true);
    priceCode.setTabIndex(33);
    priceCode.setClassName("ctrlSmall");
    selectionForm.addElement(priceCode);
    FormTextField numOfUnits = new FormTextField("numOfUnits", "0", false, 10, 10);
    numOfUnits.setTabIndex(34);
    numOfUnits.setClassName("ctrlShort");
    selectionForm.addElement(numOfUnits);
    FormDropDownMenu contactList = MilestoneHelper.getContactsDropDown(context, "contactlist", new Vector(), user, true);
    contactList.setTabIndex(35);
    contactList.setClassName("ctrlMedium");
    selectionForm.addElement(contactList);
    FormTextField contact = new FormTextField("contact", "", false, 14, 30);
    contact.setTabIndex(36);
    contact.setClassName("ctrlMedium");
    selectionForm.addElement(contact);
    FormCheckBox parentalIndicator = new FormCheckBox("parentalIndicator", "", false, false);
    parentalIndicator.setTabIndex(37);
    selectionForm.addElement(parentalIndicator);
    FormCheckBox specPkgIndicator = new FormCheckBox("specialPkgIndicator", "", false, false);
    specPkgIndicator.addFormEvent("onDblClick", "document.forms[0].PackagingHelper.value=document.forms[0].package.value;togglePackaging();");
    specPkgIndicator.setTabIndex(38);
    selectionForm.addElement(specPkgIndicator);
    FormTextField pkg = new FormTextField("package", "", false, 13, 100);
    pkg.setTabIndex(39);
    pkg.setClassName("ctrlMedium");
    pkg.addFormEvent("onDblClick", "document.forms[0].PackagingHelper.value=document.forms[0].package.value;togglePackaging();");
    selectionForm.addElement(pkg);
    FormDropDownMenu genre = MilestoneHelper.getPfmLookupDropDown("genre", Cache.getMusicTypes(), "", true, true);
    genre.setTabIndex(40);
    genre.setId("music_type");
    selectionForm.addElement(genre);
    FormTextField territory = new FormTextField("territory", "", false, 13, 255);
    territory.setTabIndex(41);
    territory.setClassName("ctrlMedium");
    territory.addFormEvent("onDblClick", "document.forms[0].TerritoryHelper.value=document.forms[0].territory.value;toggleTerritory();");
    selectionForm.addElement(territory);
    FormHidden productionGroupCode = new FormHidden("productGroupCode", selection.getProductionGroupCode(), false);
    productionGroupCode.setTabIndex(42);
    productionGroupCode.setDisplayName(selection.getProductionGroupCode());
    productionGroupCode.setClassName("ctrlMedium");
    selectionForm.addElement(productionGroupCode);
    FormTextArea specialInstructions = new FormTextArea("specialInstructions", "", false, 3, 80, "virtual");
    specialInstructions.setTabIndex(42);
    selectionForm.addElement(specialInstructions);
    FormTextField lastStreetUpdatedDate = new FormTextField("laststreetupdateddate", "", false, 13);
    selectionForm.addElement(lastStreetUpdatedDate);
    FormTextField lastUpdatedDate = new FormTextField("lastupdateddate", "", false, 50);
    selectionForm.addElement(lastUpdatedDate);
    FormTextField originDate = new FormTextField("origindate", "", false, 13);
    selectionForm.addElement(originDate);
    FormTextArea packagingHelper = new FormTextArea("PackagingHelper", "", false, 2, 44, "virtual");
    selectionForm.addElement(packagingHelper);
    FormTextArea territoryHelper = new FormTextArea("TerritoryHelper", selection.getSelectionTerritory(), false, 2, 44, "virtual");
    selectionForm.addElement(territoryHelper);
    FormTextArea comments = new FormTextArea("comments", "", false, 2, 44, "virtual");
    comments.addFormEvent("onBlur", "Javascript:checkField(this)");
    selectionForm.addElement(comments);
    addSelectionSearchElements(context, new Selection(), selectionForm, MilestoneHelper.getUserCompanies(context), true);
    if (context.getSessionValue("NOTEPAD_SELECTION_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_SELECTION_VISIBLE")); 
    context.putDelivery("releaseWeek", "");
    context.putDelivery("new-or-copy", "true");
    context.putDelivery("price", "0.00");
    boolean isParent = false;
    if (selection.getSelectionSubConfig() != null)
      isParent = selection.getSelectionSubConfig().isParent(); 
    context.putDelivery("is-parent", String.valueOf(isParent));
    String lastUpdateUser = "";
    if (selection.getLastUpdatingUser() != null)
      lastUpdateUser = selection.getLastUpdatingUser().getName(); 
    context.putDelivery("lastUpdateUser", lastUpdateUser);
    return selectionForm;
  }
  
  public static void resetSearchVariables(User user, User userSrch, Context context) {
    user.SS_searchInitiated = userSrch.SS_searchInitiated;
    user.SS_artistSearch = userSrch.SS_artistSearch;
    user.SS_titleSearch = userSrch.SS_titleSearch;
    user.SS_selectionNoSearch = userSrch.SS_selectionNoSearch;
    user.SS_prefixIDSearch = userSrch.SS_prefixIDSearch;
    user.SS_upcSearch = userSrch.SS_upcSearch;
    user.SS_streetDateSearch = userSrch.SS_streetDateSearch;
    user.SS_streetEndDateSearch = userSrch.SS_streetEndDateSearch;
    user.SS_configSearch = userSrch.SS_configSearch;
    user.SS_subconfigSearch = userSrch.SS_subconfigSearch;
    user.SS_labelSearch = userSrch.SS_labelSearch;
    user.SS_companySearch = userSrch.SS_companySearch;
    user.SS_contactSearch = userSrch.SS_contactSearch;
    user.SS_familySearch = userSrch.SS_familySearch;
    user.SS_environmentSearch = userSrch.SS_environmentSearch;
    user.SS_projectIDSearch = userSrch.SS_projectIDSearch;
    user.SS_productTypeSearch = userSrch.SS_productTypeSearch;
    user.SS_showAllSearch = userSrch.SS_showAllSearch;
    user.RC_environment = userSrch.RC_environment;
    user.RC_releasingFamily = userSrch.RC_releasingFamily;
    user.RC_labelContact = userSrch.RC_labelContact;
    user.RC_productType = userSrch.RC_productType;
    context.removeSessionValue("ResetSearchVariables");
  }
  
  public static DcGDRSResults GDRSProductStatusGet(Selection selection, int environmentID) {
    DcGDRSResults dcGDRSResults = new DcGDRSResults();
    try {
      dcGDRSResults = projectSearchSvcClient.GDRSProductStatusGet(selection.getSelectionID(), selection.getReleaseFamilyId(), environmentID);
    } catch (RemoteException re) {
      dcGDRSResults.setExceptionMessage(re.getMessage());
      System.out.println(re.getMessage());
    } catch (Exception e) {
      dcGDRSResults.setExceptionMessage(e.getMessage());
      System.out.println(e.getMessage());
    } 
    return dcGDRSResults;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\SelectionHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */