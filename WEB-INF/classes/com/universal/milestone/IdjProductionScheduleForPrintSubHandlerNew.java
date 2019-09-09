/*      */ package WEB-INF.classes.com.universal.milestone;
/*      */ 
/*      */ import com.techempower.ComponentLog;
/*      */ import com.techempower.gemini.Context;
/*      */ import com.techempower.gemini.GeminiApplication;
/*      */ import com.universal.milestone.DatePeriod;
/*      */ import com.universal.milestone.Form;
/*      */ import com.universal.milestone.IdjProductionScheduleForPrintSubHandlerNew;
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
/*      */ public class IdjProductionScheduleForPrintSubHandlerNew
/*      */   extends SecureHandler
/*      */ {
/*      */   public static final String COMPONENT_CODE = "hPsp";
/*      */   public GeminiApplication application;
/*      */   public ComponentLog log;
/*      */   
/*      */   public IdjProductionScheduleForPrintSubHandlerNew(GeminiApplication application) {
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
/*      */     
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
/*      */ 
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
/*  282 */         table_contents.setColWidth(0, 77);
/*  283 */         table_contents.setColWidth(1, 259);
/*  284 */         table_contents.setColWidth(2, 157);
/*  285 */         table_contents.setColWidth(3, 150);
/*  286 */         table_contents.setColWidth(4, 80);
/*  287 */         table_contents.setColWidth(5, 168);
/*  288 */         table_contents.setColWidth(6, 87);
/*  289 */         table_contents.setColWidth(7, 84);
/*  290 */         table_contents.setColWidth(8, 70);
/*  291 */         table_contents.setColWidth(9, 80);
/*  292 */         table_contents.setColWidth(10, 72);
/*  293 */         table_contents.setColWidth(11, 90);
/*  294 */         table_contents.setColWidth(12, 70);
/*  295 */         table_contents.setColWidth(13, 90);
/*  296 */         table_contents.setColWidth(14, 77);
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
/*  358 */         columnHeaderTable.setColWidth(0, 77);
/*  359 */         columnHeaderTable.setColWidth(1, 259);
/*  360 */         columnHeaderTable.setColWidth(2, 157);
/*  361 */         columnHeaderTable.setColWidth(3, 150);
/*  362 */         columnHeaderTable.setColWidth(4, 80);
/*  363 */         columnHeaderTable.setColWidth(5, 168);
/*  364 */         columnHeaderTable.setColWidth(6, 87);
/*  365 */         columnHeaderTable.setColWidth(7, 84);
/*  366 */         columnHeaderTable.setColWidth(8, 70);
/*  367 */         columnHeaderTable.setColWidth(9, 80);
/*  368 */         columnHeaderTable.setColWidth(10, 72);
/*  369 */         columnHeaderTable.setColWidth(11, 90);
/*  370 */         columnHeaderTable.setColWidth(12, 70);
/*  371 */         columnHeaderTable.setColWidth(13, 90);
/*  372 */         columnHeaderTable.setColWidth(14, 77);
/*      */         
/*  374 */         columnHeaderTable.setAlignment(nextRow, 0, 33);
/*  375 */         columnHeaderTable.setAlignment(nextRow, 1, 33);
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
/*  390 */         columnHeaderTable.setObject(nextRow, 0, "Release Date\nCycle");
/*  391 */         columnHeaderTable.setObject(nextRow, 1, "Artist/Title");
/*  392 */         columnHeaderTable.setObject(nextRow, 2, "Packaging\nSpecs");
/*  393 */         columnHeaderTable.setObject(nextRow, 3, "Selection\nUPC\nConfig");
/*  394 */         columnHeaderTable.setObject(nextRow, 4, "Price\nCode\nUnits");
/*  395 */         columnHeaderTable.setObject(nextRow, 5, "Label & Contacts");
/*  396 */         columnHeaderTable.setObject(nextRow, 6, "Prod\nReq\nDue");
/*  397 */         columnHeaderTable.setObject(nextRow, 7, "Readers\nCirc");
/*  398 */         columnHeaderTable.setObject(nextRow, 8, "BOM");
/*  399 */         columnHeaderTable.setObject(nextRow, 9, "Film\nShips");
/*  400 */         columnHeaderTable.setObject(nextRow, 10, "Parts\nOrder");
/*  401 */         columnHeaderTable.setObject(nextRow, 11, "Masters\nShip");
/*  402 */         columnHeaderTable.setObject(nextRow, 12, "Manf\nCopy\nDue");
/*  403 */         columnHeaderTable.setObject(nextRow, 13, "Tests\nApproved");
/*  404 */         columnHeaderTable.setObject(nextRow, 14, "Finish\nGoods\nDepot");
/*      */ 
/*      */ 
/*      */         
/*  408 */         setColBorderColor(columnHeaderTable, nextRow, 15, SHADED_AREA_COLOR);
/*      */         
/*  410 */         columnHeaderTable.setRowBorderColor(nextRow - 1, SHADED_AREA_COLOR);
/*  411 */         columnHeaderTable.setRowBorderColor(nextRow, SHADED_AREA_COLOR);
/*  412 */         columnHeaderTable.setRowFont(nextRow, new Font("Arial", 1, 8));
/*  413 */         columnHeaderTable.setRowBackground(nextRow, Color.white);
/*  414 */         columnHeaderTable.setRowForeground(nextRow, Color.black);
/*      */ 
/*      */         
/*  417 */         hbandType.addTable(columnHeaderTable, new Rectangle(0, 24, 800, 39));
/*  418 */         hbandType.setBottomBorder(0);
/*      */ 
/*      */         
/*  421 */         if (monthTable != null) {
/*      */           
/*  423 */           Enumeration months = monthTable.keys();
/*      */           
/*  425 */           Vector monthVector = new Vector();
/*      */           
/*  427 */           while (months.hasMoreElements()) {
/*  428 */             monthVector.add((String)months.nextElement());
/*      */           }
/*  430 */           Object[] monthArray = null;
/*  431 */           monthArray = monthVector.toArray();
/*      */           
/*  433 */           Arrays.sort(monthArray, new MonthYearComparator());
/*      */           
/*  435 */           for (int x = 0; x < monthArray.length; x++) {
/*      */ 
/*      */             
/*  438 */             String monthName = (String)monthArray[x];
/*  439 */             String monthNameString = monthName;
/*      */             
/*      */             try {
/*  442 */               monthNameString = MONTHS[Integer.parseInt(monthName.substring(0, 2)) - 1];
/*      */             }
/*  444 */             catch (Exception e) {
/*      */               
/*  446 */               if (monthName.equals("13")) {
/*  447 */                 monthNameString = "TBS";
/*  448 */               } else if (monthName.equals("26")) {
/*  449 */                 monthNameString = "ITW";
/*      */               } else {
/*  451 */                 monthNameString = "No street date";
/*      */               } 
/*      */             } 
/*  454 */             monthTableLens = new DefaultTableLens(1, 15);
/*  455 */             hbandCategory = new SectionBand(report);
/*  456 */             hbandCategory.setHeight(0.25F);
/*  457 */             hbandCategory.setShrinkToFit(true);
/*  458 */             hbandCategory.setVisible(true);
/*  459 */             hbandCategory.setBottomBorder(0);
/*  460 */             hbandCategory.setLeftBorder(0);
/*  461 */             hbandCategory.setRightBorder(0);
/*  462 */             hbandCategory.setTopBorder(0);
/*      */             
/*  464 */             nextRow = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  469 */             monthTableLens.setSpan(nextRow, 0, new Dimension(15, 1));
/*  470 */             monthTableLens.setObject(nextRow, 0, monthNameString);
/*  471 */             monthTableLens.setRowHeight(nextRow, 14);
/*  472 */             monthTableLens.setRowFont(nextRow, new Font("Arial", 1, 12));
/*  473 */             monthTableLens.setRowBackground(nextRow, SHADED_AREA_COLOR);
/*  474 */             monthTableLens.setRowForeground(nextRow, Color.black);
/*  475 */             monthTableLens.setRowBorderColor(nextRow, Color.white);
/*  476 */             monthTableLens.setRowBorderColor(nextRow - 1, Color.white);
/*  477 */             monthTableLens.setColBorderColor(nextRow, -1, Color.white);
/*  478 */             monthTableLens.setColBorderColor(nextRow, 0, Color.white);
/*  479 */             monthTableLens.setColBorderColor(nextRow, 14, Color.white);
/*      */             
/*  481 */             hbandCategory.addTable(monthTableLens, new Rectangle(800, 800));
/*      */             
/*  483 */             footer.setVisible(true);
/*  484 */             footer.setHeight(0.1F);
/*  485 */             footer.setShrinkToFit(false);
/*  486 */             footer.setBottomBorder(0);
/*      */             
/*  488 */             group = new DefaultSectionLens(null, group, spacer);
/*  489 */             group = new DefaultSectionLens(null, group, hbandCategory);
/*  490 */             group = new DefaultSectionLens(null, group, spacer);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  496 */             Hashtable dateTable = (Hashtable)monthTable.get(monthName);
/*  497 */             if (dateTable != null) {
/*      */               
/*  499 */               Enumeration dateSort = dateTable.keys();
/*      */               
/*  501 */               Vector dateVector = new Vector();
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  506 */               while (dateSort.hasMoreElements()) {
/*  507 */                 dateVector.add((String)dateSort.nextElement());
/*      */               }
/*  509 */               Object[] dateArray = null;
/*      */               
/*  511 */               dateArray = dateVector.toArray();
/*  512 */               Arrays.sort(dateArray, new StringDateComparator());
/*      */               
/*  514 */               for (int dIndex = 0; dIndex < dateArray.length; dIndex++) {
/*      */ 
/*      */                 
/*  517 */                 String dateName = (String)dateArray[dIndex];
/*  518 */                 String dateNameText = dateName;
/*      */                 
/*  520 */                 if (monthNameString.equalsIgnoreCase("TBS")) {
/*      */                   
/*  522 */                   dateNameText = "TBS " + dateName;
/*      */                 }
/*  524 */                 else if (monthNameString.equalsIgnoreCase("ITW")) {
/*      */                   
/*  526 */                   dateNameText = "ITW " + dateName;
/*      */                 } 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  532 */                 String cycle = "";
/*      */ 
/*      */                 
/*      */                 try {
/*  536 */                   Calendar calanderDate = MilestoneHelper.getDate(dateNameText);
/*  537 */                   DatePeriod datePeriod = MilestoneHelper.findDatePeriod(calanderDate);
/*  538 */                   cycle = " " + datePeriod.getCycle();
/*      */                 }
/*  540 */                 catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  557 */                 nextRow = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  576 */                 selections = (Vector)dateTable.get(dateName);
/*  577 */                 if (selections == null) {
/*  578 */                   selections = new Vector();
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
/*  594 */                 MilestoneHelper.setSelectionSorting(selections, 14);
/*  595 */                 Collections.sort(selections);
/*      */ 
/*      */                 
/*  598 */                 MilestoneHelper.setSelectionSorting(selections, 4);
/*  599 */                 Collections.sort(selections);
/*      */ 
/*      */                 
/*  602 */                 MilestoneHelper.setSelectionSorting(selections, 22);
/*  603 */                 Collections.sort(selections);
/*      */ 
/*      */                 
/*  606 */                 MilestoneHelper.setSelectionSorting(selections, 9);
/*  607 */                 Collections.sort(selections);
/*      */ 
/*      */                 
/*  610 */                 MilestoneHelper.setSelectionSorting(selections, 8);
/*  611 */                 Collections.sort(selections);
/*      */ 
/*      */                 
/*  614 */                 for (int i = 0; i < selections.size(); i++) {
/*      */                   
/*  616 */                   Selection sel = (Selection)selections.elementAt(i);
/*      */                   
/*  618 */                   if (count < recordCount / tenth) {
/*      */                     
/*  620 */                     count = recordCount / tenth;
/*  621 */                     sresponse = context.getResponse();
/*  622 */                     context.putDelivery("status", new String("start_report"));
/*  623 */                     int myPercent = count * 10;
/*  624 */                     if (myPercent > 90)
/*  625 */                       myPercent = 90; 
/*  626 */                     context.putDelivery("percent", new String(String.valueOf(myPercent)));
/*  627 */                     context.includeJSP("status.jsp", "hiddenFrame");
/*  628 */                     sresponse.setContentType("text/plain");
/*  629 */                     sresponse.flushBuffer();
/*      */                   } 
/*  631 */                   recordCount++;
/*  632 */                   System.out.println(">>>>>>>>>>>>>>>>>>>>   tenth = " + tenth);
/*  633 */                   System.out.println(">>>>>>>>>>>>>>>>>>>>   count = " + count);
/*  634 */                   System.out.println(">>>>>>>>>>>>>>>>>>>>   recordCount = " + recordCount);
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  639 */                   String titleId = "";
/*  640 */                   titleId = sel.getTitleID();
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  645 */                   String artist = "";
/*  646 */                   artist = sel.getFlArtist().trim();
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  651 */                   String comment = "";
/*  652 */                   String commentStr = (sel.getSelectionComments() != null) ? sel.getSelectionComments() : "";
/*      */                   
/*  654 */                   String newComment = removeLF(commentStr, 800);
/*  655 */                   int subTableRows = 2;
/*  656 */                   if (newComment.length() > 0) {
/*  657 */                     subTableRows = 3;
/*      */                   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  665 */                   String label = "";
/*  666 */                   if (sel.getLabel() != null) {
/*  667 */                     label = sel.getLabel().getName();
/*      */                   }
/*      */ 
/*      */ 
/*      */                   
/*  672 */                   String pack = "";
/*  673 */                   pack = sel.getSelectionPackaging();
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  678 */                   String title = "";
/*  679 */                   if (sel.getTitle() != null) {
/*  680 */                     title = sel.getTitle();
/*      */                   }
/*      */ 
/*      */ 
/*      */                   
/*  685 */                   String upc = "";
/*  686 */                   upc = sel.getUpc();
/*      */                   
/*  688 */                   if (upc != null && upc.length() == 0) {
/*  689 */                     upc = titleId;
/*      */                   }
/*      */ 
/*      */ 
/*      */                   
/*  694 */                   String selConfig = "";
/*  695 */                   selConfig = (sel.getSelectionConfig() != null) ? sel.getSelectionConfig().getSelectionConfigurationAbbreviation() : "";
/*      */                   
/*  697 */                   String selSubConfig = "";
/*  698 */                   selSubConfig = (sel.getSelectionSubConfig() != null) ? sel.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation() : "";
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  703 */                   String units = "";
/*  704 */                   units = (sel.getNumberOfUnits() > 0) ? String.valueOf(sel.getNumberOfUnits()) : "";
/*      */                   
/*  706 */                   String code = (sel.getSellCode() != null) ? sel.getSellCode() : "";
/*  707 */                   if (code != null && code.startsWith("-1")) {
/*  708 */                     code = "";
/*      */                   }
/*  710 */                   String retail = "";
/*  711 */                   if (sel.getPriceCode() != null && sel.getPriceCode().getRetailCode() != null) {
/*  712 */                     retail = sel.getPriceCode().getRetailCode();
/*      */                   }
/*  714 */                   if (code.length() > 0) {
/*  715 */                     retail = "/" + retail;
/*      */                   }
/*  717 */                   String price = "";
/*  718 */                   if (sel.getPriceCode() != null && sel.getPriceCode().getTotalCost() > 0.0F) {
/*  719 */                     price = "$" + MilestoneHelper.formatDollarPrice(sel.getPriceCode().getTotalCost());
/*      */                   }
/*      */ 
/*      */ 
/*      */                   
/*  724 */                   String contact = "";
/*  725 */                   contact = (sel.getLabelContact() != null) ? sel.getLabelContact().getName() : "";
/*      */ 
/*      */ 
/*      */                   
/*  729 */                   String otherContact = "";
/*  730 */                   otherContact = (sel.getOtherContact() != null) ? sel.getOtherContact() : "";
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  735 */                   Schedule schedule = sel.getSchedule();
/*      */                   
/*  737 */                   Vector tasks = (schedule != null) ? schedule.getTasks() : null;
/*  738 */                   ScheduledTask task = null;
/*      */                   
/*  740 */                   String FP = "";
/*  741 */                   String BOM = "";
/*  742 */                   String PRQ = "";
/*  743 */                   String MASTERS = "";
/*  744 */                   String FILM = "";
/*  745 */                   String DEPOT = "";
/*  746 */                   String STIC = "";
/*  747 */                   String MC = "";
/*  748 */                   String DJ = "";
/*      */                   
/*  750 */                   String FPcom = "";
/*  751 */                   String BOMcom = "";
/*  752 */                   String PRQcom = "";
/*  753 */                   String MASTERScom = "";
/*  754 */                   String FILMcom = "";
/*  755 */                   String DEPOTcom = "";
/*  756 */                   String STICcom = "";
/*  757 */                   String MCcom = "";
/*  758 */                   String DJcom = "";
/*      */                   
/*  760 */                   if (tasks != null)
/*      */                   {
/*  762 */                     for (int j = 0; j < tasks.size(); j++) {
/*      */                       
/*  764 */                       task = (ScheduledTask)tasks.get(j);
/*      */                       
/*  766 */                       if (task != null && task.getDueDate() != null) {
/*      */                         
/*  768 */                         SimpleDateFormat dueDateFormatter = new SimpleDateFormat("M/d");
/*  769 */                         String dueDate = dueDateFormatter.format(task.getDueDate().getTime());
/*  770 */                         String completionDate = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*      */                         
/*  772 */                         if (task.getScheduledTaskStatus().equals("N/A"))
/*      */                         {
/*  774 */                           completionDate = task.getScheduledTaskStatus();
/*      */                         }
/*      */                         
/*  777 */                         completionDate = String.valueOf(completionDate) + "\n";
/*      */                         
/*  779 */                         String taskAbbrev = MilestoneHelper.getTaskAbbreivationNameById(task.getTaskAbbreviationID());
/*  780 */                         String taskComment = "";
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/*  785 */                         if (taskAbbrev.equalsIgnoreCase("PRQD")) {
/*      */                           
/*  787 */                           PRQ = dueDate;
/*  788 */                           PRQcom = String.valueOf(completionDate) + taskComment;
/*      */                         
/*      */                         }
/*  791 */                         else if (taskAbbrev.equalsIgnoreCase("READ")) {
/*      */                           
/*  793 */                           STIC = dueDate;
/*  794 */                           STICcom = String.valueOf(completionDate) + taskComment;
/*      */                         }
/*  796 */                         else if (taskAbbrev.equalsIgnoreCase("BMS")) {
/*      */                           
/*  798 */                           BOM = dueDate;
/*  799 */                           BOMcom = String.valueOf(completionDate) + taskComment;
/*      */                         
/*      */                         }
/*  802 */                         else if (taskAbbrev.equalsIgnoreCase("PFS")) {
/*      */                           
/*  804 */                           FP = dueDate;
/*  805 */                           FPcom = String.valueOf(completionDate) + taskComment;
/*      */                         
/*      */                         }
/*  808 */                         else if (taskAbbrev.equalsIgnoreCase("PO")) {
/*      */                           
/*  810 */                           MC = dueDate;
/*  811 */                           MCcom = String.valueOf(completionDate) + taskComment;
/*      */                         }
/*  813 */                         else if (taskAbbrev.equalsIgnoreCase("TPS")) {
/*      */                           
/*  815 */                           MASTERS = dueDate;
/*  816 */                           MASTERScom = String.valueOf(completionDate) + taskComment;
/*      */                         
/*      */                         }
/*  819 */                         else if (taskAbbrev.equalsIgnoreCase("TA")) {
/*      */                           
/*  821 */                           FILM = dueDate;
/*  822 */                           FILMcom = String.valueOf(completionDate) + taskComment;
/*      */                         
/*      */                         }
/*  825 */                         else if (taskAbbrev.equalsIgnoreCase("PSD")) {
/*      */                           
/*  827 */                           DEPOT = dueDate;
/*  828 */                           DEPOTcom = String.valueOf(completionDate) + taskComment;
/*      */                         
/*      */                         }
/*  831 */                         else if (taskAbbrev.equalsIgnoreCase("MCS")) {
/*      */                           
/*  833 */                           DJ = dueDate;
/*  834 */                           DJcom = String.valueOf(completionDate) + taskComment;
/*      */                         } 
/*      */                         
/*  837 */                         task = null;
/*      */                       } 
/*      */                     } 
/*      */                   }
/*      */ 
/*      */ 
/*      */                   
/*  844 */                   nextRow = 0;
/*  845 */                   subTable = new DefaultTableLens(subTableRows, 15);
/*      */ 
/*      */                   
/*  848 */                   subTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
/*      */                   
/*  850 */                   setColBorderColor(subTable, nextRow, 15, SHADED_AREA_COLOR);
/*      */                   
/*  852 */                   subTable.setHeaderRowCount(0);
/*  853 */                   subTable.setColWidth(0, 77);
/*  854 */                   subTable.setColWidth(1, 259);
/*  855 */                   subTable.setColWidth(2, 157);
/*  856 */                   subTable.setColWidth(3, 150);
/*  857 */                   subTable.setColWidth(4, 80);
/*  858 */                   subTable.setColWidth(5, 168);
/*  859 */                   subTable.setColWidth(6, 87);
/*  860 */                   subTable.setColWidth(7, 84);
/*  861 */                   subTable.setColWidth(8, 70);
/*  862 */                   subTable.setColWidth(9, 80);
/*  863 */                   subTable.setColWidth(10, 72);
/*  864 */                   subTable.setColWidth(11, 90);
/*  865 */                   subTable.setColWidth(12, 70);
/*  866 */                   subTable.setColWidth(13, 90);
/*  867 */                   subTable.setColWidth(14, 77);
/*      */                   
/*  869 */                   subTable.setRowBorderColor(nextRow - 1, Color.lightGray);
/*      */ 
/*      */                   
/*  872 */                   subTable.setObject(nextRow, 0, String.valueOf(dateNameText) + "\n" + cycle.trim());
/*  873 */                   subTable.setBackground(nextRow, 0, Color.white);
/*  874 */                   subTable.setSpan(nextRow, 0, new Dimension(1, 2));
/*  875 */                   subTable.setRowAutoSize(true);
/*  876 */                   subTable.setAlignment(nextRow, 0, 9);
/*  877 */                   subTable.setFont(nextRow, 0, new Font("Arial", 1, 7));
/*      */ 
/*      */                   
/*  880 */                   subTable.setObject(nextRow, 1, String.valueOf(artist) + "\n" + title);
/*  881 */                   subTable.setBackground(nextRow, 1, Color.white);
/*  882 */                   subTable.setSpan(nextRow, 1, new Dimension(1, 2));
/*  883 */                   subTable.setRowAutoSize(true);
/*  884 */                   subTable.setAlignment(nextRow, 1, 9);
/*  885 */                   subTable.setFont(nextRow, 1, new Font("Arial", 1, 7));
/*      */ 
/*      */ 
/*      */                   
/*  889 */                   subTable.setSpan(nextRow, 2, new Dimension(1, 2));
/*  890 */                   subTable.setObject(nextRow, 2, pack);
/*  891 */                   subTable.setAlignment(nextRow, 2, 10);
/*  892 */                   subTable.setBackground(nextRow, 2, Color.white);
/*      */ 
/*      */                   
/*  895 */                   String[] checkStrings = { comment, artist, title, pack, (new String[7][4] = label).valueOf(contact) + "/n" + otherContact, price };
/*  896 */                   int[] checkStringsLength = { 20, 30, 30, 20, 25, 25, 15 };
/*      */                   
/*  898 */                   int extraLines = MilestoneHelper.lineCountWCR(checkStrings, checkStringsLength);
/*      */                   
/*  900 */                   String[] commentString = { comment };
/*  901 */                   int[] checkCommentLength = { 30 };
/*  902 */                   int commentCounter = MilestoneHelper.lineCountWCR(commentString, checkCommentLength);
/*      */ 
/*      */                   
/*  905 */                   if (extraLines <= 2) {
/*  906 */                     extraLines--;
/*      */                   } else {
/*  908 */                     extraLines--;
/*      */                   } 
/*      */ 
/*      */ 
/*      */                   
/*  913 */                   boolean otherContactExists = false;
/*  914 */                   System.out.println("***commentCounter:[" + commentCounter + "]");
/*  915 */                   if (!otherContact.equals("") || commentCounter > 1) {
/*  916 */                     otherContactExists = true;
/*      */                   }
/*      */ 
/*      */                   
/*  920 */                   extraLines = (extraLines <= 2) ? 0 : (extraLines - 2);
/*  921 */                   for (int z = 0; z < extraLines; z++)
/*      */                   {
/*  923 */                     otherContact = String.valueOf(otherContact) + "\n";
/*      */                   }
/*      */                   
/*  926 */                   subTable.setObject(nextRow, 3, String.valueOf(upc) + "\n" + selSubConfig);
/*  927 */                   subTable.setAlignment(nextRow, 3, 10);
/*  928 */                   subTable.setBackground(nextRow, 3, Color.white);
/*  929 */                   subTable.setSpan(nextRow, 3, new Dimension(1, 2));
/*      */                   
/*  931 */                   subTable.setObject(nextRow, 4, String.valueOf(price) + "\n" + code + retail + "\n" + units);
/*  932 */                   subTable.setAlignment(nextRow, 4, 10);
/*  933 */                   subTable.setBackground(nextRow, 4, Color.white);
/*  934 */                   subTable.setSpan(nextRow, 4, new Dimension(1, 2));
/*  935 */                   subTable.setAlignment(nextRow + 1, 4, 10);
/*      */                   
/*  937 */                   subTable.setObject(nextRow, 5, "Due Dates");
/*  938 */                   subTable.setColBorderColor(nextRow, 5, SHADED_AREA_COLOR);
/*  939 */                   subTable.setColBorder(nextRow, 5, 266240);
/*  940 */                   subTable.setFont(nextRow, 5, new Font("Arial", 1, 8));
/*  941 */                   subTable.setRowBorderColor(nextRow, SHADED_AREA_COLOR);
/*      */                   
/*  943 */                   subTable.setAlignment(nextRow, 6, 10);
/*  944 */                   subTable.setAlignment(nextRow + 1, 6, 10);
/*      */ 
/*      */                   
/*  947 */                   if (otherContactExists) {
/*  948 */                     subTable.setObject(nextRow + 1, 5, String.valueOf(label) + "\n" + contact + "\n" + otherContact);
/*      */                   } else {
/*  950 */                     subTable.setObject(nextRow + 1, 5, String.valueOf(label) + "\n" + contact);
/*      */                   } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  957 */                   subTable.setRowHeight(nextRow, 9);
/*      */                   
/*  959 */                   subTable.setRowBorderColor(nextRow + 1, SHADED_AREA_COLOR);
/*      */                   
/*  961 */                   subTable.setObject(nextRow, 6, PRQ);
/*  962 */                   subTable.setColBorderColor(nextRow, 6, SHADED_AREA_COLOR);
/*  963 */                   subTable.setColBorder(nextRow, 6, 266240);
/*      */                   
/*  965 */                   subTable.setObject(nextRow, 7, STIC);
/*  966 */                   subTable.setColBorderColor(nextRow, 7, SHADED_AREA_COLOR);
/*  967 */                   subTable.setColBorder(nextRow, 7, 266240);
/*      */                   
/*  969 */                   subTable.setObject(nextRow, 8, BOM);
/*  970 */                   subTable.setColBorderColor(nextRow, 8, SHADED_AREA_COLOR);
/*  971 */                   subTable.setColBorder(nextRow, 8, 266240);
/*      */                   
/*  973 */                   subTable.setObject(nextRow, 9, FP);
/*  974 */                   subTable.setColBorderColor(nextRow, 9, SHADED_AREA_COLOR);
/*  975 */                   subTable.setColBorder(nextRow, 9, 266240);
/*      */                   
/*  977 */                   subTable.setObject(nextRow, 10, MC);
/*  978 */                   subTable.setColBorderColor(nextRow, 10, SHADED_AREA_COLOR);
/*  979 */                   subTable.setColBorder(nextRow, 10, 266240);
/*      */                   
/*  981 */                   subTable.setObject(nextRow, 11, MASTERS);
/*  982 */                   subTable.setColBorderColor(nextRow, 11, Color.lightGray);
/*  983 */                   subTable.setColBorder(nextRow, 11, 266240);
/*      */                   
/*  985 */                   subTable.setObject(nextRow, 12, DJ);
/*  986 */                   subTable.setColBorderColor(nextRow, 12, Color.lightGray);
/*  987 */                   subTable.setColBorder(nextRow, 12, 266240);
/*      */                   
/*  989 */                   subTable.setObject(nextRow, 13, FILM);
/*  990 */                   subTable.setColBorderColor(nextRow, 13, SHADED_AREA_COLOR);
/*  991 */                   subTable.setColBorder(nextRow, 13, 266240);
/*      */                   
/*  993 */                   subTable.setObject(nextRow, 14, DEPOT);
/*  994 */                   subTable.setColBorderColor(nextRow, 14, SHADED_AREA_COLOR);
/*  995 */                   subTable.setColBorder(nextRow, 14, 266240);
/*      */                   
/*  997 */                   subTable.setFont(nextRow, 5, new Font("Arial", 1, 7));
/*  998 */                   subTable.setFont(nextRow, 6, new Font("Arial", 1, 7));
/*  999 */                   subTable.setFont(nextRow, 7, new Font("Arial", 1, 7));
/* 1000 */                   subTable.setFont(nextRow, 8, new Font("Arial", 1, 7));
/* 1001 */                   subTable.setFont(nextRow, 9, new Font("Arial", 1, 7));
/* 1002 */                   subTable.setFont(nextRow, 10, new Font("Arial", 1, 7));
/* 1003 */                   subTable.setFont(nextRow, 11, new Font("Arial", 1, 7));
/* 1004 */                   subTable.setFont(nextRow, 12, new Font("Arial", 1, 7));
/* 1005 */                   subTable.setFont(nextRow, 13, new Font("Arial", 1, 7));
/* 1006 */                   subTable.setFont(nextRow, 14, new Font("Arial", 1, 7));
/*      */                   
/* 1008 */                   subTable.setAlignment(nextRow, 5, 2);
/* 1009 */                   subTable.setAlignment(nextRow, 6, 2);
/* 1010 */                   subTable.setAlignment(nextRow, 7, 2);
/* 1011 */                   subTable.setAlignment(nextRow, 8, 2);
/* 1012 */                   subTable.setAlignment(nextRow, 9, 2);
/* 1013 */                   subTable.setAlignment(nextRow, 10, 2);
/* 1014 */                   subTable.setAlignment(nextRow, 11, 2);
/* 1015 */                   subTable.setAlignment(nextRow, 12, 2);
/* 1016 */                   subTable.setAlignment(nextRow, 13, 2);
/* 1017 */                   subTable.setAlignment(nextRow, 14, 2);
/*      */                   
/* 1019 */                   subTable.setObject(nextRow + 1, 6, PRQcom);
/* 1020 */                   subTable.setObject(nextRow + 1, 7, STICcom);
/* 1021 */                   subTable.setObject(nextRow + 1, 8, BOMcom);
/* 1022 */                   subTable.setObject(nextRow + 1, 9, FPcom);
/* 1023 */                   subTable.setObject(nextRow + 1, 10, MCcom);
/* 1024 */                   subTable.setObject(nextRow + 1, 11, MASTERScom);
/* 1025 */                   subTable.setObject(nextRow + 1, 12, DJcom);
/* 1026 */                   subTable.setObject(nextRow + 1, 13, FILMcom);
/* 1027 */                   subTable.setObject(nextRow + 1, 14, DEPOTcom);
/*      */                   
/* 1029 */                   setColBorderColor(subTable, nextRow + 1, 15, SHADED_AREA_COLOR);
/*      */                   
/* 1031 */                   subTable.setAlignment(nextRow + 1, 5, 10);
/* 1032 */                   subTable.setAlignment(nextRow + 1, 6, 10);
/* 1033 */                   subTable.setAlignment(nextRow + 1, 7, 10);
/* 1034 */                   subTable.setAlignment(nextRow + 1, 8, 10);
/* 1035 */                   subTable.setAlignment(nextRow + 1, 9, 10);
/* 1036 */                   subTable.setAlignment(nextRow + 1, 10, 10);
/* 1037 */                   subTable.setAlignment(nextRow + 1, 11, 10);
/* 1038 */                   subTable.setAlignment(nextRow + 1, 12, 10);
/* 1039 */                   subTable.setAlignment(nextRow + 1, 13, 10);
/* 1040 */                   subTable.setAlignment(nextRow + 1, 14, 10);
/*      */                   
/* 1042 */                   subTable.setRowFont(nextRow + 1, new Font("Arial", 0, 7));
/*      */                   
/* 1044 */                   subTable.setRowBackground(nextRow, SHADED_AREA_COLOR);
/* 1045 */                   subTable.setRowForeground(nextRow, Color.black);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 1054 */                   subTable.setRowFont(nextRow, new Font("Arial", 0, 7));
/*      */ 
/*      */                   
/* 1057 */                   if (newComment.length() > 0) {
/* 1058 */                     nextRow++;
/*      */ 
/*      */                     
/* 1061 */                     subTable.setRowAutoSize(true);
/* 1062 */                     subTable.setRowBorderColor(nextRow + 1, Color.lightGray);
/* 1063 */                     setColBorderColor(subTable, nextRow + 1, 15, SHADED_AREA_COLOR);
/* 1064 */                     subTable.setRowFont(nextRow + 1, new Font("Arial", 3, 7));
/*      */                     
/* 1066 */                     subTable.setSpan(nextRow + 1, 0, new Dimension(1, 1));
/*      */                     
/* 1068 */                     subTable.setSpan(nextRow + 1, 1, new Dimension(1, 1));
/* 1069 */                     subTable.setAlignment(nextRow + 1, 1, 12);
/* 1070 */                     subTable.setObject(nextRow + 1, 1, "Comments:   ");
/*      */                     
/* 1072 */                     subTable.setObject(nextRow + 1, 2, newComment);
/* 1073 */                     subTable.setSpan(nextRow + 1, 2, new Dimension(13, 1));
/*      */                     
/* 1075 */                     subTable.setRowBorderColor(nextRow + 1, Color.lightGray);
/* 1076 */                     setColBorderColor(subTable, nextRow + 1, 15, SHADED_AREA_COLOR);
/* 1077 */                     subTable.setRowFont(nextRow + 1, new Font("Arial", 3, 7));
/* 1078 */                     subTable.setAlignment(nextRow + 1, 2, 9);
/* 1079 */                     subTable.setColLineWrap(2, true);
/*      */                     
/* 1081 */                     subTable.setColBorderColor(nextRow + 1, 0, Color.white);
/* 1082 */                     subTable.setColBorderColor(nextRow + 1, 1, Color.white);
/* 1083 */                     subTable.setColBorderColor(nextRow + 1, 2, Color.white);
/* 1084 */                     subTable.setColBorderColor(nextRow + 1, 3, Color.white);
/* 1085 */                     subTable.setColBorderColor(nextRow + 1, 4, Color.white);
/* 1086 */                     subTable.setColBorderColor(nextRow + 1, 5, Color.white);
/* 1087 */                     subTable.setColBorderColor(nextRow + 1, 6, Color.white);
/* 1088 */                     subTable.setColBorderColor(nextRow + 1, 7, Color.white);
/* 1089 */                     subTable.setColBorderColor(nextRow + 1, 8, Color.white);
/* 1090 */                     subTable.setColBorderColor(nextRow + 1, 9, Color.white);
/* 1091 */                     subTable.setColBorderColor(nextRow + 1, 10, Color.white);
/* 1092 */                     subTable.setColBorderColor(nextRow + 1, 11, Color.white);
/* 1093 */                     subTable.setColBorderColor(nextRow + 1, 12, Color.white);
/* 1094 */                     subTable.setColBorderColor(nextRow + 1, 13, Color.white);
/*      */                   } 
/*      */ 
/*      */                   
/* 1098 */                   body = new SectionBand(report);
/*      */                   
/* 1100 */                   double lfLineCount = 1.5D;
/*      */                   
/* 1102 */                   if (extraLines > 0) {
/*      */                     
/* 1104 */                     body.setHeight(1.0F);
/*      */                   }
/*      */                   else {
/*      */                     
/* 1108 */                     body.setHeight(1.0F);
/*      */                   } 
/*      */                   
/* 1111 */                   if (extraLines > 3 || 
/* 1112 */                     PRQcom.length() > 10 || STICcom.length() > 10 || 
/* 1113 */                     BOMcom.length() > 10 || FPcom.length() > 10 || 
/* 1114 */                     MCcom.length() > 10 || MASTERScom.length() > 10 || 
/* 1115 */                     DJcom.length() > 10 || FILMcom.length() > 10 || 
/* 1116 */                     DEPOTcom.length() > 10) {
/*      */ 
/*      */                     
/* 1119 */                     if (lfLineCount < extraLines * 0.3D) {
/* 1120 */                       lfLineCount = extraLines * 0.3D;
/*      */                     }
/* 1122 */                     if (lfLineCount < (PRQcom.length() / 7) * 0.3D) {
/* 1123 */                       lfLineCount = (PRQcom.length() / 7) * 0.3D;
/*      */                     }
/* 1125 */                     if (lfLineCount < (STICcom.length() / 8) * 0.3D) {
/* 1126 */                       lfLineCount = (STICcom.length() / 8) * 0.3D;
/*      */                     }
/* 1128 */                     if (lfLineCount < (BOMcom.length() / 8) * 0.3D) {
/* 1129 */                       lfLineCount = (BOMcom.length() / 8) * 0.3D;
/*      */                     }
/* 1131 */                     if (lfLineCount < (FPcom.length() / 8) * 0.3D) {
/* 1132 */                       lfLineCount = (FPcom.length() / 8) * 0.3D;
/*      */                     }
/* 1134 */                     if (lfLineCount < (MCcom.length() / 8) * 0.3D) {
/* 1135 */                       lfLineCount = (MCcom.length() / 8) * 0.3D;
/*      */                     }
/* 1137 */                     if (lfLineCount < (MASTERScom.length() / 8) * 0.3D) {
/* 1138 */                       lfLineCount = (MASTERScom.length() / 8) * 0.3D;
/*      */                     }
/* 1140 */                     if (lfLineCount < (DJcom.length() / 8) * 0.3D) {
/* 1141 */                       lfLineCount = (DJcom.length() / 8) * 0.3D;
/*      */                     }
/* 1143 */                     if (lfLineCount < (FILMcom.length() / 8) * 0.3D) {
/* 1144 */                       lfLineCount = (FILMcom.length() / 8) * 0.3D;
/*      */                     }
/* 1146 */                     if (lfLineCount < (DEPOTcom.length() / 8) * 0.3D) {
/* 1147 */                       lfLineCount = (DEPOTcom.length() / 8) * 0.3D;
/*      */                     }
/* 1149 */                     body.setHeight((float)lfLineCount);
/*      */                   }
/*      */                   else {
/*      */                     
/* 1153 */                     body.setHeight(1.0F);
/*      */                   } 
/*      */                   
/* 1156 */                   body.addTable(subTable, new Rectangle(800, 800));
/* 1157 */                   body.setBottomBorder(0);
/* 1158 */                   body.setTopBorder(0);
/* 1159 */                   body.setShrinkToFit(true);
/* 1160 */                   body.setVisible(true);
/* 1161 */                   group = new DefaultSectionLens(null, group, body);
/*      */                 } 
/*      */                 
/* 1164 */                 group = new DefaultSectionLens(null, group, spacer);
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1174 */         group = new DefaultSectionLens(hbandType, group, null);
/* 1175 */         report.addSection(group, rowCountTable);
/* 1176 */         report.addPageBreak();
/* 1177 */         group = null;
/*      */ 
/*      */       
/*      */       }
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 1185 */     catch (Exception e) {
/*      */       
/* 1187 */       System.out.println(">>>>>>>>IdjProductionScheduleForPrintSubHandler(): exception: " + e);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1196 */     System.out.println("done");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void setColBorderColor(DefaultTableLens table, int row, int columns, Color color) {
/* 1205 */     for (int i = -1; i < columns; i++)
/*      */     {
/* 1207 */       table.setColBorderColor(row, i, color);
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
/* 1221 */     int COL_LINE_STYLE = 4097;
/* 1222 */     int HEADER_FONT_SIZE = 12;
/*      */     
/* 1224 */     table_contents.setObject(nextRow, 0, "");
/* 1225 */     table_contents.setSpan(nextRow, 0, new Dimension(cols, 1));
/* 1226 */     table_contents.setRowHeight(nextRow, 1);
/* 1227 */     table_contents.setRowBackground(nextRow, Color.white);
/*      */     
/* 1229 */     table_contents.setColBorderColor(nextRow, -1, Color.black);
/* 1230 */     table_contents.setColBorder(nextRow, -1, 4097);
/* 1231 */     table_contents.setColBorderColor(nextRow, cols - 1, Color.black);
/* 1232 */     table_contents.setColBorder(nextRow, cols - 1, 4097);
/*      */     
/* 1234 */     table_contents.setRowBorderColor(nextRow, Color.white);
/* 1235 */     table_contents.setRowBorder(nextRow, 266240);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1240 */     table_contents.setColBorderColor(nextRow + 1, -1, Color.black);
/* 1241 */     table_contents.setColBorder(nextRow + 1, -1, 4097);
/* 1242 */     table_contents.setColBorderColor(nextRow + 1, cols - 1, Color.black);
/* 1243 */     table_contents.setColBorder(nextRow + 1, cols - 1, 4097);
/*      */ 
/*      */     
/* 1246 */     table_contents.setAlignment(nextRow + 1, 0, 2);
/* 1247 */     table_contents.setSpan(nextRow + 1, 0, new Dimension(cols, 1));
/* 1248 */     table_contents.setObject(nextRow + 1, 0, title);
/* 1249 */     table_contents.setRowFont(nextRow + 1, new Font("Arial", 3, 12));
/*      */     
/* 1251 */     table_contents.setColBorderColor(nextRow + 1, -1, Color.black);
/* 1252 */     table_contents.setColBorder(nextRow + 1, -1, 4097);
/*      */     
/* 1254 */     nextRow += 2;
/*      */     
/* 1256 */     table_contents.setObject(nextRow, 0, "");
/* 1257 */     table_contents.setSpan(nextRow, 0, new Dimension(cols, 1));
/* 1258 */     table_contents.setRowHeight(nextRow, 1);
/* 1259 */     table_contents.setRowBackground(nextRow, Color.white);
/*      */ 
/*      */     
/* 1262 */     table_contents.setColBorderColor(nextRow, -1, Color.black);
/* 1263 */     table_contents.setColBorder(nextRow, -1, 4097);
/* 1264 */     table_contents.setColBorderColor(nextRow, cols, Color.black);
/* 1265 */     table_contents.setColBorder(nextRow, cols, 4097);
/*      */     
/* 1267 */     table_contents.setRowBorderColor(nextRow, Color.white);
/* 1268 */     table_contents.setRowBorder(nextRow, 266240);
/*      */     
/* 1270 */     nextRow++;
/*      */ 
/*      */     
/* 1273 */     table_contents.setObject(nextRow, 0, "");
/* 1274 */     table_contents.setSpan(nextRow, 0, new Dimension(cols, 1));
/* 1275 */     table_contents.setRowHeight(nextRow, 1);
/* 1276 */     table_contents.setRowBackground(nextRow, Color.white);
/*      */ 
/*      */     
/* 1279 */     table_contents.setColBorderColor(nextRow, -1, Color.white);
/* 1280 */     table_contents.setColBorder(nextRow, -1, 4097);
/* 1281 */     table_contents.setColBorderColor(nextRow, cols - 1, Color.white);
/* 1282 */     table_contents.setColBorder(nextRow, cols, 4097);
/*      */     
/* 1284 */     table_contents.setRowBorderColor(nextRow, Color.white);
/* 1285 */     table_contents.setRowBorder(nextRow, 266240);
/*      */     
/* 1287 */     nextRow++;
/*      */     
/* 1289 */     return nextRow;
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
/* 1304 */   public static String removeLF(String theString, int maxChars) { return theString.replace('\n', ' '); }
/*      */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\IdjProductionScheduleForPrintSubHandlerNew.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */