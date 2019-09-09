/*      */ package WEB-INF.classes.com.universal.milestone;
/*      */ 
/*      */ import com.techempower.ComponentLog;
/*      */ import com.techempower.gemini.Context;
/*      */ import com.techempower.gemini.GeminiApplication;
/*      */ import com.universal.milestone.DatePeriod;
/*      */ import com.universal.milestone.Form;
/*      */ import com.universal.milestone.MilestoneHelper;
/*      */ import com.universal.milestone.MilestoneHelper_2;
/*      */ import com.universal.milestone.MonthYearComparator;
/*      */ import com.universal.milestone.MultOtherContact;
/*      */ import com.universal.milestone.NewReleasePlanningScheduleForPrintSubHandler;
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
/*      */ public class NewReleasePlanningScheduleForPrintSubHandler
/*      */   extends SecureHandler
/*      */ {
/*      */   public static final String COMPONENT_CODE = "hMCAProd";
/*      */   public GeminiApplication application;
/*      */   public ComponentLog log;
/*      */   
/*      */   public void NewReleasePlanningScheduleForPrintSubHandler(GeminiApplication application) {
/*   66 */     this.application = application;
/*   67 */     this.log = application.getLog("hMCAProd");
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
/*      */   
/*      */   protected static void fillNewRelPlanScheduleForPrint(XStyleSheet report, Context context) {
/*   90 */     int COL_LINE_STYLE = 4097;
/*   91 */     int HEADER_FONT_SIZE = 12;
/*      */     
/*   93 */     double ldLineVal = 0.3D;
/*      */     
/*      */     try {
/*   96 */       HttpServletResponse sresponse = context.getResponse();
/*   97 */       context.putDelivery("status", new String("start_gathering"));
/*   98 */       context.includeJSP("status.jsp", "hiddenFrame");
/*   99 */       sresponse.setContentType("text/plain");
/*  100 */       sresponse.flushBuffer();
/*      */     }
/*  102 */     catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  107 */     Vector selections = MilestoneHelper.getSelectionsForReport(context);
/*      */     
/*      */     try {
/*  110 */       HttpServletResponse sresponse = context.getResponse();
/*  111 */       context.putDelivery("status", new String("start_report"));
/*  112 */       context.putDelivery("percent", new String("10"));
/*  113 */       context.includeJSP("status.jsp", "hiddenFrame");
/*  114 */       sresponse.setContentType("text/plain");
/*  115 */       sresponse.flushBuffer();
/*      */     }
/*  117 */     catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  122 */     int DATA_FONT_SIZE = 7;
/*  123 */     int SMALL_HEADER_FONT_SIZE = 8;
/*      */     
/*  125 */     int NUM_COLUMNS = 18;
/*  126 */     Color SHADED_AREA_COLOR = Color.lightGray;
/*      */ 
/*      */     
/*  129 */     SectionBand hbandType = new SectionBand(report);
/*  130 */     SectionBand hbandCategory = new SectionBand(report);
/*  131 */     SectionBand hbandDate = new SectionBand(report);
/*  132 */     SectionBand hbandRelWeek = new SectionBand(report);
/*  133 */     SectionBand body = new SectionBand(report);
/*  134 */     SectionBand footer = new SectionBand(report);
/*  135 */     SectionBand spacer = new SectionBand(report);
/*  136 */     DefaultSectionLens group = null;
/*      */     
/*  138 */     footer.setVisible(true);
/*  139 */     footer.setHeight(0.1F);
/*  140 */     footer.setShrinkToFit(false);
/*  141 */     footer.setBottomBorder(0);
/*      */     
/*  143 */     spacer.setVisible(true);
/*  144 */     spacer.setHeight(0.05F);
/*  145 */     spacer.setShrinkToFit(false);
/*  146 */     spacer.setBottomBorder(0);
/*      */ 
/*      */     
/*  149 */     Hashtable selTable = MilestoneHelper.groupSelectionsForMcaByTypeConfigAndStreetDate(selections);
/*  150 */     Enumeration configs = selTable.keys();
/*  151 */     Vector configVector = new Vector();
/*      */     
/*  153 */     while (configs.hasMoreElements()) {
/*  154 */       configVector.addElement(configs.nextElement());
/*      */     }
/*  156 */     int numConfigs = configVector.size();
/*      */ 
/*      */     
/*      */     try {
/*  160 */       Collections.sort(configVector);
/*  161 */       Vector sortedConfigVector = MilestoneHelper.sortStrings(configVector);
/*      */ 
/*      */       
/*  164 */       DefaultTableLens table_contents = null;
/*  165 */       DefaultTableLens rowCountTable = null;
/*  166 */       DefaultTableLens columnHeaderTable = null;
/*  167 */       DefaultTableLens subTable = null;
/*  168 */       DefaultTableLens monthTableLens = null;
/*  169 */       DefaultTableLens dateTableLens = null;
/*  170 */       DefaultTableLens relWeekLens = null;
/*      */       
/*  172 */       rowCountTable = new DefaultTableLens(2, 10000);
/*      */ 
/*      */       
/*  175 */       int totalCount = 0;
/*  176 */       int tenth = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  182 */       for (int n = 0; n < sortedConfigVector.size(); n++) {
/*      */         
/*  184 */         String configC = (configVector.elementAt(n) != null) ? (String)configVector.elementAt(n) : "";
/*  185 */         Hashtable monthTableC = (Hashtable)selTable.get(configC);
/*      */         
/*  187 */         totalCount++;
/*  188 */         Enumeration monthsC = monthTableC.keys();
/*      */         
/*  190 */         Vector monthVectorC = new Vector();
/*      */         
/*  192 */         while (monthsC.hasMoreElements()) {
/*  193 */           monthVectorC.add((String)monthsC.nextElement());
/*  194 */           Object[] monthArrayC = null;
/*  195 */           monthArrayC = monthVectorC.toArray();
/*  196 */           totalCount += monthArrayC.length;
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  201 */       tenth = (totalCount > 10) ? (totalCount / 10) : 1;
/*      */       
/*  203 */       HttpServletResponse sresponse = context.getResponse();
/*  204 */       context.putDelivery("status", new String("start_report"));
/*  205 */       context.putDelivery("percent", new String("20"));
/*  206 */       context.includeJSP("status.jsp", "hiddenFrame");
/*  207 */       sresponse.setContentType("text/plain");
/*  208 */       sresponse.flushBuffer();
/*      */       
/*  210 */       int recordCount = 0;
/*  211 */       int count = 0;
/*      */       
/*  213 */       for (int n = 0; n < sortedConfigVector.size(); n++)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  219 */         Form reportForm = (Form)context.getSessionValue("reportForm");
/*      */         
/*  221 */         Calendar beginStDate = (reportForm.getStringValue("beginDate") != null && 
/*  222 */           reportForm.getStringValue("beginDate").length() > 0) ? 
/*  223 */           MilestoneHelper.getDate(reportForm.getStringValue("beginDate")) : null;
/*      */         
/*  225 */         Calendar endStDate = (reportForm.getStringValue("endDate") != null && 
/*  226 */           reportForm.getStringValue("endDate").length() > 0) ? 
/*  227 */           MilestoneHelper.getDate(reportForm.getStringValue("endDate")) : null;
/*      */         
/*  229 */         report.setElement("crs_startdate", MilestoneHelper.getFormatedDate(beginStDate));
/*  230 */         report.setElement("crs_enddate", MilestoneHelper.getFormatedDate(endStDate));
/*      */         
/*  232 */         SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy, h:mm a");
/*  233 */         String todayLong = formatter.format(new Date());
/*  234 */         report.setElement("crs_bottomdate", todayLong);
/*      */         
/*  236 */         String config = (configVector.elementAt(n) != null) ? (String)configVector.elementAt(n) : "";
/*      */         
/*  238 */         Hashtable monthTable = (Hashtable)selTable.get(config);
/*      */         
/*  240 */         String previousReleaseWeek = "";
/*  241 */         boolean newCycle = false;
/*      */ 
/*      */         
/*  244 */         int numMonths = 0;
/*  245 */         int numDates = 0;
/*  246 */         int numSelections = 0;
/*      */         
/*  248 */         if (monthTable != null) {
/*      */           
/*  250 */           Enumeration months = monthTable.keys();
/*  251 */           while (months.hasMoreElements()) {
/*      */             
/*  253 */             String monthName = (String)months.nextElement();
/*      */             
/*  255 */             numMonths++;
/*      */             
/*  257 */             Hashtable dateTable = (Hashtable)monthTable.get(monthName);
/*  258 */             if (dateTable != null) {
/*      */               
/*  260 */               Enumeration dates = dateTable.keys();
/*  261 */               while (dates.hasMoreElements()) {
/*      */                 
/*  263 */                 String dateName = (String)dates.nextElement();
/*      */                 
/*  265 */                 numDates++;
/*      */                 
/*  267 */                 selections = (Vector)dateTable.get(dateName);
/*  268 */                 if (selections != null) {
/*  269 */                   numSelections += selections.size();
/*      */                 }
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */         
/*  276 */         int numRows = 0;
/*      */ 
/*      */         
/*  279 */         numRows += numMonths * 3;
/*  280 */         numRows += numDates * 2;
/*  281 */         numRows += numSelections * 2;
/*      */         
/*  283 */         numRows += 5;
/*      */ 
/*      */         
/*  286 */         hbandType = new SectionBand(report);
/*  287 */         hbandType.setHeight(1.3F);
/*  288 */         hbandType.setShrinkToFit(true);
/*  289 */         hbandType.setVisible(true);
/*      */         
/*  291 */         table_contents = new DefaultTableLens(1, 18);
/*      */ 
/*      */ 
/*      */         
/*  295 */         table_contents.setHeaderRowCount(0);
/*  296 */         table_contents.setColBorderColor(Color.lightGray);
/*  297 */         table_contents.setColWidth(0, 70);
/*  298 */         table_contents.setColWidth(1, 259);
/*  299 */         table_contents.setColWidth(2, 110);
/*  300 */         table_contents.setColWidth(3, 150);
/*  301 */         table_contents.setColWidth(4, 10);
/*  302 */         table_contents.setColWidth(5, 80);
/*  303 */         table_contents.setColWidth(6, 80);
/*  304 */         table_contents.setColWidth(7, 60);
/*  305 */         table_contents.setColWidth(8, 60);
/*  306 */         table_contents.setColWidth(9, 60);
/*  307 */         table_contents.setColWidth(10, 60);
/*  308 */         table_contents.setColWidth(11, 60);
/*  309 */         table_contents.setColWidth(12, 60);
/*  310 */         table_contents.setColWidth(13, 60);
/*  311 */         table_contents.setColWidth(14, 60);
/*  312 */         table_contents.setColWidth(15, 60);
/*  313 */         table_contents.setColWidth(16, 60);
/*  314 */         table_contents.setColWidth(17, 60);
/*      */ 
/*      */         
/*  317 */         table_contents.setColBorderColor(Color.black);
/*  318 */         table_contents.setRowBorderColor(Color.black);
/*  319 */         table_contents.setRowBorder(4097);
/*  320 */         table_contents.setColBorder(4097);
/*      */ 
/*      */         
/*  323 */         int nextRow = 0;
/*      */ 
/*      */         
/*  326 */         String configHeaderText = !config.trim().equals("") ? config : "Other";
/*      */ 
/*      */         
/*  329 */         if (configHeaderText != null)
/*      */         {
/*  331 */           if (configHeaderText.startsWith("Frontline")) {
/*  332 */             configHeaderText = "Frontline";
/*      */           }
/*  334 */           else if (configHeaderText.startsWith("Singles")) {
/*  335 */             configHeaderText = "Singles";
/*      */           } 
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  342 */         table_contents.setSpan(nextRow, 0, new Dimension(18, 1));
/*  343 */         table_contents.setAlignment(nextRow, 0, 2);
/*  344 */         table_contents.setObject(nextRow, 0, configHeaderText);
/*  345 */         table_contents.setRowHeight(nextRow, 16);
/*      */         
/*  347 */         table_contents.setRowBorderColor(nextRow, Color.black);
/*  348 */         table_contents.setRowBorder(nextRow, 0, 266240);
/*      */         
/*  350 */         table_contents.setRowFont(nextRow, new Font("Arial", 3, 12));
/*  351 */         table_contents.setRowBackground(nextRow, Color.white);
/*  352 */         table_contents.setRowForeground(nextRow, Color.black);
/*      */         
/*  354 */         table_contents.setRowBorder(nextRow - 1, 266240);
/*  355 */         table_contents.setColBorder(nextRow, -1, 266240);
/*  356 */         table_contents.setColBorder(nextRow, 0, 266240);
/*  357 */         table_contents.setColBorder(nextRow, 14, 266240);
/*  358 */         table_contents.setColBorder(nextRow, 15, 266240);
/*  359 */         table_contents.setRowBorder(nextRow, 266240);
/*      */         
/*  361 */         table_contents.setRowBorderColor(nextRow - 1, Color.black);
/*  362 */         table_contents.setColBorderColor(nextRow, -1, Color.black);
/*  363 */         table_contents.setColBorderColor(nextRow, 0, Color.black);
/*  364 */         table_contents.setColBorderColor(nextRow, 14, Color.black);
/*  365 */         table_contents.setColBorderColor(nextRow, 15, Color.black);
/*  366 */         table_contents.setRowBorderColor(nextRow, Color.black);
/*      */ 
/*      */ 
/*      */         
/*  370 */         hbandType.addTable(table_contents, new Rectangle(0, 0, 800, 25));
/*      */         
/*  372 */         nextRow = 0;
/*      */         
/*  374 */         columnHeaderTable = new DefaultTableLens(1, 18);
/*      */         
/*  376 */         columnHeaderTable.setHeaderRowCount(0);
/*  377 */         columnHeaderTable.setColBorderColor(Color.lightGray);
/*  378 */         columnHeaderTable.setColWidth(0, 60);
/*  379 */         columnHeaderTable.setColWidth(1, 259);
/*  380 */         columnHeaderTable.setColWidth(2, 110);
/*  381 */         columnHeaderTable.setColWidth(3, 150);
/*  382 */         columnHeaderTable.setColWidth(4, 50);
/*  383 */         columnHeaderTable.setColWidth(5, 80);
/*  384 */         columnHeaderTable.setColWidth(6, 80);
/*  385 */         columnHeaderTable.setColWidth(7, 45);
/*  386 */         columnHeaderTable.setColWidth(8, 70);
/*  387 */         columnHeaderTable.setColWidth(9, 70);
/*  388 */         columnHeaderTable.setColWidth(10, 55);
/*  389 */         columnHeaderTable.setColWidth(11, 60);
/*  390 */         columnHeaderTable.setColWidth(12, 60);
/*  391 */         columnHeaderTable.setColWidth(13, 60);
/*  392 */         columnHeaderTable.setColWidth(14, 60);
/*  393 */         columnHeaderTable.setColWidth(15, 60);
/*  394 */         columnHeaderTable.setColWidth(16, 60);
/*  395 */         columnHeaderTable.setColWidth(17, 60);
/*      */ 
/*      */         
/*  398 */         columnHeaderTable.setAlignment(nextRow, 0, 33);
/*  399 */         columnHeaderTable.setAlignment(nextRow, 1, 34);
/*  400 */         columnHeaderTable.setAlignment(nextRow, 2, 34);
/*  401 */         columnHeaderTable.setAlignment(nextRow, 3, 34);
/*  402 */         columnHeaderTable.setAlignment(nextRow, 4, 34);
/*  403 */         columnHeaderTable.setAlignment(nextRow, 5, 34);
/*  404 */         columnHeaderTable.setAlignment(nextRow, 6, 34);
/*  405 */         columnHeaderTable.setAlignment(nextRow, 7, 34);
/*  406 */         columnHeaderTable.setAlignment(nextRow, 8, 34);
/*  407 */         columnHeaderTable.setAlignment(nextRow, 9, 34);
/*  408 */         columnHeaderTable.setAlignment(nextRow, 10, 34);
/*  409 */         columnHeaderTable.setAlignment(nextRow, 11, 34);
/*  410 */         columnHeaderTable.setAlignment(nextRow, 12, 34);
/*  411 */         columnHeaderTable.setAlignment(nextRow, 13, 34);
/*  412 */         columnHeaderTable.setAlignment(nextRow, 14, 34);
/*  413 */         columnHeaderTable.setAlignment(nextRow, 15, 34);
/*  414 */         columnHeaderTable.setAlignment(nextRow, 16, 34);
/*  415 */         columnHeaderTable.setAlignment(nextRow, 17, 34);
/*      */ 
/*      */ 
/*      */         
/*  419 */         columnHeaderTable.setObject(nextRow, 0, "Street\nDate");
/*  420 */         columnHeaderTable.setObject(nextRow, 1, "Artist/Title");
/*  421 */         columnHeaderTable.setObject(nextRow, 2, "Config\nSelection\nUPC");
/*  422 */         columnHeaderTable.setObject(nextRow, 3, "Contact Info\nMKG DIR\nART DIR\nA&R");
/*  423 */         columnHeaderTable.setObject(nextRow, 4, "Hold");
/*  424 */         columnHeaderTable.setObject(nextRow, 5, "Credits\n to Prod");
/*  425 */         columnHeaderTable.setObject(nextRow, 6, "Credits\n to Art");
/*  426 */         columnHeaderTable.setObject(nextRow, 7, "*\n\nFM");
/*  427 */         columnHeaderTable.setObject(nextRow, 8, "POI");
/*  428 */         columnHeaderTable.setObject(nextRow, 9, "POP");
/*  429 */         columnHeaderTable.setObject(nextRow, 10, "*\n\nMAP");
/*  430 */         columnHeaderTable.setObject(nextRow, 11, "Sticker\nCopy\nDue");
/*  431 */         columnHeaderTable.setObject(nextRow, 12, "Mech\nBegin\nRtg");
/*  432 */         columnHeaderTable.setObject(nextRow, 13, "*\n\nBOM");
/*  433 */         columnHeaderTable.setObject(nextRow, 14, "Mech\nSeps");
/*  434 */         columnHeaderTable.setObject(nextRow, 15, "*\nSticker\nShips");
/*  435 */         columnHeaderTable.setObject(nextRow, 16, "Film\nShips");
/*  436 */         columnHeaderTable.setObject(nextRow, 17, "*\nPPR");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  442 */         columnHeaderTable.setRowBorderColor(nextRow - 1, SHADED_AREA_COLOR);
/*  443 */         columnHeaderTable.setRowBorderColor(nextRow, SHADED_AREA_COLOR);
/*  444 */         columnHeaderTable.setRowFont(nextRow, new Font("Arial", 1, 8));
/*  445 */         columnHeaderTable.setRowBackground(nextRow, Color.white);
/*  446 */         columnHeaderTable.setRowForeground(nextRow, Color.black);
/*      */ 
/*      */         
/*  449 */         hbandType.addTable(columnHeaderTable, new Rectangle(0, 24, 800, 59));
/*  450 */         hbandType.setBottomBorder(0);
/*      */ 
/*      */         
/*  453 */         if (monthTable != null) {
/*      */           
/*  455 */           Enumeration months = monthTable.keys();
/*      */           
/*  457 */           Vector monthVector = new Vector();
/*      */           
/*  459 */           while (months.hasMoreElements()) {
/*  460 */             monthVector.add((String)months.nextElement());
/*      */           }
/*  462 */           Object[] monthArray = null;
/*  463 */           monthArray = monthVector.toArray();
/*      */           
/*  465 */           Arrays.sort(monthArray, new MonthYearComparator());
/*      */           
/*  467 */           for (int x = 0; x < monthArray.length; x++) {
/*      */ 
/*      */             
/*  470 */             String monthName = (String)monthArray[x];
/*  471 */             String monthNameString = monthName;
/*      */             
/*      */             try {
/*  474 */               monthNameString = MONTHS[Integer.parseInt(monthName.substring(0, 2)) - 1];
/*      */             }
/*  476 */             catch (Exception e) {
/*      */               
/*  478 */               if (monthName.equals("13")) {
/*  479 */                 monthNameString = "TBS";
/*  480 */               } else if (monthName.equals("26")) {
/*  481 */                 monthNameString = "ITW";
/*      */               } else {
/*  483 */                 monthNameString = "No street date";
/*      */               } 
/*      */             } 
/*  486 */             monthTableLens = new DefaultTableLens(1, 18);
/*  487 */             hbandCategory = new SectionBand(report);
/*  488 */             hbandCategory.setHeight(0.25F);
/*  489 */             hbandCategory.setShrinkToFit(true);
/*  490 */             hbandCategory.setVisible(true);
/*  491 */             hbandCategory.setBottomBorder(0);
/*  492 */             hbandCategory.setLeftBorder(0);
/*  493 */             hbandCategory.setRightBorder(0);
/*  494 */             hbandCategory.setTopBorder(0);
/*      */             
/*  496 */             nextRow = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  504 */             monthTableLens.setSpan(nextRow, 0, new Dimension(18, 1));
/*  505 */             monthTableLens.setColBorderColor(Color.white);
/*  506 */             monthTableLens.setObject(nextRow, 0, monthNameString);
/*  507 */             monthTableLens.setRowHeight(nextRow, 14);
/*  508 */             monthTableLens.setRowFont(nextRow, new Font("Arial", 1, 12));
/*  509 */             monthTableLens.setRowBackground(nextRow, SHADED_AREA_COLOR);
/*  510 */             monthTableLens.setRowForeground(nextRow, Color.black);
/*  511 */             monthTableLens.setRowBorderColor(nextRow, Color.white);
/*  512 */             monthTableLens.setRowBorderColor(nextRow - 1, Color.white);
/*  513 */             monthTableLens.setColBorderColor(nextRow, -1, Color.white);
/*  514 */             monthTableLens.setColBorderColor(nextRow, 0, Color.white);
/*  515 */             monthTableLens.setColBorderColor(nextRow, 14, Color.white);
/*      */             
/*  517 */             hbandCategory.addTable(monthTableLens, new Rectangle(800, 800));
/*      */             
/*  519 */             footer.setVisible(true);
/*  520 */             footer.setHeight(0.1F);
/*  521 */             footer.setShrinkToFit(false);
/*  522 */             footer.setBottomBorder(0);
/*      */             
/*  524 */             group = new DefaultSectionLens(null, group, spacer);
/*  525 */             group = new DefaultSectionLens(null, group, hbandCategory);
/*  526 */             group = new DefaultSectionLens(null, group, spacer);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  532 */             Hashtable dateTable = (Hashtable)monthTable.get(monthName);
/*  533 */             if (dateTable != null) {
/*      */               
/*  535 */               Enumeration dateSort = dateTable.keys();
/*      */               
/*  537 */               Vector dateVector = new Vector();
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  542 */               while (dateSort.hasMoreElements()) {
/*  543 */                 dateVector.add((String)dateSort.nextElement());
/*      */               }
/*  545 */               Object[] dateArray = null;
/*      */               
/*  547 */               dateArray = dateVector.toArray();
/*  548 */               Arrays.sort(dateArray, new StringDateComparator());
/*      */               
/*  550 */               for (int dIndex = 0; dIndex < dateArray.length; dIndex++) {
/*      */ 
/*      */                 
/*  553 */                 String dateName = (String)dateArray[dIndex];
/*  554 */                 String dateNameText = dateName;
/*      */                 
/*  556 */                 if (monthNameString.equalsIgnoreCase("TBS")) {
/*      */                   
/*  558 */                   dateNameText = "TBS " + dateName;
/*      */                 }
/*  560 */                 else if (monthNameString.equalsIgnoreCase("ITW")) {
/*      */                   
/*  562 */                   dateNameText = "ITW " + dateName;
/*      */                 } 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  568 */                 String releaseWeek = "";
/*      */ 
/*      */                 
/*      */                 try {
/*  572 */                   Calendar calanderDate = MilestoneHelper.getDate(dateNameText);
/*  573 */                   DatePeriod datePeriod = MilestoneHelper.findDatePeriod(calanderDate);
/*  574 */                   releaseWeek = " " + datePeriod.getName();
/*      */                 }
/*  576 */                 catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  581 */                 selections = (Vector)dateTable.get(dateName);
/*  582 */                 if (selections == null) {
/*  583 */                   selections = new Vector();
/*      */                 }
/*      */                 
/*  586 */                 MilestoneHelper.setSelectionSorting(selections, 12);
/*  587 */                 Collections.sort(selections);
/*      */ 
/*      */                 
/*  590 */                 MilestoneHelper.setSelectionSorting(selections, 14);
/*  591 */                 Collections.sort(selections);
/*      */ 
/*      */                 
/*  594 */                 MilestoneHelper.setSelectionSorting(selections, 4);
/*  595 */                 Collections.sort(selections);
/*      */ 
/*      */                 
/*  598 */                 MilestoneHelper.setSelectionSorting(selections, 3);
/*  599 */                 Collections.sort(selections);
/*      */ 
/*      */                 
/*  602 */                 for (int i = 0; i < selections.size(); i++) {
/*      */                   
/*  604 */                   Selection sel = (Selection)selections.elementAt(i);
/*      */                   
/*  606 */                   if (count < recordCount / tenth) {
/*      */                     
/*  608 */                     count = recordCount / tenth;
/*  609 */                     sresponse = context.getResponse();
/*  610 */                     context.putDelivery("status", new String("start_report"));
/*  611 */                     int myPercent = count * 10;
/*  612 */                     if (myPercent > 90)
/*  613 */                       myPercent = 90; 
/*  614 */                     context.putDelivery("percent", new String(String.valueOf(myPercent)));
/*  615 */                     context.includeJSP("status.jsp", "hiddenFrame");
/*  616 */                     sresponse.setContentType("text/plain");
/*  617 */                     sresponse.flushBuffer();
/*      */                   } 
/*  619 */                   recordCount++;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  627 */                   String titleId = "";
/*  628 */                   titleId = sel.getTitleID();
/*      */                   
/*  630 */                   String bSide = "";
/*  631 */                   bSide = sel.getBSide();
/*      */                   
/*  633 */                   String internationalDate = "";
/*      */                   
/*  635 */                   String filler = "";
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  640 */                   String artist = "";
/*  641 */                   artist = sel.getArtist().trim();
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  646 */                   String comment = (sel.getSelectionComments() != null) ? sel.getSelectionComments() : "";
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  651 */                   String label = "";
/*  652 */                   if (sel.getLabel() != null) {
/*  653 */                     label = sel.getLabel().getName();
/*      */                   }
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  659 */                   String title = "";
/*  660 */                   if (sel.getTitle() != null) {
/*  661 */                     title = sel.getTitle();
/*      */                   }
/*      */ 
/*      */ 
/*      */                   
/*  666 */                   String upc = "";
/*  667 */                   upc = sel.getUpc();
/*      */                   
/*  669 */                   if (upc == null || upc.length() == 0) {
/*  670 */                     upc = "";
/*      */                   }
/*      */                   
/*  673 */                   upc = MilestoneHelper_2.getRMSReportFormat(upc, "UPC", sel.getIsDigital());
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  678 */                   String selConfig = "";
/*  679 */                   selConfig = (sel.getSelectionConfig() != null) ? sel.getSelectionConfig().getSelectionConfigurationAbbreviation() : "";
/*      */                   
/*  681 */                   String selSubConfig = "";
/*  682 */                   selSubConfig = (sel.getSelectionSubConfig() != null) ? sel.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation() : "";
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  687 */                   String units = "";
/*  688 */                   units = (sel.getNumberOfUnits() > 0) ? String.valueOf(sel.getNumberOfUnits()) : "";
/*      */                   
/*  690 */                   String code = (sel.getSellCode() != null) ? sel.getSellCode() : "";
/*  691 */                   if (code != null && code.startsWith("-1")) {
/*  692 */                     code = "";
/*      */                   }
/*  694 */                   String retail = "";
/*  695 */                   if (sel.getPriceCode() != null && sel.getPriceCode().getRetailCode() != null) {
/*  696 */                     retail = sel.getPriceCode().getRetailCode();
/*      */                   }
/*  698 */                   if (code.length() > 0) {
/*  699 */                     retail = "\n" + retail;
/*      */                   }
/*  701 */                   String price = "";
/*  702 */                   if (sel.getPriceCode() != null && sel.getPriceCode().getTotalCost() > 0.0F) {
/*  703 */                     price = "\n$" + MilestoneHelper.formatDollarPrice(sel.getPriceCode().getTotalCost());
/*      */                   }
/*  705 */                   String selectionID = "";
/*  706 */                   selectionID = sel.getSelectionNo();
/*      */                   
/*  708 */                   String prefix = "";
/*  709 */                   prefix = SelectionManager.getLookupObjectValue(sel.getPrefixID());
/*  710 */                   if (!prefix.equals("")) {
/*  711 */                     prefix = String.valueOf(prefix) + "-";
/*      */                   }
/*      */ 
/*      */ 
/*      */                   
/*  716 */                   Schedule schedule = sel.getSchedule();
/*      */                   
/*  718 */                   Vector tasks = (schedule != null) ? schedule.getTasks() : null;
/*  719 */                   ScheduledTask task = null;
/*      */                   
/*  721 */                   String HLD = "";
/*  722 */                   String CRD = "";
/*  723 */                   String CRDA = "";
/*  724 */                   String FM = "";
/*  725 */                   String POI = "";
/*  726 */                   String POP = "";
/*  727 */                   String TAPE = "";
/*  728 */                   String STD = "";
/*  729 */                   String MBR = "";
/*  730 */                   String BOM = "";
/*  731 */                   String SEPS = "";
/*  732 */                   String STIC = "";
/*  733 */                   String PFS = "";
/*  734 */                   String MC = "";
/*  735 */                   String MAP = "";
/*      */ 
/*      */ 
/*      */                   
/*  739 */                   String HLDcom = "";
/*  740 */                   String CRDcom = "";
/*  741 */                   String CRDAcom = "";
/*  742 */                   String FMcom = "";
/*  743 */                   String POIcom = "";
/*  744 */                   String POPcom = "";
/*  745 */                   String TAPEcom = "";
/*  746 */                   String STDcom = "";
/*  747 */                   String MBRcom = "";
/*  748 */                   String BOMcom = "";
/*  749 */                   String SEPScom = "";
/*  750 */                   String STICcom = "";
/*  751 */                   String PFScom = "";
/*  752 */                   String MCcom = "";
/*  753 */                   String MAPcom = "";
/*  754 */                   String taskVendor = "";
/*      */                   
/*  756 */                   if (tasks != null)
/*      */                   {
/*  758 */                     for (int j = 0; j < tasks.size(); j++) {
/*      */                       
/*  760 */                       task = (ScheduledTask)tasks.get(j);
/*      */                       
/*  762 */                       taskVendor = (task.getVendor() != null) ? task.getVendor() : "";
/*  763 */                       taskVendor = taskVendor.equals("\n") ? "" : taskVendor;
/*      */                       
/*  765 */                       if (task != null) {
/*      */ 
/*      */                         
/*  768 */                         String dueDate = "";
/*  769 */                         if (task.getDueDate() != null) {
/*  770 */                           SimpleDateFormat dueDateFormatter = new SimpleDateFormat("M/d");
/*  771 */                           dueDate = String.valueOf(dueDateFormatter.format(task.getDueDate().getTime())) + " " + MilestoneHelper.getDayType(task.getDueDate());
/*      */                         } 
/*      */                         
/*  774 */                         String completionDate = "";
/*  775 */                         if (task.getCompletionDate() != null) {
/*  776 */                           SimpleDateFormat completionDateFormatter = new SimpleDateFormat("M/d");
/*  777 */                           completionDate = completionDateFormatter.format(task.getCompletionDate().getTime());
/*      */                         } 
/*      */ 
/*      */ 
/*      */                         
/*  782 */                         if (task.getScheduledTaskStatus().equals("N/A"))
/*      */                         {
/*  784 */                           completionDate = task.getScheduledTaskStatus();
/*      */                         }
/*      */                         
/*  787 */                         String taskAbbrev = MilestoneHelper.getTaskAbbreivationNameById(task.getTaskAbbreviationID());
/*      */                         
/*  789 */                         if (taskAbbrev.equalsIgnoreCase("HLD")) {
/*      */                           
/*  791 */                           HLD = dueDate;
/*  792 */                           HLDcom = String.valueOf(completionDate) + "\n\n" + taskVendor;
/*      */                           
/*  794 */                           if (!completionDate.equals("")) {
/*  795 */                             HLD = "Done";
/*  796 */                             if (completionDate.equals("9/9/99")) {
/*  797 */                               HLDcom = "\n\n" + taskVendor;
/*      */                             }
/*      */                           } 
/*      */                         } 
/*      */                         
/*  802 */                         if (taskAbbrev.equalsIgnoreCase("CRD")) {
/*      */                           
/*  804 */                           CRD = dueDate;
/*  805 */                           CRDcom = String.valueOf(completionDate) + "\n\n" + taskVendor;
/*      */                           
/*  807 */                           if (!completionDate.equals("")) {
/*  808 */                             CRD = "Done";
/*  809 */                             if (completionDate.equals("9/9/99")) {
/*  810 */                               CRDcom = "\n\n" + taskVendor;
/*      */                             }
/*      */                           }
/*      */                         
/*      */                         }
/*  815 */                         else if (taskAbbrev.equalsIgnoreCase("CRDA")) {
/*      */                           
/*  817 */                           CRDA = dueDate;
/*  818 */                           CRDAcom = String.valueOf(completionDate) + "\n\n" + taskVendor;
/*      */                           
/*  820 */                           if (!completionDate.equals("")) {
/*  821 */                             CRDA = "Done";
/*  822 */                             if (completionDate.equals("9/9/99")) {
/*  823 */                               CRDAcom = "\n\n" + taskVendor;
/*      */                             }
/*      */                           }
/*      */                         
/*  827 */                         } else if (taskAbbrev.equalsIgnoreCase("F/M")) {
/*      */                           
/*  829 */                           FM = dueDate;
/*  830 */                           FMcom = String.valueOf(completionDate) + "\n\n" + taskVendor;
/*      */                           
/*  832 */                           if (!completionDate.equals("")) {
/*  833 */                             FM = "Done";
/*  834 */                             if (completionDate.equals("9/9/99")) {
/*  835 */                               FMcom = "\n\n" + taskVendor;
/*      */                             }
/*      */                           }
/*      */                         
/*      */                         }
/*  840 */                         else if (taskAbbrev.equalsIgnoreCase("POI")) {
/*      */                           
/*  842 */                           POI = dueDate;
/*  843 */                           POIcom = String.valueOf(completionDate) + "\n\n" + taskVendor;
/*      */                           
/*  845 */                           if (!completionDate.equals("")) {
/*  846 */                             POI = "Done";
/*  847 */                             if (completionDate.equals("9/9/99")) {
/*  848 */                               POIcom = "\n\n" + taskVendor;
/*      */                             }
/*      */                           }
/*      */                         
/*      */                         }
/*  853 */                         else if (taskAbbrev.equalsIgnoreCase("PO")) {
/*      */                           
/*  855 */                           POP = dueDate;
/*  856 */                           POPcom = String.valueOf(completionDate) + "\n\n" + taskVendor;
/*      */                           
/*  858 */                           if (!completionDate.equals("")) {
/*  859 */                             POP = "Done";
/*  860 */                             if (completionDate.equals("9/9/99")) {
/*  861 */                               POPcom = "\n\n" + taskVendor;
/*      */                             }
/*      */                           }
/*      */                         
/*  865 */                         } else if (taskAbbrev.equalsIgnoreCase("TAPE")) {
/*      */                           
/*  867 */                           TAPE = dueDate;
/*  868 */                           TAPEcom = String.valueOf(completionDate) + "\n\n" + taskVendor;
/*      */                           
/*  870 */                           if (!completionDate.equals("")) {
/*  871 */                             TAPE = "Done";
/*  872 */                             if (completionDate.equals("9/9/99")) {
/*  873 */                               TAPEcom = "\n\n" + taskVendor;
/*      */                             }
/*      */                           }
/*      */                         
/*      */                         }
/*  878 */                         else if (taskAbbrev.equalsIgnoreCase("STD")) {
/*      */                           
/*  880 */                           STD = dueDate;
/*  881 */                           STDcom = String.valueOf(completionDate) + "\n\n" + taskVendor;
/*      */                           
/*  883 */                           if (!completionDate.equals("")) {
/*  884 */                             STD = "Done";
/*  885 */                             if (completionDate.equals("9/9/99")) {
/*  886 */                               STDcom = "\n\n" + taskVendor;
/*      */                             
/*      */                             }
/*      */                           }
/*      */                         
/*      */                         }
/*  892 */                         else if (taskAbbrev.equalsIgnoreCase("MBR")) {
/*      */                           
/*  894 */                           MBR = dueDate;
/*  895 */                           MBRcom = String.valueOf(completionDate) + "\n\n" + taskVendor;
/*      */                           
/*  897 */                           if (!completionDate.equals("")) {
/*  898 */                             MBR = "Done";
/*  899 */                             if (completionDate.equals("9/9/99")) {
/*  900 */                               MBRcom = "\n\n" + taskVendor;
/*      */                             }
/*      */                           }
/*      */                         
/*      */                         }
/*  905 */                         else if (taskAbbrev.equalsIgnoreCase("BOM")) {
/*      */                           
/*  907 */                           BOM = dueDate;
/*  908 */                           BOMcom = String.valueOf(completionDate) + "\n\n" + taskVendor;
/*      */                           
/*  910 */                           if (!completionDate.equals("")) {
/*  911 */                             BOM = "Done";
/*  912 */                             if (completionDate.equals("9/9/99")) {
/*  913 */                               BOMcom = "\n\n" + taskVendor;
/*      */                             }
/*      */                           }
/*      */                         
/*      */                         }
/*  918 */                         else if (taskAbbrev.equalsIgnoreCase("SEPS")) {
/*      */                           
/*  920 */                           SEPS = dueDate;
/*  921 */                           SEPScom = String.valueOf(completionDate) + "\n\n" + taskVendor;
/*      */                           
/*  923 */                           if (!completionDate.equals("")) {
/*  924 */                             SEPS = "Done";
/*  925 */                             if (completionDate.equals("9/9/99")) {
/*  926 */                               SEPScom = "\n\n" + taskVendor;
/*      */                             }
/*      */                           }
/*      */                         
/*  930 */                         } else if (taskAbbrev.equalsIgnoreCase("STIC")) {
/*      */                           
/*  932 */                           STIC = dueDate;
/*  933 */                           STICcom = String.valueOf(completionDate) + "\n\n" + taskVendor;
/*      */                           
/*  935 */                           if (!completionDate.equals("")) {
/*  936 */                             STIC = "Done";
/*  937 */                             if (completionDate.equals("9/9/99")) {
/*  938 */                               STICcom = "\n\n" + taskVendor;
/*      */                             
/*      */                             }
/*      */                           }
/*      */                         
/*      */                         }
/*  944 */                         else if (taskAbbrev.equalsIgnoreCase("PFS")) {
/*      */                           
/*  946 */                           PFS = dueDate;
/*  947 */                           PFScom = String.valueOf(completionDate) + "\n\n" + taskVendor;
/*      */                           
/*  949 */                           if (!completionDate.equals("")) {
/*  950 */                             PFS = "Done";
/*  951 */                             if (completionDate.equals("9/9/99")) {
/*  952 */                               PFScom = "\n\n" + taskVendor;
/*      */                             }
/*      */                           }
/*      */                         
/*  956 */                         } else if (taskAbbrev.equalsIgnoreCase("M/C")) {
/*      */                           
/*  958 */                           MC = dueDate;
/*  959 */                           MCcom = String.valueOf(completionDate) + "\n\n" + taskVendor;
/*      */                           
/*  961 */                           if (!completionDate.equals("")) {
/*  962 */                             MC = "Done";
/*  963 */                             if (completionDate.equals("9/9/99")) {
/*  964 */                               MCcom = "\n\n" + taskVendor;
/*      */                             }
/*      */                           } 
/*      */                         } 
/*      */ 
/*      */                         
/*  970 */                         task = null;
/*      */                       } 
/*      */                     } 
/*      */                   }
/*      */ 
/*      */ 
/*      */                   
/*  977 */                   nextRow = 0;
/*  978 */                   subTable = new DefaultTableLens(2, 18);
/*      */ 
/*      */                   
/*  981 */                   subTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
/*      */ 
/*      */ 
/*      */                   
/*  985 */                   subTable.setHeaderRowCount(0);
/*  986 */                   subTable.setColBorderColor(Color.lightGray);
/*  987 */                   subTable.setColWidth(0, 60);
/*  988 */                   subTable.setColWidth(1, 259);
/*  989 */                   subTable.setColWidth(2, 110);
/*  990 */                   subTable.setColWidth(3, 150);
/*  991 */                   subTable.setColWidth(4, 50);
/*  992 */                   subTable.setColWidth(5, 80);
/*  993 */                   subTable.setColWidth(6, 80);
/*  994 */                   subTable.setColWidth(7, 45);
/*  995 */                   subTable.setColWidth(8, 70);
/*  996 */                   subTable.setColWidth(9, 70);
/*  997 */                   subTable.setColWidth(10, 55);
/*  998 */                   subTable.setColWidth(11, 60);
/*  999 */                   subTable.setColWidth(12, 60);
/* 1000 */                   subTable.setColWidth(13, 60);
/* 1001 */                   subTable.setColWidth(14, 60);
/* 1002 */                   subTable.setColWidth(15, 60);
/* 1003 */                   subTable.setColWidth(16, 60);
/* 1004 */                   subTable.setColWidth(17, 60);
/*      */                   
/* 1006 */                   subTable.setRowBorderColor(nextRow - 1, Color.lightGray);
/*      */ 
/*      */                   
/* 1009 */                   if (releaseWeek.equals(previousReleaseWeek)) {
/* 1010 */                     newCycle = false;
/*      */                   } else {
/* 1012 */                     newCycle = true;
/*      */                   } 
/* 1014 */                   previousReleaseWeek = releaseWeek;
/*      */ 
/*      */                   
/* 1017 */                   subTable.setBackground(nextRow, 0, Color.white);
/* 1018 */                   subTable.setAlignment(nextRow, 0, 10);
/* 1019 */                   subTable.setSpan(nextRow, 0, new Dimension(1, 2));
/*      */                   
/* 1021 */                   subTable.setObject(nextRow, 0, dateNameText);
/*      */                   
/* 1023 */                   subTable.setFont(nextRow, 0, new Font("Arial", 0, 7));
/*      */                   
/* 1025 */                   subTable.setObject(nextRow, 1, String.valueOf(artist) + "\n" + title + "\n" + bSide);
/*      */                   
/* 1027 */                   subTable.setBackground(nextRow, 1, Color.white);
/* 1028 */                   subTable.setSpan(nextRow, 1, new Dimension(1, 2));
/*      */                   
/* 1030 */                   subTable.setAlignment(nextRow, 1, 9);
/* 1031 */                   if (sel.getInternationalDate() != null) {
/* 1032 */                     subTable.setFont(nextRow, 1, new Font("Arial", 2, 7));
/*      */                   } else {
/*      */                     
/* 1035 */                     subTable.setFont(nextRow, 1, new Font("Arial", 1, 7));
/*      */                   } 
/*      */                   
/* 1038 */                   String[] check1a = { comment };
/* 1039 */                   int[] check1 = { 16 };
/* 1040 */                   String[] check2a = { artist };
/* 1041 */                   int[] check2 = { 35 };
/* 1042 */                   String[] check3a = { title };
/* 1043 */                   int[] check3 = { 35 };
/* 1044 */                   String[] check4a = { bSide };
/* 1045 */                   int[] check4 = { 35 };
/*      */                   
/* 1047 */                   int extraLines = MilestoneHelper.lineCountWCR(check1a, check1) + MilestoneHelper.lineCountWCR(check2a, check2) + MilestoneHelper.lineCountWCR(check3a, check3) + MilestoneHelper.lineCountWCR(check4a, check4);
/*      */                   
/* 1049 */                   if (PFScom.length() > 8) {
/* 1050 */                     extraLines += 2;
/*      */                   }
/* 1052 */                   if (extraLines == 10) {
/* 1053 */                     extraLines = 9;
/*      */                   }
/* 1055 */                   else if (extraLines > 10) {
/* 1056 */                     extraLines = 7;
/*      */                   } 
/*      */                   
/* 1059 */                   for (int z = 0; z < extraLines; z++) {
/* 1060 */                     filler = String.valueOf(filler) + "\n";
/*      */                   }
/*      */                   
/* 1063 */                   String explicitInd = "";
/* 1064 */                   if (sel.getParentalGuidance()) {
/* 1065 */                     explicitInd = "P.A.Required";
/*      */                   }
/*      */                   
/* 1068 */                   if (!explicitInd.equals("")) {
/* 1069 */                     subTable.setObject(nextRow, 2, String.valueOf(selSubConfig) + "\n" + prefix + selectionID + "\n" + upc + "\n" + explicitInd);
/*      */                   } else {
/* 1071 */                     subTable.setObject(nextRow, 2, String.valueOf(selSubConfig) + "\n" + prefix + selectionID + "\n" + upc);
/*      */                   } 
/*      */                   
/* 1074 */                   subTable.setRowBorderColor(nextRow, SHADED_AREA_COLOR);
/*      */ 
/*      */                   
/* 1077 */                   subTable.setAlignment(nextRow, 2, 10);
/* 1078 */                   subTable.setBackground(nextRow, 2, Color.white);
/* 1079 */                   subTable.setRowBorderColor(nextRow, 2, Color.white);
/* 1080 */                   subTable.setSpan(nextRow, 2, new Dimension(1, 2));
/*      */                   
/* 1082 */                   subTable.setSpan(nextRow, 3, new Dimension(1, 2));
/* 1083 */                   subTable.setRowBorderColor(nextRow, 3, Color.white);
/*      */                   
/* 1085 */                   subTable.setColBorderColor(nextRow, 3, SHADED_AREA_COLOR);
/* 1086 */                   subTable.setBackground(nextRow, 3, Color.white);
/*      */                   
/* 1088 */                   subTable.setColBorder(nextRow, 3, 266240);
/*      */ 
/*      */ 
/*      */                   
/* 1092 */                   Vector multContacts = sel.getMultOtherContacts();
/* 1093 */                   int numberOfContacts = multContacts.size();
/* 1094 */                   String marketingDirector = "";
/* 1095 */                   String artDirector = "";
/* 1096 */                   String AandRContact = "";
/*      */                   
/* 1098 */                   switch (numberOfContacts) {
/*      */                     case 1:
/* 1100 */                       marketingDirector = ((MultOtherContact)multContacts.get(0)).getName();
/*      */                       break;
/*      */                     case 2:
/* 1103 */                       marketingDirector = ((MultOtherContact)multContacts.get(0)).getName();
/* 1104 */                       artDirector = ((MultOtherContact)multContacts.get(1)).getName();
/*      */                       break;
/*      */                     case 3:
/* 1107 */                       marketingDirector = ((MultOtherContact)multContacts.get(0)).getName();
/* 1108 */                       artDirector = ((MultOtherContact)multContacts.get(1)).getName();
/* 1109 */                       AandRContact = ((MultOtherContact)multContacts.get(2)).getName();
/*      */                       break;
/*      */                   } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 1118 */                   subTable.setAlignment(nextRow, 3, 10);
/* 1119 */                   subTable.setObject(nextRow, 3, String.valueOf(marketingDirector) + "\n" + artDirector + "\n" + AandRContact);
/*      */ 
/*      */                   
/* 1122 */                   subTable.setAlignment(nextRow, 4, 10);
/* 1123 */                   subTable.setAlignment(nextRow + 1, 4, 10);
/*      */                   
/* 1125 */                   subTable.setRowHeight(nextRow, 9);
/* 1126 */                   subTable.setRowBorderColor(nextRow + 1, SHADED_AREA_COLOR);
/*      */                   
/* 1128 */                   subTable.setObject(nextRow, 4, HLD);
/* 1129 */                   subTable.setColBorderColor(nextRow, 4, SHADED_AREA_COLOR);
/* 1130 */                   subTable.setColBorder(nextRow, 4, 266240);
/*      */                   
/* 1132 */                   subTable.setObject(nextRow, 5, CRD);
/* 1133 */                   subTable.setColBorderColor(nextRow, 5, SHADED_AREA_COLOR);
/* 1134 */                   subTable.setColBorder(nextRow, 5, 266240);
/*      */                   
/* 1136 */                   subTable.setObject(nextRow, 6, CRDA);
/* 1137 */                   subTable.setColBorderColor(nextRow, 6, SHADED_AREA_COLOR);
/* 1138 */                   subTable.setColBorder(nextRow, 6, 266240);
/*      */                   
/* 1140 */                   subTable.setObject(nextRow, 7, FM);
/* 1141 */                   subTable.setColBorderColor(nextRow, 7, SHADED_AREA_COLOR);
/* 1142 */                   subTable.setColBorder(nextRow, 7, 266240);
/*      */                   
/* 1144 */                   subTable.setObject(nextRow, 8, POI);
/* 1145 */                   subTable.setColBorderColor(nextRow, 8, Color.lightGray);
/* 1146 */                   subTable.setColBorder(nextRow, 8, 266240);
/*      */                   
/* 1148 */                   subTable.setObject(nextRow, 9, POP);
/* 1149 */                   subTable.setColBorderColor(nextRow, 9, SHADED_AREA_COLOR);
/* 1150 */                   subTable.setColBorder(nextRow, 9, 266240);
/*      */                   
/* 1152 */                   subTable.setObject(nextRow, 10, TAPE);
/* 1153 */                   subTable.setColBorderColor(nextRow, 10, SHADED_AREA_COLOR);
/* 1154 */                   subTable.setColBorder(nextRow, 10, 266240);
/*      */                   
/* 1156 */                   subTable.setObject(nextRow, 11, STD);
/* 1157 */                   subTable.setColBorderColor(nextRow, 11, SHADED_AREA_COLOR);
/* 1158 */                   subTable.setColBorder(nextRow, 11, 266240);
/*      */                   
/* 1160 */                   subTable.setObject(nextRow, 12, MBR);
/* 1161 */                   subTable.setColBorderColor(nextRow, 12, SHADED_AREA_COLOR);
/* 1162 */                   subTable.setColBorder(nextRow, 12, 266240);
/*      */                   
/* 1164 */                   subTable.setObject(nextRow, 13, BOM);
/* 1165 */                   subTable.setColBorderColor(nextRow, 13, Color.lightGray);
/* 1166 */                   subTable.setColBorder(nextRow, 13, 266240);
/*      */                   
/* 1168 */                   subTable.setObject(nextRow, 14, SEPS);
/* 1169 */                   subTable.setColBorderColor(nextRow, 14, SHADED_AREA_COLOR);
/* 1170 */                   subTable.setColBorder(nextRow, 14, 266240);
/*      */                   
/* 1172 */                   subTable.setObject(nextRow, 15, STIC);
/* 1173 */                   subTable.setColBorderColor(nextRow, 15, SHADED_AREA_COLOR);
/* 1174 */                   subTable.setColBorder(nextRow, 15, 266240);
/*      */                   
/* 1176 */                   subTable.setObject(nextRow, 16, PFS);
/* 1177 */                   subTable.setColBorderColor(nextRow, 16, SHADED_AREA_COLOR);
/* 1178 */                   subTable.setColBorder(nextRow, 16, 266240);
/*      */                   
/* 1180 */                   subTable.setObject(nextRow, 17, MC);
/* 1181 */                   subTable.setColBorderColor(nextRow, 17, SHADED_AREA_COLOR);
/* 1182 */                   subTable.setColBorder(nextRow, 17, 266240);
/*      */ 
/*      */                   
/* 1185 */                   subTable.setFont(nextRow, 4, new Font("Arial", 1, 7));
/* 1186 */                   subTable.setFont(nextRow, 5, new Font("Arial", 1, 7));
/* 1187 */                   subTable.setFont(nextRow, 6, new Font("Arial", 1, 7));
/* 1188 */                   subTable.setFont(nextRow, 7, new Font("Arial", 1, 7));
/* 1189 */                   subTable.setFont(nextRow, 8, new Font("Arial", 1, 7));
/* 1190 */                   subTable.setFont(nextRow, 9, new Font("Arial", 1, 7));
/* 1191 */                   subTable.setFont(nextRow, 10, new Font("Arial", 1, 7));
/* 1192 */                   subTable.setFont(nextRow, 11, new Font("Arial", 1, 7));
/* 1193 */                   subTable.setFont(nextRow, 12, new Font("Arial", 1, 7));
/* 1194 */                   subTable.setFont(nextRow, 13, new Font("Arial", 1, 7));
/* 1195 */                   subTable.setFont(nextRow, 14, new Font("Arial", 1, 7));
/* 1196 */                   subTable.setFont(nextRow, 15, new Font("Arial", 1, 7));
/* 1197 */                   subTable.setFont(nextRow, 16, new Font("Arial", 1, 7));
/* 1198 */                   subTable.setFont(nextRow, 17, new Font("Arial", 1, 7));
/*      */ 
/*      */                   
/* 1201 */                   subTable.setAlignment(nextRow, 4, 2);
/* 1202 */                   subTable.setAlignment(nextRow, 5, 2);
/* 1203 */                   subTable.setAlignment(nextRow, 6, 2);
/* 1204 */                   subTable.setAlignment(nextRow, 7, 2);
/* 1205 */                   subTable.setAlignment(nextRow, 8, 2);
/* 1206 */                   subTable.setAlignment(nextRow, 9, 2);
/* 1207 */                   subTable.setAlignment(nextRow, 10, 2);
/* 1208 */                   subTable.setAlignment(nextRow, 11, 2);
/* 1209 */                   subTable.setAlignment(nextRow, 12, 2);
/* 1210 */                   subTable.setAlignment(nextRow, 13, 2);
/* 1211 */                   subTable.setAlignment(nextRow, 14, 2);
/* 1212 */                   subTable.setAlignment(nextRow, 15, 2);
/* 1213 */                   subTable.setAlignment(nextRow, 16, 2);
/* 1214 */                   subTable.setAlignment(nextRow, 17, 2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 1222 */                   subTable.setObject(nextRow + 1, 4, HLDcom);
/* 1223 */                   subTable.setObject(nextRow + 1, 5, CRDcom);
/* 1224 */                   subTable.setObject(nextRow + 1, 6, CRDAcom);
/* 1225 */                   subTable.setObject(nextRow + 1, 7, FMcom);
/* 1226 */                   subTable.setObject(nextRow + 1, 8, POIcom);
/* 1227 */                   subTable.setObject(nextRow + 1, 9, POPcom);
/* 1228 */                   subTable.setObject(nextRow + 1, 10, TAPEcom);
/* 1229 */                   subTable.setObject(nextRow + 1, 11, STDcom);
/* 1230 */                   subTable.setObject(nextRow + 1, 12, MBRcom);
/* 1231 */                   subTable.setObject(nextRow + 1, 13, BOMcom);
/* 1232 */                   subTable.setObject(nextRow + 1, 14, SEPScom);
/* 1233 */                   subTable.setObject(nextRow + 1, 15, STICcom);
/* 1234 */                   subTable.setObject(nextRow + 1, 16, PFScom);
/* 1235 */                   subTable.setObject(nextRow + 1, 17, MCcom);
/*      */ 
/*      */                   
/* 1238 */                   subTable.setAlignment(nextRow + 1, 4, 10);
/* 1239 */                   subTable.setAlignment(nextRow + 1, 5, 10);
/* 1240 */                   subTable.setAlignment(nextRow + 1, 6, 10);
/* 1241 */                   subTable.setAlignment(nextRow + 1, 7, 10);
/* 1242 */                   subTable.setAlignment(nextRow + 1, 8, 10);
/* 1243 */                   subTable.setAlignment(nextRow + 1, 9, 10);
/* 1244 */                   subTable.setAlignment(nextRow + 1, 10, 10);
/* 1245 */                   subTable.setAlignment(nextRow + 1, 11, 10);
/* 1246 */                   subTable.setAlignment(nextRow + 1, 12, 10);
/* 1247 */                   subTable.setAlignment(nextRow + 1, 13, 10);
/* 1248 */                   subTable.setAlignment(nextRow + 1, 14, 10);
/* 1249 */                   subTable.setAlignment(nextRow + 1, 15, 10);
/* 1250 */                   subTable.setAlignment(nextRow + 1, 16, 10);
/* 1251 */                   subTable.setAlignment(nextRow + 1, 17, 10);
/*      */ 
/*      */                   
/* 1254 */                   subTable.setRowFont(nextRow + 1, new Font("Arial", 0, 7));
/*      */                   
/* 1256 */                   subTable.setRowBackground(nextRow, SHADED_AREA_COLOR);
/* 1257 */                   subTable.setRowForeground(nextRow, Color.black);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 1267 */                   subTable.setRowFont(nextRow, new Font("Arial", 0, 7));
/*      */                   
/* 1269 */                   body = new SectionBand(report);
/*      */                   
/* 1271 */                   double lfLineCount = 1.5D;
/*      */                   
/* 1273 */                   if (extraLines > 0) {
/*      */                     
/* 1275 */                     body.setHeight(1.0F);
/*      */                   }
/*      */                   else {
/*      */                     
/* 1279 */                     body.setHeight(1.0F);
/*      */                   } 
/*      */                   
/* 1282 */                   if (extraLines > 3 || 
/* 1283 */                     HLDcom.length() > 10 || CRDcom.length() > 10 || 
/* 1284 */                     CRDAcom.length() > 10 || FMcom.length() > 10 || 
/* 1285 */                     POIcom.length() > 10 || POPcom.length() > 10 || 
/* 1286 */                     TAPEcom.length() > 10 || STDcom.length() > 10 || 
/* 1287 */                     SEPScom.length() > 10 || STICcom.length() > 10 || 
/* 1288 */                     PFScom.length() > 10 || MCcom.length() > 10 || 
/* 1289 */                     MBRcom.length() > 10 || BOMcom.length() > 10) {
/*      */ 
/*      */                     
/* 1292 */                     if (lfLineCount < extraLines * 0.3D) {
/* 1293 */                       lfLineCount = extraLines * 0.3D;
/*      */                     }
/* 1295 */                     if (lfLineCount < (HLDcom.length() / 7) * 0.3D) {
/* 1296 */                       lfLineCount = (HLDcom.length() / 7) * 0.3D;
/*      */                     }
/* 1298 */                     if (lfLineCount < (STICcom.length() / 8) * 0.3D) {
/* 1299 */                       lfLineCount = (STICcom.length() / 8) * 0.3D;
/*      */                     }
/* 1301 */                     if (lfLineCount < (BOMcom.length() / 8) * 0.3D) {
/* 1302 */                       lfLineCount = (BOMcom.length() / 8) * 0.3D;
/*      */                     }
/* 1304 */                     if (lfLineCount < (FMcom.length() / 8) * 0.3D) {
/* 1305 */                       lfLineCount = (FMcom.length() / 8) * 0.3D;
/*      */                     }
/* 1307 */                     if (lfLineCount < (MCcom.length() / 8) * 0.3D) {
/* 1308 */                       lfLineCount = (MCcom.length() / 8) * 0.3D;
/*      */                     }
/* 1310 */                     if (lfLineCount < (CRDcom.length() / 8) * 0.3D) {
/* 1311 */                       lfLineCount = (CRDcom.length() / 8) * 0.3D;
/*      */                     }
/* 1313 */                     if (lfLineCount < (CRDAcom.length() / 8) * 0.3D) {
/* 1314 */                       lfLineCount = (CRDAcom.length() / 8) * 0.3D;
/*      */                     }
/* 1316 */                     if (lfLineCount < (POIcom.length() / 8) * 0.3D) {
/* 1317 */                       lfLineCount = (POIcom.length() / 8) * 0.3D;
/*      */                     }
/* 1319 */                     if (lfLineCount < (POPcom.length() / 8) * 0.3D) {
/* 1320 */                       lfLineCount = (POPcom.length() / 8) * 0.3D;
/*      */                     }
/* 1322 */                     if (lfLineCount < (TAPEcom.length() / 8) * 0.3D) {
/* 1323 */                       lfLineCount = (TAPEcom.length() / 8) * 0.3D;
/*      */                     }
/* 1325 */                     if (lfLineCount < (STDcom.length() / 8) * 0.3D) {
/* 1326 */                       lfLineCount = (STDcom.length() / 8) * 0.3D;
/*      */                     }
/* 1328 */                     if (lfLineCount < (MBRcom.length() / 8) * 0.3D) {
/* 1329 */                       lfLineCount = (MBRcom.length() / 8) * 0.3D;
/*      */                     }
/* 1331 */                     if (lfLineCount < (SEPScom.length() / 8) * 0.3D) {
/* 1332 */                       lfLineCount = (SEPScom.length() / 8) * 0.3D;
/*      */                     }
/* 1334 */                     if (lfLineCount < (PFScom.length() / 8) * 0.3D) {
/* 1335 */                       lfLineCount = (PFScom.length() / 8) * 0.3D;
/*      */                     }
/*      */                     
/* 1338 */                     body.setHeight((float)lfLineCount);
/*      */                   }
/*      */                   else {
/*      */                     
/* 1342 */                     body.setHeight(1.0F);
/*      */                   } 
/*      */ 
/*      */                   
/* 1346 */                   body.addTable(subTable, new Rectangle(800, 800));
/* 1347 */                   body.setBottomBorder(0);
/* 1348 */                   body.setTopBorder(0);
/* 1349 */                   body.setShrinkToFit(true);
/* 1350 */                   body.setVisible(true);
/*      */                   
/* 1352 */                   if (newCycle) {
/* 1353 */                     group = new DefaultSectionLens(null, group, spacer);
/*      */                   }
/*      */                   
/* 1356 */                   group = new DefaultSectionLens(null, group, body);
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
/* 1367 */         group = new DefaultSectionLens(hbandType, group, null);
/* 1368 */         report.addSection(group, rowCountTable);
/* 1369 */         report.addPageBreak();
/* 1370 */         group = null;
/*      */ 
/*      */       
/*      */       }
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 1378 */     catch (Exception e) {
/*      */       
/* 1380 */       System.out.println(">>>>>>>>McaProductionScheduleForPrintSubHandler(): exception: " + e);
/*      */     } 
/*      */   }
/*      */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\NewReleasePlanningScheduleForPrintSubHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */