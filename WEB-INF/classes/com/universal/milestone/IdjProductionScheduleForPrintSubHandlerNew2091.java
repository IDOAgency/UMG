/*      */ package WEB-INF.classes.com.universal.milestone;
/*      */ 
/*      */ import com.techempower.ComponentLog;
/*      */ import com.techempower.gemini.Context;
/*      */ import com.techempower.gemini.GeminiApplication;
/*      */ import com.universal.milestone.DatePeriod;
/*      */ import com.universal.milestone.Form;
/*      */ import com.universal.milestone.IdjProductionScheduleForPrintSubHandlerNew2091;
/*      */ import com.universal.milestone.JdbcConnector;
/*      */ import com.universal.milestone.MilestoneHelper;
/*      */ import com.universal.milestone.MilestoneHelper_2;
/*      */ import com.universal.milestone.MonthYearComparator;
/*      */ import com.universal.milestone.MultCompleteDate;
/*      */ import com.universal.milestone.ReportingServices;
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
/*      */ import java.util.Iterator;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class IdjProductionScheduleForPrintSubHandlerNew2091
/*      */   extends SecureHandler
/*      */ {
/*      */   public static final String COMPONENT_CODE = "hPsp";
/*      */   public GeminiApplication application;
/*      */   public ComponentLog log;
/*      */   
/*      */   public IdjProductionScheduleForPrintSubHandlerNew2091(GeminiApplication application) {
/*   82 */     this.application = application;
/*   83 */     this.log = application.getLog("hPsp");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   91 */   public String getDescription() { return "Sub Report"; }
/*      */ 
/*      */ 
/*      */ 
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
/*  105 */     int COL_LINE_STYLE = 4097;
/*  106 */     int HEADER_FONT_SIZE = 12;
/*      */     
/*  108 */     double ldLineVal = 0.3D;
/*      */     
/*  110 */     if (!ReportingServices.usingReportServicesByContext(context)) {
/*      */       
/*      */       try {
/*      */         
/*  114 */         HttpServletResponse sresponse = context.getResponse();
/*  115 */         context.putDelivery("status", new String("start_gathering"));
/*  116 */         context.includeJSP("status.jsp", "hiddenFrame");
/*  117 */         sresponse.setContentType("text/plain");
/*  118 */         sresponse.flushBuffer();
/*      */       }
/*  120 */       catch (Exception exception) {}
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  125 */     Vector selections = MilestoneHelper.getSelectionsForReport(context);
/*  126 */     if (ReportingServices.usingReportServicesByContext(context)) {
/*      */       return;
/*      */     }
/*      */     
/*  130 */     Vector multCompleteDates = getRptMultCompleteDates(selections);
/*      */ 
/*      */     
/*      */     try {
/*  134 */       HttpServletResponse sresponse = context.getResponse();
/*  135 */       context.putDelivery("status", new String("start_report"));
/*  136 */       context.putDelivery("percent", new String("10"));
/*  137 */       context.includeJSP("status.jsp", "hiddenFrame");
/*  138 */       sresponse.setContentType("text/plain");
/*  139 */       sresponse.flushBuffer();
/*      */     }
/*  141 */     catch (Exception exception) {}
/*      */ 
/*      */     
/*  144 */     int DATA_FONT_SIZE = 7;
/*  145 */     int SMALL_HEADER_FONT_SIZE = 8;
/*  146 */     int NUM_COLUMNS = 15;
/*  147 */     Color SHADED_AREA_COLOR = Color.lightGray;
/*      */     
/*  149 */     SectionBand hbandType = new SectionBand(report);
/*  150 */     SectionBand hbandCategory = new SectionBand(report);
/*      */     
/*  152 */     SectionBand body = new SectionBand(report);
/*  153 */     SectionBand footer = new SectionBand(report);
/*  154 */     SectionBand spacer = new SectionBand(report);
/*  155 */     DefaultSectionLens group = null;
/*      */     
/*  157 */     footer.setVisible(true);
/*  158 */     footer.setHeight(0.1F);
/*  159 */     footer.setShrinkToFit(false);
/*  160 */     footer.setBottomBorder(0);
/*      */     
/*  162 */     spacer.setVisible(true);
/*  163 */     spacer.setHeight(0.05F);
/*  164 */     spacer.setShrinkToFit(false);
/*  165 */     spacer.setBottomBorder(0);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  170 */     Hashtable selTable = MilestoneHelper.groupSelectionsforIDJByConfigAndStreetDate(selections);
/*  171 */     Enumeration configs = selTable.keys();
/*  172 */     Vector configVector = new Vector();
/*      */     
/*  174 */     while (configs.hasMoreElements()) {
/*  175 */       configVector.addElement(configs.nextElement());
/*      */     }
/*  177 */     int numConfigs = configVector.size();
/*      */ 
/*      */     
/*      */     try {
/*  181 */       Collections.sort(configVector);
/*  182 */       Vector sortedConfigVector = MilestoneHelper.sortStrings(configVector);
/*      */ 
/*      */       
/*  185 */       DefaultTableLens table_contents = null;
/*  186 */       DefaultTableLens rowCountTable = null;
/*  187 */       DefaultTableLens columnHeaderTable = null;
/*  188 */       DefaultTableLens subTable = null;
/*  189 */       DefaultTableLens monthTableLens = null;
/*      */ 
/*      */       
/*  192 */       rowCountTable = new DefaultTableLens(2, 10000);
/*      */ 
/*      */       
/*  195 */       int totalCount = 0;
/*  196 */       int tenth = 1;
/*      */ 
/*      */       
/*  199 */       for (int n = 0; n < sortedConfigVector.size(); n++) {
/*      */         
/*  201 */         String configC = (configVector.elementAt(n) != null) ? (String)configVector.elementAt(n) : "";
/*  202 */         Hashtable monthTableC = (Hashtable)selTable.get(configC);
/*      */         
/*  204 */         totalCount++;
/*  205 */         Enumeration monthsC = monthTableC.keys();
/*      */         
/*  207 */         Vector monthVectorC = new Vector();
/*      */         
/*  209 */         while (monthsC.hasMoreElements()) {
/*  210 */           monthVectorC.add((String)monthsC.nextElement());
/*  211 */           Object[] monthArrayC = null;
/*  212 */           monthArrayC = monthVectorC.toArray();
/*  213 */           totalCount += monthArrayC.length;
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  218 */       tenth = (totalCount > 10) ? (totalCount / 10) : 1;
/*      */       
/*  220 */       HttpServletResponse sresponse = context.getResponse();
/*  221 */       context.putDelivery("status", new String("start_report"));
/*  222 */       context.putDelivery("percent", new String("20"));
/*  223 */       context.includeJSP("status.jsp", "hiddenFrame");
/*  224 */       sresponse.setContentType("text/plain");
/*  225 */       sresponse.flushBuffer();
/*      */       
/*  227 */       int recordCount = 0;
/*  228 */       int count = 0;
/*      */       
/*  230 */       for (int n = 0; n < sortedConfigVector.size(); n++)
/*      */       {
/*      */ 
/*      */ 
/*      */         
/*  235 */         Form reportForm = (Form)context.getSessionValue("reportForm");
/*      */         
/*  237 */         Calendar beginStDate = (reportForm.getStringValue("beginDate") != null && 
/*  238 */           reportForm.getStringValue("beginDate").length() > 0) ? 
/*  239 */           MilestoneHelper.getDate(reportForm.getStringValue("beginDate")) : null;
/*      */         
/*  241 */         Calendar endStDate = (reportForm.getStringValue("endDate") != null && 
/*  242 */           reportForm.getStringValue("endDate").length() > 0) ? 
/*  243 */           MilestoneHelper.getDate(reportForm.getStringValue("endDate")) : null;
/*      */         
/*  245 */         report.setElement("crs_startdate", MilestoneHelper.getFormatedDate(beginStDate));
/*  246 */         report.setElement("crs_enddate", MilestoneHelper.getFormatedDate(endStDate));
/*      */         
/*  248 */         SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
/*  249 */         String todayLong = formatter.format(new Date());
/*  250 */         report.setElement("crs_bottomdate", todayLong);
/*      */         
/*  252 */         String config = (configVector.elementAt(n) != null) ? (String)configVector.elementAt(n) : "";
/*      */         
/*  254 */         Hashtable monthTable = (Hashtable)selTable.get(config);
/*      */ 
/*      */         
/*  257 */         int numMonths = 0;
/*  258 */         int numDates = 0;
/*  259 */         int numSelections = 0;
/*      */         
/*  261 */         if (monthTable != null) {
/*      */           
/*  263 */           Enumeration months = monthTable.keys();
/*  264 */           while (months.hasMoreElements()) {
/*      */             
/*  266 */             String monthName = (String)months.nextElement();
/*      */             
/*  268 */             numMonths++;
/*      */             
/*  270 */             Hashtable dateTable = (Hashtable)monthTable.get(monthName);
/*  271 */             if (dateTable != null) {
/*      */               
/*  273 */               Enumeration dates = dateTable.keys();
/*  274 */               while (dates.hasMoreElements()) {
/*      */                 
/*  276 */                 String dateName = (String)dates.nextElement();
/*      */                 
/*  278 */                 numDates++;
/*      */                 
/*  280 */                 selections = (Vector)dateTable.get(dateName);
/*  281 */                 if (selections != null) {
/*  282 */                   numSelections += selections.size();
/*      */                 }
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */         
/*  289 */         int numRows = 0;
/*      */ 
/*      */         
/*  292 */         numRows += numMonths * 3;
/*  293 */         numRows += numDates * 2;
/*  294 */         numRows += numSelections * 2;
/*      */         
/*  296 */         numRows += 5;
/*      */ 
/*      */         
/*  299 */         hbandType = new SectionBand(report);
/*  300 */         hbandType.setHeight(0.95F);
/*  301 */         hbandType.setShrinkToFit(true);
/*  302 */         hbandType.setVisible(true);
/*      */         
/*  304 */         table_contents = new DefaultTableLens(1, 15);
/*      */ 
/*      */         
/*  307 */         table_contents.setHeaderRowCount(0);
/*  308 */         table_contents.setColWidth(0, 77);
/*  309 */         table_contents.setColWidth(1, 259);
/*  310 */         table_contents.setColWidth(2, 157);
/*  311 */         table_contents.setColWidth(3, 150);
/*  312 */         table_contents.setColWidth(4, 80);
/*  313 */         table_contents.setColWidth(5, 168);
/*  314 */         table_contents.setColWidth(6, 87);
/*  315 */         table_contents.setColWidth(7, 84);
/*  316 */         table_contents.setColWidth(8, 84);
/*  317 */         table_contents.setColWidth(9, 70);
/*      */         
/*  319 */         table_contents.setColWidth(10, 80);
/*      */         
/*  321 */         table_contents.setColWidth(11, 90);
/*  322 */         table_contents.setColWidth(12, 90);
/*  323 */         table_contents.setColWidth(13, 70);
/*  324 */         table_contents.setColWidth(14, 90);
/*      */         
/*  326 */         table_contents.setColBorderColor(Color.black);
/*  327 */         table_contents.setRowBorderColor(Color.black);
/*  328 */         table_contents.setRowBorder(4097);
/*  329 */         table_contents.setColBorder(4097);
/*      */ 
/*      */         
/*  332 */         int nextRow = 0;
/*      */ 
/*      */         
/*  335 */         String configHeaderText = !config.trim().equals("") ? config : "Other";
/*      */ 
/*      */         
/*  338 */         if (configHeaderText != null)
/*      */         {
/*  340 */           if (configHeaderText.startsWith("Commercial CD Single")) {
/*  341 */             configHeaderText = "Commercial Singles";
/*      */           }
/*  343 */           else if (configHeaderText.startsWith("Promotional CD Single")) {
/*  344 */             configHeaderText = "Promos Singles";
/*      */           } 
/*      */         }
/*      */ 
/*      */         
/*  349 */         table_contents.setSpan(nextRow, 0, new Dimension(15, 1));
/*  350 */         table_contents.setAlignment(nextRow, 0, 2);
/*  351 */         table_contents.setObject(nextRow, 0, configHeaderText);
/*  352 */         table_contents.setRowHeight(nextRow, 16);
/*      */         
/*  354 */         table_contents.setRowBorderColor(nextRow, Color.black);
/*  355 */         table_contents.setRowBorder(nextRow, 0, 266240);
/*      */         
/*  357 */         table_contents.setRowFont(nextRow, new Font("Arial", 3, 12));
/*  358 */         table_contents.setRowBackground(nextRow, Color.white);
/*  359 */         table_contents.setRowForeground(nextRow, Color.black);
/*      */         
/*  361 */         table_contents.setRowBorder(nextRow - 1, 266240);
/*  362 */         table_contents.setColBorder(nextRow, -1, 266240);
/*  363 */         table_contents.setColBorder(nextRow, 0, 266240);
/*  364 */         table_contents.setColBorder(nextRow, 14, 266240);
/*  365 */         table_contents.setColBorder(nextRow, 15, 266240);
/*  366 */         table_contents.setRowBorder(nextRow, 266240);
/*      */         
/*  368 */         table_contents.setRowBorderColor(nextRow - 1, Color.black);
/*  369 */         table_contents.setColBorderColor(nextRow, -1, Color.black);
/*  370 */         table_contents.setColBorderColor(nextRow, 0, Color.black);
/*  371 */         table_contents.setColBorderColor(nextRow, 14, Color.black);
/*  372 */         table_contents.setColBorderColor(nextRow, 15, Color.black);
/*  373 */         table_contents.setRowBorderColor(nextRow, Color.black);
/*      */ 
/*      */         
/*  376 */         hbandType.addTable(table_contents, new Rectangle(0, 0, 800, 25));
/*      */         
/*  378 */         nextRow = 0;
/*      */         
/*  380 */         columnHeaderTable = new DefaultTableLens(1, 15);
/*      */         
/*  382 */         columnHeaderTable.setHeaderRowCount(0);
/*  383 */         columnHeaderTable.setColWidth(0, 77);
/*  384 */         columnHeaderTable.setColWidth(1, 259);
/*  385 */         columnHeaderTable.setColWidth(2, 157);
/*  386 */         columnHeaderTable.setColWidth(3, 150);
/*  387 */         columnHeaderTable.setColWidth(4, 80);
/*  388 */         columnHeaderTable.setColWidth(5, 168);
/*  389 */         columnHeaderTable.setColWidth(6, 87);
/*  390 */         columnHeaderTable.setColWidth(7, 84);
/*  391 */         columnHeaderTable.setColWidth(8, 70);
/*  392 */         columnHeaderTable.setColWidth(9, 80);
/*      */         
/*  394 */         columnHeaderTable.setColWidth(10, 90);
/*      */         
/*  396 */         columnHeaderTable.setColWidth(11, 72);
/*  397 */         columnHeaderTable.setColWidth(12, 90);
/*  398 */         columnHeaderTable.setColWidth(13, 70);
/*  399 */         columnHeaderTable.setColWidth(14, 90);
/*      */         
/*  401 */         columnHeaderTable.setAlignment(nextRow, 0, 33);
/*  402 */         columnHeaderTable.setAlignment(nextRow, 1, 33);
/*  403 */         columnHeaderTable.setAlignment(nextRow, 2, 34);
/*  404 */         columnHeaderTable.setAlignment(nextRow, 3, 34);
/*  405 */         columnHeaderTable.setAlignment(nextRow, 4, 34);
/*  406 */         columnHeaderTable.setAlignment(nextRow, 5, 34);
/*  407 */         columnHeaderTable.setAlignment(nextRow, 6, 34);
/*  408 */         columnHeaderTable.setAlignment(nextRow, 7, 34);
/*  409 */         columnHeaderTable.setAlignment(nextRow, 8, 34);
/*  410 */         columnHeaderTable.setAlignment(nextRow, 9, 34);
/*  411 */         columnHeaderTable.setAlignment(nextRow, 10, 34);
/*  412 */         columnHeaderTable.setAlignment(nextRow, 11, 34);
/*  413 */         columnHeaderTable.setAlignment(nextRow, 12, 34);
/*  414 */         columnHeaderTable.setAlignment(nextRow, 13, 34);
/*  415 */         columnHeaderTable.setAlignment(nextRow, 14, 34);
/*      */         
/*  417 */         columnHeaderTable.setObject(nextRow, 0, "Release Date\nCycle");
/*  418 */         columnHeaderTable.setObject(nextRow, 1, "Artist/Title");
/*  419 */         columnHeaderTable.setObject(nextRow, 2, "Packaging\nSpecs");
/*  420 */         columnHeaderTable.setObject(nextRow, 3, "Local Prod #\nUPC\nConfig\nImpact Date");
/*  421 */         columnHeaderTable.setObject(nextRow, 4, "Price\nCode\nUnits");
/*  422 */         columnHeaderTable.setObject(nextRow, 5, "Label & Contacts");
/*  423 */         columnHeaderTable.setObject(nextRow, 6, "Prod\nReq\nDue");
/*  424 */         columnHeaderTable.setObject(nextRow, 7, "Sol\nFilm\nDue");
/*  425 */         columnHeaderTable.setObject(nextRow, 8, "Readers\nCirc");
/*  426 */         columnHeaderTable.setObject(nextRow, 9, "BOM");
/*  427 */         columnHeaderTable.setObject(nextRow, 10, "Film\nShips");
/*      */         
/*  429 */         columnHeaderTable.setObject(nextRow, 11, "Signed\nProd\nReq Due");
/*  430 */         columnHeaderTable.setObject(nextRow, 12, "Masters\nShip");
/*  431 */         columnHeaderTable.setObject(nextRow, 13, "Manf\nCopy\nDue");
/*  432 */         columnHeaderTable.setObject(nextRow, 14, "Tests\nApproved");
/*      */         
/*  434 */         setColBorderColor(columnHeaderTable, nextRow, 15, SHADED_AREA_COLOR);
/*      */         
/*  436 */         columnHeaderTable.setRowBorderColor(nextRow - 1, SHADED_AREA_COLOR);
/*  437 */         columnHeaderTable.setRowBorderColor(nextRow, SHADED_AREA_COLOR);
/*  438 */         columnHeaderTable.setRowFont(nextRow, new Font("Arial", 1, 8));
/*  439 */         columnHeaderTable.setRowBackground(nextRow, Color.white);
/*  440 */         columnHeaderTable.setRowForeground(nextRow, Color.black);
/*      */         
/*  442 */         hbandType.addTable(columnHeaderTable, new Rectangle(0, 24, 800, 45));
/*  443 */         hbandType.setBottomBorder(0);
/*      */ 
/*      */         
/*  446 */         if (monthTable != null) {
/*      */           
/*  448 */           Enumeration months = monthTable.keys();
/*      */           
/*  450 */           Vector monthVector = new Vector();
/*      */           
/*  452 */           while (months.hasMoreElements()) {
/*  453 */             monthVector.add((String)months.nextElement());
/*      */           }
/*  455 */           Object[] monthArray = null;
/*  456 */           monthArray = monthVector.toArray();
/*      */           
/*  458 */           Arrays.sort(monthArray, new MonthYearComparator());
/*      */           
/*  460 */           for (int x = 0; x < monthArray.length; x++) {
/*      */ 
/*      */             
/*  463 */             String monthName = (String)monthArray[x];
/*  464 */             String monthNameString = monthName;
/*      */             
/*      */             try {
/*  467 */               monthNameString = MONTHS[Integer.parseInt(monthName.substring(0, 2)) - 1];
/*      */             }
/*  469 */             catch (Exception e) {
/*      */               
/*  471 */               if (monthName.equals("13")) {
/*  472 */                 monthNameString = "TBS";
/*  473 */               } else if (monthName.equals("26")) {
/*  474 */                 monthNameString = "ITW";
/*      */               } else {
/*  476 */                 monthNameString = "No street date";
/*      */               } 
/*      */             } 
/*  479 */             monthTableLens = new DefaultTableLens(1, 15);
/*  480 */             hbandCategory = new SectionBand(report);
/*  481 */             hbandCategory.setHeight(0.25F);
/*  482 */             hbandCategory.setShrinkToFit(true);
/*  483 */             hbandCategory.setVisible(true);
/*  484 */             hbandCategory.setBottomBorder(0);
/*  485 */             hbandCategory.setLeftBorder(0);
/*  486 */             hbandCategory.setRightBorder(0);
/*  487 */             hbandCategory.setTopBorder(0);
/*      */             
/*  489 */             nextRow = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  494 */             monthTableLens.setSpan(nextRow, 0, new Dimension(15, 1));
/*  495 */             monthTableLens.setObject(nextRow, 0, monthNameString);
/*  496 */             monthTableLens.setRowHeight(nextRow, 14);
/*  497 */             monthTableLens.setRowFont(nextRow, new Font("Arial", 1, 12));
/*  498 */             monthTableLens.setRowBackground(nextRow, SHADED_AREA_COLOR);
/*  499 */             monthTableLens.setRowForeground(nextRow, Color.black);
/*  500 */             monthTableLens.setRowBorderColor(nextRow, Color.white);
/*  501 */             monthTableLens.setRowBorderColor(nextRow - 1, Color.white);
/*  502 */             monthTableLens.setColBorderColor(nextRow, -1, Color.white);
/*  503 */             monthTableLens.setColBorderColor(nextRow, 0, Color.white);
/*  504 */             monthTableLens.setColBorderColor(nextRow, 14, Color.white);
/*      */             
/*  506 */             hbandCategory.addTable(monthTableLens, new Rectangle(800, 800));
/*      */             
/*  508 */             footer.setVisible(true);
/*  509 */             footer.setHeight(0.1F);
/*  510 */             footer.setShrinkToFit(false);
/*  511 */             footer.setBottomBorder(0);
/*      */             
/*  513 */             group = new DefaultSectionLens(null, group, spacer);
/*  514 */             group = new DefaultSectionLens(null, group, hbandCategory);
/*  515 */             group = new DefaultSectionLens(null, group, spacer);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  521 */             Hashtable dateTable = (Hashtable)monthTable.get(monthName);
/*  522 */             if (dateTable != null) {
/*      */               
/*  524 */               Enumeration dateSort = dateTable.keys();
/*      */               
/*  526 */               Vector dateVector = new Vector();
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  531 */               while (dateSort.hasMoreElements()) {
/*  532 */                 dateVector.add((String)dateSort.nextElement());
/*      */               }
/*  534 */               Object[] dateArray = null;
/*      */               
/*  536 */               dateArray = dateVector.toArray();
/*  537 */               Arrays.sort(dateArray, new StringDateComparator());
/*      */               
/*  539 */               for (int dIndex = 0; dIndex < dateArray.length; dIndex++) {
/*      */ 
/*      */                 
/*  542 */                 String dateName = (String)dateArray[dIndex];
/*  543 */                 String dateNameText = dateName;
/*      */                 
/*  545 */                 if (monthNameString.equalsIgnoreCase("TBS")) {
/*      */                   
/*  547 */                   dateNameText = "TBS " + dateName;
/*      */                 }
/*  549 */                 else if (monthNameString.equalsIgnoreCase("ITW")) {
/*      */                   
/*  551 */                   dateNameText = "ITW " + dateName;
/*      */                 } 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  557 */                 String cycle = "";
/*      */ 
/*      */                 
/*      */                 try {
/*  561 */                   Calendar calanderDate = MilestoneHelper.getDate(dateNameText);
/*  562 */                   DatePeriod datePeriod = MilestoneHelper.findDatePeriod(calanderDate);
/*  563 */                   cycle = " " + datePeriod.getCycle();
/*      */                 }
/*  565 */                 catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  582 */                 nextRow = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  601 */                 selections = (Vector)dateTable.get(dateName);
/*  602 */                 if (selections == null) {
/*  603 */                   selections = new Vector();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  640 */                 MilestoneHelper.setSelectionSorting(selections, 4);
/*  641 */                 Collections.sort(selections);
/*      */                 
/*  643 */                 MilestoneHelper.setSelectionSorting(selections, 22);
/*  644 */                 Collections.sort(selections);
/*      */ 
/*      */                 
/*  647 */                 for (int i = 0; i < selections.size(); i++) {
/*      */                   
/*  649 */                   Selection sel = (Selection)selections.elementAt(i);
/*      */                   
/*  651 */                   if (count < recordCount / tenth) {
/*      */                     
/*  653 */                     count = recordCount / tenth;
/*  654 */                     sresponse = context.getResponse();
/*  655 */                     context.putDelivery("status", new String("start_report"));
/*  656 */                     int myPercent = count * 10;
/*  657 */                     if (myPercent > 90)
/*  658 */                       myPercent = 90; 
/*  659 */                     context.putDelivery("percent", new String(String.valueOf(myPercent)));
/*  660 */                     context.includeJSP("status.jsp", "hiddenFrame");
/*  661 */                     sresponse.setContentType("text/plain");
/*  662 */                     sresponse.flushBuffer();
/*      */                   } 
/*  664 */                   recordCount++;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  672 */                   String titleId = "";
/*  673 */                   titleId = sel.getTitleID();
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  678 */                   String artist = "";
/*  679 */                   artist = sel.getFlArtist().trim();
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  684 */                   String comment = "";
/*  685 */                   String commentStr = (sel.getSelectionComments() != null) ? sel.getSelectionComments() : "";
/*      */                   
/*  687 */                   String newComment = removeLF(commentStr, 800);
/*  688 */                   int subTableRows = 2;
/*  689 */                   if (newComment.length() > 0) {
/*  690 */                     subTableRows = 3;
/*      */                   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  698 */                   String label = "";
/*  699 */                   label = sel.getImprint();
/*      */ 
/*      */ 
/*      */                   
/*  703 */                   String pack = "";
/*  704 */                   pack = sel.getSelectionPackaging();
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  709 */                   String title = "";
/*  710 */                   if (sel.getTitle() != null) {
/*  711 */                     title = sel.getTitle();
/*      */                   }
/*      */ 
/*      */                   
/*  715 */                   String upc = "";
/*  716 */                   upc = (sel.getUpc() != null) ? sel.getUpc() : "";
/*      */ 
/*      */                   
/*  719 */                   String radioImpactDate = "";
/*  720 */                   radioImpactDate = MilestoneHelper.getFormatedDate(sel.getImpactDate());
/*      */ 
/*      */                   
/*  723 */                   upc = MilestoneHelper_2.getRMSReportFormat(upc, "UPC", sel.getIsDigital());
/*      */ 
/*      */                   
/*  726 */                   String localProductNumber = "";
/*  727 */                   if (sel.getPrefixID() != null && sel.getPrefixID().getAbbreviation() != null)
/*  728 */                     localProductNumber = sel.getPrefixID().getAbbreviation(); 
/*  729 */                   localProductNumber = String.valueOf(localProductNumber) + sel.getSelectionNo();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  735 */                   String selConfig = "";
/*  736 */                   selConfig = (sel.getSelectionConfig() != null) ? sel.getSelectionConfig().getSelectionConfigurationAbbreviation() : "";
/*      */                   
/*  738 */                   String selSubConfig = "";
/*  739 */                   selSubConfig = (sel.getSelectionSubConfig() != null) ? sel.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation() : "";
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  744 */                   String units = "";
/*  745 */                   units = (sel.getNumberOfUnits() > 0) ? String.valueOf(sel.getNumberOfUnits()) : "";
/*      */                   
/*  747 */                   String code = (sel.getSellCode() != null) ? sel.getSellCode() : "";
/*  748 */                   if (code != null && code.startsWith("-1")) {
/*  749 */                     code = "";
/*      */                   }
/*  751 */                   String retail = "";
/*  752 */                   if (sel.getPriceCode() != null && sel.getPriceCode().getRetailCode() != null) {
/*  753 */                     retail = sel.getPriceCode().getRetailCode();
/*      */                   }
/*  755 */                   if (code.length() > 0) {
/*  756 */                     retail = "/" + retail;
/*      */                   }
/*  758 */                   String price = "";
/*  759 */                   if (sel.getPriceCode() != null && sel.getPriceCode().getTotalCost() > 0.0F) {
/*  760 */                     price = "$" + MilestoneHelper.formatDollarPrice(sel.getPriceCode().getTotalCost());
/*      */                   }
/*      */ 
/*      */ 
/*      */                   
/*  765 */                   String contact = "";
/*  766 */                   contact = (sel.getLabelContact() != null) ? sel.getLabelContact().getName() : "";
/*      */ 
/*      */ 
/*      */                   
/*  770 */                   String otherContact = "";
/*  771 */                   otherContact = (sel.getOtherContact() != null) ? sel.getOtherContact() : "";
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  776 */                   Schedule schedule = sel.getSchedule();
/*      */                   
/*  778 */                   Vector tasks = (schedule != null) ? schedule.getTasks() : null;
/*  779 */                   ScheduledTask task = null;
/*      */                   
/*  781 */                   String FP = "";
/*  782 */                   String BOM = "";
/*  783 */                   String PRQ = "";
/*  784 */                   String MASTERS = "";
/*  785 */                   String FILM = "";
/*  786 */                   String DEPOT = "";
/*  787 */                   String STIC = "";
/*  788 */                   String SFD = "";
/*  789 */                   String MC = "";
/*  790 */                   String DJ = "";
/*      */                   
/*  792 */                   String SPR = "";
/*      */                   
/*  794 */                   String FPcom = "";
/*  795 */                   String BOMcom = "";
/*  796 */                   String PRQcom = "";
/*  797 */                   String MASTERScom = "";
/*  798 */                   String FILMcom = "";
/*  799 */                   String DEPOTcom = "";
/*  800 */                   String STICcom = "";
/*  801 */                   String SFDcom = "";
/*  802 */                   String MCcom = "";
/*  803 */                   String DJcom = "";
/*      */                   
/*  805 */                   String SPRcom = "";
/*      */                   
/*  807 */                   boolean hasSFD = false;
/*      */                   
/*  809 */                   if (tasks != null)
/*      */                   {
/*  811 */                     for (int j = 0; j < tasks.size(); j++) {
/*      */                       
/*  813 */                       task = (ScheduledTask)tasks.get(j);
/*      */                       
/*  815 */                       if (task != null) {
/*      */                         
/*  817 */                         SimpleDateFormat dueDateFormatter = new SimpleDateFormat("M/d");
/*      */                         
/*  819 */                         String dueDate = "";
/*  820 */                         if (task.getDueDate() != null)
/*      */                         {
/*      */                           
/*  823 */                           dueDate = String.valueOf(dueDateFormatter.format(task.getDueDate().getTime())) + 
/*  824 */                             " " + MilestoneHelper.getDayType(sel.getCalendarGroup(), task.getDueDate());
/*      */                         }
/*      */                         
/*  827 */                         String completionDate = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*  828 */                         if (task.getScheduledTaskStatus().equals("N/A"))
/*      */                         {
/*  830 */                           completionDate = task.getScheduledTaskStatus();
/*      */                         }
/*      */                         
/*  833 */                         completionDate = String.valueOf(completionDate) + "\n";
/*      */                         
/*  835 */                         String taskAbbrev = MilestoneHelper.getTaskAbbreivationNameById(task.getTaskAbbreviationID());
/*  836 */                         String taskComment = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/*  842 */                         if (taskAbbrev.equalsIgnoreCase("PRQD")) {
/*      */                           
/*  844 */                           PRQ = dueDate;
/*  845 */                           completionDate = String.valueOf(completionDate) + getTaskMultCompleteDates(task.getScheduledTaskID(), sel.getSelectionID(), multCompleteDates);
/*  846 */                           PRQcom = String.valueOf(completionDate) + taskComment;
/*      */                         
/*      */                         }
/*  849 */                         else if (taskAbbrev.equalsIgnoreCase("READ")) {
/*      */                           
/*  851 */                           STIC = dueDate;
/*  852 */                           STICcom = String.valueOf(completionDate) + taskComment;
/*      */                         }
/*  854 */                         else if (taskAbbrev.equalsIgnoreCase("BMS")) {
/*      */                           
/*  856 */                           BOM = dueDate;
/*  857 */                           BOMcom = String.valueOf(completionDate) + taskComment;
/*      */                         
/*      */                         }
/*  860 */                         else if (taskAbbrev.equalsIgnoreCase("SFD")) {
/*      */                           
/*  862 */                           SFD = dueDate;
/*  863 */                           SFDcom = String.valueOf(completionDate) + taskComment;
/*      */                           
/*  865 */                           hasSFD = true;
/*      */                         
/*      */                         }
/*  868 */                         else if (taskAbbrev.equalsIgnoreCase("PFS")) {
/*      */                           
/*  870 */                           FP = dueDate;
/*  871 */                           completionDate = String.valueOf(completionDate) + getTaskMultCompleteDates(task.getScheduledTaskID(), sel.getSelectionID(), multCompleteDates);
/*  872 */                           FPcom = String.valueOf(completionDate) + taskComment;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/*      */                         }
/*  883 */                         else if (taskAbbrev.equalsIgnoreCase("TPS")) {
/*      */                           
/*  885 */                           MASTERS = dueDate;
/*  886 */                           completionDate = String.valueOf(completionDate) + getTaskMultCompleteDates(task.getScheduledTaskID(), sel.getSelectionID(), multCompleteDates);
/*  887 */                           MASTERScom = String.valueOf(completionDate) + taskComment;
/*      */                         
/*      */                         }
/*  890 */                         else if (taskAbbrev.equalsIgnoreCase("TA")) {
/*      */                           
/*  892 */                           FILM = dueDate;
/*  893 */                           FILMcom = String.valueOf(completionDate) + taskComment;
/*      */                         
/*      */                         }
/*  896 */                         else if (taskAbbrev.equalsIgnoreCase("PSD")) {
/*      */                           
/*  898 */                           DEPOT = dueDate;
/*  899 */                           DEPOTcom = String.valueOf(completionDate) + taskComment;
/*      */                         
/*      */                         }
/*  902 */                         else if (taskAbbrev.equalsIgnoreCase("MCS")) {
/*      */                           
/*  904 */                           DJ = dueDate;
/*  905 */                           completionDate = String.valueOf(completionDate) + getTaskMultCompleteDates(task.getScheduledTaskID(), sel.getSelectionID(), multCompleteDates);
/*  906 */                           DJcom = String.valueOf(completionDate) + taskComment;
/*      */                         
/*      */                         }
/*  909 */                         else if (taskAbbrev.equalsIgnoreCase("SPR")) {
/*      */                           
/*  911 */                           SPR = dueDate;
/*  912 */                           completionDate = String.valueOf(completionDate) + getTaskMultCompleteDates(task.getScheduledTaskID(), sel.getSelectionID(), multCompleteDates);
/*  913 */                           SPRcom = String.valueOf(completionDate) + taskComment;
/*      */                         } 
/*      */                         
/*  916 */                         task = null;
/*      */                       } 
/*      */                     } 
/*      */                   }
/*      */ 
/*      */ 
/*      */                   
/*  923 */                   nextRow = 0;
/*  924 */                   subTable = new DefaultTableLens(subTableRows, 15);
/*      */ 
/*      */                   
/*  927 */                   subTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
/*      */                   
/*  929 */                   setColBorderColor(subTable, nextRow, 15, SHADED_AREA_COLOR);
/*      */                   
/*  931 */                   subTable.setHeaderRowCount(0);
/*  932 */                   subTable.setColWidth(0, 77);
/*  933 */                   subTable.setColWidth(1, 259);
/*  934 */                   subTable.setColWidth(2, 157);
/*  935 */                   subTable.setColWidth(3, 150);
/*  936 */                   subTable.setColWidth(4, 80);
/*  937 */                   subTable.setColWidth(5, 168);
/*  938 */                   subTable.setColWidth(6, 87);
/*  939 */                   subTable.setColWidth(7, 84);
/*  940 */                   subTable.setColWidth(8, 70);
/*  941 */                   subTable.setColWidth(9, 80);
/*      */                   
/*  943 */                   subTable.setColWidth(10, 90);
/*      */                   
/*  945 */                   subTable.setColWidth(11, 72);
/*  946 */                   subTable.setColWidth(12, 90);
/*  947 */                   subTable.setColWidth(13, 70);
/*  948 */                   subTable.setColWidth(14, 90);
/*      */ 
/*      */                   
/*  951 */                   subTable.setRowBorderColor(nextRow - 1, Color.lightGray);
/*      */ 
/*      */                   
/*  954 */                   subTable.setObject(nextRow, 0, String.valueOf(dateNameText) + "\n" + cycle.trim());
/*  955 */                   subTable.setBackground(nextRow, 0, Color.white);
/*  956 */                   subTable.setSpan(nextRow, 0, new Dimension(1, 2));
/*  957 */                   subTable.setRowAutoSize(true);
/*  958 */                   subTable.setAlignment(nextRow, 0, 9);
/*  959 */                   subTable.setFont(nextRow, 0, new Font("Arial", 1, 7));
/*      */ 
/*      */                   
/*  962 */                   subTable.setObject(nextRow, 1, String.valueOf(artist) + "\n" + title);
/*  963 */                   subTable.setBackground(nextRow, 1, Color.white);
/*  964 */                   subTable.setSpan(nextRow, 1, new Dimension(1, 2));
/*  965 */                   subTable.setRowAutoSize(true);
/*  966 */                   subTable.setAlignment(nextRow, 1, 9);
/*  967 */                   subTable.setFont(nextRow, 1, new Font("Arial", 1, 7));
/*      */ 
/*      */ 
/*      */                   
/*  971 */                   subTable.setSpan(nextRow, 2, new Dimension(1, 2));
/*  972 */                   subTable.setObject(nextRow, 2, pack);
/*  973 */                   subTable.setAlignment(nextRow, 2, 10);
/*  974 */                   subTable.setBackground(nextRow, 2, Color.white);
/*      */ 
/*      */                   
/*  977 */                   String[] checkStrings = { comment, artist, title, pack, (new String[7][4] = label).valueOf(contact) + "/n" + otherContact, price };
/*  978 */                   int[] checkStringsLength = { 20, 30, 30, 20, 25, 25, 15 };
/*      */                   
/*  980 */                   int extraLines = MilestoneHelper.lineCountWCR(checkStrings, checkStringsLength);
/*      */                   
/*  982 */                   String[] commentString = { comment };
/*  983 */                   int[] checkCommentLength = { 30 };
/*  984 */                   int commentCounter = MilestoneHelper.lineCountWCR(commentString, checkCommentLength);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  997 */                   boolean otherContactExists = false;
/*      */                   
/*  999 */                   if (!otherContact.equals("") || commentCounter > 1) {
/* 1000 */                     otherContactExists = true;
/*      */                   }
/*      */ 
/*      */                   
/* 1004 */                   extraLines = (extraLines <= 2) ? 0 : (extraLines - 2);
/* 1005 */                   for (int z = 0; z < extraLines; z++)
/*      */                   {
/* 1007 */                     otherContact = String.valueOf(otherContact) + "\n";
/*      */                   }
/*      */                   
/* 1010 */                   subTable.setObject(nextRow, 3, String.valueOf(localProductNumber) + "\n" + upc + "\n" + selSubConfig + "\n" + radioImpactDate);
/* 1011 */                   subTable.setAlignment(nextRow, 3, 10);
/* 1012 */                   subTable.setBackground(nextRow, 3, Color.white);
/* 1013 */                   subTable.setSpan(nextRow, 3, new Dimension(1, 2));
/*      */                   
/* 1015 */                   subTable.setObject(nextRow, 4, String.valueOf(price) + "\n" + code + retail + "\n" + units);
/* 1016 */                   subTable.setAlignment(nextRow, 4, 10);
/* 1017 */                   subTable.setBackground(nextRow, 4, Color.white);
/* 1018 */                   subTable.setSpan(nextRow, 4, new Dimension(1, 2));
/* 1019 */                   subTable.setAlignment(nextRow + 1, 4, 10);
/*      */                   
/* 1021 */                   subTable.setObject(nextRow, 5, "Due Dates");
/* 1022 */                   subTable.setColBorderColor(nextRow, 5, SHADED_AREA_COLOR);
/* 1023 */                   subTable.setColBorder(nextRow, 5, 266240);
/* 1024 */                   subTable.setFont(nextRow, 5, new Font("Arial", 1, 8));
/* 1025 */                   subTable.setRowBorderColor(nextRow, SHADED_AREA_COLOR);
/*      */                   
/* 1027 */                   subTable.setAlignment(nextRow, 6, 10);
/* 1028 */                   subTable.setAlignment(nextRow + 1, 6, 10);
/*      */ 
/*      */                   
/* 1031 */                   if (otherContactExists) {
/* 1032 */                     subTable.setObject(nextRow + 1, 5, String.valueOf(label) + "\n" + contact + "\n" + otherContact);
/*      */                   } else {
/* 1034 */                     subTable.setObject(nextRow + 1, 5, String.valueOf(label) + "\n" + contact);
/*      */                   } 
/*      */ 
/*      */ 
/*      */                   
/* 1039 */                   subTable.setRowAutoSize(true);
/*      */                   
/* 1041 */                   subTable.setRowHeight(nextRow, 14);
/*      */                   
/* 1043 */                   subTable.setRowBorderColor(nextRow + 1, SHADED_AREA_COLOR);
/*      */                   
/* 1045 */                   subTable.setObject(nextRow, 6, PRQ);
/* 1046 */                   subTable.setColBorderColor(nextRow, 6, SHADED_AREA_COLOR);
/* 1047 */                   subTable.setColBorder(nextRow, 6, 266240);
/*      */                   
/* 1049 */                   subTable.setObject(nextRow, 7, SFD);
/* 1050 */                   subTable.setColBorderColor(nextRow, 7, SHADED_AREA_COLOR);
/* 1051 */                   subTable.setColBorder(nextRow, 7, 266240);
/*      */                   
/* 1053 */                   subTable.setObject(nextRow, 8, STIC);
/* 1054 */                   subTable.setColBorderColor(nextRow, 8, SHADED_AREA_COLOR);
/* 1055 */                   subTable.setColBorder(nextRow, 8, 266240);
/*      */                   
/* 1057 */                   subTable.setObject(nextRow, 9, BOM);
/* 1058 */                   subTable.setColBorderColor(nextRow, 9, SHADED_AREA_COLOR);
/* 1059 */                   subTable.setColBorder(nextRow, 9, 266240);
/*      */                   
/* 1061 */                   subTable.setObject(nextRow, 10, FP);
/* 1062 */                   subTable.setColBorderColor(nextRow, 10, SHADED_AREA_COLOR);
/* 1063 */                   subTable.setColBorder(nextRow, 10, 266240);
/*      */ 
/*      */                   
/* 1066 */                   subTable.setObject(nextRow, 11, SPR);
/* 1067 */                   subTable.setColBorderColor(nextRow, 11, SHADED_AREA_COLOR);
/* 1068 */                   subTable.setColBorder(nextRow, 11, 266240);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 1076 */                   subTable.setObject(nextRow, 12, MASTERS);
/* 1077 */                   subTable.setColBorderColor(nextRow, 12, Color.lightGray);
/* 1078 */                   subTable.setColBorder(nextRow, 12, 266240);
/*      */                   
/* 1080 */                   subTable.setObject(nextRow, 13, DJ);
/* 1081 */                   subTable.setColBorderColor(nextRow, 13, Color.lightGray);
/* 1082 */                   subTable.setColBorder(nextRow, 13, 266240);
/*      */                   
/* 1084 */                   subTable.setObject(nextRow, 14, FILM);
/* 1085 */                   subTable.setColBorderColor(nextRow, 14, SHADED_AREA_COLOR);
/* 1086 */                   subTable.setColBorder(nextRow, 14, 266240);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 1092 */                   subTable.setFont(nextRow, 5, new Font("Arial", 1, 7));
/* 1093 */                   subTable.setFont(nextRow, 6, new Font("Arial", 1, 7));
/* 1094 */                   subTable.setFont(nextRow, 7, new Font("Arial", 1, 7));
/* 1095 */                   subTable.setFont(nextRow, 8, new Font("Arial", 1, 7));
/* 1096 */                   subTable.setFont(nextRow, 9, new Font("Arial", 1, 7));
/* 1097 */                   subTable.setFont(nextRow, 10, new Font("Arial", 1, 7));
/* 1098 */                   subTable.setFont(nextRow, 11, new Font("Arial", 1, 7));
/* 1099 */                   subTable.setFont(nextRow, 12, new Font("Arial", 1, 7));
/* 1100 */                   subTable.setFont(nextRow, 13, new Font("Arial", 1, 7));
/* 1101 */                   subTable.setFont(nextRow, 14, new Font("Arial", 1, 7));
/*      */ 
/*      */ 
/*      */                   
/* 1105 */                   Font holidayFont = new Font("Arial", 3, 7);
/* 1106 */                   for (int colIdx = 6; colIdx <= 14; colIdx++) {
/*      */                     
/* 1108 */                     String dueDate = subTable.getObject(nextRow, colIdx).toString();
/* 1109 */                     if (dueDate != null && dueDate.length() > 0) {
/* 1110 */                       char lastChar = dueDate.charAt(dueDate.length() - 1);
/* 1111 */                       if (Character.isLetter(lastChar)) {
/* 1112 */                         subTable.setFont(nextRow, colIdx, holidayFont);
/*      */                       }
/*      */                     } 
/*      */                   } 
/*      */ 
/*      */                   
/* 1118 */                   subTable.setAlignment(nextRow, 5, 2);
/* 1119 */                   subTable.setAlignment(nextRow, 6, 2);
/* 1120 */                   subTable.setAlignment(nextRow, 7, 2);
/* 1121 */                   subTable.setAlignment(nextRow, 8, 2);
/* 1122 */                   subTable.setAlignment(nextRow, 9, 2);
/* 1123 */                   subTable.setAlignment(nextRow, 10, 2);
/* 1124 */                   subTable.setAlignment(nextRow, 11, 2);
/* 1125 */                   subTable.setAlignment(nextRow, 12, 2);
/* 1126 */                   subTable.setAlignment(nextRow, 13, 2);
/* 1127 */                   subTable.setAlignment(nextRow, 14, 2);
/*      */                   
/* 1129 */                   subTable.setObject(nextRow + 1, 6, PRQcom);
/*      */                   
/* 1131 */                   if (!hasSFD)
/*      */                   {
/* 1133 */                     if (sel.getTemplateId() == 98 || 
/* 1134 */                       configHeaderText.startsWith("Commercial Single") || 
/* 1135 */                       configHeaderText.startsWith("Promos"))
/*      */                     {
/* 1137 */                       SFDcom = "N/A";
/*      */                     }
/*      */                   }
/*      */ 
/*      */                   
/* 1142 */                   subTable.setObject(nextRow + 1, 7, SFDcom);
/* 1143 */                   subTable.setObject(nextRow + 1, 8, STICcom);
/*      */                   
/* 1145 */                   subTable.setObject(nextRow + 1, 9, BOMcom);
/*      */                   
/* 1147 */                   subTable.setObject(nextRow + 1, 10, FPcom);
/*      */                   
/* 1149 */                   subTable.setObject(nextRow + 1, 11, SPRcom);
/*      */ 
/*      */                   
/* 1152 */                   subTable.setObject(nextRow + 1, 12, MASTERScom);
/* 1153 */                   subTable.setObject(nextRow + 1, 13, DJcom);
/* 1154 */                   subTable.setObject(nextRow + 1, 14, FILMcom);
/*      */ 
/*      */                   
/* 1157 */                   setColBorderColor(subTable, nextRow + 1, 15, SHADED_AREA_COLOR);
/*      */                   
/* 1159 */                   subTable.setAlignment(nextRow + 1, 5, 10);
/* 1160 */                   subTable.setAlignment(nextRow + 1, 6, 10);
/* 1161 */                   subTable.setAlignment(nextRow + 1, 7, 10);
/* 1162 */                   subTable.setAlignment(nextRow + 1, 8, 10);
/* 1163 */                   subTable.setAlignment(nextRow + 1, 9, 10);
/* 1164 */                   subTable.setAlignment(nextRow + 1, 10, 10);
/* 1165 */                   subTable.setAlignment(nextRow + 1, 11, 10);
/* 1166 */                   subTable.setAlignment(nextRow + 1, 12, 10);
/* 1167 */                   subTable.setAlignment(nextRow + 1, 13, 10);
/* 1168 */                   subTable.setAlignment(nextRow + 1, 14, 10);
/*      */                   
/* 1170 */                   subTable.setRowFont(nextRow + 1, new Font("Arial", 0, 7));
/*      */                   
/* 1172 */                   subTable.setRowBackground(nextRow, SHADED_AREA_COLOR);
/* 1173 */                   subTable.setRowForeground(nextRow, Color.black);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 1182 */                   subTable.setRowFont(nextRow, new Font("Arial", 0, 7));
/*      */ 
/*      */                   
/* 1185 */                   if (newComment.length() > 0) {
/* 1186 */                     nextRow++;
/*      */ 
/*      */                     
/* 1189 */                     subTable.setRowAutoSize(true);
/* 1190 */                     subTable.setRowBorderColor(nextRow + 1, Color.lightGray);
/* 1191 */                     setColBorderColor(subTable, nextRow + 1, 15, SHADED_AREA_COLOR);
/* 1192 */                     subTable.setRowFont(nextRow + 1, new Font("Arial", 3, 7));
/*      */                     
/* 1194 */                     subTable.setSpan(nextRow + 1, 0, new Dimension(1, 1));
/*      */                     
/* 1196 */                     subTable.setSpan(nextRow + 1, 1, new Dimension(1, 1));
/* 1197 */                     subTable.setAlignment(nextRow + 1, 1, 12);
/* 1198 */                     subTable.setObject(nextRow + 1, 1, "Comments:   ");
/*      */                     
/* 1200 */                     subTable.setObject(nextRow + 1, 2, newComment);
/* 1201 */                     subTable.setSpan(nextRow + 1, 2, new Dimension(13, 1));
/*      */                     
/* 1203 */                     subTable.setRowBorderColor(nextRow + 1, Color.lightGray);
/* 1204 */                     setColBorderColor(subTable, nextRow + 1, 15, SHADED_AREA_COLOR);
/* 1205 */                     subTable.setRowFont(nextRow + 1, new Font("Arial", 3, 7));
/* 1206 */                     subTable.setAlignment(nextRow + 1, 2, 9);
/* 1207 */                     subTable.setColLineWrap(2, true);
/*      */                     
/* 1209 */                     subTable.setColBorderColor(nextRow + 1, 0, Color.white);
/* 1210 */                     subTable.setColBorderColor(nextRow + 1, 1, Color.white);
/* 1211 */                     subTable.setColBorderColor(nextRow + 1, 2, Color.white);
/* 1212 */                     subTable.setColBorderColor(nextRow + 1, 3, Color.white);
/* 1213 */                     subTable.setColBorderColor(nextRow + 1, 4, Color.white);
/* 1214 */                     subTable.setColBorderColor(nextRow + 1, 5, Color.white);
/* 1215 */                     subTable.setColBorderColor(nextRow + 1, 6, Color.white);
/* 1216 */                     subTable.setColBorderColor(nextRow + 1, 7, Color.white);
/* 1217 */                     subTable.setColBorderColor(nextRow + 1, 8, Color.white);
/* 1218 */                     subTable.setColBorderColor(nextRow + 1, 9, Color.white);
/* 1219 */                     subTable.setColBorderColor(nextRow + 1, 10, Color.white);
/* 1220 */                     subTable.setColBorderColor(nextRow + 1, 11, Color.white);
/* 1221 */                     subTable.setColBorderColor(nextRow + 1, 12, Color.white);
/* 1222 */                     subTable.setColBorderColor(nextRow + 1, 13, Color.white);
/*      */                   } 
/*      */ 
/*      */                   
/* 1226 */                   body = new SectionBand(report);
/*      */                   
/* 1228 */                   double lfLineCount = 1.5D;
/*      */ 
/*      */ 
/*      */                   
/* 1232 */                   if (extraLines > 3 || 
/* 1233 */                     PRQcom.length() > 10 || STICcom.length() > 10 || 
/* 1234 */                     BOMcom.length() > 10 || FPcom.length() > 10 || 
/* 1235 */                     MCcom.length() > 10 || MASTERScom.length() > 10 || 
/* 1236 */                     DJcom.length() > 10 || FILMcom.length() > 10 || 
/* 1237 */                     DEPOTcom.length() > 10) {
/*      */ 
/*      */                     
/* 1240 */                     if (lfLineCount < extraLines * 0.3D) {
/* 1241 */                       lfLineCount = extraLines * 0.3D;
/*      */                     }
/* 1243 */                     if (lfLineCount < (PRQcom.length() / 7) * 0.3D) {
/* 1244 */                       lfLineCount = (PRQcom.length() / 7) * 0.3D;
/*      */                     }
/* 1246 */                     if (lfLineCount < (STICcom.length() / 8) * 0.3D) {
/* 1247 */                       lfLineCount = (STICcom.length() / 8) * 0.3D;
/*      */                     }
/* 1249 */                     if (lfLineCount < (BOMcom.length() / 8) * 0.3D) {
/* 1250 */                       lfLineCount = (BOMcom.length() / 8) * 0.3D;
/*      */                     }
/* 1252 */                     if (lfLineCount < (FPcom.length() / 8) * 0.3D) {
/* 1253 */                       lfLineCount = (FPcom.length() / 8) * 0.3D;
/*      */                     }
/* 1255 */                     if (lfLineCount < (MCcom.length() / 8) * 0.3D) {
/* 1256 */                       lfLineCount = (MCcom.length() / 8) * 0.3D;
/*      */                     }
/* 1258 */                     if (lfLineCount < (MASTERScom.length() / 8) * 0.3D) {
/* 1259 */                       lfLineCount = (MASTERScom.length() / 8) * 0.3D;
/*      */                     }
/* 1261 */                     if (lfLineCount < (DJcom.length() / 8) * 0.3D) {
/* 1262 */                       lfLineCount = (DJcom.length() / 8) * 0.3D;
/*      */                     }
/* 1264 */                     if (lfLineCount < (FILMcom.length() / 8) * 0.3D) {
/* 1265 */                       lfLineCount = (FILMcom.length() / 8) * 0.3D;
/*      */                     }
/* 1267 */                     if (lfLineCount < (DEPOTcom.length() / 8) * 0.3D) {
/* 1268 */                       lfLineCount = (DEPOTcom.length() / 8) * 0.3D;
/*      */                     }
/* 1270 */                     body.setHeight((float)lfLineCount);
/*      */                   
/*      */                   }
/*      */                   else {
/*      */                     
/* 1275 */                     body.setHeight(1.5F);
/*      */                   } 
/*      */                   
/* 1278 */                   body.addTable(subTable, new Rectangle(800, 800));
/* 1279 */                   body.setBottomBorder(0);
/* 1280 */                   body.setTopBorder(0);
/* 1281 */                   body.setShrinkToFit(true);
/* 1282 */                   body.setVisible(true);
/* 1283 */                   group = new DefaultSectionLens(null, group, body);
/*      */                 } 
/*      */                 
/* 1286 */                 group = new DefaultSectionLens(null, group, spacer);
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1296 */         group = new DefaultSectionLens(hbandType, group, null);
/* 1297 */         report.addSection(group, rowCountTable);
/* 1298 */         report.addPageBreak();
/* 1299 */         group = null;
/*      */ 
/*      */       
/*      */       }
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 1307 */     catch (Exception e) {
/*      */       
/* 1309 */       System.out.println(">>>>>>>>IdjProductionScheduleForPrintSubHandler(): exception: " + e);
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void setColBorderColor(DefaultTableLens table, int row, int columns, Color color) {
/* 1327 */     for (int i = -1; i < columns; i++)
/*      */     {
/* 1329 */       table.setColBorderColor(row, i, color);
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
/* 1343 */     int COL_LINE_STYLE = 4097;
/* 1344 */     int HEADER_FONT_SIZE = 12;
/*      */     
/* 1346 */     table_contents.setObject(nextRow, 0, "");
/* 1347 */     table_contents.setSpan(nextRow, 0, new Dimension(cols, 1));
/* 1348 */     table_contents.setRowHeight(nextRow, 1);
/* 1349 */     table_contents.setRowBackground(nextRow, Color.white);
/*      */     
/* 1351 */     table_contents.setColBorderColor(nextRow, -1, Color.black);
/* 1352 */     table_contents.setColBorder(nextRow, -1, 4097);
/* 1353 */     table_contents.setColBorderColor(nextRow, cols - 1, Color.black);
/* 1354 */     table_contents.setColBorder(nextRow, cols - 1, 4097);
/*      */     
/* 1356 */     table_contents.setRowBorderColor(nextRow, Color.white);
/* 1357 */     table_contents.setRowBorder(nextRow, 266240);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1362 */     table_contents.setColBorderColor(nextRow + 1, -1, Color.black);
/* 1363 */     table_contents.setColBorder(nextRow + 1, -1, 4097);
/* 1364 */     table_contents.setColBorderColor(nextRow + 1, cols - 1, Color.black);
/* 1365 */     table_contents.setColBorder(nextRow + 1, cols - 1, 4097);
/*      */ 
/*      */     
/* 1368 */     table_contents.setAlignment(nextRow + 1, 0, 2);
/* 1369 */     table_contents.setSpan(nextRow + 1, 0, new Dimension(cols, 1));
/* 1370 */     table_contents.setObject(nextRow + 1, 0, title);
/* 1371 */     table_contents.setRowFont(nextRow + 1, new Font("Arial", 3, 12));
/*      */     
/* 1373 */     table_contents.setColBorderColor(nextRow + 1, -1, Color.black);
/* 1374 */     table_contents.setColBorder(nextRow + 1, -1, 4097);
/*      */     
/* 1376 */     nextRow += 2;
/*      */     
/* 1378 */     table_contents.setObject(nextRow, 0, "");
/* 1379 */     table_contents.setSpan(nextRow, 0, new Dimension(cols, 1));
/* 1380 */     table_contents.setRowHeight(nextRow, 1);
/* 1381 */     table_contents.setRowBackground(nextRow, Color.white);
/*      */ 
/*      */     
/* 1384 */     table_contents.setColBorderColor(nextRow, -1, Color.black);
/* 1385 */     table_contents.setColBorder(nextRow, -1, 4097);
/* 1386 */     table_contents.setColBorderColor(nextRow, cols, Color.black);
/* 1387 */     table_contents.setColBorder(nextRow, cols, 4097);
/*      */     
/* 1389 */     table_contents.setRowBorderColor(nextRow, Color.white);
/* 1390 */     table_contents.setRowBorder(nextRow, 266240);
/*      */     
/* 1392 */     nextRow++;
/*      */ 
/*      */     
/* 1395 */     table_contents.setObject(nextRow, 0, "");
/* 1396 */     table_contents.setSpan(nextRow, 0, new Dimension(cols, 1));
/* 1397 */     table_contents.setRowHeight(nextRow, 1);
/* 1398 */     table_contents.setRowBackground(nextRow, Color.white);
/*      */ 
/*      */     
/* 1401 */     table_contents.setColBorderColor(nextRow, -1, Color.white);
/* 1402 */     table_contents.setColBorder(nextRow, -1, 4097);
/* 1403 */     table_contents.setColBorderColor(nextRow, cols - 1, Color.white);
/* 1404 */     table_contents.setColBorder(nextRow, cols, 4097);
/*      */     
/* 1406 */     table_contents.setRowBorderColor(nextRow, Color.white);
/* 1407 */     table_contents.setRowBorder(nextRow, 266240);
/*      */     
/* 1409 */     nextRow++;
/*      */     
/* 1411 */     return nextRow;
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
/* 1426 */   public static String removeLF(String theString, int maxChars) { return theString.replace('\n', ' '); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String getTaskMultCompleteDates(int taskID, int selectionID, Vector multCompleteDates) {
/* 1438 */     String completionDateList = "";
/* 1439 */     if (multCompleteDates != null) {
/*      */ 
/*      */       
/* 1442 */       int mcdCt = (multCompleteDates == null) ? 0 : multCompleteDates.size();
/*      */ 
/*      */       
/* 1445 */       boolean relTaskFound = false;
/* 1446 */       int i = 0;
/* 1447 */       while (!relTaskFound && i < mcdCt) {
/*      */         
/* 1449 */         MultCompleteDate mcd = (MultCompleteDate)multCompleteDates.get(i);
/* 1450 */         if (mcd.getReleaseID() == selectionID && mcd.getTaskID() == taskID) {
/* 1451 */           relTaskFound = true;
/*      */           continue;
/*      */         } 
/* 1454 */         i++;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1460 */       if (relTaskFound) {
/* 1461 */         boolean relTaskDone = false;
/* 1462 */         while (!relTaskDone && i < mcdCt) {
/* 1463 */           MultCompleteDate mcd = (MultCompleteDate)multCompleteDates.get(i);
/* 1464 */           if (mcd.getReleaseID() == selectionID && mcd.getTaskID() == taskID) {
/* 1465 */             completionDateList = String.valueOf(completionDateList) + MilestoneHelper.getFormatedDate(mcd.getCompletionDate()) + "\n";
/*      */           } else {
/*      */             
/* 1468 */             relTaskDone = true;
/*      */           } 
/* 1470 */           i++;
/*      */         } 
/*      */       } 
/*      */     } 
/* 1474 */     return completionDateList;
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
/*      */   private static Vector getRptMultCompleteDates(Vector selections) {
/* 1489 */     Vector multCompleteDates = null;
/*      */     
/* 1491 */     if (selections != null) {
/*      */       
/* 1493 */       multCompleteDates = new Vector();
/* 1494 */       StringBuffer sql = new StringBuffer();
/* 1495 */       Iterator it = selections.iterator();
/*      */       
/* 1497 */       sql.append("select * from MultCompleteDates with (nolock) where release_id in (");
/* 1498 */       while (it.hasNext()) {
/*      */         
/* 1500 */         sql.append(((Selection)it.next()).getSelectionID());
/* 1501 */         sql.append(", ");
/*      */       } 
/*      */ 
/*      */       
/* 1505 */       String query = String.valueOf(sql.substring(0, sql.length() - 2)) + ") order by release_id asc, task_id asc, order_no desc";
/*      */       
/* 1507 */       JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 1508 */       if (connector != null) {
/*      */         
/* 1510 */         connector.setForwardOnly(false);
/* 1511 */         connector.runQuery();
/* 1512 */         SimpleDateFormat adf = new SimpleDateFormat("M/d/yy");
/* 1513 */         while (connector.more()) {
/*      */           
/* 1515 */           MultCompleteDate mcd = new MultCompleteDate();
/* 1516 */           mcd.setReleaseID(connector.getInt("release_id"));
/* 1517 */           mcd.setTaskID(connector.getInt("task_id"));
/* 1518 */           mcd.setOrderNo(connector.getInt("order_no"));
/* 1519 */           mcd.setCompletionDate(MilestoneHelper.getDate(adf.format(connector.getDate("completion_date"))));
/* 1520 */           multCompleteDates.addElement(mcd);
/* 1521 */           connector.next();
/*      */         } 
/*      */         
/* 1524 */         connector.close();
/*      */       } 
/*      */     } 
/* 1527 */     return multCompleteDates;
/*      */   }
/*      */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\IdjProductionScheduleForPrintSubHandlerNew2091.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */