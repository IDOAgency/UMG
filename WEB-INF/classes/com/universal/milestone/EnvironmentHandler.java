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
/*     */ import com.techempower.gemini.FormRadioButtonGroup;
/*     */ import com.techempower.gemini.FormTextField;
/*     */ import com.techempower.gemini.FormValidation;
/*     */ import com.techempower.gemini.GeminiApplication;
/*     */ import com.universal.milestone.Cache;
/*     */ import com.universal.milestone.Company;
/*     */ import com.universal.milestone.CorporateObjectSearchObj;
/*     */ import com.universal.milestone.CorporateStructureManager;
/*     */ import com.universal.milestone.Environment;
/*     */ import com.universal.milestone.EnvironmentHandler;
/*     */ import com.universal.milestone.EnvironmentManager;
/*     */ import com.universal.milestone.Family;
/*     */ import com.universal.milestone.Form;
/*     */ import com.universal.milestone.MilestoneHelper;
/*     */ import com.universal.milestone.MilestoneHelper_2;
/*     */ import com.universal.milestone.MilestoneMessage;
/*     */ import com.universal.milestone.MilestoneSecurity;
/*     */ import com.universal.milestone.Notepad;
/*     */ import com.universal.milestone.PrefixCode;
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
/*     */ public class EnvironmentHandler
/*     */   extends SecureHandler
/*     */ {
/*     */   public static final String COMPONENT_CODE = "hEnv";
/*     */   public GeminiApplication application;
/*     */   public ComponentLog log;
/*     */   
/*     */   public EnvironmentHandler(GeminiApplication application) {
/*  57 */     this.application = application;
/*  58 */     this.log = application.getLog("hEnv");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  66 */   public String getDescription() { return "Environment Handler"; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean acceptRequest(Dispatcher dispatcher, Context context, String command) {
/*  76 */     if (super.acceptRequest(dispatcher, context, command))
/*     */     {
/*  78 */       if (command.startsWith("environment"))
/*     */       {
/*  80 */         return handleRequest(dispatcher, context, command);
/*     */       }
/*     */     }
/*  83 */     return false;
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
/*  95 */     if (command.equalsIgnoreCase("environment-search"))
/*     */     {
/*  97 */       search(dispatcher, context, command);
/*     */     }
/*  99 */     if (command.equalsIgnoreCase("environment-sort")) {
/*     */       
/* 101 */       sort(dispatcher, context);
/*     */     }
/* 103 */     else if (command.equalsIgnoreCase("environment-editor")) {
/*     */       
/* 105 */       edit(context);
/*     */     }
/* 107 */     else if (command.equalsIgnoreCase("environment-edit-save")) {
/*     */       
/* 109 */       save(context);
/*     */     }
/* 111 */     else if (command.equalsIgnoreCase("environment-edit-save-new")) {
/*     */       
/* 113 */       saveNew(context);
/*     */     }
/* 115 */     else if (command.equalsIgnoreCase("environment-edit-delete")) {
/*     */       
/* 117 */       delete(context);
/*     */     }
/* 119 */     else if (command.equalsIgnoreCase("environment-edit-new")) {
/*     */       
/* 121 */       newForm(context);
/*     */     } 
/* 123 */     return true;
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
/* 134 */     Notepad notepad = MilestoneHelper.getNotepadFromSession(21, context);
/*     */ 
/*     */     
/* 137 */     notepad.setAllContents(null);
/* 138 */     notepad.setSelected(null);
/*     */ 
/*     */     
/* 141 */     String descriptionSearch = "";
/* 142 */     String environmentDescriptionSearch = "";
/*     */     
/* 144 */     if (context.getParameter("FamilyDescriptionSearch") != null) {
/* 145 */       descriptionSearch = context.getParameter("FamilyDescriptionSearch");
/*     */     }
/* 147 */     if (context.getParameter("EnvironmentDescriptionSearch") != null) {
/* 148 */       environmentDescriptionSearch = context.getParameter("EnvironmentDescriptionSearch");
/*     */     }
/*     */     
/* 151 */     CorporateObjectSearchObj corpSearch = new CorporateObjectSearchObj();
/* 152 */     corpSearch.setFamilySearch(descriptionSearch);
/* 153 */     corpSearch.setEnvironmentSearch(environmentDescriptionSearch);
/* 154 */     notepad.setCorporateObjectSearchObj(corpSearch);
/*     */     
/* 156 */     EnvironmentManager.getInstance().setNotepadQuery(notepad);
/* 157 */     dispatcher.redispatch(context, "environment-editor");
/*     */     
/* 159 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean sort(Dispatcher dispatcher, Context context) {
/* 168 */     int sort = context.getIntRequestValue("OrderBy", 0);
/*     */     
/* 170 */     Notepad notepad = getEnvironmentNotepad(context, MilestoneSecurity.getUser(context).getUserId());
/*     */     
/* 172 */     if (notepad.getAllContents() != null) {
/*     */ 
/*     */       
/* 175 */       Vector sortedVector = notepad.getAllContents();
/*     */       
/* 177 */       if (sort == 0) {
/*     */         
/* 179 */         sortedVector = MilestoneHelper.sortCorporateVectorByName(notepad.getAllContents());
/*     */       }
/*     */       else {
/*     */         
/* 183 */         sortedVector = MilestoneHelper.sortCorporateVectorByParentName(notepad.getAllContents());
/*     */       } 
/*     */       
/* 186 */       notepad.setAllContents(sortedVector);
/*     */     } 
/*     */ 
/*     */     
/* 190 */     notepad.goToSelectedPage();
/* 191 */     dispatcher.redispatch(context, "environment-editor");
/*     */     
/* 193 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Notepad getEnvironmentNotepad(Context context, int userId) {
/* 202 */     Vector contents = new Vector();
/*     */     
/* 204 */     if (MilestoneHelper.getNotepadFromSession(21, context) != null) {
/*     */       
/* 206 */       Notepad notepad = MilestoneHelper.getNotepadFromSession(21, context);
/*     */       
/* 208 */       if (notepad.getAllContents() == null) {
/*     */         
/* 210 */         contents = EnvironmentManager.getInstance().getEnvironmentNotepadList(userId, notepad);
/* 211 */         notepad.setAllContents(contents);
/*     */       } 
/* 213 */       return notepad;
/*     */     } 
/*     */ 
/*     */     
/* 217 */     String[] columnNames = { "Environment", "Family" };
/* 218 */     contents = MilestoneHelper.sortCorporateVectorByName(EnvironmentManager.getInstance().getEnvironmentNotepadList(userId, null));
/* 219 */     return new Notepad(contents, 0, 15, "Environment", 21, columnNames);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean edit(Context context) {
/* 228 */     User user = MilestoneSecurity.getUser(context);
/* 229 */     Notepad notepad = getEnvironmentNotepad(context, user.getUserId());
/* 230 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*     */     
/* 232 */     Environment environment = MilestoneHelper.getScreenEnvironment(context);
/*     */     
/* 234 */     if (environment != null) {
/*     */       
/* 236 */       Form form = null;
/* 237 */       if (environment != null) {
/* 238 */         form = buildForm(context, environment);
/*     */       } else {
/* 240 */         form = buildNewForm(context);
/*     */       } 
/* 242 */       context.putDelivery("Form", form);
/* 243 */       return context.includeJSP("environment-editor.jsp");
/*     */     } 
/*     */ 
/*     */     
/* 247 */     return goToBlank(context);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Form buildForm(Context context, Environment environment) {
/* 257 */     Form environmentForm = new Form(this.application, "environmentForm", 
/* 258 */         this.application.getInfrastructure().getServletURL(), "POST");
/*     */ 
/*     */     
/* 261 */     Family parent = environment.getParentFamily();
/* 262 */     boolean boolVal = MilestoneHelper.getActiveCorporateStructure(environment.getStructureID());
/* 263 */     environment.setActive(boolVal);
/* 264 */     Vector families = Cache.getFamilies();
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
/* 278 */     String[] familyArray = { "" };
/* 279 */     if (families.size() > 0) {
/*     */       
/* 281 */       familyArray = new String[families.size()];
/* 282 */       for (int j = 0; j < families.size(); j++) {
/*     */         
/* 284 */         Family family = (Family)families.get(j);
/* 285 */         familyArray[j] = family.getName();
/*     */       } 
/*     */     } 
/* 288 */     FormDropDownMenu parent1Selection = new FormDropDownMenu("Parent1Selection", 
/* 289 */         parent.getName(), familyArray, true);
/*     */     
/* 291 */     parent1Selection.setTabIndex(1);
/* 292 */     environmentForm.addElement(parent1Selection);
/*     */ 
/*     */     
/* 295 */     FormCheckBox active = new FormCheckBox("active", "", true, environment.getActive());
/* 296 */     active.setTabIndex(6);
/* 297 */     environmentForm.addElement(active);
/*     */ 
/*     */     
/* 300 */     String name = environment.getName();
/* 301 */     FormTextField corporateDescription = new FormTextField("CorporateDescription", name, true, 50, 50);
/* 302 */     corporateDescription.addFormEvent("onBlur", "Javascript:checkField(this);fillAbbreviation()");
/* 303 */     corporateDescription.setTabIndex(2);
/* 304 */     environmentForm.addElement(corporateDescription);
/*     */ 
/*     */     
/* 307 */     context.putDelivery("isArchiId", String.valueOf(MilestoneHelper_2.getIsStructureArchimedes(environment.getStructureID())));
/*     */ 
/*     */     
/* 310 */     String abbreviation = environment.getStructureAbbreviation();
/* 311 */     FormTextField corporateAbbreviation = new FormTextField("CorporateAbbreviation", abbreviation, true, 3, 3);
/* 312 */     corporateAbbreviation.addFormEvent("onBlur", "Javascript:checkField(this)");
/* 313 */     corporateAbbreviation.setTabIndex(3);
/* 314 */     environmentForm.addElement(corporateAbbreviation);
/*     */ 
/*     */     
/* 317 */     String westEast = "West";
/* 318 */     if (environment.getDistribution() == 1) {
/* 319 */       westEast = "East";
/*     */     }
/*     */     
/* 322 */     FormRadioButtonGroup distribution = new FormRadioButtonGroup("Distribution", westEast, "West, East", false);
/* 323 */     distribution.setTabIndex(4);
/* 324 */     environmentForm.addElement(distribution);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 329 */     FormDropDownMenu calendarGroup = new FormDropDownMenu("CalendarGroup", Integer.toString(environment.getCalendarGroup()), "1,0", "Canada, United States", true);
/* 330 */     calendarGroup.setTabIndex(5);
/* 331 */     environmentForm.addElement(calendarGroup);
/*     */ 
/*     */     
/* 334 */     Vector companies = environment.getCompanies();
/* 335 */     StringList companyString = new StringList();
/* 336 */     Company company = null;
/* 337 */     for (int j = 0; j < companies.size(); j++) {
/*     */       
/* 339 */       company = (Company)companies.get(j);
/* 340 */       companyString.add(company.getName());
/*     */     } 
/*     */     
/* 343 */     FormDropDownMenu children = new FormDropDownMenu("children", "", companyString.toString(), false);
/* 344 */     children.addFormEvent("style", "background-color:lightgrey;");
/* 345 */     children.addFormEvent("size", "5");
/* 346 */     children.setTabIndex(7);
/* 347 */     environmentForm.addElement(children);
/*     */ 
/*     */     
/* 350 */     Vector prefixes = MilestoneHelper.getPrefixCodes(environment.getStructureID());
/* 351 */     StringList prefixString = new StringList();
/* 352 */     PrefixCode prefixCode = null;
/* 353 */     for (int y = 0; y < prefixes.size(); y++) {
/*     */       
/* 355 */       prefixCode = (PrefixCode)prefixes.get(y);
/* 356 */       prefixString.add(prefixCode.getAbbreviation());
/*     */     } 
/*     */     
/* 359 */     FormDropDownMenu prefix = new FormDropDownMenu("Prefix", "", prefixString.toString(), false);
/* 360 */     prefix.addFormEvent("style", "background-color:lightgrey;");
/* 361 */     prefix.addFormEvent("size", "5");
/* 362 */     prefix.setTabIndex(8);
/* 363 */     environmentForm.addElement(prefix);
/*     */ 
/*     */     
/* 366 */     FormTextField lastUpdated = new FormTextField("lastUpdatedDate", false, 50);
/* 367 */     if (environment.getLastUpdateDate() != null)
/* 368 */       lastUpdated.setValue(MilestoneHelper.getCustomFormatedDate(environment.getLastUpdateDate(), "M/d/yyyy hh:mm:ss a 'ET'")); 
/* 369 */     environmentForm.addElement(lastUpdated);
/*     */     
/* 371 */     FormTextField lastUpdatedBy = new FormTextField("lastUpdatedBy", false, 50);
/* 372 */     if (UserManager.getInstance().getUser(environment.getLastUpdatingUser()) != null)
/* 373 */       lastUpdatedBy.setValue(UserManager.getInstance().getUser(environment.getLastUpdatingUser()).getName()); 
/* 374 */     environmentForm.addElement(lastUpdatedBy);
/*     */ 
/*     */     
/* 377 */     FormTextField familyDescriptionSearch = new FormTextField("FamilyDescriptionSearch", "", false, 20);
/* 378 */     familyDescriptionSearch.setId("FamilyDescriptionSearch");
/* 379 */     environmentForm.addElement(familyDescriptionSearch);
/*     */     
/* 381 */     FormTextField environmentDescriptionSearch = new FormTextField("EnvironmentDescriptionSearch", "", false, 20);
/* 382 */     environmentDescriptionSearch.setId("EnvironmentDescriptionSearch");
/* 383 */     environmentForm.addElement(environmentDescriptionSearch);
/*     */     
/* 385 */     environmentForm.addElement(new FormHidden("cmd", "environment-editor"));
/* 386 */     environmentForm.addElement(new FormHidden("OrderBy", "", true));
/*     */ 
/*     */     
/* 389 */     if (context.getSessionValue("NOTEPAD_ENVIRONMENT_VISIBLE") != null) {
/* 390 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_ENVIRONMENT_VISIBLE"));
/*     */     }
/* 392 */     return environmentForm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean save(Context context) {
/* 400 */     User user = MilestoneSecurity.getUser(context);
/* 401 */     Notepad notepad = getEnvironmentNotepad(context, user.getUserId());
/* 402 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*     */     
/* 404 */     Environment environment = MilestoneHelper.getScreenEnvironment(context);
/*     */     
/* 406 */     Form form = buildForm(context, environment);
/* 407 */     Environment timestampEnvironment = (Environment)context.getSessionValue("Environment");
/*     */     
/* 409 */     if (timestampEnvironment == null || (timestampEnvironment != null && EnvironmentManager.getInstance().isTimestampValid(timestampEnvironment))) {
/*     */       
/* 411 */       form.setValues(context);
/* 412 */       String descriptionString = form.getStringValue("CorporateDescription");
/*     */ 
/*     */       
/* 415 */       String parentString = form.getStringValue("Parent1Selection");
/*     */       
/* 417 */       if (!CorporateStructureManager.getInstance().isDuplicate(descriptionString, 5, environment.getStructureID())) {
/*     */         
/* 419 */         if (!parentString.equalsIgnoreCase("")) {
/* 420 */           environment.setParentFamily((Family)MilestoneHelper.getStructureObject(MilestoneHelper.getStructureId(parentString, 1)));
/*     */         }
/*     */         
/* 423 */         environment.setName(descriptionString);
/*     */ 
/*     */         
/* 426 */         String abbreviationString = form.getStringValue("CorporateAbbreviation");
/* 427 */         environment.setStructureAbbreviation(abbreviationString);
/* 428 */         environment.setActive(((FormCheckBox)form.getElement("active")).isChecked());
/*     */         
/* 430 */         String westString = form.getStringValue("Distribution");
/*     */         
/* 432 */         if (westString.equalsIgnoreCase("West")) {
/* 433 */           environment.setDistribution(0);
/*     */         } else {
/* 435 */           environment.setDistribution(1);
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/* 443 */           environment.calendarGroup = form.getIntegerValue("CalendarGroup");
/*     */         }
/* 445 */         catch (Exception exception) {}
/*     */ 
/*     */         
/* 448 */         if (!form.isUnchanged()) {
/*     */           
/* 450 */           FormValidation formValidation = form.validate();
/* 451 */           if (formValidation.isGood()) {
/*     */             
/* 453 */             Environment savedEnvironment = EnvironmentManager.getInstance().saveEnvironment(environment, user.getUserId());
/*     */ 
/*     */             
/* 456 */             FormElement lastUpdated = form.getElement("lastupdateddate");
/* 457 */             if (savedEnvironment.getLastUpdateDate() != null) {
/* 458 */               lastUpdated.setValue(MilestoneHelper.getLongDate(savedEnvironment.getLastUpdateDate()));
/*     */             }
/*     */ 
/*     */             
/* 462 */             Cache.flushCorporateStructure();
/*     */ 
/*     */             
/* 465 */             context.removeSessionValue("user-companies");
/* 466 */             context.removeSessionValue("user-environments");
/*     */             
/* 468 */             notepad.setAllContents(null);
/* 469 */             notepad = getEnvironmentNotepad(context, user.getUserId());
/* 470 */             notepad.setSelected(savedEnvironment);
/* 471 */             environment = (Environment)notepad.validateSelected();
/*     */             
/* 473 */             if (environment == null) {
/* 474 */               return goToBlank(context);
/*     */             }
/* 476 */             form = buildForm(context, environment);
/*     */             
/* 478 */             context.putSessionValue("Environment", environment);
/* 479 */             context.putDelivery("Form", form);
/*     */           }
/*     */           else {
/*     */             
/* 483 */             context.putDelivery("FormValidation", formValidation);
/*     */           } 
/*     */         } 
/* 486 */         form.addElement(new FormHidden("OrderBy", "", true));
/* 487 */         context.putDelivery("Form", form);
/* 488 */         return edit(context);
/*     */       } 
/*     */ 
/*     */       
/* 492 */       context.putSessionValue("Environment", environment);
/* 493 */       context.putDelivery("AlertMessage", 
/* 494 */           MilestoneMessage.getMessage(5, 
/*     */             
/* 496 */             new String[] { "Environment", descriptionString }));
/* 497 */       return edit(context);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 503 */     context.putDelivery("AlertMessage", "The record is unavailable for editing at this time.  Please refresh your screen and edit it at a later time.");
/* 504 */     return edit(context);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean delete(Context context) {
/* 513 */     User user = MilestoneSecurity.getUser(context);
/* 514 */     Notepad notepad = getEnvironmentNotepad(context, user.getUserId());
/* 515 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*     */     
/* 517 */     Environment environment = MilestoneHelper.getScreenEnvironment(context);
/*     */ 
/*     */     
/* 520 */     if (environment != null) {
/*     */       
/* 522 */       String errorMsg = "";
/* 523 */       errorMsg = CorporateStructureManager.getInstance().delete(environment, user.getUserId());
/* 524 */       if (errorMsg != null) {
/*     */         
/* 526 */         context.putDelivery("AlertMessage", errorMsg);
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 531 */         Cache.flushCorporateStructure();
/*     */         
/* 533 */         context.removeSessionValue("user-companies");
/* 534 */         context.removeSessionValue("user-environments");
/*     */         
/* 536 */         notepad.setAllContents(null);
/* 537 */         notepad.setSelected(null);
/*     */       } 
/*     */     } 
/* 540 */     return edit(context);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean newForm(Context context) {
/* 548 */     User user = MilestoneSecurity.getUser(context);
/*     */     
/* 550 */     Notepad notepad = getEnvironmentNotepad(context, user.getUserId());
/* 551 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*     */     
/* 553 */     Form form = buildNewForm(context);
/* 554 */     context.putDelivery("Form", form);
/*     */ 
/*     */     
/* 557 */     if (context.getSessionValue("NOTEPAD_ENVIRONMENT_VISIBLE") != null) {
/* 558 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_ENVIRONMENT_VISIBLE"));
/*     */     }
/* 560 */     return context.includeJSP("environment-editor.jsp");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Form buildNewForm(Context context) {
/* 569 */     Form environmentForm = new Form(this.application, "environmentForm", 
/* 570 */         this.application.getInfrastructure().getServletURL(), "POST");
/* 571 */     User user = MilestoneSecurity.getUser(context);
/*     */ 
/*     */     
/* 574 */     Vector families = Cache.getFamilies();
/* 575 */     FormDropDownMenu parent1Selection = MilestoneHelper.getCorporateStructureDropDown("Parent1Selection", families, "0", true, false);
/* 576 */     parent1Selection.setTabIndex(1);
/* 577 */     environmentForm.addElement(parent1Selection);
/*     */ 
/*     */     
/* 580 */     FormCheckBox active = new FormCheckBox("active", "", true, true);
/* 581 */     active.setTabIndex(4);
/* 582 */     environmentForm.addElement(active);
/*     */ 
/*     */     
/* 585 */     FormTextField corporateDescription = new FormTextField("CorporateDescription", "", true, 50, 50);
/* 586 */     corporateDescription.addFormEvent("onBlur", "Javascript:checkField(this);fillAbbreviation()");
/* 587 */     corporateDescription.setTabIndex(2);
/* 588 */     environmentForm.addElement(corporateDescription);
/*     */ 
/*     */     
/* 591 */     FormTextField corporateAbbreviation = new FormTextField("CorporateAbbreviation", "", true, 3, 3);
/* 592 */     corporateAbbreviation.addFormEvent("onBlur", "Javascript:checkField(this)");
/* 593 */     corporateAbbreviation.setTabIndex(3);
/* 594 */     environmentForm.addElement(corporateAbbreviation);
/*     */ 
/*     */     
/* 597 */     FormTextField familyDescriptionSearch = new FormTextField("FamilyDescriptionSearch", "", false, 20);
/* 598 */     familyDescriptionSearch.setId("FamilyDescriptionSearch");
/* 599 */     environmentForm.addElement(familyDescriptionSearch);
/*     */     
/* 601 */     FormTextField environmentDescriptionSearch = new FormTextField("EnvironmentDescriptionSearch", "", false, 20);
/* 602 */     environmentDescriptionSearch.setId("EnvironmentDescriptionSearch");
/* 603 */     environmentForm.addElement(environmentDescriptionSearch);
/*     */     
/* 605 */     environmentForm.addElement(new FormHidden("cmd", "environment-edit-new", true));
/* 606 */     environmentForm.addElement(new FormHidden("OrderBy", "", true));
/*     */ 
/*     */     
/* 609 */     if (context.getSessionValue("NOTEPAD_ENVIRONMENT_VISIBLE") != null) {
/* 610 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_ENVIRONMENT_VISIBLE"));
/*     */     }
/* 612 */     return environmentForm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean saveNew(Context context) {
/* 620 */     User user = MilestoneSecurity.getUser(context);
/*     */     
/* 622 */     Notepad notepad = getEnvironmentNotepad(context, user.getUserId());
/* 623 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*     */     
/* 625 */     Environment environment = new Environment();
/*     */     
/* 627 */     Form form = buildNewForm(context);
/*     */     
/* 629 */     form.setValues(context);
/* 630 */     String descriptionString = form.getStringValue("CorporateDescription");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 635 */     environment.setStructureID(-2);
/*     */ 
/*     */     
/* 638 */     String parentString = form.getStringValue("Parent1Selection");
/* 639 */     if (!parentString.equalsIgnoreCase("")) {
/* 640 */       environment.setParentFamily((Family)MilestoneHelper.getStructureObject(Integer.parseInt(parentString)));
/*     */     }
/*     */     
/* 643 */     environment.setName(descriptionString);
/* 644 */     environment.setStructureType(5);
/*     */ 
/*     */     
/* 647 */     String abbreviationString = form.getStringValue("CorporateAbbreviation");
/* 648 */     environment.setActive(((FormCheckBox)form.getElement("active")).isChecked());
/*     */     
/* 650 */     if (!CorporateStructureManager.getInstance().isDuplicate(descriptionString, 5, environment.getStructureID())) {
/*     */       
/* 652 */       environment.setStructureAbbreviation(abbreviationString);
/*     */ 
/*     */       
/* 655 */       if (!form.isUnchanged()) {
/*     */         
/* 657 */         FormValidation formValidation = form.validate();
/* 658 */         if (formValidation.isGood())
/*     */         {
/* 660 */           Environment saveNewEnvironment = EnvironmentManager.getInstance().saveNewEnvironment(environment, user.getUserId());
/* 661 */           context.putSessionValue("Environment", saveNewEnvironment);
/*     */ 
/*     */           
/* 664 */           if (notepad.getAllContents() == null || notepad.getAllContents().size() < 1) {
/* 665 */             notepad.setCorporateObjectSearchObj(null);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 670 */           Cache.flushCorporateStructure();
/*     */ 
/*     */           
/* 673 */           context.removeSessionValue("user-companies");
/* 674 */           context.removeSessionValue("user-environments");
/*     */           
/* 676 */           notepad.setAllContents(null);
/* 677 */           notepad.newSelectedReset();
/* 678 */           notepad = getEnvironmentNotepad(context, user.getUserId());
/* 679 */           notepad.setSelected(saveNewEnvironment);
/*     */         }
/*     */         else
/*     */         {
/* 683 */           context.putDelivery("FormValidation", formValidation);
/* 684 */           form.addElement(new FormHidden("OrderBy", "", true));
/* 685 */           context.putDelivery("Form", form);
/* 686 */           return context.includeJSP("environment-editor.jsp");
/*     */         }
/*     */       
/*     */       } 
/*     */     } else {
/*     */       
/* 692 */       context.putDelivery("AlertMessage", 
/* 693 */           MilestoneMessage.getMessage(5, 
/*     */             
/* 695 */             new String[] { "Environment", descriptionString }));
/*     */     } 
/*     */     
/* 698 */     return edit(context);
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
/* 714 */     context.putDelivery("isNotePadVisible", new Boolean(MilestoneHelper.getNotePadVisiblitiy(21, context)));
/*     */     
/* 716 */     Form form = new Form(this.application, "form", this.application.getInfrastructure().getServletURL(), "Post");
/* 717 */     form.addElement(new FormHidden("cmd", "environment-editor"));
/* 718 */     form.addElement(new FormHidden("OrderBy", "", true));
/*     */ 
/*     */     
/* 721 */     FormTextField familyDescriptionSearch = new FormTextField("FamilyDescriptionSearch", "", true, 20);
/* 722 */     familyDescriptionSearch.setId("FamilyDescriptionSearch");
/* 723 */     form.addElement(familyDescriptionSearch);
/*     */     
/* 725 */     FormTextField environmentDescriptionSearch = new FormTextField("EnvironmentDescriptionSearch", "", true, 20);
/* 726 */     environmentDescriptionSearch.setId("EnvironmentDescriptionSearch");
/* 727 */     form.addElement(environmentDescriptionSearch);
/*     */     
/* 729 */     context.putDelivery("Form", form);
/*     */     
/* 731 */     return context.includeJSP("blank-environment-editor.jsp");
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\EnvironmentHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */