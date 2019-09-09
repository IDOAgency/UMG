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
/*  910 */       String productGroupCode = form.getStringValue("productGroupCode");
/*      */       
/*  912 */       String comments = form.getStringValue("comments");
/*  913 */       String priceCode = form.getStringValue("priceCode");
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  918 */       int numOfUnits = 0;
/*      */       
/*      */       try {
/*  921 */         numOfUnits = Integer.parseInt(form.getStringValue("numOfUnits"));
/*      */       }
/*  923 */       catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */       
/*  927 */       String digitalRlsDateString = form.getStringValue("digitalDate");
/*      */       
/*  929 */       String operCompany = form.getStringValue("opercompany");
/*  930 */       String superLabel = form.getStringValue("superlabel");
/*  931 */       String subLabel = form.getStringValue("sublabel");
/*  932 */       String configCode = form.getStringValue("configcode");
/*  933 */       String soundscanGrp = form.getStringValue("soundscan");
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  938 */       String imprint = form.getStringValue("imprint");
/*      */ 
/*      */       
/*  941 */       if (selection.getSellCode() != null && !selection.getSellCode().equalsIgnoreCase(sellCode))
/*      */       {
/*  943 */         selection.setPriceCode(SelectionManager.getInstance().getPriceCode(sellCode));
/*      */       }
/*      */ 
/*      */       
/*  947 */       if (selection.getSellCodeDPC() != null && !selection.getSellCodeDPC().equalsIgnoreCase(sellCodeDPC))
/*      */       {
/*  949 */         selection.setPriceCodeDPC(SelectionManager.getInstance().getPriceCode(sellCodeDPC));
/*      */       }
/*      */       
/*  952 */       String releasingFamily = form.getStringValue("releasingFamily");
/*      */       
/*  954 */       selection.setNumberOfUnits(numOfUnits);
/*  955 */       selection.setSelectionNo(selectionNo);
/*  956 */       selection.setProjectID(projectId);
/*  957 */       selection.setTitleID(titleId);
/*  958 */       selection.setTitle(title);
/*  959 */       selection.setArtistFirstName(artistFirstName);
/*  960 */       selection.setArtistLastName(artistLastName);
/*  961 */       selection.setArtist(artist);
/*  962 */       selection.setASide(sideATitle);
/*  963 */       selection.setBSide(sideBTitle);
/*  964 */       selection.setProductCategory((ProductCategory)SelectionManager.getLookupObject(productLine, Cache.getProductCategories()));
/*  965 */       selection.setReleaseType((ReleaseType)SelectionManager.getLookupObject(releaseType, Cache.getReleaseTypes()));
/*  966 */       selection.setSelectionConfig(SelectionManager.getSelectionConfigObject(configuration, Cache.getSelectionConfigs()));
/*  967 */       selection.setSelectionSubConfig(SelectionManager.getSelectionSubConfigObject(subConfiguration, selection.getSelectionConfig()));
/*  968 */       selection.setUpc(upc);
/*  969 */       selection.setSellCode(sellCode);
/*  970 */       selection.setSellCodeDPC(sellCodeDPC);
/*      */       
/*  972 */       selection.setGenre((Genre)SelectionManager.getLookupObject(genre, Cache.getMusicTypes()));
/*  973 */       System.out.println(">>>>>>>>>>>>>>>>>>>>>>> Company: " + company);
/*  974 */       System.out.println(">>>>>>>>>>>>>>>>>>>>>>> enviroment: " + environment);
/*  975 */       selection.setEnvironment((Environment)MilestoneHelper.getStructureObject(Integer.parseInt(environment)));
/*  976 */       selection.setCompany((Company)MilestoneHelper.getStructureObject(Integer.parseInt(company)));
/*  977 */       selection.setDivision((Division)MilestoneHelper.getStructureObject(Integer.parseInt(division)));
/*  978 */       selection.setLabel((Label)MilestoneHelper.getStructureObject(Integer.parseInt(label)));
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/*  983 */         selection.setReleaseFamilyId(Integer.parseInt(releasingFamily));
/*      */       }
/*  985 */       catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  990 */       Calendar oldStreetDate = null;
/*      */ 
/*      */       
/*  993 */       if (selection.getIsDigital()) {
/*  994 */         oldStreetDate = selection.getDigitalRlsDate();
/*      */       } else {
/*  996 */         oldStreetDate = selection.getStreetDate();
/*      */       } 
/*  998 */       boolean isStreetDateDifferent = false;
/*      */ 
/*      */       
/*      */       try {
/* 1002 */         Calendar streetDate = null;
/*      */         
/* 1004 */         if (selection.getIsDigital()) {
/* 1005 */           streetDate = MilestoneHelper.getDate(digitalRlsDateString);
/*      */         } else {
/* 1007 */           streetDate = MilestoneHelper.getDate(streetDateString);
/*      */         } 
/* 1009 */         String oldStreetDateString = MilestoneHelper.getFormatedDate(oldStreetDate);
/* 1010 */         String newStreetDateString = MilestoneHelper.getFormatedDate(streetDate);
/*      */         
/* 1012 */         if ((oldStreetDate == null && streetDate != null) || (oldStreetDate != null && !oldStreetDateString.equals(newStreetDateString))) {
/*      */           
/* 1014 */           selection.setLastStreetUpdateDate(Calendar.getInstance());
/* 1015 */           isStreetDateDifferent = true;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1020 */         selection.setDigitalRlsDate(MilestoneHelper.getDate(digitalRlsDateString));
/* 1021 */         selection.setDigitalRlsDateString(MilestoneHelper.getFormatedDate(MilestoneHelper.getDate(digitalRlsDateString)));
/*      */ 
/*      */ 
/*      */         
/* 1025 */         selection.setStreetDate(MilestoneHelper.getDate(streetDateString));
/* 1026 */         selection.setStreetDateString(MilestoneHelper.getFormatedDate(MilestoneHelper.getDate(streetDateString)));
/*      */       
/*      */       }
/* 1029 */       catch (Exception e) {
/*      */         
/* 1031 */         selection.setStreetDate(null);
/* 1032 */         selection.setDigitalRlsDate(null);
/*      */       } 
/*      */       
/* 1035 */       boolean isScheduleApplied = false;
/* 1036 */       isScheduleApplied = SelectionManager.getInstance().isScheduleApplied(selection);
/*      */       
/* 1038 */       Calendar internationalDate = Calendar.getInstance();
/*      */ 
/*      */       
/*      */       try {
/* 1042 */         internationalDate = MilestoneHelper.getDate(internationalDateString);
/* 1043 */         selection.setInternationalDate(internationalDate);
/*      */       }
/* 1045 */       catch (Exception e) {
/*      */         
/* 1047 */         selection.setInternationalDate(null);
/*      */       } 
/*      */       
/* 1050 */       selection.setOtherContact(otherContact);
/*      */       
/* 1052 */       int contactUser = 0;
/*      */       
/* 1054 */       if (contactlist != null && !contactlist.equals("")) contactUser = Integer.parseInt(contactlist);
/*      */       
/* 1056 */       selection.setLabelContact(UserManager.getInstance().getUser(contactUser));
/* 1057 */       selection.setSelectionStatus((SelectionStatus)SelectionManager.getLookupObject(selectionStatus, Cache.getSelectionStatusList()));
/* 1058 */       selection.setHoldReason(holdReason);
/* 1059 */       selection.setComments(comments);
/* 1060 */       selection.setSelectionPackaging(pkg);
/* 1061 */       selection.setSelectionTerritory(territory);
/* 1062 */       selection.setPrefixID((PrefixCode)SelectionManager.getLookupObject(prefix, Cache.getPrefixCodes()));
/*      */ 
/*      */       
/* 1065 */       selection.setPressAndDistribution(((FormCheckBox)form.getElement("pdIndicator")).isChecked());
/* 1066 */       selection.setSpecialPackaging(((FormCheckBox)form.getElement("specialPkgIndicator")).isChecked());
/* 1067 */       selection.setHoldSelection(((FormCheckBox)form.getElement("holdIndicator")).isChecked());
/*      */       
/* 1069 */       if (!selection.getIsDigital()) {
/* 1070 */         selection.setNoDigitalRelease(((FormCheckBox)form.getElement("noDigitalRelease")).isChecked());
/*      */       }
/*      */ 
/*      */       
/* 1074 */       selection.setParentalGuidance(((FormCheckBox)form.getElement("parentalIndicator")).isChecked());
/*      */       
/* 1076 */       Calendar impactDate = Calendar.getInstance();
/*      */ 
/*      */       
/*      */       try {
/* 1080 */         impactDate = MilestoneHelper.getDate(impactDateString);
/* 1081 */         selection.setImpactDate(impactDate);
/*      */       }
/* 1083 */       catch (Exception e) {
/*      */         
/* 1085 */         selection.setImpactDate(null);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1090 */       Calendar digitalRlsDate = Calendar.getInstance();
/*      */       try {
/* 1092 */         digitalRlsDate = MilestoneHelper.getDate(digitalRlsDateString);
/* 1093 */         selection.setDigitalRlsDate(digitalRlsDate);
/* 1094 */       } catch (Exception e) {
/* 1095 */         selection.setDigitalRlsDate(null);
/*      */       } 
/* 1097 */       selection.setInternationalFlag(((FormCheckBox)form.getElement("intlFlag")).isChecked());
/* 1098 */       selection.setOperCompany(operCompany);
/* 1099 */       selection.setSuperLabel(superLabel);
/* 1100 */       selection.setSubLabel(subLabel);
/* 1101 */       selection.setConfigCode(configCode);
/* 1102 */       selection.setSoundScanGrp(soundscanGrp);
/*      */ 
/*      */       
/* 1105 */       selection.setImprint(imprint);
/*      */ 
/*      */       
/* 1108 */       if (selection.getIsDigital()) {
/*      */ 
/*      */         
/* 1111 */         String newBundleString = form.getStringValue("newBundle");
/* 1112 */         if (newBundleString.equalsIgnoreCase("true")) {
/* 1113 */           selection.setNewBundleFlag(true);
/*      */         } else {
/* 1115 */           selection.setNewBundleFlag(false);
/*      */         } 
/* 1117 */         String specialInstructions = form.getStringValue("specialInstructions");
/* 1118 */         selection.setSpecialInstructions(specialInstructions);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1125 */         selection.setPriority(((FormCheckBox)form.getElement("priority")).isChecked());
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1130 */       String gridNumber = form.getStringValue("gridNumber");
/* 1131 */       selection.setGridNumber(gridNumber);
/*      */ 
/*      */ 
/*      */       
/* 1135 */       boolean isPNR = false;
/* 1136 */       if (context.getParameter("generateSelection") != null && (
/* 1137 */         context.getParameter("generateSelection").equalsIgnoreCase("LPNG") || 
/* 1138 */         context.getParameter("generateSelection").equalsIgnoreCase("TPNG"))) {
/* 1139 */         isPNR = true;
/*      */       }
/*      */       
/* 1142 */       String strResponse = "";
/* 1143 */       strResponse = applyBusinessRules(form, context, selection);
/*      */       
/* 1145 */       if (!strResponse.trim().equals("")) {
/* 1146 */         context.putDelivery("AlertMessage", strResponse.trim());
/* 1147 */         form.setValues(context);
/* 1148 */         User user = (User)context.getSession().getAttribute("user");
/* 1149 */         int userId = user.getUserId();
/* 1150 */         int secureLevel = getSelectionPermissions(selection, user);
/* 1151 */         setButtonVisibilities(selection, user, context, secureLevel, "new");
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1156 */         FormHidden opCo = (FormHidden)form.getElement("opercompany");
/*      */         
/* 1158 */         LookupObject oc = MilestoneHelper.getLookupObject(selection
/* 1159 */             .getOperCompany(), Cache.getOperatingCompanies());
/* 1160 */         String ocAbbr = "";
/* 1161 */         String ocName = "";
/* 1162 */         if (oc != null && oc.getAbbreviation() != null)
/* 1163 */           ocAbbr = oc.getAbbreviation(); 
/* 1164 */         if (oc != null && oc.getName() != null) {
/* 1165 */           ocName = oc.getName();
/*      */         }
/* 1167 */         opCo.setDisplayName(String.valueOf(ocAbbr) + ":" + ocName);
/*      */         
/* 1169 */         if (ocAbbr.equals("ZZ")) {
/* 1170 */           opCo.setDisplayName(ocAbbr);
/*      */         }
/*      */         
/* 1173 */         FormHidden projectID = (FormHidden)form.getElement("projectId");
/* 1174 */         projectID.setDisplayName(String.valueOf(selection.getProjectID()));
/*      */ 
/*      */         
/* 1177 */         context.putDelivery("releaseWeek", MilestoneHelper.getReleaseWeekString(selection));
/*      */ 
/*      */         
/* 1180 */         Vector releaseFamilies = ReleasingFamily.getReleasingFamilies(userId, selection.getEnvironment().getParentFamily().getStructureID(), context);
/* 1181 */         FormDropDownMenu myReleasingFamily = MilestoneHelper.getReleasingFamilyDropDown("releasingFamily", Integer.toString(selection.getReleaseFamilyId()), releaseFamilies, true, selection);
/* 1182 */         form.removeElement("releasingFamily");
/*      */         
/* 1184 */         myReleasingFamily.addFormEvent("onChange", "ReleasingFamilyChange(this)");
/* 1185 */         form.addElement(myReleasingFamily);
/*      */ 
/*      */         
/* 1188 */         String envId = "";
/* 1189 */         String envName = "";
/* 1190 */         if (selection.getEnvironment() != null) {
/* 1191 */           envId = Integer.toString(selection.getEnvironment().getStructureID());
/* 1192 */           envName = selection.getEnvironment().getName();
/*      */         } 
/* 1194 */         FormHidden myEnv = new FormHidden("environment", envId, false);
/* 1195 */         myEnv.setDisplayName(envName);
/* 1196 */         form.removeElement("environment");
/* 1197 */         form.addElement(myEnv);
/*      */ 
/*      */         
/* 1200 */         String companyId = "";
/* 1201 */         String companyName = "";
/*      */ 
/*      */         
/* 1204 */         if (selection.getCompany() != null) {
/* 1205 */           companyId = Integer.toString(selection.getCompany().getStructureID());
/* 1206 */           companyName = selection.getCompany().getName();
/*      */         } 
/* 1208 */         FormHidden myCcompany = new FormHidden("company", companyId, false);
/* 1209 */         myCcompany.setTabIndex(15);
/* 1210 */         myCcompany.setDisplayName(companyName);
/*      */         
/* 1212 */         myCcompany.addFormEvent("onClick", "return(clickCompany(this))");
/* 1213 */         form.removeElement("company");
/* 1214 */         form.addElement(myCcompany);
/*      */ 
/*      */         
/* 1217 */         String divisionId = "";
/* 1218 */         String divisionName = "";
/* 1219 */         if (selection.getDivision() != null) {
/* 1220 */           divisionId = Integer.toString(selection.getDivision().getStructureID());
/* 1221 */           divisionName = selection.getDivision().getName();
/*      */         } else {
/*      */           
/* 1224 */           divisionId = "";
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1233 */         FormHidden myDivision = new FormHidden("division", divisionId, false);
/* 1234 */         myDivision.setTabIndex(16);
/* 1235 */         myDivision.setDisplayName(divisionName);
/*      */         
/* 1237 */         myDivision.addFormEvent("onChange", "return(clickDivision(this))");
/*      */         
/* 1239 */         form.removeElement("division");
/* 1240 */         form.addElement(myDivision);
/*      */ 
/*      */         
/* 1243 */         String labelId = "";
/* 1244 */         String labelName = "";
/* 1245 */         if (selection.getLabel() != null) {
/* 1246 */           labelId = Integer.toString(selection.getLabel().getStructureID());
/* 1247 */           labelName = selection.getLabel().getName();
/*      */         } else {
/* 1249 */           labelId = "";
/*      */         } 
/* 1251 */         FormHidden myLabel = new FormHidden("label", labelId, false);
/* 1252 */         myLabel.setTabIndex(17);
/* 1253 */         myLabel.setDisplayName(labelName);
/*      */         
/* 1255 */         form.removeElement("label");
/* 1256 */         form.addElement(myLabel);
/*      */ 
/*      */ 
/*      */         
/* 1260 */         String subConfigValue = "";
/* 1261 */         if (selection.getSelectionSubConfig() != null) subConfigValue = selection.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation(); 
/* 1262 */         FormDropDownMenu mySubConfiguration = MilestoneHelper.getSelectionSubConfigurationDropDown("subConfiguration", selection.getSelectionConfig(), subConfigValue, true);
/* 1263 */         mySubConfiguration.addFormEvent("onChange", "isSubConfigParent(this.selectedIndex)");
/* 1264 */         mySubConfiguration.setTabIndex(30);
/* 1265 */         mySubConfiguration.setClassName("ctrlMedium");
/* 1266 */         form.removeElement("subConfiguration");
/* 1267 */         form.addElement(mySubConfiguration);
/*      */         
/* 1269 */         context.putDelivery("Form", form);
/*      */         
/* 1271 */         if (selection.getIsDigital()) {
/* 1272 */           return context.includeJSP("digital-selection-editor.jsp");
/*      */         }
/* 1274 */         return context.includeJSP("selection-editor.jsp");
/*      */       } 
/*      */       
/* 1277 */       if (!form.isUnchanged()) {
/*      */         
/* 1279 */         FormValidation formValidation = form.validate();
/* 1280 */         if (formValidation.isGood()) {
/*      */           
/* 1282 */           User user = (User)context.getSessionValue("user");
/* 1283 */           System.out.println(">>>>>>>>>>>>>>>>>>>>>>> Company: " + selection.getCompanyId());
/* 1284 */           System.out.println(">>>>>>>>>>>>>>>>>>>>>>> enviroment: " + selection.getEnvironmentId());
/* 1285 */           Selection savedSelection = SelectionManager.getInstance().saveSelection(selection, user);
/*      */ 
/*      */           
/* 1288 */           if (!form.getElement("label").getStartingValue().equalsIgnoreCase(form.getElement("label").getStringValue()) || 
/* 1289 */             !form.getElement("releasingFamily").getStartingValue().equalsIgnoreCase(form.getElement("releasingFamily").getStringValue())) {
/* 1290 */             Cache.flushUsedLabels();
/*      */           }
/*      */           
/* 1293 */           FormElement lastUpdated = form.getElement("lastupdateddate");
/* 1294 */           lastUpdated.setValue(MilestoneHelper.getLongDate(savedSelection.getLastUpdateDate()));
/*      */ 
/*      */           
/* 1297 */           notepad.setAllContents(null);
/*      */           
/* 1299 */           if (isNewSelection) {
/* 1300 */             notepad.newSelectedReset();
/*      */           }
/* 1302 */           notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 0);
/* 1303 */           notepad.setSelected(savedSelection);
/* 1304 */           selection = (Selection)notepad.validateSelected();
/*      */ 
/*      */           
/* 1307 */           if (selection == null && notepad.getMaxRecords() < notepad.getTotalRecords() && notepad.getMaxRecords() > 0) {
/*      */             
/* 1309 */             notepad.setMaxRecords(0);
/* 1310 */             notepad.setAllContents(null);
/* 1311 */             notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 0);
/* 1312 */             notepad.setSelected(savedSelection);
/* 1313 */             selection = (Selection)notepad.validateSelected();
/*      */           } 
/*      */           
/* 1316 */           context.putSessionValue("Selection", selection);
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1321 */           if (form.getStringValue("UPC") != null) {
/*      */             
/* 1323 */             String upcStripped = MilestoneHelper_2.reformat_UPC_SSG_SGC_forSave(form.getStringValue("UPC"), "UPC", savedSelection.getIsDigital(), true);
/* 1324 */             form.getElement("UPC").setValue(upcStripped);
/*      */           } 
/* 1326 */           if (form.getStringValue("soundscan") != null) {
/*      */             
/* 1328 */             String ssgStripped = MilestoneHelper_2.reformat_UPC_SSG_SGC_forSave(form.getStringValue("soundscan"), "SSG", savedSelection.getIsDigital(), true);
/* 1329 */             form.getElement("soundscan").setValue(ssgStripped);
/*      */           } 
/*      */ 
/*      */           
/* 1333 */           boolean isPfmChange = false;
/* 1334 */           boolean isBomChange = false;
/*      */ 
/*      */           
/* 1337 */           if (!form.getStringValue("selectionNo").equalsIgnoreCase(form.getElement("selectionNo").getStartingValue())) {
/*      */             
/* 1339 */             isPfmChange = true;
/* 1340 */             isBomChange = true;
/*      */           } 
/*      */ 
/*      */           
/* 1344 */           String prefixStartingValue = "-1";
/* 1345 */           if (!form.getElement("prefix").getStartingValue().equals("")) {
/* 1346 */             prefixStartingValue = form.getElement("prefix").getStartingValue();
/*      */           }
/* 1348 */           if (!form.getStringValue("prefix").equalsIgnoreCase(prefixStartingValue)) {
/*      */             
/* 1350 */             isPfmChange = true;
/* 1351 */             isBomChange = true;
/*      */           } 
/*      */ 
/*      */           
/* 1355 */           if (!form.getStringValue("UPC").equalsIgnoreCase(form.getElement("UPC").getStartingValue())) {
/*      */             
/* 1357 */             isPfmChange = true;
/* 1358 */             isBomChange = true;
/*      */           } 
/*      */ 
/*      */           
/* 1362 */           if (!savedSelection.getIsDigital() && !form.getStringValue("titleId").equalsIgnoreCase(form.getElement("titleId").getStartingValue())) {
/* 1363 */             isPfmChange = true;
/*      */           }
/*      */           
/* 1366 */           if (!savedSelection.getIsDigital() && !form.getStringValue("streetDate").equalsIgnoreCase(form.getElement("streetDate").getStartingValue())) {
/*      */             
/* 1368 */             isPfmChange = true;
/* 1369 */             isBomChange = true;
/*      */           } 
/*      */ 
/*      */           
/* 1373 */           if (savedSelection.getIsDigital() && !form.getStringValue("digitalDate").equalsIgnoreCase(form.getElement("digitalDate").getStartingValue())) {
/*      */             
/* 1375 */             isPfmChange = true;
/* 1376 */             isBomChange = true;
/*      */           } 
/*      */ 
/*      */           
/* 1380 */           if (!form.getStringValue("status").equalsIgnoreCase(form.getElement("status").getStartingValue()) && !form.getStringValue("status").equalsIgnoreCase("Closed")) {
/*      */ 
/*      */             
/* 1383 */             isPfmChange = true;
/* 1384 */             isBomChange = true;
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/* 1389 */           if (!form.getStringValue("status").equalsIgnoreCase(form.getElement("status").getStartingValue()) && form.getStringValue("status").equalsIgnoreCase("Closed")) {
/*      */             
/* 1391 */             Schedule schedule = null;
/*      */             
/* 1393 */             if (savedSelection != null) {
/* 1394 */               schedule = ScheduleManager.getInstance().getSchedule(savedSelection.getSelectionID());
/*      */             }
/* 1396 */             if (schedule != null) {
/*      */               
/* 1398 */               Vector tasks = schedule.getTasks();
/* 1399 */               if (tasks != null) {
/*      */                 
/* 1401 */                 ScheduledTask task = null;
/*      */                 
/* 1403 */                 Calendar labelCmpDt = MilestoneHelper.getDate("9/9/99");
/*      */                 
/* 1405 */                 for (int i = 0; i < tasks.size(); i++) {
/*      */                   
/* 1407 */                   task = (ScheduledTask)tasks.get(i);
/*      */ 
/*      */ 
/*      */                   
/* 1411 */                   if (task.getCompletionDate() == null && !MilestoneHelper.isUml(task) && !MilestoneHelper.isEcommerce(task)) {
/* 1412 */                     ScheduleManager.getInstance().UpdateCompletionDate(task.releaseID, task.taskID, user.userId, labelCmpDt);
/*      */                   }
/* 1414 */                   task = null;
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */ 
/*      */           
/* 1421 */           if (!form.getStringValue("projectId").equalsIgnoreCase(form.getElement("projectId").getStartingValue())) {
/* 1422 */             isPfmChange = true;
/*      */           }
/*      */           
/* 1425 */           if (form.getStringValue("priceCode") != null) {
/*      */ 
/*      */             
/* 1428 */             if (form.getElement("priceCode").getStartingValue().equals("")) {
/* 1429 */               form.getElement("priceCode").setStartingValue("-1");
/*      */             }
/* 1431 */             if (!form.getStringValue("priceCode").equalsIgnoreCase(form.getElement("priceCode").getStartingValue())) {
/* 1432 */               isPfmChange = true;
/*      */             }
/*      */           } 
/*      */           
/* 1436 */           if (form.getStringValue("priceCodeDPC") != null) {
/*      */ 
/*      */             
/* 1439 */             if (form.getElement("priceCodeDPC").getStartingValue().equals("")) {
/* 1440 */               form.getElement("priceCodeDPC").setStartingValue("-1");
/*      */             }
/* 1442 */             if (!form.getStringValue("priceCodeDPC").equalsIgnoreCase(form.getElement("priceCodeDPC").getStartingValue())) {
/* 1443 */               isPfmChange = true;
/*      */             }
/*      */           } 
/*      */           
/* 1447 */           if (((FormCheckBox)form.getElement("parentalIndicator")).isChecked() != ((FormCheckBox)form.getElement("parentalIndicator")).getStartingChecked()) {
/* 1448 */             isPfmChange = true;
/*      */           }
/*      */           
/* 1451 */           if (!form.getStringValue("title").equalsIgnoreCase(form.getElement("title").getStartingValue())) {
/*      */             
/* 1453 */             isPfmChange = true;
/* 1454 */             isBomChange = true;
/*      */           } 
/*      */ 
/*      */           
/* 1458 */           if (!form.getStringValue("artistFirstName").equalsIgnoreCase(form.getElement("artistFirstName").getStartingValue())) {
/*      */             
/* 1460 */             isPfmChange = true;
/* 1461 */             isBomChange = true;
/*      */           } 
/*      */           
/* 1464 */           if (!form.getStringValue("artistLastName").equalsIgnoreCase(form.getElement("artistLastName").getStartingValue())) {
/*      */             
/* 1466 */             isPfmChange = true;
/* 1467 */             isBomChange = true;
/*      */           } 
/*      */ 
/*      */           
/* 1471 */           if (!form.getStringValue("opercompany").equalsIgnoreCase(form.getElement("opercompany").getStartingValue())) {
/* 1472 */             isPfmChange = true;
/*      */           }
/*      */           
/* 1475 */           if (!form.getStringValue("superlabel").equalsIgnoreCase(form.getElement("superlabel").getStartingValue())) {
/* 1476 */             isPfmChange = true;
/*      */           }
/*      */           
/* 1479 */           if (!form.getStringValue("sublabel").equalsIgnoreCase(form.getElement("sublabel").getStartingValue())) {
/* 1480 */             isPfmChange = true;
/*      */           }
/*      */           
/* 1483 */           if (!form.getStringValue("configcode").equalsIgnoreCase(form.getElement("configcode").getStartingValue())) {
/* 1484 */             isPfmChange = true;
/*      */           }
/*      */ 
/*      */           
/* 1488 */           if (!form.getStringValue("soundscan").equalsIgnoreCase(form.getElement("soundscan").getStartingValue())) {
/* 1489 */             isPfmChange = true;
/*      */           }
/*      */           
/* 1492 */           if (!savedSelection.getIsDigital() && ((FormCheckBox)form.getElement("parentalIndicator")).isChecked() != ((FormCheckBox)form.getElement("parentalIndicator")).getStartingChecked()) {
/* 1493 */             isPfmChange = true;
/*      */           }
/*      */           
/* 1496 */           if (!form.getStringValue("numOfUnits").equalsIgnoreCase(form.getElement("numOfUnits").getStartingValue())) {
/*      */             
/* 1498 */             isPfmChange = true;
/* 1499 */             isBomChange = true;
/*      */           } 
/*      */ 
/*      */           
/* 1503 */           if (!form.getStringValue("label").equalsIgnoreCase(form.getElement("label").getStartingValue())) {
/* 1504 */             isBomChange = true;
/*      */           }
/*      */           
/* 1507 */           if (!form.getStringValue("company").equalsIgnoreCase(form.getElement("company").getStartingValue())) {
/* 1508 */             isBomChange = true;
/*      */           }
/*      */           
/* 1511 */           if (!form.getStringValue("releaseType").equalsIgnoreCase(form.getElement("releaseType").getStartingValue())) {
/* 1512 */             isBomChange = true;
/*      */           }
/*      */ 
/*      */ 
/*      */           
/* 1517 */           if (form.getStringValue("genre").equals("-1")) {
/* 1518 */             form.getElement("genre").setValue("");
/*      */           }
/*      */           
/* 1521 */           if (!form.getStringValue("genre").equalsIgnoreCase(form.getElement("genre").getStartingValue())) {
/* 1522 */             isPfmChange = true;
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1529 */           Pfm pfm = null;
/* 1530 */           Bom bom = null;
/* 1531 */           boolean isBOMFinal = false;
/* 1532 */           boolean isPFMFinal = false;
/* 1533 */           if (savedSelection != null) {
/*      */             
/* 1535 */             pfm = SelectionManager.getInstance().getPfm(savedSelection.getSelectionID());
/* 1536 */             bom = SelectionManager.getInstance().getBom(savedSelection);
/*      */             
/* 1538 */             isBOMFinal = (bom != null && bom.getPrintOption() != null && bom.getPrintOption().equalsIgnoreCase("Final"));
/* 1539 */             isPFMFinal = (pfm != null && pfm.getPrintOption() != null && pfm.getPrintOption().equalsIgnoreCase("Final"));
/*      */             
/* 1541 */             int igaId = MilestoneHelper.getStructureId("IGA", 1);
/* 1542 */             int interscopeId = MilestoneHelper.getStructureId("Interscope", 5);
/* 1543 */             int geffenId = MilestoneHelper.getStructureId("Geffen", 5);
/* 1544 */             int amId = MilestoneHelper.getStructureId("A&M", 5);
/*      */ 
/*      */             
/* 1547 */             boolean isBomChangeSave = isBomChange;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1580 */             context.putSessionValue("pfmBomSelection", savedSelection);
/*      */           } 
/*      */ 
/*      */           
/* 1584 */           if (!isNewSelection && (isPfmChange || isBomChange) && (isPFMFinal || isBOMFinal)) {
/*      */             
/* 1586 */             if (isPFMFinal && isPfmChange) {
/* 1587 */               context.putDelivery("pfmSend", "true");
/*      */             }
/* 1589 */             if (isBOMFinal && isBomChange) {
/* 1590 */               context.putDelivery("bomSend", "true");
/*      */             }
/* 1592 */             if (isNewSelection || (
/* 1593 */               isStreetDateDifferent && isScheduleApplied) || 
/* 1594 */               isPNR)
/*      */             {
/* 1596 */               context.putSessionValue("sendToSchedule", "true");
/* 1597 */               if (isStreetDateDifferent && isScheduleApplied)
/*      */               {
/* 1599 */                 context.putSessionValue("recalc-date", "true");
/* 1600 */                 context.putSessionValue("Selection", savedSelection);
/*      */ 
/*      */               
/*      */               }
/*      */ 
/*      */             
/*      */             }
/*      */ 
/*      */           
/*      */           }
/* 1610 */           else if (isNewSelection || (isStreetDateDifferent && isScheduleApplied) || isPNR) {
/*      */             
/* 1612 */             if (isStreetDateDifferent && isScheduleApplied) {
/*      */               
/* 1614 */               context.putDelivery("recalc-date", "true");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1631 */               context.putSessionValue("Selection", savedSelection);
/*      */             } 
/*      */             
/* 1634 */             dispatcher.redispatch(context, "schedule-editor");
/* 1635 */             return true;
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/* 1640 */           if (selection == null) {
/*      */             
/* 1642 */             context.putDelivery("BlankAlertMessage", " Your changes have been saved successfully.  <br>The changes made to the selection cause the selection to no longer be part of the notepad.  <br>To view this selection, modify notepad search criteria according to the changes made.  <br>Otherwise, choose another selection from the notepad on the left.");
/* 1643 */             return goToBlank(context, form, user);
/*      */           } 
/*      */ 
/*      */           
/* 1647 */           if (selection == savedSelection)
/*      */           {
/* 1649 */             form = buildForm(context, selection, command);
/*      */           }
/*      */           else
/*      */           {
/* 1653 */             edit(dispatcher, context, command);
/* 1654 */             return true;
/*      */           }
/*      */         
/*      */         }
/*      */         else {
/*      */           
/* 1660 */           context.putDelivery("FormValidation", formValidation);
/*      */         } 
/*      */       } 
/* 1663 */       form.addElement(new FormHidden("OrderBy", "", true));
/* 1664 */       context.putDelivery("Form", form);
/* 1665 */       if (isNewSelection) {
/*      */         
/* 1667 */         if (selection.getIsDigital()) {
/* 1668 */           return context.includeJSP("digital-selection-editor.jsp");
/*      */         }
/* 1670 */         return context.includeJSP("selection-editor.jsp");
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1675 */       return edit(dispatcher, context, command);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1680 */     context.putDelivery("AlertMessage", "The record is unavailable for editing at this time.  Please refresh your screen and edit it at a later time.");
/*      */ 
/*      */     
/* 1683 */     context.putDelivery("Form", form);
/* 1684 */     if (selection.getIsDigital()) {
/* 1685 */       return context.includeJSP("digital-selection-editor.jsp");
/*      */     }
/* 1687 */     return context.includeJSP("selection-editor.jsp");
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
/* 1707 */     Vector activeResult = new Vector();
/* 1708 */     boolean resultCompany = true;
/*      */ 
/*      */     
/* 1711 */     HashMap corpHashMap = MilestoneHelper.buildActiveCorporateStructureHashMap();
/*      */ 
/*      */ 
/*      */     
/* 1715 */     if (companies != null && companies.size() > 0)
/*      */     {
/* 1717 */       for (int i = 0; i < companies.size(); i++) {
/*      */ 
/*      */         
/* 1720 */         Company company = (Company)companies.get(i);
/* 1721 */         String name = company.getName();
/* 1722 */         int structureId = company.getStructureID();
/*      */ 
/*      */ 
/*      */         
/* 1726 */         resultCompany = !corpHashMap.containsKey(new Integer(structureId));
/*      */         
/* 1728 */         if (!name.equalsIgnoreCase("UML") && 
/* 1729 */           !name.equalsIgnoreCase("Enterprise") && resultCompany)
/*      */         {
/* 1731 */           activeResult.add(company);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/* 1736 */     corpHashMap = null;
/* 1737 */     return activeResult;
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
/* 1752 */     context.removeSessionValue("searchResults");
/* 1753 */     context.removeSessionValue("selectionScreenType");
/*      */ 
/*      */ 
/*      */     
/* 1757 */     User user = (User)context.getSessionValue("user");
/* 1758 */     Selection selection = (Selection)context.getSessionValue("Selection");
/* 1759 */     Notepad notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 0);
/*      */     
/* 1761 */     if (selection.getSelectionID() > 0) {
/*      */       
/* 1763 */       boolean isDeletable = false;
/*      */       
/* 1765 */       isDeletable = SelectionManager.getInstance().deleteSelection(selection, user);
/*      */       
/* 1767 */       if (isDeletable) {
/*      */ 
/*      */         
/* 1770 */         context.removeSessionValue("Selection");
/* 1771 */         Vector contents = new Vector();
/*      */ 
/*      */         
/* 1774 */         notepad.setAllContents(null);
/* 1775 */         notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 0);
/* 1776 */         notepad.setSelected(null);
/*      */         
/* 1778 */         selection = MilestoneHelper.getScreenSelection(context);
/*      */ 
/*      */         
/* 1781 */         Cache.flushUsedLabels();
/*      */         
/* 1783 */         if (selection != null)
/*      */         {
/* 1785 */           context.putSessionValue("Selection", selection);
/*      */         }
/*      */         else
/*      */         {
/* 1789 */           Form form = null;
/*      */           
/* 1791 */           form = new Form(this.application, "selectionForm", this.application.getInfrastructure().getServletURL(), "POST");
/* 1792 */           form.addElement(new FormHidden("cmd", "selection-editor", true));
/* 1793 */           form.addElement(new FormHidden("OrderBy", "", true));
/*      */ 
/*      */           
/* 1796 */           if (context.getSessionValue("NOTEPAD_SELECTION_VISIBLE") != null) {
/* 1797 */             context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_SELECTION_VISIBLE"));
/*      */           }
/* 1799 */           Vector companies = MilestoneHelper.getUserCompanies(context);
/*      */           
/* 1801 */           addSelectionSearchElements(context, null, form, companies, true);
/* 1802 */           context.putDelivery("Form", form);
/*      */           
/* 1804 */           return context.includeJSP("blank-selection-editor.jsp");
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/* 1809 */         String alert = "Cannot delete this record.\\n There are other records that are dependent on it.";
/* 1810 */         context.putDelivery("alert-box", alert);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1815 */     if (selection.getIsDigital()) {
/*      */       
/* 1817 */       Form form = buildDigitalForm(context, selection, command);
/* 1818 */       context.putDelivery("Form", form);
/* 1819 */       return context.includeJSP("digital-selection-editor.jsp");
/*      */     } 
/*      */ 
/*      */     
/* 1823 */     Form form = buildForm(context, selection, command);
/* 1824 */     context.putDelivery("Form", form);
/* 1825 */     return context.includeJSP("selection-editor.jsp");
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
/* 1836 */     Selection selection = MilestoneHelper.getScreenSelection(context);
/* 1837 */     User user = (User)context.getSession().getAttribute("user");
/* 1838 */     int userId = user.getUserId();
/*      */     
/* 1840 */     int environmentID = -1;
/*      */     
/*      */     try {
/* 1843 */       environmentID = Integer.parseInt(context.getRequestValue("eID"));
/*      */     }
/* 1845 */     catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */     
/* 1849 */     Environment env = MilestoneHelper.getEnvironmentById(environmentID);
/* 1850 */     int familyID = -1;
/*      */     
/* 1852 */     if (env != null && env.getParentFamily() != null) {
/* 1853 */       familyID = env.getParentFamily().getStructureID();
/*      */     }
/* 1855 */     Vector releaseFamilies = ReleasingFamily.getReleasingFamilies(userId, familyID, context);
/*      */     
/* 1857 */     context.putDelivery("releasing_family_list", releaseFamilies);
/* 1858 */     return context.includeJSP("selection-get-releasing-families.jsp");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean getSelectionSearchResults(Dispatcher dispatcher, Context context, String command) {
/* 1866 */     User user = (User)context.getSessionValue("user");
/* 1867 */     if (user != null) {
/* 1868 */       user.SS_searchInitiated = true;
/*      */     }
/*      */ 
/*      */     
/* 1872 */     if (user == null || user.getPreferences() == null || user.getPreferences().getSelectionPriorCriteria() != 1) {
/* 1873 */       context.putSessionValue("ResetSelectionSortOrder", "true");
/*      */     }
/*      */ 
/*      */     
/* 1877 */     context.putSessionValue("searchElementsInit", "true");
/*      */ 
/*      */ 
/*      */     
/* 1881 */     if (SelectionManager.getInstance().getSelectionSearchResults(this.application, context)) {
/*      */ 
/*      */ 
/*      */       
/* 1885 */       Notepad notepad = MilestoneHelper.getNotepadFromSession(0, context);
/*      */ 
/*      */       
/* 1888 */       notepad.setAllContents(null);
/* 1889 */       notepad.setSelected(null);
/*      */ 
/*      */       
/* 1892 */       notepad.setMaxRecords(225);
/*      */       
/* 1894 */       String searchCommand = context.getParameter("selectionSearchCommand");
/*      */ 
/*      */       
/* 1897 */       context.putDelivery("selectionSearchResults", "true");
/*      */ 
/*      */       
/* 1900 */       user = (User)context.getSessionValue("user");
/* 1901 */       if (user != null && user.getPreferences() != null) {
/* 1902 */         user.getPreferences().getSelectionPriorCriteria();
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
/* 1926 */       if (searchCommand.equals("bom-search"))
/* 1927 */         dispatcher.redispatch(context, "bom-editor"); 
/* 1928 */       if (searchCommand.equals("pfm-search"))
/* 1929 */         dispatcher.redispatch(context, "pfm-editor"); 
/* 1930 */       if (searchCommand.equals("schedule-selection-release-search"))
/* 1931 */         dispatcher.redispatch(context, "schedule-editor"); 
/* 1932 */       if (searchCommand.equals("selection-search"))
/* 1933 */         dispatcher.redispatch(context, "selection-editor"); 
/* 1934 */       if (searchCommand.equals("selection-manufacturing-search")) {
/* 1935 */         dispatcher.redispatch(context, "selection-manufacturing-editor");
/*      */       }
/*      */ 
/*      */       
/* 1939 */       return true;
/*      */     } 
/*      */ 
/*      */     
/* 1943 */     String searchCommand = context.getParameter("selectionSearchCommand");
/*      */ 
/*      */     
/* 1946 */     context.putDelivery("selectionSearchResults", "false");
/*      */ 
/*      */     
/* 1949 */     if (searchCommand.equals("bom-search"))
/* 1950 */       dispatcher.redispatch(context, "bom-editor"); 
/* 1951 */     if (searchCommand.equals("pfm-search"))
/* 1952 */       dispatcher.redispatch(context, "pfm-editor"); 
/* 1953 */     if (searchCommand.equals("schedule-selection-release-search"))
/* 1954 */       dispatcher.redispatch(context, "schedule-editor"); 
/* 1955 */     if (searchCommand.equals("selection-search"))
/* 1956 */       dispatcher.redispatch(context, "selection-editor"); 
/* 1957 */     if (searchCommand.equals("selection-manufacturing-search")) {
/* 1958 */       dispatcher.redispatch(context, "selection-manufacturing-editor");
/*      */     }
/*      */ 
/*      */     
/* 1962 */     return context.includeJSP("selection-search-results.jsp");
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
/* 1978 */     String selectionType = "";
/* 1979 */     selectionType = (String)context.getSessionValue("selectionScreenType");
/*      */ 
/*      */     
/* 1982 */     User user = (User)context.getSessionValue("user");
/* 1983 */     Notepad notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 0);
/*      */ 
/*      */     
/* 1986 */     notepad.setAllContents(null);
/* 1987 */     notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 0);
/*      */     
/* 1989 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1994 */     Selection newSelection = new Selection();
/*      */     
/* 1996 */     if (command.equalsIgnoreCase("selection-edit-new-digital") || command.equalsIgnoreCase("selection-edit-new-digital-archie-project")) {
/* 1997 */       newSelection.setIsDigital(true);
/*      */     }
/* 1999 */     context.putSessionValue("Selection", newSelection);
/*      */     
/* 2001 */     context.putSessionValue("impactSaveVisible", "true");
/*      */ 
/*      */     
/* 2004 */     Form form = null;
/* 2005 */     if (command.equalsIgnoreCase("selection-edit-new-digital") || command.equalsIgnoreCase("selection-edit-new-digital-archie-project")) {
/* 2006 */       form = buildNewDigitalForm(context, newSelection, command);
/*      */     } else {
/* 2008 */       form = buildNewForm(context, newSelection, command);
/*      */     } 
/* 2010 */     context.putDelivery("Form", form);
/*      */     
/* 2012 */     if (command.equalsIgnoreCase("selection-edit-new-digital") || command.equalsIgnoreCase("selection-edit-new-digital-archie-project")) {
/* 2013 */       return context.includeJSP("digital-selection-editor.jsp");
/*      */     }
/* 2015 */     return context.includeJSP("selection-editor.jsp");
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
/* 2031 */     context.removeSessionValue("searchResults");
/* 2032 */     context.removeSessionValue("selectionScreenType");
/*      */     
/* 2034 */     User user = (User)context.getSessionValue("user");
/*      */     
/* 2036 */     Notepad notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 0);
/*      */     
/* 2038 */     Selection targetSelection = MilestoneHelper.getScreenSelection(context);
/*      */     
/* 2040 */     String oldSelectionNumber = "";
/*      */     
/* 2042 */     if (targetSelection.getSelectionNo() != null) {
/* 2043 */       oldSelectionNumber = targetSelection.getSelectionNo();
/*      */     }
/* 2045 */     Selection copiedSelection = null;
/*      */ 
/*      */     
/*      */     try {
/* 2049 */       copiedSelection = (Selection)targetSelection.clone();
/*      */     }
/* 2051 */     catch (CloneNotSupportedException cloneNotSupportedException) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2059 */     copiedSelection.setSelectionID(-1);
/*      */     
/* 2061 */     Vector copiedImpactDates = copiedSelection.getImpactDates();
/* 2062 */     if (copiedImpactDates != null) {
/* 2063 */       for (int i = 0; i < copiedImpactDates.size(); i++) {
/*      */         
/* 2065 */         ImpactDate impact = (ImpactDate)copiedImpactDates.get(i);
/* 2066 */         impact.setSelectionID(copiedSelection.getSelectionID());
/* 2067 */         impact.setImpactDateID(-1);
/*      */       } 
/* 2069 */       copiedSelection.setImpactDates(copiedImpactDates);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2074 */     copiedSelection.setSellCode("");
/* 2075 */     copiedSelection.setSellCodeDPC("");
/* 2076 */     copiedSelection.setPriceCode(null);
/* 2077 */     copiedSelection.setNumberOfUnits(0);
/* 2078 */     copiedSelection.setSpecialPackaging(false);
/* 2079 */     copiedSelection.setUmlContact(null);
/* 2080 */     copiedSelection.setPlant(null);
/* 2081 */     copiedSelection.setDistribution(null);
/* 2082 */     copiedSelection.setManufacturingComments("");
/* 2083 */     copiedSelection.setPoQuantity(0);
/* 2084 */     copiedSelection.setCompletedQuantity(0);
/* 2085 */     copiedSelection.setMultSelections(null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2099 */     Form form = null;
/*      */ 
/*      */     
/* 2102 */     if (!copiedSelection.getIsDigital()) {
/*      */       
/* 2104 */       copiedSelection.setNoDigitalRelease(true);
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */ 
/*      */       
/* 2113 */       copiedSelection.setNoDigitalRelease(false);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2118 */     if (command.equalsIgnoreCase("selection-edit-copy-digital")) {
/*      */ 
/*      */       
/* 2121 */       copiedSelection.setReleaseType(null);
/* 2122 */       copiedSelection.setSelectionConfig(null);
/* 2123 */       copiedSelection.setSelectionSubConfig(null);
/* 2124 */       copiedSelection.setSoundScanGrp("");
/* 2125 */       copiedSelection.setProductCategory(null);
/* 2126 */       copiedSelection.setHoldReason("");
/* 2127 */       copiedSelection.setHoldSelection(false);
/* 2128 */       copiedSelection.setTitleID("");
/* 2129 */       copiedSelection.setReleaseType((ReleaseType)SelectionManager.getLookupObject("CO", Cache.getReleaseTypes()));
/* 2130 */       copiedSelection.setDigitalRlsDate(null);
/* 2131 */       copiedSelection.setInternationalDate(null);
/* 2132 */       copiedSelection.setInternationalFlag(false);
/* 2133 */       copiedSelection.setSelectionPackaging("");
/* 2134 */       copiedSelection.setSelectionTerritory("");
/*      */ 
/*      */ 
/*      */       
/* 2138 */       if (copiedSelection.getIsDigital()) {
/*      */         
/* 2140 */         copiedSelection.setSelectionNo("");
/* 2141 */         copiedSelection.setPrefixID(null);
/* 2142 */         copiedSelection.setUpc("");
/* 2143 */         copiedSelection.setImpactDates(null);
/* 2144 */         copiedSelection.setStreetDate(null);
/* 2145 */         copiedSelection.setNewBundleFlag(true);
/* 2146 */         copiedSelection.setImpactDates(null);
/* 2147 */         copiedSelection.setImpactDate(null);
/* 2148 */         copiedSelection.setSpecialInstructions("");
/*      */       }
/*      */       else {
/*      */         
/* 2152 */         copiedSelection.setConfigCode("");
/* 2153 */         copiedSelection.setNewBundleFlag(false);
/*      */       } 
/*      */       
/* 2156 */       form = buildDigitalForm(context, copiedSelection, command);
/*      */       
/* 2158 */       if (copiedSelection.getIsDigital()) {
/*      */         
/* 2160 */         FormRadioButtonGroup newBundle = (FormRadioButtonGroup)form.getElement("newBundle");
/* 2161 */         newBundle.setValue("");
/*      */       } 
/*      */       
/* 2164 */       FormCheckBox priority = (FormCheckBox)form.getElement("priority");
/* 2165 */       priority.setValue("");
/*      */       
/* 2167 */       copiedSelection.setIsDigital(true);
/*      */       
/* 2169 */       copiedSelection.setNoDigitalRelease(false);
/*      */     }
/*      */     else {
/*      */       
/* 2173 */       copiedSelection.setConfigCode("");
/* 2174 */       copiedSelection.setComments("");
/* 2175 */       copiedSelection.setSelectionNo("");
/* 2176 */       copiedSelection.setPrefixID(null);
/* 2177 */       copiedSelection.setUpc("");
/* 2178 */       form = buildForm(context, copiedSelection, command);
/*      */     } 
/*      */ 
/*      */     
/* 2182 */     context.putSessionValue("impactSaveVisible", "true");
/* 2183 */     context.putSessionValue("Selection", copiedSelection);
/* 2184 */     context.putDelivery("old-selection-no", oldSelectionNumber);
/* 2185 */     context.putDelivery("Form", form);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2191 */     Selection updatedSelection = SelectionManager.isProjectNumberValid(copiedSelection);
/*      */     
/* 2193 */     if (updatedSelection == null) {
/*      */ 
/*      */ 
/*      */       
/* 2197 */       if (targetSelection.getIsDigital()) {
/* 2198 */         form = buildDigitalForm(context, targetSelection, "selection-editor");
/*      */       } else {
/* 2200 */         form = buildForm(context, targetSelection, "selection-editor");
/*      */       } 
/* 2202 */       context.removeSessionValue("Selection");
/* 2203 */       context.removeDelivery("Form");
/*      */       
/* 2205 */       context.putSessionValue("Selection", targetSelection);
/* 2206 */       context.putDelivery("Form", form);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2211 */       context.putDelivery("ProjectNumberMessage", 
/* 2212 */           "***************************** COPY FAILED ****************************\\nThe Project Number for this Product is NOT contained within Archimedes.\\nIf the Project Number is incorrect, please set up a NEW product.\\nIf the Project Number is correct, please contact system administrator so it can be added to Archimedes.");
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2217 */       if (targetSelection.getIsDigital()) {
/*      */         
/* 2219 */         if (targetSelection != null) {
/* 2220 */           return context.includeJSP("digital-selection-editor.jsp");
/*      */         }
/* 2222 */         return context.includeJSP("blank-selection-editor.jsp");
/*      */       } 
/*      */ 
/*      */       
/* 2226 */       if (targetSelection != null) {
/* 2227 */         return context.includeJSP("selection-editor.jsp");
/*      */       }
/* 2229 */       return context.includeJSP("blank-selection-editor.jsp");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2235 */     String diffMessage = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 2245 */       if (updatedSelection.getLabel() != null && updatedSelection.getLabel().getStructureID() != copiedSelection.getLabel().getStructureID()) {
/* 2246 */         diffMessage = String.valueOf(diffMessage) + "<b>Archimedes Label:</b> " + updatedSelection.getLabel().getName() + "<BR>";
/*      */       }
/* 2248 */       if (!updatedSelection.getOperCompany().equalsIgnoreCase(copiedSelection.getOperCompany())) {
/* 2249 */         diffMessage = String.valueOf(diffMessage) + "<b>Archimedes Operating Company:</b> " + updatedSelection.getOperCompany() + "<BR>";
/*      */       }
/* 2251 */       if (!updatedSelection.getSuperLabel().equalsIgnoreCase(copiedSelection.getSuperLabel())) {
/* 2252 */         diffMessage = String.valueOf(diffMessage) + "<b>Archimedes Super Label:</b> " + updatedSelection.getSuperLabel() + "<BR>";
/*      */       }
/* 2254 */       if (!updatedSelection.getSubLabel().equalsIgnoreCase(copiedSelection.getSubLabel())) {
/* 2255 */         diffMessage = String.valueOf(diffMessage) + "<b>Archimedes Sub Label:</b> " + updatedSelection.getSubLabel() + "<BR>";
/*      */       }
/* 2257 */       if (!updatedSelection.getImprint().equalsIgnoreCase(copiedSelection.getImprint())) {
/* 2258 */         diffMessage = String.valueOf(diffMessage) + "<b>Archimedes Imprint:</b> " + updatedSelection.getImprint() + "<BR>";
/*      */       }
/* 2260 */     } catch (Exception e) {
/*      */       
/* 2262 */       System.out.println("project number validation..." + e.toString());
/*      */     } 
/*      */     
/* 2265 */     if (diffMessage.length() > 0) {
/*      */       
/* 2267 */       context.putDelivery("DiffMessage", diffMessage);
/* 2268 */       context.putDelivery("updatedSelection", updatedSelection);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2277 */     if (command.equalsIgnoreCase("selection-edit-copy-digital")) {
/*      */       
/* 2279 */       if (copiedSelection != null) {
/* 2280 */         return context.includeJSP("digital-selection-editor.jsp");
/*      */       }
/* 2282 */       return context.includeJSP("blank-selection-editor.jsp");
/*      */     } 
/*      */ 
/*      */     
/* 2286 */     if (copiedSelection != null) {
/* 2287 */       return context.includeJSP("selection-editor.jsp");
/*      */     }
/* 2289 */     return context.includeJSP("blank-selection-editor.jsp");
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
/* 2300 */     int selectionID = -1;
/* 2301 */     User user = (User)context.getSessionValue("user");
/*      */     
/* 2303 */     Selection selection = new Selection();
/*      */ 
/*      */ 
/*      */     
/* 2307 */     Notepad notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 0);
/* 2308 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */     
/* 2310 */     selection = MilestoneHelper.getScreenSelection(context);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2315 */     boolean newFlag = SelectionManager.getInstance().getSelectionManufacturingSubDetail(selection);
/*      */ 
/*      */ 
/*      */     
/* 2319 */     int mfgAccessLevel = 0;
/*      */     
/* 2321 */     if (selection != null) {
/* 2322 */       mfgAccessLevel = getSelectionPermissions(selection, user);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2332 */     Form form = null;
/* 2333 */     if (selection != null) {
/*      */ 
/*      */       
/* 2336 */       form = buildManufacturingForm(context, selection, command, mfgAccessLevel, newFlag);
/* 2337 */       context.putDelivery("Form", form);
/* 2338 */       context.putSessionValue("Selection", selection);
/* 2339 */       int secureLevel = getSelectionMfgPermissions(selection, user);
/*      */ 
/*      */       
/* 2342 */       if (selection.getIsDigital()) {
/* 2343 */         return dispatcher.redispatch(context, "schedule-editor");
/*      */       }
/* 2345 */       return context.includeJSP("selection-manufacturing-editor.jsp");
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2350 */     form = new Form(this.application, "selectionForm", this.application.getInfrastructure().getServletURL(), "POST");
/* 2351 */     form.addElement(new FormHidden("cmd", "selection-manufacturing-editor", true));
/* 2352 */     form.addElement(new FormHidden("OrderBy", "", true));
/*      */     
/* 2354 */     if (context.getSessionValue("NOTEPAD_MFG_VISIBLE") != null) {
/* 2355 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_MFG_VISIBLE"));
/*      */     }
/*      */     
/* 2358 */     Vector companies = MilestoneHelper.getUserCompanies(context);
/*      */     
/* 2360 */     addSelectionSearchElements(context, null, form, companies, true);
/* 2361 */     context.putDelivery("Form", form);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2368 */     return context.includeJSP("blank-selection-manufacturing-editor.jsp");
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
/* 2379 */     Selection selection = (Selection)context.getSessionValue("Selection");
/*      */     
/* 2381 */     User user = (User)context.getSessionValue("user");
/*      */     
/* 2383 */     Form form = buildManufacturingForm(context, selection, command, getSelectionMfgPermissions(selection, user), false);
/*      */     
/* 2385 */     form.setValues(context);
/*      */     
/* 2387 */     String mfgComments = form.getStringValue("orderCommentHelper");
/* 2388 */     String comments = form.getStringValue("comments");
/* 2389 */     String umlContact = form.getStringValue("umlcontact");
/* 2390 */     String distribution = form.getStringValue("distribution");
/*      */     
/* 2392 */     selection.setDistribution(SelectionManager.getLookupObject(distribution, Cache.getDistributionCodes()));
/* 2393 */     selection.setManufacturingComments(mfgComments);
/* 2394 */     selection.setComments(comments);
/*      */ 
/*      */     
/* 2397 */     Vector plants = new Vector();
/* 2398 */     Vector newPlants = new Vector();
/*      */     
/* 2400 */     if (selection.getManufacturingPlants() != null) {
/* 2401 */       plants = selection.getManufacturingPlants();
/*      */     }
/* 2403 */     for (int plantCount = 0; plantCount < plants.size(); plantCount++) {
/*      */       
/* 2405 */       Plant p = (Plant)plants.get(plantCount);
/*      */       
/* 2407 */       String vendor = form.getStringValue("plant" + plantCount);
/*      */       
/* 2409 */       int poQty = 0;
/* 2410 */       String poQtyString = form.getStringValue("po_qty" + plantCount);
/*      */ 
/*      */       
/* 2413 */       if (poQtyString.indexOf(",") > -1) {
/*      */         
/* 2415 */         String newString = "";
/* 2416 */         for (int i = 0; i < poQtyString.length(); i++) {
/*      */           
/* 2418 */           if (poQtyString.charAt(i) != ',') {
/* 2419 */             newString = String.valueOf(newString) + poQtyString.charAt(i);
/*      */           }
/*      */         } 
/* 2422 */         poQtyString = newString;
/*      */       } 
/*      */ 
/*      */       
/*      */       try {
/* 2427 */         poQty = Integer.parseInt(poQtyString);
/*      */       }
/* 2429 */       catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2434 */       String completedQty = form.getStringValue("completed_qty" + plantCount);
/*      */ 
/*      */       
/* 2437 */       if (completedQty.indexOf(",") > -1) {
/*      */         
/* 2439 */         String newString = "";
/* 2440 */         for (int i = 0; i < completedQty.length(); i++) {
/*      */           
/* 2442 */           if (completedQty.charAt(i) != ',') {
/* 2443 */             newString = String.valueOf(newString) + completedQty.charAt(i);
/*      */           }
/*      */         } 
/* 2446 */         completedQty = newString;
/*      */       } 
/*      */       
/* 2449 */       int completedQtyInt = 0;
/*      */       
/*      */       try {
/* 2452 */         completedQtyInt = Integer.parseInt(completedQty);
/*      */       }
/* 2454 */       catch (NumberFormatException numberFormatException) {}
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2459 */       p.setSelectionID(selection.getSelectionNo());
/* 2460 */       p.setReleaseID(selection.getSelectionID());
/* 2461 */       p.setOrderQty(poQty);
/* 2462 */       p.setCompletedQty(completedQtyInt);
/* 2463 */       p.setPlant(SelectionManager.getLookupObject(vendor, Cache.getVendors()));
/*      */       
/* 2465 */       newPlants.add(p);
/*      */     } 
/*      */     
/* 2468 */     selection.setManufacturingPlants(newPlants);
/*      */     
/* 2470 */     boolean newFlag = false;
/* 2471 */     if (form.getStringValue("new").equals("true")) {
/* 2472 */       newFlag = true;
/*      */     }
/*      */ 
/*      */     
/* 2476 */     if (newFlag || (!newFlag && SelectionManager.getInstance().isManufacturingTimestampValid(selection))) {
/*      */       
/* 2478 */       if (umlContact != null && !umlContact.equals("")) {
/*      */         
/* 2480 */         int umlContactUserId = Integer.parseInt(umlContact);
/* 2481 */         selection.setUmlContact(UserManager.getInstance().getUser(umlContactUserId));
/*      */       } 
/*      */       
/* 2484 */       if (!form.isUnchanged()) {
/*      */         
/* 2486 */         FormValidation formValidation = form.validate();
/* 2487 */         if (formValidation.isGood()) {
/*      */           
/* 2489 */           Selection savedSelection = SelectionManager.getInstance().saveManufacturingSelection(selection, user, newFlag);
/*      */ 
/*      */ 
/*      */           
/* 2493 */           Notepad notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 0);
/* 2494 */           MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */ 
/*      */           
/* 2497 */           SelectionManager.getInstance().getSelectionManufacturingSubDetail(savedSelection);
/*      */           
/* 2499 */           String lastMfgUpdatedDateText = "";
/* 2500 */           if (savedSelection.getLastMfgUpdateDate() != null)
/* 2501 */             lastMfgUpdatedDateText = MilestoneHelper.getCustomFormatedDate(savedSelection.getLastMfgUpdateDate(), "M/d/yyyy hh:mm:ss a 'ET'"); 
/* 2502 */           context.putDelivery("lastUpdateDate", lastMfgUpdatedDateText);
/*      */           
/* 2504 */           String lastMfgUpdateUser = "";
/* 2505 */           if (savedSelection.getLastMfgUpdatingUser() != null) {
/* 2506 */             lastMfgUpdateUser = savedSelection.getLastMfgUpdatingUser().getName();
/*      */           }
/*      */ 
/*      */           
/* 2510 */           context.putDelivery("lastUpdateUser", lastMfgUpdateUser);
/*      */ 
/*      */           
/* 2513 */           notepad.setAllContents(null);
/* 2514 */           notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 0);
/* 2515 */           notepad.setSelected(savedSelection);
/*      */         }
/*      */         else {
/*      */           
/* 2519 */           context.putDelivery("FormValidation", formValidation);
/*      */         } 
/*      */       } 
/* 2522 */       form.addElement(new FormHidden("OrderBy", "", true));
/* 2523 */       context.putDelivery("Form", form);
/*      */     }
/*      */     else {
/*      */       
/* 2527 */       context.putDelivery("AlertMessage", "The record is unavailable for editing at this time.  Please refresh your screen and edit it at a later time.");
/*      */     } 
/*      */     
/* 2530 */     context.putDelivery("Form", form);
/*      */     
/* 2532 */     if (selection.getIsDigital()) {
/* 2533 */       return dispatcher.redispatch(context, "schedule-editor");
/*      */     }
/* 2535 */     return context.includeJSP("selection-manufacturing-editor.jsp");
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
/* 2546 */     Selection selection = (Selection)context.getSessionValue("Selection");
/*      */     
/* 2548 */     User user = (User)context.getSessionValue("user");
/*      */     
/* 2550 */     Vector plants = new Vector();
/*      */     
/* 2552 */     plants = selection.getManufacturingPlants();
/*      */     
/* 2554 */     Plant p = new Plant();
/*      */     
/* 2556 */     plants.add(p);
/*      */     
/* 2558 */     selection.setManufacturingPlants(plants);
/*      */     
/* 2560 */     context.putSessionValue("Selection", selection);
/*      */     
/* 2562 */     Form form = buildManufacturingForm(context, selection, command, getSelectionMfgPermissions(selection, user), false);
/* 2563 */     form.setValues(context);
/*      */     
/* 2565 */     context.putDelivery("Form", form);
/*      */     
/* 2567 */     if (selection.getIsDigital()) {
/* 2568 */       return dispatcher.redispatch(context, "schedule-editor");
/*      */     }
/* 2570 */     return context.includeJSP("selection-manufacturing-editor.jsp");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean manufacturingPlantDelete(Dispatcher dispatcher, Context context, String command) {
/* 2578 */     Selection selection = (Selection)context.getSessionValue("Selection");
/*      */     
/* 2580 */     User user = (User)context.getSessionValue("user");
/*      */     
/* 2582 */     int id = Integer.parseInt(context.getRequestValue("plantId"));
/*      */     
/* 2584 */     Vector plants = new Vector();
/*      */     
/* 2586 */     plants = selection.getManufacturingPlants();
/*      */ 
/*      */ 
/*      */     
/* 2590 */     plants.remove(id);
/*      */     
/* 2592 */     selection.setManufacturingPlants(plants);
/*      */     
/* 2594 */     context.putSessionValue("Selection", selection);
/*      */     
/* 2596 */     Form form = buildManufacturingForm(context, selection, command, getSelectionMfgPermissions(selection, user), false);
/* 2597 */     form.setValues(context);
/*      */     
/* 2599 */     context.putDelivery("Form", form);
/*      */     
/* 2601 */     if (selection.getIsDigital()) {
/* 2602 */       return dispatcher.redispatch(context, "schedule-editor");
/*      */     }
/* 2604 */     return context.includeJSP("selection-manufacturing-editor.jsp");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Form buildForm(Context context, Selection selection, String command) {
/* 2614 */     Form selectionForm = new Form(this.application, "selectionForm", 
/* 2615 */         this.application.getInfrastructure().getServletURL(), "POST");
/* 2616 */     User user = (User)context.getSession().getAttribute("user");
/* 2617 */     int userId = user.getUserId();
/*      */     
/* 2619 */     int secureLevel = getSelectionPermissions(selection, user);
/* 2620 */     setButtonVisibilities(selection, user, context, secureLevel, command);
/*      */     
/* 2622 */     boolean newFlag = (selection.getSelectionID() < 0);
/*      */     
/* 2624 */     if (newFlag) {
/* 2625 */       context.putDelivery("new-or-copy", "true");
/*      */     } else {
/* 2627 */       context.putDelivery("new-or-copy", "false");
/*      */     } 
/*      */ 
/*      */     
/* 2631 */     selectionForm.addElement(new FormHidden("cmd", command, true));
/* 2632 */     selectionForm.addElement(new FormHidden("OrderBy", "", true));
/* 2633 */     selectionForm.addElement(new FormHidden("hidTitleId", "", true));
/* 2634 */     selectionForm.addElement(new FormHidden("isFocus", "", true));
/* 2635 */     selectionForm.addElement(new FormHidden("statusHidVal", "", true));
/* 2636 */     selectionForm.addElement(new FormHidden("generateSelection", "", true));
/*      */     
/* 2638 */     Vector companies = null;
/* 2639 */     companies = MilestoneHelper.getUserCompanies(context);
/*      */     
/* 2641 */     if (selection != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2655 */       FormTextField artistFirstName = new FormTextField("artistFirstName", selection.getArtistFirstName(), false, 20, 50);
/* 2656 */       artistFirstName.setTabIndex(1);
/* 2657 */       artistFirstName.addFormEvent("onChange", "javaScript:removeSpaces(this);");
/* 2658 */       artistFirstName.setClassName("ctrlMedium");
/* 2659 */       selectionForm.addElement(artistFirstName);
/*      */ 
/*      */       
/* 2662 */       FormTextField artistLastName = new FormTextField("artistLastName", selection.getArtistLastName(), false, 20, 50);
/* 2663 */       artistLastName.setTabIndex(2);
/* 2664 */       artistLastName.addFormEvent("onChange", "javaScript:removeSpaces(this);");
/* 2665 */       artistLastName.setClassName("ctrlMedium");
/* 2666 */       selectionForm.addElement(artistLastName);
/*      */ 
/*      */       
/* 2669 */       FormTextField title = new FormTextField("title", selection.getTitle(), true, 73, 125);
/* 2670 */       title.setTabIndex(3);
/* 2671 */       title.addFormEvent("onChange", "javaScript:removeSpaces(this);");
/* 2672 */       title.setClassName("ctrlXLarge");
/* 2673 */       selectionForm.addElement(title);
/*      */ 
/*      */       
/* 2676 */       FormTextField sideATitle = new FormTextField("sideATitle", selection.getASide(), false, 20, 125);
/* 2677 */       sideATitle.setTabIndex(4);
/* 2678 */       sideATitle.setClassName("ctrlMedium");
/* 2679 */       selectionForm.addElement(sideATitle);
/*      */ 
/*      */       
/* 2682 */       FormTextField sideBTitle = new FormTextField("sideBTitle", selection.getBSide(), false, 20, 125);
/* 2683 */       sideBTitle.setTabIndex(5);
/* 2684 */       sideBTitle.setClassName("ctrlMedium");
/* 2685 */       selectionForm.addElement(sideBTitle);
/*      */ 
/*      */ 
/*      */       
/* 2689 */       String GDRSProductStatusStr = "";
/* 2690 */       DcGDRSResults dcGDRSResults = GDRSProductStatusGet(selection, selection.getCompany().getParentEnvironment().getStructureID());
/* 2691 */       GDRSProductStatusStr = dcGDRSResults.getStatus();
/* 2692 */       FormHidden GDRSProductStatus = new FormHidden("GDRSProductStatus", GDRSProductStatusStr, false);
/* 2693 */       selectionForm.addElement(GDRSProductStatus);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2699 */       String streetDateText = "";
/* 2700 */       if (selection.getStreetDate() != null)
/* 2701 */         streetDateText = MilestoneHelper.getFormatedDate(selection.getStreetDate()); 
/* 2702 */       FormTextField streetDate = new FormTextField("streetDate", streetDateText, false, 10);
/* 2703 */       streetDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
/* 2704 */       streetDate.addFormEvent("onBlur", "JavaScript:validateDate(document.forms[0].streetDate.value,this);populateNoDigitalRelease()");
/*      */       
/* 2706 */       streetDate.addFormEvent("oldValue", streetDateText);
/* 2707 */       streetDate.setTabIndex(6);
/* 2708 */       streetDate.setClassName("ctrlShort");
/* 2709 */       selectionForm.addElement(streetDate);
/*      */       
/* 2711 */       FormTextField dayType = new FormTextField("dayType", MilestoneHelper.getDayType(selection.getCalendarGroup(), selection.getStreetDate()), false, 5);
/* 2712 */       selectionForm.addElement(dayType);
/*      */ 
/*      */       
/* 2715 */       String digitalRlsDateText = "";
/* 2716 */       if (selection.getDigitalRlsDate() != null)
/* 2717 */         digitalRlsDateText = MilestoneHelper.getFormatedDate(selection.getDigitalRlsDate()); 
/* 2718 */       FormTextField drDate = new FormTextField("digitalDateDisplay", digitalRlsDateText, false, 10);
/* 2719 */       selectionForm.addElement(drDate);
/* 2720 */       FormHidden digitalDate = new FormHidden("digitalDate", digitalRlsDateText, false);
/* 2721 */       selectionForm.addElement(digitalDate);
/*      */ 
/*      */ 
/*      */       
/* 2725 */       boolean noDigitalReleaseValue = dcGDRSResults.getForceNoDigitalRelease().booleanValue() ? true : selection.getNoDigitalRelease();
/*      */ 
/*      */       
/* 2728 */       FormHidden ForceNoDigitalRelease = new FormHidden("ForceNoDigitalRelease", dcGDRSResults.getForceNoDigitalRelease().toString(), false);
/* 2729 */       selectionForm.addElement(ForceNoDigitalRelease);
/*      */       
/* 2731 */       FormCheckBox noDigitalRelease = new FormCheckBox("noDigitalRelease", "", false, noDigitalReleaseValue);
/* 2732 */       noDigitalRelease.addFormEvent("onChange", "JavaScript:noDigitalReleaseChanged();");
/*      */       
/* 2734 */       noDigitalRelease.addFormEvent("oldValue", Boolean.toString(selection.getNoDigitalRelease()));
/* 2735 */       noDigitalRelease.setTabIndex(7);
/* 2736 */       selectionForm.addElement(noDigitalRelease);
/*      */ 
/*      */       
/* 2739 */       String intDateText = "";
/* 2740 */       if (selection.getInternationalDate() != null)
/* 2741 */         intDateText = MilestoneHelper.getFormatedDate(selection.getInternationalDate()); 
/* 2742 */       FormDateField intDate = new FormDateField("internationalDate", intDateText, false, 10);
/* 2743 */       intDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
/* 2744 */       intDate.addFormEvent("onBlur", "JavaScript:validateDate(document.forms[0].internationalDate.value,this)");
/* 2745 */       intDate.setTabIndex(8);
/* 2746 */       intDate.setClassName("ctrlShort");
/* 2747 */       selectionForm.addElement(intDate);
/*      */ 
/*      */       
/* 2750 */       context.putDelivery("releaseWeek", MilestoneHelper.getReleaseWeekString(selection));
/*      */ 
/*      */       
/* 2753 */       FormDropDownMenu status = MilestoneHelper.getLookupDropDown("status", Cache.getSelectionStatusList(), SelectionManager.getLookupObjectValue(selection.getSelectionStatus()), true, false);
/*      */       
/* 2755 */       status.addFormEvent("oldValue", SelectionManager.getLookupObjectValue(selection.getSelectionStatus()));
/* 2756 */       status.setTabIndex(9);
/* 2757 */       status.setClassName("ctrlSmall");
/* 2758 */       selectionForm.addElement(status);
/*      */ 
/*      */       
/* 2761 */       boolean boolHoldReason = true;
/* 2762 */       if (selection.getHoldReason().equalsIgnoreCase("")) {
/* 2763 */         boolHoldReason = false;
/*      */       }
/* 2765 */       FormCheckBox holdIndicator = new FormCheckBox("holdIndicator", "", false, boolHoldReason);
/* 2766 */       holdIndicator.setTabIndex(10);
/* 2767 */       selectionForm.addElement(holdIndicator);
/*      */ 
/*      */       
/* 2770 */       FormTextArea holdReason = new FormTextArea("holdReason", selection.getHoldReason(), false, 2, 44, "virtual");
/* 2771 */       holdReason.addFormEvent("onBlur", "Javascript:checkField(this)");
/* 2772 */       selectionForm.addElement(holdReason);
/*      */ 
/*      */       
/* 2775 */       FormCheckBox pdIndicator = new FormCheckBox("pdIndicator", "", false, selection.getPressAndDistribution());
/* 2776 */       pdIndicator.setTabIndex(12);
/* 2777 */       selectionForm.addElement(pdIndicator);
/*      */ 
/*      */       
/* 2780 */       FormCheckBox intlFlag = new FormCheckBox("intlFlag", "", false, selection.getInternationalFlag());
/* 2781 */       intlFlag.setTabIndex(11);
/* 2782 */       selectionForm.addElement(intlFlag);
/*      */ 
/*      */       
/* 2785 */       String impactDateText = "";
/* 2786 */       if (selection.getImpactDate() != null)
/* 2787 */         impactDateText = MilestoneHelper.getFormatedDate(selection.getImpactDate()); 
/* 2788 */       FormDateField impactDate = new FormDateField("impactdate", impactDateText, false, 13);
/* 2789 */       impactDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
/* 2790 */       impactDate.addFormEvent("onBlur", "JavaScript:validateDate(document.forms[0].impactdate.value,this)");
/* 2791 */       impactDate.setTabIndex(13);
/* 2792 */       impactDate.setClassName("ctrlShort");
/* 2793 */       selectionForm.addElement(impactDate);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2798 */       Vector releaseFamilies = ReleasingFamily.getReleasingFamilies(userId, selection.getFamily().getStructureID(), context);
/* 2799 */       FormDropDownMenu releasingFamily = MilestoneHelper.getReleasingFamilyDropDown("releasingFamily", Integer.toString(selection.getReleaseFamilyId()), releaseFamilies, true, selection);
/*      */       
/* 2801 */       releasingFamily.addFormEvent("onChange", "ReleasingFamilyChange(this)");
/* 2802 */       selectionForm.addElement(releasingFamily);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2807 */       String evironmentId = "";
/* 2808 */       String environmentName = "";
/* 2809 */       Vector evironmentList = filterSelectionEnvironments(companies);
/* 2810 */       if (selection.getCompany().getParentEnvironment() != null) {
/*      */         
/* 2812 */         evironmentId = Integer.toString(selection.getCompany().getParentEnvironment().getStructureID());
/* 2813 */         environmentName = selection.getCompany().getParentEnvironment().getName();
/*      */       } else {
/*      */         
/* 2816 */         evironmentId = "";
/* 2817 */       }  FormHidden evironment = new FormHidden("environment", evironmentId, false);
/* 2818 */       FormHidden evironmentLabel = new FormHidden("environment", evironmentId, false);
/* 2819 */       evironment.setTabIndex(14);
/* 2820 */       evironment.setDisplayName(environmentName);
/*      */ 
/*      */ 
/*      */       
/* 2824 */       selectionForm.addElement(evironment);
/*      */ 
/*      */       
/* 2827 */       String companyId = "";
/* 2828 */       String companyName = "";
/*      */ 
/*      */       
/* 2831 */       if (selection.getCompany() != null) {
/* 2832 */         companyId = Integer.toString(selection.getCompany().getStructureID());
/* 2833 */         companyName = selection.getCompany().getName();
/*      */       } 
/*      */       
/* 2836 */       FormHidden company = new FormHidden("company", companyId, false);
/*      */       
/* 2838 */       company.setTabIndex(15);
/* 2839 */       company.setDisplayName(companyName);
/*      */ 
/*      */ 
/*      */       
/* 2843 */       selectionForm.addElement(company);
/*      */ 
/*      */       
/* 2846 */       String divisionId = "";
/* 2847 */       String divisionName = "";
/* 2848 */       if (selection.getDivision() != null) {
/* 2849 */         divisionId = Integer.toString(selection.getDivision().getStructureID());
/* 2850 */         divisionName = selection.getDivision().getName();
/*      */       } else {
/*      */         
/* 2853 */         divisionId = "";
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2863 */       FormHidden division = new FormHidden("division", divisionId, false);
/*      */       
/* 2865 */       division.setTabIndex(16);
/* 2866 */       division.setDisplayName(divisionName);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2871 */       selectionForm.addElement(division);
/*      */ 
/*      */       
/* 2874 */       String labelId = "";
/* 2875 */       String labelName = "";
/* 2876 */       if (selection.getLabel() != null) {
/* 2877 */         labelId = Integer.toString(selection.getLabel().getStructureID());
/* 2878 */         labelName = selection.getLabel().getName();
/*      */       } else {
/* 2880 */         labelId = "";
/*      */       } 
/* 2882 */       FormHidden label = new FormHidden("label", labelId, false);
/* 2883 */       label.setTabIndex(17);
/* 2884 */       label.setDisplayName(labelName);
/* 2885 */       selectionForm.addElement(label);
/*      */ 
/*      */ 
/*      */       
/* 2889 */       if (selection.getOperCompany().equals("***")) {
/* 2890 */         FormHidden opercompany = new FormHidden("opercompany", "***", false);
/* 2891 */         opercompany.setDisplayName("***");
/* 2892 */         opercompany.setTabIndex(18);
/* 2893 */         selectionForm.addElement(opercompany);
/*      */       } else {
/* 2895 */         LookupObject oc = MilestoneHelper.getLookupObject(selection
/* 2896 */             .getOperCompany(), Cache.getOperatingCompanies());
/* 2897 */         String ocAbbr = "";
/* 2898 */         String ocName = "";
/*      */ 
/*      */ 
/*      */         
/* 2902 */         if (oc == null) {
/* 2903 */           ocAbbr = selection.getOperCompany();
/*      */         } else {
/* 2905 */           if (oc != null && oc.getAbbreviation() != null)
/* 2906 */             ocAbbr = oc.getAbbreviation(); 
/* 2907 */           if (oc != null && oc.getName() != null) {
/* 2908 */             ocName = ":" + oc.getName();
/*      */           }
/*      */         } 
/* 2911 */         FormHidden opercompany = new FormHidden("opercompany", ocAbbr, false);
/* 2912 */         opercompany.setDisplayName(String.valueOf(ocAbbr) + ocName);
/*      */         
/* 2914 */         if (ocAbbr.equals("ZZ"))
/* 2915 */           opercompany.setDisplayName(ocAbbr); 
/* 2916 */         opercompany.setTabIndex(18);
/* 2917 */         selectionForm.addElement(opercompany);
/*      */       } 
/*      */ 
/*      */       
/* 2921 */       FormHidden superlabel = new FormHidden("superlabel", selection.getSuperLabel(), false);
/* 2922 */       superlabel.setTabIndex(19);
/* 2923 */       superlabel.setClassName("ctrlShort");
/* 2924 */       superlabel.addFormEvent("onBlur", "javascript:this.value=this.value.toUpperCase();");
/* 2925 */       selectionForm.addElement(superlabel);
/*      */ 
/*      */       
/* 2928 */       FormHidden sublabel = new FormHidden("sublabel", selection.getSubLabel(), false);
/* 2929 */       sublabel.setTabIndex(20);
/* 2930 */       sublabel.setClassName("ctrlShort");
/* 2931 */       sublabel.addFormEvent("onBlur", "javascript:this.value=this.value.toUpperCase();");
/* 2932 */       selectionForm.addElement(sublabel);
/*      */ 
/*      */       
/* 2935 */       FormTextField imprint = new FormTextField("imprint", selection.getImprint(), false, 50);
/* 2936 */       imprint.setTabIndex(21);
/* 2937 */       imprint.setClassName("ctrlMedium");
/* 2938 */       imprint.addFormEvent("onChange", "javaScript:removeSpaces(this);");
/* 2939 */       selectionForm.addElement(imprint);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2944 */       FormDropDownMenu configcode = MilestoneHelper.getPfmLookupDropDown("configcode", MilestoneHelper.getConfigCodes(0), selection.getConfigCode(), false, true);
/* 2945 */       configcode.setTabIndex(21);
/*      */       
/* 2947 */       configcode.addFormEvent("onKeyPress", "scanKey(window.event.keyCode, 'document.all.configcode.options', getMaxLength(document.all.configcode.options))");
/*      */       
/* 2949 */       if (!newFlag) {
/* 2950 */         configcode.addFormEvent("onChange", "setNoDigitalRelease(this);");
/*      */       }
/*      */       
/* 2953 */       selectionForm.addElement(configcode);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2960 */       FormHidden projectId = new FormHidden("projectId", String.valueOf(selection.getProjectID()), false);
/* 2961 */       projectId.setTabIndex(22);
/* 2962 */       projectId.setDisplayName(String.valueOf(selection.getProjectID()));
/* 2963 */       projectId.setClassName("ctrlMedium");
/* 2964 */       selectionForm.addElement(projectId);
/*      */ 
/*      */ 
/*      */       
/* 2968 */       FormTextField gridNumber = new FormTextField("gridNumber", selection.getGridNumber(), false, 50);
/* 2969 */       gridNumber.setTabIndex(25);
/*      */       
/* 2971 */       gridNumber.setEnabled(true);
/* 2972 */       selectionForm.addElement(gridNumber);
/*      */ 
/*      */ 
/*      */       
/* 2976 */       FormTextField upc = new FormTextField("UPC", selection.getUpc(), false, 17, 20);
/* 2977 */       upc.setTabIndex(23);
/* 2978 */       upc.setClassName("ctrlMedium");
/*      */ 
/*      */       
/* 2981 */       upc.addFormEvent("onKeyPress", "return checkNumbersOnly(window.event.keyCode);");
/*      */ 
/*      */ 
/*      */       
/* 2985 */       selectionForm.addElement(upc);
/*      */ 
/*      */       
/* 2988 */       FormTextField soundscan = new FormTextField("soundscan", selection.getSoundScanGrp(), false, 17, 20);
/* 2989 */       soundscan.setTabIndex(24);
/* 2990 */       soundscan.setClassName("ctrlMedium");
/*      */ 
/*      */       
/* 2993 */       soundscan.addFormEvent("onKeyPress", "return checkNumbersOnly(window.event.keyCode);");
/*      */ 
/*      */ 
/*      */       
/* 2997 */       selectionForm.addElement(soundscan);
/*      */ 
/*      */       
/* 3000 */       FormDropDownMenu prefix = MilestoneHelper.getPrefixCodeDropDown(userId, "prefix", Cache.getPrefixCodes(), SelectionManager.getLookupObjectValue(selection.getPrefixID()), true, context);
/* 3001 */       prefix.setTabIndex(25);
/* 3002 */       prefix.setClassName("ctrlShort");
/* 3003 */       selectionForm.addElement(prefix);
/*      */ 
/*      */       
/* 3006 */       FormTextField selectionNo = new FormTextField("selectionNo", String.valueOf(selection.getSelectionNo()), false, 20, 20);
/* 3007 */       selectionNo.setTabIndex(26);
/* 3008 */       selectionNo.addFormEvent("onChange", "javaScript:removeSpaces(this);");
/* 3009 */       selectionNo.setClassName("ctrlMedium");
/* 3010 */       selectionForm.addElement(selectionNo);
/*      */ 
/*      */       
/* 3013 */       FormTextField titleId = new FormTextField("titleId", String.valueOf(selection.getTitleID()), false, 13, 24);
/* 3014 */       titleId.setClassName("ctrlMedium");
/*      */       
/* 3016 */       titleId.setTabIndex(27);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3022 */       selectionForm.addElement(titleId);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3029 */       FormDropDownMenu productLine = MilestoneHelper.getLookupDropDown("productLine", Cache.getProductCategories(0), SelectionManager.getLookupObjectValue(selection.getProductCategory()), true, true);
/* 3030 */       productLine.setTabIndex(28);
/* 3031 */       productLine.setClassName("ctrlMedium");
/* 3032 */       selectionForm.addElement(productLine);
/*      */ 
/*      */       
/* 3035 */       FormDropDownMenu releaseType = MilestoneHelper.getLookupDropDown("releaseType", Cache.getReleaseTypes(), SelectionManager.getLookupObjectValue(selection.getReleaseType()), true, newFlag);
/* 3036 */       releaseType.setTabIndex(29);
/* 3037 */       releaseType.setClassName("ctrlMedium");
/* 3038 */       releaseType.addFormEvent("onChange", "releaseTypeChanged()");
/* 3039 */       selectionForm.addElement(releaseType);
/*      */ 
/*      */       
/* 3042 */       String configValue = "";
/* 3043 */       if (selection.getSelectionConfig() != null) configValue = selection.getSelectionConfig().getSelectionConfigurationAbbreviation(); 
/* 3044 */       FormDropDownMenu configuration = MilestoneHelper.getSelectionConfigurationDropDown("configuration", configValue, true, 0);
/* 3045 */       configuration.setTabIndex(30);
/*      */       
/* 3047 */       configuration.addFormEvent("onChange", "buildSubConfigs(this.selectedIndex)");
/* 3048 */       selectionForm.addElement(configuration);
/*      */ 
/*      */ 
/*      */       
/* 3052 */       String subConfigValue = "";
/* 3053 */       if (selection.getSelectionSubConfig() != null) subConfigValue = selection.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation(); 
/* 3054 */       FormDropDownMenu subConfiguration = MilestoneHelper.getSelectionSubConfigurationDropDown("subConfiguration", selection.getSelectionConfig(), subConfigValue, true);
/* 3055 */       subConfiguration.addFormEvent("onChange", "isSubConfigParent(this.selectedIndex)");
/* 3056 */       subConfiguration.setTabIndex(31);
/*      */       
/* 3058 */       selectionForm.addElement(subConfiguration);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3065 */       FormTextField test = new FormTextField("test", "", false, 8, 8);
/* 3066 */       test.setTabIndex(32);
/* 3067 */       test.setClassName("ctrlShort");
/* 3068 */       test.addFormEvent("onChange", "javaScript:clickSell(this,false);");
/* 3069 */       selectionForm.addElement(test);
/*      */ 
/*      */       
/* 3072 */       String sellCode = "";
/* 3073 */       if (selection.getSellCode() != null)
/* 3074 */         sellCode = selection.getSellCode(); 
/* 3075 */       FormDropDownMenu priceCode = new FormDropDownMenu("priceCode", sellCode, "-1" + getSellCodesString(), "&nbsp;" + getSellCodesString(), false);
/* 3076 */       priceCode.setTabIndex(33);
/* 3077 */       priceCode.setClassName("ctrlSmall");
/* 3078 */       selectionForm.addElement(priceCode);
/*      */ 
/*      */       
/* 3081 */       FormTextField testDPC = new FormTextField("testDPC", "", false, 8, 8);
/* 3082 */       testDPC.setTabIndex(39);
/* 3083 */       testDPC.setClassName("ctrlShort");
/* 3084 */       testDPC.addFormEvent("onChange", "javaScript:clickSellDPC(this);");
/* 3085 */       selectionForm.addElement(testDPC);
/*      */ 
/*      */       
/* 3088 */       String sellCodeDPC = "";
/* 3089 */       if (selection.getSellCodeDPC() != null)
/* 3090 */         sellCodeDPC = selection.getSellCodeDPC(); 
/* 3091 */       FormDropDownMenu priceCodeDPC = new FormDropDownMenu("priceCodeDPC", sellCodeDPC, "-1" + getSellCodesStringDPC(), "&nbsp;" + getSellCodesStringDPC(), false);
/* 3092 */       priceCodeDPC.setTabIndex(39);
/* 3093 */       priceCodeDPC.setClassName("ctrlSmall");
/* 3094 */       selectionForm.addElement(priceCodeDPC);
/*      */ 
/*      */       
/* 3097 */       String numberOfUnits = "0";
/*      */       
/* 3099 */       if (selection.getNumberOfUnits() > 0) {
/* 3100 */         numberOfUnits = Integer.toString(selection.getNumberOfUnits());
/*      */       }
/* 3102 */       FormTextField numOfUnits = new FormTextField("numOfUnits", numberOfUnits, false, 10, 10);
/* 3103 */       numOfUnits.setTabIndex(34);
/* 3104 */       numOfUnits.setClassName("ctrlShort");
/* 3105 */       selectionForm.addElement(numOfUnits);
/*      */ 
/*      */ 
/*      */       
/* 3109 */       User labelUserContact = selection.getLabelContact();
/* 3110 */       Vector labelContacts = SelectionManager.getLabelContacts(selection);
/* 3111 */       FormDropDownMenu contactList = MilestoneHelper.getContactsDropDown(context, "contactlist", labelContacts, labelUserContact, true);
/* 3112 */       contactList.setTabIndex(35);
/* 3113 */       contactList.setClassName("ctrlMedium");
/* 3114 */       selectionForm.addElement(contactList);
/*      */ 
/*      */       
/* 3117 */       FormTextField contact = new FormTextField("contact", selection.getOtherContact(), false, 14, 30);
/* 3118 */       contact.setTabIndex(36);
/* 3119 */       contact.setClassName("ctrlMedium");
/* 3120 */       selectionForm.addElement(contact);
/*      */ 
/*      */       
/* 3123 */       FormCheckBox parentalIndicator = new FormCheckBox("parentalIndicator", "", false, selection.getParentalGuidance());
/* 3124 */       parentalIndicator.setTabIndex(37);
/* 3125 */       selectionForm.addElement(parentalIndicator);
/*      */ 
/*      */       
/* 3128 */       FormCheckBox specPkgIndicator = new FormCheckBox("specialPkgIndicator", "", false, selection.getSpecialPackaging());
/* 3129 */       specPkgIndicator.addFormEvent("onDblClick", "document.forms[0].PackagingHelper.value=document.forms[0].package.value;togglePackaging();");
/* 3130 */       specPkgIndicator.setTabIndex(38);
/* 3131 */       selectionForm.addElement(specPkgIndicator);
/*      */ 
/*      */       
/* 3134 */       FormTextField pkg = new FormTextField("package", selection.getSelectionPackaging(), false, 13, 100);
/* 3135 */       pkg.setTabIndex(39);
/* 3136 */       pkg.setClassName("ctrlMedium");
/* 3137 */       pkg.addFormEvent("onDblClick", "document.forms[0].PackagingHelper.value=document.forms[0].package.value;togglePackaging();");
/* 3138 */       selectionForm.addElement(pkg);
/*      */ 
/*      */       
/* 3141 */       FormDropDownMenu genre = MilestoneHelper.getPfmLookupDropDown("genre", Cache.getMusicTypes(), SelectionManager.getLookupObjectValue(selection.getGenre()), false, true);
/* 3142 */       genre.setTabIndex(40);
/* 3143 */       genre.setId("music_type");
/*      */       
/* 3145 */       selectionForm.addElement(genre);
/*      */ 
/*      */       
/* 3148 */       FormTextField territory = new FormTextField("territory", selection.getSelectionTerritory(), false, 13, 255);
/* 3149 */       territory.setTabIndex(41);
/* 3150 */       territory.setClassName("ctrlMedium");
/* 3151 */       territory.addFormEvent("onDblClick", "document.forms[0].TerritoryHelper.value=document.forms[0].territory.value;toggleTerritory();");
/* 3152 */       selectionForm.addElement(territory);
/*      */ 
/*      */       
/* 3155 */       FormHidden productionGroupCode = new FormHidden("productGroupCode", selection.getProductionGroupCode(), false);
/* 3156 */       productionGroupCode.setTabIndex(42);
/* 3157 */       productionGroupCode.setDisplayName(selection.getProductionGroupCode());
/* 3158 */       productionGroupCode.setClassName("ctrlMedium");
/* 3159 */       selectionForm.addElement(productionGroupCode);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3166 */       String lastStreetDateText = "";
/* 3167 */       if (selection.getLastStreetUpdateDate() != null)
/* 3168 */         lastStreetDateText = MilestoneHelper.getFormatedDate(selection.getLastStreetUpdateDate()); 
/* 3169 */       FormTextField lastStreetUpdatedDate = new FormTextField("laststreetupdateddate", lastStreetDateText, false, 13);
/* 3170 */       selectionForm.addElement(lastStreetUpdatedDate);
/*      */ 
/*      */ 
/*      */       
/* 3174 */       String lastUpdatedDateText = "";
/* 3175 */       if (selection.getLastUpdateDate() != null)
/* 3176 */         lastUpdatedDateText = MilestoneHelper.getCustomFormatedDate(selection.getLastUpdateDate(), "M/d/yyyy hh:mm:ss a 'ET'"); 
/* 3177 */       FormTextField lastUpdatedDate = new FormTextField("lastupdateddate", lastUpdatedDateText, false, 50);
/* 3178 */       selectionForm.addElement(lastUpdatedDate);
/*      */ 
/*      */       
/* 3181 */       String originDateText = "";
/* 3182 */       if (selection.getOriginDate() != null)
/* 3183 */         originDateText = MilestoneHelper.getFormatedDate(selection.getOriginDate()); 
/* 3184 */       FormTextField originDate = new FormTextField("origindate", originDateText, false, 13);
/* 3185 */       selectionForm.addElement(originDate);
/*      */ 
/*      */       
/* 3188 */       String archieDateText = "";
/* 3189 */       if (selection.getArchieDate() != null)
/* 3190 */         archieDateText = MilestoneHelper.getCustomFormatedDate(selection.getArchieDate(), "M/d/yyyy hh:mm:ss a 'ET'"); 
/* 3191 */       FormTextField archieDate = new FormTextField("archieDate", archieDateText, false, 13);
/* 3192 */       selectionForm.addElement(archieDate);
/*      */ 
/*      */       
/* 3195 */       String autoCloseDateText = "";
/* 3196 */       if (selection.getAutoCloseDate() != null)
/* 3197 */         autoCloseDateText = MilestoneHelper.getCustomFormatedDate(selection.getAutoCloseDate(), "M/d/yyyy hh:mm:ss a 'ET'"); 
/* 3198 */       FormTextField autoCloseDate = new FormTextField("autoCloseDate", autoCloseDateText, false, 40);
/* 3199 */       selectionForm.addElement(autoCloseDate);
/*      */ 
/*      */       
/* 3202 */       String lastLegacyUpdateDateText = "";
/* 3203 */       if (selection.getLastLegacyUpdateDate() != null)
/* 3204 */         lastLegacyUpdateDateText = MilestoneHelper.getCustomFormatedDate(selection.getLastLegacyUpdateDate(), "M/d/yyyy hh:mm:ss a 'ET'"); 
/* 3205 */       FormTextField lastLegacyUpdateDate = new FormTextField("lastLegacyUpdateDate", lastLegacyUpdateDateText, false, 40);
/* 3206 */       selectionForm.addElement(lastLegacyUpdateDate);
/*      */ 
/*      */       
/* 3209 */       FormTextArea packagingHelper = new FormTextArea("PackagingHelper", selection.getSelectionPackaging(), false, 2, 44, "virtual");
/* 3210 */       selectionForm.addElement(packagingHelper);
/*      */ 
/*      */       
/* 3213 */       FormTextArea territoryHelper = new FormTextArea("TerritoryHelper", selection.getSelectionTerritory(), false, 2, 44, "virtual");
/* 3214 */       selectionForm.addElement(territoryHelper);
/*      */ 
/*      */       
/* 3217 */       FormTextArea comments = new FormTextArea("comments", selection.getSelectionComments(), false, 6, 44, "virtual");
/* 3218 */       comments.addFormEvent("onBlur", "Javascript:checkField(this)");
/* 3219 */       selectionForm.addElement(comments);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3228 */     addSelectionSearchElements(context, selection, selectionForm, companies, true);
/*      */ 
/*      */     
/* 3231 */     if (context.getSessionValue("NOTEPAD_SELECTION_VISIBLE") != null) {
/* 3232 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_SELECTION_VISIBLE"));
/*      */     }
/* 3234 */     boolean isParent = false;
/*      */     
/* 3236 */     if (selection.getSelectionSubConfig() != null) {
/* 3237 */       isParent = selection.getSelectionSubConfig().isParent();
/*      */     }
/* 3239 */     context.putDelivery("is-parent", String.valueOf(isParent));
/* 3240 */     context.putDelivery("old-selection-no", selection.getSelectionNo());
/*      */     
/* 3242 */     String price = "0.00";
/* 3243 */     if (selection.getPriceCode() != null && 
/* 3244 */       selection.getPriceCode().getTotalCost() > 0.0F) {
/* 3245 */       price = MilestoneHelper.formatDollarPrice(selection.getPriceCode().getTotalCost());
/*      */     }
/* 3247 */     context.putDelivery("price", price);
/*      */     
/* 3249 */     String lastUpdateUser = "";
/* 3250 */     if (selection.getLastUpdatingUser() != null)
/* 3251 */       lastUpdateUser = selection.getLastUpdatingUser().getName(); 
/* 3252 */     context.putDelivery("lastUpdateUser", lastUpdateUser);
/*      */     
/* 3254 */     return selectionForm;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Form buildNewForm(Context context, Selection selection, String command) {
/* 3264 */     Vector projectList = (Vector)context.getSessionValue("searchResults");
/*      */     
/* 3266 */     String resultsIndex = (String)context.getSessionValue("selectionScreenTypeIndex");
/*      */ 
/*      */ 
/*      */     
/* 3270 */     ProjectSearch selectedProject = null;
/* 3271 */     if (resultsIndex != null) {
/* 3272 */       selectedProject = (ProjectSearch)projectList.elementAt(Integer.parseInt(resultsIndex));
/*      */     } else {
/* 3274 */       selectedProject = new ProjectSearch();
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 3279 */     context.removeSessionValue("selectionScreenType");
/* 3280 */     context.removeSessionValue("searchResults");
/* 3281 */     context.removeSessionValue("selectionScreenTypeIndex");
/*      */     
/* 3283 */     Form selectionForm = new Form(this.application, "selectionForm", 
/* 3284 */         this.application.getInfrastructure().getServletURL(), "POST");
/*      */     
/* 3286 */     User user = (User)context.getSession().getAttribute("user");
/* 3287 */     int userId = user.getUserId();
/*      */ 
/*      */     
/* 3290 */     int secureLevel = getSelectionPermissions(selection, user);
/* 3291 */     setButtonVisibilities(selection, user, context, secureLevel, command);
/*      */ 
/*      */     
/* 3294 */     selectionForm.addElement(new FormHidden("cmd", "selection-edit-new", true));
/* 3295 */     selectionForm.addElement(new FormHidden("OrderBy", "", true));
/* 3296 */     selectionForm.addElement(new FormHidden("hidTitleId", "", true));
/* 3297 */     selectionForm.addElement(new FormHidden("isFocus", "", true));
/* 3298 */     selectionForm.addElement(new FormHidden("statusHidVal", "", true));
/* 3299 */     selectionForm.addElement(new FormHidden("generateSelection", "", true));
/*      */     
/* 3301 */     selectionForm.addElement(new FormHidden("GDRSProductStatus", "", false));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3326 */     String strArtistFirstName = (selectedProject.getArtistFirstName() != null) ? selectedProject.getArtistFirstName() : "";
/* 3327 */     FormTextField artistFirstName = new FormTextField("artistFirstName", strArtistFirstName, false, 20, 50);
/* 3328 */     artistFirstName.setTabIndex(1);
/* 3329 */     artistFirstName.setClassName("ctrlMedium");
/* 3330 */     artistFirstName.addFormEvent("onChange", "javaScript:removeSpaces(this);");
/* 3331 */     selectionForm.addElement(artistFirstName);
/*      */ 
/*      */     
/* 3334 */     String strArtistLastName = (selectedProject.getArtistLastName() != null) ? selectedProject.getArtistLastName() : "";
/* 3335 */     FormTextField artistLastName = new FormTextField("artistLastName", strArtistLastName, false, 20, 50);
/* 3336 */     artistLastName.setTabIndex(2);
/* 3337 */     artistLastName.setClassName("ctrlMedium");
/* 3338 */     artistLastName.addFormEvent("onChange", "javaScript:removeSpaces(this);");
/* 3339 */     selectionForm.addElement(artistLastName);
/*      */ 
/*      */     
/* 3342 */     String strTitle = (selectedProject.getTitle() != null) ? selectedProject.getTitle() : "";
/* 3343 */     FormTextField title = new FormTextField("title", strTitle, true, 73, 125);
/* 3344 */     title.setTabIndex(3);
/* 3345 */     title.setClassName("ctrlXLarge");
/* 3346 */     title.addFormEvent("onChange", "javaScript:removeSpaces(this);");
/* 3347 */     selectionForm.addElement(title);
/*      */ 
/*      */     
/* 3350 */     FormTextField sideATitle = new FormTextField("sideATitle", "", false, 20, 125);
/* 3351 */     sideATitle.setTabIndex(4);
/* 3352 */     sideATitle.setClassName("ctrlMedium");
/* 3353 */     selectionForm.addElement(sideATitle);
/*      */ 
/*      */     
/* 3356 */     FormTextField sideBTitle = new FormTextField("sideBTitle", "", false, 20, 125);
/* 3357 */     sideBTitle.setTabIndex(5);
/* 3358 */     sideBTitle.setClassName("ctrlMedium");
/* 3359 */     selectionForm.addElement(sideBTitle);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3364 */     FormTextField streetDate = new FormTextField("streetDate", "", false, 10);
/* 3365 */     streetDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
/* 3366 */     streetDate.addFormEvent("onBlur", "JavaScript:validateDate(document.forms[0].streetDate.value,this);populateNoDigitalRelease();");
/*      */     
/* 3368 */     streetDate.addFormEvent("oldValue", "");
/* 3369 */     streetDate.setTabIndex(6);
/* 3370 */     streetDate.setClassName("ctrlShort");
/* 3371 */     selectionForm.addElement(streetDate);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3376 */     FormTextField drDate = new FormTextField("digitalDateDisplay", "", false, 10);
/* 3377 */     selectionForm.addElement(drDate);
/* 3378 */     FormHidden digitalDate = new FormHidden("digitalDate", "", false);
/* 3379 */     selectionForm.addElement(digitalDate);
/*      */ 
/*      */     
/* 3382 */     DcGDRSResults dcGDRSResults = GDRSProductStatusGet(selection, selectedProject.getMSEnvironmentId());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3399 */     boolean IsDefaultChecked = true;
/* 3400 */     FormHidden ForceNoDigitalRelease = new FormHidden("ForceNoDigitalRelease", "true", false);
/*      */     
/* 3402 */     selectionForm.addElement(ForceNoDigitalRelease);
/*      */     
/* 3404 */     FormCheckBox noDigitalRelease = new FormCheckBox("noDigitalRelease", "", false, IsDefaultChecked);
/* 3405 */     noDigitalRelease.addFormEvent("onChange", "JavaScript:noDigitalReleaseChanged();");
/*      */     
/* 3407 */     noDigitalRelease.addFormEvent("oldValue", "");
/* 3408 */     noDigitalRelease.setTabIndex(7);
/* 3409 */     selectionForm.addElement(noDigitalRelease);
/*      */ 
/*      */     
/* 3412 */     FormDateField intDate = new FormDateField("internationalDate", "", false, 10);
/* 3413 */     intDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
/* 3414 */     intDate.addFormEvent("onBlur", "JavaScript:validateDate(document.forms[0].internationalDate.value,this)");
/* 3415 */     intDate.setTabIndex(8);
/* 3416 */     intDate.setClassName("ctrlShort");
/* 3417 */     selectionForm.addElement(intDate);
/*      */ 
/*      */     
/* 3420 */     FormDropDownMenu status = MilestoneHelper.getLookupDropDown("status", Cache.getSelectionStatusList(), "Active", true, false);
/*      */     
/* 3422 */     status.addFormEvent("oldValue", "Active");
/* 3423 */     status.setTabIndex(9);
/* 3424 */     status.setClassName("ctrlSmall");
/* 3425 */     selectionForm.addElement(status);
/*      */ 
/*      */     
/* 3428 */     FormCheckBox holdIndicator = new FormCheckBox("holdIndicator", "", false, false);
/* 3429 */     holdIndicator.setTabIndex(10);
/* 3430 */     selectionForm.addElement(holdIndicator);
/*      */ 
/*      */     
/* 3433 */     FormTextArea holdReason = new FormTextArea("holdReason", "", false, 2, 44, "virtual");
/* 3434 */     holdReason.addFormEvent("onBlur", "Javascript:checkField(this)");
/* 3435 */     selectionForm.addElement(holdReason);
/*      */ 
/*      */     
/* 3438 */     int pd_indicator = selectedProject.getPD_Indicator();
/* 3439 */     boolean pdBool = false;
/* 3440 */     if (pd_indicator == 1) {
/* 3441 */       pdBool = true;
/*      */     }
/* 3443 */     FormCheckBox pdIndicator = new FormCheckBox("pdIndicator", "", false, pdBool);
/* 3444 */     pdIndicator.setTabIndex(12);
/* 3445 */     selectionForm.addElement(pdIndicator);
/*      */ 
/*      */     
/* 3448 */     FormCheckBox intlFlag = new FormCheckBox("intlFlag", "", false, false);
/* 3449 */     intlFlag.setTabIndex(11);
/* 3450 */     selectionForm.addElement(intlFlag);
/*      */ 
/*      */     
/* 3453 */     FormDateField impactDate = new FormDateField("impactdate", "", false, 13);
/* 3454 */     impactDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
/* 3455 */     impactDate.addFormEvent("onBlur", "JavaScript:validateDate(document.forms[0].impactdate.value,this)");
/* 3456 */     impactDate.setTabIndex(13);
/* 3457 */     impactDate.setClassName("ctrlShort");
/* 3458 */     selectionForm.addElement(impactDate);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3463 */     Vector releaseFamilies = ReleasingFamily.getReleasingFamilies(userId, selectedProject.getMSFamilyId(), context);
/* 3464 */     ReleasingFamily defaultReleasingFamily = ReleasingFamily.getDefaultReleasingFamily(userId, selectedProject.getMSFamilyId(), context);
/* 3465 */     FormDropDownMenu releasingFamily = MilestoneHelper.getReleasingFamilyDropDown("releasingFamily", String.valueOf(defaultReleasingFamily.getReleasingFamilyId()), releaseFamilies, true, selection);
/* 3466 */     releasingFamily.setTabIndex(13);
/*      */     
/* 3468 */     releasingFamily.addFormEvent("onChange", "ReleasingFamilyChange(this)");
/* 3469 */     selectionForm.addElement(releasingFamily);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3474 */     String envId = String.valueOf(selectedProject.getMSEnvironmentId());
/* 3475 */     String envName = MilestoneHelper.getStructureName(selectedProject.getMSEnvironmentId());
/* 3476 */     String environmentName = "";
/*      */     
/* 3478 */     FormHidden evironment = new FormHidden("environment", envId, false);
/* 3479 */     evironment.setDisplayName(envName);
/* 3480 */     selectionForm.addElement(evironment);
/*      */ 
/*      */     
/* 3483 */     String companyId = String.valueOf(selectedProject.getMSCompanyId());
/* 3484 */     String companyName = MilestoneHelper.getStructureName(selectedProject.getMSCompanyId());
/* 3485 */     FormHidden company = new FormHidden("company", companyId, false);
/* 3486 */     company.setTabIndex(15);
/*      */ 
/*      */     
/* 3489 */     company.setDisplayName(companyName);
/* 3490 */     selectionForm.addElement(company);
/*      */ 
/*      */ 
/*      */     
/* 3494 */     String divisionId = String.valueOf(selectedProject.getMSDivisionId());
/* 3495 */     String divisionName = MilestoneHelper.getStructureName(selectedProject.getMSDivisionId());
/* 3496 */     FormHidden division = new FormHidden("division", divisionId, false);
/* 3497 */     division.setTabIndex(16);
/*      */ 
/*      */     
/* 3500 */     division.setDisplayName(divisionName);
/* 3501 */     selectionForm.addElement(division);
/*      */ 
/*      */     
/* 3504 */     String labelId = String.valueOf(selectedProject.getMSLabelId());
/* 3505 */     String labelName = MilestoneHelper.getStructureName(selectedProject.getMSLabelId());
/* 3506 */     FormHidden label = new FormHidden("label", labelId, false);
/* 3507 */     label.setTabIndex(17);
/* 3508 */     label.setDisplayName(labelName);
/* 3509 */     selectionForm.addElement(label);
/*      */ 
/*      */     
/* 3512 */     if (selectedProject.getOperCompany().equals("***")) {
/* 3513 */       FormHidden opercompany = new FormHidden("opercompany", "***", false);
/* 3514 */       opercompany.setTabIndex(18);
/* 3515 */       opercompany.setDisplayName("***");
/* 3516 */       selectionForm.addElement(opercompany);
/*      */     } else {
/* 3518 */       LookupObject oc = MilestoneHelper.getLookupObject(selectedProject
/* 3519 */           .getOperCompany(), Cache.getOperatingCompanies());
/*      */       
/* 3521 */       String ocAbbr = "";
/* 3522 */       String ocName = "";
/*      */ 
/*      */ 
/*      */       
/* 3526 */       if (oc == null) {
/* 3527 */         ocAbbr = selectedProject.getOperCompany();
/*      */       } else {
/* 3529 */         if (oc != null && oc.getAbbreviation() != null)
/* 3530 */           ocAbbr = oc.getAbbreviation(); 
/* 3531 */         if (oc != null && oc.getName() != null)
/* 3532 */           ocName = ":" + oc.getName(); 
/*      */       } 
/* 3534 */       FormHidden opercompany = new FormHidden("opercompany", ocAbbr, false);
/* 3535 */       opercompany.setTabIndex(18);
/* 3536 */       opercompany.setDisplayName(String.valueOf(ocAbbr) + ocName);
/*      */       
/* 3538 */       if (ocAbbr.equals("ZZ"))
/* 3539 */         opercompany.setDisplayName(ocAbbr); 
/* 3540 */       selectionForm.addElement(opercompany);
/*      */     } 
/*      */ 
/*      */     
/* 3544 */     FormHidden superlabel = new FormHidden("superlabel", selectedProject.getSuperLabel(), false);
/* 3545 */     superlabel.setTabIndex(19);
/* 3546 */     superlabel.setClassName("ctrlShort");
/* 3547 */     superlabel.addFormEvent("onBlur", "javascript:this.value=this.value.toUpperCase();");
/* 3548 */     selectionForm.addElement(superlabel);
/*      */ 
/*      */     
/* 3551 */     FormHidden distCoLabelID = new FormHidden("distCoLabelID", labelId, false);
/* 3552 */     distCoLabelID.setDisplayName(labelId);
/* 3553 */     selectionForm.addElement(distCoLabelID);
/*      */ 
/*      */     
/* 3556 */     FormHidden sublabel = new FormHidden("sublabel", selectedProject.getSubLabel(), false);
/* 3557 */     sublabel.setTabIndex(20);
/* 3558 */     sublabel.setClassName("ctrlShort");
/* 3559 */     sublabel.addFormEvent("onBlur", "javascript:this.value=this.value.toUpperCase();");
/* 3560 */     selectionForm.addElement(sublabel);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3565 */     FormDropDownMenu configcode = MilestoneHelper.getPfmLookupDropDown("configcode", MilestoneHelper.getConfigCodes(0), "", false, true);
/* 3566 */     configcode.setTabIndex(21);
/*      */     
/* 3568 */     configcode.addFormEvent("onKeyPress", "scanKey(window.event.keyCode, 'document.all.configcode.options', getMaxLength(document.all.configcode.options))");
/*      */ 
/*      */ 
/*      */     
/* 3572 */     configcode.addFormEvent("onChange", "setNoDigitalRelease(this);");
/*      */ 
/*      */     
/* 3575 */     selectionForm.addElement(configcode);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3580 */     Vector jdeExceptionFamilies = ProjectSearchManager.getInstance().getProjectSearchJDEFamilies();
/* 3581 */     boolean isUmvdUser = jdeExceptionFamilies.contains(new Integer(selectedProject.getMSFamilyId()));
/*      */ 
/*      */     
/* 3584 */     String imprintStr = "";
/* 3585 */     if (isUmvdUser) {
/* 3586 */       imprintStr = labelName;
/*      */     } else {
/* 3588 */       imprintStr = (selectedProject.getImprint() != null) ? selectedProject.getImprint() : "";
/*      */     } 
/* 3590 */     FormTextField imprint = new FormTextField("imprint", imprintStr, false, 50);
/* 3591 */     imprint.setTabIndex(21);
/* 3592 */     imprint.setClassName("ctrlMedium");
/* 3593 */     imprint.addFormEvent("onChange", "javaScript:removeSpaces(this);");
/* 3594 */     selectionForm.addElement(imprint);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3604 */     String projectIdStr = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3617 */     projectIdStr = selectedProject.getRMSProjectNo();
/* 3618 */     FormHidden projectId = new FormHidden("projectId", projectIdStr, false);
/* 3619 */     projectId.setTabIndex(22);
/* 3620 */     projectId.setClassName("ctrlMedium");
/* 3621 */     projectId.setDisplayName(projectIdStr);
/* 3622 */     selectionForm.addElement(projectId);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3627 */     FormTextField gridNumber = new FormTextField("gridNumber", selection.getGridNumber(), false, 50);
/* 3628 */     gridNumber.setTabIndex(25);
/*      */     
/* 3630 */     gridNumber.setEnabled(true);
/* 3631 */     selectionForm.addElement(gridNumber);
/*      */ 
/*      */ 
/*      */     
/* 3635 */     FormTextField upc = new FormTextField("UPC", "", false, 17, 20);
/* 3636 */     upc.setTabIndex(23);
/* 3637 */     upc.setClassName("ctrlMedium");
/*      */     
/* 3639 */     upc.addFormEvent("onKeyPress", "return checkNumbersOnly(window.event.keyCode);");
/*      */ 
/*      */     
/* 3642 */     selectionForm.addElement(upc);
/*      */ 
/*      */ 
/*      */     
/* 3646 */     FormTextField soundscan = new FormTextField("soundscan", "", false, 17, 20);
/* 3647 */     soundscan.setTabIndex(24);
/* 3648 */     soundscan.setClassName("ctrlMedium");
/*      */ 
/*      */     
/* 3651 */     soundscan.addFormEvent("onKeyPress", "return checkNumbersOnly(window.event.keyCode);");
/*      */ 
/*      */ 
/*      */     
/* 3655 */     selectionForm.addElement(soundscan);
/*      */ 
/*      */     
/* 3658 */     FormDropDownMenu prefix = MilestoneHelper.getPrefixCodeDropDown(userId, "prefix", Cache.getPrefixCodes(), "", true, context);
/* 3659 */     prefix.setTabIndex(25);
/* 3660 */     prefix.setClassName("ctrlShort");
/* 3661 */     selectionForm.addElement(prefix);
/*      */ 
/*      */     
/* 3664 */     FormTextField selectionNo = new FormTextField("selectionNo", "", false, 20, 20);
/* 3665 */     selectionNo.setTabIndex(26);
/* 3666 */     selectionNo.setClassName("ctrlMedium");
/* 3667 */     selectionNo.addFormEvent("onChange", "javaScript:removeSpaces(this);");
/* 3668 */     selectionForm.addElement(selectionNo);
/*      */ 
/*      */     
/* 3671 */     FormTextField titleId = new FormTextField("titleId", "", false, 13, 24);
/* 3672 */     titleId.setClassName("ctrlMedium");
/*      */     
/* 3674 */     titleId.setTabIndex(27);
/*      */ 
/*      */     
/* 3677 */     selectionForm.addElement(titleId);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3684 */     FormDropDownMenu productLine = MilestoneHelper.getLookupDropDown("productLine", Cache.getProductCategories(0), "", true, true);
/* 3685 */     productLine.setTabIndex(28);
/* 3686 */     productLine.setClassName("ctrlMedium");
/* 3687 */     selectionForm.addElement(productLine);
/*      */ 
/*      */     
/* 3690 */     FormDropDownMenu releaseType = MilestoneHelper.getLookupDropDown("releaseType", Cache.getReleaseTypes(), "", true, true);
/* 3691 */     releaseType.setTabIndex(29);
/* 3692 */     releaseType.setClassName("ctrlMedium");
/* 3693 */     releaseType.addFormEvent("onChange", "releaseTypeChanged()");
/* 3694 */     selectionForm.addElement(releaseType);
/*      */ 
/*      */     
/* 3697 */     FormDropDownMenu configuration = MilestoneHelper.getSelectionConfigurationDropDown("configuration", "", true, 0);
/* 3698 */     configuration.setTabIndex(30);
/*      */     
/* 3700 */     configuration.addFormEvent("onChange", "buildSubConfigs(this.selectedIndex)");
/* 3701 */     selectionForm.addElement(configuration);
/*      */ 
/*      */ 
/*      */     
/* 3705 */     Vector configs = Cache.getSelectionConfigs();
/* 3706 */     SelectionConfiguration config = (SelectionConfiguration)configs.get(0);
/* 3707 */     FormDropDownMenu subConfiguration = new FormDropDownMenu("subConfiguration", "", "", "", true);
/*      */     
/* 3709 */     subConfiguration.addFormEvent("onChange", "isSubConfigParent(this.selectedIndex)");
/* 3710 */     subConfiguration.setTabIndex(31);
/* 3711 */     subConfiguration.setEnabled(false);
/*      */ 
/*      */     
/* 3714 */     selectionForm.addElement(subConfiguration);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3721 */     FormTextField test = new FormTextField("test", "", false, 8, 8);
/* 3722 */     test.setTabIndex(32);
/* 3723 */     test.setClassName("ctrlShort");
/* 3724 */     test.addFormEvent("onChange", "javaScript:clickSell(this,false);");
/* 3725 */     selectionForm.addElement(test);
/*      */ 
/*      */     
/* 3728 */     FormDropDownMenu priceCode = new FormDropDownMenu("priceCode", "", "-1" + getSellCodesString(), "&nbsp;" + getSellCodesString(), true);
/* 3729 */     priceCode.setTabIndex(33);
/* 3730 */     priceCode.setClassName("ctrlSmall");
/* 3731 */     selectionForm.addElement(priceCode);
/*      */ 
/*      */     
/* 3734 */     FormTextField testDPC = new FormTextField("testDPC", "", false, 8, 8);
/* 3735 */     testDPC.setTabIndex(39);
/* 3736 */     testDPC.setClassName("ctrlShort");
/* 3737 */     testDPC.addFormEvent("onChange", "javaScript:clickSellDPC(this);");
/* 3738 */     selectionForm.addElement(testDPC);
/*      */ 
/*      */     
/* 3741 */     FormDropDownMenu priceCodeDPC = new FormDropDownMenu("priceCodeDPC", "", "-1" + getSellCodesStringDPC(), "&nbsp;" + getSellCodesStringDPC(), false);
/* 3742 */     priceCodeDPC.setTabIndex(39);
/* 3743 */     priceCodeDPC.setClassName("ctrlSmall");
/* 3744 */     selectionForm.addElement(priceCodeDPC);
/*      */ 
/*      */     
/* 3747 */     FormTextField numOfUnits = new FormTextField("numOfUnits", "0", false, 10, 10);
/* 3748 */     numOfUnits.setTabIndex(34);
/* 3749 */     numOfUnits.setClassName("ctrlShort");
/* 3750 */     selectionForm.addElement(numOfUnits);
/*      */ 
/*      */     
/* 3753 */     FormDropDownMenu contactList = MilestoneHelper.getContactsDropDown(context, "contactlist", new Vector(), user, true);
/* 3754 */     contactList.setTabIndex(35);
/* 3755 */     contactList.setClassName("ctrlMedium");
/* 3756 */     selectionForm.addElement(contactList);
/*      */ 
/*      */     
/* 3759 */     FormTextField contact = new FormTextField("contact", "", false, 14, 30);
/* 3760 */     contact.setTabIndex(36);
/* 3761 */     contact.setClassName("ctrlMedium");
/* 3762 */     selectionForm.addElement(contact);
/*      */ 
/*      */     
/* 3765 */     FormCheckBox parentalIndicator = new FormCheckBox("parentalIndicator", "", false, false);
/* 3766 */     parentalIndicator.setTabIndex(37);
/* 3767 */     selectionForm.addElement(parentalIndicator);
/*      */ 
/*      */     
/* 3770 */     FormCheckBox specPkgIndicator = new FormCheckBox("specialPkgIndicator", "", false, false);
/* 3771 */     specPkgIndicator.addFormEvent("onDblClick", "document.forms[0].PackagingHelper.value=document.forms[0].package.value;togglePackaging();");
/* 3772 */     specPkgIndicator.setTabIndex(38);
/* 3773 */     selectionForm.addElement(specPkgIndicator);
/*      */ 
/*      */     
/* 3776 */     FormTextField pkg = new FormTextField("package", "", false, 13, 100);
/* 3777 */     pkg.setTabIndex(39);
/* 3778 */     pkg.setClassName("ctrlMedium");
/* 3779 */     pkg.addFormEvent("onDblClick", "document.forms[0].PackagingHelper.value=document.forms[0].package.value;togglePackaging();");
/* 3780 */     selectionForm.addElement(pkg);
/*      */ 
/*      */     
/* 3783 */     FormDropDownMenu genre = MilestoneHelper.getPfmLookupDropDown("genre", Cache.getMusicTypes(), "", true, true);
/*      */     
/* 3785 */     genre.setTabIndex(40);
/* 3786 */     genre.setId("music_type");
/*      */     
/* 3788 */     selectionForm.addElement(genre);
/*      */ 
/*      */     
/* 3791 */     FormTextField territory = new FormTextField("territory", "", false, 13, 255);
/* 3792 */     territory.setTabIndex(41);
/* 3793 */     territory.setClassName("ctrlMedium");
/* 3794 */     territory.addFormEvent("onDblClick", "document.forms[0].TerritoryHelper.value=document.forms[0].territory.value;toggleTerritory();");
/* 3795 */     selectionForm.addElement(territory);
/*      */ 
/*      */     
/* 3798 */     FormHidden productionGroupCode = new FormHidden("productGroupCode", selection.getProductionGroupCode(), false);
/* 3799 */     productionGroupCode.setTabIndex(42);
/* 3800 */     productionGroupCode.setDisplayName(selection.getProductionGroupCode());
/* 3801 */     productionGroupCode.setClassName("ctrlMedium");
/* 3802 */     selectionForm.addElement(productionGroupCode);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3808 */     FormTextField lastStreetUpdatedDate = new FormTextField("laststreetupdateddate", "", false, 13);
/* 3809 */     selectionForm.addElement(lastStreetUpdatedDate);
/*      */ 
/*      */     
/* 3812 */     FormTextField lastUpdatedDate = new FormTextField("lastupdateddate", "", false, 50);
/* 3813 */     selectionForm.addElement(lastUpdatedDate);
/*      */ 
/*      */     
/* 3816 */     FormTextField originDate = new FormTextField("origindate", "", false, 13);
/* 3817 */     selectionForm.addElement(originDate);
/*      */ 
/*      */     
/* 3820 */     FormTextArea packagingHelper = new FormTextArea("PackagingHelper", "", false, 2, 44, "virtual");
/* 3821 */     selectionForm.addElement(packagingHelper);
/*      */ 
/*      */     
/* 3824 */     FormTextArea territoryHelper = new FormTextArea("TerritoryHelper", selection.getSelectionTerritory(), false, 2, 44, "virtual");
/* 3825 */     selectionForm.addElement(territoryHelper);
/*      */ 
/*      */     
/* 3828 */     FormTextArea comments = new FormTextArea("comments", "", false, 2, 44, "virtual");
/* 3829 */     comments.addFormEvent("onBlur", "Javascript:checkField(this)");
/* 3830 */     selectionForm.addElement(comments);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3837 */     addSelectionSearchElements(context, new Selection(), selectionForm, MilestoneHelper.getUserCompanies(context), true);
/*      */ 
/*      */     
/* 3840 */     if (context.getSessionValue("NOTEPAD_SELECTION_VISIBLE") != null) {
/* 3841 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_SELECTION_VISIBLE"));
/*      */     }
/*      */     
/* 3844 */     context.putDelivery("releaseWeek", "");
/* 3845 */     context.putDelivery("new-or-copy", "true");
/* 3846 */     context.putDelivery("price", "0.00");
/*      */     
/* 3848 */     boolean isParent = false;
/*      */     
/* 3850 */     if (selection.getSelectionSubConfig() != null) {
/* 3851 */       isParent = selection.getSelectionSubConfig().isParent();
/*      */     }
/* 3853 */     context.putDelivery("is-parent", String.valueOf(isParent));
/*      */     
/* 3855 */     String lastUpdateUser = "";
/* 3856 */     if (selection.getLastUpdatingUser() != null)
/* 3857 */       lastUpdateUser = selection.getLastUpdatingUser().getName(); 
/* 3858 */     context.putDelivery("lastUpdateUser", lastUpdateUser);
/*      */     
/* 3860 */     return selectionForm;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Form buildManufacturingForm(Context context, Selection selection, String command, int accessLevel, boolean newFlag) {
/* 3869 */     String mainCommandString = "";
/* 3870 */     String holdReasonString = "";
/* 3871 */     String distributionString = "";
/* 3872 */     String mfgCommentsString = "";
/* 3873 */     String numberOfUnits = "0";
/*      */     
/* 3875 */     User umlContactUser = null;
/*      */ 
/*      */     
/* 3878 */     String selectedConfig = "";
/* 3879 */     String selectedSubConfig = "";
/* 3880 */     if (selection.getSelectionConfig() != null && selection.getSelectionConfig().getSelectionConfigurationAbbreviation() != null)
/* 3881 */       selectedConfig = selection.getSelectionConfig().getSelectionConfigurationAbbreviation(); 
/* 3882 */     if (selection.getSelectionSubConfig() != null && selection.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation() != null) {
/* 3883 */       selectedSubConfig = selection.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3892 */     Form selectionForm = new Form(this.application, "selectionForm", 
/* 3893 */         this.application.getInfrastructure().getServletURL(), "POST");
/*      */     
/* 3895 */     if (!newFlag) {
/*      */       
/* 3897 */       if (selection != null)
/*      */       {
/* 3899 */         mainCommandString = "selection-manufacturing-editor";
/*      */         
/* 3901 */         if (selection.getUmlContact() != null) {
/* 3902 */           umlContactUser = selection.getUmlContact();
/*      */         }
/* 3904 */         if (selection.getManufacturingComments() != null && selection.getManufacturingComments() != null)
/*      */         {
/* 3906 */           mfgCommentsString = selection.getManufacturingComments();
/*      */         }
/*      */         
/* 3909 */         if (selection.getNumberOfUnits() > 0) {
/* 3910 */           numberOfUnits = Integer.toString(selection.getNumberOfUnits());
/*      */         }
/* 3912 */         if (selection.getDistribution() != null) {
/* 3913 */           distributionString = selection.getDistribution().getAbbreviation();
/*      */         }
/* 3915 */         selectionForm.addElement(new FormHidden("new", "false"));
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/* 3920 */       if (selection.getNumberOfUnits() > 0) {
/* 3921 */         numberOfUnits = Integer.toString(selection.getNumberOfUnits());
/*      */       }
/* 3923 */       mainCommandString = "selection-manufacturing-edit-new";
/* 3924 */       selectionForm.addElement(new FormHidden("new", "true"));
/*      */     } 
/*      */     
/* 3927 */     if (selection != null) {
/*      */ 
/*      */       
/* 3930 */       String lastMfgUpdatedDateText = "";
/* 3931 */       if (selection.getLastMfgUpdateDate() != null)
/* 3932 */         lastMfgUpdatedDateText = MilestoneHelper.getCustomFormatedDate(selection.getLastMfgUpdateDate(), "M/d/yyyy hh:mm:ss a 'ET'"); 
/* 3933 */       context.putDelivery("lastUpdateDate", lastMfgUpdatedDateText);
/*      */       
/* 3935 */       String lastMfgUpdateUser = "";
/* 3936 */       if (selection.getLastMfgUpdatingUser() != null)
/* 3937 */         lastMfgUpdateUser = selection.getLastMfgUpdatingUser().getName(); 
/* 3938 */       context.putDelivery("lastUpdateUser", lastMfgUpdateUser);
/*      */ 
/*      */       
/* 3941 */       if (numberOfUnits.equals("0")) {
/* 3942 */         numberOfUnits = "";
/*      */       }
/* 3944 */       context.putDelivery("numberOfUnits", numberOfUnits);
/* 3945 */       context.putDelivery("upc", selection.getUpc());
/*      */       
/* 3947 */       context.putDelivery("label", selection.getImprint());
/* 3948 */       context.putDelivery("status", selection.getSelectionStatus().getName());
/* 3949 */       String typeConfig = String.valueOf(selection.getProductCategory().getName()) + " / " + selection.getReleaseType().getName() + " / " + selection.getSelectionConfig().getSelectionConfigurationName() + " / " + selection.getSelectionSubConfig().getSelectionSubConfigurationName();
/* 3950 */       context.putDelivery("typeConfig", typeConfig);
/*      */ 
/*      */ 
/*      */       
/* 3954 */       FormTextArea comments = new FormTextArea("comments", selection.getSelectionComments(), false, 2, 44, "virtual");
/* 3955 */       comments.addFormEvent("onBlur", "Javascript:checkField(this)");
/* 3956 */       comments.setReadOnly(true);
/* 3957 */       selectionForm.addElement(comments);
/*      */ 
/*      */       
/* 3960 */       context.putDelivery("releaseWeek", MilestoneHelper.getReleaseWeekString(selection));
/*      */ 
/*      */ 
/*      */       
/* 3964 */       context.putDelivery("releasingFamily", ReleasingFamily.getName(selection.getReleaseFamilyId()));
/*      */ 
/*      */       
/* 3967 */       selectionForm.addElement(new FormHidden("cmd", "selection-manufacturing-editor", true));
/* 3968 */       selectionForm.addElement(new FormHidden("OrderBy", "", true));
/*      */ 
/*      */       
/* 3971 */       FormTextArea holdReason = new FormTextArea("holdReason", selection.getHoldReason(), false, 2, 44, "virtual");
/* 3972 */       holdReason.setReadOnly(true);
/* 3973 */       holdReason.addFormEvent("onBlur", "Javascript:checkField(this)");
/*      */ 
/*      */       
/* 3976 */       FormDropDownMenu umlContact = MilestoneHelper.getContactsDropDown(context, "umlcontact", Cache.getUmlUsers(), umlContactUser, true);
/* 3977 */       umlContact.setId("umlcontact");
/*      */ 
/*      */       
/* 3980 */       FormDropDownMenu distribution = MilestoneHelper.getLookupDropDown("distribution", Cache.getDistributionCodes(), distributionString, true, true);
/* 3981 */       distribution.setId("distribution");
/*      */ 
/*      */       
/* 3984 */       FormTextArea mfgcommentsTextArea = new FormTextArea("orderCommentHelper", mfgCommentsString, false, 2, 44, "virtual");
/*      */ 
/*      */       
/* 3987 */       Vector vendors = new Vector();
/*      */       
/* 3989 */       if (selection.getManufacturingPlants() != null) {
/* 3990 */         vendors = selection.getManufacturingPlants();
/*      */       }
/* 3992 */       String vendorString = "";
/* 3993 */       String poQtyNumber = "0";
/* 3994 */       String completedQtyNumber = "0";
/* 3995 */       String explodedTotal = "0";
/*      */ 
/*      */       
/* 3998 */       for (int vendorCount = 0; vendorCount < vendors.size(); vendorCount++) {
/*      */         
/* 4000 */         vendorString = "";
/* 4001 */         poQtyNumber = "0";
/* 4002 */         completedQtyNumber = "0";
/* 4003 */         explodedTotal = "0";
/*      */         
/* 4005 */         Plant plant = (Plant)vendors.get(vendorCount);
/*      */         
/* 4007 */         if (plant.getOrderQty() > 0) {
/* 4008 */           poQtyNumber = Integer.toString(plant.getOrderQty());
/*      */         }
/* 4010 */         if (plant.getCompletedQty() > 0) {
/* 4011 */           completedQtyNumber = Integer.toString(plant.getCompletedQty());
/*      */         }
/* 4013 */         if (plant.getOrderQty() > 0 && selection.getNumberOfUnits() > 0) {
/* 4014 */           explodedTotal = Integer.toString(plant.getCompletedQty() * selection.getNumberOfUnits());
/*      */         }
/* 4016 */         if (plant.getPlant() != null) {
/* 4017 */           vendorString = plant.getPlant().getAbbreviation();
/*      */         }
/*      */         
/* 4020 */         FormDropDownMenu vendor = MilestoneHelper.getLookupDropDown("plant" + vendorCount, Cache.getVendors(), vendorString, true, true);
/* 4021 */         vendor.setId("plant");
/*      */ 
/*      */         
/* 4024 */         FormTextField poQty = new FormTextField("po_qty" + vendorCount, poQtyNumber, true, 8);
/* 4025 */         poQty.setId("po_qty");
/* 4026 */         poQty.addFormEvent("align", "right");
/*      */ 
/*      */         
/* 4029 */         FormTextField completedQty = new FormTextField("completed_qty" + vendorCount, completedQtyNumber, true, 8);
/* 4030 */         completedQty.setId("completed_qty");
/* 4031 */         completedQty.addFormEvent("align", "right");
/*      */ 
/*      */         
/* 4034 */         FormTextField explode = new FormTextField("explode" + vendorCount, explodedTotal, true, 8);
/* 4035 */         explode.setId("explode");
/* 4036 */         explode.addFormEvent("align", "right");
/*      */         
/* 4038 */         selectionForm.addElement(vendor);
/* 4039 */         selectionForm.addElement(poQty);
/* 4040 */         selectionForm.addElement(completedQty);
/* 4041 */         selectionForm.addElement(explode);
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
/* 4061 */       selectionForm.addElement(holdReason);
/* 4062 */       selectionForm.addElement(umlContact);
/* 4063 */       selectionForm.addElement(distribution);
/* 4064 */       selectionForm.addElement(comments);
/* 4065 */       selectionForm.addElement(mfgcommentsTextArea);
/*      */     } 
/*      */     
/* 4068 */     User user = (User)context.getSessionValue("user");
/*      */     
/* 4070 */     int secureLevel = getSelectionMfgPermissions(selection, user);
/* 4071 */     setButtonVisibilities(selection, user, context, secureLevel, command);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4076 */     context.putDelivery("releaseWeek", MilestoneHelper.getReleaseWeekString(selection));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4081 */     Vector companies = MilestoneHelper.getUserCompanies(context);
/* 4082 */     addSelectionSearchElements(context, selection, selectionForm, companies, true);
/*      */     
/* 4084 */     if (context.getSessionValue("NOTEPAD_MFG_VISIBLE") != null) {
/* 4085 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_MFG_VISIBLE"));
/*      */     }
/*      */     
/* 4088 */     return selectionForm;
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
/* 4102 */     User user = (User)context.getSessionValue("user");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4108 */     if (includeJSArrays) {
/*      */ 
/*      */       
/* 4111 */       String selectedConfig = "";
/* 4112 */       String selectedSubConfig = "";
/* 4113 */       if (selection != null && selection.getSelectionConfig() != null && selection.getSelectionConfig().getSelectionConfigurationAbbreviation() != null)
/* 4114 */         selectedConfig = selection.getSelectionConfig().getSelectionConfigurationAbbreviation(); 
/* 4115 */       if (selection != null && selection.getSelectionSubConfig() != null && selection.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation() != null) {
/* 4116 */         selectedSubConfig = selection.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation();
/*      */       }
/*      */       
/* 4119 */       context.putDelivery("selectionArrays", String.valueOf(Cache.getJavaScriptConfigArray(selectedConfig)) + 
/*      */           
/* 4121 */           " " + Cache.getJavaScriptPriceCodeArray() + " " + Cache.getJavaScriptPriceCodeDPCArray() + " " + Cache.getJavaScriptSubConfigArray(selectedSubConfig) + 
/* 4122 */           " " + ReleasingFamily.getJavaScriptCorporateArrayReleasingFamilySearch(context) + 
/*      */           
/* 4124 */           " " + Cache.getJavaScriptPFMConfigs());
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4131 */     boolean defaultStatus = false;
/* 4132 */     FormCheckBox showAllSearch = new FormCheckBox("ShowAllSearch", "", false, defaultStatus);
/* 4133 */     showAllSearch.setId("ShowAllSearch");
/* 4134 */     selectionForm.addElement(showAllSearch);
/*      */ 
/*      */     
/* 4137 */     Vector families = filterCSO(ReleasingFamily.getUserReleasingFamiliesVectorOfFamilies(context));
/*      */     
/* 4139 */     String defaultReleasingFamily = "-1";
/* 4140 */     FormDropDownMenu Family = MilestoneHelper.getCorporateStructureDropDown("FamilySearch", families, defaultReleasingFamily, false, true);
/* 4141 */     Family.addFormEvent("onChange", "return(clickFamilySearch(this))");
/* 4142 */     Family.setId("FamilySearch");
/* 4143 */     selectionForm.addElement(Family);
/*      */ 
/*      */     
/* 4146 */     Vector environments = MilestoneHelper.getUserEnvironments(context);
/* 4147 */     Vector myCompanies = MilestoneHelper.getUserCompanies(context);
/* 4148 */     environments = filterSelectionEnvironments(myCompanies);
/*      */ 
/*      */     
/* 4151 */     environments = MilestoneHelper.removeUnusedCSO(environments, context, -1);
/*      */ 
/*      */     
/* 4154 */     String defaultEnvironment = "-1";
/*      */ 
/*      */     
/* 4157 */     FormDropDownMenu envMenu = MilestoneHelper.getCorporateStructureDropDown("EnvironmentSearch", environments, defaultEnvironment, false, true);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4162 */     envMenu.addFormEvent("onChange", "return(clickEnvironmentSearch(this))");
/* 4163 */     envMenu.setId("EnvironmentSearch");
/* 4164 */     selectionForm.addElement(envMenu);
/*      */ 
/*      */     
/* 4167 */     Vector searchCompanies = null;
/*      */ 
/*      */ 
/*      */     
/* 4171 */     searchCompanies = MilestoneHelper.getUserCompanies(context);
/*      */ 
/*      */     
/* 4174 */     searchCompanies = MilestoneHelper.removeUnusedCSO(searchCompanies, context, -1);
/*      */ 
/*      */     
/* 4177 */     FormDropDownMenu companySearch = MilestoneHelper.getCorporateStructureDropDown("CompanySearch", searchCompanies, "", false, true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4183 */     companySearch.setId("CompanySearch");
/* 4184 */     companySearch.addFormEvent("onChange", "return(clickCompanySearch(this))");
/* 4185 */     selectionForm.addElement(companySearch);
/*      */ 
/*      */ 
/*      */     
/* 4189 */     Vector labels = MilestoneHelper.getUserLabels(companies);
/*      */ 
/*      */     
/* 4192 */     labels = MilestoneHelper.removeUnusedCSO(labels, context, -1);
/*      */     
/* 4194 */     FormDropDownMenu labelSearch = MilestoneHelper.getCorporateStructureDropDownDuplicates("LabelSearch", labels, "", false, true, false);
/* 4195 */     labelSearch.setId("LabelSearch");
/* 4196 */     selectionForm.addElement(labelSearch);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4219 */     User defaultContact = null;
/*      */     
/* 4221 */     Vector labelContacts = SelectionManager.getLabelContactsExcludeUml(context);
/* 4222 */     FormDropDownMenu searchContact = MilestoneHelper.getContactsDropDown(context, "ContactSearch", labelContacts, defaultContact, true);
/* 4223 */     selectionForm.addElement(searchContact);
/*      */ 
/*      */     
/* 4226 */     FormTextField streetDateSearch = new FormTextField("StreetDateSearch", "", false, 14, 10);
/* 4227 */     streetDateSearch.addFormEvent("onBlur", "JavaScript:removeSpacesInDate(document.forms[0].StreetDateSearch.value,this)");
/* 4228 */     streetDateSearch.setId("StreetDateSearch");
/* 4229 */     selectionForm.addElement(streetDateSearch);
/*      */ 
/*      */     
/* 4232 */     FormTextField streetEndDateSearch = new FormTextField("StreetEndDateSearch", "", false, 14, 10);
/* 4233 */     streetEndDateSearch.addFormEvent("onBlur", "JavaScript:removeSpacesInDate(document.forms[0].StreetEndDateSearch.value,this)");
/* 4234 */     streetEndDateSearch.setId("StreetEndDateSearch");
/* 4235 */     selectionForm.addElement(streetEndDateSearch);
/*      */ 
/*      */     
/* 4238 */     String[] dvalues = new String[3];
/* 4239 */     dvalues[0] = "physical";
/* 4240 */     dvalues[1] = "digital";
/* 4241 */     dvalues[2] = "both";
/*      */     
/* 4243 */     String[] dlabels = new String[3];
/* 4244 */     dlabels[0] = "Physical";
/* 4245 */     dlabels[1] = "Digital";
/* 4246 */     dlabels[2] = "Both";
/*      */ 
/*      */     
/* 4249 */     String defaultProdType = "both";
/* 4250 */     FormRadioButtonGroup prodType = new FormRadioButtonGroup("ProdType", defaultProdType, dvalues, dlabels, false);
/* 4251 */     prodType.addFormEvent("onClick", "buildSearchConfigs(this)");
/* 4252 */     selectionForm.addElement(prodType);
/*      */ 
/*      */ 
/*      */     
/* 4256 */     Vector searchConfigs = null;
/* 4257 */     searchConfigs = Cache.getSelectionConfigs();
/* 4258 */     FormDropDownMenu configSearch = MilestoneHelper.getSelectionConfigurationDropDown("ConfigSearch", "", false);
/* 4259 */     configSearch.setId("ConfigSearch");
/* 4260 */     configSearch.addFormEvent("onChange", "buildSearchSubConfigs(this.selectedIndex)");
/* 4261 */     selectionForm.addElement(configSearch);
/*      */ 
/*      */ 
/*      */     
/* 4265 */     FormDropDownMenu subconfigSearch = new FormDropDownMenu("SubconfigSearch", "");
/* 4266 */     subconfigSearch.setId("SubconfigSearch");
/* 4267 */     subconfigSearch.setEnabled(false);
/* 4268 */     selectionForm.addElement(subconfigSearch);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4273 */     FormTextField upcSearch = new FormTextField("UPCSearch", "", false, 20, 20);
/* 4274 */     upcSearch.setId("UPCSearch");
/* 4275 */     selectionForm.addElement(upcSearch);
/*      */ 
/*      */     
/* 4278 */     FormTextField prefixSearch = new FormTextField("PrefixSearch", "", false, 6, 5);
/* 4279 */     prefixSearch.setId("PrefixSearch");
/* 4280 */     selectionForm.addElement(prefixSearch);
/*      */ 
/*      */     
/* 4283 */     FormTextField selectionSearch = new FormTextField("SelectionSearch", "", false, 12, 20);
/* 4284 */     selectionSearch.setId("SelectionSearch");
/* 4285 */     selectionSearch.setClassName("ctrlMedium");
/* 4286 */     selectionForm.addElement(selectionSearch);
/*      */ 
/*      */     
/* 4289 */     FormTextField titleSearch = new FormTextField("TitleSearch", "", false, 20);
/* 4290 */     titleSearch.setId("TitleSearch");
/* 4291 */     selectionForm.addElement(titleSearch);
/*      */ 
/*      */     
/* 4294 */     FormTextField artistSearch = new FormTextField("ArtistSearch", "", false, 20);
/* 4295 */     artistSearch.setId("ArtistSearch");
/* 4296 */     selectionForm.addElement(artistSearch);
/*      */ 
/*      */ 
/*      */     
/* 4300 */     FormTextField projectIDSearch = new FormTextField("ProjectIDSearch", "", false, 20);
/* 4301 */     projectIDSearch.setId("ProjectIDSearch");
/* 4302 */     selectionForm.addElement(projectIDSearch);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4307 */     getUserPreferences(selectionForm, context);
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
/* 4318 */     User user = (User)context.getSessionValue("user");
/* 4319 */     if (user != null && user.getPreferences() != null) {
/*      */ 
/*      */       
/* 4322 */       String defaultStr = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 4328 */       User userSrch = (User)context.getSessionValue("ResetSearchVariables");
/* 4329 */       if (userSrch != null) {
/* 4330 */         resetSearchVariables(user, userSrch, context);
/*      */       }
/* 4332 */       if (!user.SS_searchInitiated) {
/*      */ 
/*      */         
/* 4335 */         if (user.getPreferences().getSelectionStatus() > 0) {
/* 4336 */           ((FormCheckBox)form.getElement("ShowAllSearch")).setChecked(true);
/* 4337 */           user.SS_showAllSearch = "true";
/*      */         } 
/*      */ 
/*      */         
/* 4341 */         if (user.getPreferences().getSelectionReleasingFamily() > 0) {
/* 4342 */           defaultStr = String.valueOf(user.getPreferences().getSelectionReleasingFamily());
/* 4343 */           ((FormDropDownMenu)form.getElement("FamilySearch")).setValue(defaultStr);
/* 4344 */           user.SS_familySearch = defaultStr;
/*      */         } 
/*      */         
/* 4347 */         if (user.getPreferences().getSelectionEnvironment() > 0) {
/* 4348 */           defaultStr = String.valueOf(user.getPreferences().getSelectionEnvironment());
/* 4349 */           ((FormDropDownMenu)form.getElement("EnvironmentSearch")).setValue(defaultStr);
/* 4350 */           user.SS_environmentSearch = defaultStr;
/*      */         } 
/*      */         
/* 4353 */         if (user.getPreferences().getSelectionLabelContact() > 0) {
/* 4354 */           defaultStr = String.valueOf(user.getPreferences().getSelectionLabelContact());
/* 4355 */           ((FormDropDownMenu)form.getElement("ContactSearch")).setValue(defaultStr);
/* 4356 */           user.SS_contactSearch = defaultStr;
/*      */         } 
/*      */         
/* 4359 */         if (user.getPreferences().getSelectionProductType() > -1) {
/* 4360 */           if (user.getPreferences().getSelectionProductType() == 0)
/* 4361 */             defaultStr = "physical"; 
/* 4362 */           if (user.getPreferences().getSelectionProductType() == 1)
/* 4363 */             defaultStr = "digital"; 
/* 4364 */           if (user.getPreferences().getSelectionProductType() == 2)
/* 4365 */             defaultStr = "both"; 
/* 4366 */           ((FormRadioButtonGroup)form.getElement("ProdType")).setValue(defaultStr);
/* 4367 */           user.SS_productTypeSearch = defaultStr;
/*      */         }
/*      */       
/*      */       }
/*      */       else {
/*      */         
/* 4373 */         if (user.SS_showAllSearch.equals("true")) {
/* 4374 */           ((FormCheckBox)form.getElement("ShowAllSearch")).setChecked(true);
/*      */         }
/*      */         
/* 4377 */         if (!user.SS_familySearch.equals("")) {
/* 4378 */           ((FormDropDownMenu)form.getElement("FamilySearch")).setValue(user.SS_familySearch);
/*      */         }
/*      */         
/* 4381 */         if (!user.SS_environmentSearch.equals("")) {
/* 4382 */           ((FormDropDownMenu)form.getElement("EnvironmentSearch")).setValue(user.SS_environmentSearch);
/*      */         }
/*      */         
/* 4385 */         if (!user.SS_contactSearch.equals("")) {
/* 4386 */           ((FormDropDownMenu)form.getElement("ContactSearch")).setValue(user.SS_contactSearch);
/*      */         }
/*      */         
/* 4389 */         if (!user.SS_productTypeSearch.equals("")) {
/* 4390 */           ((FormRadioButtonGroup)form.getElement("ProdType")).setValue(user.SS_productTypeSearch);
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 4395 */       user.SS_searchInitiated = true;
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
/* 4410 */     StringBuffer result = new StringBuffer(100);
/* 4411 */     String str = "";
/* 4412 */     String value = new String();
/*      */     
/* 4414 */     User user = (User)context.getSessionValue("user");
/*      */     
/* 4416 */     Vector vUserCompanies = (Vector)MilestoneHelper.getUserCompanies(context).clone();
/*      */     
/* 4418 */     Vector vUserEnvironments = filterSelectionEnvironments(vUserCompanies);
/*      */ 
/*      */     
/* 4421 */     result.append("\n");
/* 4422 */     result.append("var a = new Array();\n");
/* 4423 */     result.append("var b = new Array();\n");
/* 4424 */     result.append("var c = new Array();\n");
/* 4425 */     int arrayIndex = 0;
/*      */ 
/*      */     
/* 4428 */     result.append("a[0] = new Array( 0, '-- [nothing selected] --');\n");
/*      */ 
/*      */     
/* 4431 */     HashMap corpHashMap = MilestoneHelper.buildActiveCorporateStructureHashMap();
/*      */ 
/*      */ 
/*      */     
/* 4435 */     for (int i = 0; i < vUserEnvironments.size(); i++) {
/*      */       
/* 4437 */       Environment ue = (Environment)vUserEnvironments.elementAt(i);
/* 4438 */       if (ue != null) {
/*      */         
/* 4440 */         result.append("a[");
/* 4441 */         result.append(ue.getStructureID());
/* 4442 */         result.append("] = new Array(");
/*      */         
/* 4444 */         boolean foundFirst = false;
/* 4445 */         Vector tmpArray = new Vector();
/*      */         
/* 4447 */         Vector companies = Cache.getInstance().getCompanies();
/* 4448 */         for (int j = 0; j < companies.size(); j++) {
/*      */           
/* 4450 */           Company node = (Company)companies.elementAt(j);
/*      */           
/* 4452 */           if (node.getParentID() == ue.getStructureID() && !corpHashMap.containsKey(new Integer(node.getStructureID()))) {
/*      */             
/* 4454 */             if (foundFirst)
/* 4455 */               result.append(','); 
/* 4456 */             result.append(' ');
/* 4457 */             result.append(node.getStructureID());
/* 4458 */             result.append(", '");
/* 4459 */             result.append(MilestoneHelper.urlEncode(node.getName()));
/* 4460 */             result.append('\'');
/* 4461 */             foundFirst = true;
/* 4462 */             tmpArray.addElement(node);
/*      */           } 
/*      */         } 
/*      */         
/* 4466 */         if (foundFirst) {
/*      */           
/* 4468 */           result.append(");\n");
/*      */         }
/*      */         else {
/*      */           
/* 4472 */           result.append(" 0, '[none available]');\n");
/*      */         } 
/*      */         
/* 4475 */         Vector tmpDivisionArray = new Vector();
/*      */         
/* 4477 */         for (int j = 0; j < tmpArray.size(); j++) {
/*      */           
/* 4479 */           Company node1 = (Company)tmpArray.elementAt(j);
/* 4480 */           result.append("b[");
/* 4481 */           result.append(node1.getStructureID());
/* 4482 */           result.append("] = new Array(");
/*      */           
/* 4484 */           Vector divisions = Cache.getInstance().getDivisions();
/*      */           
/* 4486 */           boolean foundSecond = false;
/* 4487 */           for (int k = 0; k < divisions.size(); k++) {
/*      */             
/* 4489 */             Division node2 = (Division)divisions.elementAt(k);
/*      */             
/* 4491 */             if (node2.getParentID() == node1.getStructureID() && !corpHashMap.containsKey(new Integer(node2.getStructureID()))) {
/*      */               
/* 4493 */               if (foundSecond)
/* 4494 */                 result.append(','); 
/* 4495 */               result.append(' ');
/* 4496 */               result.append(node2.getStructureID());
/* 4497 */               result.append(", '");
/*      */               
/* 4499 */               result.append(MilestoneHelper.urlEncode(node2.getName()));
/* 4500 */               result.append('\'');
/* 4501 */               foundSecond = true;
/* 4502 */               tmpDivisionArray.add(node2);
/*      */             } 
/*      */           } 
/*      */           
/* 4506 */           if (foundSecond) {
/*      */             
/* 4508 */             result.append(");\n");
/*      */           }
/*      */           else {
/*      */             
/* 4512 */             result.append(" 0, '[none available]');\n");
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/* 4517 */         for (int j = 0; j < tmpDivisionArray.size(); j++) {
/*      */           
/* 4519 */           Division node1 = (Division)tmpDivisionArray.elementAt(j);
/* 4520 */           result.append("c[");
/* 4521 */           result.append(node1.getStructureID());
/* 4522 */           result.append("] = new Array(");
/*      */           
/* 4524 */           Vector labels = Cache.getInstance().getLabels();
/*      */           
/* 4526 */           boolean foundSecond = false;
/* 4527 */           for (int k = 0; k < labels.size(); k++) {
/*      */             
/* 4529 */             Label node2 = (Label)labels.elementAt(k);
/*      */ 
/*      */ 
/*      */             
/* 4533 */             if (node2.getParentID() == node1.getStructureID() && !corpHashMap.containsKey(new Integer(node2.getStructureID()))) {
/*      */               
/* 4535 */               if (foundSecond)
/* 4536 */                 result.append(','); 
/* 4537 */               result.append(' ');
/* 4538 */               result.append(node2.getStructureID());
/* 4539 */               result.append(", '");
/*      */               
/* 4541 */               result.append(MilestoneHelper.urlEncode(node2.getName()));
/* 4542 */               result.append('\'');
/* 4543 */               foundSecond = true;
/*      */             } 
/*      */           } 
/*      */           
/* 4547 */           if (foundSecond) {
/*      */             
/* 4549 */             result.append(");\n");
/*      */           }
/*      */           else {
/*      */             
/* 4553 */             result.append(" 0, '[none available]');\n");
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 4559 */     corpHashMap = null;
/* 4560 */     return result.toString();
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
/* 4574 */     StringBuffer result = new StringBuffer(100);
/* 4575 */     String str = "";
/* 4576 */     String value = new String();
/* 4577 */     boolean foundFirstTemp = false;
/*      */     
/* 4579 */     User user = (User)context.getSessionValue("user");
/* 4580 */     Vector vUserCompanies = MilestoneHelper.getUserCompanies(context);
/* 4581 */     Vector vUserEnvironments = filterSelectionEnvironments(vUserCompanies);
/*      */ 
/*      */ 
/*      */     
/* 4585 */     HashMap corpHashMap = MilestoneHelper.buildActiveCorporateStructureHashMap();
/*      */ 
/*      */     
/* 4588 */     Hashtable labelsHash = new Hashtable();
/*      */ 
/*      */     
/* 4591 */     result.append("\n");
/* 4592 */     result.append("var aSearch = new Array();\n");
/* 4593 */     int arrayIndex = 0;
/*      */     
/* 4595 */     result.append("aSearch[0] = new Array(");
/* 4596 */     result.append(0);
/* 4597 */     result.append(", '");
/* 4598 */     result.append("All");
/* 4599 */     result.append("'");
/* 4600 */     foundFirstTemp = true;
/*      */     
/* 4602 */     for (int a = 0; a < vUserCompanies.size(); a++) {
/*      */       
/* 4604 */       Company ueTemp = (Company)vUserCompanies.elementAt(a);
/* 4605 */       if (ueTemp != null) {
/*      */ 
/*      */         
/* 4608 */         Vector labels = Cache.getInstance().getLabels();
/* 4609 */         for (int b = 0; b < labels.size(); b++) {
/*      */           
/* 4611 */           Label node = (Label)labels.elementAt(b);
/*      */           
/* 4613 */           if (node.getParent().getParentID() == ueTemp.getStructureID() && 
/* 4614 */             !corpHashMap.containsKey(new Integer(node.getStructureID()))) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 4627 */             String labelName = MilestoneHelper.urlEncode(node.getName());
/* 4628 */             if (!labelsHash.containsKey(labelName)) {
/*      */               
/* 4630 */               labelsHash.put(labelName, Integer.toString(node.getStructureID()));
/*      */             }
/*      */             else {
/*      */               
/* 4634 */               String hashValue = (String)labelsHash.get(labelName);
/* 4635 */               hashValue = String.valueOf(hashValue) + "," + Integer.toString(node.getStructureID());
/* 4636 */               labelsHash.put(labelName, hashValue);
/*      */             } 
/*      */             
/* 4639 */             foundFirstTemp = true;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 4646 */     if (!foundFirstTemp) {
/* 4647 */       result.append("'[none available]');\n");
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 4652 */       boolean firstPass = false;
/*      */       
/* 4654 */       String[] labelKeys = new String[labelsHash.size()];
/*      */ 
/*      */       
/* 4657 */       int x = 0;
/* 4658 */       for (Enumeration e = labelsHash.keys(); e.hasMoreElements(); x++) {
/*      */         
/* 4660 */         String hashKey = (String)e.nextElement();
/* 4661 */         labelKeys[x] = hashKey;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 4667 */       for (int h = 0; h < labelKeys.length; h++) {
/*      */         
/* 4669 */         String hashValue = (String)labelsHash.get(labelKeys[h]);
/*      */ 
/*      */         
/* 4672 */         result.append(',');
/* 4673 */         result.append(' ');
/* 4674 */         result.append("'" + hashValue + "'");
/* 4675 */         result.append(", '");
/* 4676 */         result.append(labelKeys[h]);
/* 4677 */         result.append('\'');
/*      */         
/* 4679 */         firstPass = true;
/*      */       } 
/* 4681 */       result.append(");\n");
/*      */     } 
/*      */ 
/*      */     
/* 4685 */     for (int i = 0; i < vUserCompanies.size(); i++) {
/*      */ 
/*      */       
/* 4688 */       Company ue = (Company)vUserCompanies.elementAt(i);
/* 4689 */       if (ue != null) {
/*      */ 
/*      */         
/* 4692 */         result.append("aSearch[");
/* 4693 */         result.append(ue.getStructureID());
/* 4694 */         result.append("] = new Array(");
/*      */         
/* 4696 */         boolean foundFirst = false;
/*      */         
/* 4698 */         result.append(0);
/* 4699 */         result.append(", '");
/* 4700 */         result.append("All");
/* 4701 */         result.append("'");
/* 4702 */         foundFirst = true;
/*      */         
/* 4704 */         Vector tmpArray = new Vector();
/*      */         
/* 4706 */         labelsHash.clear();
/*      */         
/* 4708 */         Vector labels = Cache.getInstance().getLabels();
/* 4709 */         for (int j = 0; j < labels.size(); j++) {
/*      */           
/* 4711 */           Label node = (Label)labels.elementAt(j);
/*      */           
/* 4713 */           if (node.getParent().getParentID() == ue.getStructureID() && 
/* 4714 */             !corpHashMap.containsKey(new Integer(node.getStructureID()))) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 4727 */             String labelName = MilestoneHelper.urlEncode(node.getName());
/* 4728 */             if (!labelsHash.containsKey(labelName)) {
/*      */ 
/*      */               
/* 4731 */               labelsHash.put(labelName, Integer.toString(node.getStructureID()));
/*      */             
/*      */             }
/*      */             else {
/*      */               
/* 4736 */               String hashValue = (String)labelsHash.get(labelName);
/* 4737 */               hashValue = String.valueOf(hashValue) + "," + Integer.toString(node.getStructureID());
/* 4738 */               labelsHash.put(labelName, hashValue);
/*      */             } 
/*      */             
/* 4741 */             foundFirst = true;
/* 4742 */             tmpArray.addElement(node);
/*      */           } 
/*      */         } 
/*      */         
/* 4746 */         if (foundFirst) {
/*      */ 
/*      */ 
/*      */           
/* 4750 */           boolean firstPass = false;
/*      */           
/* 4752 */           String[] labelKeys = new String[labelsHash.size()];
/*      */ 
/*      */           
/* 4755 */           int x = 0;
/* 4756 */           for (Enumeration e = labelsHash.keys(); e.hasMoreElements(); x++) {
/*      */             
/* 4758 */             String hashKey = (String)e.nextElement();
/* 4759 */             labelKeys[x] = hashKey;
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 4765 */           for (int h = 0; h < labelKeys.length; h++) {
/*      */             
/* 4767 */             String hashValue = (String)labelsHash.get(labelKeys[h]);
/*      */ 
/*      */             
/* 4770 */             result.append(',');
/*      */             
/* 4772 */             result.append(' ');
/* 4773 */             result.append("'" + hashValue + "'");
/* 4774 */             result.append(", '");
/* 4775 */             result.append(labelKeys[h]);
/* 4776 */             result.append('\'');
/*      */             
/* 4778 */             firstPass = true;
/*      */           } 
/*      */           
/* 4781 */           result.append(");\n");
/*      */         
/*      */         }
/*      */         else {
/*      */           
/* 4786 */           result.append(" 0, '[none available]');\n");
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 4791 */     return result.toString();
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
/* 4804 */     String explodedTotal = Integer.toString(selection.getPoQuantity() * selection.getNumberOfUnits());
/* 4805 */     context.putDelivery("explodedtotal", explodedTotal);
/* 4806 */     context.putDelivery("upc", selection.getUpc());
/* 4807 */     context.putDelivery("label", selection.getLabel().getName());
/* 4808 */     context.putDelivery("status", selection.getSelectionStatus().getName());
/* 4809 */     String typeConfig = String.valueOf(selection.getProductCategory().getName()) + "/" + selection.getReleaseType().getName() + " / " + selection.getSelectionConfig().getSelectionConfigurationName() + " / " + selection.getSelectionSubConfig().getSelectionSubConfigurationName();
/* 4810 */     context.putDelivery("typeConfig", typeConfig);
/*      */ 
/*      */     
/* 4813 */     String lastMfgUpdatedDateText = "";
/* 4814 */     if (selection.getLastMfgUpdateDate() != null) {
/* 4815 */       lastMfgUpdatedDateText = MilestoneHelper.getCustomFormatedDate(selection.getLastMfgUpdateDate(), "M/d/yyyy hh:mm:ss a 'ET'");
/*      */     }
/* 4817 */     context.putDelivery("lastUpdateDate", lastMfgUpdatedDateText);
/*      */     
/* 4819 */     String lastMfgUpdateUser = "";
/* 4820 */     if (selection.getLastMfgUpdatingUser() != null) {
/* 4821 */       lastMfgUpdateUser = selection.getLastMfgUpdatingUser().getName();
/*      */     }
/* 4823 */     context.putDelivery("lastUpdateUser", lastMfgUpdateUser);
/*      */     
/* 4825 */     return context;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getFilteredSellCodes(String selected) {
/* 4835 */     sellCodeText = "";
/* 4836 */     Vector vSellCodeList = Cache.getSellCodes();
/*      */     
/* 4838 */     for (int j = 0; j < vSellCodeList.size(); j++) {
/*      */       
/* 4840 */       String pc = (String)vSellCodeList.elementAt(j);
/*      */       
/* 4842 */       if (pc.substring(0, selected.length()).equalsIgnoreCase(selected)) {
/* 4843 */         sellCodeText = String.valueOf(sellCodeText) + pc + ",";
/*      */       }
/*      */     } 
/*      */     
/* 4847 */     return "," + sellCodeText.substring(0, sellCodeText.length() - 1);
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
/* 4861 */     sellCodeText = "";
/* 4862 */     Vector vSellCodeList = Cache.getSellCodes();
/*      */     
/* 4864 */     for (int i = 0; i < vSellCodeList.size(); i++)
/*      */     {
/* 4866 */       sellCodeText = String.valueOf(sellCodeText) + (String)vSellCodeList.get(i) + ",";
/*      */     }
/*      */ 
/*      */     
/* 4870 */     return "," + sellCodeText.substring(0, sellCodeText.length() - 1);
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
/* 4884 */     String sellCodeText = "";
/* 4885 */     Vector vSellCodeList = Cache.getSellCodesDPC();
/*      */     
/* 4887 */     for (int i = 0; i < vSellCodeList.size(); i++)
/*      */     {
/* 4889 */       sellCodeText = String.valueOf(sellCodeText) + (String)vSellCodeList.get(i) + ",";
/*      */     }
/*      */ 
/*      */     
/* 4893 */     if (sellCodeText.length() > 1) {
/* 4894 */       sellCodeText = "," + sellCodeText.substring(0, sellCodeText.length() - 1);
/*      */     }
/* 4896 */     return sellCodeText;
/*      */   }
/*      */   
/*      */   private String FormatForLegacy(String strIn) {
/* 4900 */     String strOut = "";
/* 4901 */     char[] arr = strIn.toCharArray();
/*      */     
/* 4903 */     for (int i = 0; i < arr.length; i++) {
/* 4904 */       if (arr[i] != '-' && arr[i] != ' ' && arr[i] != '/') {
/* 4905 */         strOut = String.valueOf(strOut) + arr[i];
/*      */       }
/*      */     } 
/* 4908 */     return strOut;
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
/* 4920 */     String RetValue = "";
/* 4921 */     strError = "";
/*      */     
/* 4923 */     String titleId = form.getStringValue("titleId");
/* 4924 */     String selectionNo = form.getStringValue("selectionNo");
/* 4925 */     String upc = form.getStringValue("upc");
/* 4926 */     String prefix = "";
/* 4927 */     String strDate = "";
/*      */     
/* 4929 */     String status = (selection.getSelectionStatus() != null && selection.getSelectionStatus().getName() != null) ? 
/* 4930 */       selection.getSelectionStatus().getName() : "";
/*      */     
/* 4932 */     if (!form.getStringValue("prefix").equals("-1")) {
/* 4933 */       prefix = form.getStringValue("prefix");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 4938 */     if (context.getParameter("generateSelection") != null && 
/* 4939 */       context.getParameter("generateSelection").equalsIgnoreCase("LPNG"))
/*      */     
/*      */     { 
/*      */       
/* 4943 */       selection.setPrefixID(null);
/*      */ 
/*      */       
/* 4946 */       String strProjectID = FormatForLegacy(selection.getProjectID());
/* 4947 */       String strUPC = FormatForLegacy(selection.getUpc());
/* 4948 */       String strSoundScan = FormatForLegacy(selection.getSoundScanGrp());
/*      */ 
/*      */       
/* 4951 */       if (selection.getConfigCode().startsWith("S")) {
/* 4952 */         titleId = "";
/*      */       }
/*      */ 
/*      */       
/* 4956 */       String strTitleID = "";
/* 4957 */       for (int j = 0; j < titleId.length(); j++) {
/* 4958 */         if (titleId.charAt(j) != '-') {
/* 4959 */           strTitleID = String.valueOf(strTitleID) + titleId.charAt(j);
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 4965 */       if (selection.getStreetDateString().equals("") && 
/* 4966 */         selection.getSelectionStatus().getAbbreviation().equalsIgnoreCase("TBS")) {
/*      */ 
/*      */         
/* 4969 */         strDate = "12/31/39";
/*      */       } else {
/* 4971 */         strDate = selection.getStreetDateString().trim();
/*      */       } 
/*      */       
/* 4974 */       PnrCommunication pnr = PnrCommunication.getInstance();
/*      */       
/* 4976 */       String userIdStr = ((User)context.getSession().getAttribute("user")).getLogin();
/* 4977 */       if (userIdStr == null || userIdStr.equals(""))
/* 4978 */         userIdStr = "Mileston"; 
/* 4979 */       String strReply = PnrCommunication.GetPNR(userIdStr, 
/* 4980 */           selection.getOperCompany().trim(), 
/* 4981 */           selection.getSuperLabel().trim(), 
/* 4982 */           selection.getConfigCode().trim(), 
/* 4983 */           strTitleID, 
/* 4984 */           selection.getTitle().trim(), 
/* 4985 */           selection.getArtistFirstName().trim(), 
/* 4986 */           selection.getArtistLastName().trim(), 
/* 4987 */           strDate, 
/* 4988 */           selection.getSubLabel().trim(), 
/* 4989 */           strProjectID.trim(), 
/* 4990 */           strUPC.trim(), 
/* 4991 */           strSoundScan.trim());
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 4996 */       if (strReply.indexOf("BROKER ERROR:") != -1) {
/* 4997 */         strError = strReply.trim();
/*      */       }
/*      */       else {
/*      */         
/* 5001 */         strError = strReply.substring(312, strReply.length());
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 5006 */       if (strError.trim().equals("")) {
/*      */ 
/*      */         
/* 5009 */         if (titleId.trim().equals("")) {
/* 5010 */           titleId = strReply.substring(23, 33);
/*      */ 
/*      */           
/* 5013 */           titleId = String.valueOf(titleId.substring(0, titleId.length() - 2)) + "-" + titleId.substring(titleId.length() - 2, titleId.length());
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 5018 */         selectionNo = strReply.substring(33, 43);
/*      */       } else {
/*      */         
/* 5021 */         return strError.replace('\'', ' ');
/*      */       } 
/*      */       
/* 5024 */       selectionNo = String.valueOf(selectionNo.substring(0, selectionNo.length() - 2)) + "-" + selectionNo.substring(selectionNo.length() - 2, selectionNo.length());
/*      */        }
/*      */     
/* 5027 */     else if (context.getParameter("generateSelection") != null && 
/* 5028 */       context.getParameter("generateSelection").equalsIgnoreCase("TPNG"))
/*      */     
/*      */     { 
/*      */       
/* 5032 */       selectionNo = SelectionManager.getInstance().getSequencedSelectionNumber();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 5037 */       if (selection.getReleaseType().getAbbreviation().equalsIgnoreCase("CO") && !selection.getConfigCode().startsWith("S") && (
/* 5038 */         titleId.equals("") || titleId.startsWith("TEMP"))) {
/* 5039 */         titleId = selectionNo;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 5046 */       if (selection.getReleaseType().getAbbreviation().equalsIgnoreCase("CO") && selection.getConfigCode().startsWith("S"))
/*      */       {
/* 5048 */         titleId = selectionNo;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 5054 */       if (selection.getReleaseType().getAbbreviation().equalsIgnoreCase("PR"))
/*      */       {
/* 5056 */         titleId = String.valueOf(prefix) + selectionNo;
/*      */ 
/*      */       
/*      */       }
/*      */        }
/*      */     
/*      */     else
/*      */     
/*      */     { 
/* 5065 */       if (!status.equalsIgnoreCase("Cancelled") && SelectionManager.getInstance().isSelectionIDDuplicate(prefix, selectionNo, selection.getSelectionID(), selection.getIsDigital()))
/*      */       {
/* 5067 */         return "The Local Product No entered already exist in our database.  Please enter a new one and resubmit.";
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
/* 5121 */       if (!selection.getReleaseType().getAbbreviation().equalsIgnoreCase("PR"))
/*      */       
/* 5123 */       { if (selection.getReleaseType().getAbbreviation().equalsIgnoreCase("CO") && selection.getConfigCode().startsWith("S"))
/*      */         {
/* 5125 */           titleId = String.valueOf(prefix) + selectionNo;
/*      */         }
/*      */ 
/*      */         
/* 5129 */         selection.setSelectionNo(selectionNo);
/* 5130 */         selection.setTitleID(titleId);
/*      */         
/* 5132 */         return RetValue; }  titleId = String.valueOf(prefix) + selectionNo; }  selection.setSelectionNo(selectionNo); selection.setTitleID(titleId); return RetValue;
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
/* 5154 */     String copyVisible = "false";
/* 5155 */     String saveVisible = "false";
/* 5156 */     String deleteVisible = "false";
/* 5157 */     String newVisible = "false";
/*      */     
/* 5159 */     if (level > 1) {
/*      */       
/* 5161 */       saveVisible = "true";
/* 5162 */       copyVisible = "true";
/* 5163 */       deleteVisible = "true";
/*      */       
/* 5165 */       if (selection.getSelectionID() > 0) {
/* 5166 */         newVisible = "true";
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 5171 */     if (command.indexOf("new") > -1 || command.indexOf("copy") > -1) {
/*      */       
/* 5173 */       saveVisible = "true";
/* 5174 */       copyVisible = "false";
/* 5175 */       deleteVisible = "false";
/* 5176 */       newVisible = "false";
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 5181 */     context.putDelivery("saveVisible", saveVisible);
/*      */     
/* 5183 */     context.putDelivery("copyVisible", copyVisible);
/*      */     
/* 5185 */     context.putDelivery("deleteVisible", deleteVisible);
/*      */     
/* 5187 */     context.putDelivery("newVisible", newVisible);
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
/* 5200 */     int level = 0;
/*      */     
/* 5202 */     if (selection != null && selection.getSelectionID() > -1) {
/*      */ 
/*      */ 
/*      */       
/* 5206 */       Environment env = selection.getEnvironment();
/*      */       
/* 5208 */       CompanyAcl companyAcl = MilestoneHelper.getScreenPermissions(env, user);
/*      */       
/* 5210 */       if (companyAcl != null) {
/* 5211 */         level = companyAcl.getAccessSelection();
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 5216 */     return level;
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
/* 5231 */     int level = 0;
/*      */     
/* 5233 */     if (selection != null && selection.getSelectionID() > -1) {
/*      */ 
/*      */ 
/*      */       
/* 5237 */       Environment env = selection.getEnvironment();
/* 5238 */       CompanyAcl companyAcl = MilestoneHelper.getScreenPermissions(env, user);
/* 5239 */       if (companyAcl != null) {
/* 5240 */         level = companyAcl.getAccessManufacturing();
/*      */       }
/*      */     } 
/* 5243 */     return level;
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
/* 5262 */     Vector result = new Vector();
/*      */     
/* 5264 */     if (companies != null && companies.size() > 0)
/*      */     {
/* 5266 */       for (int i = 0; i < companies.size(); i++) {
/*      */ 
/*      */         
/* 5269 */         Company company = (Company)companies.get(i);
/* 5270 */         String name = company.getName();
/*      */ 
/*      */         
/* 5273 */         if (!name.equalsIgnoreCase("UML") && 
/* 5274 */           !name.equalsIgnoreCase("Enterprise")) {
/* 5275 */           result.add(company);
/*      */         }
/*      */       } 
/*      */     }
/* 5279 */     return result;
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
/* 5299 */     Vector result = new Vector();
/* 5300 */     Vector cmpAclList = user.getAcl().getCompanyAcl();
/* 5301 */     HashMap cmpEditRight = new HashMap();
/*      */ 
/*      */ 
/*      */     
/* 5305 */     if (cmpAclList != null) {
/* 5306 */       for (int n = 0; n < cmpAclList.size(); n++) {
/*      */         
/* 5308 */         CompanyAcl cmpAcl = (CompanyAcl)cmpAclList.get(n);
/* 5309 */         if (cmpAcl.getAccessSelection() == 2) {
/* 5310 */           cmpEditRight.put(new Integer(cmpAcl.getCompanyId()), new Integer(n));
/*      */         }
/*      */       } 
/*      */     }
/* 5314 */     if (companies != null && companies.size() > 0)
/*      */     {
/* 5316 */       for (int i = 0; i < companies.size(); i++) {
/*      */ 
/*      */         
/* 5319 */         Company company = (Company)companies.get(i);
/* 5320 */         String name = company.getName();
/*      */         
/* 5322 */         if (cmpAclList == null) {
/* 5323 */           if (!name.equalsIgnoreCase("UML") && !name.equalsIgnoreCase("Enterprise")) {
/* 5324 */             result.add(company);
/*      */           
/*      */           }
/*      */         
/*      */         }
/* 5329 */         else if (!name.equalsIgnoreCase("UML") && !name.equalsIgnoreCase("Enterprise") && 
/* 5330 */           cmpEditRight.containsKey(new Integer(company.getStructureID()))) {
/* 5331 */           result.add(company);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/* 5336 */     return result;
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
/* 5356 */     Vector result = new Vector();
/*      */     
/* 5358 */     if (csoVector != null && csoVector.size() > 0)
/*      */     {
/*      */       
/* 5361 */       for (int i = 0; i < csoVector.size(); i++) {
/*      */ 
/*      */         
/* 5364 */         CorporateStructureObject cso = (CorporateStructureObject)csoVector.get(i);
/*      */         
/* 5366 */         String abbrev = cso.getStructureAbbreviation();
/*      */         
/* 5368 */         if (!abbrev.equalsIgnoreCase("UML") && 
/* 5369 */           !abbrev.equalsIgnoreCase("ENT")) {
/* 5370 */           result.add(cso);
/*      */         }
/*      */       } 
/*      */     }
/* 5374 */     return result;
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
/* 5389 */     form = new Form(this.application, "selectionForm", this.application.getInfrastructure().getServletURL(), "POST");
/* 5390 */     form.addElement(new FormHidden("cmd", "selection-editor", true));
/* 5391 */     form.addElement(new FormHidden("OrderBy", "", true));
/*      */ 
/*      */     
/* 5394 */     if (context.getSessionValue("NOTEPAD_SELECTION_VISIBLE") != null) {
/* 5395 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_SELECTION_VISIBLE"));
/*      */     }
/* 5397 */     Vector companies = MilestoneHelper.getUserCompanies(context);
/*      */     
/* 5399 */     addSelectionSearchElements(context, null, form, companies, true);
/* 5400 */     context.putDelivery("Form", form);
/*      */     
/* 5402 */     return context.includeJSP("blank-selection-editor.jsp");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean multSelectionEditor(Dispatcher dispatcher, Context context, String command) {
/* 5412 */     Form multSelectionForm = new Form(this.application, "multSelectionForm", 
/* 5413 */         this.application.getInfrastructure().getServletURL(), "POST");
/* 5414 */     Vector multSelections = null;
/*      */     
/* 5416 */     User user = (User)context.getSession().getAttribute("user");
/* 5417 */     Selection selection = (Selection)context.getSessionValue("Selection");
/*      */     
/* 5419 */     int secureLevel = getSelectionPermissions(selection, user);
/* 5420 */     setButtonVisibilities(selection, user, context, secureLevel, command);
/*      */ 
/*      */     
/* 5423 */     if (selection != null && selection.getMultSelections() != null) {
/*      */       
/* 5425 */       multSelections = selection.getMultSelections();
/*      */       
/* 5427 */       for (int j = 0; j < multSelections.size(); j++) {
/*      */         
/* 5429 */         MultSelection multSel = (MultSelection)multSelections.get(j);
/*      */ 
/*      */         
/* 5432 */         FormTextField selectionNo = new FormTextField("selectionNo" + j, multSel.getSelectionNo(), false, 15, 20);
/* 5433 */         FormTextField upc = new FormTextField("upc" + j, multSel.getUpc(), false, 18, 20);
/* 5434 */         FormTextField description = new FormTextField("description" + j, multSel.getDescription(), false, 25, 100);
/*      */ 
/*      */         
/* 5437 */         upc.addFormEvent("onKeyPress", "return checkNumbersOnly(window.event.keyCode);");
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 5442 */         multSelectionForm.addElement(selectionNo);
/* 5443 */         multSelectionForm.addElement(upc);
/* 5444 */         multSelectionForm.addElement(description);
/*      */       } 
/*      */     } 
/*      */     
/* 5448 */     multSelectionForm.addElement(new FormHidden("cmd", command, true));
/*      */     
/* 5450 */     context.putDelivery("Selection", selection);
/* 5451 */     context.putDelivery("Form", multSelectionForm);
/*      */     
/* 5453 */     return context.includeJSP("multSelection-editor.jsp");
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean multSelectionEditorAdd(Dispatcher dispatcher, Context context, String command) {
/* 5458 */     Form multSelectionForm = new Form(this.application, "multSelectionForm", 
/* 5459 */         this.application.getInfrastructure().getServletURL(), "POST");
/* 5460 */     User user = (User)context.getSession().getAttribute("user");
/*      */     
/* 5462 */     Selection selection = (Selection)context.getSessionValue("Selection");
/* 5463 */     Vector multSelections = null;
/*      */     
/* 5465 */     multSelections = selection.getMultSelections();
/*      */     
/* 5467 */     if (multSelections == null) {
/*      */       
/* 5469 */       multSelections = new Vector();
/* 5470 */       MultSelection temp = new MultSelection();
/* 5471 */       temp.setRelease_id(selection.getSelectionID());
/* 5472 */       multSelections.add(temp);
/* 5473 */       selection.setMultSelections(multSelections);
/*      */     }
/*      */     else {
/*      */       
/* 5477 */       MultSelection temp = new MultSelection();
/* 5478 */       temp.setRelease_id(selection.getSelectionID());
/* 5479 */       multSelections.add(temp);
/* 5480 */       selection.setMultSelections(multSelections);
/*      */     } 
/*      */     
/* 5483 */     if (selection != null && selection.getMultSelections() != null)
/*      */     {
/* 5485 */       for (int j = 0; j < multSelections.size(); j++) {
/*      */ 
/*      */         
/* 5488 */         MultSelection multSel = (MultSelection)multSelections.get(j);
/*      */         
/* 5490 */         FormTextField selectionNo = new FormTextField("selectionNo" + j, multSel.getSelectionNo(), false, 15, 20);
/* 5491 */         FormTextField upc = new FormTextField("upc" + j, multSel.getUpc(), false, 18, 20);
/* 5492 */         FormTextField description = new FormTextField("description" + j, multSel.getDescription(), false, 25, 100);
/*      */ 
/*      */         
/* 5495 */         upc.addFormEvent("onKeyPress", "return checkNumbersOnly(window.event.keyCode);");
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 5500 */         multSelectionForm.addElement(selectionNo);
/* 5501 */         multSelectionForm.addElement(upc);
/* 5502 */         multSelectionForm.addElement(description);
/*      */       } 
/*      */     }
/*      */     
/* 5506 */     multSelectionForm.setValues(context);
/* 5507 */     multSelectionForm.addElement(new FormHidden("cmd", command, true));
/* 5508 */     context.putSessionValue("Selection", selection);
/* 5509 */     context.putDelivery("Form", multSelectionForm);
/*      */     
/* 5511 */     return context.includeJSP("multSelection-editor.jsp");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean multSelectionEditorCancel(Dispatcher dispatcher, Context context, String command) {
/* 5517 */     Form multSelectionForm = new Form(this.application, "multSelectionForm", 
/* 5518 */         this.application.getInfrastructure().getServletURL(), "POST");
/* 5519 */     User user = (User)context.getSession().getAttribute("user");
/*      */     
/* 5521 */     Selection sessionSelection = (Selection)context.getSessionValue("Selection");
/*      */     
/* 5523 */     Selection selection = null;
/* 5524 */     if (sessionSelection.getSelectionID() < 0) {
/* 5525 */       selection = sessionSelection;
/*      */     } else {
/* 5527 */       selection = SelectionManager.getInstance().getSelectionHeader(sessionSelection.getSelectionID());
/*      */     } 
/* 5529 */     Vector multSelections = null;
/*      */ 
/*      */     
/* 5532 */     if (selection != null) {
/*      */       
/* 5534 */       multSelections = selection.getMultSelections();
/*      */       
/* 5536 */       if (selection != null && selection.getMultSelections() != null) {
/*      */ 
/*      */         
/* 5539 */         Vector newMultSelections = new Vector();
/*      */         
/* 5541 */         for (int j = 0; j < multSelections.size(); j++) {
/*      */           
/* 5543 */           MultSelection multSel = (MultSelection)multSelections.get(j);
/*      */ 
/*      */ 
/*      */           
/* 5547 */           if (sessionSelection.getSelectionID() >= 0 || 
/* 5548 */             multSel.getSelectionNo() != null || multSel.getUpc() != null || 
/* 5549 */             multSel.getDescription() != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 5556 */             FormTextField selectionNo = new FormTextField("selectionNo" + j, multSel.getSelectionNo(), false, 15, 20);
/* 5557 */             FormTextField upc = new FormTextField("upc" + j, multSel.getUpc(), false, 18, 20);
/* 5558 */             FormTextField description = new FormTextField("description" + j, multSel.getDescription(), false, 25, 100);
/*      */ 
/*      */             
/* 5561 */             upc.addFormEvent("onKeyPress", "return checkNumbersOnly(window.event.keyCode);");
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 5566 */             multSelectionForm.addElement(selectionNo);
/* 5567 */             multSelectionForm.addElement(upc);
/* 5568 */             multSelectionForm.addElement(description);
/*      */             
/* 5570 */             newMultSelections.add(multSel);
/*      */           } 
/* 5572 */         }  selection.setMultSelections(newMultSelections);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 5577 */     multSelectionForm.addElement(new FormHidden("cmd", command, true));
/* 5578 */     context.putSessionValue("Selection", selection);
/* 5579 */     context.putDelivery("Form", multSelectionForm);
/*      */ 
/*      */     
/* 5582 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean multSelectionEditorDelete(Dispatcher dispatcher, Context context, String command) {
/* 5588 */     Form multSelectionForm = new Form(this.application, "multSelectionForm", 
/* 5589 */         this.application.getInfrastructure().getServletURL(), "POST");
/* 5590 */     Form newMultSelectionForm = new Form(this.application, "multSelectionForm", 
/* 5591 */         this.application.getInfrastructure().getServletURL(), "POST");
/* 5592 */     User user = (User)context.getSession().getAttribute("user");
/*      */     
/* 5594 */     Selection selection = (Selection)context.getSessionValue("Selection");
/* 5595 */     Vector multSelections = null;
/* 5596 */     int multSelectionsPK = -1;
/*      */     
/* 5598 */     if (context.getRequestValue("multSelectionsPK") != null) {
/* 5599 */       multSelectionsPK = Integer.parseInt(context.getRequestValue("multSelectionsPK"));
/*      */     }
/* 5601 */     multSelections = selection.getMultSelections();
/*      */     
/* 5603 */     if (selection != null && selection.getMultSelections() != null) {
/*      */ 
/*      */ 
/*      */       
/* 5607 */       for (int j = 0; j < multSelections.size(); j++) {
/*      */         
/* 5609 */         MultSelection multSel = (MultSelection)multSelections.get(j);
/*      */         
/* 5611 */         FormTextField selectionNo = new FormTextField("selectionNo" + j, "", false, 15, 20);
/* 5612 */         FormTextField upc = new FormTextField("upc" + j, "", false, 18, 20);
/* 5613 */         FormTextField description = new FormTextField("description" + j, "", false, 25, 100);
/*      */ 
/*      */         
/* 5616 */         upc.addFormEvent("onKeyPress", "return checkNumbersOnly(window.event.keyCode);");
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 5621 */         multSelectionForm.addElement(selectionNo);
/* 5622 */         multSelectionForm.addElement(upc);
/* 5623 */         multSelectionForm.addElement(description);
/*      */       } 
/* 5625 */       multSelectionForm.setValues(context);
/*      */       
/* 5627 */       Vector newMultSelections = new Vector();
/*      */       
/* 5629 */       for (int j = 0; j < multSelections.size(); j++) {
/*      */         
/* 5631 */         MultSelection multSel = (MultSelection)multSelections.get(j);
/*      */ 
/*      */         
/* 5634 */         multSel.setSelectionNo(multSelectionForm.getStringValue("selectionNo" + j));
/* 5635 */         multSel.setUpc(multSelectionForm.getStringValue("upc" + j));
/* 5636 */         multSel.setDescription(multSelectionForm.getStringValue("description" + j));
/* 5637 */         newMultSelections.add(multSel);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 5642 */       if (multSelectionsPK > -1) {
/* 5643 */         newMultSelections.remove(multSelectionsPK);
/*      */       }
/* 5645 */       selection.setMultSelections(newMultSelections);
/*      */ 
/*      */       
/* 5648 */       for (int j = 0; j < newMultSelections.size(); j++) {
/*      */         
/* 5650 */         MultSelection multSel = (MultSelection)newMultSelections.get(j);
/*      */         
/* 5652 */         FormTextField selectionNo = new FormTextField("selectionNo" + j, multSel.getSelectionNo(), false, 15, 20);
/* 5653 */         FormTextField upc = new FormTextField("upc" + j, multSel.getUpc(), false, 18, 20);
/* 5654 */         FormTextField description = new FormTextField("description" + j, multSel.getDescription(), false, 25, 100);
/*      */ 
/*      */         
/* 5657 */         upc.addFormEvent("onKeyPress", "return checkNumbersOnly(window.event.keyCode);");
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 5662 */         newMultSelectionForm.addElement(selectionNo);
/* 5663 */         newMultSelectionForm.addElement(upc);
/* 5664 */         newMultSelectionForm.addElement(description);
/*      */       } 
/*      */     } 
/*      */     
/* 5668 */     newMultSelectionForm.addElement(new FormHidden("cmd", command, true));
/* 5669 */     context.putSessionValue("Selection", selection);
/* 5670 */     context.putDelivery("Form", newMultSelectionForm);
/*      */     
/* 5672 */     return context.includeJSP("multSelection-editor.jsp");
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean multSelectionEditorSave(Dispatcher dispatcher, Context context, String command) {
/* 5677 */     Form multSelectionForm = new Form(this.application, "multSelectionForm", 
/* 5678 */         this.application.getInfrastructure().getServletURL(), "POST");
/* 5679 */     User user = (User)context.getSession().getAttribute("user");
/*      */     
/* 5681 */     Selection selection = (Selection)context.getSessionValue("Selection");
/* 5682 */     Vector multSelections = null;
/*      */     
/* 5684 */     multSelections = selection.getMultSelections();
/*      */ 
/*      */ 
/*      */     
/* 5688 */     if (selection != null && selection.getMultSelections() != null) {
/*      */       
/* 5690 */       for (int j = 0; j < multSelections.size(); j++) {
/*      */         
/* 5692 */         MultSelection multSel = (MultSelection)multSelections.get(j);
/*      */         
/* 5694 */         FormTextField selectionNo = new FormTextField("selectionNo" + j, "", false, 15, 20);
/* 5695 */         FormTextField upc = new FormTextField("upc" + j, "", false, 18, 20);
/* 5696 */         FormTextField description = new FormTextField("description" + j, "", false, 25, 100);
/*      */ 
/*      */         
/* 5699 */         upc.addFormEvent("onKeyPress", "return checkNumbersOnly(window.event.keyCode);");
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 5704 */         multSelectionForm.addElement(selectionNo);
/* 5705 */         multSelectionForm.addElement(upc);
/* 5706 */         multSelectionForm.addElement(description);
/*      */       } 
/*      */       
/* 5709 */       multSelectionForm.setValues(context);
/*      */       
/* 5711 */       Vector newMultSelections = new Vector();
/*      */       
/* 5713 */       for (int j = 0; j < multSelections.size(); j++) {
/*      */         
/* 5715 */         MultSelection multSel = (MultSelection)multSelections.get(j);
/*      */         
/* 5717 */         multSel.setSelectionNo(multSelectionForm.getStringValue("selectionNo" + j));
/*      */         
/* 5719 */         multSel.setUpc(MilestoneHelper_2.reformat_UPC_SSG_SGC_forSave(multSelectionForm.getStringValue("upc" + j), "UPC", false, true));
/* 5720 */         multSel.setDescription(multSelectionForm.getStringValue("description" + j));
/* 5721 */         newMultSelections.add(multSel);
/*      */       } 
/*      */       
/* 5724 */       selection.setMultSelections(newMultSelections);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 5729 */       if (selection.getSelectionID() > 0) {
/* 5730 */         SelectionManager.getInstance().saveSelection(selection, user);
/*      */       }
/*      */     } 
/*      */     
/* 5734 */     multSelectionForm.addElement(new FormHidden("cmd", command, true));
/*      */     
/* 5736 */     context.putSessionValue("Selection", selection);
/* 5737 */     context.putDelivery("Form", multSelectionForm);
/*      */     
/* 5739 */     return true;
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
/* 5751 */     Form multOtherContactForm = new Form(this.application, "multOtherContactForm", 
/* 5752 */         this.application.getInfrastructure().getServletURL(), "POST");
/* 5753 */     Vector multOtherContacts = null;
/*      */     
/* 5755 */     User user = (User)context.getSession().getAttribute("user");
/* 5756 */     Selection selection = (Selection)context.getSessionValue("Selection");
/*      */     
/* 5758 */     int secureLevel = getSelectionPermissions(selection, user);
/* 5759 */     setButtonVisibilities(selection, user, context, secureLevel, command);
/*      */ 
/*      */     
/* 5762 */     if (selection != null && selection.getMultOtherContacts() != null) {
/*      */       
/* 5764 */       multOtherContacts = selection.getMultOtherContacts();
/*      */       
/* 5766 */       for (int j = 0; j < multOtherContacts.size(); j++) {
/*      */         
/* 5768 */         MultOtherContact multOth = (MultOtherContact)multOtherContacts.get(j);
/*      */ 
/*      */         
/* 5771 */         FormTextField name = new FormTextField("name" + j, multOth.getName(), false, 25, 150);
/* 5772 */         FormTextField description = new FormTextField("description" + j, multOth.getDescription(), false, 25, 150);
/*      */         
/* 5774 */         multOtherContactForm.addElement(name);
/* 5775 */         multOtherContactForm.addElement(description);
/*      */       } 
/*      */     } 
/*      */     
/* 5779 */     multOtherContactForm.addElement(new FormHidden("cmd", command, true));
/*      */     
/* 5781 */     context.putDelivery("Selection", selection);
/* 5782 */     context.putDelivery("Form", multOtherContactForm);
/*      */     
/* 5784 */     return context.includeJSP("multOtherContact-editor.jsp");
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean multOtherContactEditorAdd(Dispatcher dispatcher, Context context, String command) {
/* 5789 */     Form multOtherContactForm = new Form(this.application, "multOtherContactForm", 
/* 5790 */         this.application.getInfrastructure().getServletURL(), "POST");
/* 5791 */     User user = (User)context.getSession().getAttribute("user");
/*      */     
/* 5793 */     Selection selection = (Selection)context.getSessionValue("Selection");
/* 5794 */     Vector multOtherContacts = null;
/*      */     
/* 5796 */     multOtherContacts = selection.getMultOtherContacts();
/*      */     
/* 5798 */     if (multOtherContacts == null) {
/*      */       
/* 5800 */       multOtherContacts = new Vector();
/* 5801 */       MultOtherContact temp = new MultOtherContact();
/* 5802 */       temp.setRelease_id(selection.getSelectionID());
/* 5803 */       multOtherContacts.add(temp);
/* 5804 */       selection.setMultOtherContacts(multOtherContacts);
/*      */     }
/*      */     else {
/*      */       
/* 5808 */       MultOtherContact temp = new MultOtherContact();
/* 5809 */       temp.setRelease_id(selection.getSelectionID());
/* 5810 */       multOtherContacts.add(temp);
/* 5811 */       selection.setMultOtherContacts(multOtherContacts);
/*      */     } 
/*      */     
/* 5814 */     if (selection != null && selection.getMultOtherContacts() != null)
/*      */     {
/* 5816 */       for (int j = 0; j < multOtherContacts.size(); j++) {
/*      */ 
/*      */         
/* 5819 */         MultOtherContact multOth = (MultOtherContact)multOtherContacts.get(j);
/*      */         
/* 5821 */         FormTextField name = new FormTextField("name" + j, multOth.getName(), false, 25, 150);
/* 5822 */         FormTextField description = new FormTextField("description" + j, multOth.getDescription(), false, 25, 150);
/*      */         
/* 5824 */         multOtherContactForm.addElement(name);
/* 5825 */         multOtherContactForm.addElement(description);
/*      */       } 
/*      */     }
/*      */     
/* 5829 */     multOtherContactForm.setValues(context);
/* 5830 */     multOtherContactForm.addElement(new FormHidden("cmd", command, true));
/* 5831 */     context.putSessionValue("Selection", selection);
/* 5832 */     context.putDelivery("Form", multOtherContactForm);
/*      */     
/* 5834 */     return context.includeJSP("multOtherContact-editor.jsp");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean multOtherContactEditorCancel(Dispatcher dispatcher, Context context, String command) {
/* 5840 */     Form multOtherContactForm = new Form(this.application, "multOtherContactForm", 
/* 5841 */         this.application.getInfrastructure().getServletURL(), "POST");
/* 5842 */     User user = (User)context.getSession().getAttribute("user");
/*      */     
/* 5844 */     Selection sessionSelection = (Selection)context.getSessionValue("Selection");
/*      */     
/* 5846 */     Selection selection = null;
/* 5847 */     if (sessionSelection.getSelectionID() < 0) {
/* 5848 */       selection = sessionSelection;
/*      */     } else {
/* 5850 */       selection = SelectionManager.getInstance().getSelectionHeader(sessionSelection.getSelectionID());
/*      */     } 
/* 5852 */     Vector multOtherContacts = null;
/*      */ 
/*      */     
/* 5855 */     if (selection != null) {
/*      */       
/* 5857 */       multOtherContacts = selection.getMultOtherContacts();
/*      */       
/* 5859 */       if (selection != null && selection.getMultOtherContacts() != null) {
/*      */ 
/*      */         
/* 5862 */         Vector newMultOtherContacts = new Vector();
/*      */         
/* 5864 */         for (int j = 0; j < multOtherContacts.size(); j++) {
/*      */           
/* 5866 */           MultOtherContact multOth = (MultOtherContact)multOtherContacts.get(j);
/*      */ 
/*      */ 
/*      */           
/* 5870 */           if (sessionSelection.getSelectionID() >= 0 || 
/* 5871 */             multOth.getName() != null || multOth.getDescription() != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 5878 */             FormTextField name = new FormTextField("name" + j, multOth.getName(), false, 25, 150);
/* 5879 */             FormTextField description = new FormTextField("description" + j, multOth.getDescription(), false, 25, 150);
/*      */             
/* 5881 */             multOtherContactForm.addElement(name);
/* 5882 */             multOtherContactForm.addElement(description);
/*      */             
/* 5884 */             newMultOtherContacts.add(multOth);
/*      */           } 
/* 5886 */         }  selection.setMultOtherContacts(newMultOtherContacts);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 5891 */     multOtherContactForm.addElement(new FormHidden("cmd", command, true));
/* 5892 */     context.putSessionValue("Selection", selection);
/* 5893 */     context.putDelivery("Form", multOtherContactForm);
/*      */ 
/*      */     
/* 5896 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean multOtherContactEditorDelete(Dispatcher dispatcher, Context context, String command) {
/* 5902 */     Form multOtherContactForm = new Form(this.application, "multOtherContactForm", 
/* 5903 */         this.application.getInfrastructure().getServletURL(), "POST");
/* 5904 */     Form newMultOtherContactForm = new Form(this.application, "multOtherContactForm", 
/* 5905 */         this.application.getInfrastructure().getServletURL(), "POST");
/* 5906 */     User user = (User)context.getSession().getAttribute("user");
/*      */     
/* 5908 */     Selection selection = (Selection)context.getSessionValue("Selection");
/* 5909 */     Vector multOtherContacts = null;
/* 5910 */     int multOtherContactsPK = -1;
/*      */     
/* 5912 */     if (context.getRequestValue("multOtherContactsPK") != null) {
/* 5913 */       multOtherContactsPK = Integer.parseInt(context.getRequestValue("multOtherContactsPK"));
/*      */     }
/* 5915 */     multOtherContacts = selection.getMultOtherContacts();
/*      */     
/* 5917 */     if (selection != null && selection.getMultOtherContacts() != null) {
/*      */ 
/*      */ 
/*      */       
/* 5921 */       for (int j = 0; j < multOtherContacts.size(); j++) {
/*      */         
/* 5923 */         MultOtherContact multOth = (MultOtherContact)multOtherContacts.get(j);
/*      */         
/* 5925 */         FormTextField name = new FormTextField("name" + j, "", false, 25, 150);
/* 5926 */         FormTextField description = new FormTextField("description" + j, "", false, 25, 150);
/*      */         
/* 5928 */         multOtherContactForm.addElement(name);
/* 5929 */         multOtherContactForm.addElement(description);
/*      */       } 
/* 5931 */       multOtherContactForm.setValues(context);
/*      */       
/* 5933 */       Vector newMultOtherContacts = new Vector();
/*      */       
/* 5935 */       for (int j = 0; j < multOtherContacts.size(); j++) {
/*      */         
/* 5937 */         MultOtherContact multOth = (MultOtherContact)multOtherContacts.get(j);
/*      */ 
/*      */         
/* 5940 */         multOth.setName(multOtherContactForm.getStringValue("name" + j));
/* 5941 */         multOth.setDescription(multOtherContactForm.getStringValue("description" + j));
/* 5942 */         newMultOtherContacts.add(multOth);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 5947 */       if (multOtherContactsPK > -1) {
/* 5948 */         newMultOtherContacts.remove(multOtherContactsPK);
/*      */       }
/* 5950 */       selection.setMultOtherContacts(newMultOtherContacts);
/*      */ 
/*      */       
/* 5953 */       for (int j = 0; j < newMultOtherContacts.size(); j++) {
/*      */         
/* 5955 */         MultOtherContact multOth = (MultOtherContact)newMultOtherContacts.get(j);
/*      */         
/* 5957 */         FormTextField name = new FormTextField("name" + j, multOth.getName(), false, 25, 150);
/* 5958 */         FormTextField description = new FormTextField("description" + j, multOth.getDescription(), false, 25, 150);
/*      */         
/* 5960 */         newMultOtherContactForm.addElement(name);
/* 5961 */         newMultOtherContactForm.addElement(description);
/*      */       } 
/*      */     } 
/*      */     
/* 5965 */     newMultOtherContactForm.addElement(new FormHidden("cmd", command, true));
/* 5966 */     context.putSessionValue("Selection", selection);
/* 5967 */     context.putDelivery("Form", newMultOtherContactForm);
/*      */     
/* 5969 */     return context.includeJSP("multOtherContact-editor.jsp");
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean multOtherContactEditorSave(Dispatcher dispatcher, Context context, String command) {
/* 5974 */     Form multOtherContactForm = new Form(this.application, "multOtherContactForm", 
/* 5975 */         this.application.getInfrastructure().getServletURL(), "POST");
/* 5976 */     User user = (User)context.getSession().getAttribute("user");
/*      */     
/* 5978 */     Selection selection = (Selection)context.getSessionValue("Selection");
/* 5979 */     Vector multOtherContacts = null;
/*      */     
/* 5981 */     multOtherContacts = selection.getMultOtherContacts();
/*      */ 
/*      */ 
/*      */     
/* 5985 */     if (selection != null && selection.getMultOtherContacts() != null) {
/*      */       
/* 5987 */       for (int j = 0; j < multOtherContacts.size(); j++) {
/*      */         
/* 5989 */         MultOtherContact multOth = (MultOtherContact)multOtherContacts.get(j);
/*      */         
/* 5991 */         FormTextField name = new FormTextField("name" + j, "", false, 25, 150);
/* 5992 */         FormTextField description = new FormTextField("description" + j, "", false, 25, 150);
/*      */         
/* 5994 */         multOtherContactForm.addElement(name);
/* 5995 */         multOtherContactForm.addElement(description);
/*      */       } 
/*      */       
/* 5998 */       multOtherContactForm.setValues(context);
/*      */       
/* 6000 */       Vector newMultOtherContacts = new Vector();
/*      */       
/* 6002 */       for (int j = 0; j < multOtherContacts.size(); j++) {
/*      */         
/* 6004 */         MultOtherContact multOth = (MultOtherContact)multOtherContacts.get(j);
/*      */         
/* 6006 */         multOth.setName(multOtherContactForm.getStringValue("name" + j));
/* 6007 */         multOth.setDescription(multOtherContactForm.getStringValue("description" + j));
/* 6008 */         newMultOtherContacts.add(multOth);
/*      */       } 
/*      */       
/* 6011 */       selection.setMultOtherContacts(newMultOtherContacts);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 6016 */       if (selection.getSelectionID() > 0) {
/* 6017 */         SelectionManager.getInstance().saveSelection(selection, user);
/*      */       }
/*      */     } 
/*      */     
/* 6021 */     multOtherContactForm.addElement(new FormHidden("cmd", command, true));
/*      */     
/* 6023 */     context.putSessionValue("Selection", selection);
/* 6024 */     context.putDelivery("Form", multOtherContactForm);
/*      */     
/* 6026 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean sortGroup(Dispatcher dispatcher, Context context, String command) {
/* 6036 */     int sort = Integer.parseInt(context.getParameter("OrderBy"));
/*      */     
/* 6038 */     String alphaGroupChr = context.getParameter("alphaGroupChr");
/*      */ 
/*      */     
/* 6041 */     Notepad notepad = (Notepad)context.getSessionValue(MilestoneConstants.NOTEPAD_SESSION_NAMES[0]);
/*      */ 
/*      */     
/* 6044 */     User user = (User)context.getSession().getAttribute("user");
/*      */     
/* 6046 */     if (notepad.getAllContents() != null && notepad.getAllContents().size() < notepad.getTotalRecords()) {
/*      */       
/* 6048 */       notepad.setMaxRecords(0);
/* 6049 */       notepad.setAllContents(null);
/* 6050 */       notepad = SelectionManager.getInstance().getSelectionNotepad(context, user.getUserId(), 0);
/*      */     } 
/*      */     
/* 6053 */     SelectionManager.getInstance().getAlphaGroupPosition(context, notepad, alphaGroupChr, sort);
/*      */     
/* 6055 */     notepad.goToSelectedPage();
/*      */     
/* 6057 */     if (command.equals("selection-group")) {
/*      */       
/* 6059 */       dispatcher.redispatch(context, "selection-editor");
/*      */     }
/*      */     else {
/*      */       
/* 6063 */       dispatcher.redispatch(context, "selection-manufacturing-editor");
/*      */     } 
/*      */     
/* 6066 */     return true;
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
/* 6085 */     Vector result = new Vector();
/*      */     
/* 6087 */     if (companies != null && companies.size() > 0)
/*      */     {
/* 6089 */       for (int i = 0; i < companies.size(); i++) {
/*      */ 
/*      */         
/* 6092 */         Company company = (Company)companies.get(i);
/* 6093 */         Environment environment = company.getParentEnvironment();
/* 6094 */         String name = environment.getName();
/*      */         
/* 6096 */         if (!name.equalsIgnoreCase("UML") && 
/* 6097 */           !name.equalsIgnoreCase("Enterprise")) {
/*      */           
/* 6099 */           boolean addFlag = true;
/* 6100 */           for (int r = 0; r < result.size(); r++) {
/*      */             
/* 6102 */             if (((Environment)result.get(r)).getName().equalsIgnoreCase(name)) {
/* 6103 */               addFlag = false;
/*      */             }
/*      */           } 
/* 6106 */           if (addFlag) {
/* 6107 */             result.add(environment);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     }
/* 6112 */     return result;
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
/* 6131 */     Vector result = new Vector();
/* 6132 */     Vector envAclList = user.getAcl().getEnvironmentAcl();
/* 6133 */     HashMap envEditRight = new HashMap();
/*      */ 
/*      */ 
/*      */     
/* 6137 */     if (envAclList != null) {
/* 6138 */       for (int n = 0; n < envAclList.size(); n++) {
/*      */         
/* 6140 */         EnvironmentAcl envAcl = (EnvironmentAcl)envAclList.get(n);
/* 6141 */         if (envAcl.getAccessSelection() == 2) {
/* 6142 */           envEditRight.put(new Integer(envAcl.getEnvironmentId()), new Integer(n));
/*      */         }
/*      */       } 
/*      */     }
/* 6146 */     if (environments != null && environments.size() > 0)
/*      */     {
/* 6148 */       for (int i = 0; i < environments.size(); i++) {
/*      */ 
/*      */         
/* 6151 */         Environment environment = (Environment)environments.get(i);
/* 6152 */         String name = environment.getName();
/*      */         
/* 6154 */         if (envAclList == null) {
/* 6155 */           if (!name.equalsIgnoreCase("UML") && !name.equalsIgnoreCase("Enterprise")) {
/* 6156 */             result.add(environment);
/*      */           
/*      */           }
/*      */         
/*      */         }
/* 6161 */         else if (!name.equalsIgnoreCase("UML") && !name.equalsIgnoreCase("Enterprise") && 
/* 6162 */           envEditRight.containsKey(new Integer(environment.getStructureID()))) {
/* 6163 */           result.add(environment);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/* 6168 */     return result;
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
/* 6180 */     EmailDistribution.removeSessionValues(context);
/* 6181 */     context.removeSessionValue("originalComment");
/*      */     
/* 6183 */     if (command.equals("selection-send-pfm") || command.equals("selection-send-pfmbom")) {
/*      */       
/* 6185 */       PfmHandler pfmHandler = new PfmHandler(this.application);
/* 6186 */       pfmHandler.editSave(dispatcher, context, "selectionSave");
/*      */       
/* 6188 */       EmailDistribution.removeSessionValues(context);
/*      */     } 
/*      */     
/* 6191 */     if (command.equals("selection-send-bom") || command.equals("selection-send-pfmbom")) {
/*      */       
/* 6193 */       BomHandler bomHandler = new BomHandler(this.application);
/* 6194 */       bomHandler.save(dispatcher, context, "selectionSave");
/*      */       
/* 6196 */       EmailDistribution.removeSessionValues(context);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 6201 */     if (context.getSessionValue("sendToSchedule") != null && ((String)context.getSessionValue("sendToSchedule")).equals("true")) {
/*      */       
/* 6203 */       if (context.getSessionValue("recalc-date") != null && ((String)context.getSessionValue("recalc-date")).equals("true")) {
/* 6204 */         context.putDelivery("recalc-date", "true");
/*      */       }
/* 6206 */       context.removeSessionValue("sendToSchedule");
/* 6207 */       context.removeSessionValue("recalc-date");
/*      */       
/* 6209 */       dispatcher.redispatch(context, "schedule-editor");
/* 6210 */       return true;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6216 */     if (command.equals("selection-send-cancel")) {
/*      */       
/* 6218 */       PfmHandler pfmHandler = new PfmHandler(this.application);
/* 6219 */       pfmHandler.editSave(dispatcher, context, "selectionSave");
/*      */       
/* 6221 */       BomHandler bomHandler = new BomHandler(this.application);
/* 6222 */       bomHandler.save(dispatcher, context, "selectionSave");
/*      */ 
/*      */       
/* 6225 */       EmailDistribution.removeSessionValues(context);
/*      */     } 
/*      */     
/* 6228 */     dispatcher.redispatch(context, "selection-editor");
/*      */     
/* 6230 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Form buildDigitalForm(Context context, Selection selection, String command) {
/* 6239 */     Form selectionForm = new Form(this.application, "selectionForm", 
/* 6240 */         this.application.getInfrastructure().getServletURL(), "POST");
/* 6241 */     User user = (User)context.getSession().getAttribute("user");
/* 6242 */     int userId = user.getUserId();
/*      */     
/* 6244 */     int secureLevel = getSelectionPermissions(selection, user);
/* 6245 */     setButtonVisibilities(selection, user, context, secureLevel, command);
/*      */     
/* 6247 */     boolean newFlag = (selection.getSelectionID() < 0);
/*      */     
/* 6249 */     if (newFlag) {
/* 6250 */       context.putDelivery("new-or-copy", "true");
/*      */     } else {
/* 6252 */       context.putDelivery("new-or-copy", "false");
/*      */     } 
/*      */ 
/*      */     
/* 6256 */     selectionForm.addElement(new FormHidden("cmd", command, true));
/* 6257 */     selectionForm.addElement(new FormHidden("OrderBy", "", true));
/* 6258 */     selectionForm.addElement(new FormHidden("hidTitleId", "", true));
/* 6259 */     selectionForm.addElement(new FormHidden("isFocus", "", true));
/* 6260 */     selectionForm.addElement(new FormHidden("statusHidVal", "", true));
/* 6261 */     selectionForm.addElement(new FormHidden("generateSelection", "", true));
/*      */     
/* 6263 */     Vector companies = null;
/* 6264 */     companies = MilestoneHelper.getUserCompanies(context);
/*      */     
/* 6266 */     if (selection != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 6281 */       FormTextField artistFirstName = new FormTextField("artistFirstName", selection.getArtistFirstName(), false, 20, 50);
/* 6282 */       artistFirstName.setTabIndex(1);
/* 6283 */       artistFirstName.setClassName("ctrlMedium");
/* 6284 */       artistFirstName.addFormEvent("onChange", "javaScript:removeSpaces(this);");
/* 6285 */       selectionForm.addElement(artistFirstName);
/*      */ 
/*      */       
/* 6288 */       FormTextField artistLastName = new FormTextField("artistLastName", selection.getArtistLastName(), false, 20, 50);
/* 6289 */       artistLastName.setTabIndex(2);
/* 6290 */       artistLastName.setClassName("ctrlMedium");
/* 6291 */       artistLastName.addFormEvent("onChange", "javaScript:removeSpaces(this);");
/* 6292 */       selectionForm.addElement(artistLastName);
/*      */ 
/*      */       
/* 6295 */       FormTextField title = new FormTextField("title", selection.getTitle(), true, 73, 125);
/* 6296 */       title.setTabIndex(3);
/* 6297 */       title.setClassName("ctrlXLarge");
/* 6298 */       title.addFormEvent("onChange", "javaScript:removeSpaces(this);");
/* 6299 */       selectionForm.addElement(title);
/*      */ 
/*      */       
/* 6302 */       FormTextField sideATitle = new FormTextField("sideATitle", selection.getASide(), false, 20, 125);
/* 6303 */       sideATitle.setTabIndex(4);
/* 6304 */       sideATitle.setClassName("ctrlMedium");
/* 6305 */       selectionForm.addElement(sideATitle);
/*      */ 
/*      */       
/* 6308 */       FormTextField sideBTitle = new FormTextField("sideBTitle", selection.getBSide(), false, 20, 125);
/* 6309 */       sideBTitle.setTabIndex(5);
/* 6310 */       sideBTitle.setClassName("ctrlMedium");
/* 6311 */       selectionForm.addElement(sideBTitle);
/*      */ 
/*      */       
/* 6314 */       String[] values = new String[2];
/* 6315 */       values[0] = "true";
/* 6316 */       values[1] = "false";
/*      */       
/* 6318 */       String[] labels = new String[2];
/* 6319 */       labels[0] = "New Bundle";
/* 6320 */       labels[1] = "Exact Duplicate of Physical Product";
/*      */       
/* 6322 */       FormRadioButtonGroup newBundle = new FormRadioButtonGroup("newBundle", String.valueOf(selection.getNewBundleFlag()), values, labels, false);
/*      */       
/* 6324 */       newBundle.addFormEvent("onClick", "JavaScript:filterScheduleType(false)");
/* 6325 */       newBundle.setTabIndex(6);
/* 6326 */       selectionForm.addElement(newBundle);
/*      */ 
/*      */       
/* 6329 */       FormCheckBox priority = new FormCheckBox("priority", "", false, selection.getPriority());
/* 6330 */       priority.setTabIndex(9);
/* 6331 */       selectionForm.addElement(priority);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 6338 */       String streetDateText = "";
/* 6339 */       if (selection.getStreetDate() != null)
/* 6340 */         streetDateText = MilestoneHelper.getFormatedDate(selection.getStreetDate()); 
/* 6341 */       FormTextField streetDate = new FormTextField("streetDate", streetDateText, false, 10);
/* 6342 */       streetDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
/* 6343 */       streetDate.addFormEvent("onBlur", "JavaScript:validateDate(document.forms[0].streetDate.value,this)");
/* 6344 */       streetDate.setTabIndex(11);
/* 6345 */       streetDate.setClassName("ctrlShort");
/* 6346 */       selectionForm.addElement(streetDate);
/*      */       
/* 6348 */       FormTextField dayType = new FormTextField("dayType", MilestoneHelper.getDayType(selection.getCalendarGroup(), selection.getStreetDate()), false, 5);
/* 6349 */       selectionForm.addElement(dayType);
/*      */ 
/*      */       
/* 6352 */       String digitalRlsDateText = "";
/* 6353 */       if (selection.getDigitalRlsDate() != null)
/* 6354 */         digitalRlsDateText = MilestoneHelper.getFormatedDate(selection.getDigitalRlsDate()); 
/* 6355 */       FormTextField drDate = new FormTextField("digitalDate", digitalRlsDateText, false, 10);
/* 6356 */       drDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
/* 6357 */       drDate.addFormEvent("onBlur", "JavaScript:validateDate(document.forms[0].digitalDate.value,this)");
/* 6358 */       drDate.setTabIndex(10);
/* 6359 */       drDate.setClassName("ctrlShort");
/* 6360 */       selectionForm.addElement(drDate);
/*      */ 
/*      */       
/* 6363 */       String intDateText = "";
/* 6364 */       if (selection.getInternationalDate() != null)
/* 6365 */         intDateText = MilestoneHelper.getFormatedDate(selection.getInternationalDate()); 
/* 6366 */       FormDateField intDate = new FormDateField("internationalDate", intDateText, false, 10);
/* 6367 */       intDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
/* 6368 */       intDate.addFormEvent("onBlur", "JavaScript:validateDate(document.forms[0].internationalDate.value,this)");
/* 6369 */       intDate.setTabIndex(12);
/* 6370 */       intDate.setClassName("ctrlShort");
/* 6371 */       selectionForm.addElement(intDate);
/*      */ 
/*      */       
/* 6374 */       context.putDelivery("releaseWeek", MilestoneHelper.getReleaseWeekString(selection));
/*      */ 
/*      */       
/* 6377 */       FormDropDownMenu status = MilestoneHelper.getLookupDropDown("status", Cache.getSelectionStatusList(), SelectionManager.getLookupObjectValue(selection.getSelectionStatus()), true, false);
/* 6378 */       status.setTabIndex(13);
/* 6379 */       status.setClassName("ctrlSmall");
/* 6380 */       selectionForm.addElement(status);
/*      */ 
/*      */       
/* 6383 */       boolean boolHoldReason = true;
/* 6384 */       if (selection.getHoldReason().equalsIgnoreCase("")) {
/* 6385 */         boolHoldReason = false;
/*      */       }
/* 6387 */       FormCheckBox holdIndicator = new FormCheckBox("holdIndicator", "", false, boolHoldReason);
/* 6388 */       holdIndicator.setTabIndex(10);
/* 6389 */       selectionForm.addElement(holdIndicator);
/*      */ 
/*      */       
/* 6392 */       FormTextArea holdReason = new FormTextArea("holdReason", selection.getHoldReason(), false, 2, 44, "virtual");
/* 6393 */       holdReason.addFormEvent("onBlur", "Javascript:checkField(this)");
/* 6394 */       selectionForm.addElement(holdReason);
/*      */ 
/*      */       
/* 6397 */       FormCheckBox pdIndicator = new FormCheckBox("pdIndicator", "", false, selection.getPressAndDistribution());
/* 6398 */       pdIndicator.setTabIndex(8);
/* 6399 */       selectionForm.addElement(pdIndicator);
/*      */ 
/*      */       
/* 6402 */       FormCheckBox intlFlag = new FormCheckBox("intlFlag", "", false, selection.getInternationalFlag());
/* 6403 */       intlFlag.setTabIndex(12);
/* 6404 */       selectionForm.addElement(intlFlag);
/*      */ 
/*      */       
/* 6407 */       String impactDateText = "";
/* 6408 */       if (selection.getImpactDate() != null)
/* 6409 */         impactDateText = MilestoneHelper.getFormatedDate(selection.getImpactDate()); 
/* 6410 */       FormDateField impactDate = new FormDateField("impactdate", impactDateText, false, 13);
/* 6411 */       impactDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
/* 6412 */       impactDate.addFormEvent("onBlur", "JavaScript:validateDate(document.forms[0].impactdate.value,this)");
/* 6413 */       impactDate.setTabIndex(13);
/* 6414 */       impactDate.setClassName("ctrlShort");
/* 6415 */       selectionForm.addElement(impactDate);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 6420 */       Vector releaseFamilies = ReleasingFamily.getReleasingFamilies(userId, selection.getFamily().getStructureID(), context);
/* 6421 */       FormDropDownMenu releasingFamily = MilestoneHelper.getReleasingFamilyDropDown("releasingFamily", Integer.toString(selection.getReleaseFamilyId()), releaseFamilies, true, selection);
/* 6422 */       releasingFamily.setTabIndex(14);
/*      */       
/* 6424 */       releasingFamily.addFormEvent("onChange", "ReleasingFamilyChange(this)");
/* 6425 */       selectionForm.addElement(releasingFamily);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 6430 */       String evironmentId = "";
/* 6431 */       String environmentName = "";
/* 6432 */       Vector evironmentList = filterSelectionEnvironments(companies);
/*      */       
/* 6434 */       if (selection.getCompany() != null && selection.getCompany().getParentEnvironment() != null) {
/*      */         
/* 6436 */         evironmentId = Integer.toString(selection.getCompany().getParentEnvironment().getStructureID());
/* 6437 */         environmentName = selection.getCompany().getParentEnvironment().getName();
/*      */       } else {
/*      */         
/* 6440 */         evironmentId = "";
/*      */       } 
/* 6442 */       FormHidden evironment = new FormHidden("environment", evironmentId, false);
/* 6443 */       FormHidden evironmentLabel = new FormHidden("environment", evironmentId, false);
/* 6444 */       evironment.setTabIndex(14);
/* 6445 */       evironment.setDisplayName(environmentName);
/*      */ 
/*      */ 
/*      */       
/* 6449 */       selectionForm.addElement(evironment);
/*      */ 
/*      */       
/* 6452 */       String companyId = "";
/* 6453 */       String companyName = "";
/*      */ 
/*      */       
/* 6456 */       if (selection.getCompany() != null) {
/* 6457 */         companyId = Integer.toString(selection.getCompany().getStructureID());
/* 6458 */         companyName = selection.getCompany().getName();
/*      */       } 
/*      */       
/* 6461 */       FormHidden company = new FormHidden("company", companyId, false);
/* 6462 */       company.setTabIndex(15);
/* 6463 */       company.setDisplayName(companyName);
/*      */ 
/*      */ 
/*      */       
/* 6467 */       selectionForm.addElement(company);
/*      */ 
/*      */       
/* 6470 */       String divisionId = "";
/* 6471 */       String divisionName = "";
/* 6472 */       if (selection.getDivision() != null) {
/* 6473 */         divisionId = Integer.toString(selection.getDivision().getStructureID());
/* 6474 */         divisionName = selection.getDivision().getName();
/*      */       } else {
/*      */         
/* 6477 */         divisionId = "";
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 6487 */       FormHidden division = new FormHidden("division", divisionId, false);
/* 6488 */       division.setTabIndex(16);
/* 6489 */       division.setDisplayName(divisionName);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 6494 */       selectionForm.addElement(division);
/*      */ 
/*      */       
/* 6497 */       String labelId = "";
/* 6498 */       String labelName = "";
/* 6499 */       if (selection.getLabel() != null) {
/* 6500 */         labelId = Integer.toString(selection.getLabel().getStructureID());
/* 6501 */         labelName = selection.getLabel().getName();
/*      */       } else {
/* 6503 */         labelId = "";
/*      */       } 
/* 6505 */       FormHidden label = new FormHidden("label", labelId, false);
/*      */       
/* 6507 */       label.setTabIndex(17);
/* 6508 */       label.setDisplayName(labelName);
/*      */       
/* 6510 */       selectionForm.addElement(label);
/*      */ 
/*      */ 
/*      */       
/* 6514 */       if (selection.getOperCompany().equals("***")) {
/* 6515 */         FormHidden opercompany = new FormHidden("opercompany", "***", false);
/* 6516 */         opercompany.setTabIndex(18);
/* 6517 */         opercompany.setDisplayName("***");
/* 6518 */         selectionForm.addElement(opercompany);
/*      */       } else {
/* 6520 */         LookupObject oc = MilestoneHelper.getLookupObject(selection
/* 6521 */             .getOperCompany(), Cache.getOperatingCompanies());
/* 6522 */         String ocAbbr = "";
/* 6523 */         String ocName = "";
/*      */ 
/*      */ 
/*      */         
/* 6527 */         if (oc == null) {
/* 6528 */           ocAbbr = selection.getOperCompany();
/*      */         } else {
/* 6530 */           if (oc != null && oc.getAbbreviation() != null)
/* 6531 */             ocAbbr = oc.getAbbreviation(); 
/* 6532 */           if (oc != null && oc.getName() != null)
/* 6533 */             ocName = ":" + oc.getName(); 
/*      */         } 
/* 6535 */         FormHidden opercompany = new FormHidden("opercompany", ocAbbr, false);
/* 6536 */         opercompany.setTabIndex(18);
/* 6537 */         opercompany.setDisplayName(String.valueOf(ocAbbr) + ocName);
/*      */         
/* 6539 */         if (ocAbbr.equals("ZZ"))
/* 6540 */           opercompany.setDisplayName(ocAbbr); 
/* 6541 */         selectionForm.addElement(opercompany);
/*      */       } 
/*      */ 
/*      */       
/* 6545 */       FormHidden superlabel = new FormHidden("superlabel", selection.getSuperLabel(), false);
/* 6546 */       superlabel.setTabIndex(19);
/* 6547 */       superlabel.setClassName("ctrlShort");
/* 6548 */       superlabel.addFormEvent("onBlur", "javascript:this.value=this.value.toUpperCase();");
/* 6549 */       selectionForm.addElement(superlabel);
/*      */ 
/*      */       
/* 6552 */       FormHidden sublabel = new FormHidden("sublabel", selection.getSubLabel(), false);
/* 6553 */       sublabel.setTabIndex(20);
/* 6554 */       sublabel.setClassName("ctrlShort");
/* 6555 */       sublabel.addFormEvent("onBlur", "javascript:this.value=this.value.toUpperCase();");
/* 6556 */       selectionForm.addElement(sublabel);
/*      */ 
/*      */ 
/*      */       
/* 6560 */       FormTextField imprint = new FormTextField("imprint", selection.getImprint(), false, 50);
/* 6561 */       imprint.setTabIndex(21);
/* 6562 */       imprint.setClassName("ctrlMedium");
/* 6563 */       imprint.addFormEvent("onChange", "javaScript:removeSpaces(this);");
/* 6564 */       selectionForm.addElement(imprint);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 6569 */       FormDropDownMenu configcode = MilestoneHelper.getPfmLookupDropDown("configcode", MilestoneHelper.getConfigCodes(1), selection.getConfigCode(), false, true);
/* 6570 */       configcode.setTabIndex(21);
/*      */       
/* 6572 */       configcode.addFormEvent("onKeyPress", "scanKey(window.event.keyCode, 'document.all.configcode.options', getMaxLength(document.all.configcode.options))");
/*      */       
/* 6574 */       selectionForm.addElement(configcode);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 6581 */       FormHidden projectId = new FormHidden("projectId", String.valueOf(selection.getProjectID()), false);
/* 6582 */       projectId.setTabIndex(22);
/* 6583 */       projectId.setClassName("ctrlMedium");
/* 6584 */       projectId.setDisplayName(String.valueOf(selection.getProjectID()));
/* 6585 */       selectionForm.addElement(projectId);
/*      */ 
/*      */       
/* 6588 */       FormTextField upc = new FormTextField("UPC", selection.getUpc(), false, 17, 20);
/* 6589 */       upc.setTabIndex(23);
/* 6590 */       upc.setClassName("ctrlMedium");
/*      */ 
/*      */       
/* 6593 */       upc.addFormEvent("onKeyPress", "return checkNumbersOnly(window.event.keyCode);");
/*      */ 
/*      */ 
/*      */       
/* 6597 */       selectionForm.addElement(upc);
/*      */ 
/*      */       
/* 6600 */       FormTextField soundscan = new FormTextField("soundscan", selection.getSoundScanGrp(), false, 17, 20);
/* 6601 */       soundscan.setTabIndex(24);
/*      */ 
/*      */ 
/*      */       
/* 6605 */       soundscan.setClassName("ctrlMedium");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 6613 */       selectionForm.addElement(soundscan);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 6618 */       FormTextField gridNumber = new FormTextField("gridNumber", selection.getGridNumber(), false, 50);
/* 6619 */       gridNumber.setTabIndex(25);
/*      */       
/* 6621 */       gridNumber.setEnabled(true);
/* 6622 */       selectionForm.addElement(gridNumber);
/*      */ 
/*      */       
/* 6625 */       FormDropDownMenu prefix = MilestoneHelper.getPrefixCodeDropDown(userId, "prefix", Cache.getPrefixCodes(), SelectionManager.getLookupObjectValue(selection.getPrefixID()), true, context);
/* 6626 */       prefix.setTabIndex(26);
/* 6627 */       prefix.setClassName("ctrlShort");
/*      */       
/* 6629 */       selectionForm.addElement(prefix);
/*      */ 
/*      */       
/* 6632 */       FormTextField selectionNo = new FormTextField("selectionNo", String.valueOf(selection.getSelectionNo()), false, 20, 20);
/* 6633 */       selectionNo.setTabIndex(27);
/*      */       
/* 6635 */       selectionNo.setClassName("ctrlMedium");
/* 6636 */       selectionNo.addFormEvent("onChange", "javaScript:removeSpaces(this);");
/* 6637 */       selectionForm.addElement(selectionNo);
/*      */ 
/*      */       
/* 6640 */       FormHidden titleId = new FormHidden("titleId", String.valueOf(selection.getTitleID()), false);
/* 6641 */       titleId.setClassName("ctrlMedium");
/*      */       
/* 6643 */       titleId.setTabIndex(28);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 6649 */       selectionForm.addElement(titleId);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 6656 */       FormDropDownMenu productLine = MilestoneHelper.getLookupDropDown("productLine", Cache.getProductCategories(1), SelectionManager.getLookupObjectValue(selection.getProductCategory()), true, true);
/* 6657 */       productLine.setTabIndex(29);
/* 6658 */       productLine.setClassName("ctrlMedium");
/* 6659 */       selectionForm.addElement(productLine);
/*      */ 
/*      */       
/* 6662 */       FormDropDownMenu releaseType = MilestoneHelper.getLookupDropDown("releaseType", Cache.getReleaseTypes(), SelectionManager.getLookupObjectValue(selection.getReleaseType()), true, newFlag);
/* 6663 */       releaseType.setTabIndex(30);
/* 6664 */       releaseType.setClassName("ctrlMedium");
/* 6665 */       releaseType.addFormEvent("onChange", "releaseTypeChanged()");
/* 6666 */       selectionForm.addElement(releaseType);
/*      */ 
/*      */       
/* 6669 */       String configValue = "";
/* 6670 */       boolean configNewBundle = false;
/*      */       
/* 6672 */       if (selection.getSelectionConfig() != null) {
/*      */         
/* 6674 */         configNewBundle = selection.getNewBundleFlag();
/* 6675 */         configValue = selection.getSelectionConfig().getSelectionConfigurationAbbreviation();
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 6681 */       FormDropDownMenu configuration = null;
/* 6682 */       if (command.equalsIgnoreCase("selection-edit-copy-digital")) {
/*      */         
/* 6684 */         if (selection.getIsDigital()) {
/* 6685 */           configuration = MilestoneHelper.getSelectionConfigurationDropDown("configuration", configValue, true, 1);
/*      */         } else {
/* 6687 */           configuration = MilestoneHelper.getSelectionConfigurationDropDown("configuration", configValue, true, 1, configNewBundle);
/*      */         } 
/*      */       } else {
/*      */         
/* 6691 */         configuration = MilestoneHelper.getSelectionConfigurationDropDown("configuration", configValue, true, 1, configNewBundle);
/*      */       } 
/*      */       
/* 6694 */       configuration.setTabIndex(31);
/*      */       
/* 6696 */       configuration.addFormEvent("onChange", "buildSubConfigs(this.selectedIndex)");
/* 6697 */       selectionForm.addElement(configuration);
/*      */ 
/*      */ 
/*      */       
/* 6701 */       String subConfigValue = "";
/* 6702 */       if (selection.getSelectionSubConfig() != null) subConfigValue = selection.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation(); 
/* 6703 */       FormDropDownMenu subConfiguration = MilestoneHelper.getSelectionSubConfigurationDropDown("subConfiguration", selection.getSelectionConfig(), subConfigValue, true);
/* 6704 */       subConfiguration.addFormEvent("onChange", "isSubConfigParent(this.selectedIndex)");
/* 6705 */       subConfiguration.setTabIndex(32);
/*      */       
/* 6707 */       selectionForm.addElement(subConfiguration);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 6714 */       FormTextField test = new FormTextField("test", "", false, 8, 8);
/* 6715 */       test.setTabIndex(33);
/* 6716 */       test.setClassName("ctrlShort");
/*      */ 
/*      */ 
/*      */       
/* 6720 */       test.addFormEvent("onChange", "javaScript:clickSell(this,true);");
/* 6721 */       selectionForm.addElement(test);
/*      */ 
/*      */       
/* 6724 */       String sellCode = "";
/* 6725 */       if (selection.getSellCode() != null)
/* 6726 */         sellCode = selection.getSellCode(); 
/* 6727 */       FormDropDownMenu priceCode = new FormDropDownMenu("priceCode", sellCode, "-1" + getSellCodesStringDPC(), "&nbsp;" + getSellCodesStringDPC(), false);
/* 6728 */       priceCode.setTabIndex(34);
/*      */ 
/*      */ 
/*      */       
/* 6732 */       priceCode.setClassName("ctrlSmall");
/* 6733 */       selectionForm.addElement(priceCode);
/*      */ 
/*      */       
/* 6736 */       String numberOfUnits = "0";
/*      */       
/* 6738 */       if (selection.getNumberOfUnits() > 0) {
/* 6739 */         numberOfUnits = Integer.toString(selection.getNumberOfUnits());
/*      */       }
/* 6741 */       FormTextField numOfUnits = new FormTextField("numOfUnits", numberOfUnits, false, 10, 10);
/* 6742 */       numOfUnits.setTabIndex(35);
/* 6743 */       numOfUnits.setClassName("ctrlShort");
/* 6744 */       selectionForm.addElement(numOfUnits);
/*      */ 
/*      */ 
/*      */       
/* 6748 */       User labelUserContact = selection.getLabelContact();
/* 6749 */       Vector labelContacts = SelectionManager.getLabelContacts(selection);
/* 6750 */       FormDropDownMenu contactList = MilestoneHelper.getContactsDropDown(context, "contactlist", labelContacts, labelUserContact, true);
/* 6751 */       contactList.setTabIndex(36);
/* 6752 */       contactList.setClassName("ctrlMedium");
/* 6753 */       selectionForm.addElement(contactList);
/*      */ 
/*      */       
/* 6756 */       FormTextField contact = new FormTextField("contact", selection.getOtherContact(), false, 14, 30);
/* 6757 */       contact.setTabIndex(37);
/* 6758 */       contact.setClassName("ctrlMedium");
/* 6759 */       selectionForm.addElement(contact);
/*      */ 
/*      */       
/* 6762 */       FormCheckBox parentalIndicator = new FormCheckBox("parentalIndicator", "", false, selection.getParentalGuidance());
/* 6763 */       parentalIndicator.setTabIndex(38);
/* 6764 */       selectionForm.addElement(parentalIndicator);
/*      */ 
/*      */       
/* 6767 */       FormCheckBox specPkgIndicator = new FormCheckBox("specialPkgIndicator", "", false, selection.getSpecialPackaging());
/* 6768 */       specPkgIndicator.addFormEvent("onDblClick", "document.forms[0].PackagingHelper.value=document.forms[0].package.value;togglePackaging();");
/* 6769 */       specPkgIndicator.setTabIndex(39);
/* 6770 */       selectionForm.addElement(specPkgIndicator);
/*      */ 
/*      */       
/* 6773 */       FormTextField pkg = new FormTextField("package", selection.getSelectionPackaging(), false, 13, 100);
/* 6774 */       pkg.setTabIndex(40);
/* 6775 */       pkg.setClassName("ctrlMedium");
/* 6776 */       pkg.addFormEvent("onDblClick", "document.forms[0].PackagingHelper.value=document.forms[0].package.value;togglePackaging();");
/* 6777 */       selectionForm.addElement(pkg);
/*      */ 
/*      */       
/* 6780 */       FormDropDownMenu genre = MilestoneHelper.getPfmLookupDropDown("genre", Cache.getMusicTypes(), SelectionManager.getLookupObjectValue(selection.getGenre()), false, true);
/* 6781 */       genre.setTabIndex(41);
/* 6782 */       genre.setId("music_type");
/*      */       
/* 6784 */       selectionForm.addElement(genre);
/*      */ 
/*      */       
/* 6787 */       FormTextField territory = new FormTextField("territory", selection.getSelectionTerritory(), false, 13, 255);
/* 6788 */       territory.setTabIndex(42);
/* 6789 */       territory.setClassName("ctrlMedium");
/* 6790 */       territory.addFormEvent("onDblClick", "document.forms[0].TerritoryHelper.value=document.forms[0].territory.value;toggleTerritory();");
/* 6791 */       selectionForm.addElement(territory);
/*      */ 
/*      */       
/* 6794 */       FormHidden productionGroupCode = new FormHidden("productGroupCode", selection.getProductionGroupCode(), false);
/* 6795 */       productionGroupCode.setTabIndex(42);
/* 6796 */       productionGroupCode.setDisplayName(selection.getProductionGroupCode());
/* 6797 */       productionGroupCode.setClassName("ctrlMedium");
/* 6798 */       selectionForm.addElement(productionGroupCode);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 6805 */       FormTextArea specialInstructions = new FormTextArea("specialInstructions", selection.getSpecialInstructions(), false, 3, 80, "virtual");
/* 6806 */       specialInstructions.setTabIndex(43);
/*      */       
/* 6808 */       selectionForm.addElement(specialInstructions);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 6816 */       String lastStreetDateText = "";
/* 6817 */       if (selection.getLastStreetUpdateDate() != null)
/* 6818 */         lastStreetDateText = MilestoneHelper.getFormatedDate(selection.getLastStreetUpdateDate()); 
/* 6819 */       FormTextField lastStreetUpdatedDate = new FormTextField("laststreetupdateddate", lastStreetDateText, false, 13);
/* 6820 */       selectionForm.addElement(lastStreetUpdatedDate);
/*      */ 
/*      */ 
/*      */       
/* 6824 */       String lastUpdatedDateText = "";
/* 6825 */       if (selection.getLastUpdateDate() != null)
/* 6826 */         lastUpdatedDateText = MilestoneHelper.getCustomFormatedDate(selection.getLastUpdateDate(), "M/d/yyyy hh:mm:ss a 'ET'"); 
/* 6827 */       FormTextField lastUpdatedDate = new FormTextField("lastupdateddate", lastUpdatedDateText, false, 50);
/* 6828 */       selectionForm.addElement(lastUpdatedDate);
/*      */ 
/*      */       
/* 6831 */       String originDateText = "";
/* 6832 */       if (selection.getOriginDate() != null)
/* 6833 */         originDateText = MilestoneHelper.getFormatedDate(selection.getOriginDate()); 
/* 6834 */       FormTextField originDate = new FormTextField("origindate", originDateText, false, 13);
/* 6835 */       selectionForm.addElement(originDate);
/*      */ 
/*      */       
/* 6838 */       String archieDateText = "";
/* 6839 */       if (selection.getArchieDate() != null)
/* 6840 */         archieDateText = MilestoneHelper.getCustomFormatedDate(selection.getArchieDate(), "M/d/yyyy hh:mm:ss a 'ET'"); 
/* 6841 */       FormTextField archieDate = new FormTextField("archieDate", archieDateText, false, 13);
/* 6842 */       selectionForm.addElement(archieDate);
/*      */ 
/*      */       
/* 6845 */       String autoCloseDateText = "";
/* 6846 */       if (selection.getAutoCloseDate() != null)
/* 6847 */         autoCloseDateText = MilestoneHelper.getCustomFormatedDate(selection.getAutoCloseDate(), "M/d/yyyy hh:mm:ss a 'ET'"); 
/* 6848 */       FormTextField autoCloseDate = new FormTextField("autoCloseDate", autoCloseDateText, false, 40);
/* 6849 */       selectionForm.addElement(autoCloseDate);
/*      */ 
/*      */       
/* 6852 */       String lastLegacyUpdateDateText = "";
/* 6853 */       if (selection.getLastLegacyUpdateDate() != null)
/* 6854 */         lastLegacyUpdateDateText = MilestoneHelper.getCustomFormatedDate(selection.getLastLegacyUpdateDate(), "M/d/yyyy hh:mm:ss a 'ET'"); 
/* 6855 */       FormTextField lastLegacyUpdateDate = new FormTextField("lastLegacyUpdateDate", lastLegacyUpdateDateText, false, 40);
/* 6856 */       selectionForm.addElement(lastLegacyUpdateDate);
/*      */ 
/*      */       
/* 6859 */       FormTextArea packagingHelper = new FormTextArea("PackagingHelper", selection.getSelectionPackaging(), false, 2, 44, "virtual");
/* 6860 */       selectionForm.addElement(packagingHelper);
/*      */ 
/*      */       
/* 6863 */       FormTextArea territoryHelper = new FormTextArea("TerritoryHelper", selection.getSelectionTerritory(), false, 2, 44, "virtual");
/* 6864 */       selectionForm.addElement(territoryHelper);
/*      */ 
/*      */       
/* 6867 */       FormTextArea comments = new FormTextArea("comments", selection.getSelectionComments(), false, 6, 44, "virtual");
/* 6868 */       comments.addFormEvent("onBlur", "Javascript:checkField(this)");
/* 6869 */       selectionForm.addElement(comments);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6878 */     addSelectionSearchElements(context, selection, selectionForm, companies, true);
/*      */ 
/*      */     
/* 6881 */     if (context.getSessionValue("NOTEPAD_SELECTION_VISIBLE") != null) {
/* 6882 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_SELECTION_VISIBLE"));
/*      */     }
/* 6884 */     boolean isParent = false;
/*      */     
/* 6886 */     if (selection.getSelectionSubConfig() != null) {
/* 6887 */       isParent = selection.getSelectionSubConfig().isParent();
/*      */     }
/* 6889 */     context.putDelivery("is-parent", String.valueOf(isParent));
/* 6890 */     context.putDelivery("old-selection-no", selection.getSelectionNo());
/*      */     
/* 6892 */     String price = "0.00";
/* 6893 */     if (selection.getPriceCode() != null && 
/* 6894 */       selection.getPriceCode().getTotalCost() > 0.0F) {
/* 6895 */       price = MilestoneHelper.formatDollarPrice(selection.getPriceCode().getTotalCost());
/*      */     }
/* 6897 */     context.putDelivery("price", price);
/*      */     
/* 6899 */     String lastUpdateUser = "";
/* 6900 */     if (selection.getLastUpdatingUser() != null)
/* 6901 */       lastUpdateUser = selection.getLastUpdatingUser().getName(); 
/* 6902 */     context.putDelivery("lastUpdateUser", lastUpdateUser);
/*      */     
/* 6904 */     return selectionForm;
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
/* 6915 */     Vector projectList = (Vector)context.getSessionValue("searchResults");
/* 6916 */     String resultsIndex = (String)context.getSessionValue("selectionScreenTypeIndex");
/* 6917 */     System.out.println("value of resultsIndex:[" + resultsIndex + "]");
/*      */     
/* 6919 */     ProjectSearch selectedProject = null;
/* 6920 */     if (resultsIndex != null) {
/* 6921 */       selectedProject = (ProjectSearch)projectList.elementAt(Integer.parseInt(resultsIndex));
/*      */     } else {
/* 6923 */       selectedProject = new ProjectSearch();
/*      */     } 
/*      */ 
/*      */     
/* 6927 */     context.removeSessionValue("selectionScreenType");
/* 6928 */     context.removeSessionValue("searchResults");
/* 6929 */     context.removeSessionValue("selectionScreenTypeIndex");
/*      */     
/* 6931 */     Form selectionForm = new Form(this.application, "selectionForm", 
/* 6932 */         this.application.getInfrastructure().getServletURL(), "POST");
/*      */     
/* 6934 */     User user = (User)context.getSession().getAttribute("user");
/* 6935 */     int userId = user.getUserId();
/*      */ 
/*      */     
/* 6938 */     int secureLevel = getSelectionPermissions(selection, user);
/* 6939 */     setButtonVisibilities(selection, user, context, secureLevel, command);
/*      */ 
/*      */     
/* 6942 */     selectionForm.addElement(new FormHidden("cmd", "selection-edit-new", true));
/* 6943 */     selectionForm.addElement(new FormHidden("OrderBy", "", true));
/* 6944 */     selectionForm.addElement(new FormHidden("hidTitleId", "", true));
/* 6945 */     selectionForm.addElement(new FormHidden("isFocus", "", true));
/* 6946 */     selectionForm.addElement(new FormHidden("statusHidVal", "", true));
/* 6947 */     selectionForm.addElement(new FormHidden("generateSelection", "", true));
/*      */     
/* 6949 */     String selectedConfig = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6972 */     String strArtistFirstName = (selectedProject.getArtistFirstName() != null) ? selectedProject.getArtistFirstName() : "";
/* 6973 */     FormTextField artistFirstName = new FormTextField("artistFirstName", strArtistFirstName, false, 20, 50);
/* 6974 */     artistFirstName.setTabIndex(1);
/* 6975 */     artistFirstName.setClassName("ctrlMedium");
/* 6976 */     artistFirstName.addFormEvent("onChange", "javaScript:removeSpaces(this);");
/* 6977 */     selectionForm.addElement(artistFirstName);
/*      */ 
/*      */     
/* 6980 */     String strArtistLastName = (selectedProject.getArtistLastName() != null) ? selectedProject.getArtistLastName() : "";
/* 6981 */     FormTextField artistLastName = new FormTextField("artistLastName", strArtistLastName, false, 20, 50);
/* 6982 */     artistLastName.setTabIndex(2);
/* 6983 */     artistLastName.setClassName("ctrlMedium");
/* 6984 */     artistLastName.addFormEvent("onChange", "javaScript:removeSpaces(this);");
/* 6985 */     selectionForm.addElement(artistLastName);
/*      */ 
/*      */     
/* 6988 */     String strTitle = (selectedProject.getTitle() != null) ? selectedProject.getTitle() : "";
/* 6989 */     FormTextField title = new FormTextField("title", strTitle, true, 73, 125);
/* 6990 */     title.setTabIndex(3);
/* 6991 */     title.setClassName("ctrlXLarge");
/* 6992 */     title.addFormEvent("onChange", "javaScript:removeSpaces(this);");
/* 6993 */     selectionForm.addElement(title);
/*      */ 
/*      */     
/* 6996 */     FormTextField sideATitle = new FormTextField("sideATitle", "", false, 20, 125);
/* 6997 */     sideATitle.setTabIndex(4);
/* 6998 */     sideATitle.setClassName("ctrlMedium");
/* 6999 */     selectionForm.addElement(sideATitle);
/*      */ 
/*      */     
/* 7002 */     FormTextField sideBTitle = new FormTextField("sideBTitle", "", false, 20, 125);
/* 7003 */     sideBTitle.setTabIndex(5);
/* 7004 */     sideBTitle.setClassName("ctrlMedium");
/* 7005 */     selectionForm.addElement(sideBTitle);
/*      */ 
/*      */     
/* 7008 */     String[] values = new String[2];
/* 7009 */     values[0] = "true";
/* 7010 */     values[1] = "false";
/*      */     
/* 7012 */     String[] labels = new String[2];
/* 7013 */     labels[0] = "New Bundle";
/* 7014 */     labels[1] = "Exact Duplicate of Physical Product";
/*      */     
/* 7016 */     FormRadioButtonGroup newBundle = new FormRadioButtonGroup("newBundle", "true", values, labels, false);
/*      */     
/* 7018 */     newBundle.addFormEvent("onClick", "JavaScript:filterScheduleType(true)");
/* 7019 */     newBundle.setTabIndex(5);
/* 7020 */     selectionForm.addElement(newBundle);
/*      */ 
/*      */     
/* 7023 */     FormCheckBox priority = new FormCheckBox("priority", "", false, false);
/* 7024 */     priority.setTabIndex(6);
/* 7025 */     selectionForm.addElement(priority);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 7031 */     FormTextField streetDate = new FormTextField("streetDate", "", false, 10);
/* 7032 */     streetDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
/* 7033 */     streetDate.addFormEvent("onBlur", "JavaScript:validateDate(document.forms[0].streetDate.value,this)");
/* 7034 */     streetDate.setTabIndex(7);
/* 7035 */     streetDate.setClassName("ctrlShort");
/* 7036 */     selectionForm.addElement(streetDate);
/*      */ 
/*      */     
/* 7039 */     FormTextField drDate = new FormTextField("digitalDate", "", false, 10);
/* 7040 */     drDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
/* 7041 */     drDate.addFormEvent("onBlur", "JavaScript:validateDate(document.forms[0].digitalDate.value,this)");
/* 7042 */     drDate.setTabIndex(6);
/* 7043 */     drDate.setClassName("ctrlShort");
/* 7044 */     selectionForm.addElement(drDate);
/*      */ 
/*      */     
/* 7047 */     FormDateField intDate = new FormDateField("internationalDate", "", false, 10);
/* 7048 */     intDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
/* 7049 */     intDate.addFormEvent("onBlur", "JavaScript:validateDate(document.forms[0].internationalDate.value,this)");
/* 7050 */     intDate.setTabIndex(8);
/* 7051 */     intDate.setClassName("ctrlShort");
/* 7052 */     selectionForm.addElement(intDate);
/*      */ 
/*      */     
/* 7055 */     FormDropDownMenu status = MilestoneHelper.getLookupDropDown("status", Cache.getSelectionStatusList(), "Active", true, false);
/* 7056 */     status.setTabIndex(9);
/* 7057 */     status.setClassName("ctrlSmall");
/* 7058 */     selectionForm.addElement(status);
/*      */ 
/*      */     
/* 7061 */     FormCheckBox holdIndicator = new FormCheckBox("holdIndicator", "", false, false);
/* 7062 */     holdIndicator.setTabIndex(10);
/* 7063 */     selectionForm.addElement(holdIndicator);
/*      */ 
/*      */     
/* 7066 */     FormTextArea holdReason = new FormTextArea("holdReason", "", false, 2, 44, "virtual");
/* 7067 */     holdReason.addFormEvent("onBlur", "Javascript:checkField(this)");
/* 7068 */     selectionForm.addElement(holdReason);
/*      */ 
/*      */     
/* 7071 */     int pd_indicator = selectedProject.getPD_Indicator();
/* 7072 */     boolean pdBool = false;
/* 7073 */     if (pd_indicator == 1) {
/* 7074 */       pdBool = true;
/*      */     }
/* 7076 */     FormCheckBox pdIndicator = new FormCheckBox("pdIndicator", "", false, 
/* 7077 */         pdBool);
/* 7078 */     pdIndicator.setTabIndex(6);
/* 7079 */     selectionForm.addElement(pdIndicator);
/*      */ 
/*      */     
/* 7082 */     FormCheckBox intlFlag = new FormCheckBox("intlFlag", "", false, false);
/* 7083 */     intlFlag.setTabIndex(12);
/* 7084 */     selectionForm.addElement(intlFlag);
/*      */ 
/*      */     
/* 7087 */     FormDateField impactDate = new FormDateField("impactdate", "", false, 13);
/* 7088 */     impactDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
/* 7089 */     impactDate.addFormEvent("onBlur", "JavaScript:validateDate(document.forms[0].impactdate.value,this)");
/* 7090 */     impactDate.setTabIndex(13);
/* 7091 */     impactDate.setClassName("ctrlShort");
/* 7092 */     selectionForm.addElement(impactDate);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 7097 */     Vector releaseFamilies = ReleasingFamily.getReleasingFamilies(userId, selectedProject.getMSFamilyId(), context);
/* 7098 */     ReleasingFamily defaultReleasingFamily = ReleasingFamily.getDefaultReleasingFamily(userId, selectedProject.getMSFamilyId(), context);
/* 7099 */     FormDropDownMenu releasingFamily = MilestoneHelper.getReleasingFamilyDropDown("releasingFamily", String.valueOf(defaultReleasingFamily.getReleasingFamilyId()), releaseFamilies, true, selection);
/* 7100 */     releasingFamily.setTabIndex(13);
/*      */     
/* 7102 */     releasingFamily.addFormEvent("onChange", "ReleasingFamilyChange(this)");
/* 7103 */     selectionForm.addElement(releasingFamily);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 7108 */     String envId = String.valueOf(selectedProject.getMSEnvironmentId());
/* 7109 */     String envName = MilestoneHelper.getStructureName(selectedProject.getMSEnvironmentId());
/* 7110 */     String environmentName = "";
/* 7111 */     FormHidden evironment = new FormHidden("environment", envId, false);
/* 7112 */     evironment.setDisplayName(envName);
/* 7113 */     selectionForm.addElement(evironment);
/*      */ 
/*      */     
/* 7116 */     String companyId = String.valueOf(selectedProject.getMSCompanyId());
/* 7117 */     String companyName = MilestoneHelper.getStructureName(selectedProject.getMSCompanyId());
/* 7118 */     FormHidden company = new FormHidden("company", companyId, false);
/* 7119 */     company.setTabIndex(15);
/* 7120 */     company.setDisplayName(companyName);
/* 7121 */     selectionForm.addElement(company);
/*      */ 
/*      */     
/* 7124 */     String divisionId = String.valueOf(selectedProject.getMSDivisionId());
/* 7125 */     String divisionName = MilestoneHelper.getStructureName(selectedProject.getMSDivisionId());
/* 7126 */     FormHidden division = new FormHidden("division", divisionId, false);
/* 7127 */     division.setTabIndex(16);
/* 7128 */     division.setDisplayName(divisionName);
/* 7129 */     selectionForm.addElement(division);
/*      */ 
/*      */     
/* 7132 */     String labelId = String.valueOf(selectedProject.getMSLabelId());
/* 7133 */     String labelName = MilestoneHelper.getStructureName(selectedProject.getMSLabelId());
/* 7134 */     FormHidden label = new FormHidden("label", labelId, false);
/* 7135 */     label.setTabIndex(17);
/* 7136 */     label.setDisplayName(labelName);
/* 7137 */     selectionForm.addElement(label);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 7142 */     if (selectedProject.getOperCompany().equals("***")) {
/* 7143 */       FormHidden opercompany = new FormHidden("opercompany", "***", false);
/*      */       
/* 7145 */       opercompany.setDisplayName("***");
/* 7146 */       selectionForm.addElement(opercompany);
/*      */     } else {
/* 7148 */       LookupObject oc = MilestoneHelper.getLookupObject(selectedProject
/* 7149 */           .getOperCompany(), Cache.getOperatingCompanies());
/* 7150 */       String ocAbbr = "";
/* 7151 */       String ocName = "";
/*      */ 
/*      */ 
/*      */       
/* 7155 */       if (oc == null) {
/* 7156 */         ocAbbr = selectedProject.getOperCompany();
/*      */       } else {
/* 7158 */         if (oc != null && oc.getAbbreviation() != null)
/* 7159 */           ocAbbr = oc.getAbbreviation(); 
/* 7160 */         if (oc != null && oc.getName() != null)
/* 7161 */           ocName = ":" + oc.getName(); 
/*      */       } 
/* 7163 */       FormHidden opercompany = new FormHidden("opercompany", ocAbbr, false);
/*      */       
/* 7165 */       opercompany.setDisplayName(String.valueOf(ocAbbr) + ocName);
/*      */       
/* 7167 */       if (ocAbbr.equals("ZZ"))
/* 7168 */         opercompany.setDisplayName(ocAbbr); 
/* 7169 */       selectionForm.addElement(opercompany);
/*      */     } 
/*      */ 
/*      */     
/* 7173 */     FormHidden superlabel = new FormHidden("superlabel", selectedProject.getSuperLabel(), false);
/*      */     
/* 7175 */     superlabel.setClassName("ctrlShort");
/* 7176 */     superlabel.addFormEvent("onBlur", "javascript:this.value=this.value.toUpperCase();");
/* 7177 */     selectionForm.addElement(superlabel);
/*      */ 
/*      */     
/* 7180 */     FormHidden sublabel = new FormHidden("sublabel", selectedProject.getSubLabel(), false);
/*      */     
/* 7182 */     sublabel.setClassName("ctrlShort");
/* 7183 */     sublabel.addFormEvent("onBlur", "javascript:this.value=this.value.toUpperCase();");
/* 7184 */     selectionForm.addElement(sublabel);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 7193 */     Vector jdeExceptionFamilies = ProjectSearchManager.getInstance().getProjectSearchJDEFamilies();
/* 7194 */     boolean isUmvdUser = jdeExceptionFamilies.contains(new Integer(selectedProject.getMSFamilyId()));
/*      */ 
/*      */     
/* 7197 */     String imprintStr = "";
/* 7198 */     if (isUmvdUser) {
/* 7199 */       imprintStr = labelName;
/*      */     } else {
/* 7201 */       imprintStr = (selectedProject.getImprint() != null) ? selectedProject.getImprint() : "";
/*      */     } 
/* 7203 */     FormTextField imprint = new FormTextField("imprint", imprintStr, false, 50);
/* 7204 */     imprint.setTabIndex(21);
/* 7205 */     imprint.setClassName("ctrlMedium");
/* 7206 */     imprint.addFormEvent("onChange", "javaScript:removeSpaces(this);");
/* 7207 */     selectionForm.addElement(imprint);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 7212 */     FormDropDownMenu configcode = MilestoneHelper.getPfmLookupDropDown("configcode", MilestoneHelper.getConfigCodes(1), "", false, true);
/* 7213 */     configcode.setTabIndex(21);
/*      */     
/* 7215 */     configcode.addFormEvent("onKeyPress", "scanKey(window.event.keyCode, 'document.all.configcode.options', getMaxLength(document.all.configcode.options))");
/*      */     
/* 7217 */     selectionForm.addElement(configcode);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 7227 */     String projectIdStr = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 7234 */     projectIdStr = selectedProject.getRMSProjectNo();
/* 7235 */     FormHidden projectId = new FormHidden("projectId", projectIdStr, false);
/* 7236 */     projectId.setTabIndex(22);
/* 7237 */     projectId.setClassName("ctrlMedium");
/* 7238 */     projectId.setDisplayName(projectIdStr);
/* 7239 */     selectionForm.addElement(projectId);
/*      */ 
/*      */     
/* 7242 */     FormTextField upc = new FormTextField("UPC", "", false, 17, 20);
/* 7243 */     upc.setTabIndex(23);
/* 7244 */     upc.setClassName("ctrlMedium");
/*      */     
/* 7246 */     upc.addFormEvent("onKeyPress", "return checkNumbersOnly(window.event.keyCode);");
/*      */ 
/*      */     
/* 7249 */     selectionForm.addElement(upc);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 7257 */     FormTextField soundscan = new FormTextField("soundscan", "", false, 17, 20);
/* 7258 */     soundscan.setTabIndex(24);
/* 7259 */     soundscan.setClassName("ctrlMedium");
/* 7260 */     selectionForm.addElement(soundscan);
/*      */ 
/*      */ 
/*      */     
/* 7264 */     FormTextField gridNumber = new FormTextField("gridNumber", "", false, 50);
/* 7265 */     gridNumber.setTabIndex(24);
/* 7266 */     gridNumber.setEnabled(true);
/*      */     
/* 7268 */     selectionForm.addElement(gridNumber);
/*      */ 
/*      */ 
/*      */     
/* 7272 */     FormDropDownMenu prefix = MilestoneHelper.getPrefixCodeDropDown(userId, "prefix", Cache.getPrefixCodes(), "", true, context);
/* 7273 */     prefix.setTabIndex(25);
/*      */     
/* 7275 */     prefix.setClassName("ctrlShort");
/* 7276 */     selectionForm.addElement(prefix);
/*      */ 
/*      */     
/* 7279 */     FormTextField selectionNo = new FormTextField("selectionNo", "", false, 20, 20);
/* 7280 */     selectionNo.setTabIndex(26);
/*      */     
/* 7282 */     selectionNo.setClassName("ctrlMedium");
/* 7283 */     selectionNo.addFormEvent("onChange", "javaScript:removeSpaces(this);");
/* 7284 */     selectionForm.addElement(selectionNo);
/*      */ 
/*      */     
/* 7287 */     FormTextField titleId = new FormTextField("titleId", "", false, 13, 24);
/* 7288 */     titleId.setClassName("ctrlMedium");
/*      */     
/* 7290 */     titleId.setTabIndex(27);
/*      */ 
/*      */     
/* 7293 */     selectionForm.addElement(titleId);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 7300 */     FormDropDownMenu productLine = MilestoneHelper.getLookupDropDown("productLine", Cache.getProductCategories(1), "", true, true);
/* 7301 */     productLine.setTabIndex(28);
/* 7302 */     productLine.setClassName("ctrlMedium");
/* 7303 */     selectionForm.addElement(productLine);
/*      */ 
/*      */     
/* 7306 */     FormDropDownMenu releaseType = MilestoneHelper.getLookupDropDown("releaseType", Cache.getReleaseTypes(), "CO", true, true);
/* 7307 */     releaseType.setTabIndex(29);
/* 7308 */     releaseType.setClassName("ctrlMedium");
/* 7309 */     releaseType.addFormEvent("onChange", "releaseTypeChanged()");
/* 7310 */     selectionForm.addElement(releaseType);
/*      */ 
/*      */     
/* 7313 */     FormDropDownMenu configuration = MilestoneHelper.getSelectionConfigurationDropDown("configuration", "", true, 1);
/* 7314 */     configuration.setTabIndex(30);
/*      */     
/* 7316 */     configuration.addFormEvent("onChange", "buildSubConfigs(this.selectedIndex)");
/* 7317 */     selectionForm.addElement(configuration);
/*      */ 
/*      */ 
/*      */     
/* 7321 */     Vector configs = Cache.getSelectionConfigs();
/* 7322 */     SelectionConfiguration config = (SelectionConfiguration)configs.get(0);
/* 7323 */     FormDropDownMenu subConfiguration = new FormDropDownMenu("subConfiguration", "", "", "", true);
/*      */     
/* 7325 */     subConfiguration.addFormEvent("onChange", "isSubConfigParent(this.selectedIndex)");
/* 7326 */     subConfiguration.setTabIndex(31);
/* 7327 */     subConfiguration.setEnabled(false);
/*      */     
/* 7329 */     selectionForm.addElement(subConfiguration);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 7336 */     FormTextField test = new FormTextField("test", "", false, 8, 8);
/* 7337 */     test.setTabIndex(32);
/* 7338 */     test.setClassName("ctrlShort");
/*      */ 
/*      */ 
/*      */     
/* 7342 */     test.addFormEvent("onChange", "javaScript:clickSell(this,true);");
/* 7343 */     selectionForm.addElement(test);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 7348 */     FormDropDownMenu priceCode = new FormDropDownMenu("priceCode", "", "-1" + getSellCodesStringDPC(), "&nbsp;" + getSellCodesStringDPC(), true);
/* 7349 */     priceCode.setTabIndex(33);
/*      */     
/* 7351 */     priceCode.setClassName("ctrlSmall");
/*      */     
/* 7353 */     selectionForm.addElement(priceCode);
/*      */ 
/*      */ 
/*      */     
/* 7357 */     FormTextField numOfUnits = new FormTextField("numOfUnits", "0", false, 10, 10);
/* 7358 */     numOfUnits.setTabIndex(34);
/* 7359 */     numOfUnits.setClassName("ctrlShort");
/* 7360 */     selectionForm.addElement(numOfUnits);
/*      */ 
/*      */     
/* 7363 */     FormDropDownMenu contactList = MilestoneHelper.getContactsDropDown(context, "contactlist", new Vector(), user, true);
/* 7364 */     contactList.setTabIndex(35);
/* 7365 */     contactList.setClassName("ctrlMedium");
/* 7366 */     selectionForm.addElement(contactList);
/*      */ 
/*      */     
/* 7369 */     FormTextField contact = new FormTextField("contact", "", false, 14, 30);
/* 7370 */     contact.setTabIndex(36);
/* 7371 */     contact.setClassName("ctrlMedium");
/* 7372 */     selectionForm.addElement(contact);
/*      */ 
/*      */     
/* 7375 */     FormCheckBox parentalIndicator = new FormCheckBox("parentalIndicator", "", false, false);
/* 7376 */     parentalIndicator.setTabIndex(37);
/* 7377 */     selectionForm.addElement(parentalIndicator);
/*      */ 
/*      */     
/* 7380 */     FormCheckBox specPkgIndicator = new FormCheckBox("specialPkgIndicator", "", false, false);
/* 7381 */     specPkgIndicator.addFormEvent("onDblClick", "document.forms[0].PackagingHelper.value=document.forms[0].package.value;togglePackaging();");
/* 7382 */     specPkgIndicator.setTabIndex(38);
/* 7383 */     selectionForm.addElement(specPkgIndicator);
/*      */ 
/*      */     
/* 7386 */     FormTextField pkg = new FormTextField("package", "", false, 13, 100);
/* 7387 */     pkg.setTabIndex(39);
/* 7388 */     pkg.setClassName("ctrlMedium");
/* 7389 */     pkg.addFormEvent("onDblClick", "document.forms[0].PackagingHelper.value=document.forms[0].package.value;togglePackaging();");
/* 7390 */     selectionForm.addElement(pkg);
/*      */ 
/*      */     
/* 7393 */     FormDropDownMenu genre = MilestoneHelper.getPfmLookupDropDown("genre", Cache.getMusicTypes(), "", true, true);
/*      */     
/* 7395 */     genre.setTabIndex(40);
/* 7396 */     genre.setId("music_type");
/*      */     
/* 7398 */     selectionForm.addElement(genre);
/*      */ 
/*      */     
/* 7401 */     FormTextField territory = new FormTextField("territory", "", false, 13, 255);
/* 7402 */     territory.setTabIndex(41);
/* 7403 */     territory.setClassName("ctrlMedium");
/* 7404 */     territory.addFormEvent("onDblClick", "document.forms[0].TerritoryHelper.value=document.forms[0].territory.value;toggleTerritory();");
/* 7405 */     selectionForm.addElement(territory);
/*      */ 
/*      */     
/* 7408 */     FormHidden productionGroupCode = new FormHidden("productGroupCode", selection.getProductionGroupCode(), false);
/* 7409 */     productionGroupCode.setTabIndex(42);
/* 7410 */     productionGroupCode.setDisplayName(selection.getProductionGroupCode());
/* 7411 */     productionGroupCode.setClassName("ctrlMedium");
/* 7412 */     selectionForm.addElement(productionGroupCode);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 7419 */     FormTextArea specialInstructions = new FormTextArea("specialInstructions", "", false, 3, 80, "virtual");
/* 7420 */     specialInstructions.setTabIndex(42);
/*      */     
/* 7422 */     selectionForm.addElement(specialInstructions);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 7430 */     FormTextField lastStreetUpdatedDate = new FormTextField("laststreetupdateddate", "", false, 13);
/* 7431 */     selectionForm.addElement(lastStreetUpdatedDate);
/*      */ 
/*      */     
/* 7434 */     FormTextField lastUpdatedDate = new FormTextField("lastupdateddate", "", false, 50);
/* 7435 */     selectionForm.addElement(lastUpdatedDate);
/*      */ 
/*      */     
/* 7438 */     FormTextField originDate = new FormTextField("origindate", "", false, 13);
/* 7439 */     selectionForm.addElement(originDate);
/*      */ 
/*      */     
/* 7442 */     FormTextArea packagingHelper = new FormTextArea("PackagingHelper", "", false, 2, 44, "virtual");
/* 7443 */     selectionForm.addElement(packagingHelper);
/*      */ 
/*      */     
/* 7446 */     FormTextArea territoryHelper = new FormTextArea("TerritoryHelper", selection.getSelectionTerritory(), false, 2, 44, "virtual");
/* 7447 */     selectionForm.addElement(territoryHelper);
/*      */ 
/*      */     
/* 7450 */     FormTextArea comments = new FormTextArea("comments", "", false, 2, 44, "virtual");
/* 7451 */     comments.addFormEvent("onBlur", "Javascript:checkField(this)");
/* 7452 */     selectionForm.addElement(comments);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 7459 */     addSelectionSearchElements(context, new Selection(), selectionForm, MilestoneHelper.getUserCompanies(context), true);
/*      */ 
/*      */     
/* 7462 */     if (context.getSessionValue("NOTEPAD_SELECTION_VISIBLE") != null) {
/* 7463 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_SELECTION_VISIBLE"));
/*      */     }
/*      */     
/* 7466 */     context.putDelivery("releaseWeek", "");
/* 7467 */     context.putDelivery("new-or-copy", "true");
/* 7468 */     context.putDelivery("price", "0.00");
/*      */     
/* 7470 */     boolean isParent = false;
/*      */     
/* 7472 */     if (selection.getSelectionSubConfig() != null) {
/* 7473 */       isParent = selection.getSelectionSubConfig().isParent();
/*      */     }
/* 7475 */     context.putDelivery("is-parent", String.valueOf(isParent));
/*      */     
/* 7477 */     String lastUpdateUser = "";
/* 7478 */     if (selection.getLastUpdatingUser() != null)
/* 7479 */       lastUpdateUser = selection.getLastUpdatingUser().getName(); 
/* 7480 */     context.putDelivery("lastUpdateUser", lastUpdateUser);
/*      */     
/* 7482 */     return selectionForm;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void resetSearchVariables(User user, User userSrch, Context context) {
/* 7490 */     user.SS_searchInitiated = userSrch.SS_searchInitiated;
/* 7491 */     user.SS_artistSearch = userSrch.SS_artistSearch;
/* 7492 */     user.SS_titleSearch = userSrch.SS_titleSearch;
/* 7493 */     user.SS_selectionNoSearch = userSrch.SS_selectionNoSearch;
/* 7494 */     user.SS_prefixIDSearch = userSrch.SS_prefixIDSearch;
/* 7495 */     user.SS_upcSearch = userSrch.SS_upcSearch;
/* 7496 */     user.SS_streetDateSearch = userSrch.SS_streetDateSearch;
/* 7497 */     user.SS_streetEndDateSearch = userSrch.SS_streetEndDateSearch;
/* 7498 */     user.SS_configSearch = userSrch.SS_configSearch;
/* 7499 */     user.SS_subconfigSearch = userSrch.SS_subconfigSearch;
/* 7500 */     user.SS_labelSearch = userSrch.SS_labelSearch;
/* 7501 */     user.SS_companySearch = userSrch.SS_companySearch;
/* 7502 */     user.SS_contactSearch = userSrch.SS_contactSearch;
/* 7503 */     user.SS_familySearch = userSrch.SS_familySearch;
/* 7504 */     user.SS_environmentSearch = userSrch.SS_environmentSearch;
/* 7505 */     user.SS_projectIDSearch = userSrch.SS_projectIDSearch;
/* 7506 */     user.SS_productTypeSearch = userSrch.SS_productTypeSearch;
/* 7507 */     user.SS_showAllSearch = userSrch.SS_showAllSearch;
/* 7508 */     user.RC_environment = userSrch.RC_environment;
/* 7509 */     user.RC_releasingFamily = userSrch.RC_releasingFamily;
/* 7510 */     user.RC_labelContact = userSrch.RC_labelContact;
/* 7511 */     user.RC_productType = userSrch.RC_productType;
/*      */     
/* 7513 */     context.removeSessionValue("ResetSearchVariables");
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
/* 7525 */     DcGDRSResults dcGDRSResults = new DcGDRSResults();
/*      */     try {
/* 7527 */       dcGDRSResults = projectSearchSvcClient.GDRSProductStatusGet(selection.getSelectionID(), selection.getReleaseFamilyId(), environmentID);
/* 7528 */     } catch (RemoteException re) {
/*      */       
/* 7530 */       dcGDRSResults.setExceptionMessage(re.getMessage());
/* 7531 */       System.out.println(re.getMessage());
/* 7532 */     } catch (Exception e) {
/* 7533 */       dcGDRSResults.setExceptionMessage(e.getMessage());
/* 7534 */       System.out.println(e.getMessage());
/*      */     } 
/*      */     
/* 7537 */     return dcGDRSResults;
/*      */   }
/*      */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\SelectionHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */