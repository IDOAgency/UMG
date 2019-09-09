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
/*  528 */       if (environments != null)
/*      */       {
/*  530 */         for (int i = 0; i < environments.size(); i++) {
/*      */           
/*  532 */           Environment environment = (Environment)environments.get(i);
/*  533 */           if (environment != null && form.getElement("ue" + environment.getStructureID()) != null)
/*      */           {
/*  535 */             if (((FormCheckBox)form.getElement("ue" + environment.getStructureID())).isChecked())
/*      */             {
/*  537 */               checkedEnvironments.add(environment);
/*      */             }
/*      */           }
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  547 */       String selectionValue = form.getStringValue("1");
/*  548 */       boolean selectionBool = false;
/*      */       
/*  550 */       if (selectionValue.equalsIgnoreCase("Available"))
/*      */       {
/*  552 */         selectionBool = true;
/*      */       }
/*  554 */       acl.setAccessSelection(selectionBool);
/*      */ 
/*      */       
/*  557 */       String scheduleValue = form.getStringValue("2");
/*  558 */       boolean scheduleBool = false;
/*      */       
/*  560 */       if (scheduleValue.equalsIgnoreCase("Available"))
/*      */       {
/*  562 */         scheduleBool = true;
/*      */       }
/*  564 */       acl.setAccessSchedule(scheduleBool);
/*      */ 
/*      */       
/*  567 */       String manufacturingValue = form.getStringValue("3");
/*  568 */       boolean manufacturingBool = false;
/*      */       
/*  570 */       if (manufacturingValue.equalsIgnoreCase("Available"))
/*      */       {
/*  572 */         manufacturingBool = true;
/*      */       }
/*  574 */       acl.setAccessManufacturing(manufacturingBool);
/*      */ 
/*      */       
/*  577 */       String pfmValue = form.getStringValue("4");
/*  578 */       boolean pfmBool = false;
/*      */       
/*  580 */       if (pfmValue.equalsIgnoreCase("Available"))
/*      */       {
/*  582 */         pfmBool = true;
/*      */       }
/*  584 */       acl.setAccessPfmForm(pfmBool);
/*      */ 
/*      */       
/*  587 */       String bomValue = form.getStringValue("5");
/*  588 */       boolean bomBool = false;
/*      */       
/*  590 */       if (bomValue.equalsIgnoreCase("Available"))
/*      */       {
/*  592 */         bomBool = true;
/*      */       }
/*  594 */       acl.setAccessBomForm(bomBool);
/*      */ 
/*      */       
/*  597 */       String reportValue = form.getStringValue("6");
/*  598 */       boolean reportBool = false;
/*      */       
/*  600 */       if (reportValue.equalsIgnoreCase("Available"))
/*      */       {
/*  602 */         reportBool = true;
/*      */       }
/*  604 */       acl.setAccessReport(reportBool);
/*      */ 
/*      */       
/*  607 */       String templateValue = form.getStringValue("7");
/*  608 */       boolean templateBool = false;
/*      */       
/*  610 */       if (templateValue.equalsIgnoreCase("Available"))
/*      */       {
/*  612 */         templateBool = true;
/*      */       }
/*  614 */       acl.setAccessTemplate(templateBool);
/*      */ 
/*      */       
/*  617 */       String taskValue = form.getStringValue("8");
/*  618 */       boolean taskBool = false;
/*      */       
/*  620 */       if (taskValue.equalsIgnoreCase("Available"))
/*      */       {
/*  622 */         taskBool = true;
/*      */       }
/*  624 */       acl.setAccessTask(taskBool);
/*      */ 
/*      */       
/*  627 */       String dayValue = form.getStringValue("9");
/*  628 */       boolean dayBool = false;
/*      */       
/*  630 */       if (dayValue.equalsIgnoreCase("Available"))
/*      */       {
/*  632 */         dayBool = true;
/*      */       }
/*  634 */       acl.setAccessDayType(dayBool);
/*      */ 
/*      */       
/*  637 */       String userValue = form.getStringValue("10");
/*  638 */       boolean userBool = false;
/*      */       
/*  640 */       if (userValue.equalsIgnoreCase("Available"))
/*      */       {
/*  642 */         userBool = true;
/*      */       }
/*  644 */       acl.setAccessUser(userBool);
/*      */ 
/*      */       
/*  647 */       String familyValue = form.getStringValue("11");
/*  648 */       boolean familyBool = false;
/*      */       
/*  650 */       if (familyValue.equalsIgnoreCase("Available"))
/*      */       {
/*  652 */         familyBool = true;
/*      */       }
/*  654 */       acl.setAccessFamily(familyBool);
/*      */ 
/*      */       
/*  657 */       String environmentValue = form.getStringValue("20");
/*  658 */       boolean environmentBool = false;
/*  659 */       if (environmentValue.equalsIgnoreCase("Available"))
/*      */       {
/*  661 */         environmentBool = true;
/*      */       }
/*      */       
/*  664 */       acl.setAccessEnvironment(environmentBool);
/*      */ 
/*      */       
/*  667 */       String companyValue = form.getStringValue("12");
/*  668 */       boolean companyBool = false;
/*      */       
/*  670 */       if (companyValue.equalsIgnoreCase("Available"))
/*      */       {
/*  672 */         companyBool = true;
/*      */       }
/*      */       
/*  675 */       acl.setAccessCompany(companyBool);
/*      */ 
/*      */       
/*  678 */       String divisionValue = form.getStringValue("13");
/*  679 */       boolean divisionBool = false;
/*      */       
/*  681 */       if (divisionValue.equalsIgnoreCase("Available"))
/*      */       {
/*  683 */         divisionBool = true;
/*      */       }
/*  685 */       acl.setAccessDivision(divisionBool);
/*      */ 
/*      */       
/*  688 */       String labelValue = form.getStringValue("14");
/*  689 */       boolean labelBool = false;
/*      */       
/*  691 */       if (labelValue.equalsIgnoreCase("Available"))
/*      */       {
/*  693 */         labelBool = true;
/*      */       }
/*  695 */       acl.setAccessLabel(labelBool);
/*      */ 
/*      */       
/*  698 */       String tableValue = form.getStringValue("15");
/*  699 */       boolean tableBool = false;
/*      */       
/*  701 */       if (tableValue.equalsIgnoreCase("Available"))
/*      */       {
/*  703 */         tableBool = true;
/*      */       }
/*  705 */       acl.setAccessTable(tableBool);
/*      */ 
/*      */       
/*  708 */       String parameterValue = form.getStringValue("16");
/*  709 */       boolean parameterBool = false;
/*      */       
/*  711 */       if (parameterValue.equalsIgnoreCase("Available"))
/*      */       {
/*  713 */         parameterBool = true;
/*      */       }
/*  715 */       acl.setAccessParameter(parameterBool);
/*      */ 
/*      */       
/*  718 */       String auditValue = form.getStringValue("17");
/*  719 */       boolean auditBool = false;
/*      */       
/*  721 */       if (auditValue.equalsIgnoreCase("Available"))
/*      */       {
/*  723 */         auditBool = true;
/*      */       }
/*  725 */       acl.setAccessAuditTrail(auditBool);
/*      */ 
/*      */       
/*  728 */       String configValue = form.getStringValue("18");
/*  729 */       boolean configBool = false;
/*      */       
/*  731 */       if (configValue.equalsIgnoreCase("Available"))
/*      */       {
/*  733 */         configBool = true;
/*      */       }
/*  735 */       acl.setAccessReportConfig(configBool);
/*      */ 
/*      */       
/*  738 */       String priceValue = form.getStringValue("19");
/*  739 */       boolean priceBool = false;
/*      */       
/*  741 */       if (priceValue.equalsIgnoreCase("Available"))
/*      */       {
/*  743 */         priceBool = true;
/*      */       }
/*  745 */       acl.setAccessPriceCode(priceBool);
/*      */ 
/*      */ 
/*      */       
/*  749 */       UserPreferences up = new UserPreferences();
/*      */ 
/*      */       
/*  752 */       String openingScreen = userPrefForm.getStringValue("openingScreen");
/*  753 */       up.setOpeningScreen(Integer.parseInt(openingScreen));
/*      */       
/*  755 */       String autoCloseRadio = userPrefForm.getStringValue("autoCloseRadio");
/*  756 */       up.setAutoClose(Integer.parseInt(autoCloseRadio));
/*      */       
/*  758 */       String autoCloseDays = userPrefForm.getStringValue("autoCloseDays");
/*  759 */       up.setAutoCloseDays(Integer.parseInt(autoCloseDays));
/*      */ 
/*      */       
/*  762 */       String generalSortBy = userPrefForm.getStringValue("sortBy");
/*  763 */       up.setNotepadSortBy(Integer.parseInt(generalSortBy));
/*      */       
/*  765 */       String generalOrder = userPrefForm.getStringValue("order");
/*  766 */       up.setNotepadOrder(Integer.parseInt(generalOrder));
/*      */       
/*  768 */       String generalProdType = userPrefForm.getStringValue("productType");
/*  769 */       up.setNotepadProductType(Integer.parseInt(generalProdType));
/*      */ 
/*      */       
/*  772 */       String selectionReleasingFamilies = userPrefForm.getStringValue("releasingFamilies");
/*  773 */       up.setSelectionReleasingFamily(Integer.parseInt(selectionReleasingFamilies));
/*      */       
/*  775 */       String selectionEnvironment = userPrefForm.getStringValue("envMenu");
/*  776 */       up.setSelectionEnvironment(Integer.parseInt(selectionEnvironment));
/*      */       
/*  778 */       String selectionContactList = userPrefForm.getStringValue("ContactList");
/*  779 */       up.setSelectionLabelContact(Integer.parseInt(selectionContactList));
/*      */       
/*  781 */       String selectionProductType = userPrefForm.getStringValue("selectionProductType");
/*  782 */       up.setSelectionProductType(Integer.parseInt(selectionProductType));
/*      */       
/*  784 */       FormCheckBox selectionStatus = (FormCheckBox)userPrefForm.getElement("status");
/*  785 */       up.setSelectionStatus(0);
/*  786 */       if (selectionStatus.isChecked()) {
/*  787 */         up.setSelectionStatus(1);
/*      */       }
/*  789 */       FormCheckBox selectionPriorCriteria = (FormCheckBox)userPrefForm.getElement("priorCriteria");
/*  790 */       up.setSelectionPriorCriteria(0);
/*  791 */       if (selectionPriorCriteria.isChecked()) {
/*  792 */         up.setSelectionPriorCriteria(1);
/*      */       }
/*      */       
/*  795 */       String sortBySchedule = userPrefForm.getStringValue("sortBySchedule");
/*  796 */       up.setSchedulePhysicalSortBy(Integer.parseInt(sortBySchedule));
/*      */       
/*  798 */       String ownerSchedule = userPrefForm.getStringValue("ownerSchedule");
/*  799 */       up.setSchedulePhysicalOwner(Integer.parseInt(ownerSchedule));
/*      */ 
/*      */       
/*  802 */       String sortByDigitalSchedule = userPrefForm.getStringValue("sortByDigitalSchedule");
/*  803 */       up.setScheduleDigitalSortBy(Integer.parseInt(sortByDigitalSchedule));
/*      */       
/*  805 */       String ownerDigitalSchedule = userPrefForm.getStringValue("ownerDigitalSchedule");
/*  806 */       up.setScheduleDigitalOwner(Integer.parseInt(ownerDigitalSchedule));
/*      */ 
/*      */       
/*  809 */       String releaseType = userPrefForm.getStringValue("releaseType");
/*  810 */       up.setReportsReleaseType(Integer.parseInt(releaseType));
/*      */       
/*  812 */       String releasingFamiliesReports = userPrefForm.getStringValue("releasingFamiliesReports");
/*  813 */       up.setReportsReleasingFamily(Integer.parseInt(releasingFamiliesReports));
/*      */       
/*  815 */       String envMenuReports = userPrefForm.getStringValue("envMenuReports");
/*  816 */       up.setReportsEnvironment(Integer.parseInt(envMenuReports));
/*      */       
/*  818 */       String ContactListReports = userPrefForm.getStringValue("ContactListReports");
/*  819 */       up.setReportsLabelContact(Integer.parseInt(ContactListReports));
/*      */       
/*  821 */       String umlContact = userPrefForm.getStringValue("umlContact");
/*  822 */       up.setReportsUMLContact(Integer.parseInt(umlContact));
/*      */       
/*  824 */       FormCheckBox spAll = (FormCheckBox)userPrefForm.getElement("statusReportsAll");
/*  825 */       FormCheckBox spActive = (FormCheckBox)userPrefForm.getElement("statusReportsActive");
/*  826 */       FormCheckBox spTBS = (FormCheckBox)userPrefForm.getElement("statusReportsTBS");
/*  827 */       FormCheckBox spClosed = (FormCheckBox)userPrefForm.getElement("statusReportsClosed");
/*  828 */       FormCheckBox spCancelled = (FormCheckBox)userPrefForm.getElement("statusReportsCancelled");
/*      */       
/*  830 */       up.setReportsStatusAll(0);
/*  831 */       if (spAll.isChecked()) {
/*  832 */         up.setReportsStatusAll(1);
/*      */       }
/*  834 */       up.setReportsStatusActive(0);
/*  835 */       if (spActive.isChecked()) {
/*  836 */         up.setReportsStatusActive(1);
/*      */       }
/*  838 */       up.setReportsStatusTBS(0);
/*  839 */       if (spTBS.isChecked()) {
/*  840 */         up.setReportsStatusTBS(1);
/*      */       }
/*  842 */       up.setReportsStatusClosed(0);
/*  843 */       if (spClosed.isChecked()) {
/*  844 */         up.setReportsStatusClosed(1);
/*      */       }
/*  846 */       up.setReportsStatusCancelled(0);
/*  847 */       if (spCancelled.isChecked()) {
/*  848 */         up.setReportsStatusCancelled(1);
/*      */       }
/*  850 */       user.setPreferences(up);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  855 */       if (!UserManager.getInstance().isDuplicate(user))
/*      */       {
/*      */         
/*  858 */         if (!form.isUnchanged()) {
/*      */           
/*  860 */           FormValidation formValidation = form.validate();
/*  861 */           if (formValidation.isGood()) {
/*      */ 
/*      */             
/*  864 */             Vector checkedCompanies = new Vector();
/*      */             
/*  866 */             for (int j = 0; j < checkedEnvironments.size(); j++) {
/*      */               
/*  868 */               Environment currentEnvironment = (Environment)checkedEnvironments.elementAt(j);
/*      */               
/*  870 */               Vector environmentCompanies = currentEnvironment.getChildren();
/*  871 */               for (int k = 0; k < environmentCompanies.size(); k++) {
/*  872 */                 Company envCompany = (Company)environmentCompanies.elementAt(k);
/*  873 */                 if (!checkedCompanies.contains(envCompany)) {
/*  874 */                   checkedCompanies.add(envCompany);
/*      */                 }
/*      */               } 
/*      */             } 
/*      */             
/*  879 */             User savedUser = UserManager.getInstance().save(user, sessionUser, checkedCompanies, context);
/*      */ 
/*      */             
/*  882 */             UserPreferencesManager.getInstance().savePreferences(user, context);
/*      */             
/*  884 */             form = buildForm(context, savedUser);
/*  885 */             userPrefForm = uph.buildForm(context, savedUser);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  894 */             FormElement lastUpdated = form.getElement("lastupdateddate");
/*  895 */             if (savedUser.getLastUpdateDate() != null) {
/*  896 */               lastUpdated.setValue(MilestoneHelper.getLongDate(savedUser.getLastUpdateDate()));
/*      */             }
/*      */             
/*  899 */             savedUser = UserManager.getInstance().getUser(savedUser.getUserId(), true);
/*      */             
/*  901 */             context.putSessionValue("securityUser", savedUser);
/*      */ 
/*      */             
/*  904 */             if (savedUser.getUserId() == sessionUser.getUserId()) {
/*      */               
/*  906 */               context.removeSessionValue("user");
/*      */ 
/*      */               
/*  909 */               context.removeSessionValue("user-companies");
/*  910 */               context.removeSessionValue("user-environments");
/*      */               
/*  912 */               context.putSessionValue("UserDefaultsApplied", "true");
/*  913 */               context.putSessionValue("user", savedUser);
/*      */ 
/*      */ 
/*      */               
/*  917 */               makeDynamic(savedUser, context, sessionUser);
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  924 */             UserCompaniesTableManager.getInstance().setUpdateFlag(savedUser.getUserId(), true);
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  929 */             Cache.flushCachedVariableAllUsers(savedUser.getUserId());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  938 */             Notepad notepad = getUsersNotepad(context);
/*  939 */             notepad.setAllContents(null);
/*      */             
/*  941 */             if (isNewUser)
/*      */             {
/*  943 */               notepad.newSelectedReset();
/*  944 */               notepad = getUsersNotepad(context);
/*  945 */               notepad.setSelected(savedUser);
/*  946 */               notepad = getUsersNotepad(context);
/*      */             }
/*      */             else
/*      */             {
/*  950 */               notepad = getUsersNotepad(context);
/*  951 */               notepad.setSelected(savedUser);
/*  952 */               user = (User)notepad.validateSelected();
/*      */               
/*  954 */               if (user == null) {
/*      */                 
/*  956 */                 notepad.setSwitchToCompaniesVisible(false);
/*  957 */                 return userGoToBlank(context);
/*      */               } 
/*      */ 
/*      */               
/*  961 */               form = buildForm(context, user);
/*  962 */               userPrefForm = uph.buildForm(context, user);
/*      */             
/*      */             }
/*      */           
/*      */           }
/*      */           else {
/*      */             
/*  969 */             context.putDelivery("FormValidation", formValidation);
/*      */           } 
/*      */         } 
/*      */         
/*  973 */         context.removeSessionValue(NOTEPAD_SESSION_NAMES[3]);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  978 */         if (isRelFamilyNewUser) {
/*  979 */           context.putSessionValue("showAssigned", "ASSIGNED");
/*      */         
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  985 */         Notepad notepad = getUsersNotepad(context);
/*  986 */         context.putDelivery("AlertMessage", "Duplicate.");
/*  987 */         user = (User)notepad.validateSelected();
/*  988 */         form = buildForm(context, user);
/*  989 */         userPrefForm = uph.buildForm(context, user);
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  995 */       context.putDelivery("AlertMessage", "The record is unavailable for editing at this time.  Please refresh your screen and edit it at a later time.");
/*      */     } 
/*      */ 
/*      */     
/*  999 */     context.putDelivery("Form", form);
/* 1000 */     this.lCopiedUserId = 0L;
/* 1001 */     return context.includeJSP("user-security-editor.jsp");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean userSecurityDelete(Context context) {
/* 1010 */     User sessionUser = MilestoneSecurity.getUser(context);
/*      */     
/* 1012 */     Notepad notepad = getUsersNotepad(context);
/* 1013 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */     
/* 1015 */     User user = MilestoneHelper.getScreenUser(context);
/*      */ 
/*      */     
/* 1018 */     if (user != null) {
/*      */       
/* 1020 */       UserManager.getInstance().deleteUser(user, sessionUser);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1025 */       Cache.flushCachedVariableAllUsers(user.getUserId());
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1030 */       notepad.setAllContents(null);
/* 1031 */       notepad.setSelected(null);
/*      */     } 
/*      */     
/* 1034 */     return userSecurityEditor(context);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean userSecurityNew(Context context) {
/* 1044 */     Notepad notepad = getUsersNotepad(context);
/* 1045 */     notepad.setSelected(null);
/* 1046 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/* 1047 */     context.removeSessionValue(NOTEPAD_SESSION_NAMES[21]);
/* 1048 */     context.removeSessionValue("securityUser");
/* 1049 */     context.removeSessionValue("User");
/* 1050 */     Form form = buildNewForm(context);
/*      */ 
/*      */     
/* 1053 */     Form formUserPrefs = null;
/* 1054 */     UserPreferencesHandler uph = new UserPreferencesHandler(this.application);
/* 1055 */     formUserPrefs = uph.buildNewForm(context);
/*      */     
/* 1057 */     notepad.setSwitchToCompaniesVisible(false);
/* 1058 */     return context.includeJSP("user-security-editor.jsp");
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
/* 1074 */     context.putSessionValue("showAssigned", "NEW");
/*      */ 
/*      */     
/* 1077 */     Form userSecurityForm = new Form(this.application, "userSecurityForm", 
/* 1078 */         this.application.getInfrastructure().getServletURL(), "POST");
/*      */     
/* 1080 */     Acl acl = user.getAcl();
/*      */     
/* 1082 */     Vector cAcl = null;
/*      */     
/* 1084 */     if (acl != null) {
/* 1085 */       acl.getCompanyAcl();
/*      */     }
/*      */     
/* 1088 */     FormTextField login = new FormTextField("login", "", true, 30, 100);
/* 1089 */     login.setTabIndex(1);
/* 1090 */     userSecurityForm.addElement(login);
/*      */ 
/*      */     
/* 1093 */     FormPasswordField password = new FormPasswordField("password", user.getPassword(), true, 30, 30);
/* 1094 */     password.setTabIndex(2);
/* 1095 */     userSecurityForm.addElement(password);
/*      */ 
/*      */     
/* 1098 */     String reportsToString = "";
/* 1099 */     if (user.getReportsTo() != null) {
/* 1100 */       reportsToString = user.getReportsTo();
/*      */     }
/* 1102 */     FormTextField reportto = new FormTextField("reportto", reportsToString, false, 30, 30);
/* 1103 */     reportto.setTabIndex(3);
/* 1104 */     userSecurityForm.addElement(reportto);
/*      */ 
/*      */ 
/*      */     
/* 1108 */     FormTextField fullname = new FormTextField("fullname", user.getName(), true, 30, 100);
/* 1109 */     fullname.setTabIndex(4);
/* 1110 */     userSecurityForm.addElement(fullname);
/*      */ 
/*      */     
/* 1113 */     String locationString = "";
/* 1114 */     locationString = user.getLocation();
/* 1115 */     Vector userLocations = Cache.getLookupDetailValuesFromDatabase(53);
/* 1116 */     FormDropDownMenu location = MilestoneHelper.getLookupDropDown("location", userLocations, locationString, false, true);
/* 1117 */     location.setTabIndex(5);
/* 1118 */     userSecurityForm.addElement(location);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1123 */     FormTextField email = new FormTextField("email", user.getEmail(), false, 30, 50);
/* 1124 */     email.setTabIndex(6);
/* 1125 */     userSecurityForm.addElement(email);
/*      */     
/* 1127 */     FormTextField phone = new FormTextField("phone", user.getPhone(), false, 30, 30);
/* 1128 */     phone.setTabIndex(7);
/* 1129 */     userSecurityForm.addElement(phone);
/*      */     
/* 1131 */     FormTextField fax = new FormTextField("fax", user.getFax(), false, 30, 30);
/* 1132 */     fax.setTabIndex(8);
/* 1133 */     userSecurityForm.addElement(fax);
/*      */     
/* 1135 */     Vector families = Cache.getInstance().getFamilies();
/*      */     
/* 1137 */     String employedByString = "";
/* 1138 */     if (user.getEmployedBy() > 0)
/*      */     {
/* 1140 */       employedByString = String.valueOf(user.getEmployedBy());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1145 */     FormDropDownMenu employedby = MilestoneHelper.getCorporateStructureDropDown("employedby", families, employedByString, true, true);
/* 1146 */     employedby.setTabIndex(9);
/* 1147 */     userSecurityForm.addElement(employedby);
/*      */ 
/*      */     
/* 1150 */     FormCheckBox inactive = new FormCheckBox("inactive", "Inactive", false, !(user.getInactive() == 0));
/* 1151 */     inactive.setId("inactive");
/* 1152 */     inactive.setTabIndex(11);
/* 1153 */     userSecurityForm.addElement(inactive);
/*      */ 
/*      */     
/* 1156 */     FormCheckBox administrator = new FormCheckBox("administrator", "Administrator", false, !(user.getAdministrator() == 0));
/* 1157 */     administrator.setTabIndex(12);
/* 1158 */     userSecurityForm.addElement(administrator);
/*      */ 
/*      */ 
/*      */     
/* 1162 */     String selDepartment = "";
/* 1163 */     String selStr = "";
/* 1164 */     if (user.getDeptFilter() != null)
/* 1165 */       selStr = user.getDeptFilter(); 
/* 1166 */     int sel = selStr.indexOf("department.filter.");
/* 1167 */     if (sel != -1) {
/*      */       try {
/* 1169 */         selDepartment = selStr.substring(sel + "department.filter.".length());
/* 1170 */       } catch (Exception exception) {}
/*      */     }
/*      */     
/* 1173 */     FormDropDownMenu deptFilterDD = MilestoneHelper.getDepartmentDropDown("deptFilter", selDepartment, false);
/* 1174 */     String[] values = deptFilterDD.getValueList();
/* 1175 */     String[] menuText = deptFilterDD.getMenuTextList();
/*      */     
/* 1177 */     values[0] = "All";
/* 1178 */     menuText[0] = "All";
/* 1179 */     deptFilterDD.setValueList(values);
/* 1180 */     deptFilterDD.setMenuTextList(menuText);
/* 1181 */     deptFilterDD.setTabIndex(11);
/* 1182 */     userSecurityForm.addElement(deptFilterDD);
/*      */ 
/*      */     
/* 1185 */     String filterFlagDept = "Yes";
/* 1186 */     String deptFilter = user.getDeptFilter();
/* 1187 */     if (deptFilter != null && deptFilter.length() > 0) {
/*      */       
/*      */       try {
/* 1190 */         if (deptFilter.substring(0, 4).equalsIgnoreCase("true"))
/* 1191 */         { filterFlagDept = "Yes"; }
/*      */         else
/* 1193 */         { filterFlagDept = "No"; } 
/* 1194 */       } catch (Exception exception) {}
/*      */     }
/*      */ 
/*      */     
/* 1198 */     FormRadioButtonGroup IsModifyDept = new FormRadioButtonGroup("IsModifyDept", filterFlagDept, "Yes, No", false);
/* 1199 */     IsModifyDept.setTabIndex(11);
/* 1200 */     userSecurityForm.addElement(IsModifyDept);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1206 */     String filterString = "All";
/* 1207 */     String filterFlag = "Yes";
/* 1208 */     String userFilter = user.getFilter();
/*      */ 
/*      */ 
/*      */     
/* 1212 */     if (userFilter != null && userFilter.length() > 0) {
/*      */       
/* 1214 */       if (userFilter.substring(0, 0).equalsIgnoreCase("t")) {
/* 1215 */         filterFlag = userFilter.substring(0, 3);
/*      */       } else {
/* 1217 */         filterFlag = userFilter.substring(0, 4);
/*      */       } 
/* 1219 */       if (filterFlag.equalsIgnoreCase("true")) {
/* 1220 */         filterString = userFilter.substring(5, userFilter.length());
/*      */       } else {
/* 1222 */         filterString = userFilter.substring(6, userFilter.length());
/*      */       } 
/* 1224 */       if (filterFlag.equalsIgnoreCase("true")) {
/* 1225 */         filterFlag = "Yes";
/*      */       } else {
/* 1227 */         filterFlag = "No";
/*      */       } 
/* 1229 */       if (filterString.equalsIgnoreCase("milestone.filter.FilterNone")) {
/*      */         
/* 1231 */         filterString = "All";
/*      */       }
/* 1233 */       else if (filterString.equalsIgnoreCase("milestone.filter.FilterExcludeOwnerUML")) {
/*      */         
/* 1235 */         filterString = "Only Label Tasks";
/*      */       }
/* 1237 */       else if (filterString.equalsIgnoreCase("milestone.filter.FilterIncludeOwnerUML")) {
/*      */         
/* 1239 */         filterString = "Only UML Tasks";
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1245 */     FormDropDownMenu filter = new FormDropDownMenu("filter", filterString, "All,Only Label Tasks, Only UML Tasks", true);
/* 1246 */     filter.setTabIndex(8);
/* 1247 */     userSecurityForm.addElement(filter);
/*      */ 
/*      */     
/* 1250 */     FormRadioButtonGroup IsModify = new FormRadioButtonGroup("IsModify", filterFlag, "Yes, No", false);
/* 1251 */     IsModify.setTabIndex(9);
/* 1252 */     userSecurityForm.addElement(IsModify);
/*      */ 
/*      */     
/* 1255 */     Vector environments = Cache.getInstance().getEnvironments();
/*      */ 
/*      */     
/* 1258 */     environments = Cache.getInstance().getEnvironmentsByFamily();
/*      */     
/* 1260 */     int userid = userId;
/*      */ 
/*      */     
/* 1263 */     Vector userEnvironments = MilestoneHelper.getUserEnvironments(userid);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1272 */     if (userEnvironments != null)
/*      */     {
/* 1274 */       for (int a = 0; a < userEnvironments.size(); a++) {
/*      */         
/* 1276 */         Environment userEnvironment = (Environment)userEnvironments.elementAt(a);
/* 1277 */         if (userEnvironment != null) {
/*      */           
/* 1279 */           FormCheckBox environmentCheckbox = new FormCheckBox("ue" + userEnvironment.getStructureID(), "ue" + userEnvironment.getStructureID(), userEnvironment.getName(), false, true);
/* 1280 */           environmentCheckbox.setId("ue" + userEnvironment.getStructureID());
/* 1281 */           userSecurityForm.addElement(environmentCheckbox);
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1289 */     for (int i = 0; i < environments.size(); i++) {
/*      */ 
/*      */       
/* 1292 */       Environment environment = (Environment)environments.elementAt(i);
/* 1293 */       if (environment != null) {
/*      */         
/* 1295 */         FormCheckBox environmentCheckbox = new FormCheckBox("ue" + environment.getStructureID(), "ue" + environment.getStructureID(), environment.getName(), false, false);
/* 1296 */         environmentCheckbox.setId("ue" + environment.getStructureID());
/* 1297 */         userSecurityForm.addElement(environmentCheckbox);
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
/* 1339 */     String selectionValue = "Not Available";
/* 1340 */     if (acl != null)
/*      */     {
/* 1342 */       if (acl.getAccessSelection())
/*      */       {
/* 1344 */         selectionValue = "Available";
/*      */       }
/*      */     }
/* 1347 */     FormRadioButtonGroup selectionAccess = new FormRadioButtonGroup("1", selectionValue, "Not Available, Available", false);
/* 1348 */     selectionAccess.setTabIndex(10);
/* 1349 */     userSecurityForm.addElement(selectionAccess);
/*      */ 
/*      */     
/* 1352 */     String scheduleValue = "Not Available";
/* 1353 */     if (acl != null)
/*      */     {
/* 1355 */       if (acl.getAccessSchedule())
/*      */       {
/* 1357 */         scheduleValue = "Available";
/*      */       }
/*      */     }
/* 1360 */     FormRadioButtonGroup scheduleAccess = new FormRadioButtonGroup("2", scheduleValue, "Not Available, Available", false);
/* 1361 */     scheduleAccess.setTabIndex(11);
/* 1362 */     userSecurityForm.addElement(scheduleAccess);
/*      */ 
/*      */     
/* 1365 */     String manufacturingValue = "Not Available";
/* 1366 */     if (acl != null)
/*      */     {
/* 1368 */       if (acl.getAccessManufacturing())
/*      */       {
/* 1370 */         manufacturingValue = "Available";
/*      */       }
/*      */     }
/* 1373 */     FormRadioButtonGroup manufacturingAccess = new FormRadioButtonGroup("3", manufacturingValue, "Not Available, Available", false);
/* 1374 */     manufacturingAccess.setTabIndex(12);
/* 1375 */     userSecurityForm.addElement(manufacturingAccess);
/*      */ 
/*      */     
/* 1378 */     String pfmValue = "Not Available";
/* 1379 */     if (acl != null)
/*      */     {
/* 1381 */       if (acl.getAccessPfmForm())
/*      */       {
/* 1383 */         pfmValue = "Available";
/*      */       }
/*      */     }
/* 1386 */     FormRadioButtonGroup pfmAccess = new FormRadioButtonGroup("4", pfmValue, "Not Available, Available", false);
/* 1387 */     pfmAccess.setTabIndex(13);
/* 1388 */     userSecurityForm.addElement(pfmAccess);
/*      */ 
/*      */     
/* 1391 */     String bomValue = "Not Available";
/* 1392 */     if (acl != null)
/*      */     {
/* 1394 */       if (acl.getAccessBomForm())
/*      */       {
/* 1396 */         bomValue = "Available";
/*      */       }
/*      */     }
/* 1399 */     FormRadioButtonGroup bomAccess = new FormRadioButtonGroup("5", bomValue, "Not Available, Available", false);
/* 1400 */     bomAccess.setTabIndex(14);
/* 1401 */     userSecurityForm.addElement(bomAccess);
/*      */ 
/*      */     
/* 1404 */     String reportValue = "Not Available";
/* 1405 */     if (acl != null)
/*      */     {
/* 1407 */       if (acl.getAccessReport())
/*      */       {
/* 1409 */         reportValue = "Available";
/*      */       }
/*      */     }
/* 1412 */     FormRadioButtonGroup reportAccess = new FormRadioButtonGroup("6", reportValue, "Not Available, Available", false);
/* 1413 */     reportAccess.setTabIndex(15);
/* 1414 */     userSecurityForm.addElement(reportAccess);
/*      */ 
/*      */     
/* 1417 */     String templateValue = "Not Available";
/* 1418 */     if (acl != null)
/*      */     {
/* 1420 */       if (acl.getAccessTemplate())
/*      */       {
/* 1422 */         templateValue = "Available";
/*      */       }
/*      */     }
/* 1425 */     FormRadioButtonGroup templateAccess = new FormRadioButtonGroup("7", templateValue, "Not Available, Available", false);
/* 1426 */     templateAccess.setTabIndex(16);
/* 1427 */     userSecurityForm.addElement(templateAccess);
/*      */ 
/*      */     
/* 1430 */     String taskValue = "Not Available";
/* 1431 */     if (acl != null)
/*      */     {
/* 1433 */       if (acl.getAccessTask())
/*      */       {
/* 1435 */         taskValue = "Available";
/*      */       }
/*      */     }
/* 1438 */     FormRadioButtonGroup taskAccess = new FormRadioButtonGroup("8", taskValue, "Not Available, Available", false);
/* 1439 */     taskAccess.setTabIndex(17);
/* 1440 */     userSecurityForm.addElement(taskAccess);
/*      */ 
/*      */     
/* 1443 */     String dayTypeValue = "Not Available";
/* 1444 */     if (acl != null)
/*      */     {
/* 1446 */       if (acl.getAccessDayType())
/*      */       {
/* 1448 */         dayTypeValue = "Available";
/*      */       }
/*      */     }
/* 1451 */     FormRadioButtonGroup dayTypeAccess = new FormRadioButtonGroup("9", dayTypeValue, "Not Available, Available", false);
/* 1452 */     dayTypeAccess.setTabIndex(18);
/* 1453 */     userSecurityForm.addElement(dayTypeAccess);
/*      */ 
/*      */     
/* 1456 */     String userValue = "Not Available";
/* 1457 */     if (acl != null)
/*      */     {
/* 1459 */       if (acl.getAccessUser())
/*      */       {
/* 1461 */         userValue = "Available";
/*      */       }
/*      */     }
/* 1464 */     FormRadioButtonGroup userAccess = new FormRadioButtonGroup("10", userValue, "Not Available, Available", false);
/* 1465 */     userAccess.setTabIndex(19);
/* 1466 */     userSecurityForm.addElement(userAccess);
/*      */ 
/*      */     
/* 1469 */     String familyValue = "Not Available";
/* 1470 */     if (acl != null)
/*      */     {
/* 1472 */       if (acl.getAccessFamily())
/*      */       {
/* 1474 */         familyValue = "Available";
/*      */       }
/*      */     }
/* 1477 */     FormRadioButtonGroup familyAccess = new FormRadioButtonGroup("11", familyValue, "Not Available, Available", false);
/* 1478 */     familyAccess.setTabIndex(20);
/* 1479 */     userSecurityForm.addElement(familyAccess);
/*      */ 
/*      */ 
/*      */     
/* 1483 */     String environmentValue = "Not Available";
/* 1484 */     if (acl != null)
/*      */     {
/* 1486 */       if (acl.getAccessEnvironment())
/*      */       {
/* 1488 */         environmentValue = "Available";
/*      */       }
/*      */     }
/* 1491 */     FormRadioButtonGroup environmentAccess = new FormRadioButtonGroup("20", environmentValue, "Not Available, Available", false);
/* 1492 */     environmentAccess.setTabIndex(21);
/* 1493 */     userSecurityForm.addElement(environmentAccess);
/*      */ 
/*      */     
/* 1496 */     String companyValue = "Not Available";
/* 1497 */     if (acl != null)
/*      */     {
/* 1499 */       if (acl.getAccessCompany())
/*      */       {
/* 1501 */         companyValue = "Available";
/*      */       }
/*      */     }
/* 1504 */     FormRadioButtonGroup companyAccess = new FormRadioButtonGroup("12", companyValue, "Not Available, Available", false);
/* 1505 */     companyAccess.setTabIndex(22);
/* 1506 */     userSecurityForm.addElement(companyAccess);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1511 */     String divisionValue = "Not Available";
/* 1512 */     if (acl != null)
/*      */     {
/* 1514 */       if (acl.getAccessDivision())
/*      */       {
/* 1516 */         divisionValue = "Available";
/*      */       }
/*      */     }
/* 1519 */     FormRadioButtonGroup divisionAccess = new FormRadioButtonGroup("13", divisionValue, "Not Available, Available", false);
/* 1520 */     divisionAccess.setTabIndex(23);
/* 1521 */     userSecurityForm.addElement(divisionAccess);
/*      */ 
/*      */     
/* 1524 */     String labelValue = "Not Available";
/* 1525 */     if (acl != null)
/*      */     {
/* 1527 */       if (acl.getAccessLabel())
/*      */       {
/* 1529 */         labelValue = "Available";
/*      */       }
/*      */     }
/* 1532 */     FormRadioButtonGroup labelAccess = new FormRadioButtonGroup("14", labelValue, "Not Available, Available", false);
/* 1533 */     labelAccess.setTabIndex(24);
/* 1534 */     userSecurityForm.addElement(labelAccess);
/*      */ 
/*      */     
/* 1537 */     String tableValue = "Not Available";
/* 1538 */     if (acl != null)
/*      */     {
/* 1540 */       if (acl.getAccessTable())
/*      */       {
/* 1542 */         tableValue = "Available";
/*      */       }
/*      */     }
/* 1545 */     FormRadioButtonGroup tableAccess = new FormRadioButtonGroup("15", tableValue, "Not Available, Available", false);
/* 1546 */     tableAccess.setTabIndex(25);
/* 1547 */     userSecurityForm.addElement(tableAccess);
/*      */ 
/*      */     
/* 1550 */     String parameterValue = "Not Available";
/* 1551 */     if (acl != null)
/*      */     {
/* 1553 */       if (acl.getAccessParameter())
/*      */       {
/* 1555 */         parameterValue = "Available";
/*      */       }
/*      */     }
/* 1558 */     FormRadioButtonGroup parameterAccess = new FormRadioButtonGroup("16", parameterValue, "Not Available, Available", false);
/* 1559 */     parameterAccess.setTabIndex(26);
/* 1560 */     userSecurityForm.addElement(parameterAccess);
/*      */ 
/*      */     
/* 1563 */     String auditTrailValue = "Not Available";
/* 1564 */     if (acl != null)
/*      */     {
/* 1566 */       if (acl.getAccessAuditTrail())
/*      */       {
/* 1568 */         auditTrailValue = "Available";
/*      */       }
/*      */     }
/* 1571 */     FormRadioButtonGroup auditTrailAccess = new FormRadioButtonGroup("17", auditTrailValue, "Not Available, Available", false);
/* 1572 */     auditTrailAccess.setTabIndex(27);
/* 1573 */     userSecurityForm.addElement(auditTrailAccess);
/*      */ 
/*      */     
/* 1576 */     String reportConfigValue = "Not Available";
/* 1577 */     if (acl != null)
/*      */     {
/* 1579 */       if (acl.getAccessReportConfig())
/*      */       {
/* 1581 */         reportConfigValue = "Available";
/*      */       }
/*      */     }
/* 1584 */     FormRadioButtonGroup reportConfigAccess = new FormRadioButtonGroup("18", reportConfigValue, "Not Available, Available", false);
/* 1585 */     reportConfigAccess.setTabIndex(28);
/* 1586 */     userSecurityForm.addElement(reportConfigAccess);
/*      */ 
/*      */     
/* 1589 */     String priceCodeValue = "Not Available";
/* 1590 */     if (acl != null)
/*      */     {
/* 1592 */       if (acl.getAccessPriceCode())
/*      */       {
/* 1594 */         priceCodeValue = "Available";
/*      */       }
/*      */     }
/* 1597 */     FormRadioButtonGroup priceCodeAccess = new FormRadioButtonGroup("19", priceCodeValue, "Not Available, Available", false);
/* 1598 */     priceCodeAccess.setTabIndex(29);
/* 1599 */     userSecurityForm.addElement(priceCodeAccess);
/*      */ 
/*      */     
/* 1602 */     FormTextField lastUpdated = new FormTextField("lastUpdatedDate", false, 50);
/* 1603 */     if (user.getLastUpdateDate() != null)
/* 1604 */       lastUpdated.setValue(MilestoneHelper.getLongDate(user.getLastUpdateDate())); 
/* 1605 */     userSecurityForm.addElement(lastUpdated);
/*      */     
/* 1607 */     FormTextField lastUpdatedBy = new FormTextField("lastUpdatedBy", false, 50);
/* 1608 */     if (UserManager.getInstance().getUser(user.getLastUpdatingUser(), true) != null)
/* 1609 */       lastUpdatedBy.setValue(UserManager.getInstance().getUser(user.getLastUpdatingUser(), true).getLogin()); 
/* 1610 */     userSecurityForm.addElement(lastUpdatedBy);
/*      */ 
/*      */     
/* 1613 */     FormTextField nameSrch = new FormTextField("nameSrch", "", false, 20);
/* 1614 */     nameSrch.setId("nameSrch");
/* 1615 */     userSecurityForm.addElement(nameSrch);
/*      */     
/* 1617 */     FormTextField userNameSrch = new FormTextField("userNameSrch", "", false, 20);
/* 1618 */     userNameSrch.setId("userNameSrch");
/* 1619 */     userSecurityForm.addElement(userNameSrch);
/*      */ 
/*      */     
/* 1622 */     String employedBySrcString = "";
/* 1623 */     FormDropDownMenu employedBySrc = MilestoneHelper.getCorporateStructureDropDown("employedBySrc", families, employedBySrcString, true, true);
/* 1624 */     employedBySrc.setId("employedBySrc");
/* 1625 */     userSecurityForm.addElement(employedBySrc);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1631 */     FormDropDownMenu environmentDescriptionSearch = MilestoneHelper.getCorporateStructureDropDown("EnvironmentDescriptionSearch", environments, "", false, true);
/* 1632 */     environmentDescriptionSearch.setId("EnvironmentDescriptionSearch");
/* 1633 */     userSecurityForm.addElement(environmentDescriptionSearch);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1638 */     userSecurityForm.addElement(new FormHidden("cmd", "user-security-copy"));
/*      */     
/* 1640 */     context.putDelivery("Form", userSecurityForm);
/*      */ 
/*      */     
/* 1643 */     if (context.getSessionValue("NOTEPAD_USER_VISIBLE") != null) {
/* 1644 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_USER_VISIBLE"));
/*      */     }
/*      */     
/* 1647 */     return userSecurityForm;
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
/* 1659 */     Form userSecurityForm = new Form(this.application, "userSecurityForm", 
/* 1660 */         this.application.getInfrastructure().getServletURL(), "POST");
/*      */     
/* 1662 */     Acl acl = user.getAcl();
/*      */     
/* 1664 */     Vector cAcl = null;
/*      */     
/* 1666 */     if (acl != null) {
/* 1667 */       acl.getCompanyAcl();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1672 */     String showAssigned = context.getRequest().getParameter("showAssigned");
/* 1673 */     if (showAssigned != null)
/*      */     {
/*      */ 
/*      */ 
/*      */       
/* 1678 */       if (showAssigned.equals("NEW") && user.getUserId() != -1) {
/* 1679 */         context.putSessionValue("showAssigned", "ASSIGNED");
/*      */       } else {
/* 1681 */         context.putSessionValue("showAssigned", showAssigned);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 1686 */     FormPasswordField password = new FormPasswordField("password", user.getPassword(), true, 30, 30);
/* 1687 */     password.setTabIndex(1);
/* 1688 */     userSecurityForm.addElement(password);
/*      */ 
/*      */     
/* 1691 */     String reportsToString = "";
/* 1692 */     if (user.getReportsTo() != null) {
/* 1693 */       reportsToString = user.getReportsTo();
/*      */     }
/* 1695 */     FormTextField reportto = new FormTextField("reportto", reportsToString, false, 30, 30);
/* 1696 */     reportto.setTabIndex(2);
/* 1697 */     userSecurityForm.addElement(reportto);
/*      */ 
/*      */     
/* 1700 */     FormTextField login = new FormTextField("login", user.getLogin(), true, 30, 100);
/* 1701 */     userSecurityForm.addElement(login);
/*      */ 
/*      */     
/* 1704 */     FormTextField fullname = new FormTextField("fullname", user.getName(), true, 30, 100);
/* 1705 */     fullname.setTabIndex(3);
/* 1706 */     userSecurityForm.addElement(fullname);
/*      */ 
/*      */     
/* 1709 */     String locationString = "";
/* 1710 */     locationString = user.getLocation();
/* 1711 */     Vector userLocations = Cache.getLookupDetailValuesFromDatabase(53);
/* 1712 */     FormDropDownMenu location = MilestoneHelper.getLookupDropDown("location", userLocations, locationString, false, true);
/* 1713 */     location.setTabIndex(4);
/* 1714 */     userSecurityForm.addElement(location);
/*      */ 
/*      */     
/* 1717 */     Vector families = Cache.getInstance().getFamilies();
/*      */     
/* 1719 */     String employedByString = "";
/* 1720 */     if (user.getEmployedBy() > 0)
/*      */     {
/* 1722 */       employedByString = String.valueOf(user.getEmployedBy());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1727 */     FormDropDownMenu employedby = MilestoneHelper.getCorporateStructureDropDown("employedby", families, employedByString, true, true);
/* 1728 */     employedby.setTabIndex(5);
/* 1729 */     userSecurityForm.addElement(employedby);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1735 */     FormTextField email = new FormTextField("email", user.getEmail(), false, 30, 50);
/* 1736 */     email.setTabIndex(6);
/* 1737 */     userSecurityForm.addElement(email);
/*      */     
/* 1739 */     FormTextField phone = new FormTextField("phone", user.getPhone(), false, 30, 30);
/* 1740 */     phone.setTabIndex(7);
/* 1741 */     userSecurityForm.addElement(phone);
/*      */     
/* 1743 */     FormTextField fax = new FormTextField("fax", user.getFax(), false, 30, 30);
/* 1744 */     fax.setTabIndex(10);
/* 1745 */     userSecurityForm.addElement(fax);
/*      */     
/* 1747 */     FormCheckBox inactive = new FormCheckBox("inactive", "Inactive", false, !(user.getInactive() == 0));
/* 1748 */     inactive.setId("inactive");
/* 1749 */     inactive.setTabIndex(11);
/* 1750 */     userSecurityForm.addElement(inactive);
/*      */ 
/*      */     
/* 1753 */     FormCheckBox administrator = new FormCheckBox("administrator", "Administrator", false, !(user.getAdministrator() == 0));
/* 1754 */     administrator.setTabIndex(12);
/* 1755 */     userSecurityForm.addElement(administrator);
/*      */ 
/*      */ 
/*      */     
/* 1759 */     String selDepartment = "";
/* 1760 */     String selStr = "";
/* 1761 */     if (user.getDeptFilter() != null)
/* 1762 */       selStr = user.getDeptFilter(); 
/* 1763 */     int sel = selStr.indexOf("department.filter.");
/* 1764 */     if (sel != -1) {
/*      */       try {
/* 1766 */         selDepartment = selStr.substring(sel + "department.filter.".length());
/* 1767 */       } catch (Exception exception) {}
/*      */     }
/*      */     
/* 1770 */     FormDropDownMenu deptFilterDD = MilestoneHelper.getDepartmentDropDown("deptFilter", selDepartment, false);
/* 1771 */     String[] values = deptFilterDD.getValueList();
/* 1772 */     String[] menuText = deptFilterDD.getMenuTextList();
/*      */     
/* 1774 */     values[0] = "All";
/* 1775 */     menuText[0] = "All";
/* 1776 */     deptFilterDD.setValueList(values);
/* 1777 */     deptFilterDD.setMenuTextList(menuText);
/* 1778 */     deptFilterDD.setTabIndex(11);
/* 1779 */     userSecurityForm.addElement(deptFilterDD);
/*      */ 
/*      */     
/* 1782 */     String filterFlagDept = "Yes";
/* 1783 */     String deptFilter = user.getDeptFilter();
/* 1784 */     if (deptFilter != null && deptFilter.length() > 0) {
/*      */       
/*      */       try {
/* 1787 */         if (deptFilter.substring(0, 4).equalsIgnoreCase("true"))
/* 1788 */         { filterFlagDept = "Yes"; }
/*      */         else
/* 1790 */         { filterFlagDept = "No"; } 
/* 1791 */       } catch (Exception exception) {}
/*      */     }
/*      */ 
/*      */     
/* 1795 */     FormRadioButtonGroup IsModifyDept = new FormRadioButtonGroup("IsModifyDept", filterFlagDept, "Yes, No", false);
/* 1796 */     IsModifyDept.setTabIndex(11);
/* 1797 */     userSecurityForm.addElement(IsModifyDept);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1803 */     String filterString = "All";
/* 1804 */     String filterFlag = "Yes";
/* 1805 */     String userFilter = user.getFilter();
/*      */ 
/*      */ 
/*      */     
/* 1809 */     if (userFilter != null && userFilter.length() > 0) {
/*      */       
/* 1811 */       if (userFilter.substring(0, 0).equalsIgnoreCase("t")) {
/* 1812 */         filterFlag = userFilter.substring(0, 3);
/*      */       } else {
/* 1814 */         filterFlag = userFilter.substring(0, 4);
/*      */       } 
/* 1816 */       if (filterFlag.equalsIgnoreCase("true")) {
/* 1817 */         filterString = userFilter.substring(5, userFilter.length());
/*      */       } else {
/* 1819 */         filterString = userFilter.substring(6, userFilter.length());
/*      */       } 
/* 1821 */       if (filterFlag.equalsIgnoreCase("true")) {
/* 1822 */         filterFlag = "Yes";
/*      */       } else {
/* 1824 */         filterFlag = "No";
/*      */       } 
/* 1826 */       if (filterString.equalsIgnoreCase("milestone.filter.FilterNone")) {
/*      */         
/* 1828 */         filterString = "All";
/*      */       }
/* 1830 */       else if (filterString.equalsIgnoreCase("milestone.filter.FilterExcludeOwnerUML")) {
/*      */         
/* 1832 */         filterString = "Only Label Tasks";
/*      */       }
/* 1834 */       else if (filterString.equalsIgnoreCase("milestone.filter.FilterIncludeOwnerUML")) {
/*      */         
/* 1836 */         filterString = "Only UML Tasks";
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1842 */     FormDropDownMenu filter = new FormDropDownMenu("filter", filterString, "All,Only Label Tasks, Only UML Tasks", true);
/* 1843 */     filter.setTabIndex(8);
/* 1844 */     userSecurityForm.addElement(filter);
/*      */ 
/*      */     
/* 1847 */     FormRadioButtonGroup IsModify = new FormRadioButtonGroup("IsModify", filterFlag, "Yes, No", false);
/* 1848 */     IsModify.setTabIndex(9);
/* 1849 */     userSecurityForm.addElement(IsModify);
/*      */ 
/*      */     
/* 1852 */     Vector environments = Cache.getInstance().getEnvironments();
/*      */ 
/*      */     
/* 1855 */     environments = Cache.getInstance().getEnvironmentsByFamily();
/*      */ 
/*      */     
/* 1858 */     int userid = user.getUserId();
/*      */ 
/*      */     
/* 1861 */     Vector userEnvironments = MilestoneHelper.getUserEnvironments(userid);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1867 */     if (userEnvironments != null)
/*      */     {
/* 1869 */       for (int a = 0; a < userEnvironments.size(); a++) {
/*      */         
/* 1871 */         Environment userEnvironment = (Environment)userEnvironments.elementAt(a);
/* 1872 */         if (userEnvironment != null) {
/*      */           
/* 1874 */           FormCheckBox environmentCheckbox = new FormCheckBox("ue" + userEnvironment.getStructureID(), "ue" + userEnvironment.getStructureID(), userEnvironment.getName(), false, true);
/* 1875 */           environmentCheckbox.setId("ue" + userEnvironment.getStructureID());
/* 1876 */           userSecurityForm.addElement(environmentCheckbox);
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1884 */     for (int i = 0; i < environments.size(); i++) {
/*      */       
/* 1886 */       Environment environment = (Environment)environments.elementAt(i);
/* 1887 */       if (environment != null) {
/*      */         
/* 1889 */         FormCheckBox environmentCheckbox = new FormCheckBox("ue" + environment.getStructureID(), "ue" + environment.getStructureID(), environment.getName(), false, false);
/* 1890 */         environmentCheckbox.setId("ue" + environment.getStructureID());
/* 1891 */         userSecurityForm.addElement(environmentCheckbox);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1900 */     String selectionValue = "Not Available";
/* 1901 */     if (acl != null)
/*      */     {
/* 1903 */       if (acl.getAccessSelection())
/*      */       {
/* 1905 */         selectionValue = "Available";
/*      */       }
/*      */     }
/* 1908 */     FormRadioButtonGroup selectionAccess = new FormRadioButtonGroup("1", selectionValue, "Not Available, Available", false);
/* 1909 */     selectionAccess.setTabIndex(10);
/* 1910 */     userSecurityForm.addElement(selectionAccess);
/*      */ 
/*      */     
/* 1913 */     String scheduleValue = "Not Available";
/* 1914 */     if (acl != null)
/*      */     {
/* 1916 */       if (acl.getAccessSchedule())
/*      */       {
/* 1918 */         scheduleValue = "Available";
/*      */       }
/*      */     }
/* 1921 */     FormRadioButtonGroup scheduleAccess = new FormRadioButtonGroup("2", scheduleValue, "Not Available, Available", false);
/* 1922 */     scheduleAccess.setTabIndex(11);
/* 1923 */     userSecurityForm.addElement(scheduleAccess);
/*      */ 
/*      */     
/* 1926 */     String manufacturingValue = "Not Available";
/* 1927 */     if (acl != null)
/*      */     {
/* 1929 */       if (acl.getAccessManufacturing())
/*      */       {
/* 1931 */         manufacturingValue = "Available";
/*      */       }
/*      */     }
/* 1934 */     FormRadioButtonGroup manufacturingAccess = new FormRadioButtonGroup("3", manufacturingValue, "Not Available, Available", false);
/* 1935 */     manufacturingAccess.setTabIndex(12);
/* 1936 */     userSecurityForm.addElement(manufacturingAccess);
/*      */ 
/*      */     
/* 1939 */     String pfmValue = "Not Available";
/* 1940 */     if (acl != null)
/*      */     {
/* 1942 */       if (acl.getAccessPfmForm())
/*      */       {
/* 1944 */         pfmValue = "Available";
/*      */       }
/*      */     }
/* 1947 */     FormRadioButtonGroup pfmAccess = new FormRadioButtonGroup("4", pfmValue, "Not Available, Available", false);
/* 1948 */     pfmAccess.setTabIndex(13);
/* 1949 */     userSecurityForm.addElement(pfmAccess);
/*      */ 
/*      */     
/* 1952 */     String bomValue = "Not Available";
/* 1953 */     if (acl != null)
/*      */     {
/* 1955 */       if (acl.getAccessBomForm())
/*      */       {
/* 1957 */         bomValue = "Available";
/*      */       }
/*      */     }
/* 1960 */     FormRadioButtonGroup bomAccess = new FormRadioButtonGroup("5", bomValue, "Not Available, Available", false);
/* 1961 */     bomAccess.setTabIndex(14);
/* 1962 */     userSecurityForm.addElement(bomAccess);
/*      */ 
/*      */     
/* 1965 */     String reportValue = "Not Available";
/* 1966 */     if (acl != null)
/*      */     {
/* 1968 */       if (acl.getAccessReport())
/*      */       {
/* 1970 */         reportValue = "Available";
/*      */       }
/*      */     }
/* 1973 */     FormRadioButtonGroup reportAccess = new FormRadioButtonGroup("6", reportValue, "Not Available, Available", false);
/* 1974 */     reportAccess.setTabIndex(15);
/* 1975 */     userSecurityForm.addElement(reportAccess);
/*      */ 
/*      */     
/* 1978 */     String templateValue = "Not Available";
/* 1979 */     if (acl != null)
/*      */     {
/* 1981 */       if (acl.getAccessTemplate())
/*      */       {
/* 1983 */         templateValue = "Available";
/*      */       }
/*      */     }
/* 1986 */     FormRadioButtonGroup templateAccess = new FormRadioButtonGroup("7", templateValue, "Not Available, Available", false);
/* 1987 */     templateAccess.setTabIndex(16);
/* 1988 */     userSecurityForm.addElement(templateAccess);
/*      */ 
/*      */     
/* 1991 */     String taskValue = "Not Available";
/* 1992 */     if (acl != null)
/*      */     {
/* 1994 */       if (acl.getAccessTask())
/*      */       {
/* 1996 */         taskValue = "Available";
/*      */       }
/*      */     }
/* 1999 */     FormRadioButtonGroup taskAccess = new FormRadioButtonGroup("8", taskValue, "Not Available, Available", false);
/* 2000 */     taskAccess.setTabIndex(17);
/* 2001 */     userSecurityForm.addElement(taskAccess);
/*      */ 
/*      */     
/* 2004 */     String dayTypeValue = "Not Available";
/* 2005 */     if (acl != null)
/*      */     {
/* 2007 */       if (acl.getAccessDayType())
/*      */       {
/* 2009 */         dayTypeValue = "Available";
/*      */       }
/*      */     }
/* 2012 */     FormRadioButtonGroup dayTypeAccess = new FormRadioButtonGroup("9", dayTypeValue, "Not Available, Available", false);
/* 2013 */     dayTypeAccess.setTabIndex(18);
/* 2014 */     userSecurityForm.addElement(dayTypeAccess);
/*      */ 
/*      */     
/* 2017 */     String userValue = "Not Available";
/* 2018 */     if (acl != null)
/*      */     {
/* 2020 */       if (acl.getAccessUser())
/*      */       {
/* 2022 */         userValue = "Available";
/*      */       }
/*      */     }
/* 2025 */     FormRadioButtonGroup userAccess = new FormRadioButtonGroup("10", userValue, "Not Available, Available", false);
/* 2026 */     userAccess.setTabIndex(19);
/* 2027 */     userSecurityForm.addElement(userAccess);
/*      */ 
/*      */     
/* 2030 */     String familyValue = "Not Available";
/* 2031 */     if (acl != null)
/*      */     {
/* 2033 */       if (acl.getAccessFamily())
/*      */       {
/* 2035 */         familyValue = "Available";
/*      */       }
/*      */     }
/* 2038 */     FormRadioButtonGroup familyAccess = new FormRadioButtonGroup("11", familyValue, "Not Available, Available", false);
/* 2039 */     familyAccess.setTabIndex(20);
/* 2040 */     userSecurityForm.addElement(familyAccess);
/*      */ 
/*      */     
/* 2043 */     String environmentValue = "Not Available";
/* 2044 */     if (acl != null)
/*      */     {
/* 2046 */       if (acl.getAccessEnvironment())
/*      */       {
/* 2048 */         environmentValue = "Available";
/*      */       }
/*      */     }
/* 2051 */     FormRadioButtonGroup environmentAccess = new FormRadioButtonGroup("20", environmentValue, "Not Available, Available", false);
/* 2052 */     environmentAccess.setTabIndex(21);
/* 2053 */     userSecurityForm.addElement(environmentAccess);
/*      */ 
/*      */     
/* 2056 */     String companyValue = "Not Available";
/* 2057 */     if (acl != null)
/*      */     {
/* 2059 */       if (acl.getAccessCompany())
/*      */       {
/* 2061 */         companyValue = "Available";
/*      */       }
/*      */     }
/* 2064 */     FormRadioButtonGroup companyAccess = new FormRadioButtonGroup("12", companyValue, "Not Available, Available", false);
/* 2065 */     companyAccess.setTabIndex(22);
/* 2066 */     userSecurityForm.addElement(companyAccess);
/*      */ 
/*      */     
/* 2069 */     String divisionValue = "Not Available";
/* 2070 */     if (acl != null)
/*      */     {
/* 2072 */       if (acl.getAccessDivision())
/*      */       {
/* 2074 */         divisionValue = "Available";
/*      */       }
/*      */     }
/* 2077 */     FormRadioButtonGroup divisionAccess = new FormRadioButtonGroup("13", divisionValue, "Not Available, Available", false);
/* 2078 */     divisionAccess.setTabIndex(23);
/* 2079 */     userSecurityForm.addElement(divisionAccess);
/*      */ 
/*      */     
/* 2082 */     String labelValue = "Not Available";
/* 2083 */     if (acl != null)
/*      */     {
/* 2085 */       if (acl.getAccessLabel())
/*      */       {
/* 2087 */         labelValue = "Available";
/*      */       }
/*      */     }
/* 2090 */     FormRadioButtonGroup labelAccess = new FormRadioButtonGroup("14", labelValue, "Not Available, Available", false);
/* 2091 */     labelAccess.setTabIndex(24);
/* 2092 */     userSecurityForm.addElement(labelAccess);
/*      */ 
/*      */     
/* 2095 */     String tableValue = "Not Available";
/* 2096 */     if (acl != null)
/*      */     {
/* 2098 */       if (acl.getAccessTable())
/*      */       {
/* 2100 */         tableValue = "Available";
/*      */       }
/*      */     }
/* 2103 */     FormRadioButtonGroup tableAccess = new FormRadioButtonGroup("15", tableValue, "Not Available, Available", false);
/* 2104 */     tableAccess.setTabIndex(25);
/* 2105 */     userSecurityForm.addElement(tableAccess);
/*      */ 
/*      */     
/* 2108 */     String parameterValue = "Not Available";
/* 2109 */     if (acl != null)
/*      */     {
/* 2111 */       if (acl.getAccessParameter())
/*      */       {
/* 2113 */         parameterValue = "Available";
/*      */       }
/*      */     }
/* 2116 */     FormRadioButtonGroup parameterAccess = new FormRadioButtonGroup("16", parameterValue, "Not Available, Available", false);
/* 2117 */     parameterAccess.setTabIndex(26);
/* 2118 */     userSecurityForm.addElement(parameterAccess);
/*      */ 
/*      */     
/* 2121 */     String auditTrailValue = "Not Available";
/* 2122 */     if (acl != null)
/*      */     {
/* 2124 */       if (acl.getAccessAuditTrail())
/*      */       {
/* 2126 */         auditTrailValue = "Available";
/*      */       }
/*      */     }
/* 2129 */     FormRadioButtonGroup auditTrailAccess = new FormRadioButtonGroup("17", auditTrailValue, "Not Available, Available", false);
/* 2130 */     auditTrailAccess.setTabIndex(27);
/* 2131 */     userSecurityForm.addElement(auditTrailAccess);
/*      */ 
/*      */     
/* 2134 */     String reportConfigValue = "Not Available";
/* 2135 */     if (acl != null)
/*      */     {
/* 2137 */       if (acl.getAccessReportConfig())
/*      */       {
/* 2139 */         reportConfigValue = "Available";
/*      */       }
/*      */     }
/* 2142 */     FormRadioButtonGroup reportConfigAccess = new FormRadioButtonGroup("18", reportConfigValue, "Not Available, Available", false);
/* 2143 */     reportConfigAccess.setTabIndex(28);
/* 2144 */     userSecurityForm.addElement(reportConfigAccess);
/*      */ 
/*      */     
/* 2147 */     String priceCodeValue = "Not Available";
/* 2148 */     if (acl != null)
/*      */     {
/* 2150 */       if (acl.getAccessPriceCode())
/*      */       {
/* 2152 */         priceCodeValue = "Available";
/*      */       }
/*      */     }
/* 2155 */     FormRadioButtonGroup priceCodeAccess = new FormRadioButtonGroup("19", priceCodeValue, "Not Available, Available", false);
/* 2156 */     priceCodeAccess.setTabIndex(29);
/* 2157 */     userSecurityForm.addElement(priceCodeAccess);
/*      */ 
/*      */     
/* 2160 */     FormTextField lastUpdated = new FormTextField("lastUpdatedDate", false, 50);
/* 2161 */     if (user.getLastUpdateDate() != null)
/* 2162 */       lastUpdated.setValue(MilestoneHelper.getLongDate(user.getLastUpdateDate())); 
/* 2163 */     userSecurityForm.addElement(lastUpdated);
/*      */     
/* 2165 */     FormTextField lastUpdatedBy = new FormTextField("lastUpdatedBy", false, 50);
/* 2166 */     if (UserManager.getInstance().getUser(user.getLastUpdatingUser(), true) != null)
/* 2167 */       lastUpdatedBy.setValue(UserManager.getInstance().getUser(user.getLastUpdatingUser(), true).getLogin()); 
/* 2168 */     userSecurityForm.addElement(lastUpdatedBy);
/*      */ 
/*      */     
/* 2171 */     FormTextField nameSrch = new FormTextField("nameSrch", "", false, 20);
/* 2172 */     nameSrch.setId("nameSrch");
/* 2173 */     userSecurityForm.addElement(nameSrch);
/*      */     
/* 2175 */     FormTextField userNameSrch = new FormTextField("userNameSrch", "", false, 20);
/* 2176 */     userNameSrch.setId("userNameSrch");
/* 2177 */     userSecurityForm.addElement(userNameSrch);
/*      */ 
/*      */     
/* 2180 */     String employedBySrcString = "";
/* 2181 */     FormDropDownMenu employedBySrc = MilestoneHelper.getCorporateStructureDropDown("employedBySrc", families, employedBySrcString, true, true);
/* 2182 */     employedBySrc.setId("employedBySrc");
/* 2183 */     userSecurityForm.addElement(employedBySrc);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2189 */     FormDropDownMenu environmentDescriptionSearch = MilestoneHelper.getCorporateStructureDropDown("EnvironmentDescriptionSearch", environments, "", false, true);
/* 2190 */     environmentDescriptionSearch.setId("EnvironmentDescriptionSearch");
/* 2191 */     userSecurityForm.addElement(environmentDescriptionSearch);
/*      */ 
/*      */     
/* 2194 */     userSecurityForm.addElement(new FormHidden("cmd", "user-security-editor"));
/* 2195 */     context.putDelivery("Form", userSecurityForm);
/*      */ 
/*      */     
/* 2198 */     if (context.getSessionValue("NOTEPAD_USER_VISIBLE") != null) {
/* 2199 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_USER_VISIBLE"));
/*      */     }
/*      */     
/* 2202 */     return userSecurityForm;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Form buildBlankCompanyForm(Context context) {
/* 2212 */     Form userCompanySecurityForm = new Form(this.application, "userCompanySecurityForm", 
/* 2213 */         this.application.getInfrastructure().getServletURL(), "POST");
/*      */     
/* 2215 */     userCompanySecurityForm.addElement(new FormHidden("cmd", "user-environment-security-editor"));
/* 2216 */     context.putDelivery("Form", userCompanySecurityForm);
/*      */ 
/*      */     
/* 2219 */     if (context.getSessionValue("NOTEPAD_USER_ENVIRONMENTS_VISIBLE") != null) {
/* 2220 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_USER_ENVIRONMENTS_VISIBLE"));
/*      */     }
/* 2222 */     return userCompanySecurityForm;
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
/* 2233 */     Form userCompanySecurityForm = new Form(this.application, "userCompanySecurityForm", 
/* 2234 */         this.application.getInfrastructure().getServletURL(), "POST");
/*      */     
/* 2236 */     Acl currentAcl = currentUser.getAcl();
/* 2237 */     Vector companyAcl = currentAcl.getCompanyAcl();
/* 2238 */     CompanyAcl company = null;
/* 2239 */     CompanyAcl acl = null;
/*      */ 
/*      */     
/* 2242 */     Vector childrenCompanies = currentEnvironment.getChildren();
/* 2243 */     Vector companyIDs = new Vector();
/* 2244 */     for (int k = 0; k < childrenCompanies.size(); k++) {
/* 2245 */       Company company1 = (Company)childrenCompanies.elementAt(k);
/* 2246 */       if (company1 != null) {
/* 2247 */         companyIDs.add(new Integer(company1.getStructureID()));
/*      */       }
/*      */     } 
/* 2250 */     for (int i = 0; i < companyAcl.size(); i++) {
/*      */       
/* 2252 */       company = (CompanyAcl)companyAcl.get(i);
/* 2253 */       if (company != null) {
/*      */         
/* 2255 */         Environment aclEnvironment = 
/* 2256 */           (Environment)MilestoneHelper.getStructureObject(company.getParentID());
/* 2257 */         Integer companyID = new Integer(company.getCompanyId());
/* 2258 */         if (companyIDs.contains(companyID)) {
/* 2259 */           acl = company;
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2267 */     String selectionValue = "View";
/* 2268 */     if (acl != null)
/*      */     {
/* 2270 */       if (acl.getAccessSelection() == 2)
/*      */       {
/* 2272 */         selectionValue = "Edit";
/*      */       }
/*      */     }
/*      */     
/* 2276 */     FormRadioButtonGroup selectionAccess = new FormRadioButtonGroup("1", selectionValue, "View, Edit", false);
/* 2277 */     selectionAccess.setTabIndex(10);
/* 2278 */     userCompanySecurityForm.addElement(selectionAccess);
/*      */ 
/*      */     
/* 2281 */     String scheduleValue = "View";
/* 2282 */     if (acl != null)
/*      */     {
/* 2284 */       if (acl.getAccessSchedule() == 2)
/*      */       {
/* 2286 */         scheduleValue = "Edit";
/*      */       }
/*      */     }
/* 2289 */     FormRadioButtonGroup scheduleAccess = new FormRadioButtonGroup("2", scheduleValue, "View, Edit", false);
/* 2290 */     scheduleAccess.setTabIndex(11);
/* 2291 */     userCompanySecurityForm.addElement(scheduleAccess);
/*      */ 
/*      */     
/* 2294 */     String manufacturingValue = "View";
/* 2295 */     if (acl != null)
/*      */     {
/* 2297 */       if (acl.getAccessManufacturing() == 2)
/*      */       {
/* 2299 */         manufacturingValue = "Edit";
/*      */       }
/*      */     }
/* 2302 */     FormRadioButtonGroup manufacturingAccess = new FormRadioButtonGroup("3", manufacturingValue, "View, Edit", false);
/* 2303 */     manufacturingAccess.setTabIndex(12);
/* 2304 */     userCompanySecurityForm.addElement(manufacturingAccess);
/*      */ 
/*      */     
/* 2307 */     String pfmValue = "View";
/* 2308 */     if (acl != null)
/*      */     {
/* 2310 */       if (acl.getAccessPfmForm() == 2)
/*      */       {
/* 2312 */         pfmValue = "Edit";
/*      */       }
/*      */     }
/* 2315 */     FormRadioButtonGroup pfmAccess = new FormRadioButtonGroup("4", pfmValue, "View, Edit", false);
/* 2316 */     pfmAccess.setTabIndex(13);
/* 2317 */     userCompanySecurityForm.addElement(pfmAccess);
/*      */ 
/*      */     
/* 2320 */     String bomValue = "View";
/* 2321 */     if (acl != null)
/*      */     {
/* 2323 */       if (acl.getAccessBomForm() == 2)
/*      */       {
/* 2325 */         bomValue = "Edit";
/*      */       }
/*      */     }
/* 2328 */     FormRadioButtonGroup bomAccess = new FormRadioButtonGroup("5", bomValue, "View, Edit", false);
/* 2329 */     bomAccess.setTabIndex(14);
/* 2330 */     userCompanySecurityForm.addElement(bomAccess);
/*      */ 
/*      */     
/* 2333 */     String reportValue = "View";
/* 2334 */     if (acl != null)
/*      */     {
/* 2336 */       if (acl.getAccessReport() == 2)
/*      */       {
/* 2338 */         reportValue = "Edit";
/*      */       }
/*      */     }
/* 2341 */     FormRadioButtonGroup reportAccess = new FormRadioButtonGroup("6", reportValue, "View, Edit", false);
/* 2342 */     reportAccess.setTabIndex(15);
/* 2343 */     userCompanySecurityForm.addElement(reportAccess);
/*      */ 
/*      */     
/* 2346 */     String templateValue = "View";
/* 2347 */     if (acl != null)
/*      */     {
/* 2349 */       if (acl.getAccessTemplate() == 2)
/*      */       {
/* 2351 */         templateValue = "Edit";
/*      */       }
/*      */     }
/* 2354 */     FormRadioButtonGroup templateAccess = new FormRadioButtonGroup("7", templateValue, "View, Edit", false);
/* 2355 */     templateAccess.setTabIndex(16);
/* 2356 */     userCompanySecurityForm.addElement(templateAccess);
/*      */ 
/*      */     
/* 2359 */     String taskValue = "View";
/* 2360 */     if (acl != null)
/*      */     {
/* 2362 */       if (acl.getAccessTask() == 2)
/*      */       {
/* 2364 */         taskValue = "Edit";
/*      */       }
/*      */     }
/* 2367 */     FormRadioButtonGroup taskAccess = new FormRadioButtonGroup("8", taskValue, "View, Edit", false);
/* 2368 */     taskAccess.setTabIndex(17);
/* 2369 */     userCompanySecurityForm.addElement(taskAccess);
/*      */ 
/*      */     
/* 2372 */     String dayTypeValue = "View";
/* 2373 */     if (acl != null)
/*      */     {
/* 2375 */       if (acl.getAccessDayType() == 2)
/*      */       {
/* 2377 */         dayTypeValue = "Edit";
/*      */       }
/*      */     }
/* 2380 */     FormRadioButtonGroup dayTypeAccess = new FormRadioButtonGroup("9", dayTypeValue, "View, Edit", false);
/* 2381 */     dayTypeAccess.setTabIndex(18);
/* 2382 */     userCompanySecurityForm.addElement(dayTypeAccess);
/*      */ 
/*      */     
/* 2385 */     String userValue = "View";
/* 2386 */     if (acl != null)
/*      */     {
/* 2388 */       if (acl.getAccessUser() == 2)
/*      */       {
/* 2390 */         userValue = "Edit";
/*      */       }
/*      */     }
/* 2393 */     FormRadioButtonGroup userAccess = new FormRadioButtonGroup("10", userValue, "View, Edit", false);
/* 2394 */     userAccess.setTabIndex(19);
/* 2395 */     userCompanySecurityForm.addElement(userAccess);
/*      */ 
/*      */     
/* 2398 */     String familyValue = "View";
/* 2399 */     if (acl != null)
/*      */     {
/* 2401 */       if (acl.getAccessFamily() == 2)
/*      */       {
/* 2403 */         familyValue = "Edit";
/*      */       }
/*      */     }
/* 2406 */     FormRadioButtonGroup familyAccess = new FormRadioButtonGroup("11", familyValue, "View, Edit", false);
/* 2407 */     familyAccess.setTabIndex(20);
/* 2408 */     userCompanySecurityForm.addElement(familyAccess);
/*      */ 
/*      */     
/* 2411 */     String environmentValue = "View";
/* 2412 */     if (acl != null)
/*      */     {
/* 2414 */       if (acl.getAccessEnvironment() == 2)
/*      */       {
/* 2416 */         environmentValue = "Edit";
/*      */       }
/*      */     }
/* 2419 */     FormRadioButtonGroup environmentAccess = new FormRadioButtonGroup("20", environmentValue, "View, Edit", false);
/* 2420 */     environmentAccess.setTabIndex(21);
/* 2421 */     userCompanySecurityForm.addElement(environmentAccess);
/*      */ 
/*      */     
/* 2424 */     String companyValue = "View";
/* 2425 */     if (acl != null)
/*      */     {
/* 2427 */       if (acl.getAccessCompany() == 2)
/*      */       {
/* 2429 */         companyValue = "Edit";
/*      */       }
/*      */     }
/* 2432 */     FormRadioButtonGroup companyAccess = new FormRadioButtonGroup("12", companyValue, "View, Edit", false);
/* 2433 */     companyAccess.setTabIndex(22);
/* 2434 */     userCompanySecurityForm.addElement(companyAccess);
/*      */ 
/*      */     
/* 2437 */     String divisionValue = "View";
/* 2438 */     if (acl != null)
/*      */     {
/* 2440 */       if (acl.getAccessDivision() == 2)
/*      */       {
/* 2442 */         divisionValue = "Edit";
/*      */       }
/*      */     }
/* 2445 */     FormRadioButtonGroup divisionAccess = new FormRadioButtonGroup("13", divisionValue, "View, Edit", false);
/* 2446 */     divisionAccess.setTabIndex(23);
/* 2447 */     userCompanySecurityForm.addElement(divisionAccess);
/*      */ 
/*      */     
/* 2450 */     String labelValue = "View";
/* 2451 */     if (acl != null)
/*      */     {
/* 2453 */       if (acl.getAccessLabel() == 2)
/*      */       {
/* 2455 */         labelValue = "Edit";
/*      */       }
/*      */     }
/* 2458 */     FormRadioButtonGroup labelAccess = new FormRadioButtonGroup("14", labelValue, "View, Edit", false);
/* 2459 */     labelAccess.setTabIndex(24);
/* 2460 */     userCompanySecurityForm.addElement(labelAccess);
/*      */ 
/*      */     
/* 2463 */     String tableValue = "View";
/* 2464 */     if (acl != null)
/*      */     {
/* 2466 */       if (acl.getAccessTable() == 2)
/*      */       {
/* 2468 */         tableValue = "Edit";
/*      */       }
/*      */     }
/* 2471 */     FormRadioButtonGroup tableAccess = new FormRadioButtonGroup("15", tableValue, "View, Edit", false);
/* 2472 */     tableAccess.setTabIndex(25);
/* 2473 */     userCompanySecurityForm.addElement(tableAccess);
/*      */ 
/*      */     
/* 2476 */     String parameterValue = "View";
/* 2477 */     if (acl != null)
/*      */     {
/* 2479 */       if (acl.getAccessParameter() == 2)
/*      */       {
/* 2481 */         parameterValue = "Edit";
/*      */       }
/*      */     }
/* 2484 */     FormRadioButtonGroup parameterAccess = new FormRadioButtonGroup("16", parameterValue, "View, Edit", false);
/* 2485 */     parameterAccess.setTabIndex(26);
/* 2486 */     userCompanySecurityForm.addElement(parameterAccess);
/*      */ 
/*      */     
/* 2489 */     String auditTrailValue = "View";
/* 2490 */     if (acl != null)
/*      */     {
/* 2492 */       if (acl.getAccessAuditTrail() == 2)
/*      */       {
/* 2494 */         auditTrailValue = "Edit";
/*      */       }
/*      */     }
/* 2497 */     FormRadioButtonGroup auditTrailAccess = new FormRadioButtonGroup("17", auditTrailValue, "View, Edit", false);
/* 2498 */     auditTrailAccess.setTabIndex(27);
/* 2499 */     userCompanySecurityForm.addElement(auditTrailAccess);
/*      */ 
/*      */     
/* 2502 */     String reportConfigValue = "View";
/* 2503 */     if (acl != null)
/*      */     {
/* 2505 */       if (acl.getAccessReportConfig() == 2)
/*      */       {
/* 2507 */         reportConfigValue = "Edit";
/*      */       }
/*      */     }
/* 2510 */     FormRadioButtonGroup reportConfigAccess = new FormRadioButtonGroup("18", reportConfigValue, "View, Edit", false);
/* 2511 */     reportConfigAccess.setTabIndex(28);
/* 2512 */     userCompanySecurityForm.addElement(reportConfigAccess);
/*      */ 
/*      */     
/* 2515 */     String priceCodeValue = "View";
/* 2516 */     if (acl != null)
/*      */     {
/* 2518 */       if (acl.getAccessPriceCode() == 2)
/*      */       {
/* 2520 */         priceCodeValue = "Edit";
/*      */       }
/*      */     }
/* 2523 */     FormRadioButtonGroup priceCodeAccess = new FormRadioButtonGroup("19", priceCodeValue, "View, Edit", false);
/* 2524 */     priceCodeAccess.setTabIndex(29);
/* 2525 */     userCompanySecurityForm.addElement(priceCodeAccess);
/*      */ 
/*      */     
/* 2528 */     FormTextField nameSrch = new FormTextField("nameSrch", "", false, 20);
/* 2529 */     nameSrch.setId("nameSrch");
/* 2530 */     userCompanySecurityForm.addElement(nameSrch);
/*      */     
/* 2532 */     userCompanySecurityForm.addElement(new FormHidden("cmd", "user-environment-security-editor"));
/*      */     
/* 2534 */     context.putDelivery("Form", userCompanySecurityForm);
/* 2535 */     context.putDelivery("currentUser", currentUser);
/*      */     
/* 2537 */     context.putDelivery("currentEnvironment", currentEnvironment);
/*      */ 
/*      */     
/* 2540 */     if (context.getSessionValue("NOTEPAD_USER_ENVIRONMENTS_VISIBLE") != null) {
/* 2541 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_USER_ENVIRONMENTS_VISIBLE"));
/*      */     }
/* 2543 */     return userCompanySecurityForm;
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
/* 2584 */     Vector contents = new Vector();
/*      */     
/* 2586 */     if (MilestoneHelper.getNotepadFromSession(7, context) != null) {
/*      */       
/* 2588 */       Notepad notepad = MilestoneHelper.getNotepadFromSession(7, context);
/*      */       
/* 2590 */       if (notepad.getAllContents() == null) {
/*      */         
/* 2592 */         this.log.debug("---------Reseting note pad contents------------");
/* 2593 */         contents = UserManager.getInstance().getUserNotepadList(notepad);
/* 2594 */         notepad.setAllContents(contents);
/*      */       } 
/*      */       
/* 2597 */       return notepad;
/*      */     } 
/*      */ 
/*      */     
/* 2601 */     String[] columnNames = { "Login Name", "User Name", "Employed by" };
/* 2602 */     contents = UserManager.getInstance().getUserNotepadList(null);
/* 2603 */     Notepad notepad = new Notepad(contents, 0, 7, "Users", 7, columnNames);
/* 2604 */     UserManager.getInstance().setUserNotepadQuery(context, notepad);
/*      */     
/* 2606 */     return notepad;
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
/* 2617 */     if (MilestoneHelper.getNotepadFromSession(21, context) != null) {
/*      */       
/* 2619 */       Notepad notepad = MilestoneHelper.getNotepadFromSession(21, context);
/*      */       
/* 2621 */       contents = UserManager.getInstance().getUserEnvironmentNotepadList(userId, notepad);
/*      */       
/* 2623 */       notepad.setAllContents(contents);
/* 2624 */       return notepad;
/*      */     } 
/*      */ 
/*      */     
/* 2628 */     String[] columnNames = { "Environment Name", "Family Name" };
/*      */     
/* 2630 */     contents = UserManager.getInstance().getUserEnvironmentNotepadList(userId, null);
/* 2631 */     return new Notepad(contents, 0, 7, "User-Environments", 21, columnNames);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean userCompanySecuritySearch(Context context) {
/* 2641 */     Notepad notepad = MilestoneHelper.getNotepadFromSession(21, context);
/* 2642 */     UserManager.getInstance().setUserCompanyNotepadQuery(context, notepad);
/* 2643 */     userCompanySecurityEditor(context);
/*      */     
/* 2645 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/* 2650 */   private boolean userCompanySecuritySearchResults(Context context) { return context.includeJSP("user-company-security-search-results.jsp"); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean userCompanySecurityEditor(Context context) {
/* 2658 */     User user = MilestoneHelper.getScreenUser(context);
/* 2659 */     Company company = null;
/* 2660 */     Environment environment = null;
/* 2661 */     Vector contents = new Vector();
/*      */     
/* 2663 */     if (user == null) {
/* 2664 */       return userSecurityEditor(context);
/*      */     }
/* 2666 */     Notepad notepad = getUserCompanyNotepad(contents, context, user.getUserId());
/* 2667 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */     
/* 2669 */     environment = MilestoneHelper.getScreenUserEnvironment(context);
/* 2670 */     Vector environments = MilestoneHelper.getUserEnvironments(user.getUserId());
/*      */     
/* 2672 */     if (environments.size() > 0) {
/*      */       
/* 2674 */       Form form = null;
/* 2675 */       if (environment != null) {
/*      */         
/* 2677 */         form = buildCompanyForm(context, user, environment);
/*      */       }
/*      */       else {
/*      */         
/* 2681 */         form = buildBlankCompanyForm(context);
/* 2682 */         if (context.getSessionValue("NOTEPAD_USER_ENVIRONMENTS_VISIBLE") != null)
/* 2683 */           context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_USER_ENVIRONMENTS_VISIBLE")); 
/* 2684 */         return context.includeJSP("blank-user-company-security-editor.jsp");
/*      */       } 
/* 2686 */       return context.includeJSP("user-company-security-editor.jsp");
/*      */     } 
/*      */ 
/*      */     
/* 2690 */     return userSecurityEditor(context);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean userCompanySecuritySave(Context context) {
/* 2699 */     User sessionUser = MilestoneSecurity.getUser(context);
/*      */ 
/*      */ 
/*      */     
/* 2703 */     Environment currentEnvironment = null;
/* 2704 */     currentEnvironment = MilestoneHelper.getScreenUserEnvironment(context);
/*      */     
/* 2706 */     User currentUser = (User)context.getSessionValue("securityUser");
/* 2707 */     Acl currentAcl = currentUser.getAcl();
/* 2708 */     Vector companyAcl = currentAcl.getCompanyAcl();
/* 2709 */     CompanyAcl acl = null;
/* 2710 */     CompanyAcl tempAcl = null;
/*      */ 
/*      */ 
/*      */     
/* 2714 */     Vector childrenCompanies = currentEnvironment.getChildren();
/* 2715 */     Vector companyIDs = new Vector();
/* 2716 */     for (int k = 0; k < childrenCompanies.size(); k++) {
/* 2717 */       Company company1 = (Company)childrenCompanies.elementAt(k);
/* 2718 */       companyIDs.add(new Integer(company1.getStructureID()));
/*      */     } 
/*      */     
/* 2721 */     for (int i = 0; i < companyAcl.size(); i++) {
/*      */       
/* 2723 */       tempAcl = (CompanyAcl)companyAcl.get(i);
/* 2724 */       if (tempAcl != null) {
/*      */         
/* 2726 */         Environment aclEnvironment = 
/* 2727 */           (Environment)MilestoneHelper.getStructureObject(tempAcl.getParentID());
/* 2728 */         Integer companyID = new Integer(tempAcl.getCompanyId());
/*      */         
/* 2730 */         if (companyIDs.contains(companyID)) {
/* 2731 */           acl = tempAcl;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 2736 */     Form form = buildCompanyForm(context, currentUser, currentEnvironment);
/* 2737 */     form.setValues(context);
/*      */     
/* 2739 */     String selectionValue = form.getStringValue("1");
/* 2740 */     int selectionBool = 0;
/*      */     
/* 2742 */     if (currentAcl.getAccessSelection() && selectionValue.equalsIgnoreCase("Edit")) {
/* 2743 */       selectionBool = 2;
/* 2744 */     } else if (currentAcl.getAccessSelection()) {
/* 2745 */       selectionBool = 1;
/*      */     } 
/* 2747 */     acl.setAccessSelection(selectionBool);
/*      */ 
/*      */     
/* 2750 */     String scheduleValue = form.getStringValue("2");
/* 2751 */     int scheduleBool = 0;
/*      */     
/* 2753 */     if (currentAcl.getAccessSchedule() && scheduleValue.equalsIgnoreCase("Edit")) {
/* 2754 */       scheduleBool = 2;
/* 2755 */     } else if (currentAcl.getAccessSchedule()) {
/* 2756 */       scheduleBool = 1;
/*      */     } 
/* 2758 */     acl.setAccessSchedule(scheduleBool);
/*      */ 
/*      */     
/* 2761 */     String manufacturingValue = form.getStringValue("3");
/* 2762 */     int manufacturingBool = 0;
/*      */     
/* 2764 */     if (currentAcl.getAccessManufacturing() && manufacturingValue.equalsIgnoreCase("Edit")) {
/* 2765 */       manufacturingBool = 2;
/* 2766 */     } else if (currentAcl.getAccessManufacturing()) {
/* 2767 */       manufacturingBool = 1;
/*      */     } 
/* 2769 */     acl.setAccessManufacturing(manufacturingBool);
/*      */ 
/*      */     
/* 2772 */     String pfmValue = form.getStringValue("4");
/* 2773 */     int pfmBool = 0;
/*      */     
/* 2775 */     if (currentAcl.getAccessPfmForm() && pfmValue.equalsIgnoreCase("Edit")) {
/* 2776 */       pfmBool = 2;
/* 2777 */     } else if (currentAcl.getAccessPfmForm()) {
/* 2778 */       pfmBool = 1;
/*      */     } 
/* 2780 */     acl.setAccessPfmForm(pfmBool);
/*      */ 
/*      */     
/* 2783 */     String bomValue = form.getStringValue("5");
/* 2784 */     int bomBool = 0;
/*      */     
/* 2786 */     if (currentAcl.getAccessBomForm() && bomValue.equalsIgnoreCase("Edit")) {
/* 2787 */       bomBool = 2;
/* 2788 */     } else if (currentAcl.getAccessBomForm()) {
/* 2789 */       bomBool = 1;
/*      */     } 
/* 2791 */     acl.setAccessBomForm(bomBool);
/*      */ 
/*      */     
/* 2794 */     String reportValue = form.getStringValue("6");
/* 2795 */     int reportBool = 0;
/*      */     
/* 2797 */     if (currentAcl.getAccessReport() && reportValue.equalsIgnoreCase("Edit")) {
/* 2798 */       reportBool = 2;
/* 2799 */     } else if (currentAcl.getAccessReport()) {
/* 2800 */       reportBool = 1;
/*      */     } 
/* 2802 */     acl.setAccessReport(reportBool);
/*      */ 
/*      */     
/* 2805 */     String templateValue = form.getStringValue("7");
/* 2806 */     int templateBool = 0;
/*      */     
/* 2808 */     if (currentAcl.getAccessTemplate() && templateValue.equalsIgnoreCase("Edit")) {
/* 2809 */       templateBool = 2;
/* 2810 */     } else if (currentAcl.getAccessTemplate()) {
/* 2811 */       templateBool = 1;
/*      */     } 
/* 2813 */     acl.setAccessTemplate(templateBool);
/*      */ 
/*      */     
/* 2816 */     String taskValue = form.getStringValue("8");
/* 2817 */     int taskBool = 0;
/*      */     
/* 2819 */     if (currentAcl.getAccessTask() && taskValue.equalsIgnoreCase("Edit")) {
/* 2820 */       taskBool = 2;
/* 2821 */     } else if (currentAcl.getAccessTask()) {
/* 2822 */       taskBool = 1;
/*      */     } 
/* 2824 */     acl.setAccessTask(taskBool);
/*      */ 
/*      */     
/* 2827 */     String dayValue = form.getStringValue("9");
/* 2828 */     int dayBool = 0;
/*      */     
/* 2830 */     if (currentAcl.getAccessDayType() && dayValue.equalsIgnoreCase("Edit")) {
/* 2831 */       dayBool = 2;
/* 2832 */     } else if (currentAcl.getAccessDayType()) {
/* 2833 */       dayBool = 1;
/*      */     } 
/* 2835 */     acl.setAccessDayType(dayBool);
/*      */ 
/*      */     
/* 2838 */     String userValue = form.getStringValue("10");
/* 2839 */     int userBool = 0;
/*      */     
/* 2841 */     if (currentAcl.getAccessUser() && userValue.equalsIgnoreCase("Edit")) {
/* 2842 */       userBool = 2;
/* 2843 */     } else if (currentAcl.getAccessUser()) {
/* 2844 */       userBool = 1;
/*      */     } 
/* 2846 */     acl.setAccessUser(userBool);
/*      */ 
/*      */     
/* 2849 */     String familyValue = form.getStringValue("11");
/* 2850 */     int familyBool = 0;
/*      */     
/* 2852 */     if (currentAcl.getAccessFamily() && familyValue.equalsIgnoreCase("Edit")) {
/* 2853 */       familyBool = 2;
/* 2854 */     } else if (currentAcl.getAccessFamily()) {
/* 2855 */       familyBool = 1;
/*      */     } 
/* 2857 */     acl.setAccessFamily(familyBool);
/*      */ 
/*      */     
/* 2860 */     String environmentValue = form.getStringValue("20");
/* 2861 */     int environmentBool = 0;
/*      */     
/* 2863 */     if (currentAcl.getAccessEnvironment() && environmentValue.equalsIgnoreCase("Edit")) {
/* 2864 */       environmentBool = 2;
/* 2865 */     } else if (currentAcl.getAccessEnvironment()) {
/* 2866 */       environmentBool = 1;
/*      */     } 
/* 2868 */     acl.setAccessEnvironment(environmentBool);
/*      */ 
/*      */     
/* 2871 */     String companyValue = form.getStringValue("12");
/* 2872 */     int companyBool = 0;
/*      */     
/* 2874 */     if (currentAcl.getAccessCompany() && companyValue.equalsIgnoreCase("Edit")) {
/* 2875 */       companyBool = 2;
/* 2876 */     } else if (currentAcl.getAccessCompany()) {
/* 2877 */       companyBool = 1;
/*      */     } 
/* 2879 */     acl.setAccessCompany(companyBool);
/*      */ 
/*      */     
/* 2882 */     String divisionValue = form.getStringValue("13");
/* 2883 */     int divisionBool = 0;
/*      */     
/* 2885 */     if (currentAcl.getAccessDivision() && divisionValue.equalsIgnoreCase("Edit")) {
/* 2886 */       divisionBool = 2;
/* 2887 */     } else if (currentAcl.getAccessDivision()) {
/* 2888 */       divisionBool = 1;
/*      */     } 
/* 2890 */     acl.setAccessDivision(divisionBool);
/*      */ 
/*      */     
/* 2893 */     String labelValue = form.getStringValue("14");
/* 2894 */     int labelBool = 0;
/*      */     
/* 2896 */     if (currentAcl.getAccessLabel() && labelValue.equalsIgnoreCase("Edit")) {
/* 2897 */       labelBool = 2;
/* 2898 */     } else if (currentAcl.getAccessLabel()) {
/* 2899 */       labelBool = 1;
/*      */     } 
/* 2901 */     acl.setAccessLabel(labelBool);
/*      */ 
/*      */     
/* 2904 */     String tableValue = form.getStringValue("15");
/* 2905 */     int tableBool = 0;
/*      */     
/* 2907 */     if (currentAcl.getAccessTable() && tableValue.equalsIgnoreCase("Edit")) {
/* 2908 */       tableBool = 2;
/* 2909 */     } else if (currentAcl.getAccessTable()) {
/* 2910 */       tableBool = 1;
/*      */     } 
/* 2912 */     acl.setAccessTable(tableBool);
/*      */ 
/*      */     
/* 2915 */     String parameterValue = form.getStringValue("16");
/* 2916 */     int parameterBool = 0;
/*      */     
/* 2918 */     if (currentAcl.getAccessParameter() && parameterValue.equalsIgnoreCase("Edit")) {
/* 2919 */       parameterBool = 2;
/* 2920 */     } else if (currentAcl.getAccessParameter()) {
/* 2921 */       parameterBool = 1;
/*      */     } 
/* 2923 */     acl.setAccessParameter(parameterBool);
/*      */ 
/*      */     
/* 2926 */     String auditValue = form.getStringValue("17");
/* 2927 */     int auditBool = 0;
/*      */     
/* 2929 */     if (currentAcl.getAccessAuditTrail() && auditValue.equalsIgnoreCase("Edit")) {
/* 2930 */       auditBool = 2;
/* 2931 */     } else if (currentAcl.getAccessAuditTrail()) {
/* 2932 */       auditBool = 1;
/*      */     } 
/* 2934 */     acl.setAccessAuditTrail(auditBool);
/*      */ 
/*      */     
/* 2937 */     String configValue = form.getStringValue("18");
/* 2938 */     int configBool = 0;
/*      */     
/* 2940 */     if (currentAcl.getAccessReportConfig() && configValue.equalsIgnoreCase("Edit")) {
/* 2941 */       configBool = 2;
/* 2942 */     } else if (currentAcl.getAccessReportConfig()) {
/* 2943 */       configBool = 1;
/*      */     } 
/* 2945 */     acl.setAccessReportConfig(configBool);
/*      */ 
/*      */     
/* 2948 */     String priceValue = form.getStringValue("19");
/* 2949 */     int priceBool = 0;
/*      */     
/* 2951 */     if (currentAcl.getAccessPriceCode() && priceValue.equalsIgnoreCase("Edit")) {
/* 2952 */       priceBool = 2;
/* 2953 */     } else if (currentAcl.getAccessPriceCode()) {
/* 2954 */       priceBool = 1;
/*      */     } 
/* 2956 */     acl.setAccessPriceCode(priceBool);
/*      */     
/* 2958 */     if (!form.isUnchanged()) {
/*      */       
/* 2960 */       FormValidation formValidation = form.validate();
/* 2961 */       if (formValidation.isGood()) {
/*      */ 
/*      */         
/* 2964 */         Vector companyVector = currentEnvironment.getCompanies();
/* 2965 */         for (int j = 0; j < companyVector.size(); j++) {
/* 2966 */           Company environmentCompany = (Company)companyVector.elementAt(j);
/* 2967 */           UserManager.getInstance().saveCompany(currentUser, sessionUser, acl, environmentCompany);
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2973 */         Cache.flushCachedVariableAllUsers(currentUser.getUserId());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2980 */         this.log.debug("===============================clearUserCompaniesFromSession called.");
/* 2981 */         MilestoneHelper.clearUserCompaniesFromSession(context);
/*      */         
/* 2983 */         form = buildCompanyForm(context, currentUser, currentEnvironment);
/*      */         
/* 2985 */         context.putDelivery("Form", form);
/*      */ 
/*      */         
/* 2988 */         if (currentUser.getUserId() == sessionUser.getUserId()) {
/*      */           
/* 2990 */           context.removeSessionValue("user");
/*      */           
/* 2992 */           context.putSessionValue("UserDefaultsApplied", "true");
/* 2993 */           context.putSessionValue("user", currentUser);
/*      */         } 
/*      */ 
/*      */         
/* 2997 */         Vector contents = new Vector();
/* 2998 */         Notepad notepad = getUserCompanyNotepad(contents, context, currentUser.getUserId());
/* 2999 */         MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */       }
/*      */       else {
/*      */         
/* 3003 */         context.putDelivery("FormValidation", formValidation);
/*      */       } 
/*      */     } 
/* 3006 */     context.putDelivery("Form", form);
/* 3007 */     return context.includeJSP("user-company-security-editor.jsp");
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
/* 3020 */     context.putSessionValue("showAssigned", "NEW");
/*      */ 
/*      */     
/* 3023 */     Form userSecurityForm = new Form(this.application, "userSecurityForm", 
/* 3024 */         this.application.getInfrastructure().getServletURL(), "POST");
/*      */ 
/*      */     
/* 3027 */     FormTextField login = new FormTextField("login", "", true, 30, 100);
/* 3028 */     login.setTabIndex(1);
/* 3029 */     userSecurityForm.addElement(login);
/*      */ 
/*      */     
/* 3032 */     FormPasswordField password = new FormPasswordField("password", "", true, 30, 30);
/* 3033 */     password.setTabIndex(2);
/* 3034 */     userSecurityForm.addElement(password);
/*      */ 
/*      */     
/* 3037 */     FormTextField reportto = new FormTextField("reportto", "", false, 30, 30);
/* 3038 */     reportto.setTabIndex(3);
/* 3039 */     userSecurityForm.addElement(reportto);
/*      */ 
/*      */ 
/*      */     
/* 3043 */     FormTextField fullname = new FormTextField("fullname", "", true, 30, 100);
/* 3044 */     fullname.setTabIndex(4);
/* 3045 */     userSecurityForm.addElement(fullname);
/*      */ 
/*      */     
/* 3048 */     Vector userLocations = Cache.getLookupDetailValuesFromDatabase(53);
/* 3049 */     FormDropDownMenu location = MilestoneHelper.getLookupDropDown("location", userLocations, "", false, true);
/* 3050 */     location.setTabIndex(5);
/* 3051 */     userSecurityForm.addElement(location);
/*      */ 
/*      */     
/* 3054 */     Vector families = Cache.getInstance().getFamilies();
/*      */     
/* 3056 */     FormDropDownMenu employedby = MilestoneHelper.getCorporateStructureDropDown("employedby", families, "", false, true);
/* 3057 */     employedby.setTabIndex(6);
/* 3058 */     userSecurityForm.addElement(employedby);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3063 */     FormTextField email = new FormTextField("email", "", false, 30, 50);
/* 3064 */     email.setTabIndex(7);
/* 3065 */     userSecurityForm.addElement(email);
/*      */     
/* 3067 */     FormTextField phone = new FormTextField("phone", "", false, 30, 30);
/* 3068 */     phone.setTabIndex(8);
/* 3069 */     userSecurityForm.addElement(phone);
/*      */     
/* 3071 */     FormTextField fax = new FormTextField("fax", "", false, 30, 30);
/* 3072 */     fax.setTabIndex(11);
/* 3073 */     userSecurityForm.addElement(fax);
/*      */     
/* 3075 */     FormCheckBox inactive = new FormCheckBox("inactive", "Inactive", false, false);
/* 3076 */     inactive.setId("inactive");
/* 3077 */     inactive.setTabIndex(12);
/* 3078 */     userSecurityForm.addElement(inactive);
/*      */ 
/*      */     
/* 3081 */     FormCheckBox administrator = new FormCheckBox("administrator", "Administrator", false, false);
/* 3082 */     administrator.setId("adminstrator");
/* 3083 */     administrator.setTabIndex(13);
/* 3084 */     userSecurityForm.addElement(administrator);
/*      */ 
/*      */ 
/*      */     
/* 3088 */     FormDropDownMenu deptFilter = MilestoneHelper.getDepartmentDropDown("deptFilter", "All", false);
/* 3089 */     String[] values = deptFilter.getValueList();
/* 3090 */     String[] menuText = deptFilter.getMenuTextList();
/*      */     
/* 3092 */     values[0] = "All";
/* 3093 */     menuText[0] = "All";
/* 3094 */     deptFilter.setValueList(values);
/* 3095 */     deptFilter.setMenuTextList(menuText);
/* 3096 */     deptFilter.setTabIndex(12);
/* 3097 */     userSecurityForm.addElement(deptFilter);
/*      */     
/* 3099 */     FormRadioButtonGroup IsModifyDept = new FormRadioButtonGroup("IsModifyDept", "Yes", "Yes, No", false);
/* 3100 */     IsModifyDept.setTabIndex(13);
/* 3101 */     userSecurityForm.addElement(IsModifyDept);
/*      */ 
/*      */ 
/*      */     
/* 3105 */     FormDropDownMenu filter = new FormDropDownMenu("filter", "All", "All,Only Label Tasks, Only UML Tasks", true);
/* 3106 */     filter.setTabIndex(9);
/* 3107 */     userSecurityForm.addElement(filter);
/*      */ 
/*      */     
/* 3110 */     FormRadioButtonGroup IsModify = new FormRadioButtonGroup("IsModify", "Yes", "Yes, No", false);
/* 3111 */     IsModify.setTabIndex(10);
/* 3112 */     userSecurityForm.addElement(IsModify);
/*      */ 
/*      */     
/* 3115 */     Vector environments = Cache.getInstance().getEnvironments();
/* 3116 */     for (int i = 0; i < environments.size(); i++) {
/*      */       
/* 3118 */       Environment environment = (Environment)environments.elementAt(i);
/* 3119 */       if (environment != null) {
/*      */         
/* 3121 */         FormCheckBox environmentCheckbox = new FormCheckBox("ue" + environment.getStructureID(), "ue" + environment.getStructureID(), environment.getName(), false, false);
/* 3122 */         environmentCheckbox.setId("ue" + environment.getStructureID());
/* 3123 */         userSecurityForm.addElement(environmentCheckbox);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3130 */     String selectionValue = "Not Available";
/* 3131 */     FormRadioButtonGroup selectionAccess = new FormRadioButtonGroup("1", selectionValue, "Not Available, Available", false);
/* 3132 */     selectionAccess.setTabIndex(10);
/* 3133 */     userSecurityForm.addElement(selectionAccess);
/*      */ 
/*      */     
/* 3136 */     String scheduleValue = "Not Available";
/* 3137 */     FormRadioButtonGroup scheduleAccess = new FormRadioButtonGroup("2", scheduleValue, "Not Available, Available", false);
/* 3138 */     scheduleAccess.setTabIndex(11);
/* 3139 */     userSecurityForm.addElement(scheduleAccess);
/*      */ 
/*      */     
/* 3142 */     String manufacturingValue = "Not Available";
/* 3143 */     FormRadioButtonGroup manufacturingAccess = new FormRadioButtonGroup("3", manufacturingValue, "Not Available, Available", false);
/* 3144 */     manufacturingAccess.setTabIndex(12);
/* 3145 */     userSecurityForm.addElement(manufacturingAccess);
/*      */ 
/*      */     
/* 3148 */     String pfmValue = "Not Available";
/* 3149 */     FormRadioButtonGroup pfmAccess = new FormRadioButtonGroup("4", pfmValue, "Not Available, Available", false);
/* 3150 */     pfmAccess.setTabIndex(13);
/* 3151 */     userSecurityForm.addElement(pfmAccess);
/*      */ 
/*      */     
/* 3154 */     String bomValue = "Not Available";
/* 3155 */     FormRadioButtonGroup bomAccess = new FormRadioButtonGroup("5", bomValue, "Not Available, Available", false);
/* 3156 */     bomAccess.setTabIndex(14);
/* 3157 */     userSecurityForm.addElement(bomAccess);
/*      */ 
/*      */     
/* 3160 */     String reportValue = "Not Available";
/* 3161 */     FormRadioButtonGroup reportAccess = new FormRadioButtonGroup("6", reportValue, "Not Available, Available", false);
/* 3162 */     reportAccess.setTabIndex(15);
/* 3163 */     userSecurityForm.addElement(reportAccess);
/*      */ 
/*      */     
/* 3166 */     String templateValue = "Not Available";
/* 3167 */     FormRadioButtonGroup templateAccess = new FormRadioButtonGroup("7", templateValue, "Not Available, Available", false);
/* 3168 */     templateAccess.setTabIndex(16);
/* 3169 */     userSecurityForm.addElement(templateAccess);
/*      */ 
/*      */     
/* 3172 */     String taskValue = "Not Available";
/* 3173 */     FormRadioButtonGroup taskAccess = new FormRadioButtonGroup("8", taskValue, "Not Available, Available", false);
/* 3174 */     taskAccess.setTabIndex(17);
/* 3175 */     userSecurityForm.addElement(taskAccess);
/*      */ 
/*      */     
/* 3178 */     String dayTypeValue = "Not Available";
/* 3179 */     FormRadioButtonGroup dayTypeAccess = new FormRadioButtonGroup("9", dayTypeValue, "Not Available, Available", false);
/* 3180 */     dayTypeAccess.setTabIndex(18);
/* 3181 */     userSecurityForm.addElement(dayTypeAccess);
/*      */ 
/*      */     
/* 3184 */     String userValue = "Not Available";
/* 3185 */     FormRadioButtonGroup userAccess = new FormRadioButtonGroup("10", userValue, "Not Available, Available", false);
/* 3186 */     userAccess.setTabIndex(19);
/* 3187 */     userSecurityForm.addElement(userAccess);
/*      */ 
/*      */     
/* 3190 */     String familyValue = "Not Available";
/* 3191 */     FormRadioButtonGroup familyAccess = new FormRadioButtonGroup("11", familyValue, "Not Available, Available", false);
/* 3192 */     familyAccess.setTabIndex(20);
/* 3193 */     userSecurityForm.addElement(familyAccess);
/*      */ 
/*      */     
/* 3196 */     String environmentValue = "Not Available";
/* 3197 */     FormRadioButtonGroup environmentAccess = new FormRadioButtonGroup("20", environmentValue, "Not Available, Available", false);
/* 3198 */     environmentAccess.setTabIndex(21);
/* 3199 */     userSecurityForm.addElement(environmentAccess);
/*      */ 
/*      */     
/* 3202 */     String companyValue = "Not Available";
/* 3203 */     FormRadioButtonGroup companyAccess = new FormRadioButtonGroup("12", companyValue, "Not Available, Available", false);
/* 3204 */     companyAccess.setTabIndex(22);
/* 3205 */     userSecurityForm.addElement(companyAccess);
/*      */ 
/*      */     
/* 3208 */     String divisionValue = "Not Available";
/* 3209 */     FormRadioButtonGroup divisionAccess = new FormRadioButtonGroup("13", divisionValue, "Not Available, Available", false);
/* 3210 */     divisionAccess.setTabIndex(23);
/* 3211 */     userSecurityForm.addElement(divisionAccess);
/*      */ 
/*      */     
/* 3214 */     String labelValue = "Not Available";
/* 3215 */     FormRadioButtonGroup labelAccess = new FormRadioButtonGroup("14", labelValue, "Not Available, Available", false);
/* 3216 */     labelAccess.setTabIndex(24);
/* 3217 */     userSecurityForm.addElement(labelAccess);
/*      */ 
/*      */     
/* 3220 */     String tableValue = "Not Available";
/* 3221 */     FormRadioButtonGroup tableAccess = new FormRadioButtonGroup("15", tableValue, "Not Available, Available", false);
/* 3222 */     tableAccess.setTabIndex(25);
/* 3223 */     userSecurityForm.addElement(tableAccess);
/*      */ 
/*      */     
/* 3226 */     String parameterValue = "Not Available";
/* 3227 */     FormRadioButtonGroup parameterAccess = new FormRadioButtonGroup("16", parameterValue, "Not Available, Available", false);
/* 3228 */     parameterAccess.setTabIndex(26);
/* 3229 */     userSecurityForm.addElement(parameterAccess);
/*      */ 
/*      */     
/* 3232 */     String auditTrailValue = "Not Available";
/* 3233 */     FormRadioButtonGroup auditTrailAccess = new FormRadioButtonGroup("17", auditTrailValue, "Not Available, Available", false);
/* 3234 */     auditTrailAccess.setTabIndex(27);
/* 3235 */     userSecurityForm.addElement(auditTrailAccess);
/*      */ 
/*      */     
/* 3238 */     String reportConfigValue = "Not Available";
/* 3239 */     FormRadioButtonGroup reportConfigAccess = new FormRadioButtonGroup("18", reportConfigValue, "Not Available, Available", false);
/* 3240 */     reportConfigAccess.setTabIndex(28);
/* 3241 */     userSecurityForm.addElement(reportConfigAccess);
/*      */ 
/*      */     
/* 3244 */     String priceCodeValue = "Not Available";
/* 3245 */     FormRadioButtonGroup priceCodeAccess = new FormRadioButtonGroup("19", priceCodeValue, "Not Available, Available", false);
/* 3246 */     priceCodeAccess.setTabIndex(29);
/* 3247 */     userSecurityForm.addElement(priceCodeAccess);
/*      */ 
/*      */     
/* 3250 */     FormTextField lastUpdated = new FormTextField("lastUpdatedDate", false, 50);
/* 3251 */     userSecurityForm.addElement(lastUpdated);
/*      */     
/* 3253 */     FormTextField lastUpdatedBy = new FormTextField("lastUpdatedBy", false, 50);
/* 3254 */     userSecurityForm.addElement(lastUpdatedBy);
/*      */ 
/*      */     
/* 3257 */     FormTextField nameSrch = new FormTextField("nameSrch", "", false, 20);
/* 3258 */     nameSrch.setId("nameSrch");
/* 3259 */     userSecurityForm.addElement(nameSrch);
/*      */     
/* 3261 */     FormTextField userNameSrch = new FormTextField("userNameSrch", "", false, 20);
/* 3262 */     userNameSrch.setId("userNameSrch");
/* 3263 */     userSecurityForm.addElement(userNameSrch);
/*      */ 
/*      */     
/* 3266 */     String employedBySrcString = "";
/* 3267 */     FormDropDownMenu employedBySrc = MilestoneHelper.getCorporateStructureDropDown("employedBySrc", families, employedBySrcString, true, true);
/* 3268 */     employedBySrc.setId("employedBySrc");
/* 3269 */     userSecurityForm.addElement(employedBySrc);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3274 */     FormDropDownMenu environmentDescriptionSearch = MilestoneHelper.getCorporateStructureDropDown("EnvironmentDescriptionSearch", environments, "", false, true);
/* 3275 */     environmentDescriptionSearch.setId("EnvironmentDescriptionSearch");
/* 3276 */     userSecurityForm.addElement(environmentDescriptionSearch);
/*      */ 
/*      */ 
/*      */     
/* 3280 */     userSecurityForm.addElement(new FormHidden("cmd", "user-security-new"));
/* 3281 */     context.putDelivery("Form", userSecurityForm);
/*      */ 
/*      */     
/* 3284 */     if (context.getSessionValue("NOTEPAD_USER_VISIBLE") != null) {
/* 3285 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_USER_VISIBLE"));
/*      */     }
/* 3287 */     return userSecurityForm;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean userSecuritySort(Context context) {
/* 3293 */     int sort = Integer.parseInt(context.getParameter("OrderBy"));
/* 3294 */     int userId = ((User)context.getSessionValue("user")).getUserId();
/*      */     
/* 3296 */     Notepad notepad = MilestoneHelper.getNotepadFromSession(7, context);
/*      */     
/* 3298 */     if (notepad.getSearchQuery() == null || notepad.getSearchQuery().equals("")) {
/* 3299 */       notepad.setSearchQuery(UserManager.getInstance().getDefaultQuery());
/*      */     }
/*      */     
/* 3302 */     notepad.setOrderBy(" ORDER BY vi_all_user.[" + MilestoneConstants.SORT_USER[sort] + "]");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3307 */     notepad.setAllContents(null);
/* 3308 */     notepad = getUsersNotepad(context);
/* 3309 */     notepad.goToSelectedPage();
/*      */ 
/*      */     
/* 3312 */     userSecurityEditor(context);
/*      */     
/* 3314 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean userCompanySecuritySort(Context context) {
/* 3323 */     int sort = Integer.parseInt(context.getParameter("OrderBy"));
/* 3324 */     User user = MilestoneHelper.getScreenUser(context);
/* 3325 */     int userId = user.getUserId();
/*      */     
/* 3327 */     Notepad notepad = MilestoneHelper.getNotepadFromSession(21, context);
/*      */     
/* 3329 */     if (notepad.getSearchQuery() == null || notepad.getSearchQuery().equals("")) {
/* 3330 */       notepad.setSearchQuery(UserManager.getInstance().getDefaultUserCompanyQuery(userId));
/*      */     }
/* 3332 */     notepad.setOrderBy(" ORDER BY " + MilestoneConstants.SORT_COMPANY_USER[sort]);
/*      */ 
/*      */     
/* 3335 */     notepad.setAllContents(null);
/* 3336 */     notepad = getUsersNotepad(context);
/* 3337 */     notepad.goToSelectedPage();
/*      */ 
/*      */     
/* 3340 */     userCompanySecurityEditor(context);
/*      */     
/* 3342 */     return true;
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
/* 3358 */     context.putDelivery("isNotePadVisible", new Boolean(MilestoneHelper.getNotePadVisiblitiy(7, context)));
/*      */     
/* 3360 */     Form form = new Form(this.application, "form", this.application.getInfrastructure().getServletURL(), "Post");
/* 3361 */     form.addElement(new FormHidden("cmd", "daytype-editor"));
/* 3362 */     form.addElement(new FormHidden("OrderBy", "", true));
/*      */ 
/*      */     
/* 3365 */     String employedBySrcString = "";
/*      */     
/* 3367 */     Vector families = Cache.getInstance().getFamilies();
/* 3368 */     FormDropDownMenu employedBySrc = MilestoneHelper.getCorporateStructureDropDown("employedBySrc", families, employedBySrcString, true, true);
/* 3369 */     employedBySrc.setId("employedBySrc");
/* 3370 */     form.addElement(employedBySrc);
/*      */ 
/*      */ 
/*      */     
/* 3374 */     Vector environments = Cache.getInstance().getEnvironments();
/* 3375 */     FormDropDownMenu environmentDescriptionSearch = MilestoneHelper.getCorporateStructureDropDown("EnvironmentDescriptionSearch", environments, "", false, true);
/* 3376 */     environmentDescriptionSearch.setId("EnvironmentDescriptionSearch");
/* 3377 */     form.addElement(environmentDescriptionSearch);
/*      */ 
/*      */ 
/*      */     
/* 3381 */     context.putDelivery("Form", form);
/*      */     
/* 3383 */     return context.includeJSP("blank-user-security-editor.jsp");
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
/* 3395 */     ReleasingFamily.save(context);
/*      */ 
/*      */ 
/*      */     
/* 3399 */     return userSecurityEditor(context);
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
/* 3411 */     context.putSessionValue("ScheduleTaskSort", "-1");
/*      */     
/* 3413 */     context.putSessionValue("ScheduleTaskSortDigital", "-1");
/*      */     
/* 3415 */     context.putSessionValue("ResetOpeningScreen", "true");
/*      */     
/* 3417 */     User userClone = null;
/*      */     try {
/* 3419 */       userClone = (User)sessionUser.clone();
/*      */     }
/* 3421 */     catch (CloneNotSupportedException ex) {
/* 3422 */       userClone = sessionUser;
/*      */     } 
/* 3424 */     userClone.SS_searchInitiated = false;
/* 3425 */     context.putSessionValue("ResetSearchVariables", userClone);
/*      */     
/* 3427 */     context.putSessionValue("ResetSelectionSortOrder", "true");
/*      */   }
/*      */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\SecurityHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */