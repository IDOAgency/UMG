/*      */ package WEB-INF.classes.com.universal.milestone;
/*      */ 
/*      */ import com.techempower.ComponentLog;
/*      */ import com.techempower.gemini.Context;
/*      */ import com.techempower.gemini.GeminiApplication;
/*      */ import com.universal.milestone.DatePeriod;
/*      */ import com.universal.milestone.DreamWorksRecordScheduleForPrintSubHandler;
/*      */ import com.universal.milestone.Form;
/*      */ import com.universal.milestone.MilestoneHelper;
/*      */ import com.universal.milestone.MilestoneHelper_2;
/*      */ import com.universal.milestone.MultOtherContact;
/*      */ import com.universal.milestone.Schedule;
/*      */ import com.universal.milestone.ScheduledTask;
/*      */ import com.universal.milestone.SecureHandler;
/*      */ import com.universal.milestone.Selection;
/*      */ import com.universal.milestone.SelectionManager;
/*      */ import com.universal.milestone.SelectionStatus;
/*      */ import com.universal.milestone.StringDateComparator;
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
/*      */ public class DreamWorksRecordScheduleForPrintSubHandler
/*      */   extends SecureHandler
/*      */ {
/*      */   public static final String COMPONENT_CODE = "hPsp";
/*      */   public GeminiApplication application;
/*      */   public ComponentLog log;
/*      */   
/*      */   public DreamWorksRecordScheduleForPrintSubHandler(GeminiApplication application) {
/*   67 */     this.application = application;
/*   68 */     this.log = application.getLog("hPsp");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   76 */   public String getDescription() { return "Sub Report"; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static void fillDreamWorksRecordScheduleForPrint(XStyleSheet report, Context context) {
/*   91 */     int COL_LINE_STYLE = 4097;
/*   92 */     int HEADER_FONT_SIZE = 12;
/*      */     
/*   94 */     double ldLineVal = 0.3D;
/*      */     
/*      */     try {
/*   97 */       HttpServletResponse sresponse = context.getResponse();
/*   98 */       context.putDelivery("status", new String("start_gathering"));
/*   99 */       context.includeJSP("status.jsp", "hiddenFrame");
/*  100 */       sresponse.setContentType("text/plain");
/*  101 */       sresponse.flushBuffer();
/*      */     }
/*  103 */     catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  108 */     Vector selections = MilestoneHelper.getSelectionsForReport(context);
/*      */     
/*      */     try {
/*  111 */       HttpServletResponse sresponse = context.getResponse();
/*  112 */       context.putDelivery("status", new String("start_report"));
/*  113 */       context.putDelivery("percent", new String("10"));
/*  114 */       context.includeJSP("status.jsp", "hiddenFrame");
/*  115 */       sresponse.setContentType("text/plain");
/*  116 */       sresponse.flushBuffer();
/*      */     }
/*  118 */     catch (Exception exception) {}
/*      */ 
/*      */     
/*  121 */     int DATA_FONT_SIZE = 7;
/*  122 */     int SMALL_HEADER_FONT_SIZE = 8;
/*      */     
/*  124 */     int NUM_COLUMNS = 16;
/*  125 */     Color SHADED_AREA_COLOR = Color.lightGray;
/*      */     
/*  127 */     SectionBand hbandType = new SectionBand(report);
/*  128 */     SectionBand hbandCategory = new SectionBand(report);
/*  129 */     SectionBand hbandHeadings = new SectionBand(report);
/*      */     
/*  131 */     SectionBand body = new SectionBand(report);
/*  132 */     SectionBand footer = new SectionBand(report);
/*  133 */     SectionBand spacer = new SectionBand(report);
/*  134 */     DefaultSectionLens group = null;
/*      */     
/*  136 */     footer.setVisible(true);
/*  137 */     footer.setHeight(0.1F);
/*  138 */     footer.setShrinkToFit(false);
/*  139 */     footer.setBottomBorder(0);
/*      */     
/*  141 */     spacer.setVisible(true);
/*  142 */     spacer.setHeight(0.05F);
/*  143 */     spacer.setShrinkToFit(false);
/*  144 */     spacer.setBottomBorder(0);
/*      */ 
/*      */     
/*  147 */     Hashtable selTable = groupSelectionsByTypeConfigAndStreetDate2(selections);
/*  148 */     Enumeration configs = selTable.keys();
/*  149 */     Vector configVector = new Vector();
/*      */     
/*  151 */     while (configs.hasMoreElements()) {
/*  152 */       configVector.addElement(configs.nextElement());
/*      */     }
/*  154 */     int numConfigs = configVector.size();
/*      */ 
/*      */     
/*      */     try {
/*  158 */       Collections.sort(configVector);
/*  159 */       Vector sortedConfigVector = MilestoneHelper.sortStrings(configVector);
/*      */ 
/*      */       
/*  162 */       DefaultTableLens table_contents = null;
/*  163 */       DefaultTableLens rowCountTable = null;
/*  164 */       DefaultTableLens columnHeaderTable = null;
/*  165 */       DefaultTableLens subTable = null;
/*  166 */       DefaultTableLens monthTableLens = null;
/*      */ 
/*      */       
/*  169 */       rowCountTable = new DefaultTableLens(2, 10000);
/*      */ 
/*      */       
/*  172 */       int totalCount = 0;
/*  173 */       int tenth = 1;
/*      */ 
/*      */       
/*  176 */       for (int n = 0; n < sortedConfigVector.size(); n++) {
/*      */         
/*  178 */         String configC = (configVector.elementAt(n) != null) ? (String)configVector.elementAt(n) : "";
/*  179 */         Hashtable monthTableC = (Hashtable)selTable.get(configC);
/*      */         
/*  181 */         totalCount++;
/*  182 */         Enumeration monthsC = monthTableC.keys();
/*      */         
/*  184 */         Vector monthVectorC = new Vector();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  192 */         while (monthsC.hasMoreElements()) {
/*  193 */           monthVectorC.add((String)monthsC.nextElement());
/*      */         }
/*  195 */         Object[] monthArrayC = null;
/*  196 */         monthArrayC = monthVectorC.toArray();
/*  197 */         totalCount += monthArrayC.length;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  203 */       tenth = (totalCount > 10) ? (totalCount / 10) : 1;
/*      */       
/*  205 */       HttpServletResponse sresponse = context.getResponse();
/*  206 */       context.putDelivery("status", new String("start_report"));
/*  207 */       context.putDelivery("percent", new String("20"));
/*  208 */       context.includeJSP("status.jsp", "hiddenFrame");
/*  209 */       sresponse.setContentType("text/plain");
/*  210 */       sresponse.flushBuffer();
/*      */       
/*  212 */       int recordCount = 0;
/*  213 */       int count = 0;
/*      */       
/*  215 */       for (int n = 0; n < sortedConfigVector.size(); n++)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  221 */         Form reportForm = (Form)context.getSessionValue("reportForm");
/*      */         
/*  223 */         Calendar beginStDate = (reportForm.getStringValue("beginDate") != null && 
/*  224 */           reportForm.getStringValue("beginDate").length() > 0) ? 
/*  225 */           MilestoneHelper.getDate(reportForm.getStringValue("beginDate")) : null;
/*      */         
/*  227 */         Calendar endStDate = (reportForm.getStringValue("endDate") != null && 
/*  228 */           reportForm.getStringValue("endDate").length() > 0) ? 
/*  229 */           MilestoneHelper.getDate(reportForm.getStringValue("endDate")) : null;
/*      */         
/*  231 */         report.setElement("crs_startdate", MilestoneHelper.getFormatedDate(beginStDate));
/*  232 */         report.setElement("crs_enddate", MilestoneHelper.getFormatedDate(endStDate));
/*      */         
/*  234 */         SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
/*  235 */         String todayLong = formatter.format(new Date());
/*  236 */         report.setElement("crs_bottomdate", todayLong);
/*      */         
/*  238 */         String config = (configVector.elementAt(n) != null) ? (String)configVector.elementAt(n) : "";
/*  239 */         Hashtable monthTable = (Hashtable)selTable.get(config);
/*      */ 
/*      */         
/*  242 */         int numMonths = 0;
/*  243 */         int numDates = 0;
/*  244 */         int numSelections = 0;
/*      */         
/*  246 */         if (monthTable != null) {
/*      */           
/*  248 */           Enumeration months = monthTable.keys();
/*  249 */           while (months.hasMoreElements()) {
/*      */             
/*  251 */             String monthName = (String)months.nextElement();
/*      */             
/*  253 */             numMonths++;
/*      */             
/*  255 */             selections = (Vector)monthTable.get(monthName);
/*  256 */             if (selections != null) {
/*  257 */               numSelections += selections.size();
/*      */             }
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  278 */         int numRows = 0;
/*      */ 
/*      */         
/*  281 */         numRows += numMonths * 3;
/*  282 */         numRows += numDates * 2;
/*  283 */         numRows += numSelections * 2;
/*      */         
/*  285 */         numRows += 5;
/*      */ 
/*      */         
/*  288 */         hbandType = new SectionBand(report);
/*  289 */         hbandType.setHeight(0.95F);
/*      */         
/*  291 */         hbandType.setShrinkToFit(true);
/*  292 */         hbandType.setVisible(true);
/*  293 */         hbandHeadings = new SectionBand(report);
/*  294 */         hbandHeadings.setHeight(1.0F);
/*  295 */         hbandHeadings.setShrinkToFit(true);
/*  296 */         hbandHeadings.setVisible(true);
/*      */         
/*  298 */         table_contents = new DefaultTableLens(1, 16);
/*      */ 
/*      */         
/*  301 */         table_contents.setHeaderRowCount(0);
/*  302 */         table_contents.setColWidth(0, 110);
/*      */         
/*  304 */         table_contents.setColWidth(1, 80);
/*  305 */         table_contents.setColWidth(2, 80);
/*  306 */         table_contents.setColWidth(3, 80);
/*      */         
/*  308 */         table_contents.setColWidth(4, 157);
/*  309 */         table_contents.setColWidth(5, 75);
/*  310 */         table_contents.setColWidth(6, 80);
/*  311 */         table_contents.setColWidth(7, 80);
/*  312 */         table_contents.setColWidth(8, 80);
/*  313 */         table_contents.setColWidth(9, 80);
/*  314 */         table_contents.setColWidth(10, 80);
/*  315 */         table_contents.setColWidth(11, 80);
/*  316 */         table_contents.setColWidth(12, 80);
/*  317 */         table_contents.setColWidth(13, 80);
/*  318 */         table_contents.setColWidth(14, 80);
/*  319 */         table_contents.setColWidth(15, 100);
/*      */ 
/*      */         
/*  322 */         table_contents.setColBorderColor(Color.black);
/*  323 */         table_contents.setRowBorderColor(Color.black);
/*  324 */         table_contents.setRowBorder(4097);
/*  325 */         table_contents.setColBorder(4097);
/*      */ 
/*      */         
/*  328 */         int nextRow = 0;
/*      */         
/*  330 */         String configHeaderText = !config.trim().equals("") ? config : "Other";
/*      */ 
/*      */         
/*  333 */         table_contents.setSpan(nextRow, 0, new Dimension(16, 1));
/*  334 */         table_contents.setAlignment(nextRow, 0, 2);
/*  335 */         table_contents.setObject(nextRow, 0, configHeaderText);
/*  336 */         table_contents.setRowHeight(nextRow, 16);
/*      */         
/*  338 */         table_contents.setRowBorderColor(nextRow, Color.black);
/*  339 */         table_contents.setRowBorder(nextRow, 0, 266240);
/*      */         
/*  341 */         table_contents.setRowFont(nextRow, new Font("Arial", 3, 12));
/*  342 */         table_contents.setRowBackground(nextRow, Color.white);
/*  343 */         table_contents.setRowForeground(nextRow, Color.black);
/*      */         
/*  345 */         table_contents.setRowBorder(nextRow - 1, 266240);
/*  346 */         table_contents.setColBorder(nextRow, -1, 266240);
/*  347 */         table_contents.setColBorder(nextRow, 0, 266240);
/*  348 */         table_contents.setColBorder(nextRow, 15, 266240);
/*  349 */         table_contents.setColBorder(nextRow, 16, 266240);
/*  350 */         table_contents.setRowBorder(nextRow, 266240);
/*      */         
/*  352 */         table_contents.setRowBorderColor(nextRow - 1, Color.black);
/*  353 */         table_contents.setColBorderColor(nextRow, -1, Color.black);
/*  354 */         table_contents.setColBorderColor(nextRow, 0, Color.black);
/*  355 */         table_contents.setColBorderColor(nextRow, 15, Color.black);
/*  356 */         table_contents.setColBorderColor(nextRow, 16, Color.black);
/*  357 */         table_contents.setRowBorderColor(nextRow, Color.black);
/*      */ 
/*      */ 
/*      */         
/*  361 */         hbandType.addTable(table_contents, new Rectangle(0, 0, 800, 25));
/*      */         
/*  363 */         nextRow = 0;
/*      */         
/*  365 */         columnHeaderTable = new DefaultTableLens(1, 16);
/*      */         
/*  367 */         columnHeaderTable.setHeaderRowCount(0);
/*  368 */         columnHeaderTable.setColWidth(0, 150);
/*      */         
/*  370 */         columnHeaderTable.setColWidth(1, 80);
/*  371 */         columnHeaderTable.setColWidth(2, 80);
/*  372 */         columnHeaderTable.setColWidth(3, 80);
/*      */         
/*  374 */         columnHeaderTable.setColWidth(4, 130);
/*  375 */         columnHeaderTable.setColWidth(5, 75);
/*  376 */         columnHeaderTable.setColWidth(6, 75);
/*  377 */         columnHeaderTable.setColWidth(7, 75);
/*  378 */         columnHeaderTable.setColWidth(8, 75);
/*  379 */         columnHeaderTable.setColWidth(9, 75);
/*  380 */         columnHeaderTable.setColWidth(10, 75);
/*  381 */         columnHeaderTable.setColWidth(11, 75);
/*  382 */         columnHeaderTable.setColWidth(12, 75);
/*  383 */         columnHeaderTable.setColWidth(13, 75);
/*  384 */         columnHeaderTable.setColWidth(14, 75);
/*  385 */         columnHeaderTable.setColWidth(15, 75);
/*      */         
/*  387 */         columnHeaderTable.setAlignment(nextRow, 0, 34);
/*  388 */         columnHeaderTable.setAlignment(nextRow, 1, 34);
/*  389 */         columnHeaderTable.setAlignment(nextRow, 2, 34);
/*  390 */         columnHeaderTable.setAlignment(nextRow, 3, 34);
/*  391 */         columnHeaderTable.setAlignment(nextRow, 4, 34);
/*  392 */         columnHeaderTable.setAlignment(nextRow, 5, 34);
/*  393 */         columnHeaderTable.setAlignment(nextRow, 6, 34);
/*  394 */         columnHeaderTable.setAlignment(nextRow, 7, 34);
/*  395 */         columnHeaderTable.setAlignment(nextRow, 8, 34);
/*  396 */         columnHeaderTable.setAlignment(nextRow, 9, 34);
/*  397 */         columnHeaderTable.setAlignment(nextRow, 10, 34);
/*  398 */         columnHeaderTable.setAlignment(nextRow, 11, 34);
/*  399 */         columnHeaderTable.setAlignment(nextRow, 12, 34);
/*  400 */         columnHeaderTable.setAlignment(nextRow, 13, 34);
/*  401 */         columnHeaderTable.setAlignment(nextRow, 14, 34);
/*  402 */         columnHeaderTable.setAlignment(nextRow, 15, 34);
/*      */         
/*  404 */         columnHeaderTable.setObject(nextRow, 0, "Selection #\nUPC Code\nDivision");
/*  405 */         columnHeaderTable.setObject(nextRow, 2, "Price\nArtist\nTitle\nTerritory");
/*  406 */         columnHeaderTable.setObject(nextRow, 4, "Producer/\nProduct Mgr/\nPublicist/A&R");
/*  407 */         columnHeaderTable.setObject(nextRow, 5, "Sol\nSheet");
/*  408 */         columnHeaderTable.setObject(nextRow, 6, "File Maint\n(PFM)");
/*  409 */         columnHeaderTable.setObject(nextRow, 7, "CD-CS\nCopy to\nEd");
/*  410 */         columnHeaderTable.setObject(nextRow, 8, "Copy to\nArt");
/*  411 */         columnHeaderTable.setObject(nextRow, 9, "Final Art\nDue");
/*  412 */         columnHeaderTable.setObject(nextRow, 10, "Ref\nApp/Enter\nRMS");
/*  413 */         columnHeaderTable.setObject(nextRow, 11, "Order\nParts");
/*  414 */         columnHeaderTable.setObject(nextRow, 12, "GAP/\nStker Copy\nDue");
/*  415 */         columnHeaderTable.setObject(nextRow, 13, "Stkr Film\nPkg Film\nParts Ship");
/*  416 */         columnHeaderTable.setObject(nextRow, 14, "BOM");
/*  417 */         columnHeaderTable.setObject(nextRow, 15, "Test\nAppvd");
/*      */ 
/*      */ 
/*      */         
/*  421 */         columnHeaderTable.setColBorderColor(nextRow - 1, SHADED_AREA_COLOR);
/*  422 */         columnHeaderTable.setColBorderColor(nextRow, 0, SHADED_AREA_COLOR);
/*  423 */         columnHeaderTable.setColBorderColor(nextRow, 1, Color.white);
/*  424 */         columnHeaderTable.setColBorderColor(nextRow, 2, Color.white);
/*  425 */         columnHeaderTable.setColBorderColor(nextRow, 3, SHADED_AREA_COLOR);
/*  426 */         columnHeaderTable.setColBorderColor(nextRow, 4, SHADED_AREA_COLOR);
/*  427 */         columnHeaderTable.setColBorderColor(nextRow, 5, SHADED_AREA_COLOR);
/*  428 */         columnHeaderTable.setColBorderColor(nextRow, 6, SHADED_AREA_COLOR);
/*  429 */         columnHeaderTable.setColBorderColor(nextRow, 7, SHADED_AREA_COLOR);
/*  430 */         columnHeaderTable.setColBorderColor(nextRow, 8, SHADED_AREA_COLOR);
/*  431 */         columnHeaderTable.setColBorderColor(nextRow, 9, SHADED_AREA_COLOR);
/*  432 */         columnHeaderTable.setColBorderColor(nextRow, 10, SHADED_AREA_COLOR);
/*  433 */         columnHeaderTable.setColBorderColor(nextRow, 11, SHADED_AREA_COLOR);
/*  434 */         columnHeaderTable.setColBorderColor(nextRow, 12, SHADED_AREA_COLOR);
/*  435 */         columnHeaderTable.setColBorderColor(nextRow, 13, SHADED_AREA_COLOR);
/*  436 */         columnHeaderTable.setColBorderColor(nextRow, 14, SHADED_AREA_COLOR);
/*  437 */         columnHeaderTable.setColBorderColor(nextRow, 15, SHADED_AREA_COLOR);
/*      */         
/*  439 */         columnHeaderTable.setRowBorderColor(nextRow - 1, SHADED_AREA_COLOR);
/*  440 */         columnHeaderTable.setRowBorderColor(nextRow, SHADED_AREA_COLOR);
/*      */         
/*  442 */         columnHeaderTable.setRowFont(nextRow, new Font("Arial", 1, 8));
/*  443 */         columnHeaderTable.setRowBackground(nextRow, Color.white);
/*  444 */         columnHeaderTable.setRowForeground(nextRow, Color.black);
/*  445 */         columnHeaderTable.setRowHeight(nextRow, 50);
/*      */         
/*  447 */         hbandType.addTable(columnHeaderTable, new Rectangle(0, 24, 800, 80));
/*  448 */         hbandType.setBottomBorder(0);
/*      */ 
/*      */         
/*  451 */         if (monthTable != null) {
/*      */           
/*  453 */           Enumeration months = monthTable.keys();
/*      */           
/*  455 */           Vector monthVector = new Vector();
/*      */           
/*  457 */           while (months.hasMoreElements()) {
/*  458 */             monthVector.add((String)months.nextElement());
/*      */           }
/*  460 */           Object[] monthArray = null;
/*  461 */           monthArray = monthVector.toArray();
/*  462 */           Arrays.sort(monthArray, new StringDateComparator());
/*      */           
/*  464 */           for (int x = 0; x < monthArray.length; x++) {
/*      */ 
/*      */             
/*  467 */             String monthName = (String)monthArray[x];
/*  468 */             String monthNameString = monthName;
/*      */             
/*  470 */             monthTableLens = new DefaultTableLens(1, 16);
/*  471 */             hbandCategory = new SectionBand(report);
/*  472 */             hbandCategory.setHeight(0.25F);
/*  473 */             hbandCategory.setShrinkToFit(true);
/*  474 */             hbandCategory.setVisible(true);
/*  475 */             hbandCategory.setBottomBorder(0);
/*  476 */             hbandCategory.setLeftBorder(0);
/*  477 */             hbandCategory.setRightBorder(0);
/*  478 */             hbandCategory.setTopBorder(0);
/*      */             
/*  480 */             nextRow = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  487 */             String cycle = "";
/*      */             
/*      */             try {
/*  490 */               Calendar calenderDate = MilestoneHelper.getDate(monthNameString);
/*  491 */               DatePeriod datePeriod = MilestoneHelper.findDatePeriod(calenderDate);
/*  492 */               cycle = " " + datePeriod.getCycle();
/*      */             }
/*  494 */             catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  503 */             Vector impactSelections = (Vector)monthTable.get(monthNameString);
/*  504 */             String selectedImpactDate = findLowestImpactDate(impactSelections);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  511 */             monthTableLens.setSpan(nextRow, 0, new Dimension(14, 1));
/*  512 */             monthTableLens.setObject(nextRow, 0, String.valueOf(monthNameString) + cycle);
/*  513 */             monthTableLens.setSpan(nextRow, 15, new Dimension(1, 1));
/*      */             
/*  515 */             monthTableLens.setObject(nextRow, 15, "Radio: " + selectedImpactDate);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  533 */             monthTableLens.setColBorderColor(nextRow, 1, SHADED_AREA_COLOR);
/*  534 */             monthTableLens.setColBorderColor(nextRow, 2, SHADED_AREA_COLOR);
/*  535 */             monthTableLens.setColBorderColor(nextRow, 3, SHADED_AREA_COLOR);
/*  536 */             monthTableLens.setColBorderColor(nextRow, 4, SHADED_AREA_COLOR);
/*  537 */             monthTableLens.setColBorderColor(nextRow, 5, SHADED_AREA_COLOR);
/*  538 */             monthTableLens.setColBorderColor(nextRow, 6, SHADED_AREA_COLOR);
/*  539 */             monthTableLens.setColBorderColor(nextRow, 7, SHADED_AREA_COLOR);
/*  540 */             monthTableLens.setColBorderColor(nextRow, 8, SHADED_AREA_COLOR);
/*  541 */             monthTableLens.setColBorderColor(nextRow, 9, SHADED_AREA_COLOR);
/*  542 */             monthTableLens.setColBorderColor(nextRow, 10, SHADED_AREA_COLOR);
/*  543 */             monthTableLens.setColBorderColor(nextRow, 11, SHADED_AREA_COLOR);
/*  544 */             monthTableLens.setColBorderColor(nextRow, 12, SHADED_AREA_COLOR);
/*  545 */             monthTableLens.setColBorderColor(nextRow, 13, SHADED_AREA_COLOR);
/*  546 */             monthTableLens.setColBorderColor(nextRow, 14, SHADED_AREA_COLOR);
/*  547 */             monthTableLens.setColBorderColor(nextRow, 15, SHADED_AREA_COLOR);
/*  548 */             monthTableLens.setAlignment(nextRow, 15, 4);
/*  549 */             monthTableLens.setRowHeight(nextRow, 14);
/*  550 */             monthTableLens.setRowFont(nextRow, new Font("Arial", 1, 12));
/*  551 */             monthTableLens.setRowBackground(nextRow, SHADED_AREA_COLOR);
/*  552 */             monthTableLens.setRowForeground(nextRow, Color.black);
/*  553 */             monthTableLens.setRowBorderColor(nextRow, Color.white);
/*  554 */             monthTableLens.setRowBorderColor(nextRow - 1, Color.white);
/*  555 */             monthTableLens.setColBorderColor(nextRow, -1, Color.white);
/*  556 */             monthTableLens.setColBorderColor(nextRow, 0, Color.white);
/*  557 */             monthTableLens.setColBorderColor(nextRow, 15, Color.white);
/*      */             
/*  559 */             hbandCategory.addTable(monthTableLens, new Rectangle(800, 800));
/*      */             
/*  561 */             footer.setVisible(true);
/*  562 */             footer.setHeight(0.1F);
/*  563 */             footer.setShrinkToFit(false);
/*  564 */             footer.setBottomBorder(0);
/*      */             
/*  566 */             group = new DefaultSectionLens(null, group, spacer);
/*  567 */             group = new DefaultSectionLens(null, group, hbandCategory);
/*  568 */             group = new DefaultSectionLens(null, group, spacer);
/*      */             
/*  570 */             nextRow = 0;
/*      */             
/*  572 */             selections = (Vector)monthTable.get(monthNameString);
/*  573 */             if (selections == null) {
/*  574 */               selections = new Vector();
/*      */             }
/*      */             
/*  577 */             MilestoneHelper.setSelectionSorting(selections, 14);
/*  578 */             Collections.sort(selections);
/*      */ 
/*      */             
/*  581 */             MilestoneHelper.setSelectionSorting(selections, 4);
/*  582 */             Collections.sort(selections);
/*      */ 
/*      */             
/*  585 */             MilestoneHelper.setSelectionSorting(selections, 22);
/*  586 */             Collections.sort(selections);
/*      */ 
/*      */             
/*  589 */             MilestoneHelper.setSelectionSorting(selections, 9);
/*  590 */             Collections.sort(selections);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  597 */             for (int i = 0; i < selections.size(); i++) {
/*      */               
/*  599 */               Selection sel = (Selection)selections.elementAt(i);
/*      */               
/*  601 */               if (count < recordCount / tenth) {
/*      */                 
/*  603 */                 count = recordCount / tenth;
/*  604 */                 sresponse = context.getResponse();
/*  605 */                 context.putDelivery("status", new String("start_report"));
/*  606 */                 int myPercent = count * 10;
/*  607 */                 if (myPercent > 90)
/*  608 */                   myPercent = 90; 
/*  609 */                 context.putDelivery("percent", new String(String.valueOf(myPercent)));
/*  610 */                 context.includeJSP("status.jsp", "hiddenFrame");
/*  611 */                 sresponse.setContentType("text/plain");
/*  612 */                 sresponse.flushBuffer();
/*      */               } 
/*  614 */               recordCount++;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  623 */               String titleId = "";
/*  624 */               titleId = sel.getTitleID();
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  629 */               String artist = "";
/*  630 */               artist = sel.getFlArtist().trim();
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  635 */               String comment = "";
/*  636 */               String commentStr = (sel.getSelectionComments() != null) ? sel.getSelectionComments() : "";
/*  637 */               String newComment = removeLF(commentStr, 800);
/*  638 */               int subTableRows = 9;
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  643 */               String label = "";
/*  644 */               if (sel.getLabel() != null) {
/*  645 */                 label = sel.getLabel().getName();
/*      */               }
/*      */ 
/*      */ 
/*      */               
/*  650 */               String pack = "";
/*  651 */               pack = sel.getSelectionPackaging();
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  656 */               String title = "";
/*  657 */               if (sel.getTitle() != null) {
/*  658 */                 title = sel.getTitle();
/*      */               }
/*      */               
/*  661 */               String upc = "";
/*  662 */               upc = sel.getUpc();
/*      */ 
/*      */               
/*  665 */               upc = MilestoneHelper_2.getRMSReportFormat(upc, "UPC", sel.getIsDigital());
/*      */               
/*  667 */               if (upc != null && upc.length() == 0) {
/*  668 */                 upc = titleId;
/*      */               }
/*      */               
/*  671 */               String selectionNo = "";
/*  672 */               selectionNo = (sel.getSelectionNo() != null) ? sel.getSelectionNo() : "";
/*      */ 
/*      */               
/*  675 */               String selDivision = (sel.getDivision() != null && sel.getDivision().getName() != null) ? 
/*  676 */                 sel.getDivision().getName() : "";
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  681 */               String selConfig = "";
/*  682 */               selConfig = (sel.getSelectionConfig() != null) ? sel.getSelectionConfig().getSelectionConfigurationAbbreviation() : "";
/*      */               
/*  684 */               String selSubConfig = "";
/*  685 */               selSubConfig = (sel.getSelectionSubConfig() != null) ? sel.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation() : "";
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  690 */               String units = "";
/*  691 */               units = (sel.getNumberOfUnits() > 0) ? String.valueOf(sel.getNumberOfUnits()) : "";
/*      */               
/*  693 */               String code = (sel.getSellCode() != null) ? sel.getSellCode() : "";
/*  694 */               if (code != null && code.startsWith("-1")) {
/*  695 */                 code = "";
/*      */               }
/*  697 */               String retail = "";
/*  698 */               if (sel.getPriceCode() != null && sel.getPriceCode().getRetailCode() != null) {
/*  699 */                 retail = sel.getPriceCode().getRetailCode();
/*      */               }
/*  701 */               String price = "";
/*  702 */               if (sel.getPriceCode() != null && sel.getPriceCode().getTotalCost() > 0.0F) {
/*  703 */                 price = "$" + MilestoneHelper.formatDollarPrice(sel.getPriceCode().getTotalCost());
/*      */               }
/*      */               
/*  706 */               Vector multOtherContacts = new Vector();
/*  707 */               String aContact = "";
/*  708 */               for (int co = 0; co < sel.getMultOtherContacts().size(); co++) {
/*  709 */                 aContact = ((MultOtherContact)sel.getMultOtherContacts().elementAt(co)).getName();
/*  710 */                 multOtherContacts.add(aContact);
/*      */               } 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  716 */               Schedule schedule = sel.getSchedule();
/*      */               
/*  718 */               Vector tasks = (schedule != null) ? schedule.getTasks() : null;
/*  719 */               ScheduledTask task = null;
/*      */               
/*  721 */               String SFD = "";
/*  722 */               String SFM = "";
/*  723 */               String PCF = "";
/*  724 */               String FPC = "";
/*  725 */               String FAD = "";
/*  726 */               String MA = "";
/*  727 */               String LC = "";
/*  728 */               String PO = "";
/*  729 */               String SEPS = "";
/*  730 */               String STD = "";
/*  731 */               String SFS = "";
/*  732 */               String PFS = "";
/*  733 */               String TPS = "";
/*  734 */               String BMS = "";
/*  735 */               String TA = "";
/*      */               
/*  737 */               String SFDcom = "";
/*  738 */               String SFMcom = "";
/*  739 */               String PCFcom = "";
/*  740 */               String FPCcom = "";
/*  741 */               String FADcom = "";
/*  742 */               String MAcom = "";
/*  743 */               String LCcom = "";
/*  744 */               String POcom = "";
/*  745 */               String SEPScom = "";
/*  746 */               String STDcom = "";
/*  747 */               String SFScom = "";
/*  748 */               String PFScom = "";
/*  749 */               String TPScom = "";
/*  750 */               String BMScom = "";
/*  751 */               String TAcom = "";
/*      */               
/*  753 */               String SFDvend = "";
/*  754 */               String SFMvend = "";
/*  755 */               String PCFvend = "";
/*  756 */               String FPCvend = "";
/*  757 */               String FADvend = "";
/*  758 */               String MAvend = "";
/*  759 */               String LCvend = "";
/*  760 */               String POvend = "";
/*  761 */               String SEPSvend = "";
/*  762 */               String STDvend = "";
/*  763 */               String SFSvend = "";
/*  764 */               String PFSvend = "";
/*  765 */               String TPSvend = "";
/*  766 */               String BMSvend = "";
/*  767 */               String TAvend = "";
/*      */               
/*  769 */               if (tasks != null)
/*      */               {
/*  771 */                 for (int j = 0; j < tasks.size(); j++) {
/*      */                   
/*  773 */                   task = (ScheduledTask)tasks.get(j);
/*      */                   
/*  775 */                   if (task != null && task.getDueDate() != null) {
/*      */                     
/*  777 */                     SimpleDateFormat dueDateFormatter = new SimpleDateFormat("M/d");
/*  778 */                     String dueDate = dueDateFormatter.format(task.getDueDate().getTime());
/*  779 */                     String completionDate = "";
/*  780 */                     if (task.getCompletionDate() == null) {
/*  781 */                       completionDate = "";
/*      */                     } else {
/*  783 */                       completionDate = dueDateFormatter.format(task.getCompletionDate().getTime());
/*      */                     } 
/*      */                     
/*  786 */                     if (task.getScheduledTaskStatus().equals("N/A"))
/*      */                     {
/*  788 */                       completionDate = task.getScheduledTaskStatus();
/*      */                     }
/*      */                     
/*  791 */                     String taskAbbrev = MilestoneHelper.getTaskAbbreivationNameById(task.getTaskAbbreviationID());
/*  792 */                     String taskComment = "";
/*  793 */                     String taskVendor = "";
/*      */                     
/*  795 */                     taskVendor = (task.getVendor() != null) ? task.getVendor() : "";
/*  796 */                     taskVendor = taskVendor.equals("\n") ? "" : taskVendor;
/*      */                     
/*  798 */                     if (taskAbbrev.equalsIgnoreCase("SFD")) {
/*      */                       
/*  800 */                       SFD = dueDate;
/*  801 */                       SFDcom = completionDate;
/*  802 */                       SFDvend = taskVendor;
/*      */                     }
/*  804 */                     else if (taskAbbrev.equalsIgnoreCase("SF/M")) {
/*      */                       
/*  806 */                       SFM = dueDate;
/*  807 */                       SFMcom = completionDate;
/*  808 */                       SFMvend = taskVendor;
/*      */                     }
/*  810 */                     else if (taskAbbrev.equalsIgnoreCase("PCE")) {
/*      */                       
/*  812 */                       PCF = dueDate;
/*  813 */                       PCFcom = completionDate;
/*  814 */                       PCFvend = taskVendor;
/*      */                     }
/*  816 */                     else if (taskAbbrev.equalsIgnoreCase("FPC")) {
/*      */                       
/*  818 */                       FPC = dueDate;
/*  819 */                       FPCcom = completionDate;
/*  820 */                       FPCvend = taskVendor;
/*      */                     }
/*  822 */                     else if (taskAbbrev.equalsIgnoreCase("FAD")) {
/*      */                       
/*  824 */                       FAD = dueDate;
/*  825 */                       FADcom = completionDate;
/*  826 */                       FADvend = taskVendor;
/*      */                     }
/*  828 */                     else if (taskAbbrev.equalsIgnoreCase("MA")) {
/*      */                       
/*  830 */                       MA = dueDate;
/*  831 */                       MAcom = completionDate;
/*  832 */                       MAvend = taskVendor;
/*      */                     }
/*  834 */                     else if (taskAbbrev.equalsIgnoreCase("LC")) {
/*      */                       
/*  836 */                       LC = dueDate;
/*  837 */                       LCcom = completionDate;
/*  838 */                       LCvend = taskVendor;
/*      */                     }
/*  840 */                     else if (taskAbbrev.equalsIgnoreCase("PO")) {
/*      */                       
/*  842 */                       PO = dueDate;
/*  843 */                       POcom = completionDate;
/*  844 */                       POvend = taskVendor;
/*      */                     }
/*  846 */                     else if (taskAbbrev.equalsIgnoreCase("SEPS")) {
/*      */                       
/*  848 */                       SEPS = dueDate;
/*  849 */                       SEPScom = completionDate;
/*  850 */                       SEPSvend = taskVendor;
/*      */                     }
/*  852 */                     else if (taskAbbrev.equalsIgnoreCase("STD")) {
/*      */                       
/*  854 */                       STD = dueDate;
/*  855 */                       STDcom = completionDate;
/*  856 */                       STDvend = taskVendor;
/*      */                     }
/*  858 */                     else if (taskAbbrev.equalsIgnoreCase("SFS")) {
/*      */                       
/*  860 */                       SFS = dueDate;
/*  861 */                       SFScom = completionDate;
/*  862 */                       SFSvend = taskVendor;
/*      */                     }
/*  864 */                     else if (taskAbbrev.equalsIgnoreCase("PFS")) {
/*      */                       
/*  866 */                       PFS = dueDate;
/*  867 */                       PFScom = completionDate;
/*  868 */                       PFSvend = taskVendor;
/*      */                     }
/*  870 */                     else if (taskAbbrev.equalsIgnoreCase("TPS")) {
/*      */                       
/*  872 */                       TPS = dueDate;
/*  873 */                       TPScom = completionDate;
/*  874 */                       TPSvend = taskVendor;
/*      */                     }
/*  876 */                     else if (taskAbbrev.equalsIgnoreCase("BMS")) {
/*      */                       
/*  878 */                       BMS = dueDate;
/*  879 */                       BMScom = completionDate;
/*  880 */                       BMSvend = taskVendor;
/*      */                     }
/*  882 */                     else if (taskAbbrev.equalsIgnoreCase("TA")) {
/*      */                       
/*  884 */                       TA = dueDate;
/*  885 */                       TAcom = completionDate;
/*  886 */                       TAvend = taskVendor;
/*      */                     } 
/*      */                     
/*  889 */                     task = null;
/*      */                   } 
/*      */                 } 
/*      */               }
/*      */ 
/*      */ 
/*      */               
/*  896 */               nextRow = 0;
/*      */ 
/*      */ 
/*      */               
/*  900 */               subTable = new DefaultTableLens(subTableRows, 16);
/*      */ 
/*      */               
/*  903 */               subTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
/*      */               
/*  905 */               setColBorderColor(subTable, nextRow, 16, SHADED_AREA_COLOR);
/*      */               
/*  907 */               subTable.setHeaderRowCount(0);
/*  908 */               subTable.setColWidth(0, 150);
/*      */               
/*  910 */               subTable.setColWidth(1, 80);
/*  911 */               subTable.setColWidth(2, 80);
/*  912 */               subTable.setColWidth(3, 80);
/*  913 */               subTable.setColWidth(4, 130);
/*  914 */               subTable.setColWidth(5, 75);
/*  915 */               subTable.setColWidth(6, 75);
/*  916 */               subTable.setColWidth(7, 75);
/*  917 */               subTable.setColWidth(8, 75);
/*  918 */               subTable.setColWidth(9, 75);
/*  919 */               subTable.setColWidth(10, 75);
/*  920 */               subTable.setColWidth(11, 75);
/*  921 */               subTable.setColWidth(12, 75);
/*  922 */               subTable.setColWidth(13, 75);
/*  923 */               subTable.setColWidth(14, 75);
/*  924 */               subTable.setColWidth(15, 75);
/*      */               
/*  926 */               subTable.setRowBorderColor(nextRow - 1, Color.lightGray);
/*      */               
/*  928 */               subTable.setRowBorderColor(nextRow, 0, Color.white);
/*  929 */               subTable.setRowBorderColor(nextRow, 1, SHADED_AREA_COLOR);
/*  930 */               subTable.setRowBorderColor(nextRow, 2, SHADED_AREA_COLOR);
/*  931 */               subTable.setRowBorderColor(nextRow, 3, SHADED_AREA_COLOR);
/*  932 */               subTable.setRowBorderColor(nextRow, 4, SHADED_AREA_COLOR);
/*  933 */               subTable.setRowBorderColor(nextRow, 5, SHADED_AREA_COLOR);
/*  934 */               subTable.setRowBorderColor(nextRow, 6, SHADED_AREA_COLOR);
/*  935 */               subTable.setRowBorderColor(nextRow, 7, SHADED_AREA_COLOR);
/*  936 */               subTable.setRowBorderColor(nextRow, 8, SHADED_AREA_COLOR);
/*  937 */               subTable.setRowBorderColor(nextRow, 9, SHADED_AREA_COLOR);
/*  938 */               subTable.setRowBorderColor(nextRow, 10, SHADED_AREA_COLOR);
/*  939 */               subTable.setRowBorderColor(nextRow, 11, SHADED_AREA_COLOR);
/*  940 */               subTable.setRowBorderColor(nextRow, 12, SHADED_AREA_COLOR);
/*  941 */               subTable.setRowBorderColor(nextRow, 13, SHADED_AREA_COLOR);
/*  942 */               subTable.setRowBorderColor(nextRow, 14, SHADED_AREA_COLOR);
/*  943 */               subTable.setRowBorderColor(nextRow, 15, SHADED_AREA_COLOR);
/*      */               
/*  945 */               subTable.setObject(nextRow, 0, selectionNo);
/*  946 */               subTable.setBackground(nextRow, 0, Color.white);
/*  947 */               subTable.setAlignment(nextRow, 0, 9);
/*  948 */               subTable.setFont(nextRow, 0, new Font("Arial", 0, 7));
/*      */               
/*  950 */               subTable.setObject(nextRow, 1, code);
/*  951 */               subTable.setSpan(nextRow, 1, new Dimension(1, 1));
/*  952 */               subTable.setBackground(nextRow, 1, SHADED_AREA_COLOR);
/*  953 */               subTable.setAlignment(nextRow, 1, 9);
/*  954 */               subTable.setFont(nextRow, 1, new Font("Arial", 1, 7));
/*      */               
/*  956 */               subTable.setObject(nextRow, 2, retail);
/*  957 */               subTable.setSpan(nextRow, 2, new Dimension(1, 1));
/*  958 */               subTable.setBackground(nextRow, 2, SHADED_AREA_COLOR);
/*  959 */               subTable.setAlignment(nextRow, 2, 10);
/*  960 */               subTable.setFont(nextRow, 2, new Font("Arial", 1, 7));
/*      */               
/*  962 */               subTable.setObject(nextRow, 3, price);
/*  963 */               subTable.setSpan(nextRow, 3, new Dimension(1, 1));
/*  964 */               subTable.setBackground(nextRow, 3, SHADED_AREA_COLOR);
/*  965 */               subTable.setAlignment(nextRow, 3, 12);
/*  966 */               subTable.setFont(nextRow, 3, new Font("Arial", 1, 7));
/*      */               
/*  968 */               subTable.setObject(nextRow, 4, "Due Dates");
/*  969 */               subTable.setSpan(nextRow, 4, new Dimension(1, 1));
/*  970 */               subTable.setAlignment(nextRow, 4, 10);
/*  971 */               subTable.setBackground(nextRow, 4, SHADED_AREA_COLOR);
/*  972 */               subTable.setFont(nextRow, 4, new Font("Arial", 1, 7));
/*      */               
/*  974 */               String[] checkStrings = { comment, artist, title, pack, label, price };
/*  975 */               int[] checkStringsLength = { 20, 30, 30, 20, 25, 15 };
/*      */               
/*  977 */               int extraLines = MilestoneHelper.lineCountWCR(checkStrings, checkStringsLength);
/*      */               
/*  979 */               String[] commentString = { comment };
/*  980 */               int[] checkCommentLength = { 30 };
/*  981 */               int commentCounter = MilestoneHelper.lineCountWCR(commentString, checkCommentLength);
/*      */ 
/*      */               
/*  984 */               if (extraLines <= 2) {
/*  985 */                 extraLines--;
/*      */               } else {
/*  987 */                 extraLines--;
/*      */               } 
/*      */               
/*  990 */               extraLines = (extraLines <= 2) ? 0 : (extraLines - 2);
/*      */               
/*  992 */               subTable.setObject(nextRow, 5, SFD);
/*  993 */               subTable.setAlignment(nextRow, 5, 10);
/*  994 */               subTable.setBackground(nextRow, 5, SHADED_AREA_COLOR);
/*      */               
/*  996 */               subTable.setObject(nextRow, 6, SFM);
/*  997 */               subTable.setAlignment(nextRow, 6, 10);
/*  998 */               subTable.setBackground(nextRow, 6, SHADED_AREA_COLOR);
/*      */               
/* 1000 */               subTable.setObject(nextRow, 7, PCF);
/* 1001 */               subTable.setAlignment(nextRow, 7, 10);
/* 1002 */               subTable.setColBorderColor(nextRow, 7, SHADED_AREA_COLOR);
/* 1003 */               subTable.setColBorder(nextRow, 7, 266240);
/* 1004 */               subTable.setFont(nextRow, 7, new Font("Arial", 1, 8));
/*      */               
/* 1006 */               subTable.setObject(nextRow, 8, FPC);
/* 1007 */               subTable.setAlignment(nextRow, 8, 10);
/* 1008 */               subTable.setBackground(nextRow, 8, SHADED_AREA_COLOR);
/* 1009 */               subTable.setColBorder(nextRow, 8, 266240);
/*      */               
/* 1011 */               subTable.setObject(nextRow, 9, FAD);
/* 1012 */               subTable.setAlignment(nextRow, 9, 10);
/* 1013 */               subTable.setBackground(nextRow, 9, SHADED_AREA_COLOR);
/* 1014 */               subTable.setColBorder(nextRow, 9, 266240);
/*      */               
/* 1016 */               subTable.setObject(nextRow, 10, MA);
/* 1017 */               subTable.setAlignment(nextRow, 10, 10);
/* 1018 */               subTable.setBackground(nextRow, 10, SHADED_AREA_COLOR);
/* 1019 */               subTable.setColBorder(nextRow, 10, 266240);
/*      */               
/* 1021 */               subTable.setObject(nextRow, 11, PO);
/* 1022 */               subTable.setAlignment(nextRow, 11, 10);
/* 1023 */               subTable.setBackground(nextRow, 11, SHADED_AREA_COLOR);
/* 1024 */               subTable.setColBorder(nextRow, 11, 266240);
/*      */               
/* 1026 */               subTable.setObject(nextRow, 12, SEPS);
/* 1027 */               subTable.setAlignment(nextRow, 12, 10);
/* 1028 */               subTable.setBackground(nextRow, 12, SHADED_AREA_COLOR);
/* 1029 */               subTable.setColBorder(nextRow, 12, 266240);
/*      */               
/* 1031 */               subTable.setObject(nextRow, 13, SFS);
/* 1032 */               subTable.setAlignment(nextRow, 13, 10);
/* 1033 */               subTable.setBackground(nextRow, 13, SHADED_AREA_COLOR);
/* 1034 */               subTable.setColBorder(nextRow, 13, 266240);
/*      */               
/* 1036 */               subTable.setObject(nextRow, 14, BMS);
/* 1037 */               subTable.setAlignment(nextRow, 14, 10);
/* 1038 */               subTable.setBackground(nextRow, 14, SHADED_AREA_COLOR);
/* 1039 */               subTable.setColBorder(nextRow, 14, 266240);
/*      */               
/* 1041 */               subTable.setObject(nextRow, 15, TA);
/* 1042 */               subTable.setAlignment(nextRow, 15, 10);
/* 1043 */               subTable.setBackground(nextRow, 15, SHADED_AREA_COLOR);
/* 1044 */               subTable.setColBorder(nextRow, 15, 266240);
/*      */               
/* 1046 */               subTable.setAlignment(nextRow + 1, 6, 10);
/* 1047 */               subTable.setAlignment(nextRow + 1, 8, 10);
/*      */               
/* 1049 */               subTable.setFont(nextRow, 1, new Font("Arial", 1, 7));
/* 1050 */               subTable.setFont(nextRow, 2, new Font("Arial", 0, 7));
/* 1051 */               subTable.setFont(nextRow, 3, new Font("Arial", 0, 7));
/* 1052 */               subTable.setFont(nextRow, 5, new Font("Arial", 1, 7));
/* 1053 */               subTable.setFont(nextRow, 6, new Font("Arial", 1, 7));
/* 1054 */               subTable.setFont(nextRow, 7, new Font("Arial", 1, 7));
/* 1055 */               subTable.setFont(nextRow, 8, new Font("Arial", 1, 7));
/* 1056 */               subTable.setFont(nextRow, 9, new Font("Arial", 1, 7));
/* 1057 */               subTable.setFont(nextRow, 10, new Font("Arial", 1, 7));
/* 1058 */               subTable.setFont(nextRow, 11, new Font("Arial", 1, 7));
/* 1059 */               subTable.setFont(nextRow, 12, new Font("Arial", 1, 7));
/* 1060 */               subTable.setFont(nextRow, 13, new Font("Arial", 1, 7));
/* 1061 */               subTable.setFont(nextRow, 14, new Font("Arial", 1, 7));
/* 1062 */               subTable.setFont(nextRow, 15, new Font("Arial", 1, 7));
/*      */               
/* 1064 */               subTable.setRowFont(nextRow + 1, new Font("Arial", 0, 7));
/* 1065 */               subTable.setRowBackground(nextRow, SHADED_AREA_COLOR);
/* 1066 */               subTable.setRowForeground(nextRow, Color.black);
/*      */ 
/*      */               
/* 1069 */               subTable.setRowBorderColor(nextRow + 1, Color.white);
/* 1070 */               subTable.setObject(nextRow + 1, 0, upc);
/*      */               
/* 1072 */               subTable.setObject(nextRow + 1, 1, artist);
/* 1073 */               subTable.setSpan(nextRow + 1, 1, new Dimension(3, 1));
/*      */               
/* 1075 */               if (multOtherContacts.size() == 0) {
/* 1076 */                 subTable.setObject(nextRow + 1, 4, "");
/*      */               } else {
/* 1078 */                 subTable.setObject(nextRow + 1, 4, multOtherContacts.elementAt(0));
/*      */               } 
/* 1080 */               subTable.setObject(nextRow + 1, 5, SFDcom);
/* 1081 */               subTable.setObject(nextRow + 1, 6, SFMcom);
/* 1082 */               subTable.setObject(nextRow + 1, 7, PCFcom);
/* 1083 */               subTable.setObject(nextRow + 1, 8, FPCcom);
/* 1084 */               subTable.setObject(nextRow + 1, 9, FADcom);
/* 1085 */               subTable.setObject(nextRow + 1, 10, MAcom);
/* 1086 */               subTable.setObject(nextRow + 1, 11, POcom);
/* 1087 */               subTable.setObject(nextRow + 1, 12, SEPScom);
/* 1088 */               subTable.setObject(nextRow + 1, 13, SFScom);
/* 1089 */               subTable.setObject(nextRow + 1, 14, BMScom);
/* 1090 */               subTable.setObject(nextRow + 1, 15, TAcom);
/*      */               
/* 1092 */               setColBorderColor(subTable, nextRow + 1, 16, SHADED_AREA_COLOR);
/*      */               
/* 1094 */               subTable.setFont(nextRow + 1, 0, new Font("Arial", 0, 7));
/* 1095 */               subTable.setFont(nextRow + 1, 1, new Font("Arial", 1, 7));
/* 1096 */               subTable.setFont(nextRow + 1, 2, new Font("Arial", 0, 7));
/* 1097 */               subTable.setFont(nextRow + 1, 3, new Font("Arial", 0, 7));
/* 1098 */               subTable.setFont(nextRow + 1, 4, new Font("Arial", 0, 7));
/* 1099 */               subTable.setFont(nextRow + 1, 5, new Font("Arial", 0, 7));
/* 1100 */               subTable.setFont(nextRow + 1, 6, new Font("Arial", 0, 7));
/* 1101 */               subTable.setFont(nextRow + 1, 7, new Font("Arial", 0, 7));
/* 1102 */               subTable.setFont(nextRow + 1, 8, new Font("Arial", 0, 7));
/* 1103 */               subTable.setFont(nextRow + 1, 9, new Font("Arial", 0, 7));
/* 1104 */               subTable.setFont(nextRow + 1, 10, new Font("Arial", 0, 7));
/* 1105 */               subTable.setFont(nextRow + 1, 11, new Font("Arial", 0, 7));
/* 1106 */               subTable.setFont(nextRow + 1, 12, new Font("Arial", 0, 7));
/* 1107 */               subTable.setFont(nextRow + 1, 13, new Font("Arial", 0, 7));
/* 1108 */               subTable.setFont(nextRow + 1, 14, new Font("Arial", 0, 7));
/* 1109 */               subTable.setFont(nextRow + 1, 15, new Font("Arial", 0, 7));
/*      */ 
/*      */               
/* 1112 */               subTable.setAlignment(nextRow + 1, 4, 9);
/* 1113 */               subTable.setAlignment(nextRow + 1, 5, 10);
/* 1114 */               subTable.setAlignment(nextRow + 1, 6, 10);
/* 1115 */               subTable.setAlignment(nextRow + 1, 7, 10);
/* 1116 */               subTable.setAlignment(nextRow + 1, 8, 10);
/* 1117 */               subTable.setAlignment(nextRow + 1, 9, 10);
/* 1118 */               subTable.setAlignment(nextRow + 1, 10, 10);
/* 1119 */               subTable.setAlignment(nextRow + 1, 11, 10);
/* 1120 */               subTable.setAlignment(nextRow + 1, 12, 10);
/* 1121 */               subTable.setAlignment(nextRow + 1, 13, 10);
/* 1122 */               subTable.setAlignment(nextRow + 1, 14, 10);
/* 1123 */               subTable.setAlignment(nextRow + 1, 15, 10);
/*      */ 
/*      */               
/* 1126 */               nextRow++;
/* 1127 */               subTable.setFont(nextRow + 1, 0, new Font("Arial", 0, 7));
/* 1128 */               subTable.setFont(nextRow + 1, 1, new Font("Arial", 1, 7));
/* 1129 */               subTable.setFont(nextRow + 1, 4, new Font("Arial", 0, 7));
/* 1130 */               subTable.setFont(nextRow + 1, 5, new Font("Arial", 0, 7));
/* 1131 */               subTable.setFont(nextRow + 1, 6, new Font("Arial", 0, 7));
/* 1132 */               subTable.setFont(nextRow + 1, 7, new Font("Arial", 0, 7));
/* 1133 */               subTable.setFont(nextRow + 1, 8, new Font("Arial", 0, 7));
/* 1134 */               subTable.setFont(nextRow + 1, 9, new Font("Arial", 0, 7));
/* 1135 */               subTable.setFont(nextRow + 1, 10, new Font("Arial", 0, 7));
/* 1136 */               subTable.setFont(nextRow + 1, 11, new Font("Arial", 0, 7));
/* 1137 */               subTable.setFont(nextRow + 1, 12, new Font("Arial", 0, 7));
/* 1138 */               subTable.setFont(nextRow + 1, 13, new Font("Arial", 0, 7));
/* 1139 */               subTable.setFont(nextRow + 1, 14, new Font("Arial", 0, 7));
/* 1140 */               subTable.setFont(nextRow + 1, 15, new Font("Arial", 0, 7));
/* 1141 */               setColBorderColor(subTable, nextRow + 1, 16, SHADED_AREA_COLOR);
/* 1142 */               subTable.setRowBorderColor(nextRow + 1, Color.white);
/* 1143 */               subTable.setRowHeight(nextRow + 1, 9);
/* 1144 */               subTable.setObject(nextRow + 1, 0, selDivision);
/*      */               
/* 1146 */               subTable.setObject(nextRow + 1, 1, title);
/* 1147 */               subTable.setSpan(nextRow + 1, 1, new Dimension(3, 1));
/*      */               
/* 1149 */               if (multOtherContacts.size() < 2) {
/* 1150 */                 subTable.setObject(nextRow + 1, 4, "");
/*      */               } else {
/* 1152 */                 subTable.setObject(nextRow + 1, 4, multOtherContacts.elementAt(1));
/*      */               } 
/* 1154 */               subTable.setObject(nextRow + 1, 5, SFDvend);
/* 1155 */               subTable.setObject(nextRow + 1, 6, SFMvend);
/* 1156 */               subTable.setObject(nextRow + 1, 7, PCFvend);
/* 1157 */               subTable.setObject(nextRow + 1, 8, FPCvend);
/* 1158 */               subTable.setObject(nextRow + 1, 9, FADvend);
/* 1159 */               subTable.setObject(nextRow + 1, 10, LCcom);
/* 1160 */               subTable.setObject(nextRow + 1, 11, POvend);
/* 1161 */               subTable.setObject(nextRow + 1, 12, STDcom);
/* 1162 */               subTable.setObject(nextRow + 1, 13, PFScom);
/* 1163 */               subTable.setObject(nextRow + 1, 14, BMSvend);
/* 1164 */               subTable.setObject(nextRow + 1, 15, TAvend);
/*      */ 
/*      */               
/* 1167 */               subTable.setAlignment(nextRow + 1, 4, 9);
/* 1168 */               subTable.setAlignment(nextRow + 1, 5, 10);
/* 1169 */               subTable.setAlignment(nextRow + 1, 6, 10);
/* 1170 */               subTable.setAlignment(nextRow + 1, 7, 10);
/* 1171 */               subTable.setAlignment(nextRow + 1, 8, 10);
/* 1172 */               subTable.setAlignment(nextRow + 1, 9, 10);
/* 1173 */               subTable.setAlignment(nextRow + 1, 10, 10);
/* 1174 */               subTable.setAlignment(nextRow + 1, 11, 10);
/* 1175 */               subTable.setAlignment(nextRow + 1, 12, 10);
/* 1176 */               subTable.setAlignment(nextRow + 1, 13, 10);
/* 1177 */               subTable.setAlignment(nextRow + 1, 14, 10);
/* 1178 */               subTable.setAlignment(nextRow + 1, 15, 10);
/*      */ 
/*      */               
/* 1181 */               nextRow++;
/* 1182 */               subTable.setFont(nextRow + 1, 0, new Font("Arial", 0, 7));
/* 1183 */               subTable.setFont(nextRow + 1, 1, new Font("Arial", 1, 7));
/* 1184 */               subTable.setFont(nextRow + 1, 2, new Font("Arial", 0, 7));
/* 1185 */               subTable.setFont(nextRow + 1, 3, new Font("Arial", 0, 7));
/* 1186 */               subTable.setFont(nextRow + 1, 4, new Font("Arial", 0, 7));
/* 1187 */               subTable.setFont(nextRow + 1, 5, new Font("Arial", 0, 7));
/* 1188 */               subTable.setFont(nextRow + 1, 6, new Font("Arial", 0, 7));
/* 1189 */               subTable.setFont(nextRow + 1, 7, new Font("Arial", 0, 7));
/* 1190 */               subTable.setFont(nextRow + 1, 8, new Font("Arial", 0, 7));
/* 1191 */               subTable.setFont(nextRow + 1, 9, new Font("Arial", 0, 7));
/* 1192 */               subTable.setFont(nextRow + 1, 10, new Font("Arial", 0, 7));
/* 1193 */               subTable.setFont(nextRow + 1, 11, new Font("Arial", 0, 7));
/* 1194 */               subTable.setFont(nextRow + 1, 12, new Font("Arial", 0, 7));
/* 1195 */               subTable.setFont(nextRow + 1, 13, new Font("Arial", 0, 7));
/* 1196 */               setColBorderColor(subTable, nextRow + 1, 16, SHADED_AREA_COLOR);
/* 1197 */               subTable.setRowBorderColor(nextRow + 1, Color.white);
/* 1198 */               subTable.setRowHeight(nextRow + 1, 9);
/* 1199 */               subTable.setObject(nextRow + 1, 10, MAvend);
/*      */               
/* 1201 */               subTable.setSpan(nextRow + 1, 1, new Dimension(3, 1));
/*      */               
/* 1203 */               if (multOtherContacts.size() < 3) {
/* 1204 */                 subTable.setObject(nextRow + 1, 4, "");
/*      */               } else {
/* 1206 */                 subTable.setObject(nextRow + 1, 4, multOtherContacts.elementAt(2));
/*      */               } 
/*      */               
/* 1209 */               subTable.setObject(nextRow + 1, 12, SEPSvend);
/* 1210 */               subTable.setObject(nextRow + 1, 13, TPScom);
/*      */               
/* 1212 */               subTable.setAlignment(nextRow + 1, 4, 9);
/* 1213 */               subTable.setAlignment(nextRow + 1, 5, 10);
/* 1214 */               subTable.setAlignment(nextRow + 1, 6, 10);
/* 1215 */               subTable.setAlignment(nextRow + 1, 7, 10);
/* 1216 */               subTable.setAlignment(nextRow + 1, 8, 10);
/* 1217 */               subTable.setAlignment(nextRow + 1, 9, 10);
/* 1218 */               subTable.setAlignment(nextRow + 1, 10, 10);
/* 1219 */               subTable.setAlignment(nextRow + 1, 11, 10);
/* 1220 */               subTable.setAlignment(nextRow + 1, 12, 10);
/* 1221 */               subTable.setAlignment(nextRow + 1, 13, 10);
/*      */ 
/*      */ 
/*      */               
/* 1225 */               nextRow++;
/* 1226 */               subTable.setFont(nextRow + 1, 0, new Font("Arial", 0, 7));
/* 1227 */               subTable.setFont(nextRow + 1, 1, new Font("Arial", 1, 7));
/* 1228 */               subTable.setFont(nextRow + 1, 2, new Font("Arial", 0, 7));
/* 1229 */               subTable.setFont(nextRow + 1, 3, new Font("Arial", 0, 7));
/* 1230 */               subTable.setFont(nextRow + 1, 4, new Font("Arial", 0, 7));
/* 1231 */               subTable.setFont(nextRow + 1, 5, new Font("Arial", 0, 7));
/* 1232 */               subTable.setFont(nextRow + 1, 6, new Font("Arial", 0, 7));
/* 1233 */               subTable.setFont(nextRow + 1, 7, new Font("Arial", 0, 7));
/* 1234 */               subTable.setFont(nextRow + 1, 8, new Font("Arial", 0, 7));
/* 1235 */               subTable.setFont(nextRow + 1, 9, new Font("Arial", 0, 7));
/* 1236 */               subTable.setFont(nextRow + 1, 10, new Font("Arial", 0, 7));
/* 1237 */               subTable.setFont(nextRow + 1, 11, new Font("Arial", 0, 7));
/* 1238 */               subTable.setFont(nextRow + 1, 12, new Font("Arial", 0, 7));
/* 1239 */               subTable.setFont(nextRow + 1, 13, new Font("Arial", 0, 7));
/* 1240 */               setColBorderColor(subTable, nextRow + 1, 16, SHADED_AREA_COLOR);
/* 1241 */               subTable.setRowBorderColor(nextRow + 1, Color.white);
/* 1242 */               subTable.setRowHeight(nextRow + 1, 9);
/* 1243 */               subTable.setObject(nextRow + 1, 10, LCvend);
/*      */               
/* 1245 */               subTable.setSpan(nextRow + 1, 1, new Dimension(3, 1));
/*      */               
/* 1247 */               if (multOtherContacts.size() < 4) {
/* 1248 */                 subTable.setObject(nextRow + 1, 4, "");
/*      */               } else {
/* 1250 */                 subTable.setObject(nextRow + 1, 4, multOtherContacts.elementAt(3));
/*      */               } 
/* 1252 */               subTable.setObject(nextRow + 1, 12, STDvend);
/* 1253 */               subTable.setObject(nextRow + 1, 13, SFSvend);
/*      */               
/* 1255 */               subTable.setAlignment(nextRow + 1, 4, 9);
/* 1256 */               subTable.setAlignment(nextRow + 1, 5, 10);
/* 1257 */               subTable.setAlignment(nextRow + 1, 6, 10);
/* 1258 */               subTable.setAlignment(nextRow + 1, 7, 10);
/* 1259 */               subTable.setAlignment(nextRow + 1, 8, 10);
/* 1260 */               subTable.setAlignment(nextRow + 1, 9, 10);
/* 1261 */               subTable.setAlignment(nextRow + 1, 10, 10);
/* 1262 */               subTable.setAlignment(nextRow + 1, 11, 10);
/* 1263 */               subTable.setAlignment(nextRow + 1, 12, 10);
/* 1264 */               subTable.setAlignment(nextRow + 1, 13, 10);
/*      */ 
/*      */               
/* 1267 */               nextRow++;
/* 1268 */               subTable.setFont(nextRow + 1, 0, new Font("Arial", 0, 7));
/* 1269 */               subTable.setFont(nextRow + 1, 1, new Font("Arial", 1, 7));
/* 1270 */               subTable.setFont(nextRow + 1, 2, new Font("Arial", 0, 7));
/* 1271 */               subTable.setFont(nextRow + 1, 3, new Font("Arial", 0, 7));
/* 1272 */               subTable.setFont(nextRow + 1, 4, new Font("Arial", 0, 7));
/* 1273 */               subTable.setFont(nextRow + 1, 5, new Font("Arial", 0, 7));
/* 1274 */               subTable.setFont(nextRow + 1, 6, new Font("Arial", 0, 7));
/* 1275 */               subTable.setFont(nextRow + 1, 7, new Font("Arial", 0, 7));
/* 1276 */               subTable.setFont(nextRow + 1, 8, new Font("Arial", 0, 7));
/* 1277 */               subTable.setFont(nextRow + 1, 9, new Font("Arial", 0, 7));
/* 1278 */               subTable.setFont(nextRow + 1, 10, new Font("Arial", 0, 7));
/* 1279 */               subTable.setFont(nextRow + 1, 11, new Font("Arial", 0, 7));
/* 1280 */               subTable.setFont(nextRow + 1, 12, new Font("Arial", 0, 7));
/* 1281 */               subTable.setFont(nextRow + 1, 13, new Font("Arial", 0, 7));
/* 1282 */               setColBorderColor(subTable, nextRow + 1, 16, SHADED_AREA_COLOR);
/* 1283 */               subTable.setRowBorderColor(nextRow + 1, Color.white);
/*      */               
/* 1285 */               subTable.setSpan(nextRow + 1, 1, new Dimension(3, 1));
/*      */               
/* 1287 */               if (multOtherContacts.size() < 5) {
/* 1288 */                 subTable.setObject(nextRow + 1, 4, "");
/*      */               } else {
/* 1290 */                 subTable.setObject(nextRow + 1, 4, multOtherContacts.elementAt(4));
/*      */               } 
/* 1292 */               subTable.setRowHeight(nextRow + 1, 9);
/* 1293 */               subTable.setObject(nextRow + 1, 13, PFSvend);
/*      */               
/* 1295 */               subTable.setAlignment(nextRow + 1, 4, 9);
/* 1296 */               subTable.setAlignment(nextRow + 1, 5, 10);
/* 1297 */               subTable.setAlignment(nextRow + 1, 6, 10);
/* 1298 */               subTable.setAlignment(nextRow + 1, 7, 10);
/* 1299 */               subTable.setAlignment(nextRow + 1, 8, 10);
/* 1300 */               subTable.setAlignment(nextRow + 1, 9, 10);
/* 1301 */               subTable.setAlignment(nextRow + 1, 10, 10);
/* 1302 */               subTable.setAlignment(nextRow + 1, 11, 10);
/* 1303 */               subTable.setAlignment(nextRow + 1, 12, 10);
/* 1304 */               subTable.setAlignment(nextRow + 1, 13, 10);
/*      */ 
/*      */ 
/*      */               
/* 1308 */               nextRow++;
/* 1309 */               subTable.setFont(nextRow + 1, 0, new Font("Arial", 0, 7));
/* 1310 */               subTable.setFont(nextRow + 1, 1, new Font("Arial", 1, 7));
/* 1311 */               subTable.setFont(nextRow + 1, 2, new Font("Arial", 0, 7));
/* 1312 */               subTable.setFont(nextRow + 1, 3, new Font("Arial", 0, 7));
/* 1313 */               subTable.setFont(nextRow + 1, 4, new Font("Arial", 0, 7));
/* 1314 */               subTable.setFont(nextRow + 1, 5, new Font("Arial", 0, 7));
/* 1315 */               subTable.setFont(nextRow + 1, 6, new Font("Arial", 0, 7));
/* 1316 */               subTable.setFont(nextRow + 1, 7, new Font("Arial", 0, 7));
/* 1317 */               subTable.setFont(nextRow + 1, 8, new Font("Arial", 0, 7));
/* 1318 */               subTable.setFont(nextRow + 1, 9, new Font("Arial", 0, 7));
/* 1319 */               subTable.setFont(nextRow + 1, 10, new Font("Arial", 0, 7));
/* 1320 */               subTable.setFont(nextRow + 1, 11, new Font("Arial", 0, 7));
/* 1321 */               subTable.setFont(nextRow + 1, 12, new Font("Arial", 0, 7));
/* 1322 */               subTable.setFont(nextRow + 1, 13, new Font("Arial", 0, 7));
/* 1323 */               setColBorderColor(subTable, nextRow + 1, 16, SHADED_AREA_COLOR);
/* 1324 */               subTable.setRowBorderColor(nextRow + 1, Color.white);
/*      */               
/* 1326 */               subTable.setSpan(nextRow + 1, 1, new Dimension(3, 1));
/*      */               
/* 1328 */               if (multOtherContacts.size() < 6) {
/* 1329 */                 subTable.setObject(nextRow + 1, 4, "");
/*      */               } else {
/* 1331 */                 subTable.setObject(nextRow + 1, 4, multOtherContacts.elementAt(5));
/*      */               } 
/* 1333 */               subTable.setRowHeight(nextRow + 1, 9);
/* 1334 */               subTable.setObject(nextRow + 1, 13, TPSvend);
/*      */               
/* 1336 */               subTable.setAlignment(nextRow + 1, 4, 9);
/* 1337 */               subTable.setAlignment(nextRow + 1, 5, 10);
/* 1338 */               subTable.setAlignment(nextRow + 1, 6, 10);
/* 1339 */               subTable.setAlignment(nextRow + 1, 7, 10);
/* 1340 */               subTable.setAlignment(nextRow + 1, 8, 10);
/* 1341 */               subTable.setAlignment(nextRow + 1, 9, 10);
/* 1342 */               subTable.setAlignment(nextRow + 1, 10, 10);
/* 1343 */               subTable.setAlignment(nextRow + 1, 11, 10);
/* 1344 */               subTable.setAlignment(nextRow + 1, 12, 10);
/* 1345 */               subTable.setAlignment(nextRow + 1, 13, 10);
/*      */ 
/*      */ 
/*      */               
/* 1349 */               nextRow++;
/* 1350 */               subTable.setRowBackground(nextRow + 1, SHADED_AREA_COLOR);
/* 1351 */               subTable.setColBorderColor(nextRow + 1, -1, SHADED_AREA_COLOR);
/* 1352 */               subTable.setColBorderColor(nextRow + 1, 0, SHADED_AREA_COLOR);
/* 1353 */               subTable.setColBorderColor(nextRow + 1, 1, SHADED_AREA_COLOR);
/* 1354 */               subTable.setColBorderColor(nextRow + 1, 2, Color.white);
/* 1355 */               subTable.setColBorderColor(nextRow + 1, 3, Color.white);
/* 1356 */               subTable.setColBorderColor(nextRow + 1, 4, Color.white);
/* 1357 */               subTable.setColBorderColor(nextRow + 1, 5, Color.white);
/* 1358 */               subTable.setColBorderColor(nextRow + 1, 6, Color.white);
/* 1359 */               subTable.setColBorderColor(nextRow + 1, 7, Color.white);
/* 1360 */               subTable.setColBorderColor(nextRow + 1, 8, Color.white);
/* 1361 */               subTable.setColBorderColor(nextRow + 1, 9, Color.white);
/* 1362 */               subTable.setColBorderColor(nextRow + 1, 10, Color.white);
/* 1363 */               subTable.setColBorderColor(nextRow + 1, 11, Color.white);
/* 1364 */               subTable.setColBorderColor(nextRow + 1, 12, Color.white);
/* 1365 */               subTable.setColBorderColor(nextRow + 1, 13, Color.white);
/* 1366 */               subTable.setColBorderColor(nextRow + 1, 14, Color.white);
/* 1367 */               subTable.setColBorderColor(nextRow + 1, 15, SHADED_AREA_COLOR);
/* 1368 */               subTable.setRowBorderColor(nextRow + 1, SHADED_AREA_COLOR);
/*      */               
/* 1370 */               subTable.setSpan(nextRow + 1, 0, new Dimension(1, 1));
/* 1371 */               subTable.setSpan(nextRow + 1, 1, new Dimension(15, 1));
/* 1372 */               subTable.setAlignment(nextRow + 1, 1, 9);
/* 1373 */               subTable.setFont(nextRow + 1, 0, new Font("Arial", 1, 7));
/* 1374 */               subTable.setFont(nextRow + 1, 1, new Font("Arial", 0, 7));
/* 1375 */               subTable.setObject(nextRow + 1, 0, "Packaging Specs:");
/* 1376 */               subTable.setObject(nextRow + 1, 1, pack);
/*      */ 
/*      */               
/* 1379 */               nextRow++;
/* 1380 */               subTable.setColBorderColor(nextRow + 1, -1, SHADED_AREA_COLOR);
/* 1381 */               subTable.setColBorderColor(nextRow + 1, 0, Color.white);
/* 1382 */               subTable.setColBorderColor(nextRow + 1, 1, Color.white);
/* 1383 */               subTable.setColBorderColor(nextRow + 1, 2, Color.white);
/* 1384 */               subTable.setColBorderColor(nextRow + 1, 3, Color.white);
/* 1385 */               subTable.setColBorderColor(nextRow + 1, 4, Color.white);
/* 1386 */               subTable.setColBorderColor(nextRow + 1, 5, Color.white);
/* 1387 */               subTable.setColBorderColor(nextRow + 1, 6, Color.white);
/* 1388 */               subTable.setColBorderColor(nextRow + 1, 7, Color.white);
/* 1389 */               subTable.setColBorderColor(nextRow + 1, 8, Color.white);
/* 1390 */               subTable.setColBorderColor(nextRow + 1, 9, Color.white);
/* 1391 */               subTable.setColBorderColor(nextRow + 1, 10, Color.white);
/* 1392 */               subTable.setColBorderColor(nextRow + 1, 11, Color.white);
/* 1393 */               subTable.setColBorderColor(nextRow + 1, 12, Color.white);
/* 1394 */               subTable.setColBorderColor(nextRow + 1, 13, Color.white);
/* 1395 */               subTable.setColBorderColor(nextRow + 1, 14, Color.white);
/* 1396 */               subTable.setColBorderColor(nextRow + 1, 15, SHADED_AREA_COLOR);
/* 1397 */               subTable.setRowBorderColor(nextRow + 1, SHADED_AREA_COLOR);
/*      */               
/* 1399 */               subTable.setSpan(nextRow + 1, 0, new Dimension(1, 1));
/* 1400 */               subTable.setSpan(nextRow + 1, 1, new Dimension(15, 1));
/* 1401 */               subTable.setFont(nextRow + 1, 0, new Font("Arial", 1, 7));
/* 1402 */               subTable.setFont(nextRow + 1, 1, new Font("Arial", 0, 7));
/* 1403 */               subTable.setObject(nextRow + 1, 0, "Comments:");
/* 1404 */               subTable.setObject(nextRow + 1, 1, newComment);
/*      */               
/* 1406 */               body = new SectionBand(report);
/*      */               
/* 1408 */               double lfLineCount = 1.5D;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1461 */               body.setHeight(1.8F);
/*      */ 
/*      */ 
/*      */               
/* 1465 */               body.addTable(subTable, new Rectangle(800, 800));
/* 1466 */               body.setBottomBorder(0);
/* 1467 */               body.setTopBorder(0);
/* 1468 */               body.setShrinkToFit(true);
/* 1469 */               body.setVisible(true);
/* 1470 */               group = new DefaultSectionLens(null, group, body);
/*      */             } 
/*      */             
/* 1473 */             group = new DefaultSectionLens(null, group, spacer);
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1485 */         group = new DefaultSectionLens(hbandType, group, null);
/* 1486 */         report.addSection(group, rowCountTable);
/* 1487 */         report.addPageBreak();
/* 1488 */         group = null;
/*      */ 
/*      */       
/*      */       }
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 1496 */     catch (Exception e) {
/*      */       
/* 1498 */       System.out.println(">>>>>>>>fillDreamWorksRecordScheduleForPrint(): exception: " + e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void setColBorderColor(DefaultTableLens table, int row, int columns, Color color) {
/* 1509 */     for (int i = -1; i < columns; i++)
/*      */     {
/* 1511 */       table.setColBorderColor(row, i, color);
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
/*      */ 
/*      */   
/*      */   public static int insertConfigHeader(DefaultTableLens table_contents, String title, int nextRow, int cols) {
/* 1525 */     int COL_LINE_STYLE = 4097;
/* 1526 */     int HEADER_FONT_SIZE = 12;
/*      */     
/* 1528 */     table_contents.setObject(nextRow, 0, "");
/* 1529 */     table_contents.setSpan(nextRow, 0, new Dimension(cols, 1));
/* 1530 */     table_contents.setRowHeight(nextRow, 1);
/* 1531 */     table_contents.setRowBackground(nextRow, Color.white);
/*      */     
/* 1533 */     table_contents.setColBorderColor(nextRow, -1, Color.black);
/* 1534 */     table_contents.setColBorder(nextRow, -1, 4097);
/* 1535 */     table_contents.setColBorderColor(nextRow, cols - 1, Color.black);
/* 1536 */     table_contents.setColBorder(nextRow, cols - 1, 4097);
/*      */     
/* 1538 */     table_contents.setRowBorderColor(nextRow, Color.white);
/* 1539 */     table_contents.setRowBorder(nextRow, 266240);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1544 */     table_contents.setColBorderColor(nextRow + 1, -1, Color.black);
/* 1545 */     table_contents.setColBorder(nextRow + 1, -1, 4097);
/* 1546 */     table_contents.setColBorderColor(nextRow + 1, cols - 1, Color.black);
/* 1547 */     table_contents.setColBorder(nextRow + 1, cols - 1, 4097);
/*      */ 
/*      */     
/* 1550 */     table_contents.setAlignment(nextRow + 1, 0, 2);
/* 1551 */     table_contents.setSpan(nextRow + 1, 0, new Dimension(cols, 1));
/* 1552 */     table_contents.setObject(nextRow + 1, 0, title);
/* 1553 */     table_contents.setRowFont(nextRow + 1, new Font("Arial", 3, 12));
/*      */     
/* 1555 */     table_contents.setColBorderColor(nextRow + 1, -1, Color.black);
/* 1556 */     table_contents.setColBorder(nextRow + 1, -1, 4097);
/*      */     
/* 1558 */     nextRow += 2;
/*      */     
/* 1560 */     table_contents.setObject(nextRow, 0, "");
/* 1561 */     table_contents.setSpan(nextRow, 0, new Dimension(cols, 1));
/* 1562 */     table_contents.setRowHeight(nextRow, 1);
/* 1563 */     table_contents.setRowBackground(nextRow, Color.white);
/*      */ 
/*      */     
/* 1566 */     table_contents.setColBorderColor(nextRow, -1, Color.black);
/* 1567 */     table_contents.setColBorder(nextRow, -1, 4097);
/* 1568 */     table_contents.setColBorderColor(nextRow, cols, Color.black);
/* 1569 */     table_contents.setColBorder(nextRow, cols, 4097);
/*      */     
/* 1571 */     table_contents.setRowBorderColor(nextRow, Color.white);
/* 1572 */     table_contents.setRowBorder(nextRow, 266240);
/*      */     
/* 1574 */     nextRow++;
/*      */ 
/*      */     
/* 1577 */     table_contents.setObject(nextRow, 0, "");
/* 1578 */     table_contents.setSpan(nextRow, 0, new Dimension(cols, 1));
/* 1579 */     table_contents.setRowHeight(nextRow, 1);
/* 1580 */     table_contents.setRowBackground(nextRow, Color.white);
/*      */ 
/*      */     
/* 1583 */     table_contents.setColBorderColor(nextRow, -1, Color.white);
/* 1584 */     table_contents.setColBorder(nextRow, -1, 4097);
/* 1585 */     table_contents.setColBorderColor(nextRow, cols - 1, Color.white);
/* 1586 */     table_contents.setColBorder(nextRow, cols, 4097);
/*      */     
/* 1588 */     table_contents.setRowBorderColor(nextRow, Color.white);
/* 1589 */     table_contents.setRowBorder(nextRow, 266240);
/*      */     
/* 1591 */     nextRow++;
/*      */     
/* 1593 */     return nextRow;
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
/* 1608 */   public static String removeLF(String theString, int maxChars) { return theString.replace('\n', ' '); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Hashtable groupSelectionsByTypeConfigAndStreetDate2(Vector selections) {
/* 1625 */     Hashtable groupSelectionsByTypeConfigAndStreetDate = new Hashtable();
/* 1626 */     if (selections == null) {
/* 1627 */       return groupSelectionsByTypeConfigAndStreetDate;
/*      */     }
/* 1629 */     for (int i = 0; i < selections.size(); i++) {
/*      */       
/* 1631 */       Selection sel = (Selection)selections.elementAt(i);
/* 1632 */       if (sel != null) {
/*      */         
/* 1634 */         sel = SelectionManager.getInstance().getSelectionAndSchedule(sel.getSelectionID());
/*      */ 
/*      */         
/* 1637 */         String typeConfigString = "";
/* 1638 */         String dayString = "";
/* 1639 */         String typeString = (sel.getReleaseType() != null) ? sel.getReleaseType().getName() : "";
/* 1640 */         String configString = (sel.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation() != null) ? sel.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation() : "";
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1645 */         if (!typeString.startsWith("Promo")) {
/*      */ 
/*      */           
/* 1648 */           if (typeString.startsWith("Commercial")) {
/* 1649 */             if (configString.equals("8TRK") || 
/* 1650 */               configString.equals("CDROM") || 
/* 1651 */               configString.equals("CDVID") || 
/* 1652 */               configString.equals("DCCASS") || 
/* 1653 */               configString.equals("CASSEP") || 
/* 1654 */               configString.equals("CDEP") || 
/* 1655 */               configString.equals("ECDEP") || 
/* 1656 */               configString.equals("ALBUM") || 
/* 1657 */               configString.equals("CASS") || 
/* 1658 */               configString.equals("CD") || 
/* 1659 */               configString.equals("ECD") || 
/* 1660 */               configString.equals("LASER") || 
/* 1661 */               configString.equals("MIXED") || 
/* 1662 */               configString.equals("DVDVID") || 
/* 1663 */               configString.equals("VIDEO") || 
/* 1664 */               configString.equals("DVDAUD") || 
/* 1665 */               configString.equalsIgnoreCase("DUALDISC")) {
/*      */ 
/*      */               
/* 1668 */               typeConfigString = "Commercial Full Length";
/*      */             }
/*      */             else {
/*      */               
/* 1672 */               typeConfigString = "Commercial Single";
/*      */             } 
/*      */           }
/*      */           
/* 1676 */           if (sel.getStreetDate() != null)
/*      */           {
/* 1678 */             dayString = MilestoneHelper.getFormatedDate(sel.getStreetDate());
/*      */           }
/*      */           
/* 1681 */           String statusString = "";
/* 1682 */           SelectionStatus status = sel.getSelectionStatus();
/*      */           
/* 1684 */           if (status != null) {
/* 1685 */             statusString = (status.getName() == null) ? "" : status.getName();
/*      */           }
/*      */           
/* 1688 */           Hashtable typeConfigSubTable = (Hashtable)groupSelectionsByTypeConfigAndStreetDate.get(typeConfigString);
/* 1689 */           if (typeConfigSubTable == null) {
/*      */ 
/*      */             
/* 1692 */             typeConfigSubTable = new Hashtable();
/* 1693 */             groupSelectionsByTypeConfigAndStreetDate.put(typeConfigString, typeConfigSubTable);
/*      */           } 
/*      */           
/* 1696 */           Vector selectionsForDates = (Vector)typeConfigSubTable.get(dayString);
/* 1697 */           if (selectionsForDates == null) {
/*      */ 
/*      */             
/* 1700 */             selectionsForDates = new Vector();
/* 1701 */             typeConfigSubTable.put(dayString, selectionsForDates);
/*      */           } 
/*      */ 
/*      */           
/* 1705 */           selectionsForDates.addElement(sel);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1709 */     return groupSelectionsByTypeConfigAndStreetDate;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String findLowestImpactDate(Vector selections) {
/* 1716 */     String impactDate = "None";
/* 1717 */     SimpleDateFormat dueDateFormatter = new SimpleDateFormat("MM/dd/yy");
/*      */ 
/*      */     
/* 1720 */     MilestoneHelper.setSelectionSorting(selections, 19);
/* 1721 */     Collections.sort(selections);
/*      */     
/* 1723 */     if (((Selection)selections.elementAt(false)).getImpactDate() == null) {
/* 1724 */       impactDate = "None";
/*      */     } else {
/* 1726 */       impactDate = dueDateFormatter.format(((Selection)selections.elementAt(0)).getImpactDate().getTime());
/*      */     } 
/*      */ 
/*      */     
/* 1730 */     return impactDate;
/*      */   }
/*      */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\DreamWorksRecordScheduleForPrintSubHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */