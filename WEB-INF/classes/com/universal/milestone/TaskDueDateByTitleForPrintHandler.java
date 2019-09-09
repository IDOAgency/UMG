/*      */ package WEB-INF.classes.com.universal.milestone;
/*      */ 
/*      */ import com.techempower.ComponentLog;
/*      */ import com.techempower.gemini.Context;
/*      */ import com.techempower.gemini.FormCheckBox;
/*      */ import com.techempower.gemini.GeminiApplication;
/*      */ import com.universal.milestone.Cache;
/*      */ import com.universal.milestone.Company;
/*      */ import com.universal.milestone.Day;
/*      */ import com.universal.milestone.Division;
/*      */ import com.universal.milestone.Environment;
/*      */ import com.universal.milestone.Family;
/*      */ import com.universal.milestone.Form;
/*      */ import com.universal.milestone.Genre;
/*      */ import com.universal.milestone.JdbcConnector;
/*      */ import com.universal.milestone.Label;
/*      */ import com.universal.milestone.MilestoneHelper;
/*      */ import com.universal.milestone.MilestoneHelper_2;
/*      */ import com.universal.milestone.PrefixCode;
/*      */ import com.universal.milestone.ProductCategory;
/*      */ import com.universal.milestone.ReleaseType;
/*      */ import com.universal.milestone.ReportSelectionsHelper;
/*      */ import com.universal.milestone.Schedule;
/*      */ import com.universal.milestone.ScheduledTask;
/*      */ import com.universal.milestone.SecureHandler;
/*      */ import com.universal.milestone.Selection;
/*      */ import com.universal.milestone.SelectionManager;
/*      */ import com.universal.milestone.SelectionStatus;
/*      */ import com.universal.milestone.TaskDueDateByTitleForPrintHandler;
/*      */ import com.universal.milestone.User;
/*      */ import com.universal.milestone.UserManager;
/*      */ import inetsoft.report.CompositeSheet;
/*      */ import inetsoft.report.SectionBand;
/*      */ import inetsoft.report.SeparatorElement;
/*      */ import inetsoft.report.StyleSheet;
/*      */ import inetsoft.report.XStyleSheet;
/*      */ import inetsoft.report.io.Builder;
/*      */ import inetsoft.report.lens.DefaultSectionLens;
/*      */ import inetsoft.report.lens.DefaultTableLens;
/*      */ import java.awt.Color;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Font;
/*      */ import java.awt.Rectangle;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.InputStream;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.Calendar;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.Vector;
/*      */ import javax.servlet.http.HttpServletResponse;
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
/*      */ public class TaskDueDateByTitleForPrintHandler
/*      */   extends SecureHandler
/*      */ {
/*      */   public static final String COMPONENT_CODE = "hTaskDueBySel";
/*      */   public GeminiApplication application;
/*      */   public ComponentLog log;
/*      */   
/*      */   public TaskDueDateByTitleForPrintHandler(GeminiApplication application) {
/*   74 */     this.application = application;
/*   75 */     this.log = application.getLog("hTaskDueBySel");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   83 */   public String getDescription() { return "Sub Report"; }
/*      */ 
/*      */ 
/*      */   
/*      */   protected static StyleSheet fillTaskDueDateByTitleForPrint(Context context, String reportPath, ComponentLog log) {
/*   88 */     Color SHADED_AREA_COLOR = Color.lightGray;
/*   89 */     int COL_LINE_STYLE = 4097;
/*   90 */     int HEADER_FONT_SIZE = 12;
/*   91 */     int NUM_COLUMNS = 9;
/*      */     
/*   93 */     double ldLineVal = 0.3D;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   98 */     int separatorLineStyle = 266240;
/*   99 */     Color separatorLineColor = Color.black;
/*      */ 
/*      */     
/*  102 */     int tableHeaderLineStyle = 266240;
/*      */     
/*  104 */     Color tableHeaderLineColor = Color.black;
/*      */ 
/*      */ 
/*      */     
/*  108 */     int tableRowLineStyle = 4097;
/*      */     
/*  110 */     Color tableRowLineColor = new Color(208, 206, 206, 0);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  116 */       HttpServletResponse sresponse = context.getResponse();
/*  117 */       context.putDelivery("status", new String("start_gathering"));
/*  118 */       context.includeJSP("status.jsp", "hiddenFrame");
/*  119 */       sresponse.setContentType("text/plain");
/*  120 */       sresponse.flushBuffer();
/*      */     }
/*  122 */     catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */     
/*  126 */     Vector selections = getTaskDueSelectionsForReport(context, log);
/*      */ 
/*      */     
/*      */     try {
/*  130 */       HttpServletResponse sresponse = context.getResponse();
/*  131 */       context.putDelivery("status", new String("start_report"));
/*  132 */       context.putDelivery("percent", new String("10"));
/*  133 */       context.includeJSP("status.jsp", "hiddenFrame");
/*  134 */       sresponse.setContentType("text/plain");
/*  135 */       sresponse.flushBuffer();
/*      */     }
/*  137 */     catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */     
/*  141 */     int numSelections = selections.size();
/*      */ 
/*      */     
/*  144 */     MilestoneHelper.setSelectionSorting(selections, 10);
/*  145 */     Collections.sort(selections);
/*      */ 
/*      */     
/*  148 */     MilestoneHelper.setSelectionSorting(selections, 6);
/*  149 */     Collections.sort(selections);
/*      */ 
/*      */ 
/*      */     
/*  153 */     if (numSelections == 0) {
/*  154 */       return null;
/*      */     }
/*      */     
/*  157 */     StyleSheet[] sheets = new StyleSheet[numSelections];
/*      */ 
/*      */     
/*      */     try {
/*  161 */       DefaultTableLens table_contents = null;
/*  162 */       DefaultTableLens rowCountTable = null;
/*  163 */       DefaultTableLens columnHeaderTable = null;
/*  164 */       DefaultTableLens subTable = null;
/*  165 */       DefaultTableLens headerTableLens = null;
/*      */       
/*  167 */       rowCountTable = new DefaultTableLens(2, 10000);
/*      */ 
/*      */ 
/*      */       
/*  171 */       for (int i = 0; i < numSelections; i++)
/*      */       {
/*  173 */         InputStream input = new FileInputStream(String.valueOf(reportPath) + "\\taskDueDateByTitleId.xml");
/*  174 */         XStyleSheet report = (XStyleSheet)Builder.getBuilder(1, input).read(null);
/*      */         
/*  176 */         SectionBand hbandType = new SectionBand(report);
/*  177 */         SectionBand hbandCategory = new SectionBand(report);
/*  178 */         SectionBand hbandDate = new SectionBand(report);
/*  179 */         SectionBand body = new SectionBand(report);
/*  180 */         SectionBand tbody = new SectionBand(report);
/*  181 */         SectionBand footer = new SectionBand(report);
/*  182 */         SectionBand spacer = new SectionBand(report);
/*  183 */         DefaultSectionLens group = null;
/*      */         
/*  185 */         footer.setVisible(true);
/*  186 */         footer.setHeight(0.1F);
/*  187 */         footer.setShrinkToFit(false);
/*  188 */         footer.setBottomBorder(0);
/*      */         
/*  190 */         spacer.setVisible(true);
/*  191 */         spacer.setHeight(0.05F);
/*  192 */         spacer.setShrinkToFit(false);
/*  193 */         spacer.setBottomBorder(0);
/*      */ 
/*      */         
/*  196 */         SeparatorElement topSeparator = (SeparatorElement)report.getElement("separator_top");
/*  197 */         SeparatorElement bottomHeaderSeparator = (SeparatorElement)report.getElement("separator_bottom_header");
/*  198 */         SeparatorElement bottomSeparator = (SeparatorElement)report.getElement("separator_bottom");
/*  199 */         if (topSeparator != null) {
/*      */           
/*  201 */           topSeparator.setStyle(separatorLineStyle);
/*  202 */           topSeparator.setForeground(separatorLineColor);
/*      */         } 
/*  204 */         if (bottomHeaderSeparator != null) {
/*      */           
/*  206 */           bottomHeaderSeparator.setStyle(separatorLineStyle);
/*  207 */           bottomHeaderSeparator.setForeground(separatorLineColor);
/*      */         } 
/*  209 */         if (bottomSeparator != null) {
/*      */           
/*  211 */           bottomSeparator.setStyle(separatorLineStyle);
/*  212 */           bottomSeparator.setForeground(separatorLineColor);
/*      */         } 
/*      */ 
/*      */         
/*  216 */         Form reportForm = (Form)context.getSessionValue("reportForm");
/*      */         
/*  218 */         Calendar beginDueDate = (reportForm.getStringValue("beginDueDate") != null && 
/*  219 */           reportForm.getStringValue("beginDueDate").length() > 0) ? 
/*  220 */           MilestoneHelper.getDate(reportForm.getStringValue("beginDueDate")) : null;
/*      */         
/*  222 */         Calendar endDueDate = (reportForm.getStringValue("endDueDate") != null && 
/*  223 */           reportForm.getStringValue("endDueDate").length() > 0) ? 
/*  224 */           MilestoneHelper.getDate(reportForm.getStringValue("endDueDate")) : null;
/*      */         
/*  226 */         report.setElement("startdate", MilestoneHelper.getFormatedDate(beginDueDate));
/*  227 */         report.setElement("enddate", MilestoneHelper.getFormatedDate(endDueDate));
/*      */ 
/*      */         
/*  230 */         SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
/*  231 */         String todayLong = formatter.format(new Date());
/*  232 */         report.setElement("bottomdate", todayLong);
/*      */ 
/*      */         
/*  235 */         String strUmlKey = (reportForm.getStringValue("umlKeyTask") != null) ? reportForm.getStringValue("umlKeyTask") : "";
/*      */ 
/*      */         
/*  238 */         Selection sel = (Selection)selections.elementAt(i);
/*      */ 
/*      */         
/*  241 */         if (sel != null) {
/*      */ 
/*      */           
/*  244 */           String selFamily = (sel.getFamily() != null && sel.getFamily().getName() != null) ? 
/*  245 */             sel.getFamily().getName() : "";
/*      */ 
/*      */           
/*  248 */           String selCompany = (sel.getCompany() != null && sel.getCompany().getName() != null) ? 
/*  249 */             sel.getCompany().getName() : "";
/*      */ 
/*      */           
/*  252 */           int region = (sel.getEnvironment() != null && sel.getEnvironment().getDistribution() >= 0) ? sel.getEnvironment().getDistribution() : -1;
/*  253 */           String selRegion = "";
/*      */           
/*  255 */           if (region == 0) {
/*  256 */             selRegion = "West";
/*  257 */           } else if (region == 1) {
/*  258 */             selRegion = "East";
/*      */           } 
/*      */           
/*  261 */           String selProject = (sel.getProjectID() != null) ? sel.getProjectID() : "";
/*      */ 
/*      */           
/*  264 */           String selPD = sel.getPressAndDistribution() ? "Yes" : "";
/*      */ 
/*      */           
/*  267 */           String selDivision = (sel.getDivision() != null && sel.getDivision().getName() != null) ? 
/*  268 */             sel.getDivision().getName() : "";
/*      */ 
/*      */           
/*  271 */           String selLabel = (sel.getLabel() != null && sel.getLabel().getName() != null) ? 
/*  272 */             sel.getLabel().getName() : "";
/*      */ 
/*      */           
/*  275 */           String selId = (sel.getSelectionNo() != null) ? sel.getSelectionNo() : "";
/*      */ 
/*      */ 
/*      */           
/*  279 */           String selUpc = (sel.getUpc() != null) ? sel.getUpc() : "";
/*      */ 
/*      */           
/*  282 */           selUpc = MilestoneHelper_2.getRMSReportFormat(selUpc, "UPC", sel.getIsDigital());
/*      */ 
/*      */           
/*  285 */           String selConfig = (sel.getSelectionSubConfig() != null && sel.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation() != null) ? 
/*  286 */             sel.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation() : "";
/*      */ 
/*      */           
/*  289 */           String selStreetDate = "";
/*  290 */           selStreetDate = MilestoneHelper.getFormatedDate(sel.getStreetDate());
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  295 */           String statusString = "";
/*  296 */           SelectionStatus status = sel.getSelectionStatus();
/*      */           
/*  298 */           if (status != null) {
/*  299 */             statusString = (status.getName() == null) ? "" : status.getName();
/*      */           }
/*  301 */           if (!statusString.equalsIgnoreCase("ACTIVE"))
/*      */           {
/*  303 */             if (!statusString.equalsIgnoreCase("In The Works")) {
/*  304 */               selStreetDate = String.valueOf(statusString) + " " + selStreetDate;
/*      */             } else {
/*  306 */               selStreetDate = "ITW " + selStreetDate;
/*      */             } 
/*      */           }
/*      */ 
/*      */           
/*  311 */           String selArtist = (sel.getArtist() != null) ? sel.getArtist() : "";
/*      */ 
/*      */           
/*  314 */           String selTitle = (sel.getTitle() != null) ? sel.getTitle() : "";
/*      */ 
/*      */           
/*  317 */           String selTitleId = "";
/*  318 */           selTitleId = (sel.getTitleID() != null) ? sel.getTitleID() : "";
/*      */           
/*  320 */           String selFamilyLabel = String.valueOf(selFamily) + "/" + selLabel;
/*      */           
/*  322 */           report.setElement("artist", selArtist);
/*  323 */           report.setElement("title", selTitle);
/*  324 */           report.setElement("project", selProject);
/*  325 */           report.setElement("titleId", selTitleId);
/*  326 */           report.setElement("company", selCompany);
/*  327 */           report.setElement("familylabel", selFamilyLabel);
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  332 */           Schedule schedule = sel.getSchedule();
/*      */           
/*  334 */           Vector scheduledTasks = new Vector();
/*  335 */           if (schedule != null) {
/*      */             
/*  337 */             scheduledTasks = schedule.getTasks();
/*      */ 
/*      */             
/*  340 */             if (scheduledTasks != null && strUmlKey.equalsIgnoreCase("label"))
/*      */             {
/*  342 */               scheduledTasks = MilestoneHelper.filterUmlTasks(scheduledTasks, false);
/*      */             }
/*      */             
/*  345 */             if (scheduledTasks != null && strUmlKey.equalsIgnoreCase("uml"))
/*      */             {
/*  347 */               scheduledTasks = MilestoneHelper.filterUmlTasks(scheduledTasks, true);
/*      */             }
/*      */             
/*  350 */             if (scheduledTasks == null) {
/*  351 */               scheduledTasks = new Vector();
/*      */             }
/*      */           } 
/*      */           
/*  355 */           int commentCount = MilestoneHelper.countTasksWithComments(scheduledTasks);
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  360 */           table_contents = new DefaultTableLens(1, 9);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  367 */           table_contents.setColAlignment(0, 1);
/*  368 */           table_contents.setColAlignment(1, 1);
/*  369 */           table_contents.setColAlignment(2, 1);
/*  370 */           table_contents.setColAlignment(3, 1);
/*  371 */           table_contents.setColAlignment(4, 4);
/*  372 */           table_contents.setColAlignment(6, 1);
/*  373 */           table_contents.setColAlignment(7, 12);
/*      */           
/*  375 */           table_contents.setColBorderColor(Color.black);
/*  376 */           table_contents.setRowBorderColor(Color.black);
/*  377 */           table_contents.setRowBorder(0);
/*  378 */           table_contents.setColBorder(0);
/*      */           
/*  380 */           table_contents.setRowBorder(1, 4097);
/*      */ 
/*      */ 
/*      */           
/*  384 */           table_contents.setColWidth(0, 35);
/*  385 */           table_contents.setLineWrap(0, 0, false);
/*  386 */           table_contents.setLineWrap(0, 1, false);
/*  387 */           table_contents.setColWidth(1, 82);
/*  388 */           table_contents.setColWidth(2, 55);
/*  389 */           table_contents.setColWidth(3, 45);
/*      */           
/*  391 */           table_contents.setColWidth(4, 45);
/*  392 */           table_contents.setColWidth(5, 1);
/*  393 */           table_contents.setColWidth(6, 4);
/*      */           
/*  395 */           table_contents.setColWidth(7, 52);
/*  396 */           table_contents.setColWidth(8, 1);
/*      */ 
/*      */ 
/*      */           
/*  400 */           table_contents.setHeaderRowCount(1);
/*  401 */           table_contents.setObject(0, 0, "Description /");
/*  402 */           table_contents.setFont(0, 0, new Font("Arial", 1, 10));
/*      */           
/*  404 */           table_contents.setObject(0, 1, "Comments");
/*  405 */           table_contents.setFont(0, 1, new Font("Arial", 3, 10));
/*      */           
/*  407 */           table_contents.setObject(0, 2, "Task Owner");
/*  408 */           table_contents.setFont(0, 2, new Font("Arial", 1, 10));
/*  409 */           table_contents.setObject(0, 3, "Department");
/*  410 */           table_contents.setFont(0, 3, new Font("Arial", 1, 10));
/*      */           
/*  412 */           table_contents.setObject(0, 4, "Date Due");
/*  413 */           table_contents.setFont(0, 4, new Font("Arial", 1, 10));
/*  414 */           table_contents.setSpan(0, 4, new Dimension(2, 1));
/*      */           
/*  416 */           table_contents.setObject(0, 7, "Complete");
/*  417 */           table_contents.setSpan(0, 7, new Dimension(2, 1));
/*  418 */           table_contents.setFont(0, 7, new Font("Arial", 1, 10));
/*      */ 
/*      */           
/*  421 */           table_contents.setRowBorder(-1, 0);
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  426 */           hbandType.addTable(table_contents, new Rectangle(0, 0, 800, 30));
/*      */ 
/*      */           
/*  429 */           int nextRow = 0;
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
/*  481 */           String saveSelectionNo = ";;;;";
/*  482 */           boolean printTitleId = false;
/*      */ 
/*      */           
/*  485 */           for (int j = 0; j < scheduledTasks.size(); j++) {
/*      */             String dueDateHolidayFlg, taskDueDate;
/*      */             
/*  488 */             ScheduledTask task = (ScheduledTask)scheduledTasks.get(j);
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  493 */             if (!task.getSelectionNo().equalsIgnoreCase(saveSelectionNo)) {
/*  494 */               printTitleId = true;
/*  495 */               nextRow = 0;
/*      */ 
/*      */               
/*  498 */               headerTableLens = new DefaultTableLens(2, 9);
/*  499 */               headerTableLens.setColWidth(nextRow, 3);
/*  500 */               headerTableLens.setColAlignment(nextRow, 1);
/*  501 */               headerTableLens.setColWidth(1, 3);
/*  502 */               headerTableLens.setColWidth(3, 4);
/*      */               
/*  504 */               headerTableLens.setColWidth(3, 1);
/*  505 */               headerTableLens.setColAlignment(3, 1);
/*  506 */               headerTableLens.setColAlignment(4, 1);
/*  507 */               headerTableLens.setColWidth(4, 5);
/*      */               
/*  509 */               headerTableLens.setColWidth(5, 1);
/*  510 */               headerTableLens.setColWidth(6, 3);
/*  511 */               headerTableLens.setColAlignment(6, 4);
/*  512 */               headerTableLens.setColWidth(8, 2);
/*      */               
/*  514 */               headerTableLens.setColBorder(-1, 4097);
/*      */               
/*  516 */               headerTableLens.setColBorder(nextRow, 0);
/*  517 */               headerTableLens.setColBorder(1, 0);
/*  518 */               headerTableLens.setColBorder(2, 0);
/*  519 */               headerTableLens.setColBorder(3, 0);
/*  520 */               headerTableLens.setColBorder(4, 0);
/*  521 */               headerTableLens.setColBorder(5, 0);
/*  522 */               headerTableLens.setColBorder(6, 0);
/*  523 */               headerTableLens.setColBorder(7, 0);
/*      */               
/*  525 */               headerTableLens.setColBorderColor(Color.black);
/*  526 */               headerTableLens.setRowBorderColor(Color.black);
/*  527 */               headerTableLens.setRowBackground(nextRow, Color.lightGray);
/*      */               
/*  529 */               headerTableLens.setFont(nextRow, 0, new Font("Arial", 3, 9));
/*  530 */               headerTableLens.setFont(nextRow, 1, new Font("Arial", 0, 9));
/*  531 */               headerTableLens.setFont(nextRow, 3, new Font("Arial", 3, 9));
/*  532 */               headerTableLens.setFont(nextRow, 4, new Font("Arial", 0, 9));
/*  533 */               headerTableLens.setFont(nextRow, 6, new Font("Arial", 3, 9));
/*  534 */               headerTableLens.setFont(nextRow, 8, new Font("Arial", 0, 9));
/*      */               
/*  536 */               headerTableLens.setObject(nextRow, 0, "Local Prod #:");
/*  537 */               headerTableLens.setObject(nextRow, 1, task.getSelectionNo().trim());
/*  538 */               headerTableLens.setObject(nextRow, 3, "UPC");
/*      */               
/*  540 */               String upc = task.getUpc().trim();
/*  541 */               upc = MilestoneHelper_2.getRMSReportFormat(upc, "UPC", sel.getIsDigital());
/*  542 */               headerTableLens.setObject(nextRow, 4, upc);
/*      */               
/*  544 */               headerTableLens.setObject(nextRow, 6, "Street Date");
/*      */               
/*  546 */               String streetDate = (task.getStreetDate() != null) ? 
/*  547 */                 MilestoneHelper.getFormatedDate(task.getStreetDate()) : "";
/*  548 */               headerTableLens.setObject(nextRow, 8, streetDate);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  554 */               saveSelectionNo = task.getSelectionNo();
/*      */             } 
/*      */ 
/*      */ 
/*      */             
/*  559 */             nextRow = 0;
/*      */             
/*  561 */             boolean taskHasComment = false;
/*      */             
/*  563 */             String taskComments = (task.getComments() != null) ? task.getComments().trim() : "";
/*      */ 
/*      */             
/*  566 */             if (!taskComments.equals("") && !taskComments.equalsIgnoreCase("null")) {
/*  567 */               taskHasComment = true;
/*      */             }
/*  569 */             if (taskHasComment) {
/*      */               
/*  571 */               subTable = new DefaultTableLens(2, 9);
/*      */             
/*      */             }
/*      */             else {
/*      */ 
/*      */               
/*  577 */               subTable = new DefaultTableLens(1, 9);
/*      */             } 
/*      */ 
/*      */ 
/*      */             
/*  582 */             subTable.setColAlignment(0, 1);
/*  583 */             subTable.setColAlignment(1, 1);
/*  584 */             subTable.setColAlignment(2, 1);
/*  585 */             subTable.setColAlignment(3, 1);
/*  586 */             subTable.setColAlignment(4, 4);
/*  587 */             subTable.setColAlignment(6, 1);
/*  588 */             subTable.setColAlignment(7, 12);
/*      */             
/*  590 */             subTable.setColWidth(0, 35);
/*  591 */             subTable.setLineWrap(0, 0, false);
/*  592 */             subTable.setLineWrap(0, 1, false);
/*  593 */             subTable.setColWidth(1, 82);
/*  594 */             subTable.setColWidth(2, 55);
/*  595 */             subTable.setColWidth(3, 45);
/*      */             
/*  597 */             subTable.setColWidth(4, 45);
/*  598 */             subTable.setColWidth(5, 1);
/*  599 */             subTable.setColWidth(6, 4);
/*      */             
/*  601 */             subTable.setColWidth(7, 52);
/*  602 */             subTable.setColWidth(8, 1);
/*      */             
/*  604 */             subTable.setColBorderColor(Color.black);
/*  605 */             subTable.setRowBorderColor(Color.black);
/*  606 */             subTable.setRowBorder(0);
/*  607 */             subTable.setColBorder(0);
/*      */             
/*  609 */             subTable.setRowBorder(2, 4097);
/*      */ 
/*      */             
/*  612 */             String taskName = (task.getName() != null) ? task.getName().trim() : "";
/*      */ 
/*      */             
/*  615 */             String taskOwner = (task.getOwner() != null && task.getOwner().getName() != null) ? 
/*  616 */               task.getOwner().getName() : "";
/*      */ 
/*      */ 
/*      */             
/*  620 */             String taskDepartment = (task.getDepartment() != null && !task.getDepartment().equalsIgnoreCase("-1") && !task.getDepartment().equalsIgnoreCase("null")) ? 
/*  621 */               task.getDepartment() : "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  627 */             if (task.getDueDate() != null) {
/*      */               
/*  629 */               taskDueDate = MilestoneHelper.getFormatedDate(task.getDueDate());
/*  630 */               dueDateHolidayFlg = MilestoneHelper.getDayType(sel.getCalendarGroup(), task.getDueDate());
/*      */             }
/*      */             else {
/*      */               
/*  634 */               taskDueDate = "";
/*  635 */               dueDateHolidayFlg = "";
/*      */             } 
/*      */ 
/*      */             
/*  639 */             String taskStatus = task.getScheduledTaskStatus();
/*      */ 
/*      */             
/*  642 */             String taskCompletionDate = (task.getCompletionDate() != null) ? 
/*  643 */               MilestoneHelper.getFormatedDate(task.getCompletionDate()) : "";
/*      */             
/*  645 */             if (taskStatus.equalsIgnoreCase("N/A")) {
/*  646 */               taskCompletionDate = "N/A";
/*      */             }
/*      */ 
/*      */             
/*  650 */             subTable.setObject(nextRow, 0, taskName);
/*  651 */             subTable.setSpan(nextRow, 0, new Dimension(2, 1));
/*  652 */             subTable.setObject(nextRow, 2, taskOwner);
/*  653 */             subTable.setObject(nextRow, 3, taskDepartment);
/*  654 */             subTable.setObject(nextRow, 4, taskDueDate);
/*  655 */             subTable.setObject(nextRow, 6, dueDateHolidayFlg);
/*  656 */             subTable.setObject(nextRow, 7, taskCompletionDate);
/*      */ 
/*      */             
/*  659 */             subTable.setRowFont(nextRow, new Font("Arial", 0, 9));
/*      */ 
/*      */             
/*  662 */             if (dueDateHolidayFlg != "") {
/*      */               
/*  664 */               subTable.setFont(nextRow, 4, new Font("Arial", 3, 9));
/*  665 */               subTable.setFont(nextRow, 6, new Font("Arial", 3, 9));
/*      */             } 
/*      */             
/*  668 */             if (taskHasComment) {
/*      */               
/*  670 */               nextRow++;
/*  671 */               subTable.setSpan(nextRow, 0, new Dimension(2, 1));
/*  672 */               subTable.setObject(nextRow, 0, taskComments);
/*  673 */               subTable.setRowFont(nextRow, new Font("Arial", 2, 9));
/*  674 */               subTable.setRowBorder(nextRow - 1, 0);
/*      */             } 
/*      */ 
/*      */ 
/*      */             
/*  679 */             subTable.setRowBorder(nextRow, tableRowLineStyle);
/*  680 */             subTable.setRowBorderColor(nextRow, tableRowLineColor);
/*      */ 
/*      */             
/*  683 */             if (printTitleId) {
/*  684 */               tbody = new SectionBand(report);
/*  685 */               tbody.setHeight(0.3F);
/*  686 */               tbody.addTable(headerTableLens, new Rectangle(800, 30));
/*  687 */               tbody.setVisible(true);
/*  688 */               group = new DefaultSectionLens(null, group, tbody);
/*      */             } 
/*      */ 
/*      */ 
/*      */             
/*  693 */             body = new SectionBand(report);
/*  694 */             double lfLineCount = 1.5D;
/*  695 */             body.setHeight(1.3F);
/*      */             
/*  697 */             body.addTable(subTable, new Rectangle(800, 800));
/*  698 */             body.setBottomBorder(0);
/*  699 */             body.setTopBorder(0);
/*  700 */             body.setShrinkToFit(true);
/*  701 */             body.setVisible(true);
/*      */             
/*  703 */             group = new DefaultSectionLens(null, group, body);
/*      */             
/*  705 */             printTitleId = false;
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/*  710 */           group = new DefaultSectionLens(hbandType, group, null);
/*  711 */           report.addSection(group, rowCountTable);
/*  712 */           group = null;
/*      */         } 
/*      */ 
/*      */         
/*  716 */         sheets[i] = report;
/*      */       }
/*      */     
/*      */     }
/*  720 */     catch (Exception e) {
/*      */       
/*  722 */       System.out.println(">>>>>>>>ReportHandler.fillEntProdStatusForPrint(): exception: " + e);
/*      */     } 
/*      */ 
/*      */     
/*  726 */     return new CompositeSheet(sheets);
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
/*      */   public static Vector getTaskDueSelectionsForReport(Context context, ComponentLog log) {
/*  747 */     User user = (User)context.getSessionValue("user");
/*  748 */     int userId = 0;
/*  749 */     if (user != null)
/*  750 */       userId = user.getUserId(); 
/*  751 */     Form reportForm = (Form)context.getSessionValue("reportForm");
/*      */     
/*  753 */     String beginDate = "";
/*  754 */     beginDate = (reportForm.getStringValue("beginDate") != null && reportForm.getStringValue("beginDate").length() > 0) ? reportForm.getStringValue("beginDate") : "";
/*      */     
/*  756 */     String endDate = "";
/*  757 */     endDate = (reportForm.getStringValue("endDate") != null && reportForm.getStringValue("endDate").length() > 0) ? reportForm.getStringValue("endDate") : "";
/*      */     
/*  759 */     String beginDueDate = "";
/*  760 */     beginDueDate = (reportForm.getStringValue("beginDueDate") != null && reportForm.getStringValue("beginDueDate").length() > 0) ? reportForm.getStringValue("beginDueDate") : "";
/*      */     
/*  762 */     String endDueDate = "";
/*  763 */     endDueDate = (reportForm.getStringValue("endDueDate") != null && reportForm.getStringValue("endDueDate").length() > 0) ? reportForm.getStringValue("endDueDate") : "";
/*      */     
/*  765 */     StringBuffer dueDateQuery = new StringBuffer(200);
/*      */     
/*  767 */     if (!beginDueDate.equalsIgnoreCase("")) {
/*  768 */       dueDateQuery.append("AND due_date >= '" + MilestoneHelper.escapeSingleQuotes(beginDueDate) + "'");
/*      */     }
/*  770 */     if (!endDueDate.equalsIgnoreCase(""))
/*      */     {
/*  772 */       if (!beginDueDate.equalsIgnoreCase("")) {
/*  773 */         dueDateQuery.append(" AND due_date <= '" + MilestoneHelper.escapeSingleQuotes(endDueDate) + "'");
/*      */       } else {
/*  775 */         dueDateQuery.append(" due_date <= '" + MilestoneHelper.escapeSingleQuotes(endDueDate) + "'");
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  782 */     Vector companies = MilestoneHelper.getUserCompanies(context);
/*  783 */     Company company = null;
/*  784 */     Vector precache = new Vector();
/*  785 */     StringBuffer query = new StringBuffer();
/*  786 */     query.append("SELECT header.release_id, header.title_id, header.selection_no, header.prefix,   pfm.units_per_set, detail.due_date AS taskDue, detail.comments AS taskComments,   detail.owner AS taskOwner, detail.completion_date AS taskComplete,   detail.status AS taskStatus, detail.day_of_week AS taskDayOfWeek,   detail.weeks_to_release AS taskWeeksToRelease, detail.key_task_indicator AS taskKey,    detail.vendor AS taskVendor, detail.authorization_name AS taskAuthName,   detail.authorization_date AS taskAuthDate, detail.last_updated_ck, header.project_no,   header.title, header.artist_first_name, header.artist_last_name, header.artist,   header.side_a_title, header.side_b_title, header.product_line, header.release_type,   header.configuration, header.sub_configuration, header.UPC, header.price_code, header.genre,   header.family_id, header.company_id, header.environment_id, header.division_id, header.label_id, header.street_date,   header.international_date, header.coordinator_contacts, header.contact_id, header.status,   header.hold_indicator, header.hold_reason, header.comments, header.special_pkg_indicator,   header.pd_indicator, header.packaging, header.impact_date, header.last_updated_on,   header.last_updated_by, header.entered_on, mfg.uml_id, mfg.plant, mfg.distribution,   mfg.order_qty, mfg.complete_qty, mfg.order_comments, Task.task_id, Task.department,   Task.name, Task.abbrev_id  FROM vi_release_header header with (nolock) LEFT JOIN vi_release_subdetail mfg with (nolock) ON header.[release_id] = mfg.[release_id] LEFT JOIN vi_release_detail detail with (nolock) ON header.[release_id] = detail.[release_id] " + 
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
/*  808 */         dueDateQuery.toString() + 
/*  809 */         " INNER JOIN vi_Task task with (nolock)" + 
/*  810 */         " ON detail.[task_id] = task.[task_id]" + 
/*  811 */         " LEFT JOIN vi_pfm_selection pfm with (nolock)" + 
/*  812 */         " ON header.[release_id] = pfm.[release_id]" + 
/*  813 */         " WHERE ");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  819 */     String strLabelContact = (reportForm.getStringValue("contact") != null) ? reportForm.getStringValue("contact") : "";
/*  820 */     String strUmlContact = (reportForm.getStringValue("umlContact") != null) ? reportForm.getStringValue("umlContact") : "";
/*  821 */     String strReleaseType = (reportForm.getStringValue("releaseType") != null) ? reportForm.getStringValue("releaseType") : "";
/*  822 */     String strStatus = (reportForm.getStringValue("status") != null) ? reportForm.getStringValue("status") : "";
/*  823 */     String strArtist = (reportForm.getStringValue("artist") != null) ? reportForm.getStringValue("artist") : "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  830 */     String[] strFamily = ReportSelectionsHelper.getMultiSelectionListValues("family", reportForm);
/*  831 */     String[] strEnvironment = ReportSelectionsHelper.getMultiSelectionListValues("environment", reportForm);
/*  832 */     String[] strCompany = ReportSelectionsHelper.getMultiSelectionListValues("company", reportForm);
/*  833 */     String[] strLabel = ReportSelectionsHelper.getMultiSelectionListValues("Label", reportForm);
/*      */ 
/*      */     
/*  836 */     boolean bParent = (reportForm.getElement("ParentsOnly") != null) ? ((FormCheckBox)reportForm.getElement("ParentsOnly")).isChecked() : 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  845 */     ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "header.environment_id", strEnvironment, true, "environment", reportForm, true, false);
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
/*  879 */     ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "header.company_id", strCompany, true, "company", reportForm, false, true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  889 */     ReportSelectionsHelper.addReleasingFamilyLabelFamilyMultSelect("family", context, query, reportForm);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  897 */     ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "header.label_id", strLabel, true, "Label", reportForm, false, true);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  902 */     if (!strArtist.equalsIgnoreCase("")) {
/*  903 */       query.append(" AND header.[artist] LIKE '%" + MilestoneHelper.escapeSingleQuotes(strArtist) + "%'");
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
/*  922 */     ReportSelectionsHelper.addStatusToSelect(reportForm, query);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  927 */     if (!strReleaseType.equalsIgnoreCase("") && !strReleaseType.equalsIgnoreCase("all"))
/*      */     {
/*  929 */       if (strReleaseType.equalsIgnoreCase("commercial")) {
/*  930 */         query.append(" AND header.[release_type] ='CO'");
/*      */       } else {
/*  932 */         query.append(" AND header.[release_type] ='PR'");
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  938 */     if (!strLabelContact.equalsIgnoreCase("") && !strLabelContact.equalsIgnoreCase("0")) {
/*  939 */       query.append(" AND header.[contact_id] =" + strLabelContact);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  944 */     if (!strUmlContact.equalsIgnoreCase("") && !strUmlContact.equalsIgnoreCase("0")) {
/*  945 */       query.append(" AND mfg.[uml_id] =" + strUmlContact);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  953 */     if (!beginDate.equalsIgnoreCase("") || !endDate.equalsIgnoreCase("")) {
/*  954 */       query.append(" AND ((header.[status] ='TBS' OR header.[status] ='ITW') OR (");
/*      */     }
/*  956 */     if (!beginDate.equalsIgnoreCase("")) {
/*  957 */       query.append(" street_date >= '" + MilestoneHelper.escapeSingleQuotes(beginDate) + "'");
/*      */     }
/*  959 */     if (!endDate.equalsIgnoreCase(""))
/*      */     {
/*  961 */       if (!beginDate.equalsIgnoreCase("")) {
/*  962 */         query.append(" AND street_date <= '" + MilestoneHelper.escapeSingleQuotes(endDate) + "'");
/*      */       } else {
/*  964 */         query.append(" street_date <= '" + MilestoneHelper.escapeSingleQuotes(endDate) + "'");
/*      */       } 
/*      */     }
/*  967 */     if (!beginDate.equalsIgnoreCase("") || !endDate.equalsIgnoreCase("")) query.append("))");
/*      */     
/*  969 */     query.append(" ORDER BY header.[title_id], header.[release_id], taskDue, taskComplete ");
/*      */ 
/*      */     
/*  972 */     JdbcConnector connector = MilestoneHelper.getConnector(query.toString());
/*      */     
/*  974 */     connector.setForwardOnly(false);
/*  975 */     connector.runQuery();
/*      */ 
/*      */ 
/*      */     
/*  979 */     Vector selections = null;
/*      */ 
/*      */     
/*  982 */     int totalCount = 0;
/*  983 */     int tenth = 0;
/*      */     
/*  985 */     totalCount = connector.getRowCount();
/*      */     
/*  987 */     tenth = totalCount / 10;
/*      */     
/*  989 */     if (tenth < 1) {
/*  990 */       tenth = 1;
/*      */     }
/*      */     
/*      */     try {
/*  994 */       HttpServletResponse sresponse = context.getResponse();
/*  995 */       context.putDelivery("status", new String("start_gathering"));
/*  996 */       context.putDelivery("percent", new String("10"));
/*  997 */       context.includeJSP("status.jsp", "hiddenFrame");
/*  998 */       sresponse.setContentType("text/plain");
/*  999 */       sresponse.flushBuffer();
/*      */     }
/* 1001 */     catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */     
/* 1005 */     int recordCount = 0;
/* 1006 */     int count = 0;
/*      */ 
/*      */ 
/*      */     
/* 1010 */     selections = new Vector();
/*      */     
/* 1012 */     while (connector.more()) {
/*      */ 
/*      */       
/*      */       try {
/* 1016 */         if (count < recordCount / tenth) {
/*      */           
/* 1018 */           count = recordCount / tenth;
/* 1019 */           HttpServletResponse sresponse = context.getResponse();
/* 1020 */           context.putDelivery("status", new String("start_gathering"));
/* 1021 */           context.putDelivery("percent", new String(String.valueOf(count * 10)));
/* 1022 */           context.includeJSP("status.jsp", "hiddenFrame");
/* 1023 */           sresponse.setContentType("text/plain");
/* 1024 */           sresponse.flushBuffer();
/*      */         } 
/*      */         
/* 1027 */         recordCount++;
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
/* 1038 */         if (bParent) {
/*      */           
/* 1040 */           String prefixId = "";
/* 1041 */           String tmpTitleId = connector.getField("title_id", "");
/* 1042 */           String tmpSelectionNo = connector.getField("selection_no", "");
/*      */           
/* 1044 */           prefixId = SelectionManager.getLookupObjectValue((PrefixCode)MilestoneHelper.getLookupObject(connector.getField("prefix", ""), Cache.getPrefixCodes()));
/*      */           
/* 1046 */           if (!tmpTitleId.equalsIgnoreCase(String.valueOf(prefixId) + tmpSelectionNo)) {
/*      */             
/* 1048 */             connector.next();
/*      */             
/*      */             continue;
/*      */           } 
/*      */         } 
/*      */         
/* 1054 */         int numberOfUnits = 0;
/*      */         
/*      */         try {
/* 1057 */           numberOfUnits = connector.getIntegerField("pfm.[units_per_set]");
/*      */         }
/* 1059 */         catch (Exception exception) {}
/*      */ 
/*      */         
/* 1062 */         Selection selection = null;
/*      */         
/* 1064 */         selection = new Selection();
/* 1065 */         selection.setSelectionID(connector.getIntegerField("release_id"));
/*      */         
/* 1067 */         String selectionNo = "";
/* 1068 */         if (connector.getFieldByName("selection_no") != null)
/* 1069 */           selectionNo = connector.getFieldByName("selection_no"); 
/* 1070 */         selection.setSelectionNo(selectionNo);
/*      */         
/* 1072 */         selection.setProjectID(connector.getField("project_no", ""));
/*      */         
/* 1074 */         String titleId = "";
/* 1075 */         if (connector.getFieldByName("title_id") != null)
/* 1076 */           titleId = connector.getFieldByName("title_id"); 
/* 1077 */         selection.setTitleID(titleId);
/*      */         
/* 1079 */         selection.setTitle(connector.getField("title", ""));
/* 1080 */         selection.setArtistFirstName(connector.getField("artist_first_name", ""));
/* 1081 */         selection.setArtistLastName(connector.getField("artist_last_name", ""));
/* 1082 */         selection.setArtist(connector.getField("artist", ""));
/* 1083 */         selection.setASide(connector.getField("side_a_title", ""));
/* 1084 */         selection.setBSide(connector.getField("side_b_title", ""));
/*      */         
/* 1086 */         selection.setProductCategory(
/* 1087 */             (ProductCategory)MilestoneHelper.getLookupObject(connector.getField("product_line"), Cache.getProductCategories()));
/* 1088 */         selection.setReleaseType(
/* 1089 */             (ReleaseType)MilestoneHelper.getLookupObject(connector.getField("release_type"), 
/* 1090 */               Cache.getReleaseTypes()));
/*      */         
/* 1092 */         selection.setSelectionConfig(
/* 1093 */             MilestoneHelper.getSelectionConfigObject(connector.getField("configuration"), Cache.getSelectionConfigs()));
/* 1094 */         selection.setSelectionSubConfig(
/* 1095 */             MilestoneHelper.getSelectionSubConfigObject(connector.getField("sub_configuration"), selection.getSelectionConfig()));
/*      */         
/* 1097 */         selection.setUpc(connector.getField("upc", ""));
/*      */         
/* 1099 */         String sellCodeString = connector.getFieldByName("price_code");
/* 1100 */         if (sellCodeString != null)
/*      */         {
/* 1102 */           selection.setPriceCode(SelectionManager.getInstance().getPriceCode(sellCodeString));
/*      */         }
/*      */         
/* 1105 */         selection.setSellCode(sellCodeString);
/*      */         
/* 1107 */         selection.setGenre(
/* 1108 */             (Genre)MilestoneHelper.getLookupObject(connector.getField("genre"), Cache.getMusicTypes()));
/* 1109 */         selection.setFamily((Family)MilestoneHelper.getStructureObject(connector.getIntegerField("family_id")));
/* 1110 */         selection.setEnvironment((Environment)MilestoneHelper.getStructureObject(connector.getIntegerField("environment_id")));
/* 1111 */         selection.setCompany((Company)MilestoneHelper.getStructureObject(connector.getIntegerField("company_id")));
/* 1112 */         selection.setDivision((Division)MilestoneHelper.getStructureObject(connector.getIntegerField("division_id")));
/* 1113 */         selection.setLabel((Label)MilestoneHelper.getStructureObject(connector.getIntegerField("label_id")));
/*      */         
/* 1115 */         String streetDateString = connector.getFieldByName("street_date");
/* 1116 */         if (streetDateString != null) {
/* 1117 */           selection.setStreetDate(MilestoneHelper.getDatabaseDate(streetDateString));
/*      */         }
/* 1119 */         String internationalDateString = connector.getFieldByName("international_date");
/* 1120 */         if (internationalDateString != null) {
/* 1121 */           selection.setInternationalDate(MilestoneHelper.getDatabaseDate(internationalDateString));
/*      */         }
/* 1123 */         selection.setOtherContact(connector.getField("coordinator_contacts", ""));
/*      */         
/* 1125 */         if (UserManager.getInstance().getUser(connector.getIntegerField("contact_id")) != null) {
/* 1126 */           selection.setLabelContact(UserManager.getInstance().getUser(connector.getIntegerField("contact_id")));
/*      */         }
/* 1128 */         selection.setSelectionStatus(
/* 1129 */             (SelectionStatus)MilestoneHelper.getLookupObject(connector.getField("status"), Cache.getSelectionStatusList()));
/* 1130 */         selection.setHoldSelection(connector.getBoolean("hold_indicator"));
/*      */         
/* 1132 */         selection.setHoldReason(connector.getField("hold_reason", ""));
/* 1133 */         selection.setComments(connector.getField("comments", ""));
/* 1134 */         selection.setSpecialPackaging(connector.getBoolean("special_pkg_indicator"));
/* 1135 */         selection.setNumberOfUnits(numberOfUnits);
/* 1136 */         selection.setPressAndDistribution(connector.getBoolean("pd_indicator"));
/*      */         
/* 1138 */         selection.setPrefixID((PrefixCode)MilestoneHelper.getLookupObject(connector.getField("prefix"), Cache.getPrefixCodes()));
/* 1139 */         selection.setSelectionPackaging(connector.getField("packaging", ""));
/*      */         
/* 1141 */         String impactDateString = connector.getFieldByName("impact_date");
/* 1142 */         if (impactDateString != null) {
/* 1143 */           selection.setImpactDate(MilestoneHelper.getDatabaseDate(impactDateString));
/*      */         }
/* 1145 */         String lastUpdateDateString = connector.getFieldByName("last_updated_on");
/* 1146 */         if (lastUpdateDateString != null) {
/* 1147 */           selection.setLastUpdateDate(MilestoneHelper.getDatabaseDate(lastUpdateDateString));
/*      */         }
/* 1149 */         selection.setLastUpdatingUser(UserManager.getInstance().getUser(connector.getIntegerField("last_updated_by")));
/*      */ 
/*      */         
/* 1152 */         String originDateString = connector.getFieldByName("entered_on");
/* 1153 */         if (originDateString != null) {
/* 1154 */           selection.setOriginDate(MilestoneHelper.getDatabaseDate(originDateString));
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 1159 */         User umlContact = UserManager.getInstance().getUser(connector.getIntegerField("mfg.[uml_id]"));
/* 1160 */         selection.setUmlContact(umlContact);
/* 1161 */         selection.setPlant(MilestoneHelper.getLookupObject(connector.getField("mfg.[plant]"), Cache.getVendors()));
/* 1162 */         selection.setDistribution(MilestoneHelper.getLookupObject(connector.getField("mfg.[distribution]"), Cache.getDistributionCodes()));
/* 1163 */         selection.setPoQuantity(connector.getIntegerField("mfg.[order_qty]"));
/* 1164 */         selection.setCompletedQuantity(connector.getIntegerField("mfg.[complete_qty]"));
/* 1165 */         selection.setManufacturingComments(connector.getField("mfg.[order_comments]", ""));
/* 1166 */         selection.setPrice(connector.getFloat("mfg.[list_price]"));
/*      */         
/* 1168 */         selection.setFullSelection(true);
/*      */         
/* 1170 */         String strDistribution = (reportForm.getStringValue("distribution") != null) ? reportForm.getStringValue("distribution") : "";
/* 1171 */         String strFuture2 = (reportForm.getStringValue("Future2") != null) ? reportForm.getStringValue("Future2") : "";
/*      */ 
/*      */         
/* 1174 */         if (!strDistribution.equalsIgnoreCase("") && !strDistribution.equalsIgnoreCase("All")) {
/*      */           
/* 1176 */           String selDistribution = SelectionManager.getLookupObjectValue(selection.getDistribution());
/*      */           
/* 1178 */           if (!selDistribution.equalsIgnoreCase(strDistribution.trim())) {
/*      */             continue;
/*      */           }
/*      */         } 
/* 1182 */         int nextReleaseId = connector.getIntegerField("release_id");
/*      */         
/* 1184 */         Schedule schedule = new Schedule();
/*      */ 
/*      */ 
/*      */         
/* 1188 */         Vector precacheSchedule = new Vector();
/*      */ 
/*      */ 
/*      */         
/* 1192 */         while (connector.more() && connector.getFieldByName("title_id").equalsIgnoreCase(titleId)) {
/*      */           
/* 1194 */           ScheduledTask scheduledTask = null;
/*      */ 
/*      */           
/* 1197 */           scheduledTask = new ScheduledTask();
/*      */ 
/*      */ 
/*      */           
/* 1201 */           scheduledTask.setReleaseID(connector.getIntegerField("release_Id"));
/*      */ 
/*      */           
/* 1204 */           scheduledTask.setSelectionNo(connector.getField("selection_no", ""));
/* 1205 */           scheduledTask.setUpc(connector.getField("upc", ""));
/* 1206 */           streetDateString = connector.getFieldByName("street_date");
/* 1207 */           if (streetDateString != null) {
/* 1208 */             scheduledTask.setStreetDate(MilestoneHelper.getDatabaseDate(streetDateString));
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1214 */           scheduledTask.setTaskID(connector.getIntegerField("task_id"));
/*      */ 
/*      */ 
/*      */           
/* 1218 */           scheduledTask.setOwner((Family)MilestoneHelper.getStructureObject(connector.getIntegerField("taskOwner")));
/*      */ 
/*      */           
/* 1221 */           String dueDateString = connector.getField("taskDue", "");
/* 1222 */           if (dueDateString.length() > 0) {
/* 1223 */             scheduledTask.setDueDate(MilestoneHelper.getDatabaseDate(dueDateString));
/*      */           }
/*      */           
/* 1226 */           String completionDateString = connector.getField("taskComplete", "");
/* 1227 */           if (completionDateString.length() > 0) {
/* 1228 */             scheduledTask.setCompletionDate(MilestoneHelper.getDatabaseDate(completionDateString));
/*      */           }
/*      */           
/* 1231 */           String taskStatus = connector.getField("taskStatus", "");
/* 1232 */           if (taskStatus.length() > 1) {
/* 1233 */             scheduledTask.setScheduledTaskStatus(taskStatus);
/*      */           }
/*      */           
/* 1236 */           int day = connector.getIntegerField("taskDayOfWeek");
/* 1237 */           if (day > 0) {
/* 1238 */             scheduledTask.setDayOfTheWeek(new Day(connector.getIntegerField("taskDayOfWeek")));
/*      */           }
/*      */           
/* 1241 */           int weeks = connector.getIntegerField("taskWeeksToRelease");
/* 1242 */           if (weeks > 0) {
/* 1243 */             scheduledTask.setWeeksToRelease(connector.getIntegerField("taskWeeksToRelease"));
/*      */           }
/*      */           
/* 1246 */           String vendorString = connector.getField("taskVendor", "");
/* 1247 */           if (vendorString.length() > 0) {
/* 1248 */             scheduledTask.setVendor(vendorString);
/*      */           }
/*      */           
/* 1251 */           int taskAbbrevID = connector.getIntegerField("abbrev_id");
/* 1252 */           scheduledTask.setTaskAbbreviationID(taskAbbrevID);
/*      */ 
/*      */           
/* 1255 */           String taskDept = connector.getField("department", "");
/* 1256 */           scheduledTask.setDepartment(taskDept);
/*      */ 
/*      */           
/* 1259 */           scheduledTask.setKeyTask(connector.getBoolean("taskKey"));
/*      */ 
/*      */           
/* 1262 */           scheduledTask.setAuthorizationName(connector.getField("taskAuthName", ""));
/*      */ 
/*      */           
/* 1265 */           String authDateString = connector.getField("taskAuthDate", "");
/* 1266 */           if (authDateString.length() > 0) {
/* 1267 */             scheduledTask.setAuthorizationDate(MilestoneHelper.getDatabaseDate(authDateString));
/*      */           }
/*      */           
/* 1270 */           String comments = connector.getField("taskComments", "");
/* 1271 */           scheduledTask.setComments(comments);
/*      */ 
/*      */           
/* 1274 */           scheduledTask.setName(connector.getField("name", ""));
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1279 */           precacheSchedule.add(scheduledTask);
/*      */           
/* 1281 */           scheduledTask = null;
/*      */           
/* 1283 */           if (connector.more()) {
/*      */             
/* 1285 */             connector.next();
/* 1286 */             recordCount++;
/*      */             continue;
/*      */           } 
/*      */           break;
/*      */         } 
/* 1291 */         schedule.setTasks(precacheSchedule);
/*      */         
/* 1293 */         selection.setSchedule(schedule);
/*      */         
/* 1295 */         selections.add(selection);
/*      */       
/*      */       }
/* 1298 */       catch (Exception exception) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1305 */     connector.close();
/*      */     
/* 1307 */     return selections;
/*      */   }
/*      */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\TaskDueDateByTitleForPrintHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */