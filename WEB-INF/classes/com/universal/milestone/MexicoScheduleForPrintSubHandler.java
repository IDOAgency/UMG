/*      */ package WEB-INF.classes.com.universal.milestone;
/*      */ 
/*      */ import com.techempower.ComponentLog;
/*      */ import com.techempower.gemini.Context;
/*      */ import com.techempower.gemini.GeminiApplication;
/*      */ import com.universal.milestone.Form;
/*      */ import com.universal.milestone.MexicoReportComparator;
/*      */ import com.universal.milestone.MexicoScheduleForPrintSubHandler;
/*      */ import com.universal.milestone.MilestoneHelper;
/*      */ import com.universal.milestone.MilestoneHelper_2;
/*      */ import com.universal.milestone.Plant;
/*      */ import com.universal.milestone.Schedule;
/*      */ import com.universal.milestone.ScheduledTask;
/*      */ import com.universal.milestone.SecureHandler;
/*      */ import com.universal.milestone.Selection;
/*      */ import com.universal.milestone.SelectionManager;
/*      */ import inetsoft.report.SectionBand;
/*      */ import inetsoft.report.XStyleSheet;
/*      */ import inetsoft.report.lens.DefaultSectionLens;
/*      */ import inetsoft.report.lens.DefaultTableLens;
/*      */ import java.awt.Color;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Font;
/*      */ import java.awt.Insets;
/*      */ import java.awt.Rectangle;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.Arrays;
/*      */ import java.util.Calendar;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Hashtable;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class MexicoScheduleForPrintSubHandler
/*      */   extends SecureHandler
/*      */ {
/*      */   public static final String COMPONENT_CODE = "hMCAProd";
/*      */   public GeminiApplication application;
/*      */   public ComponentLog log;
/*      */   
/*      */   public void MexicoScheduleForPrintSubHandler(GeminiApplication application) {
/*   68 */     this.application = application;
/*   69 */     this.log = application.getLog("hMCAProd");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   77 */   public String getDescription() { return "Sub Report"; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static void fillMexicoScheduleForPrint(XStyleSheet report, Context context) {
/*   89 */     int COL_LINE_STYLE = 4097;
/*   90 */     int HEADER_FONT_SIZE = 12;
/*      */     
/*   92 */     double ldLineVal = 0.3D;
/*      */     
/*      */     try {
/*   95 */       HttpServletResponse sresponse = context.getResponse();
/*   96 */       context.putDelivery("status", new String("start_gathering"));
/*   97 */       context.includeJSP("status.jsp", "hiddenFrame");
/*   98 */       sresponse.setContentType("text/plain");
/*   99 */       sresponse.flushBuffer();
/*      */     }
/*  101 */     catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  106 */     Vector selections = MilestoneHelper.getSelectionsForReport(context);
/*      */     
/*      */     try {
/*  109 */       HttpServletResponse sresponse = context.getResponse();
/*  110 */       context.putDelivery("status", new String("start_report"));
/*  111 */       context.putDelivery("percent", new String("10"));
/*  112 */       context.includeJSP("status.jsp", "hiddenFrame");
/*  113 */       sresponse.setContentType("text/plain");
/*  114 */       sresponse.flushBuffer();
/*      */     }
/*  116 */     catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  121 */     int DATA_FONT_SIZE = 7;
/*  122 */     int SMALL_HEADER_FONT_SIZE = 8;
/*  123 */     Color SHADED_AREA_COLOR = Color.lightGray;
/*  124 */     int NUM_COLUMNS = 0;
/*      */     
/*  126 */     SectionBand hbandType = new SectionBand(report);
/*  127 */     SectionBand hbandCategory = new SectionBand(report);
/*  128 */     SectionBand hbandDate = new SectionBand(report);
/*  129 */     SectionBand hbandRelWeek = new SectionBand(report);
/*  130 */     SectionBand body = new SectionBand(report);
/*  131 */     SectionBand footer = new SectionBand(report);
/*  132 */     SectionBand spacer = new SectionBand(report);
/*  133 */     DefaultSectionLens group = null;
/*      */     
/*  135 */     footer.setVisible(true);
/*  136 */     footer.setHeight(0.1F);
/*  137 */     footer.setShrinkToFit(false);
/*  138 */     footer.setBottomBorder(0);
/*      */     
/*  140 */     spacer.setVisible(true);
/*  141 */     spacer.setHeight(0.05F);
/*  142 */     spacer.setShrinkToFit(false);
/*  143 */     spacer.setBottomBorder(0);
/*      */ 
/*      */     
/*  146 */     Hashtable selTable = MilestoneHelper_2.groupSelectionsForMexicoProductionByTypeConfigAndStreetDate(selections);
/*  147 */     Enumeration configs = selTable.keys();
/*  148 */     Vector configVector = new Vector();
/*      */     
/*  150 */     while (configs.hasMoreElements()) {
/*  151 */       configVector.addElement(configs.nextElement());
/*      */     }
/*  153 */     int numConfigs = configVector.size();
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  158 */       DefaultTableLens table_contents = null;
/*  159 */       DefaultTableLens rowCountTable = null;
/*  160 */       DefaultTableLens columnHeaderTable = null;
/*  161 */       DefaultTableLens subTable = null;
/*  162 */       DefaultTableLens monthTableLens = null;
/*  163 */       DefaultTableLens dateTableLens = null;
/*  164 */       DefaultTableLens relWeekLens = null;
/*      */       
/*  166 */       rowCountTable = new DefaultTableLens(2, 10000);
/*      */       
/*  168 */       int totalCount = 0;
/*  169 */       int tenth = 1;
/*      */ 
/*      */       
/*  172 */       for (int n = 0; n < configVector.size(); n++) {
/*      */         
/*  174 */         String configC = (configVector.elementAt(n) != null) ? (String)configVector.elementAt(n) : "";
/*  175 */         Vector selectionsVector = (Vector)selTable.get(configC);
/*      */         
/*  177 */         totalCount += selectionsVector.size();
/*      */       } 
/*      */ 
/*      */       
/*  181 */       tenth = (totalCount > 10) ? (totalCount / 10) : 1;
/*      */       
/*  183 */       HttpServletResponse sresponse = context.getResponse();
/*  184 */       context.putDelivery("status", new String("start_report"));
/*  185 */       context.putDelivery("percent", new String("20"));
/*  186 */       context.includeJSP("status.jsp", "hiddenFrame");
/*  187 */       sresponse.setContentType("text/plain");
/*  188 */       sresponse.flushBuffer();
/*      */       
/*  190 */       int recordCount = 0;
/*  191 */       int count = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  197 */       Form reportForm = (Form)context.getSessionValue("reportForm");
/*      */       
/*  199 */       Calendar beginStDate = (reportForm.getStringValue("beginDate") != null && reportForm.getStringValue("beginDate").length() > 0) ? 
/*  200 */         MilestoneHelper.getDate(reportForm.getStringValue("beginDate")) : null;
/*      */       
/*  202 */       Calendar endStDate = (reportForm.getStringValue("endDate") != null && reportForm.getStringValue("endDate").length() > 0) ? 
/*  203 */         MilestoneHelper.getDate(reportForm.getStringValue("endDate")) : null;
/*      */       
/*  205 */       report.setElement("crs_startdate", MilestoneHelper.getCustomFormatedDate(beginStDate, "d-MMM yy"));
/*  206 */       report.setElement("crs_enddate", MilestoneHelper.getCustomFormatedDate(endStDate, "d-MMM yy"));
/*      */       
/*  208 */       SimpleDateFormat formatter = new SimpleDateFormat("d-MMM yyyy");
/*      */       
/*  210 */       String todayLong = formatter.format(new Date());
/*  211 */       report.setElement("crs_bottomdate", "Version: " + todayLong);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  218 */       String a4Landscape = "11.75x8.25";
/*  219 */       report.setProperty("PageSize", a4Landscape);
/*      */ 
/*      */       
/*  222 */       Object[] configArray = configVector.toArray();
/*  223 */       Arrays.sort(configArray, new MexicoReportComparator());
/*      */ 
/*      */       
/*  226 */       Vector sortedConfigs = new Vector();
/*  227 */       for (int i = 0; i < configArray.length; i++) {
/*  228 */         sortedConfigs.addElement((String)configArray[i]);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  233 */       Vector displayCatalogNumberVector = new Vector();
/*  234 */       displayCatalogNumberVector.add("Promos in Spanish");
/*  235 */       displayCatalogNumberVector.add("Promos in English");
/*  236 */       displayCatalogNumberVector.add("Distributed Promos");
/*      */ 
/*      */       
/*  239 */       for (int n = 0; n < sortedConfigs.size(); n++)
/*      */       {
/*      */         
/*  242 */         String configBefore = (sortedConfigs.elementAt(n) != null) ? (String)sortedConfigs.elementAt(n) : "";
/*      */ 
/*      */         
/*  245 */         String config = configBefore.substring(0, configBefore.indexOf("|"));
/*      */         
/*  247 */         String dateName = configBefore.substring(configBefore.indexOf("|") + 1, configBefore.length());
/*      */         
/*  249 */         Vector selectionsForConfig = (Vector)selTable.get(configBefore);
/*      */         
/*  251 */         int numRows = 0;
/*  252 */         numRows += totalCount * 2;
/*  253 */         numRows += totalCount * 2;
/*  254 */         numRows += 5;
/*      */ 
/*      */         
/*  257 */         hbandType = new SectionBand(report);
/*  258 */         hbandType.setHeight(0.95F);
/*  259 */         hbandType.setShrinkToFit(true);
/*  260 */         hbandType.setVisible(true);
/*      */ 
/*      */         
/*  263 */         if (config.equals("Commercial")) {
/*      */           
/*  265 */           NUM_COLUMNS = 16;
/*      */         } else {
/*  267 */           NUM_COLUMNS = 14;
/*      */         } 
/*      */ 
/*      */         
/*  271 */         boolean configException = false;
/*  272 */         if (config.equals("Distributed Commercial")) {
/*  273 */           configException = true;
/*      */         }
/*      */         
/*  276 */         table_contents = new DefaultTableLens(1, NUM_COLUMNS);
/*      */ 
/*      */         
/*  279 */         table_contents.setHeaderRowCount(0);
/*  280 */         table_contents.setColBorderColor(Color.lightGray);
/*      */         
/*  282 */         table_contents.setColWidth(0, 80);
/*  283 */         table_contents.setColWidth(1, 200);
/*  284 */         table_contents.setColWidth(2, 200);
/*  285 */         table_contents.setColWidth(3, 80);
/*  286 */         table_contents.setColWidth(4, 80);
/*  287 */         table_contents.setColWidth(5, 80);
/*  288 */         table_contents.setColWidth(6, 80);
/*  289 */         table_contents.setColWidth(7, 80);
/*  290 */         table_contents.setColWidth(8, 80);
/*  291 */         table_contents.setColWidth(9, 80);
/*  292 */         table_contents.setColWidth(10, 80);
/*  293 */         table_contents.setColWidth(11, 80);
/*  294 */         if (NUM_COLUMNS == 14) {
/*  295 */           table_contents.setColWidth(12, 150);
/*  296 */           table_contents.setColWidth(13, 150);
/*      */         } else {
/*  298 */           table_contents.setColWidth(12, 80);
/*  299 */           table_contents.setColWidth(13, 80);
/*  300 */           table_contents.setColWidth(14, 150);
/*  301 */           table_contents.setColWidth(15, 150);
/*      */         } 
/*      */         
/*  304 */         table_contents.setColBorderColor(Color.black);
/*  305 */         table_contents.setRowBorderColor(Color.black);
/*  306 */         table_contents.setRowBorder(4097);
/*  307 */         table_contents.setColBorder(4097);
/*      */ 
/*      */         
/*  310 */         int nextRow = 0;
/*      */         
/*  312 */         if (NUM_COLUMNS == 14) {
/*  313 */           table_contents.setSpan(nextRow, 0, new Dimension(14, 1));
/*      */         } else {
/*  315 */           table_contents.setSpan(nextRow, 0, new Dimension(16, 1));
/*      */         } 
/*  317 */         table_contents.setAlignment(nextRow, 0, 2);
/*      */         
/*  319 */         if (config.equals("")) {
/*  320 */           config = "Other";
/*      */         }
/*      */         
/*  323 */         table_contents.setObject(nextRow, 0, config);
/*  324 */         table_contents.setRowHeight(nextRow, 16);
/*      */         
/*  326 */         table_contents.setRowBorderColor(nextRow, Color.black);
/*  327 */         table_contents.setRowBorder(nextRow, 0, 266240);
/*      */         
/*  329 */         table_contents.setRowFont(nextRow, new Font("Arial", 3, 12));
/*  330 */         table_contents.setRowBackground(nextRow, Color.white);
/*  331 */         table_contents.setRowForeground(nextRow, Color.black);
/*      */         
/*  333 */         table_contents.setRowBorder(nextRow - 1, 266240);
/*  334 */         table_contents.setColBorder(nextRow, -1, 266240);
/*  335 */         table_contents.setColBorder(nextRow, 0, 266240);
/*  336 */         table_contents.setRowBorder(nextRow, 266240);
/*  337 */         table_contents.setRowBorderColor(nextRow - 1, Color.black);
/*  338 */         table_contents.setColBorderColor(nextRow, -1, Color.black);
/*  339 */         table_contents.setColBorderColor(nextRow, 0, Color.black);
/*  340 */         table_contents.setRowBorderColor(nextRow, Color.black);
/*      */         
/*  342 */         hbandType.addTable(table_contents, new Rectangle(0, 0, 800, 25));
/*      */         
/*  344 */         nextRow = 0;
/*      */         
/*  346 */         columnHeaderTable = new DefaultTableLens(1, NUM_COLUMNS);
/*      */ 
/*      */         
/*  349 */         columnHeaderTable.setHeaderRowCount(0);
/*  350 */         columnHeaderTable.setColBorderColor(Color.lightGray);
/*      */         
/*  352 */         columnHeaderTable.setColWidth(0, 80);
/*  353 */         columnHeaderTable.setColWidth(1, 200);
/*  354 */         columnHeaderTable.setColWidth(2, 200);
/*  355 */         columnHeaderTable.setColWidth(3, 80);
/*  356 */         columnHeaderTable.setColWidth(4, 80);
/*  357 */         columnHeaderTable.setColWidth(5, 80);
/*  358 */         columnHeaderTable.setColWidth(6, 80);
/*  359 */         columnHeaderTable.setColWidth(7, 80);
/*  360 */         columnHeaderTable.setColWidth(8, 80);
/*  361 */         columnHeaderTable.setColWidth(9, 80);
/*  362 */         columnHeaderTable.setColWidth(10, 80);
/*  363 */         columnHeaderTable.setColWidth(11, 80);
/*  364 */         if (NUM_COLUMNS == 14) {
/*  365 */           columnHeaderTable.setColWidth(12, 150);
/*  366 */           columnHeaderTable.setColWidth(13, 150);
/*      */         } else {
/*      */           
/*  369 */           columnHeaderTable.setColWidth(12, 80);
/*  370 */           columnHeaderTable.setColWidth(13, 80);
/*  371 */           columnHeaderTable.setColWidth(14, 150);
/*  372 */           columnHeaderTable.setColWidth(15, 150);
/*      */         } 
/*      */ 
/*      */         
/*  376 */         columnHeaderTable.setAlignment(nextRow, 0, 33);
/*  377 */         columnHeaderTable.setAlignment(nextRow, 1, 34);
/*  378 */         columnHeaderTable.setAlignment(nextRow, 2, 34);
/*  379 */         columnHeaderTable.setAlignment(nextRow, 3, 34);
/*  380 */         columnHeaderTable.setAlignment(nextRow, 4, 34);
/*  381 */         columnHeaderTable.setAlignment(nextRow, 5, 34);
/*  382 */         columnHeaderTable.setAlignment(nextRow, 6, 34);
/*  383 */         columnHeaderTable.setAlignment(nextRow, 7, 34);
/*  384 */         columnHeaderTable.setAlignment(nextRow, 8, 34);
/*  385 */         columnHeaderTable.setAlignment(nextRow, 9, 34);
/*  386 */         columnHeaderTable.setAlignment(nextRow, 10, 34);
/*  387 */         columnHeaderTable.setAlignment(nextRow, 11, 34);
/*  388 */         columnHeaderTable.setAlignment(nextRow, 12, 34);
/*  389 */         columnHeaderTable.setAlignment(nextRow, 13, 34);
/*  390 */         if (NUM_COLUMNS == 16) {
/*  391 */           columnHeaderTable.setAlignment(nextRow, 14, 34);
/*  392 */           columnHeaderTable.setAlignment(nextRow, 15, 34);
/*      */         } 
/*      */ 
/*      */         
/*  396 */         columnHeaderTable.setObject(nextRow, 0, "Price\nCode");
/*  397 */         if (displayCatalogNumberVector.contains(config)) {
/*  398 */           columnHeaderTable.setObject(nextRow, 1, "Catalog\nNumber");
/*      */         } else {
/*  400 */           columnHeaderTable.setObject(nextRow, 1, "UPC");
/*      */         } 
/*  402 */         columnHeaderTable.setObject(nextRow, 2, "Artist\nTitle");
/*  403 */         columnHeaderTable.setObject(nextRow, 3, "Label");
/*  404 */         columnHeaderTable.setObject(nextRow, 4, "Origin\nDate");
/*  405 */         columnHeaderTable.setObject(nextRow, 5, "Config");
/*  406 */         columnHeaderTable.setObject(nextRow, 6, "Initial\nQty");
/*  407 */         columnHeaderTable.setObject(nextRow, 7, "Vendor");
/*  408 */         columnHeaderTable.setObject(nextRow, 8, "Label\nCopy");
/*  409 */         columnHeaderTable.setObject(nextRow, 9, "Dat\nMaster");
/*  410 */         columnHeaderTable.setObject(nextRow, 10, "Artwork");
/*  411 */         columnHeaderTable.setObject(nextRow, 11, "Stock\nArrives");
/*  412 */         if (NUM_COLUMNS == 14) {
/*  413 */           columnHeaderTable.setObject(nextRow, 12, "Comments");
/*  414 */           columnHeaderTable.setObject(nextRow, 13, "Label\nManager");
/*      */         } else {
/*      */           
/*  417 */           columnHeaderTable.setObject(nextRow, 12, "Label\nMgr");
/*  418 */           columnHeaderTable.setObject(nextRow, 13, "Press\nMgr");
/*  419 */           columnHeaderTable.setObject(nextRow, 14, "Comments");
/*  420 */           columnHeaderTable.setObject(nextRow, 15, "Label\nManager");
/*      */         } 
/*      */         
/*  423 */         columnHeaderTable.setRowBorderColor(nextRow - 1, SHADED_AREA_COLOR);
/*  424 */         columnHeaderTable.setRowBorderColor(nextRow, SHADED_AREA_COLOR);
/*  425 */         columnHeaderTable.setRowFont(nextRow, new Font("Arial", 1, 8));
/*  426 */         columnHeaderTable.setRowBackground(nextRow, Color.white);
/*  427 */         columnHeaderTable.setRowForeground(nextRow, Color.black);
/*      */         
/*  429 */         hbandType.addTable(columnHeaderTable, new Rectangle(0, 24, 800, 45));
/*  430 */         hbandType.setBottomBorder(0);
/*      */ 
/*      */         
/*  433 */         Hashtable totals = new Hashtable();
/*      */ 
/*      */         
/*  436 */         monthTableLens = new DefaultTableLens(1, NUM_COLUMNS);
/*  437 */         hbandCategory = new SectionBand(report);
/*  438 */         hbandCategory.setHeight(0.25F);
/*  439 */         hbandCategory.setShrinkToFit(true);
/*  440 */         hbandCategory.setVisible(true);
/*  441 */         hbandCategory.setBottomBorder(0);
/*  442 */         hbandCategory.setLeftBorder(0);
/*  443 */         hbandCategory.setRightBorder(0);
/*  444 */         hbandCategory.setTopBorder(0);
/*      */         
/*  446 */         nextRow = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  451 */         monthTableLens.setObject(nextRow, 0, reformatMexicoDate(dateName));
/*  452 */         monthTableLens.setFont(nextRow, 0, new Font("Arial", 1, 12));
/*      */ 
/*      */ 
/*      */         
/*  456 */         String MC = "";
/*  457 */         String TPS = "";
/*  458 */         String FAD = "";
/*  459 */         String STA = "";
/*  460 */         String LMD = "";
/*  461 */         String PMD = "";
/*      */         
/*  463 */         if (NUM_COLUMNS == 14 && !configException) {
/*  464 */           monthTableLens.setSpan(nextRow, 0, new Dimension(14, 1));
/*  465 */           monthTableLens.setColBorderColor(nextRow, 13, Color.white);
/*      */         }
/*      */         else {
/*      */           
/*  469 */           monthTableLens.setColWidth(0, 80);
/*  470 */           monthTableLens.setColWidth(1, 200);
/*  471 */           monthTableLens.setColWidth(2, 200);
/*  472 */           monthTableLens.setColWidth(3, 80);
/*  473 */           monthTableLens.setColWidth(4, 80);
/*  474 */           monthTableLens.setColWidth(5, 80);
/*  475 */           monthTableLens.setColWidth(6, 80);
/*  476 */           monthTableLens.setColWidth(7, 80);
/*  477 */           monthTableLens.setColWidth(8, 80);
/*  478 */           monthTableLens.setColWidth(9, 80);
/*  479 */           monthTableLens.setColWidth(10, 80);
/*  480 */           monthTableLens.setColWidth(11, 80);
/*  481 */           if (configException) {
/*  482 */             monthTableLens.setColWidth(12, 150);
/*  483 */             monthTableLens.setColWidth(13, 150);
/*  484 */             monthTableLens.setColBorderColor(nextRow, 13, Color.white);
/*      */           } else {
/*  486 */             monthTableLens.setColWidth(12, 80);
/*  487 */             monthTableLens.setColWidth(13, 80);
/*  488 */             monthTableLens.setColWidth(14, 150);
/*  489 */             monthTableLens.setColWidth(15, 150);
/*  490 */             monthTableLens.setColBorderColor(nextRow, 15, Color.white);
/*      */           } 
/*      */           
/*  493 */           monthTableLens.setSpan(nextRow, 0, new Dimension(2, 1));
/*      */ 
/*      */           
/*  496 */           if (!dateName.equals("TBS")) {
/*  497 */             monthTableLens.setObject(nextRow, 6, "Due Dates");
/*  498 */             monthTableLens.setFont(nextRow, 6, new Font("Arial", 1, 8));
/*  499 */             monthTableLens.setSpan(nextRow, 6, new Dimension(2, 1));
/*  500 */             monthTableLens.setAlignment(nextRow, 6, 2);
/*      */ 
/*      */             
/*  503 */             Selection tempSel = new Selection();
/*  504 */             for (int selCounter = 0; selCounter < selectionsForConfig.size(); selCounter++) {
/*  505 */               tempSel = (Selection)selectionsForConfig.elementAt(selCounter);
/*  506 */               Schedule schedule = tempSel.getSchedule();
/*  507 */               if (schedule != null) {
/*      */                 break;
/*      */               }
/*      */             } 
/*      */             
/*  512 */             if (tempSel != null) {
/*  513 */               Schedule schedule = tempSel.getSchedule();
/*  514 */               Vector tasks = (schedule != null) ? schedule.getTasks() : null;
/*  515 */               ScheduledTask task = null;
/*  516 */               if (tasks != null) {
/*  517 */                 for (int j = 0; j < tasks.size(); j++) {
/*  518 */                   task = (ScheduledTask)tasks.get(j);
/*  519 */                   if (task != null) {
/*      */                     
/*  521 */                     String dueDate = "";
/*  522 */                     if (task.getDueDate() != null) {
/*  523 */                       SimpleDateFormat dueDateFormatter = new SimpleDateFormat(
/*  524 */                           "d-MMM");
/*  525 */                       dueDate = 
/*  526 */                         String.valueOf(dueDateFormatter.format(task.getDueDate().getTime())) + " " + 
/*  527 */                         MilestoneHelper.getDayType(tempSel.getCalendarGroup(), 
/*  528 */                           task.getDueDate());
/*      */                     } 
/*      */                     
/*  531 */                     String taskAbbrev = 
/*  532 */                       MilestoneHelper.getTaskAbbreivationNameById(task.getTaskAbbreviationID());
/*      */ 
/*      */ 
/*      */ 
/*      */                     
/*  537 */                     if (taskAbbrev.equalsIgnoreCase("M/C")) {
/*  538 */                       MC = dueDate;
/*      */                     }
/*  540 */                     if (taskAbbrev.equalsIgnoreCase("TPS")) {
/*  541 */                       TPS = dueDate;
/*      */                     }
/*  543 */                     if (taskAbbrev.equalsIgnoreCase("FAD")) {
/*  544 */                       FAD = dueDate;
/*      */                     }
/*  546 */                     if (taskAbbrev.equalsIgnoreCase("STA")) {
/*  547 */                       STA = dueDate;
/*      */                     }
/*      */                     
/*  550 */                     if (!configException) {
/*  551 */                       if (taskAbbrev.equalsIgnoreCase("LMD")) {
/*  552 */                         LMD = dueDate;
/*      */                       }
/*  554 */                       if (taskAbbrev.equalsIgnoreCase("PMD")) {
/*  555 */                         PMD = dueDate;
/*      */                       }
/*      */                     } 
/*      */ 
/*      */ 
/*      */                     
/*  561 */                     task = null;
/*      */                   } 
/*      */                 } 
/*      */               }
/*      */             } 
/*      */ 
/*      */             
/*  568 */             monthTableLens.setObject(nextRow, 8, MC);
/*  569 */             monthTableLens.setObject(nextRow, 9, TPS);
/*  570 */             monthTableLens.setObject(nextRow, 10, FAD);
/*  571 */             monthTableLens.setObject(nextRow, 11, STA);
/*  572 */             monthTableLens.setObject(nextRow, 12, LMD);
/*  573 */             monthTableLens.setObject(nextRow, 13, PMD);
/*  574 */             monthTableLens.setAlignment(nextRow, 8, 2);
/*  575 */             monthTableLens.setAlignment(nextRow, 9, 2);
/*  576 */             monthTableLens.setAlignment(nextRow, 10, 2);
/*  577 */             monthTableLens.setAlignment(nextRow, 11, 2);
/*  578 */             monthTableLens.setAlignment(nextRow, 12, 2);
/*  579 */             monthTableLens.setAlignment(nextRow, 13, 2);
/*  580 */             monthTableLens.setFont(nextRow, 8, new Font("Arial", 0, 8));
/*  581 */             monthTableLens.setFont(nextRow, 9, new Font("Arial", 0, 8));
/*  582 */             monthTableLens.setFont(nextRow, 10, new Font("Arial", 0, 8));
/*  583 */             monthTableLens.setFont(nextRow, 11, new Font("Arial", 0, 8));
/*  584 */             monthTableLens.setFont(nextRow, 12, new Font("Arial", 0, 8));
/*  585 */             monthTableLens.setFont(nextRow, 13, new Font("Arial", 0, 8));
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/*  590 */         monthTableLens.setColBorderColor(SHADED_AREA_COLOR);
/*  591 */         monthTableLens.setColBorderColor(nextRow, -1, Color.white);
/*  592 */         monthTableLens.setColBorderColor(nextRow, 0, Color.white);
/*      */         
/*  594 */         monthTableLens.setRowHeight(nextRow, 13);
/*  595 */         monthTableLens.setRowBackground(nextRow, SHADED_AREA_COLOR);
/*  596 */         monthTableLens.setRowForeground(nextRow, Color.black);
/*  597 */         monthTableLens.setRowBorderColor(nextRow, SHADED_AREA_COLOR);
/*  598 */         monthTableLens.setRowBorderColor(nextRow - 1, SHADED_AREA_COLOR);
/*      */         
/*  600 */         hbandCategory.addTable(monthTableLens, new Rectangle(800, 800));
/*      */         
/*  602 */         footer.setVisible(true);
/*  603 */         footer.setHeight(0.1F);
/*  604 */         footer.setShrinkToFit(false);
/*  605 */         footer.setBottomBorder(0);
/*      */         
/*  607 */         group = new DefaultSectionLens(null, group, spacer);
/*  608 */         group = new DefaultSectionLens(null, group, hbandCategory);
/*  609 */         group = new DefaultSectionLens(null, group, spacer);
/*      */ 
/*      */         
/*  612 */         if (selectionsForConfig == null) {
/*  613 */           selectionsForConfig = new Vector();
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  619 */         MilestoneHelper.setSelectionSorting(selectionsForConfig, 4);
/*  620 */         Collections.sort(selectionsForConfig);
/*      */         
/*  622 */         MilestoneHelper.setSelectionSorting(selectionsForConfig, 3);
/*  623 */         Collections.sort(selectionsForConfig);
/*      */         
/*  625 */         if (config.equals("Distributed Promos") || config.equals("Distributed Commercial")) {
/*      */           
/*  627 */           MilestoneHelper.setSelectionSorting(selectionsForConfig, 9);
/*  628 */           Collections.sort(selectionsForConfig);
/*      */         } 
/*      */         
/*  631 */         MilestoneHelper.applyManufacturingToSelections(selectionsForConfig);
/*      */ 
/*      */         
/*  634 */         for (int i = 0; i < selectionsForConfig.size(); i++) {
/*      */           
/*  636 */           Selection sel = (Selection)selectionsForConfig.elementAt(i);
/*      */ 
/*      */           
/*  639 */           if (count < recordCount / tenth) {
/*  640 */             count = recordCount / tenth;
/*  641 */             sresponse = context.getResponse();
/*  642 */             context.putDelivery("status", new String("start_report"));
/*  643 */             int myPercent = count * 10;
/*  644 */             if (myPercent > 90)
/*  645 */               myPercent = 90; 
/*  646 */             context.putDelivery("percent", 
/*  647 */                 new String(String.valueOf(myPercent)));
/*  648 */             context.includeJSP("status.jsp", "hiddenFrame");
/*  649 */             sresponse.setContentType("text/plain");
/*  650 */             sresponse.flushBuffer();
/*      */           } 
/*  652 */           recordCount++;
/*      */           
/*  654 */           Vector plants = sel.getManufacturingPlants();
/*  655 */           int plantSize = 1;
/*  656 */           if (plants != null && plants.size() > 0)
/*  657 */             plantSize = plants.size(); 
/*  658 */           Plant p = null;
/*  659 */           for (int plantCount = 0; plantCount < plantSize; plantCount++) {
/*      */             
/*  661 */             if (plants != null && plants.size() > 0) {
/*  662 */               p = (Plant)plants.get(plantCount);
/*      */             }
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/*  668 */           String titleId = "";
/*  669 */           titleId = sel.getTitleID();
/*      */           
/*  671 */           String internationalDate = "";
/*      */           
/*  673 */           String filler = "";
/*      */ 
/*      */           
/*  676 */           String projectID = (sel.getProjectID() != null) ? 
/*  677 */             sel.getProjectID() : "";
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  682 */           String artist = "";
/*  683 */           artist = sel.getFlArtist().trim();
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  688 */           String comment = "";
/*  689 */           String commentStr = (sel.getSelectionComments() != null) ? 
/*  690 */             sel.getSelectionComments() : "";
/*      */ 
/*      */ 
/*      */           
/*  694 */           int subTableRows = 2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  702 */           String label = "";
/*  703 */           if (sel.getLabel() != null) {
/*  704 */             label = sel.getLabel().getName();
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*  709 */           String title = "";
/*  710 */           if (sel.getTitle() != null) {
/*  711 */             title = sel.getTitle();
/*      */           }
/*      */           
/*  714 */           String upc = "";
/*  715 */           upc = sel.getUpc();
/*  716 */           if (upc == null || upc.length() == 0)
/*  717 */             upc = ""; 
/*  718 */           upc = MilestoneHelper_2.getRMSReportFormat(upc, "UPC", sel.getIsDigital());
/*      */ 
/*      */           
/*  721 */           String selConfig = "";
/*  722 */           selConfig = (sel.getSelectionConfig() != null) ? sel.getSelectionConfig().getSelectionConfigurationAbbreviation() : "";
/*      */           
/*  724 */           String selSubConfig = "";
/*  725 */           selSubConfig = (sel.getSelectionSubConfig() != null) ? sel.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation() : "";
/*      */ 
/*      */           
/*  728 */           String poQty = (p != null && p.getOrderQty() > 0) ? String.valueOf(p.getOrderQty()) : "0";
/*  729 */           Integer integerPoQty = Integer.valueOf(poQty);
/*  730 */           if (integerPoQty == null) {
/*  731 */             integerPoQty = new Integer(0);
/*      */           }
/*      */ 
/*      */           
/*  735 */           Integer configTotal = (Integer)totals.get(selConfig);
/*  736 */           if (configTotal == null) {
/*      */             
/*  738 */             totals.put(selConfig, integerPoQty);
/*      */           } else {
/*      */             
/*  741 */             Integer newTotal = new Integer(configTotal.intValue() + integerPoQty.intValue());
/*  742 */             totals.put(selConfig, newTotal);
/*      */           } 
/*      */           
/*  745 */           String sellCode = (sel.getSellCode() != null) ? sel.getSellCode() : "";
/*  746 */           if (sellCode != null && sellCode.startsWith("-1")) {
/*  747 */             sellCode = "";
/*      */           }
/*      */ 
/*      */           
/*  751 */           String price = "";
/*  752 */           if (sel.getPriceCode() != null && 
/*  753 */             sel.getPriceCode().getTotalCost() > 0.0F) {
/*  754 */             price = "\n$" + 
/*  755 */               MilestoneHelper.formatDollarPrice(sel.getPriceCode()
/*  756 */                 .getTotalCost());
/*      */           }
/*  758 */           String selectionID = "";
/*  759 */           selectionID = sel.getSelectionNo();
/*      */           
/*  761 */           String prefix = "";
/*  762 */           prefix = SelectionManager.getLookupObjectValue(sel
/*  763 */               .getPrefixID());
/*  764 */           if (!prefix.equals("")) {
/*  765 */             prefix = String.valueOf(prefix) + "-";
/*      */           }
/*      */ 
/*      */           
/*  769 */           String MCcom = "";
/*  770 */           String TPScom = "";
/*  771 */           String FADcom = "";
/*  772 */           String STAcom = "";
/*  773 */           String LMDcom = "";
/*  774 */           String PMDcom = "";
/*      */           
/*  776 */           Schedule schedule = sel.getSchedule();
/*  777 */           Vector tasks = (schedule != null) ? schedule.getTasks() : null;
/*  778 */           ScheduledTask task = null;
/*      */           
/*  780 */           if (tasks != null)
/*      */           {
/*  782 */             for (int j = 0; j < tasks.size(); j++) {
/*      */               
/*  784 */               task = (ScheduledTask)tasks.get(j);
/*  785 */               if (task != null) {
/*      */ 
/*      */                 
/*  788 */                 String completionDate = "";
/*  789 */                 if (task.getCompletionDate() != null) {
/*  790 */                   SimpleDateFormat completionDateFormatter = new SimpleDateFormat("d-MMM");
/*  791 */                   completionDate = completionDateFormatter.format(task.getCompletionDate().getTime());
/*      */                 } 
/*      */                 
/*  794 */                 String taskAbbrev = MilestoneHelper.getTaskAbbreivationNameById(task.getTaskAbbreviationID());
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  799 */                 if (taskAbbrev.equalsIgnoreCase("M/C")) {
/*  800 */                   MCcom = completionDate;
/*      */                 }
/*  802 */                 if (taskAbbrev.equalsIgnoreCase("TPS")) {
/*  803 */                   TPScom = completionDate;
/*      */                 }
/*  805 */                 if (taskAbbrev.equalsIgnoreCase("FAD")) {
/*  806 */                   FADcom = completionDate;
/*      */                 }
/*  808 */                 if (taskAbbrev.equalsIgnoreCase("STA")) {
/*  809 */                   STAcom = completionDate;
/*      */                 }
/*  811 */                 if (taskAbbrev.equalsIgnoreCase("LMD")) {
/*  812 */                   LMDcom = completionDate;
/*      */                 }
/*  814 */                 if (taskAbbrev.equalsIgnoreCase("PMD")) {
/*  815 */                   PMDcom = completionDate;
/*      */                 }
/*      */ 
/*      */                 
/*  819 */                 task = null;
/*      */               } 
/*      */             } 
/*      */           }
/*      */           
/*  824 */           nextRow = 0;
/*  825 */           subTable = new DefaultTableLens(subTableRows, NUM_COLUMNS);
/*      */ 
/*      */           
/*  828 */           subTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
/*      */           
/*  830 */           subTable.setColWidth(0, 80);
/*  831 */           subTable.setColWidth(1, 200);
/*  832 */           subTable.setColWidth(2, 200);
/*  833 */           subTable.setColWidth(3, 80);
/*  834 */           subTable.setColWidth(4, 80);
/*  835 */           subTable.setColWidth(5, 80);
/*  836 */           subTable.setColWidth(6, 80);
/*  837 */           subTable.setColWidth(7, 80);
/*  838 */           subTable.setColWidth(8, 80);
/*  839 */           subTable.setColWidth(9, 80);
/*  840 */           subTable.setColWidth(10, 80);
/*  841 */           subTable.setColWidth(11, 80);
/*  842 */           if (NUM_COLUMNS == 14) {
/*  843 */             subTable.setColWidth(12, 150);
/*  844 */             subTable.setColWidth(13, 150);
/*      */           } else {
/*  846 */             subTable.setColWidth(12, 80);
/*  847 */             subTable.setColWidth(13, 80);
/*  848 */             subTable.setColWidth(14, 150);
/*  849 */             subTable.setColWidth(15, 150);
/*      */           } 
/*      */           
/*  852 */           subTable.setRowBorderColor(nextRow - 1, Color.lightGray);
/*      */           
/*  854 */           subTable.setBackground(nextRow, 0, Color.white);
/*  855 */           subTable.setAlignment(nextRow, 0, 10);
/*      */ 
/*      */           
/*  858 */           subTable.setSpan(nextRow, 0, new Dimension(1, 2));
/*  859 */           subTable.setSpan(nextRow, 1, new Dimension(1, 2));
/*  860 */           subTable.setSpan(nextRow, 2, new Dimension(1, 2));
/*  861 */           subTable.setSpan(nextRow, 3, new Dimension(1, 2));
/*  862 */           subTable.setSpan(nextRow, 4, new Dimension(1, 2));
/*  863 */           subTable.setSpan(nextRow, 5, new Dimension(1, 2));
/*  864 */           subTable.setSpan(nextRow, 6, new Dimension(1, 2));
/*  865 */           subTable.setSpan(nextRow, 7, new Dimension(1, 2));
/*  866 */           subTable.setSpan(nextRow, 8, new Dimension(1, 2));
/*  867 */           subTable.setSpan(nextRow, 9, new Dimension(1, 2));
/*  868 */           subTable.setSpan(nextRow, 10, new Dimension(1, 2));
/*  869 */           subTable.setSpan(nextRow, 11, new Dimension(1, 2));
/*  870 */           subTable.setSpan(nextRow, 12, new Dimension(1, 2));
/*  871 */           subTable.setSpan(nextRow, 13, new Dimension(1, 2));
/*  872 */           if (NUM_COLUMNS == 16) {
/*  873 */             subTable.setSpan(nextRow, 14, new Dimension(1, 2));
/*  874 */             subTable.setSpan(nextRow, 15, new Dimension(1, 2));
/*      */           } 
/*      */ 
/*      */           
/*  878 */           setColBorderColor(subTable, nextRow, 0, SHADED_AREA_COLOR);
/*  879 */           setColBorderColor(subTable, nextRow, 1, SHADED_AREA_COLOR);
/*  880 */           setColBorderColor(subTable, nextRow, 2, SHADED_AREA_COLOR);
/*  881 */           setColBorderColor(subTable, nextRow, 3, SHADED_AREA_COLOR);
/*  882 */           setColBorderColor(subTable, nextRow, 4, SHADED_AREA_COLOR);
/*  883 */           setColBorderColor(subTable, nextRow, 5, SHADED_AREA_COLOR);
/*  884 */           setColBorderColor(subTable, nextRow, 6, SHADED_AREA_COLOR);
/*  885 */           setColBorderColor(subTable, nextRow, 7, SHADED_AREA_COLOR);
/*  886 */           setColBorderColor(subTable, nextRow, 8, SHADED_AREA_COLOR);
/*  887 */           setColBorderColor(subTable, nextRow, 9, SHADED_AREA_COLOR);
/*  888 */           setColBorderColor(subTable, nextRow, 10, SHADED_AREA_COLOR);
/*  889 */           setColBorderColor(subTable, nextRow, 11, SHADED_AREA_COLOR);
/*  890 */           setColBorderColor(subTable, nextRow, 12, SHADED_AREA_COLOR);
/*  891 */           setColBorderColor(subTable, nextRow, 13, SHADED_AREA_COLOR);
/*  892 */           setColBorderColor(subTable, nextRow, 14, SHADED_AREA_COLOR);
/*  893 */           if (NUM_COLUMNS == 16) {
/*  894 */             setColBorderColor(subTable, nextRow, 14, SHADED_AREA_COLOR);
/*  895 */             setColBorderColor(subTable, nextRow, 15, SHADED_AREA_COLOR);
/*  896 */             setColBorderColor(subTable, nextRow, 16, SHADED_AREA_COLOR);
/*      */           } 
/*      */ 
/*      */           
/*  900 */           setColBorderColor(subTable, nextRow + 1, 0, SHADED_AREA_COLOR);
/*  901 */           setColBorderColor(subTable, nextRow + 1, 1, SHADED_AREA_COLOR);
/*  902 */           setColBorderColor(subTable, nextRow + 1, 2, SHADED_AREA_COLOR);
/*  903 */           setColBorderColor(subTable, nextRow + 1, 3, SHADED_AREA_COLOR);
/*  904 */           setColBorderColor(subTable, nextRow + 1, 4, SHADED_AREA_COLOR);
/*  905 */           setColBorderColor(subTable, nextRow + 1, 5, SHADED_AREA_COLOR);
/*  906 */           setColBorderColor(subTable, nextRow + 1, 6, SHADED_AREA_COLOR);
/*  907 */           setColBorderColor(subTable, nextRow + 1, 7, SHADED_AREA_COLOR);
/*  908 */           setColBorderColor(subTable, nextRow + 1, 8, SHADED_AREA_COLOR);
/*  909 */           setColBorderColor(subTable, nextRow + 1, 9, SHADED_AREA_COLOR);
/*  910 */           setColBorderColor(subTable, nextRow + 1, 10, SHADED_AREA_COLOR);
/*  911 */           setColBorderColor(subTable, nextRow + 1, 11, SHADED_AREA_COLOR);
/*  912 */           setColBorderColor(subTable, nextRow + 1, 12, SHADED_AREA_COLOR);
/*  913 */           setColBorderColor(subTable, nextRow + 1, 13, SHADED_AREA_COLOR);
/*  914 */           setColBorderColor(subTable, nextRow + 1, 14, SHADED_AREA_COLOR);
/*  915 */           if (NUM_COLUMNS == 16) {
/*  916 */             setColBorderColor(subTable, nextRow + 1, 14, SHADED_AREA_COLOR);
/*  917 */             setColBorderColor(subTable, nextRow + 1, 15, SHADED_AREA_COLOR);
/*  918 */             setColBorderColor(subTable, nextRow + 1, 16, SHADED_AREA_COLOR);
/*      */           } 
/*      */ 
/*      */           
/*  922 */           subTable.setObject(nextRow, 0, sellCode);
/*  923 */           subTable.setFont(nextRow, 0, new Font("Arial", 0, 7));
/*  924 */           subTable.setAlignment(nextRow, 0, 2);
/*      */ 
/*      */           
/*  927 */           String contact = "";
/*  928 */           contact = (sel.getLabelContact() != null) ? sel.getLabelContact().getName() : "";
/*      */ 
/*      */           
/*  931 */           String otherContact = "";
/*  932 */           otherContact = (sel.getOtherContact() != null) ? sel.getOtherContact() : "";
/*      */ 
/*      */           
/*  935 */           String typeString = (sel.getReleaseType() != null) ? sel.getReleaseType().getName() : "";
/*      */ 
/*      */           
/*  938 */           if ((config.equals("Special Products") && typeString.equals("Promotional")) || 
/*  939 */             displayCatalogNumberVector.contains(config)) {
/*  940 */             subTable.setObject(nextRow, 1, String.valueOf(prefix) + sel.getSelectionNo());
/*      */           } else {
/*  942 */             subTable.setObject(nextRow, 1, upc);
/*      */           } 
/*  944 */           subTable.setBackground(nextRow, 1, Color.white);
/*      */           
/*  946 */           String[] check1a = { comment };
/*  947 */           int[] check1 = { 16 };
/*  948 */           String[] check2a = { artist };
/*  949 */           int[] check2 = { 35 };
/*  950 */           String[] check3a = { title };
/*  951 */           int[] check3 = { 35 };
/*      */           
/*  953 */           int extraLines = MilestoneHelper.lineCountWCR(check1a, check1) + MilestoneHelper.lineCountWCR(check2a, check2) + MilestoneHelper.lineCountWCR(check3a, check3);
/*      */           
/*  955 */           if (extraLines == 10) {
/*  956 */             extraLines = 9;
/*      */           }
/*  958 */           else if (extraLines > 10) {
/*  959 */             extraLines = 7;
/*      */           } 
/*      */           
/*  962 */           for (int z = 0; z < extraLines; z++) {
/*  963 */             filler = String.valueOf(filler) + "\n";
/*      */           }
/*      */           
/*  966 */           subTable.setObject(nextRow, 2, String.valueOf(artist) + "\n" + title);
/*      */           
/*  968 */           subTable.setAlignment(nextRow, 2, 10);
/*      */           
/*  970 */           subTable.setObject(nextRow, 3, sel.getSelectionTerritory());
/*      */           
/*  972 */           subTable.setAlignment(nextRow, 4, 10);
/*      */           
/*  974 */           String originDate = MilestoneHelper.getCustomFormatedDate(sel.getOriginDate(), "d-MMM");
/*  975 */           subTable.setObject(nextRow, 4, originDate);
/*      */           
/*  977 */           subTable.setObject(nextRow, 5, selConfig);
/*      */           
/*  979 */           subTable.setObject(nextRow, 6, poQty);
/*      */           
/*  981 */           String vendorString = "";
/*  982 */           if (p != null && p.getPlant() != null) {
/*      */             
/*  984 */             String plantNo = p.getPlant().getName();
/*  985 */             vendorString = p.getPlant().getName();
/*      */           } 
/*  987 */           subTable.setObject(nextRow, 7, vendorString);
/*      */           
/*  989 */           subTable.setObject(nextRow, 8, MCcom);
/*      */           
/*  991 */           subTable.setObject(nextRow, 9, TPScom);
/*      */           
/*  993 */           subTable.setObject(nextRow, 10, FADcom);
/*      */           
/*  995 */           subTable.setObject(nextRow, 11, STAcom);
/*      */           
/*  997 */           if (NUM_COLUMNS == 14) {
/*  998 */             subTable.setObject(nextRow, 12, commentStr);
/*  999 */             subTable.setObject(nextRow, 13, sel.getOtherContact());
/*      */           } else {
/* 1001 */             subTable.setObject(nextRow, 12, LMDcom);
/* 1002 */             subTable.setObject(nextRow, 13, PMDcom);
/* 1003 */             subTable.setObject(nextRow, 14, commentStr);
/* 1004 */             subTable.setObject(nextRow, 15, sel.getOtherContact());
/*      */           } 
/*      */           
/* 1007 */           subTable.setFont(nextRow, 3, new Font("Arial", 1, 7));
/* 1008 */           subTable.setFont(nextRow, 4, new Font("Arial", 1, 7));
/* 1009 */           subTable.setFont(nextRow, 5, new Font("Arial", 1, 7));
/* 1010 */           subTable.setFont(nextRow, 6, new Font("Arial", 1, 7));
/* 1011 */           subTable.setFont(nextRow, 7, new Font("Arial", 1, 7));
/* 1012 */           subTable.setFont(nextRow, 8, new Font("Arial", 1, 7));
/* 1013 */           subTable.setFont(nextRow, 9, new Font("Arial", 1, 7));
/* 1014 */           subTable.setFont(nextRow, 10, new Font("Arial", 1, 7));
/* 1015 */           subTable.setFont(nextRow, 11, new Font("Arial", 1, 7));
/* 1016 */           subTable.setFont(nextRow, 12, new Font("Arial", 1, 7));
/* 1017 */           subTable.setFont(nextRow, 13, new Font("Arial", 1, 7));
/* 1018 */           if (NUM_COLUMNS == 16) {
/* 1019 */             subTable.setFont(nextRow, 14, new Font("Arial", 1, 7));
/* 1020 */             subTable.setFont(nextRow, 15, new Font("Arial", 1, 7));
/*      */           } 
/*      */ 
/*      */           
/* 1024 */           subTable.setAlignment(nextRow, 4, 2);
/* 1025 */           subTable.setAlignment(nextRow, 5, 2);
/* 1026 */           subTable.setAlignment(nextRow, 6, 2);
/* 1027 */           subTable.setAlignment(nextRow, 7, 2);
/* 1028 */           subTable.setAlignment(nextRow, 8, 2);
/* 1029 */           subTable.setAlignment(nextRow, 9, 2);
/* 1030 */           subTable.setAlignment(nextRow, 10, 2);
/* 1031 */           subTable.setAlignment(nextRow, 11, 2);
/* 1032 */           subTable.setAlignment(nextRow, 12, 2);
/* 1033 */           subTable.setAlignment(nextRow, 13, 2);
/* 1034 */           if (NUM_COLUMNS == 16) {
/* 1035 */             subTable.setAlignment(nextRow, 14, 2);
/* 1036 */             subTable.setAlignment(nextRow, 15, 2);
/*      */           } 
/*      */           
/* 1039 */           subTable.setRowBorderColor(nextRow, SHADED_AREA_COLOR);
/* 1040 */           subTable.setRowBorderColor(nextRow + 1, SHADED_AREA_COLOR);
/*      */ 
/*      */           
/* 1043 */           subTable.setRowFont(nextRow, new Font("Arial", 0, 7));
/*      */           
/* 1045 */           body = new SectionBand(report);
/*      */           
/* 1047 */           double lfLineCount = 1.5D;
/* 1048 */           body.setHeight(1.0F);
/* 1049 */           body.addTable(subTable, new Rectangle(800, 800));
/* 1050 */           body.setBottomBorder(0);
/* 1051 */           body.setTopBorder(0);
/* 1052 */           body.setShrinkToFit(true);
/* 1053 */           body.setVisible(true);
/* 1054 */           group = new DefaultSectionLens(null, group, body);
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1060 */         if (NUM_COLUMNS == 16 || configException) {
/*      */           
/* 1062 */           nextRow = 0;
/*      */           
/* 1064 */           subTable = new DefaultTableLens(1, NUM_COLUMNS);
/* 1065 */           subTable.setSpan(nextRow, 0, new Dimension(NUM_COLUMNS, 1));
/* 1066 */           subTable.setRowBorder(0);
/*      */ 
/*      */           
/* 1069 */           printTotals(totals, subTable, nextRow);
/*      */           
/* 1071 */           subTable.setRowFont(nextRow, new Font("Arial", 1, 7));
/*      */ 
/*      */           
/* 1074 */           setColBorders(nextRow, -1, NUM_COLUMNS - 1, subTable, 0);
/* 1075 */           setColBorderColors(nextRow, -1, NUM_COLUMNS - 1, subTable, Color.white);
/* 1076 */           subTable.setRowBorder(nextRow, 0);
/*      */           
/* 1078 */           body = new SectionBand(report);
/* 1079 */           body.setHeight(1.0F);
/*      */           
/* 1081 */           body.addTable(subTable, new Rectangle(800, 800));
/* 1082 */           body.setBottomBorder(0);
/* 1083 */           body.setTopBorder(0);
/* 1084 */           body.setShrinkToFit(true);
/* 1085 */           body.setVisible(true);
/* 1086 */           group = new DefaultSectionLens(null, group, body);
/* 1087 */           nextRow = 0;
/*      */         } 
/*      */ 
/*      */         
/* 1091 */         group = new DefaultSectionLens(hbandType, group, null);
/* 1092 */         report.addSection(group, rowCountTable);
/* 1093 */         report.addPageBreak();
/* 1094 */         group = null;
/*      */       
/*      */       }
/*      */ 
/*      */     
/*      */     }
/* 1100 */     catch (Exception e) {
/*      */       
/* 1102 */       System.out.println(">>>>>>>>fillMexicoScheduleForPrintSubHander(): exception: " + e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1111 */   public static String removeLF(String theString, int maxChars) { return theString.replace('\n', ' '); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void setColBorderColor(DefaultTableLens table, int row, int columns, Color color) {
/* 1120 */     for (int i = -1; i < columns; i++)
/*      */     {
/* 1122 */       table.setColBorderColor(row, i, color);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void printTotals(Hashtable unitsHashtable, DefaultTableLens subTable, int nextRow) {
/* 1134 */     Enumeration keysEnum = unitsHashtable.keys();
/* 1135 */     Vector keys = new Vector();
/* 1136 */     int columnCount = 0;
/* 1137 */     while (keysEnum.hasMoreElements()) {
/* 1138 */       keys.add(keysEnum.nextElement());
/*      */     }
/*      */     
/* 1141 */     String totalsString = "Totals:     ";
/* 1142 */     for (int i = 0; i < keys.size(); i++) {
/* 1143 */       Integer idString = (Integer)unitsHashtable.get(keys.get(i));
/* 1144 */       int total = idString.intValue();
/* 1145 */       if (total > 0) {
/* 1146 */         totalsString = String.valueOf(totalsString) + keys.get(i) + ": " + MilestoneHelper.formatQuantityWithCommas(String.valueOf(total)) + "    ";
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1151 */     if (totalsString.length() > 12) {
/* 1152 */       subTable.setObject(nextRow, 0, totalsString);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void setColBorderColors(int rowNum, int start, int end, DefaultTableLens table, Color color) {
/* 1162 */     end++;
/*      */     
/* 1164 */     for (int i = start; i < end; i++)
/*      */     {
/* 1166 */       table.setColBorderColor(rowNum, i, Color.white);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void setColBorders(int rowNum, int start, int end, DefaultTableLens table, int size) {
/* 1175 */     end++;
/*      */     
/* 1177 */     for (int i = start; i < end; i++)
/*      */     {
/* 1179 */       table.setColBorder(rowNum, i, size);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String reformatMexicoDate(String dateString) {
/* 1189 */     if (dateString.equals("TBS")) {
/* 1190 */       return dateString;
/*      */     }
/* 1192 */     Calendar dateCal = MilestoneHelper.getDate(dateString);
/* 1193 */     return MilestoneHelper.getCustomFormatedDate(dateCal, "d-MMM yy");
/*      */   }
/*      */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\MexicoScheduleForPrintSubHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */