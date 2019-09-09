/*      */ package WEB-INF.classes.com.universal.milestone;
/*      */ 
/*      */ import com.techempower.ComponentLog;
/*      */ import com.techempower.gemini.Context;
/*      */ import com.techempower.gemini.GeminiApplication;
/*      */ import com.universal.milestone.Form;
/*      */ import com.universal.milestone.JdbcConnector;
/*      */ import com.universal.milestone.MilestoneHelper;
/*      */ import com.universal.milestone.Plant;
/*      */ import com.universal.milestone.ReportHandler;
/*      */ import com.universal.milestone.SecureHandler;
/*      */ import com.universal.milestone.Selection;
/*      */ import com.universal.milestone.SelectionManager;
/*      */ import com.universal.milestone.StringComparator;
/*      */ import com.universal.milestone.UmlAddsMovesReportSubHandler;
/*      */ import inetsoft.report.Margin;
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
/*      */ public class UmlAddsMovesReportSubHandler
/*      */   extends SecureHandler
/*      */ {
/*      */   public static final String COMPONENT_CODE = "hUam";
/*      */   public GeminiApplication application;
/*      */   public ComponentLog log;
/*   58 */   public static int NUM_COLS = 15;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public UmlAddsMovesReportSubHandler(GeminiApplication application) {
/*   69 */     this.application = application;
/*   70 */     this.log = application.getLog("hUam");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   78 */   public String getDescription() { return "UML Adds and Moves Report"; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static void fillUmlAddsMovesReportForPrint(XStyleSheet report, Context context) {
/*   91 */     Color SHADED_AREA_COLOR = Color.lightGray;
/*   92 */     int COL_LINE_STYLE = 4097;
/*   93 */     int HEADER_FONT_SIZE = 12;
/*   94 */     int NUM_COLUMNS = 10;
/*      */     
/*   96 */     double ldLineVal = 0.3D;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  103 */     report.setMargin(new Margin(1.0D, 0.1D, 0.1D, 0.1D));
/*  104 */     report.setFooterFromEdge(0.3D);
/*  105 */     report.setHeaderFromEdge(0.1D);
/*      */     
/*  107 */     SectionBand hbandHeader = new SectionBand(report);
/*  108 */     SectionBand hbandMonth = new SectionBand(report);
/*  109 */     SectionBand hbandCategory = new SectionBand(report);
/*  110 */     SectionBand body = new SectionBand(report);
/*  111 */     SectionBand footer = new SectionBand(report);
/*  112 */     SectionBand spacer = new SectionBand(report);
/*      */     
/*  114 */     SectionBand hbandConfig = new SectionBand(report);
/*  115 */     DefaultSectionLens group = null;
/*      */     
/*  117 */     DefaultTableLens table_contents = null;
/*  118 */     DefaultTableLens columnHeaderTable = null;
/*  119 */     DefaultTableLens rowCountTable = null;
/*  120 */     DefaultTableLens subTable = null;
/*  121 */     DefaultTableLens theConfigTable = null;
/*  122 */     DefaultTableLens configTableLens = null;
/*      */     
/*  124 */     rowCountTable = new DefaultTableLens(2, 10000);
/*      */ 
/*      */     
/*  127 */     String theConfig = "";
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  132 */       HttpServletResponse sresponse = context.getResponse();
/*  133 */       context.putDelivery("status", new String("start_gathering"));
/*  134 */       context.includeJSP("status.jsp", "hiddenFrame");
/*  135 */       sresponse.setContentType("text/plain");
/*  136 */       sresponse.flushBuffer();
/*      */     }
/*  138 */     catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  147 */       HttpServletResponse sresponse = context.getResponse();
/*  148 */       context.putDelivery("status", new String("start_report"));
/*  149 */       context.putDelivery("percent", new String("10"));
/*  150 */       context.includeJSP("status.jsp", "hiddenFrame");
/*  151 */       sresponse.setContentType("text/plain");
/*  152 */       sresponse.flushBuffer();
/*      */     }
/*  154 */     catch (Exception exception) {}
/*      */ 
/*      */ 
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
/*  167 */       Form reportForm = (Form)context.getSessionValue("reportForm");
/*      */       
/*  169 */       Calendar beginStDate = (reportForm.getStringValue("beginEffectiveDate") != null && 
/*  170 */         reportForm.getStringValue("beginEffectiveDate").length() > 0) ? 
/*  171 */         MilestoneHelper.getDate(reportForm.getStringValue("beginEffectiveDate")) : null;
/*      */       
/*  173 */       Calendar endStDate = (reportForm.getStringValue("endEffectiveDate") != null && 
/*  174 */         reportForm.getStringValue("endEffectiveDate").length() > 0) ? 
/*  175 */         MilestoneHelper.getDate(reportForm.getStringValue("endEffectiveDate")) : null;
/*      */       
/*  177 */       report.setElement("crs_startdate", MilestoneHelper.getFormatedDate(beginStDate));
/*  178 */       report.setElement("crs_enddate", MilestoneHelper.getFormatedDate(endStDate));
/*      */       
/*  180 */       SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
/*  181 */       String todayLong = formatter.format(new Date());
/*  182 */       report.setElement("crs_bottomdate", todayLong);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  187 */       String addsMovesFlag = reportForm.getStringValue("AddsMovesBoth");
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  192 */       Vector reportSelections = MilestoneHelper.getSelectionsForReport(context);
/*      */ 
/*      */       
/*  195 */       Vector AddsSelections = new Vector();
/*  196 */       Vector MovesSelections = new Vector();
/*  197 */       if (addsMovesFlag.equalsIgnoreCase("Adds") || addsMovesFlag.equalsIgnoreCase("Both"))
/*  198 */         AddsSelections = (Vector)reportSelections.elementAt(0); 
/*  199 */       if (addsMovesFlag.equalsIgnoreCase("Moves") || addsMovesFlag.equalsIgnoreCase("Both")) {
/*  200 */         MovesSelections = (Vector)reportSelections.elementAt(1);
/*      */       }
/*  202 */       Vector selections = new Vector();
/*  203 */       if (AddsSelections != null) {
/*  204 */         selections.addAll(AddsSelections);
/*      */       }
/*  206 */       if (MovesSelections != null) {
/*  207 */         selections.addAll(MovesSelections);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/*  216 */         HttpServletResponse sresponse = context.getResponse();
/*  217 */         context.putDelivery("status", new String("start_report"));
/*  218 */         context.putDelivery("percent", new String("10"));
/*  219 */         context.includeJSP("status.jsp", "hiddenFrame");
/*  220 */         sresponse.setContentType("text/plain");
/*  221 */         sresponse.flushBuffer();
/*      */       }
/*  223 */       catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  232 */       Hashtable selTable = groupSelectionsByAddsMovesConfigAndStreetDate(AddsSelections, MovesSelections);
/*      */ 
/*      */ 
/*      */       
/*  236 */       Enumeration AddsMovesEnum = selTable.keys();
/*  237 */       Vector AddsMovesVector = new Vector();
/*  238 */       while (AddsMovesEnum.hasMoreElements()) {
/*  239 */         AddsMovesVector.addElement(AddsMovesEnum.nextElement());
/*      */       }
/*      */ 
/*      */       
/*  243 */       int numConfigs = 0;
/*      */       
/*  245 */       int numExtraRows = 0;
/*      */       
/*  247 */       int numSelections = 0;
/*  248 */       int numRows = 0;
/*      */ 
/*      */       
/*  251 */       for (int k = 0; k < AddsMovesVector.size(); k++) {
/*      */         
/*  253 */         Hashtable streetHash = (Hashtable)selTable.get(AddsMovesVector.get(k));
/*  254 */         Enumeration streetDates = streetHash.keys();
/*  255 */         Vector streetDatesVector = new Vector();
/*  256 */         while (streetDates.hasMoreElements()) {
/*  257 */           streetDatesVector.addElement(streetDates.nextElement());
/*      */         }
/*      */         
/*  260 */         for (int i = 0; i < streetDatesVector.size(); i++) {
/*      */           
/*  262 */           String streetDateName = (streetDatesVector.elementAt(i) != null) ? (String)streetDatesVector.elementAt(i) : "";
/*  263 */           Hashtable streetDateTable = (Hashtable)streetHash.get(streetDateName);
/*  264 */           if (streetDateTable != null)
/*      */           {
/*  266 */             numConfigs += streetDateTable.size();
/*      */           }
/*      */         } 
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
/*  284 */       Object[] sortedAddsMovesArray = AddsMovesVector.toArray();
/*  285 */       Arrays.sort(sortedAddsMovesArray);
/*      */       
/*  287 */       int nextRow = 0;
/*      */       
/*  289 */       for (int xp = 0; xp < sortedAddsMovesArray.length; xp++)
/*      */       {
/*      */         
/*  292 */         String topGroup = (String)sortedAddsMovesArray[xp];
/*      */         
/*  294 */         Hashtable streetHash = (Hashtable)selTable.get(sortedAddsMovesArray[xp]);
/*  295 */         Enumeration streetDates = streetHash.keys();
/*  296 */         Vector streetDatesVector = new Vector();
/*  297 */         while (streetDates.hasMoreElements()) {
/*  298 */           streetDatesVector.addElement(streetDates.nextElement());
/*      */         }
/*      */ 
/*      */         
/*  302 */         configTableLens = new DefaultTableLens(1, 10);
/*      */         
/*  304 */         hbandCategory = new SectionBand(report);
/*  305 */         hbandCategory.setHeight(0.25F);
/*  306 */         hbandCategory.setShrinkToFit(true);
/*  307 */         hbandCategory.setVisible(true);
/*  308 */         hbandCategory.setBottomBorder(0);
/*  309 */         hbandCategory.setLeftBorder(0);
/*  310 */         hbandCategory.setRightBorder(0);
/*  311 */         hbandCategory.setTopBorder(0);
/*      */ 
/*      */         
/*  314 */         nextRow = 0;
/*      */ 
/*      */         
/*  317 */         configTableLens.setAlignment(nextRow, 0, 2);
/*  318 */         configTableLens.setSpan(nextRow, 0, new Dimension(10, 1));
/*  319 */         configTableLens.setObject(nextRow, 0, sortedAddsMovesArray[xp]);
/*  320 */         configTableLens.setRowFont(nextRow, new Font("Arial", 3, 12));
/*      */         
/*  322 */         hbandCategory.addTable(configTableLens, new Rectangle(800, 30));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  346 */         table_contents = new DefaultTableLens(1, NUM_COLS);
/*      */ 
/*      */         
/*  349 */         table_contents.setHeaderRowCount(1);
/*  350 */         table_contents.setColWidth(0, 140);
/*  351 */         table_contents.setColWidth(1, 150);
/*  352 */         table_contents.setColWidth(2, 10);
/*  353 */         table_contents.setColWidth(3, 110);
/*  354 */         table_contents.setColWidth(4, 25);
/*  355 */         table_contents.setColWidth(5, 250);
/*  356 */         table_contents.setColWidth(6, 200);
/*  357 */         table_contents.setColWidth(7, 200);
/*  358 */         table_contents.setColWidth(8, 100);
/*  359 */         table_contents.setColWidth(9, 45);
/*  360 */         table_contents.setColWidth(10, 85);
/*  361 */         table_contents.setColWidth(11, 85);
/*  362 */         table_contents.setColWidth(12, 85);
/*  363 */         table_contents.setColWidth(13, 85);
/*  364 */         table_contents.setColWidth(14, 85);
/*      */         
/*  366 */         table_contents.setRowBorder(0, 4097);
/*  367 */         table_contents.setRowBorderColor(-1, SHADED_AREA_COLOR);
/*  368 */         table_contents.setRowBorderColor(0, SHADED_AREA_COLOR);
/*      */         
/*  370 */         for (int i = 0; i < 15; i++) {
/*  371 */           table_contents.setAlignment(0, i, 34);
/*      */         }
/*      */ 
/*      */         
/*  375 */         table_contents.setInsets(new Insets(-1, 0, 0, 0));
/*      */         
/*  377 */         table_contents.setObject(0, 0, "P&D(*)\nSelection #");
/*  378 */         table_contents.setObject(0, 1, "Label");
/*  379 */         table_contents.setSpan(0, 1, new Dimension(2, 1));
/*  380 */         table_contents.setObject(0, 3, "Plant");
/*  381 */         table_contents.setObject(0, 4, "H\nD");
/*  382 */         table_contents.setObject(0, 5, "Artist");
/*  383 */         table_contents.setObject(0, 6, "Title");
/*  384 */         table_contents.setObject(0, 7, "Comments");
/*  385 */         table_contents.setObject(0, 8, "Subconfig");
/*  386 */         table_contents.setObject(0, 9, "P.O. Qty");
/*  387 */         table_contents.setObject(0, 10, "Unit");
/*  388 */         table_contents.setObject(0, 11, "Exploded\nTotal");
/*  389 */         table_contents.setObject(0, 12, "Qty\nDone");
/*  390 */         table_contents.setObject(0, 13, "Effective Date");
/*  391 */         table_contents.setObject(0, 14, "Old In House/\nStreetDate");
/*      */         
/*  393 */         table_contents.setRowFont(0, new Font("Arial", 1, 7));
/*  394 */         table_contents.setFont(0, 6, new Font("Arial", 3, 7));
/*      */         
/*  396 */         for (int i = -1; i < 15; i++) {
/*  397 */           table_contents.setColBorderColor(0, i, new Color(208, 206, 206, 0));
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*  402 */         nextRow = 0;
/*      */         
/*  404 */         Vector sortedStreetDatesVector = MilestoneHelper.sortDates(streetDatesVector);
/*      */ 
/*      */ 
/*      */         
/*  408 */         hbandHeader.setHeight(1.0F);
/*  409 */         hbandHeader.setShrinkToFit(true);
/*  410 */         hbandHeader.setVisible(true);
/*  411 */         hbandHeader.setBottomBorder(0);
/*      */         
/*  413 */         hbandHeader.addTable(table_contents, new Rectangle(800, 800));
/*      */ 
/*      */ 
/*      */         
/*  417 */         int totalCount = 0;
/*  418 */         int tenth = 1;
/*      */         
/*  420 */         for (int a = 0; a < sortedStreetDatesVector.size(); a++) {
/*      */           
/*  422 */           totalCount++;
/*  423 */           String datesC = (String)sortedStreetDatesVector.get(a);
/*  424 */           Hashtable configTableC = (Hashtable)streetHash.get(datesC);
/*      */           
/*  426 */           if (configTableC != null) {
/*      */             
/*  428 */             Enumeration monthsC = configTableC.keys();
/*  429 */             Vector monthVectorC = new Vector();
/*      */             
/*  431 */             while (monthsC.hasMoreElements())
/*      */             {
/*  433 */               monthVectorC.add((String)monthsC.nextElement());
/*      */             }
/*      */             
/*  436 */             Object[] configsArrayC = (Object[])null;
/*  437 */             configsArrayC = monthVectorC.toArray();
/*      */             
/*  439 */             for (int b = 0; b < configsArrayC.length; b++) {
/*      */               
/*  441 */               totalCount++;
/*  442 */               String monthNameC = (String)configsArrayC[b];
/*      */               
/*  444 */               Vector selectionsC = (Vector)configTableC.get(monthNameC);
/*  445 */               if (selectionsC == null) {
/*  446 */                 selectionsC = new Vector();
/*      */               }
/*  448 */               totalCount += selectionsC.size();
/*      */             } 
/*      */           } 
/*      */         } 
/*      */         
/*  453 */         tenth = (totalCount > 10) ? (totalCount / 10) : 1;
/*      */         
/*  455 */         HttpServletResponse sresponse = context.getResponse();
/*  456 */         context.putDelivery("status", new String("start_report"));
/*  457 */         context.putDelivery("percent", new String("10"));
/*  458 */         context.includeJSP("status.jsp", "hiddenFrame");
/*  459 */         sresponse.setContentType("text/plain");
/*  460 */         sresponse.flushBuffer();
/*      */         
/*  462 */         int recordCount = 0;
/*  463 */         int count = 0;
/*      */ 
/*      */         
/*  466 */         for (int n = 0; n < sortedStreetDatesVector.size(); n++) {
/*      */ 
/*      */           
/*  469 */           columnHeaderTable = new DefaultTableLens(5, NUM_COLS);
/*      */ 
/*      */           
/*  472 */           if (count < recordCount / tenth) {
/*      */             
/*  474 */             count = recordCount / tenth;
/*  475 */             sresponse = context.getResponse();
/*  476 */             context.putDelivery("status", new String("start_report"));
/*  477 */             context.putDelivery("percent", new String(String.valueOf(count * 10)));
/*  478 */             context.includeJSP("status.jsp", "hiddenFrame");
/*  479 */             sresponse.setContentType("text/plain");
/*  480 */             sresponse.flushBuffer();
/*      */           } 
/*      */           
/*  483 */           recordCount++;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  496 */           String theStreetDate = (String)sortedStreetDatesVector.elementAt(n);
/*  497 */           String theStreetDateText = !theStreetDate.trim().equals("") ? theStreetDate : "Other";
/*      */           
/*  499 */           nextRow = ReportHandler.insertLightGrayHeader(columnHeaderTable, theStreetDateText, nextRow, NUM_COLS);
/*  500 */           columnHeaderTable.setRowBorder(266240);
/*  501 */           columnHeaderTable.setRowBorderColor(-1, Color.lightGray);
/*      */           
/*  503 */           hbandConfig = new SectionBand(report);
/*  504 */           hbandConfig.setHeight(0.5F);
/*  505 */           hbandConfig.setShrinkToFit(true);
/*  506 */           hbandConfig.setVisible(true);
/*  507 */           hbandConfig.setBottomBorder(0);
/*  508 */           hbandConfig.setTopBorder(0);
/*      */           
/*  510 */           hbandConfig.addTable(columnHeaderTable, new Rectangle(0, 0, 800, 30));
/*      */           
/*  512 */           footer.setVisible(true);
/*  513 */           footer.setHeight(0.05F);
/*  514 */           footer.setShrinkToFit(true);
/*  515 */           footer.setBottomBorder(0);
/*      */           
/*  517 */           group = new DefaultSectionLens(null, group, hbandConfig);
/*      */           
/*  519 */           nextRow = 0;
/*      */ 
/*      */           
/*  522 */           Hashtable configTable = (Hashtable)streetHash.get(theStreetDate);
/*      */           
/*  524 */           if (configTable != null) {
/*      */             
/*  526 */             Enumeration configsEnum = configTable.keys();
/*      */             
/*  528 */             Vector configsVector = new Vector();
/*      */             
/*  530 */             while (configsEnum.hasMoreElements())
/*      */             {
/*  532 */               configsVector.add((String)configsEnum.nextElement());
/*      */             }
/*      */             
/*  535 */             Object[] configsList = configsVector.toArray();
/*      */             
/*  537 */             Arrays.sort(configsList, new StringComparator());
/*      */ 
/*      */             
/*  540 */             for (int x = 0; x < configsList.length; x++) {
/*      */               
/*  542 */               theConfigTable = new DefaultTableLens(2, NUM_COLS);
/*      */               
/*  544 */               nextRow = 0;
/*      */ 
/*      */               
/*  547 */               theConfig = (String)configsList[x];
/*      */ 
/*      */ 
/*      */               
/*  551 */               if (count < recordCount / tenth) {
/*      */                 
/*  553 */                 count = recordCount / tenth;
/*  554 */                 sresponse = context.getResponse();
/*  555 */                 context.putDelivery("status", new String("start_report"));
/*  556 */                 context.putDelivery("percent", new String(String.valueOf(count * 10)));
/*  557 */                 context.includeJSP("status.jsp", "hiddenFrame");
/*  558 */                 sresponse.setContentType("text/plain");
/*  559 */                 sresponse.flushBuffer();
/*      */               } 
/*      */               
/*  562 */               recordCount++;
/*      */ 
/*      */               
/*  565 */               setColBorderColors(nextRow, -1, NUM_COLS - 1, theConfigTable, new Color(208, 206, 206, 0));
/*      */               
/*  567 */               theConfigTable.setRowBorderColor(nextRow, new Color(208, 206, 206, 0));
/*  568 */               theConfigTable.setRowBorder(nextRow, 4097);
/*      */               
/*  570 */               setColBorderColors(nextRow + 1, -1, NUM_COLS - 1, theConfigTable, new Color(208, 206, 206, 0));
/*      */               
/*  572 */               theConfigTable.setRowBorderColor(nextRow + 1, new Color(208, 206, 206, 0));
/*  573 */               theConfigTable.setRowBorder(nextRow + 1, 4097);
/*      */ 
/*      */ 
/*      */               
/*  577 */               setColBorders(nextRow, -1, NUM_COLS - 1, theConfigTable, 0);
/*      */ 
/*      */               
/*  580 */               theConfigTable.setColBorderColor(nextRow, -1, Color.white);
/*  581 */               theConfigTable.setColBorderColor(nextRow, 1, Color.white);
/*  582 */               theConfigTable.setColBorderColor(nextRow, 2, Color.white);
/*  583 */               theConfigTable.setColBorderColor(nextRow, 3, Color.white);
/*      */ 
/*      */               
/*  586 */               theConfigTable.setSpan(nextRow, 0, new Dimension(NUM_COLS, 1));
/*  587 */               theConfigTable.setObject(nextRow, 0, theConfig);
/*      */               
/*  589 */               theConfigTable.setRowFont(nextRow, new Font("Arial", 1, 9));
/*  590 */               theConfigTable.setRowBackground(nextRow, Color.white);
/*  591 */               theConfigTable.setRowForeground(nextRow, Color.black);
/*      */ 
/*      */               
/*  594 */               theConfigTable.setRowBorderColor(-1, Color.lightGray);
/*      */ 
/*      */               
/*  597 */               theConfigTable.setRowBorderColor(Color.white);
/*  598 */               theConfigTable.setRowBorderColor(nextRow, Color.white);
/*      */               
/*  600 */               theConfigTable.setRowBorderColor(nextRow + 1, Color.white);
/*      */               
/*  602 */               hbandMonth = new SectionBand(report);
/*  603 */               hbandMonth.setHeight(0.5F);
/*  604 */               hbandMonth.setShrinkToFit(true);
/*  605 */               hbandMonth.setVisible(true);
/*  606 */               hbandMonth.setBottomBorder(0);
/*  607 */               hbandMonth.setLeftBorder(0);
/*  608 */               hbandMonth.setRightBorder(0);
/*  609 */               hbandMonth.setTopBorder(0);
/*      */               
/*  611 */               hbandMonth.addTable(theConfigTable, new Rectangle(0, 0, 800, 30));
/*      */ 
/*      */               
/*  614 */               footer.setVisible(true);
/*  615 */               footer.setHeight(0.05F);
/*  616 */               footer.setShrinkToFit(true);
/*  617 */               footer.setBottomBorder(0);
/*      */               
/*  619 */               group = new DefaultSectionLens(null, group, hbandMonth);
/*      */ 
/*      */               
/*  622 */               selections = (Vector)configTable.get(theConfig);
/*  623 */               if (selections == null) {
/*  624 */                 selections = new Vector();
/*      */               }
/*      */               
/*  627 */               MilestoneHelper.setSelectionSorting(selections, 14);
/*  628 */               Collections.sort(selections);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  637 */               MilestoneHelper.applyManufacturingToSelections(selections);
/*      */ 
/*      */ 
/*      */               
/*  641 */               for (int ix = 0; ix < selections.size(); ix++) {
/*      */                 
/*  643 */                 Selection sel = (Selection)selections.elementAt(ix);
/*      */ 
/*      */ 
/*      */                 
/*  647 */                 Vector plants = sel.getManufacturingPlants();
/*      */                 
/*  649 */                 int plantSize = 1;
/*      */                 
/*  651 */                 if (plants != null && plants.size() > 0) {
/*  652 */                   plantSize = plants.size();
/*      */                 }
/*  654 */                 for (int plantCount = 0; plantCount < plantSize; plantCount++) {
/*      */                   
/*  656 */                   Plant p = null;
/*      */                   
/*  658 */                   if (plants != null && plants.size() > 0) {
/*  659 */                     p = (Plant)plants.get(plantCount);
/*      */                   }
/*      */ 
/*      */                   
/*  663 */                   nextRow = 0;
/*  664 */                   subTable = new DefaultTableLens(2, NUM_COLS);
/*  665 */                   subTable.setColWidth(0, 140);
/*  666 */                   subTable.setColWidth(1, 150);
/*  667 */                   subTable.setColWidth(2, 10);
/*  668 */                   subTable.setColWidth(3, 110);
/*  669 */                   subTable.setColWidth(4, 25);
/*  670 */                   subTable.setColWidth(5, 250);
/*  671 */                   subTable.setColWidth(6, 200);
/*  672 */                   subTable.setColWidth(7, 200);
/*  673 */                   subTable.setColWidth(8, 100);
/*  674 */                   subTable.setColWidth(9, 45);
/*  675 */                   subTable.setColWidth(10, 85);
/*  676 */                   subTable.setColWidth(11, 85);
/*  677 */                   subTable.setColWidth(12, 85);
/*  678 */                   subTable.setColWidth(13, 85);
/*  679 */                   subTable.setColWidth(14, 85);
/*      */                   
/*  681 */                   subTable.setRowBorderColor(-1, new Color(208, 206, 206, 0));
/*  682 */                   subTable.setRowBorderColor(1, new Color(208, 206, 206, 0));
/*      */                   
/*  684 */                   subTable.setRowBorderColor(0, Color.white);
/*      */                   
/*  686 */                   subTable.setRowBorder(0, 4097);
/*      */                   
/*      */                   int i;
/*      */                   
/*  690 */                   for (i = 0; i < 15; i++) {
/*  691 */                     subTable.setLineWrap(0, i, false);
/*      */                   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  700 */                   int artistLength = 0, labelLength = 0, titleLength = 0, idLength = 0, commentLength = 0;
/*  701 */                   int subconfigLength = 0;
/*      */                   
/*  703 */                   if (count < recordCount / tenth) {
/*      */                     
/*  705 */                     count = recordCount / tenth;
/*  706 */                     sresponse = context.getResponse();
/*  707 */                     context.putDelivery("status", new String("start_report"));
/*  708 */                     context.putDelivery("percent", new String(String.valueOf(count * 10)));
/*  709 */                     context.includeJSP("status.jsp", "hiddenFrame");
/*  710 */                     sresponse.setContentType("text/plain");
/*  711 */                     sresponse.flushBuffer();
/*      */                   } 
/*      */                   
/*  714 */                   recordCount++;
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  719 */                   String selectionNo = SelectionManager.getLookupObjectValue(sel.getPrefixID());
/*  720 */                   if (selectionNo == null)
/*  721 */                     selectionNo = ""; 
/*  722 */                   selectionNo = String.valueOf(selectionNo) + sel.getSelectionNo().trim();
/*  723 */                   idLength = selectionNo.length();
/*      */ 
/*      */                   
/*  726 */                   String pd = "";
/*  727 */                   if (sel.getPressAndDistribution()) {
/*  728 */                     pd = "* ";
/*      */                   }
/*  730 */                   String selDistribution = SelectionManager.getLookupObjectValue(sel.getDistribution());
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  735 */                   String artist = "";
/*      */ 
/*      */                   
/*  738 */                   artist = sel.getFlArtist();
/*  739 */                   if (artist != null) {
/*  740 */                     artistLength = artist.length();
/*      */                   }
/*      */                   
/*  743 */                   String comment = (sel.getSelectionComments() != null) ? sel.getSelectionComments().trim() : "";
/*  744 */                   commentLength = comment.length();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  751 */                   String titleComments = sel.getTitle();
/*  752 */                   if (titleComments != null) {
/*  753 */                     titleLength = titleComments.length();
/*      */                   }
/*      */ 
/*      */                   
/*  757 */                   String poQty = (p != null && p.getOrderQty() > 0) ? String.valueOf(p.getOrderQty()) : "0";
/*      */ 
/*      */                   
/*  760 */                   String units = (sel.getNumberOfUnits() > 0) ? String.valueOf(sel.getNumberOfUnits()) : "";
/*      */ 
/*      */                   
/*  763 */                   int poQtyNum = 0;
/*      */ 
/*      */                   
/*      */                   try {
/*  767 */                     poQtyNum = Integer.parseInt(poQty);
/*      */                   }
/*  769 */                   catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  775 */                   int explodedTotal = 0;
/*  776 */                   if (poQtyNum > 0 && sel.getNumberOfUnits() > 0) {
/*  777 */                     explodedTotal = poQtyNum * sel.getNumberOfUnits();
/*      */                   }
/*      */                   
/*  780 */                   String plant = "";
/*  781 */                   String plantText = "";
/*      */                   
/*  783 */                   if (p != null && p.getPlant() != null) {
/*      */                     
/*  785 */                     String plantNo = p.getPlant().getName();
/*  786 */                     plant = p.getPlant().getAbbreviation();
/*  787 */                     plantText = p.getPlant().getSubValue();
/*      */                     
/*  789 */                     if (plant != null)
/*      */                     {
/*  791 */                       if (plant.equalsIgnoreCase("20")) {
/*  792 */                         plant = "KM";
/*  793 */                       } else if (plant.equalsIgnoreCase("175")) {
/*  794 */                         plant = "PI";
/*  795 */                       } else if (plant.equalsIgnoreCase("009")) {
/*  796 */                         plant = "HA";
/*  797 */                       } else if (plant.equalsIgnoreCase("171")) {
/*  798 */                         plant = "GL";
/*  799 */                       } else if (plant.equalsIgnoreCase("72")) {
/*  800 */                         plant = "CI";
/*  801 */                       } else if (plant.equalsIgnoreCase("371")) {
/*  802 */                         plant = "PA";
/*  803 */                       } else if (plant.equalsIgnoreCase("1772")) {
/*  804 */                         plant = "CIV";
/*      */                       } else {
/*  806 */                         plant = "";
/*      */                       } 
/*      */                     }
/*      */                   } 
/*      */ 
/*      */                   
/*  812 */                   String compQty = "0";
/*  813 */                   if (p != null && p.getCompletedQty() > 0) {
/*  814 */                     compQty = MilestoneHelper.formatQuantityWithCommas(String.valueOf(p.getCompletedQty()));
/*      */                   }
/*      */                   
/*  817 */                   String label = "";
/*  818 */                   if (sel.getLabel() != null)
/*  819 */                     label = (sel.getLabel().getName().length() > 25) ? sel.getLabel().getName().substring(0, 24) : sel.getLabel().getName().trim(); 
/*  820 */                   labelLength = label.length();
/*      */ 
/*      */                   
/*  823 */                   String effectiveDatetxt = "";
/*  824 */                   if (sel.getLastStreetUpdateDate() != null) {
/*  825 */                     effectiveDatetxt = MilestoneHelper.getCustomFormatedDate(sel.getLastStreetUpdateDate(), "MM/dd/yy");
/*  826 */                   } else if (sel.getOriginDate() != null) {
/*  827 */                     effectiveDatetxt = MilestoneHelper.getCustomFormatedDate(sel.getOriginDate(), "MM/dd/yy");
/*      */                   } 
/*      */ 
/*      */                   
/*  831 */                   String subconfigtxt = "";
/*  832 */                   if (sel.getSelectionSubConfig() != null)
/*  833 */                     subconfigtxt = sel.getSelectionSubConfig().getSelectionSubConfigurationName(); 
/*  834 */                   if (subconfigtxt != null) {
/*  835 */                     subconfigLength = subconfigtxt.length();
/*      */                   }
/*      */ 
/*      */                   
/*  839 */                   subTable.setRowInsets(nextRow, new Insets(0, 0, 5, 0));
/*      */                   
/*  841 */                   subTable.setSpan(nextRow, 0, new Dimension(1, 2));
/*  842 */                   subTable.setSpan(nextRow, 1, new Dimension(2, 2));
/*  843 */                   subTable.setSpan(nextRow, 2, new Dimension(1, 2));
/*  844 */                   subTable.setSpan(nextRow, 3, new Dimension(1, 2));
/*  845 */                   subTable.setSpan(nextRow, 4, new Dimension(1, 2));
/*  846 */                   subTable.setSpan(nextRow, 5, new Dimension(1, 2));
/*  847 */                   subTable.setSpan(nextRow, 6, new Dimension(1, 2));
/*  848 */                   subTable.setSpan(nextRow, 7, new Dimension(1, 2));
/*  849 */                   subTable.setSpan(nextRow, 8, new Dimension(1, 2));
/*  850 */                   subTable.setSpan(nextRow, 9, new Dimension(1, 2));
/*  851 */                   subTable.setSpan(nextRow, 10, new Dimension(1, 2));
/*  852 */                   subTable.setSpan(nextRow, 11, new Dimension(1, 2));
/*  853 */                   subTable.setSpan(nextRow, 12, new Dimension(1, 2));
/*  854 */                   subTable.setSpan(nextRow, 13, new Dimension(1, 2));
/*  855 */                   subTable.setSpan(nextRow, 14, new Dimension(1, 2));
/*      */                   
/*  857 */                   if (idLength == 13 && pd.trim().equals("*")) {
/*  858 */                     pd = "*";
/*      */                   }
/*      */                   
/*  861 */                   subTable.setObject(nextRow, 0, String.valueOf(pd) + selectionNo);
/*  862 */                   subTable.setObject(nextRow, 1, label);
/*  863 */                   subTable.setObject(nextRow, 3, plantText);
/*  864 */                   subTable.setObject(nextRow, 4, selDistribution);
/*  865 */                   subTable.setObject(nextRow, 5, artist);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  871 */                   subTable.setObject(nextRow, 6, titleComments);
/*  872 */                   subTable.setObject(nextRow, 7, comment);
/*  873 */                   subTable.setObject(nextRow, 8, subconfigtxt);
/*  874 */                   subTable.setObject(nextRow, 9, MilestoneHelper.formatQuantityWithCommas(poQty));
/*  875 */                   subTable.setObject(nextRow, 10, units);
/*  876 */                   subTable.setObject(nextRow, 11, MilestoneHelper.formatQuantityWithCommas(String.valueOf(explodedTotal)));
/*  877 */                   subTable.setObject(nextRow, 12, MilestoneHelper.formatQuantityWithCommas(compQty));
/*      */                   
/*  879 */                   subTable.setObject(nextRow, 13, effectiveDatetxt);
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  884 */                   if (topGroup.equalsIgnoreCase("Moves")) {
/*      */ 
/*      */                     
/*  887 */                     String strLastStDate = "";
/*  888 */                     StringBuffer StQuery = new StringBuffer(200);
/*  889 */                     StQuery.append("select top 1 street_date, logged_on from audit_release_header with (nolock) ");
/*  890 */                     StQuery.append("where release_id = " + sel.getIdentity());
/*  891 */                     StQuery.append(" order by logged_on DESC");
/*      */                     
/*  893 */                     JdbcConnector connector = MilestoneHelper.getConnector(StQuery.toString());
/*      */ 
/*      */                     
/*  896 */                     if (connector != null) {
/*      */                       
/*  898 */                       connector.setForwardOnly(false);
/*  899 */                       connector.runQuery();
/*      */ 
/*      */                       
/*  902 */                       SimpleDateFormat adf = new SimpleDateFormat("MM/dd/yy");
/*  903 */                       if (connector.more())
/*      */                       {
/*      */                         
/*  906 */                         if (connector.getDate("street_date") != null) {
/*  907 */                           strLastStDate = adf.format(connector.getDate("street_date"));
/*      */                         }
/*      */                       }
/*  910 */                       connector.close();
/*      */                     } 
/*      */ 
/*      */                     
/*  914 */                     subTable.setObject(nextRow, 14, strLastStDate);
/*      */                   
/*      */                   }
/*      */                   else {
/*      */                     
/*  919 */                     subTable.setObject(nextRow, 14, "N/A");
/*      */                   } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  926 */                   subTable.setLineWrap(nextRow, 0, false);
/*  927 */                   subTable.setAlignment(nextRow, 0, 4);
/*      */                   
/*  929 */                   for (i = 0; i < 15; i++) {
/*  930 */                     subTable.setLineWrap(nextRow, i, true);
/*      */                   }
/*      */                   
/*  933 */                   for (i = 0; i < 9; i++)
/*  934 */                     subTable.setAlignment(nextRow, i, 8); 
/*  935 */                   for (i = 9; i < 15; i++) {
/*  936 */                     subTable.setAlignment(nextRow, i, 12);
/*      */                   }
/*  938 */                   subTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
/*  939 */                   subTable.setRowFont(nextRow, new Font("Arial", 0, 6));
/*      */                   
/*  941 */                   for (i = -1; i < 15; i++) {
/*  942 */                     subTable.setColBorderColor(nextRow, i, new Color(208, 206, 206, 0));
/*      */                   }
/*  944 */                   subTable.setRowBorderColor(nextRow, new Color(208, 206, 206, 0));
/*  945 */                   subTable.setRowBorder(nextRow, 4097);
/*      */ 
/*      */ 
/*      */                   
/*  949 */                   nextRow++;
/*      */ 
/*      */ 
/*      */                   
/*  953 */                   for (i = -1; i < 15; i++) {
/*  954 */                     subTable.setColBorderColor(nextRow, i, new Color(208, 206, 206, 0));
/*      */                   }
/*  956 */                   subTable.setRowBorderColor(nextRow, new Color(208, 206, 206, 0));
/*  957 */                   subTable.setRowBorderColor(nextRow + 1, new Color(208, 206, 206, 0));
/*  958 */                   subTable.setRowBorderColor(nextRow + 3, new Color(208, 206, 206, 0));
/*      */                   
/*  960 */                   subTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  969 */                   int factor1 = titleLength / 16;
/*  970 */                   int factor2 = commentLength / 16;
/*  971 */                   if (titleLength > 16 || commentLength > 16) {
/*      */                     
/*  973 */                     if (factor1 > factor2) {
/*  974 */                       subTable.setRowHeight(nextRow, factor1 + 1);
/*      */                     } else {
/*  976 */                       subTable.setRowHeight(nextRow, factor2 + 1);
/*      */                     } 
/*  978 */                   } else if (subconfigLength > 16) {
/*      */                     
/*  980 */                     factor1 = subconfigLength / 16;
/*  981 */                     subTable.setRowHeight(nextRow, factor1 + 1);
/*      */                   }
/*      */                   else {
/*      */                     
/*  985 */                     subTable.setRowHeight(nextRow, 1);
/*      */                   } 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  991 */                   body = new SectionBand(report);
/*      */                   
/*  993 */                   body.setHeight(0.5F);
/*  994 */                   body.addTable(subTable, new Rectangle(800, 800));
/*      */                   
/*  996 */                   body.setTopBorder(2);
/*  997 */                   body.setShrinkToFit(true);
/*  998 */                   body.setVisible(true);
/*      */                   
/* 1000 */                   group = new DefaultSectionLens(null, group, body);
/*      */                   
/* 1002 */                   nextRow = 0;
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
/*      */         
/* 1014 */         spacer.setHeight(0.1F);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1019 */         group = new DefaultSectionLens(spacer, group, null);
/* 1020 */         group = new DefaultSectionLens(hbandCategory, group, null);
/* 1021 */         group = new DefaultSectionLens(spacer, group, null);
/* 1022 */         group = new DefaultSectionLens(hbandHeader, group, null);
/* 1023 */         report.addSection(group, rowCountTable);
/* 1024 */         report.addPageBreak();
/* 1025 */         group = null;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 1036 */     catch (Exception e) {
/*      */       
/* 1038 */       System.out.println(">>>>>>>>ReportHandler.fillUmlNewReleaseMasterScheduleSubHandler(): exception: " + e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void setColBorderColors(int rowNum, int start, int end, DefaultTableLens table, Color color) {
/* 1047 */     end++;
/*      */     
/* 1049 */     for (int i = start; i < end; i++)
/*      */     {
/* 1051 */       table.setColBorderColor(rowNum, i, Color.white);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void setRowBorderColors(int rowNum, int start, int end, DefaultTableLens table, Color color) {
/* 1060 */     end++;
/*      */     
/* 1062 */     for (int i = start; i < end; i++)
/*      */     {
/* 1064 */       table.setRowBorderColor(rowNum, i, Color.white);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void setColBorders(int rowNum, int start, int end, DefaultTableLens table, int size) {
/* 1073 */     end++;
/*      */     
/* 1075 */     for (int i = start; i < end; i++)
/*      */     {
/* 1077 */       table.setColBorder(rowNum, i, size);
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
/*      */   public static Hashtable groupSelectionsByAddsMovesConfigAndStreetDate(Vector addSelections, Vector movesSelections) {
/* 1093 */     Hashtable groupSelectionsByAddsMovesConfigAndStreetDate = new Hashtable();
/* 1094 */     if (addSelections == null && movesSelections == null) {
/* 1095 */       return groupSelectionsByAddsMovesConfigAndStreetDate;
/*      */     }
/* 1097 */     Vector selections = addSelections;
/* 1098 */     String AddMoveInd = "Adds";
/* 1099 */     for (int j = 0; j < 2; j++) {
/*      */       
/* 1101 */       if (selections != null)
/*      */       {
/* 1103 */         for (int i = 0; i < selections.size(); i++) {
/*      */           
/* 1105 */           Selection sel = (Selection)selections.elementAt(i);
/* 1106 */           if (sel != null) {
/*      */ 
/*      */ 
/*      */             
/* 1110 */             String configString = "", dateString = "";
/* 1111 */             configString = (sel.getSelectionConfig() != null) ? sel.getSelectionConfig().getSelectionConfigurationName() : "";
/* 1112 */             dateString = (sel.getStreetDate() != null) ? MilestoneHelper.getFormatedDate(sel.getStreetDate()) : "";
/*      */ 
/*      */             
/* 1115 */             Hashtable addsMovesSubTable = (Hashtable)groupSelectionsByAddsMovesConfigAndStreetDate.get(AddMoveInd);
/* 1116 */             if (addsMovesSubTable == null) {
/*      */ 
/*      */               
/* 1119 */               addsMovesSubTable = new Hashtable();
/* 1120 */               groupSelectionsByAddsMovesConfigAndStreetDate.put(AddMoveInd, addsMovesSubTable);
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1127 */             Hashtable streetTable = (Hashtable)addsMovesSubTable.get(dateString);
/* 1128 */             if (streetTable == null) {
/*      */ 
/*      */               
/* 1131 */               streetTable = new Hashtable();
/* 1132 */               addsMovesSubTable.put(dateString, streetTable);
/*      */             } 
/*      */ 
/*      */ 
/*      */             
/* 1137 */             Vector configsForDate = (Vector)streetTable.get(configString);
/* 1138 */             if (configsForDate == null) {
/*      */ 
/*      */               
/* 1141 */               configsForDate = new Vector();
/* 1142 */               streetTable.put(configString, configsForDate);
/*      */             } 
/*      */             
/* 1145 */             configsForDate.addElement(sel);
/*      */           } 
/*      */         } 
/*      */       }
/*      */       
/* 1150 */       selections = movesSelections;
/* 1151 */       AddMoveInd = "Moves";
/*      */     } 
/*      */     
/* 1154 */     return groupSelectionsByAddsMovesConfigAndStreetDate;
/*      */   }
/*      */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\UmlAddsMovesReportSubHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */