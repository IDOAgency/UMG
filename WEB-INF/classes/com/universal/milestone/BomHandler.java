package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.gemini.Context;
import com.techempower.gemini.Dispatcher;
import com.techempower.gemini.FormCheckBox;
import com.techempower.gemini.FormDateField;
import com.techempower.gemini.FormDropDownMenu;
import com.techempower.gemini.FormHidden;
import com.techempower.gemini.FormRadioButtonGroup;
import com.techempower.gemini.FormTextArea;
import com.techempower.gemini.FormTextField;
import com.techempower.gemini.FormValidation;
import com.techempower.gemini.GeminiApplication;
import com.techempower.gemini.Handler;
import com.universal.milestone.Bom;
import com.universal.milestone.BomCassetteDetail;
import com.universal.milestone.BomDVDDetail;
import com.universal.milestone.BomDiskDetail;
import com.universal.milestone.BomHandler;
import com.universal.milestone.BomVinylDetail;
import com.universal.milestone.Cache;
import com.universal.milestone.Company;
import com.universal.milestone.CompanyAcl;
import com.universal.milestone.EmailDistribution;
import com.universal.milestone.Environment;
import com.universal.milestone.Form;
import com.universal.milestone.Label;
import com.universal.milestone.LookupObject;
import com.universal.milestone.MilestoneConstants;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.MilestoneHelper_2;
import com.universal.milestone.MilestoneInfrastructure;
import com.universal.milestone.MilestoneSecurity;
import com.universal.milestone.Notepad;
import com.universal.milestone.NotepadSortOrder;
import com.universal.milestone.ReleasingFamily;
import com.universal.milestone.ReportHandler;
import com.universal.milestone.SecureHandler;
import com.universal.milestone.Selection;
import com.universal.milestone.SelectionHandler;
import com.universal.milestone.SelectionManager;
import com.universal.milestone.User;
import com.universal.milestone.UserManager;
import com.universal.milestone.jms.MessageObject;
import inetsoft.report.TextBoxElement;
import inetsoft.report.XStyleSheet;
import inetsoft.report.io.Builder;
import inetsoft.report.lens.DefaultTableLens;
import inetsoft.report.pdf.PDF4Generator;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

public class BomHandler extends SecureHandler implements Handler, MilestoneConstants {
  public static final String COMPONENT_CODE = "hBom";
  
  public static final int REPORT_TEXTBOX_BOTTOM_BORDER = 4113;
  
  public GeminiApplication application;
  
  public ComponentLog log;
  
  public BomHandler(GeminiApplication application) {
    this.application = application;
    this.log = application.getLog("hBom");
  }
  
  public String getDescription() { return "Bill of Materials"; }
  
  public boolean acceptRequest(Dispatcher dispatcher, Context context, String command) {
    if (super.acceptRequest(dispatcher, context, command))
      if (command.startsWith("bom"))
        return handleRequest(dispatcher, context, command);  
    return false;
  }
  
  public boolean handleRequest(Dispatcher dispatcher, Context context, String command) {
    EmailDistribution.removeSessionValues(context);
    if (command.equalsIgnoreCase("bom-editor") || command.equalsIgnoreCase("bom-edit-new")) {
      edit(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("bom-edit-save")) {
      save(dispatcher, context, command);
    } 
    if (command.equalsIgnoreCase("bom-edit-copy"))
      editCopy(dispatcher, context, command); 
    if (command.equalsIgnoreCase("bom-paste-copy")) {
      pasteCopy(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("bom-search")) {
      search(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("bom-sort")) {
      sort(dispatcher, context);
    } else if (command.equalsIgnoreCase("bom-print-pdf")) {
      print(dispatcher, context, command, 0, true, null);
    } else if (command.equalsIgnoreCase("bom-print-pdf4")) {
      print(dispatcher, context, command, 0, false, null);
    } else if (command.equalsIgnoreCase("bom-print-rtf")) {
      print(dispatcher, context, command, 1, true, null);
    } else if (command.equalsIgnoreCase("bom-group")) {
      sortGroup(dispatcher, context);
    } 
    if (command.equalsIgnoreCase("bom-send-email"))
      EmailDistribution.sendEmail(dispatcher, context, "bom-editor", null); 
    return true;
  }
  
  private boolean edit(Dispatcher dispatcher, Context context, String command) {
    int selectionID = -1;
    User user = (User)context.getSessionValue("user");
    Notepad notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 0);
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Selection selection = new Selection();
    Bom bom = null;
    selection = MilestoneHelper.getScreenSelection(context);
    context.putSessionValue("Selection", selection);
    boolean sameConfig = false;
    String configCopiedBom = "";
    String configCurrentBom = "";
    context.removeDelivery("newBOM");
    if (selection != null) {
      bom = SelectionManager.getInstance().getBom(selection);
      if (bom != null && bom.getBomId() != 0) {
        Form form = buildForm(context, selection, bom, command);
        form.addElement(new FormHidden("copyPaste", "copy", true));
        context.putDelivery("Form", form);
      } else {
        context.putDelivery("newBOM", Boolean.TRUE);
        Form form = buildNewForm(context, selection, command);
        if ((Bom)context.getSessionValue("copiedBom") != null) {
          Selection currentSelection = (Selection)context.getSessionValue("Selection");
          configCurrentBom = currentSelection.getSelectionConfig().getSelectionConfigurationName();
          Selection copiedSelection = ((Bom)context.getSessionValue("copiedBom")).getSelection();
          configCopiedBom = copiedSelection.getSelectionConfig().getSelectionConfigurationName();
          if (configCopiedBom.equals(configCurrentBom))
            form.addElement(new FormHidden("copyPaste", "paste", true)); 
        } 
        context.putDelivery("Form", form);
        context.removeDelivery("newBOM");
      } 
    } else {
      if (context.getSessionValue("NOTEPAD_BOM_VISIBLE") != null)
        context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_BOM_VISIBLE")); 
      Form form = new Form(this.application, "form", this.application.getInfrastructure().getServletURL(), "POST");
      form.addElement(new FormHidden("cmd", "bom-editor", true));
      form.addElement(new FormHidden("selectionID", "-1", true));
      form.addElement(new FormHidden("OrderBy", "", true));
      form.addElement(new FormHidden("bomLastUpdateCheck", "-1", true));
      addSelectionSearchElements(context, selection, form);
      context.putDelivery("Form", form);
      return context.includeJSP("blank-bom-editor.jsp");
    } 
    context.putSessionValue("Bom", bom);
    if (selection.getIsDigital())
      return dispatcher.redispatch(context, "schedule-editor"); 
    return context.includeJSP("bom-editor.jsp");
  }
  
  private boolean editCopy(Dispatcher dispatcher, Context context, String command) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 0);
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Selection selection = (Selection)context.getSessionValue("Selection");
    int selectionID = selection.getSelectionID();
    Bom targetBom = SelectionManager.getInstance().getBom(selection);
    Bom copiedBom = null;
    if (targetBom != null) {
      try {
        copiedBom = (Bom)targetBom.clone();
        copiedBom.setSelection((Selection)context.getSessionValue("Selection"));
      } catch (CloneNotSupportedException cloneNotSupportedException) {}
      Form form = buildForm(context, selection, copiedBom, command);
      form.addElement(new FormHidden("cmd", "bom-edit-copy", true));
      form.addElement(new FormHidden("copyPaste", "paste", true));
      context.putSessionValue("copiedBom", copiedBom);
      context.putSessionValue("Bom", targetBom);
      context.putDelivery("Form", form);
    } 
    if (context.getSessionValue("NOTEPAD_BOM_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_BOM_VISIBLE")); 
    return edit(dispatcher, context, command);
  }
  
  private boolean pasteCopy(Dispatcher dispatcher, Context context, String command) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 0);
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Selection selection = (Selection)context.getSessionValue("Selection");
    int selectionID = selection.getSelectionID();
    Bom copiedBom = (Bom)context.getSessionValue("copiedBom");
    Bom bom = (Bom)context.getSessionValue("Bom");
    copiedBom.setDate(Calendar.getInstance());
    copiedBom.setStreetDateOnBom(selection.getStreetDate());
    copiedBom.setPrintOption("Draft");
    User currentUser = (User)context.getSessionValue("user");
    String selectionValue = "Add";
    copiedBom.setType(selectionValue);
    copiedBom.setChangeNumber("-1");
    copiedBom.setSubmitter(currentUser.getName());
    copiedBom.setPhone(currentUser.getPhone());
    copiedBom.setEmail(currentUser.getEmail());
    copiedBom.setUnitsPerKG(0);
    copiedBom.setUseShrinkWrap(true);
    copiedBom.setHasSpineSticker(true);
    copiedBom.setConfiguration("");
    copiedBom.setRunTime("");
    copiedBom.setModifiedOn(null);
    copiedBom.setModifiedBy(99999);
    Form form = buildForm(context, selection, copiedBom, command);
    form.removeElement("cmd");
    form.addElement(new FormHidden("cmd", "bom-paste-copy", true));
    context.putSessionValue("Bom", bom);
    context.putDelivery("Form", form);
    context.removeSessionValue("copiedBom");
    if (context.getSessionValue("NOTEPAD_BOM_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_BOM_VISIBLE")); 
    if (selection.getIsDigital())
      return dispatcher.redispatch(context, "schedule-editor"); 
    return context.includeJSP("bom-editor.jsp");
  }
  
  private boolean search(Dispatcher dispatcher, Context context, String command) {
    String isSelectionSearchResults = context.getParameter("isSelectionSearchResults");
    if (isSelectionSearchResults == null || !isSelectionSearchResults.equals("true")) {
      Notepad notepad = MilestoneHelper.getNotepadFromSession(0, 
          context);
      notepad.setAllContents(null);
      notepad.setSelected(null);
      notepad.setMaxRecords(225);
      Form form = new Form(this.application, "selectionForm", 
          this.application.getInfrastructure().getServletURL(), 
          "POST");
      addSelectionSearchElements(context, null, form);
      form.setValues(context);
      SelectionManager.getInstance().setSelectionNotepadQuery(context, 
          MilestoneSecurity.getUser(context).getUserId(), notepad, form);
    } 
    dispatcher.redispatch(context, "bom-editor");
    return true;
  }
  
  private boolean sort(Dispatcher dispatcher, Context context) {
    int userId = ((User)context.getSessionValue("user")).getUserId();
    Notepad notepad = SelectionManager.getInstance().getSelectionNotepad(context, userId, 0);
    NotepadSortOrder.getNotepadSortOrderFromSession(context).sortHelper(dispatcher, context, notepad);
    dispatcher.redispatch(context, "bom-editor");
    return true;
  }
  
  protected Form buildForm(Context context, Selection selection, Bom bom, String command) {
    Form bomForm = new Form(this.application, "bomForm", this.application.getInfrastructure().getServletURL(), "POST");
    String selectionID = "-1";
    if (selection != null)
      selectionID = String.valueOf(selection.getSelectionID()); 
    bomForm.addElement(new FormHidden("selectionID", selectionID, true));
    long timeStamp = -1L;
    if (bom != null)
      timeStamp = bom.getLastUpdatedCheck(); 
    bomForm.addElement(new FormHidden("bomLastUpdateCheck", Long.toString(timeStamp), true));
    User currentUser = (User)context.getSessionValue("user");
    FormCheckBox UseNoShrinkWrap = new FormCheckBox("UseNoShrinkWrap", "on", "", false, bom.useShrinkWrap());
    UseNoShrinkWrap.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    UseNoShrinkWrap.setId("Shrink Wrap");
    UseNoShrinkWrap.setStartingChecked(bom.useShrinkWrap());
    UseNoShrinkWrap.setTabIndex(13);
    bomForm.addElement(UseNoShrinkWrap);
    FormCheckBox HasSpineSticker = new FormCheckBox("HasSpineSticker", "on", "", false, bom.hasSpineSticker());
    HasSpineSticker.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    HasSpineSticker.setId("Spine Sticker");
    HasSpineSticker.setTabIndex(12);
    HasSpineSticker.setStartingChecked(bom.hasSpineSticker());
    bomForm.addElement(HasSpineSticker);
    if (bom.getBomCassetteDetail() == null && bom.getBomDiskDetail() == null && bom.getBomVinylDetail() == null && bom.getBomDVDDetail() == null) {
      command = "bom-edit-new";
      if (selection.getReleaseType().getName().equalsIgnoreCase("Promotional")) {
        UseNoShrinkWrap.setChecked(false);
        HasSpineSticker.setChecked(false);
      } else {
        UseNoShrinkWrap.setChecked(true);
        HasSpineSticker.setChecked(true);
      } 
    } 
    int secureLevel = getSelectionBomPermissions(selection, currentUser);
    setButtonVisibilities(selection, currentUser, context, secureLevel, command);
    bomForm.addElement(new FormHidden("OrderBy", "", true));
    Calendar defaultDate = Calendar.getInstance();
    MilestoneInfrastructure inf = (MilestoneInfrastructure)context.getInfrastructure();
    String printOption = "Draft";
    if (bom.getPrintOption().length() > 0 && selection.getSelectionNo() != null && 
      selection.getSelectionNo().toUpperCase().indexOf("TEMP") == -1)
      printOption = bom.getPrintOption(); 
    String pOptions = "Draft,Final";
    if (printOption.equalsIgnoreCase("Final"))
      pOptions = printOption; 
    FormRadioButtonGroup PrintOption = new FormRadioButtonGroup("printOption", printOption, pOptions, false);
    boolean numberUnitsIsZero = false;
    numberUnitsIsZero = (selection.getNumberOfUnits() == 0);
    boolean statusIsTBS = false;
    statusIsTBS = SelectionManager.getLookupObjectValue(selection.getSelectionStatus()).equalsIgnoreCase("TBS");
    if (numberUnitsIsZero || statusIsTBS) {
      PrintOption.addFormEvent("onClick", "noUnitsOrTBS(this, " + numberUnitsIsZero + ", " + statusIsTBS + ");hidePrintButtons('printWindow','');toggleSave('" + inf.getImageDirectory() + "');");
    } else {
      PrintOption.addFormEvent("onClick", "hidePrintButtons('printWindow','');toggleSave('" + inf.getImageDirectory() + "');");
    } 
    PrintOption.setTabIndex(0);
    PrintOption.setId("Draft/Final");
    bomForm.addElement(PrintOption);
    FormRadioButtonGroup sendOption = new FormRadioButtonGroup("sendOption", "Send Email", "Send Email,Do Not Send Email", false);
    sendOption.addFormEvent("onClick", "toggleSaveSend('" + inf.getImageDirectory() + "');");
    sendOption.setTabIndex(0);
    bomForm.addElement(sendOption);
    String dateText = MilestoneHelper.getFormatedDate(defaultDate);
    if (bom.getDate() != null)
      dateText = MilestoneHelper.getFormatedDate(bom.getDate()); 
    FormDateField Date = new FormDateField("Date", dateText, false, 10);
    Date.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
    Date.addFormEvent("onBlur", "JavaScript:checkField( this )");
    Date.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    Date.setTabIndex(1);
    bomForm.addElement(Date);
    String typeText = "Add";
    if (bom.getType().equalsIgnoreCase("C")) {
      typeText = "Change";
    } else {
      typeText = "Add";
    } 
    FormRadioButtonGroup IsAdded = new FormRadioButtonGroup("IsAdded", typeText, "Add, Change", false);
    IsAdded.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    IsAdded.setTabIndex(2);
    IsAdded.setId("Add/Change");
    IsAdded.setEnabled(false);
    bomForm.addElement(IsAdded);
    String changeNumberText = "-1";
    if (bom.getChangeNumber() != null && !bom.getChangeNumber().equals(""))
      changeNumberText = bom.getChangeNumber(); 
    FormTextField ChangeNumber = new FormTextField("ChangeNumber", changeNumberText, false, 2, 2);
    ChangeNumber.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    ChangeNumber.setTabIndex(3);
    ChangeNumber.setId("Change #");
    ChangeNumber.setEnabled(false);
    bomForm.addElement(ChangeNumber);
    String submitterText = "";
    if (bom.getSubmitter() != null && !command.equalsIgnoreCase("bom-edit-new")) {
      submitterText = bom.getSubmitter();
    } else {
      submitterText = currentUser.getName();
    } 
    FormTextField Submitter = new FormTextField("Submitter", submitterText, false, 30, 50);
    Submitter.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    Submitter.setTabIndex(4);
    Submitter.setId("Submitted By");
    bomForm.addElement(Submitter);
    String phoneText = "";
    if (bom.getPhone() != null && !command.equalsIgnoreCase("bom-edit-new")) {
      phoneText = bom.getPhone();
    } else {
      phoneText = currentUser.getPhone();
    } 
    FormTextField Phone = new FormTextField("Phone", phoneText, false, 30, 30);
    Phone.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    Phone.setTabIndex(5);
    bomForm.addElement(Phone);
    String emailText = "";
    if (bom.getEmail() != null && !command.equalsIgnoreCase("bom-edit-new")) {
      emailText = bom.getEmail();
    } else if (command.equalsIgnoreCase("bom-edit-new")) {
      emailText = currentUser.getEmail();
    } 
    FormTextField Email = new FormTextField("Email", emailText, false, 30, 50);
    Email.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    Email.setTabIndex(6);
    bomForm.addElement(Email);
    String releasingCompanyText = "";
    Vector companyList = Cache.getReleaseCompanies();
    String releasingCompText = "";
    if (ReleasingFamily.getName(selection.getReleaseFamilyId()) != null)
      releasingCompText = ReleasingFamily.getName(selection.getReleaseFamilyId()); 
    FormTextField releasingComp = new FormTextField("ReleasingComp", releasingCompText, false, 30);
    releasingComp.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    releasingComp.setTabIndex(7);
    releasingComp.setId("Releasing Co");
    if (context.getDelivery("newBOM") == null)
      releasingComp.setStartingValue(bom.getReleasingCompanyId()); 
    bomForm.addElement(releasingComp);
    Selection mySelection = selection;
    String labelText = "";
    if (mySelection.getLabel() != null)
      labelText = mySelection.getLabel().getName().toUpperCase(); 
    FormTextField Label = new FormTextField("Label", labelText, false, 30);
    Label.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    String bomLabel = MilestoneHelper.getStructureName(bom.getLabelId()).toUpperCase();
    if (context.getDelivery("newBOM") == null)
      if (bomLabel != null && bomLabel.length() > 0)
        Label.setStartingValue(bomLabel);  
    bomForm.addElement(Label);
    String statusText = "";
    if (mySelection.getSelectionStatus() != null)
      statusText = mySelection.getSelectionStatus().getName(); 
    FormTextField status = new FormTextField("status", statusText, false, 30);
    status.setStartingValue(bom.getStatus());
    bomForm.addElement(status);
    String upc = "";
    if (mySelection.getUpc() != null)
      upc = mySelection.getUpc(); 
    FormTextField Upc = new FormTextField("upc", upc, false, 20);
    if (bom.getUpc() == null) {
      Upc.setStartingValue("");
    } else {
      Upc.setStartingValue(bom.getUpc());
    } 
    bomForm.addElement(Upc);
    String imprintText = "";
    if (mySelection.getImprint() != null)
      imprintText = mySelection.getImprint(); 
    FormTextField imprint = new FormTextField("imprint", imprintText, false, 30);
    bomForm.addElement(imprint);
    String artistText = "";
    if (mySelection.getArtist() != null)
      artistText = mySelection.getArtist(); 
    FormTextField Artist = new FormTextField("Artist", artistText, false, 125);
    Artist.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    if (context.getDelivery("newBOM") == null) {
      Artist.setStartingValue(artistText);
      if (bom.getArtist() != null && bom.getArtist().length() > 0)
        Artist.setStartingValue(bom.getArtist()); 
    } 
    bomForm.addElement(Artist);
    bomForm.addElement(new FormHidden("Artist", artistText, true));
    String titleText = "";
    if (mySelection.getTitle() != null)
      titleText = mySelection.getTitle(); 
    FormTextField Title = new FormTextField("Title", titleText, false, 125);
    Title.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    if (context.getDelivery("newBOM") == null)
      if (bom.getTitle() != null && bom.getTitle().length() > 0)
        Title.setStartingValue(bom.getTitle());  
    bomForm.addElement(Title);
    String isRetailText = "";
    if (bom.isRetail) {
      isRetailText = "Commercial";
    } else {
      isRetailText = "Promotional";
    } 
    String selIsRetailText = "";
    if (selection.getReleaseType().getName().equalsIgnoreCase("Promotional")) {
      selIsRetailText = "Promotional";
    } else {
      selIsRetailText = "Commercial";
    } 
    FormRadioButtonGroup IsRetail = new FormRadioButtonGroup("IsRetail", selIsRetailText, "Commercial, Promotional", false);
    IsRetail.setTabIndex(8);
    if (context.getDelivery("newBOM") == null)
      IsRetail.setStartingValue(isRetailText); 
    IsRetail.setId("Type");
    bomForm.addElement(IsRetail);
    String selectionNumberText = "";
    String prefix = SelectionManager.getLookupObjectValue(selection.getPrefixID());
    if (prefix.length() > 0) {
      selectionNumberText = String.valueOf(SelectionManager.getLookupObjectValue(selection.getPrefixID())) + " " + selection.getSelectionNo();
    } else {
      selectionNumberText = selection.getSelectionNo();
    } 
    FormTextField SelectionNumber = new FormTextField("SelectionNumber", selectionNumberText, false, 50);
    SelectionNumber.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SelectionNumber.setId("Local Prod #");
    if (context.getDelivery("newBOM") == null)
      if (bom.getSelectionNumber() != null && bom.getSelectionNumber().length() > 0)
        SelectionNumber.setStartingValue(bom.getSelectionNumber());  
    bomForm.addElement(SelectionNumber);
    String streetDateOnBomText = "";
    String selStreetDateOnBomText = "";
    if (bom.getBomCassetteDetail() != null || 
      bom.getBomDiskDetail() != null || 
      bom.getBomVinylDetail() != null || 
      bom.getBomDVDDetail() != null) {
      if (bom.getStreetDateOnBom() != null)
        streetDateOnBomText = MilestoneHelper.getFormatedDate(bom.getStreetDateOnBom()); 
    } else {
      streetDateOnBomText = MilestoneHelper.getFormatedDate(mySelection.getStreetDate());
    } 
    selStreetDateOnBomText = MilestoneHelper.getFormatedDate(mySelection.getStreetDate());
    FormDateField streetDateOnBom = new FormDateField("DueDate", selStreetDateOnBomText, false, 10);
    streetDateOnBom.setEnabled(false);
    streetDateOnBom.setTabIndex(9);
    streetDateOnBom.setStartingValue(streetDateOnBomText);
    streetDateOnBom.setId("Street Date");
    bomForm.addElement(streetDateOnBom);
    String unitsPerSetText = "";
    if (selection.getNumberOfUnits() > 0)
      try {
        unitsPerSetText = Integer.toString(selection.getNumberOfUnits());
      } catch (NumberFormatException e) {
        System.out.println("Error converting Units Per Set into integer.");
      }  
    FormTextField UnitsPerPackage = new FormTextField("UnitsPerPackage", unitsPerSetText, false, 10);
    UnitsPerPackage.setEnabled(false);
    UnitsPerPackage.setTabIndex(10);
    UnitsPerPackage.setId("Units per Package ");
    if (context.getDelivery("newBOM") == null)
      UnitsPerPackage.setStartingValue(String.valueOf(bom.getUnitsPerKG())); 
    bomForm.addElement(UnitsPerPackage);
    String runTimeText = "";
    if (bom.getRunTime() != null)
      runTimeText = bom.getRunTime(); 
    FormTextField Runtime = new FormTextField("Runtime", runTimeText, false, 10, 10);
    Runtime.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    Runtime.setTabIndex(11);
    Runtime.setId("Runtime");
    bomForm.addElement(Runtime);
    FormTextField Configuration = new FormTextField("Configuration", bom.getConfiguration(), false, 10, 10);
    Configuration.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    bomForm.addElement(Configuration);
    String configuration = "";
    configuration = selection.getSelectionConfig().getSelectionConfigurationAbbreviation();
    boolean isCDoverride = false;
    if (bom.getFormat().equalsIgnoreCase("CDO"))
      isCDoverride = true; 
    if (configuration.equalsIgnoreCase("CAS")) {
      if (bom.getBomCassetteDetail() == null) {
        buildNewCassette(bomForm, selection, bom);
        bomForm.addElement(new FormHidden("cmd", "bom-edit-new", true));
      } else {
        buildCassette(bomForm, selection, bom);
      } 
    } else if (configuration.equalsIgnoreCase("VIN")) {
      if (bom.getBomVinylDetail() == null) {
        buildNewVinyl(bomForm, selection, bom);
        bomForm.addElement(new FormHidden("cmd", "bom-edit-new", true));
      } else {
        buildVinyl(bomForm, selection, bom);
      } 
    } else if (configuration.equalsIgnoreCase("DVV") && !isCDoverride) {
      if (bom.getBomDVDDetail() == null) {
        buildNewDVD(bomForm, selection, bom);
        bomForm.addElement(new FormHidden("cmd", "bom-edit-new", true));
      } else {
        buildDVD(bomForm, selection, bom);
      } 
    } else if (bom.getBomDiskDetail() == null) {
      buildNewDisk(bomForm, selection, bom);
      bomForm.addElement(new FormHidden("cmd", "bom-edit-new", true));
    } else {
      buildDisk(bomForm, selection, bom, bom.getBomDiskDetail());
    } 
    FormTextArea SpecialInstructions = new FormTextArea("SpecialInstructions", bom.getSpecialInstructions(), false, 15, 100, "virtual");
    SpecialInstructions.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SpecialInstructions.setTabIndex(100);
    SpecialInstructions.setId("Special Instructions");
    bomForm.addElement(SpecialInstructions);
    FormTextField lastUpdated = new FormTextField("lastUpdatedDate", false, 50);
    lastUpdated.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    if (bom.getModifiedOn() != null)
      lastUpdated.setValue(MilestoneHelper.getCustomFormatedDate(bom.getModifiedOn(), "M/d/yyyy hh:mm:ss a 'ET'")); 
    bomForm.addElement(lastUpdated);
    FormTextField lastLegacyUpdateDate = new FormTextField("lastLegacyUpdateDate", false, 50);
    lastLegacyUpdateDate.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    lastLegacyUpdateDate.setValue(MilestoneHelper.getCustomFormatedDate(selection.getLastLegacyUpdateDate(), "M/d/yyyy hh:mm:ss a 'ET'"));
    bomForm.addElement(lastLegacyUpdateDate);
    FormTextField lastUpdatedBy = new FormTextField("lastUpdatedBy", false, 50);
    lastUpdatedBy.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    if (UserManager.getInstance().getUser(bom.getModifiedBy()) != null)
      lastUpdatedBy.setValue(UserManager.getInstance().getUser(bom.getModifiedBy()).getName()); 
    bomForm.addElement(lastUpdatedBy);
    String archieDateText = "";
    if (selection.getArchieDate() != null)
      archieDateText = MilestoneHelper.getCustomFormatedDate(selection.getArchieDate(), "M/d/yyyy hh:mm:ss a 'ET'"); 
    FormTextField archieDate = new FormTextField("archieDate", archieDateText, false, 13);
    bomForm.addElement(archieDate);
    bomForm = addSelectionSearchElements(context, selection, bomForm);
    bomForm.addElement(new FormHidden("cmd", "bom-editor", true));
    context.putDelivery("releaseWeek", MilestoneHelper.getReleaseWeekString(selection));
    if (context.getSessionValue("NOTEPAD_BOM_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_BOM_VISIBLE")); 
    selection.setBom(bom);
    context.putDelivery("selection", selection);
    return bomForm;
  }
  
  public boolean save(Dispatcher dispatcher, Context context, String command) {
    Selection selection = null;
    Selection selectionTemp = null;
    Bom bom = null;
    int selectionID = -1;
    if (command.equalsIgnoreCase("selectionSave")) {
      selectionTemp = (Selection)context.getSessionValue("pfmBomSelection");
      selection = SelectionManager.getInstance().getSelectionHeader(selectionTemp.getSelectionID());
    } else {
      Notepad notepad = MilestoneHelper.getNotepadFromSession(0, context);
      selectionID = Integer.parseInt(context.getRequestValue("selectionID"));
      selection = SelectionManager.getInstance().getSelectionHeader(selectionID);
      notepad.setSelected(selection);
    } 
    MessageObject messageObject = new MessageObject();
    try {
      messageObject.selectionObj = (Selection)selection.clone();
    } catch (CloneNotSupportedException ce) {
      messageObject.selectionObj = selection;
    } 
    User user = (User)context.getSessionValue("user");
    Notepad notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 0);
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    bom = SelectionManager.getInstance().getBom(selection);
    boolean finalFlag = false;
    boolean sendEmail = false;
    Form form = null;
    context.removeDelivery("newBOM");
    if (bom != null) {
      form = buildForm(context, selection, bom, command);
      String configString = form.getStringValue("Configuration");
      bom.setConfiguration(configString);
    } else {
      context.putDelivery("newBOM", Boolean.TRUE);
      bom = new Bom();
      bom.setReleaseId(selection.getSelectionID());
      bom.setConfiguration(selection.getSelectionConfig().getSelectionConfigurationAbbreviation());
      form = buildForm(context, selection, bom, command);
      form.addElement(new FormHidden("copyPaste", "copy", true));
      context.removeDelivery("newBOM");
    } 
    form.addElement(new FormHidden("cmd", "bom-edit-save", true));
    long timestamp = bom.getLastUpdatedCheck();
    if (!command.equalsIgnoreCase("selectionSave")) {
      String timestampStr = context.getRequestValue("bomLastUpdateCheck");
      if (timestampStr != null)
        bom.setLastUpdatedCheck(Long.parseLong(timestampStr)); 
    } 
    if (SelectionManager.getInstance().isTimestampValid(bom)) {
      bom.setLastUpdatedCheck(timestamp);
      if (!command.equalsIgnoreCase("selectionSave"))
        form.setValues(context); 
      String sendOption = form.getStringValue("sendOption");
      if (!form.isUnchanged()) {
        String printOption = form.getStringValue("printOption");
        String typeString = form.getStringValue("IsAdded");
        if (printOption.equalsIgnoreCase("Draft")) {
          bom.setPrintOption("Draft");
          if (typeString.indexOf("Add") > -1) {
            typeString = "A";
            bom.setChangeNumber("-1");
          } else {
            typeString = "C";
          } 
          bom.setType(typeString);
          bom.setType("A");
          bom.setChangeNumber("-1");
          FormTextField changeField = (FormTextField)form.getElement("ChangeNumber");
          changeField.setValue("-1");
          FormRadioButtonGroup isAdded = (FormRadioButtonGroup)form.getElement("IsAdded");
          isAdded.setValue("Add");
        } else {
          bom.setPrintOption("Final");
          finalFlag = true;
          int changeNumberInt = -1;
          try {
            changeNumberInt = Integer.parseInt(form.getStringValue("ChangeNumber"));
          } catch (Exception e) {
            changeNumberInt = -1;
          } 
          if (changeNumberInt >= 0) {
            StringBuffer changedFields = new StringBuffer();
            if (EmailDistribution.isFormChanged(form.getChangedElements(), changedFields, true, false, form, messageObject)) {
              sendEmail = true;
              if (sendOption != null && 
                !context.getCommand().equals("selection-send-cancel") && 
                sendOption.equalsIgnoreCase("Send Email"))
                changeNumberInt++; 
              bom.setChangeNumber(String.valueOf(changeNumberInt));
              bom.setType("C");
              FormTextField changeField = (FormTextField)form.getElement("ChangeNumber");
              changeField.setValue(String.valueOf(changeNumberInt));
              FormRadioButtonGroup isAdded = (FormRadioButtonGroup)form.getElement("IsAdded");
              isAdded.setValue("Change");
            } 
          } else {
            sendEmail = true;
            if (!context.getCommand().equals("selection-send-cancel"))
              changeNumberInt++; 
            bom.setChangeNumber(String.valueOf(changeNumberInt));
            bom.setType("A");
            FormTextField changeField = (FormTextField)form.getElement("ChangeNumber");
            changeField.setValue(String.valueOf(changeNumberInt));
            FormRadioButtonGroup isAdded = (FormRadioButtonGroup)form.getElement("IsAdded");
            isAdded.setValue("Add");
          } 
        } 
        if (sendOption != null && !sendOption.equalsIgnoreCase("Send Email"))
          sendEmail = false; 
        int labelID = -1;
        labelID = selection.getLabel().getStructureID();
        bom.setLabelId(labelID);
        if (selection != null)
          bom.setReleasingCompanyId(ReleasingFamily.getName(selection.getReleaseFamilyId())); 
        String selnoString = form.getStringValue("SelectionNumber");
        bom.setSelectionNumber(selnoString);
        String titleString = form.getStringValue("Title");
        bom.setTitle(titleString);
        String artistString = form.getStringValue("Artist");
        bom.setArtist(artistString);
        String dateString = form.getStringValue("Date");
        bom.setDate(MilestoneHelper.getDate(dateString));
        String submitterString = form.getStringValue("Submitter");
        bom.setSubmitter(submitterString);
        String phoneString = form.getStringValue("Phone");
        bom.setPhone(phoneString);
        String emailString = form.getStringValue("Email");
        bom.setEmail(emailString);
        String retailString = "";
        if (selection.getReleaseType().getName().equalsIgnoreCase("Promotional")) {
          retailString = "Promotional";
        } else {
          retailString = "Commercial";
        } 
        boolean isRetail = false;
        if (retailString.equalsIgnoreCase("Commercial"))
          isRetail = true; 
        bom.setIsRetail(isRetail);
        String streetDateOnBomString = form.getStringValue("DueDate");
        Calendar streetDateOnBom = null;
        try {
          streetDateOnBom = MilestoneHelper.getDate(streetDateOnBomString);
          if (streetDateOnBom != null) {
            bom.setStreetDateOnBom(streetDateOnBom);
          } else {
            bom.setStreetDateOnBom(null);
          } 
        } catch (Exception e) {
          bom.setStreetDateOnBom(null);
        } 
        int unitsInt = 0;
        try {
          unitsInt = Integer.parseInt(form.getStringValue("UnitsPerPackage"));
        } catch (Exception exception) {}
        bom.setUnitsPerKG(unitsInt);
        String runString = form.getStringValue("Runtime");
        bom.setRunTime(runString);
        bom.setUseShrinkWrap(((FormCheckBox)form.getElement("UseNoShrinkWrap")).isChecked());
        String configString = form.getStringValue("Configuration");
        if (configString != null)
          bom.setConfiguration(configString); 
        bom.setHasSpineSticker(((FormCheckBox)form.getElement("HasSpineSticker")).isChecked());
        String insString = MilestoneHelper_2.cleanText(form.getStringValue("SpecialInstructions"));
        bom.setSpecialInstructions(insString);
        String statusString = form.getStringValue("status");
        bom.setStatus(statusString);
        String upcStr = form.getStringValue("upc");
        bom.setUpc(upcStr);
        String configuration = "";
        configuration = selection.getSelectionConfig().getSelectionConfigurationAbbreviation();
        boolean isCDoverride = false;
        if (bom.getFormat().equalsIgnoreCase("CDO"))
          isCDoverride = true; 
        if (configuration.equalsIgnoreCase("CAS")) {
          BomCassetteDetail bomCassetteDetail = bom.getBomCassetteDetail();
          if (bomCassetteDetail == null)
            bomCassetteDetail = new BomCassetteDetail(); 
          bomCassetteDetail.coStatusIndicator = ((FormCheckBox)form.getElement("PID5")).isChecked();
          bomCassetteDetail.coInk1 = form.getStringValue("INK15");
          bomCassetteDetail.coInk2 = form.getStringValue("INK25");
          bomCassetteDetail.coColor = MilestoneHelper_2.cleanText(form.getStringValue("SEL5"));
          bomCassetteDetail.coInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF5"));
          bomCassetteDetail.norelcoStatusIndicator = ((FormCheckBox)form.getElement("PID16")).isChecked();
          bomCassetteDetail.norelcoColor = MilestoneHelper_2.cleanText(form.getStringValue("SEL16"));
          bomCassetteDetail.norelcoInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF16"));
          bomCassetteDetail.jCardStatusIndicator = ((FormCheckBox)form.getElement("PID13")).isChecked();
          try {
            bomCassetteDetail.norelcoSupplierId = Integer.parseInt(form.getStringValue("SID16"));
          } catch (Exception exception) {}
          try {
            bomCassetteDetail.coParSupplierId = Integer.parseInt(form.getStringValue("SID5"));
          } catch (Exception exception) {}
          try {
            bomCassetteDetail.jCardSupplierId = Integer.parseInt(form.getStringValue("SID13"));
          } catch (Exception exception) {}
          bomCassetteDetail.jCardInk1 = form.getStringValue("INK113");
          bomCassetteDetail.jCardInk2 = form.getStringValue("INK213");
          bomCassetteDetail.jCardPanels = MilestoneHelper_2.cleanText(form.getStringValue("SEL13"));
          bomCassetteDetail.jCardInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF13"));
          bomCassetteDetail.uCardStatusIndicator = ((FormCheckBox)form.getElement("PID24")).isChecked();
          try {
            bomCassetteDetail.uCardSupplierId = Integer.parseInt(form.getStringValue("SID24"));
          } catch (Exception exception) {}
          bomCassetteDetail.uCardInk1 = form.getStringValue("INK124");
          bomCassetteDetail.uCardInk2 = form.getStringValue("INK224");
          bomCassetteDetail.uCardPanels = MilestoneHelper_2.cleanText(form.getStringValue("SEL24"));
          bomCassetteDetail.uCardInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF24"));
          bomCassetteDetail.oCardStatusIndicator = ((FormCheckBox)form.getElement("PID17")).isChecked();
          try {
            bomCassetteDetail.oCardSupplierId = Integer.parseInt(form.getStringValue("SID17"));
          } catch (Exception exception) {}
          bomCassetteDetail.oCardInk1 = form.getStringValue("INK117");
          bomCassetteDetail.oCardInk2 = form.getStringValue("INK217");
          bomCassetteDetail.oCardInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF17"));
          bomCassetteDetail.stickerOneCardStatusIndicator = ((FormCheckBox)form.getElement("PID21")).isChecked();
          try {
            bomCassetteDetail.stickerOneCardSupplierId = Integer.parseInt(form.getStringValue("SID21"));
          } catch (Exception exception) {}
          bomCassetteDetail.stickerOneCardInk1 = form.getStringValue("INK121");
          bomCassetteDetail.stickerOneCardInk2 = form.getStringValue("INK221");
          bomCassetteDetail.stickerOneCardPlaces = MilestoneHelper_2.cleanText(form.getStringValue("SEL21"));
          bomCassetteDetail.stickerOneCardInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF21"));
          bomCassetteDetail.stickerTwoCardStatusIndicator = ((FormCheckBox)form.getElement("PID22")).isChecked();
          try {
            bomCassetteDetail.stickerTwoCardSupplierId = Integer.parseInt(form.getStringValue("SID22"));
          } catch (Exception exception) {}
          bomCassetteDetail.stickerTwoCardInk1 = form.getStringValue("INK122");
          bomCassetteDetail.stickerTwoCardInk2 = form.getStringValue("INK222");
          bomCassetteDetail.stickerTwoCardPlaces = MilestoneHelper_2.cleanText(form.getStringValue("SEL22"));
          bomCassetteDetail.stickerTwoCardInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF22"));
          bomCassetteDetail.otherStatusIndicator = ((FormCheckBox)form.getElement("PID18")).isChecked();
          try {
            bomCassetteDetail.otherSupplierId = Integer.parseInt(form.getStringValue("SID18"));
          } catch (Exception exception) {}
          bomCassetteDetail.otherInk1 = form.getStringValue("INK118");
          bomCassetteDetail.otherInk2 = form.getStringValue("INK218");
          bomCassetteDetail.otherInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF18"));
          bom.setBomCassetteDetail(bomCassetteDetail);
        } else if (configuration.equalsIgnoreCase("VIN")) {
          BomVinylDetail bomVinylDetail = bom.getBomVinylDetail();
          if (bomVinylDetail == null)
            bomVinylDetail = new BomVinylDetail(); 
          bomVinylDetail.recordStatusIndicator = ((FormCheckBox)form.getElement("PID19")).isChecked();
          try {
            bomVinylDetail.recordSupplierId = Integer.parseInt(form.getStringValue("SID19"));
          } catch (Exception exception) {}
          bomVinylDetail.recordColor = MilestoneHelper_2.cleanText(form.getStringValue("SEL19"));
          bomVinylDetail.recordInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF19"));
          bomVinylDetail.labelStatusIndicator = ((FormCheckBox)form.getElement("PID14")).isChecked();
          try {
            bomVinylDetail.labelSupplierId = Integer.parseInt(form.getStringValue("SID14"));
          } catch (Exception exception) {}
          bomVinylDetail.labelInk1 = form.getStringValue("INK114");
          bomVinylDetail.labelInk2 = form.getStringValue("INK214");
          bomVinylDetail.labelInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF14"));
          bomVinylDetail.sleeveStatusIndicator = ((FormCheckBox)form.getElement("PID20")).isChecked();
          try {
            bomVinylDetail.sleeveSupplierId = Integer.parseInt(form.getStringValue("SID20"));
          } catch (Exception exception) {}
          bomVinylDetail.sleeveInk1 = form.getStringValue("INK120");
          bomVinylDetail.sleeveInk2 = form.getStringValue("INK220");
          bomVinylDetail.sleeveInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF20"));
          bomVinylDetail.jacketStatusIndicator = ((FormCheckBox)form.getElement("PID11")).isChecked();
          try {
            bomVinylDetail.jacketSupplierId = Integer.parseInt(form.getStringValue("SID11"));
          } catch (Exception exception) {}
          bomVinylDetail.jacketInk1 = form.getStringValue("INK111");
          bomVinylDetail.jacketInk2 = form.getStringValue("INK211");
          bomVinylDetail.jacketInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF11"));
          bomVinylDetail.insertStatusIndicator = ((FormCheckBox)form.getElement("PID33")).isChecked();
          try {
            bomVinylDetail.insertSupplierId = Integer.parseInt(form.getStringValue("SID33"));
          } catch (Exception exception) {}
          bomVinylDetail.insertInk1 = form.getStringValue("INK133");
          bomVinylDetail.insertInk2 = form.getStringValue("INK233");
          bomVinylDetail.insertInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF33"));
          bomVinylDetail.stickerOneStatusIndicator = ((FormCheckBox)form.getElement("PID21")).isChecked();
          try {
            bomVinylDetail.stickerOneSupplierId = Integer.parseInt(form.getStringValue("SID21"));
          } catch (Exception exception) {}
          bomVinylDetail.stickerOneInk1 = form.getStringValue("INK121");
          bomVinylDetail.stickerOneInk2 = form.getStringValue("INK221");
          bomVinylDetail.stickerOnePlaces = MilestoneHelper_2.cleanText(form.getStringValue("SEL21"));
          bomVinylDetail.stickerOneInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF21"));
          bomVinylDetail.stickerTwoStatusIndicator = ((FormCheckBox)form.getElement("PID22")).isChecked();
          try {
            bomVinylDetail.stickerTwoSupplierId = Integer.parseInt(form.getStringValue("SID22"));
          } catch (Exception exception) {}
          bomVinylDetail.stickerTwoInk1 = form.getStringValue("INK122");
          bomVinylDetail.stickerTwoInk2 = form.getStringValue("INK222");
          bomVinylDetail.stickerTwoPlaces = MilestoneHelper_2.cleanText(form.getStringValue("SEL22"));
          bomVinylDetail.stickerTwoInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF22"));
          bomVinylDetail.otherStatusIndicator = ((FormCheckBox)form.getElement("PID18")).isChecked();
          try {
            bomVinylDetail.otherSupplierId = Integer.parseInt(form.getStringValue("SID18"));
          } catch (Exception exception) {}
          bomVinylDetail.otherInk1 = form.getStringValue("INK118");
          bomVinylDetail.otherInk2 = form.getStringValue("INK218");
          bomVinylDetail.otherInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF18"));
          bom.setBomVinylDetail(bomVinylDetail);
        } else {
          BomDiskDetail bomDiskDetail1;
          if (configuration.equalsIgnoreCase("DVV") && !isCDoverride) {
            bomDiskDetail1 = bom.getBomDVDDetail();
          } else {
            bomDiskDetail1 = bom.getBomDiskDetail();
          } 
          if (bomDiskDetail1 == null && configuration.equalsIgnoreCase("DVV") && 
            !isCDoverride) {
            bomDiskDetail1 = new BomDVDDetail();
          } else if (bomDiskDetail1 == null && (!configuration.equalsIgnoreCase("DVV") || 
            isCDoverride)) {
            bomDiskDetail1 = new BomDiskDetail();
          } 
          if (configuration.equalsIgnoreCase("DVV") && !isCDoverride) {
            ((BomDVDDetail)bomDiskDetail1).wrapStatusIndicator = ((FormCheckBox)form
              .getElement("PID25")).isChecked();
            try {
              ((BomDVDDetail)bomDiskDetail1).wrapSupplierId = Integer.parseInt(form
                  .getStringValue("SID25"));
            } catch (Exception exception) {}
            ((BomDVDDetail)bomDiskDetail1).wrapInk1 = form.getStringValue("INK125");
            ((BomDVDDetail)bomDiskDetail1).wrapInk2 = form.getStringValue("INK225");
            ((BomDVDDetail)bomDiskDetail1).wrapInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF25"));
            ((BomDVDDetail)bomDiskDetail1).dvdStatusIndicator = ((FormCheckBox)form
              .getElement("PID26")).isChecked();
            ((BomDVDDetail)bomDiskDetail1).dvdInk1 = form.getStringValue("INK126");
            ((BomDVDDetail)bomDiskDetail1).dvdInk2 = form.getStringValue("INK226");
            ((BomDVDDetail)bomDiskDetail1).dvdSelectionInfo = MilestoneHelper_2.cleanText(form.getStringValue("SEL26"));
            ((BomDVDDetail)bomDiskDetail1).dvdInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF26"));
            ((BomDVDDetail)bomDiskDetail1).bluRayStatusIndicator = ((FormCheckBox)form
              .getElement("PID32")).isChecked();
            ((BomDVDDetail)bomDiskDetail1).bluRayInk1 = form.getStringValue("INK132");
            ((BomDVDDetail)bomDiskDetail1).bluRayInk2 = form.getStringValue("INK232");
            ((BomDVDDetail)bomDiskDetail1).bluRaySelectionInfo = MilestoneHelper_2.cleanText(form.getStringValue("SEL32"));
            ((BomDVDDetail)bomDiskDetail1).bluRayInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF32"));
            ((BomDVDDetail)bomDiskDetail1).discSelectionInfo = MilestoneHelper_2.cleanText(form.getStringValue("SELDISC"));
          } 
          bomDiskDetail1.discStatusIndicator = ((FormCheckBox)form.getElement("PID7")).isChecked();
          try {
            bomDiskDetail1.diskSupplierId = Integer.parseInt(form.getStringValue("SID7"));
          } catch (Exception exception) {}
          bomDiskDetail1.discInk1 = form.getStringValue("INK17");
          bomDiskDetail1.discInk2 = form.getStringValue("INK27");
          bomDiskDetail1.discInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF7"));
          bomDiskDetail1.jewelStatusIndicator = ((FormCheckBox)form.getElement("PID12")).isChecked();
          bomDiskDetail1.jewelColor = MilestoneHelper_2.cleanText(form.getStringValue("SEL12"));
          bomDiskDetail1.jewelInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF12"));
          bomDiskDetail1.trayStatusIndicator = ((FormCheckBox)form.getElement("PID23")).isChecked();
          bomDiskDetail1.trayColor = MilestoneHelper_2.cleanText(form.getStringValue("SEL23"));
          bomDiskDetail1.trayInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF23"));
          bomDiskDetail1.inlayStatusIndicator = ((FormCheckBox)form.getElement("PID10")).isChecked();
          try {
            bomDiskDetail1.inlaySupplierId = Integer.parseInt(form.getStringValue("SID10"));
          } catch (Exception exception) {}
          bomDiskDetail1.inlayInk1 = form.getStringValue("INK110");
          bomDiskDetail1.inlayInk2 = form.getStringValue("INK210");
          bomDiskDetail1.inlayInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF10"));
          bomDiskDetail1.frontStatusIndicator = ((FormCheckBox)form.getElement("PID9")).isChecked();
          try {
            bomDiskDetail1.frontSupplierId = Integer.parseInt(form.getStringValue("SID9"));
          } catch (Exception exception) {}
          bomDiskDetail1.frontInk1 = form.getStringValue("INK19");
          bomDiskDetail1.frontInk2 = form.getStringValue("INK29");
          bomDiskDetail1.frontInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF9"));
          bomDiskDetail1.folderStatusIndicator = ((FormCheckBox)form.getElement("PID8")).isChecked();
          try {
            bomDiskDetail1.folderSupplierId = Integer.parseInt(form.getStringValue("SID8"));
          } catch (Exception exception) {}
          bomDiskDetail1.folderInk1 = form.getStringValue("INK18");
          bomDiskDetail1.folderInk2 = form.getStringValue("INK28");
          bomDiskDetail1.folderPages = MilestoneHelper_2.cleanText(form.getStringValue("SEL8"));
          bomDiskDetail1.folderInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF8"));
          bomDiskDetail1.bookletStatusIndicator = ((FormCheckBox)form.getElement("PID1")).isChecked();
          try {
            bomDiskDetail1.bookletSupplierId = Integer.parseInt(form.getStringValue("SID1"));
          } catch (Exception exception) {}
          bomDiskDetail1.bookletInk1 = form.getStringValue("INK11");
          bomDiskDetail1.bookletInk2 = form.getStringValue("INK21");
          bomDiskDetail1.bookletPages = MilestoneHelper_2.cleanText(form.getStringValue("SEL1"));
          bomDiskDetail1.bookletInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF1"));
          bomDiskDetail1.brcStatusIndicator = ((FormCheckBox)form.getElement("PID4")).isChecked();
          try {
            bomDiskDetail1.brcSupplierId = Integer.parseInt(form.getStringValue("SID4"));
          } catch (Exception exception) {}
          bomDiskDetail1.brcInk1 = form.getStringValue("INK14");
          bomDiskDetail1.brcInk2 = form.getStringValue("INK24");
          bomDiskDetail1.brcSize = MilestoneHelper_2.cleanText(form.getStringValue("SEL4"));
          bomDiskDetail1.brcInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF4"));
          bomDiskDetail1.miniStatusIndicator = ((FormCheckBox)form.getElement("PID15")).isChecked();
          try {
            bomDiskDetail1.miniSupplierId = Integer.parseInt(form.getStringValue("SID15"));
          } catch (Exception exception) {}
          bomDiskDetail1.miniInk1 = form.getStringValue("INK115");
          bomDiskDetail1.miniInk2 = form.getStringValue("INK215");
          bomDiskDetail1.miniInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF15"));
          bomDiskDetail1.digiPakStatusIndicator = ((FormCheckBox)form.getElement("PID6")).isChecked();
          try {
            bomDiskDetail1.digiPakSupplierId = Integer.parseInt(form.getStringValue("SID6"));
          } catch (Exception exception) {}
          bomDiskDetail1.digiPakInk1 = form.getStringValue("INK16");
          bomDiskDetail1.digiPakInk2 = form.getStringValue("INK26");
          bomDiskDetail1.digiPakTray = MilestoneHelper_2.cleanText(form.getStringValue("SEL6"));
          bomDiskDetail1.digiPakInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF6"));
          bomDiskDetail1.softPakStatusIndicator = ((FormCheckBox)form.getElement("PID31")).isChecked();
          try {
            bomDiskDetail1.softPakSupplierId = Integer.parseInt(form.getStringValue("SID31"));
          } catch (Exception exception) {}
          bomDiskDetail1.softPakInk1 = form.getStringValue("INK131");
          bomDiskDetail1.softPakInk2 = form.getStringValue("INK231");
          bomDiskDetail1.softPakTray = MilestoneHelper_2.cleanText(form.getStringValue("SEL31"));
          bomDiskDetail1.softPakInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF31"));
          bomDiskDetail1.stickerOneStatusIndicator = ((FormCheckBox)form.getElement("PID21")).isChecked();
          try {
            bomDiskDetail1.stickerOneSupplierId = Integer.parseInt(form.getStringValue("SID21"));
          } catch (Exception exception) {}
          bomDiskDetail1.stickerOneInk1 = form.getStringValue("INK121");
          bomDiskDetail1.stickerOneInk2 = form.getStringValue("INK221");
          bomDiskDetail1.stickerOnePlaces = MilestoneHelper_2.cleanText(form.getStringValue("SEL21"));
          bomDiskDetail1.stickerOneInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF21"));
          bomDiskDetail1.stickerTwoStatusIndicator = ((FormCheckBox)form.getElement("PID22")).isChecked();
          try {
            bomDiskDetail1.stickerTwoSupplierId = Integer.parseInt(form.getStringValue("SID22"));
          } catch (Exception exception) {}
          bomDiskDetail1.stickerTwoInk1 = form.getStringValue("INK122");
          bomDiskDetail1.stickerTwoInk2 = form.getStringValue("INK222");
          bomDiskDetail1.stickerTwoPlaces = MilestoneHelper_2.cleanText(form.getStringValue("SEL22"));
          bomDiskDetail1.stickerTwoInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF22"));
          bomDiskDetail1.bookStatusIndicator = ((FormCheckBox)form.getElement("PID2")).isChecked();
          try {
            bomDiskDetail1.bookSupplierId = Integer.parseInt(form.getStringValue("SID2"));
          } catch (Exception exception) {}
          bomDiskDetail1.bookInk1 = form.getStringValue("INK12");
          bomDiskDetail1.bookInk2 = form.getStringValue("INK22");
          bomDiskDetail1.bookPages = MilestoneHelper_2.cleanText(form.getStringValue("SEL2"));
          bomDiskDetail1.bookInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF2"));
          bomDiskDetail1.boxStatusIndicator = ((FormCheckBox)form.getElement("PID3")).isChecked();
          try {
            bomDiskDetail1.boxSupplierId = Integer.parseInt(form.getStringValue("SID3"));
          } catch (Exception exception) {}
          bomDiskDetail1.boxInk1 = form.getStringValue("INK13");
          bomDiskDetail1.boxInk2 = form.getStringValue("INK23");
          bomDiskDetail1.boxSizes = MilestoneHelper_2.cleanText(form.getStringValue("SEL3"));
          bomDiskDetail1.boxInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF3"));
          bomDiskDetail1.otherStatusIndicator = ((FormCheckBox)form.getElement("PID18")).isChecked();
          try {
            bomDiskDetail1.otherSupplierId = Integer.parseInt(form.getStringValue("SID18"));
          } catch (Exception exception) {}
          bomDiskDetail1.otherInk1 = form.getStringValue("INK118");
          bomDiskDetail1.otherInk2 = form.getStringValue("INK218");
          bomDiskDetail1.otherInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF18"));
          if (configuration.equalsIgnoreCase("DVV") && !isCDoverride) {
            bom.setBomDVDDetail((BomDVDDetail)bomDiskDetail1);
          } else {
            bom.setBomDiskDetail(bomDiskDetail1);
          } 
        } 
        Form emailDistForm = form;
        FormValidation formValidation = form.validate();
        if (formValidation.isGood()) {
          Bom saveBom = SelectionManager.getInstance().saveBom(bom, selection, user.getUserId());
          context.putSessionValue("Bom", saveBom);
          form.addElement(new FormHidden("copyPaste", "copy", true));
          form.addElement(new FormHidden("cmd", "bom-edit-save", true));
          context.putDelivery("Form", form);
          if (finalFlag) {
            String lastUpdatedDate = "";
            String lastUpdatedBy = "";
            if (saveBom.getModifiedOn() != null)
              lastUpdatedDate = MilestoneHelper.getCustomFormatedDate(saveBom.getModifiedOn(), "M/d/yyyy hh:mm:ss a 'ET'"); 
            if (UserManager.getInstance().getUser(saveBom.getModifiedBy()) != null)
              lastUpdatedBy = UserManager.getInstance().getUser(saveBom.getModifiedBy()).getName(); 
            if (sendEmail && 
              !context.getCommand().equals("selection-send-cancel") && 
              EmailDistribution.putEmailBody(emailDistForm, context, selection, lastUpdatedDate, lastUpdatedBy, "BOM", messageObject)) {
              try {
                messageObject.bomObj = (Bom)saveBom.clone();
              } catch (CloneNotSupportedException ce) {
                messageObject.bomObj = saveBom;
              } 
              print(dispatcher, context, command, 2, true, messageObject);
              EmailDistribution.sendEmail(dispatcher, context, "", messageObject);
              EmailDistribution.removeSessionValues(context);
            } 
          } 
          if (!command.equalsIgnoreCase("selectionSave"))
            return edit(dispatcher, context, command); 
          return true;
        } 
        context.putDelivery("FormValidation", formValidation);
      } 
      form.addElement(new FormHidden("OrderBy", "", true));
      form.addElement(new FormHidden("cmd", "bom-edit-save", true));
      context.putDelivery("Form", form);
      if (!command.equalsIgnoreCase("selectionSave"))
        return edit(dispatcher, context, command); 
      return true;
    } 
    context.putDelivery("AlertMessage", "The record is unavailable for editing at this time.  Please refresh your screen and edit it at a later time.");
    form.addElement(new FormHidden("cmd", "bom-edit-save", true));
    context.putDelivery("Form", form);
    if (!command.equalsIgnoreCase("selectionSave"))
      return edit(dispatcher, context, command); 
    return true;
  }
  
  protected Form buildDisk(Form bomDisk, Selection selection, Bom bom, BomDiskDetail bomDiskDetail) {
    Vector sel12;
    boolean isCDoverride = false;
    if (bom.getFormat().equalsIgnoreCase("CDO"))
      isCDoverride = true; 
    String typeFlag = "0";
    int ctrlSize = 50;
    if (selection.getSelectionConfig().getSelectionConfigurationAbbreviation().equalsIgnoreCase("DVV") && 
      !isCDoverride) {
      typeFlag = "1,2";
      ctrlSize = 20;
    } else {
      typeFlag = "0,1";
      ctrlSize = 50;
    } 
    FormCheckBox PID7 = new FormCheckBox("PID7", "on", "", false, bomDiskDetail.discStatusIndicator);
    PID7.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID7.setTabIndex(14);
    PID7.setId("Disc");
    bomDisk.addElement(PID7);
    FormTextField INK17 = new FormTextField("INK17", bomDiskDetail.getDiscInk1(), false, 2, 2);
    INK17.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK17.setTabIndex(15);
    INK17.setId("Disc / Ink");
    bomDisk.addElement(INK17);
    FormTextField INK27 = new FormTextField("INK27", bomDiskDetail.getDiscInk2(), false, 2, 2);
    INK27.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK27.setTabIndex(16);
    INK27.setId("Disc / Ink");
    bomDisk.addElement(INK27);
    FormTextField INF7 = new FormTextField("INF7", bomDiskDetail.getDiscInfo(), false, ctrlSize, 120);
    INF7.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF7.setTabIndex(17);
    INF7.setId("Disc / Additional Information");
    bomDisk.addElement(INF7);
    FormCheckBox PID12 = new FormCheckBox("PID12", "on", "", false, bomDiskDetail.jewelStatusIndicator);
    PID12.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID12.setId("PID12");
    PID12.setTabIndex(18);
    PID12.setId("Jewel Box");
    bomDisk.addElement(PID12);
    if (selection.getSelectionConfig().getSelectionConfigurationAbbreviation().equalsIgnoreCase("DVV") && 
      !isCDoverride) {
      sel12 = Cache.getInstance().getLookupDetailValuesFromDatabase(63);
    } else {
      sel12 = Cache.getInstance().getLookupDetailValuesFromDatabase(41);
    } 
    FormDropDownMenu SEL12 = MilestoneHelper.getLookupDropDownBom("SEL12", sel12, bomDiskDetail.getJewelColor(), false, true);
    SEL12.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SEL12.setTabIndex(19);
    SEL12.setId("Jewel Box / Additional Information");
    bomDisk.addElement(SEL12);
    FormTextField INF12 = new FormTextField("INF12", bomDiskDetail.getJewelInfo(), false, 20, 120);
    INF12.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF12.setTabIndex(20);
    INF12.setId("Jewel Box / Additional Information");
    bomDisk.addElement(INF12);
    FormCheckBox PID23 = new FormCheckBox("PID23", "on", "", false, bomDiskDetail.trayStatusIndicator);
    PID23.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID23.setId("PID23");
    PID23.setTabIndex(30);
    PID23.setId("Tray");
    bomDisk.addElement(PID23);
    Vector sel23 = Cache.getInstance().getLookupDetailValuesFromDatabase(42);
    FormDropDownMenu SEL23 = MilestoneHelper.getLookupDropDownBom("SEL23", sel23, bomDiskDetail.getTrayColor(), false, true);
    SEL23.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SEL23.setTabIndex(31);
    SEL23.setId("Tray / Additional Information");
    bomDisk.addElement(SEL23);
    FormTextField INF23 = new FormTextField("INF23", bomDiskDetail.getTrayInfo(), false, 20, 120);
    INF23.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF23.setTabIndex(32);
    INF23.setId("Tray / Additional Information");
    bomDisk.addElement(INF23);
    FormCheckBox PID10 = new FormCheckBox("PID10", "on", "", false, bomDiskDetail.inlayStatusIndicator);
    PID10.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID10.setId("PID10");
    PID10.setTabIndex(33);
    PID10.setId("Inlay");
    bomDisk.addElement(PID10);
    Vector sid10 = MilestoneHelper.getBomSuppliers(10, typeFlag, bomDiskDetail.inlaySupplierId);
    FormDropDownMenu SID10 = MilestoneHelper.getLookupDropDown("SID10", sid10, String.valueOf(bomDiskDetail.inlaySupplierId), false, false);
    SID10.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SID10.setTabIndex(34);
    SID10.setId("Inlay / Supplier");
    bomDisk.addElement(SID10);
    Vector sid7 = MilestoneHelper.getBomSuppliers(7, typeFlag);
    FormDropDownMenu SID7 = MilestoneHelper.getLookupDropDown("SID7", sid7, String.valueOf(bomDiskDetail.diskSupplierId), false, false);
    SID7.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SID7.setTabIndex(34);
    SID7.setId("Disc");
    bomDisk.addElement(SID7);
    Vector sid12 = MilestoneHelper.getBomSuppliers(12, typeFlag);
    FormDropDownMenu SID12 = MilestoneHelper.getLookupDropDown("SID12", sid12, String.valueOf(bomDiskDetail.jewelStatusIndicator), false, false);
    SID12.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SID12.setTabIndex(34);
    SID12.setId("Jewel Box");
    bomDisk.addElement(SID12);
    Vector sid23 = MilestoneHelper.getBomSuppliers(23, typeFlag);
    FormDropDownMenu SID23 = MilestoneHelper.getLookupDropDown("SID23", sid23, String.valueOf(bomDiskDetail.trayStatusIndicator), false, false);
    SID23.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SID23.setTabIndex(34);
    SID23.setId("Tray");
    bomDisk.addElement(SID23);
    FormTextField INK110 = new FormTextField("INK110", bomDiskDetail.getInlayInk1(), false, 2, 2);
    INK110.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK110.setTabIndex(35);
    INK110.setId("Inlay / Ink");
    bomDisk.addElement(INK110);
    FormTextField INK210 = new FormTextField("INK210", bomDiskDetail.getInlayInk2(), false, 2, 2);
    INK210.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK210.setTabIndex(36);
    INK210.setId("Inlay / Ink");
    bomDisk.addElement(INK210);
    FormTextField INF10 = new FormTextField("INF10", bomDiskDetail.getInlayInfo(), false, 50, 120);
    INF10.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF10.setTabIndex(37);
    INF10.setId("Inlay / Additional Information");
    bomDisk.addElement(INF10);
    FormCheckBox PID9 = new FormCheckBox("PID9", "on", "", false, bomDiskDetail.frontStatusIndicator);
    PID9.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID9.setId("PID9");
    PID9.setTabIndex(38);
    PID9.setId("Front Insert");
    bomDisk.addElement(PID9);
    Vector sid9 = MilestoneHelper.getBomSuppliers(9, typeFlag, bomDiskDetail.frontSupplierId);
    FormDropDownMenu SID9 = MilestoneHelper.getLookupDropDown("SID9", sid9, String.valueOf(bomDiskDetail.frontSupplierId), false, false);
    SID9.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SID9.setTabIndex(39);
    SID9.setId("Front Insert / Supplier");
    bomDisk.addElement(SID9);
    FormTextField INK19 = new FormTextField("INK19", bomDiskDetail.getFrontInk1(), false, 2, 2);
    INK19.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK19.setTabIndex(40);
    INK19.setId("Front Insert / Ink");
    bomDisk.addElement(INK19);
    FormTextField INK29 = new FormTextField("INK29", bomDiskDetail.getFrontInk2(), false, 2, 2);
    INK29.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK29.setTabIndex(41);
    INK29.setId("Front Insert / Ink");
    bomDisk.addElement(INK29);
    FormTextField INF9 = new FormTextField("INF9", bomDiskDetail.getFrontInfo(), false, 50, 120);
    INF9.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF9.setTabIndex(42);
    INF9.setId("Front Insert / Additional Information");
    bomDisk.addElement(INF9);
    FormCheckBox PID8 = new FormCheckBox("PID8", "on", "", false, bomDiskDetail.folderStatusIndicator);
    PID8.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID8.setId("PID8");
    PID8.setTabIndex(43);
    PID8.setId("Folder");
    bomDisk.addElement(PID8);
    Vector sid8 = MilestoneHelper.getBomSuppliers(8, typeFlag, bomDiskDetail.folderSupplierId);
    FormDropDownMenu SID8 = MilestoneHelper.getLookupDropDown("SID8", sid8, String.valueOf(bomDiskDetail.folderSupplierId), false, false);
    SID8.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SID8.setTabIndex(44);
    SID8.setId("Folder / Supplier");
    bomDisk.addElement(SID8);
    FormTextField INK18 = new FormTextField("INK18", bomDiskDetail.getFolderInk1(), false, 2, 2);
    INK18.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK18.setTabIndex(44);
    INK18.setId("Folder / Ink");
    bomDisk.addElement(INK18);
    FormTextField INK28 = new FormTextField("INK28", bomDiskDetail.getFolderInk2(), false, 2, 2);
    INK28.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK28.setTabIndex(45);
    INK28.setId("Folder / Ink");
    bomDisk.addElement(INK28);
    Vector sel8 = Cache.getInstance().getLookupDetailValuesFromDatabase(43);
    FormDropDownMenu SEL8 = MilestoneHelper.getLookupDropDownBom("SEL8", sel8, bomDiskDetail.getFolderPages(), false, true);
    SEL8.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SEL8.setTabIndex(46);
    SEL8.setId("Folder / Pages");
    bomDisk.addElement(SEL8);
    FormTextField INF8 = new FormTextField("INF8", bomDiskDetail.getFolderInfo(), false, 20, 120);
    INF8.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF8.setTabIndex(47);
    INF8.setId("Folder / Additinal Information");
    bomDisk.addElement(INF8);
    FormCheckBox PID1 = new FormCheckBox("PID1", "on", "", false, bomDiskDetail.bookletStatusIndicator);
    PID1.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID1.setId("PID1");
    PID1.setId("Booklet");
    PID1.setTabIndex(48);
    bomDisk.addElement(PID1);
    Vector sid1 = MilestoneHelper.getBomSuppliers(1, typeFlag, bomDiskDetail.bookletSupplierId);
    FormDropDownMenu SID1 = MilestoneHelper.getLookupDropDown("SID1", sid1, String.valueOf(bomDiskDetail.bookletSupplierId), false, false);
    SID1.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SID1.setTabIndex(49);
    SID1.setId("Booklet / Supplier");
    bomDisk.addElement(SID1);
    FormTextField INK11 = new FormTextField("INK11", bomDiskDetail.getBookletInk1(), false, 2, 2);
    INK11.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK11.setTabIndex(50);
    INK11.setId("Booklet / Ink");
    bomDisk.addElement(INK11);
    FormTextField INK21 = new FormTextField("INK21", bomDiskDetail.getBookletInk2(), false, 2, 2);
    INK21.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK21.setTabIndex(51);
    INK21.setId("Booklet / Ink");
    bomDisk.addElement(INK21);
    Vector sel1 = Cache.getInstance().getLookupDetailValuesFromDatabase(45);
    FormDropDownMenu SEL1 = MilestoneHelper.getLookupDropDownBom("SEL1", sel1, bomDiskDetail.getBookletPages(), false, true);
    SEL1.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SEL1.setTabIndex(52);
    SEL1.setId("Booklet / Pages");
    bomDisk.addElement(SEL1);
    FormTextField INF1 = new FormTextField("INF1", bomDiskDetail.getBookletInfo(), false, 20, 120);
    INF1.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF1.setTabIndex(53);
    INF1.setId("Booklet / Additional Information");
    bomDisk.addElement(INF1);
    FormCheckBox PID4 = new FormCheckBox("PID4", "on", "", false, bomDiskDetail.brcStatusIndicator);
    PID4.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID4.setId("PID4");
    PID4.setTabIndex(54);
    PID4.setId("BRC Insert");
    bomDisk.addElement(PID4);
    Vector sid4 = MilestoneHelper.getBomSuppliers(4, typeFlag, bomDiskDetail.brcSupplierId);
    FormDropDownMenu SID4 = MilestoneHelper.getLookupDropDown("SID4", sid4, String.valueOf(bomDiskDetail.brcSupplierId), false, false);
    SID4.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SID4.setTabIndex(55);
    SID4.setId("BRC Insert / Supplier");
    bomDisk.addElement(SID4);
    FormTextField INK14 = new FormTextField("INK14", bomDiskDetail.getBrcInk1(), false, 2, 2);
    INK14.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK14.setTabIndex(56);
    INK14.setId("BRC Insert / Ink");
    bomDisk.addElement(INK14);
    FormTextField INK24 = new FormTextField("INK24", bomDiskDetail.getBrcInk2(), false, 2, 2);
    INK24.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK24.setTabIndex(57);
    INK24.setId("BRC Insert / Ink");
    bomDisk.addElement(INK24);
    FormTextField SEL4 = new FormTextField("SEL4", bomDiskDetail.getBrcSize(), false, 10, 20);
    SEL4.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SEL4.setTabIndex(58);
    SEL4.setId("BRC Insert / Size");
    bomDisk.addElement(SEL4);
    FormTextField INF4 = new FormTextField("INF4", bomDiskDetail.getBrcInfo(), false, 20, 120);
    INF4.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF4.setTabIndex(59);
    INF4.setId("BRC Insert / Additional Information");
    bomDisk.addElement(INF4);
    FormCheckBox PID15 = new FormCheckBox("PID15", "on", "", false, bomDiskDetail.miniStatusIndicator);
    PID15.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID15.setId("PID15");
    PID15.setTabIndex(60);
    PID15.setId("Mini Jacket");
    bomDisk.addElement(PID15);
    Vector sid15 = MilestoneHelper.getBomSuppliers(15, typeFlag, bomDiskDetail.miniSupplierId);
    FormDropDownMenu SID15 = MilestoneHelper.getLookupDropDown("SID15", sid15, String.valueOf(bomDiskDetail.miniSupplierId), false, false);
    SID15.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SID15.setTabIndex(61);
    SID15.setId("Mini Jacket / Supplier");
    bomDisk.addElement(SID15);
    FormTextField INK115 = new FormTextField("INK115", bomDiskDetail.getMiniInk1(), false, 2, 2);
    INK115.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK115.setTabIndex(62);
    INK115.setId("Mini Jacket / Ink");
    bomDisk.addElement(INK115);
    FormTextField INK215 = new FormTextField("INK215", bomDiskDetail.getMiniInk2(), false, 2, 2);
    INK215.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK215.setTabIndex(63);
    INK215.setId("Mini Jacket / Ink");
    bomDisk.addElement(INK215);
    FormTextField INF15 = new FormTextField("INF15", bomDiskDetail.getMiniInfo(), false, 20, 120);
    INF15.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF15.setTabIndex(64);
    INF15.setId("Mini Jacket / Additional Information");
    bomDisk.addElement(INF15);
    FormCheckBox PID6 = new FormCheckBox("PID6", "on", "", false, bomDiskDetail.digiPakStatusIndicator);
    PID6.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID6.setId("PID6");
    PID6.setTabIndex(65);
    PID6.setId("Digipak");
    bomDisk.addElement(PID6);
    Vector sid6 = MilestoneHelper.getBomSuppliers(6, typeFlag, bomDiskDetail.digiPakSupplierId);
    FormDropDownMenu SID6 = MilestoneHelper.getLookupDropDown("SID6", sid6, String.valueOf(bomDiskDetail.digiPakSupplierId), false, false);
    SID6.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SID6.setTabIndex(66);
    SID6.setId("Digipak / Supplier");
    bomDisk.addElement(SID6);
    FormTextField INK16 = new FormTextField("INK16", bomDiskDetail.getDigiPakInk1(), false, 2, 2);
    INK16.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK16.setTabIndex(67);
    INK16.setId("Digipak / Ink");
    bomDisk.addElement(INK16);
    FormTextField INK26 = new FormTextField("INK26", bomDiskDetail.getDigiPakInk2(), false, 2, 2);
    INK26.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK26.setTabIndex(68);
    INK26.setId("Digipak / Ink");
    bomDisk.addElement(INK26);
    FormTextField SEL6 = new FormTextField("SEL6", bomDiskDetail.getDigiPakTray(), false, 6, 6);
    SEL6.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SEL6.setTabIndex(69);
    SEL6.setId("Digipak / Tray");
    bomDisk.addElement(SEL6);
    FormTextField INF6 = new FormTextField("INF6", bomDiskDetail.getDigiPakInfo(), false, 20, 120);
    INF6.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF6.setTabIndex(70);
    INF6.setId("Digipak / Additional Information");
    bomDisk.addElement(INF6);
    FormCheckBox PID31 = new FormCheckBox("PID31", "on", "", false, bomDiskDetail.softPakStatusIndicator);
    PID6.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID6.setId("PID31");
    PID6.setTabIndex(71);
    PID6.setId("Softpak");
    bomDisk.addElement(PID31);
    Vector sid31 = MilestoneHelper.getBomSuppliers(31, typeFlag, bomDiskDetail.softPakSupplierId);
    FormDropDownMenu SID31 = MilestoneHelper.getLookupDropDown("SID31", sid31, String.valueOf(bomDiskDetail.softPakSupplierId), false, false);
    SID31.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SID31.setTabIndex(72);
    SID31.setId("Softpak / Supplier");
    bomDisk.addElement(SID31);
    FormTextField INK131 = new FormTextField("INK131", bomDiskDetail.getSoftPakInk1(), false, 2, 2);
    INK131.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK131.setTabIndex(73);
    INK131.setId("Softpak / Ink");
    bomDisk.addElement(INK131);
    FormTextField INK231 = new FormTextField("INK231", bomDiskDetail.getSoftPakInk2(), false, 2, 2);
    INK231.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK231.setTabIndex(74);
    INK231.setId("Softpak / Ink");
    bomDisk.addElement(INK231);
    FormTextField SEL31 = new FormTextField("SEL31", bomDiskDetail.getSoftPakTray(), false, 6, 6);
    SEL31.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SEL31.setTabIndex(75);
    SEL31.setId("Softpak / Tray");
    bomDisk.addElement(SEL31);
    FormTextField INF31 = new FormTextField("INF31", bomDiskDetail.getSoftPakInfo(), false, 20, 120);
    INF31.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF31.setTabIndex(76);
    INF31.setId("Softpak / Additional Information");
    bomDisk.addElement(INF31);
    FormCheckBox PID21 = new FormCheckBox("PID21", "on", "", false, bomDiskDetail.stickerOneStatusIndicator);
    PID21.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID21.setId("PID21");
    PID21.setTabIndex(78);
    PID21.setId("Sticker1");
    bomDisk.addElement(PID21);
    Vector sid21 = MilestoneHelper.getBomSuppliers(21, typeFlag, bomDiskDetail.stickerOneSupplierId);
    FormDropDownMenu SID21 = MilestoneHelper.getLookupDropDown("SID21", sid21, String.valueOf(bomDiskDetail.stickerOneSupplierId), false, false);
    SID21.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SID21.setTabIndex(79);
    SID21.setId("Sticker1 / Supplier");
    bomDisk.addElement(SID21);
    FormTextField INK121 = new FormTextField("INK121", bomDiskDetail.getStickerOneInk1(), false, 2, 2);
    INK121.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK121.setTabIndex(80);
    INK121.setId("Sticker1 / Ink");
    bomDisk.addElement(INK121);
    FormTextField INK221 = new FormTextField("INK221", bomDiskDetail.getStickerOneInk2(), false, 2, 2);
    INK221.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK221.setTabIndex(81);
    INK221.setId("Sticker1 / Ink");
    bomDisk.addElement(INK221);
    FormTextField SEL21 = new FormTextField("SEL21", bomDiskDetail.getStickerOnePlaces(), false, 6, 6);
    SEL21.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SEL21.setTabIndex(82);
    SEL21.setId("Sticker1 / Places");
    bomDisk.addElement(SEL21);
    FormTextField INF21 = new FormTextField("INF21", bomDiskDetail.getStickerOneInfo(), false, 20, 120);
    INF21.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF21.setTabIndex(83);
    INF21.setId("Sticker1 / Additional Information");
    bomDisk.addElement(INF21);
    FormCheckBox PID22 = new FormCheckBox("PID22", "on", "", false, bomDiskDetail.stickerTwoStatusIndicator);
    PID22.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID22.setId("PID22");
    PID22.setTabIndex(84);
    PID22.setId("Sticker2");
    bomDisk.addElement(PID22);
    Vector sid22 = MilestoneHelper.getBomSuppliers(22, typeFlag, bomDiskDetail.stickerTwoSupplierId);
    FormDropDownMenu SID22 = MilestoneHelper.getLookupDropDown("SID22", sid22, String.valueOf(bomDiskDetail.stickerTwoSupplierId), false, false);
    SID22.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SID22.setTabIndex(85);
    SID22.setId("Sticker2 / Supplier");
    bomDisk.addElement(SID22);
    FormTextField INK122 = new FormTextField("INK122", bomDiskDetail.getStickerTwoInk1(), false, 2, 2);
    INK122.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK122.setTabIndex(86);
    INK122.setId("Sticker2 / Ink");
    bomDisk.addElement(INK122);
    FormTextField INK222 = new FormTextField("INK222", bomDiskDetail.getStickerTwoInk2(), false, 2, 2);
    INK222.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK222.setTabIndex(87);
    INK222.setId("Sticker2 / Ink");
    bomDisk.addElement(INK222);
    FormTextField SEL22 = new FormTextField("SEL22", bomDiskDetail.getStickerTwoPlaces(), false, 6, 6);
    SEL22.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SEL22.setTabIndex(88);
    SEL22.setId("Sticker2 / Places");
    bomDisk.addElement(SEL22);
    FormTextField INF22 = new FormTextField("INF22", bomDiskDetail.getStickerTwoInfo(), false, 20, 120);
    INF22.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF22.setTabIndex(89);
    INF22.setId("Sticker2 / Additional Information");
    bomDisk.addElement(INF22);
    FormCheckBox PID2 = new FormCheckBox("PID2", "on", "", false, bomDiskDetail.bookStatusIndicator);
    PID2.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID2.setId("PID2");
    PID2.setTabIndex(90);
    PID2.setId("Book (Other/Set)");
    bomDisk.addElement(PID2);
    Vector sid2 = MilestoneHelper.getBomSuppliers(2, typeFlag, bomDiskDetail.bookSupplierId);
    FormDropDownMenu SID2 = MilestoneHelper.getLookupDropDown("SID2", sid2, String.valueOf(bomDiskDetail.bookSupplierId), false, false);
    SID2.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SID2.setTabIndex(91);
    SID2.setId("Book (Other/Set) / Supplier");
    bomDisk.addElement(SID2);
    FormTextField INK12 = new FormTextField("INK12", bomDiskDetail.getBookInk1(), false, 2, 2);
    INK12.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK12.setTabIndex(92);
    INK12.setId("Book (Other/Set) / Ink");
    bomDisk.addElement(INK12);
    FormTextField INK22 = new FormTextField("INK22", bomDiskDetail.getBookInk2(), false, 2, 2);
    INK22.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK22.setTabIndex(93);
    INK22.setId("Book (Other/Set) / Ink");
    bomDisk.addElement(INK22);
    FormTextField SEL2 = new FormTextField("SEL2", bomDiskDetail.getBookPages(), false, 5, 5);
    SEL2.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SEL2.setTabIndex(94);
    SEL2.setId("Book (Other/Set) / Pages");
    bomDisk.addElement(SEL2);
    FormTextField INF2 = new FormTextField("INF2", bomDiskDetail.getBookInfo(), false, 20, 120);
    INF2.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF2.setTabIndex(95);
    INF2.setId("Book (Other/Set) / Additional Information");
    bomDisk.addElement(INF2);
    FormCheckBox PID3 = new FormCheckBox("PID3", "on", "", false, bomDiskDetail.boxStatusIndicator);
    PID3.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID3.setId("PID3");
    PID3.setTabIndex(96);
    PID3.setId("Box (Set)");
    bomDisk.addElement(PID3);
    Vector sid3 = MilestoneHelper.getBomSuppliers(3, typeFlag, bomDiskDetail.boxSupplierId);
    FormDropDownMenu SID3 = MilestoneHelper.getLookupDropDown("SID3", sid3, String.valueOf(bomDiskDetail.boxSupplierId), false, false);
    SID3.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SID3.setTabIndex(97);
    SID3.setId("Box (Set) / Supplier");
    bomDisk.addElement(SID3);
    FormTextField INK13 = new FormTextField("INK13", bomDiskDetail.getBoxInk1(), false, 2, 2);
    INK13.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK13.setTabIndex(98);
    INK13.setId("Box (Set) / Ink");
    bomDisk.addElement(INK13);
    FormTextField INK23 = new FormTextField("INK23", bomDiskDetail.getBoxInk2(), false, 2, 2);
    INK23.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK23.setTabIndex(99);
    INK23.setId("Box (Set) / Ink");
    bomDisk.addElement(INK23);
    FormTextField SEL3 = new FormTextField("SEL3", bomDiskDetail.getBoxSizes(), false, 10, 20);
    SEL3.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SEL3.setTabIndex(100);
    SEL3.setId("Box (Set) / Size");
    bomDisk.addElement(SEL3);
    FormTextField INF3 = new FormTextField("INF3", bomDiskDetail.getBoxInfo(), false, 20, 120);
    INF3.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF3.setTabIndex(101);
    INF3.setId("Box (Set) / Additional Information");
    bomDisk.addElement(INF3);
    FormCheckBox PID18 = new FormCheckBox("PID18", "on", "", false, bomDiskDetail.otherStatusIndicator);
    PID18.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID18.setId("PID18");
    PID18.setTabIndex(102);
    PID18.setId("Other");
    bomDisk.addElement(PID18);
    Vector sid18 = MilestoneHelper.getBomSuppliers(18, typeFlag, bomDiskDetail.otherSupplierId);
    FormDropDownMenu SID18 = MilestoneHelper.getLookupDropDown("SID18", sid18, String.valueOf(bomDiskDetail.otherSupplierId), false, false);
    SID18.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SID18.setTabIndex(103);
    SID18.setId("Other / Supplier");
    bomDisk.addElement(SID18);
    FormTextField INK118 = new FormTextField("INK118", bomDiskDetail.getOtherInk1(), false, 2, 2);
    INK118.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK118.setTabIndex(104);
    INK118.setId("Other / Ink");
    bomDisk.addElement(INK118);
    FormTextField INK218 = new FormTextField("INK218", bomDiskDetail.getOtherInk2(), false, 2, 2);
    INK218.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK218.setTabIndex(105);
    INK218.setId("Other / Ink");
    bomDisk.addElement(INK218);
    FormTextField INF18 = new FormTextField("INF18", bomDiskDetail.getOtherInfo(), false, 20, 120);
    INF18.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF18.setTabIndex(106);
    INF18.setId("Other / Additional Information");
    bomDisk.addElement(INF18);
    return bomDisk;
  }
  
  protected Form buildDVD(Form bomDVD, Selection selection, Bom bom) {
    BomDVDDetail bomDVDDetail = bom.getBomDVDDetail();
    Vector selDisc = Cache.getInstance().getLookupDetailValuesFromDatabase(62);
    FormDropDownMenu SELDISC = MilestoneHelper.getLookupDropDownBom("SELDISC", selDisc, bomDVDDetail.getDiscSelectionInfo(), false, true);
    SELDISC.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SELDISC.setTabIndex(17);
    SELDISC.setId("Disc / Additional Information");
    bomDVD.addElement(SELDISC);
    FormCheckBox PID25 = new FormCheckBox("PID25", "on", "", false, bomDVDDetail.wrapStatusIndicator);
    PID25.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID25.setId("PID25");
    PID25.setTabIndex(21);
    PID25.setId("Wrap");
    bomDVD.addElement(PID25);
    Vector sid25 = MilestoneHelper.getBomSuppliers(29, "1,2", bomDVDDetail.wrapSupplierId);
    FormDropDownMenu SID25 = MilestoneHelper.getLookupDropDown("SID25", sid25, String.valueOf(bomDVDDetail.wrapSupplierId), false, false);
    SID25.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SID25.setTabIndex(22);
    SID25.setId("Wrap / Supplier");
    bomDVD.addElement(SID25);
    FormTextField INK125 = new FormTextField("INK125", bomDVDDetail.getWrapInk1(), false, 2, 2);
    INK125.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK125.setTabIndex(23);
    INK125.setId("Wrap / Ink");
    bomDVD.addElement(INK125);
    FormTextField INK225 = new FormTextField("INK225", bomDVDDetail.getWrapInk2(), false, 2, 2);
    INK225.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK225.setTabIndex(24);
    INK225.setId("Wrap / Ink");
    bomDVD.addElement(INK225);
    FormTextField INF25 = new FormTextField("INF25", bomDVDDetail.getWrapInfo(), false, 50, 120);
    INF25.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF25.setTabIndex(25);
    INF25.setId("Wrap / Additional Information");
    bomDVD.addElement(INF25);
    FormCheckBox PID26 = new FormCheckBox("PID26", "on", "", false, bomDVDDetail.dvdStatusIndicator);
    PID26.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID26.setTabIndex(26);
    PID26.setId("DVD Case");
    bomDVD.addElement(PID26);
    FormTextField INK126 = new FormTextField("INK126", bomDVDDetail.getDVDInk1(), false, 2, 2);
    INK126.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK126.setTabIndex(27);
    INK126.setId("DVD Case / Ink");
    bomDVD.addElement(INK126);
    FormTextField INK226 = new FormTextField("INK226", bomDVDDetail.getDVDInk2(), false, 2, 2);
    INK226.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK226.setTabIndex(28);
    INK226.setId("DVD Case / Ink");
    bomDVD.addElement(INK226);
    Vector sel26 = Cache.getInstance().getLookupDetailValuesFromDatabase(64);
    FormDropDownMenu SEL26 = MilestoneHelper.getLookupDropDownBom("SEL26", sel26, bomDVDDetail.getDVDSelectionInfo(), false, true);
    SEL26.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SEL26.setTabIndex(29);
    SEL26.setId("DVD Case / Additional Information");
    bomDVD.addElement(SEL26);
    FormTextField INF26 = new FormTextField("INF26", bomDVDDetail.getDVDInfo(), false, 20, 120);
    INF26.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF26.setTabIndex(30);
    INF26.setId("DVD Case / Additional Information");
    bomDVD.addElement(INF26);
    FormCheckBox PID32 = new FormCheckBox("PID32", "on", "", false, bomDVDDetail.bluRayStatusIndicator);
    PID32.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID32.setTabIndex(31);
    PID32.setId("Blu-Ray Case");
    bomDVD.addElement(PID32);
    FormTextField INK132 = new FormTextField("INK132", bomDVDDetail.getBluRayInk1(), false, 2, 2);
    INK132.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK132.setTabIndex(32);
    INK132.setId("Blu-Ray Case / Ink");
    bomDVD.addElement(INK132);
    FormTextField INK232 = new FormTextField("INK232", bomDVDDetail.getBluRayInk2(), false, 2, 2);
    INK232.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK232.setTabIndex(33);
    INK232.setId("Blu-Ray Case / Ink");
    bomDVD.addElement(INK232);
    Vector sel32 = Cache.getInstance().getLookupDetailValuesFromDatabase(66);
    FormDropDownMenu SEL32 = MilestoneHelper.getLookupDropDownBom("SEL32", sel32, bomDVDDetail.getBluRaySelectionInfo(), false, true);
    SEL32.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SEL32.setTabIndex(34);
    SEL32.setId("Blu-Ray Case / Additional Information");
    bomDVD.addElement(SEL32);
    FormTextField INF32 = new FormTextField("INF32", bomDVDDetail.getBluRayInfo(), false, 20, 120);
    INF32.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF32.setTabIndex(35);
    INF32.setId("Blu-Ray Case / Additional Information");
    bomDVD.addElement(INF32);
    return buildDisk(bomDVD, selection, bom, bom.getBomDVDDetail());
  }
  
  protected Form buildCassette(Form bomCassette, Selection selection, Bom bom) {
    BomCassetteDetail bomCassetteDetail = bom.getBomCassetteDetail();
    FormCheckBox PID5 = new FormCheckBox("PID5", "on", "", false, bomCassetteDetail.coStatusIndicator);
    PID5.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID5.setId("C-0");
    PID5.setTabIndex(14);
    bomCassette.addElement(PID5);
    FormTextField INK15 = new FormTextField("INK15", bomCassetteDetail.getCoInk1(), false, 2, 2);
    INK15.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK15.setTabIndex(15);
    INK15.setId("C-0 / Ink");
    bomCassette.addElement(INK15);
    FormTextField INK25 = new FormTextField("INK25", bomCassetteDetail.getCoInk2(), false, 2, 2);
    INK25.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK25.setTabIndex(16);
    INK25.setId("C-0 / Ink");
    bomCassette.addElement(INK25);
    Vector sel5 = Cache.getInstance().getLookupDetailValuesFromDatabase(46);
    FormDropDownMenu SEL5 = MilestoneHelper.getLookupDropDownBom("SEL5", sel5, bomCassetteDetail.getCoColor(), false, true);
    SEL5.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SEL5.setTabIndex(17);
    SEL5.setId("C-0 / Color");
    bomCassette.addElement(SEL5);
    FormTextField INF5 = new FormTextField("INF5", bomCassetteDetail.getCoInfo(), false, 20, 120);
    INF5.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF5.setTabIndex(18);
    INF5.setId("C-0 / Additional Information");
    bomCassette.addElement(INF5);
    FormCheckBox PID16 = new FormCheckBox("PID16", "on", "", false, bomCassetteDetail.norelcoStatusIndicator);
    PID16.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID16.setId("PID16");
    PID16.setTabIndex(19);
    PID16.setId("Norelco");
    bomCassette.addElement(PID16);
    Vector sel16 = Cache.getInstance().getLookupDetailValuesFromDatabase(46);
    FormDropDownMenu SEL16 = MilestoneHelper.getLookupDropDownBom("SEL16", sel16, bomCassetteDetail.getNorelcoColor(), false, true);
    SEL16.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SEL16.setTabIndex(20);
    SEL16.setId("Norelco / Color");
    bomCassette.addElement(SEL16);
    FormTextField INF16 = new FormTextField("INF16", bomCassetteDetail.getNorelcoInfo(), false, 20, 120);
    INF16.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF16.setTabIndex(21);
    INF16.setId("Norelco / Additional Information");
    bomCassette.addElement(INF16);
    FormCheckBox PID13 = new FormCheckBox("PID13", "on", "", false, bomCassetteDetail.jCardStatusIndicator);
    PID13.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID13.setId("PID13");
    PID13.setTabIndex(22);
    PID13.setId("J-Card");
    bomCassette.addElement(PID13);
    Vector sid13 = MilestoneHelper.getBomSuppliers(13);
    FormDropDownMenu SID13 = MilestoneHelper.getLookupDropDown("SID13", sid13, String.valueOf(bomCassetteDetail.jCardSupplierId), false, false);
    SID13.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SID13.setTabIndex(23);
    SID13.setId("J-Card / Supplier");
    bomCassette.addElement(SID13);
    Vector sid5 = MilestoneHelper.getBomSuppliers(5);
    FormDropDownMenu SID5 = MilestoneHelper.getLookupDropDown("SID5", sid5, String.valueOf(bomCassetteDetail.coParSupplierId), false, false);
    SID5.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SID5.setTabIndex(23);
    SID5.setId("C-0");
    bomCassette.addElement(SID5);
    Vector sid16 = MilestoneHelper.getBomSuppliers(16);
    FormDropDownMenu SID16 = MilestoneHelper.getLookupDropDown("SID16", sid16, String.valueOf(bomCassetteDetail.norelcoSupplierId), false, false);
    SID16.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SID16.setTabIndex(23);
    SID16.setId("");
    bomCassette.addElement(SID16);
    FormTextField INK113 = new FormTextField("INK113", bomCassetteDetail.getJCardInk1(), false, 2, 2);
    INK113.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK113.setTabIndex(24);
    INK113.setId("J-Card / Ink");
    bomCassette.addElement(INK113);
    FormTextField INK213 = new FormTextField("INK213", bomCassetteDetail.getJCardInk2(), false, 2, 2);
    INK213.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK213.setTabIndex(25);
    INK213.setId("J-Card / Ink");
    bomCassette.addElement(INK213);
    FormTextField SEL13 = new FormTextField("SEL13", bomCassetteDetail.getJCardPanels(), false, 6, 6);
    SEL13.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SEL13.setTabIndex(26);
    SEL13.setId("J-Card / Panels");
    bomCassette.addElement(SEL13);
    FormTextField INF13 = new FormTextField("INF13", bomCassetteDetail.getJCardInfo(), false, 20, 120);
    INF13.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF13.setTabIndex(27);
    INF13.setId("J-Card / Additional Information");
    bomCassette.addElement(INF13);
    FormCheckBox PID24 = new FormCheckBox("PID24", "on", "", false, bomCassetteDetail.uCardStatusIndicator);
    PID24.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID24.setId("PID24");
    PID24.setTabIndex(28);
    PID24.setId("U-Card");
    bomCassette.addElement(PID24);
    Vector sid24 = MilestoneHelper.getBomSuppliers(24);
    FormDropDownMenu SID24 = MilestoneHelper.getLookupDropDown("SID24", sid24, String.valueOf(bomCassetteDetail.uCardSupplierId), false, false);
    SID24.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SID24.setTabIndex(29);
    SID24.setId("U-Card / Supplier");
    bomCassette.addElement(SID24);
    FormTextField INK124 = new FormTextField("INK124", bomCassetteDetail.getUCardInk1(), false, 2, 2);
    INK124.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK124.setTabIndex(30);
    INK124.setId("U-Card / Ink");
    bomCassette.addElement(INK124);
    FormTextField INK224 = new FormTextField("INK224", bomCassetteDetail.getUCardInk2(), false, 2, 2);
    INK224.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK224.setTabIndex(31);
    INK224.setId("U-Card / Ink");
    bomCassette.addElement(INK224);
    FormTextField SEL24 = new FormTextField("SEL24", bomCassetteDetail.getUCardPanels(), false, 6, 6);
    SEL24.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SEL24.setTabIndex(32);
    SEL24.setId("U-Card / Panels");
    bomCassette.addElement(SEL24);
    FormTextField INF24 = new FormTextField("INF24", bomCassetteDetail.getUCardInfo(), false, 20, 120);
    INF24.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF24.setTabIndex(33);
    INF24.setId("U-Card / Additional Information");
    bomCassette.addElement(INF24);
    FormCheckBox PID17 = new FormCheckBox("PID17", "on", "", false, bomCassetteDetail.oCardStatusIndicator);
    PID17.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID17.setTabIndex(34);
    PID17.setId("PID17");
    PID17.setId("O-Card");
    bomCassette.addElement(PID17);
    Vector sid17 = MilestoneHelper.getBomSuppliers(17);
    FormDropDownMenu SID17 = MilestoneHelper.getLookupDropDown("SID17", sid17, String.valueOf(bomCassetteDetail.oCardSupplierId), false, false);
    SID17.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SID17.setTabIndex(35);
    SID17.setId("O-Card / Supplier");
    bomCassette.addElement(SID17);
    FormTextField INK117 = new FormTextField("INK117", bomCassetteDetail.getOCardInk1(), false, 2, 2);
    INK117.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK117.setTabIndex(36);
    INK117.setId("O-Card / Ink");
    bomCassette.addElement(INK117);
    FormTextField INK217 = new FormTextField("INK217", bomCassetteDetail.getOCardInk2(), false, 2, 2);
    INK217.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK217.setTabIndex(37);
    INK217.setId("O-Card / Ink");
    bomCassette.addElement(INK217);
    FormTextField INF17 = new FormTextField("INF17", bomCassetteDetail.getOCardInfo(), false, 20, 120);
    INF17.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF17.setTabIndex(38);
    INF17.setId("O-Card / Additional Information");
    bomCassette.addElement(INF17);
    FormCheckBox PID21 = new FormCheckBox("PID21", "on", "", false, bomCassetteDetail.stickerOneCardStatusIndicator);
    PID21.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID21.setId("PID21");
    PID21.setTabIndex(39);
    PID21.setId("Sticker1");
    bomCassette.addElement(PID21);
    Vector sid21 = MilestoneHelper.getBomSuppliers(21);
    FormDropDownMenu SID21 = MilestoneHelper.getLookupDropDown("SID21", sid21, String.valueOf(bomCassetteDetail.stickerOneCardSupplierId), false, false);
    SID21.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SID21.setTabIndex(40);
    SID21.setId("Sticker1 / Supplier");
    bomCassette.addElement(SID21);
    FormTextField INK121 = new FormTextField("INK121", bomCassetteDetail.getStickerOneCardInk1(), false, 2, 2);
    INK121.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK121.setTabIndex(41);
    INK121.setId("Sticker1 / Ink");
    bomCassette.addElement(INK121);
    FormTextField INK221 = new FormTextField("INK221", bomCassetteDetail.getStickerOneCardInk2(), false, 2, 2);
    INK221.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK221.setTabIndex(42);
    INK121.setId("Sticker1 / Ink");
    bomCassette.addElement(INK221);
    FormTextField SEL21 = new FormTextField("SEL21", bomCassetteDetail.getStickerOneCardPlaces(), false, 6, 6);
    SEL21.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SEL21.setTabIndex(43);
    SEL21.setId("Sticker1 / Places");
    bomCassette.addElement(SEL21);
    FormTextField INF21 = new FormTextField("INF21", bomCassetteDetail.getStickerOneCardInfo(), false, 20, 120);
    INF21.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF21.setTabIndex(44);
    INF21.setId("Sticker1 / Additional Information");
    bomCassette.addElement(INF21);
    FormCheckBox PID22 = new FormCheckBox("PID22", "on", "", false, bomCassetteDetail.stickerTwoCardStatusIndicator);
    PID22.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID22.setId("Sticker2");
    PID22.setTabIndex(45);
    bomCassette.addElement(PID22);
    Vector sid22 = MilestoneHelper.getBomSuppliers(22);
    FormDropDownMenu SID22 = MilestoneHelper.getLookupDropDown("SID22", sid22, String.valueOf(bomCassetteDetail.stickerTwoCardSupplierId), false, false);
    SID22.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SID22.setTabIndex(46);
    SID22.setId("Sticker2 / Supplier");
    bomCassette.addElement(SID22);
    FormTextField INK122 = new FormTextField("INK122", bomCassetteDetail.getStickerTwoCardInk1(), false, 2, 2);
    INK122.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK122.setTabIndex(47);
    INK122.setId("Sticker2 / Ink");
    bomCassette.addElement(INK122);
    FormTextField INK222 = new FormTextField("INK222", bomCassetteDetail.getStickerTwoCardInk2(), false, 2, 2);
    INK222.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK222.setTabIndex(48);
    INK222.setId("Sticker2 / Ink");
    bomCassette.addElement(INK222);
    FormTextField SEL22 = new FormTextField("SEL22", bomCassetteDetail.getStickerTwoCardPlaces(), false, 6, 6);
    SEL22.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SEL22.setTabIndex(49);
    SEL22.setId("Sticker2 / Places");
    bomCassette.addElement(SEL22);
    FormTextField INF22 = new FormTextField("INF22", bomCassetteDetail.getStickerTwoCardInfo(), false, 20, 120);
    INF22.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF22.setTabIndex(50);
    INF22.setId("Sticker2 / Additional Information");
    bomCassette.addElement(INF22);
    FormCheckBox PID18 = new FormCheckBox("PID18", "on", "", false, bomCassetteDetail.otherStatusIndicator);
    PID18.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID18.setId("PID18");
    PID18.setTabIndex(51);
    PID18.setId("Other");
    bomCassette.addElement(PID18);
    Vector sid18 = MilestoneHelper.getBomSuppliers(18);
    FormDropDownMenu SID18 = MilestoneHelper.getLookupDropDown("SID18", sid18, String.valueOf(bomCassetteDetail.otherSupplierId), false, false);
    SID18.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SID18.setTabIndex(52);
    SID18.setId("Other / Supplier");
    bomCassette.addElement(SID18);
    FormTextField INK118 = new FormTextField("INK118", bomCassetteDetail.getOtherInk1(), false, 2, 2);
    INK118.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK118.setTabIndex(53);
    INK118.setId("Other / Ink");
    bomCassette.addElement(INK118);
    FormTextField INK218 = new FormTextField("INK218", bomCassetteDetail.getOtherInk2(), false, 2, 2);
    INK218.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK218.setTabIndex(54);
    INK218.setId("Other / Ink");
    bomCassette.addElement(INK218);
    FormTextField INF18 = new FormTextField("INF18", bomCassetteDetail.getOtherInfo(), false, 50, 120);
    INF18.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF18.setTabIndex(55);
    INF18.setId("Other / Additional Information");
    bomCassette.addElement(INF18);
    return bomCassette;
  }
  
  protected Form buildVinyl(Form bomVinyl, Selection selection, Bom bom) {
    BomVinylDetail bomVinylDetail = bom.getBomVinylDetail();
    FormCheckBox PID19 = new FormCheckBox("PID19", "on", "", false, bomVinylDetail.recordStatusIndicator);
    PID19.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID19.setId("Record");
    PID19.setTabIndex(14);
    bomVinyl.addElement(PID19);
    Vector sid19 = MilestoneHelper.getBomSuppliers(19, bomVinylDetail.recordSupplierId);
    FormDropDownMenu SID19 = MilestoneHelper.getLookupDropDown("SID19", sid19, String.valueOf(bomVinylDetail.recordSupplierId), false, false);
    SID19.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SID19.setTabIndex(15);
    SID19.setId("Record / Supplier");
    bomVinyl.addElement(SID19);
    Vector sel19 = Cache.getInstance().getLookupDetailValuesFromDatabase(46);
    FormDropDownMenu SEL19 = MilestoneHelper.getLookupDropDownBom("SEL19", sel19, bomVinylDetail.recordColor, false, true);
    SEL19.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SEL19.setTabIndex(16);
    SEL19.setId("Record / Color");
    bomVinyl.addElement(SEL19);
    FormTextField INF19 = new FormTextField("INF19", bomVinylDetail.getRecordInfo(), false, 20, 120);
    INF19.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF19.setTabIndex(17);
    INF19.setId("Record / Additional Information");
    bomVinyl.addElement(INF19);
    FormCheckBox PID14 = new FormCheckBox("PID14", "on", "", false, bomVinylDetail.labelStatusIndicator);
    PID14.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID14.setId("PID14");
    PID14.setTabIndex(18);
    PID14.setId("Label");
    bomVinyl.addElement(PID14);
    Vector sid14 = MilestoneHelper.getBomSuppliers(14, bomVinylDetail.labelSupplierId);
    FormDropDownMenu SID14 = MilestoneHelper.getLookupDropDown("SID14", sid14, String.valueOf(bomVinylDetail.labelSupplierId), false, false);
    SID14.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SID14.setTabIndex(19);
    SID14.setId("Label / Supplier");
    bomVinyl.addElement(SID14);
    FormTextField INK114 = new FormTextField("INK114", bomVinylDetail.getLabelInk1(), false, 2, 2);
    INK114.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK114.setTabIndex(20);
    INK114.setId("Label / Ink");
    bomVinyl.addElement(INK114);
    FormTextField INK214 = new FormTextField("INK214", bomVinylDetail.getLabelInk2(), false, 2, 2);
    INK214.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK214.setTabIndex(21);
    INK214.setId("Label / Ink");
    bomVinyl.addElement(INK214);
    FormTextField INF14 = new FormTextField("INF14", bomVinylDetail.getLabelInfo(), false, 50, 120);
    INF14.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF14.setTabIndex(22);
    INF14.setId("Label / Additional Information");
    bomVinyl.addElement(INF14);
    FormCheckBox PID20 = new FormCheckBox("PID20", "on", "", false, bomVinylDetail.sleeveStatusIndicator);
    PID20.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID20.setId("PID20");
    PID20.setTabIndex(23);
    PID20.setId("Sleeve");
    bomVinyl.addElement(PID20);
    Vector sid20 = MilestoneHelper.getBomSuppliers(20, bomVinylDetail.sleeveSupplierId);
    FormDropDownMenu SID20 = MilestoneHelper.getLookupDropDown("SID20", sid20, String.valueOf(bomVinylDetail.sleeveSupplierId), false, false);
    SID20.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SID20.setTabIndex(24);
    SID20.setId("Sleeve / Supplier");
    bomVinyl.addElement(SID20);
    FormTextField INK120 = new FormTextField("INK120", bomVinylDetail.getSleeveInk1(), false, 2, 2);
    INK120.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK120.setTabIndex(25);
    INK120.setId("Sleeve / Ink");
    bomVinyl.addElement(INK120);
    FormTextField INK220 = new FormTextField("INK220", bomVinylDetail.getSleeveInk2(), false, 2, 2);
    INK220.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK220.setTabIndex(26);
    INK220.setId("Sleeve / Ink");
    bomVinyl.addElement(INK220);
    FormTextField INF20 = new FormTextField("INF20", bomVinylDetail.getSleeveInfo(), false, 50, 120);
    INF20.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF20.setTabIndex(27);
    INF20.setId("Sleeve / Additional Information");
    bomVinyl.addElement(INF20);
    FormCheckBox PID11 = new FormCheckBox("PID11", "on", "", false, bomVinylDetail.jacketStatusIndicator);
    PID11.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID11.setId("PID11");
    PID11.setTabIndex(28);
    PID11.setId("Jacket");
    bomVinyl.addElement(PID11);
    Vector sid11 = MilestoneHelper.getBomSuppliers(11, bomVinylDetail.jacketSupplierId);
    FormDropDownMenu SID11 = MilestoneHelper.getLookupDropDown("SID11", sid11, String.valueOf(bomVinylDetail.jacketSupplierId), false, false);
    SID11.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SID11.setTabIndex(29);
    SID11.setId("Jacket / Suppliers");
    bomVinyl.addElement(SID11);
    FormTextField INK111 = new FormTextField("INK111", bomVinylDetail.getJacketInk1(), false, 2, 2);
    INK111.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK111.setTabIndex(30);
    INK111.setId("Jacket / Ink");
    bomVinyl.addElement(INK111);
    FormTextField INK211 = new FormTextField("INK211", bomVinylDetail.getJacketInk2(), false, 2, 2);
    INK211.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK211.setTabIndex(31);
    INK211.setId("Jacket / Ink");
    bomVinyl.addElement(INK211);
    FormTextField INF11 = new FormTextField("INF11", bomVinylDetail.getJacketInfo(), false, 50, 120);
    INF11.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF11.setTabIndex(32);
    INF11.setId("Jacket / Additional Information");
    bomVinyl.addElement(INF11);
    FormCheckBox PID33 = new FormCheckBox("PID33", "on", "", false, bomVinylDetail.insertStatusIndicator);
    PID33.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID33.setId("PID33");
    PID33.setTabIndex(33);
    PID33.setId("Insert");
    bomVinyl.addElement(PID33);
    Vector sid33 = MilestoneHelper.getBomSuppliers(33, bomVinylDetail.insertSupplierId);
    FormDropDownMenu SID33 = MilestoneHelper.getLookupDropDown("SID33", sid33, String.valueOf(bomVinylDetail.insertSupplierId), false, false);
    SID33.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SID33.setTabIndex(34);
    SID33.setId("Insert / Suppliers");
    bomVinyl.addElement(SID33);
    FormTextField INK133 = new FormTextField("INK133", bomVinylDetail.getInsertInk1(), false, 2, 2);
    INK133.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK133.setTabIndex(35);
    INK133.setId("Insert / Ink");
    bomVinyl.addElement(INK133);
    FormTextField INK233 = new FormTextField("INK233", bomVinylDetail.getInsertInk2(), false, 2, 2);
    INK233.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK233.setTabIndex(36);
    INK233.setId("Insert / Ink");
    bomVinyl.addElement(INK233);
    FormTextField INF33 = new FormTextField("INF33", bomVinylDetail.getInsertInfo(), false, 50, 120);
    INF33.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF33.setTabIndex(37);
    INF33.setId("Insert / Additional Information");
    bomVinyl.addElement(INF33);
    FormCheckBox PID21 = new FormCheckBox("PID21", "on", "", false, bomVinylDetail.stickerOneStatusIndicator);
    PID21.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID21.setId("PID21");
    PID21.setTabIndex(38);
    PID21.setId("Sticker1");
    bomVinyl.addElement(PID21);
    Vector sid21 = MilestoneHelper.getBomSuppliers(21, "0,1,3", bomVinylDetail.stickerOneSupplierId);
    FormDropDownMenu SID21 = MilestoneHelper.getLookupDropDown("SID21", sid21, String.valueOf(bomVinylDetail.stickerOneSupplierId), false, false);
    SID21.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SID21.setTabIndex(39);
    SID21.setId("Sticker1 / Supplier");
    bomVinyl.addElement(SID21);
    FormTextField INK121 = new FormTextField("INK121", bomVinylDetail.getStickerOneInk1(), false, 2, 2);
    INK121.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK121.setTabIndex(40);
    INK121.setId("Sticker1 / Ink");
    bomVinyl.addElement(INK121);
    FormTextField INK221 = new FormTextField("INK221", bomVinylDetail.getStickerOneInk2(), false, 2, 2);
    INK211.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK221.setTabIndex(41);
    INK221.setId("Sticker1 / Ink");
    bomVinyl.addElement(INK221);
    FormTextField SEL21 = new FormTextField("SEL21", bomVinylDetail.getStickerOnePlaces(), false, 6, 6);
    SEL21.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SEL21.setTabIndex(42);
    SEL21.setId("Sticker1 / Places");
    bomVinyl.addElement(SEL21);
    FormTextField INF21 = new FormTextField("INF21", bomVinylDetail.getStickerOneInfo(), false, 20, 120);
    INF21.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF21.setTabIndex(43);
    INF21.setId("Sticker1 / Additional Information");
    bomVinyl.addElement(INF21);
    FormCheckBox PID22 = new FormCheckBox("PID22", "on", "", false, bomVinylDetail.stickerTwoStatusIndicator);
    PID22.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID22.setId("PID22");
    PID22.setTabIndex(44);
    PID22.setId("Sticker2");
    bomVinyl.addElement(PID22);
    Vector sid22 = MilestoneHelper.getBomSuppliers(22, "0,1,3", bomVinylDetail.stickerTwoSupplierId);
    FormDropDownMenu SID22 = MilestoneHelper.getLookupDropDown("SID22", sid22, String.valueOf(bomVinylDetail.stickerTwoSupplierId), false, false);
    SID22.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SID22.setTabIndex(45);
    SID22.setId("Sticker2 / Suppliers");
    bomVinyl.addElement(SID22);
    FormTextField INK122 = new FormTextField("INK122", bomVinylDetail.getStickerTwoInk1(), false, 2, 2);
    INK122.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK122.setTabIndex(46);
    INK122.setId("Sticker2 / Ink");
    bomVinyl.addElement(INK122);
    FormTextField INK222 = new FormTextField("INK222", bomVinylDetail.getStickerTwoInk2(), false, 2, 2);
    INK222.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK222.setTabIndex(47);
    INK222.setId("Sticker2 / Ink");
    bomVinyl.addElement(INK222);
    FormTextField SEL22 = new FormTextField("SEL22", bomVinylDetail.getStickerTwoPlaces(), false, 6, 6);
    SEL22.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SEL22.setTabIndex(48);
    SEL22.setId("Sticker2 / Places");
    bomVinyl.addElement(SEL22);
    FormTextField INF22 = new FormTextField("INF22", bomVinylDetail.getStickerTwoInfo(), false, 20, 120);
    INF22.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF22.setTabIndex(49);
    INF22.setId("Sticker2 / Additional Information");
    bomVinyl.addElement(INF22);
    FormCheckBox PID18 = new FormCheckBox("PID18", "on", "", false, bomVinylDetail.otherStatusIndicator);
    PID18.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID18.setId("PID18");
    PID18.setTabIndex(50);
    PID18.setId("Other");
    bomVinyl.addElement(PID18);
    Vector sid18 = MilestoneHelper.getBomSuppliers(18, bomVinylDetail.otherSupplierId);
    FormDropDownMenu SID18 = MilestoneHelper.getLookupDropDown("SID18", sid18, String.valueOf(bomVinylDetail.otherSupplierId), false, false);
    SID18.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SID18.setTabIndex(51);
    SID18.setId("Other / Supplier");
    bomVinyl.addElement(SID18);
    FormTextField INK118 = new FormTextField("INK118", bomVinylDetail.getOtherInk1(), false, 2, 2);
    INK118.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK118.setTabIndex(52);
    INK118.setId("Other / Ink");
    bomVinyl.addElement(INK118);
    FormTextField INK218 = new FormTextField("INK218", bomVinylDetail.getOtherInk2(), false, 2, 2);
    INK218.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK218.setTabIndex(53);
    INK218.setId("Other / Ink");
    bomVinyl.addElement(INK218);
    FormTextField INF18 = new FormTextField("INF18", bomVinylDetail.getOtherInfo(), false, 50, 120);
    INF18.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF18.setTabIndex(54);
    INF18.setId("Other / Additional Information");
    bomVinyl.addElement(INF18);
    return bomVinyl;
  }
  
  protected Form buildNewForm(Context context, Selection selection, String command) {
    Form bomForm = new Form(this.application, "bomForm", this.application.getInfrastructure().getServletURL(), "POST");
    String selectionID = "-1";
    if (selection != null)
      selectionID = String.valueOf(selection.getSelectionID()); 
    bomForm.addElement(new FormHidden("selectionID", selectionID, true));
    bomForm.addElement(new FormHidden("bomLastUpdateCheck", "-1", true));
    User currentUser = (User)context.getSessionValue("user");
    int secureLevel = getSelectionBomPermissions(selection, currentUser);
    setButtonVisibilities(selection, currentUser, context, secureLevel, command);
    bomForm.addElement(new FormHidden("OrderBy", "", true));
    MilestoneInfrastructure inf = (MilestoneInfrastructure)context.getInfrastructure();
    String printOption = "Draft";
    FormRadioButtonGroup PrintOption = new FormRadioButtonGroup("printOption", printOption, "Draft,Final", false);
    boolean numberUnitsIsZero = false;
    numberUnitsIsZero = (selection.getNumberOfUnits() == 0);
    boolean statusIsTBS = false;
    statusIsTBS = SelectionManager.getLookupObjectValue(selection.getSelectionStatus()).equalsIgnoreCase("TBS");
    if (numberUnitsIsZero || statusIsTBS) {
      PrintOption.addFormEvent("onClick", "noUnitsOrTBS(this, " + numberUnitsIsZero + ", " + statusIsTBS + ");hidePrintButtons('printWindow','');toggleSave('" + inf.getImageDirectory() + "');");
    } else {
      PrintOption.addFormEvent("onClick", "hidePrintButtons('printWindow','');toggleSave('" + inf.getImageDirectory() + "');");
    } 
    PrintOption.setTabIndex(0);
    PrintOption.setId("Draft/Final");
    bomForm.addElement(PrintOption);
    FormDateField Date = new FormDateField("Date", MilestoneHelper.getFormatedDate(Calendar.getInstance()), false, 10);
    Date.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    Date.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
    Date.addFormEvent("onBlur", "JavaScript:checkField( this )");
    Date.setTabIndex(1);
    bomForm.addElement(Date);
    String selectionValue = "Add";
    FormRadioButtonGroup IsAdded = new FormRadioButtonGroup("IsAdded", selectionValue, "Add, Change", false);
    IsAdded.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    IsAdded.setTabIndex(2);
    IsAdded.setId("Add/Change");
    IsAdded.setEnabled(false);
    bomForm.addElement(IsAdded);
    FormTextField ChangeNumber = new FormTextField("ChangeNumber", "-1", false, 2, 2);
    ChangeNumber.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    ChangeNumber.setTabIndex(3);
    ChangeNumber.setId("Change #");
    ChangeNumber.setEnabled(false);
    bomForm.addElement(ChangeNumber);
    FormTextField Submitter = new FormTextField("Submitter", currentUser.getName(), false, 30, 50);
    Submitter.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    Submitter.setTabIndex(4);
    bomForm.addElement(Submitter);
    FormTextField Phone = new FormTextField("Phone", currentUser.getPhone(), false, 30, 30);
    Phone.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    Phone.setTabIndex(6);
    bomForm.addElement(Phone);
    FormTextField Email = new FormTextField("Email", currentUser.getEmail(), false, 30, 50);
    Email.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    Email.setTabIndex(5);
    bomForm.addElement(Email);
    Selection mySelection = selection;
    String labelText = "";
    if (mySelection.getLabel() != null)
      labelText = mySelection.getLabel().getName().toUpperCase(); 
    FormTextField Label = new FormTextField("Label", labelText, false, 30);
    Label.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    bomForm.addElement(Label);
    String statusText = "";
    if (mySelection.getSelectionStatus() != null)
      statusText = mySelection.getSelectionStatus().getName(); 
    FormTextField status = new FormTextField("status", statusText, false, 30);
    bomForm.addElement(status);
    String upc = "";
    if (mySelection.getUpc() != null)
      upc = mySelection.getUpc(); 
    FormTextField Upc = new FormTextField("upc", upc, false, 20);
    Upc.setStartingValue(upc);
    bomForm.addElement(Upc);
    String imprintText = "";
    if (mySelection.getImprint() != null)
      imprintText = mySelection.getImprint(); 
    FormTextField imprint = new FormTextField("imprint", imprintText, false, 30);
    bomForm.addElement(imprint);
    String artistText = "";
    if (mySelection.getArtist() != null)
      artistText = mySelection.getArtist(); 
    FormTextField Artist = new FormTextField("Artist", artistText, false, 125);
    Artist.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    bomForm.addElement(Artist);
    bomForm.addElement(new FormHidden("Artist", artistText, false));
    String titleText = "";
    if (mySelection.getTitle() != null)
      titleText = mySelection.getTitle(); 
    FormTextField Title = new FormTextField("Title", titleText, false, 125);
    Title.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    bomForm.addElement(Title);
    String selectionNumberText = "";
    selectionNumberText = String.valueOf(SelectionManager.getLookupObjectValue(selection.getPrefixID())) + " " + selection.getSelectionNo();
    FormTextField SelectionNumber = new FormTextField("SelectionNumber", selectionNumberText, false, 50);
    SelectionNumber.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SelectionNumber.setId("Local Prod #");
    bomForm.addElement(SelectionNumber);
    String ReleasingCompText = "";
    if (ReleasingFamily.getName(selection.getReleaseFamilyId()) != null)
      ReleasingCompText = ReleasingFamily.getName(selection.getReleaseFamilyId()); 
    FormTextField ReleasingComp = new FormTextField("ReleasingComp", ReleasingCompText, false, 30);
    ReleasingComp.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    ReleasingComp.setId("Releasing Company");
    bomForm.addElement(ReleasingComp);
    String isRetailText = "";
    if (selection.getReleaseType().getName() != null && selection.getReleaseType().getName().equalsIgnoreCase("Promotional")) {
      isRetailText = "Promotional";
    } else {
      isRetailText = "Commercial";
    } 
    FormTextField IsRetail = new FormTextField("IsRetail", isRetailText, false, 50);
    IsRetail.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    IsRetail.setTabIndex(8);
    IsRetail.setId("Type");
    bomForm.addElement(IsRetail);
    String streetDateOnBomText = "";
    if (selection.getStreetDate() != null)
      streetDateOnBomText = MilestoneHelper.getFormatedDate(selection.getStreetDate()); 
    FormTextField streetDateOnBom = new FormTextField("DueDate", streetDateOnBomText, false, 10);
    streetDateOnBom.setEnabled(false);
    streetDateOnBom.setTabIndex(9);
    streetDateOnBom.setId("Street Date");
    bomForm.addElement(streetDateOnBom);
    String unitsPerSetText = "";
    if (selection.getNumberOfUnits() > 0)
      try {
        unitsPerSetText = Integer.toString(selection.getNumberOfUnits());
      } catch (NumberFormatException e) {
        System.out.println("Error converting Units Per Set into integer.");
      }  
    FormTextField UnitsPerPackage = new FormTextField("UnitsPerPackage", unitsPerSetText, false, 10);
    UnitsPerPackage.setEnabled(false);
    UnitsPerPackage.setTabIndex(10);
    UnitsPerPackage.setId("Units per Package ");
    bomForm.addElement(UnitsPerPackage);
    FormTextField Runtime = new FormTextField("Runtime", "", false, 10, 10);
    Runtime.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    Runtime.setTabIndex(11);
    bomForm.addElement(Runtime);
    boolean isOn = false;
    if (!isRetailText.equalsIgnoreCase("Promotional"))
      isOn = true; 
    FormCheckBox UseNoShrinkWrap = new FormCheckBox("UseNoShrinkWrap", "on", "", false, isOn);
    UseNoShrinkWrap.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    UseNoShrinkWrap.setTabIndex(13);
    UseNoShrinkWrap.setStartingChecked(true);
    UseNoShrinkWrap.setId("Shrink Wrap");
    bomForm.addElement(UseNoShrinkWrap);
    FormCheckBox HasSpineSticker = new FormCheckBox("HasSpineSticker", "on", "", false, isOn);
    HasSpineSticker.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    HasSpineSticker.setId("HasSpineSticker");
    HasSpineSticker.setTabIndex(12);
    HasSpineSticker.setId("Spine Sticker");
    bomForm.addElement(HasSpineSticker);
    FormTextField Configuration = new FormTextField("Configuration", "", false, 10, 10);
    Configuration.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    bomForm.addElement(Configuration);
    FormTextArea SpecialInstructions = new FormTextArea("SpecialInstructions", "", false, 2, 100, "virtual");
    SpecialInstructions.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SpecialInstructions.setTabIndex(100);
    SpecialInstructions.setId("Special Instructions");
    bomForm.addElement(SpecialInstructions);
    String configuration = "";
    configuration = selection.getSelectionConfig().getSelectionConfigurationAbbreviation();
    if (configuration.equalsIgnoreCase("CAS")) {
      Bom bom = null;
      buildNewCassette(bomForm, selection, bom);
    } else if (configuration.equalsIgnoreCase("VIN")) {
      Bom bom = null;
      buildNewVinyl(bomForm, selection, bom);
    } else if (configuration.equalsIgnoreCase("DVV")) {
      Bom bom = null;
      buildNewDVD(bomForm, selection, bom);
    } else {
      Bom bom = null;
      buildNewDisk(bomForm, selection, bom);
    } 
    FormTextField lastUpdated = new FormTextField("lastUpdatedDate", false, 50);
    lastUpdated.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    lastUpdated.setValue("");
    bomForm.addElement(lastUpdated);
    FormTextField lastUpdatedBy = new FormTextField("lastUpdatedBy", false, 50);
    lastUpdatedBy.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    lastUpdatedBy.setValue("");
    bomForm.addElement(lastUpdatedBy);
    FormTextField lastLegacyUpdateDate = new FormTextField("lastLegacyUpdateDate", false, 50);
    lastLegacyUpdateDate.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    lastLegacyUpdateDate.setValue("");
    bomForm.addElement(lastLegacyUpdateDate);
    bomForm = addSelectionSearchElements(context, selection, bomForm);
    bomForm.addElement(new FormHidden("cmd", "bom-edit-new", true));
    String releaseWeekString = "";
    if (selection != null)
      releaseWeekString = MilestoneHelper.getReleaseWeekString(selection); 
    context.putDelivery("releaseWeek", releaseWeekString);
    if (context.getSessionValue("NOTEPAD_BOM_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_BOM_VISIBLE")); 
    if (selection != null)
      context.putDelivery("selection", selection); 
    return bomForm;
  }
  
  protected Form buildNewDisk(Form bomDisk, Selection selection, Bom bom) {
    Vector sel12;
    String typeFlag = "0";
    int ctrlSize = 50;
    if (selection.getSelectionConfig().getSelectionConfigurationAbbreviation().equalsIgnoreCase("DVV")) {
      typeFlag = "1,2";
      ctrlSize = 20;
    } else {
      typeFlag = "0,1";
      ctrlSize = 50;
    } 
    FormCheckBox PID7 = new FormCheckBox("PID7", "on", "", false);
    PID7.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID7.setTabIndex(14);
    PID7.setId("Disc");
    bomDisk.addElement(PID7);
    FormTextField INK17 = new FormTextField("INK17", "", false, 2, 2);
    INK17.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK17.setTabIndex(15);
    INK17.setId("Disc / Ink");
    bomDisk.addElement(INK17);
    FormTextField INK27 = new FormTextField("INK27", "", false, 2, 2);
    INK27.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK27.setId("Disc / Ink");
    INK27.setTabIndex(16);
    bomDisk.addElement(INK27);
    FormTextField INF7 = new FormTextField("INF7", "", false, ctrlSize, 120);
    INF7.setTabIndex(17);
    INF7.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF7.setId("Disc / Additional Information");
    bomDisk.addElement(INF7);
    FormCheckBox PID12 = new FormCheckBox("PID12", "on", "", false);
    PID12.setId("PID12");
    PID12.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID12.setTabIndex(18);
    PID12.setId("Jewel Box");
    bomDisk.addElement(PID12);
    if (selection.getSelectionConfig().getSelectionConfigurationAbbreviation().equalsIgnoreCase("DVV")) {
      sel12 = Cache.getInstance().getLookupDetailValuesFromDatabase(63);
    } else {
      sel12 = Cache.getInstance().getLookupDetailValuesFromDatabase(41);
    } 
    FormDropDownMenu SEL12 = MilestoneHelper.getLookupDropDownBom("SEL12", sel12, "", false, true);
    SEL12.setTabIndex(19);
    SEL12.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SEL12.setId("Jewel Box / Additional Information");
    bomDisk.addElement(SEL12);
    FormTextField INF12 = new FormTextField("INF12", "", false, 20, 120);
    INF12.setTabIndex(20);
    INF12.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF12.setId("Jewel Box / Additional Information");
    bomDisk.addElement(INF12);
    FormCheckBox PID23 = new FormCheckBox("PID23", "on", "", false);
    PID23.setId("PID23");
    PID23.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID23.setTabIndex(30);
    PID23.setId("Tray");
    bomDisk.addElement(PID23);
    Vector sel23 = Cache.getInstance().getLookupDetailValuesFromDatabase(42);
    FormDropDownMenu SEL23 = MilestoneHelper.getLookupDropDownBom("SEL23", sel23, "", false, true);
    SEL23.setTabIndex(31);
    SEL23.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SEL23.setId("Tray / Additional Information");
    bomDisk.addElement(SEL23);
    FormTextField INF23 = new FormTextField("INF23", "", false, 20, 120);
    INF23.setTabIndex(32);
    INF23.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF23.setId("Tray / Additional Information");
    bomDisk.addElement(INF23);
    FormCheckBox PID10 = new FormCheckBox("PID10", "on", "", false);
    PID10.setId("PID10");
    PID10.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID10.setTabIndex(33);
    PID10.setId("Inlay");
    bomDisk.addElement(PID10);
    Vector sid10 = MilestoneHelper.getBomSuppliers(10, typeFlag);
    FormDropDownMenu SID10 = MilestoneHelper.getLookupDropDown("SID10", sid10, "", false, false);
    SID10.setTabIndex(34);
    SID10.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SID10.setId("Inlay / Supplier");
    bomDisk.addElement(SID10);
    Vector sid7 = MilestoneHelper.getBomSuppliers(7, typeFlag);
    FormDropDownMenu SID7 = MilestoneHelper.getLookupDropDown("SID7", sid7, "", false, false);
    SID7.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SID7.setTabIndex(34);
    SID7.setId("Disc");
    bomDisk.addElement(SID7);
    FormTextField INK110 = new FormTextField("INK110", "", false, 2, 2);
    INK110.setTabIndex(35);
    INK110.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK110.setId("Inlay / Ink");
    bomDisk.addElement(INK110);
    FormTextField INK210 = new FormTextField("INK210", "", false, 2, 2);
    INK210.setTabIndex(36);
    INK210.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK210.setId("Inlay / Ink");
    bomDisk.addElement(INK210);
    FormTextField INF10 = new FormTextField("INF10", "", false, 50, 120);
    INF10.setTabIndex(37);
    INF10.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF10.setId("Inlay / Additional Information");
    bomDisk.addElement(INF10);
    FormCheckBox PID9 = new FormCheckBox("PID9", "on", "", false);
    PID9.setId("PID9");
    PID9.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID9.setTabIndex(38);
    PID9.setId("Front Insert");
    bomDisk.addElement(PID9);
    Vector sid9 = MilestoneHelper.getBomSuppliers(9, typeFlag);
    FormDropDownMenu SID9 = MilestoneHelper.getLookupDropDown("SID9", sid9, "", false, false);
    SID9.setTabIndex(39);
    SID9.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SID9.setId("Front Insert / Supplier");
    bomDisk.addElement(SID9);
    FormTextField INK19 = new FormTextField("INK19", "", false, 2, 2);
    INK19.setTabIndex(40);
    INK19.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK19.setId("Front Insert / Ink");
    bomDisk.addElement(INK19);
    FormTextField INK29 = new FormTextField("INK29", "", false, 2, 2);
    INK29.setTabIndex(41);
    INK29.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK29.setId("Front Insert / Ink");
    bomDisk.addElement(INK29);
    FormTextField INF9 = new FormTextField("INF9", "", false, 50, 120);
    INF9.setTabIndex(42);
    INF9.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF9.setId("Front Insert / Additional Information");
    bomDisk.addElement(INF9);
    FormCheckBox PID8 = new FormCheckBox("PID8", "on", "", false);
    PID8.setId("PID8");
    PID8.setTabIndex(43);
    PID8.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID8.setId("Folder");
    bomDisk.addElement(PID8);
    Vector sid8 = MilestoneHelper.getBomSuppliers(8, typeFlag);
    FormDropDownMenu SID8 = MilestoneHelper.getLookupDropDown("SID8", sid8, "", false, false);
    SID8.setTabIndex(44);
    SID8.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SID8.setId("Folder / Supplier");
    bomDisk.addElement(SID8);
    FormTextField INK18 = new FormTextField("INK18", "", false, 2, 2);
    INK18.setTabIndex(45);
    INK18.setId("Folder / Ink");
    INK18.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    bomDisk.addElement(INK18);
    FormTextField INK28 = new FormTextField("INK28", "", false, 2, 2);
    INK28.setTabIndex(46);
    INK28.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK28.setId("Folder / Ink");
    bomDisk.addElement(INK28);
    Vector sel8 = Cache.getInstance().getLookupDetailValuesFromDatabase(43);
    FormDropDownMenu SEL8 = MilestoneHelper.getLookupDropDownBom("SEL8", sel8, "", false, true);
    SEL8.setTabIndex(47);
    SEL8.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SEL8.setId("Folder / Pages");
    bomDisk.addElement(SEL8);
    FormTextField INF8 = new FormTextField("INF8", "", false, 20, 120);
    INF8.setTabIndex(48);
    INF8.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF8.setId("Folder / Additinal Information");
    bomDisk.addElement(INF8);
    FormCheckBox PID1 = new FormCheckBox("PID1", "on", "", false);
    PID1.setId("PID1");
    PID1.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID1.setTabIndex(49);
    PID1.setId("Booklet");
    bomDisk.addElement(PID1);
    Vector sid1 = MilestoneHelper.getBomSuppliers(1, typeFlag);
    FormDropDownMenu SID1 = MilestoneHelper.getLookupDropDown("SID1", sid1, "", false, false);
    SID1.setTabIndex(50);
    SID1.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SID1.setId("Booklet / Supplier");
    bomDisk.addElement(SID1);
    FormTextField INK11 = new FormTextField("INK11", "", false, 2, 2);
    INK11.setTabIndex(51);
    INK11.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK11.setId("Booklet / Ink");
    bomDisk.addElement(INK11);
    FormTextField INK21 = new FormTextField("INK21", "", false, 2, 2);
    INK21.setTabIndex(52);
    INK21.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK21.setId("Booklet / Ink");
    bomDisk.addElement(INK21);
    Vector sel1 = Cache.getInstance().getLookupDetailValuesFromDatabase(45);
    FormDropDownMenu SEL1 = MilestoneHelper.getLookupDropDownBom("SEL1", sel1, "", false, true);
    SEL1.setTabIndex(53);
    SEL1.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SEL1.setId("Booklet / Pages");
    bomDisk.addElement(SEL1);
    FormTextField INF1 = new FormTextField("INF1", "", false, 20, 120);
    INF1.setTabIndex(54);
    INF1.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF1.setId("Booklet / Additional Information");
    bomDisk.addElement(INF1);
    FormCheckBox PID4 = new FormCheckBox("PID4", "on", "", false);
    PID4.setId("PID4");
    PID4.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID4.setTabIndex(55);
    PID4.setId("BRC Insert");
    bomDisk.addElement(PID4);
    Vector sid4 = MilestoneHelper.getBomSuppliers(4, typeFlag);
    FormDropDownMenu SID4 = MilestoneHelper.getLookupDropDown("SID4", sid4, "", false, false);
    SID4.setTabIndex(56);
    SID4.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SID4.setId("BRC Insert / Supplier");
    bomDisk.addElement(SID4);
    FormTextField INK14 = new FormTextField("INK14", "", false, 2, 2);
    INK14.setTabIndex(57);
    INK14.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK14.setId("BRC Insert / Ink");
    bomDisk.addElement(INK14);
    FormTextField INK24 = new FormTextField("INK24", "", false, 2, 2);
    INK24.setTabIndex(58);
    INK24.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK24.setId("BRC Insert / Ink");
    bomDisk.addElement(INK24);
    FormTextField SEL4 = new FormTextField("SEL4", "", false, 10, 20);
    SEL4.setTabIndex(59);
    SEL4.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SEL4.setId("BRC Insert / Size");
    bomDisk.addElement(SEL4);
    FormTextField INF4 = new FormTextField("INF4", "", false, 20, 120);
    INF4.setTabIndex(60);
    INF4.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF4.setId("BRC Insert / Additional Information");
    bomDisk.addElement(INF4);
    FormCheckBox PID15 = new FormCheckBox("PID15", "on", "", false);
    PID15.setId("PID15");
    PID15.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID15.setTabIndex(61);
    PID15.setId("Mini Jacket");
    bomDisk.addElement(PID15);
    Vector sid15 = MilestoneHelper.getBomSuppliers(15, typeFlag);
    FormDropDownMenu SID15 = MilestoneHelper.getLookupDropDown("SID15", sid15, "", false, false);
    SID15.setTabIndex(62);
    SID15.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SID15.setId("Mini Jacket / Supplier");
    bomDisk.addElement(SID15);
    FormTextField INK115 = new FormTextField("INK115", "", false, 2, 2);
    INK115.setTabIndex(63);
    INK115.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK115.setId("Mini Jacket / Ink");
    bomDisk.addElement(INK115);
    FormTextField INK215 = new FormTextField("INK215", "", false, 2, 2);
    INK215.setTabIndex(64);
    INK215.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK215.setId("Mini Jacket / Ink");
    bomDisk.addElement(INK215);
    FormTextField INF15 = new FormTextField("INF15", "", false, 20, 120);
    INF15.setTabIndex(65);
    INF15.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF15.setId("Mini Jacket / Additional Information");
    bomDisk.addElement(INF15);
    FormCheckBox PID6 = new FormCheckBox("PID6", "on", "", false);
    PID6.setId("PID6");
    PID6.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID6.setTabIndex(66);
    PID6.setId("Digipak");
    bomDisk.addElement(PID6);
    Vector sid6 = MilestoneHelper.getBomSuppliers(6, typeFlag);
    FormDropDownMenu SID6 = MilestoneHelper.getLookupDropDown("SID6", sid6, "", false, false);
    SID6.setTabIndex(67);
    SID6.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SID6.setId("Digipak / Supplier");
    bomDisk.addElement(SID6);
    FormTextField INK16 = new FormTextField("INK16", "", false, 2, 2);
    INK16.setTabIndex(68);
    INK16.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK16.setId("Digipak / Ink");
    bomDisk.addElement(INK16);
    FormTextField INK26 = new FormTextField("INK26", "", false, 2, 2);
    INK26.setTabIndex(69);
    INK26.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK26.setId("Digipak / Ink");
    bomDisk.addElement(INK26);
    FormTextField SEL6 = new FormTextField("SEL6", "", false, 6, 6);
    SEL6.setTabIndex(70);
    SEL6.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SEL6.setId("Digipak / Tray");
    bomDisk.addElement(SEL6);
    FormTextField INF6 = new FormTextField("INF6", "", false, 20, 120);
    INF6.setTabIndex(71);
    INF6.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF6.setId("Digipak / Additional Information");
    bomDisk.addElement(INF6);
    FormCheckBox PID31 = new FormCheckBox("PID31", "on", "", false);
    PID6.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID6.setId("PID31");
    PID6.setTabIndex(72);
    PID6.setId("Softpak");
    bomDisk.addElement(PID31);
    Vector sid31 = MilestoneHelper.getBomSuppliers(31, typeFlag);
    FormDropDownMenu SID31 = MilestoneHelper.getLookupDropDown("SID31", sid31, "", false, false);
    SID31.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SID31.setTabIndex(73);
    SID31.setId("Softpak / Supplier");
    bomDisk.addElement(SID31);
    FormTextField INK131 = new FormTextField("INK131", "", false, 2, 2);
    INK131.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK131.setTabIndex(74);
    INK131.setId("Softpak / Ink");
    bomDisk.addElement(INK131);
    FormTextField INK231 = new FormTextField("INK231", "", false, 2, 2);
    INK231.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK231.setTabIndex(75);
    INK231.setId("Softpak / Ink");
    bomDisk.addElement(INK231);
    FormTextField SEL31 = new FormTextField("SEL31", "", false, 6, 6);
    SEL31.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SEL31.setTabIndex(76);
    SEL31.setId("Softpak / Tray");
    bomDisk.addElement(SEL31);
    FormTextField INF31 = new FormTextField("INF31", "", false, 20, 120);
    INF31.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF31.setTabIndex(77);
    INF31.setId("Softpak / Additional Information");
    bomDisk.addElement(INF31);
    FormCheckBox PID21 = new FormCheckBox("PID21", "on", "", false);
    PID21.setId("PID21");
    PID21.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID21.setTabIndex(78);
    PID21.setId("Sticker1");
    bomDisk.addElement(PID21);
    Vector sid21 = MilestoneHelper.getBomSuppliers(21, typeFlag);
    FormDropDownMenu SID21 = MilestoneHelper.getLookupDropDown("SID21", sid21, "", false, false);
    SID21.setTabIndex(79);
    SID21.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SID21.setId("Sticker1 / Supplier");
    bomDisk.addElement(SID21);
    FormTextField INK121 = new FormTextField("INK121", "", false, 2, 2);
    INK121.setTabIndex(80);
    INK121.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK121.setId("Sticker1 / Ink");
    bomDisk.addElement(INK121);
    FormTextField INK221 = new FormTextField("INK221", "", false, 2, 2);
    INK221.setTabIndex(82);
    INK221.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK221.setId("Sticker1 / Ink");
    bomDisk.addElement(INK221);
    FormTextField SEL21 = new FormTextField("SEL21", "", false, 6, 6);
    SEL21.setTabIndex(83);
    SEL21.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SEL21.setId("Sticker1 / Places");
    bomDisk.addElement(SEL21);
    FormTextField INF21 = new FormTextField("INF21", "", false, 20, 120);
    INF21.setTabIndex(84);
    INF21.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF21.setId("Sticker1 / Additional Information");
    bomDisk.addElement(INF21);
    FormCheckBox PID22 = new FormCheckBox("PID22", "on", "", false);
    PID22.setId("PID22");
    PID22.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID22.setTabIndex(85);
    PID22.setId("Sticker2");
    bomDisk.addElement(PID22);
    Vector sid22 = MilestoneHelper.getBomSuppliers(22, typeFlag);
    FormDropDownMenu SID22 = MilestoneHelper.getLookupDropDown("SID22", sid22, "", false, false);
    SID22.setTabIndex(86);
    SID22.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SID22.setId("Sticker2 / Supplier");
    bomDisk.addElement(SID22);
    FormTextField INK122 = new FormTextField("INK122", "", false, 2, 2);
    INK122.setTabIndex(87);
    INK122.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK122.setId("Sticker2 / Ink");
    bomDisk.addElement(INK122);
    FormTextField INK222 = new FormTextField("INK222", "", false, 2, 2);
    INK222.setTabIndex(88);
    INK222.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK222.setId("Sticker2 / Ink");
    bomDisk.addElement(INK222);
    FormTextField SEL22 = new FormTextField("SEL22", "", false, 6, 6);
    SEL22.setTabIndex(89);
    SEL22.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SEL22.setId("Sticker2 / Places");
    bomDisk.addElement(SEL22);
    FormTextField INF22 = new FormTextField("INF22", "", false, 20, 120);
    INF22.setTabIndex(90);
    INF22.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF22.setId("Sticker2 / Additional Information");
    bomDisk.addElement(INF22);
    FormCheckBox PID2 = new FormCheckBox("PID2", "on", "", false);
    PID2.setId("PID2");
    PID2.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID2.setTabIndex(91);
    PID2.setId("Book (Other/Set)");
    bomDisk.addElement(PID2);
    Vector sid2 = MilestoneHelper.getBomSuppliers(2, typeFlag);
    FormDropDownMenu SID2 = MilestoneHelper.getLookupDropDown("SID2", sid2, "", false, false);
    SID2.setTabIndex(92);
    SID2.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SID2.setId("Book (Other/Set) / Supplier");
    bomDisk.addElement(SID2);
    FormTextField INK12 = new FormTextField("INK12", "", false, 2, 2);
    INK12.setTabIndex(93);
    INK12.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK12.setId("Book (Other/Set) / Ink");
    bomDisk.addElement(INK12);
    FormTextField INK22 = new FormTextField("INK22", "", false, 2, 2);
    INK22.setTabIndex(94);
    INK22.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK22.setId("Book (Other/Set) / Ink");
    bomDisk.addElement(INK22);
    FormTextField SEL2 = new FormTextField("SEL2", "", false, 5, 5);
    SEL2.setTabIndex(95);
    SEL2.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SEL2.setId("Book (Other/Set) / Pages");
    bomDisk.addElement(SEL2);
    FormTextField INF2 = new FormTextField("INF2", "", false, 20, 120);
    INF2.setTabIndex(96);
    INF2.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF2.setId("Book (Other/Set) / Additional Information");
    bomDisk.addElement(INF2);
    FormCheckBox PID3 = new FormCheckBox("PID3", "on", "", false);
    PID3.setId("PID3");
    PID3.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID3.setTabIndex(97);
    PID3.setId("Box (Set)");
    bomDisk.addElement(PID3);
    Vector sid3 = MilestoneHelper.getBomSuppliers(3, typeFlag);
    FormDropDownMenu SID3 = MilestoneHelper.getLookupDropDown("SID3", sid3, "", false, false);
    SID3.setTabIndex(98);
    SID3.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SID3.setId("Box (Set) / Supplier");
    bomDisk.addElement(SID3);
    FormTextField INK13 = new FormTextField("INK13", "", false, 2, 2);
    INK13.setTabIndex(99);
    INK13.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK13.setId("Box (Set) / Ink");
    bomDisk.addElement(INK13);
    FormTextField INK23 = new FormTextField("INK23", "", false, 2, 2);
    INK23.setTabIndex(100);
    INK23.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK23.setId("Box (Set) / Ink");
    bomDisk.addElement(INK23);
    FormTextField SEL3 = new FormTextField("SEL3", "", false, 10, 20);
    SEL3.setTabIndex(101);
    SEL3.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SEL3.setId("Box (Set) / Size");
    bomDisk.addElement(SEL3);
    FormTextField INF3 = new FormTextField("INF3", "", false, 20, 120);
    INF3.setTabIndex(102);
    INF3.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF3.setId("Box (Set) / Additional Information");
    bomDisk.addElement(INF3);
    FormCheckBox PID18 = new FormCheckBox("PID18", "on", "", false);
    PID18.setId("PID18");
    PID18.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID18.setTabIndex(103);
    PID18.setId("Other");
    bomDisk.addElement(PID18);
    Vector sid18 = MilestoneHelper.getBomSuppliers(18, typeFlag);
    FormDropDownMenu SID18 = MilestoneHelper.getLookupDropDown("SID18", sid18, "", false, false);
    SID18.setTabIndex(104);
    SID18.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SID18.setId("Other / Supplier");
    bomDisk.addElement(SID18);
    FormTextField INK118 = new FormTextField("INK118", "", false, 2, 2);
    INK118.setTabIndex(105);
    INK118.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK118.setId("Other / Ink");
    bomDisk.addElement(INK118);
    FormTextField INK218 = new FormTextField("INK218", "", false, 2, 2);
    INK218.setTabIndex(106);
    INK218.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK218.setId("Other / Ink");
    bomDisk.addElement(INK218);
    FormTextField INF18 = new FormTextField("INF18", "", false, 20, 120);
    INF18.setTabIndex(107);
    INF18.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF18.setId("Other / Additional Information");
    bomDisk.addElement(INF18);
    return bomDisk;
  }
  
  protected Form buildNewCassette(Form bomCassette, Selection selection, Bom bom) {
    FormCheckBox PID5 = new FormCheckBox("PID5", "on", "", false);
    PID5.setId("PID5");
    PID5.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID5.setTabIndex(14);
    PID5.setId("C-0");
    bomCassette.addElement(PID5);
    FormTextField INK15 = new FormTextField("INK15", "", false, 2, 2);
    INK15.setTabIndex(15);
    INK15.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK15.setId("C-0 / Ink");
    bomCassette.addElement(INK15);
    Vector sid5 = MilestoneHelper.getBomSuppliers(5);
    FormDropDownMenu SID5 = MilestoneHelper.getLookupDropDown("SID5", sid5, String.valueOf(-1), false, false);
    SID5.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SID5.setTabIndex(23);
    SID5.setId("C-0");
    bomCassette.addElement(SID5);
    Vector sid16 = MilestoneHelper.getBomSuppliers(16);
    FormDropDownMenu SID16 = MilestoneHelper.getLookupDropDown("SID16", sid16, String.valueOf(-1), false, false);
    SID16.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SID16.setTabIndex(23);
    SID16.setId("Norelco");
    bomCassette.addElement(SID16);
    FormTextField INK25 = new FormTextField("INK25", "", false, 2, 2);
    INK25.setTabIndex(16);
    INK25.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK25.setId("C-0 / Ink");
    bomCassette.addElement(INK25);
    Vector sel5 = Cache.getInstance().getLookupDetailValuesFromDatabase(46);
    FormDropDownMenu SEL5 = MilestoneHelper.getLookupDropDownBom("SEL5", sel5, "", false, true);
    SEL5.setTabIndex(17);
    SEL5.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SEL5.setId("C-0 / Additional Information");
    bomCassette.addElement(SEL5);
    FormTextField INF5 = new FormTextField("INF5", "", false, 20, 120);
    SEL5.setTabIndex(18);
    INF5.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF5.setId("C-0 / Additional Information");
    bomCassette.addElement(INF5);
    FormCheckBox PID16 = new FormCheckBox("PID16", "on", "", false);
    PID16.setId("PID16");
    PID16.setTabIndex(19);
    PID16.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID16.setId("Norelco");
    bomCassette.addElement(PID16);
    Vector sel16 = Cache.getInstance().getLookupDetailValuesFromDatabase(46);
    FormDropDownMenu SEL16 = MilestoneHelper.getLookupDropDownBom("SEL16", sel16, "", false, true);
    SEL16.setTabIndex(20);
    SEL16.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SEL16.setId("Norelco / Additional Information");
    bomCassette.addElement(SEL16);
    FormTextField INF16 = new FormTextField("INF16", "", false, 20, 120);
    INF16.setTabIndex(21);
    INF16.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF16.setId("Norelco / Additional Information");
    bomCassette.addElement(INF16);
    FormCheckBox PID13 = new FormCheckBox("PID13", "on", "", false);
    PID13.setId("PID13");
    PID13.setTabIndex(22);
    PID13.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID13.setId("J-Card");
    bomCassette.addElement(PID13);
    Vector sid13 = MilestoneHelper.getBomSuppliers(13);
    FormDropDownMenu SID13 = MilestoneHelper.getLookupDropDown("SID13", sid13, "", false, false);
    SID13.setTabIndex(23);
    SID13.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SID13.setId("J-Card / Supplier");
    bomCassette.addElement(SID13);
    FormTextField INK113 = new FormTextField("INK113", "", false, 2, 2);
    INK113.setTabIndex(24);
    INK113.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK113.setId("J-Card / Ink");
    bomCassette.addElement(INK113);
    FormTextField INK213 = new FormTextField("INK213", "", false, 2, 2);
    INK213.setTabIndex(25);
    INK213.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK213.setId("J-Card / Ink");
    bomCassette.addElement(INK213);
    FormTextField SEL13 = new FormTextField("SEL13", "", false, 6, 6);
    SEL13.setTabIndex(26);
    SEL13.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SEL13.setId("J-Card / Panels");
    bomCassette.addElement(SEL13);
    FormTextField INF13 = new FormTextField("INF13", "", false, 20, 120);
    INF13.setTabIndex(27);
    INF13.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF13.setId("J-Card / Additional Information");
    bomCassette.addElement(INF13);
    FormCheckBox PID24 = new FormCheckBox("PID24", "on", "", false);
    PID24.setId("PID24");
    PID24.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID24.setTabIndex(27);
    PID24.setId("U-Card");
    bomCassette.addElement(PID24);
    Vector sid24 = MilestoneHelper.getBomSuppliers(24);
    FormDropDownMenu SID24 = MilestoneHelper.getLookupDropDown("SID24", sid24, "", false, false);
    SID24.setTabIndex(28);
    SID24.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SID24.setId("U-Card / Supplier");
    bomCassette.addElement(SID24);
    FormTextField INK124 = new FormTextField("INK124", "", false, 2, 2);
    INK124.setTabIndex(29);
    INK124.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK124.setId("U-Card / Ink");
    bomCassette.addElement(INK124);
    FormTextField INK224 = new FormTextField("INK224", "", false, 2, 2);
    INK224.setTabIndex(30);
    INK224.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK224.setId("U-Card / Ink");
    bomCassette.addElement(INK224);
    FormTextField SEL24 = new FormTextField("SEL24", "", false, 6, 6);
    SEL24.setTabIndex(31);
    SEL24.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SEL24.setId("U-Card / Panels");
    bomCassette.addElement(SEL24);
    FormTextField INF24 = new FormTextField("INF24", "", false, 20, 120);
    INF24.setTabIndex(31);
    INF24.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF24.setId("U-Card / Additional Information");
    bomCassette.addElement(INF24);
    FormCheckBox PID17 = new FormCheckBox("PID17", "on", "", false);
    PID17.setTabIndex(32);
    PID17.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID17.setId("PID17");
    PID17.setId("O-Card");
    bomCassette.addElement(PID17);
    Vector sid17 = MilestoneHelper.getBomSuppliers(17);
    FormDropDownMenu SID17 = MilestoneHelper.getLookupDropDown("SID17", sid17, "", false, false);
    SID17.setTabIndex(33);
    SID17.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SID17.setId("O-Card / Supplier");
    bomCassette.addElement(SID17);
    FormTextField INK117 = new FormTextField("INK117", "", false, 2, 2);
    INK117.setTabIndex(34);
    INK117.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK117.setId("O-Card / Ink");
    bomCassette.addElement(INK117);
    FormTextField INK217 = new FormTextField("INK217", "", false, 2, 2);
    INK217.setTabIndex(35);
    INK217.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK217.setId("O-Card / Ink");
    bomCassette.addElement(INK217);
    FormTextField INF17 = new FormTextField("INF17", "", false, 20, 120);
    INF17.setTabIndex(36);
    INF17.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF17.setId("O-Card / Additional Information");
    bomCassette.addElement(INF17);
    FormCheckBox PID21 = new FormCheckBox("PID21", "on", "", false);
    PID21.setId("PID21");
    PID21.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID21.setTabIndex(37);
    PID21.setId("Sticker1");
    bomCassette.addElement(PID21);
    Vector sid21 = MilestoneHelper.getBomSuppliers(21);
    FormDropDownMenu SID21 = MilestoneHelper.getLookupDropDown("SID21", sid21, "", false, false);
    SID21.setTabIndex(38);
    SID21.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SID21.setId("Sticker1 / Supplier");
    bomCassette.addElement(SID21);
    FormTextField INK121 = new FormTextField("INK121", "", false, 2, 2);
    INK121.setTabIndex(39);
    INK121.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK121.setId("Sticker1 / Ink");
    bomCassette.addElement(INK121);
    FormTextField INK221 = new FormTextField("INK221", "", false, 2, 2);
    INK221.setTabIndex(40);
    INK221.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK121.setId("Sticker1 / Ink");
    bomCassette.addElement(INK221);
    FormTextField SEL21 = new FormTextField("SEL21", "", false, 6, 6);
    SEL21.setTabIndex(41);
    SEL21.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SEL21.setId("Sticker1 / Places");
    bomCassette.addElement(SEL21);
    FormTextField INF21 = new FormTextField("INF21", "", false, 20, 120);
    INF21.setTabIndex(42);
    INF21.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF21.setId("Sticker1 / Additional Information");
    bomCassette.addElement(INF21);
    FormCheckBox PID22 = new FormCheckBox("PID22", "on", "", false);
    PID22.setId("PID22");
    PID22.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID22.setTabIndex(43);
    PID22.setId("Sticker2");
    bomCassette.addElement(PID22);
    Vector sid22 = MilestoneHelper.getBomSuppliers(22);
    FormDropDownMenu SID22 = MilestoneHelper.getLookupDropDown("SID22", sid22, "", false, false);
    SID22.setTabIndex(44);
    SID22.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SID22.setId("Sticker2 / Supplier");
    bomCassette.addElement(SID22);
    FormTextField INK122 = new FormTextField("INK122", "", false, 2, 2);
    INK122.setTabIndex(45);
    INK122.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK122.setId("Sticker2 / Ink");
    bomCassette.addElement(INK122);
    FormTextField INK222 = new FormTextField("INK222", "", false, 2, 2);
    INK222.setTabIndex(46);
    INK222.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK222.setId("Sticker2 / Ink");
    bomCassette.addElement(INK222);
    FormTextField SEL22 = new FormTextField("SEL22", "", false, 6, 6);
    SEL22.setTabIndex(47);
    SEL22.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SEL22.setId("Sticker2 / Places");
    bomCassette.addElement(SEL22);
    FormTextField INF22 = new FormTextField("INF22", "", false, 20, 120);
    INF22.setTabIndex(48);
    INF22.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF22.setId("Sticker2 / Additional Information");
    bomCassette.addElement(INF22);
    FormCheckBox PID18 = new FormCheckBox("PID18", "on", "", false);
    PID18.setId("PID18");
    PID18.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID18.setTabIndex(49);
    PID18.setId("Other");
    bomCassette.addElement(PID18);
    Vector sid18 = MilestoneHelper.getBomSuppliers(18);
    FormDropDownMenu SID18 = MilestoneHelper.getLookupDropDown("SID18", sid18, "", false, false);
    SID18.setTabIndex(50);
    SID18.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SID18.setId("Other / Supplier");
    bomCassette.addElement(SID18);
    FormTextField INK118 = new FormTextField("INK118", "", false, 2, 2);
    INK118.setTabIndex(51);
    INK118.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK118.setId("Other / Ink");
    bomCassette.addElement(INK118);
    FormTextField INK218 = new FormTextField("INK218", "", false, 2, 2);
    INK218.setTabIndex(52);
    INK218.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK218.setId("Other / Ink");
    bomCassette.addElement(INK218);
    FormTextField INF18 = new FormTextField("INF18", "", false, 50, 120);
    INF18.setTabIndex(53);
    INF18.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF18.setId("Other / Additional Information");
    bomCassette.addElement(INF18);
    return bomCassette;
  }
  
  protected Form buildNewVinyl(Form bomVinyl, Selection selection, Bom bom) {
    FormCheckBox PID19 = new FormCheckBox("PID19", "on", "", false);
    PID19.setId("PID19");
    PID19.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID19.setTabIndex(14);
    PID19.setId("Record");
    bomVinyl.addElement(PID19);
    Vector sid19 = MilestoneHelper.getBomSuppliers(19);
    FormDropDownMenu SID19 = MilestoneHelper.getLookupDropDown("SID19", sid19, "", false, false);
    SID19.setTabIndex(15);
    SID19.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SID19.setId("Record / Supplier");
    bomVinyl.addElement(SID19);
    Vector sel19 = Cache.getInstance().getLookupDetailValuesFromDatabase(46);
    FormDropDownMenu SEL19 = MilestoneHelper.getLookupDropDownBom("SEL19", sel19, "", false, true);
    SEL19.setTabIndex(16);
    SEL19.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SEL19.setId("Record / Color");
    bomVinyl.addElement(SEL19);
    FormTextField INF19 = new FormTextField("INF19", "", false, 20, 120);
    INF19.setTabIndex(17);
    INF19.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF19.setId("Record / Additional Information");
    bomVinyl.addElement(INF19);
    FormCheckBox PID14 = new FormCheckBox("PID14", "on", "", false);
    PID14.setId("PID14");
    PID14.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID14.setTabIndex(18);
    PID14.setId("Label");
    bomVinyl.addElement(PID14);
    Vector sid14 = MilestoneHelper.getBomSuppliers(14);
    FormDropDownMenu SID14 = MilestoneHelper.getLookupDropDown("SID14", sid14, "", false, false);
    SID14.setTabIndex(19);
    SID14.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SID14.setId("Label / Supplier");
    bomVinyl.addElement(SID14);
    FormTextField INK114 = new FormTextField("INK114", "", false, 2, 2);
    INK114.setTabIndex(20);
    INK114.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK114.setId("Label / Ink");
    bomVinyl.addElement(INK114);
    FormTextField INK214 = new FormTextField("INK214", "", false, 2, 2);
    INK214.setTabIndex(21);
    INK214.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK214.setId("Label / Ink");
    bomVinyl.addElement(INK214);
    FormTextField INF14 = new FormTextField("INF14", "", false, 50, 120);
    INF14.setTabIndex(22);
    INF14.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF14.setId("Label / Additional Information");
    bomVinyl.addElement(INF14);
    FormCheckBox PID20 = new FormCheckBox("PID20", "on", "", false);
    PID20.setId("PID20");
    PID20.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID20.setTabIndex(23);
    PID20.setId("Sleeve");
    bomVinyl.addElement(PID20);
    Vector sid20 = MilestoneHelper.getBomSuppliers(20);
    FormDropDownMenu SID20 = MilestoneHelper.getLookupDropDown("SID20", sid20, "", false, false);
    SID20.setTabIndex(24);
    SID20.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SID20.setId("Sleeve / Supplier");
    bomVinyl.addElement(SID20);
    FormTextField INK120 = new FormTextField("INK120", "", false, 2, 2);
    INK120.setTabIndex(25);
    INK120.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK120.setId("Sleeve / Ink");
    bomVinyl.addElement(INK120);
    FormTextField INK220 = new FormTextField("INK220", "", false, 2, 2);
    INK220.setTabIndex(26);
    INK220.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK220.setId("Sleeve / Ink");
    bomVinyl.addElement(INK220);
    FormTextField INF20 = new FormTextField("INF20", "", false, 50, 120);
    INF20.setTabIndex(27);
    INF20.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF20.setId("Sleeve / Additional Information");
    bomVinyl.addElement(INF20);
    FormCheckBox PID11 = new FormCheckBox("PID11", "on", "", false);
    PID11.setId("PID11");
    PID11.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID11.setTabIndex(28);
    PID11.setId("Jacket");
    bomVinyl.addElement(PID11);
    Vector sid11 = MilestoneHelper.getBomSuppliers(11);
    FormDropDownMenu SID11 = MilestoneHelper.getLookupDropDown("SID11", sid11, "", false, false);
    SID11.setTabIndex(29);
    SID11.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SID11.setId("Jacket / Suppliers");
    bomVinyl.addElement(SID11);
    FormTextField INK111 = new FormTextField("INK111", "", false, 2, 2);
    INK111.setTabIndex(30);
    INK111.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK111.setId("Jacket / Ink");
    bomVinyl.addElement(INK111);
    FormTextField INK211 = new FormTextField("INK211", "", false, 2, 2);
    INK211.setTabIndex(31);
    INK211.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK211.setId("Jacket / Ink");
    bomVinyl.addElement(INK211);
    FormTextField INF11 = new FormTextField("INF11", "", false, 50, 120);
    INF11.setTabIndex(32);
    INF11.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF11.setId("Jacket / Additional Information");
    bomVinyl.addElement(INF11);
    FormCheckBox PID33 = new FormCheckBox("PID33", "on", "", false);
    PID33.setId("PID33");
    PID33.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID33.setTabIndex(33);
    PID33.setId("Insert");
    bomVinyl.addElement(PID33);
    Vector sid33 = MilestoneHelper.getBomSuppliers(33);
    FormDropDownMenu SID33 = MilestoneHelper.getLookupDropDown("SID33", sid33, "", false, false);
    SID33.setTabIndex(34);
    SID33.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SID33.setId("Insert / Suppliers");
    bomVinyl.addElement(SID33);
    FormTextField INK133 = new FormTextField("INK133", "", false, 2, 2);
    INK133.setTabIndex(35);
    INK133.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK133.setId("Insert / Ink");
    bomVinyl.addElement(INK133);
    FormTextField INK233 = new FormTextField("INK233", "", false, 2, 2);
    INK233.setTabIndex(36);
    INK233.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK233.setId("Insert / Ink");
    bomVinyl.addElement(INK233);
    FormTextField INF33 = new FormTextField("INF33", "", false, 50, 120);
    INF33.setTabIndex(37);
    INF33.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF33.setId("Insert / Additional Information");
    bomVinyl.addElement(INF33);
    FormCheckBox PID21 = new FormCheckBox("PID21", "on", "", false);
    PID21.setId("PID21");
    PID21.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID21.setTabIndex(38);
    PID21.setId("Sticker1");
    bomVinyl.addElement(PID21);
    Vector sid21 = MilestoneHelper.getBomSuppliers(21, "0,1,3");
    FormDropDownMenu SID21 = MilestoneHelper.getLookupDropDown("SID21", sid21, "", false, false);
    SID21.setTabIndex(39);
    SID21.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SID21.setId("Sticker1 / Supplier");
    bomVinyl.addElement(SID21);
    FormTextField INK121 = new FormTextField("INK121", "", false, 2, 2);
    INK121.setTabIndex(40);
    INK121.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK121.setId("Sticker1 / Ink");
    bomVinyl.addElement(INK121);
    FormTextField INK221 = new FormTextField("INK221", "", false, 2, 2);
    INK221.setTabIndex(41);
    INK221.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK221.setId("Sticker1 / Ink");
    bomVinyl.addElement(INK221);
    FormTextField SEL21 = new FormTextField("SEL21", "", false, 6, 6);
    SEL21.setTabIndex(42);
    SEL21.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SEL21.setId("Sticker1 / Places");
    bomVinyl.addElement(SEL21);
    FormTextField INF21 = new FormTextField("INF21", "", false, 20, 120);
    INF21.setTabIndex(43);
    INF21.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF21.setId("Sticker1 / Additional Information");
    bomVinyl.addElement(INF21);
    FormCheckBox PID22 = new FormCheckBox("PID22", "on", "", false);
    PID22.setId("PID22");
    PID22.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID22.setTabIndex(44);
    PID22.setId("Sticker2");
    bomVinyl.addElement(PID22);
    Vector sid22 = MilestoneHelper.getBomSuppliers(22, "0,1,3");
    FormDropDownMenu SID22 = MilestoneHelper.getLookupDropDown("SID22", sid22, "", false, false);
    SID22.setTabIndex(45);
    SID22.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SID22.setId("Sticker2 / Suppliers");
    bomVinyl.addElement(SID22);
    FormTextField INK122 = new FormTextField("INK122", "", false, 2, 2);
    INK122.setTabIndex(46);
    INK122.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK122.setId("Sticker2 / Ink");
    bomVinyl.addElement(INK122);
    FormTextField INK222 = new FormTextField("INK222", "", false, 2, 2);
    INK222.setTabIndex(47);
    INK222.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK222.setId("Sticker2 / Ink");
    bomVinyl.addElement(INK222);
    FormTextField SEL22 = new FormTextField("SEL22", "", false, 6, 6);
    SEL22.setTabIndex(48);
    SEL22.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SEL22.setId("Sticker2 / Places");
    bomVinyl.addElement(SEL22);
    FormTextField INF22 = new FormTextField("INF22", "", false, 20, 120);
    INF22.setTabIndex(49);
    INF22.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF22.setId("Sticker2 / Additional Information");
    bomVinyl.addElement(INF22);
    FormCheckBox PID18 = new FormCheckBox("PID18", "on", "", false);
    PID18.setId("PID18");
    PID18.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID18.setTabIndex(50);
    PID18.setId("Other");
    bomVinyl.addElement(PID18);
    Vector sid18 = MilestoneHelper.getBomSuppliers(18);
    FormDropDownMenu SID18 = MilestoneHelper.getLookupDropDown("SID18", sid18, "", false, false);
    SID18.setTabIndex(51);
    SID18.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SID18.setId("Other / Supplier");
    bomVinyl.addElement(SID18);
    FormTextField INK118 = new FormTextField("INK118", "", false, 2, 2);
    INK118.setTabIndex(52);
    INK118.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK118.setId("Other / Ink");
    bomVinyl.addElement(INK118);
    FormTextField INK218 = new FormTextField("INK218", "", false, 2, 2);
    INK218.setTabIndex(53);
    INK218.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK218.setId("Other / Ink");
    bomVinyl.addElement(INK218);
    FormTextField INF18 = new FormTextField("INF18", "", false, 50, 120);
    INF18.setTabIndex(54);
    INF18.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF18.setId("Other / Additional Information");
    bomVinyl.addElement(INF18);
    return bomVinyl;
  }
  
  protected Form buildNewDVD(Form bomDVD, Selection selection, Bom bom) {
    Vector selDisc = Cache.getInstance().getLookupDetailValuesFromDatabase(62);
    FormDropDownMenu SELDISC = MilestoneHelper.getLookupDropDownBom("SELDISC", selDisc, "", false, true);
    SELDISC.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SELDISC.setTabIndex(17);
    SELDISC.setId("Disc / Additional Information");
    bomDVD.addElement(SELDISC);
    FormCheckBox PID25 = new FormCheckBox("PID25", "on", "", false);
    PID25.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID25.setTabIndex(21);
    PID25.setId("Wrap");
    bomDVD.addElement(PID25);
    Vector sid25 = MilestoneHelper.getBomSuppliers(29, "1,2");
    FormDropDownMenu SID25 = MilestoneHelper.getLookupDropDown("SID25", sid25, "", false, false);
    SID25.setTabIndex(22);
    SID25.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SID25.setId("Wrap / Supplier");
    bomDVD.addElement(SID25);
    FormTextField INK125 = new FormTextField("INK125", "", false, 2, 2);
    INK125.setTabIndex(23);
    INK125.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK125.setId("Wrap / Ink");
    bomDVD.addElement(INK125);
    FormTextField INK225 = new FormTextField("INK225", "", false, 2, 2);
    INK225.setTabIndex(24);
    INK225.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INK225.setId("Wrap / Ink");
    bomDVD.addElement(INK225);
    FormTextField INF25 = new FormTextField("INF25", "", false, 50, 120);
    INF25.setTabIndex(25);
    INF25.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF25.setId("Wrap / Additional Information");
    bomDVD.addElement(INF25);
    FormCheckBox PID26 = new FormCheckBox("PID26", "on", "", false);
    PID26.setId("PID26");
    PID26.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID26.setTabIndex(25);
    PID26.setId("DVD Case");
    bomDVD.addElement(PID26);
    Vector sel26 = Cache.getInstance().getLookupDetailValuesFromDatabase(64);
    FormDropDownMenu SEL26 = MilestoneHelper.getLookupDropDownBom("SEL26", sel26, "", false, true);
    SEL26.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SEL26.setTabIndex(26);
    SEL26.setId("Disc / Additional Information");
    bomDVD.addElement(SEL26);
    FormTextField INF26 = new FormTextField("INF26", "", false, 20, 120);
    INF26.setTabIndex(26);
    INF26.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF26.setId("DVD Case / Additional Information");
    bomDVD.addElement(INF26);
    FormCheckBox PID32 = new FormCheckBox("PID32", "on", "", false);
    PID32.setId("PID32");
    PID32.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    PID32.setTabIndex(27);
    PID32.setId("Blu-Ray Case");
    bomDVD.addElement(PID32);
    Vector sel32 = Cache.getInstance().getLookupDetailValuesFromDatabase(66);
    FormDropDownMenu SEL32 = MilestoneHelper.getLookupDropDownBom("SEL32", sel32, "", false, true);
    SEL32.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    SEL32.setTabIndex(28);
    SEL32.setId("Blu-Ray Case / Additional Information");
    bomDVD.addElement(SEL32);
    FormTextField INF32 = new FormTextField("INF32", "", false, 20, 120);
    INF32.setTabIndex(29);
    INF32.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    INF32.setId("Blu-Ray Case / Additional Information");
    bomDVD.addElement(INF32);
    return buildNewDisk(bomDVD, selection, bom);
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
      if (command.indexOf("new") > -1 || command.indexOf("copy") > -1) {
        saveVisible = "true";
        copyVisible = "false";
        deleteVisible = "false";
        newVisible = "false";
      } 
    } 
    context.putDelivery("saveVisible", saveVisible);
    context.putDelivery("copyVisible", copyVisible);
    context.putDelivery("deleteVisible", deleteVisible);
    context.putDelivery("newVisible", newVisible);
  }
  
  public static int getSelectionBomPermissions(Selection selection, User user) {
    int level = 0;
    if (selection != null && selection.getSelectionID() > -1) {
      Environment env = selection.getEnvironment();
      CompanyAcl companyAcl = MilestoneHelper.getScreenPermissions(env, user);
      if (companyAcl != null)
        level = companyAcl.getAccessBomForm(); 
    } 
    return level;
  }
  
  public boolean print(Dispatcher dispatcher, Context context, String command, int pdfRtf, boolean ie55, MessageObject messageObject) {
    Selection selection = null;
    if (command.equalsIgnoreCase("selectionSave")) {
      selection = (Selection)context.getSessionValue("pfmBomSelection");
    } else {
      selection = MilestoneHelper.getScreenSelection(context);
    } 
    if (messageObject != null && messageObject.selectionObj != null)
      selection = messageObject.selectionObj; 
    Bom bom = null;
    Form reportContent = buildNewForm(context, selection, "bom-print-pdf");
    reportContent.setValues(context);
    if (selection != null)
      bom = SelectionManager.getInstance().getBom(selection); 
    if (messageObject != null && messageObject.bomObj != null)
      bom = messageObject.bomObj; 
    if (bom != null && reportContent != null) {
      String reportTemplate = String.valueOf(ReportHandler.reportPath) + "\\";
      String configuration = selection.getSelectionConfig().getSelectionConfigurationAbbreviation();
      if (configuration.equalsIgnoreCase("CAS")) {
        reportTemplate = String.valueOf(reportTemplate) + "bom_cassette.xml";
      } else if (configuration.equalsIgnoreCase("VIN")) {
        reportTemplate = String.valueOf(reportTemplate) + "bom_vinyl.xml";
      } else {
        reportTemplate = String.valueOf(reportTemplate) + "bom_disc.xml";
      } 
      try {
        InputStream input = new FileInputStream(reportTemplate);
        XStyleSheet report = 
          (XStyleSheet)Builder.getBuilder(1, input).read(null);
        if (report != null)
          fillReportForPrint(report, reportContent, selection, context); 
        if (pdfRtf == 0) {
          HttpServletResponse sresponse = context.getResponse();
          String reportFilename = "report.pdf";
          if (ie55) {
            sresponse.setHeader("extension", "pdf");
            sresponse.setContentType("application/pdf");
            sresponse.setHeader("Content-disposition", "attachment; filename=" + reportFilename);
          } else {
            sresponse.setHeader("extension", "pdf");
            sresponse.setContentType("application/force-download");
            sresponse.setHeader("Content-disposition", "attachment; filename=" + reportFilename);
          } 
          ServletOutputStream servletOutputStream = sresponse.getOutputStream();
          servletOutputStream.flush();
          PDF4Generator pdfGenerator = new PDF4Generator(servletOutputStream);
          pdfGenerator.generate(report);
          servletOutputStream.close();
        } 
        if (pdfRtf == 2) {
          String prefix = "";
          if (selection.getPrefixID() != null && selection.getPrefixID().getAbbreviation() != null)
            prefix = selection.getPrefixID().getAbbreviation(); 
          EmailDistribution.generateFormReport(context, "BOM", report, selection.getSelectionNo(), 
              prefix, selection.getUpc(), selection.getIsDigital(), messageObject);
        } 
        return true;
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    return edit(dispatcher, context, "");
  }
  
  private void fillReportForPrint(XStyleSheet report, Form form, Selection selection, Context context) {
    SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
    String todayLong = formatter.format(new Date());
    report.setElement("bom_top_date", todayLong);
    Bom bom = (Bom)context.getSessionValue("Bom");
    if (selection != null) {
      int selectionID = selection.getSelectionID();
      bom = SelectionManager.getInstance().getBom(selection);
    } 
    if (bom != null) {
      boolean isCDoverride = false;
      if (bom.getFormat().equalsIgnoreCase("CDO"))
        isCDoverride = true; 
      DefaultTableLens table_contents = null;
      String configuration = selection.getSelectionConfig().getSelectionConfigurationAbbreviation();
      if (configuration != null && configuration.equalsIgnoreCase("CAS")) {
        table_contents = new DefaultTableLens(25, 20);
      } else if (configuration != null && configuration.equalsIgnoreCase("VIN")) {
        table_contents = new DefaultTableLens(25, 20);
      } else if (configuration != null && configuration.equalsIgnoreCase("DVV") && 
        !isCDoverride) {
        table_contents = new DefaultTableLens(35, 20);
      } else {
        table_contents = new DefaultTableLens(33, 20);
      } 
      table_contents.setHeaderRowCount(0);
      table_contents.setColWidth(0, 16);
      table_contents.setColWidth(1, 2);
      table_contents.setColWidth(2, 15);
      table_contents.setColWidth(3, 40);
      table_contents.setColWidth(4, 2);
      table_contents.setColWidth(5, 60);
      table_contents.setColWidth(6, 40);
      table_contents.setColWidth(7, 40);
      table_contents.setColWidth(8, 2);
      table_contents.setColWidth(9, 50);
      table_contents.setColWidth(10, 10);
      table_contents.setColWidth(11, 10);
      table_contents.setColWidth(12, 20);
      table_contents.setColWidth(13, 50);
      table_contents.setColWidth(14, 60);
      table_contents.setColWidth(15, 40);
      table_contents.setColWidth(16, 80);
      table_contents.setColWidth(17, 30);
      table_contents.setColWidth(18, 20);
      table_contents.setColWidth(19, 30);
      Font boldFont = new Font("Arial", 1, 10);
      Font plainFont = new Font("Arial", 0, 10);
      table_contents.setRowBorder(-1, 0);
      table_contents.setColBorder(0);
      table_contents.setRowBorderColor(Color.lightGray);
      table_contents.setRowBorder(266240);
      table_contents.setColFont(0, boldFont);
      table_contents.setColAlignment(0, 33);
      table_contents.setColAlignment(1, 33);
      table_contents.setColAlignment(2, 33);
      table_contents.setColAlignment(4, 33);
      table_contents.setColAlignment(3, 33);
      table_contents.setColAlignment(5, 33);
      table_contents.setColAlignment(6, 33);
      table_contents.setColAlignment(7, 33);
      table_contents.setColAlignment(8, 33);
      table_contents.setColAlignment(9, 33);
      table_contents.setColAlignment(10, 33);
      table_contents.setColAlignment(11, 33);
      table_contents.setColAlignment(12, 33);
      table_contents.setColAlignment(14, 33);
      table_contents.setColAlignment(13, 33);
      table_contents.setColAlignment(15, 33);
      table_contents.setColAlignment(16, 33);
      table_contents.setColAlignment(17, 33);
      table_contents.setColAlignment(18, 33);
      table_contents.setColAlignment(19, 33);
      int nextRow = 0;
      String isAdded = bom.getType();
      if (isAdded != null && isAdded.equalsIgnoreCase("A")) {
        isAdded = "A";
      } else {
        isAdded = "C";
      } 
      table_contents.setRowBorderColor(nextRow, 0, Color.white);
      table_contents.setRowBorderColor(nextRow, 1, Color.white);
      table_contents.setRowBorderColor(nextRow, 2, Color.white);
      table_contents.setRowBorderColor(nextRow, 3, Color.white);
      table_contents.setRowBorderColor(nextRow, 4, Color.white);
      table_contents.setRowBorderColor(nextRow, 6, Color.white);
      table_contents.setRowBorderColor(nextRow, 7, Color.white);
      table_contents.setRowBorderColor(nextRow, 8, Color.white);
      table_contents.setRowBorderColor(nextRow, 9, Color.white);
      table_contents.setRowBorderColor(nextRow, 10, Color.white);
      table_contents.setRowBorderColor(nextRow, 11, Color.white);
      table_contents.setRowBorderColor(nextRow, 12, Color.white);
      table_contents.setRowBorderColor(nextRow, 14, Color.white);
      table_contents.setRowBorderColor(nextRow, 15, Color.white);
      table_contents.setRowBorderColor(nextRow, 16, Color.white);
      table_contents.setRowBorderColor(nextRow, 17, Color.white);
      table_contents.setRowBorderColor(nextRow, 18, Color.white);
      table_contents.setRowBorderColor(nextRow, 19, Color.white);
      table_contents.setRowHeight(nextRow, 18);
      table_contents.setFont(nextRow, 0, boldFont);
      table_contents.setSpan(nextRow, 0, new Dimension(5, 1));
      table_contents.setObject(nextRow, 0, "Date:");
      table_contents.setFont(nextRow, 5, plainFont);
      table_contents.setObject(nextRow, 5, form.getStringValue("Date"));
      table_contents.setFont(nextRow, 9, boldFont);
      table_contents.setSpan(nextRow, 9, new Dimension(4, 1));
      table_contents.setAlignment(nextRow, 9, 33);
      table_contents.setObject(nextRow, 9, "Type:");
      table_contents.setObject(nextRow, 13, isAdded);
      table_contents.setFont(nextRow, 13, plainFont);
      table_contents.setAlignment(nextRow, 13, 34);
      nextRow++;
      String changeNumber = "";
      if (isAdded == null || !isAdded.equalsIgnoreCase("add"))
        changeNumber = bom.getChangeNumber(); 
      table_contents.setRowBorderColor(nextRow, 0, Color.white);
      table_contents.setRowBorderColor(nextRow, 1, Color.white);
      table_contents.setRowBorderColor(nextRow, 2, Color.white);
      table_contents.setRowBorderColor(nextRow, 3, Color.white);
      table_contents.setRowBorderColor(nextRow, 4, Color.white);
      table_contents.setRowBorderColor(nextRow, 5, Color.white);
      table_contents.setRowBorderColor(nextRow, 6, Color.white);
      table_contents.setRowBorderColor(nextRow, 7, Color.white);
      table_contents.setRowBorderColor(nextRow, 8, Color.white);
      table_contents.setRowBorderColor(nextRow, 9, Color.white);
      table_contents.setRowBorderColor(nextRow, 10, Color.white);
      table_contents.setRowBorderColor(nextRow, 11, Color.white);
      table_contents.setRowBorderColor(nextRow, 12, Color.white);
      table_contents.setRowBorderColor(nextRow, 14, Color.white);
      table_contents.setRowBorderColor(nextRow, 15, Color.white);
      table_contents.setRowBorderColor(nextRow, 16, Color.white);
      table_contents.setRowBorderColor(nextRow, 17, Color.white);
      table_contents.setRowBorderColor(nextRow, 18, Color.white);
      table_contents.setRowBorderColor(nextRow, 19, Color.white);
      table_contents.setRowHeight(nextRow, 18);
      table_contents.setFont(nextRow, 9, boldFont);
      table_contents.setSpan(nextRow, 9, new Dimension(4, 1));
      table_contents.setAlignment(nextRow, 9, 33);
      table_contents.setObject(nextRow, 9, "Change Number:");
      table_contents.setObject(nextRow, 13, changeNumber);
      table_contents.setFont(nextRow, 13, plainFont);
      table_contents.setAlignment(nextRow, 13, 34);
      nextRow++;
      table_contents.setRowHeight(nextRow, 5);
      table_contents.setRowBorderColor(nextRow, Color.black);
      nextRow++;
      table_contents.setRowBorderColor(nextRow, 0, Color.white);
      table_contents.setRowBorderColor(nextRow, 1, Color.white);
      table_contents.setRowBorderColor(nextRow, 2, Color.white);
      table_contents.setRowBorderColor(nextRow, 3, Color.white);
      table_contents.setRowBorderColor(nextRow, 10, Color.white);
      table_contents.setRowBorderColor(nextRow, 11, Color.white);
      table_contents.setRowBorderColor(nextRow, 12, Color.white);
      table_contents.setRowHeight(nextRow, 18);
      table_contents.setSpan(nextRow, 0, new Dimension(4, 1));
      table_contents.setFont(nextRow, 0, boldFont);
      table_contents.setAlignment(nextRow, 0, 33);
      table_contents.setObject(nextRow, 0, "Submitted by:");
      table_contents.setSpan(nextRow, 4, new Dimension(6, 1));
      table_contents.setFont(nextRow, 4, plainFont);
      table_contents.setObject(nextRow, 4, replaceQuote(bom.getSubmitter()));
      table_contents.setSpan(nextRow, 10, new Dimension(3, 1));
      table_contents.setFont(nextRow, 10, boldFont);
      table_contents.setAlignment(nextRow, 10, 36);
      table_contents.setObject(nextRow, 10, "Phone:");
      table_contents.setSpan(nextRow, 13, new Dimension(7, 1));
      table_contents.setFont(nextRow, 13, plainFont);
      table_contents.setObject(nextRow, 13, bom.getPhone());
      nextRow++;
      table_contents.setRowBorderColor(nextRow, 0, Color.white);
      table_contents.setRowBorderColor(nextRow, 1, Color.white);
      table_contents.setRowBorderColor(nextRow, 2, Color.white);
      table_contents.setRowBorderColor(nextRow, 3, Color.white);
      table_contents.setRowBorderColor(nextRow, 10, Color.white);
      table_contents.setRowBorderColor(nextRow, 11, Color.white);
      table_contents.setRowBorderColor(nextRow, 12, Color.white);
      table_contents.setRowBorderColor(nextRow, 13, Color.white);
      table_contents.setRowBorderColor(nextRow, 14, Color.white);
      table_contents.setRowBorderColor(nextRow, 15, Color.white);
      table_contents.setRowBorderColor(nextRow, 16, Color.white);
      table_contents.setRowBorderColor(nextRow, 17, Color.white);
      table_contents.setRowBorderColor(nextRow, 18, Color.white);
      table_contents.setRowBorderColor(nextRow, 19, Color.white);
      table_contents.setRowHeight(nextRow, 18);
      table_contents.setSpan(nextRow, 0, new Dimension(4, 1));
      table_contents.setFont(nextRow, 0, boldFont);
      table_contents.setAlignment(nextRow, 0, 33);
      table_contents.setObject(nextRow, 0, "E-mail:");
      table_contents.setSpan(nextRow, 4, new Dimension(6, 1));
      table_contents.setFont(nextRow, 4, plainFont);
      table_contents.setObject(nextRow, 4, bom.getEmail());
      nextRow++;
      table_contents.setRowHeight(nextRow, 5);
      table_contents.setRowBorderColor(nextRow, Color.black);
      nextRow++;
      String companyID = bom.getReleasingCompanyId();
      Vector companyList = Cache.getReleaseCompanies();
      String companyName = "";
      for (int i = 0; i < companyList.size(); i++) {
        if (((LookupObject)companyList.get(i)).getAbbreviation().equals(companyID))
          companyName = ((LookupObject)companyList.get(i)).getName(); 
      } 
      table_contents.setRowBorderColor(nextRow, 0, Color.white);
      table_contents.setRowBorderColor(nextRow, 1, Color.white);
      table_contents.setRowBorderColor(nextRow, 2, Color.white);
      table_contents.setRowBorderColor(nextRow, 3, Color.white);
      table_contents.setRowBorderColor(nextRow, 10, Color.white);
      table_contents.setRowBorderColor(nextRow, 11, Color.white);
      table_contents.setRowBorderColor(nextRow, 12, Color.white);
      table_contents.setRowInsets(nextRow, new Insets(1, 0, 0, 0));
      table_contents.setSpan(nextRow, 0, new Dimension(4, 1));
      table_contents.setFont(nextRow, 0, boldFont);
      table_contents.setAlignment(nextRow, 0, 33);
      table_contents.setObject(nextRow, 0, "Artist:");
      table_contents.setSpan(nextRow, 0, new Dimension(4, 1));
      table_contents.setSpan(nextRow, 4, new Dimension(4, 1));
      table_contents.setFont(nextRow, 4, plainFont);
      table_contents.setObject(nextRow, 4, selection.getArtist());
      table_contents.setRowAutoSize(true);
      table_contents.setColAutoSize(true);
      boolean isBomRetail = bom.isRetail();
      String isPromo = "";
      String isRetail = "";
      if (!isBomRetail) {
        isRetail = "";
        isPromo = "X";
      } else {
        isPromo = "";
        isRetail = "X";
      } 
      table_contents.setRowInsets(nextRow, new Insets(1, 0, 0, 0));
      table_contents.setSpan(nextRow, 10, new Dimension(4, 1));
      table_contents.setFont(nextRow, 10, boldFont);
      table_contents.setAlignment(nextRow, 10, 36);
      table_contents.setObject(nextRow, 10, "Commercial:");
      table_contents.setRowBorderColor(nextRow, 13, Color.white);
      table_contents.setAlignment(nextRow, 14, 34);
      table_contents.setFont(nextRow, 14, plainFont);
      table_contents.setObject(nextRow, 14, isRetail);
      table_contents.setRowBorderColor(nextRow, 15, Color.white);
      table_contents.setRowBorderColor(nextRow, 16, Color.white);
      table_contents.setSpan(nextRow, 15, new Dimension(2, 1));
      table_contents.setFont(nextRow, 15, boldFont);
      table_contents.setAlignment(nextRow, 15, 36);
      table_contents.setObject(nextRow, 15, "Promotional:");
      table_contents.setRowBorderColor(nextRow, 19, Color.white);
      table_contents.setAlignment(nextRow, 17, 34);
      table_contents.setFont(nextRow, 17, plainFont);
      table_contents.setObject(nextRow, 17, isPromo);
      nextRow++;
      table_contents.setRowBorderColor(nextRow, 0, Color.white);
      table_contents.setRowBorderColor(nextRow, 1, Color.white);
      table_contents.setRowBorderColor(nextRow, 2, Color.white);
      table_contents.setRowBorderColor(nextRow, 3, Color.white);
      table_contents.setRowBorderColor(nextRow, 10, Color.white);
      table_contents.setRowBorderColor(nextRow, 11, Color.white);
      table_contents.setRowBorderColor(nextRow, 12, Color.white);
      table_contents.setRowInsets(nextRow, new Insets(1, 0, 0, 0));
      table_contents.setSpan(nextRow, 0, new Dimension(4, 1));
      table_contents.setFont(nextRow, 0, boldFont);
      table_contents.setAlignment(nextRow, 0, 33);
      table_contents.setObject(nextRow, 0, "Title:");
      table_contents.setSpan(nextRow, 0, new Dimension(4, 1));
      table_contents.setSpan(nextRow, 4, new Dimension(4, 1));
      table_contents.setFont(nextRow, 4, plainFont);
      table_contents.setObject(nextRow, 4, selection.getTitle());
      table_contents.setRowAutoSize(true);
      table_contents.setColAutoSize(true);
      table_contents.setRowBorderColor(nextRow, 13, Color.white);
      table_contents.setSpan(nextRow, 10, new Dimension(4, 1));
      table_contents.setFont(nextRow, 10, boldFont);
      table_contents.setAlignment(nextRow, 10, 36);
      table_contents.setObject(nextRow, 10, "Street/Ship Date:");
      table_contents.setSpan(nextRow, 14, new Dimension(6, 1));
      table_contents.setFont(nextRow, 14, plainFont);
      table_contents.setObject(nextRow, 14, MilestoneHelper.getFormatedDate(selection.getStreetDate()));
      nextRow++;
      table_contents.setRowBorderColor(nextRow, 0, Color.white);
      table_contents.setRowBorderColor(nextRow, 1, Color.white);
      table_contents.setRowBorderColor(nextRow, 2, Color.white);
      table_contents.setRowBorderColor(nextRow, 3, Color.white);
      table_contents.setRowBorderColor(nextRow, 10, Color.white);
      table_contents.setRowBorderColor(nextRow, 11, Color.white);
      table_contents.setRowBorderColor(nextRow, 12, Color.white);
      table_contents.setRowInsets(nextRow, new Insets(1, 0, 0, 0));
      table_contents.setSpan(nextRow, 0, new Dimension(4, 1));
      table_contents.setFont(nextRow, 0, boldFont);
      table_contents.setAlignment(nextRow, 0, 33);
      table_contents.setObject(nextRow, 0, "Releasing Family:");
      table_contents.setSpan(nextRow, 4, new Dimension(4, 1));
      table_contents.setFont(nextRow, 4, plainFont);
      table_contents.setObject(nextRow, 4, selection.getFamily().getName());
      table_contents.setRowBorderColor(nextRow, 13, Color.white);
      table_contents.setSpan(nextRow, 10, new Dimension(4, 1));
      table_contents.setFont(nextRow, 10, boldFont);
      table_contents.setAlignment(nextRow, 10, 36);
      table_contents.setObject(nextRow, 10, "Status:");
      table_contents.setSpan(nextRow, 14, new Dimension(6, 1));
      table_contents.setFont(nextRow, 14, plainFont);
      table_contents.setObject(nextRow, 14, selection.getSelectionStatus().getName());
      nextRow++;
      table_contents.setRowBorderColor(nextRow, 0, Color.white);
      table_contents.setRowBorderColor(nextRow, 1, Color.white);
      table_contents.setRowBorderColor(nextRow, 2, Color.white);
      table_contents.setRowBorderColor(nextRow, 3, Color.white);
      table_contents.setRowBorderColor(nextRow, 10, Color.white);
      table_contents.setRowBorderColor(nextRow, 11, Color.white);
      table_contents.setRowBorderColor(nextRow, 12, Color.white);
      table_contents.setRowInsets(nextRow, new Insets(1, 0, 0, 0));
      table_contents.setSpan(nextRow, 0, new Dimension(4, 1));
      table_contents.setFont(nextRow, 0, boldFont);
      table_contents.setAlignment(nextRow, 0, 33);
      table_contents.setObject(nextRow, 0, "Label:");
      table_contents.setSpan(nextRow, 4, new Dimension(4, 1));
      table_contents.setFont(nextRow, 4, plainFont);
      table_contents.setObject(nextRow, 4, selection.getLabel().getName());
      String upc = "";
      if (bom != null && bom.getUpc() != null)
        upc = bom.getUpc(); 
      table_contents.setSpan(nextRow, 12, new Dimension(2, 1));
      table_contents.setFont(nextRow, 12, boldFont);
      table_contents.setAlignment(nextRow, 12, 36);
      table_contents.setObject(nextRow, 12, "UPC:");
      table_contents.setSpan(nextRow, 14, new Dimension(4, 1));
      table_contents.setAlignment(nextRow, 14, 33);
      table_contents.setFont(nextRow, 14, plainFont);
      table_contents.setObject(nextRow, 14, MilestoneHelper_2.getRMSReportFormat(upc, "UPC", selection.getIsDigital()));
      table_contents.setRowBorderColor(nextRow, 13, Color.white);
      nextRow++;
      table_contents.setRowBorderColor(nextRow, 0, Color.white);
      table_contents.setRowBorderColor(nextRow, 1, Color.white);
      table_contents.setRowBorderColor(nextRow, 2, Color.white);
      table_contents.setRowBorderColor(nextRow, 3, Color.white);
      table_contents.setRowBorderColor(nextRow, 10, Color.white);
      table_contents.setRowBorderColor(nextRow, 11, Color.white);
      table_contents.setRowBorderColor(nextRow, 12, Color.white);
      table_contents.setRowInsets(nextRow, new Insets(1, 0, 0, 0));
      table_contents.setSpan(nextRow, 0, new Dimension(4, 1));
      table_contents.setFont(nextRow, 0, boldFont);
      table_contents.setAlignment(nextRow, 0, 33);
      table_contents.setObject(nextRow, 0, "Imprint:");
      table_contents.setSpan(nextRow, 4, new Dimension(4, 1));
      table_contents.setFont(nextRow, 4, plainFont);
      table_contents.setObject(nextRow, 4, selection.getImprint());
      table_contents.setRowBorderColor(nextRow, 13, Color.white);
      String prefix = SelectionManager.getLookupObjectValue(selection.getPrefixID());
      if (!prefix.equals(""))
        prefix = String.valueOf(prefix) + " "; 
      table_contents.setSpan(nextRow, 10, new Dimension(4, 1));
      table_contents.setFont(nextRow, 10, boldFont);
      table_contents.setAlignment(nextRow, 10, 36);
      table_contents.setObject(nextRow, 10, "Local Prod #:");
      table_contents.setSpan(nextRow, 14, new Dimension(6, 1));
      table_contents.setFont(nextRow, 14, plainFont);
      table_contents.setObject(nextRow, 14, String.valueOf(prefix) + selection.getSelectionNo());
      nextRow++;
      table_contents.setRowHeight(nextRow, 5);
      table_contents.setRowBorderColor(nextRow, Color.black);
      nextRow++;
      nextRow++;
      table_contents.setRowHeight(nextRow, 5);
      table_contents.setRowBorderColor(nextRow, Color.black);
      nextRow++;
      int updateUserId = bom.getModifiedBy();
      User updateUser = UserManager.getInstance().getUser(updateUserId);
      if (updateUser != null) {
        String updateUserName = updateUser.getName();
        if (updateUserName != null)
          report.setElement("bom_lastupdatedby", updateUserName); 
      } 
      report.setElement("bom_lastupdated", MilestoneHelper.getFormatedDate(bom.getModifiedOn()));
      if (configuration != null && configuration.equalsIgnoreCase("CAS")) {
        addCassetteReportElements(report, bom, context, table_contents, selection);
      } else if (configuration != null && configuration.equalsIgnoreCase("VIN")) {
        addVinylReportElements(report, bom, context, table_contents, selection);
      } else if (configuration != null && configuration.equalsIgnoreCase("DVV") && 
        !isCDoverride) {
        addDiscReportElements(report, bom, context, table_contents, selection, true);
      } else {
        addDiscReportElements(report, bom, context, table_contents, selection, false);
      } 
    } 
  }
  
  private void addDiscReportElements(XStyleSheet report, Bom bom, Context context, DefaultTableLens table_contents, Selection mySelection, boolean isDVD) {
    if (bom != null) {
      BomDiskDetail bomDiskDetail;
      if (isDVD) {
        bomDiskDetail = bom.getBomDVDDetail();
      } else {
        bomDiskDetail = bom.getBomDiskDetail();
      } 
      table_contents.setRowInsets(10, new Insets(1, 0, 0, 0));
      table_contents.setRowInsets(11, new Insets(1, 0, 0, 0));
      table_contents.setRowInsets(12, new Insets(1, 0, 0, 0));
      table_contents.setRowInsets(13, new Insets(1, 0, 0, 0));
      table_contents.setRowInsets(14, new Insets(1, 0, 0, 0));
      table_contents.setRowInsets(15, new Insets(1, 0, 0, 0));
      table_contents.setRowInsets(16, new Insets(1, 0, 0, 0));
      table_contents.setRowInsets(17, new Insets(1, 0, 0, 0));
      table_contents.setRowInsets(18, new Insets(1, 0, 0, 0));
      table_contents.setRowInsets(19, new Insets(1, 0, 0, 0));
      table_contents.setRowInsets(20, new Insets(1, 0, 0, 0));
      table_contents.setRowInsets(21, new Insets(1, 0, 0, 0));
      table_contents.setRowInsets(22, new Insets(1, 0, 0, 0));
      table_contents.setRowInsets(23, new Insets(1, 0, 0, 0));
      table_contents.setRowInsets(24, new Insets(1, 0, 0, 0));
      table_contents.setRowInsets(25, new Insets(1, 0, 0, 0));
      table_contents.setRowInsets(26, new Insets(1, 0, 0, 0));
      table_contents.setRowInsets(27, new Insets(1, 0, 0, 0));
      table_contents.setRowInsets(28, new Insets(1, 0, 0, 0));
      table_contents.setRowInsets(29, new Insets(1, 0, 0, 0));
      table_contents.setRowInsets(30, new Insets(1, 0, 0, 0));
      int nextRow = 12;
      Font boldFont = new Font("Arial", 1, 10);
      Font plainFont = new Font("Arial", 0, 9);
      String stringUnitsPerPkg = "";
      if (mySelection.getNumberOfUnits() > 0 && 
        !Integer.toString(mySelection.getNumberOfUnits()).equals(""))
        stringUnitsPerPkg = Integer.toString(mySelection.getNumberOfUnits()); 
      String noSpine = "";
      if (bom.hasSpineSticker()) {
        noSpine = "X";
      } else {
        noSpine = "";
      } 
      String noShrinkwrap = context.getRequestValue("UseNoShrinkWrap");
      if (bom.useShrinkWrap()) {
        noShrinkwrap = "X";
      } else {
        noShrinkwrap = "";
      } 
      table_contents.setRowInsets(nextRow, new Insets(1, 0, 0, 0));
      table_contents.setRowBorderColor(nextRow, 0, Color.white);
      table_contents.setRowBorderColor(nextRow, 1, Color.white);
      table_contents.setRowBorderColor(nextRow, 2, Color.white);
      table_contents.setRowBorderColor(nextRow, 3, Color.white);
      table_contents.setRowBorderColor(nextRow, 4, Color.white);
      table_contents.setRowBorderColor(nextRow, 5, Color.white);
      table_contents.setRowBorderColor(nextRow, 6, Color.white);
      table_contents.setRowBorderColor(nextRow, 7, Color.white);
      table_contents.setRowBorderColor(nextRow, 8, Color.white);
      table_contents.setRowBorderColor(nextRow, 9, Color.white);
      table_contents.setRowBorderColor(nextRow, 12, Color.white);
      table_contents.setRowBorderColor(nextRow, 13, Color.white);
      table_contents.setRowBorderColor(nextRow, 14, Color.white);
      table_contents.setRowBorderColor(nextRow, 16, Color.white);
      table_contents.setRowBorderColor(nextRow, 19, Color.white);
      table_contents.setSpan(nextRow, 0, new Dimension(7, 1));
      table_contents.setFont(nextRow, 0, boldFont);
      table_contents.setAlignment(nextRow, 0, 33);
      if (isDVD) {
        table_contents.setObject(nextRow, 0, "DVD Video");
      } else {
        table_contents.setObject(nextRow, 0, "Disc");
      } 
      table_contents.setSpan(nextRow, 7, new Dimension(3, 1));
      table_contents.setFont(nextRow, 7, boldFont);
      table_contents.setAlignment(nextRow, 7, 36);
      table_contents.setObject(nextRow, 7, "# of Units:");
      table_contents.setSpan(nextRow, 10, new Dimension(2, 1));
      table_contents.setAlignment(nextRow, 10, 34);
      table_contents.setFont(nextRow, 10, plainFont);
      table_contents.setObject(nextRow, 10, stringUnitsPerPkg);
      table_contents.setSpan(nextRow, 13, new Dimension(2, 1));
      table_contents.setFont(nextRow, 13, boldFont);
      table_contents.setAlignment(nextRow, 13, 36);
      table_contents.setObject(nextRow, 13, "Spine Sticker:");
      table_contents.setFont(nextRow, 15, new Font("Arial", 0, 10));
      table_contents.setObject(nextRow, 15, noSpine);
      table_contents.setAlignment(nextRow, 15, 34);
      table_contents.setFont(nextRow, 16, boldFont);
      table_contents.setAlignment(nextRow, 16, 36);
      table_contents.setObject(nextRow, 16, "Shrink Wrap:");
      table_contents.setSpan(nextRow, 17, new Dimension(2, 1));
      table_contents.setFont(nextRow, 17, new Font("Arial", 0, 10));
      table_contents.setObject(nextRow, 17, noShrinkwrap);
      table_contents.setAlignment(nextRow, 17, 34);
      nextRow++;
      table_contents.setRowHeight(nextRow, 5);
      table_contents.setRowBorderColor(nextRow, Color.black);
      nextRow++;
      table_contents.setRowInsets(nextRow, new Insets(1, 0, 0, 0));
      table_contents.setRowBorderColor(nextRow, 0, Color.white);
      table_contents.setRowBorderColor(nextRow, 1, Color.white);
      table_contents.setRowBorderColor(nextRow, 2, Color.white);
      table_contents.setRowBorderColor(nextRow, 3, Color.white);
      table_contents.setRowBorderColor(nextRow, 4, Color.white);
      table_contents.setRowBorderColor(nextRow, 5, Color.white);
      table_contents.setRowBorderColor(nextRow, 6, Color.white);
      table_contents.setRowBorderColor(nextRow, 7, Color.white);
      table_contents.setRowBorderColor(nextRow, 8, Color.white);
      table_contents.setRowBorderColor(nextRow, 9, Color.white);
      table_contents.setRowBorderColor(nextRow, 10, Color.white);
      table_contents.setRowBorderColor(nextRow, 11, Color.white);
      table_contents.setRowBorderColor(nextRow, 12, Color.white);
      table_contents.setRowBorderColor(nextRow, 13, Color.white);
      table_contents.setRowBorderColor(nextRow, 14, Color.white);
      table_contents.setRowBorderColor(nextRow, 15, Color.white);
      table_contents.setRowBorderColor(nextRow, 16, Color.white);
      table_contents.setRowBorderColor(nextRow, 17, Color.white);
      table_contents.setRowBorderColor(nextRow, 18, Color.white);
      table_contents.setRowBorderColor(nextRow, 19, Color.white);
      table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
      table_contents.setFont(nextRow, 2, boldFont);
      table_contents.setAlignment(nextRow, 2, 33);
      table_contents.setObject(nextRow, 2, "Part");
      table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
      table_contents.setAlignment(nextRow, 5, 33);
      table_contents.setFont(nextRow, 5, boldFont);
      table_contents.setObject(nextRow, 5, "Supplier");
      table_contents.setFont(nextRow, 9, boldFont);
      table_contents.setAlignment(nextRow, 9, 34);
      table_contents.setObject(nextRow, 9, "Ink");
      table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
      table_contents.setFont(nextRow, 12, boldFont);
      table_contents.setObject(nextRow, 12, "Additional Information");
      table_contents.setAlignment(nextRow, 12, 33);
      nextRow++;
      double addInfoWidth = 4.222222328186035D;
      double addInfoLineHeight = 0.2083333283662796D;
      int charsPerLine = 85;
      String discCheck = "";
      if (bomDiskDetail.discStatusIndicator) {
        discCheck = "x";
      } else {
        discCheck = "";
      } 
      String ink17 = bomDiskDetail.discInk1;
      String ink27 = bomDiskDetail.discInk2;
      if (ink17 != null && ink17.trim().length() < 2) {
        ink17 = ink17.trim();
        if (ink17.length() == 1) {
          ink17 = " " + ink17;
        } else if (ink17.length() == 0) {
          ink17 = "  ";
        } 
      } 
      if (ink27 != null && ink27.trim().length() < 2) {
        ink27 = ink27.trim();
        if (ink27.length() == 1) {
          ink27 = " " + ink27;
        } else if (ink27.length() == 0) {
          ink27 = "  ";
        } 
      } 
      String bomDiskInk1 = String.valueOf(ink17) + "/" + ink27;
      if (isDVD) {
        addInfoText = String.valueOf(((BomDVDDetail)bomDiskDetail).discSelectionInfo) + " " + bomDiskDetail.discInfo;
      } else {
        addInfoText = bomDiskDetail.discInfo;
      } 
      String partdisc = bomDiskDetail.discSupplier;
      TextBoxElement addInfoTextBox = (TextBoxElement)report.getElement("bom_disc_additionalinfo");
      table_contents.setRowBorderColor(nextRow, 1, Color.white);
      table_contents.setRowBorderColor(nextRow, 4, Color.white);
      table_contents.setRowBorderColor(nextRow, 8, Color.white);
      table_contents.setRowBorderColor(nextRow, 10, Color.white);
      table_contents.setRowBorderColor(nextRow, 11, Color.white);
      table_contents.setFont(nextRow, 0, boldFont);
      table_contents.setAlignment(nextRow, 0, 34);
      table_contents.setObject(nextRow, 0, replaceQuote(discCheck));
      table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
      table_contents.setFont(nextRow, 2, plainFont);
      table_contents.setAlignment(nextRow, 2, 33);
      table_contents.setObject(nextRow, 2, "Disc");
      table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
      table_contents.setAlignment(nextRow, 5, 33);
      table_contents.setFont(nextRow, 5, plainFont);
      table_contents.setObject(nextRow, 5, partdisc);
      table_contents.setFont(nextRow, 9, plainFont);
      table_contents.setAlignment(nextRow, 9, 34);
      table_contents.setObject(nextRow, 9, replaceQuote(bomDiskInk1));
      table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
      table_contents.setFont(nextRow, 12, plainFont);
      table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
      table_contents.setAlignment(nextRow, 12, 33);
      nextRow++;
      String jewelboxCheck = "";
      if (bomDiskDetail.jewelStatusIndicator) {
        jewelboxCheck = "x";
      } else {
        jewelboxCheck = "";
      } 
      String selectedJewelbox = bomDiskDetail.jewelColor;
      selectedJewelbox = (selectedJewelbox == null || selectedJewelbox.length() <= 1) ? 
        "" : (String.valueOf(selectedJewelbox) + " ");
      String addInfoText = String.valueOf(selectedJewelbox) + bomDiskDetail.jewelInfo;
      table_contents.setRowBorderColor(nextRow, 1, Color.white);
      table_contents.setRowBorderColor(nextRow, 4, Color.white);
      table_contents.setRowBorderColor(nextRow, 8, Color.white);
      table_contents.setRowBorderColor(nextRow, 10, Color.white);
      table_contents.setRowBorderColor(nextRow, 11, Color.white);
      table_contents.setFont(nextRow, 0, boldFont);
      table_contents.setAlignment(nextRow, 0, 34);
      table_contents.setObject(nextRow, 0, replaceQuote(jewelboxCheck));
      table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
      table_contents.setFont(nextRow, 2, plainFont);
      table_contents.setAlignment(nextRow, 2, 33);
      table_contents.setObject(nextRow, 2, "Jewel Box");
      table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
      table_contents.setAlignment(nextRow, 5, 33);
      table_contents.setFont(nextRow, 5, plainFont);
      table_contents.setObject(nextRow, 5, "n.a.");
      table_contents.setFont(nextRow, 9, plainFont);
      table_contents.setAlignment(nextRow, 9, 34);
      table_contents.setObject(nextRow, 9, "/");
      table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
      table_contents.setFont(nextRow, 12, plainFont);
      table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
      table_contents.setAlignment(nextRow, 12, 33);
      nextRow++;
      if (isDVD) {
        String wrapCheck = "";
        if (((BomDVDDetail)bomDiskDetail).wrapStatusIndicator) {
          wrapCheck = "x";
        } else {
          wrapCheck = "";
        } 
        String wrapSupplierID = Integer.toString(((BomDVDDetail)bomDiskDetail).wrapSupplierId);
        LookupObject supplier = MilestoneHelper.getSupplierById(wrapSupplierID);
        String wrapSupplierName = (supplier == null) ? "" : supplier.getName();
        String bomWrapInk1 = String.valueOf(((BomDVDDetail)bomDiskDetail).wrapInk1) + " / " + 
          ((BomDVDDetail)bomDiskDetail).wrapInk2;
        addInfoText = ((BomDVDDetail)bomDiskDetail).wrapInfo;
        table_contents.setRowBorderColor(nextRow, 1, Color.white);
        table_contents.setRowBorderColor(nextRow, 4, Color.white);
        table_contents.setRowBorderColor(nextRow, 8, Color.white);
        table_contents.setRowBorderColor(nextRow, 10, Color.white);
        table_contents.setRowBorderColor(nextRow, 11, Color.white);
        table_contents.setFont(nextRow, 0, boldFont);
        table_contents.setAlignment(nextRow, 0, 34);
        table_contents.setObject(nextRow, 0, replaceQuote(wrapCheck));
        table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
        table_contents.setFont(nextRow, 2, plainFont);
        table_contents.setAlignment(nextRow, 2, 33);
        table_contents.setObject(nextRow, 2, "Wrap");
        table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
        table_contents.setAlignment(nextRow, 5, 33);
        table_contents.setFont(nextRow, 5, plainFont);
        table_contents.setObject(nextRow, 5, replaceQuote(wrapSupplierName));
        table_contents.setFont(nextRow, 9, plainFont);
        table_contents.setAlignment(nextRow, 9, 34);
        table_contents.setObject(nextRow, 9, replaceQuote(bomWrapInk1));
        table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
        table_contents.setFont(nextRow, 12, plainFont);
        table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
        table_contents.setAlignment(nextRow, 12, 33);
        nextRow++;
        String dvdCaseCheck = "";
        if (((BomDVDDetail)bomDiskDetail).dvdStatusIndicator) {
          dvdCaseCheck = "x";
        } else {
          dvdCaseCheck = "";
        } 
        String dvdCaseLnk = String.valueOf(((BomDVDDetail)bomDiskDetail).dvdInk1) + " / " + 
          ((BomDVDDetail)bomDiskDetail).dvdInk2;
        addInfoText = String.valueOf(((BomDVDDetail)bomDiskDetail).dvdSelectionInfo) + " " + ((BomDVDDetail)bomDiskDetail).dvdInfo;
        table_contents.setRowBorderColor(nextRow, 1, Color.white);
        table_contents.setRowBorderColor(nextRow, 4, Color.white);
        table_contents.setRowBorderColor(nextRow, 8, Color.white);
        table_contents.setRowBorderColor(nextRow, 10, Color.white);
        table_contents.setRowBorderColor(nextRow, 11, Color.white);
        table_contents.setFont(nextRow, 0, boldFont);
        table_contents.setAlignment(nextRow, 0, 34);
        table_contents.setObject(nextRow, 0, replaceQuote(dvdCaseCheck));
        table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
        table_contents.setFont(nextRow, 2, plainFont);
        table_contents.setAlignment(nextRow, 2, 33);
        table_contents.setObject(nextRow, 2, "DVD Case");
        table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
        table_contents.setAlignment(nextRow, 5, 33);
        table_contents.setFont(nextRow, 5, plainFont);
        table_contents.setObject(nextRow, 5, "n.a.");
        table_contents.setFont(nextRow, 9, plainFont);
        table_contents.setAlignment(nextRow, 9, 34);
        table_contents.setObject(nextRow, 9, dvdCaseLnk);
        table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
        table_contents.setFont(nextRow, 12, plainFont);
        table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
        table_contents.setAlignment(nextRow, 12, 33);
        nextRow++;
        String bluRayCaseCheck = "";
        if (((BomDVDDetail)bomDiskDetail).bluRayStatusIndicator) {
          bluRayCaseCheck = "x";
        } else {
          bluRayCaseCheck = "";
        } 
        String bluRayCaseLnk = String.valueOf(((BomDVDDetail)bomDiskDetail).bluRayInk1) + " / " + 
          ((BomDVDDetail)bomDiskDetail).bluRayInk2;
        addInfoText = String.valueOf(((BomDVDDetail)bomDiskDetail).bluRaySelectionInfo) + " " + ((BomDVDDetail)bomDiskDetail).bluRayInfo;
        table_contents.setRowBorderColor(nextRow, 1, Color.white);
        table_contents.setRowBorderColor(nextRow, 4, Color.white);
        table_contents.setRowBorderColor(nextRow, 8, Color.white);
        table_contents.setRowBorderColor(nextRow, 10, Color.white);
        table_contents.setRowBorderColor(nextRow, 11, Color.white);
        table_contents.setFont(nextRow, 0, boldFont);
        table_contents.setAlignment(nextRow, 0, 34);
        table_contents.setObject(nextRow, 0, replaceQuote(bluRayCaseCheck));
        table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
        table_contents.setFont(nextRow, 2, plainFont);
        table_contents.setAlignment(nextRow, 2, 33);
        table_contents.setObject(nextRow, 2, "Blu-Ray Case");
        table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
        table_contents.setAlignment(nextRow, 5, 33);
        table_contents.setFont(nextRow, 5, plainFont);
        table_contents.setObject(nextRow, 5, "n.a.");
        table_contents.setFont(nextRow, 9, plainFont);
        table_contents.setAlignment(nextRow, 9, 34);
        table_contents.setObject(nextRow, 9, bluRayCaseLnk);
        table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
        table_contents.setFont(nextRow, 12, plainFont);
        table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
        table_contents.setAlignment(nextRow, 12, 33);
        nextRow++;
      } else {
        String trayCheck = "";
        if (bomDiskDetail.trayStatusIndicator) {
          trayCheck = "x";
        } else {
          trayCheck = "";
        } 
        String selectedTray = bomDiskDetail.trayColor;
        selectedTray = (selectedTray == null || selectedTray.length() <= 1) ? 
          "" : (String.valueOf(selectedTray) + "  ");
        addInfoText = String.valueOf(selectedTray) + bomDiskDetail.trayInfo;
        table_contents.setRowBorderColor(nextRow, 1, Color.white);
        table_contents.setRowBorderColor(nextRow, 4, Color.white);
        table_contents.setRowBorderColor(nextRow, 8, Color.white);
        table_contents.setRowBorderColor(nextRow, 10, Color.white);
        table_contents.setRowBorderColor(nextRow, 11, Color.white);
        table_contents.setFont(nextRow, 0, boldFont);
        table_contents.setAlignment(nextRow, 0, 
            34);
        table_contents.setObject(nextRow, 0, replaceQuote(trayCheck));
        table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
        table_contents.setFont(nextRow, 2, plainFont);
        table_contents.setAlignment(nextRow, 2, 
            33);
        table_contents.setObject(nextRow, 2, "Tray");
        table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
        table_contents.setAlignment(nextRow, 5, 
            33);
        table_contents.setFont(nextRow, 5, plainFont);
        table_contents.setObject(nextRow, 5, "n.a.");
        table_contents.setFont(nextRow, 9, plainFont);
        table_contents.setAlignment(nextRow, 9, 
            34);
        table_contents.setObject(nextRow, 9, "/");
        table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
        table_contents.setFont(nextRow, 12, plainFont);
        table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
        table_contents.setAlignment(nextRow, 12, 
            33);
        nextRow++;
      } 
      String inlayCheck = "";
      if (bomDiskDetail.inlayStatusIndicator) {
        inlayCheck = "x";
      } else {
        inlayCheck = "";
      } 
      String supplierID = Integer.toString(bomDiskDetail.inlaySupplierId);
      LookupObject supplier = MilestoneHelper.getSupplierById(supplierID);
      String supplierName = (supplier == null) ? "" : supplier.getName();
      String bomInlayInk1 = String.valueOf(bomDiskDetail.inlayInk1) + " / " + 
        bomDiskDetail.inlayInk2;
      addInfoText = bomDiskDetail.inlayInfo;
      table_contents.setRowBorderColor(nextRow, 1, Color.white);
      table_contents.setRowBorderColor(nextRow, 4, Color.white);
      table_contents.setRowBorderColor(nextRow, 8, Color.white);
      table_contents.setRowBorderColor(nextRow, 10, Color.white);
      table_contents.setRowBorderColor(nextRow, 11, Color.white);
      table_contents.setFont(nextRow, 0, boldFont);
      table_contents.setAlignment(nextRow, 0, 34);
      table_contents.setObject(nextRow, 0, replaceQuote(inlayCheck));
      table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
      table_contents.setFont(nextRow, 2, plainFont);
      table_contents.setAlignment(nextRow, 2, 33);
      table_contents.setObject(nextRow, 2, "Inlay");
      table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
      table_contents.setAlignment(nextRow, 5, 33);
      table_contents.setFont(nextRow, 5, plainFont);
      table_contents.setObject(nextRow, 5, replaceQuote(supplierName));
      table_contents.setFont(nextRow, 9, plainFont);
      table_contents.setAlignment(nextRow, 9, 34);
      table_contents.setObject(nextRow, 9, replaceQuote(bomInlayInk1));
      table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
      table_contents.setFont(nextRow, 12, plainFont);
      table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
      table_contents.setAlignment(nextRow, 12, 33);
      nextRow++;
      String frontinsertCheck = "";
      if (bomDiskDetail.frontStatusIndicator) {
        frontinsertCheck = "x";
      } else {
        frontinsertCheck = "";
      } 
      supplierID = Integer.toString(bomDiskDetail.frontSupplierId);
      supplier = MilestoneHelper.getSupplierById(supplierID);
      supplierName = (supplier == null) ? "" : supplier.getName();
      String bomFrontInsertInk1 = String.valueOf(bomDiskDetail.frontInk1) + "/" + 
        bomDiskDetail.frontInk2;
      addInfoText = bomDiskDetail.frontInfo;
      table_contents.setRowBorderColor(nextRow, 1, Color.white);
      table_contents.setRowBorderColor(nextRow, 4, Color.white);
      table_contents.setRowBorderColor(nextRow, 8, Color.white);
      table_contents.setRowBorderColor(nextRow, 10, Color.white);
      table_contents.setRowBorderColor(nextRow, 11, Color.white);
      table_contents.setFont(nextRow, 0, boldFont);
      table_contents.setAlignment(nextRow, 0, 34);
      table_contents.setObject(nextRow, 0, replaceQuote(frontinsertCheck));
      table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
      table_contents.setFont(nextRow, 2, plainFont);
      table_contents.setAlignment(nextRow, 2, 33);
      table_contents.setObject(nextRow, 2, "Front Insert");
      table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
      table_contents.setAlignment(nextRow, 5, 33);
      table_contents.setFont(nextRow, 5, plainFont);
      table_contents.setObject(nextRow, 5, replaceQuote(supplierName));
      table_contents.setFont(nextRow, 9, plainFont);
      table_contents.setAlignment(nextRow, 9, 34);
      table_contents.setObject(nextRow, 9, replaceQuote(bomFrontInsertInk1));
      table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
      table_contents.setFont(nextRow, 12, plainFont);
      table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
      table_contents.setAlignment(nextRow, 12, 33);
      nextRow++;
      String folderCheck = "";
      if (bomDiskDetail.folderStatusIndicator) {
        folderCheck = "x";
      } else {
        folderCheck = "";
      } 
      supplierID = Integer.toString(bomDiskDetail.folderSupplierId);
      supplier = MilestoneHelper.getSupplierById(supplierID);
      supplierName = (supplier == null) ? "" : supplier.getName();
      String bomFolderInk1 = String.valueOf(bomDiskDetail.folderInk1) + "/" + 
        bomDiskDetail.folderInk2;
      String selectedFolder = bomDiskDetail.folderPages;
      selectedFolder = (selectedFolder == null || !MilestoneHelper.isAllNumeric(selectedFolder)) ? 
        "" : ("Pages: " + selectedFolder + "  ");
      addInfoText = String.valueOf(selectedFolder) + bomDiskDetail.folderInfo;
      table_contents.setRowBorderColor(nextRow, 1, Color.white);
      table_contents.setRowBorderColor(nextRow, 4, Color.white);
      table_contents.setRowBorderColor(nextRow, 8, Color.white);
      table_contents.setRowBorderColor(nextRow, 10, Color.white);
      table_contents.setRowBorderColor(nextRow, 11, Color.white);
      table_contents.setFont(nextRow, 0, boldFont);
      table_contents.setAlignment(nextRow, 0, 34);
      table_contents.setObject(nextRow, 0, replaceQuote(folderCheck));
      table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
      table_contents.setFont(nextRow, 2, plainFont);
      table_contents.setAlignment(nextRow, 2, 33);
      table_contents.setObject(nextRow, 2, "Folder");
      table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
      table_contents.setAlignment(nextRow, 5, 33);
      table_contents.setFont(nextRow, 5, plainFont);
      table_contents.setObject(nextRow, 5, replaceQuote(supplierName));
      table_contents.setFont(nextRow, 9, plainFont);
      table_contents.setAlignment(nextRow, 9, 34);
      table_contents.setObject(nextRow, 9, replaceQuote(bomFolderInk1));
      table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
      table_contents.setFont(nextRow, 12, plainFont);
      table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
      table_contents.setAlignment(nextRow, 12, 33);
      nextRow++;
      String check = "";
      if (bomDiskDetail.bookletStatusIndicator) {
        check = "x";
      } else {
        check = "";
      } 
      supplierID = Integer.toString(bomDiskDetail.bookletSupplierId);
      supplier = MilestoneHelper.getSupplierById(supplierID);
      supplierName = (supplier == null) ? "" : supplier.getName();
      String bomBookletInk1 = String.valueOf(bomDiskDetail.bookletInk1) + "/" + 
        bomDiskDetail.bookletInk2;
      String selectedBooklet = bomDiskDetail.getBookletPages();
      selectedBooklet = (selectedBooklet == null || !MilestoneHelper.isAllNumeric(selectedBooklet)) ? 
        "" : ("Pages: " + selectedBooklet + "  ");
      addInfoText = String.valueOf(selectedBooklet) + bomDiskDetail.bookletInfo;
      table_contents.setRowBorderColor(nextRow, 1, Color.white);
      table_contents.setRowBorderColor(nextRow, 4, Color.white);
      table_contents.setRowBorderColor(nextRow, 8, Color.white);
      table_contents.setRowBorderColor(nextRow, 10, Color.white);
      table_contents.setRowBorderColor(nextRow, 11, Color.white);
      table_contents.setFont(nextRow, 0, boldFont);
      table_contents.setAlignment(nextRow, 0, 34);
      table_contents.setObject(nextRow, 0, replaceQuote(check));
      table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
      table_contents.setFont(nextRow, 2, plainFont);
      table_contents.setAlignment(nextRow, 2, 33);
      table_contents.setObject(nextRow, 2, "Booklet");
      table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
      table_contents.setAlignment(nextRow, 5, 33);
      table_contents.setFont(nextRow, 5, plainFont);
      table_contents.setObject(nextRow, 5, replaceQuote(supplierName));
      table_contents.setFont(nextRow, 9, plainFont);
      table_contents.setAlignment(nextRow, 9, 34);
      table_contents.setObject(nextRow, 9, replaceQuote(bomBookletInk1));
      table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
      table_contents.setFont(nextRow, 12, plainFont);
      table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
      table_contents.setAlignment(nextRow, 12, 33);
      nextRow++;
      check = "";
      if (bomDiskDetail.brcStatusIndicator) {
        check = "x";
      } else {
        check = "";
      } 
      supplierID = Integer.toString(bomDiskDetail.brcSupplierId);
      supplier = MilestoneHelper.getSupplierById(supplierID);
      supplierName = (supplier == null) ? "" : supplier.getName();
      String bomBrcInsertInk1 = String.valueOf(bomDiskDetail.brcInk1) + "/" + 
        bomDiskDetail.brcInk2;
      String sizeBrcInsert = bomDiskDetail.brcSize;
      sizeBrcInsert = (sizeBrcInsert == null || sizeBrcInsert.trim().equals("")) ? 
        "" : ("Size: " + sizeBrcInsert + "  ");
      addInfoText = String.valueOf(sizeBrcInsert) + bomDiskDetail.brcInfo;
      table_contents.setRowBorderColor(nextRow, 1, Color.white);
      table_contents.setRowBorderColor(nextRow, 4, Color.white);
      table_contents.setRowBorderColor(nextRow, 8, Color.white);
      table_contents.setRowBorderColor(nextRow, 10, Color.white);
      table_contents.setRowBorderColor(nextRow, 11, Color.white);
      table_contents.setFont(nextRow, 0, boldFont);
      table_contents.setAlignment(nextRow, 0, 34);
      table_contents.setObject(nextRow, 0, replaceQuote(check));
      table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
      table_contents.setFont(nextRow, 2, plainFont);
      table_contents.setAlignment(nextRow, 2, 33);
      table_contents.setObject(nextRow, 2, "BRCInsert");
      table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
      table_contents.setAlignment(nextRow, 5, 33);
      table_contents.setFont(nextRow, 5, plainFont);
      table_contents.setObject(nextRow, 5, replaceQuote(supplierName));
      table_contents.setFont(nextRow, 9, plainFont);
      table_contents.setAlignment(nextRow, 9, 34);
      table_contents.setObject(nextRow, 9, replaceQuote(bomBrcInsertInk1));
      table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
      table_contents.setFont(nextRow, 12, plainFont);
      table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
      table_contents.setAlignment(nextRow, 12, 33);
      nextRow++;
      check = "";
      if (bomDiskDetail.miniStatusIndicator) {
        check = "x";
      } else {
        check = "";
      } 
      supplierID = Integer.toString(bomDiskDetail.miniSupplierId);
      supplier = MilestoneHelper.getSupplierById(supplierID);
      supplierName = (supplier == null) ? "" : supplier.getName();
      String bomMiniJacketInk1 = String.valueOf(bomDiskDetail.miniInk1) + "/" + 
        bomDiskDetail.miniInk2;
      addInfoText = bomDiskDetail.miniInfo;
      table_contents.setRowBorderColor(nextRow, 1, Color.white);
      table_contents.setRowBorderColor(nextRow, 4, Color.white);
      table_contents.setRowBorderColor(nextRow, 8, Color.white);
      table_contents.setRowBorderColor(nextRow, 10, Color.white);
      table_contents.setRowBorderColor(nextRow, 11, Color.white);
      table_contents.setFont(nextRow, 0, boldFont);
      table_contents.setAlignment(nextRow, 0, 34);
      table_contents.setObject(nextRow, 0, replaceQuote(check));
      table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
      table_contents.setFont(nextRow, 2, plainFont);
      table_contents.setAlignment(nextRow, 2, 33);
      table_contents.setObject(nextRow, 2, "Mini Jacket");
      table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
      table_contents.setAlignment(nextRow, 5, 33);
      table_contents.setFont(nextRow, 5, plainFont);
      table_contents.setObject(nextRow, 5, replaceQuote(supplierName));
      table_contents.setFont(nextRow, 9, plainFont);
      table_contents.setAlignment(nextRow, 9, 34);
      table_contents.setObject(nextRow, 9, replaceQuote(bomMiniJacketInk1));
      table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
      table_contents.setFont(nextRow, 12, plainFont);
      table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
      table_contents.setAlignment(nextRow, 12, 33);
      nextRow++;
      check = "";
      if (bomDiskDetail.digiPakStatusIndicator) {
        check = "x";
      } else {
        check = "";
      } 
      supplierID = Integer.toString(bomDiskDetail.digiPakSupplierId);
      supplier = MilestoneHelper.getSupplierById(supplierID);
      supplierName = (supplier == null) ? "" : supplier.getName();
      String bomDigiPakInk1 = String.valueOf(bomDiskDetail.digiPakInk1) + "/" + 
        bomDiskDetail.digiPakInk2;
      String trayDigipak = bomDiskDetail.digiPakTray;
      trayDigipak = (trayDigipak == null || trayDigipak.trim().equals("")) ? 
        "" : ("Tray: " + trayDigipak + "  ");
      addInfoText = String.valueOf(trayDigipak) + bomDiskDetail.digiPakInfo;
      table_contents.setRowBorderColor(nextRow, 1, Color.white);
      table_contents.setRowBorderColor(nextRow, 4, Color.white);
      table_contents.setRowBorderColor(nextRow, 8, Color.white);
      table_contents.setRowBorderColor(nextRow, 10, Color.white);
      table_contents.setRowBorderColor(nextRow, 11, Color.white);
      table_contents.setFont(nextRow, 0, boldFont);
      table_contents.setAlignment(nextRow, 0, 34);
      table_contents.setObject(nextRow, 0, replaceQuote(check));
      table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
      table_contents.setFont(nextRow, 2, plainFont);
      table_contents.setAlignment(nextRow, 2, 33);
      table_contents.setObject(nextRow, 2, "Digipak");
      table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
      table_contents.setAlignment(nextRow, 5, 33);
      table_contents.setFont(nextRow, 5, plainFont);
      table_contents.setObject(nextRow, 5, replaceQuote(supplierName));
      table_contents.setFont(nextRow, 9, plainFont);
      table_contents.setAlignment(nextRow, 9, 34);
      table_contents.setObject(nextRow, 9, replaceQuote(bomDigiPakInk1));
      table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
      table_contents.setFont(nextRow, 12, plainFont);
      table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
      table_contents.setAlignment(nextRow, 12, 33);
      nextRow++;
      check = "";
      if (bomDiskDetail.softPakStatusIndicator) {
        check = "x";
      } else {
        check = "";
      } 
      supplierID = Integer.toString(bomDiskDetail.softPakSupplierId);
      supplier = MilestoneHelper.getSupplierById(supplierID);
      supplierName = (supplier == null) ? "" : supplier.getName();
      String bomSoftPakInk1 = String.valueOf(bomDiskDetail.softPakInk1) + "/" + 
        bomDiskDetail.softPakInk2;
      String traySoftpak = bomDiskDetail.softPakTray;
      traySoftpak = (traySoftpak == null || traySoftpak.trim().equals("")) ? 
        "" : ("Tray: " + traySoftpak + "  ");
      addInfoText = String.valueOf(traySoftpak) + bomDiskDetail.softPakInfo;
      table_contents.setRowBorderColor(nextRow, 1, Color.white);
      table_contents.setRowBorderColor(nextRow, 4, Color.white);
      table_contents.setRowBorderColor(nextRow, 8, Color.white);
      table_contents.setRowBorderColor(nextRow, 10, Color.white);
      table_contents.setRowBorderColor(nextRow, 11, Color.white);
      table_contents.setFont(nextRow, 0, boldFont);
      table_contents.setAlignment(nextRow, 0, 34);
      table_contents.setObject(nextRow, 0, replaceQuote(check));
      table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
      table_contents.setFont(nextRow, 2, plainFont);
      table_contents.setAlignment(nextRow, 2, 33);
      table_contents.setObject(nextRow, 2, "Softpak");
      table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
      table_contents.setAlignment(nextRow, 5, 33);
      table_contents.setFont(nextRow, 5, plainFont);
      table_contents.setObject(nextRow, 5, replaceQuote(supplierName));
      table_contents.setFont(nextRow, 9, plainFont);
      table_contents.setAlignment(nextRow, 9, 34);
      table_contents.setObject(nextRow, 9, replaceQuote(bomSoftPakInk1));
      table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
      table_contents.setFont(nextRow, 12, plainFont);
      table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
      table_contents.setAlignment(nextRow, 12, 33);
      nextRow++;
      check = "";
      if (bomDiskDetail.stickerOneStatusIndicator) {
        check = "x";
      } else {
        check = "";
      } 
      supplierID = Integer.toString(bomDiskDetail.stickerOneSupplierId);
      supplier = MilestoneHelper.getSupplierById(supplierID);
      supplierName = (supplier == null) ? "" : supplier.getName();
      String bomSticker1Ink1 = String.valueOf(bomDiskDetail.stickerOneInk1) + "/" + 
        bomDiskDetail.stickerOneInk2;
      String placesSticker1 = bomDiskDetail.stickerOnePlaces;
      placesSticker1 = (placesSticker1 == null || placesSticker1.trim().equals("")) ? 
        "" : ("Places: " + placesSticker1 + "  ");
      addInfoText = String.valueOf(placesSticker1) + bomDiskDetail.stickerOneInfo;
      table_contents.setRowBorderColor(nextRow, 1, Color.white);
      table_contents.setRowBorderColor(nextRow, 4, Color.white);
      table_contents.setRowBorderColor(nextRow, 8, Color.white);
      table_contents.setRowBorderColor(nextRow, 10, Color.white);
      table_contents.setRowBorderColor(nextRow, 11, Color.white);
      table_contents.setFont(nextRow, 0, boldFont);
      table_contents.setAlignment(nextRow, 0, 34);
      table_contents.setObject(nextRow, 0, replaceQuote(check));
      table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
      table_contents.setFont(nextRow, 2, plainFont);
      table_contents.setAlignment(nextRow, 2, 33);
      table_contents.setObject(nextRow, 2, "Sticker1");
      table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
      table_contents.setAlignment(nextRow, 5, 33);
      table_contents.setFont(nextRow, 5, plainFont);
      table_contents.setObject(nextRow, 5, replaceQuote(supplierName));
      table_contents.setFont(nextRow, 9, plainFont);
      table_contents.setAlignment(nextRow, 9, 34);
      table_contents.setObject(nextRow, 9, replaceQuote(bomSticker1Ink1));
      table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
      table_contents.setFont(nextRow, 12, plainFont);
      table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
      table_contents.setAlignment(nextRow, 12, 33);
      nextRow++;
      check = "";
      if (bomDiskDetail.stickerTwoStatusIndicator) {
        check = "x";
      } else {
        check = "";
      } 
      supplierID = Integer.toString(bomDiskDetail.stickerTwoSupplierId);
      supplier = MilestoneHelper.getSupplierById(supplierID);
      supplierName = (supplier == null) ? "" : supplier.getName();
      String bomSticker2Ink1 = String.valueOf(bomDiskDetail.stickerTwoInk1) + "/" + 
        bomDiskDetail.stickerTwoInk2;
      String placesSticker2 = bomDiskDetail.stickerTwoPlaces;
      placesSticker2 = (placesSticker2 == null || placesSticker2.trim().equals("")) ? 
        "" : ("Places: " + placesSticker2 + "  ");
      addInfoText = String.valueOf(placesSticker2) + bomDiskDetail.stickerTwoInfo;
      table_contents.setRowBorderColor(nextRow, 1, Color.white);
      table_contents.setRowBorderColor(nextRow, 4, Color.white);
      table_contents.setRowBorderColor(nextRow, 8, Color.white);
      table_contents.setRowBorderColor(nextRow, 10, Color.white);
      table_contents.setRowBorderColor(nextRow, 11, Color.white);
      table_contents.setFont(nextRow, 0, boldFont);
      table_contents.setAlignment(nextRow, 0, 34);
      table_contents.setObject(nextRow, 0, replaceQuote(check));
      table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
      table_contents.setFont(nextRow, 2, plainFont);
      table_contents.setAlignment(nextRow, 2, 33);
      table_contents.setObject(nextRow, 2, "Sticker2");
      table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
      table_contents.setAlignment(nextRow, 5, 33);
      table_contents.setFont(nextRow, 5, plainFont);
      table_contents.setObject(nextRow, 5, replaceQuote(supplierName));
      table_contents.setFont(nextRow, 9, plainFont);
      table_contents.setAlignment(nextRow, 9, 34);
      table_contents.setObject(nextRow, 9, replaceQuote(bomSticker2Ink1));
      table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
      table_contents.setFont(nextRow, 12, plainFont);
      table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
      table_contents.setAlignment(nextRow, 12, 33);
      nextRow++;
      check = "";
      if (bomDiskDetail.bookStatusIndicator) {
        check = "x";
      } else {
        check = "";
      } 
      supplierID = Integer.toString(bomDiskDetail.bookSupplierId);
      supplier = MilestoneHelper.getSupplierById(supplierID);
      supplierName = (supplier == null) ? "" : supplier.getName();
      String bomBookSetInk1 = String.valueOf(bomDiskDetail.bookInk1) + "/" + 
        bomDiskDetail.bookInk2;
      String booksetPages = bomDiskDetail.bookPages;
      booksetPages = (booksetPages == null || booksetPages.trim().equals("")) ? 
        "" : ("Pages: " + booksetPages + "  ");
      addInfoText = String.valueOf(booksetPages) + bomDiskDetail.bookInfo;
      table_contents.setRowBorderColor(nextRow, 1, Color.white);
      table_contents.setRowBorderColor(nextRow, 4, Color.white);
      table_contents.setRowBorderColor(nextRow, 8, Color.white);
      table_contents.setRowBorderColor(nextRow, 10, Color.white);
      table_contents.setRowBorderColor(nextRow, 11, Color.white);
      table_contents.setFont(nextRow, 0, boldFont);
      table_contents.setAlignment(nextRow, 0, 34);
      table_contents.setObject(nextRow, 0, replaceQuote(check));
      table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
      table_contents.setFont(nextRow, 2, plainFont);
      table_contents.setAlignment(nextRow, 2, 33);
      table_contents.setObject(nextRow, 2, "Book(Other/Set)");
      table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
      table_contents.setAlignment(nextRow, 5, 33);
      table_contents.setFont(nextRow, 5, plainFont);
      table_contents.setObject(nextRow, 5, replaceQuote(supplierName));
      table_contents.setFont(nextRow, 9, plainFont);
      table_contents.setAlignment(nextRow, 9, 34);
      table_contents.setObject(nextRow, 9, replaceQuote(bomBookSetInk1));
      table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
      table_contents.setFont(nextRow, 12, plainFont);
      table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
      table_contents.setAlignment(nextRow, 12, 33);
      nextRow++;
      check = "";
      if (bomDiskDetail.boxStatusIndicator) {
        check = "x";
      } else {
        check = "";
      } 
      supplierID = Integer.toString(bomDiskDetail.boxSupplierId);
      supplier = MilestoneHelper.getSupplierById(supplierID);
      supplierName = (supplier == null) ? "" : supplier.getName();
      String bomBoxSetInk1 = String.valueOf(bomDiskDetail.boxInk1) + "/" + 
        bomDiskDetail.boxInk2;
      String boxsetSize = bomDiskDetail.boxSizes;
      boxsetSize = (boxsetSize == null || boxsetSize.trim().equals("")) ? 
        "" : ("Size: " + boxsetSize + "  ");
      addInfoText = String.valueOf(boxsetSize) + bomDiskDetail.boxInfo;
      table_contents.setRowBorderColor(nextRow, 1, Color.white);
      table_contents.setRowBorderColor(nextRow, 4, Color.white);
      table_contents.setRowBorderColor(nextRow, 8, Color.white);
      table_contents.setRowBorderColor(nextRow, 10, Color.white);
      table_contents.setRowBorderColor(nextRow, 11, Color.white);
      table_contents.setFont(nextRow, 0, boldFont);
      table_contents.setAlignment(nextRow, 0, 34);
      table_contents.setObject(nextRow, 0, replaceQuote(check));
      table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
      table_contents.setFont(nextRow, 2, plainFont);
      table_contents.setAlignment(nextRow, 2, 33);
      table_contents.setObject(nextRow, 2, "Box(Set)");
      table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
      table_contents.setAlignment(nextRow, 5, 33);
      table_contents.setFont(nextRow, 5, plainFont);
      table_contents.setObject(nextRow, 5, replaceQuote(supplierName));
      table_contents.setFont(nextRow, 9, plainFont);
      table_contents.setAlignment(nextRow, 9, 34);
      table_contents.setObject(nextRow, 9, replaceQuote(bomBoxSetInk1));
      table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
      table_contents.setFont(nextRow, 12, plainFont);
      table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
      table_contents.setAlignment(nextRow, 12, 33);
      nextRow++;
      check = "";
      if (bomDiskDetail.otherStatusIndicator) {
        check = "x";
      } else {
        check = "";
      } 
      supplierID = Integer.toString(bomDiskDetail.otherSupplierId);
      supplier = MilestoneHelper.getSupplierById(supplierID);
      supplierName = (supplier == null) ? "" : supplier.getName();
      String bomOtherInk1 = String.valueOf(bomDiskDetail.otherInk1) + "/" + 
        bomDiskDetail.otherInk2;
      addInfoText = bomDiskDetail.otherInfo;
      table_contents.setRowBorderColor(nextRow, 1, Color.white);
      table_contents.setRowBorderColor(nextRow, 4, Color.white);
      table_contents.setRowBorderColor(nextRow, 8, Color.white);
      table_contents.setRowBorderColor(nextRow, 10, Color.white);
      table_contents.setRowBorderColor(nextRow, 11, Color.white);
      table_contents.setFont(nextRow, 0, boldFont);
      table_contents.setAlignment(nextRow, 0, 34);
      table_contents.setObject(nextRow, 0, replaceQuote(check));
      table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
      table_contents.setFont(nextRow, 2, plainFont);
      table_contents.setAlignment(nextRow, 2, 33);
      table_contents.setObject(nextRow, 2, "Other");
      table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
      table_contents.setAlignment(nextRow, 5, 33);
      table_contents.setFont(nextRow, 5, plainFont);
      table_contents.setObject(nextRow, 5, replaceQuote(supplierName));
      table_contents.setFont(nextRow, 9, plainFont);
      table_contents.setAlignment(nextRow, 9, 34);
      table_contents.setObject(nextRow, 9, replaceQuote(bomOtherInk1));
      table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
      table_contents.setFont(nextRow, 12, plainFont);
      table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
      table_contents.setAlignment(nextRow, 12, 33);
      nextRow++;
      table_contents.setRowBorderColor(nextRow, Color.white);
      table_contents.setRowHeight(nextRow, 10);
      nextRow++;
      table_contents.setRowBorderColor(nextRow, Color.white);
      table_contents.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
      table_contents.setSpan(nextRow, 0, new Dimension(6, 1));
      table_contents.setFont(nextRow, 0, new Font("Arial", 1, 12));
      table_contents.setAlignment(nextRow, 0, 9);
      table_contents.setObject(nextRow, 0, "Special Instructions:");
      table_contents.setSpan(nextRow, 6, new Dimension(14, 1));
      table_contents.setFont(nextRow, 6, new Font("Arial", 0, 11));
      table_contents.setAlignment(nextRow, 6, 9);
      String specialInstructionsString = "";
      if (bom.getSpecialInstructions().length() > 0)
        specialInstructionsString = bom.getSpecialInstructions(); 
      table_contents.setObject(nextRow, 6, String.valueOf(replaceQuote(specialInstructionsString)) + "\n");
      report.setElement("table_colheaders", table_contents);
    } 
  }
  
  private void addCassetteReportElements(XStyleSheet report, Bom bom, Context context, DefaultTableLens table_contents, Selection mySelection) {
    if (bom != null) {
      BomCassetteDetail bomCassetteDetail = bom.getBomCassetteDetail();
      table_contents.setHeaderRowCount(0);
      table_contents.setColWidth(0, 16);
      table_contents.setColWidth(1, 2);
      table_contents.setColWidth(2, 15);
      table_contents.setColWidth(3, 40);
      table_contents.setColWidth(4, 2);
      table_contents.setColWidth(5, 60);
      table_contents.setColWidth(6, 40);
      table_contents.setColWidth(7, 40);
      table_contents.setColWidth(8, 2);
      table_contents.setColWidth(9, 50);
      table_contents.setColWidth(10, 10);
      table_contents.setColWidth(11, 10);
      table_contents.setColWidth(12, 20);
      table_contents.setColWidth(13, 40);
      table_contents.setColWidth(14, 40);
      table_contents.setColWidth(15, 110);
      table_contents.setColWidth(16, 45);
      table_contents.setColWidth(17, 30);
      table_contents.setColWidth(18, 20);
      table_contents.setColWidth(19, 40);
      table_contents.setRowInsets(10, new Insets(1, 0, 0, 0));
      table_contents.setRowInsets(11, new Insets(1, 0, 0, 0));
      table_contents.setRowInsets(12, new Insets(1, 0, 0, 0));
      table_contents.setRowInsets(13, new Insets(1, 0, 0, 0));
      table_contents.setRowInsets(14, new Insets(1, 0, 0, 0));
      table_contents.setRowInsets(15, new Insets(1, 0, 0, 0));
      table_contents.setRowInsets(16, new Insets(1, 0, 0, 0));
      table_contents.setRowInsets(17, new Insets(1, 0, 0, 0));
      table_contents.setRowInsets(18, new Insets(1, 0, 0, 0));
      table_contents.setRowInsets(19, new Insets(1, 0, 0, 0));
      table_contents.setRowInsets(20, new Insets(1, 0, 0, 0));
      table_contents.setRowInsets(21, new Insets(1, 0, 0, 0));
      table_contents.setRowInsets(22, new Insets(1, 0, 0, 0));
      table_contents.setRowInsets(23, new Insets(1, 0, 0, 0));
      table_contents.setRowInsets(24, new Insets(1, 0, 0, 0));
      table_contents.setRowInsets(25, new Insets(1, 0, 0, 0));
      table_contents.setRowInsets(26, new Insets(1, 0, 0, 0));
      table_contents.setRowInsets(27, new Insets(1, 0, 0, 0));
      table_contents.setRowInsets(28, new Insets(1, 0, 0, 0));
      table_contents.setRowInsets(29, new Insets(1, 0, 0, 0));
      int nextRow = 12;
      Font boldFont = new Font("Arial", 1, 10);
      Font plainFont = new Font("Arial", 0, 9);
      String stringUnitsPerPkg = "";
      if (mySelection.getNumberOfUnits() > 0 && 
        !Integer.toString(mySelection.getNumberOfUnits()).equals(""))
        stringUnitsPerPkg = Integer.toString(mySelection.getNumberOfUnits()); 
      String noShrinkwrap = context.getRequestValue("UseNoShrinkWrap");
      if (bom.useShrinkWrap()) {
        noShrinkwrap = "X";
      } else {
        noShrinkwrap = "";
      } 
      table_contents.setRowInsets(nextRow, new Insets(1, 0, 0, 0));
      table_contents.setRowBorderColor(nextRow, 0, Color.white);
      table_contents.setRowBorderColor(nextRow, 1, Color.white);
      table_contents.setRowBorderColor(nextRow, 2, Color.white);
      table_contents.setRowBorderColor(nextRow, 3, Color.white);
      table_contents.setRowBorderColor(nextRow, 4, Color.white);
      table_contents.setRowBorderColor(nextRow, 5, Color.white);
      table_contents.setRowBorderColor(nextRow, 6, Color.white);
      table_contents.setRowBorderColor(nextRow, 7, Color.white);
      table_contents.setRowBorderColor(nextRow, 8, Color.white);
      table_contents.setRowBorderColor(nextRow, 9, Color.white);
      table_contents.setRowBorderColor(nextRow, 12, Color.white);
      table_contents.setRowBorderColor(nextRow, 13, Color.white);
      table_contents.setRowBorderColor(nextRow, 14, Color.white);
      table_contents.setRowBorderColor(nextRow, 16, Color.white);
      table_contents.setRowBorderColor(nextRow, 17, Color.white);
      table_contents.setRowBorderColor(nextRow, 18, Color.white);
      table_contents.setSpan(nextRow, 0, new Dimension(7, 1));
      table_contents.setFont(nextRow, 0, boldFont);
      table_contents.setAlignment(nextRow, 0, 33);
      table_contents.setObject(nextRow, 0, "Cassette");
      table_contents.setSpan(nextRow, 7, new Dimension(3, 1));
      table_contents.setFont(nextRow, 7, boldFont);
      table_contents.setAlignment(nextRow, 7, 36);
      table_contents.setObject(nextRow, 7, "# of Units:");
      table_contents.setSpan(nextRow, 10, new Dimension(2, 1));
      table_contents.setAlignment(nextRow, 10, 34);
      table_contents.setFont(nextRow, 10, plainFont);
      table_contents.setObject(nextRow, 10, replaceQuote(stringUnitsPerPkg));
      table_contents.setSpan(nextRow, 13, new Dimension(2, 1));
      table_contents.setFont(nextRow, 13, boldFont);
      table_contents.setAlignment(nextRow, 13, 36);
      table_contents.setObject(nextRow, 13, "Run Time(s):");
      table_contents.setFont(nextRow, 15, new Font("Arial", 0, 10));
      table_contents.setObject(nextRow, 15, replaceQuote(bom.getRunTime()));
      table_contents.setAlignment(nextRow, 15, 34);
      table_contents.setSpan(nextRow, 16, new Dimension(3, 1));
      table_contents.setFont(nextRow, 16, boldFont);
      table_contents.setAlignment(nextRow, 16, 36);
      table_contents.setObject(nextRow, 16, "Shrink Wrap:");
      table_contents.setFont(nextRow, 19, new Font("Arial", 0, 10));
      table_contents.setObject(nextRow, 19, replaceQuote(noShrinkwrap));
      table_contents.setAlignment(nextRow, 19, 34);
      nextRow++;
      table_contents.setRowHeight(nextRow, 5);
      table_contents.setRowBorderColor(nextRow, Color.black);
      nextRow++;
      table_contents.setRowInsets(nextRow, new Insets(1, 0, 0, 0));
      table_contents.setRowBorderColor(nextRow, Color.white);
      table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
      table_contents.setFont(nextRow, 2, boldFont);
      table_contents.setAlignment(nextRow, 2, 33);
      table_contents.setObject(nextRow, 2, "Part");
      table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
      table_contents.setAlignment(nextRow, 5, 33);
      table_contents.setFont(nextRow, 5, boldFont);
      table_contents.setObject(nextRow, 5, "Supplier");
      table_contents.setFont(nextRow, 9, boldFont);
      table_contents.setAlignment(nextRow, 9, 34);
      table_contents.setObject(nextRow, 9, "Ink");
      table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
      table_contents.setFont(nextRow, 12, boldFont);
      table_contents.setObject(nextRow, 12, "Additional Information");
      table_contents.setAlignment(nextRow, 12, 33);
      nextRow++;
      String check = "";
      if (bomCassetteDetail.coStatusIndicator) {
        check = "x";
      } else {
        check = "";
      } 
      String supplierC0ID = Integer.toString(bomCassetteDetail.coParSupplierId);
      LookupObject supplierc0 = MilestoneHelper.getSupplierById(supplierC0ID);
      String supplierNamec0 = (supplierc0 == null) ? "" : supplierc0.getName();
      String bomCOInk1 = String.valueOf(bomCassetteDetail.coInk1) + "/" + 
        bomCassetteDetail.coInk2;
      String c0Color = bomCassetteDetail.coColor;
      c0Color = (c0Color == null || c0Color.length() <= 1) ? 
        "" : ("Color: " + c0Color + "  ");
      String addInfoText = String.valueOf(c0Color) + bomCassetteDetail.coInfo;
      table_contents.setRowBorderColor(nextRow, 1, Color.white);
      table_contents.setRowBorderColor(nextRow, 4, Color.white);
      table_contents.setRowBorderColor(nextRow, 8, Color.white);
      table_contents.setRowBorderColor(nextRow, 10, Color.white);
      table_contents.setRowBorderColor(nextRow, 11, Color.white);
      table_contents.setFont(nextRow, 0, boldFont);
      table_contents.setAlignment(nextRow, 0, 34);
      table_contents.setObject(nextRow, 0, replaceQuote(check));
      table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
      table_contents.setFont(nextRow, 2, plainFont);
      table_contents.setAlignment(nextRow, 2, 33);
      table_contents.setObject(nextRow, 2, "C-O");
      table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
      table_contents.setAlignment(nextRow, 5, 33);
      table_contents.setFont(nextRow, 5, plainFont);
      table_contents.setObject(nextRow, 5, supplierNamec0);
      table_contents.setFont(nextRow, 9, plainFont);
      table_contents.setAlignment(nextRow, 9, 34);
      table_contents.setObject(nextRow, 9, replaceQuote(bomCOInk1));
      table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
      table_contents.setFont(nextRow, 12, plainFont);
      table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
      table_contents.setAlignment(nextRow, 12, 33);
      nextRow++;
      check = "";
      if (bomCassetteDetail.norelcoStatusIndicator) {
        check = "x";
      } else {
        check = "";
      } 
      String supplierNorelcoID = Integer.toString(bomCassetteDetail.norelcoSupplierId);
      LookupObject suppliernorelco = MilestoneHelper.getSupplierById(supplierNorelcoID);
      String supplierNameNorelco = (suppliernorelco == null) ? "" : suppliernorelco.getName();
      String norelcoColor = bomCassetteDetail.norelcoColor;
      norelcoColor = (norelcoColor == null || norelcoColor.length() <= 1) ? 
        "" : ("Color: " + norelcoColor + "  ");
      addInfoText = String.valueOf(norelcoColor) + bomCassetteDetail.norelcoInfo;
      table_contents.setRowBorderColor(nextRow, 1, Color.white);
      table_contents.setRowBorderColor(nextRow, 4, Color.white);
      table_contents.setRowBorderColor(nextRow, 8, Color.white);
      table_contents.setRowBorderColor(nextRow, 10, Color.white);
      table_contents.setRowBorderColor(nextRow, 11, Color.white);
      table_contents.setFont(nextRow, 0, boldFont);
      table_contents.setAlignment(nextRow, 0, 34);
      table_contents.setObject(nextRow, 0, replaceQuote(check));
      table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
      table_contents.setFont(nextRow, 2, plainFont);
      table_contents.setAlignment(nextRow, 2, 33);
      table_contents.setObject(nextRow, 2, "Norelco");
      table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
      table_contents.setAlignment(nextRow, 5, 33);
      table_contents.setFont(nextRow, 5, plainFont);
      table_contents.setObject(nextRow, 5, supplierNameNorelco);
      table_contents.setFont(nextRow, 9, plainFont);
      table_contents.setAlignment(nextRow, 9, 34);
      table_contents.setObject(nextRow, 9, "/");
      table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
      table_contents.setFont(nextRow, 12, plainFont);
      table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
      table_contents.setAlignment(nextRow, 12, 33);
      nextRow++;
      check = "";
      if (bomCassetteDetail.jCardStatusIndicator) {
        check = "x";
      } else {
        check = "";
      } 
      String supplierID = Integer.toString(bomCassetteDetail.jCardSupplierId);
      LookupObject supplier = MilestoneHelper.getSupplierById(supplierID);
      String supplierName = (supplier == null) ? "" : supplier.getName();
      String bomJCardInk1 = String.valueOf(bomCassetteDetail.jCardInk1) + "/" + 
        bomCassetteDetail.jCardInk2;
      String jcardPanels = bomCassetteDetail.jCardPanels;
      jcardPanels = (jcardPanels == null || jcardPanels.trim().equals("")) ? 
        "" : ("Panels: " + jcardPanels + "  ");
      addInfoText = String.valueOf(jcardPanels) + bomCassetteDetail.jCardInfo;
      table_contents.setRowBorderColor(nextRow, 1, Color.white);
      table_contents.setRowBorderColor(nextRow, 4, Color.white);
      table_contents.setRowBorderColor(nextRow, 8, Color.white);
      table_contents.setRowBorderColor(nextRow, 10, Color.white);
      table_contents.setRowBorderColor(nextRow, 11, Color.white);
      table_contents.setFont(nextRow, 0, boldFont);
      table_contents.setAlignment(nextRow, 0, 34);
      table_contents.setObject(nextRow, 0, replaceQuote(check));
      table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
      table_contents.setFont(nextRow, 2, plainFont);
      table_contents.setAlignment(nextRow, 2, 33);
      table_contents.setObject(nextRow, 2, "J-Card");
      table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
      table_contents.setAlignment(nextRow, 5, 33);
      table_contents.setFont(nextRow, 5, plainFont);
      table_contents.setObject(nextRow, 5, replaceQuote(supplierName));
      table_contents.setFont(nextRow, 9, plainFont);
      table_contents.setAlignment(nextRow, 9, 34);
      table_contents.setObject(nextRow, 9, replaceQuote(bomJCardInk1));
      table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
      table_contents.setFont(nextRow, 12, plainFont);
      table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
      table_contents.setAlignment(nextRow, 12, 33);
      nextRow++;
      check = "";
      if (bomCassetteDetail.uCardStatusIndicator) {
        check = "x";
      } else {
        check = "";
      } 
      supplierID = Integer.toString(bomCassetteDetail.uCardSupplierId);
      supplier = MilestoneHelper.getSupplierById(supplierID);
      supplierName = (supplier == null) ? "" : supplier.getName();
      String bomUCardInk1 = String.valueOf(bomCassetteDetail.uCardInk1) + "/" + 
        bomCassetteDetail.uCardInk2;
      String ucardPanels = bomCassetteDetail.uCardPanels;
      ucardPanels = (ucardPanels == null || ucardPanels.trim().equals("")) ? 
        "" : ("Panels: " + ucardPanels + "  ");
      addInfoText = String.valueOf(ucardPanels) + bomCassetteDetail.uCardInfo;
      table_contents.setRowBorderColor(nextRow, 1, Color.white);
      table_contents.setRowBorderColor(nextRow, 4, Color.white);
      table_contents.setRowBorderColor(nextRow, 8, Color.white);
      table_contents.setRowBorderColor(nextRow, 10, Color.white);
      table_contents.setRowBorderColor(nextRow, 11, Color.white);
      table_contents.setFont(nextRow, 0, boldFont);
      table_contents.setAlignment(nextRow, 0, 34);
      table_contents.setObject(nextRow, 0, replaceQuote(check));
      table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
      table_contents.setFont(nextRow, 2, plainFont);
      table_contents.setAlignment(nextRow, 2, 33);
      table_contents.setObject(nextRow, 2, "U-Card");
      table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
      table_contents.setAlignment(nextRow, 5, 33);
      table_contents.setFont(nextRow, 5, plainFont);
      table_contents.setObject(nextRow, 5, replaceQuote(supplierName));
      table_contents.setFont(nextRow, 9, plainFont);
      table_contents.setAlignment(nextRow, 9, 34);
      table_contents.setObject(nextRow, 9, replaceQuote(bomUCardInk1));
      table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
      table_contents.setFont(nextRow, 12, plainFont);
      table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
      table_contents.setAlignment(nextRow, 12, 33);
      nextRow++;
      check = "";
      if (bomCassetteDetail.oCardStatusIndicator) {
        check = "x";
      } else {
        check = "";
      } 
      supplierID = Integer.toString(bomCassetteDetail.oCardSupplierId);
      supplier = MilestoneHelper.getSupplierById(supplierID);
      supplierName = (supplier == null) ? "" : supplier.getName();
      String bomOCardInk1 = String.valueOf(bomCassetteDetail.oCardInk1) + "/" + 
        bomCassetteDetail.oCardInk2;
      addInfoText = bomCassetteDetail.oCardInfo;
      table_contents.setRowBorderColor(nextRow, 1, Color.white);
      table_contents.setRowBorderColor(nextRow, 4, Color.white);
      table_contents.setRowBorderColor(nextRow, 8, Color.white);
      table_contents.setRowBorderColor(nextRow, 10, Color.white);
      table_contents.setRowBorderColor(nextRow, 11, Color.white);
      table_contents.setFont(nextRow, 0, boldFont);
      table_contents.setAlignment(nextRow, 0, 34);
      table_contents.setObject(nextRow, 0, replaceQuote(check));
      table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
      table_contents.setFont(nextRow, 2, plainFont);
      table_contents.setAlignment(nextRow, 2, 33);
      table_contents.setObject(nextRow, 2, "O-Card");
      table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
      table_contents.setAlignment(nextRow, 5, 33);
      table_contents.setFont(nextRow, 5, plainFont);
      table_contents.setObject(nextRow, 5, replaceQuote(supplierName));
      table_contents.setFont(nextRow, 9, plainFont);
      table_contents.setAlignment(nextRow, 9, 34);
      table_contents.setObject(nextRow, 9, replaceQuote(bomOCardInk1));
      table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
      table_contents.setFont(nextRow, 12, plainFont);
      table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
      table_contents.setAlignment(nextRow, 12, 33);
      nextRow++;
      check = "";
      if (bomCassetteDetail.stickerOneCardStatusIndicator) {
        check = "x";
      } else {
        check = "";
      } 
      supplierID = Integer.toString(bomCassetteDetail.stickerOneCardSupplierId);
      supplier = MilestoneHelper.getSupplierById(supplierID);
      supplierName = (supplier == null) ? "" : supplier.getName();
      String bomSticker1Ink1 = String.valueOf(bomCassetteDetail.stickerOneCardInk1) + "/" + 
        bomCassetteDetail.stickerOneCardInk2;
      String sticker1Places = bomCassetteDetail.stickerOneCardPlaces;
      sticker1Places = (sticker1Places == null || sticker1Places.trim().equals("")) ? 
        "" : ("Places: " + sticker1Places + "  ");
      addInfoText = String.valueOf(sticker1Places) + bomCassetteDetail.stickerOneCardInfo;
      table_contents.setRowBorderColor(nextRow, 1, Color.white);
      table_contents.setRowBorderColor(nextRow, 4, Color.white);
      table_contents.setRowBorderColor(nextRow, 8, Color.white);
      table_contents.setRowBorderColor(nextRow, 10, Color.white);
      table_contents.setRowBorderColor(nextRow, 11, Color.white);
      table_contents.setFont(nextRow, 0, boldFont);
      table_contents.setAlignment(nextRow, 0, 34);
      table_contents.setObject(nextRow, 0, replaceQuote(check));
      table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
      table_contents.setFont(nextRow, 2, plainFont);
      table_contents.setAlignment(nextRow, 2, 33);
      table_contents.setObject(nextRow, 2, "Sticker1");
      table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
      table_contents.setAlignment(nextRow, 5, 33);
      table_contents.setFont(nextRow, 5, plainFont);
      table_contents.setObject(nextRow, 5, replaceQuote(supplierName));
      table_contents.setFont(nextRow, 9, plainFont);
      table_contents.setAlignment(nextRow, 9, 34);
      table_contents.setObject(nextRow, 9, replaceQuote(bomSticker1Ink1));
      table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
      table_contents.setFont(nextRow, 12, plainFont);
      table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
      table_contents.setAlignment(nextRow, 12, 33);
      nextRow++;
      check = "";
      if (bomCassetteDetail.stickerTwoCardStatusIndicator) {
        check = "x";
      } else {
        check = "";
      } 
      supplierID = Integer.toString(bomCassetteDetail.stickerTwoCardSupplierId);
      supplier = MilestoneHelper.getSupplierById(supplierID);
      supplierName = (supplier == null) ? "" : supplier.getName();
      String bomSticker2Ink1 = String.valueOf(bomCassetteDetail.stickerTwoCardInk1) + "/" + 
        bomCassetteDetail.stickerTwoCardInk2;
      String sticker2Places = bomCassetteDetail.stickerTwoCardPlaces;
      sticker2Places = (sticker2Places == null || sticker2Places.trim().equals("")) ? 
        "" : ("Places: " + sticker2Places + "  ");
      addInfoText = String.valueOf(sticker2Places) + bomCassetteDetail.stickerTwoCardInfo;
      table_contents.setRowBorderColor(nextRow, 1, Color.white);
      table_contents.setRowBorderColor(nextRow, 4, Color.white);
      table_contents.setRowBorderColor(nextRow, 8, Color.white);
      table_contents.setRowBorderColor(nextRow, 10, Color.white);
      table_contents.setRowBorderColor(nextRow, 11, Color.white);
      table_contents.setFont(nextRow, 0, boldFont);
      table_contents.setAlignment(nextRow, 0, 34);
      table_contents.setObject(nextRow, 0, replaceQuote(check));
      table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
      table_contents.setFont(nextRow, 2, plainFont);
      table_contents.setAlignment(nextRow, 2, 33);
      table_contents.setObject(nextRow, 2, "Sticker2");
      table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
      table_contents.setAlignment(nextRow, 5, 33);
      table_contents.setFont(nextRow, 5, plainFont);
      table_contents.setObject(nextRow, 5, replaceQuote(supplierName));
      table_contents.setFont(nextRow, 9, plainFont);
      table_contents.setAlignment(nextRow, 9, 34);
      table_contents.setObject(nextRow, 9, replaceQuote(bomSticker2Ink1));
      table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
      table_contents.setFont(nextRow, 12, plainFont);
      table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
      table_contents.setAlignment(nextRow, 12, 33);
      nextRow++;
      check = "";
      if (bomCassetteDetail.otherStatusIndicator) {
        check = "x";
      } else {
        check = "";
      } 
      supplierID = Integer.toString(bomCassetteDetail.otherSupplierId);
      supplier = MilestoneHelper.getSupplierById(supplierID);
      supplierName = (supplier == null) ? "" : supplier.getName();
      String bomOtherInk1 = String.valueOf(bomCassetteDetail.otherInk1) + "/" + 
        bomCassetteDetail.otherInk2;
      addInfoText = bomCassetteDetail.otherInfo;
      table_contents.setRowBorderColor(nextRow, 1, Color.white);
      table_contents.setRowBorderColor(nextRow, 4, Color.white);
      table_contents.setRowBorderColor(nextRow, 8, Color.white);
      table_contents.setRowBorderColor(nextRow, 10, Color.white);
      table_contents.setRowBorderColor(nextRow, 11, Color.white);
      table_contents.setFont(nextRow, 0, boldFont);
      table_contents.setAlignment(nextRow, 0, 34);
      table_contents.setObject(nextRow, 0, replaceQuote(check));
      table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
      table_contents.setFont(nextRow, 2, plainFont);
      table_contents.setAlignment(nextRow, 2, 33);
      table_contents.setObject(nextRow, 2, "Other");
      table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
      table_contents.setAlignment(nextRow, 5, 33);
      table_contents.setFont(nextRow, 5, plainFont);
      table_contents.setObject(nextRow, 5, replaceQuote(supplierName));
      table_contents.setFont(nextRow, 9, plainFont);
      table_contents.setAlignment(nextRow, 9, 34);
      table_contents.setObject(nextRow, 9, replaceQuote(bomOtherInk1));
      table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
      table_contents.setFont(nextRow, 12, plainFont);
      table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
      table_contents.setAlignment(nextRow, 12, 33);
      nextRow++;
      table_contents.setRowBorderColor(nextRow, Color.white);
      table_contents.setRowHeight(nextRow, 10);
      nextRow++;
      table_contents.setRowBorderColor(nextRow, Color.white);
      table_contents.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
      table_contents.setSpan(nextRow, 0, new Dimension(6, 1));
      table_contents.setFont(nextRow, 0, new Font("Arial", 1, 12));
      table_contents.setAlignment(nextRow, 0, 9);
      table_contents.setObject(nextRow, 0, "Special Instructions:");
      table_contents.setSpan(nextRow, 6, new Dimension(14, 1));
      table_contents.setFont(nextRow, 6, new Font("Arial", 0, 11));
      table_contents.setAlignment(nextRow, 6, 9);
      String specialInstructionsString = "";
      if (bom.getSpecialInstructions().length() > 0)
        specialInstructionsString = bom.getSpecialInstructions(); 
      table_contents.setObject(nextRow, 6, String.valueOf(replaceQuote(specialInstructionsString)) + "\n");
      report.setElement("table_colheaders", table_contents);
    } 
  }
  
  private void addVinylReportElements(XStyleSheet report, Bom bom, Context context, DefaultTableLens table_contents, Selection mySelection) {
    if (bom != null) {
      BomVinylDetail bomVinylDetail = bom.getBomVinylDetail();
      table_contents.setRowInsets(10, new Insets(1, 0, 0, 0));
      table_contents.setRowInsets(11, new Insets(1, 0, 0, 0));
      table_contents.setRowInsets(12, new Insets(1, 0, 0, 0));
      table_contents.setRowInsets(13, new Insets(1, 0, 0, 0));
      table_contents.setRowInsets(14, new Insets(1, 0, 0, 0));
      table_contents.setRowInsets(15, new Insets(1, 0, 0, 0));
      table_contents.setRowInsets(16, new Insets(1, 0, 0, 0));
      table_contents.setRowInsets(17, new Insets(1, 0, 0, 0));
      table_contents.setRowInsets(18, new Insets(1, 0, 0, 0));
      table_contents.setRowInsets(19, new Insets(1, 0, 0, 0));
      table_contents.setRowInsets(20, new Insets(1, 0, 0, 0));
      table_contents.setRowInsets(21, new Insets(1, 0, 0, 0));
      table_contents.setRowInsets(22, new Insets(1, 0, 0, 0));
      table_contents.setRowInsets(23, new Insets(1, 0, 0, 0));
      table_contents.setRowInsets(24, new Insets(1, 0, 0, 0));
      table_contents.setRowInsets(25, new Insets(1, 0, 0, 0));
      table_contents.setRowInsets(26, new Insets(1, 0, 0, 0));
      table_contents.setRowInsets(27, new Insets(1, 0, 0, 0));
      table_contents.setRowInsets(28, new Insets(1, 0, 0, 0));
      table_contents.setRowInsets(29, new Insets(1, 0, 0, 0));
      table_contents.setRowInsets(30, new Insets(1, 0, 0, 0));
      int nextRow = 12;
      Font boldFont = new Font("Arial", 1, 10);
      Font plainFont = new Font("Arial", 0, 9);
      String stringUnitsPerPkg = "";
      if (mySelection.getNumberOfUnits() > 0 && 
        !Integer.toString(mySelection.getNumberOfUnits()).equals(""))
        stringUnitsPerPkg = Integer.toString(mySelection.getNumberOfUnits()); 
      String noShrinkwrap = context.getRequestValue("UseNoShrinkWrap");
      if (bom.useShrinkWrap()) {
        noShrinkwrap = "X";
      } else {
        noShrinkwrap = "";
      } 
      table_contents.setRowInsets(nextRow, new Insets(1, 0, 0, 0));
      table_contents.setRowBorderColor(nextRow, 0, Color.white);
      table_contents.setRowBorderColor(nextRow, 1, Color.white);
      table_contents.setRowBorderColor(nextRow, 2, Color.white);
      table_contents.setRowBorderColor(nextRow, 3, Color.white);
      table_contents.setRowBorderColor(nextRow, 4, Color.white);
      table_contents.setRowBorderColor(nextRow, 5, Color.white);
      table_contents.setRowBorderColor(nextRow, 6, Color.white);
      table_contents.setRowBorderColor(nextRow, 7, Color.white);
      table_contents.setRowBorderColor(nextRow, 8, Color.white);
      table_contents.setRowBorderColor(nextRow, 9, Color.white);
      table_contents.setRowBorderColor(nextRow, 12, Color.white);
      table_contents.setRowBorderColor(nextRow, 13, Color.white);
      table_contents.setRowBorderColor(nextRow, 14, Color.white);
      table_contents.setRowBorderColor(nextRow, 16, Color.white);
      table_contents.setRowBorderColor(nextRow, 17, Color.white);
      table_contents.setSpan(nextRow, 0, new Dimension(7, 1));
      table_contents.setFont(nextRow, 0, boldFont);
      table_contents.setAlignment(nextRow, 0, 33);
      table_contents.setObject(nextRow, 0, "Vinyl");
      table_contents.setSpan(nextRow, 7, new Dimension(3, 1));
      table_contents.setFont(nextRow, 7, boldFont);
      table_contents.setAlignment(nextRow, 7, 36);
      table_contents.setObject(nextRow, 7, "# of Units:");
      table_contents.setSpan(nextRow, 10, new Dimension(2, 1));
      table_contents.setAlignment(nextRow, 10, 34);
      table_contents.setFont(nextRow, 10, plainFont);
      table_contents.setObject(nextRow, 10, replaceQuote(stringUnitsPerPkg));
      String configuration = "";
      if (bom.getConfiguration() != null)
        configuration = bom.getConfiguration(); 
      table_contents.setSpan(nextRow, 13, new Dimension(2, 1));
      table_contents.setFont(nextRow, 13, boldFont);
      table_contents.setAlignment(nextRow, 13, 36);
      table_contents.setObject(nextRow, 13, "Configuration:");
      table_contents.setFont(nextRow, 15, new Font("Arial", 0, 10));
      table_contents.setObject(nextRow, 15, replaceQuote(configuration));
      table_contents.setAlignment(nextRow, 15, 34);
      table_contents.setSpan(nextRow, 16, new Dimension(2, 1));
      table_contents.setFont(nextRow, 16, boldFont);
      table_contents.setAlignment(nextRow, 16, 36);
      table_contents.setObject(nextRow, 16, "Shrink Wrap:");
      table_contents.setSpan(nextRow, 18, new Dimension(2, 1));
      table_contents.setFont(nextRow, 18, new Font("Arial", 0, 10));
      table_contents.setObject(nextRow, 18, replaceQuote(noShrinkwrap));
      table_contents.setAlignment(nextRow, 18, 34);
      nextRow++;
      table_contents.setRowHeight(nextRow, 5);
      table_contents.setRowBorderColor(nextRow, Color.black);
      nextRow++;
      table_contents.setRowInsets(nextRow, new Insets(1, 0, 0, 0));
      table_contents.setRowBorderColor(nextRow, Color.white);
      table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
      table_contents.setFont(nextRow, 2, boldFont);
      table_contents.setAlignment(nextRow, 2, 33);
      table_contents.setObject(nextRow, 2, "Part");
      table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
      table_contents.setAlignment(nextRow, 5, 33);
      table_contents.setFont(nextRow, 5, boldFont);
      table_contents.setObject(nextRow, 5, "Supplier");
      table_contents.setFont(nextRow, 9, boldFont);
      table_contents.setAlignment(nextRow, 9, 34);
      table_contents.setObject(nextRow, 9, "Ink");
      table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
      table_contents.setFont(nextRow, 12, boldFont);
      table_contents.setObject(nextRow, 12, "Additional Information");
      table_contents.setAlignment(nextRow, 12, 33);
      nextRow++;
      String check = "";
      if (bomVinylDetail.recordStatusIndicator) {
        check = "x";
      } else {
        check = "";
      } 
      String supplierID = Integer.toString(bomVinylDetail.recordSupplierId);
      LookupObject supplier = MilestoneHelper.getSupplierById(supplierID);
      String supplierName = (supplier == null) ? "" : supplier.getName();
      String recordColor = bomVinylDetail.recordColor;
      recordColor = (recordColor == null || recordColor.length() <= 1) ? 
        "" : ("Color: " + recordColor + "  ");
      String addInfoText = String.valueOf(recordColor) + bomVinylDetail.recordInfo;
      table_contents.setRowBorderColor(nextRow, 1, Color.white);
      table_contents.setRowBorderColor(nextRow, 4, Color.white);
      table_contents.setRowBorderColor(nextRow, 8, Color.white);
      table_contents.setRowBorderColor(nextRow, 10, Color.white);
      table_contents.setRowBorderColor(nextRow, 11, Color.white);
      table_contents.setFont(nextRow, 0, boldFont);
      table_contents.setAlignment(nextRow, 0, 34);
      table_contents.setObject(nextRow, 0, replaceQuote(check));
      table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
      table_contents.setFont(nextRow, 2, plainFont);
      table_contents.setAlignment(nextRow, 2, 33);
      table_contents.setObject(nextRow, 2, "Record");
      table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
      table_contents.setAlignment(nextRow, 5, 33);
      table_contents.setFont(nextRow, 5, plainFont);
      table_contents.setObject(nextRow, 5, replaceQuote(supplierName));
      table_contents.setFont(nextRow, 9, plainFont);
      table_contents.setAlignment(nextRow, 9, 34);
      table_contents.setObject(nextRow, 9, "/");
      table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
      table_contents.setFont(nextRow, 12, plainFont);
      table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
      table_contents.setAlignment(nextRow, 12, 33);
      nextRow++;
      check = "";
      if (bomVinylDetail.labelStatusIndicator) {
        check = "x";
      } else {
        check = "";
      } 
      supplierID = Integer.toString(bomVinylDetail.labelSupplierId);
      supplier = MilestoneHelper.getSupplierById(supplierID);
      supplierName = (supplier == null) ? "" : supplier.getName();
      String bomLabelInk1 = String.valueOf(bomVinylDetail.labelInk1) + "/" + 
        bomVinylDetail.labelInk2;
      addInfoText = bomVinylDetail.labelInfo;
      table_contents.setRowBorderColor(nextRow, 1, Color.white);
      table_contents.setRowBorderColor(nextRow, 4, Color.white);
      table_contents.setRowBorderColor(nextRow, 8, Color.white);
      table_contents.setRowBorderColor(nextRow, 10, Color.white);
      table_contents.setRowBorderColor(nextRow, 11, Color.white);
      table_contents.setFont(nextRow, 0, boldFont);
      table_contents.setAlignment(nextRow, 0, 34);
      table_contents.setObject(nextRow, 0, replaceQuote(check));
      table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
      table_contents.setFont(nextRow, 2, plainFont);
      table_contents.setAlignment(nextRow, 2, 33);
      table_contents.setObject(nextRow, 2, "Label");
      table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
      table_contents.setAlignment(nextRow, 5, 33);
      table_contents.setFont(nextRow, 5, plainFont);
      table_contents.setObject(nextRow, 5, replaceQuote(supplierName));
      table_contents.setFont(nextRow, 9, plainFont);
      table_contents.setAlignment(nextRow, 9, 34);
      table_contents.setObject(nextRow, 9, replaceQuote(bomLabelInk1));
      table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
      table_contents.setFont(nextRow, 12, plainFont);
      table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
      table_contents.setAlignment(nextRow, 12, 33);
      nextRow++;
      check = "";
      if (bomVinylDetail.sleeveStatusIndicator) {
        check = "x";
      } else {
        check = "";
      } 
      supplierID = Integer.toString(bomVinylDetail.sleeveSupplierId);
      supplier = MilestoneHelper.getSupplierById(supplierID);
      supplierName = (supplier == null) ? "" : supplier.getName();
      String bomSleveInk1 = String.valueOf(bomVinylDetail.sleeveInk1) + "/" + 
        bomVinylDetail.sleeveInk2;
      addInfoText = bomVinylDetail.sleeveInfo;
      table_contents.setRowBorderColor(nextRow, 1, Color.white);
      table_contents.setRowBorderColor(nextRow, 4, Color.white);
      table_contents.setRowBorderColor(nextRow, 8, Color.white);
      table_contents.setRowBorderColor(nextRow, 10, Color.white);
      table_contents.setRowBorderColor(nextRow, 11, Color.white);
      table_contents.setFont(nextRow, 0, boldFont);
      table_contents.setAlignment(nextRow, 0, 34);
      table_contents.setObject(nextRow, 0, replaceQuote(check));
      table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
      table_contents.setFont(nextRow, 2, plainFont);
      table_contents.setAlignment(nextRow, 2, 33);
      table_contents.setObject(nextRow, 2, "Sleeve");
      table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
      table_contents.setAlignment(nextRow, 5, 33);
      table_contents.setFont(nextRow, 5, plainFont);
      table_contents.setObject(nextRow, 5, replaceQuote(supplierName));
      table_contents.setFont(nextRow, 9, plainFont);
      table_contents.setAlignment(nextRow, 9, 34);
      table_contents.setObject(nextRow, 9, replaceQuote(bomSleveInk1));
      table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
      table_contents.setFont(nextRow, 12, plainFont);
      table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
      table_contents.setAlignment(nextRow, 12, 33);
      nextRow++;
      check = "";
      if (bomVinylDetail.jacketStatusIndicator) {
        check = "x";
      } else {
        check = "";
      } 
      supplierID = Integer.toString(bomVinylDetail.jacketSupplierId);
      supplier = MilestoneHelper.getSupplierById(supplierID);
      supplierName = (supplier == null) ? "" : supplier.getName();
      String bomJacketInk1 = String.valueOf(bomVinylDetail.jacketInk1) + "/" + 
        bomVinylDetail.jacketInk2;
      addInfoText = bomVinylDetail.jacketInfo;
      table_contents.setRowBorderColor(nextRow, 1, Color.white);
      table_contents.setRowBorderColor(nextRow, 4, Color.white);
      table_contents.setRowBorderColor(nextRow, 8, Color.white);
      table_contents.setRowBorderColor(nextRow, 10, Color.white);
      table_contents.setRowBorderColor(nextRow, 11, Color.white);
      table_contents.setFont(nextRow, 0, boldFont);
      table_contents.setAlignment(nextRow, 0, 34);
      table_contents.setObject(nextRow, 0, replaceQuote(check));
      table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
      table_contents.setFont(nextRow, 2, plainFont);
      table_contents.setAlignment(nextRow, 2, 33);
      table_contents.setObject(nextRow, 2, "Jacket");
      table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
      table_contents.setAlignment(nextRow, 5, 33);
      table_contents.setFont(nextRow, 5, plainFont);
      table_contents.setObject(nextRow, 5, replaceQuote(supplierName));
      table_contents.setFont(nextRow, 9, plainFont);
      table_contents.setAlignment(nextRow, 9, 34);
      table_contents.setObject(nextRow, 9, replaceQuote(bomJacketInk1));
      table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
      table_contents.setFont(nextRow, 12, plainFont);
      table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
      table_contents.setAlignment(nextRow, 12, 33);
      nextRow++;
      check = "";
      if (bomVinylDetail.insertStatusIndicator) {
        check = "x";
      } else {
        check = "";
      } 
      supplierID = Integer.toString(bomVinylDetail.insertSupplierId);
      supplier = MilestoneHelper.getSupplierById(supplierID);
      supplierName = (supplier == null) ? "" : supplier.getName();
      String bomInsertInk1 = String.valueOf(bomVinylDetail.insertInk1) + "/" + 
        bomVinylDetail.insertInk2;
      addInfoText = bomVinylDetail.insertInfo;
      table_contents.setRowBorderColor(nextRow, 1, Color.white);
      table_contents.setRowBorderColor(nextRow, 4, Color.white);
      table_contents.setRowBorderColor(nextRow, 8, Color.white);
      table_contents.setRowBorderColor(nextRow, 10, Color.white);
      table_contents.setRowBorderColor(nextRow, 11, Color.white);
      table_contents.setFont(nextRow, 0, boldFont);
      table_contents.setAlignment(nextRow, 0, 34);
      table_contents.setObject(nextRow, 0, replaceQuote(check));
      table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
      table_contents.setFont(nextRow, 2, plainFont);
      table_contents.setAlignment(nextRow, 2, 33);
      table_contents.setObject(nextRow, 2, "Insert");
      table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
      table_contents.setAlignment(nextRow, 5, 33);
      table_contents.setFont(nextRow, 5, plainFont);
      table_contents.setObject(nextRow, 5, replaceQuote(supplierName));
      table_contents.setFont(nextRow, 9, plainFont);
      table_contents.setAlignment(nextRow, 9, 34);
      table_contents.setObject(nextRow, 9, replaceQuote(bomInsertInk1));
      table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
      table_contents.setFont(nextRow, 12, plainFont);
      table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
      table_contents.setAlignment(nextRow, 12, 33);
      nextRow++;
      check = "";
      if (bomVinylDetail.stickerOneStatusIndicator) {
        check = "x";
      } else {
        check = "";
      } 
      supplierID = Integer.toString(bomVinylDetail.stickerOneSupplierId);
      supplier = MilestoneHelper.getSupplierById(supplierID);
      supplierName = (supplier == null) ? "" : supplier.getName();
      String bomSticker1Ink1 = String.valueOf(bomVinylDetail.stickerOneInk1) + "/" + 
        bomVinylDetail.stickerOneInk2;
      String sticker1Places = bomVinylDetail.stickerOnePlaces;
      sticker1Places = (sticker1Places == null || sticker1Places.trim().equals("")) ? 
        "" : ("Places: " + sticker1Places + "  ");
      addInfoText = String.valueOf(sticker1Places) + bomVinylDetail.stickerOneInfo;
      table_contents.setRowBorderColor(nextRow, 1, Color.white);
      table_contents.setRowBorderColor(nextRow, 4, Color.white);
      table_contents.setRowBorderColor(nextRow, 8, Color.white);
      table_contents.setRowBorderColor(nextRow, 10, Color.white);
      table_contents.setRowBorderColor(nextRow, 11, Color.white);
      table_contents.setFont(nextRow, 0, boldFont);
      table_contents.setAlignment(nextRow, 0, 34);
      table_contents.setObject(nextRow, 0, replaceQuote(check));
      table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
      table_contents.setFont(nextRow, 2, plainFont);
      table_contents.setAlignment(nextRow, 2, 33);
      table_contents.setObject(nextRow, 2, "Sticker1");
      table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
      table_contents.setAlignment(nextRow, 5, 33);
      table_contents.setFont(nextRow, 5, plainFont);
      table_contents.setObject(nextRow, 5, replaceQuote(supplierName));
      table_contents.setFont(nextRow, 9, plainFont);
      table_contents.setAlignment(nextRow, 9, 34);
      table_contents.setObject(nextRow, 9, replaceQuote(bomSticker1Ink1));
      table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
      table_contents.setFont(nextRow, 12, plainFont);
      table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
      table_contents.setAlignment(nextRow, 12, 33);
      nextRow++;
      check = "";
      if (bomVinylDetail.stickerTwoStatusIndicator) {
        check = "x";
      } else {
        check = "";
      } 
      supplierID = Integer.toString(bomVinylDetail.stickerTwoSupplierId);
      supplier = MilestoneHelper.getSupplierById(supplierID);
      supplierName = (supplier == null) ? "" : supplier.getName();
      String bomSticker2Ink1 = String.valueOf(bomVinylDetail.stickerTwoInk1) + "/" + 
        bomVinylDetail.stickerTwoInk2;
      String sticker2Places = bomVinylDetail.stickerTwoPlaces;
      sticker2Places = (sticker2Places == null || sticker2Places.trim().equals("")) ? 
        "" : ("Places: " + sticker2Places + "  ");
      addInfoText = String.valueOf(sticker2Places) + bomVinylDetail.stickerTwoInfo;
      table_contents.setRowBorderColor(nextRow, 1, Color.white);
      table_contents.setRowBorderColor(nextRow, 4, Color.white);
      table_contents.setRowBorderColor(nextRow, 8, Color.white);
      table_contents.setRowBorderColor(nextRow, 10, Color.white);
      table_contents.setRowBorderColor(nextRow, 11, Color.white);
      table_contents.setFont(nextRow, 0, boldFont);
      table_contents.setAlignment(nextRow, 0, 34);
      table_contents.setObject(nextRow, 0, replaceQuote(check));
      table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
      table_contents.setFont(nextRow, 2, plainFont);
      table_contents.setAlignment(nextRow, 2, 33);
      table_contents.setObject(nextRow, 2, "Sticker2");
      table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
      table_contents.setAlignment(nextRow, 5, 33);
      table_contents.setFont(nextRow, 5, plainFont);
      table_contents.setObject(nextRow, 5, replaceQuote(supplierName));
      table_contents.setFont(nextRow, 9, plainFont);
      table_contents.setAlignment(nextRow, 9, 34);
      table_contents.setObject(nextRow, 9, replaceQuote(bomSticker2Ink1));
      table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
      table_contents.setFont(nextRow, 12, plainFont);
      table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
      table_contents.setAlignment(nextRow, 12, 33);
      nextRow++;
      check = "";
      if (bomVinylDetail.otherStatusIndicator) {
        check = "x";
      } else {
        check = "";
      } 
      supplierID = Integer.toString(bomVinylDetail.otherSupplierId);
      supplier = MilestoneHelper.getSupplierById(supplierID);
      supplierName = (supplier == null) ? "" : supplier.getName();
      String bomOtherInk1 = String.valueOf(bomVinylDetail.otherInk1) + "/" + 
        bomVinylDetail.otherInk2;
      addInfoText = bomVinylDetail.otherInfo;
      table_contents.setRowBorderColor(nextRow, 1, Color.white);
      table_contents.setRowBorderColor(nextRow, 4, Color.white);
      table_contents.setRowBorderColor(nextRow, 8, Color.white);
      table_contents.setRowBorderColor(nextRow, 10, Color.white);
      table_contents.setRowBorderColor(nextRow, 11, Color.white);
      table_contents.setFont(nextRow, 0, boldFont);
      table_contents.setAlignment(nextRow, 0, 34);
      table_contents.setObject(nextRow, 0, replaceQuote(check));
      table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
      table_contents.setFont(nextRow, 2, plainFont);
      table_contents.setAlignment(nextRow, 2, 33);
      table_contents.setObject(nextRow, 2, "Other");
      table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
      table_contents.setAlignment(nextRow, 5, 33);
      table_contents.setFont(nextRow, 5, plainFont);
      table_contents.setObject(nextRow, 5, replaceQuote(supplierName));
      table_contents.setFont(nextRow, 9, plainFont);
      table_contents.setAlignment(nextRow, 9, 34);
      table_contents.setObject(nextRow, 9, replaceQuote(bomOtherInk1));
      table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
      table_contents.setFont(nextRow, 12, plainFont);
      table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
      table_contents.setAlignment(nextRow, 12, 33);
      nextRow++;
      table_contents.setRowBorderColor(nextRow, Color.white);
      table_contents.setRowHeight(nextRow, 10);
      nextRow++;
      table_contents.setRowBorderColor(nextRow, Color.white);
      table_contents.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
      table_contents.setSpan(nextRow, 0, new Dimension(6, 1));
      table_contents.setFont(nextRow, 0, new Font("Arial", 1, 12));
      table_contents.setAlignment(nextRow, 0, 9);
      table_contents.setObject(nextRow, 0, "Special Instructions:");
      table_contents.setSpan(nextRow, 6, new Dimension(14, 1));
      table_contents.setFont(nextRow, 6, new Font("Arial", 0, 11));
      table_contents.setAlignment(nextRow, 6, 9);
      String specialInstructionsString = "";
      if (bom.getSpecialInstructions().length() > 0)
        specialInstructionsString = bom.getSpecialInstructions(); 
      table_contents.setObject(nextRow, 6, String.valueOf(replaceQuote(specialInstructionsString)) + "\n");
      report.setElement("table_colheaders", table_contents);
    } 
  }
  
  private void setTextBoxBottomBorder(XStyleSheet report, String key) {
    try {
      TextBoxElement textBoxElement = (TextBoxElement)report.getElement(key);
    } catch (Exception exception) {}
  }
  
  protected Form addSelectionSearchElements(Context context, Selection selection, Form form) {
    context.putDelivery("selectionArrays", String.valueOf(Cache.getJavaScriptConfigArray("")) + Cache.getJavaScriptSubConfigArray("") + " " + ReleasingFamily.getJavaScriptCorporateArrayReleasingFamilySearch(context));
    FormCheckBox showAllSearch = new FormCheckBox("ShowAllSearch", "", "", false);
    showAllSearch.setId("ShowAllSearch");
    form.addElement(showAllSearch);
    Vector companies = MilestoneHelper.getUserCompanies(context);
    Vector labels = MilestoneHelper.getUserLabels(companies);
    labels = MilestoneHelper.removeUnusedCSO(labels, context, -1);
    FormDropDownMenu labelSearch = MilestoneHelper.getCorporateStructureDropDownDuplicates("LabelSearch", labels, "", false, true, false);
    labelSearch.setId("LabelSearch");
    form.addElement(labelSearch);
    Vector searchCompanies = null;
    searchCompanies = MilestoneHelper.getUserCompanies(context);
    searchCompanies = MilestoneHelper.removeUnusedCSO(searchCompanies, context, -1);
    FormDropDownMenu companySearch = MilestoneHelper.getCorporateStructureDropDown("CompanySearch", searchCompanies, "", false, true);
    companySearch.setId("CompanySearch");
    companySearch.addFormEvent("onChange", "return(clickCompanySearch(this))");
    form.addElement(companySearch);
    Vector labelContacts = SelectionManager.getLabelContactsExcludeUml(context);
    FormDropDownMenu searchContact = MilestoneHelper.getContactsDropDown(context, "ContactSearch", labelContacts, null, true);
    form.addElement(searchContact);
    Vector families = SelectionHandler.filterCSO(ReleasingFamily.getUserReleasingFamiliesVectorOfFamilies(context));
    FormDropDownMenu Family = MilestoneHelper.getCorporateStructureDropDown("FamilySearch", families, "-1", false, true);
    Family.addFormEvent("onChange", "return(clickFamilySearch(this))");
    Family.setId("FamilySearch");
    form.addElement(Family);
    Vector environments = MilestoneHelper.getUserEnvironments(context);
    Vector myCompanies = MilestoneHelper.getUserCompanies(context);
    environments = SelectionHandler.filterSelectionEnvironments(myCompanies);
    environments = MilestoneHelper.removeUnusedCSO(environments, context, -1);
    FormDropDownMenu envMenu = MilestoneHelper.getCorporateStructureDropDown("EnvironmentSearch", environments, "-1", false, true);
    envMenu.addFormEvent("onChange", "return(clickEnvironmentSearch(this))");
    envMenu.setId("EnvironmentSearch");
    form.addElement(envMenu);
    FormTextField streetDateSearch = new FormTextField("StreetDateSearch", "", false, 14, 10);
    streetDateSearch.addFormEvent("onBlur", "JavaScript:removeSpacesInDate(document.forms[0].StreetDateSearch.value,this)");
    streetDateSearch.setId("StreetDateSearch");
    form.addElement(streetDateSearch);
    FormTextField streetEndDateSearch = new FormTextField("StreetEndDateSearch", "", false, 14, 10);
    streetEndDateSearch.addFormEvent("onBlur", "JavaScript:removeSpacesInDate(document.forms[0].StreetEndDateSearch.value,this)");
    streetEndDateSearch.setId("StreetEndDateSearch");
    form.addElement(streetEndDateSearch);
    String[] dvalues = new String[3];
    dvalues[0] = "physical";
    dvalues[1] = "digital";
    dvalues[2] = "both";
    String[] dlabels = new String[3];
    dlabels[0] = "Physical";
    dlabels[1] = "Digital";
    dlabels[2] = "Both";
    FormRadioButtonGroup prodType = new FormRadioButtonGroup("ProdType", "both", dvalues, dlabels, false);
    prodType.addFormEvent("onClick", "buildSearchConfigs(this)");
    form.addElement(prodType);
    Vector searchConfigs = null;
    searchConfigs = Cache.getSelectionConfigs();
    FormDropDownMenu configSearch = MilestoneHelper.getSelectionConfigurationDropDown("ConfigSearch", "", false);
    configSearch.setId("ConfigSearch");
    configSearch.addFormEvent("onChange", "buildSearchSubConfigs(this.selectedIndex)");
    form.addElement(configSearch);
    FormDropDownMenu subconfigSearch = new FormDropDownMenu("SubconfigSearch", "");
    subconfigSearch.setId("SubconfigSearch");
    subconfigSearch.setEnabled(false);
    form.addElement(subconfigSearch);
    FormTextField upcSearch = new FormTextField("UPCSearch", "", false, 20, 20);
    upcSearch.setId("UPCSearch");
    form.addElement(upcSearch);
    FormTextField prefixSearch = new FormTextField("PrefixSearch", "", false, 6, 5);
    prefixSearch.setId("PrefixSearch");
    form.addElement(prefixSearch);
    FormTextField selectionSearch = new FormTextField("SelectionSearch", "", false, 12, 20);
    selectionSearch.setId("SelectionSearch");
    selectionSearch.setClassName("ctrlMedium");
    form.addElement(selectionSearch);
    FormTextField titleSearch = new FormTextField("TitleSearch", "", false, 20);
    titleSearch.setId("TitleSearch");
    form.addElement(titleSearch);
    FormTextField artistSearch = new FormTextField("ArtistSearch", "", false, 20);
    artistSearch.setId("ArtistSearch");
    form.addElement(artistSearch);
    FormTextField projectIDSearch = new FormTextField("ProjectIDSearch", "", false, 20);
    projectIDSearch.setId("ProjectIDSearch");
    form.addElement(projectIDSearch);
    SelectionHandler.getUserPreferences(form, context);
    return form;
  }
  
  private String getSearchJavaScriptCorporateArray(Context context) {
    StringBuffer result = new StringBuffer(100);
    String str = "";
    String value = new String();
    boolean foundFirstTemp = false;
    User user = (User)context.getSessionValue("user");
    Vector vUserCompanies = MilestoneHelper.getUserCompanies(context);
    HashMap corpHashMap = MilestoneHelper.buildActiveCorporateStructureHashMap();
    result.append("\n");
    result.append("var aSearch = new Array();\n");
    int arrayIndex = 0;
    result.append("aSearch[0] = new Array(");
    result.append(0);
    result.append(", '");
    result.append(" ");
    result.append('\'');
    foundFirstTemp = true;
    for (int a = 0; a < vUserCompanies.size(); a++) {
      Company ucTemp = (Company)vUserCompanies.elementAt(a);
      if (ucTemp != null) {
        Vector labels = Cache.getInstance().getLabels();
        for (int b = 0; b < labels.size(); b++) {
          Label node = (Label)labels.elementAt(b);
          if (node.getParent().getParentID() == ucTemp.getStructureID() && 
            !corpHashMap.containsKey(new Integer(node.getStructureID()))) {
            if (foundFirstTemp)
              result.append(','); 
            result.append(' ');
            result.append(node.getStructureID());
            result.append(", '");
            result.append(MilestoneHelper.urlEncode(node.getName()));
            result.append('\'');
            foundFirstTemp = true;
          } 
        } 
      } 
    } 
    if (!foundFirstTemp) {
      result.append("'[none available]');\n");
    } else {
      result.append(");\n");
    } 
    for (int i = 0; i < vUserCompanies.size(); i++) {
      Company uc = (Company)vUserCompanies.elementAt(i);
      if (uc != null) {
        result.append("aSearch[");
        result.append(uc.getStructureID());
        result.append("] = new Array(");
        boolean foundFirst = false;
        result.append(0);
        result.append(", '");
        result.append(" ");
        result.append('\'');
        foundFirst = true;
        Vector tmpArray = new Vector();
        Vector labels = Cache.getInstance().getLabels();
        for (int j = 0; j < labels.size(); j++) {
          Label node = (Label)labels.elementAt(j);
          if (node.getParent().getParentID() == uc.getStructureID() && 
            !corpHashMap.containsKey(new Integer(node.getStructureID()))) {
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
      } 
    } 
    return result.toString();
  }
  
  private boolean sortGroup(Dispatcher dispatcher, Context context) {
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
    dispatcher.redispatch(context, "bom-editor");
    return true;
  }
  
  private String replaceQuote(String str) {
    int s = 0;
    int e = 0;
    StringBuffer result = new StringBuffer();
    while ((e = str.indexOf("&quot;", s)) >= 0) {
      result.append(str.substring(s, e));
      result.append("\"");
      s = e + "&quot;".length();
    } 
    result.append(str.substring(s));
    return result.toString();
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\BomHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */