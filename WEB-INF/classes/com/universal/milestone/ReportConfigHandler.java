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
/*      */ import com.universal.milestone.Form;
/*      */ import com.universal.milestone.MilestoneConstants;
/*      */ import com.universal.milestone.MilestoneHelper;
/*      */ import com.universal.milestone.MilestoneHelper_2;
/*      */ import com.universal.milestone.MilestoneMessage;
/*      */ import com.universal.milestone.MilestoneSecurity;
/*      */ import com.universal.milestone.Notepad;
/*      */ import com.universal.milestone.Report;
/*      */ import com.universal.milestone.ReportConfigHandler;
/*      */ import com.universal.milestone.ReportConfigManager;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ReportConfigHandler
/*      */   extends SecureHandler
/*      */ {
/*      */   public static final String COMPONENT_CODE = "hRpC";
/*      */   public GeminiApplication application;
/*      */   public ComponentLog log;
/*      */   
/*      */   public ReportConfigHandler(GeminiApplication application) {
/*   69 */     this.application = application;
/*   70 */     this.log = application.getLog("hRpC");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   78 */   public String getDescription() { return "Report Config Handler"; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean acceptRequest(Dispatcher dispatcher, Context context, String command) {
/*   88 */     if (super.acceptRequest(dispatcher, context, command))
/*      */     {
/*   90 */       if (command.startsWith("report-config"))
/*      */       {
/*   92 */         return handleRequest(dispatcher, context, command);
/*      */       }
/*      */     }
/*   95 */     return false;
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
/*  107 */     if (command.equalsIgnoreCase("report-config-search"))
/*      */     {
/*  109 */       search(dispatcher, context, command);
/*      */     }
/*  111 */     if (command.equalsIgnoreCase("report-config-sort")) {
/*      */       
/*  113 */       sort(dispatcher, context);
/*      */     }
/*  115 */     else if (command.equalsIgnoreCase("report-config-editor")) {
/*      */       
/*  117 */       edit(context);
/*      */     }
/*  119 */     else if (command.equalsIgnoreCase("report-config-edit-save")) {
/*      */       
/*  121 */       saveEdit(context);
/*      */     }
/*  123 */     else if (command.equalsIgnoreCase("report-config-edit-save-new")) {
/*      */       
/*  125 */       saveNew(context);
/*      */     }
/*  127 */     else if (command.equalsIgnoreCase("report-config-edit-delete")) {
/*      */       
/*  129 */       delete(context);
/*      */     }
/*  131 */     else if (command.equalsIgnoreCase("report-config-edit-new")) {
/*      */       
/*  133 */       editNew(context);
/*      */     } 
/*  135 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean search(Dispatcher dispatcher, Context context, String command) {
/*  145 */     Notepad notepad = MilestoneHelper.getNotepadFromSession(17, context);
/*  146 */     notepad.setAllContents(null);
/*  147 */     notepad.setSelected(null);
/*      */     
/*  149 */     ReportConfigManager.getInstance().setNotepadQuery(context, MilestoneSecurity.getUser(context).getUserId(), notepad);
/*  150 */     dispatcher.redispatch(context, "report-config-editor");
/*      */     
/*  152 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean sort(Dispatcher dispatcher, Context context) {
/*  162 */     int sort = Integer.parseInt(context.getParameter("OrderBy"));
/*  163 */     Notepad notepad = MilestoneHelper.getNotepadFromSession(17, context);
/*      */     
/*  165 */     if (notepad.getSearchQuery() == null || notepad.getSearchQuery().equals("")) {
/*  166 */       ReportConfigManager.getInstance(); notepad.setSearchQuery("SELECT DISTINCT report_id, report_name, file_name, description, report_status, report_owner FROM vi_Report_Config");
/*      */     } 
/*  168 */     notepad.setOrderBy(" ORDER BY " + MilestoneConstants.SORT_REPORT_CONFIG[sort]);
/*      */ 
/*      */     
/*  171 */     notepad.setAllContents(null);
/*  172 */     notepad = getReportConfigNotepad(context, MilestoneSecurity.getUser(context).getUserId());
/*  173 */     notepad.setSelected(notepad.getSelected());
/*      */     
/*  175 */     dispatcher.redispatch(context, "report-config-editor");
/*      */     
/*  177 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Notepad getReportConfigNotepad(Context context, int userId) {
/*  186 */     Vector contents = new Vector();
/*      */     
/*  188 */     if (MilestoneHelper.getNotepadFromSession(17, context) != null) {
/*      */       
/*  190 */       Notepad notepad = MilestoneHelper.getNotepadFromSession(17, context);
/*      */       
/*  192 */       if (notepad.getAllContents() == null) {
/*      */         
/*  194 */         contents = ReportConfigManager.getInstance().getReportConfigNotepadList(notepad);
/*  195 */         notepad.setAllContents(contents);
/*      */       } 
/*      */       
/*  198 */       return notepad;
/*      */     } 
/*      */ 
/*      */     
/*  202 */     String[] columnNames = { "Name", "File Name" };
/*  203 */     contents = ReportConfigManager.getInstance().getReportConfigNotepadList(null);
/*  204 */     return new Notepad(contents, 0, 15, "Reports", 17, columnNames);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean edit(Context context) {
/*  212 */     User user = MilestoneSecurity.getUser(context);
/*      */     
/*  214 */     Notepad notepad = getReportConfigNotepad(context, user.getUserId());
/*  215 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */     
/*  217 */     Report reportConfig = MilestoneHelper.getScreenReportConfig(context);
/*      */     
/*  219 */     if (reportConfig != null) {
/*      */ 
/*      */       
/*  222 */       Form reportConfigForm = buildForm(context, reportConfig);
/*      */       
/*  224 */       context.putDelivery("Form", reportConfigForm);
/*      */       
/*  226 */       return context.includeJSP("report-config-editor.jsp");
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  231 */     return goToBlank(context);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean saveEdit(Context context) {
/*  239 */     User user = MilestoneSecurity.getUser(context);
/*      */ 
/*      */     
/*  242 */     Notepad notepad = getReportConfigNotepad(context, user.getUserId());
/*  243 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */     
/*  245 */     Report reportConfig = MilestoneHelper.getScreenReportConfig(context);
/*      */     
/*  247 */     Form form = buildForm(context, reportConfig);
/*      */ 
/*      */     
/*  250 */     if (ReportConfigManager.getInstance().isTimestampValid(reportConfig)) {
/*      */ 
/*      */       
/*  253 */       form.setValues(context);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  258 */       String description = form.getStringValue("Description");
/*      */ 
/*      */       
/*  261 */       String reportName = form.getStringValue("ReportName");
/*      */ 
/*      */       
/*  264 */       String subName = form.getStringValue("SubName");
/*      */ 
/*      */       
/*  267 */       String reportType = form.getStringValue("ReportType");
/*      */ 
/*      */       
/*  270 */       String reportFormat = form.getStringValue("ReportFormat").trim();
/*      */ 
/*      */       
/*  273 */       String reportStatus = form.getStringValue("ReportStatus").trim();
/*      */ 
/*      */       
/*  276 */       String path = form.getStringValue("Path");
/*      */ 
/*      */       
/*  279 */       String fileName = form.getStringValue("FileName");
/*      */ 
/*      */       
/*  282 */       String subReportName = form.getStringValue("SubRepName");
/*      */ 
/*      */       
/*  285 */       String uml = form.getStringValue("UML");
/*      */ 
/*      */       
/*  288 */       String uml2 = form.getStringValue("UML2");
/*      */ 
/*      */       
/*  291 */       String uml3 = form.getStringValue("UML3");
/*      */ 
/*      */       
/*  294 */       String pub = form.getStringValue("PUB");
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  299 */       reportConfig.setDescription(description);
/*  300 */       reportConfig.setReportName(reportName);
/*  301 */       int type = -1;
/*      */       
/*      */       try {
/*  304 */         type = Integer.parseInt(reportType);
/*      */       }
/*  306 */       catch (NumberFormatException e) {
/*      */         
/*  308 */         System.out.println("Error converting the Report Type to integer.");
/*  309 */         type = 0;
/*      */       } 
/*  311 */       reportConfig.setType(type);
/*  312 */       reportConfig.setPath(path);
/*  313 */       reportConfig.setFileName(fileName);
/*  314 */       reportConfig.setSubName(subReportName);
/*  315 */       reportConfig.setReportStatus(reportStatus);
/*      */       
/*  317 */       int format = -1;
/*      */       
/*      */       try {
/*  320 */         format = Integer.parseInt(reportFormat);
/*      */       }
/*  322 */       catch (NumberFormatException e) {
/*      */         
/*  324 */         format = 0;
/*      */       } 
/*  326 */       reportConfig.setFormat(format);
/*      */ 
/*      */       
/*  329 */       String reportOwner = form.getStringValue("ReportOwner");
/*      */       
/*  331 */       int owner = -1;
/*      */       
/*      */       try {
/*  334 */         owner = Integer.parseInt(reportOwner);
/*      */       }
/*  336 */       catch (NumberFormatException e) {
/*      */         
/*  338 */         System.out.println("Error converting the Report Owner to integer.");
/*  339 */         owner = 0;
/*      */       } 
/*  341 */       reportConfig.setReportOwner(owner);
/*      */ 
/*      */       
/*  344 */       reportConfig.setBeginDateFlag(((FormCheckBox)form.getElement("BeginDate")).isChecked());
/*  345 */       reportConfig.setEndDateFlag(((FormCheckBox)form.getElement("EndDate")).isChecked());
/*  346 */       reportConfig.setRegionFlag(((FormCheckBox)form.getElement("Region")).isChecked());
/*  347 */       reportConfig.setFamilyFlag(((FormCheckBox)form.getElement("Family")).isChecked());
/*  348 */       reportConfig.setEnvironmentFlag(((FormCheckBox)form.getElement("Environment")).isChecked());
/*  349 */       reportConfig.setCompanyFlag(((FormCheckBox)form.getElement("Company")).isChecked());
/*  350 */       reportConfig.setLabelFlag(((FormCheckBox)form.getElement("Label")).isChecked());
/*  351 */       reportConfig.setContactFlag(((FormCheckBox)form.getElement("LabelContact")).isChecked());
/*      */       
/*  353 */       reportConfig.setCompleteKeyTaskFlag(((FormCheckBox)form.getElement("CompleteKeyTask")).isChecked());
/*  354 */       reportConfig.setParentsOnlyFlag(((FormCheckBox)form.getElement("ParentsOnly")).isChecked());
/*  355 */       reportConfig.setKeyTaskFlag(((FormCheckBox)form.getElement("UMLKeyTask")).isChecked());
/*  356 */       reportConfig.setReleaseTypeFlag(((FormCheckBox)form.getElement("ReleaseType")).isChecked());
/*  357 */       reportConfig.setArtistFlag(((FormCheckBox)form.getElement("Artist")).isChecked());
/*  358 */       reportConfig.setStatusFlag(((FormCheckBox)form.getElement("Status")).isChecked());
/*      */ 
/*      */ 
/*      */       
/*  362 */       reportConfig.setUmlKeyTaskFlag(((FormCheckBox)form.getElement("TaskOwner")).isChecked());
/*      */ 
/*      */ 
/*      */       
/*  366 */       reportConfig.setFuture1Flag(((FormCheckBox)form.getElement("UmlContact")).isChecked());
/*  367 */       reportConfig.setFuture2Flag(((FormCheckBox)form.getElement("Future2")).isChecked());
/*  368 */       reportConfig.setBeginDueDateFlag(((FormCheckBox)form.getElement("BeginDueDate")).isChecked());
/*  369 */       reportConfig.setEndDueDateFlag(((FormCheckBox)form.getElement("EndDueDate")).isChecked());
/*      */       
/*  371 */       reportConfig.setBeginEffectiveDateFlag(((FormCheckBox)form.getElement("BeginEffectiveDate")).isChecked());
/*  372 */       reportConfig.setEndEffectiveDateFlag(((FormCheckBox)form.getElement("EndEffectiveDate")).isChecked());
/*      */       
/*  374 */       reportConfig.setConfiguration(((FormCheckBox)form.getElement("Configuration")).isChecked());
/*  375 */       reportConfig.setScheduledReleasesFlag(((FormCheckBox)form.getElement("ScheduledReleases")).isChecked());
/*  376 */       reportConfig.setAddsMovesBoth(((FormCheckBox)form.getElement("AddsMovesBoth")).isChecked());
/*  377 */       reportConfig.setSubconfigFlag(((FormCheckBox)form.getElement("SubConfiguration")).isChecked());
/*  378 */       reportConfig.setPhysicalProductActivity(((FormCheckBox)form.getElement("PhysicalProductActivity")).isChecked());
/*  379 */       reportConfig.setDistCo(((FormCheckBox)form.getElement("DistCo")).isChecked());
/*      */ 
/*      */       
/*  382 */       String productTypeStr = ((FormRadioButtonGroup)form.getElement("ProductType")).getStringValue();
/*  383 */       int productTypeInt = 0;
/*  384 */       if (productTypeStr.equals("Physical")) {
/*  385 */         productTypeInt = 0;
/*  386 */       } else if (productTypeStr.equals("Digital")) {
/*  387 */         productTypeInt = 1;
/*  388 */       } else if (productTypeStr.equals("Both")) {
/*  389 */         productTypeInt = 2;
/*      */       } 
/*  391 */       reportConfig.setProductType(productTypeInt);
/*      */       
/*  393 */       if (!form.isUnchanged()) {
/*      */         
/*  395 */         FormValidation formValidation = form.validate();
/*  396 */         if (formValidation.isGood())
/*      */         {
/*  398 */           Report savedReportConfig = ReportConfigManager.getInstance().saveReportConfig(reportConfig, user.getUserId());
/*      */ 
/*      */           
/*  401 */           FormElement lastUpdated = form.getElement("lastupdateddate");
/*  402 */           lastUpdated.setValue(MilestoneHelper.getLongDate(savedReportConfig.getLastUpdatedDate()));
/*      */ 
/*      */           
/*  405 */           notepad.setAllContents(null);
/*  406 */           notepad = getReportConfigNotepad(context, MilestoneSecurity.getUser(context).getUserId());
/*  407 */           notepad.goToSelectedPage();
/*  408 */           notepad.setSelected(savedReportConfig);
/*  409 */           reportConfig = (Report)notepad.validateSelected();
/*      */           
/*  411 */           context.putSessionValue("ReportConfig", reportConfig);
/*      */           
/*  413 */           if (reportConfig == null) {
/*  414 */             return goToBlank(context);
/*      */           }
/*  416 */           form = buildForm(context, reportConfig);
/*      */         }
/*      */         else
/*      */         {
/*  420 */           context.putDelivery("FormValidation", formValidation);
/*      */         }
/*      */       
/*      */       } 
/*      */     } else {
/*      */       
/*  426 */       context.putDelivery("AlertMessage", "The record is unavailable for editing at this time.  Please refresh your screen and edit it at a later time.");
/*      */     } 
/*      */ 
/*      */     
/*  430 */     Notepad reportNotepad = MilestoneHelper.getNotepadFromSession(3, context);
/*  431 */     if (reportNotepad != null) {
/*      */       
/*  433 */       reportNotepad.setAllContents(null);
/*  434 */       reportNotepad.setSelected(null);
/*      */     } 
/*      */     
/*  437 */     context.putDelivery("Form", form);
/*  438 */     return context.includeJSP("report-config-editor.jsp");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean delete(Context context) {
/*  446 */     User user = MilestoneSecurity.getUser(context);
/*      */     
/*  448 */     Notepad notepad = getReportConfigNotepad(context, user.getUserId());
/*  449 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */     
/*  451 */     Report reportConfig = MilestoneHelper.getScreenReportConfig(context);
/*      */ 
/*      */     
/*  454 */     if (reportConfig != null) {
/*      */       
/*  456 */       ReportConfigManager.getInstance().deleteReportConfig(reportConfig, user.getUserId());
/*      */ 
/*      */       
/*  459 */       notepad.setAllContents(null);
/*  460 */       notepad = getReportConfigNotepad(context, MilestoneSecurity.getUser(context).getUserId());
/*  461 */       notepad.setSelected(null);
/*      */ 
/*      */       
/*  464 */       Notepad reportNotepad = MilestoneHelper.getNotepadFromSession(3, context);
/*  465 */       if (reportNotepad != null) {
/*      */         
/*  467 */         reportNotepad.setAllContents(null);
/*  468 */         reportNotepad.setSelected(null);
/*      */       } 
/*      */     } 
/*  471 */     return edit(context);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Form buildForm(Context context, Report reportConfig) {
/*  480 */     Form reportConfigForm = new Form(this.application, "reportConfigForm", 
/*  481 */         this.application.getInfrastructure().getServletURL(), "POST");
/*      */     
/*  483 */     if (reportConfig != null) {
/*      */ 
/*      */       
/*  486 */       String reportName = "";
/*  487 */       if (reportConfig.getReportName() != null) {
/*  488 */         reportName = reportConfig.getReportName();
/*      */       }
/*  490 */       FormTextField ReportName = new FormTextField("ReportName", reportName, true, 50, 50);
/*  491 */       reportConfigForm.addElement(ReportName);
/*      */ 
/*      */       
/*  494 */       String subName = "";
/*  495 */       if (reportConfig.getReportName() != null) {
/*  496 */         subName = reportConfig.getSubName();
/*      */       }
/*  498 */       FormTextField SubName = new FormTextField("SubName", subName, false, 50, 50);
/*  499 */       reportConfigForm.addElement(SubName);
/*      */ 
/*      */       
/*  502 */       String descriptionValue = "";
/*  503 */       if (reportConfig.getDescription() != null) {
/*  504 */         descriptionValue = reportConfig.getDescription();
/*      */       }
/*  506 */       FormTextField Description = new FormTextField("Description", descriptionValue, false, 50, 50);
/*  507 */       Description.setTabIndex(1);
/*  508 */       reportConfigForm.addElement(Description);
/*      */ 
/*      */       
/*  511 */       int reportType = 0;
/*  512 */       reportType = reportConfig.getType();
/*      */       
/*  514 */       FormDropDownMenu ReportType = MilestoneHelper.getLookupDropDown("ReportType", Cache.getReportConfigTypes(), 
/*  515 */           "0" + String.valueOf(reportType), true, false);
/*  516 */       ReportType.setTabIndex(2);
/*  517 */       reportConfigForm.addElement(ReportType);
/*      */ 
/*      */       
/*  520 */       int reportFormat = 0;
/*  521 */       reportFormat = reportConfig.getFormat();
/*      */       
/*  523 */       FormDropDownMenu ReportFormat = MilestoneHelper.getLookupDropDown("ReportFormat", Cache.getReportConfigFormats(), 
/*  524 */           "0" + String.valueOf(reportFormat), true, false);
/*  525 */       ReportFormat.setTabIndex(3);
/*  526 */       reportConfigForm.addElement(ReportFormat);
/*      */ 
/*      */       
/*  529 */       String reportStatus = "";
/*  530 */       if (reportConfig != null && reportConfig.getReportStatus() != null) {
/*  531 */         reportStatus = reportConfig.getReportStatus();
/*      */       }
/*  533 */       FormDropDownMenu ReportStatus = MilestoneHelper.getLookupDropDown("ReportStatus", Cache.getReportConfigStatuses(), 
/*  534 */           String.valueOf(reportStatus), true, false);
/*  535 */       ReportStatus.setTabIndex(4);
/*  536 */       reportConfigForm.addElement(ReportStatus);
/*      */ 
/*      */       
/*  539 */       String reportPath = "";
/*  540 */       if (reportConfig.getPath() != null) {
/*  541 */         reportPath = reportConfig.getPath();
/*      */       }
/*  543 */       FormTextField Path = new FormTextField("Path", reportPath, false, 80, 255);
/*  544 */       Path.setTabIndex(5);
/*  545 */       reportConfigForm.addElement(Path);
/*      */ 
/*      */       
/*  548 */       String fileName = "";
/*  549 */       if (reportConfig.getFileName() != null) {
/*  550 */         fileName = reportConfig.getFileName();
/*      */       }
/*  552 */       FormTextField FileName = new FormTextField("FileName", fileName, false, 50, 50);
/*  553 */       FileName.setTabIndex(6);
/*  554 */       reportConfigForm.addElement(FileName);
/*      */ 
/*      */       
/*  557 */       String subRepName = "";
/*  558 */       if (reportConfig.getSubName() != null && !reportConfig.getSubName().equals("[none]")) {
/*  559 */         subRepName = reportConfig.getSubName();
/*      */       }
/*  561 */       FormTextField SubRepName = new FormTextField("SubRepName", subRepName, false, 50, 50);
/*  562 */       SubRepName.setTabIndex(7);
/*  563 */       reportConfigForm.addElement(SubRepName);
/*      */ 
/*      */       
/*  566 */       boolean beginDate = false;
/*  567 */       beginDate = reportConfig.getBeginDateFlag();
/*      */       
/*  569 */       FormCheckBox BeginDate = new FormCheckBox("BeginDate", "BeginDate", "Begin Date", false);
/*  570 */       BeginDate.setChecked(beginDate);
/*  571 */       BeginDate.setId("BeginDate");
/*  572 */       reportConfigForm.addElement(BeginDate);
/*      */ 
/*      */       
/*  575 */       boolean endDate = false;
/*  576 */       endDate = reportConfig.getEndDateFlag();
/*      */       
/*  578 */       FormCheckBox EndDate = new FormCheckBox("EndDate", "EndDate", "End Date", false);
/*  579 */       EndDate.setChecked(endDate);
/*  580 */       EndDate.setId("EndDate");
/*  581 */       reportConfigForm.addElement(EndDate);
/*      */ 
/*      */       
/*  584 */       boolean region = false;
/*  585 */       region = reportConfig.getRegionFlag();
/*      */       
/*  587 */       FormCheckBox Region = new FormCheckBox("Region", "Region", "Region", false);
/*  588 */       Region.setChecked(region);
/*  589 */       Region.setId("Region");
/*  590 */       reportConfigForm.addElement(Region);
/*      */ 
/*      */       
/*  593 */       boolean family = false;
/*  594 */       family = reportConfig.getFamilyFlag();
/*      */       
/*  596 */       FormCheckBox Family = new FormCheckBox("Family", "Family", "Family", false);
/*  597 */       Family.setChecked(family);
/*  598 */       Family.setId("Family");
/*  599 */       reportConfigForm.addElement(Family);
/*      */ 
/*      */       
/*  602 */       Vector families = Cache.getInstance().getFamilies();
/*  603 */       FormDropDownMenu UML = MilestoneHelper.getCorporateStructureDropDown("UML", families, "0", true, true);
/*  604 */       reportConfigForm.addElement(UML);
/*      */ 
/*      */       
/*  607 */       boolean environment = false;
/*  608 */       environment = reportConfig.getEnvironmentFlag();
/*  609 */       FormCheckBox Environment = new FormCheckBox("Environment", "Environment", "Environment", false);
/*  610 */       Environment.setChecked(environment);
/*  611 */       Environment.setId("Environment");
/*  612 */       reportConfigForm.addElement(Environment);
/*      */       
/*  614 */       Vector environments = Cache.getInstance().getEnvironments();
/*  615 */       FormDropDownMenu UML3 = MilestoneHelper.getCorporateStructureDropDown("UML3", environments, "0", true, true);
/*  616 */       reportConfigForm.addElement(UML3);
/*      */ 
/*      */       
/*  619 */       boolean company = false;
/*  620 */       company = reportConfig.getCompanyFlag();
/*      */       
/*  622 */       FormCheckBox Company = new FormCheckBox("Company", "Company", "Company", false);
/*  623 */       Company.setChecked(company);
/*  624 */       Company.setId("Company");
/*  625 */       reportConfigForm.addElement(Company);
/*      */ 
/*      */       
/*  628 */       Vector companies = Cache.getCompanies();
/*  629 */       FormDropDownMenu UML2 = MilestoneHelper.getCorporateStructureDropDown("UML2", companies, "0", true, true);
/*  630 */       reportConfigForm.addElement(UML2);
/*      */ 
/*      */       
/*  633 */       boolean label = false;
/*  634 */       label = reportConfig.getLabelFlag();
/*      */       
/*  636 */       FormCheckBox Label = new FormCheckBox("Label", "Label", "Label", false);
/*  637 */       Label.setChecked(label);
/*  638 */       Label.setId("Label");
/*  639 */       reportConfigForm.addElement(Label);
/*      */ 
/*      */       
/*  642 */       Vector labels = Cache.getLabels();
/*  643 */       FormDropDownMenu PUB = MilestoneHelper.getCorporateStructureDropDown("PUB", labels, "0", true, true);
/*  644 */       reportConfigForm.addElement(PUB);
/*      */ 
/*      */       
/*  647 */       boolean contact = false;
/*  648 */       contact = reportConfig.getContactFlag();
/*  649 */       FormCheckBox Contact = new FormCheckBox("LabelContact", "LabelContact", "Label Contact", false);
/*  650 */       Contact.setChecked(contact);
/*  651 */       Contact.setId("LabelContact");
/*  652 */       reportConfigForm.addElement(Contact);
/*      */ 
/*      */       
/*  655 */       Vector contactVector = ReportConfigManager.getLabelContacts();
/*  656 */       String contactList = "&nbsp;,";
/*      */       
/*  658 */       for (int i = 0; i < contactVector.size(); i++) {
/*  659 */         User userContact = (User)contactVector.get(i);
/*  660 */         if (i < contactVector.size() - 1) {
/*  661 */           if (userContact != null) {
/*  662 */             contactList = String.valueOf(contactList) + userContact.getName() + ",";
/*      */           }
/*      */         }
/*  665 */         else if (userContact != null) {
/*  666 */           contactList = String.valueOf(contactList) + userContact.getName();
/*      */         } 
/*      */       } 
/*  669 */       FormDropDownMenu ContactList = new FormDropDownMenu("LabelContactDropDown", "All", contactList, false);
/*  670 */       reportConfigForm.addElement(ContactList);
/*      */ 
/*      */       
/*  673 */       boolean completeKeyTask = false;
/*  674 */       completeKeyTask = reportConfig.getCompleteKeyTaskFlag();
/*      */       
/*  676 */       FormCheckBox CompleteKeyTask = new FormCheckBox("CompleteKeyTask", "CompleteKeyTask", "Completed Task", false);
/*  677 */       CompleteKeyTask.setChecked(completeKeyTask);
/*  678 */       CompleteKeyTask.setId("CompleteKeyTask");
/*  679 */       reportConfigForm.addElement(CompleteKeyTask);
/*      */ 
/*      */ 
/*      */       
/*  683 */       boolean parentsOnly = false;
/*  684 */       parentsOnly = reportConfig.getParentsOnlyFlag();
/*      */       
/*  686 */       FormCheckBox ParentsOnly = new FormCheckBox("ParentsOnly", "ParentsOnly", "Parents Only", false);
/*  687 */       ParentsOnly.setChecked(parentsOnly);
/*  688 */       ParentsOnly.setId("ParentsOnly");
/*  689 */       reportConfigForm.addElement(ParentsOnly);
/*      */ 
/*      */       
/*  692 */       boolean umlKeyTask = false;
/*  693 */       umlKeyTask = reportConfig.getKeyTaskFlag();
/*      */       
/*  695 */       FormCheckBox UMLKeyTask = new FormCheckBox("UMLKeyTask", "UMLKeyTask", "UMLKeyTask", false);
/*  696 */       UMLKeyTask.setChecked(umlKeyTask);
/*  697 */       UMLKeyTask.setId("UMLKeyTask");
/*  698 */       reportConfigForm.addElement(UMLKeyTask);
/*      */ 
/*      */       
/*  701 */       boolean releaseType = false;
/*  702 */       releaseType = reportConfig.getReleaseTypeFlag();
/*      */       
/*  704 */       FormCheckBox ReleaseType = new FormCheckBox("ReleaseType", "ReleaseType", "ReleaseType", false);
/*  705 */       ReleaseType.setChecked(releaseType);
/*  706 */       ReleaseType.setId("ReleaseType");
/*  707 */       reportConfigForm.addElement(ReleaseType);
/*      */ 
/*      */       
/*  710 */       boolean status = false;
/*  711 */       status = reportConfig.getStatusFlag();
/*      */       
/*  713 */       FormCheckBox Status = new FormCheckBox("Status", "Status", "Status", false);
/*  714 */       Status.setChecked(status);
/*  715 */       Status.setId("Status");
/*  716 */       reportConfigForm.addElement(Status);
/*      */ 
/*      */       
/*  719 */       boolean artist = false;
/*  720 */       artist = reportConfig.getArtistFlag();
/*      */       
/*  722 */       FormCheckBox Artist = new FormCheckBox("Artist", "Artist", "Artist", false);
/*  723 */       Artist.setChecked(artist);
/*  724 */       Artist.setId("Artist");
/*  725 */       reportConfigForm.addElement(Artist);
/*      */ 
/*      */       
/*  728 */       boolean taskOwner = false;
/*      */       
/*  730 */       taskOwner = reportConfig.getUmlKeyTaskFlag();
/*      */       
/*  732 */       FormCheckBox TaskOwner = new FormCheckBox("TaskOwner", "TaskOwner", "Task Owner", false);
/*  733 */       TaskOwner.setChecked(taskOwner);
/*  734 */       TaskOwner.setId("TaskOwner");
/*  735 */       reportConfigForm.addElement(TaskOwner);
/*      */ 
/*      */       
/*  738 */       boolean future1 = false;
/*  739 */       future1 = reportConfig.getFuture1Flag();
/*      */       
/*  741 */       FormCheckBox Fut1 = new FormCheckBox("UmlContact", "UmlContact", "Uml Contact", false);
/*  742 */       Fut1.setChecked(future1);
/*  743 */       Fut1.setId("UmlContact");
/*  744 */       reportConfigForm.addElement(Fut1);
/*      */ 
/*      */       
/*  747 */       Vector contactVector1 = ReportConfigManager.getUmlContacts();
/*  748 */       String contactList1 = "&nbsp;,";
/*      */       
/*  750 */       for (int i = 0; i < contactVector1.size(); i++) {
/*  751 */         User userContact1 = (User)contactVector1.get(i);
/*  752 */         if (i < contactVector1.size() - 1) {
/*  753 */           if (userContact1 != null) {
/*  754 */             contactList1 = String.valueOf(contactList1) + userContact1.getName() + ",";
/*      */           }
/*      */         }
/*  757 */         else if (userContact1 != null) {
/*  758 */           contactList1 = String.valueOf(contactList1) + userContact1.getName();
/*      */         } 
/*      */       } 
/*  761 */       FormDropDownMenu ContactList1 = new FormDropDownMenu("UmlContactDropDown", "All", contactList1, false);
/*  762 */       reportConfigForm.addElement(ContactList1);
/*      */ 
/*      */       
/*  765 */       boolean future2 = false;
/*  766 */       future2 = reportConfig.getFuture2Flag();
/*      */       
/*  768 */       FormCheckBox Future2 = new FormCheckBox("Future2", "Future2", "Future2", false);
/*  769 */       Future2.setChecked(future2);
/*  770 */       Future2.setId("Future2");
/*  771 */       reportConfigForm.addElement(Future2);
/*      */ 
/*      */       
/*  774 */       boolean beginDueDate = false;
/*  775 */       beginDueDate = reportConfig.getBeginDueDateFlag();
/*      */       
/*  777 */       FormCheckBox BeginDueDate = new FormCheckBox("BeginDueDate", "BeginDueDate", "Begin Due Date", false);
/*  778 */       BeginDueDate.setChecked(beginDueDate);
/*  779 */       BeginDueDate.setId("BeginDueDate");
/*  780 */       reportConfigForm.addElement(BeginDueDate);
/*      */ 
/*      */       
/*  783 */       boolean endDueDate = false;
/*  784 */       endDueDate = reportConfig.getEndDueDateFlag();
/*      */       
/*  786 */       FormCheckBox EndDueDate = new FormCheckBox("EndDueDate", "EndDueDate", "End Due Date", false);
/*  787 */       EndDueDate.setChecked(endDueDate);
/*  788 */       EndDueDate.setId("EndDueDate");
/*  789 */       reportConfigForm.addElement(EndDueDate);
/*      */ 
/*      */       
/*  792 */       FormTextField lastUpdated = new FormTextField("lastupdateddate", false, 50);
/*  793 */       if (reportConfig.getLastUpdatedDate() != null)
/*  794 */         lastUpdated.setValue(MilestoneHelper.getCustomFormatedDate(reportConfig.getLastUpdatedDate(), "M/d/yyyy hh:mm:ss a 'ET'")); 
/*  795 */       reportConfigForm.addElement(lastUpdated);
/*      */       
/*  797 */       FormTextField lastUpdatedBy = new FormTextField("lastUpdatedBy", false, 50);
/*  798 */       if (UserManager.getInstance().getUser(reportConfig.getLastUpdatingUser()) != null)
/*  799 */         lastUpdatedBy.setValue(UserManager.getInstance().getUser(reportConfig.getLastUpdatingUser()).getName()); 
/*  800 */       reportConfigForm.addElement(lastUpdatedBy);
/*      */ 
/*      */       
/*  803 */       boolean beginEffectiveDate = false;
/*  804 */       beginEffectiveDate = reportConfig.getBeginEffectiveDateFlag();
/*      */       
/*  806 */       FormCheckBox BeginEffectiveDate = new FormCheckBox("BeginEffectiveDate", "BeginEffectiveDate", "Begin Effective Date", false);
/*  807 */       BeginEffectiveDate.setChecked(beginEffectiveDate);
/*  808 */       BeginEffectiveDate.setId("BeginEffectiveDate");
/*  809 */       reportConfigForm.addElement(BeginEffectiveDate);
/*      */ 
/*      */       
/*  812 */       boolean endEffectiveDate = false;
/*  813 */       endEffectiveDate = reportConfig.getEndEffectiveDateFlag();
/*      */       
/*  815 */       FormCheckBox EndEffectiveDate = new FormCheckBox("EndEffectiveDate", "EndEffectiveDate", "Ending Effective Date", false);
/*  816 */       EndEffectiveDate.setChecked(endEffectiveDate);
/*  817 */       EndEffectiveDate.setId("EndEffectiveDate");
/*  818 */       reportConfigForm.addElement(EndEffectiveDate);
/*      */ 
/*      */       
/*  821 */       Vector ownerFamilies = Cache.getInstance().getFamilies();
/*  822 */       String ownerId = Integer.toString(reportConfig.getReportOwner(), 0);
/*  823 */       FormDropDownMenu ReportOwner = MilestoneHelper.getCorporateStructureDropDown("ReportOwner", ownerFamilies, ownerId, true, true);
/*  824 */       reportConfigForm.addElement(ReportOwner);
/*      */ 
/*      */ 
/*      */       
/*  828 */       boolean configuration = false;
/*  829 */       configuration = reportConfig.getConfiguration();
/*      */ 
/*      */ 
/*      */       
/*  833 */       FormCheckBox Config = new FormCheckBox("Configuration", "Configuration", "Format/Schedule Type", false);
/*  834 */       Config.setChecked(configuration);
/*  835 */       Config.addFormEvent("onClick", "isSubconfigSelected();");
/*  836 */       Config.setId("Configuration");
/*  837 */       reportConfigForm.addElement(Config);
/*      */       
/*  839 */       FormDropDownMenu configList = MilestoneHelper.getSelectionConfigurationDropDown("configurationList", "", false);
/*  840 */       reportConfigForm.addElement(configList);
/*      */ 
/*      */ 
/*      */       
/*  844 */       boolean subconfiguration = false;
/*  845 */       subconfiguration = reportConfig.getSubconfigFlag();
/*      */       
/*  847 */       FormCheckBox SubConfig = new FormCheckBox("Subconfiguration", "Subconfiguration", "Sub-Format", false);
/*  848 */       SubConfig.addFormEvent("onClick", "isConfigSelected();");
/*  849 */       SubConfig.setChecked(subconfiguration);
/*  850 */       SubConfig.setId("Subconfiguration");
/*  851 */       reportConfigForm.addElement(SubConfig);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  859 */       boolean scheduledReleases = false;
/*  860 */       scheduledReleases = reportConfig.getScheduledReleasesFlag();
/*      */       
/*  862 */       FormCheckBox ScheduledReleases = new FormCheckBox("ScheduledReleases", "ScheduledReleases", "Scheduled Releases", false);
/*  863 */       ScheduledReleases.setChecked(scheduledReleases);
/*  864 */       ScheduledReleases.setId("ScheduledReleases");
/*  865 */       reportConfigForm.addElement(ScheduledReleases);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  870 */       boolean addsMovesBoth = false;
/*  871 */       addsMovesBoth = reportConfig.getAddsMovesBoth();
/*      */       
/*  873 */       FormCheckBox AddsMovesBoth = new FormCheckBox("AddsMovesBoth", "AddsMovesBoth", "Adds Moves Both", false);
/*  874 */       AddsMovesBoth.setChecked(addsMovesBoth);
/*  875 */       AddsMovesBoth.setId("AddsMovesBoth");
/*  876 */       reportConfigForm.addElement(AddsMovesBoth);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  881 */       boolean physicalActivity = false;
/*  882 */       physicalActivity = reportConfig.getPhysicalProductActivity();
/*      */       
/*  884 */       FormCheckBox PhysicalProductActivity = new FormCheckBox("PhysicalProductActivity", 
/*  885 */           "PhysicalProductActivity", "Physical Product Activity", false);
/*  886 */       PhysicalProductActivity.setChecked(physicalActivity);
/*  887 */       PhysicalProductActivity.setId("PhysicalProductActivity");
/*  888 */       reportConfigForm.addElement(PhysicalProductActivity);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  893 */       boolean distCo = false;
/*  894 */       distCo = reportConfig.getDistCo();
/*  895 */       FormCheckBox DistCo = new FormCheckBox("DistCo", "DistCo", "Distribution Company", false);
/*  896 */       DistCo.setChecked(distCo);
/*  897 */       DistCo.setId("DistCo");
/*  898 */       reportConfigForm.addElement(DistCo);
/*      */       
/*  900 */       reportConfigForm.addElement(MilestoneHelper_2.BuildDistCoDropDown());
/*      */ 
/*      */ 
/*      */       
/*  904 */       int productType = reportConfig.getProductType();
/*  905 */       String productTypeStr = "";
/*  906 */       if (productType == 0) {
/*  907 */         productTypeStr = "Physical";
/*  908 */       } else if (productType == 1) {
/*  909 */         productTypeStr = "Digital";
/*  910 */       } else if (productType == 2) {
/*  911 */         productTypeStr = "Both";
/*      */       } 
/*  913 */       FormRadioButtonGroup ProductType = new FormRadioButtonGroup("ProductType", productTypeStr, "Physical,Digital,Both", false);
/*  914 */       ProductType.setId("ProductType");
/*  915 */       reportConfigForm.addElement(ProductType);
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/*  922 */       FormTextField ReportName = new FormTextField("ReportName", "", true, 50, 50);
/*  923 */       reportConfigForm.addElement(ReportName);
/*      */ 
/*      */       
/*  926 */       FormTextField SubName = new FormTextField("SubName", "", true, 50, 50);
/*  927 */       reportConfigForm.addElement(SubName);
/*      */ 
/*      */       
/*  930 */       FormTextField Description = new FormTextField("Description", "", true, 50, 50);
/*  931 */       Description.setTabIndex(1);
/*  932 */       reportConfigForm.addElement(Description);
/*      */ 
/*      */       
/*  935 */       FormDropDownMenu ReportType = MilestoneHelper.getLookupDropDown("ReportType", Cache.getReportConfigTypes(), "", true, true);
/*  936 */       ReportType.setTabIndex(2);
/*  937 */       reportConfigForm.addElement(ReportType);
/*      */ 
/*      */       
/*  940 */       FormDropDownMenu ReportFormat = MilestoneHelper.getLookupDropDown("ReportFormat", Cache.getReportConfigFormats(), "", true, true);
/*  941 */       ReportFormat.setTabIndex(3);
/*  942 */       reportConfigForm.addElement(ReportFormat);
/*      */ 
/*      */       
/*  945 */       FormDropDownMenu ReportStatus = MilestoneHelper.getLookupDropDown("ReportStatus", Cache.getReportConfigStatuses(), "", true, true);
/*  946 */       ReportStatus.setTabIndex(4);
/*  947 */       reportConfigForm.addElement(ReportStatus);
/*      */ 
/*      */       
/*  950 */       FormTextField Path = new FormTextField("Path", "", true, 80, 255);
/*  951 */       Path.setTabIndex(5);
/*  952 */       reportConfigForm.addElement(Path);
/*      */ 
/*      */       
/*  955 */       FormTextField FileName = new FormTextField("FileName", "", true, 50, 50);
/*  956 */       FileName.setTabIndex(6);
/*  957 */       reportConfigForm.addElement(FileName);
/*      */ 
/*      */       
/*  960 */       FormCheckBox BeginDate = new FormCheckBox("BeginDate", "BeginDate", "BeginDate", false);
/*  961 */       BeginDate.setId("BeginDate");
/*  962 */       reportConfigForm.addElement(BeginDate);
/*      */ 
/*      */       
/*  965 */       FormCheckBox EndDate = new FormCheckBox("EndDate", "EndDate", "EndDate", false);
/*  966 */       EndDate.setId("EndDate");
/*  967 */       reportConfigForm.addElement(EndDate);
/*      */ 
/*      */       
/*  970 */       FormCheckBox Region = new FormCheckBox("Region", "Region", "Region", false);
/*  971 */       Region.setId("Region");
/*  972 */       reportConfigForm.addElement(Region);
/*      */ 
/*      */       
/*  975 */       FormCheckBox Family = new FormCheckBox("Family", "Family", "Family", false);
/*  976 */       Family.setId("Family");
/*  977 */       reportConfigForm.addElement(Family);
/*      */ 
/*      */       
/*  980 */       Vector families = Cache.getInstance().getFamilies();
/*  981 */       FormDropDownMenu UML = MilestoneHelper.getCorporateStructureDropDown("UML", families, "0", true, true);
/*  982 */       reportConfigForm.addElement(UML);
/*      */ 
/*      */       
/*  985 */       FormCheckBox Environment = new FormCheckBox("Environment", "Environment", "Environment", false);
/*  986 */       Environment.setId("Environment");
/*  987 */       reportConfigForm.addElement(Environment);
/*  988 */       Vector environments = Cache.getInstance().getEnvironments();
/*  989 */       FormDropDownMenu UML3 = MilestoneHelper.getCorporateStructureDropDown("UML3", environments, "0", true, true);
/*  990 */       reportConfigForm.addElement(UML3);
/*      */ 
/*      */       
/*  993 */       FormCheckBox Company = new FormCheckBox("Company", "Company", "Company", false);
/*  994 */       Company.setId("Company");
/*  995 */       reportConfigForm.addElement(Company);
/*      */ 
/*      */       
/*  998 */       Vector companies = Cache.getCompanies();
/*  999 */       FormDropDownMenu UML2 = MilestoneHelper.getCorporateStructureDropDown("UML2", companies, "0", true, true);
/* 1000 */       reportConfigForm.addElement(UML2);
/*      */ 
/*      */       
/* 1003 */       FormCheckBox Label = new FormCheckBox("Label", "Label", "Label", false);
/* 1004 */       Label.setId("Label");
/* 1005 */       reportConfigForm.addElement(Label);
/*      */ 
/*      */       
/* 1008 */       Vector labels = Cache.getLabels();
/* 1009 */       FormDropDownMenu PUB = MilestoneHelper.getCorporateStructureDropDown("PUB", labels, "0", true, true);
/* 1010 */       reportConfigForm.addElement(PUB);
/*      */ 
/*      */       
/* 1013 */       FormCheckBox Contact = new FormCheckBox("LabelContact", "LabelContact", "Label Contact", false);
/* 1014 */       Contact.setId("LabelContact");
/* 1015 */       reportConfigForm.addElement(Contact);
/*      */ 
/*      */       
/* 1018 */       Vector contactVector = ReportConfigManager.getLabelContacts();
/* 1019 */       String contactList = "&nbsp;,";
/*      */       
/* 1021 */       for (int i = 0; i < contactVector.size(); i++) {
/*      */         
/* 1023 */         User userContact = (User)contactVector.get(i);
/* 1024 */         if (i < contactVector.size() - 1) {
/*      */           
/* 1026 */           if (userContact != null) {
/* 1027 */             contactList = String.valueOf(contactList) + userContact.getName() + ",";
/*      */           
/*      */           }
/*      */         }
/* 1031 */         else if (userContact != null) {
/* 1032 */           contactList = String.valueOf(contactList) + userContact.getName();
/*      */         } 
/*      */       } 
/* 1035 */       FormDropDownMenu ContactList = new FormDropDownMenu("LabelContactDropDown", "All", contactList, false);
/* 1036 */       reportConfigForm.addElement(ContactList);
/*      */ 
/*      */       
/* 1039 */       FormCheckBox CompleteKeyTask = new FormCheckBox("CompleteKeyTask", "CompleteKeyTask", "Completed Task", false);
/* 1040 */       CompleteKeyTask.setId("CompleteKeyTask");
/* 1041 */       reportConfigForm.addElement(CompleteKeyTask);
/*      */ 
/*      */ 
/*      */       
/* 1045 */       FormCheckBox ParentsOnly = new FormCheckBox("ParentsOnly", "ParentsOnly", "Parents Only", false);
/* 1046 */       ParentsOnly.setId("ParentsOnly");
/* 1047 */       reportConfigForm.addElement(ParentsOnly);
/*      */ 
/*      */       
/* 1050 */       FormCheckBox UMLKeyTask = new FormCheckBox("UMLKeyTask", "UMLKeyTask", "UML Key Task", false);
/* 1051 */       UMLKeyTask.setId("UMLKeyTask");
/* 1052 */       reportConfigForm.addElement(UMLKeyTask);
/*      */ 
/*      */       
/* 1055 */       FormCheckBox ReleaseType = new FormCheckBox("ReleaseType", "ReleaseType", "ReleaseType", false);
/* 1056 */       ReleaseType.setId("ReleaseType");
/* 1057 */       reportConfigForm.addElement(ReleaseType);
/*      */ 
/*      */       
/* 1060 */       FormCheckBox Status = new FormCheckBox("Status", "Status", "Status", false);
/* 1061 */       Status.setId("Status");
/* 1062 */       reportConfigForm.addElement(Status);
/*      */ 
/*      */       
/* 1065 */       FormCheckBox Artist = new FormCheckBox("Artist", "Artist", "Artist", false);
/* 1066 */       Artist.setId("Artist");
/* 1067 */       reportConfigForm.addElement(Artist);
/*      */ 
/*      */       
/* 1070 */       FormCheckBox UMLDates = new FormCheckBox("TaskOwner", "TaskOwner", "Task Owner", false);
/* 1071 */       UMLDates.setId("UMLDates");
/* 1072 */       reportConfigForm.addElement(UMLDates);
/*      */ 
/*      */       
/* 1075 */       FormCheckBox Fut1 = new FormCheckBox("UmlContact", "UmlContact", "Uml Contact", false);
/* 1076 */       Fut1.setId("UmlContact");
/* 1077 */       reportConfigForm.addElement(Fut1);
/*      */ 
/*      */       
/* 1080 */       Vector contactVector1 = ReportConfigManager.getUmlContacts();
/* 1081 */       String contactList1 = "&nbsp;,";
/*      */       
/* 1083 */       for (int i = 0; i < contactVector1.size(); i++) {
/*      */         
/* 1085 */         User userContact1 = (User)contactVector1.get(i);
/* 1086 */         if (i < contactVector1.size() - 1) {
/*      */           
/* 1088 */           if (userContact1 != null) {
/* 1089 */             contactList1 = String.valueOf(contactList1) + userContact1.getName() + ",";
/*      */           
/*      */           }
/*      */         }
/* 1093 */         else if (userContact1 != null) {
/* 1094 */           contactList1 = String.valueOf(contactList1) + userContact1.getName();
/*      */         } 
/*      */       } 
/* 1097 */       FormDropDownMenu ContactList1 = new FormDropDownMenu("UmlContactDropDown", "All", contactList1, false);
/* 1098 */       reportConfigForm.addElement(ContactList1);
/*      */ 
/*      */       
/* 1101 */       FormCheckBox Future2 = new FormCheckBox("Future2", "Future2", "Future2", false);
/* 1102 */       Future2.setId("Future2");
/* 1103 */       reportConfigForm.addElement(Future2);
/*      */ 
/*      */       
/* 1106 */       FormCheckBox BeginDueDate = new FormCheckBox("BeginDueDate", "BeginDueDate", "Begin Due Date", false);
/* 1107 */       BeginDueDate.setId("BeginDueDate");
/* 1108 */       reportConfigForm.addElement(BeginDueDate);
/*      */ 
/*      */       
/* 1111 */       FormCheckBox EndDueDate = new FormCheckBox("EndDueDate", "EndDueDate", "End Due Date", false);
/* 1112 */       EndDueDate.setId("EndDueDate");
/* 1113 */       reportConfigForm.addElement(EndDueDate);
/*      */ 
/*      */       
/* 1116 */       FormTextField lastUpdated = new FormTextField("lastupdateddate", false, 50);
/* 1117 */       lastUpdated.setValue("");
/* 1118 */       reportConfigForm.addElement(lastUpdated);
/*      */       
/* 1120 */       FormTextField lastUpdatedBy = new FormTextField("lastUpdatedBy", false, 50);
/* 1121 */       lastUpdatedBy.setValue("");
/* 1122 */       reportConfigForm.addElement(lastUpdatedBy);
/*      */ 
/*      */       
/* 1125 */       FormCheckBox BeginEffectiveDate = new FormCheckBox("BeginEffectiveDate", "BeginEffectiveDate", "Begin Effective Date", false);
/* 1126 */       BeginEffectiveDate.setId("BeginEffectiveDate");
/* 1127 */       reportConfigForm.addElement(BeginEffectiveDate);
/*      */ 
/*      */       
/* 1130 */       FormCheckBox EndEffectiveDate = new FormCheckBox("EndEffectiveDate", "EndEffectiveDate", "Ending Effective Date", false);
/* 1131 */       EndEffectiveDate.setId("EndEffectiveDate");
/* 1132 */       reportConfigForm.addElement(EndEffectiveDate);
/*      */ 
/*      */       
/* 1135 */       Vector ownerFamilies = Cache.getInstance().getFamilies();
/* 1136 */       FormDropDownMenu ReportOwner = MilestoneHelper.getCorporateStructureDropDown("ReportOwner", ownerFamilies, "", true, true);
/* 1137 */       reportConfigForm.addElement(ReportOwner);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1142 */     FormTextField ReportNameSearch = new FormTextField("ReportNameSearch", "", false, 10, 10);
/* 1143 */     ReportNameSearch.setId("ReportNameSearch");
/* 1144 */     reportConfigForm.addElement(ReportNameSearch);
/*      */     
/* 1146 */     FormTextField FileNameSearch = new FormTextField("FileNameSearch", "", false, 10, 10);
/* 1147 */     FileNameSearch.setId("FileNameSearch");
/* 1148 */     reportConfigForm.addElement(FileNameSearch);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1154 */     FormCheckBox Config = new FormCheckBox("Configuration", "Configuration", "Format", false);
/* 1155 */     Config.addFormEvent("onClick", "isSubconfigSelected();");
/* 1156 */     reportConfigForm.addElement(Config);
/*      */     
/* 1158 */     FormDropDownMenu configList = MilestoneHelper.getSelectionConfigurationDropDown("configurationList", "", false);
/* 1159 */     reportConfigForm.addElement(configList);
/*      */ 
/*      */ 
/*      */     
/* 1163 */     boolean subconfiguration = false;
/* 1164 */     subconfiguration = reportConfig.getSubconfigFlag();
/*      */ 
/*      */     
/* 1167 */     FormCheckBox SubConfig = new FormCheckBox("Subconfiguration", "Subconfiguration", "Sub-Format", false);
/*      */     
/* 1169 */     SubConfig.setChecked(subconfiguration);
/* 1170 */     SubConfig.addFormEvent("onClick", "isConfigSelected();");
/* 1171 */     SubConfig.setId("Subconfiguration");
/* 1172 */     reportConfigForm.addElement(SubConfig);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1180 */     FormCheckBox ScheduledReleases = new FormCheckBox("ScheduledReleases", "ScheduledReleases", "Scheduled Releases", false);
/* 1181 */     ScheduledReleases.setId("SchduledReleases");
/* 1182 */     reportConfigForm.addElement(ScheduledReleases);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1187 */     FormCheckBox AddsMovesBoth = new FormCheckBox("AddsMovesBoth", "AddsMovesBoth", "Adds Moves Both", false);
/* 1188 */     AddsMovesBoth.setId("AddsMovesBoth");
/* 1189 */     reportConfigForm.addElement(AddsMovesBoth);
/*      */ 
/*      */ 
/*      */     
/* 1193 */     FormRadioButtonGroup ProductType = new FormRadioButtonGroup("ProductType", "Physical", "Physical,Digital,Both", false);
/* 1194 */     ProductType.setId("ProductType");
/* 1195 */     reportConfigForm.addElement(ProductType);
/*      */ 
/*      */     
/* 1198 */     if (reportConfig != null)
/*      */     {
/*      */       
/* 1201 */       context.putSessionValue("ReportConfig", reportConfig);
/*      */     }
/*      */ 
/*      */     
/* 1205 */     reportConfigForm.addElement(new FormHidden("cmd", "report-config-editor", true));
/* 1206 */     reportConfigForm.addElement(new FormHidden("OrderBy", "", true));
/*      */ 
/*      */     
/* 1209 */     if (context.getSessionValue("NOTEPAD_REPORT_CONFIG_VISIBLE") != null) {
/* 1210 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_REPORT_CONFIG_VISIBLE"));
/*      */     }
/* 1212 */     return reportConfigForm;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean editNew(Context context) {
/* 1219 */     User user = MilestoneSecurity.getUser(context);
/*      */     
/* 1221 */     Notepad notepad = getReportConfigNotepad(context, user.getUserId());
/* 1222 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */     
/* 1224 */     Form form = buildNewForm(context);
/* 1225 */     form.addElement(new FormHidden("cmd", "report-config-edit-new", true));
/* 1226 */     context.putDelivery("Form", form);
/*      */     
/* 1228 */     return context.includeJSP("report-config-editor.jsp");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Form buildNewForm(Context context) {
/* 1237 */     Form reportConfigForm = new Form(this.application, "reportConfigForm", 
/* 1238 */         this.application.getInfrastructure().getServletURL(), "POST");
/* 1239 */     User user = MilestoneSecurity.getUser(context);
/*      */ 
/*      */     
/* 1242 */     FormTextField ReportName = new FormTextField("ReportName", "", true, 10, 10);
/* 1243 */     ReportName.setTabIndex(1);
/* 1244 */     reportConfigForm.addElement(ReportName);
/*      */ 
/*      */     
/* 1247 */     FormTextField Description = new FormTextField("Description", "", false, 50, 50);
/* 1248 */     Description.setTabIndex(2);
/* 1249 */     reportConfigForm.addElement(Description);
/*      */ 
/*      */     
/* 1252 */     FormDropDownMenu ReportType = MilestoneHelper.getLookupDropDown("ReportType", Cache.getReportConfigTypes(), "", true, false);
/* 1253 */     ReportType.setTabIndex(3);
/* 1254 */     reportConfigForm.addElement(ReportType);
/*      */ 
/*      */     
/* 1257 */     FormDropDownMenu ReportFormat = MilestoneHelper.getLookupDropDown("ReportFormat", Cache.getReportConfigFormats(), "", true, false);
/* 1258 */     ReportFormat.setTabIndex(4);
/* 1259 */     reportConfigForm.addElement(ReportFormat);
/*      */ 
/*      */     
/* 1262 */     FormTextField Path = new FormTextField("Path", "", false, 80, 255);
/* 1263 */     Path.setTabIndex(5);
/* 1264 */     reportConfigForm.addElement(Path);
/*      */ 
/*      */     
/* 1267 */     FormTextField FileName = new FormTextField("FileName", "", false, 50, 50);
/* 1268 */     FileName.setTabIndex(6);
/* 1269 */     reportConfigForm.addElement(FileName);
/*      */ 
/*      */     
/* 1272 */     FormRadioButtonGroup ProductType = new FormRadioButtonGroup("ProductType", "Physical", "Physical,Digital,Both", false);
/* 1273 */     ProductType.setId("ProductType");
/* 1274 */     reportConfigForm.addElement(ProductType);
/*      */ 
/*      */ 
/*      */     
/* 1278 */     FormCheckBox BeginDate = new FormCheckBox("BeginDate", "", "Begin Date", false);
/* 1279 */     BeginDate.setTabIndex(7);
/* 1280 */     reportConfigForm.addElement(BeginDate);
/*      */ 
/*      */     
/* 1283 */     FormCheckBox EndDate = new FormCheckBox("EndDate", "", "End Date", false);
/* 1284 */     EndDate.setTabIndex(8);
/* 1285 */     reportConfigForm.addElement(EndDate);
/*      */ 
/*      */     
/* 1288 */     FormCheckBox Region = new FormCheckBox("Region", "", "Region", false);
/* 1289 */     Region.setTabIndex(9);
/* 1290 */     reportConfigForm.addElement(Region);
/*      */ 
/*      */     
/* 1293 */     FormCheckBox Family = new FormCheckBox("Family", "", "Family", false);
/* 1294 */     Family.setTabIndex(10);
/* 1295 */     reportConfigForm.addElement(Family);
/*      */ 
/*      */     
/* 1298 */     FormCheckBox Environment = new FormCheckBox("Environment", "", "Environment", false);
/* 1299 */     Environment.setTabIndex(11);
/* 1300 */     reportConfigForm.addElement(Environment);
/*      */ 
/*      */     
/* 1303 */     FormCheckBox Company = new FormCheckBox("Company", "", "Company", false);
/* 1304 */     Company.setTabIndex(12);
/* 1305 */     reportConfigForm.addElement(Company);
/*      */ 
/*      */     
/* 1308 */     FormCheckBox Label = new FormCheckBox("Label", "", "Label", false);
/* 1309 */     Label.setTabIndex(13);
/* 1310 */     reportConfigForm.addElement(Label);
/*      */ 
/*      */     
/* 1313 */     FormCheckBox Contact = new FormCheckBox("LabelContact", "", "LabelContact", false);
/* 1314 */     Contact.setTabIndex(14);
/* 1315 */     reportConfigForm.addElement(Contact);
/*      */ 
/*      */     
/* 1318 */     FormCheckBox CompleteKeyTask = new FormCheckBox("CompleteKeyTask", "", "Completed Task", false);
/* 1319 */     CompleteKeyTask.setTabIndex(15);
/* 1320 */     reportConfigForm.addElement(CompleteKeyTask);
/*      */ 
/*      */ 
/*      */     
/* 1324 */     FormCheckBox ParentsOnly = new FormCheckBox("ParentsOnly", "", "Parents Only", false);
/* 1325 */     ParentsOnly.setTabIndex(16);
/* 1326 */     reportConfigForm.addElement(ParentsOnly);
/*      */ 
/*      */     
/* 1329 */     FormCheckBox UMLKeyTask = new FormCheckBox("UMLKeyTask", "", "Uml Key Task", false);
/* 1330 */     UMLKeyTask.setTabIndex(17);
/* 1331 */     reportConfigForm.addElement(UMLKeyTask);
/*      */ 
/*      */     
/* 1334 */     FormCheckBox ReleaseType = new FormCheckBox("ReleaseType", "", "Release Type", false);
/* 1335 */     ReleaseType.setTabIndex(18);
/* 1336 */     reportConfigForm.addElement(ReleaseType);
/*      */ 
/*      */     
/* 1339 */     FormCheckBox Status = new FormCheckBox("Status", "", "Status", false);
/* 1340 */     Status.setTabIndex(19);
/* 1341 */     reportConfigForm.addElement(Status);
/*      */ 
/*      */     
/* 1344 */     FormCheckBox Artist = new FormCheckBox("Artist", "", "Artist", false);
/* 1345 */     Artist.setTabIndex(20);
/* 1346 */     reportConfigForm.addElement(Artist);
/*      */ 
/*      */     
/* 1349 */     FormCheckBox UMLDates = new FormCheckBox("TaskOwner", "", "Task Owner", false);
/* 1350 */     UMLDates.setTabIndex(21);
/* 1351 */     reportConfigForm.addElement(UMLDates);
/*      */ 
/*      */     
/* 1354 */     FormCheckBox umlContact = new FormCheckBox("UmlContact", "", "Uml Contact", false);
/* 1355 */     umlContact.setTabIndex(22);
/* 1356 */     reportConfigForm.addElement(umlContact);
/*      */ 
/*      */     
/* 1359 */     FormCheckBox Future2 = new FormCheckBox("Future2", "", "Future2", false);
/* 1360 */     Future2.setTabIndex(23);
/* 1361 */     reportConfigForm.addElement(Future2);
/*      */ 
/*      */     
/* 1364 */     FormCheckBox BeginDueDate = new FormCheckBox("BeginDueDate", "", "Begin Due Date", false);
/* 1365 */     BeginDueDate.setTabIndex(24);
/* 1366 */     reportConfigForm.addElement(BeginDueDate);
/*      */ 
/*      */     
/* 1369 */     FormCheckBox EndDueDate = new FormCheckBox("EndDueDate", "", "End Due Date", false);
/* 1370 */     EndDueDate.setTabIndex(25);
/* 1371 */     reportConfigForm.addElement(EndDueDate);
/*      */ 
/*      */     
/* 1374 */     FormTextField ReportNameSearch = new FormTextField("ReportNameSearch", "", false, 10, 10);
/* 1375 */     ReportNameSearch.setId("ReportNameSearch");
/* 1376 */     reportConfigForm.addElement(ReportNameSearch);
/*      */     
/* 1378 */     FormTextField FileNameSearch = new FormTextField("FileNameSearch", "", false, 10, 10);
/* 1379 */     FileNameSearch.setId("FileNameSearch");
/* 1380 */     reportConfigForm.addElement(FileNameSearch);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1385 */     FormCheckBox Config = new FormCheckBox("Configuration", "", "Format", false);
/*      */     
/* 1387 */     Config.addFormEvent("onClick", "isSubconfigSelected();");
/* 1388 */     Config.setTabIndex(26);
/* 1389 */     reportConfigForm.addElement(Config);
/*      */ 
/*      */ 
/*      */     
/* 1393 */     FormCheckBox ScheduledReleases = new FormCheckBox("ScheduledReleases", "", "Scheduled Releases", false);
/* 1394 */     ScheduledReleases.setTabIndex(27);
/* 1395 */     reportConfigForm.addElement(ScheduledReleases);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1401 */     FormCheckBox SubConfig = new FormCheckBox("Subconfiguration", "", "Sub-Format", false);
/*      */     
/* 1403 */     SubConfig.addFormEvent("onClick", "isConfigSelected();");
/* 1404 */     SubConfig.setTabIndex(28);
/* 1405 */     reportConfigForm.addElement(SubConfig);
/*      */ 
/*      */ 
/*      */     
/* 1409 */     FormCheckBox AddsMovesBoth = new FormCheckBox("AddsMovesBoth", "", "Adds Moves Both", false);
/* 1410 */     AddsMovesBoth.setTabIndex(29);
/* 1411 */     reportConfigForm.addElement(AddsMovesBoth);
/*      */ 
/*      */ 
/*      */     
/* 1415 */     FormCheckBox PhysicalProductActivity = new FormCheckBox("PhysicalProductActivity", "", "Physical Product Activity", false);
/* 1416 */     PhysicalProductActivity.setTabIndex(30);
/* 1417 */     reportConfigForm.addElement(PhysicalProductActivity);
/*      */ 
/*      */ 
/*      */     
/* 1421 */     FormCheckBox DistCo = new FormCheckBox("DistCo", "", "Distribution Company", false);
/* 1422 */     DistCo.setTabIndex(31);
/* 1423 */     reportConfigForm.addElement(DistCo);
/*      */ 
/*      */ 
/*      */     
/* 1427 */     if (context.getSessionValue("NOTEPAD_REPORT_CONFIG_VISIBLE") != null) {
/* 1428 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_REPORT_CONFIG_VISIBLE"));
/*      */     }
/* 1430 */     return reportConfigForm;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean saveNew(Context context) {
/* 1437 */     User user = MilestoneSecurity.getUser(context);
/*      */     
/* 1439 */     Notepad notepad = getReportConfigNotepad(context, user.getUserId());
/* 1440 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */     
/* 1442 */     Report reportConfig = new Report();
/*      */     
/* 1444 */     Form form = buildNewForm(context);
/* 1445 */     form.addElement(new FormHidden("cmd", "report-config-edit-save-new"));
/*      */     
/* 1447 */     form.setValues(context);
/*      */ 
/*      */ 
/*      */     
/* 1451 */     reportConfig.setReportID(-1);
/*      */ 
/*      */     
/* 1454 */     reportConfig.setSubName(null);
/*      */ 
/*      */     
/* 1457 */     String reportString = form.getStringValue("ReportName");
/* 1458 */     reportConfig.setReportName(reportString);
/*      */ 
/*      */     
/* 1461 */     String descriptionString = form.getStringValue("Description");
/* 1462 */     reportConfig.setDescription(descriptionString);
/*      */ 
/*      */     
/* 1465 */     String reportType = form.getStringValue("ReportType");
/* 1466 */     int type = -1;
/*      */     
/*      */     try {
/* 1469 */       type = Integer.parseInt(reportType);
/*      */     }
/* 1471 */     catch (NumberFormatException e) {
/*      */       
/* 1473 */       System.out.println("Error converting the Report Type to integer.");
/* 1474 */       type = 0;
/*      */     } 
/* 1476 */     reportConfig.setType(type);
/*      */ 
/*      */     
/* 1479 */     String reportFormat = form.getStringValue("ReportFormat");
/* 1480 */     int format = -1;
/*      */     
/*      */     try {
/* 1483 */       format = Integer.parseInt(reportFormat);
/*      */     }
/* 1485 */     catch (NumberFormatException e) {
/*      */       
/* 1487 */       format = 0;
/*      */     } 
/* 1489 */     reportConfig.setFormat(format);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1494 */     reportConfig.setReportStatus("ACTIVE");
/*      */ 
/*      */     
/* 1497 */     String path = form.getStringValue("Path");
/* 1498 */     reportConfig.setPath(path);
/*      */ 
/*      */     
/* 1501 */     String fileName = form.getStringValue("FileName");
/* 1502 */     reportConfig.setFileName(fileName);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1507 */     String subReportName = "";
/* 1508 */     reportConfig.setSubName(subReportName);
/*      */ 
/*      */     
/* 1511 */     reportConfig.setBeginDateFlag(((FormCheckBox)form.getElement("BeginDate")).isChecked());
/* 1512 */     reportConfig.setEndDateFlag(((FormCheckBox)form.getElement("EndDate")).isChecked());
/* 1513 */     reportConfig.setRegionFlag(((FormCheckBox)form.getElement("Region")).isChecked());
/* 1514 */     reportConfig.setFamilyFlag(((FormCheckBox)form.getElement("Family")).isChecked());
/* 1515 */     reportConfig.setEnvironmentFlag(((FormCheckBox)form.getElement("Environment")).isChecked());
/* 1516 */     reportConfig.setCompanyFlag(((FormCheckBox)form.getElement("Company")).isChecked());
/* 1517 */     reportConfig.setLabelFlag(((FormCheckBox)form.getElement("Label")).isChecked());
/* 1518 */     reportConfig.setContactFlag(((FormCheckBox)form.getElement("LabelContact")).isChecked());
/*      */     
/* 1520 */     reportConfig.setCompleteKeyTaskFlag(((FormCheckBox)form.getElement("CompleteKeyTask")).isChecked());
/* 1521 */     reportConfig.setParentsOnlyFlag(((FormCheckBox)form.getElement("ParentsOnly")).isChecked());
/* 1522 */     reportConfig.setKeyTaskFlag(((FormCheckBox)form.getElement("UMLKeyTask")).isChecked());
/* 1523 */     reportConfig.setReleaseTypeFlag(((FormCheckBox)form.getElement("ReleaseType")).isChecked());
/* 1524 */     reportConfig.setArtistFlag(((FormCheckBox)form.getElement("Artist")).isChecked());
/* 1525 */     reportConfig.setStatusFlag(((FormCheckBox)form.getElement("Status")).isChecked());
/*      */     
/* 1527 */     reportConfig.setUmlDatesFlag(((FormCheckBox)form.getElement("TaskOwner")).isChecked());
/*      */     
/* 1529 */     reportConfig.setFuture1Flag(((FormCheckBox)form.getElement("UmlContact")).isChecked());
/* 1530 */     reportConfig.setFuture2Flag(((FormCheckBox)form.getElement("Future2")).isChecked());
/* 1531 */     reportConfig.setBeginDueDateFlag(((FormCheckBox)form.getElement("BeginDueDate")).isChecked());
/* 1532 */     reportConfig.setEndDueDateFlag(((FormCheckBox)form.getElement("EndDueDate")).isChecked());
/* 1533 */     reportConfig.setConfiguration(((FormCheckBox)form.getElement("Configuration")).isChecked());
/* 1534 */     reportConfig.setScheduledReleasesFlag(((FormCheckBox)form.getElement("ScheduledReleases")).isChecked());
/* 1535 */     reportConfig.setAddsMovesBoth(((FormCheckBox)form.getElement("AddsMovesBoth")).isChecked());
/* 1536 */     reportConfig.setPhysicalProductActivity(((FormCheckBox)form.getElement("PhysicalProductActivity")).isChecked());
/* 1537 */     reportConfig.setDistCo(((FormCheckBox)form.getElement("DistCo")).isChecked());
/*      */ 
/*      */     
/* 1540 */     String productTypeStr = ((FormRadioButtonGroup)form.getElement("ProductType")).getStringValue();
/* 1541 */     int productTypeInt = 0;
/* 1542 */     if (productTypeStr.equals("Physical")) {
/* 1543 */       productTypeInt = 0;
/*      */     }
/* 1545 */     else if (productTypeStr.equals("Digital")) {
/* 1546 */       productTypeInt = 1;
/*      */     }
/* 1548 */     else if (productTypeStr.equals("Both")) {
/* 1549 */       productTypeInt = 2;
/*      */     } 
/* 1551 */     reportConfig.setProductType(productTypeInt);
/*      */     
/* 1553 */     if (!ReportConfigManager.getInstance().isDuplicate(reportConfig)) {
/*      */       
/* 1555 */       if (!form.isUnchanged()) {
/*      */         
/* 1557 */         FormValidation formValidation = form.validate();
/* 1558 */         if (formValidation.isGood())
/*      */         {
/* 1560 */           Report savedReportConfig = ReportConfigManager.getInstance().saveReportConfig(reportConfig, user.getUserId());
/*      */           
/* 1562 */           context.putSessionValue("ReportConfig", savedReportConfig);
/*      */           
/* 1564 */           context.putDelivery("Form", form);
/*      */           
/* 1566 */           if (notepad.getAllContents() == null || notepad.getAllContents().size() < 1) {
/* 1567 */             notepad.setSearchQuery("");
/*      */           }
/*      */           
/* 1570 */           notepad.setAllContents(null);
/* 1571 */           notepad.newSelectedReset();
/* 1572 */           notepad = getReportConfigNotepad(context, MilestoneSecurity.getUser(context).getUserId());
/* 1573 */           notepad.setSelected(reportConfig);
/*      */         }
/*      */         else
/*      */         {
/* 1577 */           context.putDelivery("FormValidation", formValidation);
/* 1578 */           form.addElement(new FormHidden("OrderBy", "", true));
/* 1579 */           context.putDelivery("Form", form);
/* 1580 */           return context.includeJSP("report-config-editor.jsp");
/*      */         }
/*      */       
/*      */       } 
/*      */     } else {
/*      */       
/* 1586 */       context.putDelivery("AlertMessage", 
/* 1587 */           MilestoneMessage.getMessage(5, 
/*      */             
/* 1589 */             new String[] { "Report Configuration", reportConfig.getReportName() }));
/*      */     } 
/*      */ 
/*      */     
/* 1593 */     Notepad reportNotepad = MilestoneHelper.getNotepadFromSession(3, context);
/* 1594 */     if (reportNotepad != null) {
/*      */       
/* 1596 */       reportNotepad.setAllContents(null);
/* 1597 */       reportNotepad.setSelected(null);
/*      */     } 
/*      */     
/* 1600 */     return edit(context);
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
/*      */   private boolean goToBlank(Context context) {
/* 1612 */     context.putDelivery("isNotePadVisible", new Boolean(MilestoneHelper.getNotePadVisiblitiy(17, context)));
/*      */     
/* 1614 */     Form form = new Form(this.application, "form", this.application.getInfrastructure().getServletURL(), "Post");
/* 1615 */     form.addElement(new FormHidden("cmd", "report-config-editor"));
/* 1616 */     form.addElement(new FormHidden("OrderBy", "", true));
/*      */ 
/*      */     
/* 1619 */     FormTextField ReportNameSearch = new FormTextField("ReportNameSearch", "", false, 10, 10);
/* 1620 */     ReportNameSearch.setId("ReportNameSearch");
/* 1621 */     form.addElement(ReportNameSearch);
/*      */     
/* 1623 */     FormTextField FileNameSearch = new FormTextField("FileNameSearch", "", false, 10, 10);
/* 1624 */     FileNameSearch.setId("FileNameSearch");
/* 1625 */     form.addElement(FileNameSearch);
/*      */     
/* 1627 */     context.putDelivery("Form", form);
/*      */     
/* 1629 */     return context.includeJSP("blank-report-config-editor.jsp");
/*      */   }
/*      */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\ReportConfigHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */