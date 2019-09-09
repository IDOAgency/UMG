/*      */ package WEB-INF.classes.com.universal.milestone;
/*      */ 
/*      */ import com.techempower.ComponentLog;
/*      */ import com.techempower.gemini.Context;
/*      */ import com.techempower.gemini.GeminiApplication;
/*      */ import com.universal.milestone.DatePeriod;
/*      */ import com.universal.milestone.DreamWorksPromoScheduleForPrintSubHandler;
/*      */ import com.universal.milestone.Form;
/*      */ import com.universal.milestone.MilestoneHelper;
/*      */ import com.universal.milestone.MilestoneHelper_2;
/*      */ import com.universal.milestone.MultOtherContact;
/*      */ import com.universal.milestone.ProductCategory;
/*      */ import com.universal.milestone.Schedule;
/*      */ import com.universal.milestone.ScheduledTask;
/*      */ import com.universal.milestone.SecureHandler;
/*      */ import com.universal.milestone.Selection;
/*      */ import com.universal.milestone.SelectionManager;
/*      */ import com.universal.milestone.SelectionStatus;
/*      */ import com.universal.milestone.StringDateComparator;
/*      */ import com.universal.milestone.StringReverseComparator;
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
/*      */ public class DreamWorksPromoScheduleForPrintSubHandler
/*      */   extends SecureHandler
/*      */ {
/*      */   public static final String COMPONENT_CODE = "hPsp";
/*      */   public GeminiApplication application;
/*      */   public ComponentLog log;
/*      */   
/*      */   public DreamWorksPromoScheduleForPrintSubHandler(GeminiApplication application) {
/*   74 */     this.application = application;
/*   75 */     this.log = application.getLog("hPsp");
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static void fillDreamWorksPromoScheduleForPrint(XStyleSheet report, Context context) {
/*   98 */     int COL_LINE_STYLE = 4097;
/*   99 */     int HEADER_FONT_SIZE = 12;
/*      */     
/*  101 */     double ldLineVal = 0.3D;
/*      */     
/*      */     try {
/*  104 */       HttpServletResponse sresponse = context.getResponse();
/*  105 */       context.putDelivery("status", new String("start_gathering"));
/*  106 */       context.includeJSP("status.jsp", "hiddenFrame");
/*  107 */       sresponse.setContentType("text/plain");
/*  108 */       sresponse.flushBuffer();
/*      */     }
/*  110 */     catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  115 */     Vector selections = MilestoneHelper.getSelectionsForReport(context);
/*      */     
/*      */     try {
/*  118 */       HttpServletResponse sresponse = context.getResponse();
/*  119 */       context.putDelivery("status", new String("start_report"));
/*  120 */       context.putDelivery("percent", new String("10"));
/*  121 */       context.includeJSP("status.jsp", "hiddenFrame");
/*  122 */       sresponse.setContentType("text/plain");
/*  123 */       sresponse.flushBuffer();
/*      */     }
/*  125 */     catch (Exception exception) {}
/*      */ 
/*      */     
/*  128 */     int DATA_FONT_SIZE = 7;
/*  129 */     int SMALL_HEADER_FONT_SIZE = 8;
/*      */     
/*  131 */     int NUM_COLUMNS = 14;
/*  132 */     Color SHADED_AREA_COLOR = Color.lightGray;
/*      */     
/*  134 */     SectionBand hbandType = new SectionBand(report);
/*  135 */     SectionBand hbandCategory = new SectionBand(report);
/*  136 */     SectionBand hbandHeadings = new SectionBand(report);
/*      */     
/*  138 */     SectionBand body = new SectionBand(report);
/*  139 */     SectionBand footer = new SectionBand(report);
/*  140 */     SectionBand spacer = new SectionBand(report);
/*  141 */     DefaultSectionLens group = null;
/*      */     
/*  143 */     footer.setVisible(true);
/*  144 */     footer.setHeight(0.1F);
/*  145 */     footer.setShrinkToFit(false);
/*  146 */     footer.setBottomBorder(0);
/*      */     
/*  148 */     spacer.setVisible(true);
/*  149 */     spacer.setHeight(0.05F);
/*  150 */     spacer.setShrinkToFit(false);
/*  151 */     spacer.setBottomBorder(0);
/*      */ 
/*      */     
/*  154 */     Hashtable selTable = groupSelectionsByTypeConfigAndStreetDate2(selections);
/*  155 */     Enumeration configs = selTable.keys();
/*  156 */     Vector configVector = new Vector();
/*      */     
/*  158 */     while (configs.hasMoreElements()) {
/*  159 */       configVector.addElement(configs.nextElement());
/*      */     }
/*  161 */     int numConfigs = configVector.size();
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  166 */       Collections.sort(configVector);
/*  167 */       Vector sortedConfigVector = MilestoneHelper.sortStrings(configVector);
/*  168 */       Collections.sort(sortedConfigVector, new StringReverseComparator());
/*      */ 
/*      */       
/*  171 */       DefaultTableLens table_contents = null;
/*  172 */       DefaultTableLens rowCountTable = null;
/*  173 */       DefaultTableLens columnHeaderTable = null;
/*  174 */       DefaultTableLens subTable = null;
/*  175 */       DefaultTableLens monthTableLens = null;
/*      */ 
/*      */       
/*  178 */       rowCountTable = new DefaultTableLens(2, 10000);
/*      */ 
/*      */       
/*  181 */       int totalCount = 0;
/*  182 */       int tenth = 1;
/*      */ 
/*      */       
/*  185 */       for (int n = 0; n < sortedConfigVector.size(); n++) {
/*      */         
/*  187 */         String configC = (configVector.elementAt(n) != null) ? (String)configVector.elementAt(n) : "";
/*  188 */         Hashtable monthTableC = (Hashtable)selTable.get(configC);
/*      */         
/*  190 */         totalCount++;
/*  191 */         Enumeration monthsC = monthTableC.keys();
/*      */         
/*  193 */         Vector monthVectorC = new Vector();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  201 */         while (monthsC.hasMoreElements()) {
/*  202 */           monthVectorC.add((String)monthsC.nextElement());
/*      */         }
/*  204 */         Object[] monthArrayC = null;
/*  205 */         monthArrayC = monthVectorC.toArray();
/*  206 */         totalCount += monthArrayC.length;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  212 */       tenth = (totalCount > 10) ? (totalCount / 10) : 1;
/*      */       
/*  214 */       HttpServletResponse sresponse = context.getResponse();
/*  215 */       context.putDelivery("status", new String("start_report"));
/*  216 */       context.putDelivery("percent", new String("20"));
/*  217 */       context.includeJSP("status.jsp", "hiddenFrame");
/*  218 */       sresponse.setContentType("text/plain");
/*  219 */       sresponse.flushBuffer();
/*      */       
/*  221 */       int recordCount = 0;
/*  222 */       int count = 0;
/*      */       
/*  224 */       for (int n = 0; n < sortedConfigVector.size(); n++)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  230 */         Form reportForm = (Form)context.getSessionValue("reportForm");
/*      */         
/*  232 */         Calendar beginStDate = (reportForm.getStringValue("beginDate") != null && 
/*  233 */           reportForm.getStringValue("beginDate").length() > 0) ? 
/*  234 */           MilestoneHelper.getDate(reportForm.getStringValue("beginDate")) : null;
/*      */         
/*  236 */         Calendar endStDate = (reportForm.getStringValue("endDate") != null && 
/*  237 */           reportForm.getStringValue("endDate").length() > 0) ? 
/*  238 */           MilestoneHelper.getDate(reportForm.getStringValue("endDate")) : null;
/*      */         
/*  240 */         report.setElement("crs_startdate", MilestoneHelper.getFormatedDate(beginStDate));
/*  241 */         report.setElement("crs_enddate", MilestoneHelper.getFormatedDate(endStDate));
/*      */         
/*  243 */         SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
/*  244 */         String todayLong = formatter.format(new Date());
/*  245 */         report.setElement("crs_bottomdate", todayLong);
/*      */ 
/*      */         
/*  248 */         String config = (sortedConfigVector.elementAt(n) != null) ? (String)sortedConfigVector.elementAt(n) : "";
/*  249 */         Hashtable monthTable = (Hashtable)selTable.get(config);
/*      */ 
/*      */         
/*  252 */         int numMonths = 0;
/*  253 */         int numDates = 0;
/*  254 */         int numSelections = 0;
/*      */         
/*  256 */         if (monthTable != null) {
/*      */           
/*  258 */           Enumeration months = monthTable.keys();
/*  259 */           while (months.hasMoreElements()) {
/*      */             
/*  261 */             String monthName = (String)months.nextElement();
/*      */             
/*  263 */             numMonths++;
/*      */             
/*  265 */             selections = (Vector)monthTable.get(monthName);
/*  266 */             if (selections != null) {
/*  267 */               numSelections += selections.size();
/*      */             }
/*      */           } 
/*      */         } 
/*  271 */         int numRows = 0;
/*      */ 
/*      */         
/*  274 */         numRows += numMonths * 3;
/*  275 */         numRows += numDates * 2;
/*  276 */         numRows += numSelections * 2;
/*      */         
/*  278 */         numRows += 5;
/*      */ 
/*      */         
/*  281 */         hbandType = new SectionBand(report);
/*  282 */         hbandType.setHeight(0.95F);
/*      */         
/*  284 */         hbandType.setShrinkToFit(true);
/*  285 */         hbandType.setVisible(true);
/*  286 */         hbandHeadings = new SectionBand(report);
/*  287 */         hbandHeadings.setHeight(1.0F);
/*  288 */         hbandHeadings.setShrinkToFit(true);
/*  289 */         hbandHeadings.setVisible(true);
/*      */         
/*  291 */         table_contents = new DefaultTableLens(1, 14);
/*      */ 
/*      */         
/*  294 */         table_contents.setHeaderRowCount(0);
/*  295 */         table_contents.setColWidth(0, 110);
/*  296 */         table_contents.setColWidth(1, 259);
/*  297 */         table_contents.setColWidth(2, 157);
/*  298 */         table_contents.setColWidth(3, 75);
/*  299 */         table_contents.setColWidth(4, 80);
/*  300 */         table_contents.setColWidth(5, 80);
/*  301 */         table_contents.setColWidth(6, 80);
/*  302 */         table_contents.setColWidth(7, 80);
/*  303 */         table_contents.setColWidth(8, 80);
/*  304 */         table_contents.setColWidth(9, 80);
/*  305 */         table_contents.setColWidth(10, 80);
/*  306 */         table_contents.setColWidth(11, 80);
/*  307 */         table_contents.setColWidth(12, 80);
/*  308 */         table_contents.setColWidth(13, 100);
/*      */ 
/*      */         
/*  311 */         table_contents.setColBorderColor(Color.black);
/*  312 */         table_contents.setRowBorderColor(Color.black);
/*  313 */         table_contents.setRowBorder(4097);
/*  314 */         table_contents.setColBorder(4097);
/*      */ 
/*      */         
/*  317 */         int nextRow = 0;
/*      */ 
/*      */         
/*  320 */         String configHeaderText = !config.trim().equals("") ? config : "Other";
/*      */ 
/*      */         
/*  323 */         table_contents.setSpan(nextRow, 0, new Dimension(14, 1));
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
/*  338 */         table_contents.setColBorder(nextRow, 13, 266240);
/*  339 */         table_contents.setColBorder(nextRow, 14, 266240);
/*  340 */         table_contents.setRowBorder(nextRow, 266240);
/*      */         
/*  342 */         table_contents.setRowBorderColor(nextRow - 1, Color.black);
/*  343 */         table_contents.setColBorderColor(nextRow, -1, Color.black);
/*  344 */         table_contents.setColBorderColor(nextRow, 0, Color.black);
/*  345 */         table_contents.setColBorderColor(nextRow, 13, Color.black);
/*  346 */         table_contents.setColBorderColor(nextRow, 14, Color.black);
/*  347 */         table_contents.setRowBorderColor(nextRow, Color.black);
/*      */ 
/*      */ 
/*      */         
/*  351 */         hbandType.addTable(table_contents, new Rectangle(0, 0, 800, 25));
/*      */         
/*  353 */         nextRow = 0;
/*      */         
/*  355 */         columnHeaderTable = new DefaultTableLens(1, 14);
/*      */         
/*  357 */         columnHeaderTable.setHeaderRowCount(0);
/*  358 */         columnHeaderTable.setColWidth(0, 150);
/*  359 */         columnHeaderTable.setColWidth(1, 259);
/*  360 */         columnHeaderTable.setColWidth(2, 130);
/*  361 */         columnHeaderTable.setColWidth(3, 75);
/*  362 */         columnHeaderTable.setColWidth(4, 75);
/*  363 */         columnHeaderTable.setColWidth(5, 75);
/*  364 */         columnHeaderTable.setColWidth(6, 75);
/*  365 */         columnHeaderTable.setColWidth(7, 75);
/*  366 */         columnHeaderTable.setColWidth(8, 75);
/*  367 */         columnHeaderTable.setColWidth(9, 75);
/*  368 */         columnHeaderTable.setColWidth(10, 75);
/*  369 */         columnHeaderTable.setColWidth(11, 75);
/*  370 */         columnHeaderTable.setColWidth(12, 75);
/*  371 */         columnHeaderTable.setColWidth(13, 75);
/*      */         
/*  373 */         columnHeaderTable.setAlignment(nextRow, 0, 34);
/*  374 */         columnHeaderTable.setAlignment(nextRow, 1, 34);
/*  375 */         columnHeaderTable.setAlignment(nextRow, 2, 34);
/*  376 */         columnHeaderTable.setAlignment(nextRow, 3, 34);
/*  377 */         columnHeaderTable.setAlignment(nextRow, 4, 34);
/*  378 */         columnHeaderTable.setAlignment(nextRow, 5, 34);
/*  379 */         columnHeaderTable.setAlignment(nextRow, 6, 34);
/*  380 */         columnHeaderTable.setAlignment(nextRow, 7, 34);
/*  381 */         columnHeaderTable.setAlignment(nextRow, 8, 34);
/*  382 */         columnHeaderTable.setAlignment(nextRow, 9, 34);
/*  383 */         columnHeaderTable.setAlignment(nextRow, 10, 34);
/*  384 */         columnHeaderTable.setAlignment(nextRow, 11, 34);
/*  385 */         columnHeaderTable.setAlignment(nextRow, 12, 34);
/*  386 */         columnHeaderTable.setAlignment(nextRow, 13, 34);
/*      */         
/*  388 */         columnHeaderTable.setObject(nextRow, 0, "Selection #\nArtist");
/*  389 */         columnHeaderTable.setObject(nextRow, 1, "Title");
/*  390 */         columnHeaderTable.setObject(nextRow, 2, "File Maint\n(PFM)");
/*  391 */         columnHeaderTable.setObject(nextRow, 3, "CD-CS\nCopy to\nEd");
/*  392 */         columnHeaderTable.setObject(nextRow, 4, "Copy to\nArt");
/*  393 */         columnHeaderTable.setObject(nextRow, 5, "Final Art\nDue");
/*  394 */         columnHeaderTable.setObject(nextRow, 6, "Ref\nApp/Enter\nRMS");
/*  395 */         columnHeaderTable.setObject(nextRow, 7, "Order\nParts");
/*  396 */         columnHeaderTable.setObject(nextRow, 8, "Film &\nParts Ship");
/*  397 */         columnHeaderTable.setObject(nextRow, 9, "BOM");
/*  398 */         columnHeaderTable.setObject(nextRow, 10, "Enhn\nTest\nAppvd");
/*  399 */         columnHeaderTable.setObject(nextRow, 11, "DJs\nShip\nHome\nOffice");
/*  400 */         columnHeaderTable.setObject(nextRow, 12, "DJs\nShip\nField");
/*  401 */         columnHeaderTable.setObject(nextRow, 13, "DJs\nShip\nRadio");
/*      */         
/*  403 */         setColBorderColor(columnHeaderTable, nextRow, 14, SHADED_AREA_COLOR);
/*      */         
/*  405 */         columnHeaderTable.setRowBorderColor(nextRow - 1, SHADED_AREA_COLOR);
/*  406 */         columnHeaderTable.setRowBorderColor(nextRow, SHADED_AREA_COLOR);
/*  407 */         columnHeaderTable.setRowFont(nextRow, new Font("Arial", 1, 8));
/*  408 */         columnHeaderTable.setRowBackground(nextRow, Color.white);
/*  409 */         columnHeaderTable.setRowForeground(nextRow, Color.black);
/*  410 */         columnHeaderTable.setRowHeight(nextRow, 50);
/*      */         
/*  412 */         hbandType.addTable(columnHeaderTable, new Rectangle(0, 24, 800, 80));
/*  413 */         hbandType.setBottomBorder(0);
/*      */ 
/*      */         
/*  416 */         if (monthTable != null) {
/*      */           
/*  418 */           Enumeration months = monthTable.keys();
/*      */           
/*  420 */           Vector monthVector = new Vector();
/*      */           
/*  422 */           while (months.hasMoreElements()) {
/*  423 */             monthVector.add((String)months.nextElement());
/*      */           }
/*  425 */           Object[] monthArray = null;
/*  426 */           monthArray = monthVector.toArray();
/*      */           
/*  428 */           Arrays.sort(monthArray, new StringDateComparator());
/*      */           
/*  430 */           for (int x = 0; x < monthArray.length; x++) {
/*      */ 
/*      */             
/*  433 */             String monthName = (String)monthArray[x];
/*  434 */             String monthNameString = monthName;
/*      */             
/*  436 */             monthTableLens = new DefaultTableLens(1, 14);
/*  437 */             hbandCategory = new SectionBand(report);
/*  438 */             hbandCategory.setHeight(0.25F);
/*  439 */             hbandCategory.setShrinkToFit(true);
/*  440 */             hbandCategory.setVisible(true);
/*  441 */             hbandCategory.setBottomBorder(0);
/*  442 */             hbandCategory.setLeftBorder(0);
/*  443 */             hbandCategory.setRightBorder(0);
/*  444 */             hbandCategory.setTopBorder(0);
/*      */             
/*  446 */             nextRow = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  453 */             String cycle = "";
/*      */             
/*      */             try {
/*  456 */               Calendar calenderDate = MilestoneHelper.getDate(monthNameString);
/*  457 */               DatePeriod datePeriod = MilestoneHelper.findDatePeriod(calenderDate);
/*  458 */               cycle = " " + datePeriod.getCycle();
/*      */             }
/*  460 */             catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  466 */             monthTableLens.setSpan(nextRow, 0, new Dimension(11, 1));
/*      */             
/*  468 */             monthTableLens.setObject(nextRow, 0, monthNameString);
/*  469 */             monthTableLens.setSpan(nextRow, 13, new Dimension(1, 1));
/*      */             
/*  471 */             monthTableLens.setColBorderColor(nextRow, 1, SHADED_AREA_COLOR);
/*  472 */             monthTableLens.setColBorderColor(nextRow, 2, SHADED_AREA_COLOR);
/*  473 */             monthTableLens.setColBorderColor(nextRow, 3, SHADED_AREA_COLOR);
/*  474 */             monthTableLens.setColBorderColor(nextRow, 4, SHADED_AREA_COLOR);
/*  475 */             monthTableLens.setColBorderColor(nextRow, 5, SHADED_AREA_COLOR);
/*  476 */             monthTableLens.setColBorderColor(nextRow, 6, SHADED_AREA_COLOR);
/*  477 */             monthTableLens.setColBorderColor(nextRow, 7, SHADED_AREA_COLOR);
/*  478 */             monthTableLens.setColBorderColor(nextRow, 8, SHADED_AREA_COLOR);
/*  479 */             monthTableLens.setColBorderColor(nextRow, 9, SHADED_AREA_COLOR);
/*  480 */             monthTableLens.setColBorderColor(nextRow, 10, SHADED_AREA_COLOR);
/*  481 */             monthTableLens.setColBorderColor(nextRow, 11, SHADED_AREA_COLOR);
/*  482 */             monthTableLens.setColBorderColor(nextRow, 12, SHADED_AREA_COLOR);
/*  483 */             monthTableLens.setColBorderColor(nextRow, 13, SHADED_AREA_COLOR);
/*  484 */             monthTableLens.setAlignment(nextRow, 13, 4);
/*  485 */             monthTableLens.setRowHeight(nextRow, 14);
/*  486 */             monthTableLens.setRowFont(nextRow, new Font("Arial", 1, 12));
/*  487 */             monthTableLens.setRowBackground(nextRow, SHADED_AREA_COLOR);
/*  488 */             monthTableLens.setRowForeground(nextRow, Color.black);
/*  489 */             monthTableLens.setRowBorderColor(nextRow, Color.white);
/*  490 */             monthTableLens.setRowBorderColor(nextRow - 1, Color.white);
/*  491 */             monthTableLens.setColBorderColor(nextRow, -1, Color.white);
/*  492 */             monthTableLens.setColBorderColor(nextRow, 0, Color.white);
/*  493 */             monthTableLens.setColBorderColor(nextRow, 14, Color.white);
/*      */             
/*  495 */             hbandCategory.addTable(monthTableLens, new Rectangle(800, 800));
/*      */             
/*  497 */             footer.setVisible(true);
/*  498 */             footer.setHeight(0.1F);
/*  499 */             footer.setShrinkToFit(false);
/*  500 */             footer.setBottomBorder(0);
/*      */             
/*  502 */             group = new DefaultSectionLens(null, group, spacer);
/*  503 */             group = new DefaultSectionLens(null, group, hbandCategory);
/*  504 */             group = new DefaultSectionLens(null, group, spacer);
/*      */             
/*  506 */             nextRow = 0;
/*      */             
/*  508 */             selections = (Vector)monthTable.get(monthNameString);
/*  509 */             if (selections == null) {
/*  510 */               selections = new Vector();
/*      */             }
/*      */             
/*  513 */             MilestoneHelper.setSelectionSorting(selections, 14);
/*  514 */             Collections.sort(selections);
/*      */ 
/*      */             
/*  517 */             MilestoneHelper.setSelectionSorting(selections, 4);
/*  518 */             Collections.sort(selections);
/*      */ 
/*      */             
/*  521 */             MilestoneHelper.setSelectionSorting(selections, 22);
/*  522 */             Collections.sort(selections);
/*      */ 
/*      */             
/*  525 */             MilestoneHelper.setSelectionSorting(selections, 9);
/*  526 */             Collections.sort(selections);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  533 */             for (int i = 0; i < selections.size(); i++) {
/*      */               int subTableRows;
/*  535 */               Selection sel = (Selection)selections.elementAt(i);
/*      */               
/*  537 */               if (count < recordCount / tenth) {
/*      */                 
/*  539 */                 count = recordCount / tenth;
/*  540 */                 sresponse = context.getResponse();
/*  541 */                 context.putDelivery("status", new String("start_report"));
/*  542 */                 int myPercent = count * 10;
/*  543 */                 if (myPercent > 90)
/*  544 */                   myPercent = 90; 
/*  545 */                 context.putDelivery("percent", new String(String.valueOf(myPercent)));
/*  546 */                 context.includeJSP("status.jsp", "hiddenFrame");
/*  547 */                 sresponse.setContentType("text/plain");
/*  548 */                 sresponse.flushBuffer();
/*      */               } 
/*  550 */               recordCount++;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  558 */               String titleId = "";
/*  559 */               titleId = sel.getTitleID();
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  564 */               String artist = "";
/*  565 */               artist = sel.getFlArtist().trim();
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  570 */               String comment = "";
/*  571 */               String commentStr = (sel.getSelectionComments() != null) ? sel.getSelectionComments() : "";
/*  572 */               String newComment = removeLF(commentStr, 800);
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  577 */               String label = "";
/*  578 */               if (sel.getLabel() != null) {
/*  579 */                 label = sel.getLabel().getName();
/*      */               }
/*      */ 
/*      */ 
/*      */               
/*  584 */               String pack = "";
/*  585 */               pack = sel.getSelectionPackaging();
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  590 */               String title = "";
/*  591 */               if (sel.getTitle() != null) {
/*  592 */                 title = sel.getTitle();
/*      */               }
/*      */               
/*  595 */               String upc = "";
/*  596 */               upc = sel.getUpc();
/*      */ 
/*      */               
/*  599 */               upc = MilestoneHelper_2.getRMSReportFormat(upc, "UPC", sel.getIsDigital());
/*      */               
/*  601 */               if (upc != null && upc.length() == 0) {
/*  602 */                 upc = titleId;
/*      */               }
/*      */               
/*  605 */               String selectionNo = "";
/*  606 */               selectionNo = (sel.getSelectionNo() != null) ? sel.getSelectionNo() : "";
/*      */ 
/*      */               
/*  609 */               if (sel.getPrefixID() != null && sel.getPrefixID().getAbbreviation() != null) {
/*  610 */                 selectionNo = String.valueOf(sel.getPrefixID().getAbbreviation()) + " " + selectionNo;
/*      */               }
/*      */ 
/*      */               
/*  614 */               Vector multOtherContacts = new Vector();
/*  615 */               String aContact = "";
/*  616 */               for (int co = 0; co < sel.getMultOtherContacts().size(); co++) {
/*  617 */                 aContact = ((MultOtherContact)sel.getMultOtherContacts().elementAt(co)).getName();
/*  618 */                 multOtherContacts.add(aContact);
/*      */               } 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  624 */               Schedule schedule = sel.getSchedule();
/*      */               
/*  626 */               Vector tasks = (schedule != null) ? schedule.getTasks() : null;
/*  627 */               ScheduledTask task = null;
/*      */               
/*  629 */               String SFD = "";
/*  630 */               String SFM = "";
/*  631 */               String PCF = "";
/*  632 */               String FPC = "";
/*  633 */               String FAD = "";
/*  634 */               String MA = "";
/*  635 */               String LC = "";
/*  636 */               String PO = "";
/*  637 */               String SEPS = "";
/*  638 */               String STD = "";
/*  639 */               String SFS = "";
/*  640 */               String PFS = "";
/*  641 */               String TPS = "";
/*  642 */               String BMS = "";
/*  643 */               String TA = "";
/*  644 */               String DJHO = "";
/*  645 */               String DJFS = "";
/*  646 */               String DJR = "";
/*      */               
/*  648 */               String SFDcom = "";
/*  649 */               String SFMcom = "";
/*  650 */               String PCFcom = "";
/*  651 */               String FPCcom = "";
/*  652 */               String FADcom = "";
/*  653 */               String MAcom = "";
/*  654 */               String LCcom = "";
/*  655 */               String POcom = "";
/*  656 */               String SEPScom = "";
/*  657 */               String STDcom = "";
/*  658 */               String SFScom = "";
/*  659 */               String PFScom = "";
/*  660 */               String TPScom = "";
/*  661 */               String BMScom = "";
/*  662 */               String TAcom = "";
/*  663 */               String DJHOcom = "";
/*  664 */               String DJFScom = "";
/*  665 */               String DJRcom = "";
/*      */               
/*  667 */               String SFDvend = "";
/*  668 */               String SFMvend = "";
/*  669 */               String PCFvend = "";
/*  670 */               String FPCvend = "";
/*  671 */               String FADvend = "";
/*  672 */               String MAvend = "";
/*  673 */               String LCvend = "";
/*  674 */               String POvend = "";
/*  675 */               String SEPSvend = "";
/*  676 */               String STDvend = "";
/*  677 */               String SFSvend = "";
/*  678 */               String PFSvend = "";
/*  679 */               String TPSvend = "";
/*  680 */               String BMSvend = "";
/*  681 */               String TAvend = "";
/*  682 */               String DJHOvend = "";
/*  683 */               String DJFSvend = "";
/*  684 */               String DJRvend = "";
/*      */               
/*  686 */               if (tasks != null)
/*      */               {
/*  688 */                 for (subTableRows = 0; subTableRows < tasks.size(); subTableRows++) {
/*      */                   
/*  690 */                   task = (ScheduledTask)tasks.get(subTableRows);
/*      */                   
/*  692 */                   if (task != null && task.getDueDate() != null) {
/*      */                     
/*  694 */                     SimpleDateFormat dueDateFormatter = new SimpleDateFormat("M/d");
/*  695 */                     String dueDate = dueDateFormatter.format(task.getDueDate().getTime());
/*  696 */                     String completionDate = "";
/*      */                     
/*  698 */                     if (task.getCompletionDate() == null) {
/*  699 */                       completionDate = "";
/*      */                     } else {
/*  701 */                       completionDate = dueDateFormatter.format(task.getCompletionDate().getTime());
/*      */                     } 
/*      */ 
/*      */                     
/*  705 */                     if (task.getScheduledTaskStatus().equals("N/A"))
/*      */                     {
/*  707 */                       completionDate = task.getScheduledTaskStatus();
/*      */                     }
/*      */                     
/*  710 */                     String taskAbbrev = MilestoneHelper.getTaskAbbreivationNameById(task.getTaskAbbreviationID());
/*  711 */                     String taskComment = "";
/*  712 */                     String taskVendor = "";
/*      */                     
/*  714 */                     taskVendor = (task.getVendor() != null) ? task.getVendor() : "";
/*  715 */                     taskVendor = taskVendor.equals("\n") ? "" : taskVendor;
/*      */                     
/*  717 */                     if (taskAbbrev.equalsIgnoreCase("SFD")) {
/*      */                       
/*  719 */                       SFD = dueDate;
/*  720 */                       SFDcom = completionDate;
/*  721 */                       SFDvend = taskVendor;
/*      */                     }
/*  723 */                     else if (taskAbbrev.equalsIgnoreCase("SF/M")) {
/*      */                       
/*  725 */                       SFM = dueDate;
/*  726 */                       SFMcom = completionDate;
/*  727 */                       SFMvend = taskVendor;
/*      */                     }
/*  729 */                     else if (taskAbbrev.equalsIgnoreCase("PCE")) {
/*      */                       
/*  731 */                       PCF = dueDate;
/*  732 */                       PCFcom = completionDate;
/*  733 */                       PCFvend = taskVendor;
/*      */                     }
/*  735 */                     else if (taskAbbrev.equalsIgnoreCase("FPC")) {
/*      */                       
/*  737 */                       FPC = dueDate;
/*  738 */                       FPCcom = completionDate;
/*  739 */                       FPCvend = taskVendor;
/*      */                     }
/*  741 */                     else if (taskAbbrev.equalsIgnoreCase("FAD")) {
/*      */                       
/*  743 */                       FAD = dueDate;
/*  744 */                       FADcom = completionDate;
/*  745 */                       FADvend = taskVendor;
/*      */                     }
/*  747 */                     else if (taskAbbrev.equalsIgnoreCase("MA")) {
/*      */                       
/*  749 */                       MA = dueDate;
/*  750 */                       MAcom = completionDate;
/*  751 */                       MAvend = taskVendor;
/*      */                     }
/*  753 */                     else if (taskAbbrev.equalsIgnoreCase("LC")) {
/*      */                       
/*  755 */                       LC = dueDate;
/*  756 */                       LCcom = completionDate;
/*  757 */                       LCvend = taskVendor;
/*      */                     }
/*  759 */                     else if (taskAbbrev.equalsIgnoreCase("PO")) {
/*      */                       
/*  761 */                       PO = dueDate;
/*  762 */                       POcom = completionDate;
/*  763 */                       POvend = taskVendor;
/*      */                     }
/*  765 */                     else if (taskAbbrev.equalsIgnoreCase("SEPS")) {
/*      */                       
/*  767 */                       SEPS = dueDate;
/*  768 */                       SEPScom = completionDate;
/*  769 */                       SEPSvend = taskVendor;
/*      */                     }
/*  771 */                     else if (taskAbbrev.equalsIgnoreCase("STD")) {
/*      */                       
/*  773 */                       STD = dueDate;
/*  774 */                       STDcom = completionDate;
/*  775 */                       STDvend = taskVendor;
/*      */                     }
/*  777 */                     else if (taskAbbrev.equalsIgnoreCase("SFS")) {
/*      */                       
/*  779 */                       SFS = dueDate;
/*  780 */                       SFScom = completionDate;
/*  781 */                       SFSvend = taskVendor;
/*      */                     }
/*  783 */                     else if (taskAbbrev.equalsIgnoreCase("PFS")) {
/*      */                       
/*  785 */                       PFS = dueDate;
/*  786 */                       PFScom = completionDate;
/*  787 */                       PFSvend = taskVendor;
/*      */                     }
/*  789 */                     else if (taskAbbrev.equalsIgnoreCase("TPS")) {
/*      */                       
/*  791 */                       TPS = dueDate;
/*  792 */                       TPScom = completionDate;
/*  793 */                       TPSvend = taskVendor;
/*      */                     }
/*  795 */                     else if (taskAbbrev.equalsIgnoreCase("BMS")) {
/*      */                       
/*  797 */                       BMS = dueDate;
/*  798 */                       BMScom = completionDate;
/*  799 */                       BMSvend = taskVendor;
/*      */                     }
/*  801 */                     else if (taskAbbrev.equalsIgnoreCase("TA")) {
/*      */                       
/*  803 */                       TA = dueDate;
/*  804 */                       TAcom = completionDate;
/*  805 */                       TAvend = taskVendor;
/*      */                     }
/*  807 */                     else if (taskAbbrev.equalsIgnoreCase("DJHO")) {
/*      */                       
/*  809 */                       DJHO = dueDate;
/*  810 */                       DJHOcom = completionDate;
/*  811 */                       DJHOvend = taskVendor;
/*      */                     }
/*  813 */                     else if (taskAbbrev.equalsIgnoreCase("DJFS")) {
/*      */                       
/*  815 */                       DJFS = dueDate;
/*  816 */                       DJFScom = completionDate;
/*  817 */                       DJFSvend = taskVendor;
/*      */                     }
/*  819 */                     else if (taskAbbrev.equalsIgnoreCase("DJR")) {
/*      */                       
/*  821 */                       DJRvend = taskVendor;
/*      */                     } 
/*      */                     
/*  824 */                     task = null;
/*      */                   } 
/*      */                 } 
/*      */               }
/*      */ 
/*      */ 
/*      */               
/*  831 */               nextRow = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  836 */               if (multOtherContacts.size() > 4) {
/*  837 */                 subTableRows = 3 + multOtherContacts.size();
/*      */               } else {
/*  839 */                 subTableRows = 7;
/*      */               } 
/*      */ 
/*      */               
/*  843 */               subTable = new DefaultTableLens(subTableRows, 14);
/*      */ 
/*      */               
/*  846 */               subTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
/*      */ 
/*      */               
/*  849 */               setColBorderColor(subTable, nextRow, 14, SHADED_AREA_COLOR);
/*      */               
/*  851 */               subTable.setHeaderRowCount(0);
/*  852 */               subTable.setColWidth(0, 150);
/*  853 */               subTable.setColWidth(1, 259);
/*  854 */               subTable.setColWidth(2, 130);
/*  855 */               subTable.setColWidth(3, 75);
/*  856 */               subTable.setColWidth(4, 75);
/*  857 */               subTable.setColWidth(5, 75);
/*  858 */               subTable.setColWidth(6, 75);
/*  859 */               subTable.setColWidth(7, 75);
/*  860 */               subTable.setColWidth(8, 75);
/*  861 */               subTable.setColWidth(9, 75);
/*  862 */               subTable.setColWidth(10, 75);
/*  863 */               subTable.setColWidth(11, 75);
/*  864 */               subTable.setColWidth(12, 75);
/*  865 */               subTable.setColWidth(13, 75);
/*      */               
/*  867 */               subTable.setRowBorderColor(nextRow - 1, Color.lightGray);
/*      */               
/*  869 */               subTable.setRowBorderColor(nextRow, 0, Color.white);
/*  870 */               subTable.setRowBorderColor(nextRow, 1, SHADED_AREA_COLOR);
/*  871 */               subTable.setRowBorderColor(nextRow, 2, SHADED_AREA_COLOR);
/*  872 */               subTable.setRowBorderColor(nextRow, 3, SHADED_AREA_COLOR);
/*  873 */               subTable.setRowBorderColor(nextRow, 4, SHADED_AREA_COLOR);
/*  874 */               subTable.setRowBorderColor(nextRow, 5, SHADED_AREA_COLOR);
/*  875 */               subTable.setRowBorderColor(nextRow, 6, SHADED_AREA_COLOR);
/*  876 */               subTable.setRowBorderColor(nextRow, 7, SHADED_AREA_COLOR);
/*  877 */               subTable.setRowBorderColor(nextRow, 8, SHADED_AREA_COLOR);
/*  878 */               subTable.setRowBorderColor(nextRow, 9, SHADED_AREA_COLOR);
/*  879 */               subTable.setRowBorderColor(nextRow, 10, SHADED_AREA_COLOR);
/*  880 */               subTable.setRowBorderColor(nextRow, 11, SHADED_AREA_COLOR);
/*  881 */               subTable.setRowBorderColor(nextRow, 12, SHADED_AREA_COLOR);
/*  882 */               subTable.setRowBorderColor(nextRow, 13, SHADED_AREA_COLOR);
/*      */               
/*  884 */               subTable.setObject(nextRow, 0, selectionNo);
/*  885 */               subTable.setBackground(nextRow, 0, Color.white);
/*  886 */               subTable.setAlignment(nextRow, 0, 9);
/*  887 */               subTable.setFont(nextRow, 0, new Font("Arial", 0, 7));
/*      */               
/*  889 */               subTable.setObject(nextRow, 1, "Due Dates");
/*  890 */               subTable.setSpan(nextRow, 1, new Dimension(1, 1));
/*  891 */               subTable.setBackground(nextRow, 1, SHADED_AREA_COLOR);
/*  892 */               subTable.setAlignment(nextRow, 1, 12);
/*      */               
/*  894 */               String[] checkStrings = { comment, artist, title, pack, label };
/*  895 */               int[] checkStringsLength = { 20, 30, 30, 20, 25 };
/*      */               
/*  897 */               int extraLines = MilestoneHelper.lineCountWCR(checkStrings, checkStringsLength);
/*      */               
/*  899 */               String[] commentString = { comment };
/*  900 */               int[] checkCommentLength = { 30 };
/*  901 */               int commentCounter = MilestoneHelper.lineCountWCR(commentString, checkCommentLength);
/*      */ 
/*      */               
/*  904 */               if (extraLines <= 2) {
/*  905 */                 extraLines--;
/*      */               } else {
/*  907 */                 extraLines--;
/*      */               } 
/*  909 */               subTable.setObject(nextRow, 2, SFM);
/*  910 */               subTable.setAlignment(nextRow, 2, 10);
/*  911 */               subTable.setBackground(nextRow, 2, SHADED_AREA_COLOR);
/*      */               
/*  913 */               subTable.setObject(nextRow, 3, PCF);
/*  914 */               subTable.setAlignment(nextRow, 3, 10);
/*  915 */               subTable.setColBorderColor(nextRow, 3, SHADED_AREA_COLOR);
/*  916 */               subTable.setColBorder(nextRow, 3, 266240);
/*      */               
/*  918 */               subTable.setObject(nextRow, 4, FPC);
/*  919 */               subTable.setAlignment(nextRow, 4, 10);
/*  920 */               subTable.setBackground(nextRow, 4, SHADED_AREA_COLOR);
/*  921 */               subTable.setColBorder(nextRow, 4, 266240);
/*      */               
/*  923 */               subTable.setObject(nextRow, 5, FAD);
/*  924 */               subTable.setAlignment(nextRow, 5, 10);
/*  925 */               subTable.setBackground(nextRow, 5, SHADED_AREA_COLOR);
/*  926 */               subTable.setColBorder(nextRow, 5, 266240);
/*      */               
/*  928 */               subTable.setObject(nextRow, 6, MA);
/*  929 */               subTable.setAlignment(nextRow, 6, 10);
/*  930 */               subTable.setBackground(nextRow, 6, SHADED_AREA_COLOR);
/*  931 */               subTable.setColBorder(nextRow, 6, 266240);
/*      */               
/*  933 */               subTable.setObject(nextRow, 7, PO);
/*  934 */               subTable.setAlignment(nextRow, 7, 10);
/*  935 */               subTable.setBackground(nextRow, 7, SHADED_AREA_COLOR);
/*  936 */               subTable.setColBorder(nextRow, 7, 266240);
/*      */               
/*  938 */               subTable.setObject(nextRow, 8, PFS);
/*  939 */               subTable.setAlignment(nextRow, 8, 10);
/*  940 */               subTable.setBackground(nextRow, 8, SHADED_AREA_COLOR);
/*  941 */               subTable.setColBorder(nextRow, 8, 266240);
/*      */               
/*  943 */               subTable.setObject(nextRow, 9, BMS);
/*  944 */               subTable.setAlignment(nextRow, 9, 10);
/*  945 */               subTable.setBackground(nextRow, 9, SHADED_AREA_COLOR);
/*  946 */               subTable.setColBorder(nextRow, 9, 266240);
/*      */               
/*  948 */               subTable.setObject(nextRow, 10, TA);
/*  949 */               subTable.setAlignment(nextRow, 10, 10);
/*  950 */               subTable.setBackground(nextRow, 10, SHADED_AREA_COLOR);
/*  951 */               subTable.setColBorder(nextRow, 10, 266240);
/*      */               
/*  953 */               subTable.setObject(nextRow, 11, DJHO);
/*  954 */               subTable.setAlignment(nextRow, 11, 10);
/*  955 */               subTable.setBackground(nextRow, 11, SHADED_AREA_COLOR);
/*  956 */               subTable.setColBorder(nextRow, 11, 266240);
/*      */               
/*  958 */               subTable.setObject(nextRow, 12, DJFS);
/*  959 */               subTable.setAlignment(nextRow, 12, 10);
/*  960 */               subTable.setBackground(nextRow, 12, SHADED_AREA_COLOR);
/*  961 */               subTable.setColBorder(nextRow, 12, 266240);
/*      */               
/*  963 */               SimpleDateFormat dueDateFormatter = new SimpleDateFormat("M/d");
/*  964 */               if (monthNameString.equals("")) {
/*  965 */                 subTable.setObject(nextRow, 13, "");
/*      */               } else {
/*  967 */                 Calendar streetDateCal = MilestoneHelper.getDate(monthNameString);
/*  968 */                 String formattedStreetDate = "";
/*      */                 try {
/*  970 */                   formattedStreetDate = dueDateFormatter.format(streetDateCal.getTime());
/*  971 */                 } catch (Exception exception) {}
/*  972 */                 subTable.setObject(nextRow, 13, formattedStreetDate);
/*      */               } 
/*  974 */               subTable.setAlignment(nextRow, 13, 10);
/*  975 */               subTable.setBackground(nextRow, 13, SHADED_AREA_COLOR);
/*  976 */               subTable.setColBorder(nextRow, 13, 266240);
/*      */               
/*  978 */               subTable.setAlignment(nextRow, 6, 10);
/*  979 */               subTable.setAlignment(nextRow + 1, 4, 10);
/*  980 */               subTable.setAlignment(nextRow + 1, 6, 10);
/*      */               
/*  982 */               subTable.setFont(nextRow, 1, new Font("Arial", 1, 7));
/*  983 */               subTable.setFont(nextRow, 2, new Font("Arial", 1, 7));
/*  984 */               subTable.setFont(nextRow, 3, new Font("Arial", 1, 7));
/*  985 */               subTable.setFont(nextRow, 4, new Font("Arial", 1, 7));
/*  986 */               subTable.setFont(nextRow, 5, new Font("Arial", 1, 7));
/*  987 */               subTable.setFont(nextRow, 6, new Font("Arial", 1, 7));
/*  988 */               subTable.setFont(nextRow, 7, new Font("Arial", 1, 7));
/*  989 */               subTable.setFont(nextRow, 8, new Font("Arial", 1, 7));
/*  990 */               subTable.setFont(nextRow, 9, new Font("Arial", 1, 7));
/*  991 */               subTable.setFont(nextRow, 10, new Font("Arial", 1, 7));
/*  992 */               subTable.setFont(nextRow, 11, new Font("Arial", 1, 7));
/*  993 */               subTable.setFont(nextRow, 12, new Font("Arial", 1, 7));
/*  994 */               subTable.setFont(nextRow, 13, new Font("Arial", 1, 7));
/*      */               
/*  996 */               subTable.setRowFont(nextRow + 1, new Font("Arial", 0, 7));
/*  997 */               subTable.setRowBackground(nextRow, SHADED_AREA_COLOR);
/*  998 */               subTable.setRowForeground(nextRow, Color.black);
/*      */ 
/*      */               
/* 1001 */               subTable.setRowBorderColor(nextRow + 1, Color.white);
/* 1002 */               subTable.setAlignment(nextRow + 1, 0, 9);
/* 1003 */               subTable.setObject(nextRow + 1, 0, artist);
/* 1004 */               subTable.setFont(nextRow + 1, 0, new Font("Arial", 1, 7));
/*      */               
/* 1006 */               if (multOtherContacts.size() == 0) {
/* 1007 */                 subTable.setObject(nextRow + 1, 1, "");
/*      */               } else {
/* 1009 */                 subTable.setObject(nextRow + 1, 1, multOtherContacts.elementAt(0));
/*      */               } 
/* 1011 */               subTable.setFont(nextRow + 1, 1, new Font("Arial", 1, 7));
/*      */               
/* 1013 */               subTable.setObject(nextRow + 1, 2, SFMcom);
/* 1014 */               subTable.setObject(nextRow + 1, 3, PCFcom);
/* 1015 */               subTable.setObject(nextRow + 1, 4, FPCcom);
/* 1016 */               subTable.setObject(nextRow + 1, 5, FADcom);
/* 1017 */               subTable.setObject(nextRow + 1, 6, MAcom);
/* 1018 */               subTable.setObject(nextRow + 1, 7, POcom);
/* 1019 */               subTable.setObject(nextRow + 1, 8, PFScom);
/* 1020 */               subTable.setObject(nextRow + 1, 9, BMScom);
/* 1021 */               subTable.setObject(nextRow + 1, 10, TAcom);
/* 1022 */               subTable.setObject(nextRow + 1, 11, DJHOcom);
/* 1023 */               subTable.setObject(nextRow + 1, 12, DJFScom);
/*      */ 
/*      */               
/* 1026 */               setColBorderColor(subTable, nextRow + 1, 14, SHADED_AREA_COLOR);
/*      */               
/* 1028 */               subTable.setAlignment(nextRow + 1, 1, 9);
/* 1029 */               subTable.setAlignment(nextRow + 1, 2, 10);
/* 1030 */               subTable.setAlignment(nextRow + 1, 3, 10);
/* 1031 */               subTable.setAlignment(nextRow + 1, 4, 10);
/* 1032 */               subTable.setAlignment(nextRow + 1, 5, 10);
/* 1033 */               subTable.setAlignment(nextRow + 1, 6, 10);
/* 1034 */               subTable.setAlignment(nextRow + 1, 7, 10);
/* 1035 */               subTable.setAlignment(nextRow + 1, 8, 10);
/* 1036 */               subTable.setAlignment(nextRow + 1, 9, 10);
/* 1037 */               subTable.setAlignment(nextRow + 1, 10, 10);
/* 1038 */               subTable.setAlignment(nextRow + 1, 11, 10);
/* 1039 */               subTable.setAlignment(nextRow + 1, 12, 10);
/* 1040 */               subTable.setAlignment(nextRow + 1, 13, 10);
/*      */ 
/*      */               
/* 1043 */               nextRow++;
/* 1044 */               subTable.setFont(nextRow + 1, 0, new Font("Arial", 0, 7));
/* 1045 */               subTable.setFont(nextRow + 1, 1, new Font("Arial", 1, 7));
/* 1046 */               subTable.setFont(nextRow + 1, 2, new Font("Arial", 0, 7));
/* 1047 */               subTable.setFont(nextRow + 1, 3, new Font("Arial", 0, 7));
/* 1048 */               subTable.setFont(nextRow + 1, 4, new Font("Arial", 0, 7));
/* 1049 */               subTable.setFont(nextRow + 1, 5, new Font("Arial", 0, 7));
/* 1050 */               subTable.setFont(nextRow + 1, 6, new Font("Arial", 0, 7));
/* 1051 */               subTable.setFont(nextRow + 1, 7, new Font("Arial", 0, 7));
/* 1052 */               subTable.setFont(nextRow + 1, 8, new Font("Arial", 0, 7));
/* 1053 */               subTable.setFont(nextRow + 1, 9, new Font("Arial", 0, 7));
/* 1054 */               subTable.setFont(nextRow + 1, 10, new Font("Arial", 0, 7));
/* 1055 */               subTable.setFont(nextRow + 1, 11, new Font("Arial", 0, 7));
/* 1056 */               subTable.setFont(nextRow + 1, 12, new Font("Arial", 0, 7));
/* 1057 */               subTable.setFont(nextRow + 1, 13, new Font("Arial", 0, 7));
/* 1058 */               setColBorderColor(subTable, nextRow + 1, 14, SHADED_AREA_COLOR);
/* 1059 */               subTable.setRowBorderColor(nextRow + 1, Color.white);
/* 1060 */               subTable.setRowHeight(nextRow + 1, 9);
/*      */               
/* 1062 */               if (multOtherContacts.size() < 2) {
/* 1063 */                 subTable.setObject(nextRow + 1, 1, "");
/*      */               } else {
/* 1065 */                 subTable.setObject(nextRow + 1, 1, multOtherContacts.elementAt(1));
/*      */               } 
/*      */               
/* 1068 */               subTable.setObject(nextRow + 1, 2, SFMvend);
/* 1069 */               subTable.setObject(nextRow + 1, 3, PCFvend);
/* 1070 */               subTable.setObject(nextRow + 1, 4, FPCvend);
/* 1071 */               subTable.setObject(nextRow + 1, 5, FADvend);
/* 1072 */               subTable.setObject(nextRow + 1, 6, LCcom);
/* 1073 */               subTable.setObject(nextRow + 1, 7, POvend);
/* 1074 */               subTable.setObject(nextRow + 1, 8, TPScom);
/* 1075 */               subTable.setObject(nextRow + 1, 9, BMSvend);
/* 1076 */               subTable.setObject(nextRow + 1, 10, TAcom);
/* 1077 */               subTable.setObject(nextRow + 1, 11, DJHOcom);
/* 1078 */               subTable.setObject(nextRow + 1, 12, DJFSvend);
/* 1079 */               subTable.setObject(nextRow + 1, 13, DJRvend);
/*      */               
/* 1081 */               subTable.setAlignment(nextRow + 1, 0, 10);
/* 1082 */               subTable.setAlignment(nextRow + 1, 1, 9);
/* 1083 */               subTable.setAlignment(nextRow + 1, 2, 10);
/* 1084 */               subTable.setAlignment(nextRow + 1, 3, 10);
/* 1085 */               subTable.setAlignment(nextRow + 1, 4, 10);
/* 1086 */               subTable.setAlignment(nextRow + 1, 5, 10);
/* 1087 */               subTable.setAlignment(nextRow + 1, 6, 10);
/* 1088 */               subTable.setAlignment(nextRow + 1, 7, 10);
/* 1089 */               subTable.setAlignment(nextRow + 1, 8, 10);
/* 1090 */               subTable.setAlignment(nextRow + 1, 9, 10);
/* 1091 */               subTable.setAlignment(nextRow + 1, 10, 10);
/* 1092 */               subTable.setAlignment(nextRow + 1, 11, 10);
/* 1093 */               subTable.setAlignment(nextRow + 1, 12, 10);
/* 1094 */               subTable.setAlignment(nextRow + 1, 13, 10);
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1099 */               nextRow++;
/* 1100 */               subTable.setFont(nextRow + 1, 0, new Font("Arial", 0, 7));
/* 1101 */               subTable.setFont(nextRow + 1, 1, new Font("Arial", 1, 7));
/* 1102 */               subTable.setFont(nextRow + 1, 2, new Font("Arial", 0, 7));
/* 1103 */               subTable.setFont(nextRow + 1, 3, new Font("Arial", 0, 7));
/* 1104 */               subTable.setFont(nextRow + 1, 4, new Font("Arial", 0, 7));
/* 1105 */               subTable.setFont(nextRow + 1, 5, new Font("Arial", 0, 7));
/* 1106 */               subTable.setFont(nextRow + 1, 6, new Font("Arial", 0, 7));
/* 1107 */               subTable.setFont(nextRow + 1, 7, new Font("Arial", 0, 7));
/* 1108 */               subTable.setFont(nextRow + 1, 8, new Font("Arial", 0, 7));
/* 1109 */               subTable.setFont(nextRow + 1, 9, new Font("Arial", 0, 7));
/* 1110 */               subTable.setFont(nextRow + 1, 10, new Font("Arial", 0, 7));
/* 1111 */               subTable.setFont(nextRow + 1, 11, new Font("Arial", 0, 7));
/* 1112 */               subTable.setFont(nextRow + 1, 12, new Font("Arial", 0, 7));
/* 1113 */               subTable.setFont(nextRow + 1, 13, new Font("Arial", 0, 7));
/* 1114 */               setColBorderColor(subTable, nextRow + 1, 14, SHADED_AREA_COLOR);
/* 1115 */               subTable.setRowBorderColor(nextRow + 1, Color.white);
/* 1116 */               subTable.setRowHeight(nextRow + 1, 9);
/* 1117 */               subTable.setObject(nextRow + 1, 8, MAvend);
/* 1118 */               if (multOtherContacts.size() < 3) {
/* 1119 */                 subTable.setObject(nextRow + 1, 1, "");
/*      */               } else {
/* 1121 */                 subTable.setObject(nextRow + 1, 1, multOtherContacts.elementAt(2));
/*      */               } 
/* 1123 */               subTable.setObject(nextRow + 1, 6, MAvend);
/* 1124 */               subTable.setObject(nextRow + 1, 8, PFScom);
/* 1125 */               subTable.setObject(nextRow + 1, 11, DJHOvend);
/* 1126 */               subTable.setObject(nextRow + 1, 10, TAvend);
/*      */               
/* 1128 */               subTable.setAlignment(nextRow + 1, 0, 10);
/* 1129 */               subTable.setAlignment(nextRow + 1, 1, 9);
/* 1130 */               subTable.setAlignment(nextRow + 1, 2, 10);
/* 1131 */               subTable.setAlignment(nextRow + 1, 3, 10);
/* 1132 */               subTable.setAlignment(nextRow + 1, 4, 10);
/* 1133 */               subTable.setAlignment(nextRow + 1, 5, 10);
/* 1134 */               subTable.setAlignment(nextRow + 1, 6, 10);
/* 1135 */               subTable.setAlignment(nextRow + 1, 7, 10);
/* 1136 */               subTable.setAlignment(nextRow + 1, 8, 10);
/* 1137 */               subTable.setAlignment(nextRow + 1, 9, 10);
/* 1138 */               subTable.setAlignment(nextRow + 1, 10, 10);
/* 1139 */               subTable.setAlignment(nextRow + 1, 11, 10);
/* 1140 */               subTable.setAlignment(nextRow + 1, 12, 10);
/* 1141 */               subTable.setAlignment(nextRow + 1, 13, 10);
/*      */ 
/*      */ 
/*      */               
/* 1145 */               nextRow++;
/* 1146 */               subTable.setFont(nextRow + 1, 0, new Font("Arial", 0, 7));
/* 1147 */               subTable.setFont(nextRow + 1, 1, new Font("Arial", 1, 7));
/* 1148 */               subTable.setFont(nextRow + 1, 2, new Font("Arial", 0, 7));
/* 1149 */               subTable.setFont(nextRow + 1, 3, new Font("Arial", 0, 7));
/* 1150 */               subTable.setFont(nextRow + 1, 4, new Font("Arial", 0, 7));
/* 1151 */               subTable.setFont(nextRow + 1, 5, new Font("Arial", 0, 7));
/* 1152 */               subTable.setFont(nextRow + 1, 6, new Font("Arial", 0, 7));
/* 1153 */               subTable.setFont(nextRow + 1, 7, new Font("Arial", 0, 7));
/* 1154 */               subTable.setFont(nextRow + 1, 8, new Font("Arial", 0, 7));
/* 1155 */               subTable.setFont(nextRow + 1, 9, new Font("Arial", 0, 7));
/* 1156 */               subTable.setFont(nextRow + 1, 10, new Font("Arial", 0, 7));
/* 1157 */               subTable.setFont(nextRow + 1, 11, new Font("Arial", 0, 7));
/* 1158 */               subTable.setFont(nextRow + 1, 12, new Font("Arial", 0, 7));
/* 1159 */               subTable.setFont(nextRow + 1, 13, new Font("Arial", 0, 7));
/* 1160 */               setColBorderColor(subTable, nextRow + 1, 14, SHADED_AREA_COLOR);
/* 1161 */               subTable.setRowBorderColor(nextRow + 1, Color.white);
/* 1162 */               subTable.setRowHeight(nextRow + 1, 9);
/* 1163 */               subTable.setObject(nextRow + 1, 8, LCvend);
/* 1164 */               if (multOtherContacts.size() < 4) {
/* 1165 */                 subTable.setObject(nextRow + 1, 1, "");
/*      */               } else {
/* 1167 */                 subTable.setObject(nextRow + 1, 1, multOtherContacts.elementAt(3));
/*      */               } 
/* 1169 */               subTable.setObject(nextRow + 1, 6, LCvend);
/* 1170 */               subTable.setObject(nextRow + 1, 8, TPSvend);
/* 1171 */               subTable.setAlignment(nextRow + 1, 0, 10);
/* 1172 */               subTable.setAlignment(nextRow + 1, 1, 9);
/* 1173 */               subTable.setAlignment(nextRow + 1, 2, 10);
/* 1174 */               subTable.setAlignment(nextRow + 1, 3, 10);
/* 1175 */               subTable.setAlignment(nextRow + 1, 4, 10);
/* 1176 */               subTable.setAlignment(nextRow + 1, 5, 10);
/* 1177 */               subTable.setAlignment(nextRow + 1, 6, 10);
/* 1178 */               subTable.setAlignment(nextRow + 1, 7, 10);
/* 1179 */               subTable.setAlignment(nextRow + 1, 8, 10);
/* 1180 */               subTable.setAlignment(nextRow + 1, 9, 10);
/* 1181 */               subTable.setAlignment(nextRow + 1, 10, 10);
/* 1182 */               subTable.setAlignment(nextRow + 1, 11, 10);
/* 1183 */               subTable.setAlignment(nextRow + 1, 12, 10);
/* 1184 */               subTable.setAlignment(nextRow + 1, 13, 10);
/*      */ 
/*      */               
/* 1187 */               if (multOtherContacts.size() > 4) {
/* 1188 */                 int extraRows = multOtherContacts.size() - 4;
/* 1189 */                 for (int k = 0; k < extraRows; k++) {
/* 1190 */                   nextRow++;
/* 1191 */                   subTable.setFont(nextRow + 1, 0, new Font("Arial", 0, 7));
/* 1192 */                   subTable.setFont(nextRow + 1, 1, new Font("Arial", 1, 7));
/* 1193 */                   subTable.setFont(nextRow + 1, 2, new Font("Arial", 0, 7));
/* 1194 */                   subTable.setFont(nextRow + 1, 3, new Font("Arial", 0, 7));
/* 1195 */                   subTable.setFont(nextRow + 1, 4, new Font("Arial", 0, 7));
/* 1196 */                   subTable.setFont(nextRow + 1, 5, new Font("Arial", 0, 7));
/* 1197 */                   subTable.setFont(nextRow + 1, 6, new Font("Arial", 0, 7));
/* 1198 */                   subTable.setFont(nextRow + 1, 7, new Font("Arial", 0, 7));
/* 1199 */                   subTable.setFont(nextRow + 1, 8, new Font("Arial", 0, 7));
/* 1200 */                   subTable.setFont(nextRow + 1, 9, new Font("Arial", 0, 7));
/* 1201 */                   subTable.setFont(nextRow + 1, 10, new Font("Arial", 0, 7));
/* 1202 */                   subTable.setFont(nextRow + 1, 11, new Font("Arial", 0, 7));
/* 1203 */                   subTable.setFont(nextRow + 1, 12, new Font("Arial", 0, 7));
/* 1204 */                   subTable.setFont(nextRow + 1, 13, new Font("Arial", 0, 7));
/*      */                   
/* 1206 */                   subTable.setRowHeight(nextRow + 1, 9);
/* 1207 */                   setColBorderColor(subTable, nextRow + 1, 14, SHADED_AREA_COLOR);
/* 1208 */                   subTable.setRowBorderColor(nextRow + 1, Color.white);
/*      */                   
/* 1210 */                   subTable.setAlignment(nextRow + 1, 0, 10);
/* 1211 */                   subTable.setAlignment(nextRow + 1, 1, 9);
/* 1212 */                   subTable.setAlignment(nextRow + 1, 2, 10);
/* 1213 */                   subTable.setAlignment(nextRow + 1, 3, 10);
/* 1214 */                   subTable.setAlignment(nextRow + 1, 4, 10);
/* 1215 */                   subTable.setAlignment(nextRow + 1, 5, 10);
/* 1216 */                   subTable.setAlignment(nextRow + 1, 6, 10);
/* 1217 */                   subTable.setAlignment(nextRow + 1, 7, 10);
/* 1218 */                   subTable.setAlignment(nextRow + 1, 8, 10);
/* 1219 */                   subTable.setAlignment(nextRow + 1, 9, 10);
/* 1220 */                   subTable.setAlignment(nextRow + 1, 10, 10);
/* 1221 */                   subTable.setAlignment(nextRow + 1, 11, 10);
/* 1222 */                   subTable.setAlignment(nextRow + 1, 12, 10);
/* 1223 */                   subTable.setAlignment(nextRow + 1, 13, 10);
/*      */                   
/* 1225 */                   subTable.setObject(nextRow + 1, 1, multOtherContacts.elementAt(k + 4));
/*      */                 } 
/*      */               } 
/*      */ 
/*      */               
/* 1230 */               nextRow++;
/* 1231 */               subTable.setRowBackground(nextRow + 1, SHADED_AREA_COLOR);
/* 1232 */               subTable.setColBorderColor(nextRow + 1, -1, SHADED_AREA_COLOR);
/* 1233 */               subTable.setColBorderColor(nextRow + 1, 0, SHADED_AREA_COLOR);
/* 1234 */               subTable.setColBorderColor(nextRow + 1, 1, SHADED_AREA_COLOR);
/* 1235 */               subTable.setColBorderColor(nextRow + 1, 2, Color.white);
/* 1236 */               subTable.setColBorderColor(nextRow + 1, 3, Color.white);
/* 1237 */               subTable.setColBorderColor(nextRow + 1, 4, Color.white);
/* 1238 */               subTable.setColBorderColor(nextRow + 1, 5, Color.white);
/* 1239 */               subTable.setColBorderColor(nextRow + 1, 6, Color.white);
/* 1240 */               subTable.setColBorderColor(nextRow + 1, 7, Color.white);
/* 1241 */               subTable.setColBorderColor(nextRow + 1, 8, Color.white);
/* 1242 */               subTable.setColBorderColor(nextRow + 1, 9, Color.white);
/* 1243 */               subTable.setColBorderColor(nextRow + 1, 10, Color.white);
/* 1244 */               subTable.setColBorderColor(nextRow + 1, 11, Color.white);
/* 1245 */               subTable.setColBorderColor(nextRow + 1, 12, Color.white);
/* 1246 */               subTable.setColBorderColor(nextRow + 1, 13, SHADED_AREA_COLOR);
/* 1247 */               subTable.setRowBorderColor(nextRow + 1, SHADED_AREA_COLOR);
/*      */               
/* 1249 */               subTable.setSpan(nextRow + 1, 0, new Dimension(1, 1));
/* 1250 */               subTable.setSpan(nextRow + 1, 1, new Dimension(13, 1));
/* 1251 */               subTable.setAlignment(nextRow + 1, 1, 9);
/* 1252 */               subTable.setFont(nextRow + 1, 0, new Font("Arial", 1, 7));
/* 1253 */               subTable.setFont(nextRow + 1, 1, new Font("Arial", 0, 7));
/* 1254 */               subTable.setObject(nextRow + 1, 0, "Packaging Specs:");
/* 1255 */               subTable.setObject(nextRow + 1, 1, pack);
/*      */ 
/*      */               
/* 1258 */               nextRow++;
/* 1259 */               subTable.setColBorderColor(nextRow + 1, -1, SHADED_AREA_COLOR);
/* 1260 */               subTable.setColBorderColor(nextRow + 1, 0, Color.white);
/* 1261 */               subTable.setColBorderColor(nextRow + 1, 1, Color.white);
/* 1262 */               subTable.setColBorderColor(nextRow + 1, 2, Color.white);
/* 1263 */               subTable.setColBorderColor(nextRow + 1, 3, Color.white);
/* 1264 */               subTable.setColBorderColor(nextRow + 1, 4, Color.white);
/* 1265 */               subTable.setColBorderColor(nextRow + 1, 5, Color.white);
/* 1266 */               subTable.setColBorderColor(nextRow + 1, 6, Color.white);
/* 1267 */               subTable.setColBorderColor(nextRow + 1, 7, Color.white);
/* 1268 */               subTable.setColBorderColor(nextRow + 1, 8, Color.white);
/* 1269 */               subTable.setColBorderColor(nextRow + 1, 9, Color.white);
/* 1270 */               subTable.setColBorderColor(nextRow + 1, 10, Color.white);
/* 1271 */               subTable.setColBorderColor(nextRow + 1, 11, Color.white);
/* 1272 */               subTable.setColBorderColor(nextRow + 1, 12, Color.white);
/* 1273 */               subTable.setColBorderColor(nextRow + 1, 13, SHADED_AREA_COLOR);
/* 1274 */               subTable.setRowBorderColor(nextRow + 1, SHADED_AREA_COLOR);
/*      */               
/* 1276 */               subTable.setSpan(nextRow + 1, 0, new Dimension(1, 1));
/* 1277 */               subTable.setSpan(nextRow + 1, 1, new Dimension(13, 1));
/* 1278 */               subTable.setFont(nextRow + 1, 0, new Font("Arial", 1, 7));
/* 1279 */               subTable.setFont(nextRow + 1, 1, new Font("Arial", 0, 7));
/* 1280 */               subTable.setObject(nextRow + 1, 0, "Comments:");
/* 1281 */               subTable.setObject(nextRow + 1, 1, newComment);
/*      */               
/* 1283 */               body = new SectionBand(report);
/*      */               
/* 1285 */               double lfLineCount = 1.5D;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1338 */               body.setHeight(1.8F);
/*      */ 
/*      */ 
/*      */               
/* 1342 */               body.addTable(subTable, new Rectangle(800, 800));
/* 1343 */               body.setBottomBorder(0);
/* 1344 */               body.setTopBorder(0);
/* 1345 */               body.setShrinkToFit(true);
/* 1346 */               body.setVisible(true);
/* 1347 */               group = new DefaultSectionLens(null, group, body);
/*      */             } 
/*      */             
/* 1350 */             group = new DefaultSectionLens(null, group, spacer);
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
/* 1362 */         group = new DefaultSectionLens(hbandType, group, null);
/* 1363 */         report.addSection(group, rowCountTable);
/* 1364 */         report.addPageBreak();
/* 1365 */         group = null;
/*      */ 
/*      */       
/*      */       }
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 1373 */     catch (Exception e) {
/*      */       
/* 1375 */       System.out.println(">>>>>>>>fillDreamWorksRecordScheduleForPrint(): exception: " + e);
/* 1376 */       e.printStackTrace();
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
/*      */   private static void setColBorderColor(DefaultTableLens table, int row, int columns, Color color) {
/* 1388 */     for (int i = -1; i < columns; i++)
/*      */     {
/* 1390 */       table.setColBorderColor(row, i, color);
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
/*      */ 
/*      */   
/* 1409 */   public static String removeLF(String theString, int maxChars) { return theString.replace('\n', ' '); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 1426 */     Hashtable groupSelectionsByTypeConfigAndStreetDate = new Hashtable();
/* 1427 */     if (selections == null) {
/* 1428 */       return groupSelectionsByTypeConfigAndStreetDate;
/*      */     }
/* 1430 */     for (int i = 0; i < selections.size(); i++) {
/*      */       
/* 1432 */       Selection sel = (Selection)selections.elementAt(i);
/* 1433 */       if (sel != null) {
/*      */         
/* 1435 */         sel = SelectionManager.getInstance().getSelectionAndSchedule(sel.getSelectionID());
/*      */ 
/*      */         
/* 1438 */         String typeConfigString = "";
/* 1439 */         String dayString = "";
/* 1440 */         String typeString = (sel.getReleaseType() != null) ? sel.getReleaseType().getName() : "";
/* 1441 */         String configString = (sel.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation() != null) ? sel.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation() : "";
/*      */         
/* 1443 */         String productCatString = "";
/* 1444 */         ProductCategory productCat = sel.getProductCategory();
/* 1445 */         productCatString = productCat.getName();
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1450 */         if (typeString.startsWith("Promo")) {
/* 1451 */           if (productCatString.equals("Advances")) {
/*      */             
/* 1453 */             typeConfigString = "Full Length Advance Promos";
/*      */           }
/*      */           else {
/*      */             
/* 1457 */             typeConfigString = "Promos";
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1465 */           if (sel.getStreetDate() != null)
/*      */           {
/* 1467 */             dayString = MilestoneHelper.getFormatedDate(sel.getStreetDate());
/*      */           }
/*      */           
/* 1470 */           String statusString = "";
/* 1471 */           SelectionStatus status = sel.getSelectionStatus();
/*      */           
/* 1473 */           if (status != null) {
/* 1474 */             statusString = (status.getName() == null) ? "" : status.getName();
/*      */           }
/*      */           
/* 1477 */           Hashtable typeConfigSubTable = (Hashtable)groupSelectionsByTypeConfigAndStreetDate.get(typeConfigString);
/* 1478 */           if (typeConfigSubTable == null) {
/*      */ 
/*      */             
/* 1481 */             typeConfigSubTable = new Hashtable();
/* 1482 */             groupSelectionsByTypeConfigAndStreetDate.put(typeConfigString, typeConfigSubTable);
/*      */           } 
/*      */           
/* 1485 */           Vector selectionsForDates = (Vector)typeConfigSubTable.get(dayString);
/* 1486 */           if (selectionsForDates == null) {
/*      */ 
/*      */             
/* 1489 */             selectionsForDates = new Vector();
/* 1490 */             typeConfigSubTable.put(dayString, selectionsForDates);
/*      */           } 
/*      */ 
/*      */           
/* 1494 */           selectionsForDates.addElement(sel);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1498 */     return groupSelectionsByTypeConfigAndStreetDate;
/*      */   }
/*      */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\DreamWorksPromoScheduleForPrintSubHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */