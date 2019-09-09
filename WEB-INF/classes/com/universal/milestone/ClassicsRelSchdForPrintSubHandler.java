/*      */ package WEB-INF.classes.com.universal.milestone;
/*      */ 
/*      */ import com.techempower.ComponentLog;
/*      */ import com.techempower.gemini.Context;
/*      */ import com.techempower.gemini.GeminiApplication;
/*      */ import com.universal.milestone.ClassicsRelSchdForPrintSubHandler;
/*      */ import com.universal.milestone.Form;
/*      */ import com.universal.milestone.MilestoneHelper;
/*      */ import com.universal.milestone.MilestoneHelper_2;
/*      */ import com.universal.milestone.MonthYearComparator;
/*      */ import com.universal.milestone.SecureHandler;
/*      */ import com.universal.milestone.Selection;
/*      */ import com.universal.milestone.SelectionManager;
/*      */ import com.universal.milestone.StatusJSPupdate;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ClassicsRelSchdForPrintSubHandler
/*      */   extends SecureHandler
/*      */ {
/*      */   public static final String COMPONENT_CODE = "hCRsch";
/*      */   public GeminiApplication application;
/*      */   public ComponentLog log;
/*      */   
/*      */   public ClassicsRelSchdForPrintSubHandler(GeminiApplication application) {
/*   74 */     this.application = application;
/*   75 */     this.log = application.getLog("hCRsch");
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
/*      */   
/*      */   protected static void fillClassicsRelSchdForPrint(XStyleSheet report, Context context) {
/*   99 */     int COL_LINE_STYLE = 4097;
/*  100 */     int HEADER_FONT_SIZE = 12;
/*      */     
/*  102 */     double ldLineVal = 0.3D;
/*      */     
/*  104 */     StatusJSPupdate aStatusUpdate = new StatusJSPupdate(context);
/*      */ 
/*      */     
/*  107 */     aStatusUpdate.updateStatus(0, 0, "start_gathering", 0);
/*      */ 
/*      */     
/*  110 */     Vector selections1 = MilestoneHelper.getSelectionsForReport(context);
/*      */ 
/*      */     
/*  113 */     aStatusUpdate.updateStatus(0, 0, "start_report", 10);
/*      */ 
/*      */     
/*  116 */     aStatusUpdate.setInternalCounter(true);
/*      */     
/*  118 */     int DATA_FONT_SIZE = 7;
/*  119 */     int SMALL_HEADER_FONT_SIZE = 10;
/*  120 */     int NUM_COLUMNS = 15;
/*  121 */     Color SHADED_AREA_COLOR = Color.lightGray;
/*      */     
/*  123 */     boolean productCatagoryFlag = false;
/*      */     
/*  125 */     SectionBand hbandType = new SectionBand(report);
/*  126 */     SectionBand hbandCategory = new SectionBand(report);
/*  127 */     SectionBand hbandDate = new SectionBand(report);
/*  128 */     SectionBand hbandDiv = new SectionBand(report);
/*  129 */     SectionBand hbandProd = new SectionBand(report);
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
/*      */     
/*  147 */     Hashtable selTable = MilestoneHelper.groupSelectionsByMonthAndDayAndDivision(selections1);
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  152 */       DefaultTableLens table_contents = null;
/*  153 */       DefaultTableLens rowCountTable = null;
/*  154 */       DefaultTableLens columnHeaderTable = null;
/*  155 */       DefaultTableLens subTable = null;
/*  156 */       DefaultTableLens monthTableLens = null;
/*  157 */       DefaultTableLens dateTableLens = null;
/*  158 */       DefaultTableLens divTableLens = null;
/*  159 */       DefaultTableLens prodTableLens = null;
/*      */       
/*  161 */       rowCountTable = new DefaultTableLens(2, 10000);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  166 */       Form reportForm = (Form)context.getSessionValue("reportForm");
/*      */       
/*  168 */       Calendar beginStDate = (reportForm.getStringValue("beginDate") != null && 
/*  169 */         reportForm.getStringValue("beginDate").length() > 0) ? 
/*  170 */         MilestoneHelper.getDate(reportForm.getStringValue("beginDate")) : null;
/*      */       
/*  172 */       Calendar endStDate = (reportForm.getStringValue("endDate") != null && 
/*  173 */         reportForm.getStringValue("endDate").length() > 0) ? 
/*  174 */         MilestoneHelper.getDate(reportForm.getStringValue("endDate")) : null;
/*      */       
/*  176 */       report.setElement("crs_startdate", MilestoneHelper.getFormatedDate(beginStDate));
/*  177 */       report.setElement("crs_enddate", MilestoneHelper.getFormatedDate(endStDate));
/*      */       
/*  179 */       SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
/*  180 */       String todayLong = formatter.format(new Date());
/*  181 */       report.setElement("crs_bottomdate", todayLong);
/*      */       
/*  183 */       Calendar beginEffectiveDate = (reportForm.getStringValue("beginEffectiveDate") != null && reportForm.getStringValue("beginEffectiveDate").length() > 0) ? MilestoneHelper.getDate(reportForm.getStringValue("beginEffectiveDate")) : null;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  188 */       if (beginEffectiveDate != null) {
/*      */         
/*  190 */         beginEffectiveDate.set(11, 0);
/*  191 */         beginEffectiveDate.set(12, 0);
/*  192 */         beginEffectiveDate.set(13, 0);
/*  193 */         beginEffectiveDate.set(14, 0);
/*      */       } 
/*      */       
/*  196 */       Calendar endEffectiveDate = (reportForm.getStringValue("endEffectiveDate") != null && reportForm.getStringValue("endEffectiveDate").length() > 0) ? MilestoneHelper.getDate(reportForm.getStringValue("endEffectiveDate")) : null;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  201 */       if (endEffectiveDate != null) {
/*      */         
/*  203 */         endEffectiveDate.set(11, 23);
/*  204 */         endEffectiveDate.set(12, 59);
/*  205 */         endEffectiveDate.set(13, 59);
/*  206 */         endEffectiveDate.set(14, 999);
/*      */       } 
/*      */       
/*  209 */       int numColumns = 10;
/*      */ 
/*      */       
/*  212 */       hbandType = new SectionBand(report);
/*  213 */       hbandType.setHeight(0.95F);
/*  214 */       hbandType.setShrinkToFit(true);
/*  215 */       hbandType.setVisible(true);
/*      */       
/*  217 */       table_contents = new DefaultTableLens(1, numColumns);
/*      */ 
/*      */       
/*  220 */       table_contents.setHeaderRowCount(0);
/*  221 */       table_contents.setColWidth(0, 50);
/*  222 */       table_contents.setColWidth(1, 130);
/*  223 */       table_contents.setColWidth(2, 130);
/*  224 */       table_contents.setColWidth(3, 75);
/*  225 */       table_contents.setColWidth(4, 75);
/*  226 */       table_contents.setColWidth(5, 70);
/*  227 */       table_contents.setColWidth(6, 30);
/*  228 */       table_contents.setColWidth(7, 70);
/*  229 */       table_contents.setColWidth(8, 30);
/*  230 */       table_contents.setColWidth(9, 110);
/*      */ 
/*      */       
/*  233 */       int nextRow = 0;
/*      */       
/*  235 */       nextRow = 0;
/*      */       
/*  237 */       columnHeaderTable = new DefaultTableLens(1, numColumns);
/*      */ 
/*      */       
/*  240 */       columnHeaderTable.setHeaderRowCount(0);
/*  241 */       columnHeaderTable.setColWidth(0, 50);
/*  242 */       columnHeaderTable.setColWidth(1, 130);
/*  243 */       columnHeaderTable.setColWidth(2, 130);
/*  244 */       columnHeaderTable.setColWidth(3, 75);
/*  245 */       columnHeaderTable.setColWidth(4, 75);
/*  246 */       columnHeaderTable.setColWidth(5, 70);
/*  247 */       columnHeaderTable.setColWidth(6, 30);
/*  248 */       columnHeaderTable.setColWidth(7, 70);
/*  249 */       columnHeaderTable.setColWidth(8, 30);
/*  250 */       columnHeaderTable.setColWidth(9, 110);
/*      */       
/*  252 */       columnHeaderTable.setColBorder(0);
/*  253 */       columnHeaderTable.setRowBorder(nextRow - 1, 0);
/*  254 */       columnHeaderTable.setRowBorder(nextRow, 4097);
/*  255 */       columnHeaderTable.setRowBorderColor(nextRow, Color.black);
/*      */ 
/*      */ 
/*      */       
/*  259 */       columnHeaderTable.setAlignment(nextRow, 0, 33);
/*  260 */       columnHeaderTable.setAlignment(nextRow, 1, 33);
/*  261 */       columnHeaderTable.setAlignment(nextRow, 2, 33);
/*  262 */       columnHeaderTable.setAlignment(nextRow, 3, 33);
/*  263 */       columnHeaderTable.setAlignment(nextRow, 4, 33);
/*  264 */       columnHeaderTable.setAlignment(nextRow, 5, 33);
/*  265 */       columnHeaderTable.setAlignment(nextRow, 6, 33);
/*  266 */       columnHeaderTable.setAlignment(nextRow, 7, 34);
/*  267 */       columnHeaderTable.setAlignment(nextRow, 8, 33);
/*  268 */       columnHeaderTable.setAlignment(nextRow, 9, 34);
/*      */       
/*  270 */       columnHeaderTable.setObject(nextRow, 0, "Release\nDate");
/*  271 */       columnHeaderTable.setObject(nextRow, 1, "Artist");
/*  272 */       columnHeaderTable.setObject(nextRow, 2, "Title");
/*  273 */       columnHeaderTable.setObject(nextRow, 3, "LPN");
/*  274 */       columnHeaderTable.setObject(nextRow, 4, "UPC");
/*  275 */       columnHeaderTable.setObject(nextRow, 5, "Label");
/*  276 */       columnHeaderTable.setObject(nextRow, 6, "Retail\nCode");
/*  277 */       columnHeaderTable.setObject(nextRow, 7, "Other\nContact");
/*  278 */       columnHeaderTable.setObject(nextRow, 8, "Int'l\nDate");
/*  279 */       columnHeaderTable.setObject(nextRow, 9, "Comments");
/*      */ 
/*      */ 
/*      */       
/*  283 */       columnHeaderTable.setRowFont(nextRow, new Font("Arial", 3, 10));
/*  284 */       columnHeaderTable.setRowBackground(nextRow, Color.white);
/*  285 */       columnHeaderTable.setRowForeground(nextRow, Color.black);
/*      */ 
/*      */       
/*  288 */       hbandType.addTable(columnHeaderTable, new Rectangle(0, 10, 800, 35));
/*  289 */       hbandType.setBottomBorder(0);
/*      */ 
/*      */       
/*  292 */       if (selTable != null) {
/*      */         
/*  294 */         Enumeration months = selTable.keys();
/*      */         
/*  296 */         Vector monthVector = new Vector();
/*      */         
/*  298 */         while (months.hasMoreElements()) {
/*  299 */           monthVector.add((String)months.nextElement());
/*      */         }
/*  301 */         Object[] monthArray = (Object[])null;
/*  302 */         monthArray = monthVector.toArray();
/*      */         
/*  304 */         Arrays.sort(monthArray, new MonthYearComparator());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  310 */         int statusBarCount = 0;
/*      */         
/*  312 */         for (int n = 0; n < monthArray.length; n++) {
/*      */           
/*  314 */           String monthName = (String)monthArray[n];
/*  315 */           String monthNameString = monthName;
/*      */ 
/*      */           
/*      */           try {
/*  319 */             monthNameString = MONTHS[Integer.parseInt(monthName.substring(0, 2)) - 1];
/*      */           }
/*  321 */           catch (Exception e) {
/*      */             
/*  323 */             if (monthName.equals("13")) {
/*  324 */               monthNameString = "TBS";
/*  325 */             } else if (monthName.equals("26")) {
/*  326 */               monthNameString = "ITW";
/*      */             } else {
/*  328 */               monthNameString = "No street date";
/*      */             } 
/*      */           } 
/*  331 */           Hashtable dateTable = (Hashtable)selTable.get(monthName);
/*  332 */           if (dateTable != null) {
/*      */             
/*  334 */             Enumeration dateSort = dateTable.keys();
/*  335 */             Vector dateVector = new Vector();
/*      */             
/*  337 */             while (dateSort.hasMoreElements()) {
/*  338 */               dateVector.add((String)dateSort.nextElement());
/*      */             }
/*  340 */             Object[] dateArray = (Object[])null;
/*  341 */             dateArray = dateVector.toArray();
/*  342 */             Arrays.sort(dateArray, new StringDateComparator());
/*      */ 
/*      */             
/*  345 */             for (int dIndex = 0; dIndex < dateArray.length; dIndex++) {
/*      */               
/*  347 */               String dateName = (String)dateArray[dIndex];
/*  348 */               Hashtable divTable = (Hashtable)dateTable.get(dateName);
/*      */               
/*  350 */               if (divTable != null) {
/*      */                 
/*  352 */                 Enumeration divSort = divTable.keys();
/*  353 */                 Vector divVector = new Vector();
/*      */                 
/*  355 */                 while (divSort.hasMoreElements()) {
/*  356 */                   divVector.add((String)divSort.nextElement());
/*      */                 }
/*  358 */                 Object[] divArray = (Object[])null;
/*  359 */                 divArray = divVector.toArray();
/*  360 */                 Arrays.sort(divArray, new StringDateComparator());
/*      */ 
/*      */                 
/*  363 */                 for (int divIndex = 0; divIndex < divArray.length; divIndex++) {
/*      */                   
/*  365 */                   String divName = (String)divArray[divIndex];
/*  366 */                   selections1 = (Vector)divTable.get(divName);
/*  367 */                   statusBarCount += selections1.size();
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */         
/*  374 */         for (int x = 0; x < monthArray.length; x++) {
/*      */           
/*  376 */           String monthName = (String)monthArray[x];
/*  377 */           String monthNameString = monthName;
/*      */ 
/*      */ 
/*      */           
/*      */           try {
/*  382 */             monthNameString = MONTHS[Integer.parseInt(monthName.substring(0, 2)) - 1];
/*      */           }
/*  384 */           catch (Exception e) {
/*      */             
/*  386 */             if (monthName.equals("13")) {
/*  387 */               monthNameString = "TBS";
/*  388 */             } else if (monthName.equals("26")) {
/*  389 */               monthNameString = "ITW";
/*      */             } else {
/*  391 */               monthNameString = "No street date";
/*      */             } 
/*      */           } 
/*  394 */           monthTableLens = new DefaultTableLens(1, numColumns);
/*  395 */           hbandCategory = new SectionBand(report);
/*  396 */           hbandCategory.setHeight(0.25F);
/*  397 */           hbandCategory.setShrinkToFit(true);
/*  398 */           hbandCategory.setVisible(true);
/*  399 */           hbandCategory.setBottomBorder(0);
/*  400 */           hbandCategory.setLeftBorder(0);
/*  401 */           hbandCategory.setRightBorder(0);
/*  402 */           hbandCategory.setTopBorder(0);
/*      */           
/*  404 */           nextRow = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  409 */           monthTableLens.setSpan(nextRow, 0, new Dimension(numColumns, 1));
/*  410 */           monthTableLens.setObject(nextRow, 0, monthNameString);
/*  411 */           monthTableLens.setRowHeight(nextRow, 14);
/*  412 */           monthTableLens.setRowFont(nextRow, new Font("Arial", 1, 12));
/*  413 */           monthTableLens.setRowBackground(nextRow, Color.black);
/*  414 */           monthTableLens.setRowForeground(nextRow, Color.white);
/*  415 */           monthTableLens.setRowBorderColor(nextRow, Color.white);
/*  416 */           monthTableLens.setRowBorderColor(nextRow - 1, Color.white);
/*  417 */           monthTableLens.setColBorderColor(nextRow, -1, Color.white);
/*  418 */           monthTableLens.setColBorderColor(nextRow, 0, Color.white);
/*  419 */           monthTableLens.setColBorderColor(nextRow, 14, Color.white);
/*      */           
/*  421 */           hbandCategory.addTable(monthTableLens, new Rectangle(800, 800));
/*      */ 
/*      */           
/*  424 */           footer.setVisible(true);
/*  425 */           footer.setHeight(0.1F);
/*  426 */           footer.setShrinkToFit(false);
/*  427 */           footer.setBottomBorder(0);
/*      */ 
/*      */           
/*  430 */           group = new DefaultSectionLens(null, group, hbandCategory);
/*  431 */           group = new DefaultSectionLens(null, group, spacer);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  437 */           Hashtable dateTable = (Hashtable)selTable.get(monthName);
/*  438 */           if (dateTable != null) {
/*      */ 
/*      */             
/*  441 */             Enumeration dateSort = dateTable.keys();
/*      */             
/*  443 */             Vector dateVector = new Vector();
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  448 */             while (dateSort.hasMoreElements()) {
/*  449 */               dateVector.add((String)dateSort.nextElement());
/*      */             }
/*  451 */             Object[] dateArray = (Object[])null;
/*      */             
/*  453 */             dateArray = dateVector.toArray();
/*  454 */             Arrays.sort(dateArray, new StringDateComparator());
/*      */             
/*  456 */             for (int dIndex = 0; dIndex < dateArray.length; dIndex++) {
/*      */               
/*  458 */               String dateName = (String)dateArray[dIndex];
/*  459 */               String dateNameText = dateName;
/*      */ 
/*      */               
/*  462 */               if (monthNameString.equalsIgnoreCase("TBS")) {
/*      */                 
/*  464 */                 dateNameText = "TBS " + dateName;
/*      */               }
/*  466 */               else if (monthNameString.equalsIgnoreCase("ITW")) {
/*      */                 
/*  468 */                 dateNameText = "ITW " + dateName;
/*      */               } 
/*      */ 
/*      */               
/*  472 */               hbandDate = new SectionBand(report);
/*  473 */               hbandDate.setHeight(0.25F);
/*  474 */               hbandDate.setShrinkToFit(true);
/*  475 */               hbandDate.setVisible(true);
/*  476 */               hbandDate.setBottomBorder(0);
/*  477 */               hbandDate.setLeftBorder(0);
/*  478 */               hbandDate.setRightBorder(0);
/*  479 */               hbandDate.setTopBorder(0);
/*      */               
/*  481 */               dateTableLens = new DefaultTableLens(1, 15);
/*      */               
/*  483 */               nextRow = 0;
/*      */               
/*  485 */               dateTableLens.setSpan(nextRow, 0, new Dimension(15, 1));
/*  486 */               dateTableLens.setObject(nextRow, 0, dateNameText);
/*  487 */               dateTableLens.setRowHeight(nextRow, 14);
/*  488 */               dateTableLens.setRowFont(nextRow, new Font("Arial", 1, 12));
/*  489 */               dateTableLens.setRowForeground(nextRow, Color.black);
/*  490 */               dateTableLens.setRowBorderColor(nextRow, Color.white);
/*  491 */               dateTableLens.setRowBorderColor(nextRow - 1, Color.white);
/*  492 */               dateTableLens.setColBorderColor(nextRow, -1, Color.white);
/*  493 */               dateTableLens.setColBorderColor(nextRow, 0, Color.white);
/*  494 */               dateTableLens.setColBorderColor(nextRow, 14, Color.white);
/*  495 */               dateTableLens.setRowBackground(nextRow, Color.lightGray);
/*      */               
/*  497 */               hbandDate.addTable(dateTableLens, new Rectangle(800, 200));
/*  498 */               hbandDate.setBottomBorder(0);
/*      */               
/*  500 */               group = new DefaultSectionLens(null, group, hbandDate);
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  505 */               Hashtable divTable = (Hashtable)dateTable.get(dateName);
/*      */               
/*  507 */               if (divTable != null) {
/*      */                 
/*  509 */                 Enumeration divSort = divTable.keys();
/*      */                 
/*  511 */                 Vector divVector = new Vector();
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  516 */                 while (divSort.hasMoreElements()) {
/*  517 */                   divVector.add((String)divSort.nextElement());
/*      */                 }
/*  519 */                 Object[] divArray = (Object[])null;
/*      */                 
/*  521 */                 divArray = divVector.toArray();
/*  522 */                 Arrays.sort(divArray, new StringDateComparator());
/*      */ 
/*      */ 
/*      */                 
/*  526 */                 for (int divIndex = 0; divIndex < divArray.length; divIndex++) {
/*      */                   
/*  528 */                   String divName = (String)divArray[divIndex];
/*      */ 
/*      */                   
/*  531 */                   hbandDiv = new SectionBand(report);
/*  532 */                   hbandDiv.setHeight(0.25F);
/*  533 */                   hbandDiv.setShrinkToFit(true);
/*  534 */                   hbandDiv.setVisible(true);
/*  535 */                   hbandDiv.setBottomBorder(0);
/*  536 */                   hbandDiv.setLeftBorder(0);
/*  537 */                   hbandDiv.setRightBorder(0);
/*  538 */                   hbandDiv.setTopBorder(0);
/*      */                   
/*  540 */                   divTableLens = new DefaultTableLens(1, 15);
/*      */                   
/*  542 */                   selections1 = (Vector)divTable.get(divName);
/*      */                   
/*  544 */                   nextRow = 0;
/*      */                   
/*  546 */                   divTableLens.setSpan(nextRow, 0, new Dimension(15, 1));
/*  547 */                   if (selections1.size() > 0) {
/*      */ 
/*      */                     
/*  550 */                     divTableLens.setRowHeight(nextRow, 14);
/*  551 */                     divTableLens.setRowFont(nextRow, new Font("Arial", 1, 12));
/*  552 */                     divTableLens.setObject(nextRow, 0, divArray[divIndex]);
/*  553 */                     divTableLens.setRowForeground(nextRow, Color.black);
/*  554 */                     divTableLens.setRowBorderColor(nextRow, Color.white);
/*  555 */                     divTableLens.setRowBorderColor(nextRow - 1, Color.white);
/*  556 */                     divTableLens.setColBorderColor(nextRow, -1, Color.white);
/*  557 */                     divTableLens.setColBorderColor(nextRow, 0, Color.white);
/*  558 */                     divTableLens.setColBorderColor(nextRow, 14, Color.white);
/*  559 */                     divTableLens.setRowBackground(nextRow, Color.white);
/*      */                     
/*  561 */                     hbandDiv.addTable(divTableLens, new Rectangle(800, 200));
/*  562 */                     hbandDiv.setBottomBorder(0);
/*      */                     
/*  564 */                     group = new DefaultSectionLens(null, group, hbandDiv);
/*      */                     
/*  566 */                     group = new DefaultSectionLens(null, group, spacer);
/*      */                   
/*      */                   }
/*      */                   else {
/*      */                     
/*  571 */                     divTableLens.setObject(nextRow, 0, "");
/*      */                   } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  580 */                   if (selections1 == null) {
/*  581 */                     selections1 = new Vector();
/*      */                   }
/*      */ 
/*      */                   
/*  585 */                   MilestoneHelper.setSelectionSorting(selections1, 12);
/*  586 */                   Collections.sort(selections1);
/*      */ 
/*      */                   
/*  589 */                   MilestoneHelper.setSelectionSorting(selections1, 4);
/*  590 */                   Collections.sort(selections1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  597 */                   MilestoneHelper.setSelectionSorting(selections1, 22);
/*  598 */                   Collections.sort(selections1);
/*      */ 
/*      */                   
/*  601 */                   MilestoneHelper.setSelectionSorting(selections1, 9);
/*  602 */                   Collections.sort(selections1);
/*      */ 
/*      */                   
/*  605 */                   MilestoneHelper.setSelectionSorting(selections1, 7);
/*  606 */                   Collections.sort(selections1);
/*      */ 
/*      */                   
/*  609 */                   MilestoneHelper.setSelectionSorting(selections1, 1);
/*  610 */                   Collections.sort(selections1);
/*      */ 
/*      */                   
/*  613 */                   MilestoneHelper.setSelectionSorting(selections1, 8);
/*  614 */                   Collections.sort(selections1);
/*      */ 
/*      */                   
/*  617 */                   MilestoneHelper.setSelectionSorting(selections1, 6);
/*  618 */                   Collections.sort(selections1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  624 */                   selections1 = reorderByProductCategory(selections1);
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  629 */                   productCatagoryFlag = true;
/*      */ 
/*      */                   
/*  632 */                   for (int i = 0; i < selections1.size(); i++) {
/*      */ 
/*      */ 
/*      */                     
/*  636 */                     aStatusUpdate.updateStatus(statusBarCount, i, "start_report", 0);
/*      */ 
/*      */                     
/*  639 */                     Selection sel = (Selection)selections1.elementAt(i);
/*      */ 
/*      */ 
/*      */ 
/*      */                     
/*  644 */                     String categoryString = (sel.getProductCategory() != null) ? sel.getProductCategory().getName() : "";
/*  645 */                     if (categoryString.equals("Catalog/Reissue") && productCatagoryFlag) {
/*      */                       
/*  647 */                       productCatagoryFlag = false;
/*      */                       
/*  649 */                       hbandProd = new SectionBand(report);
/*  650 */                       hbandProd.setHeight(0.25F);
/*  651 */                       hbandProd.setShrinkToFit(true);
/*  652 */                       hbandProd.setVisible(true);
/*  653 */                       hbandProd.setBottomBorder(0);
/*  654 */                       hbandProd.setLeftBorder(0);
/*  655 */                       hbandProd.setRightBorder(0);
/*  656 */                       hbandProd.setTopBorder(0);
/*      */                       
/*  658 */                       prodTableLens = new DefaultTableLens(1, 15);
/*  659 */                       nextRow = 0;
/*  660 */                       prodTableLens.setSpan(nextRow, 0, new Dimension(15, 1));
/*      */                       
/*  662 */                       prodTableLens.setRowHeight(nextRow, 14);
/*  663 */                       prodTableLens.setRowFont(nextRow, new Font("Arial", 1, 12));
/*  664 */                       prodTableLens.setObject(nextRow, 0, sel.getProductCategory().getName());
/*  665 */                       prodTableLens.setRowForeground(nextRow, Color.black);
/*  666 */                       prodTableLens.setRowBorderColor(nextRow, Color.white);
/*  667 */                       prodTableLens.setRowBorderColor(nextRow - 1, Color.white);
/*  668 */                       prodTableLens.setColBorderColor(nextRow, -1, Color.white);
/*  669 */                       prodTableLens.setColBorderColor(nextRow, 0, Color.white);
/*  670 */                       prodTableLens.setColBorderColor(nextRow, 14, Color.white);
/*  671 */                       prodTableLens.setRowBackground(nextRow, Color.white);
/*      */                       
/*  673 */                       hbandProd.addTable(prodTableLens, new Rectangle(800, 200));
/*  674 */                       hbandProd.setBottomBorder(0);
/*      */                       
/*  676 */                       group = new DefaultSectionLens(null, group, spacer);
/*  677 */                       group = new DefaultSectionLens(null, group, hbandProd);
/*  678 */                       group = new DefaultSectionLens(null, group, spacer);
/*      */                     } 
/*      */ 
/*      */ 
/*      */                     
/*  683 */                     nextRow = 0;
/*      */                     
/*  685 */                     subTable = new DefaultTableLens(1, numColumns);
/*  686 */                     subTable.setColWidth(0, 50);
/*  687 */                     subTable.setColWidth(1, 130);
/*  688 */                     subTable.setColWidth(2, 130);
/*  689 */                     subTable.setColWidth(3, 75);
/*  690 */                     subTable.setColWidth(4, 75);
/*  691 */                     subTable.setColWidth(5, 70);
/*  692 */                     subTable.setColWidth(6, 30);
/*  693 */                     subTable.setColWidth(7, 70);
/*  694 */                     subTable.setColWidth(8, 30);
/*  695 */                     subTable.setColWidth(9, 110);
/*      */ 
/*      */                     
/*  698 */                     String labelName = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                     
/*  706 */                     if (sel.getImprint() != null)
/*      */                     {
/*  708 */                       labelName = sel.getImprint();
/*      */                     }
/*      */ 
/*      */                     
/*  712 */                     String retail = "";
/*  713 */                     if (sel.getPriceCode() != null && sel.getPriceCode().getRetailCode() != null) {
/*  714 */                       retail = sel.getPriceCode().getRetailCode();
/*      */                     }
/*      */                     
/*  717 */                     subTable.setRowInsets(nextRow, new Insets(0, 0, 5, 0));
/*      */                     
/*  719 */                     String intlDate = "  " + MilestoneHelper.getFormatedDate(sel.getInternationalDate());
/*      */ 
/*      */ 
/*      */ 
/*      */                     
/*  724 */                     if (intlDate.length() < 8) {
/*      */                       
/*  726 */                       intlDate = String.valueOf(intlDate) + " ";
/*      */                       
/*  728 */                       if (intlDate.length() < 7) {
/*  729 */                         intlDate = String.valueOf(intlDate) + " ";
/*      */                       }
/*      */                     } 
/*  732 */                     String streetDate = "  " + MilestoneHelper.getFormatedDate(sel.getStreetDate());
/*      */                     
/*  734 */                     String status = (sel.getSelectionStatus() != null && sel.getSelectionStatus().getName() != null) ? 
/*      */                       
/*  736 */                       sel.getSelectionStatus().getName() : "";
/*      */ 
/*      */                     
/*  739 */                     if (status.equalsIgnoreCase("TBS")) {
/*  740 */                       streetDate = "TBS " + streetDate;
/*      */                     }
/*  742 */                     else if (status.equalsIgnoreCase("In The Works")) {
/*  743 */                       streetDate = "ITW " + streetDate;
/*      */                     } 
/*      */ 
/*      */ 
/*      */                     
/*  748 */                     if (streetDate.length() < 8) {
/*      */                       
/*  750 */                       streetDate = String.valueOf(streetDate) + " ";
/*      */                       
/*  752 */                       if (streetDate.length() < 7)
/*  753 */                         streetDate = String.valueOf(streetDate) + " "; 
/*      */                     } 
/*  755 */                     String artist = "";
/*      */ 
/*      */ 
/*      */ 
/*      */                     
/*  760 */                     if (sel.getFlArtist() != null)
/*      */                     {
/*  762 */                       artist = sel.getFlArtist();
/*      */                     }
/*  764 */                     String title = "";
/*  765 */                     if (sel.getTitle() != null)
/*      */                     {
/*  767 */                       title = sel.getTitle();
/*      */                     }
/*  769 */                     String otherContact = "";
/*  770 */                     if (sel.getOtherContact() != null)
/*      */                     {
/*  772 */                       otherContact = sel.getOtherContact();
/*      */                     }
/*  774 */                     String[] checkStrings = { artist, title, otherContact };
/*  775 */                     int[] checkStringLengths = { 25, 25, 20 };
/*      */                     
/*  777 */                     int extraLines = MilestoneHelper.lineCount(checkStrings, checkStringLengths);
/*  778 */                     if (extraLines > 3) {
/*  779 */                       extraLines -= 4;
/*      */                     }
/*      */ 
/*      */ 
/*      */ 
/*      */                     
/*  785 */                     subTable.setObject(nextRow, 8, intlDate);
/*  786 */                     subTable.setAlignment(nextRow, 8, 9);
/*      */ 
/*      */ 
/*      */ 
/*      */                     
/*  791 */                     Calendar streetUpdate = sel.getLastStreetUpdateDate();
/*      */ 
/*      */                     
/*  794 */                     Calendar enteredOn = sel.getOriginDate();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                     
/*  802 */                     boolean asteric = false;
/*  803 */                     if (streetUpdate != null) {
/*      */                       
/*  805 */                       if (beginEffectiveDate != null) {
/*      */                         
/*  807 */                         if (streetUpdate.after(beginEffectiveDate) || streetUpdate.equals(beginEffectiveDate)) {
/*  808 */                           asteric = true;
/*      */                         }
/*  810 */                       } else if (beginStDate != null) {
/*      */                         
/*  812 */                         if (streetUpdate.after(beginStDate) || streetUpdate.equals(beginStDate)) {
/*  813 */                           asteric = true;
/*      */                         }
/*      */                       } 
/*  816 */                       if (endEffectiveDate != null) {
/*      */                         
/*  818 */                         if ((beginEffectiveDate != null || beginStDate != null) && asteric && (
/*  819 */                           streetUpdate.before(endEffectiveDate) || streetUpdate.equals(endEffectiveDate))) {
/*  820 */                           asteric = true;
/*  821 */                         } else if (beginEffectiveDate == null && beginStDate == null && (streetUpdate.before(endEffectiveDate) || streetUpdate.equals(endEffectiveDate))) {
/*  822 */                           asteric = true;
/*      */                         } else {
/*  824 */                           asteric = false;
/*      */                         } 
/*  826 */                       } else if (endStDate != null) {
/*      */                         
/*  828 */                         if ((beginEffectiveDate != null || beginStDate != null) && asteric && (
/*  829 */                           streetUpdate.before(endStDate) || streetUpdate.equals(endStDate))) {
/*  830 */                           asteric = true;
/*  831 */                         } else if (beginEffectiveDate == null && beginStDate == null && (streetUpdate.before(endStDate) || streetUpdate.equals(endStDate))) {
/*  832 */                           asteric = true;
/*      */                         } else {
/*  834 */                           asteric = false;
/*      */                         } 
/*      */                       } 
/*      */                     } 
/*  838 */                     if (enteredOn != null && streetUpdate != null && enteredOn.equals(streetUpdate)) {
/*  839 */                       asteric = false;
/*      */                     }
/*  841 */                     if (asteric) {
/*  842 */                       streetDate = String.valueOf(streetDate) + " *";
/*      */                     }
/*      */                     
/*  845 */                     boolean lightGrayBG = false;
/*  846 */                     if (enteredOn != null) {
/*      */                       
/*  848 */                       if (beginEffectiveDate != null) {
/*      */                         
/*  850 */                         if (enteredOn.after(beginEffectiveDate) || enteredOn.equals(beginEffectiveDate)) {
/*  851 */                           lightGrayBG = true;
/*      */                         }
/*  853 */                       } else if (beginStDate != null) {
/*      */                         
/*  855 */                         if (enteredOn.after(beginStDate) || enteredOn.equals(beginStDate)) {
/*  856 */                           lightGrayBG = true;
/*      */                         }
/*      */                       } 
/*  859 */                       if (endEffectiveDate != null) {
/*      */                         
/*  861 */                         if ((beginEffectiveDate != null || beginStDate != null) && lightGrayBG && (
/*  862 */                           enteredOn.before(endEffectiveDate) || enteredOn.equals(endEffectiveDate))) {
/*  863 */                           lightGrayBG = true;
/*  864 */                         } else if (beginEffectiveDate == null && beginStDate == null && (enteredOn.before(endEffectiveDate) || enteredOn.equals(endEffectiveDate))) {
/*  865 */                           lightGrayBG = true;
/*      */                         } else {
/*  867 */                           lightGrayBG = false;
/*      */                         } 
/*  869 */                       } else if (endStDate != null) {
/*      */                         
/*  871 */                         if ((beginEffectiveDate != null || beginStDate != null) && lightGrayBG && (
/*  872 */                           enteredOn.before(endStDate) || enteredOn.equals(endStDate))) {
/*  873 */                           lightGrayBG = true;
/*  874 */                         } else if (beginEffectiveDate == null && beginStDate == null && (enteredOn.before(endStDate) || enteredOn.equals(endStDate))) {
/*  875 */                           lightGrayBG = true;
/*      */                         } else {
/*  877 */                           lightGrayBG = false;
/*      */                         } 
/*      */                       } 
/*      */                     } 
/*  881 */                     if (lightGrayBG) {
/*  882 */                       subTable.setBackground(nextRow, 0, Color.lightGray);
/*      */                     }
/*      */                     
/*  885 */                     subTable.setObject(nextRow, 0, streetDate);
/*  886 */                     subTable.setAlignment(nextRow, 0, 9);
/*      */ 
/*      */                     
/*  889 */                     subTable.setObject(nextRow, 1, sel.getFlArtist().trim());
/*  890 */                     subTable.setAlignment(nextRow, 1, 9);
/*      */                     
/*  892 */                     subTable.setObject(nextRow, 2, sel.getTitle());
/*  893 */                     subTable.setAlignment(nextRow, 2, 9);
/*      */ 
/*      */ 
/*      */                     
/*  897 */                     subTable.setObject(nextRow, 5, sel.getImprint());
/*  898 */                     subTable.setAlignment(nextRow, 5, 9);
/*      */                     
/*  900 */                     subTable.setObject(nextRow, 6, retail);
/*  901 */                     subTable.setAlignment(nextRow, 6, 10);
/*      */                     
/*  903 */                     subTable.setObject(nextRow, 7, sel.getOtherContact());
/*  904 */                     subTable.setAlignment(nextRow, 7, 9);
/*      */ 
/*      */ 
/*      */                     
/*  908 */                     String selectionNumber = "";
/*  909 */                     if (SelectionManager.getLookupObjectValue(sel.getPrefixID()).equals("")) {
/*  910 */                       selectionNumber = sel.getSelectionNo();
/*      */                     } else {
/*  912 */                       selectionNumber = String.valueOf(SelectionManager.getLookupObjectValue(sel.getPrefixID())) + sel.getSelectionNo();
/*      */                     } 
/*      */                     
/*  915 */                     subTable.setObject(nextRow, 3, selectionNumber);
/*  916 */                     subTable.setAlignment(nextRow, 3, 9);
/*      */ 
/*      */ 
/*      */ 
/*      */                     
/*  921 */                     String upc = sel.getUpc();
/*  922 */                     upc = MilestoneHelper_2.getRMSReportFormat(upc, "UPC", sel.getIsDigital());
/*      */                     
/*  924 */                     subTable.setObject(nextRow, 4, !upc.equals("") ? upc : "");
/*  925 */                     subTable.setAlignment(nextRow, 4, 9);
/*      */                     
/*  927 */                     subTable.setObject(nextRow, 9, sel.getSelectionComments());
/*  928 */                     subTable.setAlignment(nextRow, 9, 9);
/*      */ 
/*      */                     
/*  931 */                     subTable.setColBorder(0);
/*  932 */                     subTable.setRowBorderColor(nextRow, Color.white);
/*  933 */                     subTable.setRowBorder(nextRow - 1, 4097);
/*  934 */                     subTable.setRowBorder(nextRow, 4097);
/*  935 */                     subTable.setRowBorderColor(nextRow, Color.lightGray);
/*  936 */                     subTable.setRowBorderColor(nextRow - 1, Color.lightGray);
/*      */                     
/*  938 */                     subTable.setColBorder(4097);
/*  939 */                     subTable.setColBorder(nextRow - 1, 0);
/*  940 */                     subTable.setColBorder(nextRow, -1, 4097);
/*  941 */                     subTable.setColBorder(nextRow, 4097);
/*  942 */                     subTable.setColBorderColor(nextRow, Color.lightGray);
/*  943 */                     subTable.setColBorderColor(Color.lightGray);
/*      */                     
/*  945 */                     subTable.setRowFont(nextRow, new Font("Arial", 0, 8));
/*      */ 
/*      */                     
/*  948 */                     body = new SectionBand(report);
/*      */ 
/*      */                     
/*  951 */                     double lfLineCount = 1.5D;
/*      */                     
/*  953 */                     if (extraLines > 0 || sel.getTitle().length() > 20) {
/*      */                       
/*  955 */                       body.setHeight(1.5F);
/*      */                     }
/*      */                     else {
/*      */                       
/*  959 */                       body.setHeight(1.0F);
/*      */                     } 
/*  961 */                     body.addTable(subTable, new Rectangle(800, 800));
/*  962 */                     body.setBottomBorder(0);
/*  963 */                     body.setTopBorder(0);
/*  964 */                     body.setShrinkToFit(true);
/*  965 */                     body.setVisible(true);
/*  966 */                     group = new DefaultSectionLens(null, group, body);
/*      */                   } 
/*      */                   
/*  969 */                   group = new DefaultSectionLens(null, group, spacer);
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  979 */       group = new DefaultSectionLens(hbandType, group, null);
/*  980 */       report.addSection(group, rowCountTable);
/*      */       
/*  982 */       group = null;
/*      */     
/*      */     }
/*  985 */     catch (Exception e) {
/*      */       
/*  987 */       System.out.println(">>>>>>>>ClassicsRelSchdForPrintSubHandler(): exception: " + e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Vector reorderByProductCategory(Vector initialSelections) {
/*  998 */     Vector selectionsThatAreNotCatalogReissue = new Vector();
/*  999 */     Vector selectionsThatAreCatalogReissue = new Vector();
/*      */     
/* 1001 */     for (int counter = 0; counter < initialSelections.size(); counter++) {
/* 1002 */       Selection sel = (Selection)initialSelections.elementAt(counter);
/* 1003 */       String categoryString = (sel.getProductCategory() != null) ? sel.getProductCategory().getName() : "";
/* 1004 */       if (categoryString.equals("Catalog/Reissue")) {
/* 1005 */         selectionsThatAreCatalogReissue.add(sel);
/*      */       } else {
/* 1007 */         selectionsThatAreNotCatalogReissue.add(sel);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1012 */     Vector finalSelections = new Vector();
/* 1013 */     for (int count = 0; count < selectionsThatAreNotCatalogReissue.size(); count++) {
/* 1014 */       finalSelections.addElement(selectionsThatAreNotCatalogReissue.elementAt(count));
/*      */     }
/* 1016 */     for (int count2 = 0; count2 < selectionsThatAreCatalogReissue.size(); count2++) {
/* 1017 */       finalSelections.addElement(selectionsThatAreCatalogReissue.elementAt(count2));
/*      */     }
/*      */     
/* 1020 */     return finalSelections;
/*      */   }
/*      */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\ClassicsRelSchdForPrintSubHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */