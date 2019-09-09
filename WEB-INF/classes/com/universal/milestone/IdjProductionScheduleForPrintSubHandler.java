/*      */ package WEB-INF.classes.com.universal.milestone;
/*      */ 
/*      */ import com.techempower.ComponentLog;
/*      */ import com.techempower.gemini.Context;
/*      */ import com.techempower.gemini.GeminiApplication;
/*      */ import com.universal.milestone.DatePeriod;
/*      */ import com.universal.milestone.Form;
/*      */ import com.universal.milestone.IdjProductionScheduleForPrintSubHandler;
/*      */ import com.universal.milestone.MilestoneHelper;
/*      */ import com.universal.milestone.MonthYearComparator;
/*      */ import com.universal.milestone.Schedule;
/*      */ import com.universal.milestone.ScheduledTask;
/*      */ import com.universal.milestone.SecureHandler;
/*      */ import com.universal.milestone.Selection;
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
/*      */ 
/*      */ 
/*      */ public class IdjProductionScheduleForPrintSubHandler
/*      */   extends SecureHandler
/*      */ {
/*      */   public static final String COMPONENT_CODE = "hPsp";
/*      */   public GeminiApplication application;
/*      */   public ComponentLog log;
/*      */   
/*      */   public IdjProductionScheduleForPrintSubHandler(GeminiApplication application) {
/*   66 */     this.application = application;
/*   67 */     this.log = application.getLog("hPsp");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   75 */   public String getDescription() { return "Sub Report"; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static void fillIdjProductionScheduleForPrint(XStyleSheet report, Context context) {
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
/*  119 */     int DATA_FONT_SIZE = 7;
/*  120 */     int SMALL_HEADER_FONT_SIZE = 8;
/*  121 */     int NUM_COLUMNS = 15;
/*  122 */     Color SHADED_AREA_COLOR = Color.lightGray;
/*      */     
/*  124 */     SectionBand hbandType = new SectionBand(report);
/*  125 */     SectionBand hbandCategory = new SectionBand(report);
/*  126 */     SectionBand hbandDate = new SectionBand(report);
/*  127 */     SectionBand body = new SectionBand(report);
/*  128 */     SectionBand footer = new SectionBand(report);
/*  129 */     SectionBand spacer = new SectionBand(report);
/*  130 */     DefaultSectionLens group = null;
/*      */     
/*  132 */     footer.setVisible(true);
/*  133 */     footer.setHeight(0.1F);
/*  134 */     footer.setShrinkToFit(false);
/*  135 */     footer.setBottomBorder(0);
/*      */     
/*  137 */     spacer.setVisible(true);
/*  138 */     spacer.setHeight(0.05F);
/*  139 */     spacer.setShrinkToFit(false);
/*  140 */     spacer.setBottomBorder(0);
/*      */ 
/*      */     
/*  143 */     Hashtable selTable = MilestoneHelper.groupSelectionsByTypeConfigAndStreetDate(selections);
/*  144 */     Enumeration configs = selTable.keys();
/*  145 */     Vector configVector = new Vector();
/*      */     
/*  147 */     while (configs.hasMoreElements()) {
/*  148 */       configVector.addElement(configs.nextElement());
/*      */     }
/*  150 */     int numConfigs = configVector.size();
/*      */ 
/*      */     
/*      */     try {
/*  154 */       Collections.sort(configVector);
/*  155 */       Vector sortedConfigVector = MilestoneHelper.sortStrings(configVector);
/*      */ 
/*      */       
/*  158 */       DefaultTableLens table_contents = null;
/*  159 */       DefaultTableLens rowCountTable = null;
/*  160 */       DefaultTableLens columnHeaderTable = null;
/*  161 */       DefaultTableLens subTable = null;
/*  162 */       DefaultTableLens monthTableLens = null;
/*  163 */       DefaultTableLens dateTableLens = null;
/*      */       
/*  165 */       rowCountTable = new DefaultTableLens(2, 10000);
/*      */ 
/*      */       
/*  168 */       int totalCount = 0;
/*  169 */       int tenth = 1;
/*      */ 
/*      */       
/*  172 */       for (int n = 0; n < sortedConfigVector.size(); n++) {
/*      */         
/*  174 */         String configC = (configVector.elementAt(n) != null) ? (String)configVector.elementAt(n) : "";
/*  175 */         Hashtable monthTableC = (Hashtable)selTable.get(configC);
/*      */         
/*  177 */         totalCount++;
/*  178 */         Enumeration monthsC = monthTableC.keys();
/*      */         
/*  180 */         Vector monthVectorC = new Vector();
/*      */         
/*  182 */         while (monthsC.hasMoreElements()) {
/*  183 */           monthVectorC.add((String)monthsC.nextElement());
/*  184 */           Object[] monthArrayC = null;
/*  185 */           monthArrayC = monthVectorC.toArray();
/*  186 */           totalCount += monthArrayC.length;
/*      */         } 
/*  188 */         System.out.println(">>>>>>>>>>>>>>>>>>>>   Total Count = " + totalCount);
/*      */       } 
/*      */       
/*  191 */       tenth = (totalCount > 10) ? (totalCount / 10) : 1;
/*      */       
/*  193 */       HttpServletResponse sresponse = context.getResponse();
/*  194 */       context.putDelivery("status", new String("start_report"));
/*  195 */       context.putDelivery("percent", new String("20"));
/*  196 */       context.includeJSP("status.jsp", "hiddenFrame");
/*  197 */       sresponse.setContentType("text/plain");
/*  198 */       sresponse.flushBuffer();
/*      */       
/*  200 */       int recordCount = 0;
/*  201 */       int count = 0;
/*      */       
/*  203 */       for (int n = 0; n < sortedConfigVector.size(); n++)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  209 */         Form reportForm = (Form)context.getSessionValue("reportForm");
/*      */         
/*  211 */         Calendar beginStDate = (reportForm.getStringValue("beginDate") != null && 
/*  212 */           reportForm.getStringValue("beginDate").length() > 0) ? 
/*  213 */           MilestoneHelper.getDate(reportForm.getStringValue("beginDate")) : null;
/*      */         
/*  215 */         Calendar endStDate = (reportForm.getStringValue("endDate") != null && 
/*  216 */           reportForm.getStringValue("endDate").length() > 0) ? 
/*  217 */           MilestoneHelper.getDate(reportForm.getStringValue("endDate")) : null;
/*      */         
/*  219 */         report.setElement("crs_startdate", MilestoneHelper.getFormatedDate(beginStDate));
/*  220 */         report.setElement("crs_enddate", MilestoneHelper.getFormatedDate(endStDate));
/*      */         
/*  222 */         SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
/*  223 */         String todayLong = formatter.format(new Date());
/*  224 */         report.setElement("crs_bottomdate", todayLong);
/*      */         
/*  226 */         String config = (configVector.elementAt(n) != null) ? (String)configVector.elementAt(n) : "";
/*      */         
/*  228 */         Hashtable monthTable = (Hashtable)selTable.get(config);
/*      */ 
/*      */         
/*  231 */         int numMonths = 0;
/*  232 */         int numDates = 0;
/*  233 */         int numSelections = 0;
/*      */         
/*  235 */         if (monthTable != null) {
/*      */           
/*  237 */           Enumeration months = monthTable.keys();
/*  238 */           while (months.hasMoreElements()) {
/*      */             
/*  240 */             String monthName = (String)months.nextElement();
/*      */             
/*  242 */             numMonths++;
/*      */             
/*  244 */             Hashtable dateTable = (Hashtable)monthTable.get(monthName);
/*  245 */             if (dateTable != null) {
/*      */               
/*  247 */               Enumeration dates = dateTable.keys();
/*  248 */               while (dates.hasMoreElements()) {
/*      */                 
/*  250 */                 String dateName = (String)dates.nextElement();
/*      */                 
/*  252 */                 numDates++;
/*      */                 
/*  254 */                 selections = (Vector)dateTable.get(dateName);
/*  255 */                 if (selections != null) {
/*  256 */                   numSelections += selections.size();
/*      */                 }
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */         
/*  263 */         int numRows = 0;
/*      */ 
/*      */         
/*  266 */         numRows += numMonths * 3;
/*  267 */         numRows += numDates * 2;
/*  268 */         numRows += numSelections * 2;
/*      */         
/*  270 */         numRows += 5;
/*      */ 
/*      */         
/*  273 */         hbandType = new SectionBand(report);
/*  274 */         hbandType.setHeight(0.95F);
/*  275 */         hbandType.setShrinkToFit(true);
/*  276 */         hbandType.setVisible(true);
/*      */         
/*  278 */         table_contents = new DefaultTableLens(1, 15);
/*      */ 
/*      */         
/*  281 */         table_contents.setHeaderRowCount(0);
/*  282 */         table_contents.setColWidth(0, 259);
/*  283 */         table_contents.setColWidth(1, 157);
/*  284 */         table_contents.setColWidth(2, 150);
/*  285 */         table_contents.setColWidth(3, 80);
/*  286 */         table_contents.setColWidth(4, 168);
/*  287 */         table_contents.setColWidth(5, 87);
/*  288 */         table_contents.setColWidth(6, 84);
/*  289 */         table_contents.setColWidth(7, 70);
/*  290 */         table_contents.setColWidth(8, 80);
/*  291 */         table_contents.setColWidth(9, 72);
/*  292 */         table_contents.setColWidth(10, 90);
/*  293 */         table_contents.setColWidth(11, 70);
/*  294 */         table_contents.setColWidth(12, 90);
/*  295 */         table_contents.setColWidth(13, 77);
/*  296 */         table_contents.setColWidth(14, 180);
/*      */ 
/*      */ 
/*      */         
/*  300 */         table_contents.setColBorderColor(Color.black);
/*  301 */         table_contents.setRowBorderColor(Color.black);
/*  302 */         table_contents.setRowBorder(4097);
/*  303 */         table_contents.setColBorder(4097);
/*      */ 
/*      */         
/*  306 */         int nextRow = 0;
/*      */ 
/*      */         
/*  309 */         String configHeaderText = !config.trim().equals("") ? config : "Other";
/*      */ 
/*      */         
/*  312 */         if (configHeaderText != null)
/*      */         {
/*  314 */           if (configHeaderText.startsWith("Commercial CD Single")) {
/*  315 */             configHeaderText = "Commercial Singles";
/*      */           }
/*  317 */           else if (configHeaderText.startsWith("Promotional CD Single")) {
/*  318 */             configHeaderText = "Promos Singles";
/*      */           } 
/*      */         }
/*      */ 
/*      */         
/*  323 */         table_contents.setSpan(nextRow, 0, new Dimension(15, 1));
/*  324 */         table_contents.setAlignment(nextRow, 0, 2);
/*  325 */         table_contents.setObject(nextRow, 0, configHeaderText);
/*  326 */         table_contents.setRowHeight(nextRow, 16);
/*      */         
/*  328 */         table_contents.setRowBorderColor(nextRow, Color.black);
/*  329 */         table_contents.setRowBorder(nextRow, 0, 266240);
/*      */         
/*  331 */         table_contents.setRowFont(nextRow, new Font("Arial", 3, 12));
/*  332 */         table_contents.setRowBackground(nextRow, Color.white);
/*  333 */         table_contents.setRowForeground(nextRow, Color.black);
/*      */         
/*  335 */         table_contents.setRowBorder(nextRow - 1, 266240);
/*  336 */         table_contents.setColBorder(nextRow, -1, 266240);
/*  337 */         table_contents.setColBorder(nextRow, 0, 266240);
/*  338 */         table_contents.setColBorder(nextRow, 14, 266240);
/*  339 */         table_contents.setColBorder(nextRow, 15, 266240);
/*  340 */         table_contents.setRowBorder(nextRow, 266240);
/*      */         
/*  342 */         table_contents.setRowBorderColor(nextRow - 1, Color.black);
/*  343 */         table_contents.setColBorderColor(nextRow, -1, Color.black);
/*  344 */         table_contents.setColBorderColor(nextRow, 0, Color.black);
/*  345 */         table_contents.setColBorderColor(nextRow, 14, Color.black);
/*  346 */         table_contents.setColBorderColor(nextRow, 15, Color.black);
/*  347 */         table_contents.setRowBorderColor(nextRow, Color.black);
/*      */ 
/*      */ 
/*      */         
/*  351 */         hbandType.addTable(table_contents, new Rectangle(0, 0, 800, 25));
/*      */         
/*  353 */         nextRow = 0;
/*      */         
/*  355 */         columnHeaderTable = new DefaultTableLens(1, 15);
/*      */         
/*  357 */         columnHeaderTable.setHeaderRowCount(0);
/*  358 */         columnHeaderTable.setColWidth(0, 259);
/*  359 */         columnHeaderTable.setColWidth(1, 157);
/*  360 */         columnHeaderTable.setColWidth(2, 150);
/*  361 */         columnHeaderTable.setColWidth(3, 80);
/*  362 */         columnHeaderTable.setColWidth(4, 168);
/*  363 */         columnHeaderTable.setColWidth(5, 87);
/*  364 */         columnHeaderTable.setColWidth(6, 84);
/*  365 */         columnHeaderTable.setColWidth(7, 70);
/*  366 */         columnHeaderTable.setColWidth(8, 80);
/*  367 */         columnHeaderTable.setColWidth(9, 72);
/*  368 */         columnHeaderTable.setColWidth(10, 90);
/*  369 */         columnHeaderTable.setColWidth(11, 70);
/*  370 */         columnHeaderTable.setColWidth(12, 90);
/*  371 */         columnHeaderTable.setColWidth(13, 77);
/*  372 */         columnHeaderTable.setColWidth(14, 180);
/*      */         
/*  374 */         columnHeaderTable.setAlignment(nextRow, 0, 33);
/*  375 */         columnHeaderTable.setAlignment(nextRow, 1, 34);
/*  376 */         columnHeaderTable.setAlignment(nextRow, 2, 34);
/*  377 */         columnHeaderTable.setAlignment(nextRow, 3, 34);
/*  378 */         columnHeaderTable.setAlignment(nextRow, 4, 34);
/*  379 */         columnHeaderTable.setAlignment(nextRow, 5, 34);
/*  380 */         columnHeaderTable.setAlignment(nextRow, 6, 34);
/*  381 */         columnHeaderTable.setAlignment(nextRow, 7, 34);
/*  382 */         columnHeaderTable.setAlignment(nextRow, 8, 34);
/*  383 */         columnHeaderTable.setAlignment(nextRow, 9, 34);
/*  384 */         columnHeaderTable.setAlignment(nextRow, 10, 34);
/*  385 */         columnHeaderTable.setAlignment(nextRow, 11, 34);
/*  386 */         columnHeaderTable.setAlignment(nextRow, 12, 34);
/*  387 */         columnHeaderTable.setAlignment(nextRow, 13, 34);
/*  388 */         columnHeaderTable.setAlignment(nextRow, 14, 34);
/*      */         
/*  390 */         columnHeaderTable.setObject(nextRow, 0, "Artist/Title");
/*  391 */         columnHeaderTable.setObject(nextRow, 1, "Packaging\nSpecs");
/*  392 */         columnHeaderTable.setObject(nextRow, 2, "Selection\nUPC\nConfig");
/*  393 */         columnHeaderTable.setObject(nextRow, 3, "Price\nCode\nUnits");
/*  394 */         columnHeaderTable.setObject(nextRow, 4, "Label & Contacts");
/*  395 */         columnHeaderTable.setObject(nextRow, 5, "Prod\nReq\nDue");
/*  396 */         columnHeaderTable.setObject(nextRow, 6, "Readers\nCirc");
/*  397 */         columnHeaderTable.setObject(nextRow, 7, "BOM");
/*  398 */         columnHeaderTable.setObject(nextRow, 8, "Film\nShips");
/*  399 */         columnHeaderTable.setObject(nextRow, 9, "Parts\nOrder");
/*  400 */         columnHeaderTable.setObject(nextRow, 10, "Masters\nShip");
/*  401 */         columnHeaderTable.setObject(nextRow, 11, "Manf\nCopy\nDue");
/*  402 */         columnHeaderTable.setObject(nextRow, 12, "Tests\nApproved");
/*  403 */         columnHeaderTable.setObject(nextRow, 13, "Finish\nGoods\nDepot");
/*  404 */         columnHeaderTable.setObject(nextRow, 14, "Comments");
/*      */ 
/*      */         
/*  407 */         setColBorderColor(columnHeaderTable, nextRow, 15, SHADED_AREA_COLOR);
/*      */         
/*  409 */         columnHeaderTable.setRowBorderColor(nextRow - 1, SHADED_AREA_COLOR);
/*  410 */         columnHeaderTable.setRowBorderColor(nextRow, SHADED_AREA_COLOR);
/*  411 */         columnHeaderTable.setRowFont(nextRow, new Font("Arial", 1, 8));
/*  412 */         columnHeaderTable.setRowBackground(nextRow, Color.white);
/*  413 */         columnHeaderTable.setRowForeground(nextRow, Color.black);
/*      */ 
/*      */         
/*  416 */         hbandType.addTable(columnHeaderTable, new Rectangle(0, 24, 800, 39));
/*  417 */         hbandType.setBottomBorder(0);
/*      */ 
/*      */         
/*  420 */         if (monthTable != null) {
/*      */           
/*  422 */           Enumeration months = monthTable.keys();
/*      */           
/*  424 */           Vector monthVector = new Vector();
/*      */           
/*  426 */           while (months.hasMoreElements()) {
/*  427 */             monthVector.add((String)months.nextElement());
/*      */           }
/*  429 */           Object[] monthArray = null;
/*  430 */           monthArray = monthVector.toArray();
/*      */           
/*  432 */           Arrays.sort(monthArray, new MonthYearComparator());
/*      */           
/*  434 */           for (int x = 0; x < monthArray.length; x++) {
/*      */ 
/*      */             
/*  437 */             String monthName = (String)monthArray[x];
/*  438 */             String monthNameString = monthName;
/*      */             
/*      */             try {
/*  441 */               monthNameString = MONTHS[Integer.parseInt(monthName.substring(0, 2)) - 1];
/*      */             }
/*  443 */             catch (Exception e) {
/*      */               
/*  445 */               if (monthName.equals("13")) {
/*  446 */                 monthNameString = "TBS";
/*  447 */               } else if (monthName.equals("26")) {
/*  448 */                 monthNameString = "ITW";
/*      */               } else {
/*  450 */                 monthNameString = "No street date";
/*      */               } 
/*      */             } 
/*  453 */             monthTableLens = new DefaultTableLens(1, 15);
/*  454 */             hbandCategory = new SectionBand(report);
/*  455 */             hbandCategory.setHeight(0.25F);
/*  456 */             hbandCategory.setShrinkToFit(true);
/*  457 */             hbandCategory.setVisible(true);
/*  458 */             hbandCategory.setBottomBorder(0);
/*  459 */             hbandCategory.setLeftBorder(0);
/*  460 */             hbandCategory.setRightBorder(0);
/*  461 */             hbandCategory.setTopBorder(0);
/*      */             
/*  463 */             nextRow = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  468 */             monthTableLens.setSpan(nextRow, 0, new Dimension(15, 1));
/*  469 */             monthTableLens.setObject(nextRow, 0, monthNameString);
/*  470 */             monthTableLens.setRowHeight(nextRow, 14);
/*  471 */             monthTableLens.setRowFont(nextRow, new Font("Arial", 1, 12));
/*  472 */             monthTableLens.setRowBackground(nextRow, SHADED_AREA_COLOR);
/*  473 */             monthTableLens.setRowForeground(nextRow, Color.black);
/*  474 */             monthTableLens.setRowBorderColor(nextRow, Color.white);
/*  475 */             monthTableLens.setRowBorderColor(nextRow - 1, Color.white);
/*  476 */             monthTableLens.setColBorderColor(nextRow, -1, Color.white);
/*  477 */             monthTableLens.setColBorderColor(nextRow, 0, Color.white);
/*  478 */             monthTableLens.setColBorderColor(nextRow, 14, Color.white);
/*      */             
/*  480 */             hbandCategory.addTable(monthTableLens, new Rectangle(800, 800));
/*      */             
/*  482 */             footer.setVisible(true);
/*  483 */             footer.setHeight(0.1F);
/*  484 */             footer.setShrinkToFit(false);
/*  485 */             footer.setBottomBorder(0);
/*      */             
/*  487 */             group = new DefaultSectionLens(null, group, spacer);
/*  488 */             group = new DefaultSectionLens(null, group, hbandCategory);
/*  489 */             group = new DefaultSectionLens(null, group, spacer);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  495 */             Hashtable dateTable = (Hashtable)monthTable.get(monthName);
/*  496 */             if (dateTable != null) {
/*      */               
/*  498 */               Enumeration dateSort = dateTable.keys();
/*      */               
/*  500 */               Vector dateVector = new Vector();
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  505 */               while (dateSort.hasMoreElements()) {
/*  506 */                 dateVector.add((String)dateSort.nextElement());
/*      */               }
/*  508 */               Object[] dateArray = null;
/*      */               
/*  510 */               dateArray = dateVector.toArray();
/*  511 */               Arrays.sort(dateArray, new StringDateComparator());
/*      */               
/*  513 */               for (int dIndex = 0; dIndex < dateArray.length; dIndex++) {
/*      */ 
/*      */                 
/*  516 */                 String dateName = (String)dateArray[dIndex];
/*  517 */                 String dateNameText = dateName;
/*      */                 
/*  519 */                 if (monthNameString.equalsIgnoreCase("TBS")) {
/*      */                   
/*  521 */                   dateNameText = "TBS " + dateName;
/*      */                 }
/*  523 */                 else if (monthNameString.equalsIgnoreCase("ITW")) {
/*      */                   
/*  525 */                   dateNameText = "ITW " + dateName;
/*      */                 } 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  531 */                 String cycle = "";
/*      */ 
/*      */                 
/*      */                 try {
/*  535 */                   Calendar calanderDate = MilestoneHelper.getDate(dateNameText);
/*  536 */                   DatePeriod datePeriod = MilestoneHelper.findDatePeriod(calanderDate);
/*  537 */                   cycle = " " + datePeriod.getCycle();
/*      */                 }
/*  539 */                 catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  544 */                 hbandDate = new SectionBand(report);
/*  545 */                 hbandDate.setHeight(0.25F);
/*  546 */                 hbandDate.setShrinkToFit(true);
/*  547 */                 hbandDate.setVisible(true);
/*  548 */                 hbandDate.setBottomBorder(0);
/*  549 */                 hbandDate.setLeftBorder(0);
/*  550 */                 hbandDate.setRightBorder(0);
/*  551 */                 hbandDate.setTopBorder(0);
/*      */                 
/*  553 */                 dateTableLens = new DefaultTableLens(1, 15);
/*      */                 
/*  555 */                 nextRow = 0;
/*      */                 
/*  557 */                 dateTableLens.setSpan(nextRow, 0, new Dimension(15, 1));
/*  558 */                 dateTableLens.setObject(nextRow, 0, String.valueOf(dateNameText) + cycle);
/*  559 */                 dateTableLens.setRowHeight(nextRow, 14);
/*  560 */                 dateTableLens.setRowFont(nextRow, new Font("Arial", 1, 12));
/*  561 */                 dateTableLens.setRowForeground(nextRow, Color.black);
/*  562 */                 dateTableLens.setRowBorderColor(nextRow, Color.white);
/*  563 */                 dateTableLens.setRowBorderColor(nextRow - 1, Color.white);
/*  564 */                 dateTableLens.setColBorderColor(nextRow, -1, Color.white);
/*  565 */                 dateTableLens.setColBorderColor(nextRow, 0, Color.white);
/*  566 */                 dateTableLens.setColBorderColor(nextRow, 14, Color.white);
/*  567 */                 dateTableLens.setRowBackground(nextRow, Color.white);
/*      */                 
/*  569 */                 hbandDate.addTable(dateTableLens, new Rectangle(800, 200));
/*  570 */                 hbandDate.setBottomBorder(0);
/*      */                 
/*  572 */                 group = new DefaultSectionLens(null, group, hbandDate);
/*      */                 
/*  574 */                 selections = (Vector)dateTable.get(dateName);
/*  575 */                 if (selections == null) {
/*  576 */                   selections = new Vector();
/*      */                 }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  592 */                 MilestoneHelper.setSelectionSorting(selections, 14);
/*  593 */                 Collections.sort(selections);
/*      */ 
/*      */                 
/*  596 */                 MilestoneHelper.setSelectionSorting(selections, 4);
/*  597 */                 Collections.sort(selections);
/*      */ 
/*      */                 
/*  600 */                 MilestoneHelper.setSelectionSorting(selections, 3);
/*  601 */                 Collections.sort(selections);
/*      */ 
/*      */                 
/*  604 */                 MilestoneHelper.setSelectionSorting(selections, 9);
/*  605 */                 Collections.sort(selections);
/*      */ 
/*      */                 
/*  608 */                 MilestoneHelper.setSelectionSorting(selections, 8);
/*  609 */                 Collections.sort(selections);
/*      */ 
/*      */                 
/*  612 */                 for (int i = 0; i < selections.size(); i++) {
/*      */                   
/*  614 */                   Selection sel = (Selection)selections.elementAt(i);
/*      */                   
/*  616 */                   if (count < recordCount / tenth) {
/*      */                     
/*  618 */                     count = recordCount / tenth;
/*  619 */                     sresponse = context.getResponse();
/*  620 */                     context.putDelivery("status", new String("start_report"));
/*  621 */                     int myPercent = count * 10;
/*  622 */                     if (myPercent > 90)
/*  623 */                       myPercent = 90; 
/*  624 */                     context.putDelivery("percent", new String(String.valueOf(myPercent)));
/*  625 */                     context.includeJSP("status.jsp", "hiddenFrame");
/*  626 */                     sresponse.setContentType("text/plain");
/*  627 */                     sresponse.flushBuffer();
/*      */                   } 
/*  629 */                   recordCount++;
/*  630 */                   System.out.println(">>>>>>>>>>>>>>>>>>>>   tenth = " + tenth);
/*  631 */                   System.out.println(">>>>>>>>>>>>>>>>>>>>   count = " + count);
/*  632 */                   System.out.println(">>>>>>>>>>>>>>>>>>>>   recordCount = " + recordCount);
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  637 */                   String titleId = "";
/*  638 */                   titleId = sel.getTitleID();
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  643 */                   String artist = "";
/*  644 */                   artist = sel.getArtist().trim();
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  649 */                   String comment = (sel.getSelectionComments() != null) ? sel.getSelectionComments() : "";
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  654 */                   String label = "";
/*  655 */                   if (sel.getLabel() != null) {
/*  656 */                     label = sel.getLabel().getName();
/*      */                   }
/*      */ 
/*      */ 
/*      */                   
/*  661 */                   String pack = "";
/*  662 */                   pack = sel.getSelectionPackaging();
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  667 */                   String title = "";
/*  668 */                   if (sel.getTitle() != null) {
/*  669 */                     title = sel.getTitle();
/*      */                   }
/*      */ 
/*      */ 
/*      */                   
/*  674 */                   String upc = "";
/*  675 */                   upc = sel.getUpc();
/*      */                   
/*  677 */                   if (upc != null && upc.length() == 0) {
/*  678 */                     upc = titleId;
/*      */                   }
/*      */ 
/*      */ 
/*      */                   
/*  683 */                   String selConfig = "";
/*  684 */                   selConfig = (sel.getSelectionConfig() != null) ? sel.getSelectionConfig().getSelectionConfigurationAbbreviation() : "";
/*      */                   
/*  686 */                   String selSubConfig = "";
/*  687 */                   selSubConfig = (sel.getSelectionSubConfig() != null) ? sel.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation() : "";
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  692 */                   String units = "";
/*  693 */                   units = (sel.getNumberOfUnits() > 0) ? String.valueOf(sel.getNumberOfUnits()) : "";
/*      */                   
/*  695 */                   String code = (sel.getSellCode() != null) ? sel.getSellCode() : "";
/*  696 */                   if (code != null && code.startsWith("-1")) {
/*  697 */                     code = "";
/*      */                   }
/*  699 */                   String retail = "";
/*  700 */                   if (sel.getPriceCode() != null && sel.getPriceCode().getRetailCode() != null) {
/*  701 */                     retail = sel.getPriceCode().getRetailCode();
/*      */                   }
/*  703 */                   if (code.length() > 0) {
/*  704 */                     retail = "/" + retail;
/*      */                   }
/*  706 */                   String price = "";
/*  707 */                   if (sel.getPriceCode() != null && sel.getPriceCode().getTotalCost() > 0.0F) {
/*  708 */                     price = "$" + MilestoneHelper.formatDollarPrice(sel.getPriceCode().getTotalCost());
/*      */                   }
/*      */ 
/*      */ 
/*      */                   
/*  713 */                   String contact = "";
/*  714 */                   contact = (sel.getLabelContact() != null) ? sel.getLabelContact().getName() : "";
/*      */ 
/*      */ 
/*      */                   
/*  718 */                   String otherContact = "";
/*  719 */                   otherContact = (sel.getOtherContact() != null) ? sel.getOtherContact() : "";
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  724 */                   Schedule schedule = sel.getSchedule();
/*      */                   
/*  726 */                   Vector tasks = (schedule != null) ? schedule.getTasks() : null;
/*  727 */                   ScheduledTask task = null;
/*      */                   
/*  729 */                   String FP = "";
/*  730 */                   String BOM = "";
/*  731 */                   String PRQ = "";
/*  732 */                   String MASTERS = "";
/*  733 */                   String FILM = "";
/*  734 */                   String DEPOT = "";
/*  735 */                   String STIC = "";
/*  736 */                   String MC = "";
/*  737 */                   String DJ = "";
/*      */                   
/*  739 */                   String FPcom = "";
/*  740 */                   String BOMcom = "";
/*  741 */                   String PRQcom = "";
/*  742 */                   String MASTERScom = "";
/*  743 */                   String FILMcom = "";
/*  744 */                   String DEPOTcom = "";
/*  745 */                   String STICcom = "";
/*  746 */                   String MCcom = "";
/*  747 */                   String DJcom = "";
/*      */                   
/*  749 */                   if (tasks != null)
/*      */                   {
/*  751 */                     for (int j = 0; j < tasks.size(); j++) {
/*      */                       
/*  753 */                       task = (ScheduledTask)tasks.get(j);
/*      */                       
/*  755 */                       if (task != null && task.getDueDate() != null) {
/*      */                         
/*  757 */                         SimpleDateFormat dueDateFormatter = new SimpleDateFormat("M/d");
/*  758 */                         String dueDate = dueDateFormatter.format(task.getDueDate().getTime());
/*  759 */                         String completionDate = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*      */                         
/*  761 */                         if (task.getScheduledTaskStatus().equals("N/A"))
/*      */                         {
/*  763 */                           completionDate = task.getScheduledTaskStatus();
/*      */                         }
/*      */                         
/*  766 */                         completionDate = String.valueOf(completionDate) + "\n";
/*      */                         
/*  768 */                         String taskAbbrev = MilestoneHelper.getTaskAbbreivationNameById(task.getTaskAbbreviationID());
/*  769 */                         String taskComment = "";
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/*  774 */                         if (taskAbbrev.equalsIgnoreCase("PRQD")) {
/*      */                           
/*  776 */                           PRQ = dueDate;
/*  777 */                           PRQcom = String.valueOf(completionDate) + taskComment;
/*      */                         
/*      */                         }
/*  780 */                         else if (taskAbbrev.equalsIgnoreCase("READ")) {
/*      */                           
/*  782 */                           STIC = dueDate;
/*  783 */                           STICcom = String.valueOf(completionDate) + taskComment;
/*      */                         }
/*  785 */                         else if (taskAbbrev.equalsIgnoreCase("BMS")) {
/*      */                           
/*  787 */                           BOM = dueDate;
/*  788 */                           BOMcom = String.valueOf(completionDate) + taskComment;
/*      */                         
/*      */                         }
/*  791 */                         else if (taskAbbrev.equalsIgnoreCase("PFS")) {
/*      */                           
/*  793 */                           FP = dueDate;
/*  794 */                           FPcom = String.valueOf(completionDate) + taskComment;
/*      */                         
/*      */                         }
/*  797 */                         else if (taskAbbrev.equalsIgnoreCase("PO")) {
/*      */                           
/*  799 */                           MC = dueDate;
/*  800 */                           MCcom = String.valueOf(completionDate) + taskComment;
/*      */                         }
/*  802 */                         else if (taskAbbrev.equalsIgnoreCase("TPS")) {
/*      */                           
/*  804 */                           MASTERS = dueDate;
/*  805 */                           MASTERScom = String.valueOf(completionDate) + taskComment;
/*      */                         
/*      */                         }
/*  808 */                         else if (taskAbbrev.equalsIgnoreCase("TA")) {
/*      */                           
/*  810 */                           FILM = dueDate;
/*  811 */                           FILMcom = String.valueOf(completionDate) + taskComment;
/*      */                         
/*      */                         }
/*  814 */                         else if (taskAbbrev.equalsIgnoreCase("PSD")) {
/*      */                           
/*  816 */                           DEPOT = dueDate;
/*  817 */                           DEPOTcom = String.valueOf(completionDate) + taskComment;
/*      */                         
/*      */                         }
/*  820 */                         else if (taskAbbrev.equalsIgnoreCase("MCS")) {
/*      */                           
/*  822 */                           DJ = dueDate;
/*  823 */                           DJcom = String.valueOf(completionDate) + taskComment;
/*      */                         } 
/*      */                         
/*  826 */                         task = null;
/*      */                       } 
/*      */                     } 
/*      */                   }
/*      */ 
/*      */ 
/*      */                   
/*  833 */                   nextRow = 0;
/*  834 */                   subTable = new DefaultTableLens(2, 15);
/*      */ 
/*      */                   
/*  837 */                   subTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
/*      */                   
/*  839 */                   setColBorderColor(subTable, nextRow, 15, SHADED_AREA_COLOR);
/*      */                   
/*  841 */                   subTable.setHeaderRowCount(0);
/*  842 */                   subTable.setColWidth(0, 259);
/*  843 */                   subTable.setColWidth(1, 157);
/*  844 */                   subTable.setColWidth(2, 150);
/*  845 */                   subTable.setColWidth(3, 80);
/*  846 */                   subTable.setColWidth(4, 168);
/*  847 */                   subTable.setColWidth(5, 87);
/*  848 */                   subTable.setColWidth(6, 84);
/*  849 */                   subTable.setColWidth(7, 70);
/*  850 */                   subTable.setColWidth(8, 80);
/*  851 */                   subTable.setColWidth(9, 72);
/*  852 */                   subTable.setColWidth(10, 90);
/*  853 */                   subTable.setColWidth(11, 70);
/*  854 */                   subTable.setColWidth(12, 77);
/*  855 */                   subTable.setColWidth(13, 77);
/*  856 */                   subTable.setColWidth(14, 180);
/*  857 */                   subTable.setRowBorderColor(nextRow - 1, Color.lightGray);
/*      */                   
/*  859 */                   subTable.setObject(nextRow, 0, String.valueOf(artist) + "\n" + title);
/*  860 */                   subTable.setBackground(nextRow, 0, Color.white);
/*  861 */                   subTable.setSpan(nextRow, 0, new Dimension(1, 2));
/*  862 */                   subTable.setRowAutoSize(true);
/*  863 */                   subTable.setAlignment(nextRow, 0, 9);
/*  864 */                   subTable.setFont(nextRow, 0, new Font("Arial", 1, 7));
/*      */ 
/*      */ 
/*      */                   
/*  868 */                   subTable.setSpan(nextRow, 1, new Dimension(1, 2));
/*  869 */                   subTable.setObject(nextRow, 1, pack);
/*  870 */                   subTable.setAlignment(nextRow, 1, 10);
/*  871 */                   subTable.setBackground(nextRow, 1, Color.white);
/*      */ 
/*      */                   
/*  874 */                   String[] checkStrings = { comment, artist, title, pack, (new String[7][4] = label).valueOf(contact) + "/n" + otherContact, price };
/*  875 */                   int[] checkStringsLength = { 20, 30, 30, 20, 25, 25, 15 };
/*      */                   
/*  877 */                   int extraLines = MilestoneHelper.lineCountWCR(checkStrings, checkStringsLength);
/*      */                   
/*  879 */                   String[] commentString = { comment };
/*  880 */                   int[] checkCommentLength = { 30 };
/*  881 */                   int commentCounter = MilestoneHelper.lineCountWCR(commentString, checkCommentLength);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  891 */                   boolean otherContactExists = false;
/*  892 */                   System.out.println("***commentCounter:[" + commentCounter + "]");
/*  893 */                   if (!otherContact.equals("") || commentCounter > 1) {
/*  894 */                     otherContactExists = true;
/*      */                   }
/*      */ 
/*      */                   
/*  898 */                   extraLines = (extraLines <= 2) ? 0 : (extraLines - 2);
/*  899 */                   for (int z = 0; z < extraLines; z++)
/*      */                   {
/*  901 */                     otherContact = String.valueOf(otherContact) + "\n";
/*      */                   }
/*      */                   
/*  904 */                   subTable.setObject(nextRow, 2, String.valueOf(upc) + "\n" + selSubConfig);
/*  905 */                   subTable.setAlignment(nextRow, 2, 10);
/*  906 */                   subTable.setBackground(nextRow, 2, Color.white);
/*  907 */                   subTable.setSpan(nextRow, 2, new Dimension(1, 2));
/*      */                   
/*  909 */                   subTable.setObject(nextRow, 3, String.valueOf(price) + "\n" + code + retail + "\n" + units);
/*  910 */                   subTable.setAlignment(nextRow, 3, 10);
/*  911 */                   subTable.setBackground(nextRow, 3, Color.white);
/*  912 */                   subTable.setSpan(nextRow, 3, new Dimension(1, 2));
/*  913 */                   subTable.setAlignment(nextRow + 1, 3, 10);
/*      */                   
/*  915 */                   subTable.setObject(nextRow, 4, "Due Dates");
/*  916 */                   subTable.setColBorderColor(nextRow, 4, SHADED_AREA_COLOR);
/*  917 */                   subTable.setColBorder(nextRow, 4, 266240);
/*  918 */                   subTable.setFont(nextRow, 4, new Font("Arial", 1, 8));
/*  919 */                   subTable.setRowBorderColor(nextRow, SHADED_AREA_COLOR);
/*      */                   
/*  921 */                   subTable.setAlignment(nextRow, 4, 10);
/*  922 */                   subTable.setAlignment(nextRow + 1, 4, 10);
/*      */ 
/*      */                   
/*  925 */                   if (otherContactExists) {
/*  926 */                     subTable.setObject(nextRow + 1, 4, String.valueOf(label) + "\n" + contact + "\n" + otherContact);
/*      */                   } else {
/*  928 */                     subTable.setObject(nextRow + 1, 4, String.valueOf(label) + "\n" + contact);
/*      */                   } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  935 */                   subTable.setRowHeight(nextRow, 9);
/*      */                   
/*  937 */                   subTable.setRowBorderColor(nextRow + 1, SHADED_AREA_COLOR);
/*      */                   
/*  939 */                   subTable.setObject(nextRow, 5, PRQ);
/*  940 */                   subTable.setColBorderColor(nextRow, 5, SHADED_AREA_COLOR);
/*  941 */                   subTable.setColBorder(nextRow, 5, 266240);
/*      */                   
/*  943 */                   subTable.setObject(nextRow, 6, STIC);
/*  944 */                   subTable.setColBorderColor(nextRow, 6, SHADED_AREA_COLOR);
/*  945 */                   subTable.setColBorder(nextRow, 6, 266240);
/*      */                   
/*  947 */                   subTable.setObject(nextRow, 7, BOM);
/*  948 */                   subTable.setColBorderColor(nextRow, 7, SHADED_AREA_COLOR);
/*  949 */                   subTable.setColBorder(nextRow, 7, 266240);
/*      */                   
/*  951 */                   subTable.setObject(nextRow, 8, FP);
/*  952 */                   subTable.setColBorderColor(nextRow, 8, SHADED_AREA_COLOR);
/*  953 */                   subTable.setColBorder(nextRow, 8, 266240);
/*      */                   
/*  955 */                   subTable.setObject(nextRow, 9, MC);
/*  956 */                   subTable.setColBorderColor(nextRow, 9, SHADED_AREA_COLOR);
/*  957 */                   subTable.setColBorder(nextRow, 9, 266240);
/*      */                   
/*  959 */                   subTable.setObject(nextRow, 10, MASTERS);
/*  960 */                   subTable.setColBorderColor(nextRow, 10, Color.lightGray);
/*  961 */                   subTable.setColBorder(nextRow, 10, 266240);
/*      */                   
/*  963 */                   subTable.setObject(nextRow, 11, DJ);
/*  964 */                   subTable.setColBorderColor(nextRow, 11, Color.lightGray);
/*  965 */                   subTable.setColBorder(nextRow, 11, 266240);
/*      */                   
/*  967 */                   subTable.setObject(nextRow, 12, FILM);
/*  968 */                   subTable.setColBorderColor(nextRow, 12, SHADED_AREA_COLOR);
/*  969 */                   subTable.setColBorder(nextRow, 12, 266240);
/*      */                   
/*  971 */                   subTable.setObject(nextRow, 13, DEPOT);
/*  972 */                   subTable.setColBorderColor(nextRow, 13, SHADED_AREA_COLOR);
/*  973 */                   subTable.setColBorder(nextRow, 13, 266240);
/*      */                   
/*  975 */                   subTable.setFont(nextRow, 5, new Font("Arial", 1, 7));
/*  976 */                   subTable.setFont(nextRow, 6, new Font("Arial", 1, 7));
/*  977 */                   subTable.setFont(nextRow, 7, new Font("Arial", 1, 7));
/*  978 */                   subTable.setFont(nextRow, 8, new Font("Arial", 1, 7));
/*  979 */                   subTable.setFont(nextRow, 9, new Font("Arial", 1, 7));
/*  980 */                   subTable.setFont(nextRow, 10, new Font("Arial", 1, 7));
/*  981 */                   subTable.setFont(nextRow, 11, new Font("Arial", 1, 7));
/*  982 */                   subTable.setFont(nextRow, 12, new Font("Arial", 1, 7));
/*  983 */                   subTable.setFont(nextRow, 13, new Font("Arial", 1, 7));
/*  984 */                   subTable.setFont(nextRow, 14, new Font("Arial", 1, 7));
/*      */                   
/*  986 */                   subTable.setAlignment(nextRow, 5, 2);
/*  987 */                   subTable.setAlignment(nextRow, 6, 2);
/*  988 */                   subTable.setAlignment(nextRow, 7, 2);
/*  989 */                   subTable.setAlignment(nextRow, 8, 2);
/*  990 */                   subTable.setAlignment(nextRow, 9, 2);
/*  991 */                   subTable.setAlignment(nextRow, 10, 2);
/*  992 */                   subTable.setAlignment(nextRow, 11, 2);
/*  993 */                   subTable.setAlignment(nextRow, 12, 2);
/*  994 */                   subTable.setAlignment(nextRow, 13, 2);
/*  995 */                   subTable.setAlignment(nextRow, 14, 2);
/*      */                   
/*  997 */                   subTable.setObject(nextRow + 1, 5, PRQcom);
/*  998 */                   subTable.setObject(nextRow + 1, 6, STICcom);
/*  999 */                   subTable.setObject(nextRow + 1, 7, BOMcom);
/* 1000 */                   subTable.setObject(nextRow + 1, 8, FPcom);
/* 1001 */                   subTable.setObject(nextRow + 1, 9, MCcom);
/* 1002 */                   subTable.setObject(nextRow + 1, 10, MASTERScom);
/* 1003 */                   subTable.setObject(nextRow + 1, 11, DJcom);
/* 1004 */                   subTable.setObject(nextRow + 1, 12, FILMcom);
/* 1005 */                   subTable.setObject(nextRow + 1, 13, DEPOTcom);
/*      */                   
/* 1007 */                   setColBorderColor(subTable, nextRow + 1, 15, SHADED_AREA_COLOR);
/*      */                   
/* 1009 */                   subTable.setAlignment(nextRow + 1, 5, 10);
/* 1010 */                   subTable.setAlignment(nextRow + 1, 6, 10);
/* 1011 */                   subTable.setAlignment(nextRow + 1, 7, 10);
/* 1012 */                   subTable.setAlignment(nextRow + 1, 8, 10);
/* 1013 */                   subTable.setAlignment(nextRow + 1, 9, 10);
/* 1014 */                   subTable.setAlignment(nextRow + 1, 10, 10);
/* 1015 */                   subTable.setAlignment(nextRow + 1, 11, 10);
/* 1016 */                   subTable.setAlignment(nextRow + 1, 12, 10);
/* 1017 */                   subTable.setAlignment(nextRow + 1, 13, 10);
/* 1018 */                   subTable.setAlignment(nextRow + 1, 14, 10);
/*      */                   
/* 1020 */                   subTable.setRowFont(nextRow + 1, new Font("Arial", 0, 7));
/*      */                   
/* 1022 */                   subTable.setRowBackground(nextRow, SHADED_AREA_COLOR);
/* 1023 */                   subTable.setRowForeground(nextRow, Color.black);
/*      */                   
/* 1025 */                   subTable.setBackground(nextRow, 14, Color.white);
/* 1026 */                   subTable.setAlignment(nextRow, 14, 9);
/* 1027 */                   subTable.setSpan(nextRow, 14, new Dimension(1, 2));
/* 1028 */                   subTable.setObject(nextRow, 14, comment);
/* 1029 */                   subTable.setFont(nextRow, 14, new Font("Arial", 0, 7));
/*      */ 
/*      */                   
/* 1032 */                   subTable.setRowFont(nextRow, new Font("Arial", 0, 7));
/*      */                   
/* 1034 */                   body = new SectionBand(report);
/*      */                   
/* 1036 */                   double lfLineCount = 1.5D;
/*      */                   
/* 1038 */                   if (extraLines > 0) {
/*      */                     
/* 1040 */                     body.setHeight(1.5F);
/*      */                   }
/*      */                   else {
/*      */                     
/* 1044 */                     body.setHeight(1.5F);
/*      */                   } 
/*      */                   
/* 1047 */                   if (extraLines > 3 || 
/* 1048 */                     PRQcom.length() > 10 || STICcom.length() > 10 || 
/* 1049 */                     BOMcom.length() > 10 || FPcom.length() > 10 || 
/* 1050 */                     MCcom.length() > 10 || MASTERScom.length() > 10 || 
/* 1051 */                     DJcom.length() > 10 || FILMcom.length() > 10 || 
/* 1052 */                     DEPOTcom.length() > 10) {
/*      */ 
/*      */                     
/* 1055 */                     if (lfLineCount < extraLines * 0.3D) {
/* 1056 */                       lfLineCount = extraLines * 0.3D;
/*      */                     }
/* 1058 */                     if (lfLineCount < (PRQcom.length() / 7) * 0.3D) {
/* 1059 */                       lfLineCount = (PRQcom.length() / 7) * 0.3D;
/*      */                     }
/* 1061 */                     if (lfLineCount < (STICcom.length() / 8) * 0.3D) {
/* 1062 */                       lfLineCount = (STICcom.length() / 8) * 0.3D;
/*      */                     }
/* 1064 */                     if (lfLineCount < (BOMcom.length() / 8) * 0.3D) {
/* 1065 */                       lfLineCount = (BOMcom.length() / 8) * 0.3D;
/*      */                     }
/* 1067 */                     if (lfLineCount < (FPcom.length() / 8) * 0.3D) {
/* 1068 */                       lfLineCount = (FPcom.length() / 8) * 0.3D;
/*      */                     }
/* 1070 */                     if (lfLineCount < (MCcom.length() / 8) * 0.3D) {
/* 1071 */                       lfLineCount = (MCcom.length() / 8) * 0.3D;
/*      */                     }
/* 1073 */                     if (lfLineCount < (MASTERScom.length() / 8) * 0.3D) {
/* 1074 */                       lfLineCount = (MASTERScom.length() / 8) * 0.3D;
/*      */                     }
/* 1076 */                     if (lfLineCount < (DJcom.length() / 8) * 0.3D) {
/* 1077 */                       lfLineCount = (DJcom.length() / 8) * 0.3D;
/*      */                     }
/* 1079 */                     if (lfLineCount < (FILMcom.length() / 8) * 0.3D) {
/* 1080 */                       lfLineCount = (FILMcom.length() / 8) * 0.3D;
/*      */                     }
/* 1082 */                     if (lfLineCount < (DEPOTcom.length() / 8) * 0.3D) {
/* 1083 */                       lfLineCount = (DEPOTcom.length() / 8) * 0.3D;
/*      */                     }
/* 1085 */                     body.setHeight((float)lfLineCount);
/*      */                   }
/*      */                   else {
/*      */                     
/* 1089 */                     body.setHeight(1.5F);
/*      */                   } 
/*      */                   
/* 1092 */                   body.addTable(subTable, new Rectangle(800, 800));
/* 1093 */                   body.setBottomBorder(0);
/* 1094 */                   body.setTopBorder(0);
/* 1095 */                   body.setShrinkToFit(true);
/* 1096 */                   body.setVisible(true);
/* 1097 */                   group = new DefaultSectionLens(null, group, body);
/*      */                 } 
/*      */                 
/* 1100 */                 group = new DefaultSectionLens(null, group, spacer);
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1110 */         group = new DefaultSectionLens(hbandType, group, null);
/* 1111 */         report.addSection(group, rowCountTable);
/* 1112 */         report.addPageBreak();
/* 1113 */         group = null;
/*      */ 
/*      */       
/*      */       }
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 1121 */     catch (Exception e) {
/*      */       
/* 1123 */       System.out.println(">>>>>>>>IdjProductionScheduleForPrintSubHandler(): exception: " + e);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1132 */     System.out.println("done");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void setColBorderColor(DefaultTableLens table, int row, int columns, Color color) {
/* 1141 */     for (int i = -1; i < columns; i++)
/*      */     {
/* 1143 */       table.setColBorderColor(row, i, color);
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
/* 1157 */     int COL_LINE_STYLE = 4097;
/* 1158 */     int HEADER_FONT_SIZE = 12;
/*      */     
/* 1160 */     table_contents.setObject(nextRow, 0, "");
/* 1161 */     table_contents.setSpan(nextRow, 0, new Dimension(cols, 1));
/* 1162 */     table_contents.setRowHeight(nextRow, 1);
/* 1163 */     table_contents.setRowBackground(nextRow, Color.white);
/*      */     
/* 1165 */     table_contents.setColBorderColor(nextRow, -1, Color.black);
/* 1166 */     table_contents.setColBorder(nextRow, -1, 4097);
/* 1167 */     table_contents.setColBorderColor(nextRow, cols - 1, Color.black);
/* 1168 */     table_contents.setColBorder(nextRow, cols - 1, 4097);
/*      */     
/* 1170 */     table_contents.setRowBorderColor(nextRow, Color.white);
/* 1171 */     table_contents.setRowBorder(nextRow, 266240);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1176 */     table_contents.setColBorderColor(nextRow + 1, -1, Color.black);
/* 1177 */     table_contents.setColBorder(nextRow + 1, -1, 4097);
/* 1178 */     table_contents.setColBorderColor(nextRow + 1, cols - 1, Color.black);
/* 1179 */     table_contents.setColBorder(nextRow + 1, cols - 1, 4097);
/*      */ 
/*      */     
/* 1182 */     table_contents.setAlignment(nextRow + 1, 0, 2);
/* 1183 */     table_contents.setSpan(nextRow + 1, 0, new Dimension(cols, 1));
/* 1184 */     table_contents.setObject(nextRow + 1, 0, title);
/* 1185 */     table_contents.setRowFont(nextRow + 1, new Font("Arial", 3, 12));
/*      */     
/* 1187 */     table_contents.setColBorderColor(nextRow + 1, -1, Color.black);
/* 1188 */     table_contents.setColBorder(nextRow + 1, -1, 4097);
/*      */     
/* 1190 */     nextRow += 2;
/*      */     
/* 1192 */     table_contents.setObject(nextRow, 0, "");
/* 1193 */     table_contents.setSpan(nextRow, 0, new Dimension(cols, 1));
/* 1194 */     table_contents.setRowHeight(nextRow, 1);
/* 1195 */     table_contents.setRowBackground(nextRow, Color.white);
/*      */ 
/*      */     
/* 1198 */     table_contents.setColBorderColor(nextRow, -1, Color.black);
/* 1199 */     table_contents.setColBorder(nextRow, -1, 4097);
/* 1200 */     table_contents.setColBorderColor(nextRow, cols, Color.black);
/* 1201 */     table_contents.setColBorder(nextRow, cols, 4097);
/*      */     
/* 1203 */     table_contents.setRowBorderColor(nextRow, Color.white);
/* 1204 */     table_contents.setRowBorder(nextRow, 266240);
/*      */     
/* 1206 */     nextRow++;
/*      */ 
/*      */     
/* 1209 */     table_contents.setObject(nextRow, 0, "");
/* 1210 */     table_contents.setSpan(nextRow, 0, new Dimension(cols, 1));
/* 1211 */     table_contents.setRowHeight(nextRow, 1);
/* 1212 */     table_contents.setRowBackground(nextRow, Color.white);
/*      */ 
/*      */     
/* 1215 */     table_contents.setColBorderColor(nextRow, -1, Color.white);
/* 1216 */     table_contents.setColBorder(nextRow, -1, 4097);
/* 1217 */     table_contents.setColBorderColor(nextRow, cols - 1, Color.white);
/* 1218 */     table_contents.setColBorder(nextRow, cols, 4097);
/*      */     
/* 1220 */     table_contents.setRowBorderColor(nextRow, Color.white);
/* 1221 */     table_contents.setRowBorder(nextRow, 266240);
/*      */     
/* 1223 */     nextRow++;
/*      */     
/* 1225 */     return nextRow;
/*      */   }
/*      */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\IdjProductionScheduleForPrintSubHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */