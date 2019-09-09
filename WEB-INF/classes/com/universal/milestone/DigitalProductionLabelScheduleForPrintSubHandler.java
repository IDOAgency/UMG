/*      */ package WEB-INF.classes.com.universal.milestone;
/*      */ 
/*      */ import com.techempower.ComponentLog;
/*      */ import com.techempower.EnhancedProperties;
/*      */ import com.techempower.gemini.Context;
/*      */ import com.techempower.gemini.GeminiApplication;
/*      */ import com.universal.milestone.DigitalProductionLabelScheduleForPrintSubHandler;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class DigitalProductionLabelScheduleForPrintSubHandler
/*      */   extends SecureHandler
/*      */ {
/*      */   public static final String COMPONENT_CODE = "hUMEsu";
/*      */   public static ComponentLog log;
/*      */   
/*      */   public DigitalProductionLabelScheduleForPrintSubHandler(GeminiApplication application) {}
/*      */   
/*   90 */   public static void configure(EnhancedProperties props, GeminiApplication application) { log = application.getLog("hUMEsu"); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   98 */   public String getDescription() { return "Digital Production Schedule Report"; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static void fillDigitalProductionLabelScheduleUpdateForPrint(XStyleSheet report, Context context) {
/*  110 */     int COL_LINE_STYLE = 4097;
/*  111 */     int HEADER_FONT_SIZE = 12;
/*      */     
/*  113 */     double ldLineVal = 0.25D;
/*      */ 
/*      */     
/*      */     try {
/*  117 */       HttpServletResponse sresponse = context.getResponse();
/*  118 */       context.putDelivery("status", new String("start_gathering"));
/*  119 */       context.includeJSP("status.jsp", "hiddenFrame");
/*  120 */       sresponse.setContentType("text/plain");
/*  121 */       sresponse.flushBuffer();
/*      */     }
/*  123 */     catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  128 */     Vector selections = MilestoneHelper.getSelectionsForReport(context);
/*      */ 
/*      */     
/*      */     try {
/*  132 */       HttpServletResponse sresponse = context.getResponse();
/*  133 */       context.putDelivery("status", new String("start_report"));
/*  134 */       context.putDelivery("percent", new String("10"));
/*  135 */       context.includeJSP("status.jsp", "hiddenFrame");
/*  136 */       sresponse.setContentType("text/plain");
/*  137 */       sresponse.flushBuffer();
/*      */     }
/*  139 */     catch (Exception e) {
/*      */       
/*  141 */       System.out.println("exception caught in DigitalProductionScheduleForPrintSubhandler");
/*      */     } 
/*      */     
/*  144 */     Hashtable selTable = MilestoneHelper.groupSelectionsByDigitalTypeAndStreetDate(selections);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  151 */       Form reportForm = (Form)context.getSessionValue("reportForm");
/*      */       
/*  153 */       Calendar beginStDate = (reportForm.getStringValue("beginDate") != null && 
/*  154 */         reportForm.getStringValue("beginDate").length() > 0) ? 
/*  155 */         MilestoneHelper.getDate(reportForm.getStringValue("beginDate")) : null;
/*      */       
/*  157 */       Calendar endStDate = (reportForm.getStringValue("endDate") != null && 
/*  158 */         reportForm.getStringValue("endDate").length() > 0) ? 
/*  159 */         MilestoneHelper.getDate(reportForm.getStringValue("endDate")) : null;
/*      */       
/*  161 */       report.setElement("startdate", MilestoneHelper.getFormatedDate(beginStDate));
/*  162 */       report.setElement("enddate", MilestoneHelper.getFormatedDate(endStDate));
/*      */ 
/*      */       
/*  165 */       int numMonths = 0;
/*  166 */       int numConfigs = 0;
/*  167 */       int numSelections = 0;
/*  168 */       Color SHADED_AREA_COLOR = Color.lightGray;
/*  169 */       int DATA_FONT_SIZE = 7;
/*  170 */       int SMALL_HEADER_FONT_SIZE = 8;
/*      */ 
/*      */ 
/*      */       
/*  174 */       int numColumns = 14;
/*  175 */       DefaultTableLens table_contents = null;
/*  176 */       DefaultTableLens columnHeaderTable = null;
/*  177 */       DefaultTableLens rowCountTable = null;
/*  178 */       DefaultTableLens subTable = null;
/*      */       
/*  180 */       SectionBand hbandType = new SectionBand(report);
/*  181 */       SectionBand hbandMonth = new SectionBand(report);
/*  182 */       SectionBand body = new SectionBand(report);
/*  183 */       SectionBand footer = new SectionBand(report);
/*  184 */       DefaultSectionLens group = null;
/*      */       
/*  186 */       Enumeration configs = selTable.keys();
/*  187 */       Vector configVector = new Vector();
/*      */       
/*  189 */       while (configs.hasMoreElements()) {
/*  190 */         configVector.addElement(configs.nextElement());
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  195 */       Object[] sortedArray = null;
/*  196 */       sortedArray = MilestoneHelper.sortStringVector(configVector);
/*  197 */       Vector sortedConfigVector = new Vector();
/*  198 */       for (int aCount = 0; aCount < sortedArray.length; aCount++) {
/*  199 */         sortedConfigVector.addElement(sortedArray[aCount]);
/*      */       }
/*      */ 
/*      */       
/*  203 */       int nextRow = 0;
/*      */       
/*  205 */       int totalCount = 0;
/*  206 */       int tenth = 1;
/*      */       
/*  208 */       for (int a = 0; a < sortedConfigVector.size(); a++) {
/*      */         
/*  210 */         totalCount++;
/*  211 */         String configC = (String)sortedConfigVector.get(a);
/*  212 */         Hashtable monthTableC = (Hashtable)selTable.get(configC);
/*      */         
/*  214 */         if (monthTableC != null) {
/*      */           
/*  216 */           Enumeration monthsC = monthTableC.keys();
/*  217 */           Vector monthVectorC = new Vector();
/*      */           
/*  219 */           while (monthsC.hasMoreElements())
/*      */           {
/*  221 */             monthVectorC.add((String)monthsC.nextElement());
/*      */           }
/*      */           
/*  224 */           Object[] monthArrayC = null;
/*  225 */           monthArrayC = monthVectorC.toArray();
/*      */           
/*  227 */           for (int b = 0; b < monthArrayC.length; b++) {
/*      */             
/*  229 */             totalCount++;
/*  230 */             String monthNameC = (String)monthArrayC[b];
/*      */             
/*  232 */             Vector selectionsC = (Vector)monthTableC.get(monthNameC);
/*  233 */             if (selectionsC == null) {
/*  234 */               selectionsC = new Vector();
/*      */             }
/*  236 */             totalCount += selectionsC.size();
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  241 */       tenth = (totalCount > 10) ? (totalCount / 10) : 1;
/*      */       
/*  243 */       HttpServletResponse sresponse = context.getResponse();
/*  244 */       context.putDelivery("status", new String("start_report"));
/*  245 */       context.putDelivery("percent", new String("10"));
/*  246 */       context.includeJSP("status.jsp", "hiddenFrame");
/*  247 */       sresponse.setContentType("text/plain");
/*  248 */       sresponse.flushBuffer();
/*      */       
/*  250 */       int recordCount = 0;
/*  251 */       int count = 0;
/*      */ 
/*      */       
/*  254 */       String saveConfig = "";
/*      */       
/*  256 */       for (int n = 0; n < sortedConfigVector.size(); n++)
/*      */       {
/*  258 */         if (count < recordCount / tenth) {
/*      */           
/*  260 */           count = recordCount / tenth;
/*  261 */           sresponse = context.getResponse();
/*  262 */           context.putDelivery("status", new String("start_report"));
/*  263 */           context.putDelivery("percent", new String(String.valueOf(count * 10)));
/*  264 */           context.includeJSP("status.jsp", "hiddenFrame");
/*  265 */           sresponse.setContentType("text/plain");
/*  266 */           sresponse.flushBuffer();
/*      */         } 
/*      */         
/*  269 */         recordCount++;
/*      */         
/*  271 */         long start_config = System.currentTimeMillis();
/*      */         
/*  273 */         String config = (String)sortedConfigVector.get(n);
/*  274 */         Hashtable monthTable = (Hashtable)selTable.get(config);
/*      */ 
/*      */         
/*  277 */         rowCountTable = new DefaultTableLens(2, 10000);
/*  278 */         table_contents = new DefaultTableLens(1, 14);
/*      */         
/*  280 */         table_contents.setColWidth(0, 70);
/*  281 */         table_contents.setColWidth(1, 150);
/*  282 */         table_contents.setColWidth(2, 65);
/*  283 */         table_contents.setColWidth(3, 150);
/*  284 */         table_contents.setColWidth(4, 65);
/*  285 */         table_contents.setColWidth(5, 65);
/*  286 */         table_contents.setColWidth(6, 105);
/*  287 */         table_contents.setColWidth(7, 65);
/*  288 */         table_contents.setColWidth(8, 80);
/*  289 */         table_contents.setColWidth(9, 80);
/*  290 */         table_contents.setColWidth(10, 70);
/*  291 */         table_contents.setColWidth(11, 70);
/*  292 */         table_contents.setColWidth(12, 75);
/*  293 */         table_contents.setColWidth(13, 100);
/*      */ 
/*      */         
/*  296 */         nextRow = 0;
/*  297 */         hbandType = new SectionBand(report);
/*  298 */         hbandType.setHeight(1.0F);
/*  299 */         hbandType.setShrinkToFit(false);
/*  300 */         hbandType.setVisible(true);
/*      */         
/*  302 */         int cols = 14;
/*      */ 
/*      */         
/*  305 */         table_contents.setObject(nextRow, 0, "");
/*  306 */         table_contents.setSpan(nextRow, 0, new Dimension(cols, 1));
/*  307 */         table_contents.setRowHeight(nextRow, 14);
/*  308 */         table_contents.setRowBackground(nextRow, Color.white);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  316 */         table_contents.setRowBorderColor(nextRow, Color.black);
/*  317 */         table_contents.setRowBorder(nextRow, 266240);
/*  318 */         table_contents.setAlignment(nextRow, 0, 2);
/*  319 */         table_contents.setObject(nextRow, 0, config);
/*  320 */         table_contents.setRowFont(nextRow, new Font("Arial", 3, 12));
/*      */         
/*  322 */         nextRow++;
/*      */         
/*  324 */         hbandType.addTable(table_contents, new Rectangle(0, 0, 800, 25));
/*      */ 
/*      */         
/*  327 */         if (monthTable != null) {
/*      */           
/*  329 */           Enumeration months = monthTable.keys();
/*  330 */           Vector monthVector = new Vector();
/*      */           
/*  332 */           while (months.hasMoreElements())
/*      */           {
/*  334 */             monthVector.add((String)months.nextElement());
/*      */           }
/*      */           
/*  337 */           Object[] monthArray = null;
/*  338 */           monthArray = monthVector.toArray();
/*  339 */           Arrays.sort(monthArray, new MonthYearComparator());
/*      */           
/*  341 */           for (int x = 0; x < monthArray.length; x++) {
/*      */             
/*  343 */             if (count < recordCount / tenth) {
/*      */               
/*  345 */               count = recordCount / tenth;
/*  346 */               sresponse = context.getResponse();
/*  347 */               context.putDelivery("status", new String("start_report"));
/*  348 */               context.putDelivery("percent", new String(String.valueOf(count * 10)));
/*  349 */               context.includeJSP("status.jsp", "hiddenFrame");
/*  350 */               sresponse.setContentType("text/plain");
/*  351 */               sresponse.flushBuffer();
/*      */             } 
/*      */             
/*  354 */             recordCount++;
/*      */             
/*  356 */             long start_month = System.currentTimeMillis();
/*  357 */             String monthName = (String)monthArray[x];
/*  358 */             String monthNameString = monthName;
/*      */             
/*  360 */             hbandMonth = new SectionBand(report);
/*  361 */             hbandMonth.setHeight(0.25F);
/*  362 */             hbandMonth.setShrinkToFit(true);
/*  363 */             hbandMonth.setVisible(true);
/*  364 */             hbandMonth.setBottomBorder(0);
/*  365 */             hbandMonth.setLeftBorder(0);
/*  366 */             hbandMonth.setRightBorder(0);
/*  367 */             hbandMonth.setTopBorder(0);
/*      */ 
/*      */             
/*      */             try {
/*  371 */               Calendar currentDate = MilestoneHelper.getMYDate(monthName);
/*  372 */               SimpleDateFormat monthFormatter = new SimpleDateFormat("MMMMM");
/*  373 */               monthNameString = monthFormatter.format(currentDate.getTime());
/*      */             }
/*  375 */             catch (Exception e) {
/*      */               
/*  377 */               if (monthName.equals("13")) {
/*  378 */                 monthNameString = "TBS";
/*  379 */               } else if (monthName.equals("26")) {
/*  380 */                 monthNameString = "ITW";
/*      */               } else {
/*  382 */                 monthNameString = "No street date";
/*      */               } 
/*      */             } 
/*  385 */             selections = (Vector)monthTable.get(monthName);
/*  386 */             if (selections == null) {
/*  387 */               selections = new Vector();
/*      */             }
/*      */             
/*  390 */             MilestoneHelper.setSelectionSorting(selections, 5);
/*  391 */             Collections.sort(selections);
/*      */ 
/*      */             
/*  394 */             MilestoneHelper.setSelectionSorting(selections, 2);
/*  395 */             Collections.sort(selections);
/*      */ 
/*      */             
/*  398 */             MilestoneHelper.setSelectionSorting(selections, 13);
/*  399 */             Collections.sort(selections);
/*      */ 
/*      */             
/*  402 */             MilestoneHelper.setSelectionSorting(selections, 4);
/*  403 */             Collections.sort(selections);
/*      */ 
/*      */             
/*  406 */             MilestoneHelper.setSelectionSorting(selections, 22);
/*  407 */             Collections.sort(selections);
/*      */ 
/*      */             
/*  410 */             MilestoneHelper.setSelectionSorting(selections, 26);
/*  411 */             Collections.sort(selections);
/*      */             
/*  413 */             columnHeaderTable = new DefaultTableLens(1, 14);
/*      */             
/*  415 */             nextRow = 0;
/*      */             
/*  417 */             table_contents.setColWidth(0, 70);
/*      */             
/*  419 */             columnHeaderTable.setColWidth(0, 70);
/*  420 */             columnHeaderTable.setColWidth(1, 150);
/*  421 */             columnHeaderTable.setColWidth(2, 65);
/*  422 */             columnHeaderTable.setColWidth(3, 150);
/*  423 */             columnHeaderTable.setColWidth(4, 65);
/*  424 */             columnHeaderTable.setColWidth(5, 65);
/*  425 */             columnHeaderTable.setColWidth(6, 105);
/*  426 */             columnHeaderTable.setColWidth(7, 65);
/*  427 */             columnHeaderTable.setColWidth(8, 80);
/*  428 */             columnHeaderTable.setColWidth(9, 80);
/*  429 */             columnHeaderTable.setColWidth(10, 70);
/*  430 */             columnHeaderTable.setColWidth(11, 70);
/*  431 */             columnHeaderTable.setColWidth(12, 75);
/*  432 */             columnHeaderTable.setColWidth(13, 100);
/*      */ 
/*      */             
/*  435 */             columnHeaderTable.setRowAlignment(nextRow, 2);
/*  436 */             columnHeaderTable.setObject(nextRow, 0, "Digital\nRelease\nDate");
/*  437 */             columnHeaderTable.setObject(nextRow, 1, "Artist /\nTitle");
/*  438 */             columnHeaderTable.setObject(nextRow, 2, "Explicit\n(Y/N)");
/*  439 */             columnHeaderTable.setObject(nextRow, 3, "Releasing Family\nLabel");
/*  440 */             columnHeaderTable.setObject(nextRow, 4, "Priority");
/*  441 */             if (config.equals("eAlbum")) {
/*  442 */               columnHeaderTable.setObject(nextRow, 5, "Phys.\nStreet\nDate");
/*  443 */               columnHeaderTable.setObject(nextRow, 6, "Corresponding\nPhysical\nLocal Prod # /\nDigital UPC");
/*      */             } else {
/*  445 */               columnHeaderTable.setObject(nextRow, 5, "Radio\nImpact\nDate");
/*  446 */               columnHeaderTable.setObject(nextRow, 6, "Corresponding\nPhysical\nLocal Prod # /\nDigital UPC");
/*      */             } 
/*  448 */             columnHeaderTable.setObject(nextRow, 7, "Sched\nType");
/*  449 */             columnHeaderTable.setObject(nextRow, 8, "eCommerce\nNotified");
/*  450 */             columnHeaderTable.setObject(nextRow, 9, "RMS\nEntry");
/*  451 */             columnHeaderTable.setObject(nextRow, 10, "Complete\nPFM");
/*  452 */             columnHeaderTable.setObject(nextRow, 11, "Graphics\nDue");
/*  453 */             columnHeaderTable.setObject(nextRow, 12, "Audio\nDue");
/*  454 */             columnHeaderTable.setObject(nextRow, 13, "Legal\nClearance\nsent to\neCommerce");
/*      */ 
/*      */             
/*  457 */             columnHeaderTable.setRowAlignment(nextRow, 34);
/*  458 */             columnHeaderTable.setRowFont(nextRow, new Font("Arial", 1, 8));
/*  459 */             columnHeaderTable.setRowBorderColor(nextRow, Color.lightGray);
/*  460 */             columnHeaderTable.setRowBorderColor(nextRow - 1, Color.lightGray);
/*  461 */             columnHeaderTable.setColBorderColor(nextRow, -1, Color.lightGray);
/*  462 */             columnHeaderTable.setColBorderColor(nextRow, 0, Color.lightGray);
/*  463 */             columnHeaderTable.setColBorderColor(nextRow, 1, Color.lightGray);
/*  464 */             columnHeaderTable.setColBorderColor(nextRow, 2, Color.lightGray);
/*  465 */             columnHeaderTable.setColBorderColor(nextRow, 3, Color.lightGray);
/*  466 */             columnHeaderTable.setColBorderColor(nextRow, 4, Color.lightGray);
/*  467 */             columnHeaderTable.setColBorderColor(nextRow, 5, Color.lightGray);
/*  468 */             columnHeaderTable.setColBorderColor(nextRow, 6, Color.lightGray);
/*  469 */             columnHeaderTable.setColBorderColor(nextRow, 7, Color.lightGray);
/*  470 */             columnHeaderTable.setColBorderColor(nextRow, 8, Color.lightGray);
/*  471 */             columnHeaderTable.setColBorderColor(nextRow, 9, Color.lightGray);
/*  472 */             columnHeaderTable.setColBorderColor(nextRow, 10, Color.lightGray);
/*  473 */             columnHeaderTable.setColBorderColor(nextRow, 11, Color.lightGray);
/*  474 */             columnHeaderTable.setColBorderColor(nextRow, 12, Color.lightGray);
/*  475 */             columnHeaderTable.setColBorderColor(nextRow, 13, Color.lightGray);
/*      */             
/*  477 */             columnHeaderTable.setRowBorderColor(nextRow, Color.lightGray);
/*      */ 
/*      */             
/*  480 */             hbandType.addTable(columnHeaderTable, new Rectangle(0, 25, 800, 50));
/*      */             
/*  482 */             nextRow = 0;
/*      */             
/*  484 */             DefaultTableLens monthTableLens = new DefaultTableLens(1, 14);
/*      */             
/*  486 */             nextRow = 0;
/*      */ 
/*      */             
/*  489 */             monthTableLens.setObject(nextRow, 0, monthNameString);
/*  490 */             monthTableLens.setSpan(nextRow, 0, new Dimension(14, 1));
/*  491 */             monthTableLens.setRowAlignment(nextRow, 1);
/*  492 */             monthTableLens.setRowFont(nextRow, new Font("Arial", 1, 12));
/*  493 */             monthTableLens.setRowHeight(nextRow, 14);
/*  494 */             monthTableLens.setColBorderColor(nextRow, -1, Color.white);
/*  495 */             monthTableLens.setColBorderColor(nextRow, 13, Color.white);
/*  496 */             monthTableLens.setColBorderColor(nextRow, 14, Color.white);
/*  497 */             monthTableLens.setRowBorderColor(nextRow, Color.white);
/*  498 */             monthTableLens.setRowBorderColor(nextRow - 1, Color.white);
/*  499 */             monthTableLens.setRowBackground(nextRow, Color.lightGray);
/*      */             
/*  501 */             hbandMonth.addTable(monthTableLens, new Rectangle(800, 800));
/*  502 */             hbandMonth.setBottomBorder(0);
/*      */             
/*  504 */             footer.setVisible(true);
/*  505 */             footer.setHeight(0.05F);
/*  506 */             footer.setShrinkToFit(true);
/*  507 */             footer.setBottomBorder(0);
/*      */             
/*  509 */             group = new DefaultSectionLens(null, group, footer);
/*  510 */             group = new DefaultSectionLens(null, group, hbandMonth);
/*  511 */             group = new DefaultSectionLens(null, group, footer);
/*      */ 
/*      */             
/*  514 */             for (int j = 0; j < selections.size(); j++) {
/*      */ 
/*      */               
/*  517 */               if (count < recordCount / tenth) {
/*      */                 
/*  519 */                 count = recordCount / tenth;
/*  520 */                 sresponse = context.getResponse();
/*  521 */                 context.putDelivery("status", new String("start_report"));
/*  522 */                 context.putDelivery("percent", new String(String.valueOf(count * 10)));
/*  523 */                 context.includeJSP("status.jsp", "hiddenFrame");
/*  524 */                 sresponse.setContentType("text/plain");
/*  525 */                 sresponse.flushBuffer();
/*      */               } 
/*      */               
/*  528 */               recordCount++;
/*      */               
/*  530 */               long start_selection = System.currentTimeMillis();
/*      */ 
/*      */               
/*  533 */               Selection sel = (Selection)selections.elementAt(j);
/*  534 */               sel = SelectionManager.getInstance().getSelectionAndSchedule(sel.getSelectionID());
/*      */ 
/*      */               
/*  537 */               Schedule schedule = sel.getSchedule();
/*  538 */               Vector tasks = null;
/*  539 */               if (schedule != null) {
/*  540 */                 tasks = schedule.getTasks();
/*      */               }
/*      */ 
/*      */               
/*  544 */               String digitalReleaseDate = MilestoneHelper.getFormatedDate(sel.getDigitalRlsDate());
/*      */ 
/*      */               
/*  547 */               String artist = sel.getFlArtist();
/*  548 */               String title = sel.getTitle();
/*      */ 
/*      */               
/*  551 */               boolean parental = sel.getParentalGuidance();
/*  552 */               String explicit = "No";
/*  553 */               if (parental) {
/*  554 */                 explicit = "Yes";
/*      */               }
/*      */ 
/*      */               
/*  558 */               String releasingFamilyString = ReleasingFamily.getName(sel.getReleaseFamilyId());
/*  559 */               String label = (sel.getImprint() != null) ? sel.getImprint() : "";
/*      */ 
/*      */               
/*  562 */               boolean priorityBool = sel.getPriority();
/*  563 */               String priority = "No";
/*  564 */               if (priorityBool) {
/*  565 */                 priority = "Yes";
/*      */               }
/*      */ 
/*      */               
/*  569 */               String physicalReleaseDate = "";
/*  570 */               if (config.equals("eAlbum") && sel.getStreetDate() != null) {
/*  571 */                 physicalReleaseDate = MilestoneHelper.getFormatedDate(sel.getStreetDate());
/*      */               }
/*      */ 
/*      */ 
/*      */               
/*  576 */               String radioImpactDate = "";
/*      */               
/*  578 */               ImpactDate earliestImpactDate = new ImpactDate();
/*  579 */               radioImpactDate = MilestoneHelper.getFormatedDate(sel.getImpactDate());
/*      */               
/*  581 */               if (radioImpactDate.equals("")) {
/*      */                 
/*  583 */                 if (sel.getImpactDates().size() != 0) {
/*  584 */                   for (int i = 0; i < sel.getImpactDates().size(); i++) {
/*  585 */                     ImpactDate iDate = (ImpactDate)sel.getImpactDates().elementAt(i);
/*  586 */                     Calendar cals = iDate.getImpactDate();
/*  587 */                     if (i == 0) {
/*  588 */                       earliestImpactDate = iDate;
/*      */                     }
/*  590 */                     if (cals.before(earliestImpactDate.getImpactDate())) {
/*  591 */                       earliestImpactDate = iDate;
/*      */                     }
/*      */                   } 
/*      */                 }
/*  595 */                 radioImpactDate = MilestoneHelper.getFormatedDate(earliestImpactDate.getImpactDate());
/*      */               } 
/*      */ 
/*      */ 
/*      */               
/*  600 */               String prefix = "";
/*  601 */               prefix = SelectionManager.getLookupObjectValue(sel.getPrefixID());
/*  602 */               if (!prefix.equals(""))
/*  603 */                 prefix = String.valueOf(prefix) + "-"; 
/*  604 */               String selectionID = "";
/*  605 */               selectionID = sel.getSelectionNo();
/*  606 */               String localProductNumber = String.valueOf(prefix) + selectionID;
/*  607 */               String upc = (sel.getUpc() != null) ? sel.getUpc() : "";
/*      */ 
/*      */               
/*  610 */               upc = MilestoneHelper_2.getRMSReportFormat(upc, "UPC", sel.getIsDigital());
/*      */ 
/*      */               
/*  613 */               String schedTypeCode = sel.getSelectionConfig().getSelectionConfigurationAbbreviation();
/*  614 */               String schedType = "";
/*  615 */               if (schedTypeCode.equals("DO")) {
/*  616 */                 schedType = "D";
/*      */               }
/*  618 */               else if (schedTypeCode.equals("DAS")) {
/*  619 */                 schedType = "A";
/*      */               }
/*  621 */               else if (schedTypeCode.equals("DSWP")) {
/*  622 */                 schedType = "S";
/*      */               }
/*  624 */               else if (schedTypeCode.equals("DPTP")) {
/*  625 */                 schedType = "P";
/*      */               }
/*  627 */               else if (schedTypeCode.equals("PTC")) {
/*  628 */                 schedType = "PTC";
/*      */               } 
/*      */ 
/*      */ 
/*      */               
/*  633 */               String pfmComments = "";
/*  634 */               Pfm pfm = new Pfm();
/*  635 */               pfm = SelectionManager.getInstance().getPfm(sel
/*  636 */                   .getSelectionID());
/*  637 */               if (pfm != null && 
/*  638 */                 pfm.getComments() != null) {
/*  639 */                 pfmComments = pfm.getComments();
/*      */               }
/*      */ 
/*      */               
/*  643 */               String selComments2 = "";
/*  644 */               selComments2 = (sel.getSelectionComments() != null) ? sel.getSelectionComments() : "";
/*  645 */               String selectionScreenComments = selComments2.replace('\n', ' ');
/*      */ 
/*      */ 
/*      */               
/*  649 */               int subTableRows = 2;
/*      */               
/*  651 */               if (pfmComments.trim().length() > 0) {
/*  652 */                 subTableRows++;
/*      */               }
/*  654 */               if (selectionScreenComments.trim().length() > 0) {
/*  655 */                 subTableRows++;
/*      */               }
/*      */               
/*  658 */               int extraLines = 0;
/*      */               
/*  660 */               nextRow = 0;
/*      */               
/*  662 */               subTable = new DefaultTableLens(subTableRows, 14);
/*      */               
/*  664 */               columnHeaderTable.setColWidth(0, 70);
/*      */               
/*  666 */               subTable.setColWidth(0, 70);
/*  667 */               subTable.setColWidth(1, 150);
/*  668 */               subTable.setColWidth(2, 65);
/*  669 */               subTable.setColWidth(3, 150);
/*  670 */               subTable.setColWidth(4, 65);
/*  671 */               subTable.setColWidth(5, 65);
/*  672 */               subTable.setColWidth(6, 105);
/*  673 */               subTable.setColWidth(7, 65);
/*  674 */               subTable.setColWidth(8, 80);
/*  675 */               subTable.setColWidth(9, 80);
/*  676 */               subTable.setColWidth(10, 70);
/*  677 */               subTable.setColWidth(11, 70);
/*  678 */               subTable.setColWidth(12, 75);
/*  679 */               subTable.setColWidth(13, 100);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  685 */               boolean extendedLabelString = false;
/*  686 */               boolean extendedTitleString = false;
/*  687 */               boolean extendedArtistString = false;
/*  688 */               if (label.length() > 17)
/*  689 */                 extendedLabelString = true; 
/*  690 */               if (title.length() > 20)
/*  691 */                 extendedTitleString = true; 
/*  692 */               if (artist.length() > 20) {
/*  693 */                 extendedArtistString = true;
/*      */               }
/*      */               
/*  696 */               if (extendedLabelString || extendedTitleString || extendedArtistString) {
/*  697 */                 subTable.setRowHeight(nextRow, 25);
/*      */               } else {
/*  699 */                 subTable.setRowHeight(nextRow, 9);
/*      */               } 
/*  701 */               subTable.setSpan(nextRow, 0, new Dimension(1, 2));
/*      */               
/*  703 */               subTable.setRowFont(nextRow, new Font("Arial", 0, 7));
/*  704 */               subTable.setRowFont(nextRow + 1, new Font("Arial", 0, 7));
/*  705 */               subTable.setRowBorderColor(nextRow - 1, Color.lightGray);
/*      */ 
/*      */ 
/*      */               
/*  709 */               subTable.setRowBorderColor(nextRow, 0, Color.white);
/*  710 */               subTable.setRowBorderColor(nextRow, 1, Color.white);
/*  711 */               subTable.setRowBorderColor(nextRow, 2, Color.white);
/*  712 */               subTable.setRowBorderColor(nextRow, 3, Color.white);
/*  713 */               subTable.setRowBorderColor(nextRow, 4, Color.white);
/*  714 */               subTable.setRowBorderColor(nextRow, 5, Color.white);
/*  715 */               subTable.setRowBorderColor(nextRow, 6, Color.white);
/*  716 */               subTable.setRowBorderColor(nextRow, 7, Color.white);
/*      */ 
/*      */               
/*  719 */               subTable.setRowBorderColor(nextRow, 8, Color.lightGray);
/*  720 */               subTable.setRowBorderColor(nextRow, 9, Color.lightGray);
/*  721 */               subTable.setRowBorderColor(nextRow, 10, Color.lightGray);
/*  722 */               subTable.setRowBorderColor(nextRow, 11, Color.lightGray);
/*  723 */               subTable.setRowBorderColor(nextRow, 12, Color.lightGray);
/*  724 */               subTable.setRowBorderColor(nextRow, 13, Color.lightGray);
/*      */               
/*  726 */               subTable.setAlignment(nextRow, 0, 2);
/*  727 */               subTable.setAlignment(nextRow, 1, 2);
/*  728 */               subTable.setAlignment(nextRow, 2, 2);
/*  729 */               subTable.setAlignment(nextRow, 3, 2);
/*  730 */               subTable.setAlignment(nextRow, 4, 2);
/*  731 */               subTable.setAlignment(nextRow, 5, 2);
/*  732 */               subTable.setAlignment(nextRow, 6, 2);
/*  733 */               subTable.setAlignment(nextRow, 7, 2);
/*  734 */               subTable.setAlignment(nextRow, 8, 2);
/*  735 */               subTable.setAlignment(nextRow, 9, 2);
/*  736 */               subTable.setAlignment(nextRow, 10, 2);
/*  737 */               subTable.setAlignment(nextRow, 11, 2);
/*  738 */               subTable.setAlignment(nextRow, 12, 2);
/*  739 */               subTable.setAlignment(nextRow, 13, 2);
/*      */               
/*  741 */               subTable.setObject(nextRow, 0, digitalReleaseDate);
/*      */               
/*  743 */               subTable.setObject(nextRow, 1, artist);
/*  744 */               subTable.setFont(nextRow, 1, new Font("Arial", 1, 7));
/*      */               
/*  746 */               subTable.setObject(nextRow, 2, explicit);
/*  747 */               subTable.setObject(nextRow, 3, releasingFamilyString);
/*  748 */               subTable.setObject(nextRow, 4, priority);
/*      */               
/*  750 */               if (config.equals("eAlbum")) {
/*  751 */                 subTable.setObject(nextRow, 5, physicalReleaseDate);
/*      */               } else {
/*  753 */                 subTable.setObject(nextRow, 5, radioImpactDate);
/*      */               } 
/*  755 */               subTable.setObject(nextRow, 6, localProductNumber);
/*  756 */               subTable.setObject(nextRow, 7, schedType);
/*      */               
/*  758 */               subTable.setObject(nextRow + 1, 1, title);
/*  759 */               subTable.setObject(nextRow + 1, 3, label);
/*  760 */               subTable.setObject(nextRow + 1, 6, upc);
/*      */ 
/*      */               
/*  763 */               subTable.setAlignment(nextRow + 1, 1, 2);
/*  764 */               subTable.setAlignment(nextRow + 1, 3, 2);
/*  765 */               subTable.setAlignment(nextRow + 1, 6, 2);
/*  766 */               subTable.setAlignment(nextRow + 1, 7, 2);
/*  767 */               subTable.setAlignment(nextRow + 1, 8, 2);
/*  768 */               subTable.setAlignment(nextRow + 1, 9, 2);
/*  769 */               subTable.setAlignment(nextRow + 1, 10, 2);
/*  770 */               subTable.setAlignment(nextRow + 1, 11, 2);
/*  771 */               subTable.setAlignment(nextRow + 1, 12, 2);
/*  772 */               subTable.setAlignment(nextRow + 1, 13, 2);
/*      */               
/*  774 */               subTable.setRowBorderColor(nextRow + 1, Color.lightGray);
/*      */               
/*  776 */               for (int z = -1; z <= numColumns; z++) {
/*      */                 
/*  778 */                 subTable.setColBorder(nextRow, z, 4097);
/*  779 */                 subTable.setColBorderColor(nextRow, z, Color.lightGray);
/*      */               } 
/*      */               
/*  782 */               subTable.setObject(nextRow + 1, 1, sel.getTitle());
/*  783 */               subTable.setFont(nextRow + 1, 1, new Font("Arial", 1, 7));
/*      */               
/*  785 */               String NOT = "";
/*  786 */               String RMS = "";
/*  787 */               String PFM = "";
/*  788 */               String GRA = "";
/*  789 */               String WAV = "";
/*  790 */               String BAS = "";
/*      */               
/*  792 */               String NOTcom = "";
/*  793 */               String RMScom = "";
/*  794 */               String PFMcom = "";
/*  795 */               String GRAcom = "";
/*  796 */               String WAVcom = "";
/*  797 */               String BAScom = "";
/*      */               
/*  799 */               String taskDueDate = "", taskCompletionDate = "", taskVendor = "";
/*  800 */               ScheduledTask task = null;
/*      */               
/*  802 */               if (tasks != null)
/*      */               {
/*  804 */                 for (int z = 0; z < tasks.size(); z++) {
/*      */                   
/*  806 */                   task = (ScheduledTask)tasks.get(z);
/*  807 */                   if (task != null) {
/*      */                     
/*  809 */                     String taskStatus = task.getScheduledTaskStatus();
/*  810 */                     SimpleDateFormat dueDateFormatter = new SimpleDateFormat("M/dd");
/*      */                     
/*  812 */                     String dueDate = "";
/*  813 */                     if (task.getDueDate() != null)
/*      */                     {
/*      */                       
/*  816 */                       dueDate = String.valueOf(dueDateFormatter.format(task.getDueDate().getTime())) + 
/*  817 */                         " " + MilestoneHelper.getDayType(sel.getCalendarGroup(), task.getDueDate());
/*      */                     }
/*      */                     
/*  820 */                     String completionDate = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*      */                     
/*  822 */                     if (completionDate.equals("9/9/99") && task.getScheduledTaskStatus().equalsIgnoreCase("Done")) {
/*  823 */                       completionDate = "Done";
/*      */                     }
/*      */                     
/*  826 */                     taskVendor = (task.getVendor() != null) ? (String.valueOf(task.getVendor()) + "\n") : "\n";
/*      */                     
/*  828 */                     if (!completionDate.equals("")) {
/*  829 */                       completionDate = String.valueOf(completionDate) + "\n";
/*      */                     }
/*  831 */                     if (taskStatus.equalsIgnoreCase("N/A")) {
/*  832 */                       completionDate = "N/A\n";
/*      */                     }
/*  834 */                     String taskAbbrev = MilestoneHelper.getTaskAbbreivationNameById(task.getTaskAbbreviationID());
/*      */                     
/*  836 */                     String taskComment = "";
/*      */                     
/*  838 */                     if (task.getComments() != null && !task.getComments().equals("")) {
/*  839 */                       taskComment = task.getComments();
/*      */                     }
/*      */ 
/*      */                     
/*  843 */                     if (taskAbbrev.equalsIgnoreCase("NOT")) {
/*      */                       
/*  845 */                       NOT = dueDate;
/*  846 */                       NOTcom = String.valueOf(completionDate) + taskVendor;
/*      */                     }
/*  848 */                     else if (taskAbbrev.equalsIgnoreCase("RMS")) {
/*      */                       
/*  850 */                       RMS = dueDate;
/*  851 */                       RMScom = String.valueOf(completionDate) + taskVendor;
/*      */                     }
/*  853 */                     else if (taskAbbrev.equalsIgnoreCase("PFM")) {
/*      */                       
/*  855 */                       PFM = dueDate;
/*  856 */                       PFMcom = String.valueOf(completionDate) + taskVendor;
/*      */                     }
/*  858 */                     else if (taskAbbrev.equalsIgnoreCase("GRA")) {
/*      */                       
/*  860 */                       GRA = dueDate;
/*  861 */                       GRAcom = String.valueOf(completionDate) + taskVendor;
/*      */                     }
/*  863 */                     else if (taskAbbrev.equalsIgnoreCase("WAV")) {
/*      */                       
/*  865 */                       WAV = dueDate;
/*  866 */                       WAVcom = String.valueOf(completionDate) + taskVendor;
/*      */                     }
/*  868 */                     else if (taskAbbrev.equalsIgnoreCase("BAS")) {
/*      */                       
/*  870 */                       BAS = dueDate;
/*  871 */                       BAScom = String.valueOf(completionDate) + taskVendor;
/*      */                     } 
/*      */                     
/*  874 */                     task = null;
/*      */                   } 
/*      */                 } 
/*      */               }
/*      */ 
/*      */               
/*  880 */               subTable.setRowBorderColor(nextRow + 1, Color.lightGray);
/*      */               
/*  882 */               for (int a = -1; a <= numColumns; a++) {
/*      */                 
/*  884 */                 subTable.setColBorder(nextRow, a, 4097);
/*  885 */                 subTable.setColBorderColor(nextRow, a, Color.lightGray);
/*  886 */                 subTable.setColBorder(nextRow + 1, a, 4097);
/*  887 */                 subTable.setColBorderColor(nextRow + 1, a, Color.lightGray);
/*      */               } 
/*      */               
/*  890 */               subTable.setObject(nextRow, 8, NOT);
/*  891 */               subTable.setBackground(nextRow, 8, Color.lightGray);
/*  892 */               subTable.setObject(nextRow + 1, 8, NOTcom);
/*      */               
/*  894 */               subTable.setObject(nextRow, 9, RMS);
/*  895 */               subTable.setBackground(nextRow, 9, Color.lightGray);
/*  896 */               subTable.setObject(nextRow + 1, 9, RMScom);
/*      */               
/*  898 */               subTable.setObject(nextRow, 10, PFM);
/*  899 */               subTable.setBackground(nextRow, 10, Color.lightGray);
/*  900 */               subTable.setObject(nextRow + 1, 10, PFMcom);
/*      */               
/*  902 */               subTable.setBackground(nextRow, 11, Color.lightGray);
/*  903 */               subTable.setObject(nextRow, 11, GRA);
/*  904 */               subTable.setObject(nextRow + 1, 11, GRAcom);
/*      */               
/*  906 */               subTable.setBackground(nextRow, 12, Color.lightGray);
/*  907 */               subTable.setObject(nextRow, 12, WAV);
/*  908 */               subTable.setObject(nextRow + 1, 12, WAVcom);
/*      */               
/*  910 */               subTable.setObject(nextRow, 13, BAS);
/*  911 */               subTable.setBackground(nextRow, 13, Color.lightGray);
/*  912 */               subTable.setObject(nextRow + 1, 13, BAScom);
/*      */ 
/*      */ 
/*      */               
/*  916 */               Font holidayFont = new Font("Arial", 3, 8);
/*  917 */               Font nonHolidayFont = new Font("Arial", 1, 8);
/*  918 */               for (int colIdx = 8; colIdx <= 13; colIdx++) {
/*  919 */                 String dueDate = subTable.getObject(nextRow, colIdx).toString();
/*  920 */                 if (dueDate != null && dueDate.length() > 0) {
/*  921 */                   char lastChar = dueDate.charAt(dueDate.length() - 1);
/*  922 */                   if (Character.isLetter(lastChar)) {
/*  923 */                     subTable.setFont(nextRow, colIdx, holidayFont);
/*      */                   } else {
/*      */                     
/*  926 */                     subTable.setFont(nextRow, colIdx, nonHolidayFont);
/*      */                   } 
/*      */                 } 
/*      */               } 
/*      */ 
/*      */               
/*  932 */               if (selectionScreenComments.trim().length() > 0) {
/*  933 */                 nextRow++;
/*      */                 
/*  935 */                 subTable.setRowAutoSize(true);
/*  936 */                 subTable.setRowBorderColor(nextRow + 1, Color.lightGray);
/*  937 */                 subTable.setRowFont(nextRow + 1, new Font("Arial", 2, 7));
/*      */                 
/*  939 */                 subTable.setSpan(nextRow + 1, 0, new Dimension(14, 1));
/*  940 */                 subTable.setAlignment(nextRow + 1, 0, 9);
/*  941 */                 subTable.setObject(nextRow + 1, 0, "Selection Comments: " + selectionScreenComments);
/*      */                 
/*  943 */                 subTable.setRowBorderColor(nextRow + 1, Color.lightGray);
/*  944 */                 subTable.setColLineWrap(2, true);
/*      */                 
/*  946 */                 subTable.setColBorderColor(nextRow + 1, -1, Color.lightGray);
/*  947 */                 subTable.setColBorderColor(nextRow + 1, 1, Color.white);
/*  948 */                 subTable.setColBorderColor(nextRow + 1, 13, Color.lightGray);
/*      */               } 
/*      */               
/*  951 */               if (pfmComments.trim().length() > 0) {
/*      */ 
/*      */                 
/*  954 */                 if (selectionScreenComments.trim().length() > 0) {
/*  955 */                   subTable.setRowBorderColor(nextRow + 1, Color.white);
/*      */                 }
/*      */                 
/*  958 */                 nextRow++;
/*      */                 
/*  960 */                 subTable.setRowAutoSize(true);
/*  961 */                 subTable.setRowBorderColor(nextRow + 1, Color.lightGray);
/*  962 */                 subTable.setRowFont(nextRow + 1, new Font("Arial", 2, 7));
/*      */                 
/*  964 */                 subTable.setSpan(nextRow + 1, 0, new Dimension(14, 1));
/*  965 */                 subTable.setAlignment(nextRow + 1, 0, 9);
/*  966 */                 subTable.setObject(nextRow + 1, 0, "PFM Comments: " + pfmComments);
/*      */                 
/*  968 */                 subTable.setRowBorderColor(nextRow + 1, Color.lightGray);
/*  969 */                 subTable.setColLineWrap(2, true);
/*      */                 
/*  971 */                 subTable.setColBorderColor(nextRow + 1, -1, Color.lightGray);
/*  972 */                 subTable.setColBorderColor(nextRow + 1, 1, Color.white);
/*  973 */                 subTable.setColBorderColor(nextRow + 1, 13, Color.lightGray);
/*      */               } 
/*      */               
/*  976 */               nextRow += 2;
/*      */               
/*  978 */               body = new SectionBand(report);
/*      */ 
/*      */               
/*  981 */               double lfLineCount = 1.5D;
/*      */               
/*  983 */               if (label.length() > 17 || sel.getTitle().length() > 20 || sel.getArtist().length() > 20) {
/*      */                 
/*  985 */                 body.setHeight(1.5F);
/*      */               }
/*      */               else {
/*      */                 
/*  989 */                 body.setHeight(1.0F);
/*      */               } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1065 */               if (pfmComments.length() > 0 || selectionScreenComments.length() > 0) {
/*      */                 
/* 1067 */                 lfLineCount = 2.0D;
/*      */                 
/* 1069 */                 if (pfmComments.length() > 500) {
/* 1070 */                   lfLineCount = 3.0D;
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
/* 1084 */                 body.setHeight((float)lfLineCount);
/*      */               } 
/*      */               
/* 1087 */               body.addTable(subTable, new Rectangle(800, 800));
/* 1088 */               body.setBottomBorder(0);
/* 1089 */               body.setTopBorder(0);
/* 1090 */               body.setShrinkToFit(true);
/* 1091 */               body.setVisible(true);
/*      */               
/* 1093 */               group = new DefaultSectionLens(null, group, body);
/*      */             } 
/*      */             
/* 1096 */             long l = System.currentTimeMillis();
/*      */           } 
/*      */         } 
/*      */         
/* 1100 */         group = new DefaultSectionLens(hbandType, group, null);
/*      */         
/* 1102 */         report.addSection(group, rowCountTable);
/*      */ 
/*      */         
/* 1105 */         if (!config.equals(saveConfig) || saveConfig.equals("")) {
/* 1106 */           report.addPageBreak();
/*      */         }
/*      */         
/* 1109 */         saveConfig = config;
/*      */         
/* 1111 */         group = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*      */     
/*      */     }
/* 1182 */     catch (Exception e) {
/*      */       
/* 1184 */       System.out.println(">>>>>>>>DigitalProductionScheduleForPrintSubHandler.fillUmeProdScheduleForPrint(): exception: " + e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void setColBorderColor(DefaultTableLens table, int row, int columns, Color color) {
/* 1194 */     for (int i = -1; i < columns; i++)
/*      */     {
/* 1196 */       table.setColBorderColor(row, i, color);
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\DigitalProductionLabelScheduleForPrintSubHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */