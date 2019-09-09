/*      */ package WEB-INF.classes.com.universal.milestone;
/*      */ 
/*      */ import com.techempower.ComponentLog;
/*      */ import com.techempower.gemini.Context;
/*      */ import com.techempower.gemini.GeminiApplication;
/*      */ import com.universal.milestone.Company;
/*      */ import com.universal.milestone.Form;
/*      */ import com.universal.milestone.MilestoneHelper;
/*      */ import com.universal.milestone.Plant;
/*      */ import com.universal.milestone.ReleasingFamily;
/*      */ import com.universal.milestone.Schedule;
/*      */ import com.universal.milestone.ScheduledTask;
/*      */ import com.universal.milestone.SecureHandler;
/*      */ import com.universal.milestone.Selection;
/*      */ import com.universal.milestone.SelectionManager;
/*      */ import com.universal.milestone.StringDateComparator;
/*      */ import com.universal.milestone.UmlProductionScheduleForPrintSubHandler;
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
/*      */ public class UmlProductionScheduleForPrintSubHandler
/*      */   extends SecureHandler
/*      */ {
/*      */   public static final String COMPONENT_CODE = "hPsp";
/*      */   public GeminiApplication application;
/*      */   public ComponentLog log;
/*      */   
/*      */   public UmlProductionScheduleForPrintSubHandler(GeminiApplication application) {
/*   72 */     this.application = application;
/*   73 */     this.log = application.getLog("hPsp");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   81 */   public String getDescription() { return "Sub Report"; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static void fillUmlProductionScheduleForPrint(XStyleSheet report, Context context) {
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
/*      */     
/*      */     try {
/*  119 */       HttpServletResponse sresponse = context.getResponse();
/*  120 */       context.putDelivery("status", new String("start_report"));
/*  121 */       context.putDelivery("percent", new String("10"));
/*  122 */       context.includeJSP("status.jsp", "hiddenFrame");
/*  123 */       sresponse.setContentType("text/plain");
/*  124 */       sresponse.flushBuffer();
/*      */     }
/*  126 */     catch (Exception exception) {}
/*      */ 
/*      */     
/*  129 */     int DATA_FONT_SIZE = 7;
/*  130 */     int SMALL_HEADER_FONT_SIZE = 8;
/*  131 */     int NUM_COLUMNS = 17;
/*  132 */     Color SHADED_AREA_COLOR = Color.lightGray;
/*      */     
/*  134 */     SectionBand hbandType = new SectionBand(report);
/*  135 */     SectionBand hbandCategory = new SectionBand(report);
/*  136 */     SectionBand hbandDate = new SectionBand(report);
/*  137 */     SectionBand body = new SectionBand(report);
/*  138 */     SectionBand footer = new SectionBand(report);
/*  139 */     SectionBand spacer = new SectionBand(report);
/*  140 */     DefaultSectionLens group = null;
/*      */     
/*  142 */     footer.setVisible(true);
/*  143 */     footer.setHeight(0.1F);
/*  144 */     footer.setShrinkToFit(false);
/*  145 */     footer.setBottomBorder(0);
/*      */     
/*  147 */     spacer.setVisible(true);
/*  148 */     spacer.setHeight(0.05F);
/*  149 */     spacer.setShrinkToFit(false);
/*  150 */     spacer.setBottomBorder(0);
/*      */ 
/*      */     
/*  153 */     Hashtable selTable = groupSelectionsByCompanyAndStreetDate(selections);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  159 */     Enumeration configs = selTable.keys();
/*  160 */     Vector configVector = new Vector();
/*      */     
/*  162 */     while (configs.hasMoreElements()) {
/*  163 */       configVector.addElement(configs.nextElement());
/*      */     }
/*  165 */     int numConfigs = configVector.size();
/*      */ 
/*      */     
/*      */     try {
/*  169 */       Collections.sort(configVector);
/*  170 */       Vector sortedConfigVector = MilestoneHelper.sortStrings(configVector);
/*      */ 
/*      */       
/*  173 */       DefaultTableLens rowCountTable = null;
/*  174 */       DefaultTableLens columnHeaderTable = null;
/*  175 */       DefaultTableLens subTable = null;
/*  176 */       DefaultTableLens companyTableLens = null;
/*  177 */       DefaultTableLens dateTableLens = null;
/*      */       
/*  179 */       rowCountTable = new DefaultTableLens(2, 10000);
/*      */       
/*  181 */       int totalCount = 0;
/*  182 */       int tenth = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  187 */       Vector selectionsC = new Vector();
/*  188 */       int counterC = 0;
/*  189 */       for (int n = 0; n < sortedConfigVector.size(); n++) {
/*      */         
/*  191 */         String configC = (configVector.elementAt(n) != null) ? (String)configVector.elementAt(n) : "";
/*      */         
/*  193 */         Hashtable monthTableC = (Hashtable)selTable.get(configC);
/*      */ 
/*      */         
/*  196 */         Enumeration monthsC = monthTableC.keys();
/*  197 */         Vector monthVectorC = new Vector();
/*      */         
/*  199 */         while (monthsC.hasMoreElements()) {
/*  200 */           monthVectorC.add((String)monthsC.nextElement());
/*      */         }
/*  202 */         Object[] monthArrayC = (Object[])null;
/*  203 */         monthArrayC = monthVectorC.toArray();
/*      */ 
/*      */         
/*  206 */         for (int x = 0; x < monthArrayC.length; x++) {
/*      */ 
/*      */           
/*  209 */           String monthNameC = (String)monthArrayC[x];
/*      */           
/*  211 */           selectionsC = (Vector)monthTableC.get(monthNameC);
/*      */           
/*  213 */           counterC += selectionsC.size();
/*      */         } 
/*      */       } 
/*      */       
/*  217 */       totalCount = counterC;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  222 */       tenth = (totalCount > 10) ? (totalCount / 10) : 1;
/*      */       
/*  224 */       HttpServletResponse sresponse = context.getResponse();
/*  225 */       context.putDelivery("status", new String("start_report"));
/*  226 */       context.putDelivery("percent", new String("20"));
/*  227 */       context.includeJSP("status.jsp", "hiddenFrame");
/*  228 */       sresponse.setContentType("text/plain");
/*  229 */       sresponse.flushBuffer();
/*      */       
/*  231 */       int recordCount = 0;
/*  232 */       int count = 0;
/*      */       
/*  234 */       for (int n = 0; n < sortedConfigVector.size(); n++) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  240 */         Form reportForm = (Form)context.getSessionValue("reportForm");
/*      */         
/*  242 */         Calendar beginStDate = (reportForm.getStringValue("beginDate") != null && 
/*  243 */           reportForm.getStringValue("beginDate").length() > 0) ? 
/*  244 */           MilestoneHelper.getDate(reportForm.getStringValue("beginDate")) : null;
/*      */         
/*  246 */         Calendar endStDate = (reportForm.getStringValue("endDate") != null && 
/*  247 */           reportForm.getStringValue("endDate").length() > 0) ? 
/*  248 */           MilestoneHelper.getDate(reportForm.getStringValue("endDate")) : null;
/*      */         
/*  250 */         report.setElement("crs_startdate", MilestoneHelper.getFormatedDate(beginStDate));
/*  251 */         report.setElement("crs_enddate", MilestoneHelper.getFormatedDate(endStDate));
/*      */         
/*  253 */         SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
/*  254 */         String todayLong = formatter.format(new Date());
/*  255 */         report.setElement("crs_bottomdate", todayLong);
/*      */ 
/*      */ 
/*      */         
/*  259 */         String config = (configVector.elementAt(n) != null) ? (String)configVector.elementAt(n) : "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  265 */         int nextRow = 0;
/*  266 */         companyTableLens = new DefaultTableLens(1, 17);
/*  267 */         hbandCategory = new SectionBand(report);
/*  268 */         hbandCategory.setHeight(0.25F);
/*  269 */         hbandCategory.setShrinkToFit(true);
/*  270 */         hbandCategory.setVisible(true);
/*  271 */         hbandCategory.setBottomBorder(0);
/*  272 */         hbandCategory.setLeftBorder(0);
/*  273 */         hbandCategory.setRightBorder(0);
/*  274 */         hbandCategory.setTopBorder(0);
/*      */         
/*  276 */         companyTableLens.setSpan(nextRow, 0, new Dimension(17, 1));
/*  277 */         companyTableLens.setObject(nextRow, 0, config);
/*  278 */         companyTableLens.setRowHeight(nextRow, 15);
/*  279 */         companyTableLens.setRowFont(nextRow, new Font("Arial", 1, 12));
/*  280 */         companyTableLens.setRowBackground(nextRow, SHADED_AREA_COLOR);
/*  281 */         companyTableLens.setRowForeground(nextRow, Color.black);
/*  282 */         companyTableLens.setRowBorderColor(nextRow, Color.white);
/*  283 */         companyTableLens.setRowBorderColor(nextRow - 1, Color.white);
/*  284 */         companyTableLens.setColBorderColor(nextRow, -1, Color.white);
/*  285 */         companyTableLens.setColBorderColor(nextRow, 0, Color.white);
/*  286 */         companyTableLens.setColBorderColor(nextRow, 16, Color.white);
/*  287 */         hbandCategory.addTable(companyTableLens, new Rectangle(800, 800));
/*  288 */         group = new DefaultSectionLens(null, group, spacer);
/*  289 */         group = new DefaultSectionLens(null, group, hbandCategory);
/*  290 */         group = new DefaultSectionLens(null, group, spacer);
/*      */ 
/*      */         
/*  293 */         Hashtable monthTable = (Hashtable)selTable.get(config);
/*      */ 
/*      */         
/*  296 */         int numMonths = 0;
/*  297 */         int numDates = 0;
/*  298 */         int numSelections = 0;
/*      */         
/*  300 */         if (monthTable != null) {
/*      */           
/*  302 */           Enumeration months = monthTable.keys();
/*  303 */           while (months.hasMoreElements()) {
/*      */             
/*  305 */             String monthName = (String)months.nextElement();
/*      */ 
/*      */             
/*  308 */             numMonths++;
/*      */             
/*  310 */             selections = (Vector)monthTable.get(monthName);
/*  311 */             if (selections != null) {
/*  312 */               numSelections += selections.size();
/*      */             }
/*      */           } 
/*      */         } 
/*  316 */         int numRows = 0;
/*      */ 
/*      */         
/*  319 */         numRows += numMonths * 3;
/*  320 */         numRows += numDates * 2;
/*  321 */         numRows += numSelections * 2;
/*      */         
/*  323 */         numRows += 5;
/*      */ 
/*      */         
/*  326 */         hbandType = new SectionBand(report);
/*  327 */         hbandType.setHeight(0.95F);
/*  328 */         hbandType.setShrinkToFit(true);
/*  329 */         hbandType.setVisible(true);
/*      */ 
/*      */ 
/*      */         
/*  333 */         nextRow = 0;
/*      */ 
/*      */         
/*  336 */         String configHeaderText = !config.trim().equals("") ? config : "Other";
/*      */         
/*  338 */         columnHeaderTable = new DefaultTableLens(1, 17);
/*      */         
/*  340 */         columnHeaderTable.setHeaderRowCount(0);
/*  341 */         columnHeaderTable.setColWidth(0, 120);
/*  342 */         columnHeaderTable.setColWidth(1, 150);
/*  343 */         columnHeaderTable.setColWidth(2, 300);
/*  344 */         columnHeaderTable.setColWidth(3, 120);
/*  345 */         columnHeaderTable.setColWidth(4, 40);
/*  346 */         columnHeaderTable.setColWidth(5, 100);
/*  347 */         columnHeaderTable.setColWidth(6, 60);
/*  348 */         columnHeaderTable.setColWidth(7, 60);
/*  349 */         columnHeaderTable.setColWidth(8, 60);
/*  350 */         columnHeaderTable.setColWidth(9, 60);
/*  351 */         columnHeaderTable.setColWidth(10, 60);
/*  352 */         columnHeaderTable.setColWidth(11, 60);
/*  353 */         columnHeaderTable.setColWidth(12, 60);
/*  354 */         columnHeaderTable.setColWidth(13, 60);
/*  355 */         columnHeaderTable.setColWidth(14, 60);
/*  356 */         columnHeaderTable.setColWidth(15, 60);
/*  357 */         columnHeaderTable.setColWidth(16, 60);
/*      */         
/*  359 */         columnHeaderTable.setAlignment(nextRow, 0, 33);
/*  360 */         columnHeaderTable.setAlignment(nextRow, 1, 34);
/*  361 */         columnHeaderTable.setAlignment(nextRow, 2, 34);
/*  362 */         columnHeaderTable.setAlignment(nextRow, 3, 34);
/*  363 */         columnHeaderTable.setAlignment(nextRow, 4, 34);
/*  364 */         columnHeaderTable.setAlignment(nextRow, 5, 34);
/*  365 */         columnHeaderTable.setAlignment(nextRow, 6, 34);
/*  366 */         columnHeaderTable.setAlignment(nextRow, 7, 34);
/*  367 */         columnHeaderTable.setAlignment(nextRow, 8, 34);
/*  368 */         columnHeaderTable.setAlignment(nextRow, 9, 34);
/*  369 */         columnHeaderTable.setAlignment(nextRow, 10, 34);
/*  370 */         columnHeaderTable.setAlignment(nextRow, 11, 34);
/*  371 */         columnHeaderTable.setAlignment(nextRow, 12, 34);
/*  372 */         columnHeaderTable.setAlignment(nextRow, 13, 34);
/*  373 */         columnHeaderTable.setAlignment(nextRow, 14, 34);
/*  374 */         columnHeaderTable.setAlignment(nextRow, 15, 34);
/*  375 */         columnHeaderTable.setAlignment(nextRow, 16, 34);
/*      */ 
/*      */         
/*  378 */         columnHeaderTable.setObject(nextRow, 0, "*P&D\n Local Prod#");
/*  379 */         columnHeaderTable.setObject(nextRow, 1, "Releasing Family/\nLabel");
/*  380 */         columnHeaderTable.setObject(nextRow, 2, "Artist/Title\nComments");
/*  381 */         columnHeaderTable.setObject(nextRow, 3, "P.O.\nQty");
/*  382 */         columnHeaderTable.setObject(nextRow, 4, "Unit");
/*  383 */         columnHeaderTable.setObject(nextRow, 5, "Exploded Total");
/*  384 */         columnHeaderTable.setObject(nextRow, 6, "Qty\nDone");
/*  385 */         columnHeaderTable.setObject(nextRow, 7, "F/M");
/*  386 */         columnHeaderTable.setObject(nextRow, 8, "BOM");
/*  387 */         columnHeaderTable.setObject(nextRow, 9, "FAP");
/*  388 */         columnHeaderTable.setObject(nextRow, 10, "PRQ");
/*  389 */         columnHeaderTable.setObject(nextRow, 11, "TAPE");
/*  390 */         columnHeaderTable.setObject(nextRow, 12, "FILM");
/*  391 */         columnHeaderTable.setObject(nextRow, 13, "STIC");
/*  392 */         columnHeaderTable.setObject(nextRow, 14, "MC");
/*  393 */         columnHeaderTable.setObject(nextRow, 15, "PAP");
/*  394 */         columnHeaderTable.setObject(nextRow, 16, "PSD");
/*      */         
/*  396 */         setColBorderColor(columnHeaderTable, nextRow, 17, SHADED_AREA_COLOR);
/*      */         
/*  398 */         columnHeaderTable.setRowBorderColor(nextRow - 1, SHADED_AREA_COLOR);
/*  399 */         columnHeaderTable.setRowBorderColor(nextRow, SHADED_AREA_COLOR);
/*  400 */         columnHeaderTable.setRowFont(nextRow, new Font("Arial", 1, 8));
/*  401 */         columnHeaderTable.setRowBackground(nextRow, Color.white);
/*  402 */         columnHeaderTable.setRowForeground(nextRow, Color.black);
/*      */         
/*  404 */         hbandType.addTable(columnHeaderTable, new Rectangle(0, 10, 800, 39));
/*  405 */         hbandType.setBottomBorder(0);
/*      */ 
/*      */         
/*  408 */         if (monthTable != null) {
/*      */ 
/*      */           
/*  411 */           Enumeration months = monthTable.keys();
/*      */           
/*  413 */           Vector monthVector = new Vector();
/*      */           
/*  415 */           while (months.hasMoreElements()) {
/*  416 */             monthVector.add((String)months.nextElement());
/*      */           }
/*  418 */           Object[] monthArray = (Object[])null;
/*  419 */           monthArray = monthVector.toArray();
/*  420 */           Arrays.sort(monthArray, new StringDateComparator());
/*      */           
/*  422 */           for (int x = 0; x < monthArray.length; x++) {
/*      */ 
/*      */             
/*  425 */             String monthName = (String)monthArray[x];
/*  426 */             String monthNameString = monthName;
/*  427 */             selections = (Vector)monthTable.get(monthName);
/*      */             
/*  429 */             if (selections != null) {
/*      */ 
/*      */               
/*  432 */               MilestoneHelper.setSelectionSorting(selections, 12);
/*  433 */               Collections.sort(selections);
/*      */ 
/*      */               
/*  436 */               MilestoneHelper.setSelectionSorting(selections, 4);
/*  437 */               Collections.sort(selections);
/*      */ 
/*      */               
/*  440 */               MilestoneHelper.setSelectionSorting(selections, 3);
/*  441 */               Collections.sort(selections);
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  446 */               MilestoneHelper.setSelectionSorting(selections, 24);
/*  447 */               Collections.sort(selections);
/*      */ 
/*      */               
/*  450 */               MilestoneHelper.setSelectionSorting(selections, 1);
/*  451 */               Collections.sort(selections);
/*      */ 
/*      */               
/*  454 */               MilestoneHelper.setSelectionSorting(selections, 16);
/*  455 */               Collections.sort(selections);
/*      */ 
/*      */               
/*  458 */               MilestoneHelper.setSelectionSorting(selections, 8);
/*  459 */               Collections.sort(selections);
/*      */ 
/*      */               
/*  462 */               MilestoneHelper.setSelectionSorting(selections, 6);
/*  463 */               Collections.sort(selections);
/*      */               
/*  465 */               footer.setVisible(true);
/*  466 */               footer.setHeight(0.1F);
/*  467 */               footer.setShrinkToFit(false);
/*  468 */               footer.setBottomBorder(0);
/*      */ 
/*      */               
/*  471 */               hbandDate = new SectionBand(report);
/*  472 */               hbandDate.setHeight(0.25F);
/*  473 */               hbandDate.setShrinkToFit(true);
/*  474 */               hbandDate.setVisible(true);
/*  475 */               hbandDate.setBottomBorder(0);
/*  476 */               hbandDate.setLeftBorder(0);
/*  477 */               hbandDate.setRightBorder(0);
/*  478 */               hbandDate.setTopBorder(0);
/*  479 */               dateTableLens = new DefaultTableLens(1, 17);
/*      */               
/*  481 */               nextRow = 0;
/*      */               
/*  483 */               dateTableLens.setSpan(nextRow, 0, new Dimension(17, 1));
/*  484 */               dateTableLens.setObject(nextRow, 0, monthName);
/*  485 */               dateTableLens.setRowHeight(nextRow, 16);
/*  486 */               dateTableLens.setRowFont(nextRow, new Font("Arial", 1, 12));
/*  487 */               dateTableLens.setRowForeground(nextRow, Color.black);
/*  488 */               dateTableLens.setRowBorderColor(nextRow, Color.white);
/*  489 */               dateTableLens.setRowBorderColor(nextRow - 1, Color.white);
/*  490 */               dateTableLens.setColBorderColor(nextRow, -1, Color.white);
/*  491 */               dateTableLens.setColBorderColor(nextRow, 0, Color.white);
/*  492 */               dateTableLens.setColBorderColor(nextRow, 16, Color.white);
/*  493 */               dateTableLens.setRowBackground(nextRow, Color.white);
/*      */               
/*  495 */               hbandDate.addTable(dateTableLens, new Rectangle(800, 200));
/*  496 */               hbandDate.setBottomBorder(0);
/*      */               
/*  498 */               group = new DefaultSectionLens(null, group, hbandDate);
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  503 */               MilestoneHelper.applyManufacturingToSelections(selections);
/*      */               
/*  505 */               for (int i = 0; i < selections.size(); i++) {
/*      */ 
/*      */                 
/*  508 */                 Selection sel = (Selection)selections.elementAt(i);
/*      */                 
/*  510 */                 if (count < recordCount / tenth) {
/*      */                   
/*  512 */                   count = recordCount / tenth;
/*  513 */                   sresponse = context.getResponse();
/*  514 */                   context.putDelivery("status", new String("start_report"));
/*  515 */                   int myPercent = count * 10;
/*  516 */                   if (myPercent > 90)
/*  517 */                     myPercent = 90; 
/*  518 */                   context.putDelivery("percent", new String(String.valueOf(myPercent)));
/*  519 */                   context.includeJSP("status.jsp", "hiddenFrame");
/*  520 */                   sresponse.setContentType("text/plain");
/*  521 */                   sresponse.flushBuffer();
/*      */                 } 
/*      */                 
/*  524 */                 recordCount++;
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  529 */                 String titleId = "";
/*  530 */                 titleId = sel.getTitleID();
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  535 */                 String artist = "";
/*  536 */                 artist = sel.getArtist().trim();
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  541 */                 String comment = (sel.getSelectionComments() != null) ? sel.getSelectionComments() : "";
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  546 */                 boolean bpad = false;
/*  547 */                 bpad = sel.getPressAndDistribution();
/*  548 */                 String pAndD = "";
/*      */                 
/*  550 */                 String prefix = "";
/*  551 */                 if (sel.getPrefixID() != null)
/*  552 */                   prefix = String.valueOf(sel.getPrefixID().getAbbreviation()) + " "; 
/*  553 */                 if (sel.getSelectionNo() != null) {
/*  554 */                   if (bpad) {
/*  555 */                     pAndD = "*" + prefix + sel.getSelectionNo();
/*      */                   } else {
/*  557 */                     pAndD = String.valueOf(prefix) + sel.getSelectionNo();
/*      */                   } 
/*      */                 }
/*      */ 
/*      */ 
/*      */                 
/*  563 */                 String releasingFamily = "";
/*  564 */                 if (sel.getReleaseFamilyId() > 0) {
/*  565 */                   releasingFamily = ReleasingFamily.getName(sel.getReleaseFamilyId());
/*      */                 }
/*      */ 
/*      */                 
/*  569 */                 String label = "";
/*      */                 
/*  571 */                 if (sel.getImprint() != null) {
/*  572 */                   label = sel.getImprint();
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
/*  585 */                 String title = "";
/*  586 */                 if (sel.getTitle() != null) {
/*  587 */                   title = sel.getTitle();
/*      */                 }
/*      */ 
/*      */                 
/*  591 */                 Schedule schedule = sel.getSchedule();
/*      */                 
/*  593 */                 Vector tasks = (schedule != null) ? schedule.getTasks() : null;
/*  594 */                 ScheduledTask task = null;
/*      */ 
/*      */                 
/*  597 */                 String FM = "";
/*  598 */                 String BOM = "";
/*  599 */                 String FAP = "";
/*  600 */                 String PRQ = "";
/*  601 */                 String TAPE = "";
/*  602 */                 String FILM = "";
/*  603 */                 String STIC = "";
/*  604 */                 String MC = "";
/*  605 */                 String PAP = "";
/*  606 */                 String PSD = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  612 */                 Vector plants = sel.getManufacturingPlants();
/*      */                 
/*  614 */                 int plantSize = 1;
/*  615 */                 int explodedTotal = 0;
/*  616 */                 int poQtyInt = 0;
/*  617 */                 String compQty = "0";
/*  618 */                 int complqtyInt = 0;
/*      */                 
/*  620 */                 if (plants != null && plants.size() > 0) {
/*  621 */                   plantSize = plants.size();
/*      */                 }
/*  623 */                 for (int plantCount = 0; plantCount < plantSize; plantCount++) {
/*      */ 
/*      */                   
/*  626 */                   Plant p = null;
/*      */                   
/*  628 */                   if (plants != null && plants.size() > 0) {
/*  629 */                     p = (Plant)plants.get(plantCount);
/*      */                   }
/*      */                   
/*  632 */                   int currentPoQty = 0;
/*      */ 
/*      */                   
/*  635 */                   if (p != null && p.getOrderQty() > 0) {
/*  636 */                     currentPoQty = p.getOrderQty();
/*  637 */                     poQtyInt += currentPoQty;
/*      */                   } 
/*      */ 
/*      */                   
/*  641 */                   if (poQtyInt > 0 && sel.getNumberOfUnits() > 0) {
/*  642 */                     explodedTotal += currentPoQty * sel.getNumberOfUnits();
/*      */                   }
/*      */                   
/*  645 */                   if (p != null && p.getCompletedQty() > 0) {
/*  646 */                     complqtyInt += p.getCompletedQty();
/*      */                   }
/*      */                 } 
/*      */                 
/*  650 */                 String units = (sel.getNumberOfUnits() > 0) ? String.valueOf(sel.getNumberOfUnits()) : "";
/*      */                 
/*  652 */                 compQty = MilestoneHelper.formatQuantityWithCommas(String.valueOf(complqtyInt));
/*      */ 
/*      */                 
/*  655 */                 String FMcom = "";
/*  656 */                 String BOMcom = "";
/*  657 */                 String FAPcom = "";
/*  658 */                 String PRQcom = "";
/*  659 */                 String TAPEcom = "";
/*  660 */                 String FILMcom = "";
/*  661 */                 String STICcom = "";
/*  662 */                 String MCcom = "";
/*  663 */                 String PAPcom = "";
/*  664 */                 String PSDcom = "";
/*      */ 
/*      */                 
/*  667 */                 String FMDaysLate = "";
/*  668 */                 String BOMDaysLate = "";
/*  669 */                 String FAPDaysLate = "";
/*  670 */                 String PRQDaysLate = "";
/*  671 */                 String TAPEDaysLate = "";
/*  672 */                 String FILMDaysLate = "";
/*  673 */                 String STICDaysLate = "";
/*  674 */                 String MCDaysLate = "";
/*  675 */                 String PAPDaysLate = "";
/*  676 */                 String PSDDaysLate = "";
/*      */ 
/*      */                 
/*  679 */                 if (tasks != null)
/*      */                 {
/*      */                   
/*  682 */                   for (int j = 0; j < tasks.size(); j++) {
/*      */                     
/*  684 */                     task = (ScheduledTask)tasks.get(j);
/*      */                     
/*  686 */                     if (task != null && task.getDueDate() != null) {
/*      */                       
/*  688 */                       SimpleDateFormat dueDateFormatter = new SimpleDateFormat("M/d");
/*      */ 
/*      */                       
/*  691 */                       String dueDate = String.valueOf(dueDateFormatter.format(task.getDueDate().getTime())) + 
/*  692 */                         " " + MilestoneHelper.getDayType(sel.getCalendarGroup(), task.getDueDate());
/*  693 */                       String completionDate = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*      */                       
/*  695 */                       if (task.getScheduledTaskStatus().equals("N/A"))
/*      */                       {
/*  697 */                         completionDate = task.getScheduledTaskStatus();
/*      */                       }
/*      */ 
/*      */ 
/*      */                       
/*  702 */                       String taskAbbrev = MilestoneHelper.getTaskAbbreivationNameById(task.getTaskAbbreviationID());
/*  703 */                       String taskComment = "";
/*      */                       
/*  705 */                       if (taskAbbrev.equalsIgnoreCase("F/M")) {
/*      */                         
/*  707 */                         FM = dueDate;
/*  708 */                         FMcom = String.valueOf(completionDate) + taskComment;
/*      */                         
/*  710 */                         if (dueDate != null && completionDate != "") {
/*  711 */                           FMDaysLate = getDaysLate(task);
/*      */                         }
/*      */                       }
/*  714 */                       else if (taskAbbrev.equalsIgnoreCase("BOM")) {
/*      */                         
/*  716 */                         BOM = dueDate;
/*  717 */                         BOMcom = String.valueOf(completionDate) + taskComment;
/*      */                         
/*  719 */                         if (dueDate != null && completionDate != "") {
/*  720 */                           BOMDaysLate = getDaysLate(task);
/*      */                         }
/*      */                       }
/*  723 */                       else if (taskAbbrev.equalsIgnoreCase("FAP")) {
/*      */ 
/*      */                         
/*  726 */                         FAP = dueDate;
/*  727 */                         FAPcom = String.valueOf(completionDate) + taskComment;
/*      */                         
/*  729 */                         if (dueDate != null && completionDate != "") {
/*  730 */                           FAPDaysLate = getDaysLate(task);
/*      */                         }
/*      */                       }
/*  733 */                       else if (taskAbbrev.equalsIgnoreCase("PRQ")) {
/*      */ 
/*      */                         
/*  736 */                         PRQ = dueDate;
/*  737 */                         PRQcom = String.valueOf(completionDate) + taskComment;
/*      */                         
/*  739 */                         if (dueDate != null && completionDate != "") {
/*  740 */                           PRQDaysLate = getDaysLate(task);
/*      */                         }
/*      */                       }
/*  743 */                       else if (taskAbbrev.equalsIgnoreCase("TAPE")) {
/*      */                         
/*  745 */                         TAPE = dueDate;
/*  746 */                         TAPEcom = String.valueOf(completionDate) + taskComment;
/*      */                         
/*  748 */                         if (dueDate != null && completionDate != "") {
/*  749 */                           TAPEDaysLate = getDaysLate(task);
/*      */                         }
/*      */                       }
/*  752 */                       else if (taskAbbrev.equalsIgnoreCase("FILM")) {
/*      */ 
/*      */                         
/*  755 */                         FILM = dueDate;
/*  756 */                         FILMcom = String.valueOf(completionDate) + taskComment;
/*      */                         
/*  758 */                         if (dueDate != null && completionDate != "") {
/*  759 */                           FILMDaysLate = getDaysLate(task);
/*      */                         }
/*      */                       }
/*  762 */                       else if (taskAbbrev.equalsIgnoreCase("STIC")) {
/*      */                         
/*  764 */                         STIC = dueDate;
/*  765 */                         STICcom = String.valueOf(completionDate) + taskComment;
/*      */                         
/*  767 */                         if (dueDate != null && completionDate != "") {
/*  768 */                           STICDaysLate = getDaysLate(task);
/*      */                         
/*      */                         }
/*      */                       }
/*  772 */                       else if (taskAbbrev.equalsIgnoreCase("M/C")) {
/*      */                         
/*  774 */                         MC = dueDate;
/*  775 */                         MCcom = String.valueOf(completionDate) + taskComment;
/*      */                         
/*  777 */                         if (dueDate != null && completionDate != "") {
/*  778 */                           MCDaysLate = getDaysLate(task);
/*      */                         }
/*      */                       }
/*  781 */                       else if (taskAbbrev.equalsIgnoreCase("PAP")) {
/*      */ 
/*      */                         
/*  784 */                         PAP = dueDate;
/*  785 */                         PAPcom = String.valueOf(completionDate) + taskComment;
/*      */                         
/*  787 */                         if (dueDate != null && completionDate != "") {
/*  788 */                           PAPDaysLate = getDaysLate(task);
/*      */                         }
/*      */                       }
/*  791 */                       else if (taskAbbrev.equalsIgnoreCase("PSD")) {
/*      */ 
/*      */                         
/*  794 */                         PSD = dueDate;
/*  795 */                         PSDcom = String.valueOf(completionDate) + taskComment;
/*      */                         
/*  797 */                         if (dueDate != null && completionDate != "") {
/*  798 */                           PSDDaysLate = getDaysLate(task);
/*      */                         }
/*      */                       } 
/*      */                       
/*  802 */                       task = null;
/*      */                     } 
/*      */                   } 
/*      */                 }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  811 */                 nextRow = 0;
/*  812 */                 subTable = new DefaultTableLens(3, 17);
/*      */ 
/*      */                 
/*  815 */                 subTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
/*      */                 
/*  817 */                 setColBorderColor(subTable, nextRow, 17, SHADED_AREA_COLOR);
/*      */                 
/*  819 */                 subTable.setHeaderRowCount(0);
/*  820 */                 subTable.setColWidth(0, 120);
/*  821 */                 subTable.setColWidth(1, 150);
/*  822 */                 subTable.setColWidth(2, 300);
/*  823 */                 subTable.setColWidth(3, 120);
/*  824 */                 subTable.setColWidth(4, 40);
/*  825 */                 subTable.setColWidth(5, 100);
/*  826 */                 subTable.setColWidth(6, 60);
/*  827 */                 subTable.setColWidth(7, 60);
/*  828 */                 subTable.setColWidth(8, 60);
/*  829 */                 subTable.setColWidth(9, 60);
/*  830 */                 subTable.setColWidth(10, 60);
/*  831 */                 subTable.setColWidth(11, 60);
/*  832 */                 subTable.setColWidth(12, 60);
/*  833 */                 subTable.setColWidth(13, 60);
/*  834 */                 subTable.setColWidth(14, 60);
/*  835 */                 subTable.setColWidth(15, 60);
/*  836 */                 subTable.setColWidth(16, 60);
/*  837 */                 subTable.setRowBorderColor(nextRow - 1, Color.lightGray);
/*      */                 
/*  839 */                 subTable.setObject(nextRow, 0, String.valueOf(pAndD) + "\n" + sel.getReleaseType().getName());
/*  840 */                 subTable.setBackground(nextRow, 0, Color.white);
/*  841 */                 subTable.setSpan(nextRow, 0, new Dimension(1, 3));
/*  842 */                 subTable.setRowAutoSize(true);
/*  843 */                 subTable.setAlignment(nextRow, 0, 9);
/*  844 */                 subTable.setFont(nextRow, 0, new Font("Arial", 1, 7));
/*      */                 
/*  846 */                 subTable.setSpan(nextRow, 1, new Dimension(1, 3));
/*  847 */                 subTable.setObject(nextRow, 1, String.valueOf(releasingFamily) + " /\n " + label);
/*  848 */                 subTable.setAlignment(nextRow, 1, 10);
/*  849 */                 subTable.setBackground(nextRow, 1, Color.white);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  855 */                 String[] checkStrings = { pAndD };
/*  856 */                 int[] checkStringsLength = { 15 };
/*      */                 
/*  858 */                 int extraLines = MilestoneHelper.lineCountWCR(checkStrings, checkStringsLength);
/*      */                 
/*  860 */                 String[] commentString = { comment };
/*  861 */                 int[] checkCommentLength = { 30 };
/*  862 */                 int commentCounter = MilestoneHelper.lineCountWCR(commentString, checkCommentLength);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  879 */                 extraLines = (extraLines <= 2) ? 0 : (extraLines - 2);
/*  880 */                 for (int z = 0; z < extraLines; z++);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  889 */                 subTable.setObject(nextRow, 2, String.valueOf(artist) + "\n" + title);
/*  890 */                 subTable.setAlignment(nextRow, 2, 10);
/*  891 */                 subTable.setBackground(nextRow, 2, Color.white);
/*  892 */                 subTable.setSpan(nextRow, 2, new Dimension(1, 3));
/*      */                 
/*  894 */                 subTable.setAlignment(nextRow + 1, 4, 10);
/*      */                 
/*  896 */                 subTable.setRowHeight(nextRow, 9);
/*      */                 
/*  898 */                 subTable.setRowBorderColor(nextRow + 1, SHADED_AREA_COLOR);
/*  899 */                 subTable.setRowBorderColor(nextRow + 2, SHADED_AREA_COLOR);
/*      */                 
/*  901 */                 subTable.setObject(nextRow, 3, "Due Date");
/*  902 */                 subTable.setAlignment(nextRow, 3, 2);
/*  903 */                 subTable.setFont(nextRow, 3, new Font("Arial", 1, 7));
/*  904 */                 subTable.setSpan(nextRow, 3, new Dimension(4, 1));
/*  905 */                 subTable.setColBorder(nextRow, 3, 266240);
/*  906 */                 subTable.setRowBorderColor(nextRow, SHADED_AREA_COLOR);
/*      */ 
/*      */                 
/*  909 */                 subTable.setObject(nextRow, 7, FM);
/*  910 */                 subTable.setColBorderColor(nextRow, 7, SHADED_AREA_COLOR);
/*  911 */                 subTable.setColBorder(nextRow, 7, 266240);
/*      */                 
/*  913 */                 subTable.setObject(nextRow, 8, BOM);
/*  914 */                 subTable.setColBorderColor(nextRow, 8, SHADED_AREA_COLOR);
/*  915 */                 subTable.setColBorder(nextRow, 8, 266240);
/*      */                 
/*  917 */                 subTable.setObject(nextRow, 9, FAP);
/*  918 */                 subTable.setColBorderColor(nextRow, 9, SHADED_AREA_COLOR);
/*  919 */                 subTable.setColBorder(nextRow, 9, 266240);
/*      */                 
/*  921 */                 subTable.setObject(nextRow, 10, PRQ);
/*  922 */                 subTable.setColBorderColor(nextRow, 10, Color.lightGray);
/*  923 */                 subTable.setColBorder(nextRow, 10, 266240);
/*      */                 
/*  925 */                 subTable.setObject(nextRow, 11, TAPE);
/*  926 */                 subTable.setColBorderColor(nextRow, 11, Color.lightGray);
/*  927 */                 subTable.setColBorder(nextRow, 11, 266240);
/*      */                 
/*  929 */                 subTable.setObject(nextRow, 12, FILM);
/*  930 */                 subTable.setColBorderColor(nextRow, 12, SHADED_AREA_COLOR);
/*  931 */                 subTable.setColBorder(nextRow, 12, 266240);
/*      */                 
/*  933 */                 subTable.setObject(nextRow, 13, STIC);
/*  934 */                 subTable.setColBorderColor(nextRow, 13, SHADED_AREA_COLOR);
/*  935 */                 subTable.setColBorder(nextRow, 13, 266240);
/*      */                 
/*  937 */                 subTable.setObject(nextRow, 14, MC);
/*  938 */                 subTable.setColBorderColor(nextRow, 14, SHADED_AREA_COLOR);
/*  939 */                 subTable.setColBorder(nextRow, 14, 266240);
/*      */                 
/*  941 */                 subTable.setObject(nextRow, 15, PAP);
/*  942 */                 subTable.setColBorderColor(nextRow, 15, SHADED_AREA_COLOR);
/*  943 */                 subTable.setColBorder(nextRow, 15, 266240);
/*      */                 
/*  945 */                 subTable.setObject(nextRow, 16, PSD);
/*  946 */                 subTable.setColBorderColor(nextRow, 16, SHADED_AREA_COLOR);
/*  947 */                 subTable.setColBorder(nextRow, 16, 266240);
/*      */ 
/*      */                 
/*  950 */                 subTable.setFont(nextRow, 5, new Font("Arial", 1, 7));
/*  951 */                 subTable.setFont(nextRow, 6, new Font("Arial", 1, 7));
/*      */ 
/*      */ 
/*      */                 
/*  955 */                 Font holidayFont = new Font("Arial", 3, 7);
/*  956 */                 Font nonHolidayFont = new Font("Arial", 1, 7);
/*  957 */                 for (int colIdx = 7; colIdx <= 16; colIdx++) {
/*  958 */                   String dueDate = subTable.getObject(nextRow, colIdx).toString();
/*  959 */                   if (dueDate != null && dueDate.length() > 0) {
/*  960 */                     char lastChar = dueDate.charAt(dueDate.length() - 1);
/*  961 */                     if (Character.isLetter(lastChar)) {
/*  962 */                       subTable.setFont(nextRow, colIdx, holidayFont);
/*      */                     } else {
/*      */                       
/*  965 */                       subTable.setFont(nextRow, colIdx, nonHolidayFont);
/*      */                     } 
/*      */                   } 
/*      */                 } 
/*      */                 
/*  970 */                 subTable.setAlignment(nextRow, 5, 2);
/*  971 */                 subTable.setAlignment(nextRow, 6, 2);
/*  972 */                 subTable.setAlignment(nextRow, 7, 2);
/*  973 */                 subTable.setAlignment(nextRow, 8, 2);
/*  974 */                 subTable.setAlignment(nextRow, 9, 2);
/*  975 */                 subTable.setAlignment(nextRow, 10, 2);
/*  976 */                 subTable.setAlignment(nextRow, 11, 2);
/*  977 */                 subTable.setAlignment(nextRow, 12, 2);
/*  978 */                 subTable.setAlignment(nextRow, 13, 2);
/*  979 */                 subTable.setAlignment(nextRow, 14, 2);
/*  980 */                 subTable.setAlignment(nextRow, 15, 2);
/*  981 */                 subTable.setAlignment(nextRow, 16, 2);
/*      */ 
/*      */                 
/*  984 */                 subTable.setObject(nextRow + 1, 3, MilestoneHelper.formatQuantityWithCommas(String.valueOf(poQtyInt)));
/*  985 */                 subTable.setObject(nextRow + 1, 4, units);
/*  986 */                 subTable.setObject(nextRow + 1, 5, MilestoneHelper.formatQuantityWithCommas(String.valueOf(explodedTotal)));
/*  987 */                 subTable.setObject(nextRow + 1, 6, MilestoneHelper.formatQuantityWithCommas(compQty));
/*      */                 
/*  989 */                 subTable.setObject(nextRow + 1, 7, FMcom);
/*  990 */                 subTable.setObject(nextRow + 1, 8, BOMcom);
/*  991 */                 subTable.setObject(nextRow + 1, 9, FAPcom);
/*  992 */                 subTable.setObject(nextRow + 1, 10, PRQcom);
/*  993 */                 subTable.setObject(nextRow + 1, 11, TAPEcom);
/*  994 */                 subTable.setObject(nextRow + 1, 12, FILMcom);
/*  995 */                 subTable.setObject(nextRow + 1, 13, STICcom);
/*  996 */                 subTable.setObject(nextRow + 1, 14, MCcom);
/*  997 */                 subTable.setObject(nextRow + 1, 15, PAPcom);
/*  998 */                 subTable.setObject(nextRow + 1, 16, PSDcom);
/*      */ 
/*      */                 
/* 1001 */                 subTable.setObject(nextRow + 2, 3, "Days Late");
/* 1002 */                 subTable.setAlignment(nextRow + 2, 3, 2);
/* 1003 */                 subTable.setFont(nextRow + 2, 3, new Font("Arial", 1, 7));
/* 1004 */                 subTable.setSpan(nextRow + 2, 3, new Dimension(4, 1));
/*      */ 
/*      */                 
/* 1007 */                 subTable.setObject(nextRow + 2, 7, FMDaysLate);
/* 1008 */                 subTable.setObject(nextRow + 2, 8, BOMDaysLate);
/* 1009 */                 subTable.setObject(nextRow + 2, 9, FAPDaysLate);
/* 1010 */                 subTable.setObject(nextRow + 2, 10, PRQDaysLate);
/* 1011 */                 subTable.setObject(nextRow + 2, 11, TAPEDaysLate);
/* 1012 */                 subTable.setObject(nextRow + 2, 12, FILMDaysLate);
/* 1013 */                 subTable.setObject(nextRow + 2, 13, STICDaysLate);
/* 1014 */                 subTable.setObject(nextRow + 2, 14, MCDaysLate);
/* 1015 */                 subTable.setObject(nextRow + 2, 15, PAPDaysLate);
/* 1016 */                 subTable.setObject(nextRow + 2, 16, PSDDaysLate);
/*      */                 
/* 1018 */                 subTable.setAlignment(nextRow + 2, 7, 10);
/* 1019 */                 subTable.setAlignment(nextRow + 2, 8, 10);
/* 1020 */                 subTable.setAlignment(nextRow + 2, 9, 10);
/* 1021 */                 subTable.setAlignment(nextRow + 2, 10, 10);
/* 1022 */                 subTable.setAlignment(nextRow + 2, 11, 10);
/* 1023 */                 subTable.setAlignment(nextRow + 2, 12, 10);
/* 1024 */                 subTable.setAlignment(nextRow + 2, 13, 10);
/* 1025 */                 subTable.setAlignment(nextRow + 2, 14, 10);
/* 1026 */                 subTable.setAlignment(nextRow + 2, 15, 10);
/* 1027 */                 subTable.setAlignment(nextRow + 2, 16, 10);
/*      */ 
/*      */                 
/* 1030 */                 setColBorderColor(subTable, nextRow + 1, 17, SHADED_AREA_COLOR);
/* 1031 */                 setColBorderColor(subTable, nextRow + 2, 17, SHADED_AREA_COLOR);
/*      */                 
/* 1033 */                 subTable.setAlignment(nextRow + 1, 3, 10);
/* 1034 */                 subTable.setAlignment(nextRow + 1, 4, 10);
/* 1035 */                 subTable.setAlignment(nextRow + 1, 5, 10);
/* 1036 */                 subTable.setAlignment(nextRow + 1, 6, 10);
/* 1037 */                 subTable.setAlignment(nextRow + 1, 7, 10);
/* 1038 */                 subTable.setAlignment(nextRow + 1, 8, 10);
/* 1039 */                 subTable.setAlignment(nextRow + 1, 9, 10);
/* 1040 */                 subTable.setAlignment(nextRow + 1, 10, 10);
/* 1041 */                 subTable.setAlignment(nextRow + 1, 11, 10);
/* 1042 */                 subTable.setAlignment(nextRow + 1, 12, 10);
/* 1043 */                 subTable.setAlignment(nextRow + 1, 13, 10);
/* 1044 */                 subTable.setAlignment(nextRow + 1, 14, 10);
/* 1045 */                 subTable.setAlignment(nextRow + 1, 15, 10);
/* 1046 */                 subTable.setAlignment(nextRow + 1, 16, 10);
/*      */                 
/* 1048 */                 subTable.setRowFont(nextRow + 1, new Font("Arial", 0, 7));
/* 1049 */                 subTable.setRowFont(nextRow + 2, new Font("Arial", 0, 7));
/*      */                 
/* 1051 */                 subTable.setRowBackground(nextRow, SHADED_AREA_COLOR);
/* 1052 */                 subTable.setRowForeground(nextRow, Color.black);
/*      */ 
/*      */                 
/* 1055 */                 subTable.setRowFont(nextRow, new Font("Arial", 0, 7));
/*      */                 
/* 1057 */                 body = new SectionBand(report);
/*      */                 
/* 1059 */                 double lfLineCount = 1.5D;
/*      */                 
/* 1061 */                 if (extraLines > 0) {
/*      */                   
/* 1063 */                   body.setHeight(1.5F);
/*      */                 }
/*      */                 else {
/*      */                   
/* 1067 */                   body.setHeight(1.5F);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 1119 */                 body.addTable(subTable, new Rectangle(800, 800));
/* 1120 */                 body.setBottomBorder(0);
/* 1121 */                 body.setTopBorder(0);
/* 1122 */                 body.setShrinkToFit(true);
/* 1123 */                 body.setVisible(true);
/* 1124 */                 group = new DefaultSectionLens(null, group, body);
/* 1125 */                 group = new DefaultSectionLens(null, group, spacer);
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1135 */       group = new DefaultSectionLens(hbandType, group, null);
/* 1136 */       report.addSection(group, rowCountTable);
/* 1137 */       report.addPageBreak();
/* 1138 */       group = null;
/*      */ 
/*      */     
/*      */     }
/* 1142 */     catch (Exception e) {
/*      */       
/* 1144 */       System.out.println(">>>>>>>>IdjProductionScheduleForPrintSubHandler(): exception: " + e);
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
/* 1162 */     for (int i = -1; i < columns; i++)
/*      */     {
/* 1164 */       table.setColBorderColor(row, i, color);
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
/*      */   public static Hashtable groupSelectionsByCompanyAndStreetDate(Vector selections) {
/* 1182 */     Hashtable companyTable = new Hashtable();
/* 1183 */     if (selections == null) {
/* 1184 */       return companyTable;
/*      */     }
/*      */ 
/*      */     
/* 1188 */     for (int i = 0; i < selections.size(); i++) {
/*      */       
/* 1190 */       Selection sel = (Selection)selections.elementAt(i);
/* 1191 */       if (sel != null) {
/*      */         
/* 1193 */         sel = SelectionManager.getInstance().getSelectionAndSchedule(sel.getSelectionID());
/*      */ 
/*      */         
/* 1196 */         String companyName = "";
/* 1197 */         String dateString = "";
/*      */ 
/*      */         
/* 1200 */         Company company = sel.getCompany();
/* 1201 */         if (company != null) {
/* 1202 */           companyName = (company.getName() == null) ? "" : company.getName();
/*      */         }
/*      */ 
/*      */         
/* 1206 */         dateString = MilestoneHelper.getFormatedDate(sel.getStreetDate());
/*      */ 
/*      */ 
/*      */         
/* 1210 */         Hashtable datesTable = (Hashtable)companyTable.get(companyName);
/* 1211 */         if (datesTable == null) {
/*      */ 
/*      */ 
/*      */           
/* 1215 */           datesTable = new Hashtable();
/*      */           
/* 1217 */           companyTable.put(companyName, datesTable);
/*      */         } 
/*      */ 
/*      */         
/* 1221 */         Vector selectionsForDates = (Vector)datesTable.get(dateString);
/* 1222 */         if (selectionsForDates == null) {
/*      */ 
/*      */ 
/*      */           
/* 1226 */           selectionsForDates = new Vector();
/*      */           
/* 1228 */           datesTable.put(dateString, selectionsForDates);
/*      */         } 
/*      */ 
/*      */         
/* 1232 */         selectionsForDates.add(sel);
/*      */       } 
/*      */     } 
/*      */     
/* 1236 */     return companyTable;
/*      */   }
/*      */ 
/*      */   
/*      */   private static String getDaysLate(ScheduledTask task) {
/* 1241 */     Calendar d1 = task.getCompletionDate();
/* 1242 */     Calendar d2 = task.getDueDate();
/*      */     
/* 1244 */     if (d1.after(d2)) {
/*      */       
/* 1246 */       long timer = d1.getTime().getTime() - d2.getTime().getTime();
/*      */       
/* 1248 */       int days = (int)timer / 86400000 - 1;
/* 1249 */       Integer daysInt = new Integer(days);
/* 1250 */       return daysInt.toString();
/*      */     } 
/* 1252 */     return "";
/*      */   }
/*      */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\UmlProductionScheduleForPrintSubHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */