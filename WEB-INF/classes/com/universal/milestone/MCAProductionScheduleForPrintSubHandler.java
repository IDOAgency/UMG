/*      */ package WEB-INF.classes.com.universal.milestone;
/*      */ 
/*      */ import com.techempower.ComponentLog;
/*      */ import com.techempower.gemini.Context;
/*      */ import com.techempower.gemini.GeminiApplication;
/*      */ import com.universal.milestone.DatePeriod;
/*      */ import com.universal.milestone.Form;
/*      */ import com.universal.milestone.MCAProductionScheduleForPrintSubHandler;
/*      */ import com.universal.milestone.MilestoneHelper;
/*      */ import com.universal.milestone.MilestoneHelper_2;
/*      */ import com.universal.milestone.MonthYearComparator;
/*      */ import com.universal.milestone.Schedule;
/*      */ import com.universal.milestone.ScheduledTask;
/*      */ import com.universal.milestone.SecureHandler;
/*      */ import com.universal.milestone.Selection;
/*      */ import com.universal.milestone.SelectionManager;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class MCAProductionScheduleForPrintSubHandler
/*      */   extends SecureHandler
/*      */ {
/*      */   public static final String COMPONENT_CODE = "hMCAProd";
/*      */   public GeminiApplication application;
/*      */   public ComponentLog log;
/*      */   
/*      */   public void MCAProdcutionScheduleForPrintSubHandler(GeminiApplication application) {
/*   92 */     this.application = application;
/*   93 */     this.log = application.getLog("hMCAProd");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  101 */   public String getDescription() { return "Sub Report"; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static void fillMCAProductionScheduleForPrint(XStyleSheet report, Context context) {
/*  113 */     int COL_LINE_STYLE = 4097;
/*  114 */     int HEADER_FONT_SIZE = 12;
/*      */     
/*  116 */     double ldLineVal = 0.3D;
/*      */     
/*      */     try {
/*  119 */       HttpServletResponse sresponse = context.getResponse();
/*  120 */       context.putDelivery("status", new String("start_gathering"));
/*  121 */       context.includeJSP("status.jsp", "hiddenFrame");
/*  122 */       sresponse.setContentType("text/plain");
/*  123 */       sresponse.flushBuffer();
/*      */     }
/*  125 */     catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  130 */     Vector selections = MilestoneHelper.getSelectionsForReport(context);
/*      */     
/*      */     try {
/*  133 */       HttpServletResponse sresponse = context.getResponse();
/*  134 */       context.putDelivery("status", new String("start_report"));
/*  135 */       context.putDelivery("percent", new String("10"));
/*  136 */       context.includeJSP("status.jsp", "hiddenFrame");
/*  137 */       sresponse.setContentType("text/plain");
/*  138 */       sresponse.flushBuffer();
/*      */     }
/*  140 */     catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  145 */     int DATA_FONT_SIZE = 7;
/*  146 */     int SMALL_HEADER_FONT_SIZE = 8;
/*  147 */     int NUM_COLUMNS = 12;
/*  148 */     Color SHADED_AREA_COLOR = Color.lightGray;
/*      */ 
/*      */     
/*  151 */     SectionBand hbandType = new SectionBand(report);
/*  152 */     SectionBand hbandCategory = new SectionBand(report);
/*  153 */     SectionBand hbandDate = new SectionBand(report);
/*  154 */     SectionBand hbandRelWeek = new SectionBand(report);
/*  155 */     SectionBand body = new SectionBand(report);
/*  156 */     SectionBand footer = new SectionBand(report);
/*  157 */     SectionBand spacer = new SectionBand(report);
/*  158 */     DefaultSectionLens group = null;
/*      */     
/*  160 */     footer.setVisible(true);
/*  161 */     footer.setHeight(0.1F);
/*  162 */     footer.setShrinkToFit(false);
/*  163 */     footer.setBottomBorder(0);
/*      */     
/*  165 */     spacer.setVisible(true);
/*  166 */     spacer.setHeight(0.05F);
/*  167 */     spacer.setShrinkToFit(false);
/*  168 */     spacer.setBottomBorder(0);
/*      */ 
/*      */     
/*  171 */     Hashtable selTable = MilestoneHelper.groupSelectionsForMcaProductionByTypeConfigAndStreetDate(selections);
/*  172 */     Enumeration configs = selTable.keys();
/*  173 */     Vector configVector = new Vector();
/*      */     
/*  175 */     while (configs.hasMoreElements()) {
/*  176 */       configVector.addElement(configs.nextElement());
/*      */     }
/*  178 */     int numConfigs = configVector.size();
/*      */ 
/*      */     
/*      */     try {
/*  182 */       Collections.sort(configVector);
/*  183 */       Vector sortedConfigVector = MilestoneHelper.sortStrings(configVector);
/*      */ 
/*      */       
/*  186 */       DefaultTableLens table_contents = null;
/*  187 */       DefaultTableLens rowCountTable = null;
/*  188 */       DefaultTableLens columnHeaderTable = null;
/*  189 */       DefaultTableLens subTable = null;
/*  190 */       DefaultTableLens monthTableLens = null;
/*  191 */       DefaultTableLens dateTableLens = null;
/*  192 */       DefaultTableLens relWeekLens = null;
/*      */       
/*  194 */       rowCountTable = new DefaultTableLens(2, 10000);
/*      */ 
/*      */       
/*  197 */       int totalCount = 0;
/*  198 */       int tenth = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  204 */       for (int n = 0; n < sortedConfigVector.size(); n++) {
/*      */         
/*  206 */         String configC = (configVector.elementAt(n) != null) ? (String)configVector.elementAt(n) : "";
/*  207 */         Hashtable monthTableC = (Hashtable)selTable.get(configC);
/*      */         
/*  209 */         totalCount++;
/*  210 */         Enumeration monthsC = monthTableC.keys();
/*      */         
/*  212 */         Vector monthVectorC = new Vector();
/*      */         
/*  214 */         while (monthsC.hasMoreElements()) {
/*  215 */           monthVectorC.add((String)monthsC.nextElement());
/*  216 */           Object[] monthArrayC = null;
/*  217 */           monthArrayC = monthVectorC.toArray();
/*  218 */           totalCount += monthArrayC.length;
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  223 */       tenth = (totalCount > 10) ? (totalCount / 10) : 1;
/*      */       
/*  225 */       HttpServletResponse sresponse = context.getResponse();
/*  226 */       context.putDelivery("status", new String("start_report"));
/*  227 */       context.putDelivery("percent", new String("20"));
/*  228 */       context.includeJSP("status.jsp", "hiddenFrame");
/*  229 */       sresponse.setContentType("text/plain");
/*  230 */       sresponse.flushBuffer();
/*      */       
/*  232 */       int recordCount = 0;
/*  233 */       int count = 0;
/*      */       
/*  235 */       for (int n = 0; n < sortedConfigVector.size(); n++)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  241 */         Form reportForm = (Form)context.getSessionValue("reportForm");
/*      */         
/*  243 */         Calendar beginStDate = (reportForm.getStringValue("beginDate") != null && 
/*  244 */           reportForm.getStringValue("beginDate").length() > 0) ? 
/*  245 */           MilestoneHelper.getDate(reportForm.getStringValue("beginDate")) : null;
/*      */         
/*  247 */         Calendar endStDate = (reportForm.getStringValue("endDate") != null && 
/*  248 */           reportForm.getStringValue("endDate").length() > 0) ? 
/*  249 */           MilestoneHelper.getDate(reportForm.getStringValue("endDate")) : null;
/*      */         
/*  251 */         report.setElement("crs_startdate", MilestoneHelper.getFormatedDate(beginStDate));
/*  252 */         report.setElement("crs_enddate", MilestoneHelper.getFormatedDate(endStDate));
/*      */         
/*  254 */         SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy, h:mm a");
/*  255 */         String todayLong = formatter.format(new Date());
/*  256 */         report.setElement("crs_bottomdate", todayLong);
/*      */         
/*  258 */         String config = (configVector.elementAt(n) != null) ? (String)configVector.elementAt(n) : "";
/*      */         
/*  260 */         Hashtable monthTable = (Hashtable)selTable.get(config);
/*      */         
/*  262 */         String previousReleaseWeek = "";
/*  263 */         boolean newCycle = false;
/*      */ 
/*      */         
/*  266 */         int numMonths = 0;
/*  267 */         int numDates = 0;
/*  268 */         int numSelections = 0;
/*      */         
/*  270 */         if (monthTable != null) {
/*      */           
/*  272 */           Enumeration months = monthTable.keys();
/*  273 */           while (months.hasMoreElements()) {
/*      */             
/*  275 */             String monthName = (String)months.nextElement();
/*      */             
/*  277 */             numMonths++;
/*      */             
/*  279 */             Hashtable dateTable = (Hashtable)monthTable.get(monthName);
/*  280 */             if (dateTable != null) {
/*      */               
/*  282 */               Enumeration dates = dateTable.keys();
/*  283 */               while (dates.hasMoreElements()) {
/*      */                 
/*  285 */                 String dateName = (String)dates.nextElement();
/*      */                 
/*  287 */                 numDates++;
/*      */                 
/*  289 */                 selections = (Vector)dateTable.get(dateName);
/*  290 */                 if (selections != null) {
/*  291 */                   numSelections += selections.size();
/*      */                 }
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */         
/*  298 */         int numRows = 0;
/*      */ 
/*      */         
/*  301 */         numRows += numMonths * 3;
/*  302 */         numRows += numDates * 2;
/*  303 */         numRows += numSelections * 2;
/*      */         
/*  305 */         numRows += 5;
/*      */ 
/*      */         
/*  308 */         hbandType = new SectionBand(report);
/*  309 */         hbandType.setHeight(0.95F);
/*  310 */         hbandType.setShrinkToFit(true);
/*  311 */         hbandType.setVisible(true);
/*      */         
/*  313 */         table_contents = new DefaultTableLens(1, 12);
/*      */ 
/*      */ 
/*      */         
/*  317 */         table_contents.setHeaderRowCount(0);
/*  318 */         table_contents.setColBorderColor(Color.lightGray);
/*  319 */         table_contents.setColWidth(0, 70);
/*  320 */         table_contents.setColWidth(1, 259);
/*  321 */         table_contents.setColWidth(2, 157);
/*  322 */         table_contents.setColWidth(3, 80);
/*  323 */         table_contents.setColWidth(4, 80);
/*  324 */         table_contents.setColWidth(5, 80);
/*  325 */         table_contents.setColWidth(6, 80);
/*  326 */         table_contents.setColWidth(7, 60);
/*  327 */         table_contents.setColWidth(8, 60);
/*  328 */         table_contents.setColWidth(9, 60);
/*  329 */         table_contents.setColWidth(10, 60);
/*  330 */         table_contents.setColWidth(11, 60);
/*  331 */         table_contents.setColWidth(12, 60);
/*      */ 
/*      */         
/*  334 */         table_contents.setColBorderColor(Color.black);
/*  335 */         table_contents.setRowBorderColor(Color.black);
/*  336 */         table_contents.setRowBorder(4097);
/*  337 */         table_contents.setColBorder(4097);
/*      */ 
/*      */         
/*  340 */         int nextRow = 0;
/*      */ 
/*      */         
/*  343 */         String configHeaderText = !config.trim().equals("") ? config : "Other";
/*      */ 
/*      */         
/*  346 */         if (configHeaderText != null)
/*      */         {
/*  348 */           if (configHeaderText.startsWith("Frontline")) {
/*  349 */             configHeaderText = "Frontline";
/*      */           }
/*  351 */           else if (configHeaderText.startsWith("Singles")) {
/*  352 */             configHeaderText = "Singles";
/*      */           } 
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*  358 */         table_contents.setSpan(nextRow, 0, new Dimension(12, 1));
/*  359 */         table_contents.setAlignment(nextRow, 0, 2);
/*  360 */         table_contents.setObject(nextRow, 0, configHeaderText);
/*  361 */         table_contents.setRowHeight(nextRow, 16);
/*      */         
/*  363 */         table_contents.setRowBorderColor(nextRow, Color.black);
/*  364 */         table_contents.setRowBorder(nextRow, 0, 266240);
/*      */         
/*  366 */         table_contents.setRowFont(nextRow, new Font("Arial", 3, 12));
/*  367 */         table_contents.setRowBackground(nextRow, Color.white);
/*  368 */         table_contents.setRowForeground(nextRow, Color.black);
/*      */         
/*  370 */         table_contents.setRowBorder(nextRow - 1, 266240);
/*  371 */         table_contents.setColBorder(nextRow, -1, 266240);
/*  372 */         table_contents.setColBorder(nextRow, 0, 266240);
/*      */ 
/*      */         
/*  375 */         table_contents.setRowBorder(nextRow, 266240);
/*      */         
/*  377 */         table_contents.setRowBorderColor(nextRow - 1, Color.black);
/*  378 */         table_contents.setColBorderColor(nextRow, -1, Color.black);
/*  379 */         table_contents.setColBorderColor(nextRow, 0, Color.black);
/*      */ 
/*      */         
/*  382 */         table_contents.setRowBorderColor(nextRow, Color.black);
/*      */ 
/*      */ 
/*      */         
/*  386 */         hbandType.addTable(table_contents, new Rectangle(0, 0, 800, 25));
/*      */         
/*  388 */         nextRow = 0;
/*      */         
/*  390 */         columnHeaderTable = new DefaultTableLens(1, 12);
/*      */ 
/*      */         
/*  393 */         columnHeaderTable.setHeaderRowCount(0);
/*  394 */         columnHeaderTable.setColBorderColor(Color.lightGray);
/*  395 */         columnHeaderTable.setColWidth(0, 100);
/*  396 */         columnHeaderTable.setColWidth(1, 259);
/*  397 */         columnHeaderTable.setColWidth(2, 157);
/*  398 */         columnHeaderTable.setColWidth(3, 80);
/*  399 */         columnHeaderTable.setColWidth(4, 80);
/*  400 */         columnHeaderTable.setColWidth(5, 80);
/*  401 */         columnHeaderTable.setColWidth(6, 80);
/*  402 */         columnHeaderTable.setColWidth(7, 60);
/*  403 */         columnHeaderTable.setColWidth(8, 60);
/*  404 */         columnHeaderTable.setColWidth(9, 60);
/*  405 */         columnHeaderTable.setColWidth(10, 60);
/*  406 */         columnHeaderTable.setColWidth(11, 60);
/*  407 */         columnHeaderTable.setColWidth(12, 60);
/*      */ 
/*      */         
/*  410 */         columnHeaderTable.setAlignment(nextRow, 0, 33);
/*  411 */         columnHeaderTable.setAlignment(nextRow, 1, 34);
/*  412 */         columnHeaderTable.setAlignment(nextRow, 2, 34);
/*  413 */         columnHeaderTable.setAlignment(nextRow, 3, 34);
/*  414 */         columnHeaderTable.setAlignment(nextRow, 4, 34);
/*  415 */         columnHeaderTable.setAlignment(nextRow, 5, 34);
/*  416 */         columnHeaderTable.setAlignment(nextRow, 6, 34);
/*  417 */         columnHeaderTable.setAlignment(nextRow, 7, 34);
/*  418 */         columnHeaderTable.setAlignment(nextRow, 8, 34);
/*  419 */         columnHeaderTable.setAlignment(nextRow, 9, 34);
/*  420 */         columnHeaderTable.setAlignment(nextRow, 10, 34);
/*  421 */         columnHeaderTable.setAlignment(nextRow, 11, 34);
/*  422 */         columnHeaderTable.setAlignment(nextRow, 12, 34);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  427 */         columnHeaderTable.setObject(nextRow, 0, "Street Date\nRelease Week\nProject ID");
/*  428 */         columnHeaderTable.setObject(nextRow, 1, "Artist/Title\nProd./MM");
/*  429 */         columnHeaderTable.setObject(nextRow, 2, "Config\nLocal Prod #\nUPC");
/*  430 */         columnHeaderTable.setObject(nextRow, 3, "Sell\nRetail\nPrice");
/*  431 */         columnHeaderTable.setObject(nextRow, 4, "Credits\n to Art");
/*  432 */         columnHeaderTable.setObject(nextRow, 5, "*\n\nPFM");
/*  433 */         columnHeaderTable.setObject(nextRow, 6, "*\n\nMAP");
/*  434 */         columnHeaderTable.setObject(nextRow, 7, "*\n\nBOM");
/*  435 */         columnHeaderTable.setObject(nextRow, 8, "*\nDJ\nQty\nDue");
/*  436 */         columnHeaderTable.setObject(nextRow, 9, "Film\nShips");
/*  437 */         columnHeaderTable.setObject(nextRow, 10, "*\nPPR");
/*  438 */         columnHeaderTable.setObject(nextRow, 11, "*\nAvail\nto\nShip");
/*      */ 
/*      */         
/*  441 */         columnHeaderTable.setRowBorderColor(nextRow - 1, SHADED_AREA_COLOR);
/*  442 */         columnHeaderTable.setRowBorderColor(nextRow, SHADED_AREA_COLOR);
/*  443 */         columnHeaderTable.setRowFont(nextRow, new Font("Arial", 1, 8));
/*  444 */         columnHeaderTable.setRowBackground(nextRow, Color.white);
/*  445 */         columnHeaderTable.setRowForeground(nextRow, Color.black);
/*      */ 
/*      */         
/*  448 */         hbandType.addTable(columnHeaderTable, new Rectangle(0, 24, 800, 45));
/*  449 */         hbandType.setBottomBorder(0);
/*      */ 
/*      */         
/*  452 */         if (monthTable != null) {
/*      */           
/*  454 */           Enumeration months = monthTable.keys();
/*      */           
/*  456 */           Vector monthVector = new Vector();
/*      */           
/*  458 */           while (months.hasMoreElements()) {
/*  459 */             monthVector.add((String)months.nextElement());
/*      */           }
/*  461 */           Object[] monthArray = null;
/*  462 */           monthArray = monthVector.toArray();
/*      */           
/*  464 */           Arrays.sort(monthArray, new MonthYearComparator());
/*      */           
/*  466 */           for (int x = 0; x < monthArray.length; x++) {
/*      */ 
/*      */             
/*  469 */             String monthName = (String)monthArray[x];
/*  470 */             String monthNameString = monthName;
/*      */             
/*      */             try {
/*  473 */               monthNameString = MONTHS[Integer.parseInt(monthName.substring(0, 2)) - 1];
/*      */             }
/*  475 */             catch (Exception e) {
/*      */               
/*  477 */               if (monthName.equals("13")) {
/*  478 */                 monthNameString = "TBS";
/*  479 */               } else if (monthName.equals("26")) {
/*  480 */                 monthNameString = "ITW";
/*      */               } else {
/*  482 */                 monthNameString = "No street date";
/*      */               } 
/*      */             } 
/*  485 */             monthTableLens = new DefaultTableLens(1, 12);
/*  486 */             hbandCategory = new SectionBand(report);
/*  487 */             hbandCategory.setHeight(0.25F);
/*  488 */             hbandCategory.setShrinkToFit(true);
/*  489 */             hbandCategory.setVisible(true);
/*  490 */             hbandCategory.setBottomBorder(0);
/*  491 */             hbandCategory.setLeftBorder(0);
/*  492 */             hbandCategory.setRightBorder(0);
/*  493 */             hbandCategory.setTopBorder(0);
/*      */             
/*  495 */             nextRow = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  502 */             monthTableLens.setSpan(nextRow, 0, new Dimension(12, 1));
/*  503 */             monthTableLens.setColBorderColor(Color.white);
/*  504 */             monthTableLens.setObject(nextRow, 0, monthNameString);
/*  505 */             monthTableLens.setRowHeight(nextRow, 13);
/*  506 */             monthTableLens.setRowFont(nextRow, new Font("Arial", 1, 12));
/*  507 */             monthTableLens.setRowBackground(nextRow, SHADED_AREA_COLOR);
/*  508 */             monthTableLens.setRowForeground(nextRow, Color.black);
/*  509 */             monthTableLens.setRowBorderColor(nextRow, Color.white);
/*  510 */             monthTableLens.setRowBorderColor(nextRow - 1, Color.white);
/*  511 */             monthTableLens.setColBorderColor(nextRow, -1, Color.white);
/*  512 */             monthTableLens.setColBorderColor(nextRow, 0, Color.white);
/*  513 */             monthTableLens.setColBorderColor(nextRow, 12, Color.white);
/*      */             
/*  515 */             hbandCategory.addTable(monthTableLens, new Rectangle(800, 800));
/*      */             
/*  517 */             footer.setVisible(true);
/*  518 */             footer.setHeight(0.1F);
/*  519 */             footer.setShrinkToFit(false);
/*  520 */             footer.setBottomBorder(0);
/*      */             
/*  522 */             group = new DefaultSectionLens(null, group, spacer);
/*  523 */             group = new DefaultSectionLens(null, group, hbandCategory);
/*  524 */             group = new DefaultSectionLens(null, group, spacer);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  530 */             Hashtable dateTable = (Hashtable)monthTable.get(monthName);
/*  531 */             if (dateTable != null) {
/*      */               
/*  533 */               Enumeration dateSort = dateTable.keys();
/*      */               
/*  535 */               Vector dateVector = new Vector();
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  540 */               while (dateSort.hasMoreElements()) {
/*  541 */                 dateVector.add((String)dateSort.nextElement());
/*      */               }
/*  543 */               Object[] dateArray = null;
/*      */               
/*  545 */               dateArray = dateVector.toArray();
/*  546 */               Arrays.sort(dateArray, new StringDateComparator());
/*      */               
/*  548 */               for (int dIndex = 0; dIndex < dateArray.length; dIndex++) {
/*      */ 
/*      */                 
/*  551 */                 String dateName = (String)dateArray[dIndex];
/*  552 */                 String dateNameText = dateName;
/*      */                 
/*  554 */                 if (monthNameString.equalsIgnoreCase("TBS")) {
/*      */                   
/*  556 */                   dateNameText = "TBS " + dateName;
/*      */                 }
/*  558 */                 else if (monthNameString.equalsIgnoreCase("ITW")) {
/*      */                   
/*  560 */                   dateNameText = "ITW " + dateName;
/*      */                 } 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  566 */                 String releaseWeek = "";
/*      */ 
/*      */                 
/*      */                 try {
/*  570 */                   Calendar calanderDate = MilestoneHelper.getDate(dateNameText);
/*  571 */                   DatePeriod datePeriod = MilestoneHelper.findDatePeriod(calanderDate);
/*  572 */                   releaseWeek = " " + datePeriod.getName();
/*      */                 }
/*  574 */                 catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  579 */                 selections = (Vector)dateTable.get(dateName);
/*  580 */                 if (selections == null) {
/*  581 */                   selections = new Vector();
/*      */                 }
/*      */                 
/*  584 */                 MilestoneHelper.setSelectionSorting(selections, 12);
/*  585 */                 Collections.sort(selections);
/*      */ 
/*      */                 
/*  588 */                 MilestoneHelper.setSelectionSorting(selections, 14);
/*  589 */                 Collections.sort(selections);
/*      */ 
/*      */                 
/*  592 */                 MilestoneHelper.setSelectionSorting(selections, 4);
/*  593 */                 Collections.sort(selections);
/*      */ 
/*      */                 
/*  596 */                 MilestoneHelper.setSelectionSorting(selections, 3);
/*  597 */                 Collections.sort(selections);
/*      */ 
/*      */                 
/*  600 */                 for (int i = 0; i < selections.size(); i++) {
/*      */                   
/*  602 */                   Selection sel = (Selection)selections.elementAt(i);
/*      */                   
/*  604 */                   if (count < recordCount / tenth) {
/*  605 */                     count = recordCount / tenth;
/*  606 */                     sresponse = context.getResponse();
/*  607 */                     context.putDelivery("status", new String("start_report"));
/*  608 */                     int myPercent = count * 10;
/*  609 */                     if (myPercent > 90)
/*  610 */                       myPercent = 90; 
/*  611 */                     context.putDelivery("percent", 
/*  612 */                         new String(String.valueOf(myPercent)));
/*  613 */                     context.includeJSP("status.jsp", "hiddenFrame");
/*  614 */                     sresponse.setContentType("text/plain");
/*  615 */                     sresponse.flushBuffer();
/*      */                   } 
/*  617 */                   recordCount++;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  625 */                   String titleId = "";
/*  626 */                   titleId = sel.getTitleID();
/*      */                   
/*  628 */                   String bSide = "";
/*  629 */                   bSide = sel.getBSide();
/*      */                   
/*  631 */                   String internationalDate = "";
/*      */                   
/*  633 */                   String filler = "";
/*      */ 
/*      */                   
/*  636 */                   String projectID = (sel.getProjectID() != null) ? 
/*  637 */                     sel.getProjectID() : "";
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  642 */                   String artist = "";
/*  643 */                   artist = sel.getArtist().trim();
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  648 */                   String comment = "";
/*  649 */                   String commentStr = (sel.getSelectionComments() != null) ? 
/*  650 */                     sel.getSelectionComments() : "";
/*  651 */                   String newComment = removeLF(commentStr, 800);
/*  652 */                   int subTableRows = 2;
/*  653 */                   if (newComment.length() > 0) {
/*  654 */                     subTableRows = 3;
/*      */                   }
/*      */ 
/*      */ 
/*      */                   
/*  659 */                   String label = "";
/*  660 */                   if (sel.getLabel() != null) {
/*  661 */                     label = sel.getLabel().getName();
/*      */                   }
/*      */ 
/*      */ 
/*      */                   
/*  666 */                   String title = "";
/*  667 */                   if (sel.getTitle() != null) {
/*  668 */                     title = sel.getTitle();
/*      */                   }
/*      */ 
/*      */ 
/*      */                   
/*  673 */                   String upc = "";
/*  674 */                   upc = sel.getUpc();
/*      */                   
/*  676 */                   if (upc == null || upc.length() == 0) {
/*  677 */                     upc = "";
/*      */                   }
/*      */                   
/*  680 */                   upc = MilestoneHelper_2.getRMSReportFormat(upc, "UPC", 
/*  681 */                       sel.getIsDigital());
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  686 */                   String selConfig = "";
/*  687 */                   selConfig = (sel.getSelectionConfig() != null) ? 
/*  688 */                     sel.getSelectionConfig()
/*  689 */                     .getSelectionConfigurationAbbreviation() : "";
/*      */                   
/*  691 */                   String selSubConfig = "";
/*  692 */                   selSubConfig = (sel.getSelectionSubConfig() != null) ? 
/*  693 */                     sel.getSelectionSubConfig()
/*  694 */                     .getSelectionSubConfigurationAbbreviation() : "";
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  699 */                   String units = "";
/*  700 */                   units = (sel.getNumberOfUnits() > 0) ? 
/*  701 */                     String.valueOf(sel.getNumberOfUnits()) : "";
/*      */                   
/*  703 */                   String code = (sel.getSellCode() != null) ? sel.getSellCode() : 
/*  704 */                     "";
/*  705 */                   if (code != null && code.startsWith("-1")) {
/*  706 */                     code = "";
/*      */                   }
/*  708 */                   String retail = "";
/*  709 */                   if (sel.getPriceCode() != null && 
/*  710 */                     sel.getPriceCode().getRetailCode() != null) {
/*  711 */                     retail = sel.getPriceCode().getRetailCode();
/*      */                   }
/*  713 */                   if (code.length() > 0) {
/*  714 */                     retail = "\n" + retail;
/*      */                   }
/*  716 */                   String price = "";
/*  717 */                   if (sel.getPriceCode() != null && 
/*  718 */                     sel.getPriceCode().getTotalCost() > 0.0F) {
/*  719 */                     price = "\n$" + 
/*  720 */                       MilestoneHelper.formatDollarPrice(sel.getPriceCode()
/*  721 */                         .getTotalCost());
/*      */                   }
/*  723 */                   String selectionID = "";
/*  724 */                   selectionID = sel.getSelectionNo();
/*      */                   
/*  726 */                   String prefix = "";
/*  727 */                   prefix = SelectionManager.getLookupObjectValue(sel
/*  728 */                       .getPrefixID());
/*  729 */                   if (!prefix.equals("")) {
/*  730 */                     prefix = String.valueOf(prefix) + "-";
/*      */                   }
/*      */ 
/*      */ 
/*      */                   
/*  735 */                   Schedule schedule = sel.getSchedule();
/*      */                   
/*  737 */                   Vector tasks = (schedule != null) ? schedule.getTasks() : null;
/*  738 */                   ScheduledTask task = null;
/*      */ 
/*      */                   
/*  741 */                   String CRD = "";
/*  742 */                   String FM = "";
/*  743 */                   String MAP = "";
/*  744 */                   String BOM = "";
/*  745 */                   String DJ = "";
/*  746 */                   String LFS = "";
/*  747 */                   String MC = "";
/*  748 */                   String PSD = "";
/*      */                   
/*  750 */                   String CRDcom = "";
/*  751 */                   String FMcom = "";
/*  752 */                   String MAPcom = "";
/*  753 */                   String BOMcom = "";
/*  754 */                   String DJcom = "";
/*  755 */                   String LFScom = "";
/*  756 */                   String MCcom = "";
/*  757 */                   String PSDcom = "";
/*      */                   
/*  759 */                   String taskVendor = "";
/*      */                   
/*  761 */                   if (tasks != null)
/*      */                   {
/*  763 */                     for (int j = 0; j < tasks.size(); j++) {
/*      */                       
/*  765 */                       task = (ScheduledTask)tasks.get(j);
/*      */                       
/*  767 */                       taskVendor = (task.getVendor() != null) ? task.getVendor() : "";
/*  768 */                       taskVendor = taskVendor.equals("\n") ? "" : taskVendor;
/*      */                       
/*  770 */                       if (task != null) {
/*      */ 
/*      */                         
/*  773 */                         String dueDate = "";
/*  774 */                         if (task.getDueDate() != null) {
/*  775 */                           SimpleDateFormat dueDateFormatter = 
/*  776 */                             new SimpleDateFormat("M/d");
/*      */                           
/*  778 */                           dueDate = 
/*  779 */                             String.valueOf(dueDateFormatter.format(task.getDueDate().getTime())) + " " + 
/*  780 */                             MilestoneHelper.getDayType(sel.getCalendarGroup(), 
/*  781 */                               task.getDueDate());
/*      */                         } 
/*      */                         
/*  784 */                         String completionDate = "";
/*  785 */                         if (task.getCompletionDate() != null) {
/*  786 */                           SimpleDateFormat completionDateFormatter = 
/*  787 */                             new SimpleDateFormat("M/d");
/*  788 */                           completionDate = completionDateFormatter.format(task
/*  789 */                               .getCompletionDate().getTime());
/*      */                         } 
/*      */ 
/*      */ 
/*      */                         
/*  794 */                         if (task.getScheduledTaskStatus().equals("N/A")) {
/*  795 */                           completionDate = task.getScheduledTaskStatus();
/*      */                         }
/*      */                         
/*  798 */                         String taskAbbrev = 
/*  799 */                           MilestoneHelper.getTaskAbbreivationNameById(task
/*  800 */                             .getTaskAbbreviationID());
/*      */                         
/*  802 */                         if (taskAbbrev.equalsIgnoreCase("FPC")) {
/*  803 */                           CRD = dueDate;
/*  804 */                           CRDcom = String.valueOf(completionDate) + "\n\n" + taskVendor;
/*      */                           
/*  806 */                           if (!completionDate.equals("")) {
/*  807 */                             CRD = "Done";
/*  808 */                             if (completionDate.equals("9/9/99")) {
/*  809 */                               CRDcom = "\n\n" + taskVendor;
/*      */                             }
/*      */                           }
/*      */                         
/*  813 */                         } else if (taskAbbrev.equalsIgnoreCase("F/M")) {
/*      */                           
/*  815 */                           FM = dueDate;
/*  816 */                           FMcom = String.valueOf(completionDate) + "\n\n" + taskVendor;
/*      */                           
/*  818 */                           if (!completionDate.equals("")) {
/*  819 */                             FM = "Done";
/*  820 */                             if (completionDate.equals("9/9/99")) {
/*  821 */                               FMcom = "\n\n" + taskVendor;
/*      */                             }
/*      */                           }
/*      */                         
/*  825 */                         } else if (taskAbbrev.equalsIgnoreCase("PSD")) {
/*      */                           
/*  827 */                           PSD = dueDate;
/*  828 */                           PSDcom = String.valueOf(completionDate) + "\n\n" + taskVendor;
/*      */                           
/*  830 */                           if (!completionDate.equals("")) {
/*  831 */                             PSD = "Done";
/*  832 */                             if (completionDate.equals("9/9/99")) {
/*  833 */                               PSDcom = "\n\n" + taskVendor;
/*      */                             }
/*      */                           }
/*      */                         
/*      */                         }
/*  838 */                         else if (taskAbbrev.equalsIgnoreCase("BOM")) {
/*      */                           
/*  840 */                           BOM = dueDate;
/*  841 */                           BOMcom = String.valueOf(completionDate) + "\n\n" + taskVendor;
/*      */                           
/*  843 */                           if (!completionDate.equals("")) {
/*  844 */                             BOM = "Done";
/*  845 */                             if (completionDate.equals("9/9/99")) {
/*  846 */                               BOMcom = "\n\n" + taskVendor;
/*      */                             }
/*      */                           }
/*      */                         
/*  850 */                         } else if (taskAbbrev.equalsIgnoreCase("MPOP")) {
/*      */                           
/*  852 */                           DJ = dueDate;
/*  853 */                           DJcom = String.valueOf(completionDate) + "\n\n" + taskVendor;
/*      */                           
/*  855 */                           if (!completionDate.equals("")) {
/*  856 */                             DJ = "Done";
/*  857 */                             if (completionDate.equals("9/9/99")) {
/*  858 */                               DJcom = "\n\n" + taskVendor;
/*      */                             }
/*      */                           }
/*      */                         
/*  862 */                         } else if (taskAbbrev.equalsIgnoreCase("TPS")) {
/*      */                           
/*  864 */                           MAP = dueDate;
/*  865 */                           MAPcom = String.valueOf(completionDate) + "\n\n" + taskVendor;
/*      */                           
/*  867 */                           if (!completionDate.equals("")) {
/*  868 */                             MAP = "Done";
/*  869 */                             if (completionDate.equals("9/9/99")) {
/*  870 */                               MAPcom = "\n\n" + taskVendor;
/*      */                             }
/*      */                           }
/*      */                         
/*  874 */                         } else if (taskAbbrev.equalsIgnoreCase("LFS")) {
/*      */                           
/*  876 */                           LFS = dueDate;
/*  877 */                           LFScom = String.valueOf(completionDate) + "\n\n" + taskVendor;
/*      */                           
/*  879 */                           if (!completionDate.equals("")) {
/*  880 */                             LFS = "Done";
/*  881 */                             if (completionDate.equals("9/9/99")) {
/*  882 */                               LFScom = "\n\n" + taskVendor;
/*      */                             }
/*      */                           }
/*      */                         
/*  886 */                         } else if (taskAbbrev.equalsIgnoreCase("M/C")) {
/*      */                           
/*  888 */                           MC = dueDate;
/*  889 */                           MCcom = String.valueOf(completionDate) + "\n\n" + taskVendor;
/*      */                           
/*  891 */                           if (!completionDate.equals("")) {
/*  892 */                             MC = "Done";
/*  893 */                             if (completionDate.equals("9/9/99")) {
/*  894 */                               MCcom = "\n\n" + taskVendor;
/*      */                             }
/*      */                           } 
/*      */                         } 
/*      */ 
/*      */                         
/*  900 */                         task = null;
/*      */                       } 
/*      */                     } 
/*      */                   }
/*      */ 
/*      */ 
/*      */                   
/*  907 */                   nextRow = 0;
/*  908 */                   subTable = new DefaultTableLens(subTableRows, 12);
/*      */ 
/*      */                   
/*  911 */                   subTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
/*      */ 
/*      */ 
/*      */                   
/*  915 */                   subTable.setHeaderRowCount(0);
/*  916 */                   subTable.setColBorderColor(Color.lightGray);
/*  917 */                   subTable.setColWidth(0, 100);
/*  918 */                   subTable.setColWidth(1, 259);
/*  919 */                   subTable.setColWidth(2, 157);
/*  920 */                   subTable.setColWidth(3, 80);
/*  921 */                   subTable.setColWidth(4, 80);
/*  922 */                   subTable.setColWidth(5, 80);
/*  923 */                   subTable.setColWidth(6, 80);
/*  924 */                   subTable.setColWidth(7, 60);
/*  925 */                   subTable.setColWidth(8, 60);
/*  926 */                   subTable.setColWidth(9, 60);
/*  927 */                   subTable.setColWidth(10, 60);
/*  928 */                   subTable.setColWidth(11, 60);
/*  929 */                   subTable.setColWidth(12, 60);
/*      */                   
/*  931 */                   subTable.setRowBorderColor(nextRow - 1, Color.lightGray);
/*      */ 
/*      */                   
/*  934 */                   if (releaseWeek.equals(previousReleaseWeek)) {
/*  935 */                     newCycle = false;
/*      */                   } else {
/*  937 */                     newCycle = true;
/*      */                   } 
/*  939 */                   previousReleaseWeek = releaseWeek;
/*      */ 
/*      */                   
/*  942 */                   subTable.setBackground(nextRow, 0, Color.white);
/*  943 */                   subTable.setAlignment(nextRow, 0, 10);
/*  944 */                   subTable.setSpan(nextRow, 0, new Dimension(1, 2));
/*      */ 
/*      */                   
/*  947 */                   String releaseWeekString = "";
/*  948 */                   if (releaseWeek.trim().equals("")) {
/*  949 */                     releaseWeekString = projectID;
/*      */                   } else {
/*  951 */                     releaseWeekString = String.valueOf(releaseWeek) + "\n" + projectID;
/*      */                   } 
/*  953 */                   subTable.setObject(nextRow, 0, String.valueOf(dateNameText) + "\n" + releaseWeekString);
/*      */                   
/*  955 */                   subTable.setFont(nextRow, 0, new Font("Arial", 0, 7));
/*      */ 
/*      */                   
/*  958 */                   String contact = "";
/*  959 */                   contact = (sel.getLabelContact() != null) ? sel.getLabelContact().getName() : "";
/*      */ 
/*      */                   
/*  962 */                   String otherContact = "";
/*  963 */                   otherContact = (sel.getOtherContact() != null) ? sel.getOtherContact() : "";
/*      */                   
/*  965 */                   subTable.setObject(nextRow, 1, String.valueOf(artist) + "\n" + title + "\n" + contact + "\n" + otherContact);
/*  966 */                   subTable.setBackground(nextRow, 1, Color.white);
/*  967 */                   subTable.setSpan(nextRow, 1, new Dimension(1, 2));
/*      */                   
/*  969 */                   subTable.setAlignment(nextRow, 1, 9);
/*  970 */                   if (sel.getInternationalDate() != null) {
/*  971 */                     subTable.setFont(nextRow, 1, new Font("Arial", 2, 7));
/*      */                   } else {
/*      */                     
/*  974 */                     subTable.setFont(nextRow, 1, new Font("Arial", 1, 7));
/*      */                   } 
/*      */                   
/*  977 */                   String[] check1a = { comment };
/*  978 */                   int[] check1 = { 16 };
/*  979 */                   String[] check2a = { artist };
/*  980 */                   int[] check2 = { 35 };
/*  981 */                   String[] check3a = { title };
/*  982 */                   int[] check3 = { 35 };
/*  983 */                   String[] check4a = { bSide };
/*  984 */                   int[] check4 = { 35 };
/*      */                   
/*  986 */                   int extraLines = MilestoneHelper.lineCountWCR(check1a, check1) + MilestoneHelper.lineCountWCR(check2a, check2) + MilestoneHelper.lineCountWCR(check3a, check3) + MilestoneHelper.lineCountWCR(check4a, check4);
/*      */                   
/*  988 */                   if (LFScom.length() > 8) {
/*  989 */                     extraLines += 2;
/*      */                   }
/*  991 */                   if (extraLines == 10) {
/*  992 */                     extraLines = 9;
/*      */                   }
/*  994 */                   else if (extraLines > 10) {
/*  995 */                     extraLines = 7;
/*      */                   } 
/*      */                   
/*  998 */                   for (int z = 0; z < extraLines; z++) {
/*  999 */                     filler = String.valueOf(filler) + "\n";
/*      */                   }
/*      */                   
/* 1002 */                   String explicitInd = "";
/* 1003 */                   if (sel.getParentalGuidance()) {
/* 1004 */                     explicitInd = "P.A.Required";
/*      */                   }
/*      */                   
/* 1007 */                   if (!explicitInd.equals("")) {
/* 1008 */                     subTable.setObject(nextRow, 2, String.valueOf(selSubConfig) + "\n" + prefix + selectionID + "\n" + upc + "\n" + explicitInd);
/*      */                   } else {
/* 1010 */                     subTable.setObject(nextRow, 2, String.valueOf(selSubConfig) + "\n" + prefix + selectionID + "\n" + upc);
/*      */                   } 
/*      */ 
/*      */                   
/* 1014 */                   subTable.setAlignment(nextRow, 2, 10);
/* 1015 */                   subTable.setBackground(nextRow, 2, Color.white);
/* 1016 */                   subTable.setSpan(nextRow, 2, new Dimension(1, 2));
/*      */                   
/* 1018 */                   subTable.setObject(nextRow, 3, "Due Dates");
/* 1019 */                   subTable.setColBorderColor(nextRow, 3, SHADED_AREA_COLOR);
/* 1020 */                   subTable.setColBorder(nextRow, 3, 266240);
/* 1021 */                   subTable.setFont(nextRow, 3, new Font("Arial", 1, 8));
/* 1022 */                   subTable.setRowBorderColor(nextRow, SHADED_AREA_COLOR);
/*      */                   
/* 1024 */                   subTable.setAlignment(nextRow, 4, 10);
/* 1025 */                   subTable.setAlignment(nextRow + 1, 4, 10);
/*      */                   
/* 1027 */                   subTable.setRowHeight(nextRow, 9);
/* 1028 */                   subTable.setRowBorderColor(nextRow + 1, SHADED_AREA_COLOR);
/*      */ 
/*      */                   
/* 1031 */                   subTable.setObject(nextRow, 4, CRD);
/* 1032 */                   subTable.setColBorderColor(nextRow, 4, SHADED_AREA_COLOR);
/* 1033 */                   subTable.setColBorder(nextRow, 4, 266240);
/*      */                   
/* 1035 */                   subTable.setObject(nextRow, 5, FM);
/* 1036 */                   subTable.setColBorderColor(nextRow, 5, SHADED_AREA_COLOR);
/* 1037 */                   subTable.setColBorder(nextRow, 5, 266240);
/*      */                   
/* 1039 */                   subTable.setObject(nextRow, 6, MAP);
/* 1040 */                   subTable.setColBorderColor(nextRow, 6, Color.lightGray);
/* 1041 */                   subTable.setColBorder(nextRow, 6, 266240);
/*      */                   
/* 1043 */                   subTable.setObject(nextRow, 7, BOM);
/* 1044 */                   subTable.setColBorderColor(nextRow, 7, SHADED_AREA_COLOR);
/* 1045 */                   subTable.setColBorder(nextRow, 7, 266240);
/*      */                   
/* 1047 */                   subTable.setObject(nextRow, 8, DJ);
/* 1048 */                   subTable.setColBorderColor(nextRow, 8, SHADED_AREA_COLOR);
/* 1049 */                   subTable.setColBorder(nextRow, 8, 266240);
/*      */                   
/* 1051 */                   subTable.setObject(nextRow, 9, LFS);
/* 1052 */                   subTable.setColBorderColor(nextRow, 9, Color.lightGray);
/* 1053 */                   subTable.setColBorder(nextRow, 9, 266240);
/*      */                   
/* 1055 */                   subTable.setObject(nextRow, 10, MC);
/* 1056 */                   subTable.setColBorderColor(nextRow, 10, SHADED_AREA_COLOR);
/* 1057 */                   subTable.setColBorder(nextRow, 10, 266240);
/*      */                   
/* 1059 */                   subTable.setObject(nextRow, 11, PSD);
/* 1060 */                   subTable.setColBorderColor(nextRow, 11, SHADED_AREA_COLOR);
/* 1061 */                   subTable.setColBorder(nextRow, 11, 266240);
/*      */                   
/* 1063 */                   subTable.setFont(nextRow, 3, new Font("Arial", 1, 7));
/* 1064 */                   subTable.setFont(nextRow, 4, new Font("Arial", 1, 7));
/* 1065 */                   subTable.setFont(nextRow, 5, new Font("Arial", 1, 7));
/* 1066 */                   subTable.setFont(nextRow, 6, new Font("Arial", 1, 7));
/* 1067 */                   subTable.setFont(nextRow, 7, new Font("Arial", 1, 7));
/* 1068 */                   subTable.setFont(nextRow, 8, new Font("Arial", 1, 7));
/* 1069 */                   subTable.setFont(nextRow, 9, new Font("Arial", 1, 7));
/* 1070 */                   subTable.setFont(nextRow, 10, new Font("Arial", 1, 7));
/* 1071 */                   subTable.setFont(nextRow, 11, new Font("Arial", 1, 7));
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 1076 */                   Font holidayFont = new Font("Arial", 3, 7);
/* 1077 */                   for (int colIdx = 4; colIdx <= 10; colIdx++) {
/* 1078 */                     String dueDate = subTable.getObject(nextRow, colIdx).toString();
/* 1079 */                     if (dueDate != null && dueDate.length() > 0) {
/* 1080 */                       char lastChar = dueDate.charAt(dueDate.length() - 1);
/* 1081 */                       if (Character.isLetter(lastChar)) {
/* 1082 */                         subTable.setFont(nextRow, colIdx, holidayFont);
/*      */                       }
/*      */                     } 
/*      */                   } 
/*      */                   
/* 1087 */                   subTable.setAlignment(nextRow, 4, 2);
/* 1088 */                   subTable.setAlignment(nextRow, 5, 2);
/* 1089 */                   subTable.setAlignment(nextRow, 6, 2);
/* 1090 */                   subTable.setAlignment(nextRow, 7, 2);
/* 1091 */                   subTable.setAlignment(nextRow, 8, 2);
/* 1092 */                   subTable.setAlignment(nextRow, 9, 2);
/* 1093 */                   subTable.setAlignment(nextRow, 10, 2);
/* 1094 */                   subTable.setAlignment(nextRow, 11, 2);
/*      */ 
/*      */                   
/* 1097 */                   if (extraLines > 0) {
/* 1098 */                     subTable.setObject(nextRow + 1, 3, String.valueOf(code) + retail + price + filler);
/*      */                   } else {
/* 1100 */                     subTable.setObject(nextRow + 1, 3, String.valueOf(code) + retail + price);
/*      */                   } 
/*      */                   
/* 1103 */                   subTable.setObject(nextRow + 1, 4, CRDcom);
/* 1104 */                   subTable.setObject(nextRow + 1, 5, FMcom);
/* 1105 */                   subTable.setObject(nextRow + 1, 6, MAPcom);
/* 1106 */                   subTable.setObject(nextRow + 1, 7, BOMcom);
/* 1107 */                   subTable.setObject(nextRow + 1, 8, DJcom);
/* 1108 */                   subTable.setObject(nextRow + 1, 9, LFScom);
/* 1109 */                   subTable.setObject(nextRow + 1, 10, MCcom);
/* 1110 */                   subTable.setObject(nextRow + 1, 11, PSDcom);
/*      */ 
/*      */                   
/* 1113 */                   subTable.setAlignment(nextRow + 1, 3, 10);
/* 1114 */                   subTable.setAlignment(nextRow + 1, 4, 10);
/* 1115 */                   subTable.setAlignment(nextRow + 1, 5, 10);
/* 1116 */                   subTable.setAlignment(nextRow + 1, 6, 10);
/* 1117 */                   subTable.setAlignment(nextRow + 1, 7, 10);
/* 1118 */                   subTable.setAlignment(nextRow + 1, 8, 10);
/* 1119 */                   subTable.setAlignment(nextRow + 1, 9, 10);
/* 1120 */                   subTable.setAlignment(nextRow + 1, 10, 10);
/* 1121 */                   subTable.setAlignment(nextRow + 1, 11, 10);
/*      */ 
/*      */                   
/* 1124 */                   subTable.setRowFont(nextRow + 1, new Font("Arial", 0, 7));
/*      */                   
/* 1126 */                   subTable.setRowBackground(nextRow, SHADED_AREA_COLOR);
/* 1127 */                   subTable.setRowForeground(nextRow, Color.black);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 1136 */                   subTable.setRowFont(nextRow, new Font("Arial", 0, 7));
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 1141 */                   if (newComment.length() > 0) {
/* 1142 */                     nextRow++;
/* 1143 */                     subTable.setRowAutoSize(true);
/* 1144 */                     subTable.setRowBorderColor(nextRow + 1, Color.lightGray);
/* 1145 */                     setColBorderColor(subTable, nextRow + 1, 12, SHADED_AREA_COLOR);
/* 1146 */                     subTable.setRowFont(nextRow + 1, new Font("Arial", 3, 7));
/*      */                     
/* 1148 */                     subTable.setSpan(nextRow + 1, 0, new Dimension(1, 1));
/*      */                     
/* 1150 */                     subTable.setSpan(nextRow + 1, 1, new Dimension(1, 1));
/* 1151 */                     subTable.setAlignment(nextRow + 1, 1, 12);
/* 1152 */                     subTable.setObject(nextRow + 1, 0, "Comments:   ");
/*      */                     
/* 1154 */                     subTable.setObject(nextRow + 1, 1, newComment);
/* 1155 */                     subTable.setSpan(nextRow + 1, 1, new Dimension(10, 1));
/*      */                     
/* 1157 */                     subTable.setRowBorderColor(nextRow + 1, Color.lightGray);
/* 1158 */                     setColBorderColor(subTable, nextRow + 1, 12, SHADED_AREA_COLOR);
/* 1159 */                     subTable.setRowFont(nextRow + 1, new Font("Arial", 3, 7));
/* 1160 */                     subTable.setAlignment(nextRow + 1, 1, 9);
/* 1161 */                     subTable.setColLineWrap(1, true);
/*      */                     
/* 1163 */                     subTable.setColBorderColor(nextRow + 1, 0, Color.white);
/* 1164 */                     subTable.setColBorderColor(nextRow + 1, 1, Color.white);
/* 1165 */                     subTable.setColBorderColor(nextRow + 1, 2, Color.white);
/* 1166 */                     subTable.setColBorderColor(nextRow + 1, 3, Color.white);
/* 1167 */                     subTable.setColBorderColor(nextRow + 1, 4, Color.white);
/* 1168 */                     subTable.setColBorderColor(nextRow + 1, 5, Color.white);
/* 1169 */                     subTable.setColBorderColor(nextRow + 1, 6, Color.white);
/* 1170 */                     subTable.setColBorderColor(nextRow + 1, 7, Color.white);
/* 1171 */                     subTable.setColBorderColor(nextRow + 1, 8, Color.white);
/* 1172 */                     subTable.setColBorderColor(nextRow + 1, 9, Color.white);
/* 1173 */                     subTable.setColBorderColor(nextRow + 1, 10, Color.white);
/* 1174 */                     subTable.setColBorderColor(nextRow + 1, 11, Color.white);
/*      */                   } 
/*      */ 
/*      */ 
/*      */                   
/* 1179 */                   body = new SectionBand(report);
/*      */                   
/* 1181 */                   double lfLineCount = 1.5D;
/*      */                   
/* 1183 */                   if (extraLines > 0) {
/*      */                     
/* 1185 */                     body.setHeight(1.0F);
/*      */                   }
/*      */                   else {
/*      */                     
/* 1189 */                     body.setHeight(1.0F);
/*      */                   } 
/*      */                   
/* 1192 */                   if (extraLines > 3 || 
/* 1193 */                     CRDcom.length() > 10 || FMcom.length() > 10 || 
/* 1194 */                     MAPcom.length() > 10 || BOMcom.length() > 10 || 
/* 1195 */                     DJcom.length() > 10 || LFScom.length() > 10 || 
/* 1196 */                     MCcom.length() > 10 || PSDcom.length() > 10) {
/*      */ 
/*      */                     
/* 1199 */                     if (lfLineCount < extraLines * 0.3D) {
/* 1200 */                       lfLineCount = extraLines * 0.3D;
/*      */                     }
/* 1202 */                     if (lfLineCount < (CRDcom.length() / 8) * 0.3D) {
/* 1203 */                       lfLineCount = (CRDcom.length() / 8) * 0.3D;
/*      */                     }
/* 1205 */                     if (lfLineCount < (BOMcom.length() / 8) * 0.3D) {
/* 1206 */                       lfLineCount = (BOMcom.length() / 8) * 0.3D;
/*      */                     }
/* 1208 */                     if (lfLineCount < (FMcom.length() / 8) * 0.3D) {
/* 1209 */                       lfLineCount = (FMcom.length() / 8) * 0.3D;
/*      */                     }
/* 1211 */                     if (lfLineCount < (MCcom.length() / 8) * 0.3D) {
/* 1212 */                       lfLineCount = (MCcom.length() / 8) * 0.3D;
/*      */                     }
/* 1214 */                     if (lfLineCount < (CRDcom.length() / 8) * 0.3D) {
/* 1215 */                       lfLineCount = (CRDcom.length() / 8) * 0.3D;
/*      */                     }
/* 1217 */                     if (lfLineCount < (MAPcom.length() / 8) * 0.3D) {
/* 1218 */                       lfLineCount = (MAPcom.length() / 8) * 0.3D;
/*      */                     }
/* 1220 */                     if (lfLineCount < (PSDcom.length() / 8) * 0.3D) {
/* 1221 */                       lfLineCount = (PSDcom.length() / 8) * 0.3D;
/*      */                     }
/* 1223 */                     if (lfLineCount < (DJcom.length() / 8) * 0.3D) {
/* 1224 */                       lfLineCount = (DJcom.length() / 8) * 0.3D;
/*      */                     }
/* 1226 */                     if (lfLineCount < (LFScom.length() / 8) * 0.3D) {
/* 1227 */                       lfLineCount = (LFScom.length() / 8) * 0.3D;
/*      */                     }
/* 1229 */                     body.setHeight((float)lfLineCount);
/*      */                   }
/*      */                   else {
/*      */                     
/* 1233 */                     body.setHeight(1.0F);
/*      */                   } 
/*      */ 
/*      */                   
/* 1237 */                   body.addTable(subTable, new Rectangle(800, 800));
/* 1238 */                   body.setBottomBorder(0);
/* 1239 */                   body.setTopBorder(0);
/* 1240 */                   body.setShrinkToFit(true);
/* 1241 */                   body.setVisible(true);
/*      */                   
/* 1243 */                   if (newCycle) {
/* 1244 */                     group = new DefaultSectionLens(null, group, spacer);
/*      */                   }
/*      */                   
/* 1247 */                   group = new DefaultSectionLens(null, group, body);
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1258 */         group = new DefaultSectionLens(hbandType, group, null);
/* 1259 */         report.addSection(group, rowCountTable);
/* 1260 */         report.addPageBreak();
/* 1261 */         group = null;
/*      */ 
/*      */       
/*      */       }
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 1269 */     catch (Exception e) {
/*      */       
/* 1271 */       System.out.println(">>>>>>>>McaProductionScheduleForPrintSubHandler(): exception: " + e);
/*      */     } 
/*      */ 
/*      */     
/* 1275 */     System.out.println("done");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1282 */   public static String removeLF(String theString, int maxChars) { return theString.replace('\n', ' '); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void setColBorderColor(DefaultTableLens table, int row, int columns, Color color) {
/* 1291 */     for (int i = -1; i < columns; i++)
/*      */     {
/* 1293 */       table.setColBorderColor(row, i, color);
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\MCAProductionScheduleForPrintSubHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */