/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.ComponentLog;
/*     */ import com.techempower.StringList;
/*     */ import com.techempower.gemini.Context;
/*     */ import com.techempower.gemini.Dispatcher;
/*     */ import com.techempower.gemini.FormCheckBox;
/*     */ import com.techempower.gemini.FormDropDownMenu;
/*     */ import com.techempower.gemini.FormElement;
/*     */ import com.techempower.gemini.FormHidden;
/*     */ import com.techempower.gemini.FormTextField;
/*     */ import com.techempower.gemini.FormValidation;
/*     */ import com.techempower.gemini.GeminiApplication;
/*     */ import com.universal.milestone.Cache;
/*     */ import com.universal.milestone.Company;
/*     */ import com.universal.milestone.CompanyHandler;
/*     */ import com.universal.milestone.CompanyManager;
/*     */ import com.universal.milestone.CorporateObjectSearchObj;
/*     */ import com.universal.milestone.CorporateStructureManager;
/*     */ import com.universal.milestone.Division;
/*     */ import com.universal.milestone.Environment;
/*     */ import com.universal.milestone.Family;
/*     */ import com.universal.milestone.Form;
/*     */ import com.universal.milestone.MilestoneHelper;
/*     */ import com.universal.milestone.MilestoneHelper_2;
/*     */ import com.universal.milestone.MilestoneMessage;
/*     */ import com.universal.milestone.MilestoneSecurity;
/*     */ import com.universal.milestone.Notepad;
/*     */ import com.universal.milestone.SecureHandler;
/*     */ import com.universal.milestone.User;
/*     */ import com.universal.milestone.UserManager;
/*     */ import java.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CompanyHandler
/*     */   extends SecureHandler
/*     */ {
/*     */   public static final String COMPONENT_CODE = "hCom";
/*     */   public GeminiApplication application;
/*     */   public ComponentLog log;
/*     */   
/*     */   public CompanyHandler(GeminiApplication application) {
/*  60 */     this.application = application;
/*  61 */     this.log = application.getLog("hCom");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  69 */   public String getDescription() { return "Company Handler"; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean acceptRequest(Dispatcher dispatcher, Context context, String command) {
/*  79 */     if (super.acceptRequest(dispatcher, context, command))
/*     */     {
/*  81 */       if (command.startsWith("company"))
/*     */       {
/*  83 */         return handleRequest(dispatcher, context, command);
/*     */       }
/*     */     }
/*  86 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean handleRequest(Dispatcher dispatcher, Context context, String command) {
/*  98 */     if (command.equalsIgnoreCase("company-search"))
/*     */     {
/* 100 */       search(dispatcher, context, command);
/*     */     }
/* 102 */     if (command.equalsIgnoreCase("company-sort")) {
/*     */       
/* 104 */       sort(dispatcher, context);
/*     */     }
/* 106 */     else if (command.equalsIgnoreCase("company-editor")) {
/*     */       
/* 108 */       edit(context);
/*     */     }
/* 110 */     else if (command.equalsIgnoreCase("company-edit-save")) {
/*     */       
/* 112 */       save(context);
/*     */     }
/* 114 */     else if (command.equalsIgnoreCase("company-edit-save-new")) {
/*     */       
/* 116 */       saveNew(context);
/*     */     }
/* 118 */     else if (command.equalsIgnoreCase("company-edit-delete")) {
/*     */       
/* 120 */       delete(context);
/*     */     }
/* 122 */     else if (command.equalsIgnoreCase("company-edit-new")) {
/*     */       
/* 124 */       newForm(context);
/*     */     } 
/* 126 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean search(Dispatcher dispatcher, Context context, String command) {
/* 137 */     Notepad notepad = MilestoneHelper.getNotepadFromSession(10, context);
/*     */ 
/*     */     
/* 140 */     notepad.setAllContents(null);
/* 141 */     notepad.setSelected(null);
/*     */ 
/*     */     
/* 144 */     String familyDescriptionSearch = "";
/* 145 */     String environmentDescriptionSearch = "";
/* 146 */     String companyDescriptionSearch = "";
/*     */ 
/*     */ 
/*     */     
/* 150 */     if (context.getParameter("FamilyDescriptionSearch") != null) {
/* 151 */       familyDescriptionSearch = context.getParameter("FamilyDescriptionSearch");
/*     */     }
/* 153 */     if (context.getParameter("EnvironmentDescriptionSearch") != null) {
/* 154 */       environmentDescriptionSearch = context.getParameter("EnvironmentDescriptionSearch");
/*     */     }
/* 156 */     if (context.getParameter("CompanyDescriptionSearch") != null) {
/* 157 */       companyDescriptionSearch = context.getParameter("CompanyDescriptionSearch");
/*     */     }
/*     */     
/* 160 */     CorporateObjectSearchObj corpSearch = new CorporateObjectSearchObj();
/*     */ 
/*     */     
/* 163 */     corpSearch.setFamilySearch(familyDescriptionSearch);
/* 164 */     corpSearch.setEnvironmentSearch(environmentDescriptionSearch);
/* 165 */     corpSearch.setCompanySearch(companyDescriptionSearch);
/*     */ 
/*     */     
/* 168 */     notepad.setCorporateObjectSearchObj(corpSearch);
/*     */     
/* 170 */     CompanyManager.getInstance().setNotepadQuery(notepad);
/* 171 */     dispatcher.redispatch(context, "company-editor");
/*     */     
/* 173 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean sort(Dispatcher dispatcher, Context context) {
/* 182 */     int sort = context.getIntRequestValue("OrderBy", 0);
/*     */     
/* 184 */     Notepad notepad = getCompanyNotepad(context, MilestoneSecurity.getUser(context).getUserId());
/*     */     
/* 186 */     if (notepad.getAllContents() != null) {
/*     */ 
/*     */       
/* 189 */       Vector sortedVector = notepad.getAllContents();
/*     */       
/* 191 */       if (sort == 0) {
/*     */         
/* 193 */         sortedVector = MilestoneHelper.sortCorporateVectorByName(notepad.getAllContents());
/*     */       }
/*     */       else {
/*     */         
/* 197 */         sortedVector = MilestoneHelper.sortCorporateVectorByParentName(notepad.getAllContents());
/*     */       } 
/*     */       
/* 200 */       notepad.setAllContents(sortedVector);
/*     */     } 
/*     */ 
/*     */     
/* 204 */     notepad.goToSelectedPage();
/* 205 */     dispatcher.redispatch(context, "company-editor");
/*     */     
/* 207 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Notepad getCompanyNotepad(Context context, int userId) {
/* 216 */     Vector contents = new Vector();
/*     */     
/* 218 */     if (MilestoneHelper.getNotepadFromSession(10, context) != null) {
/*     */       
/* 220 */       Notepad notepad = MilestoneHelper.getNotepadFromSession(10, context);
/*     */       
/* 222 */       if (notepad.getAllContents() == null) {
/*     */         
/* 224 */         contents = CompanyManager.getInstance().getCompanyNotepadList(userId, notepad);
/* 225 */         notepad.setAllContents(contents);
/*     */       } 
/* 227 */       return notepad;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 233 */     String[] columnNames = { "Company", "Environment" };
/* 234 */     contents = MilestoneHelper.sortCorporateVectorByName(CompanyManager.getInstance().getCompanyNotepadList(userId, null));
/* 235 */     return new Notepad(contents, 0, 15, "Company", 10, columnNames);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean edit(Context context) {
/* 244 */     User user = MilestoneSecurity.getUser(context);
/* 245 */     Notepad notepad = getCompanyNotepad(context, user.getUserId());
/* 246 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*     */     
/* 248 */     Company company = MilestoneHelper.getScreenCompany(context);
/*     */     
/* 250 */     if (company != null) {
/*     */       
/* 252 */       Form form = null;
/* 253 */       if (company != null) {
/* 254 */         form = buildForm(context, company);
/*     */       } else {
/* 256 */         form = buildNewForm(context);
/*     */       } 
/* 258 */       context.putDelivery("Form", form);
/* 259 */       return context.includeJSP("company-editor.jsp");
/*     */     } 
/*     */ 
/*     */     
/* 263 */     return goToBlank(context);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Form buildForm(Context context, Company company) {
/* 273 */     Form companyForm = new Form(this.application, "companyForm", 
/* 274 */         this.application.getInfrastructure().getServletURL(), "POST");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 299 */     Environment environment = null;
/* 300 */     Family family = null;
/* 301 */     String familyString = "";
/* 302 */     environment = (Environment)MilestoneHelper.getStructureObject(company.getParentEnvironment().getStructureID());
/* 303 */     family = (Family)MilestoneHelper.getStructureObject(environment.getParentFamily().getStructureID());
/* 304 */     familyString = family.getName();
/* 305 */     FormTextField familyTB = new FormTextField("Family", familyString, true, 50, 25);
/* 306 */     companyForm.addElement(familyTB);
/*     */ 
/*     */ 
/*     */     
/* 310 */     Environment parent = company.getParentEnvironment();
/* 311 */     boolean boolVal = MilestoneHelper.getActiveCorporateStructure(company.getStructureID());
/* 312 */     company.setActive(boolVal);
/* 313 */     Vector environments = Cache.getEnvironments();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 326 */     String[] environmentArray = { "" };
/* 327 */     if (environments.size() > 0) {
/*     */       
/* 329 */       environmentArray = new String[environments.size()];
/* 330 */       for (int j = 0; j < environments.size(); j++) {
/*     */         
/* 332 */         environment = (Environment)environments.get(j);
/* 333 */         environmentArray[j] = environment.getName();
/*     */       } 
/*     */     } 
/* 336 */     FormDropDownMenu parent1Selection = new FormDropDownMenu("Parent1Selection", 
/* 337 */         parent.getName(), environmentArray, true);
/*     */     
/* 339 */     parent1Selection.setTabIndex(1);
/* 340 */     companyForm.addElement(parent1Selection);
/*     */ 
/*     */     
/* 343 */     FormCheckBox active = new FormCheckBox("active", "", true, company.getActive());
/* 344 */     active.setTabIndex(6);
/* 345 */     companyForm.addElement(active);
/*     */ 
/*     */     
/* 348 */     String name = company.getName();
/* 349 */     FormTextField corporateDescription = new FormTextField("CorporateDescription", name, true, 50, 50);
/* 350 */     corporateDescription.addFormEvent("onBlur", "Javascript:checkField(this);fillAbbreviation()");
/* 351 */     corporateDescription.setTabIndex(2);
/* 352 */     companyForm.addElement(corporateDescription);
/*     */ 
/*     */     
/* 355 */     context.putDelivery("isArchiId", String.valueOf(MilestoneHelper_2.getIsStructureArchimedes(company.getStructureID())));
/*     */ 
/*     */     
/* 358 */     String abbreviation = company.getStructureAbbreviation();
/* 359 */     FormTextField corporateAbbreviation = new FormTextField("CorporateAbbreviation", abbreviation, true, 3, 3);
/* 360 */     corporateAbbreviation.addFormEvent("onBlur", "Javascript:checkField(this)");
/* 361 */     corporateAbbreviation.setTabIndex(3);
/* 362 */     companyForm.addElement(corporateAbbreviation);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 385 */     Vector divisions = company.getDivisions();
/* 386 */     StringList divisionString = new StringList();
/* 387 */     Division division = null;
/* 388 */     for (int j = 0; j < divisions.size(); j++) {
/*     */       
/* 390 */       division = (Division)divisions.get(j);
/* 391 */       divisionString.add(division.getName());
/*     */     } 
/*     */     
/* 394 */     FormDropDownMenu children = new FormDropDownMenu("children", "", divisionString.toString(), false);
/* 395 */     children.addFormEvent("style", "background-color:lightgrey;");
/* 396 */     children.addFormEvent("size", "5");
/* 397 */     children.setTabIndex(7);
/* 398 */     companyForm.addElement(children);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 420 */     FormTextField lastUpdated = new FormTextField("lastUpdatedDate", false, 50);
/* 421 */     if (company.getLastUpdateDate() != null)
/* 422 */       lastUpdated.setValue(MilestoneHelper.getCustomFormatedDate(company.getLastUpdateDate(), "M/d/yyyy hh:mm:ss a 'ET'")); 
/* 423 */     companyForm.addElement(lastUpdated);
/*     */     
/* 425 */     FormTextField lastUpdatedBy = new FormTextField("lastUpdatedBy", false, 50);
/* 426 */     if (UserManager.getInstance().getUser(company.getLastUpdatingUser()) != null)
/* 427 */       lastUpdatedBy.setValue(UserManager.getInstance().getUser(company.getLastUpdatingUser()).getName()); 
/* 428 */     companyForm.addElement(lastUpdatedBy);
/*     */ 
/*     */ 
/*     */     
/* 432 */     FormTextField familyDescriptionSearch = new FormTextField("FamilyDescriptionSearch", "", false, 20);
/* 433 */     familyDescriptionSearch.setId("FamilyDescriptionSearch");
/* 434 */     companyForm.addElement(familyDescriptionSearch);
/*     */ 
/*     */     
/* 437 */     FormTextField environmentDescriptionSearch = new FormTextField("EnvironmentDescriptionSearch", "", false, 20);
/* 438 */     environmentDescriptionSearch.setId("EnvironmentDescriptionSearch");
/* 439 */     companyForm.addElement(environmentDescriptionSearch);
/*     */     
/* 441 */     FormTextField companyDescriptionSearch = new FormTextField("CompanyDescriptionSearch", "", false, 20);
/* 442 */     companyDescriptionSearch.setId("CompanyDescriptionSearch");
/* 443 */     companyForm.addElement(companyDescriptionSearch);
/*     */     
/* 445 */     companyForm.addElement(new FormHidden("cmd", "company-editor"));
/* 446 */     companyForm.addElement(new FormHidden("OrderBy", "", true));
/*     */ 
/*     */     
/* 449 */     if (context.getSessionValue("NOTEPAD_COMPANY_VISIBLE") != null) {
/* 450 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_COMPANY_VISIBLE"));
/*     */     }
/* 452 */     return companyForm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean save(Context context) {
/* 460 */     User user = MilestoneSecurity.getUser(context);
/* 461 */     Notepad notepad = getCompanyNotepad(context, user.getUserId());
/* 462 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*     */     
/* 464 */     Company company = MilestoneHelper.getScreenCompany(context);
/*     */     
/* 466 */     Form form = buildForm(context, company);
/* 467 */     Company timestampCompany = (Company)context.getSessionValue("Company");
/*     */     
/* 469 */     if (timestampCompany == null || (timestampCompany != null && CompanyManager.getInstance().isTimestampValid(timestampCompany))) {
/*     */       
/* 471 */       form.setValues(context);
/* 472 */       String descriptionString = form.getStringValue("CorporateDescription");
/*     */ 
/*     */ 
/*     */       
/* 476 */       String parentString = form.getStringValue("Parent1Selection");
/*     */       
/* 478 */       if (!CorporateStructureManager.getInstance().isDuplicate(descriptionString, 2, company.getStructureID())) {
/*     */         
/* 480 */         if (!parentString.equalsIgnoreCase("")) {
/* 481 */           company.setParentEnvironment((Environment)MilestoneHelper.getStructureObject(MilestoneHelper.getStructureId(parentString, 5)));
/*     */         }
/*     */ 
/*     */         
/* 485 */         company.setName(descriptionString);
/*     */ 
/*     */         
/* 488 */         String abbreviationString = form.getStringValue("CorporateAbbreviation");
/* 489 */         company.setStructureAbbreviation(abbreviationString);
/* 490 */         company.setActive(((FormCheckBox)form.getElement("active")).isChecked());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 514 */         if (!form.isUnchanged()) {
/*     */           
/* 516 */           FormValidation formValidation = form.validate();
/* 517 */           if (formValidation.isGood()) {
/*     */             
/* 519 */             Company savedCompany = CompanyManager.getInstance().saveCompany(company, user.getUserId());
/*     */ 
/*     */             
/* 522 */             FormElement lastUpdated = form.getElement("lastupdateddate");
/* 523 */             if (savedCompany.getLastUpdateDate() != null) {
/* 524 */               lastUpdated.setValue(MilestoneHelper.getLongDate(savedCompany.getLastUpdateDate()));
/*     */             }
/*     */ 
/*     */             
/* 528 */             Cache.flushCorporateStructure();
/*     */ 
/*     */             
/* 531 */             context.removeSessionValue("user-companies");
/* 532 */             context.removeSessionValue("user-environments");
/*     */             
/* 534 */             notepad.setAllContents(null);
/* 535 */             notepad = getCompanyNotepad(context, user.getUserId());
/* 536 */             notepad.setSelected(savedCompany);
/* 537 */             company = (Company)notepad.validateSelected();
/*     */             
/* 539 */             if (company == null) {
/* 540 */               return goToBlank(context);
/*     */             }
/* 542 */             form = buildForm(context, company);
/*     */             
/* 544 */             context.putSessionValue("Company", company);
/* 545 */             context.putDelivery("Form", form);
/*     */           }
/*     */           else {
/*     */             
/* 549 */             context.putDelivery("FormValidation", formValidation);
/*     */           } 
/*     */         } 
/* 552 */         form.addElement(new FormHidden("OrderBy", "", true));
/* 553 */         context.putDelivery("Form", form);
/* 554 */         return edit(context);
/*     */       } 
/*     */ 
/*     */       
/* 558 */       context.putSessionValue("Company", company);
/* 559 */       context.putDelivery("AlertMessage", 
/* 560 */           MilestoneMessage.getMessage(5, 
/*     */             
/* 562 */             new String[] { "Company", descriptionString }));
/* 563 */       return edit(context);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 569 */     context.putDelivery("AlertMessage", "The record is unavailable for editing at this time.  Please refresh your screen and edit it at a later time.");
/* 570 */     return edit(context);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean delete(Context context) {
/* 579 */     User user = MilestoneSecurity.getUser(context);
/* 580 */     Notepad notepad = getCompanyNotepad(context, user.getUserId());
/* 581 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*     */     
/* 583 */     Company company = MilestoneHelper.getScreenCompany(context);
/*     */ 
/*     */     
/* 586 */     if (company != null) {
/*     */       
/* 588 */       String errorMsg = "";
/* 589 */       errorMsg = CorporateStructureManager.getInstance().delete(company, user.getUserId());
/* 590 */       if (errorMsg != null) {
/*     */         
/* 592 */         context.putDelivery("AlertMessage", errorMsg);
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 597 */         Cache.flushCorporateStructure();
/*     */ 
/*     */         
/* 600 */         context.removeSessionValue("user-companies");
/* 601 */         context.removeSessionValue("user-environments");
/*     */         
/* 603 */         notepad.setAllContents(null);
/* 604 */         notepad.setSelected(null);
/*     */       } 
/*     */     } 
/* 607 */     return edit(context);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean newForm(Context context) {
/* 615 */     User user = MilestoneSecurity.getUser(context);
/*     */     
/* 617 */     Notepad notepad = getCompanyNotepad(context, user.getUserId());
/* 618 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*     */     
/* 620 */     Form form = buildNewForm(context);
/* 621 */     context.putDelivery("Form", form);
/*     */ 
/*     */     
/* 624 */     if (context.getSessionValue("NOTEPAD_COMPANY_VISIBLE") != null) {
/* 625 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_COMPANY_VISIBLE"));
/*     */     }
/* 627 */     return context.includeJSP("company-editor.jsp");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Form buildNewForm(Context context) {
/* 636 */     Form companyForm = new Form(this.application, "companyForm", 
/* 637 */         this.application.getInfrastructure().getServletURL(), "POST");
/* 638 */     User user = MilestoneSecurity.getUser(context);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 649 */     FormTextField familyTB = new FormTextField("Family", "", false, 50, 25);
/* 650 */     companyForm.addElement(familyTB);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 655 */     Vector environments = Cache.getEnvironments();
/* 656 */     FormDropDownMenu parent1Selection = MilestoneHelper.getCorporateStructureDropDown("Parent1Selection", environments, "0", true, false);
/* 657 */     parent1Selection.setTabIndex(1);
/* 658 */     companyForm.addElement(parent1Selection);
/*     */ 
/*     */     
/* 661 */     FormCheckBox active = new FormCheckBox("active", "", true, true);
/* 662 */     active.setTabIndex(2);
/* 663 */     companyForm.addElement(active);
/*     */ 
/*     */     
/* 666 */     FormTextField corporateDescription = new FormTextField("CorporateDescription", "", true, 50, 50);
/* 667 */     corporateDescription.addFormEvent("onBlur", "Javascript:checkField(this);fillAbbreviation()");
/* 668 */     corporateDescription.setTabIndex(3);
/* 669 */     companyForm.addElement(corporateDescription);
/*     */ 
/*     */     
/* 672 */     FormTextField corporateAbbreviation = new FormTextField("CorporateAbbreviation", "", true, 3, 3);
/* 673 */     corporateAbbreviation.addFormEvent("onBlur", "Javascript:checkField(this)");
/* 674 */     corporateAbbreviation.setTabIndex(4);
/* 675 */     companyForm.addElement(corporateAbbreviation);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 680 */     FormTextField familyDescriptionSearch = new FormTextField("FamilyDescriptionSearch", "", false, 20);
/* 681 */     familyDescriptionSearch.setId("FamilyDescriptionSearch");
/* 682 */     companyForm.addElement(familyDescriptionSearch);
/*     */ 
/*     */     
/* 685 */     FormTextField environmentDescriptionSearch = new FormTextField("EnvironmentDescriptionSearch", "", false, 20);
/* 686 */     environmentDescriptionSearch.setId("EnvironmentDescriptionSearch");
/* 687 */     companyForm.addElement(environmentDescriptionSearch);
/*     */     
/* 689 */     FormTextField companyDescriptionSearch = new FormTextField("CompanyDescriptionSearch", "", false, 20);
/* 690 */     companyDescriptionSearch.setId("CompanyDescriptionSearch");
/* 691 */     companyForm.addElement(companyDescriptionSearch);
/*     */     
/* 693 */     companyForm.addElement(new FormHidden("cmd", "company-edit-new", true));
/* 694 */     companyForm.addElement(new FormHidden("OrderBy", "", true));
/*     */ 
/*     */     
/* 697 */     if (context.getSessionValue("NOTEPAD_COMPANY_VISIBLE") != null) {
/* 698 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_COMPANY_VISIBLE"));
/*     */     }
/* 700 */     return companyForm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean saveNew(Context context) {
/* 708 */     User user = MilestoneSecurity.getUser(context);
/*     */     
/* 710 */     Notepad notepad = getCompanyNotepad(context, user.getUserId());
/* 711 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*     */     
/* 713 */     Company company = new Company();
/*     */     
/* 715 */     Form form = buildNewForm(context);
/*     */     
/* 717 */     form.setValues(context);
/* 718 */     String descriptionString = form.getStringValue("CorporateDescription");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 723 */     company.setStructureID(-2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 733 */     String parentString = form.getStringValue("Parent1Selection");
/* 734 */     if (!parentString.equalsIgnoreCase("")) {
/* 735 */       company.setParentEnvironment((Environment)MilestoneHelper.getStructureObject(Integer.parseInt(parentString)));
/*     */     }
/*     */ 
/*     */     
/* 739 */     company.setName(descriptionString);
/* 740 */     company.setStructureType(2);
/*     */ 
/*     */     
/* 743 */     String abbreviationString = form.getStringValue("CorporateAbbreviation");
/* 744 */     company.setActive(((FormCheckBox)form.getElement("active")).isChecked());
/*     */     
/* 746 */     if (!CorporateStructureManager.getInstance().isDuplicate(descriptionString, 2, company.getStructureID())) {
/*     */       
/* 748 */       company.setStructureAbbreviation(abbreviationString);
/*     */ 
/*     */       
/* 751 */       if (!form.isUnchanged()) {
/*     */         
/* 753 */         FormValidation formValidation = form.validate();
/* 754 */         if (formValidation.isGood())
/*     */         {
/* 756 */           Company saveNewCompany = CompanyManager.getInstance().saveNewCompany(company, user.getUserId());
/* 757 */           context.putSessionValue("Company", saveNewCompany);
/*     */ 
/*     */           
/* 760 */           if (notepad.getAllContents() == null || notepad.getAllContents().size() < 1) {
/* 761 */             notepad.setCorporateObjectSearchObj(null);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 766 */           Cache.flushCorporateStructure();
/*     */ 
/*     */           
/* 769 */           context.removeSessionValue("user-companies");
/* 770 */           context.removeSessionValue("user-environments");
/*     */           
/* 772 */           notepad.setAllContents(null);
/* 773 */           notepad.newSelectedReset();
/* 774 */           notepad = getCompanyNotepad(context, user.getUserId());
/* 775 */           notepad.setSelected(saveNewCompany);
/*     */         }
/*     */         else
/*     */         {
/* 779 */           context.putDelivery("FormValidation", formValidation);
/* 780 */           form.addElement(new FormHidden("OrderBy", "", true));
/* 781 */           context.putDelivery("Form", form);
/* 782 */           return context.includeJSP("company-editor.jsp");
/*     */         }
/*     */       
/*     */       } 
/*     */     } else {
/*     */       
/* 788 */       context.putDelivery("AlertMessage", 
/* 789 */           MilestoneMessage.getMessage(5, 
/*     */             
/* 791 */             new String[] { "Company", descriptionString }));
/*     */     } 
/*     */     
/* 794 */     return edit(context);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean goToBlank(Context context) {
/* 810 */     context.putDelivery("isNotePadVisible", new Boolean(MilestoneHelper.getNotePadVisiblitiy(10, context)));
/*     */     
/* 812 */     Form form = new Form(this.application, "form", this.application.getInfrastructure().getServletURL(), "Post");
/* 813 */     form.addElement(new FormHidden("cmd", "company-editor"));
/* 814 */     form.addElement(new FormHidden("OrderBy", "", true));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 819 */     FormTextField familyDescriptionSearch = new FormTextField("FamilyDescriptionSearch", "", true, 20);
/* 820 */     familyDescriptionSearch.setId("FamilyDescriptionSearch");
/* 821 */     form.addElement(familyDescriptionSearch);
/*     */ 
/*     */     
/* 824 */     FormTextField environmentDescriptionSearch = new FormTextField("EnvironmentDescriptionSearch", "", true, 20);
/* 825 */     environmentDescriptionSearch.setId("EnvironmentDescriptionSearch");
/* 826 */     form.addElement(environmentDescriptionSearch);
/*     */     
/* 828 */     FormTextField companyDescriptionSearch = new FormTextField("CompanyDescriptionSearch", "", true, 20);
/* 829 */     companyDescriptionSearch.setId("CompanyDescriptionSearch");
/* 830 */     form.addElement(companyDescriptionSearch);
/*     */     
/* 832 */     context.putDelivery("Form", form);
/*     */     
/* 834 */     return context.includeJSP("blank-company-editor.jsp");
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\CompanyHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */