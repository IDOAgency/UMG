/*      */ package WEB-INF.classes.com.universal.milestone;
/*      */ 
/*      */ import com.techempower.ComponentLog;
/*      */ import com.techempower.gemini.Context;
/*      */ import com.techempower.gemini.GeminiApplication;
/*      */ import com.universal.milestone.Form;
/*      */ import com.universal.milestone.IDJExceptionsComparator;
/*      */ import com.universal.milestone.IDJReportDateArtistComparator;
/*      */ import com.universal.milestone.IDJSelectionSortComparator;
/*      */ import com.universal.milestone.IdjProductionScheduleForPrintSubHandlerAlternate;
/*      */ import com.universal.milestone.JdbcConnector;
/*      */ import com.universal.milestone.MilestoneHelper;
/*      */ import com.universal.milestone.MilestoneHelper_2;
/*      */ import com.universal.milestone.MultCompleteDate;
/*      */ import com.universal.milestone.Schedule;
/*      */ import com.universal.milestone.ScheduledTask;
/*      */ import com.universal.milestone.SecureHandler;
/*      */ import com.universal.milestone.Selection;
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
/*      */ import java.util.Calendar;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
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
/*      */ public class IdjProductionScheduleForPrintSubHandlerAlternate
/*      */   extends SecureHandler
/*      */ {
/*      */   public static final String COMPONENT_CODE = "hPsp";
/*      */   public GeminiApplication application;
/*      */   public ComponentLog log;
/*      */   
/*      */   public IdjProductionScheduleForPrintSubHandlerAlternate(GeminiApplication application) {
/*   76 */     this.application = application;
/*   77 */     this.log = application.getLog("hPsp");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   85 */   public String getDescription() { return "IdjProductionScheduleForPrintSubHandlerAlternate"; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static void fillIdjProductionScheduleForPrint(XStyleSheet report, Context context) {
/*   99 */     int COL_LINE_STYLE = 4097;
/*  100 */     int HEADER_FONT_SIZE = 12;
/*      */     
/*  102 */     double ldLineVal = 0.3D;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  108 */       HttpServletResponse sresponse = context.getResponse();
/*  109 */       context.putDelivery("status", new String("start_gathering"));
/*  110 */       context.includeJSP("status.jsp", "hiddenFrame");
/*  111 */       sresponse.setContentType("text/plain");
/*  112 */       sresponse.flushBuffer();
/*      */     }
/*  114 */     catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  119 */     Vector selections = MilestoneHelper.getSelectionsForReport(context);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  124 */     Vector multCompleteDates = getRptMultCompleteDates(selections);
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
/*  135 */     catch (Exception exception) {}
/*      */ 
/*      */     
/*  138 */     int DATA_FONT_SIZE = 7;
/*  139 */     int SMALL_HEADER_FONT_SIZE = 8;
/*  140 */     int NUM_COLUMNS = 16;
/*  141 */     Color SHADED_AREA_COLOR = Color.lightGray;
/*      */     
/*  143 */     SectionBand hbandType = new SectionBand(report);
/*  144 */     SectionBand hbandCategory = new SectionBand(report);
/*  145 */     SectionBand body = new SectionBand(report);
/*  146 */     SectionBand footer = new SectionBand(report);
/*  147 */     SectionBand spacer = new SectionBand(report);
/*  148 */     DefaultSectionLens group = null;
/*      */     
/*  150 */     footer.setVisible(true);
/*  151 */     footer.setHeight(0.1F);
/*  152 */     footer.setShrinkToFit(false);
/*  153 */     footer.setBottomBorder(0);
/*      */     
/*  155 */     spacer.setVisible(true);
/*  156 */     spacer.setHeight(0.05F);
/*  157 */     spacer.setShrinkToFit(false);
/*  158 */     spacer.setBottomBorder(0);
/*      */ 
/*      */     
/*  161 */     Hashtable selTable = MilestoneHelper.buildFinalIDJAlternateSelections(selections);
/*  162 */     Enumeration types = selTable.keys();
/*  163 */     Vector typeVector = new Vector();
/*      */     
/*  165 */     while (types.hasMoreElements()) {
/*  166 */       typeVector.addElement(types.nextElement());
/*      */     }
/*  168 */     int numConfigs = typeVector.size();
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  173 */       Vector sortedTypeVector = typeVector;
/*      */ 
/*      */       
/*  176 */       DefaultTableLens table_contents = null;
/*  177 */       DefaultTableLens rowCountTable = null;
/*  178 */       DefaultTableLens columnHeaderTable = null;
/*  179 */       DefaultTableLens subTable = null;
/*  180 */       DefaultTableLens dateArtistTableLens = null;
/*      */       
/*  182 */       rowCountTable = new DefaultTableLens(2, 10000);
/*      */       
/*  184 */       int totalCount = 0;
/*  185 */       int tenth = 1;
/*      */       
/*  187 */       Vector updatedTypeVector = new Vector();
/*  188 */       updatedTypeVector.add(0, "Product");
/*  189 */       updatedTypeVector.add(1, "TBS");
/*      */ 
/*      */       
/*  192 */       for (int n = 0; n < updatedTypeVector.size(); n++) {
/*      */         
/*  194 */         String typeC = (updatedTypeVector.elementAt(n) != null) ? (String)updatedTypeVector.elementAt(n) : "";
/*  195 */         Hashtable typeTableC = (Hashtable)selTable.get(typeC);
/*      */         
/*  197 */         totalCount++;
/*  198 */         Enumeration typeTableCEnum = typeTableC.keys();
/*      */         
/*  200 */         Vector SelectionsVectorC = new Vector();
/*      */         
/*  202 */         while (typeTableCEnum.hasMoreElements()) {
/*  203 */           SelectionsVectorC.add((String)typeTableCEnum.nextElement());
/*  204 */           Object[] SelectionsArrayC = (Object[])null;
/*  205 */           SelectionsArrayC = SelectionsVectorC.toArray();
/*  206 */           totalCount += SelectionsArrayC.length;
/*      */         } 
/*      */       } 
/*      */       
/*  210 */       tenth = (totalCount > 10) ? (totalCount / 10) : 1;
/*      */       
/*  212 */       HttpServletResponse sresponse = context.getResponse();
/*  213 */       context.putDelivery("status", new String("start_report"));
/*  214 */       context.putDelivery("percent", new String("20"));
/*  215 */       context.includeJSP("status.jsp", "hiddenFrame");
/*  216 */       sresponse.setContentType("text/plain");
/*  217 */       sresponse.flushBuffer();
/*      */       
/*  219 */       int recordCount = 0;
/*  220 */       int count = 0;
/*      */       
/*  222 */       for (int n = 0; n < updatedTypeVector.size(); n++)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  228 */         Form reportForm = (Form)context.getSessionValue("reportForm");
/*      */         
/*  230 */         Calendar beginStDate = (reportForm.getStringValue("beginDate") != null && 
/*  231 */           reportForm.getStringValue("beginDate").length() > 0) ? 
/*  232 */           MilestoneHelper.getDate(reportForm.getStringValue("beginDate")) : null;
/*      */         
/*  234 */         Calendar endStDate = (reportForm.getStringValue("endDate") != null && 
/*  235 */           reportForm.getStringValue("endDate").length() > 0) ? 
/*  236 */           MilestoneHelper.getDate(reportForm.getStringValue("endDate")) : null;
/*      */         
/*  238 */         report.setElement("crs_startdate", MilestoneHelper.getFormatedDate(beginStDate));
/*  239 */         report.setElement("crs_enddate", MilestoneHelper.getFormatedDate(endStDate));
/*      */         
/*  241 */         SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
/*  242 */         String todayLong = formatter.format(new Date());
/*  243 */         report.setElement("crs_bottomdate", todayLong);
/*      */         
/*  245 */         String type = (updatedTypeVector.elementAt(n) != null) ? (String)updatedTypeVector.elementAt(n) : "";
/*      */         
/*  247 */         Hashtable typeTable = (Hashtable)selTable.get(type);
/*      */ 
/*      */         
/*  250 */         int numTypes = 0;
/*  251 */         int numDates = 0;
/*  252 */         int numSelections = 0;
/*      */         
/*  254 */         if (typeTable != null) {
/*      */           
/*  256 */           Enumeration typeEnum = typeTable.keys();
/*  257 */           while (typeEnum.hasMoreElements()) {
/*      */             
/*  259 */             String typeName = (String)typeEnum.nextElement();
/*  260 */             numTypes++;
/*  261 */             Vector selectionSet = (Vector)typeTable.get(typeName);
/*  262 */             if (selectionSet != null)
/*      */             {
/*  264 */               numSelections += selectionSet.size();
/*      */             }
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/*  270 */         int numRows = 0;
/*      */ 
/*      */         
/*  273 */         numRows += numTypes * 3;
/*  274 */         numRows += numDates * 2;
/*  275 */         numRows += numSelections * 2;
/*      */         
/*  277 */         numRows += 5;
/*      */ 
/*      */         
/*  280 */         hbandType = new SectionBand(report);
/*  281 */         hbandType.setHeight(1.06F);
/*  282 */         hbandType.setShrinkToFit(true);
/*  283 */         hbandType.setVisible(true);
/*      */         
/*  285 */         table_contents = new DefaultTableLens(1, 16);
/*      */ 
/*      */         
/*  288 */         table_contents.setHeaderRowCount(0);
/*  289 */         table_contents.setColWidth(0, 70);
/*  290 */         table_contents.setColWidth(1, 365);
/*  291 */         table_contents.setColWidth(2, 200);
/*  292 */         table_contents.setColWidth(3, 160);
/*  293 */         table_contents.setColWidth(4, 95);
/*  294 */         table_contents.setColWidth(5, 80);
/*  295 */         table_contents.setColWidth(6, 80);
/*  296 */         table_contents.setColWidth(7, 80);
/*  297 */         table_contents.setColWidth(8, 80);
/*  298 */         table_contents.setColWidth(9, 80);
/*  299 */         table_contents.setColWidth(10, 80);
/*  300 */         table_contents.setColWidth(11, 80);
/*  301 */         table_contents.setColWidth(12, 2);
/*  302 */         table_contents.setColWidth(13, 80);
/*  303 */         table_contents.setColWidth(14, 80);
/*  304 */         table_contents.setColWidth(15, 80);
/*  305 */         table_contents.setColBorderColor(Color.black);
/*  306 */         table_contents.setRowBorderColor(Color.black);
/*  307 */         table_contents.setRowBorder(4097);
/*  308 */         table_contents.setColBorder(4097);
/*      */ 
/*      */         
/*  311 */         int nextRow = 0;
/*      */         
/*  313 */         String typeHeaderText = !type.trim().equals("") ? type : "Other";
/*      */ 
/*      */         
/*  316 */         table_contents.setSpan(nextRow, 0, new Dimension(16, 1));
/*  317 */         table_contents.setAlignment(nextRow, 0, 2);
/*      */         
/*  319 */         table_contents.setObject(nextRow, 0, typeHeaderText);
/*  320 */         table_contents.setRowHeight(nextRow, 16);
/*      */         
/*  322 */         table_contents.setRowBorderColor(nextRow, Color.black);
/*  323 */         table_contents.setRowBorder(nextRow, 0, 266240);
/*      */         
/*  325 */         table_contents.setRowFont(nextRow, new Font("Arial", 3, 12));
/*  326 */         table_contents.setRowBackground(nextRow, Color.white);
/*  327 */         table_contents.setRowForeground(nextRow, Color.black);
/*      */         
/*  329 */         table_contents.setRowBorder(nextRow - 1, 266240);
/*  330 */         table_contents.setColBorder(nextRow, -1, 266240);
/*  331 */         table_contents.setColBorder(nextRow, 0, 266240);
/*  332 */         table_contents.setColBorder(nextRow, 15, 266240);
/*  333 */         table_contents.setRowBorder(nextRow, 266240);
/*      */         
/*  335 */         table_contents.setRowBorderColor(nextRow - 1, Color.black);
/*  336 */         table_contents.setColBorderColor(nextRow, -1, Color.black);
/*  337 */         table_contents.setColBorderColor(nextRow, 0, Color.black);
/*  338 */         table_contents.setColBorderColor(nextRow, 15, Color.black);
/*  339 */         table_contents.setRowBorderColor(nextRow, Color.black);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  344 */         nextRow = 0;
/*      */         
/*  346 */         columnHeaderTable = new DefaultTableLens(3, 16);
/*      */         
/*  348 */         columnHeaderTable.setHeaderRowCount(0);
/*  349 */         columnHeaderTable.setColWidth(0, 70);
/*  350 */         columnHeaderTable.setColWidth(1, 365);
/*  351 */         columnHeaderTable.setColWidth(2, 200);
/*  352 */         columnHeaderTable.setColWidth(3, 160);
/*  353 */         columnHeaderTable.setColWidth(4, 95);
/*  354 */         columnHeaderTable.setColWidth(5, 80);
/*  355 */         columnHeaderTable.setColWidth(6, 80);
/*  356 */         columnHeaderTable.setColWidth(7, 80);
/*  357 */         columnHeaderTable.setColWidth(8, 80);
/*  358 */         columnHeaderTable.setColWidth(9, 80);
/*  359 */         columnHeaderTable.setColWidth(10, 80);
/*  360 */         columnHeaderTable.setColWidth(11, 80);
/*  361 */         columnHeaderTable.setColWidth(12, 2);
/*  362 */         columnHeaderTable.setColWidth(13, 80);
/*  363 */         columnHeaderTable.setColWidth(14, 80);
/*  364 */         columnHeaderTable.setColWidth(15, 80);
/*      */         
/*  366 */         columnHeaderTable.setAlignment(nextRow, 0, 33);
/*  367 */         columnHeaderTable.setAlignment(nextRow, 1, 33);
/*  368 */         columnHeaderTable.setAlignment(nextRow, 2, 34);
/*  369 */         columnHeaderTable.setAlignment(nextRow, 3, 34);
/*  370 */         columnHeaderTable.setAlignment(nextRow, 4, 34);
/*  371 */         columnHeaderTable.setAlignment(nextRow, 5, 33);
/*  372 */         columnHeaderTable.setAlignment(nextRow, 6, 33);
/*  373 */         columnHeaderTable.setAlignment(nextRow, 7, 33);
/*  374 */         columnHeaderTable.setAlignment(nextRow, 8, 33);
/*  375 */         columnHeaderTable.setAlignment(nextRow, 9, 33);
/*  376 */         columnHeaderTable.setAlignment(nextRow, 10, 33);
/*  377 */         columnHeaderTable.setAlignment(nextRow, 11, 33);
/*  378 */         columnHeaderTable.setAlignment(nextRow, 12, 33);
/*  379 */         columnHeaderTable.setAlignment(nextRow, 13, 33);
/*  380 */         columnHeaderTable.setAlignment(nextRow, 14, 33);
/*  381 */         columnHeaderTable.setAlignment(nextRow, 15, 33);
/*      */         
/*  383 */         columnHeaderTable.setSpan(nextRow, 0, new Dimension(1, 2));
/*  384 */         columnHeaderTable.setObject(nextRow, 0, "Release\nDate");
/*  385 */         columnHeaderTable.setSpan(nextRow, 1, new Dimension(1, 2));
/*  386 */         columnHeaderTable.setObject(nextRow, 1, "Artist\nTitle\nLabel\nContacts");
/*  387 */         columnHeaderTable.setSpan(nextRow, 2, new Dimension(1, 2));
/*  388 */         columnHeaderTable.setObject(nextRow, 2, "Packaging\nSpecs");
/*  389 */         columnHeaderTable.setSpan(nextRow, 3, new Dimension(1, 2));
/*  390 */         columnHeaderTable.setObject(nextRow, 3, "UPC\nLocal Prod #\nConfig\nImpact Date");
/*  391 */         columnHeaderTable.setSpan(nextRow, 4, new Dimension(1, 2));
/*  392 */         columnHeaderTable.setObject(nextRow, 4, "Price\nCode\nUnits");
/*      */ 
/*      */         
/*  395 */         columnHeaderTable.setRowHeight(nextRow, 10);
/*  396 */         columnHeaderTable.setRowHeight(nextRow + 1, 27);
/*      */         
/*  398 */         columnHeaderTable.setSpan(nextRow, 5, new Dimension(7, 1));
/*  399 */         columnHeaderTable.setObject(nextRow, 5, "PHYSICAL PRODUCTS");
/*  400 */         columnHeaderTable.setAlignment(nextRow, 5, 2);
/*  401 */         columnHeaderTable.setSpan(nextRow, 13, new Dimension(3, 1));
/*  402 */         columnHeaderTable.setObject(nextRow, 13, "DIGITAL PRODUCTS");
/*  403 */         columnHeaderTable.setAlignment(nextRow, 13, 2);
/*      */         
/*  405 */         columnHeaderTable.setObject(nextRow + 1, 5, "Prod\nReq\nDue");
/*  406 */         columnHeaderTable.setObject(nextRow + 1, 6, "Sol\nFilm\nDue");
/*  407 */         columnHeaderTable.setObject(nextRow + 1, 7, "To Seps");
/*  408 */         columnHeaderTable.setObject(nextRow + 1, 8, "Film\nShips");
/*  409 */         columnHeaderTable.setObject(nextRow + 1, 9, "Signed\nProd\nReqs");
/*  410 */         columnHeaderTable.setObject(nextRow + 1, 10, "Master\nShips");
/*  411 */         columnHeaderTable.setObject(nextRow + 1, 11, "Tests\nApprv");
/*  412 */         columnHeaderTable.setObject(nextRow + 1, 13, "BA\nApprv");
/*  413 */         columnHeaderTable.setObject(nextRow + 1, 14, "Graphics\nDue");
/*  414 */         columnHeaderTable.setObject(nextRow + 1, 15, "Audio\nDue");
/*      */         
/*  416 */         columnHeaderTable.setAlignment(nextRow + 1, 5, 34);
/*  417 */         columnHeaderTable.setAlignment(nextRow + 1, 6, 34);
/*  418 */         columnHeaderTable.setAlignment(nextRow + 1, 7, 34);
/*  419 */         columnHeaderTable.setAlignment(nextRow + 1, 8, 34);
/*  420 */         columnHeaderTable.setAlignment(nextRow + 1, 9, 34);
/*  421 */         columnHeaderTable.setAlignment(nextRow + 1, 10, 34);
/*  422 */         columnHeaderTable.setAlignment(nextRow + 1, 11, 34);
/*  423 */         columnHeaderTable.setAlignment(nextRow + 1, 12, 34);
/*  424 */         columnHeaderTable.setAlignment(nextRow + 1, 13, 34);
/*  425 */         columnHeaderTable.setAlignment(nextRow + 1, 14, 34);
/*  426 */         columnHeaderTable.setAlignment(nextRow + 1, 15, 34);
/*      */         
/*  428 */         setColBorderColor(columnHeaderTable, nextRow, 16, SHADED_AREA_COLOR);
/*  429 */         setColBorderColor(columnHeaderTable, nextRow + 1, 16, SHADED_AREA_COLOR);
/*      */         
/*  431 */         columnHeaderTable.setRowBorderColor(nextRow - 1, SHADED_AREA_COLOR);
/*  432 */         columnHeaderTable.setRowBorderColor(nextRow, SHADED_AREA_COLOR);
/*  433 */         columnHeaderTable.setRowBorderColor(nextRow + 1, SHADED_AREA_COLOR);
/*      */         
/*  435 */         columnHeaderTable.setRowFont(nextRow, new Font("Arial", 1, 7));
/*  436 */         columnHeaderTable.setRowFont(nextRow + 1, new Font("Arial", 1, 7));
/*      */         
/*  438 */         columnHeaderTable.setRowBackground(nextRow, Color.white);
/*  439 */         columnHeaderTable.setRowForeground(nextRow, Color.black);
/*      */         
/*  441 */         hbandType.addTable(columnHeaderTable, new Rectangle(0, 24, 800, 50));
/*      */ 
/*      */         
/*  444 */         DefaultTableLens blank_table_contents = null;
/*  445 */         blank_table_contents = new DefaultTableLens(1, 16);
/*  446 */         blank_table_contents.setHeaderRowCount(0);
/*      */         
/*  448 */         blank_table_contents.setColBorderColor(Color.white);
/*  449 */         blank_table_contents.setRowBorderColor(Color.white);
/*      */         
/*  451 */         nextRow = 0;
/*      */         
/*  453 */         blank_table_contents.setSpan(nextRow, 0, new Dimension(16, 1));
/*  454 */         blank_table_contents.setAlignment(nextRow, 0, 2);
/*  455 */         blank_table_contents.setRowHeight(nextRow, 1);
/*  456 */         blank_table_contents.setRowBorderColor(nextRow, Color.white);
/*  457 */         blank_table_contents.setRowBorder(nextRow, 0, 266240);
/*  458 */         blank_table_contents.setRowFont(nextRow, new Font("Arial", 3, 12));
/*  459 */         blank_table_contents.setRowBackground(nextRow, Color.white);
/*  460 */         blank_table_contents.setRowForeground(nextRow, Color.white);
/*  461 */         blank_table_contents.setRowBorder(nextRow - 1, 266240);
/*  462 */         blank_table_contents.setColBorder(nextRow, -1, 266240);
/*  463 */         blank_table_contents.setColBorder(nextRow, 0, 266240);
/*  464 */         blank_table_contents.setColBorder(nextRow, 15, 266240);
/*  465 */         blank_table_contents.setRowBorder(nextRow, 266240);
/*  466 */         blank_table_contents.setRowBorderColor(nextRow - 1, Color.white);
/*  467 */         blank_table_contents.setColBorderColor(nextRow, -1, Color.white);
/*  468 */         blank_table_contents.setColBorderColor(nextRow, 0, Color.white);
/*  469 */         blank_table_contents.setColBorderColor(nextRow, 15, Color.white);
/*  470 */         blank_table_contents.setRowBorderColor(nextRow, Color.white);
/*      */ 
/*      */         
/*  473 */         hbandType.addTable(blank_table_contents, new Rectangle(0, 75, 800, 15));
/*      */         
/*  475 */         hbandType.setBottomBorder(0);
/*      */ 
/*      */         
/*  478 */         if (typeTable != null) {
/*      */           
/*  480 */           Enumeration typesEnum = typeTable.keys();
/*      */           
/*  482 */           Vector currentTypeVector = new Vector();
/*      */           
/*  484 */           while (typesEnum.hasMoreElements()) {
/*  485 */             currentTypeVector.add((String)typesEnum.nextElement());
/*      */           }
/*      */           
/*  488 */           Collections.sort(currentTypeVector, new IDJReportDateArtistComparator());
/*      */           
/*  490 */           for (int x = 0; x < currentTypeVector.size(); x++) {
/*      */ 
/*      */ 
/*      */             
/*  494 */             String typeName = (String)currentTypeVector.elementAt(x);
/*  495 */             dateArtistTableLens = new DefaultTableLens(1, 16);
/*      */             
/*  497 */             nextRow = 0;
/*      */ 
/*      */ 
/*      */             
/*  501 */             selections = (Vector)typeTable.get(typeName);
/*      */ 
/*      */ 
/*      */             
/*  505 */             if (typeName.startsWith("TBS")) {
/*  506 */               Collections.sort(selections, new IDJExceptionsComparator());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             }
/*      */             else {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  521 */               Collections.sort(selections, new IDJSelectionSortComparator());
/*      */             } 
/*      */             
/*  524 */             nextRow = 0;
/*      */             
/*  526 */             if (selections == null) {
/*  527 */               selections = new Vector();
/*      */             }
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  533 */             for (int i = 0; i < selections.size(); i++) {
/*      */               Calendar streetCal;
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
/*  555 */               String titleId = "";
/*  556 */               titleId = sel.getTitleID();
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  561 */               String artist = "";
/*  562 */               artist = sel.getFlArtist().trim();
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  567 */               String comment = "";
/*  568 */               String commentStr = (sel.getSelectionComments() != null) ? sel.getSelectionComments() : "";
/*  569 */               String newComment = removeLF(commentStr, 800);
/*  570 */               int subTableRows = 3;
/*  571 */               if (newComment.length() > 0) {
/*  572 */                 subTableRows = 4;
/*      */               }
/*      */ 
/*      */ 
/*      */               
/*  577 */               String labelCell = "";
/*  578 */               labelCell = sel.getImprint();
/*      */ 
/*      */ 
/*      */               
/*  582 */               String pack = "";
/*  583 */               pack = sel.getSelectionPackaging();
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  588 */               String title = "";
/*  589 */               if (sel.getTitle() != null) {
/*  590 */                 title = sel.getTitle();
/*      */               }
/*      */               
/*  593 */               String upc = "";
/*  594 */               upc = (sel.getUpc() != null) ? sel.getUpc() : "";
/*      */ 
/*      */               
/*  597 */               upc = MilestoneHelper_2.getRMSReportFormat(upc, "UPC", sel.getIsDigital());
/*      */ 
/*      */               
/*  600 */               String localProductNumber = "";
/*  601 */               if (sel.getPrefixID() != null && sel.getPrefixID().getAbbreviation() != null)
/*  602 */                 localProductNumber = sel.getPrefixID().getAbbreviation(); 
/*  603 */               localProductNumber = String.valueOf(localProductNumber) + sel.getSelectionNo();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  609 */               String selConfig = "";
/*  610 */               selConfig = (sel.getSelectionConfig() != null) ? sel.getSelectionConfig().getSelectionConfigurationAbbreviation() : "";
/*      */               
/*  612 */               String selSubConfig = "";
/*  613 */               selSubConfig = (sel.getSelectionSubConfig() != null) ? sel.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation() : "";
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  618 */               String units = "";
/*  619 */               units = (sel.getNumberOfUnits() > 0) ? String.valueOf(sel.getNumberOfUnits()) : "";
/*      */               
/*  621 */               String code = (sel.getSellCode() != null) ? sel.getSellCode() : "";
/*  622 */               if (code != null && code.startsWith("-1")) {
/*  623 */                 code = "";
/*      */               }
/*  625 */               String retail = "";
/*  626 */               if (sel.getPriceCode() != null && sel.getPriceCode().getRetailCode() != null) {
/*  627 */                 retail = sel.getPriceCode().getRetailCode();
/*      */               }
/*  629 */               if (code.length() > 0) {
/*  630 */                 retail = "/" + retail;
/*      */               }
/*  632 */               String price = "";
/*  633 */               if (sel.getPriceCode() != null && sel.getPriceCode().getTotalCost() > 0.0F) {
/*  634 */                 price = "$" + MilestoneHelper.formatDollarPrice(sel.getPriceCode().getTotalCost());
/*      */               }
/*      */ 
/*      */ 
/*      */               
/*  639 */               String contact = "";
/*  640 */               contact = (sel.getLabelContact() != null) ? sel.getLabelContact().getName() : "";
/*      */ 
/*      */               
/*  643 */               String otherContact = "";
/*  644 */               otherContact = (sel.getOtherContact() != null) ? sel.getOtherContact() : "";
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  649 */               Schedule schedule = sel.getSchedule();
/*  650 */               Vector tasks = (schedule != null) ? schedule.getTasks() : null;
/*  651 */               ScheduledTask task = null;
/*      */ 
/*      */ 
/*      */               
/*  655 */               String PRQD = "N/A";
/*  656 */               String SFD = "N/A";
/*  657 */               String PTS = "N/A";
/*  658 */               String TPS = "N/A";
/*  659 */               String TA = "N/A";
/*  660 */               String PRQDcom = "";
/*  661 */               String SFDcom = "";
/*  662 */               String PTScom = "";
/*  663 */               String TPScom = "";
/*  664 */               String TAcom = "";
/*  665 */               String SEPS = "N/A";
/*  666 */               String SPR = "N/A";
/*      */ 
/*      */ 
/*      */               
/*  670 */               String BAS = "N/A";
/*  671 */               String GRA = "N/A";
/*  672 */               String WAV = "N/A";
/*  673 */               String PFM = "N/A";
/*  674 */               String BAScom = "";
/*  675 */               String GRAcom = "";
/*  676 */               String WAVcom = "";
/*  677 */               String PFMcom = "";
/*  678 */               String SEPScom = "";
/*  679 */               String SPRcom = "";
/*      */               
/*  681 */               boolean hasSFD = false;
/*      */               
/*  683 */               if (tasks != null)
/*      */               {
/*  685 */                 for (int j = 0; j < tasks.size(); j++) {
/*      */                   
/*  687 */                   task = (ScheduledTask)tasks.get(j);
/*      */                   
/*  689 */                   if (task != null) {
/*      */                     
/*  691 */                     streetCal = new SimpleDateFormat("M/d");
/*      */                     
/*  693 */                     String dueDate = "";
/*  694 */                     if (task.getDueDate() != null)
/*      */                     {
/*      */                       
/*  697 */                       dueDate = String.valueOf(streetCal.format(task.getDueDate().getTime())) + 
/*  698 */                         " " + MilestoneHelper.getDayType(sel.getCalendarGroup(), task.getDueDate());
/*      */                     }
/*      */                     
/*  701 */                     String completionDate = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*  702 */                     if (task.getScheduledTaskStatus().equals("N/A"))
/*      */                     {
/*  704 */                       completionDate = task.getScheduledTaskStatus();
/*      */                     }
/*      */                     
/*  707 */                     completionDate = String.valueOf(completionDate) + "\n";
/*      */                     
/*  709 */                     String taskAbbrev = MilestoneHelper.getTaskAbbreivationNameById(task.getTaskAbbreviationID());
/*  710 */                     String taskComment = "";
/*      */                     
/*  712 */                     if (taskAbbrev.equalsIgnoreCase("PRQD")) {
/*      */                       
/*  714 */                       PRQD = dueDate;
/*  715 */                       completionDate = String.valueOf(completionDate) + getTaskMultCompleteDates(task.getScheduledTaskID(), sel.getSelectionID(), multCompleteDates);
/*  716 */                       PRQDcom = String.valueOf(completionDate) + taskComment;
/*      */                     
/*      */                     }
/*  719 */                     else if (taskAbbrev.equalsIgnoreCase("SFD")) {
/*      */                       
/*  721 */                       SFD = dueDate;
/*  722 */                       SFDcom = String.valueOf(completionDate) + taskComment;
/*      */                       
/*  724 */                       hasSFD = true;
/*      */                     
/*      */                     }
/*  727 */                     else if (taskAbbrev.equalsIgnoreCase("PFS")) {
/*      */                       
/*  729 */                       PTS = dueDate;
/*  730 */                       completionDate = String.valueOf(completionDate) + getTaskMultCompleteDates(task.getScheduledTaskID(), sel.getSelectionID(), multCompleteDates);
/*  731 */                       PTScom = String.valueOf(completionDate) + taskComment;
/*      */                     }
/*  733 */                     else if (taskAbbrev.equalsIgnoreCase("TPS")) {
/*      */                       
/*  735 */                       TPS = dueDate;
/*  736 */                       completionDate = String.valueOf(completionDate) + getTaskMultCompleteDates(task.getScheduledTaskID(), sel.getSelectionID(), multCompleteDates);
/*  737 */                       TPScom = String.valueOf(completionDate) + taskComment;
/*      */                     
/*      */                     }
/*  740 */                     else if (taskAbbrev.equalsIgnoreCase("TA")) {
/*      */                       
/*  742 */                       TA = dueDate;
/*  743 */                       TAcom = String.valueOf(completionDate) + taskComment;
/*      */                     
/*      */                     }
/*  746 */                     else if (taskAbbrev.equalsIgnoreCase("BAS")) {
/*      */                       
/*  748 */                       BAS = dueDate;
/*  749 */                       BAScom = String.valueOf(completionDate) + taskComment;
/*      */                     
/*      */                     }
/*  752 */                     else if (taskAbbrev.equalsIgnoreCase("GRA")) {
/*      */                       
/*  754 */                       GRA = dueDate;
/*  755 */                       completionDate = String.valueOf(completionDate) + getTaskMultCompleteDates(task.getScheduledTaskID(), sel.getSelectionID(), multCompleteDates);
/*  756 */                       GRAcom = String.valueOf(completionDate) + taskComment;
/*      */                     }
/*  758 */                     else if (taskAbbrev.equalsIgnoreCase("WAV")) {
/*      */                       
/*  760 */                       WAV = dueDate;
/*  761 */                       completionDate = String.valueOf(completionDate) + getTaskMultCompleteDates(task.getScheduledTaskID(), sel.getSelectionID(), multCompleteDates);
/*  762 */                       WAVcom = String.valueOf(completionDate) + taskComment;
/*      */                     }
/*  764 */                     else if (taskAbbrev.equalsIgnoreCase("PFM")) {
/*      */                       
/*  766 */                       PFM = dueDate;
/*  767 */                       completionDate = String.valueOf(completionDate) + 
/*  768 */                         getTaskMultCompleteDates(task.getScheduledTaskID(), 
/*  769 */                           sel.getSelectionID(), multCompleteDates);
/*  770 */                       PFMcom = String.valueOf(completionDate) + taskComment;
/*      */                     }
/*  772 */                     else if (taskAbbrev.equalsIgnoreCase("SEPS")) {
/*      */                       
/*  774 */                       SEPS = dueDate;
/*  775 */                       completionDate = String.valueOf(completionDate) + 
/*  776 */                         getTaskMultCompleteDates(task.getScheduledTaskID(), 
/*  777 */                           sel.getSelectionID(), multCompleteDates);
/*  778 */                       SEPScom = String.valueOf(completionDate) + taskComment;
/*      */                     
/*      */                     }
/*  781 */                     else if (taskAbbrev.equalsIgnoreCase("SPR")) {
/*  782 */                       SPR = dueDate;
/*  783 */                       completionDate = String.valueOf(completionDate) + 
/*  784 */                         getTaskMultCompleteDates(task.getScheduledTaskID(), 
/*  785 */                           sel.getSelectionID(), multCompleteDates);
/*  786 */                       SPRcom = String.valueOf(completionDate) + taskComment;
/*      */                     } 
/*      */                     
/*  789 */                     task = null;
/*      */                   } 
/*      */                 } 
/*      */               }
/*      */ 
/*      */ 
/*      */               
/*  796 */               nextRow = 0;
/*      */               
/*  798 */               if (i == 0) {
/*  799 */                 subTable = new DefaultTableLens(subTableRows + 3, 16);
/*      */               } else {
/*  801 */                 subTable = new DefaultTableLens(subTableRows, 16);
/*      */               } 
/*      */ 
/*      */               
/*  805 */               subTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
/*      */               
/*  807 */               setColBorderColor(subTable, nextRow, 16, SHADED_AREA_COLOR);
/*  808 */               setColBorderColor(subTable, nextRow + 1, 16, SHADED_AREA_COLOR);
/*  809 */               setColBorderColor(subTable, nextRow + 2, 16, SHADED_AREA_COLOR);
/*  810 */               setColBorderColor(subTable, nextRow + 3, 16, SHADED_AREA_COLOR);
/*      */               
/*  812 */               if (i == 0) {
/*  813 */                 setColBorderColor(subTable, nextRow + 4, 16, SHADED_AREA_COLOR);
/*  814 */                 setColBorderColor(subTable, nextRow + 5, 16, SHADED_AREA_COLOR);
/*      */               } 
/*      */               
/*  817 */               subTable.setHeaderRowCount(0);
/*  818 */               subTable.setColWidth(0, 70);
/*  819 */               subTable.setColWidth(1, 365);
/*  820 */               subTable.setColWidth(2, 200);
/*  821 */               subTable.setColWidth(3, 160);
/*  822 */               subTable.setColWidth(4, 95);
/*  823 */               subTable.setColWidth(5, 80);
/*  824 */               subTable.setColWidth(6, 80);
/*  825 */               subTable.setColWidth(7, 80);
/*  826 */               subTable.setColWidth(8, 80);
/*  827 */               subTable.setColWidth(9, 80);
/*  828 */               subTable.setColWidth(10, 80);
/*  829 */               subTable.setColWidth(11, 80);
/*  830 */               subTable.setColWidth(12, 2);
/*  831 */               subTable.setColWidth(13, 80);
/*  832 */               subTable.setColWidth(14, 80);
/*  833 */               subTable.setColWidth(15, 80);
/*  834 */               subTable.setRowBorderColor(nextRow - 1, Color.lightGray);
/*      */ 
/*      */ 
/*      */               
/*  838 */               if (i == 0) {
/*      */ 
/*      */                 
/*  841 */                 subTable.setHeaderRowCount(0);
/*  842 */                 subTable.setSpan(nextRow, 0, new Dimension(16, 1));
/*  843 */                 subTable.setRowHeight(nextRow, 2);
/*  844 */                 subTable.setRowBackground(nextRow, Color.white);
/*  845 */                 subTable.setRowForeground(nextRow, Color.black);
/*  846 */                 subTable.setColBorderColor(nextRow, -1, Color.white);
/*  847 */                 subTable.setColBorderColor(nextRow, 15, Color.white);
/*  848 */                 subTable.setRowBorderColor(nextRow - 1, Color.white);
/*  849 */                 subTable.setRowBorderColor(nextRow, SHADED_AREA_COLOR);
/*      */ 
/*      */                 
/*  852 */                 nextRow++;
/*      */ 
/*      */ 
/*      */                 
/*  856 */                 if (typeName.startsWith("TBS")) {
/*  857 */                   String tbsDate = "";
/*  858 */                   tbsDate = typeName.substring(6, typeName.lastIndexOf("*") - 1);
/*  859 */                   if (tbsDate.equals("NoDate")) {
/*  860 */                     streetCal = typeName.substring(typeName.lastIndexOf("*") + 2, typeName.length());
/*  861 */                     typeName = "TBS - " + streetCal;
/*      */                   } 
/*      */                 } 
/*      */ 
/*      */                 
/*  866 */                 typeName = typeName.replace('*', '-');
/*      */                 
/*  868 */                 subTable.setHeaderRowCount(0);
/*  869 */                 subTable.setSpan(nextRow, 0, new Dimension(16, 1));
/*  870 */                 subTable.setAlignment(nextRow, 0, 17);
/*  871 */                 subTable.setObject(nextRow, 0, typeName);
/*  872 */                 subTable.setRowHeight(nextRow, 15);
/*  873 */                 subTable.setRowFont(nextRow, new Font("Arial", 1, 12));
/*  874 */                 subTable.setRowBackground(nextRow, SHADED_AREA_COLOR);
/*  875 */                 subTable.setRowForeground(nextRow, Color.black);
/*  876 */                 subTable.setColBorderColor(nextRow, -1, SHADED_AREA_COLOR);
/*  877 */                 subTable.setColBorderColor(nextRow, 15, SHADED_AREA_COLOR);
/*  878 */                 subTable.setColBorderColor(nextRow - 1, 15, Color.white);
/*  879 */                 subTable.setRowBorderColor(nextRow - 1, SHADED_AREA_COLOR);
/*  880 */                 subTable.setRowBorderColor(nextRow, SHADED_AREA_COLOR);
/*      */                 
/*  882 */                 nextRow++;
/*      */ 
/*      */                 
/*  885 */                 subTable.setHeaderRowCount(0);
/*  886 */                 subTable.setSpan(nextRow, 0, new Dimension(16, 1));
/*  887 */                 subTable.setRowHeight(nextRow, 2);
/*  888 */                 subTable.setRowBackground(nextRow, Color.white);
/*  889 */                 subTable.setRowForeground(nextRow, Color.black);
/*  890 */                 subTable.setColBorderColor(nextRow, -1, Color.white);
/*  891 */                 subTable.setColBorderColor(nextRow, 15, Color.white);
/*  892 */                 subTable.setRowBorderColor(nextRow - 1, Color.white);
/*  893 */                 subTable.setRowBorderColor(nextRow, SHADED_AREA_COLOR);
/*      */ 
/*      */                 
/*  896 */                 nextRow++;
/*      */               } 
/*      */ 
/*      */ 
/*      */               
/*  901 */               String streetDate = "";
/*      */               
/*  903 */               if (sel.getIsDigital()) {
/*  904 */                 streetCal = sel.getDigitalRlsDate();
/*      */               } else {
/*  906 */                 streetCal = sel.getStreetDate();
/*      */               } 
/*  908 */               streetDate = MilestoneHelper.getFormatedDate(streetCal);
/*      */ 
/*      */               
/*  911 */               String status = (sel.getSelectionStatus() != null && sel.getSelectionStatus().getName() != null) ? 
/*  912 */                 sel.getSelectionStatus().getName() : "";
/*  913 */               if (status.equalsIgnoreCase("TBS")) {
/*  914 */                 streetDate = "TBS\n" + streetDate;
/*      */               }
/*      */               
/*  917 */               String radioImpactDate = "";
/*  918 */               radioImpactDate = MilestoneHelper.getFormatedDate(sel.getImpactDate());
/*      */               
/*  920 */               subTable.setObject(nextRow, 0, streetDate);
/*  921 */               subTable.setBackground(nextRow, 0, Color.white);
/*  922 */               subTable.setSpan(nextRow, 0, new Dimension(1, 3));
/*      */               
/*  924 */               subTable.setAlignment(nextRow, 0, 9);
/*  925 */               subTable.setFont(nextRow, 0, new Font("Arial", 1, 7));
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  930 */               boolean otherContactExists = false;
/*  931 */               if (!otherContact.equals("")) {
/*  932 */                 otherContactExists = true;
/*      */               }
/*  934 */               if (contact != null && !contact.equals("")) {
/*  935 */                 labelCell = String.valueOf(labelCell) + "\n" + contact;
/*      */               }
/*      */               
/*  938 */               if (otherContactExists) {
/*  939 */                 labelCell = String.valueOf(labelCell) + "\n" + otherContact;
/*      */               }
/*      */               
/*  942 */               String[] checkStrings = { comment, artist, title, (new String[5][3] = pack).valueOf(contact) + "/n" + otherContact };
/*  943 */               int[] checkStringsLength = { 20, 30, 30, 20, 25 };
/*  944 */               int extraLines = MilestoneHelper.lineCountWCR(checkStrings, checkStringsLength);
/*      */ 
/*      */ 
/*      */               
/*  948 */               subTable.setAlignment(nextRow, 1, 9);
/*      */               
/*  950 */               subTable.setAlignment(nextRow + 2, 1, 9);
/*  951 */               subTable.setFont(nextRow, 1, new Font("Arial", 1, 7));
/*      */               
/*  953 */               subTable.setFont(nextRow + 2, 1, new Font("Arial", 0, 7));
/*      */ 
/*      */               
/*  956 */               subTable.setSpan(nextRow, 1, new Dimension(1, 2));
/*  957 */               subTable.setObject(nextRow, 1, String.valueOf(artist) + "\n" + title);
/*      */ 
/*      */               
/*  960 */               subTable.setObject(nextRow + 2, 1, labelCell);
/*  961 */               subTable.setRowBorderColor(nextRow, 1, Color.white);
/*      */               
/*  963 */               subTable.setBackground(nextRow, 1, Color.white);
/*      */               
/*  965 */               subTable.setBackground(nextRow + 2, 1, Color.white);
/*      */ 
/*      */ 
/*      */               
/*  969 */               if (contact.equals("") && !otherContactExists && (radioImpactDate == null || radioImpactDate.equals(""))) {
/*  970 */                 subTable.setRowHeight(nextRow + 2, 8);
/*      */               }
/*      */               
/*  973 */               subTable.setRowBorderColor(nextRow, -1, SHADED_AREA_COLOR);
/*      */ 
/*      */               
/*  976 */               subTable.setObject(nextRow, 2, pack);
/*  977 */               subTable.setBackground(nextRow, 2, Color.white);
/*  978 */               subTable.setSpan(nextRow, 2, new Dimension(1, 3));
/*      */               
/*  980 */               subTable.setAlignment(nextRow, 2, 9);
/*  981 */               subTable.setFont(nextRow, 2, new Font("Arial", 0, 7));
/*      */               
/*  983 */               subTable.setAlignment(nextRow, 3, 10);
/*      */               
/*  985 */               subTable.setAlignment(nextRow + 2, 3, 10);
/*  986 */               subTable.setFont(nextRow, 3, new Font("Arial", 0, 7));
/*  987 */               subTable.setFont(nextRow + 1, 3, new Font("Arial", 0, 7));
/*  988 */               subTable.setFont(nextRow + 2, 3, new Font("Arial", 1, 7));
/*      */ 
/*      */               
/*  991 */               subTable.setSpan(nextRow, 3, new Dimension(1, 2));
/*  992 */               subTable.setObject(nextRow, 3, String.valueOf(upc) + "\n" + localProductNumber);
/*      */               
/*  994 */               subTable.setObject(nextRow + 2, 3, String.valueOf(selSubConfig) + "\n" + radioImpactDate);
/*  995 */               subTable.setRowBorderColor(nextRow, 3, Color.white);
/*      */               
/*  997 */               subTable.setBackground(nextRow, 3, Color.white);
/*      */               
/*  999 */               subTable.setBackground(nextRow + 2, 3, Color.white);
/*      */               
/* 1001 */               subTable.setObject(nextRow, 4, "Due Dates");
/* 1002 */               subTable.setColBorderColor(nextRow, 4, SHADED_AREA_COLOR);
/* 1003 */               subTable.setColBorder(nextRow, 4, 266240);
/* 1004 */               subTable.setFont(nextRow, 4, new Font("Arial", 1, 7));
/* 1005 */               subTable.setFont(nextRow + 1, 4, new Font("Arial", 0, 7));
/*      */               
/* 1007 */               subTable.setRowBorderColor(nextRow, SHADED_AREA_COLOR);
/*      */               
/* 1009 */               subTable.setSpan(nextRow + 1, 4, new Dimension(1, 2));
/* 1010 */               subTable.setObject(nextRow + 1, 4, String.valueOf(price) + "\n" + code + "\n" + units);
/*      */ 
/*      */               
/* 1013 */               subTable.setAlignment(nextRow + 1, 4, 10);
/*      */ 
/*      */               
/* 1016 */               subTable.setAlignment(nextRow, 6, 10);
/* 1017 */               subTable.setAlignment(nextRow + 1, 6, 10);
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1022 */               subTable.setRowHeight(nextRow, 8);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1028 */               subTable.setRowBorderColor(nextRow + 1, Color.white);
/*      */               
/* 1030 */               subTable.setObject(nextRow, 5, PRQD);
/* 1031 */               subTable.setColBorderColor(nextRow, 5, SHADED_AREA_COLOR);
/* 1032 */               subTable.setColBorder(nextRow, 5, 266240);
/*      */               
/* 1034 */               subTable.setObject(nextRow, 6, SFD);
/* 1035 */               subTable.setColBorderColor(nextRow, 6, SHADED_AREA_COLOR);
/* 1036 */               subTable.setColBorder(nextRow, 6, 266240);
/*      */               
/* 1038 */               subTable.setObject(nextRow, 7, SEPS);
/* 1039 */               subTable.setColBorderColor(nextRow, 7, SHADED_AREA_COLOR);
/* 1040 */               subTable.setColBorder(nextRow, 7, 266240);
/*      */               
/* 1042 */               subTable.setObject(nextRow, 8, PTS);
/* 1043 */               subTable.setColBorderColor(nextRow, 8, SHADED_AREA_COLOR);
/* 1044 */               subTable.setColBorder(nextRow, 8, 266240);
/*      */               
/* 1046 */               subTable.setObject(nextRow, 9, SPR);
/* 1047 */               subTable.setColBorderColor(nextRow, 9, SHADED_AREA_COLOR);
/* 1048 */               subTable.setColBorder(nextRow, 9, 266240);
/*      */               
/* 1050 */               subTable.setObject(nextRow, 10, TPS);
/* 1051 */               subTable.setColBorderColor(nextRow, 10, SHADED_AREA_COLOR);
/* 1052 */               subTable.setColBorder(nextRow, 10, 266240);
/*      */               
/* 1054 */               subTable.setObject(nextRow, 11, TA);
/* 1055 */               subTable.setColBorderColor(nextRow, 11, Color.lightGray);
/* 1056 */               subTable.setColBorder(nextRow, 11, 266240);
/*      */               
/* 1058 */               subTable.setObject(nextRow, 13, BAS);
/* 1059 */               subTable.setColBorderColor(nextRow, 13, Color.lightGray);
/* 1060 */               subTable.setColBorder(nextRow, 13, 266240);
/*      */               
/* 1062 */               subTable.setObject(nextRow, 14, GRA);
/* 1063 */               subTable.setColBorderColor(nextRow, 14, Color.lightGray);
/* 1064 */               subTable.setColBorder(nextRow, 14, 266240);
/*      */               
/* 1066 */               subTable.setObject(nextRow, 15, WAV);
/* 1067 */               subTable.setColBorderColor(nextRow, 15, Color.lightGray);
/* 1068 */               subTable.setColBorder(nextRow, 15, 266240);
/*      */               
/* 1070 */               subTable.setFont(nextRow, 5, new Font("Arial", 1, 7));
/* 1071 */               subTable.setFont(nextRow, 6, new Font("Arial", 1, 7));
/* 1072 */               subTable.setFont(nextRow, 7, new Font("Arial", 1, 7));
/* 1073 */               subTable.setFont(nextRow, 8, new Font("Arial", 1, 7));
/* 1074 */               subTable.setFont(nextRow, 9, new Font("Arial", 1, 7));
/* 1075 */               subTable.setFont(nextRow, 10, new Font("Arial", 1, 7));
/* 1076 */               subTable.setFont(nextRow, 11, new Font("Arial", 1, 7));
/* 1077 */               subTable.setFont(nextRow, 12, new Font("Arial", 1, 7));
/* 1078 */               subTable.setFont(nextRow, 13, new Font("Arial", 1, 7));
/* 1079 */               subTable.setFont(nextRow, 14, new Font("Arial", 1, 7));
/* 1080 */               subTable.setFont(nextRow, 15, new Font("Arial", 1, 7));
/*      */ 
/*      */ 
/*      */               
/* 1084 */               Font holidayFont = new Font("Arial", 3, 7);
/* 1085 */               for (int colIdx = 5; colIdx <= 15; colIdx++) {
/*      */ 
/*      */                 
/* 1088 */                 if (colIdx != 12) {
/* 1089 */                   String dueDate = subTable.getObject(nextRow, colIdx).toString();
/* 1090 */                   if (dueDate != null && dueDate.length() > 0) {
/* 1091 */                     char lastChar = dueDate.charAt(dueDate.length() - 1);
/* 1092 */                     if (Character.isLetter(lastChar)) {
/* 1093 */                       subTable.setFont(nextRow, colIdx, holidayFont);
/*      */                     }
/*      */                   } 
/*      */                 } 
/*      */               } 
/*      */ 
/*      */               
/* 1100 */               subTable.setAlignment(nextRow, 4, 2);
/* 1101 */               subTable.setAlignment(nextRow, 5, 2);
/* 1102 */               subTable.setAlignment(nextRow, 6, 2);
/* 1103 */               subTable.setAlignment(nextRow, 7, 2);
/* 1104 */               subTable.setAlignment(nextRow, 8, 2);
/* 1105 */               subTable.setAlignment(nextRow, 9, 2);
/* 1106 */               subTable.setAlignment(nextRow, 10, 2);
/* 1107 */               subTable.setAlignment(nextRow, 11, 2);
/* 1108 */               subTable.setAlignment(nextRow, 12, 2);
/* 1109 */               subTable.setAlignment(nextRow, 13, 2);
/* 1110 */               subTable.setAlignment(nextRow, 14, 2);
/* 1111 */               subTable.setAlignment(nextRow, 15, 2);
/*      */               
/* 1113 */               subTable.setObject(nextRow + 1, 5, PRQDcom);
/* 1114 */               subTable.setObject(nextRow + 1, 6, SFDcom);
/* 1115 */               subTable.setObject(nextRow + 1, 7, SEPScom);
/* 1116 */               subTable.setObject(nextRow + 1, 8, PTScom);
/* 1117 */               subTable.setObject(nextRow + 1, 9, SPRcom);
/* 1118 */               subTable.setObject(nextRow + 1, 10, TPScom);
/* 1119 */               subTable.setObject(nextRow + 1, 11, TAcom);
/* 1120 */               subTable.setObject(nextRow + 1, 13, BAScom);
/* 1121 */               subTable.setObject(nextRow + 1, 14, GRAcom);
/* 1122 */               subTable.setObject(nextRow + 1, 15, WAVcom);
/*      */               
/* 1124 */               subTable.setSpan(nextRow + 1, 5, new Dimension(1, 2));
/* 1125 */               subTable.setSpan(nextRow + 1, 6, new Dimension(1, 2));
/* 1126 */               subTable.setSpan(nextRow + 1, 7, new Dimension(1, 2));
/* 1127 */               subTable.setSpan(nextRow + 1, 8, new Dimension(1, 2));
/* 1128 */               subTable.setSpan(nextRow + 1, 9, new Dimension(1, 2));
/* 1129 */               subTable.setSpan(nextRow + 1, 10, new Dimension(1, 2));
/* 1130 */               subTable.setSpan(nextRow + 1, 11, new Dimension(1, 2));
/* 1131 */               subTable.setSpan(nextRow + 1, 12, new Dimension(1, 2));
/* 1132 */               subTable.setSpan(nextRow + 1, 13, new Dimension(1, 2));
/* 1133 */               subTable.setSpan(nextRow + 1, 14, new Dimension(1, 2));
/* 1134 */               subTable.setSpan(nextRow + 1, 15, new Dimension(1, 2));
/*      */ 
/*      */               
/* 1137 */               subTable.setAlignment(nextRow + 1, 4, 10);
/* 1138 */               subTable.setAlignment(nextRow + 1, 5, 10);
/* 1139 */               subTable.setAlignment(nextRow + 1, 6, 10);
/* 1140 */               subTable.setAlignment(nextRow + 1, 7, 10);
/* 1141 */               subTable.setAlignment(nextRow + 1, 8, 10);
/* 1142 */               subTable.setAlignment(nextRow + 1, 9, 10);
/* 1143 */               subTable.setAlignment(nextRow + 1, 10, 10);
/* 1144 */               subTable.setAlignment(nextRow + 1, 11, 10);
/* 1145 */               subTable.setAlignment(nextRow + 1, 13, 10);
/* 1146 */               subTable.setAlignment(nextRow + 1, 14, 10);
/* 1147 */               subTable.setAlignment(nextRow + 1, 15, 10);
/*      */               
/* 1149 */               subTable.setRowFont(nextRow + 1, new Font("Arial", 0, 7));
/*      */               
/* 1151 */               subTable.setRowBackground(nextRow, SHADED_AREA_COLOR);
/* 1152 */               subTable.setRowForeground(nextRow, Color.black);
/*      */               
/* 1154 */               subTable.setRowBorderColor(nextRow + 2, Color.lightGray);
/*      */ 
/*      */               
/* 1157 */               subTable.setRowFont(nextRow, new Font("Arial", 0, 7));
/*      */ 
/*      */               
/* 1160 */               if (newComment.length() > 0) {
/*      */                 
/* 1162 */                 nextRow++;
/*      */                 
/* 1164 */                 subTable.setRowBorderColor(nextRow + 2, Color.lightGray);
/*      */                 
/* 1166 */                 subTable.setRowFont(nextRow + 2, new Font("Arial", 3, 7));
/* 1167 */                 subTable.setSpan(nextRow + 2, 0, new Dimension(16, 1));
/* 1168 */                 subTable.setAlignment(nextRow + 2, 0, 9);
/* 1169 */                 subTable.setObject(nextRow + 2, 0, "Comments:   " + newComment);
/*      */                 
/* 1171 */                 subTable.setColBorderColor(nextRow + 2, -1, Color.lightGray);
/* 1172 */                 subTable.setColBorderColor(nextRow + 2, 15, Color.lightGray);
/*      */               } 
/*      */ 
/*      */               
/* 1176 */               body = new SectionBand(report);
/*      */ 
/*      */               
/* 1179 */               double lfLineCount = 1.0D;
/*      */ 
/*      */               
/* 1182 */               if (i == 0 && typeName.startsWith("TBS")) {
/* 1183 */                 lfLineCount = 1.3D;
/*      */               }
/*      */ 
/*      */               
/* 1187 */               if (newComment.length() > 0 && ((!contact.equals("") && contact != null) || otherContactExists)) {
/* 1188 */                 lfLineCount = 2.0D;
/*      */               
/*      */               }
/* 1191 */               else if ((!contact.equals("") && contact != null) || otherContactExists) {
/* 1192 */                 lfLineCount = 1.5D;
/*      */               
/*      */               }
/* 1195 */               else if (!otherContactExists && newComment.length() > 0) {
/* 1196 */                 lfLineCount = 1.3D;
/*      */               } 
/*      */               
/* 1199 */               body.setHeight((float)lfLineCount);
/*      */               
/* 1201 */               body.addTable(subTable, new Rectangle(800, 800));
/* 1202 */               body.setBottomBorder(0);
/* 1203 */               body.setTopBorder(0);
/* 1204 */               body.setShrinkToFit(true);
/* 1205 */               body.setVisible(true);
/* 1206 */               group = new DefaultSectionLens(null, group, body);
/*      */             } 
/*      */             
/* 1209 */             group = new DefaultSectionLens(null, group, spacer);
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1218 */         group = new DefaultSectionLens(hbandType, group, null);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1223 */         report.addSection(group, rowCountTable);
/* 1224 */         report.addPageBreak();
/* 1225 */         group = null;
/*      */ 
/*      */       
/*      */       }
/*      */ 
/*      */     
/*      */     }
/* 1232 */     catch (Exception e) {
/*      */       
/* 1234 */       System.out.println(">>>>>>>>IdjProductionScheduleForPrintSubHandlerAlternate(): exception: " + e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void setColBorderColor(DefaultTableLens table, int row, int columns, Color color) {
/* 1245 */     for (int i = -1; i < columns; i++)
/*      */     {
/* 1247 */       table.setColBorderColor(row, i, color);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1257 */   public static String removeLF(String theString, int maxChars) { return theString.replace('\n', ' '); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String getTaskMultCompleteDates(int taskID, int selectionID, Vector multCompleteDates) {
/* 1269 */     String completionDateList = "";
/* 1270 */     if (multCompleteDates != null) {
/*      */ 
/*      */       
/* 1273 */       int mcdCt = (multCompleteDates == null) ? 0 : multCompleteDates.size();
/*      */ 
/*      */       
/* 1276 */       boolean relTaskFound = false;
/* 1277 */       int i = 0;
/* 1278 */       while (!relTaskFound && i < mcdCt) {
/*      */         
/* 1280 */         MultCompleteDate mcd = (MultCompleteDate)multCompleteDates.get(i);
/* 1281 */         if (mcd.getReleaseID() == selectionID && mcd.getTaskID() == taskID) {
/* 1282 */           relTaskFound = true;
/*      */           continue;
/*      */         } 
/* 1285 */         i++;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1291 */       if (relTaskFound) {
/* 1292 */         boolean relTaskDone = false;
/* 1293 */         while (!relTaskDone && i < mcdCt) {
/* 1294 */           MultCompleteDate mcd = (MultCompleteDate)multCompleteDates.get(i);
/* 1295 */           if (mcd.getReleaseID() == selectionID && mcd.getTaskID() == taskID) {
/* 1296 */             completionDateList = String.valueOf(completionDateList) + MilestoneHelper.getFormatedDate(mcd.getCompletionDate()) + "\n";
/*      */           } else {
/*      */             
/* 1299 */             relTaskDone = true;
/*      */           } 
/* 1301 */           i++;
/*      */         } 
/*      */       } 
/*      */     } 
/* 1305 */     return completionDateList;
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
/*      */   private static Vector getRptMultCompleteDates(Vector selections) {
/* 1320 */     Vector multCompleteDates = null;
/*      */     
/* 1322 */     if (selections != null) {
/*      */       
/* 1324 */       multCompleteDates = new Vector();
/* 1325 */       StringBuffer sql = new StringBuffer();
/* 1326 */       Iterator it = selections.iterator();
/*      */       
/* 1328 */       sql.append("select * from MultCompleteDates with (nolock) where release_id in (");
/* 1329 */       while (it.hasNext()) {
/*      */         
/* 1331 */         sql.append(((Selection)it.next()).getSelectionID());
/* 1332 */         sql.append(", ");
/*      */       } 
/*      */ 
/*      */       
/* 1336 */       String query = String.valueOf(sql.substring(0, sql.length() - 2)) + ") order by release_id asc, task_id asc, order_no desc";
/*      */       
/* 1338 */       JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 1339 */       if (connector != null) {
/*      */         
/* 1341 */         connector.setForwardOnly(false);
/* 1342 */         connector.runQuery();
/* 1343 */         SimpleDateFormat adf = new SimpleDateFormat("M/d/yy");
/* 1344 */         while (connector.more()) {
/*      */           
/* 1346 */           MultCompleteDate mcd = new MultCompleteDate();
/* 1347 */           mcd.setReleaseID(connector.getInt("release_id"));
/* 1348 */           mcd.setTaskID(connector.getInt("task_id"));
/* 1349 */           mcd.setOrderNo(connector.getInt("order_no"));
/* 1350 */           mcd.setCompletionDate(MilestoneHelper.getDate(adf.format(connector.getDate("completion_date"))));
/* 1351 */           multCompleteDates.addElement(mcd);
/* 1352 */           connector.next();
/*      */         } 
/*      */         
/* 1355 */         connector.close();
/*      */       } 
/*      */     } 
/* 1358 */     return multCompleteDates;
/*      */   }
/*      */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\IdjProductionScheduleForPrintSubHandlerAlternate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */