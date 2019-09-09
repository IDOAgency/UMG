/*      */ package WEB-INF.classes.com.universal.milestone;
/*      */ 
/*      */ import com.techempower.ComponentLog;
/*      */ import com.techempower.gemini.Context;
/*      */ import com.techempower.gemini.Dispatcher;
/*      */ import com.techempower.gemini.FormCheckBox;
/*      */ import com.techempower.gemini.FormDropDownMenu;
/*      */ import com.techempower.gemini.FormHidden;
/*      */ import com.techempower.gemini.FormRadioButtonGroup;
/*      */ import com.techempower.gemini.GeminiApplication;
/*      */ import com.universal.milestone.Cache;
/*      */ import com.universal.milestone.Company;
/*      */ import com.universal.milestone.Division;
/*      */ import com.universal.milestone.Environment;
/*      */ import com.universal.milestone.Family;
/*      */ import com.universal.milestone.Form;
/*      */ import com.universal.milestone.JdbcConnector;
/*      */ import com.universal.milestone.Label;
/*      */ import com.universal.milestone.MilestoneFormDropDownMenu;
/*      */ import com.universal.milestone.MilestoneHelper;
/*      */ import com.universal.milestone.MilestoneHelper_2;
/*      */ import com.universal.milestone.MilestoneSecurity;
/*      */ import com.universal.milestone.PrefixCode;
/*      */ import com.universal.milestone.ReleaseCalendarHandler;
/*      */ import com.universal.milestone.ReleasingFamily;
/*      */ import com.universal.milestone.ReportConfigManager;
/*      */ import com.universal.milestone.SecureHandler;
/*      */ import com.universal.milestone.Selection;
/*      */ import com.universal.milestone.SelectionHandler;
/*      */ import com.universal.milestone.SelectionManager;
/*      */ import com.universal.milestone.SelectionStatus;
/*      */ import com.universal.milestone.User;
/*      */ import com.universal.milestone.UserManager;
/*      */ import java.util.Calendar;
/*      */ import java.util.GregorianCalendar;
/*      */ import java.util.HashMap;
/*      */ import java.util.Vector;
/*      */ 
/*      */ public class ReleaseCalendarHandler
/*      */   extends SecureHandler
/*      */ {
/*      */   public static final String COMPONENT_CODE = "hRCa";
/*      */   public GeminiApplication application;
/*      */   public ComponentLog log;
/*      */   public HashMap corpHashMap;
/*      */   public String[] calendarMonths;
/*      */   public String[] calendarDays;
/*      */   
/*      */   public ReleaseCalendarHandler(GeminiApplication application) {
/*   50 */     this.corpHashMap = null;
/*   51 */     this.calendarMonths = new String[12];
/*   52 */     this.calendarDays = new String[7];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   64 */     this.application = application;
/*   65 */     this.log = application.getLog("hRCa");
/*      */     
/*   67 */     this.calendarMonths[0] = "January";
/*   68 */     this.calendarMonths[1] = "February";
/*   69 */     this.calendarMonths[2] = "March";
/*   70 */     this.calendarMonths[3] = "April";
/*   71 */     this.calendarMonths[4] = "May";
/*   72 */     this.calendarMonths[5] = "June";
/*   73 */     this.calendarMonths[6] = "July";
/*   74 */     this.calendarMonths[7] = "August";
/*   75 */     this.calendarMonths[8] = "September";
/*   76 */     this.calendarMonths[9] = "October";
/*   77 */     this.calendarMonths[10] = "November";
/*   78 */     this.calendarMonths[11] = "December";
/*      */     
/*   80 */     this.calendarDays[0] = "Sunday";
/*   81 */     this.calendarDays[1] = "Monday";
/*   82 */     this.calendarDays[2] = "Tuesday";
/*   83 */     this.calendarDays[3] = "Wednesday";
/*   84 */     this.calendarDays[4] = "Thursday";
/*   85 */     this.calendarDays[5] = "Friday";
/*   86 */     this.calendarDays[6] = "Saturday";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   94 */   public String getDescription() { return "Release Calendar Handler"; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean acceptRequest(Dispatcher dispatcher, Context context, String command) {
/*  104 */     if (super.acceptRequest(dispatcher, context, command))
/*      */     {
/*  106 */       if (command.startsWith("release-calendar"))
/*      */       {
/*  108 */         return handleRequest(dispatcher, context, command);
/*      */       }
/*      */     }
/*  111 */     return false;
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
/*  123 */     if (command.equalsIgnoreCase("release-calendar-editor"))
/*      */     {
/*  125 */       editReleaseCalendar(dispatcher, context, command);
/*      */     }
/*  127 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean editReleaseCalendar(Dispatcher dispatcher, Context context, String command) {
/*  136 */     SelectionManager.getInstance().setSelectionNotepadUserDefaults(context);
/*      */ 
/*      */     
/*  139 */     Form form = buildForm(context);
/*  140 */     context.putDelivery("Form", form);
/*      */ 
/*      */     
/*  143 */     HashMap selections = getReleaseCalendarSelections(form, context);
/*  144 */     context.putDelivery("ReleaseCalendarSelections", selections);
/*      */     
/*  146 */     return context.includeJSP("release-calendar-editor.jsp");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Form buildForm(Context context) {
/*  155 */     User user = MilestoneSecurity.getUser(context);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  161 */     User userSrch = (User)context.getSessionValue("ResetSearchVariables");
/*  162 */     if (userSrch != null) {
/*  163 */       SelectionHandler.resetSearchVariables(user, userSrch, context);
/*      */     }
/*      */     
/*  166 */     if (user != null && !user.SS_searchInitiated) {
/*  167 */       UserManager.getInstance().setUserPreferenceReleaseCalendar(user);
/*      */     } else {
/*  169 */       user.RC_environment = user.SS_environmentSearch;
/*  170 */       user.RC_releasingFamily = user.SS_familySearch;
/*  171 */       user.RC_labelContact = user.SS_contactSearch;
/*  172 */       user.RC_productType = MilestoneHelper_2.convertProductTypeToReleaseCalendar(user.SS_productTypeSearch);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  178 */     Calendar calendar = new GregorianCalendar();
/*  179 */     int defaultMonth = calendar.get(2) + 1;
/*  180 */     int defaultYear = calendar.get(1);
/*      */     
/*  182 */     if (!user.RC_month.equals(""))
/*  183 */       defaultMonth = Integer.parseInt(user.RC_month); 
/*  184 */     if (context.getRequestValue("monthList") != null) {
/*  185 */       defaultMonth = Integer.parseInt(context.getRequestValue("monthList"));
/*      */     }
/*  187 */     if (!user.RC_year.equals(""))
/*  188 */       defaultYear = Integer.parseInt(user.RC_year); 
/*  189 */     if (context.getRequestValue("yearList") != null) {
/*  190 */       defaultYear = Integer.parseInt(context.getRequestValue("yearList"));
/*      */     }
/*      */ 
/*      */     
/*  194 */     String paramReleasingFamily = "-1";
/*  195 */     if (!user.RC_releasingFamily.equals(""))
/*  196 */       paramReleasingFamily = user.RC_releasingFamily; 
/*  197 */     if (context.getRequestValue("family") != null) {
/*      */       
/*  199 */       paramReleasingFamily = context.getRequestValue("family");
/*  200 */       user.SS_familySearch = paramReleasingFamily;
/*      */     } 
/*      */     
/*  203 */     String paramEnvironment = "-1";
/*  204 */     if (!user.RC_environment.equals(""))
/*  205 */       paramEnvironment = user.RC_environment; 
/*  206 */     if (context.getRequestValue("environment") != null) {
/*      */       
/*  208 */       paramEnvironment = context.getRequestValue("environment");
/*  209 */       user.SS_environmentSearch = paramEnvironment;
/*      */     } 
/*      */     
/*  212 */     String paramReleaseType = "All";
/*  213 */     if (!user.RC_releaseType.equals(""))
/*  214 */       paramReleaseType = user.RC_releaseType; 
/*  215 */     if (context.getRequestValue("releaseType") != null) {
/*  216 */       paramReleaseType = context.getRequestValue("releaseType");
/*      */     }
/*      */     
/*  219 */     String paramProductType = "All";
/*  220 */     if (!user.RC_productType.equals(""))
/*  221 */       paramProductType = user.RC_productType; 
/*  222 */     if (context.getRequestValue("productType") != null) {
/*      */       
/*  224 */       paramProductType = context.getRequestValue("productType");
/*  225 */       user.SS_productTypeSearch = MilestoneHelper_2.convertProductTypeToUserPref(paramProductType);
/*      */     } 
/*      */ 
/*      */     
/*  229 */     String paramContactList = "All";
/*  230 */     if (!user.RC_labelContact.equals(""))
/*  231 */       paramContactList = user.RC_labelContact; 
/*  232 */     if (context.getRequestValue("contact") != null) {
/*      */       
/*  234 */       paramContactList = context.getRequestValue("contact");
/*  235 */       user.SS_contactSearch = paramContactList;
/*      */     } 
/*      */     
/*  238 */     String paramConfigList = "All";
/*  239 */     if (!user.RC_formatType.equals(""))
/*  240 */       paramConfigList = user.RC_formatType; 
/*  241 */     if (context.getRequestValue("configurationList") != null) {
/*  242 */       paramConfigList = context.getRequestValue("configurationList");
/*      */     }
/*      */     
/*  245 */     boolean paramAllStatus = false;
/*  246 */     if (!user.RC_status.equals("") && user.RC_status.indexOf("All") > -1 && context.getRequestValue("configurationList") == null)
/*  247 */       paramAllStatus = true; 
/*  248 */     if (context.getRequestValue("AllStatus") != null) {
/*  249 */       paramAllStatus = true;
/*      */     }
/*  251 */     boolean paramActiveStatus = false;
/*  252 */     if (!user.RC_status.equals("") && user.RC_status.indexOf("Active") > -1 && context.getRequestValue("configurationList") == null) {
/*  253 */       paramActiveStatus = true;
/*  254 */     } else if (user.RC_status.equals("") && context.getRequestValue("configurationList") == null) {
/*  255 */       paramActiveStatus = true;
/*  256 */     }  if (context.getRequestValue("ActiveStatus") != null) {
/*  257 */       paramActiveStatus = true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  263 */     boolean paramClosedStatus = false;
/*  264 */     if (!user.RC_status.equals("") && user.RC_status.indexOf("Closed") > -1 && context.getRequestValue("configurationList") == null)
/*  265 */       paramClosedStatus = true; 
/*  266 */     if (context.getRequestValue("ClosedStatus") != null) {
/*  267 */       paramClosedStatus = true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  277 */     Form calendarForm = new Form(this.application, "reportForm", 
/*  278 */         this.application.getInfrastructure().getServletURL(), "POST");
/*      */ 
/*      */     
/*  281 */     calendarForm.addElement(new FormHidden("cmd", "release-calendar-editor", true));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  286 */     String monthList = "January,February,March,April,May,June,July,August,September,October,November,December";
/*  287 */     String monthIdList = "1,2,3,4,5,6,7,8,9,10,11,12";
/*  288 */     FormDropDownMenu MonthList = new FormDropDownMenu("monthList", String.valueOf(defaultMonth), monthIdList, monthList, false);
/*      */     
/*  290 */     MonthList.setTabIndex(1);
/*  291 */     calendarForm.addElement(MonthList);
/*      */     
/*  293 */     String yearList = "";
/*  294 */     String yearIdList = "";
/*  295 */     Calendar thisYear = GregorianCalendar.getInstance();
/*  296 */     for (int y = 1998; y < thisYear.get(1) + 2; y++) {
/*  297 */       yearList = String.valueOf(yearList) + "," + String.valueOf(y);
/*  298 */       yearIdList = String.valueOf(yearIdList) + "," + String.valueOf(y);
/*      */     } 
/*      */     
/*  301 */     FormDropDownMenu YearList = new FormDropDownMenu("yearList", String.valueOf(defaultYear), yearIdList, yearList, false);
/*      */     
/*  303 */     YearList.setTabIndex(2);
/*  304 */     calendarForm.addElement(YearList);
/*      */ 
/*      */ 
/*      */     
/*  308 */     Vector families = SelectionHandler.filterCSO(ReleasingFamily.getUserReleasingFamiliesVectorOfFamilies(context));
/*  309 */     FormDropDownMenu Family = MilestoneHelper.getCorporateStructureDropDown("family", families, paramReleasingFamily, false, true);
/*  310 */     Family.addFormEvent("onChange", "return(clickFamily(this))");
/*      */     
/*  312 */     Family.setTabIndex(3);
/*  313 */     calendarForm.addElement(Family);
/*      */     
/*  315 */     String hiddenFamilyIndex = "0";
/*  316 */     if (context.getRequest().getParameter("hiddenFamilyIndex") != null)
/*  317 */       hiddenFamilyIndex = context.getRequest().getParameter("hiddenFamilyIndex"); 
/*  318 */     calendarForm.addElement(new FormHidden("hiddenFamilyIndex", hiddenFamilyIndex, false));
/*      */ 
/*      */     
/*  321 */     Vector environments = MilestoneHelper.getUserEnvironments(context);
/*  322 */     Vector companies = MilestoneHelper.getUserCompanies(context);
/*      */ 
/*      */ 
/*      */     
/*  326 */     environments = SelectionHandler.filterSelectionEnvironments(companies);
/*      */ 
/*      */     
/*  329 */     environments = MilestoneHelper.removeUnusedCSO(environments, context, -1);
/*      */ 
/*      */     
/*  332 */     companies = MilestoneHelper.removeUnusedCSO(companies, context, -1);
/*      */ 
/*      */     
/*  335 */     FormDropDownMenu envMenu = MilestoneHelper.getCorporateStructureDropDown("environment", environments, paramEnvironment, false, true);
/*  336 */     envMenu.addFormEvent("onChange", "return(clickEnvironment(this))");
/*      */     
/*  338 */     envMenu.setTabIndex(4);
/*  339 */     calendarForm.addElement(envMenu);
/*  340 */     String hiddenEnvironmentIndex = "0";
/*  341 */     if (context.getRequest().getParameter("hiddenEnvironmentIndex") != null)
/*  342 */       hiddenEnvironmentIndex = context.getRequest().getParameter("hiddenEnvironmentIndex"); 
/*  343 */     calendarForm.addElement(new FormHidden("hiddenEnvironmentIndex", hiddenEnvironmentIndex, false));
/*      */ 
/*      */     
/*  346 */     FormRadioButtonGroup ReleaseType = new FormRadioButtonGroup("releaseType", paramReleaseType, "Commercial, Promotional, All", false);
/*  347 */     ReleaseType.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/*  348 */     ReleaseType.setTabIndex(11);
/*  349 */     calendarForm.addElement(ReleaseType);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  354 */     if (user == null || user.getAdministrator() != 1) {
/*  355 */       paramProductType = "Physical";
/*      */     }
/*      */     
/*  358 */     FormRadioButtonGroup ProductType = new FormRadioButtonGroup("productType", paramProductType, "Digital, Physical, All", false);
/*  359 */     ProductType.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/*  360 */     ProductType.setTabIndex(10);
/*  361 */     calendarForm.addElement(ProductType);
/*      */ 
/*      */     
/*  364 */     Vector contactVector = ReportConfigManager.getLabelContacts();
/*  365 */     String contactList = "All,";
/*  366 */     String idList = "0,";
/*      */     
/*  368 */     if (contactVector != null) {
/*      */       
/*  370 */       for (int i = 0; i < contactVector.size(); i++)
/*      */       {
/*  372 */         User userContact = (User)contactVector.get(i);
/*  373 */         if (i < contactVector.size() - 1)
/*      */         {
/*  375 */           if (userContact != null)
/*      */           {
/*  377 */             contactList = String.valueOf(contactList) + userContact.getName() + ",";
/*  378 */             idList = String.valueOf(idList) + userContact.getUserId() + ",";
/*      */           
/*      */           }
/*      */         
/*      */         }
/*  383 */         else if (userContact != null)
/*      */         {
/*  385 */           contactList = String.valueOf(contactList) + userContact.getName();
/*  386 */           idList = String.valueOf(idList) + userContact.getUserId();
/*      */         }
/*      */       
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  393 */       contactList = "All";
/*  394 */       idList = "0";
/*      */     } 
/*      */     
/*  397 */     FormDropDownMenu ContactList = new FormDropDownMenu("contact", paramContactList, idList, contactList, false);
/*  398 */     ContactList.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/*  399 */     ContactList.setTabIndex(5);
/*  400 */     calendarForm.addElement(ContactList);
/*      */ 
/*      */ 
/*      */     
/*  404 */     MilestoneFormDropDownMenu ConfigList = new MilestoneFormDropDownMenu(MilestoneHelper.getSelectionConfigurationDropDown("configurationList", "", false));
/*  405 */     ConfigList.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/*      */     
/*  407 */     String[] values = ConfigList.getValueList();
/*  408 */     String[] menuText = ConfigList.getMenuTextList();
/*      */     
/*  410 */     values[0] = "All";
/*  411 */     menuText[0] = "All";
/*  412 */     ConfigList.setValueList(values);
/*  413 */     ConfigList.setMenuTextList(menuText);
/*  414 */     ConfigList.setValue(paramConfigList);
/*  415 */     ConfigList.setTabIndex(6);
/*      */ 
/*      */     
/*  418 */     calendarForm.addElement(ConfigList);
/*      */ 
/*      */ 
/*      */     
/*  422 */     FormCheckBox AllStatus = new FormCheckBox("AllStatus", "All", false, false);
/*  423 */     AllStatus.setChecked(paramAllStatus);
/*  424 */     AllStatus.addFormEvent("onClick", "StatusSelected(this);");
/*  425 */     AllStatus.setTabIndex(7);
/*  426 */     AllStatus.setId("AllStatus");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  431 */     calendarForm.addElement(AllStatus);
/*      */     
/*  433 */     FormCheckBox ActiveStatus = new FormCheckBox("ActiveStatus", "Active", false, true);
/*  434 */     ActiveStatus.setChecked(paramActiveStatus);
/*  435 */     ActiveStatus.addFormEvent("onClick", "StatusSelected(this);");
/*  436 */     ActiveStatus.setTabIndex(8);
/*  437 */     ActiveStatus.setId("ActiveStatus");
/*  438 */     calendarForm.addElement(ActiveStatus);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  448 */     FormCheckBox ClosedStatus = new FormCheckBox("ClosedStatus", "Closed", false, false);
/*  449 */     ClosedStatus.setChecked(paramClosedStatus);
/*  450 */     ClosedStatus.addFormEvent("onClick", "StatusSelected(this);");
/*  451 */     ClosedStatus.setTabIndex(9);
/*  452 */     ClosedStatus.setId("ClosedStatus");
/*  453 */     calendarForm.addElement(ClosedStatus);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  464 */     user.RC_environment = paramEnvironment;
/*  465 */     user.RC_formatType = paramConfigList;
/*  466 */     user.RC_labelContact = paramContactList;
/*  467 */     user.RC_month = String.valueOf(defaultMonth);
/*  468 */     user.RC_productType = paramProductType;
/*  469 */     user.RC_releaseType = paramReleaseType;
/*  470 */     user.RC_releasingFamily = paramReleasingFamily;
/*  471 */     user.RC_status = "";
/*  472 */     if (paramAllStatus)
/*  473 */       user.RC_status = String.valueOf(user.RC_status) + "All"; 
/*  474 */     if (paramActiveStatus)
/*  475 */       user.RC_status = String.valueOf(user.RC_status) + "Active"; 
/*  476 */     if (paramClosedStatus)
/*  477 */       user.RC_status = String.valueOf(user.RC_status) + "Closed"; 
/*  478 */     user.RC_year = String.valueOf(defaultYear);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  483 */     context.putDelivery("selectionArrays", ReleasingFamily.getJavaScriptCorporateArrayReleasingFamilySearch(context));
/*  484 */     return calendarForm;
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
/*      */   private String getJavaScriptCorporateArray(Context context) {
/*  498 */     StringBuffer result = new StringBuffer(100);
/*  499 */     String str = "";
/*  500 */     String value = new String();
/*      */ 
/*      */     
/*  503 */     this.corpHashMap = MilestoneHelper.buildActiveCorporateStructureHashMap();
/*      */ 
/*      */     
/*  506 */     User user = (User)context.getSessionValue("user");
/*  507 */     Vector vUserCompanies = SelectionHandler.filterSelectionActiveCompanies(user.getUserId(), MilestoneHelper.getUserCompanies(context));
/*  508 */     Vector vUserEnvironments = SelectionHandler.filterSelectionEnvironments(vUserCompanies);
/*      */ 
/*      */     
/*  511 */     result.append("\n");
/*  512 */     result.append("var familyArray = new Array();\n");
/*  513 */     result.append("var environmentArray = new Array();\n");
/*  514 */     result.append("var companyArray = new Array();\n");
/*  515 */     int arrayIndex = 0;
/*      */ 
/*      */     
/*  518 */     result.append("familyArray[0] = new Array( 0, 'All');\n");
/*  519 */     result.append("environmentArray[0] = new Array( 0, 'All');\n");
/*      */     
/*  521 */     Vector vUserFamilies = SelectionHandler.filterCSO(MilestoneHelper.getNonSecureActiveUserFamilies(context));
/*      */     
/*  523 */     for (int l = 0; l < vUserFamilies.size(); l++) {
/*      */       
/*  525 */       Family family = (Family)vUserFamilies.elementAt(l);
/*  526 */       int structureId = family.getStructureID();
/*      */ 
/*      */ 
/*      */       
/*  530 */       boolean familyVal = true;
/*  531 */       if (this.corpHashMap.containsKey(new Integer(structureId))) {
/*  532 */         familyVal = false;
/*      */       }
/*      */       
/*  535 */       if (family != null && familyVal) {
/*      */         
/*  537 */         result.append("familyArray[");
/*  538 */         result.append(family.getStructureID());
/*  539 */         result.append("] = new Array(");
/*      */         
/*  541 */         boolean foundZeroth = false;
/*      */ 
/*      */         
/*  544 */         Vector environmentVector = new Vector();
/*  545 */         Vector environments = getUserEnvironmentsFromFamily(family, context);
/*      */         
/*  547 */         if (environments != null) {
/*      */           
/*  549 */           result.append(" 0, 'All',");
/*  550 */           for (int j = 0; j < environments.size(); j++) {
/*      */             
/*  552 */             Environment environment = (Environment)environments.elementAt(j);
/*  553 */             int structureIdc = environment.getStructureID();
/*      */             
/*  555 */             boolean boolVal = true;
/*  556 */             if (this.corpHashMap.containsKey(new Integer(structureIdc))) {
/*  557 */               boolVal = false;
/*      */             }
/*  559 */             if (environment.getParentID() == family.getStructureID() && boolVal) {
/*      */               
/*  561 */               if (foundZeroth) {
/*  562 */                 result.append(',');
/*      */               }
/*  564 */               result.append(' ');
/*  565 */               result.append(environment.getStructureID());
/*  566 */               result.append(", '");
/*  567 */               result.append(MilestoneHelper.urlEncode(environment.getName()));
/*  568 */               result.append('\'');
/*  569 */               foundZeroth = true;
/*  570 */               environmentVector.addElement(environment);
/*      */             } 
/*      */           } 
/*  573 */           if (foundZeroth) {
/*      */             
/*  575 */             result.append(");\n");
/*      */           }
/*      */           else {
/*      */             
/*  579 */             result.append(" 0, 'All');\n");
/*      */           } 
/*      */           
/*  582 */           for (int k = 0; k < environmentVector.size(); k++) {
/*  583 */             Environment environmentNode = (Environment)environmentVector.elementAt(k);
/*  584 */             result.append("environmentArray[");
/*  585 */             result.append(environmentNode.getStructureID());
/*  586 */             result.append("] = new Array(");
/*  587 */             Vector companyVector = new Vector();
/*  588 */             Vector companies = environmentNode.getChildren();
/*      */             
/*  590 */             if (companies != null) {
/*  591 */               result.append(" 0, 'All',");
/*      */               
/*  593 */               boolean foundZeroth2 = false;
/*      */               
/*  595 */               for (int m = 0; m < companies.size(); m++) {
/*      */                 
/*  597 */                 Company company = (Company)companies.elementAt(m);
/*  598 */                 int structureIdc = company.getStructureID();
/*      */ 
/*      */                 
/*  601 */                 boolean boolVal = true;
/*  602 */                 if (this.corpHashMap.containsKey(new Integer(structureIdc))) {
/*  603 */                   boolVal = false;
/*      */                 }
/*      */                 
/*  606 */                 if (company.getParentID() == environmentNode.getStructureID() && boolVal) {
/*      */                   
/*  608 */                   if (foundZeroth2)
/*  609 */                     result.append(','); 
/*  610 */                   result.append(' ');
/*  611 */                   result.append(company.getStructureID());
/*  612 */                   result.append(", '");
/*  613 */                   result.append(MilestoneHelper.urlEncode(company.getName()));
/*  614 */                   result.append('\'');
/*  615 */                   foundZeroth2 = true;
/*  616 */                   companyVector.addElement(company);
/*      */                 } 
/*      */               } 
/*      */               
/*  620 */               if (foundZeroth2) {
/*      */                 
/*  622 */                 result.append(");\n");
/*      */               }
/*      */               else {
/*      */                 
/*  626 */                 result.append(" 0, 'All');\n");
/*      */               } 
/*      */               
/*  629 */               for (int n = 0; n < companyVector.size(); n++) {
/*      */                 
/*  631 */                 Company companyNode = (Company)companyVector.elementAt(n);
/*  632 */                 result.append("companyArray[");
/*  633 */                 result.append(companyNode.getStructureID());
/*  634 */                 result.append("] = new Array(");
/*      */                 
/*  636 */                 Vector divisions = companyNode.getChildren();
/*      */                 
/*  638 */                 boolean foundSecond2 = false;
/*      */                 
/*  640 */                 result.append(" 0, 'All',");
/*      */                 
/*  642 */                 for (int x = 0; x < divisions.size(); x++) {
/*      */                   
/*  644 */                   Division division = (Division)divisions.elementAt(x);
/*  645 */                   int structureIds = division.getStructureID();
/*      */ 
/*      */ 
/*      */                   
/*  649 */                   boolean boolRes = true;
/*  650 */                   if (this.corpHashMap.containsKey(new Integer(structureIds))) {
/*  651 */                     boolRes = false;
/*      */                   }
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  657 */                   if (division != null && boolRes) {
/*      */ 
/*      */                     
/*  660 */                     Vector labels = division.getChildren();
/*      */                     
/*  662 */                     for (int y = 0; y < labels.size(); y++) {
/*      */                       
/*  664 */                       Label labelNode = (Label)labels.get(y);
/*  665 */                       int structureIdl = labelNode.getStructureID();
/*      */ 
/*      */ 
/*      */                       
/*  669 */                       boolean boolVal = true;
/*  670 */                       if (this.corpHashMap.containsKey(new Integer(structureIdl))) {
/*  671 */                         boolVal = false;
/*      */                       }
/*      */                       
/*  674 */                       if (labelNode.getParentID() == division.getStructureID() && boolVal) {
/*      */ 
/*      */                         
/*  677 */                         if (foundSecond2)
/*  678 */                           result.append(','); 
/*  679 */                         result.append(' ');
/*  680 */                         result.append(labelNode.getStructureID());
/*  681 */                         result.append(", '");
/*  682 */                         result.append(MilestoneHelper.urlEncode(labelNode.getName()));
/*  683 */                         result.append('\'');
/*  684 */                         foundSecond2 = true;
/*      */                       } 
/*      */                     } 
/*      */                   } 
/*      */                 } 
/*      */ 
/*      */ 
/*      */                 
/*  692 */                 if (foundSecond2) {
/*      */                   
/*  694 */                   result.append(");\n");
/*      */                 }
/*      */                 else {
/*      */                   
/*  698 */                   result.append(" 0, 'All');\n");
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  707 */     this.corpHashMap = null;
/*  708 */     return result.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Vector getUserEnvironmentsFromFamily(Family family, Context context) {
/*  715 */     Vector userEnvironments = MilestoneHelper.getUserEnvironments(context);
/*  716 */     Vector result = new Vector();
/*      */     
/*  718 */     if (family != null) {
/*      */ 
/*      */       
/*  721 */       Vector familyEnvironments = family.getChildren();
/*      */       
/*  723 */       if (familyEnvironments != null)
/*      */       {
/*      */         
/*  726 */         for (int i = 0; i < familyEnvironments.size(); i++) {
/*      */ 
/*      */           
/*  729 */           Environment familyEnvironment = (Environment)familyEnvironments.get(i);
/*      */ 
/*      */           
/*  732 */           for (int j = 0; j < userEnvironments.size(); j++) {
/*      */             
/*  734 */             Environment userEnvironment = (Environment)userEnvironments.get(j);
/*  735 */             int structureId = userEnvironment.getStructureID();
/*      */             
/*  737 */             boolean resultStructure = true;
/*  738 */             if (this.corpHashMap.containsKey(new Integer(structureId))) {
/*  739 */               resultStructure = false;
/*      */             }
/*      */             
/*  742 */             if (userEnvironment.getStructureID() == familyEnvironment.getStructureID() && resultStructure) {
/*  743 */               result.add(familyEnvironment);
/*      */             }
/*      */           } 
/*      */         } 
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  751 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   private void getInitialDates(Calendar currDate, HashMap selectionsPerMonth1) {
/*  756 */     String txtDateHeader = "";
/*      */     
/*  758 */     int originalMonth = currDate.get(2);
/*  759 */     while (originalMonth == currDate.get(2)) {
/*  760 */       if (this.calendarDays[currDate.get(7) - 1].equalsIgnoreCase("Monday") || 
/*  761 */         this.calendarDays[currDate.get(7) - 1].equalsIgnoreCase("Tuesday")) {
/*  762 */         txtDateHeader = String.valueOf(String.valueOf(currDate.get(5))) + "-" + this.calendarDays[currDate.get(7) - 1];
/*  763 */         Vector workingSelections = new Vector();
/*  764 */         selectionsPerMonth1.put(txtDateHeader, workingSelections);
/*      */       } 
/*      */       
/*  767 */       currDate.add(5, 1);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private HashMap getReleaseCalendarSelections(Form form, Context context) {
/*  777 */     int monthpassedin = form.getElement("monthList").getIntegerValue();
/*  778 */     int yearpassedin = form.getElement("yearList").getIntegerValue();
/*      */     
/*  780 */     String strDate = String.valueOf(String.valueOf(monthpassedin)) + "/1/" + String.valueOf(yearpassedin);
/*      */     
/*  782 */     StringBuffer releaseHeaderQuery = new StringBuffer();
/*      */     
/*  784 */     releaseHeaderQuery.append("select release_id, title, artist, selection_no, release_date = case digital_flag when 0 then street_date when 1 then digital_street_date end,digital_flag, street_date, digital_street_date, configuration, sub_configuration, upc, prefix, status from vi_release_header where ((street_date between '" + 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  793 */         strDate + "'  and dateadd(month,3,'" + strDate + "') and digital_flag = 0) or " + 
/*  794 */         "       (digital_street_date between '" + strDate + "'  and dateadd(month,3,'" + strDate + "') and digital_flag = 1)) ");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  805 */     SelectionManager.addReleasingFamilyLabelFamilySelect("family", context, releaseHeaderQuery, form);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  810 */     FormDropDownMenu envDD = (FormDropDownMenu)form.getElement("environment");
/*  811 */     if (!envDD.getStringValue().equals("0") && !envDD.getStringValue().equals("-1")) {
/*  812 */       releaseHeaderQuery.append(" and ( environment_id = " + envDD.getStringValue() + " ) ");
/*      */     } else {
/*  814 */       releaseHeaderQuery.append(" and ( environment_id in (");
/*  815 */       for (int f = 0; f < envDD.getValueList().length; f++) {
/*  816 */         if (f == 0) {
/*  817 */           releaseHeaderQuery.append(envDD.getValueList()[f]);
/*      */         } else {
/*  819 */           releaseHeaderQuery.append("," + envDD.getValueList()[f]);
/*      */         } 
/*  821 */       }  releaseHeaderQuery.append(" ))");
/*      */     } 
/*      */ 
/*      */     
/*  825 */     FormRadioButtonGroup releaseTypeRB = (FormRadioButtonGroup)form.getElement("releaseType");
/*  826 */     if (!releaseTypeRB.getStringValue().equalsIgnoreCase("All")) {
/*  827 */       if (releaseTypeRB.getStringValue().equalsIgnoreCase("Commercial")) {
/*  828 */         releaseHeaderQuery.append(" and ( release_type = 'CO' ) ");
/*      */       } else {
/*  830 */         releaseHeaderQuery.append(" and ( release_type = 'PR' ) ");
/*      */       } 
/*      */     }
/*      */     
/*  834 */     FormRadioButtonGroup productTypeRB = (FormRadioButtonGroup)form.getElement("productType");
/*  835 */     if (!productTypeRB.getStringValue().equalsIgnoreCase("All")) {
/*  836 */       if (productTypeRB.getStringValue().equalsIgnoreCase("Digital")) {
/*  837 */         releaseHeaderQuery.append(" and ( digital_flag = 1 ) ");
/*      */       } else {
/*  839 */         releaseHeaderQuery.append(" and ( digital_flag = 0 ) ");
/*      */       } 
/*      */     }
/*      */     
/*  843 */     FormDropDownMenu labelContactDD = (FormDropDownMenu)form.getElement("contact");
/*  844 */     if (!labelContactDD.getStringValue().equalsIgnoreCase("All") && !labelContactDD.getStringValue().equalsIgnoreCase("0")) {
/*  845 */       releaseHeaderQuery.append(" and ( contact_id = " + labelContactDD.getStringValue() + " ) ");
/*      */     }
/*      */     
/*  848 */     FormDropDownMenu formatTypeDD = (FormDropDownMenu)form.getElement("configurationList");
/*  849 */     if (!formatTypeDD.getStringValue().equalsIgnoreCase("All")) {
/*  850 */       releaseHeaderQuery.append(" and ( configuration = '" + formatTypeDD.getStringValue() + "' ) ");
/*      */     }
/*      */     
/*  853 */     FormCheckBox allStatusCB = (FormCheckBox)form.getElement("AllStatus");
/*  854 */     if (allStatusCB == null || !allStatusCB.isChecked())
/*      */     
/*  856 */     { FormCheckBox ActiveStatusCB = (FormCheckBox)form.getElement("ActiveStatus");
/*  857 */       boolean isTrue = false;
/*  858 */       if (ActiveStatusCB.isChecked()) {
/*  859 */         releaseHeaderQuery.append(" and ( status = 'Active' ");
/*  860 */         isTrue = true;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  865 */       FormCheckBox ClosedStatusCB = (FormCheckBox)form.getElement("ClosedStatus");
/*  866 */       if (ClosedStatusCB.isChecked()) {
/*  867 */         if (isTrue) {
/*  868 */           releaseHeaderQuery.append(" or status = 'Closed' ");
/*      */         } else {
/*  870 */           releaseHeaderQuery.append(" and ( status = 'Closed' ");
/*      */         } 
/*  872 */         isTrue = true;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  877 */       if (isTrue) {
/*  878 */         releaseHeaderQuery.append(" ) ");
/*      */       } else {
/*  880 */         releaseHeaderQuery.append(" and (status = 'Active' OR status = 'Closed') ");
/*      */       }  }
/*  882 */     else { releaseHeaderQuery.append(" and (status = 'Active' OR status = 'Closed') "); }
/*      */ 
/*      */     
/*  885 */     releaseHeaderQuery.append("order by release_date ASC,artist,title");
/*      */ 
/*      */ 
/*      */     
/*  889 */     JdbcConnector connector = MilestoneHelper.getConnector(releaseHeaderQuery.toString());
/*  890 */     connector.runQuery();
/*      */     
/*  892 */     Selection selection = null;
/*  893 */     HashMap AllSelections = new HashMap();
/*  894 */     HashMap selectionsPerMonth1 = new HashMap();
/*  895 */     HashMap selectionsPerMonth2 = new HashMap();
/*  896 */     HashMap selectionsPerMonth3 = new HashMap();
/*      */     
/*  898 */     Calendar currDate = null;
/*  899 */     String txtDateHeader = "";
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  904 */     currDate = new GregorianCalendar(yearpassedin, monthpassedin - 1, 1);
/*      */     
/*  906 */     getInitialDates(currDate, selectionsPerMonth1);
/*      */     
/*  908 */     getInitialDates(currDate, selectionsPerMonth2);
/*      */     
/*  910 */     getInitialDates(currDate, selectionsPerMonth3);
/*      */     
/*  912 */     while (connector.more()) {
/*      */       
/*  914 */       selection = new Selection();
/*  915 */       selection.setSelectionID(connector.getIntegerField("release_id"));
/*      */       
/*  917 */       String selectionNo = "";
/*  918 */       if (connector.getFieldByName("selection_no") != null)
/*  919 */         selectionNo = connector.getFieldByName("selection_no"); 
/*  920 */       selection.setSelectionNo(selectionNo);
/*      */       
/*  922 */       selection.setPrefixID((PrefixCode)SelectionManager.getLookupObject(connector.getField("prefix"), Cache.getPrefixCodes()));
/*      */       
/*  924 */       selection.setTitle(connector.getField("title", ""));
/*  925 */       selection.setArtist(connector.getField("artist", ""));
/*      */       
/*  927 */       String streetDateString = connector.getFieldByName("street_date");
/*  928 */       if (streetDateString != null) {
/*  929 */         selection.setStreetDate(MilestoneHelper.getDatabaseDate(streetDateString));
/*      */       }
/*      */       
/*  932 */       String digitalRlsString = connector.getFieldByName("digital_street_date");
/*  933 */       if (digitalRlsString != null) {
/*  934 */         selection.setDigitalRlsDateString(digitalRlsString);
/*  935 */         selection.setDigitalRlsDate(MilestoneHelper.getDatabaseDate(digitalRlsString));
/*      */       } 
/*      */       
/*  938 */       selection.setIsDigital(connector.getBoolean("digital_flag"));
/*      */       
/*  940 */       selection.setSelectionConfig(
/*  941 */           SelectionManager.getSelectionConfigObject(connector.getField("configuration"), Cache.getSelectionConfigs()));
/*      */       
/*  943 */       selection.setSelectionSubConfig(
/*  944 */           SelectionManager.getSelectionSubConfigObject(connector.getField("sub_configuration"), selection.getSelectionConfig()));
/*      */       
/*  946 */       selection.setUpc(connector.getField("upc", ""));
/*      */ 
/*      */ 
/*      */       
/*  950 */       selection.setSelectionStatus(
/*  951 */           (SelectionStatus)MilestoneHelper.getLookupObject(connector.getField("status", ""), Cache.getSelectionStatusList()));
/*      */       
/*  953 */       currDate = MilestoneHelper.getDatabaseDate(connector.getField("release_date"));
/*  954 */       txtDateHeader = String.valueOf(String.valueOf(currDate.get(5))) + "-" + this.calendarDays[currDate.get(7) - 1];
/*      */       
/*  956 */       if (currDate.get(2) == monthpassedin - 1) {
/*  957 */         Vector workingSelections; if (selectionsPerMonth1.containsKey(txtDateHeader)) {
/*  958 */           workingSelections = (Vector)selectionsPerMonth1.get(txtDateHeader);
/*      */         }
/*      */         else {
/*      */           
/*  962 */           workingSelections = new Vector();
/*      */         } 
/*  964 */         workingSelections.add(selection);
/*  965 */         selectionsPerMonth1.put(txtDateHeader, workingSelections);
/*      */       
/*      */       }
/*  968 */       else if ((currDate.get(1) == yearpassedin && currDate.get(2) == monthpassedin) || (
/*  969 */         currDate.get(1) == yearpassedin + 1 && currDate.get(2) == 0 && monthpassedin == 12)) {
/*  970 */         Vector workingSelections; if (selectionsPerMonth2.containsKey(txtDateHeader)) {
/*  971 */           workingSelections = (Vector)selectionsPerMonth2.get(txtDateHeader);
/*      */         } else {
/*  973 */           workingSelections = new Vector();
/*      */         } 
/*  975 */         workingSelections.add(selection);
/*  976 */         selectionsPerMonth2.put(txtDateHeader, workingSelections);
/*      */       
/*      */       }
/*  979 */       else if ((currDate.get(1) == yearpassedin && currDate.get(2) == monthpassedin + 1) || (
/*  980 */         currDate.get(1) == yearpassedin + 1 && currDate.get(2) == 0 && monthpassedin == 11) || (
/*  981 */         currDate.get(1) == yearpassedin + 1 && currDate.get(2) == 1 && monthpassedin == 12)) {
/*  982 */         Vector workingSelections; if (selectionsPerMonth3.containsKey(txtDateHeader)) {
/*  983 */           workingSelections = (Vector)selectionsPerMonth3.get(txtDateHeader);
/*      */         } else {
/*  985 */           workingSelections = new Vector();
/*      */         } 
/*  987 */         workingSelections.add(selection);
/*  988 */         selectionsPerMonth3.put(txtDateHeader, workingSelections);
/*      */       } 
/*      */ 
/*      */       
/*  992 */       connector.next();
/*      */     } 
/*      */ 
/*      */     
/*  996 */     connector.close();
/*      */     
/*  998 */     int intForArray1 = monthpassedin - 1;
/*  999 */     int intForArray2 = monthpassedin;
/* 1000 */     int intForArray3 = monthpassedin + 1;
/* 1001 */     int intYear1 = yearpassedin;
/* 1002 */     int intYear2 = yearpassedin;
/* 1003 */     int intYear3 = yearpassedin;
/*      */     
/* 1005 */     if (monthpassedin > 10) {
/* 1006 */       switch (monthpassedin) { case 11:
/* 1007 */           intForArray1 = 10;
/* 1008 */           intForArray2 = 11;
/* 1009 */           intForArray3 = 0;
/* 1010 */           intYear3 = yearpassedin + 1; break;
/*      */         case 12:
/* 1012 */           intForArray1 = 11;
/* 1013 */           intForArray2 = 0;
/* 1014 */           intForArray3 = 1;
/* 1015 */           intYear2 = yearpassedin + 1;
/* 1016 */           intYear3 = yearpassedin + 1;
/*      */           break; }
/*      */ 
/*      */     
/*      */     }
/* 1021 */     String monthHeader = String.valueOf(String.valueOf(this.calendarMonths[intForArray1])) + "-" + String.valueOf(intYear1);
/* 1022 */     AllSelections.put(monthHeader, selectionsPerMonth1);
/* 1023 */     monthHeader = String.valueOf(String.valueOf(this.calendarMonths[intForArray2])) + "-" + String.valueOf(intYear2);
/* 1024 */     AllSelections.put(monthHeader, selectionsPerMonth2);
/* 1025 */     monthHeader = String.valueOf(String.valueOf(this.calendarMonths[intForArray3])) + "-" + String.valueOf(intYear3);
/* 1026 */     AllSelections.put(monthHeader, selectionsPerMonth3);
/*      */     
/* 1028 */     return AllSelections;
/*      */   }
/*      */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\ReleaseCalendarHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */