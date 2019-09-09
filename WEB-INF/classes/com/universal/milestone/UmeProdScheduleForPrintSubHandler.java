/*      */ package WEB-INF.classes.com.universal.milestone;
/*      */ 
/*      */ import com.techempower.ComponentLog;
/*      */ import com.techempower.EnhancedProperties;
/*      */ import com.techempower.gemini.Context;
/*      */ import com.techempower.gemini.GeminiApplication;
/*      */ import com.universal.milestone.Form;
/*      */ import com.universal.milestone.MilestoneHelper;
/*      */ import com.universal.milestone.MilestoneHelper_2;
/*      */ import com.universal.milestone.MonthYearComparator;
/*      */ import com.universal.milestone.ScheduledTask;
/*      */ import com.universal.milestone.SecureHandler;
/*      */ import com.universal.milestone.Selection;
/*      */ import com.universal.milestone.SelectionManager;
/*      */ import com.universal.milestone.UmeProdScheduleForPrintSubHandler;
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
/*      */ public class UmeProdScheduleForPrintSubHandler
/*      */   extends SecureHandler
/*      */ {
/*      */   public static final String COMPONENT_CODE = "hUMEsu";
/*      */   public static ComponentLog log;
/*      */   
/*      */   public UmeProdScheduleForPrintSubHandler(GeminiApplication application) {}
/*      */   
/*   88 */   public static void configure(EnhancedProperties props, GeminiApplication application) { log = application.getLog("hUMEsu"); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   96 */   public String getDescription() { return "Sub Report"; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static void fillUmeProdScheduleForPrint(XStyleSheet report, Context context) {
/*  108 */     int COL_LINE_STYLE = 4097;
/*  109 */     int HEADER_FONT_SIZE = 12;
/*      */     
/*  111 */     double ldLineVal = 0.25D;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  126 */       HttpServletResponse sresponse = context.getResponse();
/*  127 */       context.putDelivery("status", new String("start_gathering"));
/*  128 */       context.includeJSP("status.jsp", "hiddenFrame");
/*  129 */       sresponse.setContentType("text/plain");
/*  130 */       sresponse.flushBuffer();
/*      */     }
/*  132 */     catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  138 */     Vector selections = MilestoneHelper.getSelectionsForReport(context);
/*      */ 
/*      */     
/*      */     try {
/*  142 */       HttpServletResponse sresponse = context.getResponse();
/*  143 */       context.putDelivery("status", new String("start_report"));
/*  144 */       context.putDelivery("percent", new String("10"));
/*  145 */       context.includeJSP("status.jsp", "hiddenFrame");
/*  146 */       sresponse.setContentType("text/plain");
/*  147 */       sresponse.flushBuffer();
/*      */     }
/*  149 */     catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  159 */     Hashtable selTable = MilestoneHelper.groupSelectionsByTypeAndStreetDate(selections);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  172 */       Form reportForm = (Form)context.getSessionValue("reportForm");
/*      */       
/*  174 */       Calendar beginStDate = (reportForm.getStringValue("beginDate") != null && 
/*  175 */         reportForm.getStringValue("beginDate").length() > 0) ? 
/*  176 */         MilestoneHelper.getDate(reportForm.getStringValue("beginDate")) : null;
/*      */       
/*  178 */       Calendar endStDate = (reportForm.getStringValue("endDate") != null && 
/*  179 */         reportForm.getStringValue("endDate").length() > 0) ? 
/*  180 */         MilestoneHelper.getDate(reportForm.getStringValue("endDate")) : null;
/*      */       
/*  182 */       report.setElement("startdate", MilestoneHelper.getFormatedDate(beginStDate));
/*  183 */       report.setElement("enddate", MilestoneHelper.getFormatedDate(endStDate));
/*      */       
/*  185 */       SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
/*  186 */       String todayLong = formatter.format(new Date());
/*  187 */       report.setElement("bottomdate", todayLong);
/*      */ 
/*      */       
/*  190 */       int numMonths = 0;
/*  191 */       int numConfigs = 0;
/*  192 */       int numSelections = 0;
/*  193 */       Color SHADED_AREA_COLOR = Color.lightGray;
/*  194 */       int DATA_FONT_SIZE = 7;
/*  195 */       int SMALL_HEADER_FONT_SIZE = 8;
/*      */ 
/*      */ 
/*      */       
/*  199 */       int numColumns = 19;
/*  200 */       DefaultTableLens table_contents = null;
/*  201 */       DefaultTableLens columnHeaderTable = null;
/*  202 */       DefaultTableLens rowCountTable = null;
/*  203 */       DefaultTableLens subTable = null;
/*      */       
/*  205 */       SectionBand hbandType = new SectionBand(report);
/*  206 */       SectionBand hbandMonth = new SectionBand(report);
/*  207 */       SectionBand body = new SectionBand(report);
/*  208 */       SectionBand footer = new SectionBand(report);
/*  209 */       DefaultSectionLens group = null;
/*      */       
/*  211 */       Enumeration configs = selTable.keys();
/*  212 */       Vector configVector = new Vector();
/*      */       
/*  214 */       while (configs.hasMoreElements()) {
/*  215 */         configVector.addElement(configs.nextElement());
/*      */       }
/*  217 */       Collections.sort(configVector);
/*  218 */       Vector sortedConfigVector = MilestoneHelper.sortStrings(configVector);
/*      */ 
/*      */       
/*  221 */       int nextRow = 0;
/*      */       
/*  223 */       int totalCount = 0;
/*  224 */       int tenth = 1;
/*      */       
/*  226 */       for (int a = 0; a < sortedConfigVector.size(); a++) {
/*      */         
/*  228 */         totalCount++;
/*  229 */         String configC = (String)sortedConfigVector.get(a);
/*  230 */         Hashtable monthTableC = (Hashtable)selTable.get(configC);
/*      */         
/*  232 */         if (monthTableC != null) {
/*      */           
/*  234 */           Enumeration monthsC = monthTableC.keys();
/*  235 */           Vector monthVectorC = new Vector();
/*      */           
/*  237 */           while (monthsC.hasMoreElements())
/*      */           {
/*  239 */             monthVectorC.add((String)monthsC.nextElement());
/*      */           }
/*      */           
/*  242 */           Object[] monthArrayC = null;
/*  243 */           monthArrayC = monthVectorC.toArray();
/*      */           
/*  245 */           for (int b = 0; b < monthArrayC.length; b++) {
/*      */             
/*  247 */             totalCount++;
/*  248 */             String monthNameC = (String)monthArrayC[b];
/*      */             
/*  250 */             Vector selectionsC = (Vector)monthTableC.get(monthNameC);
/*  251 */             if (selectionsC == null) {
/*  252 */               selectionsC = new Vector();
/*      */             }
/*  254 */             totalCount += selectionsC.size();
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  259 */       tenth = (totalCount > 10) ? (totalCount / 10) : 1;
/*      */       
/*  261 */       HttpServletResponse sresponse = context.getResponse();
/*  262 */       context.putDelivery("status", new String("start_report"));
/*  263 */       context.putDelivery("percent", new String("10"));
/*  264 */       context.includeJSP("status.jsp", "hiddenFrame");
/*  265 */       sresponse.setContentType("text/plain");
/*  266 */       sresponse.flushBuffer();
/*      */       
/*  268 */       int recordCount = 0;
/*  269 */       int count = 0;
/*      */       
/*  271 */       for (int n = 0; n < sortedConfigVector.size(); n++)
/*      */       {
/*  273 */         if (count < recordCount / tenth) {
/*      */           
/*  275 */           count = recordCount / tenth;
/*  276 */           sresponse = context.getResponse();
/*  277 */           context.putDelivery("status", new String("start_report"));
/*  278 */           context.putDelivery("percent", new String(String.valueOf(count * 10)));
/*  279 */           context.includeJSP("status.jsp", "hiddenFrame");
/*  280 */           sresponse.setContentType("text/plain");
/*  281 */           sresponse.flushBuffer();
/*      */         } 
/*      */         
/*  284 */         recordCount++;
/*      */         
/*  286 */         long start_config = System.currentTimeMillis();
/*      */         
/*  288 */         String config = (String)sortedConfigVector.get(n);
/*  289 */         Hashtable monthTable = (Hashtable)selTable.get(config);
/*      */ 
/*      */         
/*  292 */         rowCountTable = new DefaultTableLens(2, 10000);
/*  293 */         table_contents = new DefaultTableLens(1, 19);
/*      */         
/*  295 */         table_contents.setColWidth(0, 110);
/*  296 */         table_contents.setColWidth(1, 70);
/*  297 */         table_contents.setColWidth(2, 70);
/*  298 */         table_contents.setColWidth(3, 70);
/*  299 */         table_contents.setColWidth(4, 150);
/*  300 */         table_contents.setColWidth(5, 65);
/*  301 */         table_contents.setColWidth(6, 60);
/*  302 */         table_contents.setColWidth(7, 80);
/*  303 */         table_contents.setColWidth(8, 85);
/*  304 */         table_contents.setColWidth(9, 85);
/*      */         
/*  306 */         table_contents.setColWidth(10, 70);
/*  307 */         table_contents.setColWidth(11, 65);
/*  308 */         table_contents.setColWidth(12, 70);
/*  309 */         table_contents.setColWidth(13, 70);
/*  310 */         table_contents.setColWidth(14, 80);
/*  311 */         table_contents.setColWidth(15, 70);
/*  312 */         table_contents.setColWidth(16, 65);
/*  313 */         table_contents.setColWidth(17, 70);
/*  314 */         table_contents.setColWidth(18, 75);
/*      */ 
/*      */ 
/*      */         
/*  318 */         nextRow = 0;
/*  319 */         hbandType = new SectionBand(report);
/*  320 */         hbandType.setHeight(0.9F);
/*  321 */         hbandType.setShrinkToFit(false);
/*  322 */         hbandType.setVisible(true);
/*      */         
/*  324 */         int cols = 19;
/*      */ 
/*      */         
/*  327 */         table_contents.setObject(nextRow, 0, "");
/*  328 */         table_contents.setSpan(nextRow, 0, new Dimension(cols, 1));
/*  329 */         table_contents.setRowHeight(nextRow, 15);
/*  330 */         table_contents.setRowBackground(nextRow, Color.white);
/*      */         
/*  332 */         for (int col = -1; col < cols; col++) {
/*      */           
/*  334 */           table_contents.setColBorderColor(nextRow, col, Color.black);
/*  335 */           table_contents.setColBorder(nextRow, col, 4097);
/*      */         } 
/*      */         
/*  338 */         table_contents.setRowBorderColor(nextRow, Color.black);
/*  339 */         table_contents.setRowBorder(nextRow, 266240);
/*      */         
/*  341 */         table_contents.setAlignment(nextRow, 0, 2);
/*  342 */         table_contents.setObject(nextRow, 0, config);
/*  343 */         table_contents.setRowFont(nextRow, new Font("Arial", 3, 12));
/*      */         
/*  345 */         nextRow++;
/*  346 */         hbandType.addTable(table_contents, new Rectangle(0, 0, 800, 25));
/*      */ 
/*      */ 
/*      */         
/*  350 */         if (monthTable != null) {
/*      */           
/*  352 */           Enumeration months = monthTable.keys();
/*      */           
/*  354 */           Vector monthVector = new Vector();
/*      */           
/*  356 */           while (months.hasMoreElements())
/*      */           {
/*  358 */             monthVector.add((String)months.nextElement());
/*      */           }
/*      */           
/*  361 */           Object[] monthArray = null;
/*  362 */           monthArray = monthVector.toArray();
/*  363 */           Arrays.sort(monthArray, new MonthYearComparator());
/*      */           
/*  365 */           for (int x = 0; x < monthArray.length; x++) {
/*      */             
/*  367 */             if (count < recordCount / tenth) {
/*      */               
/*  369 */               count = recordCount / tenth;
/*  370 */               sresponse = context.getResponse();
/*  371 */               context.putDelivery("status", new String("start_report"));
/*  372 */               context.putDelivery("percent", new String(String.valueOf(count * 10)));
/*  373 */               context.includeJSP("status.jsp", "hiddenFrame");
/*  374 */               sresponse.setContentType("text/plain");
/*  375 */               sresponse.flushBuffer();
/*      */             } 
/*      */             
/*  378 */             recordCount++;
/*      */             
/*  380 */             long start_month = System.currentTimeMillis();
/*      */             
/*  382 */             String monthName = (String)monthArray[x];
/*  383 */             String monthNameString = monthName;
/*      */             
/*  385 */             hbandMonth = new SectionBand(report);
/*  386 */             hbandMonth.setHeight(0.25F);
/*  387 */             hbandMonth.setShrinkToFit(true);
/*  388 */             hbandMonth.setVisible(true);
/*  389 */             hbandMonth.setBottomBorder(0);
/*  390 */             hbandMonth.setLeftBorder(0);
/*  391 */             hbandMonth.setRightBorder(0);
/*  392 */             hbandMonth.setTopBorder(0);
/*      */ 
/*      */             
/*      */             try {
/*  396 */               Calendar currentDate = MilestoneHelper.getMYDate(monthName);
/*  397 */               SimpleDateFormat monthFormatter = new SimpleDateFormat("MMMMM");
/*  398 */               monthNameString = monthFormatter.format(currentDate.getTime());
/*      */             }
/*  400 */             catch (Exception e) {
/*      */               
/*  402 */               if (monthName.equals("13")) {
/*  403 */                 monthNameString = "TBS";
/*  404 */               } else if (monthName.equals("26")) {
/*  405 */                 monthNameString = "ITW";
/*      */               } else {
/*  407 */                 monthNameString = "No street date";
/*      */               } 
/*      */             } 
/*  410 */             selections = (Vector)monthTable.get(monthName);
/*  411 */             if (selections == null) {
/*  412 */               selections = new Vector();
/*      */             }
/*      */             
/*  415 */             MilestoneHelper.setSelectionSorting(selections, 5);
/*  416 */             Collections.sort(selections);
/*      */ 
/*      */             
/*  419 */             MilestoneHelper.setSelectionSorting(selections, 2);
/*  420 */             Collections.sort(selections);
/*      */ 
/*      */             
/*  423 */             MilestoneHelper.setSelectionSorting(selections, 13);
/*  424 */             Collections.sort(selections);
/*      */ 
/*      */             
/*  427 */             MilestoneHelper.setSelectionSorting(selections, 4);
/*  428 */             Collections.sort(selections);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  435 */             MilestoneHelper.setSelectionSorting(selections, 22);
/*  436 */             Collections.sort(selections);
/*      */ 
/*      */             
/*  439 */             MilestoneHelper.setSelectionSorting(selections, 1);
/*  440 */             Collections.sort(selections);
/*      */             
/*  442 */             columnHeaderTable = new DefaultTableLens(1, 19);
/*      */             
/*  444 */             nextRow = 0;
/*      */             
/*  446 */             columnHeaderTable.setColWidth(0, 110);
/*  447 */             columnHeaderTable.setColWidth(1, 70);
/*  448 */             columnHeaderTable.setColWidth(2, 70);
/*  449 */             columnHeaderTable.setColWidth(3, 70);
/*  450 */             columnHeaderTable.setColWidth(4, 150);
/*      */             
/*  452 */             columnHeaderTable.setColWidth(5, 65);
/*  453 */             columnHeaderTable.setColWidth(6, 60);
/*  454 */             columnHeaderTable.setColWidth(7, 80);
/*  455 */             columnHeaderTable.setColWidth(8, 85);
/*  456 */             columnHeaderTable.setColWidth(9, 85);
/*      */             
/*  458 */             columnHeaderTable.setColWidth(10, 70);
/*  459 */             columnHeaderTable.setColWidth(11, 65);
/*  460 */             columnHeaderTable.setColWidth(12, 70);
/*  461 */             columnHeaderTable.setColWidth(13, 70);
/*  462 */             columnHeaderTable.setColWidth(14, 80);
/*  463 */             columnHeaderTable.setColWidth(15, 70);
/*  464 */             columnHeaderTable.setColWidth(16, 65);
/*  465 */             columnHeaderTable.setColWidth(17, 70);
/*  466 */             columnHeaderTable.setColWidth(18, 75);
/*      */ 
/*      */             
/*  469 */             columnHeaderTable.setRowAlignment(nextRow, 2);
/*  470 */             columnHeaderTable.setObject(nextRow, 0, "Rls Date\nWeek\nProj No.");
/*      */             
/*  472 */             columnHeaderTable.setObject(nextRow, 1, "\nPrice\nArtist/Title");
/*  473 */             columnHeaderTable.setSpan(nextRow, 1, new Dimension(3, 1));
/*      */             
/*  475 */             columnHeaderTable.setObject(nextRow, 4, "Label\nProduct #\nUPC");
/*  476 */             columnHeaderTable.setObject(nextRow, 5, "Start\nMemo\nRec'd");
/*  477 */             columnHeaderTable.setObject(nextRow, 6, "\nP&L\nAprv'd");
/*      */             
/*  479 */             columnHeaderTable.setObject(nextRow, 7, "\nLegal\nClear.");
/*  480 */             columnHeaderTable.setObject(nextRow, 8, "Labelc./\nCredits\nto Prod.");
/*  481 */             columnHeaderTable.setObject(nextRow, 9, "Final\nPckg. Cpy.\nto Design");
/*      */             
/*  483 */             columnHeaderTable.setObject(nextRow, 10, "\nMech.\nRouting");
/*  484 */             columnHeaderTable.setObject(nextRow, 11, "\nManf.\nQnty");
/*  485 */             columnHeaderTable.setObject(nextRow, 12, "\nSolic.\nFilm");
/*      */             
/*  487 */             columnHeaderTable.setObject(nextRow, 13, "\n\nSeps");
/*  488 */             columnHeaderTable.setObject(nextRow, 14, "Mastering\ncomplt'd/\nPts. Ord.");
/*  489 */             columnHeaderTable.setObject(nextRow, 15, "Film\nto\nPrinter");
/*  490 */             columnHeaderTable.setObject(nextRow, 16, "Master\nrecvd/\nplant");
/*  491 */             columnHeaderTable.setObject(nextRow, 17, "Print\nat\nPlant");
/*  492 */             columnHeaderTable.setObject(nextRow, 18, "\nPlant\nShip");
/*      */ 
/*      */ 
/*      */             
/*  496 */             columnHeaderTable.setRowFont(nextRow, new Font("Arial", 1, 8));
/*  497 */             columnHeaderTable.setRowBorderColor(nextRow, Color.lightGray);
/*  498 */             columnHeaderTable.setRowBorderColor(nextRow - 1, Color.lightGray);
/*      */             
/*  500 */             columnHeaderTable.setColBorderColor(nextRow, -1, Color.lightGray);
/*  501 */             columnHeaderTable.setColBorderColor(nextRow, 0, Color.lightGray);
/*  502 */             columnHeaderTable.setColBorderColor(nextRow, 1, Color.lightGray);
/*  503 */             columnHeaderTable.setColBorderColor(nextRow, 2, Color.lightGray);
/*  504 */             columnHeaderTable.setColBorderColor(nextRow, 3, Color.lightGray);
/*  505 */             columnHeaderTable.setColBorderColor(nextRow, 4, Color.lightGray);
/*  506 */             columnHeaderTable.setColBorderColor(nextRow, 5, Color.lightGray);
/*  507 */             columnHeaderTable.setColBorderColor(nextRow, 6, Color.lightGray);
/*  508 */             columnHeaderTable.setColBorderColor(nextRow, 7, Color.lightGray);
/*  509 */             columnHeaderTable.setColBorderColor(nextRow, 8, Color.lightGray);
/*  510 */             columnHeaderTable.setColBorderColor(nextRow, 9, Color.lightGray);
/*  511 */             columnHeaderTable.setColBorderColor(nextRow, 10, Color.lightGray);
/*  512 */             columnHeaderTable.setColBorderColor(nextRow, 11, Color.lightGray);
/*  513 */             columnHeaderTable.setColBorderColor(nextRow, 12, Color.lightGray);
/*  514 */             columnHeaderTable.setColBorderColor(nextRow, 13, Color.lightGray);
/*  515 */             columnHeaderTable.setColBorderColor(nextRow, 14, Color.lightGray);
/*  516 */             columnHeaderTable.setColBorderColor(nextRow, 15, Color.lightGray);
/*  517 */             columnHeaderTable.setColBorderColor(nextRow, 16, Color.lightGray);
/*  518 */             columnHeaderTable.setColBorderColor(nextRow, 17, Color.lightGray);
/*  519 */             columnHeaderTable.setColBorderColor(nextRow, 18, Color.lightGray);
/*      */             
/*  521 */             columnHeaderTable.setRowBorderColor(nextRow, Color.lightGray);
/*      */             
/*  523 */             hbandType.addTable(columnHeaderTable, new Rectangle(0, 25, 800, 40));
/*      */             
/*  525 */             nextRow = 0;
/*      */             
/*  527 */             DefaultTableLens monthTableLens = new DefaultTableLens(1, 19);
/*      */             
/*  529 */             nextRow = 0;
/*      */ 
/*      */             
/*  532 */             monthTableLens.setObject(nextRow, 0, monthNameString);
/*  533 */             monthTableLens.setSpan(nextRow, 0, new Dimension(19, 1));
/*  534 */             monthTableLens.setRowAlignment(nextRow, 1);
/*  535 */             monthTableLens.setRowFont(nextRow, new Font("Arial", 1, 12));
/*  536 */             monthTableLens.setRowHeight(nextRow, 14);
/*  537 */             monthTableLens.setColBorderColor(nextRow, -1, Color.white);
/*  538 */             monthTableLens.setColBorderColor(nextRow, 18, Color.white);
/*  539 */             monthTableLens.setRowBorderColor(nextRow, Color.white);
/*  540 */             monthTableLens.setRowBorderColor(nextRow - 1, Color.white);
/*  541 */             monthTableLens.setRowBackground(nextRow, Color.lightGray);
/*      */             
/*  543 */             hbandMonth.addTable(monthTableLens, new Rectangle(800, 800));
/*  544 */             hbandMonth.setBottomBorder(0);
/*      */             
/*  546 */             footer.setVisible(true);
/*  547 */             footer.setHeight(0.05F);
/*  548 */             footer.setShrinkToFit(true);
/*  549 */             footer.setBottomBorder(0);
/*      */             
/*  551 */             group = new DefaultSectionLens(null, group, footer);
/*  552 */             group = new DefaultSectionLens(null, group, hbandMonth);
/*  553 */             group = new DefaultSectionLens(null, group, footer);
/*      */ 
/*      */             
/*  556 */             for (int j = 0; j < selections.size(); j++) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  562 */               if (count < recordCount / tenth) {
/*      */                 
/*  564 */                 count = recordCount / tenth;
/*  565 */                 sresponse = context.getResponse();
/*  566 */                 context.putDelivery("status", new String("start_report"));
/*  567 */                 context.putDelivery("percent", new String(String.valueOf(count * 10)));
/*  568 */                 context.includeJSP("status.jsp", "hiddenFrame");
/*  569 */                 sresponse.setContentType("text/plain");
/*  570 */                 sresponse.flushBuffer();
/*      */               } 
/*      */               
/*  573 */               recordCount++;
/*      */               
/*  575 */               long start_selection = System.currentTimeMillis();
/*      */ 
/*      */               
/*  578 */               Selection sel = (Selection)selections.elementAt(j);
/*      */               
/*  580 */               String releaseDate = MilestoneHelper.getFormatedDate(sel.getStreetDate());
/*      */               
/*  582 */               String status = (sel.getSelectionStatus() != null && sel.getSelectionStatus().getName() != null) ? 
/*  583 */                 sel.getSelectionStatus().getName() : "";
/*      */               
/*  585 */               if (status.equalsIgnoreCase("TBS")) {
/*  586 */                 releaseDate = "TBS " + releaseDate;
/*      */               }
/*  588 */               else if (status.equalsIgnoreCase("In The Works")) {
/*  589 */                 releaseDate = "ITW " + releaseDate;
/*      */               } 
/*  591 */               String releaseWeek = MilestoneHelper.getReleaseWeekString(sel);
/*  592 */               if (releaseWeek != null) {
/*      */                 
/*  594 */                 int slashIndex = releaseWeek.indexOf("/");
/*  595 */                 if (slashIndex != -1) {
/*  596 */                   releaseWeek = releaseWeek.substring(0, slashIndex).trim();
/*      */                 }
/*      */               } 
/*  599 */               String projectNo = sel.getProjectID();
/*  600 */               String labelContact = "";
/*  601 */               if (sel.getLabelContact() != null && sel.getLabelContact().getName() != null) {
/*  602 */                 labelContact = sel.getLabelContact().getName();
/*      */               }
/*  604 */               String sellCode = (sel.getSellCode() != null) ? sel.getSellCode() : "   ";
/*  605 */               if (sellCode != null && sellCode.startsWith("-1")) {
/*  606 */                 sellCode = "";
/*      */               }
/*  608 */               String retailCode = "";
/*  609 */               if (sel.getPriceCode() != null && sel.getPriceCode().getRetailCode() != null) {
/*  610 */                 retailCode = sel.getPriceCode().getRetailCode();
/*      */               }
/*  612 */               String price = "";
/*  613 */               if (sel.getPriceCode() != null && sel.getPriceCode().getTotalCost() > 0.0F) {
/*  614 */                 price = "$" + MilestoneHelper.formatDollarPrice(sel.getPriceCode().getTotalCost());
/*      */               }
/*      */               
/*  617 */               String label = sel.getImprint();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  624 */               String localProductNumber = "";
/*  625 */               if (sel.getPrefixID() != null && SelectionManager.getLookupObjectValue(sel.getPrefixID()) != null) {
/*  626 */                 localProductNumber = SelectionManager.getLookupObjectValue(sel.getPrefixID());
/*      */               }
/*  628 */               localProductNumber = String.valueOf(localProductNumber) + sel.getSelectionNo();
/*      */ 
/*      */               
/*  631 */               String upc = (sel.getUpc() != null) ? sel.getUpc() : "";
/*      */ 
/*      */               
/*  634 */               upc = MilestoneHelper_2.getRMSReportFormat(upc, "UPC", sel.getIsDigital());
/*      */               
/*  636 */               String selComments = "";
/*  637 */               selComments = (sel.getSelectionComments() != null) ? sel.getSelectionComments() : "";
/*  638 */               String newComment = selComments.replace('\n', ' ');
/*      */ 
/*      */               
/*  641 */               int subTableRows = 2;
/*  642 */               if (newComment.length() > 0) {
/*  643 */                 subTableRows = 3;
/*      */               }
/*      */ 
/*      */ 
/*      */               
/*  648 */               String comments = "";
/*  649 */               String[] checkStrings = { comments, labelContact };
/*  650 */               int[] checkStringLengths = { 50, 10 };
/*      */ 
/*      */               
/*  653 */               int extraLines = MilestoneHelper.lineCountWCR(checkStrings, checkStringLengths);
/*      */               
/*  655 */               if (extraLines <= 2) {
/*  656 */                 extraLines += ((extraLines == 1) ? 2 : 1);
/*      */               }
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  662 */               Vector tasks = MilestoneHelper.getTasksForUmeProdScheduleReportAbbrev(sel);
/*      */               
/*  664 */               nextRow = 0;
/*      */               
/*  666 */               subTable = new DefaultTableLens(subTableRows, 19);
/*      */               
/*  668 */               subTable.setColWidth(0, 110);
/*  669 */               subTable.setColWidth(1, 70);
/*  670 */               subTable.setColWidth(2, 70);
/*  671 */               subTable.setColWidth(3, 70);
/*  672 */               subTable.setColWidth(4, 150);
/*      */               
/*  674 */               subTable.setColWidth(5, 65);
/*  675 */               subTable.setColWidth(6, 65);
/*  676 */               subTable.setColWidth(7, 80);
/*  677 */               subTable.setColWidth(8, 85);
/*  678 */               subTable.setColWidth(9, 85);
/*      */               
/*  680 */               subTable.setColWidth(10, 70);
/*  681 */               subTable.setColWidth(11, 70);
/*  682 */               subTable.setColWidth(12, 70);
/*  683 */               subTable.setColWidth(13, 70);
/*  684 */               subTable.setColWidth(14, 80);
/*  685 */               subTable.setColWidth(15, 70);
/*  686 */               subTable.setColWidth(16, 65);
/*  687 */               subTable.setColWidth(17, 70);
/*  688 */               subTable.setColWidth(18, 70);
/*      */ 
/*      */               
/*  691 */               subTable.setRowHeight(nextRow, 9);
/*  692 */               subTable.setRowAlignment(nextRow, 10);
/*  693 */               subTable.setRowFont(nextRow, new Font("Arial", 0, 7));
/*  694 */               subTable.setRowFont(nextRow + 1, new Font("Arial", 0, 7));
/*  695 */               subTable.setRowBorderColor(nextRow, Color.lightGray);
/*  696 */               subTable.setRowBorderColor(nextRow - 1, Color.lightGray);
/*      */ 
/*      */               
/*  699 */               subTable.setSpan(nextRow, 0, new Dimension(1, 2));
/*  700 */               subTable.setObject(nextRow, 0, 
/*  701 */                   String.valueOf(releaseDate) + "\n" + releaseWeek + "\n" + projectNo + "\n" + labelContact);
/*      */ 
/*      */               
/*  704 */               subTable.setObject(nextRow, 1, sellCode);
/*  705 */               subTable.setObject(nextRow, 2, retailCode);
/*  706 */               subTable.setObject(nextRow, 3, price);
/*  707 */               subTable.setBackground(nextRow, 1, Color.lightGray);
/*  708 */               subTable.setBackground(nextRow, 2, Color.lightGray);
/*  709 */               subTable.setBackground(nextRow, 3, Color.lightGray);
/*      */               
/*  711 */               subTable.setRowAlignment(nextRow + 1, 10);
/*  712 */               subTable.setRowBorderColor(nextRow + 1, Color.lightGray);
/*  713 */               for (int z = -1; z <= numColumns; z++) {
/*      */                 
/*  715 */                 subTable.setColBorder(nextRow, z, 4097);
/*  716 */                 subTable.setColBorderColor(nextRow, z, Color.lightGray);
/*      */               } 
/*      */               
/*  719 */               subTable.setSpan(nextRow + 1, 1, new Dimension(3, 1));
/*      */               
/*  721 */               subTable.setObject(nextRow + 1, 1, String.valueOf(sel.getFlArtist()) + "\n" + sel.getTitle());
/*      */ 
/*      */               
/*  724 */               subTable.setFont(nextRow, 4, new Font("Arial", 1, 8));
/*  725 */               subTable.setObject(nextRow, 4, "Due Dates");
/*  726 */               subTable.setBackground(nextRow, 4, Color.lightGray);
/*      */               
/*  728 */               for (int y = 0; y < extraLines - 2; y++) {
/*  729 */                 upc = String.valueOf(upc) + "\n";
/*      */               }
/*  731 */               subTable.setObject(nextRow + 1, 4, String.valueOf(label) + "\n" + localProductNumber + "\n" + upc);
/*      */               
/*  733 */               String CLR = "";
/*  734 */               String CRD = "";
/*  735 */               String FPC = "";
/*  736 */               String MBR = "";
/*  737 */               String SEPS = "";
/*  738 */               String SFD = "";
/*  739 */               String TAPE = "";
/*  740 */               String PFS = "";
/*  741 */               String MQD = "";
/*  742 */               String PAP = "";
/*  743 */               String PSD = "";
/*  744 */               String SMR = "";
/*  745 */               String PL = "";
/*  746 */               String PO = "";
/*      */               
/*  748 */               String CLRcom = "";
/*  749 */               String CRDcom = "";
/*  750 */               String FPCcom = "";
/*  751 */               String MBRcom = "";
/*  752 */               String SEPScom = "";
/*  753 */               String SFDcom = "";
/*  754 */               String TAPEcom = "";
/*  755 */               String PFScom = "";
/*  756 */               String MQDcom = "";
/*  757 */               String PAPcom = "";
/*  758 */               String PSDcom = "";
/*  759 */               String SMRcom = "";
/*  760 */               String PLcom = "";
/*  761 */               String POcom = "";
/*      */               
/*  763 */               String taskDueDate = "", taskCompletionDate = "", taskVendor = "";
/*  764 */               ScheduledTask task = null;
/*      */               
/*  766 */               if (tasks != null)
/*      */               {
/*      */                 
/*  769 */                 for (int z = 0; z < tasks.size(); z++) {
/*      */                   
/*  771 */                   task = (ScheduledTask)tasks.get(z);
/*      */ 
/*      */                   
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
/*      */ 
/*      */                     
/*  807 */                     if (taskAbbrev.equalsIgnoreCase("CLR")) {
/*      */                       
/*  809 */                       CLR = dueDate;
/*  810 */                       CLRcom = String.valueOf(completionDate) + taskVendor;
/*      */                     }
/*  812 */                     else if (taskAbbrev.equalsIgnoreCase("CRD")) {
/*      */                       
/*  814 */                       CRD = dueDate;
/*  815 */                       CRDcom = String.valueOf(completionDate) + taskVendor;
/*      */                     }
/*  817 */                     else if (taskAbbrev.equalsIgnoreCase("FPC")) {
/*      */                       
/*  819 */                       FPC = dueDate;
/*  820 */                       FPCcom = String.valueOf(completionDate) + taskVendor;
/*      */                     }
/*  822 */                     else if (taskAbbrev.equalsIgnoreCase("MBR")) {
/*      */                       
/*  824 */                       MBR = dueDate;
/*  825 */                       MBRcom = String.valueOf(completionDate) + taskVendor;
/*      */                     }
/*  827 */                     else if (taskAbbrev.equalsIgnoreCase("SEPS")) {
/*      */                       
/*  829 */                       SEPS = dueDate;
/*  830 */                       SEPScom = String.valueOf(completionDate) + taskVendor;
/*      */                     }
/*  832 */                     else if (taskAbbrev.equalsIgnoreCase("SFD")) {
/*      */                       
/*  834 */                       SFD = dueDate;
/*  835 */                       SFDcom = String.valueOf(completionDate) + taskVendor;
/*      */                     }
/*  837 */                     else if (taskAbbrev.equalsIgnoreCase("TAPE")) {
/*      */                       
/*  839 */                       TAPE = dueDate;
/*  840 */                       TAPEcom = String.valueOf(completionDate) + taskVendor;
/*      */                     }
/*  842 */                     else if (taskAbbrev.equalsIgnoreCase("PFS")) {
/*      */                       
/*  844 */                       PFS = dueDate;
/*  845 */                       PFScom = String.valueOf(completionDate) + taskVendor;
/*      */                     }
/*  847 */                     else if (taskAbbrev.equalsIgnoreCase("MQD")) {
/*      */                       
/*  849 */                       MQD = dueDate;
/*  850 */                       MQDcom = String.valueOf(completionDate) + taskVendor;
/*      */                     }
/*  852 */                     else if (taskAbbrev.equalsIgnoreCase("PAP")) {
/*      */                       
/*  854 */                       PAP = dueDate;
/*  855 */                       PAPcom = String.valueOf(completionDate) + taskVendor;
/*      */                     }
/*  857 */                     else if (taskAbbrev.equalsIgnoreCase("PSD")) {
/*      */                       
/*  859 */                       PSD = dueDate;
/*  860 */                       PSDcom = String.valueOf(completionDate) + taskVendor;
/*      */                     
/*      */                     }
/*  863 */                     else if (taskAbbrev.equalsIgnoreCase("SMR")) {
/*      */                       
/*  865 */                       SMR = dueDate;
/*  866 */                       SMRcom = String.valueOf(completionDate) + taskVendor;
/*      */                     
/*      */                     }
/*  869 */                     else if (taskAbbrev.equalsIgnoreCase("P&L")) {
/*      */                       
/*  871 */                       PL = dueDate;
/*  872 */                       PLcom = String.valueOf(completionDate) + taskVendor;
/*      */                     
/*      */                     }
/*  875 */                     else if (taskAbbrev.equalsIgnoreCase("PO")) {
/*      */                       
/*  877 */                       PO = dueDate;
/*  878 */                       POcom = String.valueOf(completionDate) + taskVendor;
/*      */                     } 
/*      */ 
/*      */                     
/*  882 */                     task = null;
/*      */                   } 
/*      */                 } 
/*      */               }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  891 */               subTable.setRowBorderColor(nextRow, Color.lightGray);
/*  892 */               subTable.setRowBorderColor(nextRow + 1, Color.lightGray);
/*      */               
/*  894 */               for (int a = -1; a <= numColumns; a++) {
/*      */                 
/*  896 */                 subTable.setColBorder(nextRow, a, 4097);
/*  897 */                 subTable.setColBorderColor(nextRow, a, Color.lightGray);
/*  898 */                 subTable.setColBorder(nextRow + 1, a, 4097);
/*  899 */                 subTable.setColBorderColor(nextRow + 1, a, Color.lightGray);
/*      */               } 
/*      */ 
/*      */               
/*  903 */               subTable.setObject(nextRow, 5, SMR);
/*  904 */               subTable.setBackground(nextRow, 5, Color.lightGray);
/*  905 */               subTable.setObject(nextRow + 1, 5, SMRcom);
/*      */ 
/*      */               
/*  908 */               subTable.setObject(nextRow, 6, PL);
/*  909 */               subTable.setBackground(nextRow, 6, Color.lightGray);
/*  910 */               subTable.setObject(nextRow + 1, 6, PLcom);
/*      */ 
/*      */               
/*  913 */               subTable.setObject(nextRow, 7, CLR);
/*  914 */               subTable.setBackground(nextRow, 7, Color.lightGray);
/*  915 */               subTable.setObject(nextRow + 1, 7, CLRcom);
/*      */ 
/*      */               
/*  918 */               subTable.setObject(nextRow, 8, CRD);
/*  919 */               subTable.setBackground(nextRow, 8, Color.lightGray);
/*  920 */               subTable.setObject(nextRow + 1, 8, CRDcom);
/*      */ 
/*      */               
/*  923 */               subTable.setObject(nextRow, 9, FPC);
/*  924 */               subTable.setBackground(nextRow, 9, Color.lightGray);
/*  925 */               subTable.setObject(nextRow + 1, 9, FPCcom);
/*      */ 
/*      */ 
/*      */               
/*  929 */               subTable.setObject(nextRow, 11, MQD);
/*  930 */               subTable.setBackground(nextRow, 11, Color.lightGray);
/*  931 */               subTable.setObject(nextRow + 1, 11, MQDcom);
/*      */ 
/*      */               
/*  934 */               subTable.setObject(nextRow, 12, SFD);
/*  935 */               subTable.setBackground(nextRow, 12, Color.lightGray);
/*  936 */               subTable.setObject(nextRow + 1, 12, SFDcom);
/*      */ 
/*      */               
/*  939 */               subTable.setObject(nextRow, 10, MBR);
/*  940 */               subTable.setBackground(nextRow, 10, Color.lightGray);
/*  941 */               subTable.setObject(nextRow + 1, 10, MBRcom);
/*      */ 
/*      */               
/*  944 */               subTable.setObject(nextRow, 13, SEPS);
/*  945 */               subTable.setBackground(nextRow, 13, Color.lightGray);
/*  946 */               subTable.setObject(nextRow + 1, 13, SEPScom);
/*      */ 
/*      */               
/*  949 */               subTable.setObject(nextRow, 14, PO);
/*  950 */               subTable.setBackground(nextRow, 14, Color.lightGray);
/*  951 */               subTable.setObject(nextRow + 1, 14, POcom);
/*      */ 
/*      */               
/*  954 */               subTable.setObject(nextRow, 15, PFS);
/*  955 */               subTable.setBackground(nextRow, 15, Color.lightGray);
/*  956 */               subTable.setObject(nextRow + 1, 15, PFScom);
/*      */ 
/*      */               
/*  959 */               subTable.setObject(nextRow, 16, TAPE);
/*  960 */               subTable.setBackground(nextRow, 16, Color.lightGray);
/*  961 */               subTable.setObject(nextRow + 1, 16, TAPEcom);
/*      */ 
/*      */ 
/*      */               
/*  965 */               subTable.setObject(nextRow, 17, PAP);
/*  966 */               subTable.setBackground(nextRow, 17, Color.lightGray);
/*  967 */               subTable.setObject(nextRow + 1, 17, PAPcom);
/*      */ 
/*      */               
/*  970 */               subTable.setObject(nextRow, 18, PSD);
/*  971 */               subTable.setBackground(nextRow, 18, Color.lightGray);
/*  972 */               subTable.setObject(nextRow + 1, 18, PSDcom);
/*      */ 
/*      */ 
/*      */               
/*  976 */               Font holidayFont = new Font("Arial", 3, 8);
/*  977 */               Font nonHolidayFont = new Font("Arial", 1, 8);
/*  978 */               for (int colIdx = 5; colIdx <= 18; colIdx++) {
/*  979 */                 String dueDate = subTable.getObject(nextRow, colIdx).toString();
/*  980 */                 if (dueDate != null && dueDate.length() > 0) {
/*  981 */                   char lastChar = dueDate.charAt(dueDate.length() - 1);
/*  982 */                   if (Character.isLetter(lastChar)) {
/*  983 */                     subTable.setFont(nextRow, colIdx, holidayFont);
/*      */                   } else {
/*      */                     
/*  986 */                     subTable.setFont(nextRow, colIdx, nonHolidayFont);
/*      */                   } 
/*      */                 } 
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
/* 1003 */               if (newComment.length() > 0) {
/* 1004 */                 nextRow++;
/*      */ 
/*      */                 
/* 1007 */                 subTable.setRowAutoSize(true);
/* 1008 */                 subTable.setRowBorderColor(nextRow + 1, Color.lightGray);
/* 1009 */                 setColBorderColor(subTable, nextRow + 1, numColumns, SHADED_AREA_COLOR);
/* 1010 */                 subTable.setRowFont(nextRow + 1, new Font("Arial", 2, 7));
/*      */                 
/* 1012 */                 subTable.setSpan(nextRow + 1, 0, new Dimension(1, 1));
/* 1013 */                 subTable.setAlignment(nextRow + 1, 0, 9);
/* 1014 */                 subTable.setObject(nextRow + 1, 0, "Comments:   ");
/*      */                 
/* 1016 */                 subTable.setObject(nextRow + 1, 1, newComment);
/* 1017 */                 subTable.setSpan(nextRow + 1, 1, new Dimension(18, 1));
/*      */                 
/* 1019 */                 subTable.setRowBorderColor(nextRow + 1, Color.lightGray);
/* 1020 */                 setColBorderColor(subTable, nextRow + 1, numColumns, SHADED_AREA_COLOR);
/* 1021 */                 subTable.setRowFont(nextRow + 1, new Font("Arial", 2, 7));
/* 1022 */                 subTable.setAlignment(nextRow + 1, 2, 9);
/* 1023 */                 subTable.setColLineWrap(2, true);
/*      */                 
/* 1025 */                 subTable.setColBorderColor(nextRow + 1, 0, Color.white);
/* 1026 */                 subTable.setColBorderColor(nextRow + 1, 1, Color.white);
/* 1027 */                 subTable.setColBorderColor(nextRow + 1, 2, Color.white);
/* 1028 */                 subTable.setColBorderColor(nextRow + 1, 3, Color.white);
/* 1029 */                 subTable.setColBorderColor(nextRow + 1, 4, Color.white);
/* 1030 */                 subTable.setColBorderColor(nextRow + 1, 5, Color.white);
/* 1031 */                 subTable.setColBorderColor(nextRow + 1, 6, Color.white);
/* 1032 */                 subTable.setColBorderColor(nextRow + 1, 7, Color.white);
/* 1033 */                 subTable.setColBorderColor(nextRow + 1, 8, Color.white);
/* 1034 */                 subTable.setColBorderColor(nextRow + 1, 9, Color.white);
/* 1035 */                 subTable.setColBorderColor(nextRow + 1, 10, Color.white);
/* 1036 */                 subTable.setColBorderColor(nextRow + 1, 11, Color.white);
/* 1037 */                 subTable.setColBorderColor(nextRow + 1, 12, Color.white);
/* 1038 */                 subTable.setColBorderColor(nextRow + 1, 13, Color.white);
/*      */               } 
/*      */ 
/*      */ 
/*      */               
/* 1043 */               nextRow += 2;
/*      */               
/* 1045 */               body = new SectionBand(report);
/*      */ 
/*      */               
/* 1048 */               double lfLineCount = 1.5D;
/*      */               
/* 1050 */               if (extraLines > 0 || label.length() > 17 || sel.getTitle().length() > 20) {
/*      */                 
/* 1052 */                 body.setHeight(1.5F);
/*      */               }
/*      */               else {
/*      */                 
/* 1056 */                 body.setHeight(1.0F);
/*      */               } 
/*      */               
/* 1059 */               if (extraLines > 0 || label.length() > 13 || sel.getTitle().length() > 14 || 
/* 1060 */                 CLRcom.length() > 18 || CRDcom.length() > 18 || FPCcom.length() > 18 || 
/* 1061 */                 MBRcom.length() > 18 || SEPScom.length() > 18 || 
/* 1062 */                 SFDcom.length() > 18 || TAPEcom.length() > 18 || 
/* 1063 */                 PFScom.length() > 18 || MQDcom.length() > 18 || 
/* 1064 */                 PAPcom.length() > 18 || 
/* 1065 */                 PSDcom.length() > 18 || SMRcom.length() > 18) {
/*      */                 
/* 1067 */                 if (lfLineCount < extraLines * 0.25D) {
/* 1068 */                   lfLineCount = extraLines * 0.25D;
/*      */                 }
/* 1070 */                 if (lfLineCount < (CLRcom.length() / 7) * 0.25D) {
/* 1071 */                   lfLineCount = (CLRcom.length() / 7) * 0.25D;
/*      */                 }
/* 1073 */                 if (lfLineCount < (CRDcom.length() / 8) * 0.25D) {
/* 1074 */                   lfLineCount = (CRDcom.length() / 8) * 0.25D;
/*      */                 }
/* 1076 */                 if (lfLineCount < (FPCcom.length() / 8) * 0.25D) {
/* 1077 */                   lfLineCount = (FPCcom.length() / 8) * 0.25D;
/*      */                 }
/* 1079 */                 if (lfLineCount < (MBRcom.length() / 8) * 0.25D) {
/* 1080 */                   lfLineCount = (MBRcom.length() / 8) * 0.25D;
/*      */                 }
/* 1082 */                 if (lfLineCount < (sel.getTitle().length() / 8) * 0.25D) {
/* 1083 */                   lfLineCount = (sel.getTitle().length() / 8) * 0.25D;
/*      */                 }
/* 1085 */                 if (lfLineCount < (SEPScom.length() / 7) * 0.25D) {
/* 1086 */                   lfLineCount = (SEPScom.length() / 7) * 0.25D;
/*      */                 }
/* 1088 */                 if (lfLineCount < (SFDcom.length() / 7) * 0.25D) {
/* 1089 */                   lfLineCount = (SFDcom.length() / 7) * 0.25D;
/*      */                 }
/* 1091 */                 if (lfLineCount < (TAPEcom.length() / 7) * 0.25D) {
/* 1092 */                   lfLineCount = (TAPEcom.length() / 7) * 0.25D;
/*      */                 }
/* 1094 */                 if (lfLineCount < (PFScom.length() / 7) * 0.25D) {
/* 1095 */                   lfLineCount = (PFScom.length() / 7) * 0.25D;
/*      */                 }
/* 1097 */                 if (lfLineCount < (MQDcom.length() / 7) * 0.25D) {
/* 1098 */                   lfLineCount = (MQDcom.length() / 7) * 0.25D;
/*      */                 }
/* 1100 */                 if (lfLineCount < (PAPcom.length() / 7) * 0.25D) {
/* 1101 */                   lfLineCount = (PAPcom.length() / 7) * 0.25D;
/*      */                 }
/* 1103 */                 if (lfLineCount < (PSDcom.length() / 7) * 0.25D) {
/* 1104 */                   lfLineCount = (PSDcom.length() / 7) * 0.25D;
/*      */                 }
/* 1106 */                 body.setHeight((float)lfLineCount);
/*      */               }
/* 1108 */               else if (extraLines > 0 || label.length() > 17 || sel.getTitle().length() > 20 || 
/* 1109 */                 CLRcom.length() > 5 || CRDcom.length() > 5 || FPCcom.length() > 5 || 
/* 1110 */                 MBRcom.length() > 5 || SEPScom.length() > 5 || 
/* 1111 */                 SFDcom.length() > 5 || TAPEcom.length() > 5 || 
/* 1112 */                 PFScom.length() > 5 || MQDcom.length() > 5 || 
/* 1113 */                 PAPcom.length() > 5 || 
/* 1114 */                 PSDcom.length() > 5 || SMRcom.length() > 5) {
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 1119 */                 if (lfLineCount < extraLines * 0.25D) {
/* 1120 */                   lfLineCount = extraLines * 0.25D;
/*      */                 }
/* 1122 */                 if (lfLineCount < (CLRcom.length() / 7) * 0.25D) {
/* 1123 */                   lfLineCount = (CLRcom.length() / 7) * 0.25D;
/*      */                 }
/* 1125 */                 if (lfLineCount < (CRDcom.length() / 8) * 0.25D) {
/* 1126 */                   lfLineCount = (CRDcom.length() / 8) * 0.25D;
/*      */                 }
/* 1128 */                 if (lfLineCount < (FPCcom.length() / 8) * 0.25D) {
/* 1129 */                   lfLineCount = (FPCcom.length() / 8) * 0.25D;
/*      */                 }
/* 1131 */                 if (lfLineCount < (MBRcom.length() / 8) * 0.25D) {
/* 1132 */                   lfLineCount = (MBRcom.length() / 8) * 0.25D;
/*      */                 }
/* 1134 */                 if (lfLineCount < (sel.getTitle().length() / 8) * 0.25D) {
/* 1135 */                   lfLineCount = (sel.getTitle().length() / 8) * 0.25D;
/*      */                 }
/* 1137 */                 if (lfLineCount < (SEPScom.length() / 7) * 0.25D) {
/* 1138 */                   lfLineCount = (SEPScom.length() / 7) * 0.25D;
/*      */                 }
/* 1140 */                 if (lfLineCount < (SFDcom.length() / 7) * 0.25D) {
/* 1141 */                   lfLineCount = (SFDcom.length() / 7) * 0.25D;
/*      */                 }
/* 1143 */                 if (lfLineCount < (TAPEcom.length() / 7) * 0.25D) {
/* 1144 */                   lfLineCount = (TAPEcom.length() / 7) * 0.25D;
/*      */                 }
/* 1146 */                 if (lfLineCount < (PFScom.length() / 7) * 0.25D) {
/* 1147 */                   lfLineCount = (PFScom.length() / 7) * 0.25D;
/*      */                 }
/* 1149 */                 if (lfLineCount < (MQDcom.length() / 7) * 0.25D) {
/* 1150 */                   lfLineCount = (MQDcom.length() / 7) * 0.25D;
/*      */                 }
/* 1152 */                 if (lfLineCount < (PAPcom.length() / 7) * 0.25D) {
/* 1153 */                   lfLineCount = (PAPcom.length() / 7) * 0.25D;
/*      */                 }
/* 1155 */                 if (lfLineCount < (PSDcom.length() / 7) * 0.25D) {
/* 1156 */                   lfLineCount = (PSDcom.length() / 7) * 0.25D;
/*      */                 }
/* 1158 */                 if (lfLineCount < (SMRcom.length() / 7) * 0.25D) {
/* 1159 */                   lfLineCount = (SMRcom.length() / 7) * 0.25D;
/*      */                 }
/* 1161 */                 body.setHeight((float)lfLineCount);
/*      */               
/*      */               }
/*      */               else {
/*      */                 
/* 1166 */                 body.setHeight(1.0F);
/*      */               } 
/*      */ 
/*      */               
/* 1170 */               if (newComment.length() > 0) {
/* 1171 */                 lfLineCount = 2.0D;
/* 1172 */                 if (newComment.length() > 500)
/* 1173 */                   lfLineCount = 3.0D; 
/* 1174 */                 if (newComment.length() > 1000)
/* 1175 */                   lfLineCount = 4.0D; 
/* 1176 */                 if (newComment.length() > 3000)
/* 1177 */                   lfLineCount = 5.0D; 
/* 1178 */                 body.setHeight((float)lfLineCount);
/*      */               } 
/*      */ 
/*      */               
/* 1182 */               body.addTable(subTable, new Rectangle(800, 800));
/* 1183 */               body.setBottomBorder(0);
/* 1184 */               body.setTopBorder(0);
/* 1185 */               body.setShrinkToFit(true);
/* 1186 */               body.setVisible(true);
/*      */               
/* 1188 */               group = new DefaultSectionLens(null, group, body);
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1194 */             long l = System.currentTimeMillis();
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/* 1199 */         group = new DefaultSectionLens(hbandType, group, null);
/*      */         
/* 1201 */         report.addSection(group, rowCountTable);
/* 1202 */         report.addPageBreak();
/* 1203 */         group = null;
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 1212 */     catch (Exception e) {
/*      */       
/* 1214 */       System.out.println(">>>>>>>>UmeProdScheduleForPrintSubHandler.fillUmeProdScheduleForPrint(): exception: " + e);
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
/*      */   public static int insertConfigHeader(DefaultTableLens table_contents, String title, int nextRow, int cols) {
/* 1230 */     int COL_LINE_STYLE = 4097;
/* 1231 */     int HEADER_FONT_SIZE = 12;
/*      */     
/* 1233 */     table_contents.setObject(nextRow, 0, "");
/* 1234 */     table_contents.setSpan(nextRow, 0, new Dimension(cols, 1));
/* 1235 */     table_contents.setRowHeight(nextRow, 1);
/* 1236 */     table_contents.setRowBackground(nextRow, Color.white);
/*      */     
/* 1238 */     table_contents.setColBorderColor(nextRow, -1, Color.black);
/* 1239 */     table_contents.setColBorder(nextRow, -1, 4097);
/* 1240 */     table_contents.setColBorderColor(nextRow, cols - 1, Color.black);
/* 1241 */     table_contents.setColBorder(nextRow, cols - 1, 4097);
/*      */     
/* 1243 */     table_contents.setRowBorderColor(nextRow, Color.white);
/*      */     
/* 1245 */     table_contents.setRowBorder(nextRow, 0);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1250 */     table_contents.setColBorderColor(nextRow + 1, -1, Color.black);
/* 1251 */     table_contents.setColBorder(nextRow + 1, -1, 4097);
/* 1252 */     table_contents.setColBorderColor(nextRow + 1, cols - 1, Color.black);
/* 1253 */     table_contents.setColBorder(nextRow + 1, cols - 1, 4097);
/*      */ 
/*      */     
/* 1256 */     table_contents.setAlignment(nextRow + 1, 0, 2);
/* 1257 */     table_contents.setSpan(nextRow + 1, 0, new Dimension(cols, 1));
/* 1258 */     table_contents.setObject(nextRow + 1, 0, title);
/* 1259 */     table_contents.setRowFont(nextRow + 1, new Font("Arial", 3, 12));
/*      */     
/* 1261 */     nextRow += 2;
/*      */     
/* 1263 */     table_contents.setObject(nextRow, 0, "");
/* 1264 */     table_contents.setSpan(nextRow, 0, new Dimension(cols, 1));
/* 1265 */     table_contents.setRowHeight(nextRow, 1);
/* 1266 */     table_contents.setRowBackground(nextRow, Color.white);
/*      */     
/* 1268 */     table_contents.setColBorderColor(nextRow, -1, Color.white);
/* 1269 */     table_contents.setColBorder(nextRow, -1, 0);
/* 1270 */     table_contents.setColBorderColor(nextRow, cols, Color.black);
/* 1271 */     table_contents.setColBorder(nextRow, cols, 4097);
/*      */     
/* 1273 */     table_contents.setRowBorderColor(nextRow, Color.white);
/* 1274 */     table_contents.setRowBorder(nextRow, 0);
/*      */     
/* 1276 */     return nextRow;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1283 */   public static void removeLF(String theString) { theString.replace('\n', ' '); }
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


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\UmeProdScheduleForPrintSubHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */