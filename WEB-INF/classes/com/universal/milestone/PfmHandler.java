/*      */ package WEB-INF.classes.com.universal.milestone;
/*      */ 
/*      */ import com.techempower.ComponentLog;
/*      */ import com.techempower.gemini.Context;
/*      */ import com.techempower.gemini.Dispatcher;
/*      */ import com.techempower.gemini.FormCheckBox;
/*      */ import com.techempower.gemini.FormDropDownMenu;
/*      */ import com.techempower.gemini.FormElement;
/*      */ import com.techempower.gemini.FormHidden;
/*      */ import com.techempower.gemini.FormRadioButtonGroup;
/*      */ import com.techempower.gemini.FormTextArea;
/*      */ import com.techempower.gemini.FormTextField;
/*      */ import com.techempower.gemini.FormValidation;
/*      */ import com.techempower.gemini.GeminiApplication;
/*      */ import com.universal.milestone.Cache;
/*      */ import com.universal.milestone.Company;
/*      */ import com.universal.milestone.CompanyAcl;
/*      */ import com.universal.milestone.EmailDistribution;
/*      */ import com.universal.milestone.Environment;
/*      */ import com.universal.milestone.Form;
/*      */ import com.universal.milestone.Genre;
/*      */ import com.universal.milestone.Label;
/*      */ import com.universal.milestone.LookupObject;
/*      */ import com.universal.milestone.MilestoneConstants;
/*      */ import com.universal.milestone.MilestoneHelper;
/*      */ import com.universal.milestone.MilestoneHelper_2;
/*      */ import com.universal.milestone.MilestoneInfrastructure;
/*      */ import com.universal.milestone.MilestoneSecurity;
/*      */ import com.universal.milestone.Notepad;
/*      */ import com.universal.milestone.NotepadSortOrder;
/*      */ import com.universal.milestone.Pfm;
/*      */ import com.universal.milestone.PfmHandler;
/*      */ import com.universal.milestone.PrefixCode;
/*      */ import com.universal.milestone.ProjectSearchManager;
/*      */ import com.universal.milestone.ReleasingFamily;
/*      */ import com.universal.milestone.ReportHandler;
/*      */ import com.universal.milestone.SecureHandler;
/*      */ import com.universal.milestone.Selection;
/*      */ import com.universal.milestone.SelectionHandler;
/*      */ import com.universal.milestone.SelectionManager;
/*      */ import com.universal.milestone.User;
/*      */ import com.universal.milestone.UserManager;
/*      */ import com.universal.milestone.jms.MessageObject;
/*      */ import com.universal.milestone.push.PushCommunication;
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
/*      */ import net.umusic.milestone.alps.DcGDRSResults;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class PfmHandler
/*      */   extends SecureHandler
/*      */ {
/*      */   public static final String COMPONENT_CODE = "hPfm";
/*      */   public GeminiApplication application;
/*      */   public ComponentLog log;
/*      */   
/*      */   public PfmHandler(GeminiApplication application) {
/*  123 */     this.application = application;
/*  124 */     this.log = application.getLog("hPfm");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  132 */   public String getDescription() { return "Pfm"; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean acceptRequest(Dispatcher dispatcher, Context context, String command) {
/*  142 */     if (super.acceptRequest(dispatcher, context, command))
/*      */     {
/*  144 */       if (command.startsWith("pfm"))
/*      */       {
/*  146 */         return handleRequest(dispatcher, context, command);
/*      */       }
/*      */     }
/*  149 */     return false;
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
/*      */   public boolean handleRequest(Dispatcher dispatcher, Context context, String command) {
/*  163 */     EmailDistribution.removeSessionValues(context);
/*      */     
/*  165 */     if (command.equalsIgnoreCase("pfm-editor") || command.equalsIgnoreCase("pfm-edit-new"))
/*      */     {
/*  167 */       edit(dispatcher, context, command);
/*      */     }
/*  169 */     if (command.equalsIgnoreCase("pfm-edit-save") || command.equalsIgnoreCase("pfm-edit-save-comment"))
/*      */     {
/*  171 */       editSave(dispatcher, context, command);
/*      */     }
/*  173 */     if (command.equalsIgnoreCase("pfm-edit-copy"))
/*      */     {
/*  175 */       editCopy(dispatcher, context, command);
/*      */     }
/*  177 */     if (command.equalsIgnoreCase("pfm-paste-copy"))
/*      */     {
/*  179 */       pasteCopy(dispatcher, context, command);
/*      */     }
/*  181 */     if (command.equalsIgnoreCase("pfm-search"))
/*      */     {
/*  183 */       search(dispatcher, context, command);
/*      */     }
/*  185 */     if (command.equalsIgnoreCase("pfm-sort"))
/*      */     {
/*  187 */       sort(dispatcher, context, command);
/*      */     }
/*  189 */     if (command.equalsIgnoreCase("pfm-print-pdf"))
/*      */     {
/*  191 */       print(dispatcher, context, command, 0, true, null);
/*      */     }
/*  193 */     if (command.equalsIgnoreCase("pfm-print-rtf"))
/*      */     {
/*  195 */       print(dispatcher, context, command, 1, true, null);
/*      */     }
/*  197 */     if (command.equalsIgnoreCase("pfm-print-pdf4"))
/*      */     {
/*  199 */       print(dispatcher, context, command, 0, false, null);
/*      */     }
/*  201 */     if (command.equalsIgnoreCase("pfm-print-rtf4"))
/*      */     {
/*  203 */       print(dispatcher, context, command, 1, false, null);
/*      */     }
/*  205 */     if (command.equalsIgnoreCase("pfm-group"))
/*      */     {
/*  207 */       sortGroup(dispatcher, context);
/*      */     }
/*  209 */     if (command.equalsIgnoreCase("pfm-send-email"))
/*      */     {
/*  211 */       EmailDistribution.sendEmail(dispatcher, context, "pfm-editor", null);
/*      */     }
/*      */ 
/*      */     
/*  215 */     return true;
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
/*      */   private boolean edit(Dispatcher dispatcher, Context context, String command) {
/*  230 */     int selectionID = -1;
/*  231 */     Selection selection = null;
/*      */     
/*  233 */     User user = MilestoneSecurity.getUser(context);
/*      */ 
/*      */     
/*  236 */     Notepad notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 0);
/*  237 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */     
/*  239 */     selection = MilestoneHelper.getScreenSelection(context);
/*  240 */     context.putSessionValue("Selection", selection);
/*      */     
/*  242 */     if (selection != null) {
/*      */       
/*  244 */       selectionID = selection.getSelectionID();
/*  245 */       Pfm pfm = new Pfm();
/*      */       
/*  247 */       pfm = SelectionManager.getInstance().getPfm(selectionID);
/*      */ 
/*      */       
/*  250 */       Form form = buildForm(context, pfm, command);
/*      */       
/*  252 */       if ((Pfm)context.getSessionValue("copiedPfm") != null && pfm == null)
/*      */       {
/*      */         
/*  255 */         if (((Pfm)context.getSessionValue("copiedPfm")).getSelection().getIsDigital() == selection.getIsDigital()) {
/*  256 */           form.addElement(new FormHidden("copyPaste", "paste", true));
/*      */         }
/*      */       }
/*  259 */       form.addElement(new FormHidden("cmd", "pfm-editor", true));
/*  260 */       if (pfm != null) {
/*      */         
/*  262 */         context.putSessionValue("Pfm", pfm);
/*  263 */         form.addElement(new FormHidden("copyPaste", "copy", true));
/*      */       } 
/*  265 */       context.putDelivery("Form", form);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  270 */       if (context.getSessionValue("originalCommentRelId") != null) {
/*      */         
/*      */         try {
/*  273 */           int orgCmtRelId = ((Integer)context.getSessionValue("originalCommentRelId")).intValue();
/*  274 */           if (orgCmtRelId != pfm.getReleaseId())
/*      */           {
/*  276 */             context.removeSessionValue("originalComment");
/*  277 */             context.putSessionValue("originalCommentRelId", new Integer(pfm.getReleaseId()));
/*      */           }
/*      */         
/*      */         }
/*  281 */         catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*  286 */       else if (pfm == null) {
/*  287 */         context.putSessionValue("originalCommentRelId", new Integer("-1"));
/*      */       } else {
/*  289 */         context.putSessionValue("originalCommentRelId", new Integer(pfm.getReleaseId()));
/*      */       } 
/*      */       
/*  292 */       if (selection.getIsDigital()) {
/*  293 */         return context.includeJSP("pfm-editor-digital.jsp");
/*      */       }
/*  295 */       return context.includeJSP("pfm-editor.jsp");
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  300 */     context.removeSessionValue("originalComment");
/*  301 */     context.putSessionValue("originalCommentRelId", new Integer("-1"));
/*      */     
/*  303 */     Pfm pfm = new Pfm();
/*  304 */     pfm = null;
/*      */ 
/*      */     
/*  307 */     Form form = buildForm(context, pfm, command);
/*  308 */     form.addElement(new FormHidden("cmd", "pfm-editor", true));
/*      */     
/*  310 */     if (pfm != null)
/*      */     {
/*  312 */       context.putSessionValue("Pfm", pfm);
/*      */     }
/*  314 */     context.putDelivery("Form", form);
/*  315 */     form.addElement(new FormHidden("OrderBy", "", true));
/*      */     
/*  317 */     if (context.getSessionValue("NOTEPAD_PFM_VISIBLE") != null) {
/*  318 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_PFM_VISIBLE"));
/*      */     }
/*  320 */     return context.includeJSP("blank-pfm-editor.jsp");
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
/*      */   private boolean search(Dispatcher dispatcher, Context context, String command) {
/*  333 */     String isSelectionSearchResults = context.getParameter("isSelectionSearchResults");
/*  334 */     if (isSelectionSearchResults == null || !isSelectionSearchResults.equals("true")) {
/*      */ 
/*      */       
/*  337 */       Notepad notepad = MilestoneHelper.getNotepadFromSession(0, 
/*  338 */           context);
/*      */ 
/*      */       
/*  341 */       notepad.setAllContents(null);
/*  342 */       notepad.setSelected(null);
/*      */ 
/*      */       
/*  345 */       notepad.setMaxRecords(225);
/*      */ 
/*      */       
/*  348 */       Form form = new Form(this.application, "selectionForm", 
/*  349 */           this.application.getInfrastructure().getServletURL(), 
/*  350 */           "POST");
/*  351 */       form = addSelectionSearchElements(context, null, form);
/*  352 */       form.setValues(context);
/*      */       
/*  354 */       SelectionManager.getInstance().setSelectionNotepadQuery(context, 
/*  355 */           MilestoneSecurity.getUser(context).getUserId(), notepad, form);
/*      */     } 
/*  357 */     dispatcher.redispatch(context, "pfm-editor");
/*      */     
/*  359 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean sort(Dispatcher dispatcher, Context context, String command) {
/*  369 */     int userId = MilestoneSecurity.getUser(context).getUserId();
/*      */     
/*  371 */     Notepad notepad = SelectionManager.getInstance().getSelectionNotepad(context, userId, 0);
/*      */ 
/*      */     
/*  374 */     NotepadSortOrder.getNotepadSortOrderFromSession(context).sortHelper(dispatcher, context, notepad);
/*      */     
/*  376 */     dispatcher.redispatch(context, "pfm-editor");
/*      */     
/*  378 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Form buildForm(Context context, Pfm pfm, String command) {
/*  387 */     Form pfmForm = new Form(this.application, "pfmForm", 
/*  388 */         this.application.getInfrastructure().getServletURL(), "POST");
/*      */     
/*  390 */     Selection selection = null;
/*  391 */     if (command.equalsIgnoreCase("selectionSave")) {
/*  392 */       selection = (Selection)context.getSessionValue("pfmBomSelection");
/*      */     } else {
/*  394 */       selection = (Selection)context.getSessionValue("Selection");
/*      */     } 
/*  396 */     User user = MilestoneSecurity.getUser(context);
/*  397 */     int userId = user.getUserId();
/*      */     
/*  399 */     int secureLevel = getSelectionPfmPermissions(selection, user);
/*  400 */     setButtonVisibilities(selection, user, context, secureLevel, command);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  406 */     String selectionID = "-1";
/*  407 */     if (selection != null)
/*      */     {
/*  409 */       selectionID = String.valueOf(selection.getSelectionID());
/*      */     }
/*  411 */     pfmForm.addElement(new FormHidden("selectionID", selectionID, true));
/*      */ 
/*      */     
/*  414 */     long timeStamp = -1L;
/*  415 */     if (pfm != null)
/*  416 */       timeStamp = pfm.getLastUpdatedCk(); 
/*  417 */     pfmForm.addElement(new FormHidden("pfmLastUpdateCheck", Long.toString(timeStamp), true));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  422 */     String gridnum = "";
/*  423 */     if (selection != null)
/*      */     {
/*  425 */       gridnum = selection.getGridNumber();
/*      */     }
/*  427 */     FormTextField ftf_gridnum = new FormTextField("gridnum", gridnum, false, 20, 50);
/*      */     
/*  429 */     ftf_gridnum.setEnabled(false);
/*  430 */     pfmForm.addElement(ftf_gridnum);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  435 */     if (selection != null)
/*      */     {
/*  437 */       if (pfm != null) {
/*      */ 
/*      */         
/*  440 */         pfmForm.addElement(new FormHidden("OrderBy", "", true));
/*      */         
/*  442 */         String printOption = "Draft";
/*      */ 
/*      */         
/*  445 */         if (pfm.getPrintOption().length() > 0 && selection.getSelectionNo() != null && 
/*  446 */           selection.getIsDigital())
/*      */         {
/*  448 */           printOption = pfm.getPrintOption();
/*      */         }
/*      */         
/*  451 */         if (pfm.getPrintOption().length() > 0 && selection.getSelectionNo() != null && 
/*  452 */           !selection.getIsDigital() && 
/*  453 */           selection.getSelectionNo().toUpperCase().indexOf("TEMP") == -1)
/*      */         {
/*  455 */           printOption = pfm.getPrintOption();
/*      */         }
/*      */ 
/*      */         
/*  459 */         String pOptions = "Draft,Final";
/*      */         
/*  461 */         if (printOption.equalsIgnoreCase("Final")) {
/*  462 */           pOptions = printOption;
/*      */         }
/*      */ 
/*      */         
/*  466 */         MilestoneInfrastructure inf = (MilestoneInfrastructure)context.getInfrastructure();
/*      */         
/*  468 */         FormRadioButtonGroup PrintOption = new FormRadioButtonGroup("printOption", printOption, pOptions, false);
/*      */         
/*  470 */         PrintOption.addFormEvent("onClick", "verifyFinalValues(this);hidePrintButtons('printWindow','');toggleSave('" + inf.getImageDirectory() + "');");
/*  471 */         PrintOption.setId("Draft/Final");
/*  472 */         PrintOption.setTabIndex(0);
/*  473 */         pfmForm.addElement(PrintOption);
/*      */ 
/*      */ 
/*      */         
/*  477 */         FormRadioButtonGroup sendOption = new FormRadioButtonGroup("sendOption", "Send Email", "Send Email,Do Not Send Email", false);
/*  478 */         sendOption.addFormEvent("onClick", "toggleSaveSend('" + inf.getImageDirectory() + "');");
/*  479 */         sendOption.setTabIndex(0);
/*  480 */         pfmForm.addElement(sendOption);
/*      */ 
/*      */         
/*  483 */         String preparedByText = "";
/*  484 */         if (pfm.getPreparedBy() != null && pfm.getPreparedBy().length() > 0)
/*      */         {
/*  486 */           preparedByText = pfm.getPreparedBy();
/*      */         }
/*      */ 
/*      */         
/*  490 */         FormTextField prepared_by = new FormTextField("prepared_by", preparedByText, false, 30, 50);
/*      */         
/*  492 */         prepared_by.setTabIndex(1);
/*  493 */         pfmForm.addElement(prepared_by);
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
/*  512 */         String phoneText = "";
/*  513 */         if (pfm.getPhone() != null && pfm.getPhone().length() > 0)
/*      */         {
/*  515 */           phoneText = pfm.getPhone();
/*      */         }
/*      */ 
/*      */         
/*  519 */         FormTextField phone = new FormTextField("phone", phoneText, false, 30, 50);
/*  520 */         phone.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/*  521 */         phone.setTabIndex(2);
/*  522 */         pfmForm.addElement(phone);
/*      */ 
/*      */         
/*  525 */         String emailText = "";
/*  526 */         if (pfm.getEmail() != null && pfm.getEmail().length() > 0)
/*      */         {
/*  528 */           emailText = pfm.getEmail();
/*      */         }
/*      */ 
/*      */         
/*  532 */         FormTextField email = new FormTextField("email", emailText, false, 30, 50);
/*  533 */         email.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/*  534 */         email.setTabIndex(3);
/*  535 */         pfmForm.addElement(email);
/*      */ 
/*      */         
/*  538 */         String faxText = "";
/*  539 */         if (pfm.getFaxNumber() != null && pfm.getFaxNumber().length() > 0)
/*      */         {
/*  541 */           faxText = pfm.getFaxNumber();
/*      */         }
/*      */ 
/*      */         
/*  545 */         FormTextField fax = new FormTextField("fax", faxText, false, 30, 50);
/*  546 */         fax.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/*  547 */         fax.setTabIndex(4);
/*  548 */         pfmForm.addElement(fax);
/*      */ 
/*      */         
/*  551 */         String commentsText = "";
/*  552 */         if (pfm.getComments().length() > 0)
/*      */         {
/*  554 */           commentsText = pfm.getComments();
/*      */         }
/*  556 */         FormTextArea comments = new FormTextArea("comments", commentsText, false, 2, 100, "virtual");
/*  557 */         comments.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/*  558 */         comments.addFormEvent("onBlur", "Javascript:checkField(this)");
/*  559 */         comments.setTabIndex(4);
/*  560 */         pfmForm.addElement(comments);
/*      */         
/*  562 */         String mode = "";
/*  563 */         if (pfm.getMode().length() > 0) {
/*      */           
/*  565 */           mode = pfm.getMode();
/*  566 */           if (!mode.equalsIgnoreCase("Add"))
/*      */           {
/*  568 */             mode = "Change";
/*      */           }
/*      */         } 
/*  571 */         String[] labels = new String[2];
/*  572 */         labels[0] = "Add";
/*  573 */         labels[1] = "Change";
/*      */         
/*  575 */         String[] values = new String[2];
/*  576 */         values[0] = "Add";
/*  577 */         values[1] = "Change";
/*      */         
/*  579 */         FormRadioButtonGroup Mode = new FormRadioButtonGroup("mode", mode, values, labels, false);
/*  580 */         Mode.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/*      */         
/*  582 */         Mode.setEnabled(false);
/*  583 */         Mode.setTabIndex(5);
/*  584 */         Mode.setId("Add/Change");
/*  585 */         pfmForm.addElement(Mode);
/*      */ 
/*      */ 
/*      */         
/*  589 */         String operatingCompanyText = "";
/*  590 */         if (selection.getOperCompany() != null && !selection.getOperCompany().equals("-1")) {
/*  591 */           operatingCompanyText = selection.getOperCompany();
/*      */         }
/*  593 */         FormTextField operating_company = new FormTextField("operating_company", operatingCompanyText, false, 50);
/*  594 */         operating_company.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/*  595 */         operating_company.setTabIndex(8);
/*      */ 
/*      */         
/*  598 */         if (pfm.getOperatingCompany() == null) {
/*  599 */           operating_company.setStartingValue("");
/*      */         } else {
/*  601 */           operating_company.setStartingValue(pfm.getOperatingCompany());
/*      */         } 
/*  603 */         pfmForm.addElement(operating_company);
/*      */ 
/*      */         
/*  606 */         String productNumberText = "";
/*  607 */         productNumberText = String.valueOf(SelectionManager.getLookupObjectValue(selection.getPrefixID())) + selection.getSelectionNo();
/*  608 */         FormTextField product_number = new FormTextField("product_number", productNumberText, false, 20, 50);
/*  609 */         product_number.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/*      */         
/*  611 */         product_number.setTabIndex(7);
/*  612 */         product_number.setReadOnly(true);
/*      */ 
/*      */         
/*  615 */         if (pfm.getProductNumber() == null) {
/*  616 */           product_number.setStartingValue("");
/*      */         } else {
/*  618 */           product_number.setStartingValue(pfm.getProductNumber());
/*      */         } 
/*  620 */         pfmForm.addElement(product_number);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  626 */         FormDropDownMenu prefix = MilestoneHelper.getPrefixCodeDropDown(userId, "prefix", Cache.getPrefixCodes(), SelectionManager.getLookupObjectValue(pfm.getPrefixID()), false, context);
/*  627 */         prefix.setTabIndex(9);
/*  628 */         prefix.setClassName("ctrlShort");
/*  629 */         prefix.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/*  630 */         pfmForm.addElement(prefix);
/*      */         
/*  632 */         FormTextField selectionNo = new FormTextField("selectionNo", String.valueOf(pfm.getSelectionNo()), false, 14, 20);
/*  633 */         selectionNo.setTabIndex(10);
/*  634 */         selectionNo.setDisplayName("Physical Product Number");
/*  635 */         selectionNo.setClassName("ctrlSmall");
/*  636 */         selectionNo.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/*  637 */         pfmForm.addElement(selectionNo);
/*      */ 
/*      */         
/*  640 */         String musicLineText = "";
/*  641 */         if (pfm.getMusicLine() != null && pfm.getMusicLine().length() > 0)
/*      */         {
/*  643 */           musicLineText = pfm.getMusicLine();
/*      */         }
/*  645 */         FormDropDownMenu music_line = MilestoneHelper.getPfmLookupDropDown("music_line", Cache.getMusicLines(), String.valueOf(musicLineText), false, true);
/*  646 */         music_line.addFormEvent("onKeyPress", "scanKey(window.event.keyCode, 'document.all.music_line.options', getMaxLength(document.all.music_line.options))");
/*  647 */         music_line.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/*  648 */         music_line.setTabIndex(21);
/*  649 */         pfmForm.addElement(music_line);
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
/*  679 */         String selConfigCode = "";
/*  680 */         int selDigital = 0;
/*      */         
/*  682 */         if (selection != null) {
/*  683 */           if (selection.getIsDigital())
/*  684 */             selDigital = 1; 
/*  685 */           selConfigCode = selection.getConfigCode();
/*      */         } 
/*  687 */         FormDropDownMenu config_code = MilestoneHelper.getPfmLookupDropDown("config_code", MilestoneHelper.getConfigCodes(selDigital), selection.getConfigCode(), false, true);
/*  688 */         if (pfm.getConfigCode() == null) {
/*  689 */           config_code.setStartingValue("");
/*      */         } else {
/*      */           
/*  692 */           config_code.setStartingValue(pfm.getConfigCode());
/*      */         } 
/*      */         
/*  695 */         config_code.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/*  696 */         config_code.setTabIndex(11);
/*      */         
/*  698 */         pfmForm.addElement(config_code);
/*      */ 
/*      */         
/*  701 */         String repOwnerText = "";
/*  702 */         if (pfm.getRepertoireOwner() != null && pfm.getRepertoireOwner().length() > 0)
/*      */         {
/*  704 */           repOwnerText = pfm.getRepertoireOwner();
/*      */         }
/*  706 */         FormDropDownMenu repertoire_owner = MilestoneHelper.getPfmLookupDropDown("repertoire_owner", Cache.getRepertoireOwners(), String.valueOf(repOwnerText), false, true);
/*  707 */         repertoire_owner.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/*  708 */         repertoire_owner.setTabIndex(22);
/*  709 */         pfmForm.addElement(repertoire_owner);
/*      */ 
/*      */         
/*  712 */         String modifierText = "";
/*  713 */         if (pfm.getModifier() != null && pfm.getModifier().length() > 0)
/*      */         {
/*  715 */           modifierText = pfm.getModifier();
/*      */         }
/*  717 */         FormDropDownMenu modifier = MilestoneHelper.getPfmLookupDropDown("modifier", Cache.getModifiers(), String.valueOf(modifierText), false, true);
/*  718 */         modifier.addFormEvent("onKeyPress", "scanKey(window.event.keyCode, 'document.all.modifier.options', getMaxLength(document.all.modifier.options))");
/*  719 */         modifier.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/*  720 */         modifier.setTabIndex(12);
/*  721 */         pfmForm.addElement(modifier);
/*      */ 
/*      */         
/*  724 */         String repClass = "";
/*  725 */         if (pfm.getRepertoireClass() != null && pfm.getRepertoireClass().length() > 0) {
/*      */           
/*  727 */           LookupObject lookupObject = MilestoneHelper.getLookupObject(pfm.getRepertoireClass(), Cache.getRepertoireClasses());
/*  728 */           if (lookupObject != null) {
/*  729 */             repClass = String.valueOf(lookupObject.getAbbreviation()) + ":" + lookupObject.getName();
/*      */           }
/*      */         } 
/*  732 */         FormTextField RepClass = new FormTextField("repClass", repClass, false, 30, 50);
/*      */         
/*  734 */         RepClass.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/*  735 */         RepClass.setId("Rep. Class");
/*  736 */         pfmForm.addElement(RepClass);
/*      */ 
/*      */         
/*  739 */         String upc = "";
/*  740 */         if (selection.getUpc() != null)
/*      */         {
/*  742 */           upc = selection.getUpc();
/*      */         }
/*  744 */         FormTextField Upc = new FormTextField("upc", upc, false, 20);
/*  745 */         Upc.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/*  746 */         Upc.setTabIndex(20);
/*      */         
/*  748 */         if (pfm.getUpc() == null) {
/*  749 */           Upc.setStartingValue("");
/*      */         } else {
/*  751 */           Upc.setStartingValue(pfm.getUpc());
/*      */         } 
/*      */ 
/*      */         
/*  755 */         if (selection.getIsDigital())
/*      */         {
/*  757 */           Upc.addFormEvent("onKeyPress", "return checkNumbersOnly(window.event.keyCode);");
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*  762 */         pfmForm.addElement(Upc);
/*      */ 
/*      */ 
/*      */         
/*  766 */         String isPromo = "";
/*  767 */         if (selection.getReleaseType() != null)
/*  768 */           isPromo = selection.getReleaseType().getAbbreviation(); 
/*  769 */         FormTextField isPromoEl = new FormTextField("isPromo", isPromo, false, 20);
/*  770 */         pfmForm.addElement(isPromoEl);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  775 */         String imprintText = "";
/*  776 */         if (selection.getImprint() != null) {
/*  777 */           imprintText = selection.getImprint();
/*      */         }
/*  779 */         FormTextField imprint = new FormTextField("imprint", imprintText, false, 50);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  784 */         if (pfm.getSelection().getImprint() == null) {
/*  785 */           imprint.setStartingValue("");
/*      */         } else {
/*  787 */           imprint.setStartingValue(pfm.getSelection().getImprint());
/*  788 */         }  pfmForm.addElement(imprint);
/*      */ 
/*      */         
/*  791 */         String titleText = "";
/*  792 */         if (selection.getTitle() != null) {
/*  793 */           titleText = selection.getTitle();
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*  798 */         FormTextField title = new FormTextField("title", titleText, false, 50);
/*  799 */         title.setTabIndex(13);
/*  800 */         title.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/*      */         
/*  802 */         if (pfm.getTitle() == null) {
/*  803 */           title.setStartingValue("");
/*      */         } else {
/*  805 */           title.setStartingValue(pfm.getTitle());
/*      */         } 
/*  807 */         pfmForm.addElement(title);
/*      */ 
/*      */         
/*  810 */         String artistText = "";
/*  811 */         if (selection.getArtist() != null) {
/*      */           
/*  813 */           artistText = selection.getArtistLastName();
/*  814 */           if (!selection.getArtistLastName().equals("") && !selection.getArtistFirstName().equals(""))
/*      */           {
/*  816 */             artistText = String.valueOf(artistText) + ", ";
/*      */           }
/*  818 */           artistText = String.valueOf(artistText) + selection.getArtistFirstName();
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  823 */         FormTextField artistField = new FormTextField("artist", artistText, false, 50);
/*  824 */         artistField.setTabIndex(14);
/*      */         
/*  826 */         if (pfm.getArtist() == null) {
/*  827 */           artistField.setStartingValue("");
/*      */         } else {
/*  829 */           artistField.setStartingValue(pfm.getArtist());
/*      */         } 
/*  831 */         pfmForm.addElement(artistField);
/*      */ 
/*      */         
/*  834 */         String titleID = "";
/*      */         
/*  836 */         if (selection.getTitleID() != null) {
/*  837 */           titleID = selection.getTitleID();
/*      */         }
/*  839 */         FormTextField TitleID = new FormTextField("titleID", titleID, false, 50);
/*  840 */         TitleID.setTabIndex(15);
/*  841 */         TitleID.setId("Title ID");
/*  842 */         TitleID.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/*      */         
/*  844 */         if (pfm.getTitleId() == null) {
/*  845 */           TitleID.setStartingValue("");
/*      */         } else {
/*  847 */           TitleID.setStartingValue(pfm.getTitleId());
/*  848 */         }  pfmForm.addElement(TitleID);
/*      */ 
/*      */         
/*  851 */         String soundScanGroup = "";
/*  852 */         if (selection.getSoundScanGrp() != null) {
/*  853 */           soundScanGroup = selection.getSoundScanGrp();
/*      */         }
/*  855 */         FormTextField soundScanGroupField = new FormTextField("sound_scan_code", soundScanGroup, false, 20);
/*      */         
/*  857 */         if (selection.getIsDigital()) {
/*  858 */           soundScanGroupField.setId("Sales Grouping Code");
/*  859 */           soundScanGroupField.setTabIndex(21);
/*      */         } else {
/*      */           
/*  862 */           soundScanGroupField.setId("SoundScan Group Code");
/*  863 */           soundScanGroupField.setTabIndex(17);
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  869 */         soundScanGroupField.setClassName("ctrlMedium");
/*  870 */         soundScanGroupField.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/*      */ 
/*      */         
/*  873 */         if (pfm.getSoundScanGrp() == null) {
/*  874 */           soundScanGroupField.setStartingValue("");
/*      */         } else {
/*  876 */           soundScanGroupField.setStartingValue(pfm.getSoundScanGrp());
/*      */         } 
/*      */ 
/*      */         
/*  880 */         selection.getIsDigital();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  887 */         pfmForm.addElement(soundScanGroupField);
/*      */ 
/*      */         
/*  890 */         String returnCodeText = "";
/*  891 */         if (pfm.getReturnCode() != null && !pfm.getReturnCode().equals("[none]"))
/*      */         {
/*  893 */           returnCodeText = pfm.getReturnCode();
/*      */         }
/*  895 */         FormDropDownMenu return_code = MilestoneHelper.getPfmLookupDropDown("return_code", Cache.getReturnCodes(), String.valueOf(returnCodeText), false, true);
/*      */         
/*  897 */         return_code.setDisplayName("Return/Scrap Code");
/*  898 */         return_code.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/*  899 */         return_code.setTabIndex(23);
/*  900 */         pfmForm.addElement(return_code);
/*      */ 
/*      */ 
/*      */         
/*  904 */         String releaseDate = "";
/*  905 */         if (!selection.getIsDigital() && selection.getStreetDate() != null) {
/*      */           
/*  907 */           releaseDate = MilestoneHelper.getFormatedDate(selection.getStreetDate());
/*      */         }
/*  909 */         else if (selection.getIsDigital() && selection.getDigitalRlsDate() != null) {
/*      */           
/*  911 */           releaseDate = MilestoneHelper.getFormatedDate(selection.getDigitalRlsDate());
/*      */         } 
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
/*  923 */         FormTextField ReleaseDate = new FormTextField("releaseDate", releaseDate, false, 15);
/*  924 */         ReleaseDate.setTabIndex(17);
/*  925 */         ReleaseDate.setId("Release Date");
/*  926 */         ReleaseDate.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/*      */         
/*  928 */         String pfmReleaseDate = "";
/*  929 */         if (pfm.getStreetDate() != null) {
/*  930 */           pfmReleaseDate = MilestoneHelper.getFormatedDate(pfm.getStreetDate());
/*      */         }
/*      */         
/*  933 */         ReleaseDate.setStartingValue(pfmReleaseDate);
/*      */         
/*  935 */         pfmForm.addElement(ReleaseDate);
/*      */ 
/*      */ 
/*      */         
/*  939 */         FormHidden status = new FormHidden("status", SelectionManager.getLookupObjectValue(selection.getSelectionStatus()));
/*  940 */         if (pfm.getStatus() != null) {
/*  941 */           status.setStartingValue(pfm.getStatus());
/*      */         } else {
/*  943 */           status.setStartingValue("");
/*      */         } 
/*  945 */         pfmForm.addElement(status);
/*      */ 
/*      */ 
/*      */         
/*  949 */         String exportFlagText = "";
/*  950 */         if (pfm.getExportFlag() != null)
/*      */         {
/*  952 */           exportFlagText = pfm.getExportFlag();
/*      */         }
/*  954 */         FormDropDownMenu export_flag = MilestoneHelper.getPfmLookupDropDown("export_flag", Cache.getExportFlags(), String.valueOf(exportFlagText), false, true);
/*  955 */         export_flag.setTabIndex(24);
/*  956 */         export_flag.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/*  957 */         pfmForm.addElement(export_flag);
/*      */ 
/*      */         
/*  960 */         String superLabelText = "";
/*  961 */         if (selection.getSuperLabel() != null) {
/*  962 */           superLabelText = selection.getSuperLabel();
/*      */         }
/*  964 */         FormTextField super_label = new FormTextField("super_label", superLabelText, false, 50);
/*  965 */         super_label.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/*  966 */         super_label.setTabIndex(19);
/*      */ 
/*      */         
/*  969 */         if (pfm.getSuperLabel() == null) {
/*  970 */           super_label.setStartingValue("");
/*      */         } else {
/*  972 */           super_label.setStartingValue(pfm.getSuperLabel());
/*      */         } 
/*  974 */         pfmForm.addElement(super_label);
/*      */ 
/*      */         
/*  977 */         String countryText = "";
/*  978 */         if (pfm.getCountries() != null)
/*      */         {
/*  980 */           countryText = pfm.getCountries();
/*      */         }
/*  982 */         FormTextField countries = new FormTextField("countries", countryText, false, 20, 50);
/*  983 */         countries.setTabIndex(25);
/*  984 */         countries.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/*  985 */         pfmForm.addElement(countries);
/*      */ 
/*      */         
/*  988 */         String labelCodeText = "";
/*  989 */         if (selection.getSubLabel() != null) {
/*  990 */           labelCodeText = selection.getSubLabel();
/*      */         }
/*  992 */         FormTextField label_code = new FormTextField("label_code", labelCodeText, false, 20);
/*  993 */         label_code.setTabIndex(21);
/*  994 */         label_code.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/*      */ 
/*      */         
/*  997 */         if (pfm.getLabelCode() == null) {
/*  998 */           label_code.setStartingValue("");
/*      */         } else {
/* 1000 */           label_code.setStartingValue(pfm.getLabelCode());
/*      */         } 
/* 1002 */         pfmForm.addElement(label_code);
/*      */ 
/*      */         
/* 1005 */         String spineTitleText = "";
/* 1006 */         if (pfm.getSpineTitle() != null)
/*      */         {
/* 1008 */           spineTitleText = pfm.getSpineTitle();
/*      */         }
/* 1010 */         if (spineTitleText.length() > 37) {
/* 1011 */           spineTitleText = spineTitleText.substring(0, 37);
/*      */         }
/* 1013 */         FormTextField spine_title = new FormTextField("spine_title", spineTitleText, false, 37, 37);
/* 1014 */         spine_title.setTabIndex(26);
/* 1015 */         spine_title.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 1016 */         pfmForm.addElement(spine_title);
/*      */ 
/*      */         
/* 1019 */         String companyCodeText = "";
/* 1020 */         if (pfm.getCompanyCode() != null)
/*      */         {
/* 1022 */           companyCodeText = pfm.getCompanyCode();
/*      */         }
/* 1024 */         FormDropDownMenu company_code = MilestoneHelper.getPfmLookupDropDown("company_code", Cache.getCompanyCodes(), String.valueOf(companyCodeText), false, true);
/* 1025 */         company_code.setTabIndex(13);
/* 1026 */         company_code.addFormEvent("onKeyPress", "scanKey(window.event.keyCode, 'document.all.company_code.options', getMaxLength(document.all.company_code.options))");
/* 1027 */         company_code.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 1028 */         pfmForm.addElement(company_code);
/*      */ 
/*      */         
/* 1031 */         String spineArtistText = "";
/* 1032 */         if (pfm.getSpineArtist() != null)
/*      */         {
/* 1034 */           spineArtistText = pfm.getSpineArtist();
/*      */         }
/* 1036 */         if (spineArtistText.length() > 37) {
/* 1037 */           spineArtistText = spineArtistText.substring(0, 37);
/*      */         }
/* 1039 */         FormTextField spine_artist = new FormTextField("spine_artist", spineArtistText, false, 37, 37);
/* 1040 */         spine_artist.setTabIndex(27);
/* 1041 */         spine_artist.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 1042 */         pfmForm.addElement(spine_artist);
/*      */ 
/*      */         
/* 1045 */         String projectID = "";
/* 1046 */         if (selection.getProjectID() != null)
/*      */         {
/* 1048 */           projectID = selection.getProjectID();
/*      */         }
/* 1050 */         FormTextField ProjectID = new FormTextField("projectID", projectID, false, 50);
/* 1051 */         ProjectID.setTabIndex(25);
/* 1052 */         ProjectID.setId("Proj ID");
/* 1053 */         ProjectID.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/*      */         
/* 1055 */         if (pfm.getProjectID() == null) {
/* 1056 */           ProjectID.setStartingValue("");
/*      */         } else {
/* 1058 */           ProjectID.setStartingValue(pfm.getProjectID());
/*      */         } 
/* 1060 */         pfmForm.addElement(ProjectID);
/*      */ 
/*      */         
/* 1063 */         String priceCodeText = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1073 */         if (selection.getPriceCode() != null)
/* 1074 */           priceCodeText = selection.getPriceCode().getSellCode(); 
/* 1075 */         FormTextField price_code = new FormTextField("price_code", priceCodeText, false, 20, 50);
/* 1076 */         price_code.setTabIndex(25);
/*      */ 
/*      */         
/* 1079 */         price_code.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/*      */         
/* 1081 */         String pfmPriceCodeText = "";
/* 1082 */         if (pfm.getPriceCode() != null)
/* 1083 */           pfmPriceCodeText = pfm.getPriceCode(); 
/* 1084 */         price_code.setStartingValue(pfmPriceCodeText);
/* 1085 */         if (selection.getIsDigital())
/* 1086 */           price_code.setDisplayName("Dig. Price Code"); 
/* 1087 */         pfmForm.addElement(price_code);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1092 */         if (!selection.getIsDigital()) {
/*      */           
/* 1094 */           String priceCodeDPCText = "";
/* 1095 */           if (selection.getPriceCodeDPC() != null)
/* 1096 */             priceCodeDPCText = selection.getPriceCodeDPC().getSellCode(); 
/* 1097 */           FormTextField price_codeDPC = new FormTextField("price_codeDPC", priceCodeDPCText, false, 20, 50);
/* 1098 */           price_codeDPC.setTabIndex(25);
/*      */ 
/*      */           
/* 1101 */           price_codeDPC.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 1102 */           String pfmPriceCodeDPCText = "";
/* 1103 */           if (pfm.getPriceCodeDPC() != null)
/* 1104 */             pfmPriceCodeDPCText = pfm.getPriceCodeDPC(); 
/* 1105 */           price_codeDPC.setStartingValue(pfmPriceCodeDPCText);
/* 1106 */           price_codeDPC.setDisplayName("Dig. Price Code");
/*      */           
/* 1108 */           pfmForm.addElement(price_codeDPC);
/*      */         } 
/*      */ 
/*      */         
/* 1112 */         String poMergeCodeText = "";
/* 1113 */         if (pfm.getPoMergeCode() != null)
/*      */         {
/* 1115 */           poMergeCodeText = pfm.getPoMergeCode();
/*      */         }
/* 1117 */         FormDropDownMenu po_merge_code = MilestoneHelper.getPfmLookupDropDown("po_merge_code", Cache.getPoMergeCodes(), String.valueOf(poMergeCodeText), false, true);
/*      */         
/* 1119 */         po_merge_code.setDisplayName("PO Merge Code");
/* 1120 */         po_merge_code.setTabIndex(14);
/* 1121 */         po_merge_code.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 1122 */         pfmForm.addElement(po_merge_code);
/*      */ 
/*      */         
/* 1125 */         String guaranteeCodeText = "";
/* 1126 */         if (pfm.getGuaranteeCode() != null)
/*      */         {
/* 1128 */           guaranteeCodeText = pfm.getGuaranteeCode();
/*      */         }
/* 1130 */         FormDropDownMenu guarantee_code = MilestoneHelper.getPfmLookupDropDown("guarantee_code", Cache.getGuaranteeCodes(), String.valueOf(guaranteeCodeText), false, true);
/*      */         
/* 1132 */         guarantee_code.setDisplayName("IMI Exempt Code");
/* 1133 */         guarantee_code.setTabIndex(29);
/* 1134 */         guarantee_code.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 1135 */         pfmForm.addElement(guarantee_code);
/*      */ 
/*      */         
/* 1138 */         String loosePickExemptCodeText = "";
/* 1139 */         if (pfm.getLoosePickExemptCode() != null)
/*      */         {
/* 1141 */           loosePickExemptCodeText = pfm.getLoosePickExemptCode();
/*      */         }
/* 1143 */         FormDropDownMenu loose_pick_exempt = MilestoneHelper.getPfmLookupDropDown("loose_pick_exempt", Cache.getLoosePickExempt(), String.valueOf(loosePickExemptCodeText), false, true);
/*      */         
/* 1145 */         loose_pick_exempt.setDisplayName("Loose Pick Exempt");
/* 1146 */         loose_pick_exempt.setTabIndex(28);
/* 1147 */         loose_pick_exempt.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 1148 */         pfmForm.addElement(loose_pick_exempt);
/*      */ 
/*      */         
/* 1151 */         String compilationCodeText = "";
/* 1152 */         if (pfm.getCompilationCode() != null)
/*      */         {
/* 1154 */           compilationCodeText = pfm.getCompilationCode();
/*      */         }
/* 1156 */         FormDropDownMenu compilation_code = MilestoneHelper.getPfmLookupDropDown("compilation_code", Cache.getCompilationCodes(), String.valueOf(compilationCodeText), false, true);
/* 1157 */         compilation_code.setTabIndex(34);
/* 1158 */         compilation_code.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 1159 */         pfmForm.addElement(compilation_code);
/*      */ 
/*      */         
/* 1162 */         FormCheckBox valueAdded = new FormCheckBox("ValueAdded", "on", "", false, pfm.getValueAdded());
/* 1163 */         valueAdded.setTabIndex(36);
/* 1164 */         valueAdded.setId("Value Added");
/* 1165 */         pfmForm.addElement(valueAdded);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1170 */         FormCheckBox parentalAdv = new FormCheckBox("ParentalAdv", "on", "", false, selection.getParentalGuidance());
/* 1171 */         parentalAdv.setTabIndex(37);
/*      */ 
/*      */ 
/*      */         
/* 1175 */         parentalAdv.setEnabled(false);
/*      */         
/* 1177 */         parentalAdv.setReadOnly(true);
/*      */         
/* 1179 */         parentalAdv.setChecked(selection.getParentalGuidance());
/* 1180 */         parentalAdv.setId("Parental Adv");
/* 1181 */         parentalAdv.setClassName("ctrlMediumGrayBG");
/*      */ 
/*      */         
/* 1184 */         parentalAdv.setStartingChecked(pfm.getParentalGuidance());
/*      */         
/* 1186 */         pfmForm.addElement(parentalAdv);
/*      */ 
/*      */         
/* 1189 */         FormCheckBox boxSet = new FormCheckBox("BoxSet", "on", "", false, pfm.getBoxSet());
/* 1190 */         boxSet.setTabIndex(35);
/* 1191 */         boxSet.setId("Box Set");
/*      */         
/* 1193 */         pfmForm.addElement(boxSet);
/*      */ 
/*      */         
/* 1196 */         String unitsPerSetText = "";
/*      */ 
/*      */         
/* 1199 */         if (pfm.getUnitsPerSet() > 0) {
/*      */           
/*      */           try {
/*      */ 
/*      */ 
/*      */             
/* 1205 */             unitsPerSetText = Integer.toString(pfm.getUnitsPerSet());
/*      */           }
/* 1207 */           catch (NumberFormatException e) {
/*      */             
/* 1209 */             System.out.println("Error converting Units Per Set into integer.");
/*      */           } 
/*      */         }
/* 1212 */         FormTextField units_per_set = new FormTextField("units_per_set", unitsPerSetText, false, 20, 20);
/*      */         
/* 1214 */         units_per_set.setDisplayName("# of Units");
/* 1215 */         units_per_set.setTabIndex(15);
/*      */         
/* 1217 */         units_per_set.addFormEvent("onKeyPress", "return checkNumbersOnly(window.event.keyCode);");
/* 1218 */         units_per_set.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1225 */         units_per_set.setStartingValue(unitsPerSetText);
/*      */         
/* 1227 */         pfmForm.addElement(units_per_set);
/*      */ 
/*      */         
/* 1230 */         String impRateCodeText = "";
/* 1231 */         if (pfm.getImpRateCode() != null)
/*      */         {
/* 1233 */           impRateCodeText = pfm.getImpRateCode();
/*      */         }
/* 1235 */         FormDropDownMenu imp_rate_code = MilestoneHelper.getPfmLookupDropDown("imp_rate_code", Cache.getImpRateCodes(), String.valueOf(impRateCodeText), false, true);
/* 1236 */         imp_rate_code.setTabIndex(31);
/* 1237 */         imp_rate_code.addFormEvent("onKeyPress", "scanKey(window.event.keyCode, 'document.all.imp_rate_code.options', getMaxLength(document.all.imp_rate_code.options))");
/* 1238 */         imp_rate_code.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 1239 */         pfmForm.addElement(imp_rate_code);
/*      */ 
/*      */         
/* 1242 */         String setsPerCartonText = "0";
/* 1243 */         if (pfm.getSetsPerCarton() > 0) {
/*      */           
/*      */           try {
/*      */             
/* 1247 */             setsPerCartonText = Integer.toString(pfm.getSetsPerCarton());
/*      */           }
/* 1249 */           catch (NumberFormatException e) {
/*      */             
/* 1251 */             System.out.println("Error converting Sets Per Carton into integer.");
/*      */           } 
/*      */         }
/* 1254 */         FormTextField sets_per_carton = new FormTextField("sets_per_carton", setsPerCartonText, false, 20, 20);
/*      */         
/* 1256 */         sets_per_carton.setDisplayName("Sets Per Carton");
/* 1257 */         sets_per_carton.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 1258 */         sets_per_carton.setTabIndex(16);
/*      */         
/* 1260 */         sets_per_carton.addFormEvent("onKeyPress", "return checkNumbersOnly(window.event.keyCode);");
/* 1261 */         pfmForm.addElement(sets_per_carton);
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
/* 1282 */         FormDropDownMenu music_type = MilestoneHelper.getPfmLookupDropDown("music_type", Cache.getMusicTypes(), SelectionManager.getLookupObjectValue(selection.getGenre()), false, true);
/* 1283 */         music_type.setTabIndex(30);
/* 1284 */         music_type.addFormEvent("onKeyPress", "scanKey(window.event.keyCode, 'document.all.music_type.options', getMaxLength(document.all.music_type.options))");
/* 1285 */         music_type.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 1286 */         if (pfm.getMusicType() == null) {
/* 1287 */           music_type.setStartingValue("");
/*      */         } else {
/* 1289 */           music_type.setStartingValue(SelectionManager.getLookupObjectValue(pfm.getMusicType()));
/* 1290 */         }  pfmForm.addElement(music_type);
/*      */ 
/*      */         
/* 1293 */         String supplierText = "";
/* 1294 */         if (pfm.getSupplier() != null)
/*      */         {
/* 1296 */           supplierText = pfm.getSupplier();
/*      */         }
/* 1298 */         FormDropDownMenu supplier = MilestoneHelper.getPfmLookupDropDown("suppliers", Cache.getSuppliers(), String.valueOf(supplierText), false, true);
/* 1299 */         supplier.setTabIndex(17);
/* 1300 */         supplier.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 1301 */         pfmForm.addElement(supplier);
/*      */ 
/*      */         
/* 1304 */         String narmText = "";
/* 1305 */         if (pfm.getNarmFlag() != null)
/*      */         {
/* 1307 */           narmText = pfm.getNarmFlag();
/*      */         }
/* 1309 */         FormDropDownMenu narm = MilestoneHelper.getPfmLookupDropDown("NARM", Cache.getNarmExtracts(), String.valueOf(narmText), false, true);
/*      */         
/* 1311 */         narm.setDisplayName("Narm Extract Ind.");
/* 1312 */         narm.setTabIndex(33);
/* 1313 */         narm.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 1314 */         pfmForm.addElement(narm);
/*      */ 
/*      */ 
/*      */         
/* 1318 */         String encryptionText = "";
/* 1319 */         if (pfm.getEncryptionFlag() != null) {
/* 1320 */           encryptionText = pfm.getEncryptionFlag();
/*      */         }
/* 1322 */         FormDropDownMenu encryptionFlag = MilestoneHelper.getPfmLookupDropDown("encryptionFlag", Cache.getEncryptionFlags(), String.valueOf(encryptionText), false, true);
/* 1323 */         encryptionFlag.setTabIndex(38);
/* 1324 */         encryptionFlag.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 1325 */         pfmForm.addElement(encryptionFlag);
/*      */ 
/*      */         
/* 1328 */         String importIndicatorText = "";
/*      */         
/* 1330 */         String names = "";
/*      */         
/* 1332 */         if (pfm.getImportIndicator() != null) {
/*      */           
/* 1334 */           importIndicatorText = pfm.getImportIndicator();
/* 1335 */           Vector importIndicators = Cache.getImportIndicators();
/*      */           
/* 1337 */           String[] abbreviation = new String[importIndicators.size()];
/*      */ 
/*      */ 
/*      */           
/* 1341 */           for (int i = importIndicators.size() - 1; i > -1; i--) {
/*      */             
/* 1343 */             LookupObject lookupObject = (LookupObject)importIndicators.elementAt(i);
/*      */             
/* 1345 */             names = String.valueOf(names) + lookupObject.getName();
/* 1346 */             if (i > 0)
/*      */             {
/* 1348 */               names = String.valueOf(names) + ",";
/*      */             }
/* 1350 */             abbreviation[i] = lookupObject.getAbbreviation();
/* 1351 */             if (importIndicatorText.equalsIgnoreCase(abbreviation[i]))
/*      */             {
/* 1353 */               importIndicatorText = lookupObject.getName();
/*      */             }
/*      */           } 
/*      */         } 
/*      */         
/* 1358 */         FormRadioButtonGroup ImportIndicator = new FormRadioButtonGroup("ImportIndicator", importIndicatorText, names, false);
/* 1359 */         ImportIndicator.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 1360 */         ImportIndicator.setId("Imp. Ind");
/* 1361 */         ImportIndicator.setTabIndex(18);
/* 1362 */         pfmForm.addElement(ImportIndicator);
/*      */ 
/*      */         
/* 1365 */         String pricePointText = "";
/* 1366 */         if (pfm.getPricePoint() != null)
/*      */         {
/* 1368 */           pricePointText = pfm.getPricePoint();
/*      */         }
/*      */         
/* 1371 */         if (selection.getIsDigital()) {
/* 1372 */           pricePointText = "PS";
/*      */         }
/* 1374 */         FormDropDownMenu price_point = MilestoneHelper.getPfmLookupDropDown("price_point", Cache.getPricePoints(), String.valueOf(pricePointText), false, true);
/* 1375 */         price_point.setTabIndex(32);
/* 1376 */         price_point.addFormEvent("onKeyPress", "scanKey(window.event.keyCode, 'document.all.price_point.options', getMaxLength(document.all.price_point.options))");
/* 1377 */         price_point.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 1378 */         pfmForm.addElement(price_point);
/*      */ 
/*      */         
/* 1381 */         FormTextField lastUpdated = new FormTextField("lastUpdatedDate", false, 50);
/* 1382 */         if (pfm.getLastUpdatedDate() != null)
/* 1383 */           lastUpdated.setValue(MilestoneHelper.getCustomFormatedDate(pfm.getLastUpdatedDate(), "M/d/yyyy hh:mm:ss a 'ET'")); 
/* 1384 */         lastUpdated.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 1385 */         pfmForm.addElement(lastUpdated);
/*      */         
/* 1387 */         FormTextField lastUpdatedBy = new FormTextField("lastUpdatedBy", false, 50);
/* 1388 */         if (UserManager.getInstance().getUser(pfm.getLastUpdatingUser()) != null)
/* 1389 */           lastUpdatedBy.setValue(UserManager.getInstance().getUser(pfm.getLastUpdatingUser()).getName()); 
/* 1390 */         lastUpdatedBy.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 1391 */         pfmForm.addElement(lastUpdatedBy);
/*      */ 
/*      */         
/* 1394 */         FormTextField lastLegacyUpdateDate = new FormTextField("lastLegacyUpdateDate", false, 50);
/* 1395 */         lastLegacyUpdateDate.setValue(MilestoneHelper.getCustomFormatedDate(selection.getLastLegacyUpdateDate(), "M/d/yyyy hh:mm:ss a 'ET'"));
/* 1396 */         pfmForm.addElement(lastLegacyUpdateDate);
/*      */ 
/*      */         
/* 1399 */         String archieDateText = "";
/* 1400 */         if (selection.getArchieDate() != null)
/* 1401 */           archieDateText = MilestoneHelper.getCustomFormatedDate(selection.getArchieDate(), "M/d/yyyy hh:mm:ss a 'ET'"); 
/* 1402 */         FormTextField archieDate = new FormTextField("archieDate", archieDateText, false, 13);
/* 1403 */         pfmForm.addElement(archieDate);
/*      */ 
/*      */         
/* 1406 */         String approvedByName = "";
/* 1407 */         if (pfm.getApprovedByName() != null)
/*      */         {
/* 1409 */           approvedByName = pfm.getApprovedByName();
/*      */         }
/* 1411 */         FormTextField approvedBy = new FormTextField("approvedBy", false, 50);
/* 1412 */         approvedBy.setValue(approvedByName);
/* 1413 */         pfmForm.addElement(approvedBy);
/*      */ 
/*      */         
/* 1416 */         String enteredByName = "";
/* 1417 */         if (pfm.getEnteredByName() != null)
/*      */         {
/* 1419 */           enteredByName = pfm.getEnteredByName();
/*      */         }
/* 1421 */         FormTextField enteredBy = new FormTextField("enteredBy", false, 50);
/* 1422 */         enteredBy.setValue(enteredByName);
/* 1423 */         pfmForm.addElement(enteredBy);
/*      */ 
/*      */         
/* 1426 */         String verifiedByName = "";
/* 1427 */         if (pfm.getVerifiedByName() != null)
/*      */         {
/* 1429 */           verifiedByName = pfm.getVerifiedByName();
/*      */         }
/* 1431 */         FormTextField verifiedBy = new FormTextField("verifiedBy", false, 50);
/* 1432 */         verifiedBy.setValue(verifiedByName);
/* 1433 */         pfmForm.addElement(verifiedBy);
/*      */ 
/*      */ 
/*      */         
/* 1437 */         String changeNumberText = "-1";
/* 1438 */         if (pfm.getChangeNumber() != null && !pfm.getChangeNumber().equals(""))
/*      */         {
/* 1440 */           changeNumberText = pfm.getChangeNumber();
/*      */         }
/* 1442 */         FormTextField ChangeNumber = new FormTextField("ChangeNumber", changeNumberText, false, 2, 2);
/* 1443 */         ChangeNumber.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/*      */         
/* 1445 */         ChangeNumber.setTabIndex(6);
/*      */ 
/*      */         
/* 1448 */         ChangeNumber.setEnabled(false);
/* 1449 */         ChangeNumber.setId("Change #");
/*      */         
/* 1451 */         pfmForm.addElement(ChangeNumber);
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */         
/* 1458 */         pfmForm.addElement(new FormHidden("cmd", "pfm-edit-new", true));
/* 1459 */         pfmForm.addElement(new FormHidden("OrderBy", "", true));
/*      */         
/* 1461 */         pfmForm.addElement(new FormHidden("selectionID", selectionID, true));
/*      */ 
/*      */         
/* 1464 */         pfmForm.addElement(new FormHidden("pfmLastUpdateCheck", "-1", true));
/*      */         
/* 1466 */         String printOption = "Draft";
/* 1467 */         if (pfm != null && pfm.getPrintOption().length() > 0 && selection.getSelectionNo() != null && 
/* 1468 */           selection.getSelectionNo().toUpperCase().indexOf("TEMP") == -1)
/*      */         {
/* 1470 */           printOption = pfm.getPrintOption();
/*      */         }
/*      */ 
/*      */         
/* 1474 */         MilestoneInfrastructure inf = (MilestoneInfrastructure)context.getInfrastructure();
/* 1475 */         FormRadioButtonGroup PrintOption = new FormRadioButtonGroup("printOption", printOption, "Draft,Final", false);
/*      */         
/* 1477 */         PrintOption.addFormEvent("onClick", "verifyFinalValues(this);hidePrintButtons('printWindow','');toggleSave('" + inf.getImageDirectory() + "');");
/* 1478 */         PrintOption.setId("Draft/Final");
/* 1479 */         PrintOption.setTabIndex(0);
/* 1480 */         pfmForm.addElement(PrintOption);
/*      */ 
/*      */         
/* 1483 */         FormTextField prepared_by = new FormTextField("prepared_by", user.getName(), false, 30, 50);
/* 1484 */         prepared_by.setTabIndex(1);
/* 1485 */         pfmForm.addElement(prepared_by);
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
/* 1498 */         FormTextField phone = new FormTextField("phone", user.getPhone(), false, 30, 50);
/* 1499 */         phone.setTabIndex(2);
/* 1500 */         pfmForm.addElement(phone);
/*      */ 
/*      */         
/* 1503 */         FormTextField email = new FormTextField("email", user.getEmail(), false, 30, 50);
/* 1504 */         email.setTabIndex(3);
/* 1505 */         pfmForm.addElement(email);
/*      */ 
/*      */         
/* 1508 */         FormTextField fax = new FormTextField("fax", user.getFax(), false, 30, 50);
/* 1509 */         fax.setTabIndex(4);
/* 1510 */         pfmForm.addElement(fax);
/*      */ 
/*      */         
/* 1513 */         FormTextArea comments = new FormTextArea("comments", "", false, 2, 100, "virtual");
/* 1514 */         comments.addFormEvent("onBlur", "Javascript:checkField(this)");
/* 1515 */         comments.setTabIndex(4);
/* 1516 */         pfmForm.addElement(comments);
/*      */ 
/*      */         
/* 1519 */         FormRadioButtonGroup Mode = new FormRadioButtonGroup("mode", "Add", "Add,Change", false);
/* 1520 */         Mode.setTabIndex(5);
/*      */         
/* 1522 */         Mode.setEnabled(false);
/* 1523 */         Mode.setId("Add/Change");
/* 1524 */         pfmForm.addElement(Mode);
/*      */ 
/*      */         
/* 1527 */         String operatingCompanyText = "";
/* 1528 */         operatingCompanyText = selection.getOperCompany();
/* 1529 */         if (operatingCompanyText.equalsIgnoreCase("-1"))
/* 1530 */           operatingCompanyText = ""; 
/* 1531 */         FormTextField operating_company = new FormTextField("operating_company", operatingCompanyText, false, 50);
/* 1532 */         operating_company.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 1533 */         operating_company.setTabIndex(8);
/* 1534 */         pfmForm.addElement(operating_company);
/*      */ 
/*      */ 
/*      */         
/* 1538 */         String productNumber = String.valueOf(SelectionManager.getLookupObjectValue(selection.getPrefixID())) + selection.getSelectionNo();
/* 1539 */         FormTextField product_number = new FormTextField("product_number", productNumber, false, 20, 50);
/* 1540 */         product_number.setTabIndex(7);
/* 1541 */         product_number.setReadOnly(true);
/* 1542 */         pfmForm.addElement(product_number);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1547 */         FormDropDownMenu prefix = MilestoneHelper.getPrefixCodeDropDown(userId, "prefix", Cache.getPrefixCodes(), SelectionManager.getLookupObjectValue(selection.getPrefixID()), false, context);
/* 1548 */         prefix.setTabIndex(9);
/* 1549 */         prefix.setClassName("ctrlShort");
/* 1550 */         prefix.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 1551 */         pfmForm.addElement(prefix);
/*      */         
/* 1553 */         FormTextField selectionNo = new FormTextField("selectionNo", String.valueOf(selection.getSelectionNo()), false, 14, 20);
/* 1554 */         selectionNo.setTabIndex(10);
/* 1555 */         selectionNo.setDisplayName("Physical Product Number");
/* 1556 */         selectionNo.setClassName("ctrlSmall");
/* 1557 */         selectionNo.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 1558 */         pfmForm.addElement(selectionNo);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1565 */         String musicLineText = "";
/* 1566 */         if (selection.getIsDigital() && (
/* 1567 */           MilestoneHelper.getStructureName(selection.getReleaseFamilyId()).equals("Canada") || 
/* 1568 */           MilestoneHelper.getStructureName(selection.getReleaseFamilyId()).equals("Mexico")))
/* 1569 */           musicLineText = "1010"; 
/* 1570 */         FormDropDownMenu music_line = MilestoneHelper.getPfmLookupDropDown("music_line", Cache.getMusicLines(), musicLineText, false, true);
/* 1571 */         music_line.addFormEvent("onKeyPress", "scanKey(window.event.keyCode, 'document.all.music_line.options', getMaxLength(document.all.music_line.options))");
/* 1572 */         music_line.setTabIndex(21);
/* 1573 */         pfmForm.addElement(music_line);
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
/* 1585 */         String selConfigCode = "";
/* 1586 */         int selDigital = 0;
/*      */         
/* 1588 */         if (selection != null) {
/* 1589 */           if (selection.getIsDigital())
/* 1590 */             selDigital = 1; 
/* 1591 */           selConfigCode = selection.getConfigCode();
/*      */         } 
/* 1593 */         FormDropDownMenu config_code = MilestoneHelper.getPfmLookupDropDown("config_code", MilestoneHelper.getConfigCodes(selDigital), selConfigCode, false, true);
/* 1594 */         config_code.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 1595 */         config_code.setTabIndex(11);
/*      */         
/* 1597 */         pfmForm.addElement(config_code);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1604 */         String repOwnerText = "";
/* 1605 */         if (selection.getIsDigital() && (
/* 1606 */           MilestoneHelper.getStructureName(selection.getReleaseFamilyId()).equals("Canada") || 
/* 1607 */           MilestoneHelper.getStructureName(selection.getReleaseFamilyId()).equals("Mexico")))
/* 1608 */           repOwnerText = "OT"; 
/* 1609 */         FormDropDownMenu repertoire_owner = MilestoneHelper.getPfmLookupDropDown("repertoire_owner", Cache.getRepertoireOwners(), repOwnerText, false, true);
/* 1610 */         repertoire_owner.setTabIndex(22);
/* 1611 */         pfmForm.addElement(repertoire_owner);
/*      */ 
/*      */         
/* 1614 */         FormDropDownMenu modifier = MilestoneHelper.getPfmLookupDropDown("modifier", Cache.getModifiers(), "", false, true);
/* 1615 */         modifier.addFormEvent("onKeyPress", "scanKey(window.event.keyCode, 'document.all.modifier.options', getMaxLength(document.all.modifier.options))");
/* 1616 */         modifier.setTabIndex(12);
/* 1617 */         pfmForm.addElement(modifier);
/*      */ 
/*      */         
/* 1620 */         String soundScanGroup = "";
/* 1621 */         if (selection.getSoundScanGrp() != null) {
/* 1622 */           soundScanGroup = selection.getSoundScanGrp();
/*      */         }
/* 1624 */         FormTextField soundScanGroupField = new FormTextField("sound_scan_code", soundScanGroup, false, 20);
/*      */         
/* 1626 */         if (selection.getIsDigital()) {
/* 1627 */           soundScanGroupField.setId("Sales Grouping Code");
/* 1628 */           soundScanGroupField.setTabIndex(21);
/*      */         }
/*      */         else {
/*      */           
/* 1632 */           soundScanGroupField.setId("SoundScan Group Code");
/* 1633 */           soundScanGroupField.setTabIndex(17);
/*      */         } 
/*      */         
/* 1636 */         soundScanGroupField.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/*      */ 
/*      */ 
/*      */         
/* 1640 */         soundScanGroupField.setClassName("ctrlMedium");
/*      */ 
/*      */         
/* 1643 */         selection.getIsDigital();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1650 */         pfmForm.addElement(soundScanGroupField);
/*      */ 
/*      */         
/* 1653 */         FormTextField RepClass = new FormTextField("repClass", "", false, 30, 50);
/*      */         
/* 1655 */         pfmForm.addElement(RepClass);
/*      */ 
/*      */         
/* 1658 */         String upc = "";
/* 1659 */         if (selection.getUpc() != null)
/*      */         {
/* 1661 */           upc = selection.getUpc();
/*      */         }
/* 1663 */         FormTextField Upc = new FormTextField("upc", upc, false, 20);
/* 1664 */         Upc.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 1665 */         Upc.setTabIndex(20);
/*      */ 
/*      */ 
/*      */         
/* 1669 */         if (selection.getIsDigital())
/*      */         {
/* 1671 */           Upc.addFormEvent("onKeyPress", "return checkNumbersOnly(window.event.keyCode);");
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 1676 */         pfmForm.addElement(Upc);
/*      */ 
/*      */ 
/*      */         
/* 1680 */         String isPromo = "";
/* 1681 */         if (selection.getReleaseType() != null)
/* 1682 */           isPromo = selection.getReleaseType().getAbbreviation(); 
/* 1683 */         FormTextField isPromoEl = new FormTextField("isPromo", isPromo, false, 20);
/* 1684 */         pfmForm.addElement(isPromoEl);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1689 */         String imprintText = "";
/* 1690 */         if (selection.getImprint() != null) {
/* 1691 */           imprintText = selection.getImprint();
/*      */         }
/* 1693 */         FormTextField imprint = new FormTextField("imprint", imprintText, false, 50);
/* 1694 */         pfmForm.addElement(imprint);
/*      */ 
/*      */ 
/*      */         
/* 1698 */         String titleText = "";
/* 1699 */         if (selection.getTitle() != null) {
/* 1700 */           titleText = selection.getTitle();
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 1705 */         FormTextField title = new FormTextField("title", titleText, false, 50);
/* 1706 */         title.setTabIndex(13);
/* 1707 */         title.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 1708 */         pfmForm.addElement(title);
/*      */ 
/*      */         
/* 1711 */         String artist = "";
/* 1712 */         if (selection.getArtist() != null) {
/*      */           
/* 1714 */           artist = selection.getArtistLastName();
/* 1715 */           if (!selection.getArtistLastName().equals("") && !selection.getArtistFirstName().equals(""))
/*      */           {
/* 1717 */             artist = String.valueOf(artist) + ", ";
/*      */           }
/* 1719 */           artist = String.valueOf(artist) + selection.getArtistFirstName();
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1724 */         FormTextField artistField = new FormTextField("artist", artist, false, 50);
/* 1725 */         artistField.setTabIndex(14);
/* 1726 */         pfmForm.addElement(artistField);
/*      */ 
/*      */         
/* 1729 */         String titleID = "";
/* 1730 */         if (selection.getTitleID() != null)
/*      */         {
/* 1732 */           titleID = selection.getTitleID();
/*      */         }
/* 1734 */         FormTextField TitleID = new FormTextField("titleID", titleID, false, 50);
/* 1735 */         TitleID.setTabIndex(15);
/* 1736 */         pfmForm.addElement(TitleID);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1742 */         String defaultCode = "";
/* 1743 */         if (selection.getIsDigital() || selection.getReleaseType().getAbbreviation().equalsIgnoreCase("PR")) {
/* 1744 */           defaultCode = "T";
/*      */         }
/* 1746 */         FormDropDownMenu return_code = MilestoneHelper.getPfmLookupDropDown("return_code", Cache.getReturnCodes(), defaultCode, false, true);
/* 1747 */         return_code.setDisplayName("Return/Scrap Code");
/* 1748 */         return_code.setTabIndex(23);
/* 1749 */         pfmForm.addElement(return_code);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1755 */         String releaseDate = "";
/* 1756 */         if (!selection.getIsDigital() && selection.getStreetDate() != null) {
/*      */           
/* 1758 */           releaseDate = MilestoneHelper.getFormatedDate(selection.getStreetDate());
/*      */         }
/* 1760 */         else if (selection.getIsDigital() && selection.getDigitalRlsDate() != null) {
/*      */           
/* 1762 */           releaseDate = MilestoneHelper.getFormatedDate(selection.getDigitalRlsDate());
/*      */         } 
/*      */         
/* 1765 */         FormTextField ReleaseDate = new FormTextField("releaseDate", releaseDate, false, 15);
/* 1766 */         ReleaseDate.setId("Release Date");
/* 1767 */         ReleaseDate.setTabIndex(17);
/* 1768 */         pfmForm.addElement(ReleaseDate);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1773 */         FormHidden status = new FormHidden("status", SelectionManager.getLookupObjectValue(selection.getSelectionStatus()));
/* 1774 */         pfmForm.addElement(status);
/*      */ 
/*      */ 
/*      */         
/* 1778 */         FormDropDownMenu export_flag = MilestoneHelper.getPfmLookupDropDown("export_flag", Cache.getExportFlags(), "", false, true);
/* 1779 */         export_flag.setTabIndex(24);
/* 1780 */         pfmForm.addElement(export_flag);
/*      */ 
/*      */         
/* 1783 */         String superLabelText = "";
/* 1784 */         if (selection.getSuperLabel() != null) {
/* 1785 */           superLabelText = selection.getSuperLabel();
/*      */         }
/* 1787 */         FormTextField super_label = new FormTextField("super_label", superLabelText, false, 50);
/* 1788 */         super_label.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 1789 */         super_label.setTabIndex(19);
/* 1790 */         pfmForm.addElement(super_label);
/*      */ 
/*      */         
/* 1793 */         FormTextField countries = new FormTextField("countries", "", false, 20, 50);
/* 1794 */         countries.setTabIndex(25);
/* 1795 */         pfmForm.addElement(countries);
/*      */ 
/*      */         
/* 1798 */         String labelCodeText = "";
/* 1799 */         if (selection.getSubLabel() != null) {
/* 1800 */           labelCodeText = selection.getSubLabel();
/*      */         }
/* 1802 */         FormTextField label_code = new FormTextField("label_code", labelCodeText, false, 20);
/* 1803 */         label_code.setTabIndex(21);
/* 1804 */         label_code.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 1805 */         pfmForm.addElement(label_code);
/*      */ 
/*      */         
/* 1808 */         FormTextField spine_title = new FormTextField("spine_title", "", false, 37, 37);
/* 1809 */         spine_title.setTabIndex(26);
/* 1810 */         String spineTitleText = "";
/* 1811 */         if (selection != null)
/*      */         {
/* 1813 */           if (selection.getTitle() != null) {
/* 1814 */             spineTitleText = (selection.getTitle().length() > 37) ? selection.getTitle().substring(0, 37) : selection.getTitle();
/*      */           }
/*      */         }
/*      */         
/* 1818 */         spine_title.setValue(spineTitleText);
/* 1819 */         spine_title.setStartingValue(spineTitleText);
/* 1820 */         pfmForm.addElement(spine_title);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1827 */         String companyCodeText = "";
/* 1828 */         if (selection.getIsDigital() && (
/* 1829 */           MilestoneHelper.getStructureName(selection.getReleaseFamilyId()).equals("Canada") || 
/* 1830 */           MilestoneHelper.getStructureName(selection.getReleaseFamilyId()).equals("Mexico")))
/* 1831 */           companyCodeText = "1010"; 
/* 1832 */         FormDropDownMenu company_code = MilestoneHelper.getPfmLookupDropDown("company_code", Cache.getCompanyCodes(), companyCodeText, false, true);
/* 1833 */         company_code.addFormEvent("onKeyPress", "scanKey(window.event.keyCode, 'document.all.company_code.options', getMaxLength(document.all.company_code.options))");
/* 1834 */         company_code.setTabIndex(13);
/* 1835 */         pfmForm.addElement(company_code);
/*      */ 
/*      */ 
/*      */         
/* 1839 */         FormTextField spine_artist = new FormTextField("spine_artist", "", false, 37, 37);
/* 1840 */         spine_artist.setTabIndex(27);
/*      */         
/* 1842 */         String spineArtistText = "";
/* 1843 */         if (selection != null) {
/*      */           
/* 1845 */           if (selection.getArtist() != null)
/*      */           {
/* 1847 */             if (selection.getArtistFirstName().length() > 0)
/* 1848 */               spineArtistText = String.valueOf(selection.getArtistFirstName()) + " "; 
/*      */           }
/* 1850 */           if (selection.getArtistLastName().length() > 0) {
/* 1851 */             spineArtistText = String.valueOf(spineArtistText) + selection.getArtistLastName();
/*      */           }
/* 1853 */           if (spineArtistText.length() > 37) {
/* 1854 */             spineArtistText = spineArtistText.trim().substring(0, 37);
/*      */           }
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1860 */         spine_artist.setValue(spineArtistText.trim());
/*      */ 
/*      */         
/* 1863 */         spine_artist.setStartingValue(spineArtistText.trim());
/* 1864 */         pfmForm.addElement(spine_artist);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1869 */         String projectID = "";
/* 1870 */         if (selection.getProjectID() != null)
/*      */         {
/* 1872 */           projectID = selection.getProjectID();
/*      */         }
/* 1874 */         FormTextField ProjectID = new FormTextField("projectID", projectID, false, 50);
/* 1875 */         ProjectID.setTabIndex(25);
/* 1876 */         pfmForm.addElement(ProjectID);
/*      */ 
/*      */         
/* 1879 */         String priceCode = "";
/* 1880 */         if (selection.getSellCode() != null && !selection.getSellCode().equals("-1"))
/* 1881 */           priceCode = selection.getSellCode(); 
/* 1882 */         FormTextField price_code = new FormTextField("price_code", priceCode, false, 20, 50);
/* 1883 */         price_code.setTabIndex(25);
/* 1884 */         price_code.setReadOnly(true);
/* 1885 */         pfmForm.addElement(price_code);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1891 */         if (!selection.getIsDigital()) {
/*      */           
/* 1893 */           String priceCodeDPC = "";
/* 1894 */           if (selection.getSellCodeDPC() != null && !selection.getSellCodeDPC().equals("-1"))
/* 1895 */             priceCodeDPC = selection.getSellCodeDPC(); 
/* 1896 */           FormTextField price_codeDPC = new FormTextField("price_codeDPC", priceCodeDPC, false, 20, 50);
/* 1897 */           price_codeDPC.setTabIndex(25);
/* 1898 */           price_codeDPC.setReadOnly(true);
/* 1899 */           price_codeDPC.setDisplayName("Dig. Price Code");
/* 1900 */           pfmForm.addElement(price_codeDPC);
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1905 */         FormDropDownMenu po_merge_code = MilestoneHelper.getPfmLookupDropDown("po_merge_code", Cache.getPoMergeCodes(), "99", false, true);
/* 1906 */         po_merge_code.setDisplayName("PO Merge Code");
/* 1907 */         po_merge_code.setTabIndex(14);
/* 1908 */         pfmForm.addElement(po_merge_code);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1913 */         FormDropDownMenu guarantee_code = MilestoneHelper.getPfmLookupDropDown("guarantee_code", Cache.getGuaranteeCodes(), "N", false, true);
/* 1914 */         guarantee_code.setDisplayName("IMI Exempt Code");
/* 1915 */         guarantee_code.setTabIndex(29);
/* 1916 */         pfmForm.addElement(guarantee_code);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1921 */         FormDropDownMenu loose_pick_exempt = MilestoneHelper.getPfmLookupDropDown("loose_pick_exempt", Cache.getLoosePickExempt(), "N", false, true);
/* 1922 */         loose_pick_exempt.setDisplayName("Loose Pick Exempt");
/* 1923 */         loose_pick_exempt.setTabIndex(28);
/* 1924 */         pfmForm.addElement(loose_pick_exempt);
/*      */ 
/*      */         
/* 1927 */         FormDropDownMenu compilation_code = MilestoneHelper.getPfmLookupDropDown("compilation_code", Cache.getCompilationCodes(), "", false, true);
/* 1928 */         compilation_code.setTabIndex(34);
/* 1929 */         pfmForm.addElement(compilation_code);
/*      */ 
/*      */         
/* 1932 */         FormCheckBox valueAdded = new FormCheckBox("ValueAdded", "on", "", false, false);
/* 1933 */         valueAdded.setTabIndex(36);
/* 1934 */         pfmForm.addElement(valueAdded);
/*      */ 
/*      */         
/* 1937 */         FormCheckBox parentalAdv = new FormCheckBox("ParentalAdv", "on", "", false, selection.getParentalGuidance());
/* 1938 */         parentalAdv.setTabIndex(37);
/* 1939 */         parentalAdv.setEnabled(false);
/* 1940 */         pfmForm.addElement(parentalAdv);
/*      */ 
/*      */         
/* 1943 */         FormCheckBox boxSet = new FormCheckBox("BoxSet", "on", "", false, false);
/* 1944 */         boxSet.setTabIndex(35);
/* 1945 */         boxSet.setId("Box Set");
/* 1946 */         pfmForm.addElement(boxSet);
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
/* 1963 */         FormTextField units_per_set = new FormTextField("units_per_set", "", false, 20, 20);
/* 1964 */         units_per_set.setDisplayName("1");
/* 1965 */         units_per_set.setTabIndex(15);
/* 1966 */         units_per_set.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/*      */         
/* 1968 */         units_per_set.addFormEvent("onKeyPress", "return checkNumbersOnly(window.event.keyCode);");
/* 1969 */         pfmForm.addElement(units_per_set);
/*      */ 
/*      */         
/* 1972 */         FormDropDownMenu imp_rate_code = MilestoneHelper.getPfmLookupDropDown("imp_rate_code", Cache.getImpRateCodes(), "", false, true);
/* 1973 */         imp_rate_code.addFormEvent("onKeyPress", "scanKey(window.event.keyCode, 'document.all.imp_rate_code.options', getMaxLength(document.all.imp_rate_code.options))");
/* 1974 */         imp_rate_code.setTabIndex(31);
/* 1975 */         pfmForm.addElement(imp_rate_code);
/*      */ 
/*      */ 
/*      */         
/* 1979 */         FormTextField sets_per_carton = new FormTextField("sets_per_carton", "", false, 20, 20);
/* 1980 */         sets_per_carton.setDisplayName("Sets Per Carton");
/* 1981 */         sets_per_carton.setTabIndex(16);
/*      */         
/* 1983 */         sets_per_carton.addFormEvent("onKeyPress", "return checkNumbersOnly(window.event.keyCode);");
/* 1984 */         pfmForm.addElement(sets_per_carton);
/*      */ 
/*      */         
/* 1987 */         String musicType = "";
/* 1988 */         if (selection != null) {
/* 1989 */           musicType = SelectionManager.getLookupObjectValue(selection.getGenre());
/*      */         }
/*      */         
/* 1992 */         FormDropDownMenu music_type = MilestoneHelper.getPfmLookupDropDown("music_type", Cache.getMusicTypes(), musicType, false, true);
/* 1993 */         music_type.setTabIndex(30);
/*      */         
/* 1995 */         music_type.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 1996 */         music_type.addFormEvent("onKeyPress", "scanKey(window.event.keyCode, 'document.all.music_type.options', getMaxLength(document.all.music_type.options))");
/*      */         
/* 1998 */         pfmForm.addElement(music_type);
/*      */ 
/*      */         
/* 2001 */         context.putDelivery("music_type", musicType);
/*      */ 
/*      */         
/* 2004 */         FormDropDownMenu supplier = MilestoneHelper.getPfmLookupDropDown("suppliers", Cache.getSuppliers(), "", false, true);
/* 2005 */         supplier.setTabIndex(17);
/* 2006 */         supplier.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2007 */         pfmForm.addElement(supplier);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2012 */         FormDropDownMenu narm = MilestoneHelper.getPfmLookupDropDown("NARM", Cache.getNarmExtracts(), "Y", false, true);
/* 2013 */         narm.setDisplayName("Narm Extract Ind.");
/* 2014 */         narm.setTabIndex(33);
/* 2015 */         narm.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2016 */         pfmForm.addElement(narm);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2021 */         String defaultEncryption = "N";
/*      */ 
/*      */ 
/*      */         
/* 2025 */         FormDropDownMenu encryptionFlag = MilestoneHelper.getPfmLookupDropDown("encryptionFlag", Cache.getEncryptionFlags(), defaultEncryption, false, true);
/* 2026 */         encryptionFlag.setTabIndex(38);
/* 2027 */         encryptionFlag.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2028 */         pfmForm.addElement(encryptionFlag);
/*      */ 
/*      */ 
/*      */         
/* 2032 */         String names = "";
/*      */         
/* 2034 */         Vector importIndicators = Cache.getImportIndicators();
/*      */         
/* 2036 */         String[] abbreviation = new String[importIndicators.size()];
/*      */ 
/*      */ 
/*      */         
/* 2040 */         for (int i = importIndicators.size() - 1; i > -1; i--) {
/*      */           
/* 2042 */           LookupObject lookupObject = (LookupObject)importIndicators.elementAt(i);
/* 2043 */           names = String.valueOf(names) + lookupObject.getName();
/* 2044 */           if (i > 0)
/*      */           {
/* 2046 */             names = String.valueOf(names) + ",";
/*      */           }
/* 2048 */           abbreviation[i] = lookupObject.getAbbreviation();
/*      */         } 
/*      */ 
/*      */         
/* 2052 */         String defaultName = "US Made";
/* 2053 */         if (selection.getIsDigital() && (
/* 2054 */           MilestoneHelper.getStructureName(selection.getReleaseFamilyId()).equals("Canada") || 
/* 2055 */           MilestoneHelper.getStructureName(selection.getReleaseFamilyId()).equals("Mexico")))
/* 2056 */           defaultName = "Import"; 
/* 2057 */         FormRadioButtonGroup ImportIndicator = new FormRadioButtonGroup("ImportIndicator", defaultName, names, false);
/* 2058 */         ImportIndicator.setTabIndex(18);
/* 2059 */         ImportIndicator.setId("Imp. Ind");
/* 2060 */         ImportIndicator.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2061 */         pfmForm.addElement(ImportIndicator);
/*      */ 
/*      */ 
/*      */         
/* 2065 */         String ppTxt = "";
/* 2066 */         if (selection.getIsDigital()) {
/* 2067 */           ppTxt = "PS";
/*      */         }
/* 2069 */         FormDropDownMenu price_point = MilestoneHelper.getPfmLookupDropDown("price_point", Cache.getPricePoints(), ppTxt, false, true);
/* 2070 */         price_point.setTabIndex(32);
/* 2071 */         price_point.addFormEvent("onKeyPress", "scanKey(window.event.keyCode, 'document.all.price_point.options', getMaxLength(document.all.price_point.options))");
/* 2072 */         price_point.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2073 */         pfmForm.addElement(price_point);
/*      */ 
/*      */         
/* 2076 */         FormTextField lastUpdated = new FormTextField("lastUpdatedDate", false, 50);
/* 2077 */         lastUpdated.setValue("");
/* 2078 */         lastUpdated.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2079 */         pfmForm.addElement(lastUpdated);
/*      */ 
/*      */         
/* 2082 */         FormTextField lastLegacyUpdateDate = new FormTextField("lastLegacyUpdateDate", false, 50);
/* 2083 */         lastLegacyUpdateDate.setValue("");
/* 2084 */         pfmForm.addElement(lastLegacyUpdateDate);
/*      */ 
/*      */         
/* 2087 */         FormTextField lastUpdatedBy = new FormTextField("lastUpdatedBy", false, 50);
/* 2088 */         lastUpdatedBy.setValue("");
/* 2089 */         lastUpdatedBy.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2090 */         pfmForm.addElement(lastUpdatedBy);
/*      */ 
/*      */         
/* 2093 */         FormTextField approvedBy = new FormTextField("approvedBy", false, 50);
/* 2094 */         approvedBy.setValue("");
/* 2095 */         approvedBy.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2096 */         pfmForm.addElement(approvedBy);
/*      */ 
/*      */         
/* 2099 */         FormTextField enteredBy = new FormTextField("enteredBy", false, 50);
/* 2100 */         enteredBy.setValue("");
/* 2101 */         enteredBy.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2102 */         pfmForm.addElement(enteredBy);
/*      */ 
/*      */         
/* 2105 */         FormTextField verifiedBy = new FormTextField("verifiedBy", false, 50);
/* 2106 */         verifiedBy.setValue("");
/* 2107 */         verifiedBy.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2108 */         pfmForm.addElement(verifiedBy);
/*      */ 
/*      */ 
/*      */         
/* 2112 */         String changeNumberText = "-1";
/* 2113 */         FormTextField ChangeNumber = new FormTextField("ChangeNumber", changeNumberText, false, 2, 2);
/* 2114 */         ChangeNumber.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/*      */ 
/*      */         
/* 2117 */         ChangeNumber.setEnabled(false);
/* 2118 */         ChangeNumber.setId("Change #");
/* 2119 */         ChangeNumber.setTabIndex(6);
/* 2120 */         pfmForm.addElement(ChangeNumber);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2130 */     pfmForm = addSelectionSearchElements(context, selection, pfmForm);
/*      */ 
/*      */     
/* 2133 */     if (context.getSessionValue("NOTEPAD_PFM_VISIBLE") != null) {
/* 2134 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_PFM_VISIBLE"));
/*      */     }
/* 2136 */     return pfmForm;
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
/* 2149 */     context.putDelivery("selectionArrays", String.valueOf(Cache.getJavaScriptConfigArray("")) + Cache.getJavaScriptSubConfigArray("") + " " + ReleasingFamily.getJavaScriptCorporateArrayReleasingFamilySearch(context));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2155 */     FormCheckBox showAllSearch = new FormCheckBox("ShowAllSearch", "", "", false);
/* 2156 */     showAllSearch.setId("ShowAllSearch");
/* 2157 */     form.addElement(showAllSearch);
/*      */ 
/*      */     
/* 2160 */     Vector companies = MilestoneHelper.getUserCompanies(context);
/* 2161 */     Vector labels = MilestoneHelper.getUserLabels(companies);
/*      */ 
/*      */     
/* 2164 */     labels = MilestoneHelper.removeUnusedCSO(labels, context, -1);
/*      */     
/* 2166 */     FormDropDownMenu labelSearch = MilestoneHelper.getCorporateStructureDropDownDuplicates("LabelSearch", labels, "", false, true, false);
/* 2167 */     labelSearch.setId("LabelSearch");
/* 2168 */     form.addElement(labelSearch);
/*      */ 
/*      */     
/* 2171 */     Vector searchCompanies = null;
/*      */ 
/*      */ 
/*      */     
/* 2175 */     searchCompanies = MilestoneHelper.getUserCompanies(context);
/*      */ 
/*      */     
/* 2178 */     searchCompanies = MilestoneHelper.removeUnusedCSO(searchCompanies, context, -1);
/*      */     
/* 2180 */     FormDropDownMenu companySearch = MilestoneHelper.getCorporateStructureDropDown("CompanySearch", searchCompanies, "", false, true);
/* 2181 */     companySearch.setId("CompanySearch");
/* 2182 */     companySearch.addFormEvent("onChange", "return(clickCompanySearch(this))");
/* 2183 */     form.addElement(companySearch);
/*      */ 
/*      */     
/* 2186 */     Vector labelContacts = SelectionManager.getLabelContactsExcludeUml(context);
/* 2187 */     FormDropDownMenu searchContact = MilestoneHelper.getContactsDropDown(context, "ContactSearch", labelContacts, null, true);
/* 2188 */     form.addElement(searchContact);
/*      */ 
/*      */     
/* 2191 */     Vector families = SelectionHandler.filterCSO(ReleasingFamily.getUserReleasingFamiliesVectorOfFamilies(context));
/* 2192 */     FormDropDownMenu Family = MilestoneHelper.getCorporateStructureDropDown("FamilySearch", families, "-1", false, true);
/* 2193 */     Family.addFormEvent("onChange", "return(clickFamilySearch(this))");
/* 2194 */     Family.setId("FamilySearch");
/* 2195 */     form.addElement(Family);
/*      */ 
/*      */     
/* 2198 */     Vector environments = MilestoneHelper.getUserEnvironments(context);
/* 2199 */     Vector myCompanies = MilestoneHelper.getUserCompanies(context);
/* 2200 */     environments = SelectionHandler.filterSelectionEnvironments(myCompanies);
/*      */ 
/*      */     
/* 2203 */     environments = MilestoneHelper.removeUnusedCSO(environments, context, -1);
/*      */ 
/*      */     
/* 2206 */     FormDropDownMenu envMenu = MilestoneHelper.getCorporateStructureDropDown("EnvironmentSearch", environments, "-1", false, true);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2211 */     envMenu.addFormEvent("onChange", "return(clickEnvironmentSearch(this))");
/* 2212 */     envMenu.setId("EnvironmentSearch");
/* 2213 */     form.addElement(envMenu);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2218 */     FormTextField streetDateSearch = new FormTextField("StreetDateSearch", "", false, 14, 10);
/* 2219 */     streetDateSearch.addFormEvent("onBlur", "JavaScript:removeSpacesInDate(document.forms[0].StreetDateSearch.value,this)");
/* 2220 */     streetDateSearch.setId("StreetDateSearch");
/* 2221 */     form.addElement(streetDateSearch);
/*      */ 
/*      */     
/* 2224 */     FormTextField streetEndDateSearch = new FormTextField("StreetEndDateSearch", "", false, 14, 10);
/* 2225 */     streetEndDateSearch.addFormEvent("onBlur", "JavaScript:removeSpacesInDate(document.forms[0].StreetEndDateSearch.value,this)");
/* 2226 */     streetEndDateSearch.setId("StreetEndDateSearch");
/* 2227 */     form.addElement(streetEndDateSearch);
/*      */ 
/*      */     
/* 2230 */     String[] dvalues = new String[3];
/* 2231 */     dvalues[0] = "physical";
/* 2232 */     dvalues[1] = "digital";
/* 2233 */     dvalues[2] = "both";
/*      */     
/* 2235 */     String[] dlabels = new String[3];
/* 2236 */     dlabels[0] = "Physical";
/* 2237 */     dlabels[1] = "Digital";
/* 2238 */     dlabels[2] = "Both";
/*      */     
/* 2240 */     FormRadioButtonGroup prodType = new FormRadioButtonGroup("ProdType", "both", dvalues, dlabels, false);
/* 2241 */     prodType.addFormEvent("onClick", "buildSearchConfigs(this)");
/* 2242 */     form.addElement(prodType);
/*      */ 
/*      */ 
/*      */     
/* 2246 */     Vector searchConfigs = null;
/* 2247 */     searchConfigs = Cache.getSelectionConfigs();
/* 2248 */     FormDropDownMenu configSearch = MilestoneHelper.getSelectionConfigurationDropDown("ConfigSearch", "", false);
/* 2249 */     configSearch.setId("ConfigSearch");
/* 2250 */     configSearch.addFormEvent("onChange", "buildSearchSubConfigs(this.selectedIndex)");
/* 2251 */     form.addElement(configSearch);
/*      */ 
/*      */ 
/*      */     
/* 2255 */     FormDropDownMenu subconfigSearch = new FormDropDownMenu("SubconfigSearch", "");
/* 2256 */     subconfigSearch.setId("SubconfigSearch");
/* 2257 */     subconfigSearch.setEnabled(false);
/* 2258 */     form.addElement(subconfigSearch);
/*      */ 
/*      */ 
/*      */     
/* 2262 */     FormTextField upcSearch = new FormTextField("UPCSearch", "", false, 20, 20);
/* 2263 */     upcSearch.setId("UPCSearch");
/* 2264 */     form.addElement(upcSearch);
/*      */ 
/*      */     
/* 2267 */     FormTextField prefixSearch = new FormTextField("PrefixSearch", "", false, 6, 5);
/* 2268 */     prefixSearch.setId("PrefixSearch");
/* 2269 */     form.addElement(prefixSearch);
/*      */ 
/*      */     
/* 2272 */     FormTextField selectionSearch = new FormTextField("SelectionSearch", "", false, 12, 20);
/* 2273 */     selectionSearch.setId("SelectionSearch");
/* 2274 */     selectionSearch.setClassName("ctrlMedium");
/* 2275 */     form.addElement(selectionSearch);
/*      */ 
/*      */     
/* 2278 */     FormTextField titleSearch = new FormTextField("TitleSearch", "", false, 20);
/* 2279 */     titleSearch.setId("TitleSearch");
/* 2280 */     form.addElement(titleSearch);
/*      */ 
/*      */     
/* 2283 */     FormTextField artistSearch = new FormTextField("ArtistSearch", "", false, 20);
/* 2284 */     artistSearch.setId("ArtistSearch");
/* 2285 */     form.addElement(artistSearch);
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
/* 2322 */     FormTextField projectIDSearch = new FormTextField("ProjectIDSearch", "", false, 20);
/* 2323 */     projectIDSearch.setId("ProjectIDSearch");
/* 2324 */     form.addElement(projectIDSearch);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2329 */     SelectionHandler.getUserPreferences(form, context);
/*      */ 
/*      */     
/* 2332 */     return form;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean editSave(Dispatcher dispatcher, Context context, String command) {
/* 2338 */     User user = MilestoneSecurity.getUser(context);
/*      */ 
/*      */     
/* 2341 */     Notepad notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 0);
/* 2342 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */     
/* 2344 */     Selection selection = null;
/* 2345 */     Selection selectionTemp = null;
/* 2346 */     int selectionID = -1;
/*      */     
/* 2348 */     if (command.equalsIgnoreCase("selectionSave")) {
/*      */       
/* 2350 */       selectionTemp = (Selection)context.getSessionValue("pfmBomSelection");
/* 2351 */       selection = SelectionManager.getInstance().getSelectionHeader(selectionTemp.getSelectionID());
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */ 
/*      */       
/* 2359 */       selectionID = Integer.parseInt(context.getRequestValue("selectionID"));
/*      */ 
/*      */       
/* 2362 */       selection = SelectionManager.getInstance().getSelectionHeader(selectionID);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2367 */     MessageObject messageObject = new MessageObject();
/*      */     try {
/* 2369 */       messageObject.selectionObj = (Selection)selection.clone();
/* 2370 */     } catch (CloneNotSupportedException ce) {
/* 2371 */       messageObject.selectionObj = selection;
/*      */     } 
/*      */     
/* 2374 */     Pfm pfm = SelectionManager.getInstance().getPfm(selection.getSelectionID());
/*      */     
/* 2376 */     boolean finalFlag = false;
/*      */     
/* 2378 */     boolean sendEmail = false;
/* 2379 */     boolean firstTimeFinal = false;
/*      */     
/* 2381 */     if (pfm != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2387 */       if (context.getSessionValue("originalComment") == null)
/*      */       {
/*      */         
/* 2390 */         context.putSessionValue("originalComment", pfm.getComments());
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 2395 */       Form form = buildForm(context, pfm, command);
/* 2396 */       form.addElement(new FormHidden("cmd", "pfm-edit-save", true));
/* 2397 */       form.addElement(new FormHidden("copyPaste", "copy", true));
/*      */ 
/*      */       
/* 2400 */       long timestamp = pfm.getLastUpdatedCk();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2405 */       if (!command.equalsIgnoreCase("selectionSave")) {
/*      */         
/* 2407 */         String timestampStr = context.getRequestValue("pfmLastUpdateCheck");
/* 2408 */         if (timestampStr != null) {
/* 2409 */           pfm.setLastUpdatedCk(Long.parseLong(timestampStr));
/*      */         }
/*      */       } 
/*      */       
/* 2413 */       if (SelectionManager.getInstance().isTimestampValid(pfm)) {
/*      */ 
/*      */         
/* 2416 */         pfm.setLastUpdatedCk(timestamp);
/*      */ 
/*      */         
/* 2419 */         if (!command.equalsIgnoreCase("selectionSave")) {
/* 2420 */           form.setValues(context);
/*      */         }
/*      */         
/* 2423 */         String sendOption = form.getStringValue("sendOption");
/*      */ 
/*      */         
/* 2426 */         if (!form.isUnchanged()) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2431 */           String printOption = form.getStringValue("printOption");
/* 2432 */           if (printOption.equalsIgnoreCase("Draft")) {
/* 2433 */             pfm.setPrintOption("Draft");
/*      */ 
/*      */ 
/*      */             
/* 2437 */             String mode = form.getStringValue("mode");
/* 2438 */             if (mode.equalsIgnoreCase("Add")) {
/* 2439 */               mode = "Add";
/* 2440 */               pfm.setMode(mode);
/* 2441 */               pfm.setReleaseType("A");
/* 2442 */               pfm.setChangeNumber("-1");
/*      */             } else {
/*      */               
/* 2445 */               pfm.setMode("Change");
/* 2446 */               pfm.setReleaseType("M");
/*      */               
/* 2448 */               pfm.setChangeNumber("-1");
/*      */             } 
/*      */ 
/*      */             
/* 2452 */             pfm.setMode("Add");
/* 2453 */             pfm.setReleaseType("A");
/* 2454 */             pfm.setChangeNumber("-1");
/* 2455 */             FormTextField changeField = (FormTextField)form.getElement("ChangeNumber");
/* 2456 */             changeField.setValue("-1");
/* 2457 */             FormRadioButtonGroup modeField = (FormRadioButtonGroup)form.getElement("mode");
/* 2458 */             modeField.setValue("Add");
/*      */           
/*      */           }
/*      */           else {
/*      */             
/* 2463 */             pfm.setPrintOption("Final");
/*      */             
/* 2465 */             finalFlag = true;
/* 2466 */             sendEmail = false;
/*      */ 
/*      */             
/* 2469 */             int changeNumberInt = -1;
/*      */             
/*      */             try {
/* 2472 */               changeNumberInt = Integer.parseInt(form.getStringValue("ChangeNumber"));
/*      */             }
/* 2474 */             catch (Exception e) {
/* 2475 */               changeNumberInt = -1;
/*      */             } 
/*      */             
/* 2478 */             String newMode = "";
/*      */ 
/*      */             
/* 2481 */             if (changeNumberInt >= 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 2490 */               if (context.getSessionValue("originalComment") != null) {
/* 2491 */                 ((FormTextArea)form.getElement("comments")).setStartingValue((String)context.getSessionValue("originalComment"));
/*      */               }
/* 2493 */               StringBuffer changedFields = new StringBuffer();
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 2498 */               String upcStripped = MilestoneHelper_2.reformat_UPC_SSG_SGC_forSave(form.getStringValue("upc"), "UPC", selection.getIsDigital(), true);
/* 2499 */               form.getElement("upc").setValue(upcStripped);
/* 2500 */               String ssgStripped = MilestoneHelper_2.reformat_UPC_SSG_SGC_forSave(form.getStringValue("sound_scan_code"), "SSG", selection.getIsDigital(), true);
/* 2501 */               form.getElement("sound_scan_code").setValue(ssgStripped);
/*      */ 
/*      */ 
/*      */               
/* 2505 */               if (!command.equalsIgnoreCase("pfm-edit-save-comment") && 
/* 2506 */                 EmailDistribution.isFormChanged(form.getChangedElements(), changedFields, true, false, form, messageObject))
/*      */               {
/*      */                 
/* 2509 */                 sendEmail = true;
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 2514 */                 if (sendOption != null && sendOption.equalsIgnoreCase("Send Email") && 
/* 2515 */                   !context.getCommand().equals("selection-send-cancel"))
/*      */                 {
/* 2517 */                   changeNumberInt++;
/*      */                 }
/*      */                 
/* 2520 */                 pfm.setMode("Change");
/* 2521 */                 pfm.setReleaseType("M");
/* 2522 */                 newMode = "Change";
/*      */                 
/* 2524 */                 pfm.setChangeNumber(Integer.toString(changeNumberInt));
/* 2525 */                 FormTextField changeField = (FormTextField)form.getElement("ChangeNumber");
/* 2526 */                 changeField.setValue(Integer.toString(changeNumberInt));
/* 2527 */                 FormRadioButtonGroup modeField = (FormRadioButtonGroup)form.getElement("mode");
/* 2528 */                 modeField.setValue(newMode);
/*      */               }
/*      */             
/*      */             }
/*      */             else {
/*      */               
/* 2534 */               firstTimeFinal = true;
/*      */ 
/*      */               
/* 2537 */               sendEmail = true;
/*      */ 
/*      */ 
/*      */               
/* 2541 */               if (!context.getCommand().equals("selection-send-cancel")) {
/* 2542 */                 changeNumberInt++;
/*      */               }
/*      */               
/* 2545 */               pfm.setMode("Add");
/* 2546 */               pfm.setReleaseType("A");
/* 2547 */               newMode = "Add";
/*      */               
/* 2549 */               pfm.setChangeNumber(Integer.toString(changeNumberInt));
/* 2550 */               FormTextField changeField = (FormTextField)form.getElement("ChangeNumber");
/* 2551 */               changeField.setValue(Integer.toString(changeNumberInt));
/* 2552 */               FormRadioButtonGroup modeField = (FormRadioButtonGroup)form.getElement("mode");
/* 2553 */               modeField.setValue(newMode);
/*      */             } 
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2560 */           if (sendOption != null && !sendOption.equalsIgnoreCase("Send Email")) {
/* 2561 */             sendEmail = false;
/*      */           }
/*      */           
/* 2564 */           String preparedBy = form.getStringValue("prepared_by");
/* 2565 */           pfm.setPreparedBy(preparedBy);
/*      */ 
/*      */           
/* 2568 */           String phone = form.getStringValue("phone");
/* 2569 */           pfm.setPhone(phone);
/*      */ 
/*      */           
/* 2572 */           String email = form.getStringValue("email");
/* 2573 */           pfm.setEmail(email);
/*      */ 
/*      */           
/* 2576 */           String fax = form.getStringValue("fax");
/* 2577 */           pfm.setFaxNumber(fax);
/*      */ 
/*      */           
/* 2580 */           String comments = form.getStringValue("comments");
/* 2581 */           pfm.setComments(comments);
/*      */ 
/*      */           
/* 2584 */           String soundScanGroup = form.getStringValue("sound_scan_code");
/* 2585 */           pfm.setSoundScanGrp(soundScanGroup);
/*      */ 
/*      */           
/* 2588 */           String operatingCompany = form.getStringValue("operating_company");
/* 2589 */           pfm.setOperatingCompany(operatingCompany);
/*      */ 
/*      */           
/* 2592 */           String productNumber = form.getStringValue("product_number");
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
/* 2610 */           pfm.setProductNumber(productNumber);
/*      */ 
/*      */           
/* 2613 */           String musicLine = form.getStringValue("music_line");
/* 2614 */           pfm.setMusicLine(musicLine);
/*      */ 
/*      */           
/* 2617 */           String configCode = form.getStringValue("config_code");
/* 2618 */           pfm.setConfigCode(configCode);
/*      */ 
/*      */           
/* 2621 */           String repertoireOwner = form.getStringValue("repertoire_owner");
/* 2622 */           pfm.setRepertoireOwner(repertoireOwner);
/*      */ 
/*      */           
/* 2625 */           String modifier = form.getStringValue("modifier");
/* 2626 */           pfm.setModifier(modifier);
/*      */ 
/*      */           
/* 2629 */           String repClass = repertoireOwner;
/* 2630 */           pfm.setRepertoireClass(repClass);
/*      */ 
/*      */           
/* 2633 */           String upc = form.getStringValue("upc");
/* 2634 */           pfm.setUpc(upc);
/*      */ 
/*      */           
/* 2637 */           String title = form.getStringValue("title");
/* 2638 */           pfm.setTitle(title);
/*      */ 
/*      */           
/* 2641 */           String artist = form.getStringValue("artist");
/* 2642 */           pfm.setArtist(artist);
/*      */ 
/*      */           
/* 2645 */           String titleID = form.getStringValue("titleID");
/* 2646 */           pfm.setTitleId(titleID);
/*      */ 
/*      */ 
/*      */           
/* 2650 */           String returnCode = "T";
/* 2651 */           if (!selection.getIsDigital())
/* 2652 */             returnCode = form.getStringValue("return_code"); 
/* 2653 */           pfm.setReturnCode(returnCode);
/*      */ 
/*      */           
/* 2656 */           String exportFlag = form.getStringValue("export_flag");
/* 2657 */           pfm.setExportFlag(exportFlag);
/*      */ 
/*      */           
/* 2660 */           String superLabel = form.getStringValue("super_label");
/* 2661 */           pfm.setSuperLabel(superLabel);
/*      */ 
/*      */           
/* 2664 */           String countries = form.getStringValue("countries");
/* 2665 */           pfm.setCountries(countries);
/*      */ 
/*      */           
/* 2668 */           String labelCode = form.getStringValue("label_code");
/* 2669 */           pfm.setLabelCode(labelCode);
/*      */ 
/*      */           
/* 2672 */           String spineTitle = form.getStringValue("spine_title");
/* 2673 */           pfm.setSpineTitle(spineTitle);
/*      */ 
/*      */           
/* 2676 */           String companyCode = form.getStringValue("company_code");
/* 2677 */           pfm.setCompanyCode(companyCode);
/*      */ 
/*      */           
/* 2680 */           String spineArtist = form.getStringValue("spine_artist");
/* 2681 */           pfm.setSpineArtist(spineArtist);
/*      */ 
/*      */           
/* 2684 */           String priceCode = (form.getStringValue("price_code") != null) ? form.getStringValue("price_code") : "";
/* 2685 */           pfm.setPriceCode(priceCode);
/*      */ 
/*      */           
/* 2688 */           String priceCodeDPC = (form.getStringValue("price_codeDPC") != null) ? form.getStringValue("price_codeDPC") : "";
/* 2689 */           pfm.setPriceCodeDPC(priceCodeDPC);
/*      */ 
/*      */ 
/*      */           
/* 2693 */           String poMergeCode = "99";
/* 2694 */           if (!selection.getIsDigital())
/* 2695 */             poMergeCode = form.getStringValue("po_merge_code"); 
/* 2696 */           pfm.setPoMergeCode(poMergeCode);
/*      */ 
/*      */ 
/*      */           
/* 2700 */           String guaranteeCode = "N";
/* 2701 */           if (!selection.getIsDigital())
/* 2702 */             guaranteeCode = form.getStringValue("guarantee_code"); 
/* 2703 */           pfm.setGuaranteeCode(guaranteeCode);
/*      */ 
/*      */ 
/*      */           
/* 2707 */           String loosePickExemptCode = "N";
/* 2708 */           if (!selection.getIsDigital())
/* 2709 */             loosePickExemptCode = form.getStringValue("loose_pick_exempt"); 
/* 2710 */           pfm.setLoosePickExemptCode(loosePickExemptCode);
/*      */ 
/*      */           
/* 2713 */           pfm.setValueAdded(((FormCheckBox)form.getElement("ValueAdded"))
/* 2714 */               .isChecked());
/*      */ 
/*      */           
/* 2717 */           pfm.setBoxSet(((FormCheckBox)form.getElement("BoxSet")).isChecked());
/*      */ 
/*      */           
/* 2720 */           String compilation = form.getStringValue("compilation_code");
/* 2721 */           pfm.setCompilationCode(compilation);
/*      */ 
/*      */ 
/*      */           
/* 2725 */           String unitsPerSet = "1";
/* 2726 */           if (!selection.getIsDigital())
/* 2727 */             unitsPerSet = form.getStringValue("units_per_set").trim(); 
/* 2728 */           int units = 0;
/* 2729 */           if (unitsPerSet != null && !unitsPerSet.equals("")) {
/*      */             try {
/* 2731 */               units = Integer.parseInt(unitsPerSet);
/*      */             }
/* 2733 */             catch (NumberFormatException e) {
/* 2734 */               units = 0;
/*      */             } 
/*      */           }
/* 2737 */           pfm.setUnitsPerSet(units);
/*      */ 
/*      */           
/* 2740 */           String impRateCode = form.getStringValue("imp_rate_code");
/* 2741 */           pfm.setImpRateCode(impRateCode);
/*      */ 
/*      */ 
/*      */           
/* 2745 */           String setsPerCarton = "1";
/* 2746 */           if (!selection.getIsDigital())
/* 2747 */             setsPerCarton = form.getStringValue("sets_per_carton").trim(); 
/* 2748 */           int sets = 0;
/* 2749 */           if (setsPerCarton != null && !setsPerCarton.equals("")) {
/*      */             try {
/* 2751 */               sets = Integer.parseInt(setsPerCarton);
/*      */             }
/* 2753 */             catch (NumberFormatException e) {
/* 2754 */               sets = 0;
/*      */             } 
/*      */           }
/* 2757 */           pfm.setSetsPerCarton(sets);
/*      */ 
/*      */           
/* 2760 */           String musicType = form.getStringValue("music_type");
/*      */ 
/*      */ 
/*      */           
/* 2764 */           pfm.setMusicType((Genre)SelectionManager.getLookupObject(musicType, Cache.getMusicTypes()));
/*      */ 
/*      */           
/* 2767 */           String prefix = form.getStringValue("prefix");
/* 2768 */           pfm.setPrefixID((PrefixCode)SelectionManager.getLookupObject(prefix, Cache.getPrefixCodes()));
/*      */ 
/*      */           
/* 2771 */           String selectionNo = form.getStringValue("selectionNo");
/* 2772 */           pfm.setSelectionNo(selectionNo);
/*      */ 
/*      */           
/* 2775 */           String suppliers = form.getStringValue("suppliers");
/* 2776 */           pfm.setSupplier(suppliers);
/*      */ 
/*      */ 
/*      */           
/* 2780 */           String narm = "N";
/* 2781 */           if (!selection.getIsDigital())
/* 2782 */             narm = form.getStringValue("NARM"); 
/* 2783 */           pfm.setNarmFlag(narm);
/*      */ 
/*      */ 
/*      */           
/* 2787 */           String eFlag = form.getStringValue("encryptionFlag");
/* 2788 */           pfm.setEncryptionFlag(eFlag);
/*      */ 
/*      */           
/* 2791 */           String importIndicator = form.getStringValue("ImportIndicator");
/* 2792 */           Vector importIndicators = Cache.getImportIndicators();
/* 2793 */           String[] name = new String[importIndicators.size()];
/* 2794 */           String[] abbreviation = new String[importIndicators.size()];
/*      */           
/* 2796 */           for (int i = 0; i < importIndicators.size(); i++) {
/* 2797 */             LookupObject lookupObject = (LookupObject)importIndicators
/* 2798 */               .elementAt(i);
/* 2799 */             name[i] = lookupObject.getName();
/* 2800 */             abbreviation[i] = lookupObject.getAbbreviation();
/* 2801 */             if (importIndicator.equalsIgnoreCase(name[i])) {
/* 2802 */               importIndicator = abbreviation[i];
/*      */             }
/*      */           } 
/* 2805 */           pfm.setImportIndicator(importIndicator);
/*      */ 
/*      */           
/* 2808 */           String pricePoint = form.getStringValue("price_point");
/*      */           
/* 2810 */           if (selection.getIsDigital())
/* 2811 */             pricePoint = "PS"; 
/* 2812 */           pfm.setPricePoint(pricePoint);
/*      */ 
/*      */           
/* 2815 */           String projectID = form.getStringValue("projectID");
/* 2816 */           pfm.setProjectID(projectID);
/*      */           
/* 2818 */           String streetDateString = form.getStringValue("releaseDate");
/*      */           try {
/* 2820 */             Calendar streetDate = MilestoneHelper.getDate(streetDateString);
/* 2821 */             pfm.setStreetDate(streetDate);
/*      */           }
/* 2823 */           catch (Exception e) {
/* 2824 */             pfm.setStreetDate(null);
/*      */           } 
/*      */ 
/*      */           
/* 2828 */           String strStatus = form.getStringValue("status");
/* 2829 */           pfm.setStatus(strStatus);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2835 */           String projectIDtoValidate = "";
/* 2836 */           if (pfm.getProjectID().trim().indexOf("-") > -1) {
/* 2837 */             for (int j = 1; j < pfm.getProjectID().trim().length(); j++) {
/* 2838 */               if (pfm.getProjectID().trim().charAt(j) != '-') {
/* 2839 */                 projectIDtoValidate = String.valueOf(projectIDtoValidate) + pfm.getProjectID().trim().charAt(j);
/*      */               }
/*      */             } 
/*      */           } else {
/* 2843 */             projectIDtoValidate = pfm.getProjectID().trim();
/*      */           } 
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
/* 2858 */           if (command.equalsIgnoreCase("selectionSave")) {
/* 2859 */             pfm.setParentalGuidance(selection.getParentalGuidance());
/*      */           } else {
/* 2861 */             pfm.setParentalGuidance(((FormCheckBox)form.getElement("ParentalAdv")).isChecked());
/*      */           } 
/* 2863 */           Form emailDistForm = form;
/*      */           
/* 2865 */           form = buildForm(context, pfm, command);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2872 */           if (sendEmail)
/*      */           {
/* 2874 */             if (context.getSessionValue("originalComment") != null) {
/*      */               
/* 2876 */               ((FormTextArea)form.getElement("comments")).setStartingValue((String)context.getSessionValue("originalComment"));
/* 2877 */               ((FormTextArea)emailDistForm.getElement("comments")).setStartingValue((String)context.getSessionValue("originalComment"));
/*      */             } 
/*      */           }
/*      */           
/* 2881 */           form.addElement(new FormHidden("cmd", "pfm-edit-save", true));
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2886 */           FormValidation formValidation = form.validate();
/* 2887 */           if (formValidation.isGood()) {
/*      */ 
/*      */ 
/*      */             
/* 2891 */             Pfm savedPfm = SelectionManager.getInstance().savePfm(pfm, user, selection.getIsDigital());
/*      */ 
/*      */ 
/*      */             
/* 2895 */             if (selection.getIsDigital()) {
/*      */               
/* 2897 */               SelectionManager.getInstance().saveSelectionDataFromDigitalPfm(
/* 2898 */                   pfm.getReleaseId(), 
/* 2899 */                   pfm.getPrefixID(), 
/* 2900 */                   pfm.getSelectionNo(), 
/*      */                   
/* 2902 */                   MilestoneHelper_2.reformat_UPC_SSG_SGC_forSave(pfm.getUpc(), "UPC", selection.getIsDigital(), true), 
/* 2903 */                   pfm.getConfigCode(), 
/*      */                   
/* 2905 */                   MilestoneHelper_2.reformat_UPC_SSG_SGC_forSave(pfm.getSoundScanGrp(), "SSG", selection.getIsDigital(), true));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 2911 */               if (messageObject != null && messageObject.selectionObj != null) {
/*      */                 
/* 2913 */                 messageObject.selectionObj.setUpc(pfm.getUpc());
/* 2914 */                 messageObject.selectionObj.setConfigCode(pfm.getConfigCode());
/* 2915 */                 messageObject.selectionObj.setSoundScanGrp(pfm.getSoundScanGrp());
/*      */               } 
/*      */             } 
/*      */ 
/*      */ 
/*      */             
/* 2921 */             FormElement lastUpdated = form.getElement("lastUpdatedDate");
/* 2922 */             lastUpdated.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 2923 */             lastUpdated.setValue(MilestoneHelper.getLongDate(savedPfm.getLastUpdatedDate()));
/*      */             
/* 2925 */             context.putSessionValue("Pfm", savedPfm);
/* 2926 */             context.putDelivery("Form", form);
/*      */ 
/*      */             
/* 2929 */             notepad.setAllContents(null);
/* 2930 */             notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 0);
/* 2931 */             notepad.goToSelectedPage();
/*      */             
/* 2933 */             Boolean IsSaveSend = Boolean.valueOf(false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 2939 */             if ((finalFlag && 
/* 2940 */               !context.getCommand().equals("selection-send-cancel") && 
/* 2941 */               !command.equalsIgnoreCase("pfm-edit-save-comment")) || firstTimeFinal) {
/* 2942 */               String lastUpdatedDate = "";
/* 2943 */               String lastUpdatedBy = "";
/*      */               
/* 2945 */               if (savedPfm.getLastUpdatedDate() != null) {
/* 2946 */                 lastUpdatedDate = MilestoneHelper.getCustomFormatedDate(savedPfm.getLastUpdatedDate(), "M/d/yyyy hh:mm:ss a 'ET'");
/*      */               }
/* 2948 */               if (UserManager.getInstance().getUser(savedPfm.getLastUpdatingUser()) != null) {
/* 2949 */                 lastUpdatedBy = UserManager.getInstance().getUser(savedPfm.getLastUpdatingUser()).getName();
/*      */               }
/*      */               
/* 2952 */               if (sendEmail && (EmailDistribution.putEmailBody(emailDistForm, context, selection, lastUpdatedDate, lastUpdatedBy, "PFM", messageObject) || 
/* 2953 */                 messageObject.IsPushOnDemand)) {
/*      */ 
/*      */                 
/*      */                 try {
/*      */                   
/* 2958 */                   messageObject.pfmObj = (Pfm)savedPfm.clone();
/* 2959 */                 } catch (CloneNotSupportedException ce) {
/*      */                   
/* 2961 */                   messageObject.pfmObj = savedPfm;
/*      */                 } 
/* 2963 */                 print(dispatcher, context, command, 2, true, messageObject);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 2969 */                 if (messageObject.IsPushPFM || messageObject.IsPushOnDemand) {
/* 2970 */                   PushCommunication.pushPFMLegacyAppendMessge(messageObject, PushCommunication.pushPFMLegacy(pfm, selection, context));
/*      */                 }
/* 2972 */                 EmailDistribution.sendEmail(dispatcher, context, "", messageObject);
/* 2973 */                 EmailDistribution.removeSessionValues(context);
/*      */                 
/* 2975 */                 IsSaveSend = Boolean.valueOf(true);
/*      */               } 
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 2987 */             DcGDRSResults dcGDRSResults = SelectionHandler.GDRSProductStatusGet(selection, selection.getCompany().getParentEnvironment().getStructureID());
/* 2988 */             boolean IsGDRSactive = (!dcGDRSResults.getStatus().equals("") && !dcGDRSResults.getStatus().equals("DELETE"));
/* 2989 */             if (!selection.getNoDigitalRelease()) {
/*      */ 
/*      */               
/* 2992 */               if (!dcGDRSResults.getForceNoDigitalRelease().booleanValue())
/*      */               {
/* 2994 */                 String status = IsSaveSend.booleanValue() ? "SAVE_SEND" : "CREATE_EDIT";
/* 2995 */                 SelectionManager.GDRS_QueueAddReleaseId(selection, status);
/*      */               }
/*      */             
/* 2998 */             } else if (IsGDRSactive) {
/* 2999 */               SelectionManager.GDRS_QueueAddReleaseId(selection, "DELETE");
/*      */             } 
/*      */ 
/*      */ 
/*      */             
/* 3004 */             if (!command.equalsIgnoreCase("pfm-edit-save-comment")) {
/* 3005 */               context.putSessionValue("originalComment", savedPfm.getComments());
/*      */             }
/* 3007 */             context.putSessionValue("originalCommentRelId", new Integer(savedPfm.getReleaseId()));
/*      */           }
/*      */           else {
/*      */             
/* 3011 */             context.putDelivery("FormValidation", formValidation);
/*      */           } 
/*      */         } 
/* 3014 */         form.addElement(new FormHidden("OrderBy", "", true));
/* 3015 */         context.putDelivery("Form", form);
/*      */         
/* 3017 */         if (!command.equalsIgnoreCase("selectionSave")) {
/* 3018 */           return edit(dispatcher, context, command);
/*      */         }
/* 3020 */         return true;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 3025 */       context.putDelivery("AlertMessage", "The record is unavailable for editing at this time.  Please refresh your screen and edit it at a later time.");
/*      */ 
/*      */       
/* 3028 */       context.putDelivery("Form", form);
/*      */       
/* 3030 */       if (!command.equalsIgnoreCase("selectionSave")) {
/* 3031 */         return context.includeJSP("pfm-editor.jsp");
/*      */       }
/* 3033 */       return true;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 3038 */     Form form = buildForm(context, null, command);
/* 3039 */     form.addElement(new FormHidden("cmd", "pfm-edit-save", true));
/* 3040 */     form.addElement(new FormHidden("copyPaste", "copy", true));
/*      */ 
/*      */     
/* 3043 */     if (!command.equalsIgnoreCase("selectionSave")) {
/* 3044 */       form.setValues(context);
/*      */     }
/* 3046 */     pfm = new Pfm();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3051 */     String printOption = form.getStringValue("printOption");
/* 3052 */     if (printOption.equalsIgnoreCase("Draft")) {
/* 3053 */       pfm.setPrintOption("Draft");
/*      */ 
/*      */ 
/*      */       
/* 3057 */       String mode = form.getStringValue("mode");
/* 3058 */       if (!mode.equalsIgnoreCase("Add")) {
/* 3059 */         mode = "Change";
/* 3060 */         pfm.setMode(mode);
/* 3061 */         pfm.setReleaseType("M");
/*      */       
/*      */       }
/*      */       else {
/*      */         
/* 3066 */         pfm.setMode(mode);
/* 3067 */         pfm.setReleaseType("A");
/*      */         
/* 3069 */         pfm.setChangeNumber("-1");
/*      */       } 
/*      */ 
/*      */       
/* 3073 */       pfm.setMode("Add");
/* 3074 */       pfm.setReleaseType("A");
/* 3075 */       pfm.setChangeNumber("-1");
/* 3076 */       FormTextField changeField = (FormTextField)form.getElement("ChangeNumber");
/* 3077 */       changeField.setValue("-1");
/* 3078 */       FormRadioButtonGroup modeField = (FormRadioButtonGroup)form.getElement("mode");
/* 3079 */       modeField.setValue("Add");
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 3084 */       pfm.setPrintOption("Final");
/*      */       
/* 3086 */       finalFlag = true;
/* 3087 */       sendEmail = false;
/*      */ 
/*      */       
/* 3090 */       int changeNumberInt = -1;
/*      */       
/*      */       try {
/* 3093 */         changeNumberInt = Integer.parseInt(form.getStringValue("ChangeNumber"));
/*      */       }
/* 3095 */       catch (Exception e) {
/* 3096 */         changeNumberInt = -1;
/*      */       } 
/*      */       
/* 3099 */       String newMode = "";
/*      */ 
/*      */       
/* 3102 */       if (changeNumberInt >= 0) {
/*      */         
/* 3104 */         StringBuffer changedFields = new StringBuffer();
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
/* 3115 */         String upcStripped = MilestoneHelper_2.reformat_UPC_SSG_SGC_forSave(form.getStringValue("upc"), "UPC", selection.getIsDigital(), true);
/* 3116 */         form.getElement("upc").setValue(upcStripped);
/* 3117 */         String ssgStripped = MilestoneHelper_2.reformat_UPC_SSG_SGC_forSave(form.getStringValue("sound_scan_code"), "SSG", selection.getIsDigital(), true);
/* 3118 */         form.getElement("sound_scan_code").setValue(ssgStripped);
/*      */         
/* 3120 */         if (!command.equalsIgnoreCase("pfm-edit-save-comment") && 
/* 3121 */           EmailDistribution.isFormChanged(form.getChangedElements(), changedFields, true, false, form, messageObject))
/*      */         {
/* 3123 */           sendEmail = true;
/*      */           
/* 3125 */           changeNumberInt++;
/*      */           
/* 3127 */           pfm.setMode("Change");
/* 3128 */           pfm.setReleaseType("M");
/* 3129 */           newMode = "Change";
/*      */           
/* 3131 */           pfm.setChangeNumber(Integer.toString(changeNumberInt));
/* 3132 */           FormTextField changeField = (FormTextField)form.getElement("ChangeNumber");
/* 3133 */           changeField.setValue(Integer.toString(changeNumberInt));
/* 3134 */           FormRadioButtonGroup modeField = (FormRadioButtonGroup)form.getElement("mode");
/* 3135 */           modeField.setValue(newMode);
/*      */         }
/*      */       
/*      */       }
/*      */       else {
/*      */         
/* 3141 */         firstTimeFinal = true;
/*      */ 
/*      */ 
/*      */         
/* 3145 */         sendEmail = true;
/*      */         
/* 3147 */         changeNumberInt++;
/*      */         
/* 3149 */         pfm.setMode("Add");
/* 3150 */         pfm.setReleaseType("A");
/* 3151 */         newMode = "Add";
/*      */         
/* 3153 */         pfm.setChangeNumber(Integer.toString(changeNumberInt));
/* 3154 */         FormTextField changeField = (FormTextField)form.getElement("ChangeNumber");
/* 3155 */         changeField.setValue(Integer.toString(changeNumberInt));
/* 3156 */         FormRadioButtonGroup modeField = (FormRadioButtonGroup)form.getElement("mode");
/* 3157 */         modeField.setValue(newMode);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 3162 */     if (form.getStringValue("selectionID") != null) {
/*      */       
/*      */       try {
/*      */         
/* 3166 */         selectionID = Integer.parseInt(form.getStringValue("selectionID"));
/*      */       }
/* 3168 */       catch (NumberFormatException e) {
/*      */         
/* 3170 */         selectionID = -1;
/*      */       } 
/*      */     }
/* 3173 */     pfm.setReleaseId(selectionID);
/*      */ 
/*      */     
/* 3176 */     String preparedBy = "";
/* 3177 */     if (form.getStringValue("prepared_by") != null)
/*      */     {
/* 3179 */       preparedBy = form.getStringValue("prepared_by");
/*      */     }
/* 3181 */     pfm.setPreparedBy(preparedBy);
/*      */ 
/*      */     
/* 3184 */     String phone = form.getStringValue("phone");
/* 3185 */     pfm.setPhone(phone);
/*      */ 
/*      */     
/* 3188 */     String email = form.getStringValue("email");
/* 3189 */     pfm.setEmail(email);
/*      */ 
/*      */     
/* 3192 */     String fax = "";
/* 3193 */     if (form.getStringValue("fax") != null)
/*      */     {
/* 3195 */       fax = form.getStringValue("fax");
/*      */     }
/* 3197 */     pfm.setFaxNumber(fax);
/*      */ 
/*      */     
/* 3200 */     String comments = "";
/* 3201 */     if (form.getStringValue("comments") != null)
/*      */     {
/* 3203 */       comments = form.getStringValue("comments");
/*      */     }
/* 3205 */     pfm.setComments(comments);
/*      */ 
/*      */     
/* 3208 */     String mode = "";
/* 3209 */     if (form.getStringValue("mode") != null) {
/*      */       
/* 3211 */       mode = form.getStringValue("mode");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3219 */       if (!mode.equalsIgnoreCase("Add")) {
/*      */ 
/*      */         
/* 3222 */         pfm.setMode(mode);
/* 3223 */         pfm.setReleaseType("M");
/*      */       }
/*      */       else {
/*      */         
/* 3227 */         pfm.setMode(mode);
/* 3228 */         pfm.setReleaseType("A");
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 3233 */     String soundScanGroup = "";
/*      */     
/* 3235 */     if (selection.getIsDigital()) {
/* 3236 */       soundScanGroup = form.getStringValue("sound_scan_code");
/*      */     } else {
/* 3238 */       soundScanGroup = selection.getSoundScanGrp();
/*      */     } 
/* 3240 */     pfm.setSoundScanGrp(soundScanGroup);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3245 */     String operatingCompany = form.getStringValue("operating_company");
/* 3246 */     pfm.setOperatingCompany(operatingCompany);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3252 */     String productNumber = form.getStringValue("product_number");
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
/* 3270 */     pfm.setProductNumber(productNumber);
/*      */ 
/*      */     
/* 3273 */     String musicLine = "";
/* 3274 */     if (form.getStringValue("music_line") != null)
/*      */     {
/* 3276 */       musicLine = form.getStringValue("music_line");
/*      */     }
/* 3278 */     pfm.setMusicLine(musicLine);
/*      */ 
/*      */     
/* 3281 */     String prefix = "";
/* 3282 */     if (form.getStringValue("prefix") != null)
/*      */     {
/* 3284 */       prefix = form.getStringValue("prefix");
/*      */     }
/* 3286 */     pfm.setPrefixID((PrefixCode)SelectionManager.getLookupObject(prefix, Cache.getPrefixCodes()));
/*      */ 
/*      */     
/* 3289 */     String selectionNo = "";
/* 3290 */     if (form.getStringValue("selectionNo") != null)
/*      */     {
/* 3292 */       selectionNo = form.getStringValue("selectionNo");
/*      */     }
/* 3294 */     pfm.setSelectionNo(selectionNo);
/*      */ 
/*      */     
/* 3297 */     String configCode = "";
/* 3298 */     if (form.getStringValue("config_code") != null)
/*      */     {
/* 3300 */       configCode = form.getStringValue("config_code");
/*      */     }
/* 3302 */     pfm.setConfigCode(configCode);
/*      */ 
/*      */     
/* 3305 */     String repertoireOwner = "";
/* 3306 */     if (form.getStringValue("repertoire_owner") != null)
/*      */     {
/* 3308 */       repertoireOwner = form.getStringValue("repertoire_owner");
/*      */     }
/* 3310 */     pfm.setRepertoireOwner(repertoireOwner);
/*      */ 
/*      */     
/* 3313 */     String modifier = "";
/* 3314 */     if (form.getStringValue("modifier") != null)
/*      */     {
/* 3316 */       modifier = form.getStringValue("modifier");
/*      */     }
/* 3318 */     pfm.setModifier(modifier);
/*      */ 
/*      */     
/* 3321 */     String repertoireClass = "";
/* 3322 */     String tempHold = "";
/* 3323 */     Vector repOwners = Cache.getRepertoireClasses();
/* 3324 */     for (int i = 0; i < repOwners.size(); i++) {
/*      */       
/* 3326 */       repertoireClass = ((LookupObject)repOwners.elementAt(i)).getAbbreviation();
/* 3327 */       if (repertoireOwner.equals(repertoireClass))
/*      */       {
/* 3329 */         tempHold = repertoireClass;
/*      */       }
/*      */     } 
/* 3332 */     String repClass = tempHold;
/* 3333 */     pfm.setRepertoireClass(repClass);
/*      */ 
/*      */     
/* 3336 */     String upc = "";
/* 3337 */     if (form.getStringValue("upc") != null)
/*      */     {
/* 3339 */       upc = form.getStringValue("upc");
/*      */     }
/* 3341 */     pfm.setUpc(upc);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3350 */     String title = "";
/* 3351 */     if (form.getStringValue("title") != null)
/*      */     {
/* 3353 */       title = form.getStringValue("title");
/*      */     }
/* 3355 */     pfm.setTitle(title);
/*      */ 
/*      */     
/* 3358 */     String artist = "";
/* 3359 */     if (form.getStringValue("artist") != null)
/*      */     {
/* 3361 */       artist = form.getStringValue("artist");
/*      */     }
/* 3363 */     pfm.setArtist(artist);
/*      */ 
/*      */     
/* 3366 */     String titleID = "";
/* 3367 */     if (selection != null)
/*      */     {
/* 3369 */       titleID = selection.getTitleID();
/*      */     }
/* 3371 */     pfm.setTitleId(titleID);
/*      */ 
/*      */     
/* 3374 */     String returnCode = "";
/* 3375 */     if (form.getStringValue("return_code") != null)
/*      */     {
/* 3377 */       returnCode = form.getStringValue("return_code");
/*      */     }
/*      */     
/* 3380 */     if (selection.getIsDigital()) {
/* 3381 */       returnCode = "T";
/*      */     }
/* 3383 */     pfm.setReturnCode(returnCode);
/*      */ 
/*      */     
/* 3386 */     String exportFlag = "";
/* 3387 */     if (form.getStringValue("export_flag") != null)
/*      */     {
/* 3389 */       exportFlag = form.getStringValue("export_flag");
/*      */     }
/* 3391 */     pfm.setExportFlag(exportFlag);
/*      */ 
/*      */     
/* 3394 */     String superLabel = "";
/* 3395 */     if (form.getStringValue("super_label") != null)
/*      */     {
/* 3397 */       superLabel = form.getStringValue("super_label");
/*      */     }
/* 3399 */     pfm.setSuperLabel(superLabel);
/*      */ 
/*      */     
/* 3402 */     String countries = "";
/* 3403 */     if (form.getStringValue("countries") != null)
/*      */     {
/* 3405 */       countries = form.getStringValue("countries");
/*      */     }
/* 3407 */     pfm.setCountries(countries);
/*      */ 
/*      */     
/* 3410 */     String labelCode = "";
/* 3411 */     if (form.getStringValue("label_code") != null)
/*      */     {
/* 3413 */       labelCode = form.getStringValue("label_code");
/*      */     }
/* 3415 */     pfm.setLabelCode(labelCode);
/*      */ 
/*      */     
/* 3418 */     String spineTitle = "";
/* 3419 */     if (form.getStringValue("spine_title") != null)
/*      */     {
/* 3421 */       spineTitle = form.getStringValue("spine_title");
/*      */     }
/* 3423 */     pfm.setSpineTitle(spineTitle);
/*      */ 
/*      */     
/* 3426 */     String companyCode = "";
/* 3427 */     if (form.getStringValue("company_code") != null)
/*      */     {
/* 3429 */       companyCode = form.getStringValue("company_code");
/*      */     }
/* 3431 */     pfm.setCompanyCode(companyCode);
/*      */ 
/*      */     
/* 3434 */     String spineArtist = "";
/* 3435 */     if (form.getStringValue("spine_artist") != null)
/*      */     {
/* 3437 */       spineArtist = form.getStringValue("spine_artist");
/*      */     }
/* 3439 */     pfm.setSpineArtist(spineArtist);
/*      */ 
/*      */     
/* 3442 */     String priceCode = "";
/* 3443 */     if (form.getStringValue("price_code") != null)
/*      */     {
/* 3445 */       priceCode = form.getStringValue("price_code");
/*      */     }
/* 3447 */     pfm.setPriceCode(priceCode);
/*      */ 
/*      */     
/* 3450 */     String priceCodeDPC = "";
/* 3451 */     if (form.getStringValue("price_codeDPC") != null)
/*      */     {
/* 3453 */       priceCodeDPC = form.getStringValue("price_codeDPC");
/*      */     }
/* 3455 */     pfm.setPriceCodeDPC(priceCodeDPC);
/*      */ 
/*      */ 
/*      */     
/* 3459 */     String poMergeCode = "";
/* 3460 */     if (form.getStringValue("po_merge_code") != null)
/*      */     {
/* 3462 */       poMergeCode = form.getStringValue("po_merge_code");
/*      */     }
/*      */     
/* 3465 */     if (selection.getIsDigital())
/* 3466 */       poMergeCode = "99"; 
/* 3467 */     pfm.setPoMergeCode(poMergeCode);
/*      */ 
/*      */ 
/*      */     
/* 3471 */     String guaranteeCode = "";
/* 3472 */     if (form.getStringValue("guarantee_code") != null)
/*      */     {
/* 3474 */       guaranteeCode = form.getStringValue("guarantee_code");
/*      */     }
/*      */     
/* 3477 */     if (selection.getIsDigital())
/* 3478 */       guaranteeCode = "N"; 
/* 3479 */     pfm.setGuaranteeCode(guaranteeCode);
/*      */ 
/*      */     
/* 3482 */     String loosePickExemptCode = "";
/* 3483 */     if (form.getStringValue("loose_pick_exempt") != null)
/*      */     {
/* 3485 */       loosePickExemptCode = form.getStringValue("loose_pick_exempt");
/*      */     }
/*      */     
/* 3488 */     if (selection.getIsDigital())
/* 3489 */       loosePickExemptCode = "N"; 
/* 3490 */     pfm.setLoosePickExemptCode(loosePickExemptCode);
/*      */ 
/*      */     
/* 3493 */     pfm.setValueAdded(((FormCheckBox)form.getElement("ValueAdded")).isChecked());
/*      */ 
/*      */     
/* 3496 */     pfm.setBoxSet(((FormCheckBox)form.getElement("BoxSet")).isChecked());
/*      */ 
/*      */     
/* 3499 */     String compilationCode = "";
/* 3500 */     if (form.getStringValue("compilation_code") != null)
/*      */     {
/* 3502 */       compilationCode = form.getStringValue("compilation_code");
/*      */     }
/* 3504 */     pfm.setCompilationCode(compilationCode);
/*      */ 
/*      */     
/* 3507 */     String impRateCode = "";
/* 3508 */     if (form.getStringValue("imp_rate_code") != null)
/*      */     {
/* 3510 */       impRateCode = form.getStringValue("imp_rate_code");
/*      */     }
/* 3512 */     pfm.setImpRateCode(impRateCode);
/*      */ 
/*      */     
/* 3515 */     String unitsString = form.getStringValue("units_per_set").trim();
/* 3516 */     int units = 0;
/*      */     
/*      */     try {
/* 3519 */       units = Integer.parseInt(unitsString);
/*      */     }
/* 3521 */     catch (NumberFormatException numberFormatException) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3526 */     if (selection.getIsDigital())
/* 3527 */       units = 1; 
/* 3528 */     pfm.setUnitsPerSet(units);
/*      */ 
/*      */     
/* 3531 */     int sets = 0;
/*      */     
/*      */     try {
/* 3534 */       sets = Integer.parseInt(form.getStringValue("sets_per_carton").trim());
/*      */     }
/* 3536 */     catch (NumberFormatException numberFormatException) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3541 */     if (selection.getIsDigital())
/* 3542 */       sets = 1; 
/* 3543 */     pfm.setSetsPerCarton(sets);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3552 */     String musicType = "";
/* 3553 */     musicType = form.getStringValue("music_type");
/* 3554 */     pfm.setMusicType((Genre)SelectionManager.getLookupObject(musicType, Cache.getMusicTypes()));
/*      */ 
/*      */     
/* 3557 */     String suppliers = "";
/* 3558 */     if (form.getStringValue("suppliers") != null)
/*      */     {
/* 3560 */       suppliers = form.getStringValue("suppliers");
/*      */     }
/* 3562 */     pfm.setSupplier(suppliers);
/*      */ 
/*      */     
/* 3565 */     String narm = "";
/* 3566 */     if (form.getStringValue("NARM") != null)
/*      */     {
/* 3568 */       narm = form.getStringValue("NARM");
/*      */     }
/*      */     
/* 3571 */     if (selection.getIsDigital())
/* 3572 */       narm = "N"; 
/* 3573 */     pfm.setNarmFlag(narm);
/*      */ 
/*      */ 
/*      */     
/* 3577 */     String eFlag = "";
/* 3578 */     if (form.getStringValue("encryptionFlag") != null)
/* 3579 */       eFlag = form.getStringValue("encryptionFlag"); 
/* 3580 */     pfm.setEncryptionFlag(eFlag);
/*      */ 
/*      */     
/* 3583 */     String importIndicator = "";
/* 3584 */     if (form.getStringValue("ImportIndicator") != null)
/*      */     {
/* 3586 */       importIndicator = form.getStringValue("ImportIndicator");
/*      */     }
/* 3588 */     Vector importIndicators = Cache.getImportIndicators();
/* 3589 */     String[] name = new String[importIndicators.size()];
/* 3590 */     String[] abbreviation = new String[importIndicators.size()];
/*      */     
/* 3592 */     for (int i = 0; i < importIndicators.size(); i++) {
/*      */       
/* 3594 */       LookupObject lookupObject = (LookupObject)importIndicators.elementAt(i);
/* 3595 */       name[i] = lookupObject.getName();
/* 3596 */       abbreviation[i] = lookupObject.getAbbreviation();
/* 3597 */       if (importIndicator.equalsIgnoreCase(name[i]))
/*      */       {
/* 3599 */         importIndicator = abbreviation[i];
/*      */       }
/*      */     } 
/* 3602 */     pfm.setImportIndicator(importIndicator);
/*      */ 
/*      */     
/* 3605 */     String pricePoint = "";
/* 3606 */     if (form.getStringValue("price_point") != null)
/*      */     {
/* 3608 */       pricePoint = form.getStringValue("price_point");
/*      */     }
/*      */     
/* 3611 */     if (selection.getIsDigital())
/* 3612 */       pricePoint = "PS"; 
/* 3613 */     pfm.setPricePoint(pricePoint);
/*      */ 
/*      */ 
/*      */     
/* 3617 */     String projectID = form.getStringValue("projectID");
/* 3618 */     pfm.setProjectID(projectID);
/*      */     
/* 3620 */     String streetDateString = form.getStringValue("releaseDate");
/*      */     
/*      */     try {
/* 3623 */       Calendar streetDate = MilestoneHelper.getDate(streetDateString);
/* 3624 */       pfm.setStreetDate(streetDate);
/*      */     }
/* 3626 */     catch (Exception e) {
/*      */       
/* 3628 */       pfm.setStreetDate(null);
/*      */     } 
/*      */ 
/*      */     
/* 3632 */     String strStatus = form.getStringValue("status");
/* 3633 */     pfm.setStatus(strStatus);
/*      */     
/* 3635 */     pfm.setParentalGuidance(((FormCheckBox)form.getElement("ParentalAdv")).isChecked());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3641 */     String projectIDtoValidate = "";
/* 3642 */     if (pfm.getProjectID().trim().indexOf("-") > -1) {
/* 3643 */       for (int j = 1; j < pfm.getProjectID().trim().length(); j++) {
/* 3644 */         if (pfm.getProjectID().trim().charAt(j) != '-') {
/* 3645 */           projectIDtoValidate = String.valueOf(projectIDtoValidate) + pfm.getProjectID().trim().charAt(j);
/*      */         }
/*      */       } 
/*      */     } else {
/* 3649 */       projectIDtoValidate = pfm.getProjectID().trim();
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
/* 3662 */     if (!form.isUnchanged()) {
/*      */       
/* 3664 */       FormValidation formValidation = form.validate();
/* 3665 */       if (formValidation.isGood()) {
/*      */ 
/*      */         
/* 3668 */         Pfm savedPfm = SelectionManager.getInstance().saveNewPfm(pfm, user, selection.getIsDigital());
/*      */ 
/*      */ 
/*      */         
/* 3672 */         if (selection.getIsDigital()) {
/*      */           
/* 3674 */           SelectionManager.getInstance().saveSelectionDataFromDigitalPfm(
/* 3675 */               pfm.getReleaseId(), 
/* 3676 */               pfm.getPrefixID(), 
/* 3677 */               pfm.getSelectionNo(), 
/* 3678 */               pfm.getUpc(), 
/* 3679 */               pfm.getConfigCode(), 
/* 3680 */               pfm.getSoundScanGrp());
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 3685 */           if (messageObject != null && messageObject.selectionObj != null) {
/*      */             
/* 3687 */             messageObject.selectionObj.setUpc(pfm.getUpc());
/* 3688 */             messageObject.selectionObj.setConfigCode(pfm.getConfigCode());
/* 3689 */             messageObject.selectionObj.setSoundScanGrp(pfm.getSoundScanGrp());
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/* 3694 */         Form emailDistForm = form;
/*      */         
/* 3696 */         context.putSessionValue("Pfm", savedPfm);
/* 3697 */         context.putDelivery("Form", form);
/*      */ 
/*      */         
/* 3700 */         notepad.setAllContents(null);
/* 3701 */         notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 0);
/* 3702 */         notepad.goToSelectedPage();
/*      */         
/* 3704 */         Boolean IsSaveSend = Boolean.valueOf(false);
/*      */         
/* 3706 */         if ((finalFlag && !command.equalsIgnoreCase("pfm-edit-save-comment")) || firstTimeFinal) {
/* 3707 */           String lastUpdatedDate = "";
/* 3708 */           String lastUpdatedBy = "";
/*      */           
/* 3710 */           if (savedPfm.getLastUpdatedDate() != null) {
/* 3711 */             lastUpdatedDate = MilestoneHelper.getCustomFormatedDate(savedPfm.getLastUpdatedDate(), "M/d/yyyy hh:mm:ss a 'ET'");
/*      */           }
/* 3713 */           if (UserManager.getInstance().getUser(savedPfm.getLastUpdatingUser()) != null) {
/* 3714 */             lastUpdatedBy = UserManager.getInstance().getUser(savedPfm.getLastUpdatingUser()).getName();
/*      */           }
/*      */ 
/*      */ 
/*      */           
/* 3719 */           if (sendEmail && (EmailDistribution.putEmailBody(emailDistForm, context, selection, lastUpdatedDate, lastUpdatedBy, "PFM", messageObject) || 
/* 3720 */             messageObject.IsPushOnDemand)) {
/*      */ 
/*      */             
/*      */             try {
/* 3724 */               messageObject.pfmObj = (Pfm)savedPfm.clone();
/* 3725 */             } catch (CloneNotSupportedException ce) {
/* 3726 */               messageObject.pfmObj = savedPfm;
/*      */             } 
/* 3728 */             print(dispatcher, context, command, 2, true, messageObject);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 3734 */             if (messageObject.IsPushPFM || messageObject.IsPushOnDemand) {
/* 3735 */               PushCommunication.pushPFMLegacyAppendMessge(messageObject, PushCommunication.pushPFMLegacy(pfm, selection, context));
/*      */             }
/* 3737 */             EmailDistribution.sendEmail(dispatcher, context, "", messageObject);
/* 3738 */             EmailDistribution.removeSessionValues(context);
/*      */             
/* 3740 */             IsSaveSend = Boolean.valueOf(true);
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3749 */         DcGDRSResults dcGDRSResults = SelectionHandler.GDRSProductStatusGet(selection, selection.getCompany().getParentEnvironment().getStructureID());
/* 3750 */         boolean IsGDRSactive = (!dcGDRSResults.getStatus().equals("") && !dcGDRSResults.getStatus().equals("DELETE"));
/* 3751 */         if (!command.equalsIgnoreCase("pfm-edit-save-comment") && !selection.getNoDigitalRelease()) {
/*      */ 
/*      */           
/* 3754 */           if (!dcGDRSResults.getForceNoDigitalRelease().booleanValue())
/*      */           {
/* 3756 */             String status = IsSaveSend.booleanValue() ? "SAVE_SEND" : "CREATE_EDIT";
/* 3757 */             SelectionManager.GDRS_QueueAddReleaseId(selection, status);
/*      */           }
/*      */         
/* 3760 */         } else if (IsGDRSactive) {
/* 3761 */           SelectionManager.GDRS_QueueAddReleaseId(selection, "DELETE");
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3767 */         if (!command.equalsIgnoreCase("pfm-edit-save-comment")) {
/* 3768 */           context.putSessionValue("originalComment", savedPfm.getComments());
/*      */         }
/* 3770 */         context.putSessionValue("originalCommentRelId", new Integer(savedPfm.getReleaseId()));
/*      */         
/* 3772 */         if (!command.equalsIgnoreCase("selectionSave")) {
/* 3773 */           return edit(dispatcher, context, command);
/*      */         }
/* 3775 */         return true;
/*      */       } 
/*      */ 
/*      */       
/* 3779 */       context.putDelivery("FormValidation", formValidation);
/* 3780 */       form.addElement(new FormHidden("OrderBy", "", true));
/* 3781 */       context.putDelivery("Form", form);
/* 3782 */       if (!command.equalsIgnoreCase("selectionSave")) {
/* 3783 */         return context.includeJSP("pfm-editor.jsp");
/*      */       }
/* 3785 */       return true;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3791 */     if (!command.equalsIgnoreCase("selectionSave")) {
/* 3792 */       return edit(dispatcher, context, command);
/*      */     }
/* 3794 */     return true;
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
/*      */   public boolean print(Dispatcher dispatcher, Context context, String command, int pdfRtf, boolean ie5, MessageObject messageObject) {
/* 4069 */     Selection selection = null;
/* 4070 */     if (command.equalsIgnoreCase("selectionSave")) {
/*      */       
/* 4072 */       selection = (Selection)context.getSessionValue("pfmBomSelection");
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 4077 */       selection = MilestoneHelper.getScreenSelection(context);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4085 */     if (messageObject != null && messageObject.selectionObj != null) {
/* 4086 */       selection = messageObject.selectionObj;
/*      */     }
/* 4088 */     Pfm pfm = (Pfm)context.getSessionValue("Pfm");
/*      */     
/* 4090 */     if (selection != null) {
/*      */       
/* 4092 */       int selectionID = selection.getSelectionID();
/* 4093 */       pfm = SelectionManager.getInstance().getPfm(selectionID);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4100 */     if (messageObject != null && messageObject.pfmObj != null) {
/* 4101 */       pfm = messageObject.pfmObj;
/*      */     }
/* 4103 */     if (pfm != null) {
/*      */       try {
/*      */         DefaultTableLens table_contents;
/*      */ 
/*      */ 
/*      */         
/* 4109 */         InputStream input = new FileInputStream(String.valueOf(ReportHandler.reportPath) + "\\pfmtemplate.xml");
/*      */         
/* 4111 */         XStyleSheet report = 
/* 4112 */           (XStyleSheet)Builder.getBuilder(1, input).read(null);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 4117 */         SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
/* 4118 */         String todayLong = formatter.format(new Date());
/*      */         
/* 4120 */         report.setElement("txtReportDate", todayLong);
/* 4121 */         report.setElement("bottomdate", todayLong);
/*      */ 
/*      */         
/* 4124 */         report.setElement("txtChangeNumber", pfm.getChangeNumber());
/*      */         
/* 4126 */         String mode = pfm.getMode();
/* 4127 */         if (mode.equalsIgnoreCase("Add")) {
/*      */           
/* 4129 */           report.setElement("txtAdd", "X");
/* 4130 */           report.setElement("txtModify", "");
/*      */         }
/*      */         else {
/*      */           
/* 4134 */           report.setElement("txtAdd", "");
/* 4135 */           report.setElement("txtModify", "X");
/*      */         } 
/*      */ 
/*      */         
/* 4139 */         if (selection.getIsDigital()) {
/* 4140 */           report.setElement("txtProdType", "Digital");
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 4146 */         if (selection.getIsDigital()) {
/* 4147 */           table_contents = new DefaultTableLens(24, 8);
/*      */         } else {
/* 4149 */           table_contents = new DefaultTableLens(29, 8);
/*      */         } 
/*      */         
/* 4152 */         table_contents.setHeaderRowCount(0);
/* 4153 */         table_contents.setColWidth(0, 120);
/* 4154 */         table_contents.setColWidth(1, 30);
/* 4155 */         table_contents.setColWidth(2, 200);
/* 4156 */         table_contents.setColWidth(3, 100);
/* 4157 */         table_contents.setColWidth(4, 30);
/* 4158 */         table_contents.setColWidth(5, 110);
/* 4159 */         table_contents.setColWidth(6, 60);
/* 4160 */         table_contents.setColWidth(7, 80);
/*      */         
/* 4162 */         table_contents.setRowInsets(0, new Insets(1, 0, 0, 0));
/* 4163 */         table_contents.setRowInsets(1, new Insets(0, 0, 0, 0));
/* 4164 */         table_contents.setRowInsets(2, new Insets(0, 0, 0, 0));
/* 4165 */         table_contents.setRowInsets(3, new Insets(1, 0, 0, 0));
/* 4166 */         table_contents.setRowInsets(4, new Insets(1, 0, 0, 0));
/* 4167 */         table_contents.setRowInsets(5, new Insets(1, 0, 0, 0));
/* 4168 */         table_contents.setRowInsets(6, new Insets(1, 0, 0, 0));
/* 4169 */         table_contents.setRowInsets(7, new Insets(1, 0, 0, 0));
/* 4170 */         table_contents.setRowInsets(8, new Insets(1, 0, 0, 0));
/* 4171 */         table_contents.setRowInsets(9, new Insets(1, 0, 0, 0));
/* 4172 */         table_contents.setRowInsets(10, new Insets(1, 0, 0, 0));
/* 4173 */         table_contents.setRowInsets(11, new Insets(1, 0, 0, 0));
/* 4174 */         table_contents.setRowInsets(12, new Insets(1, 0, 0, 0));
/* 4175 */         table_contents.setRowInsets(13, new Insets(1, 0, 0, 0));
/* 4176 */         table_contents.setRowInsets(14, new Insets(1, 0, 0, 0));
/* 4177 */         table_contents.setRowInsets(15, new Insets(1, 0, 0, 0));
/* 4178 */         table_contents.setRowInsets(16, new Insets(1, 0, 0, 0));
/* 4179 */         table_contents.setRowInsets(17, new Insets(1, 0, 0, 0));
/* 4180 */         table_contents.setRowInsets(18, new Insets(1, 0, 0, 0));
/* 4181 */         table_contents.setRowInsets(19, new Insets(1, 0, 0, 0));
/* 4182 */         table_contents.setRowInsets(20, new Insets(1, 0, 0, 0));
/* 4183 */         table_contents.setRowInsets(21, new Insets(1, 0, 0, 0));
/* 4184 */         table_contents.setRowInsets(22, new Insets(1, 0, 0, 0));
/*      */ 
/*      */ 
/*      */         
/* 4188 */         table_contents.setRowBorder(-1, 0);
/* 4189 */         table_contents.setColBorder(0);
/* 4190 */         table_contents.setRowBorderColor(Color.lightGray);
/* 4191 */         table_contents.setRowBorder(266240);
/*      */         
/* 4193 */         table_contents.setColFont(0, new Font("Arial", 1, 10));
/* 4194 */         table_contents.setColFont(1, new Font("Arial", 1, 10));
/* 4195 */         table_contents.setColFont(2, new Font("Arial", 0, 10));
/* 4196 */         table_contents.setColFont(3, new Font("Arial", 1, 10));
/* 4197 */         table_contents.setColFont(4, new Font("Arial", 1, 10));
/* 4198 */         table_contents.setColFont(5, new Font("Arial", 0, 10));
/* 4199 */         table_contents.setColFont(6, new Font("Arial", 1, 10));
/* 4200 */         table_contents.setColFont(7, new Font("Arial", 0, 10));
/*      */         
/* 4202 */         table_contents.setColAlignment(0, 33);
/* 4203 */         table_contents.setColAlignment(1, 33);
/* 4204 */         table_contents.setColAlignment(2, 33);
/* 4205 */         table_contents.setColAlignment(4, 33);
/* 4206 */         table_contents.setColAlignment(3, 36);
/* 4207 */         table_contents.setColAlignment(5, 33);
/* 4208 */         table_contents.setColAlignment(6, 33);
/* 4209 */         table_contents.setColAlignment(7, 33);
/*      */ 
/*      */         
/* 4212 */         int nextRow = 0;
/*      */         
/* 4214 */         table_contents.setRowBorderColor(nextRow, 0, Color.white);
/* 4215 */         table_contents.setRowBorderColor(nextRow, 3, Color.white);
/* 4216 */         table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 4217 */         table_contents.setObject(nextRow, 0, "Prepared By:");
/* 4218 */         table_contents.setSpan(nextRow, 1, new Dimension(2, 1));
/* 4219 */         table_contents.setObject(nextRow, 1, pfm.getPreparedBy());
/* 4220 */         table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
/* 4221 */         table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
/* 4222 */         table_contents.setObject(nextRow, 3, "  Phone:");
/* 4223 */         table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
/* 4224 */         table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 4225 */         table_contents.setObject(nextRow, 5, pfm.getPhone());
/* 4226 */         table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
/* 4227 */         table_contents.setRowHeight(nextRow, 17);
/* 4228 */         nextRow++;
/*      */         
/* 4230 */         table_contents.setRowBorderColor(nextRow, 0, Color.white);
/* 4231 */         table_contents.setRowBorderColor(nextRow, 3, Color.white);
/* 4232 */         table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 4233 */         table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
/* 4234 */         table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
/* 4235 */         table_contents.setObject(nextRow, 0, "Email:");
/* 4236 */         table_contents.setSpan(nextRow, 1, new Dimension(2, 1));
/* 4237 */         table_contents.setObject(nextRow, 1, pfm.getEmail());
/* 4238 */         table_contents.setObject(nextRow, 3, "  Fax:");
/* 4239 */         table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
/* 4240 */         table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 4241 */         table_contents.setObject(nextRow, 5, pfm.getFaxNumber());
/* 4242 */         table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
/* 4243 */         table_contents.setRowHeight(nextRow, 17);
/* 4244 */         nextRow++;
/*      */         
/* 4246 */         table_contents.setRowBorderColor(nextRow, 0, Color.white);
/*      */         
/* 4248 */         table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
/* 4249 */         table_contents.setObject(nextRow, 0, "Comments:");
/* 4250 */         table_contents.setSpan(nextRow, 1, new Dimension(7, 4));
/* 4251 */         table_contents.setObject(nextRow, 1, pfm.getComments());
/* 4252 */         table_contents.setColLineWrap(1, true);
/* 4253 */         table_contents.setRowAutoSize(true);
/* 4254 */         table_contents.setRowHeight(nextRow, 23);
/* 4255 */         table_contents.setAlignment(nextRow, 1, 17);
/*      */         
/* 4257 */         nextRow++;
/*      */         
/* 4259 */         table_contents.setRowHeight(nextRow, 5);
/* 4260 */         table_contents.setRowBorderColor(nextRow, Color.white);
/* 4261 */         nextRow++;
/* 4262 */         table_contents.setRowHeight(nextRow, 5);
/* 4263 */         table_contents.setRowBorderColor(nextRow, Color.white);
/* 4264 */         nextRow++;
/*      */ 
/*      */         
/* 4267 */         table_contents.setRowHeight(nextRow, 5);
/* 4268 */         table_contents.setRowBorderColor(nextRow, Color.black);
/*      */         
/* 4270 */         nextRow++;
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
/* 4286 */         String operatingCompanyText = "";
/* 4287 */         if (selection.getOperCompany() != null && !selection.getOperCompany().equals("-1")) {
/* 4288 */           operatingCompanyText = selection.getOperCompany();
/*      */         }
/*      */ 
/*      */         
/* 4292 */         String upc = "";
/* 4293 */         if (selection != null && selection.getUpc() != null)
/*      */         {
/* 4295 */           upc = selection.getUpc();
/*      */         }
/*      */ 
/*      */         
/* 4299 */         String musicLineText = "";
/* 4300 */         if (pfm.getMusicLine() != null && pfm.getMusicLine().length() > 0)
/*      */         {
/* 4302 */           musicLineText = pfm.getMusicLine();
/*      */         }
/* 4304 */         musicLineText = reportPrintHelper(Cache.getMusicLines(), String.valueOf(musicLineText));
/*      */ 
/*      */         
/* 4307 */         String productNumber = String.valueOf(SelectionManager.getLookupObjectValue(selection.getPrefixID())) + selection.getSelectionNo();
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
/* 4319 */         String configCodeText = "";
/* 4320 */         if (selection.getConfigCode() != null && !selection.getConfigCode().equals("-1")) {
/* 4321 */           configCodeText = selection.getConfigCode();
/*      */         }
/* 4323 */         configCodeText = reportPrintHelper(Cache.getConfigCodes(), String.valueOf(configCodeText));
/*      */ 
/*      */         
/* 4326 */         String repertoireOwnerText = "";
/* 4327 */         if (pfm.getRepertoireOwner() != null && pfm.getRepertoireOwner().length() > 0)
/*      */         {
/* 4329 */           repertoireOwnerText = pfm.getRepertoireOwner();
/*      */         }
/* 4331 */         repertoireOwnerText = reportPrintHelper(Cache.getRepertoireOwners(), String.valueOf(repertoireOwnerText));
/*      */ 
/*      */         
/* 4334 */         String modifierText = "";
/* 4335 */         if (pfm.getModifier() != null && pfm.getModifier().length() > 0)
/*      */         {
/* 4337 */           modifierText = pfm.getModifier();
/*      */         }
/* 4339 */         modifierText = reportPrintHelper(Cache.getModifiers(), String.valueOf(modifierText));
/*      */ 
/*      */         
/* 4342 */         String repertoireClassText = "";
/* 4343 */         if (pfm.getRepertoireClass() != null && pfm.getRepertoireClass().length() > 0)
/*      */         {
/* 4345 */           repertoireClassText = pfm.getRepertoireClass();
/*      */         }
/* 4347 */         repertoireClassText = reportPrintHelper(Cache.getRepertoireClasses(), String.valueOf(repertoireClassText));
/*      */ 
/*      */         
/* 4350 */         String soundScanGroupText = "";
/* 4351 */         if (selection.getSoundScanGrp() != null) {
/* 4352 */           soundScanGroupText = selection.getSoundScanGrp();
/*      */         }
/*      */ 
/*      */         
/* 4356 */         String returnScrapCodeText = "";
/* 4357 */         if (pfm.getReturnCode() != null && pfm.getReturnCode().length() > 0)
/*      */         {
/* 4359 */           returnScrapCodeText = pfm.getReturnCode();
/*      */         }
/* 4361 */         returnScrapCodeText = reportPrintHelper(Cache.getReturnCodes(), String.valueOf(returnScrapCodeText));
/*      */ 
/*      */         
/* 4364 */         String titleText = "";
/* 4365 */         if (selection.getTitle() != null) {
/* 4366 */           titleText = selection.getTitle();
/*      */         }
/*      */ 
/*      */         
/* 4370 */         String exportCodeText = "";
/* 4371 */         if (pfm.getExportFlag() != null && pfm.getExportFlag().length() > 0)
/*      */         {
/* 4373 */           exportCodeText = pfm.getExportFlag();
/*      */         }
/* 4375 */         exportCodeText = reportPrintHelper(Cache.getExportFlags(), String.valueOf(exportCodeText));
/*      */ 
/*      */         
/* 4378 */         String artistText = "";
/* 4379 */         if (selection.getArtist() != null) {
/*      */           
/* 4381 */           artistText = selection.getArtistLastName();
/* 4382 */           if (!selection.getArtistLastName().equals("") && !selection.getArtistFirstName().equals(""))
/*      */           {
/* 4384 */             artistText = String.valueOf(artistText) + ", ";
/*      */           }
/* 4386 */           artistText = String.valueOf(artistText) + selection.getArtistFirstName();
/*      */         } 
/*      */ 
/*      */         
/* 4390 */         String titleID = "";
/* 4391 */         if (selection.getTitleID() != null) {
/* 4392 */           titleID = selection.getTitleID();
/*      */         }
/*      */         
/* 4395 */         String releaseDate = "";
/* 4396 */         if (!selection.getIsDigital() && selection.getStreetDate() != null) {
/*      */           
/* 4398 */           releaseDate = MilestoneHelper.getFormatedDate(selection.getStreetDate());
/* 4399 */         } else if (selection.getIsDigital() && selection.getDigitalRlsDate() != null) {
/* 4400 */           releaseDate = MilestoneHelper.getFormatedDate(selection.getDigitalRlsDate());
/*      */         } 
/*      */ 
/*      */         
/* 4404 */         String priceCodeText = "";
/* 4405 */         if (selection.getPriceCode() != null) {
/* 4406 */           priceCodeText = selection.getPriceCode().getSellCode();
/*      */         }
/*      */         
/* 4409 */         String priceCodeDPCText = "";
/* 4410 */         if (selection.getPriceCodeDPC() != null) {
/* 4411 */           priceCodeDPCText = selection.getPriceCodeDPC().getSellCode();
/*      */         }
/*      */ 
/*      */         
/* 4415 */         String superLabelText = "";
/* 4416 */         if (selection.getSuperLabel() != null) {
/* 4417 */           superLabelText = selection.getSuperLabel();
/*      */         }
/*      */ 
/*      */         
/* 4421 */         String guaranteeCodeText = "";
/* 4422 */         if (pfm.getGuaranteeCode() != null && pfm.getGuaranteeCode().length() > 0)
/*      */         {
/* 4424 */           guaranteeCodeText = pfm.getGuaranteeCode();
/*      */         }
/* 4426 */         guaranteeCodeText = reportPrintHelper(Cache.getGuaranteeCodes(), String.valueOf(guaranteeCodeText));
/*      */         
/* 4428 */         String labelCodeText = "";
/* 4429 */         if (selection.getSubLabel() != null) {
/* 4430 */           labelCodeText = selection.getSubLabel();
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 4435 */         String narmText = "";
/* 4436 */         if (pfm.getNarmFlag() != null)
/*      */         {
/* 4438 */           narmText = pfm.getNarmFlag();
/*      */         }
/* 4440 */         narmText = reportPrintHelper(Cache.getNarmExtracts(), String.valueOf(narmText));
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 4445 */         String encryptionText = "";
/* 4446 */         if (pfm.getEncryptionFlag() != null)
/*      */         {
/* 4448 */           encryptionText = pfm.getEncryptionFlag();
/*      */         }
/* 4450 */         encryptionText = reportPrintHelper(Cache.getEncryptionFlags(), String.valueOf(encryptionText));
/*      */ 
/*      */         
/* 4453 */         String musicTypeText = "";
/* 4454 */         if (pfm.getMusicType() != null) {
/* 4455 */           musicTypeText = SelectionManager.getLookupObjectValue(pfm.getMusicType());
/* 4456 */           musicTypeText = reportPrintHelper(Cache.getMusicTypes(), String.valueOf(musicTypeText));
/*      */         } 
/*      */ 
/*      */         
/* 4460 */         String projectID = "";
/* 4461 */         if (selection != null && selection.getProjectID() != null)
/*      */         {
/* 4463 */           projectID = selection.getProjectID();
/*      */         }
/*      */ 
/*      */         
/* 4467 */         String poMergeCodeText = "";
/* 4468 */         if (pfm.getPoMergeCode() != null && pfm.getPoMergeCode().length() > 0)
/*      */         {
/* 4470 */           poMergeCodeText = pfm.getPoMergeCode();
/*      */         }
/* 4472 */         poMergeCodeText = reportPrintHelper(Cache.getPoMergeCodes(), String.valueOf(poMergeCodeText));
/*      */ 
/*      */         
/* 4475 */         String loosePickExemptCodeText = "";
/* 4476 */         if (pfm.getLoosePickExemptCode() != null && pfm.getLoosePickExemptCode().length() > 0)
/*      */         {
/* 4478 */           loosePickExemptCodeText = pfm.getLoosePickExemptCode();
/*      */         }
/* 4480 */         loosePickExemptCodeText = reportPrintHelper(Cache.getLoosePickExempt(), String.valueOf(loosePickExemptCodeText));
/*      */ 
/*      */         
/* 4483 */         String companyCodeText = "";
/* 4484 */         if (pfm.getCompanyCode() != null && pfm.getCompanyCode().length() > 0)
/*      */         {
/* 4486 */           companyCodeText = pfm.getCompanyCode();
/*      */         }
/* 4488 */         companyCodeText = reportPrintHelper(Cache.getCompanyCodes(), String.valueOf(companyCodeText));
/*      */ 
/*      */         
/* 4491 */         String importRateCodeText = "";
/* 4492 */         if (pfm.getImpRateCode() != null && pfm.getImpRateCode().length() > 0)
/*      */         {
/* 4494 */           importRateCodeText = pfm.getImpRateCode();
/*      */         }
/* 4496 */         importRateCodeText = reportPrintHelper(Cache.getImpRateCodes(), String.valueOf(importRateCodeText));
/*      */         
/* 4498 */         String pricePointText = "";
/* 4499 */         if (pfm.getPricePoint() != null && pfm.getPricePoint().length() > 0)
/*      */         {
/* 4501 */           pricePointText = pfm.getPricePoint();
/*      */         }
/* 4503 */         pricePointText = reportPrintHelper(Cache.getPricePoints(), String.valueOf(pricePointText));
/*      */ 
/*      */         
/* 4506 */         String unitsPerSetText = "";
/*      */ 
/*      */         
/* 4509 */         if (pfm.getUnitsPerSet() > 0) {
/*      */           
/*      */           try {
/*      */ 
/*      */             
/* 4514 */             unitsPerSetText = Integer.toString(pfm.getUnitsPerSet());
/*      */           }
/* 4516 */           catch (NumberFormatException e) {
/*      */             
/* 4518 */             System.out.println("Error converting Units Per Set into integer.");
/*      */           } 
/*      */         }
/*      */ 
/*      */         
/* 4523 */         String compilationCodeText = "";
/* 4524 */         if (pfm.getCompilationCode() != null)
/*      */         {
/* 4526 */           compilationCodeText = pfm.getCompilationCode();
/*      */         }
/* 4528 */         compilationCodeText = reportPrintHelper(Cache.getCompilationCodes(), String.valueOf(compilationCodeText));
/*      */ 
/*      */         
/* 4531 */         String supplierText = "";
/* 4532 */         if (pfm.getSupplier() != null && pfm.getSupplier().length() > 0)
/*      */         {
/* 4534 */           supplierText = pfm.getSupplier();
/*      */         }
/* 4536 */         supplierText = reportPrintHelper(Cache.getSuppliers(), String.valueOf(supplierText));
/*      */         
/* 4538 */         String enteredByName = "";
/* 4539 */         int enteredById = 0;
/*      */ 
/*      */         
/*      */         try {
/* 4543 */           if (pfm.getEnteredByName() != null && pfm.getEnteredByName().length() > 0) {
/* 4544 */             enteredById = Integer.parseInt(pfm.getEnteredByName());
/*      */           }
/* 4546 */         } catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 4555 */         if (enteredById == 0) {
/*      */           
/* 4557 */           User user = MilestoneSecurity.getUser(context);
/* 4558 */           if (user != null) {
/* 4559 */             enteredById = user.getUserId();
/*      */           }
/*      */         } 
/* 4562 */         if (UserManager.getInstance().getUser(enteredById) != null) {
/* 4563 */           enteredByName = UserManager.getInstance().getUser(enteredById).getName();
/*      */         }
/*      */         
/* 4566 */         String importCodeText = "";
/* 4567 */         if (pfm.getImportIndicator() != null && pfm.getImportIndicator().length() > 0) {
/*      */           
/* 4569 */           importCodeText = pfm.getImportIndicator().trim();
/* 4570 */           if (importCodeText.equals("D")) {
/* 4571 */             importCodeText = "D:US Made";
/* 4572 */           } else if (importCodeText.equals("I")) {
/* 4573 */             importCodeText = "I:Import";
/*      */           } 
/*      */         } 
/* 4576 */         String gridNum = "";
/* 4577 */         if (selection != null) {
/* 4578 */           gridNum = selection.getGridNumber();
/*      */         }
/*      */         
/* 4581 */         String imprint = "";
/* 4582 */         if (selection.getImprint() != null) {
/* 4583 */           imprint = selection.getImprint();
/*      */         }
/*      */ 
/*      */         
/* 4587 */         String statusText = selection.getSelectionStatus().getName();
/*      */ 
/*      */         
/* 4590 */         String prefixNumber = "";
/* 4591 */         prefixNumber = SelectionManager.getLookupObjectValue(selection.getPrefixID());
/* 4592 */         if (!prefixNumber.equals(""))
/* 4593 */           prefixNumber = String.valueOf(prefixNumber) + "-"; 
/* 4594 */         String physicalPrefixCatNumber = String.valueOf(prefixNumber) + selection.getSelectionNo();
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 4599 */         if (selection.getIsDigital()) {
/* 4600 */           table_contents.setRowBorderColor(nextRow, 0, Color.white);
/* 4601 */           table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 4602 */           table_contents.setRowBorderColor(nextRow, 3, Color.white);
/* 4603 */           table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 4604 */           table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
/* 4605 */           table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
/* 4606 */           table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
/* 4607 */           table_contents.setObject(nextRow, 0, "Imprint");
/* 4608 */           table_contents.setObject(nextRow, 2, imprint);
/* 4609 */           table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
/* 4610 */           table_contents.setAlignment(nextRow, 3, 1);
/* 4611 */           table_contents.setObject(nextRow, 3, "  UPC:");
/*      */           
/* 4613 */           table_contents.setObject(nextRow, 5, MilestoneHelper_2.getRMSReportFormat(upc, "UPC", selection.getIsDigital()));
/* 4614 */           table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 4615 */           table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
/* 4616 */           nextRow++;
/*      */           
/* 4618 */           table_contents.setRowBorderColor(nextRow, 0, Color.white);
/* 4619 */           table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 4620 */           table_contents.setRowBorderColor(nextRow, 3, Color.white);
/* 4621 */           table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 4622 */           table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
/* 4623 */           table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
/* 4624 */           table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
/* 4625 */           table_contents.setObject(nextRow, 0, "Operating Company:");
/* 4626 */           table_contents.setObject(nextRow, 2, operatingCompanyText);
/*      */           
/* 4628 */           table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
/* 4629 */           table_contents.setAlignment(nextRow, 3, 1);
/* 4630 */           table_contents.setObject(nextRow, 3, "  Sales Grouping Cd:");
/*      */           
/* 4632 */           table_contents.setObject(nextRow, 5, MilestoneHelper_2.getRMSReportFormat(soundScanGroupText, "SSG", selection.getIsDigital()));
/* 4633 */           table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 4634 */           table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 4643 */           nextRow++;
/*      */           
/* 4645 */           table_contents.setRowBorderColor(nextRow, 0, Color.white);
/* 4646 */           table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 4647 */           table_contents.setRowBorderColor(nextRow, 3, Color.white);
/* 4648 */           table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 4649 */           table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
/* 4650 */           table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
/* 4651 */           table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
/* 4652 */           table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
/* 4653 */           table_contents.setObject(nextRow, 0, "Physical Prefix /\nLocal Prod #");
/* 4654 */           table_contents.setObject(nextRow, 2, physicalPrefixCatNumber);
/* 4655 */           table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
/* 4656 */           table_contents.setAlignment(nextRow, 3, 1);
/* 4657 */           table_contents.setObject(nextRow, 3, "  Music Line:");
/* 4658 */           table_contents.setObject(nextRow, 5, musicLineText);
/* 4659 */           table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 4660 */           nextRow++;
/*      */           
/* 4662 */           table_contents.setRowBorderColor(nextRow, 0, Color.white);
/* 4663 */           table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 4664 */           table_contents.setRowBorderColor(nextRow, 3, Color.white);
/* 4665 */           table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 4666 */           table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
/* 4667 */           table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
/* 4668 */           table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
/* 4669 */           table_contents.setObject(nextRow, 0, "Config Code:");
/* 4670 */           table_contents.setObject(nextRow, 2, configCodeText);
/*      */           
/* 4672 */           table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
/* 4673 */           table_contents.setAlignment(nextRow, 3, 1);
/* 4674 */           table_contents.setObject(nextRow, 3, "  Repertoire Owner:");
/* 4675 */           table_contents.setObject(nextRow, 5, repertoireOwnerText);
/* 4676 */           table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 4677 */           table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
/* 4678 */           nextRow++;
/*      */           
/* 4680 */           table_contents.setRowBorderColor(nextRow, 0, Color.white);
/* 4681 */           table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 4682 */           table_contents.setRowBorderColor(nextRow, 3, Color.white);
/* 4683 */           table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 4684 */           table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
/* 4685 */           table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
/* 4686 */           table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
/* 4687 */           table_contents.setObject(nextRow, 0, "Title:");
/* 4688 */           table_contents.setObject(nextRow, 2, titleText);
/* 4689 */           table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
/* 4690 */           table_contents.setAlignment(nextRow, 3, 1);
/* 4691 */           table_contents.setObject(nextRow, 3, "  Repertoire Class:");
/* 4692 */           table_contents.setObject(nextRow, 5, repertoireClassText);
/* 4693 */           table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 4694 */           table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
/* 4695 */           nextRow++;
/*      */           
/* 4697 */           table_contents.setRowBorderColor(nextRow, 0, Color.white);
/* 4698 */           table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 4699 */           table_contents.setRowBorderColor(nextRow, 3, Color.white);
/* 4700 */           table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 4701 */           table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
/* 4702 */           table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 4709 */           table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
/* 4710 */           table_contents.setObject(nextRow, 0, "Artist:");
/* 4711 */           table_contents.setObject(nextRow, 2, artistText);
/* 4712 */           table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
/* 4713 */           table_contents.setAlignment(nextRow, 3, 1);
/* 4714 */           table_contents.setObject(nextRow, 3, "  Return/Scrap Code:");
/* 4715 */           table_contents.setObject(nextRow, 5, returnScrapCodeText);
/* 4716 */           table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 4717 */           table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
/* 4718 */           nextRow++;
/*      */           
/* 4720 */           table_contents.setRowBorderColor(nextRow, 0, Color.white);
/* 4721 */           table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 4722 */           table_contents.setRowBorderColor(nextRow, 3, Color.white);
/* 4723 */           table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 4724 */           table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
/* 4725 */           table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
/* 4726 */           table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
/* 4727 */           table_contents.setObject(nextRow, 0, "Digital Release Date:");
/* 4728 */           table_contents.setObject(nextRow, 2, releaseDate);
/* 4729 */           table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
/* 4730 */           table_contents.setAlignment(nextRow, 3, 1);
/* 4731 */           table_contents.setRowLineWrap(nextRow, false);
/* 4732 */           table_contents.setObject(nextRow, 3, "  Loose Pick Exempt:");
/* 4733 */           table_contents.setObject(nextRow, 5, loosePickExemptCodeText);
/* 4734 */           table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 4735 */           table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
/* 4736 */           nextRow++;
/*      */           
/* 4738 */           table_contents.setRowBorderColor(nextRow, 0, Color.white);
/* 4739 */           table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 4740 */           table_contents.setRowBorderColor(nextRow, 3, Color.white);
/* 4741 */           table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 4742 */           table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
/* 4743 */           table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
/* 4744 */           table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
/* 4745 */           table_contents.setObject(nextRow, 0, "Status:");
/* 4746 */           table_contents.setObject(nextRow, 2, statusText);
/* 4747 */           table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
/* 4748 */           table_contents.setAlignment(nextRow, 3, 1);
/* 4749 */           table_contents.setObject(nextRow, 3, "  IMI Exempt Code:");
/* 4750 */           table_contents.setObject(nextRow, 5, guaranteeCodeText);
/* 4751 */           table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 4752 */           table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
/* 4753 */           nextRow++;
/*      */           
/* 4755 */           table_contents.setRowBorderColor(nextRow, 0, Color.white);
/* 4756 */           table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 4757 */           table_contents.setRowBorderColor(nextRow, 3, Color.white);
/* 4758 */           table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 4759 */           table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
/* 4760 */           table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
/* 4761 */           table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
/* 4762 */           table_contents.setObject(nextRow, 0, "Super Label:");
/* 4763 */           table_contents.setObject(nextRow, 2, superLabelText);
/* 4764 */           table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
/* 4765 */           table_contents.setAlignment(nextRow, 3, 1);
/* 4766 */           table_contents.setObject(nextRow, 3, "  Music Type:");
/* 4767 */           table_contents.setObject(nextRow, 5, musicTypeText);
/* 4768 */           table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 4769 */           table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
/* 4770 */           nextRow++;
/*      */           
/* 4772 */           table_contents.setRowBorderColor(nextRow, 0, Color.white);
/* 4773 */           table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 4774 */           table_contents.setRowBorderColor(nextRow, 3, Color.white);
/* 4775 */           table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 4776 */           table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
/* 4777 */           table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
/* 4778 */           table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
/* 4779 */           table_contents.setObject(nextRow, 0, "Sub Label:");
/* 4780 */           table_contents.setObject(nextRow, 2, labelCodeText);
/* 4781 */           table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
/* 4782 */           table_contents.setAlignment(nextRow, 3, 1);
/* 4783 */           table_contents.setObject(nextRow, 3, "  Price Point:");
/* 4784 */           table_contents.setObject(nextRow, 5, pricePointText);
/* 4785 */           table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 4786 */           table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
/* 4787 */           nextRow++;
/*      */           
/* 4789 */           table_contents.setRowBorderColor(nextRow, 0, Color.white);
/* 4790 */           table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 4791 */           table_contents.setRowBorderColor(nextRow, 3, Color.white);
/* 4792 */           table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 4793 */           table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
/* 4794 */           table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
/* 4795 */           table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
/* 4796 */           table_contents.setObject(nextRow, 0, "Company Code:");
/* 4797 */           table_contents.setObject(nextRow, 2, companyCodeText);
/* 4798 */           table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
/* 4799 */           table_contents.setAlignment(nextRow, 3, 1);
/* 4800 */           table_contents.setObject(nextRow, 3, "  Narm Extract Ind:");
/* 4801 */           table_contents.setObject(nextRow, 5, narmText);
/* 4802 */           table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 4803 */           table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
/* 4804 */           nextRow++;
/*      */           
/* 4806 */           table_contents.setRowBorderColor(nextRow, 0, Color.white);
/* 4807 */           table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 4808 */           table_contents.setRowBorderColor(nextRow, 3, Color.white);
/* 4809 */           table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 4810 */           table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
/* 4811 */           table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
/* 4812 */           table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
/* 4813 */           table_contents.setObject(nextRow, 0, "Project ID (9 Digits):");
/* 4814 */           table_contents.setObject(nextRow, 2, projectID);
/* 4815 */           table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
/* 4816 */           table_contents.setAlignment(nextRow, 3, 1);
/* 4817 */           table_contents.setObject(nextRow, 3, "  Compilation \n  Soundtrack:");
/* 4818 */           table_contents.setObject(nextRow, 5, compilationCodeText);
/* 4819 */           table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 4820 */           table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
/* 4821 */           nextRow++;
/*      */           
/* 4823 */           table_contents.setRowBorderColor(nextRow, 0, Color.white);
/* 4824 */           table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 4825 */           table_contents.setRowBorderColor(nextRow, 3, Color.white);
/* 4826 */           table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 4827 */           table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
/* 4828 */           table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
/* 4829 */           table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
/* 4830 */           table_contents.setObject(nextRow, 0, "PO Merge Code:");
/* 4831 */           table_contents.setObject(nextRow, 2, poMergeCodeText);
/*      */           
/* 4833 */           table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
/* 4834 */           table_contents.setAlignment(nextRow, 3, 1);
/* 4835 */           table_contents.setObject(nextRow, 3, "  Parental Adv:");
/* 4836 */           table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 4837 */           table_contents.setObject(nextRow, 5, selection.getParentalGuidance() ? "Yes" : "");
/* 4838 */           nextRow++;
/*      */           
/* 4840 */           table_contents.setRowBorderColor(nextRow, 0, Color.white);
/* 4841 */           table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 4842 */           table_contents.setRowBorderColor(nextRow, 3, Color.white);
/* 4843 */           table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 4844 */           table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
/* 4845 */           table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
/* 4846 */           table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
/* 4847 */           table_contents.setObject(nextRow, 0, "# of Units:");
/* 4848 */           table_contents.setObject(nextRow, 2, unitsPerSetText);
/* 4849 */           table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
/* 4850 */           table_contents.setAlignment(nextRow, 3, 1);
/* 4851 */           table_contents.setObject(nextRow, 3, "  Dig. Price Code:");
/* 4852 */           table_contents.setObject(nextRow, 5, priceCodeText);
/* 4853 */           table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 4854 */           table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
/* 4855 */           nextRow++;
/*      */           
/* 4857 */           table_contents.setRowBorderColor(nextRow, 0, Color.white);
/* 4858 */           table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 4859 */           table_contents.setRowBorderColor(nextRow, 3, Color.white);
/* 4860 */           table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 4861 */           table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
/* 4862 */           table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
/* 4863 */           table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
/* 4864 */           table_contents.setObject(nextRow, 0, "Sets Per Carton:");
/* 4865 */           table_contents.setObject(nextRow, 2, String.valueOf(pfm.getSetsPerCarton()));
/* 4866 */           table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
/* 4867 */           table_contents.setAlignment(nextRow, 3, 1);
/* 4868 */           table_contents.setObject(nextRow, 3, "  Entered By:");
/* 4869 */           table_contents.setObject(nextRow, 5, enteredByName);
/* 4870 */           table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 4871 */           table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
/* 4872 */           nextRow++;
/*      */           
/* 4874 */           table_contents.setRowBorderColor(nextRow, 0, Color.white);
/* 4875 */           table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 4876 */           table_contents.setRowBorderColor(nextRow, 3, Color.white);
/* 4877 */           table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 4878 */           table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
/* 4879 */           table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
/* 4880 */           table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
/* 4881 */           table_contents.setObject(nextRow, 0, "Import Indicator:");
/* 4882 */           table_contents.setObject(nextRow, 2, importCodeText);
/* 4883 */           table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
/* 4884 */           table_contents.setAlignment(nextRow, 3, 1);
/* 4885 */           table_contents.setObject(nextRow, 3, "  Date:");
/* 4886 */           table_contents.setObject(nextRow, 5, pfm.getEnteredDate());
/* 4887 */           table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 4888 */           table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
/* 4889 */           nextRow++;
/*      */ 
/*      */ 
/*      */           
/* 4893 */           table_contents.setRowBorderColor(nextRow, 0, Color.white);
/* 4894 */           table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 4895 */           table_contents.setRowBorderColor(nextRow, 3, Color.white);
/* 4896 */           table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 4897 */           table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
/* 4898 */           table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
/* 4899 */           table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
/* 4900 */           table_contents.setObject(nextRow, 0, "GRid #:");
/* 4901 */           table_contents.setObject(nextRow, 2, gridNum);
/* 4902 */           table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
/* 4903 */           table_contents.setAlignment(nextRow, 3, 1);
/* 4904 */           table_contents.setObject(nextRow, 3, "  Approved By:");
/* 4905 */           table_contents.setObject(nextRow, 5, pfm.getApprovedByName());
/* 4906 */           table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 4907 */           table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
/* 4908 */           nextRow++;
/*      */ 
/*      */           
/* 4911 */           table_contents.setRowBorderColor(nextRow, 0, Color.white);
/* 4912 */           table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 4913 */           table_contents.setRowBorderColor(nextRow, 2, Color.white);
/* 4914 */           table_contents.setRowBorderColor(nextRow, 3, Color.white);
/* 4915 */           table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 4916 */           table_contents.setRowBorderColor(nextRow, 5, Color.white);
/* 4917 */           table_contents.setRowBorderColor(nextRow, 6, Color.white);
/* 4918 */           table_contents.setRowBorderColor(nextRow, 7, Color.white);
/* 4919 */           nextRow++;
/*      */           
/* 4921 */           table_contents.setRowBorderColor(nextRow, Color.white);
/*      */         
/*      */         }
/*      */         else {
/*      */           
/* 4926 */           table_contents.setRowBorderColor(nextRow, 0, Color.white);
/* 4927 */           table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 4928 */           table_contents.setRowBorderColor(nextRow, 3, Color.white);
/* 4929 */           table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 4930 */           table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
/* 4931 */           table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
/* 4932 */           table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
/* 4933 */           table_contents.setObject(nextRow, 0, "Operating Company:");
/* 4934 */           table_contents.setObject(nextRow, 2, operatingCompanyText);
/* 4935 */           table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
/* 4936 */           table_contents.setAlignment(nextRow, 3, 1);
/* 4937 */           table_contents.setObject(nextRow, 3, "  Repertoire Owner:");
/* 4938 */           table_contents.setObject(nextRow, 5, repertoireOwnerText);
/* 4939 */           table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 4940 */           table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
/* 4941 */           nextRow++;
/*      */           
/* 4943 */           table_contents.setRowBorderColor(nextRow, 0, Color.white);
/* 4944 */           table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 4945 */           table_contents.setRowBorderColor(nextRow, 3, Color.white);
/* 4946 */           table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 4947 */           table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
/* 4948 */           table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
/* 4949 */           table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
/* 4950 */           table_contents.setObject(nextRow, 0, "Local Prod #:");
/* 4951 */           table_contents.setObject(nextRow, 2, productNumber);
/* 4952 */           table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
/* 4953 */           table_contents.setAlignment(nextRow, 3, 1);
/* 4954 */           table_contents.setObject(nextRow, 3, "  Repertoire Class:");
/* 4955 */           table_contents.setObject(nextRow, 5, repertoireClassText);
/* 4956 */           table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 4957 */           table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
/* 4958 */           nextRow++;
/*      */           
/* 4960 */           table_contents.setRowBorderColor(nextRow, 0, Color.white);
/* 4961 */           table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 4962 */           table_contents.setRowBorderColor(nextRow, 3, Color.white);
/* 4963 */           table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 4964 */           table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
/* 4965 */           table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
/* 4966 */           table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
/* 4967 */           table_contents.setObject(nextRow, 0, "Config Code:");
/* 4968 */           table_contents.setObject(nextRow, 2, configCodeText);
/* 4969 */           table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
/* 4970 */           table_contents.setAlignment(nextRow, 3, 1);
/* 4971 */           table_contents.setObject(nextRow, 3, "  Return/Scrap Code:");
/* 4972 */           table_contents.setObject(nextRow, 5, returnScrapCodeText);
/* 4973 */           table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 4974 */           table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
/* 4975 */           nextRow++;
/*      */ 
/*      */           
/* 4978 */           table_contents.setRowBorderColor(nextRow, 0, Color.white);
/* 4979 */           table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 4980 */           table_contents.setRowBorderColor(nextRow, 3, Color.white);
/* 4981 */           table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 4982 */           table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
/* 4983 */           table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
/*      */ 
/*      */ 
/*      */           
/* 4987 */           table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
/* 4988 */           table_contents.setAlignment(nextRow, 3, 1);
/* 4989 */           table_contents.setObject(nextRow, 3, "  Export Indicator:");
/* 4990 */           table_contents.setObject(nextRow, 5, exportCodeText);
/* 4991 */           table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 4992 */           table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
/* 4993 */           nextRow++;
/*      */           
/* 4995 */           table_contents.setRowBorderColor(nextRow, 0, Color.white);
/* 4996 */           table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 4997 */           table_contents.setRowBorderColor(nextRow, 3, Color.white);
/* 4998 */           table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 4999 */           table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
/* 5000 */           table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
/* 5001 */           table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
/* 5002 */           table_contents.setObject(nextRow, 0, "Title:");
/* 5003 */           table_contents.setObject(nextRow, 2, titleText);
/*      */           
/* 5005 */           table_contents.setSpan(nextRow, 3, new Dimension(3, 1));
/* 5006 */           table_contents.setAlignment(nextRow, 3, 1);
/* 5007 */           table_contents.setObject(nextRow, 3, "  Included or Excluded Countries:");
/* 5008 */           table_contents.setObject(nextRow, 6, pfm.getCountries());
/* 5009 */           table_contents.setSpan(nextRow, 6, new Dimension(2, 1));
/* 5010 */           table_contents.setFont(nextRow, 6, new Font("Arial", 0, 9));
/* 5011 */           nextRow++;
/*      */           
/* 5013 */           table_contents.setRowBorderColor(nextRow, 0, Color.white);
/* 5014 */           table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 5015 */           table_contents.setRowBorderColor(nextRow, 3, Color.white);
/* 5016 */           table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 5017 */           table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
/* 5018 */           table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
/* 5019 */           table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
/* 5020 */           table_contents.setObject(nextRow, 0, "Artist:");
/* 5021 */           table_contents.setObject(nextRow, 2, artistText);
/* 5022 */           table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
/* 5023 */           table_contents.setAlignment(nextRow, 3, 1);
/* 5024 */           table_contents.setObject(nextRow, 3, "  Spine Title:");
/*      */           
/* 5026 */           String spineTitle = pfm.getSpineTitle();
/* 5027 */           if (spineTitle.length() > 37) {
/* 5028 */             spineTitle = spineTitle.substring(0, 37);
/*      */           }
/* 5030 */           table_contents.setObject(nextRow, 5, spineTitle);
/* 5031 */           table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 5032 */           table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
/* 5033 */           nextRow++;
/*      */           
/* 5035 */           table_contents.setRowBorderColor(nextRow, 0, Color.white);
/* 5036 */           table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 5037 */           table_contents.setRowBorderColor(nextRow, 3, Color.white);
/* 5038 */           table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 5039 */           table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
/* 5040 */           table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
/* 5041 */           table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
/* 5042 */           table_contents.setObject(nextRow, 0, "Title ID (10 Char):");
/* 5043 */           table_contents.setObject(nextRow, 2, titleID);
/* 5044 */           table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
/* 5045 */           table_contents.setAlignment(nextRow, 3, 1);
/* 5046 */           table_contents.setObject(nextRow, 3, "  Spine Artist:");
/*      */           
/* 5048 */           String spineArtist = pfm.getSpineArtist();
/* 5049 */           if (spineArtist.length() > 37) {
/* 5050 */             spineArtist = spineArtist.substring(0, 37);
/*      */           }
/* 5052 */           table_contents.setObject(nextRow, 5, spineArtist);
/* 5053 */           table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 5054 */           table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
/* 5055 */           nextRow++;
/*      */           
/* 5057 */           table_contents.setRowBorderColor(nextRow, 0, Color.white);
/* 5058 */           table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 5059 */           table_contents.setRowBorderColor(nextRow, 3, Color.white);
/* 5060 */           table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 5061 */           table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
/* 5062 */           table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
/* 5063 */           table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
/* 5064 */           table_contents.setObject(nextRow, 0, "Planned Release Date:");
/* 5065 */           table_contents.setObject(nextRow, 2, releaseDate);
/* 5066 */           table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
/* 5067 */           table_contents.setAlignment(nextRow, 3, 1);
/* 5068 */           table_contents.setRowLineWrap(nextRow, false);
/* 5069 */           table_contents.setObject(nextRow, 3, "  Loose Pick Exempt:");
/* 5070 */           table_contents.setObject(nextRow, 5, loosePickExemptCodeText);
/* 5071 */           table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 5072 */           table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
/* 5073 */           nextRow++;
/*      */           
/* 5075 */           table_contents.setRowBorderColor(nextRow, 0, Color.white);
/* 5076 */           table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 5077 */           table_contents.setRowBorderColor(nextRow, 3, Color.white);
/* 5078 */           table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 5079 */           table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
/* 5080 */           table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
/* 5081 */           table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
/* 5082 */           table_contents.setObject(nextRow, 0, "Status:");
/* 5083 */           table_contents.setObject(nextRow, 2, statusText);
/*      */           
/* 5085 */           table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
/* 5086 */           table_contents.setAlignment(nextRow, 3, 1);
/* 5087 */           table_contents.setObject(nextRow, 3, "  IMI Exempt Code:");
/* 5088 */           table_contents.setObject(nextRow, 5, guaranteeCodeText);
/* 5089 */           table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 5090 */           table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
/* 5091 */           nextRow++;
/*      */           
/* 5093 */           table_contents.setRowBorderColor(nextRow, 0, Color.white);
/* 5094 */           table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 5095 */           table_contents.setRowBorderColor(nextRow, 3, Color.white);
/* 5096 */           table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 5097 */           table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
/* 5098 */           table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
/* 5099 */           table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
/* 5100 */           table_contents.setObject(nextRow, 0, "Super Label:");
/* 5101 */           table_contents.setObject(nextRow, 2, superLabelText);
/*      */           
/* 5103 */           table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
/* 5104 */           table_contents.setAlignment(nextRow, 3, 1);
/* 5105 */           table_contents.setObject(nextRow, 3, "  Music Type:");
/* 5106 */           table_contents.setObject(nextRow, 5, musicTypeText);
/* 5107 */           table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 5108 */           table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
/* 5109 */           nextRow++;
/*      */           
/* 5111 */           table_contents.setRowBorderColor(nextRow, 0, Color.white);
/* 5112 */           table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 5113 */           table_contents.setRowBorderColor(nextRow, 3, Color.white);
/* 5114 */           table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 5115 */           table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
/* 5116 */           table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
/* 5117 */           table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
/* 5118 */           table_contents.setObject(nextRow, 0, "Sub Label:");
/* 5119 */           table_contents.setObject(nextRow, 2, labelCodeText);
/* 5120 */           table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
/* 5121 */           table_contents.setAlignment(nextRow, 3, 1);
/* 5122 */           table_contents.setObject(nextRow, 3, "  IMI/IMP Rate Code:");
/* 5123 */           table_contents.setObject(nextRow, 5, importRateCodeText);
/* 5124 */           table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 5125 */           table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
/* 5126 */           nextRow++;
/*      */           
/* 5128 */           table_contents.setRowBorderColor(nextRow, 0, Color.white);
/* 5129 */           table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 5130 */           table_contents.setRowBorderColor(nextRow, 3, Color.white);
/* 5131 */           table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 5132 */           table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
/* 5133 */           table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
/* 5134 */           table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
/* 5135 */           table_contents.setObject(nextRow, 0, "Company Code:");
/* 5136 */           table_contents.setObject(nextRow, 2, companyCodeText);
/* 5137 */           table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
/* 5138 */           table_contents.setAlignment(nextRow, 3, 1);
/* 5139 */           table_contents.setObject(nextRow, 3, "  Price Point:");
/* 5140 */           table_contents.setObject(nextRow, 5, pricePointText);
/* 5141 */           table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 5142 */           table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
/* 5143 */           nextRow++;
/*      */           
/* 5145 */           table_contents.setRowBorderColor(nextRow, 0, Color.white);
/* 5146 */           table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 5147 */           table_contents.setRowBorderColor(nextRow, 3, Color.white);
/* 5148 */           table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 5149 */           table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
/* 5150 */           table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
/* 5151 */           table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
/* 5152 */           table_contents.setObject(nextRow, 0, "Project ID (9 Digits):");
/* 5153 */           table_contents.setObject(nextRow, 2, projectID);
/* 5154 */           table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
/* 5155 */           table_contents.setAlignment(nextRow, 3, 1);
/* 5156 */           table_contents.setObject(nextRow, 3, "  Narm Extract Ind:");
/* 5157 */           table_contents.setObject(nextRow, 5, narmText);
/* 5158 */           table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 5159 */           table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
/* 5160 */           nextRow++;
/*      */           
/* 5162 */           table_contents.setRowBorderColor(nextRow, 0, Color.white);
/* 5163 */           table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 5164 */           table_contents.setRowBorderColor(nextRow, 3, Color.white);
/* 5165 */           table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 5166 */           table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
/* 5167 */           table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
/* 5168 */           table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
/* 5169 */           table_contents.setObject(nextRow, 0, "PO Merge Code:");
/* 5170 */           table_contents.setObject(nextRow, 2, poMergeCodeText);
/*      */           
/* 5172 */           table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
/* 5173 */           table_contents.setAlignment(nextRow, 3, 1);
/* 5174 */           table_contents.setObject(nextRow, 3, "  Compilation \n  Soundtrack:");
/* 5175 */           table_contents.setObject(nextRow, 5, compilationCodeText);
/* 5176 */           table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 5177 */           table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
/* 5178 */           nextRow++;
/*      */           
/* 5180 */           table_contents.setRowBorderColor(nextRow, 0, Color.white);
/* 5181 */           table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 5182 */           table_contents.setRowBorderColor(nextRow, 3, Color.white);
/* 5183 */           table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 5184 */           table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
/* 5185 */           table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
/* 5186 */           table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
/* 5187 */           table_contents.setObject(nextRow, 0, "# of Units:");
/* 5188 */           table_contents.setObject(nextRow, 2, unitsPerSetText);
/* 5189 */           table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
/* 5190 */           table_contents.setAlignment(nextRow, 3, 1);
/* 5191 */           table_contents.setObject(nextRow, 3, "  Parental Adv:");
/* 5192 */           table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 5193 */           table_contents.setObject(nextRow, 5, selection.getParentalGuidance() ? "Yes" : "");
/* 5194 */           nextRow++;
/*      */           
/* 5196 */           table_contents.setRowBorderColor(nextRow, 0, Color.white);
/* 5197 */           table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 5198 */           table_contents.setRowBorderColor(nextRow, 3, Color.white);
/* 5199 */           table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 5200 */           table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
/* 5201 */           table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
/* 5202 */           table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
/* 5203 */           table_contents.setObject(nextRow, 0, "Sets Per Carton:");
/* 5204 */           table_contents.setObject(nextRow, 2, String.valueOf(pfm.getSetsPerCarton()));
/* 5205 */           table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
/* 5206 */           table_contents.setAlignment(nextRow, 3, 1);
/* 5207 */           table_contents.setObject(nextRow, 3, "  Box Set:");
/* 5208 */           table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 5209 */           table_contents.setObject(nextRow, 5, pfm.getBoxSet() ? "Yes" : "");
/* 5210 */           nextRow++;
/*      */           
/* 5212 */           table_contents.setRowBorderColor(nextRow, 0, Color.white);
/* 5213 */           table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 5214 */           table_contents.setRowBorderColor(nextRow, 3, Color.white);
/* 5215 */           table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 5216 */           table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
/* 5217 */           table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
/* 5218 */           table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
/* 5219 */           table_contents.setObject(nextRow, 0, "Supplier:");
/* 5220 */           table_contents.setObject(nextRow, 2, supplierText);
/* 5221 */           table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
/* 5222 */           table_contents.setAlignment(nextRow, 3, 1);
/* 5223 */           table_contents.setObject(nextRow, 3, "  Value Added:");
/* 5224 */           table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 5225 */           table_contents.setObject(nextRow, 5, pfm.getValueAdded() ? "Yes" : "");
/* 5226 */           nextRow++;
/*      */           
/* 5228 */           table_contents.setRowBorderColor(nextRow, 0, Color.white);
/* 5229 */           table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 5230 */           table_contents.setRowBorderColor(nextRow, 3, Color.white);
/* 5231 */           table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 5232 */           table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
/* 5233 */           table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
/* 5234 */           table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
/* 5235 */           table_contents.setObject(nextRow, 0, "Import Indicator:");
/* 5236 */           table_contents.setObject(nextRow, 2, importCodeText);
/* 5237 */           table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
/* 5238 */           table_contents.setAlignment(nextRow, 3, 1);
/* 5239 */           table_contents.setObject(nextRow, 3, "  Encryption:");
/* 5240 */           table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 5241 */           table_contents.setObject(nextRow, 5, encryptionText);
/* 5242 */           table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
/* 5243 */           nextRow++;
/*      */           
/* 5245 */           table_contents.setRowBorderColor(nextRow, 0, Color.white);
/* 5246 */           table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 5247 */           table_contents.setRowBorderColor(nextRow, 3, Color.white);
/* 5248 */           table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 5249 */           table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
/* 5250 */           table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
/* 5251 */           table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
/* 5252 */           table_contents.setObject(nextRow, 0, "UPC:");
/*      */           
/* 5254 */           table_contents.setObject(nextRow, 2, MilestoneHelper_2.getRMSReportFormat(upc, "UPC", selection.getIsDigital()));
/* 5255 */           table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
/* 5256 */           table_contents.setAlignment(nextRow, 3, 1);
/* 5257 */           table_contents.setObject(nextRow, 3, "  Price Code:");
/* 5258 */           table_contents.setObject(nextRow, 5, priceCodeText);
/* 5259 */           table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 5260 */           table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
/* 5261 */           nextRow++;
/*      */           
/* 5263 */           table_contents.setRowBorderColor(nextRow, 0, Color.white);
/* 5264 */           table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 5265 */           table_contents.setRowBorderColor(nextRow, 3, Color.white);
/* 5266 */           table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 5267 */           table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
/* 5268 */           table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
/* 5269 */           table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
/*      */ 
/*      */           
/* 5272 */           table_contents.setObject(nextRow, 0, "SoundScan Grp:");
/*      */           
/* 5274 */           table_contents.setObject(nextRow, 2, MilestoneHelper_2.getRMSReportFormat(soundScanGroupText, "SSG", selection.getIsDigital()));
/*      */           
/* 5276 */           table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
/* 5277 */           table_contents.setAlignment(nextRow, 3, 1);
/* 5278 */           table_contents.setObject(nextRow, 3, "  Dig. Price Code:");
/* 5279 */           table_contents.setObject(nextRow, 5, priceCodeDPCText);
/* 5280 */           table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 5281 */           table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
/* 5282 */           nextRow++;
/*      */           
/* 5284 */           table_contents.setRowBorderColor(nextRow, 0, Color.white);
/* 5285 */           table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 5286 */           table_contents.setRowBorderColor(nextRow, 3, Color.white);
/* 5287 */           table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 5288 */           table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
/* 5289 */           table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
/* 5290 */           table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
/*      */           
/* 5292 */           table_contents.setObject(nextRow, 0, "Music Line:");
/* 5293 */           table_contents.setObject(nextRow, 2, musicLineText);
/*      */ 
/*      */           
/* 5296 */           table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
/* 5297 */           table_contents.setAlignment(nextRow, 3, 1);
/* 5298 */           table_contents.setObject(nextRow, 3, "  GRid #:");
/* 5299 */           table_contents.setObject(nextRow, 5, gridNum);
/* 5300 */           table_contents.setSpan(nextRow, 5, new Dimension(3, 1));
/* 5301 */           table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
/* 5302 */           nextRow++;
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
/* 5326 */           table_contents.setRowBorderColor(nextRow, 0, Color.white);
/* 5327 */           table_contents.setRowBorderColor(nextRow, 1, Color.white);
/* 5328 */           table_contents.setRowBorderColor(nextRow, 3, Color.white);
/* 5329 */           table_contents.setRowBorderColor(nextRow, 4, Color.white);
/* 5330 */           table_contents.setFont(nextRow, 1, new Font("Arial", 0, 9));
/* 5331 */           table_contents.setFont(nextRow, 2, new Font("Arial", 0, 9));
/* 5332 */           table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
/* 5333 */           table_contents.setObject(nextRow, 0, "Entered By:");
/* 5334 */           table_contents.setObject(nextRow, 2, enteredByName);
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 5339 */           table_contents.setSpan(nextRow, 3, new Dimension(2, 1));
/* 5340 */           table_contents.setAlignment(nextRow, 3, 1);
/* 5341 */           table_contents.setObject(nextRow, 3, "  Approved By:");
/* 5342 */           table_contents.setObject(nextRow, 5, pfm.getApprovedByName());
/* 5343 */           table_contents.setFont(nextRow, 6, new Font("Arial", 0, 9));
/* 5344 */           table_contents.setObject(nextRow, 6, "  Date:");
/* 5345 */           table_contents.setRowBorderColor(nextRow, 6, Color.white);
/* 5346 */           table_contents.setObject(nextRow, 7, pfm.getEnteredDate());
/* 5347 */           table_contents.setFont(nextRow, 5, new Font("Arial", 0, 9));
/* 5348 */           table_contents.setFont(nextRow, 7, new Font("Arial", 0, 9));
/*      */ 
/*      */           
/* 5351 */           nextRow++;
/*      */           
/* 5353 */           nextRow++;
/*      */           
/* 5355 */           table_contents.setRowBorderColor(nextRow, Color.white);
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 5360 */         report.setElement("table_colheaders", table_contents);
/* 5361 */         String reportFilename = "report.pdf";
/*      */         
/* 5363 */         if (pdfRtf == 0) {
/*      */           
/* 5365 */           HttpServletResponse sresponse = context.getResponse();
/*      */           
/* 5367 */           if (ie5) {
/*      */             
/* 5369 */             sresponse.setHeader("extension", "pdf");
/* 5370 */             sresponse.setContentType("application/pdf");
/* 5371 */             sresponse.setHeader("Content-disposition", "attachment; filename=" + reportFilename);
/*      */           }
/*      */           else {
/*      */             
/* 5375 */             sresponse.setHeader("extension", "pdf");
/* 5376 */             sresponse.setContentType("application/force-download");
/* 5377 */             sresponse.setHeader("Content-disposition", "attachment; filename=" + reportFilename);
/*      */           } 
/*      */           
/* 5380 */           ServletOutputStream servletOutputStream = sresponse.getOutputStream();
/* 5381 */           servletOutputStream.flush();
/*      */           
/* 5383 */           PDF4Generator pdfGenerator = new PDF4Generator(servletOutputStream);
/* 5384 */           pdfGenerator.generate(report);
/*      */           
/* 5386 */           servletOutputStream.close();
/*      */         } 
/*      */ 
/*      */         
/* 5390 */         if (pdfRtf == 2) {
/*      */           
/* 5392 */           String prefix = "";
/* 5393 */           if (selection.getPrefixID() != null && selection.getPrefixID().getAbbreviation() != null)
/* 5394 */             prefix = selection.getPrefixID().getAbbreviation(); 
/* 5395 */           EmailDistribution.generateFormReport(context, "PFM", report, 
/* 5396 */               selection.getSelectionNo(), prefix, selection.getUpc(), selection.getIsDigital(), messageObject);
/*      */         } 
/*      */ 
/*      */         
/* 5400 */         return true;
/*      */       
/*      */       }
/* 5403 */       catch (Exception e) {
/*      */         
/* 5405 */         e.printStackTrace();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 5410 */     return edit(dispatcher, context, command);
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
/*      */   private static String reportPrintHelper(Vector menuVector, String selectedOption) {
/* 5425 */     String result = "";
/*      */     
/* 5427 */     if (selectedOption == null) selectedOption = "";
/*      */     
/* 5429 */     if (menuVector != null)
/*      */     {
/* 5431 */       for (int i = 0; i < menuVector.size(); i++) {
/*      */         
/* 5433 */         LookupObject lookupObject = (LookupObject)menuVector.elementAt(i);
/* 5434 */         if (selectedOption.equalsIgnoreCase(lookupObject.getAbbreviation())) {
/* 5435 */           String temporaryHold = lookupObject.getName();
/* 5436 */           temporaryHold = temporaryHold.replace(',', ' ');
/* 5437 */           result = String.valueOf(lookupObject.getAbbreviation()) + ":" + temporaryHold;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     }
/* 5443 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getSelectionPfmPermissions(Selection selection, User user) {
/* 5454 */     int level = 0;
/*      */     
/* 5456 */     if (selection != null && selection.getSelectionID() > -1) {
/*      */ 
/*      */ 
/*      */       
/* 5460 */       Environment env = selection.getEnvironment();
/*      */       
/* 5462 */       CompanyAcl companyAcl = MilestoneHelper.getScreenPermissions(env, user);
/* 5463 */       if (companyAcl != null && companyAcl.getAccessPfmForm() > level)
/*      */       {
/* 5465 */         level = companyAcl.getAccessPfmForm();
/*      */       }
/*      */     } 
/*      */     
/* 5469 */     return level;
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
/* 5491 */     String saveVisible = "false";
/*      */     
/* 5493 */     if (level > 1)
/*      */     {
/* 5495 */       saveVisible = "true";
/*      */     }
/*      */ 
/*      */     
/* 5499 */     context.removeSessionValue("saveVisible");
/* 5500 */     context.putDelivery("saveVisible", saveVisible);
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
/*      */   private boolean editCopy(Dispatcher dispatcher, Context context, String command) {
/* 5515 */     User user = MilestoneSecurity.getUser(context);
/*      */     
/* 5517 */     Notepad notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 0);
/* 5518 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */     
/* 5520 */     Selection selection = (Selection)context.getSessionValue("Selection");
/*      */     
/* 5522 */     int selectionID = selection.getSelectionID();
/*      */     
/* 5524 */     Pfm targetPfm = SelectionManager.getInstance().getPfm(selectionID);
/* 5525 */     Pfm copiedPfm = null;
/*      */     
/* 5527 */     if (targetPfm != null) {
/*      */ 
/*      */       
/*      */       try {
/* 5531 */         copiedPfm = (Pfm)targetPfm.clone();
/*      */       }
/* 5533 */       catch (CloneNotSupportedException cloneNotSupportedException) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 5539 */       Form form = buildForm(context, copiedPfm, command);
/* 5540 */       form.addElement(new FormHidden("cmd", "pfm-edit-copy", true));
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
/* 5552 */       String projectIDtoValidate = "";
/* 5553 */       if (copiedPfm.getProjectID().trim().indexOf("-") > -1) {
/* 5554 */         for (int j = 1; j < copiedPfm.getProjectID().trim().length(); j++) {
/* 5555 */           if (copiedPfm.getProjectID().trim().charAt(j) != '-') {
/* 5556 */             projectIDtoValidate = String.valueOf(projectIDtoValidate) + copiedPfm.getProjectID().trim().charAt(j);
/*      */           }
/*      */         } 
/*      */       } else {
/* 5560 */         projectIDtoValidate = copiedPfm.getProjectID().trim();
/*      */       } 
/*      */       
/* 5563 */       if (!ProjectSearchManager.getInstance().isProjectNumberValid(projectIDtoValidate)) {
/* 5564 */         context.putDelivery("AlertMessage", "The Project Number on this product is invalid, please set up a new product.");
/*      */       }
/* 5566 */       context.putSessionValue("copiedPfm", copiedPfm);
/* 5567 */       context.putSessionValue("Pfm", targetPfm);
/* 5568 */       context.putDelivery("Form", form);
/*      */     } 
/*      */ 
/*      */     
/* 5572 */     if (context.getSessionValue("NOTEPAD_PFM_VISIBLE") != null) {
/* 5573 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_PFM_VISIBLE"));
/*      */     }
/* 5575 */     return edit(dispatcher, context, command);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean pasteCopy(Dispatcher dispatcher, Context context, String command) {
/* 5585 */     User user = MilestoneSecurity.getUser(context);
/*      */     
/* 5587 */     Notepad notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 0);
/* 5588 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */     
/* 5590 */     Selection selection = (Selection)context.getSessionValue("Selection");
/* 5591 */     int selectionID = selection.getSelectionID();
/*      */     
/* 5593 */     Pfm copiedPfm = (Pfm)context.getSessionValue("copiedPfm");
/*      */ 
/*      */ 
/*      */     
/* 5597 */     String productNumber = String.valueOf(SelectionManager.getLookupObjectValue(selection.getPrefixID())) + selection.getSelectionNo();
/*      */ 
/*      */     
/* 5600 */     String upc = "";
/* 5601 */     if (selection.getUpc() != null)
/*      */     {
/* 5603 */       upc = selection.getUpc();
/*      */     }
/*      */ 
/*      */     
/* 5607 */     String title = "";
/* 5608 */     if (selection.getTitle() != null)
/*      */     {
/* 5610 */       title = selection.getTitle();
/*      */     }
/*      */ 
/*      */     
/* 5614 */     String artist = "";
/* 5615 */     if (selection.getArtist() != null) {
/*      */       
/* 5617 */       artist = selection.getArtistLastName();
/* 5618 */       if (!selection.getArtistLastName().equals("") && !selection.getArtistFirstName().equals(""))
/*      */       {
/* 5620 */         artist = String.valueOf(artist) + ", ";
/*      */       }
/* 5622 */       artist = String.valueOf(artist) + selection.getArtistFirstName();
/*      */     } 
/*      */ 
/*      */     
/* 5626 */     String titleID = "";
/* 5627 */     if (selection.getTitleID() != null)
/*      */     {
/* 5629 */       titleID = selection.getTitleID();
/*      */     }
/*      */     
/* 5632 */     String spineTitleText = "";
/* 5633 */     if (selection != null)
/*      */     {
/* 5635 */       if (selection.getTitle() != null) {
/* 5636 */         spineTitleText = (selection.getTitle().length() > 39) ? selection.getTitle().substring(0, 38) : selection.getTitle();
/*      */       }
/*      */     }
/* 5639 */     String spineArtistText = "";
/* 5640 */     if (selection != null)
/*      */     {
/*      */       
/* 5643 */       if (selection.getArtist() != null) {
/* 5644 */         spineArtistText = (selection.getFlArtist().length() > 39) ? selection.getFlArtist().substring(0, 38) : selection.getFlArtist();
/*      */       }
/*      */     }
/*      */ 
/*      */     
/* 5649 */     String priceCode = "";
/* 5650 */     if (selection.getSellCode() != null && !selection.getSellCode().equals("-1")) {
/* 5651 */       priceCode = selection.getSellCode();
/*      */     }
/*      */     
/* 5654 */     String priceCodeDPC = "";
/* 5655 */     if (selection.getSellCodeDPC() != null && !selection.getSellCodeDPC().equals("-1")) {
/* 5656 */       priceCodeDPC = selection.getSellCodeDPC();
/*      */     }
/* 5658 */     copiedPfm.setUpc(upc);
/* 5659 */     copiedPfm.setProductNumber(productNumber);
/* 5660 */     copiedPfm.setTitle(title);
/* 5661 */     copiedPfm.setArtist(artist);
/* 5662 */     copiedPfm.setTitleId(titleID);
/* 5663 */     copiedPfm.setSpineTitle(spineTitleText);
/*      */     
/* 5665 */     copiedPfm.setSpineArtist(spineArtistText.trim());
/* 5666 */     copiedPfm.setPriceCode(priceCode);
/* 5667 */     copiedPfm.setPriceCodeDPC(priceCodeDPC);
/*      */ 
/*      */     
/* 5670 */     copiedPfm.setStreetDate(selection.getDigitalRlsDate());
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5675 */     Pfm pfm = SelectionManager.getInstance().getPfm(selectionID);
/*      */ 
/*      */ 
/*      */     
/* 5679 */     copiedPfm.setPrintOption("Draft");
/* 5680 */     copiedPfm.setComments("");
/* 5681 */     copiedPfm.setMode("Add");
/* 5682 */     User currentUser = (User)context.getSessionValue("user");
/* 5683 */     copiedPfm.setPreparedBy(currentUser.getName());
/* 5684 */     copiedPfm.setPhone(currentUser.getPhone());
/* 5685 */     copiedPfm.setEmail(currentUser.getEmail());
/* 5686 */     copiedPfm.setFaxNumber(currentUser.getFax());
/* 5687 */     copiedPfm.setLastUpdatedDate(null);
/*      */     
/* 5689 */     copiedPfm.setLastUpdatingUser(99999);
/*      */     
/* 5691 */     copiedPfm.setModifier("-1");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5696 */     int releaseId = -1;
/* 5697 */     long timestamp = -1L;
/* 5698 */     int lastUser = -1;
/*      */ 
/*      */     
/* 5701 */     Form form = buildForm(context, copiedPfm, command);
/* 5702 */     form.addElement(new FormHidden("cmd", "pfm-paste-copy", true));
/* 5703 */     form.addElement(new FormHidden("copyPaste", "copy", true));
/*      */     
/* 5705 */     context.putSessionValue("Pfm", pfm);
/* 5706 */     context.putDelivery("Form", form);
/*      */     
/* 5708 */     context.removeSessionValue("copiedPfm");
/*      */ 
/*      */     
/* 5711 */     if (context.getSessionValue("NOTEPAD_PFM_VISIBLE") != null) {
/* 5712 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_PFM_VISIBLE"));
/*      */     }
/*      */     
/* 5715 */     if (selection.getIsDigital()) {
/* 5716 */       return context.includeJSP("pfm-editor-digital.jsp");
/*      */     }
/* 5718 */     return context.includeJSP("pfm-editor.jsp");
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
/*      */   private String getSearchJavaScriptCorporateArray(Context context) {
/* 5732 */     StringBuffer result = new StringBuffer(100);
/* 5733 */     String str = "";
/* 5734 */     String value = new String();
/* 5735 */     boolean foundFirstTemp = false;
/*      */     
/* 5737 */     User user = (User)context.getSessionValue("user");
/* 5738 */     Vector vUserCompanies = MilestoneHelper.getUserCompanies(context);
/*      */ 
/*      */     
/* 5741 */     HashMap corpHashMap = MilestoneHelper.buildActiveCorporateStructureHashMap();
/*      */ 
/*      */ 
/*      */     
/* 5745 */     result.append("\n");
/* 5746 */     result.append("var aSearch = new Array();\n");
/* 5747 */     int arrayIndex = 0;
/*      */     
/* 5749 */     result.append("aSearch[0] = new Array(");
/* 5750 */     result.append(0);
/* 5751 */     result.append(", '");
/* 5752 */     result.append(" ");
/* 5753 */     result.append('\'');
/* 5754 */     foundFirstTemp = true;
/*      */     
/* 5756 */     for (int a = 0; a < vUserCompanies.size(); a++) {
/*      */       
/* 5758 */       Company ucTemp = (Company)vUserCompanies.elementAt(a);
/* 5759 */       if (ucTemp != null) {
/*      */ 
/*      */         
/* 5762 */         Vector labels = Cache.getInstance().getLabels();
/* 5763 */         for (int b = 0; b < labels.size(); b++) {
/*      */           
/* 5765 */           Label node = (Label)labels.elementAt(b);
/*      */           
/* 5767 */           if (node.getParent().getParentID() == ucTemp.getStructureID() && 
/* 5768 */             !corpHashMap.containsKey(new Integer(node.getStructureID()))) {
/*      */             
/* 5770 */             if (foundFirstTemp)
/* 5771 */               result.append(','); 
/* 5772 */             result.append(' ');
/* 5773 */             result.append(node.getStructureID());
/* 5774 */             result.append(", '");
/* 5775 */             result.append(MilestoneHelper.urlEncode(node.getName()));
/* 5776 */             result.append('\'');
/* 5777 */             foundFirstTemp = true;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 5784 */     if (!foundFirstTemp) {
/* 5785 */       result.append("'[none available]');\n");
/*      */     } else {
/* 5787 */       result.append(");\n");
/*      */     } 
/*      */     
/* 5790 */     for (int i = 0; i < vUserCompanies.size(); i++) {
/*      */       
/* 5792 */       Company uc = (Company)vUserCompanies.elementAt(i);
/* 5793 */       if (uc != null) {
/*      */         
/* 5795 */         result.append("aSearch[");
/* 5796 */         result.append(uc.getStructureID());
/* 5797 */         result.append("] = new Array(");
/*      */         
/* 5799 */         boolean foundFirst = false;
/*      */         
/* 5801 */         result.append(0);
/* 5802 */         result.append(", '");
/* 5803 */         result.append(" ");
/* 5804 */         result.append('\'');
/* 5805 */         foundFirst = true;
/*      */         
/* 5807 */         Vector tmpArray = new Vector();
/*      */         
/* 5809 */         Vector labels = Cache.getInstance().getLabels();
/* 5810 */         for (int j = 0; j < labels.size(); j++) {
/*      */           
/* 5812 */           Label node = (Label)labels.elementAt(j);
/*      */           
/* 5814 */           if (node.getParent().getParentID() == uc.getStructureID() && 
/* 5815 */             !corpHashMap.containsKey(new Integer(node.getStructureID()))) {
/*      */             
/* 5817 */             if (foundFirst)
/* 5818 */               result.append(','); 
/* 5819 */             result.append(' ');
/* 5820 */             result.append(node.getStructureID());
/* 5821 */             result.append(", '");
/* 5822 */             result.append(MilestoneHelper.urlEncode(node.getName()));
/* 5823 */             result.append('\'');
/* 5824 */             foundFirst = true;
/* 5825 */             tmpArray.addElement(node);
/*      */           } 
/*      */         } 
/*      */         
/* 5829 */         if (foundFirst) {
/*      */           
/* 5831 */           result.append(");\n");
/*      */         }
/*      */         else {
/*      */           
/* 5835 */           result.append(" 0, '[none available]');\n");
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 5840 */     return result.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean sortGroup(Dispatcher dispatcher, Context context) {
/* 5849 */     int sort = Integer.parseInt(context.getParameter("OrderBy"));
/*      */     
/* 5851 */     String alphaGroupChr = context.getParameter("alphaGroupChr");
/*      */ 
/*      */     
/* 5854 */     Notepad notepad = (Notepad)context.getSessionValue(MilestoneConstants.NOTEPAD_SESSION_NAMES[0]);
/*      */ 
/*      */     
/* 5857 */     User user = (User)context.getSession().getAttribute("user");
/* 5858 */     if (notepad.getAllContents() != null && notepad.getAllContents().size() < notepad.getTotalRecords()) {
/*      */       
/* 5860 */       notepad.setMaxRecords(0);
/* 5861 */       notepad.setAllContents(null);
/* 5862 */       notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 0);
/*      */     } 
/*      */     
/* 5865 */     SelectionManager.getInstance().getAlphaGroupPosition(context, notepad, alphaGroupChr, sort);
/*      */     
/* 5867 */     notepad.goToSelectedPage();
/*      */     
/* 5869 */     dispatcher.redispatch(context, "pfm-editor");
/*      */     
/* 5871 */     return true;
/*      */   }
/*      */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\PfmHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */