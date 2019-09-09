/*      */ package WEB-INF.classes.com.universal.milestone;
/*      */ 
/*      */ import com.techempower.ComponentLog;
/*      */ import com.techempower.gemini.Context;
/*      */ import com.techempower.gemini.GeminiApplication;
/*      */ import com.universal.milestone.Form;
/*      */ import com.universal.milestone.IgaProductionScheduleUpdateForPrintSubHandler;
/*      */ import com.universal.milestone.MilestoneHelper;
/*      */ import com.universal.milestone.MilestoneHelper_2;
/*      */ import com.universal.milestone.MonthYearComparator;
/*      */ import com.universal.milestone.Schedule;
/*      */ import com.universal.milestone.ScheduledTask;
/*      */ import com.universal.milestone.SecureHandler;
/*      */ import com.universal.milestone.Selection;
/*      */ import com.universal.milestone.SelectionFlArtistComparator;
/*      */ import com.universal.milestone.SelectionManager;
/*      */ import com.universal.milestone.SelectionSelectionNumberComparator;
/*      */ import com.universal.milestone.SelectionStatus;
/*      */ import com.universal.milestone.SelectionTitleComparator;
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
/*      */ public class IgaProductionScheduleUpdateForPrintSubHandler
/*      */   extends SecureHandler
/*      */ {
/*      */   public static final String COMPONENT_CODE = "hIga";
/*      */   public GeminiApplication application;
/*      */   public ComponentLog log;
/*      */   
/*      */   public IgaProductionScheduleUpdateForPrintSubHandler(GeminiApplication application) {
/*   77 */     this.application = application;
/*   78 */     this.log = application.getLog("hIga");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   86 */   public String getDescription() { return "Sub Report"; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static void fillIgaProductionScheduleForPrint(XStyleSheet report, Context context) {
/*      */     try {
/*  101 */       HttpServletResponse sresponse = context.getResponse();
/*  102 */       context.putDelivery("status", new String("start_gathering"));
/*  103 */       context.putDelivery("percent", new String("10"));
/*  104 */       context.includeJSP("status.jsp", "hiddenFrame");
/*  105 */       sresponse.setContentType("text/plain");
/*  106 */       sresponse.flushBuffer();
/*      */     }
/*  108 */     catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  113 */     Vector selections = MilestoneHelper.getSelectionsForReport(context);
/*      */ 
/*      */     
/*      */     try {
/*  117 */       HttpServletResponse sresponse = context.getResponse();
/*  118 */       context.putDelivery("status", new String("start_report"));
/*  119 */       context.putDelivery("percent", new String("10"));
/*  120 */       context.includeJSP("status.jsp", "hiddenFrame");
/*  121 */       sresponse.setContentType("text/plain");
/*  122 */       sresponse.flushBuffer();
/*      */     }
/*  124 */     catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  130 */     Color SHADED_AREA_COLOR = Color.lightGray;
/*  131 */     int COL_LINE_STYLE = 4097;
/*  132 */     int HEADER_FONT_SIZE = 12;
/*  133 */     int NUM_COLUMNS = 12;
/*      */     
/*  135 */     double ldLineVal = 0.3D;
/*      */     
/*  137 */     SectionBand hbandType = new SectionBand(report);
/*  138 */     SectionBand hbandCategory = new SectionBand(report);
/*  139 */     SectionBand hbandDate = new SectionBand(report);
/*  140 */     SectionBand body = new SectionBand(report);
/*  141 */     SectionBand footer = new SectionBand(report);
/*  142 */     SectionBand spacer = new SectionBand(report);
/*  143 */     DefaultSectionLens group = null;
/*      */ 
/*      */ 
/*      */     
/*  147 */     footer.setVisible(true);
/*  148 */     footer.setHeight(0.1F);
/*  149 */     footer.setShrinkToFit(false);
/*  150 */     footer.setBottomBorder(0);
/*      */     
/*  152 */     spacer.setVisible(true);
/*  153 */     spacer.setHeight(0.05F);
/*  154 */     spacer.setShrinkToFit(false);
/*  155 */     spacer.setBottomBorder(0);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  161 */       DefaultTableLens table_contents = null;
/*  162 */       DefaultTableLens rowCountTable = null;
/*  163 */       DefaultTableLens columnHeaderTable = null;
/*  164 */       DefaultTableLens subTable = null;
/*  165 */       DefaultTableLens monthTableLens = null;
/*  166 */       DefaultTableLens dateTableLens = null;
/*      */       
/*  168 */       rowCountTable = new DefaultTableLens(2, 10000);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  175 */       Form reportForm = (Form)context.getSessionValue("reportForm");
/*      */       
/*  177 */       Calendar beginStDate = (reportForm.getStringValue("beginDate") != null && 
/*  178 */         reportForm.getStringValue("beginDate").length() > 0) ? 
/*  179 */         MilestoneHelper.getDate(reportForm.getStringValue("beginDate")) : null;
/*      */       
/*  181 */       Calendar endStDate = (reportForm.getStringValue("endDate") != null && 
/*  182 */         reportForm.getStringValue("endDate").length() > 0) ? 
/*  183 */         MilestoneHelper.getDate(reportForm.getStringValue("endDate")) : null;
/*      */       
/*  185 */       report.setElement("crs_startdate", MilestoneHelper.getFormatedDate(beginStDate));
/*  186 */       report.setElement("crs_enddate", MilestoneHelper.getFormatedDate(endStDate));
/*      */       
/*  188 */       SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
/*  189 */       String todayLong = formatter.format(new Date());
/*  190 */       report.setElement("crs_bottomdate", todayLong);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  196 */       Hashtable selTable = groupSelections(selections);
/*  197 */       Enumeration configs = selTable.keys();
/*  198 */       Vector configVector = new Vector();
/*      */       
/*  200 */       while (configs.hasMoreElements()) {
/*  201 */         configVector.addElement(configs.nextElement());
/*      */       }
/*      */       
/*  204 */       Collections.sort(configVector);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  209 */       if (configVector.size() > 1) {
/*      */         
/*  211 */         String subConfig1 = (String)configVector.elementAt(0);
/*  212 */         String subConfig2 = (String)configVector.elementAt(1);
/*      */         
/*  214 */         if (subConfig1 != null && subConfig2 != null)
/*      */         {
/*  216 */           if (subConfig1.startsWith("1") && subConfig2.startsWith("7")) {
/*      */             
/*  218 */             configVector.set(0, subConfig2);
/*  219 */             configVector.set(1, subConfig1);
/*      */           } 
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/*  225 */       int numSubconfigs = 0;
/*  226 */       int numMonths = 0;
/*  227 */       int numDates = 0;
/*  228 */       int numSelections = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  234 */       if (selTable != null) {
/*      */         
/*  236 */         numSubconfigs = selTable.size();
/*      */         
/*  238 */         Enumeration subconfigs = selTable.keys();
/*      */         
/*  240 */         while (subconfigs.hasMoreElements()) {
/*      */           
/*  242 */           String subconfig = (String)subconfigs.nextElement();
/*  243 */           Hashtable monthTable = (Hashtable)selTable.get(subconfig);
/*  244 */           if (monthTable != null) {
/*      */             
/*  246 */             Enumeration months = monthTable.keys();
/*  247 */             while (months.hasMoreElements()) {
/*      */               
/*  249 */               String monthName = (String)months.nextElement();
/*      */               
/*  251 */               numMonths++;
/*      */               
/*  253 */               Hashtable dateTable = (Hashtable)monthTable.get(monthName);
/*  254 */               if (dateTable != null) {
/*      */                 
/*  256 */                 Enumeration dates = dateTable.keys();
/*  257 */                 while (dates.hasMoreElements()) {
/*      */                   
/*  259 */                   String dateName = (String)dates.nextElement();
/*      */                   
/*  261 */                   numDates++;
/*      */                   
/*  263 */                   selections = (Vector)dateTable.get(dateName);
/*  264 */                   if (selections != null) {
/*  265 */                     numSelections += selections.size();
/*      */                   }
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  278 */       int numRows = 0;
/*  279 */       numRows += numSubconfigs * 2;
/*  280 */       numRows += numMonths * 2;
/*  281 */       numRows += numDates * 4;
/*  282 */       numRows += numSelections * 2;
/*      */ 
/*      */       
/*  285 */       int nextRow = 0;
/*      */       
/*  287 */       for (int n = 0; n < configVector.size(); n++)
/*      */       {
/*  289 */         String config = (String)configVector.elementAt(n);
/*  290 */         String configHeaderText = !config.trim().equals("") ? config : "Other";
/*      */         
/*  292 */         hbandType = new SectionBand(report);
/*  293 */         hbandType.setHeight(0.95F);
/*  294 */         hbandType.setShrinkToFit(true);
/*  295 */         hbandType.setVisible(true);
/*      */ 
/*      */         
/*  298 */         table_contents = new DefaultTableLens(1, 12);
/*      */ 
/*      */         
/*  301 */         table_contents.setHeaderRowCount(0);
/*  302 */         table_contents.setColWidth(0, 270);
/*  303 */         table_contents.setColWidth(1, 135);
/*  304 */         table_contents.setColWidth(2, 127);
/*  305 */         table_contents.setColWidth(3, 65);
/*  306 */         table_contents.setColWidth(4, 118);
/*  307 */         table_contents.setColWidth(5, 90);
/*  308 */         table_contents.setColWidth(6, 85);
/*  309 */         table_contents.setColWidth(7, 85);
/*  310 */         table_contents.setColWidth(8, 85);
/*  311 */         table_contents.setColWidth(9, 85);
/*  312 */         table_contents.setColWidth(10, 85);
/*  313 */         table_contents.setColWidth(11, 220);
/*      */         
/*  315 */         table_contents.setRowFont(0, new Font("Arial", 1, 8));
/*      */ 
/*      */         
/*  318 */         table_contents.setSpan(nextRow, 0, new Dimension(12, 1));
/*  319 */         table_contents.setAlignment(nextRow, 0, 2);
/*  320 */         table_contents.setObject(nextRow, 0, configHeaderText);
/*  321 */         table_contents.setRowHeight(nextRow, 16);
/*      */         
/*  323 */         table_contents.setRowBorderColor(nextRow, Color.black);
/*  324 */         table_contents.setRowBorder(nextRow, 0, 266240);
/*      */         
/*  326 */         table_contents.setRowFont(nextRow, new Font("Arial", 3, 12));
/*  327 */         table_contents.setRowBackground(nextRow, Color.white);
/*  328 */         table_contents.setRowForeground(nextRow, Color.black);
/*      */         
/*  330 */         table_contents.setRowBorder(nextRow - 1, 266240);
/*  331 */         table_contents.setColBorder(nextRow, -1, 266240);
/*  332 */         table_contents.setColBorder(nextRow, 0, 266240);
/*  333 */         table_contents.setColBorder(nextRow, 12, 266240);
/*  334 */         table_contents.setColBorder(nextRow, 11, 266240);
/*  335 */         table_contents.setRowBorder(nextRow, 266240);
/*      */         
/*  337 */         table_contents.setRowBorderColor(nextRow - 1, Color.black);
/*  338 */         table_contents.setColBorderColor(nextRow, -1, Color.black);
/*  339 */         table_contents.setColBorderColor(nextRow, 0, Color.black);
/*  340 */         table_contents.setColBorderColor(nextRow, 12, Color.black);
/*  341 */         table_contents.setColBorderColor(nextRow, 11, Color.black);
/*  342 */         table_contents.setRowBorderColor(nextRow, Color.black);
/*      */ 
/*      */         
/*  345 */         hbandType.addTable(table_contents, new Rectangle(0, 0, 800, 25));
/*      */         
/*  347 */         nextRow = 0;
/*      */         
/*  349 */         columnHeaderTable = new DefaultTableLens(1, 12);
/*      */         
/*  351 */         columnHeaderTable.setHeaderRowCount(0);
/*  352 */         columnHeaderTable.setColWidth(0, 270);
/*  353 */         columnHeaderTable.setColWidth(1, 135);
/*  354 */         columnHeaderTable.setColWidth(2, 127);
/*  355 */         columnHeaderTable.setColWidth(3, 65);
/*  356 */         columnHeaderTable.setColWidth(4, 118);
/*  357 */         columnHeaderTable.setColWidth(5, 90);
/*  358 */         columnHeaderTable.setColWidth(6, 85);
/*  359 */         columnHeaderTable.setColWidth(7, 85);
/*  360 */         columnHeaderTable.setColWidth(8, 85);
/*  361 */         columnHeaderTable.setColWidth(9, 85);
/*  362 */         columnHeaderTable.setColWidth(10, 85);
/*  363 */         columnHeaderTable.setColWidth(11, 220);
/*      */         
/*  365 */         columnHeaderTable.setRowBorderColor(nextRow - 1, SHADED_AREA_COLOR);
/*  366 */         columnHeaderTable.setRowBorderColor(nextRow, SHADED_AREA_COLOR);
/*  367 */         columnHeaderTable.setRowBorderColor(nextRow + 1, SHADED_AREA_COLOR);
/*      */         
/*  369 */         columnHeaderTable.setRowHeight(nextRow, 100);
/*      */         
/*  371 */         columnHeaderTable.setColBorderColor(-1, Color.lightGray);
/*  372 */         columnHeaderTable.setColBorderColor(0, Color.lightGray);
/*  373 */         columnHeaderTable.setColBorderColor(1, Color.lightGray);
/*  374 */         columnHeaderTable.setColBorderColor(2, Color.lightGray);
/*  375 */         columnHeaderTable.setColBorderColor(3, Color.lightGray);
/*  376 */         columnHeaderTable.setColBorderColor(4, Color.lightGray);
/*  377 */         columnHeaderTable.setColBorderColor(5, Color.lightGray);
/*  378 */         columnHeaderTable.setColBorderColor(6, Color.lightGray);
/*  379 */         columnHeaderTable.setColBorderColor(7, Color.lightGray);
/*  380 */         columnHeaderTable.setColBorderColor(8, Color.lightGray);
/*  381 */         columnHeaderTable.setColBorderColor(9, Color.lightGray);
/*  382 */         columnHeaderTable.setColBorderColor(10, Color.lightGray);
/*  383 */         columnHeaderTable.setColBorderColor(11, Color.lightGray);
/*  384 */         columnHeaderTable.setColBorderColor(12, Color.lightGray);
/*      */ 
/*      */         
/*  387 */         columnHeaderTable.setAlignment(nextRow, 0, 33);
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
/*  398 */         columnHeaderTable.setAlignment(nextRow, 11, 33);
/*  399 */         columnHeaderTable.setRowFont(nextRow, new Font("Arial", 1, 9));
/*      */         
/*  401 */         columnHeaderTable.setObject(nextRow, 0, "Artist/Title/UPC #");
/*  402 */         columnHeaderTable.setObject(nextRow, 1, "MM/Prod.");
/*  403 */         columnHeaderTable.setObject(nextRow, 2, "Catalog #/\nConfig/Label/\nImprint");
/*  404 */         columnHeaderTable.setObject(nextRow, 3, "List\nPrice");
/*  405 */         columnHeaderTable.setObject(nextRow, 4, "Packaging\nSpecs");
/*  406 */         columnHeaderTable.setObject(nextRow, 5, "Credits\nDue");
/*  407 */         columnHeaderTable.setObject(nextRow, 6, "Art\nDue");
/*  408 */         columnHeaderTable.setObject(nextRow, 7, "Pkging\nDue");
/*  409 */         columnHeaderTable.setObject(nextRow, 8, "Master\nShips");
/*  410 */         columnHeaderTable.setObject(nextRow, 9, "Film\nShips");
/*  411 */         columnHeaderTable.setObject(nextRow, 10, "Plant\nShip\nDate");
/*  412 */         columnHeaderTable.setObject(nextRow, 11, "Comments");
/*      */ 
/*      */         
/*  415 */         hbandType.addTable(columnHeaderTable, new Rectangle(0, 24, 800, 37));
/*  416 */         hbandType.setBottomBorder(0);
/*      */ 
/*      */         
/*  419 */         Hashtable monthTable = (Hashtable)selTable.get(config);
/*      */         
/*  421 */         if (monthTable != null) {
/*      */           
/*  423 */           Enumeration monthSort = monthTable.keys();
/*      */           
/*  425 */           Vector monthVector = new Vector();
/*      */ 
/*      */           
/*  428 */           while (monthSort.hasMoreElements()) {
/*      */             
/*  430 */             String elem = (String)monthSort.nextElement();
/*      */             
/*  432 */             monthVector.add(elem);
/*      */           } 
/*      */           
/*  435 */           Object[] monthArray = null;
/*      */           
/*  437 */           monthArray = monthVector.toArray();
/*      */ 
/*      */ 
/*      */           
/*  441 */           Arrays.sort(monthArray, new MonthYearComparator());
/*      */           
/*  443 */           for (int mIndex = 0; mIndex < monthArray.length; mIndex++) {
/*      */             
/*  445 */             String monthName = (String)monthArray[mIndex];
/*  446 */             String monthNameString = monthName;
/*      */ 
/*      */ 
/*      */             
/*      */             try {
/*  451 */               monthNameString = MONTHS[Integer.parseInt(monthName.substring(0, 2)) - 1];
/*      */             }
/*  453 */             catch (Exception e) {
/*      */               
/*  455 */               if (monthName.equals("13")) {
/*  456 */                 monthNameString = "TBS";
/*  457 */               } else if (monthName.equals("26")) {
/*  458 */                 monthNameString = "ITW";
/*      */               } else {
/*  460 */                 monthNameString = "No street date";
/*      */               } 
/*      */             } 
/*  463 */             monthTableLens = new DefaultTableLens(1, 12);
/*  464 */             hbandCategory = new SectionBand(report);
/*  465 */             hbandCategory.setHeight(0.25F);
/*  466 */             hbandCategory.setShrinkToFit(true);
/*  467 */             hbandCategory.setVisible(true);
/*  468 */             hbandCategory.setBottomBorder(0);
/*  469 */             hbandCategory.setLeftBorder(0);
/*  470 */             hbandCategory.setRightBorder(0);
/*  471 */             hbandCategory.setTopBorder(0);
/*      */             
/*  473 */             nextRow = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  478 */             monthTableLens.setSpan(nextRow, 0, new Dimension(12, 1));
/*  479 */             monthTableLens.setObject(nextRow, 0, monthNameString);
/*  480 */             monthTableLens.setRowHeight(nextRow, 14);
/*  481 */             monthTableLens.setRowFont(nextRow, new Font("Arial", 1, 12));
/*  482 */             monthTableLens.setRowBackground(nextRow, SHADED_AREA_COLOR);
/*  483 */             monthTableLens.setRowForeground(nextRow, Color.black);
/*  484 */             monthTableLens.setRowBorderColor(nextRow, Color.white);
/*  485 */             monthTableLens.setRowBorderColor(nextRow - 1, Color.white);
/*  486 */             monthTableLens.setColBorderColor(nextRow, -1, Color.white);
/*  487 */             monthTableLens.setColBorderColor(nextRow, 0, Color.white);
/*  488 */             monthTableLens.setColBorderColor(nextRow, 11, Color.white);
/*      */             
/*  490 */             hbandCategory.addTable(monthTableLens, new Rectangle(800, 800));
/*      */             
/*  492 */             footer.setVisible(true);
/*  493 */             footer.setHeight(0.1F);
/*  494 */             footer.setShrinkToFit(false);
/*  495 */             footer.setBottomBorder(0);
/*      */             
/*  497 */             group = new DefaultSectionLens(null, group, spacer);
/*  498 */             group = new DefaultSectionLens(null, group, hbandCategory);
/*  499 */             group = new DefaultSectionLens(null, group, spacer);
/*      */ 
/*      */             
/*  502 */             Hashtable dateTable = (Hashtable)monthTable.get(monthName);
/*  503 */             if (dateTable != null) {
/*      */               
/*  505 */               Enumeration dates = dateTable.keys();
/*      */               
/*  507 */               Vector streetVector = new Vector();
/*      */               
/*  509 */               while (dates.hasMoreElements()) {
/*  510 */                 streetVector.addElement(dates.nextElement());
/*      */               }
/*      */               
/*  513 */               Collections.sort(streetVector, new StringDateComparator());
/*      */               
/*  515 */               for (int z = 0; z < streetVector.size(); z++) {
/*      */                 
/*  517 */                 String date = (String)streetVector.get(z);
/*      */                 
/*  519 */                 hbandDate = new SectionBand(report);
/*  520 */                 hbandDate.setHeight(0.25F);
/*  521 */                 hbandDate.setShrinkToFit(true);
/*  522 */                 hbandDate.setVisible(true);
/*  523 */                 hbandDate.setBottomBorder(0);
/*  524 */                 hbandDate.setLeftBorder(0);
/*  525 */                 hbandDate.setRightBorder(0);
/*  526 */                 hbandDate.setTopBorder(0);
/*      */                 
/*  528 */                 dateTableLens = new DefaultTableLens(1, 12);
/*      */                 
/*  530 */                 nextRow = 0;
/*      */                 
/*  532 */                 dateTableLens.setSpan(nextRow, 0, new Dimension(12, 1));
/*  533 */                 dateTableLens.setObject(nextRow, 0, date);
/*  534 */                 dateTableLens.setRowHeight(nextRow, 14);
/*  535 */                 dateTableLens.setRowFont(nextRow, new Font("Arial", 1, 12));
/*  536 */                 dateTableLens.setRowForeground(nextRow, Color.black);
/*  537 */                 dateTableLens.setRowBorderColor(nextRow, Color.white);
/*  538 */                 dateTableLens.setRowBorderColor(nextRow - 1, Color.white);
/*  539 */                 dateTableLens.setColBorderColor(nextRow, -1, Color.white);
/*  540 */                 dateTableLens.setColBorderColor(nextRow, 0, Color.white);
/*  541 */                 dateTableLens.setColBorderColor(nextRow, 11, Color.white);
/*  542 */                 dateTableLens.setRowBackground(nextRow, Color.white);
/*      */                 
/*  544 */                 hbandDate.addTable(dateTableLens, new Rectangle(800, 200));
/*  545 */                 hbandDate.setBottomBorder(0);
/*      */                 
/*  547 */                 group = new DefaultSectionLens(null, group, hbandDate);
/*      */ 
/*      */                 
/*  550 */                 selections = (Vector)dateTable.get(date);
/*  551 */                 if (selections == null) {
/*  552 */                   selections = new Vector();
/*      */                 }
/*  554 */                 Object[] selectionsArray = selections.toArray();
/*      */                 
/*  556 */                 Arrays.sort(selectionsArray, new SelectionSelectionNumberComparator());
/*      */                 
/*  558 */                 Arrays.sort(selectionsArray, new SelectionTitleComparator());
/*      */                 
/*  560 */                 Arrays.sort(selectionsArray, new SelectionFlArtistComparator());
/*      */ 
/*      */                 
/*  563 */                 int totalCount = selectionsArray.length;
/*  564 */                 int tenth = 0;
/*  565 */                 int recordCount = 0;
/*  566 */                 int count = 0;
/*      */                 
/*  568 */                 tenth = totalCount / 10;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*      */                 try {
/*  575 */                   HttpServletResponse sresponse = context.getResponse();
/*  576 */                   context.putDelivery("status", new String("start_report"));
/*  577 */                   context.putDelivery("percent", new String("10"));
/*  578 */                   context.includeJSP("status.jsp", "hiddenFrame");
/*  579 */                   sresponse.setContentType("text/plain");
/*  580 */                   sresponse.flushBuffer();
/*      */                 }
/*  582 */                 catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  587 */                 for (int i = 0; i < selectionsArray.length; i++) {
/*      */ 
/*      */                   
/*      */                   try {
/*      */ 
/*      */                     
/*  593 */                     if (count < recordCount / tenth)
/*      */                     {
/*  595 */                       count = recordCount / tenth;
/*      */                       
/*  597 */                       HttpServletResponse sresponse = context.getResponse();
/*  598 */                       context.putDelivery("status", new String("start_report"));
/*      */                       
/*  600 */                       int myPercent = count * 10;
/*  601 */                       if (myPercent > 90)
/*  602 */                         myPercent = 90; 
/*  603 */                       context.putDelivery("percent", new String(String.valueOf(myPercent)));
/*  604 */                       context.includeJSP("status.jsp", "hiddenFrame");
/*  605 */                       sresponse.setContentType("text/plain");
/*  606 */                       sresponse.flushBuffer();
/*      */                     }
/*      */                   
/*  609 */                   } catch (Exception exception) {}
/*      */ 
/*      */                   
/*  612 */                   recordCount++;
/*      */ 
/*      */                   
/*  615 */                   Selection sel = (Selection)selectionsArray[i];
/*  616 */                   sel = SelectionManager.getInstance().getSelectionAndSchedule(sel.getSelectionID());
/*      */                   
/*  618 */                   nextRow = 0;
/*  619 */                   subTable = new DefaultTableLens(2, 12);
/*      */                   
/*  621 */                   subTable.setColWidth(0, 270);
/*  622 */                   subTable.setColWidth(1, 135);
/*  623 */                   subTable.setColWidth(2, 127);
/*  624 */                   subTable.setColWidth(3, 65);
/*  625 */                   subTable.setColWidth(4, 118);
/*  626 */                   subTable.setColWidth(5, 90);
/*  627 */                   subTable.setColWidth(6, 85);
/*  628 */                   subTable.setColWidth(7, 85);
/*  629 */                   subTable.setColWidth(8, 85);
/*  630 */                   subTable.setColWidth(9, 85);
/*  631 */                   subTable.setColWidth(10, 85);
/*  632 */                   subTable.setColWidth(11, 220);
/*      */                   
/*  634 */                   subTable.setColBorderColor(-1, Color.lightGray);
/*  635 */                   subTable.setColBorderColor(0, Color.lightGray);
/*  636 */                   subTable.setColBorderColor(1, Color.lightGray);
/*  637 */                   subTable.setColBorderColor(2, Color.lightGray);
/*  638 */                   subTable.setColBorderColor(3, Color.lightGray);
/*  639 */                   subTable.setColBorderColor(4, Color.lightGray);
/*  640 */                   subTable.setColBorderColor(5, Color.lightGray);
/*  641 */                   subTable.setColBorderColor(6, Color.lightGray);
/*  642 */                   subTable.setColBorderColor(7, Color.lightGray);
/*  643 */                   subTable.setColBorderColor(8, Color.lightGray);
/*  644 */                   subTable.setColBorderColor(9, Color.lightGray);
/*  645 */                   subTable.setColBorderColor(10, Color.lightGray);
/*  646 */                   subTable.setColBorderColor(11, Color.lightGray);
/*  647 */                   subTable.setColBorderColor(12, Color.lightGray);
/*      */ 
/*      */                   
/*  650 */                   String titleId = "";
/*  651 */                   titleId = String.valueOf(sel.getSelectionNo());
/*      */                   
/*  653 */                   if (sel.getPrefixID() != null) {
/*  654 */                     titleId = String.valueOf(SelectionManager.getLookupObjectValue(sel.getPrefixID())) + " " + titleId;
/*      */                   }
/*      */                   
/*  657 */                   String artist = "";
/*      */                   
/*  659 */                   artist = sel.getFlArtist();
/*      */ 
/*      */                   
/*  662 */                   String comment = (sel.getSelectionComments() != null) ? sel.getSelectionComments() : "";
/*      */ 
/*      */                   
/*  665 */                   String pack = "";
/*  666 */                   pack = sel.getSelectionPackaging();
/*      */ 
/*      */                   
/*  669 */                   String title = sel.getTitle();
/*      */ 
/*      */                   
/*  672 */                   String upc = "";
/*  673 */                   if (sel.getUpc() != null) {
/*  674 */                     upc = sel.getUpc();
/*      */                   }
/*      */ 
/*      */ 
/*      */                   
/*  679 */                   upc = MilestoneHelper_2.getRMSReportFormat(upc, "UPC", sel.getIsDigital());
/*      */ 
/*      */                   
/*  682 */                   String price = "";
/*  683 */                   if (sel.getPriceCode() != null && sel.getPriceCode().getTotalCost() > 0.0F) {
/*  684 */                     price = "$" + MilestoneHelper.formatDollarPrice(sel.getPriceCode().getTotalCost());
/*      */                   }
/*  686 */                   String status = (sel.getSelectionStatus() != null && sel.getSelectionStatus().getName() != null) ? 
/*  687 */                     sel.getSelectionStatus().getName() : "";
/*      */                   
/*  689 */                   String mm = "";
/*  690 */                   if (sel.getOtherContact() != null) {
/*  691 */                     mm = sel.getOtherContact();
/*      */                   }
/*  693 */                   SelectionManager.getInstance().getSelectionManufacturingSubDetail(sel);
/*  694 */                   String prod = "";
/*  695 */                   if (sel.getLabelContact() != null) {
/*  696 */                     prod = sel.getLabelContact().getName();
/*      */                   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  703 */                   String format = "";
/*  704 */                   if (sel.getSelectionConfig() != null) {
/*  705 */                     format = sel.getSelectionConfig().getSelectionConfigurationName();
/*      */                   }
/*  707 */                   String label = "";
/*  708 */                   if (sel.getLabel() != null) {
/*  709 */                     label = sel.getLabel().getName();
/*      */                   }
/*      */                   
/*  712 */                   String imprint = sel.getImprint();
/*      */ 
/*      */                   
/*  715 */                   Schedule schedule = sel.getSchedule();
/*      */                   
/*  717 */                   Vector tasks = (schedule != null) ? schedule.getTasks() : null;
/*  718 */                   ScheduledTask task = null;
/*      */                   
/*  720 */                   String credits = "";
/*  721 */                   String art = "";
/*  722 */                   String packing = "";
/*  723 */                   String master = "";
/*  724 */                   String film = "";
/*  725 */                   String plant = "";
/*      */                   
/*  727 */                   String creditsCom = "";
/*  728 */                   String artCom = "";
/*  729 */                   String packingCom = "";
/*  730 */                   String masterCom = "";
/*  731 */                   String filmCom = "";
/*  732 */                   String plantCom = "";
/*      */                   
/*  734 */                   if (tasks != null)
/*      */                   {
/*  736 */                     for (int j = 0; j < tasks.size(); j++) {
/*      */                       
/*  738 */                       task = (ScheduledTask)tasks.get(j);
/*      */                       
/*  740 */                       if (task != null && task.getDueDate() != null) {
/*      */                         
/*  742 */                         String taskStatus = task.getScheduledTaskStatus();
/*  743 */                         SimpleDateFormat dueDateFormatter = new SimpleDateFormat("M/dd");
/*      */ 
/*      */                         
/*  746 */                         String dueDate = String.valueOf(dueDateFormatter.format(task.getDueDate().getTime())) + 
/*  747 */                           " " + MilestoneHelper.getDayType(sel.getCalendarGroup(), task.getDueDate());
/*  748 */                         String completionDate = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*      */                         
/*  750 */                         if (!completionDate.equals("")) {
/*  751 */                           completionDate = String.valueOf(completionDate) + "\n";
/*      */                         }
/*  753 */                         if (taskStatus.equalsIgnoreCase("N/A")) {
/*  754 */                           completionDate = "N/A\n";
/*      */                         }
/*  756 */                         String taskAbbrev = MilestoneHelper.getTaskAbbreivationNameById(task.getTaskAbbreviationID());
/*  757 */                         String taskComment = "";
/*      */                         
/*  759 */                         if (task.getComments() != null && !task.getComments().equals("")) {
/*  760 */                           taskComment = task.getComments();
/*      */                         }
/*  762 */                         if (taskAbbrev.equalsIgnoreCase("CRD")) {
/*      */                           
/*  764 */                           credits = dueDate;
/*  765 */                           creditsCom = String.valueOf(completionDate) + taskComment;
/*      */                         }
/*  767 */                         else if (taskAbbrev.equalsIgnoreCase("CAD")) {
/*      */                           
/*  769 */                           art = dueDate;
/*  770 */                           artCom = String.valueOf(completionDate) + taskComment;
/*      */                         }
/*  772 */                         else if (taskAbbrev.equalsIgnoreCase("FPC")) {
/*      */                           
/*  774 */                           packing = dueDate;
/*  775 */                           packingCom = String.valueOf(completionDate) + taskComment;
/*      */                         }
/*  777 */                         else if (taskAbbrev.equalsIgnoreCase("TPS")) {
/*      */                           
/*  779 */                           master = dueDate;
/*  780 */                           masterCom = String.valueOf(completionDate) + taskComment;
/*      */                         }
/*  782 */                         else if (taskAbbrev.equalsIgnoreCase("LFS")) {
/*      */                           
/*  784 */                           film = dueDate;
/*  785 */                           filmCom = String.valueOf(completionDate) + taskComment;
/*      */                         }
/*  787 */                         else if (taskAbbrev.equalsIgnoreCase("PSD")) {
/*      */                           
/*  789 */                           plant = dueDate;
/*  790 */                           plantCom = String.valueOf(completionDate) + taskComment;
/*      */                         } 
/*      */                         
/*  793 */                         task = null;
/*      */                       } 
/*      */                     } 
/*      */                   }
/*      */ 
/*      */                   
/*  799 */                   String vSpacer = "";
/*      */ 
/*      */                   
/*  802 */                   String[] bigString = {
/*  803 */                       String.valueOf(artist) + "\n" + title, 
/*  804 */                       comment, 
/*  805 */                       String.valueOf(titleId) + "\n" + format + "\n" + label + "\n" + imprint + "\n\n\n"
/*      */                     };
/*      */ 
/*      */ 
/*      */                   
/*  810 */                   int[] bigLines = {
/*  811 */                       36, 
/*  812 */                       25, 
/*  813 */                       25
/*      */                     };
/*  815 */                   int maxLines = 0;
/*      */                   
/*  817 */                   maxLines = MilestoneHelper.lineCountWCR(bigString, bigLines);
/*  818 */                   maxLines = (maxLines > 2) ? --maxLines : maxLines;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  836 */                   if (pack.length() < 12) {
/*      */ 
/*      */                     
/*  839 */                     for (int k = (comment.length() == 0) ? 2 : 1; k < maxLines; k++) {
/*  840 */                       vSpacer = String.valueOf(vSpacer) + "\n";
/*      */                     }
/*      */                   } else {
/*      */                     
/*  844 */                     int k = pack.length() / 15;
/*      */ 
/*      */                     
/*  847 */                     if (k >= maxLines) {
/*  848 */                       maxLines = k + 1;
/*      */                     }
/*      */                     
/*  851 */                     if (creditsCom.length() == 0 && artCom.length() == 0 && 
/*  852 */                       packingCom.length() == 0 && masterCom.length() == 0 && 
/*  853 */                       filmCom.length() == 0 && plantCom.length() == 0 && 
/*  854 */                       comment.length() == 0) {
/*  855 */                       k += 2;
/*      */                     }
/*      */                     
/*  858 */                     for (; k < maxLines; k++) {
/*  859 */                       vSpacer = String.valueOf(vSpacer) + "\n";
/*      */                     }
/*      */                   } 
/*      */ 
/*      */                   
/*  864 */                   subTable.setRowBorderColor(nextRow - 1, Color.lightGray);
/*  865 */                   subTable.setRowBorderColor(nextRow, Color.lightGray);
/*  866 */                   subTable.setRowBorderColor(nextRow + 1, Color.lightGray);
/*      */                   
/*  868 */                   subTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
/*  869 */                   subTable.setRowFont(nextRow + 1, new Font("Arial", 0, 8));
/*      */                   
/*  871 */                   subTable.setObject(nextRow, 0, String.valueOf(artist) + "\n" + title + "\n" + upc);
/*  872 */                   subTable.setBackground(nextRow, 0, Color.white);
/*  873 */                   subTable.setSpan(nextRow, 0, new Dimension(1, 2));
/*  874 */                   subTable.setAlignment(nextRow, 0, 9);
/*      */ 
/*      */                   
/*  877 */                   mm = String.valueOf(mm) + "\n";
/*      */                   
/*  879 */                   subTable.setObject(nextRow, 1, String.valueOf(mm) + prod);
/*  880 */                   subTable.setBackground(nextRow, 1, Color.white);
/*  881 */                   subTable.setSpan(nextRow, 1, new Dimension(1, 2));
/*  882 */                   subTable.setAlignment(nextRow, 1, 9);
/*      */                   
/*  884 */                   subTable.setObject(nextRow, 2, String.valueOf(titleId) + "\n" + format + "\n" + label + "\n" + imprint);
/*  885 */                   subTable.setBackground(nextRow, 2, Color.white);
/*  886 */                   subTable.setSpan(nextRow, 2, new Dimension(1, 2));
/*  887 */                   subTable.setAlignment(nextRow, 2, 10);
/*      */                   
/*  889 */                   subTable.setObject(nextRow, 3, price);
/*  890 */                   subTable.setBackground(nextRow, 3, Color.white);
/*  891 */                   subTable.setSpan(nextRow, 3, new Dimension(1, 2));
/*  892 */                   subTable.setAlignment(nextRow, 3, 12);
/*      */                   
/*  894 */                   subTable.setObject(nextRow, 4, "Due Dates");
/*  895 */                   subTable.setColBorderColor(nextRow, 4, Color.lightGray);
/*  896 */                   subTable.setColBorder(nextRow, 4, 266240);
/*  897 */                   subTable.setFont(nextRow, 4, new Font("Arial", 1, 9));
/*  898 */                   subTable.setAlignment(nextRow, 4, 34);
/*      */                   
/*  900 */                   subTable.setAlignment(nextRow + 1, 4, 9);
/*  901 */                   subTable.setObject(nextRow + 1, 4, String.valueOf(pack) + vSpacer);
/*  902 */                   subTable.setRowHeight(nextRow, 10);
/*  903 */                   subTable.setRowHeight(nextRow, 10);
/*      */ 
/*      */                   
/*  906 */                   subTable.setObject(nextRow, 5, credits);
/*  907 */                   subTable.setColBorderColor(nextRow, 5, Color.lightGray);
/*  908 */                   subTable.setColBorder(nextRow, 5, 266240);
/*  909 */                   subTable.setFont(nextRow, 5, new Font("Arial", 1, 8));
/*  910 */                   subTable.setAlignment(nextRow, 5, 10);
/*  911 */                   subTable.setObject(nextRow + 1, 5, creditsCom);
/*  912 */                   subTable.setAlignment(nextRow + 1, 5, 10);
/*      */                   
/*  914 */                   subTable.setObject(nextRow, 6, art);
/*  915 */                   subTable.setColBorderColor(nextRow, 6, Color.lightGray);
/*  916 */                   subTable.setColBorder(nextRow, 6, 266240);
/*  917 */                   subTable.setAlignment(nextRow, 6, 10);
/*  918 */                   subTable.setAlignment(nextRow + 1, 6, 10);
/*  919 */                   subTable.setObject(nextRow + 1, 6, artCom);
/*  920 */                   subTable.setFont(nextRow, 6, new Font("Arial", 1, 8));
/*      */                   
/*  922 */                   subTable.setObject(nextRow, 7, packing);
/*  923 */                   subTable.setColBorderColor(nextRow, 7, Color.lightGray);
/*  924 */                   subTable.setColBorder(nextRow, 7, 266240);
/*  925 */                   subTable.setFont(nextRow, 7, new Font("Arial", 1, 8));
/*  926 */                   subTable.setAlignment(nextRow, 7, 10);
/*  927 */                   subTable.setObject(nextRow + 1, 7, packingCom);
/*  928 */                   subTable.setAlignment(nextRow + 1, 7, 10);
/*      */                   
/*  930 */                   subTable.setObject(nextRow, 8, master);
/*  931 */                   subTable.setColBorderColor(nextRow, 8, Color.lightGray);
/*  932 */                   subTable.setColBorder(nextRow, 8, 266240);
/*  933 */                   subTable.setFont(nextRow, 8, new Font("Arial", 1, 8));
/*  934 */                   subTable.setAlignment(nextRow, 8, 10);
/*  935 */                   subTable.setObject(nextRow + 1, 8, masterCom);
/*  936 */                   subTable.setAlignment(nextRow + 1, 8, 10);
/*      */                   
/*  938 */                   subTable.setObject(nextRow, 9, film);
/*  939 */                   subTable.setColBorderColor(nextRow, 9, Color.lightGray);
/*  940 */                   subTable.setColBorder(nextRow, 9, 266240);
/*  941 */                   subTable.setFont(nextRow, 9, new Font("Arial", 1, 8));
/*  942 */                   subTable.setAlignment(nextRow, 9, 2);
/*  943 */                   subTable.setAlignment(nextRow, 9, 10);
/*  944 */                   subTable.setObject(nextRow + 1, 9, filmCom);
/*  945 */                   subTable.setAlignment(nextRow + 1, 9, 10);
/*      */                   
/*  947 */                   subTable.setObject(nextRow, 10, plant);
/*  948 */                   subTable.setColBorderColor(nextRow, 10, Color.lightGray);
/*  949 */                   subTable.setColBorder(nextRow, 10, 266240);
/*  950 */                   subTable.setFont(nextRow, 10, new Font("Arial", 1, 8));
/*  951 */                   subTable.setAlignment(nextRow, 10, 10);
/*  952 */                   subTable.setObject(nextRow + 1, 10, plantCom);
/*  953 */                   subTable.setAlignment(nextRow + 1, 10, 10);
/*      */                   
/*  955 */                   subTable.setRowBackground(nextRow, Color.lightGray);
/*  956 */                   subTable.setRowForeground(nextRow, Color.black);
/*      */                   
/*  958 */                   subTable.setBackground(nextRow, 11, Color.white);
/*  959 */                   subTable.setSpan(nextRow, 11, new Dimension(1, 2));
/*  960 */                   subTable.setObject(nextRow, 11, comment);
/*  961 */                   subTable.setAlignment(nextRow, 11, 9);
/*      */                   
/*  963 */                   subTable.setRowFont(nextRow, new Font("Arial", 0, 8));
/*      */ 
/*      */ 
/*      */                   
/*  967 */                   Font holidayFont = new Font("Arial", 3, 8);
/*  968 */                   for (int colIdx = 5; colIdx <= 10; colIdx++) {
/*      */                     
/*  970 */                     String dueDate = subTable.getObject(nextRow, colIdx).toString();
/*  971 */                     if (dueDate != null && dueDate.length() > 0) {
/*  972 */                       char lastChar = dueDate.charAt(dueDate.length() - 1);
/*  973 */                       if (Character.isLetter(lastChar)) {
/*  974 */                         subTable.setFont(nextRow, colIdx, holidayFont);
/*      */                       }
/*      */                     } 
/*      */                   } 
/*      */ 
/*      */                   
/*  980 */                   body = new SectionBand(report);
/*      */                   
/*  982 */                   double lfLineCount = 1.5D;
/*      */                   
/*  984 */                   if (maxLines > 0) {
/*      */                     
/*  986 */                     body.setHeight(1.5F);
/*      */                   }
/*      */                   else {
/*      */                     
/*  990 */                     body.setHeight(1.0F);
/*      */                   } 
/*      */                   
/*  993 */                   if (maxLines > 3 || 
/*  994 */                     creditsCom.length() > 10 || artCom.length() > 10 || 
/*  995 */                     packingCom.length() > 10 || masterCom.length() > 10 || 
/*  996 */                     filmCom.length() > 10 || plantCom.length() > 10) {
/*      */ 
/*      */                     
/*  999 */                     if (lfLineCount < maxLines * 0.3D) {
/* 1000 */                       lfLineCount = maxLines * 0.3D;
/*      */                     }
/* 1002 */                     if (lfLineCount < (creditsCom.length() / 7) * 0.3D) {
/* 1003 */                       lfLineCount = (creditsCom.length() / 7) * 0.3D;
/*      */                     }
/* 1005 */                     if (lfLineCount < (artCom.length() / 8) * 0.3D) {
/* 1006 */                       lfLineCount = (artCom.length() / 8) * 0.3D;
/*      */                     }
/* 1008 */                     if (lfLineCount < (packingCom.length() / 8) * 0.3D) {
/* 1009 */                       lfLineCount = (packingCom.length() / 8) * 0.3D;
/*      */                     }
/* 1011 */                     if (lfLineCount < (masterCom.length() / 8) * 0.3D) {
/* 1012 */                       lfLineCount = (masterCom.length() / 8) * 0.3D;
/*      */                     }
/* 1014 */                     if (lfLineCount < (filmCom.length() / 8) * 0.3D) {
/* 1015 */                       lfLineCount = (filmCom.length() / 8) * 0.3D;
/*      */                     }
/* 1017 */                     if (lfLineCount < (plantCom.length() / 8) * 0.3D) {
/* 1018 */                       lfLineCount = (plantCom.length() / 8) * 0.3D;
/*      */                     }
/* 1020 */                     body.setHeight((float)lfLineCount);
/*      */                   }
/*      */                   else {
/*      */                     
/* 1024 */                     body.setHeight(1.0F);
/*      */                   } 
/*      */                   
/* 1027 */                   body.addTable(subTable, new Rectangle(800, 800));
/* 1028 */                   body.setBottomBorder(0);
/* 1029 */                   body.setTopBorder(0);
/* 1030 */                   body.setShrinkToFit(true);
/* 1031 */                   body.setVisible(true);
/*      */                   
/* 1033 */                   group = new DefaultSectionLens(null, group, body);
/*      */                 } 
/* 1035 */                 group = new DefaultSectionLens(null, group, spacer);
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/* 1040 */         group = new DefaultSectionLens(hbandType, group, null);
/* 1041 */         report.addSection(group, rowCountTable);
/* 1042 */         report.addPageBreak();
/* 1043 */         group = null;
/*      */       }
/*      */     
/* 1046 */     } catch (Exception e) {
/*      */       
/* 1048 */       System.out.println(">>>>>>>>ReportHandler(): exception: " + e);
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
/*      */   public static Hashtable groupSelections(Vector selections) {
/* 1065 */     Hashtable allSelections = new Hashtable();
/* 1066 */     if (selections == null) {
/* 1067 */       return allSelections;
/*      */     }
/* 1069 */     for (int i = 0; i < selections.size(); i++) {
/*      */       
/* 1071 */       Selection sel = (Selection)selections.elementAt(i);
/* 1072 */       if (sel != null) {
/*      */         
/* 1074 */         sel = SelectionManager.getInstance().getSelectionAndSchedule(sel.getSelectionID());
/*      */ 
/*      */         
/* 1077 */         String configIDString = "", configString = "", dateString = "";
/*      */ 
/*      */         
/* 1080 */         configIDString = (sel.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation() != null) ? sel.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation() : "";
/*      */         
/* 1082 */         SelectionStatus status = sel.getSelectionStatus();
/* 1083 */         String statusString = "";
/*      */         
/* 1085 */         if (status != null) {
/* 1086 */           statusString = (status.getName() == null) ? "" : status.getName();
/*      */         }
/* 1088 */         dateString = (sel.getStreetDate() != null) ? MilestoneHelper.getFormatedDate(sel.getStreetDate()) : "No street date";
/*      */         
/* 1090 */         String typeString = (sel.getReleaseType() != null) ? sel.getReleaseType().getName() : "";
/*      */         
/* 1092 */         if (typeString.trim().toUpperCase().startsWith("PROMO")) {
/*      */ 
/*      */           
/* 1095 */           configIDString = "Promos";
/*      */ 
/*      */         
/*      */         }
/* 1099 */         else if (configIDString != null) {
/*      */           
/* 1101 */           configIDString = configIDString.trim();
/* 1102 */           configIDString = configIDString.toUpperCase();
/*      */ 
/*      */           
/* 1105 */           if (configIDString.equals("ALBUM") || configIDString.equals("CASS") || 
/* 1106 */             configIDString.equals("CD") || configIDString.equals("ECD") || 
/* 1107 */             configIDString.equals("CASSEP") || configIDString.equals("CDEP") || 
/* 1108 */             configIDString.equals("ECDEP") || configIDString.equals("DVDAUD") || configIDString.startsWith("CDADVD") || configIDString.startsWith("MIXED") || 
/* 1109 */             configIDString.equals("DVDVID") || configIDString.equals("VIDEO") || configIDString.startsWith("SACD") || configIDString.startsWith("DUALDISC") || 
/* 1110 */             configIDString.equals("UMD") || configIDString.equals("UMDFL")) {
/* 1111 */             configIDString = "Commercial Full Length";
/*      */           } else {
/*      */             
/* 1114 */             configIDString = "Commercial Singles";
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1122 */         String monthString = "";
/*      */         
/* 1124 */         if (sel.getStreetDate() != null && !statusString.equalsIgnoreCase("TBS") && !statusString.equalsIgnoreCase("In the Works")) {
/*      */ 
/*      */           
/* 1127 */           SimpleDateFormat dueDateFormatter = new SimpleDateFormat("MM/yyyy");
/* 1128 */           monthString = dueDateFormatter.format(sel.getStreetDate().getTime());
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1136 */         if (statusString.equalsIgnoreCase("TBS")) {
/*      */           
/* 1138 */           monthString = "13";
/*      */         }
/* 1140 */         else if (statusString.equalsIgnoreCase("In The Works")) {
/*      */           
/* 1142 */           monthString = "26";
/*      */ 
/*      */         
/*      */         }
/* 1146 */         else if (monthString.length() < 1) {
/* 1147 */           monthString = "52";
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1156 */         Hashtable configSubTable = (Hashtable)allSelections.get(configIDString);
/* 1157 */         if (configSubTable == null) {
/*      */ 
/*      */           
/* 1160 */           configSubTable = new Hashtable();
/* 1161 */           allSelections.put(configIDString, configSubTable);
/*      */         } 
/*      */ 
/*      */         
/* 1165 */         Hashtable monthsTable = (Hashtable)configSubTable.get(monthString);
/* 1166 */         if (monthsTable == null) {
/*      */ 
/*      */           
/* 1169 */           monthsTable = new Hashtable();
/* 1170 */           configSubTable.put(monthString, monthsTable);
/*      */         } 
/*      */ 
/*      */         
/* 1174 */         Vector selectionsForDate = (Vector)monthsTable.get(dateString);
/* 1175 */         if (selectionsForDate == null) {
/*      */ 
/*      */           
/* 1178 */           selectionsForDate = new Vector();
/* 1179 */           monthsTable.put(dateString, selectionsForDate);
/*      */         } 
/*      */ 
/*      */         
/* 1183 */         selectionsForDate.addElement(sel);
/*      */       } 
/*      */     } 
/*      */     
/* 1187 */     return allSelections;
/*      */   }
/*      */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\IgaProductionScheduleUpdateForPrintSubHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */