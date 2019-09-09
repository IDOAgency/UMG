/*      */ package WEB-INF.classes.com.universal.milestone;
/*      */ 
/*      */ import com.techempower.ComponentLog;
/*      */ import com.techempower.gemini.Context;
/*      */ import com.techempower.gemini.Dispatcher;
/*      */ import com.techempower.gemini.FormCheckBox;
/*      */ import com.techempower.gemini.FormDateField;
/*      */ import com.techempower.gemini.FormDropDownMenu;
/*      */ import com.techempower.gemini.FormElement;
/*      */ import com.techempower.gemini.FormHidden;
/*      */ import com.techempower.gemini.FormRadioButtonGroup;
/*      */ import com.techempower.gemini.FormTextArea;
/*      */ import com.techempower.gemini.FormTextField;
/*      */ import com.techempower.gemini.FormValidation;
/*      */ import com.techempower.gemini.GeminiApplication;
/*      */ import com.universal.milestone.Bom;
/*      */ import com.universal.milestone.BomHandler;
/*      */ import com.universal.milestone.Cache;
/*      */ import com.universal.milestone.Company;
/*      */ import com.universal.milestone.CompanyAcl;
/*      */ import com.universal.milestone.CorporateStructureObject;
/*      */ import com.universal.milestone.Division;
/*      */ import com.universal.milestone.EmailDistribution;
/*      */ import com.universal.milestone.Environment;
/*      */ import com.universal.milestone.EnvironmentAcl;
/*      */ import com.universal.milestone.Form;
/*      */ import com.universal.milestone.Genre;
/*      */ import com.universal.milestone.ImpactDate;
/*      */ import com.universal.milestone.Label;
/*      */ import com.universal.milestone.LookupObject;
/*      */ import com.universal.milestone.MilestoneConstants;
/*      */ import com.universal.milestone.MilestoneHelper;
/*      */ import com.universal.milestone.MilestoneHelper_2;
/*      */ import com.universal.milestone.MultOtherContact;
/*      */ import com.universal.milestone.MultSelection;
/*      */ import com.universal.milestone.Notepad;
/*      */ import com.universal.milestone.NotepadSortOrder;
/*      */ import com.universal.milestone.Pfm;
/*      */ import com.universal.milestone.PfmHandler;
/*      */ import com.universal.milestone.Plant;
/*      */ import com.universal.milestone.PnrCommunication;
/*      */ import com.universal.milestone.PrefixCode;
/*      */ import com.universal.milestone.ProductCategory;
/*      */ import com.universal.milestone.ProjectSearch;
/*      */ import com.universal.milestone.ProjectSearchManager;
/*      */ import com.universal.milestone.ReleaseType;
/*      */ import com.universal.milestone.ReleasingFamily;
/*      */ import com.universal.milestone.Schedule;
/*      */ import com.universal.milestone.ScheduleManager;
/*      */ import com.universal.milestone.ScheduledTask;
/*      */ import com.universal.milestone.SecureHandler;
/*      */ import com.universal.milestone.Selection;
/*      */ import com.universal.milestone.SelectionConfiguration;
/*      */ import com.universal.milestone.SelectionHandler;
/*      */ import com.universal.milestone.SelectionManager;
/*      */ import com.universal.milestone.SelectionStatus;
/*      */ import com.universal.milestone.User;
/*      */ import com.universal.milestone.UserManager;
/*      */ import com.universal.milestone.projectSearchSvcClient;
/*      */ import java.rmi.RemoteException;
/*      */ import java.util.Calendar;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Vector;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class SelectionHandler
/*      */   extends SecureHandler
/*      */ {
/*      */   public static final String COMPONENT_CODE = "hSel";
/*      */   public GeminiApplication application;
/*      */   public ComponentLog log;
/*      */   
/*      */   public SelectionHandler(GeminiApplication application) {
/*  147 */     this.application = application;
/*  148 */     this.log = application.getLog("hSel");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  156 */   public String getDescription() { return "Selection"; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean acceptRequest(Dispatcher dispatcher, Context context, String command) {
/*  168 */     if (super.acceptRequest(dispatcher, context, command))
/*      */     {
/*  170 */       if (command.startsWith("selection"))
/*      */       {
/*  172 */         return handleRequest(dispatcher, context, command);
/*      */       }
/*      */     }
/*  175 */     return false;
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
/*      */   public boolean handleRequest(Dispatcher dispatcher, Context context, String command) {
/*  190 */     if (command.equalsIgnoreCase("selection-impactDate-cancel"))
/*      */     {
/*  192 */       impactEditorCancel(dispatcher, context, command);
/*      */     }
/*  194 */     if (command.equalsIgnoreCase("selection-impactDate-save"))
/*      */     {
/*  196 */       impactEditorSave(dispatcher, context, command);
/*      */     }
/*  198 */     if (command.equalsIgnoreCase("selection-impactDate-delete"))
/*      */     {
/*  200 */       impactEditorDelete(dispatcher, context, command);
/*      */     }
/*  202 */     if (command.equalsIgnoreCase("selection-impactDate-add"))
/*      */     {
/*  204 */       impactEditorAdd(dispatcher, context, command);
/*      */     }
/*  206 */     if (command.equalsIgnoreCase("selection-impactDate-editor"))
/*      */     {
/*  208 */       impactEditor(dispatcher, context, command);
/*      */     }
/*  210 */     if (command.equalsIgnoreCase("selection-impactDate-frame")) {
/*      */       
/*  212 */       User user = (User)context.getSession().getAttribute("user");
/*  213 */       Selection selection = (Selection)context.getSessionValue("Selection");
/*  214 */       int secureLevel = getSelectionPermissions(selection, user);
/*  215 */       setButtonVisibilities(selection, user, context, secureLevel, command);
/*      */       
/*  217 */       return context.includeJSP("impactDateFrame.jsp");
/*      */     } 
/*      */ 
/*      */     
/*  221 */     if (command.equalsIgnoreCase("selection-multSelection-cancel"))
/*      */     {
/*  223 */       multSelectionEditorCancel(dispatcher, context, command);
/*      */     }
/*  225 */     if (command.equalsIgnoreCase("selection-multSelection-save"))
/*      */     {
/*  227 */       multSelectionEditorSave(dispatcher, context, command);
/*      */     }
/*  229 */     if (command.equalsIgnoreCase("selection-multSelection-delete"))
/*      */     {
/*  231 */       multSelectionEditorDelete(dispatcher, context, command);
/*      */     }
/*  233 */     if (command.equalsIgnoreCase("selection-multSelection-add"))
/*      */     {
/*  235 */       multSelectionEditorAdd(dispatcher, context, command);
/*      */     }
/*  237 */     if (command.equalsIgnoreCase("selection-multSelection-editor"))
/*      */     {
/*  239 */       multSelectionEditor(dispatcher, context, command);
/*      */     }
/*  241 */     if (command.equalsIgnoreCase("selection-multSelection-frame")) {
/*      */       
/*  243 */       User user = (User)context.getSession().getAttribute("user");
/*  244 */       Selection selection = (Selection)context.getSessionValue("Selection");
/*  245 */       int secureLevel = getSelectionPermissions(selection, user);
/*  246 */       setButtonVisibilities(selection, user, context, secureLevel, command);
/*      */       
/*  248 */       return context.includeJSP("multSelectionFrame.jsp");
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  253 */     if (command.equalsIgnoreCase("selection-multOtherContact-cancel"))
/*      */     {
/*  255 */       multOtherContactEditorCancel(dispatcher, context, command);
/*      */     }
/*  257 */     if (command.equalsIgnoreCase("selection-multOtherContact-save"))
/*      */     {
/*  259 */       multOtherContactEditorSave(dispatcher, context, command);
/*      */     }
/*  261 */     if (command.equalsIgnoreCase("selection-multOtherContact-delete"))
/*      */     {
/*  263 */       multOtherContactEditorDelete(dispatcher, context, command);
/*      */     }
/*  265 */     if (command.equalsIgnoreCase("selection-multOtherContact-add"))
/*      */     {
/*  267 */       multOtherContactEditorAdd(dispatcher, context, command);
/*      */     }
/*  269 */     if (command.equalsIgnoreCase("selection-multOtherContact-editor"))
/*      */     {
/*  271 */       multOtherContactEditor(dispatcher, context, command);
/*      */     }
/*  273 */     if (command.equalsIgnoreCase("selection-multOtherContact-frame")) {
/*      */       
/*  275 */       User user = (User)context.getSession().getAttribute("user");
/*  276 */       Selection selection = (Selection)context.getSessionValue("Selection");
/*  277 */       int secureLevel = getSelectionPermissions(selection, user);
/*  278 */       setButtonVisibilities(selection, user, context, secureLevel, command);
/*      */       
/*  280 */       return context.includeJSP("multOtherContactFrame.jsp");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  286 */     if (command.equalsIgnoreCase("selection-search"))
/*      */     {
/*  288 */       search(dispatcher, context, command);
/*      */     }
/*  290 */     if (command.equalsIgnoreCase("selection-sort"))
/*      */     {
/*  292 */       sort(dispatcher, context, command);
/*      */     }
/*  294 */     if (command.equalsIgnoreCase("selection-group"))
/*      */     {
/*  296 */       sortGroup(dispatcher, context, command);
/*      */     }
/*      */     
/*  299 */     if (command.equalsIgnoreCase("selection-manufacturing-sort")) {
/*      */       
/*  301 */       sort(dispatcher, context, command);
/*      */     }
/*  303 */     else if (command.equalsIgnoreCase("selection-manufacturing-group")) {
/*      */       
/*  305 */       sortGroup(dispatcher, context, command);
/*      */     }
/*  307 */     else if (command.equalsIgnoreCase("selection-editor")) {
/*      */       
/*  309 */       edit(dispatcher, context, command);
/*      */     }
/*  311 */     else if (command.equalsIgnoreCase("selection-edit-save")) {
/*      */       
/*  313 */       editSave(dispatcher, context, command);
/*      */     }
/*  315 */     else if (command.equalsIgnoreCase("selection-edit-delete")) {
/*      */       
/*  317 */       editDelete(dispatcher, context, command);
/*      */     
/*      */     }
/*  320 */     else if (command.equalsIgnoreCase("selection-edit-copy") || command.equalsIgnoreCase("selection-edit-copy-digital")) {
/*      */       
/*  322 */       editCopy(dispatcher, context, command);
/*      */     }
/*  324 */     else if (command.equalsIgnoreCase("selection-edit-new") || command.equalsIgnoreCase("selection-edit-new-digital")) {
/*      */       
/*  326 */       editNew(dispatcher, context, command);
/*      */     } 
/*  328 */     if (command.equalsIgnoreCase("selection-manufacturing-search")) {
/*      */       
/*  330 */       search(dispatcher, context, command);
/*      */     }
/*  332 */     else if (command.equalsIgnoreCase("selection-manufacturing-editor")) {
/*      */       
/*  334 */       manufacturingEdit(dispatcher, context, command);
/*      */     }
/*  336 */     else if (command.equalsIgnoreCase("selection-manufacturing-edit-save")) {
/*      */       
/*  338 */       manufacturingEditSave(dispatcher, context, command);
/*      */     }
/*  340 */     else if (command.equalsIgnoreCase("selection-manufacturing-plant-add")) {
/*      */       
/*  342 */       manufacturingPlantAdd(dispatcher, context, command);
/*      */     }
/*  344 */     else if (command.equalsIgnoreCase("selection-manufacturing-plant-delete")) {
/*      */       
/*  346 */       manufacturingPlantDelete(dispatcher, context, command);
/*      */     }
/*  348 */     else if (command.equalsIgnoreCase("selection-send-pfmbom") || command.equalsIgnoreCase("selection-send-pfm") || command.equalsIgnoreCase("selection-send-bom") || command.equalsIgnoreCase("selection-send-cancel")) {
/*      */       
/*  350 */       sendPfmBom(dispatcher, context, command);
/*      */     }
/*  352 */     else if (command.equalsIgnoreCase("selection-edit-new-physical-archie-project")) {
/*      */       
/*  354 */       editNew(dispatcher, context, command);
/*      */     }
/*  356 */     else if (command.equalsIgnoreCase("selection-edit-new-digital-archie-project")) {
/*      */       
/*  358 */       editNew(dispatcher, context, command);
/*      */     }
/*  360 */     else if (command.equalsIgnoreCase("selection-get-releasing-families")) {
/*      */       
/*  362 */       getReleasingFamilies(dispatcher, context, command);
/*      */     }
/*  364 */     else if (command.equalsIgnoreCase("selection-search-results")) {
/*      */       
/*  366 */       getSelectionSearchResults(dispatcher, context, command);
/*      */     } 
/*      */     
/*  369 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean impactEditor(Dispatcher dispatcher, Context context, String command) {
/*  374 */     Form impactForm = new Form(this.application, "impactForm", 
/*  375 */         this.application.getInfrastructure().getServletURL(), "POST");
/*  376 */     Vector impactDates = null;
/*      */     
/*  378 */     User user = (User)context.getSession().getAttribute("user");
/*  379 */     Selection selection = (Selection)context.getSessionValue("Selection");
/*      */     
/*  381 */     int secureLevel = getSelectionPermissions(selection, user);
/*  382 */     setButtonVisibilities(selection, user, context, secureLevel, command);
/*      */ 
/*      */     
/*  385 */     if (selection != null && selection.getImpactDates() != null) {
/*      */       
/*  387 */       impactDates = selection.getImpactDates();
/*      */       
/*  389 */       for (int j = 0; j < impactDates.size(); j++) {
/*      */         
/*  391 */         ImpactDate impact = (ImpactDate)impactDates.get(j);
/*      */         
/*  393 */         FormDropDownMenu format = MilestoneHelper.getLookupDropDown("format" + j, Cache.getFormats(), impact.getFormat(), true, true);
/*  394 */         FormDateField impactDate = new FormDateField("impactDate" + j, MilestoneHelper.getFormatedDate(impact.getImpactDate()), false, 10);
/*  395 */         impactDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
/*  396 */         FormCheckBox tbi = new FormCheckBox("tbi" + j, "", false, false);
/*  397 */         tbi.setChecked(impact.getTbi());
/*      */         
/*  399 */         impactForm.addElement(format);
/*  400 */         impactForm.addElement(impactDate);
/*  401 */         impactForm.addElement(tbi);
/*      */       } 
/*      */     } 
/*      */     
/*  405 */     impactForm.addElement(new FormHidden("cmd", command, true));
/*      */     
/*  407 */     context.putDelivery("Selection", selection);
/*  408 */     context.putDelivery("Form", impactForm);
/*      */     
/*  410 */     return context.includeJSP("impactDate-editor.jsp");
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean impactEditorAdd(Dispatcher dispatcher, Context context, String command) {
/*  415 */     Form impactForm = new Form(this.application, "impactForm", 
/*  416 */         this.application.getInfrastructure().getServletURL(), "POST");
/*  417 */     User user = (User)context.getSession().getAttribute("user");
/*      */     
/*  419 */     Selection selection = (Selection)context.getSessionValue("Selection");
/*  420 */     Vector impactDates = null;
/*      */     
/*  422 */     impactDates = selection.getImpactDates();
/*      */     
/*  424 */     if (impactDates == null) {
/*      */       
/*  426 */       impactDates = new Vector();
/*  427 */       ImpactDate temp = new ImpactDate();
/*  428 */       temp.setSelectionID(selection.getSelectionID());
/*  429 */       impactDates.add(temp);
/*  430 */       selection.setImpactDates(impactDates);
/*      */     }
/*      */     else {
/*      */       
/*  434 */       ImpactDate temp = new ImpactDate();
/*  435 */       temp.setSelectionID(selection.getSelectionID());
/*  436 */       impactDates.add(temp);
/*  437 */       selection.setImpactDates(impactDates);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  442 */     if (selection != null && selection.getImpactDates() != null)
/*      */     {
/*  444 */       for (int j = 0; j < impactDates.size(); j++) {
/*      */         
/*  446 */         ImpactDate impact = (ImpactDate)impactDates.get(j);
/*      */         
/*  448 */         FormDropDownMenu format = MilestoneHelper.getLookupDropDown("format" + j, Cache.getFormats(), impact.getFormat(), true, true);
/*  449 */         FormDateField impactDate = new FormDateField("impactDate" + j, MilestoneHelper.getFormatedDate(impact.getImpactDate()), false, 10);
/*  450 */         impactDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
/*      */         
/*  452 */         FormCheckBox tbi = new FormCheckBox("tbi" + j, "", false, false);
/*  453 */         tbi.setChecked(impact.getTbi());
/*      */         
/*  455 */         impactForm.addElement(format);
/*  456 */         impactForm.addElement(impactDate);
/*  457 */         impactForm.addElement(tbi);
/*      */       } 
/*      */     }
/*      */     
/*  461 */     impactForm.setValues(context);
/*  462 */     impactForm.addElement(new FormHidden("cmd", command, true));
/*  463 */     context.putSessionValue("Selection", selection);
/*  464 */     context.putDelivery("Form", impactForm);
/*      */     
/*  466 */     return context.includeJSP("impactDate-editor.jsp");
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean impactEditorDelete(Dispatcher dispatcher, Context context, String command) {
/*  471 */     Form impactForm = new Form(this.application, "impactForm", 
/*  472 */         this.application.getInfrastructure().getServletURL(), "POST");
/*  473 */     User user = (User)context.getSession().getAttribute("user");
/*      */     
/*  475 */     Selection selection = (Selection)context.getSessionValue("Selection");
/*  476 */     Vector impactDates = null;
/*  477 */     int impactDateId = -1;
/*      */     
/*  479 */     if (context.getRequestValue("impactDateID") != null) {
/*  480 */       impactDateId = Integer.parseInt(context.getRequestValue("impactDateID"));
/*      */     }
/*  482 */     impactDates = selection.getImpactDates();
/*      */     
/*  484 */     if (selection != null && selection.getImpactDates() != null) {
/*      */       
/*  486 */       if (impactDateId > -1) {
/*  487 */         impactDates.remove(impactDateId);
/*      */       }
/*  489 */       selection.setImpactDates(impactDates);
/*      */       
/*  491 */       for (int j = 0; j < impactDates.size(); j++) {
/*      */         
/*  493 */         ImpactDate impact = (ImpactDate)impactDates.get(j);
/*      */         
/*  495 */         FormDropDownMenu format = MilestoneHelper.getLookupDropDown("format" + j, Cache.getFormats(), impact.getFormat(), true, true);
/*  496 */         FormDateField impactDate = new FormDateField("impactDate" + j, MilestoneHelper.getFormatedDate(impact.getImpactDate()), false, 10);
/*  497 */         impactDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
/*      */         
/*  499 */         FormCheckBox tbi = new FormCheckBox("tbi" + j, "", false, false);
/*  500 */         tbi.setChecked(impact.getTbi());
/*      */         
/*  502 */         impactForm.addElement(format);
/*  503 */         impactForm.addElement(impactDate);
/*  504 */         impactForm.addElement(tbi);
/*      */       } 
/*      */     } 
/*      */     
/*  508 */     impactForm.setValues(context);
/*  509 */     impactForm.addElement(new FormHidden("cmd", command, true));
/*  510 */     context.putSessionValue("Selection", selection);
/*  511 */     context.putDelivery("Form", impactForm);
/*      */     
/*  513 */     return context.includeJSP("impactDate-editor.jsp");
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean impactEditorSave(Dispatcher dispatcher, Context context, String command) {
/*  518 */     Form impactForm = new Form(this.application, "impactForm", 
/*  519 */         this.application.getInfrastructure().getServletURL(), "POST");
/*  520 */     User user = (User)context.getSession().getAttribute("user");
/*      */     
/*  522 */     Selection selection = (Selection)context.getSessionValue("Selection");
/*  523 */     Vector impactDates = null;
/*      */     
/*  525 */     impactDates = selection.getImpactDates();
/*      */ 
/*      */ 
/*      */     
/*  529 */     if (selection != null && selection.getImpactDates() != null) {
/*      */       
/*  531 */       for (int j = 0; j < impactDates.size(); j++) {
/*      */         
/*  533 */         ImpactDate impact = (ImpactDate)impactDates.get(j);
/*      */         
/*  535 */         FormDropDownMenu format = MilestoneHelper.getLookupDropDown("format" + j, Cache.getFormats(), "", true, true);
/*  536 */         FormDateField impactDate = new FormDateField("impactDate" + j, "", false, 10);
/*  537 */         impactDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
/*  538 */         FormCheckBox tbi = new FormCheckBox("tbi" + j, "", false, false);
/*      */         
/*  540 */         impactForm.addElement(format);
/*  541 */         impactForm.addElement(impactDate);
/*  542 */         impactForm.addElement(tbi);
/*      */       } 
/*      */       
/*  545 */       impactForm.setValues(context);
/*      */       
/*  547 */       Vector newImpactDates = new Vector();
/*      */       
/*  549 */       for (int j = 0; j < impactDates.size(); j++) {
/*      */         
/*  551 */         ImpactDate impact = (ImpactDate)impactDates.get(j);
/*      */         
/*  553 */         impact.setFormat(impactForm.getStringValue("format" + j));
/*  554 */         impact.setImpactDate(MilestoneHelper.getDate(impactForm.getStringValue("impactDate" + j)));
/*  555 */         impact.setTbi(((FormCheckBox)impactForm.getElement("tbi" + j)).isChecked());
/*  556 */         newImpactDates.add(impact);
/*      */       } 
/*      */       
/*  559 */       selection.setImpactDates(newImpactDates);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  564 */       if (selection.getSelectionID() > 0) {
/*  565 */         SelectionManager.getInstance().saveSelection(selection, user);
/*      */       }
/*      */     } 
/*      */     
/*  569 */     impactForm.addElement(new FormHidden("cmd", command, true));
/*      */     
/*  571 */     context.putSessionValue("Selection", selection);
/*  572 */     context.putDelivery("Form", impactForm);
/*      */ 
/*      */     
/*  575 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean impactEditorCancel(Dispatcher dispatcher, Context context, String command) {
/*  580 */     Form impactForm = new Form(this.application, "impactForm", 
/*  581 */         this.application.getInfrastructure().getServletURL(), "POST");
/*  582 */     User user = (User)context.getSession().getAttribute("user");
/*      */     
/*  584 */     Selection sessionSelection = (Selection)context.getSessionValue("Selection");
/*      */     
/*  586 */     Selection selection = null;
/*  587 */     if (sessionSelection.getSelectionID() < 0) {
/*  588 */       selection = sessionSelection;
/*      */     } else {
/*  590 */       selection = SelectionManager.getInstance().getSelectionHeader(sessionSelection.getSelectionID());
/*      */     } 
/*  592 */     Vector impactDates = null;
/*      */ 
/*      */     
/*  595 */     if (selection != null) {
/*      */       
/*  597 */       impactDates = selection.getImpactDates();
/*      */       
/*  599 */       if (selection != null && selection.getImpactDates() != null) {
/*      */ 
/*      */         
/*  602 */         Vector newImpactDates = new Vector();
/*      */         
/*  604 */         for (int j = 0; j < impactDates.size(); j++) {
/*      */           
/*  606 */           ImpactDate impact = (ImpactDate)impactDates.get(j);
/*      */ 
/*      */ 
/*      */           
/*  610 */           if (sessionSelection.getSelectionID() >= 0 || 
/*  611 */             impact.getImpactDate() != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  618 */             FormDropDownMenu format = MilestoneHelper.getLookupDropDown("format" + j, Cache.getFormats(), impact.getFormat(), true, true);
/*  619 */             FormDateField impactDate = new FormDateField("impactDate" + j, MilestoneHelper.getFormatedDate(impact.getImpactDate()), false, 10);
/*  620 */             impactDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
/*  621 */             FormCheckBox tbi = new FormCheckBox("tbi" + j, "", false, false);
/*  622 */             tbi.setChecked(impact.getTbi());
/*      */             
/*  624 */             impactForm.addElement(format);
/*  625 */             impactForm.addElement(impactDate);
/*  626 */             impactForm.addElement(tbi);
/*      */             
/*  628 */             newImpactDates.add(impact);
/*      */           } 
/*  630 */         }  selection.setImpactDates(newImpactDates);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  635 */     impactForm.addElement(new FormHidden("cmd", command, true));
/*  636 */     context.putSessionValue("Selection", selection);
/*  637 */     context.putDelivery("Form", impactForm);
/*      */ 
/*      */     
/*  640 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean search(Dispatcher dispatcher, Context context, String command) {
/*  651 */     String isSelectionSearchResults = context.getParameter("isSelectionSearchResults");
/*  652 */     if (isSelectionSearchResults == null || !isSelectionSearchResults.equals("true")) {
/*      */       
/*  654 */       Notepad notepad = MilestoneHelper.getNotepadFromSession(0, context);
/*      */ 
/*      */       
/*  657 */       notepad.setAllContents(null);
/*  658 */       notepad.setSelected(null);
/*      */ 
/*      */       
/*  661 */       notepad.setMaxRecords(225);
/*      */       
/*  663 */       Form form = new Form(this.application, "selectionForm", 
/*  664 */           this.application.getInfrastructure().getServletURL(), 
/*  665 */           "POST");
/*  666 */       Vector companies = MilestoneHelper.getUserCompanies(context);
/*      */ 
/*      */       
/*  669 */       addSelectionSearchElements(context, null, form, companies, false);
/*      */       
/*  671 */       form.setValues(context);
/*      */       
/*  673 */       SelectionManager.getInstance().setSelectionNotepadQuery(context, (
/*  674 */           (User)context.getSessionValue("user")).getUserId(), notepad, form);
/*      */     } 
/*      */     
/*  677 */     if (command.equals("selection-search")) {
/*  678 */       dispatcher.redispatch(context, "selection-editor");
/*      */     } else {
/*      */       
/*  681 */       dispatcher.redispatch(context, "selection-manufacturing-editor");
/*      */     } 
/*      */     
/*  684 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean sort(Dispatcher dispatcher, Context context, String command) {
/*  695 */     Notepad notepad = MilestoneHelper.getNotepadFromSession(0, context);
/*      */ 
/*      */     
/*  698 */     NotepadSortOrder.getNotepadSortOrderFromSession(context).sortHelper(dispatcher, context, notepad);
/*      */     
/*  700 */     if (command.equals("selection-sort")) {
/*      */       
/*  702 */       dispatcher.redispatch(context, "selection-editor");
/*      */     }
/*      */     else {
/*      */       
/*  706 */       dispatcher.redispatch(context, "selection-manufacturing-editor");
/*      */     } 
/*      */     
/*  709 */     return true;
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
/*      */   private boolean edit(Dispatcher dispatcher, Context context, String command) {
/*  735 */     Selection selection = new Selection();
/*  736 */     User user = (User)context.getSessionValue("user");
/*      */ 
/*      */     
/*  739 */     context.removeSessionValue("searchResults");
/*  740 */     context.removeSessionValue("selectionScreenType");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  747 */     Notepad notepad = MilestoneHelper.getNotepadFromSession(0, context);
/*  748 */     if (notepad == null) {
/*      */       
/*  750 */       notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 0);
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  755 */       notepad.setAllContents(null);
/*  756 */       notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 0);
/*      */     } 
/*      */     
/*  759 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */     
/*  761 */     selection = MilestoneHelper.getScreenSelection(context);
/*      */     
/*  763 */     context.putSessionValue("impactSaveVisible", "false");
/*      */ 
/*      */ 
/*      */     
/*  767 */     Form form = null;
/*  768 */     if (selection != null) {
/*      */       
/*  770 */       context.putSessionValue("Selection", selection);
/*  771 */       context.putDelivery("isOkToClose", new Boolean(SelectionManager.getInstance().isSelectionOkToClose(context)));
/*      */       
/*  773 */       if (selection.getIsDigital()) {
/*      */         
/*  775 */         form = buildDigitalForm(context, selection, command);
/*  776 */         context.putDelivery("Form", form);
/*  777 */         return context.includeJSP("digital-selection-editor.jsp");
/*      */       } 
/*      */ 
/*      */       
/*  781 */       form = buildForm(context, selection, command);
/*  782 */       context.putDelivery("Form", form);
/*  783 */       return context.includeJSP("selection-editor.jsp");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  789 */     return goToBlank(context, form, user);
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
/*      */   private boolean editSave(Dispatcher dispatcher, Context context, String command) {
/*  802 */     context.removeSessionValue("searchResults");
/*  803 */     context.removeSessionValue("selectionScreenType");
/*      */ 
/*      */     
/*  806 */     Selection selection = (Selection)context.getSessionValue("Selection");
/*      */ 
/*      */     
/*  809 */     Notepad notepad = SelectionManager.getInstance().getSelectionNotepad(context, ((User)context.getSessionValue("user")).getUserId(), 0);
/*      */     
/*  811 */     int selectionId = -1;
/*      */ 
/*      */     
/*      */     try {
/*  815 */       selectionId = selection.getSelectionID();
/*      */     }
/*  817 */     catch (NullPointerException nullPointerException) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  826 */     boolean isNewSelection = (selectionId < 1);
/*      */     
/*  828 */     Form form = null;
/*      */     
/*  830 */     if (isNewSelection) {
/*      */       
/*  832 */       if (selection.getIsDigital()) {
/*  833 */         form = buildNewDigitalForm(context, selection, command);
/*      */       } else {
/*  835 */         form = buildNewForm(context, selection, command);
/*      */       }
/*      */     
/*      */     }
/*  839 */     else if (selection.getIsDigital()) {
/*  840 */       form = buildDigitalForm(context, selection, command);
/*      */     } else {
/*  842 */       form = buildForm(context, selection, command);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  848 */     if (isNewSelection || (!isNewSelection && SelectionManager.getInstance().isTimestampValid(selection))) {
/*      */       
/*  850 */       form.setValues(context);
/*      */       
/*  852 */       String streetDateString = form.getStringValue("streetDate");
/*      */       
/*  854 */       String internationalDateString = form.getStringValue("internationalDate");
/*  855 */       String impactDateString = form.getStringValue("impactdate");
/*  856 */       String selectionStatus = form.getStringValue("status");
/*  857 */       String holdReason = form.getStringValue("holdReason");
/*      */       
/*  859 */       String packagingHelper = form.getStringValue("PackagingHelper");
/*  860 */       String territoryHelper = form.getStringValue("TerritoryHelper");
/*  861 */       String projectId = form.getStringValue("projectId");
/*  862 */       String prefix = form.getStringValue("prefix");
/*      */       
/*  864 */       String titleId = "";
/*  865 */       String selectionNo = form.getStringValue("selectionNo");
/*      */       
/*  867 */       String upc = form.getStringValue("UPC");
/*  868 */       String artistFirstName = form.getStringValue("artistFirstName");
/*  869 */       String artistLastName = form.getStringValue("artistLastName");
/*  870 */       String artist = "";
/*      */ 
/*      */ 
/*      */       
/*  874 */       if (artistFirstName == null || artistFirstName.equals("")) {
/*      */         
/*  876 */         artist = artistLastName;
/*      */       }
/*  878 */       else if (artistLastName == null || artistLastName.equals("")) {
/*      */         
/*  880 */         artist = artistFirstName;
/*      */       }
/*      */       else {
/*      */         
/*  884 */         artist = String.valueOf(artistFirstName) + " " + artistLastName;
/*      */       } 
/*  886 */       String title = form.getStringValue("title");
/*  887 */       String sideATitle = "";
/*  888 */       if (form.getStringValue("sideATitle") != null)
/*  889 */         sideATitle = form.getStringValue("sideATitle"); 
/*  890 */       String sideBTitle = form.getStringValue("sideBTitle");
/*  891 */       String releaseType = form.getStringValue("releaseType");
/*  892 */       String configuration = form.getStringValue("configuration");
/*  893 */       String subConfiguration = form.getStringValue("subConfiguration");
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  898 */       String productLine = form.getStringValue("productLine");
/*  899 */       String environment = form.getStringValue("environment");
/*  900 */       String company = form.getStringValue("company");
/*  901 */       String division = form.getStringValue("division");
/*  902 */       String label = form.getStringValue("label");
/*  903 */       String sellCode = form.getStringValue("priceCode");
/*  904 */       String sellCodeDPC = form.getStringValue("priceCodeDPC");
/*  905 */       String genre = form.getStringValue("genre");
/*  906 */       String pkg = form.getStringValue("package");
/*  907 */       String territory = form.getStringValue("territory");
/*  908 */       String contactlist = form.getStringValue("contactlist");
/*  909 */       String otherContact = form.getStringValue("contact");
/*  910 */       String comments = form.getStringValue("comments");
/*  911 */       String priceCode = form.getStringValue("priceCode");
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  916 */       int numOfUnits = 0;
/*      */       
/*      */       try {
/*  919 */         numOfUnits = Integer.parseInt(form.getStringValue("numOfUnits"));
/*      */       }
/*  921 */       catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */       
/*  925 */       String digitalRlsDateString = form.getStringValue("digitalDate");
/*      */       
/*  927 */       String operCompany = form.getStringValue("opercompany");
/*  928 */       String superLabel = form.getStringValue("superlabel");
/*  929 */       String subLabel = form.getStringValue("sublabel");
/*  930 */       String configCode = form.getStringValue("configcode");
/*  931 */       String soundscanGrp = form.getStringValue("soundscan");
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  936 */       String imprint = form.getStringValue("imprint");
/*      */ 
/*      */       
/*  939 */       if (selection.getSellCode() != null && !selection.getSellCode().equalsIgnoreCase(sellCode))
/*      */       {
/*  941 */         selection.setPriceCode(SelectionManager.getInstance().getPriceCode(sellCode));
/*      */       }
/*      */ 
/*      */       
/*  945 */       if (selection.getSellCodeDPC() != null && !selection.getSellCodeDPC().equalsIgnoreCase(sellCodeDPC))
/*      */       {
/*  947 */         selection.setPriceCodeDPC(SelectionManager.getInstance().getPriceCode(sellCodeDPC));
/*      */       }
/*      */       
/*  950 */       String releasingFamily = form.getStringValue("releasingFamily");
/*      */       
/*  952 */       selection.setNumberOfUnits(numOfUnits);
/*  953 */       selection.setSelectionNo(selectionNo);
/*  954 */       selection.setProjectID(projectId);
/*  955 */       selection.setTitleID(titleId);
/*  956 */       selection.setTitle(title);
/*  957 */       selection.setArtistFirstName(artistFirstName);
/*  958 */       selection.setArtistLastName(artistLastName);
/*  959 */       selection.setArtist(artist);
/*  960 */       selection.setASide(sideATitle);
/*  961 */       selection.setBSide(sideBTitle);
/*  962 */       selection.setProductCategory((ProductCategory)SelectionManager.getLookupObject(productLine, Cache.getProductCategories()));
/*  963 */       selection.setReleaseType((ReleaseType)SelectionManager.getLookupObject(releaseType, Cache.getReleaseTypes()));
/*  964 */       selection.setSelectionConfig(SelectionManager.getSelectionConfigObject(configuration, Cache.getSelectionConfigs()));
/*  965 */       selection.setSelectionSubConfig(SelectionManager.getSelectionSubConfigObject(subConfiguration, selection.getSelectionConfig()));
/*  966 */       selection.setUpc(upc);
/*  967 */       selection.setSellCode(sellCode);
/*  968 */       selection.setSellCodeDPC(sellCodeDPC);
/*      */       
/*  970 */       selection.setGenre((Genre)SelectionManager.getLookupObject(genre, Cache.getMusicTypes()));
/*  971 */       System.out.println(">>>>>>>>>>>>>>>>>>>>>>> Company: " + company);
/*  972 */       System.out.println(">>>>>>>>>>>>>>>>>>>>>>> enviroment: " + environment);
/*  973 */       selection.setEnvironment((Environment)MilestoneHelper.getStructureObject(Integer.parseInt(environment)));
/*  974 */       selection.setCompany((Company)MilestoneHelper.getStructureObject(Integer.parseInt(company)));
/*  975 */       selection.setDivision((Division)MilestoneHelper.getStructureObject(Integer.parseInt(division)));
/*  976 */       selection.setLabel((Label)MilestoneHelper.getStructureObject(Integer.parseInt(label)));
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/*  981 */         selection.setReleaseFamilyId(Integer.parseInt(releasingFamily));
/*      */       }
/*  983 */       catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  988 */       Calendar oldStreetDate = null;
/*      */ 
/*      */       
/*  991 */       if (selection.getIsDigital()) {
/*  992 */         oldStreetDate = selection.getDigitalRlsDate();
/*      */       } else {
/*  994 */         oldStreetDate = selection.getStreetDate();
/*      */       } 
/*  996 */       boolean isStreetDateDifferent = false;
/*      */ 
/*      */       
/*      */       try {
/* 1000 */         Calendar streetDate = null;
/*      */         
/* 1002 */         if (selection.getIsDigital()) {
/* 1003 */           streetDate = MilestoneHelper.getDate(digitalRlsDateString);
/*      */         } else {
/* 1005 */           streetDate = MilestoneHelper.getDate(streetDateString);
/*      */         } 
/* 1007 */         String oldStreetDateString = MilestoneHelper.getFormatedDate(oldStreetDate);
/* 1008 */         String newStreetDateString = MilestoneHelper.getFormatedDate(streetDate);
/*      */         
/* 1010 */         if ((oldStreetDate == null && streetDate != null) || (oldStreetDate != null && !oldStreetDateString.equals(newStreetDateString))) {
/*      */           
/* 1012 */           selection.setLastStreetUpdateDate(Calendar.getInstance());
/* 1013 */           isStreetDateDifferent = true;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1018 */         selection.setDigitalRlsDate(MilestoneHelper.getDate(digitalRlsDateString));
/* 1019 */         selection.setDigitalRlsDateString(MilestoneHelper.getFormatedDate(MilestoneHelper.getDate(digitalRlsDateString)));
/*      */ 
/*      */ 
/*      */         
/* 1023 */         selection.setStreetDate(MilestoneHelper.getDate(streetDateString));
/* 1024 */         selection.setStreetDateString(MilestoneHelper.getFormatedDate(MilestoneHelper.getDate(streetDateString)));
/*      */       
/*      */       }
/* 1027 */       catch (Exception e) {
/*      */         
/* 1029 */         selection.setStreetDate(null);
/* 1030 */         selection.setDigitalRlsDate(null);
/*      */       } 
/*      */       
/* 1033 */       boolean isScheduleApplied = false;
/* 1034 */       isScheduleApplied = SelectionManager.getInstance().isScheduleApplied(selection);
/*      */       
/* 1036 */       Calendar internationalDate = Calendar.getInstance();
/*      */ 
/*      */       
/*      */       try {
/* 1040 */         internationalDate = MilestoneHelper.getDate(internationalDateString);
/* 1041 */         selection.setInternationalDate(internationalDate);
/*      */       }
/* 1043 */       catch (Exception e) {
/*      */         
/* 1045 */         selection.setInternationalDate(null);
/*      */       } 
/*      */       
/* 1048 */       selection.setOtherContact(otherContact);
/*      */       
/* 1050 */       int contactUser = 0;
/*      */       
/* 1052 */       if (contactlist != null && !contactlist.equals("")) contactUser = Integer.parseInt(contactlist);
/*      */       
/* 1054 */       selection.setLabelContact(UserManager.getInstance().getUser(contactUser));
/* 1055 */       selection.setSelectionStatus((SelectionStatus)SelectionManager.getLookupObject(selectionStatus, Cache.getSelectionStatusList()));
/* 1056 */       selection.setHoldReason(holdReason);
/* 1057 */       selection.setComments(comments);
/* 1058 */       selection.setSelectionPackaging(pkg);
/* 1059 */       selection.setSelectionTerritory(territory);
/* 1060 */       selection.setPrefixID((PrefixCode)SelectionManager.getLookupObject(prefix, Cache.getPrefixCodes()));
/*      */ 
/*      */       
/* 1063 */       selection.setPressAndDistribution(((FormCheckBox)form.getElement("pdIndicator")).isChecked());
/* 1064 */       selection.setSpecialPackaging(((FormCheckBox)form.getElement("specialPkgIndicator")).isChecked());
/* 1065 */       selection.setHoldSelection(((FormCheckBox)form.getElement("holdIndicator")).isChecked());
/*      */       
/* 1067 */       if (!selection.getIsDigital()) {
/* 1068 */         selection.setNoDigitalRelease(((FormCheckBox)form.getElement("noDigitalRelease")).isChecked());
/*      */       }
/*      */ 
/*      */       
/* 1072 */       selection.setParentalGuidance(((FormCheckBox)form.getElement("parentalIndicator")).isChecked());
/*      */       
/* 1074 */       Calendar impactDate = Calendar.getInstance();
/*      */ 
/*      */       
/*      */       try {
/* 1078 */         impactDate = MilestoneHelper.getDate(impactDateString);
/* 1079 */         selection.setImpactDate(impactDate);
/*      */       }
/* 1081 */       catch (Exception e) {
/*      */         
/* 1083 */         selection.setImpactDate(null);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1088 */       Calendar digitalRlsDate = Calendar.getInstance();
/*      */       try {
/* 1090 */         digitalRlsDate = MilestoneHelper.getDate(digitalRlsDateString);
/* 1091 */         selection.setDigitalRlsDate(digitalRlsDate);
/* 1092 */       } catch (Exception e) {
/* 1093 */         selection.setDigitalRlsDate(null);
/*      */       } 
/* 1095 */       selection.setInternationalFlag(((FormCheckBox)form.getElement("intlFlag")).isChecked());
/* 1096 */       selection.setOperCompany(operCompany);
/* 1097 */       selection.setSuperLabel(superLabel);
/* 1098 */       selection.setSubLabel(subLabel);
/* 1099 */       selection.setConfigCode(configCode);
/* 1100 */       selection.setSoundScanGrp(soundscanGrp);
/*      */ 
/*      */       
/* 1103 */       selection.setImprint(imprint);
/*      */ 
/*      */       
/* 1106 */       if (selection.getIsDigital()) {
/*      */ 
/*      */         
/* 1109 */         String newBundleString = form.getStringValue("newBundle");
/* 1110 */         if (newBundleString.equalsIgnoreCase("true")) {
/* 1111 */           selection.setNewBundleFlag(true);
/*      */         } else {
/* 1113 */           selection.setNewBundleFlag(false);
/*      */         } 
/* 1115 */         String specialInstructions = form.getStringValue("specialInstructions");
/* 1116 */         selection.setSpecialInstructions(specialInstructions);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1123 */         selection.setPriority(((FormCheckBox)form.getElement("priority")).isChecked());
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1128 */       String gridNumber = form.getStringValue("gridNumber");
/* 1129 */       selection.setGridNumber(gridNumber);
/*      */ 
/*      */ 
/*      */       
/* 1133 */       boolean isPNR = false;
/* 1134 */       if (context.getParameter("generateSelection") != null && (
/* 1135 */         context.getParameter("generateSelection").equalsIgnoreCase("LPNG") || 
/* 1136 */         context.getParameter("generateSelection").equalsIgnoreCase("TPNG"))) {
/* 1137 */         isPNR = true;
/*      */       }
/*      */       
/* 1140 */       String strResponse = "";
/* 1141 */       strResponse = applyBusinessRules(form, context, selection);
/*      */       
/* 1143 */       if (!strResponse.trim().equals("")) {
/* 1144 */         context.putDelivery("AlertMessage", strResponse.trim());
/* 1145 */         form.setValues(context);
/* 1146 */         User user = (User)context.getSession().getAttribute("user");
/* 1147 */         int userId = user.getUserId();
/* 1148 */         int secureLevel = getSelectionPermissions(selection, user);
/* 1149 */         setButtonVisibilities(selection, user, context, secureLevel, "new");
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1154 */         FormHidden opCo = (FormHidden)form.getElement("opercompany");
/*      */         
/* 1156 */         LookupObject oc = MilestoneHelper.getLookupObject(selection
/* 1157 */             .getOperCompany(), Cache.getOperatingCompanies());
/* 1158 */         String ocAbbr = "";
/* 1159 */         String ocName = "";
/* 1160 */         if (oc != null && oc.getAbbreviation() != null)
/* 1161 */           ocAbbr = oc.getAbbreviation(); 
/* 1162 */         if (oc != null && oc.getName() != null) {
/* 1163 */           ocName = oc.getName();
/*      */         }
/* 1165 */         opCo.setDisplayName(String.valueOf(ocAbbr) + ":" + ocName);
/*      */         
/* 1167 */         if (ocAbbr.equals("ZZ")) {
/* 1168 */           opCo.setDisplayName(ocAbbr);
/*      */         }
/*      */         
/* 1171 */         FormHidden projectID = (FormHidden)form.getElement("projectId");
/* 1172 */         projectID.setDisplayName(String.valueOf(selection.getProjectID()));
/*      */ 
/*      */         
/* 1175 */         context.putDelivery("releaseWeek", MilestoneHelper.getReleaseWeekString(selection));
/*      */ 
/*      */         
/* 1178 */         Vector releaseFamilies = ReleasingFamily.getReleasingFamilies(userId, selection.getEnvironment().getParentFamily().getStructureID(), context);
/* 1179 */         FormDropDownMenu myReleasingFamily = MilestoneHelper.getReleasingFamilyDropDown("releasingFamily", Integer.toString(selection.getReleaseFamilyId()), releaseFamilies, true, selection);
/* 1180 */         form.removeElement("releasingFamily");
/*      */         
/* 1182 */         myReleasingFamily.addFormEvent("onChange", "ReleasingFamilyChange(this)");
/* 1183 */         form.addElement(myReleasingFamily);
/*      */ 
/*      */         
/* 1186 */         String envId = "";
/* 1187 */         String envName = "";
/* 1188 */         if (selection.getEnvironment() != null) {
/* 1189 */           envId = Integer.toString(selection.getEnvironment().getStructureID());
/* 1190 */           envName = selection.getEnvironment().getName();
/*      */         } 
/* 1192 */         FormHidden myEnv = new FormHidden("environment", envId, false);
/* 1193 */         myEnv.setDisplayName(envName);
/* 1194 */         form.removeElement("environment");
/* 1195 */         form.addElement(myEnv);
/*      */ 
/*      */         
/* 1198 */         String companyId = "";
/* 1199 */         String companyName = "";
/*      */ 
/*      */         
/* 1202 */         if (selection.getCompany() != null) {
/* 1203 */           companyId = Integer.toString(selection.getCompany().getStructureID());
/* 1204 */           companyName = selection.getCompany().getName();
/*      */         } 
/* 1206 */         FormHidden myCcompany = new FormHidden("company", companyId, false);
/* 1207 */         myCcompany.setTabIndex(15);
/* 1208 */         myCcompany.setDisplayName(companyName);
/*      */         
/* 1210 */         myCcompany.addFormEvent("onClick", "return(clickCompany(this))");
/* 1211 */         form.removeElement("company");
/* 1212 */         form.addElement(myCcompany);
/*      */ 
/*      */         
/* 1215 */         String divisionId = "";
/* 1216 */         String divisionName = "";
/* 1217 */         if (selection.getDivision() != null) {
/* 1218 */           divisionId = Integer.toString(selection.getDivision().getStructureID());
/* 1219 */           divisionName = selection.getDivision().getName();
/*      */         } else {
/*      */           
/* 1222 */           divisionId = "";
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1231 */         FormHidden myDivision = new FormHidden("division", divisionId, false);
/* 1232 */         myDivision.setTabIndex(16);
/* 1233 */         myDivision.setDisplayName(divisionName);
/*      */         
/* 1235 */         myDivision.addFormEvent("onChange", "return(clickDivision(this))");
/*      */         
/* 1237 */         form.removeElement("division");
/* 1238 */         form.addElement(myDivision);
/*      */ 
/*      */         
/* 1241 */         String labelId = "";
/* 1242 */         String labelName = "";
/* 1243 */         if (selection.getLabel() != null) {
/* 1244 */           labelId = Integer.toString(selection.getLabel().getStructureID());
/* 1245 */           labelName = selection.getLabel().getName();
/*      */         } else {
/* 1247 */           labelId = "";
/*      */         } 
/* 1249 */         FormHidden myLabel = new FormHidden("label", labelId, false);
/* 1250 */         myLabel.setTabIndex(17);
/* 1251 */         myLabel.setDisplayName(labelName);
/*      */         
/* 1253 */         form.removeElement("label");
/* 1254 */         form.addElement(myLabel);
/*      */ 
/*      */ 
/*      */         
/* 1258 */         String subConfigValue = "";
/* 1259 */         if (selection.getSelectionSubConfig() != null) subConfigValue = selection.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation(); 
/* 1260 */         FormDropDownMenu mySubConfiguration = MilestoneHelper.getSelectionSubConfigurationDropDown("subConfiguration", selection.getSelectionConfig(), subConfigValue, true);
/* 1261 */         mySubConfiguration.addFormEvent("onChange", "isSubConfigParent(this.selectedIndex)");
/* 1262 */         mySubConfiguration.setTabIndex(30);
/* 1263 */         mySubConfiguration.setClassName("ctrlMedium");
/* 1264 */         form.removeElement("subConfiguration");
/* 1265 */         form.addElement(mySubConfiguration);
/*      */         
/* 1267 */         context.putDelivery("Form", form);
/*      */         
/* 1269 */         if (selection.getIsDigital()) {
/* 1270 */           return context.includeJSP("digital-selection-editor.jsp");
/*      */         }
/* 1272 */         return context.includeJSP("selection-editor.jsp");
/*      */       } 
/*      */       
/* 1275 */       if (!form.isUnchanged()) {
/*      */         
/* 1277 */         FormValidation formValidation = form.validate();
/* 1278 */         if (formValidation.isGood()) {
/*      */           
/* 1280 */           User user = (User)context.getSessionValue("user");
/* 1281 */           System.out.println(">>>>>>>>>>>>>>>>>>>>>>> Company: " + selection.getCompanyId());
/* 1282 */           System.out.println(">>>>>>>>>>>>>>>>>>>>>>> enviroment: " + selection.getEnvironmentId());
/* 1283 */           Selection savedSelection = SelectionManager.getInstance().saveSelection(selection, user);
/*      */ 
/*      */           
/* 1286 */           if (!form.getElement("label").getStartingValue().equalsIgnoreCase(form.getElement("label").getStringValue()) || 
/* 1287 */             !form.getElement("releasingFamily").getStartingValue().equalsIgnoreCase(form.getElement("releasingFamily").getStringValue())) {
/* 1288 */             Cache.flushUsedLabels();
/*      */           }
/*      */           
/* 1291 */           FormElement lastUpdated = form.getElement("lastupdateddate");
/* 1292 */           lastUpdated.setValue(MilestoneHelper.getLongDate(savedSelection.getLastUpdateDate()));
/*      */ 
/*      */           
/* 1295 */           notepad.setAllContents(null);
/*      */           
/* 1297 */           if (isNewSelection) {
/* 1298 */             notepad.newSelectedReset();
/*      */           }
/* 1300 */           notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 0);
/* 1301 */           notepad.setSelected(savedSelection);
/* 1302 */           selection = (Selection)notepad.validateSelected();
/*      */ 
/*      */           
/* 1305 */           if (selection == null && notepad.getMaxRecords() < notepad.getTotalRecords() && notepad.getMaxRecords() > 0) {
/*      */             
/* 1307 */             notepad.setMaxRecords(0);
/* 1308 */             notepad.setAllContents(null);
/* 1309 */             notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 0);
/* 1310 */             notepad.setSelected(savedSelection);
/* 1311 */             selection = (Selection)notepad.validateSelected();
/*      */           } 
/*      */           
/* 1314 */           context.putSessionValue("Selection", selection);
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1319 */           if (form.getStringValue("UPC") != null) {
/*      */             
/* 1321 */             String upcStripped = MilestoneHelper_2.reformat_UPC_SSG_SGC_forSave(form.getStringValue("UPC"), "UPC", savedSelection.getIsDigital(), true);
/* 1322 */             form.getElement("UPC").setValue(upcStripped);
/*      */           } 
/* 1324 */           if (form.getStringValue("soundscan") != null) {
/*      */             
/* 1326 */             String ssgStripped = MilestoneHelper_2.reformat_UPC_SSG_SGC_forSave(form.getStringValue("soundscan"), "SSG", savedSelection.getIsDigital(), true);
/* 1327 */             form.getElement("soundscan").setValue(ssgStripped);
/*      */           } 
/*      */ 
/*      */           
/* 1331 */           boolean isPfmChange = false;
/* 1332 */           boolean isBomChange = false;
/*      */ 
/*      */           
/* 1335 */           if (!form.getStringValue("selectionNo").equalsIgnoreCase(form.getElement("selectionNo").getStartingValue())) {
/*      */             
/* 1337 */             isPfmChange = true;
/* 1338 */             isBomChange = true;
/*      */           } 
/*      */ 
/*      */           
/* 1342 */           String prefixStartingValue = "-1";
/* 1343 */           if (!form.getElement("prefix").getStartingValue().equals("")) {
/* 1344 */             prefixStartingValue = form.getElement("prefix").getStartingValue();
/*      */           }
/* 1346 */           if (!form.getStringValue("prefix").equalsIgnoreCase(prefixStartingValue)) {
/*      */             
/* 1348 */             isPfmChange = true;
/* 1349 */             isBomChange = true;
/*      */           } 
/*      */ 
/*      */           
/* 1353 */           if (!form.getStringValue("UPC").equalsIgnoreCase(form.getElement("UPC").getStartingValue())) {
/*      */             
/* 1355 */             isPfmChange = true;
/* 1356 */             isBomChange = true;
/*      */           } 
/*      */ 
/*      */           
/* 1360 */           if (!savedSelection.getIsDigital() && !form.getStringValue("titleId").equalsIgnoreCase(form.getElement("titleId").getStartingValue())) {
/* 1361 */             isPfmChange = true;
/*      */           }
/*      */           
/* 1364 */           if (!savedSelection.getIsDigital() && !form.getStringValue("streetDate").equalsIgnoreCase(form.getElement("streetDate").getStartingValue())) {
/*      */             
/* 1366 */             isPfmChange = true;
/* 1367 */             isBomChange = true;
/*      */           } 
/*      */ 
/*      */           
/* 1371 */           if (savedSelection.getIsDigital() && !form.getStringValue("digitalDate").equalsIgnoreCase(form.getElement("digitalDate").getStartingValue())) {
/*      */             
/* 1373 */             isPfmChange = true;
/* 1374 */             isBomChange = true;
/*      */           } 
/*      */ 
/*      */           
/* 1378 */           if (!form.getStringValue("status").equalsIgnoreCase(form.getElement("status").getStartingValue()) && !form.getStringValue("status").equalsIgnoreCase("Closed")) {
/*      */ 
/*      */             
/* 1381 */             isPfmChange = true;
/* 1382 */             isBomChange = true;
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/* 1387 */           if (!form.getStringValue("status").equalsIgnoreCase(form.getElement("status").getStartingValue()) && form.getStringValue("status").equalsIgnoreCase("Closed")) {
/*      */             
/* 1389 */             Schedule schedule = null;
/*      */             
/* 1391 */             if (savedSelection != null) {
/* 1392 */               schedule = ScheduleManager.getInstance().getSchedule(savedSelection.getSelectionID());
/*      */             }
/* 1394 */             if (schedule != null) {
/*      */               
/* 1396 */               Vector tasks = schedule.getTasks();
/* 1397 */               if (tasks != null) {
/*      */                 
/* 1399 */                 ScheduledTask task = null;
/*      */                 
/* 1401 */                 Calendar labelCmpDt = MilestoneHelper.getDate("9/9/99");
/*      */                 
/* 1403 */                 for (int i = 0; i < tasks.size(); i++) {
/*      */                   
/* 1405 */                   task = (ScheduledTask)tasks.get(i);
/*      */ 
/*      */ 
/*      */                   
/* 1409 */                   if (task.getCompletionDate() == null && !MilestoneHelper.isUml(task) && !MilestoneHelper.isEcommerce(task)) {
/* 1410 */                     ScheduleManager.getInstance().UpdateCompletionDate(task.releaseID, task.taskID, user.userId, labelCmpDt);
/*      */                   }
/* 1412 */                   task = null;
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */ 
/*      */           
/* 1419 */           if (!form.getStringValue("projectId").equalsIgnoreCase(form.getElement("projectId").getStartingValue())) {
/* 1420 */             isPfmChange = true;
/*      */           }
/*      */           
/* 1423 */           if (form.getStringValue("priceCode") != null) {
/*      */ 
/*      */             
/* 1426 */             if (form.getElement("priceCode").getStartingValue().equals("")) {
/* 1427 */               form.getElement("priceCode").setStartingValue("-1");
/*      */             }
/* 1429 */             if (!form.getStringValue("priceCode").equalsIgnoreCase(form.getElement("priceCode").getStartingValue())) {
/* 1430 */               isPfmChange = true;
/*      */             }
/*      */           } 
/*      */           
/* 1434 */           if (form.getStringValue("priceCodeDPC") != null) {
/*      */ 
/*      */             
/* 1437 */             if (form.getElement("priceCodeDPC").getStartingValue().equals("")) {
/* 1438 */               form.getElement("priceCodeDPC").setStartingValue("-1");
/*      */             }
/* 1440 */             if (!form.getStringValue("priceCodeDPC").equalsIgnoreCase(form.getElement("priceCodeDPC").getStartingValue())) {
/* 1441 */               isPfmChange = true;
/*      */             }
/*      */           } 
/*      */           
/* 1445 */           if (((FormCheckBox)form.getElement("parentalIndicator")).isChecked() != ((FormCheckBox)form.getElement("parentalIndicator")).getStartingChecked()) {
/* 1446 */             isPfmChange = true;
/*      */           }
/*      */           
/* 1449 */           if (!form.getStringValue("title").equalsIgnoreCase(form.getElement("title").getStartingValue())) {
/*      */             
/* 1451 */             isPfmChange = true;
/* 1452 */             isBomChange = true;
/*      */           } 
/*      */ 
/*      */           
/* 1456 */           if (!form.getStringValue("artistFirstName").equalsIgnoreCase(form.getElement("artistFirstName").getStartingValue())) {
/*      */             
/* 1458 */             isPfmChange = true;
/* 1459 */             isBomChange = true;
/*      */           } 
/*      */           
/* 1462 */           if (!form.getStringValue("artistLastName").equalsIgnoreCase(form.getElement("artistLastName").getStartingValue())) {
/*      */             
/* 1464 */             isPfmChange = true;
/* 1465 */             isBomChange = true;
/*      */           } 
/*      */ 
/*      */           
/* 1469 */           if (!form.getStringValue("opercompany").equalsIgnoreCase(form.getElement("opercompany").getStartingValue())) {
/* 1470 */             isPfmChange = true;
/*      */           }
/*      */           
/* 1473 */           if (!form.getStringValue("superlabel").equalsIgnoreCase(form.getElement("superlabel").getStartingValue())) {
/* 1474 */             isPfmChange = true;
/*      */           }
/*      */           
/* 1477 */           if (!form.getStringValue("sublabel").equalsIgnoreCase(form.getElement("sublabel").getStartingValue())) {
/* 1478 */             isPfmChange = true;
/*      */           }
/*      */           
/* 1481 */           if (!form.getStringValue("configcode").equalsIgnoreCase(form.getElement("configcode").getStartingValue())) {
/* 1482 */             isPfmChange = true;
/*      */           }
/*      */ 
/*      */           
/* 1486 */           if (!form.getStringValue("soundscan").equalsIgnoreCase(form.getElement("soundscan").getStartingValue())) {
/* 1487 */             isPfmChange = true;
/*      */           }
/*      */           
/* 1490 */           if (!savedSelection.getIsDigital() && ((FormCheckBox)form.getElement("parentalIndicator")).isChecked() != ((FormCheckBox)form.getElement("parentalIndicator")).getStartingChecked()) {
/* 1491 */             isPfmChange = true;
/*      */           }
/*      */           
/* 1494 */           if (!form.getStringValue("numOfUnits").equalsIgnoreCase(form.getElement("numOfUnits").getStartingValue())) {
/*      */             
/* 1496 */             isPfmChange = true;
/* 1497 */             isBomChange = true;
/*      */           } 
/*      */ 
/*      */           
/* 1501 */           if (!form.getStringValue("label").equalsIgnoreCase(form.getElement("label").getStartingValue())) {
/* 1502 */             isBomChange = true;
/*      */           }
/*      */           
/* 1505 */           if (!form.getStringValue("company").equalsIgnoreCase(form.getElement("company").getStartingValue())) {
/* 1506 */             isBomChange = true;
/*      */           }
/*      */           
/* 1509 */           if (!form.getStringValue("releaseType").equalsIgnoreCase(form.getElement("releaseType").getStartingValue())) {
/* 1510 */             isBomChange = true;
/*      */           }
/*      */ 
/*      */ 
/*      */           
/* 1515 */           if (form.getStringValue("genre").equals("-1")) {
/* 1516 */             form.getElement("genre").setValue("");
/*      */           }
/*      */           
/* 1519 */           if (!form.getStringValue("genre").equalsIgnoreCase(form.getElement("genre").getStartingValue())) {
/* 1520 */             isPfmChange = true;
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1527 */           Pfm pfm = null;
/* 1528 */           Bom bom = null;
/* 1529 */           boolean isBOMFinal = false;
/* 1530 */           boolean isPFMFinal = false;
/* 1531 */           if (savedSelection != null) {
/*      */             
/* 1533 */             pfm = SelectionManager.getInstance().getPfm(savedSelection.getSelectionID());
/* 1534 */             bom = SelectionManager.getInstance().getBom(savedSelection);
/*      */             
/* 1536 */             isBOMFinal = (bom != null && bom.getPrintOption() != null && bom.getPrintOption().equalsIgnoreCase("Final"));
/* 1537 */             isPFMFinal = (pfm != null && pfm.getPrintOption() != null && pfm.getPrintOption().equalsIgnoreCase("Final"));
/*      */             
/* 1539 */             int igaId = MilestoneHelper.getStructureId("IGA", 1);
/* 1540 */             int interscopeId = MilestoneHelper.getStructureId("Interscope", 5);
/* 1541 */             int geffenId = MilestoneHelper.getStructureId("Geffen", 5);
/* 1542 */             int amId = MilestoneHelper.getStructureId("A&M", 5);
/*      */ 
/*      */             
/* 1545 */             boolean isBomChangeSave = isBomChange;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1578 */             context.putSessionValue("pfmBomSelection", savedSelection);
/*      */           } 
/*      */ 
/*      */           
/* 1582 */           if (!isNewSelection && (isPfmChange || isBomChange) && (isPFMFinal || isBOMFinal)) {
/*      */             
/* 1584 */             if (isPFMFinal && isPfmChange) {
/* 1585 */               context.putDelivery("pfmSend", "true");
/*      */             }
/* 1587 */             if (isBOMFinal && isBomChange) {
/* 1588 */               context.putDelivery("bomSend", "true");
/*      */             }
/* 1590 */             if (isNewSelection || (
/* 1591 */               isStreetDateDifferent && isScheduleApplied) || 
/* 1592 */               isPNR)
/*      */             {
/* 1594 */               context.putSessionValue("sendToSchedule", "true");
/* 1595 */               if (isStreetDateDifferent && isScheduleApplied)
/*      */               {
/* 1597 */                 context.putSessionValue("recalc-date", "true");
/* 1598 */                 context.putSessionValue("Selection", savedSelection);
/*      */ 
/*      */               
/*      */               }
/*      */ 
/*      */             
/*      */             }
/*      */ 
/*      */           
/*      */           }
/* 1608 */           else if (isNewSelection || (isStreetDateDifferent && isScheduleApplied) || isPNR) {
/*      */             
/* 1610 */             if (isStreetDateDifferent && isScheduleApplied) {
/*      */               
/* 1612 */               context.putDelivery("recalc-date", "true");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1629 */               context.putSessionValue("Selection", savedSelection);
/*      */             } 
/*      */             
/* 1632 */             dispatcher.redispatch(context, "schedule-editor");
/* 1633 */             return true;
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/* 1638 */           if (selection == null) {
/*      */             
/* 1640 */             context.putDelivery("BlankAlertMessage", " Your changes have been saved successfully.  <br>The changes made to the selection cause the selection to no longer be part of the notepad.  <br>To view this selection, modify notepad search criteria according to the changes made.  <br>Otherwise, choose another selection from the notepad on the left.");
/* 1641 */             return goToBlank(context, form, user);
/*      */           } 
/*      */ 
/*      */           
/* 1645 */           if (selection == savedSelection)
/*      */           {
/* 1647 */             form = buildForm(context, selection, command);
/*      */           }
/*      */           else
/*      */           {
/* 1651 */             edit(dispatcher, context, command);
/* 1652 */             return true;
/*      */           }
/*      */         
/*      */         }
/*      */         else {
/*      */           
/* 1658 */           context.putDelivery("FormValidation", formValidation);
/*      */         } 
/*      */       } 
/* 1661 */       form.addElement(new FormHidden("OrderBy", "", true));
/* 1662 */       context.putDelivery("Form", form);
/* 1663 */       if (isNewSelection) {
/*      */         
/* 1665 */         if (selection.getIsDigital()) {
/* 1666 */           return context.includeJSP("digital-selection-editor.jsp");
/*      */         }
/* 1668 */         return context.includeJSP("selection-editor.jsp");
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1673 */       return edit(dispatcher, context, command);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1678 */     context.putDelivery("AlertMessage", "The record is unavailable for editing at this time.  Please refresh your screen and edit it at a later time.");
/*      */ 
/*      */     
/* 1681 */     context.putDelivery("Form", form);
/* 1682 */     if (selection.getIsDigital()) {
/* 1683 */       return context.includeJSP("digital-selection-editor.jsp");
/*      */     }
/* 1685 */     return context.includeJSP("selection-editor.jsp");
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
/*      */   public static Vector filterSelectionActiveCompanies(int userId, Vector companies) {
/* 1705 */     Vector activeResult = new Vector();
/* 1706 */     boolean resultCompany = true;
/*      */ 
/*      */     
/* 1709 */     HashMap corpHashMap = MilestoneHelper.buildActiveCorporateStructureHashMap();
/*      */ 
/*      */ 
/*      */     
/* 1713 */     if (companies != null && companies.size() > 0)
/*      */     {
/* 1715 */       for (int i = 0; i < companies.size(); i++) {
/*      */ 
/*      */         
/* 1718 */         Company company = (Company)companies.get(i);
/* 1719 */         String name = company.getName();
/* 1720 */         int structureId = company.getStructureID();
/*      */ 
/*      */ 
/*      */         
/* 1724 */         resultCompany = !corpHashMap.containsKey(new Integer(structureId));
/*      */         
/* 1726 */         if (!name.equalsIgnoreCase("UML") && 
/* 1727 */           !name.equalsIgnoreCase("Enterprise") && resultCompany)
/*      */         {
/* 1729 */           activeResult.add(company);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/* 1734 */     corpHashMap = null;
/* 1735 */     return activeResult;
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
/*      */   private boolean editDelete(Dispatcher dispatcher, Context context, String command) {
/* 1750 */     context.removeSessionValue("searchResults");
/* 1751 */     context.removeSessionValue("selectionScreenType");
/*      */ 
/*      */ 
/*      */     
/* 1755 */     User user = (User)context.getSessionValue("user");
/* 1756 */     Selection selection = (Selection)context.getSessionValue("Selection");
/* 1757 */     Notepad notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 0);
/*      */     
/* 1759 */     if (selection.getSelectionID() > 0) {
/*      */       
/* 1761 */       boolean isDeletable = false;
/*      */       
/* 1763 */       isDeletable = SelectionManager.getInstance().deleteSelection(selection, user);
/*      */       
/* 1765 */       if (isDeletable) {
/*      */ 
/*      */         
/* 1768 */         context.removeSessionValue("Selection");
/* 1769 */         Vector contents = new Vector();
/*      */ 
/*      */         
/* 1772 */         notepad.setAllContents(null);
/* 1773 */         notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 0);
/* 1774 */         notepad.setSelected(null);
/*      */         
/* 1776 */         selection = MilestoneHelper.getScreenSelection(context);
/*      */ 
/*      */         
/* 1779 */         Cache.flushUsedLabels();
/*      */         
/* 1781 */         if (selection != null)
/*      */         {
/* 1783 */           context.putSessionValue("Selection", selection);
/*      */         }
/*      */         else
/*      */         {
/* 1787 */           Form form = null;
/*      */           
/* 1789 */           form = new Form(this.application, "selectionForm", this.application.getInfrastructure().getServletURL(), "POST");
/* 1790 */           form.addElement(new FormHidden("cmd", "selection-editor", true));
/* 1791 */           form.addElement(new FormHidden("OrderBy", "", true));
/*      */ 
/*      */           
/* 1794 */           if (context.getSessionValue("NOTEPAD_SELECTION_VISIBLE") != null) {
/* 1795 */             context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_SELECTION_VISIBLE"));
/*      */           }
/* 1797 */           Vector companies = MilestoneHelper.getUserCompanies(context);
/*      */           
/* 1799 */           addSelectionSearchElements(context, null, form, companies, true);
/* 1800 */           context.putDelivery("Form", form);
/*      */           
/* 1802 */           return context.includeJSP("blank-selection-editor.jsp");
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/* 1807 */         String alert = "Cannot delete this record.\\n There are other records that are dependent on it.";
/* 1808 */         context.putDelivery("alert-box", alert);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1813 */     if (selection.getIsDigital()) {
/*      */       
/* 1815 */       Form form = buildDigitalForm(context, selection, command);
/* 1816 */       context.putDelivery("Form", form);
/* 1817 */       return context.includeJSP("digital-selection-editor.jsp");
/*      */     } 
/*      */ 
/*      */     
/* 1821 */     Form form = buildForm(context, selection, command);
/* 1822 */     context.putDelivery("Form", form);
/* 1823 */     return context.includeJSP("selection-editor.jsp");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean getReleasingFamilies(Dispatcher dispatcher, Context context, String command) {
/* 1834 */     Selection selection = MilestoneHelper.getScreenSelection(context);
/* 1835 */     User user = (User)context.getSession().getAttribute("user");
/* 1836 */     int userId = user.getUserId();
/*      */     
/* 1838 */     int environmentID = -1;
/*      */     
/*      */     try {
/* 1841 */       environmentID = Integer.parseInt(context.getRequestValue("eID"));
/*      */     }
/* 1843 */     catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */     
/* 1847 */     Environment env = MilestoneHelper.getEnvironmentById(environmentID);
/* 1848 */     int familyID = -1;
/*      */     
/* 1850 */     if (env != null && env.getParentFamily() != null) {
/* 1851 */       familyID = env.getParentFamily().getStructureID();
/*      */     }
/* 1853 */     Vector releaseFamilies = ReleasingFamily.getReleasingFamilies(userId, familyID, context);
/*      */     
/* 1855 */     context.putDelivery("releasing_family_list", releaseFamilies);
/* 1856 */     return context.includeJSP("selection-get-releasing-families.jsp");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean getSelectionSearchResults(Dispatcher dispatcher, Context context, String command) {
/* 1864 */     User user = (User)context.getSessionValue("user");
/* 1865 */     if (user != null) {
/* 1866 */       user.SS_searchInitiated = true;
/*      */     }
/*      */ 
/*      */     
/* 1870 */     if (user == null || user.getPreferences() == null || user.getPreferences().getSelectionPriorCriteria() != 1) {
/* 1871 */       context.putSessionValue("ResetSelectionSortOrder", "true");
/*      */     }
/*      */ 
/*      */     
/* 1875 */     context.putSessionValue("searchElementsInit", "true");
/*      */ 
/*      */ 
/*      */     
/* 1879 */     if (SelectionManager.getInstance().getSelectionSearchResults(this.application, context)) {
/*      */ 
/*      */ 
/*      */       
/* 1883 */       Notepad notepad = MilestoneHelper.getNotepadFromSession(0, context);
/*      */ 
/*      */       
/* 1886 */       notepad.setAllContents(null);
/* 1887 */       notepad.setSelected(null);
/*      */ 
/*      */       
/* 1890 */       notepad.setMaxRecords(225);
/*      */       
/* 1892 */       String searchCommand = context.getParameter("selectionSearchCommand");
/*      */ 
/*      */       
/* 1895 */       context.putDelivery("selectionSearchResults", "true");
/*      */ 
/*      */       
/* 1898 */       user = (User)context.getSessionValue("user");
/* 1899 */       if (user != null && user.getPreferences() != null) {
/* 1900 */         user.getPreferences().getSelectionPriorCriteria();
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1924 */       if (searchCommand.equals("bom-search"))
/* 1925 */         dispatcher.redispatch(context, "bom-editor"); 
/* 1926 */       if (searchCommand.equals("pfm-search"))
/* 1927 */         dispatcher.redispatch(context, "pfm-editor"); 
/* 1928 */       if (searchCommand.equals("schedule-selection-release-search"))
/* 1929 */         dispatcher.redispatch(context, "schedule-editor"); 
/* 1930 */       if (searchCommand.equals("selection-search"))
/* 1931 */         dispatcher.redispatch(context, "selection-editor"); 
/* 1932 */       if (searchCommand.equals("selection-manufacturing-search")) {
/* 1933 */         dispatcher.redispatch(context, "selection-manufacturing-editor");
/*      */       }
/*      */ 
/*      */       
/* 1937 */       return true;
/*      */     } 
/*      */ 
/*      */     
/* 1941 */     String searchCommand = context.getParameter("selectionSearchCommand");
/*      */ 
/*      */     
/* 1944 */     context.putDelivery("selectionSearchResults", "false");
/*      */ 
/*      */     
/* 1947 */     if (searchCommand.equals("bom-search"))
/* 1948 */       dispatcher.redispatch(context, "bom-editor"); 
/* 1949 */     if (searchCommand.equals("pfm-search"))
/* 1950 */       dispatcher.redispatch(context, "pfm-editor"); 
/* 1951 */     if (searchCommand.equals("schedule-selection-release-search"))
/* 1952 */       dispatcher.redispatch(context, "schedule-editor"); 
/* 1953 */     if (searchCommand.equals("selection-search"))
/* 1954 */       dispatcher.redispatch(context, "selection-editor"); 
/* 1955 */     if (searchCommand.equals("selection-manufacturing-search")) {
/* 1956 */       dispatcher.redispatch(context, "selection-manufacturing-editor");
/*      */     }
/*      */ 
/*      */     
/* 1960 */     return context.includeJSP("selection-search-results.jsp");
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
/*      */   private boolean editNew(Dispatcher dispatcher, Context context, String command) {
/* 1976 */     String selectionType = "";
/* 1977 */     selectionType = (String)context.getSessionValue("selectionScreenType");
/*      */ 
/*      */     
/* 1980 */     User user = (User)context.getSessionValue("user");
/* 1981 */     Notepad notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 0);
/*      */ 
/*      */     
/* 1984 */     notepad.setAllContents(null);
/* 1985 */     notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 0);
/*      */     
/* 1987 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1992 */     Selection newSelection = new Selection();
/*      */     
/* 1994 */     if (command.equalsIgnoreCase("selection-edit-new-digital") || command.equalsIgnoreCase("selection-edit-new-digital-archie-project")) {
/* 1995 */       newSelection.setIsDigital(true);
/*      */     }
/* 1997 */     context.putSessionValue("Selection", newSelection);
/*      */     
/* 1999 */     context.putSessionValue("impactSaveVisible", "true");
/*      */ 
/*      */     
/* 2002 */     Form form = null;
/* 2003 */     if (command.equalsIgnoreCase("selection-edit-new-digital") || command.equalsIgnoreCase("selection-edit-new-digital-archie-project")) {
/* 2004 */       form = buildNewDigitalForm(context, newSelection, command);
/*      */     } else {
/* 2006 */       form = buildNewForm(context, newSelection, command);
/*      */     } 
/* 2008 */     context.putDelivery("Form", form);
/*      */     
/* 2010 */     if (command.equalsIgnoreCase("selection-edit-new-digital") || command.equalsIgnoreCase("selection-edit-new-digital-archie-project")) {
/* 2011 */       return context.includeJSP("digital-selection-editor.jsp");
/*      */     }
/* 2013 */     return context.includeJSP("selection-editor.jsp");
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
/* 2029 */     context.removeSessionValue("searchResults");
/* 2030 */     context.removeSessionValue("selectionScreenType");
/*      */     
/* 2032 */     User user = (User)context.getSessionValue("user");
/*      */     
/* 2034 */     Notepad notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 0);
/*      */     
/* 2036 */     Selection targetSelection = MilestoneHelper.getScreenSelection(context);
/*      */     
/* 2038 */     String oldSelectionNumber = "";
/*      */     
/* 2040 */     if (targetSelection.getSelectionNo() != null) {
/* 2041 */       oldSelectionNumber = targetSelection.getSelectionNo();
/*      */     }
/* 2043 */     Selection copiedSelection = null;
/*      */ 
/*      */     
/*      */     try {
/* 2047 */       copiedSelection = (Selection)targetSelection.clone();
/*      */     }
/* 2049 */     catch (CloneNotSupportedException cloneNotSupportedException) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2057 */     copiedSelection.setSelectionID(-1);
/*      */     
/* 2059 */     Vector copiedImpactDates = copiedSelection.getImpactDates();
/* 2060 */     if (copiedImpactDates != null) {
/* 2061 */       for (int i = 0; i < copiedImpactDates.size(); i++) {
/*      */         
/* 2063 */         ImpactDate impact = (ImpactDate)copiedImpactDates.get(i);
/* 2064 */         impact.setSelectionID(copiedSelection.getSelectionID());
/* 2065 */         impact.setImpactDateID(-1);
/*      */       } 
/* 2067 */       copiedSelection.setImpactDates(copiedImpactDates);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2072 */     copiedSelection.setSellCode("");
/* 2073 */     copiedSelection.setSellCodeDPC("");
/* 2074 */     copiedSelection.setPriceCode(null);
/* 2075 */     copiedSelection.setNumberOfUnits(0);
/* 2076 */     copiedSelection.setSpecialPackaging(false);
/* 2077 */     copiedSelection.setUmlContact(null);
/* 2078 */     copiedSelection.setPlant(null);
/* 2079 */     copiedSelection.setDistribution(null);
/* 2080 */     copiedSelection.setManufacturingComments("");
/* 2081 */     copiedSelection.setPoQuantity(0);
/* 2082 */     copiedSelection.setCompletedQuantity(0);
/* 2083 */     copiedSelection.setMultSelections(null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2097 */     Form form = null;
/*      */ 
/*      */     
/* 2100 */     if (!copiedSelection.getIsDigital()) {
/*      */       
/* 2102 */       copiedSelection.setNoDigitalRelease(true);
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */ 
/*      */       
/* 2111 */       copiedSelection.setNoDigitalRelease(false);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2116 */     if (command.equalsIgnoreCase("selection-edit-copy-digital")) {
/*      */ 
/*      */       
/* 2119 */       copiedSelection.setReleaseType(null);
/* 2120 */       copiedSelection.setSelectionConfig(null);
/* 2121 */       copiedSelection.setSelectionSubConfig(null);
/* 2122 */       copiedSelection.setSoundScanGrp("");
/* 2123 */       copiedSelection.setProductCategory(null);
/* 2124 */       copiedSelection.setHoldReason("");
/* 2125 */       copiedSelection.setHoldSelection(false);
/* 2126 */       copiedSelection.setTitleID("");
/* 2127 */       copiedSelection.setReleaseType((ReleaseType)SelectionManager.getLookupObject("CO", Cache.getReleaseTypes()));
/* 2128 */       copiedSelection.setDigitalRlsDate(null);
/* 2129 */       copiedSelection.setInternationalDate(null);
/* 2130 */       copiedSelection.setInternationalFlag(false);
/* 2131 */       copiedSelection.setSelectionPackaging("");
/* 2132 */       copiedSelection.setSelectionTerritory("");
/*      */ 
/*      */ 
/*      */       
/* 2136 */       if (copiedSelection.getIsDigital()) {
/*      */         
/* 2138 */         copiedSelection.setSelectionNo("");
/* 2139 */         copiedSelection.setPrefixID(null);
/* 2140 */         copiedSelection.setUpc("");
/* 2141 */         copiedSelection.setImpactDates(null);
/* 2142 */         copiedSelection.setStreetDate(null);
/* 2143 */         copiedSelection.setNewBundleFlag(true);
/* 2144 */         copiedSelection.setImpactDates(null);
/* 2145 */         copiedSelection.setImpactDate(null);
/* 2146 */         copiedSelection.setSpecialInstructions("");
/*      */       }
/*      */       else {
/*      */         
/* 2150 */         copiedSelection.setConfigCode("");
/* 2151 */         copiedSelection.setNewBundleFlag(false);
/*      */       } 
/*      */       
/* 2154 */       form = buildDigitalForm(context, copiedSelection, command);
/*      */       
/* 2156 */       if (copiedSelection.getIsDigital()) {
/*      */         
/* 2158 */         FormRadioButtonGroup newBundle = (FormRadioButtonGroup)form.getElement("newBundle");
/* 2159 */         newBundle.setValue("");
/*      */       } 
/*      */       
/* 2162 */       FormCheckBox priority = (FormCheckBox)form.getElement("priority");
/* 2163 */       priority.setValue("");
/*      */       
/* 2165 */       copiedSelection.setIsDigital(true);
/*      */       
/* 2167 */       copiedSelection.setNoDigitalRelease(false);
/*      */     }
/*      */     else {
/*      */       
/* 2171 */       copiedSelection.setConfigCode("");
/* 2172 */       copiedSelection.setComments("");
/* 2173 */       copiedSelection.setSelectionNo("");
/* 2174 */       copiedSelection.setPrefixID(null);
/* 2175 */       copiedSelection.setUpc("");
/* 2176 */       form = buildForm(context, copiedSelection, command);
/*      */     } 
/*      */ 
/*      */     
/* 2180 */     context.putSessionValue("impactSaveVisible", "true");
/* 2181 */     context.putSessionValue("Selection", copiedSelection);
/* 2182 */     context.putDelivery("old-selection-no", oldSelectionNumber);
/* 2183 */     context.putDelivery("Form", form);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2189 */     Selection updatedSelection = SelectionManager.isProjectNumberValid(copiedSelection);
/*      */     
/* 2191 */     if (updatedSelection == null) {
/*      */ 
/*      */ 
/*      */       
/* 2195 */       if (targetSelection.getIsDigital()) {
/* 2196 */         form = buildDigitalForm(context, targetSelection, "selection-editor");
/*      */       } else {
/* 2198 */         form = buildForm(context, targetSelection, "selection-editor");
/*      */       } 
/* 2200 */       context.removeSessionValue("Selection");
/* 2201 */       context.removeDelivery("Form");
/*      */       
/* 2203 */       context.putSessionValue("Selection", targetSelection);
/* 2204 */       context.putDelivery("Form", form);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2209 */       context.putDelivery("ProjectNumberMessage", 
/* 2210 */           "***************************** COPY FAILED ****************************\\nThe Project Number for this Product is NOT contained within Archimedes.\\nIf the Project Number is incorrect, please set up a NEW product.\\nIf the Project Number is correct, please contact system administrator so it can be added to Archimedes.");
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2215 */       if (targetSelection.getIsDigital()) {
/*      */         
/* 2217 */         if (targetSelection != null) {
/* 2218 */           return context.includeJSP("digital-selection-editor.jsp");
/*      */         }
/* 2220 */         return context.includeJSP("blank-selection-editor.jsp");
/*      */       } 
/*      */ 
/*      */       
/* 2224 */       if (targetSelection != null) {
/* 2225 */         return context.includeJSP("selection-editor.jsp");
/*      */       }
/* 2227 */       return context.includeJSP("blank-selection-editor.jsp");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2233 */     String diffMessage = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 2243 */       if (updatedSelection.getLabel() != null && updatedSelection.getLabel().getStructureID() != copiedSelection.getLabel().getStructureID()) {
/* 2244 */         diffMessage = String.valueOf(diffMessage) + "<b>Archimedes Label:</b> " + updatedSelection.getLabel().getName() + "<BR>";
/*      */       }
/* 2246 */       if (!updatedSelection.getOperCompany().equalsIgnoreCase(copiedSelection.getOperCompany())) {
/* 2247 */         diffMessage = String.valueOf(diffMessage) + "<b>Archimedes Operating Company:</b> " + updatedSelection.getOperCompany() + "<BR>";
/*      */       }
/* 2249 */       if (!updatedSelection.getSuperLabel().equalsIgnoreCase(copiedSelection.getSuperLabel())) {
/* 2250 */         diffMessage = String.valueOf(diffMessage) + "<b>Archimedes Super Label:</b> " + updatedSelection.getSuperLabel() + "<BR>";
/*      */       }
/* 2252 */       if (!updatedSelection.getSubLabel().equalsIgnoreCase(copiedSelection.getSubLabel())) {
/* 2253 */         diffMessage = String.valueOf(diffMessage) + "<b>Archimedes Sub Label:</b> " + updatedSelection.getSubLabel() + "<BR>";
/*      */       }
/* 2255 */       if (!updatedSelection.getImprint().equalsIgnoreCase(copiedSelection.getImprint())) {
/* 2256 */         diffMessage = String.valueOf(diffMessage) + "<b>Archimedes Imprint:</b> " + updatedSelection.getImprint() + "<BR>";
/*      */       }
/* 2258 */     } catch (Exception e) {
/*      */       
/* 2260 */       System.out.println("project number validation..." + e.toString());
/*      */     } 
/*      */     
/* 2263 */     if (diffMessage.length() > 0) {
/*      */       
/* 2265 */       context.putDelivery("DiffMessage", diffMessage);
/* 2266 */       context.putDelivery("updatedSelection", updatedSelection);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2275 */     if (command.equalsIgnoreCase("selection-edit-copy-digital")) {
/*      */       
/* 2277 */       if (copiedSelection != null) {
/* 2278 */         return context.includeJSP("digital-selection-editor.jsp");
/*      */       }
/* 2280 */       return context.includeJSP("blank-selection-editor.jsp");
/*      */     } 
/*      */ 
/*      */     
/* 2284 */     if (copiedSelection != null) {
/* 2285 */       return context.includeJSP("selection-editor.jsp");
/*      */     }
/* 2287 */     return context.includeJSP("blank-selection-editor.jsp");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean manufacturingEdit(Dispatcher dispatcher, Context context, String command) {
/* 2298 */     int selectionID = -1;
/* 2299 */     User user = (User)context.getSessionValue("user");
/*      */     
/* 2301 */     Selection selection = new Selection();
/*      */ 
/*      */ 
/*      */     
/* 2305 */     Notepad notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 0);
/* 2306 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */     
/* 2308 */     selection = MilestoneHelper.getScreenSelection(context);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2313 */     boolean newFlag = SelectionManager.getInstance().getSelectionManufacturingSubDetail(selection);
/*      */ 
/*      */ 
/*      */     
/* 2317 */     int mfgAccessLevel = 0;
/*      */     
/* 2319 */     if (selection != null) {
/* 2320 */       mfgAccessLevel = getSelectionPermissions(selection, user);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2330 */     Form form = null;
/* 2331 */     if (selection != null) {
/*      */ 
/*      */       
/* 2334 */       form = buildManufacturingForm(context, selection, command, mfgAccessLevel, newFlag);
/* 2335 */       context.putDelivery("Form", form);
/* 2336 */       context.putSessionValue("Selection", selection);
/* 2337 */       int secureLevel = getSelectionMfgPermissions(selection, user);
/*      */ 
/*      */       
/* 2340 */       if (selection.getIsDigital()) {
/* 2341 */         return dispatcher.redispatch(context, "schedule-editor");
/*      */       }
/* 2343 */       return context.includeJSP("selection-manufacturing-editor.jsp");
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2348 */     form = new Form(this.application, "selectionForm", this.application.getInfrastructure().getServletURL(), "POST");
/* 2349 */     form.addElement(new FormHidden("cmd", "selection-manufacturing-editor", true));
/* 2350 */     form.addElement(new FormHidden("OrderBy", "", true));
/*      */     
/* 2352 */     if (context.getSessionValue("NOTEPAD_MFG_VISIBLE") != null) {
/* 2353 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_MFG_VISIBLE"));
/*      */     }
/*      */     
/* 2356 */     Vector companies = MilestoneHelper.getUserCompanies(context);
/*      */     
/* 2358 */     addSelectionSearchElements(context, null, form, companies, true);
/* 2359 */     context.putDelivery("Form", form);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2366 */     return context.includeJSP("blank-selection-manufacturing-editor.jsp");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean manufacturingEditSave(Dispatcher dispatcher, Context context, String command) {
/* 2377 */     Selection selection = (Selection)context.getSessionValue("Selection");
/*      */     
/* 2379 */     User user = (User)context.getSessionValue("user");
/*      */     
/* 2381 */     Form form = buildManufacturingForm(context, selection, command, getSelectionMfgPermissions(selection, user), false);
/*      */     
/* 2383 */     form.setValues(context);
/*      */     
/* 2385 */     String mfgComments = form.getStringValue("orderCommentHelper");
/* 2386 */     String comments = form.getStringValue("comments");
/* 2387 */     String umlContact = form.getStringValue("umlcontact");
/* 2388 */     String distribution = form.getStringValue("distribution");
/*      */     
/* 2390 */     selection.setDistribution(SelectionManager.getLookupObject(distribution, Cache.getDistributionCodes()));
/* 2391 */     selection.setManufacturingComments(mfgComments);
/* 2392 */     selection.setComments(comments);
/*      */ 
/*      */     
/* 2395 */     Vector plants = new Vector();
/* 2396 */     Vector newPlants = new Vector();
/*      */     
/* 2398 */     if (selection.getManufacturingPlants() != null) {
/* 2399 */       plants = selection.getManufacturingPlants();
/*      */     }
/* 2401 */     for (int plantCount = 0; plantCount < plants.size(); plantCount++) {
/*      */       
/* 2403 */       Plant p = (Plant)plants.get(plantCount);
/*      */       
/* 2405 */       String vendor = form.getStringValue("plant" + plantCount);
/*      */       
/* 2407 */       int poQty = 0;
/* 2408 */       String poQtyString = form.getStringValue("po_qty" + plantCount);
/*      */ 
/*      */       
/* 2411 */       if (poQtyString.indexOf(",") > -1) {
/*      */         
/* 2413 */         String newString = "";
/* 2414 */         for (int i = 0; i < poQtyString.length(); i++) {
/*      */           
/* 2416 */           if (poQtyString.charAt(i) != ',') {
/* 2417 */             newString = String.valueOf(newString) + poQtyString.charAt(i);
/*      */           }
/*      */         } 
/* 2420 */         poQtyString = newString;
/*      */       } 
/*      */ 
/*      */       
/*      */       try {
/* 2425 */         poQty = Integer.parseInt(poQtyString);
/*      */       }
/* 2427 */       catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2432 */       String completedQty = form.getStringValue("completed_qty" + plantCount);
/*      */ 
/*      */       
/* 2435 */       if (completedQty.indexOf(",") > -1) {
/*      */         
/* 2437 */         String newString = "";
/* 2438 */         for (int i = 0; i < completedQty.length(); i++) {
/*      */           
/* 2440 */           if (completedQty.charAt(i) != ',') {
/* 2441 */             newString = String.valueOf(newString) + completedQty.charAt(i);
/*      */           }
/*      */         } 
/* 2444 */         completedQty = newString;
/*      */       } 
/*      */       
/* 2447 */       int completedQtyInt = 0;
/*      */       
/*      */       try {
/* 2450 */         completedQtyInt = Integer.parseInt(completedQty);
/*      */       }
/* 2452 */       catch (NumberFormatException numberFormatException) {}
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2457 */       p.setSelectionID(selection.getSelectionNo());
/* 2458 */       p.setReleaseID(selection.getSelectionID());
/* 2459 */       p.setOrderQty(poQty);
/* 2460 */       p.setCompletedQty(completedQtyInt);
/* 2461 */       p.setPlant(SelectionManager.getLookupObject(vendor, Cache.getVendors()));
/*      */       
/* 2463 */       newPlants.add(p);
/*      */     } 
/*      */     
/* 2466 */     selection.setManufacturingPlants(newPlants);
/*      */     
/* 2468 */     boolean newFlag = false;
/* 2469 */     if (form.getStringValue("new").equals("true")) {
/* 2470 */       newFlag = true;
/*      */     }
/*      */ 
/*      */     
/* 2474 */     if (newFlag || (!newFlag && SelectionManager.getInstance().isManufacturingTimestampValid(selection))) {
/*      */       
/* 2476 */       if (umlContact != null && !umlContact.equals("")) {
/*      */         
/* 2478 */         int umlContactUserId = Integer.parseInt(umlContact);
/* 2479 */         selection.setUmlContact(UserManager.getInstance().getUser(umlContactUserId));
/*      */       } 
/*      */       
/* 2482 */       if (!form.isUnchanged()) {
/*      */         
/* 2484 */         FormValidation formValidation = form.validate();
/* 2485 */         if (formValidation.isGood()) {
/*      */           
/* 2487 */           Selection savedSelection = SelectionManager.getInstance().saveManufacturingSelection(selection, user, newFlag);
/*      */ 
/*      */ 
/*      */           
/* 2491 */           Notepad notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 0);
/* 2492 */           MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */ 
/*      */           
/* 2495 */           SelectionManager.getInstance().getSelectionManufacturingSubDetail(savedSelection);
/*      */           
/* 2497 */           String lastMfgUpdatedDateText = "";
/* 2498 */           if (savedSelection.getLastMfgUpdateDate() != null)
/* 2499 */             lastMfgUpdatedDateText = MilestoneHelper.getCustomFormatedDate(savedSelection.getLastMfgUpdateDate(), "M/d/yyyy hh:mm:ss a 'ET'"); 
/* 2500 */           context.putDelivery("lastUpdateDate", lastMfgUpdatedDateText);
/*      */           
/* 2502 */           String lastMfgUpdateUser = "";
/* 2503 */           if (savedSelection.getLastMfgUpdatingUser() != null) {
/* 2504 */             lastMfgUpdateUser = savedSelection.getLastMfgUpdatingUser().getName();
/*      */           }
/*      */ 
/*      */           
/* 2508 */           context.putDelivery("lastUpdateUser", lastMfgUpdateUser);
/*      */ 
/*      */           
/* 2511 */           notepad.setAllContents(null);
/* 2512 */           notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 0);
/* 2513 */           notepad.setSelected(savedSelection);
/*      */         }
/*      */         else {
/*      */           
/* 2517 */           context.putDelivery("FormValidation", formValidation);
/*      */         } 
/*      */       } 
/* 2520 */       form.addElement(new FormHidden("OrderBy", "", true));
/* 2521 */       context.putDelivery("Form", form);
/*      */     }
/*      */     else {
/*      */       
/* 2525 */       context.putDelivery("AlertMessage", "The record is unavailable for editing at this time.  Please refresh your screen and edit it at a later time.");
/*      */     } 
/*      */     
/* 2528 */     context.putDelivery("Form", form);
/*      */     
/* 2530 */     if (selection.getIsDigital()) {
/* 2531 */       return dispatcher.redispatch(context, "schedule-editor");
/*      */     }
/* 2533 */     return context.includeJSP("selection-manufacturing-editor.jsp");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean manufacturingPlantAdd(Dispatcher dispatcher, Context context, String command) {
/* 2544 */     Selection selection = (Selection)context.getSessionValue("Selection");
/*      */     
/* 2546 */     User user = (User)context.getSessionValue("user");
/*      */     
/* 2548 */     Vector plants = new Vector();
/*      */     
/* 2550 */     plants = selection.getManufacturingPlants();
/*      */     
/* 2552 */     Plant p = new Plant();
/*      */     
/* 2554 */     plants.add(p);
/*      */     
/* 2556 */     selection.setManufacturingPlants(plants);
/*      */     
/* 2558 */     context.putSessionValue("Selection", selection);
/*      */     
/* 2560 */     Form form = buildManufacturingForm(context, selection, command, getSelectionMfgPermissions(selection, user), false);
/* 2561 */     form.setValues(context);
/*      */     
/* 2563 */     context.putDelivery("Form", form);
/*      */     
/* 2565 */     if (selection.getIsDigital()) {
/* 2566 */       return dispatcher.redispatch(context, "schedule-editor");
/*      */     }
/* 2568 */     return context.includeJSP("selection-manufacturing-editor.jsp");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean manufacturingPlantDelete(Dispatcher dispatcher, Context context, String command) {
/* 2576 */     Selection selection = (Selection)context.getSessionValue("Selection");
/*      */     
/* 2578 */     User user = (User)context.getSessionValue("user");
/*      */     
/* 2580 */     int id = Integer.parseInt(context.getRequestValue("plantId"));
/*      */     
/* 2582 */     Vector plants = new Vector();
/*      */     
/* 2584 */     plants = selection.getManufacturingPlants();
/*      */ 
/*      */ 
/*      */     
/* 2588 */     plants.remove(id);
/*      */     
/* 2590 */     selection.setManufacturingPlants(plants);
/*      */     
/* 2592 */     context.putSessionValue("Selection", selection);
/*      */     
/* 2594 */     Form form = buildManufacturingForm(context, selection, command, getSelectionMfgPermissions(selection, user), false);
/* 2595 */     form.setValues(context);
/*      */     
/* 2597 */     context.putDelivery("Form", form);
/*      */     
/* 2599 */     if (selection.getIsDigital()) {
/* 2600 */       return dispatcher.redispatch(context, "schedule-editor");
/*      */     }
/* 2602 */     return context.includeJSP("selection-manufacturing-editor.jsp");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Form buildForm(Context context, Selection selection, String command) {
/* 2612 */     Form selectionForm = new Form(this.application, "selectionForm", 
/* 2613 */         this.application.getInfrastructure().getServletURL(), "POST");
/* 2614 */     User user = (User)context.getSession().getAttribute("user");
/* 2615 */     int userId = user.getUserId();
/*      */     
/* 2617 */     int secureLevel = getSelectionPermissions(selection, user);
/* 2618 */     setButtonVisibilities(selection, user, context, secureLevel, command);
/*      */     
/* 2620 */     boolean newFlag = (selection.getSelectionID() < 0);
/*      */     
/* 2622 */     if (newFlag) {
/* 2623 */       context.putDelivery("new-or-copy", "true");
/*      */     } else {
/* 2625 */       context.putDelivery("new-or-copy", "false");
/*      */     } 
/*      */ 
/*      */     
/* 2629 */     selectionForm.addElement(new FormHidden("cmd", command, true));
/* 2630 */     selectionForm.addElement(new FormHidden("OrderBy", "", true));
/* 2631 */     selectionForm.addElement(new FormHidden("hidTitleId", "", true));
/* 2632 */     selectionForm.addElement(new FormHidden("isFocus", "", true));
/* 2633 */     selectionForm.addElement(new FormHidden("statusHidVal", "", true));
/* 2634 */     selectionForm.addElement(new FormHidden("generateSelection", "", true));
/*      */     
/* 2636 */     Vector companies = null;
/* 2637 */     companies = MilestoneHelper.getUserCompanies(context);
/*      */     
/* 2639 */     if (selection != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2653 */       FormTextField artistFirstName = new FormTextField("artistFirstName", selection.getArtistFirstName(), false, 20, 50);
/* 2654 */       artistFirstName.setTabIndex(1);
/* 2655 */       artistFirstName.addFormEvent("onChange", "javaScript:removeSpaces(this);");
/* 2656 */       artistFirstName.setClassName("ctrlMedium");
/* 2657 */       selectionForm.addElement(artistFirstName);
/*      */ 
/*      */       
/* 2660 */       FormTextField artistLastName = new FormTextField("artistLastName", selection.getArtistLastName(), false, 20, 50);
/* 2661 */       artistLastName.setTabIndex(2);
/* 2662 */       artistLastName.addFormEvent("onChange", "javaScript:removeSpaces(this);");
/* 2663 */       artistLastName.setClassName("ctrlMedium");
/* 2664 */       selectionForm.addElement(artistLastName);
/*      */ 
/*      */       
/* 2667 */       FormTextField title = new FormTextField("title", selection.getTitle(), true, 73, 125);
/* 2668 */       title.setTabIndex(3);
/* 2669 */       title.addFormEvent("onChange", "javaScript:removeSpaces(this);");
/* 2670 */       title.setClassName("ctrlXLarge");
/* 2671 */       selectionForm.addElement(title);
/*      */ 
/*      */       
/* 2674 */       FormTextField sideATitle = new FormTextField("sideATitle", selection.getASide(), false, 20, 125);
/* 2675 */       sideATitle.setTabIndex(4);
/* 2676 */       sideATitle.setClassName("ctrlMedium");
/* 2677 */       selectionForm.addElement(sideATitle);
/*      */ 
/*      */       
/* 2680 */       FormTextField sideBTitle = new FormTextField("sideBTitle", selection.getBSide(), false, 20, 125);
/* 2681 */       sideBTitle.setTabIndex(5);
/* 2682 */       sideBTitle.setClassName("ctrlMedium");
/* 2683 */       selectionForm.addElement(sideBTitle);
/*      */ 
/*      */ 
/*      */       
/* 2687 */       String GDRSProductStatusStr = "";
/* 2688 */       DcGDRSResults dcGDRSResults = GDRSProductStatusGet(selection, selection.getCompany().getParentEnvironment().getStructureID());
/* 2689 */       GDRSProductStatusStr = dcGDRSResults.getStatus();
/* 2690 */       FormHidden GDRSProductStatus = new FormHidden("GDRSProductStatus", GDRSProductStatusStr, false);
/* 2691 */       selectionForm.addElement(GDRSProductStatus);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2697 */       String streetDateText = "";
/* 2698 */       if (selection.getStreetDate() != null)
/* 2699 */         streetDateText = MilestoneHelper.getFormatedDate(selection.getStreetDate()); 
/* 2700 */       FormTextField streetDate = new FormTextField("streetDate", streetDateText, false, 10);
/* 2701 */       streetDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
/* 2702 */       streetDate.addFormEvent("onBlur", "JavaScript:validateDate(document.forms[0].streetDate.value,this);populateNoDigitalRelease()");
/*      */       
/* 2704 */       streetDate.addFormEvent("oldValue", streetDateText);
/* 2705 */       streetDate.setTabIndex(6);
/* 2706 */       streetDate.setClassName("ctrlShort");
/* 2707 */       selectionForm.addElement(streetDate);
/*      */       
/* 2709 */       FormTextField dayType = new FormTextField("dayType", MilestoneHelper.getDayType(selection.getCalendarGroup(), selection.getStreetDate()), false, 5);
/* 2710 */       selectionForm.addElement(dayType);
/*      */ 
/*      */       
/* 2713 */       String digitalRlsDateText = "";
/* 2714 */       if (selection.getDigitalRlsDate() != null)
/* 2715 */         digitalRlsDateText = MilestoneHelper.getFormatedDate(selection.getDigitalRlsDate()); 
/* 2716 */       FormTextField drDate = new FormTextField("digitalDateDisplay", digitalRlsDateText, false, 10);
/* 2717 */       selectionForm.addElement(drDate);
/* 2718 */       FormHidden digitalDate = new FormHidden("digitalDate", digitalRlsDateText, false);
/* 2719 */       selectionForm.addElement(digitalDate);
/*      */ 
/*      */ 
/*      */       
/* 2723 */       boolean noDigitalReleaseValue = dcGDRSResults.getForceNoDigitalRelease().booleanValue() ? true : selection.getNoDigitalRelease();
/*      */ 
/*      */       
/* 2726 */       FormHidden ForceNoDigitalRelease = new FormHidden("ForceNoDigitalRelease", dcGDRSResults.getForceNoDigitalRelease().toString(), false);
/* 2727 */       selectionForm.addElement(ForceNoDigitalRelease);
/*      */       
/* 2729 */       FormCheckBox noDigitalRelease = new FormCheckBox("noDigitalRelease", "", false, noDigitalReleaseValue);
/* 2730 */       noDigitalRelease.addFormEvent("onChange", "JavaScript:noDigitalReleaseChanged();");
/*      */       
/* 2732 */       noDigitalRelease.addFormEvent("oldValue", Boolean.toString(selection.getNoDigitalRelease()));
/* 2733 */       noDigitalRelease.setTabIndex(7);
/* 2734 */       selectionForm.addElement(noDigitalRelease);
/*      */ 
/*      */       
/* 2737 */       String intDateText = "";
/* 2738 */       if (selection.getInternationalDate() != null)
/* 2739 */         intDateText = MilestoneHelper.getFormatedDate(selection.getInternationalDate()); 
/* 2740 */       FormDateField intDate = new FormDateField("internationalDate", intDateText, false, 10);
/* 2741 */       intDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
/* 2742 */       intDate.addFormEvent("onBlur", "JavaScript:validateDate(document.forms[0].internationalDate.value,this)");
/* 2743 */       intDate.setTabIndex(8);
/* 2744 */       intDate.setClassName("ctrlShort");
/* 2745 */       selectionForm.addElement(intDate);
/*      */ 
/*      */       
/* 2748 */       context.putDelivery("releaseWeek", MilestoneHelper.getReleaseWeekString(selection));
/*      */ 
/*      */       
/* 2751 */       FormDropDownMenu status = MilestoneHelper.getLookupDropDown("status", Cache.getSelectionStatusList(), SelectionManager.getLookupObjectValue(selection.getSelectionStatus()), true, false);
/*      */       
/* 2753 */       status.addFormEvent("oldValue", SelectionManager.getLookupObjectValue(selection.getSelectionStatus()));
/* 2754 */       status.setTabIndex(9);
/* 2755 */       status.setClassName("ctrlSmall");
/* 2756 */       selectionForm.addElement(status);
/*      */ 
/*      */       
/* 2759 */       boolean boolHoldReason = true;
/* 2760 */       if (selection.getHoldReason().equalsIgnoreCase("")) {
/* 2761 */         boolHoldReason = false;
/*      */       }
/* 2763 */       FormCheckBox holdIndicator = new FormCheckBox("holdIndicator", "", false, boolHoldReason);
/* 2764 */       holdIndicator.setTabIndex(10);
/* 2765 */       selectionForm.addElement(holdIndicator);
/*      */ 
/*      */       
/* 2768 */       FormTextArea holdReason = new FormTextArea("holdReason", selection.getHoldReason(), false, 2, 44, "virtual");
/* 2769 */       holdReason.addFormEvent("onBlur", "Javascript:checkField(this)");
/* 2770 */       selectionForm.addElement(holdReason);
/*      */ 
/*      */       
/* 2773 */       FormCheckBox pdIndicator = new FormCheckBox("pdIndicator", "", false, selection.getPressAndDistribution());
/* 2774 */       pdIndicator.setTabIndex(12);
/* 2775 */       selectionForm.addElement(pdIndicator);
/*      */ 
/*      */       
/* 2778 */       FormCheckBox intlFlag = new FormCheckBox("intlFlag", "", false, selection.getInternationalFlag());
/* 2779 */       intlFlag.setTabIndex(11);
/* 2780 */       selectionForm.addElement(intlFlag);
/*      */ 
/*      */       
/* 2783 */       String impactDateText = "";
/* 2784 */       if (selection.getImpactDate() != null)
/* 2785 */         impactDateText = MilestoneHelper.getFormatedDate(selection.getImpactDate()); 
/* 2786 */       FormDateField impactDate = new FormDateField("impactdate", impactDateText, false, 13);
/* 2787 */       impactDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
/* 2788 */       impactDate.addFormEvent("onBlur", "JavaScript:validateDate(document.forms[0].impactdate.value,this)");
/* 2789 */       impactDate.setTabIndex(13);
/* 2790 */       impactDate.setClassName("ctrlShort");
/* 2791 */       selectionForm.addElement(impactDate);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2796 */       Vector releaseFamilies = ReleasingFamily.getReleasingFamilies(userId, selection.getFamily().getStructureID(), context);
/* 2797 */       FormDropDownMenu releasingFamily = MilestoneHelper.getReleasingFamilyDropDown("releasingFamily", Integer.toString(selection.getReleaseFamilyId()), releaseFamilies, true, selection);
/*      */       
/* 2799 */       releasingFamily.addFormEvent("onChange", "ReleasingFamilyChange(this)");
/* 2800 */       selectionForm.addElement(releasingFamily);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2805 */       String evironmentId = "";
/* 2806 */       String environmentName = "";
/* 2807 */       Vector evironmentList = filterSelectionEnvironments(companies);
/* 2808 */       if (selection.getCompany().getParentEnvironment() != null) {
/*      */         
/* 2810 */         evironmentId = Integer.toString(selection.getCompany().getParentEnvironment().getStructureID());
/* 2811 */         environmentName = selection.getCompany().getParentEnvironment().getName();
/*      */       } else {
/*      */         
/* 2814 */         evironmentId = "";
/* 2815 */       }  FormHidden evironment = new FormHidden("environment", evironmentId, false);
/* 2816 */       FormHidden evironmentLabel = new FormHidden("environment", evironmentId, false);
/* 2817 */       evironment.setTabIndex(14);
/* 2818 */       evironment.setDisplayName(environmentName);
/*      */ 
/*      */ 
/*      */       
/* 2822 */       selectionForm.addElement(evironment);
/*      */ 
/*      */       
/* 2825 */       String companyId = "";
/* 2826 */       String companyName = "";
/*      */ 
/*      */       
/* 2829 */       if (selection.getCompany() != null) {
/* 2830 */         companyId = Integer.toString(selection.getCompany().getStructureID());
/* 2831 */         companyName = selection.getCompany().getName();
/*      */       } 
/*      */       
/* 2834 */       FormHidden company = new FormHidden("company", companyId, false);
/*      */       
/* 2836 */       company.setTabIndex(15);
/* 2837 */       company.setDisplayName(companyName);
/*      */ 
/*      */ 
/*      */       
/* 2841 */       selectionForm.addElement(company);
/*      */ 
/*      */       
/* 2844 */       String divisionId = "";
/* 2845 */       String divisionName = "";
/* 2846 */       if (selection.getDivision() != null) {
/* 2847 */         divisionId = Integer.toString(selection.getDivision().getStructureID());
/* 2848 */         divisionName = selection.getDivision().getName();
/*      */       } else {
/*      */         
/* 2851 */         divisionId = "";
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2861 */       FormHidden division = new FormHidden("division", divisionId, false);
/*      */       
/* 2863 */       division.setTabIndex(16);
/* 2864 */       division.setDisplayName(divisionName);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2869 */       selectionForm.addElement(division);
/*      */ 
/*      */       
/* 2872 */       String labelId = "";
/* 2873 */       String labelName = "";
/* 2874 */       if (selection.getLabel() != null) {
/* 2875 */         labelId = Integer.toString(selection.getLabel().getStructureID());
/* 2876 */         labelName = selection.getLabel().getName();
/*      */       } else {
/* 2878 */         labelId = "";
/*      */       } 
/* 2880 */       FormHidden label = new FormHidden("label", labelId, false);
/* 2881 */       label.setTabIndex(17);
/* 2882 */       label.setDisplayName(labelName);
/* 2883 */       selectionForm.addElement(label);
/*      */ 
/*      */ 
/*      */       
/* 2887 */       if (selection.getOperCompany().equals("***")) {
/* 2888 */         FormHidden opercompany = new FormHidden("opercompany", "***", false);
/* 2889 */         opercompany.setDisplayName("***");
/* 2890 */         opercompany.setTabIndex(18);
/* 2891 */         selectionForm.addElement(opercompany);
/*      */       } else {
/* 2893 */         LookupObject oc = MilestoneHelper.getLookupObject(selection
/* 2894 */             .getOperCompany(), Cache.getOperatingCompanies());
/* 2895 */         String ocAbbr = "";
/* 2896 */         String ocName = "";
/*      */ 
/*      */ 
/*      */         
/* 2900 */         if (oc == null) {
/* 2901 */           ocAbbr = selection.getOperCompany();
/*      */         } else {
/* 2903 */           if (oc != null && oc.getAbbreviation() != null)
/* 2904 */             ocAbbr = oc.getAbbreviation(); 
/* 2905 */           if (oc != null && oc.getName() != null) {
/* 2906 */             ocName = ":" + oc.getName();
/*      */           }
/*      */         } 
/* 2909 */         FormHidden opercompany = new FormHidden("opercompany", ocAbbr, false);
/* 2910 */         opercompany.setDisplayName(String.valueOf(ocAbbr) + ocName);
/*      */         
/* 2912 */         if (ocAbbr.equals("ZZ"))
/* 2913 */           opercompany.setDisplayName(ocAbbr); 
/* 2914 */         opercompany.setTabIndex(18);
/* 2915 */         selectionForm.addElement(opercompany);
/*      */       } 
/*      */ 
/*      */       
/* 2919 */       FormHidden superlabel = new FormHidden("superlabel", selection.getSuperLabel(), false);
/* 2920 */       superlabel.setTabIndex(19);
/* 2921 */       superlabel.setClassName("ctrlShort");
/* 2922 */       superlabel.addFormEvent("onBlur", "javascript:this.value=this.value.toUpperCase();");
/* 2923 */       selectionForm.addElement(superlabel);
/*      */ 
/*      */       
/* 2926 */       FormHidden sublabel = new FormHidden("sublabel", selection.getSubLabel(), false);
/* 2927 */       sublabel.setTabIndex(20);
/* 2928 */       sublabel.setClassName("ctrlShort");
/* 2929 */       sublabel.addFormEvent("onBlur", "javascript:this.value=this.value.toUpperCase();");
/* 2930 */       selectionForm.addElement(sublabel);
/*      */ 
/*      */       
/* 2933 */       FormTextField imprint = new FormTextField("imprint", selection.getImprint(), false, 50);
/* 2934 */       imprint.setTabIndex(21);
/* 2935 */       imprint.setClassName("ctrlMedium");
/* 2936 */       imprint.addFormEvent("onChange", "javaScript:removeSpaces(this);");
/* 2937 */       selectionForm.addElement(imprint);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2942 */       FormDropDownMenu configcode = MilestoneHelper.getPfmLookupDropDown("configcode", MilestoneHelper.getConfigCodes(0), selection.getConfigCode(), false, true);
/* 2943 */       configcode.setTabIndex(21);
/*      */       
/* 2945 */       configcode.addFormEvent("onKeyPress", "scanKey(window.event.keyCode, 'document.all.configcode.options', getMaxLength(document.all.configcode.options))");
/*      */       
/* 2947 */       if (!newFlag) {
/* 2948 */         configcode.addFormEvent("onChange", "setNoDigitalRelease(this);");
/*      */       }
/*      */       
/* 2951 */       selectionForm.addElement(configcode);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2958 */       FormHidden projectId = new FormHidden("projectId", String.valueOf(selection.getProjectID()), false);
/* 2959 */       projectId.setTabIndex(22);
/* 2960 */       projectId.setDisplayName(String.valueOf(selection.getProjectID()));
/* 2961 */       projectId.setClassName("ctrlMedium");
/* 2962 */       selectionForm.addElement(projectId);
/*      */ 
/*      */ 
/*      */       
/* 2966 */       FormTextField gridNumber = new FormTextField("gridNumber", selection.getGridNumber(), false, 50);
/* 2967 */       gridNumber.setTabIndex(25);
/*      */       
/* 2969 */       gridNumber.setEnabled(true);
/* 2970 */       selectionForm.addElement(gridNumber);
/*      */ 
/*      */ 
/*      */       
/* 2974 */       FormTextField upc = new FormTextField("UPC", selection.getUpc(), false, 17, 20);
/* 2975 */       upc.setTabIndex(23);
/* 2976 */       upc.setClassName("ctrlMedium");
/*      */ 
/*      */       
/* 2979 */       upc.addFormEvent("onKeyPress", "return checkNumbersOnly(window.event.keyCode);");
/*      */ 
/*      */ 
/*      */       
/* 2983 */       selectionForm.addElement(upc);
/*      */ 
/*      */       
/* 2986 */       FormTextField soundscan = new FormTextField("soundscan", selection.getSoundScanGrp(), false, 17, 20);
/* 2987 */       soundscan.setTabIndex(24);
/* 2988 */       soundscan.setClassName("ctrlMedium");
/*      */ 
/*      */       
/* 2991 */       soundscan.addFormEvent("onKeyPress", "return checkNumbersOnly(window.event.keyCode);");
/*      */ 
/*      */ 
/*      */       
/* 2995 */       selectionForm.addElement(soundscan);
/*      */ 
/*      */       
/* 2998 */       FormDropDownMenu prefix = MilestoneHelper.getPrefixCodeDropDown(userId, "prefix", Cache.getPrefixCodes(), SelectionManager.getLookupObjectValue(selection.getPrefixID()), true, context);
/* 2999 */       prefix.setTabIndex(25);
/* 3000 */       prefix.setClassName("ctrlShort");
/* 3001 */       selectionForm.addElement(prefix);
/*      */ 
/*      */       
/* 3004 */       FormTextField selectionNo = new FormTextField("selectionNo", String.valueOf(selection.getSelectionNo()), false, 20, 20);
/* 3005 */       selectionNo.setTabIndex(26);
/* 3006 */       selectionNo.addFormEvent("onChange", "javaScript:removeSpaces(this);");
/* 3007 */       selectionNo.setClassName("ctrlMedium");
/* 3008 */       selectionForm.addElement(selectionNo);
/*      */ 
/*      */       
/* 3011 */       FormTextField titleId = new FormTextField("titleId", String.valueOf(selection.getTitleID()), false, 13, 24);
/* 3012 */       titleId.setClassName("ctrlMedium");
/*      */       
/* 3014 */       titleId.setTabIndex(27);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3020 */       selectionForm.addElement(titleId);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3027 */       FormDropDownMenu productLine = MilestoneHelper.getLookupDropDown("productLine", Cache.getProductCategories(0), SelectionManager.getLookupObjectValue(selection.getProductCategory()), true, true);
/* 3028 */       productLine.setTabIndex(28);
/* 3029 */       productLine.setClassName("ctrlMedium");
/* 3030 */       selectionForm.addElement(productLine);
/*      */ 
/*      */       
/* 3033 */       FormDropDownMenu releaseType = MilestoneHelper.getLookupDropDown("releaseType", Cache.getReleaseTypes(), SelectionManager.getLookupObjectValue(selection.getReleaseType()), true, newFlag);
/* 3034 */       releaseType.setTabIndex(29);
/* 3035 */       releaseType.setClassName("ctrlMedium");
/* 3036 */       releaseType.addFormEvent("onChange", "releaseTypeChanged()");
/* 3037 */       selectionForm.addElement(releaseType);
/*      */ 
/*      */       
/* 3040 */       String configValue = "";
/* 3041 */       if (selection.getSelectionConfig() != null) configValue = selection.getSelectionConfig().getSelectionConfigurationAbbreviation(); 
/* 3042 */       FormDropDownMenu configuration = MilestoneHelper.getSelectionConfigurationDropDown("configuration", configValue, true, 0);
/* 3043 */       configuration.setTabIndex(30);
/*      */       
/* 3045 */       configuration.addFormEvent("onChange", "buildSubConfigs(this.selectedIndex)");
/* 3046 */       selectionForm.addElement(configuration);
/*      */ 
/*      */ 
/*      */       
/* 3050 */       String subConfigValue = "";
/* 3051 */       if (selection.getSelectionSubConfig() != null) subConfigValue = selection.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation(); 
/* 3052 */       FormDropDownMenu subConfiguration = MilestoneHelper.getSelectionSubConfigurationDropDown("subConfiguration", selection.getSelectionConfig(), subConfigValue, true);
/* 3053 */       subConfiguration.addFormEvent("onChange", "isSubConfigParent(this.selectedIndex)");
/* 3054 */       subConfiguration.setTabIndex(31);
/*      */       
/* 3056 */       selectionForm.addElement(subConfiguration);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3063 */       FormTextField test = new FormTextField("test", "", false, 8, 8);
/* 3064 */       test.setTabIndex(32);
/* 3065 */       test.setClassName("ctrlShort");
/* 3066 */       test.addFormEvent("onChange", "javaScript:clickSell(this,false);");
/* 3067 */       selectionForm.addElement(test);
/*      */ 
/*      */       
/* 3070 */       String sellCode = "";
/* 3071 */       if (selection.getSellCode() != null)
/* 3072 */         sellCode = selection.getSellCode(); 
/* 3073 */       FormDropDownMenu priceCode = new FormDropDownMenu("priceCode", sellCode, "-1" + getSellCodesString(), "&nbsp;" + getSellCodesString(), false);
/* 3074 */       priceCode.setTabIndex(33);
/* 3075 */       priceCode.setClassName("ctrlSmall");
/* 3076 */       selectionForm.addElement(priceCode);
/*      */ 
/*      */       
/* 3079 */       FormTextField testDPC = new FormTextField("testDPC", "", false, 8, 8);
/* 3080 */       testDPC.setTabIndex(39);
/* 3081 */       testDPC.setClassName("ctrlShort");
/* 3082 */       testDPC.addFormEvent("onChange", "javaScript:clickSellDPC(this);");
/* 3083 */       selectionForm.addElement(testDPC);
/*      */ 
/*      */       
/* 3086 */       String sellCodeDPC = "";
/* 3087 */       if (selection.getSellCodeDPC() != null)
/* 3088 */         sellCodeDPC = selection.getSellCodeDPC(); 
/* 3089 */       FormDropDownMenu priceCodeDPC = new FormDropDownMenu("priceCodeDPC", sellCodeDPC, "-1" + getSellCodesStringDPC(), "&nbsp;" + getSellCodesStringDPC(), false);
/* 3090 */       priceCodeDPC.setTabIndex(39);
/* 3091 */       priceCodeDPC.setClassName("ctrlSmall");
/* 3092 */       selectionForm.addElement(priceCodeDPC);
/*      */ 
/*      */       
/* 3095 */       String numberOfUnits = "0";
/*      */       
/* 3097 */       if (selection.getNumberOfUnits() > 0) {
/* 3098 */         numberOfUnits = Integer.toString(selection.getNumberOfUnits());
/*      */       }
/* 3100 */       FormTextField numOfUnits = new FormTextField("numOfUnits", numberOfUnits, false, 10, 10);
/* 3101 */       numOfUnits.setTabIndex(34);
/* 3102 */       numOfUnits.setClassName("ctrlShort");
/* 3103 */       selectionForm.addElement(numOfUnits);
/*      */ 
/*      */ 
/*      */       
/* 3107 */       User labelUserContact = selection.getLabelContact();
/* 3108 */       Vector labelContacts = SelectionManager.getLabelContacts(selection);
/* 3109 */       FormDropDownMenu contactList = MilestoneHelper.getContactsDropDown(context, "contactlist", labelContacts, labelUserContact, true);
/* 3110 */       contactList.setTabIndex(35);
/* 3111 */       contactList.setClassName("ctrlMedium");
/* 3112 */       selectionForm.addElement(contactList);
/*      */ 
/*      */       
/* 3115 */       FormTextField contact = new FormTextField("contact", selection.getOtherContact(), false, 14, 30);
/* 3116 */       contact.setTabIndex(36);
/* 3117 */       contact.setClassName("ctrlMedium");
/* 3118 */       selectionForm.addElement(contact);
/*      */ 
/*      */       
/* 3121 */       FormCheckBox parentalIndicator = new FormCheckBox("parentalIndicator", "", false, selection.getParentalGuidance());
/* 3122 */       parentalIndicator.setTabIndex(37);
/* 3123 */       selectionForm.addElement(parentalIndicator);
/*      */ 
/*      */       
/* 3126 */       FormCheckBox specPkgIndicator = new FormCheckBox("specialPkgIndicator", "", false, selection.getSpecialPackaging());
/* 3127 */       specPkgIndicator.addFormEvent("onDblClick", "document.forms[0].PackagingHelper.value=document.forms[0].package.value;togglePackaging();");
/* 3128 */       specPkgIndicator.setTabIndex(38);
/* 3129 */       selectionForm.addElement(specPkgIndicator);
/*      */ 
/*      */       
/* 3132 */       FormTextField pkg = new FormTextField("package", selection.getSelectionPackaging(), false, 13, 100);
/* 3133 */       pkg.setTabIndex(39);
/* 3134 */       pkg.setClassName("ctrlMedium");
/* 3135 */       pkg.addFormEvent("onDblClick", "document.forms[0].PackagingHelper.value=document.forms[0].package.value;togglePackaging();");
/* 3136 */       selectionForm.addElement(pkg);
/*      */ 
/*      */       
/* 3139 */       FormDropDownMenu genre = MilestoneHelper.getPfmLookupDropDown("genre", Cache.getMusicTypes(), SelectionManager.getLookupObjectValue(selection.getGenre()), false, true);
/* 3140 */       genre.setTabIndex(40);
/* 3141 */       genre.setId("music_type");
/*      */       
/* 3143 */       selectionForm.addElement(genre);
/*      */ 
/*      */       
/* 3146 */       FormTextField territory = new FormTextField("territory", selection.getSelectionTerritory(), false, 13, 255);
/* 3147 */       territory.setTabIndex(41);
/* 3148 */       territory.setClassName("ctrlMedium");
/* 3149 */       territory.addFormEvent("onDblClick", "document.forms[0].TerritoryHelper.value=document.forms[0].territory.value;toggleTerritory();");
/* 3150 */       selectionForm.addElement(territory);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3156 */       String lastStreetDateText = "";
/* 3157 */       if (selection.getLastStreetUpdateDate() != null)
/* 3158 */         lastStreetDateText = MilestoneHelper.getFormatedDate(selection.getLastStreetUpdateDate()); 
/* 3159 */       FormTextField lastStreetUpdatedDate = new FormTextField("laststreetupdateddate", lastStreetDateText, false, 13);
/* 3160 */       selectionForm.addElement(lastStreetUpdatedDate);
/*      */ 
/*      */ 
/*      */       
/* 3164 */       String lastUpdatedDateText = "";
/* 3165 */       if (selection.getLastUpdateDate() != null)
/* 3166 */         lastUpdatedDateText = MilestoneHelper.getCustomFormatedDate(selection.getLastUpdateDate(), "M/d/yyyy hh:mm:ss a 'ET'"); 
/* 3167 */       FormTextField lastUpdatedDate = new FormTextField("lastupdateddate", lastUpdatedDateText, false, 50);
/* 3168 */       selectionForm.addElement(lastUpdatedDate);
/*      */ 
/*      */       
/* 3171 */       String originDateText = "";
/* 3172 */       if (selection.getOriginDate() != null)
/* 3173 */         originDateText = MilestoneHelper.getFormatedDate(selection.getOriginDate()); 
/* 3174 */       FormTextField originDate = new FormTextField("origindate", originDateText, false, 13);
/* 3175 */       selectionForm.addElement(originDate);
/*      */ 
/*      */       
/* 3178 */       String archieDateText = "";
/* 3179 */       if (selection.getArchieDate() != null)
/* 3180 */         archieDateText = MilestoneHelper.getCustomFormatedDate(selection.getArchieDate(), "M/d/yyyy hh:mm:ss a 'ET'"); 
/* 3181 */       FormTextField archieDate = new FormTextField("archieDate", archieDateText, false, 13);
/* 3182 */       selectionForm.addElement(archieDate);
/*      */ 
/*      */       
/* 3185 */       String autoCloseDateText = "";
/* 3186 */       if (selection.getAutoCloseDate() != null)
/* 3187 */         autoCloseDateText = MilestoneHelper.getCustomFormatedDate(selection.getAutoCloseDate(), "M/d/yyyy hh:mm:ss a 'ET'"); 
/* 3188 */       FormTextField autoCloseDate = new FormTextField("autoCloseDate", autoCloseDateText, false, 40);
/* 3189 */       selectionForm.addElement(autoCloseDate);
/*      */ 
/*      */       
/* 3192 */       String lastLegacyUpdateDateText = "";
/* 3193 */       if (selection.getLastLegacyUpdateDate() != null)
/* 3194 */         lastLegacyUpdateDateText = MilestoneHelper.getCustomFormatedDate(selection.getLastLegacyUpdateDate(), "M/d/yyyy hh:mm:ss a 'ET'"); 
/* 3195 */       FormTextField lastLegacyUpdateDate = new FormTextField("lastLegacyUpdateDate", lastLegacyUpdateDateText, false, 40);
/* 3196 */       selectionForm.addElement(lastLegacyUpdateDate);
/*      */ 
/*      */       
/* 3199 */       FormTextArea packagingHelper = new FormTextArea("PackagingHelper", selection.getSelectionPackaging(), false, 2, 44, "virtual");
/* 3200 */       selectionForm.addElement(packagingHelper);
/*      */ 
/*      */       
/* 3203 */       FormTextArea territoryHelper = new FormTextArea("TerritoryHelper", selection.getSelectionTerritory(), false, 2, 44, "virtual");
/* 3204 */       selectionForm.addElement(territoryHelper);
/*      */ 
/*      */       
/* 3207 */       FormTextArea comments = new FormTextArea("comments", selection.getSelectionComments(), false, 6, 44, "virtual");
/* 3208 */       comments.addFormEvent("onBlur", "Javascript:checkField(this)");
/* 3209 */       selectionForm.addElement(comments);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3218 */     addSelectionSearchElements(context, selection, selectionForm, companies, true);
/*      */ 
/*      */     
/* 3221 */     if (context.getSessionValue("NOTEPAD_SELECTION_VISIBLE") != null) {
/* 3222 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_SELECTION_VISIBLE"));
/*      */     }
/* 3224 */     boolean isParent = false;
/*      */     
/* 3226 */     if (selection.getSelectionSubConfig() != null) {
/* 3227 */       isParent = selection.getSelectionSubConfig().isParent();
/*      */     }
/* 3229 */     context.putDelivery("is-parent", String.valueOf(isParent));
/* 3230 */     context.putDelivery("old-selection-no", selection.getSelectionNo());
/*      */     
/* 3232 */     String price = "0.00";
/* 3233 */     if (selection.getPriceCode() != null && 
/* 3234 */       selection.getPriceCode().getTotalCost() > 0.0F) {
/* 3235 */       price = MilestoneHelper.formatDollarPrice(selection.getPriceCode().getTotalCost());
/*      */     }
/* 3237 */     context.putDelivery("price", price);
/*      */     
/* 3239 */     String lastUpdateUser = "";
/* 3240 */     if (selection.getLastUpdatingUser() != null)
/* 3241 */       lastUpdateUser = selection.getLastUpdatingUser().getName(); 
/* 3242 */     context.putDelivery("lastUpdateUser", lastUpdateUser);
/*      */     
/* 3244 */     return selectionForm;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Form buildNewForm(Context context, Selection selection, String command) {
/* 3254 */     Vector projectList = (Vector)context.getSessionValue("searchResults");
/*      */     
/* 3256 */     String resultsIndex = (String)context.getSessionValue("selectionScreenTypeIndex");
/*      */ 
/*      */ 
/*      */     
/* 3260 */     ProjectSearch selectedProject = null;
/* 3261 */     if (resultsIndex != null) {
/* 3262 */       selectedProject = (ProjectSearch)projectList.elementAt(Integer.parseInt(resultsIndex));
/*      */     } else {
/* 3264 */       selectedProject = new ProjectSearch();
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 3269 */     context.removeSessionValue("selectionScreenType");
/* 3270 */     context.removeSessionValue("searchResults");
/* 3271 */     context.removeSessionValue("selectionScreenTypeIndex");
/*      */     
/* 3273 */     Form selectionForm = new Form(this.application, "selectionForm", 
/* 3274 */         this.application.getInfrastructure().getServletURL(), "POST");
/*      */     
/* 3276 */     User user = (User)context.getSession().getAttribute("user");
/* 3277 */     int userId = user.getUserId();
/*      */ 
/*      */     
/* 3280 */     int secureLevel = getSelectionPermissions(selection, user);
/* 3281 */     setButtonVisibilities(selection, user, context, secureLevel, command);
/*      */ 
/*      */     
/* 3284 */     selectionForm.addElement(new FormHidden("cmd", "selection-edit-new", true));
/* 3285 */     selectionForm.addElement(new FormHidden("OrderBy", "", true));
/* 3286 */     selectionForm.addElement(new FormHidden("hidTitleId", "", true));
/* 3287 */     selectionForm.addElement(new FormHidden("isFocus", "", true));
/* 3288 */     selectionForm.addElement(new FormHidden("statusHidVal", "", true));
/* 3289 */     selectionForm.addElement(new FormHidden("generateSelection", "", true));
/*      */     
/* 3291 */     selectionForm.addElement(new FormHidden("GDRSProductStatus", "", false));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3316 */     String strArtistFirstName = (selectedProject.getArtistFirstName() != null) ? selectedProject.getArtistFirstName() : "";
/* 3317 */     FormTextField artistFirstName = new FormTextField("artistFirstName", strArtistFirstName, false, 20, 50);
/* 3318 */     artistFirstName.setTabIndex(1);
/* 3319 */     artistFirstName.setClassName("ctrlMedium");
/* 3320 */     artistFirstName.addFormEvent("onChange", "javaScript:removeSpaces(this);");
/* 3321 */     selectionForm.addElement(artistFirstName);
/*      */ 
/*      */     
/* 3324 */     String strArtistLastName = (selectedProject.getArtistLastName() != null) ? selectedProject.getArtistLastName() : "";
/* 3325 */     FormTextField artistLastName = new FormTextField("artistLastName", strArtistLastName, false, 20, 50);
/* 3326 */     artistLastName.setTabIndex(2);
/* 3327 */     artistLastName.setClassName("ctrlMedium");
/* 3328 */     artistLastName.addFormEvent("onChange", "javaScript:removeSpaces(this);");
/* 3329 */     selectionForm.addElement(artistLastName);
/*      */ 
/*      */     
/* 3332 */     String strTitle = (selectedProject.getTitle() != null) ? selectedProject.getTitle() : "";
/* 3333 */     FormTextField title = new FormTextField("title", strTitle, true, 73, 125);
/* 3334 */     title.setTabIndex(3);
/* 3335 */     title.setClassName("ctrlXLarge");
/* 3336 */     title.addFormEvent("onChange", "javaScript:removeSpaces(this);");
/* 3337 */     selectionForm.addElement(title);
/*      */ 
/*      */     
/* 3340 */     FormTextField sideATitle = new FormTextField("sideATitle", "", false, 20, 125);
/* 3341 */     sideATitle.setTabIndex(4);
/* 3342 */     sideATitle.setClassName("ctrlMedium");
/* 3343 */     selectionForm.addElement(sideATitle);
/*      */ 
/*      */     
/* 3346 */     FormTextField sideBTitle = new FormTextField("sideBTitle", "", false, 20, 125);
/* 3347 */     sideBTitle.setTabIndex(5);
/* 3348 */     sideBTitle.setClassName("ctrlMedium");
/* 3349 */     selectionForm.addElement(sideBTitle);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3354 */     FormTextField streetDate = new FormTextField("streetDate", "", false, 10);
/* 3355 */     streetDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
/* 3356 */     streetDate.addFormEvent("onBlur", "JavaScript:validateDate(document.forms[0].streetDate.value,this);populateNoDigitalRelease();");
/*      */     
/* 3358 */     streetDate.addFormEvent("oldValue", "");
/* 3359 */     streetDate.setTabIndex(6);
/* 3360 */     streetDate.setClassName("ctrlShort");
/* 3361 */     selectionForm.addElement(streetDate);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3366 */     FormTextField drDate = new FormTextField("digitalDateDisplay", "", false, 10);
/* 3367 */     selectionForm.addElement(drDate);
/* 3368 */     FormHidden digitalDate = new FormHidden("digitalDate", "", false);
/* 3369 */     selectionForm.addElement(digitalDate);
/*      */ 
/*      */     
/* 3372 */     DcGDRSResults dcGDRSResults = GDRSProductStatusGet(selection, selectedProject.getMSEnvironmentId());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3389 */     boolean IsDefaultChecked = true;
/* 3390 */     FormHidden ForceNoDigitalRelease = new FormHidden("ForceNoDigitalRelease", "true", false);
/*      */     
/* 3392 */     selectionForm.addElement(ForceNoDigitalRelease);
/*      */     
/* 3394 */     FormCheckBox noDigitalRelease = new FormCheckBox("noDigitalRelease", "", false, IsDefaultChecked);
/* 3395 */     noDigitalRelease.addFormEvent("onChange", "JavaScript:noDigitalReleaseChanged();");
/*      */     
/* 3397 */     noDigitalRelease.addFormEvent("oldValue", "");
/* 3398 */     noDigitalRelease.setTabIndex(7);
/* 3399 */     selectionForm.addElement(noDigitalRelease);
/*      */ 
/*      */     
/* 3402 */     FormDateField intDate = new FormDateField("internationalDate", "", false, 10);
/* 3403 */     intDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
/* 3404 */     intDate.addFormEvent("onBlur", "JavaScript:validateDate(document.forms[0].internationalDate.value,this)");
/* 3405 */     intDate.setTabIndex(8);
/* 3406 */     intDate.setClassName("ctrlShort");
/* 3407 */     selectionForm.addElement(intDate);
/*      */ 
/*      */     
/* 3410 */     FormDropDownMenu status = MilestoneHelper.getLookupDropDown("status", Cache.getSelectionStatusList(), "Active", true, false);
/*      */     
/* 3412 */     status.addFormEvent("oldValue", "Active");
/* 3413 */     status.setTabIndex(9);
/* 3414 */     status.setClassName("ctrlSmall");
/* 3415 */     selectionForm.addElement(status);
/*      */ 
/*      */     
/* 3418 */     FormCheckBox holdIndicator = new FormCheckBox("holdIndicator", "", false, false);
/* 3419 */     holdIndicator.setTabIndex(10);
/* 3420 */     selectionForm.addElement(holdIndicator);
/*      */ 
/*      */     
/* 3423 */     FormTextArea holdReason = new FormTextArea("holdReason", "", false, 2, 44, "virtual");
/* 3424 */     holdReason.addFormEvent("onBlur", "Javascript:checkField(this)");
/* 3425 */     selectionForm.addElement(holdReason);
/*      */ 
/*      */     
/* 3428 */     int pd_indicator = selectedProject.getPD_Indicator();
/* 3429 */     boolean pdBool = false;
/* 3430 */     if (pd_indicator == 1) {
/* 3431 */       pdBool = true;
/*      */     }
/* 3433 */     FormCheckBox pdIndicator = new FormCheckBox("pdIndicator", "", false, pdBool);
/* 3434 */     pdIndicator.setTabIndex(12);
/* 3435 */     selectionForm.addElement(pdIndicator);
/*      */ 
/*      */     
/* 3438 */     FormCheckBox intlFlag = new FormCheckBox("intlFlag", "", false, false);
/* 3439 */     intlFlag.setTabIndex(11);
/* 3440 */     selectionForm.addElement(intlFlag);
/*      */ 
/*      */     
/* 3443 */     FormDateField impactDate = new FormDateField("impactdate", "", false, 13);
/* 3444 */     impactDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
/* 3445 */     impactDate.addFormEvent("onBlur", "JavaScript:validateDate(document.forms[0].impactdate.value,this)");
/* 3446 */     impactDate.setTabIndex(13);
/* 3447 */     impactDate.setClassName("ctrlShort");
/* 3448 */     selectionForm.addElement(impactDate);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3453 */     Vector releaseFamilies = ReleasingFamily.getReleasingFamilies(userId, selectedProject.getMSFamilyId(), context);
/* 3454 */     ReleasingFamily defaultReleasingFamily = ReleasingFamily.getDefaultReleasingFamily(userId, selectedProject.getMSFamilyId(), context);
/* 3455 */     FormDropDownMenu releasingFamily = MilestoneHelper.getReleasingFamilyDropDown("releasingFamily", String.valueOf(defaultReleasingFamily.getReleasingFamilyId()), releaseFamilies, true, selection);
/* 3456 */     releasingFamily.setTabIndex(13);
/*      */     
/* 3458 */     releasingFamily.addFormEvent("onChange", "ReleasingFamilyChange(this)");
/* 3459 */     selectionForm.addElement(releasingFamily);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3464 */     String envId = String.valueOf(selectedProject.getMSEnvironmentId());
/* 3465 */     String envName = MilestoneHelper.getStructureName(selectedProject.getMSEnvironmentId());
/* 3466 */     String environmentName = "";
/*      */     
/* 3468 */     FormHidden evironment = new FormHidden("environment", envId, false);
/* 3469 */     evironment.setDisplayName(envName);
/* 3470 */     selectionForm.addElement(evironment);
/*      */ 
/*      */     
/* 3473 */     String companyId = String.valueOf(selectedProject.getMSCompanyId());
/* 3474 */     String companyName = MilestoneHelper.getStructureName(selectedProject.getMSCompanyId());
/* 3475 */     FormHidden company = new FormHidden("company", companyId, false);
/* 3476 */     company.setTabIndex(15);
/*      */ 
/*      */     
/* 3479 */     company.setDisplayName(companyName);
/* 3480 */     selectionForm.addElement(company);
/*      */ 
/*      */ 
/*      */     
/* 3484 */     String divisionId = String.valueOf(selectedProject.getMSDivisionId());
/* 3485 */     String divisionName = MilestoneHelper.getStructureName(selectedProject.getMSDivisionId());
/* 3486 */     FormHidden division = new FormHidden("division", divisionId, false);
/* 3487 */     division.setTabIndex(16);
/*      */ 
/*      */     
/* 3490 */     division.setDisplayName(divisionName);
/* 3491 */     selectionForm.addElement(division);
/*      */ 
/*      */     
/* 3494 */     String labelId = String.valueOf(selectedProject.getMSLabelId());
/* 3495 */     String labelName = MilestoneHelper.getStructureName(selectedProject.getMSLabelId());
/* 3496 */     FormHidden label = new FormHidden("label", labelId, false);
/* 3497 */     label.setTabIndex(17);
/* 3498 */     label.setDisplayName(labelName);
/* 3499 */     selectionForm.addElement(label);
/*      */ 
/*      */     
/* 3502 */     if (selectedProject.getOperCompany().equals("***")) {
/* 3503 */       FormHidden opercompany = new FormHidden("opercompany", "***", false);
/* 3504 */       opercompany.setTabIndex(18);
/* 3505 */       opercompany.setDisplayName("***");
/* 3506 */       selectionForm.addElement(opercompany);
/*      */     } else {
/* 3508 */       LookupObject oc = MilestoneHelper.getLookupObject(selectedProject
/* 3509 */           .getOperCompany(), Cache.getOperatingCompanies());
/*      */       
/* 3511 */       String ocAbbr = "";
/* 3512 */       String ocName = "";
/*      */ 
/*      */ 
/*      */       
/* 3516 */       if (oc == null) {
/* 3517 */         ocAbbr = selectedProject.getOperCompany();
/*      */       } else {
/* 3519 */         if (oc != null && oc.getAbbreviation() != null)
/* 3520 */           ocAbbr = oc.getAbbreviation(); 
/* 3521 */         if (oc != null && oc.getName() != null)
/* 3522 */           ocName = ":" + oc.getName(); 
/*      */       } 
/* 3524 */       FormHidden opercompany = new FormHidden("opercompany", ocAbbr, false);
/* 3525 */       opercompany.setTabIndex(18);
/* 3526 */       opercompany.setDisplayName(String.valueOf(ocAbbr) + ocName);
/*      */       
/* 3528 */       if (ocAbbr.equals("ZZ"))
/* 3529 */         opercompany.setDisplayName(ocAbbr); 
/* 3530 */       selectionForm.addElement(opercompany);
/*      */     } 
/*      */ 
/*      */     
/* 3534 */     FormHidden superlabel = new FormHidden("superlabel", selectedProject.getSuperLabel(), false);
/* 3535 */     superlabel.setTabIndex(19);
/* 3536 */     superlabel.setClassName("ctrlShort");
/* 3537 */     superlabel.addFormEvent("onBlur", "javascript:this.value=this.value.toUpperCase();");
/* 3538 */     selectionForm.addElement(superlabel);
/*      */ 
/*      */     
/* 3541 */     FormHidden distCoLabelID = new FormHidden("distCoLabelID", labelId, false);
/* 3542 */     distCoLabelID.setDisplayName(labelId);
/* 3543 */     selectionForm.addElement(distCoLabelID);
/*      */ 
/*      */     
/* 3546 */     FormHidden sublabel = new FormHidden("sublabel", selectedProject.getSubLabel(), false);
/* 3547 */     sublabel.setTabIndex(20);
/* 3548 */     sublabel.setClassName("ctrlShort");
/* 3549 */     sublabel.addFormEvent("onBlur", "javascript:this.value=this.value.toUpperCase();");
/* 3550 */     selectionForm.addElement(sublabel);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3555 */     FormDropDownMenu configcode = MilestoneHelper.getPfmLookupDropDown("configcode", MilestoneHelper.getConfigCodes(0), "", false, true);
/* 3556 */     configcode.setTabIndex(21);
/*      */     
/* 3558 */     configcode.addFormEvent("onKeyPress", "scanKey(window.event.keyCode, 'document.all.configcode.options', getMaxLength(document.all.configcode.options))");
/*      */ 
/*      */ 
/*      */     
/* 3562 */     configcode.addFormEvent("onChange", "setNoDigitalRelease(this);");
/*      */ 
/*      */     
/* 3565 */     selectionForm.addElement(configcode);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3570 */     Vector jdeExceptionFamilies = ProjectSearchManager.getInstance().getProjectSearchJDEFamilies();
/* 3571 */     boolean isUmvdUser = jdeExceptionFamilies.contains(new Integer(selectedProject.getMSFamilyId()));
/*      */ 
/*      */     
/* 3574 */     String imprintStr = "";
/* 3575 */     if (isUmvdUser) {
/* 3576 */       imprintStr = labelName;
/*      */     } else {
/* 3578 */       imprintStr = (selectedProject.getImprint() != null) ? selectedProject.getImprint() : "";
/*      */     } 
/* 3580 */     FormTextField imprint = new FormTextField("imprint", imprintStr, false, 50);
/* 3581 */     imprint.setTabIndex(21);
/* 3582 */     imprint.setClassName("ctrlMedium");
/* 3583 */     imprint.addFormEvent("onChange", "javaScript:removeSpaces(this);");
/* 3584 */     selectionForm.addElement(imprint);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3594 */     String projectIdStr = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3607 */     projectIdStr = selectedProject.getRMSProjectNo();
/* 3608 */     FormHidden projectId = new FormHidden("projectId", projectIdStr, false);
/* 3609 */     projectId.setTabIndex(22);
/* 3610 */     projectId.setClassName("ctrlMedium");
/* 3611 */     projectId.setDisplayName(projectIdStr);
/* 3612 */     selectionForm.addElement(projectId);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3617 */     FormTextField gridNumber = new FormTextField("gridNumber", selection.getGridNumber(), false, 50);
/* 3618 */     gridNumber.setTabIndex(25);
/*      */     
/* 3620 */     gridNumber.setEnabled(true);
/* 3621 */     selectionForm.addElement(gridNumber);
/*      */ 
/*      */ 
/*      */     
/* 3625 */     FormTextField upc = new FormTextField("UPC", "", false, 17, 20);
/* 3626 */     upc.setTabIndex(23);
/* 3627 */     upc.setClassName("ctrlMedium");
/*      */     
/* 3629 */     upc.addFormEvent("onKeyPress", "return checkNumbersOnly(window.event.keyCode);");
/*      */ 
/*      */     
/* 3632 */     selectionForm.addElement(upc);
/*      */ 
/*      */ 
/*      */     
/* 3636 */     FormTextField soundscan = new FormTextField("soundscan", "", false, 17, 20);
/* 3637 */     soundscan.setTabIndex(24);
/* 3638 */     soundscan.setClassName("ctrlMedium");
/*      */ 
/*      */     
/* 3641 */     soundscan.addFormEvent("onKeyPress", "return checkNumbersOnly(window.event.keyCode);");
/*      */ 
/*      */ 
/*      */     
/* 3645 */     selectionForm.addElement(soundscan);
/*      */ 
/*      */     
/* 3648 */     FormDropDownMenu prefix = MilestoneHelper.getPrefixCodeDropDown(userId, "prefix", Cache.getPrefixCodes(), "", true, context);
/* 3649 */     prefix.setTabIndex(25);
/* 3650 */     prefix.setClassName("ctrlShort");
/* 3651 */     selectionForm.addElement(prefix);
/*      */ 
/*      */     
/* 3654 */     FormTextField selectionNo = new FormTextField("selectionNo", "", false, 20, 20);
/* 3655 */     selectionNo.setTabIndex(26);
/* 3656 */     selectionNo.setClassName("ctrlMedium");
/* 3657 */     selectionNo.addFormEvent("onChange", "javaScript:removeSpaces(this);");
/* 3658 */     selectionForm.addElement(selectionNo);
/*      */ 
/*      */     
/* 3661 */     FormTextField titleId = new FormTextField("titleId", "", false, 13, 24);
/* 3662 */     titleId.setClassName("ctrlMedium");
/*      */     
/* 3664 */     titleId.setTabIndex(27);
/*      */ 
/*      */     
/* 3667 */     selectionForm.addElement(titleId);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3674 */     FormDropDownMenu productLine = MilestoneHelper.getLookupDropDown("productLine", Cache.getProductCategories(0), "", true, true);
/* 3675 */     productLine.setTabIndex(28);
/* 3676 */     productLine.setClassName("ctrlMedium");
/* 3677 */     selectionForm.addElement(productLine);
/*      */ 
/*      */     
/* 3680 */     FormDropDownMenu releaseType = MilestoneHelper.getLookupDropDown("releaseType", Cache.getReleaseTypes(), "", true, true);
/* 3681 */     releaseType.setTabIndex(29);
/* 3682 */     releaseType.setClassName("ctrlMedium");
/* 3683 */     releaseType.addFormEvent("onChange", "releaseTypeChanged()");
/* 3684 */     selectionForm.addElement(releaseType);
/*      */ 
/*      */     
/* 3687 */     FormDropDownMenu configuration = MilestoneHelper.getSelectionConfigurationDropDown("configuration", "", true, 0);
/* 3688 */     configuration.setTabIndex(30);
/*      */     
/* 3690 */     configuration.addFormEvent("onChange", "buildSubConfigs(this.selectedIndex)");
/* 3691 */     selectionForm.addElement(configuration);
/*      */ 
/*      */ 
/*      */     
/* 3695 */     Vector configs = Cache.getSelectionConfigs();
/* 3696 */     SelectionConfiguration config = (SelectionConfiguration)configs.get(0);
/* 3697 */     FormDropDownMenu subConfiguration = new FormDropDownMenu("subConfiguration", "", "", "", true);
/*      */     
/* 3699 */     subConfiguration.addFormEvent("onChange", "isSubConfigParent(this.selectedIndex)");
/* 3700 */     subConfiguration.setTabIndex(31);
/* 3701 */     subConfiguration.setEnabled(false);
/*      */ 
/*      */     
/* 3704 */     selectionForm.addElement(subConfiguration);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3711 */     FormTextField test = new FormTextField("test", "", false, 8, 8);
/* 3712 */     test.setTabIndex(32);
/* 3713 */     test.setClassName("ctrlShort");
/* 3714 */     test.addFormEvent("onChange", "javaScript:clickSell(this,false);");
/* 3715 */     selectionForm.addElement(test);
/*      */ 
/*      */     
/* 3718 */     FormDropDownMenu priceCode = new FormDropDownMenu("priceCode", "", "-1" + getSellCodesString(), "&nbsp;" + getSellCodesString(), true);
/* 3719 */     priceCode.setTabIndex(33);
/* 3720 */     priceCode.setClassName("ctrlSmall");
/* 3721 */     selectionForm.addElement(priceCode);
/*      */ 
/*      */     
/* 3724 */     FormTextField testDPC = new FormTextField("testDPC", "", false, 8, 8);
/* 3725 */     testDPC.setTabIndex(39);
/* 3726 */     testDPC.setClassName("ctrlShort");
/* 3727 */     testDPC.addFormEvent("onChange", "javaScript:clickSellDPC(this);");
/* 3728 */     selectionForm.addElement(testDPC);
/*      */ 
/*      */     
/* 3731 */     FormDropDownMenu priceCodeDPC = new FormDropDownMenu("priceCodeDPC", "", "-1" + getSellCodesStringDPC(), "&nbsp;" + getSellCodesStringDPC(), false);
/* 3732 */     priceCodeDPC.setTabIndex(39);
/* 3733 */     priceCodeDPC.setClassName("ctrlSmall");
/* 3734 */     selectionForm.addElement(priceCodeDPC);
/*      */ 
/*      */     
/* 3737 */     FormTextField numOfUnits = new FormTextField("numOfUnits", "0", false, 10, 10);
/* 3738 */     numOfUnits.setTabIndex(34);
/* 3739 */     numOfUnits.setClassName("ctrlShort");
/* 3740 */     selectionForm.addElement(numOfUnits);
/*      */ 
/*      */     
/* 3743 */     FormDropDownMenu contactList = MilestoneHelper.getContactsDropDown(context, "contactlist", new Vector(), user, true);
/* 3744 */     contactList.setTabIndex(35);
/* 3745 */     contactList.setClassName("ctrlMedium");
/* 3746 */     selectionForm.addElement(contactList);
/*      */ 
/*      */     
/* 3749 */     FormTextField contact = new FormTextField("contact", "", false, 14, 30);
/* 3750 */     contact.setTabIndex(36);
/* 3751 */     contact.setClassName("ctrlMedium");
/* 3752 */     selectionForm.addElement(contact);
/*      */ 
/*      */     
/* 3755 */     FormCheckBox parentalIndicator = new FormCheckBox("parentalIndicator", "", false, false);
/* 3756 */     parentalIndicator.setTabIndex(37);
/* 3757 */     selectionForm.addElement(parentalIndicator);
/*      */ 
/*      */     
/* 3760 */     FormCheckBox specPkgIndicator = new FormCheckBox("specialPkgIndicator", "", false, false);
/* 3761 */     specPkgIndicator.addFormEvent("onDblClick", "document.forms[0].PackagingHelper.value=document.forms[0].package.value;togglePackaging();");
/* 3762 */     specPkgIndicator.setTabIndex(38);
/* 3763 */     selectionForm.addElement(specPkgIndicator);
/*      */ 
/*      */     
/* 3766 */     FormTextField pkg = new FormTextField("package", "", false, 13, 100);
/* 3767 */     pkg.setTabIndex(39);
/* 3768 */     pkg.setClassName("ctrlMedium");
/* 3769 */     pkg.addFormEvent("onDblClick", "document.forms[0].PackagingHelper.value=document.forms[0].package.value;togglePackaging();");
/* 3770 */     selectionForm.addElement(pkg);
/*      */ 
/*      */     
/* 3773 */     FormDropDownMenu genre = MilestoneHelper.getPfmLookupDropDown("genre", Cache.getMusicTypes(), "", true, true);
/*      */     
/* 3775 */     genre.setTabIndex(40);
/* 3776 */     genre.setId("music_type");
/*      */     
/* 3778 */     selectionForm.addElement(genre);
/*      */ 
/*      */     
/* 3781 */     FormTextField territory = new FormTextField("territory", "", false, 13, 255);
/* 3782 */     territory.setTabIndex(41);
/* 3783 */     territory.setClassName("ctrlMedium");
/* 3784 */     territory.addFormEvent("onDblClick", "document.forms[0].TerritoryHelper.value=document.forms[0].territory.value;toggleTerritory();");
/* 3785 */     selectionForm.addElement(territory);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3791 */     FormTextField lastStreetUpdatedDate = new FormTextField("laststreetupdateddate", "", false, 13);
/* 3792 */     selectionForm.addElement(lastStreetUpdatedDate);
/*      */ 
/*      */     
/* 3795 */     FormTextField lastUpdatedDate = new FormTextField("lastupdateddate", "", false, 50);
/* 3796 */     selectionForm.addElement(lastUpdatedDate);
/*      */ 
/*      */     
/* 3799 */     FormTextField originDate = new FormTextField("origindate", "", false, 13);
/* 3800 */     selectionForm.addElement(originDate);
/*      */ 
/*      */     
/* 3803 */     FormTextArea packagingHelper = new FormTextArea("PackagingHelper", "", false, 2, 44, "virtual");
/* 3804 */     selectionForm.addElement(packagingHelper);
/*      */ 
/*      */     
/* 3807 */     FormTextArea territoryHelper = new FormTextArea("TerritoryHelper", selection.getSelectionTerritory(), false, 2, 44, "virtual");
/* 3808 */     selectionForm.addElement(territoryHelper);
/*      */ 
/*      */     
/* 3811 */     FormTextArea comments = new FormTextArea("comments", "", false, 2, 44, "virtual");
/* 3812 */     comments.addFormEvent("onBlur", "Javascript:checkField(this)");
/* 3813 */     selectionForm.addElement(comments);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3820 */     addSelectionSearchElements(context, new Selection(), selectionForm, MilestoneHelper.getUserCompanies(context), true);
/*      */ 
/*      */     
/* 3823 */     if (context.getSessionValue("NOTEPAD_SELECTION_VISIBLE") != null) {
/* 3824 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_SELECTION_VISIBLE"));
/*      */     }
/*      */     
/* 3827 */     context.putDelivery("releaseWeek", "");
/* 3828 */     context.putDelivery("new-or-copy", "true");
/* 3829 */     context.putDelivery("price", "0.00");
/*      */     
/* 3831 */     boolean isParent = false;
/*      */     
/* 3833 */     if (selection.getSelectionSubConfig() != null) {
/* 3834 */       isParent = selection.getSelectionSubConfig().isParent();
/*      */     }
/* 3836 */     context.putDelivery("is-parent", String.valueOf(isParent));
/*      */     
/* 3838 */     String lastUpdateUser = "";
/* 3839 */     if (selection.getLastUpdatingUser() != null)
/* 3840 */       lastUpdateUser = selection.getLastUpdatingUser().getName(); 
/* 3841 */     context.putDelivery("lastUpdateUser", lastUpdateUser);
/*      */     
/* 3843 */     return selectionForm;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Form buildManufacturingForm(Context context, Selection selection, String command, int accessLevel, boolean newFlag) {
/* 3852 */     String mainCommandString = "";
/* 3853 */     String holdReasonString = "";
/* 3854 */     String distributionString = "";
/* 3855 */     String mfgCommentsString = "";
/* 3856 */     String numberOfUnits = "0";
/*      */     
/* 3858 */     User umlContactUser = null;
/*      */ 
/*      */     
/* 3861 */     String selectedConfig = "";
/* 3862 */     String selectedSubConfig = "";
/* 3863 */     if (selection.getSelectionConfig() != null && selection.getSelectionConfig().getSelectionConfigurationAbbreviation() != null)
/* 3864 */       selectedConfig = selection.getSelectionConfig().getSelectionConfigurationAbbreviation(); 
/* 3865 */     if (selection.getSelectionSubConfig() != null && selection.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation() != null) {
/* 3866 */       selectedSubConfig = selection.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3875 */     Form selectionForm = new Form(this.application, "selectionForm", 
/* 3876 */         this.application.getInfrastructure().getServletURL(), "POST");
/*      */     
/* 3878 */     if (!newFlag) {
/*      */       
/* 3880 */       if (selection != null)
/*      */       {
/* 3882 */         mainCommandString = "selection-manufacturing-editor";
/*      */         
/* 3884 */         if (selection.getUmlContact() != null) {
/* 3885 */           umlContactUser = selection.getUmlContact();
/*      */         }
/* 3887 */         if (selection.getManufacturingComments() != null && selection.getManufacturingComments() != null)
/*      */         {
/* 3889 */           mfgCommentsString = selection.getManufacturingComments();
/*      */         }
/*      */         
/* 3892 */         if (selection.getNumberOfUnits() > 0) {
/* 3893 */           numberOfUnits = Integer.toString(selection.getNumberOfUnits());
/*      */         }
/* 3895 */         if (selection.getDistribution() != null) {
/* 3896 */           distributionString = selection.getDistribution().getAbbreviation();
/*      */         }
/* 3898 */         selectionForm.addElement(new FormHidden("new", "false"));
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/* 3903 */       if (selection.getNumberOfUnits() > 0) {
/* 3904 */         numberOfUnits = Integer.toString(selection.getNumberOfUnits());
/*      */       }
/* 3906 */       mainCommandString = "selection-manufacturing-edit-new";
/* 3907 */       selectionForm.addElement(new FormHidden("new", "true"));
/*      */     } 
/*      */     
/* 3910 */     if (selection != null) {
/*      */ 
/*      */       
/* 3913 */       String lastMfgUpdatedDateText = "";
/* 3914 */       if (selection.getLastMfgUpdateDate() != null)
/* 3915 */         lastMfgUpdatedDateText = MilestoneHelper.getCustomFormatedDate(selection.getLastMfgUpdateDate(), "M/d/yyyy hh:mm:ss a 'ET'"); 
/* 3916 */       context.putDelivery("lastUpdateDate", lastMfgUpdatedDateText);
/*      */       
/* 3918 */       String lastMfgUpdateUser = "";
/* 3919 */       if (selection.getLastMfgUpdatingUser() != null)
/* 3920 */         lastMfgUpdateUser = selection.getLastMfgUpdatingUser().getName(); 
/* 3921 */       context.putDelivery("lastUpdateUser", lastMfgUpdateUser);
/*      */ 
/*      */       
/* 3924 */       if (numberOfUnits.equals("0")) {
/* 3925 */         numberOfUnits = "";
/*      */       }
/* 3927 */       context.putDelivery("numberOfUnits", numberOfUnits);
/* 3928 */       context.putDelivery("upc", selection.getUpc());
/*      */       
/* 3930 */       context.putDelivery("label", selection.getImprint());
/* 3931 */       context.putDelivery("status", selection.getSelectionStatus().getName());
/* 3932 */       String typeConfig = String.valueOf(selection.getProductCategory().getName()) + " / " + selection.getReleaseType().getName() + " / " + selection.getSelectionConfig().getSelectionConfigurationName() + " / " + selection.getSelectionSubConfig().getSelectionSubConfigurationName();
/* 3933 */       context.putDelivery("typeConfig", typeConfig);
/*      */ 
/*      */ 
/*      */       
/* 3937 */       FormTextArea comments = new FormTextArea("comments", selection.getSelectionComments(), false, 2, 44, "virtual");
/* 3938 */       comments.addFormEvent("onBlur", "Javascript:checkField(this)");
/* 3939 */       comments.setReadOnly(true);
/* 3940 */       selectionForm.addElement(comments);
/*      */ 
/*      */       
/* 3943 */       context.putDelivery("releaseWeek", MilestoneHelper.getReleaseWeekString(selection));
/*      */ 
/*      */ 
/*      */       
/* 3947 */       context.putDelivery("releasingFamily", ReleasingFamily.getName(selection.getReleaseFamilyId()));
/*      */ 
/*      */       
/* 3950 */       selectionForm.addElement(new FormHidden("cmd", "selection-manufacturing-editor", true));
/* 3951 */       selectionForm.addElement(new FormHidden("OrderBy", "", true));
/*      */ 
/*      */       
/* 3954 */       FormTextArea holdReason = new FormTextArea("holdReason", selection.getHoldReason(), false, 2, 44, "virtual");
/* 3955 */       holdReason.setReadOnly(true);
/* 3956 */       holdReason.addFormEvent("onBlur", "Javascript:checkField(this)");
/*      */ 
/*      */       
/* 3959 */       FormDropDownMenu umlContact = MilestoneHelper.getContactsDropDown(context, "umlcontact", Cache.getUmlUsers(), umlContactUser, true);
/* 3960 */       umlContact.setId("umlcontact");
/*      */ 
/*      */       
/* 3963 */       FormDropDownMenu distribution = MilestoneHelper.getLookupDropDown("distribution", Cache.getDistributionCodes(), distributionString, true, true);
/* 3964 */       distribution.setId("distribution");
/*      */ 
/*      */       
/* 3967 */       FormTextArea mfgcommentsTextArea = new FormTextArea("orderCommentHelper", mfgCommentsString, false, 2, 44, "virtual");
/*      */ 
/*      */       
/* 3970 */       Vector vendors = new Vector();
/*      */       
/* 3972 */       if (selection.getManufacturingPlants() != null) {
/* 3973 */         vendors = selection.getManufacturingPlants();
/*      */       }
/* 3975 */       String vendorString = "";
/* 3976 */       String poQtyNumber = "0";
/* 3977 */       String completedQtyNumber = "0";
/* 3978 */       String explodedTotal = "0";
/*      */ 
/*      */       
/* 3981 */       for (int vendorCount = 0; vendorCount < vendors.size(); vendorCount++) {
/*      */         
/* 3983 */         vendorString = "";
/* 3984 */         poQtyNumber = "0";
/* 3985 */         completedQtyNumber = "0";
/* 3986 */         explodedTotal = "0";
/*      */         
/* 3988 */         Plant plant = (Plant)vendors.get(vendorCount);
/*      */         
/* 3990 */         if (plant.getOrderQty() > 0) {
/* 3991 */           poQtyNumber = Integer.toString(plant.getOrderQty());
/*      */         }
/* 3993 */         if (plant.getCompletedQty() > 0) {
/* 3994 */           completedQtyNumber = Integer.toString(plant.getCompletedQty());
/*      */         }
/* 3996 */         if (plant.getOrderQty() > 0 && selection.getNumberOfUnits() > 0) {
/* 3997 */           explodedTotal = Integer.toString(plant.getCompletedQty() * selection.getNumberOfUnits());
/*      */         }
/* 3999 */         if (plant.getPlant() != null) {
/* 4000 */           vendorString = plant.getPlant().getAbbreviation();
/*      */         }
/*      */         
/* 4003 */         FormDropDownMenu vendor = MilestoneHelper.getLookupDropDown("plant" + vendorCount, Cache.getVendors(), vendorString, true, true);
/* 4004 */         vendor.setId("plant");
/*      */ 
/*      */         
/* 4007 */         FormTextField poQty = new FormTextField("po_qty" + vendorCount, poQtyNumber, true, 8);
/* 4008 */         poQty.setId("po_qty");
/* 4009 */         poQty.addFormEvent("align", "right");
/*      */ 
/*      */         
/* 4012 */         FormTextField completedQty = new FormTextField("completed_qty" + vendorCount, completedQtyNumber, true, 8);
/* 4013 */         completedQty.setId("completed_qty");
/* 4014 */         completedQty.addFormEvent("align", "right");
/*      */ 
/*      */         
/* 4017 */         FormTextField explode = new FormTextField("explode" + vendorCount, explodedTotal, true, 8);
/* 4018 */         explode.setId("explode");
/* 4019 */         explode.addFormEvent("align", "right");
/*      */         
/* 4021 */         selectionForm.addElement(vendor);
/* 4022 */         selectionForm.addElement(poQty);
/* 4023 */         selectionForm.addElement(completedQty);
/* 4024 */         selectionForm.addElement(explode);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 4044 */       selectionForm.addElement(holdReason);
/* 4045 */       selectionForm.addElement(umlContact);
/* 4046 */       selectionForm.addElement(distribution);
/* 4047 */       selectionForm.addElement(comments);
/* 4048 */       selectionForm.addElement(mfgcommentsTextArea);
/*      */     } 
/*      */     
/* 4051 */     User user = (User)context.getSessionValue("user");
/*      */     
/* 4053 */     int secureLevel = getSelectionMfgPermissions(selection, user);
/* 4054 */     setButtonVisibilities(selection, user, context, secureLevel, command);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4059 */     context.putDelivery("releaseWeek", MilestoneHelper.getReleaseWeekString(selection));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4064 */     Vector companies = MilestoneHelper.getUserCompanies(context);
/* 4065 */     addSelectionSearchElements(context, selection, selectionForm, companies, true);
/*      */     
/* 4067 */     if (context.getSessionValue("NOTEPAD_MFG_VISIBLE") != null) {
/* 4068 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_MFG_VISIBLE"));
/*      */     }
/*      */     
/* 4071 */     return selectionForm;
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
/*      */   protected static void addSelectionSearchElements(Context context, Selection selection, Form selectionForm, Vector companies, boolean includeJSArrays) {
/* 4085 */     User user = (User)context.getSessionValue("user");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4091 */     if (includeJSArrays) {
/*      */ 
/*      */       
/* 4094 */       String selectedConfig = "";
/* 4095 */       String selectedSubConfig = "";
/* 4096 */       if (selection != null && selection.getSelectionConfig() != null && selection.getSelectionConfig().getSelectionConfigurationAbbreviation() != null)
/* 4097 */         selectedConfig = selection.getSelectionConfig().getSelectionConfigurationAbbreviation(); 
/* 4098 */       if (selection != null && selection.getSelectionSubConfig() != null && selection.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation() != null) {
/* 4099 */         selectedSubConfig = selection.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation();
/*      */       }
/*      */       
/* 4102 */       context.putDelivery("selectionArrays", String.valueOf(Cache.getJavaScriptConfigArray(selectedConfig)) + 
/*      */           
/* 4104 */           " " + Cache.getJavaScriptPriceCodeArray() + " " + Cache.getJavaScriptPriceCodeDPCArray() + " " + Cache.getJavaScriptSubConfigArray(selectedSubConfig) + 
/* 4105 */           " " + ReleasingFamily.getJavaScriptCorporateArrayReleasingFamilySearch(context) + 
/*      */           
/* 4107 */           " " + Cache.getJavaScriptPFMConfigs());
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4114 */     boolean defaultStatus = false;
/* 4115 */     FormCheckBox showAllSearch = new FormCheckBox("ShowAllSearch", "", false, defaultStatus);
/* 4116 */     showAllSearch.setId("ShowAllSearch");
/* 4117 */     selectionForm.addElement(showAllSearch);
/*      */ 
/*      */     
/* 4120 */     Vector families = filterCSO(ReleasingFamily.getUserReleasingFamiliesVectorOfFamilies(context));
/*      */     
/* 4122 */     String defaultReleasingFamily = "-1";
/* 4123 */     FormDropDownMenu Family = MilestoneHelper.getCorporateStructureDropDown("FamilySearch", families, defaultReleasingFamily, false, true);
/* 4124 */     Family.addFormEvent("onChange", "return(clickFamilySearch(this))");
/* 4125 */     Family.setId("FamilySearch");
/* 4126 */     selectionForm.addElement(Family);
/*      */ 
/*      */     
/* 4129 */     Vector environments = MilestoneHelper.getUserEnvironments(context);
/* 4130 */     Vector myCompanies = MilestoneHelper.getUserCompanies(context);
/* 4131 */     environments = filterSelectionEnvironments(myCompanies);
/*      */ 
/*      */     
/* 4134 */     environments = MilestoneHelper.removeUnusedCSO(environments, context, -1);
/*      */ 
/*      */     
/* 4137 */     String defaultEnvironment = "-1";
/*      */ 
/*      */     
/* 4140 */     FormDropDownMenu envMenu = MilestoneHelper.getCorporateStructureDropDown("EnvironmentSearch", environments, defaultEnvironment, false, true);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4145 */     envMenu.addFormEvent("onChange", "return(clickEnvironmentSearch(this))");
/* 4146 */     envMenu.setId("EnvironmentSearch");
/* 4147 */     selectionForm.addElement(envMenu);
/*      */ 
/*      */     
/* 4150 */     Vector searchCompanies = null;
/*      */ 
/*      */ 
/*      */     
/* 4154 */     searchCompanies = MilestoneHelper.getUserCompanies(context);
/*      */ 
/*      */     
/* 4157 */     searchCompanies = MilestoneHelper.removeUnusedCSO(searchCompanies, context, -1);
/*      */ 
/*      */     
/* 4160 */     FormDropDownMenu companySearch = MilestoneHelper.getCorporateStructureDropDown("CompanySearch", searchCompanies, "", false, true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4166 */     companySearch.setId("CompanySearch");
/* 4167 */     companySearch.addFormEvent("onChange", "return(clickCompanySearch(this))");
/* 4168 */     selectionForm.addElement(companySearch);
/*      */ 
/*      */ 
/*      */     
/* 4172 */     Vector labels = MilestoneHelper.getUserLabels(companies);
/*      */ 
/*      */     
/* 4175 */     labels = MilestoneHelper.removeUnusedCSO(labels, context, -1);
/*      */     
/* 4177 */     FormDropDownMenu labelSearch = MilestoneHelper.getCorporateStructureDropDownDuplicates("LabelSearch", labels, "", false, true, false);
/* 4178 */     labelSearch.setId("LabelSearch");
/* 4179 */     selectionForm.addElement(labelSearch);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4202 */     User defaultContact = null;
/*      */     
/* 4204 */     Vector labelContacts = SelectionManager.getLabelContactsExcludeUml(context);
/* 4205 */     FormDropDownMenu searchContact = MilestoneHelper.getContactsDropDown(context, "ContactSearch", labelContacts, defaultContact, true);
/* 4206 */     selectionForm.addElement(searchContact);
/*      */ 
/*      */     
/* 4209 */     FormTextField streetDateSearch = new FormTextField("StreetDateSearch", "", false, 14, 10);
/* 4210 */     streetDateSearch.addFormEvent("onBlur", "JavaScript:removeSpacesInDate(document.forms[0].StreetDateSearch.value,this)");
/* 4211 */     streetDateSearch.setId("StreetDateSearch");
/* 4212 */     selectionForm.addElement(streetDateSearch);
/*      */ 
/*      */     
/* 4215 */     FormTextField streetEndDateSearch = new FormTextField("StreetEndDateSearch", "", false, 14, 10);
/* 4216 */     streetEndDateSearch.addFormEvent("onBlur", "JavaScript:removeSpacesInDate(document.forms[0].StreetEndDateSearch.value,this)");
/* 4217 */     streetEndDateSearch.setId("StreetEndDateSearch");
/* 4218 */     selectionForm.addElement(streetEndDateSearch);
/*      */ 
/*      */     
/* 4221 */     String[] dvalues = new String[3];
/* 4222 */     dvalues[0] = "physical";
/* 4223 */     dvalues[1] = "digital";
/* 4224 */     dvalues[2] = "both";
/*      */     
/* 4226 */     String[] dlabels = new String[3];
/* 4227 */     dlabels[0] = "Physical";
/* 4228 */     dlabels[1] = "Digital";
/* 4229 */     dlabels[2] = "Both";
/*      */ 
/*      */     
/* 4232 */     String defaultProdType = "both";
/* 4233 */     FormRadioButtonGroup prodType = new FormRadioButtonGroup("ProdType", defaultProdType, dvalues, dlabels, false);
/* 4234 */     prodType.addFormEvent("onClick", "buildSearchConfigs(this)");
/* 4235 */     selectionForm.addElement(prodType);
/*      */ 
/*      */ 
/*      */     
/* 4239 */     Vector searchConfigs = null;
/* 4240 */     searchConfigs = Cache.getSelectionConfigs();
/* 4241 */     FormDropDownMenu configSearch = MilestoneHelper.getSelectionConfigurationDropDown("ConfigSearch", "", false);
/* 4242 */     configSearch.setId("ConfigSearch");
/* 4243 */     configSearch.addFormEvent("onChange", "buildSearchSubConfigs(this.selectedIndex)");
/* 4244 */     selectionForm.addElement(configSearch);
/*      */ 
/*      */ 
/*      */     
/* 4248 */     FormDropDownMenu subconfigSearch = new FormDropDownMenu("SubconfigSearch", "");
/* 4249 */     subconfigSearch.setId("SubconfigSearch");
/* 4250 */     subconfigSearch.setEnabled(false);
/* 4251 */     selectionForm.addElement(subconfigSearch);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4256 */     FormTextField upcSearch = new FormTextField("UPCSearch", "", false, 20, 20);
/* 4257 */     upcSearch.setId("UPCSearch");
/* 4258 */     selectionForm.addElement(upcSearch);
/*      */ 
/*      */     
/* 4261 */     FormTextField prefixSearch = new FormTextField("PrefixSearch", "", false, 6, 5);
/* 4262 */     prefixSearch.setId("PrefixSearch");
/* 4263 */     selectionForm.addElement(prefixSearch);
/*      */ 
/*      */     
/* 4266 */     FormTextField selectionSearch = new FormTextField("SelectionSearch", "", false, 12, 20);
/* 4267 */     selectionSearch.setId("SelectionSearch");
/* 4268 */     selectionSearch.setClassName("ctrlMedium");
/* 4269 */     selectionForm.addElement(selectionSearch);
/*      */ 
/*      */     
/* 4272 */     FormTextField titleSearch = new FormTextField("TitleSearch", "", false, 20);
/* 4273 */     titleSearch.setId("TitleSearch");
/* 4274 */     selectionForm.addElement(titleSearch);
/*      */ 
/*      */     
/* 4277 */     FormTextField artistSearch = new FormTextField("ArtistSearch", "", false, 20);
/* 4278 */     artistSearch.setId("ArtistSearch");
/* 4279 */     selectionForm.addElement(artistSearch);
/*      */ 
/*      */ 
/*      */     
/* 4283 */     FormTextField projectIDSearch = new FormTextField("ProjectIDSearch", "", false, 20);
/* 4284 */     projectIDSearch.setId("ProjectIDSearch");
/* 4285 */     selectionForm.addElement(projectIDSearch);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4290 */     getUserPreferences(selectionForm, context);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void getUserPreferences(Form form, Context context) {
/* 4301 */     User user = (User)context.getSessionValue("user");
/* 4302 */     if (user != null && user.getPreferences() != null) {
/*      */ 
/*      */       
/* 4305 */       String defaultStr = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 4311 */       User userSrch = (User)context.getSessionValue("ResetSearchVariables");
/* 4312 */       if (userSrch != null) {
/* 4313 */         resetSearchVariables(user, userSrch, context);
/*      */       }
/* 4315 */       if (!user.SS_searchInitiated) {
/*      */ 
/*      */         
/* 4318 */         if (user.getPreferences().getSelectionStatus() > 0) {
/* 4319 */           ((FormCheckBox)form.getElement("ShowAllSearch")).setChecked(true);
/* 4320 */           user.SS_showAllSearch = "true";
/*      */         } 
/*      */ 
/*      */         
/* 4324 */         if (user.getPreferences().getSelectionReleasingFamily() > 0) {
/* 4325 */           defaultStr = String.valueOf(user.getPreferences().getSelectionReleasingFamily());
/* 4326 */           ((FormDropDownMenu)form.getElement("FamilySearch")).setValue(defaultStr);
/* 4327 */           user.SS_familySearch = defaultStr;
/*      */         } 
/*      */         
/* 4330 */         if (user.getPreferences().getSelectionEnvironment() > 0) {
/* 4331 */           defaultStr = String.valueOf(user.getPreferences().getSelectionEnvironment());
/* 4332 */           ((FormDropDownMenu)form.getElement("EnvironmentSearch")).setValue(defaultStr);
/* 4333 */           user.SS_environmentSearch = defaultStr;
/*      */         } 
/*      */         
/* 4336 */         if (user.getPreferences().getSelectionLabelContact() > 0) {
/* 4337 */           defaultStr = String.valueOf(user.getPreferences().getSelectionLabelContact());
/* 4338 */           ((FormDropDownMenu)form.getElement("ContactSearch")).setValue(defaultStr);
/* 4339 */           user.SS_contactSearch = defaultStr;
/*      */         } 
/*      */         
/* 4342 */         if (user.getPreferences().getSelectionProductType() > -1) {
/* 4343 */           if (user.getPreferences().getSelectionProductType() == 0)
/* 4344 */             defaultStr = "physical"; 
/* 4345 */           if (user.getPreferences().getSelectionProductType() == 1)
/* 4346 */             defaultStr = "digital"; 
/* 4347 */           if (user.getPreferences().getSelectionProductType() == 2)
/* 4348 */             defaultStr = "both"; 
/* 4349 */           ((FormRadioButtonGroup)form.getElement("ProdType")).setValue(defaultStr);
/* 4350 */           user.SS_productTypeSearch = defaultStr;
/*      */         }
/*      */       
/*      */       }
/*      */       else {
/*      */         
/* 4356 */         if (user.SS_showAllSearch.equals("true")) {
/* 4357 */           ((FormCheckBox)form.getElement("ShowAllSearch")).setChecked(true);
/*      */         }
/*      */         
/* 4360 */         if (!user.SS_familySearch.equals("")) {
/* 4361 */           ((FormDropDownMenu)form.getElement("FamilySearch")).setValue(user.SS_familySearch);
/*      */         }
/*      */         
/* 4364 */         if (!user.SS_environmentSearch.equals("")) {
/* 4365 */           ((FormDropDownMenu)form.getElement("EnvironmentSearch")).setValue(user.SS_environmentSearch);
/*      */         }
/*      */         
/* 4368 */         if (!user.SS_contactSearch.equals("")) {
/* 4369 */           ((FormDropDownMenu)form.getElement("ContactSearch")).setValue(user.SS_contactSearch);
/*      */         }
/*      */         
/* 4372 */         if (!user.SS_productTypeSearch.equals("")) {
/* 4373 */           ((FormRadioButtonGroup)form.getElement("ProdType")).setValue(user.SS_productTypeSearch);
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 4378 */       user.SS_searchInitiated = true;
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
/*      */   public String getJavaScriptCorporateArray(Context context) {
/* 4393 */     StringBuffer result = new StringBuffer(100);
/* 4394 */     String str = "";
/* 4395 */     String value = new String();
/*      */     
/* 4397 */     User user = (User)context.getSessionValue("user");
/*      */     
/* 4399 */     Vector vUserCompanies = (Vector)MilestoneHelper.getUserCompanies(context).clone();
/*      */     
/* 4401 */     Vector vUserEnvironments = filterSelectionEnvironments(vUserCompanies);
/*      */ 
/*      */     
/* 4404 */     result.append("\n");
/* 4405 */     result.append("var a = new Array();\n");
/* 4406 */     result.append("var b = new Array();\n");
/* 4407 */     result.append("var c = new Array();\n");
/* 4408 */     int arrayIndex = 0;
/*      */ 
/*      */     
/* 4411 */     result.append("a[0] = new Array( 0, '-- [nothing selected] --');\n");
/*      */ 
/*      */     
/* 4414 */     HashMap corpHashMap = MilestoneHelper.buildActiveCorporateStructureHashMap();
/*      */ 
/*      */ 
/*      */     
/* 4418 */     for (int i = 0; i < vUserEnvironments.size(); i++) {
/*      */       
/* 4420 */       Environment ue = (Environment)vUserEnvironments.elementAt(i);
/* 4421 */       if (ue != null) {
/*      */         
/* 4423 */         result.append("a[");
/* 4424 */         result.append(ue.getStructureID());
/* 4425 */         result.append("] = new Array(");
/*      */         
/* 4427 */         boolean foundFirst = false;
/* 4428 */         Vector tmpArray = new Vector();
/*      */         
/* 4430 */         Vector companies = Cache.getInstance().getCompanies();
/* 4431 */         for (int j = 0; j < companies.size(); j++) {
/*      */           
/* 4433 */           Company node = (Company)companies.elementAt(j);
/*      */           
/* 4435 */           if (node.getParentID() == ue.getStructureID() && !corpHashMap.containsKey(new Integer(node.getStructureID()))) {
/*      */             
/* 4437 */             if (foundFirst)
/* 4438 */               result.append(','); 
/* 4439 */             result.append(' ');
/* 4440 */             result.append(node.getStructureID());
/* 4441 */             result.append(", '");
/* 4442 */             result.append(MilestoneHelper.urlEncode(node.getName()));
/* 4443 */             result.append('\'');
/* 4444 */             foundFirst = true;
/* 4445 */             tmpArray.addElement(node);
/*      */           } 
/*      */         } 
/*      */         
/* 4449 */         if (foundFirst) {
/*      */           
/* 4451 */           result.append(");\n");
/*      */         }
/*      */         else {
/*      */           
/* 4455 */           result.append(" 0, '[none available]');\n");
/*      */         } 
/*      */         
/* 4458 */         Vector tmpDivisionArray = new Vector();
/*      */         
/* 4460 */         for (int j = 0; j < tmpArray.size(); j++) {
/*      */           
/* 4462 */           Company node1 = (Company)tmpArray.elementAt(j);
/* 4463 */           result.append("b[");
/* 4464 */           result.append(node1.getStructureID());
/* 4465 */           result.append("] = new Array(");
/*      */           
/* 4467 */           Vector divisions = Cache.getInstance().getDivisions();
/*      */           
/* 4469 */           boolean foundSecond = false;
/* 4470 */           for (int k = 0; k < divisions.size(); k++) {
/*      */             
/* 4472 */             Division node2 = (Division)divisions.elementAt(k);
/*      */             
/* 4474 */             if (node2.getParentID() == node1.getStructureID() && !corpHashMap.containsKey(new Integer(node2.getStructureID()))) {
/*      */               
/* 4476 */               if (foundSecond)
/* 4477 */                 result.append(','); 
/* 4478 */               result.append(' ');
/* 4479 */               result.append(node2.getStructureID());
/* 4480 */               result.append(", '");
/*      */               
/* 4482 */               result.append(MilestoneHelper.urlEncode(node2.getName()));
/* 4483 */               result.append('\'');
/* 4484 */               foundSecond = true;
/* 4485 */               tmpDivisionArray.add(node2);
/*      */             } 
/*      */           } 
/*      */           
/* 4489 */           if (foundSecond) {
/*      */             
/* 4491 */             result.append(");\n");
/*      */           }
/*      */           else {
/*      */             
/* 4495 */             result.append(" 0, '[none available]');\n");
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/* 4500 */         for (int j = 0; j < tmpDivisionArray.size(); j++) {
/*      */           
/* 4502 */           Division node1 = (Division)tmpDivisionArray.elementAt(j);
/* 4503 */           result.append("c[");
/* 4504 */           result.append(node1.getStructureID());
/* 4505 */           result.append("] = new Array(");
/*      */           
/* 4507 */           Vector labels = Cache.getInstance().getLabels();
/*      */           
/* 4509 */           boolean foundSecond = false;
/* 4510 */           for (int k = 0; k < labels.size(); k++) {
/*      */             
/* 4512 */             Label node2 = (Label)labels.elementAt(k);
/*      */ 
/*      */ 
/*      */             
/* 4516 */             if (node2.getParentID() == node1.getStructureID() && !corpHashMap.containsKey(new Integer(node2.getStructureID()))) {
/*      */               
/* 4518 */               if (foundSecond)
/* 4519 */                 result.append(','); 
/* 4520 */               result.append(' ');
/* 4521 */               result.append(node2.getStructureID());
/* 4522 */               result.append(", '");
/*      */               
/* 4524 */               result.append(MilestoneHelper.urlEncode(node2.getName()));
/* 4525 */               result.append('\'');
/* 4526 */               foundSecond = true;
/*      */             } 
/*      */           } 
/*      */           
/* 4530 */           if (foundSecond) {
/*      */             
/* 4532 */             result.append(");\n");
/*      */           }
/*      */           else {
/*      */             
/* 4536 */             result.append(" 0, '[none available]');\n");
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 4542 */     corpHashMap = null;
/* 4543 */     return result.toString();
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
/*      */   public static String getSearchJavaScriptCorporateArray(Context context) {
/* 4557 */     StringBuffer result = new StringBuffer(100);
/* 4558 */     String str = "";
/* 4559 */     String value = new String();
/* 4560 */     boolean foundFirstTemp = false;
/*      */     
/* 4562 */     User user = (User)context.getSessionValue("user");
/* 4563 */     Vector vUserCompanies = MilestoneHelper.getUserCompanies(context);
/* 4564 */     Vector vUserEnvironments = filterSelectionEnvironments(vUserCompanies);
/*      */ 
/*      */ 
/*      */     
/* 4568 */     HashMap corpHashMap = MilestoneHelper.buildActiveCorporateStructureHashMap();
/*      */ 
/*      */     
/* 4571 */     Hashtable labelsHash = new Hashtable();
/*      */ 
/*      */     
/* 4574 */     result.append("\n");
/* 4575 */     result.append("var aSearch = new Array();\n");
/* 4576 */     int arrayIndex = 0;
/*      */     
/* 4578 */     result.append("aSearch[0] = new Array(");
/* 4579 */     result.append(0);
/* 4580 */     result.append(", '");
/* 4581 */     result.append("All");
/* 4582 */     result.append("'");
/* 4583 */     foundFirstTemp = true;
/*      */     
/* 4585 */     for (int a = 0; a < vUserCompanies.size(); a++) {
/*      */       
/* 4587 */       Company ueTemp = (Company)vUserCompanies.elementAt(a);
/* 4588 */       if (ueTemp != null) {
/*      */ 
/*      */         
/* 4591 */         Vector labels = Cache.getInstance().getLabels();
/* 4592 */         for (int b = 0; b < labels.size(); b++) {
/*      */           
/* 4594 */           Label node = (Label)labels.elementAt(b);
/*      */           
/* 4596 */           if (node.getParent().getParentID() == ueTemp.getStructureID() && 
/* 4597 */             !corpHashMap.containsKey(new Integer(node.getStructureID()))) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 4610 */             String labelName = MilestoneHelper.urlEncode(node.getName());
/* 4611 */             if (!labelsHash.containsKey(labelName)) {
/*      */               
/* 4613 */               labelsHash.put(labelName, Integer.toString(node.getStructureID()));
/*      */             }
/*      */             else {
/*      */               
/* 4617 */               String hashValue = (String)labelsHash.get(labelName);
/* 4618 */               hashValue = String.valueOf(hashValue) + "," + Integer.toString(node.getStructureID());
/* 4619 */               labelsHash.put(labelName, hashValue);
/*      */             } 
/*      */             
/* 4622 */             foundFirstTemp = true;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 4629 */     if (!foundFirstTemp) {
/* 4630 */       result.append("'[none available]');\n");
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 4635 */       boolean firstPass = false;
/*      */       
/* 4637 */       String[] labelKeys = new String[labelsHash.size()];
/*      */ 
/*      */       
/* 4640 */       int x = 0;
/* 4641 */       for (Enumeration e = labelsHash.keys(); e.hasMoreElements(); x++) {
/*      */         
/* 4643 */         String hashKey = (String)e.nextElement();
/* 4644 */         labelKeys[x] = hashKey;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 4650 */       for (int h = 0; h < labelKeys.length; h++) {
/*      */         
/* 4652 */         String hashValue = (String)labelsHash.get(labelKeys[h]);
/*      */ 
/*      */         
/* 4655 */         result.append(',');
/* 4656 */         result.append(' ');
/* 4657 */         result.append("'" + hashValue + "'");
/* 4658 */         result.append(", '");
/* 4659 */         result.append(labelKeys[h]);
/* 4660 */         result.append('\'');
/*      */         
/* 4662 */         firstPass = true;
/*      */       } 
/* 4664 */       result.append(");\n");
/*      */     } 
/*      */ 
/*      */     
/* 4668 */     for (int i = 0; i < vUserCompanies.size(); i++) {
/*      */ 
/*      */       
/* 4671 */       Company ue = (Company)vUserCompanies.elementAt(i);
/* 4672 */       if (ue != null) {
/*      */ 
/*      */         
/* 4675 */         result.append("aSearch[");
/* 4676 */         result.append(ue.getStructureID());
/* 4677 */         result.append("] = new Array(");
/*      */         
/* 4679 */         boolean foundFirst = false;
/*      */         
/* 4681 */         result.append(0);
/* 4682 */         result.append(", '");
/* 4683 */         result.append("All");
/* 4684 */         result.append("'");
/* 4685 */         foundFirst = true;
/*      */         
/* 4687 */         Vector tmpArray = new Vector();
/*      */         
/* 4689 */         labelsHash.clear();
/*      */         
/* 4691 */         Vector labels = Cache.getInstance().getLabels();
/* 4692 */         for (int j = 0; j < labels.size(); j++) {
/*      */           
/* 4694 */           Label node = (Label)labels.elementAt(j);
/*      */           
/* 4696 */           if (node.getParent().getParentID() == ue.getStructureID() && 
/* 4697 */             !corpHashMap.containsKey(new Integer(node.getStructureID()))) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 4710 */             String labelName = MilestoneHelper.urlEncode(node.getName());
/* 4711 */             if (!labelsHash.containsKey(labelName)) {
/*      */ 
/*      */               
/* 4714 */               labelsHash.put(labelName, Integer.toString(node.getStructureID()));
/*      */             
/*      */             }
/*      */             else {
/*      */               
/* 4719 */               String hashValue = (String)labelsHash.get(labelName);
/* 4720 */               hashValue = String.valueOf(hashValue) + "," + Integer.toString(node.getStructureID());
/* 4721 */               labelsHash.put(labelName, hashValue);
/*      */             } 
/*      */             
/* 4724 */             foundFirst = true;
/* 4725 */             tmpArray.addElement(node);
/*      */           } 
/*      */         } 
/*      */         
/* 4729 */         if (foundFirst) {
/*      */ 
/*      */ 
/*      */           
/* 4733 */           boolean firstPass = false;
/*      */           
/* 4735 */           String[] labelKeys = new String[labelsHash.size()];
/*      */ 
/*      */           
/* 4738 */           int x = 0;
/* 4739 */           for (Enumeration e = labelsHash.keys(); e.hasMoreElements(); x++) {
/*      */             
/* 4741 */             String hashKey = (String)e.nextElement();
/* 4742 */             labelKeys[x] = hashKey;
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 4748 */           for (int h = 0; h < labelKeys.length; h++) {
/*      */             
/* 4750 */             String hashValue = (String)labelsHash.get(labelKeys[h]);
/*      */ 
/*      */             
/* 4753 */             result.append(',');
/*      */             
/* 4755 */             result.append(' ');
/* 4756 */             result.append("'" + hashValue + "'");
/* 4757 */             result.append(", '");
/* 4758 */             result.append(labelKeys[h]);
/* 4759 */             result.append('\'');
/*      */             
/* 4761 */             firstPass = true;
/*      */           } 
/*      */           
/* 4764 */           result.append(");\n");
/*      */         
/*      */         }
/*      */         else {
/*      */           
/* 4769 */           result.append(" 0, '[none available]');\n");
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 4774 */     return result.toString();
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
/*      */   private Context addMfgInfo(Context context, Selection selection) {
/* 4787 */     String explodedTotal = Integer.toString(selection.getPoQuantity() * selection.getNumberOfUnits());
/* 4788 */     context.putDelivery("explodedtotal", explodedTotal);
/* 4789 */     context.putDelivery("upc", selection.getUpc());
/* 4790 */     context.putDelivery("label", selection.getLabel().getName());
/* 4791 */     context.putDelivery("status", selection.getSelectionStatus().getName());
/* 4792 */     String typeConfig = String.valueOf(selection.getProductCategory().getName()) + "/" + selection.getReleaseType().getName() + " / " + selection.getSelectionConfig().getSelectionConfigurationName() + " / " + selection.getSelectionSubConfig().getSelectionSubConfigurationName();
/* 4793 */     context.putDelivery("typeConfig", typeConfig);
/*      */ 
/*      */     
/* 4796 */     String lastMfgUpdatedDateText = "";
/* 4797 */     if (selection.getLastMfgUpdateDate() != null) {
/* 4798 */       lastMfgUpdatedDateText = MilestoneHelper.getCustomFormatedDate(selection.getLastMfgUpdateDate(), "M/d/yyyy hh:mm:ss a 'ET'");
/*      */     }
/* 4800 */     context.putDelivery("lastUpdateDate", lastMfgUpdatedDateText);
/*      */     
/* 4802 */     String lastMfgUpdateUser = "";
/* 4803 */     if (selection.getLastMfgUpdatingUser() != null) {
/* 4804 */       lastMfgUpdateUser = selection.getLastMfgUpdatingUser().getName();
/*      */     }
/* 4806 */     context.putDelivery("lastUpdateUser", lastMfgUpdateUser);
/*      */     
/* 4808 */     return context;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getFilteredSellCodes(String selected) {
/* 4818 */     sellCodeText = "";
/* 4819 */     Vector vSellCodeList = Cache.getSellCodes();
/*      */     
/* 4821 */     for (int j = 0; j < vSellCodeList.size(); j++) {
/*      */       
/* 4823 */       String pc = (String)vSellCodeList.elementAt(j);
/*      */       
/* 4825 */       if (pc.substring(0, selected.length()).equalsIgnoreCase(selected)) {
/* 4826 */         sellCodeText = String.valueOf(sellCodeText) + pc + ",";
/*      */       }
/*      */     } 
/*      */     
/* 4830 */     return "," + sellCodeText.substring(0, sellCodeText.length() - 1);
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
/*      */   public String getSellCodesString() {
/* 4844 */     sellCodeText = "";
/* 4845 */     Vector vSellCodeList = Cache.getSellCodes();
/*      */     
/* 4847 */     for (int i = 0; i < vSellCodeList.size(); i++)
/*      */     {
/* 4849 */       sellCodeText = String.valueOf(sellCodeText) + (String)vSellCodeList.get(i) + ",";
/*      */     }
/*      */ 
/*      */     
/* 4853 */     return "," + sellCodeText.substring(0, sellCodeText.length() - 1);
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
/*      */   public String getSellCodesStringDPC() {
/* 4867 */     String sellCodeText = "";
/* 4868 */     Vector vSellCodeList = Cache.getSellCodesDPC();
/*      */     
/* 4870 */     for (int i = 0; i < vSellCodeList.size(); i++)
/*      */     {
/* 4872 */       sellCodeText = String.valueOf(sellCodeText) + (String)vSellCodeList.get(i) + ",";
/*      */     }
/*      */ 
/*      */     
/* 4876 */     if (sellCodeText.length() > 1) {
/* 4877 */       sellCodeText = "," + sellCodeText.substring(0, sellCodeText.length() - 1);
/*      */     }
/* 4879 */     return sellCodeText;
/*      */   }
/*      */   
/*      */   private String FormatForLegacy(String strIn) {
/* 4883 */     String strOut = "";
/* 4884 */     char[] arr = strIn.toCharArray();
/*      */     
/* 4886 */     for (int i = 0; i < arr.length; i++) {
/* 4887 */       if (arr[i] != '-' && arr[i] != ' ' && arr[i] != '/') {
/* 4888 */         strOut = String.valueOf(strOut) + arr[i];
/*      */       }
/*      */     } 
/* 4891 */     return strOut;
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
/*      */   private String applyBusinessRules(Form form, Context context, Selection selection) {
/* 4903 */     String RetValue = "";
/* 4904 */     strError = "";
/*      */     
/* 4906 */     String titleId = form.getStringValue("titleId");
/* 4907 */     String selectionNo = form.getStringValue("selectionNo");
/* 4908 */     String upc = form.getStringValue("upc");
/* 4909 */     String prefix = "";
/* 4910 */     String strDate = "";
/*      */     
/* 4912 */     String status = (selection.getSelectionStatus() != null && selection.getSelectionStatus().getName() != null) ? 
/* 4913 */       selection.getSelectionStatus().getName() : "";
/*      */     
/* 4915 */     if (!form.getStringValue("prefix").equals("-1")) {
/* 4916 */       prefix = form.getStringValue("prefix");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 4921 */     if (context.getParameter("generateSelection") != null && 
/* 4922 */       context.getParameter("generateSelection").equalsIgnoreCase("LPNG"))
/*      */     
/*      */     { 
/*      */       
/* 4926 */       selection.setPrefixID(null);
/*      */ 
/*      */       
/* 4929 */       String strProjectID = FormatForLegacy(selection.getProjectID());
/* 4930 */       String strUPC = FormatForLegacy(selection.getUpc());
/* 4931 */       String strSoundScan = FormatForLegacy(selection.getSoundScanGrp());
/*      */ 
/*      */       
/* 4934 */       if (selection.getConfigCode().startsWith("S")) {
/* 4935 */         titleId = "";
/*      */       }
/*      */ 
/*      */       
/* 4939 */       String strTitleID = "";
/* 4940 */       for (int j = 0; j < titleId.length(); j++) {
/* 4941 */         if (titleId.charAt(j) != '-') {
/* 4942 */           strTitleID = String.valueOf(strTitleID) + titleId.charAt(j);
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 4948 */       if (selection.getStreetDateString().equals("") && 
/* 4949 */         selection.getSelectionStatus().getAbbreviation().equalsIgnoreCase("TBS")) {
/*      */ 
/*      */         
/* 4952 */         strDate = "12/31/39";
/*      */       } else {
/* 4954 */         strDate = selection.getStreetDateString().trim();
/*      */       } 
/*      */       
/* 4957 */       PnrCommunication pnr = PnrCommunication.getInstance();
/*      */       
/* 4959 */       String userIdStr = ((User)context.getSession().getAttribute("user")).getLogin();
/* 4960 */       if (userIdStr == null || userIdStr.equals(""))
/* 4961 */         userIdStr = "Mileston"; 
/* 4962 */       String strReply = PnrCommunication.GetPNR(userIdStr, 
/* 4963 */           selection.getOperCompany().trim(), 
/* 4964 */           selection.getSuperLabel().trim(), 
/* 4965 */           selection.getConfigCode().trim(), 
/* 4966 */           strTitleID, 
/* 4967 */           selection.getTitle().trim(), 
/* 4968 */           selection.getArtistFirstName().trim(), 
/* 4969 */           selection.getArtistLastName().trim(), 
/* 4970 */           strDate, 
/* 4971 */           selection.getSubLabel().trim(), 
/* 4972 */           strProjectID.trim(), 
/* 4973 */           strUPC.trim(), 
/* 4974 */           strSoundScan.trim());
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 4979 */       if (strReply.indexOf("BROKER ERROR:") != -1) {
/* 4980 */         strError = strReply.trim();
/*      */       }
/*      */       else {
/*      */         
/* 4984 */         strError = strReply.substring(312, strReply.length());
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 4989 */       if (strError.trim().equals("")) {
/*      */ 
/*      */         
/* 4992 */         if (titleId.trim().equals("")) {
/* 4993 */           titleId = strReply.substring(23, 33);
/*      */ 
/*      */           
/* 4996 */           titleId = String.valueOf(titleId.substring(0, titleId.length() - 2)) + "-" + titleId.substring(titleId.length() - 2, titleId.length());
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 5001 */         selectionNo = strReply.substring(33, 43);
/*      */       } else {
/*      */         
/* 5004 */         return strError.replace('\'', ' ');
/*      */       } 
/*      */       
/* 5007 */       selectionNo = String.valueOf(selectionNo.substring(0, selectionNo.length() - 2)) + "-" + selectionNo.substring(selectionNo.length() - 2, selectionNo.length());
/*      */        }
/*      */     
/* 5010 */     else if (context.getParameter("generateSelection") != null && 
/* 5011 */       context.getParameter("generateSelection").equalsIgnoreCase("TPNG"))
/*      */     
/*      */     { 
/*      */       
/* 5015 */       selectionNo = SelectionManager.getInstance().getSequencedSelectionNumber();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 5020 */       if (selection.getReleaseType().getAbbreviation().equalsIgnoreCase("CO") && !selection.getConfigCode().startsWith("S") && (
/* 5021 */         titleId.equals("") || titleId.startsWith("TEMP"))) {
/* 5022 */         titleId = selectionNo;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 5029 */       if (selection.getReleaseType().getAbbreviation().equalsIgnoreCase("CO") && selection.getConfigCode().startsWith("S"))
/*      */       {
/* 5031 */         titleId = selectionNo;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 5037 */       if (selection.getReleaseType().getAbbreviation().equalsIgnoreCase("PR"))
/*      */       {
/* 5039 */         titleId = String.valueOf(prefix) + selectionNo;
/*      */ 
/*      */       
/*      */       }
/*      */        }
/*      */     
/*      */     else
/*      */     
/*      */     { 
/* 5048 */       if (!status.equalsIgnoreCase("Cancelled") && SelectionManager.getInstance().isSelectionIDDuplicate(prefix, selectionNo, selection.getSelectionID(), selection.getIsDigital()))
/*      */       {
/* 5050 */         return "The Local Product No entered already exist in our database.  Please enter a new one and resubmit.";
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 5104 */       if (!selection.getReleaseType().getAbbreviation().equalsIgnoreCase("PR"))
/*      */       
/* 5106 */       { if (selection.getReleaseType().getAbbreviation().equalsIgnoreCase("CO") && selection.getConfigCode().startsWith("S"))
/*      */         {
/* 5108 */           titleId = String.valueOf(prefix) + selectionNo;
/*      */         }
/*      */ 
/*      */         
/* 5112 */         selection.setSelectionNo(selectionNo);
/* 5113 */         selection.setTitleID(titleId);
/*      */         
/* 5115 */         return RetValue; }  titleId = String.valueOf(prefix) + selectionNo; }  selection.setSelectionNo(selectionNo); selection.setTitleID(titleId); return RetValue;
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
/* 5137 */     String copyVisible = "false";
/* 5138 */     String saveVisible = "false";
/* 5139 */     String deleteVisible = "false";
/* 5140 */     String newVisible = "false";
/*      */     
/* 5142 */     if (level > 1) {
/*      */       
/* 5144 */       saveVisible = "true";
/* 5145 */       copyVisible = "true";
/* 5146 */       deleteVisible = "true";
/*      */       
/* 5148 */       if (selection.getSelectionID() > 0) {
/* 5149 */         newVisible = "true";
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 5154 */     if (command.indexOf("new") > -1 || command.indexOf("copy") > -1) {
/*      */       
/* 5156 */       saveVisible = "true";
/* 5157 */       copyVisible = "false";
/* 5158 */       deleteVisible = "false";
/* 5159 */       newVisible = "false";
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 5164 */     context.putDelivery("saveVisible", saveVisible);
/*      */     
/* 5166 */     context.putDelivery("copyVisible", copyVisible);
/*      */     
/* 5168 */     context.putDelivery("deleteVisible", deleteVisible);
/*      */     
/* 5170 */     context.putDelivery("newVisible", newVisible);
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
/*      */   public static int getSelectionPermissions(Selection selection, User user) {
/* 5183 */     int level = 0;
/*      */     
/* 5185 */     if (selection != null && selection.getSelectionID() > -1) {
/*      */ 
/*      */ 
/*      */       
/* 5189 */       Environment env = selection.getEnvironment();
/*      */       
/* 5191 */       CompanyAcl companyAcl = MilestoneHelper.getScreenPermissions(env, user);
/*      */       
/* 5193 */       if (companyAcl != null) {
/* 5194 */         level = companyAcl.getAccessSelection();
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 5199 */     return level;
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
/*      */   public static int getSelectionMfgPermissions(Selection selection, User user) {
/* 5214 */     int level = 0;
/*      */     
/* 5216 */     if (selection != null && selection.getSelectionID() > -1) {
/*      */ 
/*      */ 
/*      */       
/* 5220 */       Environment env = selection.getEnvironment();
/* 5221 */       CompanyAcl companyAcl = MilestoneHelper.getScreenPermissions(env, user);
/* 5222 */       if (companyAcl != null) {
/* 5223 */         level = companyAcl.getAccessManufacturing();
/*      */       }
/*      */     } 
/* 5226 */     return level;
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
/*      */   public static Vector filterSelectionCompanies(Vector companies) {
/* 5245 */     Vector result = new Vector();
/*      */     
/* 5247 */     if (companies != null && companies.size() > 0)
/*      */     {
/* 5249 */       for (int i = 0; i < companies.size(); i++) {
/*      */ 
/*      */         
/* 5252 */         Company company = (Company)companies.get(i);
/* 5253 */         String name = company.getName();
/*      */ 
/*      */         
/* 5256 */         if (!name.equalsIgnoreCase("UML") && 
/* 5257 */           !name.equalsIgnoreCase("Enterprise")) {
/* 5258 */           result.add(company);
/*      */         }
/*      */       } 
/*      */     }
/* 5262 */     return result;
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
/*      */   public static Vector filterSelectionCompaniesWithEditRigthts(Vector companies, User user) {
/* 5282 */     Vector result = new Vector();
/* 5283 */     Vector cmpAclList = user.getAcl().getCompanyAcl();
/* 5284 */     HashMap cmpEditRight = new HashMap();
/*      */ 
/*      */ 
/*      */     
/* 5288 */     if (cmpAclList != null) {
/* 5289 */       for (int n = 0; n < cmpAclList.size(); n++) {
/*      */         
/* 5291 */         CompanyAcl cmpAcl = (CompanyAcl)cmpAclList.get(n);
/* 5292 */         if (cmpAcl.getAccessSelection() == 2) {
/* 5293 */           cmpEditRight.put(new Integer(cmpAcl.getCompanyId()), new Integer(n));
/*      */         }
/*      */       } 
/*      */     }
/* 5297 */     if (companies != null && companies.size() > 0)
/*      */     {
/* 5299 */       for (int i = 0; i < companies.size(); i++) {
/*      */ 
/*      */         
/* 5302 */         Company company = (Company)companies.get(i);
/* 5303 */         String name = company.getName();
/*      */         
/* 5305 */         if (cmpAclList == null) {
/* 5306 */           if (!name.equalsIgnoreCase("UML") && !name.equalsIgnoreCase("Enterprise")) {
/* 5307 */             result.add(company);
/*      */           
/*      */           }
/*      */         
/*      */         }
/* 5312 */         else if (!name.equalsIgnoreCase("UML") && !name.equalsIgnoreCase("Enterprise") && 
/* 5313 */           cmpEditRight.containsKey(new Integer(company.getStructureID()))) {
/* 5314 */           result.add(company);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/* 5319 */     return result;
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
/*      */   public static Vector filterCSO(Vector csoVector) {
/* 5339 */     Vector result = new Vector();
/*      */     
/* 5341 */     if (csoVector != null && csoVector.size() > 0)
/*      */     {
/*      */       
/* 5344 */       for (int i = 0; i < csoVector.size(); i++) {
/*      */ 
/*      */         
/* 5347 */         CorporateStructureObject cso = (CorporateStructureObject)csoVector.get(i);
/*      */         
/* 5349 */         String abbrev = cso.getStructureAbbreviation();
/*      */         
/* 5351 */         if (!abbrev.equalsIgnoreCase("UML") && 
/* 5352 */           !abbrev.equalsIgnoreCase("ENT")) {
/* 5353 */           result.add(cso);
/*      */         }
/*      */       } 
/*      */     }
/* 5357 */     return result;
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
/*      */   private boolean goToBlank(Context context, Form form, User user) {
/* 5372 */     form = new Form(this.application, "selectionForm", this.application.getInfrastructure().getServletURL(), "POST");
/* 5373 */     form.addElement(new FormHidden("cmd", "selection-editor", true));
/* 5374 */     form.addElement(new FormHidden("OrderBy", "", true));
/*      */ 
/*      */     
/* 5377 */     if (context.getSessionValue("NOTEPAD_SELECTION_VISIBLE") != null) {
/* 5378 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_SELECTION_VISIBLE"));
/*      */     }
/* 5380 */     Vector companies = MilestoneHelper.getUserCompanies(context);
/*      */     
/* 5382 */     addSelectionSearchElements(context, null, form, companies, true);
/* 5383 */     context.putDelivery("Form", form);
/*      */     
/* 5385 */     return context.includeJSP("blank-selection-editor.jsp");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean multSelectionEditor(Dispatcher dispatcher, Context context, String command) {
/* 5395 */     Form multSelectionForm = new Form(this.application, "multSelectionForm", 
/* 5396 */         this.application.getInfrastructure().getServletURL(), "POST");
/* 5397 */     Vector multSelections = null;
/*      */     
/* 5399 */     User user = (User)context.getSession().getAttribute("user");
/* 5400 */     Selection selection = (Selection)context.getSessionValue("Selection");
/*      */     
/* 5402 */     int secureLevel = getSelectionPermissions(selection, user);
/* 5403 */     setButtonVisibilities(selection, user, context, secureLevel, command);
/*      */ 
/*      */     
/* 5406 */     if (selection != null && selection.getMultSelections() != null) {
/*      */       
/* 5408 */       multSelections = selection.getMultSelections();
/*      */       
/* 5410 */       for (int j = 0; j < multSelections.size(); j++) {
/*      */         
/* 5412 */         MultSelection multSel = (MultSelection)multSelections.get(j);
/*      */ 
/*      */         
/* 5415 */         FormTextField selectionNo = new FormTextField("selectionNo" + j, multSel.getSelectionNo(), false, 15, 20);
/* 5416 */         FormTextField upc = new FormTextField("upc" + j, multSel.getUpc(), false, 18, 20);
/* 5417 */         FormTextField description = new FormTextField("description" + j, multSel.getDescription(), false, 25, 100);
/*      */ 
/*      */         
/* 5420 */         upc.addFormEvent("onKeyPress", "return checkNumbersOnly(window.event.keyCode);");
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 5425 */         multSelectionForm.addElement(selectionNo);
/* 5426 */         multSelectionForm.addElement(upc);
/* 5427 */         multSelectionForm.addElement(description);
/*      */       } 
/*      */     } 
/*      */     
/* 5431 */     multSelectionForm.addElement(new FormHidden("cmd", command, true));
/*      */     
/* 5433 */     context.putDelivery("Selection", selection);
/* 5434 */     context.putDelivery("Form", multSelectionForm);
/*      */     
/* 5436 */     return context.includeJSP("multSelection-editor.jsp");
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean multSelectionEditorAdd(Dispatcher dispatcher, Context context, String command) {
/* 5441 */     Form multSelectionForm = new Form(this.application, "multSelectionForm", 
/* 5442 */         this.application.getInfrastructure().getServletURL(), "POST");
/* 5443 */     User user = (User)context.getSession().getAttribute("user");
/*      */     
/* 5445 */     Selection selection = (Selection)context.getSessionValue("Selection");
/* 5446 */     Vector multSelections = null;
/*      */     
/* 5448 */     multSelections = selection.getMultSelections();
/*      */     
/* 5450 */     if (multSelections == null) {
/*      */       
/* 5452 */       multSelections = new Vector();
/* 5453 */       MultSelection temp = new MultSelection();
/* 5454 */       temp.setRelease_id(selection.getSelectionID());
/* 5455 */       multSelections.add(temp);
/* 5456 */       selection.setMultSelections(multSelections);
/*      */     }
/*      */     else {
/*      */       
/* 5460 */       MultSelection temp = new MultSelection();
/* 5461 */       temp.setRelease_id(selection.getSelectionID());
/* 5462 */       multSelections.add(temp);
/* 5463 */       selection.setMultSelections(multSelections);
/*      */     } 
/*      */     
/* 5466 */     if (selection != null && selection.getMultSelections() != null)
/*      */     {
/* 5468 */       for (int j = 0; j < multSelections.size(); j++) {
/*      */ 
/*      */         
/* 5471 */         MultSelection multSel = (MultSelection)multSelections.get(j);
/*      */         
/* 5473 */         FormTextField selectionNo = new FormTextField("selectionNo" + j, multSel.getSelectionNo(), false, 15, 20);
/* 5474 */         FormTextField upc = new FormTextField("upc" + j, multSel.getUpc(), false, 18, 20);
/* 5475 */         FormTextField description = new FormTextField("description" + j, multSel.getDescription(), false, 25, 100);
/*      */ 
/*      */         
/* 5478 */         upc.addFormEvent("onKeyPress", "return checkNumbersOnly(window.event.keyCode);");
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 5483 */         multSelectionForm.addElement(selectionNo);
/* 5484 */         multSelectionForm.addElement(upc);
/* 5485 */         multSelectionForm.addElement(description);
/*      */       } 
/*      */     }
/*      */     
/* 5489 */     multSelectionForm.setValues(context);
/* 5490 */     multSelectionForm.addElement(new FormHidden("cmd", command, true));
/* 5491 */     context.putSessionValue("Selection", selection);
/* 5492 */     context.putDelivery("Form", multSelectionForm);
/*      */     
/* 5494 */     return context.includeJSP("multSelection-editor.jsp");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean multSelectionEditorCancel(Dispatcher dispatcher, Context context, String command) {
/* 5500 */     Form multSelectionForm = new Form(this.application, "multSelectionForm", 
/* 5501 */         this.application.getInfrastructure().getServletURL(), "POST");
/* 5502 */     User user = (User)context.getSession().getAttribute("user");
/*      */     
/* 5504 */     Selection sessionSelection = (Selection)context.getSessionValue("Selection");
/*      */     
/* 5506 */     Selection selection = null;
/* 5507 */     if (sessionSelection.getSelectionID() < 0) {
/* 5508 */       selection = sessionSelection;
/*      */     } else {
/* 5510 */       selection = SelectionManager.getInstance().getSelectionHeader(sessionSelection.getSelectionID());
/*      */     } 
/* 5512 */     Vector multSelections = null;
/*      */ 
/*      */     
/* 5515 */     if (selection != null) {
/*      */       
/* 5517 */       multSelections = selection.getMultSelections();
/*      */       
/* 5519 */       if (selection != null && selection.getMultSelections() != null) {
/*      */ 
/*      */         
/* 5522 */         Vector newMultSelections = new Vector();
/*      */         
/* 5524 */         for (int j = 0; j < multSelections.size(); j++) {
/*      */           
/* 5526 */           MultSelection multSel = (MultSelection)multSelections.get(j);
/*      */ 
/*      */ 
/*      */           
/* 5530 */           if (sessionSelection.getSelectionID() >= 0 || 
/* 5531 */             multSel.getSelectionNo() != null || multSel.getUpc() != null || 
/* 5532 */             multSel.getDescription() != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 5539 */             FormTextField selectionNo = new FormTextField("selectionNo" + j, multSel.getSelectionNo(), false, 15, 20);
/* 5540 */             FormTextField upc = new FormTextField("upc" + j, multSel.getUpc(), false, 18, 20);
/* 5541 */             FormTextField description = new FormTextField("description" + j, multSel.getDescription(), false, 25, 100);
/*      */ 
/*      */             
/* 5544 */             upc.addFormEvent("onKeyPress", "return checkNumbersOnly(window.event.keyCode);");
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 5549 */             multSelectionForm.addElement(selectionNo);
/* 5550 */             multSelectionForm.addElement(upc);
/* 5551 */             multSelectionForm.addElement(description);
/*      */             
/* 5553 */             newMultSelections.add(multSel);
/*      */           } 
/* 5555 */         }  selection.setMultSelections(newMultSelections);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 5560 */     multSelectionForm.addElement(new FormHidden("cmd", command, true));
/* 5561 */     context.putSessionValue("Selection", selection);
/* 5562 */     context.putDelivery("Form", multSelectionForm);
/*      */ 
/*      */     
/* 5565 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean multSelectionEditorDelete(Dispatcher dispatcher, Context context, String command) {
/* 5571 */     Form multSelectionForm = new Form(this.application, "multSelectionForm", 
/* 5572 */         this.application.getInfrastructure().getServletURL(), "POST");
/* 5573 */     Form newMultSelectionForm = new Form(this.application, "multSelectionForm", 
/* 5574 */         this.application.getInfrastructure().getServletURL(), "POST");
/* 5575 */     User user = (User)context.getSession().getAttribute("user");
/*      */     
/* 5577 */     Selection selection = (Selection)context.getSessionValue("Selection");
/* 5578 */     Vector multSelections = null;
/* 5579 */     int multSelectionsPK = -1;
/*      */     
/* 5581 */     if (context.getRequestValue("multSelectionsPK") != null) {
/* 5582 */       multSelectionsPK = Integer.parseInt(context.getRequestValue("multSelectionsPK"));
/*      */     }
/* 5584 */     multSelections = selection.getMultSelections();
/*      */     
/* 5586 */     if (selection != null && selection.getMultSelections() != null) {
/*      */ 
/*      */ 
/*      */       
/* 5590 */       for (int j = 0; j < multSelections.size(); j++) {
/*      */         
/* 5592 */         MultSelection multSel = (MultSelection)multSelections.get(j);
/*      */         
/* 5594 */         FormTextField selectionNo = new FormTextField("selectionNo" + j, "", false, 15, 20);
/* 5595 */         FormTextField upc = new FormTextField("upc" + j, "", false, 18, 20);
/* 5596 */         FormTextField description = new FormTextField("description" + j, "", false, 25, 100);
/*      */ 
/*      */         
/* 5599 */         upc.addFormEvent("onKeyPress", "return checkNumbersOnly(window.event.keyCode);");
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 5604 */         multSelectionForm.addElement(selectionNo);
/* 5605 */         multSelectionForm.addElement(upc);
/* 5606 */         multSelectionForm.addElement(description);
/*      */       } 
/* 5608 */       multSelectionForm.setValues(context);
/*      */       
/* 5610 */       Vector newMultSelections = new Vector();
/*      */       
/* 5612 */       for (int j = 0; j < multSelections.size(); j++) {
/*      */         
/* 5614 */         MultSelection multSel = (MultSelection)multSelections.get(j);
/*      */ 
/*      */         
/* 5617 */         multSel.setSelectionNo(multSelectionForm.getStringValue("selectionNo" + j));
/* 5618 */         multSel.setUpc(multSelectionForm.getStringValue("upc" + j));
/* 5619 */         multSel.setDescription(multSelectionForm.getStringValue("description" + j));
/* 5620 */         newMultSelections.add(multSel);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 5625 */       if (multSelectionsPK > -1) {
/* 5626 */         newMultSelections.remove(multSelectionsPK);
/*      */       }
/* 5628 */       selection.setMultSelections(newMultSelections);
/*      */ 
/*      */       
/* 5631 */       for (int j = 0; j < newMultSelections.size(); j++) {
/*      */         
/* 5633 */         MultSelection multSel = (MultSelection)newMultSelections.get(j);
/*      */         
/* 5635 */         FormTextField selectionNo = new FormTextField("selectionNo" + j, multSel.getSelectionNo(), false, 15, 20);
/* 5636 */         FormTextField upc = new FormTextField("upc" + j, multSel.getUpc(), false, 18, 20);
/* 5637 */         FormTextField description = new FormTextField("description" + j, multSel.getDescription(), false, 25, 100);
/*      */ 
/*      */         
/* 5640 */         upc.addFormEvent("onKeyPress", "return checkNumbersOnly(window.event.keyCode);");
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 5645 */         newMultSelectionForm.addElement(selectionNo);
/* 5646 */         newMultSelectionForm.addElement(upc);
/* 5647 */         newMultSelectionForm.addElement(description);
/*      */       } 
/*      */     } 
/*      */     
/* 5651 */     newMultSelectionForm.addElement(new FormHidden("cmd", command, true));
/* 5652 */     context.putSessionValue("Selection", selection);
/* 5653 */     context.putDelivery("Form", newMultSelectionForm);
/*      */     
/* 5655 */     return context.includeJSP("multSelection-editor.jsp");
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean multSelectionEditorSave(Dispatcher dispatcher, Context context, String command) {
/* 5660 */     Form multSelectionForm = new Form(this.application, "multSelectionForm", 
/* 5661 */         this.application.getInfrastructure().getServletURL(), "POST");
/* 5662 */     User user = (User)context.getSession().getAttribute("user");
/*      */     
/* 5664 */     Selection selection = (Selection)context.getSessionValue("Selection");
/* 5665 */     Vector multSelections = null;
/*      */     
/* 5667 */     multSelections = selection.getMultSelections();
/*      */ 
/*      */ 
/*      */     
/* 5671 */     if (selection != null && selection.getMultSelections() != null) {
/*      */       
/* 5673 */       for (int j = 0; j < multSelections.size(); j++) {
/*      */         
/* 5675 */         MultSelection multSel = (MultSelection)multSelections.get(j);
/*      */         
/* 5677 */         FormTextField selectionNo = new FormTextField("selectionNo" + j, "", false, 15, 20);
/* 5678 */         FormTextField upc = new FormTextField("upc" + j, "", false, 18, 20);
/* 5679 */         FormTextField description = new FormTextField("description" + j, "", false, 25, 100);
/*      */ 
/*      */         
/* 5682 */         upc.addFormEvent("onKeyPress", "return checkNumbersOnly(window.event.keyCode);");
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 5687 */         multSelectionForm.addElement(selectionNo);
/* 5688 */         multSelectionForm.addElement(upc);
/* 5689 */         multSelectionForm.addElement(description);
/*      */       } 
/*      */       
/* 5692 */       multSelectionForm.setValues(context);
/*      */       
/* 5694 */       Vector newMultSelections = new Vector();
/*      */       
/* 5696 */       for (int j = 0; j < multSelections.size(); j++) {
/*      */         
/* 5698 */         MultSelection multSel = (MultSelection)multSelections.get(j);
/*      */         
/* 5700 */         multSel.setSelectionNo(multSelectionForm.getStringValue("selectionNo" + j));
/*      */         
/* 5702 */         multSel.setUpc(MilestoneHelper_2.reformat_UPC_SSG_SGC_forSave(multSelectionForm.getStringValue("upc" + j), "UPC", false, true));
/* 5703 */         multSel.setDescription(multSelectionForm.getStringValue("description" + j));
/* 5704 */         newMultSelections.add(multSel);
/*      */       } 
/*      */       
/* 5707 */       selection.setMultSelections(newMultSelections);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 5712 */       if (selection.getSelectionID() > 0) {
/* 5713 */         SelectionManager.getInstance().saveSelection(selection, user);
/*      */       }
/*      */     } 
/*      */     
/* 5717 */     multSelectionForm.addElement(new FormHidden("cmd", command, true));
/*      */     
/* 5719 */     context.putSessionValue("Selection", selection);
/* 5720 */     context.putDelivery("Form", multSelectionForm);
/*      */     
/* 5722 */     return true;
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
/*      */   private boolean multOtherContactEditor(Dispatcher dispatcher, Context context, String command) {
/* 5734 */     Form multOtherContactForm = new Form(this.application, "multOtherContactForm", 
/* 5735 */         this.application.getInfrastructure().getServletURL(), "POST");
/* 5736 */     Vector multOtherContacts = null;
/*      */     
/* 5738 */     User user = (User)context.getSession().getAttribute("user");
/* 5739 */     Selection selection = (Selection)context.getSessionValue("Selection");
/*      */     
/* 5741 */     int secureLevel = getSelectionPermissions(selection, user);
/* 5742 */     setButtonVisibilities(selection, user, context, secureLevel, command);
/*      */ 
/*      */     
/* 5745 */     if (selection != null && selection.getMultOtherContacts() != null) {
/*      */       
/* 5747 */       multOtherContacts = selection.getMultOtherContacts();
/*      */       
/* 5749 */       for (int j = 0; j < multOtherContacts.size(); j++) {
/*      */         
/* 5751 */         MultOtherContact multOth = (MultOtherContact)multOtherContacts.get(j);
/*      */ 
/*      */         
/* 5754 */         FormTextField name = new FormTextField("name" + j, multOth.getName(), false, 25, 150);
/* 5755 */         FormTextField description = new FormTextField("description" + j, multOth.getDescription(), false, 25, 150);
/*      */         
/* 5757 */         multOtherContactForm.addElement(name);
/* 5758 */         multOtherContactForm.addElement(description);
/*      */       } 
/*      */     } 
/*      */     
/* 5762 */     multOtherContactForm.addElement(new FormHidden("cmd", command, true));
/*      */     
/* 5764 */     context.putDelivery("Selection", selection);
/* 5765 */     context.putDelivery("Form", multOtherContactForm);
/*      */     
/* 5767 */     return context.includeJSP("multOtherContact-editor.jsp");
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean multOtherContactEditorAdd(Dispatcher dispatcher, Context context, String command) {
/* 5772 */     Form multOtherContactForm = new Form(this.application, "multOtherContactForm", 
/* 5773 */         this.application.getInfrastructure().getServletURL(), "POST");
/* 5774 */     User user = (User)context.getSession().getAttribute("user");
/*      */     
/* 5776 */     Selection selection = (Selection)context.getSessionValue("Selection");
/* 5777 */     Vector multOtherContacts = null;
/*      */     
/* 5779 */     multOtherContacts = selection.getMultOtherContacts();
/*      */     
/* 5781 */     if (multOtherContacts == null) {
/*      */       
/* 5783 */       multOtherContacts = new Vector();
/* 5784 */       MultOtherContact temp = new MultOtherContact();
/* 5785 */       temp.setRelease_id(selection.getSelectionID());
/* 5786 */       multOtherContacts.add(temp);
/* 5787 */       selection.setMultOtherContacts(multOtherContacts);
/*      */     }
/*      */     else {
/*      */       
/* 5791 */       MultOtherContact temp = new MultOtherContact();
/* 5792 */       temp.setRelease_id(selection.getSelectionID());
/* 5793 */       multOtherContacts.add(temp);
/* 5794 */       selection.setMultOtherContacts(multOtherContacts);
/*      */     } 
/*      */     
/* 5797 */     if (selection != null && selection.getMultOtherContacts() != null)
/*      */     {
/* 5799 */       for (int j = 0; j < multOtherContacts.size(); j++) {
/*      */ 
/*      */         
/* 5802 */         MultOtherContact multOth = (MultOtherContact)multOtherContacts.get(j);
/*      */         
/* 5804 */         FormTextField name = new FormTextField("name" + j, multOth.getName(), false, 25, 150);
/* 5805 */         FormTextField description = new FormTextField("description" + j, multOth.getDescription(), false, 25, 150);
/*      */         
/* 5807 */         multOtherContactForm.addElement(name);
/* 5808 */         multOtherContactForm.addElement(description);
/*      */       } 
/*      */     }
/*      */     
/* 5812 */     multOtherContactForm.setValues(context);
/* 5813 */     multOtherContactForm.addElement(new FormHidden("cmd", command, true));
/* 5814 */     context.putSessionValue("Selection", selection);
/* 5815 */     context.putDelivery("Form", multOtherContactForm);
/*      */     
/* 5817 */     return context.includeJSP("multOtherContact-editor.jsp");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean multOtherContactEditorCancel(Dispatcher dispatcher, Context context, String command) {
/* 5823 */     Form multOtherContactForm = new Form(this.application, "multOtherContactForm", 
/* 5824 */         this.application.getInfrastructure().getServletURL(), "POST");
/* 5825 */     User user = (User)context.getSession().getAttribute("user");
/*      */     
/* 5827 */     Selection sessionSelection = (Selection)context.getSessionValue("Selection");
/*      */     
/* 5829 */     Selection selection = null;
/* 5830 */     if (sessionSelection.getSelectionID() < 0) {
/* 5831 */       selection = sessionSelection;
/*      */     } else {
/* 5833 */       selection = SelectionManager.getInstance().getSelectionHeader(sessionSelection.getSelectionID());
/*      */     } 
/* 5835 */     Vector multOtherContacts = null;
/*      */ 
/*      */     
/* 5838 */     if (selection != null) {
/*      */       
/* 5840 */       multOtherContacts = selection.getMultOtherContacts();
/*      */       
/* 5842 */       if (selection != null && selection.getMultOtherContacts() != null) {
/*      */ 
/*      */         
/* 5845 */         Vector newMultOtherContacts = new Vector();
/*      */         
/* 5847 */         for (int j = 0; j < multOtherContacts.size(); j++) {
/*      */           
/* 5849 */           MultOtherContact multOth = (MultOtherContact)multOtherContacts.get(j);
/*      */ 
/*      */ 
/*      */           
/* 5853 */           if (sessionSelection.getSelectionID() >= 0 || 
/* 5854 */             multOth.getName() != null || multOth.getDescription() != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 5861 */             FormTextField name = new FormTextField("name" + j, multOth.getName(), false, 25, 150);
/* 5862 */             FormTextField description = new FormTextField("description" + j, multOth.getDescription(), false, 25, 150);
/*      */             
/* 5864 */             multOtherContactForm.addElement(name);
/* 5865 */             multOtherContactForm.addElement(description);
/*      */             
/* 5867 */             newMultOtherContacts.add(multOth);
/*      */           } 
/* 5869 */         }  selection.setMultOtherContacts(newMultOtherContacts);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 5874 */     multOtherContactForm.addElement(new FormHidden("cmd", command, true));
/* 5875 */     context.putSessionValue("Selection", selection);
/* 5876 */     context.putDelivery("Form", multOtherContactForm);
/*      */ 
/*      */     
/* 5879 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean multOtherContactEditorDelete(Dispatcher dispatcher, Context context, String command) {
/* 5885 */     Form multOtherContactForm = new Form(this.application, "multOtherContactForm", 
/* 5886 */         this.application.getInfrastructure().getServletURL(), "POST");
/* 5887 */     Form newMultOtherContactForm = new Form(this.application, "multOtherContactForm", 
/* 5888 */         this.application.getInfrastructure().getServletURL(), "POST");
/* 5889 */     User user = (User)context.getSession().getAttribute("user");
/*      */     
/* 5891 */     Selection selection = (Selection)context.getSessionValue("Selection");
/* 5892 */     Vector multOtherContacts = null;
/* 5893 */     int multOtherContactsPK = -1;
/*      */     
/* 5895 */     if (context.getRequestValue("multOtherContactsPK") != null) {
/* 5896 */       multOtherContactsPK = Integer.parseInt(context.getRequestValue("multOtherContactsPK"));
/*      */     }
/* 5898 */     multOtherContacts = selection.getMultOtherContacts();
/*      */     
/* 5900 */     if (selection != null && selection.getMultOtherContacts() != null) {
/*      */ 
/*      */ 
/*      */       
/* 5904 */       for (int j = 0; j < multOtherContacts.size(); j++) {
/*      */         
/* 5906 */         MultOtherContact multOth = (MultOtherContact)multOtherContacts.get(j);
/*      */         
/* 5908 */         FormTextField name = new FormTextField("name" + j, "", false, 25, 150);
/* 5909 */         FormTextField description = new FormTextField("description" + j, "", false, 25, 150);
/*      */         
/* 5911 */         multOtherContactForm.addElement(name);
/* 5912 */         multOtherContactForm.addElement(description);
/*      */       } 
/* 5914 */       multOtherContactForm.setValues(context);
/*      */       
/* 5916 */       Vector newMultOtherContacts = new Vector();
/*      */       
/* 5918 */       for (int j = 0; j < multOtherContacts.size(); j++) {
/*      */         
/* 5920 */         MultOtherContact multOth = (MultOtherContact)multOtherContacts.get(j);
/*      */ 
/*      */         
/* 5923 */         multOth.setName(multOtherContactForm.getStringValue("name" + j));
/* 5924 */         multOth.setDescription(multOtherContactForm.getStringValue("description" + j));
/* 5925 */         newMultOtherContacts.add(multOth);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 5930 */       if (multOtherContactsPK > -1) {
/* 5931 */         newMultOtherContacts.remove(multOtherContactsPK);
/*      */       }
/* 5933 */       selection.setMultOtherContacts(newMultOtherContacts);
/*      */ 
/*      */       
/* 5936 */       for (int j = 0; j < newMultOtherContacts.size(); j++) {
/*      */         
/* 5938 */         MultOtherContact multOth = (MultOtherContact)newMultOtherContacts.get(j);
/*      */         
/* 5940 */         FormTextField name = new FormTextField("name" + j, multOth.getName(), false, 25, 150);
/* 5941 */         FormTextField description = new FormTextField("description" + j, multOth.getDescription(), false, 25, 150);
/*      */         
/* 5943 */         newMultOtherContactForm.addElement(name);
/* 5944 */         newMultOtherContactForm.addElement(description);
/*      */       } 
/*      */     } 
/*      */     
/* 5948 */     newMultOtherContactForm.addElement(new FormHidden("cmd", command, true));
/* 5949 */     context.putSessionValue("Selection", selection);
/* 5950 */     context.putDelivery("Form", newMultOtherContactForm);
/*      */     
/* 5952 */     return context.includeJSP("multOtherContact-editor.jsp");
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean multOtherContactEditorSave(Dispatcher dispatcher, Context context, String command) {
/* 5957 */     Form multOtherContactForm = new Form(this.application, "multOtherContactForm", 
/* 5958 */         this.application.getInfrastructure().getServletURL(), "POST");
/* 5959 */     User user = (User)context.getSession().getAttribute("user");
/*      */     
/* 5961 */     Selection selection = (Selection)context.getSessionValue("Selection");
/* 5962 */     Vector multOtherContacts = null;
/*      */     
/* 5964 */     multOtherContacts = selection.getMultOtherContacts();
/*      */ 
/*      */ 
/*      */     
/* 5968 */     if (selection != null && selection.getMultOtherContacts() != null) {
/*      */       
/* 5970 */       for (int j = 0; j < multOtherContacts.size(); j++) {
/*      */         
/* 5972 */         MultOtherContact multOth = (MultOtherContact)multOtherContacts.get(j);
/*      */         
/* 5974 */         FormTextField name = new FormTextField("name" + j, "", false, 25, 150);
/* 5975 */         FormTextField description = new FormTextField("description" + j, "", false, 25, 150);
/*      */         
/* 5977 */         multOtherContactForm.addElement(name);
/* 5978 */         multOtherContactForm.addElement(description);
/*      */       } 
/*      */       
/* 5981 */       multOtherContactForm.setValues(context);
/*      */       
/* 5983 */       Vector newMultOtherContacts = new Vector();
/*      */       
/* 5985 */       for (int j = 0; j < multOtherContacts.size(); j++) {
/*      */         
/* 5987 */         MultOtherContact multOth = (MultOtherContact)multOtherContacts.get(j);
/*      */         
/* 5989 */         multOth.setName(multOtherContactForm.getStringValue("name" + j));
/* 5990 */         multOth.setDescription(multOtherContactForm.getStringValue("description" + j));
/* 5991 */         newMultOtherContacts.add(multOth);
/*      */       } 
/*      */       
/* 5994 */       selection.setMultOtherContacts(newMultOtherContacts);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 5999 */       if (selection.getSelectionID() > 0) {
/* 6000 */         SelectionManager.getInstance().saveSelection(selection, user);
/*      */       }
/*      */     } 
/*      */     
/* 6004 */     multOtherContactForm.addElement(new FormHidden("cmd", command, true));
/*      */     
/* 6006 */     context.putSessionValue("Selection", selection);
/* 6007 */     context.putDelivery("Form", multOtherContactForm);
/*      */     
/* 6009 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean sortGroup(Dispatcher dispatcher, Context context, String command) {
/* 6019 */     int sort = Integer.parseInt(context.getParameter("OrderBy"));
/*      */     
/* 6021 */     String alphaGroupChr = context.getParameter("alphaGroupChr");
/*      */ 
/*      */     
/* 6024 */     Notepad notepad = (Notepad)context.getSessionValue(MilestoneConstants.NOTEPAD_SESSION_NAMES[0]);
/*      */ 
/*      */     
/* 6027 */     User user = (User)context.getSession().getAttribute("user");
/*      */     
/* 6029 */     if (notepad.getAllContents() != null && notepad.getAllContents().size() < notepad.getTotalRecords()) {
/*      */       
/* 6031 */       notepad.setMaxRecords(0);
/* 6032 */       notepad.setAllContents(null);
/* 6033 */       notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 0);
/*      */     } 
/*      */     
/* 6036 */     SelectionManager.getInstance().getAlphaGroupPosition(context, notepad, alphaGroupChr, sort);
/*      */     
/* 6038 */     notepad.goToSelectedPage();
/*      */     
/* 6040 */     if (command.equals("selection-group")) {
/*      */       
/* 6042 */       dispatcher.redispatch(context, "selection-editor");
/*      */     }
/*      */     else {
/*      */       
/* 6046 */       dispatcher.redispatch(context, "selection-manufacturing-editor");
/*      */     } 
/*      */     
/* 6049 */     return true;
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
/*      */   public static Vector filterSelectionEnvironments(Vector companies) {
/* 6068 */     Vector result = new Vector();
/*      */     
/* 6070 */     if (companies != null && companies.size() > 0)
/*      */     {
/* 6072 */       for (int i = 0; i < companies.size(); i++) {
/*      */ 
/*      */         
/* 6075 */         Company company = (Company)companies.get(i);
/* 6076 */         Environment environment = company.getParentEnvironment();
/* 6077 */         String name = environment.getName();
/*      */         
/* 6079 */         if (!name.equalsIgnoreCase("UML") && 
/* 6080 */           !name.equalsIgnoreCase("Enterprise")) {
/*      */           
/* 6082 */           boolean addFlag = true;
/* 6083 */           for (int r = 0; r < result.size(); r++) {
/*      */             
/* 6085 */             if (((Environment)result.get(r)).getName().equalsIgnoreCase(name)) {
/* 6086 */               addFlag = false;
/*      */             }
/*      */           } 
/* 6089 */           if (addFlag) {
/* 6090 */             result.add(environment);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     }
/* 6095 */     return result;
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
/*      */   public static Vector filterSelectionEnvironmentsWithEditRigthts(Vector environments, User user) {
/* 6114 */     Vector result = new Vector();
/* 6115 */     Vector envAclList = user.getAcl().getEnvironmentAcl();
/* 6116 */     HashMap envEditRight = new HashMap();
/*      */ 
/*      */ 
/*      */     
/* 6120 */     if (envAclList != null) {
/* 6121 */       for (int n = 0; n < envAclList.size(); n++) {
/*      */         
/* 6123 */         EnvironmentAcl envAcl = (EnvironmentAcl)envAclList.get(n);
/* 6124 */         if (envAcl.getAccessSelection() == 2) {
/* 6125 */           envEditRight.put(new Integer(envAcl.getEnvironmentId()), new Integer(n));
/*      */         }
/*      */       } 
/*      */     }
/* 6129 */     if (environments != null && environments.size() > 0)
/*      */     {
/* 6131 */       for (int i = 0; i < environments.size(); i++) {
/*      */ 
/*      */         
/* 6134 */         Environment environment = (Environment)environments.get(i);
/* 6135 */         String name = environment.getName();
/*      */         
/* 6137 */         if (envAclList == null) {
/* 6138 */           if (!name.equalsIgnoreCase("UML") && !name.equalsIgnoreCase("Enterprise")) {
/* 6139 */             result.add(environment);
/*      */           
/*      */           }
/*      */         
/*      */         }
/* 6144 */         else if (!name.equalsIgnoreCase("UML") && !name.equalsIgnoreCase("Enterprise") && 
/* 6145 */           envEditRight.containsKey(new Integer(environment.getStructureID()))) {
/* 6146 */           result.add(environment);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/* 6151 */     return result;
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
/*      */   private boolean sendPfmBom(Dispatcher dispatcher, Context context, String command) {
/* 6163 */     EmailDistribution.removeSessionValues(context);
/* 6164 */     context.removeSessionValue("originalComment");
/*      */     
/* 6166 */     if (command.equals("selection-send-pfm") || command.equals("selection-send-pfmbom")) {
/*      */       
/* 6168 */       PfmHandler pfmHandler = new PfmHandler(this.application);
/* 6169 */       pfmHandler.editSave(dispatcher, context, "selectionSave");
/*      */       
/* 6171 */       EmailDistribution.removeSessionValues(context);
/*      */     } 
/*      */     
/* 6174 */     if (command.equals("selection-send-bom") || command.equals("selection-send-pfmbom")) {
/*      */       
/* 6176 */       BomHandler bomHandler = new BomHandler(this.application);
/* 6177 */       bomHandler.save(dispatcher, context, "selectionSave");
/*      */       
/* 6179 */       EmailDistribution.removeSessionValues(context);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 6184 */     if (context.getSessionValue("sendToSchedule") != null && ((String)context.getSessionValue("sendToSchedule")).equals("true")) {
/*      */       
/* 6186 */       if (context.getSessionValue("recalc-date") != null && ((String)context.getSessionValue("recalc-date")).equals("true")) {
/* 6187 */         context.putDelivery("recalc-date", "true");
/*      */       }
/* 6189 */       context.removeSessionValue("sendToSchedule");
/* 6190 */       context.removeSessionValue("recalc-date");
/*      */       
/* 6192 */       dispatcher.redispatch(context, "schedule-editor");
/* 6193 */       return true;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6199 */     if (command.equals("selection-send-cancel")) {
/*      */       
/* 6201 */       PfmHandler pfmHandler = new PfmHandler(this.application);
/* 6202 */       pfmHandler.editSave(dispatcher, context, "selectionSave");
/*      */       
/* 6204 */       BomHandler bomHandler = new BomHandler(this.application);
/* 6205 */       bomHandler.save(dispatcher, context, "selectionSave");
/*      */ 
/*      */       
/* 6208 */       EmailDistribution.removeSessionValues(context);
/*      */     } 
/*      */     
/* 6211 */     dispatcher.redispatch(context, "selection-editor");
/*      */     
/* 6213 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Form buildDigitalForm(Context context, Selection selection, String command) {
/* 6222 */     Form selectionForm = new Form(this.application, "selectionForm", 
/* 6223 */         this.application.getInfrastructure().getServletURL(), "POST");
/* 6224 */     User user = (User)context.getSession().getAttribute("user");
/* 6225 */     int userId = user.getUserId();
/*      */     
/* 6227 */     int secureLevel = getSelectionPermissions(selection, user);
/* 6228 */     setButtonVisibilities(selection, user, context, secureLevel, command);
/*      */     
/* 6230 */     boolean newFlag = (selection.getSelectionID() < 0);
/*      */     
/* 6232 */     if (newFlag) {
/* 6233 */       context.putDelivery("new-or-copy", "true");
/*      */     } else {
/* 6235 */       context.putDelivery("new-or-copy", "false");
/*      */     } 
/*      */ 
/*      */     
/* 6239 */     selectionForm.addElement(new FormHidden("cmd", command, true));
/* 6240 */     selectionForm.addElement(new FormHidden("OrderBy", "", true));
/* 6241 */     selectionForm.addElement(new FormHidden("hidTitleId", "", true));
/* 6242 */     selectionForm.addElement(new FormHidden("isFocus", "", true));
/* 6243 */     selectionForm.addElement(new FormHidden("statusHidVal", "", true));
/* 6244 */     selectionForm.addElement(new FormHidden("generateSelection", "", true));
/*      */     
/* 6246 */     Vector companies = null;
/* 6247 */     companies = MilestoneHelper.getUserCompanies(context);
/*      */     
/* 6249 */     if (selection != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 6264 */       FormTextField artistFirstName = new FormTextField("artistFirstName", selection.getArtistFirstName(), false, 20, 50);
/* 6265 */       artistFirstName.setTabIndex(1);
/* 6266 */       artistFirstName.setClassName("ctrlMedium");
/* 6267 */       artistFirstName.addFormEvent("onChange", "javaScript:removeSpaces(this);");
/* 6268 */       selectionForm.addElement(artistFirstName);
/*      */ 
/*      */       
/* 6271 */       FormTextField artistLastName = new FormTextField("artistLastName", selection.getArtistLastName(), false, 20, 50);
/* 6272 */       artistLastName.setTabIndex(2);
/* 6273 */       artistLastName.setClassName("ctrlMedium");
/* 6274 */       artistLastName.addFormEvent("onChange", "javaScript:removeSpaces(this);");
/* 6275 */       selectionForm.addElement(artistLastName);
/*      */ 
/*      */       
/* 6278 */       FormTextField title = new FormTextField("title", selection.getTitle(), true, 73, 125);
/* 6279 */       title.setTabIndex(3);
/* 6280 */       title.setClassName("ctrlXLarge");
/* 6281 */       title.addFormEvent("onChange", "javaScript:removeSpaces(this);");
/* 6282 */       selectionForm.addElement(title);
/*      */ 
/*      */       
/* 6285 */       FormTextField sideATitle = new FormTextField("sideATitle", selection.getASide(), false, 20, 125);
/* 6286 */       sideATitle.setTabIndex(4);
/* 6287 */       sideATitle.setClassName("ctrlMedium");
/* 6288 */       selectionForm.addElement(sideATitle);
/*      */ 
/*      */       
/* 6291 */       FormTextField sideBTitle = new FormTextField("sideBTitle", selection.getBSide(), false, 20, 125);
/* 6292 */       sideBTitle.setTabIndex(5);
/* 6293 */       sideBTitle.setClassName("ctrlMedium");
/* 6294 */       selectionForm.addElement(sideBTitle);
/*      */ 
/*      */       
/* 6297 */       String[] values = new String[2];
/* 6298 */       values[0] = "true";
/* 6299 */       values[1] = "false";
/*      */       
/* 6301 */       String[] labels = new String[2];
/* 6302 */       labels[0] = "New Bundle";
/* 6303 */       labels[1] = "Exact Duplicate of Physical Product";
/*      */       
/* 6305 */       FormRadioButtonGroup newBundle = new FormRadioButtonGroup("newBundle", String.valueOf(selection.getNewBundleFlag()), values, labels, false);
/*      */       
/* 6307 */       newBundle.addFormEvent("onClick", "JavaScript:filterScheduleType(false)");
/* 6308 */       newBundle.setTabIndex(6);
/* 6309 */       selectionForm.addElement(newBundle);
/*      */ 
/*      */       
/* 6312 */       FormCheckBox priority = new FormCheckBox("priority", "", false, selection.getPriority());
/* 6313 */       priority.setTabIndex(9);
/* 6314 */       selectionForm.addElement(priority);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 6321 */       String streetDateText = "";
/* 6322 */       if (selection.getStreetDate() != null)
/* 6323 */         streetDateText = MilestoneHelper.getFormatedDate(selection.getStreetDate()); 
/* 6324 */       FormTextField streetDate = new FormTextField("streetDate", streetDateText, false, 10);
/* 6325 */       streetDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
/* 6326 */       streetDate.addFormEvent("onBlur", "JavaScript:validateDate(document.forms[0].streetDate.value,this)");
/* 6327 */       streetDate.setTabIndex(11);
/* 6328 */       streetDate.setClassName("ctrlShort");
/* 6329 */       selectionForm.addElement(streetDate);
/*      */       
/* 6331 */       FormTextField dayType = new FormTextField("dayType", MilestoneHelper.getDayType(selection.getCalendarGroup(), selection.getStreetDate()), false, 5);
/* 6332 */       selectionForm.addElement(dayType);
/*      */ 
/*      */       
/* 6335 */       String digitalRlsDateText = "";
/* 6336 */       if (selection.getDigitalRlsDate() != null)
/* 6337 */         digitalRlsDateText = MilestoneHelper.getFormatedDate(selection.getDigitalRlsDate()); 
/* 6338 */       FormTextField drDate = new FormTextField("digitalDate", digitalRlsDateText, false, 10);
/* 6339 */       drDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
/* 6340 */       drDate.addFormEvent("onBlur", "JavaScript:validateDate(document.forms[0].digitalDate.value,this)");
/* 6341 */       drDate.setTabIndex(10);
/* 6342 */       drDate.setClassName("ctrlShort");
/* 6343 */       selectionForm.addElement(drDate);
/*      */ 
/*      */       
/* 6346 */       String intDateText = "";
/* 6347 */       if (selection.getInternationalDate() != null)
/* 6348 */         intDateText = MilestoneHelper.getFormatedDate(selection.getInternationalDate()); 
/* 6349 */       FormDateField intDate = new FormDateField("internationalDate", intDateText, false, 10);
/* 6350 */       intDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
/* 6351 */       intDate.addFormEvent("onBlur", "JavaScript:validateDate(document.forms[0].internationalDate.value,this)");
/* 6352 */       intDate.setTabIndex(12);
/* 6353 */       intDate.setClassName("ctrlShort");
/* 6354 */       selectionForm.addElement(intDate);
/*      */ 
/*      */       
/* 6357 */       context.putDelivery("releaseWeek", MilestoneHelper.getReleaseWeekString(selection));
/*      */ 
/*      */       
/* 6360 */       FormDropDownMenu status = MilestoneHelper.getLookupDropDown("status", Cache.getSelectionStatusList(), SelectionManager.getLookupObjectValue(selection.getSelectionStatus()), true, false);
/* 6361 */       status.setTabIndex(13);
/* 6362 */       status.setClassName("ctrlSmall");
/* 6363 */       selectionForm.addElement(status);
/*      */ 
/*      */       
/* 6366 */       boolean boolHoldReason = true;
/* 6367 */       if (selection.getHoldReason().equalsIgnoreCase("")) {
/* 6368 */         boolHoldReason = false;
/*      */       }
/* 6370 */       FormCheckBox holdIndicator = new FormCheckBox("holdIndicator", "", false, boolHoldReason);
/* 6371 */       holdIndicator.setTabIndex(10);
/* 6372 */       selectionForm.addElement(holdIndicator);
/*      */ 
/*      */       
/* 6375 */       FormTextArea holdReason = new FormTextArea("holdReason", selection.getHoldReason(), false, 2, 44, "virtual");
/* 6376 */       holdReason.addFormEvent("onBlur", "Javascript:checkField(this)");
/* 6377 */       selectionForm.addElement(holdReason);
/*      */ 
/*      */       
/* 6380 */       FormCheckBox pdIndicator = new FormCheckBox("pdIndicator", "", false, selection.getPressAndDistribution());
/* 6381 */       pdIndicator.setTabIndex(8);
/* 6382 */       selectionForm.addElement(pdIndicator);
/*      */ 
/*      */       
/* 6385 */       FormCheckBox intlFlag = new FormCheckBox("intlFlag", "", false, selection.getInternationalFlag());
/* 6386 */       intlFlag.setTabIndex(12);
/* 6387 */       selectionForm.addElement(intlFlag);
/*      */ 
/*      */       
/* 6390 */       String impactDateText = "";
/* 6391 */       if (selection.getImpactDate() != null)
/* 6392 */         impactDateText = MilestoneHelper.getFormatedDate(selection.getImpactDate()); 
/* 6393 */       FormDateField impactDate = new FormDateField("impactdate", impactDateText, false, 13);
/* 6394 */       impactDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
/* 6395 */       impactDate.addFormEvent("onBlur", "JavaScript:validateDate(document.forms[0].impactdate.value,this)");
/* 6396 */       impactDate.setTabIndex(13);
/* 6397 */       impactDate.setClassName("ctrlShort");
/* 6398 */       selectionForm.addElement(impactDate);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 6403 */       Vector releaseFamilies = ReleasingFamily.getReleasingFamilies(userId, selection.getFamily().getStructureID(), context);
/* 6404 */       FormDropDownMenu releasingFamily = MilestoneHelper.getReleasingFamilyDropDown("releasingFamily", Integer.toString(selection.getReleaseFamilyId()), releaseFamilies, true, selection);
/* 6405 */       releasingFamily.setTabIndex(14);
/*      */       
/* 6407 */       releasingFamily.addFormEvent("onChange", "ReleasingFamilyChange(this)");
/* 6408 */       selectionForm.addElement(releasingFamily);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 6413 */       String evironmentId = "";
/* 6414 */       String environmentName = "";
/* 6415 */       Vector evironmentList = filterSelectionEnvironments(companies);
/*      */       
/* 6417 */       if (selection.getCompany() != null && selection.getCompany().getParentEnvironment() != null) {
/*      */         
/* 6419 */         evironmentId = Integer.toString(selection.getCompany().getParentEnvironment().getStructureID());
/* 6420 */         environmentName = selection.getCompany().getParentEnvironment().getName();
/*      */       } else {
/*      */         
/* 6423 */         evironmentId = "";
/*      */       } 
/* 6425 */       FormHidden evironment = new FormHidden("environment", evironmentId, false);
/* 6426 */       FormHidden evironmentLabel = new FormHidden("environment", evironmentId, false);
/* 6427 */       evironment.setTabIndex(14);
/* 6428 */       evironment.setDisplayName(environmentName);
/*      */ 
/*      */ 
/*      */       
/* 6432 */       selectionForm.addElement(evironment);
/*      */ 
/*      */       
/* 6435 */       String companyId = "";
/* 6436 */       String companyName = "";
/*      */ 
/*      */       
/* 6439 */       if (selection.getCompany() != null) {
/* 6440 */         companyId = Integer.toString(selection.getCompany().getStructureID());
/* 6441 */         companyName = selection.getCompany().getName();
/*      */       } 
/*      */       
/* 6444 */       FormHidden company = new FormHidden("company", companyId, false);
/* 6445 */       company.setTabIndex(15);
/* 6446 */       company.setDisplayName(companyName);
/*      */ 
/*      */ 
/*      */       
/* 6450 */       selectionForm.addElement(company);
/*      */ 
/*      */       
/* 6453 */       String divisionId = "";
/* 6454 */       String divisionName = "";
/* 6455 */       if (selection.getDivision() != null) {
/* 6456 */         divisionId = Integer.toString(selection.getDivision().getStructureID());
/* 6457 */         divisionName = selection.getDivision().getName();
/*      */       } else {
/*      */         
/* 6460 */         divisionId = "";
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 6470 */       FormHidden division = new FormHidden("division", divisionId, false);
/* 6471 */       division.setTabIndex(16);
/* 6472 */       division.setDisplayName(divisionName);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 6477 */       selectionForm.addElement(division);
/*      */ 
/*      */       
/* 6480 */       String labelId = "";
/* 6481 */       String labelName = "";
/* 6482 */       if (selection.getLabel() != null) {
/* 6483 */         labelId = Integer.toString(selection.getLabel().getStructureID());
/* 6484 */         labelName = selection.getLabel().getName();
/*      */       } else {
/* 6486 */         labelId = "";
/*      */       } 
/* 6488 */       FormHidden label = new FormHidden("label", labelId, false);
/*      */       
/* 6490 */       label.setTabIndex(17);
/* 6491 */       label.setDisplayName(labelName);
/*      */       
/* 6493 */       selectionForm.addElement(label);
/*      */ 
/*      */ 
/*      */       
/* 6497 */       if (selection.getOperCompany().equals("***")) {
/* 6498 */         FormHidden opercompany = new FormHidden("opercompany", "***", false);
/* 6499 */         opercompany.setTabIndex(18);
/* 6500 */         opercompany.setDisplayName("***");
/* 6501 */         selectionForm.addElement(opercompany);
/*      */       } else {
/* 6503 */         LookupObject oc = MilestoneHelper.getLookupObject(selection
/* 6504 */             .getOperCompany(), Cache.getOperatingCompanies());
/* 6505 */         String ocAbbr = "";
/* 6506 */         String ocName = "";
/*      */ 
/*      */ 
/*      */         
/* 6510 */         if (oc == null) {
/* 6511 */           ocAbbr = selection.getOperCompany();
/*      */         } else {
/* 6513 */           if (oc != null && oc.getAbbreviation() != null)
/* 6514 */             ocAbbr = oc.getAbbreviation(); 
/* 6515 */           if (oc != null && oc.getName() != null)
/* 6516 */             ocName = ":" + oc.getName(); 
/*      */         } 
/* 6518 */         FormHidden opercompany = new FormHidden("opercompany", ocAbbr, false);
/* 6519 */         opercompany.setTabIndex(18);
/* 6520 */         opercompany.setDisplayName(String.valueOf(ocAbbr) + ocName);
/*      */         
/* 6522 */         if (ocAbbr.equals("ZZ"))
/* 6523 */           opercompany.setDisplayName(ocAbbr); 
/* 6524 */         selectionForm.addElement(opercompany);
/*      */       } 
/*      */ 
/*      */       
/* 6528 */       FormHidden superlabel = new FormHidden("superlabel", selection.getSuperLabel(), false);
/* 6529 */       superlabel.setTabIndex(19);
/* 6530 */       superlabel.setClassName("ctrlShort");
/* 6531 */       superlabel.addFormEvent("onBlur", "javascript:this.value=this.value.toUpperCase();");
/* 6532 */       selectionForm.addElement(superlabel);
/*      */ 
/*      */       
/* 6535 */       FormHidden sublabel = new FormHidden("sublabel", selection.getSubLabel(), false);
/* 6536 */       sublabel.setTabIndex(20);
/* 6537 */       sublabel.setClassName("ctrlShort");
/* 6538 */       sublabel.addFormEvent("onBlur", "javascript:this.value=this.value.toUpperCase();");
/* 6539 */       selectionForm.addElement(sublabel);
/*      */ 
/*      */ 
/*      */       
/* 6543 */       FormTextField imprint = new FormTextField("imprint", selection.getImprint(), false, 50);
/* 6544 */       imprint.setTabIndex(21);
/* 6545 */       imprint.setClassName("ctrlMedium");
/* 6546 */       imprint.addFormEvent("onChange", "javaScript:removeSpaces(this);");
/* 6547 */       selectionForm.addElement(imprint);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 6552 */       FormDropDownMenu configcode = MilestoneHelper.getPfmLookupDropDown("configcode", MilestoneHelper.getConfigCodes(1), selection.getConfigCode(), false, true);
/* 6553 */       configcode.setTabIndex(21);
/*      */       
/* 6555 */       configcode.addFormEvent("onKeyPress", "scanKey(window.event.keyCode, 'document.all.configcode.options', getMaxLength(document.all.configcode.options))");
/*      */       
/* 6557 */       selectionForm.addElement(configcode);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 6564 */       FormHidden projectId = new FormHidden("projectId", String.valueOf(selection.getProjectID()), false);
/* 6565 */       projectId.setTabIndex(22);
/* 6566 */       projectId.setClassName("ctrlMedium");
/* 6567 */       projectId.setDisplayName(String.valueOf(selection.getProjectID()));
/* 6568 */       selectionForm.addElement(projectId);
/*      */ 
/*      */       
/* 6571 */       FormTextField upc = new FormTextField("UPC", selection.getUpc(), false, 17, 20);
/* 6572 */       upc.setTabIndex(23);
/* 6573 */       upc.setClassName("ctrlMedium");
/*      */ 
/*      */       
/* 6576 */       upc.addFormEvent("onKeyPress", "return checkNumbersOnly(window.event.keyCode);");
/*      */ 
/*      */ 
/*      */       
/* 6580 */       selectionForm.addElement(upc);
/*      */ 
/*      */       
/* 6583 */       FormTextField soundscan = new FormTextField("soundscan", selection.getSoundScanGrp(), false, 17, 20);
/* 6584 */       soundscan.setTabIndex(24);
/*      */ 
/*      */ 
/*      */       
/* 6588 */       soundscan.setClassName("ctrlMedium");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 6596 */       selectionForm.addElement(soundscan);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 6601 */       FormTextField gridNumber = new FormTextField("gridNumber", selection.getGridNumber(), false, 50);
/* 6602 */       gridNumber.setTabIndex(25);
/*      */       
/* 6604 */       gridNumber.setEnabled(true);
/* 6605 */       selectionForm.addElement(gridNumber);
/*      */ 
/*      */       
/* 6608 */       FormDropDownMenu prefix = MilestoneHelper.getPrefixCodeDropDown(userId, "prefix", Cache.getPrefixCodes(), SelectionManager.getLookupObjectValue(selection.getPrefixID()), true, context);
/* 6609 */       prefix.setTabIndex(26);
/* 6610 */       prefix.setClassName("ctrlShort");
/*      */       
/* 6612 */       selectionForm.addElement(prefix);
/*      */ 
/*      */       
/* 6615 */       FormTextField selectionNo = new FormTextField("selectionNo", String.valueOf(selection.getSelectionNo()), false, 20, 20);
/* 6616 */       selectionNo.setTabIndex(27);
/*      */       
/* 6618 */       selectionNo.setClassName("ctrlMedium");
/* 6619 */       selectionNo.addFormEvent("onChange", "javaScript:removeSpaces(this);");
/* 6620 */       selectionForm.addElement(selectionNo);
/*      */ 
/*      */       
/* 6623 */       FormHidden titleId = new FormHidden("titleId", String.valueOf(selection.getTitleID()), false);
/* 6624 */       titleId.setClassName("ctrlMedium");
/*      */       
/* 6626 */       titleId.setTabIndex(28);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 6632 */       selectionForm.addElement(titleId);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 6639 */       FormDropDownMenu productLine = MilestoneHelper.getLookupDropDown("productLine", Cache.getProductCategories(1), SelectionManager.getLookupObjectValue(selection.getProductCategory()), true, true);
/* 6640 */       productLine.setTabIndex(29);
/* 6641 */       productLine.setClassName("ctrlMedium");
/* 6642 */       selectionForm.addElement(productLine);
/*      */ 
/*      */       
/* 6645 */       FormDropDownMenu releaseType = MilestoneHelper.getLookupDropDown("releaseType", Cache.getReleaseTypes(), SelectionManager.getLookupObjectValue(selection.getReleaseType()), true, newFlag);
/* 6646 */       releaseType.setTabIndex(30);
/* 6647 */       releaseType.setClassName("ctrlMedium");
/* 6648 */       releaseType.addFormEvent("onChange", "releaseTypeChanged()");
/* 6649 */       selectionForm.addElement(releaseType);
/*      */ 
/*      */       
/* 6652 */       String configValue = "";
/* 6653 */       boolean configNewBundle = false;
/*      */       
/* 6655 */       if (selection.getSelectionConfig() != null) {
/*      */         
/* 6657 */         configNewBundle = selection.getNewBundleFlag();
/* 6658 */         configValue = selection.getSelectionConfig().getSelectionConfigurationAbbreviation();
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 6664 */       FormDropDownMenu configuration = null;
/* 6665 */       if (command.equalsIgnoreCase("selection-edit-copy-digital")) {
/*      */         
/* 6667 */         if (selection.getIsDigital()) {
/* 6668 */           configuration = MilestoneHelper.getSelectionConfigurationDropDown("configuration", configValue, true, 1);
/*      */         } else {
/* 6670 */           configuration = MilestoneHelper.getSelectionConfigurationDropDown("configuration", configValue, true, 1, configNewBundle);
/*      */         } 
/*      */       } else {
/*      */         
/* 6674 */         configuration = MilestoneHelper.getSelectionConfigurationDropDown("configuration", configValue, true, 1, configNewBundle);
/*      */       } 
/*      */       
/* 6677 */       configuration.setTabIndex(31);
/*      */       
/* 6679 */       configuration.addFormEvent("onChange", "buildSubConfigs(this.selectedIndex)");
/* 6680 */       selectionForm.addElement(configuration);
/*      */ 
/*      */ 
/*      */       
/* 6684 */       String subConfigValue = "";
/* 6685 */       if (selection.getSelectionSubConfig() != null) subConfigValue = selection.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation(); 
/* 6686 */       FormDropDownMenu subConfiguration = MilestoneHelper.getSelectionSubConfigurationDropDown("subConfiguration", selection.getSelectionConfig(), subConfigValue, true);
/* 6687 */       subConfiguration.addFormEvent("onChange", "isSubConfigParent(this.selectedIndex)");
/* 6688 */       subConfiguration.setTabIndex(32);
/*      */       
/* 6690 */       selectionForm.addElement(subConfiguration);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 6697 */       FormTextField test = new FormTextField("test", "", false, 8, 8);
/* 6698 */       test.setTabIndex(33);
/* 6699 */       test.setClassName("ctrlShort");
/*      */ 
/*      */ 
/*      */       
/* 6703 */       test.addFormEvent("onChange", "javaScript:clickSell(this,true);");
/* 6704 */       selectionForm.addElement(test);
/*      */ 
/*      */       
/* 6707 */       String sellCode = "";
/* 6708 */       if (selection.getSellCode() != null)
/* 6709 */         sellCode = selection.getSellCode(); 
/* 6710 */       FormDropDownMenu priceCode = new FormDropDownMenu("priceCode", sellCode, "-1" + getSellCodesStringDPC(), "&nbsp;" + getSellCodesStringDPC(), false);
/* 6711 */       priceCode.setTabIndex(34);
/*      */ 
/*      */ 
/*      */       
/* 6715 */       priceCode.setClassName("ctrlSmall");
/* 6716 */       selectionForm.addElement(priceCode);
/*      */ 
/*      */       
/* 6719 */       String numberOfUnits = "0";
/*      */       
/* 6721 */       if (selection.getNumberOfUnits() > 0) {
/* 6722 */         numberOfUnits = Integer.toString(selection.getNumberOfUnits());
/*      */       }
/* 6724 */       FormTextField numOfUnits = new FormTextField("numOfUnits", numberOfUnits, false, 10, 10);
/* 6725 */       numOfUnits.setTabIndex(35);
/* 6726 */       numOfUnits.setClassName("ctrlShort");
/* 6727 */       selectionForm.addElement(numOfUnits);
/*      */ 
/*      */ 
/*      */       
/* 6731 */       User labelUserContact = selection.getLabelContact();
/* 6732 */       Vector labelContacts = SelectionManager.getLabelContacts(selection);
/* 6733 */       FormDropDownMenu contactList = MilestoneHelper.getContactsDropDown(context, "contactlist", labelContacts, labelUserContact, true);
/* 6734 */       contactList.setTabIndex(36);
/* 6735 */       contactList.setClassName("ctrlMedium");
/* 6736 */       selectionForm.addElement(contactList);
/*      */ 
/*      */       
/* 6739 */       FormTextField contact = new FormTextField("contact", selection.getOtherContact(), false, 14, 30);
/* 6740 */       contact.setTabIndex(37);
/* 6741 */       contact.setClassName("ctrlMedium");
/* 6742 */       selectionForm.addElement(contact);
/*      */ 
/*      */       
/* 6745 */       FormCheckBox parentalIndicator = new FormCheckBox("parentalIndicator", "", false, selection.getParentalGuidance());
/* 6746 */       parentalIndicator.setTabIndex(38);
/* 6747 */       selectionForm.addElement(parentalIndicator);
/*      */ 
/*      */       
/* 6750 */       FormCheckBox specPkgIndicator = new FormCheckBox("specialPkgIndicator", "", false, selection.getSpecialPackaging());
/* 6751 */       specPkgIndicator.addFormEvent("onDblClick", "document.forms[0].PackagingHelper.value=document.forms[0].package.value;togglePackaging();");
/* 6752 */       specPkgIndicator.setTabIndex(39);
/* 6753 */       selectionForm.addElement(specPkgIndicator);
/*      */ 
/*      */       
/* 6756 */       FormTextField pkg = new FormTextField("package", selection.getSelectionPackaging(), false, 13, 100);
/* 6757 */       pkg.setTabIndex(40);
/* 6758 */       pkg.setClassName("ctrlMedium");
/* 6759 */       pkg.addFormEvent("onDblClick", "document.forms[0].PackagingHelper.value=document.forms[0].package.value;togglePackaging();");
/* 6760 */       selectionForm.addElement(pkg);
/*      */ 
/*      */       
/* 6763 */       FormDropDownMenu genre = MilestoneHelper.getPfmLookupDropDown("genre", Cache.getMusicTypes(), SelectionManager.getLookupObjectValue(selection.getGenre()), false, true);
/* 6764 */       genre.setTabIndex(41);
/* 6765 */       genre.setId("music_type");
/*      */       
/* 6767 */       selectionForm.addElement(genre);
/*      */ 
/*      */       
/* 6770 */       FormTextField territory = new FormTextField("territory", selection.getSelectionTerritory(), false, 13, 255);
/* 6771 */       territory.setTabIndex(42);
/* 6772 */       territory.setClassName("ctrlMedium");
/* 6773 */       territory.addFormEvent("onDblClick", "document.forms[0].TerritoryHelper.value=document.forms[0].territory.value;toggleTerritory();");
/* 6774 */       selectionForm.addElement(territory);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 6781 */       FormTextArea specialInstructions = new FormTextArea("specialInstructions", selection.getSpecialInstructions(), false, 3, 80, "virtual");
/* 6782 */       specialInstructions.setTabIndex(43);
/*      */       
/* 6784 */       selectionForm.addElement(specialInstructions);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 6792 */       String lastStreetDateText = "";
/* 6793 */       if (selection.getLastStreetUpdateDate() != null)
/* 6794 */         lastStreetDateText = MilestoneHelper.getFormatedDate(selection.getLastStreetUpdateDate()); 
/* 6795 */       FormTextField lastStreetUpdatedDate = new FormTextField("laststreetupdateddate", lastStreetDateText, false, 13);
/* 6796 */       selectionForm.addElement(lastStreetUpdatedDate);
/*      */ 
/*      */ 
/*      */       
/* 6800 */       String lastUpdatedDateText = "";
/* 6801 */       if (selection.getLastUpdateDate() != null)
/* 6802 */         lastUpdatedDateText = MilestoneHelper.getCustomFormatedDate(selection.getLastUpdateDate(), "M/d/yyyy hh:mm:ss a 'ET'"); 
/* 6803 */       FormTextField lastUpdatedDate = new FormTextField("lastupdateddate", lastUpdatedDateText, false, 50);
/* 6804 */       selectionForm.addElement(lastUpdatedDate);
/*      */ 
/*      */       
/* 6807 */       String originDateText = "";
/* 6808 */       if (selection.getOriginDate() != null)
/* 6809 */         originDateText = MilestoneHelper.getFormatedDate(selection.getOriginDate()); 
/* 6810 */       FormTextField originDate = new FormTextField("origindate", originDateText, false, 13);
/* 6811 */       selectionForm.addElement(originDate);
/*      */ 
/*      */       
/* 6814 */       String archieDateText = "";
/* 6815 */       if (selection.getArchieDate() != null)
/* 6816 */         archieDateText = MilestoneHelper.getCustomFormatedDate(selection.getArchieDate(), "M/d/yyyy hh:mm:ss a 'ET'"); 
/* 6817 */       FormTextField archieDate = new FormTextField("archieDate", archieDateText, false, 13);
/* 6818 */       selectionForm.addElement(archieDate);
/*      */ 
/*      */       
/* 6821 */       String autoCloseDateText = "";
/* 6822 */       if (selection.getAutoCloseDate() != null)
/* 6823 */         autoCloseDateText = MilestoneHelper.getCustomFormatedDate(selection.getAutoCloseDate(), "M/d/yyyy hh:mm:ss a 'ET'"); 
/* 6824 */       FormTextField autoCloseDate = new FormTextField("autoCloseDate", autoCloseDateText, false, 40);
/* 6825 */       selectionForm.addElement(autoCloseDate);
/*      */ 
/*      */       
/* 6828 */       String lastLegacyUpdateDateText = "";
/* 6829 */       if (selection.getLastLegacyUpdateDate() != null)
/* 6830 */         lastLegacyUpdateDateText = MilestoneHelper.getCustomFormatedDate(selection.getLastLegacyUpdateDate(), "M/d/yyyy hh:mm:ss a 'ET'"); 
/* 6831 */       FormTextField lastLegacyUpdateDate = new FormTextField("lastLegacyUpdateDate", lastLegacyUpdateDateText, false, 40);
/* 6832 */       selectionForm.addElement(lastLegacyUpdateDate);
/*      */ 
/*      */       
/* 6835 */       FormTextArea packagingHelper = new FormTextArea("PackagingHelper", selection.getSelectionPackaging(), false, 2, 44, "virtual");
/* 6836 */       selectionForm.addElement(packagingHelper);
/*      */ 
/*      */       
/* 6839 */       FormTextArea territoryHelper = new FormTextArea("TerritoryHelper", selection.getSelectionTerritory(), false, 2, 44, "virtual");
/* 6840 */       selectionForm.addElement(territoryHelper);
/*      */ 
/*      */       
/* 6843 */       FormTextArea comments = new FormTextArea("comments", selection.getSelectionComments(), false, 6, 44, "virtual");
/* 6844 */       comments.addFormEvent("onBlur", "Javascript:checkField(this)");
/* 6845 */       selectionForm.addElement(comments);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6854 */     addSelectionSearchElements(context, selection, selectionForm, companies, true);
/*      */ 
/*      */     
/* 6857 */     if (context.getSessionValue("NOTEPAD_SELECTION_VISIBLE") != null) {
/* 6858 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_SELECTION_VISIBLE"));
/*      */     }
/* 6860 */     boolean isParent = false;
/*      */     
/* 6862 */     if (selection.getSelectionSubConfig() != null) {
/* 6863 */       isParent = selection.getSelectionSubConfig().isParent();
/*      */     }
/* 6865 */     context.putDelivery("is-parent", String.valueOf(isParent));
/* 6866 */     context.putDelivery("old-selection-no", selection.getSelectionNo());
/*      */     
/* 6868 */     String price = "0.00";
/* 6869 */     if (selection.getPriceCode() != null && 
/* 6870 */       selection.getPriceCode().getTotalCost() > 0.0F) {
/* 6871 */       price = MilestoneHelper.formatDollarPrice(selection.getPriceCode().getTotalCost());
/*      */     }
/* 6873 */     context.putDelivery("price", price);
/*      */     
/* 6875 */     String lastUpdateUser = "";
/* 6876 */     if (selection.getLastUpdatingUser() != null)
/* 6877 */       lastUpdateUser = selection.getLastUpdatingUser().getName(); 
/* 6878 */     context.putDelivery("lastUpdateUser", lastUpdateUser);
/*      */     
/* 6880 */     return selectionForm;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Form buildNewDigitalForm(Context context, Selection selection, String command) {
/* 6891 */     Vector projectList = (Vector)context.getSessionValue("searchResults");
/* 6892 */     String resultsIndex = (String)context.getSessionValue("selectionScreenTypeIndex");
/* 6893 */     System.out.println("value of resultsIndex:[" + resultsIndex + "]");
/*      */     
/* 6895 */     ProjectSearch selectedProject = null;
/* 6896 */     if (resultsIndex != null) {
/* 6897 */       selectedProject = (ProjectSearch)projectList.elementAt(Integer.parseInt(resultsIndex));
/*      */     } else {
/* 6899 */       selectedProject = new ProjectSearch();
/*      */     } 
/*      */ 
/*      */     
/* 6903 */     context.removeSessionValue("selectionScreenType");
/* 6904 */     context.removeSessionValue("searchResults");
/* 6905 */     context.removeSessionValue("selectionScreenTypeIndex");
/*      */     
/* 6907 */     Form selectionForm = new Form(this.application, "selectionForm", 
/* 6908 */         this.application.getInfrastructure().getServletURL(), "POST");
/*      */     
/* 6910 */     User user = (User)context.getSession().getAttribute("user");
/* 6911 */     int userId = user.getUserId();
/*      */ 
/*      */     
/* 6914 */     int secureLevel = getSelectionPermissions(selection, user);
/* 6915 */     setButtonVisibilities(selection, user, context, secureLevel, command);
/*      */ 
/*      */     
/* 6918 */     selectionForm.addElement(new FormHidden("cmd", "selection-edit-new", true));
/* 6919 */     selectionForm.addElement(new FormHidden("OrderBy", "", true));
/* 6920 */     selectionForm.addElement(new FormHidden("hidTitleId", "", true));
/* 6921 */     selectionForm.addElement(new FormHidden("isFocus", "", true));
/* 6922 */     selectionForm.addElement(new FormHidden("statusHidVal", "", true));
/* 6923 */     selectionForm.addElement(new FormHidden("generateSelection", "", true));
/*      */     
/* 6925 */     String selectedConfig = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6948 */     String strArtistFirstName = (selectedProject.getArtistFirstName() != null) ? selectedProject.getArtistFirstName() : "";
/* 6949 */     FormTextField artistFirstName = new FormTextField("artistFirstName", strArtistFirstName, false, 20, 50);
/* 6950 */     artistFirstName.setTabIndex(1);
/* 6951 */     artistFirstName.setClassName("ctrlMedium");
/* 6952 */     artistFirstName.addFormEvent("onChange", "javaScript:removeSpaces(this);");
/* 6953 */     selectionForm.addElement(artistFirstName);
/*      */ 
/*      */     
/* 6956 */     String strArtistLastName = (selectedProject.getArtistLastName() != null) ? selectedProject.getArtistLastName() : "";
/* 6957 */     FormTextField artistLastName = new FormTextField("artistLastName", strArtistLastName, false, 20, 50);
/* 6958 */     artistLastName.setTabIndex(2);
/* 6959 */     artistLastName.setClassName("ctrlMedium");
/* 6960 */     artistLastName.addFormEvent("onChange", "javaScript:removeSpaces(this);");
/* 6961 */     selectionForm.addElement(artistLastName);
/*      */ 
/*      */     
/* 6964 */     String strTitle = (selectedProject.getTitle() != null) ? selectedProject.getTitle() : "";
/* 6965 */     FormTextField title = new FormTextField("title", strTitle, true, 73, 125);
/* 6966 */     title.setTabIndex(3);
/* 6967 */     title.setClassName("ctrlXLarge");
/* 6968 */     title.addFormEvent("onChange", "javaScript:removeSpaces(this);");
/* 6969 */     selectionForm.addElement(title);
/*      */ 
/*      */     
/* 6972 */     FormTextField sideATitle = new FormTextField("sideATitle", "", false, 20, 125);
/* 6973 */     sideATitle.setTabIndex(4);
/* 6974 */     sideATitle.setClassName("ctrlMedium");
/* 6975 */     selectionForm.addElement(sideATitle);
/*      */ 
/*      */     
/* 6978 */     FormTextField sideBTitle = new FormTextField("sideBTitle", "", false, 20, 125);
/* 6979 */     sideBTitle.setTabIndex(5);
/* 6980 */     sideBTitle.setClassName("ctrlMedium");
/* 6981 */     selectionForm.addElement(sideBTitle);
/*      */ 
/*      */     
/* 6984 */     String[] values = new String[2];
/* 6985 */     values[0] = "true";
/* 6986 */     values[1] = "false";
/*      */     
/* 6988 */     String[] labels = new String[2];
/* 6989 */     labels[0] = "New Bundle";
/* 6990 */     labels[1] = "Exact Duplicate of Physical Product";
/*      */     
/* 6992 */     FormRadioButtonGroup newBundle = new FormRadioButtonGroup("newBundle", "true", values, labels, false);
/*      */     
/* 6994 */     newBundle.addFormEvent("onClick", "JavaScript:filterScheduleType(true)");
/* 6995 */     newBundle.setTabIndex(5);
/* 6996 */     selectionForm.addElement(newBundle);
/*      */ 
/*      */     
/* 6999 */     FormCheckBox priority = new FormCheckBox("priority", "", false, false);
/* 7000 */     priority.setTabIndex(6);
/* 7001 */     selectionForm.addElement(priority);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 7007 */     FormTextField streetDate = new FormTextField("streetDate", "", false, 10);
/* 7008 */     streetDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
/* 7009 */     streetDate.addFormEvent("onBlur", "JavaScript:validateDate(document.forms[0].streetDate.value,this)");
/* 7010 */     streetDate.setTabIndex(7);
/* 7011 */     streetDate.setClassName("ctrlShort");
/* 7012 */     selectionForm.addElement(streetDate);
/*      */ 
/*      */     
/* 7015 */     FormTextField drDate = new FormTextField("digitalDate", "", false, 10);
/* 7016 */     drDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
/* 7017 */     drDate.addFormEvent("onBlur", "JavaScript:validateDate(document.forms[0].digitalDate.value,this)");
/* 7018 */     drDate.setTabIndex(6);
/* 7019 */     drDate.setClassName("ctrlShort");
/* 7020 */     selectionForm.addElement(drDate);
/*      */ 
/*      */     
/* 7023 */     FormDateField intDate = new FormDateField("internationalDate", "", false, 10);
/* 7024 */     intDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
/* 7025 */     intDate.addFormEvent("onBlur", "JavaScript:validateDate(document.forms[0].internationalDate.value,this)");
/* 7026 */     intDate.setTabIndex(8);
/* 7027 */     intDate.setClassName("ctrlShort");
/* 7028 */     selectionForm.addElement(intDate);
/*      */ 
/*      */     
/* 7031 */     FormDropDownMenu status = MilestoneHelper.getLookupDropDown("status", Cache.getSelectionStatusList(), "Active", true, false);
/* 7032 */     status.setTabIndex(9);
/* 7033 */     status.setClassName("ctrlSmall");
/* 7034 */     selectionForm.addElement(status);
/*      */ 
/*      */     
/* 7037 */     FormCheckBox holdIndicator = new FormCheckBox("holdIndicator", "", false, false);
/* 7038 */     holdIndicator.setTabIndex(10);
/* 7039 */     selectionForm.addElement(holdIndicator);
/*      */ 
/*      */     
/* 7042 */     FormTextArea holdReason = new FormTextArea("holdReason", "", false, 2, 44, "virtual");
/* 7043 */     holdReason.addFormEvent("onBlur", "Javascript:checkField(this)");
/* 7044 */     selectionForm.addElement(holdReason);
/*      */ 
/*      */     
/* 7047 */     int pd_indicator = selectedProject.getPD_Indicator();
/* 7048 */     boolean pdBool = false;
/* 7049 */     if (pd_indicator == 1) {
/* 7050 */       pdBool = true;
/*      */     }
/* 7052 */     FormCheckBox pdIndicator = new FormCheckBox("pdIndicator", "", false, 
/* 7053 */         pdBool);
/* 7054 */     pdIndicator.setTabIndex(6);
/* 7055 */     selectionForm.addElement(pdIndicator);
/*      */ 
/*      */     
/* 7058 */     FormCheckBox intlFlag = new FormCheckBox("intlFlag", "", false, false);
/* 7059 */     intlFlag.setTabIndex(12);
/* 7060 */     selectionForm.addElement(intlFlag);
/*      */ 
/*      */     
/* 7063 */     FormDateField impactDate = new FormDateField("impactdate", "", false, 13);
/* 7064 */     impactDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
/* 7065 */     impactDate.addFormEvent("onBlur", "JavaScript:validateDate(document.forms[0].impactdate.value,this)");
/* 7066 */     impactDate.setTabIndex(13);
/* 7067 */     impactDate.setClassName("ctrlShort");
/* 7068 */     selectionForm.addElement(impactDate);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 7073 */     Vector releaseFamilies = ReleasingFamily.getReleasingFamilies(userId, selectedProject.getMSFamilyId(), context);
/* 7074 */     ReleasingFamily defaultReleasingFamily = ReleasingFamily.getDefaultReleasingFamily(userId, selectedProject.getMSFamilyId(), context);
/* 7075 */     FormDropDownMenu releasingFamily = MilestoneHelper.getReleasingFamilyDropDown("releasingFamily", String.valueOf(defaultReleasingFamily.getReleasingFamilyId()), releaseFamilies, true, selection);
/* 7076 */     releasingFamily.setTabIndex(13);
/*      */     
/* 7078 */     releasingFamily.addFormEvent("onChange", "ReleasingFamilyChange(this)");
/* 7079 */     selectionForm.addElement(releasingFamily);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 7084 */     String envId = String.valueOf(selectedProject.getMSEnvironmentId());
/* 7085 */     String envName = MilestoneHelper.getStructureName(selectedProject.getMSEnvironmentId());
/* 7086 */     String environmentName = "";
/* 7087 */     FormHidden evironment = new FormHidden("environment", envId, false);
/* 7088 */     evironment.setDisplayName(envName);
/* 7089 */     selectionForm.addElement(evironment);
/*      */ 
/*      */     
/* 7092 */     String companyId = String.valueOf(selectedProject.getMSCompanyId());
/* 7093 */     String companyName = MilestoneHelper.getStructureName(selectedProject.getMSCompanyId());
/* 7094 */     FormHidden company = new FormHidden("company", companyId, false);
/* 7095 */     company.setTabIndex(15);
/* 7096 */     company.setDisplayName(companyName);
/* 7097 */     selectionForm.addElement(company);
/*      */ 
/*      */     
/* 7100 */     String divisionId = String.valueOf(selectedProject.getMSDivisionId());
/* 7101 */     String divisionName = MilestoneHelper.getStructureName(selectedProject.getMSDivisionId());
/* 7102 */     FormHidden division = new FormHidden("division", divisionId, false);
/* 7103 */     division.setTabIndex(16);
/* 7104 */     division.setDisplayName(divisionName);
/* 7105 */     selectionForm.addElement(division);
/*      */ 
/*      */     
/* 7108 */     String labelId = String.valueOf(selectedProject.getMSLabelId());
/* 7109 */     String labelName = MilestoneHelper.getStructureName(selectedProject.getMSLabelId());
/* 7110 */     FormHidden label = new FormHidden("label", labelId, false);
/* 7111 */     label.setTabIndex(17);
/* 7112 */     label.setDisplayName(labelName);
/* 7113 */     selectionForm.addElement(label);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 7118 */     if (selectedProject.getOperCompany().equals("***")) {
/* 7119 */       FormHidden opercompany = new FormHidden("opercompany", "***", false);
/*      */       
/* 7121 */       opercompany.setDisplayName("***");
/* 7122 */       selectionForm.addElement(opercompany);
/*      */     } else {
/* 7124 */       LookupObject oc = MilestoneHelper.getLookupObject(selectedProject
/* 7125 */           .getOperCompany(), Cache.getOperatingCompanies());
/* 7126 */       String ocAbbr = "";
/* 7127 */       String ocName = "";
/*      */ 
/*      */ 
/*      */       
/* 7131 */       if (oc == null) {
/* 7132 */         ocAbbr = selectedProject.getOperCompany();
/*      */       } else {
/* 7134 */         if (oc != null && oc.getAbbreviation() != null)
/* 7135 */           ocAbbr = oc.getAbbreviation(); 
/* 7136 */         if (oc != null && oc.getName() != null)
/* 7137 */           ocName = ":" + oc.getName(); 
/*      */       } 
/* 7139 */       FormHidden opercompany = new FormHidden("opercompany", ocAbbr, false);
/*      */       
/* 7141 */       opercompany.setDisplayName(String.valueOf(ocAbbr) + ocName);
/*      */       
/* 7143 */       if (ocAbbr.equals("ZZ"))
/* 7144 */         opercompany.setDisplayName(ocAbbr); 
/* 7145 */       selectionForm.addElement(opercompany);
/*      */     } 
/*      */ 
/*      */     
/* 7149 */     FormHidden superlabel = new FormHidden("superlabel", selectedProject.getSuperLabel(), false);
/*      */     
/* 7151 */     superlabel.setClassName("ctrlShort");
/* 7152 */     superlabel.addFormEvent("onBlur", "javascript:this.value=this.value.toUpperCase();");
/* 7153 */     selectionForm.addElement(superlabel);
/*      */ 
/*      */     
/* 7156 */     FormHidden sublabel = new FormHidden("sublabel", selectedProject.getSubLabel(), false);
/*      */     
/* 7158 */     sublabel.setClassName("ctrlShort");
/* 7159 */     sublabel.addFormEvent("onBlur", "javascript:this.value=this.value.toUpperCase();");
/* 7160 */     selectionForm.addElement(sublabel);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 7169 */     Vector jdeExceptionFamilies = ProjectSearchManager.getInstance().getProjectSearchJDEFamilies();
/* 7170 */     boolean isUmvdUser = jdeExceptionFamilies.contains(new Integer(selectedProject.getMSFamilyId()));
/*      */ 
/*      */     
/* 7173 */     String imprintStr = "";
/* 7174 */     if (isUmvdUser) {
/* 7175 */       imprintStr = labelName;
/*      */     } else {
/* 7177 */       imprintStr = (selectedProject.getImprint() != null) ? selectedProject.getImprint() : "";
/*      */     } 
/* 7179 */     FormTextField imprint = new FormTextField("imprint", imprintStr, false, 50);
/* 7180 */     imprint.setTabIndex(21);
/* 7181 */     imprint.setClassName("ctrlMedium");
/* 7182 */     imprint.addFormEvent("onChange", "javaScript:removeSpaces(this);");
/* 7183 */     selectionForm.addElement(imprint);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 7188 */     FormDropDownMenu configcode = MilestoneHelper.getPfmLookupDropDown("configcode", MilestoneHelper.getConfigCodes(1), "", false, true);
/* 7189 */     configcode.setTabIndex(21);
/*      */     
/* 7191 */     configcode.addFormEvent("onKeyPress", "scanKey(window.event.keyCode, 'document.all.configcode.options', getMaxLength(document.all.configcode.options))");
/*      */     
/* 7193 */     selectionForm.addElement(configcode);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 7203 */     String projectIdStr = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 7210 */     projectIdStr = selectedProject.getRMSProjectNo();
/* 7211 */     FormHidden projectId = new FormHidden("projectId", projectIdStr, false);
/* 7212 */     projectId.setTabIndex(22);
/* 7213 */     projectId.setClassName("ctrlMedium");
/* 7214 */     projectId.setDisplayName(projectIdStr);
/* 7215 */     selectionForm.addElement(projectId);
/*      */ 
/*      */     
/* 7218 */     FormTextField upc = new FormTextField("UPC", "", false, 17, 20);
/* 7219 */     upc.setTabIndex(23);
/* 7220 */     upc.setClassName("ctrlMedium");
/*      */     
/* 7222 */     upc.addFormEvent("onKeyPress", "return checkNumbersOnly(window.event.keyCode);");
/*      */ 
/*      */     
/* 7225 */     selectionForm.addElement(upc);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 7233 */     FormTextField soundscan = new FormTextField("soundscan", "", false, 17, 20);
/* 7234 */     soundscan.setTabIndex(24);
/* 7235 */     soundscan.setClassName("ctrlMedium");
/* 7236 */     selectionForm.addElement(soundscan);
/*      */ 
/*      */ 
/*      */     
/* 7240 */     FormTextField gridNumber = new FormTextField("gridNumber", "", false, 50);
/* 7241 */     gridNumber.setTabIndex(24);
/* 7242 */     gridNumber.setEnabled(true);
/*      */     
/* 7244 */     selectionForm.addElement(gridNumber);
/*      */ 
/*      */ 
/*      */     
/* 7248 */     FormDropDownMenu prefix = MilestoneHelper.getPrefixCodeDropDown(userId, "prefix", Cache.getPrefixCodes(), "", true, context);
/* 7249 */     prefix.setTabIndex(25);
/*      */     
/* 7251 */     prefix.setClassName("ctrlShort");
/* 7252 */     selectionForm.addElement(prefix);
/*      */ 
/*      */     
/* 7255 */     FormTextField selectionNo = new FormTextField("selectionNo", "", false, 20, 20);
/* 7256 */     selectionNo.setTabIndex(26);
/*      */     
/* 7258 */     selectionNo.setClassName("ctrlMedium");
/* 7259 */     selectionNo.addFormEvent("onChange", "javaScript:removeSpaces(this);");
/* 7260 */     selectionForm.addElement(selectionNo);
/*      */ 
/*      */     
/* 7263 */     FormTextField titleId = new FormTextField("titleId", "", false, 13, 24);
/* 7264 */     titleId.setClassName("ctrlMedium");
/*      */     
/* 7266 */     titleId.setTabIndex(27);
/*      */ 
/*      */     
/* 7269 */     selectionForm.addElement(titleId);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 7276 */     FormDropDownMenu productLine = MilestoneHelper.getLookupDropDown("productLine", Cache.getProductCategories(1), "", true, true);
/* 7277 */     productLine.setTabIndex(28);
/* 7278 */     productLine.setClassName("ctrlMedium");
/* 7279 */     selectionForm.addElement(productLine);
/*      */ 
/*      */     
/* 7282 */     FormDropDownMenu releaseType = MilestoneHelper.getLookupDropDown("releaseType", Cache.getReleaseTypes(), "CO", true, true);
/* 7283 */     releaseType.setTabIndex(29);
/* 7284 */     releaseType.setClassName("ctrlMedium");
/* 7285 */     releaseType.addFormEvent("onChange", "releaseTypeChanged()");
/* 7286 */     selectionForm.addElement(releaseType);
/*      */ 
/*      */     
/* 7289 */     FormDropDownMenu configuration = MilestoneHelper.getSelectionConfigurationDropDown("configuration", "", true, 1);
/* 7290 */     configuration.setTabIndex(30);
/*      */     
/* 7292 */     configuration.addFormEvent("onChange", "buildSubConfigs(this.selectedIndex)");
/* 7293 */     selectionForm.addElement(configuration);
/*      */ 
/*      */ 
/*      */     
/* 7297 */     Vector configs = Cache.getSelectionConfigs();
/* 7298 */     SelectionConfiguration config = (SelectionConfiguration)configs.get(0);
/* 7299 */     FormDropDownMenu subConfiguration = new FormDropDownMenu("subConfiguration", "", "", "", true);
/*      */     
/* 7301 */     subConfiguration.addFormEvent("onChange", "isSubConfigParent(this.selectedIndex)");
/* 7302 */     subConfiguration.setTabIndex(31);
/* 7303 */     subConfiguration.setEnabled(false);
/*      */     
/* 7305 */     selectionForm.addElement(subConfiguration);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 7312 */     FormTextField test = new FormTextField("test", "", false, 8, 8);
/* 7313 */     test.setTabIndex(32);
/* 7314 */     test.setClassName("ctrlShort");
/*      */ 
/*      */ 
/*      */     
/* 7318 */     test.addFormEvent("onChange", "javaScript:clickSell(this,true);");
/* 7319 */     selectionForm.addElement(test);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 7324 */     FormDropDownMenu priceCode = new FormDropDownMenu("priceCode", "", "-1" + getSellCodesStringDPC(), "&nbsp;" + getSellCodesStringDPC(), true);
/* 7325 */     priceCode.setTabIndex(33);
/*      */     
/* 7327 */     priceCode.setClassName("ctrlSmall");
/*      */     
/* 7329 */     selectionForm.addElement(priceCode);
/*      */ 
/*      */ 
/*      */     
/* 7333 */     FormTextField numOfUnits = new FormTextField("numOfUnits", "0", false, 10, 10);
/* 7334 */     numOfUnits.setTabIndex(34);
/* 7335 */     numOfUnits.setClassName("ctrlShort");
/* 7336 */     selectionForm.addElement(numOfUnits);
/*      */ 
/*      */     
/* 7339 */     FormDropDownMenu contactList = MilestoneHelper.getContactsDropDown(context, "contactlist", new Vector(), user, true);
/* 7340 */     contactList.setTabIndex(35);
/* 7341 */     contactList.setClassName("ctrlMedium");
/* 7342 */     selectionForm.addElement(contactList);
/*      */ 
/*      */     
/* 7345 */     FormTextField contact = new FormTextField("contact", "", false, 14, 30);
/* 7346 */     contact.setTabIndex(36);
/* 7347 */     contact.setClassName("ctrlMedium");
/* 7348 */     selectionForm.addElement(contact);
/*      */ 
/*      */     
/* 7351 */     FormCheckBox parentalIndicator = new FormCheckBox("parentalIndicator", "", false, false);
/* 7352 */     parentalIndicator.setTabIndex(37);
/* 7353 */     selectionForm.addElement(parentalIndicator);
/*      */ 
/*      */     
/* 7356 */     FormCheckBox specPkgIndicator = new FormCheckBox("specialPkgIndicator", "", false, false);
/* 7357 */     specPkgIndicator.addFormEvent("onDblClick", "document.forms[0].PackagingHelper.value=document.forms[0].package.value;togglePackaging();");
/* 7358 */     specPkgIndicator.setTabIndex(38);
/* 7359 */     selectionForm.addElement(specPkgIndicator);
/*      */ 
/*      */     
/* 7362 */     FormTextField pkg = new FormTextField("package", "", false, 13, 100);
/* 7363 */     pkg.setTabIndex(39);
/* 7364 */     pkg.setClassName("ctrlMedium");
/* 7365 */     pkg.addFormEvent("onDblClick", "document.forms[0].PackagingHelper.value=document.forms[0].package.value;togglePackaging();");
/* 7366 */     selectionForm.addElement(pkg);
/*      */ 
/*      */     
/* 7369 */     FormDropDownMenu genre = MilestoneHelper.getPfmLookupDropDown("genre", Cache.getMusicTypes(), "", true, true);
/*      */     
/* 7371 */     genre.setTabIndex(40);
/* 7372 */     genre.setId("music_type");
/*      */     
/* 7374 */     selectionForm.addElement(genre);
/*      */ 
/*      */     
/* 7377 */     FormTextField territory = new FormTextField("territory", "", false, 13, 255);
/* 7378 */     territory.setTabIndex(41);
/* 7379 */     territory.setClassName("ctrlMedium");
/* 7380 */     territory.addFormEvent("onDblClick", "document.forms[0].TerritoryHelper.value=document.forms[0].territory.value;toggleTerritory();");
/* 7381 */     selectionForm.addElement(territory);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 7388 */     FormTextArea specialInstructions = new FormTextArea("specialInstructions", "", false, 3, 80, "virtual");
/* 7389 */     specialInstructions.setTabIndex(42);
/*      */     
/* 7391 */     selectionForm.addElement(specialInstructions);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 7399 */     FormTextField lastStreetUpdatedDate = new FormTextField("laststreetupdateddate", "", false, 13);
/* 7400 */     selectionForm.addElement(lastStreetUpdatedDate);
/*      */ 
/*      */     
/* 7403 */     FormTextField lastUpdatedDate = new FormTextField("lastupdateddate", "", false, 50);
/* 7404 */     selectionForm.addElement(lastUpdatedDate);
/*      */ 
/*      */     
/* 7407 */     FormTextField originDate = new FormTextField("origindate", "", false, 13);
/* 7408 */     selectionForm.addElement(originDate);
/*      */ 
/*      */     
/* 7411 */     FormTextArea packagingHelper = new FormTextArea("PackagingHelper", "", false, 2, 44, "virtual");
/* 7412 */     selectionForm.addElement(packagingHelper);
/*      */ 
/*      */     
/* 7415 */     FormTextArea territoryHelper = new FormTextArea("TerritoryHelper", selection.getSelectionTerritory(), false, 2, 44, "virtual");
/* 7416 */     selectionForm.addElement(territoryHelper);
/*      */ 
/*      */     
/* 7419 */     FormTextArea comments = new FormTextArea("comments", "", false, 2, 44, "virtual");
/* 7420 */     comments.addFormEvent("onBlur", "Javascript:checkField(this)");
/* 7421 */     selectionForm.addElement(comments);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 7428 */     addSelectionSearchElements(context, new Selection(), selectionForm, MilestoneHelper.getUserCompanies(context), true);
/*      */ 
/*      */     
/* 7431 */     if (context.getSessionValue("NOTEPAD_SELECTION_VISIBLE") != null) {
/* 7432 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_SELECTION_VISIBLE"));
/*      */     }
/*      */     
/* 7435 */     context.putDelivery("releaseWeek", "");
/* 7436 */     context.putDelivery("new-or-copy", "true");
/* 7437 */     context.putDelivery("price", "0.00");
/*      */     
/* 7439 */     boolean isParent = false;
/*      */     
/* 7441 */     if (selection.getSelectionSubConfig() != null) {
/* 7442 */       isParent = selection.getSelectionSubConfig().isParent();
/*      */     }
/* 7444 */     context.putDelivery("is-parent", String.valueOf(isParent));
/*      */     
/* 7446 */     String lastUpdateUser = "";
/* 7447 */     if (selection.getLastUpdatingUser() != null)
/* 7448 */       lastUpdateUser = selection.getLastUpdatingUser().getName(); 
/* 7449 */     context.putDelivery("lastUpdateUser", lastUpdateUser);
/*      */     
/* 7451 */     return selectionForm;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void resetSearchVariables(User user, User userSrch, Context context) {
/* 7459 */     user.SS_searchInitiated = userSrch.SS_searchInitiated;
/* 7460 */     user.SS_artistSearch = userSrch.SS_artistSearch;
/* 7461 */     user.SS_titleSearch = userSrch.SS_titleSearch;
/* 7462 */     user.SS_selectionNoSearch = userSrch.SS_selectionNoSearch;
/* 7463 */     user.SS_prefixIDSearch = userSrch.SS_prefixIDSearch;
/* 7464 */     user.SS_upcSearch = userSrch.SS_upcSearch;
/* 7465 */     user.SS_streetDateSearch = userSrch.SS_streetDateSearch;
/* 7466 */     user.SS_streetEndDateSearch = userSrch.SS_streetEndDateSearch;
/* 7467 */     user.SS_configSearch = userSrch.SS_configSearch;
/* 7468 */     user.SS_subconfigSearch = userSrch.SS_subconfigSearch;
/* 7469 */     user.SS_labelSearch = userSrch.SS_labelSearch;
/* 7470 */     user.SS_companySearch = userSrch.SS_companySearch;
/* 7471 */     user.SS_contactSearch = userSrch.SS_contactSearch;
/* 7472 */     user.SS_familySearch = userSrch.SS_familySearch;
/* 7473 */     user.SS_environmentSearch = userSrch.SS_environmentSearch;
/* 7474 */     user.SS_projectIDSearch = userSrch.SS_projectIDSearch;
/* 7475 */     user.SS_productTypeSearch = userSrch.SS_productTypeSearch;
/* 7476 */     user.SS_showAllSearch = userSrch.SS_showAllSearch;
/* 7477 */     user.RC_environment = userSrch.RC_environment;
/* 7478 */     user.RC_releasingFamily = userSrch.RC_releasingFamily;
/* 7479 */     user.RC_labelContact = userSrch.RC_labelContact;
/* 7480 */     user.RC_productType = userSrch.RC_productType;
/*      */     
/* 7482 */     context.removeSessionValue("ResetSearchVariables");
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
/*      */   public static DcGDRSResults GDRSProductStatusGet(Selection selection, int environmentID) {
/* 7494 */     DcGDRSResults dcGDRSResults = new DcGDRSResults();
/*      */     try {
/* 7496 */       dcGDRSResults = projectSearchSvcClient.GDRSProductStatusGet(selection.getSelectionID(), selection.getReleaseFamilyId(), environmentID);
/* 7497 */     } catch (RemoteException re) {
/*      */       
/* 7499 */       dcGDRSResults.setExceptionMessage(re.getMessage());
/* 7500 */       System.out.println(re.getMessage());
/* 7501 */     } catch (Exception e) {
/* 7502 */       dcGDRSResults.setExceptionMessage(e.getMessage());
/* 7503 */       System.out.println(e.getMessage());
/*      */     } 
/*      */     
/* 7506 */     return dcGDRSResults;
/*      */   }
/*      */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\SelectionHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */