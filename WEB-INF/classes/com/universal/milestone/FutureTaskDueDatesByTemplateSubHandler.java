/*      */ package WEB-INF.classes.com.universal.milestone;
/*      */ 
/*      */ import com.techempower.ComponentLog;
/*      */ import com.techempower.gemini.Context;
/*      */ import com.techempower.gemini.GeminiApplication;
/*      */ import com.universal.milestone.Cache;
/*      */ import com.universal.milestone.DatePeriod;
/*      */ import com.universal.milestone.Day;
/*      */ import com.universal.milestone.Family;
/*      */ import com.universal.milestone.Form;
/*      */ import com.universal.milestone.FutureTaskDueDatesByTemplateSubHandler;
/*      */ import com.universal.milestone.JdbcConnector;
/*      */ import com.universal.milestone.MilestoneFormDropDownMenu;
/*      */ import com.universal.milestone.MilestoneHelper;
/*      */ import com.universal.milestone.Report;
/*      */ import com.universal.milestone.ReportSelectionsHelper;
/*      */ import com.universal.milestone.SecureHandler;
/*      */ import com.universal.milestone.StatusJSPupdate;
/*      */ import inetsoft.report.SectionBand;
/*      */ import inetsoft.report.StyleFont;
/*      */ import inetsoft.report.XStyleSheet;
/*      */ import inetsoft.report.lens.DefaultSectionLens;
/*      */ import inetsoft.report.lens.DefaultTableLens;
/*      */ import java.awt.Color;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Font;
/*      */ import java.awt.Rectangle;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Calendar;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.Vector;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class FutureTaskDueDatesByTemplateSubHandler
/*      */   extends SecureHandler
/*      */ {
/*      */   public static final String COMPONENT_CODE = "fTaskDue";
/*      */   public GeminiApplication application;
/*      */   public ComponentLog log;
/*      */   public static final int NUM_COLUMNS = 14;
/*      */   public static final int addDays = 7;
/*      */   public static final int NUM_DATE_COLS = 12;
/*      */   public DefaultTableLens templateLens;
/*      */   public DefaultTableLens taskLens;
/*      */   public DefaultTableLens rowCountTable;
/*      */   public DefaultSectionLens group;
/*      */   public StatusJSPupdate statusJSPupdate;
/*      */   public int row;
/*      */   
/*      */   public FutureTaskDueDatesByTemplateSubHandler(GeminiApplication application) {
/*   56 */     this.templateLens = null;
/*   57 */     this.taskLens = null;
/*   58 */     this.rowCountTable = null;
/*   59 */     this.group = null;
/*      */ 
/*      */     
/*   62 */     this.statusJSPupdate = null;
/*      */     
/*   64 */     this.row = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   75 */     this.application = application;
/*   76 */     this.log = application.getLog("fTaskDue");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   86 */   public String getDescription() { return "Future Task Due Dates By Template"; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void fillFutureTaskDueDatesByTemplateSubHandler(XStyleSheet report, Context context) {
/*  102 */     this.statusJSPupdate = new StatusJSPupdate(context);
/*      */ 
/*      */     
/*  105 */     Color SHADED_AREA_COLOR = Color.lightGray;
/*  106 */     int COL_LINE_STYLE = 4097;
/*  107 */     int HEADER_FONT_SIZE = 12;
/*  108 */     double ldLineVal = 0.3D;
/*      */ 
/*      */ 
/*      */     
/*  112 */     int separatorLineStyle = 266240;
/*  113 */     Color separatorLineColor = Color.black;
/*      */ 
/*      */     
/*  116 */     int tableHeaderLineStyle = 266240;
/*      */     
/*  118 */     Color tableHeaderLineColor = Color.black;
/*      */ 
/*      */     
/*  121 */     int tableRowLineStyle = 4097;
/*      */ 
/*      */     
/*  124 */     Color tableRowLineColor = new Color(208, 206, 206, 0);
/*      */ 
/*      */ 
/*      */     
/*  128 */     ArrayList taskDueTemplates = getAllTemplatesForUser(context);
/*      */ 
/*      */ 
/*      */     
/*  132 */     int totalCount = 0;
/*  133 */     int recordCount = 0;
/*      */ 
/*      */     
/*  136 */     if (taskDueTemplates.size() > 0) {
/*  137 */       TemplateObj templateObj = (TemplateObj)taskDueTemplates.get(0);
/*  138 */       totalCount = templateObj.totalCount;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  146 */       this.rowCountTable = new DefaultTableLens(1, 20000);
/*      */       
/*  148 */       SectionBand hbandType = new SectionBand(report);
/*  149 */       SectionBand body = new SectionBand(report);
/*  150 */       SectionBand spacer = new SectionBand(report);
/*  151 */       spacer.setVisible(true);
/*  152 */       spacer.setHeight(0.05F);
/*  153 */       spacer.setShrinkToFit(false);
/*  154 */       spacer.setBottomBorder(0);
/*      */ 
/*      */ 
/*      */       
/*  158 */       Form reportForm = (Form)context.getSessionValue("reportForm");
/*      */       
/*  160 */       Calendar beginDueDate = (reportForm.getStringValue("beginDate") != null && 
/*  161 */         reportForm.getStringValue("beginDate").length() > 0) ? 
/*  162 */         MilestoneHelper.getDate(reportForm.getStringValue("beginDate")) : null;
/*      */       
/*  164 */       Calendar endDueDate = (reportForm.getStringValue("endDate") != null && 
/*  165 */         reportForm.getStringValue("endDate").length() > 0) ? 
/*  166 */         MilestoneHelper.getDate(reportForm.getStringValue("endDate")) : null;
/*      */       
/*  168 */       report.setElement("startdate", MilestoneHelper.getFormatedDate(beginDueDate));
/*  169 */       report.setElement("enddate", MilestoneHelper.getFormatedDate(endDueDate));
/*      */       
/*  171 */       SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
/*  172 */       String todayLong = formatter.format(new Date());
/*  173 */       report.setElement("bottomdate", todayLong);
/*      */ 
/*      */ 
/*      */       
/*  177 */       this.statusJSPupdate.updateStatus(totalCount, recordCount, "start_report", 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  184 */       for (int i = 0; i < taskDueTemplates.size(); i++)
/*      */       {
/*      */         
/*  187 */         int nextRow = 0;
/*      */ 
/*      */         
/*  190 */         TemplateObj templateObj = (TemplateObj)taskDueTemplates.get(i);
/*      */ 
/*      */         
/*  193 */         if (templateObj.taskObjs == null || templateObj.taskObjs.size() == 0) {
/*  194 */           recordCount++;
/*      */           
/*  196 */           this.statusJSPupdate.updateStatus(totalCount, recordCount, "start_report", 0);
/*      */         } 
/*      */ 
/*      */         
/*  200 */         int numDateCols = templateObj.streetDates.size();
/*      */ 
/*      */ 
/*      */         
/*  204 */         int startDateIndex = 0;
/*      */         
/*  206 */         boolean isFirstTime = true;
/*      */         
/*  208 */         boolean shade = true;
/*      */         
/*  210 */         boolean addBlankRow = false;
/*  211 */         boolean addCont = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  217 */         updateSortVar(templateObj);
/*      */ 
/*      */         
/*  220 */         Collections.sort(templateObj.taskObjs);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         do {
/*  229 */           int toColumn = (numDateCols > 12) ? (startDateIndex + 12) : (startDateIndex + numDateCols);
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  234 */           printTemplateHdr(report, context, templateObj, startDateIndex, toColumn, addCont);
/*      */ 
/*      */ 
/*      */           
/*  238 */           ArrayList taskObjs = templateObj.taskObjs;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  243 */           this.taskLens = new DefaultTableLens(taskObjs.size(), 14);
/*      */ 
/*      */           
/*  246 */           this.row = 0;
/*      */           
/*  248 */           for (int t = 0; t < taskObjs.size(); t++) {
/*      */ 
/*      */ 
/*      */             
/*  252 */             if (isFirstTime) {
/*  253 */               recordCount++;
/*      */               
/*  255 */               this.statusJSPupdate.updateStatus(totalCount, recordCount, "start_report", 0);
/*      */             } 
/*      */ 
/*      */             
/*  259 */             TaskObj taskObj = (TaskObj)taskObjs.get(t);
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  264 */             printTemplateDetail(report, context, taskObj, startDateIndex, toColumn, shade, 
/*  265 */                 templateObj, addCont);
/*      */             
/*  267 */             shade = !shade;
/*      */             
/*  269 */             this.row++;
/*      */           } 
/*      */ 
/*      */           
/*  273 */           numDateCols -= 12;
/*      */ 
/*      */ 
/*      */           
/*  277 */           startDateIndex += 12;
/*      */ 
/*      */ 
/*      */           
/*  281 */           isFirstTime = false;
/*  282 */           shade = true;
/*  283 */           addCont = true;
/*      */           
/*  285 */           if (numDateCols <= 0)
/*  286 */             continue;  report.addPageBreak();
/*      */         
/*      */         }
/*  289 */         while (numDateCols > 0);
/*      */         
/*  291 */         if (i < taskDueTemplates.size() - 1) {
/*  292 */           report.addPageBreak();
/*      */         }
/*      */       }
/*      */     
/*  296 */     } catch (Exception e) {
/*      */       
/*  298 */       System.out.println(">>>>>>>>FutureTaskDueDatesByTemplateSubHandler: exception: " + e);
/*  299 */       e.printStackTrace();
/*      */     } 
/*      */     
/*  302 */     this.statusJSPupdate = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void printTemplateHdr(XStyleSheet report, Context context, TemplateObj templateObj, int dateIndex, int toColumn, boolean addCont) {
/*  310 */     this.rowCountTable = new DefaultTableLens(2, 10000);
/*      */     
/*  312 */     SectionBand hbandType = new SectionBand(report);
/*  313 */     SectionBand body = new SectionBand(report);
/*      */     
/*  315 */     this.group = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  324 */     int lensRows = 3;
/*      */ 
/*      */ 
/*      */     
/*  328 */     this.templateLens = new DefaultTableLens(lensRows, 14);
/*      */     
/*  330 */     int nextRow = 0;
/*      */ 
/*      */     
/*  333 */     this.templateLens.setColAlignment(0, 1);
/*  334 */     this.templateLens.setColAlignment(1, 1);
/*  335 */     this.templateLens.setColAlignment(2, 4);
/*  336 */     this.templateLens.setColAlignment(3, 4);
/*  337 */     this.templateLens.setColAlignment(4, 4);
/*  338 */     this.templateLens.setColAlignment(5, 4);
/*  339 */     this.templateLens.setColAlignment(6, 4);
/*  340 */     this.templateLens.setColAlignment(7, 4);
/*  341 */     this.templateLens.setColAlignment(8, 4);
/*  342 */     this.templateLens.setColAlignment(9, 4);
/*  343 */     this.templateLens.setColAlignment(10, 4);
/*  344 */     this.templateLens.setColAlignment(11, 4);
/*  345 */     this.templateLens.setColAlignment(12, 4);
/*  346 */     this.templateLens.setColAlignment(13, 4);
/*      */     
/*  348 */     this.templateLens.setColBorderColor(Color.white);
/*  349 */     this.templateLens.setRowBorderColor(Color.white);
/*  350 */     this.templateLens.setRowBorder(0);
/*  351 */     this.templateLens.setColBorder(0);
/*      */ 
/*      */ 
/*      */     
/*  355 */     this.templateLens.setColWidth(0, 200);
/*  356 */     this.templateLens.setColWidth(1, 50);
/*  357 */     this.templateLens.setColWidth(2, 50);
/*  358 */     this.templateLens.setColWidth(3, 50);
/*  359 */     this.templateLens.setColWidth(4, 50);
/*  360 */     this.templateLens.setColWidth(5, 50);
/*  361 */     this.templateLens.setColWidth(6, 50);
/*  362 */     this.templateLens.setColWidth(7, 50);
/*  363 */     this.templateLens.setColWidth(8, 50);
/*  364 */     this.templateLens.setColWidth(9, 50);
/*  365 */     this.templateLens.setColWidth(10, 50);
/*  366 */     this.templateLens.setColWidth(11, 50);
/*  367 */     this.templateLens.setColWidth(12, 50);
/*  368 */     this.templateLens.setColWidth(13, 50);
/*      */ 
/*      */     
/*  371 */     this.templateLens.setSpan(nextRow, 0, new Dimension(7, 1));
/*  372 */     if (!addCont) {
/*  373 */       this.templateLens.setObject(nextRow, 0, templateObj.name);
/*      */     } else {
/*  375 */       this.templateLens.setObject(nextRow, 0, String.valueOf(templateObj.name) + " - Continued");
/*      */     } 
/*      */     
/*  378 */     this.templateLens.setFont(nextRow, 0, new StyleFont("Arial", 1, 12));
/*      */     
/*  380 */     nextRow++;
/*      */ 
/*      */     
/*  383 */     this.templateLens.setSpan(nextRow, 1, new Dimension(1, 1));
/*  384 */     this.templateLens.setObject(nextRow, 1, "Street Date");
/*  385 */     this.templateLens.setFont(nextRow, 1, new StyleFont("Arial", 1, 8));
/*  386 */     this.templateLens.setRowBorder(nextRow, 1, 4097);
/*  387 */     this.templateLens.setRowBorderColor(nextRow, 1, Color.black);
/*  388 */     this.templateLens.setColBorder(1, 0);
/*      */     
/*  390 */     int col = 2;
/*  391 */     for (int x = dateIndex; x < toColumn; x++) {
/*  392 */       this.templateLens.setObject(nextRow, col, MilestoneHelper.getFormatedDate((Calendar)templateObj.streetDates.get(x)));
/*  393 */       this.templateLens.setFont(nextRow, col, new StyleFont("Arial", 1, 9));
/*  394 */       this.templateLens.setRowBorder(nextRow, col, 4097);
/*  395 */       this.templateLens.setRowBorderColor(nextRow, col, Color.black);
/*  396 */       col++;
/*      */     } 
/*      */     
/*  399 */     nextRow++;
/*      */     
/*  401 */     this.templateLens.setObject(nextRow, 0, "Task Description");
/*  402 */     this.templateLens.setFont(nextRow, 0, new Font("Arial", 1, 10));
/*  403 */     this.templateLens.setSpan(nextRow, 1, new Dimension(1, 1));
/*  404 */     this.templateLens.setObject(nextRow, 1, "Wks To Rel");
/*  405 */     this.templateLens.setFont(nextRow, 1, new Font("Arial", 1, 7));
/*  406 */     this.templateLens.setColBorder(1, 4097);
/*  407 */     this.templateLens.setColBorderColor(1, Color.black);
/*  408 */     this.templateLens.setColBorder(nextRow - 1, 1, 0);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  413 */     body = new SectionBand(report);
/*  414 */     body.setHeight(1.2F);
/*  415 */     body.addTable(this.templateLens, new Rectangle(800, 100));
/*  416 */     body.setBottomBorder(0);
/*  417 */     body.setTopBorder(0);
/*  418 */     body.setShrinkToFit(true);
/*  419 */     body.setVisible(true);
/*      */     
/*  421 */     this.group = new DefaultSectionLens(null, this.group, body);
/*      */ 
/*      */     
/*  424 */     report.addSection(this.group, this.rowCountTable);
/*  425 */     this.group = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void printTemplateDetail(XStyleSheet report, Context context, TaskObj taskObj, int dateIndex, int toColumn, boolean shade, TemplateObj templateObj, boolean addCont) {
/*  435 */     SectionBand body = new SectionBand(report);
/*      */     
/*  437 */     int nextRow = 0;
/*      */ 
/*      */     
/*  440 */     if (this.row > 29) {
/*  441 */       this.taskLens = new DefaultTableLens(4, 14);
/*      */     } else {
/*  443 */       this.taskLens = new DefaultTableLens(1, 14);
/*      */     } 
/*      */     
/*  446 */     this.taskLens.setRowHeight(14);
/*      */ 
/*      */     
/*  449 */     this.taskLens.setColAlignment(0, 1);
/*  450 */     this.taskLens.setColAlignment(1, 1);
/*  451 */     this.taskLens.setColAlignment(2, 4);
/*  452 */     this.taskLens.setColAlignment(3, 4);
/*  453 */     this.taskLens.setColAlignment(4, 4);
/*  454 */     this.taskLens.setColAlignment(5, 4);
/*  455 */     this.taskLens.setColAlignment(6, 4);
/*  456 */     this.taskLens.setColAlignment(7, 4);
/*  457 */     this.taskLens.setColAlignment(8, 4);
/*  458 */     this.taskLens.setColAlignment(9, 4);
/*  459 */     this.taskLens.setColAlignment(10, 4);
/*  460 */     this.taskLens.setColAlignment(11, 4);
/*  461 */     this.taskLens.setColAlignment(12, 4);
/*  462 */     this.taskLens.setColAlignment(13, 4);
/*      */     
/*  464 */     this.taskLens.setColBorderColor(Color.white);
/*  465 */     this.taskLens.setRowBorderColor(Color.white);
/*  466 */     this.taskLens.setRowBorder(0);
/*  467 */     this.taskLens.setColBorder(0);
/*      */ 
/*      */ 
/*      */     
/*  471 */     this.taskLens.setColWidth(0, 200);
/*  472 */     this.taskLens.setColWidth(1, 50);
/*  473 */     this.taskLens.setColWidth(2, 50);
/*  474 */     this.taskLens.setColWidth(3, 50);
/*  475 */     this.taskLens.setColWidth(4, 50);
/*  476 */     this.taskLens.setColWidth(5, 50);
/*  477 */     this.taskLens.setColWidth(6, 50);
/*  478 */     this.taskLens.setColWidth(7, 50);
/*  479 */     this.taskLens.setColWidth(8, 50);
/*  480 */     this.taskLens.setColWidth(9, 50);
/*  481 */     this.taskLens.setColWidth(10, 50);
/*  482 */     this.taskLens.setColWidth(11, 50);
/*  483 */     this.taskLens.setColWidth(12, 50);
/*  484 */     this.taskLens.setColWidth(13, 50);
/*      */ 
/*      */     
/*  487 */     String fontName = "Arial";
/*  488 */     if (this.row > 29) {
/*      */ 
/*      */       
/*  491 */       this.row = 0;
/*      */ 
/*      */       
/*  494 */       this.taskLens.setSpan(nextRow, 0, new Dimension(7, 1));
/*  495 */       if (!addCont) {
/*  496 */         this.taskLens.setObject(nextRow, 0, templateObj.name);
/*      */       } else {
/*  498 */         this.taskLens.setObject(nextRow, 0, String.valueOf(templateObj.name) + " - Continued");
/*      */       } 
/*      */       
/*  501 */       this.taskLens.setFont(nextRow, 0, new StyleFont("Arial", 1, 12));
/*      */       
/*  503 */       nextRow++;
/*      */ 
/*      */       
/*  506 */       this.taskLens.setSpan(nextRow, 1, new Dimension(1, 1));
/*  507 */       this.taskLens.setObject(nextRow, 1, "Street Date");
/*  508 */       this.taskLens.setFont(nextRow, 1, new StyleFont("Arial", 1, 8));
/*  509 */       this.taskLens.setRowBorder(nextRow, 1, 4097);
/*  510 */       this.taskLens.setRowBorderColor(nextRow, 1, Color.black);
/*  511 */       this.taskLens.setColBorder(1, 0);
/*      */ 
/*      */       
/*  514 */       int cols = 2;
/*  515 */       for (int y = dateIndex; y < toColumn; y++) {
/*      */         
/*  517 */         this.taskLens.setObject(nextRow, cols, MilestoneHelper.getFormatedDate((Calendar)templateObj.streetDates.get(y)));
/*  518 */         this.taskLens.setFont(nextRow, cols, new StyleFont("Arial", 1, 9));
/*  519 */         this.taskLens.setRowBorder(nextRow, cols, 4097);
/*  520 */         this.taskLens.setRowBorderColor(nextRow, cols, Color.black);
/*  521 */         cols++;
/*      */       } 
/*      */       
/*  524 */       nextRow++;
/*      */       
/*  526 */       this.taskLens.setObject(nextRow, 0, "Task Description");
/*  527 */       this.taskLens.setFont(nextRow, 0, new Font("Arial", 1, 10));
/*  528 */       this.taskLens.setSpan(nextRow, 1, new Dimension(1, 1));
/*  529 */       this.taskLens.setObject(nextRow, 1, "Wks To Rel");
/*  530 */       this.taskLens.setFont(nextRow, 1, new Font("Arial", 1, 7));
/*  531 */       this.taskLens.setColBorder(1, 4097);
/*  532 */       this.taskLens.setColBorderColor(1, Color.black);
/*  533 */       this.taskLens.setColBorder(nextRow - 1, 1, 0);
/*      */       
/*  535 */       nextRow++;
/*      */     } 
/*      */     
/*  538 */     if (shade) {
/*  539 */       this.taskLens.setRowBackground(nextRow, Color.lightGray);
/*      */     }
/*      */ 
/*      */     
/*  543 */     this.taskLens.setObject(nextRow, 0, taskObj.name);
/*  544 */     if (!shade) {
/*  545 */       this.taskLens.setFont(nextRow, 0, new Font(fontName, 0, 8));
/*      */     } else {
/*  547 */       this.taskLens.setFont(nextRow, 0, new Font(fontName, 1, 8));
/*      */     } 
/*      */     
/*  550 */     String dayStr = (new Day(taskObj.dayOfWeek)).getDay();
/*  551 */     String weeks = " ";
/*  552 */     if (!dayStr.equalsIgnoreCase("SOL")) {
/*  553 */       weeks = String.valueOf(weeks) + (new Integer(taskObj.weeksToRelease)).toString();
/*      */     }
/*  555 */     this.taskLens.setObject(nextRow, 1, String.valueOf(dayStr) + weeks);
/*  556 */     if (!shade) {
/*  557 */       this.taskLens.setFont(nextRow, 1, new Font(fontName, 0, 8));
/*      */     } else {
/*  559 */       this.taskLens.setFont(nextRow, 1, new Font(fontName, 1, 8));
/*      */     } 
/*  561 */     this.taskLens.setColBorder(1, 4097);
/*  562 */     this.taskLens.setColBorderColor(1, Color.black);
/*      */ 
/*      */     
/*  565 */     int col = 2;
/*  566 */     for (int x = dateIndex; x < toColumn; x++) {
/*  567 */       String dueDate = MilestoneHelper.getFormatedDate((Calendar)taskObj.dueDates.get(x));
/*  568 */       this.taskLens.setObject(nextRow, col, !dueDate.equals("") ? dueDate : "n/a");
/*  569 */       if (!shade) {
/*  570 */         this.taskLens.setFont(nextRow, col, new Font(fontName, 0, 8));
/*      */       } else {
/*  572 */         this.taskLens.setFont(nextRow, col, new Font(fontName, 1, 8));
/*      */       } 
/*  574 */       col++;
/*      */     } 
/*      */ 
/*      */     
/*  578 */     body = new SectionBand(report);
/*  579 */     body.setHeight(1.5F);
/*  580 */     body.addTable(this.taskLens, new Rectangle(800, 800));
/*  581 */     body.setBottomBorder(0);
/*  582 */     body.setTopBorder(0);
/*  583 */     body.setShrinkToFit(true);
/*  584 */     body.setVisible(true);
/*      */     
/*  586 */     this.group = new DefaultSectionLens(null, this.group, body);
/*      */     
/*  588 */     report.addSection(this.group, this.rowCountTable);
/*  589 */     this.group = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ArrayList getAllTemplatesForUser(Context context) {
/*  598 */     HashMap weekDayConv = buildDayConvTable();
/*      */     
/*  600 */     StringBuffer query = new StringBuffer();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  605 */     Form reportForm = (Form)context.getSessionValue("reportForm");
/*      */     
/*  607 */     Report report = (Report)context.getSessionValue("Report");
/*      */     
/*  609 */     query.append("SELECT hdr.[template_id] as hdrTemplate_id, hdr.[name] as templateName, hdr.[owner], dtl.[task_id], task.[name] as taskName, task.[day_of_week], task.[weeks_to_release] FROM vi_Template_Header hdr INNER JOIN vi_Template_Detail dtl ON hdr.template_id = dtl.template_id INNER JOIN vi_Task task ON dtl.task_id = task.task_id ");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  623 */       MilestoneFormDropDownMenu templateList = (MilestoneFormDropDownMenu)reportForm.getElement("templatesList");
/*  624 */       if (templateList != null) {
/*  625 */         ArrayList templateIdStr = templateList.getStringValues();
/*  626 */         if (templateIdStr != null) {
/*  627 */           for (int x = 0; x < templateIdStr.size(); x++) {
/*  628 */             if (x == 0) {
/*  629 */               query.append(" WHERE ");
/*      */             } else {
/*  631 */               query.append(" OR ");
/*      */             } 
/*  633 */             query.append(" hdr.template_id = " + (String)templateIdStr.get(x));
/*      */           } 
/*      */         }
/*      */       } 
/*  637 */     } catch (Exception e) {
/*  638 */       e.printStackTrace();
/*  639 */       System.out.println("<<< template vector execption " + e.getMessage());
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  646 */     String strUmlKey = (reportForm.getStringValue("umlKeyTask") != null) ? reportForm.getStringValue("umlKeyTask") : "";
/*  647 */     if (!strUmlKey.equalsIgnoreCase("") && !strUmlKey.trim().equalsIgnoreCase("all")) {
/*      */       
/*  649 */       int intUml = -1;
/*      */ 
/*      */       
/*  652 */       Vector theFamilies = Cache.getFamilies();
/*  653 */       for (int i = 0; i < theFamilies.size(); i++) {
/*  654 */         Family family = (Family)theFamilies.get(i);
/*      */         
/*  656 */         if (family.getName().trim().equalsIgnoreCase("UML")) {
/*  657 */           intUml = family.getStructureID();
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */       
/*  663 */       if (intUml > 0)
/*      */       {
/*  665 */         if (strUmlKey.equalsIgnoreCase("UML")) {
/*  666 */           query.append(" AND task.owner = " + intUml);
/*      */         } else {
/*  668 */           query.append(" AND task.owner != " + intUml);
/*      */         } 
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  679 */     String[] strFamily = ReportSelectionsHelper.getMultiSelectionListValues("family", reportForm);
/*  680 */     if (strFamily != null && strFamily.length > 0) {
/*      */       
/*  682 */       String famStr = "";
/*  683 */       for (int i = 0; i < strFamily.length; i++) {
/*      */ 
/*      */         
/*  686 */         if (!strFamily[i].equals("0") && !strFamily[i].equals("") && !strFamily[i].equals("-1"))
/*      */         {
/*      */           
/*  689 */           if (famStr.equals("")) {
/*  690 */             famStr = String.valueOf(famStr) + " AND hdr.owner in (" + strFamily[i];
/*      */           } else {
/*  692 */             famStr = String.valueOf(famStr) + "," + strFamily[i];
/*      */           }  } 
/*      */       } 
/*  695 */       if (!famStr.equals("")) {
/*  696 */         famStr = String.valueOf(famStr) + ") ";
/*      */       }
/*  698 */       query.append(famStr);
/*      */     } 
/*      */     
/*  701 */     query.append(" AND task.active_flag = 1 ");
/*  702 */     query.append(" ORDER BY templateName, hdrTemplate_id, weeks_to_release desc, taskName ");
/*      */ 
/*      */ 
/*      */     
/*  706 */     JdbcConnector connector = MilestoneHelper.getConnector(query.toString());
/*  707 */     connector.setForwardOnly(false);
/*  708 */     connector.runQuery();
/*      */ 
/*      */     
/*  711 */     int totalCount = 0;
/*      */     
/*  713 */     totalCount = connector.getRowCount();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  718 */     this.statusJSPupdate.updateStatus(0, 0, "start_gathering", 10);
/*      */     
/*  720 */     int recordCount = 0;
/*      */ 
/*      */     
/*  723 */     Calendar effDateFrom = (reportForm.getStringValue("beginDate") != null && 
/*  724 */       reportForm.getStringValue("beginDate").length() > 0) ? 
/*  725 */       MilestoneHelper.getDate(reportForm.getStringValue("beginDate")) : 
/*  726 */       null;
/*      */     
/*  728 */     Calendar effDateTo = (reportForm.getStringValue("endDate") != null && 
/*  729 */       reportForm.getStringValue("endDate").length() > 0) ? 
/*  730 */       MilestoneHelper.getDate(reportForm.getStringValue("endDate")) : 
/*  731 */       null;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  736 */     ArrayList templateObjs = new ArrayList();
/*  737 */     ArrayList taskObjs = new ArrayList();
/*  738 */     TemplateObj templateObj = null;
/*  739 */     TaskObj taskObj = null;
/*      */ 
/*      */ 
/*      */     
/*  743 */     ArrayList reportStreeDates = computeStreetDates(effDateFrom, effDateTo);
/*      */ 
/*      */     
/*  746 */     int saveTemplateId = -99;
/*  747 */     boolean isFirstRecord = true;
/*  748 */     boolean hasSOL = false;
/*      */     
/*  750 */     while (connector.more()) {
/*      */ 
/*      */ 
/*      */       
/*  754 */       recordCount++;
/*      */ 
/*      */       
/*  757 */       this.statusJSPupdate.updateStatus(totalCount, recordCount, "start_gathering", 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/*  767 */         int templateId = connector.getInt("hdrTemplate_id");
/*      */         
/*  769 */         if (templateId != saveTemplateId) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  774 */           if (!isFirstRecord) {
/*  775 */             templateObj.taskObjs = taskObjs;
/*  776 */             templateObj.hasSOL = hasSOL;
/*  777 */             templateObjs.add(templateObj);
/*      */             
/*  779 */             taskObjs = new ArrayList();
/*      */             
/*  781 */             hasSOL = false;
/*      */           } 
/*      */           
/*  784 */           isFirstRecord = false;
/*  785 */           saveTemplateId = templateId;
/*      */ 
/*      */           
/*  788 */           templateObj = new TemplateObj(this);
/*  789 */           templateObj.templateId = connector.getInt("dhrTemplate_id");
/*  790 */           templateObj.name = connector.getField("templateName", "");
/*  791 */           templateObj.streetDates = reportStreeDates;
/*  792 */           templateObj.totalCount = totalCount;
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  801 */         taskObj = new TaskObj(this);
/*      */ 
/*      */         
/*  804 */         taskObj.taskId = connector.getInt("task_Id");
/*      */ 
/*      */         
/*  807 */         taskObj.name = connector.getField("taskName", "");
/*      */ 
/*      */         
/*  810 */         taskObj.dayOfWeek = connector.getInt("day_of_week", -1);
/*      */ 
/*      */         
/*  813 */         taskObj.weeksToRelease = connector.getInt("weeks_to_release", -1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  819 */         if (taskObj.dayOfWeek == 9) {
/*      */           
/*  821 */           taskObj.dueDates = computeSOLdates(templateObj);
/*  822 */           taskObj.isSOL = true;
/*  823 */           hasSOL = true;
/*      */         }
/*      */         else {
/*      */           
/*  827 */           taskObj.dueDates = computeDueDates(effDateFrom, effDateTo, taskObj.weeksToRelease, 
/*  828 */               taskObj.dayOfWeek, weekDayConv, templateObj.streetDates.size());
/*      */         } 
/*      */ 
/*      */         
/*  832 */         taskObjs.add(taskObj);
/*      */         
/*  834 */         taskObj = null;
/*      */         
/*  836 */         connector.next();
/*      */       
/*      */       }
/*  839 */       catch (Exception e) {
/*  840 */         e.printStackTrace();
/*      */ 
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  848 */     if (!isFirstRecord) {
/*  849 */       templateObj.taskObjs = taskObjs;
/*  850 */       templateObj.hasSOL = hasSOL;
/*  851 */       templateObjs.add(templateObj);
/*      */     } 
/*      */ 
/*      */     
/*  855 */     connector.close();
/*      */ 
/*      */     
/*  858 */     taskObjs = null;
/*  859 */     templateObj = null;
/*  860 */     taskObj = null;
/*      */     
/*  862 */     return templateObjs;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ArrayList computeDueDates(Calendar effDateFrom, Calendar effDateTo, int weeksToRel, int dayOfWeek, HashMap weekDayConv, int numStreetDates) {
/*  872 */     ArrayList dueDates = new ArrayList();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  879 */     if (dayOfWeek < 0 || dayOfWeek > 7) {
/*      */ 
/*      */       
/*  882 */       int dayOfTheWeek = effDateFrom.get(7);
/*      */ 
/*      */ 
/*      */       
/*  886 */       int liRest = weeksToRel - dayOfTheWeek + 1;
/*  887 */       int liOffset = 0;
/*      */ 
/*      */       
/*  890 */       if (liRest >= 0)
/*      */       {
/*      */ 
/*      */         
/*  894 */         liOffset = (liRest / 5 + 1) * 2;
/*      */       }
/*      */       
/*  897 */       int days = weeksToRel - liOffset;
/*      */       
/*  899 */       Calendar dueDate = (Calendar)effDateFrom.clone();
/*  900 */       Calendar dueDateTo = (Calendar)effDateTo.clone();
/*      */       
/*  902 */       dueDate.add(5, -weeksToRel - liOffset);
/*  903 */       dueDateTo.add(5, -weeksToRel - liOffset);
/*      */       
/*  905 */       Calendar newDate = Calendar.getInstance();
/*  906 */       newDate.setTime(dueDate.getTime());
/*  907 */       dueDates.add(newDate);
/*      */ 
/*      */       
/*  910 */       while (dueDate.before(dueDateTo) || dueDates.size() < numStreetDates) {
/*  911 */         dueDate.add(5, 7);
/*  912 */         newDate = Calendar.getInstance();
/*  913 */         newDate.setTime(dueDate.getTime());
/*  914 */         dueDates.add(newDate);
/*      */ 
/*      */       
/*      */       }
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/*  924 */       int days = weeksToRel * 7;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  929 */       Calendar dueDate = (Calendar)effDateFrom.clone();
/*      */ 
/*      */       
/*  932 */       dueDate.add(5, days * -1);
/*      */       
/*  934 */       int calDay = dueDate.get(7);
/*      */ 
/*      */       
/*  937 */       int convDay = calDay;
/*      */ 
/*      */       
/*  940 */       if (weekDayConv.get(new Integer(dayOfWeek)) != null) {
/*  941 */         convDay = ((Integer)weekDayConv.get(new Integer(dayOfWeek))).intValue();
/*      */       }
/*      */ 
/*      */       
/*  945 */       if (convDay != calDay) {
/*  946 */         dueDate.add(5, convDay - calDay);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  951 */       Calendar dueDateTo = (Calendar)effDateTo.clone();
/*      */       
/*  953 */       dueDateTo.add(5, days * -1);
/*      */       
/*  955 */       calDay = dueDateTo.get(7);
/*      */ 
/*      */       
/*  958 */       if (convDay != calDay) {
/*  959 */         dueDateTo.add(5, convDay - calDay);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  965 */       Calendar newDate = Calendar.getInstance();
/*  966 */       newDate.setTime(dueDate.getTime());
/*  967 */       dueDates.add(newDate);
/*      */ 
/*      */       
/*  970 */       while (dueDate.before(dueDateTo) || dueDates.size() < numStreetDates) {
/*  971 */         dueDate.add(5, 7);
/*  972 */         newDate = Calendar.getInstance();
/*  973 */         newDate.setTime(dueDate.getTime());
/*  974 */         dueDates.add(newDate);
/*      */       } 
/*      */     } 
/*      */     
/*  978 */     return dueDates;
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
/*      */   public void updateSortVar(TemplateObj templateObj) {
/*  990 */     int sortCol = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  995 */     if (templateObj.hasSOL)
/*      */     {
/*      */       
/*  998 */       for (int x = 0; x < templateObj.taskObjs.size(); x++) {
/*      */         
/* 1000 */         TaskObj taskObj = (TaskObj)templateObj.taskObjs.get(x);
/* 1001 */         if (taskObj.isSOL) {
/*      */ 
/*      */           
/* 1004 */           for (int y = 0; y < taskObj.dueDates.size(); y++) {
/*      */             
/* 1006 */             if (taskObj.dueDates.get(y) != null);
/*      */             
/* 1008 */             String dueDate = MilestoneHelper.getFormatedDate((Calendar)taskObj.dueDates.get(y));
/* 1009 */             if (!dueDate.equals("")) {
/*      */               
/* 1011 */               sortCol = y;
/*      */ 
/*      */               
/*      */               break;
/*      */             } 
/*      */           } 
/*      */ 
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/* 1024 */     if (templateObj.taskObjs != null) {
/* 1025 */       for (int x = 0; x < templateObj.taskObjs.size(); x++) {
/*      */         
/* 1027 */         TaskObj taskObj = (TaskObj)templateObj.taskObjs.get(x);
/*      */         
/* 1029 */         if (taskObj != null && taskObj.dueDates != null && 
/* 1030 */           taskObj.dueDates.get(sortCol) != null) {
/*      */           
/* 1032 */           taskObj.compareDate.setTime(((Calendar)taskObj.dueDates.get(sortCol)).getTime());
/* 1033 */           templateObj.taskObjs.set(x, taskObj);
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ArrayList computeStreetDates(Calendar effDateFrom, Calendar effDateTo) {
/* 1046 */     ArrayList streetDates = new ArrayList();
/*      */     
/* 1048 */     Calendar streetDate = (Calendar)effDateFrom.clone();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1053 */     Calendar newDate = Calendar.getInstance();
/* 1054 */     newDate.setTime(streetDate.getTime());
/* 1055 */     streetDates.add(newDate);
/*      */ 
/*      */     
/* 1058 */     while (streetDate.before(effDateTo)) {
/* 1059 */       streetDate.add(5, 7);
/*      */       
/* 1061 */       newDate = Calendar.getInstance();
/* 1062 */       newDate.setTime(streetDate.getTime());
/* 1063 */       streetDates.add(newDate);
/*      */     } 
/*      */ 
/*      */     
/* 1067 */     return streetDates;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ArrayList computeSOLdates(TemplateObj templateObj) {
/* 1073 */     ArrayList streetDates = templateObj.streetDates;
/* 1074 */     ArrayList solDates = new ArrayList();
/*      */ 
/*      */     
/* 1077 */     for (int i = 0; i < streetDates.size(); i++) {
/*      */       
/* 1079 */       Calendar streetDate = (Calendar)streetDates.get(i);
/* 1080 */       DatePeriod datePeriod = MilestoneHelper.findDatePeriod(streetDate);
/* 1081 */       if (datePeriod != null && datePeriod.getSolDate() != null) {
/* 1082 */         Calendar newDate = Calendar.getInstance();
/* 1083 */         newDate.setTime(datePeriod.getSolDate().getTime());
/* 1084 */         solDates.add(newDate);
/*      */       } else {
/* 1086 */         solDates.add(null);
/*      */       } 
/*      */     } 
/* 1089 */     return solDates;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public HashMap buildDayConvTable() {
/* 1163 */     HashMap dayConv = new HashMap();
/*      */     
/* 1165 */     dayConv.put(new Integer(7), new Integer(1));
/* 1166 */     dayConv.put(new Integer(1), new Integer(2));
/* 1167 */     dayConv.put(new Integer(2), new Integer(3));
/* 1168 */     dayConv.put(new Integer(3), new Integer(4));
/* 1169 */     dayConv.put(new Integer(4), new Integer(5));
/* 1170 */     dayConv.put(new Integer(5), new Integer(6));
/* 1171 */     dayConv.put(new Integer(6), new Integer(7));
/* 1172 */     dayConv.put(new Integer(0), new Integer(1));
/* 1173 */     dayConv.put(new Integer(-1), new Integer(1));
/*      */     
/* 1175 */     return dayConv;
/*      */   }
/*      */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\FutureTaskDueDatesByTemplateSubHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */