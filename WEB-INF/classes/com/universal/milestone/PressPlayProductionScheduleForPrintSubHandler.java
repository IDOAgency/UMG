/*      */ package WEB-INF.classes.com.universal.milestone;
/*      */ 
/*      */ import com.techempower.ComponentLog;
/*      */ import com.techempower.gemini.Context;
/*      */ import com.techempower.gemini.GeminiApplication;
/*      */ import com.universal.milestone.Form;
/*      */ import com.universal.milestone.MilestoneHelper;
/*      */ import com.universal.milestone.MonthYearComparator;
/*      */ import com.universal.milestone.PressPlayProductionScheduleForPrintSubHandler;
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
/*      */ 
/*      */ public class PressPlayProductionScheduleForPrintSubHandler
/*      */   extends SecureHandler
/*      */ {
/*      */   public static final String COMPONENT_CODE = "hPsp";
/*      */   public GeminiApplication application;
/*      */   public ComponentLog log;
/*      */   
/*      */   public PressPlayProductionScheduleForPrintSubHandler(GeminiApplication application) {
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
/*      */   protected static void fillPressPlayProductionScheduleForPrint(XStyleSheet report, Context context) {
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
/*  121 */     int NUM_COLUMNS = 14;
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
/*  278 */         table_contents = new DefaultTableLens(1, 14);
/*      */ 
/*      */         
/*  281 */         table_contents.setHeaderRowCount(0);
/*  282 */         table_contents.setColWidth(0, 77);
/*  283 */         table_contents.setColWidth(1, 259);
/*  284 */         table_contents.setColWidth(2, 157);
/*  285 */         table_contents.setColWidth(3, 150);
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
/*      */         
/*  297 */         table_contents.setColBorderColor(Color.black);
/*  298 */         table_contents.setRowBorderColor(Color.black);
/*  299 */         table_contents.setRowBorder(4097);
/*  300 */         table_contents.setColBorder(4097);
/*      */ 
/*      */         
/*  303 */         int nextRow = 0;
/*      */ 
/*      */         
/*  306 */         String configHeaderText = !config.trim().equals("") ? config : "Other";
/*      */ 
/*      */         
/*  309 */         if (configHeaderText != null)
/*      */         {
/*  311 */           if (configHeaderText.startsWith("Commercial CD Single")) {
/*  312 */             configHeaderText = "Commercial Singles";
/*      */           }
/*  314 */           else if (configHeaderText.startsWith("Promotional CD Single")) {
/*  315 */             configHeaderText = "Promos Singles";
/*      */           } 
/*      */         }
/*      */ 
/*      */         
/*  320 */         table_contents.setSpan(nextRow, 0, new Dimension(14, 1));
/*  321 */         table_contents.setAlignment(nextRow, 0, 2);
/*  322 */         table_contents.setObject(nextRow, 0, configHeaderText);
/*  323 */         table_contents.setRowHeight(nextRow, 16);
/*      */         
/*  325 */         table_contents.setRowBorderColor(nextRow, Color.black);
/*  326 */         table_contents.setRowBorder(nextRow, 0, 266240);
/*      */         
/*  328 */         table_contents.setRowFont(nextRow, new Font("Arial", 3, 12));
/*  329 */         table_contents.setRowBackground(nextRow, Color.white);
/*  330 */         table_contents.setRowForeground(nextRow, Color.black);
/*      */         
/*  332 */         table_contents.setRowBorder(nextRow - 1, 266240);
/*  333 */         table_contents.setColBorder(nextRow, -1, 266240);
/*  334 */         table_contents.setColBorder(nextRow, 0, 266240);
/*  335 */         table_contents.setColBorder(nextRow, 13, 266240);
/*  336 */         table_contents.setColBorder(nextRow, 14, 266240);
/*  337 */         table_contents.setRowBorder(nextRow, 266240);
/*      */         
/*  339 */         table_contents.setRowBorderColor(nextRow - 1, Color.black);
/*  340 */         table_contents.setColBorderColor(nextRow, -1, Color.black);
/*  341 */         table_contents.setColBorderColor(nextRow, 0, Color.black);
/*  342 */         table_contents.setColBorderColor(nextRow, 13, Color.black);
/*  343 */         table_contents.setColBorderColor(nextRow, 14, Color.black);
/*  344 */         table_contents.setRowBorderColor(nextRow, Color.black);
/*      */ 
/*      */         
/*  347 */         hbandType.addTable(table_contents, new Rectangle(0, 0, 800, 25));
/*      */         
/*  349 */         nextRow = 0;
/*      */         
/*  351 */         columnHeaderTable = new DefaultTableLens(1, 14);
/*      */         
/*  353 */         columnHeaderTable.setHeaderRowCount(0);
/*  354 */         columnHeaderTable.setColWidth(0, 77);
/*  355 */         columnHeaderTable.setColWidth(1, 259);
/*  356 */         columnHeaderTable.setColWidth(2, 157);
/*  357 */         columnHeaderTable.setColWidth(3, 150);
/*  358 */         columnHeaderTable.setColWidth(4, 168);
/*  359 */         columnHeaderTable.setColWidth(5, 87);
/*  360 */         columnHeaderTable.setColWidth(6, 84);
/*  361 */         columnHeaderTable.setColWidth(7, 70);
/*  362 */         columnHeaderTable.setColWidth(8, 80);
/*  363 */         columnHeaderTable.setColWidth(9, 72);
/*  364 */         columnHeaderTable.setColWidth(10, 90);
/*  365 */         columnHeaderTable.setColWidth(11, 70);
/*  366 */         columnHeaderTable.setColWidth(12, 90);
/*  367 */         columnHeaderTable.setColWidth(13, 77);
/*      */         
/*  369 */         columnHeaderTable.setAlignment(nextRow, 0, 33);
/*  370 */         columnHeaderTable.setAlignment(nextRow, 1, 33);
/*  371 */         columnHeaderTable.setAlignment(nextRow, 2, 34);
/*  372 */         columnHeaderTable.setAlignment(nextRow, 3, 34);
/*  373 */         columnHeaderTable.setAlignment(nextRow, 4, 34);
/*  374 */         columnHeaderTable.setAlignment(nextRow, 5, 34);
/*  375 */         columnHeaderTable.setAlignment(nextRow, 6, 34);
/*  376 */         columnHeaderTable.setAlignment(nextRow, 7, 34);
/*  377 */         columnHeaderTable.setAlignment(nextRow, 8, 34);
/*  378 */         columnHeaderTable.setAlignment(nextRow, 9, 34);
/*  379 */         columnHeaderTable.setAlignment(nextRow, 10, 34);
/*  380 */         columnHeaderTable.setAlignment(nextRow, 11, 34);
/*  381 */         columnHeaderTable.setAlignment(nextRow, 12, 34);
/*  382 */         columnHeaderTable.setAlignment(nextRow, 13, 34);
/*      */         
/*  384 */         columnHeaderTable.setObject(nextRow, 0, "Release Date");
/*  385 */         columnHeaderTable.setObject(nextRow, 1, "Artist/Title");
/*  386 */         columnHeaderTable.setObject(nextRow, 2, "Volume");
/*  387 */         columnHeaderTable.setObject(nextRow, 3, "UPC\nConfig");
/*  388 */         columnHeaderTable.setObject(nextRow, 4, "Label & Contacts");
/*  389 */         columnHeaderTable.setObject(nextRow, 5, "PP\nList Due");
/*  390 */         columnHeaderTable.setObject(nextRow, 6, "Vol.\nCreated");
/*  391 */         columnHeaderTable.setObject(nextRow, 7, "Title\nRouted");
/*  392 */         columnHeaderTable.setObject(nextRow, 8, "Title\nto LE");
/*  393 */         columnHeaderTable.setObject(nextRow, 9, "CDs\nat LE");
/*  394 */         columnHeaderTable.setObject(nextRow, 10, "Research\nDue");
/*  395 */         columnHeaderTable.setObject(nextRow, 11, "Append\nTitles");
/*  396 */         columnHeaderTable.setObject(nextRow, 12, "Exception\nList\nTo PP");
/*  397 */         columnHeaderTable.setObject(nextRow, 13, "Encoded\nFiles\nTo PP");
/*      */ 
/*      */         
/*  400 */         setColBorderColor(columnHeaderTable, nextRow, 14, SHADED_AREA_COLOR);
/*      */         
/*  402 */         columnHeaderTable.setRowBorderColor(nextRow - 1, SHADED_AREA_COLOR);
/*  403 */         columnHeaderTable.setRowBorderColor(nextRow, SHADED_AREA_COLOR);
/*  404 */         columnHeaderTable.setRowFont(nextRow, new Font("Arial", 1, 8));
/*  405 */         columnHeaderTable.setRowBackground(nextRow, Color.white);
/*  406 */         columnHeaderTable.setRowForeground(nextRow, Color.black);
/*      */ 
/*      */         
/*  409 */         hbandType.addTable(columnHeaderTable, new Rectangle(0, 24, 800, 39));
/*  410 */         hbandType.setBottomBorder(0);
/*      */ 
/*      */         
/*  413 */         if (monthTable != null) {
/*      */           
/*  415 */           Enumeration months = monthTable.keys();
/*      */           
/*  417 */           Vector monthVector = new Vector();
/*      */           
/*  419 */           while (months.hasMoreElements()) {
/*  420 */             monthVector.add((String)months.nextElement());
/*      */           }
/*  422 */           Object[] monthArray = null;
/*  423 */           monthArray = monthVector.toArray();
/*      */           
/*  425 */           Arrays.sort(monthArray, new MonthYearComparator());
/*      */           
/*  427 */           for (int x = 0; x < monthArray.length; x++) {
/*      */ 
/*      */             
/*  430 */             String monthName = (String)monthArray[x];
/*  431 */             String monthNameString = monthName;
/*      */             
/*      */             try {
/*  434 */               monthNameString = MONTHS[Integer.parseInt(monthName.substring(0, 2)) - 1];
/*      */             }
/*  436 */             catch (Exception e) {
/*      */               
/*  438 */               if (monthName.equals("13")) {
/*  439 */                 monthNameString = "TBS";
/*  440 */               } else if (monthName.equals("26")) {
/*  441 */                 monthNameString = "ITW";
/*      */               } else {
/*  443 */                 monthNameString = "No street date";
/*      */               } 
/*      */             } 
/*  446 */             monthTableLens = new DefaultTableLens(1, 14);
/*  447 */             hbandCategory = new SectionBand(report);
/*  448 */             hbandCategory.setHeight(0.25F);
/*  449 */             hbandCategory.setShrinkToFit(true);
/*  450 */             hbandCategory.setVisible(true);
/*  451 */             hbandCategory.setBottomBorder(0);
/*  452 */             hbandCategory.setLeftBorder(0);
/*  453 */             hbandCategory.setRightBorder(0);
/*  454 */             hbandCategory.setTopBorder(0);
/*      */             
/*  456 */             nextRow = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  461 */             monthTableLens.setSpan(nextRow, 0, new Dimension(14, 1));
/*  462 */             monthTableLens.setObject(nextRow, 0, monthNameString);
/*  463 */             monthTableLens.setRowHeight(nextRow, 14);
/*  464 */             monthTableLens.setRowFont(nextRow, new Font("Arial", 1, 12));
/*  465 */             monthTableLens.setRowBackground(nextRow, SHADED_AREA_COLOR);
/*  466 */             monthTableLens.setRowForeground(nextRow, Color.black);
/*  467 */             monthTableLens.setRowBorderColor(nextRow, Color.white);
/*  468 */             monthTableLens.setRowBorderColor(nextRow - 1, Color.white);
/*  469 */             monthTableLens.setColBorderColor(nextRow, -1, Color.white);
/*  470 */             monthTableLens.setColBorderColor(nextRow, 0, Color.white);
/*  471 */             monthTableLens.setColBorderColor(nextRow, 13, Color.white);
/*      */             
/*  473 */             hbandCategory.addTable(monthTableLens, new Rectangle(800, 800));
/*      */             
/*  475 */             footer.setVisible(true);
/*  476 */             footer.setHeight(0.1F);
/*  477 */             footer.setShrinkToFit(false);
/*  478 */             footer.setBottomBorder(0);
/*      */             
/*  480 */             group = new DefaultSectionLens(null, group, spacer);
/*  481 */             group = new DefaultSectionLens(null, group, hbandCategory);
/*  482 */             group = new DefaultSectionLens(null, group, spacer);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  488 */             Hashtable dateTable = (Hashtable)monthTable.get(monthName);
/*  489 */             if (dateTable != null) {
/*      */               
/*  491 */               Enumeration dateSort = dateTable.keys();
/*      */               
/*  493 */               Vector dateVector = new Vector();
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  498 */               while (dateSort.hasMoreElements()) {
/*  499 */                 dateVector.add((String)dateSort.nextElement());
/*      */               }
/*  501 */               Object[] dateArray = null;
/*      */               
/*  503 */               dateArray = dateVector.toArray();
/*  504 */               Arrays.sort(dateArray, new StringDateComparator());
/*      */               
/*  506 */               for (int dIndex = 0; dIndex < dateArray.length; dIndex++) {
/*      */ 
/*      */                 
/*  509 */                 String dateName = (String)dateArray[dIndex];
/*  510 */                 String dateNameText = dateName;
/*      */                 
/*  512 */                 if (monthNameString.equalsIgnoreCase("TBS")) {
/*      */                   
/*  514 */                   dateNameText = "TBS " + dateName;
/*      */                 }
/*  516 */                 else if (monthNameString.equalsIgnoreCase("ITW")) {
/*      */                   
/*  518 */                   dateNameText = "ITW " + dateName;
/*      */                 } 
/*      */ 
/*      */                 
/*  522 */                 nextRow = 0;
/*      */                 
/*  524 */                 selections = (Vector)dateTable.get(dateName);
/*  525 */                 if (selections == null) {
/*  526 */                   selections = new Vector();
/*      */                 }
/*      */                 
/*  529 */                 MilestoneHelper.setSelectionSorting(selections, 5);
/*  530 */                 Collections.sort(selections);
/*      */ 
/*      */                 
/*  533 */                 MilestoneHelper.setSelectionSorting(selections, 4);
/*  534 */                 Collections.sort(selections);
/*      */ 
/*      */                 
/*  537 */                 MilestoneHelper.setSelectionSorting(selections, 3);
/*  538 */                 Collections.sort(selections);
/*      */ 
/*      */                 
/*  541 */                 MilestoneHelper.setSelectionSorting(selections, 21);
/*  542 */                 Collections.sort(selections);
/*      */ 
/*      */                 
/*  545 */                 for (int i = 0; i < selections.size(); i++) {
/*      */                   
/*  547 */                   Selection sel = (Selection)selections.elementAt(i);
/*      */                   
/*  549 */                   if (count < recordCount / tenth) {
/*      */                     
/*  551 */                     count = recordCount / tenth;
/*  552 */                     sresponse = context.getResponse();
/*  553 */                     context.putDelivery("status", new String("start_report"));
/*  554 */                     int myPercent = count * 10;
/*  555 */                     if (myPercent > 90)
/*  556 */                       myPercent = 90; 
/*  557 */                     context.putDelivery("percent", new String(String.valueOf(myPercent)));
/*  558 */                     context.includeJSP("status.jsp", "hiddenFrame");
/*  559 */                     sresponse.setContentType("text/plain");
/*  560 */                     sresponse.flushBuffer();
/*      */                   } 
/*  562 */                   recordCount++;
/*  563 */                   System.out.println(">>>>>>>>>>>>>>>>>>>>   tenth = " + tenth);
/*  564 */                   System.out.println(">>>>>>>>>>>>>>>>>>>>   count = " + count);
/*  565 */                   System.out.println(">>>>>>>>>>>>>>>>>>>>   recordCount = " + recordCount);
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  570 */                   String titleId = "";
/*  571 */                   titleId = sel.getTitleID();
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  576 */                   String artist = "";
/*  577 */                   artist = sel.getArtist().trim();
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  582 */                   String comment = "";
/*  583 */                   String commentStr = (sel.getSelectionComments() != null) ? sel.getSelectionComments() : "";
/*      */                   
/*  585 */                   String newComment = removeLF(commentStr, 800);
/*  586 */                   int subTableRows = 2;
/*  587 */                   if (newComment.length() > 0) {
/*  588 */                     subTableRows = 3;
/*      */                   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  596 */                   String label = "";
/*  597 */                   if (sel.getLabel() != null) {
/*  598 */                     label = sel.getLabel().getName();
/*      */                   }
/*      */ 
/*      */ 
/*      */                   
/*  603 */                   String pack = "";
/*  604 */                   pack = sel.getSelectionPackaging();
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  609 */                   String title = "";
/*  610 */                   if (sel.getTitle() != null) {
/*  611 */                     title = sel.getTitle();
/*      */                   }
/*      */ 
/*      */ 
/*      */                   
/*  616 */                   String upc = "";
/*  617 */                   upc = sel.getUpc();
/*      */                   
/*  619 */                   if (upc != null && upc.length() == 0) {
/*  620 */                     upc = titleId;
/*      */                   }
/*      */ 
/*      */ 
/*      */                   
/*  625 */                   String selConfig = "";
/*  626 */                   selConfig = (sel.getSelectionConfig() != null) ? sel.getSelectionConfig().getSelectionConfigurationAbbreviation() : "";
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  631 */                   String units = "";
/*  632 */                   units = (sel.getNumberOfUnits() > 0) ? String.valueOf(sel.getNumberOfUnits()) : "";
/*      */                   
/*  634 */                   String code = (sel.getSellCode() != null) ? sel.getSellCode() : "";
/*  635 */                   if (code != null && code.startsWith("-1")) {
/*  636 */                     code = "";
/*      */                   }
/*  638 */                   String retail = "";
/*  639 */                   if (sel.getPriceCode() != null && sel.getPriceCode().getRetailCode() != null) {
/*  640 */                     retail = sel.getPriceCode().getRetailCode();
/*      */                   }
/*  642 */                   if (code.length() > 0) {
/*  643 */                     retail = "/" + retail;
/*      */                   }
/*  645 */                   String price = "";
/*  646 */                   if (sel.getPriceCode() != null && sel.getPriceCode().getTotalCost() > 0.0F) {
/*  647 */                     price = "$" + MilestoneHelper.formatDollarPrice(sel.getPriceCode().getTotalCost());
/*      */                   }
/*      */ 
/*      */ 
/*      */                   
/*  652 */                   String contact = "";
/*  653 */                   contact = (sel.getLabelContact() != null) ? sel.getLabelContact().getName() : "";
/*      */ 
/*      */ 
/*      */                   
/*  657 */                   String otherContact = "";
/*  658 */                   otherContact = (sel.getOtherContact() != null) ? sel.getOtherContact() : "";
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  663 */                   Schedule schedule = sel.getSchedule();
/*      */                   
/*  665 */                   Vector tasks = (schedule != null) ? schedule.getTasks() : null;
/*  666 */                   ScheduledTask task = null;
/*      */                   
/*  668 */                   String PLD = "";
/*  669 */                   String VC = "";
/*  670 */                   String VR = "";
/*  671 */                   String LTL = "";
/*  672 */                   String CAI = "";
/*  673 */                   String RD = "";
/*  674 */                   String AT = "";
/*  675 */                   String ELP = "";
/*  676 */                   String EFF = "";
/*      */                   
/*  678 */                   String PLDcom = "";
/*  679 */                   String VCcom = "";
/*  680 */                   String VRcom = "";
/*  681 */                   String LTLcom = "";
/*  682 */                   String CAIcom = "";
/*  683 */                   String RDcom = "";
/*  684 */                   String ATcom = "";
/*  685 */                   String ELPcom = "";
/*  686 */                   String EFFcom = "";
/*      */                   
/*  688 */                   if (tasks != null)
/*      */                   {
/*  690 */                     for (int j = 0; j < tasks.size(); j++) {
/*      */                       
/*  692 */                       task = (ScheduledTask)tasks.get(j);
/*      */                       
/*  694 */                       if (task != null && task.getDueDate() != null) {
/*      */                         
/*  696 */                         SimpleDateFormat dueDateFormatter = new SimpleDateFormat("M/d");
/*  697 */                         String dueDate = dueDateFormatter.format(task.getDueDate().getTime());
/*  698 */                         String completionDate = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*      */                         
/*  700 */                         if (task.getScheduledTaskStatus().equals("N/A"))
/*      */                         {
/*  702 */                           completionDate = task.getScheduledTaskStatus();
/*      */                         }
/*      */                         
/*  705 */                         completionDate = String.valueOf(completionDate) + "\n";
/*      */                         
/*  707 */                         String taskAbbrev = MilestoneHelper.getTaskAbbreivationNameById(task.getTaskAbbreviationID());
/*  708 */                         String taskComment = "";
/*      */                         
/*  710 */                         if (taskAbbrev.equalsIgnoreCase("VR")) {
/*      */                           
/*  712 */                           VR = dueDate;
/*  713 */                           VRcom = String.valueOf(completionDate) + taskComment;
/*      */                         
/*      */                         }
/*  716 */                         else if (taskAbbrev.equalsIgnoreCase("AT")) {
/*      */                           
/*  718 */                           AT = dueDate;
/*  719 */                           ATcom = String.valueOf(completionDate) + taskComment;
/*      */                         }
/*  721 */                         else if (taskAbbrev.equalsIgnoreCase("VC")) {
/*      */                           
/*  723 */                           VC = dueDate;
/*  724 */                           VCcom = String.valueOf(completionDate) + taskComment;
/*      */                         
/*      */                         }
/*  727 */                         else if (taskAbbrev.equalsIgnoreCase("PLD")) {
/*      */                           
/*  729 */                           PLD = dueDate;
/*  730 */                           PLDcom = String.valueOf(completionDate) + taskComment;
/*      */                         
/*      */                         }
/*  733 */                         else if (taskAbbrev.equalsIgnoreCase("ELP")) {
/*      */                           
/*  735 */                           ELP = dueDate;
/*  736 */                           ELPcom = String.valueOf(completionDate) + taskComment;
/*      */                         }
/*  738 */                         else if (taskAbbrev.equalsIgnoreCase("LTL")) {
/*      */                           
/*  740 */                           LTL = dueDate;
/*  741 */                           LTLcom = String.valueOf(completionDate) + taskComment;
/*      */                         
/*      */                         }
/*  744 */                         else if (taskAbbrev.equalsIgnoreCase("CAL")) {
/*      */                           
/*  746 */                           CAI = dueDate;
/*  747 */                           CAIcom = String.valueOf(completionDate) + taskComment;
/*      */                         
/*      */                         }
/*  750 */                         else if (taskAbbrev.equalsIgnoreCase("RD")) {
/*      */                           
/*  752 */                           RD = dueDate;
/*  753 */                           RDcom = String.valueOf(completionDate) + taskComment;
/*      */                         
/*      */                         }
/*  756 */                         else if (taskAbbrev.equalsIgnoreCase("EFP")) {
/*      */                           
/*  758 */                           EFF = dueDate;
/*  759 */                           EFFcom = String.valueOf(completionDate) + taskComment;
/*      */                         } 
/*      */                         
/*  762 */                         task = null;
/*      */                       } 
/*      */                     } 
/*      */                   }
/*      */ 
/*      */ 
/*      */                   
/*  769 */                   nextRow = 0;
/*  770 */                   subTable = new DefaultTableLens(subTableRows, 14);
/*      */ 
/*      */                   
/*  773 */                   subTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
/*      */                   
/*  775 */                   setColBorderColor(subTable, nextRow, 14, SHADED_AREA_COLOR);
/*      */                   
/*  777 */                   subTable.setHeaderRowCount(0);
/*  778 */                   subTable.setColWidth(0, 77);
/*  779 */                   subTable.setColWidth(1, 259);
/*  780 */                   subTable.setColWidth(2, 157);
/*  781 */                   subTable.setColWidth(3, 150);
/*  782 */                   subTable.setColWidth(4, 168);
/*  783 */                   subTable.setColWidth(5, 87);
/*  784 */                   subTable.setColWidth(6, 84);
/*  785 */                   subTable.setColWidth(7, 70);
/*  786 */                   subTable.setColWidth(8, 80);
/*  787 */                   subTable.setColWidth(9, 72);
/*  788 */                   subTable.setColWidth(10, 90);
/*  789 */                   subTable.setColWidth(11, 70);
/*  790 */                   subTable.setColWidth(12, 90);
/*  791 */                   subTable.setColWidth(13, 77);
/*  792 */                   subTable.setRowBorderColor(nextRow - 1, Color.lightGray);
/*      */ 
/*      */                   
/*  795 */                   subTable.setObject(nextRow, 0, dateNameText);
/*  796 */                   subTable.setBackground(nextRow, 0, Color.white);
/*  797 */                   subTable.setSpan(nextRow, 0, new Dimension(1, 2));
/*  798 */                   subTable.setRowAutoSize(true);
/*  799 */                   subTable.setAlignment(nextRow, 0, 9);
/*  800 */                   subTable.setFont(nextRow, 0, new Font("Arial", 1, 7));
/*      */ 
/*      */                   
/*  803 */                   subTable.setObject(nextRow, 1, String.valueOf(artist) + "\n" + title);
/*  804 */                   subTable.setBackground(nextRow, 1, Color.white);
/*  805 */                   subTable.setSpan(nextRow, 1, new Dimension(1, 2));
/*  806 */                   subTable.setRowAutoSize(true);
/*  807 */                   subTable.setAlignment(nextRow, 1, 9);
/*  808 */                   subTable.setFont(nextRow, 1, new Font("Arial", 1, 7));
/*      */ 
/*      */ 
/*      */                   
/*  812 */                   subTable.setSpan(nextRow, 2, new Dimension(1, 2));
/*  813 */                   subTable.setObject(nextRow, 2, pack);
/*  814 */                   subTable.setAlignment(nextRow, 2, 10);
/*  815 */                   subTable.setBackground(nextRow, 2, Color.white);
/*      */ 
/*      */                   
/*  818 */                   String[] checkStrings = { comment, artist, title, pack, (new String[7][4] = label).valueOf(contact) + "/n" + otherContact, price };
/*  819 */                   int[] checkStringsLength = { 20, 30, 30, 20, 25, 25, 15 };
/*      */                   
/*  821 */                   int extraLines = MilestoneHelper.lineCountWCR(checkStrings, checkStringsLength);
/*      */                   
/*  823 */                   String[] commentString = { comment };
/*  824 */                   int[] checkCommentLength = { 30 };
/*  825 */                   int commentCounter = MilestoneHelper.lineCountWCR(commentString, checkCommentLength);
/*      */                   
/*  827 */                   extraLines--;
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  832 */                   boolean otherContactExists = false;
/*  833 */                   if (!otherContact.equals("") || commentCounter > 1) {
/*  834 */                     otherContactExists = true;
/*      */                   }
/*      */ 
/*      */                   
/*  838 */                   extraLines = (extraLines <= 2) ? 0 : (extraLines - 2);
/*  839 */                   for (int z = 0; z < extraLines; z++) {
/*  840 */                     otherContact = String.valueOf(otherContact) + "\n";
/*      */                   }
/*      */                   
/*  843 */                   subTable.setObject(nextRow, 3, String.valueOf(upc) + "\n" + selConfig);
/*  844 */                   subTable.setAlignment(nextRow, 3, 10);
/*  845 */                   subTable.setBackground(nextRow, 3, Color.white);
/*  846 */                   subTable.setSpan(nextRow, 3, new Dimension(1, 2));
/*      */                   
/*  848 */                   subTable.setObject(nextRow, 4, "Due Dates");
/*  849 */                   subTable.setColBorderColor(nextRow, 4, SHADED_AREA_COLOR);
/*  850 */                   subTable.setColBorder(nextRow, 4, 266240);
/*  851 */                   subTable.setFont(nextRow, 4, new Font("Arial", 1, 8));
/*  852 */                   subTable.setRowBorderColor(nextRow, SHADED_AREA_COLOR);
/*      */                   
/*  854 */                   subTable.setAlignment(nextRow, 5, 10);
/*  855 */                   subTable.setAlignment(nextRow + 1, 5, 10);
/*      */ 
/*      */                   
/*  858 */                   if (otherContactExists) {
/*  859 */                     subTable.setObject(nextRow + 1, 4, String.valueOf(label) + "\n" + contact + "\n" + otherContact);
/*      */                   } else {
/*  861 */                     subTable.setObject(nextRow + 1, 4, String.valueOf(label) + "\n" + contact);
/*      */                   } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  868 */                   subTable.setRowHeight(nextRow, 9);
/*      */                   
/*  870 */                   subTable.setRowBorderColor(nextRow + 1, SHADED_AREA_COLOR);
/*      */                   
/*  872 */                   subTable.setObject(nextRow, 5, PLD);
/*  873 */                   subTable.setColBorderColor(nextRow, 5, SHADED_AREA_COLOR);
/*  874 */                   subTable.setColBorder(nextRow, 5, 266240);
/*      */                   
/*  876 */                   subTable.setObject(nextRow, 6, VC);
/*  877 */                   subTable.setColBorderColor(nextRow, 6, SHADED_AREA_COLOR);
/*  878 */                   subTable.setColBorder(nextRow, 6, 266240);
/*      */                   
/*  880 */                   subTable.setObject(nextRow, 7, VR);
/*  881 */                   subTable.setColBorderColor(nextRow, 7, SHADED_AREA_COLOR);
/*  882 */                   subTable.setColBorder(nextRow, 7, 266240);
/*      */                   
/*  884 */                   subTable.setObject(nextRow, 8, LTL);
/*  885 */                   subTable.setColBorderColor(nextRow, 8, SHADED_AREA_COLOR);
/*  886 */                   subTable.setColBorder(nextRow, 8, 266240);
/*      */                   
/*  888 */                   subTable.setObject(nextRow, 9, CAI);
/*  889 */                   subTable.setColBorderColor(nextRow, 9, SHADED_AREA_COLOR);
/*  890 */                   subTable.setColBorder(nextRow, 9, 266240);
/*      */                   
/*  892 */                   subTable.setObject(nextRow, 10, RD);
/*  893 */                   subTable.setColBorderColor(nextRow, 10, Color.lightGray);
/*  894 */                   subTable.setColBorder(nextRow, 10, 266240);
/*      */                   
/*  896 */                   subTable.setObject(nextRow, 11, AT);
/*  897 */                   subTable.setColBorderColor(nextRow, 11, Color.lightGray);
/*  898 */                   subTable.setColBorder(nextRow, 11, 266240);
/*      */                   
/*  900 */                   subTable.setObject(nextRow, 12, ELP);
/*  901 */                   subTable.setColBorderColor(nextRow, 12, SHADED_AREA_COLOR);
/*  902 */                   subTable.setColBorder(nextRow, 12, 266240);
/*      */                   
/*  904 */                   subTable.setObject(nextRow, 13, EFF);
/*  905 */                   subTable.setColBorderColor(nextRow, 13, SHADED_AREA_COLOR);
/*  906 */                   subTable.setColBorder(nextRow, 13, 266240);
/*      */                   
/*  908 */                   subTable.setFont(nextRow, 4, new Font("Arial", 1, 7));
/*  909 */                   subTable.setFont(nextRow, 5, new Font("Arial", 1, 7));
/*  910 */                   subTable.setFont(nextRow, 6, new Font("Arial", 1, 7));
/*  911 */                   subTable.setFont(nextRow, 7, new Font("Arial", 1, 7));
/*  912 */                   subTable.setFont(nextRow, 8, new Font("Arial", 1, 7));
/*  913 */                   subTable.setFont(nextRow, 9, new Font("Arial", 1, 7));
/*  914 */                   subTable.setFont(nextRow, 10, new Font("Arial", 1, 7));
/*  915 */                   subTable.setFont(nextRow, 11, new Font("Arial", 1, 7));
/*  916 */                   subTable.setFont(nextRow, 12, new Font("Arial", 1, 7));
/*  917 */                   subTable.setFont(nextRow, 13, new Font("Arial", 1, 7));
/*      */                   
/*  919 */                   subTable.setAlignment(nextRow, 4, 2);
/*  920 */                   subTable.setAlignment(nextRow, 5, 2);
/*  921 */                   subTable.setAlignment(nextRow, 6, 2);
/*  922 */                   subTable.setAlignment(nextRow, 7, 2);
/*  923 */                   subTable.setAlignment(nextRow, 8, 2);
/*  924 */                   subTable.setAlignment(nextRow, 9, 2);
/*  925 */                   subTable.setAlignment(nextRow, 10, 2);
/*  926 */                   subTable.setAlignment(nextRow, 11, 2);
/*  927 */                   subTable.setAlignment(nextRow, 12, 2);
/*  928 */                   subTable.setAlignment(nextRow, 13, 2);
/*      */                   
/*  930 */                   subTable.setObject(nextRow + 1, 5, PLDcom);
/*  931 */                   subTable.setObject(nextRow + 1, 6, VCcom);
/*  932 */                   subTable.setObject(nextRow + 1, 7, VRcom);
/*  933 */                   subTable.setObject(nextRow + 1, 8, LTLcom);
/*  934 */                   subTable.setObject(nextRow + 1, 9, CAIcom);
/*  935 */                   subTable.setObject(nextRow + 1, 10, RDcom);
/*  936 */                   subTable.setObject(nextRow + 1, 11, ATcom);
/*  937 */                   subTable.setObject(nextRow + 1, 12, ELPcom);
/*  938 */                   subTable.setObject(nextRow + 1, 13, EFFcom);
/*      */                   
/*  940 */                   setColBorderColor(subTable, nextRow + 1, 14, SHADED_AREA_COLOR);
/*      */                   
/*  942 */                   subTable.setAlignment(nextRow + 1, 4, 10);
/*  943 */                   subTable.setAlignment(nextRow + 1, 5, 10);
/*  944 */                   subTable.setAlignment(nextRow + 1, 6, 10);
/*  945 */                   subTable.setAlignment(nextRow + 1, 7, 10);
/*  946 */                   subTable.setAlignment(nextRow + 1, 8, 10);
/*  947 */                   subTable.setAlignment(nextRow + 1, 9, 10);
/*  948 */                   subTable.setAlignment(nextRow + 1, 10, 10);
/*  949 */                   subTable.setAlignment(nextRow + 1, 11, 10);
/*  950 */                   subTable.setAlignment(nextRow + 1, 12, 10);
/*  951 */                   subTable.setAlignment(nextRow + 1, 13, 10);
/*      */                   
/*  953 */                   subTable.setRowFont(nextRow + 1, new Font("Arial", 0, 7));
/*      */                   
/*  955 */                   subTable.setRowBackground(nextRow, SHADED_AREA_COLOR);
/*  956 */                   subTable.setRowForeground(nextRow, Color.black);
/*      */ 
/*      */                   
/*  959 */                   subTable.setRowFont(nextRow, new Font("Arial", 0, 7));
/*      */ 
/*      */                   
/*  962 */                   if (newComment.length() > 0) {
/*  963 */                     nextRow++;
/*      */                     
/*  965 */                     subTable.setRowAutoSize(true);
/*  966 */                     subTable.setRowBorderColor(nextRow + 1, Color.lightGray);
/*  967 */                     setColBorderColor(subTable, nextRow + 1, 14, SHADED_AREA_COLOR);
/*  968 */                     subTable.setRowFont(nextRow + 1, new Font("Arial", 2, 7));
/*      */                     
/*  970 */                     subTable.setSpan(nextRow + 1, 0, new Dimension(1, 1));
/*      */                     
/*  972 */                     subTable.setSpan(nextRow + 1, 1, new Dimension(1, 1));
/*  973 */                     subTable.setAlignment(nextRow + 1, 1, 12);
/*  974 */                     subTable.setObject(nextRow + 1, 1, "Comments:   ");
/*      */                     
/*  976 */                     subTable.setObject(nextRow + 1, 2, newComment);
/*  977 */                     subTable.setSpan(nextRow + 1, 2, new Dimension(12, 1));
/*      */                     
/*  979 */                     subTable.setRowBorderColor(nextRow + 1, Color.lightGray);
/*  980 */                     setColBorderColor(subTable, nextRow + 1, 14, SHADED_AREA_COLOR);
/*  981 */                     subTable.setRowFont(nextRow + 1, new Font("Arial", 2, 7));
/*  982 */                     subTable.setAlignment(nextRow + 1, 2, 9);
/*  983 */                     subTable.setColLineWrap(2, true);
/*      */                     
/*  985 */                     subTable.setColBorderColor(nextRow + 1, 0, Color.white);
/*  986 */                     subTable.setColBorderColor(nextRow + 1, 1, Color.white);
/*  987 */                     subTable.setColBorderColor(nextRow + 1, 2, Color.white);
/*  988 */                     subTable.setColBorderColor(nextRow + 1, 3, Color.white);
/*  989 */                     subTable.setColBorderColor(nextRow + 1, 4, Color.white);
/*  990 */                     subTable.setColBorderColor(nextRow + 1, 5, Color.white);
/*  991 */                     subTable.setColBorderColor(nextRow + 1, 6, Color.white);
/*  992 */                     subTable.setColBorderColor(nextRow + 1, 7, Color.white);
/*  993 */                     subTable.setColBorderColor(nextRow + 1, 8, Color.white);
/*  994 */                     subTable.setColBorderColor(nextRow + 1, 9, Color.white);
/*  995 */                     subTable.setColBorderColor(nextRow + 1, 10, Color.white);
/*  996 */                     subTable.setColBorderColor(nextRow + 1, 11, Color.white);
/*  997 */                     subTable.setColBorderColor(nextRow + 1, 12, Color.white);
/*  998 */                     subTable.setColBorderColor(nextRow + 1, 13, Color.white);
/*      */                   } 
/*      */ 
/*      */                   
/* 1002 */                   body = new SectionBand(report);
/*      */                   
/* 1004 */                   double lfLineCount = 1.5D;
/*      */                   
/* 1006 */                   if (extraLines > 0) {
/*      */                     
/* 1008 */                     body.setHeight(1.0F);
/*      */                   }
/*      */                   else {
/*      */                     
/* 1012 */                     body.setHeight(1.0F);
/*      */                   } 
/*      */                   
/* 1015 */                   if (extraLines > 3 || 
/* 1016 */                     VRcom.length() > 10 || ATcom.length() > 10 || 
/* 1017 */                     VCcom.length() > 10 || PLDcom.length() > 10 || 
/* 1018 */                     ELPcom.length() > 10 || LTLcom.length() > 10 || 
/* 1019 */                     EFFcom.length() > 10 || CAIcom.length() > 10 || 
/* 1020 */                     RDcom.length() > 10) {
/*      */ 
/*      */                     
/* 1023 */                     if (lfLineCount < extraLines * 0.3D) {
/* 1024 */                       lfLineCount = extraLines * 0.3D;
/*      */                     }
/* 1026 */                     if (lfLineCount < (VRcom.length() / 7) * 0.3D) {
/* 1027 */                       lfLineCount = (VRcom.length() / 7) * 0.3D;
/*      */                     }
/* 1029 */                     if (lfLineCount < (ATcom.length() / 8) * 0.3D) {
/* 1030 */                       lfLineCount = (ATcom.length() / 8) * 0.3D;
/*      */                     }
/* 1032 */                     if (lfLineCount < (VCcom.length() / 8) * 0.3D) {
/* 1033 */                       lfLineCount = (VCcom.length() / 8) * 0.3D;
/*      */                     }
/* 1035 */                     if (lfLineCount < (PLDcom.length() / 8) * 0.3D) {
/* 1036 */                       lfLineCount = (PLDcom.length() / 8) * 0.3D;
/*      */                     }
/* 1038 */                     if (lfLineCount < (ELPcom.length() / 8) * 0.3D) {
/* 1039 */                       lfLineCount = (ELPcom.length() / 8) * 0.3D;
/*      */                     }
/* 1041 */                     if (lfLineCount < (LTLcom.length() / 8) * 0.3D) {
/* 1042 */                       lfLineCount = (LTLcom.length() / 8) * 0.3D;
/*      */                     }
/* 1044 */                     if (lfLineCount < (EFFcom.length() / 8) * 0.3D) {
/* 1045 */                       lfLineCount = (EFFcom.length() / 8) * 0.3D;
/*      */                     }
/* 1047 */                     if (lfLineCount < (CAIcom.length() / 8) * 0.3D) {
/* 1048 */                       lfLineCount = (CAIcom.length() / 8) * 0.3D;
/*      */                     }
/* 1050 */                     if (lfLineCount < (RDcom.length() / 8) * 0.3D) {
/* 1051 */                       lfLineCount = (RDcom.length() / 8) * 0.3D;
/*      */                     }
/* 1053 */                     body.setHeight((float)lfLineCount);
/*      */                   }
/*      */                   else {
/*      */                     
/* 1057 */                     body.setHeight(1.0F);
/*      */                   } 
/*      */                   
/* 1060 */                   body.addTable(subTable, new Rectangle(800, 800));
/* 1061 */                   body.setBottomBorder(0);
/* 1062 */                   body.setTopBorder(0);
/* 1063 */                   body.setShrinkToFit(true);
/* 1064 */                   body.setVisible(true);
/* 1065 */                   group = new DefaultSectionLens(null, group, body);
/*      */                 } 
/*      */                 
/* 1068 */                 group = new DefaultSectionLens(null, group, spacer);
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1078 */         group = new DefaultSectionLens(hbandType, group, null);
/* 1079 */         report.addSection(group, rowCountTable);
/* 1080 */         report.addPageBreak();
/* 1081 */         group = null;
/*      */ 
/*      */       
/*      */       }
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 1089 */     catch (Exception e) {
/*      */       
/* 1091 */       System.out.println(">>>>>>>>PressPlayProductionScheduleForPrintSubHandler(): exception: " + e);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1100 */     System.out.println("done");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void setColBorderColor(DefaultTableLens table, int row, int columns, Color color) {
/* 1109 */     for (int i = -1; i < columns; i++)
/*      */     {
/* 1111 */       table.setColBorderColor(row, i, color);
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
/* 1125 */     int COL_LINE_STYLE = 4097;
/* 1126 */     int HEADER_FONT_SIZE = 12;
/*      */     
/* 1128 */     table_contents.setObject(nextRow, 0, "");
/* 1129 */     table_contents.setSpan(nextRow, 0, new Dimension(cols, 1));
/* 1130 */     table_contents.setRowHeight(nextRow, 1);
/* 1131 */     table_contents.setRowBackground(nextRow, Color.white);
/*      */     
/* 1133 */     table_contents.setColBorderColor(nextRow, -1, Color.black);
/* 1134 */     table_contents.setColBorder(nextRow, -1, 4097);
/* 1135 */     table_contents.setColBorderColor(nextRow, cols - 1, Color.black);
/* 1136 */     table_contents.setColBorder(nextRow, cols - 1, 4097);
/*      */     
/* 1138 */     table_contents.setRowBorderColor(nextRow, Color.white);
/* 1139 */     table_contents.setRowBorder(nextRow, 266240);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1144 */     table_contents.setColBorderColor(nextRow + 1, -1, Color.black);
/* 1145 */     table_contents.setColBorder(nextRow + 1, -1, 4097);
/* 1146 */     table_contents.setColBorderColor(nextRow + 1, cols - 1, Color.black);
/* 1147 */     table_contents.setColBorder(nextRow + 1, cols - 1, 4097);
/*      */ 
/*      */     
/* 1150 */     table_contents.setAlignment(nextRow + 1, 0, 2);
/* 1151 */     table_contents.setSpan(nextRow + 1, 0, new Dimension(cols, 1));
/* 1152 */     table_contents.setObject(nextRow + 1, 0, title);
/* 1153 */     table_contents.setRowFont(nextRow + 1, new Font("Arial", 3, 12));
/*      */     
/* 1155 */     table_contents.setColBorderColor(nextRow + 1, -1, Color.black);
/* 1156 */     table_contents.setColBorder(nextRow + 1, -1, 4097);
/*      */     
/* 1158 */     nextRow += 2;
/*      */     
/* 1160 */     table_contents.setObject(nextRow, 0, "");
/* 1161 */     table_contents.setSpan(nextRow, 0, new Dimension(cols, 1));
/* 1162 */     table_contents.setRowHeight(nextRow, 1);
/* 1163 */     table_contents.setRowBackground(nextRow, Color.white);
/*      */ 
/*      */     
/* 1166 */     table_contents.setColBorderColor(nextRow, -1, Color.black);
/* 1167 */     table_contents.setColBorder(nextRow, -1, 4097);
/* 1168 */     table_contents.setColBorderColor(nextRow, cols, Color.black);
/* 1169 */     table_contents.setColBorder(nextRow, cols, 4097);
/*      */     
/* 1171 */     table_contents.setRowBorderColor(nextRow, Color.white);
/* 1172 */     table_contents.setRowBorder(nextRow, 266240);
/*      */     
/* 1174 */     nextRow++;
/*      */ 
/*      */     
/* 1177 */     table_contents.setObject(nextRow, 0, "");
/* 1178 */     table_contents.setSpan(nextRow, 0, new Dimension(cols, 1));
/* 1179 */     table_contents.setRowHeight(nextRow, 1);
/* 1180 */     table_contents.setRowBackground(nextRow, Color.white);
/*      */ 
/*      */     
/* 1183 */     table_contents.setColBorderColor(nextRow, -1, Color.white);
/* 1184 */     table_contents.setColBorder(nextRow, -1, 4097);
/* 1185 */     table_contents.setColBorderColor(nextRow, cols - 1, Color.white);
/* 1186 */     table_contents.setColBorder(nextRow, cols, 4097);
/*      */     
/* 1188 */     table_contents.setRowBorderColor(nextRow, Color.white);
/* 1189 */     table_contents.setRowBorder(nextRow, 266240);
/*      */     
/* 1191 */     nextRow++;
/*      */     
/* 1193 */     return nextRow;
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
/* 1208 */   public static String removeLF(String theString, int maxChars) { return theString.replace('\n', ' '); }
/*      */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\PressPlayProductionScheduleForPrintSubHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */