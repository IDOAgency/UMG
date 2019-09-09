/*      */ package WEB-INF.classes.com.universal.milestone;
/*      */ 
/*      */ import com.techempower.ComponentLog;
/*      */ import com.techempower.gemini.Context;
/*      */ import com.techempower.gemini.Dispatcher;
/*      */ import com.techempower.gemini.FormCheckBox;
/*      */ import com.techempower.gemini.FormDropDownMenu;
/*      */ import com.techempower.gemini.FormElement;
/*      */ import com.techempower.gemini.FormHidden;
/*      */ import com.techempower.gemini.FormPasswordField;
/*      */ import com.techempower.gemini.FormRadioButtonGroup;
/*      */ import com.techempower.gemini.FormTextField;
/*      */ import com.techempower.gemini.FormValidation;
/*      */ import com.techempower.gemini.GeminiApplication;
/*      */ import com.universal.milestone.Acl;
/*      */ import com.universal.milestone.Cache;
/*      */ import com.universal.milestone.Company;
/*      */ import com.universal.milestone.CompanyAcl;
/*      */ import com.universal.milestone.Environment;
/*      */ import com.universal.milestone.Form;
/*      */ import com.universal.milestone.MilestoneConstants;
/*      */ import com.universal.milestone.MilestoneHelper;
/*      */ import com.universal.milestone.MilestoneSecurity;
/*      */ import com.universal.milestone.Notepad;
/*      */ import com.universal.milestone.ReleasingFamily;
/*      */ import com.universal.milestone.SecureHandler;
/*      */ import com.universal.milestone.SecurityHandler;
/*      */ import com.universal.milestone.User;
/*      */ import com.universal.milestone.UserCompaniesTableManager;
/*      */ import com.universal.milestone.UserManager;
/*      */ import com.universal.milestone.UserPreferences;
/*      */ import com.universal.milestone.UserPreferencesHandler;
/*      */ import com.universal.milestone.UserPreferencesManager;
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
/*      */ public class SecurityHandler
/*      */   extends SecureHandler
/*      */ {
/*      */   public static final String COMPONENT_CODE = "hSec";
/*      */   public static final String deptFilterFormat = "department.filter.";
/*      */   public GeminiApplication application;
/*      */   public ComponentLog log;
/*      */   protected long lCopiedUserId;
/*      */   
/*      */   public SecurityHandler(GeminiApplication application) {
/*   61 */     this.lCopiedUserId = 0L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   73 */     this.application = application;
/*   74 */     this.log = application.getLog("hSec");
/*      */ 
/*      */ 
/*      */     
/*   78 */     ReleasingFamily.setDebugLog(this.log);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   86 */   public String getDescription() { return "Security"; }
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
/*   97 */     if (super.acceptRequest(dispatcher, context, command))
/*      */     {
/*   99 */       if (command.startsWith("user-security") || 
/*  100 */         command.startsWith("user-environment-security") || 
/*  101 */         command.startsWith("family") || 
/*  102 */         command.startsWith("company") || 
/*  103 */         command.startsWith("division") || 
/*  104 */         command.startsWith("label"))
/*      */       {
/*  106 */         return handleRequest(dispatcher, context, command);
/*      */       }
/*      */     }
/*      */     
/*  110 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean handleRequest(Dispatcher dispatcher, Context context, String command) {
/*  120 */     if (command.equalsIgnoreCase("user-security-search")) {
/*      */       
/*  122 */       userSecuritySearch(context);
/*      */     }
/*  124 */     else if (command.equalsIgnoreCase("user-security-save")) {
/*      */       
/*  126 */       userSecuritySave(context);
/*      */     }
/*  128 */     else if (command.equalsIgnoreCase("user-security-copy")) {
/*      */       
/*  130 */       userSecurityCopy(context);
/*      */     
/*      */     }
/*  133 */     else if (command.equalsIgnoreCase("user-security-delete")) {
/*      */       
/*  135 */       userSecurityDelete(context);
/*      */     }
/*  137 */     else if (command.equalsIgnoreCase("user-security-search-results")) {
/*      */       
/*  139 */       userSecuritySearchResults(context);
/*      */     }
/*  141 */     else if (command.equalsIgnoreCase("user-security-editor")) {
/*      */       
/*  143 */       userSecurityEditor(context);
/*      */     }
/*  145 */     else if (command.equalsIgnoreCase("user-security-editor-info")) {
/*      */       
/*  147 */       userSecurityEditorInfo(context);
/*      */     
/*      */     }
/*  150 */     else if (command.equalsIgnoreCase("user-security-new")) {
/*      */       
/*  152 */       userSecurityNew(context);
/*      */     }
/*  154 */     else if (command.equalsIgnoreCase("user-security-sort")) {
/*      */       
/*  156 */       userSecuritySort(context);
/*      */     }
/*  158 */     else if (command.equalsIgnoreCase("user-environment-security-search")) {
/*      */       
/*  160 */       userCompanySecuritySearch(context);
/*      */     }
/*  162 */     else if (command.equalsIgnoreCase("user-environment-security-search-results")) {
/*      */       
/*  164 */       userCompanySecuritySearchResults(context);
/*      */     }
/*  166 */     else if (command.equalsIgnoreCase("user-environment-security-editor")) {
/*      */       
/*  168 */       userCompanySecurityEditor(context);
/*      */     }
/*  170 */     else if (command.equalsIgnoreCase("user-environment-security-save")) {
/*      */       
/*  172 */       userCompanySecuritySave(context);
/*      */     }
/*  174 */     else if (command.equalsIgnoreCase("user-environment-security-sort")) {
/*      */       
/*  176 */       userCompanySecuritySort(context);
/*      */     
/*      */     }
/*  179 */     else if (command.equalsIgnoreCase("user-security-save-releasing-family")) {
/*      */       
/*  181 */       userReleasingFamilySave(dispatcher, context);
/*      */     } 
/*      */     
/*  184 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean userSecuritySearch(Context context) {
/*  194 */     Notepad notepad = MilestoneHelper.getNotepadFromSession(7, context);
/*  195 */     UserManager.getInstance().setUserNotepadQuery(context, notepad);
/*      */ 
/*      */ 
/*      */     
/*  199 */     notepad.setAllContents(null);
/*  200 */     notepad.setSelected(null);
/*      */     
/*  202 */     userSecurityEditor(context);
/*      */ 
/*      */     
/*  205 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*  210 */   private boolean userSecuritySearchResults(Context context) { return context.includeJSP("user-security-search-results.jsp"); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean userSecurityEditor(Context context) {
/*  221 */     context.removeSessionValue("copiedUserObj");
/*      */     
/*  223 */     Notepad notepad = getUsersNotepad(context);
/*  224 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*  225 */     context.removeSessionValue(NOTEPAD_SESSION_NAMES[21]);
/*  226 */     User user = MilestoneHelper.getScreenUser(context);
/*      */     
/*  228 */     context.putSessionValue("userInfo", "false");
/*  229 */     this.lCopiedUserId = 0L;
/*  230 */     if (user != null) {
/*      */       
/*  232 */       Form form = null;
/*      */       
/*  234 */       Form formUserPrefs = null;
/*  235 */       UserPreferencesHandler uph = new UserPreferencesHandler(this.application);
/*      */       
/*  237 */       if (user != null) {
/*      */         
/*  239 */         form = buildForm(context, user);
/*  240 */         formUserPrefs = uph.buildForm(context, user);
/*      */       } else {
/*      */         
/*  243 */         form = buildNewForm(context);
/*  244 */         notepad.setSwitchToCompaniesVisible(false);
/*  245 */         formUserPrefs = uph.buildNewForm(context);
/*      */       } 
/*      */       
/*  248 */       return context.includeJSP("user-security-editor.jsp");
/*      */     } 
/*      */ 
/*      */     
/*  252 */     notepad.setSwitchToCompaniesVisible(false);
/*  253 */     return userGoToBlank(context);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean userSecurityEditorInfo(Context context) {
/*  264 */     Notepad notepad = getUsersNotepad(context);
/*  265 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*  266 */     context.removeSessionValue(NOTEPAD_SESSION_NAMES[21]);
/*      */ 
/*      */     
/*  269 */     User user = (User)context.getSessionValue("user");
/*  270 */     context.putSessionValue("securityUser", user);
/*  271 */     notepad.setSelected(user);
/*      */     
/*  273 */     context.putSessionValue("userInfo", "true");
/*  274 */     this.lCopiedUserId = 0L;
/*  275 */     if (user != null) {
/*      */       
/*  277 */       Form form = null;
/*      */ 
/*      */       
/*  280 */       Form formUserPrefs = null;
/*  281 */       UserPreferencesHandler uph = new UserPreferencesHandler(this.application);
/*      */       
/*  283 */       if (user != null) {
/*      */         
/*  285 */         form = buildForm(context, user);
/*  286 */         formUserPrefs = uph.buildForm(context, user);
/*      */       }
/*      */       else {
/*      */         
/*  290 */         form = buildNewForm(context);
/*  291 */         formUserPrefs = uph.buildNewForm(context);
/*  292 */         notepad.setSwitchToCompaniesVisible(false);
/*      */       } 
/*      */       
/*  295 */       return context.includeJSP("user-security-editor.jsp");
/*      */     } 
/*      */ 
/*      */     
/*  299 */     notepad.setSwitchToCompaniesVisible(false);
/*  300 */     return userGoToBlank(context);
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
/*      */   private boolean userSecurityCopy(Context context) {
/*  313 */     User users = MilestoneSecurity.getUser(context);
/*  314 */     User sessionUser = (User)context.getSessionValue("securityUser");
/*  315 */     User user = MilestoneHelper.getScreenUser(context);
/*      */     
/*  317 */     Acl acl = user.getAcl();
/*  318 */     String aclString = user.getAclString();
/*  319 */     String filter = user.getFilter();
/*  320 */     String location = user.getLocation();
/*      */ 
/*      */ 
/*      */     
/*  324 */     User copiedUserObj = null;
/*  325 */     context.removeSessionValue("copiedUserObj");
/*      */     
/*      */     try {
/*  328 */       copiedUserObj = (User)user.clone();
/*  329 */       context.putSessionValue("copiedUserObj", copiedUserObj);
/*      */     }
/*  331 */     catch (CloneNotSupportedException cloneNotSupportedException) {}
/*      */ 
/*      */     
/*  334 */     int userId = user.getUserId();
/*  335 */     user.setUserId(-1);
/*      */     
/*  337 */     this.lCopiedUserId = userId;
/*      */     
/*  339 */     String reportsTo = user.getReportsTo();
/*  340 */     int employedBy = user.getEmployedBy();
/*      */     
/*  342 */     Notepad notepad = getUsersNotepad(context);
/*  343 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */     
/*  345 */     User copiedUser = new User();
/*      */ 
/*      */     
/*      */     try {
/*  349 */       copiedUser = (User)user.clone();
/*      */     }
/*  351 */     catch (CloneNotSupportedException cloneNotSupportedException) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  361 */     copiedUser.setPassword("");
/*      */     
/*  363 */     copiedUser.setReportsTo(reportsTo);
/*  364 */     copiedUser.setName(null);
/*      */     
/*  366 */     copiedUser.setLocation(location);
/*      */ 
/*      */     
/*  369 */     copiedUser.setEmployedBy(employedBy);
/*      */ 
/*      */     
/*  372 */     copiedUser.setReleasingFamily(sessionUser.getReleasingFamily());
/*  373 */     copiedUser.setAssignedEnvironments(sessionUser.getAssignedEnvironments());
/*      */ 
/*      */     
/*  376 */     copiedUser.setFilter(filter);
/*      */ 
/*      */     
/*  379 */     String[] filterArray = MilestoneHelper.parseFilter(filter);
/*      */ 
/*      */     
/*  382 */     copiedUser.setAclString(aclString);
/*  383 */     copiedUser.setAcl(acl);
/*      */     
/*  385 */     Form form = null;
/*      */     
/*  387 */     copiedUser.setEmail("");
/*  388 */     copiedUser.setFax("");
/*  389 */     copiedUser.setPhone("");
/*  390 */     form = buildCopyForm(context, copiedUser, userId);
/*  391 */     form.addElement(new FormHidden("cmd", "user-security-copy"));
/*      */     
/*  393 */     copiedUser.setUserId(-1);
/*  394 */     copiedUser.setLogin("");
/*  395 */     context.putSessionValue("User", copiedUser);
/*  396 */     context.putDelivery("Form", form);
/*      */ 
/*      */     
/*  399 */     Form formUserPrefs = null;
/*  400 */     UserPreferencesHandler uph = new UserPreferencesHandler(this.application);
/*  401 */     formUserPrefs = uph.buildNewForm(context);
/*      */ 
/*      */     
/*  404 */     if (context.getSessionValue("NOTEPAD_USER_VISIBLE") != null) {
/*  405 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_USER_VISIBLE"));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  411 */     return context.includeJSP("user-security-editor.jsp");
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
/*      */   private boolean userSecuritySave(Context context) {
/*  427 */     User sessionUser = MilestoneSecurity.getUser(context);
/*  428 */     User user = (User)context.getSessionValue("securityUser");
/*      */     
/*  430 */     boolean isNewUser = false;
/*      */     
/*  432 */     if (user == null) {
/*      */       
/*  434 */       user = new User();
/*  435 */       isNewUser = true;
/*      */     } 
/*      */ 
/*      */     
/*  439 */     boolean isRelFamilyNewUser = false;
/*  440 */     if (isNewUser || user.getUserId() == -1) {
/*  441 */       isRelFamilyNewUser = true;
/*      */     }
/*  443 */     Form form = buildForm(context, user);
/*      */ 
/*      */     
/*  446 */     UserPreferencesHandler uph = new UserPreferencesHandler(this.application);
/*  447 */     Form userPrefForm = uph.buildForm(context, user);
/*      */ 
/*      */     
/*  450 */     if (UserManager.getInstance().isTimestampValid(user)) {
/*      */       
/*  452 */       form.setValues(context);
/*  453 */       userPrefForm.setValues(context);
/*      */       
/*  455 */       Acl acl = user.getAcl();
/*  456 */       Vector cAcl = acl.getCompanyAcl();
/*      */ 
/*      */ 
/*      */       
/*  460 */       String login = form.getStringValue("login");
/*  461 */       user.setLogin(login);
/*      */ 
/*      */       
/*  464 */       String password = form.getStringValue("password");
/*  465 */       user.setPassword(password);
/*      */ 
/*      */       
/*  468 */       String reportto = form.getStringValue("reportto");
/*  469 */       user.setReportsTo(reportto);
/*      */ 
/*      */       
/*  472 */       String name = form.getStringValue("fullname");
/*  473 */       user.setName(name);
/*      */ 
/*      */       
/*  476 */       String location = form.getStringValue("location");
/*  477 */       user.setLocation(location);
/*      */ 
/*      */       
/*  480 */       String employedby = form.getStringValue("employedby");
/*      */       
/*  482 */       user.setEmployedBy(Integer.parseInt(employedby));
/*      */ 
/*      */ 
/*      */       
/*  486 */       user.setEmail(form.getStringValue("email"));
/*  487 */       user.setPhone(form.getStringValue("phone"));
/*  488 */       user.setFax(form.getStringValue("fax"));
/*  489 */       user.setInactive(((FormCheckBox)form.getElement("inactive")).isChecked() ? -1 : 0);
/*      */       
/*  491 */       user.setAdministrator(((FormCheckBox)form.getElement("administrator")).isChecked() ? 1 : 0);
/*  492 */       String department = form.getStringValue("deptFilter");
/*  493 */       String deptFilterFlag = form.getStringValue("IsModifyDept");
/*  494 */       String deptFilter = deptFilterFlag.equalsIgnoreCase("Yes") ? ("trueßdepartment.filter." + department) : ("falseßdepartment.filter." + department);
/*  495 */       user.setDeptFilter(deptFilter);
/*  496 */       context.putSessionValue("deptFilterFlag", deptFilterFlag);
/*  497 */       context.putSessionValue("deptFilter", department);
/*      */ 
/*      */ 
/*      */       
/*  501 */       String filter = form.getStringValue("filter");
/*  502 */       String filterFlag = form.getStringValue("IsModify");
/*      */       
/*  504 */       if (filter.equalsIgnoreCase("All")) {
/*      */         
/*  506 */         filter = filterFlag.equalsIgnoreCase("Yes") ? "trueßmilestone.filter.FilterNone" : "falseßmilestone.filter.FilterNone";
/*      */       }
/*  508 */       else if (filter.equalsIgnoreCase("Only Label Tasks")) {
/*      */         
/*  510 */         filter = filterFlag.equalsIgnoreCase("Yes") ? "trueßmilestone.filter.FilterExcludeOwnerUML" : "falseßmilestone.filter.FilterExcludeOwnerUML";
/*      */       }
/*  512 */       else if (filter.equalsIgnoreCase("Only UML Tasks")) {
/*      */         
/*  514 */         filter = filterFlag.equalsIgnoreCase("Yes") ? "trueßmilestone.filter.FilterIncludeOwnerUML" : "falseßmilestone.filter.FilterIncludeOwnerUML";
/*      */       } 
/*  516 */       user.setFilter(filter);
/*      */ 
/*      */       
/*  519 */       String[] filterArray = MilestoneHelper.parseFilter(filter);
/*      */       
/*  521 */       context.putSessionValue("filterFlag", filterArray[0]);
/*  522 */       context.putSessionValue("filter", filterArray[1]);
/*      */ 
/*      */       
/*  525 */       Vector checkedEnvironments = new Vector();
/*  526 */       Vector environments = Cache.getInstance().getEnvironments();
/*      */       
/*  528 */       if (environments != null) {
/*      */         
/*  530 */         this.log.log("envoroments no null");
/*  531 */         this.log.log("--" + environments.size());
/*  532 */         for (int i = 0; i < environments.size(); i++) {
/*      */ 
/*      */           
/*  535 */           Environment environment = (Environment)environments.get(i);
/*  536 */           this.log.log("--" + environment.getName());
/*  537 */           if (environment != null && form.getElement("ue" + environment.getStructureID()) != null) {
/*      */             
/*  539 */             this.log.log("check box ue" + environment.getStructureID());
/*  540 */             if (((FormCheckBox)form.getElement("ue" + environment.getStructureID())).isChecked()) {
/*      */               
/*  542 */               this.log.log("check box ue" + environment.getStructureID() + "  checked");
/*  543 */               checkedEnvironments.add(environment);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  549 */       this.log.log("--" + checkedEnvironments.size());
/*      */ 
/*      */ 
/*      */       
/*  553 */       String selectionValue = form.getStringValue("1");
/*  554 */       boolean selectionBool = false;
/*      */       
/*  556 */       if (selectionValue.equalsIgnoreCase("Available"))
/*      */       {
/*  558 */         selectionBool = true;
/*      */       }
/*  560 */       acl.setAccessSelection(selectionBool);
/*      */ 
/*      */       
/*  563 */       String scheduleValue = form.getStringValue("2");
/*  564 */       boolean scheduleBool = false;
/*      */       
/*  566 */       if (scheduleValue.equalsIgnoreCase("Available"))
/*      */       {
/*  568 */         scheduleBool = true;
/*      */       }
/*  570 */       acl.setAccessSchedule(scheduleBool);
/*      */ 
/*      */       
/*  573 */       String manufacturingValue = form.getStringValue("3");
/*  574 */       boolean manufacturingBool = false;
/*      */       
/*  576 */       if (manufacturingValue.equalsIgnoreCase("Available"))
/*      */       {
/*  578 */         manufacturingBool = true;
/*      */       }
/*  580 */       acl.setAccessManufacturing(manufacturingBool);
/*      */ 
/*      */       
/*  583 */       String pfmValue = form.getStringValue("4");
/*  584 */       boolean pfmBool = false;
/*      */       
/*  586 */       if (pfmValue.equalsIgnoreCase("Available"))
/*      */       {
/*  588 */         pfmBool = true;
/*      */       }
/*  590 */       acl.setAccessPfmForm(pfmBool);
/*      */ 
/*      */       
/*  593 */       String bomValue = form.getStringValue("5");
/*  594 */       boolean bomBool = false;
/*      */       
/*  596 */       if (bomValue.equalsIgnoreCase("Available"))
/*      */       {
/*  598 */         bomBool = true;
/*      */       }
/*  600 */       acl.setAccessBomForm(bomBool);
/*      */ 
/*      */       
/*  603 */       String reportValue = form.getStringValue("6");
/*  604 */       boolean reportBool = false;
/*      */       
/*  606 */       if (reportValue.equalsIgnoreCase("Available"))
/*      */       {
/*  608 */         reportBool = true;
/*      */       }
/*  610 */       acl.setAccessReport(reportBool);
/*      */ 
/*      */       
/*  613 */       String templateValue = form.getStringValue("7");
/*  614 */       boolean templateBool = false;
/*      */       
/*  616 */       if (templateValue.equalsIgnoreCase("Available"))
/*      */       {
/*  618 */         templateBool = true;
/*      */       }
/*  620 */       acl.setAccessTemplate(templateBool);
/*      */ 
/*      */       
/*  623 */       String taskValue = form.getStringValue("8");
/*  624 */       boolean taskBool = false;
/*      */       
/*  626 */       if (taskValue.equalsIgnoreCase("Available"))
/*      */       {
/*  628 */         taskBool = true;
/*      */       }
/*  630 */       acl.setAccessTask(taskBool);
/*      */ 
/*      */       
/*  633 */       String dayValue = form.getStringValue("9");
/*  634 */       boolean dayBool = false;
/*      */       
/*  636 */       if (dayValue.equalsIgnoreCase("Available"))
/*      */       {
/*  638 */         dayBool = true;
/*      */       }
/*  640 */       acl.setAccessDayType(dayBool);
/*      */ 
/*      */       
/*  643 */       String userValue = form.getStringValue("10");
/*  644 */       boolean userBool = false;
/*      */       
/*  646 */       if (userValue.equalsIgnoreCase("Available"))
/*      */       {
/*  648 */         userBool = true;
/*      */       }
/*  650 */       acl.setAccessUser(userBool);
/*      */ 
/*      */       
/*  653 */       String familyValue = form.getStringValue("11");
/*  654 */       boolean familyBool = false;
/*      */       
/*  656 */       if (familyValue.equalsIgnoreCase("Available"))
/*      */       {
/*  658 */         familyBool = true;
/*      */       }
/*  660 */       acl.setAccessFamily(familyBool);
/*      */ 
/*      */       
/*  663 */       String environmentValue = form.getStringValue("20");
/*  664 */       boolean environmentBool = false;
/*  665 */       if (environmentValue.equalsIgnoreCase("Available"))
/*      */       {
/*  667 */         environmentBool = true;
/*      */       }
/*      */       
/*  670 */       acl.setAccessEnvironment(environmentBool);
/*      */ 
/*      */       
/*  673 */       String companyValue = form.getStringValue("12");
/*  674 */       boolean companyBool = false;
/*      */       
/*  676 */       if (companyValue.equalsIgnoreCase("Available"))
/*      */       {
/*  678 */         companyBool = true;
/*      */       }
/*      */       
/*  681 */       acl.setAccessCompany(companyBool);
/*      */ 
/*      */       
/*  684 */       String divisionValue = form.getStringValue("13");
/*  685 */       boolean divisionBool = false;
/*      */       
/*  687 */       if (divisionValue.equalsIgnoreCase("Available"))
/*      */       {
/*  689 */         divisionBool = true;
/*      */       }
/*  691 */       acl.setAccessDivision(divisionBool);
/*      */ 
/*      */       
/*  694 */       String labelValue = form.getStringValue("14");
/*  695 */       boolean labelBool = false;
/*      */       
/*  697 */       if (labelValue.equalsIgnoreCase("Available"))
/*      */       {
/*  699 */         labelBool = true;
/*      */       }
/*  701 */       acl.setAccessLabel(labelBool);
/*      */ 
/*      */       
/*  704 */       String tableValue = form.getStringValue("15");
/*  705 */       boolean tableBool = false;
/*      */       
/*  707 */       if (tableValue.equalsIgnoreCase("Available"))
/*      */       {
/*  709 */         tableBool = true;
/*      */       }
/*  711 */       acl.setAccessTable(tableBool);
/*      */ 
/*      */       
/*  714 */       String parameterValue = form.getStringValue("16");
/*  715 */       boolean parameterBool = false;
/*      */       
/*  717 */       if (parameterValue.equalsIgnoreCase("Available"))
/*      */       {
/*  719 */         parameterBool = true;
/*      */       }
/*  721 */       acl.setAccessParameter(parameterBool);
/*      */ 
/*      */       
/*  724 */       String auditValue = form.getStringValue("17");
/*  725 */       boolean auditBool = false;
/*      */       
/*  727 */       if (auditValue.equalsIgnoreCase("Available"))
/*      */       {
/*  729 */         auditBool = true;
/*      */       }
/*  731 */       acl.setAccessAuditTrail(auditBool);
/*      */ 
/*      */       
/*  734 */       String configValue = form.getStringValue("18");
/*  735 */       boolean configBool = false;
/*      */       
/*  737 */       if (configValue.equalsIgnoreCase("Available"))
/*      */       {
/*  739 */         configBool = true;
/*      */       }
/*  741 */       acl.setAccessReportConfig(configBool);
/*      */ 
/*      */       
/*  744 */       String priceValue = form.getStringValue("19");
/*  745 */       boolean priceBool = false;
/*      */       
/*  747 */       if (priceValue.equalsIgnoreCase("Available"))
/*      */       {
/*  749 */         priceBool = true;
/*      */       }
/*  751 */       acl.setAccessPriceCode(priceBool);
/*      */ 
/*      */ 
/*      */       
/*  755 */       UserPreferences up = new UserPreferences();
/*      */ 
/*      */       
/*  758 */       String openingScreen = userPrefForm.getStringValue("openingScreen");
/*  759 */       up.setOpeningScreen(Integer.parseInt(openingScreen));
/*      */       
/*  761 */       String autoCloseRadio = userPrefForm.getStringValue("autoCloseRadio");
/*  762 */       up.setAutoClose(Integer.parseInt(autoCloseRadio));
/*      */       
/*  764 */       String autoCloseDays = userPrefForm.getStringValue("autoCloseDays");
/*  765 */       up.setAutoCloseDays(Integer.parseInt(autoCloseDays));
/*      */ 
/*      */       
/*  768 */       String generalSortBy = userPrefForm.getStringValue("sortBy");
/*  769 */       up.setNotepadSortBy(Integer.parseInt(generalSortBy));
/*      */       
/*  771 */       String generalOrder = userPrefForm.getStringValue("order");
/*  772 */       up.setNotepadOrder(Integer.parseInt(generalOrder));
/*      */       
/*  774 */       String generalProdType = userPrefForm.getStringValue("productType");
/*  775 */       up.setNotepadProductType(Integer.parseInt(generalProdType));
/*      */ 
/*      */       
/*  778 */       String selectionReleasingFamilies = userPrefForm.getStringValue("releasingFamilies");
/*  779 */       up.setSelectionReleasingFamily(Integer.parseInt(selectionReleasingFamilies));
/*      */       
/*  781 */       String selectionEnvironment = userPrefForm.getStringValue("envMenu");
/*  782 */       up.setSelectionEnvironment(Integer.parseInt(selectionEnvironment));
/*      */       
/*  784 */       String selectionContactList = userPrefForm.getStringValue("ContactList");
/*  785 */       up.setSelectionLabelContact(Integer.parseInt(selectionContactList));
/*      */       
/*  787 */       String selectionProductType = userPrefForm.getStringValue("selectionProductType");
/*  788 */       up.setSelectionProductType(Integer.parseInt(selectionProductType));
/*      */       
/*  790 */       FormCheckBox selectionStatus = (FormCheckBox)userPrefForm.getElement("status");
/*  791 */       up.setSelectionStatus(0);
/*  792 */       if (selectionStatus.isChecked()) {
/*  793 */         up.setSelectionStatus(1);
/*      */       }
/*  795 */       FormCheckBox selectionPriorCriteria = (FormCheckBox)userPrefForm.getElement("priorCriteria");
/*  796 */       up.setSelectionPriorCriteria(0);
/*  797 */       if (selectionPriorCriteria.isChecked()) {
/*  798 */         up.setSelectionPriorCriteria(1);
/*      */       }
/*      */       
/*  801 */       String sortBySchedule = userPrefForm.getStringValue("sortBySchedule");
/*  802 */       up.setSchedulePhysicalSortBy(Integer.parseInt(sortBySchedule));
/*      */       
/*  804 */       String ownerSchedule = userPrefForm.getStringValue("ownerSchedule");
/*  805 */       up.setSchedulePhysicalOwner(Integer.parseInt(ownerSchedule));
/*      */ 
/*      */       
/*  808 */       String sortByDigitalSchedule = userPrefForm.getStringValue("sortByDigitalSchedule");
/*  809 */       up.setScheduleDigitalSortBy(Integer.parseInt(sortByDigitalSchedule));
/*      */       
/*  811 */       String ownerDigitalSchedule = userPrefForm.getStringValue("ownerDigitalSchedule");
/*  812 */       up.setScheduleDigitalOwner(Integer.parseInt(ownerDigitalSchedule));
/*      */ 
/*      */       
/*  815 */       String releaseType = userPrefForm.getStringValue("releaseType");
/*  816 */       up.setReportsReleaseType(Integer.parseInt(releaseType));
/*      */       
/*  818 */       String releasingFamiliesReports = userPrefForm.getStringValue("releasingFamiliesReports");
/*  819 */       up.setReportsReleasingFamily(Integer.parseInt(releasingFamiliesReports));
/*      */       
/*  821 */       String envMenuReports = userPrefForm.getStringValue("envMenuReports");
/*  822 */       up.setReportsEnvironment(Integer.parseInt(envMenuReports));
/*      */       
/*  824 */       String ContactListReports = userPrefForm.getStringValue("ContactListReports");
/*  825 */       up.setReportsLabelContact(Integer.parseInt(ContactListReports));
/*      */       
/*  827 */       String umlContact = userPrefForm.getStringValue("umlContact");
/*  828 */       up.setReportsUMLContact(Integer.parseInt(umlContact));
/*      */       
/*  830 */       FormCheckBox spAll = (FormCheckBox)userPrefForm.getElement("statusReportsAll");
/*  831 */       FormCheckBox spActive = (FormCheckBox)userPrefForm.getElement("statusReportsActive");
/*  832 */       FormCheckBox spTBS = (FormCheckBox)userPrefForm.getElement("statusReportsTBS");
/*  833 */       FormCheckBox spClosed = (FormCheckBox)userPrefForm.getElement("statusReportsClosed");
/*  834 */       FormCheckBox spCancelled = (FormCheckBox)userPrefForm.getElement("statusReportsCancelled");
/*      */       
/*  836 */       up.setReportsStatusAll(0);
/*  837 */       if (spAll.isChecked()) {
/*  838 */         up.setReportsStatusAll(1);
/*      */       }
/*  840 */       up.setReportsStatusActive(0);
/*  841 */       if (spActive.isChecked()) {
/*  842 */         up.setReportsStatusActive(1);
/*      */       }
/*  844 */       up.setReportsStatusTBS(0);
/*  845 */       if (spTBS.isChecked()) {
/*  846 */         up.setReportsStatusTBS(1);
/*      */       }
/*  848 */       up.setReportsStatusClosed(0);
/*  849 */       if (spClosed.isChecked()) {
/*  850 */         up.setReportsStatusClosed(1);
/*      */       }
/*  852 */       up.setReportsStatusCancelled(0);
/*  853 */       if (spCancelled.isChecked()) {
/*  854 */         up.setReportsStatusCancelled(1);
/*      */       }
/*  856 */       user.setPreferences(up);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  861 */       if (!UserManager.getInstance().isDuplicate(user))
/*      */       {
/*      */         
/*  864 */         if (!form.isUnchanged()) {
/*      */           
/*  866 */           FormValidation formValidation = form.validate();
/*  867 */           if (formValidation.isGood()) {
/*      */ 
/*      */             
/*  870 */             Vector checkedCompanies = new Vector();
/*  871 */             this.log.log("checked companies");
/*      */             
/*  873 */             for (int j = 0; j < checkedEnvironments.size(); j++) {
/*      */               
/*  875 */               Environment currentEnvironment = (Environment)checkedEnvironments.elementAt(j);
/*      */               
/*  877 */               Vector environmentCompanies = currentEnvironment.getChildren();
/*  878 */               this.log.log("envoronments comanies for " + environmentCompanies.size());
/*  879 */               for (int k = 0; k < environmentCompanies.size(); k++) {
/*      */                 
/*  881 */                 Company envCompany = (Company)environmentCompanies.elementAt(k);
/*  882 */                 this.log.log("envoronments comanies for " + envCompany.getName());
/*      */                 
/*  884 */                 if (!checkedCompanies.contains(envCompany))
/*      */                 {
/*  886 */                   checkedCompanies.add(envCompany);
/*      */                 }
/*      */               } 
/*      */             } 
/*  890 */             this.log.log("enviroments conmpanies number" + checkedCompanies.size());
/*      */             
/*  892 */             if (user == null)
/*  893 */               this.log.log("User null"); 
/*  894 */             if (sessionUser == null)
/*  895 */               this.log.log("sessionUser null"); 
/*  896 */             if (checkedCompanies == null) {
/*  897 */               this.log.log("checked companies is null");
/*      */             }
/*  899 */             User savedUser = UserManager.getInstance().save(user, sessionUser, checkedCompanies, context);
/*      */ 
/*      */             
/*  902 */             UserPreferencesManager.getInstance().savePreferences(user, context);
/*  903 */             if (savedUser == null) {
/*  904 */               this.log.log("saved user null");
/*      */             }
/*  906 */             form = buildForm(context, savedUser);
/*  907 */             userPrefForm = uph.buildForm(context, savedUser);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  916 */             FormElement lastUpdated = form.getElement("lastupdateddate");
/*  917 */             if (savedUser.getLastUpdateDate() != null) {
/*  918 */               lastUpdated.setValue(MilestoneHelper.getLongDate(savedUser.getLastUpdateDate()));
/*      */             }
/*      */             
/*  921 */             savedUser = UserManager.getInstance().getUser(savedUser.getUserId(), true);
/*      */             
/*  923 */             context.putSessionValue("securityUser", savedUser);
/*      */ 
/*      */             
/*  926 */             if (savedUser.getUserId() == sessionUser.getUserId()) {
/*      */               
/*  928 */               context.removeSessionValue("user");
/*      */ 
/*      */               
/*  931 */               context.removeSessionValue("user-companies");
/*  932 */               context.removeSessionValue("user-environments");
/*      */               
/*  934 */               context.putSessionValue("UserDefaultsApplied", "true");
/*  935 */               context.putSessionValue("user", savedUser);
/*      */ 
/*      */ 
/*      */               
/*  939 */               makeDynamic(savedUser, context, sessionUser);
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  946 */             UserCompaniesTableManager.getInstance().setUpdateFlag(savedUser.getUserId(), true);
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  951 */             Cache.flushCachedVariableAllUsers(savedUser.getUserId());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  960 */             Notepad notepad = getUsersNotepad(context);
/*  961 */             notepad.setAllContents(null);
/*      */             
/*  963 */             if (isNewUser)
/*      */             {
/*  965 */               notepad.newSelectedReset();
/*  966 */               notepad = getUsersNotepad(context);
/*  967 */               notepad.setSelected(savedUser);
/*  968 */               notepad = getUsersNotepad(context);
/*      */             }
/*      */             else
/*      */             {
/*  972 */               notepad = getUsersNotepad(context);
/*  973 */               notepad.setSelected(savedUser);
/*  974 */               user = (User)notepad.validateSelected();
/*      */               
/*  976 */               if (user == null) {
/*      */                 
/*  978 */                 notepad.setSwitchToCompaniesVisible(false);
/*  979 */                 return userGoToBlank(context);
/*      */               } 
/*      */ 
/*      */               
/*  983 */               form = buildForm(context, user);
/*  984 */               userPrefForm = uph.buildForm(context, user);
/*      */             
/*      */             }
/*      */           
/*      */           }
/*      */           else {
/*      */             
/*  991 */             context.putDelivery("FormValidation", formValidation);
/*      */           } 
/*      */         } 
/*      */         
/*  995 */         context.removeSessionValue(NOTEPAD_SESSION_NAMES[3]);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1000 */         if (isRelFamilyNewUser) {
/* 1001 */           context.putSessionValue("showAssigned", "ASSIGNED");
/*      */         
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1007 */         Notepad notepad = getUsersNotepad(context);
/* 1008 */         context.putDelivery("AlertMessage", "Duplicate.");
/* 1009 */         user = (User)notepad.validateSelected();
/* 1010 */         form = buildForm(context, user);
/* 1011 */         userPrefForm = uph.buildForm(context, user);
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 1017 */       context.putDelivery("AlertMessage", "The record is unavailable for editing at this time.  Please refresh your screen and edit it at a later time.");
/*      */     } 
/*      */ 
/*      */     
/* 1021 */     context.putDelivery("Form", form);
/* 1022 */     this.lCopiedUserId = 0L;
/* 1023 */     return context.includeJSP("user-security-editor.jsp");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean userSecurityDelete(Context context) {
/* 1032 */     User sessionUser = MilestoneSecurity.getUser(context);
/*      */     
/* 1034 */     Notepad notepad = getUsersNotepad(context);
/* 1035 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */     
/* 1037 */     User user = MilestoneHelper.getScreenUser(context);
/*      */ 
/*      */     
/* 1040 */     if (user != null) {
/*      */       
/* 1042 */       UserManager.getInstance().deleteUser(user, sessionUser);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1047 */       Cache.flushCachedVariableAllUsers(user.getUserId());
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1052 */       notepad.setAllContents(null);
/* 1053 */       notepad.setSelected(null);
/*      */     } 
/*      */     
/* 1056 */     return userSecurityEditor(context);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean userSecurityNew(Context context) {
/* 1066 */     Notepad notepad = getUsersNotepad(context);
/* 1067 */     notepad.setSelected(null);
/* 1068 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/* 1069 */     context.removeSessionValue(NOTEPAD_SESSION_NAMES[21]);
/* 1070 */     context.removeSessionValue("securityUser");
/* 1071 */     context.removeSessionValue("User");
/* 1072 */     Form form = buildNewForm(context);
/*      */ 
/*      */     
/* 1075 */     Form formUserPrefs = null;
/* 1076 */     UserPreferencesHandler uph = new UserPreferencesHandler(this.application);
/* 1077 */     formUserPrefs = uph.buildNewForm(context);
/*      */     
/* 1079 */     notepad.setSwitchToCompaniesVisible(false);
/* 1080 */     return context.includeJSP("user-security-editor.jsp");
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
/*      */   private Form buildCopyForm(Context context, User user, int userId) {
/* 1096 */     context.putSessionValue("showAssigned", "NEW");
/*      */ 
/*      */     
/* 1099 */     Form userSecurityForm = new Form(this.application, "userSecurityForm", 
/* 1100 */         this.application.getInfrastructure().getServletURL(), "POST");
/*      */     
/* 1102 */     Acl acl = user.getAcl();
/*      */     
/* 1104 */     Vector cAcl = null;
/*      */     
/* 1106 */     if (acl != null) {
/* 1107 */       acl.getCompanyAcl();
/*      */     }
/*      */     
/* 1110 */     FormTextField login = new FormTextField("login", "", true, 30, 100);
/* 1111 */     login.setTabIndex(1);
/* 1112 */     userSecurityForm.addElement(login);
/*      */ 
/*      */     
/* 1115 */     FormPasswordField password = new FormPasswordField("password", user.getPassword(), true, 30, 30);
/* 1116 */     password.setTabIndex(2);
/* 1117 */     userSecurityForm.addElement(password);
/*      */ 
/*      */     
/* 1120 */     String reportsToString = "";
/* 1121 */     if (user.getReportsTo() != null) {
/* 1122 */       reportsToString = user.getReportsTo();
/*      */     }
/* 1124 */     FormTextField reportto = new FormTextField("reportto", reportsToString, false, 30, 30);
/* 1125 */     reportto.setTabIndex(3);
/* 1126 */     userSecurityForm.addElement(reportto);
/*      */ 
/*      */ 
/*      */     
/* 1130 */     FormTextField fullname = new FormTextField("fullname", user.getName(), true, 30, 100);
/* 1131 */     fullname.setTabIndex(4);
/* 1132 */     userSecurityForm.addElement(fullname);
/*      */ 
/*      */     
/* 1135 */     String locationString = "";
/* 1136 */     locationString = user.getLocation();
/* 1137 */     Vector userLocations = Cache.getLookupDetailValuesFromDatabase(53);
/* 1138 */     FormDropDownMenu location = MilestoneHelper.getLookupDropDown("location", userLocations, locationString, false, true);
/* 1139 */     location.setTabIndex(5);
/* 1140 */     userSecurityForm.addElement(location);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1145 */     FormTextField email = new FormTextField("email", user.getEmail(), false, 30, 50);
/* 1146 */     email.setTabIndex(6);
/* 1147 */     userSecurityForm.addElement(email);
/*      */     
/* 1149 */     FormTextField phone = new FormTextField("phone", user.getPhone(), false, 30, 30);
/* 1150 */     phone.setTabIndex(7);
/* 1151 */     userSecurityForm.addElement(phone);
/*      */     
/* 1153 */     FormTextField fax = new FormTextField("fax", user.getFax(), false, 30, 30);
/* 1154 */     fax.setTabIndex(8);
/* 1155 */     userSecurityForm.addElement(fax);
/*      */     
/* 1157 */     Vector families = Cache.getInstance().getFamilies();
/*      */     
/* 1159 */     String employedByString = "";
/* 1160 */     if (user.getEmployedBy() > 0)
/*      */     {
/* 1162 */       employedByString = String.valueOf(user.getEmployedBy());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1167 */     FormDropDownMenu employedby = MilestoneHelper.getCorporateStructureDropDown("employedby", families, employedByString, true, true);
/* 1168 */     employedby.setTabIndex(9);
/* 1169 */     userSecurityForm.addElement(employedby);
/*      */ 
/*      */     
/* 1172 */     FormCheckBox inactive = new FormCheckBox("inactive", "Inactive", false, !(user.getInactive() == 0));
/* 1173 */     inactive.setId("inactive");
/* 1174 */     inactive.setTabIndex(11);
/* 1175 */     userSecurityForm.addElement(inactive);
/*      */ 
/*      */     
/* 1178 */     FormCheckBox administrator = new FormCheckBox("administrator", "Administrator", false, !(user.getAdministrator() == 0));
/* 1179 */     administrator.setTabIndex(12);
/* 1180 */     userSecurityForm.addElement(administrator);
/*      */ 
/*      */ 
/*      */     
/* 1184 */     String selDepartment = "";
/* 1185 */     String selStr = "";
/* 1186 */     if (user.getDeptFilter() != null)
/* 1187 */       selStr = user.getDeptFilter(); 
/* 1188 */     int sel = selStr.indexOf("department.filter.");
/* 1189 */     if (sel != -1) {
/*      */       try {
/* 1191 */         selDepartment = selStr.substring(sel + "department.filter.".length());
/* 1192 */       } catch (Exception exception) {}
/*      */     }
/*      */     
/* 1195 */     FormDropDownMenu deptFilterDD = MilestoneHelper.getDepartmentDropDown("deptFilter", selDepartment, false);
/* 1196 */     String[] values = deptFilterDD.getValueList();
/* 1197 */     String[] menuText = deptFilterDD.getMenuTextList();
/*      */     
/* 1199 */     values[0] = "All";
/* 1200 */     menuText[0] = "All";
/* 1201 */     deptFilterDD.setValueList(values);
/* 1202 */     deptFilterDD.setMenuTextList(menuText);
/* 1203 */     deptFilterDD.setTabIndex(11);
/* 1204 */     userSecurityForm.addElement(deptFilterDD);
/*      */ 
/*      */     
/* 1207 */     String filterFlagDept = "Yes";
/* 1208 */     String deptFilter = user.getDeptFilter();
/* 1209 */     if (deptFilter != null && deptFilter.length() > 0) {
/*      */       
/*      */       try {
/* 1212 */         if (deptFilter.substring(0, 4).equalsIgnoreCase("true"))
/* 1213 */         { filterFlagDept = "Yes"; }
/*      */         else
/* 1215 */         { filterFlagDept = "No"; } 
/* 1216 */       } catch (Exception exception) {}
/*      */     }
/*      */ 
/*      */     
/* 1220 */     FormRadioButtonGroup IsModifyDept = new FormRadioButtonGroup("IsModifyDept", filterFlagDept, "Yes, No", false);
/* 1221 */     IsModifyDept.setTabIndex(11);
/* 1222 */     userSecurityForm.addElement(IsModifyDept);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1228 */     String filterString = "All";
/* 1229 */     String filterFlag = "Yes";
/* 1230 */     String userFilter = user.getFilter();
/*      */ 
/*      */ 
/*      */     
/* 1234 */     if (userFilter != null && userFilter.length() > 0) {
/*      */       
/* 1236 */       if (userFilter.substring(0, 0).equalsIgnoreCase("t")) {
/* 1237 */         filterFlag = userFilter.substring(0, 3);
/*      */       } else {
/* 1239 */         filterFlag = userFilter.substring(0, 4);
/*      */       } 
/* 1241 */       if (filterFlag.equalsIgnoreCase("true")) {
/* 1242 */         filterString = userFilter.substring(5, userFilter.length());
/*      */       } else {
/* 1244 */         filterString = userFilter.substring(6, userFilter.length());
/*      */       } 
/* 1246 */       if (filterFlag.equalsIgnoreCase("true")) {
/* 1247 */         filterFlag = "Yes";
/*      */       } else {
/* 1249 */         filterFlag = "No";
/*      */       } 
/* 1251 */       if (filterString.equalsIgnoreCase("milestone.filter.FilterNone")) {
/*      */         
/* 1253 */         filterString = "All";
/*      */       }
/* 1255 */       else if (filterString.equalsIgnoreCase("milestone.filter.FilterExcludeOwnerUML")) {
/*      */         
/* 1257 */         filterString = "Only Label Tasks";
/*      */       }
/* 1259 */       else if (filterString.equalsIgnoreCase("milestone.filter.FilterIncludeOwnerUML")) {
/*      */         
/* 1261 */         filterString = "Only UML Tasks";
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1267 */     FormDropDownMenu filter = new FormDropDownMenu("filter", filterString, "All,Only Label Tasks, Only UML Tasks", true);
/* 1268 */     filter.setTabIndex(8);
/* 1269 */     userSecurityForm.addElement(filter);
/*      */ 
/*      */     
/* 1272 */     FormRadioButtonGroup IsModify = new FormRadioButtonGroup("IsModify", filterFlag, "Yes, No", false);
/* 1273 */     IsModify.setTabIndex(9);
/* 1274 */     userSecurityForm.addElement(IsModify);
/*      */ 
/*      */     
/* 1277 */     Vector environments = Cache.getInstance().getEnvironments();
/*      */ 
/*      */     
/* 1280 */     environments = Cache.getInstance().getEnvironmentsByFamily();
/*      */     
/* 1282 */     int userid = userId;
/*      */ 
/*      */     
/* 1285 */     Vector userEnvironments = MilestoneHelper.getUserEnvironments(userid);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1294 */     if (userEnvironments != null)
/*      */     {
/* 1296 */       for (int a = 0; a < userEnvironments.size(); a++) {
/*      */         
/* 1298 */         Environment userEnvironment = (Environment)userEnvironments.elementAt(a);
/* 1299 */         if (userEnvironment != null) {
/*      */           
/* 1301 */           FormCheckBox environmentCheckbox = new FormCheckBox("ue" + userEnvironment.getStructureID(), "ue" + userEnvironment.getStructureID(), userEnvironment.getName(), false, true);
/* 1302 */           environmentCheckbox.setId("ue" + userEnvironment.getStructureID());
/* 1303 */           userSecurityForm.addElement(environmentCheckbox);
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1311 */     for (int i = 0; i < environments.size(); i++) {
/*      */ 
/*      */       
/* 1314 */       Environment environment = (Environment)environments.elementAt(i);
/* 1315 */       if (environment != null) {
/*      */         
/* 1317 */         FormCheckBox environmentCheckbox = new FormCheckBox("ue" + environment.getStructureID(), "ue" + environment.getStructureID(), environment.getName(), false, false);
/* 1318 */         environmentCheckbox.setId("ue" + environment.getStructureID());
/* 1319 */         userSecurityForm.addElement(environmentCheckbox);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1361 */     String selectionValue = "Not Available";
/* 1362 */     if (acl != null)
/*      */     {
/* 1364 */       if (acl.getAccessSelection())
/*      */       {
/* 1366 */         selectionValue = "Available";
/*      */       }
/*      */     }
/* 1369 */     FormRadioButtonGroup selectionAccess = new FormRadioButtonGroup("1", selectionValue, "Not Available, Available", false);
/* 1370 */     selectionAccess.setTabIndex(10);
/* 1371 */     userSecurityForm.addElement(selectionAccess);
/*      */ 
/*      */     
/* 1374 */     String scheduleValue = "Not Available";
/* 1375 */     if (acl != null)
/*      */     {
/* 1377 */       if (acl.getAccessSchedule())
/*      */       {
/* 1379 */         scheduleValue = "Available";
/*      */       }
/*      */     }
/* 1382 */     FormRadioButtonGroup scheduleAccess = new FormRadioButtonGroup("2", scheduleValue, "Not Available, Available", false);
/* 1383 */     scheduleAccess.setTabIndex(11);
/* 1384 */     userSecurityForm.addElement(scheduleAccess);
/*      */ 
/*      */     
/* 1387 */     String manufacturingValue = "Not Available";
/* 1388 */     if (acl != null)
/*      */     {
/* 1390 */       if (acl.getAccessManufacturing())
/*      */       {
/* 1392 */         manufacturingValue = "Available";
/*      */       }
/*      */     }
/* 1395 */     FormRadioButtonGroup manufacturingAccess = new FormRadioButtonGroup("3", manufacturingValue, "Not Available, Available", false);
/* 1396 */     manufacturingAccess.setTabIndex(12);
/* 1397 */     userSecurityForm.addElement(manufacturingAccess);
/*      */ 
/*      */     
/* 1400 */     String pfmValue = "Not Available";
/* 1401 */     if (acl != null)
/*      */     {
/* 1403 */       if (acl.getAccessPfmForm())
/*      */       {
/* 1405 */         pfmValue = "Available";
/*      */       }
/*      */     }
/* 1408 */     FormRadioButtonGroup pfmAccess = new FormRadioButtonGroup("4", pfmValue, "Not Available, Available", false);
/* 1409 */     pfmAccess.setTabIndex(13);
/* 1410 */     userSecurityForm.addElement(pfmAccess);
/*      */ 
/*      */     
/* 1413 */     String bomValue = "Not Available";
/* 1414 */     if (acl != null)
/*      */     {
/* 1416 */       if (acl.getAccessBomForm())
/*      */       {
/* 1418 */         bomValue = "Available";
/*      */       }
/*      */     }
/* 1421 */     FormRadioButtonGroup bomAccess = new FormRadioButtonGroup("5", bomValue, "Not Available, Available", false);
/* 1422 */     bomAccess.setTabIndex(14);
/* 1423 */     userSecurityForm.addElement(bomAccess);
/*      */ 
/*      */     
/* 1426 */     String reportValue = "Not Available";
/* 1427 */     if (acl != null)
/*      */     {
/* 1429 */       if (acl.getAccessReport())
/*      */       {
/* 1431 */         reportValue = "Available";
/*      */       }
/*      */     }
/* 1434 */     FormRadioButtonGroup reportAccess = new FormRadioButtonGroup("6", reportValue, "Not Available, Available", false);
/* 1435 */     reportAccess.setTabIndex(15);
/* 1436 */     userSecurityForm.addElement(reportAccess);
/*      */ 
/*      */     
/* 1439 */     String templateValue = "Not Available";
/* 1440 */     if (acl != null)
/*      */     {
/* 1442 */       if (acl.getAccessTemplate())
/*      */       {
/* 1444 */         templateValue = "Available";
/*      */       }
/*      */     }
/* 1447 */     FormRadioButtonGroup templateAccess = new FormRadioButtonGroup("7", templateValue, "Not Available, Available", false);
/* 1448 */     templateAccess.setTabIndex(16);
/* 1449 */     userSecurityForm.addElement(templateAccess);
/*      */ 
/*      */     
/* 1452 */     String taskValue = "Not Available";
/* 1453 */     if (acl != null)
/*      */     {
/* 1455 */       if (acl.getAccessTask())
/*      */       {
/* 1457 */         taskValue = "Available";
/*      */       }
/*      */     }
/* 1460 */     FormRadioButtonGroup taskAccess = new FormRadioButtonGroup("8", taskValue, "Not Available, Available", false);
/* 1461 */     taskAccess.setTabIndex(17);
/* 1462 */     userSecurityForm.addElement(taskAccess);
/*      */ 
/*      */     
/* 1465 */     String dayTypeValue = "Not Available";
/* 1466 */     if (acl != null)
/*      */     {
/* 1468 */       if (acl.getAccessDayType())
/*      */       {
/* 1470 */         dayTypeValue = "Available";
/*      */       }
/*      */     }
/* 1473 */     FormRadioButtonGroup dayTypeAccess = new FormRadioButtonGroup("9", dayTypeValue, "Not Available, Available", false);
/* 1474 */     dayTypeAccess.setTabIndex(18);
/* 1475 */     userSecurityForm.addElement(dayTypeAccess);
/*      */ 
/*      */     
/* 1478 */     String userValue = "Not Available";
/* 1479 */     if (acl != null)
/*      */     {
/* 1481 */       if (acl.getAccessUser())
/*      */       {
/* 1483 */         userValue = "Available";
/*      */       }
/*      */     }
/* 1486 */     FormRadioButtonGroup userAccess = new FormRadioButtonGroup("10", userValue, "Not Available, Available", false);
/* 1487 */     userAccess.setTabIndex(19);
/* 1488 */     userSecurityForm.addElement(userAccess);
/*      */ 
/*      */     
/* 1491 */     String familyValue = "Not Available";
/* 1492 */     if (acl != null)
/*      */     {
/* 1494 */       if (acl.getAccessFamily())
/*      */       {
/* 1496 */         familyValue = "Available";
/*      */       }
/*      */     }
/* 1499 */     FormRadioButtonGroup familyAccess = new FormRadioButtonGroup("11", familyValue, "Not Available, Available", false);
/* 1500 */     familyAccess.setTabIndex(20);
/* 1501 */     userSecurityForm.addElement(familyAccess);
/*      */ 
/*      */ 
/*      */     
/* 1505 */     String environmentValue = "Not Available";
/* 1506 */     if (acl != null)
/*      */     {
/* 1508 */       if (acl.getAccessEnvironment())
/*      */       {
/* 1510 */         environmentValue = "Available";
/*      */       }
/*      */     }
/* 1513 */     FormRadioButtonGroup environmentAccess = new FormRadioButtonGroup("20", environmentValue, "Not Available, Available", false);
/* 1514 */     environmentAccess.setTabIndex(21);
/* 1515 */     userSecurityForm.addElement(environmentAccess);
/*      */ 
/*      */     
/* 1518 */     String companyValue = "Not Available";
/* 1519 */     if (acl != null)
/*      */     {
/* 1521 */       if (acl.getAccessCompany())
/*      */       {
/* 1523 */         companyValue = "Available";
/*      */       }
/*      */     }
/* 1526 */     FormRadioButtonGroup companyAccess = new FormRadioButtonGroup("12", companyValue, "Not Available, Available", false);
/* 1527 */     companyAccess.setTabIndex(22);
/* 1528 */     userSecurityForm.addElement(companyAccess);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1533 */     String divisionValue = "Not Available";
/* 1534 */     if (acl != null)
/*      */     {
/* 1536 */       if (acl.getAccessDivision())
/*      */       {
/* 1538 */         divisionValue = "Available";
/*      */       }
/*      */     }
/* 1541 */     FormRadioButtonGroup divisionAccess = new FormRadioButtonGroup("13", divisionValue, "Not Available, Available", false);
/* 1542 */     divisionAccess.setTabIndex(23);
/* 1543 */     userSecurityForm.addElement(divisionAccess);
/*      */ 
/*      */     
/* 1546 */     String labelValue = "Not Available";
/* 1547 */     if (acl != null)
/*      */     {
/* 1549 */       if (acl.getAccessLabel())
/*      */       {
/* 1551 */         labelValue = "Available";
/*      */       }
/*      */     }
/* 1554 */     FormRadioButtonGroup labelAccess = new FormRadioButtonGroup("14", labelValue, "Not Available, Available", false);
/* 1555 */     labelAccess.setTabIndex(24);
/* 1556 */     userSecurityForm.addElement(labelAccess);
/*      */ 
/*      */     
/* 1559 */     String tableValue = "Not Available";
/* 1560 */     if (acl != null)
/*      */     {
/* 1562 */       if (acl.getAccessTable())
/*      */       {
/* 1564 */         tableValue = "Available";
/*      */       }
/*      */     }
/* 1567 */     FormRadioButtonGroup tableAccess = new FormRadioButtonGroup("15", tableValue, "Not Available, Available", false);
/* 1568 */     tableAccess.setTabIndex(25);
/* 1569 */     userSecurityForm.addElement(tableAccess);
/*      */ 
/*      */     
/* 1572 */     String parameterValue = "Not Available";
/* 1573 */     if (acl != null)
/*      */     {
/* 1575 */       if (acl.getAccessParameter())
/*      */       {
/* 1577 */         parameterValue = "Available";
/*      */       }
/*      */     }
/* 1580 */     FormRadioButtonGroup parameterAccess = new FormRadioButtonGroup("16", parameterValue, "Not Available, Available", false);
/* 1581 */     parameterAccess.setTabIndex(26);
/* 1582 */     userSecurityForm.addElement(parameterAccess);
/*      */ 
/*      */     
/* 1585 */     String auditTrailValue = "Not Available";
/* 1586 */     if (acl != null)
/*      */     {
/* 1588 */       if (acl.getAccessAuditTrail())
/*      */       {
/* 1590 */         auditTrailValue = "Available";
/*      */       }
/*      */     }
/* 1593 */     FormRadioButtonGroup auditTrailAccess = new FormRadioButtonGroup("17", auditTrailValue, "Not Available, Available", false);
/* 1594 */     auditTrailAccess.setTabIndex(27);
/* 1595 */     userSecurityForm.addElement(auditTrailAccess);
/*      */ 
/*      */     
/* 1598 */     String reportConfigValue = "Not Available";
/* 1599 */     if (acl != null)
/*      */     {
/* 1601 */       if (acl.getAccessReportConfig())
/*      */       {
/* 1603 */         reportConfigValue = "Available";
/*      */       }
/*      */     }
/* 1606 */     FormRadioButtonGroup reportConfigAccess = new FormRadioButtonGroup("18", reportConfigValue, "Not Available, Available", false);
/* 1607 */     reportConfigAccess.setTabIndex(28);
/* 1608 */     userSecurityForm.addElement(reportConfigAccess);
/*      */ 
/*      */     
/* 1611 */     String priceCodeValue = "Not Available";
/* 1612 */     if (acl != null)
/*      */     {
/* 1614 */       if (acl.getAccessPriceCode())
/*      */       {
/* 1616 */         priceCodeValue = "Available";
/*      */       }
/*      */     }
/* 1619 */     FormRadioButtonGroup priceCodeAccess = new FormRadioButtonGroup("19", priceCodeValue, "Not Available, Available", false);
/* 1620 */     priceCodeAccess.setTabIndex(29);
/* 1621 */     userSecurityForm.addElement(priceCodeAccess);
/*      */ 
/*      */     
/* 1624 */     FormTextField lastUpdated = new FormTextField("lastUpdatedDate", false, 50);
/* 1625 */     if (user.getLastUpdateDate() != null)
/* 1626 */       lastUpdated.setValue(MilestoneHelper.getLongDate(user.getLastUpdateDate())); 
/* 1627 */     userSecurityForm.addElement(lastUpdated);
/*      */     
/* 1629 */     FormTextField lastUpdatedBy = new FormTextField("lastUpdatedBy", false, 50);
/* 1630 */     if (UserManager.getInstance().getUser(user.getLastUpdatingUser(), true) != null)
/* 1631 */       lastUpdatedBy.setValue(UserManager.getInstance().getUser(user.getLastUpdatingUser(), true).getLogin()); 
/* 1632 */     userSecurityForm.addElement(lastUpdatedBy);
/*      */ 
/*      */     
/* 1635 */     FormTextField nameSrch = new FormTextField("nameSrch", "", false, 20);
/* 1636 */     nameSrch.setId("nameSrch");
/* 1637 */     userSecurityForm.addElement(nameSrch);
/*      */     
/* 1639 */     FormTextField userNameSrch = new FormTextField("userNameSrch", "", false, 20);
/* 1640 */     userNameSrch.setId("userNameSrch");
/* 1641 */     userSecurityForm.addElement(userNameSrch);
/*      */ 
/*      */     
/* 1644 */     String employedBySrcString = "";
/* 1645 */     FormDropDownMenu employedBySrc = MilestoneHelper.getCorporateStructureDropDown("employedBySrc", families, employedBySrcString, true, true);
/* 1646 */     employedBySrc.setId("employedBySrc");
/* 1647 */     userSecurityForm.addElement(employedBySrc);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1653 */     FormDropDownMenu environmentDescriptionSearch = MilestoneHelper.getCorporateStructureDropDown("EnvironmentDescriptionSearch", environments, "", false, true);
/* 1654 */     environmentDescriptionSearch.setId("EnvironmentDescriptionSearch");
/* 1655 */     userSecurityForm.addElement(environmentDescriptionSearch);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1660 */     userSecurityForm.addElement(new FormHidden("cmd", "user-security-copy"));
/*      */     
/* 1662 */     context.putDelivery("Form", userSecurityForm);
/*      */ 
/*      */     
/* 1665 */     if (context.getSessionValue("NOTEPAD_USER_VISIBLE") != null) {
/* 1666 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_USER_VISIBLE"));
/*      */     }
/*      */     
/* 1669 */     return userSecurityForm;
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
/*      */   private Form buildForm(Context context, User user) {
/* 1681 */     Form userSecurityForm = new Form(this.application, "userSecurityForm", 
/* 1682 */         this.application.getInfrastructure().getServletURL(), "POST");
/*      */     
/* 1684 */     Acl acl = user.getAcl();
/*      */     
/* 1686 */     Vector cAcl = null;
/*      */     
/* 1688 */     if (acl != null) {
/* 1689 */       acl.getCompanyAcl();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1694 */     String showAssigned = context.getRequest().getParameter("showAssigned");
/* 1695 */     if (showAssigned != null)
/*      */     {
/*      */ 
/*      */ 
/*      */       
/* 1700 */       if (showAssigned.equals("NEW") && user.getUserId() != -1) {
/* 1701 */         context.putSessionValue("showAssigned", "ASSIGNED");
/*      */       } else {
/* 1703 */         context.putSessionValue("showAssigned", showAssigned);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 1708 */     FormPasswordField password = new FormPasswordField("password", user.getPassword(), true, 30, 30);
/* 1709 */     password.setTabIndex(1);
/* 1710 */     userSecurityForm.addElement(password);
/*      */ 
/*      */     
/* 1713 */     String reportsToString = "";
/* 1714 */     if (user.getReportsTo() != null) {
/* 1715 */       reportsToString = user.getReportsTo();
/*      */     }
/* 1717 */     FormTextField reportto = new FormTextField("reportto", reportsToString, false, 30, 30);
/* 1718 */     reportto.setTabIndex(2);
/* 1719 */     userSecurityForm.addElement(reportto);
/*      */ 
/*      */     
/* 1722 */     FormTextField login = new FormTextField("login", user.getLogin(), true, 30, 100);
/* 1723 */     userSecurityForm.addElement(login);
/*      */ 
/*      */     
/* 1726 */     FormTextField fullname = new FormTextField("fullname", user.getName(), true, 30, 100);
/* 1727 */     fullname.setTabIndex(3);
/* 1728 */     userSecurityForm.addElement(fullname);
/*      */ 
/*      */     
/* 1731 */     String locationString = "";
/* 1732 */     locationString = user.getLocation();
/* 1733 */     Vector userLocations = Cache.getLookupDetailValuesFromDatabase(53);
/* 1734 */     FormDropDownMenu location = MilestoneHelper.getLookupDropDown("location", userLocations, locationString, false, true);
/* 1735 */     location.setTabIndex(4);
/* 1736 */     userSecurityForm.addElement(location);
/*      */ 
/*      */     
/* 1739 */     Vector families = Cache.getInstance().getFamilies();
/*      */     
/* 1741 */     String employedByString = "";
/* 1742 */     if (user.getEmployedBy() > 0)
/*      */     {
/* 1744 */       employedByString = String.valueOf(user.getEmployedBy());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1749 */     FormDropDownMenu employedby = MilestoneHelper.getCorporateStructureDropDown("employedby", families, employedByString, true, true);
/* 1750 */     employedby.setTabIndex(5);
/* 1751 */     userSecurityForm.addElement(employedby);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1757 */     FormTextField email = new FormTextField("email", user.getEmail(), false, 30, 50);
/* 1758 */     email.setTabIndex(6);
/* 1759 */     userSecurityForm.addElement(email);
/*      */     
/* 1761 */     FormTextField phone = new FormTextField("phone", user.getPhone(), false, 30, 30);
/* 1762 */     phone.setTabIndex(7);
/* 1763 */     userSecurityForm.addElement(phone);
/*      */     
/* 1765 */     FormTextField fax = new FormTextField("fax", user.getFax(), false, 30, 30);
/* 1766 */     fax.setTabIndex(10);
/* 1767 */     userSecurityForm.addElement(fax);
/*      */     
/* 1769 */     FormCheckBox inactive = new FormCheckBox("inactive", "Inactive", false, !(user.getInactive() == 0));
/* 1770 */     inactive.setId("inactive");
/* 1771 */     inactive.setTabIndex(11);
/* 1772 */     userSecurityForm.addElement(inactive);
/*      */ 
/*      */     
/* 1775 */     FormCheckBox administrator = new FormCheckBox("administrator", "Administrator", false, !(user.getAdministrator() == 0));
/* 1776 */     administrator.setTabIndex(12);
/* 1777 */     userSecurityForm.addElement(administrator);
/*      */ 
/*      */ 
/*      */     
/* 1781 */     String selDepartment = "";
/* 1782 */     String selStr = "";
/* 1783 */     if (user.getDeptFilter() != null)
/* 1784 */       selStr = user.getDeptFilter(); 
/* 1785 */     int sel = selStr.indexOf("department.filter.");
/* 1786 */     if (sel != -1) {
/*      */       try {
/* 1788 */         selDepartment = selStr.substring(sel + "department.filter.".length());
/* 1789 */       } catch (Exception exception) {}
/*      */     }
/*      */     
/* 1792 */     FormDropDownMenu deptFilterDD = MilestoneHelper.getDepartmentDropDown("deptFilter", selDepartment, false);
/* 1793 */     String[] values = deptFilterDD.getValueList();
/* 1794 */     String[] menuText = deptFilterDD.getMenuTextList();
/*      */     
/* 1796 */     values[0] = "All";
/* 1797 */     menuText[0] = "All";
/* 1798 */     deptFilterDD.setValueList(values);
/* 1799 */     deptFilterDD.setMenuTextList(menuText);
/* 1800 */     deptFilterDD.setTabIndex(11);
/* 1801 */     userSecurityForm.addElement(deptFilterDD);
/*      */ 
/*      */     
/* 1804 */     String filterFlagDept = "Yes";
/* 1805 */     String deptFilter = user.getDeptFilter();
/* 1806 */     if (deptFilter != null && deptFilter.length() > 0) {
/*      */       
/*      */       try {
/* 1809 */         if (deptFilter.substring(0, 4).equalsIgnoreCase("true"))
/* 1810 */         { filterFlagDept = "Yes"; }
/*      */         else
/* 1812 */         { filterFlagDept = "No"; } 
/* 1813 */       } catch (Exception exception) {}
/*      */     }
/*      */ 
/*      */     
/* 1817 */     FormRadioButtonGroup IsModifyDept = new FormRadioButtonGroup("IsModifyDept", filterFlagDept, "Yes, No", false);
/* 1818 */     IsModifyDept.setTabIndex(11);
/* 1819 */     userSecurityForm.addElement(IsModifyDept);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1825 */     String filterString = "All";
/* 1826 */     String filterFlag = "Yes";
/* 1827 */     String userFilter = user.getFilter();
/*      */ 
/*      */ 
/*      */     
/* 1831 */     if (userFilter != null && userFilter.length() > 0) {
/*      */       
/* 1833 */       if (userFilter.substring(0, 0).equalsIgnoreCase("t")) {
/* 1834 */         filterFlag = userFilter.substring(0, 3);
/*      */       } else {
/* 1836 */         filterFlag = userFilter.substring(0, 4);
/*      */       } 
/* 1838 */       if (filterFlag.equalsIgnoreCase("true")) {
/* 1839 */         filterString = userFilter.substring(5, userFilter.length());
/*      */       } else {
/* 1841 */         filterString = userFilter.substring(6, userFilter.length());
/*      */       } 
/* 1843 */       if (filterFlag.equalsIgnoreCase("true")) {
/* 1844 */         filterFlag = "Yes";
/*      */       } else {
/* 1846 */         filterFlag = "No";
/*      */       } 
/* 1848 */       if (filterString.equalsIgnoreCase("milestone.filter.FilterNone")) {
/*      */         
/* 1850 */         filterString = "All";
/*      */       }
/* 1852 */       else if (filterString.equalsIgnoreCase("milestone.filter.FilterExcludeOwnerUML")) {
/*      */         
/* 1854 */         filterString = "Only Label Tasks";
/*      */       }
/* 1856 */       else if (filterString.equalsIgnoreCase("milestone.filter.FilterIncludeOwnerUML")) {
/*      */         
/* 1858 */         filterString = "Only UML Tasks";
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1864 */     FormDropDownMenu filter = new FormDropDownMenu("filter", filterString, "All,Only Label Tasks, Only UML Tasks", true);
/* 1865 */     filter.setTabIndex(8);
/* 1866 */     userSecurityForm.addElement(filter);
/*      */ 
/*      */     
/* 1869 */     FormRadioButtonGroup IsModify = new FormRadioButtonGroup("IsModify", filterFlag, "Yes, No", false);
/* 1870 */     IsModify.setTabIndex(9);
/* 1871 */     userSecurityForm.addElement(IsModify);
/*      */ 
/*      */     
/* 1874 */     Vector environments = Cache.getInstance().getEnvironments();
/*      */ 
/*      */     
/* 1877 */     environments = Cache.getInstance().getEnvironmentsByFamily();
/*      */ 
/*      */     
/* 1880 */     int userid = user.getUserId();
/*      */ 
/*      */     
/* 1883 */     Vector userEnvironments = MilestoneHelper.getUserEnvironments(userid);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1889 */     if (userEnvironments != null)
/*      */     {
/* 1891 */       for (int a = 0; a < userEnvironments.size(); a++) {
/*      */         
/* 1893 */         Environment userEnvironment = (Environment)userEnvironments.elementAt(a);
/* 1894 */         if (userEnvironment != null) {
/*      */           
/* 1896 */           FormCheckBox environmentCheckbox = new FormCheckBox("ue" + userEnvironment.getStructureID(), "ue" + userEnvironment.getStructureID(), userEnvironment.getName(), false, true);
/* 1897 */           environmentCheckbox.setId("ue" + userEnvironment.getStructureID());
/* 1898 */           userSecurityForm.addElement(environmentCheckbox);
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1906 */     for (int i = 0; i < environments.size(); i++) {
/*      */       
/* 1908 */       Environment environment = (Environment)environments.elementAt(i);
/* 1909 */       if (environment != null) {
/*      */         
/* 1911 */         FormCheckBox environmentCheckbox = new FormCheckBox("ue" + environment.getStructureID(), "ue" + environment.getStructureID(), environment.getName(), false, false);
/* 1912 */         environmentCheckbox.setId("ue" + environment.getStructureID());
/* 1913 */         userSecurityForm.addElement(environmentCheckbox);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1922 */     String selectionValue = "Not Available";
/* 1923 */     if (acl != null)
/*      */     {
/* 1925 */       if (acl.getAccessSelection())
/*      */       {
/* 1927 */         selectionValue = "Available";
/*      */       }
/*      */     }
/* 1930 */     FormRadioButtonGroup selectionAccess = new FormRadioButtonGroup("1", selectionValue, "Not Available, Available", false);
/* 1931 */     selectionAccess.setTabIndex(10);
/* 1932 */     userSecurityForm.addElement(selectionAccess);
/*      */ 
/*      */     
/* 1935 */     String scheduleValue = "Not Available";
/* 1936 */     if (acl != null)
/*      */     {
/* 1938 */       if (acl.getAccessSchedule())
/*      */       {
/* 1940 */         scheduleValue = "Available";
/*      */       }
/*      */     }
/* 1943 */     FormRadioButtonGroup scheduleAccess = new FormRadioButtonGroup("2", scheduleValue, "Not Available, Available", false);
/* 1944 */     scheduleAccess.setTabIndex(11);
/* 1945 */     userSecurityForm.addElement(scheduleAccess);
/*      */ 
/*      */     
/* 1948 */     String manufacturingValue = "Not Available";
/* 1949 */     if (acl != null)
/*      */     {
/* 1951 */       if (acl.getAccessManufacturing())
/*      */       {
/* 1953 */         manufacturingValue = "Available";
/*      */       }
/*      */     }
/* 1956 */     FormRadioButtonGroup manufacturingAccess = new FormRadioButtonGroup("3", manufacturingValue, "Not Available, Available", false);
/* 1957 */     manufacturingAccess.setTabIndex(12);
/* 1958 */     userSecurityForm.addElement(manufacturingAccess);
/*      */ 
/*      */     
/* 1961 */     String pfmValue = "Not Available";
/* 1962 */     if (acl != null)
/*      */     {
/* 1964 */       if (acl.getAccessPfmForm())
/*      */       {
/* 1966 */         pfmValue = "Available";
/*      */       }
/*      */     }
/* 1969 */     FormRadioButtonGroup pfmAccess = new FormRadioButtonGroup("4", pfmValue, "Not Available, Available", false);
/* 1970 */     pfmAccess.setTabIndex(13);
/* 1971 */     userSecurityForm.addElement(pfmAccess);
/*      */ 
/*      */     
/* 1974 */     String bomValue = "Not Available";
/* 1975 */     if (acl != null)
/*      */     {
/* 1977 */       if (acl.getAccessBomForm())
/*      */       {
/* 1979 */         bomValue = "Available";
/*      */       }
/*      */     }
/* 1982 */     FormRadioButtonGroup bomAccess = new FormRadioButtonGroup("5", bomValue, "Not Available, Available", false);
/* 1983 */     bomAccess.setTabIndex(14);
/* 1984 */     userSecurityForm.addElement(bomAccess);
/*      */ 
/*      */     
/* 1987 */     String reportValue = "Not Available";
/* 1988 */     if (acl != null)
/*      */     {
/* 1990 */       if (acl.getAccessReport())
/*      */       {
/* 1992 */         reportValue = "Available";
/*      */       }
/*      */     }
/* 1995 */     FormRadioButtonGroup reportAccess = new FormRadioButtonGroup("6", reportValue, "Not Available, Available", false);
/* 1996 */     reportAccess.setTabIndex(15);
/* 1997 */     userSecurityForm.addElement(reportAccess);
/*      */ 
/*      */     
/* 2000 */     String templateValue = "Not Available";
/* 2001 */     if (acl != null)
/*      */     {
/* 2003 */       if (acl.getAccessTemplate())
/*      */       {
/* 2005 */         templateValue = "Available";
/*      */       }
/*      */     }
/* 2008 */     FormRadioButtonGroup templateAccess = new FormRadioButtonGroup("7", templateValue, "Not Available, Available", false);
/* 2009 */     templateAccess.setTabIndex(16);
/* 2010 */     userSecurityForm.addElement(templateAccess);
/*      */ 
/*      */     
/* 2013 */     String taskValue = "Not Available";
/* 2014 */     if (acl != null)
/*      */     {
/* 2016 */       if (acl.getAccessTask())
/*      */       {
/* 2018 */         taskValue = "Available";
/*      */       }
/*      */     }
/* 2021 */     FormRadioButtonGroup taskAccess = new FormRadioButtonGroup("8", taskValue, "Not Available, Available", false);
/* 2022 */     taskAccess.setTabIndex(17);
/* 2023 */     userSecurityForm.addElement(taskAccess);
/*      */ 
/*      */     
/* 2026 */     String dayTypeValue = "Not Available";
/* 2027 */     if (acl != null)
/*      */     {
/* 2029 */       if (acl.getAccessDayType())
/*      */       {
/* 2031 */         dayTypeValue = "Available";
/*      */       }
/*      */     }
/* 2034 */     FormRadioButtonGroup dayTypeAccess = new FormRadioButtonGroup("9", dayTypeValue, "Not Available, Available", false);
/* 2035 */     dayTypeAccess.setTabIndex(18);
/* 2036 */     userSecurityForm.addElement(dayTypeAccess);
/*      */ 
/*      */     
/* 2039 */     String userValue = "Not Available";
/* 2040 */     if (acl != null)
/*      */     {
/* 2042 */       if (acl.getAccessUser())
/*      */       {
/* 2044 */         userValue = "Available";
/*      */       }
/*      */     }
/* 2047 */     FormRadioButtonGroup userAccess = new FormRadioButtonGroup("10", userValue, "Not Available, Available", false);
/* 2048 */     userAccess.setTabIndex(19);
/* 2049 */     userSecurityForm.addElement(userAccess);
/*      */ 
/*      */     
/* 2052 */     String familyValue = "Not Available";
/* 2053 */     if (acl != null)
/*      */     {
/* 2055 */       if (acl.getAccessFamily())
/*      */       {
/* 2057 */         familyValue = "Available";
/*      */       }
/*      */     }
/* 2060 */     FormRadioButtonGroup familyAccess = new FormRadioButtonGroup("11", familyValue, "Not Available, Available", false);
/* 2061 */     familyAccess.setTabIndex(20);
/* 2062 */     userSecurityForm.addElement(familyAccess);
/*      */ 
/*      */     
/* 2065 */     String environmentValue = "Not Available";
/* 2066 */     if (acl != null)
/*      */     {
/* 2068 */       if (acl.getAccessEnvironment())
/*      */       {
/* 2070 */         environmentValue = "Available";
/*      */       }
/*      */     }
/* 2073 */     FormRadioButtonGroup environmentAccess = new FormRadioButtonGroup("20", environmentValue, "Not Available, Available", false);
/* 2074 */     environmentAccess.setTabIndex(21);
/* 2075 */     userSecurityForm.addElement(environmentAccess);
/*      */ 
/*      */     
/* 2078 */     String companyValue = "Not Available";
/* 2079 */     if (acl != null)
/*      */     {
/* 2081 */       if (acl.getAccessCompany())
/*      */       {
/* 2083 */         companyValue = "Available";
/*      */       }
/*      */     }
/* 2086 */     FormRadioButtonGroup companyAccess = new FormRadioButtonGroup("12", companyValue, "Not Available, Available", false);
/* 2087 */     companyAccess.setTabIndex(22);
/* 2088 */     userSecurityForm.addElement(companyAccess);
/*      */ 
/*      */     
/* 2091 */     String divisionValue = "Not Available";
/* 2092 */     if (acl != null)
/*      */     {
/* 2094 */       if (acl.getAccessDivision())
/*      */       {
/* 2096 */         divisionValue = "Available";
/*      */       }
/*      */     }
/* 2099 */     FormRadioButtonGroup divisionAccess = new FormRadioButtonGroup("13", divisionValue, "Not Available, Available", false);
/* 2100 */     divisionAccess.setTabIndex(23);
/* 2101 */     userSecurityForm.addElement(divisionAccess);
/*      */ 
/*      */     
/* 2104 */     String labelValue = "Not Available";
/* 2105 */     if (acl != null)
/*      */     {
/* 2107 */       if (acl.getAccessLabel())
/*      */       {
/* 2109 */         labelValue = "Available";
/*      */       }
/*      */     }
/* 2112 */     FormRadioButtonGroup labelAccess = new FormRadioButtonGroup("14", labelValue, "Not Available, Available", false);
/* 2113 */     labelAccess.setTabIndex(24);
/* 2114 */     userSecurityForm.addElement(labelAccess);
/*      */ 
/*      */     
/* 2117 */     String tableValue = "Not Available";
/* 2118 */     if (acl != null)
/*      */     {
/* 2120 */       if (acl.getAccessTable())
/*      */       {
/* 2122 */         tableValue = "Available";
/*      */       }
/*      */     }
/* 2125 */     FormRadioButtonGroup tableAccess = new FormRadioButtonGroup("15", tableValue, "Not Available, Available", false);
/* 2126 */     tableAccess.setTabIndex(25);
/* 2127 */     userSecurityForm.addElement(tableAccess);
/*      */ 
/*      */     
/* 2130 */     String parameterValue = "Not Available";
/* 2131 */     if (acl != null)
/*      */     {
/* 2133 */       if (acl.getAccessParameter())
/*      */       {
/* 2135 */         parameterValue = "Available";
/*      */       }
/*      */     }
/* 2138 */     FormRadioButtonGroup parameterAccess = new FormRadioButtonGroup("16", parameterValue, "Not Available, Available", false);
/* 2139 */     parameterAccess.setTabIndex(26);
/* 2140 */     userSecurityForm.addElement(parameterAccess);
/*      */ 
/*      */     
/* 2143 */     String auditTrailValue = "Not Available";
/* 2144 */     if (acl != null)
/*      */     {
/* 2146 */       if (acl.getAccessAuditTrail())
/*      */       {
/* 2148 */         auditTrailValue = "Available";
/*      */       }
/*      */     }
/* 2151 */     FormRadioButtonGroup auditTrailAccess = new FormRadioButtonGroup("17", auditTrailValue, "Not Available, Available", false);
/* 2152 */     auditTrailAccess.setTabIndex(27);
/* 2153 */     userSecurityForm.addElement(auditTrailAccess);
/*      */ 
/*      */     
/* 2156 */     String reportConfigValue = "Not Available";
/* 2157 */     if (acl != null)
/*      */     {
/* 2159 */       if (acl.getAccessReportConfig())
/*      */       {
/* 2161 */         reportConfigValue = "Available";
/*      */       }
/*      */     }
/* 2164 */     FormRadioButtonGroup reportConfigAccess = new FormRadioButtonGroup("18", reportConfigValue, "Not Available, Available", false);
/* 2165 */     reportConfigAccess.setTabIndex(28);
/* 2166 */     userSecurityForm.addElement(reportConfigAccess);
/*      */ 
/*      */     
/* 2169 */     String priceCodeValue = "Not Available";
/* 2170 */     if (acl != null)
/*      */     {
/* 2172 */       if (acl.getAccessPriceCode())
/*      */       {
/* 2174 */         priceCodeValue = "Available";
/*      */       }
/*      */     }
/* 2177 */     FormRadioButtonGroup priceCodeAccess = new FormRadioButtonGroup("19", priceCodeValue, "Not Available, Available", false);
/* 2178 */     priceCodeAccess.setTabIndex(29);
/* 2179 */     userSecurityForm.addElement(priceCodeAccess);
/*      */ 
/*      */     
/* 2182 */     FormTextField lastUpdated = new FormTextField("lastUpdatedDate", false, 50);
/* 2183 */     if (user.getLastUpdateDate() != null)
/* 2184 */       lastUpdated.setValue(MilestoneHelper.getLongDate(user.getLastUpdateDate())); 
/* 2185 */     userSecurityForm.addElement(lastUpdated);
/*      */     
/* 2187 */     FormTextField lastUpdatedBy = new FormTextField("lastUpdatedBy", false, 50);
/* 2188 */     if (UserManager.getInstance().getUser(user.getLastUpdatingUser(), true) != null)
/* 2189 */       lastUpdatedBy.setValue(UserManager.getInstance().getUser(user.getLastUpdatingUser(), true).getLogin()); 
/* 2190 */     userSecurityForm.addElement(lastUpdatedBy);
/*      */ 
/*      */     
/* 2193 */     FormTextField nameSrch = new FormTextField("nameSrch", "", false, 20);
/* 2194 */     nameSrch.setId("nameSrch");
/* 2195 */     userSecurityForm.addElement(nameSrch);
/*      */     
/* 2197 */     FormTextField userNameSrch = new FormTextField("userNameSrch", "", false, 20);
/* 2198 */     userNameSrch.setId("userNameSrch");
/* 2199 */     userSecurityForm.addElement(userNameSrch);
/*      */ 
/*      */     
/* 2202 */     String employedBySrcString = "";
/* 2203 */     FormDropDownMenu employedBySrc = MilestoneHelper.getCorporateStructureDropDown("employedBySrc", families, employedBySrcString, true, true);
/* 2204 */     employedBySrc.setId("employedBySrc");
/* 2205 */     userSecurityForm.addElement(employedBySrc);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2211 */     FormDropDownMenu environmentDescriptionSearch = MilestoneHelper.getCorporateStructureDropDown("EnvironmentDescriptionSearch", environments, "", false, true);
/* 2212 */     environmentDescriptionSearch.setId("EnvironmentDescriptionSearch");
/* 2213 */     userSecurityForm.addElement(environmentDescriptionSearch);
/*      */ 
/*      */     
/* 2216 */     userSecurityForm.addElement(new FormHidden("cmd", "user-security-editor"));
/* 2217 */     context.putDelivery("Form", userSecurityForm);
/*      */ 
/*      */     
/* 2220 */     if (context.getSessionValue("NOTEPAD_USER_VISIBLE") != null) {
/* 2221 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_USER_VISIBLE"));
/*      */     }
/*      */     
/* 2224 */     return userSecurityForm;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Form buildBlankCompanyForm(Context context) {
/* 2234 */     Form userCompanySecurityForm = new Form(this.application, "userCompanySecurityForm", 
/* 2235 */         this.application.getInfrastructure().getServletURL(), "POST");
/*      */     
/* 2237 */     userCompanySecurityForm.addElement(new FormHidden("cmd", "user-environment-security-editor"));
/* 2238 */     context.putDelivery("Form", userCompanySecurityForm);
/*      */ 
/*      */     
/* 2241 */     if (context.getSessionValue("NOTEPAD_USER_ENVIRONMENTS_VISIBLE") != null) {
/* 2242 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_USER_ENVIRONMENTS_VISIBLE"));
/*      */     }
/* 2244 */     return userCompanySecurityForm;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Form buildCompanyForm(Context context, User currentUser, Environment currentEnvironment) {
/* 2255 */     Form userCompanySecurityForm = new Form(this.application, "userCompanySecurityForm", 
/* 2256 */         this.application.getInfrastructure().getServletURL(), "POST");
/*      */     
/* 2258 */     Acl currentAcl = currentUser.getAcl();
/* 2259 */     Vector companyAcl = currentAcl.getCompanyAcl();
/* 2260 */     CompanyAcl company = null;
/* 2261 */     CompanyAcl acl = null;
/*      */ 
/*      */     
/* 2264 */     Vector childrenCompanies = currentEnvironment.getChildren();
/* 2265 */     Vector companyIDs = new Vector();
/* 2266 */     for (int k = 0; k < childrenCompanies.size(); k++) {
/* 2267 */       Company company1 = (Company)childrenCompanies.elementAt(k);
/* 2268 */       if (company1 != null) {
/* 2269 */         companyIDs.add(new Integer(company1.getStructureID()));
/*      */       }
/*      */     } 
/* 2272 */     for (int i = 0; i < companyAcl.size(); i++) {
/*      */       
/* 2274 */       company = (CompanyAcl)companyAcl.get(i);
/* 2275 */       if (company != null) {
/*      */         
/* 2277 */         Environment aclEnvironment = 
/* 2278 */           (Environment)MilestoneHelper.getStructureObject(company.getParentID());
/* 2279 */         Integer companyID = new Integer(company.getCompanyId());
/* 2280 */         if (companyIDs.contains(companyID)) {
/* 2281 */           acl = company;
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2289 */     String selectionValue = "View";
/* 2290 */     if (acl != null)
/*      */     {
/* 2292 */       if (acl.getAccessSelection() == 2)
/*      */       {
/* 2294 */         selectionValue = "Edit";
/*      */       }
/*      */     }
/*      */     
/* 2298 */     FormRadioButtonGroup selectionAccess = new FormRadioButtonGroup("1", selectionValue, "View, Edit", false);
/* 2299 */     selectionAccess.setTabIndex(10);
/* 2300 */     userCompanySecurityForm.addElement(selectionAccess);
/*      */ 
/*      */     
/* 2303 */     String scheduleValue = "View";
/* 2304 */     if (acl != null)
/*      */     {
/* 2306 */       if (acl.getAccessSchedule() == 2)
/*      */       {
/* 2308 */         scheduleValue = "Edit";
/*      */       }
/*      */     }
/* 2311 */     FormRadioButtonGroup scheduleAccess = new FormRadioButtonGroup("2", scheduleValue, "View, Edit", false);
/* 2312 */     scheduleAccess.setTabIndex(11);
/* 2313 */     userCompanySecurityForm.addElement(scheduleAccess);
/*      */ 
/*      */     
/* 2316 */     String manufacturingValue = "View";
/* 2317 */     if (acl != null)
/*      */     {
/* 2319 */       if (acl.getAccessManufacturing() == 2)
/*      */       {
/* 2321 */         manufacturingValue = "Edit";
/*      */       }
/*      */     }
/* 2324 */     FormRadioButtonGroup manufacturingAccess = new FormRadioButtonGroup("3", manufacturingValue, "View, Edit", false);
/* 2325 */     manufacturingAccess.setTabIndex(12);
/* 2326 */     userCompanySecurityForm.addElement(manufacturingAccess);
/*      */ 
/*      */     
/* 2329 */     String pfmValue = "View";
/* 2330 */     if (acl != null)
/*      */     {
/* 2332 */       if (acl.getAccessPfmForm() == 2)
/*      */       {
/* 2334 */         pfmValue = "Edit";
/*      */       }
/*      */     }
/* 2337 */     FormRadioButtonGroup pfmAccess = new FormRadioButtonGroup("4", pfmValue, "View, Edit", false);
/* 2338 */     pfmAccess.setTabIndex(13);
/* 2339 */     userCompanySecurityForm.addElement(pfmAccess);
/*      */ 
/*      */     
/* 2342 */     String bomValue = "View";
/* 2343 */     if (acl != null)
/*      */     {
/* 2345 */       if (acl.getAccessBomForm() == 2)
/*      */       {
/* 2347 */         bomValue = "Edit";
/*      */       }
/*      */     }
/* 2350 */     FormRadioButtonGroup bomAccess = new FormRadioButtonGroup("5", bomValue, "View, Edit", false);
/* 2351 */     bomAccess.setTabIndex(14);
/* 2352 */     userCompanySecurityForm.addElement(bomAccess);
/*      */ 
/*      */     
/* 2355 */     String reportValue = "View";
/* 2356 */     if (acl != null)
/*      */     {
/* 2358 */       if (acl.getAccessReport() == 2)
/*      */       {
/* 2360 */         reportValue = "Edit";
/*      */       }
/*      */     }
/* 2363 */     FormRadioButtonGroup reportAccess = new FormRadioButtonGroup("6", reportValue, "View, Edit", false);
/* 2364 */     reportAccess.setTabIndex(15);
/* 2365 */     userCompanySecurityForm.addElement(reportAccess);
/*      */ 
/*      */     
/* 2368 */     String templateValue = "View";
/* 2369 */     if (acl != null)
/*      */     {
/* 2371 */       if (acl.getAccessTemplate() == 2)
/*      */       {
/* 2373 */         templateValue = "Edit";
/*      */       }
/*      */     }
/* 2376 */     FormRadioButtonGroup templateAccess = new FormRadioButtonGroup("7", templateValue, "View, Edit", false);
/* 2377 */     templateAccess.setTabIndex(16);
/* 2378 */     userCompanySecurityForm.addElement(templateAccess);
/*      */ 
/*      */     
/* 2381 */     String taskValue = "View";
/* 2382 */     if (acl != null)
/*      */     {
/* 2384 */       if (acl.getAccessTask() == 2)
/*      */       {
/* 2386 */         taskValue = "Edit";
/*      */       }
/*      */     }
/* 2389 */     FormRadioButtonGroup taskAccess = new FormRadioButtonGroup("8", taskValue, "View, Edit", false);
/* 2390 */     taskAccess.setTabIndex(17);
/* 2391 */     userCompanySecurityForm.addElement(taskAccess);
/*      */ 
/*      */     
/* 2394 */     String dayTypeValue = "View";
/* 2395 */     if (acl != null)
/*      */     {
/* 2397 */       if (acl.getAccessDayType() == 2)
/*      */       {
/* 2399 */         dayTypeValue = "Edit";
/*      */       }
/*      */     }
/* 2402 */     FormRadioButtonGroup dayTypeAccess = new FormRadioButtonGroup("9", dayTypeValue, "View, Edit", false);
/* 2403 */     dayTypeAccess.setTabIndex(18);
/* 2404 */     userCompanySecurityForm.addElement(dayTypeAccess);
/*      */ 
/*      */     
/* 2407 */     String userValue = "View";
/* 2408 */     if (acl != null)
/*      */     {
/* 2410 */       if (acl.getAccessUser() == 2)
/*      */       {
/* 2412 */         userValue = "Edit";
/*      */       }
/*      */     }
/* 2415 */     FormRadioButtonGroup userAccess = new FormRadioButtonGroup("10", userValue, "View, Edit", false);
/* 2416 */     userAccess.setTabIndex(19);
/* 2417 */     userCompanySecurityForm.addElement(userAccess);
/*      */ 
/*      */     
/* 2420 */     String familyValue = "View";
/* 2421 */     if (acl != null)
/*      */     {
/* 2423 */       if (acl.getAccessFamily() == 2)
/*      */       {
/* 2425 */         familyValue = "Edit";
/*      */       }
/*      */     }
/* 2428 */     FormRadioButtonGroup familyAccess = new FormRadioButtonGroup("11", familyValue, "View, Edit", false);
/* 2429 */     familyAccess.setTabIndex(20);
/* 2430 */     userCompanySecurityForm.addElement(familyAccess);
/*      */ 
/*      */     
/* 2433 */     String environmentValue = "View";
/* 2434 */     if (acl != null)
/*      */     {
/* 2436 */       if (acl.getAccessEnvironment() == 2)
/*      */       {
/* 2438 */         environmentValue = "Edit";
/*      */       }
/*      */     }
/* 2441 */     FormRadioButtonGroup environmentAccess = new FormRadioButtonGroup("20", environmentValue, "View, Edit", false);
/* 2442 */     environmentAccess.setTabIndex(21);
/* 2443 */     userCompanySecurityForm.addElement(environmentAccess);
/*      */ 
/*      */     
/* 2446 */     String companyValue = "View";
/* 2447 */     if (acl != null)
/*      */     {
/* 2449 */       if (acl.getAccessCompany() == 2)
/*      */       {
/* 2451 */         companyValue = "Edit";
/*      */       }
/*      */     }
/* 2454 */     FormRadioButtonGroup companyAccess = new FormRadioButtonGroup("12", companyValue, "View, Edit", false);
/* 2455 */     companyAccess.setTabIndex(22);
/* 2456 */     userCompanySecurityForm.addElement(companyAccess);
/*      */ 
/*      */     
/* 2459 */     String divisionValue = "View";
/* 2460 */     if (acl != null)
/*      */     {
/* 2462 */       if (acl.getAccessDivision() == 2)
/*      */       {
/* 2464 */         divisionValue = "Edit";
/*      */       }
/*      */     }
/* 2467 */     FormRadioButtonGroup divisionAccess = new FormRadioButtonGroup("13", divisionValue, "View, Edit", false);
/* 2468 */     divisionAccess.setTabIndex(23);
/* 2469 */     userCompanySecurityForm.addElement(divisionAccess);
/*      */ 
/*      */     
/* 2472 */     String labelValue = "View";
/* 2473 */     if (acl != null)
/*      */     {
/* 2475 */       if (acl.getAccessLabel() == 2)
/*      */       {
/* 2477 */         labelValue = "Edit";
/*      */       }
/*      */     }
/* 2480 */     FormRadioButtonGroup labelAccess = new FormRadioButtonGroup("14", labelValue, "View, Edit", false);
/* 2481 */     labelAccess.setTabIndex(24);
/* 2482 */     userCompanySecurityForm.addElement(labelAccess);
/*      */ 
/*      */     
/* 2485 */     String tableValue = "View";
/* 2486 */     if (acl != null)
/*      */     {
/* 2488 */       if (acl.getAccessTable() == 2)
/*      */       {
/* 2490 */         tableValue = "Edit";
/*      */       }
/*      */     }
/* 2493 */     FormRadioButtonGroup tableAccess = new FormRadioButtonGroup("15", tableValue, "View, Edit", false);
/* 2494 */     tableAccess.setTabIndex(25);
/* 2495 */     userCompanySecurityForm.addElement(tableAccess);
/*      */ 
/*      */     
/* 2498 */     String parameterValue = "View";
/* 2499 */     if (acl != null)
/*      */     {
/* 2501 */       if (acl.getAccessParameter() == 2)
/*      */       {
/* 2503 */         parameterValue = "Edit";
/*      */       }
/*      */     }
/* 2506 */     FormRadioButtonGroup parameterAccess = new FormRadioButtonGroup("16", parameterValue, "View, Edit", false);
/* 2507 */     parameterAccess.setTabIndex(26);
/* 2508 */     userCompanySecurityForm.addElement(parameterAccess);
/*      */ 
/*      */     
/* 2511 */     String auditTrailValue = "View";
/* 2512 */     if (acl != null)
/*      */     {
/* 2514 */       if (acl.getAccessAuditTrail() == 2)
/*      */       {
/* 2516 */         auditTrailValue = "Edit";
/*      */       }
/*      */     }
/* 2519 */     FormRadioButtonGroup auditTrailAccess = new FormRadioButtonGroup("17", auditTrailValue, "View, Edit", false);
/* 2520 */     auditTrailAccess.setTabIndex(27);
/* 2521 */     userCompanySecurityForm.addElement(auditTrailAccess);
/*      */ 
/*      */     
/* 2524 */     String reportConfigValue = "View";
/* 2525 */     if (acl != null)
/*      */     {
/* 2527 */       if (acl.getAccessReportConfig() == 2)
/*      */       {
/* 2529 */         reportConfigValue = "Edit";
/*      */       }
/*      */     }
/* 2532 */     FormRadioButtonGroup reportConfigAccess = new FormRadioButtonGroup("18", reportConfigValue, "View, Edit", false);
/* 2533 */     reportConfigAccess.setTabIndex(28);
/* 2534 */     userCompanySecurityForm.addElement(reportConfigAccess);
/*      */ 
/*      */     
/* 2537 */     String priceCodeValue = "View";
/* 2538 */     if (acl != null)
/*      */     {
/* 2540 */       if (acl.getAccessPriceCode() == 2)
/*      */       {
/* 2542 */         priceCodeValue = "Edit";
/*      */       }
/*      */     }
/* 2545 */     FormRadioButtonGroup priceCodeAccess = new FormRadioButtonGroup("19", priceCodeValue, "View, Edit", false);
/* 2546 */     priceCodeAccess.setTabIndex(29);
/* 2547 */     userCompanySecurityForm.addElement(priceCodeAccess);
/*      */ 
/*      */     
/* 2550 */     FormTextField nameSrch = new FormTextField("nameSrch", "", false, 20);
/* 2551 */     nameSrch.setId("nameSrch");
/* 2552 */     userCompanySecurityForm.addElement(nameSrch);
/*      */     
/* 2554 */     userCompanySecurityForm.addElement(new FormHidden("cmd", "user-environment-security-editor"));
/*      */     
/* 2556 */     context.putDelivery("Form", userCompanySecurityForm);
/* 2557 */     context.putDelivery("currentUser", currentUser);
/*      */     
/* 2559 */     context.putDelivery("currentEnvironment", currentEnvironment);
/*      */ 
/*      */     
/* 2562 */     if (context.getSessionValue("NOTEPAD_USER_ENVIRONMENTS_VISIBLE") != null) {
/* 2563 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_USER_ENVIRONMENTS_VISIBLE"));
/*      */     }
/* 2565 */     return userCompanySecurityForm;
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
/*      */   public Notepad getUsersNotepad(Context context) {
/* 2606 */     Vector contents = new Vector();
/*      */     
/* 2608 */     if (MilestoneHelper.getNotepadFromSession(7, context) != null) {
/*      */       
/* 2610 */       Notepad notepad = MilestoneHelper.getNotepadFromSession(7, context);
/*      */       
/* 2612 */       if (notepad.getAllContents() == null) {
/*      */         
/* 2614 */         this.log.debug("---------Reseting note pad contents------------");
/* 2615 */         contents = UserManager.getInstance().getUserNotepadList(notepad);
/* 2616 */         notepad.setAllContents(contents);
/*      */       } 
/*      */       
/* 2619 */       return notepad;
/*      */     } 
/*      */ 
/*      */     
/* 2623 */     String[] columnNames = { "Login Name", "User Name", "Employed by" };
/* 2624 */     contents = UserManager.getInstance().getUserNotepadList(null);
/* 2625 */     Notepad notepad = new Notepad(contents, 0, 15, "Users", 7, columnNames);
/* 2626 */     UserManager.getInstance().setUserNotepadQuery(context, notepad);
/*      */     
/* 2628 */     return notepad;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Notepad getUserCompanyNotepad(Vector contents, Context context, int userId) {
/* 2639 */     if (MilestoneHelper.getNotepadFromSession(21, context) != null) {
/*      */       
/* 2641 */       Notepad notepad = MilestoneHelper.getNotepadFromSession(21, context);
/*      */       
/* 2643 */       contents = UserManager.getInstance().getUserEnvironmentNotepadList(userId, notepad);
/*      */       
/* 2645 */       notepad.setAllContents(contents);
/* 2646 */       return notepad;
/*      */     } 
/*      */ 
/*      */     
/* 2650 */     String[] columnNames = { "Environment Name", "Family Name" };
/*      */     
/* 2652 */     contents = UserManager.getInstance().getUserEnvironmentNotepadList(userId, null);
/* 2653 */     return new Notepad(contents, 0, 15, "User-Environments", 21, columnNames);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean userCompanySecuritySearch(Context context) {
/* 2663 */     Notepad notepad = MilestoneHelper.getNotepadFromSession(21, context);
/* 2664 */     UserManager.getInstance().setUserCompanyNotepadQuery(context, notepad);
/* 2665 */     userCompanySecurityEditor(context);
/*      */     
/* 2667 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/* 2672 */   private boolean userCompanySecuritySearchResults(Context context) { return context.includeJSP("user-company-security-search-results.jsp"); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean userCompanySecurityEditor(Context context) {
/* 2680 */     User user = MilestoneHelper.getScreenUser(context);
/* 2681 */     Company company = null;
/* 2682 */     Environment environment = null;
/* 2683 */     Vector contents = new Vector();
/*      */     
/* 2685 */     if (user == null) {
/* 2686 */       return userSecurityEditor(context);
/*      */     }
/* 2688 */     Notepad notepad = getUserCompanyNotepad(contents, context, user.getUserId());
/* 2689 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */     
/* 2691 */     environment = MilestoneHelper.getScreenUserEnvironment(context);
/* 2692 */     Vector environments = MilestoneHelper.getUserEnvironments(user.getUserId());
/*      */     
/* 2694 */     if (environments.size() > 0) {
/*      */       
/* 2696 */       Form form = null;
/* 2697 */       if (environment != null) {
/*      */         
/* 2699 */         form = buildCompanyForm(context, user, environment);
/*      */       }
/*      */       else {
/*      */         
/* 2703 */         form = buildBlankCompanyForm(context);
/* 2704 */         if (context.getSessionValue("NOTEPAD_USER_ENVIRONMENTS_VISIBLE") != null)
/* 2705 */           context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_USER_ENVIRONMENTS_VISIBLE")); 
/* 2706 */         return context.includeJSP("blank-user-company-security-editor.jsp");
/*      */       } 
/* 2708 */       return context.includeJSP("user-company-security-editor.jsp");
/*      */     } 
/*      */ 
/*      */     
/* 2712 */     return userSecurityEditor(context);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean userCompanySecuritySave(Context context) {
/* 2721 */     User sessionUser = MilestoneSecurity.getUser(context);
/*      */ 
/*      */ 
/*      */     
/* 2725 */     Environment currentEnvironment = null;
/* 2726 */     currentEnvironment = MilestoneHelper.getScreenUserEnvironment(context);
/*      */     
/* 2728 */     User currentUser = (User)context.getSessionValue("securityUser");
/* 2729 */     Acl currentAcl = currentUser.getAcl();
/* 2730 */     Vector companyAcl = currentAcl.getCompanyAcl();
/* 2731 */     CompanyAcl acl = null;
/* 2732 */     CompanyAcl tempAcl = null;
/*      */ 
/*      */ 
/*      */     
/* 2736 */     Vector childrenCompanies = currentEnvironment.getChildren();
/* 2737 */     Vector companyIDs = new Vector();
/* 2738 */     for (int k = 0; k < childrenCompanies.size(); k++) {
/* 2739 */       Company company1 = (Company)childrenCompanies.elementAt(k);
/* 2740 */       companyIDs.add(new Integer(company1.getStructureID()));
/*      */     } 
/*      */     
/* 2743 */     for (int i = 0; i < companyAcl.size(); i++) {
/*      */       
/* 2745 */       tempAcl = (CompanyAcl)companyAcl.get(i);
/* 2746 */       if (tempAcl != null) {
/*      */         
/* 2748 */         Environment aclEnvironment = 
/* 2749 */           (Environment)MilestoneHelper.getStructureObject(tempAcl.getParentID());
/* 2750 */         Integer companyID = new Integer(tempAcl.getCompanyId());
/*      */         
/* 2752 */         if (companyIDs.contains(companyID)) {
/* 2753 */           acl = tempAcl;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 2758 */     Form form = buildCompanyForm(context, currentUser, currentEnvironment);
/* 2759 */     form.setValues(context);
/*      */     
/* 2761 */     String selectionValue = form.getStringValue("1");
/* 2762 */     int selectionBool = 0;
/*      */     
/* 2764 */     if (currentAcl.getAccessSelection() && selectionValue.equalsIgnoreCase("Edit")) {
/* 2765 */       selectionBool = 2;
/* 2766 */     } else if (currentAcl.getAccessSelection()) {
/* 2767 */       selectionBool = 1;
/*      */     } 
/* 2769 */     acl.setAccessSelection(selectionBool);
/*      */ 
/*      */     
/* 2772 */     String scheduleValue = form.getStringValue("2");
/* 2773 */     int scheduleBool = 0;
/*      */     
/* 2775 */     if (currentAcl.getAccessSchedule() && scheduleValue.equalsIgnoreCase("Edit")) {
/* 2776 */       scheduleBool = 2;
/* 2777 */     } else if (currentAcl.getAccessSchedule()) {
/* 2778 */       scheduleBool = 1;
/*      */     } 
/* 2780 */     acl.setAccessSchedule(scheduleBool);
/*      */ 
/*      */     
/* 2783 */     String manufacturingValue = form.getStringValue("3");
/* 2784 */     int manufacturingBool = 0;
/*      */     
/* 2786 */     if (currentAcl.getAccessManufacturing() && manufacturingValue.equalsIgnoreCase("Edit")) {
/* 2787 */       manufacturingBool = 2;
/* 2788 */     } else if (currentAcl.getAccessManufacturing()) {
/* 2789 */       manufacturingBool = 1;
/*      */     } 
/* 2791 */     acl.setAccessManufacturing(manufacturingBool);
/*      */ 
/*      */     
/* 2794 */     String pfmValue = form.getStringValue("4");
/* 2795 */     int pfmBool = 0;
/*      */     
/* 2797 */     if (currentAcl.getAccessPfmForm() && pfmValue.equalsIgnoreCase("Edit")) {
/* 2798 */       pfmBool = 2;
/* 2799 */     } else if (currentAcl.getAccessPfmForm()) {
/* 2800 */       pfmBool = 1;
/*      */     } 
/* 2802 */     acl.setAccessPfmForm(pfmBool);
/*      */ 
/*      */     
/* 2805 */     String bomValue = form.getStringValue("5");
/* 2806 */     int bomBool = 0;
/*      */     
/* 2808 */     if (currentAcl.getAccessBomForm() && bomValue.equalsIgnoreCase("Edit")) {
/* 2809 */       bomBool = 2;
/* 2810 */     } else if (currentAcl.getAccessBomForm()) {
/* 2811 */       bomBool = 1;
/*      */     } 
/* 2813 */     acl.setAccessBomForm(bomBool);
/*      */ 
/*      */     
/* 2816 */     String reportValue = form.getStringValue("6");
/* 2817 */     int reportBool = 0;
/*      */     
/* 2819 */     if (currentAcl.getAccessReport() && reportValue.equalsIgnoreCase("Edit")) {
/* 2820 */       reportBool = 2;
/* 2821 */     } else if (currentAcl.getAccessReport()) {
/* 2822 */       reportBool = 1;
/*      */     } 
/* 2824 */     acl.setAccessReport(reportBool);
/*      */ 
/*      */     
/* 2827 */     String templateValue = form.getStringValue("7");
/* 2828 */     int templateBool = 0;
/*      */     
/* 2830 */     if (currentAcl.getAccessTemplate() && templateValue.equalsIgnoreCase("Edit")) {
/* 2831 */       templateBool = 2;
/* 2832 */     } else if (currentAcl.getAccessTemplate()) {
/* 2833 */       templateBool = 1;
/*      */     } 
/* 2835 */     acl.setAccessTemplate(templateBool);
/*      */ 
/*      */     
/* 2838 */     String taskValue = form.getStringValue("8");
/* 2839 */     int taskBool = 0;
/*      */     
/* 2841 */     if (currentAcl.getAccessTask() && taskValue.equalsIgnoreCase("Edit")) {
/* 2842 */       taskBool = 2;
/* 2843 */     } else if (currentAcl.getAccessTask()) {
/* 2844 */       taskBool = 1;
/*      */     } 
/* 2846 */     acl.setAccessTask(taskBool);
/*      */ 
/*      */     
/* 2849 */     String dayValue = form.getStringValue("9");
/* 2850 */     int dayBool = 0;
/*      */     
/* 2852 */     if (currentAcl.getAccessDayType() && dayValue.equalsIgnoreCase("Edit")) {
/* 2853 */       dayBool = 2;
/* 2854 */     } else if (currentAcl.getAccessDayType()) {
/* 2855 */       dayBool = 1;
/*      */     } 
/* 2857 */     acl.setAccessDayType(dayBool);
/*      */ 
/*      */     
/* 2860 */     String userValue = form.getStringValue("10");
/* 2861 */     int userBool = 0;
/*      */     
/* 2863 */     if (currentAcl.getAccessUser() && userValue.equalsIgnoreCase("Edit")) {
/* 2864 */       userBool = 2;
/* 2865 */     } else if (currentAcl.getAccessUser()) {
/* 2866 */       userBool = 1;
/*      */     } 
/* 2868 */     acl.setAccessUser(userBool);
/*      */ 
/*      */     
/* 2871 */     String familyValue = form.getStringValue("11");
/* 2872 */     int familyBool = 0;
/*      */     
/* 2874 */     if (currentAcl.getAccessFamily() && familyValue.equalsIgnoreCase("Edit")) {
/* 2875 */       familyBool = 2;
/* 2876 */     } else if (currentAcl.getAccessFamily()) {
/* 2877 */       familyBool = 1;
/*      */     } 
/* 2879 */     acl.setAccessFamily(familyBool);
/*      */ 
/*      */     
/* 2882 */     String environmentValue = form.getStringValue("20");
/* 2883 */     int environmentBool = 0;
/*      */     
/* 2885 */     if (currentAcl.getAccessEnvironment() && environmentValue.equalsIgnoreCase("Edit")) {
/* 2886 */       environmentBool = 2;
/* 2887 */     } else if (currentAcl.getAccessEnvironment()) {
/* 2888 */       environmentBool = 1;
/*      */     } 
/* 2890 */     acl.setAccessEnvironment(environmentBool);
/*      */ 
/*      */     
/* 2893 */     String companyValue = form.getStringValue("12");
/* 2894 */     int companyBool = 0;
/*      */     
/* 2896 */     if (currentAcl.getAccessCompany() && companyValue.equalsIgnoreCase("Edit")) {
/* 2897 */       companyBool = 2;
/* 2898 */     } else if (currentAcl.getAccessCompany()) {
/* 2899 */       companyBool = 1;
/*      */     } 
/* 2901 */     acl.setAccessCompany(companyBool);
/*      */ 
/*      */     
/* 2904 */     String divisionValue = form.getStringValue("13");
/* 2905 */     int divisionBool = 0;
/*      */     
/* 2907 */     if (currentAcl.getAccessDivision() && divisionValue.equalsIgnoreCase("Edit")) {
/* 2908 */       divisionBool = 2;
/* 2909 */     } else if (currentAcl.getAccessDivision()) {
/* 2910 */       divisionBool = 1;
/*      */     } 
/* 2912 */     acl.setAccessDivision(divisionBool);
/*      */ 
/*      */     
/* 2915 */     String labelValue = form.getStringValue("14");
/* 2916 */     int labelBool = 0;
/*      */     
/* 2918 */     if (currentAcl.getAccessLabel() && labelValue.equalsIgnoreCase("Edit")) {
/* 2919 */       labelBool = 2;
/* 2920 */     } else if (currentAcl.getAccessLabel()) {
/* 2921 */       labelBool = 1;
/*      */     } 
/* 2923 */     acl.setAccessLabel(labelBool);
/*      */ 
/*      */     
/* 2926 */     String tableValue = form.getStringValue("15");
/* 2927 */     int tableBool = 0;
/*      */     
/* 2929 */     if (currentAcl.getAccessTable() && tableValue.equalsIgnoreCase("Edit")) {
/* 2930 */       tableBool = 2;
/* 2931 */     } else if (currentAcl.getAccessTable()) {
/* 2932 */       tableBool = 1;
/*      */     } 
/* 2934 */     acl.setAccessTable(tableBool);
/*      */ 
/*      */     
/* 2937 */     String parameterValue = form.getStringValue("16");
/* 2938 */     int parameterBool = 0;
/*      */     
/* 2940 */     if (currentAcl.getAccessParameter() && parameterValue.equalsIgnoreCase("Edit")) {
/* 2941 */       parameterBool = 2;
/* 2942 */     } else if (currentAcl.getAccessParameter()) {
/* 2943 */       parameterBool = 1;
/*      */     } 
/* 2945 */     acl.setAccessParameter(parameterBool);
/*      */ 
/*      */     
/* 2948 */     String auditValue = form.getStringValue("17");
/* 2949 */     int auditBool = 0;
/*      */     
/* 2951 */     if (currentAcl.getAccessAuditTrail() && auditValue.equalsIgnoreCase("Edit")) {
/* 2952 */       auditBool = 2;
/* 2953 */     } else if (currentAcl.getAccessAuditTrail()) {
/* 2954 */       auditBool = 1;
/*      */     } 
/* 2956 */     acl.setAccessAuditTrail(auditBool);
/*      */ 
/*      */     
/* 2959 */     String configValue = form.getStringValue("18");
/* 2960 */     int configBool = 0;
/*      */     
/* 2962 */     if (currentAcl.getAccessReportConfig() && configValue.equalsIgnoreCase("Edit")) {
/* 2963 */       configBool = 2;
/* 2964 */     } else if (currentAcl.getAccessReportConfig()) {
/* 2965 */       configBool = 1;
/*      */     } 
/* 2967 */     acl.setAccessReportConfig(configBool);
/*      */ 
/*      */     
/* 2970 */     String priceValue = form.getStringValue("19");
/* 2971 */     int priceBool = 0;
/*      */     
/* 2973 */     if (currentAcl.getAccessPriceCode() && priceValue.equalsIgnoreCase("Edit")) {
/* 2974 */       priceBool = 2;
/* 2975 */     } else if (currentAcl.getAccessPriceCode()) {
/* 2976 */       priceBool = 1;
/*      */     } 
/* 2978 */     acl.setAccessPriceCode(priceBool);
/*      */     
/* 2980 */     if (!form.isUnchanged()) {
/*      */       
/* 2982 */       FormValidation formValidation = form.validate();
/* 2983 */       if (formValidation.isGood()) {
/*      */ 
/*      */         
/* 2986 */         Vector companyVector = currentEnvironment.getCompanies();
/* 2987 */         for (int j = 0; j < companyVector.size(); j++) {
/* 2988 */           Company environmentCompany = (Company)companyVector.elementAt(j);
/* 2989 */           UserManager.getInstance().saveCompany(currentUser, sessionUser, acl, environmentCompany);
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2995 */         Cache.flushCachedVariableAllUsers(currentUser.getUserId());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3002 */         this.log.debug("===============================clearUserCompaniesFromSession called.");
/* 3003 */         MilestoneHelper.clearUserCompaniesFromSession(context);
/*      */         
/* 3005 */         form = buildCompanyForm(context, currentUser, currentEnvironment);
/*      */         
/* 3007 */         context.putDelivery("Form", form);
/*      */ 
/*      */         
/* 3010 */         if (currentUser.getUserId() == sessionUser.getUserId()) {
/*      */           
/* 3012 */           context.removeSessionValue("user");
/*      */           
/* 3014 */           context.putSessionValue("UserDefaultsApplied", "true");
/* 3015 */           context.putSessionValue("user", currentUser);
/*      */         } 
/*      */ 
/*      */         
/* 3019 */         Vector contents = new Vector();
/* 3020 */         Notepad notepad = getUserCompanyNotepad(contents, context, currentUser.getUserId());
/* 3021 */         MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */       }
/*      */       else {
/*      */         
/* 3025 */         context.putDelivery("FormValidation", formValidation);
/*      */       } 
/*      */     } 
/* 3028 */     context.putDelivery("Form", form);
/* 3029 */     return context.includeJSP("user-company-security-editor.jsp");
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
/*      */   private Form buildNewForm(Context context) {
/* 3042 */     context.putSessionValue("showAssigned", "NEW");
/*      */ 
/*      */     
/* 3045 */     Form userSecurityForm = new Form(this.application, "userSecurityForm", 
/* 3046 */         this.application.getInfrastructure().getServletURL(), "POST");
/*      */ 
/*      */     
/* 3049 */     FormTextField login = new FormTextField("login", "", true, 30, 100);
/* 3050 */     login.setTabIndex(1);
/* 3051 */     userSecurityForm.addElement(login);
/*      */ 
/*      */     
/* 3054 */     FormPasswordField password = new FormPasswordField("password", "", true, 30, 30);
/* 3055 */     password.setTabIndex(2);
/* 3056 */     userSecurityForm.addElement(password);
/*      */ 
/*      */     
/* 3059 */     FormTextField reportto = new FormTextField("reportto", "", false, 30, 30);
/* 3060 */     reportto.setTabIndex(3);
/* 3061 */     userSecurityForm.addElement(reportto);
/*      */ 
/*      */ 
/*      */     
/* 3065 */     FormTextField fullname = new FormTextField("fullname", "", true, 30, 100);
/* 3066 */     fullname.setTabIndex(4);
/* 3067 */     userSecurityForm.addElement(fullname);
/*      */ 
/*      */     
/* 3070 */     Vector userLocations = Cache.getLookupDetailValuesFromDatabase(53);
/* 3071 */     FormDropDownMenu location = MilestoneHelper.getLookupDropDown("location", userLocations, "", false, true);
/* 3072 */     location.setTabIndex(5);
/* 3073 */     userSecurityForm.addElement(location);
/*      */ 
/*      */     
/* 3076 */     Vector families = Cache.getInstance().getFamilies();
/*      */     
/* 3078 */     FormDropDownMenu employedby = MilestoneHelper.getCorporateStructureDropDown("employedby", families, "", false, true);
/* 3079 */     employedby.setTabIndex(6);
/* 3080 */     userSecurityForm.addElement(employedby);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3085 */     FormTextField email = new FormTextField("email", "", false, 30, 50);
/* 3086 */     email.setTabIndex(7);
/* 3087 */     userSecurityForm.addElement(email);
/*      */     
/* 3089 */     FormTextField phone = new FormTextField("phone", "", false, 30, 30);
/* 3090 */     phone.setTabIndex(8);
/* 3091 */     userSecurityForm.addElement(phone);
/*      */     
/* 3093 */     FormTextField fax = new FormTextField("fax", "", false, 30, 30);
/* 3094 */     fax.setTabIndex(11);
/* 3095 */     userSecurityForm.addElement(fax);
/*      */     
/* 3097 */     FormCheckBox inactive = new FormCheckBox("inactive", "Inactive", false, false);
/* 3098 */     inactive.setId("inactive");
/* 3099 */     inactive.setTabIndex(12);
/* 3100 */     userSecurityForm.addElement(inactive);
/*      */ 
/*      */     
/* 3103 */     FormCheckBox administrator = new FormCheckBox("administrator", "Administrator", false, false);
/* 3104 */     administrator.setId("adminstrator");
/* 3105 */     administrator.setTabIndex(13);
/* 3106 */     userSecurityForm.addElement(administrator);
/*      */ 
/*      */ 
/*      */     
/* 3110 */     FormDropDownMenu deptFilter = MilestoneHelper.getDepartmentDropDown("deptFilter", "All", false);
/* 3111 */     String[] values = deptFilter.getValueList();
/* 3112 */     String[] menuText = deptFilter.getMenuTextList();
/*      */     
/* 3114 */     values[0] = "All";
/* 3115 */     menuText[0] = "All";
/* 3116 */     deptFilter.setValueList(values);
/* 3117 */     deptFilter.setMenuTextList(menuText);
/* 3118 */     deptFilter.setTabIndex(12);
/* 3119 */     userSecurityForm.addElement(deptFilter);
/*      */     
/* 3121 */     FormRadioButtonGroup IsModifyDept = new FormRadioButtonGroup("IsModifyDept", "Yes", "Yes, No", false);
/* 3122 */     IsModifyDept.setTabIndex(13);
/* 3123 */     userSecurityForm.addElement(IsModifyDept);
/*      */ 
/*      */ 
/*      */     
/* 3127 */     FormDropDownMenu filter = new FormDropDownMenu("filter", "All", "All,Only Label Tasks, Only UML Tasks", true);
/* 3128 */     filter.setTabIndex(9);
/* 3129 */     userSecurityForm.addElement(filter);
/*      */ 
/*      */     
/* 3132 */     FormRadioButtonGroup IsModify = new FormRadioButtonGroup("IsModify", "Yes", "Yes, No", false);
/* 3133 */     IsModify.setTabIndex(10);
/* 3134 */     userSecurityForm.addElement(IsModify);
/*      */ 
/*      */     
/* 3137 */     Vector environments = Cache.getInstance().getEnvironments();
/* 3138 */     for (int i = 0; i < environments.size(); i++) {
/*      */       
/* 3140 */       Environment environment = (Environment)environments.elementAt(i);
/* 3141 */       if (environment != null) {
/*      */         
/* 3143 */         FormCheckBox environmentCheckbox = new FormCheckBox("ue" + environment.getStructureID(), "ue" + environment.getStructureID(), environment.getName(), false, false);
/* 3144 */         environmentCheckbox.setId("ue" + environment.getStructureID());
/* 3145 */         userSecurityForm.addElement(environmentCheckbox);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3152 */     String selectionValue = "Not Available";
/* 3153 */     FormRadioButtonGroup selectionAccess = new FormRadioButtonGroup("1", selectionValue, "Not Available, Available", false);
/* 3154 */     selectionAccess.setTabIndex(10);
/* 3155 */     userSecurityForm.addElement(selectionAccess);
/*      */ 
/*      */     
/* 3158 */     String scheduleValue = "Not Available";
/* 3159 */     FormRadioButtonGroup scheduleAccess = new FormRadioButtonGroup("2", scheduleValue, "Not Available, Available", false);
/* 3160 */     scheduleAccess.setTabIndex(11);
/* 3161 */     userSecurityForm.addElement(scheduleAccess);
/*      */ 
/*      */     
/* 3164 */     String manufacturingValue = "Not Available";
/* 3165 */     FormRadioButtonGroup manufacturingAccess = new FormRadioButtonGroup("3", manufacturingValue, "Not Available, Available", false);
/* 3166 */     manufacturingAccess.setTabIndex(12);
/* 3167 */     userSecurityForm.addElement(manufacturingAccess);
/*      */ 
/*      */     
/* 3170 */     String pfmValue = "Not Available";
/* 3171 */     FormRadioButtonGroup pfmAccess = new FormRadioButtonGroup("4", pfmValue, "Not Available, Available", false);
/* 3172 */     pfmAccess.setTabIndex(13);
/* 3173 */     userSecurityForm.addElement(pfmAccess);
/*      */ 
/*      */     
/* 3176 */     String bomValue = "Not Available";
/* 3177 */     FormRadioButtonGroup bomAccess = new FormRadioButtonGroup("5", bomValue, "Not Available, Available", false);
/* 3178 */     bomAccess.setTabIndex(14);
/* 3179 */     userSecurityForm.addElement(bomAccess);
/*      */ 
/*      */     
/* 3182 */     String reportValue = "Not Available";
/* 3183 */     FormRadioButtonGroup reportAccess = new FormRadioButtonGroup("6", reportValue, "Not Available, Available", false);
/* 3184 */     reportAccess.setTabIndex(15);
/* 3185 */     userSecurityForm.addElement(reportAccess);
/*      */ 
/*      */     
/* 3188 */     String templateValue = "Not Available";
/* 3189 */     FormRadioButtonGroup templateAccess = new FormRadioButtonGroup("7", templateValue, "Not Available, Available", false);
/* 3190 */     templateAccess.setTabIndex(16);
/* 3191 */     userSecurityForm.addElement(templateAccess);
/*      */ 
/*      */     
/* 3194 */     String taskValue = "Not Available";
/* 3195 */     FormRadioButtonGroup taskAccess = new FormRadioButtonGroup("8", taskValue, "Not Available, Available", false);
/* 3196 */     taskAccess.setTabIndex(17);
/* 3197 */     userSecurityForm.addElement(taskAccess);
/*      */ 
/*      */     
/* 3200 */     String dayTypeValue = "Not Available";
/* 3201 */     FormRadioButtonGroup dayTypeAccess = new FormRadioButtonGroup("9", dayTypeValue, "Not Available, Available", false);
/* 3202 */     dayTypeAccess.setTabIndex(18);
/* 3203 */     userSecurityForm.addElement(dayTypeAccess);
/*      */ 
/*      */     
/* 3206 */     String userValue = "Not Available";
/* 3207 */     FormRadioButtonGroup userAccess = new FormRadioButtonGroup("10", userValue, "Not Available, Available", false);
/* 3208 */     userAccess.setTabIndex(19);
/* 3209 */     userSecurityForm.addElement(userAccess);
/*      */ 
/*      */     
/* 3212 */     String familyValue = "Not Available";
/* 3213 */     FormRadioButtonGroup familyAccess = new FormRadioButtonGroup("11", familyValue, "Not Available, Available", false);
/* 3214 */     familyAccess.setTabIndex(20);
/* 3215 */     userSecurityForm.addElement(familyAccess);
/*      */ 
/*      */     
/* 3218 */     String environmentValue = "Not Available";
/* 3219 */     FormRadioButtonGroup environmentAccess = new FormRadioButtonGroup("20", environmentValue, "Not Available, Available", false);
/* 3220 */     environmentAccess.setTabIndex(21);
/* 3221 */     userSecurityForm.addElement(environmentAccess);
/*      */ 
/*      */     
/* 3224 */     String companyValue = "Not Available";
/* 3225 */     FormRadioButtonGroup companyAccess = new FormRadioButtonGroup("12", companyValue, "Not Available, Available", false);
/* 3226 */     companyAccess.setTabIndex(22);
/* 3227 */     userSecurityForm.addElement(companyAccess);
/*      */ 
/*      */     
/* 3230 */     String divisionValue = "Not Available";
/* 3231 */     FormRadioButtonGroup divisionAccess = new FormRadioButtonGroup("13", divisionValue, "Not Available, Available", false);
/* 3232 */     divisionAccess.setTabIndex(23);
/* 3233 */     userSecurityForm.addElement(divisionAccess);
/*      */ 
/*      */     
/* 3236 */     String labelValue = "Not Available";
/* 3237 */     FormRadioButtonGroup labelAccess = new FormRadioButtonGroup("14", labelValue, "Not Available, Available", false);
/* 3238 */     labelAccess.setTabIndex(24);
/* 3239 */     userSecurityForm.addElement(labelAccess);
/*      */ 
/*      */     
/* 3242 */     String tableValue = "Not Available";
/* 3243 */     FormRadioButtonGroup tableAccess = new FormRadioButtonGroup("15", tableValue, "Not Available, Available", false);
/* 3244 */     tableAccess.setTabIndex(25);
/* 3245 */     userSecurityForm.addElement(tableAccess);
/*      */ 
/*      */     
/* 3248 */     String parameterValue = "Not Available";
/* 3249 */     FormRadioButtonGroup parameterAccess = new FormRadioButtonGroup("16", parameterValue, "Not Available, Available", false);
/* 3250 */     parameterAccess.setTabIndex(26);
/* 3251 */     userSecurityForm.addElement(parameterAccess);
/*      */ 
/*      */     
/* 3254 */     String auditTrailValue = "Not Available";
/* 3255 */     FormRadioButtonGroup auditTrailAccess = new FormRadioButtonGroup("17", auditTrailValue, "Not Available, Available", false);
/* 3256 */     auditTrailAccess.setTabIndex(27);
/* 3257 */     userSecurityForm.addElement(auditTrailAccess);
/*      */ 
/*      */     
/* 3260 */     String reportConfigValue = "Not Available";
/* 3261 */     FormRadioButtonGroup reportConfigAccess = new FormRadioButtonGroup("18", reportConfigValue, "Not Available, Available", false);
/* 3262 */     reportConfigAccess.setTabIndex(28);
/* 3263 */     userSecurityForm.addElement(reportConfigAccess);
/*      */ 
/*      */     
/* 3266 */     String priceCodeValue = "Not Available";
/* 3267 */     FormRadioButtonGroup priceCodeAccess = new FormRadioButtonGroup("19", priceCodeValue, "Not Available, Available", false);
/* 3268 */     priceCodeAccess.setTabIndex(29);
/* 3269 */     userSecurityForm.addElement(priceCodeAccess);
/*      */ 
/*      */     
/* 3272 */     FormTextField lastUpdated = new FormTextField("lastUpdatedDate", false, 50);
/* 3273 */     userSecurityForm.addElement(lastUpdated);
/*      */     
/* 3275 */     FormTextField lastUpdatedBy = new FormTextField("lastUpdatedBy", false, 50);
/* 3276 */     userSecurityForm.addElement(lastUpdatedBy);
/*      */ 
/*      */     
/* 3279 */     FormTextField nameSrch = new FormTextField("nameSrch", "", false, 20);
/* 3280 */     nameSrch.setId("nameSrch");
/* 3281 */     userSecurityForm.addElement(nameSrch);
/*      */     
/* 3283 */     FormTextField userNameSrch = new FormTextField("userNameSrch", "", false, 20);
/* 3284 */     userNameSrch.setId("userNameSrch");
/* 3285 */     userSecurityForm.addElement(userNameSrch);
/*      */ 
/*      */     
/* 3288 */     String employedBySrcString = "";
/* 3289 */     FormDropDownMenu employedBySrc = MilestoneHelper.getCorporateStructureDropDown("employedBySrc", families, employedBySrcString, true, true);
/* 3290 */     employedBySrc.setId("employedBySrc");
/* 3291 */     userSecurityForm.addElement(employedBySrc);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3296 */     FormDropDownMenu environmentDescriptionSearch = MilestoneHelper.getCorporateStructureDropDown("EnvironmentDescriptionSearch", environments, "", false, true);
/* 3297 */     environmentDescriptionSearch.setId("EnvironmentDescriptionSearch");
/* 3298 */     userSecurityForm.addElement(environmentDescriptionSearch);
/*      */ 
/*      */ 
/*      */     
/* 3302 */     userSecurityForm.addElement(new FormHidden("cmd", "user-security-new"));
/* 3303 */     context.putDelivery("Form", userSecurityForm);
/*      */ 
/*      */     
/* 3306 */     if (context.getSessionValue("NOTEPAD_USER_VISIBLE") != null) {
/* 3307 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_USER_VISIBLE"));
/*      */     }
/* 3309 */     return userSecurityForm;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean userSecuritySort(Context context) {
/* 3315 */     int sort = Integer.parseInt(context.getParameter("OrderBy"));
/* 3316 */     int userId = ((User)context.getSessionValue("user")).getUserId();
/*      */     
/* 3318 */     Notepad notepad = MilestoneHelper.getNotepadFromSession(7, context);
/*      */     
/* 3320 */     if (notepad.getSearchQuery() == null || notepad.getSearchQuery().equals("")) {
/* 3321 */       notepad.setSearchQuery(UserManager.getInstance().getDefaultQuery());
/*      */     }
/*      */     
/* 3324 */     notepad.setOrderBy(" ORDER BY vi_all_user.[" + MilestoneConstants.SORT_USER[sort] + "]");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3329 */     notepad.setAllContents(null);
/* 3330 */     notepad = getUsersNotepad(context);
/* 3331 */     notepad.goToSelectedPage();
/*      */ 
/*      */     
/* 3334 */     userSecurityEditor(context);
/*      */     
/* 3336 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean userCompanySecuritySort(Context context) {
/* 3345 */     int sort = Integer.parseInt(context.getParameter("OrderBy"));
/* 3346 */     User user = MilestoneHelper.getScreenUser(context);
/* 3347 */     int userId = user.getUserId();
/*      */     
/* 3349 */     Notepad notepad = MilestoneHelper.getNotepadFromSession(21, context);
/*      */     
/* 3351 */     if (notepad.getSearchQuery() == null || notepad.getSearchQuery().equals("")) {
/* 3352 */       notepad.setSearchQuery(UserManager.getInstance().getDefaultUserCompanyQuery(userId));
/*      */     }
/* 3354 */     notepad.setOrderBy(" ORDER BY " + MilestoneConstants.SORT_COMPANY_USER[sort]);
/*      */ 
/*      */     
/* 3357 */     notepad.setAllContents(null);
/* 3358 */     notepad = getUsersNotepad(context);
/* 3359 */     notepad.goToSelectedPage();
/*      */ 
/*      */     
/* 3362 */     userCompanySecurityEditor(context);
/*      */     
/* 3364 */     return true;
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
/*      */   private boolean userGoToBlank(Context context) {
/* 3380 */     context.putDelivery("isNotePadVisible", new Boolean(MilestoneHelper.getNotePadVisiblitiy(7, context)));
/*      */     
/* 3382 */     Form form = new Form(this.application, "form", this.application.getInfrastructure().getServletURL(), "Post");
/* 3383 */     form.addElement(new FormHidden("cmd", "daytype-editor"));
/* 3384 */     form.addElement(new FormHidden("OrderBy", "", true));
/*      */ 
/*      */     
/* 3387 */     String employedBySrcString = "";
/*      */     
/* 3389 */     Vector families = Cache.getInstance().getFamilies();
/* 3390 */     FormDropDownMenu employedBySrc = MilestoneHelper.getCorporateStructureDropDown("employedBySrc", families, employedBySrcString, true, true);
/* 3391 */     employedBySrc.setId("employedBySrc");
/* 3392 */     form.addElement(employedBySrc);
/*      */ 
/*      */ 
/*      */     
/* 3396 */     Vector environments = Cache.getInstance().getEnvironments();
/* 3397 */     FormDropDownMenu environmentDescriptionSearch = MilestoneHelper.getCorporateStructureDropDown("EnvironmentDescriptionSearch", environments, "", false, true);
/* 3398 */     environmentDescriptionSearch.setId("EnvironmentDescriptionSearch");
/* 3399 */     form.addElement(environmentDescriptionSearch);
/*      */ 
/*      */ 
/*      */     
/* 3403 */     context.putDelivery("Form", form);
/*      */     
/* 3405 */     return context.includeJSP("blank-user-security-editor.jsp");
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
/*      */   private boolean userReleasingFamilySave(Dispatcher dispatcher, Context context) {
/* 3417 */     ReleasingFamily.save(context);
/*      */ 
/*      */ 
/*      */     
/* 3421 */     return userSecurityEditor(context);
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
/*      */   public static void makeDynamic(User savedUser, Context context, User sessionUser) {
/* 3433 */     context.putSessionValue("ScheduleTaskSort", "-1");
/*      */     
/* 3435 */     context.putSessionValue("ScheduleTaskSortDigital", "-1");
/*      */     
/* 3437 */     context.putSessionValue("ResetOpeningScreen", "true");
/*      */     
/* 3439 */     User userClone = null;
/*      */     try {
/* 3441 */       userClone = (User)sessionUser.clone();
/*      */     }
/* 3443 */     catch (CloneNotSupportedException ex) {
/* 3444 */       userClone = sessionUser;
/*      */     } 
/* 3446 */     userClone.SS_searchInitiated = false;
/* 3447 */     context.putSessionValue("ResetSearchVariables", userClone);
/*      */     
/* 3449 */     context.putSessionValue("ResetSelectionSortOrder", "true");
/*      */   }
/*      */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\SecurityHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */