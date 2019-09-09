/*      */ package WEB-INF.classes.com.universal.milestone;
/*      */ 
/*      */ import com.techempower.ComponentLog;
/*      */ import com.techempower.StringList;
/*      */ import com.techempower.gemini.Context;
/*      */ import com.techempower.gemini.Dispatcher;
/*      */ import com.techempower.gemini.FormCheckBox;
/*      */ import com.techempower.gemini.FormDropDownMenu;
/*      */ import com.techempower.gemini.FormElement;
/*      */ import com.techempower.gemini.FormHidden;
/*      */ import com.techempower.gemini.FormTextField;
/*      */ import com.techempower.gemini.FormValidation;
/*      */ import com.techempower.gemini.GeminiApplication;
/*      */ import com.universal.milestone.Cache;
/*      */ import com.universal.milestone.Company;
/*      */ import com.universal.milestone.CorporateObjectSearchObj;
/*      */ import com.universal.milestone.CorporateStructureManager;
/*      */ import com.universal.milestone.Division;
/*      */ import com.universal.milestone.DivisionHandler;
/*      */ import com.universal.milestone.DivisionManager;
/*      */ import com.universal.milestone.Environment;
/*      */ import com.universal.milestone.Family;
/*      */ import com.universal.milestone.Form;
/*      */ import com.universal.milestone.Label;
/*      */ import com.universal.milestone.MilestoneHelper;
/*      */ import com.universal.milestone.MilestoneHelper_2;
/*      */ import com.universal.milestone.MilestoneMessage;
/*      */ import com.universal.milestone.MilestoneSecurity;
/*      */ import com.universal.milestone.Notepad;
/*      */ import com.universal.milestone.SecureHandler;
/*      */ import com.universal.milestone.User;
/*      */ import com.universal.milestone.UserManager;
/*      */ import java.util.HashMap;
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
/*      */ public class DivisionHandler
/*      */   extends SecureHandler
/*      */ {
/*      */   public static final String COMPONENT_CODE = "hDiv";
/*      */   public GeminiApplication application;
/*      */   public ComponentLog log;
/*      */   
/*      */   public DivisionHandler(GeminiApplication application) {
/*   61 */     this.application = application;
/*   62 */     this.log = application.getLog("hDiv");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   70 */   public String getDescription() { return "Division Handler"; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean acceptRequest(Dispatcher dispatcher, Context context, String command) {
/*   80 */     if (super.acceptRequest(dispatcher, context, command))
/*      */     {
/*   82 */       if (command.startsWith("division"))
/*      */       {
/*   84 */         return handleRequest(dispatcher, context, command);
/*      */       }
/*      */     }
/*   87 */     return false;
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
/*      */   public boolean handleRequest(Dispatcher dispatcher, Context context, String command) {
/*   99 */     if (command.equalsIgnoreCase("division-search"))
/*      */     {
/*  101 */       search(dispatcher, context, command);
/*      */     }
/*  103 */     if (command.equalsIgnoreCase("division-sort")) {
/*      */       
/*  105 */       sort(dispatcher, context);
/*      */     }
/*  107 */     else if (command.equalsIgnoreCase("division-editor")) {
/*      */       
/*  109 */       edit(context);
/*      */     }
/*  111 */     else if (command.equalsIgnoreCase("division-edit-save")) {
/*      */       
/*  113 */       save(context);
/*      */     }
/*  115 */     else if (command.equalsIgnoreCase("division-edit-save-new")) {
/*      */       
/*  117 */       saveNew(context);
/*      */     }
/*  119 */     else if (command.equalsIgnoreCase("division-edit-delete")) {
/*      */       
/*  121 */       delete(context);
/*      */     }
/*  123 */     else if (command.equalsIgnoreCase("division-edit-new")) {
/*      */       
/*  125 */       newForm(context);
/*      */     } 
/*  127 */     return true;
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
/*  138 */     Notepad notepad = MilestoneHelper.getNotepadFromSession(11, context);
/*      */ 
/*      */     
/*  141 */     notepad.setAllContents(null);
/*  142 */     notepad.setSelected(null);
/*      */ 
/*      */ 
/*      */     
/*  146 */     String familyDescriptionSearch = "";
/*  147 */     String environmentDescriptionSearch = "";
/*  148 */     String companyDescriptionSearch = "";
/*  149 */     String divisionDescriptionSearch = "";
/*      */     
/*  151 */     if (context.getParameter("FamilyDescriptionSearch") != null) {
/*  152 */       familyDescriptionSearch = context.getParameter("FamilyDescriptionSearch");
/*      */     }
/*  154 */     if (context.getParameter("EnvironmentDescriptionSearch") != null) {
/*  155 */       environmentDescriptionSearch = context.getParameter("EnvironmentDescriptionSearch");
/*      */     }
/*  157 */     if (context.getParameter("CompanyDescriptionSearch") != null) {
/*  158 */       companyDescriptionSearch = context.getParameter("CompanyDescriptionSearch");
/*      */     }
/*  160 */     if (context.getParameter("DivisionDescriptionSearch") != null) {
/*  161 */       divisionDescriptionSearch = context.getParameter("DivisionDescriptionSearch");
/*      */     }
/*      */     
/*  164 */     CorporateObjectSearchObj corpSearch = new CorporateObjectSearchObj();
/*  165 */     corpSearch.setEnvironmentSearch(environmentDescriptionSearch);
/*  166 */     corpSearch.setFamilySearch(familyDescriptionSearch);
/*  167 */     corpSearch.setCompanySearch(companyDescriptionSearch);
/*  168 */     corpSearch.setDivisionSearch(divisionDescriptionSearch);
/*  169 */     notepad.setCorporateObjectSearchObj(corpSearch);
/*      */ 
/*      */     
/*  172 */     DivisionManager.getInstance().setNotepadQuery(notepad);
/*  173 */     dispatcher.redispatch(context, "division-editor");
/*      */     
/*  175 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean sort(Dispatcher dispatcher, Context context) {
/*  185 */     int sort = Integer.parseInt(context.getParameter("OrderBy"));
/*      */     
/*  187 */     Notepad notepad = getDivisionNotepad(context, MilestoneSecurity.getUser(context).getUserId());
/*      */     
/*  189 */     if (notepad.getAllContents() != null) {
/*      */ 
/*      */       
/*  192 */       Vector sortedVector = notepad.getAllContents();
/*      */       
/*  194 */       if (sort == 0) {
/*      */         
/*  196 */         sortedVector = MilestoneHelper.sortCorporateVectorByName(notepad.getAllContents());
/*      */       }
/*      */       else {
/*      */         
/*  200 */         sortedVector = MilestoneHelper.sortCorporateVectorByParentName(notepad.getAllContents());
/*      */       } 
/*      */       
/*  203 */       notepad.setAllContents(sortedVector);
/*      */     } 
/*      */ 
/*      */     
/*  207 */     notepad.goToSelectedPage();
/*  208 */     dispatcher.redispatch(context, "division-editor");
/*      */     
/*  210 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Notepad getDivisionNotepad(Context context, int userId) {
/*  220 */     Vector contents = new Vector();
/*      */     
/*  222 */     if (MilestoneHelper.getNotepadFromSession(11, context) != null) {
/*      */       
/*  224 */       Notepad notepad = MilestoneHelper.getNotepadFromSession(11, context);
/*      */       
/*  226 */       if (notepad.getAllContents() == null) {
/*      */         
/*  228 */         contents = DivisionManager.getInstance().getDivisionNotepadList(userId, notepad);
/*  229 */         notepad.setAllContents(contents);
/*      */       } 
/*      */       
/*  232 */       return notepad;
/*      */     } 
/*      */ 
/*      */     
/*  236 */     String[] columnNames = { "Division", "Company" };
/*  237 */     contents = MilestoneHelper.sortCorporateVectorByName(DivisionManager.getInstance().getDivisionNotepadList(userId, null));
/*  238 */     return new Notepad(contents, 0, 15, "Division", 11, columnNames);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean edit(Context context) {
/*  247 */     User user = MilestoneSecurity.getUser(context);
/*      */     
/*  249 */     Notepad notepad = getDivisionNotepad(context, user.getUserId());
/*  250 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */     
/*  252 */     Division division = MilestoneHelper.getScreenDivision(context);
/*      */     
/*  254 */     if (division != null) {
/*      */       
/*  256 */       Form form = null;
/*  257 */       if (division != null) {
/*      */ 
/*      */         
/*  260 */         form = buildForm(context, division);
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  265 */         context.putDelivery("CorporateArrays", getJavaScriptCorporateArray(context));
/*  266 */         form = buildNewForm(context);
/*      */       } 
/*  268 */       form.addElement(new FormHidden("OrderBy", "", true));
/*  269 */       context.putDelivery("Form", form);
/*  270 */       return context.includeJSP("division-editor.jsp");
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  275 */     return goToBlank(context);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Form buildForm(Context context, Division division) {
/*  282 */     this.log.debug("Build form.");
/*  283 */     Form divisionForm = new Form(this.application, "divisionForm", 
/*  284 */         this.application.getInfrastructure().getServletURL(), "POST");
/*      */ 
/*      */ 
/*      */     
/*  288 */     Environment companyEnvironment = division.getParentCompany().getParentEnvironment();
/*  289 */     Family parentFamily = companyEnvironment.getParentFamily();
/*  290 */     boolean boolVal = MilestoneHelper.getActiveCorporateStructure(division.getStructureID());
/*  291 */     division.setActive(boolVal);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  298 */     Vector families = Cache.getFamilies();
/*  299 */     FormDropDownMenu Parent2Selection = MilestoneHelper.getCorporateStructureDropDown("Parent2Selection", families, Integer.toString(parentFamily.getStructureID()), true, false);
/*  300 */     Parent2Selection.addFormEvent("onChange", "adjustSelection(this)");
/*  301 */     Parent2Selection.setTabIndex(1);
/*  302 */     divisionForm.addElement(Parent2Selection);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  308 */     FormCheckBox active = new FormCheckBox("active", "", true, division.getActive());
/*      */     
/*  310 */     active.setTabIndex(5);
/*  311 */     divisionForm.addElement(active);
/*      */ 
/*      */     
/*  314 */     Vector companies = companyEnvironment.getCompanies();
/*      */     
/*  316 */     StringList companyString = new StringList(",");
/*  317 */     Company company = null;
/*  318 */     for (int j = 0; j < companies.size(); j++) {
/*      */       
/*  320 */       company = (Company)companies.get(j);
/*  321 */       companyString.add(company.getName());
/*      */     } 
/*      */     
/*  324 */     Company parent = division.getParentCompany();
/*      */ 
/*      */ 
/*      */     
/*  328 */     FormDropDownMenu parent1Selection = MilestoneHelper.getCorporateStructureDropDown("Parent1Selection", 
/*  329 */         companies, 
/*  330 */         Integer.toString(parent.getStructureID()), 
/*  331 */         true, false);
/*  332 */     parent1Selection.setTabIndex(2);
/*  333 */     parent1Selection.addFormEvent("onChange", "adjustSelection(this)");
/*      */     
/*  335 */     divisionForm.addElement(parent1Selection);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  340 */     Vector environments = parentFamily.getEnvironments();
/*      */     
/*  342 */     StringList environmentString = new StringList(",");
/*  343 */     Environment environment = null;
/*  344 */     for (int j = 0; j < environments.size(); j++) {
/*      */       
/*  346 */       environment = (Environment)environments.get(j);
/*  347 */       environmentString.add(environment.getName());
/*      */     } 
/*      */     
/*  350 */     Environment parentEnvironment = parent.getParentEnvironment();
/*      */ 
/*      */ 
/*      */     
/*  354 */     FormDropDownMenu parent3Selection = MilestoneHelper.getCorporateStructureDropDown("Parent3Selection", 
/*  355 */         environments, Integer.toString(parentEnvironment.getStructureID()), true, false);
/*      */     
/*  357 */     parent3Selection.setTabIndex(3);
/*      */     
/*  359 */     parent3Selection.addFormEvent("onChange", "adjustSelection(this)");
/*  360 */     divisionForm.addElement(parent3Selection);
/*      */ 
/*      */ 
/*      */     
/*  364 */     FormTextField corporateDescription = new FormTextField("CorporateDescription", division.getName(), true, 50, 50);
/*  365 */     corporateDescription.addFormEvent("onBlur", "Javascript:checkField(this);fillAbbreviation()");
/*  366 */     corporateDescription.setTabIndex(4);
/*  367 */     divisionForm.addElement(corporateDescription);
/*      */ 
/*      */     
/*  370 */     context.putDelivery("isArchiId", String.valueOf(MilestoneHelper_2.getIsStructureArchimedes(division.getStructureID())));
/*      */ 
/*      */     
/*  373 */     FormTextField corporateAbbreviation = new FormTextField("CorporateAbbreviation", division.getStructureAbbreviation(), true, 3, 3);
/*  374 */     corporateAbbreviation.addFormEvent("onBlur", "Javascript:checkField(this)");
/*  375 */     corporateAbbreviation.setTabIndex(5);
/*  376 */     divisionForm.addElement(corporateAbbreviation);
/*      */ 
/*      */     
/*  379 */     Vector labels = division.getLabels();
/*  380 */     StringList labelString = new StringList(",");
/*  381 */     Label label = null;
/*  382 */     for (int j = 0; j < labels.size(); j++) {
/*      */       
/*  384 */       label = (Label)labels.get(j);
/*  385 */       labelString.add(label.getName());
/*      */     } 
/*      */     
/*  388 */     FormDropDownMenu children = new FormDropDownMenu("children", "", labelString.toString(), false);
/*  389 */     children.addFormEvent("style", "background-color:lightgrey;");
/*  390 */     children.addFormEvent("size", "5");
/*  391 */     children.setTabIndex(4);
/*  392 */     divisionForm.addElement(children);
/*      */ 
/*      */     
/*  395 */     FormTextField lastUpdated = new FormTextField("lastUpdatedDate", false, 50);
/*  396 */     if (division.getLastUpdateDate() != null)
/*  397 */       lastUpdated.setValue(MilestoneHelper.getCustomFormatedDate(division.getLastUpdateDate(), "M/d/yyyy hh:mm:ss a 'ET'")); 
/*  398 */     divisionForm.addElement(lastUpdated);
/*      */     
/*  400 */     FormTextField lastUpdatedBy = new FormTextField("lastUpdatedBy", false, 50);
/*  401 */     if (UserManager.getInstance().getUser(division.getLastUpdatingUser()) != null)
/*  402 */       lastUpdatedBy.setValue(UserManager.getInstance().getUser(division.getLastUpdatingUser()).getName()); 
/*  403 */     divisionForm.addElement(lastUpdatedBy);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  408 */     FormTextField familyDescriptionSearch = new FormTextField("FamilyDescriptionSearch", "", false, 20);
/*  409 */     familyDescriptionSearch.setId("FamilyDescriptionSearch");
/*  410 */     divisionForm.addElement(familyDescriptionSearch);
/*      */ 
/*      */     
/*  413 */     FormTextField environmentDescriptionSearch = new FormTextField("EnvironmentDescriptionSearch", "", false, 20);
/*  414 */     environmentDescriptionSearch.setId("EnvironmentDescriptionSearch");
/*  415 */     divisionForm.addElement(environmentDescriptionSearch);
/*      */     
/*  417 */     FormTextField companyDescriptionSearch = new FormTextField("CompanyDescriptionSearch", "", false, 20);
/*  418 */     companyDescriptionSearch.setId("CompanyDescriptionSearch");
/*  419 */     divisionForm.addElement(companyDescriptionSearch);
/*      */     
/*  421 */     FormTextField divisionDescriptionSearch = new FormTextField("DivisionDescriptionSearch", "", false, 20);
/*  422 */     divisionDescriptionSearch.setId("DivisionDescriptionSearch");
/*  423 */     divisionForm.addElement(divisionDescriptionSearch);
/*      */     
/*  425 */     divisionForm.addElement(new FormHidden("cmd", "division-editor"));
/*  426 */     divisionForm.addElement(new FormHidden("OrderBy", "", true));
/*      */ 
/*      */     
/*  429 */     if (context.getSessionValue("NOTEPAD_DIVISION_VISIBLE") != null) {
/*  430 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_DIVISION_VISIBLE"));
/*      */     }
/*      */     
/*  433 */     context.putDelivery("CorporateArraysNew", getJavaScriptCorporateArrayNew(context));
/*  434 */     context.putDelivery("CorporateArrays", getJavaScriptCorporateArray(context));
/*      */     
/*  436 */     return divisionForm;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean save(Context context) {
/*  444 */     User user = MilestoneSecurity.getUser(context);
/*      */     
/*  446 */     Notepad notepad = getDivisionNotepad(context, user.getUserId());
/*  447 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */     
/*  449 */     Division division = MilestoneHelper.getScreenDivision(context);
/*      */     
/*  451 */     Form form = buildForm(context, division);
/*      */     
/*  453 */     Division timestampDivision = (Division)context.getSessionValue("Division");
/*  454 */     if (timestampDivision == null || (timestampDivision != null && DivisionManager.getInstance().isTimestampValid(timestampDivision))) {
/*      */       
/*  456 */       form.setValues(context);
/*  457 */       String descriptionString = form.getStringValue("CorporateDescription");
/*      */ 
/*      */       
/*  460 */       String parentString = form.getStringValue("Parent1Selection");
/*      */ 
/*      */       
/*  463 */       String abbreviationString = form.getStringValue("CorporateAbbreviation");
/*      */       
/*  465 */       if (!CorporateStructureManager.getInstance().isDuplicate(descriptionString, 3, division.getStructureID())) {
/*      */ 
/*      */ 
/*      */         
/*  469 */         division.setName(descriptionString);
/*  470 */         division.setStructureAbbreviation(abbreviationString);
/*  471 */         division.setActive(((FormCheckBox)form.getElement("active")).isChecked());
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  476 */         if (!parentString.equalsIgnoreCase("")) {
/*  477 */           division.setParentCompany((Company)MilestoneHelper.getStructureObject(Integer.parseInt(parentString)));
/*      */         }
/*      */ 
/*      */         
/*  481 */         if (!form.isUnchanged()) {
/*      */           
/*  483 */           FormValidation formValidation = form.validate();
/*  484 */           if (formValidation.isGood()) {
/*      */ 
/*      */             
/*  487 */             Division savedDivision = DivisionManager.getInstance().saveDivision(division, user.getUserId());
/*      */ 
/*      */             
/*  490 */             FormElement lastUpdated = form.getElement("lastupdateddate");
/*  491 */             if (savedDivision.getLastUpdateDate() != null) {
/*  492 */               lastUpdated.setValue(MilestoneHelper.getLongDate(savedDivision.getLastUpdateDate()));
/*      */             }
/*      */             
/*  495 */             Cache.flushCorporateStructure();
/*      */             
/*  497 */             context.removeSessionValue("user-companies");
/*  498 */             context.removeSessionValue("user-environments");
/*      */             
/*  500 */             notepad.setAllContents(null);
/*  501 */             notepad = getDivisionNotepad(context, user.getUserId());
/*  502 */             notepad.setSelected(savedDivision);
/*  503 */             division = (Division)notepad.validateSelected();
/*      */             
/*  505 */             if (division == null) {
/*  506 */               return goToBlank(context);
/*      */             }
/*  508 */             form = buildForm(context, division);
/*      */             
/*  510 */             context.putSessionValue("Division", division);
/*  511 */             context.putDelivery("Form", form);
/*      */           }
/*      */           else {
/*      */             
/*  515 */             context.putDelivery("FormValidation", formValidation);
/*      */           } 
/*      */         } 
/*  518 */         form.addElement(new FormHidden("OrderBy", "", true));
/*  519 */         context.putDelivery("Form", form);
/*  520 */         return edit(context);
/*      */       } 
/*      */ 
/*      */       
/*  524 */       context.putSessionValue("Division", division);
/*  525 */       context.putDelivery("AlertMessage", 
/*  526 */           MilestoneMessage.getMessage(5, 
/*      */             
/*  528 */             new String[] { "Environment", descriptionString }));
/*  529 */       return edit(context);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  534 */     context.putDelivery("AlertMessage", "The record is unavailable for editing at this time.  Please refresh your screen and edit it at a later time.");
/*  535 */     return edit(context);
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
/*      */   private boolean delete(Context context) {
/*  550 */     User user = MilestoneSecurity.getUser(context);
/*  551 */     Notepad notepad = getDivisionNotepad(context, user.getUserId());
/*  552 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */     
/*  554 */     Division division = MilestoneHelper.getScreenDivision(context);
/*      */ 
/*      */ 
/*      */     
/*  558 */     if (division != null) {
/*      */       
/*  560 */       String errorMsg = "";
/*  561 */       errorMsg = CorporateStructureManager.getInstance().delete(division, user.getUserId());
/*  562 */       if (errorMsg != null) {
/*      */         
/*  564 */         context.putDelivery("AlertMessage", errorMsg);
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  569 */         Cache.flushCorporateStructure();
/*      */         
/*  571 */         context.removeSessionValue("user-companies");
/*  572 */         context.removeSessionValue("user-environments");
/*      */         
/*  574 */         notepad.setAllContents(null);
/*  575 */         notepad = getDivisionNotepad(context, user.getUserId());
/*  576 */         notepad.setSelected(null);
/*      */       } 
/*      */     } 
/*      */     
/*  580 */     return edit(context);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean newForm(Context context) {
/*  588 */     User user = MilestoneSecurity.getUser(context);
/*      */     
/*  590 */     Notepad notepad = getDivisionNotepad(context, user.getUserId());
/*  591 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */ 
/*      */     
/*  594 */     context.putDelivery("CorporateArrays", getJavaScriptCorporateArray(context));
/*      */     
/*  596 */     Form form = buildNewForm(context);
/*  597 */     context.putDelivery("Form", form);
/*      */ 
/*      */     
/*  600 */     if (context.getSessionValue("NOTEPAD_DIVISION_VISIBLE") != null) {
/*  601 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_DIVISION_VISIBLE"));
/*      */     }
/*  603 */     return context.includeJSP("division-editor.jsp");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Form buildNewForm(Context context) {
/*  613 */     Form divisionForm = new Form(this.application, "divisionForm", 
/*  614 */         this.application.getInfrastructure().getServletURL(), "POST");
/*  615 */     User user = MilestoneSecurity.getUser(context);
/*      */ 
/*      */     
/*  618 */     Vector families = Cache.getFamilies();
/*  619 */     FormDropDownMenu Parent2Selection = MilestoneHelper.getCorporateStructureDropDown("Parent2Selection", families, "0", true, false);
/*  620 */     Parent2Selection.addFormEvent("onChange", "adjustSelection(this)");
/*  621 */     Parent2Selection.setTabIndex(1);
/*  622 */     divisionForm.addElement(Parent2Selection);
/*      */ 
/*      */ 
/*      */     
/*  626 */     Vector environments = Cache.getEnvironments();
/*  627 */     FormDropDownMenu Parent3Selection = MilestoneHelper.getCorporateStructureDropDown("Parent3Selection", environments, "0", true, true);
/*  628 */     Parent3Selection.addFormEvent("onChange", "adjustSelection(this)");
/*  629 */     Parent3Selection.setTabIndex(2);
/*  630 */     divisionForm.addElement(Parent3Selection);
/*      */ 
/*      */     
/*  633 */     FormCheckBox active = new FormCheckBox("active", "", true, true);
/*  634 */     active.setTabIndex(5);
/*  635 */     divisionForm.addElement(active);
/*      */ 
/*      */     
/*  638 */     Vector companies = Cache.getCompanies();
/*  639 */     FormDropDownMenu Parent1Selection = MilestoneHelper.getCorporateStructureDropDown("Parent1Selection", companies, "0", true, true);
/*  640 */     Parent1Selection.addFormEvent("onChange", "adjustSelection(this)");
/*  641 */     Parent1Selection.setTabIndex(2);
/*  642 */     divisionForm.addElement(Parent1Selection);
/*      */ 
/*      */     
/*  645 */     FormTextField corporateDescription = new FormTextField("CorporateDescription", "", true, 50, 50);
/*  646 */     corporateDescription.addFormEvent("onBlur", "checkField(this);fillAbbreviation()");
/*  647 */     corporateDescription.setTabIndex(3);
/*  648 */     divisionForm.addElement(corporateDescription);
/*      */ 
/*      */     
/*  651 */     FormTextField corporateAbbreviation = new FormTextField("CorporateAbbreviation", "", true, 3, 3);
/*  652 */     corporateAbbreviation.addFormEvent("onBlur", "checkField(this)");
/*  653 */     corporateAbbreviation.setTabIndex(4);
/*  654 */     divisionForm.addElement(corporateAbbreviation);
/*      */ 
/*      */ 
/*      */     
/*  658 */     FormTextField familyDescriptionSearch = new FormTextField("FamilyDescriptionSearch", "", false, 20);
/*  659 */     familyDescriptionSearch.setId("FamilyDescriptionSearch");
/*  660 */     divisionForm.addElement(familyDescriptionSearch);
/*      */ 
/*      */     
/*  663 */     FormTextField environmentDescriptionSearch = new FormTextField("EnvironmentDescriptionSearch", "", false, 20);
/*  664 */     environmentDescriptionSearch.setId("EnvironmentDescriptionSearch");
/*  665 */     divisionForm.addElement(environmentDescriptionSearch);
/*      */     
/*  667 */     FormTextField companyDescriptionSearch = new FormTextField("CompanyDescriptionSearch", "", false, 20);
/*  668 */     companyDescriptionSearch.setId("CompanyDescriptionSearch");
/*  669 */     divisionForm.addElement(companyDescriptionSearch);
/*      */     
/*  671 */     FormTextField divisionDescriptionSearch = new FormTextField("DivisionDescriptionSearch", "", false, 20);
/*  672 */     divisionDescriptionSearch.setId("DivisionDescriptionSearch");
/*  673 */     divisionForm.addElement(divisionDescriptionSearch);
/*      */     
/*  675 */     divisionForm.addElement(new FormHidden("cmd", "division-edit-new", true));
/*  676 */     divisionForm.addElement(new FormHidden("OrderBy", "", true));
/*      */ 
/*      */     
/*  679 */     if (context.getSessionValue("NOTEPAD_DIVISION_VISIBLE") != null) {
/*  680 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_DIVISION_VISIBLE"));
/*      */     }
/*  682 */     return divisionForm;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean saveNew(Context context) {
/*  688 */     User user = MilestoneSecurity.getUser(context);
/*      */     
/*  690 */     Notepad notepad = getDivisionNotepad(context, user.getUserId());
/*  691 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */     
/*  693 */     Division division = new Division();
/*      */     
/*  695 */     Form form = buildNewForm(context);
/*  696 */     form.setValues(context);
/*      */ 
/*      */     
/*  699 */     String parentString = form.getStringValue("Parent1Selection");
/*  700 */     if (!parentString.equalsIgnoreCase("")) {
/*  701 */       division.setParentCompany((Company)MilestoneHelper.getStructureObject(Integer.parseInt(parentString)));
/*      */     }
/*      */ 
/*      */     
/*  705 */     String descriptionString = form.getStringValue("CorporateDescription");
/*  706 */     division.setName(descriptionString);
/*  707 */     division.setStructureType(3);
/*      */ 
/*      */     
/*  710 */     String abbreviationString = form.getStringValue("CorporateAbbreviation");
/*  711 */     division.setStructureAbbreviation(abbreviationString);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  716 */     division.setStructureID(-2);
/*      */ 
/*      */     
/*  719 */     division.setActive(((FormCheckBox)form.getElement("active")).isChecked());
/*      */ 
/*      */     
/*  722 */     if (!CorporateStructureManager.getInstance().isDuplicate(descriptionString, 3, division.getStructureID())) {
/*      */       
/*  724 */       if (!form.isUnchanged()) {
/*      */         
/*  726 */         FormValidation formValidation = form.validate();
/*  727 */         if (formValidation.isGood())
/*      */         {
/*  729 */           Division saveNewDivision = DivisionManager.getInstance().saveNewDivision(division, user.getUserId());
/*      */ 
/*      */           
/*  732 */           if (notepad.getAllContents() == null || notepad.getAllContents().size() < 1) {
/*  733 */             notepad.setCorporateObjectSearchObj(null);
/*      */           }
/*      */           
/*  736 */           Cache.flushCorporateStructure();
/*      */           
/*  738 */           context.removeSessionValue("user-companies");
/*  739 */           context.removeSessionValue("user-environments");
/*      */           
/*  741 */           notepad.setAllContents(null);
/*  742 */           notepad.newSelectedReset();
/*  743 */           notepad = getDivisionNotepad(context, user.getUserId());
/*  744 */           notepad.setSelected(saveNewDivision);
/*      */           
/*  746 */           context.putSessionValue("Division", saveNewDivision);
/*      */         }
/*      */         else
/*      */         {
/*  750 */           context.putDelivery("FormValidation", formValidation);
/*  751 */           form.addElement(new FormHidden("OrderBy", "", true));
/*  752 */           context.putDelivery("Form", form);
/*      */           
/*  754 */           context.putDelivery("CorporateArrays", getJavaScriptCorporateArray(context));
/*  755 */           return context.includeJSP("division-editor.jsp");
/*      */         }
/*      */       
/*      */       } 
/*      */     } else {
/*      */       
/*  761 */       context.putSessionValue("Division", division);
/*  762 */       context.putDelivery("Form", form);
/*  763 */       context.putDelivery("AlertMessage", 
/*  764 */           MilestoneMessage.getMessage(5, 
/*      */             
/*  766 */             new String[] { "Division", division.getName() }));
/*      */     } 
/*  768 */     return edit(context);
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
/*      */   private String getJavaScriptCorporateArray(Context context) {
/*  780 */     Vector families = Cache.getFamilies();
/*      */     
/*  782 */     StringBuffer buffer = new StringBuffer("function createChildren()\n{\n  lRoot = ");
/*  783 */     buffer.append("new Node(0, 'Root', [\n");
/*      */     
/*  785 */     for (int i = 0; i < families.size(); i++) {
/*      */       
/*  787 */       Family family = (Family)families.elementAt(i);
/*  788 */       if (family != null) {
/*      */ 
/*      */         
/*  791 */         buffer.append("new Node(");
/*  792 */         buffer.append(family.getStructureID());
/*  793 */         buffer.append(", '");
/*  794 */         buffer.append(MilestoneHelper.urlEncode(family.getName()));
/*  795 */         buffer.append("',[\n");
/*      */         
/*  797 */         Vector environments = family.getEnvironments();
/*      */         
/*  799 */         for (int e = 0; e < environments.size(); e++) {
/*      */           
/*  801 */           Environment environment = (Environment)environments.elementAt(e);
/*  802 */           if (environment != null) {
/*      */ 
/*      */             
/*  805 */             buffer.append("new Node(");
/*  806 */             buffer.append(environment.getStructureID());
/*  807 */             buffer.append(", '");
/*  808 */             buffer.append(MilestoneHelper.urlEncode(environment.getName()));
/*  809 */             buffer.append("',[\n");
/*      */ 
/*      */             
/*  812 */             Vector companies = environment.getCompanies();
/*  813 */             for (int j = 0; j < companies.size(); j++) {
/*      */               
/*  815 */               Company company = (Company)companies.elementAt(j);
/*  816 */               if (company != null) {
/*      */                 
/*  818 */                 buffer.append("new Node(");
/*  819 */                 buffer.append(company.getStructureID());
/*  820 */                 buffer.append(",'");
/*  821 */                 buffer.append(MilestoneHelper.urlEncode(company.getName()));
/*  822 */                 buffer.append("',[\n");
/*      */ 
/*      */                 
/*  825 */                 if (j == companies.size() - 1) {
/*      */ 
/*      */                   
/*  828 */                   buffer.append("])");
/*      */                 
/*      */                 }
/*      */                 else {
/*      */                   
/*  833 */                   buffer.append("]),");
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */ 
/*      */ 
/*      */             
/*  840 */             if (e == environments.size() - 1) {
/*      */ 
/*      */               
/*  843 */               buffer.append("])");
/*      */             
/*      */             }
/*      */             else {
/*      */               
/*  848 */               buffer.append("]),");
/*      */             } 
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/*  854 */         if (i == families.size() - 1) {
/*      */ 
/*      */           
/*  857 */           buffer.append("])");
/*      */         
/*      */         }
/*      */         else {
/*      */           
/*  862 */           buffer.append("]),");
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  870 */     buffer.append("]);\n");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  875 */     Vector allEnvironments = Cache.getEnvironments();
/*  876 */     buffer.append("\n  eRoot = ");
/*  877 */     buffer.append("new Node(0, 'Root', [\n");
/*      */     
/*  879 */     for (int x = 0; x < allEnvironments.size(); x++) {
/*      */       
/*  881 */       Environment thisEnvironment = (Environment)allEnvironments.elementAt(x);
/*  882 */       if (thisEnvironment != null) {
/*      */ 
/*      */         
/*  885 */         buffer.append("new Node(");
/*  886 */         buffer.append(thisEnvironment.getStructureID());
/*  887 */         buffer.append(", '");
/*  888 */         buffer.append(MilestoneHelper.urlEncode(thisEnvironment.getName()));
/*  889 */         buffer.append("',[\n");
/*      */ 
/*      */         
/*  892 */         Vector allCompanies = thisEnvironment.getCompanies();
/*  893 */         for (int z = 0; z < allCompanies.size(); z++) {
/*      */           
/*  895 */           Company thisCompany = (Company)allCompanies.elementAt(z);
/*  896 */           if (thisCompany != null) {
/*      */             
/*  898 */             buffer.append("new Node(");
/*  899 */             buffer.append(thisCompany.getStructureID());
/*  900 */             buffer.append(",'");
/*  901 */             buffer.append(MilestoneHelper.urlEncode(thisCompany.getName()));
/*  902 */             buffer.append("',[\n");
/*      */ 
/*      */             
/*  905 */             if (z == allCompanies.size() - 1) {
/*      */ 
/*      */               
/*  908 */               buffer.append("])");
/*      */             
/*      */             }
/*      */             else {
/*      */               
/*  913 */               buffer.append("]),");
/*      */             } 
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  920 */         if (x == allEnvironments.size() - 1) {
/*      */ 
/*      */           
/*  923 */           buffer.append("])");
/*      */         
/*      */         }
/*      */         else {
/*      */           
/*  928 */           buffer.append("]),");
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  934 */     buffer.append("]);\n");
/*      */     
/*  936 */     buffer.append("\n }//end function createChildren");
/*      */     
/*  938 */     return buffer.toString();
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
/*      */   public String getJavaScriptCorporateArrayNew(Context context) {
/*  950 */     StringBuffer result = new StringBuffer(100);
/*  951 */     String str = "";
/*  952 */     String value = new String();
/*      */     
/*  954 */     User user = (User)context.getSessionValue("user");
/*  955 */     Vector vUserCompanies = (Vector)MilestoneHelper.getUserCompanies(context).clone();
/*      */ 
/*      */     
/*  958 */     Vector vUserEnvironments = Cache.getEnvironments();
/*      */ 
/*      */     
/*  961 */     result.append("\n");
/*  962 */     result.append("var a = new Array();\n");
/*  963 */     result.append("var b = new Array();\n");
/*  964 */     result.append("var c = new Array();\n");
/*  965 */     int arrayIndex = 0;
/*      */ 
/*      */     
/*  968 */     result.append("a[0] = new Array( 0, '-- [nothing selected] --');\n");
/*      */ 
/*      */     
/*  971 */     HashMap corpHashMap = MilestoneHelper.buildActiveCorporateStructureHashMap();
/*      */ 
/*      */ 
/*      */     
/*  975 */     for (int i = 0; i < vUserEnvironments.size(); i++) {
/*      */       
/*  977 */       Environment ue = (Environment)vUserEnvironments.elementAt(i);
/*  978 */       if (ue != null) {
/*      */         
/*  980 */         result.append("a[");
/*  981 */         result.append(ue.getStructureID());
/*  982 */         result.append("] = new Array(");
/*      */         
/*  984 */         boolean foundFirst = false;
/*  985 */         Vector tmpArray = new Vector();
/*      */         
/*  987 */         Vector companies = Cache.getInstance().getCompanies();
/*  988 */         for (int j = 0; j < companies.size(); j++) {
/*      */           
/*  990 */           Company node = (Company)companies.elementAt(j);
/*      */           
/*  992 */           if (node.getParentID() == ue.getStructureID() && !corpHashMap.containsKey(new Integer(node.getStructureID()))) {
/*      */             
/*  994 */             if (foundFirst)
/*  995 */               result.append(','); 
/*  996 */             result.append(' ');
/*  997 */             result.append(node.getStructureID());
/*  998 */             result.append(", '");
/*  999 */             result.append(MilestoneHelper.urlEncode(node.getName()));
/* 1000 */             result.append('\'');
/* 1001 */             foundFirst = true;
/* 1002 */             tmpArray.addElement(node);
/*      */           } 
/*      */         } 
/*      */         
/* 1006 */         if (foundFirst) {
/*      */           
/* 1008 */           result.append(");\n");
/*      */         }
/*      */         else {
/*      */           
/* 1012 */           result.append(" 0, '[none available]');\n");
/*      */         } 
/*      */         
/* 1015 */         Vector tmpDivisionArray = new Vector();
/*      */         
/* 1017 */         for (int j = 0; j < tmpArray.size(); j++) {
/*      */           
/* 1019 */           Company node1 = (Company)tmpArray.elementAt(j);
/* 1020 */           result.append("b[");
/* 1021 */           result.append(node1.getStructureID());
/* 1022 */           result.append("] = new Array(");
/*      */           
/* 1024 */           Vector divisions = Cache.getInstance().getDivisions();
/*      */           
/* 1026 */           boolean foundSecond = false;
/* 1027 */           for (int k = 0; k < divisions.size(); k++) {
/*      */             
/* 1029 */             Division node2 = (Division)divisions.elementAt(k);
/*      */             
/* 1031 */             if (node2.getParentID() == node1.getStructureID() && !corpHashMap.containsKey(new Integer(node2.getStructureID()))) {
/*      */               
/* 1033 */               if (foundSecond)
/* 1034 */                 result.append(','); 
/* 1035 */               result.append(' ');
/* 1036 */               result.append(node2.getStructureID());
/* 1037 */               result.append(", '");
/*      */               
/* 1039 */               result.append(MilestoneHelper.urlEncode(node2.getName()));
/* 1040 */               result.append('\'');
/* 1041 */               foundSecond = true;
/* 1042 */               tmpDivisionArray.add(node2);
/*      */             } 
/*      */           } 
/*      */           
/* 1046 */           if (foundSecond) {
/*      */             
/* 1048 */             result.append(");\n");
/*      */           }
/*      */           else {
/*      */             
/* 1052 */             result.append(" 0, '[none available]');\n");
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/* 1057 */         for (int j = 0; j < tmpDivisionArray.size(); j++) {
/*      */           
/* 1059 */           Division node1 = (Division)tmpDivisionArray.elementAt(j);
/* 1060 */           result.append("c[");
/* 1061 */           result.append(node1.getStructureID());
/* 1062 */           result.append("] = new Array(");
/*      */           
/* 1064 */           Vector labels = Cache.getInstance().getLabels();
/*      */           
/* 1066 */           boolean foundSecond = false;
/* 1067 */           for (int k = 0; k < labels.size(); k++) {
/*      */             
/* 1069 */             Label node2 = (Label)labels.elementAt(k);
/*      */ 
/*      */ 
/*      */             
/* 1073 */             if (node2.getParentID() == node1.getStructureID() && !corpHashMap.containsKey(new Integer(node2.getStructureID()))) {
/*      */               
/* 1075 */               if (foundSecond)
/* 1076 */                 result.append(','); 
/* 1077 */               result.append(' ');
/* 1078 */               result.append(node2.getStructureID());
/* 1079 */               result.append(", '");
/*      */               
/* 1081 */               result.append(MilestoneHelper.urlEncode(node2.getName()));
/* 1082 */               result.append('\'');
/* 1083 */               foundSecond = true;
/*      */             } 
/*      */           } 
/*      */           
/* 1087 */           if (foundSecond) {
/*      */             
/* 1089 */             result.append(");\n");
/*      */           }
/*      */           else {
/*      */             
/* 1093 */             result.append(" 0, '[none available]');\n");
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/* 1098 */     corpHashMap = null;
/* 1099 */     return result.toString();
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
/*      */   private boolean goToBlank(Context context) {
/* 1114 */     context.putDelivery("isNotePadVisible", new Boolean(MilestoneHelper.getNotePadVisiblitiy(11, context)));
/*      */     
/* 1116 */     Form form = new Form(this.application, "form", this.application.getInfrastructure().getServletURL(), "Post");
/* 1117 */     form.addElement(new FormHidden("cmd", "division-editor"));
/* 1118 */     form.addElement(new FormHidden("OrderBy", "", true));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1123 */     FormTextField familyDescriptionSearch = new FormTextField("FamilyDescriptionSearch", "", true, 20);
/* 1124 */     familyDescriptionSearch.setId("FamilyDescriptionSearch");
/* 1125 */     form.addElement(familyDescriptionSearch);
/*      */ 
/*      */     
/* 1128 */     FormTextField environmentDescriptionSearch = new FormTextField("EnvironmentDescriptionSearch", "", true, 20);
/* 1129 */     environmentDescriptionSearch.setId("EnvironmentDescriptionSearch");
/* 1130 */     form.addElement(environmentDescriptionSearch);
/*      */     
/* 1132 */     FormTextField companyDescriptionSearch = new FormTextField("CompanyDescriptionSearch", "", true, 20);
/* 1133 */     companyDescriptionSearch.setId("CompanyDescriptionSearch");
/* 1134 */     form.addElement(companyDescriptionSearch);
/*      */     
/* 1136 */     FormTextField divisionDescriptionSearch = new FormTextField("DivisionDescriptionSearch", "", true, 20);
/* 1137 */     divisionDescriptionSearch.setId("DivisionDescriptionSearch");
/* 1138 */     form.addElement(divisionDescriptionSearch);
/*      */     
/* 1140 */     context.putDelivery("Form", form);
/*      */     
/* 1142 */     return context.includeJSP("blank-division-editor.jsp");
/*      */   }
/*      */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\DivisionHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */