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
/*      */ import com.techempower.gemini.FormTextField;
/*      */ import com.techempower.gemini.FormValidation;
/*      */ import com.techempower.gemini.GeminiApplication;
/*      */ import com.universal.milestone.Cache;
/*      */ import com.universal.milestone.EmailDistribution;
/*      */ import com.universal.milestone.EmailDistributionDetail;
/*      */ import com.universal.milestone.EmailDistributionHandler;
/*      */ import com.universal.milestone.EmailDistributionManager;
/*      */ import com.universal.milestone.EmailDistributionReleasingFamily;
/*      */ import com.universal.milestone.Environment;
/*      */ import com.universal.milestone.Form;
/*      */ import com.universal.milestone.MilestoneConstants;
/*      */ import com.universal.milestone.MilestoneHelper;
/*      */ import com.universal.milestone.MilestoneSecurity;
/*      */ import com.universal.milestone.Notepad;
/*      */ import com.universal.milestone.SecureHandler;
/*      */ import com.universal.milestone.User;
/*      */ import com.universal.milestone.UserManager;
/*      */ import java.util.Vector;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class EmailDistributionHandler
/*      */   extends SecureHandler
/*      */ {
/*      */   public static final String COMPONENT_CODE = "hEmailDist";
/*      */   public GeminiApplication application;
/*      */   public ComponentLog log;
/*      */   
/*      */   public EmailDistributionHandler(GeminiApplication application) {
/*   61 */     this.application = application;
/*   62 */     this.log = application.getLog("hEmailDist");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   69 */   public String getDescription() { return "emailDistribution"; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean acceptRequest(Dispatcher dispatcher, Context context, String command) {
/*   79 */     if (super.acceptRequest(dispatcher, context, command) && 
/*   80 */       command.startsWith("email-distribution")) {
/*   81 */       return handleRequest(dispatcher, context, command);
/*      */     }
/*      */ 
/*      */     
/*   85 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean handleRequest(Dispatcher dispatcher, Context context, String command) {
/*   94 */     if (command.equalsIgnoreCase("email-distribution-search")) {
/*   95 */       emailDistributionSearch(context);
/*      */     }
/*   97 */     else if (command.equalsIgnoreCase("email-distribution-save")) {
/*   98 */       emailDistributionSave(context);
/*      */     }
/*  100 */     else if (command.equalsIgnoreCase("email-distribution-delete")) {
/*  101 */       emailDistributionDelete(context);
/*      */     }
/*  103 */     else if (command.equalsIgnoreCase("email-distribution-search-results")) {
/*  104 */       emailDistributionSearchResults(context);
/*      */     }
/*  106 */     else if (command.equalsIgnoreCase("email-distribution-editor")) {
/*  107 */       emailDistributionEditor(context);
/*      */     }
/*  109 */     else if (command.equalsIgnoreCase("email-distribution-new")) {
/*  110 */       emailDistributionNew(context);
/*      */     }
/*  112 */     else if (command.equalsIgnoreCase("email-distribution-sort")) {
/*  113 */       emailDistributionSort(context);
/*      */     
/*      */     }
/*  116 */     else if (command.equalsIgnoreCase("email-distribution-save-releasing-family")) {
/*      */       
/*  118 */       emailDistributionReleasingFamilySave(dispatcher, context);
/*      */     
/*      */     }
/*  121 */     else if (command.equalsIgnoreCase("email-distribution-copy")) {
/*  122 */       emailDistributionCopy(context);
/*      */     } 
/*      */     
/*  125 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean emailDistributionSearch(Context context) {
/*  133 */     Notepad notepad = MilestoneHelper.getNotepadFromSession(
/*  134 */         20, context);
/*  135 */     EmailDistributionManager.getInstance().setEmailDistributionNotepadQuery(
/*  136 */         context, notepad);
/*      */ 
/*      */ 
/*      */     
/*  140 */     notepad.setAllContents(null);
/*  141 */     notepad.setSelected(null);
/*      */     
/*  143 */     emailDistributionEditor(context);
/*      */     
/*  145 */     return true;
/*      */   }
/*      */ 
/*      */   
/*  149 */   private boolean emailDistributionSearchResults(Context context) { return context.includeJSP("email-distribution-search-results.jsp"); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean emailDistributionEditor(Context context) {
/*  159 */     context.removeSessionValue("copiedEmailDistObj");
/*      */     
/*  161 */     Notepad notepad = getEmailDistributionNotepad(context);
/*  162 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*  163 */     EmailDistribution emailDist = getEmailDistribution(context);
/*      */     
/*  165 */     context.putSessionValue("emailDistribution", emailDist);
/*      */     
/*  167 */     if (emailDist != null) {
/*  168 */       Form form = null;
/*      */       
/*  170 */       if (emailDist != null) {
/*  171 */         form = buildForm(context, emailDist);
/*      */       } else {
/*  173 */         form = buildNewForm(context);
/*      */       } 
/*      */       
/*  176 */       return context.includeJSP("email-distribution-editor.jsp");
/*      */     } 
/*      */     
/*  179 */     return emailDistributionGoToBlank(context);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean emailDistributionSave(Context context) {
/*  187 */     User sessionUser = MilestoneSecurity.getUser(context);
/*  188 */     EmailDistribution emailDist = (EmailDistribution)context.getSessionValue(
/*  189 */         "emailDistribution");
/*      */     
/*  191 */     boolean isNewUser = false;
/*      */     
/*  193 */     if (emailDist == null) {
/*  194 */       emailDist = new EmailDistribution();
/*  195 */       isNewUser = true;
/*      */     } 
/*      */ 
/*      */     
/*  199 */     boolean isRelFamilyNewUser = false;
/*  200 */     if (isNewUser || emailDist.getDistributionId() == -1) {
/*  201 */       isRelFamilyNewUser = true;
/*      */     }
/*  203 */     Form form = buildForm(context, emailDist);
/*      */ 
/*      */     
/*  206 */     if (EmailDistributionManager.getInstance().isTimestampValid(emailDist)) {
/*  207 */       form.setValues(context);
/*      */       
/*  209 */       Vector emailDistDetail = emailDist.getEmailDistributionDetail();
/*      */ 
/*      */       
/*  212 */       String firstName = form.getStringValue("firstName");
/*  213 */       emailDist.setFirstName(firstName);
/*      */ 
/*      */       
/*  216 */       String lastName = form.getStringValue("lastName");
/*  217 */       emailDist.setLastName(lastName);
/*      */ 
/*      */       
/*  220 */       String email = form.getStringValue("email");
/*  221 */       emailDist.setEmail(email);
/*      */ 
/*      */       
/*  224 */       emailDist.setPfm(((FormCheckBox)form.getElement("pfm")).isChecked());
/*      */ 
/*      */       
/*  227 */       emailDist.setBom(((FormCheckBox)form.getElement("bom")).isChecked());
/*      */ 
/*      */       
/*  230 */       emailDist.setPromo(((FormCheckBox)form.getElement("promo")).isChecked());
/*      */ 
/*      */       
/*  233 */       emailDist.setCommercial(
/*  234 */           ((FormCheckBox)form.getElement("commercial")).isChecked());
/*      */       
/*  236 */       emailDist.setInactive(
/*  237 */           ((FormCheckBox)form.getElement("inactive")).isChecked());
/*      */ 
/*      */       
/*  240 */       String westString = form.getStringValue("Distribution");
/*      */       
/*  242 */       if (westString.equalsIgnoreCase("West")) {
/*  243 */         emailDist.setLabelDistribution(0);
/*  244 */       } else if (westString.equalsIgnoreCase("East")) {
/*  245 */         emailDist.setLabelDistribution(1);
/*  246 */       } else if (westString.equalsIgnoreCase("Both")) {
/*  247 */         emailDist.setLabelDistribution(2);
/*      */       } else {
/*  249 */         emailDist.setLabelDistribution(3);
/*      */       } 
/*      */       
/*  252 */       String productType = form.getStringValue("ProductType");
/*  253 */       if (productType.equalsIgnoreCase("Digital")) {
/*  254 */         emailDist.setProductType(0);
/*  255 */       } else if (productType.equalsIgnoreCase("Physical")) {
/*  256 */         emailDist.setProductType(1);
/*      */       } else {
/*  258 */         emailDist.setProductType(2);
/*      */       } 
/*      */       
/*  261 */       Vector checkedDetails = new Vector();
/*  262 */       Vector environments = Cache.getInstance().getEnvironments();
/*      */ 
/*      */       
/*  265 */       if (environments != null) {
/*  266 */         for (int i = 0; i < environments.size(); i++) {
/*  267 */           Environment environment = (Environment)environments.get(i);
/*  268 */           if (environment != null && 
/*  269 */             form.getElement("uc" + environment.getStructureID()) != null) {
/*  270 */             if (((FormCheckBox)form.getElement("uc" + 
/*  271 */                 environment.getStructureID()))
/*  272 */               .isChecked())
/*      */             {
/*  274 */               checkedDetails.add(environment);
/*      */             }
/*      */           }
/*      */         } 
/*      */       }
/*      */ 
/*      */       
/*  281 */       if (!EmailDistributionManager.getInstance().isDuplicate(emailDist)) {
/*      */         
/*  283 */         if (!form.isUnchanged()) {
/*  284 */           FormValidation formValidation = form.validate();
/*  285 */           if (formValidation.isGood()) {
/*      */             
/*  287 */             EmailDistribution savedEmailDist = EmailDistributionManager.getInstance().save(emailDist, sessionUser, checkedDetails, 
/*  288 */                 context);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  294 */             if (emailDist.getCopyDistributionId() != -1) {
/*  295 */               EmailDistributionManager.getInstance().saveCopyEmailDistributionReleasingFamily(
/*  296 */                   savedEmailDist.getDistributionId(), emailDist.getCopyDistributionId(), sessionUser.getUserId());
/*      */             }
/*      */             
/*  299 */             FormElement lastUpdated = form.getElement("lastupdatedon");
/*  300 */             if (savedEmailDist.getLastUpdateOn() != null) {
/*  301 */               lastUpdated.setValue(MilestoneHelper.getLongDate(
/*  302 */                     savedEmailDist.getLastUpdateOn()));
/*      */             }
/*  304 */             savedEmailDist = EmailDistributionManager.getInstance()
/*  305 */               .getEmailDistribution(savedEmailDist.getDistributionId(), true);
/*  306 */             form = buildForm(context, savedEmailDist);
/*      */             
/*  308 */             context.putSessionValue("emailDistribution", savedEmailDist);
/*      */ 
/*      */             
/*  311 */             Notepad notepad = getEmailDistributionNotepad(context);
/*  312 */             notepad.setAllContents(null);
/*      */ 
/*      */ 
/*      */             
/*  316 */             if (isNewUser) {
/*  317 */               notepad.newSelectedReset();
/*  318 */               notepad = getEmailDistributionNotepad(context);
/*  319 */               notepad.setSelected(savedEmailDist);
/*  320 */               notepad = getEmailDistributionNotepad(context);
/*      */             } else {
/*      */               
/*  323 */               notepad = getEmailDistributionNotepad(context);
/*  324 */               notepad.setSelected(savedEmailDist);
/*  325 */               emailDist = (EmailDistribution)notepad.validateSelected();
/*      */               
/*  327 */               if (emailDist == null) {
/*  328 */                 return emailDistributionGoToBlank(context);
/*      */               }
/*      */               
/*  331 */               form = buildForm(context, emailDist);
/*      */             }
/*      */           
/*      */           } else {
/*      */             
/*  336 */             context.putDelivery("FormValidation", formValidation);
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  343 */         if (isRelFamilyNewUser) {
/*  344 */           context.putSessionValue("showAssignedEmail", "ASSIGNED");
/*      */         }
/*      */       } else {
/*      */         
/*  348 */         Notepad notepad = getEmailDistributionNotepad(context);
/*  349 */         context.putDelivery("AlertMessage", "Duplicate.");
/*  350 */         emailDist = (EmailDistribution)notepad.validateSelected();
/*  351 */         form = buildForm(context, emailDist);
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  356 */       context.putDelivery("AlertMessage", "The record is unavailable for editing at this time.  Please refresh your screen and edit it at a later time.");
/*      */     } 
/*      */     
/*  359 */     context.putDelivery("Form", form);
/*  360 */     return context.includeJSP("email-distribution-editor.jsp");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean emailDistributionNew(Context context) {
/*  367 */     Notepad notepad = getEmailDistributionNotepad(context);
/*  368 */     notepad.setSelected(null);
/*  369 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*  370 */     context.removeSessionValue("emailDistribution");
/*  371 */     Form form = buildNewForm(context);
/*  372 */     return context.includeJSP("email-distribution-editor.jsp");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean emailDistributionDelete(Context context) {
/*  380 */     Notepad notepad = getEmailDistributionNotepad(context);
/*  381 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */     
/*  383 */     EmailDistribution emailDist = getEmailDistribution(context);
/*      */ 
/*      */     
/*  386 */     if (emailDist != null) {
/*  387 */       EmailDistributionManager.getInstance().deleteEmailDistribution(
/*  388 */           emailDist.getDistributionId());
/*      */       
/*  390 */       notepad.setAllContents(null);
/*  391 */       notepad.setSelected(null);
/*      */     } 
/*      */     
/*  394 */     return emailDistributionEditor(context);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Form buildForm(Context context, EmailDistribution emailDist) {
/*  402 */     String showAssigned = context.getRequest().getParameter("showAssignedEmail");
/*  403 */     if (showAssigned == null && context.getSessionValue("showAssignedEmail") != null) {
/*  404 */       showAssigned = (String)context.getSessionValue("showAssignedEmail");
/*      */     }
/*  406 */     if (showAssigned != null) {
/*      */       
/*  408 */       if (emailDist.getDistributionId() == -1) {
/*  409 */         context.putSessionValue("showAssignedEmail", "ALL");
/*      */       } else {
/*  411 */         context.putSessionValue("showAssignedEmail", showAssigned);
/*      */       } 
/*      */     } else {
/*  414 */       context.putSessionValue("showAssignedEmail", "ASSIGNED");
/*      */     } 
/*      */     
/*  417 */     Form emailDistForm = new Form(this.application, "emailDistributionForm", 
/*  418 */         this.application.getInfrastructure().getServletURL(), 
/*  419 */         "POST");
/*      */ 
/*      */     
/*  422 */     FormTextField firstName = new FormTextField("firstName", 
/*  423 */         emailDist.getFirstName(), true, 
/*  424 */         30, 30);
/*  425 */     firstName.setTabIndex(1);
/*  426 */     emailDistForm.addElement(firstName);
/*      */ 
/*      */     
/*  429 */     FormTextField lastName = new FormTextField("lastName", 
/*  430 */         emailDist.getLastName(), true, 
/*  431 */         30, 30);
/*  432 */     lastName.setTabIndex(2);
/*  433 */     emailDistForm.addElement(lastName);
/*      */ 
/*      */     
/*  436 */     FormCheckBox inactive = new FormCheckBox("inactive", "Inactive", false, 
/*  437 */         !!emailDist.getInactive());
/*  438 */     inactive.setId("inactive");
/*  439 */     inactive.setTabIndex(3);
/*  440 */     emailDistForm.addElement(inactive);
/*      */ 
/*      */     
/*  443 */     FormTextField email = new FormTextField("email", emailDist.getEmail(), true, 
/*  444 */         50, 50);
/*  445 */     email.addFormEvent("onfocus", "JavaScript:buildEmail()");
/*  446 */     email.addFormEvent("onblur", "JavaScript:isValidEmailAddr(this)");
/*  447 */     email.setTabIndex(4);
/*  448 */     emailDistForm.addElement(email);
/*      */ 
/*      */     
/*  451 */     String westEast = "None";
/*  452 */     if (emailDist.getLabelDistribution() == 
/*  453 */       1)
/*  454 */       westEast = "East"; 
/*  455 */     if (emailDist.getLabelDistribution() == 0)
/*      */     {
/*  457 */       westEast = "West"; } 
/*  458 */     if (emailDist.getLabelDistribution() == 
/*  459 */       2) {
/*  460 */       westEast = "Both";
/*      */     }
/*  462 */     FormRadioButtonGroup distribution = new FormRadioButtonGroup("Distribution", 
/*  463 */         westEast, "West, East, Both, None", false);
/*  464 */     distribution.setTabIndex(5);
/*  465 */     emailDistForm.addElement(distribution);
/*      */ 
/*      */     
/*  468 */     String productTypeStr = "Both";
/*  469 */     if (emailDist.getProductType() == 0)
/*  470 */       productTypeStr = "Digital"; 
/*  471 */     if (emailDist.getProductType() == 1) {
/*  472 */       productTypeStr = "Physical";
/*      */     }
/*  474 */     FormRadioButtonGroup productType = new FormRadioButtonGroup("ProductType", 
/*  475 */         productTypeStr, "Digital, Physical, Both", false);
/*  476 */     distribution.setTabIndex(6);
/*  477 */     emailDistForm.addElement(productType);
/*      */ 
/*      */     
/*  480 */     FormCheckBox pfm = new FormCheckBox("PFM", "PFM", false, !!emailDist.getPfm());
/*  481 */     pfm.setId("pfm");
/*  482 */     pfm.setTabIndex(6);
/*  483 */     emailDistForm.addElement(pfm);
/*      */ 
/*      */     
/*  486 */     FormCheckBox bom = new FormCheckBox("BOM", "BOM", false, !!emailDist.getBom());
/*  487 */     bom.setId("bom");
/*  488 */     bom.setTabIndex(7);
/*  489 */     emailDistForm.addElement(bom);
/*      */ 
/*      */     
/*  492 */     FormCheckBox promo = new FormCheckBox("Promo", "Promo", false, 
/*  493 */         !!emailDist.getPromo());
/*  494 */     promo.setId("promo");
/*  495 */     promo.setTabIndex(8);
/*  496 */     emailDistForm.addElement(promo);
/*      */ 
/*      */     
/*  499 */     FormCheckBox commercial = new FormCheckBox("Commercial", "Commercial", false, 
/*  500 */         !!emailDist.getCommercial());
/*  501 */     commercial.setId("commercial");
/*  502 */     commercial.setTabIndex(9);
/*  503 */     emailDistForm.addElement(commercial);
/*      */ 
/*      */     
/*  506 */     Vector environments = Cache.getInstance().getEnvironments();
/*      */ 
/*      */ 
/*      */     
/*  510 */     int distributionId = emailDist.getDistributionId();
/*      */ 
/*      */     
/*  513 */     Vector userEnvironments = null;
/*  514 */     userEnvironments = emailDist.getEmailDistributionDetail();
/*      */ 
/*      */ 
/*      */     
/*  518 */     for (int i = 0; i < environments.size(); i++) {
/*  519 */       Environment environment = (Environment)environments.elementAt(i);
/*      */       
/*  521 */       if (environment != null) {
/*      */ 
/*      */         
/*  524 */         if (userEnvironments != null) {
/*  525 */           for (int a = 0; a < userEnvironments.size(); a++) {
/*  526 */             EmailDistributionDetail emailDistDetail = 
/*  527 */               (EmailDistributionDetail)userEnvironments.elementAt(a);
/*      */             
/*  529 */             if (emailDistDetail != null && 
/*  530 */               emailDistDetail.getStructureId() == environment.getStructureID()) {
/*      */               
/*  532 */               FormCheckBox environmentCheckbox = new FormCheckBox("uc" + 
/*  533 */                   emailDistDetail.getStructureId(), 
/*  534 */                   "uc" + emailDistDetail.getStructureId(), environment.getName(), false, true);
/*  535 */               environmentCheckbox.setId("uc" + emailDistDetail.getStructureId());
/*  536 */               emailDistForm.addElement(environmentCheckbox);
/*      */             } 
/*      */           } 
/*      */         }
/*      */         
/*  541 */         FormCheckBox environmentCheckbox = new FormCheckBox("uc" + 
/*  542 */             environment.getStructureID(), "uc" + environment.getStructureID(), 
/*  543 */             environment.getName(), false, false);
/*  544 */         environmentCheckbox.setId("uc" + environment.getStructureID());
/*  545 */         emailDistForm.addElement(environmentCheckbox);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  550 */     FormTextField lastUpdated = new FormTextField("lastUpdatedOn", false, 50);
/*  551 */     if (emailDist.getLastUpdateOn() != null)
/*  552 */       lastUpdated.setValue(MilestoneHelper.getLongDate(
/*  553 */             emailDist.getLastUpdateOn())); 
/*  554 */     emailDistForm.addElement(lastUpdated);
/*      */     
/*  556 */     FormTextField lastUpdatedBy = new FormTextField("lastUpdatedBy", false, 50);
/*  557 */     if (UserManager.getInstance().getUser(emailDist.getLastUpdateBy(), true) != null)
/*  558 */       lastUpdatedBy.setValue(
/*  559 */           UserManager.getInstance().getUser(emailDist.getLastUpdateBy(), true).getLogin()); 
/*  560 */     emailDistForm.addElement(lastUpdatedBy);
/*      */ 
/*      */     
/*  563 */     FormTextField firstNameSrch = new FormTextField("firstNameSrch", "", false, 
/*  564 */         20);
/*  565 */     firstNameSrch.setId("firstNameSrch");
/*  566 */     emailDistForm.addElement(firstNameSrch);
/*      */     
/*  568 */     FormTextField lastNameSrch = new FormTextField("lastNameSrch", "", false, 
/*  569 */         20);
/*  570 */     lastNameSrch.setId("lastNameSrch");
/*  571 */     emailDistForm.addElement(lastNameSrch);
/*      */     
/*  573 */     FormDropDownMenu environmentSrch = 
/*  574 */       MilestoneHelper.getCorporateStructureDropDown("environmentSrch", environments, "", false, true);
/*  575 */     environmentSrch.setId("environmentSrch");
/*  576 */     emailDistForm.addElement(environmentSrch);
/*      */     
/*  578 */     FormDropDownMenu formTypeSrch = new FormDropDownMenu("formTypeSrch", "", 
/*  579 */         "-1,pfm,bom", 
/*  580 */         "&nbsp;,PFM, BOM", false);
/*  581 */     formTypeSrch.setId("formTypeSrch");
/*  582 */     emailDistForm.addElement(formTypeSrch);
/*      */     
/*  584 */     FormDropDownMenu releaseTypeSrch = new FormDropDownMenu("releaseTypeSrch", 
/*  585 */         "", 
/*  586 */         "-1,promo,commercial", 
/*  587 */         "&nbsp;,Promo, Commercial", false);
/*  588 */     releaseTypeSrch.setId("releaseTypeSrch");
/*  589 */     emailDistForm.addElement(releaseTypeSrch);
/*      */     
/*  591 */     FormDropDownMenu productTypeSrch = new FormDropDownMenu("productTypeSrch", 
/*  592 */         "", 
/*  593 */         "-1,2,1,0", 
/*  594 */         "&nbsp;,Both ,Physical, Digital", false);
/*  595 */     productTypeSrch.setId("productTypeSrch");
/*  596 */     emailDistForm.addElement(productTypeSrch);
/*      */     
/*  598 */     emailDistForm.addElement(new FormHidden("cmd", "email-distribution-editor"));
/*  599 */     context.putDelivery("Form", emailDistForm);
/*      */     
/*  601 */     if (context.getSessionValue("NOTEPAD_EMAIL_DISTRIBUTION_VISIBLE") != null) {
/*  602 */       context.putDelivery("isNotePadVisible", 
/*  603 */           (Boolean)context.getSessionValue("NOTEPAD_EMAIL_DISTRIBUTION_VISIBLE"));
/*      */     }
/*  605 */     return emailDistForm;
/*      */   }
/*      */ 
/*      */   
/*      */   public Notepad getEmailDistributionNotepad(Context context) {
/*  610 */     Vector contents = new Vector();
/*      */     
/*  612 */     if (MilestoneHelper.getNotepadFromSession(20, 
/*  613 */         context) != null) {
/*  614 */       Notepad notepad = MilestoneHelper.getNotepadFromSession(
/*  615 */           20, context);
/*      */       
/*  617 */       if (notepad.getAllContents() == null) {
/*  618 */         this.log.debug("---------Reseting note pad contents------------");
/*  619 */         EmailDistributionManager.getInstance(); contents = 
/*  620 */           EmailDistributionManager.getDistributionNotepadList(notepad);
/*  621 */         notepad.setAllContents(contents);
/*      */       } 
/*      */       
/*  624 */       return notepad;
/*      */     } 
/*      */     
/*  627 */     String[] columnNames = {
/*  628 */         "First Name", "Last Name" };
/*  629 */     EmailDistributionManager.getInstance(); contents = 
/*  630 */       EmailDistributionManager.getDistributionNotepadList(null);
/*      */     
/*  632 */     Notepad notepad = new Notepad(contents, 0, 7, 
/*  633 */         "Email Distribution", 
/*  634 */         20, columnNames);
/*  635 */     EmailDistributionManager.getInstance().setEmailDistributionNotepadQuery(
/*  636 */         context, notepad);
/*      */     
/*  638 */     return notepad;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Form buildNewForm(Context context) {
/*  647 */     context.putSessionValue("showAssignedEmail", "NEW");
/*      */ 
/*      */     
/*  650 */     Form emailDistForm = new Form(this.application, "emailDistributionForm", 
/*  651 */         this.application.getInfrastructure().getServletURL(), 
/*  652 */         "POST");
/*      */ 
/*      */     
/*  655 */     FormTextField firstName = new FormTextField("firstName", "", true, 30, 100);
/*  656 */     firstName.setTabIndex(1);
/*  657 */     emailDistForm.addElement(firstName);
/*      */ 
/*      */     
/*  660 */     FormTextField lastName = new FormTextField("lastName", "", true, 30, 30);
/*  661 */     lastName.setTabIndex(2);
/*  662 */     emailDistForm.addElement(lastName);
/*      */ 
/*      */     
/*  665 */     FormCheckBox inactive = new FormCheckBox("inactive", "Inactive", false, false);
/*  666 */     inactive.setId("inactive");
/*  667 */     inactive.setTabIndex(3);
/*  668 */     emailDistForm.addElement(inactive);
/*      */ 
/*      */     
/*  671 */     FormTextField email = new FormTextField("email", "", false, 50, 50);
/*  672 */     email.addFormEvent("onfocus", "JavaScript:buildEmail()");
/*  673 */     email.addFormEvent("onblur", "JavaScript:isValidEmailAddr(this)");
/*  674 */     email.setTabIndex(4);
/*  675 */     emailDistForm.addElement(email);
/*      */ 
/*      */     
/*  678 */     String westEast = "None";
/*  679 */     FormRadioButtonGroup distribution = new FormRadioButtonGroup("Distribution", 
/*  680 */         westEast, "West, East, Both, None", false);
/*  681 */     distribution.setTabIndex(5);
/*  682 */     emailDistForm.addElement(distribution);
/*      */ 
/*      */     
/*  685 */     String productTypeStr = "Both";
/*  686 */     FormRadioButtonGroup productType = new FormRadioButtonGroup("ProductType", 
/*  687 */         productTypeStr, "Digital, Physical, Both", false);
/*  688 */     distribution.setTabIndex(6);
/*  689 */     emailDistForm.addElement(productType);
/*      */ 
/*      */     
/*  692 */     FormCheckBox pfm = new FormCheckBox("PFM", "PFM", false, false);
/*  693 */     pfm.setId("pfm");
/*  694 */     pfm.setTabIndex(6);
/*  695 */     emailDistForm.addElement(pfm);
/*      */ 
/*      */     
/*  698 */     FormCheckBox bom = new FormCheckBox("BOM", "BOM", false, false);
/*  699 */     bom.setId("bom");
/*  700 */     bom.setTabIndex(7);
/*  701 */     emailDistForm.addElement(bom);
/*      */ 
/*      */     
/*  704 */     FormCheckBox promo = new FormCheckBox("Promo", "Promo", false, false);
/*  705 */     promo.setId("promo");
/*  706 */     promo.setTabIndex(8);
/*  707 */     emailDistForm.addElement(promo);
/*      */ 
/*      */     
/*  710 */     FormCheckBox commercial = new FormCheckBox("Commercial", "Commercial", false, false);
/*  711 */     commercial.setId("commercial");
/*  712 */     commercial.setTabIndex(9);
/*  713 */     emailDistForm.addElement(commercial);
/*      */ 
/*      */     
/*  716 */     Vector environments = Cache.getInstance().getEnvironments();
/*      */ 
/*      */     
/*  719 */     for (int i = 0; i < environments.size(); i++) {
/*  720 */       Environment environment = (Environment)environments.elementAt(i);
/*  721 */       if (environment != null) {
/*  722 */         FormCheckBox environmentCheckbox = new FormCheckBox("uc" + 
/*  723 */             environment.getStructureID(), "uc" + environment.getStructureID(), 
/*  724 */             environment.getName(), false, false);
/*  725 */         environmentCheckbox.setId("uc" + environment.getStructureID());
/*  726 */         emailDistForm.addElement(environmentCheckbox);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  731 */     FormTextField lastUpdated = new FormTextField("lastUpdatedOn", false, 50);
/*  732 */     emailDistForm.addElement(lastUpdated);
/*      */     
/*  734 */     FormTextField lastUpdatedBy = new FormTextField("lastUpdatedBy", false, 50);
/*  735 */     emailDistForm.addElement(lastUpdatedBy);
/*      */ 
/*      */     
/*  738 */     FormTextField firstNameSrch = new FormTextField("firstNameSrch", "", false, 
/*  739 */         20);
/*  740 */     firstNameSrch.setId("firstNameSrch");
/*  741 */     emailDistForm.addElement(firstNameSrch);
/*      */     
/*  743 */     FormTextField lastNameSrch = new FormTextField("lastNameSrch", "", false, 
/*  744 */         20);
/*  745 */     lastNameSrch.setId("lastNameSrch");
/*  746 */     emailDistForm.addElement(lastNameSrch);
/*      */     
/*  748 */     FormDropDownMenu environmentSrch = 
/*  749 */       MilestoneHelper.getCorporateStructureDropDown("environmentSrch", environments, "", false, true);
/*  750 */     environmentSrch.setId("environmentSrch");
/*  751 */     emailDistForm.addElement(environmentSrch);
/*      */     
/*  753 */     FormDropDownMenu formTypeSrch = new FormDropDownMenu("formTypeSrch", "", 
/*  754 */         "-1,pfm,bom", 
/*  755 */         "&nbsp;,PFM, BOM", false);
/*  756 */     formTypeSrch.setId("formTypeSrch");
/*  757 */     emailDistForm.addElement(formTypeSrch);
/*      */     
/*  759 */     FormDropDownMenu releaseTypeSrch = new FormDropDownMenu("releaseTypeSrch", 
/*  760 */         "", 
/*  761 */         "-1,promo,commercial", 
/*  762 */         "&nbsp;,Promo, Commercial", false);
/*  763 */     releaseTypeSrch.setId("releaseTypeSrch");
/*  764 */     emailDistForm.addElement(releaseTypeSrch);
/*      */     
/*  766 */     FormDropDownMenu productTypeSrch = new FormDropDownMenu("productTypeSrch", 
/*  767 */         "", 
/*  768 */         "-1,2,1,0", 
/*  769 */         "&nbsp;,Both, Physical, Digital", false);
/*  770 */     productTypeSrch.setId("productTypeSrch");
/*  771 */     emailDistForm.addElement(productTypeSrch);
/*      */     
/*  773 */     emailDistForm.addElement(new FormHidden("cmd", "email-distribution-new"));
/*  774 */     context.putDelivery("Form", emailDistForm);
/*      */     
/*  776 */     if (context.getSessionValue("NOTEPAD_EMAIL_DISTRIBUTION_VISIBLE") != null) {
/*  777 */       context.putDelivery("isNotePadVisible", 
/*  778 */           (Boolean)context.getSessionValue("NOTEPAD_EMAIL_DISTRIBUTION_VISIBLE"));
/*      */     }
/*  780 */     return emailDistForm;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean emailDistributionSort(Context context) {
/*  785 */     int sort = Integer.parseInt(context.getParameter("OrderBy"));
/*      */ 
/*      */     
/*  788 */     Notepad notepad = MilestoneHelper.getNotepadFromSession(
/*  789 */         20, context);
/*      */     
/*  791 */     if (notepad.getSearchQuery() == null || notepad.getSearchQuery().equals("")) {
/*  792 */       notepad.setSearchQuery(
/*  793 */           EmailDistributionManager.getInstance().getDefaultQuery());
/*      */     }
/*  795 */     notepad.setOrderBy(" ORDER BY [" + 
/*  796 */         MilestoneConstants.SORT_EMAIL_DISTRIBUTION[sort] + "]");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  801 */     notepad.setAllContents(null);
/*  802 */     notepad = getEmailDistributionNotepad(context);
/*  803 */     notepad.goToSelectedPage();
/*      */ 
/*      */     
/*  806 */     emailDistributionEditor(context);
/*      */     
/*  808 */     return true;
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
/*      */   private boolean emailDistributionGoToBlank(Context context) {
/*  822 */     context.putDelivery("isNotePadVisible", 
/*  823 */         new Boolean(
/*  824 */           MilestoneHelper.getNotePadVisiblitiy(20, 
/*  825 */             context)));
/*      */     
/*  827 */     Form form = new Form(this.application, "form", 
/*  828 */         this.application.getInfrastructure().getServletURL(), 
/*  829 */         "Post");
/*  830 */     form.addElement(new FormHidden("cmd", "email-distribution-editor", true));
/*  831 */     form.addElement(new FormHidden("OrderBy", "", true));
/*      */ 
/*      */     
/*  834 */     FormTextField firstNameSrch = new FormTextField("firstNameSrch", "", false, 
/*  835 */         20);
/*  836 */     firstNameSrch.setId("firstNameSrch");
/*  837 */     form.addElement(firstNameSrch);
/*      */     
/*  839 */     FormTextField lastNameSrch = new FormTextField("lastNameSrch", "", false, 
/*  840 */         20);
/*  841 */     lastNameSrch.setId("lastNameSrch");
/*  842 */     form.addElement(lastNameSrch);
/*      */ 
/*      */     
/*  845 */     Vector environments = Cache.getInstance().getEnvironments();
/*  846 */     FormDropDownMenu environmentSrch = 
/*  847 */       MilestoneHelper.getCorporateStructureDropDown("environmentSrch", environments, "", false, true);
/*  848 */     environmentSrch.setId("environmentSrch");
/*  849 */     form.addElement(environmentSrch);
/*      */     
/*  851 */     FormDropDownMenu formTypeSrch = new FormDropDownMenu("formTypeSrch", "", 
/*  852 */         "-1,pfm,bom", 
/*  853 */         "&nbsp;,PFM, BOM", false);
/*  854 */     formTypeSrch.setId("formTypeSrch");
/*  855 */     form.addElement(formTypeSrch);
/*      */     
/*  857 */     FormDropDownMenu releaseTypeSrch = new FormDropDownMenu("releaseTypeSrch", 
/*  858 */         "", 
/*  859 */         "-1,promo,commercial", 
/*  860 */         "&nbsp;,Promo, Commercial", false);
/*  861 */     releaseTypeSrch.setId("releaseTypeSrch");
/*  862 */     form.addElement(releaseTypeSrch);
/*      */     
/*  864 */     FormDropDownMenu productTypeSrch = new FormDropDownMenu("productTypeSrch", 
/*  865 */         "", 
/*  866 */         "-1,2,1,0", 
/*  867 */         "&nbsp;,Both, Physical, Digital", false);
/*  868 */     productTypeSrch.setId("productTypeSrch");
/*  869 */     form.addElement(productTypeSrch);
/*      */     
/*  871 */     context.putDelivery("Form", form);
/*      */     
/*  873 */     return context.includeJSP("blank-email-distribution-editor.jsp");
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
/*      */   public static EmailDistribution getEmailDistribution(Context context) {
/*  889 */     EmailDistribution emailDist = null;
/*  890 */     int distributionId = -1;
/*  891 */     Notepad notepad = MilestoneHelper.getNotepadFromSession(
/*  892 */         20, context);
/*      */     
/*  894 */     if (context.getRequestValue("distribution-id") != null) {
/*      */       
/*  896 */       distributionId = Integer.parseInt(context.getRequestValue(
/*  897 */             "distribution-id"));
/*      */ 
/*      */ 
/*      */       
/*  901 */       emailDist = EmailDistributionManager.getInstance().getEmailDistribution(
/*  902 */           distributionId, true);
/*  903 */       notepad.setSelected(emailDist);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*  910 */     else if ((EmailDistribution)notepad.getSelected() != null) {
/*      */       
/*  912 */       EmailDistribution notepadEmailDist = (EmailDistribution)notepad
/*  913 */         .getSelected();
/*  914 */       distributionId = notepadEmailDist.getDistributionId();
/*  915 */       emailDist = EmailDistributionManager.getInstance().getEmailDistribution(
/*  916 */           distributionId, true);
/*  917 */       if (emailDist.getEmailDistributionDetail() == null) {
/*  918 */         System.out.println(
/*  919 */             "<<< emailDist.getEmailDistributionDetail() == null ");
/*      */       
/*      */       }
/*      */     
/*      */     }
/*  924 */     else if (notepad.getAllContents() != null && 
/*  925 */       notepad.getAllContents().size() > 0) {
/*      */       
/*  927 */       emailDist = (EmailDistribution)notepad.getAllContents().get(0);
/*  928 */       notepad.setSelected(emailDist);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  933 */     return emailDist;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean emailDistributionReleasingFamilySave(Dispatcher dispatcher, Context context) {
/*  943 */     EmailDistributionReleasingFamily.save(context);
/*      */ 
/*      */     
/*  946 */     return emailDistributionEditor(context);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean emailDistributionCopy(Context context) {
/*  956 */     EmailDistribution emailDist = getEmailDistribution(context);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  961 */     EmailDistribution copiedEmailDistObj = null;
/*      */ 
/*      */     
/*  964 */     context.removeSessionValue("copiedEmailDistObj");
/*      */     
/*      */     try {
/*  967 */       copiedEmailDistObj = (EmailDistribution)emailDist.clone();
/*  968 */       context.putSessionValue("copiedEmailDistObj", copiedEmailDistObj);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  973 */       copiedEmailDistObj.setCopyDistributionId(copiedEmailDistObj.getDistributionId());
/*  974 */       copiedEmailDistObj.setDistributionId(-1);
/*  975 */       context.putSessionValue("emailDistribution", copiedEmailDistObj);
/*      */     
/*      */     }
/*  978 */     catch (CloneNotSupportedException cloneNotSupportedException) {}
/*      */ 
/*      */     
/*  981 */     copiedEmailDistObj.setFirstName("");
/*  982 */     copiedEmailDistObj.setLastName("");
/*  983 */     copiedEmailDistObj.setEmail("");
/*  984 */     copiedEmailDistObj.setDistributionId(-1);
/*      */     
/*  986 */     Notepad notepad = getEmailDistributionNotepad(context);
/*  987 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */     
/*  989 */     Form form = null;
/*  990 */     form = buildCopyForm(context, copiedEmailDistObj);
/*      */     
/*  992 */     form.addElement(new FormHidden("cmd", "email-distribution-copy"));
/*      */ 
/*      */ 
/*      */     
/*  996 */     context.putDelivery("Form", form);
/*  997 */     return context.includeJSP("email-distribution-editor.jsp");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Form buildCopyForm(Context context, EmailDistribution emailDist) {
/* 1004 */     context.putSessionValue("showAssigned", "NEW");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1021 */     Form emailDistForm = new Form(this.application, "emailDistributionForm", 
/* 1022 */         this.application.getInfrastructure().getServletURL(), 
/* 1023 */         "POST");
/*      */ 
/*      */     
/* 1026 */     FormTextField firstName = new FormTextField("firstName", 
/* 1027 */         emailDist.getFirstName(), true, 
/* 1028 */         30, 30);
/* 1029 */     firstName.setTabIndex(1);
/* 1030 */     emailDistForm.addElement(firstName);
/*      */ 
/*      */     
/* 1033 */     FormTextField lastName = new FormTextField("lastName", 
/* 1034 */         emailDist.getLastName(), true, 
/* 1035 */         30, 30);
/* 1036 */     lastName.setTabIndex(2);
/* 1037 */     emailDistForm.addElement(lastName);
/*      */ 
/*      */     
/* 1040 */     FormCheckBox inactive = new FormCheckBox("inactive", "Inactive", false, 
/* 1041 */         !!emailDist.getInactive());
/* 1042 */     inactive.setId("inactive");
/* 1043 */     inactive.setTabIndex(3);
/* 1044 */     emailDistForm.addElement(inactive);
/*      */ 
/*      */     
/* 1047 */     FormTextField email = new FormTextField("email", emailDist.getEmail(), true, 
/* 1048 */         50, 50);
/* 1049 */     email.addFormEvent("onfocus", "JavaScript:buildEmail()");
/* 1050 */     email.addFormEvent("onblur", "JavaScript:isValidEmailAddr(this)");
/* 1051 */     email.setTabIndex(4);
/* 1052 */     emailDistForm.addElement(email);
/*      */ 
/*      */     
/* 1055 */     String westEast = "None";
/* 1056 */     if (emailDist.getLabelDistribution() == 
/* 1057 */       1)
/* 1058 */       westEast = "East"; 
/* 1059 */     if (emailDist.getLabelDistribution() == 0)
/*      */     {
/* 1061 */       westEast = "West"; } 
/* 1062 */     if (emailDist.getLabelDistribution() == 
/* 1063 */       2) {
/* 1064 */       westEast = "Both";
/*      */     }
/* 1066 */     FormRadioButtonGroup distribution = new FormRadioButtonGroup("Distribution", 
/* 1067 */         westEast, "West, East, Both, None", false);
/* 1068 */     distribution.setTabIndex(5);
/* 1069 */     emailDistForm.addElement(distribution);
/*      */ 
/*      */     
/* 1072 */     String productTypeStr = "Both";
/* 1073 */     if (emailDist.getProductType() == 0)
/* 1074 */       productTypeStr = "Digital"; 
/* 1075 */     if (emailDist.getProductType() == 1) {
/* 1076 */       productTypeStr = "Physical";
/*      */     }
/* 1078 */     FormRadioButtonGroup productType = new FormRadioButtonGroup("ProductType", 
/* 1079 */         productTypeStr, "Digital, Physical, Both", false);
/* 1080 */     distribution.setTabIndex(6);
/* 1081 */     emailDistForm.addElement(productType);
/*      */ 
/*      */     
/* 1084 */     FormCheckBox pfm = new FormCheckBox("PFM", "PFM", false, !!emailDist.getPfm());
/* 1085 */     pfm.setId("pfm");
/* 1086 */     pfm.setTabIndex(6);
/* 1087 */     emailDistForm.addElement(pfm);
/*      */ 
/*      */     
/* 1090 */     FormCheckBox bom = new FormCheckBox("BOM", "BOM", false, !!emailDist.getBom());
/* 1091 */     bom.setId("bom");
/* 1092 */     bom.setTabIndex(7);
/* 1093 */     emailDistForm.addElement(bom);
/*      */ 
/*      */     
/* 1096 */     FormCheckBox promo = new FormCheckBox("Promo", "Promo", false, 
/* 1097 */         !!emailDist.getPromo());
/* 1098 */     promo.setId("promo");
/* 1099 */     promo.setTabIndex(8);
/* 1100 */     emailDistForm.addElement(promo);
/*      */ 
/*      */     
/* 1103 */     FormCheckBox commercial = new FormCheckBox("Commercial", "Commercial", false, 
/* 1104 */         !!emailDist.getCommercial());
/* 1105 */     commercial.setId("commercial");
/* 1106 */     commercial.setTabIndex(9);
/* 1107 */     emailDistForm.addElement(commercial);
/*      */ 
/*      */     
/* 1110 */     Vector environments = Cache.getInstance().getEnvironments();
/*      */ 
/*      */ 
/*      */     
/* 1114 */     int distributionId = emailDist.getDistributionId();
/*      */ 
/*      */     
/* 1117 */     Vector userEnvironments = null;
/* 1118 */     userEnvironments = emailDist.getEmailDistributionDetail();
/*      */ 
/*      */ 
/*      */     
/* 1122 */     for (int i = 0; i < environments.size(); i++) {
/* 1123 */       Environment environment = (Environment)environments.elementAt(i);
/*      */       
/* 1125 */       if (environment != null) {
/*      */ 
/*      */         
/* 1128 */         if (userEnvironments != null) {
/* 1129 */           for (int a = 0; a < userEnvironments.size(); a++) {
/* 1130 */             EmailDistributionDetail emailDistDetail = 
/* 1131 */               (EmailDistributionDetail)userEnvironments.elementAt(a);
/*      */             
/* 1133 */             if (emailDistDetail != null && 
/* 1134 */               emailDistDetail.getStructureId() == environment.getStructureID()) {
/*      */               
/* 1136 */               FormCheckBox environmentCheckbox = new FormCheckBox("uc" + 
/* 1137 */                   emailDistDetail.getStructureId(), 
/* 1138 */                   "uc" + emailDistDetail.getStructureId(), environment.getName(), false, true);
/* 1139 */               environmentCheckbox.setId("uc" + emailDistDetail.getStructureId());
/* 1140 */               emailDistForm.addElement(environmentCheckbox);
/*      */             } 
/*      */           } 
/*      */         }
/*      */         
/* 1145 */         FormCheckBox environmentCheckbox = new FormCheckBox("uc" + 
/* 1146 */             environment.getStructureID(), "uc" + environment.getStructureID(), 
/* 1147 */             environment.getName(), false, false);
/* 1148 */         environmentCheckbox.setId("uc" + environment.getStructureID());
/* 1149 */         emailDistForm.addElement(environmentCheckbox);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1154 */     FormTextField lastUpdated = new FormTextField("lastUpdatedOn", false, 50);
/* 1155 */     if (emailDist.getLastUpdateOn() != null)
/* 1156 */       lastUpdated.setValue(MilestoneHelper.getLongDate(
/* 1157 */             emailDist.getLastUpdateOn())); 
/* 1158 */     emailDistForm.addElement(lastUpdated);
/*      */     
/* 1160 */     FormTextField lastUpdatedBy = new FormTextField("lastUpdatedBy", false, 50);
/* 1161 */     if (UserManager.getInstance().getUser(emailDist.getLastUpdateBy(), true) != null)
/* 1162 */       lastUpdatedBy.setValue(
/* 1163 */           UserManager.getInstance().getUser(emailDist.getLastUpdateBy(), true).getLogin()); 
/* 1164 */     emailDistForm.addElement(lastUpdatedBy);
/*      */ 
/*      */     
/* 1167 */     FormTextField firstNameSrch = new FormTextField("firstNameSrch", "", false, 
/* 1168 */         20);
/* 1169 */     firstNameSrch.setId("firstNameSrch");
/* 1170 */     emailDistForm.addElement(firstNameSrch);
/*      */     
/* 1172 */     FormTextField lastNameSrch = new FormTextField("lastNameSrch", "", false, 
/* 1173 */         20);
/* 1174 */     lastNameSrch.setId("lastNameSrch");
/* 1175 */     emailDistForm.addElement(lastNameSrch);
/*      */     
/* 1177 */     FormDropDownMenu environmentSrch = 
/* 1178 */       MilestoneHelper.getCorporateStructureDropDown("environmentSrch", environments, "", false, true);
/* 1179 */     environmentSrch.setId("environmentSrch");
/* 1180 */     emailDistForm.addElement(environmentSrch);
/*      */     
/* 1182 */     FormDropDownMenu formTypeSrch = new FormDropDownMenu("formTypeSrch", "", 
/* 1183 */         "-1,pfm,bom", 
/* 1184 */         "&nbsp;,PFM, BOM", false);
/* 1185 */     formTypeSrch.setId("formTypeSrch");
/* 1186 */     emailDistForm.addElement(formTypeSrch);
/*      */     
/* 1188 */     FormDropDownMenu releaseTypeSrch = new FormDropDownMenu("releaseTypeSrch", 
/* 1189 */         "", 
/* 1190 */         "-1,promo,commercial", 
/* 1191 */         "&nbsp;,Promo, Commercial", false);
/* 1192 */     releaseTypeSrch.setId("releaseTypeSrch");
/* 1193 */     emailDistForm.addElement(releaseTypeSrch);
/*      */     
/* 1195 */     FormDropDownMenu productTypeSrch = new FormDropDownMenu("productTypeSrch", 
/* 1196 */         "", 
/* 1197 */         "-1,2,1,0", 
/* 1198 */         "&nbsp;,Both ,Physical, Digital", false);
/* 1199 */     productTypeSrch.setId("productTypeSrch");
/* 1200 */     emailDistForm.addElement(productTypeSrch);
/*      */     
/* 1202 */     emailDistForm.addElement(new FormHidden("cmd", "email-distribution-copy"));
/* 1203 */     context.putDelivery("Form", emailDistForm);
/*      */     
/* 1205 */     if (context.getSessionValue("NOTEPAD_EMAIL_DISTRIBUTION_VISIBLE") != null) {
/* 1206 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_EMAIL_DISTRIBUTION_VISIBLE"));
/*      */     }
/* 1208 */     return emailDistForm;
/*      */   }
/*      */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\EmailDistributionHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */