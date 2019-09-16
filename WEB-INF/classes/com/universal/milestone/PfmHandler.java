package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.gemini.Context;
import com.techempower.gemini.Dispatcher;
import com.techempower.gemini.FormCheckBox;
import com.techempower.gemini.FormDropDownMenu;
import com.techempower.gemini.FormElement;
import com.techempower.gemini.FormHidden;
import com.techempower.gemini.FormRadioButtonGroup;
import com.techempower.gemini.FormTextArea;
import com.techempower.gemini.FormTextField;
import com.techempower.gemini.FormValidation;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.Cache;
import com.universal.milestone.Company;
import com.universal.milestone.CompanyAcl;
import com.universal.milestone.EmailDistribution;
import com.universal.milestone.Environment;
import com.universal.milestone.Form;
import com.universal.milestone.Genre;
import com.universal.milestone.Label;
import com.universal.milestone.LookupObject;
import com.universal.milestone.MilestoneConstants;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.MilestoneHelper_2;
import com.universal.milestone.MilestoneInfrastructure;
import com.universal.milestone.MilestoneSecurity;
import com.universal.milestone.Notepad;
import com.universal.milestone.NotepadSortOrder;
import com.universal.milestone.Pfm;
import com.universal.milestone.PfmHandler;
import com.universal.milestone.PrefixCode;
import com.universal.milestone.ProjectSearchManager;
import com.universal.milestone.ReleasingFamily;
import com.universal.milestone.ReportHandler;
import com.universal.milestone.SecureHandler;
import com.universal.milestone.Selection;
import com.universal.milestone.SelectionHandler;
import com.universal.milestone.SelectionManager;
import com.universal.milestone.User;
import com.universal.milestone.UserManager;
import com.universal.milestone.jms.MessageObject;
import com.universal.milestone.push.PushCommunication;
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
import net.umusic.milestone.alps.DcGDRSResults;

public class PfmHandler extends SecureHandler {
  public static final String COMPONENT_CODE = "hPfm";
  
  public GeminiApplication application;
  
  public ComponentLog log;
  
  public PfmHandler(GeminiApplication application) {
    this.application = application;
    this.log = application.getLog("hPfm");
  }
  
  public String getDescription() { return "Pfm"; }
  
  public boolean acceptRequest(Dispatcher dispatcher, Context context, String command) {
    if (super.acceptRequest(dispatcher, context, command))
      if (command.startsWith("pfm"))
        return handleRequest(dispatcher, context, command);  
    return false;
  }
  
  public boolean handleRequest(Dispatcher dispatcher, Context context, String command) {
    EmailDistribution.removeSessionValues(context);
    if (command.equalsIgnoreCase("pfm-editor") || command.equalsIgnoreCase("pfm-edit-new"))
      edit(dispatcher, context, command); 
    if (command.equalsIgnoreCase("pfm-edit-save") || command.equalsIgnoreCase("pfm-edit-save-comment"))
      editSave(dispatcher, context, command); 
    if (command.equalsIgnoreCase("pfm-edit-copy"))
      editCopy(dispatcher, context, command); 
    if (command.equalsIgnoreCase("pfm-paste-copy"))
      pasteCopy(dispatcher, context, command); 
    if (command.equalsIgnoreCase("pfm-search"))
      search(dispatcher, context, command); 
    if (command.equalsIgnoreCase("pfm-sort"))
      sort(dispatcher, context, command); 
    if (command.equalsIgnoreCase("pfm-print-pdf"))
      print(dispatcher, context, command, 0, true, null); 
    if (command.equalsIgnoreCase("pfm-print-rtf"))
      print(dispatcher, context, command, 1, true, null); 
    if (command.equalsIgnoreCase("pfm-print-pdf4"))
      print(dispatcher, context, command, 0, false, null); 
    if (command.equalsIgnoreCase("pfm-print-rtf4"))
      print(dispatcher, context, command, 1, false, null); 
    if (command.equalsIgnoreCase("pfm-group"))
      sortGroup(dispatcher, context); 
    if (command.equalsIgnoreCase("pfm-send-email"))
      EmailDistribution.sendEmail(dispatcher, context, "pfm-editor", null); 
    return true;
  }
  
  private boolean edit(Dispatcher dispatcher, Context context, String command) {
    int selectionID = -1;
    Selection selection = null;
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 0);
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    selection = MilestoneHelper.getScreenSelection(context);
    context.putSessionValue("Selection", selection);
    if (selection != null) {
      selectionID = selection.getSelectionID();
      Pfm pfm = new Pfm();
      pfm = SelectionManager.getInstance().getPfm(selectionID);
      Form form = buildForm(context, pfm, command);
      if ((Pfm)context.getSessionValue("copiedPfm") != null && pfm == null)
        if (((Pfm)context.getSessionValue("copiedPfm")).getSelection().getIsDigital() == selection.getIsDigital())
          form.addElement(new FormHidden("copyPaste", "paste", true));  
      form.addElement(new FormHidden("cmd", "pfm-editor", true));
      if (pfm != null) {
        context.putSessionValue("Pfm", pfm);
        form.addElement(new FormHidden("copyPaste", "copy", true));
      } 
      context.putDelivery("Form", form);
      if (context.getSessionValue("originalCommentRelId") != null) {
        try {
          int orgCmtRelId = ((Integer)context.getSessionValue("originalCommentRelId")).intValue();
          if (orgCmtRelId != pfm.getReleaseId()) {
            context.removeSessionValue("originalComment");
            context.putSessionValue("originalCommentRelId", new Integer(pfm.getReleaseId()));
          } 
        } catch (Exception exception) {}
      } else if (pfm == null) {
        context.putSessionValue("originalCommentRelId", new Integer("-1"));
      } else {
        context.putSessionValue("originalCommentRelId", new Integer(pfm.getReleaseId()));
      } 
      if (selection.getIsDigital())
        return context.includeJSP("pfm-editor-digital.jsp"); 
      return context.includeJSP("pfm-editor.jsp");
    } 
    context.removeSessionValue("originalComment");
    context.putSessionValue("originalCommentRelId", new Integer("-1"));
    Pfm pfm = new Pfm();
    pfm = null;
    Form form = buildForm(context, pfm, command);
    form.addElement(new FormHidden("cmd", "pfm-editor", true));
    if (pfm != null)
      context.putSessionValue("Pfm", pfm); 
    context.putDelivery("Form", form);
    form.addElement(new FormHidden("OrderBy", "", true));
    if (context.getSessionValue("NOTEPAD_PFM_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_PFM_VISIBLE")); 
    return context.includeJSP("blank-pfm-editor.jsp");
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
      form = addSelectionSearchElements(context, null, form);
      form.setValues(context);
      SelectionManager.getInstance().setSelectionNotepadQuery(context, 
          MilestoneSecurity.getUser(context).getUserId(), notepad, form);
    } 
    dispatcher.redispatch(context, "pfm-editor");
    return true;
  }
  
  private boolean sort(Dispatcher dispatcher, Context context, String command) {
    int userId = MilestoneSecurity.getUser(context).getUserId();
    Notepad notepad = SelectionManager.getInstance().getSelectionNotepad(context, userId, 0);
    NotepadSortOrder.getNotepadSortOrderFromSession(context).sortHelper(dispatcher, context, notepad);
    dispatcher.redispatch(context, "pfm-editor");
    return true;
  }
  
  public Form buildForm(Context context, Pfm pfm, String command) {
    Form pfmForm = new Form(this.application, "pfmForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    Selection selection = null;
    if (command.equalsIgnoreCase("selectionSave")) {
      selection = (Selection)context.getSessionValue("pfmBomSelection");
    } else {
      selection = (Selection)context.getSessionValue("Selection");
    } 
    User user = MilestoneSecurity.getUser(context);
    int userId = user.getUserId();
    int secureLevel = getSelectionPfmPermissions(selection, user);
    setButtonVisibilities(selection, user, context, secureLevel, command);
    String selectionID = "-1";
    if (selection != null)
      selectionID = String.valueOf(selection.getSelectionID()); 
    pfmForm.addElement(new FormHidden("selectionID", selectionID, true));
    long timeStamp = -1L;
    if (pfm != null)
      timeStamp = pfm.getLastUpdatedCk(); 
    pfmForm.addElement(new FormHidden("pfmLastUpdateCheck", Long.toString(timeStamp), true));
    String gridnum = "";
    if (selection != null)
      gridnum = selection.getGridNumber(); 
    FormTextField ftf_gridnum = new FormTextField("gridnum", gridnum, false, 20, 50);
    ftf_gridnum.setEnabled(false);
    pfmForm.addElement(ftf_gridnum);
    if (selection != null)
      if (pfm != null) {
        pfmForm.addElement(new FormHidden("OrderBy", "", true));
        String printOption = "Draft";
        if (pfm.getPrintOption().length() > 0 && selection.getSelectionNo() != null && 
          selection.getIsDigital())
          printOption = pfm.getPrintOption(); 
        if (pfm.getPrintOption().length() > 0 && selection.getSelectionNo() != null && 
          !selection.getIsDigital() && 
          selection.getSelectionNo().toUpperCase().indexOf("TEMP") == -1)
          printOption = pfm.getPrintOption(); 
        String pOptions = "Draft,Final";
        if (printOption.equalsIgnoreCase("Final"))
          pOptions = printOption; 
        MilestoneInfrastructure inf = (MilestoneInfrastructure)context.getInfrastructure();
        FormRadioButtonGroup PrintOption = new FormRadioButtonGroup("printOption", printOption, pOptions, false);
        PrintOption.addFormEvent("onClick", "verifyFinalValues(this);hidePrintButtons('printWindow','');toggleSave('" + inf.getImageDirectory() + "');");
        PrintOption.setId("Draft/Final");
        PrintOption.setTabIndex(0);
        pfmForm.addElement(PrintOption);
        FormRadioButtonGroup sendOption = new FormRadioButtonGroup("sendOption", "Send Email", "Send Email,Do Not Send Email", false);
        sendOption.addFormEvent("onClick", "toggleSaveSend('" + inf.getImageDirectory() + "');");
        sendOption.setTabIndex(0);
        pfmForm.addElement(sendOption);
        String preparedByText = "";
        if (pfm.getPreparedBy() != null && pfm.getPreparedBy().length() > 0)
          preparedByText = pfm.getPreparedBy(); 
        FormTextField prepared_by = new FormTextField("prepared_by", preparedByText, false, 30, 50);
        prepared_by.setTabIndex(1);
        pfmForm.addElement(prepared_by);
        String phoneText = "";
        if (pfm.getPhone() != null && pfm.getPhone().length() > 0)
          phoneText = pfm.getPhone(); 
        FormTextField phone = new FormTextField("phone", phoneText, false, 30, 50);
        phone.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        phone.setTabIndex(2);
        pfmForm.addElement(phone);
        String emailText = "";
        if (pfm.getEmail() != null && pfm.getEmail().length() > 0)
          emailText = pfm.getEmail(); 
        FormTextField email = new FormTextField("email", emailText, false, 30, 50);
        email.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        email.setTabIndex(3);
        pfmForm.addElement(email);
        String faxText = "";
        if (pfm.getFaxNumber() != null && pfm.getFaxNumber().length() > 0)
          faxText = pfm.getFaxNumber(); 
        FormTextField fax = new FormTextField("fax", faxText, false, 30, 50);
        fax.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        fax.setTabIndex(4);
        pfmForm.addElement(fax);
        String commentsText = "";
        if (pfm.getComments().length() > 0)
          commentsText = pfm.getComments(); 
        FormTextArea comments = new FormTextArea("comments", commentsText, false, 2, 100, "virtual");
        comments.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        comments.addFormEvent("onBlur", "Javascript:checkField(this)");
        comments.setTabIndex(4);
        pfmForm.addElement(comments);
        String mode = "";
        if (pfm.getMode().length() > 0) {
          mode = pfm.getMode();
          if (!mode.equalsIgnoreCase("Add"))
            mode = "Change"; 
        } 
        String[] labels = new String[2];
        labels[0] = "Add";
        labels[1] = "Change";
        String[] values = new String[2];
        values[0] = "Add";
        values[1] = "Change";
        FormRadioButtonGroup Mode = new FormRadioButtonGroup("mode", mode, values, labels, false);
        Mode.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        Mode.setEnabled(false);
        Mode.setTabIndex(5);
        Mode.setId("Add/Change");
        pfmForm.addElement(Mode);
        String operatingCompanyText = "";
        if (selection.getOperCompany() != null && !selection.getOperCompany().equals("-1"))
          operatingCompanyText = selection.getOperCompany(); 
        FormTextField operating_company = new FormTextField("operating_company", operatingCompanyText, false, 50);
        operating_company.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        operating_company.setTabIndex(8);
        if (pfm.getOperatingCompany() == null) {
          operating_company.setStartingValue("");
        } else {
          operating_company.setStartingValue(pfm.getOperatingCompany());
        } 
        pfmForm.addElement(operating_company);
        String productNumberText = "";
        productNumberText = String.valueOf(SelectionManager.getLookupObjectValue(selection.getPrefixID())) + selection.getSelectionNo();
        FormTextField product_number = new FormTextField("product_number", productNumberText, false, 20, 50);
        product_number.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        product_number.setTabIndex(7);
        product_number.setReadOnly(true);
        if (pfm.getProductNumber() == null) {
          product_number.setStartingValue("");
        } else {
          product_number.setStartingValue(pfm.getProductNumber());
        } 
        pfmForm.addElement(product_number);
        FormDropDownMenu prefix = MilestoneHelper.getPrefixCodeDropDown(userId, "prefix", Cache.getPrefixCodes(), SelectionManager.getLookupObjectValue(pfm.getPrefixID()), false, context);
        prefix.setTabIndex(9);
        prefix.setClassName("ctrlShort");
        prefix.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        pfmForm.addElement(prefix);
        FormTextField selectionNo = new FormTextField("selectionNo", String.valueOf(pfm.getSelectionNo()), false, 14, 20);
        selectionNo.setTabIndex(10);
        selectionNo.setDisplayName("Physical Product Number");
        selectionNo.setClassName("ctrlSmall");
        selectionNo.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        pfmForm.addElement(selectionNo);
        String musicLineText = "";
        if (pfm.getMusicLine() != null && pfm.getMusicLine().length() > 0)
          musicLineText = pfm.getMusicLine(); 
        FormDropDownMenu music_line = MilestoneHelper.getPfmLookupDropDown("music_line", Cache.getMusicLines(), String.valueOf(musicLineText), false, true);
        music_line.addFormEvent("onKeyPress", "scanKey(window.event.keyCode, 'document.all.music_line.options', getMaxLength(document.all.music_line.options))");
        music_line.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        music_line.setTabIndex(21);
        pfmForm.addElement(music_line);
        String selConfigCode = "";
        int selDigital = 0;
        if (selection != null) {
          if (selection.getIsDigital())
            selDigital = 1; 
          selConfigCode = selection.getConfigCode();
        } 
        FormDropDownMenu config_code = MilestoneHelper.getPfmLookupDropDown("config_code", MilestoneHelper.getConfigCodes(selDigital), selection.getConfigCode(), false, true);
        if (pfm.getConfigCode() == null) {
          config_code.setStartingValue("");
        } else {
          config_code.setStartingValue(pfm.getConfigCode());
        } 
        config_code.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        config_code.setTabIndex(11);
        pfmForm.addElement(config_code);
        String repOwnerText = "";
        if (pfm.getRepertoireOwner() != null && pfm.getRepertoireOwner().length() > 0)
          repOwnerText = pfm.getRepertoireOwner(); 
        FormDropDownMenu repertoire_owner = MilestoneHelper.getPfmLookupDropDown("repertoire_owner", Cache.getRepertoireOwners(), String.valueOf(repOwnerText), false, true);
        repertoire_owner.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        repertoire_owner.setTabIndex(22);
        pfmForm.addElement(repertoire_owner);
        String modifierText = "";
        if (pfm.getModifier() != null && pfm.getModifier().length() > 0)
          modifierText = pfm.getModifier(); 
        FormDropDownMenu modifier = MilestoneHelper.getPfmLookupDropDown("modifier", Cache.getModifiers(), String.valueOf(modifierText), false, true);
        modifier.addFormEvent("onKeyPress", "scanKey(window.event.keyCode, 'document.all.modifier.options', getMaxLength(document.all.modifier.options))");
        modifier.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        modifier.setTabIndex(12);
        pfmForm.addElement(modifier);
        String repClass = "";
        if (pfm.getRepertoireClass() != null && pfm.getRepertoireClass().length() > 0) {
          LookupObject lookupObject = MilestoneHelper.getLookupObject(pfm.getRepertoireClass(), Cache.getRepertoireClasses());
          if (lookupObject != null)
            repClass = String.valueOf(lookupObject.getAbbreviation()) + ":" + lookupObject.getName(); 
        } 
        FormTextField RepClass = new FormTextField("repClass", repClass, false, 30, 50);
        RepClass.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        RepClass.setId("Rep. Class");
        pfmForm.addElement(RepClass);
        String upc = "";
        if (selection.getUpc() != null)
          upc = selection.getUpc(); 
        FormTextField Upc = new FormTextField("upc", upc, false, 20);
        Upc.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        Upc.setTabIndex(20);
        if (pfm.getUpc() == null) {
          Upc.setStartingValue("");
        } else {
          Upc.setStartingValue(pfm.getUpc());
        } 
        if (selection.getIsDigital())
          Upc.addFormEvent("onKeyPress", "return checkNumbersOnly(window.event.keyCode);"); 
        pfmForm.addElement(Upc);
        String isPromo = "";
        if (selection.getReleaseType() != null)
          isPromo = selection.getReleaseType().getAbbreviation(); 
        FormTextField isPromoEl = new FormTextField("isPromo", isPromo, false, 20);
        pfmForm.addElement(isPromoEl);
        String imprintText = "";
        if (selection.getImprint() != null)
          imprintText = selection.getImprint(); 
        FormTextField imprint = new FormTextField("imprint", imprintText, false, 50);
        if (pfm.getSelection().getImprint() == null) {
          imprint.setStartingValue("");
        } else {
          imprint.setStartingValue(pfm.getSelection().getImprint());
        } 
        pfmForm.addElement(imprint);
        String titleText = "";
        if (selection.getTitle() != null)
          titleText = selection.getTitle(); 
        FormTextField title = new FormTextField("title", titleText, false, 50);
        title.setTabIndex(13);
        title.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        if (pfm.getTitle() == null) {
          title.setStartingValue("");
        } else {
          title.setStartingValue(pfm.getTitle());
        } 
        pfmForm.addElement(title);
        String artistText = "";
        if (selection.getArtist() != null) {
          artistText = selection.getArtistLastName();
          if (!selection.getArtistLastName().equals("") && !selection.getArtistFirstName().equals(""))
            artistText = String.valueOf(artistText) + ", "; 
          artistText = String.valueOf(artistText) + selection.getArtistFirstName();
        } 
        FormTextField artistField = new FormTextField("artist", artistText, false, 50);
        artistField.setTabIndex(14);
        if (pfm.getArtist() == null) {
          artistField.setStartingValue("");
        } else {
          artistField.setStartingValue(pfm.getArtist());
        } 
        pfmForm.addElement(artistField);
        String titleID = "";
        if (selection.getTitleID() != null)
          titleID = selection.getTitleID(); 
        FormTextField TitleID = new FormTextField("titleID", titleID, false, 50);
        TitleID.setTabIndex(15);
        TitleID.setId("Title ID");
        TitleID.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        if (pfm.getTitleId() == null) {
          TitleID.setStartingValue("");
        } else {
          TitleID.setStartingValue(pfm.getTitleId());
        } 
        pfmForm.addElement(TitleID);
        String soundScanGroup = "";
        if (selection.getSoundScanGrp() != null)
          soundScanGroup = selection.getSoundScanGrp(); 
        FormTextField soundScanGroupField = new FormTextField("sound_scan_code", soundScanGroup, false, 20);
        if (selection.getIsDigital()) {
          soundScanGroupField.setId("Sales Grouping Code");
          soundScanGroupField.setTabIndex(21);
        } else {
          soundScanGroupField.setId("SoundScan Group Code");
          soundScanGroupField.setTabIndex(17);
        } 
        soundScanGroupField.setClassName("ctrlMedium");
        soundScanGroupField.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        if (pfm.getSoundScanGrp() == null) {
          soundScanGroupField.setStartingValue("");
        } else {
          soundScanGroupField.setStartingValue(pfm.getSoundScanGrp());
        } 
        selection.getIsDigital();
        pfmForm.addElement(soundScanGroupField);
        String returnCodeText = "";
        if (pfm.getReturnCode() != null && !pfm.getReturnCode().equals("[none]"))
          returnCodeText = pfm.getReturnCode(); 
        FormDropDownMenu return_code = MilestoneHelper.getPfmLookupDropDown("return_code", Cache.getReturnCodes(), String.valueOf(returnCodeText), false, true);
        return_code.setDisplayName("Return/Scrap Code");
        return_code.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        return_code.setTabIndex(23);
        pfmForm.addElement(return_code);
        String releaseDate = "";
        if (!selection.getIsDigital() && selection.getStreetDate() != null) {
          releaseDate = MilestoneHelper.getFormatedDate(selection.getStreetDate());
        } else if (selection.getIsDigital() && selection.getDigitalRlsDate() != null) {
          releaseDate = MilestoneHelper.getFormatedDate(selection.getDigitalRlsDate());
        } 
        FormTextField ReleaseDate = new FormTextField("releaseDate", releaseDate, false, 15);
        ReleaseDate.setTabIndex(17);
        ReleaseDate.setId("Release Date");
        ReleaseDate.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        String pfmReleaseDate = "";
        if (pfm.getStreetDate() != null)
          pfmReleaseDate = MilestoneHelper.getFormatedDate(pfm.getStreetDate()); 
        ReleaseDate.setStartingValue(pfmReleaseDate);
        pfmForm.addElement(ReleaseDate);
        FormHidden status = new FormHidden("status", SelectionManager.getLookupObjectValue(selection.getSelectionStatus()));
        if (pfm.getStatus() != null) {
          status.setStartingValue(pfm.getStatus());
        } else {
          status.setStartingValue("");
        } 
        pfmForm.addElement(status);
        String exportFlagText = "";
        if (pfm.getExportFlag() != null)
          exportFlagText = pfm.getExportFlag(); 
        FormDropDownMenu export_flag = MilestoneHelper.getPfmLookupDropDown("export_flag", Cache.getExportFlags(), String.valueOf(exportFlagText), false, true);
        export_flag.setTabIndex(24);
        export_flag.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        pfmForm.addElement(export_flag);
        String superLabelText = "";
        if (selection.getSuperLabel() != null)
          superLabelText = selection.getSuperLabel(); 
        FormTextField super_label = new FormTextField("super_label", superLabelText, false, 50);
        super_label.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        super_label.setTabIndex(19);
        if (pfm.getSuperLabel() == null) {
          super_label.setStartingValue("");
        } else {
          super_label.setStartingValue(pfm.getSuperLabel());
        } 
        pfmForm.addElement(super_label);
        String countryText = "";
        if (pfm.getCountries() != null)
          countryText = pfm.getCountries(); 
        FormTextField countries = new FormTextField("countries", countryText, false, 20, 50);
        countries.setTabIndex(25);
        countries.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        pfmForm.addElement(countries);
        String labelCodeText = "";
        if (selection.getSubLabel() != null)
          labelCodeText = selection.getSubLabel(); 
        FormTextField label_code = new FormTextField("label_code", labelCodeText, false, 20);
        label_code.setTabIndex(21);
        label_code.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        if (pfm.getLabelCode() == null) {
          label_code.setStartingValue("");
        } else {
          label_code.setStartingValue(pfm.getLabelCode());
        } 
        pfmForm.addElement(label_code);
        String spineTitleText = "";
        if (pfm.getSpineTitle() != null)
          spineTitleText = pfm.getSpineTitle(); 
        if (spineTitleText.length() > 37)
          spineTitleText = spineTitleText.substring(0, 37); 
        FormTextField spine_title = new FormTextField("spine_title", spineTitleText, false, 37, 37);
        spine_title.setTabIndex(26);
        spine_title.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        pfmForm.addElement(spine_title);
        String companyCodeText = "";
        if (pfm.getCompanyCode() != null)
          companyCodeText = pfm.getCompanyCode(); 
        FormDropDownMenu company_code = MilestoneHelper.getPfmLookupDropDown("company_code", Cache.getCompanyCodes(), String.valueOf(companyCodeText), false, true);
        company_code.setTabIndex(13);
        company_code.addFormEvent("onKeyPress", "scanKey(window.event.keyCode, 'document.all.company_code.options', getMaxLength(document.all.company_code.options))");
        company_code.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        pfmForm.addElement(company_code);
        String spineArtistText = "";
        if (pfm.getSpineArtist() != null)
          spineArtistText = pfm.getSpineArtist(); 
        if (spineArtistText.length() > 37)
          spineArtistText = spineArtistText.substring(0, 37); 
        FormTextField spine_artist = new FormTextField("spine_artist", spineArtistText, false, 37, 37);
        spine_artist.setTabIndex(27);
        spine_artist.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        pfmForm.addElement(spine_artist);
        String projectID = "";
        if (selection.getProjectID() != null)
          projectID = selection.getProjectID(); 
        FormTextField ProjectID = new FormTextField("projectID", projectID, false, 50);
        ProjectID.setTabIndex(25);
        ProjectID.setId("Proj ID");
        ProjectID.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        if (pfm.getProjectID() == null) {
          ProjectID.setStartingValue("");
        } else {
          ProjectID.setStartingValue(pfm.getProjectID());
        } 
        pfmForm.addElement(ProjectID);
        String priceCodeText = "";
        if (selection.getPriceCode() != null)
          priceCodeText = selection.getPriceCode().getSellCode(); 
        FormTextField price_code = new FormTextField("price_code", priceCodeText, false, 20, 50);
        price_code.setTabIndex(25);
        price_code.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        String pfmPriceCodeText = "";
        if (pfm.getPriceCode() != null)
          pfmPriceCodeText = pfm.getPriceCode(); 
        price_code.setStartingValue(pfmPriceCodeText);
        if (selection.getIsDigital())
          price_code.setDisplayName("Dig. Price Code"); 
        pfmForm.addElement(price_code);
        if (!selection.getIsDigital()) {
          String priceCodeDPCText = "";
          if (selection.getPriceCodeDPC() != null)
            priceCodeDPCText = selection.getPriceCodeDPC().getSellCode(); 
          FormTextField price_codeDPC = new FormTextField("price_codeDPC", priceCodeDPCText, false, 20, 50);
          price_codeDPC.setTabIndex(25);
          price_codeDPC.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
          String pfmPriceCodeDPCText = "";
          if (pfm.getPriceCodeDPC() != null)
            pfmPriceCodeDPCText = pfm.getPriceCodeDPC(); 
          price_codeDPC.setStartingValue(pfmPriceCodeDPCText);
          price_codeDPC.setDisplayName("Dig. Price Code");
          pfmForm.addElement(price_codeDPC);
        } 
        String poMergeCodeText = "";
        if (pfm.getPoMergeCode() != null)
          poMergeCodeText = pfm.getPoMergeCode(); 
        FormDropDownMenu po_merge_code = MilestoneHelper.getPfmLookupDropDown("po_merge_code", Cache.getPoMergeCodes(), String.valueOf(poMergeCodeText), false, true);
        po_merge_code.setDisplayName("PO Merge Code");
        po_merge_code.setTabIndex(14);
        po_merge_code.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        pfmForm.addElement(po_merge_code);
        String guaranteeCodeText = "";
        if (pfm.getGuaranteeCode() != null)
          guaranteeCodeText = pfm.getGuaranteeCode(); 
        FormDropDownMenu guarantee_code = MilestoneHelper.getPfmLookupDropDown("guarantee_code", Cache.getGuaranteeCodes(), String.valueOf(guaranteeCodeText), false, true);
        guarantee_code.setDisplayName("IMI Exempt Code");
        guarantee_code.setTabIndex(29);
        guarantee_code.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        pfmForm.addElement(guarantee_code);
        String loosePickExemptCodeText = "";
        if (pfm.getLoosePickExemptCode() != null)
          loosePickExemptCodeText = pfm.getLoosePickExemptCode(); 
        FormDropDownMenu loose_pick_exempt = MilestoneHelper.getPfmLookupDropDown("loose_pick_exempt", Cache.getLoosePickExempt(), String.valueOf(loosePickExemptCodeText), false, true);
        loose_pick_exempt.setDisplayName("Loose Pick Exempt");
        loose_pick_exempt.setTabIndex(28);
        loose_pick_exempt.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        pfmForm.addElement(loose_pick_exempt);
        String compilationCodeText = "";
        if (pfm.getCompilationCode() != null)
          compilationCodeText = pfm.getCompilationCode(); 
        FormDropDownMenu compilation_code = MilestoneHelper.getPfmLookupDropDown("compilation_code", Cache.getCompilationCodes(), String.valueOf(compilationCodeText), false, true);
        compilation_code.setTabIndex(34);
        compilation_code.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        pfmForm.addElement(compilation_code);
        FormCheckBox valueAdded = new FormCheckBox("ValueAdded", "on", "", false, pfm.getValueAdded());
        valueAdded.setTabIndex(36);
        valueAdded.setId("Value Added");
        pfmForm.addElement(valueAdded);
        FormCheckBox parentalAdv = new FormCheckBox("ParentalAdv", "on", "", false, selection.getParentalGuidance());
        parentalAdv.setTabIndex(37);
        parentalAdv.setEnabled(false);
        parentalAdv.setReadOnly(true);
        parentalAdv.setChecked(selection.getParentalGuidance());
        parentalAdv.setId("Parental Adv");
        parentalAdv.setClassName("ctrlMediumGrayBG");
        parentalAdv.setStartingChecked(pfm.getParentalGuidance());
        pfmForm.addElement(parentalAdv);
        FormCheckBox boxSet = new FormCheckBox("BoxSet", "on", "", false, pfm.getBoxSet());
        boxSet.setTabIndex(35);
        boxSet.setId("Box Set");
        pfmForm.addElement(boxSet);
        String unitsPerSetText = "";
        if (pfm.getUnitsPerSet() > 0)
          try {
            unitsPerSetText = Integer.toString(pfm.getUnitsPerSet());
          } catch (NumberFormatException e) {
            System.out.println("Error converting Units Per Set into integer.");
          }  
        FormTextField units_per_set = new FormTextField("units_per_set", unitsPerSetText, false, 20, 20);
        units_per_set.setDisplayName("# of Units");
        units_per_set.setTabIndex(15);
        units_per_set.addFormEvent("onKeyPress", "return checkNumbersOnly(window.event.keyCode);");
        units_per_set.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        units_per_set.setStartingValue(unitsPerSetText);
        pfmForm.addElement(units_per_set);
        String impRateCodeText = "";
        if (pfm.getImpRateCode() != null)
          impRateCodeText = pfm.getImpRateCode(); 
        FormDropDownMenu imp_rate_code = MilestoneHelper.getPfmLookupDropDown("imp_rate_code", Cache.getImpRateCodes(), String.valueOf(impRateCodeText), false, true);
        imp_rate_code.setTabIndex(31);
        imp_rate_code.addFormEvent("onKeyPress", "scanKey(window.event.keyCode, 'document.all.imp_rate_code.options', getMaxLength(document.all.imp_rate_code.options))");
        imp_rate_code.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        pfmForm.addElement(imp_rate_code);
        String setsPerCartonText = "0";
        if (pfm.getSetsPerCarton() > 0)
          try {
            setsPerCartonText = Integer.toString(pfm.getSetsPerCarton());
          } catch (NumberFormatException e) {
            System.out.println("Error converting Sets Per Carton into integer.");
          }  
        FormTextField sets_per_carton = new FormTextField("sets_per_carton", setsPerCartonText, false, 20, 20);
        sets_per_carton.setDisplayName("Sets Per Carton");
        sets_per_carton.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        sets_per_carton.setTabIndex(16);
        sets_per_carton.addFormEvent("onKeyPress", "return checkNumbersOnly(window.event.keyCode);");
        pfmForm.addElement(sets_per_carton);
        FormDropDownMenu music_type = MilestoneHelper.getPfmLookupDropDown("music_type", Cache.getMusicTypes(), SelectionManager.getLookupObjectValue(selection.getGenre()), false, true);
        music_type.setTabIndex(30);
        music_type.addFormEvent("onKeyPress", "scanKey(window.event.keyCode, 'document.all.music_type.options', getMaxLength(document.all.music_type.options))");
        music_type.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        if (pfm.getMusicType() == null) {
          music_type.setStartingValue("");
        } else {
          music_type.setStartingValue(SelectionManager.getLookupObjectValue(pfm.getMusicType()));
        } 
        pfmForm.addElement(music_type);
        String supplierText = "";
        if (pfm.getSupplier() != null)
          supplierText = pfm.getSupplier(); 
        FormDropDownMenu supplier = MilestoneHelper.getPfmLookupDropDown("suppliers", Cache.getSuppliers(), String.valueOf(supplierText), false, true);
        supplier.setTabIndex(17);
        supplier.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        pfmForm.addElement(supplier);
        String narmText = "";
        if (pfm.getNarmFlag() != null)
          narmText = pfm.getNarmFlag(); 
        FormDropDownMenu narm = MilestoneHelper.getPfmLookupDropDown("NARM", Cache.getNarmExtracts(), String.valueOf(narmText), false, true);
        narm.setDisplayName("Narm Extract Ind.");
        narm.setTabIndex(33);
        narm.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        pfmForm.addElement(narm);
        String encryptionText = "";
        if (pfm.getEncryptionFlag() != null)
          encryptionText = pfm.getEncryptionFlag(); 
        FormDropDownMenu encryptionFlag = MilestoneHelper.getPfmLookupDropDown("encryptionFlag", Cache.getEncryptionFlags(), String.valueOf(encryptionText), false, true);
        encryptionFlag.setTabIndex(38);
        encryptionFlag.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        pfmForm.addElement(encryptionFlag);
        String importIndicatorText = "";
        String names = "";
        if (pfm.getImportIndicator() != null) {
          importIndicatorText = pfm.getImportIndicator();
          Vector importIndicators = Cache.getImportIndicators();
          String[] abbreviation = new String[importIndicators.size()];
          for (int i = importIndicators.size() - 1; i > -1; i--) {
            LookupObject lookupObject = (LookupObject)importIndicators.elementAt(i);
            names = String.valueOf(names) + lookupObject.getName();
            if (i > 0)
              names = String.valueOf(names) + ","; 
            abbreviation[i] = lookupObject.getAbbreviation();
            if (importIndicatorText.equalsIgnoreCase(abbreviation[i]))
              importIndicatorText = lookupObject.getName(); 
          } 
        } 
        FormRadioButtonGroup ImportIndicator = new FormRadioButtonGroup("ImportIndicator", importIndicatorText, names, false);
        ImportIndicator.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        ImportIndicator.setId("Imp. Ind");
        ImportIndicator.setTabIndex(18);
        pfmForm.addElement(ImportIndicator);
        String pricePointText = "";
        if (pfm.getPricePoint() != null)
          pricePointText = pfm.getPricePoint(); 
        if (selection.getIsDigital())
          pricePointText = "PS"; 
        FormDropDownMenu price_point = MilestoneHelper.getPfmLookupDropDown("price_point", Cache.getPricePoints(), String.valueOf(pricePointText), false, true);
        price_point.setTabIndex(32);
        price_point.addFormEvent("onKeyPress", "scanKey(window.event.keyCode, 'document.all.price_point.options', getMaxLength(document.all.price_point.options))");
        price_point.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        pfmForm.addElement(price_point);
        FormTextField lastUpdated = new FormTextField("lastUpdatedDate", false, 50);
        if (pfm.getLastUpdatedDate() != null)
          lastUpdated.setValue(MilestoneHelper.getCustomFormatedDate(pfm.getLastUpdatedDate(), "M/d/yyyy hh:mm:ss a 'ET'")); 
        lastUpdated.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        pfmForm.addElement(lastUpdated);
        FormTextField lastUpdatedBy = new FormTextField("lastUpdatedBy", false, 50);
        if (UserManager.getInstance().getUser(pfm.getLastUpdatingUser()) != null)
          lastUpdatedBy.setValue(UserManager.getInstance().getUser(pfm.getLastUpdatingUser()).getName()); 
        lastUpdatedBy.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        pfmForm.addElement(lastUpdatedBy);
        FormTextField lastLegacyUpdateDate = new FormTextField("lastLegacyUpdateDate", false, 50);
        lastLegacyUpdateDate.setValue(MilestoneHelper.getCustomFormatedDate(selection.getLastLegacyUpdateDate(), "M/d/yyyy hh:mm:ss a 'ET'"));
        pfmForm.addElement(lastLegacyUpdateDate);
        String archieDateText = "";
        if (selection.getArchieDate() != null)
          archieDateText = MilestoneHelper.getCustomFormatedDate(selection.getArchieDate(), "M/d/yyyy hh:mm:ss a 'ET'"); 
        FormTextField archieDate = new FormTextField("archieDate", archieDateText, false, 13);
        pfmForm.addElement(archieDate);
        String approvedByName = "";
        if (pfm.getApprovedByName() != null)
          approvedByName = pfm.getApprovedByName(); 
        FormTextField approvedBy = new FormTextField("approvedBy", false, 50);
        approvedBy.setValue(approvedByName);
        pfmForm.addElement(approvedBy);
        String enteredByName = "";
        if (pfm.getEnteredByName() != null)
          enteredByName = pfm.getEnteredByName(); 
        FormTextField enteredBy = new FormTextField("enteredBy", false, 50);
        enteredBy.setValue(enteredByName);
        pfmForm.addElement(enteredBy);
        String verifiedByName = "";
        if (pfm.getVerifiedByName() != null)
          verifiedByName = pfm.getVerifiedByName(); 
        FormTextField verifiedBy = new FormTextField("verifiedBy", false, 50);
        verifiedBy.setValue(verifiedByName);
        pfmForm.addElement(verifiedBy);
        String changeNumberText = "-1";
        if (pfm.getChangeNumber() != null && !pfm.getChangeNumber().equals(""))
          changeNumberText = pfm.getChangeNumber(); 
        FormTextField ChangeNumber = new FormTextField("ChangeNumber", changeNumberText, false, 2, 2);
        ChangeNumber.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        ChangeNumber.setTabIndex(6);
        ChangeNumber.setEnabled(false);
        ChangeNumber.setId("Change #");
        pfmForm.addElement(ChangeNumber);
      } else {
        pfmForm.addElement(new FormHidden("cmd", "pfm-edit-new", true));
        pfmForm.addElement(new FormHidden("OrderBy", "", true));
        pfmForm.addElement(new FormHidden("selectionID", selectionID, true));
        pfmForm.addElement(new FormHidden("pfmLastUpdateCheck", "-1", true));
        String printOption = "Draft";
        if (pfm != null && pfm.getPrintOption().length() > 0 && selection.getSelectionNo() != null && 
          selection.getSelectionNo().toUpperCase().indexOf("TEMP") == -1)
          printOption = pfm.getPrintOption(); 
        MilestoneInfrastructure inf = (MilestoneInfrastructure)context.getInfrastructure();
        FormRadioButtonGroup PrintOption = new FormRadioButtonGroup("printOption", printOption, "Draft,Final", false);
        PrintOption.addFormEvent("onClick", "verifyFinalValues(this);hidePrintButtons('printWindow','');toggleSave('" + inf.getImageDirectory() + "');");
        PrintOption.setId("Draft/Final");
        PrintOption.setTabIndex(0);
        pfmForm.addElement(PrintOption);
        FormTextField prepared_by = new FormTextField("prepared_by", user.getName(), false, 30, 50);
        prepared_by.setTabIndex(1);
        pfmForm.addElement(prepared_by);
        FormTextField phone = new FormTextField("phone", user.getPhone(), false, 30, 50);
        phone.setTabIndex(2);
        pfmForm.addElement(phone);
        FormTextField email = new FormTextField("email", user.getEmail(), false, 30, 50);
        email.setTabIndex(3);
        pfmForm.addElement(email);
        FormTextField fax = new FormTextField("fax", user.getFax(), false, 30, 50);
        fax.setTabIndex(4);
        pfmForm.addElement(fax);
        FormTextArea comments = new FormTextArea("comments", "", false, 2, 100, "virtual");
        comments.addFormEvent("onBlur", "Javascript:checkField(this)");
        comments.setTabIndex(4);
        pfmForm.addElement(comments);
        FormRadioButtonGroup Mode = new FormRadioButtonGroup("mode", "Add", "Add,Change", false);
        Mode.setTabIndex(5);
        Mode.setEnabled(false);
        Mode.setId("Add/Change");
        pfmForm.addElement(Mode);
        String operatingCompanyText = "";
        operatingCompanyText = selection.getOperCompany();
        if (operatingCompanyText.equalsIgnoreCase("-1"))
          operatingCompanyText = ""; 
        FormTextField operating_company = new FormTextField("operating_company", operatingCompanyText, false, 50);
        operating_company.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        operating_company.setTabIndex(8);
        pfmForm.addElement(operating_company);
        String productNumber = String.valueOf(SelectionManager.getLookupObjectValue(selection.getPrefixID())) + selection.getSelectionNo();
        FormTextField product_number = new FormTextField("product_number", productNumber, false, 20, 50);
        product_number.setTabIndex(7);
        product_number.setReadOnly(true);
        pfmForm.addElement(product_number);
        FormDropDownMenu prefix = MilestoneHelper.getPrefixCodeDropDown(userId, "prefix", Cache.getPrefixCodes(), SelectionManager.getLookupObjectValue(selection.getPrefixID()), false, context);
        prefix.setTabIndex(9);
        prefix.setClassName("ctrlShort");
        prefix.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        pfmForm.addElement(prefix);
        FormTextField selectionNo = new FormTextField("selectionNo", String.valueOf(selection.getSelectionNo()), false, 14, 20);
        selectionNo.setTabIndex(10);
        selectionNo.setDisplayName("Physical Product Number");
        selectionNo.setClassName("ctrlSmall");
        selectionNo.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        pfmForm.addElement(selectionNo);
        String musicLineText = "";
        if (selection.getIsDigital() && (
          MilestoneHelper.getStructureName(selection.getReleaseFamilyId()).equals("Canada") || 
          MilestoneHelper.getStructureName(selection.getReleaseFamilyId()).equals("Mexico")))
          musicLineText = "1010"; 
        FormDropDownMenu music_line = MilestoneHelper.getPfmLookupDropDown("music_line", Cache.getMusicLines(), musicLineText, false, true);
        music_line.addFormEvent("onKeyPress", "scanKey(window.event.keyCode, 'document.all.music_line.options', getMaxLength(document.all.music_line.options))");
        music_line.setTabIndex(21);
        pfmForm.addElement(music_line);
        String selConfigCode = "";
        int selDigital = 0;
        if (selection != null) {
          if (selection.getIsDigital())
            selDigital = 1; 
          selConfigCode = selection.getConfigCode();
        } 
        FormDropDownMenu config_code = MilestoneHelper.getPfmLookupDropDown("config_code", MilestoneHelper.getConfigCodes(selDigital), selConfigCode, false, true);
        config_code.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        config_code.setTabIndex(11);
        pfmForm.addElement(config_code);
        String repOwnerText = "";
        if (selection.getIsDigital() && (
          MilestoneHelper.getStructureName(selection.getReleaseFamilyId()).equals("Canada") || 
          MilestoneHelper.getStructureName(selection.getReleaseFamilyId()).equals("Mexico")))
          repOwnerText = "OT"; 
        FormDropDownMenu repertoire_owner = MilestoneHelper.getPfmLookupDropDown("repertoire_owner", Cache.getRepertoireOwners(), repOwnerText, false, true);
        repertoire_owner.setTabIndex(22);
        pfmForm.addElement(repertoire_owner);
        FormDropDownMenu modifier = MilestoneHelper.getPfmLookupDropDown("modifier", Cache.getModifiers(), "", false, true);
        modifier.addFormEvent("onKeyPress", "scanKey(window.event.keyCode, 'document.all.modifier.options', getMaxLength(document.all.modifier.options))");
        modifier.setTabIndex(12);
        pfmForm.addElement(modifier);
        String soundScanGroup = "";
        if (selection.getSoundScanGrp() != null)
          soundScanGroup = selection.getSoundScanGrp(); 
        FormTextField soundScanGroupField = new FormTextField("sound_scan_code", soundScanGroup, false, 20);
        if (selection.getIsDigital()) {
          soundScanGroupField.setId("Sales Grouping Code");
          soundScanGroupField.setTabIndex(21);
        } else {
          soundScanGroupField.setId("SoundScan Group Code");
          soundScanGroupField.setTabIndex(17);
        } 
        soundScanGroupField.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        soundScanGroupField.setClassName("ctrlMedium");
        selection.getIsDigital();
        pfmForm.addElement(soundScanGroupField);
        FormTextField RepClass = new FormTextField("repClass", "", false, 30, 50);
        pfmForm.addElement(RepClass);
        String upc = "";
        if (selection.getUpc() != null)
          upc = selection.getUpc(); 
        FormTextField Upc = new FormTextField("upc", upc, false, 20);
        Upc.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        Upc.setTabIndex(20);
        if (selection.getIsDigital())
          Upc.addFormEvent("onKeyPress", "return checkNumbersOnly(window.event.keyCode);"); 
        pfmForm.addElement(Upc);
        String isPromo = "";
        if (selection.getReleaseType() != null)
          isPromo = selection.getReleaseType().getAbbreviation(); 
        FormTextField isPromoEl = new FormTextField("isPromo", isPromo, false, 20);
        pfmForm.addElement(isPromoEl);
        String imprintText = "";
        if (selection.getImprint() != null)
          imprintText = selection.getImprint(); 
        FormTextField imprint = new FormTextField("imprint", imprintText, false, 50);
        pfmForm.addElement(imprint);
        String titleText = "";
        if (selection.getTitle() != null)
          titleText = selection.getTitle(); 
        FormTextField title = new FormTextField("title", titleText, false, 50);
        title.setTabIndex(13);
        title.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        pfmForm.addElement(title);
        String artist = "";
        if (selection.getArtist() != null) {
          artist = selection.getArtistLastName();
          if (!selection.getArtistLastName().equals("") && !selection.getArtistFirstName().equals(""))
            artist = String.valueOf(artist) + ", "; 
          artist = String.valueOf(artist) + selection.getArtistFirstName();
        } 
        FormTextField artistField = new FormTextField("artist", artist, false, 50);
        artistField.setTabIndex(14);
        pfmForm.addElement(artistField);
        String titleID = "";
        if (selection.getTitleID() != null)
          titleID = selection.getTitleID(); 
        FormTextField TitleID = new FormTextField("titleID", titleID, false, 50);
        TitleID.setTabIndex(15);
        pfmForm.addElement(TitleID);
        String defaultCode = "";
        if (selection.getIsDigital() || selection.getReleaseType().getAbbreviation().equalsIgnoreCase("PR"))
          defaultCode = "T"; 
        FormDropDownMenu return_code = MilestoneHelper.getPfmLookupDropDown("return_code", Cache.getReturnCodes(), defaultCode, false, true);
        return_code.setDisplayName("Return/Scrap Code");
        return_code.setTabIndex(23);
        pfmForm.addElement(return_code);
        String releaseDate = "";
        if (!selection.getIsDigital() && selection.getStreetDate() != null) {
          releaseDate = MilestoneHelper.getFormatedDate(selection.getStreetDate());
        } else if (selection.getIsDigital() && selection.getDigitalRlsDate() != null) {
          releaseDate = MilestoneHelper.getFormatedDate(selection.getDigitalRlsDate());
        } 
        FormTextField ReleaseDate = new FormTextField("releaseDate", releaseDate, false, 15);
        ReleaseDate.setId("Release Date");
        ReleaseDate.setTabIndex(17);
        pfmForm.addElement(ReleaseDate);
        FormHidden status = new FormHidden("status", SelectionManager.getLookupObjectValue(selection.getSelectionStatus()));
        pfmForm.addElement(status);
        FormDropDownMenu export_flag = MilestoneHelper.getPfmLookupDropDown("export_flag", Cache.getExportFlags(), "", false, true);
        export_flag.setTabIndex(24);
        pfmForm.addElement(export_flag);
        String superLabelText = "";
        if (selection.getSuperLabel() != null)
          superLabelText = selection.getSuperLabel(); 
        FormTextField super_label = new FormTextField("super_label", superLabelText, false, 50);
        super_label.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        super_label.setTabIndex(19);
        pfmForm.addElement(super_label);
        FormTextField countries = new FormTextField("countries", "", false, 20, 50);
        countries.setTabIndex(25);
        pfmForm.addElement(countries);
        String labelCodeText = "";
        if (selection.getSubLabel() != null)
          labelCodeText = selection.getSubLabel(); 
        FormTextField label_code = new FormTextField("label_code", labelCodeText, false, 20);
        label_code.setTabIndex(21);
        label_code.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        pfmForm.addElement(label_code);
        FormTextField spine_title = new FormTextField("spine_title", "", false, 37, 37);
        spine_title.setTabIndex(26);
        String spineTitleText = "";
        if (selection != null)
          if (selection.getTitle() != null)
            spineTitleText = (selection.getTitle().length() > 37) ? selection.getTitle().substring(0, 37) : selection.getTitle();  
        spine_title.setValue(spineTitleText);
        spine_title.setStartingValue(spineTitleText);
        pfmForm.addElement(spine_title);
        String companyCodeText = "";
        if (selection.getIsDigital() && (
          MilestoneHelper.getStructureName(selection.getReleaseFamilyId()).equals("Canada") || 
          MilestoneHelper.getStructureName(selection.getReleaseFamilyId()).equals("Mexico")))
          companyCodeText = "1010"; 
        FormDropDownMenu company_code = MilestoneHelper.getPfmLookupDropDown("company_code", Cache.getCompanyCodes(), companyCodeText, false, true);
        company_code.addFormEvent("onKeyPress", "scanKey(window.event.keyCode, 'document.all.company_code.options', getMaxLength(document.all.company_code.options))");
        company_code.setTabIndex(13);
        pfmForm.addElement(company_code);
        FormTextField spine_artist = new FormTextField("spine_artist", "", false, 37, 37);
        spine_artist.setTabIndex(27);
        String spineArtistText = "";
        if (selection != null) {
          if (selection.getArtist() != null)
            if (selection.getArtistFirstName().length() > 0)
              spineArtistText = String.valueOf(selection.getArtistFirstName()) + " ";  
          if (selection.getArtistLastName().length() > 0)
            spineArtistText = String.valueOf(spineArtistText) + selection.getArtistLastName(); 
          if (spineArtistText.length() > 37)
            spineArtistText = spineArtistText.trim().substring(0, 37); 
        } 
        spine_artist.setValue(spineArtistText.trim());
        spine_artist.setStartingValue(spineArtistText.trim());
        pfmForm.addElement(spine_artist);
        String projectID = "";
        if (selection.getProjectID() != null)
          projectID = selection.getProjectID(); 
        FormTextField ProjectID = new FormTextField("projectID", projectID, false, 50);
        ProjectID.setTabIndex(25);
        pfmForm.addElement(ProjectID);
        String priceCode = "";
        if (selection.getSellCode() != null && !selection.getSellCode().equals("-1"))
          priceCode = selection.getSellCode(); 
        FormTextField price_code = new FormTextField("price_code", priceCode, false, 20, 50);
        price_code.setTabIndex(25);
        price_code.setReadOnly(true);
        pfmForm.addElement(price_code);
        if (!selection.getIsDigital()) {
          String priceCodeDPC = "";
          if (selection.getSellCodeDPC() != null && !selection.getSellCodeDPC().equals("-1"))
            priceCodeDPC = selection.getSellCodeDPC(); 
          FormTextField price_codeDPC = new FormTextField("price_codeDPC", priceCodeDPC, false, 20, 50);
          price_codeDPC.setTabIndex(25);
          price_codeDPC.setReadOnly(true);
          price_codeDPC.setDisplayName("Dig. Price Code");
          pfmForm.addElement(price_codeDPC);
        } 
        FormDropDownMenu po_merge_code = MilestoneHelper.getPfmLookupDropDown("po_merge_code", Cache.getPoMergeCodes(), "99", false, true);
        po_merge_code.setDisplayName("PO Merge Code");
        po_merge_code.setTabIndex(14);
        pfmForm.addElement(po_merge_code);
        FormDropDownMenu guarantee_code = MilestoneHelper.getPfmLookupDropDown("guarantee_code", Cache.getGuaranteeCodes(), "N", false, true);
        guarantee_code.setDisplayName("IMI Exempt Code");
        guarantee_code.setTabIndex(29);
        pfmForm.addElement(guarantee_code);
        FormDropDownMenu loose_pick_exempt = MilestoneHelper.getPfmLookupDropDown("loose_pick_exempt", Cache.getLoosePickExempt(), "N", false, true);
        loose_pick_exempt.setDisplayName("Loose Pick Exempt");
        loose_pick_exempt.setTabIndex(28);
        pfmForm.addElement(loose_pick_exempt);
        FormDropDownMenu compilation_code = MilestoneHelper.getPfmLookupDropDown("compilation_code", Cache.getCompilationCodes(), "", false, true);
        compilation_code.setTabIndex(34);
        pfmForm.addElement(compilation_code);
        FormCheckBox valueAdded = new FormCheckBox("ValueAdded", "on", "", false, false);
        valueAdded.setTabIndex(36);
        pfmForm.addElement(valueAdded);
        FormCheckBox parentalAdv = new FormCheckBox("ParentalAdv", "on", "", false, selection.getParentalGuidance());
        parentalAdv.setTabIndex(37);
        parentalAdv.setEnabled(false);
        pfmForm.addElement(parentalAdv);
        FormCheckBox boxSet = new FormCheckBox("BoxSet", "on", "", false, false);
        boxSet.setTabIndex(35);
        boxSet.setId("Box Set");
        pfmForm.addElement(boxSet);
        FormTextField units_per_set = new FormTextField("units_per_set", "", false, 20, 20);
        units_per_set.setDisplayName("1");
        units_per_set.setTabIndex(15);
        units_per_set.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        units_per_set.addFormEvent("onKeyPress", "return checkNumbersOnly(window.event.keyCode);");
        pfmForm.addElement(units_per_set);
        FormDropDownMenu imp_rate_code = MilestoneHelper.getPfmLookupDropDown("imp_rate_code", Cache.getImpRateCodes(), "", false, true);
        imp_rate_code.addFormEvent("onKeyPress", "scanKey(window.event.keyCode, 'document.all.imp_rate_code.options', getMaxLength(document.all.imp_rate_code.options))");
        imp_rate_code.setTabIndex(31);
        pfmForm.addElement(imp_rate_code);
        FormTextField sets_per_carton = new FormTextField("sets_per_carton", "", false, 20, 20);
        sets_per_carton.setDisplayName("Sets Per Carton");
        sets_per_carton.setTabIndex(16);
        sets_per_carton.addFormEvent("onKeyPress", "return checkNumbersOnly(window.event.keyCode);");
        pfmForm.addElement(sets_per_carton);
        String musicType = "";
        if (selection != null)
          musicType = SelectionManager.getLookupObjectValue(selection.getGenre()); 
        FormDropDownMenu music_type = MilestoneHelper.getPfmLookupDropDown("music_type", Cache.getMusicTypes(), musicType, false, true);
        music_type.setTabIndex(30);
        music_type.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        music_type.addFormEvent("onKeyPress", "scanKey(window.event.keyCode, 'document.all.music_type.options', getMaxLength(document.all.music_type.options))");
        pfmForm.addElement(music_type);
        context.putDelivery("music_type", musicType);
        FormDropDownMenu supplier = MilestoneHelper.getPfmLookupDropDown("suppliers", Cache.getSuppliers(), "", false, true);
        supplier.setTabIndex(17);
        supplier.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        pfmForm.addElement(supplier);
        FormDropDownMenu narm = MilestoneHelper.getPfmLookupDropDown("NARM", Cache.getNarmExtracts(), "Y", false, true);
        narm.setDisplayName("Narm Extract Ind.");
        narm.setTabIndex(33);
        narm.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        pfmForm.addElement(narm);
        String defaultEncryption = "N";
        FormDropDownMenu encryptionFlag = MilestoneHelper.getPfmLookupDropDown("encryptionFlag", Cache.getEncryptionFlags(), defaultEncryption, false, true);
        encryptionFlag.setTabIndex(38);
        encryptionFlag.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        pfmForm.addElement(encryptionFlag);
        String names = "";
        Vector importIndicators = Cache.getImportIndicators();
        String[] abbreviation = new String[importIndicators.size()];
        for (int i = importIndicators.size() - 1; i > -1; i--) {
          LookupObject lookupObject = (LookupObject)importIndicators.elementAt(i);
          names = String.valueOf(names) + lookupObject.getName();
          if (i > 0)
            names = String.valueOf(names) + ","; 
          abbreviation[i] = lookupObject.getAbbreviation();
        } 
        String defaultName = "US Made";
        if (selection.getIsDigital() && (
          MilestoneHelper.getStructureName(selection.getReleaseFamilyId()).equals("Canada") || 
          MilestoneHelper.getStructureName(selection.getReleaseFamilyId()).equals("Mexico")))
          defaultName = "Import"; 
        FormRadioButtonGroup ImportIndicator = new FormRadioButtonGroup("ImportIndicator", defaultName, names, false);
        ImportIndicator.setTabIndex(18);
        ImportIndicator.setId("Imp. Ind");
        ImportIndicator.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        pfmForm.addElement(ImportIndicator);
        String ppTxt = "";
        if (selection.getIsDigital())
          ppTxt = "PS"; 
        FormDropDownMenu price_point = MilestoneHelper.getPfmLookupDropDown("price_point", Cache.getPricePoints(), ppTxt, false, true);
        price_point.setTabIndex(32);
        price_point.addFormEvent("onKeyPress", "scanKey(window.event.keyCode, 'document.all.price_point.options', getMaxLength(document.all.price_point.options))");
        price_point.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        pfmForm.addElement(price_point);
        FormTextField lastUpdated = new FormTextField("lastUpdatedDate", false, 50);
        lastUpdated.setValue("");
        lastUpdated.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        pfmForm.addElement(lastUpdated);
        FormTextField lastLegacyUpdateDate = new FormTextField("lastLegacyUpdateDate", false, 50);
        lastLegacyUpdateDate.setValue("");
        pfmForm.addElement(lastLegacyUpdateDate);
        FormTextField lastUpdatedBy = new FormTextField("lastUpdatedBy", false, 50);
        lastUpdatedBy.setValue("");
        lastUpdatedBy.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        pfmForm.addElement(lastUpdatedBy);
        FormTextField approvedBy = new FormTextField("approvedBy", false, 50);
        approvedBy.setValue("");
        approvedBy.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        pfmForm.addElement(approvedBy);
        FormTextField enteredBy = new FormTextField("enteredBy", false, 50);
        enteredBy.setValue("");
        enteredBy.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        pfmForm.addElement(enteredBy);
        FormTextField verifiedBy = new FormTextField("verifiedBy", false, 50);
        verifiedBy.setValue("");
        verifiedBy.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        pfmForm.addElement(verifiedBy);
        String changeNumberText = "-1";
        FormTextField ChangeNumber = new FormTextField("ChangeNumber", changeNumberText, false, 2, 2);
        ChangeNumber.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        ChangeNumber.setEnabled(false);
        ChangeNumber.setId("Change #");
        ChangeNumber.setTabIndex(6);
        pfmForm.addElement(ChangeNumber);
      }  
    pfmForm = addSelectionSearchElements(context, selection, pfmForm);
    if (context.getSessionValue("NOTEPAD_PFM_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_PFM_VISIBLE")); 
    return pfmForm;
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
  
  public boolean editSave(Dispatcher dispatcher, Context context, String command) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 0);
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Selection selection = null;
    Selection selectionTemp = null;
    int selectionID = -1;
    if (command.equalsIgnoreCase("selectionSave")) {
      selectionTemp = (Selection)context.getSessionValue("pfmBomSelection");
      selection = SelectionManager.getInstance().getSelectionHeader(selectionTemp.getSelectionID());
    } else {
      selectionID = Integer.parseInt(context.getRequestValue("selectionID"));
      selection = SelectionManager.getInstance().getSelectionHeader(selectionID);
    } 
    MessageObject messageObject = new MessageObject();
    try {
      messageObject.selectionObj = (Selection)selection.clone();
    } catch (CloneNotSupportedException ce) {
      messageObject.selectionObj = selection;
    } 
    Pfm pfm = SelectionManager.getInstance().getPfm(selection.getSelectionID());
    boolean finalFlag = false;
    boolean sendEmail = false;
    boolean firstTimeFinal = false;
    if (pfm != null) {
      if (context.getSessionValue("originalComment") == null)
        context.putSessionValue("originalComment", pfm.getComments()); 
      Form form = buildForm(context, pfm, command);
      form.addElement(new FormHidden("cmd", "pfm-edit-save", true));
      form.addElement(new FormHidden("copyPaste", "copy", true));
      long timestamp = pfm.getLastUpdatedCk();
      if (!command.equalsIgnoreCase("selectionSave")) {
        String timestampStr = context.getRequestValue("pfmLastUpdateCheck");
        if (timestampStr != null)
          pfm.setLastUpdatedCk(Long.parseLong(timestampStr)); 
      } 
      if (SelectionManager.getInstance().isTimestampValid(pfm)) {
        pfm.setLastUpdatedCk(timestamp);
        if (!command.equalsIgnoreCase("selectionSave"))
          form.setValues(context); 
        String sendOption = form.getStringValue("sendOption");
        if (!form.isUnchanged()) {
          String printOption = form.getStringValue("printOption");
          if (printOption.equalsIgnoreCase("Draft")) {
            pfm.setPrintOption("Draft");
            String mode = form.getStringValue("mode");
            if (mode.equalsIgnoreCase("Add")) {
              mode = "Add";
              pfm.setMode(mode);
              pfm.setReleaseType("A");
              pfm.setChangeNumber("-1");
            } else {
              pfm.setMode("Change");
              pfm.setReleaseType("M");
              pfm.setChangeNumber("-1");
            } 
            pfm.setMode("Add");
            pfm.setReleaseType("A");
            pfm.setChangeNumber("-1");
            FormTextField changeField = (FormTextField)form.getElement("ChangeNumber");
            changeField.setValue("-1");
            FormRadioButtonGroup modeField = (FormRadioButtonGroup)form.getElement("mode");
            modeField.setValue("Add");
          } else {
            pfm.setPrintOption("Final");
            finalFlag = true;
            sendEmail = false;
            int changeNumberInt = -1;
            try {
              changeNumberInt = Integer.parseInt(form.getStringValue("ChangeNumber"));
            } catch (Exception e) {
              changeNumberInt = -1;
            } 
            String newMode = "";
            if (changeNumberInt >= 0) {
              if (context.getSessionValue("originalComment") != null)
                ((FormTextArea)form.getElement("comments")).setStartingValue((String)context.getSessionValue("originalComment")); 
              StringBuffer changedFields = new StringBuffer();
              String upcStripped = MilestoneHelper_2.reformat_UPC_SSG_SGC_forSave(form.getStringValue("upc"), "UPC", selection.getIsDigital(), true);
              form.getElement("upc").setValue(upcStripped);
              String ssgStripped = MilestoneHelper_2.reformat_UPC_SSG_SGC_forSave(form.getStringValue("sound_scan_code"), "SSG", selection.getIsDigital(), true);
              form.getElement("sound_scan_code").setValue(ssgStripped);
              if (!command.equalsIgnoreCase("pfm-edit-save-comment") && 
                EmailDistribution.isFormChanged(form.getChangedElements(), changedFields, true, false, form, messageObject)) {
                sendEmail = true;
                if (sendOption != null && sendOption.equalsIgnoreCase("Send Email") && 
                  !context.getCommand().equals("selection-send-cancel"))
                  changeNumberInt++; 
                pfm.setMode("Change");
                pfm.setReleaseType("M");
                newMode = "Change";
                pfm.setChangeNumber(Integer.toString(changeNumberInt));
                FormTextField changeField = (FormTextField)form.getElement("ChangeNumber");
                changeField.setValue(Integer.toString(changeNumberInt));
                FormRadioButtonGroup modeField = (FormRadioButtonGroup)form.getElement("mode");
                modeField.setValue(newMode);
              } 
            } else {
              firstTimeFinal = true;
              sendEmail = true;
              if (!context.getCommand().equals("selection-send-cancel"))
                changeNumberInt++; 
              pfm.setMode("Add");
              pfm.setReleaseType("A");
              newMode = "Add";
              pfm.setChangeNumber(Integer.toString(changeNumberInt));
              FormTextField changeField = (FormTextField)form.getElement("ChangeNumber");
              changeField.setValue(Integer.toString(changeNumberInt));
              FormRadioButtonGroup modeField = (FormRadioButtonGroup)form.getElement("mode");
              modeField.setValue(newMode);
            } 
          } 
          if (sendOption != null && !sendOption.equalsIgnoreCase("Send Email"))
            sendEmail = false; 
          String preparedBy = form.getStringValue("prepared_by");
          pfm.setPreparedBy(preparedBy);
          String phone = form.getStringValue("phone");
          pfm.setPhone(phone);
          String email = form.getStringValue("email");
          pfm.setEmail(email);
          String fax = form.getStringValue("fax");
          pfm.setFaxNumber(fax);
          String comments = form.getStringValue("comments");
          pfm.setComments(comments);
          String soundScanGroup = form.getStringValue("sound_scan_code");
          pfm.setSoundScanGrp(soundScanGroup);
          String operatingCompany = form.getStringValue("operating_company");
          pfm.setOperatingCompany(operatingCompany);
          String productNumber = form.getStringValue("product_number");
          pfm.setProductNumber(productNumber);
          String musicLine = form.getStringValue("music_line");
          pfm.setMusicLine(musicLine);
          String configCode = form.getStringValue("config_code");
          pfm.setConfigCode(configCode);
          String repertoireOwner = form.getStringValue("repertoire_owner");
          pfm.setRepertoireOwner(repertoireOwner);
          String modifier = form.getStringValue("modifier");
          pfm.setModifier(modifier);
          String repClass = repertoireOwner;
          pfm.setRepertoireClass(repClass);
          String upc = form.getStringValue("upc");
          pfm.setUpc(upc);
          String title = form.getStringValue("title");
          pfm.setTitle(title);
          String artist = form.getStringValue("artist");
          pfm.setArtist(artist);
          String titleID = form.getStringValue("titleID");
          pfm.setTitleId(titleID);
          String returnCode = "T";
          if (!selection.getIsDigital())
            returnCode = form.getStringValue("return_code"); 
          pfm.setReturnCode(returnCode);
          String exportFlag = form.getStringValue("export_flag");
          pfm.setExportFlag(exportFlag);
          String superLabel = form.getStringValue("super_label");
          pfm.setSuperLabel(superLabel);
          String countries = form.getStringValue("countries");
          pfm.setCountries(countries);
          String labelCode = form.getStringValue("label_code");
          pfm.setLabelCode(labelCode);
          String spineTitle = form.getStringValue("spine_title");
          pfm.setSpineTitle(spineTitle);
          String companyCode = form.getStringValue("company_code");
          pfm.setCompanyCode(companyCode);
          String spineArtist = form.getStringValue("spine_artist");
          pfm.setSpineArtist(spineArtist);
          String priceCode = (form.getStringValue("price_code") != null) ? form.getStringValue("price_code") : "";
          pfm.setPriceCode(priceCode);
          String priceCodeDPC = (form.getStringValue("price_codeDPC") != null) ? form.getStringValue("price_codeDPC") : "";
          pfm.setPriceCodeDPC(priceCodeDPC);
          String poMergeCode = "99";
          if (!selection.getIsDigital())
            poMergeCode = form.getStringValue("po_merge_code"); 
          pfm.setPoMergeCode(poMergeCode);
          String guaranteeCode = "N";
          if (!selection.getIsDigital())
            guaranteeCode = form.getStringValue("guarantee_code"); 
          pfm.setGuaranteeCode(guaranteeCode);
          String loosePickExemptCode = "N";
          if (!selection.getIsDigital())
            loosePickExemptCode = form.getStringValue("loose_pick_exempt"); 
          pfm.setLoosePickExemptCode(loosePickExemptCode);
          pfm.setValueAdded(((FormCheckBox)form.getElement("ValueAdded"))
              .isChecked());
          pfm.setBoxSet(((FormCheckBox)form.getElement("BoxSet")).isChecked());
          String compilation = form.getStringValue("compilation_code");
          pfm.setCompilationCode(compilation);
          String unitsPerSet = "1";
          if (!selection.getIsDigital())
            unitsPerSet = form.getStringValue("units_per_set").trim(); 
          int units = 0;
          if (unitsPerSet != null && !unitsPerSet.equals(""))
            try {
              units = Integer.parseInt(unitsPerSet);
            } catch (NumberFormatException e) {
              units = 0;
            }  
          pfm.setUnitsPerSet(units);
          String impRateCode = form.getStringValue("imp_rate_code");
          pfm.setImpRateCode(impRateCode);
          String setsPerCarton = "1";
          if (!selection.getIsDigital())
            setsPerCarton = form.getStringValue("sets_per_carton").trim(); 
          int sets = 0;
          if (setsPerCarton != null && !setsPerCarton.equals(""))
            try {
              sets = Integer.parseInt(setsPerCarton);
            } catch (NumberFormatException e) {
              sets = 0;
            }  
          pfm.setSetsPerCarton(sets);
          String musicType = form.getStringValue("music_type");
          pfm.setMusicType((Genre)SelectionManager.getLookupObject(musicType, Cache.getMusicTypes()));
          String prefix = form.getStringValue("prefix");
          pfm.setPrefixID((PrefixCode)SelectionManager.getLookupObject(prefix, Cache.getPrefixCodes()));
          String selectionNo = form.getStringValue("selectionNo");
          pfm.setSelectionNo(selectionNo);
          String suppliers = form.getStringValue("suppliers");
          pfm.setSupplier(suppliers);
          String narm = "N";
          if (!selection.getIsDigital())
            narm = form.getStringValue("NARM"); 
          pfm.setNarmFlag(narm);
          String eFlag = form.getStringValue("encryptionFlag");
          pfm.setEncryptionFlag(eFlag);
          String importIndicator = form.getStringValue("ImportIndicator");
          Vector importIndicators = Cache.getImportIndicators();
          String[] name = new String[importIndicators.size()];
          String[] abbreviation = new String[importIndicators.size()];
          for (int i = 0; i < importIndicators.size(); i++) {
            LookupObject lookupObject = (LookupObject)importIndicators
              .elementAt(i);
            name[i] = lookupObject.getName();
            abbreviation[i] = lookupObject.getAbbreviation();
            if (importIndicator.equalsIgnoreCase(name[i]))
              importIndicator = abbreviation[i]; 
          } 
          pfm.setImportIndicator(importIndicator);
          String pricePoint = form.getStringValue("price_point");
          if (selection.getIsDigital())
            pricePoint = "PS"; 
          pfm.setPricePoint(pricePoint);
          String projectID = form.getStringValue("projectID");
          pfm.setProjectID(projectID);
          String streetDateString = form.getStringValue("releaseDate");
          try {
            Calendar streetDate = MilestoneHelper.getDate(streetDateString);
            pfm.setStreetDate(streetDate);
          } catch (Exception e) {
            pfm.setStreetDate(null);
          } 
          String strStatus = form.getStringValue("status");
          pfm.setStatus(strStatus);
          String projectIDtoValidate = "";
          if (pfm.getProjectID().trim().indexOf("-") > -1) {
            for (int j = 1; j < pfm.getProjectID().trim().length(); j++) {
              if (pfm.getProjectID().trim().charAt(j) != '-')
                projectIDtoValidate = String.valueOf(projectIDtoValidate) + pfm.getProjectID().trim().charAt(j); 
            } 
          } else {
            projectIDtoValidate = pfm.getProjectID().trim();
          } 
          if (command.equalsIgnoreCase("selectionSave")) {
            pfm.setParentalGuidance(selection.getParentalGuidance());
          } else {
            pfm.setParentalGuidance(((FormCheckBox)form.getElement("ParentalAdv")).isChecked());
          } 
          Form emailDistForm = form;
          form = buildForm(context, pfm, command);
          if (sendEmail)
            if (context.getSessionValue("originalComment") != null) {
              ((FormTextArea)form.getElement("comments")).setStartingValue((String)context.getSessionValue("originalComment"));
              ((FormTextArea)emailDistForm.getElement("comments")).setStartingValue((String)context.getSessionValue("originalComment"));
            }  
          form.addElement(new FormHidden("cmd", "pfm-edit-save", true));
          FormValidation formValidation = form.validate();
          if (formValidation.isGood()) {
            Pfm savedPfm = SelectionManager.getInstance().savePfm(pfm, user, selection.getIsDigital());
            if (selection.getIsDigital()) {
              SelectionManager.getInstance().saveSelectionDataFromDigitalPfm(
                  pfm.getReleaseId(), 
                  pfm.getPrefixID(), 
                  pfm.getSelectionNo(), 
                  
                  MilestoneHelper_2.reformat_UPC_SSG_SGC_forSave(pfm.getUpc(), "UPC", selection.getIsDigital(), true), 
                  pfm.getConfigCode(), 
                  
                  MilestoneHelper_2.reformat_UPC_SSG_SGC_forSave(pfm.getSoundScanGrp(), "SSG", selection.getIsDigital(), true));
              if (messageObject != null && messageObject.selectionObj != null) {
                messageObject.selectionObj.setUpc(pfm.getUpc());
                messageObject.selectionObj.setConfigCode(pfm.getConfigCode());
                messageObject.selectionObj.setSoundScanGrp(pfm.getSoundScanGrp());
              } 
            } 
            FormElement lastUpdated = form.getElement("lastUpdatedDate");
            lastUpdated.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
            lastUpdated.setValue(MilestoneHelper.getLongDate(savedPfm.getLastUpdatedDate()));
            context.putSessionValue("Pfm", savedPfm);
            context.putDelivery("Form", form);
            notepad.setAllContents(null);
            notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 0);
            notepad.goToSelectedPage();
            Boolean IsSaveSend = Boolean.valueOf(false);
            if ((finalFlag && 
              !context.getCommand().equals("selection-send-cancel") && 
              !command.equalsIgnoreCase("pfm-edit-save-comment")) || firstTimeFinal) {
              String lastUpdatedDate = "";
              String lastUpdatedBy = "";
              if (savedPfm.getLastUpdatedDate() != null)
                lastUpdatedDate = MilestoneHelper.getCustomFormatedDate(savedPfm.getLastUpdatedDate(), "M/d/yyyy hh:mm:ss a 'ET'"); 
              if (UserManager.getInstance().getUser(savedPfm.getLastUpdatingUser()) != null)
                lastUpdatedBy = UserManager.getInstance().getUser(savedPfm.getLastUpdatingUser()).getName(); 
              if (sendEmail && (EmailDistribution.putEmailBody(emailDistForm, context, selection, lastUpdatedDate, lastUpdatedBy, "PFM", messageObject) || 
                messageObject.IsPushOnDemand)) {
                try {
                  messageObject.pfmObj = (Pfm)savedPfm.clone();
                } catch (CloneNotSupportedException ce) {
                  messageObject.pfmObj = savedPfm;
                } 
                print(dispatcher, context, command, 2, true, messageObject);
                if (messageObject.IsPushPFM || messageObject.IsPushOnDemand)
                  PushCommunication.pushPFMLegacyAppendMessge(messageObject, PushCommunication.pushPFMLegacy(pfm, selection, context)); 
                EmailDistribution.sendEmail(dispatcher, context, "", messageObject);
                EmailDistribution.removeSessionValues(context);
                IsSaveSend = Boolean.valueOf(true);
              } 
            } 
            DcGDRSResults dcGDRSResults = SelectionHandler.GDRSProductStatusGet(selection, selection.getCompany().getParentEnvironment().getStructureID());
            boolean IsGDRSactive = (!dcGDRSResults.getStatus().equals("") && !dcGDRSResults.getStatus().equals("DELETE"));
            if (!selection.getNoDigitalRelease()) {
              if (!dcGDRSResults.getForceNoDigitalRelease().booleanValue()) {
                String status = IsSaveSend.booleanValue() ? "SAVE_SEND" : "CREATE_EDIT";
                SelectionManager.GDRS_QueueAddReleaseId(selection, status);
              } 
            } else if (IsGDRSactive) {
              SelectionManager.GDRS_QueueAddReleaseId(selection, "DELETE");
            } 
            if (!command.equalsIgnoreCase("pfm-edit-save-comment"))
              context.putSessionValue("originalComment", savedPfm.getComments()); 
            context.putSessionValue("originalCommentRelId", new Integer(savedPfm.getReleaseId()));
          } else {
            context.putDelivery("FormValidation", formValidation);
          } 
        } 
        form.addElement(new FormHidden("OrderBy", "", true));
        context.putDelivery("Form", form);
        if (!command.equalsIgnoreCase("selectionSave"))
          return edit(dispatcher, context, command); 
        return true;
      } 
      context.putDelivery("AlertMessage", "The record is unavailable for editing at this time.  Please refresh your screen and edit it at a later time.");
      context.putDelivery("Form", form);
      if (!command.equalsIgnoreCase("selectionSave"))
        return context.includeJSP("pfm-editor.jsp"); 
      return true;
    } 
    Form form = buildForm(context, null, command);
    form.addElement(new FormHidden("cmd", "pfm-edit-save", true));
    form.addElement(new FormHidden("copyPaste", "copy", true));
    if (!command.equalsIgnoreCase("selectionSave"))
      form.setValues(context); 
    pfm = new Pfm();
    String printOption = form.getStringValue("printOption");
    if (printOption.equalsIgnoreCase("Draft")) {
      pfm.setPrintOption("Draft");
      String mode = form.getStringValue("mode");
      if (!mode.equalsIgnoreCase("Add")) {
        mode = "Change";
        pfm.setMode(mode);
        pfm.setReleaseType("M");
      } else {
        pfm.setMode(mode);
        pfm.setReleaseType("A");
        pfm.setChangeNumber("-1");
      } 
      pfm.setMode("Add");
      pfm.setReleaseType("A");
      pfm.setChangeNumber("-1");
      FormTextField changeField = (FormTextField)form.getElement("ChangeNumber");
      changeField.setValue("-1");
      FormRadioButtonGroup modeField = (FormRadioButtonGroup)form.getElement("mode");
      modeField.setValue("Add");
    } else {
      pfm.setPrintOption("Final");
      finalFlag = true;
      sendEmail = false;
      int changeNumberInt = -1;
      try {
        changeNumberInt = Integer.parseInt(form.getStringValue("ChangeNumber"));
      } catch (Exception e) {
        changeNumberInt = -1;
      } 
      String newMode = "";
      if (changeNumberInt >= 0) {
        StringBuffer changedFields = new StringBuffer();
        String upcStripped = MilestoneHelper_2.reformat_UPC_SSG_SGC_forSave(form.getStringValue("upc"), "UPC", selection.getIsDigital(), true);
        form.getElement("upc").setValue(upcStripped);
        String ssgStripped = MilestoneHelper_2.reformat_UPC_SSG_SGC_forSave(form.getStringValue("sound_scan_code"), "SSG", selection.getIsDigital(), true);
        form.getElement("sound_scan_code").setValue(ssgStripped);
        if (!command.equalsIgnoreCase("pfm-edit-save-comment") && 
          EmailDistribution.isFormChanged(form.getChangedElements(), changedFields, true, false, form, messageObject)) {
          sendEmail = true;
          changeNumberInt++;
          pfm.setMode("Change");
          pfm.setReleaseType("M");
          newMode = "Change";
          pfm.setChangeNumber(Integer.toString(changeNumberInt));
          FormTextField changeField = (FormTextField)form.getElement("ChangeNumber");
          changeField.setValue(Integer.toString(changeNumberInt));
          FormRadioButtonGroup modeField = (FormRadioButtonGroup)form.getElement("mode");
          modeField.setValue(newMode);
        } 
      } else {
        firstTimeFinal = true;
        sendEmail = true;
        changeNumberInt++;
        pfm.setMode("Add");
        pfm.setReleaseType("A");
        newMode = "Add";
        pfm.setChangeNumber(Integer.toString(changeNumberInt));
        FormTextField changeField = (FormTextField)form.getElement("ChangeNumber");
        changeField.setValue(Integer.toString(changeNumberInt));
        FormRadioButtonGroup modeField = (FormRadioButtonGroup)form.getElement("mode");
        modeField.setValue(newMode);
      } 
    } 
    if (form.getStringValue("selectionID") != null)
      try {
        selectionID = Integer.parseInt(form.getStringValue("selectionID"));
      } catch (NumberFormatException e) {
        selectionID = -1;
      }  
    pfm.setReleaseId(selectionID);
    String preparedBy = "";
    if (form.getStringValue("prepared_by") != null)
      preparedBy = form.getStringValue("prepared_by"); 
    pfm.setPreparedBy(preparedBy);
    String phone = form.getStringValue("phone");
    pfm.setPhone(phone);
    String email = form.getStringValue("email");
    pfm.setEmail(email);
    String fax = "";
    if (form.getStringValue("fax") != null)
      fax = form.getStringValue("fax"); 
    pfm.setFaxNumber(fax);
    String comments = "";
    if (form.getStringValue("comments") != null)
      comments = form.getStringValue("comments"); 
    pfm.setComments(comments);
    String mode = "";
    if (form.getStringValue("mode") != null) {
      mode = form.getStringValue("mode");
      if (!mode.equalsIgnoreCase("Add")) {
        pfm.setMode(mode);
        pfm.setReleaseType("M");
      } else {
        pfm.setMode(mode);
        pfm.setReleaseType("A");
      } 
    } 
    String soundScanGroup = "";
    if (selection.getIsDigital()) {
      soundScanGroup = form.getStringValue("sound_scan_code");
    } else {
      soundScanGroup = selection.getSoundScanGrp();
    } 
    pfm.setSoundScanGrp(soundScanGroup);
    String operatingCompany = form.getStringValue("operating_company");
    pfm.setOperatingCompany(operatingCompany);
    String productNumber = form.getStringValue("product_number");
    pfm.setProductNumber(productNumber);
    String musicLine = "";
    if (form.getStringValue("music_line") != null)
      musicLine = form.getStringValue("music_line"); 
    pfm.setMusicLine(musicLine);
    String prefix = "";
    if (form.getStringValue("prefix") != null)
      prefix = form.getStringValue("prefix"); 
    pfm.setPrefixID((PrefixCode)SelectionManager.getLookupObject(prefix, Cache.getPrefixCodes()));
    String selectionNo = "";
    if (form.getStringValue("selectionNo") != null)
      selectionNo = form.getStringValue("selectionNo"); 
    pfm.setSelectionNo(selectionNo);
    String configCode = "";
    if (form.getStringValue("config_code") != null)
      configCode = form.getStringValue("config_code"); 
    pfm.setConfigCode(configCode);
    String repertoireOwner = "";
    if (form.getStringValue("repertoire_owner") != null)
      repertoireOwner = form.getStringValue("repertoire_owner"); 
    pfm.setRepertoireOwner(repertoireOwner);
    String modifier = "";
    if (form.getStringValue("modifier") != null)
      modifier = form.getStringValue("modifier"); 
    pfm.setModifier(modifier);
    String repertoireClass = "";
    String tempHold = "";
    Vector repOwners = Cache.getRepertoireClasses();
    for (int i = 0; i < repOwners.size(); i++) {
      repertoireClass = ((LookupObject)repOwners.elementAt(i)).getAbbreviation();
      if (repertoireOwner.equals(repertoireClass))
        tempHold = repertoireClass; 
    } 
    String repClass = tempHold;
    pfm.setRepertoireClass(repClass);
    String upc = "";
    if (form.getStringValue("upc") != null)
      upc = form.getStringValue("upc"); 
    pfm.setUpc(upc);
    String title = "";
    if (form.getStringValue("title") != null)
      title = form.getStringValue("title"); 
    pfm.setTitle(title);
    String artist = "";
    if (form.getStringValue("artist") != null)
      artist = form.getStringValue("artist"); 
    pfm.setArtist(artist);
    String titleID = "";
    if (selection != null)
      titleID = selection.getTitleID(); 
    pfm.setTitleId(titleID);
    String returnCode = "";
    if (form.getStringValue("return_code") != null)
      returnCode = form.getStringValue("return_code"); 
    if (selection.getIsDigital())
      returnCode = "T"; 
    pfm.setReturnCode(returnCode);
    String exportFlag = "";
    if (form.getStringValue("export_flag") != null)
      exportFlag = form.getStringValue("export_flag"); 
    pfm.setExportFlag(exportFlag);
    String superLabel = "";
    if (form.getStringValue("super_label") != null)
      superLabel = form.getStringValue("super_label"); 
    pfm.setSuperLabel(superLabel);
    String countries = "";
    if (form.getStringValue("countries") != null)
      countries = form.getStringValue("countries"); 
    pfm.setCountries(countries);
    String labelCode = "";
    if (form.getStringValue("label_code") != null)
      labelCode = form.getStringValue("label_code"); 
    pfm.setLabelCode(labelCode);
    String spineTitle = "";
    if (form.getStringValue("spine_title") != null)
      spineTitle = form.getStringValue("spine_title"); 
    pfm.setSpineTitle(spineTitle);
    String companyCode = "";
    if (form.getStringValue("company_code") != null)
      companyCode = form.getStringValue("company_code"); 
    pfm.setCompanyCode(companyCode);
    String spineArtist = "";
    if (form.getStringValue("spine_artist") != null)
      spineArtist = form.getStringValue("spine_artist"); 
    pfm.setSpineArtist(spineArtist);
    String priceCode = "";
    if (form.getStringValue("price_code") != null)
      priceCode = form.getStringValue("price_code"); 
    pfm.setPriceCode(priceCode);
    String priceCodeDPC = "";
    if (form.getStringValue("price_codeDPC") != null)
      priceCodeDPC = form.getStringValue("price_codeDPC"); 
    pfm.setPriceCodeDPC(priceCodeDPC);
    String poMergeCode = "";
    if (form.getStringValue("po_merge_code") != null)
      poMergeCode = form.getStringValue("po_merge_code"); 
    if (selection.getIsDigital())
      poMergeCode = "99"; 
    pfm.setPoMergeCode(poMergeCode);
    String guaranteeCode = "";
    if (form.getStringValue("guarantee_code") != null)
      guaranteeCode = form.getStringValue("guarantee_code"); 
    if (selection.getIsDigital())
      guaranteeCode = "N"; 
    pfm.setGuaranteeCode(guaranteeCode);
    String loosePickExemptCode = "";
    if (form.getStringValue("loose_pick_exempt") != null)
      loosePickExemptCode = form.getStringValue("loose_pick_exempt"); 
    if (selection.getIsDigital())
      loosePickExemptCode = "N"; 
    pfm.setLoosePickExemptCode(loosePickExemptCode);
    pfm.setValueAdded(((FormCheckBox)form.getElement("ValueAdded")).isChecked());
    pfm.setBoxSet(((FormCheckBox)form.getElement("BoxSet")).isChecked());
    String compilationCode = "";
    if (form.getStringValue("compilation_code") != null)
      compilationCode = form.getStringValue("compilation_code"); 
    pfm.setCompilationCode(compilationCode);
    String impRateCode = "";
    if (form.getStringValue("imp_rate_code") != null)
      impRateCode = form.getStringValue("imp_rate_code"); 
    pfm.setImpRateCode(impRateCode);
    String unitsString = form.getStringValue("units_per_set").trim();
    int units = 0;
    try {
      units = Integer.parseInt(unitsString);
    } catch (NumberFormatException numberFormatException) {}
    if (selection.getIsDigital())
      units = 1; 
    pfm.setUnitsPerSet(units);
    int sets = 0;
    try {
      sets = Integer.parseInt(form.getStringValue("sets_per_carton").trim());
    } catch (NumberFormatException numberFormatException) {}
    if (selection.getIsDigital())
      sets = 1; 
    pfm.setSetsPerCarton(sets);
    String musicType = "";
    musicType = form.getStringValue("music_type");
    pfm.setMusicType((Genre)SelectionManager.getLookupObject(musicType, Cache.getMusicTypes()));
    String suppliers = "";
    if (form.getStringValue("suppliers") != null)
      suppliers = form.getStringValue("suppliers"); 
    pfm.setSupplier(suppliers);
    String narm = "";
    if (form.getStringValue("NARM") != null)
      narm = form.getStringValue("NARM"); 
    if (selection.getIsDigital())
      narm = "N"; 
    pfm.setNarmFlag(narm);
    String eFlag = "";
    if (form.getStringValue("encryptionFlag") != null)
      eFlag = form.getStringValue("encryptionFlag"); 
    pfm.setEncryptionFlag(eFlag);
    String importIndicator = "";
    if (form.getStringValue("ImportIndicator") != null)
      importIndicator = form.getStringValue("ImportIndicator"); 
    Vector importIndicators = Cache.getImportIndicators();
    String[] name = new String[importIndicators.size()];
    String[] abbreviation = new String[importIndicators.size()];
    for (int i = 0; i < importIndicators.size(); i++) {
      LookupObject lookupObject = (LookupObject)importIndicators.elementAt(i);
      name[i] = lookupObject.getName();
      abbreviation[i] = lookupObject.getAbbreviation();
      if (importIndicator.equalsIgnoreCase(name[i]))
        importIndicator = abbreviation[i]; 
    } 
    pfm.setImportIndicator(importIndicator);
    String pricePoint = "";
    if (form.getStringValue("price_point") != null)
      pricePoint = form.getStringValue("price_point"); 
    if (selection.getIsDigital())
      pricePoint = "PS"; 
    pfm.setPricePoint(pricePoint);
    String projectID = form.getStringValue("projectID");
    pfm.setProjectID(projectID);
    String streetDateString = form.getStringValue("releaseDate");
    try {
      Calendar streetDate = MilestoneHelper.getDate(streetDateString);
      pfm.setStreetDate(streetDate);
    } catch (Exception e) {
      pfm.setStreetDate(null);
    } 
    String strStatus = form.getStringValue("status");
    pfm.setStatus(strStatus);
    pfm.setParentalGuidance(((FormCheckBox)form.getElement("ParentalAdv")).isChecked());
    String projectIDtoValidate = "";
    if (pfm.getProjectID().trim().indexOf("-") > -1) {
      for (int j = 1; j < pfm.getProjectID().trim().length(); j++) {
        if (pfm.getProjectID().trim().charAt(j) != '-')
          projectIDtoValidate = String.valueOf(projectIDtoValidate) + pfm.getProjectID().trim().charAt(j); 
      } 
    } else {
      projectIDtoValidate = pfm.getProjectID().trim();
    } 
    if (!form.isUnchanged()) {
      FormValidation formValidation = form.validate();
      if (formValidation.isGood()) {
        Pfm savedPfm = SelectionManager.getInstance().saveNewPfm(pfm, user, selection.getIsDigital());
        if (selection.getIsDigital()) {
          SelectionManager.getInstance().saveSelectionDataFromDigitalPfm(
              pfm.getReleaseId(), 
              pfm.getPrefixID(), 
              pfm.getSelectionNo(), 
              pfm.getUpc(), 
              pfm.getConfigCode(), 
              pfm.getSoundScanGrp());
          if (messageObject != null && messageObject.selectionObj != null) {
            messageObject.selectionObj.setUpc(pfm.getUpc());
            messageObject.selectionObj.setConfigCode(pfm.getConfigCode());
            messageObject.selectionObj.setSoundScanGrp(pfm.getSoundScanGrp());
          } 
        } 
        Form emailDistForm = form;
        context.putSessionValue("Pfm", savedPfm);
        context.putDelivery("Form", form);
        notepad.setAllContents(null);
        notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 0);
        notepad.goToSelectedPage();
        Boolean IsSaveSend = Boolean.valueOf(false);
        if ((finalFlag && !command.equalsIgnoreCase("pfm-edit-save-comment")) || firstTimeFinal) {
          String lastUpdatedDate = "";
          String lastUpdatedBy = "";
          if (savedPfm.getLastUpdatedDate() != null)
            lastUpdatedDate = MilestoneHelper.getCustomFormatedDate(savedPfm.getLastUpdatedDate(), "M/d/yyyy hh:mm:ss a 'ET'"); 
          if (UserManager.getInstance().getUser(savedPfm.getLastUpdatingUser()) != null)
            lastUpdatedBy = UserManager.getInstance().getUser(savedPfm.getLastUpdatingUser()).getName(); 
          if (sendEmail && (EmailDistribution.putEmailBody(emailDistForm, context, selection, lastUpdatedDate, lastUpdatedBy, "PFM", messageObject) || 
            messageObject.IsPushOnDemand)) {
            try {
              messageObject.pfmObj = (Pfm)savedPfm.clone();
            } catch (CloneNotSupportedException ce) {
              messageObject.pfmObj = savedPfm;
            } 
            print(dispatcher, context, command, 2, true, messageObject);
            if (messageObject.IsPushPFM || messageObject.IsPushOnDemand)
              PushCommunication.pushPFMLegacyAppendMessge(messageObject, PushCommunication.pushPFMLegacy(pfm, selection, context)); 
            EmailDistribution.sendEmail(dispatcher, context, "", messageObject);
            EmailDistribution.removeSessionValues(context);
            IsSaveSend = Boolean.valueOf(true);
          } 
        } 
        DcGDRSResults dcGDRSResults = SelectionHandler.GDRSProductStatusGet(selection, selection.getCompany().getParentEnvironment().getStructureID());
        boolean IsGDRSactive = (!dcGDRSResults.getStatus().equals("") && !dcGDRSResults.getStatus().equals("DELETE"));
        if (!command.equalsIgnoreCase("pfm-edit-save-comment") && !selection.getNoDigitalRelease()) {
          if (!dcGDRSResults.getForceNoDigitalRelease().booleanValue()) {
            String status = IsSaveSend.booleanValue() ? "SAVE_SEND" : "CREATE_EDIT";
            SelectionManager.GDRS_QueueAddReleaseId(selection, status);
          } 
        } else if (IsGDRSactive) {
          SelectionManager.GDRS_QueueAddReleaseId(selection, "DELETE");
        } 
        if (!command.equalsIgnoreCase("pfm-edit-save-comment"))
          context.putSessionValue("originalComment", savedPfm.getComments()); 
        context.putSessionValue("originalCommentRelId", new Integer(savedPfm.getReleaseId()));
        if (!command.equalsIgnoreCase("selectionSave"))
          return edit(dispatcher, context, command); 
        return true;
      } 
      context.putDelivery("FormValidation", formValidation);
      form.addElement(new FormHidden("OrderBy", "", true));
      context.putDelivery("Form", form);
      if (!command.equalsIgnoreCase("selectionSave"))
        return context.includeJSP("pfm-editor.jsp"); 
      return true;
    } 
    if (!command.equalsIgnoreCase("selectionSave"))
      return edit(dispatcher, context, command); 
    return true;
  }
  
  public boolean print(Dispatcher dispatcher, Context context, String command, int pdfRtf, boolean ie5, MessageObject messageObject) {
    Selection selection = null;
    if (command.equalsIgnoreCase("selectionSave")) {
      selection = (Selection)context.getSessionValue("pfmBomSelection");
    } else {
      selection = MilestoneHelper.getScreenSelection(context);
    } 
    if (messageObject != null && messageObject.selectionObj != null)
      selection = messageObject.selectionObj; 
    Pfm pfm = (Pfm)context.getSessionValue("Pfm");
    if (selection != null) {
      int selectionID = selection.getSelectionID();
      pfm = SelectionManager.getInstance().getPfm(selectionID);
    } 
    if (messageObject != null && messageObject.pfmObj != null)
      pfm = messageObject.pfmObj; 
    if (pfm != null)
      try {
        DefaultTableLens table_contents;
        InputStream input = new FileInputStream(String.valueOf(ReportHandler.reportPath) + "\\pfmtemplate.xml");
        XStyleSheet report = 
          (XStyleSheet)Builder.getBuilder(1, input).read(null);
        SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
        String todayLong = formatter.format(new Date());
        report.setElement("txtReportDate", todayLong);
        report.setElement("bottomdate", todayLong);
        report.setElement("txtChangeNumber", pfm.getChangeNumber());
        String mode = pfm.getMode();
        if (mode.equalsIgnoreCase("Add")) {
          report.setElement("txtAdd", "X");
          report.setElement("txtModify", "");
        } else {
          report.setElement("txtAdd", "");
          report.setElement("txtModify", "X");
        } 
        if (selection.getIsDigital())
          report.setElement("txtProdType", "Digital"); 
        if (selection.getIsDigital()) {
          table_contents = new DefaultTableLens(24, 8);
        } else {
          table_contents = new DefaultTableLens(29, 8);
        } 
        table_contents.setHeaderRowCount(0);
        table_contents.setColWidth(0, 120);
        table_contents.setColWidth(1, 30);
        table_contents.setColWidth(2, 200);
        table_contents.setColWidth(3, 100);
        table_contents.setColWidth(4, 30);
        table_contents.setColWidth(5, 110);
        table_contents.setColWidth(6, 60);
        table_contents.setColWidth(7, 80);
        table_contents.setRowInsets(0, new Insets(1, 0, 0, 0));
        table_contents.setRowInsets(1, new Insets(0, 0, 0, 0));
        table_contents.setRowInsets(2, new Insets(0, 0, 0, 0));
        table_contents.setRowInsets(3, new Insets(1, 0, 0, 0));
        table_contents.setRowInsets(4, new Insets(1, 0, 0, 0));
        table_contents.setRowInsets(5, new Insets(1, 0, 0, 0));
        table_contents.setRowInsets(6, new Insets(1, 0, 0, 0));
        table_contents.setRowInsets(7, new Insets(1, 0, 0, 0));
        table_contents.setRowInsets(8, new Insets(1, 0, 0, 0));
        table_contents.setRowInsets(9, new Insets(1, 0, 0, 0));
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
        table_contents.setRowBorder(-1, 0);
        table_contents.setColBorder(0);
        table_contents.setRowBorderColor(Color.lightGray);
        table_contents.setRowBorder(266240);
        table_contents.setColFont(0, new Font("Arial", 1, 10));
        table_contents.setColFont(1, new Font("Arial", 1, 10));
        table_contents.setColFont(2, new Font("Arial", 0, 10));
        table_contents.setColFont(3, new Font("Arial", 1, 10));
        table_contents.setColFont(4, new Font("Arial", 1, 10));
        table_contents.setColFont(5, new Font("Arial", 0, 10));
        table_contents.setColFont(6, new Font("Arial", 1, 10));
        table_contents.setColFont(7, new Font("Arial", 0, 10));
        table_contents.setColAlignment(0, 33);
        table_contents.setColAlignment(1, 33);
        table_contents.setColAlignment(2, 33);
        table_contents.setColAlignment(4, 33);
        table_contents.setColAlignment(3, 36);
        table_contents.setColAlignment(5, 33);
        table_contents.setColAlignment(6, 33);
        table_contents.setColAlignment(7, 33);
        int nextRow = 0;
        table_contents.setRowBorderColor(nextRow, 0, Color.white);
        table_contents.setRowBorderColor(nextRow, 3, Color.white);
        table_contents.setRowBorderColor(nextRow, 4, Color.white);
        table_contents.setObject(nextRow, 0, "Prepared By:");
        table_contents.setSpan(nextRow, 1, new Dimension(2, 1));
        table_contents.setObject(nextRow, 1, pfm.getPreparedBy());
        table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
        table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
        table_contents.setObject(nextRow, 3, "  Phone:");
        table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
        table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
        table_contents.setObject(nextRow, 5, pfm.getPhone());
        table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
        table_contents.setRowHeight(nextRow, 17);
        nextRow++;
        table_contents.setRowBorderColor(nextRow, 0, Color.white);
        table_contents.setRowBorderColor(nextRow, 3, Color.white);
        table_contents.setRowBorderColor(nextRow, 4, Color.white);
        table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
        table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
        table_contents.setObject(nextRow, 0, "Email:");
        table_contents.setSpan(nextRow, 1, new Dimension(2, 1));
        table_contents.setObject(nextRow, 1, pfm.getEmail());
        table_contents.setObject(nextRow, 3, "  Fax:");
        table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
        table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
        table_contents.setObject(nextRow, 5, pfm.getFaxNumber());
        table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
        table_contents.setRowHeight(nextRow, 17);
        nextRow++;
        table_contents.setRowBorderColor(nextRow, 0, Color.white);
        table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
        table_contents.setObject(nextRow, 0, "Comments:");
        table_contents.setSpan(nextRow, 1, new Dimension(7, 4));
        table_contents.setObject(nextRow, 1, pfm.getComments());
        table_contents.setColLineWrap(1, true);
        table_contents.setRowAutoSize(true);
        table_contents.setRowHeight(nextRow, 23);
        table_contents.setAlignment(nextRow, 1, 17);
        nextRow++;
        table_contents.setRowHeight(nextRow, 5);
        table_contents.setRowBorderColor(nextRow, Color.white);
        nextRow++;
        table_contents.setRowHeight(nextRow, 5);
        table_contents.setRowBorderColor(nextRow, Color.white);
        nextRow++;
        table_contents.setRowHeight(nextRow, 5);
        table_contents.setRowBorderColor(nextRow, Color.black);
        nextRow++;
        String operatingCompanyText = "";
        if (selection.getOperCompany() != null && !selection.getOperCompany().equals("-1"))
          operatingCompanyText = selection.getOperCompany(); 
        String upc = "";
        if (selection != null && selection.getUpc() != null)
          upc = selection.getUpc(); 
        String musicLineText = "";
        if (pfm.getMusicLine() != null && pfm.getMusicLine().length() > 0)
          musicLineText = pfm.getMusicLine(); 
        musicLineText = reportPrintHelper(Cache.getMusicLines(), String.valueOf(musicLineText));
        String productNumber = String.valueOf(SelectionManager.getLookupObjectValue(selection.getPrefixID())) + selection.getSelectionNo();
        String configCodeText = "";
        if (selection.getConfigCode() != null && !selection.getConfigCode().equals("-1"))
          configCodeText = selection.getConfigCode(); 
        configCodeText = reportPrintHelper(Cache.getConfigCodes(), String.valueOf(configCodeText));
        String repertoireOwnerText = "";
        if (pfm.getRepertoireOwner() != null && pfm.getRepertoireOwner().length() > 0)
          repertoireOwnerText = pfm.getRepertoireOwner(); 
        repertoireOwnerText = reportPrintHelper(Cache.getRepertoireOwners(), String.valueOf(repertoireOwnerText));
        String modifierText = "";
        if (pfm.getModifier() != null && pfm.getModifier().length() > 0)
          modifierText = pfm.getModifier(); 
        modifierText = reportPrintHelper(Cache.getModifiers(), String.valueOf(modifierText));
        String repertoireClassText = "";
        if (pfm.getRepertoireClass() != null && pfm.getRepertoireClass().length() > 0)
          repertoireClassText = pfm.getRepertoireClass(); 
        repertoireClassText = reportPrintHelper(Cache.getRepertoireClasses(), String.valueOf(repertoireClassText));
        String soundScanGroupText = "";
        if (selection.getSoundScanGrp() != null)
          soundScanGroupText = selection.getSoundScanGrp(); 
        String returnScrapCodeText = "";
        if (pfm.getReturnCode() != null && pfm.getReturnCode().length() > 0)
          returnScrapCodeText = pfm.getReturnCode(); 
        returnScrapCodeText = reportPrintHelper(Cache.getReturnCodes(), String.valueOf(returnScrapCodeText));
        String titleText = "";
        if (selection.getTitle() != null)
          titleText = selection.getTitle(); 
        String exportCodeText = "";
        if (pfm.getExportFlag() != null && pfm.getExportFlag().length() > 0)
          exportCodeText = pfm.getExportFlag(); 
        exportCodeText = reportPrintHelper(Cache.getExportFlags(), String.valueOf(exportCodeText));
        String artistText = "";
        if (selection.getArtist() != null) {
          artistText = selection.getArtistLastName();
          if (!selection.getArtistLastName().equals("") && !selection.getArtistFirstName().equals(""))
            artistText = String.valueOf(artistText) + ", "; 
          artistText = String.valueOf(artistText) + selection.getArtistFirstName();
        } 
        String titleID = "";
        if (selection.getTitleID() != null)
          titleID = selection.getTitleID(); 
        String releaseDate = "";
        if (!selection.getIsDigital() && selection.getStreetDate() != null) {
          releaseDate = MilestoneHelper.getFormatedDate(selection.getStreetDate());
        } else if (selection.getIsDigital() && selection.getDigitalRlsDate() != null) {
          releaseDate = MilestoneHelper.getFormatedDate(selection.getDigitalRlsDate());
        } 
        String priceCodeText = "";
        if (selection.getPriceCode() != null)
          priceCodeText = selection.getPriceCode().getSellCode(); 
        String priceCodeDPCText = "";
        if (selection.getPriceCodeDPC() != null)
          priceCodeDPCText = selection.getPriceCodeDPC().getSellCode(); 
        String superLabelText = "";
        if (selection.getSuperLabel() != null)
          superLabelText = selection.getSuperLabel(); 
        String guaranteeCodeText = "";
        if (pfm.getGuaranteeCode() != null && pfm.getGuaranteeCode().length() > 0)
          guaranteeCodeText = pfm.getGuaranteeCode(); 
        guaranteeCodeText = reportPrintHelper(Cache.getGuaranteeCodes(), String.valueOf(guaranteeCodeText));
        String labelCodeText = "";
        if (selection.getSubLabel() != null)
          labelCodeText = selection.getSubLabel(); 
        String narmText = "";
        if (pfm.getNarmFlag() != null)
          narmText = pfm.getNarmFlag(); 
        narmText = reportPrintHelper(Cache.getNarmExtracts(), String.valueOf(narmText));
        String encryptionText = "";
        if (pfm.getEncryptionFlag() != null)
          encryptionText = pfm.getEncryptionFlag(); 
        encryptionText = reportPrintHelper(Cache.getEncryptionFlags(), String.valueOf(encryptionText));
        String musicTypeText = "";
        if (pfm.getMusicType() != null) {
          musicTypeText = SelectionManager.getLookupObjectValue(pfm.getMusicType());
          musicTypeText = reportPrintHelper(Cache.getMusicTypes(), String.valueOf(musicTypeText));
        } 
        String projectID = "";
        if (selection != null && selection.getProjectID() != null)
          projectID = selection.getProjectID(); 
        String poMergeCodeText = "";
        if (pfm.getPoMergeCode() != null && pfm.getPoMergeCode().length() > 0)
          poMergeCodeText = pfm.getPoMergeCode(); 
        poMergeCodeText = reportPrintHelper(Cache.getPoMergeCodes(), String.valueOf(poMergeCodeText));
        String loosePickExemptCodeText = "";
        if (pfm.getLoosePickExemptCode() != null && pfm.getLoosePickExemptCode().length() > 0)
          loosePickExemptCodeText = pfm.getLoosePickExemptCode(); 
        loosePickExemptCodeText = reportPrintHelper(Cache.getLoosePickExempt(), String.valueOf(loosePickExemptCodeText));
        String companyCodeText = "";
        if (pfm.getCompanyCode() != null && pfm.getCompanyCode().length() > 0)
          companyCodeText = pfm.getCompanyCode(); 
        companyCodeText = reportPrintHelper(Cache.getCompanyCodes(), String.valueOf(companyCodeText));
        String importRateCodeText = "";
        if (pfm.getImpRateCode() != null && pfm.getImpRateCode().length() > 0)
          importRateCodeText = pfm.getImpRateCode(); 
        importRateCodeText = reportPrintHelper(Cache.getImpRateCodes(), String.valueOf(importRateCodeText));
        String pricePointText = "";
        if (pfm.getPricePoint() != null && pfm.getPricePoint().length() > 0)
          pricePointText = pfm.getPricePoint(); 
        pricePointText = reportPrintHelper(Cache.getPricePoints(), String.valueOf(pricePointText));
        String unitsPerSetText = "";
        if (pfm.getUnitsPerSet() > 0)
          try {
            unitsPerSetText = Integer.toString(pfm.getUnitsPerSet());
          } catch (NumberFormatException e) {
            System.out.println("Error converting Units Per Set into integer.");
          }  
        String compilationCodeText = "";
        if (pfm.getCompilationCode() != null)
          compilationCodeText = pfm.getCompilationCode(); 
        compilationCodeText = reportPrintHelper(Cache.getCompilationCodes(), String.valueOf(compilationCodeText));
        String supplierText = "";
        if (pfm.getSupplier() != null && pfm.getSupplier().length() > 0)
          supplierText = pfm.getSupplier(); 
        supplierText = reportPrintHelper(Cache.getSuppliers(), String.valueOf(supplierText));
        String enteredByName = "";
        int enteredById = 0;
        try {
          if (pfm.getEnteredByName() != null && pfm.getEnteredByName().length() > 0)
            enteredById = Integer.parseInt(pfm.getEnteredByName()); 
        } catch (Exception exception) {}
        if (enteredById == 0) {
          User user = MilestoneSecurity.getUser(context);
          if (user != null)
            enteredById = user.getUserId(); 
        } 
        if (UserManager.getInstance().getUser(enteredById) != null)
          enteredByName = UserManager.getInstance().getUser(enteredById).getName(); 
        String importCodeText = "";
        if (pfm.getImportIndicator() != null && pfm.getImportIndicator().length() > 0) {
          importCodeText = pfm.getImportIndicator().trim();
          if (importCodeText.equals("D")) {
            importCodeText = "D:US Made";
          } else if (importCodeText.equals("I")) {
            importCodeText = "I:Import";
          } 
        } 
        String gridNum = "";
        if (selection != null)
          gridNum = selection.getGridNumber(); 
        String imprint = "";
        if (selection.getImprint() != null)
          imprint = selection.getImprint(); 
        String statusText = selection.getSelectionStatus().getName();
        String prefixNumber = "";
        prefixNumber = SelectionManager.getLookupObjectValue(selection.getPrefixID());
        if (!prefixNumber.equals(""))
          prefixNumber = String.valueOf(prefixNumber) + "-"; 
        String physicalPrefixCatNumber = String.valueOf(prefixNumber) + selection.getSelectionNo();
        if (selection.getIsDigital()) {
          table_contents.setRowBorderColor(nextRow, 0, Color.white);
          table_contents.setRowBorderColor(nextRow, 1, Color.white);
          table_contents.setRowBorderColor(nextRow, 3, Color.white);
          table_contents.setRowBorderColor(nextRow, 4, Color.white);
          table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
          table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
          table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
          table_contents.setObject(nextRow, 0, "Imprint");
          table_contents.setObject(nextRow, 2, imprint);
          table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
          table_contents.setAlignment(nextRow, 3, 1);
          table_contents.setObject(nextRow, 3, "  UPC:");
          table_contents.setObject(nextRow, 5, MilestoneHelper_2.getRMSReportFormat(upc, "UPC", selection.getIsDigital()));
          table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
          table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
          nextRow++;
          table_contents.setRowBorderColor(nextRow, 0, Color.white);
          table_contents.setRowBorderColor(nextRow, 1, Color.white);
          table_contents.setRowBorderColor(nextRow, 3, Color.white);
          table_contents.setRowBorderColor(nextRow, 4, Color.white);
          table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
          table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
          table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
          table_contents.setObject(nextRow, 0, "Operating Company:");
          table_contents.setObject(nextRow, 2, operatingCompanyText);
          table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
          table_contents.setAlignment(nextRow, 3, 1);
          table_contents.setObject(nextRow, 3, "  Sales Grouping Cd:");
          table_contents.setObject(nextRow, 5, MilestoneHelper_2.getRMSReportFormat(soundScanGroupText, "SSG", selection.getIsDigital()));
          table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
          table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
          nextRow++;
          table_contents.setRowBorderColor(nextRow, 0, Color.white);
          table_contents.setRowBorderColor(nextRow, 1, Color.white);
          table_contents.setRowBorderColor(nextRow, 3, Color.white);
          table_contents.setRowBorderColor(nextRow, 4, Color.white);
          table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
          table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
          table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
          table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
          table_contents.setObject(nextRow, 0, "Physical Prefix /\nLocal Prod #");
          table_contents.setObject(nextRow, 2, physicalPrefixCatNumber);
          table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
          table_contents.setAlignment(nextRow, 3, 1);
          table_contents.setObject(nextRow, 3, "  Music Line:");
          table_contents.setObject(nextRow, 5, musicLineText);
          table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
          nextRow++;
          table_contents.setRowBorderColor(nextRow, 0, Color.white);
          table_contents.setRowBorderColor(nextRow, 1, Color.white);
          table_contents.setRowBorderColor(nextRow, 3, Color.white);
          table_contents.setRowBorderColor(nextRow, 4, Color.white);
          table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
          table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
          table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
          table_contents.setObject(nextRow, 0, "Config Code:");
          table_contents.setObject(nextRow, 2, configCodeText);
          table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
          table_contents.setAlignment(nextRow, 3, 1);
          table_contents.setObject(nextRow, 3, "  Repertoire Owner:");
          table_contents.setObject(nextRow, 5, repertoireOwnerText);
          table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
          table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
          nextRow++;
          table_contents.setRowBorderColor(nextRow, 0, Color.white);
          table_contents.setRowBorderColor(nextRow, 1, Color.white);
          table_contents.setRowBorderColor(nextRow, 3, Color.white);
          table_contents.setRowBorderColor(nextRow, 4, Color.white);
          table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
          table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
          table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
          table_contents.setObject(nextRow, 0, "Title:");
          table_contents.setObject(nextRow, 2, titleText);
          table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
          table_contents.setAlignment(nextRow, 3, 1);
          table_contents.setObject(nextRow, 3, "  Repertoire Class:");
          table_contents.setObject(nextRow, 5, repertoireClassText);
          table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
          table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
          nextRow++;
          table_contents.setRowBorderColor(nextRow, 0, Color.white);
          table_contents.setRowBorderColor(nextRow, 1, Color.white);
          table_contents.setRowBorderColor(nextRow, 3, Color.white);
          table_contents.setRowBorderColor(nextRow, 4, Color.white);
          table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
          table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
          table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
          table_contents.setObject(nextRow, 0, "Artist:");
          table_contents.setObject(nextRow, 2, artistText);
          table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
          table_contents.setAlignment(nextRow, 3, 1);
          table_contents.setObject(nextRow, 3, "  Return/Scrap Code:");
          table_contents.setObject(nextRow, 5, returnScrapCodeText);
          table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
          table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
          nextRow++;
          table_contents.setRowBorderColor(nextRow, 0, Color.white);
          table_contents.setRowBorderColor(nextRow, 1, Color.white);
          table_contents.setRowBorderColor(nextRow, 3, Color.white);
          table_contents.setRowBorderColor(nextRow, 4, Color.white);
          table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
          table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
          table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
          table_contents.setObject(nextRow, 0, "Digital Release Date:");
          table_contents.setObject(nextRow, 2, releaseDate);
          table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
          table_contents.setAlignment(nextRow, 3, 1);
          table_contents.setRowLineWrap(nextRow, false);
          table_contents.setObject(nextRow, 3, "  Loose Pick Exempt:");
          table_contents.setObject(nextRow, 5, loosePickExemptCodeText);
          table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
          table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
          nextRow++;
          table_contents.setRowBorderColor(nextRow, 0, Color.white);
          table_contents.setRowBorderColor(nextRow, 1, Color.white);
          table_contents.setRowBorderColor(nextRow, 3, Color.white);
          table_contents.setRowBorderColor(nextRow, 4, Color.white);
          table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
          table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
          table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
          table_contents.setObject(nextRow, 0, "Status:");
          table_contents.setObject(nextRow, 2, statusText);
          table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
          table_contents.setAlignment(nextRow, 3, 1);
          table_contents.setObject(nextRow, 3, "  IMI Exempt Code:");
          table_contents.setObject(nextRow, 5, guaranteeCodeText);
          table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
          table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
          nextRow++;
          table_contents.setRowBorderColor(nextRow, 0, Color.white);
          table_contents.setRowBorderColor(nextRow, 1, Color.white);
          table_contents.setRowBorderColor(nextRow, 3, Color.white);
          table_contents.setRowBorderColor(nextRow, 4, Color.white);
          table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
          table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
          table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
          table_contents.setObject(nextRow, 0, "Super Label:");
          table_contents.setObject(nextRow, 2, superLabelText);
          table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
          table_contents.setAlignment(nextRow, 3, 1);
          table_contents.setObject(nextRow, 3, "  Music Type:");
          table_contents.setObject(nextRow, 5, musicTypeText);
          table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
          table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
          nextRow++;
          table_contents.setRowBorderColor(nextRow, 0, Color.white);
          table_contents.setRowBorderColor(nextRow, 1, Color.white);
          table_contents.setRowBorderColor(nextRow, 3, Color.white);
          table_contents.setRowBorderColor(nextRow, 4, Color.white);
          table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
          table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
          table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
          table_contents.setObject(nextRow, 0, "Sub Label:");
          table_contents.setObject(nextRow, 2, labelCodeText);
          table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
          table_contents.setAlignment(nextRow, 3, 1);
          table_contents.setObject(nextRow, 3, "  Price Point:");
          table_contents.setObject(nextRow, 5, pricePointText);
          table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
          table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
          nextRow++;
          table_contents.setRowBorderColor(nextRow, 0, Color.white);
          table_contents.setRowBorderColor(nextRow, 1, Color.white);
          table_contents.setRowBorderColor(nextRow, 3, Color.white);
          table_contents.setRowBorderColor(nextRow, 4, Color.white);
          table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
          table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
          table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
          table_contents.setObject(nextRow, 0, "Company Code:");
          table_contents.setObject(nextRow, 2, companyCodeText);
          table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
          table_contents.setAlignment(nextRow, 3, 1);
          table_contents.setObject(nextRow, 3, "  Narm Extract Ind:");
          table_contents.setObject(nextRow, 5, narmText);
          table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
          table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
          nextRow++;
          table_contents.setRowBorderColor(nextRow, 0, Color.white);
          table_contents.setRowBorderColor(nextRow, 1, Color.white);
          table_contents.setRowBorderColor(nextRow, 3, Color.white);
          table_contents.setRowBorderColor(nextRow, 4, Color.white);
          table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
          table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
          table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
          table_contents.setObject(nextRow, 0, "Project ID (9 Digits):");
          table_contents.setObject(nextRow, 2, projectID);
          table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
          table_contents.setAlignment(nextRow, 3, 1);
          table_contents.setObject(nextRow, 3, "  Compilation \n  Soundtrack:");
          table_contents.setObject(nextRow, 5, compilationCodeText);
          table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
          table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
          nextRow++;
          table_contents.setRowBorderColor(nextRow, 0, Color.white);
          table_contents.setRowBorderColor(nextRow, 1, Color.white);
          table_contents.setRowBorderColor(nextRow, 3, Color.white);
          table_contents.setRowBorderColor(nextRow, 4, Color.white);
          table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
          table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
          table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
          table_contents.setObject(nextRow, 0, "PO Merge Code:");
          table_contents.setObject(nextRow, 2, poMergeCodeText);
          table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
          table_contents.setAlignment(nextRow, 3, 1);
          table_contents.setObject(nextRow, 3, "  Parental Adv:");
          table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
          table_contents.setObject(nextRow, 5, selection.getParentalGuidance() ? "Yes" : "");
          nextRow++;
          table_contents.setRowBorderColor(nextRow, 0, Color.white);
          table_contents.setRowBorderColor(nextRow, 1, Color.white);
          table_contents.setRowBorderColor(nextRow, 3, Color.white);
          table_contents.setRowBorderColor(nextRow, 4, Color.white);
          table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
          table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
          table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
          table_contents.setObject(nextRow, 0, "# of Units:");
          table_contents.setObject(nextRow, 2, unitsPerSetText);
          table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
          table_contents.setAlignment(nextRow, 3, 1);
          table_contents.setObject(nextRow, 3, "  Dig. Price Code:");
          table_contents.setObject(nextRow, 5, priceCodeText);
          table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
          table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
          nextRow++;
          table_contents.setRowBorderColor(nextRow, 0, Color.white);
          table_contents.setRowBorderColor(nextRow, 1, Color.white);
          table_contents.setRowBorderColor(nextRow, 3, Color.white);
          table_contents.setRowBorderColor(nextRow, 4, Color.white);
          table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
          table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
          table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
          table_contents.setObject(nextRow, 0, "Sets Per Carton:");
          table_contents.setObject(nextRow, 2, String.valueOf(pfm.getSetsPerCarton()));
          table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
          table_contents.setAlignment(nextRow, 3, 1);
          table_contents.setObject(nextRow, 3, "  Entered By:");
          table_contents.setObject(nextRow, 5, enteredByName);
          table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
          table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
          nextRow++;
          table_contents.setRowBorderColor(nextRow, 0, Color.white);
          table_contents.setRowBorderColor(nextRow, 1, Color.white);
          table_contents.setRowBorderColor(nextRow, 3, Color.white);
          table_contents.setRowBorderColor(nextRow, 4, Color.white);
          table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
          table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
          table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
          table_contents.setObject(nextRow, 0, "Import Indicator:");
          table_contents.setObject(nextRow, 2, importCodeText);
          table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
          table_contents.setAlignment(nextRow, 3, 1);
          table_contents.setObject(nextRow, 3, "  Date:");
          table_contents.setObject(nextRow, 5, pfm.getEnteredDate());
          table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
          table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
          nextRow++;
          table_contents.setRowBorderColor(nextRow, 0, Color.white);
          table_contents.setRowBorderColor(nextRow, 1, Color.white);
          table_contents.setRowBorderColor(nextRow, 3, Color.white);
          table_contents.setRowBorderColor(nextRow, 4, Color.white);
          table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
          table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
          table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
          table_contents.setObject(nextRow, 0, "GRid #:");
          table_contents.setObject(nextRow, 2, gridNum);
          table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
          table_contents.setAlignment(nextRow, 3, 1);
          table_contents.setObject(nextRow, 3, "  Approved By:");
          table_contents.setObject(nextRow, 5, pfm.getApprovedByName());
          table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
          table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
          nextRow++;
          table_contents.setRowBorderColor(nextRow, 0, Color.white);
          table_contents.setRowBorderColor(nextRow, 1, Color.white);
          table_contents.setRowBorderColor(nextRow, 2, Color.white);
          table_contents.setRowBorderColor(nextRow, 3, Color.white);
          table_contents.setRowBorderColor(nextRow, 4, Color.white);
          table_contents.setRowBorderColor(nextRow, 5, Color.white);
          table_contents.setRowBorderColor(nextRow, 6, Color.white);
          table_contents.setRowBorderColor(nextRow, 7, Color.white);
          nextRow++;
          table_contents.setRowBorderColor(nextRow, Color.white);
        } else {
          table_contents.setRowBorderColor(nextRow, 0, Color.white);
          table_contents.setRowBorderColor(nextRow, 1, Color.white);
          table_contents.setRowBorderColor(nextRow, 3, Color.white);
          table_contents.setRowBorderColor(nextRow, 4, Color.white);
          table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
          table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
          table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
          table_contents.setObject(nextRow, 0, "Operating Company:");
          table_contents.setObject(nextRow, 2, operatingCompanyText);
          table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
          table_contents.setAlignment(nextRow, 3, 1);
          table_contents.setObject(nextRow, 3, "  Repertoire Owner:");
          table_contents.setObject(nextRow, 5, repertoireOwnerText);
          table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
          table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
          nextRow++;
          table_contents.setRowBorderColor(nextRow, 0, Color.white);
          table_contents.setRowBorderColor(nextRow, 1, Color.white);
          table_contents.setRowBorderColor(nextRow, 3, Color.white);
          table_contents.setRowBorderColor(nextRow, 4, Color.white);
          table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
          table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
          table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
          table_contents.setObject(nextRow, 0, "Local Prod #:");
          table_contents.setObject(nextRow, 2, productNumber);
          table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
          table_contents.setAlignment(nextRow, 3, 1);
          table_contents.setObject(nextRow, 3, "  Repertoire Class:");
          table_contents.setObject(nextRow, 5, repertoireClassText);
          table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
          table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
          nextRow++;
          table_contents.setRowBorderColor(nextRow, 0, Color.white);
          table_contents.setRowBorderColor(nextRow, 1, Color.white);
          table_contents.setRowBorderColor(nextRow, 3, Color.white);
          table_contents.setRowBorderColor(nextRow, 4, Color.white);
          table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
          table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
          table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
          table_contents.setObject(nextRow, 0, "Config Code:");
          table_contents.setObject(nextRow, 2, configCodeText);
          table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
          table_contents.setAlignment(nextRow, 3, 1);
          table_contents.setObject(nextRow, 3, "  Return/Scrap Code:");
          table_contents.setObject(nextRow, 5, returnScrapCodeText);
          table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
          table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
          nextRow++;
          table_contents.setRowBorderColor(nextRow, 0, Color.white);
          table_contents.setRowBorderColor(nextRow, 1, Color.white);
          table_contents.setRowBorderColor(nextRow, 3, Color.white);
          table_contents.setRowBorderColor(nextRow, 4, Color.white);
          table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
          table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
          table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
          table_contents.setAlignment(nextRow, 3, 1);
          table_contents.setObject(nextRow, 3, "  Export Indicator:");
          table_contents.setObject(nextRow, 5, exportCodeText);
          table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
          table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
          nextRow++;
          table_contents.setRowBorderColor(nextRow, 0, Color.white);
          table_contents.setRowBorderColor(nextRow, 1, Color.white);
          table_contents.setRowBorderColor(nextRow, 3, Color.white);
          table_contents.setRowBorderColor(nextRow, 4, Color.white);
          table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
          table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
          table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
          table_contents.setObject(nextRow, 0, "Title:");
          table_contents.setObject(nextRow, 2, titleText);
          table_contents.setSpan(nextRow, 3, new Dimension(3, 1));
          table_contents.setAlignment(nextRow, 3, 1);
          table_contents.setObject(nextRow, 3, "  Included or Excluded Countries:");
          table_contents.setObject(nextRow, 6, pfm.getCountries());
          table_contents.setSpan(nextRow, 6, new Dimension(2, 1));
          table_contents.setFont(nextRow, 6, new Font("Arial", 0, 9));
          nextRow++;
          table_contents.setRowBorderColor(nextRow, 0, Color.white);
          table_contents.setRowBorderColor(nextRow, 1, Color.white);
          table_contents.setRowBorderColor(nextRow, 3, Color.white);
          table_contents.setRowBorderColor(nextRow, 4, Color.white);
          table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
          table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
          table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
          table_contents.setObject(nextRow, 0, "Artist:");
          table_contents.setObject(nextRow, 2, artistText);
          table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
          table_contents.setAlignment(nextRow, 3, 1);
          table_contents.setObject(nextRow, 3, "  Spine Title:");
          String spineTitle = pfm.getSpineTitle();
          if (spineTitle.length() > 37)
            spineTitle = spineTitle.substring(0, 37); 
          table_contents.setObject(nextRow, 5, spineTitle);
          table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
          table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
          nextRow++;
          table_contents.setRowBorderColor(nextRow, 0, Color.white);
          table_contents.setRowBorderColor(nextRow, 1, Color.white);
          table_contents.setRowBorderColor(nextRow, 3, Color.white);
          table_contents.setRowBorderColor(nextRow, 4, Color.white);
          table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
          table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
          table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
          table_contents.setObject(nextRow, 0, "Title ID (10 Char):");
          table_contents.setObject(nextRow, 2, titleID);
          table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
          table_contents.setAlignment(nextRow, 3, 1);
          table_contents.setObject(nextRow, 3, "  Spine Artist:");
          String spineArtist = pfm.getSpineArtist();
          if (spineArtist.length() > 37)
            spineArtist = spineArtist.substring(0, 37); 
          table_contents.setObject(nextRow, 5, spineArtist);
          table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
          table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
          nextRow++;
          table_contents.setRowBorderColor(nextRow, 0, Color.white);
          table_contents.setRowBorderColor(nextRow, 1, Color.white);
          table_contents.setRowBorderColor(nextRow, 3, Color.white);
          table_contents.setRowBorderColor(nextRow, 4, Color.white);
          table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
          table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
          table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
          table_contents.setObject(nextRow, 0, "Planned Release Date:");
          table_contents.setObject(nextRow, 2, releaseDate);
          table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
          table_contents.setAlignment(nextRow, 3, 1);
          table_contents.setRowLineWrap(nextRow, false);
          table_contents.setObject(nextRow, 3, "  Loose Pick Exempt:");
          table_contents.setObject(nextRow, 5, loosePickExemptCodeText);
          table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
          table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
          nextRow++;
          table_contents.setRowBorderColor(nextRow, 0, Color.white);
          table_contents.setRowBorderColor(nextRow, 1, Color.white);
          table_contents.setRowBorderColor(nextRow, 3, Color.white);
          table_contents.setRowBorderColor(nextRow, 4, Color.white);
          table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
          table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
          table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
          table_contents.setObject(nextRow, 0, "Status:");
          table_contents.setObject(nextRow, 2, statusText);
          table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
          table_contents.setAlignment(nextRow, 3, 1);
          table_contents.setObject(nextRow, 3, "  IMI Exempt Code:");
          table_contents.setObject(nextRow, 5, guaranteeCodeText);
          table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
          table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
          nextRow++;
          table_contents.setRowBorderColor(nextRow, 0, Color.white);
          table_contents.setRowBorderColor(nextRow, 1, Color.white);
          table_contents.setRowBorderColor(nextRow, 3, Color.white);
          table_contents.setRowBorderColor(nextRow, 4, Color.white);
          table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
          table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
          table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
          table_contents.setObject(nextRow, 0, "Super Label:");
          table_contents.setObject(nextRow, 2, superLabelText);
          table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
          table_contents.setAlignment(nextRow, 3, 1);
          table_contents.setObject(nextRow, 3, "  Music Type:");
          table_contents.setObject(nextRow, 5, musicTypeText);
          table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
          table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
          nextRow++;
          table_contents.setRowBorderColor(nextRow, 0, Color.white);
          table_contents.setRowBorderColor(nextRow, 1, Color.white);
          table_contents.setRowBorderColor(nextRow, 3, Color.white);
          table_contents.setRowBorderColor(nextRow, 4, Color.white);
          table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
          table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
          table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
          table_contents.setObject(nextRow, 0, "Sub Label:");
          table_contents.setObject(nextRow, 2, labelCodeText);
          table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
          table_contents.setAlignment(nextRow, 3, 1);
          table_contents.setObject(nextRow, 3, "  IMI/IMP Rate Code:");
          table_contents.setObject(nextRow, 5, importRateCodeText);
          table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
          table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
          nextRow++;
          table_contents.setRowBorderColor(nextRow, 0, Color.white);
          table_contents.setRowBorderColor(nextRow, 1, Color.white);
          table_contents.setRowBorderColor(nextRow, 3, Color.white);
          table_contents.setRowBorderColor(nextRow, 4, Color.white);
          table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
          table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
          table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
          table_contents.setObject(nextRow, 0, "Company Code:");
          table_contents.setObject(nextRow, 2, companyCodeText);
          table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
          table_contents.setAlignment(nextRow, 3, 1);
          table_contents.setObject(nextRow, 3, "  Price Point:");
          table_contents.setObject(nextRow, 5, pricePointText);
          table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
          table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
          nextRow++;
          table_contents.setRowBorderColor(nextRow, 0, Color.white);
          table_contents.setRowBorderColor(nextRow, 1, Color.white);
          table_contents.setRowBorderColor(nextRow, 3, Color.white);
          table_contents.setRowBorderColor(nextRow, 4, Color.white);
          table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
          table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
          table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
          table_contents.setObject(nextRow, 0, "Project ID (9 Digits):");
          table_contents.setObject(nextRow, 2, projectID);
          table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
          table_contents.setAlignment(nextRow, 3, 1);
          table_contents.setObject(nextRow, 3, "  Narm Extract Ind:");
          table_contents.setObject(nextRow, 5, narmText);
          table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
          table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
          nextRow++;
          table_contents.setRowBorderColor(nextRow, 0, Color.white);
          table_contents.setRowBorderColor(nextRow, 1, Color.white);
          table_contents.setRowBorderColor(nextRow, 3, Color.white);
          table_contents.setRowBorderColor(nextRow, 4, Color.white);
          table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
          table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
          table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
          table_contents.setObject(nextRow, 0, "PO Merge Code:");
          table_contents.setObject(nextRow, 2, poMergeCodeText);
          table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
          table_contents.setAlignment(nextRow, 3, 1);
          table_contents.setObject(nextRow, 3, "  Compilation \n  Soundtrack:");
          table_contents.setObject(nextRow, 5, compilationCodeText);
          table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
          table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
          nextRow++;
          table_contents.setRowBorderColor(nextRow, 0, Color.white);
          table_contents.setRowBorderColor(nextRow, 1, Color.white);
          table_contents.setRowBorderColor(nextRow, 3, Color.white);
          table_contents.setRowBorderColor(nextRow, 4, Color.white);
          table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
          table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
          table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
          table_contents.setObject(nextRow, 0, "# of Units:");
          table_contents.setObject(nextRow, 2, unitsPerSetText);
          table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
          table_contents.setAlignment(nextRow, 3, 1);
          table_contents.setObject(nextRow, 3, "  Parental Adv:");
          table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
          table_contents.setObject(nextRow, 5, selection.getParentalGuidance() ? "Yes" : "");
          nextRow++;
          table_contents.setRowBorderColor(nextRow, 0, Color.white);
          table_contents.setRowBorderColor(nextRow, 1, Color.white);
          table_contents.setRowBorderColor(nextRow, 3, Color.white);
          table_contents.setRowBorderColor(nextRow, 4, Color.white);
          table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
          table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
          table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
          table_contents.setObject(nextRow, 0, "Sets Per Carton:");
          table_contents.setObject(nextRow, 2, String.valueOf(pfm.getSetsPerCarton()));
          table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
          table_contents.setAlignment(nextRow, 3, 1);
          table_contents.setObject(nextRow, 3, "  Box Set:");
          table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
          table_contents.setObject(nextRow, 5, pfm.getBoxSet() ? "Yes" : "");
          nextRow++;
          table_contents.setRowBorderColor(nextRow, 0, Color.white);
          table_contents.setRowBorderColor(nextRow, 1, Color.white);
          table_contents.setRowBorderColor(nextRow, 3, Color.white);
          table_contents.setRowBorderColor(nextRow, 4, Color.white);
          table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
          table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
          table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
          table_contents.setObject(nextRow, 0, "Supplier:");
          table_contents.setObject(nextRow, 2, supplierText);
          table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
          table_contents.setAlignment(nextRow, 3, 1);
          table_contents.setObject(nextRow, 3, "  Value Added:");
          table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
          table_contents.setObject(nextRow, 5, pfm.getValueAdded() ? "Yes" : "");
          nextRow++;
          table_contents.setRowBorderColor(nextRow, 0, Color.white);
          table_contents.setRowBorderColor(nextRow, 1, Color.white);
          table_contents.setRowBorderColor(nextRow, 3, Color.white);
          table_contents.setRowBorderColor(nextRow, 4, Color.white);
          table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
          table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
          table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
          table_contents.setObject(nextRow, 0, "Import Indicator:");
          table_contents.setObject(nextRow, 2, importCodeText);
          table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
          table_contents.setAlignment(nextRow, 3, 1);
          table_contents.setObject(nextRow, 3, "  Encryption:");
          table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
          table_contents.setObject(nextRow, 5, encryptionText);
          table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
          nextRow++;
          table_contents.setRowBorderColor(nextRow, 0, Color.white);
          table_contents.setRowBorderColor(nextRow, 1, Color.white);
          table_contents.setRowBorderColor(nextRow, 3, Color.white);
          table_contents.setRowBorderColor(nextRow, 4, Color.white);
          table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
          table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
          table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
          table_contents.setObject(nextRow, 0, "UPC:");
          table_contents.setObject(nextRow, 2, MilestoneHelper_2.getRMSReportFormat(upc, "UPC", selection.getIsDigital()));
          table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
          table_contents.setAlignment(nextRow, 3, 1);
          table_contents.setObject(nextRow, 3, "  Price Code:");
          table_contents.setObject(nextRow, 5, priceCodeText);
          table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
          table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
          nextRow++;
          table_contents.setRowBorderColor(nextRow, 0, Color.white);
          table_contents.setRowBorderColor(nextRow, 1, Color.white);
          table_contents.setRowBorderColor(nextRow, 3, Color.white);
          table_contents.setRowBorderColor(nextRow, 4, Color.white);
          table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
          table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
          table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
          table_contents.setObject(nextRow, 0, "SoundScan Grp:");
          table_contents.setObject(nextRow, 2, MilestoneHelper_2.getRMSReportFormat(soundScanGroupText, "SSG", selection.getIsDigital()));
          table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
          table_contents.setAlignment(nextRow, 3, 1);
          table_contents.setObject(nextRow, 3, "  Dig. Price Code:");
          table_contents.setObject(nextRow, 5, priceCodeDPCText);
          table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
          table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
          nextRow++;
          table_contents.setRowBorderColor(nextRow, 0, Color.white);
          table_contents.setRowBorderColor(nextRow, 1, Color.white);
          table_contents.setRowBorderColor(nextRow, 3, Color.white);
          table_contents.setRowBorderColor(nextRow, 4, Color.white);
          table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
          table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
          table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
          table_contents.setObject(nextRow, 0, "Music Line:");
          table_contents.setObject(nextRow, 2, musicLineText);
          table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
          table_contents.setAlignment(nextRow, 3, 1);
          table_contents.setObject(nextRow, 3, "  GRid #:");
          table_contents.setObject(nextRow, 5, gridNum);
          table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
          table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
          nextRow++;
          table_contents.setRowBorderColor(nextRow, 0, Color.white);
          table_contents.setRowBorderColor(nextRow, 1, Color.white);
          table_contents.setRowBorderColor(nextRow, 3, Color.white);
          table_contents.setRowBorderColor(nextRow, 4, Color.white);
          table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
          table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
          table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
          table_contents.setObject(nextRow, 0, "Entered By:");
          table_contents.setObject(nextRow, 2, enteredByName);
          table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
          table_contents.setAlignment(nextRow, 3, 1);
          table_contents.setObject(nextRow, 3, "  Approved By:");
          table_contents.setObject(nextRow, 5, pfm.getApprovedByName());
          table_contents.setFont(nextRow, 6, new Font("Arial", 0, 9));
          table_contents.setObject(nextRow, 6, "  Date:");
          table_contents.setRowBorderColor(nextRow, 6, Color.white);
          table_contents.setObject(nextRow, 7, pfm.getEnteredDate());
          table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
          table_contents.setFont(nextRow, 7, new Font("Arial", 0, 9));
          nextRow++;
          nextRow++;
          table_contents.setRowBorderColor(nextRow, Color.white);
        } 
        report.setElement("table_colheaders", table_contents);
        String reportFilename = "report.pdf";
        if (pdfRtf == 0) {
          HttpServletResponse sresponse = context.getResponse();
          if (ie5) {
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
          EmailDistribution.generateFormReport(context, "PFM", report, 
              selection.getSelectionNo(), prefix, selection.getUpc(), selection.getIsDigital(), messageObject);
        } 
        return true;
      } catch (Exception e) {
        e.printStackTrace();
      }  
    return edit(dispatcher, context, command);
  }
  
  private static String reportPrintHelper(Vector menuVector, String selectedOption) {
    String result = "";
    if (selectedOption == null)
      selectedOption = ""; 
    if (menuVector != null)
      for (int i = 0; i < menuVector.size(); i++) {
        LookupObject lookupObject = (LookupObject)menuVector.elementAt(i);
        if (selectedOption.equalsIgnoreCase(lookupObject.getAbbreviation())) {
          String temporaryHold = lookupObject.getName();
          temporaryHold = temporaryHold.replace(',', ' ');
          result = String.valueOf(lookupObject.getAbbreviation()) + ":" + temporaryHold;
          break;
        } 
      }  
    return result;
  }
  
  public static int getSelectionPfmPermissions(Selection selection, User user) {
    int level = 0;
    if (selection != null && selection.getSelectionID() > -1) {
      Environment env = selection.getEnvironment();
      CompanyAcl companyAcl = MilestoneHelper.getScreenPermissions(env, user);
      if (companyAcl != null && companyAcl.getAccessPfmForm() > level)
        level = companyAcl.getAccessPfmForm(); 
    } 
    return level;
  }
  
  public void setButtonVisibilities(Selection selection, User user, Context context, int level, String command) {
    String saveVisible = "false";
    if (level > 1)
      saveVisible = "true"; 
    context.removeSessionValue("saveVisible");
    context.putDelivery("saveVisible", saveVisible);
  }
  
  private boolean editCopy(Dispatcher dispatcher, Context context, String command) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 0);
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Selection selection = (Selection)context.getSessionValue("Selection");
    int selectionID = selection.getSelectionID();
    Pfm targetPfm = SelectionManager.getInstance().getPfm(selectionID);
    Pfm copiedPfm = null;
    if (targetPfm != null) {
      try {
        copiedPfm = (Pfm)targetPfm.clone();
      } catch (CloneNotSupportedException cloneNotSupportedException) {}
      Form form = buildForm(context, copiedPfm, command);
      form.addElement(new FormHidden("cmd", "pfm-edit-copy", true));
      String projectIDtoValidate = "";
      if (copiedPfm.getProjectID().trim().indexOf("-") > -1) {
        for (int j = 1; j < copiedPfm.getProjectID().trim().length(); j++) {
          if (copiedPfm.getProjectID().trim().charAt(j) != '-')
            projectIDtoValidate = String.valueOf(projectIDtoValidate) + copiedPfm.getProjectID().trim().charAt(j); 
        } 
      } else {
        projectIDtoValidate = copiedPfm.getProjectID().trim();
      } 
      if (!ProjectSearchManager.getInstance().isProjectNumberValid(projectIDtoValidate))
        context.putDelivery("AlertMessage", "The Project Number on this product is invalid, please set up a new product."); 
      context.putSessionValue("copiedPfm", copiedPfm);
      context.putSessionValue("Pfm", targetPfm);
      context.putDelivery("Form", form);
    } 
    if (context.getSessionValue("NOTEPAD_PFM_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_PFM_VISIBLE")); 
    return edit(dispatcher, context, command);
  }
  
  private boolean pasteCopy(Dispatcher dispatcher, Context context, String command) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 0);
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Selection selection = (Selection)context.getSessionValue("Selection");
    int selectionID = selection.getSelectionID();
    Pfm copiedPfm = (Pfm)context.getSessionValue("copiedPfm");
    String productNumber = String.valueOf(SelectionManager.getLookupObjectValue(selection.getPrefixID())) + selection.getSelectionNo();
    String upc = "";
    if (selection.getUpc() != null)
      upc = selection.getUpc(); 
    String title = "";
    if (selection.getTitle() != null)
      title = selection.getTitle(); 
    String artist = "";
    if (selection.getArtist() != null) {
      artist = selection.getArtistLastName();
      if (!selection.getArtistLastName().equals("") && !selection.getArtistFirstName().equals(""))
        artist = String.valueOf(artist) + ", "; 
      artist = String.valueOf(artist) + selection.getArtistFirstName();
    } 
    String titleID = "";
    if (selection.getTitleID() != null)
      titleID = selection.getTitleID(); 
    String spineTitleText = "";
    if (selection != null)
      if (selection.getTitle() != null)
        spineTitleText = (selection.getTitle().length() > 39) ? selection.getTitle().substring(0, 38) : selection.getTitle();  
    String spineArtistText = "";
    if (selection != null)
      if (selection.getArtist() != null)
        spineArtistText = (selection.getFlArtist().length() > 39) ? selection.getFlArtist().substring(0, 38) : selection.getFlArtist();  
    String priceCode = "";
    if (selection.getSellCode() != null && !selection.getSellCode().equals("-1"))
      priceCode = selection.getSellCode(); 
    String priceCodeDPC = "";
    if (selection.getSellCodeDPC() != null && !selection.getSellCodeDPC().equals("-1"))
      priceCodeDPC = selection.getSellCodeDPC(); 
    copiedPfm.setUpc(upc);
    copiedPfm.setProductNumber(productNumber);
    copiedPfm.setTitle(title);
    copiedPfm.setArtist(artist);
    copiedPfm.setTitleId(titleID);
    copiedPfm.setSpineTitle(spineTitleText);
    copiedPfm.setSpineArtist(spineArtistText.trim());
    copiedPfm.setPriceCode(priceCode);
    copiedPfm.setPriceCodeDPC(priceCodeDPC);
    copiedPfm.setStreetDate(selection.getDigitalRlsDate());
    Pfm pfm = SelectionManager.getInstance().getPfm(selectionID);
    copiedPfm.setPrintOption("Draft");
    copiedPfm.setComments("");
    copiedPfm.setMode("Add");
    User currentUser = (User)context.getSessionValue("user");
    copiedPfm.setPreparedBy(currentUser.getName());
    copiedPfm.setPhone(currentUser.getPhone());
    copiedPfm.setEmail(currentUser.getEmail());
    copiedPfm.setFaxNumber(currentUser.getFax());
    copiedPfm.setLastUpdatedDate(null);
    copiedPfm.setLastUpdatingUser(99999);
    copiedPfm.setModifier("-1");
    int releaseId = -1;
    long timestamp = -1L;
    int lastUser = -1;
    Form form = buildForm(context, copiedPfm, command);
    form.addElement(new FormHidden("cmd", "pfm-paste-copy", true));
    form.addElement(new FormHidden("copyPaste", "copy", true));
    context.putSessionValue("Pfm", pfm);
    context.putDelivery("Form", form);
    context.removeSessionValue("copiedPfm");
    if (context.getSessionValue("NOTEPAD_PFM_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_PFM_VISIBLE")); 
    if (selection.getIsDigital())
      return context.includeJSP("pfm-editor-digital.jsp"); 
    return context.includeJSP("pfm-editor.jsp");
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
    dispatcher.redispatch(context, "pfm-editor");
    return true;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\PfmHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */