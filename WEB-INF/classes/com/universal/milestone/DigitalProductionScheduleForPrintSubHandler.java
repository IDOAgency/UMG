/*      */ package WEB-INF.classes.com.universal.milestone;
/*      */ 
/*      */ import com.techempower.ComponentLog;
/*      */ import com.techempower.EnhancedProperties;
/*      */ import com.techempower.gemini.Context;
/*      */ import com.techempower.gemini.GeminiApplication;
/*      */ import com.universal.milestone.DigitalProductionScheduleForPrintSubHandler;
/*      */ import com.universal.milestone.Form;
/*      */ import com.universal.milestone.ImpactDate;
/*      */ import com.universal.milestone.MilestoneHelper;
/*      */ import com.universal.milestone.MilestoneHelper_2;
/*      */ import com.universal.milestone.MonthYearComparator;
/*      */ import com.universal.milestone.Pfm;
/*      */ import com.universal.milestone.ReleasingFamily;
/*      */ import com.universal.milestone.Schedule;
/*      */ import com.universal.milestone.ScheduledTask;
/*      */ import com.universal.milestone.SecureHandler;
/*      */ import com.universal.milestone.Selection;
/*      */ import com.universal.milestone.SelectionManager;
/*      */ import inetsoft.report.SectionBand;
/*      */ import inetsoft.report.XStyleSheet;
/*      */ import inetsoft.report.lens.DefaultSectionLens;
/*      */ import inetsoft.report.lens.DefaultTableLens;
/*      */ import java.awt.Color;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Font;
/*      */ import java.awt.Rectangle;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.Arrays;
/*      */ import java.util.Calendar;
/*      */ import java.util.Collections;
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
/*      */ public class DigitalProductionScheduleForPrintSubHandler
/*      */   extends SecureHandler
/*      */ {
/*      */   public static final String COMPONENT_CODE = "hUMEsu";
/*      */   public static ComponentLog log;
/*      */   
/*      */   public DigitalProductionScheduleForPrintSubHandler(GeminiApplication application) {}
/*      */   
/*   86 */   public static void configure(EnhancedProperties props, GeminiApplication application) { log = application.getLog("hUMEsu"); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   94 */   public String getDescription() { return "Digital Production Schedule Report"; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static void fillDigitalProductionScheduleUpdateForPrint(XStyleSheet report, Context context) {
/*  106 */     int COL_LINE_STYLE = 4097;
/*  107 */     int HEADER_FONT_SIZE = 12;
/*      */     
/*  109 */     double ldLineVal = 0.25D;
/*      */ 
/*      */     
/*      */     try {
/*  113 */       HttpServletResponse sresponse = context.getResponse();
/*  114 */       context.putDelivery("status", new String("start_gathering"));
/*  115 */       context.includeJSP("status.jsp", "hiddenFrame");
/*  116 */       sresponse.setContentType("text/plain");
/*  117 */       sresponse.flushBuffer();
/*      */     }
/*  119 */     catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  124 */     Vector selections = MilestoneHelper.getSelectionsForReport(context);
/*      */ 
/*      */     
/*      */     try {
/*  128 */       HttpServletResponse sresponse = context.getResponse();
/*  129 */       context.putDelivery("status", new String("start_report"));
/*  130 */       context.putDelivery("percent", new String("10"));
/*  131 */       context.includeJSP("status.jsp", "hiddenFrame");
/*  132 */       sresponse.setContentType("text/plain");
/*  133 */       sresponse.flushBuffer();
/*      */     }
/*  135 */     catch (Exception e) {
/*      */       
/*  137 */       System.out.println("exception caught in DigitalProductionScheduleForPrintSubhandler");
/*      */     } 
/*      */     
/*  140 */     Hashtable selTable = MilestoneHelper.groupSelectionsByDigitalTypeAndStreetDate(selections);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  147 */       Form reportForm = (Form)context.getSessionValue("reportForm");
/*      */       
/*  149 */       Calendar beginStDate = (reportForm.getStringValue("beginDate") != null && 
/*  150 */         reportForm.getStringValue("beginDate").length() > 0) ? 
/*  151 */         MilestoneHelper.getDate(reportForm.getStringValue("beginDate")) : null;
/*      */       
/*  153 */       Calendar endStDate = (reportForm.getStringValue("endDate") != null && 
/*  154 */         reportForm.getStringValue("endDate").length() > 0) ? 
/*  155 */         MilestoneHelper.getDate(reportForm.getStringValue("endDate")) : null;
/*      */       
/*  157 */       report.setElement("startdate", MilestoneHelper.getFormatedDate(beginStDate));
/*  158 */       report.setElement("enddate", MilestoneHelper.getFormatedDate(endStDate));
/*      */ 
/*      */       
/*  161 */       int numMonths = 0;
/*  162 */       int numConfigs = 0;
/*  163 */       int numSelections = 0;
/*  164 */       Color SHADED_AREA_COLOR = Color.lightGray;
/*  165 */       int DATA_FONT_SIZE = 7;
/*  166 */       int SMALL_HEADER_FONT_SIZE = 8;
/*      */ 
/*      */ 
/*      */       
/*  170 */       int numColumns = 14;
/*  171 */       DefaultTableLens table_contents = null;
/*  172 */       DefaultTableLens columnHeaderTable = null;
/*  173 */       DefaultTableLens rowCountTable = null;
/*  174 */       DefaultTableLens subTable = null;
/*      */       
/*  176 */       SectionBand hbandType = new SectionBand(report);
/*  177 */       SectionBand hbandMonth = new SectionBand(report);
/*  178 */       SectionBand body = new SectionBand(report);
/*  179 */       SectionBand footer = new SectionBand(report);
/*  180 */       DefaultSectionLens group = null;
/*      */       
/*  182 */       Enumeration configs = selTable.keys();
/*  183 */       Vector configVector = new Vector();
/*      */       
/*  185 */       while (configs.hasMoreElements()) {
/*  186 */         configVector.addElement(configs.nextElement());
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  191 */       Object[] sortedArray = (Object[])null;
/*  192 */       sortedArray = MilestoneHelper.sortStringVector(configVector);
/*  193 */       Vector sortedConfigVector = new Vector();
/*  194 */       for (int aCount = 0; aCount < sortedArray.length; aCount++) {
/*  195 */         sortedConfigVector.addElement(sortedArray[aCount]);
/*      */       }
/*      */ 
/*      */       
/*  199 */       int nextRow = 0;
/*      */       
/*  201 */       int totalCount = 0;
/*  202 */       int tenth = 1;
/*      */ 
/*      */       
/*  205 */       String saveConfig = "";
/*      */       
/*  207 */       for (int a = 0; a < sortedConfigVector.size(); a++) {
/*      */         
/*  209 */         totalCount++;
/*  210 */         String configC = (String)sortedConfigVector.get(a);
/*  211 */         Hashtable monthTableC = (Hashtable)selTable.get(configC);
/*      */         
/*  213 */         if (monthTableC != null) {
/*      */           
/*  215 */           Enumeration monthsC = monthTableC.keys();
/*  216 */           Vector monthVectorC = new Vector();
/*      */           
/*  218 */           while (monthsC.hasMoreElements())
/*      */           {
/*  220 */             monthVectorC.add((String)monthsC.nextElement());
/*      */           }
/*      */           
/*  223 */           Object[] monthArrayC = (Object[])null;
/*  224 */           monthArrayC = monthVectorC.toArray();
/*      */           
/*  226 */           for (int b = 0; b < monthArrayC.length; b++) {
/*      */             
/*  228 */             totalCount++;
/*  229 */             String monthNameC = (String)monthArrayC[b];
/*      */             
/*  231 */             Vector selectionsC = (Vector)monthTableC.get(monthNameC);
/*  232 */             if (selectionsC == null) {
/*  233 */               selectionsC = new Vector();
/*      */             }
/*  235 */             totalCount += selectionsC.size();
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  240 */       tenth = (totalCount > 10) ? (totalCount / 10) : 1;
/*      */       
/*  242 */       HttpServletResponse sresponse = context.getResponse();
/*  243 */       context.putDelivery("status", new String("start_report"));
/*  244 */       context.putDelivery("percent", new String("10"));
/*  245 */       context.includeJSP("status.jsp", "hiddenFrame");
/*  246 */       sresponse.setContentType("text/plain");
/*  247 */       sresponse.flushBuffer();
/*      */       
/*  249 */       int recordCount = 0;
/*  250 */       int count = 0;
/*      */       
/*  252 */       for (int n = 0; n < sortedConfigVector.size(); n++)
/*      */       {
/*  254 */         if (count < recordCount / tenth) {
/*      */           
/*  256 */           count = recordCount / tenth;
/*  257 */           sresponse = context.getResponse();
/*  258 */           context.putDelivery("status", new String("start_report"));
/*  259 */           context.putDelivery("percent", new String(String.valueOf(count * 10)));
/*  260 */           context.includeJSP("status.jsp", "hiddenFrame");
/*  261 */           sresponse.setContentType("text/plain");
/*  262 */           sresponse.flushBuffer();
/*      */         } 
/*      */         
/*  265 */         recordCount++;
/*      */         
/*  267 */         long start_config = System.currentTimeMillis();
/*      */         
/*  269 */         String config = (String)sortedConfigVector.get(n);
/*  270 */         Hashtable monthTable = (Hashtable)selTable.get(config);
/*      */ 
/*      */         
/*  273 */         rowCountTable = new DefaultTableLens(2, 10000);
/*  274 */         table_contents = new DefaultTableLens(1, 14);
/*      */         
/*  276 */         table_contents.setColWidth(0, 70);
/*  277 */         table_contents.setColWidth(1, 150);
/*  278 */         table_contents.setColWidth(2, 65);
/*  279 */         table_contents.setColWidth(3, 150);
/*  280 */         table_contents.setColWidth(4, 65);
/*  281 */         table_contents.setColWidth(5, 65);
/*  282 */         table_contents.setColWidth(6, 105);
/*  283 */         table_contents.setColWidth(7, 80);
/*  284 */         table_contents.setColWidth(8, 80);
/*  285 */         table_contents.setColWidth(9, 80);
/*  286 */         table_contents.setColWidth(10, 70);
/*  287 */         table_contents.setColWidth(11, 70);
/*  288 */         table_contents.setColWidth(12, 85);
/*  289 */         table_contents.setColWidth(13, 70);
/*      */ 
/*      */         
/*  292 */         nextRow = 0;
/*  293 */         hbandType = new SectionBand(report);
/*  294 */         hbandType.setHeight(1.0F);
/*  295 */         hbandType.setShrinkToFit(false);
/*  296 */         hbandType.setVisible(true);
/*      */         
/*  298 */         int cols = 14;
/*      */ 
/*      */         
/*  301 */         table_contents.setObject(nextRow, 0, "");
/*  302 */         table_contents.setSpan(nextRow, 0, new Dimension(cols, 1));
/*  303 */         table_contents.setRowHeight(nextRow, 14);
/*  304 */         table_contents.setRowBackground(nextRow, Color.white);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  312 */         table_contents.setRowBorderColor(nextRow, Color.black);
/*  313 */         table_contents.setRowBorder(nextRow, 266240);
/*  314 */         table_contents.setAlignment(nextRow, 0, 2);
/*  315 */         table_contents.setObject(nextRow, 0, config);
/*  316 */         table_contents.setRowFont(nextRow, new Font("Arial", 3, 12));
/*      */         
/*  318 */         nextRow++;
/*      */         
/*  320 */         hbandType.addTable(table_contents, new Rectangle(0, 0, 800, 25));
/*      */ 
/*      */         
/*  323 */         if (monthTable != null) {
/*      */           
/*  325 */           Enumeration months = monthTable.keys();
/*  326 */           Vector monthVector = new Vector();
/*      */           
/*  328 */           while (months.hasMoreElements())
/*      */           {
/*  330 */             monthVector.add((String)months.nextElement());
/*      */           }
/*      */           
/*  333 */           Object[] monthArray = (Object[])null;
/*  334 */           monthArray = monthVector.toArray();
/*  335 */           Arrays.sort(monthArray, new MonthYearComparator());
/*      */           
/*  337 */           for (int x = 0; x < monthArray.length; x++) {
/*      */             
/*  339 */             if (count < recordCount / tenth) {
/*      */               
/*  341 */               count = recordCount / tenth;
/*  342 */               sresponse = context.getResponse();
/*  343 */               context.putDelivery("status", new String("start_report"));
/*  344 */               context.putDelivery("percent", new String(String.valueOf(count * 10)));
/*  345 */               context.includeJSP("status.jsp", "hiddenFrame");
/*  346 */               sresponse.setContentType("text/plain");
/*  347 */               sresponse.flushBuffer();
/*      */             } 
/*      */             
/*  350 */             recordCount++;
/*      */             
/*  352 */             long start_month = System.currentTimeMillis();
/*  353 */             String monthName = (String)monthArray[x];
/*  354 */             String monthNameString = monthName;
/*      */             
/*  356 */             hbandMonth = new SectionBand(report);
/*  357 */             hbandMonth.setHeight(0.25F);
/*  358 */             hbandMonth.setShrinkToFit(true);
/*  359 */             hbandMonth.setVisible(true);
/*  360 */             hbandMonth.setBottomBorder(0);
/*  361 */             hbandMonth.setLeftBorder(0);
/*  362 */             hbandMonth.setRightBorder(0);
/*  363 */             hbandMonth.setTopBorder(0);
/*      */ 
/*      */             
/*      */             try {
/*  367 */               Calendar currentDate = MilestoneHelper.getMYDate(monthName);
/*  368 */               SimpleDateFormat monthFormatter = new SimpleDateFormat("MMMMM");
/*  369 */               monthNameString = monthFormatter.format(currentDate.getTime());
/*      */             }
/*  371 */             catch (Exception e) {
/*      */               
/*  373 */               if (monthName.equals("13")) {
/*  374 */                 monthNameString = "TBS";
/*  375 */               } else if (monthName.equals("26")) {
/*  376 */                 monthNameString = "ITW";
/*      */               } else {
/*  378 */                 monthNameString = "No street date";
/*      */               } 
/*      */             } 
/*  381 */             selections = (Vector)monthTable.get(monthName);
/*  382 */             if (selections == null) {
/*  383 */               selections = new Vector();
/*      */             }
/*      */ 
/*      */             
/*  387 */             MilestoneHelper.setSelectionSorting(selections, 22);
/*  388 */             Collections.sort(selections);
/*      */ 
/*      */             
/*  391 */             MilestoneHelper.setSelectionSorting(selections, 25);
/*  392 */             Collections.sort(selections);
/*      */ 
/*      */             
/*  395 */             MilestoneHelper.setSelectionSorting(selections, 26);
/*  396 */             Collections.sort(selections);
/*      */             
/*  398 */             columnHeaderTable = new DefaultTableLens(1, 14);
/*      */             
/*  400 */             nextRow = 0;
/*      */             
/*  402 */             table_contents.setColWidth(0, 70);
/*      */             
/*  404 */             columnHeaderTable.setColWidth(0, 70);
/*  405 */             columnHeaderTable.setColWidth(1, 150);
/*  406 */             columnHeaderTable.setColWidth(2, 65);
/*  407 */             columnHeaderTable.setColWidth(3, 150);
/*  408 */             columnHeaderTable.setColWidth(4, 65);
/*  409 */             columnHeaderTable.setColWidth(5, 65);
/*  410 */             columnHeaderTable.setColWidth(6, 105);
/*  411 */             columnHeaderTable.setColWidth(7, 80);
/*  412 */             columnHeaderTable.setColWidth(8, 80);
/*  413 */             columnHeaderTable.setColWidth(9, 80);
/*  414 */             columnHeaderTable.setColWidth(10, 70);
/*  415 */             columnHeaderTable.setColWidth(11, 70);
/*  416 */             columnHeaderTable.setColWidth(12, 85);
/*  417 */             columnHeaderTable.setColWidth(13, 70);
/*      */ 
/*      */             
/*  420 */             columnHeaderTable.setRowAlignment(nextRow, 2);
/*  421 */             columnHeaderTable.setObject(nextRow, 0, "Digital\nRelease\nDate");
/*  422 */             columnHeaderTable.setObject(nextRow, 1, "Artist /\nTitle");
/*  423 */             columnHeaderTable.setObject(nextRow, 2, "Explicit\n(Y/N)");
/*  424 */             columnHeaderTable.setObject(nextRow, 3, "Releasing Family\nLabel");
/*  425 */             columnHeaderTable.setObject(nextRow, 4, "Priority");
/*  426 */             if (config.equals("eAlbum")) {
/*  427 */               columnHeaderTable.setObject(nextRow, 5, "Phys.\nStreet\nDate");
/*  428 */               columnHeaderTable.setObject(nextRow, 6, "Corresponding\nPhysical\nLocal Prod # /\nDigital UPC");
/*      */             } else {
/*  430 */               columnHeaderTable.setObject(nextRow, 5, "Radio\nImpact\nDate");
/*  431 */               columnHeaderTable.setObject(nextRow, 6, "Corresponding\nPhysical\nLocal Prod # /\nDigital UPC");
/*      */             } 
/*  433 */             columnHeaderTable.setObject(nextRow, 7, "eCommerce\nNotified");
/*  434 */             columnHeaderTable.setObject(nextRow, 8, "PFM\nCompleted");
/*  435 */             columnHeaderTable.setObject(nextRow, 9, "Graphics\nReceived");
/*  436 */             columnHeaderTable.setObject(nextRow, 10, "Audio\nRecieved");
/*  437 */             columnHeaderTable.setObject(nextRow, 11, "Digital Parts\nOrder Placed");
/*  438 */             columnHeaderTable.setObject(nextRow, 12, "Legal\nClearance");
/*  439 */             columnHeaderTable.setObject(nextRow, 13, "Parts\nreceived\nby\nClient");
/*      */             
/*  441 */             columnHeaderTable.setRowAlignment(nextRow, 34);
/*  442 */             columnHeaderTable.setRowFont(nextRow, new Font("Arial", 1, 8));
/*  443 */             columnHeaderTable.setRowBorderColor(nextRow, Color.lightGray);
/*  444 */             columnHeaderTable.setRowBorderColor(nextRow - 1, Color.lightGray);
/*  445 */             columnHeaderTable.setColBorderColor(nextRow, -1, Color.lightGray);
/*  446 */             columnHeaderTable.setColBorderColor(nextRow, 0, Color.lightGray);
/*  447 */             columnHeaderTable.setColBorderColor(nextRow, 1, Color.lightGray);
/*  448 */             columnHeaderTable.setColBorderColor(nextRow, 2, Color.lightGray);
/*  449 */             columnHeaderTable.setColBorderColor(nextRow, 3, Color.lightGray);
/*  450 */             columnHeaderTable.setColBorderColor(nextRow, 4, Color.lightGray);
/*  451 */             columnHeaderTable.setColBorderColor(nextRow, 5, Color.lightGray);
/*  452 */             columnHeaderTable.setColBorderColor(nextRow, 6, Color.lightGray);
/*  453 */             columnHeaderTable.setColBorderColor(nextRow, 7, Color.lightGray);
/*  454 */             columnHeaderTable.setColBorderColor(nextRow, 8, Color.lightGray);
/*  455 */             columnHeaderTable.setColBorderColor(nextRow, 9, Color.lightGray);
/*  456 */             columnHeaderTable.setColBorderColor(nextRow, 10, Color.lightGray);
/*  457 */             columnHeaderTable.setColBorderColor(nextRow, 11, Color.lightGray);
/*  458 */             columnHeaderTable.setColBorderColor(nextRow, 12, Color.lightGray);
/*  459 */             columnHeaderTable.setColBorderColor(nextRow, 13, Color.lightGray);
/*      */             
/*  461 */             columnHeaderTable.setRowBorderColor(nextRow, Color.lightGray);
/*      */ 
/*      */             
/*  464 */             hbandType.addTable(columnHeaderTable, new Rectangle(0, 25, 800, 50));
/*      */             
/*  466 */             nextRow = 0;
/*      */             
/*  468 */             DefaultTableLens monthTableLens = new DefaultTableLens(1, 14);
/*      */             
/*  470 */             nextRow = 0;
/*      */ 
/*      */             
/*  473 */             monthTableLens.setObject(nextRow, 0, monthNameString);
/*  474 */             monthTableLens.setSpan(nextRow, 0, new Dimension(14, 1));
/*  475 */             monthTableLens.setRowAlignment(nextRow, 1);
/*  476 */             monthTableLens.setRowFont(nextRow, new Font("Arial", 1, 12));
/*  477 */             monthTableLens.setRowHeight(nextRow, 14);
/*  478 */             monthTableLens.setColBorderColor(nextRow, -1, Color.white);
/*  479 */             monthTableLens.setColBorderColor(nextRow, 13, Color.white);
/*  480 */             monthTableLens.setColBorderColor(nextRow, 14, Color.white);
/*  481 */             monthTableLens.setRowBorderColor(nextRow, Color.white);
/*  482 */             monthTableLens.setRowBorderColor(nextRow - 1, Color.white);
/*  483 */             monthTableLens.setRowBackground(nextRow, Color.lightGray);
/*      */             
/*  485 */             hbandMonth.addTable(monthTableLens, new Rectangle(800, 800));
/*  486 */             hbandMonth.setBottomBorder(0);
/*      */             
/*  488 */             footer.setVisible(true);
/*  489 */             footer.setHeight(0.05F);
/*  490 */             footer.setShrinkToFit(true);
/*  491 */             footer.setBottomBorder(0);
/*      */             
/*  493 */             group = new DefaultSectionLens(null, group, footer);
/*  494 */             group = new DefaultSectionLens(null, group, hbandMonth);
/*  495 */             group = new DefaultSectionLens(null, group, footer);
/*      */ 
/*      */             
/*  498 */             for (int j = 0; j < selections.size(); j++) {
/*      */ 
/*      */               
/*  501 */               if (count < recordCount / tenth) {
/*      */                 
/*  503 */                 count = recordCount / tenth;
/*  504 */                 sresponse = context.getResponse();
/*  505 */                 context.putDelivery("status", new String("start_report"));
/*  506 */                 context.putDelivery("percent", new String(String.valueOf(count * 10)));
/*  507 */                 context.includeJSP("status.jsp", "hiddenFrame");
/*  508 */                 sresponse.setContentType("text/plain");
/*  509 */                 sresponse.flushBuffer();
/*      */               } 
/*      */               
/*  512 */               recordCount++;
/*      */               
/*  514 */               long start_selection = System.currentTimeMillis();
/*      */ 
/*      */               
/*  517 */               Selection sel = (Selection)selections.elementAt(j);
/*  518 */               sel = SelectionManager.getInstance().getSelectionAndSchedule(sel.getSelectionID());
/*      */ 
/*      */               
/*  521 */               Schedule schedule = sel.getSchedule();
/*  522 */               Vector tasks = null;
/*  523 */               if (schedule != null) {
/*  524 */                 tasks = schedule.getTasks();
/*      */               }
/*      */ 
/*      */               
/*  528 */               String digitalReleaseDate = MilestoneHelper.getFormatedDate(sel.getDigitalRlsDate());
/*      */ 
/*      */               
/*  531 */               String artist = sel.getFlArtist();
/*  532 */               String title = sel.getTitle();
/*      */ 
/*      */               
/*  535 */               boolean parental = sel.getParentalGuidance();
/*  536 */               String explicit = "No";
/*  537 */               if (parental) {
/*  538 */                 explicit = "Yes";
/*      */               }
/*      */ 
/*      */               
/*  542 */               String releasingFamilyString = ReleasingFamily.getName(sel.getReleaseFamilyId());
/*  543 */               String label = (sel.getImprint() != null) ? sel.getImprint() : "";
/*      */ 
/*      */               
/*  546 */               boolean priorityBool = sel.getPriority();
/*  547 */               String priority = "No";
/*  548 */               if (priorityBool) {
/*  549 */                 priority = "Yes";
/*      */               }
/*      */ 
/*      */               
/*  553 */               String physicalReleaseDate = "";
/*  554 */               if (config.equals("eAlbum") && sel.getStreetDate() != null) {
/*  555 */                 physicalReleaseDate = MilestoneHelper.getFormatedDate(sel.getStreetDate());
/*      */               }
/*      */ 
/*      */               
/*  559 */               String radioImpactDate = "";
/*      */               
/*  561 */               ImpactDate earliestImpactDate = new ImpactDate();
/*  562 */               radioImpactDate = MilestoneHelper.getFormatedDate(sel.getImpactDate());
/*      */               
/*  564 */               if (radioImpactDate.equals("")) {
/*      */                 
/*  566 */                 if (sel.getImpactDates().size() != 0) {
/*  567 */                   for (int i = 0; i < sel.getImpactDates().size(); i++) {
/*  568 */                     ImpactDate iDate = (ImpactDate)sel.getImpactDates().elementAt(i);
/*  569 */                     Calendar cals = iDate.getImpactDate();
/*  570 */                     if (i == 0) {
/*  571 */                       earliestImpactDate = iDate;
/*      */                     }
/*  573 */                     if (cals.before(earliestImpactDate.getImpactDate())) {
/*  574 */                       earliestImpactDate = iDate;
/*      */                     }
/*      */                   } 
/*      */                 }
/*  578 */                 radioImpactDate = MilestoneHelper.getFormatedDate(earliestImpactDate.getImpactDate());
/*      */               } 
/*      */ 
/*      */ 
/*      */               
/*  583 */               String prefix = "";
/*  584 */               prefix = SelectionManager.getLookupObjectValue(sel.getPrefixID());
/*  585 */               if (!prefix.equals(""))
/*  586 */                 prefix = String.valueOf(prefix) + "-"; 
/*  587 */               String selectionID = "";
/*  588 */               selectionID = sel.getSelectionNo();
/*  589 */               String localProductNumber = String.valueOf(prefix) + selectionID;
/*  590 */               String upc = (sel.getUpc() != null) ? sel.getUpc() : "";
/*      */ 
/*      */               
/*  593 */               upc = MilestoneHelper_2.getRMSReportFormat(upc, "UPC", sel.getIsDigital());
/*      */ 
/*      */               
/*  596 */               String pfmComments = "";
/*  597 */               Pfm pfm = new Pfm();
/*  598 */               pfm = SelectionManager.getInstance().getPfm(
/*  599 */                   sel.getSelectionID());
/*  600 */               if (pfm != null && 
/*  601 */                 pfm.getComments() != null) {
/*  602 */                 pfmComments = pfm.getComments();
/*      */               }
/*      */ 
/*      */               
/*  606 */               String selComments2 = "";
/*  607 */               selComments2 = (sel.getSelectionComments() != null) ? sel.getSelectionComments() : "";
/*  608 */               String selectionScreenComments = selComments2.replace('\n', ' ');
/*      */ 
/*      */ 
/*      */               
/*  612 */               int subTableRows = 2;
/*      */               
/*  614 */               if (pfmComments.trim().length() > 0) {
/*  615 */                 subTableRows++;
/*      */               }
/*  617 */               if (selectionScreenComments.trim().length() > 0) {
/*  618 */                 subTableRows++;
/*      */               }
/*  620 */               int extraLines = 0;
/*      */               
/*  622 */               nextRow = 0;
/*      */               
/*  624 */               subTable = new DefaultTableLens(subTableRows, 14);
/*      */               
/*  626 */               columnHeaderTable.setColWidth(0, 70);
/*      */               
/*  628 */               subTable.setColWidth(0, 70);
/*  629 */               subTable.setColWidth(1, 150);
/*  630 */               subTable.setColWidth(2, 65);
/*  631 */               subTable.setColWidth(3, 150);
/*  632 */               subTable.setColWidth(4, 65);
/*  633 */               subTable.setColWidth(5, 65);
/*  634 */               subTable.setColWidth(6, 105);
/*  635 */               subTable.setColWidth(7, 80);
/*  636 */               subTable.setColWidth(8, 80);
/*  637 */               subTable.setColWidth(9, 80);
/*  638 */               subTable.setColWidth(10, 70);
/*  639 */               subTable.setColWidth(11, 70);
/*  640 */               subTable.setColWidth(12, 85);
/*  641 */               subTable.setColWidth(13, 70);
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  646 */               boolean extendedLabelString = false;
/*  647 */               boolean extendedTitleString = false;
/*  648 */               boolean extendedArtistString = false;
/*  649 */               if (label.length() > 17)
/*  650 */                 extendedLabelString = true; 
/*  651 */               if (title.length() > 20)
/*  652 */                 extendedTitleString = true; 
/*  653 */               if (artist.length() > 20) {
/*  654 */                 extendedArtistString = true;
/*      */               }
/*      */               
/*  657 */               if (extendedLabelString || extendedTitleString || extendedArtistString) {
/*  658 */                 subTable.setRowHeight(nextRow, 25);
/*      */               } else {
/*  660 */                 subTable.setRowHeight(nextRow, 9);
/*      */               } 
/*  662 */               subTable.setSpan(nextRow, 0, new Dimension(1, 2));
/*      */               
/*  664 */               subTable.setRowFont(nextRow, new Font("Arial", 0, 7));
/*  665 */               subTable.setRowFont(nextRow + 1, new Font("Arial", 0, 7));
/*  666 */               subTable.setRowBorderColor(nextRow - 1, Color.lightGray);
/*      */ 
/*      */ 
/*      */               
/*  670 */               subTable.setRowBorderColor(nextRow, 0, Color.white);
/*  671 */               subTable.setRowBorderColor(nextRow, 1, Color.white);
/*  672 */               subTable.setRowBorderColor(nextRow, 2, Color.white);
/*  673 */               subTable.setRowBorderColor(nextRow, 3, Color.white);
/*  674 */               subTable.setRowBorderColor(nextRow, 4, Color.white);
/*  675 */               subTable.setRowBorderColor(nextRow, 5, Color.white);
/*  676 */               subTable.setRowBorderColor(nextRow, 6, Color.white);
/*      */               
/*  678 */               subTable.setRowBorderColor(nextRow, 7, Color.lightGray);
/*  679 */               subTable.setRowBorderColor(nextRow, 8, Color.lightGray);
/*  680 */               subTable.setRowBorderColor(nextRow, 9, Color.lightGray);
/*  681 */               subTable.setRowBorderColor(nextRow, 10, Color.lightGray);
/*  682 */               subTable.setRowBorderColor(nextRow, 11, Color.lightGray);
/*  683 */               subTable.setRowBorderColor(nextRow, 12, Color.lightGray);
/*  684 */               subTable.setRowBorderColor(nextRow, 13, Color.lightGray);
/*      */               
/*  686 */               subTable.setAlignment(nextRow, 0, 2);
/*  687 */               subTable.setAlignment(nextRow, 1, 2);
/*  688 */               subTable.setAlignment(nextRow, 2, 2);
/*  689 */               subTable.setAlignment(nextRow, 3, 2);
/*  690 */               subTable.setAlignment(nextRow, 4, 2);
/*  691 */               subTable.setAlignment(nextRow, 5, 2);
/*  692 */               subTable.setAlignment(nextRow, 6, 2);
/*  693 */               subTable.setAlignment(nextRow, 7, 2);
/*  694 */               subTable.setAlignment(nextRow, 8, 2);
/*  695 */               subTable.setAlignment(nextRow, 9, 2);
/*  696 */               subTable.setAlignment(nextRow, 10, 2);
/*  697 */               subTable.setAlignment(nextRow, 11, 2);
/*  698 */               subTable.setAlignment(nextRow, 12, 2);
/*  699 */               subTable.setAlignment(nextRow, 13, 2);
/*      */               
/*  701 */               subTable.setObject(nextRow, 0, digitalReleaseDate);
/*  702 */               subTable.setObject(nextRow, 1, artist);
/*      */ 
/*      */               
/*  705 */               subTable.setFont(nextRow, 1, new Font("Arial", 1, 7));
/*      */               
/*  707 */               subTable.setObject(nextRow, 2, explicit);
/*  708 */               subTable.setObject(nextRow, 3, releasingFamilyString);
/*  709 */               subTable.setObject(nextRow, 4, priority);
/*      */               
/*  711 */               if (config.equals("eAlbum")) {
/*  712 */                 subTable.setObject(nextRow, 5, physicalReleaseDate);
/*      */               } else {
/*  714 */                 subTable.setObject(nextRow, 5, radioImpactDate);
/*      */               } 
/*  716 */               subTable.setObject(nextRow, 6, localProductNumber);
/*      */               
/*  718 */               subTable.setObject(nextRow + 1, 1, title);
/*  719 */               subTable.setFont(nextRow + 1, 1, new Font("Arial", 1, 7));
/*      */               
/*  721 */               subTable.setObject(nextRow + 1, 3, label);
/*  722 */               subTable.setObject(nextRow + 1, 6, upc);
/*      */ 
/*      */               
/*  725 */               subTable.setAlignment(nextRow + 1, 1, 2);
/*  726 */               subTable.setAlignment(nextRow + 1, 3, 2);
/*  727 */               subTable.setAlignment(nextRow + 1, 6, 2);
/*  728 */               subTable.setAlignment(nextRow + 1, 7, 2);
/*  729 */               subTable.setAlignment(nextRow + 1, 8, 2);
/*  730 */               subTable.setAlignment(nextRow + 1, 9, 2);
/*  731 */               subTable.setAlignment(nextRow + 1, 10, 2);
/*  732 */               subTable.setAlignment(nextRow + 1, 11, 2);
/*  733 */               subTable.setAlignment(nextRow + 1, 12, 2);
/*  734 */               subTable.setAlignment(nextRow + 1, 13, 2);
/*      */               
/*  736 */               subTable.setRowBorderColor(nextRow + 1, Color.lightGray);
/*      */               
/*  738 */               for (int z = -1; z <= numColumns; z++) {
/*      */                 
/*  740 */                 subTable.setColBorder(nextRow, z, 4097);
/*  741 */                 subTable.setColBorderColor(nextRow, z, Color.lightGray);
/*      */               } 
/*      */               
/*  744 */               subTable.setObject(nextRow + 1, 1, sel.getTitle());
/*      */               
/*  746 */               String NOC = "";
/*  747 */               String RMS = "";
/*  748 */               String DFM = "";
/*  749 */               String GRE = "";
/*  750 */               String DPO = "";
/*  751 */               String WAE = "";
/*  752 */               String PFM = "";
/*  753 */               String BAR = "";
/*  754 */               String PRC = "";
/*      */               
/*  756 */               String NOCcom = "";
/*  757 */               String RMScom = "";
/*  758 */               String DFMcom = "";
/*  759 */               String GREcom = "";
/*  760 */               String DPOcom = "";
/*  761 */               String WAEcom = "";
/*  762 */               String PFMcom = "";
/*  763 */               String BARcom = "";
/*  764 */               String PRCcom = "";
/*      */               
/*  766 */               String taskDueDate = "", taskCompletionDate = "", taskVendor = "";
/*  767 */               ScheduledTask task = null;
/*      */               
/*  769 */               if (tasks != null)
/*      */               {
/*  771 */                 for (int z = 0; z < tasks.size(); z++) {
/*      */                   
/*  773 */                   task = (ScheduledTask)tasks.get(z);
/*  774 */                   if (task != null) {
/*      */                     
/*  776 */                     String taskStatus = task.getScheduledTaskStatus();
/*  777 */                     SimpleDateFormat dueDateFormatter = new SimpleDateFormat("M/dd");
/*      */                     
/*  779 */                     String dueDate = "";
/*  780 */                     if (task.getDueDate() != null)
/*      */                     {
/*      */                       
/*  783 */                       dueDate = String.valueOf(dueDateFormatter.format(task.getDueDate().getTime())) + 
/*  784 */                         " " + MilestoneHelper.getDayType(sel.getCalendarGroup(), task.getDueDate());
/*      */                     }
/*      */                     
/*  787 */                     String completionDate = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*  788 */                     taskVendor = (task.getVendor() != null) ? (String.valueOf(task.getVendor()) + "\n") : "\n";
/*      */                     
/*  790 */                     if (!completionDate.equals("")) {
/*  791 */                       completionDate = String.valueOf(completionDate) + "\n";
/*      */                     }
/*  793 */                     if (taskStatus.equalsIgnoreCase("N/A")) {
/*  794 */                       completionDate = "N/A\n";
/*      */                     }
/*  796 */                     String taskAbbrev = MilestoneHelper.getTaskAbbreivationNameById(task.getTaskAbbreviationID());
/*      */                     
/*  798 */                     String taskComment = "";
/*      */                     
/*  800 */                     if (task.getComments() != null && !task.getComments().equals("")) {
/*  801 */                       taskComment = task.getComments();
/*      */                     }
/*      */ 
/*      */                     
/*  805 */                     if (taskAbbrev.equalsIgnoreCase("NOC")) {
/*      */                       
/*  807 */                       NOC = dueDate;
/*  808 */                       NOCcom = String.valueOf(completionDate) + taskVendor;
/*      */                     }
/*  810 */                     else if (taskAbbrev.equalsIgnoreCase("RMS")) {
/*      */                       
/*  812 */                       RMS = dueDate;
/*  813 */                       RMScom = String.valueOf(completionDate) + taskVendor;
/*      */                     }
/*  815 */                     else if (taskAbbrev.equalsIgnoreCase("DFM")) {
/*      */                       
/*  817 */                       DFM = dueDate;
/*  818 */                       DFMcom = String.valueOf(completionDate) + taskVendor;
/*      */                     }
/*  820 */                     else if (taskAbbrev.equalsIgnoreCase("PFM")) {
/*      */                       
/*  822 */                       PFM = dueDate;
/*  823 */                       PFMcom = String.valueOf(completionDate) + taskVendor;
/*      */                     }
/*  825 */                     else if (taskAbbrev.equalsIgnoreCase("GRE")) {
/*      */                       
/*  827 */                       GRE = dueDate;
/*  828 */                       GREcom = String.valueOf(completionDate) + taskVendor;
/*      */                     }
/*  830 */                     else if (taskAbbrev.equalsIgnoreCase("WAE")) {
/*      */                       
/*  832 */                       WAE = dueDate;
/*  833 */                       WAEcom = String.valueOf(completionDate) + taskVendor;
/*      */                     }
/*  835 */                     else if (taskAbbrev.equalsIgnoreCase("DPO")) {
/*      */                       
/*  837 */                       DPO = dueDate;
/*  838 */                       DPOcom = String.valueOf(completionDate) + taskVendor;
/*      */                     }
/*  840 */                     else if (taskAbbrev.equalsIgnoreCase("BAR")) {
/*      */                       
/*  842 */                       BAR = dueDate;
/*  843 */                       BARcom = String.valueOf(completionDate) + taskVendor;
/*      */                     }
/*  845 */                     else if (taskAbbrev.equalsIgnoreCase("PRC")) {
/*      */                       
/*  847 */                       PRC = dueDate;
/*  848 */                       PRCcom = String.valueOf(completionDate) + taskVendor;
/*      */                     } 
/*      */                     
/*  851 */                     task = null;
/*      */                   } 
/*      */                 } 
/*      */               }
/*      */ 
/*      */               
/*  857 */               subTable.setRowBorderColor(nextRow + 1, Color.lightGray);
/*      */               
/*  859 */               for (int a = -1; a <= numColumns; a++) {
/*      */                 
/*  861 */                 subTable.setColBorder(nextRow, a, 4097);
/*  862 */                 subTable.setColBorderColor(nextRow, a, Color.lightGray);
/*  863 */                 subTable.setColBorder(nextRow + 1, a, 4097);
/*  864 */                 subTable.setColBorderColor(nextRow + 1, a, Color.lightGray);
/*      */               } 
/*      */               
/*  867 */               subTable.setObject(nextRow, 7, NOC);
/*  868 */               subTable.setBackground(nextRow, 7, Color.lightGray);
/*  869 */               subTable.setObject(nextRow + 1, 7, NOCcom);
/*      */               
/*  871 */               subTable.setObject(nextRow, 8, DFM);
/*  872 */               subTable.setBackground(nextRow, 8, Color.lightGray);
/*  873 */               subTable.setObject(nextRow + 1, 8, DFMcom);
/*      */               
/*  875 */               subTable.setObject(nextRow, 9, GRE);
/*  876 */               subTable.setBackground(nextRow, 9, Color.lightGray);
/*  877 */               subTable.setObject(nextRow + 1, 9, GREcom);
/*      */               
/*  879 */               subTable.setBackground(nextRow, 10, Color.lightGray);
/*  880 */               subTable.setObject(nextRow, 10, WAE);
/*  881 */               subTable.setObject(nextRow + 1, 10, WAEcom);
/*      */               
/*  883 */               subTable.setBackground(nextRow, 11, Color.lightGray);
/*  884 */               subTable.setObject(nextRow, 11, DPO);
/*  885 */               subTable.setObject(nextRow + 1, 11, DPOcom);
/*      */               
/*  887 */               subTable.setObject(nextRow, 12, BAR);
/*  888 */               subTable.setBackground(nextRow, 12, Color.lightGray);
/*  889 */               subTable.setObject(nextRow + 1, 12, BARcom);
/*      */               
/*  891 */               subTable.setObject(nextRow, 13, PRC);
/*  892 */               subTable.setBackground(nextRow, 13, Color.lightGray);
/*  893 */               subTable.setObject(nextRow + 1, 13, PRCcom);
/*      */ 
/*      */ 
/*      */               
/*  897 */               Font holidayFont = new Font("Arial", 3, 8);
/*  898 */               Font nonHolidayFont = new Font("Arial", 1, 8);
/*  899 */               for (int colIdx = 7; colIdx <= 13; colIdx++) {
/*  900 */                 String dueDate = subTable.getObject(nextRow, colIdx).toString();
/*  901 */                 if (dueDate != null && dueDate.length() > 0) {
/*  902 */                   char lastChar = dueDate.charAt(dueDate.length() - 1);
/*  903 */                   if (Character.isLetter(lastChar)) {
/*  904 */                     subTable.setFont(nextRow, colIdx, holidayFont);
/*      */                   } else {
/*      */                     
/*  907 */                     subTable.setFont(nextRow, colIdx, nonHolidayFont);
/*      */                   } 
/*      */                 } 
/*      */               } 
/*      */ 
/*      */               
/*  913 */               if (selectionScreenComments.trim().length() > 0) {
/*  914 */                 nextRow++;
/*      */                 
/*  916 */                 subTable.setRowAutoSize(true);
/*  917 */                 subTable.setRowBorderColor(nextRow + 1, Color.lightGray);
/*  918 */                 subTable.setRowFont(nextRow + 1, new Font("Arial", 2, 7));
/*      */                 
/*  920 */                 subTable.setSpan(nextRow + 1, 0, new Dimension(14, 1));
/*  921 */                 subTable.setAlignment(nextRow + 1, 0, 9);
/*  922 */                 subTable.setObject(nextRow + 1, 0, "Selection Comments: " + selectionScreenComments);
/*      */                 
/*  924 */                 subTable.setRowBorderColor(nextRow + 1, Color.lightGray);
/*  925 */                 subTable.setColLineWrap(2, true);
/*      */                 
/*  927 */                 subTable.setColBorderColor(nextRow + 1, -1, Color.lightGray);
/*  928 */                 subTable.setColBorderColor(nextRow + 1, 1, Color.white);
/*  929 */                 subTable.setColBorderColor(nextRow + 1, 13, Color.lightGray);
/*      */               } 
/*      */               
/*  932 */               if (pfmComments.trim().length() > 0) {
/*      */ 
/*      */                 
/*  935 */                 if (selectionScreenComments.trim().length() > 0) {
/*  936 */                   subTable.setRowBorderColor(nextRow + 1, Color.white);
/*      */                 }
/*      */                 
/*  939 */                 nextRow++;
/*      */                 
/*  941 */                 subTable.setRowAutoSize(true);
/*  942 */                 subTable.setRowBorderColor(nextRow + 1, Color.lightGray);
/*  943 */                 subTable.setRowFont(nextRow + 1, new Font("Arial", 2, 7));
/*      */                 
/*  945 */                 subTable.setSpan(nextRow + 1, 0, new Dimension(14, 1));
/*  946 */                 subTable.setAlignment(nextRow + 1, 0, 9);
/*  947 */                 subTable.setObject(nextRow + 1, 0, "PFM Comments: " + pfmComments);
/*      */                 
/*  949 */                 subTable.setRowBorderColor(nextRow + 1, Color.lightGray);
/*  950 */                 subTable.setColLineWrap(2, true);
/*      */                 
/*  952 */                 subTable.setColBorderColor(nextRow + 1, -1, Color.lightGray);
/*  953 */                 subTable.setColBorderColor(nextRow + 1, 1, Color.white);
/*  954 */                 subTable.setColBorderColor(nextRow + 1, 13, Color.lightGray);
/*      */               } 
/*      */               
/*  957 */               body = new SectionBand(report);
/*      */               
/*  959 */               double lfLineCount = 1.5D;
/*      */               
/*  961 */               if (label.length() > 17 || sel.getTitle().length() > 20 || sel.getArtist().length() > 20) {
/*      */                 
/*  963 */                 body.setHeight(1.5F);
/*      */               }
/*      */               else {
/*      */                 
/*  967 */                 body.setHeight(1.0F);
/*      */               } 
/*      */ 
/*      */               
/*  971 */               if (pfmComments.length() > 0 || selectionScreenComments.length() > 0) {
/*  972 */                 lfLineCount = 2.0D;
/*      */                 
/*  974 */                 if (pfmComments.length() > 500) {
/*  975 */                   lfLineCount = 3.0D;
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
/*  989 */                 body.setHeight((float)lfLineCount);
/*      */               } 
/*      */ 
/*      */               
/*  993 */               body.addTable(subTable, new Rectangle(800, 800));
/*  994 */               body.setBottomBorder(0);
/*  995 */               body.setTopBorder(0);
/*  996 */               body.setShrinkToFit(true);
/*  997 */               body.setVisible(true);
/*      */               
/*  999 */               group = new DefaultSectionLens(null, group, body);
/*      */             } 
/*      */             
/* 1002 */             long l = System.currentTimeMillis();
/*      */           } 
/*      */         } 
/*      */         
/* 1006 */         group = new DefaultSectionLens(hbandType, group, null);
/* 1007 */         report.addSection(group, rowCountTable);
/*      */ 
/*      */         
/* 1010 */         if (!config.equals(saveConfig) || saveConfig.equals("")) {
/* 1011 */           report.addPageBreak();
/*      */         }
/*      */         
/* 1014 */         saveConfig = config;
/*      */         
/* 1016 */         group = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 1085 */     catch (Exception e) {
/*      */       
/* 1087 */       System.out.println(">>>>>>>>DigitalProductionScheduleForPrintSubHandler.fillUmeProdScheduleForPrint(): exception: " + e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void setColBorderColor(DefaultTableLens table, int row, int columns, Color color) {
/* 1097 */     for (int i = -1; i < columns; i++)
/*      */     {
/* 1099 */       table.setColBorderColor(row, i, color);
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\DigitalProductionScheduleForPrintSubHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */