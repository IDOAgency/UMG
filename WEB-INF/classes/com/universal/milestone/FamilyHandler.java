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
/*     */ import com.universal.milestone.CorporateObjectSearchObj;
/*     */ import com.universal.milestone.CorporateStructureManager;
/*     */ import com.universal.milestone.Environment;
/*     */ import com.universal.milestone.Family;
/*     */ import com.universal.milestone.FamilyHandler;
/*     */ import com.universal.milestone.FamilyManager;
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
/*     */ public class FamilyHandler
/*     */   extends SecureHandler
/*     */ {
/*     */   public static final String COMPONENT_CODE = "hBom";
/*     */   public GeminiApplication application;
/*     */   public ComponentLog log;
/*     */   
/*     */   public FamilyHandler(GeminiApplication application) {
/*  58 */     this.application = application;
/*  59 */     this.log = application.getLog("hBom");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  67 */   public String getDescription() { return "Family Handler"; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean acceptRequest(Dispatcher dispatcher, Context context, String command) {
/*  77 */     if (super.acceptRequest(dispatcher, context, command))
/*     */     {
/*  79 */       if (command.startsWith("family"))
/*     */       {
/*  81 */         return handleRequest(dispatcher, context, command);
/*     */       }
/*     */     }
/*  84 */     return false;
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
/*  96 */     if (command.equalsIgnoreCase("family-search")) {
/*     */       
/*  98 */       search(dispatcher, context, command);
/*     */     }
/* 100 */     else if (command.equalsIgnoreCase("family-editor")) {
/*     */       
/* 102 */       edit(context);
/*     */     }
/* 104 */     else if (command.equalsIgnoreCase("family-edit-save")) {
/*     */       
/* 106 */       save(context);
/*     */     }
/* 108 */     else if (command.equalsIgnoreCase("family-edit-new")) {
/*     */       
/* 110 */       editNew(context);
/*     */     }
/* 112 */     else if (command.equalsIgnoreCase("family-edit-save-new")) {
/*     */       
/* 114 */       editSaveNew(context);
/*     */     }
/* 116 */     else if (command.equalsIgnoreCase("family-edit-delete")) {
/*     */       
/* 118 */       delete(context);
/*     */     } 
/* 120 */     return true;
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
/* 131 */     Notepad notepad = MilestoneHelper.getNotepadFromSession(9, context);
/*     */ 
/*     */     
/* 134 */     notepad.setAllContents(null);
/* 135 */     notepad.setSelected(null);
/*     */ 
/*     */     
/* 138 */     String descriptionSearch = "";
/*     */     
/* 140 */     if (context.getParameter("FamilyDescriptionSearch") != null) {
/* 141 */       descriptionSearch = context.getParameter("FamilyDescriptionSearch");
/*     */     }
/*     */     
/* 144 */     CorporateObjectSearchObj corpSearch = new CorporateObjectSearchObj();
/* 145 */     corpSearch.setFamilySearch(descriptionSearch);
/* 146 */     notepad.setCorporateObjectSearchObj(corpSearch);
/*     */     
/* 148 */     FamilyManager.getInstance().setNotepadQuery(notepad);
/* 149 */     dispatcher.redispatch(context, "family-editor");
/*     */     
/* 151 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Notepad getFamilyNotepad(Context context, int userId) {
/* 161 */     Vector contents = new Vector();
/*     */     
/* 163 */     if (MilestoneHelper.getNotepadFromSession(9, context) != null) {
/*     */       
/* 165 */       Notepad notepad = MilestoneHelper.getNotepadFromSession(9, context);
/*     */       
/* 167 */       if (notepad.getAllContents() == null) {
/*     */         
/* 169 */         contents = MilestoneHelper.sortCorporateVectorByName(FamilyManager.getInstance().getFamilyNotepadList(userId, notepad));
/* 170 */         notepad.setAllContents(contents);
/*     */       } 
/*     */       
/* 173 */       return notepad;
/*     */     } 
/*     */ 
/*     */     
/* 177 */     String[] columnNames = { "Family" };
/* 178 */     contents = MilestoneHelper.sortCorporateVectorByName(FamilyManager.getInstance().getFamilyNotepadList(userId, null));
/* 179 */     return new Notepad(contents, 0, 15, "Family", 9, columnNames);
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
/*     */   private boolean edit(Context context) {
/* 191 */     User user = MilestoneSecurity.getUser(context);
/*     */     
/* 193 */     Notepad notepad = getFamilyNotepad(context, user.getUserId());
/* 194 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*     */     
/* 196 */     Family family = MilestoneHelper.getScreenFamily(context);
/*     */     
/* 198 */     if (family != null) {
/*     */       
/* 200 */       Form form = null;
/* 201 */       if (family != null) {
/* 202 */         form = buildForm(context, family);
/*     */       } else {
/* 204 */         form = buildNewForm(context);
/*     */       } 
/* 206 */       context.putDelivery("Form", form);
/*     */       
/* 208 */       return context.includeJSP("family-editor.jsp");
/*     */     } 
/*     */ 
/*     */     
/* 212 */     return goToBlank(context);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Form buildForm(Context context, Family family) {
/* 219 */     this.log.debug("buildForm");
/* 220 */     Form familyForm = new Form(this.application, "FamilyForm", 
/* 221 */         this.application.getInfrastructure().getServletURL(), "POST");
/*     */ 
/*     */     
/* 224 */     String name = "";
/* 225 */     name = family.getName();
/* 226 */     boolean boolVal = MilestoneHelper.getActiveCorporateStructure(family.getStructureID());
/* 227 */     family.setActive(boolVal);
/*     */     
/* 229 */     FormTextField corporateDescription = new FormTextField("CorporateDescription", name, true, 50, 25);
/* 230 */     corporateDescription.addFormEvent("onBlur", "Javascript:checkField(this);fillAbbreviation()");
/* 231 */     corporateDescription.setTabIndex(1);
/* 232 */     familyForm.addElement(corporateDescription);
/*     */ 
/*     */     
/* 235 */     context.putDelivery("isArchiId", String.valueOf(MilestoneHelper_2.getIsStructureArchimedes(family.getStructureID())));
/*     */ 
/*     */     
/* 238 */     FormCheckBox active = new FormCheckBox("active", "", true, family.getActive());
/* 239 */     active.setTabIndex(3);
/* 240 */     familyForm.addElement(active);
/*     */ 
/*     */     
/* 243 */     String abbreviation = "";
/* 244 */     abbreviation = family.getStructureAbbreviation();
/*     */     
/* 246 */     FormTextField corporateAbbreviation = new FormTextField("CorporateAbbreviation", abbreviation, true, 8, 8);
/* 247 */     corporateAbbreviation.addFormEvent("onBlur", "Javascript:checkField(this)");
/* 248 */     corporateAbbreviation.setTabIndex(2);
/* 249 */     familyForm.addElement(corporateAbbreviation);
/*     */ 
/*     */ 
/*     */     
/* 253 */     Vector environments = family.getEnvironments();
/* 254 */     StringList environmentString = new StringList(",");
/* 255 */     Environment environment = null;
/* 256 */     for (int j = 0; j < environments.size(); j++) {
/*     */       
/* 258 */       environment = (Environment)environments.get(j);
/* 259 */       environmentString.add(environment.getName());
/*     */     } 
/* 261 */     FormDropDownMenu children = new FormDropDownMenu("children", "", environmentString.toString(), false);
/* 262 */     children.addFormEvent("style", "background-color:lightgrey;");
/* 263 */     children.addFormEvent("size", "5");
/* 264 */     children.setTabIndex(3);
/* 265 */     familyForm.addElement(children);
/*     */ 
/*     */     
/* 268 */     FormTextField lastUpdated = new FormTextField("lastUpdatedDate", false, 50);
/* 269 */     if (family.getLastUpdateDate() != null)
/* 270 */       lastUpdated.setValue(MilestoneHelper.getCustomFormatedDate(family.getLastUpdateDate(), "M/d/yyyy hh:mm:ss a 'ET'")); 
/* 271 */     familyForm.addElement(lastUpdated);
/*     */     
/* 273 */     FormTextField lastUpdatedBy = new FormTextField("lastUpdatedBy", false, 50);
/* 274 */     if (UserManager.getInstance().getUser(family.getLastUpdatingUser()) != null)
/* 275 */       lastUpdatedBy.setValue(UserManager.getInstance().getUser(family.getLastUpdatingUser()).getName()); 
/* 276 */     familyForm.addElement(lastUpdatedBy);
/*     */ 
/*     */     
/* 279 */     FormTextField FamilyDescriptionSearch = new FormTextField("FamilyDescriptionSearch", "", false, 20);
/* 280 */     FamilyDescriptionSearch.setId("FamilyDescriptionSearch");
/* 281 */     familyForm.addElement(FamilyDescriptionSearch);
/*     */     
/* 283 */     familyForm.addElement(new FormHidden("cmd", "family-editor"));
/*     */ 
/*     */     
/* 286 */     context.putSessionValue("Family", family);
/*     */ 
/*     */     
/* 289 */     if (context.getSessionValue("NOTEPAD_FAMILY_VISIBLE") != null) {
/* 290 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_FAMILY_VISIBLE"));
/*     */     }
/* 292 */     return familyForm;
/*     */   }
/*     */ 
/*     */   
/*     */   private Form buildNewForm(Context context) {
/* 297 */     Form familyForm = new Form(this.application, "FamilyForm", 
/* 298 */         this.application.getInfrastructure().getServletURL(), "POST");
/*     */ 
/*     */     
/* 301 */     FormTextField corporateDescription = new FormTextField("CorporateDescription", "", true, 50, 25);
/* 302 */     corporateDescription.addFormEvent("onBlur", "Javascript:checkField(this);fillAbbreviation()");
/* 303 */     corporateDescription.setTabIndex(1);
/* 304 */     familyForm.addElement(corporateDescription);
/*     */ 
/*     */     
/* 307 */     FormCheckBox active = new FormCheckBox("active", "", true, true);
/* 308 */     active.setTabIndex(3);
/* 309 */     familyForm.addElement(active);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 314 */     FormTextField corporateAbbreviation = new FormTextField("CorporateAbbreviation", "", true, 8, 8);
/* 315 */     corporateAbbreviation.addFormEvent("onBlur", "Javascript:checkField(this)");
/* 316 */     corporateAbbreviation.setTabIndex(2);
/* 317 */     familyForm.addElement(corporateAbbreviation);
/*     */ 
/*     */     
/* 320 */     FormTextField lastUpdated = new FormTextField("lastUpdatedDate", false, 50);
/* 321 */     familyForm.addElement(lastUpdated);
/*     */     
/* 323 */     FormTextField lastUpdatedBy = new FormTextField("lastUpdatedBy", false, 50);
/* 324 */     familyForm.addElement(lastUpdatedBy);
/*     */ 
/*     */     
/* 327 */     FormTextField FamilyDescriptionSearch = new FormTextField("FamilyDescriptionSearch", "", false, 20);
/* 328 */     FamilyDescriptionSearch.setId("FamilyDescriptionSearch");
/* 329 */     familyForm.addElement(FamilyDescriptionSearch);
/*     */     
/* 331 */     familyForm.addElement(new FormHidden("cmd", "family-edit-new"));
/*     */ 
/*     */     
/* 334 */     if (context.getSessionValue("NOTEPAD_FAMILY_VISIBLE") != null) {
/* 335 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_FAMILY_VISIBLE"));
/*     */     }
/* 337 */     return familyForm;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean save(Context context) {
/* 343 */     User user = MilestoneSecurity.getUser(context);
/* 344 */     Notepad notepad = getFamilyNotepad(context, user.getUserId());
/* 345 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*     */     
/* 347 */     Family family = MilestoneHelper.getScreenFamily(context);
/* 348 */     Family timestampFamily = (Family)context.getSessionValue("Family");
/*     */     
/* 350 */     Form form = buildForm(context, family);
/*     */     
/* 352 */     if (timestampFamily == null || (timestampFamily != null && FamilyManager.getInstance().isTimestampValid(timestampFamily))) {
/*     */       
/* 354 */       form.setValues(context);
/*     */ 
/*     */       
/* 357 */       String descriptionString = form.getStringValue("CorporateDescription");
/*     */ 
/*     */       
/* 360 */       String abbreviationString = form.getStringValue("CorporateAbbreviation");
/*     */       
/* 362 */       if (!CorporateStructureManager.getInstance().isDuplicate(descriptionString, 1, family.getStructureID())) {
/*     */ 
/*     */         
/* 365 */         family.setName(descriptionString);
/* 366 */         family.setStructureAbbreviation(abbreviationString);
/*     */         
/* 368 */         family.setActive(((FormCheckBox)form.getElement("active")).isChecked());
/*     */ 
/*     */         
/* 371 */         if (!form.isUnchanged()) {
/*     */           
/* 373 */           FormValidation formValidation = form.validate();
/* 374 */           if (formValidation.isGood())
/*     */           {
/* 376 */             Family savedFamily = FamilyManager.getInstance().saveFamily(family, user.getUserId());
/*     */ 
/*     */             
/* 379 */             FormElement lastUpdated = form.getElement("lastupdateddate");
/*     */             
/* 381 */             if (savedFamily.getLastUpdateDate() != null) {
/* 382 */               lastUpdated.setValue(MilestoneHelper.getLongDate(savedFamily.getLastUpdateDate()));
/*     */             }
/*     */             
/* 385 */             Cache.flushCorporateStructure();
/*     */             
/* 387 */             context.removeSessionValue("user-companies");
/* 388 */             context.removeSessionValue("user-environments");
/*     */ 
/*     */ 
/*     */             
/* 392 */             notepad.setAllContents(null);
/* 393 */             notepad = getFamilyNotepad(context, user.getUserId());
/* 394 */             notepad.setSelected(savedFamily);
/* 395 */             family = (Family)notepad.validateSelected();
/*     */             
/* 397 */             context.putSessionValue("Family", savedFamily);
/*     */             
/* 399 */             if (family == null) {
/* 400 */               return goToBlank(context);
/*     */             }
/* 402 */             form = buildForm(context, family);
/*     */             
/* 404 */             context.putDelivery("Form", form);
/*     */           }
/*     */           else
/*     */           {
/* 408 */             context.putDelivery("FormValidation", formValidation);
/* 409 */             form.addElement(new FormHidden("OrderBy", "", true));
/* 410 */             context.putDelivery("Form", form);
/* 411 */             return context.includeJSP("family-editor.jsp");
/*     */           }
/*     */         
/*     */         } 
/*     */       } else {
/*     */         
/* 417 */         context.putSessionValue("Family", family);
/* 418 */         context.putDelivery("AlertMessage", 
/* 419 */             MilestoneMessage.getMessage(5, 
/*     */               
/* 421 */               new String[] { "Family", descriptionString }));
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 427 */       context.putDelivery("AlertMessage", "The record is unavailable for editing at this time.  Please refresh your screen and edit it at a later time.");
/*     */     } 
/* 429 */     return edit(context);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean delete(Context context) {
/* 436 */     User user = MilestoneSecurity.getUser(context);
/* 437 */     Notepad notepad = getFamilyNotepad(context, user.getUserId());
/* 438 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*     */     
/* 440 */     Family family = MilestoneHelper.getScreenFamily(context);
/*     */ 
/*     */     
/* 443 */     if (family != null) {
/*     */       
/* 445 */       String errorMsg = "";
/* 446 */       errorMsg = CorporateStructureManager.getInstance().delete(family, user.getUserId());
/*     */       
/* 448 */       if (errorMsg != null) {
/*     */         
/* 450 */         context.putDelivery("AlertMessage", errorMsg);
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 455 */         Cache.flushCorporateStructure();
/*     */         
/* 457 */         context.removeSessionValue("user-companies");
/* 458 */         context.removeSessionValue("user-environments");
/*     */ 
/*     */ 
/*     */         
/* 462 */         notepad.setAllContents(null);
/* 463 */         notepad = getFamilyNotepad(context, user.getUserId());
/* 464 */         notepad.setSelected(null);
/*     */       } 
/*     */ 
/*     */       
/* 468 */       if (context.getSessionValue("NOTEPAD_FAMILY_VISIBLE") != null) {
/* 469 */         context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_FAMILY_VISIBLE"));
/*     */       }
/*     */     } 
/* 472 */     return edit(context);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean editNew(Context context) {
/* 481 */     User user = MilestoneSecurity.getUser(context);
/*     */     
/* 483 */     Notepad notepad = getFamilyNotepad(context, user.getUserId());
/* 484 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*     */     
/* 486 */     Form form = buildNewForm(context);
/* 487 */     context.putDelivery("Form", form);
/*     */     
/* 489 */     return context.includeJSP("family-editor.jsp");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean editSaveNew(Context context) {
/* 497 */     User user = MilestoneSecurity.getUser(context);
/*     */ 
/*     */     
/* 500 */     Notepad notepad = getFamilyNotepad(context, user.getUserId());
/* 501 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*     */     
/* 503 */     Family family = new Family();
/* 504 */     Form form = buildNewForm(context);
/* 505 */     form.setValues(context);
/*     */ 
/*     */ 
/*     */     
/* 509 */     family.setStructureID(-2);
/*     */ 
/*     */     
/* 512 */     String descriptionString = form.getStringValue("CorporateDescription");
/*     */ 
/*     */     
/* 515 */     String abbreviationString = form.getStringValue("CorporateAbbreviation");
/*     */     
/* 517 */     if (!CorporateStructureManager.getInstance().isDuplicate(descriptionString, 1, -2)) {
/*     */       
/* 519 */       family.setName(descriptionString);
/* 520 */       family.setStructureAbbreviation(abbreviationString);
/*     */       
/* 522 */       family.setActive(((FormCheckBox)form.getElement("active")).isChecked());
/* 523 */       family.setStructureType(1);
/* 524 */       if (!form.isUnchanged()) {
/*     */         
/* 526 */         FormValidation formValidation = form.validate();
/* 527 */         if (formValidation.isGood())
/*     */         {
/* 529 */           Family savedFamily = FamilyManager.getInstance().saveFamily(family, user.getUserId());
/*     */ 
/*     */           
/* 532 */           FormElement lastUpdated = form.getElement("lastupdateddate");
/* 533 */           if (savedFamily.getLastUpdateDate() != null) {
/* 534 */             lastUpdated.setValue(MilestoneHelper.getLongDate(savedFamily.getLastUpdateDate()));
/*     */           }
/* 536 */           context.putSessionValue("Family", savedFamily);
/* 537 */           context.putDelivery("Form", form);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 542 */           if (notepad.getAllContents() == null || notepad.getAllContents().size() < 1) {
/* 543 */             notepad.setCorporateObjectSearchObj(null);
/*     */           }
/* 545 */           Cache.flushCorporateStructure();
/*     */           
/* 547 */           context.removeSessionValue("user-companies");
/* 548 */           context.removeSessionValue("user-environments");
/*     */ 
/*     */           
/* 551 */           notepad.setAllContents(null);
/* 552 */           notepad.newSelectedReset();
/* 553 */           notepad = getFamilyNotepad(context, user.getUserId());
/* 554 */           notepad.setSelected(savedFamily);
/*     */         }
/*     */         else
/*     */         {
/* 558 */           context.putDelivery("FormValidation", formValidation);
/* 559 */           form.addElement(new FormHidden("OrderBy", "", true));
/* 560 */           context.putDelivery("Form", form);
/* 561 */           return context.includeJSP("division-editor.jsp");
/*     */         }
/*     */       
/*     */       } 
/*     */     } else {
/*     */       
/* 567 */       context.putDelivery("AlertMessage", 
/* 568 */           MilestoneMessage.getMessage(5, 
/*     */             
/* 570 */             new String[] { "Family", descriptionString }));
/*     */     } 
/*     */     
/* 573 */     return edit(context);
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
/*     */   private boolean goToBlank(Context context) {
/* 588 */     context.putDelivery("isNotePadVisible", new Boolean(MilestoneHelper.getNotePadVisiblitiy(9, context)));
/*     */     
/* 590 */     Form form = new Form(this.application, "form", this.application.getInfrastructure().getServletURL(), "Post");
/* 591 */     form.addElement(new FormHidden("cmd", "family-editor"));
/* 592 */     form.addElement(new FormHidden("OrderBy", "", true));
/*     */ 
/*     */     
/* 595 */     FormTextField FamilyDescriptionSearch = new FormTextField("FamilyDescriptionSearch", "", true, 20);
/* 596 */     FamilyDescriptionSearch.setId("FamilyDescriptionSearch");
/* 597 */     form.addElement(FamilyDescriptionSearch);
/*     */     
/* 599 */     context.putDelivery("Form", form);
/*     */     
/* 601 */     return context.includeJSP("blank-family-editor.jsp");
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\FamilyHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */