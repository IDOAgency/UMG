/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.ComponentLog;
/*     */ import com.techempower.gemini.Context;
/*     */ import com.techempower.gemini.Dispatcher;
/*     */ import com.techempower.gemini.FormCheckBox;
/*     */ import com.techempower.gemini.FormDropDownMenu;
/*     */ import com.techempower.gemini.FormRadioButtonGroup;
/*     */ import com.techempower.gemini.FormTextField;
/*     */ import com.techempower.gemini.FormValidation;
/*     */ import com.techempower.gemini.GeminiApplication;
/*     */ import com.universal.milestone.Form;
/*     */ import com.universal.milestone.MilestoneHelper;
/*     */ import com.universal.milestone.ReleasingFamily;
/*     */ import com.universal.milestone.ReportConfigManager;
/*     */ import com.universal.milestone.SecureHandler;
/*     */ import com.universal.milestone.SelectionHandler;
/*     */ import com.universal.milestone.User;
/*     */ import com.universal.milestone.UserManager;
/*     */ import com.universal.milestone.UserPreferences;
/*     */ import com.universal.milestone.UserPreferencesHandler;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UserPreferencesHandler
/*     */   extends SecureHandler
/*     */ {
/*     */   public static final String COMPONENT_CODE = "hSec";
/*     */   public GeminiApplication application;
/*     */   public ComponentLog log;
/*     */   
/*     */   public UserPreferencesHandler(GeminiApplication application) {
/*  59 */     this.application = application;
/*  60 */     this.log = application.getLog("hSec");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   public String getDescription() { return "UserPreferences"; }
/*     */ 
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
/*  81 */       if (command.startsWith("user-preferences"))
/*     */       {
/*  83 */         return handleRequest(dispatcher, context, command);
/*     */       }
/*     */     }
/*     */     
/*  87 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean handleRequest(Dispatcher dispatcher, Context context, String command) {
/*  97 */     if (command.equalsIgnoreCase("user-preferences-save")) {
/*     */       
/*  99 */       userPreferencesSave(context);
/*     */     }
/* 101 */     else if (command.equalsIgnoreCase("user-preferences-editor")) {
/*     */       
/* 103 */       userPreferencesEditor(context);
/*     */     } 
/*     */ 
/*     */     
/* 107 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Form userPreferencesEditor(Context context) {
/* 115 */     User user = (User)context.getSessionValue("user");
/*     */     
/* 117 */     if (user != null) {
/*     */       
/* 119 */       Form form = null;
/*     */       
/* 121 */       if (user != null) {
/* 122 */         form = buildForm(context, user);
/*     */       } else {
/* 124 */         form = buildNewForm(context);
/*     */       } 
/* 126 */       return form;
/*     */     } 
/*     */ 
/*     */     
/* 130 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Form userPreferencesSave(Context context) {
/* 140 */     User user = (User)context.getSessionValue("securityUser");
/*     */     
/* 142 */     Form form = buildForm(context, user);
/*     */ 
/*     */     
/* 145 */     if (UserManager.getInstance().isTimestampValid(user)) {
/*     */       
/* 147 */       form.setValues(context);
/*     */ 
/*     */       
/* 150 */       String login = form.getStringValue("login");
/* 151 */       user.setLogin(login);
/*     */       
/* 153 */       if (!UserManager.getInstance().isDuplicate(user) && 
/* 154 */         !form.isUnchanged()) {
/* 155 */         FormValidation formValidation = form.validate();
/* 156 */         if (formValidation.isGood())
/*     */         {
/* 158 */           User savedUser = null;
/* 159 */           form = buildForm(context, savedUser);
/*     */           
/* 161 */           context.putSessionValue("securityUser", savedUser);
/*     */         }
/*     */       
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 168 */       context.putDelivery("AlertMessage", "Duplicate.");
/* 169 */       form = buildForm(context, user);
/*     */     } 
/*     */     
/* 172 */     return form;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Form buildForm(Context context, User user) {
/* 182 */     Form userSecurityForm = new Form(this.application, "userSecurityForm", 
/* 183 */         this.application.getInfrastructure().getServletURL(), "POST");
/*     */     
/* 185 */     UserPreferences up = null;
/*     */     
/* 187 */     if (user == null || user.getPreferences() == null) {
/* 188 */       up = new UserPreferences();
/*     */     } else {
/* 190 */       up = user.getPreferences();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 195 */     String[] values = new String[7];
/* 196 */     values[0] = "2";
/* 197 */     values[1] = "3";
/* 198 */     values[2] = "4";
/* 199 */     values[3] = "5";
/* 200 */     values[4] = "6";
/* 201 */     values[5] = "1";
/* 202 */     values[6] = "7";
/*     */     
/* 204 */     String[] labels = new String[7];
/*     */     
/* 206 */     labels[0] = "Schedule";
/* 207 */     labels[1] = "Selection";
/* 208 */     labels[2] = "Manufacturing";
/* 209 */     labels[3] = "PFM";
/* 210 */     labels[4] = "BOM";
/* 211 */     labels[5] = "Release Calendar";
/* 212 */     labels[6] = "Reports";
/*     */     
/* 214 */     FormRadioButtonGroup openingScreen = new FormRadioButtonGroup("openingScreen", String.valueOf(up.getOpeningScreen()), values, labels, false);
/* 215 */     openingScreen.setTabIndex(50);
/* 216 */     userSecurityForm.addElement(openingScreen);
/*     */ 
/*     */     
/* 219 */     values = new String[3];
/* 220 */     values[0] = "1";
/* 221 */     values[1] = "2";
/* 222 */     values[2] = "3";
/*     */     
/* 224 */     labels = new String[3];
/* 225 */     labels[0] = "Do not auto-close";
/* 226 */     labels[1] = "Close my completed products";
/* 227 */     labels[2] = "Close my completed products when all UML / eCommerce tasks are completed";
/*     */     
/* 229 */     FormRadioButtonGroup autoCloseRadio = new FormRadioButtonGroup("autoCloseRadio", String.valueOf(up.getAutoClose()), values, labels, false);
/* 230 */     autoCloseRadio.setTabIndex(51);
/* 231 */     userSecurityForm.addElement(autoCloseRadio);
/*     */ 
/*     */     
/* 234 */     FormTextField autoCloseDays = new FormTextField("autoCloseDays", String.valueOf(up.getAutoCloseDays()), false, 10, 100);
/* 235 */     autoCloseDays.setTabIndex(52);
/* 236 */     userSecurityForm.addElement(autoCloseDays);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 242 */     values = new String[3];
/* 243 */     values[0] = "1";
/* 244 */     values[1] = "2";
/* 245 */     values[2] = "3";
/*     */     
/* 247 */     labels = new String[3];
/* 248 */     labels[0] = "Artist";
/* 249 */     labels[1] = "Title";
/* 250 */     labels[2] = "Street Date";
/*     */     
/* 252 */     FormRadioButtonGroup sortBy = new FormRadioButtonGroup("sortBy", String.valueOf(up.getNotepadSortBy()), values, labels, false);
/* 253 */     sortBy.setTabIndex(53);
/* 254 */     userSecurityForm.addElement(sortBy);
/*     */ 
/*     */     
/* 257 */     values = new String[2];
/* 258 */     values[0] = "1";
/* 259 */     values[1] = "2";
/*     */     
/* 261 */     labels = new String[2];
/* 262 */     labels[0] = "Ascending";
/* 263 */     labels[1] = "Descending";
/*     */     
/* 265 */     FormRadioButtonGroup order = new FormRadioButtonGroup("order", String.valueOf(up.getNotepadOrder()), values, labels, false);
/* 266 */     order.setTabIndex(54);
/* 267 */     userSecurityForm.addElement(order);
/*     */ 
/*     */     
/* 270 */     values = new String[3];
/* 271 */     values[0] = "1";
/* 272 */     values[1] = "2";
/* 273 */     values[2] = "3";
/*     */     
/* 275 */     labels = new String[3];
/* 276 */     labels[0] = "Physical";
/* 277 */     labels[1] = "Digital";
/* 278 */     labels[2] = "Both";
/*     */     
/* 280 */     FormRadioButtonGroup productType = new FormRadioButtonGroup("productType", String.valueOf(up.getNotepadProductType()), values, labels, false);
/* 281 */     productType.setTabIndex(55);
/* 282 */     userSecurityForm.addElement(productType);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 287 */     Vector families = SelectionHandler.filterCSO(ReleasingFamily.getUserReleasingFamiliesVectorOfFamilies(context));
/* 288 */     FormDropDownMenu releasingFamilies = MilestoneHelper.getCorporateStructureDropDown("releasingFamilies", families, String.valueOf(up.getSelectionReleasingFamily()), false, true);
/* 289 */     releasingFamilies.addFormEvent("onChange", "return(clickReleasingFamily(this, document.all.envMenu))");
/* 290 */     releasingFamilies.setTabIndex(56);
/* 291 */     userSecurityForm.addElement(releasingFamilies);
/*     */ 
/*     */     
/* 294 */     Vector environments = MilestoneHelper.getUserEnvironments(context);
/* 295 */     Vector companies = MilestoneHelper.getUserCompanies(context);
/*     */     
/* 297 */     environments = SelectionHandler.filterSelectionEnvironments(companies);
/* 298 */     companies = MilestoneHelper.removeUnusedCSO(companies, context, -1);
/*     */     
/* 300 */     FormDropDownMenu envMenu = MilestoneHelper.getCorporateStructureDropDown("envMenu", SelectionHandler.filterSelectionEnvironments(companies), String.valueOf(up.getSelectionEnvironment()), false, true);
/* 301 */     envMenu.setTabIndex(58);
/* 302 */     userSecurityForm.addElement(envMenu);
/*     */ 
/*     */     
/* 305 */     Vector contactVector = ReportConfigManager.getLabelContacts();
/* 306 */     String contactList = "All,";
/* 307 */     String idList = "0,";
/*     */     
/* 309 */     if (contactVector != null) {
/*     */       
/* 311 */       for (int i = 0; i < contactVector.size(); i++) {
/*     */         
/* 313 */         User userContact = (User)contactVector.get(i);
/*     */ 
/*     */         
/* 316 */         if (userContact == null || userContact.getInactive() == 0)
/*     */         {
/*     */           
/* 319 */           if (i < contactVector.size() - 1)
/*     */           {
/* 321 */             if (userContact != null)
/*     */             {
/* 323 */               contactList = String.valueOf(contactList) + userContact.getName() + ",";
/* 324 */               idList = String.valueOf(idList) + userContact.getUserId() + ",";
/*     */             
/*     */             }
/*     */           
/*     */           }
/* 329 */           else if (userContact != null)
/*     */           {
/* 331 */             contactList = String.valueOf(contactList) + userContact.getName();
/* 332 */             idList = String.valueOf(idList) + userContact.getUserId();
/*     */           }
/*     */         
/*     */         }
/*     */       } 
/*     */     } else {
/*     */       
/* 339 */       contactList = "All";
/* 340 */       idList = "0";
/*     */     } 
/*     */     
/* 343 */     FormDropDownMenu ContactList = new FormDropDownMenu("ContactList", String.valueOf(up.getSelectionLabelContact()), idList, contactList, false);
/* 344 */     ContactList.setTabIndex(59);
/* 345 */     userSecurityForm.addElement(ContactList);
/*     */ 
/*     */     
/* 348 */     values = new String[3];
/* 349 */     values[0] = "0";
/* 350 */     values[1] = "1";
/* 351 */     values[2] = "2";
/*     */     
/* 353 */     labels = new String[3];
/* 354 */     labels[0] = "Physical";
/* 355 */     labels[1] = "Digital";
/* 356 */     labels[2] = "Both";
/*     */     
/* 358 */     FormRadioButtonGroup selectionProductType = new FormRadioButtonGroup("selectionProductType", String.valueOf(up.getSelectionProductType()), values, labels, false);
/* 359 */     selectionProductType.setTabIndex(60);
/* 360 */     userSecurityForm.addElement(selectionProductType);
/*     */ 
/*     */     
/* 363 */     FormCheckBox status = new FormCheckBox("status", "Include Closed/Cancelled", false, false);
/* 364 */     status.setId("status");
/* 365 */     if (up.getSelectionStatus() > 0)
/* 366 */       status.setChecked(true); 
/* 367 */     status.setTabIndex(61);
/* 368 */     userSecurityForm.addElement(status);
/*     */ 
/*     */     
/* 371 */     FormCheckBox priorCriteria = new FormCheckBox("priorCriteria", "Override User Preferences with manually selected search and sort criteria", false, false);
/* 372 */     priorCriteria.setId("priorCriteria");
/* 373 */     if (up.getSelectionPriorCriteria() > 0)
/* 374 */       priorCriteria.setChecked(true); 
/* 375 */     priorCriteria.setTabIndex(62);
/* 376 */     userSecurityForm.addElement(priorCriteria);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 381 */     values = new String[6];
/* 382 */     values[0] = "1";
/* 383 */     values[1] = "2";
/* 384 */     values[2] = "3";
/* 385 */     values[3] = "4";
/* 386 */     values[4] = "5";
/* 387 */     values[5] = "6";
/*     */     
/* 389 */     labels = new String[6];
/* 390 */     labels[0] = "Task";
/* 391 */     labels[1] = "Weeks to Release";
/* 392 */     labels[2] = "Due Date";
/* 393 */     labels[3] = "Completion Date";
/* 394 */     labels[4] = "Status";
/* 395 */     labels[5] = "Vendor";
/*     */     
/* 397 */     FormRadioButtonGroup sortBySchedule = new FormRadioButtonGroup("sortBySchedule", String.valueOf(up.getSchedulePhysicalSortBy()), values, labels, false);
/* 398 */     sortBySchedule.setTabIndex(63);
/*     */ 
/*     */ 
/*     */     
/* 402 */     userSecurityForm.addElement(sortBySchedule);
/*     */ 
/*     */     
/* 405 */     values = new String[3];
/* 406 */     values[0] = "1";
/* 407 */     values[1] = "2";
/* 408 */     values[2] = "3";
/*     */     
/* 410 */     labels = new String[3];
/* 411 */     labels[0] = "All";
/* 412 */     labels[1] = "Only UML Tasks";
/* 413 */     labels[2] = "Only Label Tasks";
/*     */     
/* 415 */     FormRadioButtonGroup ownerSchedule = new FormRadioButtonGroup("ownerSchedule", String.valueOf(up.getSchedulePhysicalOwner()), values, labels, false);
/* 416 */     ownerSchedule.setTabIndex(64);
/* 417 */     userSecurityForm.addElement(ownerSchedule);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 422 */     values = new String[6];
/* 423 */     values[0] = "1";
/* 424 */     values[1] = "2";
/* 425 */     values[2] = "3";
/* 426 */     values[3] = "4";
/* 427 */     values[4] = "5";
/* 428 */     values[5] = "6";
/*     */     
/* 430 */     labels = new String[6];
/* 431 */     labels[0] = "Task";
/* 432 */     labels[1] = "Weeks to Release";
/* 433 */     labels[2] = "Due Date";
/* 434 */     labels[3] = "Completion Date";
/* 435 */     labels[4] = "Status";
/* 436 */     labels[5] = "Vendor";
/*     */     
/* 438 */     FormRadioButtonGroup sortByDigitalSchedule = new FormRadioButtonGroup("sortByDigitalSchedule", String.valueOf(up.getScheduleDigitalSortBy()), values, labels, false);
/* 439 */     sortByDigitalSchedule.setTabIndex(65);
/*     */ 
/*     */ 
/*     */     
/* 443 */     userSecurityForm.addElement(sortByDigitalSchedule);
/*     */ 
/*     */     
/* 446 */     values = new String[3];
/* 447 */     values[0] = "1";
/* 448 */     values[1] = "2";
/* 449 */     values[2] = "3";
/*     */     
/* 451 */     labels = new String[3];
/* 452 */     labels[0] = "All";
/* 453 */     labels[1] = "Only eCommerce Tasks";
/* 454 */     labels[2] = "Only Label Tasks";
/*     */     
/* 456 */     FormRadioButtonGroup ownerDigitalSchedule = new FormRadioButtonGroup("ownerDigitalSchedule", String.valueOf(up.getScheduleDigitalOwner()), values, labels, false);
/* 457 */     ownerDigitalSchedule.setTabIndex(66);
/* 458 */     userSecurityForm.addElement(ownerDigitalSchedule);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 464 */     values = new String[3];
/* 465 */     values[0] = "1";
/* 466 */     values[1] = "2";
/* 467 */     values[2] = "3";
/*     */     
/* 469 */     labels = new String[3];
/* 470 */     labels[0] = "Commercial";
/* 471 */     labels[1] = "Promotional";
/* 472 */     labels[2] = "All";
/*     */     
/* 474 */     FormRadioButtonGroup releaseType = new FormRadioButtonGroup("releaseType", String.valueOf(up.getReportsReleaseType()), values, labels, false);
/* 475 */     releaseType.setTabIndex(67);
/* 476 */     userSecurityForm.addElement(releaseType);
/*     */ 
/*     */     
/* 479 */     FormDropDownMenu releasingFamiliesReports = MilestoneHelper.getCorporateStructureDropDown("releasingFamiliesReports", families, String.valueOf(up.getReportsReleasingFamily()), false, true);
/* 480 */     releasingFamiliesReports.addFormEvent("onChange", "return(clickReleasingFamily(this, document.all.envMenuReports))");
/* 481 */     releasingFamiliesReports.setTabIndex(68);
/* 482 */     userSecurityForm.addElement(releasingFamiliesReports);
/*     */ 
/*     */     
/* 485 */     FormDropDownMenu envMenuReports = MilestoneHelper.getCorporateStructureDropDown("envMenuReports", SelectionHandler.filterSelectionEnvironments(companies), String.valueOf(up.getReportsEnvironment()), false, true);
/* 486 */     envMenuReports.setTabIndex(70);
/* 487 */     userSecurityForm.addElement(envMenuReports);
/*     */ 
/*     */     
/* 490 */     FormDropDownMenu ContactListReports = new FormDropDownMenu("ContactListReports", String.valueOf(up.getReportsLabelContact()), idList, contactList, false);
/* 491 */     ContactListReports.setTabIndex(71);
/* 492 */     userSecurityForm.addElement(ContactListReports);
/*     */ 
/*     */ 
/*     */     
/* 496 */     Vector contactVector1 = ReportConfigManager.getUmlContacts();
/* 497 */     String contactList1 = "All,";
/* 498 */     idList = "0,";
/*     */     
/* 500 */     if (contactVector1 != null) {
/*     */       
/* 502 */       for (int i = 0; i < contactVector1.size(); i++)
/*     */       {
/* 504 */         User userContact1 = (User)contactVector1.get(i);
/* 505 */         if (i < contactVector1.size() - 1)
/*     */         {
/* 507 */           if (userContact1 != null)
/*     */           {
/* 509 */             contactList1 = String.valueOf(contactList1) + userContact1.getName() + ",";
/* 510 */             idList = String.valueOf(idList) + userContact1.getUserId() + ",";
/*     */           
/*     */           }
/*     */         
/*     */         }
/* 515 */         else if (userContact1 != null)
/*     */         {
/* 517 */           contactList1 = String.valueOf(contactList1) + userContact1.getName();
/* 518 */           idList = String.valueOf(idList) + userContact1.getUserId();
/*     */         }
/*     */       
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 525 */       contactList1 = "All";
/* 526 */       idList = "0";
/*     */     } 
/*     */     
/* 529 */     FormDropDownMenu ContactList1 = new FormDropDownMenu("umlContact", String.valueOf(up.getReportsUMLContact()), idList, contactList1, false);
/* 530 */     ContactList1.setTabIndex(72);
/* 531 */     userSecurityForm.addElement(ContactList1);
/*     */ 
/*     */     
/* 534 */     FormCheckBox statusReportsAll = new FormCheckBox("statusReportsAll", "All", false, false);
/* 535 */     statusReportsAll.setId("statusReportsAll");
/* 536 */     if (up.getReportsStatusAll() == 1) {
/* 537 */       statusReportsAll.setChecked(true);
/*     */     }
/* 539 */     statusReportsAll.addFormEvent("onClick", "checkAll()");
/* 540 */     statusReportsAll.setTabIndex(73);
/* 541 */     userSecurityForm.addElement(statusReportsAll);
/*     */     
/* 543 */     FormCheckBox statusReportsActive = new FormCheckBox("statusReportsActive", "Active", false, false);
/* 544 */     statusReportsActive.setId("statusReportsActive");
/* 545 */     if (up.getReportsStatusActive() == 1) {
/* 546 */       statusReportsActive.setChecked(true);
/*     */     }
/* 548 */     statusReportsActive.addFormEvent("onClick", "processCheck()");
/*     */     
/* 550 */     statusReportsActive.setTabIndex(74);
/* 551 */     userSecurityForm.addElement(statusReportsActive);
/*     */     
/* 553 */     FormCheckBox statusReportsTBS = new FormCheckBox("statusReportsTBS", "TBS", false, false);
/* 554 */     statusReportsTBS.setId("statusReportsTBS");
/* 555 */     if (up.getReportsStatusTBS() == 1) {
/* 556 */       statusReportsTBS.setChecked(true);
/*     */     }
/* 558 */     statusReportsTBS.addFormEvent("onClick", "processCheck()");
/* 559 */     statusReportsTBS.setTabIndex(75);
/* 560 */     userSecurityForm.addElement(statusReportsTBS);
/*     */     
/* 562 */     FormCheckBox statusReportsClosed = new FormCheckBox("statusReportsClosed", "Closed", false, false);
/* 563 */     statusReportsClosed.setId("statusReportsClosed");
/* 564 */     if (up.getReportsStatusClosed() == 1) {
/* 565 */       statusReportsClosed.setChecked(true);
/*     */     }
/* 567 */     statusReportsClosed.addFormEvent("onClick", "processCheck()");
/* 568 */     statusReportsClosed.setTabIndex(76);
/* 569 */     userSecurityForm.addElement(statusReportsClosed);
/*     */     
/* 571 */     FormCheckBox statusReportsCancelled = new FormCheckBox("statusReportsCancelled", "Cancelled", false, false);
/* 572 */     statusReportsCancelled.setId("statusReportsCancelled");
/* 573 */     if (up.getReportsStatusCancelled() == 1) {
/* 574 */       statusReportsCancelled.setChecked(true);
/*     */     }
/* 576 */     statusReportsCancelled.addFormEvent("onClick", "processCheck()");
/* 577 */     statusReportsCancelled.setTabIndex(77);
/* 578 */     userSecurityForm.addElement(statusReportsCancelled);
/*     */     
/* 580 */     context.putDelivery("UserPrefsForm", userSecurityForm);
/* 581 */     context.putDelivery("corporate-array", ReleasingFamily.getJavaScriptCorporateArrayReleasingFamily(context));
/*     */     
/* 583 */     return userSecurityForm;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 602 */   public Form buildNewForm(Context context) { return buildForm(context, null); }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\UserPreferencesHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */