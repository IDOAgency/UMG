/*      */ package WEB-INF.classes.com.universal.milestone;
/*      */ 
/*      */ import com.techempower.ComponentLog;
/*      */ import com.techempower.gemini.Context;
/*      */ import com.techempower.gemini.Dispatcher;
/*      */ import com.techempower.gemini.FormCheckBox;
/*      */ import com.techempower.gemini.FormDateField;
/*      */ import com.techempower.gemini.FormDropDownMenu;
/*      */ import com.techempower.gemini.FormHidden;
/*      */ import com.techempower.gemini.FormRadioButtonGroup;
/*      */ import com.techempower.gemini.FormTextArea;
/*      */ import com.techempower.gemini.FormTextField;
/*      */ import com.techempower.gemini.FormValidation;
/*      */ import com.techempower.gemini.GeminiApplication;
/*      */ import com.techempower.gemini.Handler;
/*      */ import com.universal.milestone.Bom;
/*      */ import com.universal.milestone.BomCassetteDetail;
/*      */ import com.universal.milestone.BomDVDDetail;
/*      */ import com.universal.milestone.BomDiskDetail;
/*      */ import com.universal.milestone.BomHandler;
/*      */ import com.universal.milestone.BomVinylDetail;
/*      */ import com.universal.milestone.Cache;
/*      */ import com.universal.milestone.Company;
/*      */ import com.universal.milestone.CompanyAcl;
/*      */ import com.universal.milestone.EmailDistribution;
/*      */ import com.universal.milestone.Environment;
/*      */ import com.universal.milestone.Form;
/*      */ import com.universal.milestone.Label;
/*      */ import com.universal.milestone.LookupObject;
/*      */ import com.universal.milestone.MilestoneConstants;
/*      */ import com.universal.milestone.MilestoneHelper;
/*      */ import com.universal.milestone.MilestoneHelper_2;
/*      */ import com.universal.milestone.MilestoneInfrastructure;
/*      */ import com.universal.milestone.MilestoneSecurity;
/*      */ import com.universal.milestone.Notepad;
/*      */ import com.universal.milestone.NotepadSortOrder;
/*      */ import com.universal.milestone.ReleasingFamily;
/*      */ import com.universal.milestone.ReportHandler;
/*      */ import com.universal.milestone.SecureHandler;
/*      */ import com.universal.milestone.Selection;
/*      */ import com.universal.milestone.SelectionHandler;
/*      */ import com.universal.milestone.SelectionManager;
/*      */ import com.universal.milestone.User;
/*      */ import com.universal.milestone.UserManager;
/*      */ import com.universal.milestone.jms.MessageObject;
/*      */ import inetsoft.report.TextBoxElement;
/*      */ import inetsoft.report.XStyleSheet;
/*      */ import inetsoft.report.io.Builder;
/*      */ import inetsoft.report.lens.DefaultTableLens;
/*      */ import inetsoft.report.pdf.PDF4Generator;
/*      */ import java.awt.Color;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Font;
/*      */ import java.awt.Insets;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.InputStream;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.Calendar;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.Vector;
/*      */ import javax.servlet.ServletOutputStream;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class BomHandler
/*      */   extends SecureHandler
/*      */   implements Handler, MilestoneConstants
/*      */ {
/*      */   public static final String COMPONENT_CODE = "hBom";
/*      */   public static final int REPORT_TEXTBOX_BOTTOM_BORDER = 4113;
/*      */   public GeminiApplication application;
/*      */   public ComponentLog log;
/*      */   
/*      */   public BomHandler(GeminiApplication application) {
/*  117 */     this.application = application;
/*  118 */     this.log = application.getLog("hBom");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  126 */   public String getDescription() { return "Bill of Materials"; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean acceptRequest(Dispatcher dispatcher, Context context, String command) {
/*  136 */     if (super.acceptRequest(dispatcher, context, command))
/*      */     {
/*  138 */       if (command.startsWith("bom"))
/*      */       {
/*  140 */         return handleRequest(dispatcher, context, command);
/*      */       }
/*      */     }
/*  143 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean handleRequest(Dispatcher dispatcher, Context context, String command) {
/*  156 */     EmailDistribution.removeSessionValues(context);
/*      */     
/*  158 */     if (command.equalsIgnoreCase("bom-editor") || command.equalsIgnoreCase("bom-edit-new")) {
/*      */       
/*  160 */       edit(dispatcher, context, command);
/*      */     }
/*  162 */     else if (command.equalsIgnoreCase("bom-edit-save")) {
/*      */       
/*  164 */       save(dispatcher, context, command);
/*      */     } 
/*  166 */     if (command.equalsIgnoreCase("bom-edit-copy"))
/*      */     {
/*  168 */       editCopy(dispatcher, context, command);
/*      */     }
/*  170 */     if (command.equalsIgnoreCase("bom-paste-copy")) {
/*      */       
/*  172 */       pasteCopy(dispatcher, context, command);
/*      */     }
/*  174 */     else if (command.equalsIgnoreCase("bom-search")) {
/*      */       
/*  176 */       search(dispatcher, context, command);
/*      */     }
/*  178 */     else if (command.equalsIgnoreCase("bom-sort")) {
/*      */       
/*  180 */       sort(dispatcher, context);
/*      */     }
/*  182 */     else if (command.equalsIgnoreCase("bom-print-pdf")) {
/*      */       
/*  184 */       print(dispatcher, context, command, 0, true, null);
/*      */     }
/*  186 */     else if (command.equalsIgnoreCase("bom-print-pdf4")) {
/*      */       
/*  188 */       print(dispatcher, context, command, 0, false, null);
/*      */     }
/*  190 */     else if (command.equalsIgnoreCase("bom-print-rtf")) {
/*      */       
/*  192 */       print(dispatcher, context, command, 1, true, null);
/*      */     }
/*  194 */     else if (command.equalsIgnoreCase("bom-group")) {
/*      */       
/*  196 */       sortGroup(dispatcher, context);
/*      */     } 
/*  198 */     if (command.equalsIgnoreCase("bom-send-email"))
/*      */     {
/*      */       
/*  201 */       EmailDistribution.sendEmail(dispatcher, context, "bom-editor", null);
/*      */     }
/*      */ 
/*      */     
/*  205 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean edit(Dispatcher dispatcher, Context context, String command) {
/*  213 */     int selectionID = -1;
/*      */     
/*  215 */     User user = (User)context.getSessionValue("user");
/*      */ 
/*      */     
/*  218 */     Notepad notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 0);
/*  219 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */     
/*  221 */     Selection selection = new Selection();
/*  222 */     Bom bom = null;
/*  223 */     selection = MilestoneHelper.getScreenSelection(context);
/*      */     
/*  225 */     context.putSessionValue("Selection", selection);
/*      */     
/*  227 */     boolean sameConfig = false;
/*  228 */     String configCopiedBom = "";
/*  229 */     String configCurrentBom = "";
/*      */     
/*  231 */     context.removeDelivery("newBOM");
/*      */     
/*  233 */     if (selection != null) {
/*      */       
/*  235 */       bom = SelectionManager.getInstance().getBom(selection);
/*      */       
/*  237 */       if (bom != null && bom.getBomId() != 0)
/*      */       {
/*      */         
/*  240 */         Form form = buildForm(context, selection, bom, command);
/*  241 */         form.addElement(new FormHidden("copyPaste", "copy", true));
/*  242 */         context.putDelivery("Form", form);
/*      */       }
/*      */       else
/*      */       {
/*  246 */         context.putDelivery("newBOM", Boolean.TRUE);
/*      */         
/*  248 */         Form form = buildNewForm(context, selection, command);
/*      */ 
/*      */ 
/*      */         
/*  252 */         if ((Bom)context.getSessionValue("copiedBom") != null) {
/*  253 */           Selection currentSelection = (Selection)context.getSessionValue("Selection");
/*  254 */           configCurrentBom = currentSelection.getSelectionConfig().getSelectionConfigurationName();
/*      */           
/*  256 */           Selection copiedSelection = ((Bom)context.getSessionValue("copiedBom")).getSelection();
/*  257 */           configCopiedBom = copiedSelection.getSelectionConfig().getSelectionConfigurationName();
/*      */           
/*  259 */           if (configCopiedBom.equals(configCurrentBom)) {
/*  260 */             form.addElement(new FormHidden("copyPaste", "paste", true));
/*      */           }
/*      */         } 
/*      */ 
/*      */         
/*  265 */         context.putDelivery("Form", form);
/*      */         
/*  267 */         context.removeDelivery("newBOM");
/*      */       
/*      */       }
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  275 */       if (context.getSessionValue("NOTEPAD_BOM_VISIBLE") != null) {
/*  276 */         context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_BOM_VISIBLE"));
/*      */       }
/*  278 */       Form form = new Form(this.application, "form", this.application.getInfrastructure().getServletURL(), "POST");
/*  279 */       form.addElement(new FormHidden("cmd", "bom-editor", true));
/*  280 */       form.addElement(new FormHidden("selectionID", "-1", true));
/*  281 */       form.addElement(new FormHidden("OrderBy", "", true));
/*      */ 
/*      */       
/*  284 */       form.addElement(new FormHidden("bomLastUpdateCheck", "-1", true));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  291 */       addSelectionSearchElements(context, selection, form);
/*      */       
/*  293 */       context.putDelivery("Form", form);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  300 */       return context.includeJSP("blank-bom-editor.jsp");
/*      */     } 
/*      */     
/*  303 */     context.putSessionValue("Bom", bom);
/*      */ 
/*      */     
/*  306 */     if (selection.getIsDigital()) {
/*  307 */       return dispatcher.redispatch(context, "schedule-editor");
/*      */     }
/*  309 */     return context.includeJSP("bom-editor.jsp");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean editCopy(Dispatcher dispatcher, Context context, String command) {
/*  325 */     User user = MilestoneSecurity.getUser(context);
/*      */     
/*  327 */     Notepad notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 0);
/*  328 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */     
/*  330 */     Selection selection = (Selection)context.getSessionValue("Selection");
/*      */     
/*  332 */     int selectionID = selection.getSelectionID();
/*      */     
/*  334 */     Bom targetBom = SelectionManager.getInstance().getBom(selection);
/*  335 */     Bom copiedBom = null;
/*      */ 
/*      */     
/*  338 */     if (targetBom != null) {
/*      */ 
/*      */       
/*      */       try {
/*  342 */         copiedBom = (Bom)targetBom.clone();
/*  343 */         copiedBom.setSelection((Selection)context.getSessionValue("Selection"));
/*      */       }
/*  345 */       catch (CloneNotSupportedException cloneNotSupportedException) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  351 */       Form form = buildForm(context, selection, copiedBom, command);
/*  352 */       form.addElement(new FormHidden("cmd", "bom-edit-copy", true));
/*  353 */       form.addElement(new FormHidden("copyPaste", "paste", true));
/*      */       
/*  355 */       context.putSessionValue("copiedBom", copiedBom);
/*  356 */       context.putSessionValue("Bom", targetBom);
/*  357 */       context.putDelivery("Form", form);
/*      */     } 
/*      */     
/*  360 */     if (context.getSessionValue("NOTEPAD_BOM_VISIBLE") != null) {
/*  361 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_BOM_VISIBLE"));
/*      */     }
/*  363 */     return edit(dispatcher, context, command);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean pasteCopy(Dispatcher dispatcher, Context context, String command) {
/*  373 */     User user = MilestoneSecurity.getUser(context);
/*      */     
/*  375 */     Notepad notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 0);
/*  376 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */     
/*  378 */     Selection selection = (Selection)context.getSessionValue("Selection");
/*  379 */     int selectionID = selection.getSelectionID();
/*      */     
/*  381 */     Bom copiedBom = (Bom)context.getSessionValue("copiedBom");
/*  382 */     Bom bom = (Bom)context.getSessionValue("Bom");
/*      */ 
/*      */     
/*  385 */     copiedBom.setDate(Calendar.getInstance());
/*      */     
/*  387 */     copiedBom.setStreetDateOnBom(selection.getStreetDate());
/*      */ 
/*      */     
/*  390 */     copiedBom.setPrintOption("Draft");
/*      */ 
/*      */     
/*  393 */     User currentUser = (User)context.getSessionValue("user");
/*      */     
/*  395 */     String selectionValue = "Add";
/*  396 */     copiedBom.setType(selectionValue);
/*      */     
/*  398 */     copiedBom.setChangeNumber("-1");
/*      */     
/*  400 */     copiedBom.setSubmitter(currentUser.getName());
/*      */     
/*  402 */     copiedBom.setPhone(currentUser.getPhone());
/*      */     
/*  404 */     copiedBom.setEmail(currentUser.getEmail());
/*      */     
/*  406 */     copiedBom.setUnitsPerKG(0);
/*      */     
/*  408 */     copiedBom.setUseShrinkWrap(true);
/*      */     
/*  410 */     copiedBom.setHasSpineSticker(true);
/*      */     
/*  412 */     copiedBom.setConfiguration("");
/*      */     
/*  414 */     copiedBom.setRunTime("");
/*      */     
/*  416 */     copiedBom.setModifiedOn(null);
/*      */     
/*  418 */     copiedBom.setModifiedBy(99999);
/*      */ 
/*      */     
/*  421 */     Form form = buildForm(context, selection, copiedBom, command);
/*  422 */     form.removeElement("cmd");
/*  423 */     form.addElement(new FormHidden("cmd", "bom-paste-copy", true));
/*      */     
/*  425 */     context.putSessionValue("Bom", bom);
/*  426 */     context.putDelivery("Form", form);
/*      */     
/*  428 */     context.removeSessionValue("copiedBom");
/*      */ 
/*      */     
/*  431 */     if (context.getSessionValue("NOTEPAD_BOM_VISIBLE") != null) {
/*  432 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_BOM_VISIBLE"));
/*      */     }
/*      */ 
/*      */     
/*  436 */     if (selection.getIsDigital()) {
/*  437 */       return dispatcher.redispatch(context, "schedule-editor");
/*      */     }
/*  439 */     return context.includeJSP("bom-editor.jsp");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean search(Dispatcher dispatcher, Context context, String command) {
/*  451 */     String isSelectionSearchResults = context.getParameter("isSelectionSearchResults");
/*  452 */     if (isSelectionSearchResults == null || !isSelectionSearchResults.equals("true")) {
/*      */       
/*  454 */       Notepad notepad = MilestoneHelper.getNotepadFromSession(0, 
/*  455 */           context);
/*      */ 
/*      */       
/*  458 */       notepad.setAllContents(null);
/*  459 */       notepad.setSelected(null);
/*      */ 
/*      */       
/*  462 */       notepad.setMaxRecords(225);
/*      */ 
/*      */       
/*  465 */       Form form = new Form(this.application, "selectionForm", 
/*  466 */           this.application.getInfrastructure().getServletURL(), 
/*  467 */           "POST");
/*  468 */       addSelectionSearchElements(context, null, form);
/*  469 */       form.setValues(context);
/*      */       
/*  471 */       SelectionManager.getInstance().setSelectionNotepadQuery(context, 
/*  472 */           MilestoneSecurity.getUser(context).getUserId(), notepad, form);
/*      */     } 
/*  474 */     dispatcher.redispatch(context, "bom-editor");
/*      */     
/*  476 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean sort(Dispatcher dispatcher, Context context) {
/*  487 */     int userId = ((User)context.getSessionValue("user")).getUserId();
/*      */     
/*  489 */     Notepad notepad = SelectionManager.getInstance().getSelectionNotepad(context, userId, 0);
/*      */ 
/*      */     
/*  492 */     NotepadSortOrder.getNotepadSortOrderFromSession(context).sortHelper(dispatcher, context, notepad);
/*      */ 
/*      */     
/*  495 */     dispatcher.redispatch(context, "bom-editor");
/*      */     
/*  497 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Form buildForm(Context context, Selection selection, Bom bom, String command) {
/*  506 */     Form bomForm = new Form(this.application, "bomForm", this.application.getInfrastructure().getServletURL(), "POST");
/*      */ 
/*      */     
/*  509 */     String selectionID = "-1";
/*  510 */     if (selection != null)
/*      */     {
/*  512 */       selectionID = String.valueOf(selection.getSelectionID());
/*      */     }
/*  514 */     bomForm.addElement(new FormHidden("selectionID", selectionID, true));
/*      */ 
/*      */     
/*  517 */     long timeStamp = -1L;
/*  518 */     if (bom != null)
/*  519 */       timeStamp = bom.getLastUpdatedCheck(); 
/*  520 */     bomForm.addElement(new FormHidden("bomLastUpdateCheck", Long.toString(timeStamp), true));
/*      */     
/*  522 */     User currentUser = (User)context.getSessionValue("user");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  538 */     FormCheckBox UseNoShrinkWrap = new FormCheckBox("UseNoShrinkWrap", "on", "", false, bom.useShrinkWrap());
/*  539 */     UseNoShrinkWrap.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/*  540 */     UseNoShrinkWrap.setId("Shrink Wrap");
/*      */     
/*  542 */     UseNoShrinkWrap.setStartingChecked(bom.useShrinkWrap());
/*  543 */     UseNoShrinkWrap.setTabIndex(13);
/*      */     
/*  545 */     bomForm.addElement(UseNoShrinkWrap);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  550 */     FormCheckBox HasSpineSticker = new FormCheckBox("HasSpineSticker", "on", "", false, bom.hasSpineSticker());
/*  551 */     HasSpineSticker.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/*  552 */     HasSpineSticker.setId("Spine Sticker");
/*  553 */     HasSpineSticker.setTabIndex(12);
/*      */     
/*  555 */     HasSpineSticker.setStartingChecked(bom.hasSpineSticker());
/*  556 */     bomForm.addElement(HasSpineSticker);
/*      */ 
/*      */ 
/*      */     
/*  560 */     if (bom.getBomCassetteDetail() == null && bom.getBomDiskDetail() == null && bom.getBomVinylDetail() == null && bom.getBomDVDDetail() == null) {
/*      */ 
/*      */       
/*  563 */       command = "bom-edit-new";
/*      */       
/*  565 */       if (selection.getReleaseType().getName().equalsIgnoreCase("Promotional")) {
/*  566 */         UseNoShrinkWrap.setChecked(false);
/*  567 */         HasSpineSticker.setChecked(false);
/*      */       } else {
/*  569 */         UseNoShrinkWrap.setChecked(true);
/*  570 */         HasSpineSticker.setChecked(true);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  584 */     int secureLevel = getSelectionBomPermissions(selection, currentUser);
/*  585 */     setButtonVisibilities(selection, currentUser, context, secureLevel, command);
/*      */     
/*  587 */     bomForm.addElement(new FormHidden("OrderBy", "", true));
/*      */     
/*  589 */     Calendar defaultDate = Calendar.getInstance();
/*      */ 
/*      */ 
/*      */     
/*  593 */     MilestoneInfrastructure inf = (MilestoneInfrastructure)context.getInfrastructure();
/*      */ 
/*      */     
/*  596 */     String printOption = "Draft";
/*  597 */     if (bom.getPrintOption().length() > 0 && selection.getSelectionNo() != null && 
/*  598 */       selection.getSelectionNo().toUpperCase().indexOf("TEMP") == -1)
/*      */     {
/*  600 */       printOption = bom.getPrintOption();
/*      */     }
/*      */ 
/*      */     
/*  604 */     String pOptions = "Draft,Final";
/*      */     
/*  606 */     if (printOption.equalsIgnoreCase("Final")) {
/*  607 */       pOptions = printOption;
/*      */     }
/*  609 */     FormRadioButtonGroup PrintOption = new FormRadioButtonGroup("printOption", printOption, pOptions, false);
/*      */ 
/*      */ 
/*      */     
/*  613 */     boolean numberUnitsIsZero = false;
/*  614 */     numberUnitsIsZero = (selection.getNumberOfUnits() == 0);
/*  615 */     boolean statusIsTBS = false;
/*  616 */     statusIsTBS = SelectionManager.getLookupObjectValue(selection.getSelectionStatus()).equalsIgnoreCase("TBS");
/*      */     
/*  618 */     if (numberUnitsIsZero || statusIsTBS) {
/*  619 */       PrintOption.addFormEvent("onClick", "noUnitsOrTBS(this, " + numberUnitsIsZero + ", " + statusIsTBS + ");hidePrintButtons('printWindow','');toggleSave('" + inf.getImageDirectory() + "');");
/*      */     } else {
/*  621 */       PrintOption.addFormEvent("onClick", "hidePrintButtons('printWindow','');toggleSave('" + inf.getImageDirectory() + "');");
/*      */     } 
/*      */     
/*  624 */     PrintOption.setTabIndex(0);
/*  625 */     PrintOption.setId("Draft/Final");
/*  626 */     bomForm.addElement(PrintOption);
/*      */ 
/*      */ 
/*      */     
/*  630 */     FormRadioButtonGroup sendOption = new FormRadioButtonGroup("sendOption", "Send Email", "Send Email,Do Not Send Email", false);
/*  631 */     sendOption.addFormEvent("onClick", "toggleSaveSend('" + inf.getImageDirectory() + "');");
/*  632 */     sendOption.setTabIndex(0);
/*  633 */     bomForm.addElement(sendOption);
/*      */ 
/*      */     
/*  636 */     String dateText = MilestoneHelper.getFormatedDate(defaultDate);
/*  637 */     if (bom.getDate() != null)
/*      */     {
/*  639 */       dateText = MilestoneHelper.getFormatedDate(bom.getDate());
/*      */     }
/*      */     
/*  642 */     FormDateField Date = new FormDateField("Date", dateText, false, 10);
/*  643 */     Date.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
/*  644 */     Date.addFormEvent("onBlur", "JavaScript:checkField( this )");
/*  645 */     Date.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/*  646 */     Date.setTabIndex(1);
/*  647 */     bomForm.addElement(Date);
/*      */ 
/*      */ 
/*      */     
/*  651 */     String typeText = "Add";
/*  652 */     if (bom.getType().equalsIgnoreCase("C")) {
/*      */       
/*  654 */       typeText = "Change";
/*      */     }
/*      */     else {
/*      */       
/*  658 */       typeText = "Add";
/*      */     } 
/*  660 */     FormRadioButtonGroup IsAdded = new FormRadioButtonGroup("IsAdded", typeText, "Add, Change", false);
/*  661 */     IsAdded.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/*  662 */     IsAdded.setTabIndex(2);
/*  663 */     IsAdded.setId("Add/Change");
/*      */ 
/*      */     
/*  666 */     IsAdded.setEnabled(false);
/*      */     
/*  668 */     bomForm.addElement(IsAdded);
/*      */ 
/*      */     
/*  671 */     String changeNumberText = "-1";
/*  672 */     if (bom.getChangeNumber() != null && !bom.getChangeNumber().equals(""))
/*      */     {
/*  674 */       changeNumberText = bom.getChangeNumber();
/*      */     }
/*  676 */     FormTextField ChangeNumber = new FormTextField("ChangeNumber", changeNumberText, false, 2, 2);
/*  677 */     ChangeNumber.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/*  678 */     ChangeNumber.setTabIndex(3);
/*  679 */     ChangeNumber.setId("Change #");
/*      */ 
/*      */     
/*  682 */     ChangeNumber.setEnabled(false);
/*  683 */     bomForm.addElement(ChangeNumber);
/*      */ 
/*      */     
/*  686 */     String submitterText = "";
/*  687 */     if (bom.getSubmitter() != null && !command.equalsIgnoreCase("bom-edit-new")) {
/*      */       
/*  689 */       submitterText = bom.getSubmitter();
/*      */     } else {
/*  691 */       submitterText = currentUser.getName();
/*      */     } 
/*      */     
/*  694 */     FormTextField Submitter = new FormTextField("Submitter", submitterText, false, 30, 50);
/*  695 */     Submitter.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/*  696 */     Submitter.setTabIndex(4);
/*  697 */     Submitter.setId("Submitted By");
/*  698 */     bomForm.addElement(Submitter);
/*      */ 
/*      */     
/*  701 */     String phoneText = "";
/*  702 */     if (bom.getPhone() != null && !command.equalsIgnoreCase("bom-edit-new")) {
/*      */       
/*  704 */       phoneText = bom.getPhone();
/*      */     } else {
/*  706 */       phoneText = currentUser.getPhone();
/*      */     } 
/*  708 */     FormTextField Phone = new FormTextField("Phone", phoneText, false, 30, 30);
/*  709 */     Phone.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/*  710 */     Phone.setTabIndex(5);
/*  711 */     bomForm.addElement(Phone);
/*      */     
/*  713 */     String emailText = "";
/*  714 */     if (bom.getEmail() != null && !command.equalsIgnoreCase("bom-edit-new")) {
/*      */       
/*  716 */       emailText = bom.getEmail();
/*      */     }
/*  718 */     else if (command.equalsIgnoreCase("bom-edit-new")) {
/*  719 */       emailText = currentUser.getEmail();
/*      */     } 
/*  721 */     FormTextField Email = new FormTextField("Email", emailText, false, 30, 50);
/*  722 */     Email.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/*  723 */     Email.setTabIndex(6);
/*  724 */     bomForm.addElement(Email);
/*      */ 
/*      */     
/*  727 */     String releasingCompanyText = "";
/*  728 */     Vector companyList = Cache.getReleaseCompanies();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  741 */     String releasingCompText = "";
/*  742 */     if (ReleasingFamily.getName(selection.getReleaseFamilyId()) != null) {
/*  743 */       releasingCompText = ReleasingFamily.getName(selection.getReleaseFamilyId());
/*      */     }
/*      */     
/*  746 */     FormTextField releasingComp = new FormTextField("ReleasingComp", releasingCompText, false, 30);
/*  747 */     releasingComp.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/*  748 */     releasingComp.setTabIndex(7);
/*  749 */     releasingComp.setId("Releasing Co");
/*      */ 
/*      */     
/*  752 */     if (context.getDelivery("newBOM") == null) {
/*  753 */       releasingComp.setStartingValue(bom.getReleasingCompanyId());
/*      */     }
/*  755 */     bomForm.addElement(releasingComp);
/*      */ 
/*      */     
/*  758 */     Selection mySelection = selection;
/*      */ 
/*      */     
/*  761 */     String labelText = "";
/*  762 */     if (mySelection.getLabel() != null)
/*      */     {
/*  764 */       labelText = mySelection.getLabel().getName().toUpperCase();
/*      */     }
/*  766 */     FormTextField Label = new FormTextField("Label", labelText, false, 30);
/*  767 */     Label.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/*      */ 
/*      */ 
/*      */     
/*  771 */     String bomLabel = MilestoneHelper.getStructureName(bom.getLabelId()).toUpperCase();
/*  772 */     if (context.getDelivery("newBOM") == null)
/*      */     {
/*  774 */       if (bomLabel != null && bomLabel.length() > 0)
/*  775 */         Label.setStartingValue(bomLabel); 
/*      */     }
/*  777 */     bomForm.addElement(Label);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  782 */     String statusText = "";
/*  783 */     if (mySelection.getSelectionStatus() != null)
/*      */     {
/*  785 */       statusText = mySelection.getSelectionStatus().getName();
/*      */     }
/*  787 */     FormTextField status = new FormTextField("status", statusText, false, 30);
/*  788 */     status.setStartingValue(bom.getStatus());
/*  789 */     bomForm.addElement(status);
/*      */ 
/*      */ 
/*      */     
/*  793 */     String upc = "";
/*  794 */     if (mySelection.getUpc() != null)
/*      */     {
/*  796 */       upc = mySelection.getUpc();
/*      */     }
/*  798 */     FormTextField Upc = new FormTextField("upc", upc, false, 20);
/*      */     
/*  800 */     if (bom.getUpc() == null) {
/*  801 */       Upc.setStartingValue("");
/*      */     } else {
/*  803 */       Upc.setStartingValue(bom.getUpc());
/*  804 */     }  bomForm.addElement(Upc);
/*      */ 
/*      */     
/*  807 */     String imprintText = "";
/*  808 */     if (mySelection.getImprint() != null)
/*      */     {
/*  810 */       imprintText = mySelection.getImprint();
/*      */     }
/*  812 */     FormTextField imprint = new FormTextField("imprint", imprintText, false, 30);
/*  813 */     bomForm.addElement(imprint);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  819 */     String artistText = "";
/*  820 */     if (mySelection.getArtist() != null)
/*      */     {
/*  822 */       artistText = mySelection.getArtist();
/*      */     }
/*  824 */     FormTextField Artist = new FormTextField("Artist", artistText, false, 125);
/*  825 */     Artist.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/*      */ 
/*      */     
/*  828 */     if (context.getDelivery("newBOM") == null) {
/*      */       
/*  830 */       Artist.setStartingValue(artistText);
/*  831 */       if (bom.getArtist() != null && bom.getArtist().length() > 0) {
/*  832 */         Artist.setStartingValue(bom.getArtist());
/*      */       }
/*      */     } 
/*  835 */     bomForm.addElement(Artist);
/*  836 */     bomForm.addElement(new FormHidden("Artist", artistText, true));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  858 */     String titleText = "";
/*  859 */     if (mySelection.getTitle() != null)
/*      */     {
/*  861 */       titleText = mySelection.getTitle();
/*      */     }
/*  863 */     FormTextField Title = new FormTextField("Title", titleText, false, 125);
/*  864 */     Title.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/*      */ 
/*      */     
/*  867 */     if (context.getDelivery("newBOM") == null)
/*      */     {
/*  869 */       if (bom.getTitle() != null && bom.getTitle().length() > 0)
/*  870 */         Title.setStartingValue(bom.getTitle()); 
/*      */     }
/*  872 */     bomForm.addElement(Title);
/*      */ 
/*      */     
/*  875 */     String isRetailText = "";
/*      */     
/*  877 */     if (bom.isRetail) {
/*  878 */       isRetailText = "Commercial";
/*      */     } else {
/*  880 */       isRetailText = "Promotional";
/*      */     } 
/*  882 */     String selIsRetailText = "";
/*  883 */     if (selection.getReleaseType().getName().equalsIgnoreCase("Promotional")) {
/*  884 */       selIsRetailText = "Promotional";
/*      */     } else {
/*  886 */       selIsRetailText = "Commercial";
/*      */     } 
/*  888 */     FormRadioButtonGroup IsRetail = new FormRadioButtonGroup("IsRetail", selIsRetailText, "Commercial, Promotional", false);
/*      */     
/*  890 */     IsRetail.setTabIndex(8);
/*      */ 
/*      */     
/*  893 */     if (context.getDelivery("newBOM") == null) {
/*  894 */       IsRetail.setStartingValue(isRetailText);
/*      */     }
/*  896 */     IsRetail.setId("Type");
/*      */     
/*  898 */     bomForm.addElement(IsRetail);
/*      */ 
/*      */     
/*  901 */     String selectionNumberText = "";
/*      */ 
/*      */     
/*  904 */     String prefix = SelectionManager.getLookupObjectValue(selection.getPrefixID());
/*  905 */     if (prefix.length() > 0) {
/*  906 */       selectionNumberText = String.valueOf(SelectionManager.getLookupObjectValue(selection.getPrefixID())) + " " + selection.getSelectionNo();
/*      */     } else {
/*  908 */       selectionNumberText = selection.getSelectionNo();
/*  909 */     }  FormTextField SelectionNumber = new FormTextField("SelectionNumber", selectionNumberText, false, 50);
/*  910 */     SelectionNumber.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/*      */ 
/*      */     
/*  913 */     SelectionNumber.setId("Local Prod #");
/*  914 */     if (context.getDelivery("newBOM") == null)
/*      */     {
/*  916 */       if (bom.getSelectionNumber() != null && bom.getSelectionNumber().length() > 0)
/*  917 */         SelectionNumber.setStartingValue(bom.getSelectionNumber()); 
/*      */     }
/*  919 */     bomForm.addElement(SelectionNumber);
/*      */ 
/*      */ 
/*      */     
/*  923 */     String streetDateOnBomText = "";
/*  924 */     String selStreetDateOnBomText = "";
/*  925 */     if (bom.getBomCassetteDetail() != null || 
/*  926 */       bom.getBomDiskDetail() != null || 
/*  927 */       bom.getBomVinylDetail() != null || 
/*  928 */       bom.getBomDVDDetail() != null) {
/*      */       
/*  930 */       if (bom.getStreetDateOnBom() != null)
/*      */       {
/*  932 */         streetDateOnBomText = MilestoneHelper.getFormatedDate(bom.getStreetDateOnBom());
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/*  937 */       streetDateOnBomText = MilestoneHelper.getFormatedDate(mySelection.getStreetDate());
/*      */     } 
/*      */     
/*  940 */     selStreetDateOnBomText = MilestoneHelper.getFormatedDate(mySelection.getStreetDate());
/*      */     
/*  942 */     FormDateField streetDateOnBom = new FormDateField("DueDate", selStreetDateOnBomText, false, 10);
/*  943 */     streetDateOnBom.setEnabled(false);
/*  944 */     streetDateOnBom.setTabIndex(9);
/*  945 */     streetDateOnBom.setStartingValue(streetDateOnBomText);
/*  946 */     streetDateOnBom.setId("Street Date");
/*  947 */     bomForm.addElement(streetDateOnBom);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  952 */     String unitsPerSetText = "";
/*  953 */     if (selection.getNumberOfUnits() > 0) {
/*      */       
/*      */       try {
/*      */         
/*  957 */         unitsPerSetText = Integer.toString(selection.getNumberOfUnits());
/*      */       }
/*  959 */       catch (NumberFormatException e) {
/*      */         
/*  961 */         System.out.println("Error converting Units Per Set into integer.");
/*      */       } 
/*      */     }
/*      */     
/*  965 */     FormTextField UnitsPerPackage = new FormTextField("UnitsPerPackage", unitsPerSetText, false, 10);
/*  966 */     UnitsPerPackage.setEnabled(false);
/*  967 */     UnitsPerPackage.setTabIndex(10);
/*  968 */     UnitsPerPackage.setId("Units per Package ");
/*      */ 
/*      */     
/*  971 */     if (context.getDelivery("newBOM") == null) {
/*  972 */       UnitsPerPackage.setStartingValue(String.valueOf(bom.getUnitsPerKG()));
/*      */     }
/*  974 */     bomForm.addElement(UnitsPerPackage);
/*      */ 
/*      */     
/*  977 */     String runTimeText = "";
/*  978 */     if (bom.getRunTime() != null)
/*      */     {
/*  980 */       runTimeText = bom.getRunTime();
/*      */     }
/*  982 */     FormTextField Runtime = new FormTextField("Runtime", runTimeText, false, 10, 10);
/*  983 */     Runtime.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/*  984 */     Runtime.setTabIndex(11);
/*  985 */     Runtime.setId("Runtime");
/*  986 */     bomForm.addElement(Runtime);
/*      */ 
/*      */     
/*  989 */     FormTextField Configuration = new FormTextField("Configuration", bom.getConfiguration(), false, 10, 10);
/*  990 */     Configuration.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/*  991 */     bomForm.addElement(Configuration);
/*      */ 
/*      */     
/*  994 */     String configuration = "";
/*  995 */     configuration = selection.getSelectionConfig().getSelectionConfigurationAbbreviation();
/*      */ 
/*      */     
/*  998 */     boolean isCDoverride = false;
/*  999 */     if (bom.getFormat().equalsIgnoreCase("CDO")) {
/* 1000 */       isCDoverride = true;
/*      */     }
/*      */ 
/*      */     
/* 1004 */     if (configuration.equalsIgnoreCase("CAS")) {
/*      */       
/* 1006 */       if (bom.getBomCassetteDetail() == null)
/*      */       {
/* 1008 */         buildNewCassette(bomForm, selection, bom);
/* 1009 */         bomForm.addElement(new FormHidden("cmd", "bom-edit-new", true));
/*      */       }
/*      */       else
/*      */       {
/* 1013 */         buildCassette(bomForm, selection, bom);
/*      */       }
/*      */     
/* 1016 */     } else if (configuration.equalsIgnoreCase("VIN")) {
/*      */       
/* 1018 */       if (bom.getBomVinylDetail() == null)
/*      */       {
/* 1020 */         buildNewVinyl(bomForm, selection, bom);
/* 1021 */         bomForm.addElement(new FormHidden("cmd", "bom-edit-new", true));
/*      */       }
/*      */       else
/*      */       {
/* 1025 */         buildVinyl(bomForm, selection, bom);
/*      */       }
/*      */     
/*      */     }
/* 1029 */     else if (configuration.equalsIgnoreCase("DVV") && !isCDoverride) {
/*      */       
/* 1031 */       if (bom.getBomDVDDetail() == null)
/*      */       {
/* 1033 */         buildNewDVD(bomForm, selection, bom);
/* 1034 */         bomForm.addElement(new FormHidden("cmd", "bom-edit-new", true));
/*      */       }
/*      */       else
/*      */       {
/* 1038 */         buildDVD(bomForm, selection, bom);
/*      */       
/*      */       }
/*      */     
/*      */     }
/* 1043 */     else if (bom.getBomDiskDetail() == null) {
/*      */       
/* 1045 */       buildNewDisk(bomForm, selection, bom);
/* 1046 */       bomForm.addElement(new FormHidden("cmd", "bom-edit-new", true));
/*      */     }
/*      */     else {
/*      */       
/* 1050 */       buildDisk(bomForm, selection, bom, bom.getBomDiskDetail());
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1055 */     FormTextArea SpecialInstructions = new FormTextArea("SpecialInstructions", bom.getSpecialInstructions(), false, 15, 100, "virtual");
/* 1056 */     SpecialInstructions.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 1057 */     SpecialInstructions.setTabIndex(100);
/* 1058 */     SpecialInstructions.setId("Special Instructions");
/* 1059 */     bomForm.addElement(SpecialInstructions);
/*      */ 
/*      */     
/* 1062 */     FormTextField lastUpdated = new FormTextField("lastUpdatedDate", false, 50);
/* 1063 */     lastUpdated.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 1064 */     if (bom.getModifiedOn() != null)
/* 1065 */       lastUpdated.setValue(MilestoneHelper.getCustomFormatedDate(bom.getModifiedOn(), "M/d/yyyy hh:mm:ss a 'ET'")); 
/* 1066 */     bomForm.addElement(lastUpdated);
/*      */ 
/*      */     
/* 1069 */     FormTextField lastLegacyUpdateDate = new FormTextField("lastLegacyUpdateDate", false, 50);
/* 1070 */     lastLegacyUpdateDate.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 1071 */     lastLegacyUpdateDate.setValue(MilestoneHelper.getCustomFormatedDate(selection.getLastLegacyUpdateDate(), "M/d/yyyy hh:mm:ss a 'ET'"));
/* 1072 */     bomForm.addElement(lastLegacyUpdateDate);
/*      */     
/* 1074 */     FormTextField lastUpdatedBy = new FormTextField("lastUpdatedBy", false, 50);
/* 1075 */     lastUpdatedBy.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 1076 */     if (UserManager.getInstance().getUser(bom.getModifiedBy()) != null)
/* 1077 */       lastUpdatedBy.setValue(UserManager.getInstance().getUser(bom.getModifiedBy()).getName()); 
/* 1078 */     bomForm.addElement(lastUpdatedBy);
/*      */ 
/*      */     
/* 1081 */     String archieDateText = "";
/* 1082 */     if (selection.getArchieDate() != null)
/* 1083 */       archieDateText = MilestoneHelper.getCustomFormatedDate(selection.getArchieDate(), "M/d/yyyy hh:mm:ss a 'ET'"); 
/* 1084 */     FormTextField archieDate = new FormTextField("archieDate", archieDateText, false, 13);
/* 1085 */     bomForm.addElement(archieDate);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1092 */     bomForm = addSelectionSearchElements(context, selection, bomForm);
/*      */     
/* 1094 */     bomForm.addElement(new FormHidden("cmd", "bom-editor", true));
/*      */     
/* 1096 */     context.putDelivery("releaseWeek", MilestoneHelper.getReleaseWeekString(selection));
/*      */ 
/*      */     
/* 1099 */     if (context.getSessionValue("NOTEPAD_BOM_VISIBLE") != null) {
/* 1100 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_BOM_VISIBLE"));
/*      */     }
/* 1102 */     selection.setBom(bom);
/*      */ 
/*      */     
/* 1105 */     context.putDelivery("selection", selection);
/* 1106 */     return bomForm;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean save(Dispatcher dispatcher, Context context, String command) {
/* 1115 */     Selection selection = null;
/* 1116 */     Selection selectionTemp = null;
/* 1117 */     Bom bom = null;
/* 1118 */     int selectionID = -1;
/*      */ 
/*      */     
/* 1121 */     if (command.equalsIgnoreCase("selectionSave")) {
/*      */       
/* 1123 */       selectionTemp = (Selection)context.getSessionValue("pfmBomSelection");
/* 1124 */       selection = SelectionManager.getInstance().getSelectionHeader(selectionTemp.getSelectionID());
/*      */     }
/*      */     else {
/*      */       
/* 1128 */       Notepad notepad = MilestoneHelper.getNotepadFromSession(0, context);
/* 1129 */       selectionID = Integer.parseInt(context.getRequestValue("selectionID"));
/*      */ 
/*      */       
/* 1132 */       selection = SelectionManager.getInstance().getSelectionHeader(selectionID);
/* 1133 */       notepad.setSelected(selection);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1144 */     MessageObject messageObject = new MessageObject();
/*      */     try {
/* 1146 */       messageObject.selectionObj = (Selection)selection.clone();
/* 1147 */     } catch (CloneNotSupportedException ce) {
/* 1148 */       messageObject.selectionObj = selection;
/*      */     } 
/*      */ 
/*      */     
/* 1152 */     User user = (User)context.getSessionValue("user");
/*      */ 
/*      */     
/* 1155 */     Notepad notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 0);
/* 1156 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */ 
/*      */     
/* 1159 */     bom = SelectionManager.getInstance().getBom(selection);
/*      */     
/* 1161 */     boolean finalFlag = false;
/*      */     
/* 1163 */     boolean sendEmail = false;
/*      */     
/* 1165 */     Form form = null;
/*      */     
/* 1167 */     context.removeDelivery("newBOM");
/*      */     
/* 1169 */     if (bom != null) {
/*      */       
/* 1171 */       form = buildForm(context, selection, bom, command);
/*      */       
/* 1173 */       String configString = form.getStringValue("Configuration");
/* 1174 */       bom.setConfiguration(configString);
/*      */     }
/*      */     else {
/*      */       
/* 1178 */       context.putDelivery("newBOM", Boolean.TRUE);
/* 1179 */       bom = new Bom();
/* 1180 */       bom.setReleaseId(selection.getSelectionID());
/* 1181 */       bom.setConfiguration(selection.getSelectionConfig().getSelectionConfigurationAbbreviation());
/* 1182 */       form = buildForm(context, selection, bom, command);
/* 1183 */       form.addElement(new FormHidden("copyPaste", "copy", true));
/* 1184 */       context.removeDelivery("newBOM");
/*      */     } 
/*      */     
/* 1187 */     form.addElement(new FormHidden("cmd", "bom-edit-save", true));
/*      */ 
/*      */     
/* 1190 */     long timestamp = bom.getLastUpdatedCheck();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1195 */     if (!command.equalsIgnoreCase("selectionSave")) {
/*      */       
/* 1197 */       String timestampStr = context.getRequestValue("bomLastUpdateCheck");
/* 1198 */       if (timestampStr != null) {
/* 1199 */         bom.setLastUpdatedCheck(Long.parseLong(timestampStr));
/*      */       }
/*      */     } 
/*      */     
/* 1203 */     if (SelectionManager.getInstance().isTimestampValid(bom)) {
/*      */ 
/*      */       
/* 1206 */       bom.setLastUpdatedCheck(timestamp);
/*      */ 
/*      */       
/* 1209 */       if (!command.equalsIgnoreCase("selectionSave")) {
/* 1210 */         form.setValues(context);
/*      */       }
/*      */       
/* 1213 */       String sendOption = form.getStringValue("sendOption");
/*      */       
/* 1215 */       if (!form.isUnchanged()) {
/*      */ 
/*      */         
/* 1218 */         String printOption = form.getStringValue("printOption");
/* 1219 */         String typeString = form.getStringValue("IsAdded");
/*      */         
/* 1221 */         if (printOption.equalsIgnoreCase("Draft")) {
/*      */           
/* 1223 */           bom.setPrintOption("Draft");
/*      */ 
/*      */ 
/*      */           
/* 1227 */           if (typeString.indexOf("Add") > -1) {
/*      */             
/* 1229 */             typeString = "A";
/* 1230 */             bom.setChangeNumber("-1");
/*      */           }
/*      */           else {
/*      */             
/* 1234 */             typeString = "C";
/*      */           } 
/* 1236 */           bom.setType(typeString);
/*      */ 
/*      */           
/* 1239 */           bom.setType("A");
/* 1240 */           bom.setChangeNumber("-1");
/* 1241 */           FormTextField changeField = (FormTextField)form.getElement("ChangeNumber");
/* 1242 */           changeField.setValue("-1");
/* 1243 */           FormRadioButtonGroup isAdded = (FormRadioButtonGroup)form.getElement("IsAdded");
/* 1244 */           isAdded.setValue("Add");
/*      */         
/*      */         }
/*      */         else {
/*      */           
/* 1249 */           bom.setPrintOption("Final");
/* 1250 */           finalFlag = true;
/*      */ 
/*      */           
/* 1253 */           int changeNumberInt = -1;
/*      */           
/*      */           try {
/* 1256 */             changeNumberInt = Integer.parseInt(form.getStringValue("ChangeNumber"));
/*      */           }
/* 1258 */           catch (Exception e) {
/* 1259 */             changeNumberInt = -1;
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1265 */           if (changeNumberInt >= 0) {
/*      */             
/* 1267 */             StringBuffer changedFields = new StringBuffer();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1273 */             if (EmailDistribution.isFormChanged(form.getChangedElements(), changedFields, true, false, form, messageObject))
/*      */             {
/* 1275 */               sendEmail = true;
/*      */ 
/*      */ 
/*      */               
/* 1279 */               if (sendOption != null && 
/* 1280 */                 !context.getCommand().equals("selection-send-cancel") && 
/* 1281 */                 sendOption.equalsIgnoreCase("Send Email")) {
/* 1282 */                 changeNumberInt++;
/*      */               }
/*      */               
/* 1285 */               bom.setChangeNumber(String.valueOf(changeNumberInt));
/*      */               
/* 1287 */               bom.setType("C");
/* 1288 */               FormTextField changeField = (FormTextField)form.getElement("ChangeNumber");
/* 1289 */               changeField.setValue(String.valueOf(changeNumberInt));
/* 1290 */               FormRadioButtonGroup isAdded = (FormRadioButtonGroup)form.getElement("IsAdded");
/* 1291 */               isAdded.setValue("Change");
/*      */             }
/*      */           
/*      */           } else {
/*      */             
/* 1296 */             sendEmail = true;
/*      */ 
/*      */             
/* 1299 */             if (!context.getCommand().equals("selection-send-cancel")) {
/* 1300 */               changeNumberInt++;
/*      */             }
/*      */             
/* 1303 */             bom.setChangeNumber(String.valueOf(changeNumberInt));
/*      */             
/* 1305 */             bom.setType("A");
/* 1306 */             FormTextField changeField = (FormTextField)form.getElement("ChangeNumber");
/* 1307 */             changeField.setValue(String.valueOf(changeNumberInt));
/* 1308 */             FormRadioButtonGroup isAdded = (FormRadioButtonGroup)form.getElement("IsAdded");
/* 1309 */             isAdded.setValue("Add");
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1318 */         if (sendOption != null && !sendOption.equalsIgnoreCase("Send Email")) {
/* 1319 */           sendEmail = false;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1325 */         int labelID = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1335 */         labelID = selection.getLabel().getStructureID();
/* 1336 */         bom.setLabelId(labelID);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1345 */         if (selection != null) {
/* 1346 */           bom.setReleasingCompanyId(ReleasingFamily.getName(selection.getReleaseFamilyId()));
/*      */         }
/*      */ 
/*      */         
/* 1350 */         String selnoString = form.getStringValue("SelectionNumber");
/* 1351 */         bom.setSelectionNumber(selnoString);
/*      */ 
/*      */         
/* 1354 */         String titleString = form.getStringValue("Title");
/* 1355 */         bom.setTitle(titleString);
/*      */ 
/*      */         
/* 1358 */         String artistString = form.getStringValue("Artist");
/* 1359 */         bom.setArtist(artistString);
/*      */ 
/*      */         
/* 1362 */         String dateString = form.getStringValue("Date");
/* 1363 */         bom.setDate(MilestoneHelper.getDate(dateString));
/*      */ 
/*      */         
/* 1366 */         String submitterString = form.getStringValue("Submitter");
/* 1367 */         bom.setSubmitter(submitterString);
/*      */ 
/*      */         
/* 1370 */         String phoneString = form.getStringValue("Phone");
/* 1371 */         bom.setPhone(phoneString);
/*      */ 
/*      */         
/* 1374 */         String emailString = form.getStringValue("Email");
/* 1375 */         bom.setEmail(emailString);
/*      */ 
/*      */ 
/*      */         
/* 1379 */         String retailString = "";
/* 1380 */         if (selection.getReleaseType().getName().equalsIgnoreCase("Promotional")) {
/* 1381 */           retailString = "Promotional";
/*      */         } else {
/* 1383 */           retailString = "Commercial";
/* 1384 */         }  boolean isRetail = false;
/*      */         
/* 1386 */         if (retailString.equalsIgnoreCase("Commercial")) {
/* 1387 */           isRetail = true;
/*      */         }
/* 1389 */         bom.setIsRetail(isRetail);
/*      */ 
/*      */         
/* 1392 */         String streetDateOnBomString = form.getStringValue("DueDate");
/* 1393 */         Calendar streetDateOnBom = null;
/*      */         
/*      */         try {
/* 1396 */           streetDateOnBom = MilestoneHelper.getDate(streetDateOnBomString);
/* 1397 */           if (streetDateOnBom != null) {
/* 1398 */             bom.setStreetDateOnBom(streetDateOnBom);
/*      */           } else {
/* 1400 */             bom.setStreetDateOnBom(null);
/*      */           }
/*      */         
/* 1403 */         } catch (Exception e) {
/*      */           
/* 1405 */           bom.setStreetDateOnBom(null);
/*      */         } 
/*      */ 
/*      */         
/* 1409 */         int unitsInt = 0;
/*      */         
/*      */         try {
/* 1412 */           unitsInt = Integer.parseInt(form.getStringValue("UnitsPerPackage"));
/*      */         }
/* 1414 */         catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */         
/* 1418 */         bom.setUnitsPerKG(unitsInt);
/*      */ 
/*      */         
/* 1421 */         String runString = form.getStringValue("Runtime");
/* 1422 */         bom.setRunTime(runString);
/*      */ 
/*      */         
/* 1425 */         bom.setUseShrinkWrap(((FormCheckBox)form.getElement("UseNoShrinkWrap")).isChecked());
/*      */         
/* 1427 */         String configString = form.getStringValue("Configuration");
/* 1428 */         if (configString != null) {
/* 1429 */           bom.setConfiguration(configString);
/*      */         }
/*      */         
/* 1432 */         bom.setHasSpineSticker(((FormCheckBox)form.getElement("HasSpineSticker")).isChecked());
/*      */ 
/*      */         
/* 1435 */         String insString = MilestoneHelper_2.cleanText(form.getStringValue("SpecialInstructions"));
/* 1436 */         bom.setSpecialInstructions(insString);
/*      */ 
/*      */         
/* 1439 */         String statusString = form.getStringValue("status");
/* 1440 */         bom.setStatus(statusString);
/*      */ 
/*      */         
/* 1443 */         String upcStr = form.getStringValue("upc");
/* 1444 */         bom.setUpc(upcStr);
/*      */ 
/*      */         
/* 1447 */         String configuration = "";
/* 1448 */         configuration = selection.getSelectionConfig().getSelectionConfigurationAbbreviation();
/*      */ 
/*      */         
/* 1451 */         boolean isCDoverride = false;
/* 1452 */         if (bom.getFormat().equalsIgnoreCase("CDO")) {
/* 1453 */           isCDoverride = true;
/*      */         }
/*      */         
/* 1456 */         if (configuration.equalsIgnoreCase("CAS")) {
/*      */           
/* 1458 */           BomCassetteDetail bomCassetteDetail = bom.getBomCassetteDetail();
/*      */           
/* 1460 */           if (bomCassetteDetail == null)
/*      */           {
/* 1462 */             bomCassetteDetail = new BomCassetteDetail();
/*      */           }
/*      */ 
/*      */           
/* 1466 */           bomCassetteDetail.coStatusIndicator = ((FormCheckBox)form.getElement("PID5")).isChecked();
/*      */ 
/*      */           
/* 1469 */           bomCassetteDetail.coInk1 = form.getStringValue("INK15");
/*      */ 
/*      */           
/* 1472 */           bomCassetteDetail.coInk2 = form.getStringValue("INK25");
/*      */ 
/*      */           
/* 1475 */           bomCassetteDetail.coColor = MilestoneHelper_2.cleanText(form.getStringValue("SEL5"));
/*      */ 
/*      */           
/* 1478 */           bomCassetteDetail.coInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF5"));
/*      */ 
/*      */           
/* 1481 */           bomCassetteDetail.norelcoStatusIndicator = ((FormCheckBox)form.getElement("PID16")).isChecked();
/*      */ 
/*      */           
/* 1484 */           bomCassetteDetail.norelcoColor = MilestoneHelper_2.cleanText(form.getStringValue("SEL16"));
/*      */ 
/*      */           
/* 1487 */           bomCassetteDetail.norelcoInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF16"));
/*      */ 
/*      */           
/* 1490 */           bomCassetteDetail.jCardStatusIndicator = ((FormCheckBox)form.getElement("PID13")).isChecked();
/*      */ 
/*      */ 
/*      */           
/*      */           try {
/* 1495 */             bomCassetteDetail.norelcoSupplierId = Integer.parseInt(form.getStringValue("SID16"));
/*      */           }
/* 1497 */           catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           try {
/* 1503 */             bomCassetteDetail.coParSupplierId = Integer.parseInt(form.getStringValue("SID5"));
/*      */           }
/* 1505 */           catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           try {
/* 1511 */             bomCassetteDetail.jCardSupplierId = Integer.parseInt(form.getStringValue("SID13"));
/*      */           }
/* 1513 */           catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1518 */           bomCassetteDetail.jCardInk1 = form.getStringValue("INK113");
/*      */ 
/*      */           
/* 1521 */           bomCassetteDetail.jCardInk2 = form.getStringValue("INK213");
/*      */ 
/*      */           
/* 1524 */           bomCassetteDetail.jCardPanels = MilestoneHelper_2.cleanText(form.getStringValue("SEL13"));
/*      */ 
/*      */           
/* 1527 */           bomCassetteDetail.jCardInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF13"));
/*      */ 
/*      */           
/* 1530 */           bomCassetteDetail.uCardStatusIndicator = ((FormCheckBox)form.getElement("PID24")).isChecked();
/*      */ 
/*      */ 
/*      */           
/*      */           try {
/* 1535 */             bomCassetteDetail.uCardSupplierId = Integer.parseInt(form.getStringValue("SID24"));
/*      */           }
/* 1537 */           catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1542 */           bomCassetteDetail.uCardInk1 = form.getStringValue("INK124");
/*      */ 
/*      */           
/* 1545 */           bomCassetteDetail.uCardInk2 = form.getStringValue("INK224");
/*      */ 
/*      */           
/* 1548 */           bomCassetteDetail.uCardPanels = MilestoneHelper_2.cleanText(form.getStringValue("SEL24"));
/*      */ 
/*      */           
/* 1551 */           bomCassetteDetail.uCardInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF24"));
/*      */ 
/*      */           
/* 1554 */           bomCassetteDetail.oCardStatusIndicator = ((FormCheckBox)form.getElement("PID17")).isChecked();
/*      */ 
/*      */ 
/*      */           
/*      */           try {
/* 1559 */             bomCassetteDetail.oCardSupplierId = Integer.parseInt(form.getStringValue("SID17"));
/*      */           }
/* 1561 */           catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1566 */           bomCassetteDetail.oCardInk1 = form.getStringValue("INK117");
/*      */ 
/*      */           
/* 1569 */           bomCassetteDetail.oCardInk2 = form.getStringValue("INK217");
/*      */ 
/*      */           
/* 1572 */           bomCassetteDetail.oCardInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF17"));
/*      */ 
/*      */           
/* 1575 */           bomCassetteDetail.stickerOneCardStatusIndicator = ((FormCheckBox)form.getElement("PID21")).isChecked();
/*      */ 
/*      */ 
/*      */           
/*      */           try {
/* 1580 */             bomCassetteDetail.stickerOneCardSupplierId = Integer.parseInt(form.getStringValue("SID21"));
/*      */           }
/* 1582 */           catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1587 */           bomCassetteDetail.stickerOneCardInk1 = form.getStringValue("INK121");
/*      */ 
/*      */           
/* 1590 */           bomCassetteDetail.stickerOneCardInk2 = form.getStringValue("INK221");
/*      */ 
/*      */           
/* 1593 */           bomCassetteDetail.stickerOneCardPlaces = MilestoneHelper_2.cleanText(form.getStringValue("SEL21"));
/*      */ 
/*      */           
/* 1596 */           bomCassetteDetail.stickerOneCardInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF21"));
/*      */ 
/*      */           
/* 1599 */           bomCassetteDetail.stickerTwoCardStatusIndicator = ((FormCheckBox)form.getElement("PID22")).isChecked();
/*      */ 
/*      */ 
/*      */           
/*      */           try {
/* 1604 */             bomCassetteDetail.stickerTwoCardSupplierId = Integer.parseInt(form.getStringValue("SID22"));
/*      */           }
/* 1606 */           catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1611 */           bomCassetteDetail.stickerTwoCardInk1 = form.getStringValue("INK122");
/*      */ 
/*      */           
/* 1614 */           bomCassetteDetail.stickerTwoCardInk2 = form.getStringValue("INK222");
/*      */ 
/*      */           
/* 1617 */           bomCassetteDetail.stickerTwoCardPlaces = MilestoneHelper_2.cleanText(form.getStringValue("SEL22"));
/*      */ 
/*      */           
/* 1620 */           bomCassetteDetail.stickerTwoCardInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF22"));
/*      */ 
/*      */           
/* 1623 */           bomCassetteDetail.otherStatusIndicator = ((FormCheckBox)form.getElement("PID18")).isChecked();
/*      */ 
/*      */ 
/*      */           
/*      */           try {
/* 1628 */             bomCassetteDetail.otherSupplierId = Integer.parseInt(form.getStringValue("SID18"));
/*      */           }
/* 1630 */           catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1635 */           bomCassetteDetail.otherInk1 = form.getStringValue("INK118");
/*      */ 
/*      */           
/* 1638 */           bomCassetteDetail.otherInk2 = form.getStringValue("INK218");
/*      */ 
/*      */           
/* 1641 */           bomCassetteDetail.otherInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF18"));
/*      */           
/* 1643 */           bom.setBomCassetteDetail(bomCassetteDetail);
/*      */         
/*      */         }
/* 1646 */         else if (configuration.equalsIgnoreCase("VIN")) {
/*      */           
/* 1648 */           BomVinylDetail bomVinylDetail = bom.getBomVinylDetail();
/*      */           
/* 1650 */           if (bomVinylDetail == null)
/*      */           {
/* 1652 */             bomVinylDetail = new BomVinylDetail();
/*      */           }
/*      */ 
/*      */           
/* 1656 */           bomVinylDetail.recordStatusIndicator = ((FormCheckBox)form.getElement("PID19")).isChecked();
/*      */ 
/*      */ 
/*      */           
/*      */           try {
/* 1661 */             bomVinylDetail.recordSupplierId = Integer.parseInt(form.getStringValue("SID19"));
/*      */           }
/* 1663 */           catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1668 */           bomVinylDetail.recordColor = MilestoneHelper_2.cleanText(form.getStringValue("SEL19"));
/*      */ 
/*      */           
/* 1671 */           bomVinylDetail.recordInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF19"));
/*      */ 
/*      */           
/* 1674 */           bomVinylDetail.labelStatusIndicator = ((FormCheckBox)form.getElement("PID14")).isChecked();
/*      */ 
/*      */ 
/*      */           
/*      */           try {
/* 1679 */             bomVinylDetail.labelSupplierId = Integer.parseInt(form.getStringValue("SID14"));
/*      */           }
/* 1681 */           catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1686 */           bomVinylDetail.labelInk1 = form.getStringValue("INK114");
/*      */ 
/*      */           
/* 1689 */           bomVinylDetail.labelInk2 = form.getStringValue("INK214");
/*      */ 
/*      */           
/* 1692 */           bomVinylDetail.labelInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF14"));
/*      */ 
/*      */           
/* 1695 */           bomVinylDetail.sleeveStatusIndicator = ((FormCheckBox)form.getElement("PID20")).isChecked();
/*      */ 
/*      */ 
/*      */           
/*      */           try {
/* 1700 */             bomVinylDetail.sleeveSupplierId = Integer.parseInt(form.getStringValue("SID20"));
/*      */           }
/* 1702 */           catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1707 */           bomVinylDetail.sleeveInk1 = form.getStringValue("INK120");
/*      */ 
/*      */           
/* 1710 */           bomVinylDetail.sleeveInk2 = form.getStringValue("INK220");
/*      */ 
/*      */           
/* 1713 */           bomVinylDetail.sleeveInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF20"));
/*      */ 
/*      */           
/* 1716 */           bomVinylDetail.jacketStatusIndicator = ((FormCheckBox)form.getElement("PID11")).isChecked();
/*      */ 
/*      */ 
/*      */           
/*      */           try {
/* 1721 */             bomVinylDetail.jacketSupplierId = Integer.parseInt(form.getStringValue("SID11"));
/*      */           }
/* 1723 */           catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1728 */           bomVinylDetail.jacketInk1 = form.getStringValue("INK111");
/*      */ 
/*      */           
/* 1731 */           bomVinylDetail.jacketInk2 = form.getStringValue("INK211");
/*      */ 
/*      */           
/* 1734 */           bomVinylDetail.jacketInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF11"));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1741 */           bomVinylDetail.insertStatusIndicator = ((FormCheckBox)form.getElement("PID33")).isChecked();
/*      */ 
/*      */ 
/*      */           
/*      */           try {
/* 1746 */             bomVinylDetail.insertSupplierId = Integer.parseInt(form.getStringValue("SID33"));
/*      */           }
/* 1748 */           catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1753 */           bomVinylDetail.insertInk1 = form.getStringValue("INK133");
/*      */ 
/*      */           
/* 1756 */           bomVinylDetail.insertInk2 = form.getStringValue("INK233");
/*      */ 
/*      */           
/* 1759 */           bomVinylDetail.insertInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF33"));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1768 */           bomVinylDetail.stickerOneStatusIndicator = ((FormCheckBox)form.getElement("PID21")).isChecked();
/*      */ 
/*      */ 
/*      */           
/*      */           try {
/* 1773 */             bomVinylDetail.stickerOneSupplierId = Integer.parseInt(form.getStringValue("SID21"));
/*      */           }
/* 1775 */           catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1780 */           bomVinylDetail.stickerOneInk1 = form.getStringValue("INK121");
/*      */ 
/*      */           
/* 1783 */           bomVinylDetail.stickerOneInk2 = form.getStringValue("INK221");
/*      */ 
/*      */           
/* 1786 */           bomVinylDetail.stickerOnePlaces = MilestoneHelper_2.cleanText(form.getStringValue("SEL21"));
/*      */ 
/*      */           
/* 1789 */           bomVinylDetail.stickerOneInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF21"));
/*      */ 
/*      */           
/* 1792 */           bomVinylDetail.stickerTwoStatusIndicator = ((FormCheckBox)form.getElement("PID22")).isChecked();
/*      */ 
/*      */ 
/*      */           
/*      */           try {
/* 1797 */             bomVinylDetail.stickerTwoSupplierId = Integer.parseInt(form.getStringValue("SID22"));
/*      */           }
/* 1799 */           catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1804 */           bomVinylDetail.stickerTwoInk1 = form.getStringValue("INK122");
/*      */ 
/*      */           
/* 1807 */           bomVinylDetail.stickerTwoInk2 = form.getStringValue("INK222");
/*      */ 
/*      */           
/* 1810 */           bomVinylDetail.stickerTwoPlaces = MilestoneHelper_2.cleanText(form.getStringValue("SEL22"));
/*      */ 
/*      */           
/* 1813 */           bomVinylDetail.stickerTwoInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF22"));
/*      */ 
/*      */           
/* 1816 */           bomVinylDetail.otherStatusIndicator = ((FormCheckBox)form.getElement("PID18")).isChecked();
/*      */ 
/*      */ 
/*      */           
/*      */           try {
/* 1821 */             bomVinylDetail.otherSupplierId = Integer.parseInt(form.getStringValue("SID18"));
/*      */           }
/* 1823 */           catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1828 */           bomVinylDetail.otherInk1 = form.getStringValue("INK118");
/*      */ 
/*      */           
/* 1831 */           bomVinylDetail.otherInk2 = form.getStringValue("INK218");
/*      */ 
/*      */           
/* 1834 */           bomVinylDetail.otherInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF18"));
/*      */           
/* 1836 */           bom.setBomVinylDetail(bomVinylDetail);
/*      */         } else {
/*      */           BomDiskDetail bomDiskDetail1;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1843 */           if (configuration.equalsIgnoreCase("DVV") && !isCDoverride) {
/* 1844 */             bomDiskDetail1 = bom.getBomDVDDetail();
/*      */           } else {
/* 1846 */             bomDiskDetail1 = bom.getBomDiskDetail();
/*      */           } 
/*      */           
/* 1849 */           if (bomDiskDetail1 == null && configuration.equalsIgnoreCase("DVV") && 
/* 1850 */             !isCDoverride) {
/* 1851 */             bomDiskDetail1 = new BomDVDDetail();
/* 1852 */           } else if (bomDiskDetail1 == null && (!configuration.equalsIgnoreCase("DVV") || 
/* 1853 */             isCDoverride)) {
/* 1854 */             bomDiskDetail1 = new BomDiskDetail();
/*      */           } 
/*      */           
/* 1857 */           if (configuration.equalsIgnoreCase("DVV") && !isCDoverride) {
/*      */ 
/*      */ 
/*      */             
/* 1861 */             ((BomDVDDetail)bomDiskDetail1).wrapStatusIndicator = ((FormCheckBox)form
/* 1862 */               .getElement("PID25")).isChecked();
/*      */             
/*      */             try {
/* 1865 */               ((BomDVDDetail)bomDiskDetail1).wrapSupplierId = Integer.parseInt(form
/* 1866 */                   .getStringValue("SID25"));
/*      */             }
/* 1868 */             catch (Exception exception) {}
/*      */ 
/*      */             
/* 1871 */             ((BomDVDDetail)bomDiskDetail1).wrapInk1 = form.getStringValue("INK125");
/*      */             
/* 1873 */             ((BomDVDDetail)bomDiskDetail1).wrapInk2 = form.getStringValue("INK225");
/*      */             
/* 1875 */             ((BomDVDDetail)bomDiskDetail1).wrapInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF25"));
/*      */ 
/*      */ 
/*      */             
/* 1879 */             ((BomDVDDetail)bomDiskDetail1).dvdStatusIndicator = ((FormCheckBox)form
/* 1880 */               .getElement("PID26")).isChecked();
/*      */             
/* 1882 */             ((BomDVDDetail)bomDiskDetail1).dvdInk1 = form.getStringValue("INK126");
/*      */             
/* 1884 */             ((BomDVDDetail)bomDiskDetail1).dvdInk2 = form.getStringValue("INK226");
/*      */             
/* 1886 */             ((BomDVDDetail)bomDiskDetail1).dvdSelectionInfo = MilestoneHelper_2.cleanText(form.getStringValue("SEL26"));
/*      */             
/* 1888 */             ((BomDVDDetail)bomDiskDetail1).dvdInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF26"));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1895 */             ((BomDVDDetail)bomDiskDetail1).bluRayStatusIndicator = ((FormCheckBox)form
/* 1896 */               .getElement("PID32")).isChecked();
/*      */             
/* 1898 */             ((BomDVDDetail)bomDiskDetail1).bluRayInk1 = form.getStringValue("INK132");
/*      */             
/* 1900 */             ((BomDVDDetail)bomDiskDetail1).bluRayInk2 = form.getStringValue("INK232");
/*      */             
/* 1902 */             ((BomDVDDetail)bomDiskDetail1).bluRaySelectionInfo = MilestoneHelper_2.cleanText(form.getStringValue("SEL32"));
/*      */             
/* 1904 */             ((BomDVDDetail)bomDiskDetail1).bluRayInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF32"));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1910 */             ((BomDVDDetail)bomDiskDetail1).discSelectionInfo = MilestoneHelper_2.cleanText(form.getStringValue("SELDISC"));
/*      */           } 
/*      */ 
/*      */           
/* 1914 */           bomDiskDetail1.discStatusIndicator = ((FormCheckBox)form.getElement("PID7")).isChecked();
/*      */ 
/*      */ 
/*      */           
/*      */           try {
/* 1919 */             bomDiskDetail1.diskSupplierId = Integer.parseInt(form.getStringValue("SID7"));
/*      */           }
/* 1921 */           catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */           
/* 1925 */           bomDiskDetail1.discInk1 = form.getStringValue("INK17");
/*      */ 
/*      */           
/* 1928 */           bomDiskDetail1.discInk2 = form.getStringValue("INK27");
/*      */ 
/*      */           
/* 1931 */           bomDiskDetail1.discInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF7"));
/*      */ 
/*      */           
/* 1934 */           bomDiskDetail1.jewelStatusIndicator = ((FormCheckBox)form.getElement("PID12")).isChecked();
/*      */ 
/*      */           
/* 1937 */           bomDiskDetail1.jewelColor = MilestoneHelper_2.cleanText(form.getStringValue("SEL12"));
/*      */ 
/*      */           
/* 1940 */           bomDiskDetail1.jewelInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF12"));
/*      */ 
/*      */           
/* 1943 */           bomDiskDetail1.trayStatusIndicator = ((FormCheckBox)form.getElement("PID23")).isChecked();
/*      */ 
/*      */           
/* 1946 */           bomDiskDetail1.trayColor = MilestoneHelper_2.cleanText(form.getStringValue("SEL23"));
/*      */ 
/*      */           
/* 1949 */           bomDiskDetail1.trayInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF23"));
/*      */ 
/*      */           
/* 1952 */           bomDiskDetail1.inlayStatusIndicator = ((FormCheckBox)form.getElement("PID10")).isChecked();
/*      */ 
/*      */ 
/*      */           
/*      */           try {
/* 1957 */             bomDiskDetail1.inlaySupplierId = Integer.parseInt(form.getStringValue("SID10"));
/*      */           }
/* 1959 */           catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1964 */           bomDiskDetail1.inlayInk1 = form.getStringValue("INK110");
/*      */ 
/*      */           
/* 1967 */           bomDiskDetail1.inlayInk2 = form.getStringValue("INK210");
/*      */ 
/*      */           
/* 1970 */           bomDiskDetail1.inlayInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF10"));
/*      */ 
/*      */           
/* 1973 */           bomDiskDetail1.frontStatusIndicator = ((FormCheckBox)form.getElement("PID9")).isChecked();
/*      */ 
/*      */ 
/*      */           
/*      */           try {
/* 1978 */             bomDiskDetail1.frontSupplierId = Integer.parseInt(form.getStringValue("SID9"));
/*      */           }
/* 1980 */           catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1985 */           bomDiskDetail1.frontInk1 = form.getStringValue("INK19");
/*      */ 
/*      */           
/* 1988 */           bomDiskDetail1.frontInk2 = form.getStringValue("INK29");
/*      */ 
/*      */           
/* 1991 */           bomDiskDetail1.frontInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF9"));
/*      */ 
/*      */           
/* 1994 */           bomDiskDetail1.folderStatusIndicator = ((FormCheckBox)form.getElement("PID8")).isChecked();
/*      */ 
/*      */ 
/*      */           
/*      */           try {
/* 1999 */             bomDiskDetail1.folderSupplierId = Integer.parseInt(form.getStringValue("SID8"));
/*      */           }
/* 2001 */           catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2006 */           bomDiskDetail1.folderInk1 = form.getStringValue("INK18");
/*      */ 
/*      */           
/* 2009 */           bomDiskDetail1.folderInk2 = form.getStringValue("INK28");
/*      */ 
/*      */           
/* 2012 */           bomDiskDetail1.folderPages = MilestoneHelper_2.cleanText(form.getStringValue("SEL8"));
/*      */ 
/*      */           
/* 2015 */           bomDiskDetail1.folderInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF8"));
/*      */ 
/*      */           
/* 2018 */           bomDiskDetail1.bookletStatusIndicator = ((FormCheckBox)form.getElement("PID1")).isChecked();
/*      */ 
/*      */ 
/*      */           
/*      */           try {
/* 2023 */             bomDiskDetail1.bookletSupplierId = Integer.parseInt(form.getStringValue("SID1"));
/*      */           }
/* 2025 */           catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2030 */           bomDiskDetail1.bookletInk1 = form.getStringValue("INK11");
/*      */ 
/*      */           
/* 2033 */           bomDiskDetail1.bookletInk2 = form.getStringValue("INK21");
/*      */ 
/*      */           
/* 2036 */           bomDiskDetail1.bookletPages = MilestoneHelper_2.cleanText(form.getStringValue("SEL1"));
/*      */ 
/*      */           
/* 2039 */           bomDiskDetail1.bookletInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF1"));
/*      */ 
/*      */           
/* 2042 */           bomDiskDetail1.brcStatusIndicator = ((FormCheckBox)form.getElement("PID4")).isChecked();
/*      */ 
/*      */ 
/*      */           
/*      */           try {
/* 2047 */             bomDiskDetail1.brcSupplierId = Integer.parseInt(form.getStringValue("SID4"));
/*      */           }
/* 2049 */           catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2054 */           bomDiskDetail1.brcInk1 = form.getStringValue("INK14");
/*      */ 
/*      */           
/* 2057 */           bomDiskDetail1.brcInk2 = form.getStringValue("INK24");
/*      */ 
/*      */           
/* 2060 */           bomDiskDetail1.brcSize = MilestoneHelper_2.cleanText(form.getStringValue("SEL4"));
/*      */ 
/*      */           
/* 2063 */           bomDiskDetail1.brcInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF4"));
/*      */ 
/*      */           
/* 2066 */           bomDiskDetail1.miniStatusIndicator = ((FormCheckBox)form.getElement("PID15")).isChecked();
/*      */ 
/*      */ 
/*      */           
/*      */           try {
/* 2071 */             bomDiskDetail1.miniSupplierId = Integer.parseInt(form.getStringValue("SID15"));
/*      */           }
/* 2073 */           catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2078 */           bomDiskDetail1.miniInk1 = form.getStringValue("INK115");
/*      */ 
/*      */           
/* 2081 */           bomDiskDetail1.miniInk2 = form.getStringValue("INK215");
/*      */ 
/*      */           
/* 2084 */           bomDiskDetail1.miniInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF15"));
/*      */ 
/*      */           
/* 2087 */           bomDiskDetail1.digiPakStatusIndicator = ((FormCheckBox)form.getElement("PID6")).isChecked();
/*      */ 
/*      */ 
/*      */           
/*      */           try {
/* 2092 */             bomDiskDetail1.digiPakSupplierId = Integer.parseInt(form.getStringValue("SID6"));
/*      */           }
/* 2094 */           catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2099 */           bomDiskDetail1.digiPakInk1 = form.getStringValue("INK16");
/*      */ 
/*      */           
/* 2102 */           bomDiskDetail1.digiPakInk2 = form.getStringValue("INK26");
/*      */ 
/*      */           
/* 2105 */           bomDiskDetail1.digiPakTray = MilestoneHelper_2.cleanText(form.getStringValue("SEL6"));
/*      */ 
/*      */           
/* 2108 */           bomDiskDetail1.digiPakInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF6"));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2114 */           bomDiskDetail1.softPakStatusIndicator = ((FormCheckBox)form.getElement("PID31")).isChecked();
/*      */ 
/*      */ 
/*      */           
/*      */           try {
/* 2119 */             bomDiskDetail1.softPakSupplierId = Integer.parseInt(form.getStringValue("SID31"));
/*      */           }
/* 2121 */           catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2127 */           bomDiskDetail1.softPakInk1 = form.getStringValue("INK131");
/*      */ 
/*      */           
/* 2130 */           bomDiskDetail1.softPakInk2 = form.getStringValue("INK231");
/*      */ 
/*      */           
/* 2133 */           bomDiskDetail1.softPakTray = MilestoneHelper_2.cleanText(form.getStringValue("SEL31"));
/*      */ 
/*      */           
/* 2136 */           bomDiskDetail1.softPakInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF31"));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2144 */           bomDiskDetail1.stickerOneStatusIndicator = ((FormCheckBox)form.getElement("PID21")).isChecked();
/*      */ 
/*      */ 
/*      */           
/*      */           try {
/* 2149 */             bomDiskDetail1.stickerOneSupplierId = Integer.parseInt(form.getStringValue("SID21"));
/*      */           }
/* 2151 */           catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2156 */           bomDiskDetail1.stickerOneInk1 = form.getStringValue("INK121");
/*      */ 
/*      */           
/* 2159 */           bomDiskDetail1.stickerOneInk2 = form.getStringValue("INK221");
/*      */ 
/*      */           
/* 2162 */           bomDiskDetail1.stickerOnePlaces = MilestoneHelper_2.cleanText(form.getStringValue("SEL21"));
/*      */ 
/*      */           
/* 2165 */           bomDiskDetail1.stickerOneInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF21"));
/*      */ 
/*      */           
/* 2168 */           bomDiskDetail1.stickerTwoStatusIndicator = ((FormCheckBox)form.getElement("PID22")).isChecked();
/*      */ 
/*      */ 
/*      */           
/*      */           try {
/* 2173 */             bomDiskDetail1.stickerTwoSupplierId = Integer.parseInt(form.getStringValue("SID22"));
/*      */           }
/* 2175 */           catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2180 */           bomDiskDetail1.stickerTwoInk1 = form.getStringValue("INK122");
/*      */ 
/*      */           
/* 2183 */           bomDiskDetail1.stickerTwoInk2 = form.getStringValue("INK222");
/*      */ 
/*      */           
/* 2186 */           bomDiskDetail1.stickerTwoPlaces = MilestoneHelper_2.cleanText(form.getStringValue("SEL22"));
/*      */ 
/*      */           
/* 2189 */           bomDiskDetail1.stickerTwoInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF22"));
/*      */ 
/*      */           
/* 2192 */           bomDiskDetail1.bookStatusIndicator = ((FormCheckBox)form.getElement("PID2")).isChecked();
/*      */ 
/*      */ 
/*      */           
/*      */           try {
/* 2197 */             bomDiskDetail1.bookSupplierId = Integer.parseInt(form.getStringValue("SID2"));
/*      */           }
/* 2199 */           catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2204 */           bomDiskDetail1.bookInk1 = form.getStringValue("INK12");
/*      */ 
/*      */           
/* 2207 */           bomDiskDetail1.bookInk2 = form.getStringValue("INK22");
/*      */ 
/*      */           
/* 2210 */           bomDiskDetail1.bookPages = MilestoneHelper_2.cleanText(form.getStringValue("SEL2"));
/*      */ 
/*      */           
/* 2213 */           bomDiskDetail1.bookInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF2"));
/*      */ 
/*      */           
/* 2216 */           bomDiskDetail1.boxStatusIndicator = ((FormCheckBox)form.getElement("PID3")).isChecked();
/*      */ 
/*      */ 
/*      */           
/*      */           try {
/* 2221 */             bomDiskDetail1.boxSupplierId = Integer.parseInt(form.getStringValue("SID3"));
/*      */           }
/* 2223 */           catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2228 */           bomDiskDetail1.boxInk1 = form.getStringValue("INK13");
/*      */ 
/*      */           
/* 2231 */           bomDiskDetail1.boxInk2 = form.getStringValue("INK23");
/*      */ 
/*      */           
/* 2234 */           bomDiskDetail1.boxSizes = MilestoneHelper_2.cleanText(form.getStringValue("SEL3"));
/*      */ 
/*      */           
/* 2237 */           bomDiskDetail1.boxInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF3"));
/*      */ 
/*      */           
/* 2240 */           bomDiskDetail1.otherStatusIndicator = ((FormCheckBox)form.getElement("PID18")).isChecked();
/*      */ 
/*      */ 
/*      */           
/*      */           try {
/* 2245 */             bomDiskDetail1.otherSupplierId = Integer.parseInt(form.getStringValue("SID18"));
/*      */           }
/* 2247 */           catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2252 */           bomDiskDetail1.otherInk1 = form.getStringValue("INK118");
/*      */ 
/*      */           
/* 2255 */           bomDiskDetail1.otherInk2 = form.getStringValue("INK218");
/*      */ 
/*      */           
/* 2258 */           bomDiskDetail1.otherInfo = MilestoneHelper_2.cleanText(form.getStringValue("INF18"));
/*      */           
/* 2260 */           if (configuration.equalsIgnoreCase("DVV") && !isCDoverride) {
/* 2261 */             bom.setBomDVDDetail((BomDVDDetail)bomDiskDetail1);
/*      */           } else {
/* 2263 */             bom.setBomDiskDetail(bomDiskDetail1);
/*      */           } 
/*      */         } 
/* 2266 */         Form emailDistForm = form;
/*      */ 
/*      */ 
/*      */         
/* 2270 */         FormValidation formValidation = form.validate();
/* 2271 */         if (formValidation.isGood()) {
/*      */           
/* 2273 */           Bom saveBom = SelectionManager.getInstance().saveBom(bom, selection, user.getUserId());
/*      */           
/* 2275 */           context.putSessionValue("Bom", saveBom);
/* 2276 */           form.addElement(new FormHidden("copyPaste", "copy", true));
/* 2277 */           form.addElement(new FormHidden("cmd", "bom-edit-save", true));
/* 2278 */           context.putDelivery("Form", form);
/*      */ 
/*      */           
/* 2281 */           if (finalFlag) {
/* 2282 */             String lastUpdatedDate = "";
/* 2283 */             String lastUpdatedBy = "";
/*      */             
/* 2285 */             if (saveBom.getModifiedOn() != null) {
/* 2286 */               lastUpdatedDate = MilestoneHelper.getCustomFormatedDate(saveBom.getModifiedOn(), "M/d/yyyy hh:mm:ss a 'ET'");
/*      */             }
/* 2288 */             if (UserManager.getInstance().getUser(saveBom.getModifiedBy()) != null) {
/* 2289 */               lastUpdatedBy = UserManager.getInstance().getUser(saveBom.getModifiedBy()).getName();
/*      */             }
/*      */ 
/*      */ 
/*      */             
/* 2294 */             if (sendEmail && 
/* 2295 */               !context.getCommand().equals("selection-send-cancel") && 
/* 2296 */               EmailDistribution.putEmailBody(emailDistForm, context, selection, lastUpdatedDate, lastUpdatedBy, "BOM", messageObject)) {
/*      */ 
/*      */               
/*      */               try {
/*      */                 
/* 2301 */                 messageObject.bomObj = (Bom)saveBom.clone();
/* 2302 */               } catch (CloneNotSupportedException ce) {
/* 2303 */                 messageObject.bomObj = saveBom;
/*      */               } 
/* 2305 */               print(dispatcher, context, command, 2, true, messageObject);
/* 2306 */               EmailDistribution.sendEmail(dispatcher, context, "", messageObject);
/* 2307 */               EmailDistribution.removeSessionValues(context);
/*      */             } 
/*      */           } 
/*      */           
/* 2311 */           if (!command.equalsIgnoreCase("selectionSave")) {
/* 2312 */             return edit(dispatcher, context, command);
/*      */           }
/* 2314 */           return true;
/*      */         } 
/*      */ 
/*      */         
/* 2318 */         context.putDelivery("FormValidation", formValidation);
/*      */       } 
/*      */       
/* 2321 */       form.addElement(new FormHidden("OrderBy", "", true));
/* 2322 */       form.addElement(new FormHidden("cmd", "bom-edit-save", true));
/* 2323 */       context.putDelivery("Form", form);
/*      */       
/* 2325 */       if (!command.equalsIgnoreCase("selectionSave")) {
/* 2326 */         return edit(dispatcher, context, command);
/*      */       }
/* 2328 */       return true;
/*      */     } 
/*      */ 
/*      */     
/* 2332 */     context.putDelivery("AlertMessage", "The record is unavailable for editing at this time.  Please refresh your screen and edit it at a later time.");
/*      */ 
/*      */     
/* 2335 */     form.addElement(new FormHidden("cmd", "bom-edit-save", true));
/* 2336 */     context.putDelivery("Form", form);
/* 2337 */     if (!command.equalsIgnoreCase("selectionSave")) {
/* 2338 */       return edit(dispatcher, context, command);
/*      */     }
/* 2340 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   protected Form buildDisk(Form bomDisk, Selection selection, Bom bom, BomDiskDetail bomDiskDetail) {
/*      */     Vector sel12;
/* 2346 */     boolean isCDoverride = false;
/* 2347 */     if (bom.getFormat().equalsIgnoreCase("CDO")) {
/* 2348 */       isCDoverride = true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2354 */     String typeFlag = "0";
/* 2355 */     int ctrlSize = 50;
/* 2356 */     if (selection.getSelectionConfig().getSelectionConfigurationAbbreviation().equalsIgnoreCase("DVV") && 
/* 2357 */       !isCDoverride) {
/*      */       
/* 2359 */       typeFlag = "1,2";
/* 2360 */       ctrlSize = 20;
/*      */     } else {
/* 2362 */       typeFlag = "0,1";
/* 2363 */       ctrlSize = 50;
/*      */     } 
/*      */ 
/*      */     
/* 2367 */     FormCheckBox PID7 = new FormCheckBox("PID7", "on", "", false, bomDiskDetail.discStatusIndicator);
/* 2368 */     PID7.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2369 */     PID7.setTabIndex(14);
/* 2370 */     PID7.setId("Disc");
/* 2371 */     bomDisk.addElement(PID7);
/*      */ 
/*      */     
/* 2374 */     FormTextField INK17 = new FormTextField("INK17", bomDiskDetail.getDiscInk1(), false, 2, 2);
/* 2375 */     INK17.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2376 */     INK17.setTabIndex(15);
/* 2377 */     INK17.setId("Disc / Ink");
/* 2378 */     bomDisk.addElement(INK17);
/*      */ 
/*      */     
/* 2381 */     FormTextField INK27 = new FormTextField("INK27", bomDiskDetail.getDiscInk2(), false, 2, 2);
/* 2382 */     INK27.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2383 */     INK27.setTabIndex(16);
/* 2384 */     INK27.setId("Disc / Ink");
/* 2385 */     bomDisk.addElement(INK27);
/*      */ 
/*      */     
/* 2388 */     FormTextField INF7 = new FormTextField("INF7", bomDiskDetail.getDiscInfo(), false, ctrlSize, 120);
/* 2389 */     INF7.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2390 */     INF7.setTabIndex(17);
/* 2391 */     INF7.setId("Disc / Additional Information");
/* 2392 */     bomDisk.addElement(INF7);
/*      */ 
/*      */     
/* 2395 */     FormCheckBox PID12 = new FormCheckBox("PID12", "on", "", false, bomDiskDetail.jewelStatusIndicator);
/* 2396 */     PID12.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2397 */     PID12.setId("PID12");
/* 2398 */     PID12.setTabIndex(18);
/* 2399 */     PID12.setId("Jewel Box");
/* 2400 */     bomDisk.addElement(PID12);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2405 */     if (selection.getSelectionConfig().getSelectionConfigurationAbbreviation().equalsIgnoreCase("DVV") && 
/* 2406 */       !isCDoverride) {
/* 2407 */       sel12 = Cache.getInstance().getLookupDetailValuesFromDatabase(63);
/*      */     } else {
/* 2409 */       sel12 = Cache.getInstance().getLookupDetailValuesFromDatabase(41);
/*      */     } 
/*      */     
/* 2412 */     FormDropDownMenu SEL12 = MilestoneHelper.getLookupDropDownBom("SEL12", sel12, bomDiskDetail.getJewelColor(), false, true);
/* 2413 */     SEL12.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2414 */     SEL12.setTabIndex(19);
/* 2415 */     SEL12.setId("Jewel Box / Additional Information");
/* 2416 */     bomDisk.addElement(SEL12);
/*      */ 
/*      */     
/* 2419 */     FormTextField INF12 = new FormTextField("INF12", bomDiskDetail.getJewelInfo(), false, 20, 120);
/* 2420 */     INF12.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2421 */     INF12.setTabIndex(20);
/* 2422 */     INF12.setId("Jewel Box / Additional Information");
/* 2423 */     bomDisk.addElement(INF12);
/*      */ 
/*      */     
/* 2426 */     FormCheckBox PID23 = new FormCheckBox("PID23", "on", "", false, bomDiskDetail.trayStatusIndicator);
/* 2427 */     PID23.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2428 */     PID23.setId("PID23");
/* 2429 */     PID23.setTabIndex(30);
/* 2430 */     PID23.setId("Tray");
/* 2431 */     bomDisk.addElement(PID23);
/*      */ 
/*      */     
/* 2434 */     Vector sel23 = Cache.getInstance().getLookupDetailValuesFromDatabase(42);
/* 2435 */     FormDropDownMenu SEL23 = MilestoneHelper.getLookupDropDownBom("SEL23", sel23, bomDiskDetail.getTrayColor(), false, true);
/* 2436 */     SEL23.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2437 */     SEL23.setTabIndex(31);
/* 2438 */     SEL23.setId("Tray / Additional Information");
/* 2439 */     bomDisk.addElement(SEL23);
/*      */ 
/*      */     
/* 2442 */     FormTextField INF23 = new FormTextField("INF23", bomDiskDetail.getTrayInfo(), false, 20, 120);
/* 2443 */     INF23.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2444 */     INF23.setTabIndex(32);
/* 2445 */     INF23.setId("Tray / Additional Information");
/* 2446 */     bomDisk.addElement(INF23);
/*      */ 
/*      */     
/* 2449 */     FormCheckBox PID10 = new FormCheckBox("PID10", "on", "", false, bomDiskDetail.inlayStatusIndicator);
/* 2450 */     PID10.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2451 */     PID10.setId("PID10");
/* 2452 */     PID10.setTabIndex(33);
/* 2453 */     PID10.setId("Inlay");
/* 2454 */     bomDisk.addElement(PID10);
/*      */ 
/*      */     
/* 2457 */     Vector sid10 = MilestoneHelper.getBomSuppliers(10, typeFlag, bomDiskDetail.inlaySupplierId);
/* 2458 */     FormDropDownMenu SID10 = MilestoneHelper.getLookupDropDown("SID10", sid10, String.valueOf(bomDiskDetail.inlaySupplierId), false, false);
/* 2459 */     SID10.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2460 */     SID10.setTabIndex(34);
/* 2461 */     SID10.setId("Inlay / Supplier");
/* 2462 */     bomDisk.addElement(SID10);
/*      */ 
/*      */     
/* 2465 */     Vector sid7 = MilestoneHelper.getBomSuppliers(7, typeFlag);
/* 2466 */     FormDropDownMenu SID7 = MilestoneHelper.getLookupDropDown("SID7", sid7, String.valueOf(bomDiskDetail.diskSupplierId), false, false);
/* 2467 */     SID7.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2468 */     SID7.setTabIndex(34);
/* 2469 */     SID7.setId("Disc");
/* 2470 */     bomDisk.addElement(SID7);
/*      */     
/* 2472 */     Vector sid12 = MilestoneHelper.getBomSuppliers(12, typeFlag);
/* 2473 */     FormDropDownMenu SID12 = MilestoneHelper.getLookupDropDown("SID12", sid12, String.valueOf(bomDiskDetail.jewelStatusIndicator), false, false);
/* 2474 */     SID12.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2475 */     SID12.setTabIndex(34);
/* 2476 */     SID12.setId("Jewel Box");
/* 2477 */     bomDisk.addElement(SID12);
/*      */     
/* 2479 */     Vector sid23 = MilestoneHelper.getBomSuppliers(23, typeFlag);
/* 2480 */     FormDropDownMenu SID23 = MilestoneHelper.getLookupDropDown("SID23", sid23, String.valueOf(bomDiskDetail.trayStatusIndicator), false, false);
/* 2481 */     SID23.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2482 */     SID23.setTabIndex(34);
/* 2483 */     SID23.setId("Tray");
/* 2484 */     bomDisk.addElement(SID23);
/*      */     
/* 2486 */     FormTextField INK110 = new FormTextField("INK110", bomDiskDetail.getInlayInk1(), false, 2, 2);
/* 2487 */     INK110.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2488 */     INK110.setTabIndex(35);
/* 2489 */     INK110.setId("Inlay / Ink");
/* 2490 */     bomDisk.addElement(INK110);
/*      */ 
/*      */     
/* 2493 */     FormTextField INK210 = new FormTextField("INK210", bomDiskDetail.getInlayInk2(), false, 2, 2);
/* 2494 */     INK210.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2495 */     INK210.setTabIndex(36);
/* 2496 */     INK210.setId("Inlay / Ink");
/* 2497 */     bomDisk.addElement(INK210);
/*      */ 
/*      */     
/* 2500 */     FormTextField INF10 = new FormTextField("INF10", bomDiskDetail.getInlayInfo(), false, 50, 120);
/* 2501 */     INF10.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2502 */     INF10.setTabIndex(37);
/* 2503 */     INF10.setId("Inlay / Additional Information");
/* 2504 */     bomDisk.addElement(INF10);
/*      */ 
/*      */     
/* 2507 */     FormCheckBox PID9 = new FormCheckBox("PID9", "on", "", false, bomDiskDetail.frontStatusIndicator);
/* 2508 */     PID9.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2509 */     PID9.setId("PID9");
/* 2510 */     PID9.setTabIndex(38);
/* 2511 */     PID9.setId("Front Insert");
/* 2512 */     bomDisk.addElement(PID9);
/*      */ 
/*      */     
/* 2515 */     Vector sid9 = MilestoneHelper.getBomSuppliers(9, typeFlag, bomDiskDetail.frontSupplierId);
/* 2516 */     FormDropDownMenu SID9 = MilestoneHelper.getLookupDropDown("SID9", sid9, String.valueOf(bomDiskDetail.frontSupplierId), false, false);
/* 2517 */     SID9.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2518 */     SID9.setTabIndex(39);
/* 2519 */     SID9.setId("Front Insert / Supplier");
/* 2520 */     bomDisk.addElement(SID9);
/*      */ 
/*      */     
/* 2523 */     FormTextField INK19 = new FormTextField("INK19", bomDiskDetail.getFrontInk1(), false, 2, 2);
/* 2524 */     INK19.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2525 */     INK19.setTabIndex(40);
/* 2526 */     INK19.setId("Front Insert / Ink");
/* 2527 */     bomDisk.addElement(INK19);
/*      */ 
/*      */     
/* 2530 */     FormTextField INK29 = new FormTextField("INK29", bomDiskDetail.getFrontInk2(), false, 2, 2);
/* 2531 */     INK29.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2532 */     INK29.setTabIndex(41);
/* 2533 */     INK29.setId("Front Insert / Ink");
/* 2534 */     bomDisk.addElement(INK29);
/*      */ 
/*      */     
/* 2537 */     FormTextField INF9 = new FormTextField("INF9", bomDiskDetail.getFrontInfo(), false, 50, 120);
/* 2538 */     INF9.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2539 */     INF9.setTabIndex(42);
/* 2540 */     INF9.setId("Front Insert / Additional Information");
/* 2541 */     bomDisk.addElement(INF9);
/*      */ 
/*      */     
/* 2544 */     FormCheckBox PID8 = new FormCheckBox("PID8", "on", "", false, bomDiskDetail.folderStatusIndicator);
/* 2545 */     PID8.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2546 */     PID8.setId("PID8");
/* 2547 */     PID8.setTabIndex(43);
/* 2548 */     PID8.setId("Folder");
/* 2549 */     bomDisk.addElement(PID8);
/*      */ 
/*      */     
/* 2552 */     Vector sid8 = MilestoneHelper.getBomSuppliers(8, typeFlag, bomDiskDetail.folderSupplierId);
/* 2553 */     FormDropDownMenu SID8 = MilestoneHelper.getLookupDropDown("SID8", sid8, String.valueOf(bomDiskDetail.folderSupplierId), false, false);
/* 2554 */     SID8.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2555 */     SID8.setTabIndex(44);
/* 2556 */     SID8.setId("Folder / Supplier");
/* 2557 */     bomDisk.addElement(SID8);
/*      */ 
/*      */     
/* 2560 */     FormTextField INK18 = new FormTextField("INK18", bomDiskDetail.getFolderInk1(), false, 2, 2);
/* 2561 */     INK18.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2562 */     INK18.setTabIndex(44);
/* 2563 */     INK18.setId("Folder / Ink");
/* 2564 */     bomDisk.addElement(INK18);
/*      */ 
/*      */     
/* 2567 */     FormTextField INK28 = new FormTextField("INK28", bomDiskDetail.getFolderInk2(), false, 2, 2);
/* 2568 */     INK28.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2569 */     INK28.setTabIndex(45);
/* 2570 */     INK28.setId("Folder / Ink");
/* 2571 */     bomDisk.addElement(INK28);
/*      */ 
/*      */     
/* 2574 */     Vector sel8 = Cache.getInstance().getLookupDetailValuesFromDatabase(43);
/* 2575 */     FormDropDownMenu SEL8 = MilestoneHelper.getLookupDropDownBom("SEL8", sel8, bomDiskDetail.getFolderPages(), false, true);
/* 2576 */     SEL8.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2577 */     SEL8.setTabIndex(46);
/* 2578 */     SEL8.setId("Folder / Pages");
/* 2579 */     bomDisk.addElement(SEL8);
/*      */ 
/*      */     
/* 2582 */     FormTextField INF8 = new FormTextField("INF8", bomDiskDetail.getFolderInfo(), false, 20, 120);
/* 2583 */     INF8.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2584 */     INF8.setTabIndex(47);
/* 2585 */     INF8.setId("Folder / Additinal Information");
/* 2586 */     bomDisk.addElement(INF8);
/*      */ 
/*      */     
/* 2589 */     FormCheckBox PID1 = new FormCheckBox("PID1", "on", "", false, bomDiskDetail.bookletStatusIndicator);
/* 2590 */     PID1.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2591 */     PID1.setId("PID1");
/* 2592 */     PID1.setId("Booklet");
/* 2593 */     PID1.setTabIndex(48);
/* 2594 */     bomDisk.addElement(PID1);
/*      */ 
/*      */     
/* 2597 */     Vector sid1 = MilestoneHelper.getBomSuppliers(1, typeFlag, bomDiskDetail.bookletSupplierId);
/* 2598 */     FormDropDownMenu SID1 = MilestoneHelper.getLookupDropDown("SID1", sid1, String.valueOf(bomDiskDetail.bookletSupplierId), false, false);
/* 2599 */     SID1.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2600 */     SID1.setTabIndex(49);
/* 2601 */     SID1.setId("Booklet / Supplier");
/* 2602 */     bomDisk.addElement(SID1);
/*      */ 
/*      */     
/* 2605 */     FormTextField INK11 = new FormTextField("INK11", bomDiskDetail.getBookletInk1(), false, 2, 2);
/* 2606 */     INK11.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2607 */     INK11.setTabIndex(50);
/* 2608 */     INK11.setId("Booklet / Ink");
/* 2609 */     bomDisk.addElement(INK11);
/*      */ 
/*      */     
/* 2612 */     FormTextField INK21 = new FormTextField("INK21", bomDiskDetail.getBookletInk2(), false, 2, 2);
/* 2613 */     INK21.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2614 */     INK21.setTabIndex(51);
/* 2615 */     INK21.setId("Booklet / Ink");
/* 2616 */     bomDisk.addElement(INK21);
/*      */ 
/*      */     
/* 2619 */     Vector sel1 = Cache.getInstance().getLookupDetailValuesFromDatabase(45);
/* 2620 */     FormDropDownMenu SEL1 = MilestoneHelper.getLookupDropDownBom("SEL1", sel1, bomDiskDetail.getBookletPages(), false, true);
/* 2621 */     SEL1.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2622 */     SEL1.setTabIndex(52);
/* 2623 */     SEL1.setId("Booklet / Pages");
/* 2624 */     bomDisk.addElement(SEL1);
/*      */ 
/*      */     
/* 2627 */     FormTextField INF1 = new FormTextField("INF1", bomDiskDetail.getBookletInfo(), false, 20, 120);
/* 2628 */     INF1.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2629 */     INF1.setTabIndex(53);
/* 2630 */     INF1.setId("Booklet / Additional Information");
/* 2631 */     bomDisk.addElement(INF1);
/*      */ 
/*      */     
/* 2634 */     FormCheckBox PID4 = new FormCheckBox("PID4", "on", "", false, bomDiskDetail.brcStatusIndicator);
/* 2635 */     PID4.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2636 */     PID4.setId("PID4");
/* 2637 */     PID4.setTabIndex(54);
/* 2638 */     PID4.setId("BRC Insert");
/* 2639 */     bomDisk.addElement(PID4);
/*      */ 
/*      */     
/* 2642 */     Vector sid4 = MilestoneHelper.getBomSuppliers(4, typeFlag, bomDiskDetail.brcSupplierId);
/* 2643 */     FormDropDownMenu SID4 = MilestoneHelper.getLookupDropDown("SID4", sid4, String.valueOf(bomDiskDetail.brcSupplierId), false, false);
/* 2644 */     SID4.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2645 */     SID4.setTabIndex(55);
/* 2646 */     SID4.setId("BRC Insert / Supplier");
/* 2647 */     bomDisk.addElement(SID4);
/*      */ 
/*      */     
/* 2650 */     FormTextField INK14 = new FormTextField("INK14", bomDiskDetail.getBrcInk1(), false, 2, 2);
/* 2651 */     INK14.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2652 */     INK14.setTabIndex(56);
/* 2653 */     INK14.setId("BRC Insert / Ink");
/* 2654 */     bomDisk.addElement(INK14);
/*      */ 
/*      */     
/* 2657 */     FormTextField INK24 = new FormTextField("INK24", bomDiskDetail.getBrcInk2(), false, 2, 2);
/* 2658 */     INK24.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2659 */     INK24.setTabIndex(57);
/* 2660 */     INK24.setId("BRC Insert / Ink");
/* 2661 */     bomDisk.addElement(INK24);
/*      */ 
/*      */     
/* 2664 */     FormTextField SEL4 = new FormTextField("SEL4", bomDiskDetail.getBrcSize(), false, 10, 20);
/* 2665 */     SEL4.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2666 */     SEL4.setTabIndex(58);
/* 2667 */     SEL4.setId("BRC Insert / Size");
/* 2668 */     bomDisk.addElement(SEL4);
/*      */ 
/*      */     
/* 2671 */     FormTextField INF4 = new FormTextField("INF4", bomDiskDetail.getBrcInfo(), false, 20, 120);
/* 2672 */     INF4.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2673 */     INF4.setTabIndex(59);
/* 2674 */     INF4.setId("BRC Insert / Additional Information");
/* 2675 */     bomDisk.addElement(INF4);
/*      */ 
/*      */     
/* 2678 */     FormCheckBox PID15 = new FormCheckBox("PID15", "on", "", false, bomDiskDetail.miniStatusIndicator);
/* 2679 */     PID15.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2680 */     PID15.setId("PID15");
/* 2681 */     PID15.setTabIndex(60);
/* 2682 */     PID15.setId("Mini Jacket");
/* 2683 */     bomDisk.addElement(PID15);
/*      */ 
/*      */     
/* 2686 */     Vector sid15 = MilestoneHelper.getBomSuppliers(15, typeFlag, bomDiskDetail.miniSupplierId);
/* 2687 */     FormDropDownMenu SID15 = MilestoneHelper.getLookupDropDown("SID15", sid15, String.valueOf(bomDiskDetail.miniSupplierId), false, false);
/* 2688 */     SID15.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2689 */     SID15.setTabIndex(61);
/* 2690 */     SID15.setId("Mini Jacket / Supplier");
/* 2691 */     bomDisk.addElement(SID15);
/*      */ 
/*      */     
/* 2694 */     FormTextField INK115 = new FormTextField("INK115", bomDiskDetail.getMiniInk1(), false, 2, 2);
/* 2695 */     INK115.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2696 */     INK115.setTabIndex(62);
/* 2697 */     INK115.setId("Mini Jacket / Ink");
/* 2698 */     bomDisk.addElement(INK115);
/*      */ 
/*      */     
/* 2701 */     FormTextField INK215 = new FormTextField("INK215", bomDiskDetail.getMiniInk2(), false, 2, 2);
/* 2702 */     INK215.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2703 */     INK215.setTabIndex(63);
/* 2704 */     INK215.setId("Mini Jacket / Ink");
/* 2705 */     bomDisk.addElement(INK215);
/*      */ 
/*      */     
/* 2708 */     FormTextField INF15 = new FormTextField("INF15", bomDiskDetail.getMiniInfo(), false, 20, 120);
/* 2709 */     INF15.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2710 */     INF15.setTabIndex(64);
/* 2711 */     INF15.setId("Mini Jacket / Additional Information");
/* 2712 */     bomDisk.addElement(INF15);
/*      */ 
/*      */     
/* 2715 */     FormCheckBox PID6 = new FormCheckBox("PID6", "on", "", false, bomDiskDetail.digiPakStatusIndicator);
/* 2716 */     PID6.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2717 */     PID6.setId("PID6");
/* 2718 */     PID6.setTabIndex(65);
/* 2719 */     PID6.setId("Digipak");
/* 2720 */     bomDisk.addElement(PID6);
/*      */ 
/*      */     
/* 2723 */     Vector sid6 = MilestoneHelper.getBomSuppliers(6, typeFlag, bomDiskDetail.digiPakSupplierId);
/* 2724 */     FormDropDownMenu SID6 = MilestoneHelper.getLookupDropDown("SID6", sid6, String.valueOf(bomDiskDetail.digiPakSupplierId), false, false);
/* 2725 */     SID6.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2726 */     SID6.setTabIndex(66);
/* 2727 */     SID6.setId("Digipak / Supplier");
/* 2728 */     bomDisk.addElement(SID6);
/*      */ 
/*      */     
/* 2731 */     FormTextField INK16 = new FormTextField("INK16", bomDiskDetail.getDigiPakInk1(), false, 2, 2);
/* 2732 */     INK16.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2733 */     INK16.setTabIndex(67);
/* 2734 */     INK16.setId("Digipak / Ink");
/* 2735 */     bomDisk.addElement(INK16);
/*      */ 
/*      */     
/* 2738 */     FormTextField INK26 = new FormTextField("INK26", bomDiskDetail.getDigiPakInk2(), false, 2, 2);
/* 2739 */     INK26.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2740 */     INK26.setTabIndex(68);
/* 2741 */     INK26.setId("Digipak / Ink");
/* 2742 */     bomDisk.addElement(INK26);
/*      */ 
/*      */     
/* 2745 */     FormTextField SEL6 = new FormTextField("SEL6", bomDiskDetail.getDigiPakTray(), false, 6, 6);
/* 2746 */     SEL6.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2747 */     SEL6.setTabIndex(69);
/* 2748 */     SEL6.setId("Digipak / Tray");
/* 2749 */     bomDisk.addElement(SEL6);
/*      */ 
/*      */     
/* 2752 */     FormTextField INF6 = new FormTextField("INF6", bomDiskDetail.getDigiPakInfo(), false, 20, 120);
/* 2753 */     INF6.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2754 */     INF6.setTabIndex(70);
/* 2755 */     INF6.setId("Digipak / Additional Information");
/* 2756 */     bomDisk.addElement(INF6);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2763 */     FormCheckBox PID31 = new FormCheckBox("PID31", "on", "", false, bomDiskDetail.softPakStatusIndicator);
/* 2764 */     PID6.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2765 */     PID6.setId("PID31");
/* 2766 */     PID6.setTabIndex(71);
/* 2767 */     PID6.setId("Softpak");
/* 2768 */     bomDisk.addElement(PID31);
/*      */ 
/*      */     
/* 2771 */     Vector sid31 = MilestoneHelper.getBomSuppliers(31, typeFlag, bomDiskDetail.softPakSupplierId);
/* 2772 */     FormDropDownMenu SID31 = MilestoneHelper.getLookupDropDown("SID31", sid31, String.valueOf(bomDiskDetail.softPakSupplierId), false, false);
/* 2773 */     SID31.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2774 */     SID31.setTabIndex(72);
/* 2775 */     SID31.setId("Softpak / Supplier");
/* 2776 */     bomDisk.addElement(SID31);
/*      */ 
/*      */     
/* 2779 */     FormTextField INK131 = new FormTextField("INK131", bomDiskDetail.getSoftPakInk1(), false, 2, 2);
/* 2780 */     INK131.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2781 */     INK131.setTabIndex(73);
/* 2782 */     INK131.setId("Softpak / Ink");
/* 2783 */     bomDisk.addElement(INK131);
/*      */ 
/*      */     
/* 2786 */     FormTextField INK231 = new FormTextField("INK231", bomDiskDetail.getSoftPakInk2(), false, 2, 2);
/* 2787 */     INK231.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2788 */     INK231.setTabIndex(74);
/* 2789 */     INK231.setId("Softpak / Ink");
/* 2790 */     bomDisk.addElement(INK231);
/*      */ 
/*      */     
/* 2793 */     FormTextField SEL31 = new FormTextField("SEL31", bomDiskDetail.getSoftPakTray(), false, 6, 6);
/* 2794 */     SEL31.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2795 */     SEL31.setTabIndex(75);
/* 2796 */     SEL31.setId("Softpak / Tray");
/* 2797 */     bomDisk.addElement(SEL31);
/*      */ 
/*      */     
/* 2800 */     FormTextField INF31 = new FormTextField("INF31", bomDiskDetail.getSoftPakInfo(), false, 20, 120);
/* 2801 */     INF31.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2802 */     INF31.setTabIndex(76);
/* 2803 */     INF31.setId("Softpak / Additional Information");
/* 2804 */     bomDisk.addElement(INF31);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2810 */     FormCheckBox PID21 = new FormCheckBox("PID21", "on", "", false, bomDiskDetail.stickerOneStatusIndicator);
/* 2811 */     PID21.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2812 */     PID21.setId("PID21");
/* 2813 */     PID21.setTabIndex(78);
/* 2814 */     PID21.setId("Sticker1");
/* 2815 */     bomDisk.addElement(PID21);
/*      */ 
/*      */     
/* 2818 */     Vector sid21 = MilestoneHelper.getBomSuppliers(21, typeFlag, bomDiskDetail.stickerOneSupplierId);
/* 2819 */     FormDropDownMenu SID21 = MilestoneHelper.getLookupDropDown("SID21", sid21, String.valueOf(bomDiskDetail.stickerOneSupplierId), false, false);
/* 2820 */     SID21.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2821 */     SID21.setTabIndex(79);
/* 2822 */     SID21.setId("Sticker1 / Supplier");
/* 2823 */     bomDisk.addElement(SID21);
/*      */ 
/*      */     
/* 2826 */     FormTextField INK121 = new FormTextField("INK121", bomDiskDetail.getStickerOneInk1(), false, 2, 2);
/* 2827 */     INK121.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2828 */     INK121.setTabIndex(80);
/* 2829 */     INK121.setId("Sticker1 / Ink");
/* 2830 */     bomDisk.addElement(INK121);
/*      */ 
/*      */     
/* 2833 */     FormTextField INK221 = new FormTextField("INK221", bomDiskDetail.getStickerOneInk2(), false, 2, 2);
/* 2834 */     INK221.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2835 */     INK221.setTabIndex(81);
/* 2836 */     INK221.setId("Sticker1 / Ink");
/* 2837 */     bomDisk.addElement(INK221);
/*      */ 
/*      */     
/* 2840 */     FormTextField SEL21 = new FormTextField("SEL21", bomDiskDetail.getStickerOnePlaces(), false, 6, 6);
/* 2841 */     SEL21.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2842 */     SEL21.setTabIndex(82);
/* 2843 */     SEL21.setId("Sticker1 / Places");
/* 2844 */     bomDisk.addElement(SEL21);
/*      */ 
/*      */     
/* 2847 */     FormTextField INF21 = new FormTextField("INF21", bomDiskDetail.getStickerOneInfo(), false, 20, 120);
/* 2848 */     INF21.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2849 */     INF21.setTabIndex(83);
/* 2850 */     INF21.setId("Sticker1 / Additional Information");
/* 2851 */     bomDisk.addElement(INF21);
/*      */ 
/*      */     
/* 2854 */     FormCheckBox PID22 = new FormCheckBox("PID22", "on", "", false, bomDiskDetail.stickerTwoStatusIndicator);
/* 2855 */     PID22.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2856 */     PID22.setId("PID22");
/* 2857 */     PID22.setTabIndex(84);
/* 2858 */     PID22.setId("Sticker2");
/* 2859 */     bomDisk.addElement(PID22);
/*      */ 
/*      */     
/* 2862 */     Vector sid22 = MilestoneHelper.getBomSuppliers(22, typeFlag, bomDiskDetail.stickerTwoSupplierId);
/* 2863 */     FormDropDownMenu SID22 = MilestoneHelper.getLookupDropDown("SID22", sid22, String.valueOf(bomDiskDetail.stickerTwoSupplierId), false, false);
/* 2864 */     SID22.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2865 */     SID22.setTabIndex(85);
/* 2866 */     SID22.setId("Sticker2 / Supplier");
/* 2867 */     bomDisk.addElement(SID22);
/*      */ 
/*      */     
/* 2870 */     FormTextField INK122 = new FormTextField("INK122", bomDiskDetail.getStickerTwoInk1(), false, 2, 2);
/* 2871 */     INK122.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2872 */     INK122.setTabIndex(86);
/* 2873 */     INK122.setId("Sticker2 / Ink");
/* 2874 */     bomDisk.addElement(INK122);
/*      */ 
/*      */     
/* 2877 */     FormTextField INK222 = new FormTextField("INK222", bomDiskDetail.getStickerTwoInk2(), false, 2, 2);
/* 2878 */     INK222.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2879 */     INK222.setTabIndex(87);
/* 2880 */     INK222.setId("Sticker2 / Ink");
/* 2881 */     bomDisk.addElement(INK222);
/*      */ 
/*      */     
/* 2884 */     FormTextField SEL22 = new FormTextField("SEL22", bomDiskDetail.getStickerTwoPlaces(), false, 6, 6);
/* 2885 */     SEL22.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2886 */     SEL22.setTabIndex(88);
/* 2887 */     SEL22.setId("Sticker2 / Places");
/* 2888 */     bomDisk.addElement(SEL22);
/*      */ 
/*      */     
/* 2891 */     FormTextField INF22 = new FormTextField("INF22", bomDiskDetail.getStickerTwoInfo(), false, 20, 120);
/* 2892 */     INF22.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2893 */     INF22.setTabIndex(89);
/* 2894 */     INF22.setId("Sticker2 / Additional Information");
/* 2895 */     bomDisk.addElement(INF22);
/*      */ 
/*      */     
/* 2898 */     FormCheckBox PID2 = new FormCheckBox("PID2", "on", "", false, bomDiskDetail.bookStatusIndicator);
/* 2899 */     PID2.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2900 */     PID2.setId("PID2");
/* 2901 */     PID2.setTabIndex(90);
/* 2902 */     PID2.setId("Book (Other/Set)");
/* 2903 */     bomDisk.addElement(PID2);
/*      */ 
/*      */     
/* 2906 */     Vector sid2 = MilestoneHelper.getBomSuppliers(2, typeFlag, bomDiskDetail.bookSupplierId);
/* 2907 */     FormDropDownMenu SID2 = MilestoneHelper.getLookupDropDown("SID2", sid2, String.valueOf(bomDiskDetail.bookSupplierId), false, false);
/* 2908 */     SID2.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2909 */     SID2.setTabIndex(91);
/* 2910 */     SID2.setId("Book (Other/Set) / Supplier");
/* 2911 */     bomDisk.addElement(SID2);
/*      */ 
/*      */     
/* 2914 */     FormTextField INK12 = new FormTextField("INK12", bomDiskDetail.getBookInk1(), false, 2, 2);
/* 2915 */     INK12.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2916 */     INK12.setTabIndex(92);
/* 2917 */     INK12.setId("Book (Other/Set) / Ink");
/* 2918 */     bomDisk.addElement(INK12);
/*      */ 
/*      */     
/* 2921 */     FormTextField INK22 = new FormTextField("INK22", bomDiskDetail.getBookInk2(), false, 2, 2);
/* 2922 */     INK22.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2923 */     INK22.setTabIndex(93);
/* 2924 */     INK22.setId("Book (Other/Set) / Ink");
/* 2925 */     bomDisk.addElement(INK22);
/*      */ 
/*      */     
/* 2928 */     FormTextField SEL2 = new FormTextField("SEL2", bomDiskDetail.getBookPages(), false, 5, 5);
/* 2929 */     SEL2.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2930 */     SEL2.setTabIndex(94);
/* 2931 */     SEL2.setId("Book (Other/Set) / Pages");
/* 2932 */     bomDisk.addElement(SEL2);
/*      */ 
/*      */     
/* 2935 */     FormTextField INF2 = new FormTextField("INF2", bomDiskDetail.getBookInfo(), false, 20, 120);
/* 2936 */     INF2.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2937 */     INF2.setTabIndex(95);
/* 2938 */     INF2.setId("Book (Other/Set) / Additional Information");
/* 2939 */     bomDisk.addElement(INF2);
/*      */ 
/*      */     
/* 2942 */     FormCheckBox PID3 = new FormCheckBox("PID3", "on", "", false, bomDiskDetail.boxStatusIndicator);
/* 2943 */     PID3.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2944 */     PID3.setId("PID3");
/* 2945 */     PID3.setTabIndex(96);
/* 2946 */     PID3.setId("Box (Set)");
/* 2947 */     bomDisk.addElement(PID3);
/*      */ 
/*      */     
/* 2950 */     Vector sid3 = MilestoneHelper.getBomSuppliers(3, typeFlag, bomDiskDetail.boxSupplierId);
/* 2951 */     FormDropDownMenu SID3 = MilestoneHelper.getLookupDropDown("SID3", sid3, String.valueOf(bomDiskDetail.boxSupplierId), false, false);
/* 2952 */     SID3.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2953 */     SID3.setTabIndex(97);
/* 2954 */     SID3.setId("Box (Set) / Supplier");
/* 2955 */     bomDisk.addElement(SID3);
/*      */ 
/*      */     
/* 2958 */     FormTextField INK13 = new FormTextField("INK13", bomDiskDetail.getBoxInk1(), false, 2, 2);
/* 2959 */     INK13.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2960 */     INK13.setTabIndex(98);
/* 2961 */     INK13.setId("Box (Set) / Ink");
/* 2962 */     bomDisk.addElement(INK13);
/*      */ 
/*      */     
/* 2965 */     FormTextField INK23 = new FormTextField("INK23", bomDiskDetail.getBoxInk2(), false, 2, 2);
/* 2966 */     INK23.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2967 */     INK23.setTabIndex(99);
/* 2968 */     INK23.setId("Box (Set) / Ink");
/* 2969 */     bomDisk.addElement(INK23);
/*      */ 
/*      */     
/* 2972 */     FormTextField SEL3 = new FormTextField("SEL3", bomDiskDetail.getBoxSizes(), false, 10, 20);
/* 2973 */     SEL3.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2974 */     SEL3.setTabIndex(100);
/* 2975 */     SEL3.setId("Box (Set) / Size");
/* 2976 */     bomDisk.addElement(SEL3);
/*      */ 
/*      */     
/* 2979 */     FormTextField INF3 = new FormTextField("INF3", bomDiskDetail.getBoxInfo(), false, 20, 120);
/* 2980 */     INF3.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2981 */     INF3.setTabIndex(101);
/* 2982 */     INF3.setId("Box (Set) / Additional Information");
/* 2983 */     bomDisk.addElement(INF3);
/*      */ 
/*      */     
/* 2986 */     FormCheckBox PID18 = new FormCheckBox("PID18", "on", "", false, bomDiskDetail.otherStatusIndicator);
/* 2987 */     PID18.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2988 */     PID18.setId("PID18");
/* 2989 */     PID18.setTabIndex(102);
/* 2990 */     PID18.setId("Other");
/* 2991 */     bomDisk.addElement(PID18);
/*      */ 
/*      */     
/* 2994 */     Vector sid18 = MilestoneHelper.getBomSuppliers(18, typeFlag, bomDiskDetail.otherSupplierId);
/* 2995 */     FormDropDownMenu SID18 = MilestoneHelper.getLookupDropDown("SID18", sid18, String.valueOf(bomDiskDetail.otherSupplierId), false, false);
/* 2996 */     SID18.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2997 */     SID18.setTabIndex(103);
/* 2998 */     SID18.setId("Other / Supplier");
/* 2999 */     bomDisk.addElement(SID18);
/*      */ 
/*      */     
/* 3002 */     FormTextField INK118 = new FormTextField("INK118", bomDiskDetail.getOtherInk1(), false, 2, 2);
/* 3003 */     INK118.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3004 */     INK118.setTabIndex(104);
/* 3005 */     INK118.setId("Other / Ink");
/* 3006 */     bomDisk.addElement(INK118);
/*      */ 
/*      */     
/* 3009 */     FormTextField INK218 = new FormTextField("INK218", bomDiskDetail.getOtherInk2(), false, 2, 2);
/* 3010 */     INK218.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3011 */     INK218.setTabIndex(105);
/* 3012 */     INK218.setId("Other / Ink");
/* 3013 */     bomDisk.addElement(INK218);
/*      */ 
/*      */     
/* 3016 */     FormTextField INF18 = new FormTextField("INF18", bomDiskDetail.getOtherInfo(), false, 20, 120);
/* 3017 */     INF18.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3018 */     INF18.setTabIndex(106);
/* 3019 */     INF18.setId("Other / Additional Information");
/* 3020 */     bomDisk.addElement(INF18);
/*      */     
/* 3022 */     return bomDisk;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Form buildDVD(Form bomDVD, Selection selection, Bom bom) {
/* 3029 */     BomDVDDetail bomDVDDetail = bom.getBomDVDDetail();
/*      */ 
/*      */     
/* 3032 */     Vector selDisc = Cache.getInstance().getLookupDetailValuesFromDatabase(62);
/* 3033 */     FormDropDownMenu SELDISC = MilestoneHelper.getLookupDropDownBom("SELDISC", selDisc, bomDVDDetail.getDiscSelectionInfo(), false, true);
/* 3034 */     SELDISC.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3035 */     SELDISC.setTabIndex(17);
/* 3036 */     SELDISC.setId("Disc / Additional Information");
/* 3037 */     bomDVD.addElement(SELDISC);
/*      */ 
/*      */ 
/*      */     
/* 3041 */     FormCheckBox PID25 = new FormCheckBox("PID25", "on", "", false, bomDVDDetail.wrapStatusIndicator);
/* 3042 */     PID25.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3043 */     PID25.setId("PID25");
/* 3044 */     PID25.setTabIndex(21);
/* 3045 */     PID25.setId("Wrap");
/* 3046 */     bomDVD.addElement(PID25);
/*      */ 
/*      */     
/* 3049 */     Vector sid25 = MilestoneHelper.getBomSuppliers(29, "1,2", bomDVDDetail.wrapSupplierId);
/* 3050 */     FormDropDownMenu SID25 = MilestoneHelper.getLookupDropDown("SID25", sid25, String.valueOf(bomDVDDetail.wrapSupplierId), false, false);
/* 3051 */     SID25.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3052 */     SID25.setTabIndex(22);
/* 3053 */     SID25.setId("Wrap / Supplier");
/* 3054 */     bomDVD.addElement(SID25);
/*      */ 
/*      */     
/* 3057 */     FormTextField INK125 = new FormTextField("INK125", bomDVDDetail.getWrapInk1(), false, 2, 2);
/* 3058 */     INK125.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3059 */     INK125.setTabIndex(23);
/* 3060 */     INK125.setId("Wrap / Ink");
/* 3061 */     bomDVD.addElement(INK125);
/*      */ 
/*      */     
/* 3064 */     FormTextField INK225 = new FormTextField("INK225", bomDVDDetail.getWrapInk2(), false, 2, 2);
/* 3065 */     INK225.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3066 */     INK225.setTabIndex(24);
/* 3067 */     INK225.setId("Wrap / Ink");
/* 3068 */     bomDVD.addElement(INK225);
/*      */ 
/*      */     
/* 3071 */     FormTextField INF25 = new FormTextField("INF25", bomDVDDetail.getWrapInfo(), false, 50, 120);
/* 3072 */     INF25.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3073 */     INF25.setTabIndex(25);
/* 3074 */     INF25.setId("Wrap / Additional Information");
/* 3075 */     bomDVD.addElement(INF25);
/*      */ 
/*      */ 
/*      */     
/* 3079 */     FormCheckBox PID26 = new FormCheckBox("PID26", "on", "", false, bomDVDDetail.dvdStatusIndicator);
/* 3080 */     PID26.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3081 */     PID26.setTabIndex(26);
/* 3082 */     PID26.setId("DVD Case");
/* 3083 */     bomDVD.addElement(PID26);
/*      */ 
/*      */     
/* 3086 */     FormTextField INK126 = new FormTextField("INK126", bomDVDDetail.getDVDInk1(), false, 2, 2);
/* 3087 */     INK126.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3088 */     INK126.setTabIndex(27);
/* 3089 */     INK126.setId("DVD Case / Ink");
/* 3090 */     bomDVD.addElement(INK126);
/*      */ 
/*      */     
/* 3093 */     FormTextField INK226 = new FormTextField("INK226", bomDVDDetail.getDVDInk2(), false, 2, 2);
/* 3094 */     INK226.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3095 */     INK226.setTabIndex(28);
/* 3096 */     INK226.setId("DVD Case / Ink");
/* 3097 */     bomDVD.addElement(INK226);
/*      */ 
/*      */     
/* 3100 */     Vector sel26 = Cache.getInstance().getLookupDetailValuesFromDatabase(64);
/* 3101 */     FormDropDownMenu SEL26 = MilestoneHelper.getLookupDropDownBom("SEL26", sel26, bomDVDDetail.getDVDSelectionInfo(), false, true);
/* 3102 */     SEL26.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3103 */     SEL26.setTabIndex(29);
/* 3104 */     SEL26.setId("DVD Case / Additional Information");
/* 3105 */     bomDVD.addElement(SEL26);
/*      */ 
/*      */     
/* 3108 */     FormTextField INF26 = new FormTextField("INF26", bomDVDDetail.getDVDInfo(), false, 20, 120);
/* 3109 */     INF26.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3110 */     INF26.setTabIndex(30);
/* 3111 */     INF26.setId("DVD Case / Additional Information");
/* 3112 */     bomDVD.addElement(INF26);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3118 */     FormCheckBox PID32 = new FormCheckBox("PID32", "on", "", false, bomDVDDetail.bluRayStatusIndicator);
/* 3119 */     PID32.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3120 */     PID32.setTabIndex(31);
/* 3121 */     PID32.setId("Blu-Ray Case");
/* 3122 */     bomDVD.addElement(PID32);
/*      */ 
/*      */     
/* 3125 */     FormTextField INK132 = new FormTextField("INK132", bomDVDDetail.getBluRayInk1(), false, 2, 2);
/* 3126 */     INK132.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3127 */     INK132.setTabIndex(32);
/* 3128 */     INK132.setId("Blu-Ray Case / Ink");
/* 3129 */     bomDVD.addElement(INK132);
/*      */ 
/*      */     
/* 3132 */     FormTextField INK232 = new FormTextField("INK232", bomDVDDetail.getBluRayInk2(), false, 2, 2);
/* 3133 */     INK232.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3134 */     INK232.setTabIndex(33);
/* 3135 */     INK232.setId("Blu-Ray Case / Ink");
/* 3136 */     bomDVD.addElement(INK232);
/*      */ 
/*      */     
/* 3139 */     Vector sel32 = Cache.getInstance().getLookupDetailValuesFromDatabase(66);
/* 3140 */     FormDropDownMenu SEL32 = MilestoneHelper.getLookupDropDownBom("SEL32", sel32, bomDVDDetail.getBluRaySelectionInfo(), false, true);
/* 3141 */     SEL32.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3142 */     SEL32.setTabIndex(34);
/* 3143 */     SEL32.setId("Blu-Ray Case / Additional Information");
/* 3144 */     bomDVD.addElement(SEL32);
/*      */ 
/*      */     
/* 3147 */     FormTextField INF32 = new FormTextField("INF32", bomDVDDetail.getBluRayInfo(), false, 20, 120);
/* 3148 */     INF32.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3149 */     INF32.setTabIndex(35);
/* 3150 */     INF32.setId("Blu-Ray Case / Additional Information");
/* 3151 */     bomDVD.addElement(INF32);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3158 */     return buildDisk(bomDVD, selection, bom, bom.getBomDVDDetail());
/*      */   }
/*      */ 
/*      */   
/*      */   protected Form buildCassette(Form bomCassette, Selection selection, Bom bom) {
/* 3163 */     BomCassetteDetail bomCassetteDetail = bom.getBomCassetteDetail();
/*      */ 
/*      */     
/* 3166 */     FormCheckBox PID5 = new FormCheckBox("PID5", "on", "", false, bomCassetteDetail.coStatusIndicator);
/* 3167 */     PID5.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3168 */     PID5.setId("C-0");
/* 3169 */     PID5.setTabIndex(14);
/* 3170 */     bomCassette.addElement(PID5);
/*      */ 
/*      */     
/* 3173 */     FormTextField INK15 = new FormTextField("INK15", bomCassetteDetail.getCoInk1(), false, 2, 2);
/* 3174 */     INK15.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3175 */     INK15.setTabIndex(15);
/* 3176 */     INK15.setId("C-0 / Ink");
/* 3177 */     bomCassette.addElement(INK15);
/*      */ 
/*      */     
/* 3180 */     FormTextField INK25 = new FormTextField("INK25", bomCassetteDetail.getCoInk2(), false, 2, 2);
/* 3181 */     INK25.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3182 */     INK25.setTabIndex(16);
/* 3183 */     INK25.setId("C-0 / Ink");
/* 3184 */     bomCassette.addElement(INK25);
/*      */ 
/*      */     
/* 3187 */     Vector sel5 = Cache.getInstance().getLookupDetailValuesFromDatabase(46);
/* 3188 */     FormDropDownMenu SEL5 = MilestoneHelper.getLookupDropDownBom("SEL5", sel5, bomCassetteDetail.getCoColor(), false, true);
/* 3189 */     SEL5.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3190 */     SEL5.setTabIndex(17);
/* 3191 */     SEL5.setId("C-0 / Color");
/* 3192 */     bomCassette.addElement(SEL5);
/*      */ 
/*      */     
/* 3195 */     FormTextField INF5 = new FormTextField("INF5", bomCassetteDetail.getCoInfo(), false, 20, 120);
/* 3196 */     INF5.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3197 */     INF5.setTabIndex(18);
/* 3198 */     INF5.setId("C-0 / Additional Information");
/* 3199 */     bomCassette.addElement(INF5);
/*      */ 
/*      */     
/* 3202 */     FormCheckBox PID16 = new FormCheckBox("PID16", "on", "", false, bomCassetteDetail.norelcoStatusIndicator);
/* 3203 */     PID16.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3204 */     PID16.setId("PID16");
/* 3205 */     PID16.setTabIndex(19);
/* 3206 */     PID16.setId("Norelco");
/* 3207 */     bomCassette.addElement(PID16);
/*      */ 
/*      */     
/* 3210 */     Vector sel16 = Cache.getInstance().getLookupDetailValuesFromDatabase(46);
/* 3211 */     FormDropDownMenu SEL16 = MilestoneHelper.getLookupDropDownBom("SEL16", sel16, bomCassetteDetail.getNorelcoColor(), false, true);
/* 3212 */     SEL16.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3213 */     SEL16.setTabIndex(20);
/* 3214 */     SEL16.setId("Norelco / Color");
/* 3215 */     bomCassette.addElement(SEL16);
/*      */ 
/*      */     
/* 3218 */     FormTextField INF16 = new FormTextField("INF16", bomCassetteDetail.getNorelcoInfo(), false, 20, 120);
/* 3219 */     INF16.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3220 */     INF16.setTabIndex(21);
/* 3221 */     INF16.setId("Norelco / Additional Information");
/* 3222 */     bomCassette.addElement(INF16);
/*      */ 
/*      */     
/* 3225 */     FormCheckBox PID13 = new FormCheckBox("PID13", "on", "", false, bomCassetteDetail.jCardStatusIndicator);
/* 3226 */     PID13.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3227 */     PID13.setId("PID13");
/* 3228 */     PID13.setTabIndex(22);
/* 3229 */     PID13.setId("J-Card");
/* 3230 */     bomCassette.addElement(PID13);
/*      */ 
/*      */     
/* 3233 */     Vector sid13 = MilestoneHelper.getBomSuppliers(13);
/* 3234 */     FormDropDownMenu SID13 = MilestoneHelper.getLookupDropDown("SID13", sid13, String.valueOf(bomCassetteDetail.jCardSupplierId), false, false);
/* 3235 */     SID13.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3236 */     SID13.setTabIndex(23);
/* 3237 */     SID13.setId("J-Card / Supplier");
/* 3238 */     bomCassette.addElement(SID13);
/*      */     
/* 3240 */     Vector sid5 = MilestoneHelper.getBomSuppliers(5);
/* 3241 */     FormDropDownMenu SID5 = MilestoneHelper.getLookupDropDown("SID5", sid5, String.valueOf(bomCassetteDetail.coParSupplierId), false, false);
/* 3242 */     SID5.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3243 */     SID5.setTabIndex(23);
/* 3244 */     SID5.setId("C-0");
/* 3245 */     bomCassette.addElement(SID5);
/*      */ 
/*      */     
/* 3248 */     Vector sid16 = MilestoneHelper.getBomSuppliers(16);
/*      */     
/* 3250 */     FormDropDownMenu SID16 = MilestoneHelper.getLookupDropDown("SID16", sid16, String.valueOf(bomCassetteDetail.norelcoSupplierId), false, false);
/* 3251 */     SID16.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3252 */     SID16.setTabIndex(23);
/* 3253 */     SID16.setId("");
/*      */     
/* 3255 */     bomCassette.addElement(SID16);
/*      */ 
/*      */     
/* 3258 */     FormTextField INK113 = new FormTextField("INK113", bomCassetteDetail.getJCardInk1(), false, 2, 2);
/* 3259 */     INK113.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3260 */     INK113.setTabIndex(24);
/* 3261 */     INK113.setId("J-Card / Ink");
/* 3262 */     bomCassette.addElement(INK113);
/*      */ 
/*      */     
/* 3265 */     FormTextField INK213 = new FormTextField("INK213", bomCassetteDetail.getJCardInk2(), false, 2, 2);
/* 3266 */     INK213.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3267 */     INK213.setTabIndex(25);
/* 3268 */     INK213.setId("J-Card / Ink");
/* 3269 */     bomCassette.addElement(INK213);
/*      */ 
/*      */     
/* 3272 */     FormTextField SEL13 = new FormTextField("SEL13", bomCassetteDetail.getJCardPanels(), false, 6, 6);
/* 3273 */     SEL13.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3274 */     SEL13.setTabIndex(26);
/* 3275 */     SEL13.setId("J-Card / Panels");
/* 3276 */     bomCassette.addElement(SEL13);
/*      */ 
/*      */     
/* 3279 */     FormTextField INF13 = new FormTextField("INF13", bomCassetteDetail.getJCardInfo(), false, 20, 120);
/* 3280 */     INF13.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3281 */     INF13.setTabIndex(27);
/* 3282 */     INF13.setId("J-Card / Additional Information");
/* 3283 */     bomCassette.addElement(INF13);
/*      */ 
/*      */     
/* 3286 */     FormCheckBox PID24 = new FormCheckBox("PID24", "on", "", false, bomCassetteDetail.uCardStatusIndicator);
/* 3287 */     PID24.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3288 */     PID24.setId("PID24");
/* 3289 */     PID24.setTabIndex(28);
/* 3290 */     PID24.setId("U-Card");
/* 3291 */     bomCassette.addElement(PID24);
/*      */ 
/*      */     
/* 3294 */     Vector sid24 = MilestoneHelper.getBomSuppliers(24);
/* 3295 */     FormDropDownMenu SID24 = MilestoneHelper.getLookupDropDown("SID24", sid24, String.valueOf(bomCassetteDetail.uCardSupplierId), false, false);
/* 3296 */     SID24.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3297 */     SID24.setTabIndex(29);
/* 3298 */     SID24.setId("U-Card / Supplier");
/* 3299 */     bomCassette.addElement(SID24);
/*      */ 
/*      */     
/* 3302 */     FormTextField INK124 = new FormTextField("INK124", bomCassetteDetail.getUCardInk1(), false, 2, 2);
/* 3303 */     INK124.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3304 */     INK124.setTabIndex(30);
/* 3305 */     INK124.setId("U-Card / Ink");
/* 3306 */     bomCassette.addElement(INK124);
/*      */ 
/*      */     
/* 3309 */     FormTextField INK224 = new FormTextField("INK224", bomCassetteDetail.getUCardInk2(), false, 2, 2);
/* 3310 */     INK224.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3311 */     INK224.setTabIndex(31);
/* 3312 */     INK224.setId("U-Card / Ink");
/* 3313 */     bomCassette.addElement(INK224);
/*      */ 
/*      */     
/* 3316 */     FormTextField SEL24 = new FormTextField("SEL24", bomCassetteDetail.getUCardPanels(), false, 6, 6);
/* 3317 */     SEL24.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3318 */     SEL24.setTabIndex(32);
/* 3319 */     SEL24.setId("U-Card / Panels");
/* 3320 */     bomCassette.addElement(SEL24);
/*      */ 
/*      */     
/* 3323 */     FormTextField INF24 = new FormTextField("INF24", bomCassetteDetail.getUCardInfo(), false, 20, 120);
/* 3324 */     INF24.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3325 */     INF24.setTabIndex(33);
/* 3326 */     INF24.setId("U-Card / Additional Information");
/* 3327 */     bomCassette.addElement(INF24);
/*      */ 
/*      */     
/* 3330 */     FormCheckBox PID17 = new FormCheckBox("PID17", "on", "", false, bomCassetteDetail.oCardStatusIndicator);
/* 3331 */     PID17.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3332 */     PID17.setTabIndex(34);
/* 3333 */     PID17.setId("PID17");
/* 3334 */     PID17.setId("O-Card");
/* 3335 */     bomCassette.addElement(PID17);
/*      */ 
/*      */     
/* 3338 */     Vector sid17 = MilestoneHelper.getBomSuppliers(17);
/* 3339 */     FormDropDownMenu SID17 = MilestoneHelper.getLookupDropDown("SID17", sid17, String.valueOf(bomCassetteDetail.oCardSupplierId), false, false);
/* 3340 */     SID17.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3341 */     SID17.setTabIndex(35);
/* 3342 */     SID17.setId("O-Card / Supplier");
/* 3343 */     bomCassette.addElement(SID17);
/*      */ 
/*      */     
/* 3346 */     FormTextField INK117 = new FormTextField("INK117", bomCassetteDetail.getOCardInk1(), false, 2, 2);
/* 3347 */     INK117.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3348 */     INK117.setTabIndex(36);
/* 3349 */     INK117.setId("O-Card / Ink");
/* 3350 */     bomCassette.addElement(INK117);
/*      */ 
/*      */     
/* 3353 */     FormTextField INK217 = new FormTextField("INK217", bomCassetteDetail.getOCardInk2(), false, 2, 2);
/* 3354 */     INK217.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3355 */     INK217.setTabIndex(37);
/* 3356 */     INK217.setId("O-Card / Ink");
/* 3357 */     bomCassette.addElement(INK217);
/*      */ 
/*      */     
/* 3360 */     FormTextField INF17 = new FormTextField("INF17", bomCassetteDetail.getOCardInfo(), false, 20, 120);
/* 3361 */     INF17.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3362 */     INF17.setTabIndex(38);
/* 3363 */     INF17.setId("O-Card / Additional Information");
/* 3364 */     bomCassette.addElement(INF17);
/*      */ 
/*      */     
/* 3367 */     FormCheckBox PID21 = new FormCheckBox("PID21", "on", "", false, bomCassetteDetail.stickerOneCardStatusIndicator);
/* 3368 */     PID21.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3369 */     PID21.setId("PID21");
/* 3370 */     PID21.setTabIndex(39);
/* 3371 */     PID21.setId("Sticker1");
/* 3372 */     bomCassette.addElement(PID21);
/*      */ 
/*      */     
/* 3375 */     Vector sid21 = MilestoneHelper.getBomSuppliers(21);
/* 3376 */     FormDropDownMenu SID21 = MilestoneHelper.getLookupDropDown("SID21", sid21, String.valueOf(bomCassetteDetail.stickerOneCardSupplierId), false, false);
/* 3377 */     SID21.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3378 */     SID21.setTabIndex(40);
/* 3379 */     SID21.setId("Sticker1 / Supplier");
/* 3380 */     bomCassette.addElement(SID21);
/*      */ 
/*      */     
/* 3383 */     FormTextField INK121 = new FormTextField("INK121", bomCassetteDetail.getStickerOneCardInk1(), false, 2, 2);
/* 3384 */     INK121.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3385 */     INK121.setTabIndex(41);
/* 3386 */     INK121.setId("Sticker1 / Ink");
/* 3387 */     bomCassette.addElement(INK121);
/*      */ 
/*      */     
/* 3390 */     FormTextField INK221 = new FormTextField("INK221", bomCassetteDetail.getStickerOneCardInk2(), false, 2, 2);
/* 3391 */     INK221.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3392 */     INK221.setTabIndex(42);
/* 3393 */     INK121.setId("Sticker1 / Ink");
/* 3394 */     bomCassette.addElement(INK221);
/*      */ 
/*      */     
/* 3397 */     FormTextField SEL21 = new FormTextField("SEL21", bomCassetteDetail.getStickerOneCardPlaces(), false, 6, 6);
/* 3398 */     SEL21.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3399 */     SEL21.setTabIndex(43);
/* 3400 */     SEL21.setId("Sticker1 / Places");
/* 3401 */     bomCassette.addElement(SEL21);
/*      */ 
/*      */     
/* 3404 */     FormTextField INF21 = new FormTextField("INF21", bomCassetteDetail.getStickerOneCardInfo(), false, 20, 120);
/* 3405 */     INF21.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3406 */     INF21.setTabIndex(44);
/* 3407 */     INF21.setId("Sticker1 / Additional Information");
/* 3408 */     bomCassette.addElement(INF21);
/*      */ 
/*      */     
/* 3411 */     FormCheckBox PID22 = new FormCheckBox("PID22", "on", "", false, bomCassetteDetail.stickerTwoCardStatusIndicator);
/* 3412 */     PID22.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3413 */     PID22.setId("Sticker2");
/* 3414 */     PID22.setTabIndex(45);
/* 3415 */     bomCassette.addElement(PID22);
/*      */ 
/*      */     
/* 3418 */     Vector sid22 = MilestoneHelper.getBomSuppliers(22);
/* 3419 */     FormDropDownMenu SID22 = MilestoneHelper.getLookupDropDown("SID22", sid22, String.valueOf(bomCassetteDetail.stickerTwoCardSupplierId), false, false);
/* 3420 */     SID22.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3421 */     SID22.setTabIndex(46);
/* 3422 */     SID22.setId("Sticker2 / Supplier");
/* 3423 */     bomCassette.addElement(SID22);
/*      */ 
/*      */     
/* 3426 */     FormTextField INK122 = new FormTextField("INK122", bomCassetteDetail.getStickerTwoCardInk1(), false, 2, 2);
/* 3427 */     INK122.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3428 */     INK122.setTabIndex(47);
/* 3429 */     INK122.setId("Sticker2 / Ink");
/* 3430 */     bomCassette.addElement(INK122);
/*      */ 
/*      */     
/* 3433 */     FormTextField INK222 = new FormTextField("INK222", bomCassetteDetail.getStickerTwoCardInk2(), false, 2, 2);
/* 3434 */     INK222.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3435 */     INK222.setTabIndex(48);
/* 3436 */     INK222.setId("Sticker2 / Ink");
/* 3437 */     bomCassette.addElement(INK222);
/*      */ 
/*      */     
/* 3440 */     FormTextField SEL22 = new FormTextField("SEL22", bomCassetteDetail.getStickerTwoCardPlaces(), false, 6, 6);
/* 3441 */     SEL22.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3442 */     SEL22.setTabIndex(49);
/* 3443 */     SEL22.setId("Sticker2 / Places");
/* 3444 */     bomCassette.addElement(SEL22);
/*      */ 
/*      */     
/* 3447 */     FormTextField INF22 = new FormTextField("INF22", bomCassetteDetail.getStickerTwoCardInfo(), false, 20, 120);
/* 3448 */     INF22.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3449 */     INF22.setTabIndex(50);
/* 3450 */     INF22.setId("Sticker2 / Additional Information");
/* 3451 */     bomCassette.addElement(INF22);
/*      */ 
/*      */     
/* 3454 */     FormCheckBox PID18 = new FormCheckBox("PID18", "on", "", false, bomCassetteDetail.otherStatusIndicator);
/* 3455 */     PID18.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3456 */     PID18.setId("PID18");
/* 3457 */     PID18.setTabIndex(51);
/* 3458 */     PID18.setId("Other");
/* 3459 */     bomCassette.addElement(PID18);
/*      */ 
/*      */     
/* 3462 */     Vector sid18 = MilestoneHelper.getBomSuppliers(18);
/* 3463 */     FormDropDownMenu SID18 = MilestoneHelper.getLookupDropDown("SID18", sid18, String.valueOf(bomCassetteDetail.otherSupplierId), false, false);
/* 3464 */     SID18.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3465 */     SID18.setTabIndex(52);
/* 3466 */     SID18.setId("Other / Supplier");
/* 3467 */     bomCassette.addElement(SID18);
/*      */ 
/*      */     
/* 3470 */     FormTextField INK118 = new FormTextField("INK118", bomCassetteDetail.getOtherInk1(), false, 2, 2);
/* 3471 */     INK118.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3472 */     INK118.setTabIndex(53);
/* 3473 */     INK118.setId("Other / Ink");
/* 3474 */     bomCassette.addElement(INK118);
/*      */ 
/*      */     
/* 3477 */     FormTextField INK218 = new FormTextField("INK218", bomCassetteDetail.getOtherInk2(), false, 2, 2);
/* 3478 */     INK218.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3479 */     INK218.setTabIndex(54);
/* 3480 */     INK218.setId("Other / Ink");
/* 3481 */     bomCassette.addElement(INK218);
/*      */ 
/*      */     
/* 3484 */     FormTextField INF18 = new FormTextField("INF18", bomCassetteDetail.getOtherInfo(), false, 50, 120);
/* 3485 */     INF18.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3486 */     INF18.setTabIndex(55);
/* 3487 */     INF18.setId("Other / Additional Information");
/* 3488 */     bomCassette.addElement(INF18);
/*      */     
/* 3490 */     return bomCassette;
/*      */   }
/*      */ 
/*      */   
/*      */   protected Form buildVinyl(Form bomVinyl, Selection selection, Bom bom) {
/* 3495 */     BomVinylDetail bomVinylDetail = bom.getBomVinylDetail();
/*      */ 
/*      */     
/* 3498 */     FormCheckBox PID19 = new FormCheckBox("PID19", "on", "", false, bomVinylDetail.recordStatusIndicator);
/* 3499 */     PID19.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3500 */     PID19.setId("Record");
/* 3501 */     PID19.setTabIndex(14);
/* 3502 */     bomVinyl.addElement(PID19);
/*      */ 
/*      */     
/* 3505 */     Vector sid19 = MilestoneHelper.getBomSuppliers(19, bomVinylDetail.recordSupplierId);
/* 3506 */     FormDropDownMenu SID19 = MilestoneHelper.getLookupDropDown("SID19", sid19, String.valueOf(bomVinylDetail.recordSupplierId), false, false);
/* 3507 */     SID19.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3508 */     SID19.setTabIndex(15);
/* 3509 */     SID19.setId("Record / Supplier");
/* 3510 */     bomVinyl.addElement(SID19);
/*      */ 
/*      */     
/* 3513 */     Vector sel19 = Cache.getInstance().getLookupDetailValuesFromDatabase(46);
/* 3514 */     FormDropDownMenu SEL19 = MilestoneHelper.getLookupDropDownBom("SEL19", sel19, bomVinylDetail.recordColor, false, true);
/* 3515 */     SEL19.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3516 */     SEL19.setTabIndex(16);
/* 3517 */     SEL19.setId("Record / Color");
/* 3518 */     bomVinyl.addElement(SEL19);
/*      */ 
/*      */     
/* 3521 */     FormTextField INF19 = new FormTextField("INF19", bomVinylDetail.getRecordInfo(), false, 20, 120);
/* 3522 */     INF19.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3523 */     INF19.setTabIndex(17);
/* 3524 */     INF19.setId("Record / Additional Information");
/* 3525 */     bomVinyl.addElement(INF19);
/*      */ 
/*      */     
/* 3528 */     FormCheckBox PID14 = new FormCheckBox("PID14", "on", "", false, bomVinylDetail.labelStatusIndicator);
/* 3529 */     PID14.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3530 */     PID14.setId("PID14");
/* 3531 */     PID14.setTabIndex(18);
/* 3532 */     PID14.setId("Label");
/* 3533 */     bomVinyl.addElement(PID14);
/*      */ 
/*      */     
/* 3536 */     Vector sid14 = MilestoneHelper.getBomSuppliers(14, bomVinylDetail.labelSupplierId);
/* 3537 */     FormDropDownMenu SID14 = MilestoneHelper.getLookupDropDown("SID14", sid14, String.valueOf(bomVinylDetail.labelSupplierId), false, false);
/* 3538 */     SID14.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3539 */     SID14.setTabIndex(19);
/* 3540 */     SID14.setId("Label / Supplier");
/* 3541 */     bomVinyl.addElement(SID14);
/*      */ 
/*      */     
/* 3544 */     FormTextField INK114 = new FormTextField("INK114", bomVinylDetail.getLabelInk1(), false, 2, 2);
/* 3545 */     INK114.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3546 */     INK114.setTabIndex(20);
/* 3547 */     INK114.setId("Label / Ink");
/* 3548 */     bomVinyl.addElement(INK114);
/*      */ 
/*      */     
/* 3551 */     FormTextField INK214 = new FormTextField("INK214", bomVinylDetail.getLabelInk2(), false, 2, 2);
/* 3552 */     INK214.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3553 */     INK214.setTabIndex(21);
/* 3554 */     INK214.setId("Label / Ink");
/* 3555 */     bomVinyl.addElement(INK214);
/*      */ 
/*      */     
/* 3558 */     FormTextField INF14 = new FormTextField("INF14", bomVinylDetail.getLabelInfo(), false, 50, 120);
/* 3559 */     INF14.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3560 */     INF14.setTabIndex(22);
/* 3561 */     INF14.setId("Label / Additional Information");
/* 3562 */     bomVinyl.addElement(INF14);
/*      */ 
/*      */     
/* 3565 */     FormCheckBox PID20 = new FormCheckBox("PID20", "on", "", false, bomVinylDetail.sleeveStatusIndicator);
/* 3566 */     PID20.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3567 */     PID20.setId("PID20");
/* 3568 */     PID20.setTabIndex(23);
/* 3569 */     PID20.setId("Sleeve");
/* 3570 */     bomVinyl.addElement(PID20);
/*      */ 
/*      */     
/* 3573 */     Vector sid20 = MilestoneHelper.getBomSuppliers(20, bomVinylDetail.sleeveSupplierId);
/* 3574 */     FormDropDownMenu SID20 = MilestoneHelper.getLookupDropDown("SID20", sid20, String.valueOf(bomVinylDetail.sleeveSupplierId), false, false);
/* 3575 */     SID20.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3576 */     SID20.setTabIndex(24);
/* 3577 */     SID20.setId("Sleeve / Supplier");
/* 3578 */     bomVinyl.addElement(SID20);
/*      */ 
/*      */     
/* 3581 */     FormTextField INK120 = new FormTextField("INK120", bomVinylDetail.getSleeveInk1(), false, 2, 2);
/* 3582 */     INK120.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3583 */     INK120.setTabIndex(25);
/* 3584 */     INK120.setId("Sleeve / Ink");
/* 3585 */     bomVinyl.addElement(INK120);
/*      */ 
/*      */     
/* 3588 */     FormTextField INK220 = new FormTextField("INK220", bomVinylDetail.getSleeveInk2(), false, 2, 2);
/* 3589 */     INK220.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3590 */     INK220.setTabIndex(26);
/* 3591 */     INK220.setId("Sleeve / Ink");
/* 3592 */     bomVinyl.addElement(INK220);
/*      */ 
/*      */     
/* 3595 */     FormTextField INF20 = new FormTextField("INF20", bomVinylDetail.getSleeveInfo(), false, 50, 120);
/* 3596 */     INF20.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3597 */     INF20.setTabIndex(27);
/* 3598 */     INF20.setId("Sleeve / Additional Information");
/* 3599 */     bomVinyl.addElement(INF20);
/*      */ 
/*      */     
/* 3602 */     FormCheckBox PID11 = new FormCheckBox("PID11", "on", "", false, bomVinylDetail.jacketStatusIndicator);
/* 3603 */     PID11.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3604 */     PID11.setId("PID11");
/* 3605 */     PID11.setTabIndex(28);
/* 3606 */     PID11.setId("Jacket");
/* 3607 */     bomVinyl.addElement(PID11);
/*      */ 
/*      */     
/* 3610 */     Vector sid11 = MilestoneHelper.getBomSuppliers(11, bomVinylDetail.jacketSupplierId);
/* 3611 */     FormDropDownMenu SID11 = MilestoneHelper.getLookupDropDown("SID11", sid11, String.valueOf(bomVinylDetail.jacketSupplierId), false, false);
/* 3612 */     SID11.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3613 */     SID11.setTabIndex(29);
/* 3614 */     SID11.setId("Jacket / Suppliers");
/* 3615 */     bomVinyl.addElement(SID11);
/*      */ 
/*      */     
/* 3618 */     FormTextField INK111 = new FormTextField("INK111", bomVinylDetail.getJacketInk1(), false, 2, 2);
/* 3619 */     INK111.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3620 */     INK111.setTabIndex(30);
/* 3621 */     INK111.setId("Jacket / Ink");
/* 3622 */     bomVinyl.addElement(INK111);
/*      */ 
/*      */     
/* 3625 */     FormTextField INK211 = new FormTextField("INK211", bomVinylDetail.getJacketInk2(), false, 2, 2);
/* 3626 */     INK211.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3627 */     INK211.setTabIndex(31);
/* 3628 */     INK211.setId("Jacket / Ink");
/* 3629 */     bomVinyl.addElement(INK211);
/*      */ 
/*      */     
/* 3632 */     FormTextField INF11 = new FormTextField("INF11", bomVinylDetail.getJacketInfo(), false, 50, 120);
/* 3633 */     INF11.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3634 */     INF11.setTabIndex(32);
/* 3635 */     INF11.setId("Jacket / Additional Information");
/* 3636 */     bomVinyl.addElement(INF11);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3643 */     FormCheckBox PID33 = new FormCheckBox("PID33", "on", "", false, bomVinylDetail.insertStatusIndicator);
/* 3644 */     PID33.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3645 */     PID33.setId("PID33");
/* 3646 */     PID33.setTabIndex(33);
/* 3647 */     PID33.setId("Insert");
/* 3648 */     bomVinyl.addElement(PID33);
/*      */ 
/*      */     
/* 3651 */     Vector sid33 = MilestoneHelper.getBomSuppliers(33, bomVinylDetail.insertSupplierId);
/* 3652 */     FormDropDownMenu SID33 = MilestoneHelper.getLookupDropDown("SID33", sid33, String.valueOf(bomVinylDetail.insertSupplierId), false, false);
/* 3653 */     SID33.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3654 */     SID33.setTabIndex(34);
/* 3655 */     SID33.setId("Insert / Suppliers");
/* 3656 */     bomVinyl.addElement(SID33);
/*      */ 
/*      */     
/* 3659 */     FormTextField INK133 = new FormTextField("INK133", bomVinylDetail.getInsertInk1(), false, 2, 2);
/* 3660 */     INK133.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3661 */     INK133.setTabIndex(35);
/* 3662 */     INK133.setId("Insert / Ink");
/* 3663 */     bomVinyl.addElement(INK133);
/*      */ 
/*      */     
/* 3666 */     FormTextField INK233 = new FormTextField("INK233", bomVinylDetail.getInsertInk2(), false, 2, 2);
/* 3667 */     INK233.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3668 */     INK233.setTabIndex(36);
/* 3669 */     INK233.setId("Insert / Ink");
/* 3670 */     bomVinyl.addElement(INK233);
/*      */ 
/*      */     
/* 3673 */     FormTextField INF33 = new FormTextField("INF33", bomVinylDetail.getInsertInfo(), false, 50, 120);
/* 3674 */     INF33.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3675 */     INF33.setTabIndex(37);
/* 3676 */     INF33.setId("Insert / Additional Information");
/* 3677 */     bomVinyl.addElement(INF33);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3683 */     FormCheckBox PID21 = new FormCheckBox("PID21", "on", "", false, bomVinylDetail.stickerOneStatusIndicator);
/* 3684 */     PID21.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3685 */     PID21.setId("PID21");
/* 3686 */     PID21.setTabIndex(38);
/* 3687 */     PID21.setId("Sticker1");
/* 3688 */     bomVinyl.addElement(PID21);
/*      */ 
/*      */     
/* 3691 */     Vector sid21 = MilestoneHelper.getBomSuppliers(21, "0,1,3", bomVinylDetail.stickerOneSupplierId);
/* 3692 */     FormDropDownMenu SID21 = MilestoneHelper.getLookupDropDown("SID21", sid21, String.valueOf(bomVinylDetail.stickerOneSupplierId), false, false);
/* 3693 */     SID21.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3694 */     SID21.setTabIndex(39);
/* 3695 */     SID21.setId("Sticker1 / Supplier");
/* 3696 */     bomVinyl.addElement(SID21);
/*      */ 
/*      */     
/* 3699 */     FormTextField INK121 = new FormTextField("INK121", bomVinylDetail.getStickerOneInk1(), false, 2, 2);
/* 3700 */     INK121.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3701 */     INK121.setTabIndex(40);
/* 3702 */     INK121.setId("Sticker1 / Ink");
/* 3703 */     bomVinyl.addElement(INK121);
/*      */ 
/*      */     
/* 3706 */     FormTextField INK221 = new FormTextField("INK221", bomVinylDetail.getStickerOneInk2(), false, 2, 2);
/* 3707 */     INK211.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3708 */     INK221.setTabIndex(41);
/* 3709 */     INK221.setId("Sticker1 / Ink");
/* 3710 */     bomVinyl.addElement(INK221);
/*      */ 
/*      */     
/* 3713 */     FormTextField SEL21 = new FormTextField("SEL21", bomVinylDetail.getStickerOnePlaces(), false, 6, 6);
/* 3714 */     SEL21.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3715 */     SEL21.setTabIndex(42);
/* 3716 */     SEL21.setId("Sticker1 / Places");
/* 3717 */     bomVinyl.addElement(SEL21);
/*      */ 
/*      */     
/* 3720 */     FormTextField INF21 = new FormTextField("INF21", bomVinylDetail.getStickerOneInfo(), false, 20, 120);
/* 3721 */     INF21.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3722 */     INF21.setTabIndex(43);
/* 3723 */     INF21.setId("Sticker1 / Additional Information");
/* 3724 */     bomVinyl.addElement(INF21);
/*      */ 
/*      */     
/* 3727 */     FormCheckBox PID22 = new FormCheckBox("PID22", "on", "", false, bomVinylDetail.stickerTwoStatusIndicator);
/* 3728 */     PID22.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3729 */     PID22.setId("PID22");
/* 3730 */     PID22.setTabIndex(44);
/* 3731 */     PID22.setId("Sticker2");
/* 3732 */     bomVinyl.addElement(PID22);
/*      */ 
/*      */     
/* 3735 */     Vector sid22 = MilestoneHelper.getBomSuppliers(22, "0,1,3", bomVinylDetail.stickerTwoSupplierId);
/* 3736 */     FormDropDownMenu SID22 = MilestoneHelper.getLookupDropDown("SID22", sid22, String.valueOf(bomVinylDetail.stickerTwoSupplierId), false, false);
/* 3737 */     SID22.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3738 */     SID22.setTabIndex(45);
/* 3739 */     SID22.setId("Sticker2 / Suppliers");
/* 3740 */     bomVinyl.addElement(SID22);
/*      */ 
/*      */     
/* 3743 */     FormTextField INK122 = new FormTextField("INK122", bomVinylDetail.getStickerTwoInk1(), false, 2, 2);
/* 3744 */     INK122.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3745 */     INK122.setTabIndex(46);
/* 3746 */     INK122.setId("Sticker2 / Ink");
/* 3747 */     bomVinyl.addElement(INK122);
/*      */ 
/*      */     
/* 3750 */     FormTextField INK222 = new FormTextField("INK222", bomVinylDetail.getStickerTwoInk2(), false, 2, 2);
/* 3751 */     INK222.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3752 */     INK222.setTabIndex(47);
/* 3753 */     INK222.setId("Sticker2 / Ink");
/* 3754 */     bomVinyl.addElement(INK222);
/*      */ 
/*      */     
/* 3757 */     FormTextField SEL22 = new FormTextField("SEL22", bomVinylDetail.getStickerTwoPlaces(), false, 6, 6);
/* 3758 */     SEL22.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3759 */     SEL22.setTabIndex(48);
/* 3760 */     SEL22.setId("Sticker2 / Places");
/* 3761 */     bomVinyl.addElement(SEL22);
/*      */ 
/*      */     
/* 3764 */     FormTextField INF22 = new FormTextField("INF22", bomVinylDetail.getStickerTwoInfo(), false, 20, 120);
/* 3765 */     INF22.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3766 */     INF22.setTabIndex(49);
/* 3767 */     INF22.setId("Sticker2 / Additional Information");
/* 3768 */     bomVinyl.addElement(INF22);
/*      */ 
/*      */     
/* 3771 */     FormCheckBox PID18 = new FormCheckBox("PID18", "on", "", false, bomVinylDetail.otherStatusIndicator);
/* 3772 */     PID18.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3773 */     PID18.setId("PID18");
/* 3774 */     PID18.setTabIndex(50);
/* 3775 */     PID18.setId("Other");
/* 3776 */     bomVinyl.addElement(PID18);
/*      */ 
/*      */     
/* 3779 */     Vector sid18 = MilestoneHelper.getBomSuppliers(18, bomVinylDetail.otherSupplierId);
/* 3780 */     FormDropDownMenu SID18 = MilestoneHelper.getLookupDropDown("SID18", sid18, String.valueOf(bomVinylDetail.otherSupplierId), false, false);
/* 3781 */     SID18.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3782 */     SID18.setTabIndex(51);
/* 3783 */     SID18.setId("Other / Supplier");
/* 3784 */     bomVinyl.addElement(SID18);
/*      */ 
/*      */     
/* 3787 */     FormTextField INK118 = new FormTextField("INK118", bomVinylDetail.getOtherInk1(), false, 2, 2);
/* 3788 */     INK118.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3789 */     INK118.setTabIndex(52);
/* 3790 */     INK118.setId("Other / Ink");
/* 3791 */     bomVinyl.addElement(INK118);
/*      */ 
/*      */     
/* 3794 */     FormTextField INK218 = new FormTextField("INK218", bomVinylDetail.getOtherInk2(), false, 2, 2);
/* 3795 */     INK218.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3796 */     INK218.setTabIndex(53);
/* 3797 */     INK218.setId("Other / Ink");
/* 3798 */     bomVinyl.addElement(INK218);
/*      */ 
/*      */     
/* 3801 */     FormTextField INF18 = new FormTextField("INF18", bomVinylDetail.getOtherInfo(), false, 50, 120);
/* 3802 */     INF18.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3803 */     INF18.setTabIndex(54);
/* 3804 */     INF18.setId("Other / Additional Information");
/* 3805 */     bomVinyl.addElement(INF18);
/*      */     
/* 3807 */     return bomVinyl;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Form buildNewForm(Context context, Selection selection, String command) {
/* 3817 */     Form bomForm = new Form(this.application, "bomForm", this.application.getInfrastructure().getServletURL(), "POST");
/*      */ 
/*      */     
/* 3820 */     String selectionID = "-1";
/* 3821 */     if (selection != null)
/*      */     {
/* 3823 */       selectionID = String.valueOf(selection.getSelectionID());
/*      */     }
/* 3825 */     bomForm.addElement(new FormHidden("selectionID", selectionID, true));
/*      */ 
/*      */     
/* 3828 */     bomForm.addElement(new FormHidden("bomLastUpdateCheck", "-1", true));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3833 */     User currentUser = (User)context.getSessionValue("user");
/*      */     
/* 3835 */     int secureLevel = getSelectionBomPermissions(selection, currentUser);
/* 3836 */     setButtonVisibilities(selection, currentUser, context, secureLevel, command);
/*      */     
/* 3838 */     bomForm.addElement(new FormHidden("OrderBy", "", true));
/*      */ 
/*      */     
/* 3841 */     MilestoneInfrastructure inf = (MilestoneInfrastructure)context.getInfrastructure();
/*      */     
/* 3843 */     String printOption = "Draft";
/* 3844 */     FormRadioButtonGroup PrintOption = new FormRadioButtonGroup("printOption", printOption, "Draft,Final", false);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3849 */     boolean numberUnitsIsZero = false;
/* 3850 */     numberUnitsIsZero = (selection.getNumberOfUnits() == 0);
/* 3851 */     boolean statusIsTBS = false;
/* 3852 */     statusIsTBS = SelectionManager.getLookupObjectValue(selection.getSelectionStatus()).equalsIgnoreCase("TBS");
/*      */     
/* 3854 */     if (numberUnitsIsZero || statusIsTBS) {
/* 3855 */       PrintOption.addFormEvent("onClick", "noUnitsOrTBS(this, " + numberUnitsIsZero + ", " + statusIsTBS + ");hidePrintButtons('printWindow','');toggleSave('" + inf.getImageDirectory() + "');");
/*      */     } else {
/* 3857 */       PrintOption.addFormEvent("onClick", "hidePrintButtons('printWindow','');toggleSave('" + inf.getImageDirectory() + "');");
/*      */     } 
/* 3859 */     PrintOption.setTabIndex(0);
/* 3860 */     PrintOption.setId("Draft/Final");
/* 3861 */     bomForm.addElement(PrintOption);
/*      */ 
/*      */     
/* 3864 */     FormDateField Date = new FormDateField("Date", MilestoneHelper.getFormatedDate(Calendar.getInstance()), false, 10);
/* 3865 */     Date.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3866 */     Date.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
/* 3867 */     Date.addFormEvent("onBlur", "JavaScript:checkField( this )");
/* 3868 */     Date.setTabIndex(1);
/* 3869 */     bomForm.addElement(Date);
/*      */ 
/*      */     
/* 3872 */     String selectionValue = "Add";
/*      */     
/* 3874 */     FormRadioButtonGroup IsAdded = new FormRadioButtonGroup("IsAdded", selectionValue, "Add, Change", false);
/* 3875 */     IsAdded.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3876 */     IsAdded.setTabIndex(2);
/* 3877 */     IsAdded.setId("Add/Change");
/*      */ 
/*      */     
/* 3880 */     IsAdded.setEnabled(false);
/* 3881 */     bomForm.addElement(IsAdded);
/*      */ 
/*      */     
/* 3884 */     FormTextField ChangeNumber = new FormTextField("ChangeNumber", "-1", false, 2, 2);
/* 3885 */     ChangeNumber.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3886 */     ChangeNumber.setTabIndex(3);
/* 3887 */     ChangeNumber.setId("Change #");
/* 3888 */     ChangeNumber.setEnabled(false);
/* 3889 */     bomForm.addElement(ChangeNumber);
/*      */ 
/*      */     
/* 3892 */     FormTextField Submitter = new FormTextField("Submitter", currentUser.getName(), false, 30, 50);
/*      */     
/* 3894 */     Submitter.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3895 */     Submitter.setTabIndex(4);
/* 3896 */     bomForm.addElement(Submitter);
/*      */ 
/*      */     
/* 3899 */     FormTextField Phone = new FormTextField("Phone", currentUser.getPhone(), false, 30, 30);
/* 3900 */     Phone.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3901 */     Phone.setTabIndex(6);
/* 3902 */     bomForm.addElement(Phone);
/*      */ 
/*      */     
/* 3905 */     FormTextField Email = new FormTextField("Email", currentUser.getEmail(), false, 30, 50);
/* 3906 */     Email.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3907 */     Email.setTabIndex(5);
/* 3908 */     bomForm.addElement(Email);
/*      */ 
/*      */     
/* 3911 */     Selection mySelection = selection;
/*      */ 
/*      */     
/* 3914 */     String labelText = "";
/* 3915 */     if (mySelection.getLabel() != null)
/*      */     {
/* 3917 */       labelText = mySelection.getLabel().getName().toUpperCase();
/*      */     }
/* 3919 */     FormTextField Label = new FormTextField("Label", labelText, false, 30);
/* 3920 */     Label.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3921 */     bomForm.addElement(Label);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3926 */     String statusText = "";
/* 3927 */     if (mySelection.getSelectionStatus() != null)
/*      */     {
/* 3929 */       statusText = mySelection.getSelectionStatus().getName();
/*      */     }
/* 3931 */     FormTextField status = new FormTextField("status", statusText, false, 30);
/* 3932 */     bomForm.addElement(status);
/*      */ 
/*      */ 
/*      */     
/* 3936 */     String upc = "";
/* 3937 */     if (mySelection.getUpc() != null)
/*      */     {
/* 3939 */       upc = mySelection.getUpc();
/*      */     }
/* 3941 */     FormTextField Upc = new FormTextField("upc", upc, false, 20);
/* 3942 */     Upc.setStartingValue(upc);
/* 3943 */     bomForm.addElement(Upc);
/*      */ 
/*      */     
/* 3946 */     String imprintText = "";
/* 3947 */     if (mySelection.getImprint() != null)
/*      */     {
/* 3949 */       imprintText = mySelection.getImprint();
/*      */     }
/* 3951 */     FormTextField imprint = new FormTextField("imprint", imprintText, false, 30);
/* 3952 */     bomForm.addElement(imprint);
/*      */ 
/*      */ 
/*      */     
/* 3956 */     String artistText = "";
/* 3957 */     if (mySelection.getArtist() != null)
/*      */     {
/* 3959 */       artistText = mySelection.getArtist();
/*      */     }
/* 3961 */     FormTextField Artist = new FormTextField("Artist", artistText, false, 125);
/* 3962 */     Artist.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3963 */     bomForm.addElement(Artist);
/* 3964 */     bomForm.addElement(new FormHidden("Artist", artistText, false));
/*      */ 
/*      */     
/* 3967 */     String titleText = "";
/* 3968 */     if (mySelection.getTitle() != null)
/*      */     {
/* 3970 */       titleText = mySelection.getTitle();
/*      */     }
/* 3972 */     FormTextField Title = new FormTextField("Title", titleText, false, 125);
/* 3973 */     Title.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3974 */     bomForm.addElement(Title);
/*      */ 
/*      */     
/* 3977 */     String selectionNumberText = "";
/* 3978 */     selectionNumberText = String.valueOf(SelectionManager.getLookupObjectValue(selection.getPrefixID())) + " " + selection.getSelectionNo();
/* 3979 */     FormTextField SelectionNumber = new FormTextField("SelectionNumber", selectionNumberText, false, 50);
/* 3980 */     SelectionNumber.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 3981 */     SelectionNumber.setId("Local Prod #");
/* 3982 */     bomForm.addElement(SelectionNumber);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3994 */     String ReleasingCompText = "";
/* 3995 */     if (ReleasingFamily.getName(selection.getReleaseFamilyId()) != null) {
/* 3996 */       ReleasingCompText = ReleasingFamily.getName(selection.getReleaseFamilyId());
/*      */     }
/* 3998 */     FormTextField ReleasingComp = new FormTextField("ReleasingComp", ReleasingCompText, false, 30);
/* 3999 */     ReleasingComp.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4000 */     ReleasingComp.setId("Releasing Company");
/* 4001 */     bomForm.addElement(ReleasingComp);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4018 */     String isRetailText = "";
/*      */ 
/*      */ 
/*      */     
/* 4022 */     if (selection.getReleaseType().getName() != null && selection.getReleaseType().getName().equalsIgnoreCase("Promotional")) {
/* 4023 */       isRetailText = "Promotional";
/*      */     } else {
/* 4025 */       isRetailText = "Commercial";
/* 4026 */     }  FormTextField IsRetail = new FormTextField("IsRetail", isRetailText, false, 50);
/* 4027 */     IsRetail.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4028 */     IsRetail.setTabIndex(8);
/* 4029 */     IsRetail.setId("Type");
/* 4030 */     bomForm.addElement(IsRetail);
/*      */ 
/*      */     
/* 4033 */     String streetDateOnBomText = "";
/* 4034 */     if (selection.getStreetDate() != null)
/*      */     {
/* 4036 */       streetDateOnBomText = MilestoneHelper.getFormatedDate(selection.getStreetDate());
/*      */     }
/* 4038 */     FormTextField streetDateOnBom = new FormTextField("DueDate", streetDateOnBomText, false, 10);
/* 4039 */     streetDateOnBom.setEnabled(false);
/* 4040 */     streetDateOnBom.setTabIndex(9);
/* 4041 */     streetDateOnBom.setId("Street Date");
/* 4042 */     bomForm.addElement(streetDateOnBom);
/*      */ 
/*      */ 
/*      */     
/* 4046 */     String unitsPerSetText = "";
/* 4047 */     if (selection.getNumberOfUnits() > 0) {
/*      */       
/*      */       try {
/*      */         
/* 4051 */         unitsPerSetText = Integer.toString(selection.getNumberOfUnits());
/*      */       }
/* 4053 */       catch (NumberFormatException e) {
/*      */         
/* 4055 */         System.out.println("Error converting Units Per Set into integer.");
/*      */       } 
/*      */     }
/*      */     
/* 4059 */     FormTextField UnitsPerPackage = new FormTextField("UnitsPerPackage", unitsPerSetText, false, 10);
/* 4060 */     UnitsPerPackage.setEnabled(false);
/* 4061 */     UnitsPerPackage.setTabIndex(10);
/* 4062 */     UnitsPerPackage.setId("Units per Package ");
/* 4063 */     bomForm.addElement(UnitsPerPackage);
/*      */ 
/*      */     
/* 4066 */     FormTextField Runtime = new FormTextField("Runtime", "", false, 10, 10);
/* 4067 */     Runtime.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4068 */     Runtime.setTabIndex(11);
/* 4069 */     bomForm.addElement(Runtime);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4075 */     boolean isOn = false;
/* 4076 */     if (!isRetailText.equalsIgnoreCase("Promotional")) {
/* 4077 */       isOn = true;
/*      */     }
/* 4079 */     FormCheckBox UseNoShrinkWrap = new FormCheckBox("UseNoShrinkWrap", "on", "", false, isOn);
/* 4080 */     UseNoShrinkWrap.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4081 */     UseNoShrinkWrap.setTabIndex(13);
/* 4082 */     UseNoShrinkWrap.setStartingChecked(true);
/* 4083 */     UseNoShrinkWrap.setId("Shrink Wrap");
/* 4084 */     bomForm.addElement(UseNoShrinkWrap);
/*      */ 
/*      */     
/* 4087 */     FormCheckBox HasSpineSticker = new FormCheckBox("HasSpineSticker", "on", "", false, isOn);
/* 4088 */     HasSpineSticker.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4089 */     HasSpineSticker.setId("HasSpineSticker");
/* 4090 */     HasSpineSticker.setTabIndex(12);
/* 4091 */     HasSpineSticker.setId("Spine Sticker");
/* 4092 */     bomForm.addElement(HasSpineSticker);
/*      */ 
/*      */     
/* 4095 */     FormTextField Configuration = new FormTextField("Configuration", "", false, 10, 10);
/* 4096 */     Configuration.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4097 */     bomForm.addElement(Configuration);
/*      */ 
/*      */     
/* 4100 */     FormTextArea SpecialInstructions = new FormTextArea("SpecialInstructions", "", false, 2, 100, "virtual");
/* 4101 */     SpecialInstructions.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4102 */     SpecialInstructions.setTabIndex(100);
/* 4103 */     SpecialInstructions.setId("Special Instructions");
/*      */ 
/*      */     
/* 4106 */     bomForm.addElement(SpecialInstructions);
/*      */ 
/*      */     
/* 4109 */     String configuration = "";
/* 4110 */     configuration = selection.getSelectionConfig().getSelectionConfigurationAbbreviation();
/*      */     
/* 4112 */     if (configuration.equalsIgnoreCase("CAS")) {
/*      */       
/* 4114 */       Bom bom = null;
/* 4115 */       buildNewCassette(bomForm, selection, bom);
/*      */     }
/* 4117 */     else if (configuration.equalsIgnoreCase("VIN")) {
/*      */       
/* 4119 */       Bom bom = null;
/* 4120 */       buildNewVinyl(bomForm, selection, bom);
/*      */     
/*      */     }
/* 4123 */     else if (configuration.equalsIgnoreCase("DVV")) {
/*      */       
/* 4125 */       Bom bom = null;
/* 4126 */       buildNewDVD(bomForm, selection, bom);
/*      */     }
/*      */     else {
/*      */       
/* 4130 */       Bom bom = null;
/* 4131 */       buildNewDisk(bomForm, selection, bom);
/*      */     } 
/*      */ 
/*      */     
/* 4135 */     FormTextField lastUpdated = new FormTextField("lastUpdatedDate", false, 50);
/* 4136 */     lastUpdated.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4137 */     lastUpdated.setValue("");
/* 4138 */     bomForm.addElement(lastUpdated);
/*      */     
/* 4140 */     FormTextField lastUpdatedBy = new FormTextField("lastUpdatedBy", false, 50);
/* 4141 */     lastUpdatedBy.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4142 */     lastUpdatedBy.setValue("");
/* 4143 */     bomForm.addElement(lastUpdatedBy);
/*      */ 
/*      */     
/* 4146 */     FormTextField lastLegacyUpdateDate = new FormTextField("lastLegacyUpdateDate", false, 50);
/* 4147 */     lastLegacyUpdateDate.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4148 */     lastLegacyUpdateDate.setValue("");
/* 4149 */     bomForm.addElement(lastLegacyUpdateDate);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4155 */     bomForm = addSelectionSearchElements(context, selection, bomForm);
/*      */     
/* 4157 */     bomForm.addElement(new FormHidden("cmd", "bom-edit-new", true));
/*      */     
/* 4159 */     String releaseWeekString = "";
/* 4160 */     if (selection != null) {
/* 4161 */       releaseWeekString = MilestoneHelper.getReleaseWeekString(selection);
/*      */     }
/* 4163 */     context.putDelivery("releaseWeek", releaseWeekString);
/*      */ 
/*      */     
/* 4166 */     if (context.getSessionValue("NOTEPAD_BOM_VISIBLE") != null) {
/* 4167 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_BOM_VISIBLE"));
/*      */     }
/*      */     
/* 4170 */     if (selection != null) {
/* 4171 */       context.putDelivery("selection", selection);
/*      */     }
/* 4173 */     return bomForm;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected Form buildNewDisk(Form bomDisk, Selection selection, Bom bom) {
/*      */     Vector sel12;
/* 4180 */     String typeFlag = "0";
/* 4181 */     int ctrlSize = 50;
/* 4182 */     if (selection.getSelectionConfig().getSelectionConfigurationAbbreviation().equalsIgnoreCase("DVV")) {
/* 4183 */       typeFlag = "1,2";
/* 4184 */       ctrlSize = 20;
/*      */     } else {
/* 4186 */       typeFlag = "0,1";
/* 4187 */       ctrlSize = 50;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 4192 */     FormCheckBox PID7 = new FormCheckBox("PID7", "on", "", false);
/* 4193 */     PID7.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4194 */     PID7.setTabIndex(14);
/* 4195 */     PID7.setId("Disc");
/* 4196 */     bomDisk.addElement(PID7);
/*      */ 
/*      */ 
/*      */     
/* 4200 */     FormTextField INK17 = new FormTextField("INK17", "", false, 2, 2);
/* 4201 */     INK17.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4202 */     INK17.setTabIndex(15);
/* 4203 */     INK17.setId("Disc / Ink");
/* 4204 */     bomDisk.addElement(INK17);
/*      */ 
/*      */     
/* 4207 */     FormTextField INK27 = new FormTextField("INK27", "", false, 2, 2);
/* 4208 */     INK27.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4209 */     INK27.setId("Disc / Ink");
/* 4210 */     INK27.setTabIndex(16);
/* 4211 */     bomDisk.addElement(INK27);
/*      */ 
/*      */     
/* 4214 */     FormTextField INF7 = new FormTextField("INF7", "", false, ctrlSize, 120);
/* 4215 */     INF7.setTabIndex(17);
/* 4216 */     INF7.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4217 */     INF7.setId("Disc / Additional Information");
/* 4218 */     bomDisk.addElement(INF7);
/*      */ 
/*      */     
/* 4221 */     FormCheckBox PID12 = new FormCheckBox("PID12", "on", "", false);
/* 4222 */     PID12.setId("PID12");
/* 4223 */     PID12.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4224 */     PID12.setTabIndex(18);
/* 4225 */     PID12.setId("Jewel Box");
/* 4226 */     bomDisk.addElement(PID12);
/*      */ 
/*      */ 
/*      */     
/* 4230 */     if (selection.getSelectionConfig().getSelectionConfigurationAbbreviation().equalsIgnoreCase("DVV")) {
/* 4231 */       sel12 = Cache.getInstance().getLookupDetailValuesFromDatabase(63);
/*      */     } else {
/* 4233 */       sel12 = Cache.getInstance().getLookupDetailValuesFromDatabase(41);
/*      */     } 
/* 4235 */     FormDropDownMenu SEL12 = MilestoneHelper.getLookupDropDownBom("SEL12", sel12, "", false, true);
/* 4236 */     SEL12.setTabIndex(19);
/* 4237 */     SEL12.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4238 */     SEL12.setId("Jewel Box / Additional Information");
/* 4239 */     bomDisk.addElement(SEL12);
/*      */ 
/*      */     
/* 4242 */     FormTextField INF12 = new FormTextField("INF12", "", false, 20, 120);
/* 4243 */     INF12.setTabIndex(20);
/* 4244 */     INF12.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4245 */     INF12.setId("Jewel Box / Additional Information");
/* 4246 */     bomDisk.addElement(INF12);
/*      */ 
/*      */     
/* 4249 */     FormCheckBox PID23 = new FormCheckBox("PID23", "on", "", false);
/* 4250 */     PID23.setId("PID23");
/* 4251 */     PID23.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4252 */     PID23.setTabIndex(30);
/* 4253 */     PID23.setId("Tray");
/* 4254 */     bomDisk.addElement(PID23);
/*      */ 
/*      */     
/* 4257 */     Vector sel23 = Cache.getInstance().getLookupDetailValuesFromDatabase(42);
/* 4258 */     FormDropDownMenu SEL23 = MilestoneHelper.getLookupDropDownBom("SEL23", sel23, "", false, true);
/* 4259 */     SEL23.setTabIndex(31);
/* 4260 */     SEL23.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4261 */     SEL23.setId("Tray / Additional Information");
/* 4262 */     bomDisk.addElement(SEL23);
/*      */ 
/*      */     
/* 4265 */     FormTextField INF23 = new FormTextField("INF23", "", false, 20, 120);
/* 4266 */     INF23.setTabIndex(32);
/* 4267 */     INF23.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4268 */     INF23.setId("Tray / Additional Information");
/* 4269 */     bomDisk.addElement(INF23);
/*      */ 
/*      */     
/* 4272 */     FormCheckBox PID10 = new FormCheckBox("PID10", "on", "", false);
/* 4273 */     PID10.setId("PID10");
/* 4274 */     PID10.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4275 */     PID10.setTabIndex(33);
/* 4276 */     PID10.setId("Inlay");
/* 4277 */     bomDisk.addElement(PID10);
/*      */ 
/*      */     
/* 4280 */     Vector sid10 = MilestoneHelper.getBomSuppliers(10, typeFlag);
/* 4281 */     FormDropDownMenu SID10 = MilestoneHelper.getLookupDropDown("SID10", sid10, "", false, false);
/* 4282 */     SID10.setTabIndex(34);
/* 4283 */     SID10.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4284 */     SID10.setId("Inlay / Supplier");
/* 4285 */     bomDisk.addElement(SID10);
/*      */     
/* 4287 */     Vector sid7 = MilestoneHelper.getBomSuppliers(7, typeFlag);
/* 4288 */     FormDropDownMenu SID7 = MilestoneHelper.getLookupDropDown("SID7", sid7, "", false, false);
/* 4289 */     SID7.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4290 */     SID7.setTabIndex(34);
/* 4291 */     SID7.setId("Disc");
/* 4292 */     bomDisk.addElement(SID7);
/*      */     
/* 4294 */     FormTextField INK110 = new FormTextField("INK110", "", false, 2, 2);
/* 4295 */     INK110.setTabIndex(35);
/* 4296 */     INK110.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4297 */     INK110.setId("Inlay / Ink");
/* 4298 */     bomDisk.addElement(INK110);
/*      */ 
/*      */     
/* 4301 */     FormTextField INK210 = new FormTextField("INK210", "", false, 2, 2);
/* 4302 */     INK210.setTabIndex(36);
/* 4303 */     INK210.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4304 */     INK210.setId("Inlay / Ink");
/* 4305 */     bomDisk.addElement(INK210);
/*      */ 
/*      */     
/* 4308 */     FormTextField INF10 = new FormTextField("INF10", "", false, 50, 120);
/* 4309 */     INF10.setTabIndex(37);
/* 4310 */     INF10.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4311 */     INF10.setId("Inlay / Additional Information");
/* 4312 */     bomDisk.addElement(INF10);
/*      */ 
/*      */     
/* 4315 */     FormCheckBox PID9 = new FormCheckBox("PID9", "on", "", false);
/* 4316 */     PID9.setId("PID9");
/* 4317 */     PID9.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4318 */     PID9.setTabIndex(38);
/* 4319 */     PID9.setId("Front Insert");
/* 4320 */     bomDisk.addElement(PID9);
/*      */ 
/*      */     
/* 4323 */     Vector sid9 = MilestoneHelper.getBomSuppliers(9, typeFlag);
/* 4324 */     FormDropDownMenu SID9 = MilestoneHelper.getLookupDropDown("SID9", sid9, "", false, false);
/* 4325 */     SID9.setTabIndex(39);
/* 4326 */     SID9.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4327 */     SID9.setId("Front Insert / Supplier");
/* 4328 */     bomDisk.addElement(SID9);
/*      */ 
/*      */     
/* 4331 */     FormTextField INK19 = new FormTextField("INK19", "", false, 2, 2);
/* 4332 */     INK19.setTabIndex(40);
/* 4333 */     INK19.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4334 */     INK19.setId("Front Insert / Ink");
/* 4335 */     bomDisk.addElement(INK19);
/*      */ 
/*      */     
/* 4338 */     FormTextField INK29 = new FormTextField("INK29", "", false, 2, 2);
/* 4339 */     INK29.setTabIndex(41);
/* 4340 */     INK29.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4341 */     INK29.setId("Front Insert / Ink");
/* 4342 */     bomDisk.addElement(INK29);
/*      */ 
/*      */     
/* 4345 */     FormTextField INF9 = new FormTextField("INF9", "", false, 50, 120);
/* 4346 */     INF9.setTabIndex(42);
/* 4347 */     INF9.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4348 */     INF9.setId("Front Insert / Additional Information");
/* 4349 */     bomDisk.addElement(INF9);
/*      */ 
/*      */     
/* 4352 */     FormCheckBox PID8 = new FormCheckBox("PID8", "on", "", false);
/* 4353 */     PID8.setId("PID8");
/* 4354 */     PID8.setTabIndex(43);
/* 4355 */     PID8.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4356 */     PID8.setId("Folder");
/* 4357 */     bomDisk.addElement(PID8);
/*      */ 
/*      */     
/* 4360 */     Vector sid8 = MilestoneHelper.getBomSuppliers(8, typeFlag);
/* 4361 */     FormDropDownMenu SID8 = MilestoneHelper.getLookupDropDown("SID8", sid8, "", false, false);
/* 4362 */     SID8.setTabIndex(44);
/* 4363 */     SID8.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4364 */     SID8.setId("Folder / Supplier");
/* 4365 */     bomDisk.addElement(SID8);
/*      */ 
/*      */     
/* 4368 */     FormTextField INK18 = new FormTextField("INK18", "", false, 2, 2);
/* 4369 */     INK18.setTabIndex(45);
/* 4370 */     INK18.setId("Folder / Ink");
/* 4371 */     INK18.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4372 */     bomDisk.addElement(INK18);
/*      */ 
/*      */     
/* 4375 */     FormTextField INK28 = new FormTextField("INK28", "", false, 2, 2);
/* 4376 */     INK28.setTabIndex(46);
/* 4377 */     INK28.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4378 */     INK28.setId("Folder / Ink");
/* 4379 */     bomDisk.addElement(INK28);
/*      */ 
/*      */     
/* 4382 */     Vector sel8 = Cache.getInstance().getLookupDetailValuesFromDatabase(43);
/* 4383 */     FormDropDownMenu SEL8 = MilestoneHelper.getLookupDropDownBom("SEL8", sel8, "", false, true);
/* 4384 */     SEL8.setTabIndex(47);
/* 4385 */     SEL8.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4386 */     SEL8.setId("Folder / Pages");
/* 4387 */     bomDisk.addElement(SEL8);
/*      */ 
/*      */     
/* 4390 */     FormTextField INF8 = new FormTextField("INF8", "", false, 20, 120);
/* 4391 */     INF8.setTabIndex(48);
/* 4392 */     INF8.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4393 */     INF8.setId("Folder / Additinal Information");
/* 4394 */     bomDisk.addElement(INF8);
/*      */ 
/*      */     
/* 4397 */     FormCheckBox PID1 = new FormCheckBox("PID1", "on", "", false);
/* 4398 */     PID1.setId("PID1");
/* 4399 */     PID1.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4400 */     PID1.setTabIndex(49);
/* 4401 */     PID1.setId("Booklet");
/* 4402 */     bomDisk.addElement(PID1);
/*      */ 
/*      */     
/* 4405 */     Vector sid1 = MilestoneHelper.getBomSuppliers(1, typeFlag);
/* 4406 */     FormDropDownMenu SID1 = MilestoneHelper.getLookupDropDown("SID1", sid1, "", false, false);
/* 4407 */     SID1.setTabIndex(50);
/* 4408 */     SID1.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4409 */     SID1.setId("Booklet / Supplier");
/* 4410 */     bomDisk.addElement(SID1);
/*      */ 
/*      */     
/* 4413 */     FormTextField INK11 = new FormTextField("INK11", "", false, 2, 2);
/* 4414 */     INK11.setTabIndex(51);
/* 4415 */     INK11.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4416 */     INK11.setId("Booklet / Ink");
/* 4417 */     bomDisk.addElement(INK11);
/*      */ 
/*      */     
/* 4420 */     FormTextField INK21 = new FormTextField("INK21", "", false, 2, 2);
/* 4421 */     INK21.setTabIndex(52);
/* 4422 */     INK21.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4423 */     INK21.setId("Booklet / Ink");
/* 4424 */     bomDisk.addElement(INK21);
/*      */ 
/*      */     
/* 4427 */     Vector sel1 = Cache.getInstance().getLookupDetailValuesFromDatabase(45);
/* 4428 */     FormDropDownMenu SEL1 = MilestoneHelper.getLookupDropDownBom("SEL1", sel1, "", false, true);
/* 4429 */     SEL1.setTabIndex(53);
/* 4430 */     SEL1.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4431 */     SEL1.setId("Booklet / Pages");
/* 4432 */     bomDisk.addElement(SEL1);
/*      */ 
/*      */     
/* 4435 */     FormTextField INF1 = new FormTextField("INF1", "", false, 20, 120);
/* 4436 */     INF1.setTabIndex(54);
/* 4437 */     INF1.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4438 */     INF1.setId("Booklet / Additional Information");
/* 4439 */     bomDisk.addElement(INF1);
/*      */ 
/*      */     
/* 4442 */     FormCheckBox PID4 = new FormCheckBox("PID4", "on", "", false);
/* 4443 */     PID4.setId("PID4");
/* 4444 */     PID4.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4445 */     PID4.setTabIndex(55);
/* 4446 */     PID4.setId("BRC Insert");
/* 4447 */     bomDisk.addElement(PID4);
/*      */ 
/*      */     
/* 4450 */     Vector sid4 = MilestoneHelper.getBomSuppliers(4, typeFlag);
/* 4451 */     FormDropDownMenu SID4 = MilestoneHelper.getLookupDropDown("SID4", sid4, "", false, false);
/* 4452 */     SID4.setTabIndex(56);
/* 4453 */     SID4.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4454 */     SID4.setId("BRC Insert / Supplier");
/* 4455 */     bomDisk.addElement(SID4);
/*      */ 
/*      */     
/* 4458 */     FormTextField INK14 = new FormTextField("INK14", "", false, 2, 2);
/* 4459 */     INK14.setTabIndex(57);
/* 4460 */     INK14.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4461 */     INK14.setId("BRC Insert / Ink");
/* 4462 */     bomDisk.addElement(INK14);
/*      */ 
/*      */     
/* 4465 */     FormTextField INK24 = new FormTextField("INK24", "", false, 2, 2);
/* 4466 */     INK24.setTabIndex(58);
/* 4467 */     INK24.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4468 */     INK24.setId("BRC Insert / Ink");
/* 4469 */     bomDisk.addElement(INK24);
/*      */ 
/*      */     
/* 4472 */     FormTextField SEL4 = new FormTextField("SEL4", "", false, 10, 20);
/* 4473 */     SEL4.setTabIndex(59);
/* 4474 */     SEL4.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4475 */     SEL4.setId("BRC Insert / Size");
/* 4476 */     bomDisk.addElement(SEL4);
/*      */ 
/*      */     
/* 4479 */     FormTextField INF4 = new FormTextField("INF4", "", false, 20, 120);
/* 4480 */     INF4.setTabIndex(60);
/* 4481 */     INF4.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4482 */     INF4.setId("BRC Insert / Additional Information");
/* 4483 */     bomDisk.addElement(INF4);
/*      */ 
/*      */     
/* 4486 */     FormCheckBox PID15 = new FormCheckBox("PID15", "on", "", false);
/* 4487 */     PID15.setId("PID15");
/* 4488 */     PID15.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4489 */     PID15.setTabIndex(61);
/* 4490 */     PID15.setId("Mini Jacket");
/* 4491 */     bomDisk.addElement(PID15);
/*      */ 
/*      */     
/* 4494 */     Vector sid15 = MilestoneHelper.getBomSuppliers(15, typeFlag);
/* 4495 */     FormDropDownMenu SID15 = MilestoneHelper.getLookupDropDown("SID15", sid15, "", false, false);
/* 4496 */     SID15.setTabIndex(62);
/* 4497 */     SID15.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4498 */     SID15.setId("Mini Jacket / Supplier");
/* 4499 */     bomDisk.addElement(SID15);
/*      */ 
/*      */     
/* 4502 */     FormTextField INK115 = new FormTextField("INK115", "", false, 2, 2);
/* 4503 */     INK115.setTabIndex(63);
/* 4504 */     INK115.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4505 */     INK115.setId("Mini Jacket / Ink");
/* 4506 */     bomDisk.addElement(INK115);
/*      */ 
/*      */     
/* 4509 */     FormTextField INK215 = new FormTextField("INK215", "", false, 2, 2);
/* 4510 */     INK215.setTabIndex(64);
/* 4511 */     INK215.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4512 */     INK215.setId("Mini Jacket / Ink");
/* 4513 */     bomDisk.addElement(INK215);
/*      */ 
/*      */     
/* 4516 */     FormTextField INF15 = new FormTextField("INF15", "", false, 20, 120);
/* 4517 */     INF15.setTabIndex(65);
/* 4518 */     INF15.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4519 */     INF15.setId("Mini Jacket / Additional Information");
/* 4520 */     bomDisk.addElement(INF15);
/*      */ 
/*      */     
/* 4523 */     FormCheckBox PID6 = new FormCheckBox("PID6", "on", "", false);
/* 4524 */     PID6.setId("PID6");
/* 4525 */     PID6.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4526 */     PID6.setTabIndex(66);
/* 4527 */     PID6.setId("Digipak");
/* 4528 */     bomDisk.addElement(PID6);
/*      */ 
/*      */     
/* 4531 */     Vector sid6 = MilestoneHelper.getBomSuppliers(6, typeFlag);
/* 4532 */     FormDropDownMenu SID6 = MilestoneHelper.getLookupDropDown("SID6", sid6, "", false, false);
/* 4533 */     SID6.setTabIndex(67);
/* 4534 */     SID6.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4535 */     SID6.setId("Digipak / Supplier");
/* 4536 */     bomDisk.addElement(SID6);
/*      */ 
/*      */     
/* 4539 */     FormTextField INK16 = new FormTextField("INK16", "", false, 2, 2);
/* 4540 */     INK16.setTabIndex(68);
/* 4541 */     INK16.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4542 */     INK16.setId("Digipak / Ink");
/* 4543 */     bomDisk.addElement(INK16);
/*      */ 
/*      */     
/* 4546 */     FormTextField INK26 = new FormTextField("INK26", "", false, 2, 2);
/* 4547 */     INK26.setTabIndex(69);
/* 4548 */     INK26.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4549 */     INK26.setId("Digipak / Ink");
/* 4550 */     bomDisk.addElement(INK26);
/*      */ 
/*      */     
/* 4553 */     FormTextField SEL6 = new FormTextField("SEL6", "", false, 6, 6);
/* 4554 */     SEL6.setTabIndex(70);
/* 4555 */     SEL6.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4556 */     SEL6.setId("Digipak / Tray");
/* 4557 */     bomDisk.addElement(SEL6);
/*      */ 
/*      */     
/* 4560 */     FormTextField INF6 = new FormTextField("INF6", "", false, 20, 120);
/* 4561 */     INF6.setTabIndex(71);
/* 4562 */     INF6.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4563 */     INF6.setId("Digipak / Additional Information");
/* 4564 */     bomDisk.addElement(INF6);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4572 */     FormCheckBox PID31 = new FormCheckBox("PID31", "on", "", false);
/* 4573 */     PID6.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4574 */     PID6.setId("PID31");
/* 4575 */     PID6.setTabIndex(72);
/* 4576 */     PID6.setId("Softpak");
/* 4577 */     bomDisk.addElement(PID31);
/*      */ 
/*      */     
/* 4580 */     Vector sid31 = MilestoneHelper.getBomSuppliers(31, typeFlag);
/* 4581 */     FormDropDownMenu SID31 = MilestoneHelper.getLookupDropDown("SID31", sid31, "", false, false);
/* 4582 */     SID31.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4583 */     SID31.setTabIndex(73);
/* 4584 */     SID31.setId("Softpak / Supplier");
/* 4585 */     bomDisk.addElement(SID31);
/*      */ 
/*      */     
/* 4588 */     FormTextField INK131 = new FormTextField("INK131", "", false, 2, 2);
/* 4589 */     INK131.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4590 */     INK131.setTabIndex(74);
/* 4591 */     INK131.setId("Softpak / Ink");
/* 4592 */     bomDisk.addElement(INK131);
/*      */ 
/*      */     
/* 4595 */     FormTextField INK231 = new FormTextField("INK231", "", false, 2, 2);
/* 4596 */     INK231.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4597 */     INK231.setTabIndex(75);
/* 4598 */     INK231.setId("Softpak / Ink");
/* 4599 */     bomDisk.addElement(INK231);
/*      */ 
/*      */     
/* 4602 */     FormTextField SEL31 = new FormTextField("SEL31", "", false, 6, 6);
/* 4603 */     SEL31.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4604 */     SEL31.setTabIndex(76);
/* 4605 */     SEL31.setId("Softpak / Tray");
/* 4606 */     bomDisk.addElement(SEL31);
/*      */ 
/*      */     
/* 4609 */     FormTextField INF31 = new FormTextField("INF31", "", false, 20, 120);
/* 4610 */     INF31.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4611 */     INF31.setTabIndex(77);
/* 4612 */     INF31.setId("Softpak / Additional Information");
/* 4613 */     bomDisk.addElement(INF31);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4621 */     FormCheckBox PID21 = new FormCheckBox("PID21", "on", "", false);
/* 4622 */     PID21.setId("PID21");
/* 4623 */     PID21.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4624 */     PID21.setTabIndex(78);
/* 4625 */     PID21.setId("Sticker1");
/* 4626 */     bomDisk.addElement(PID21);
/*      */ 
/*      */     
/* 4629 */     Vector sid21 = MilestoneHelper.getBomSuppliers(21, typeFlag);
/* 4630 */     FormDropDownMenu SID21 = MilestoneHelper.getLookupDropDown("SID21", sid21, "", false, false);
/* 4631 */     SID21.setTabIndex(79);
/* 4632 */     SID21.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4633 */     SID21.setId("Sticker1 / Supplier");
/* 4634 */     bomDisk.addElement(SID21);
/*      */ 
/*      */     
/* 4637 */     FormTextField INK121 = new FormTextField("INK121", "", false, 2, 2);
/* 4638 */     INK121.setTabIndex(80);
/* 4639 */     INK121.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4640 */     INK121.setId("Sticker1 / Ink");
/* 4641 */     bomDisk.addElement(INK121);
/*      */ 
/*      */     
/* 4644 */     FormTextField INK221 = new FormTextField("INK221", "", false, 2, 2);
/* 4645 */     INK221.setTabIndex(82);
/* 4646 */     INK221.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4647 */     INK221.setId("Sticker1 / Ink");
/* 4648 */     bomDisk.addElement(INK221);
/*      */ 
/*      */     
/* 4651 */     FormTextField SEL21 = new FormTextField("SEL21", "", false, 6, 6);
/* 4652 */     SEL21.setTabIndex(83);
/* 4653 */     SEL21.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4654 */     SEL21.setId("Sticker1 / Places");
/* 4655 */     bomDisk.addElement(SEL21);
/*      */ 
/*      */     
/* 4658 */     FormTextField INF21 = new FormTextField("INF21", "", false, 20, 120);
/* 4659 */     INF21.setTabIndex(84);
/* 4660 */     INF21.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4661 */     INF21.setId("Sticker1 / Additional Information");
/* 4662 */     bomDisk.addElement(INF21);
/*      */ 
/*      */     
/* 4665 */     FormCheckBox PID22 = new FormCheckBox("PID22", "on", "", false);
/* 4666 */     PID22.setId("PID22");
/* 4667 */     PID22.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4668 */     PID22.setTabIndex(85);
/* 4669 */     PID22.setId("Sticker2");
/* 4670 */     bomDisk.addElement(PID22);
/*      */ 
/*      */     
/* 4673 */     Vector sid22 = MilestoneHelper.getBomSuppliers(22, typeFlag);
/* 4674 */     FormDropDownMenu SID22 = MilestoneHelper.getLookupDropDown("SID22", sid22, "", false, false);
/* 4675 */     SID22.setTabIndex(86);
/* 4676 */     SID22.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4677 */     SID22.setId("Sticker2 / Supplier");
/* 4678 */     bomDisk.addElement(SID22);
/*      */ 
/*      */     
/* 4681 */     FormTextField INK122 = new FormTextField("INK122", "", false, 2, 2);
/* 4682 */     INK122.setTabIndex(87);
/* 4683 */     INK122.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4684 */     INK122.setId("Sticker2 / Ink");
/* 4685 */     bomDisk.addElement(INK122);
/*      */ 
/*      */     
/* 4688 */     FormTextField INK222 = new FormTextField("INK222", "", false, 2, 2);
/* 4689 */     INK222.setTabIndex(88);
/* 4690 */     INK222.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4691 */     INK222.setId("Sticker2 / Ink");
/* 4692 */     bomDisk.addElement(INK222);
/*      */ 
/*      */     
/* 4695 */     FormTextField SEL22 = new FormTextField("SEL22", "", false, 6, 6);
/* 4696 */     SEL22.setTabIndex(89);
/* 4697 */     SEL22.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4698 */     SEL22.setId("Sticker2 / Places");
/* 4699 */     bomDisk.addElement(SEL22);
/*      */ 
/*      */     
/* 4702 */     FormTextField INF22 = new FormTextField("INF22", "", false, 20, 120);
/* 4703 */     INF22.setTabIndex(90);
/* 4704 */     INF22.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4705 */     INF22.setId("Sticker2 / Additional Information");
/* 4706 */     bomDisk.addElement(INF22);
/*      */ 
/*      */     
/* 4709 */     FormCheckBox PID2 = new FormCheckBox("PID2", "on", "", false);
/* 4710 */     PID2.setId("PID2");
/* 4711 */     PID2.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4712 */     PID2.setTabIndex(91);
/* 4713 */     PID2.setId("Book (Other/Set)");
/* 4714 */     bomDisk.addElement(PID2);
/*      */ 
/*      */     
/* 4717 */     Vector sid2 = MilestoneHelper.getBomSuppliers(2, typeFlag);
/* 4718 */     FormDropDownMenu SID2 = MilestoneHelper.getLookupDropDown("SID2", sid2, "", false, false);
/* 4719 */     SID2.setTabIndex(92);
/* 4720 */     SID2.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4721 */     SID2.setId("Book (Other/Set) / Supplier");
/* 4722 */     bomDisk.addElement(SID2);
/*      */ 
/*      */     
/* 4725 */     FormTextField INK12 = new FormTextField("INK12", "", false, 2, 2);
/* 4726 */     INK12.setTabIndex(93);
/* 4727 */     INK12.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4728 */     INK12.setId("Book (Other/Set) / Ink");
/* 4729 */     bomDisk.addElement(INK12);
/*      */ 
/*      */     
/* 4732 */     FormTextField INK22 = new FormTextField("INK22", "", false, 2, 2);
/* 4733 */     INK22.setTabIndex(94);
/* 4734 */     INK22.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4735 */     INK22.setId("Book (Other/Set) / Ink");
/* 4736 */     bomDisk.addElement(INK22);
/*      */ 
/*      */     
/* 4739 */     FormTextField SEL2 = new FormTextField("SEL2", "", false, 5, 5);
/* 4740 */     SEL2.setTabIndex(95);
/* 4741 */     SEL2.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4742 */     SEL2.setId("Book (Other/Set) / Pages");
/* 4743 */     bomDisk.addElement(SEL2);
/*      */ 
/*      */     
/* 4746 */     FormTextField INF2 = new FormTextField("INF2", "", false, 20, 120);
/* 4747 */     INF2.setTabIndex(96);
/* 4748 */     INF2.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4749 */     INF2.setId("Book (Other/Set) / Additional Information");
/* 4750 */     bomDisk.addElement(INF2);
/*      */ 
/*      */     
/* 4753 */     FormCheckBox PID3 = new FormCheckBox("PID3", "on", "", false);
/* 4754 */     PID3.setId("PID3");
/* 4755 */     PID3.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4756 */     PID3.setTabIndex(97);
/* 4757 */     PID3.setId("Box (Set)");
/* 4758 */     bomDisk.addElement(PID3);
/*      */ 
/*      */     
/* 4761 */     Vector sid3 = MilestoneHelper.getBomSuppliers(3, typeFlag);
/* 4762 */     FormDropDownMenu SID3 = MilestoneHelper.getLookupDropDown("SID3", sid3, "", false, false);
/* 4763 */     SID3.setTabIndex(98);
/* 4764 */     SID3.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4765 */     SID3.setId("Box (Set) / Supplier");
/* 4766 */     bomDisk.addElement(SID3);
/*      */ 
/*      */     
/* 4769 */     FormTextField INK13 = new FormTextField("INK13", "", false, 2, 2);
/* 4770 */     INK13.setTabIndex(99);
/* 4771 */     INK13.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4772 */     INK13.setId("Box (Set) / Ink");
/* 4773 */     bomDisk.addElement(INK13);
/*      */ 
/*      */     
/* 4776 */     FormTextField INK23 = new FormTextField("INK23", "", false, 2, 2);
/* 4777 */     INK23.setTabIndex(100);
/* 4778 */     INK23.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4779 */     INK23.setId("Box (Set) / Ink");
/* 4780 */     bomDisk.addElement(INK23);
/*      */ 
/*      */     
/* 4783 */     FormTextField SEL3 = new FormTextField("SEL3", "", false, 10, 20);
/* 4784 */     SEL3.setTabIndex(101);
/* 4785 */     SEL3.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4786 */     SEL3.setId("Box (Set) / Size");
/* 4787 */     bomDisk.addElement(SEL3);
/*      */ 
/*      */ 
/*      */     
/* 4791 */     FormTextField INF3 = new FormTextField("INF3", "", false, 20, 120);
/* 4792 */     INF3.setTabIndex(102);
/* 4793 */     INF3.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4794 */     INF3.setId("Box (Set) / Additional Information");
/* 4795 */     bomDisk.addElement(INF3);
/*      */ 
/*      */     
/* 4798 */     FormCheckBox PID18 = new FormCheckBox("PID18", "on", "", false);
/* 4799 */     PID18.setId("PID18");
/* 4800 */     PID18.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4801 */     PID18.setTabIndex(103);
/* 4802 */     PID18.setId("Other");
/* 4803 */     bomDisk.addElement(PID18);
/*      */ 
/*      */     
/* 4806 */     Vector sid18 = MilestoneHelper.getBomSuppliers(18, typeFlag);
/* 4807 */     FormDropDownMenu SID18 = MilestoneHelper.getLookupDropDown("SID18", sid18, "", false, false);
/* 4808 */     SID18.setTabIndex(104);
/* 4809 */     SID18.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4810 */     SID18.setId("Other / Supplier");
/* 4811 */     bomDisk.addElement(SID18);
/*      */ 
/*      */     
/* 4814 */     FormTextField INK118 = new FormTextField("INK118", "", false, 2, 2);
/* 4815 */     INK118.setTabIndex(105);
/* 4816 */     INK118.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4817 */     INK118.setId("Other / Ink");
/* 4818 */     bomDisk.addElement(INK118);
/*      */ 
/*      */     
/* 4821 */     FormTextField INK218 = new FormTextField("INK218", "", false, 2, 2);
/* 4822 */     INK218.setTabIndex(106);
/* 4823 */     INK218.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4824 */     INK218.setId("Other / Ink");
/* 4825 */     bomDisk.addElement(INK218);
/*      */ 
/*      */     
/* 4828 */     FormTextField INF18 = new FormTextField("INF18", "", false, 20, 120);
/* 4829 */     INF18.setTabIndex(107);
/* 4830 */     INF18.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4831 */     INF18.setId("Other / Additional Information");
/* 4832 */     bomDisk.addElement(INF18);
/*      */     
/* 4834 */     return bomDisk;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Form buildNewCassette(Form bomCassette, Selection selection, Bom bom) {
/* 4842 */     FormCheckBox PID5 = new FormCheckBox("PID5", "on", "", false);
/* 4843 */     PID5.setId("PID5");
/* 4844 */     PID5.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4845 */     PID5.setTabIndex(14);
/* 4846 */     PID5.setId("C-0");
/* 4847 */     bomCassette.addElement(PID5);
/*      */ 
/*      */     
/* 4850 */     FormTextField INK15 = new FormTextField("INK15", "", false, 2, 2);
/* 4851 */     INK15.setTabIndex(15);
/* 4852 */     INK15.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4853 */     INK15.setId("C-0 / Ink");
/* 4854 */     bomCassette.addElement(INK15);
/*      */ 
/*      */     
/* 4857 */     Vector sid5 = MilestoneHelper.getBomSuppliers(5);
/* 4858 */     FormDropDownMenu SID5 = MilestoneHelper.getLookupDropDown("SID5", sid5, String.valueOf(-1), false, false);
/* 4859 */     SID5.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4860 */     SID5.setTabIndex(23);
/* 4861 */     SID5.setId("C-0");
/* 4862 */     bomCassette.addElement(SID5);
/*      */ 
/*      */     
/* 4865 */     Vector sid16 = MilestoneHelper.getBomSuppliers(16);
/*      */     
/* 4867 */     FormDropDownMenu SID16 = MilestoneHelper.getLookupDropDown("SID16", sid16, String.valueOf(-1), false, false);
/* 4868 */     SID16.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4869 */     SID16.setTabIndex(23);
/* 4870 */     SID16.setId("Norelco");
/* 4871 */     bomCassette.addElement(SID16);
/*      */     
/* 4873 */     FormTextField INK25 = new FormTextField("INK25", "", false, 2, 2);
/* 4874 */     INK25.setTabIndex(16);
/* 4875 */     INK25.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4876 */     INK25.setId("C-0 / Ink");
/* 4877 */     bomCassette.addElement(INK25);
/*      */ 
/*      */     
/* 4880 */     Vector sel5 = Cache.getInstance().getLookupDetailValuesFromDatabase(46);
/* 4881 */     FormDropDownMenu SEL5 = MilestoneHelper.getLookupDropDownBom("SEL5", sel5, "", false, true);
/* 4882 */     SEL5.setTabIndex(17);
/* 4883 */     SEL5.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4884 */     SEL5.setId("C-0 / Additional Information");
/* 4885 */     bomCassette.addElement(SEL5);
/*      */ 
/*      */     
/* 4888 */     FormTextField INF5 = new FormTextField("INF5", "", false, 20, 120);
/* 4889 */     SEL5.setTabIndex(18);
/* 4890 */     INF5.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4891 */     INF5.setId("C-0 / Additional Information");
/* 4892 */     bomCassette.addElement(INF5);
/*      */ 
/*      */     
/* 4895 */     FormCheckBox PID16 = new FormCheckBox("PID16", "on", "", false);
/* 4896 */     PID16.setId("PID16");
/* 4897 */     PID16.setTabIndex(19);
/* 4898 */     PID16.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4899 */     PID16.setId("Norelco");
/* 4900 */     bomCassette.addElement(PID16);
/*      */ 
/*      */     
/* 4903 */     Vector sel16 = Cache.getInstance().getLookupDetailValuesFromDatabase(46);
/* 4904 */     FormDropDownMenu SEL16 = MilestoneHelper.getLookupDropDownBom("SEL16", sel16, "", false, true);
/* 4905 */     SEL16.setTabIndex(20);
/* 4906 */     SEL16.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4907 */     SEL16.setId("Norelco / Additional Information");
/* 4908 */     bomCassette.addElement(SEL16);
/*      */ 
/*      */     
/* 4911 */     FormTextField INF16 = new FormTextField("INF16", "", false, 20, 120);
/* 4912 */     INF16.setTabIndex(21);
/* 4913 */     INF16.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4914 */     INF16.setId("Norelco / Additional Information");
/* 4915 */     bomCassette.addElement(INF16);
/*      */ 
/*      */     
/* 4918 */     FormCheckBox PID13 = new FormCheckBox("PID13", "on", "", false);
/* 4919 */     PID13.setId("PID13");
/* 4920 */     PID13.setTabIndex(22);
/* 4921 */     PID13.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4922 */     PID13.setId("J-Card");
/* 4923 */     bomCassette.addElement(PID13);
/*      */ 
/*      */     
/* 4926 */     Vector sid13 = MilestoneHelper.getBomSuppliers(13);
/* 4927 */     FormDropDownMenu SID13 = MilestoneHelper.getLookupDropDown("SID13", sid13, "", false, false);
/* 4928 */     SID13.setTabIndex(23);
/* 4929 */     SID13.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4930 */     SID13.setId("J-Card / Supplier");
/* 4931 */     bomCassette.addElement(SID13);
/*      */ 
/*      */     
/* 4934 */     FormTextField INK113 = new FormTextField("INK113", "", false, 2, 2);
/* 4935 */     INK113.setTabIndex(24);
/* 4936 */     INK113.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4937 */     INK113.setId("J-Card / Ink");
/* 4938 */     bomCassette.addElement(INK113);
/*      */ 
/*      */     
/* 4941 */     FormTextField INK213 = new FormTextField("INK213", "", false, 2, 2);
/* 4942 */     INK213.setTabIndex(25);
/* 4943 */     INK213.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4944 */     INK213.setId("J-Card / Ink");
/* 4945 */     bomCassette.addElement(INK213);
/*      */ 
/*      */     
/* 4948 */     FormTextField SEL13 = new FormTextField("SEL13", "", false, 6, 6);
/* 4949 */     SEL13.setTabIndex(26);
/* 4950 */     SEL13.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4951 */     SEL13.setId("J-Card / Panels");
/* 4952 */     bomCassette.addElement(SEL13);
/*      */ 
/*      */     
/* 4955 */     FormTextField INF13 = new FormTextField("INF13", "", false, 20, 120);
/* 4956 */     INF13.setTabIndex(27);
/* 4957 */     INF13.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4958 */     INF13.setId("J-Card / Additional Information");
/* 4959 */     bomCassette.addElement(INF13);
/*      */ 
/*      */     
/* 4962 */     FormCheckBox PID24 = new FormCheckBox("PID24", "on", "", false);
/* 4963 */     PID24.setId("PID24");
/* 4964 */     PID24.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4965 */     PID24.setTabIndex(27);
/* 4966 */     PID24.setId("U-Card");
/* 4967 */     bomCassette.addElement(PID24);
/*      */ 
/*      */     
/* 4970 */     Vector sid24 = MilestoneHelper.getBomSuppliers(24);
/* 4971 */     FormDropDownMenu SID24 = MilestoneHelper.getLookupDropDown("SID24", sid24, "", false, false);
/* 4972 */     SID24.setTabIndex(28);
/* 4973 */     SID24.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4974 */     SID24.setId("U-Card / Supplier");
/* 4975 */     bomCassette.addElement(SID24);
/*      */ 
/*      */     
/* 4978 */     FormTextField INK124 = new FormTextField("INK124", "", false, 2, 2);
/* 4979 */     INK124.setTabIndex(29);
/* 4980 */     INK124.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4981 */     INK124.setId("U-Card / Ink");
/* 4982 */     bomCassette.addElement(INK124);
/*      */ 
/*      */     
/* 4985 */     FormTextField INK224 = new FormTextField("INK224", "", false, 2, 2);
/* 4986 */     INK224.setTabIndex(30);
/* 4987 */     INK224.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4988 */     INK224.setId("U-Card / Ink");
/* 4989 */     bomCassette.addElement(INK224);
/*      */ 
/*      */     
/* 4992 */     FormTextField SEL24 = new FormTextField("SEL24", "", false, 6, 6);
/* 4993 */     SEL24.setTabIndex(31);
/* 4994 */     SEL24.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 4995 */     SEL24.setId("U-Card / Panels");
/* 4996 */     bomCassette.addElement(SEL24);
/*      */ 
/*      */     
/* 4999 */     FormTextField INF24 = new FormTextField("INF24", "", false, 20, 120);
/* 5000 */     INF24.setTabIndex(31);
/* 5001 */     INF24.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5002 */     INF24.setId("U-Card / Additional Information");
/* 5003 */     bomCassette.addElement(INF24);
/*      */ 
/*      */     
/* 5006 */     FormCheckBox PID17 = new FormCheckBox("PID17", "on", "", false);
/* 5007 */     PID17.setTabIndex(32);
/* 5008 */     PID17.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5009 */     PID17.setId("PID17");
/* 5010 */     PID17.setId("O-Card");
/* 5011 */     bomCassette.addElement(PID17);
/*      */ 
/*      */     
/* 5014 */     Vector sid17 = MilestoneHelper.getBomSuppliers(17);
/* 5015 */     FormDropDownMenu SID17 = MilestoneHelper.getLookupDropDown("SID17", sid17, "", false, false);
/* 5016 */     SID17.setTabIndex(33);
/* 5017 */     SID17.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5018 */     SID17.setId("O-Card / Supplier");
/* 5019 */     bomCassette.addElement(SID17);
/*      */ 
/*      */     
/* 5022 */     FormTextField INK117 = new FormTextField("INK117", "", false, 2, 2);
/* 5023 */     INK117.setTabIndex(34);
/* 5024 */     INK117.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5025 */     INK117.setId("O-Card / Ink");
/* 5026 */     bomCassette.addElement(INK117);
/*      */ 
/*      */     
/* 5029 */     FormTextField INK217 = new FormTextField("INK217", "", false, 2, 2);
/* 5030 */     INK217.setTabIndex(35);
/* 5031 */     INK217.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5032 */     INK217.setId("O-Card / Ink");
/* 5033 */     bomCassette.addElement(INK217);
/*      */ 
/*      */     
/* 5036 */     FormTextField INF17 = new FormTextField("INF17", "", false, 20, 120);
/* 5037 */     INF17.setTabIndex(36);
/* 5038 */     INF17.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5039 */     INF17.setId("O-Card / Additional Information");
/* 5040 */     bomCassette.addElement(INF17);
/*      */ 
/*      */     
/* 5043 */     FormCheckBox PID21 = new FormCheckBox("PID21", "on", "", false);
/* 5044 */     PID21.setId("PID21");
/* 5045 */     PID21.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5046 */     PID21.setTabIndex(37);
/* 5047 */     PID21.setId("Sticker1");
/* 5048 */     bomCassette.addElement(PID21);
/*      */ 
/*      */     
/* 5051 */     Vector sid21 = MilestoneHelper.getBomSuppliers(21);
/* 5052 */     FormDropDownMenu SID21 = MilestoneHelper.getLookupDropDown("SID21", sid21, "", false, false);
/* 5053 */     SID21.setTabIndex(38);
/* 5054 */     SID21.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5055 */     SID21.setId("Sticker1 / Supplier");
/* 5056 */     bomCassette.addElement(SID21);
/*      */ 
/*      */     
/* 5059 */     FormTextField INK121 = new FormTextField("INK121", "", false, 2, 2);
/* 5060 */     INK121.setTabIndex(39);
/* 5061 */     INK121.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5062 */     INK121.setId("Sticker1 / Ink");
/* 5063 */     bomCassette.addElement(INK121);
/*      */ 
/*      */     
/* 5066 */     FormTextField INK221 = new FormTextField("INK221", "", false, 2, 2);
/* 5067 */     INK221.setTabIndex(40);
/* 5068 */     INK221.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5069 */     INK121.setId("Sticker1 / Ink");
/* 5070 */     bomCassette.addElement(INK221);
/*      */ 
/*      */     
/* 5073 */     FormTextField SEL21 = new FormTextField("SEL21", "", false, 6, 6);
/* 5074 */     SEL21.setTabIndex(41);
/* 5075 */     SEL21.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5076 */     SEL21.setId("Sticker1 / Places");
/* 5077 */     bomCassette.addElement(SEL21);
/*      */ 
/*      */     
/* 5080 */     FormTextField INF21 = new FormTextField("INF21", "", false, 20, 120);
/* 5081 */     INF21.setTabIndex(42);
/* 5082 */     INF21.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5083 */     INF21.setId("Sticker1 / Additional Information");
/* 5084 */     bomCassette.addElement(INF21);
/*      */ 
/*      */     
/* 5087 */     FormCheckBox PID22 = new FormCheckBox("PID22", "on", "", false);
/* 5088 */     PID22.setId("PID22");
/* 5089 */     PID22.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5090 */     PID22.setTabIndex(43);
/* 5091 */     PID22.setId("Sticker2");
/* 5092 */     bomCassette.addElement(PID22);
/*      */ 
/*      */     
/* 5095 */     Vector sid22 = MilestoneHelper.getBomSuppliers(22);
/* 5096 */     FormDropDownMenu SID22 = MilestoneHelper.getLookupDropDown("SID22", sid22, "", false, false);
/* 5097 */     SID22.setTabIndex(44);
/* 5098 */     SID22.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5099 */     SID22.setId("Sticker2 / Supplier");
/* 5100 */     bomCassette.addElement(SID22);
/*      */ 
/*      */     
/* 5103 */     FormTextField INK122 = new FormTextField("INK122", "", false, 2, 2);
/* 5104 */     INK122.setTabIndex(45);
/* 5105 */     INK122.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5106 */     INK122.setId("Sticker2 / Ink");
/* 5107 */     bomCassette.addElement(INK122);
/*      */ 
/*      */     
/* 5110 */     FormTextField INK222 = new FormTextField("INK222", "", false, 2, 2);
/* 5111 */     INK222.setTabIndex(46);
/* 5112 */     INK222.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5113 */     INK222.setId("Sticker2 / Ink");
/* 5114 */     bomCassette.addElement(INK222);
/*      */ 
/*      */     
/* 5117 */     FormTextField SEL22 = new FormTextField("SEL22", "", false, 6, 6);
/* 5118 */     SEL22.setTabIndex(47);
/* 5119 */     SEL22.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5120 */     SEL22.setId("Sticker2 / Places");
/* 5121 */     bomCassette.addElement(SEL22);
/*      */ 
/*      */     
/* 5124 */     FormTextField INF22 = new FormTextField("INF22", "", false, 20, 120);
/* 5125 */     INF22.setTabIndex(48);
/* 5126 */     INF22.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5127 */     INF22.setId("Sticker2 / Additional Information");
/* 5128 */     bomCassette.addElement(INF22);
/*      */ 
/*      */     
/* 5131 */     FormCheckBox PID18 = new FormCheckBox("PID18", "on", "", false);
/* 5132 */     PID18.setId("PID18");
/* 5133 */     PID18.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5134 */     PID18.setTabIndex(49);
/* 5135 */     PID18.setId("Other");
/* 5136 */     bomCassette.addElement(PID18);
/*      */ 
/*      */     
/* 5139 */     Vector sid18 = MilestoneHelper.getBomSuppliers(18);
/* 5140 */     FormDropDownMenu SID18 = MilestoneHelper.getLookupDropDown("SID18", sid18, "", false, false);
/* 5141 */     SID18.setTabIndex(50);
/* 5142 */     SID18.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5143 */     SID18.setId("Other / Supplier");
/* 5144 */     bomCassette.addElement(SID18);
/*      */ 
/*      */     
/* 5147 */     FormTextField INK118 = new FormTextField("INK118", "", false, 2, 2);
/* 5148 */     INK118.setTabIndex(51);
/* 5149 */     INK118.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5150 */     INK118.setId("Other / Ink");
/* 5151 */     bomCassette.addElement(INK118);
/*      */ 
/*      */     
/* 5154 */     FormTextField INK218 = new FormTextField("INK218", "", false, 2, 2);
/* 5155 */     INK218.setTabIndex(52);
/* 5156 */     INK218.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5157 */     INK218.setId("Other / Ink");
/* 5158 */     bomCassette.addElement(INK218);
/*      */ 
/*      */     
/* 5161 */     FormTextField INF18 = new FormTextField("INF18", "", false, 50, 120);
/* 5162 */     INF18.setTabIndex(53);
/* 5163 */     INF18.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5164 */     INF18.setId("Other / Additional Information");
/* 5165 */     bomCassette.addElement(INF18);
/*      */     
/* 5167 */     return bomCassette;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Form buildNewVinyl(Form bomVinyl, Selection selection, Bom bom) {
/* 5175 */     FormCheckBox PID19 = new FormCheckBox("PID19", "on", "", false);
/* 5176 */     PID19.setId("PID19");
/* 5177 */     PID19.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5178 */     PID19.setTabIndex(14);
/* 5179 */     PID19.setId("Record");
/* 5180 */     bomVinyl.addElement(PID19);
/*      */ 
/*      */     
/* 5183 */     Vector sid19 = MilestoneHelper.getBomSuppliers(19);
/* 5184 */     FormDropDownMenu SID19 = MilestoneHelper.getLookupDropDown("SID19", sid19, "", false, false);
/* 5185 */     SID19.setTabIndex(15);
/* 5186 */     SID19.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5187 */     SID19.setId("Record / Supplier");
/* 5188 */     bomVinyl.addElement(SID19);
/*      */ 
/*      */     
/* 5191 */     Vector sel19 = Cache.getInstance().getLookupDetailValuesFromDatabase(46);
/* 5192 */     FormDropDownMenu SEL19 = MilestoneHelper.getLookupDropDownBom("SEL19", sel19, "", false, true);
/* 5193 */     SEL19.setTabIndex(16);
/* 5194 */     SEL19.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5195 */     SEL19.setId("Record / Color");
/* 5196 */     bomVinyl.addElement(SEL19);
/*      */ 
/*      */     
/* 5199 */     FormTextField INF19 = new FormTextField("INF19", "", false, 20, 120);
/* 5200 */     INF19.setTabIndex(17);
/* 5201 */     INF19.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5202 */     INF19.setId("Record / Additional Information");
/* 5203 */     bomVinyl.addElement(INF19);
/*      */ 
/*      */     
/* 5206 */     FormCheckBox PID14 = new FormCheckBox("PID14", "on", "", false);
/* 5207 */     PID14.setId("PID14");
/* 5208 */     PID14.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5209 */     PID14.setTabIndex(18);
/* 5210 */     PID14.setId("Label");
/* 5211 */     bomVinyl.addElement(PID14);
/*      */ 
/*      */     
/* 5214 */     Vector sid14 = MilestoneHelper.getBomSuppliers(14);
/* 5215 */     FormDropDownMenu SID14 = MilestoneHelper.getLookupDropDown("SID14", sid14, "", false, false);
/* 5216 */     SID14.setTabIndex(19);
/* 5217 */     SID14.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5218 */     SID14.setId("Label / Supplier");
/* 5219 */     bomVinyl.addElement(SID14);
/*      */ 
/*      */     
/* 5222 */     FormTextField INK114 = new FormTextField("INK114", "", false, 2, 2);
/* 5223 */     INK114.setTabIndex(20);
/* 5224 */     INK114.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5225 */     INK114.setId("Label / Ink");
/* 5226 */     bomVinyl.addElement(INK114);
/*      */ 
/*      */     
/* 5229 */     FormTextField INK214 = new FormTextField("INK214", "", false, 2, 2);
/* 5230 */     INK214.setTabIndex(21);
/* 5231 */     INK214.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5232 */     INK214.setId("Label / Ink");
/* 5233 */     bomVinyl.addElement(INK214);
/*      */ 
/*      */     
/* 5236 */     FormTextField INF14 = new FormTextField("INF14", "", false, 50, 120);
/* 5237 */     INF14.setTabIndex(22);
/* 5238 */     INF14.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5239 */     INF14.setId("Label / Additional Information");
/* 5240 */     bomVinyl.addElement(INF14);
/*      */ 
/*      */     
/* 5243 */     FormCheckBox PID20 = new FormCheckBox("PID20", "on", "", false);
/* 5244 */     PID20.setId("PID20");
/* 5245 */     PID20.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5246 */     PID20.setTabIndex(23);
/* 5247 */     PID20.setId("Sleeve");
/* 5248 */     bomVinyl.addElement(PID20);
/*      */ 
/*      */     
/* 5251 */     Vector sid20 = MilestoneHelper.getBomSuppliers(20);
/* 5252 */     FormDropDownMenu SID20 = MilestoneHelper.getLookupDropDown("SID20", sid20, "", false, false);
/* 5253 */     SID20.setTabIndex(24);
/* 5254 */     SID20.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5255 */     SID20.setId("Sleeve / Supplier");
/* 5256 */     bomVinyl.addElement(SID20);
/*      */ 
/*      */     
/* 5259 */     FormTextField INK120 = new FormTextField("INK120", "", false, 2, 2);
/* 5260 */     INK120.setTabIndex(25);
/* 5261 */     INK120.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5262 */     INK120.setId("Sleeve / Ink");
/* 5263 */     bomVinyl.addElement(INK120);
/*      */ 
/*      */     
/* 5266 */     FormTextField INK220 = new FormTextField("INK220", "", false, 2, 2);
/* 5267 */     INK220.setTabIndex(26);
/* 5268 */     INK220.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5269 */     INK220.setId("Sleeve / Ink");
/* 5270 */     bomVinyl.addElement(INK220);
/*      */ 
/*      */     
/* 5273 */     FormTextField INF20 = new FormTextField("INF20", "", false, 50, 120);
/* 5274 */     INF20.setTabIndex(27);
/* 5275 */     INF20.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5276 */     INF20.setId("Sleeve / Additional Information");
/* 5277 */     bomVinyl.addElement(INF20);
/*      */ 
/*      */     
/* 5280 */     FormCheckBox PID11 = new FormCheckBox("PID11", "on", "", false);
/* 5281 */     PID11.setId("PID11");
/* 5282 */     PID11.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5283 */     PID11.setTabIndex(28);
/* 5284 */     PID11.setId("Jacket");
/* 5285 */     bomVinyl.addElement(PID11);
/*      */ 
/*      */     
/* 5288 */     Vector sid11 = MilestoneHelper.getBomSuppliers(11);
/* 5289 */     FormDropDownMenu SID11 = MilestoneHelper.getLookupDropDown("SID11", sid11, "", false, false);
/* 5290 */     SID11.setTabIndex(29);
/* 5291 */     SID11.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5292 */     SID11.setId("Jacket / Suppliers");
/* 5293 */     bomVinyl.addElement(SID11);
/*      */ 
/*      */     
/* 5296 */     FormTextField INK111 = new FormTextField("INK111", "", false, 2, 2);
/* 5297 */     INK111.setTabIndex(30);
/* 5298 */     INK111.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5299 */     INK111.setId("Jacket / Ink");
/* 5300 */     bomVinyl.addElement(INK111);
/*      */ 
/*      */     
/* 5303 */     FormTextField INK211 = new FormTextField("INK211", "", false, 2, 2);
/* 5304 */     INK211.setTabIndex(31);
/* 5305 */     INK211.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5306 */     INK211.setId("Jacket / Ink");
/* 5307 */     bomVinyl.addElement(INK211);
/*      */ 
/*      */     
/* 5310 */     FormTextField INF11 = new FormTextField("INF11", "", false, 50, 120);
/* 5311 */     INF11.setTabIndex(32);
/* 5312 */     INF11.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5313 */     INF11.setId("Jacket / Additional Information");
/* 5314 */     bomVinyl.addElement(INF11);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5320 */     FormCheckBox PID33 = new FormCheckBox("PID33", "on", "", false);
/* 5321 */     PID33.setId("PID33");
/* 5322 */     PID33.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5323 */     PID33.setTabIndex(33);
/* 5324 */     PID33.setId("Insert");
/* 5325 */     bomVinyl.addElement(PID33);
/*      */ 
/*      */     
/* 5328 */     Vector sid33 = MilestoneHelper.getBomSuppliers(33);
/* 5329 */     FormDropDownMenu SID33 = MilestoneHelper.getLookupDropDown("SID33", sid33, "", false, false);
/* 5330 */     SID33.setTabIndex(34);
/* 5331 */     SID33.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5332 */     SID33.setId("Insert / Suppliers");
/* 5333 */     bomVinyl.addElement(SID33);
/*      */ 
/*      */     
/* 5336 */     FormTextField INK133 = new FormTextField("INK133", "", false, 2, 2);
/* 5337 */     INK133.setTabIndex(35);
/* 5338 */     INK133.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5339 */     INK133.setId("Insert / Ink");
/* 5340 */     bomVinyl.addElement(INK133);
/*      */ 
/*      */     
/* 5343 */     FormTextField INK233 = new FormTextField("INK233", "", false, 2, 2);
/* 5344 */     INK233.setTabIndex(36);
/* 5345 */     INK233.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5346 */     INK233.setId("Insert / Ink");
/* 5347 */     bomVinyl.addElement(INK233);
/*      */ 
/*      */     
/* 5350 */     FormTextField INF33 = new FormTextField("INF33", "", false, 50, 120);
/* 5351 */     INF33.setTabIndex(37);
/* 5352 */     INF33.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5353 */     INF33.setId("Insert / Additional Information");
/* 5354 */     bomVinyl.addElement(INF33);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5362 */     FormCheckBox PID21 = new FormCheckBox("PID21", "on", "", false);
/* 5363 */     PID21.setId("PID21");
/* 5364 */     PID21.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5365 */     PID21.setTabIndex(38);
/* 5366 */     PID21.setId("Sticker1");
/* 5367 */     bomVinyl.addElement(PID21);
/*      */ 
/*      */     
/* 5370 */     Vector sid21 = MilestoneHelper.getBomSuppliers(21, "0,1,3");
/* 5371 */     FormDropDownMenu SID21 = MilestoneHelper.getLookupDropDown("SID21", sid21, "", false, false);
/* 5372 */     SID21.setTabIndex(39);
/* 5373 */     SID21.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5374 */     SID21.setId("Sticker1 / Supplier");
/* 5375 */     bomVinyl.addElement(SID21);
/*      */ 
/*      */     
/* 5378 */     FormTextField INK121 = new FormTextField("INK121", "", false, 2, 2);
/* 5379 */     INK121.setTabIndex(40);
/* 5380 */     INK121.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5381 */     INK121.setId("Sticker1 / Ink");
/* 5382 */     bomVinyl.addElement(INK121);
/*      */ 
/*      */     
/* 5385 */     FormTextField INK221 = new FormTextField("INK221", "", false, 2, 2);
/* 5386 */     INK221.setTabIndex(41);
/* 5387 */     INK221.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5388 */     INK221.setId("Sticker1 / Ink");
/* 5389 */     bomVinyl.addElement(INK221);
/*      */ 
/*      */     
/* 5392 */     FormTextField SEL21 = new FormTextField("SEL21", "", false, 6, 6);
/* 5393 */     SEL21.setTabIndex(42);
/* 5394 */     SEL21.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5395 */     SEL21.setId("Sticker1 / Places");
/* 5396 */     bomVinyl.addElement(SEL21);
/*      */ 
/*      */     
/* 5399 */     FormTextField INF21 = new FormTextField("INF21", "", false, 20, 120);
/* 5400 */     INF21.setTabIndex(43);
/* 5401 */     INF21.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5402 */     INF21.setId("Sticker1 / Additional Information");
/* 5403 */     bomVinyl.addElement(INF21);
/*      */ 
/*      */     
/* 5406 */     FormCheckBox PID22 = new FormCheckBox("PID22", "on", "", false);
/* 5407 */     PID22.setId("PID22");
/* 5408 */     PID22.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5409 */     PID22.setTabIndex(44);
/* 5410 */     PID22.setId("Sticker2");
/* 5411 */     bomVinyl.addElement(PID22);
/*      */ 
/*      */     
/* 5414 */     Vector sid22 = MilestoneHelper.getBomSuppliers(22, "0,1,3");
/* 5415 */     FormDropDownMenu SID22 = MilestoneHelper.getLookupDropDown("SID22", sid22, "", false, false);
/* 5416 */     SID22.setTabIndex(45);
/* 5417 */     SID22.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5418 */     SID22.setId("Sticker2 / Suppliers");
/* 5419 */     bomVinyl.addElement(SID22);
/*      */ 
/*      */     
/* 5422 */     FormTextField INK122 = new FormTextField("INK122", "", false, 2, 2);
/* 5423 */     INK122.setTabIndex(46);
/* 5424 */     INK122.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5425 */     INK122.setId("Sticker2 / Ink");
/* 5426 */     bomVinyl.addElement(INK122);
/*      */ 
/*      */     
/* 5429 */     FormTextField INK222 = new FormTextField("INK222", "", false, 2, 2);
/* 5430 */     INK222.setTabIndex(47);
/* 5431 */     INK222.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5432 */     INK222.setId("Sticker2 / Ink");
/* 5433 */     bomVinyl.addElement(INK222);
/*      */ 
/*      */     
/* 5436 */     FormTextField SEL22 = new FormTextField("SEL22", "", false, 6, 6);
/* 5437 */     SEL22.setTabIndex(48);
/* 5438 */     SEL22.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5439 */     SEL22.setId("Sticker2 / Places");
/* 5440 */     bomVinyl.addElement(SEL22);
/*      */ 
/*      */     
/* 5443 */     FormTextField INF22 = new FormTextField("INF22", "", false, 20, 120);
/* 5444 */     INF22.setTabIndex(49);
/* 5445 */     INF22.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5446 */     INF22.setId("Sticker2 / Additional Information");
/* 5447 */     bomVinyl.addElement(INF22);
/*      */ 
/*      */     
/* 5450 */     FormCheckBox PID18 = new FormCheckBox("PID18", "on", "", false);
/* 5451 */     PID18.setId("PID18");
/* 5452 */     PID18.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5453 */     PID18.setTabIndex(50);
/* 5454 */     PID18.setId("Other");
/* 5455 */     bomVinyl.addElement(PID18);
/*      */ 
/*      */     
/* 5458 */     Vector sid18 = MilestoneHelper.getBomSuppliers(18);
/* 5459 */     FormDropDownMenu SID18 = MilestoneHelper.getLookupDropDown("SID18", sid18, "", false, false);
/* 5460 */     SID18.setTabIndex(51);
/* 5461 */     SID18.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5462 */     SID18.setId("Other / Supplier");
/* 5463 */     bomVinyl.addElement(SID18);
/*      */ 
/*      */     
/* 5466 */     FormTextField INK118 = new FormTextField("INK118", "", false, 2, 2);
/* 5467 */     INK118.setTabIndex(52);
/* 5468 */     INK118.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5469 */     INK118.setId("Other / Ink");
/* 5470 */     bomVinyl.addElement(INK118);
/*      */ 
/*      */     
/* 5473 */     FormTextField INK218 = new FormTextField("INK218", "", false, 2, 2);
/* 5474 */     INK218.setTabIndex(53);
/* 5475 */     INK218.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5476 */     INK218.setId("Other / Ink");
/* 5477 */     bomVinyl.addElement(INK218);
/*      */ 
/*      */     
/* 5480 */     FormTextField INF18 = new FormTextField("INF18", "", false, 50, 120);
/* 5481 */     INF18.setTabIndex(54);
/* 5482 */     INF18.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5483 */     INF18.setId("Other / Additional Information");
/* 5484 */     bomVinyl.addElement(INF18);
/*      */     
/* 5486 */     return bomVinyl;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Form buildNewDVD(Form bomDVD, Selection selection, Bom bom) {
/* 5495 */     Vector selDisc = Cache.getInstance().getLookupDetailValuesFromDatabase(62);
/* 5496 */     FormDropDownMenu SELDISC = MilestoneHelper.getLookupDropDownBom("SELDISC", selDisc, "", false, true);
/* 5497 */     SELDISC.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5498 */     SELDISC.setTabIndex(17);
/* 5499 */     SELDISC.setId("Disc / Additional Information");
/* 5500 */     bomDVD.addElement(SELDISC);
/*      */ 
/*      */     
/* 5503 */     FormCheckBox PID25 = new FormCheckBox("PID25", "on", "", false);
/* 5504 */     PID25.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5505 */     PID25.setTabIndex(21);
/* 5506 */     PID25.setId("Wrap");
/* 5507 */     bomDVD.addElement(PID25);
/*      */ 
/*      */     
/* 5510 */     Vector sid25 = MilestoneHelper.getBomSuppliers(29, "1,2");
/* 5511 */     FormDropDownMenu SID25 = MilestoneHelper.getLookupDropDown("SID25", sid25, "", false, false);
/* 5512 */     SID25.setTabIndex(22);
/* 5513 */     SID25.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5514 */     SID25.setId("Wrap / Supplier");
/* 5515 */     bomDVD.addElement(SID25);
/*      */ 
/*      */     
/* 5518 */     FormTextField INK125 = new FormTextField("INK125", "", false, 2, 2);
/* 5519 */     INK125.setTabIndex(23);
/* 5520 */     INK125.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5521 */     INK125.setId("Wrap / Ink");
/* 5522 */     bomDVD.addElement(INK125);
/*      */ 
/*      */     
/* 5525 */     FormTextField INK225 = new FormTextField("INK225", "", false, 2, 2);
/* 5526 */     INK225.setTabIndex(24);
/* 5527 */     INK225.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5528 */     INK225.setId("Wrap / Ink");
/* 5529 */     bomDVD.addElement(INK225);
/*      */ 
/*      */     
/* 5532 */     FormTextField INF25 = new FormTextField("INF25", "", false, 50, 120);
/* 5533 */     INF25.setTabIndex(25);
/* 5534 */     INF25.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5535 */     INF25.setId("Wrap / Additional Information");
/* 5536 */     bomDVD.addElement(INF25);
/*      */ 
/*      */ 
/*      */     
/* 5540 */     FormCheckBox PID26 = new FormCheckBox("PID26", "on", "", false);
/* 5541 */     PID26.setId("PID26");
/* 5542 */     PID26.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5543 */     PID26.setTabIndex(25);
/* 5544 */     PID26.setId("DVD Case");
/* 5545 */     bomDVD.addElement(PID26);
/*      */ 
/*      */     
/* 5548 */     Vector sel26 = Cache.getInstance().getLookupDetailValuesFromDatabase(64);
/* 5549 */     FormDropDownMenu SEL26 = MilestoneHelper.getLookupDropDownBom("SEL26", sel26, "", false, true);
/* 5550 */     SEL26.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5551 */     SEL26.setTabIndex(26);
/* 5552 */     SEL26.setId("Disc / Additional Information");
/* 5553 */     bomDVD.addElement(SEL26);
/*      */ 
/*      */     
/* 5556 */     FormTextField INF26 = new FormTextField("INF26", "", false, 20, 120);
/* 5557 */     INF26.setTabIndex(26);
/* 5558 */     INF26.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5559 */     INF26.setId("DVD Case / Additional Information");
/* 5560 */     bomDVD.addElement(INF26);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5568 */     FormCheckBox PID32 = new FormCheckBox("PID32", "on", "", false);
/* 5569 */     PID32.setId("PID32");
/* 5570 */     PID32.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5571 */     PID32.setTabIndex(27);
/* 5572 */     PID32.setId("Blu-Ray Case");
/* 5573 */     bomDVD.addElement(PID32);
/*      */ 
/*      */     
/* 5576 */     Vector sel32 = Cache.getInstance().getLookupDetailValuesFromDatabase(66);
/* 5577 */     FormDropDownMenu SEL32 = MilestoneHelper.getLookupDropDownBom("SEL32", sel32, "", false, true);
/* 5578 */     SEL32.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5579 */     SEL32.setTabIndex(28);
/* 5580 */     SEL32.setId("Blu-Ray Case / Additional Information");
/* 5581 */     bomDVD.addElement(SEL32);
/*      */ 
/*      */     
/* 5584 */     FormTextField INF32 = new FormTextField("INF32", "", false, 20, 120);
/* 5585 */     INF32.setTabIndex(29);
/* 5586 */     INF32.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 5587 */     INF32.setId("Blu-Ray Case / Additional Information");
/* 5588 */     bomDVD.addElement(INF32);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5594 */     return buildNewDisk(bomDVD, selection, bom);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setButtonVisibilities(Selection selection, User user, Context context, int level, String command) {
/* 5616 */     String copyVisible = "false";
/* 5617 */     String saveVisible = "false";
/* 5618 */     String deleteVisible = "false";
/* 5619 */     String newVisible = "false";
/*      */     
/* 5621 */     if (level > 1) {
/*      */       
/* 5623 */       saveVisible = "true";
/* 5624 */       copyVisible = "true";
/* 5625 */       deleteVisible = "true";
/*      */       
/* 5627 */       if (selection.getSelectionID() > 0) {
/* 5628 */         newVisible = "true";
/*      */       }
/* 5630 */       if (command.indexOf("new") > -1 || command.indexOf("copy") > -1) {
/*      */         
/* 5632 */         saveVisible = "true";
/* 5633 */         copyVisible = "false";
/* 5634 */         deleteVisible = "false";
/* 5635 */         newVisible = "false";
/*      */       } 
/*      */     } 
/*      */     
/* 5639 */     context.putDelivery("saveVisible", saveVisible);
/*      */     
/* 5641 */     context.putDelivery("copyVisible", copyVisible);
/*      */     
/* 5643 */     context.putDelivery("deleteVisible", deleteVisible);
/*      */     
/* 5645 */     context.putDelivery("newVisible", newVisible);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getSelectionBomPermissions(Selection selection, User user) {
/* 5656 */     int level = 0;
/*      */     
/* 5658 */     if (selection != null && selection.getSelectionID() > -1) {
/*      */ 
/*      */ 
/*      */       
/* 5662 */       Environment env = selection.getEnvironment();
/*      */       
/* 5664 */       CompanyAcl companyAcl = MilestoneHelper.getScreenPermissions(env, user);
/* 5665 */       if (companyAcl != null)
/* 5666 */         level = companyAcl.getAccessBomForm(); 
/*      */     } 
/* 5668 */     return level;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean print(Dispatcher dispatcher, Context context, String command, int pdfRtf, boolean ie55, MessageObject messageObject) {
/* 5675 */     Selection selection = null;
/* 5676 */     if (command.equalsIgnoreCase("selectionSave")) {
/*      */       
/* 5678 */       selection = (Selection)context.getSessionValue("pfmBomSelection");
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 5683 */       selection = MilestoneHelper.getScreenSelection(context);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5691 */     if (messageObject != null && messageObject.selectionObj != null) {
/* 5692 */       selection = messageObject.selectionObj;
/*      */     }
/*      */     
/* 5695 */     Bom bom = null;
/* 5696 */     Form reportContent = buildNewForm(context, selection, "bom-print-pdf");
/* 5697 */     reportContent.setValues(context);
/*      */     
/* 5699 */     if (selection != null)
/*      */     {
/* 5701 */       bom = SelectionManager.getInstance().getBom(selection);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5708 */     if (messageObject != null && messageObject.bomObj != null) {
/* 5709 */       bom = messageObject.bomObj;
/*      */     }
/* 5711 */     if (bom != null && reportContent != null) {
/*      */ 
/*      */       
/* 5714 */       String reportTemplate = String.valueOf(ReportHandler.reportPath) + "\\";
/* 5715 */       String configuration = selection.getSelectionConfig().getSelectionConfigurationAbbreviation();
/*      */       
/* 5717 */       if (configuration.equalsIgnoreCase("CAS")) {
/*      */         
/* 5719 */         reportTemplate = String.valueOf(reportTemplate) + "bom_cassette.xml";
/*      */       }
/* 5721 */       else if (configuration.equalsIgnoreCase("VIN")) {
/*      */         
/* 5723 */         reportTemplate = String.valueOf(reportTemplate) + "bom_vinyl.xml";
/*      */       }
/*      */       else {
/*      */         
/* 5727 */         reportTemplate = String.valueOf(reportTemplate) + "bom_disc.xml";
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/* 5733 */         InputStream input = new FileInputStream(reportTemplate);
/*      */         
/* 5735 */         XStyleSheet report = 
/* 5736 */           (XStyleSheet)Builder.getBuilder(1, input).read(null);
/*      */         
/* 5738 */         if (report != null)
/*      */         {
/* 5740 */           fillReportForPrint(report, reportContent, selection, context);
/*      */         }
/*      */         
/* 5743 */         if (pdfRtf == 0) {
/*      */           
/* 5745 */           HttpServletResponse sresponse = context.getResponse();
/*      */           
/* 5747 */           String reportFilename = "report.pdf";
/*      */           
/* 5749 */           if (ie55) {
/*      */             
/* 5751 */             sresponse.setHeader("extension", "pdf");
/* 5752 */             sresponse.setContentType("application/pdf");
/* 5753 */             sresponse.setHeader("Content-disposition", "attachment; filename=" + reportFilename);
/*      */           }
/*      */           else {
/*      */             
/* 5757 */             sresponse.setHeader("extension", "pdf");
/* 5758 */             sresponse.setContentType("application/force-download");
/* 5759 */             sresponse.setHeader("Content-disposition", "attachment; filename=" + reportFilename);
/*      */           } 
/*      */           
/* 5762 */           ServletOutputStream servletOutputStream = sresponse.getOutputStream();
/* 5763 */           servletOutputStream.flush();
/*      */           
/* 5765 */           PDF4Generator pdfGenerator = new PDF4Generator(servletOutputStream);
/* 5766 */           pdfGenerator.generate(report);
/*      */           
/* 5768 */           servletOutputStream.close();
/*      */         } 
/*      */ 
/*      */         
/* 5772 */         if (pdfRtf == 2) {
/*      */           
/* 5774 */           String prefix = "";
/* 5775 */           if (selection.getPrefixID() != null && selection.getPrefixID().getAbbreviation() != null)
/* 5776 */             prefix = selection.getPrefixID().getAbbreviation(); 
/* 5777 */           EmailDistribution.generateFormReport(context, "BOM", report, selection.getSelectionNo(), 
/* 5778 */               prefix, selection.getUpc(), selection.getIsDigital(), messageObject);
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 5783 */         return true;
/*      */       
/*      */       }
/* 5786 */       catch (Exception e) {
/*      */         
/* 5788 */         e.printStackTrace();
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 5793 */     return edit(dispatcher, context, "");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void fillReportForPrint(XStyleSheet report, Form form, Selection selection, Context context) {
/* 5813 */     SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
/* 5814 */     String todayLong = formatter.format(new Date());
/* 5815 */     report.setElement("bom_top_date", todayLong);
/*      */     
/* 5817 */     Bom bom = (Bom)context.getSessionValue("Bom");
/*      */     
/* 5819 */     if (selection != null) {
/*      */       
/* 5821 */       int selectionID = selection.getSelectionID();
/* 5822 */       bom = SelectionManager.getInstance().getBom(selection);
/*      */     } 
/*      */     
/* 5825 */     if (bom != null) {
/*      */ 
/*      */ 
/*      */       
/* 5829 */       boolean isCDoverride = false;
/* 5830 */       if (bom.getFormat().equalsIgnoreCase("CDO")) {
/* 5831 */         isCDoverride = true;
/*      */       }
/*      */ 
/*      */       
/* 5835 */       DefaultTableLens table_contents = null;
/*      */ 
/*      */       
/* 5838 */       String configuration = selection.getSelectionConfig().getSelectionConfigurationAbbreviation();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 5845 */       if (configuration != null && configuration.equalsIgnoreCase("CAS")) {
/*      */         
/* 5847 */         table_contents = new DefaultTableLens(25, 20);
/*      */       }
/* 5849 */       else if (configuration != null && configuration.equalsIgnoreCase("VIN")) {
/*      */         
/* 5851 */         table_contents = new DefaultTableLens(25, 20);
/*      */       
/*      */       }
/* 5854 */       else if (configuration != null && configuration.equalsIgnoreCase("DVV") && 
/* 5855 */         !isCDoverride) {
/*      */         
/* 5857 */         table_contents = new DefaultTableLens(35, 20);
/*      */       }
/*      */       else {
/*      */         
/* 5861 */         table_contents = new DefaultTableLens(33, 20);
/*      */       } 
/*      */ 
/*      */       
/* 5865 */       table_contents.setHeaderRowCount(0);
/* 5866 */       table_contents.setColWidth(0, 16);
/* 5867 */       table_contents.setColWidth(1, 2);
/* 5868 */       table_contents.setColWidth(2, 15);
/* 5869 */       table_contents.setColWidth(3, 40);
/* 5870 */       table_contents.setColWidth(4, 2);
/* 5871 */       table_contents.setColWidth(5, 60);
/* 5872 */       table_contents.setColWidth(6, 40);
/* 5873 */       table_contents.setColWidth(7, 40);
/* 5874 */       table_contents.setColWidth(8, 2);
/* 5875 */       table_contents.setColWidth(9, 50);
/* 5876 */       table_contents.setColWidth(10, 10);
/* 5877 */       table_contents.setColWidth(11, 10);
/* 5878 */       table_contents.setColWidth(12, 20);
/* 5879 */       table_contents.setColWidth(13, 50);
/* 5880 */       table_contents.setColWidth(14, 60);
/* 5881 */       table_contents.setColWidth(15, 40);
/* 5882 */       table_contents.setColWidth(16, 80);
/* 5883 */       table_contents.setColWidth(17, 30);
/* 5884 */       table_contents.setColWidth(18, 20);
/* 5885 */       table_contents.setColWidth(19, 30);
/*      */ 
/*      */       
/* 5888 */       Font boldFont = new Font("Arial", 1, 10);
/* 5889 */       Font plainFont = new Font("Arial", 0, 10);
/*      */       
/* 5891 */       table_contents.setRowBorder(-1, 0);
/* 5892 */       table_contents.setColBorder(0);
/* 5893 */       table_contents.setRowBorderColor(Color.lightGray);
/* 5894 */       table_contents.setRowBorder(266240);
/*      */       
/* 5896 */       table_contents.setColFont(0, boldFont);
/*      */       
/* 5898 */       table_contents.setColAlignment(0, 33);
/* 5899 */       table_contents.setColAlignment(1, 33);
/* 5900 */       table_contents.setColAlignment(2, 33);
/* 5901 */       table_contents.setColAlignment(4, 33);
/* 5902 */       table_contents.setColAlignment(3, 33);
/* 5903 */       table_contents.setColAlignment(5, 33);
/* 5904 */       table_contents.setColAlignment(6, 33);
/* 5905 */       table_contents.setColAlignment(7, 33);
/* 5906 */       table_contents.setColAlignment(8, 33);
/* 5907 */       table_contents.setColAlignment(9, 33);
/* 5908 */       table_contents.setColAlignment(10, 33);
/* 5909 */       table_contents.setColAlignment(11, 33);
/* 5910 */       table_contents.setColAlignment(12, 33);
/* 5911 */       table_contents.setColAlignment(14, 33);
/* 5912 */       table_contents.setColAlignment(13, 33);
/* 5913 */       table_contents.setColAlignment(15, 33);
/* 5914 */       table_contents.setColAlignment(16, 33);
/* 5915 */       table_contents.setColAlignment(17, 33);
/* 5916 */       table_contents.setColAlignment(18, 33);
/* 5917 */       table_contents.setColAlignment(19, 33);
/*      */ 
/*      */       
/* 5920 */       int nextRow = 0;
/*      */ 
/*      */       
/* 5923 */       String isAdded = bom.getType();
/*      */       
/* 5925 */       if (isAdded != null && isAdded.equalsIgnoreCase("A")) {
/* 5926 */         isAdded = "A";
/*      */       } else {
/* 5928 */         isAdded = "C";
/* 5929 */       }  table_contents.setRowBorderColor(nextRow, 0, Color.white);
/* 5930 */       table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 5931 */       table_contents.setRowBorderColor(nextRow, 2, Color.white);
/* 5932 */       table_contents.setRowBorderColor(nextRow, 3, Color.white);
/* 5933 */       table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 5934 */       table_contents.setRowBorderColor(nextRow, 6, Color.white);
/* 5935 */       table_contents.setRowBorderColor(nextRow, 7, Color.white);
/* 5936 */       table_contents.setRowBorderColor(nextRow, 8, Color.white);
/* 5937 */       table_contents.setRowBorderColor(nextRow, 9, Color.white);
/* 5938 */       table_contents.setRowBorderColor(nextRow, 10, Color.white);
/* 5939 */       table_contents.setRowBorderColor(nextRow, 11, Color.white);
/* 5940 */       table_contents.setRowBorderColor(nextRow, 12, Color.white);
/* 5941 */       table_contents.setRowBorderColor(nextRow, 14, Color.white);
/* 5942 */       table_contents.setRowBorderColor(nextRow, 15, Color.white);
/* 5943 */       table_contents.setRowBorderColor(nextRow, 16, Color.white);
/* 5944 */       table_contents.setRowBorderColor(nextRow, 17, Color.white);
/* 5945 */       table_contents.setRowBorderColor(nextRow, 18, Color.white);
/* 5946 */       table_contents.setRowBorderColor(nextRow, 19, Color.white);
/* 5947 */       table_contents.setRowHeight(nextRow, 18);
/*      */       
/* 5949 */       table_contents.setFont(nextRow, 0, boldFont);
/* 5950 */       table_contents.setSpan(nextRow, 0, new Dimension(5, 1));
/* 5951 */       table_contents.setObject(nextRow, 0, "Date:");
/* 5952 */       table_contents.setFont(nextRow, 5, plainFont);
/* 5953 */       table_contents.setObject(nextRow, 5, form.getStringValue("Date"));
/*      */       
/* 5955 */       table_contents.setFont(nextRow, 9, boldFont);
/* 5956 */       table_contents.setSpan(nextRow, 9, new Dimension(4, 1));
/* 5957 */       table_contents.setAlignment(nextRow, 9, 33);
/* 5958 */       table_contents.setObject(nextRow, 9, "Type:");
/* 5959 */       table_contents.setObject(nextRow, 13, isAdded);
/* 5960 */       table_contents.setFont(nextRow, 13, plainFont);
/* 5961 */       table_contents.setAlignment(nextRow, 13, 34);
/* 5962 */       nextRow++;
/*      */ 
/*      */       
/* 5965 */       String changeNumber = "";
/* 5966 */       if (isAdded == null || !isAdded.equalsIgnoreCase("add")) {
/* 5967 */         changeNumber = bom.getChangeNumber();
/*      */       }
/* 5969 */       table_contents.setRowBorderColor(nextRow, 0, Color.white);
/* 5970 */       table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 5971 */       table_contents.setRowBorderColor(nextRow, 2, Color.white);
/* 5972 */       table_contents.setRowBorderColor(nextRow, 3, Color.white);
/* 5973 */       table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 5974 */       table_contents.setRowBorderColor(nextRow, 5, Color.white);
/* 5975 */       table_contents.setRowBorderColor(nextRow, 6, Color.white);
/* 5976 */       table_contents.setRowBorderColor(nextRow, 7, Color.white);
/* 5977 */       table_contents.setRowBorderColor(nextRow, 8, Color.white);
/* 5978 */       table_contents.setRowBorderColor(nextRow, 9, Color.white);
/* 5979 */       table_contents.setRowBorderColor(nextRow, 10, Color.white);
/* 5980 */       table_contents.setRowBorderColor(nextRow, 11, Color.white);
/* 5981 */       table_contents.setRowBorderColor(nextRow, 12, Color.white);
/* 5982 */       table_contents.setRowBorderColor(nextRow, 14, Color.white);
/* 5983 */       table_contents.setRowBorderColor(nextRow, 15, Color.white);
/* 5984 */       table_contents.setRowBorderColor(nextRow, 16, Color.white);
/* 5985 */       table_contents.setRowBorderColor(nextRow, 17, Color.white);
/* 5986 */       table_contents.setRowBorderColor(nextRow, 18, Color.white);
/* 5987 */       table_contents.setRowBorderColor(nextRow, 19, Color.white);
/* 5988 */       table_contents.setRowHeight(nextRow, 18);
/*      */       
/* 5990 */       table_contents.setFont(nextRow, 9, boldFont);
/* 5991 */       table_contents.setSpan(nextRow, 9, new Dimension(4, 1));
/* 5992 */       table_contents.setAlignment(nextRow, 9, 33);
/* 5993 */       table_contents.setObject(nextRow, 9, "Change Number:");
/* 5994 */       table_contents.setObject(nextRow, 13, changeNumber);
/* 5995 */       table_contents.setFont(nextRow, 13, plainFont);
/* 5996 */       table_contents.setAlignment(nextRow, 13, 34);
/* 5997 */       nextRow++;
/*      */ 
/*      */       
/* 6000 */       table_contents.setRowHeight(nextRow, 5);
/* 6001 */       table_contents.setRowBorderColor(nextRow, Color.black);
/* 6002 */       nextRow++;
/*      */ 
/*      */       
/* 6005 */       table_contents.setRowBorderColor(nextRow, 0, Color.white);
/* 6006 */       table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 6007 */       table_contents.setRowBorderColor(nextRow, 2, Color.white);
/* 6008 */       table_contents.setRowBorderColor(nextRow, 3, Color.white);
/* 6009 */       table_contents.setRowBorderColor(nextRow, 10, Color.white);
/* 6010 */       table_contents.setRowBorderColor(nextRow, 11, Color.white);
/* 6011 */       table_contents.setRowBorderColor(nextRow, 12, Color.white);
/* 6012 */       table_contents.setRowHeight(nextRow, 18);
/*      */       
/* 6014 */       table_contents.setSpan(nextRow, 0, new Dimension(4, 1));
/* 6015 */       table_contents.setFont(nextRow, 0, boldFont);
/* 6016 */       table_contents.setAlignment(nextRow, 0, 33);
/* 6017 */       table_contents.setObject(nextRow, 0, "Submitted by:");
/* 6018 */       table_contents.setSpan(nextRow, 4, new Dimension(6, 1));
/* 6019 */       table_contents.setFont(nextRow, 4, plainFont);
/* 6020 */       table_contents.setObject(nextRow, 4, replaceQuote(bom.getSubmitter()));
/*      */       
/* 6022 */       table_contents.setSpan(nextRow, 10, new Dimension(3, 1));
/* 6023 */       table_contents.setFont(nextRow, 10, boldFont);
/* 6024 */       table_contents.setAlignment(nextRow, 10, 36);
/* 6025 */       table_contents.setObject(nextRow, 10, "Phone:");
/* 6026 */       table_contents.setSpan(nextRow, 13, new Dimension(7, 1));
/* 6027 */       table_contents.setFont(nextRow, 13, plainFont);
/* 6028 */       table_contents.setObject(nextRow, 13, bom.getPhone());
/* 6029 */       nextRow++;
/*      */ 
/*      */       
/* 6032 */       table_contents.setRowBorderColor(nextRow, 0, Color.white);
/* 6033 */       table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 6034 */       table_contents.setRowBorderColor(nextRow, 2, Color.white);
/* 6035 */       table_contents.setRowBorderColor(nextRow, 3, Color.white);
/* 6036 */       table_contents.setRowBorderColor(nextRow, 10, Color.white);
/* 6037 */       table_contents.setRowBorderColor(nextRow, 11, Color.white);
/* 6038 */       table_contents.setRowBorderColor(nextRow, 12, Color.white);
/* 6039 */       table_contents.setRowBorderColor(nextRow, 13, Color.white);
/* 6040 */       table_contents.setRowBorderColor(nextRow, 14, Color.white);
/* 6041 */       table_contents.setRowBorderColor(nextRow, 15, Color.white);
/* 6042 */       table_contents.setRowBorderColor(nextRow, 16, Color.white);
/* 6043 */       table_contents.setRowBorderColor(nextRow, 17, Color.white);
/* 6044 */       table_contents.setRowBorderColor(nextRow, 18, Color.white);
/* 6045 */       table_contents.setRowBorderColor(nextRow, 19, Color.white);
/* 6046 */       table_contents.setRowHeight(nextRow, 18);
/*      */       
/* 6048 */       table_contents.setSpan(nextRow, 0, new Dimension(4, 1));
/* 6049 */       table_contents.setFont(nextRow, 0, boldFont);
/* 6050 */       table_contents.setAlignment(nextRow, 0, 33);
/* 6051 */       table_contents.setObject(nextRow, 0, "E-mail:");
/* 6052 */       table_contents.setSpan(nextRow, 4, new Dimension(6, 1));
/* 6053 */       table_contents.setFont(nextRow, 4, plainFont);
/* 6054 */       table_contents.setObject(nextRow, 4, bom.getEmail());
/* 6055 */       nextRow++;
/*      */ 
/*      */       
/* 6058 */       table_contents.setRowHeight(nextRow, 5);
/* 6059 */       table_contents.setRowBorderColor(nextRow, Color.black);
/* 6060 */       nextRow++;
/*      */ 
/*      */       
/* 6063 */       String companyID = bom.getReleasingCompanyId();
/*      */       
/* 6065 */       Vector companyList = Cache.getReleaseCompanies();
/* 6066 */       String companyName = "";
/*      */       
/* 6068 */       for (int i = 0; i < companyList.size(); i++) {
/*      */         
/* 6070 */         if (((LookupObject)companyList.get(i)).getAbbreviation().equals(companyID))
/*      */         {
/* 6072 */           companyName = ((LookupObject)companyList.get(i)).getName();
/*      */         }
/*      */       } 
/*      */       
/* 6076 */       table_contents.setRowBorderColor(nextRow, 0, Color.white);
/* 6077 */       table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 6078 */       table_contents.setRowBorderColor(nextRow, 2, Color.white);
/* 6079 */       table_contents.setRowBorderColor(nextRow, 3, Color.white);
/* 6080 */       table_contents.setRowBorderColor(nextRow, 10, Color.white);
/* 6081 */       table_contents.setRowBorderColor(nextRow, 11, Color.white);
/* 6082 */       table_contents.setRowBorderColor(nextRow, 12, Color.white);
/* 6083 */       table_contents.setRowInsets(nextRow, new Insets(1, 0, 0, 0));
/*      */ 
/*      */ 
/*      */       
/* 6087 */       table_contents.setSpan(nextRow, 0, new Dimension(4, 1));
/* 6088 */       table_contents.setFont(nextRow, 0, boldFont);
/* 6089 */       table_contents.setAlignment(nextRow, 0, 33);
/* 6090 */       table_contents.setObject(nextRow, 0, "Artist:");
/* 6091 */       table_contents.setSpan(nextRow, 0, new Dimension(4, 1));
/* 6092 */       table_contents.setSpan(nextRow, 4, new Dimension(4, 1));
/* 6093 */       table_contents.setFont(nextRow, 4, plainFont);
/* 6094 */       table_contents.setObject(nextRow, 4, selection.getArtist());
/* 6095 */       table_contents.setRowAutoSize(true);
/* 6096 */       table_contents.setColAutoSize(true);
/*      */ 
/*      */ 
/*      */       
/* 6100 */       boolean isBomRetail = bom.isRetail();
/*      */       
/* 6102 */       String isPromo = "";
/* 6103 */       String isRetail = "";
/* 6104 */       if (!isBomRetail) {
/*      */         
/* 6106 */         isRetail = "";
/* 6107 */         isPromo = "X";
/*      */       }
/*      */       else {
/*      */         
/* 6111 */         isPromo = "";
/* 6112 */         isRetail = "X";
/*      */       } 
/*      */       
/* 6115 */       table_contents.setRowInsets(nextRow, new Insets(1, 0, 0, 0));
/*      */       
/* 6117 */       table_contents.setSpan(nextRow, 10, new Dimension(4, 1));
/* 6118 */       table_contents.setFont(nextRow, 10, boldFont);
/* 6119 */       table_contents.setAlignment(nextRow, 10, 36);
/* 6120 */       table_contents.setObject(nextRow, 10, "Commercial:");
/*      */       
/* 6122 */       table_contents.setRowBorderColor(nextRow, 13, Color.white);
/*      */ 
/*      */       
/* 6125 */       table_contents.setAlignment(nextRow, 14, 34);
/* 6126 */       table_contents.setFont(nextRow, 14, plainFont);
/* 6127 */       table_contents.setObject(nextRow, 14, isRetail);
/*      */ 
/*      */       
/* 6130 */       table_contents.setRowBorderColor(nextRow, 15, Color.white);
/* 6131 */       table_contents.setRowBorderColor(nextRow, 16, Color.white);
/*      */       
/* 6133 */       table_contents.setSpan(nextRow, 15, new Dimension(2, 1));
/* 6134 */       table_contents.setFont(nextRow, 15, boldFont);
/* 6135 */       table_contents.setAlignment(nextRow, 15, 36);
/* 6136 */       table_contents.setObject(nextRow, 15, "Promotional:");
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 6141 */       table_contents.setRowBorderColor(nextRow, 19, Color.white);
/* 6142 */       table_contents.setAlignment(nextRow, 17, 34);
/* 6143 */       table_contents.setFont(nextRow, 17, plainFont);
/* 6144 */       table_contents.setObject(nextRow, 17, isPromo);
/* 6145 */       nextRow++;
/*      */ 
/*      */       
/* 6148 */       table_contents.setRowBorderColor(nextRow, 0, Color.white);
/* 6149 */       table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 6150 */       table_contents.setRowBorderColor(nextRow, 2, Color.white);
/* 6151 */       table_contents.setRowBorderColor(nextRow, 3, Color.white);
/* 6152 */       table_contents.setRowBorderColor(nextRow, 10, Color.white);
/* 6153 */       table_contents.setRowBorderColor(nextRow, 11, Color.white);
/* 6154 */       table_contents.setRowBorderColor(nextRow, 12, Color.white);
/* 6155 */       table_contents.setRowInsets(nextRow, new Insets(1, 0, 0, 0));
/*      */       
/* 6157 */       table_contents.setSpan(nextRow, 0, new Dimension(4, 1));
/* 6158 */       table_contents.setFont(nextRow, 0, boldFont);
/* 6159 */       table_contents.setAlignment(nextRow, 0, 33);
/* 6160 */       table_contents.setObject(nextRow, 0, "Title:");
/* 6161 */       table_contents.setSpan(nextRow, 0, new Dimension(4, 1));
/* 6162 */       table_contents.setSpan(nextRow, 4, new Dimension(4, 1));
/* 6163 */       table_contents.setFont(nextRow, 4, plainFont);
/* 6164 */       table_contents.setObject(nextRow, 4, selection.getTitle());
/* 6165 */       table_contents.setRowAutoSize(true);
/* 6166 */       table_contents.setColAutoSize(true);
/* 6167 */       table_contents.setRowBorderColor(nextRow, 13, Color.white);
/*      */       
/* 6169 */       table_contents.setSpan(nextRow, 10, new Dimension(4, 1));
/* 6170 */       table_contents.setFont(nextRow, 10, boldFont);
/* 6171 */       table_contents.setAlignment(nextRow, 10, 36);
/* 6172 */       table_contents.setObject(nextRow, 10, "Street/Ship Date:");
/* 6173 */       table_contents.setSpan(nextRow, 14, new Dimension(6, 1));
/* 6174 */       table_contents.setFont(nextRow, 14, plainFont);
/* 6175 */       table_contents.setObject(nextRow, 14, MilestoneHelper.getFormatedDate(selection.getStreetDate()));
/* 6176 */       nextRow++;
/*      */ 
/*      */ 
/*      */       
/* 6180 */       table_contents.setRowBorderColor(nextRow, 0, Color.white);
/* 6181 */       table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 6182 */       table_contents.setRowBorderColor(nextRow, 2, Color.white);
/* 6183 */       table_contents.setRowBorderColor(nextRow, 3, Color.white);
/* 6184 */       table_contents.setRowBorderColor(nextRow, 10, Color.white);
/* 6185 */       table_contents.setRowBorderColor(nextRow, 11, Color.white);
/* 6186 */       table_contents.setRowBorderColor(nextRow, 12, Color.white);
/* 6187 */       table_contents.setRowInsets(nextRow, new Insets(1, 0, 0, 0));
/* 6188 */       table_contents.setSpan(nextRow, 0, new Dimension(4, 1));
/* 6189 */       table_contents.setFont(nextRow, 0, boldFont);
/* 6190 */       table_contents.setAlignment(nextRow, 0, 33);
/* 6191 */       table_contents.setObject(nextRow, 0, "Releasing Family:");
/*      */       
/* 6193 */       table_contents.setSpan(nextRow, 4, new Dimension(4, 1));
/* 6194 */       table_contents.setFont(nextRow, 4, plainFont);
/* 6195 */       table_contents.setObject(nextRow, 4, selection.getFamily().getName());
/* 6196 */       table_contents.setRowBorderColor(nextRow, 13, Color.white);
/*      */       
/* 6198 */       table_contents.setSpan(nextRow, 10, new Dimension(4, 1));
/* 6199 */       table_contents.setFont(nextRow, 10, boldFont);
/* 6200 */       table_contents.setAlignment(nextRow, 10, 36);
/* 6201 */       table_contents.setObject(nextRow, 10, "Status:");
/* 6202 */       table_contents.setSpan(nextRow, 14, new Dimension(6, 1));
/* 6203 */       table_contents.setFont(nextRow, 14, plainFont);
/* 6204 */       table_contents.setObject(nextRow, 14, selection.getSelectionStatus().getName());
/* 6205 */       nextRow++;
/*      */ 
/*      */       
/* 6208 */       table_contents.setRowBorderColor(nextRow, 0, Color.white);
/* 6209 */       table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 6210 */       table_contents.setRowBorderColor(nextRow, 2, Color.white);
/* 6211 */       table_contents.setRowBorderColor(nextRow, 3, Color.white);
/* 6212 */       table_contents.setRowBorderColor(nextRow, 10, Color.white);
/* 6213 */       table_contents.setRowBorderColor(nextRow, 11, Color.white);
/* 6214 */       table_contents.setRowBorderColor(nextRow, 12, Color.white);
/* 6215 */       table_contents.setRowInsets(nextRow, new Insets(1, 0, 0, 0));
/* 6216 */       table_contents.setSpan(nextRow, 0, new Dimension(4, 1));
/* 6217 */       table_contents.setFont(nextRow, 0, boldFont);
/* 6218 */       table_contents.setAlignment(nextRow, 0, 33);
/* 6219 */       table_contents.setObject(nextRow, 0, "Label:");
/*      */       
/* 6221 */       table_contents.setSpan(nextRow, 4, new Dimension(4, 1));
/* 6222 */       table_contents.setFont(nextRow, 4, plainFont);
/* 6223 */       table_contents.setObject(nextRow, 4, selection.getLabel().getName());
/*      */ 
/*      */ 
/*      */       
/* 6227 */       String upc = "";
/*      */ 
/*      */ 
/*      */       
/* 6231 */       if (bom != null && bom.getUpc() != null)
/* 6232 */         upc = bom.getUpc(); 
/* 6233 */       table_contents.setSpan(nextRow, 12, new Dimension(2, 1));
/* 6234 */       table_contents.setFont(nextRow, 12, boldFont);
/* 6235 */       table_contents.setAlignment(nextRow, 12, 36);
/* 6236 */       table_contents.setObject(nextRow, 12, "UPC:");
/* 6237 */       table_contents.setSpan(nextRow, 14, new Dimension(4, 1));
/* 6238 */       table_contents.setAlignment(nextRow, 14, 33);
/* 6239 */       table_contents.setFont(nextRow, 14, plainFont);
/* 6240 */       table_contents.setObject(nextRow, 14, MilestoneHelper_2.getRMSReportFormat(upc, "UPC", selection.getIsDigital()));
/*      */       
/* 6242 */       table_contents.setRowBorderColor(nextRow, 13, Color.white);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 6249 */       nextRow++;
/*      */ 
/*      */ 
/*      */       
/* 6253 */       table_contents.setRowBorderColor(nextRow, 0, Color.white);
/* 6254 */       table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 6255 */       table_contents.setRowBorderColor(nextRow, 2, Color.white);
/* 6256 */       table_contents.setRowBorderColor(nextRow, 3, Color.white);
/* 6257 */       table_contents.setRowBorderColor(nextRow, 10, Color.white);
/* 6258 */       table_contents.setRowBorderColor(nextRow, 11, Color.white);
/* 6259 */       table_contents.setRowBorderColor(nextRow, 12, Color.white);
/* 6260 */       table_contents.setRowInsets(nextRow, new Insets(1, 0, 0, 0));
/* 6261 */       table_contents.setSpan(nextRow, 0, new Dimension(4, 1));
/* 6262 */       table_contents.setFont(nextRow, 0, boldFont);
/* 6263 */       table_contents.setAlignment(nextRow, 0, 33);
/* 6264 */       table_contents.setObject(nextRow, 0, "Imprint:");
/*      */       
/* 6266 */       table_contents.setSpan(nextRow, 4, new Dimension(4, 1));
/* 6267 */       table_contents.setFont(nextRow, 4, plainFont);
/* 6268 */       table_contents.setObject(nextRow, 4, selection.getImprint());
/* 6269 */       table_contents.setRowBorderColor(nextRow, 13, Color.white);
/*      */       
/* 6271 */       String prefix = SelectionManager.getLookupObjectValue(selection.getPrefixID());
/* 6272 */       if (!prefix.equals("")) {
/* 6273 */         prefix = String.valueOf(prefix) + " ";
/*      */       }
/* 6275 */       table_contents.setSpan(nextRow, 10, new Dimension(4, 1));
/* 6276 */       table_contents.setFont(nextRow, 10, boldFont);
/* 6277 */       table_contents.setAlignment(nextRow, 10, 36);
/* 6278 */       table_contents.setObject(nextRow, 10, "Local Prod #:");
/* 6279 */       table_contents.setSpan(nextRow, 14, new Dimension(6, 1));
/* 6280 */       table_contents.setFont(nextRow, 14, plainFont);
/* 6281 */       table_contents.setObject(nextRow, 14, String.valueOf(prefix) + selection.getSelectionNo());
/* 6282 */       nextRow++;
/*      */ 
/*      */ 
/*      */       
/* 6286 */       table_contents.setRowHeight(nextRow, 5);
/* 6287 */       table_contents.setRowBorderColor(nextRow, Color.black);
/* 6288 */       nextRow++;
/*      */ 
/*      */       
/* 6291 */       nextRow++;
/*      */ 
/*      */       
/* 6294 */       table_contents.setRowHeight(nextRow, 5);
/* 6295 */       table_contents.setRowBorderColor(nextRow, Color.black);
/* 6296 */       nextRow++;
/*      */       
/* 6298 */       int updateUserId = bom.getModifiedBy();
/* 6299 */       User updateUser = UserManager.getInstance().getUser(updateUserId);
/*      */       
/* 6301 */       if (updateUser != null) {
/*      */         
/* 6303 */         String updateUserName = updateUser.getName();
/* 6304 */         if (updateUserName != null) {
/* 6305 */           report.setElement("bom_lastupdatedby", updateUserName);
/*      */         }
/*      */       } 
/* 6308 */       report.setElement("bom_lastupdated", MilestoneHelper.getFormatedDate(bom.getModifiedOn()));
/*      */ 
/*      */       
/* 6311 */       if (configuration != null && configuration.equalsIgnoreCase("CAS")) {
/*      */         
/* 6313 */         addCassetteReportElements(report, bom, context, table_contents, selection);
/*      */       }
/* 6315 */       else if (configuration != null && configuration.equalsIgnoreCase("VIN")) {
/*      */         
/* 6317 */         addVinylReportElements(report, bom, context, table_contents, selection);
/*      */       
/*      */       }
/* 6320 */       else if (configuration != null && configuration.equalsIgnoreCase("DVV") && 
/* 6321 */         !isCDoverride) {
/*      */         
/* 6323 */         addDiscReportElements(report, bom, context, table_contents, selection, true);
/*      */       }
/*      */       else {
/*      */         
/* 6327 */         addDiscReportElements(report, bom, context, table_contents, selection, false);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addDiscReportElements(XStyleSheet report, Bom bom, Context context, DefaultTableLens table_contents, Selection mySelection, boolean isDVD) {
/* 6348 */     if (bom != null) {
/*      */       BomDiskDetail bomDiskDetail;
/*      */       
/* 6351 */       if (isDVD) {
/* 6352 */         bomDiskDetail = bom.getBomDVDDetail();
/*      */       } else {
/* 6354 */         bomDiskDetail = bom.getBomDiskDetail();
/*      */       } 
/* 6356 */       table_contents.setRowInsets(10, new Insets(1, 0, 0, 0));
/* 6357 */       table_contents.setRowInsets(11, new Insets(1, 0, 0, 0));
/* 6358 */       table_contents.setRowInsets(12, new Insets(1, 0, 0, 0));
/* 6359 */       table_contents.setRowInsets(13, new Insets(1, 0, 0, 0));
/* 6360 */       table_contents.setRowInsets(14, new Insets(1, 0, 0, 0));
/* 6361 */       table_contents.setRowInsets(15, new Insets(1, 0, 0, 0));
/* 6362 */       table_contents.setRowInsets(16, new Insets(1, 0, 0, 0));
/* 6363 */       table_contents.setRowInsets(17, new Insets(1, 0, 0, 0));
/* 6364 */       table_contents.setRowInsets(18, new Insets(1, 0, 0, 0));
/* 6365 */       table_contents.setRowInsets(19, new Insets(1, 0, 0, 0));
/* 6366 */       table_contents.setRowInsets(20, new Insets(1, 0, 0, 0));
/* 6367 */       table_contents.setRowInsets(21, new Insets(1, 0, 0, 0));
/* 6368 */       table_contents.setRowInsets(22, new Insets(1, 0, 0, 0));
/* 6369 */       table_contents.setRowInsets(23, new Insets(1, 0, 0, 0));
/* 6370 */       table_contents.setRowInsets(24, new Insets(1, 0, 0, 0));
/* 6371 */       table_contents.setRowInsets(25, new Insets(1, 0, 0, 0));
/* 6372 */       table_contents.setRowInsets(26, new Insets(1, 0, 0, 0));
/* 6373 */       table_contents.setRowInsets(27, new Insets(1, 0, 0, 0));
/* 6374 */       table_contents.setRowInsets(28, new Insets(1, 0, 0, 0));
/* 6375 */       table_contents.setRowInsets(29, new Insets(1, 0, 0, 0));
/* 6376 */       table_contents.setRowInsets(30, new Insets(1, 0, 0, 0));
/*      */       
/* 6378 */       int nextRow = 12;
/* 6379 */       Font boldFont = new Font("Arial", 1, 10);
/* 6380 */       Font plainFont = new Font("Arial", 0, 9);
/*      */ 
/*      */       
/* 6383 */       String stringUnitsPerPkg = "";
/*      */       
/* 6385 */       if (mySelection.getNumberOfUnits() > 0 && 
/* 6386 */         !Integer.toString(mySelection.getNumberOfUnits()).equals(""))
/*      */       {
/* 6388 */         stringUnitsPerPkg = Integer.toString(mySelection.getNumberOfUnits());
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 6393 */       String noSpine = "";
/* 6394 */       if (bom.hasSpineSticker()) {
/* 6395 */         noSpine = "X";
/*      */       } else {
/* 6397 */         noSpine = "";
/*      */       } 
/*      */       
/* 6400 */       String noShrinkwrap = context.getRequestValue("UseNoShrinkWrap");
/* 6401 */       if (bom.useShrinkWrap()) {
/* 6402 */         noShrinkwrap = "X";
/*      */       } else {
/* 6404 */         noShrinkwrap = "";
/*      */       } 
/*      */       
/* 6407 */       table_contents.setRowInsets(nextRow, new Insets(1, 0, 0, 0));
/* 6408 */       table_contents.setRowBorderColor(nextRow, 0, Color.white);
/* 6409 */       table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 6410 */       table_contents.setRowBorderColor(nextRow, 2, Color.white);
/* 6411 */       table_contents.setRowBorderColor(nextRow, 3, Color.white);
/* 6412 */       table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 6413 */       table_contents.setRowBorderColor(nextRow, 5, Color.white);
/* 6414 */       table_contents.setRowBorderColor(nextRow, 6, Color.white);
/* 6415 */       table_contents.setRowBorderColor(nextRow, 7, Color.white);
/* 6416 */       table_contents.setRowBorderColor(nextRow, 8, Color.white);
/* 6417 */       table_contents.setRowBorderColor(nextRow, 9, Color.white);
/* 6418 */       table_contents.setRowBorderColor(nextRow, 12, Color.white);
/* 6419 */       table_contents.setRowBorderColor(nextRow, 13, Color.white);
/* 6420 */       table_contents.setRowBorderColor(nextRow, 14, Color.white);
/* 6421 */       table_contents.setRowBorderColor(nextRow, 16, Color.white);
/*      */ 
/*      */       
/* 6424 */       table_contents.setRowBorderColor(nextRow, 19, Color.white);
/*      */ 
/*      */       
/* 6427 */       table_contents.setSpan(nextRow, 0, new Dimension(7, 1));
/* 6428 */       table_contents.setFont(nextRow, 0, boldFont);
/* 6429 */       table_contents.setAlignment(nextRow, 0, 33);
/*      */       
/* 6431 */       if (isDVD) {
/* 6432 */         table_contents.setObject(nextRow, 0, "DVD Video");
/*      */       } else {
/* 6434 */         table_contents.setObject(nextRow, 0, "Disc");
/*      */       } 
/*      */ 
/*      */       
/* 6438 */       table_contents.setSpan(nextRow, 7, new Dimension(3, 1));
/* 6439 */       table_contents.setFont(nextRow, 7, boldFont);
/* 6440 */       table_contents.setAlignment(nextRow, 7, 36);
/* 6441 */       table_contents.setObject(nextRow, 7, "# of Units:");
/* 6442 */       table_contents.setSpan(nextRow, 10, new Dimension(2, 1));
/* 6443 */       table_contents.setAlignment(nextRow, 10, 34);
/* 6444 */       table_contents.setFont(nextRow, 10, plainFont);
/* 6445 */       table_contents.setObject(nextRow, 10, stringUnitsPerPkg);
/*      */ 
/*      */ 
/*      */       
/* 6449 */       table_contents.setSpan(nextRow, 13, new Dimension(2, 1));
/* 6450 */       table_contents.setFont(nextRow, 13, boldFont);
/* 6451 */       table_contents.setAlignment(nextRow, 13, 36);
/* 6452 */       table_contents.setObject(nextRow, 13, "Spine Sticker:");
/* 6453 */       table_contents.setFont(nextRow, 15, new Font("Arial", 0, 10));
/* 6454 */       table_contents.setObject(nextRow, 15, noSpine);
/* 6455 */       table_contents.setAlignment(nextRow, 15, 34);
/*      */ 
/*      */ 
/*      */       
/* 6459 */       table_contents.setFont(nextRow, 16, boldFont);
/* 6460 */       table_contents.setAlignment(nextRow, 16, 36);
/* 6461 */       table_contents.setObject(nextRow, 16, "Shrink Wrap:");
/*      */       
/* 6463 */       table_contents.setSpan(nextRow, 17, new Dimension(2, 1));
/*      */       
/* 6465 */       table_contents.setFont(nextRow, 17, new Font("Arial", 0, 10));
/* 6466 */       table_contents.setObject(nextRow, 17, noShrinkwrap);
/* 6467 */       table_contents.setAlignment(nextRow, 17, 34);
/* 6468 */       nextRow++;
/*      */ 
/*      */       
/* 6471 */       table_contents.setRowHeight(nextRow, 5);
/* 6472 */       table_contents.setRowBorderColor(nextRow, Color.black);
/* 6473 */       nextRow++;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 6478 */       table_contents.setRowInsets(nextRow, new Insets(1, 0, 0, 0));
/*      */ 
/*      */       
/* 6481 */       table_contents.setRowBorderColor(nextRow, 0, Color.white);
/* 6482 */       table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 6483 */       table_contents.setRowBorderColor(nextRow, 2, Color.white);
/* 6484 */       table_contents.setRowBorderColor(nextRow, 3, Color.white);
/* 6485 */       table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 6486 */       table_contents.setRowBorderColor(nextRow, 5, Color.white);
/* 6487 */       table_contents.setRowBorderColor(nextRow, 6, Color.white);
/* 6488 */       table_contents.setRowBorderColor(nextRow, 7, Color.white);
/* 6489 */       table_contents.setRowBorderColor(nextRow, 8, Color.white);
/* 6490 */       table_contents.setRowBorderColor(nextRow, 9, Color.white);
/* 6491 */       table_contents.setRowBorderColor(nextRow, 10, Color.white);
/* 6492 */       table_contents.setRowBorderColor(nextRow, 11, Color.white);
/* 6493 */       table_contents.setRowBorderColor(nextRow, 12, Color.white);
/* 6494 */       table_contents.setRowBorderColor(nextRow, 13, Color.white);
/* 6495 */       table_contents.setRowBorderColor(nextRow, 14, Color.white);
/* 6496 */       table_contents.setRowBorderColor(nextRow, 15, Color.white);
/* 6497 */       table_contents.setRowBorderColor(nextRow, 16, Color.white);
/* 6498 */       table_contents.setRowBorderColor(nextRow, 17, Color.white);
/* 6499 */       table_contents.setRowBorderColor(nextRow, 18, Color.white);
/* 6500 */       table_contents.setRowBorderColor(nextRow, 19, Color.white);
/*      */       
/* 6502 */       table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
/* 6503 */       table_contents.setFont(nextRow, 2, boldFont);
/* 6504 */       table_contents.setAlignment(nextRow, 2, 33);
/* 6505 */       table_contents.setObject(nextRow, 2, "Part");
/*      */       
/* 6507 */       table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 6508 */       table_contents.setAlignment(nextRow, 5, 33);
/* 6509 */       table_contents.setFont(nextRow, 5, boldFont);
/* 6510 */       table_contents.setObject(nextRow, 5, "Supplier");
/*      */       
/* 6512 */       table_contents.setFont(nextRow, 9, boldFont);
/* 6513 */       table_contents.setAlignment(nextRow, 9, 34);
/* 6514 */       table_contents.setObject(nextRow, 9, "Ink");
/*      */       
/* 6516 */       table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
/* 6517 */       table_contents.setFont(nextRow, 12, boldFont);
/* 6518 */       table_contents.setObject(nextRow, 12, "Additional Information");
/* 6519 */       table_contents.setAlignment(nextRow, 12, 33);
/* 6520 */       nextRow++;
/*      */ 
/*      */ 
/*      */       
/* 6524 */       double addInfoWidth = 4.222222328186035D;
/* 6525 */       double addInfoLineHeight = 0.2083333283662796D;
/* 6526 */       int charsPerLine = 85;
/*      */ 
/*      */       
/* 6529 */       String discCheck = "";
/* 6530 */       if (bomDiskDetail.discStatusIndicator) {
/* 6531 */         discCheck = "x";
/*      */       } else {
/* 6533 */         discCheck = "";
/*      */       } 
/* 6535 */       String ink17 = bomDiskDetail.discInk1;
/* 6536 */       String ink27 = bomDiskDetail.discInk2;
/*      */       
/* 6538 */       if (ink17 != null && ink17.trim().length() < 2) {
/*      */         
/* 6540 */         ink17 = ink17.trim();
/* 6541 */         if (ink17.length() == 1) {
/* 6542 */           ink17 = " " + ink17;
/* 6543 */         } else if (ink17.length() == 0) {
/* 6544 */           ink17 = "  ";
/*      */         } 
/*      */       } 
/* 6547 */       if (ink27 != null && ink27.trim().length() < 2) {
/*      */         
/* 6549 */         ink27 = ink27.trim();
/* 6550 */         if (ink27.length() == 1) {
/* 6551 */           ink27 = " " + ink27;
/* 6552 */         } else if (ink27.length() == 0) {
/* 6553 */           ink27 = "  ";
/*      */         } 
/* 6555 */       }  String bomDiskInk1 = String.valueOf(ink17) + "/" + ink27;
/*      */ 
/*      */       
/* 6558 */       if (isDVD) {
/* 6559 */         addInfoText = String.valueOf(((BomDVDDetail)bomDiskDetail).discSelectionInfo) + " " + bomDiskDetail.discInfo;
/*      */       } else {
/* 6561 */         addInfoText = bomDiskDetail.discInfo;
/*      */       } 
/* 6563 */       String partdisc = bomDiskDetail.discSupplier;
/*      */       
/* 6565 */       TextBoxElement addInfoTextBox = (TextBoxElement)report.getElement("bom_disc_additionalinfo");
/*      */ 
/*      */       
/* 6568 */       table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 6569 */       table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 6570 */       table_contents.setRowBorderColor(nextRow, 8, Color.white);
/* 6571 */       table_contents.setRowBorderColor(nextRow, 10, Color.white);
/* 6572 */       table_contents.setRowBorderColor(nextRow, 11, Color.white);
/*      */       
/* 6574 */       table_contents.setFont(nextRow, 0, boldFont);
/* 6575 */       table_contents.setAlignment(nextRow, 0, 34);
/* 6576 */       table_contents.setObject(nextRow, 0, replaceQuote(discCheck));
/*      */       
/* 6578 */       table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
/* 6579 */       table_contents.setFont(nextRow, 2, plainFont);
/* 6580 */       table_contents.setAlignment(nextRow, 2, 33);
/* 6581 */       table_contents.setObject(nextRow, 2, "Disc");
/*      */       
/* 6583 */       table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 6584 */       table_contents.setAlignment(nextRow, 5, 33);
/* 6585 */       table_contents.setFont(nextRow, 5, plainFont);
/*      */       
/* 6587 */       table_contents.setObject(nextRow, 5, partdisc);
/*      */       
/* 6589 */       table_contents.setFont(nextRow, 9, plainFont);
/* 6590 */       table_contents.setAlignment(nextRow, 9, 34);
/* 6591 */       table_contents.setObject(nextRow, 9, replaceQuote(bomDiskInk1));
/*      */       
/* 6593 */       table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
/* 6594 */       table_contents.setFont(nextRow, 12, plainFont);
/* 6595 */       table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
/* 6596 */       table_contents.setAlignment(nextRow, 12, 33);
/* 6597 */       nextRow++;
/*      */ 
/*      */       
/* 6600 */       String jewelboxCheck = "";
/*      */       
/* 6602 */       if (bomDiskDetail.jewelStatusIndicator) {
/* 6603 */         jewelboxCheck = "x";
/*      */       } else {
/* 6605 */         jewelboxCheck = "";
/*      */       } 
/* 6607 */       String selectedJewelbox = bomDiskDetail.jewelColor;
/*      */       
/* 6609 */       selectedJewelbox = (selectedJewelbox == null || selectedJewelbox.length() <= 1) ? 
/* 6610 */         "" : (String.valueOf(selectedJewelbox) + " ");
/* 6611 */       String addInfoText = String.valueOf(selectedJewelbox) + bomDiskDetail.jewelInfo;
/*      */ 
/*      */ 
/*      */       
/* 6615 */       table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 6616 */       table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 6617 */       table_contents.setRowBorderColor(nextRow, 8, Color.white);
/* 6618 */       table_contents.setRowBorderColor(nextRow, 10, Color.white);
/* 6619 */       table_contents.setRowBorderColor(nextRow, 11, Color.white);
/*      */       
/* 6621 */       table_contents.setFont(nextRow, 0, boldFont);
/* 6622 */       table_contents.setAlignment(nextRow, 0, 34);
/* 6623 */       table_contents.setObject(nextRow, 0, replaceQuote(jewelboxCheck));
/*      */       
/* 6625 */       table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
/* 6626 */       table_contents.setFont(nextRow, 2, plainFont);
/* 6627 */       table_contents.setAlignment(nextRow, 2, 33);
/* 6628 */       table_contents.setObject(nextRow, 2, "Jewel Box");
/*      */       
/* 6630 */       table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 6631 */       table_contents.setAlignment(nextRow, 5, 33);
/* 6632 */       table_contents.setFont(nextRow, 5, plainFont);
/* 6633 */       table_contents.setObject(nextRow, 5, "n.a.");
/*      */       
/* 6635 */       table_contents.setFont(nextRow, 9, plainFont);
/* 6636 */       table_contents.setAlignment(nextRow, 9, 34);
/* 6637 */       table_contents.setObject(nextRow, 9, "/");
/*      */       
/* 6639 */       table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
/* 6640 */       table_contents.setFont(nextRow, 12, plainFont);
/* 6641 */       table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
/* 6642 */       table_contents.setAlignment(nextRow, 12, 33);
/* 6643 */       nextRow++;
/*      */ 
/*      */       
/* 6646 */       if (isDVD) {
/*      */ 
/*      */         
/* 6649 */         String wrapCheck = "";
/*      */         
/* 6651 */         if (((BomDVDDetail)bomDiskDetail).wrapStatusIndicator) {
/* 6652 */           wrapCheck = "x";
/*      */         } else {
/* 6654 */           wrapCheck = "";
/*      */         } 
/* 6656 */         String wrapSupplierID = Integer.toString(((BomDVDDetail)bomDiskDetail).wrapSupplierId);
/*      */         
/* 6658 */         LookupObject supplier = MilestoneHelper.getSupplierById(wrapSupplierID);
/* 6659 */         String wrapSupplierName = (supplier == null) ? "" : supplier.getName();
/*      */         
/* 6661 */         String bomWrapInk1 = String.valueOf(((BomDVDDetail)bomDiskDetail).wrapInk1) + " / " + 
/* 6662 */           ((BomDVDDetail)bomDiskDetail).wrapInk2;
/*      */         
/* 6664 */         addInfoText = ((BomDVDDetail)bomDiskDetail).wrapInfo;
/*      */ 
/*      */         
/* 6667 */         table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 6668 */         table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 6669 */         table_contents.setRowBorderColor(nextRow, 8, Color.white);
/* 6670 */         table_contents.setRowBorderColor(nextRow, 10, Color.white);
/* 6671 */         table_contents.setRowBorderColor(nextRow, 11, Color.white);
/*      */         
/* 6673 */         table_contents.setFont(nextRow, 0, boldFont);
/* 6674 */         table_contents.setAlignment(nextRow, 0, 34);
/* 6675 */         table_contents.setObject(nextRow, 0, replaceQuote(wrapCheck));
/*      */         
/* 6677 */         table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
/* 6678 */         table_contents.setFont(nextRow, 2, plainFont);
/* 6679 */         table_contents.setAlignment(nextRow, 2, 33);
/* 6680 */         table_contents.setObject(nextRow, 2, "Wrap");
/*      */         
/* 6682 */         table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 6683 */         table_contents.setAlignment(nextRow, 5, 33);
/* 6684 */         table_contents.setFont(nextRow, 5, plainFont);
/* 6685 */         table_contents.setObject(nextRow, 5, replaceQuote(wrapSupplierName));
/*      */         
/* 6687 */         table_contents.setFont(nextRow, 9, plainFont);
/* 6688 */         table_contents.setAlignment(nextRow, 9, 34);
/* 6689 */         table_contents.setObject(nextRow, 9, replaceQuote(bomWrapInk1));
/*      */         
/* 6691 */         table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
/* 6692 */         table_contents.setFont(nextRow, 12, plainFont);
/* 6693 */         table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
/* 6694 */         table_contents.setAlignment(nextRow, 12, 33);
/* 6695 */         nextRow++;
/*      */ 
/*      */         
/* 6698 */         String dvdCaseCheck = "";
/*      */         
/* 6700 */         if (((BomDVDDetail)bomDiskDetail).dvdStatusIndicator) {
/* 6701 */           dvdCaseCheck = "x";
/*      */         } else {
/* 6703 */           dvdCaseCheck = "";
/*      */         } 
/* 6705 */         String dvdCaseLnk = String.valueOf(((BomDVDDetail)bomDiskDetail).dvdInk1) + " / " + 
/* 6706 */           ((BomDVDDetail)bomDiskDetail).dvdInk2;
/*      */         
/* 6708 */         addInfoText = String.valueOf(((BomDVDDetail)bomDiskDetail).dvdSelectionInfo) + " " + ((BomDVDDetail)bomDiskDetail).dvdInfo;
/*      */ 
/*      */ 
/*      */         
/* 6712 */         table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 6713 */         table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 6714 */         table_contents.setRowBorderColor(nextRow, 8, Color.white);
/* 6715 */         table_contents.setRowBorderColor(nextRow, 10, Color.white);
/* 6716 */         table_contents.setRowBorderColor(nextRow, 11, Color.white);
/*      */         
/* 6718 */         table_contents.setFont(nextRow, 0, boldFont);
/* 6719 */         table_contents.setAlignment(nextRow, 0, 34);
/* 6720 */         table_contents.setObject(nextRow, 0, replaceQuote(dvdCaseCheck));
/*      */         
/* 6722 */         table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
/* 6723 */         table_contents.setFont(nextRow, 2, plainFont);
/* 6724 */         table_contents.setAlignment(nextRow, 2, 33);
/* 6725 */         table_contents.setObject(nextRow, 2, "DVD Case");
/*      */         
/* 6727 */         table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 6728 */         table_contents.setAlignment(nextRow, 5, 33);
/* 6729 */         table_contents.setFont(nextRow, 5, plainFont);
/* 6730 */         table_contents.setObject(nextRow, 5, "n.a.");
/*      */         
/* 6732 */         table_contents.setFont(nextRow, 9, plainFont);
/* 6733 */         table_contents.setAlignment(nextRow, 9, 34);
/* 6734 */         table_contents.setObject(nextRow, 9, dvdCaseLnk);
/*      */         
/* 6736 */         table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
/* 6737 */         table_contents.setFont(nextRow, 12, plainFont);
/* 6738 */         table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
/* 6739 */         table_contents.setAlignment(nextRow, 12, 33);
/* 6740 */         nextRow++;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 6746 */         String bluRayCaseCheck = "";
/*      */         
/* 6748 */         if (((BomDVDDetail)bomDiskDetail).bluRayStatusIndicator) {
/* 6749 */           bluRayCaseCheck = "x";
/*      */         } else {
/* 6751 */           bluRayCaseCheck = "";
/*      */         } 
/* 6753 */         String bluRayCaseLnk = String.valueOf(((BomDVDDetail)bomDiskDetail).bluRayInk1) + " / " + 
/* 6754 */           ((BomDVDDetail)bomDiskDetail).bluRayInk2;
/*      */         
/* 6756 */         addInfoText = String.valueOf(((BomDVDDetail)bomDiskDetail).bluRaySelectionInfo) + " " + ((BomDVDDetail)bomDiskDetail).bluRayInfo;
/*      */ 
/*      */ 
/*      */         
/* 6760 */         table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 6761 */         table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 6762 */         table_contents.setRowBorderColor(nextRow, 8, Color.white);
/* 6763 */         table_contents.setRowBorderColor(nextRow, 10, Color.white);
/* 6764 */         table_contents.setRowBorderColor(nextRow, 11, Color.white);
/*      */         
/* 6766 */         table_contents.setFont(nextRow, 0, boldFont);
/* 6767 */         table_contents.setAlignment(nextRow, 0, 34);
/* 6768 */         table_contents.setObject(nextRow, 0, replaceQuote(bluRayCaseCheck));
/*      */         
/* 6770 */         table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
/* 6771 */         table_contents.setFont(nextRow, 2, plainFont);
/* 6772 */         table_contents.setAlignment(nextRow, 2, 33);
/* 6773 */         table_contents.setObject(nextRow, 2, "Blu-Ray Case");
/*      */         
/* 6775 */         table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 6776 */         table_contents.setAlignment(nextRow, 5, 33);
/* 6777 */         table_contents.setFont(nextRow, 5, plainFont);
/* 6778 */         table_contents.setObject(nextRow, 5, "n.a.");
/*      */         
/* 6780 */         table_contents.setFont(nextRow, 9, plainFont);
/* 6781 */         table_contents.setAlignment(nextRow, 9, 34);
/* 6782 */         table_contents.setObject(nextRow, 9, bluRayCaseLnk);
/*      */         
/* 6784 */         table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
/* 6785 */         table_contents.setFont(nextRow, 12, plainFont);
/* 6786 */         table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
/* 6787 */         table_contents.setAlignment(nextRow, 12, 33);
/* 6788 */         nextRow++;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 6799 */         String trayCheck = "";
/*      */         
/* 6801 */         if (bomDiskDetail.trayStatusIndicator) {
/* 6802 */           trayCheck = "x";
/*      */         } else {
/* 6804 */           trayCheck = "";
/*      */         } 
/* 6806 */         String selectedTray = bomDiskDetail.trayColor;
/*      */         
/* 6808 */         selectedTray = (selectedTray == null || selectedTray.length() <= 1) ? 
/* 6809 */           "" : (String.valueOf(selectedTray) + "  ");
/* 6810 */         addInfoText = String.valueOf(selectedTray) + bomDiskDetail.trayInfo;
/*      */ 
/*      */         
/* 6813 */         table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 6814 */         table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 6815 */         table_contents.setRowBorderColor(nextRow, 8, Color.white);
/* 6816 */         table_contents.setRowBorderColor(nextRow, 10, Color.white);
/* 6817 */         table_contents.setRowBorderColor(nextRow, 11, Color.white);
/*      */         
/* 6819 */         table_contents.setFont(nextRow, 0, boldFont);
/* 6820 */         table_contents.setAlignment(nextRow, 0, 
/* 6821 */             34);
/*      */         
/* 6823 */         table_contents.setObject(nextRow, 0, replaceQuote(trayCheck));
/*      */         
/* 6825 */         table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
/* 6826 */         table_contents.setFont(nextRow, 2, plainFont);
/* 6827 */         table_contents.setAlignment(nextRow, 2, 
/* 6828 */             33);
/*      */         
/* 6830 */         table_contents.setObject(nextRow, 2, "Tray");
/*      */         
/* 6832 */         table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 6833 */         table_contents.setAlignment(nextRow, 5, 
/* 6834 */             33);
/*      */         
/* 6836 */         table_contents.setFont(nextRow, 5, plainFont);
/* 6837 */         table_contents.setObject(nextRow, 5, "n.a.");
/*      */         
/* 6839 */         table_contents.setFont(nextRow, 9, plainFont);
/* 6840 */         table_contents.setAlignment(nextRow, 9, 
/* 6841 */             34);
/*      */         
/* 6843 */         table_contents.setObject(nextRow, 9, "/");
/*      */         
/* 6845 */         table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
/* 6846 */         table_contents.setFont(nextRow, 12, plainFont);
/* 6847 */         table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
/* 6848 */         table_contents.setAlignment(nextRow, 12, 
/* 6849 */             33);
/*      */         
/* 6851 */         nextRow++;
/*      */       } 
/*      */ 
/*      */       
/* 6855 */       String inlayCheck = "";
/*      */       
/* 6857 */       if (bomDiskDetail.inlayStatusIndicator) {
/* 6858 */         inlayCheck = "x";
/*      */       } else {
/* 6860 */         inlayCheck = "";
/*      */       } 
/* 6862 */       String supplierID = Integer.toString(bomDiskDetail.inlaySupplierId);
/*      */       
/* 6864 */       LookupObject supplier = MilestoneHelper.getSupplierById(supplierID);
/* 6865 */       String supplierName = (supplier == null) ? "" : supplier.getName();
/*      */       
/* 6867 */       String bomInlayInk1 = String.valueOf(bomDiskDetail.inlayInk1) + " / " + 
/* 6868 */         bomDiskDetail.inlayInk2;
/*      */       
/* 6870 */       addInfoText = bomDiskDetail.inlayInfo;
/*      */ 
/*      */       
/* 6873 */       table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 6874 */       table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 6875 */       table_contents.setRowBorderColor(nextRow, 8, Color.white);
/* 6876 */       table_contents.setRowBorderColor(nextRow, 10, Color.white);
/* 6877 */       table_contents.setRowBorderColor(nextRow, 11, Color.white);
/*      */       
/* 6879 */       table_contents.setFont(nextRow, 0, boldFont);
/* 6880 */       table_contents.setAlignment(nextRow, 0, 34);
/* 6881 */       table_contents.setObject(nextRow, 0, replaceQuote(inlayCheck));
/*      */       
/* 6883 */       table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
/* 6884 */       table_contents.setFont(nextRow, 2, plainFont);
/* 6885 */       table_contents.setAlignment(nextRow, 2, 33);
/* 6886 */       table_contents.setObject(nextRow, 2, "Inlay");
/*      */       
/* 6888 */       table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 6889 */       table_contents.setAlignment(nextRow, 5, 33);
/* 6890 */       table_contents.setFont(nextRow, 5, plainFont);
/* 6891 */       table_contents.setObject(nextRow, 5, replaceQuote(supplierName));
/*      */       
/* 6893 */       table_contents.setFont(nextRow, 9, plainFont);
/* 6894 */       table_contents.setAlignment(nextRow, 9, 34);
/* 6895 */       table_contents.setObject(nextRow, 9, replaceQuote(bomInlayInk1));
/*      */       
/* 6897 */       table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
/* 6898 */       table_contents.setFont(nextRow, 12, plainFont);
/* 6899 */       table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
/* 6900 */       table_contents.setAlignment(nextRow, 12, 33);
/* 6901 */       nextRow++;
/*      */ 
/*      */       
/* 6904 */       String frontinsertCheck = "";
/*      */       
/* 6906 */       if (bomDiskDetail.frontStatusIndicator) {
/* 6907 */         frontinsertCheck = "x";
/*      */       } else {
/* 6909 */         frontinsertCheck = "";
/*      */       } 
/* 6911 */       supplierID = Integer.toString(bomDiskDetail.frontSupplierId);
/*      */       
/* 6913 */       supplier = MilestoneHelper.getSupplierById(supplierID);
/* 6914 */       supplierName = (supplier == null) ? "" : supplier.getName();
/*      */       
/* 6916 */       String bomFrontInsertInk1 = String.valueOf(bomDiskDetail.frontInk1) + "/" + 
/* 6917 */         bomDiskDetail.frontInk2;
/*      */       
/* 6919 */       addInfoText = bomDiskDetail.frontInfo;
/*      */ 
/*      */       
/* 6922 */       table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 6923 */       table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 6924 */       table_contents.setRowBorderColor(nextRow, 8, Color.white);
/* 6925 */       table_contents.setRowBorderColor(nextRow, 10, Color.white);
/* 6926 */       table_contents.setRowBorderColor(nextRow, 11, Color.white);
/*      */       
/* 6928 */       table_contents.setFont(nextRow, 0, boldFont);
/* 6929 */       table_contents.setAlignment(nextRow, 0, 34);
/* 6930 */       table_contents.setObject(nextRow, 0, replaceQuote(frontinsertCheck));
/*      */       
/* 6932 */       table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
/* 6933 */       table_contents.setFont(nextRow, 2, plainFont);
/* 6934 */       table_contents.setAlignment(nextRow, 2, 33);
/* 6935 */       table_contents.setObject(nextRow, 2, "Front Insert");
/*      */       
/* 6937 */       table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 6938 */       table_contents.setAlignment(nextRow, 5, 33);
/* 6939 */       table_contents.setFont(nextRow, 5, plainFont);
/* 6940 */       table_contents.setObject(nextRow, 5, replaceQuote(supplierName));
/*      */       
/* 6942 */       table_contents.setFont(nextRow, 9, plainFont);
/* 6943 */       table_contents.setAlignment(nextRow, 9, 34);
/* 6944 */       table_contents.setObject(nextRow, 9, replaceQuote(bomFrontInsertInk1));
/*      */       
/* 6946 */       table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
/* 6947 */       table_contents.setFont(nextRow, 12, plainFont);
/* 6948 */       table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
/* 6949 */       table_contents.setAlignment(nextRow, 12, 33);
/* 6950 */       nextRow++;
/*      */ 
/*      */       
/* 6953 */       String folderCheck = "";
/*      */       
/* 6955 */       if (bomDiskDetail.folderStatusIndicator) {
/* 6956 */         folderCheck = "x";
/*      */       } else {
/* 6958 */         folderCheck = "";
/*      */       } 
/* 6960 */       supplierID = Integer.toString(bomDiskDetail.folderSupplierId);
/*      */       
/* 6962 */       supplier = MilestoneHelper.getSupplierById(supplierID);
/* 6963 */       supplierName = (supplier == null) ? "" : supplier.getName();
/*      */       
/* 6965 */       String bomFolderInk1 = String.valueOf(bomDiskDetail.folderInk1) + "/" + 
/* 6966 */         bomDiskDetail.folderInk2;
/*      */       
/* 6968 */       String selectedFolder = bomDiskDetail.folderPages;
/*      */       
/* 6970 */       selectedFolder = (selectedFolder == null || !MilestoneHelper.isAllNumeric(selectedFolder)) ? 
/* 6971 */         "" : ("Pages: " + selectedFolder + "  ");
/*      */       
/* 6973 */       addInfoText = String.valueOf(selectedFolder) + bomDiskDetail.folderInfo;
/*      */ 
/*      */       
/* 6976 */       table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 6977 */       table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 6978 */       table_contents.setRowBorderColor(nextRow, 8, Color.white);
/* 6979 */       table_contents.setRowBorderColor(nextRow, 10, Color.white);
/* 6980 */       table_contents.setRowBorderColor(nextRow, 11, Color.white);
/*      */       
/* 6982 */       table_contents.setFont(nextRow, 0, boldFont);
/* 6983 */       table_contents.setAlignment(nextRow, 0, 34);
/* 6984 */       table_contents.setObject(nextRow, 0, replaceQuote(folderCheck));
/*      */       
/* 6986 */       table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
/* 6987 */       table_contents.setFont(nextRow, 2, plainFont);
/* 6988 */       table_contents.setAlignment(nextRow, 2, 33);
/* 6989 */       table_contents.setObject(nextRow, 2, "Folder");
/*      */       
/* 6991 */       table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 6992 */       table_contents.setAlignment(nextRow, 5, 33);
/* 6993 */       table_contents.setFont(nextRow, 5, plainFont);
/* 6994 */       table_contents.setObject(nextRow, 5, replaceQuote(supplierName));
/*      */       
/* 6996 */       table_contents.setFont(nextRow, 9, plainFont);
/* 6997 */       table_contents.setAlignment(nextRow, 9, 34);
/* 6998 */       table_contents.setObject(nextRow, 9, replaceQuote(bomFolderInk1));
/*      */       
/* 7000 */       table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
/* 7001 */       table_contents.setFont(nextRow, 12, plainFont);
/* 7002 */       table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
/* 7003 */       table_contents.setAlignment(nextRow, 12, 33);
/* 7004 */       nextRow++;
/*      */ 
/*      */       
/* 7007 */       String check = "";
/*      */       
/* 7009 */       if (bomDiskDetail.bookletStatusIndicator) {
/* 7010 */         check = "x";
/*      */       } else {
/* 7012 */         check = "";
/*      */       } 
/* 7014 */       supplierID = Integer.toString(bomDiskDetail.bookletSupplierId);
/*      */       
/* 7016 */       supplier = MilestoneHelper.getSupplierById(supplierID);
/* 7017 */       supplierName = (supplier == null) ? "" : supplier.getName();
/*      */       
/* 7019 */       String bomBookletInk1 = String.valueOf(bomDiskDetail.bookletInk1) + "/" + 
/* 7020 */         bomDiskDetail.bookletInk2;
/*      */       
/* 7022 */       String selectedBooklet = bomDiskDetail.getBookletPages();
/*      */       
/* 7024 */       selectedBooklet = (selectedBooklet == null || !MilestoneHelper.isAllNumeric(selectedBooklet)) ? 
/* 7025 */         "" : ("Pages: " + selectedBooklet + "  ");
/*      */       
/* 7027 */       addInfoText = String.valueOf(selectedBooklet) + bomDiskDetail.bookletInfo;
/*      */ 
/*      */       
/* 7030 */       table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 7031 */       table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 7032 */       table_contents.setRowBorderColor(nextRow, 8, Color.white);
/* 7033 */       table_contents.setRowBorderColor(nextRow, 10, Color.white);
/* 7034 */       table_contents.setRowBorderColor(nextRow, 11, Color.white);
/*      */       
/* 7036 */       table_contents.setFont(nextRow, 0, boldFont);
/* 7037 */       table_contents.setAlignment(nextRow, 0, 34);
/* 7038 */       table_contents.setObject(nextRow, 0, replaceQuote(check));
/*      */       
/* 7040 */       table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
/* 7041 */       table_contents.setFont(nextRow, 2, plainFont);
/* 7042 */       table_contents.setAlignment(nextRow, 2, 33);
/* 7043 */       table_contents.setObject(nextRow, 2, "Booklet");
/*      */       
/* 7045 */       table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 7046 */       table_contents.setAlignment(nextRow, 5, 33);
/* 7047 */       table_contents.setFont(nextRow, 5, plainFont);
/* 7048 */       table_contents.setObject(nextRow, 5, replaceQuote(supplierName));
/*      */       
/* 7050 */       table_contents.setFont(nextRow, 9, plainFont);
/* 7051 */       table_contents.setAlignment(nextRow, 9, 34);
/* 7052 */       table_contents.setObject(nextRow, 9, replaceQuote(bomBookletInk1));
/*      */       
/* 7054 */       table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
/* 7055 */       table_contents.setFont(nextRow, 12, plainFont);
/* 7056 */       table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
/* 7057 */       table_contents.setAlignment(nextRow, 12, 33);
/* 7058 */       nextRow++;
/*      */ 
/*      */ 
/*      */       
/* 7062 */       check = "";
/*      */       
/* 7064 */       if (bomDiskDetail.brcStatusIndicator) {
/* 7065 */         check = "x";
/*      */       } else {
/* 7067 */         check = "";
/*      */       } 
/* 7069 */       supplierID = Integer.toString(bomDiskDetail.brcSupplierId);
/*      */       
/* 7071 */       supplier = MilestoneHelper.getSupplierById(supplierID);
/* 7072 */       supplierName = (supplier == null) ? "" : supplier.getName();
/*      */       
/* 7074 */       String bomBrcInsertInk1 = String.valueOf(bomDiskDetail.brcInk1) + "/" + 
/* 7075 */         bomDiskDetail.brcInk2;
/*      */       
/* 7077 */       String sizeBrcInsert = bomDiskDetail.brcSize;
/*      */       
/* 7079 */       sizeBrcInsert = (sizeBrcInsert == null || sizeBrcInsert.trim().equals("")) ? 
/* 7080 */         "" : ("Size: " + sizeBrcInsert + "  ");
/*      */       
/* 7082 */       addInfoText = String.valueOf(sizeBrcInsert) + bomDiskDetail.brcInfo;
/*      */ 
/*      */       
/* 7085 */       table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 7086 */       table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 7087 */       table_contents.setRowBorderColor(nextRow, 8, Color.white);
/* 7088 */       table_contents.setRowBorderColor(nextRow, 10, Color.white);
/* 7089 */       table_contents.setRowBorderColor(nextRow, 11, Color.white);
/*      */       
/* 7091 */       table_contents.setFont(nextRow, 0, boldFont);
/* 7092 */       table_contents.setAlignment(nextRow, 0, 34);
/* 7093 */       table_contents.setObject(nextRow, 0, replaceQuote(check));
/*      */       
/* 7095 */       table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
/* 7096 */       table_contents.setFont(nextRow, 2, plainFont);
/* 7097 */       table_contents.setAlignment(nextRow, 2, 33);
/* 7098 */       table_contents.setObject(nextRow, 2, "BRCInsert");
/*      */       
/* 7100 */       table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 7101 */       table_contents.setAlignment(nextRow, 5, 33);
/* 7102 */       table_contents.setFont(nextRow, 5, plainFont);
/* 7103 */       table_contents.setObject(nextRow, 5, replaceQuote(supplierName));
/*      */       
/* 7105 */       table_contents.setFont(nextRow, 9, plainFont);
/* 7106 */       table_contents.setAlignment(nextRow, 9, 34);
/* 7107 */       table_contents.setObject(nextRow, 9, replaceQuote(bomBrcInsertInk1));
/*      */       
/* 7109 */       table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
/* 7110 */       table_contents.setFont(nextRow, 12, plainFont);
/* 7111 */       table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
/* 7112 */       table_contents.setAlignment(nextRow, 12, 33);
/* 7113 */       nextRow++;
/*      */ 
/*      */       
/* 7116 */       check = "";
/*      */       
/* 7118 */       if (bomDiskDetail.miniStatusIndicator) {
/* 7119 */         check = "x";
/*      */       } else {
/* 7121 */         check = "";
/*      */       } 
/*      */       
/* 7124 */       supplierID = Integer.toString(bomDiskDetail.miniSupplierId);
/*      */       
/* 7126 */       supplier = MilestoneHelper.getSupplierById(supplierID);
/* 7127 */       supplierName = (supplier == null) ? "" : supplier.getName();
/*      */       
/* 7129 */       String bomMiniJacketInk1 = String.valueOf(bomDiskDetail.miniInk1) + "/" + 
/* 7130 */         bomDiskDetail.miniInk2;
/*      */       
/* 7132 */       addInfoText = bomDiskDetail.miniInfo;
/*      */ 
/*      */       
/* 7135 */       table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 7136 */       table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 7137 */       table_contents.setRowBorderColor(nextRow, 8, Color.white);
/* 7138 */       table_contents.setRowBorderColor(nextRow, 10, Color.white);
/* 7139 */       table_contents.setRowBorderColor(nextRow, 11, Color.white);
/*      */       
/* 7141 */       table_contents.setFont(nextRow, 0, boldFont);
/* 7142 */       table_contents.setAlignment(nextRow, 0, 34);
/* 7143 */       table_contents.setObject(nextRow, 0, replaceQuote(check));
/*      */       
/* 7145 */       table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
/* 7146 */       table_contents.setFont(nextRow, 2, plainFont);
/* 7147 */       table_contents.setAlignment(nextRow, 2, 33);
/* 7148 */       table_contents.setObject(nextRow, 2, "Mini Jacket");
/*      */       
/* 7150 */       table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 7151 */       table_contents.setAlignment(nextRow, 5, 33);
/* 7152 */       table_contents.setFont(nextRow, 5, plainFont);
/* 7153 */       table_contents.setObject(nextRow, 5, replaceQuote(supplierName));
/*      */       
/* 7155 */       table_contents.setFont(nextRow, 9, plainFont);
/* 7156 */       table_contents.setAlignment(nextRow, 9, 34);
/* 7157 */       table_contents.setObject(nextRow, 9, replaceQuote(bomMiniJacketInk1));
/*      */       
/* 7159 */       table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
/* 7160 */       table_contents.setFont(nextRow, 12, plainFont);
/* 7161 */       table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
/* 7162 */       table_contents.setAlignment(nextRow, 12, 33);
/* 7163 */       nextRow++;
/*      */ 
/*      */ 
/*      */       
/* 7167 */       check = "";
/*      */       
/* 7169 */       if (bomDiskDetail.digiPakStatusIndicator) {
/* 7170 */         check = "x";
/*      */       } else {
/* 7172 */         check = "";
/*      */       } 
/* 7174 */       supplierID = Integer.toString(bomDiskDetail.digiPakSupplierId);
/*      */       
/* 7176 */       supplier = MilestoneHelper.getSupplierById(supplierID);
/* 7177 */       supplierName = (supplier == null) ? "" : supplier.getName();
/*      */       
/* 7179 */       String bomDigiPakInk1 = String.valueOf(bomDiskDetail.digiPakInk1) + "/" + 
/* 7180 */         bomDiskDetail.digiPakInk2;
/*      */       
/* 7182 */       String trayDigipak = bomDiskDetail.digiPakTray;
/*      */       
/* 7184 */       trayDigipak = (trayDigipak == null || trayDigipak.trim().equals("")) ? 
/* 7185 */         "" : ("Tray: " + trayDigipak + "  ");
/*      */       
/* 7187 */       addInfoText = String.valueOf(trayDigipak) + bomDiskDetail.digiPakInfo;
/*      */ 
/*      */       
/* 7190 */       table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 7191 */       table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 7192 */       table_contents.setRowBorderColor(nextRow, 8, Color.white);
/* 7193 */       table_contents.setRowBorderColor(nextRow, 10, Color.white);
/* 7194 */       table_contents.setRowBorderColor(nextRow, 11, Color.white);
/*      */       
/* 7196 */       table_contents.setFont(nextRow, 0, boldFont);
/* 7197 */       table_contents.setAlignment(nextRow, 0, 34);
/* 7198 */       table_contents.setObject(nextRow, 0, replaceQuote(check));
/*      */       
/* 7200 */       table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
/* 7201 */       table_contents.setFont(nextRow, 2, plainFont);
/* 7202 */       table_contents.setAlignment(nextRow, 2, 33);
/* 7203 */       table_contents.setObject(nextRow, 2, "Digipak");
/*      */       
/* 7205 */       table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 7206 */       table_contents.setAlignment(nextRow, 5, 33);
/* 7207 */       table_contents.setFont(nextRow, 5, plainFont);
/* 7208 */       table_contents.setObject(nextRow, 5, replaceQuote(supplierName));
/*      */       
/* 7210 */       table_contents.setFont(nextRow, 9, plainFont);
/* 7211 */       table_contents.setAlignment(nextRow, 9, 34);
/* 7212 */       table_contents.setObject(nextRow, 9, replaceQuote(bomDigiPakInk1));
/*      */       
/* 7214 */       table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
/* 7215 */       table_contents.setFont(nextRow, 12, plainFont);
/* 7216 */       table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
/* 7217 */       table_contents.setAlignment(nextRow, 12, 33);
/* 7218 */       nextRow++;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 7224 */       check = "";
/*      */       
/* 7226 */       if (bomDiskDetail.softPakStatusIndicator) {
/* 7227 */         check = "x";
/*      */       } else {
/* 7229 */         check = "";
/*      */       } 
/* 7231 */       supplierID = Integer.toString(bomDiskDetail.softPakSupplierId);
/*      */       
/* 7233 */       supplier = MilestoneHelper.getSupplierById(supplierID);
/* 7234 */       supplierName = (supplier == null) ? "" : supplier.getName();
/*      */       
/* 7236 */       String bomSoftPakInk1 = String.valueOf(bomDiskDetail.softPakInk1) + "/" + 
/* 7237 */         bomDiskDetail.softPakInk2;
/*      */       
/* 7239 */       String traySoftpak = bomDiskDetail.softPakTray;
/*      */       
/* 7241 */       traySoftpak = (traySoftpak == null || traySoftpak.trim().equals("")) ? 
/* 7242 */         "" : ("Tray: " + traySoftpak + "  ");
/*      */       
/* 7244 */       addInfoText = String.valueOf(traySoftpak) + bomDiskDetail.softPakInfo;
/*      */ 
/*      */       
/* 7247 */       table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 7248 */       table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 7249 */       table_contents.setRowBorderColor(nextRow, 8, Color.white);
/* 7250 */       table_contents.setRowBorderColor(nextRow, 10, Color.white);
/* 7251 */       table_contents.setRowBorderColor(nextRow, 11, Color.white);
/*      */       
/* 7253 */       table_contents.setFont(nextRow, 0, boldFont);
/* 7254 */       table_contents.setAlignment(nextRow, 0, 34);
/* 7255 */       table_contents.setObject(nextRow, 0, replaceQuote(check));
/*      */       
/* 7257 */       table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
/* 7258 */       table_contents.setFont(nextRow, 2, plainFont);
/* 7259 */       table_contents.setAlignment(nextRow, 2, 33);
/* 7260 */       table_contents.setObject(nextRow, 2, "Softpak");
/*      */       
/* 7262 */       table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 7263 */       table_contents.setAlignment(nextRow, 5, 33);
/* 7264 */       table_contents.setFont(nextRow, 5, plainFont);
/* 7265 */       table_contents.setObject(nextRow, 5, replaceQuote(supplierName));
/*      */       
/* 7267 */       table_contents.setFont(nextRow, 9, plainFont);
/* 7268 */       table_contents.setAlignment(nextRow, 9, 34);
/* 7269 */       table_contents.setObject(nextRow, 9, replaceQuote(bomSoftPakInk1));
/*      */       
/* 7271 */       table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
/* 7272 */       table_contents.setFont(nextRow, 12, plainFont);
/* 7273 */       table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
/* 7274 */       table_contents.setAlignment(nextRow, 12, 33);
/* 7275 */       nextRow++;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 7282 */       check = "";
/*      */       
/* 7284 */       if (bomDiskDetail.stickerOneStatusIndicator) {
/* 7285 */         check = "x";
/*      */       } else {
/* 7287 */         check = "";
/*      */       } 
/* 7289 */       supplierID = Integer.toString(bomDiskDetail.stickerOneSupplierId);
/*      */       
/* 7291 */       supplier = MilestoneHelper.getSupplierById(supplierID);
/* 7292 */       supplierName = (supplier == null) ? "" : supplier.getName();
/*      */       
/* 7294 */       String bomSticker1Ink1 = String.valueOf(bomDiskDetail.stickerOneInk1) + "/" + 
/* 7295 */         bomDiskDetail.stickerOneInk2;
/*      */       
/* 7297 */       String placesSticker1 = bomDiskDetail.stickerOnePlaces;
/*      */       
/* 7299 */       placesSticker1 = (placesSticker1 == null || placesSticker1.trim().equals("")) ? 
/* 7300 */         "" : ("Places: " + placesSticker1 + "  ");
/*      */       
/* 7302 */       addInfoText = String.valueOf(placesSticker1) + bomDiskDetail.stickerOneInfo;
/*      */ 
/*      */ 
/*      */       
/* 7306 */       table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 7307 */       table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 7308 */       table_contents.setRowBorderColor(nextRow, 8, Color.white);
/* 7309 */       table_contents.setRowBorderColor(nextRow, 10, Color.white);
/* 7310 */       table_contents.setRowBorderColor(nextRow, 11, Color.white);
/*      */       
/* 7312 */       table_contents.setFont(nextRow, 0, boldFont);
/* 7313 */       table_contents.setAlignment(nextRow, 0, 34);
/* 7314 */       table_contents.setObject(nextRow, 0, replaceQuote(check));
/*      */       
/* 7316 */       table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
/* 7317 */       table_contents.setFont(nextRow, 2, plainFont);
/* 7318 */       table_contents.setAlignment(nextRow, 2, 33);
/* 7319 */       table_contents.setObject(nextRow, 2, "Sticker1");
/*      */       
/* 7321 */       table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 7322 */       table_contents.setAlignment(nextRow, 5, 33);
/* 7323 */       table_contents.setFont(nextRow, 5, plainFont);
/* 7324 */       table_contents.setObject(nextRow, 5, replaceQuote(supplierName));
/*      */       
/* 7326 */       table_contents.setFont(nextRow, 9, plainFont);
/* 7327 */       table_contents.setAlignment(nextRow, 9, 34);
/* 7328 */       table_contents.setObject(nextRow, 9, replaceQuote(bomSticker1Ink1));
/*      */       
/* 7330 */       table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
/* 7331 */       table_contents.setFont(nextRow, 12, plainFont);
/* 7332 */       table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
/* 7333 */       table_contents.setAlignment(nextRow, 12, 33);
/* 7334 */       nextRow++;
/*      */ 
/*      */       
/* 7337 */       check = "";
/*      */       
/* 7339 */       if (bomDiskDetail.stickerTwoStatusIndicator) {
/* 7340 */         check = "x";
/*      */       } else {
/* 7342 */         check = "";
/*      */       } 
/* 7344 */       supplierID = Integer.toString(bomDiskDetail.stickerTwoSupplierId);
/*      */       
/* 7346 */       supplier = MilestoneHelper.getSupplierById(supplierID);
/* 7347 */       supplierName = (supplier == null) ? "" : supplier.getName();
/*      */       
/* 7349 */       String bomSticker2Ink1 = String.valueOf(bomDiskDetail.stickerTwoInk1) + "/" + 
/* 7350 */         bomDiskDetail.stickerTwoInk2;
/*      */       
/* 7352 */       String placesSticker2 = bomDiskDetail.stickerTwoPlaces;
/*      */       
/* 7354 */       placesSticker2 = (placesSticker2 == null || placesSticker2.trim().equals("")) ? 
/* 7355 */         "" : ("Places: " + placesSticker2 + "  ");
/*      */       
/* 7357 */       addInfoText = String.valueOf(placesSticker2) + bomDiskDetail.stickerTwoInfo;
/*      */ 
/*      */       
/* 7360 */       table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 7361 */       table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 7362 */       table_contents.setRowBorderColor(nextRow, 8, Color.white);
/* 7363 */       table_contents.setRowBorderColor(nextRow, 10, Color.white);
/* 7364 */       table_contents.setRowBorderColor(nextRow, 11, Color.white);
/*      */       
/* 7366 */       table_contents.setFont(nextRow, 0, boldFont);
/* 7367 */       table_contents.setAlignment(nextRow, 0, 34);
/* 7368 */       table_contents.setObject(nextRow, 0, replaceQuote(check));
/*      */       
/* 7370 */       table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
/* 7371 */       table_contents.setFont(nextRow, 2, plainFont);
/* 7372 */       table_contents.setAlignment(nextRow, 2, 33);
/* 7373 */       table_contents.setObject(nextRow, 2, "Sticker2");
/*      */       
/* 7375 */       table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 7376 */       table_contents.setAlignment(nextRow, 5, 33);
/* 7377 */       table_contents.setFont(nextRow, 5, plainFont);
/* 7378 */       table_contents.setObject(nextRow, 5, replaceQuote(supplierName));
/*      */       
/* 7380 */       table_contents.setFont(nextRow, 9, plainFont);
/* 7381 */       table_contents.setAlignment(nextRow, 9, 34);
/* 7382 */       table_contents.setObject(nextRow, 9, replaceQuote(bomSticker2Ink1));
/*      */       
/* 7384 */       table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
/* 7385 */       table_contents.setFont(nextRow, 12, plainFont);
/* 7386 */       table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
/* 7387 */       table_contents.setAlignment(nextRow, 12, 33);
/* 7388 */       nextRow++;
/*      */ 
/*      */       
/* 7391 */       check = "";
/*      */       
/* 7393 */       if (bomDiskDetail.bookStatusIndicator) {
/* 7394 */         check = "x";
/*      */       } else {
/* 7396 */         check = "";
/*      */       } 
/* 7398 */       supplierID = Integer.toString(bomDiskDetail.bookSupplierId);
/*      */       
/* 7400 */       supplier = MilestoneHelper.getSupplierById(supplierID);
/* 7401 */       supplierName = (supplier == null) ? "" : supplier.getName();
/*      */       
/* 7403 */       String bomBookSetInk1 = String.valueOf(bomDiskDetail.bookInk1) + "/" + 
/* 7404 */         bomDiskDetail.bookInk2;
/*      */       
/* 7406 */       String booksetPages = bomDiskDetail.bookPages;
/*      */       
/* 7408 */       booksetPages = (booksetPages == null || booksetPages.trim().equals("")) ? 
/* 7409 */         "" : ("Pages: " + booksetPages + "  ");
/*      */       
/* 7411 */       addInfoText = String.valueOf(booksetPages) + bomDiskDetail.bookInfo;
/*      */ 
/*      */       
/* 7414 */       table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 7415 */       table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 7416 */       table_contents.setRowBorderColor(nextRow, 8, Color.white);
/* 7417 */       table_contents.setRowBorderColor(nextRow, 10, Color.white);
/* 7418 */       table_contents.setRowBorderColor(nextRow, 11, Color.white);
/*      */       
/* 7420 */       table_contents.setFont(nextRow, 0, boldFont);
/* 7421 */       table_contents.setAlignment(nextRow, 0, 34);
/* 7422 */       table_contents.setObject(nextRow, 0, replaceQuote(check));
/*      */       
/* 7424 */       table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
/* 7425 */       table_contents.setFont(nextRow, 2, plainFont);
/* 7426 */       table_contents.setAlignment(nextRow, 2, 33);
/* 7427 */       table_contents.setObject(nextRow, 2, "Book(Other/Set)");
/*      */       
/* 7429 */       table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 7430 */       table_contents.setAlignment(nextRow, 5, 33);
/* 7431 */       table_contents.setFont(nextRow, 5, plainFont);
/* 7432 */       table_contents.setObject(nextRow, 5, replaceQuote(supplierName));
/*      */       
/* 7434 */       table_contents.setFont(nextRow, 9, plainFont);
/* 7435 */       table_contents.setAlignment(nextRow, 9, 34);
/* 7436 */       table_contents.setObject(nextRow, 9, replaceQuote(bomBookSetInk1));
/*      */       
/* 7438 */       table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
/* 7439 */       table_contents.setFont(nextRow, 12, plainFont);
/* 7440 */       table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
/* 7441 */       table_contents.setAlignment(nextRow, 12, 33);
/* 7442 */       nextRow++;
/*      */ 
/*      */       
/* 7445 */       check = "";
/*      */       
/* 7447 */       if (bomDiskDetail.boxStatusIndicator) {
/* 7448 */         check = "x";
/*      */       } else {
/* 7450 */         check = "";
/*      */       } 
/* 7452 */       supplierID = Integer.toString(bomDiskDetail.boxSupplierId);
/*      */       
/* 7454 */       supplier = MilestoneHelper.getSupplierById(supplierID);
/* 7455 */       supplierName = (supplier == null) ? "" : supplier.getName();
/*      */       
/* 7457 */       String bomBoxSetInk1 = String.valueOf(bomDiskDetail.boxInk1) + "/" + 
/* 7458 */         bomDiskDetail.boxInk2;
/*      */       
/* 7460 */       String boxsetSize = bomDiskDetail.boxSizes;
/*      */       
/* 7462 */       boxsetSize = (boxsetSize == null || boxsetSize.trim().equals("")) ? 
/* 7463 */         "" : ("Size: " + boxsetSize + "  ");
/*      */       
/* 7465 */       addInfoText = String.valueOf(boxsetSize) + bomDiskDetail.boxInfo;
/*      */ 
/*      */ 
/*      */       
/* 7469 */       table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 7470 */       table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 7471 */       table_contents.setRowBorderColor(nextRow, 8, Color.white);
/* 7472 */       table_contents.setRowBorderColor(nextRow, 10, Color.white);
/* 7473 */       table_contents.setRowBorderColor(nextRow, 11, Color.white);
/*      */       
/* 7475 */       table_contents.setFont(nextRow, 0, boldFont);
/* 7476 */       table_contents.setAlignment(nextRow, 0, 34);
/* 7477 */       table_contents.setObject(nextRow, 0, replaceQuote(check));
/*      */       
/* 7479 */       table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
/* 7480 */       table_contents.setFont(nextRow, 2, plainFont);
/* 7481 */       table_contents.setAlignment(nextRow, 2, 33);
/* 7482 */       table_contents.setObject(nextRow, 2, "Box(Set)");
/*      */       
/* 7484 */       table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 7485 */       table_contents.setAlignment(nextRow, 5, 33);
/* 7486 */       table_contents.setFont(nextRow, 5, plainFont);
/* 7487 */       table_contents.setObject(nextRow, 5, replaceQuote(supplierName));
/*      */       
/* 7489 */       table_contents.setFont(nextRow, 9, plainFont);
/* 7490 */       table_contents.setAlignment(nextRow, 9, 34);
/* 7491 */       table_contents.setObject(nextRow, 9, replaceQuote(bomBoxSetInk1));
/*      */       
/* 7493 */       table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
/* 7494 */       table_contents.setFont(nextRow, 12, plainFont);
/* 7495 */       table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
/* 7496 */       table_contents.setAlignment(nextRow, 12, 33);
/* 7497 */       nextRow++;
/*      */ 
/*      */ 
/*      */       
/* 7501 */       check = "";
/*      */       
/* 7503 */       if (bomDiskDetail.otherStatusIndicator) {
/* 7504 */         check = "x";
/*      */       } else {
/* 7506 */         check = "";
/*      */       } 
/* 7508 */       supplierID = Integer.toString(bomDiskDetail.otherSupplierId);
/*      */       
/* 7510 */       supplier = MilestoneHelper.getSupplierById(supplierID);
/* 7511 */       supplierName = (supplier == null) ? "" : supplier.getName();
/*      */       
/* 7513 */       String bomOtherInk1 = String.valueOf(bomDiskDetail.otherInk1) + "/" + 
/* 7514 */         bomDiskDetail.otherInk2;
/*      */       
/* 7516 */       addInfoText = bomDiskDetail.otherInfo;
/*      */ 
/*      */       
/* 7519 */       table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 7520 */       table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 7521 */       table_contents.setRowBorderColor(nextRow, 8, Color.white);
/* 7522 */       table_contents.setRowBorderColor(nextRow, 10, Color.white);
/* 7523 */       table_contents.setRowBorderColor(nextRow, 11, Color.white);
/*      */       
/* 7525 */       table_contents.setFont(nextRow, 0, boldFont);
/* 7526 */       table_contents.setAlignment(nextRow, 0, 34);
/* 7527 */       table_contents.setObject(nextRow, 0, replaceQuote(check));
/*      */       
/* 7529 */       table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
/* 7530 */       table_contents.setFont(nextRow, 2, plainFont);
/* 7531 */       table_contents.setAlignment(nextRow, 2, 33);
/* 7532 */       table_contents.setObject(nextRow, 2, "Other");
/*      */       
/* 7534 */       table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 7535 */       table_contents.setAlignment(nextRow, 5, 33);
/* 7536 */       table_contents.setFont(nextRow, 5, plainFont);
/* 7537 */       table_contents.setObject(nextRow, 5, replaceQuote(supplierName));
/*      */       
/* 7539 */       table_contents.setFont(nextRow, 9, plainFont);
/* 7540 */       table_contents.setAlignment(nextRow, 9, 34);
/* 7541 */       table_contents.setObject(nextRow, 9, replaceQuote(bomOtherInk1));
/*      */       
/* 7543 */       table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
/* 7544 */       table_contents.setFont(nextRow, 12, plainFont);
/* 7545 */       table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
/* 7546 */       table_contents.setAlignment(nextRow, 12, 33);
/* 7547 */       nextRow++;
/*      */ 
/*      */       
/* 7550 */       table_contents.setRowBorderColor(nextRow, Color.white);
/* 7551 */       table_contents.setRowHeight(nextRow, 10);
/* 7552 */       nextRow++;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 7557 */       table_contents.setRowBorderColor(nextRow, Color.white);
/* 7558 */       table_contents.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
/* 7559 */       table_contents.setSpan(nextRow, 0, new Dimension(6, 1));
/* 7560 */       table_contents.setFont(nextRow, 0, new Font("Arial", 1, 12));
/* 7561 */       table_contents.setAlignment(nextRow, 0, 9);
/* 7562 */       table_contents.setObject(nextRow, 0, "Special Instructions:");
/*      */       
/* 7564 */       table_contents.setSpan(nextRow, 6, new Dimension(14, 1));
/* 7565 */       table_contents.setFont(nextRow, 6, new Font("Arial", 0, 11));
/* 7566 */       table_contents.setAlignment(nextRow, 6, 9);
/*      */       
/* 7568 */       String specialInstructionsString = "";
/*      */       
/* 7570 */       if (bom.getSpecialInstructions().length() > 0) {
/* 7571 */         specialInstructionsString = bom.getSpecialInstructions();
/*      */       }
/* 7573 */       table_contents.setObject(nextRow, 6, String.valueOf(replaceQuote(specialInstructionsString)) + "\n");
/*      */       
/* 7575 */       report.setElement("table_colheaders", table_contents);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addCassetteReportElements(XStyleSheet report, Bom bom, Context context, DefaultTableLens table_contents, Selection mySelection) {
/* 7593 */     if (bom != null) {
/*      */       
/* 7595 */       BomCassetteDetail bomCassetteDetail = bom.getBomCassetteDetail();
/*      */       
/* 7597 */       table_contents.setHeaderRowCount(0);
/* 7598 */       table_contents.setColWidth(0, 16);
/* 7599 */       table_contents.setColWidth(1, 2);
/* 7600 */       table_contents.setColWidth(2, 15);
/* 7601 */       table_contents.setColWidth(3, 40);
/* 7602 */       table_contents.setColWidth(4, 2);
/* 7603 */       table_contents.setColWidth(5, 60);
/* 7604 */       table_contents.setColWidth(6, 40);
/* 7605 */       table_contents.setColWidth(7, 40);
/* 7606 */       table_contents.setColWidth(8, 2);
/* 7607 */       table_contents.setColWidth(9, 50);
/* 7608 */       table_contents.setColWidth(10, 10);
/* 7609 */       table_contents.setColWidth(11, 10);
/* 7610 */       table_contents.setColWidth(12, 20);
/* 7611 */       table_contents.setColWidth(13, 40);
/* 7612 */       table_contents.setColWidth(14, 40);
/* 7613 */       table_contents.setColWidth(15, 110);
/* 7614 */       table_contents.setColWidth(16, 45);
/* 7615 */       table_contents.setColWidth(17, 30);
/* 7616 */       table_contents.setColWidth(18, 20);
/* 7617 */       table_contents.setColWidth(19, 40);
/*      */       
/* 7619 */       table_contents.setRowInsets(10, new Insets(1, 0, 0, 0));
/* 7620 */       table_contents.setRowInsets(11, new Insets(1, 0, 0, 0));
/* 7621 */       table_contents.setRowInsets(12, new Insets(1, 0, 0, 0));
/* 7622 */       table_contents.setRowInsets(13, new Insets(1, 0, 0, 0));
/* 7623 */       table_contents.setRowInsets(14, new Insets(1, 0, 0, 0));
/* 7624 */       table_contents.setRowInsets(15, new Insets(1, 0, 0, 0));
/* 7625 */       table_contents.setRowInsets(16, new Insets(1, 0, 0, 0));
/* 7626 */       table_contents.setRowInsets(17, new Insets(1, 0, 0, 0));
/* 7627 */       table_contents.setRowInsets(18, new Insets(1, 0, 0, 0));
/* 7628 */       table_contents.setRowInsets(19, new Insets(1, 0, 0, 0));
/* 7629 */       table_contents.setRowInsets(20, new Insets(1, 0, 0, 0));
/* 7630 */       table_contents.setRowInsets(21, new Insets(1, 0, 0, 0));
/* 7631 */       table_contents.setRowInsets(22, new Insets(1, 0, 0, 0));
/* 7632 */       table_contents.setRowInsets(23, new Insets(1, 0, 0, 0));
/* 7633 */       table_contents.setRowInsets(24, new Insets(1, 0, 0, 0));
/* 7634 */       table_contents.setRowInsets(25, new Insets(1, 0, 0, 0));
/* 7635 */       table_contents.setRowInsets(26, new Insets(1, 0, 0, 0));
/* 7636 */       table_contents.setRowInsets(27, new Insets(1, 0, 0, 0));
/* 7637 */       table_contents.setRowInsets(28, new Insets(1, 0, 0, 0));
/* 7638 */       table_contents.setRowInsets(29, new Insets(1, 0, 0, 0));
/*      */       
/* 7640 */       int nextRow = 12;
/* 7641 */       Font boldFont = new Font("Arial", 1, 10);
/* 7642 */       Font plainFont = new Font("Arial", 0, 9);
/*      */ 
/*      */       
/* 7645 */       String stringUnitsPerPkg = "";
/*      */       
/* 7647 */       if (mySelection.getNumberOfUnits() > 0 && 
/* 7648 */         !Integer.toString(mySelection.getNumberOfUnits()).equals(""))
/*      */       {
/* 7650 */         stringUnitsPerPkg = Integer.toString(mySelection.getNumberOfUnits());
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 7655 */       String noShrinkwrap = context.getRequestValue("UseNoShrinkWrap");
/* 7656 */       if (bom.useShrinkWrap()) {
/* 7657 */         noShrinkwrap = "X";
/*      */       } else {
/* 7659 */         noShrinkwrap = "";
/*      */       } 
/*      */       
/* 7662 */       table_contents.setRowInsets(nextRow, new Insets(1, 0, 0, 0));
/* 7663 */       table_contents.setRowBorderColor(nextRow, 0, Color.white);
/* 7664 */       table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 7665 */       table_contents.setRowBorderColor(nextRow, 2, Color.white);
/* 7666 */       table_contents.setRowBorderColor(nextRow, 3, Color.white);
/* 7667 */       table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 7668 */       table_contents.setRowBorderColor(nextRow, 5, Color.white);
/* 7669 */       table_contents.setRowBorderColor(nextRow, 6, Color.white);
/* 7670 */       table_contents.setRowBorderColor(nextRow, 7, Color.white);
/* 7671 */       table_contents.setRowBorderColor(nextRow, 8, Color.white);
/* 7672 */       table_contents.setRowBorderColor(nextRow, 9, Color.white);
/* 7673 */       table_contents.setRowBorderColor(nextRow, 12, Color.white);
/* 7674 */       table_contents.setRowBorderColor(nextRow, 13, Color.white);
/* 7675 */       table_contents.setRowBorderColor(nextRow, 14, Color.white);
/* 7676 */       table_contents.setRowBorderColor(nextRow, 16, Color.white);
/* 7677 */       table_contents.setRowBorderColor(nextRow, 17, Color.white);
/* 7678 */       table_contents.setRowBorderColor(nextRow, 18, Color.white);
/*      */ 
/*      */       
/* 7681 */       table_contents.setSpan(nextRow, 0, new Dimension(7, 1));
/* 7682 */       table_contents.setFont(nextRow, 0, boldFont);
/* 7683 */       table_contents.setAlignment(nextRow, 0, 33);
/* 7684 */       table_contents.setObject(nextRow, 0, "Cassette");
/*      */ 
/*      */ 
/*      */       
/* 7688 */       table_contents.setSpan(nextRow, 7, new Dimension(3, 1));
/* 7689 */       table_contents.setFont(nextRow, 7, boldFont);
/* 7690 */       table_contents.setAlignment(nextRow, 7, 36);
/* 7691 */       table_contents.setObject(nextRow, 7, "# of Units:");
/* 7692 */       table_contents.setSpan(nextRow, 10, new Dimension(2, 1));
/* 7693 */       table_contents.setAlignment(nextRow, 10, 34);
/* 7694 */       table_contents.setFont(nextRow, 10, plainFont);
/* 7695 */       table_contents.setObject(nextRow, 10, replaceQuote(stringUnitsPerPkg));
/*      */ 
/*      */       
/* 7698 */       table_contents.setSpan(nextRow, 13, new Dimension(2, 1));
/* 7699 */       table_contents.setFont(nextRow, 13, boldFont);
/* 7700 */       table_contents.setAlignment(nextRow, 13, 36);
/* 7701 */       table_contents.setObject(nextRow, 13, "Run Time(s):");
/* 7702 */       table_contents.setFont(nextRow, 15, new Font("Arial", 0, 10));
/* 7703 */       table_contents.setObject(nextRow, 15, replaceQuote(bom.getRunTime()));
/* 7704 */       table_contents.setAlignment(nextRow, 15, 34);
/*      */ 
/*      */       
/* 7707 */       table_contents.setSpan(nextRow, 16, new Dimension(3, 1));
/* 7708 */       table_contents.setFont(nextRow, 16, boldFont);
/* 7709 */       table_contents.setAlignment(nextRow, 16, 36);
/* 7710 */       table_contents.setObject(nextRow, 16, "Shrink Wrap:");
/* 7711 */       table_contents.setFont(nextRow, 19, new Font("Arial", 0, 10));
/* 7712 */       table_contents.setObject(nextRow, 19, replaceQuote(noShrinkwrap));
/* 7713 */       table_contents.setAlignment(nextRow, 19, 34);
/* 7714 */       nextRow++;
/*      */ 
/*      */       
/* 7717 */       table_contents.setRowHeight(nextRow, 5);
/* 7718 */       table_contents.setRowBorderColor(nextRow, Color.black);
/* 7719 */       nextRow++;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 7724 */       table_contents.setRowInsets(nextRow, new Insets(1, 0, 0, 0));
/*      */ 
/*      */       
/* 7727 */       table_contents.setRowBorderColor(nextRow, Color.white);
/*      */       
/* 7729 */       table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
/* 7730 */       table_contents.setFont(nextRow, 2, boldFont);
/* 7731 */       table_contents.setAlignment(nextRow, 2, 33);
/* 7732 */       table_contents.setObject(nextRow, 2, "Part");
/*      */       
/* 7734 */       table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 7735 */       table_contents.setAlignment(nextRow, 5, 33);
/* 7736 */       table_contents.setFont(nextRow, 5, boldFont);
/* 7737 */       table_contents.setObject(nextRow, 5, "Supplier");
/*      */       
/* 7739 */       table_contents.setFont(nextRow, 9, boldFont);
/* 7740 */       table_contents.setAlignment(nextRow, 9, 34);
/* 7741 */       table_contents.setObject(nextRow, 9, "Ink");
/*      */       
/* 7743 */       table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
/* 7744 */       table_contents.setFont(nextRow, 12, boldFont);
/* 7745 */       table_contents.setObject(nextRow, 12, "Additional Information");
/* 7746 */       table_contents.setAlignment(nextRow, 12, 33);
/* 7747 */       nextRow++;
/*      */ 
/*      */       
/* 7750 */       String check = "";
/*      */       
/* 7752 */       if (bomCassetteDetail.coStatusIndicator) {
/* 7753 */         check = "x";
/*      */       } else {
/* 7755 */         check = "";
/*      */       } 
/* 7757 */       String supplierC0ID = Integer.toString(bomCassetteDetail.coParSupplierId);
/*      */       
/* 7759 */       LookupObject supplierc0 = MilestoneHelper.getSupplierById(supplierC0ID);
/* 7760 */       String supplierNamec0 = (supplierc0 == null) ? "" : supplierc0.getName();
/*      */ 
/*      */       
/* 7763 */       String bomCOInk1 = String.valueOf(bomCassetteDetail.coInk1) + "/" + 
/* 7764 */         bomCassetteDetail.coInk2;
/*      */       
/* 7766 */       String c0Color = bomCassetteDetail.coColor;
/* 7767 */       c0Color = (c0Color == null || c0Color.length() <= 1) ? 
/* 7768 */         "" : ("Color: " + c0Color + "  ");
/* 7769 */       String addInfoText = String.valueOf(c0Color) + bomCassetteDetail.coInfo;
/*      */ 
/*      */       
/* 7772 */       table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 7773 */       table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 7774 */       table_contents.setRowBorderColor(nextRow, 8, Color.white);
/* 7775 */       table_contents.setRowBorderColor(nextRow, 10, Color.white);
/* 7776 */       table_contents.setRowBorderColor(nextRow, 11, Color.white);
/*      */       
/* 7778 */       table_contents.setFont(nextRow, 0, boldFont);
/* 7779 */       table_contents.setAlignment(nextRow, 0, 34);
/* 7780 */       table_contents.setObject(nextRow, 0, replaceQuote(check));
/*      */       
/* 7782 */       table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
/* 7783 */       table_contents.setFont(nextRow, 2, plainFont);
/* 7784 */       table_contents.setAlignment(nextRow, 2, 33);
/* 7785 */       table_contents.setObject(nextRow, 2, "C-O");
/*      */       
/* 7787 */       table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 7788 */       table_contents.setAlignment(nextRow, 5, 33);
/* 7789 */       table_contents.setFont(nextRow, 5, plainFont);
/*      */       
/* 7791 */       table_contents.setObject(nextRow, 5, supplierNamec0);
/*      */       
/* 7793 */       table_contents.setFont(nextRow, 9, plainFont);
/* 7794 */       table_contents.setAlignment(nextRow, 9, 34);
/* 7795 */       table_contents.setObject(nextRow, 9, replaceQuote(bomCOInk1));
/*      */       
/* 7797 */       table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
/* 7798 */       table_contents.setFont(nextRow, 12, plainFont);
/* 7799 */       table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
/* 7800 */       table_contents.setAlignment(nextRow, 12, 33);
/* 7801 */       nextRow++;
/*      */ 
/*      */       
/* 7804 */       check = "";
/*      */       
/* 7806 */       if (bomCassetteDetail.norelcoStatusIndicator) {
/* 7807 */         check = "x";
/*      */       } else {
/* 7809 */         check = "";
/*      */       } 
/* 7811 */       String supplierNorelcoID = Integer.toString(bomCassetteDetail.norelcoSupplierId);
/*      */       
/* 7813 */       LookupObject suppliernorelco = MilestoneHelper.getSupplierById(supplierNorelcoID);
/* 7814 */       String supplierNameNorelco = (suppliernorelco == null) ? "" : suppliernorelco.getName();
/*      */       
/* 7816 */       String norelcoColor = bomCassetteDetail.norelcoColor;
/*      */       
/* 7818 */       norelcoColor = (norelcoColor == null || norelcoColor.length() <= 1) ? 
/* 7819 */         "" : ("Color: " + norelcoColor + "  ");
/* 7820 */       addInfoText = String.valueOf(norelcoColor) + bomCassetteDetail.norelcoInfo;
/*      */ 
/*      */       
/* 7823 */       table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 7824 */       table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 7825 */       table_contents.setRowBorderColor(nextRow, 8, Color.white);
/* 7826 */       table_contents.setRowBorderColor(nextRow, 10, Color.white);
/* 7827 */       table_contents.setRowBorderColor(nextRow, 11, Color.white);
/*      */       
/* 7829 */       table_contents.setFont(nextRow, 0, boldFont);
/* 7830 */       table_contents.setAlignment(nextRow, 0, 34);
/* 7831 */       table_contents.setObject(nextRow, 0, replaceQuote(check));
/*      */       
/* 7833 */       table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
/* 7834 */       table_contents.setFont(nextRow, 2, plainFont);
/* 7835 */       table_contents.setAlignment(nextRow, 2, 33);
/* 7836 */       table_contents.setObject(nextRow, 2, "Norelco");
/*      */       
/* 7838 */       table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 7839 */       table_contents.setAlignment(nextRow, 5, 33);
/* 7840 */       table_contents.setFont(nextRow, 5, plainFont);
/*      */       
/* 7842 */       table_contents.setObject(nextRow, 5, supplierNameNorelco);
/*      */       
/* 7844 */       table_contents.setFont(nextRow, 9, plainFont);
/* 7845 */       table_contents.setAlignment(nextRow, 9, 34);
/* 7846 */       table_contents.setObject(nextRow, 9, "/");
/*      */       
/* 7848 */       table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
/* 7849 */       table_contents.setFont(nextRow, 12, plainFont);
/* 7850 */       table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
/* 7851 */       table_contents.setAlignment(nextRow, 12, 33);
/* 7852 */       nextRow++;
/*      */ 
/*      */       
/* 7855 */       check = "";
/*      */       
/* 7857 */       if (bomCassetteDetail.jCardStatusIndicator) {
/* 7858 */         check = "x";
/*      */       } else {
/* 7860 */         check = "";
/*      */       } 
/* 7862 */       String supplierID = Integer.toString(bomCassetteDetail.jCardSupplierId);
/*      */       
/* 7864 */       LookupObject supplier = MilestoneHelper.getSupplierById(supplierID);
/* 7865 */       String supplierName = (supplier == null) ? "" : supplier.getName();
/*      */       
/* 7867 */       String bomJCardInk1 = String.valueOf(bomCassetteDetail.jCardInk1) + "/" + 
/* 7868 */         bomCassetteDetail.jCardInk2;
/*      */       
/* 7870 */       String jcardPanels = bomCassetteDetail.jCardPanels;
/*      */       
/* 7872 */       jcardPanels = (jcardPanels == null || jcardPanels.trim().equals("")) ? 
/* 7873 */         "" : ("Panels: " + jcardPanels + "  ");
/*      */       
/* 7875 */       addInfoText = String.valueOf(jcardPanels) + bomCassetteDetail.jCardInfo;
/*      */ 
/*      */       
/* 7878 */       table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 7879 */       table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 7880 */       table_contents.setRowBorderColor(nextRow, 8, Color.white);
/* 7881 */       table_contents.setRowBorderColor(nextRow, 10, Color.white);
/* 7882 */       table_contents.setRowBorderColor(nextRow, 11, Color.white);
/*      */       
/* 7884 */       table_contents.setFont(nextRow, 0, boldFont);
/* 7885 */       table_contents.setAlignment(nextRow, 0, 34);
/* 7886 */       table_contents.setObject(nextRow, 0, replaceQuote(check));
/*      */       
/* 7888 */       table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
/* 7889 */       table_contents.setFont(nextRow, 2, plainFont);
/* 7890 */       table_contents.setAlignment(nextRow, 2, 33);
/* 7891 */       table_contents.setObject(nextRow, 2, "J-Card");
/*      */       
/* 7893 */       table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 7894 */       table_contents.setAlignment(nextRow, 5, 33);
/* 7895 */       table_contents.setFont(nextRow, 5, plainFont);
/* 7896 */       table_contents.setObject(nextRow, 5, replaceQuote(supplierName));
/*      */       
/* 7898 */       table_contents.setFont(nextRow, 9, plainFont);
/* 7899 */       table_contents.setAlignment(nextRow, 9, 34);
/* 7900 */       table_contents.setObject(nextRow, 9, replaceQuote(bomJCardInk1));
/*      */       
/* 7902 */       table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
/* 7903 */       table_contents.setFont(nextRow, 12, plainFont);
/* 7904 */       table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
/* 7905 */       table_contents.setAlignment(nextRow, 12, 33);
/* 7906 */       nextRow++;
/*      */ 
/*      */       
/* 7909 */       check = "";
/*      */       
/* 7911 */       if (bomCassetteDetail.uCardStatusIndicator) {
/* 7912 */         check = "x";
/*      */       } else {
/* 7914 */         check = "";
/*      */       } 
/* 7916 */       supplierID = Integer.toString(bomCassetteDetail.uCardSupplierId);
/*      */       
/* 7918 */       supplier = MilestoneHelper.getSupplierById(supplierID);
/* 7919 */       supplierName = (supplier == null) ? "" : supplier.getName();
/*      */       
/* 7921 */       String bomUCardInk1 = String.valueOf(bomCassetteDetail.uCardInk1) + "/" + 
/* 7922 */         bomCassetteDetail.uCardInk2;
/*      */       
/* 7924 */       String ucardPanels = bomCassetteDetail.uCardPanels;
/*      */       
/* 7926 */       ucardPanels = (ucardPanels == null || ucardPanels.trim().equals("")) ? 
/* 7927 */         "" : ("Panels: " + ucardPanels + "  ");
/* 7928 */       addInfoText = String.valueOf(ucardPanels) + bomCassetteDetail.uCardInfo;
/*      */ 
/*      */       
/* 7931 */       table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 7932 */       table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 7933 */       table_contents.setRowBorderColor(nextRow, 8, Color.white);
/* 7934 */       table_contents.setRowBorderColor(nextRow, 10, Color.white);
/* 7935 */       table_contents.setRowBorderColor(nextRow, 11, Color.white);
/*      */       
/* 7937 */       table_contents.setFont(nextRow, 0, boldFont);
/* 7938 */       table_contents.setAlignment(nextRow, 0, 34);
/* 7939 */       table_contents.setObject(nextRow, 0, replaceQuote(check));
/*      */       
/* 7941 */       table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
/* 7942 */       table_contents.setFont(nextRow, 2, plainFont);
/* 7943 */       table_contents.setAlignment(nextRow, 2, 33);
/* 7944 */       table_contents.setObject(nextRow, 2, "U-Card");
/*      */       
/* 7946 */       table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 7947 */       table_contents.setAlignment(nextRow, 5, 33);
/* 7948 */       table_contents.setFont(nextRow, 5, plainFont);
/* 7949 */       table_contents.setObject(nextRow, 5, replaceQuote(supplierName));
/*      */       
/* 7951 */       table_contents.setFont(nextRow, 9, plainFont);
/* 7952 */       table_contents.setAlignment(nextRow, 9, 34);
/* 7953 */       table_contents.setObject(nextRow, 9, replaceQuote(bomUCardInk1));
/*      */       
/* 7955 */       table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
/* 7956 */       table_contents.setFont(nextRow, 12, plainFont);
/* 7957 */       table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
/* 7958 */       table_contents.setAlignment(nextRow, 12, 33);
/* 7959 */       nextRow++;
/*      */ 
/*      */       
/* 7962 */       check = "";
/*      */       
/* 7964 */       if (bomCassetteDetail.oCardStatusIndicator) {
/* 7965 */         check = "x";
/*      */       } else {
/* 7967 */         check = "";
/*      */       } 
/* 7969 */       supplierID = Integer.toString(bomCassetteDetail.oCardSupplierId);
/*      */       
/* 7971 */       supplier = MilestoneHelper.getSupplierById(supplierID);
/* 7972 */       supplierName = (supplier == null) ? "" : supplier.getName();
/*      */       
/* 7974 */       String bomOCardInk1 = String.valueOf(bomCassetteDetail.oCardInk1) + "/" + 
/* 7975 */         bomCassetteDetail.oCardInk2;
/*      */       
/* 7977 */       addInfoText = bomCassetteDetail.oCardInfo;
/*      */ 
/*      */       
/* 7980 */       table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 7981 */       table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 7982 */       table_contents.setRowBorderColor(nextRow, 8, Color.white);
/* 7983 */       table_contents.setRowBorderColor(nextRow, 10, Color.white);
/* 7984 */       table_contents.setRowBorderColor(nextRow, 11, Color.white);
/*      */       
/* 7986 */       table_contents.setFont(nextRow, 0, boldFont);
/* 7987 */       table_contents.setAlignment(nextRow, 0, 34);
/* 7988 */       table_contents.setObject(nextRow, 0, replaceQuote(check));
/*      */       
/* 7990 */       table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
/* 7991 */       table_contents.setFont(nextRow, 2, plainFont);
/* 7992 */       table_contents.setAlignment(nextRow, 2, 33);
/* 7993 */       table_contents.setObject(nextRow, 2, "O-Card");
/*      */       
/* 7995 */       table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 7996 */       table_contents.setAlignment(nextRow, 5, 33);
/* 7997 */       table_contents.setFont(nextRow, 5, plainFont);
/* 7998 */       table_contents.setObject(nextRow, 5, replaceQuote(supplierName));
/*      */       
/* 8000 */       table_contents.setFont(nextRow, 9, plainFont);
/* 8001 */       table_contents.setAlignment(nextRow, 9, 34);
/* 8002 */       table_contents.setObject(nextRow, 9, replaceQuote(bomOCardInk1));
/*      */       
/* 8004 */       table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
/* 8005 */       table_contents.setFont(nextRow, 12, plainFont);
/* 8006 */       table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
/* 8007 */       table_contents.setAlignment(nextRow, 12, 33);
/* 8008 */       nextRow++;
/*      */ 
/*      */       
/* 8011 */       check = "";
/*      */       
/* 8013 */       if (bomCassetteDetail.stickerOneCardStatusIndicator) {
/* 8014 */         check = "x";
/*      */       } else {
/* 8016 */         check = "";
/*      */       } 
/* 8018 */       supplierID = Integer.toString(bomCassetteDetail.stickerOneCardSupplierId);
/*      */       
/* 8020 */       supplier = MilestoneHelper.getSupplierById(supplierID);
/* 8021 */       supplierName = (supplier == null) ? "" : supplier.getName();
/*      */       
/* 8023 */       String bomSticker1Ink1 = String.valueOf(bomCassetteDetail.stickerOneCardInk1) + "/" + 
/* 8024 */         bomCassetteDetail.stickerOneCardInk2;
/*      */       
/* 8026 */       String sticker1Places = bomCassetteDetail.stickerOneCardPlaces;
/*      */       
/* 8028 */       sticker1Places = (sticker1Places == null || sticker1Places.trim().equals("")) ? 
/* 8029 */         "" : ("Places: " + sticker1Places + "  ");
/*      */       
/* 8031 */       addInfoText = String.valueOf(sticker1Places) + bomCassetteDetail.stickerOneCardInfo;
/*      */ 
/*      */       
/* 8034 */       table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 8035 */       table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 8036 */       table_contents.setRowBorderColor(nextRow, 8, Color.white);
/* 8037 */       table_contents.setRowBorderColor(nextRow, 10, Color.white);
/* 8038 */       table_contents.setRowBorderColor(nextRow, 11, Color.white);
/*      */       
/* 8040 */       table_contents.setFont(nextRow, 0, boldFont);
/* 8041 */       table_contents.setAlignment(nextRow, 0, 34);
/* 8042 */       table_contents.setObject(nextRow, 0, replaceQuote(check));
/*      */       
/* 8044 */       table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
/* 8045 */       table_contents.setFont(nextRow, 2, plainFont);
/* 8046 */       table_contents.setAlignment(nextRow, 2, 33);
/* 8047 */       table_contents.setObject(nextRow, 2, "Sticker1");
/*      */       
/* 8049 */       table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 8050 */       table_contents.setAlignment(nextRow, 5, 33);
/* 8051 */       table_contents.setFont(nextRow, 5, plainFont);
/* 8052 */       table_contents.setObject(nextRow, 5, replaceQuote(supplierName));
/*      */       
/* 8054 */       table_contents.setFont(nextRow, 9, plainFont);
/* 8055 */       table_contents.setAlignment(nextRow, 9, 34);
/* 8056 */       table_contents.setObject(nextRow, 9, replaceQuote(bomSticker1Ink1));
/*      */       
/* 8058 */       table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
/* 8059 */       table_contents.setFont(nextRow, 12, plainFont);
/* 8060 */       table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
/* 8061 */       table_contents.setAlignment(nextRow, 12, 33);
/* 8062 */       nextRow++;
/*      */ 
/*      */       
/* 8065 */       check = "";
/*      */       
/* 8067 */       if (bomCassetteDetail.stickerTwoCardStatusIndicator) {
/* 8068 */         check = "x";
/*      */       } else {
/* 8070 */         check = "";
/*      */       } 
/* 8072 */       supplierID = Integer.toString(bomCassetteDetail.stickerTwoCardSupplierId);
/*      */       
/* 8074 */       supplier = MilestoneHelper.getSupplierById(supplierID);
/* 8075 */       supplierName = (supplier == null) ? "" : supplier.getName();
/*      */       
/* 8077 */       String bomSticker2Ink1 = String.valueOf(bomCassetteDetail.stickerTwoCardInk1) + "/" + 
/* 8078 */         bomCassetteDetail.stickerTwoCardInk2;
/*      */       
/* 8080 */       String sticker2Places = bomCassetteDetail.stickerTwoCardPlaces;
/*      */       
/* 8082 */       sticker2Places = (sticker2Places == null || sticker2Places.trim().equals("")) ? 
/* 8083 */         "" : ("Places: " + sticker2Places + "  ");
/* 8084 */       addInfoText = String.valueOf(sticker2Places) + bomCassetteDetail.stickerTwoCardInfo;
/*      */ 
/*      */       
/* 8087 */       table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 8088 */       table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 8089 */       table_contents.setRowBorderColor(nextRow, 8, Color.white);
/* 8090 */       table_contents.setRowBorderColor(nextRow, 10, Color.white);
/* 8091 */       table_contents.setRowBorderColor(nextRow, 11, Color.white);
/*      */       
/* 8093 */       table_contents.setFont(nextRow, 0, boldFont);
/* 8094 */       table_contents.setAlignment(nextRow, 0, 34);
/* 8095 */       table_contents.setObject(nextRow, 0, replaceQuote(check));
/*      */       
/* 8097 */       table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
/* 8098 */       table_contents.setFont(nextRow, 2, plainFont);
/* 8099 */       table_contents.setAlignment(nextRow, 2, 33);
/* 8100 */       table_contents.setObject(nextRow, 2, "Sticker2");
/*      */       
/* 8102 */       table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 8103 */       table_contents.setAlignment(nextRow, 5, 33);
/* 8104 */       table_contents.setFont(nextRow, 5, plainFont);
/* 8105 */       table_contents.setObject(nextRow, 5, replaceQuote(supplierName));
/*      */       
/* 8107 */       table_contents.setFont(nextRow, 9, plainFont);
/* 8108 */       table_contents.setAlignment(nextRow, 9, 34);
/* 8109 */       table_contents.setObject(nextRow, 9, replaceQuote(bomSticker2Ink1));
/*      */       
/* 8111 */       table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
/* 8112 */       table_contents.setFont(nextRow, 12, plainFont);
/* 8113 */       table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
/* 8114 */       table_contents.setAlignment(nextRow, 12, 33);
/* 8115 */       nextRow++;
/*      */ 
/*      */       
/* 8118 */       check = "";
/*      */       
/* 8120 */       if (bomCassetteDetail.otherStatusIndicator) {
/* 8121 */         check = "x";
/*      */       } else {
/* 8123 */         check = "";
/*      */       } 
/* 8125 */       supplierID = Integer.toString(bomCassetteDetail.otherSupplierId);
/*      */       
/* 8127 */       supplier = MilestoneHelper.getSupplierById(supplierID);
/* 8128 */       supplierName = (supplier == null) ? "" : supplier.getName();
/*      */       
/* 8130 */       String bomOtherInk1 = String.valueOf(bomCassetteDetail.otherInk1) + "/" + 
/* 8131 */         bomCassetteDetail.otherInk2;
/*      */       
/* 8133 */       addInfoText = bomCassetteDetail.otherInfo;
/*      */ 
/*      */       
/* 8136 */       table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 8137 */       table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 8138 */       table_contents.setRowBorderColor(nextRow, 8, Color.white);
/* 8139 */       table_contents.setRowBorderColor(nextRow, 10, Color.white);
/* 8140 */       table_contents.setRowBorderColor(nextRow, 11, Color.white);
/*      */       
/* 8142 */       table_contents.setFont(nextRow, 0, boldFont);
/* 8143 */       table_contents.setAlignment(nextRow, 0, 34);
/* 8144 */       table_contents.setObject(nextRow, 0, replaceQuote(check));
/*      */       
/* 8146 */       table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
/* 8147 */       table_contents.setFont(nextRow, 2, plainFont);
/* 8148 */       table_contents.setAlignment(nextRow, 2, 33);
/* 8149 */       table_contents.setObject(nextRow, 2, "Other");
/*      */       
/* 8151 */       table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 8152 */       table_contents.setAlignment(nextRow, 5, 33);
/* 8153 */       table_contents.setFont(nextRow, 5, plainFont);
/* 8154 */       table_contents.setObject(nextRow, 5, replaceQuote(supplierName));
/*      */       
/* 8156 */       table_contents.setFont(nextRow, 9, plainFont);
/* 8157 */       table_contents.setAlignment(nextRow, 9, 34);
/* 8158 */       table_contents.setObject(nextRow, 9, replaceQuote(bomOtherInk1));
/*      */       
/* 8160 */       table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
/* 8161 */       table_contents.setFont(nextRow, 12, plainFont);
/* 8162 */       table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
/* 8163 */       table_contents.setAlignment(nextRow, 12, 33);
/* 8164 */       nextRow++;
/*      */ 
/*      */       
/* 8167 */       table_contents.setRowBorderColor(nextRow, Color.white);
/* 8168 */       table_contents.setRowHeight(nextRow, 10);
/* 8169 */       nextRow++;
/*      */ 
/*      */       
/* 8172 */       table_contents.setRowBorderColor(nextRow, Color.white);
/* 8173 */       table_contents.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
/* 8174 */       table_contents.setSpan(nextRow, 0, new Dimension(6, 1));
/* 8175 */       table_contents.setFont(nextRow, 0, new Font("Arial", 1, 12));
/* 8176 */       table_contents.setAlignment(nextRow, 0, 9);
/* 8177 */       table_contents.setObject(nextRow, 0, "Special Instructions:");
/*      */       
/* 8179 */       table_contents.setSpan(nextRow, 6, new Dimension(14, 1));
/* 8180 */       table_contents.setFont(nextRow, 6, new Font("Arial", 0, 11));
/* 8181 */       table_contents.setAlignment(nextRow, 6, 9);
/*      */       
/* 8183 */       String specialInstructionsString = "";
/*      */       
/* 8185 */       if (bom.getSpecialInstructions().length() > 0) {
/* 8186 */         specialInstructionsString = bom.getSpecialInstructions();
/*      */       }
/* 8188 */       table_contents.setObject(nextRow, 6, String.valueOf(replaceQuote(specialInstructionsString)) + "\n");
/*      */       
/* 8190 */       report.setElement("table_colheaders", table_contents);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addVinylReportElements(XStyleSheet report, Bom bom, Context context, DefaultTableLens table_contents, Selection mySelection) {
/* 8206 */     if (bom != null) {
/*      */       
/* 8208 */       BomVinylDetail bomVinylDetail = bom.getBomVinylDetail();
/*      */       
/* 8210 */       table_contents.setRowInsets(10, new Insets(1, 0, 0, 0));
/* 8211 */       table_contents.setRowInsets(11, new Insets(1, 0, 0, 0));
/* 8212 */       table_contents.setRowInsets(12, new Insets(1, 0, 0, 0));
/* 8213 */       table_contents.setRowInsets(13, new Insets(1, 0, 0, 0));
/* 8214 */       table_contents.setRowInsets(14, new Insets(1, 0, 0, 0));
/* 8215 */       table_contents.setRowInsets(15, new Insets(1, 0, 0, 0));
/* 8216 */       table_contents.setRowInsets(16, new Insets(1, 0, 0, 0));
/* 8217 */       table_contents.setRowInsets(17, new Insets(1, 0, 0, 0));
/* 8218 */       table_contents.setRowInsets(18, new Insets(1, 0, 0, 0));
/* 8219 */       table_contents.setRowInsets(19, new Insets(1, 0, 0, 0));
/* 8220 */       table_contents.setRowInsets(20, new Insets(1, 0, 0, 0));
/* 8221 */       table_contents.setRowInsets(21, new Insets(1, 0, 0, 0));
/* 8222 */       table_contents.setRowInsets(22, new Insets(1, 0, 0, 0));
/* 8223 */       table_contents.setRowInsets(23, new Insets(1, 0, 0, 0));
/* 8224 */       table_contents.setRowInsets(24, new Insets(1, 0, 0, 0));
/* 8225 */       table_contents.setRowInsets(25, new Insets(1, 0, 0, 0));
/* 8226 */       table_contents.setRowInsets(26, new Insets(1, 0, 0, 0));
/* 8227 */       table_contents.setRowInsets(27, new Insets(1, 0, 0, 0));
/* 8228 */       table_contents.setRowInsets(28, new Insets(1, 0, 0, 0));
/* 8229 */       table_contents.setRowInsets(29, new Insets(1, 0, 0, 0));
/* 8230 */       table_contents.setRowInsets(30, new Insets(1, 0, 0, 0));
/*      */       
/* 8232 */       int nextRow = 12;
/* 8233 */       Font boldFont = new Font("Arial", 1, 10);
/* 8234 */       Font plainFont = new Font("Arial", 0, 9);
/*      */ 
/*      */       
/* 8237 */       String stringUnitsPerPkg = "";
/*      */       
/* 8239 */       if (mySelection.getNumberOfUnits() > 0 && 
/* 8240 */         !Integer.toString(mySelection.getNumberOfUnits()).equals(""))
/*      */       {
/* 8242 */         stringUnitsPerPkg = Integer.toString(mySelection.getNumberOfUnits());
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 8247 */       String noShrinkwrap = context.getRequestValue("UseNoShrinkWrap");
/* 8248 */       if (bom.useShrinkWrap()) {
/* 8249 */         noShrinkwrap = "X";
/*      */       } else {
/* 8251 */         noShrinkwrap = "";
/*      */       } 
/*      */       
/* 8254 */       table_contents.setRowInsets(nextRow, new Insets(1, 0, 0, 0));
/* 8255 */       table_contents.setRowBorderColor(nextRow, 0, Color.white);
/* 8256 */       table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 8257 */       table_contents.setRowBorderColor(nextRow, 2, Color.white);
/* 8258 */       table_contents.setRowBorderColor(nextRow, 3, Color.white);
/* 8259 */       table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 8260 */       table_contents.setRowBorderColor(nextRow, 5, Color.white);
/* 8261 */       table_contents.setRowBorderColor(nextRow, 6, Color.white);
/* 8262 */       table_contents.setRowBorderColor(nextRow, 7, Color.white);
/* 8263 */       table_contents.setRowBorderColor(nextRow, 8, Color.white);
/* 8264 */       table_contents.setRowBorderColor(nextRow, 9, Color.white);
/* 8265 */       table_contents.setRowBorderColor(nextRow, 12, Color.white);
/* 8266 */       table_contents.setRowBorderColor(nextRow, 13, Color.white);
/* 8267 */       table_contents.setRowBorderColor(nextRow, 14, Color.white);
/* 8268 */       table_contents.setRowBorderColor(nextRow, 16, Color.white);
/* 8269 */       table_contents.setRowBorderColor(nextRow, 17, Color.white);
/*      */ 
/*      */       
/* 8272 */       table_contents.setSpan(nextRow, 0, new Dimension(7, 1));
/* 8273 */       table_contents.setFont(nextRow, 0, boldFont);
/* 8274 */       table_contents.setAlignment(nextRow, 0, 33);
/* 8275 */       table_contents.setObject(nextRow, 0, "Vinyl");
/*      */ 
/*      */ 
/*      */       
/* 8279 */       table_contents.setSpan(nextRow, 7, new Dimension(3, 1));
/* 8280 */       table_contents.setFont(nextRow, 7, boldFont);
/* 8281 */       table_contents.setAlignment(nextRow, 7, 36);
/* 8282 */       table_contents.setObject(nextRow, 7, "# of Units:");
/* 8283 */       table_contents.setSpan(nextRow, 10, new Dimension(2, 1));
/* 8284 */       table_contents.setAlignment(nextRow, 10, 34);
/* 8285 */       table_contents.setFont(nextRow, 10, plainFont);
/* 8286 */       table_contents.setObject(nextRow, 10, replaceQuote(stringUnitsPerPkg));
/*      */ 
/*      */       
/* 8289 */       String configuration = "";
/* 8290 */       if (bom.getConfiguration() != null)
/*      */       {
/* 8292 */         configuration = bom.getConfiguration();
/*      */       }
/*      */       
/* 8295 */       table_contents.setSpan(nextRow, 13, new Dimension(2, 1));
/* 8296 */       table_contents.setFont(nextRow, 13, boldFont);
/* 8297 */       table_contents.setAlignment(nextRow, 13, 36);
/* 8298 */       table_contents.setObject(nextRow, 13, "Configuration:");
/* 8299 */       table_contents.setFont(nextRow, 15, new Font("Arial", 0, 10));
/* 8300 */       table_contents.setObject(nextRow, 15, replaceQuote(configuration));
/* 8301 */       table_contents.setAlignment(nextRow, 15, 34);
/*      */ 
/*      */ 
/*      */       
/* 8305 */       table_contents.setSpan(nextRow, 16, new Dimension(2, 1));
/* 8306 */       table_contents.setFont(nextRow, 16, boldFont);
/* 8307 */       table_contents.setAlignment(nextRow, 16, 36);
/* 8308 */       table_contents.setObject(nextRow, 16, "Shrink Wrap:");
/* 8309 */       table_contents.setSpan(nextRow, 18, new Dimension(2, 1));
/* 8310 */       table_contents.setFont(nextRow, 18, new Font("Arial", 0, 10));
/* 8311 */       table_contents.setObject(nextRow, 18, replaceQuote(noShrinkwrap));
/* 8312 */       table_contents.setAlignment(nextRow, 18, 34);
/* 8313 */       nextRow++;
/*      */ 
/*      */       
/* 8316 */       table_contents.setRowHeight(nextRow, 5);
/* 8317 */       table_contents.setRowBorderColor(nextRow, Color.black);
/* 8318 */       nextRow++;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 8323 */       table_contents.setRowInsets(nextRow, new Insets(1, 0, 0, 0));
/*      */ 
/*      */       
/* 8326 */       table_contents.setRowBorderColor(nextRow, Color.white);
/*      */       
/* 8328 */       table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
/* 8329 */       table_contents.setFont(nextRow, 2, boldFont);
/* 8330 */       table_contents.setAlignment(nextRow, 2, 33);
/* 8331 */       table_contents.setObject(nextRow, 2, "Part");
/*      */       
/* 8333 */       table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 8334 */       table_contents.setAlignment(nextRow, 5, 33);
/* 8335 */       table_contents.setFont(nextRow, 5, boldFont);
/* 8336 */       table_contents.setObject(nextRow, 5, "Supplier");
/*      */       
/* 8338 */       table_contents.setFont(nextRow, 9, boldFont);
/* 8339 */       table_contents.setAlignment(nextRow, 9, 34);
/* 8340 */       table_contents.setObject(nextRow, 9, "Ink");
/*      */       
/* 8342 */       table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
/* 8343 */       table_contents.setFont(nextRow, 12, boldFont);
/* 8344 */       table_contents.setObject(nextRow, 12, "Additional Information");
/* 8345 */       table_contents.setAlignment(nextRow, 12, 33);
/* 8346 */       nextRow++;
/*      */ 
/*      */       
/* 8349 */       String check = "";
/*      */       
/* 8351 */       if (bomVinylDetail.recordStatusIndicator) {
/* 8352 */         check = "x";
/*      */       } else {
/* 8354 */         check = "";
/*      */       } 
/* 8356 */       String supplierID = Integer.toString(bomVinylDetail.recordSupplierId);
/*      */       
/* 8358 */       LookupObject supplier = MilestoneHelper.getSupplierById(supplierID);
/* 8359 */       String supplierName = (supplier == null) ? "" : supplier.getName();
/*      */       
/* 8361 */       String recordColor = bomVinylDetail.recordColor;
/*      */       
/* 8363 */       recordColor = (recordColor == null || recordColor.length() <= 1) ? 
/* 8364 */         "" : ("Color: " + recordColor + "  ");
/*      */       
/* 8366 */       String addInfoText = String.valueOf(recordColor) + bomVinylDetail.recordInfo;
/*      */ 
/*      */       
/* 8369 */       table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 8370 */       table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 8371 */       table_contents.setRowBorderColor(nextRow, 8, Color.white);
/* 8372 */       table_contents.setRowBorderColor(nextRow, 10, Color.white);
/* 8373 */       table_contents.setRowBorderColor(nextRow, 11, Color.white);
/*      */       
/* 8375 */       table_contents.setFont(nextRow, 0, boldFont);
/* 8376 */       table_contents.setAlignment(nextRow, 0, 34);
/* 8377 */       table_contents.setObject(nextRow, 0, replaceQuote(check));
/*      */       
/* 8379 */       table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
/* 8380 */       table_contents.setFont(nextRow, 2, plainFont);
/* 8381 */       table_contents.setAlignment(nextRow, 2, 33);
/* 8382 */       table_contents.setObject(nextRow, 2, "Record");
/*      */       
/* 8384 */       table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 8385 */       table_contents.setAlignment(nextRow, 5, 33);
/* 8386 */       table_contents.setFont(nextRow, 5, plainFont);
/* 8387 */       table_contents.setObject(nextRow, 5, replaceQuote(supplierName));
/*      */       
/* 8389 */       table_contents.setFont(nextRow, 9, plainFont);
/* 8390 */       table_contents.setAlignment(nextRow, 9, 34);
/* 8391 */       table_contents.setObject(nextRow, 9, "/");
/*      */       
/* 8393 */       table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
/* 8394 */       table_contents.setFont(nextRow, 12, plainFont);
/* 8395 */       table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
/* 8396 */       table_contents.setAlignment(nextRow, 12, 33);
/* 8397 */       nextRow++;
/*      */ 
/*      */       
/* 8400 */       check = "";
/*      */       
/* 8402 */       if (bomVinylDetail.labelStatusIndicator) {
/* 8403 */         check = "x";
/*      */       } else {
/* 8405 */         check = "";
/*      */       } 
/* 8407 */       supplierID = Integer.toString(bomVinylDetail.labelSupplierId);
/*      */       
/* 8409 */       supplier = MilestoneHelper.getSupplierById(supplierID);
/* 8410 */       supplierName = (supplier == null) ? "" : supplier.getName();
/*      */       
/* 8412 */       String bomLabelInk1 = String.valueOf(bomVinylDetail.labelInk1) + "/" + 
/* 8413 */         bomVinylDetail.labelInk2;
/*      */       
/* 8415 */       addInfoText = bomVinylDetail.labelInfo;
/*      */ 
/*      */       
/* 8418 */       table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 8419 */       table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 8420 */       table_contents.setRowBorderColor(nextRow, 8, Color.white);
/* 8421 */       table_contents.setRowBorderColor(nextRow, 10, Color.white);
/* 8422 */       table_contents.setRowBorderColor(nextRow, 11, Color.white);
/*      */       
/* 8424 */       table_contents.setFont(nextRow, 0, boldFont);
/* 8425 */       table_contents.setAlignment(nextRow, 0, 34);
/* 8426 */       table_contents.setObject(nextRow, 0, replaceQuote(check));
/*      */       
/* 8428 */       table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
/* 8429 */       table_contents.setFont(nextRow, 2, plainFont);
/* 8430 */       table_contents.setAlignment(nextRow, 2, 33);
/* 8431 */       table_contents.setObject(nextRow, 2, "Label");
/*      */       
/* 8433 */       table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 8434 */       table_contents.setAlignment(nextRow, 5, 33);
/* 8435 */       table_contents.setFont(nextRow, 5, plainFont);
/* 8436 */       table_contents.setObject(nextRow, 5, replaceQuote(supplierName));
/*      */       
/* 8438 */       table_contents.setFont(nextRow, 9, plainFont);
/* 8439 */       table_contents.setAlignment(nextRow, 9, 34);
/* 8440 */       table_contents.setObject(nextRow, 9, replaceQuote(bomLabelInk1));
/*      */       
/* 8442 */       table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
/* 8443 */       table_contents.setFont(nextRow, 12, plainFont);
/* 8444 */       table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
/* 8445 */       table_contents.setAlignment(nextRow, 12, 33);
/* 8446 */       nextRow++;
/*      */ 
/*      */       
/* 8449 */       check = "";
/*      */       
/* 8451 */       if (bomVinylDetail.sleeveStatusIndicator) {
/* 8452 */         check = "x";
/*      */       } else {
/* 8454 */         check = "";
/*      */       } 
/* 8456 */       supplierID = Integer.toString(bomVinylDetail.sleeveSupplierId);
/*      */       
/* 8458 */       supplier = MilestoneHelper.getSupplierById(supplierID);
/* 8459 */       supplierName = (supplier == null) ? "" : supplier.getName();
/*      */       
/* 8461 */       String bomSleveInk1 = String.valueOf(bomVinylDetail.sleeveInk1) + "/" + 
/* 8462 */         bomVinylDetail.sleeveInk2;
/*      */       
/* 8464 */       addInfoText = bomVinylDetail.sleeveInfo;
/*      */ 
/*      */       
/* 8467 */       table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 8468 */       table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 8469 */       table_contents.setRowBorderColor(nextRow, 8, Color.white);
/* 8470 */       table_contents.setRowBorderColor(nextRow, 10, Color.white);
/* 8471 */       table_contents.setRowBorderColor(nextRow, 11, Color.white);
/*      */       
/* 8473 */       table_contents.setFont(nextRow, 0, boldFont);
/* 8474 */       table_contents.setAlignment(nextRow, 0, 34);
/* 8475 */       table_contents.setObject(nextRow, 0, replaceQuote(check));
/*      */       
/* 8477 */       table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
/* 8478 */       table_contents.setFont(nextRow, 2, plainFont);
/* 8479 */       table_contents.setAlignment(nextRow, 2, 33);
/* 8480 */       table_contents.setObject(nextRow, 2, "Sleeve");
/*      */       
/* 8482 */       table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 8483 */       table_contents.setAlignment(nextRow, 5, 33);
/* 8484 */       table_contents.setFont(nextRow, 5, plainFont);
/* 8485 */       table_contents.setObject(nextRow, 5, replaceQuote(supplierName));
/*      */       
/* 8487 */       table_contents.setFont(nextRow, 9, plainFont);
/* 8488 */       table_contents.setAlignment(nextRow, 9, 34);
/* 8489 */       table_contents.setObject(nextRow, 9, replaceQuote(bomSleveInk1));
/*      */       
/* 8491 */       table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
/* 8492 */       table_contents.setFont(nextRow, 12, plainFont);
/* 8493 */       table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
/* 8494 */       table_contents.setAlignment(nextRow, 12, 33);
/* 8495 */       nextRow++;
/*      */ 
/*      */       
/* 8498 */       check = "";
/*      */       
/* 8500 */       if (bomVinylDetail.jacketStatusIndicator) {
/* 8501 */         check = "x";
/*      */       } else {
/* 8503 */         check = "";
/*      */       } 
/* 8505 */       supplierID = Integer.toString(bomVinylDetail.jacketSupplierId);
/*      */       
/* 8507 */       supplier = MilestoneHelper.getSupplierById(supplierID);
/* 8508 */       supplierName = (supplier == null) ? "" : supplier.getName();
/*      */       
/* 8510 */       String bomJacketInk1 = String.valueOf(bomVinylDetail.jacketInk1) + "/" + 
/* 8511 */         bomVinylDetail.jacketInk2;
/*      */       
/* 8513 */       addInfoText = bomVinylDetail.jacketInfo;
/*      */ 
/*      */       
/* 8516 */       table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 8517 */       table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 8518 */       table_contents.setRowBorderColor(nextRow, 8, Color.white);
/* 8519 */       table_contents.setRowBorderColor(nextRow, 10, Color.white);
/* 8520 */       table_contents.setRowBorderColor(nextRow, 11, Color.white);
/*      */       
/* 8522 */       table_contents.setFont(nextRow, 0, boldFont);
/* 8523 */       table_contents.setAlignment(nextRow, 0, 34);
/* 8524 */       table_contents.setObject(nextRow, 0, replaceQuote(check));
/*      */       
/* 8526 */       table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
/* 8527 */       table_contents.setFont(nextRow, 2, plainFont);
/* 8528 */       table_contents.setAlignment(nextRow, 2, 33);
/* 8529 */       table_contents.setObject(nextRow, 2, "Jacket");
/*      */       
/* 8531 */       table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 8532 */       table_contents.setAlignment(nextRow, 5, 33);
/* 8533 */       table_contents.setFont(nextRow, 5, plainFont);
/* 8534 */       table_contents.setObject(nextRow, 5, replaceQuote(supplierName));
/*      */       
/* 8536 */       table_contents.setFont(nextRow, 9, plainFont);
/* 8537 */       table_contents.setAlignment(nextRow, 9, 34);
/* 8538 */       table_contents.setObject(nextRow, 9, replaceQuote(bomJacketInk1));
/*      */       
/* 8540 */       table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
/* 8541 */       table_contents.setFont(nextRow, 12, plainFont);
/* 8542 */       table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
/* 8543 */       table_contents.setAlignment(nextRow, 12, 33);
/* 8544 */       nextRow++;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 8550 */       check = "";
/*      */       
/* 8552 */       if (bomVinylDetail.insertStatusIndicator) {
/* 8553 */         check = "x";
/*      */       } else {
/* 8555 */         check = "";
/*      */       } 
/* 8557 */       supplierID = Integer.toString(bomVinylDetail.insertSupplierId);
/*      */       
/* 8559 */       supplier = MilestoneHelper.getSupplierById(supplierID);
/* 8560 */       supplierName = (supplier == null) ? "" : supplier.getName();
/*      */       
/* 8562 */       String bomInsertInk1 = String.valueOf(bomVinylDetail.insertInk1) + "/" + 
/* 8563 */         bomVinylDetail.insertInk2;
/*      */       
/* 8565 */       addInfoText = bomVinylDetail.insertInfo;
/*      */ 
/*      */       
/* 8568 */       table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 8569 */       table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 8570 */       table_contents.setRowBorderColor(nextRow, 8, Color.white);
/* 8571 */       table_contents.setRowBorderColor(nextRow, 10, Color.white);
/* 8572 */       table_contents.setRowBorderColor(nextRow, 11, Color.white);
/*      */       
/* 8574 */       table_contents.setFont(nextRow, 0, boldFont);
/* 8575 */       table_contents.setAlignment(nextRow, 0, 34);
/* 8576 */       table_contents.setObject(nextRow, 0, replaceQuote(check));
/*      */       
/* 8578 */       table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
/* 8579 */       table_contents.setFont(nextRow, 2, plainFont);
/* 8580 */       table_contents.setAlignment(nextRow, 2, 33);
/* 8581 */       table_contents.setObject(nextRow, 2, "Insert");
/*      */       
/* 8583 */       table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 8584 */       table_contents.setAlignment(nextRow, 5, 33);
/* 8585 */       table_contents.setFont(nextRow, 5, plainFont);
/* 8586 */       table_contents.setObject(nextRow, 5, replaceQuote(supplierName));
/*      */       
/* 8588 */       table_contents.setFont(nextRow, 9, plainFont);
/* 8589 */       table_contents.setAlignment(nextRow, 9, 34);
/* 8590 */       table_contents.setObject(nextRow, 9, replaceQuote(bomInsertInk1));
/*      */       
/* 8592 */       table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
/* 8593 */       table_contents.setFont(nextRow, 12, plainFont);
/* 8594 */       table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
/* 8595 */       table_contents.setAlignment(nextRow, 12, 33);
/* 8596 */       nextRow++;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 8604 */       check = "";
/*      */       
/* 8606 */       if (bomVinylDetail.stickerOneStatusIndicator) {
/* 8607 */         check = "x";
/*      */       } else {
/* 8609 */         check = "";
/*      */       } 
/* 8611 */       supplierID = Integer.toString(bomVinylDetail.stickerOneSupplierId);
/*      */       
/* 8613 */       supplier = MilestoneHelper.getSupplierById(supplierID);
/* 8614 */       supplierName = (supplier == null) ? "" : supplier.getName();
/*      */       
/* 8616 */       String bomSticker1Ink1 = String.valueOf(bomVinylDetail.stickerOneInk1) + "/" + 
/* 8617 */         bomVinylDetail.stickerOneInk2;
/*      */       
/* 8619 */       String sticker1Places = bomVinylDetail.stickerOnePlaces;
/*      */       
/* 8621 */       sticker1Places = (sticker1Places == null || sticker1Places.trim().equals("")) ? 
/* 8622 */         "" : ("Places: " + sticker1Places + "  ");
/*      */       
/* 8624 */       addInfoText = String.valueOf(sticker1Places) + bomVinylDetail.stickerOneInfo;
/*      */ 
/*      */       
/* 8627 */       table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 8628 */       table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 8629 */       table_contents.setRowBorderColor(nextRow, 8, Color.white);
/* 8630 */       table_contents.setRowBorderColor(nextRow, 10, Color.white);
/* 8631 */       table_contents.setRowBorderColor(nextRow, 11, Color.white);
/*      */       
/* 8633 */       table_contents.setFont(nextRow, 0, boldFont);
/* 8634 */       table_contents.setAlignment(nextRow, 0, 34);
/* 8635 */       table_contents.setObject(nextRow, 0, replaceQuote(check));
/*      */       
/* 8637 */       table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
/* 8638 */       table_contents.setFont(nextRow, 2, plainFont);
/* 8639 */       table_contents.setAlignment(nextRow, 2, 33);
/* 8640 */       table_contents.setObject(nextRow, 2, "Sticker1");
/*      */       
/* 8642 */       table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 8643 */       table_contents.setAlignment(nextRow, 5, 33);
/* 8644 */       table_contents.setFont(nextRow, 5, plainFont);
/* 8645 */       table_contents.setObject(nextRow, 5, replaceQuote(supplierName));
/*      */       
/* 8647 */       table_contents.setFont(nextRow, 9, plainFont);
/* 8648 */       table_contents.setAlignment(nextRow, 9, 34);
/* 8649 */       table_contents.setObject(nextRow, 9, replaceQuote(bomSticker1Ink1));
/*      */       
/* 8651 */       table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
/* 8652 */       table_contents.setFont(nextRow, 12, plainFont);
/* 8653 */       table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
/* 8654 */       table_contents.setAlignment(nextRow, 12, 33);
/* 8655 */       nextRow++;
/*      */ 
/*      */       
/* 8658 */       check = "";
/*      */       
/* 8660 */       if (bomVinylDetail.stickerTwoStatusIndicator) {
/* 8661 */         check = "x";
/*      */       } else {
/* 8663 */         check = "";
/*      */       } 
/* 8665 */       supplierID = Integer.toString(bomVinylDetail.stickerTwoSupplierId);
/*      */       
/* 8667 */       supplier = MilestoneHelper.getSupplierById(supplierID);
/* 8668 */       supplierName = (supplier == null) ? "" : supplier.getName();
/*      */       
/* 8670 */       String bomSticker2Ink1 = String.valueOf(bomVinylDetail.stickerTwoInk1) + "/" + 
/* 8671 */         bomVinylDetail.stickerTwoInk2;
/*      */       
/* 8673 */       String sticker2Places = bomVinylDetail.stickerTwoPlaces;
/*      */       
/* 8675 */       sticker2Places = (sticker2Places == null || sticker2Places.trim().equals("")) ? 
/* 8676 */         "" : ("Places: " + sticker2Places + "  ");
/*      */       
/* 8678 */       addInfoText = String.valueOf(sticker2Places) + bomVinylDetail.stickerTwoInfo;
/*      */ 
/*      */       
/* 8681 */       table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 8682 */       table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 8683 */       table_contents.setRowBorderColor(nextRow, 8, Color.white);
/* 8684 */       table_contents.setRowBorderColor(nextRow, 10, Color.white);
/* 8685 */       table_contents.setRowBorderColor(nextRow, 11, Color.white);
/*      */       
/* 8687 */       table_contents.setFont(nextRow, 0, boldFont);
/* 8688 */       table_contents.setAlignment(nextRow, 0, 34);
/* 8689 */       table_contents.setObject(nextRow, 0, replaceQuote(check));
/*      */       
/* 8691 */       table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
/* 8692 */       table_contents.setFont(nextRow, 2, plainFont);
/* 8693 */       table_contents.setAlignment(nextRow, 2, 33);
/* 8694 */       table_contents.setObject(nextRow, 2, "Sticker2");
/*      */       
/* 8696 */       table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 8697 */       table_contents.setAlignment(nextRow, 5, 33);
/* 8698 */       table_contents.setFont(nextRow, 5, plainFont);
/* 8699 */       table_contents.setObject(nextRow, 5, replaceQuote(supplierName));
/*      */       
/* 8701 */       table_contents.setFont(nextRow, 9, plainFont);
/* 8702 */       table_contents.setAlignment(nextRow, 9, 34);
/* 8703 */       table_contents.setObject(nextRow, 9, replaceQuote(bomSticker2Ink1));
/*      */       
/* 8705 */       table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
/* 8706 */       table_contents.setFont(nextRow, 12, plainFont);
/* 8707 */       table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
/* 8708 */       table_contents.setAlignment(nextRow, 12, 33);
/* 8709 */       nextRow++;
/*      */ 
/*      */       
/* 8712 */       check = "";
/*      */       
/* 8714 */       if (bomVinylDetail.otherStatusIndicator) {
/* 8715 */         check = "x";
/*      */       } else {
/* 8717 */         check = "";
/*      */       } 
/* 8719 */       supplierID = Integer.toString(bomVinylDetail.otherSupplierId);
/*      */       
/* 8721 */       supplier = MilestoneHelper.getSupplierById(supplierID);
/* 8722 */       supplierName = (supplier == null) ? "" : supplier.getName();
/*      */       
/* 8724 */       String bomOtherInk1 = String.valueOf(bomVinylDetail.otherInk1) + "/" + 
/* 8725 */         bomVinylDetail.otherInk2;
/*      */       
/* 8727 */       addInfoText = bomVinylDetail.otherInfo;
/*      */ 
/*      */       
/* 8730 */       table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 8731 */       table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 8732 */       table_contents.setRowBorderColor(nextRow, 8, Color.white);
/* 8733 */       table_contents.setRowBorderColor(nextRow, 10, Color.white);
/* 8734 */       table_contents.setRowBorderColor(nextRow, 11, Color.white);
/*      */       
/* 8736 */       table_contents.setFont(nextRow, 0, boldFont);
/* 8737 */       table_contents.setAlignment(nextRow, 0, 34);
/* 8738 */       table_contents.setObject(nextRow, 0, replaceQuote(check));
/*      */       
/* 8740 */       table_contents.setSpan(nextRow, 2, new Dimension(2, 1));
/* 8741 */       table_contents.setFont(nextRow, 2, plainFont);
/* 8742 */       table_contents.setAlignment(nextRow, 2, 33);
/* 8743 */       table_contents.setObject(nextRow, 2, "Other");
/*      */       
/* 8745 */       table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 8746 */       table_contents.setAlignment(nextRow, 5, 33);
/* 8747 */       table_contents.setFont(nextRow, 5, plainFont);
/* 8748 */       table_contents.setObject(nextRow, 5, replaceQuote(supplierName));
/*      */       
/* 8750 */       table_contents.setFont(nextRow, 9, plainFont);
/* 8751 */       table_contents.setAlignment(nextRow, 9, 34);
/* 8752 */       table_contents.setObject(nextRow, 9, replaceQuote(bomOtherInk1));
/*      */       
/* 8754 */       table_contents.setSpan(nextRow, 12, new Dimension(8, 1));
/* 8755 */       table_contents.setFont(nextRow, 12, plainFont);
/* 8756 */       table_contents.setObject(nextRow, 12, replaceQuote(addInfoText));
/* 8757 */       table_contents.setAlignment(nextRow, 12, 33);
/* 8758 */       nextRow++;
/*      */ 
/*      */       
/* 8761 */       table_contents.setRowBorderColor(nextRow, Color.white);
/* 8762 */       table_contents.setRowHeight(nextRow, 10);
/* 8763 */       nextRow++;
/*      */ 
/*      */       
/* 8766 */       table_contents.setRowBorderColor(nextRow, Color.white);
/* 8767 */       table_contents.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
/* 8768 */       table_contents.setSpan(nextRow, 0, new Dimension(6, 1));
/* 8769 */       table_contents.setFont(nextRow, 0, new Font("Arial", 1, 12));
/* 8770 */       table_contents.setAlignment(nextRow, 0, 9);
/* 8771 */       table_contents.setObject(nextRow, 0, "Special Instructions:");
/*      */       
/* 8773 */       table_contents.setSpan(nextRow, 6, new Dimension(14, 1));
/* 8774 */       table_contents.setFont(nextRow, 6, new Font("Arial", 0, 11));
/* 8775 */       table_contents.setAlignment(nextRow, 6, 9);
/*      */       
/* 8777 */       String specialInstructionsString = "";
/*      */       
/* 8779 */       if (bom.getSpecialInstructions().length() > 0) {
/* 8780 */         specialInstructionsString = bom.getSpecialInstructions();
/*      */       }
/* 8782 */       table_contents.setObject(nextRow, 6, String.valueOf(replaceQuote(specialInstructionsString)) + "\n");
/*      */       
/* 8784 */       report.setElement("table_colheaders", table_contents);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setTextBoxBottomBorder(XStyleSheet report, String key) {
/*      */     try {
/* 8801 */       TextBoxElement textBoxElement = (TextBoxElement)report.getElement(key);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 8807 */     catch (Exception exception) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Form addSelectionSearchElements(Context context, Selection selection, Form form) {
/* 8820 */     context.putDelivery("selectionArrays", String.valueOf(Cache.getJavaScriptConfigArray("")) + Cache.getJavaScriptSubConfigArray("") + " " + ReleasingFamily.getJavaScriptCorporateArrayReleasingFamilySearch(context));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 8826 */     FormCheckBox showAllSearch = new FormCheckBox("ShowAllSearch", "", "", false);
/* 8827 */     showAllSearch.setId("ShowAllSearch");
/* 8828 */     form.addElement(showAllSearch);
/*      */ 
/*      */     
/* 8831 */     Vector companies = MilestoneHelper.getUserCompanies(context);
/* 8832 */     Vector labels = MilestoneHelper.getUserLabels(companies);
/*      */ 
/*      */     
/* 8835 */     labels = MilestoneHelper.removeUnusedCSO(labels, context, -1);
/*      */     
/* 8837 */     FormDropDownMenu labelSearch = MilestoneHelper.getCorporateStructureDropDownDuplicates("LabelSearch", labels, "", false, true, false);
/* 8838 */     labelSearch.setId("LabelSearch");
/* 8839 */     form.addElement(labelSearch);
/*      */ 
/*      */     
/* 8842 */     Vector searchCompanies = null;
/*      */ 
/*      */ 
/*      */     
/* 8846 */     searchCompanies = MilestoneHelper.getUserCompanies(context);
/*      */ 
/*      */     
/* 8849 */     searchCompanies = MilestoneHelper.removeUnusedCSO(searchCompanies, context, -1);
/*      */     
/* 8851 */     FormDropDownMenu companySearch = MilestoneHelper.getCorporateStructureDropDown("CompanySearch", searchCompanies, "", false, true);
/* 8852 */     companySearch.setId("CompanySearch");
/* 8853 */     companySearch.addFormEvent("onChange", "return(clickCompanySearch(this))");
/* 8854 */     form.addElement(companySearch);
/*      */ 
/*      */     
/* 8857 */     Vector labelContacts = SelectionManager.getLabelContactsExcludeUml(context);
/* 8858 */     FormDropDownMenu searchContact = MilestoneHelper.getContactsDropDown(context, "ContactSearch", labelContacts, null, true);
/* 8859 */     form.addElement(searchContact);
/*      */ 
/*      */     
/* 8862 */     Vector families = SelectionHandler.filterCSO(ReleasingFamily.getUserReleasingFamiliesVectorOfFamilies(context));
/* 8863 */     FormDropDownMenu Family = MilestoneHelper.getCorporateStructureDropDown("FamilySearch", families, "-1", false, true);
/* 8864 */     Family.addFormEvent("onChange", "return(clickFamilySearch(this))");
/* 8865 */     Family.setId("FamilySearch");
/* 8866 */     form.addElement(Family);
/*      */ 
/*      */     
/* 8869 */     Vector environments = MilestoneHelper.getUserEnvironments(context);
/* 8870 */     Vector myCompanies = MilestoneHelper.getUserCompanies(context);
/* 8871 */     environments = SelectionHandler.filterSelectionEnvironments(myCompanies);
/*      */ 
/*      */     
/* 8874 */     environments = MilestoneHelper.removeUnusedCSO(environments, context, -1);
/*      */ 
/*      */     
/* 8877 */     FormDropDownMenu envMenu = MilestoneHelper.getCorporateStructureDropDown("EnvironmentSearch", environments, "-1", false, true);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 8882 */     envMenu.addFormEvent("onChange", "return(clickEnvironmentSearch(this))");
/* 8883 */     envMenu.setId("EnvironmentSearch");
/* 8884 */     form.addElement(envMenu);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 8889 */     FormTextField streetDateSearch = new FormTextField("StreetDateSearch", "", false, 14, 10);
/* 8890 */     streetDateSearch.addFormEvent("onBlur", "JavaScript:removeSpacesInDate(document.forms[0].StreetDateSearch.value,this)");
/* 8891 */     streetDateSearch.setId("StreetDateSearch");
/* 8892 */     form.addElement(streetDateSearch);
/*      */ 
/*      */     
/* 8895 */     FormTextField streetEndDateSearch = new FormTextField("StreetEndDateSearch", "", false, 14, 10);
/* 8896 */     streetEndDateSearch.addFormEvent("onBlur", "JavaScript:removeSpacesInDate(document.forms[0].StreetEndDateSearch.value,this)");
/* 8897 */     streetEndDateSearch.setId("StreetEndDateSearch");
/* 8898 */     form.addElement(streetEndDateSearch);
/*      */ 
/*      */     
/* 8901 */     String[] dvalues = new String[3];
/* 8902 */     dvalues[0] = "physical";
/* 8903 */     dvalues[1] = "digital";
/* 8904 */     dvalues[2] = "both";
/*      */     
/* 8906 */     String[] dlabels = new String[3];
/* 8907 */     dlabels[0] = "Physical";
/* 8908 */     dlabels[1] = "Digital";
/* 8909 */     dlabels[2] = "Both";
/*      */     
/* 8911 */     FormRadioButtonGroup prodType = new FormRadioButtonGroup("ProdType", "both", dvalues, dlabels, false);
/* 8912 */     prodType.addFormEvent("onClick", "buildSearchConfigs(this)");
/* 8913 */     form.addElement(prodType);
/*      */ 
/*      */ 
/*      */     
/* 8917 */     Vector searchConfigs = null;
/* 8918 */     searchConfigs = Cache.getSelectionConfigs();
/* 8919 */     FormDropDownMenu configSearch = MilestoneHelper.getSelectionConfigurationDropDown("ConfigSearch", "", false);
/* 8920 */     configSearch.setId("ConfigSearch");
/* 8921 */     configSearch.addFormEvent("onChange", "buildSearchSubConfigs(this.selectedIndex)");
/* 8922 */     form.addElement(configSearch);
/*      */ 
/*      */ 
/*      */     
/* 8926 */     FormDropDownMenu subconfigSearch = new FormDropDownMenu("SubconfigSearch", "");
/* 8927 */     subconfigSearch.setId("SubconfigSearch");
/* 8928 */     subconfigSearch.setEnabled(false);
/* 8929 */     form.addElement(subconfigSearch);
/*      */ 
/*      */ 
/*      */     
/* 8933 */     FormTextField upcSearch = new FormTextField("UPCSearch", "", false, 20, 20);
/* 8934 */     upcSearch.setId("UPCSearch");
/* 8935 */     form.addElement(upcSearch);
/*      */ 
/*      */     
/* 8938 */     FormTextField prefixSearch = new FormTextField("PrefixSearch", "", false, 6, 5);
/* 8939 */     prefixSearch.setId("PrefixSearch");
/* 8940 */     form.addElement(prefixSearch);
/*      */ 
/*      */     
/* 8943 */     FormTextField selectionSearch = new FormTextField("SelectionSearch", "", false, 12, 20);
/* 8944 */     selectionSearch.setId("SelectionSearch");
/* 8945 */     selectionSearch.setClassName("ctrlMedium");
/* 8946 */     form.addElement(selectionSearch);
/*      */ 
/*      */     
/* 8949 */     FormTextField titleSearch = new FormTextField("TitleSearch", "", false, 20);
/* 8950 */     titleSearch.setId("TitleSearch");
/* 8951 */     form.addElement(titleSearch);
/*      */ 
/*      */     
/* 8954 */     FormTextField artistSearch = new FormTextField("ArtistSearch", "", false, 20);
/* 8955 */     artistSearch.setId("ArtistSearch");
/* 8956 */     form.addElement(artistSearch);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 8994 */     FormTextField projectIDSearch = new FormTextField("ProjectIDSearch", "", false, 20);
/* 8995 */     projectIDSearch.setId("ProjectIDSearch");
/* 8996 */     form.addElement(projectIDSearch);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 9001 */     SelectionHandler.getUserPreferences(form, context);
/*      */     
/* 9003 */     return form;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getSearchJavaScriptCorporateArray(Context context) {
/* 9018 */     StringBuffer result = new StringBuffer(100);
/* 9019 */     String str = "";
/* 9020 */     String value = new String();
/* 9021 */     boolean foundFirstTemp = false;
/*      */     
/* 9023 */     User user = (User)context.getSessionValue("user");
/* 9024 */     Vector vUserCompanies = MilestoneHelper.getUserCompanies(context);
/*      */ 
/*      */     
/* 9027 */     HashMap corpHashMap = MilestoneHelper.buildActiveCorporateStructureHashMap();
/*      */ 
/*      */ 
/*      */     
/* 9031 */     result.append("\n");
/* 9032 */     result.append("var aSearch = new Array();\n");
/* 9033 */     int arrayIndex = 0;
/*      */     
/* 9035 */     result.append("aSearch[0] = new Array(");
/* 9036 */     result.append(0);
/* 9037 */     result.append(", '");
/* 9038 */     result.append(" ");
/* 9039 */     result.append('\'');
/* 9040 */     foundFirstTemp = true;
/*      */     
/* 9042 */     for (int a = 0; a < vUserCompanies.size(); a++) {
/*      */       
/* 9044 */       Company ucTemp = (Company)vUserCompanies.elementAt(a);
/* 9045 */       if (ucTemp != null) {
/*      */ 
/*      */         
/* 9048 */         Vector labels = Cache.getInstance().getLabels();
/* 9049 */         for (int b = 0; b < labels.size(); b++) {
/*      */           
/* 9051 */           Label node = (Label)labels.elementAt(b);
/*      */           
/* 9053 */           if (node.getParent().getParentID() == ucTemp.getStructureID() && 
/* 9054 */             !corpHashMap.containsKey(new Integer(node.getStructureID()))) {
/*      */             
/* 9056 */             if (foundFirstTemp)
/* 9057 */               result.append(','); 
/* 9058 */             result.append(' ');
/* 9059 */             result.append(node.getStructureID());
/* 9060 */             result.append(", '");
/* 9061 */             result.append(MilestoneHelper.urlEncode(node.getName()));
/* 9062 */             result.append('\'');
/* 9063 */             foundFirstTemp = true;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 9070 */     if (!foundFirstTemp) {
/* 9071 */       result.append("'[none available]');\n");
/*      */     } else {
/* 9073 */       result.append(");\n");
/*      */     } 
/*      */     
/* 9076 */     for (int i = 0; i < vUserCompanies.size(); i++) {
/*      */       
/* 9078 */       Company uc = (Company)vUserCompanies.elementAt(i);
/* 9079 */       if (uc != null) {
/*      */         
/* 9081 */         result.append("aSearch[");
/* 9082 */         result.append(uc.getStructureID());
/* 9083 */         result.append("] = new Array(");
/*      */         
/* 9085 */         boolean foundFirst = false;
/*      */         
/* 9087 */         result.append(0);
/* 9088 */         result.append(", '");
/* 9089 */         result.append(" ");
/* 9090 */         result.append('\'');
/* 9091 */         foundFirst = true;
/*      */         
/* 9093 */         Vector tmpArray = new Vector();
/*      */         
/* 9095 */         Vector labels = Cache.getInstance().getLabels();
/* 9096 */         for (int j = 0; j < labels.size(); j++) {
/*      */           
/* 9098 */           Label node = (Label)labels.elementAt(j);
/*      */           
/* 9100 */           if (node.getParent().getParentID() == uc.getStructureID() && 
/* 9101 */             !corpHashMap.containsKey(new Integer(node.getStructureID()))) {
/*      */             
/* 9103 */             if (foundFirst)
/* 9104 */               result.append(','); 
/* 9105 */             result.append(' ');
/* 9106 */             result.append(node.getStructureID());
/* 9107 */             result.append(", '");
/* 9108 */             result.append(MilestoneHelper.urlEncode(node.getName()));
/* 9109 */             result.append('\'');
/* 9110 */             foundFirst = true;
/* 9111 */             tmpArray.addElement(node);
/*      */           } 
/*      */         } 
/*      */         
/* 9115 */         if (foundFirst) {
/*      */           
/* 9117 */           result.append(");\n");
/*      */         }
/*      */         else {
/*      */           
/* 9121 */           result.append(" 0, '[none available]');\n");
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 9126 */     return result.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean sortGroup(Dispatcher dispatcher, Context context) {
/* 9135 */     int sort = Integer.parseInt(context.getParameter("OrderBy"));
/*      */     
/* 9137 */     String alphaGroupChr = context.getParameter("alphaGroupChr");
/*      */ 
/*      */     
/* 9140 */     Notepad notepad = (Notepad)context.getSessionValue(MilestoneConstants.NOTEPAD_SESSION_NAMES[0]);
/*      */ 
/*      */     
/* 9143 */     User user = (User)context.getSession().getAttribute("user");
/* 9144 */     if (notepad.getAllContents() != null && notepad.getAllContents().size() < notepad.getTotalRecords()) {
/*      */       
/* 9146 */       notepad.setMaxRecords(0);
/* 9147 */       notepad.setAllContents(null);
/* 9148 */       notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 0);
/*      */     } 
/*      */     
/* 9151 */     SelectionManager.getInstance().getAlphaGroupPosition(context, notepad, alphaGroupChr, sort);
/*      */     
/* 9153 */     notepad.goToSelectedPage();
/*      */     
/* 9155 */     dispatcher.redispatch(context, "bom-editor");
/*      */     
/* 9157 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String replaceQuote(String str) {
/* 9165 */     int s = 0;
/* 9166 */     int e = 0;
/* 9167 */     StringBuffer result = new StringBuffer();
/* 9168 */     while ((e = str.indexOf("&quot;", s)) >= 0) {
/* 9169 */       result.append(str.substring(s, e));
/* 9170 */       result.append("\"");
/* 9171 */       s = e + "&quot;".length();
/*      */     } 
/* 9173 */     result.append(str.substring(s));
/* 9174 */     return result.toString();
/*      */   }
/*      */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\BomHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */