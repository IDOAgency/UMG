/*      */ package WEB-INF.classes.com.universal.milestone;
/*      */ 
/*      */ import com.techempower.ComponentLog;
/*      */ import com.techempower.gemini.Context;
/*      */ import com.techempower.gemini.GeminiApplication;
/*      */ import com.universal.milestone.Form;
/*      */ import com.universal.milestone.JdbcConnector;
/*      */ import com.universal.milestone.MilestoneHelper;
/*      */ import com.universal.milestone.Plant;
/*      */ import com.universal.milestone.ReleasingFamily;
/*      */ import com.universal.milestone.ReportHandler;
/*      */ import com.universal.milestone.Schedule;
/*      */ import com.universal.milestone.ScheduledTask;
/*      */ import com.universal.milestone.SecureHandler;
/*      */ import com.universal.milestone.Selection;
/*      */ import com.universal.milestone.SelectionManager;
/*      */ import com.universal.milestone.StringComparator;
/*      */ import com.universal.milestone.UmlNewReleaseMasterScheduleSubHandler;
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
/*      */ import java.io.IOException;
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
/*      */ public class UmlNewReleaseMasterScheduleSubHandler
/*      */   extends SecureHandler
/*      */ {
/*      */   public static final String COMPONENT_CODE = "hUsu";
/*      */   public GeminiApplication application;
/*      */   public ComponentLog log;
/*   83 */   public static int NUM_COLS = 22;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public UmlNewReleaseMasterScheduleSubHandler(GeminiApplication application) {
/*   94 */     this.application = application;
/*   95 */     this.log = application.getLog("hUsu");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  103 */   public String getDescription() { return "Sub Report"; }
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean tryParseInt(String value) {
/*      */     try {
/*  109 */       Integer.parseInt(value);
/*  110 */       return true;
/*  111 */     } catch (NumberFormatException nfe) {
/*      */       
/*  113 */       return false;
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
/*      */   protected static void fillUmlNewReleaseMasterScheduleForPrint(XStyleSheet report, Context context) {
/*  130 */     report.setMargin(new Margin(1.0D, 0.1D, 0.1D, 0.1D));
/*  131 */     report.setFooterFromEdge(0.5D);
/*  132 */     report.setHeaderFromEdge(0.1D);
/*      */     
/*  134 */     SectionBand hbandHeader = new SectionBand(report);
/*  135 */     SectionBand body = new SectionBand(report);
/*  136 */     SectionBand footer = new SectionBand(report);
/*      */     
/*  138 */     DefaultSectionLens group = null;
/*      */     
/*  140 */     DefaultTableLens table_contents = null;
/*  141 */     DefaultTableLens rowCountTable = null;
/*  142 */     DefaultTableLens subTable = null;
/*      */     
/*  144 */     rowCountTable = new DefaultTableLens(2, 10000);
/*      */     
/*  146 */     Color SHADED_AREA_COLOR = Color.lightGray;
/*  147 */     String theConfig = "";
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  152 */       HttpServletResponse sresponse = context.getResponse();
/*  153 */       context.putDelivery("status", new String("start_gathering"));
/*  154 */       context.includeJSP("status.jsp", "hiddenFrame");
/*  155 */       sresponse.setContentType("text/plain");
/*  156 */       sresponse.flushBuffer();
/*      */     }
/*  158 */     catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  163 */     Vector selections = MilestoneHelper.getSelectionsForReport(context);
/*      */ 
/*      */     
/*  166 */     Hashtable plantIdandName = buildPlantHash();
/*      */ 
/*      */     
/*      */     try {
/*  170 */       HttpServletResponse sresponse = context.getResponse();
/*  171 */       context.putDelivery("status", new String("start_report"));
/*  172 */       context.putDelivery("percent", new String("10"));
/*  173 */       context.includeJSP("status.jsp", "hiddenFrame");
/*  174 */       sresponse.setContentType("text/plain");
/*  175 */       sresponse.flushBuffer();
/*      */     }
/*  177 */     catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  187 */       Form reportForm = (Form)context.getSessionValue("reportForm");
/*      */       
/*  189 */       Calendar beginStDate = (reportForm.getStringValue("beginDate") != null && 
/*  190 */         reportForm.getStringValue("beginDate").length() > 0) ? 
/*  191 */         MilestoneHelper.getDate(reportForm.getStringValue("beginDate")) : null;
/*      */       
/*  193 */       Calendar endStDate = (reportForm.getStringValue("endDate") != null && 
/*  194 */         reportForm.getStringValue("endDate").length() > 0) ? 
/*  195 */         MilestoneHelper.getDate(reportForm.getStringValue("endDate")) : null;
/*      */       
/*  197 */       report.setElement("crs_startdate", MilestoneHelper.getFormatedDate(beginStDate));
/*  198 */       report.setElement("crs_enddate", MilestoneHelper.getFormatedDate(endStDate));
/*      */       
/*  200 */       SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
/*  201 */       String todayLong = formatter.format(new Date());
/*  202 */       report.setElement("crs_bottomdate", todayLong);
/*      */ 
/*      */       
/*  205 */       Hashtable selTable = MilestoneHelper.groupSelectionsByConfigAndStreetDate(selections);
/*      */       
/*  207 */       Enumeration streetDates = selTable.keys();
/*  208 */       Vector streetDatesVector = new Vector();
/*  209 */       while (streetDates.hasMoreElements()) {
/*  210 */         streetDatesVector.addElement(streetDates.nextElement());
/*      */       }
/*      */       
/*  213 */       int numConfigs = 0;
/*  214 */       for (int i = 0; i < streetDatesVector.size(); i++) {
/*      */         
/*  216 */         String streetDateName = (streetDatesVector.elementAt(i) != null) ? (String)streetDatesVector.elementAt(i) : "";
/*  217 */         Hashtable streetDateTable = (Hashtable)selTable.get(streetDateName);
/*  218 */         if (streetDateTable != null)
/*      */         {
/*  220 */           numConfigs += streetDateTable.size();
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/*  225 */       int numExtraRows = 2 + streetDatesVector.size() * 10 + numConfigs;
/*      */ 
/*      */       
/*  228 */       int numSelections = selections.size() * 2;
/*  229 */       int numRows = numSelections + numExtraRows;
/*      */ 
/*      */       
/*  232 */       numRows -= streetDatesVector.size() * 2 - 1;
/*      */ 
/*      */       
/*  235 */       table_contents = new DefaultTableLens(2, NUM_COLS);
/*      */ 
/*      */       
/*  238 */       table_contents.setHeaderRowCount(1);
/*  239 */       table_contents.setColWidth(0, 140);
/*  240 */       table_contents.setColWidth(1, 150);
/*  241 */       table_contents.setColWidth(2, 10);
/*  242 */       table_contents.setColWidth(3, 110);
/*  243 */       table_contents.setColWidth(4, 25);
/*  244 */       table_contents.setColWidth(5, 246);
/*  245 */       table_contents.setColWidth(6, 140);
/*  246 */       table_contents.setColWidth(7, 140);
/*  247 */       table_contents.setColWidth(8, 100);
/*  248 */       table_contents.setColWidth(9, 45);
/*  249 */       table_contents.setColWidth(10, 110);
/*  250 */       table_contents.setColWidth(11, 80);
/*  251 */       table_contents.setColWidth(12, 85);
/*  252 */       table_contents.setColWidth(13, 85);
/*  253 */       table_contents.setColWidth(14, 85);
/*  254 */       table_contents.setColWidth(15, 85);
/*  255 */       table_contents.setColWidth(16, 85);
/*  256 */       table_contents.setColWidth(17, 85);
/*  257 */       table_contents.setColWidth(18, 85);
/*  258 */       table_contents.setColWidth(19, 85);
/*  259 */       table_contents.setColWidth(20, 85);
/*  260 */       table_contents.setColWidth(21, 89);
/*      */       
/*  262 */       table_contents.setRowBorder(0, 4097);
/*  263 */       table_contents.setRowBorderColor(-1, SHADED_AREA_COLOR);
/*  264 */       table_contents.setRowBorderColor(0, SHADED_AREA_COLOR);
/*      */       
/*  266 */       table_contents.setAlignment(0, 0, 34);
/*  267 */       table_contents.setAlignment(0, 1, 34);
/*  268 */       table_contents.setAlignment(0, 2, 34);
/*  269 */       table_contents.setAlignment(0, 3, 34);
/*  270 */       table_contents.setAlignment(0, 4, 34);
/*  271 */       table_contents.setAlignment(0, 5, 34);
/*  272 */       table_contents.setAlignment(0, 6, 36);
/*  273 */       table_contents.setAlignment(0, 7, 33);
/*  274 */       table_contents.setAlignment(0, 8, 34);
/*  275 */       table_contents.setAlignment(0, 9, 34);
/*  276 */       table_contents.setAlignment(0, 10, 34);
/*  277 */       table_contents.setAlignment(0, 11, 34);
/*  278 */       table_contents.setAlignment(0, 12, 34);
/*  279 */       table_contents.setAlignment(0, 13, 34);
/*  280 */       table_contents.setAlignment(0, 14, 34);
/*  281 */       table_contents.setAlignment(0, 15, 34);
/*  282 */       table_contents.setAlignment(0, 16, 34);
/*  283 */       table_contents.setAlignment(0, 17, 34);
/*  284 */       table_contents.setAlignment(0, 18, 34);
/*  285 */       table_contents.setAlignment(0, 19, 34);
/*  286 */       table_contents.setAlignment(0, 20, 34);
/*  287 */       table_contents.setAlignment(0, 21, 34);
/*      */       
/*  289 */       table_contents.setInsets(new Insets(-1, 0, 0, 0));
/*      */       
/*  291 */       table_contents.setObject(0, 0, "P&D(*)\nLocal Prod #");
/*  292 */       table_contents.setObject(0, 1, "Rls Family - Label");
/*  293 */       table_contents.setSpan(0, 1, new Dimension(2, 1));
/*  294 */       table_contents.setObject(0, 3, "Plant");
/*  295 */       table_contents.setObject(0, 4, "H\nD");
/*  296 */       table_contents.setObject(0, 5, "Artist");
/*  297 */       table_contents.setObject(0, 6, "Title  /");
/*  298 */       table_contents.setObject(0, 7, "Comments");
/*  299 */       table_contents.setObject(0, 8, "P.O.\nQty");
/*  300 */       table_contents.setObject(0, 9, "Unit");
/*  301 */       table_contents.setObject(0, 10, "Exploded\nTotal");
/*  302 */       table_contents.setObject(0, 11, "Qty\nDone");
/*  303 */       table_contents.setObject(0, 12, "F/M");
/*  304 */       table_contents.setObject(0, 13, "BOM");
/*  305 */       table_contents.setObject(0, 15, "PRQ");
/*  306 */       table_contents.setObject(0, 16, "TAPE");
/*  307 */       table_contents.setObject(0, 17, "FILM");
/*  308 */       table_contents.setObject(0, 20, "PAP");
/*  309 */       table_contents.setObject(0, 18, "STIC");
/*  310 */       table_contents.setObject(0, 19, "PPR");
/*  311 */       table_contents.setObject(0, 14, "FAP");
/*  312 */       table_contents.setObject(0, 21, "PSD\nComp\nDue");
/*      */       
/*  314 */       table_contents.setRowFont(0, new Font("Arial", 1, 7));
/*  315 */       table_contents.setFont(0, 6, new Font("Arial", 3, 7));
/*      */       
/*  317 */       table_contents.setColBorderColor(0, -1, new Color(208, 206, 206, 0));
/*  318 */       table_contents.setColBorderColor(0, 0, new Color(208, 206, 206, 0));
/*  319 */       table_contents.setColBorderColor(0, 1, Color.white);
/*  320 */       table_contents.setColBorderColor(0, 2, new Color(208, 206, 206, 0));
/*  321 */       table_contents.setColBorderColor(0, 3, new Color(208, 206, 206, 0));
/*  322 */       table_contents.setColBorderColor(0, 4, new Color(208, 206, 206, 0));
/*  323 */       table_contents.setColBorderColor(0, 5, new Color(208, 206, 206, 0));
/*  324 */       table_contents.setColBorderColor(0, 6, Color.white);
/*  325 */       table_contents.setColBorderColor(0, 7, new Color(208, 206, 206, 0));
/*  326 */       table_contents.setColBorderColor(0, 8, new Color(208, 206, 206, 0));
/*  327 */       table_contents.setColBorderColor(0, 9, new Color(208, 206, 206, 0));
/*  328 */       table_contents.setColBorderColor(0, 10, new Color(208, 206, 206, 0));
/*  329 */       table_contents.setColBorderColor(0, 11, new Color(208, 206, 206, 0));
/*  330 */       table_contents.setColBorderColor(0, 12, new Color(208, 206, 206, 0));
/*  331 */       table_contents.setColBorderColor(0, 13, new Color(208, 206, 206, 0));
/*  332 */       table_contents.setColBorderColor(0, 14, new Color(208, 206, 206, 0));
/*  333 */       table_contents.setColBorderColor(0, 15, new Color(208, 206, 206, 0));
/*  334 */       table_contents.setColBorderColor(0, 16, new Color(208, 206, 206, 0));
/*  335 */       table_contents.setColBorderColor(0, 17, new Color(208, 206, 206, 0));
/*  336 */       table_contents.setColBorderColor(0, 18, new Color(208, 206, 206, 0));
/*  337 */       table_contents.setColBorderColor(0, 19, new Color(208, 206, 206, 0));
/*  338 */       table_contents.setColBorderColor(0, 20, new Color(208, 206, 206, 0));
/*  339 */       table_contents.setColBorderColor(0, 21, new Color(208, 206, 206, 0));
/*      */ 
/*      */       
/*  342 */       table_contents.setSpan(1, 0, new Dimension(22, 1));
/*  343 */       table_contents.setRowHeight(1, 1);
/*  344 */       table_contents.setRowBackground(1, Color.white);
/*  345 */       table_contents.setRowForeground(1, Color.black);
/*  346 */       table_contents.setRowBorderColor(1, SHADED_AREA_COLOR);
/*  347 */       setColBorderColors(1, -1, NUM_COLS - 1, table_contents, new Color(208, 206, 206, 0));
/*  348 */       setColBorders(1, -1, NUM_COLS - 1, table_contents, 0);
/*      */ 
/*      */ 
/*      */       
/*  352 */       int nextRow = 0;
/*      */       
/*  354 */       Vector sortedStreetDatesVector = MilestoneHelper.sortDates(streetDatesVector);
/*      */       
/*  356 */       hbandHeader.setHeight(1.0F);
/*  357 */       hbandHeader.setShrinkToFit(true);
/*  358 */       hbandHeader.setVisible(true);
/*  359 */       hbandHeader.setBottomBorder(0);
/*      */       
/*  361 */       hbandHeader.addTable(table_contents, new Rectangle(800, 800));
/*      */ 
/*      */ 
/*      */       
/*  365 */       int totalCount = 0;
/*  366 */       int tenth = 1;
/*      */       
/*  368 */       for (int a = 0; a < sortedStreetDatesVector.size(); a++) {
/*      */         
/*  370 */         totalCount++;
/*  371 */         String datesC = (String)sortedStreetDatesVector.get(a);
/*  372 */         Hashtable configTableC = (Hashtable)selTable.get(datesC);
/*      */         
/*  374 */         if (configTableC != null) {
/*      */           
/*  376 */           Enumeration monthsC = configTableC.keys();
/*  377 */           Vector monthVectorC = new Vector();
/*      */           
/*  379 */           while (monthsC.hasMoreElements())
/*      */           {
/*  381 */             monthVectorC.add((String)monthsC.nextElement());
/*      */           }
/*      */           
/*  384 */           Object[] configsArrayC = null;
/*  385 */           configsArrayC = monthVectorC.toArray();
/*      */           
/*  387 */           for (int b = 0; b < configsArrayC.length; b++) {
/*      */             
/*  389 */             totalCount++;
/*  390 */             String monthNameC = (String)configsArrayC[b];
/*      */             
/*  392 */             Vector selectionsC = (Vector)configTableC.get(monthNameC);
/*  393 */             if (selectionsC == null) {
/*  394 */               selectionsC = new Vector();
/*      */             }
/*  396 */             totalCount += selectionsC.size();
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  401 */       tenth = (totalCount > 10) ? (totalCount / 10) : 1;
/*      */       
/*  403 */       HttpServletResponse sresponse = context.getResponse();
/*  404 */       context.putDelivery("status", new String("start_report"));
/*  405 */       context.putDelivery("percent", new String("10"));
/*  406 */       context.includeJSP("status.jsp", "hiddenFrame");
/*  407 */       sresponse.setContentType("text/plain");
/*      */       try {
/*  409 */         sresponse.flushBuffer();
/*      */       }
/*  411 */       catch (IOException iOException) {}
/*      */ 
/*      */       
/*  414 */       int recordCount = 0;
/*  415 */       int count = 0;
/*      */ 
/*      */       
/*  418 */       for (int n = 0; n < sortedStreetDatesVector.size(); n++) {
/*      */ 
/*      */ 
/*      */         
/*  422 */         if (count < recordCount / tenth) {
/*      */           
/*  424 */           count = recordCount / tenth;
/*  425 */           sresponse = context.getResponse();
/*  426 */           context.putDelivery("status", new String("start_report"));
/*  427 */           context.putDelivery("percent", new String(String.valueOf(count * 10)));
/*  428 */           context.includeJSP("status.jsp", "hiddenFrame");
/*  429 */           sresponse.setContentType("text/plain");
/*      */           try {
/*  431 */             sresponse.flushBuffer();
/*      */           }
/*  433 */           catch (IOException iOException) {}
/*      */         } 
/*      */ 
/*      */         
/*  437 */         recordCount++;
/*      */ 
/*      */         
/*  440 */         int piTotal = 0;
/*  441 */         int kmTotal = 0;
/*  442 */         int haTotal = 0;
/*  443 */         int glTotal = 0;
/*  444 */         int ciTotal = 0;
/*  445 */         int paTotal = 0;
/*  446 */         int civTotal = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  452 */         Hashtable plantTotals = buildTotals(plantIdandName);
/*      */         
/*  454 */         String theStreetDate = (String)sortedStreetDatesVector.elementAt(n);
/*  455 */         String theStreetDateText = !theStreetDate.trim().equals("") ? theStreetDate : "Other";
/*      */         
/*  457 */         footer.setVisible(true);
/*  458 */         footer.setHeight(0.05F);
/*  459 */         footer.setShrinkToFit(true);
/*  460 */         footer.setBottomBorder(0);
/*  461 */         nextRow = 0;
/*      */ 
/*      */         
/*  464 */         Hashtable configTable = (Hashtable)selTable.get(theStreetDate);
/*      */         
/*  466 */         if (configTable != null) {
/*      */           
/*  468 */           Enumeration configsEnum = configTable.keys();
/*      */           
/*  470 */           Vector configsVector = new Vector();
/*      */           
/*  472 */           while (configsEnum.hasMoreElements())
/*      */           {
/*  474 */             configsVector.add((String)configsEnum.nextElement());
/*      */           }
/*      */           
/*  477 */           Object[] configsList = configsVector.toArray();
/*      */           
/*  479 */           Arrays.sort(configsList, new StringComparator());
/*      */ 
/*      */           
/*  482 */           for (int x = 0; x < configsList.length; x++) {
/*      */ 
/*      */             
/*  485 */             nextRow = 0;
/*      */ 
/*      */             
/*  488 */             theConfig = (String)configsList[x];
/*      */ 
/*      */ 
/*      */             
/*  492 */             if (count < recordCount / tenth) {
/*      */               
/*  494 */               count = recordCount / tenth;
/*  495 */               sresponse = context.getResponse();
/*  496 */               context.putDelivery("status", new String("start_report"));
/*  497 */               context.putDelivery("percent", new String(String.valueOf(count * 10)));
/*  498 */               context.includeJSP("status.jsp", "hiddenFrame");
/*  499 */               sresponse.setContentType("text/plain");
/*      */               try {
/*  501 */                 sresponse.flushBuffer();
/*      */               }
/*  503 */               catch (IOException iOException) {}
/*      */             } 
/*      */ 
/*      */             
/*  507 */             recordCount++;
/*      */ 
/*      */             
/*  510 */             footer.setVisible(true);
/*  511 */             footer.setHeight(0.05F);
/*  512 */             footer.setShrinkToFit(true);
/*  513 */             footer.setBottomBorder(0);
/*      */ 
/*      */             
/*  516 */             selections = (Vector)configTable.get(theConfig);
/*  517 */             if (selections == null) {
/*  518 */               selections = new Vector();
/*      */             }
/*      */             
/*  521 */             MilestoneHelper.setSelectionSorting(selections, 15);
/*  522 */             Collections.sort(selections);
/*      */ 
/*      */             
/*  525 */             MilestoneHelper.setSelectionSorting(selections, 14);
/*  526 */             Collections.sort(selections);
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  531 */             MilestoneHelper.setSelectionSorting(selections, 24);
/*  532 */             Collections.sort(selections);
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  537 */             MilestoneHelper.applyManufacturingToSelections(selections);
/*      */ 
/*      */             
/*  540 */             for (int i = 0; i < selections.size(); i++) {
/*      */               
/*  542 */               Selection sel = (Selection)selections.elementAt(i);
/*      */               
/*  544 */               Vector plants = sel.getManufacturingPlants();
/*      */               
/*  546 */               int plantSize = 1;
/*      */               
/*  548 */               if (plants != null && plants.size() > 0) {
/*  549 */                 plantSize = plants.size();
/*      */               }
/*  551 */               for (int plantCount = 0; plantCount < plantSize; plantCount++) {
/*      */ 
/*      */                 
/*  554 */                 if (i == 0 && plantCount == 0) {
/*  555 */                   subTable = new DefaultTableLens(8, NUM_COLS);
/*      */                 } else {
/*      */                   
/*  558 */                   subTable = new DefaultTableLens(2, NUM_COLS);
/*      */                 } 
/*      */                 
/*  561 */                 Plant p = null;
/*      */                 
/*  563 */                 if (plants != null && plants.size() > 0) {
/*  564 */                   p = (Plant)plants.get(plantCount);
/*      */                 }
/*  566 */                 nextRow = 0;
/*      */                 
/*  568 */                 subTable.setColWidth(0, 140);
/*  569 */                 subTable.setColWidth(1, 150);
/*  570 */                 subTable.setColWidth(2, 10);
/*  571 */                 subTable.setColWidth(3, 110);
/*  572 */                 subTable.setColWidth(4, 25);
/*  573 */                 subTable.setColWidth(5, 247);
/*  574 */                 subTable.setColWidth(6, 140);
/*  575 */                 subTable.setColWidth(7, 140);
/*  576 */                 subTable.setColWidth(8, 100);
/*  577 */                 subTable.setColWidth(9, 45);
/*  578 */                 subTable.setColWidth(10, 110);
/*  579 */                 subTable.setColWidth(11, 80);
/*  580 */                 subTable.setColWidth(12, 85);
/*  581 */                 subTable.setColWidth(13, 85);
/*  582 */                 subTable.setColWidth(14, 85);
/*  583 */                 subTable.setColWidth(15, 85);
/*  584 */                 subTable.setColWidth(16, 85);
/*  585 */                 subTable.setColWidth(17, 85);
/*  586 */                 subTable.setColWidth(18, 85);
/*  587 */                 subTable.setColWidth(19, 85);
/*  588 */                 subTable.setColWidth(20, 85);
/*  589 */                 subTable.setColWidth(21, 88);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  595 */                 subTable.setRowBorder(0, 4097);
/*      */ 
/*      */                 
/*  598 */                 subTable.setLineWrap(0, 0, false);
/*  599 */                 subTable.setLineWrap(0, 1, false);
/*  600 */                 subTable.setLineWrap(0, 2, false);
/*  601 */                 subTable.setLineWrap(0, 3, false);
/*  602 */                 subTable.setLineWrap(0, 4, false);
/*  603 */                 subTable.setLineWrap(0, 5, false);
/*  604 */                 subTable.setLineWrap(0, 6, false);
/*  605 */                 subTable.setLineWrap(0, 7, false);
/*  606 */                 subTable.setLineWrap(0, 8, false);
/*  607 */                 subTable.setLineWrap(0, 9, false);
/*  608 */                 subTable.setLineWrap(0, 10, false);
/*  609 */                 subTable.setLineWrap(0, 11, false);
/*  610 */                 subTable.setLineWrap(0, 12, false);
/*  611 */                 subTable.setLineWrap(0, 13, false);
/*  612 */                 subTable.setLineWrap(0, 14, false);
/*  613 */                 subTable.setLineWrap(0, 15, false);
/*  614 */                 subTable.setLineWrap(0, 16, false);
/*  615 */                 subTable.setLineWrap(0, 17, false);
/*  616 */                 subTable.setLineWrap(0, 18, false);
/*  617 */                 subTable.setLineWrap(0, 19, false);
/*  618 */                 subTable.setLineWrap(0, 20, false);
/*  619 */                 subTable.setLineWrap(0, 21, false);
/*      */                 
/*  621 */                 subTable.setAlignment(0, 0, 34);
/*  622 */                 subTable.setAlignment(0, 1, 34);
/*  623 */                 subTable.setAlignment(0, 2, 34);
/*  624 */                 subTable.setAlignment(0, 3, 34);
/*  625 */                 subTable.setAlignment(0, 4, 34);
/*  626 */                 subTable.setAlignment(0, 5, 36);
/*  627 */                 subTable.setAlignment(0, 6, 33);
/*  628 */                 subTable.setAlignment(0, 7, 34);
/*  629 */                 subTable.setAlignment(0, 8, 34);
/*  630 */                 subTable.setAlignment(0, 9, 34);
/*  631 */                 subTable.setAlignment(0, 10, 34);
/*  632 */                 subTable.setAlignment(0, 11, 34);
/*  633 */                 subTable.setAlignment(0, 12, 34);
/*  634 */                 subTable.setAlignment(0, 13, 34);
/*  635 */                 subTable.setAlignment(0, 14, 34);
/*  636 */                 subTable.setAlignment(0, 15, 34);
/*  637 */                 subTable.setAlignment(0, 16, 34);
/*  638 */                 subTable.setAlignment(0, 17, 34);
/*  639 */                 subTable.setAlignment(0, 18, 34);
/*  640 */                 subTable.setAlignment(0, 19, 34);
/*  641 */                 subTable.setAlignment(0, 20, 34);
/*  642 */                 subTable.setAlignment(0, 21, 34);
/*      */                 
/*  644 */                 int artistLength = 0, labelLength = 0, titleLength = 0, idLength = 0;
/*      */                 
/*  646 */                 if (count < recordCount / tenth) {
/*      */                   
/*  648 */                   count = recordCount / tenth;
/*  649 */                   sresponse = context.getResponse();
/*  650 */                   context.putDelivery("status", new String("start_report"));
/*  651 */                   context.putDelivery("percent", new String(String.valueOf(count * 10)));
/*  652 */                   context.includeJSP("status.jsp", "hiddenFrame");
/*  653 */                   sresponse.setContentType("text/plain");
/*      */                   try {
/*  655 */                     sresponse.flushBuffer();
/*      */                   }
/*  657 */                   catch (IOException iOException) {}
/*      */                 } 
/*      */ 
/*      */                 
/*  661 */                 recordCount++;
/*      */ 
/*      */ 
/*      */                 
/*  665 */                 String selectionNo = SelectionManager.getLookupObjectValue(sel.getPrefixID());
/*  666 */                 if (selectionNo == null)
/*  667 */                   selectionNo = ""; 
/*  668 */                 if (sel != null) {
/*  669 */                   selectionNo = String.valueOf(selectionNo) + sel.getSelectionNo().trim();
/*      */                   
/*  671 */                   idLength = selectionNo.length();
/*      */                 } 
/*      */                 
/*  674 */                 String pd = "";
/*  675 */                 if (sel != null && sel.getPressAndDistribution()) {
/*  676 */                   pd = "* ";
/*      */                 }
/*  678 */                 String selDistribution = SelectionManager.getLookupObjectValue(sel.getDistribution());
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  683 */                 String artist = "";
/*      */                 
/*  685 */                 if (sel != null) {
/*  686 */                   artist = sel.getArtist();
/*      */                 } else {
/*  688 */                   artist = null;
/*  689 */                 }  if (artist != null) {
/*  690 */                   artistLength = artist.length();
/*      */                 }
/*      */                 
/*  693 */                 String comment = "";
/*      */                 try {
/*  695 */                   comment = (sel.getSelectionComments() != null) ? sel.getSelectionComments().trim() : "";
/*      */                 }
/*  697 */                 catch (Exception ex) {
/*      */                   
/*  699 */                   System.out.print("comment:" + ex);
/*      */                 } 
/*      */                 
/*  702 */                 String mfgComment = "";
/*      */                 try {
/*  704 */                   mfgComment = (sel.getManufacturingComments() != null) ? sel.getManufacturingComments().trim() : "";
/*      */                 }
/*  706 */                 catch (Exception ed) {
/*      */                   
/*  708 */                   System.out.print("mfgcoment:" + ed);
/*      */                 } 
/*      */ 
/*      */                 
/*  712 */                 String releasingFamily = "";
/*  713 */                 if (sel != null && sel.getReleaseFamilyId() > 0) {
/*  714 */                   releasingFamily = ReleasingFamily.getName(sel.getReleaseFamilyId());
/*      */                 }
/*      */ 
/*      */                 
/*  718 */                 String label = "";
/*      */                 
/*  720 */                 if (sel != null && sel.getImprint() != null) {
/*  721 */                   label = sel.getImprint();
/*      */                 }
/*      */                 
/*      */                 try {
/*  725 */                   labelLength = label.length() + releasingFamily.length();
/*      */                 }
/*  727 */                 catch (Exception ef) {
/*      */                   
/*  729 */                   System.out.print("labellengh:" + ef);
/*      */                 } 
/*      */                 
/*  732 */                 String titleComments = "";
/*      */                 
/*      */                 try {
/*  735 */                   titleComments = sel.getTitle();
/*      */                 
/*      */                 }
/*  738 */                 catch (Exception rg) {
/*      */                   
/*  740 */                   System.out.print("title coments:" + rg);
/*      */                 } 
/*  742 */                 if (titleComments != null) {
/*  743 */                   titleLength = titleComments.length();
/*      */                 }
/*  745 */                 String poQty = "0";
/*      */                 
/*      */                 try {
/*  748 */                   poQty = (p != null && p.getOrderQty() > 0) ? String.valueOf(p.getOrderQty()) : "0";
/*      */                 }
/*  750 */                 catch (Exception ex) {
/*      */                   
/*  752 */                   System.out.print("poQty:" + ex);
/*      */                 } 
/*  754 */                 String units = "";
/*      */                 
/*      */                 try {
/*  757 */                   units = (sel.getNumberOfUnits() > 0) ? String.valueOf(sel.getNumberOfUnits()) : "";
/*      */                 }
/*  759 */                 catch (Exception ex) {
/*      */                   
/*  761 */                   System.out.print("Units:" + ex);
/*      */                 } 
/*      */                 
/*  764 */                 int poQtyNum = 0;
/*      */ 
/*      */                 
/*      */                 try {
/*  768 */                   poQtyNum = Integer.parseInt(poQty);
/*      */                 }
/*  770 */                 catch (Exception e) {
/*      */ 
/*      */                   
/*  773 */                   System.out.print("poQtyNum:" + e);
/*      */                 } 
/*      */ 
/*      */                 
/*  777 */                 int explodedTotal = 0;
/*      */                 
/*      */                 try {
/*  780 */                   if (poQtyNum > 0 && sel != null && sel.getNumberOfUnits() > 0) {
/*  781 */                     explodedTotal = poQtyNum * sel.getNumberOfUnits();
/*      */                   }
/*  783 */                 } catch (Exception g) {
/*      */                   
/*  785 */                   System.out.print("poQtyNum:" + g);
/*      */                 } 
/*      */                 
/*  788 */                 String plant = "";
/*  789 */                 String plantText = "";
/*      */                 
/*  791 */                 if (p != null && p.getPlant() != null) {
/*      */ 
/*      */                   
/*  794 */                   String plantNo = (p.getPlant().getName() != null) ? p.getPlant().getName() : "";
/*  795 */                   plant = (p.getPlant().getAbbreviation() != null) ? p.getPlant().getAbbreviation() : "";
/*      */ 
/*      */ 
/*      */                   
/*  799 */                   plantText = (p.getPlant().getName() != null) ? p.getPlant().getName() : "";
/*      */ 
/*      */                   
/*  802 */                   String plantId = p.getPlant().getAbbreviation();
/*  803 */                   if (plantIdandName != null && plantIdandName.get(plantId) != null) {
/*  804 */                     plant = (String)plantIdandName.get(plantId);
/*      */                   } else {
/*  806 */                     plant = "";
/*      */                   } 
/*      */ 
/*      */                   
/*  810 */                   int currentTotal = 0;
/*  811 */                   if (tryParseInt((String)plantTotals.get(plantId))) {
/*  812 */                     currentTotal = Integer.parseInt((String)plantTotals.get(plantId));
/*      */                   }
/*  814 */                   int newTotal = currentTotal + explodedTotal;
/*  815 */                   plantTotals.put(plantId, String.valueOf(newTotal));
/*      */                 } 
/*      */ 
/*      */                 
/*  819 */                 String compQty = "0";
/*  820 */                 if (p != null && p.getCompletedQty() > 0) {
/*      */                   
/*      */                   try {
/*  823 */                     compQty = MilestoneHelper.formatQuantityWithCommas(String.valueOf(p.getCompletedQty()));
/*      */                   }
/*  825 */                   catch (Exception f) {
/*      */                     
/*  827 */                     System.out.print("compQty:" + f);
/*      */                   } 
/*      */                 }
/*      */                 
/*  831 */                 Schedule schedule = (sel.getSchedule() != null) ? sel.getSchedule() : null;
/*      */                 
/*  833 */                 Vector tasks = (schedule != null) ? schedule.getTasks() : null;
/*  834 */                 ScheduledTask task = null;
/*      */                 
/*  836 */                 String FM = "";
/*  837 */                 String BOM = "";
/*  838 */                 String PRQ = "";
/*  839 */                 String TAPE = "";
/*  840 */                 String FILM = "";
/*  841 */                 String PAP = "";
/*  842 */                 String STIC = "";
/*  843 */                 String MC = "";
/*  844 */                 String FAP = "";
/*  845 */                 String PSD = "";
/*  846 */                 String dueDateHolidayFlg = "";
/*      */                 
/*  848 */                 String MCvend = "";
/*      */                 
/*  850 */                 boolean hasPPRtask = false;
/*      */                 
/*  852 */                 if (tasks != null) {
/*      */ 
/*      */ 
/*      */                   
/*  856 */                   PSD = "N/A";
/*      */                   
/*  858 */                   for (int j = 0; j < tasks.size(); j++) {
/*      */                     
/*  860 */                     task = (ScheduledTask)tasks.get(j);
/*      */                     
/*  862 */                     String taskAbbrev = MilestoneHelper.getTaskAbbreivationNameById(task.getTaskAbbreviationID());
/*      */ 
/*      */                     
/*  865 */                     String taskVendor = (task.getVendor() != null) ? task.getVendor() : "";
/*  866 */                     taskVendor = taskVendor.equals("\n") ? "" : taskVendor;
/*      */                     
/*  868 */                     if (taskAbbrev.equalsIgnoreCase("F/M")) {
/*      */                       
/*  870 */                       FM = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*  871 */                       if (task.getScheduledTaskStatus() != null && 
/*  872 */                         task.getScheduledTaskStatus().equals("N/A"))
/*      */                       {
/*  874 */                         FM = "N/A";
/*      */                       
/*      */                       }
/*      */                     }
/*  878 */                     else if (taskAbbrev.equalsIgnoreCase("BOM")) {
/*      */                       
/*  880 */                       BOM = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*  881 */                       if (task.getScheduledTaskStatus() != null && 
/*  882 */                         task.getScheduledTaskStatus().equals("N/A"))
/*      */                       {
/*  884 */                         BOM = "N/A";
/*      */                       
/*      */                       }
/*      */                     }
/*  888 */                     else if (taskAbbrev.equalsIgnoreCase("PRQ")) {
/*      */                       
/*  890 */                       PRQ = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*  891 */                       if (task.getScheduledTaskStatus() != null && 
/*  892 */                         task.getScheduledTaskStatus().equals("N/A"))
/*      */                       {
/*  894 */                         PRQ = "N/A";
/*      */                       
/*      */                       }
/*      */                     }
/*  898 */                     else if (taskAbbrev.equalsIgnoreCase("TAPE")) {
/*      */                       
/*  900 */                       TAPE = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*  901 */                       if (task.getScheduledTaskStatus() != null && 
/*  902 */                         task.getScheduledTaskStatus().equals("N/A"))
/*      */                       {
/*  904 */                         TAPE = "N/A";
/*      */                       
/*      */                       }
/*      */                     }
/*  908 */                     else if (taskAbbrev.equalsIgnoreCase("FILM")) {
/*      */                       
/*  910 */                       FILM = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*  911 */                       if (task.getScheduledTaskStatus() != null && 
/*  912 */                         task.getScheduledTaskStatus().equals("N/A"))
/*      */                       {
/*  914 */                         FILM = "N/A";
/*      */                       
/*      */                       }
/*      */                     }
/*  918 */                     else if (taskAbbrev.equalsIgnoreCase("PAP")) {
/*      */                       
/*  920 */                       PAP = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*  921 */                       if (task.getScheduledTaskStatus() != null && 
/*  922 */                         task.getScheduledTaskStatus().equals("N/A"))
/*      */                       {
/*  924 */                         PAP = "N/A";
/*      */                       
/*      */                       }
/*      */                     
/*      */                     }
/*  929 */                     else if (taskAbbrev.equalsIgnoreCase("STIC")) {
/*      */                       
/*  931 */                       STIC = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*  932 */                       if (task.getScheduledTaskStatus() != null && 
/*  933 */                         task.getScheduledTaskStatus().equals("N/A"))
/*      */                       {
/*  935 */                         STIC = "N/A";
/*      */                       
/*      */                       }
/*      */                     }
/*  939 */                     else if (taskAbbrev.equalsIgnoreCase("M/C")) {
/*      */                       
/*  941 */                       MC = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*      */ 
/*      */                       
/*  944 */                       if (task.getScheduledTaskStatus() != null && 
/*  945 */                         task.getScheduledTaskStatus().equals("N/A"))
/*      */                       {
/*  947 */                         MC = "N/A";
/*      */                       }
/*      */ 
/*      */                       
/*  951 */                       MCvend = taskVendor;
/*  952 */                       hasPPRtask = true;
/*      */                     
/*      */                     }
/*  955 */                     else if (taskAbbrev.equalsIgnoreCase("FAP")) {
/*      */                       
/*  957 */                       FAP = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*  958 */                       if (task.getScheduledTaskStatus() != null && 
/*  959 */                         task.getScheduledTaskStatus().equals("N/A"))
/*      */                       {
/*  961 */                         FAP = "N/A";
/*      */                       }
/*      */                     }
/*  964 */                     else if (taskAbbrev.equalsIgnoreCase("PSD")) {
/*      */                       
/*  966 */                       if (task.getScheduledTaskStatus() != null && 
/*  967 */                         task.getScheduledTaskStatus().equals("N/A")) {
/*      */                         
/*  969 */                         PSD = "N/A";
/*      */ 
/*      */                       
/*      */                       }
/*      */                       else {
/*      */ 
/*      */                         
/*  976 */                         dueDateHolidayFlg = MilestoneHelper.getDayType(sel.getCalendarGroup(), task.getDueDate());
/*  977 */                         String PsdCompDt = (task.getCompletionDate() == null) ? "" : MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*  978 */                         String PsdDueDt = (task.getDueDate() == null) ? "" : MilestoneHelper.getFormatedDate(task.getDueDate());
/*  979 */                         PSD = String.valueOf(PsdCompDt) + "\n" + PsdDueDt + " " + dueDateHolidayFlg;
/*      */                       } 
/*      */                     } 
/*  982 */                     task = null;
/*      */                   } 
/*      */                 } 
/*      */                 
/*  986 */                 nextRow = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  991 */                 if (i == 0 && plantCount == 0) {
/*      */ 
/*      */                   
/*  994 */                   nextRow = ReportHandler.insertLightGrayHeader(subTable, theStreetDateText, nextRow, 22);
/*      */                   
/*  996 */                   subTable.setRowBorder(nextRow, 4097);
/*  997 */                   subTable.setRowBorder(nextRow + 1, 4097);
/*  998 */                   setColBorders(nextRow, -1, NUM_COLS - 1, subTable, 0);
/*  999 */                   subTable.setColBorderColor(nextRow, Color.white);
/* 1000 */                   setColBorderColors(nextRow, -1, NUM_COLS - 1, subTable, Color.white);
/* 1001 */                   subTable.setSpan(nextRow, 0, new Dimension(22, 1));
/* 1002 */                   subTable.setObject(nextRow, 0, theConfig);
/* 1003 */                   subTable.setAlignment(0, 0, 33);
/* 1004 */                   subTable.setRowFont(nextRow, new Font("Arial", 1, 9));
/*      */                   
/* 1006 */                   subTable.setRowBorderColor(-1, Color.lightGray);
/* 1007 */                   subTable.setRowBorderColor(Color.lightGray);
/*      */                   
/* 1009 */                   nextRow++;
/*      */ 
/*      */ 
/*      */                   
/* 1013 */                   subTable.setHeaderRowCount(0);
/* 1014 */                   subTable.setSpan(nextRow, 0, new Dimension(22, 1));
/* 1015 */                   subTable.setRowHeight(nextRow, 2);
/* 1016 */                   subTable.setRowBackground(nextRow, Color.white);
/* 1017 */                   subTable.setRowForeground(nextRow, Color.black);
/* 1018 */                   setColBorderColors(nextRow, -1, NUM_COLS - 1, subTable, new Color(208, 206, 206, 0));
/* 1019 */                   setColBorders(nextRow, -1, NUM_COLS - 1, subTable, 0);
/*      */ 
/*      */                   
/* 1022 */                   nextRow++;
/*      */                 } 
/*      */ 
/*      */                 
/* 1026 */                 subTable.setRowInsets(nextRow, new Insets(0, 0, 5, 0));
/*      */                 
/* 1028 */                 if (sel.getSpecialPackaging()) {
/*      */                   
/* 1030 */                   if (BOM.length() > 0) {
/*      */                     
/* 1032 */                     BOM = String.valueOf(BOM) + "\n";
/* 1033 */                     subTable.setRowHeight(nextRow, 15);
/*      */                   } 
/* 1035 */                   BOM = String.valueOf(BOM) + "sp. pkg";
/*      */                 } 
/*      */ 
/*      */                 
/* 1039 */                 if (plantText.length() > 13 || labelLength > 17 || artistLength > 27) {
/*      */                   
/* 1041 */                   subTable.setRowHeight(nextRow, 15);
/* 1042 */                   subTable.setRowHeight(nextRow + 1, 1);
/*      */                 } 
/*      */                 
/* 1045 */                 subTable.setRowInsets(nextRow, new Insets(0, 0, 5, 0));
/*      */                 
/* 1047 */                 subTable.setSpan(nextRow, 0, new Dimension(1, 2));
/* 1048 */                 subTable.setSpan(nextRow, 1, new Dimension(2, 2));
/* 1049 */                 subTable.setSpan(nextRow, 2, new Dimension(1, 2));
/* 1050 */                 subTable.setSpan(nextRow, 3, new Dimension(1, 2));
/* 1051 */                 subTable.setSpan(nextRow, 4, new Dimension(1, 2));
/* 1052 */                 subTable.setSpan(nextRow, 5, new Dimension(1, 2));
/* 1053 */                 subTable.setSpan(nextRow, 6, new Dimension(2, 2));
/* 1054 */                 subTable.setSpan(nextRow, 7, new Dimension(1, 2));
/* 1055 */                 subTable.setSpan(nextRow, 8, new Dimension(1, 2));
/* 1056 */                 subTable.setSpan(nextRow, 9, new Dimension(1, 2));
/* 1057 */                 subTable.setSpan(nextRow, 10, new Dimension(1, 2));
/* 1058 */                 subTable.setSpan(nextRow, 11, new Dimension(1, 2));
/* 1059 */                 subTable.setSpan(nextRow, 12, new Dimension(1, 2));
/* 1060 */                 subTable.setSpan(nextRow, 13, new Dimension(1, 2));
/* 1061 */                 subTable.setSpan(nextRow, 14, new Dimension(1, 2));
/* 1062 */                 subTable.setSpan(nextRow, 15, new Dimension(1, 2));
/* 1063 */                 subTable.setSpan(nextRow, 16, new Dimension(1, 2));
/* 1064 */                 subTable.setSpan(nextRow, 17, new Dimension(1, 2));
/* 1065 */                 subTable.setSpan(nextRow, 18, new Dimension(1, 2));
/* 1066 */                 subTable.setSpan(nextRow, 19, new Dimension(1, 2));
/* 1067 */                 subTable.setSpan(nextRow, 20, new Dimension(1, 2));
/* 1068 */                 subTable.setSpan(nextRow, 21, new Dimension(1, 2));
/*      */                 
/* 1070 */                 if (idLength == 13 && pd.trim().equals("*")) {
/* 1071 */                   pd = "*";
/*      */                 }
/*      */ 
/*      */ 
/*      */                 
/* 1076 */                 subTable.setObject(nextRow, 0, String.valueOf(pd) + selectionNo);
/* 1077 */                 subTable.setObject(nextRow, 1, String.valueOf(releasingFamily) + " - " + label);
/* 1078 */                 subTable.setObject(nextRow, 3, plantText);
/*      */                 
/* 1080 */                 subTable.setObject(nextRow, 4, selDistribution);
/* 1081 */                 subTable.setObject(nextRow, 5, artist);
/*      */                 
/* 1083 */                 subTable.setSpan(nextRow, 6, new Dimension(2, 1));
/* 1084 */                 subTable.setSpan(nextRow + 1, 6, new Dimension(2, 1));
/*      */ 
/*      */                 
/* 1087 */                 subTable.setObject(nextRow, 6, titleComments);
/* 1088 */                 subTable.setObject(nextRow, 8, MilestoneHelper.formatQuantityWithCommas(poQty));
/* 1089 */                 subTable.setObject(nextRow, 9, units);
/* 1090 */                 subTable.setObject(nextRow, 10, MilestoneHelper.formatQuantityWithCommas(String.valueOf(explodedTotal)));
/* 1091 */                 subTable.setObject(nextRow, 11, MilestoneHelper.formatQuantityWithCommas(compQty));
/*      */                 
/* 1093 */                 subTable.setObject(nextRow, 12, FM);
/* 1094 */                 subTable.setObject(nextRow, 13, BOM);
/* 1095 */                 subTable.setObject(nextRow, 15, PRQ);
/* 1096 */                 subTable.setObject(nextRow, 16, TAPE);
/* 1097 */                 subTable.setObject(nextRow, 17, FILM);
/* 1098 */                 subTable.setObject(nextRow, 20, PAP);
/* 1099 */                 subTable.setObject(nextRow, 18, STIC);
/*      */ 
/*      */ 
/*      */                 
/* 1103 */                 if (tasks == null) {
/* 1104 */                   subTable.setObject(nextRow, 19, "");
/*      */                 
/*      */                 }
/* 1107 */                 else if (!hasPPRtask) {
/* 1108 */                   subTable.setObject(nextRow, 19, "N/A");
/*      */ 
/*      */                 
/*      */                 }
/* 1112 */                 else if (MCvend != null && !MCvend.equals("")) {
/* 1113 */                   subTable.setObject(nextRow, 19, String.valueOf(MC) + "\n" + MCvend);
/*      */                 } else {
/*      */                   
/* 1116 */                   subTable.setObject(nextRow, 19, MC);
/*      */                 } 
/*      */ 
/*      */ 
/*      */                 
/* 1121 */                 subTable.setObject(nextRow, 14, FAP);
/* 1122 */                 subTable.setObject(nextRow, 21, PSD);
/*      */ 
/*      */ 
/*      */                 
/* 1126 */                 subTable.setAlignment(nextRow, 0, 4);
/*      */                 
/* 1128 */                 subTable.setLineWrap(nextRow, 1, true);
/* 1129 */                 subTable.setLineWrap(nextRow, 2, true);
/* 1130 */                 subTable.setLineWrap(nextRow, 3, true);
/* 1131 */                 subTable.setLineWrap(nextRow, 4, true);
/* 1132 */                 subTable.setLineWrap(nextRow, 5, true);
/* 1133 */                 subTable.setLineWrap(nextRow, 6, true);
/* 1134 */                 subTable.setLineWrap(nextRow, 7, true);
/* 1135 */                 subTable.setLineWrap(nextRow, 8, true);
/* 1136 */                 subTable.setLineWrap(nextRow, 9, true);
/* 1137 */                 subTable.setLineWrap(nextRow, 10, true);
/* 1138 */                 subTable.setLineWrap(nextRow, 11, true);
/* 1139 */                 subTable.setLineWrap(nextRow, 12, true);
/* 1140 */                 subTable.setLineWrap(nextRow, 13, true);
/* 1141 */                 subTable.setLineWrap(nextRow, 14, true);
/* 1142 */                 subTable.setLineWrap(nextRow, 15, true);
/* 1143 */                 subTable.setLineWrap(nextRow, 16, true);
/* 1144 */                 subTable.setLineWrap(nextRow, 17, true);
/* 1145 */                 subTable.setLineWrap(nextRow, 18, true);
/* 1146 */                 subTable.setLineWrap(nextRow, 19, true);
/* 1147 */                 subTable.setLineWrap(nextRow, 20, true);
/* 1148 */                 subTable.setLineWrap(nextRow, 21, true);
/*      */ 
/*      */                 
/* 1151 */                 subTable.setAlignment(nextRow, 0, 8);
/* 1152 */                 subTable.setAlignment(nextRow, 1, 8);
/* 1153 */                 subTable.setAlignment(nextRow, 2, 8);
/* 1154 */                 subTable.setAlignment(nextRow, 3, 8);
/* 1155 */                 subTable.setAlignment(nextRow, 4, 8);
/* 1156 */                 subTable.setAlignment(nextRow, 5, 8);
/* 1157 */                 subTable.setAlignment(nextRow, 6, 8);
/* 1158 */                 subTable.setAlignment(nextRow, 7, 12);
/* 1159 */                 subTable.setAlignment(nextRow, 8, 12);
/* 1160 */                 subTable.setAlignment(nextRow, 9, 12);
/* 1161 */                 subTable.setAlignment(nextRow, 10, 12);
/* 1162 */                 subTable.setAlignment(nextRow, 11, 12);
/* 1163 */                 subTable.setAlignment(nextRow, 12, 12);
/* 1164 */                 subTable.setAlignment(nextRow, 13, 12);
/* 1165 */                 subTable.setAlignment(nextRow, 14, 12);
/* 1166 */                 subTable.setAlignment(nextRow, 15, 12);
/* 1167 */                 subTable.setAlignment(nextRow, 16, 12);
/* 1168 */                 subTable.setAlignment(nextRow, 17, 12);
/* 1169 */                 subTable.setAlignment(nextRow, 18, 12);
/* 1170 */                 subTable.setAlignment(nextRow, 19, 12);
/* 1171 */                 subTable.setAlignment(nextRow, 20, 12);
/* 1172 */                 subTable.setAlignment(nextRow, 21, 12);
/*      */                 
/* 1174 */                 subTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
/* 1175 */                 subTable.setRowFont(nextRow, new Font("Arial", 0, 6));
/*      */ 
/*      */                 
/* 1178 */                 if (dueDateHolidayFlg != "")
/*      */                 {
/* 1180 */                   subTable.setFont(nextRow, 21, new Font("Arial", 3, 6));
/*      */                 }
/*      */                 
/* 1183 */                 subTable.setColBorderColor(nextRow, -1, new Color(208, 206, 206, 0));
/* 1184 */                 subTable.setColBorderColor(nextRow, 0, new Color(208, 206, 206, 0));
/* 1185 */                 subTable.setColBorderColor(nextRow, 1, new Color(208, 206, 206, 0));
/* 1186 */                 subTable.setColBorderColor(nextRow, 2, new Color(208, 206, 206, 0));
/* 1187 */                 subTable.setColBorderColor(nextRow, 3, new Color(208, 206, 206, 0));
/* 1188 */                 subTable.setColBorderColor(nextRow, 4, new Color(208, 206, 206, 0));
/* 1189 */                 subTable.setColBorderColor(nextRow, 5, new Color(208, 206, 206, 0));
/* 1190 */                 subTable.setColBorderColor(nextRow, 6, new Color(208, 206, 206, 0));
/* 1191 */                 subTable.setColBorderColor(nextRow, 7, new Color(208, 206, 206, 0));
/* 1192 */                 subTable.setColBorderColor(nextRow, 8, new Color(208, 206, 206, 0));
/* 1193 */                 subTable.setColBorderColor(nextRow, 9, new Color(208, 206, 206, 0));
/* 1194 */                 subTable.setColBorderColor(nextRow, 10, new Color(208, 206, 206, 0));
/* 1195 */                 subTable.setColBorderColor(nextRow, 11, new Color(208, 206, 206, 0));
/* 1196 */                 subTable.setColBorderColor(nextRow, 12, new Color(208, 206, 206, 0));
/* 1197 */                 subTable.setColBorderColor(nextRow, 13, new Color(208, 206, 206, 0));
/* 1198 */                 subTable.setColBorderColor(nextRow, 14, new Color(208, 206, 206, 0));
/* 1199 */                 subTable.setColBorderColor(nextRow, 15, new Color(208, 206, 206, 0));
/* 1200 */                 subTable.setColBorderColor(nextRow, 16, new Color(208, 206, 206, 0));
/* 1201 */                 subTable.setColBorderColor(nextRow, 17, new Color(208, 206, 206, 0));
/* 1202 */                 subTable.setColBorderColor(nextRow, 18, new Color(208, 206, 206, 0));
/* 1203 */                 subTable.setColBorderColor(nextRow, 19, new Color(208, 206, 206, 0));
/* 1204 */                 subTable.setColBorderColor(nextRow, 20, new Color(208, 206, 206, 0));
/* 1205 */                 subTable.setColBorderColor(nextRow, 21, new Color(208, 206, 206, 0));
/*      */                 
/* 1207 */                 subTable.setRowBorder(nextRow, 4097);
/*      */                 
/* 1209 */                 nextRow++;
/*      */ 
/*      */                 
/* 1212 */                 subTable.setRowBorderColor(nextRow - 2, Color.lightGray);
/* 1213 */                 subTable.setRowBorderColor(nextRow - 1, Color.white);
/* 1214 */                 subTable.setRowBorderColor(nextRow, Color.lightGray);
/*      */                 
/* 1216 */                 subTable.setRowBorderColor(nextRow, 0, Color.lightGray);
/* 1217 */                 subTable.setRowBorderColor(nextRow, 6, Color.lightGray);
/*      */                 
/* 1219 */                 subTable.setColBorderColor(nextRow, -1, new Color(208, 206, 206, 0));
/* 1220 */                 subTable.setColBorderColor(nextRow, 0, new Color(208, 206, 206, 0));
/* 1221 */                 subTable.setColBorderColor(nextRow, 1, new Color(208, 206, 206, 0));
/* 1222 */                 subTable.setColBorderColor(nextRow, 2, new Color(208, 206, 206, 0));
/* 1223 */                 subTable.setColBorderColor(nextRow, 3, new Color(208, 206, 206, 0));
/* 1224 */                 subTable.setColBorderColor(nextRow, 4, new Color(208, 206, 206, 0));
/* 1225 */                 subTable.setColBorderColor(nextRow, 5, new Color(208, 206, 206, 0));
/* 1226 */                 subTable.setColBorderColor(nextRow, 6, new Color(208, 206, 206, 0));
/* 1227 */                 subTable.setColBorderColor(nextRow, 7, new Color(208, 206, 206, 0));
/* 1228 */                 subTable.setColBorderColor(nextRow, 8, new Color(208, 206, 206, 0));
/* 1229 */                 subTable.setColBorderColor(nextRow, 9, new Color(208, 206, 206, 0));
/* 1230 */                 subTable.setColBorderColor(nextRow, 10, new Color(208, 206, 206, 0));
/* 1231 */                 subTable.setColBorderColor(nextRow, 11, new Color(208, 206, 206, 0));
/* 1232 */                 subTable.setColBorderColor(nextRow, 12, new Color(208, 206, 206, 0));
/* 1233 */                 subTable.setColBorderColor(nextRow, 13, new Color(208, 206, 206, 0));
/* 1234 */                 subTable.setColBorderColor(nextRow, 14, new Color(208, 206, 206, 0));
/* 1235 */                 subTable.setColBorderColor(nextRow, 15, new Color(208, 206, 206, 0));
/* 1236 */                 subTable.setColBorderColor(nextRow, 16, new Color(208, 206, 206, 0));
/* 1237 */                 subTable.setColBorderColor(nextRow, 17, new Color(208, 206, 206, 0));
/* 1238 */                 subTable.setColBorderColor(nextRow, 18, new Color(208, 206, 206, 0));
/* 1239 */                 subTable.setColBorderColor(nextRow, 19, new Color(208, 206, 206, 0));
/* 1240 */                 subTable.setColBorderColor(nextRow, 20, new Color(208, 206, 206, 0));
/* 1241 */                 subTable.setColBorderColor(nextRow, 21, new Color(208, 206, 206, 0));
/*      */                 
/* 1243 */                 subTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
/*      */ 
/*      */                 
/* 1246 */                 if (!MCvend.equals("")) {
/* 1247 */                   subTable.setRowHeight(nextRow, 15);
/*      */                 }
/*      */ 
/*      */                 
/* 1251 */                 if (mfgComment != null && mfgComment.trim().length() > 0) {
/* 1252 */                   subTable.setRowAutoSize(true);
/* 1253 */                   subTable.setObject(nextRow, 6, mfgComment);
/* 1254 */                   subTable.setFont(nextRow, 6, new Font("Arial", 2, 6));
/*      */ 
/*      */ 
/*      */                   
/* 1258 */                   if (mfgComment.length() > 40) {
/* 1259 */                     subTable.setRowHeight(nextRow, 60);
/*      */                   }
/* 1261 */                   else if (mfgComment.length() > 10) {
/* 1262 */                     subTable.setRowHeight(nextRow, 15);
/*      */                   } else {
/* 1264 */                     subTable.setRowHeight(nextRow, 15);
/*      */ 
/*      */ 
/*      */                   
/*      */                   }
/*      */ 
/*      */ 
/*      */                 
/*      */                 }
/* 1273 */                 else if (artistLength < 26 && labelLength < 21 && idLength + pd.length() < 15) {
/* 1274 */                   subTable.setRowHeight(nextRow, 3);
/*      */                 
/*      */                 }
/* 1277 */                 else if (labelLength > 30) {
/* 1278 */                   subTable.setRowHeight(nextRow, 8);
/*      */                 } 
/*      */                 
/* 1281 */                 body = new SectionBand(report);
/*      */ 
/*      */                 
/* 1284 */                 if (mfgComment != null && mfgComment.trim().length() > 0) {
/* 1285 */                   double lfLineCount = 1.5D;
/* 1286 */                   if (mfgComment.trim().length() > 100)
/* 1287 */                     lfLineCount = 8.0D; 
/* 1288 */                   if (mfgComment.trim().length() > 40)
/* 1289 */                     lfLineCount = 4.0D; 
/* 1290 */                   body.setHeight((float)lfLineCount);
/*      */                 } else {
/*      */                   
/* 1293 */                   body.setHeight(1.5F);
/*      */                 } 
/* 1295 */                 body.addTable(subTable, new Rectangle(800, 800));
/* 1296 */                 body.setBottomBorder(0);
/* 1297 */                 body.setTopBorder(0);
/* 1298 */                 body.setShrinkToFit(true);
/* 1299 */                 body.setVisible(true);
/* 1300 */                 group = new DefaultSectionLens(null, group, body);
/*      */ 
/*      */                 
/* 1303 */                 nextRow = 0;
/*      */               } 
/*      */             } 
/*      */ 
/*      */ 
/*      */             
/* 1309 */             if (theConfig.equals("Compact Disc") && totalsExist(plantTotals)) {
/*      */               
/* 1311 */               nextRow = 0;
/*      */               
/* 1313 */               subTable = new DefaultTableLens(1, 14);
/*      */               
/* 1315 */               subTable.setColWidth(0, 90);
/* 1316 */               subTable.setColWidth(1, 90);
/* 1317 */               subTable.setColWidth(2, 90);
/* 1318 */               subTable.setColWidth(3, 90);
/* 1319 */               subTable.setColWidth(4, 90);
/* 1320 */               subTable.setColWidth(5, 90);
/* 1321 */               subTable.setColWidth(6, 90);
/* 1322 */               subTable.setColWidth(7, 90);
/* 1323 */               subTable.setColWidth(8, 90);
/* 1324 */               subTable.setColWidth(9, 90);
/* 1325 */               subTable.setColWidth(10, 90);
/* 1326 */               subTable.setColWidth(11, 90);
/* 1327 */               subTable.setColWidth(12, 90);
/* 1328 */               subTable.setColWidth(13, 90);
/*      */               
/* 1330 */               subTable.setRowBorder(0);
/*      */ 
/*      */               
/* 1333 */               printTotals(plantTotals, plantIdandName, subTable, nextRow);
/*      */               
/* 1335 */               subTable.setFont(nextRow, 0, new Font("Arial", 1, 7));
/* 1336 */               subTable.setFont(nextRow, 1, new Font("Arial", 0, 7));
/* 1337 */               subTable.setFont(nextRow, 2, new Font("Arial", 1, 7));
/* 1338 */               subTable.setFont(nextRow, 3, new Font("Arial", 0, 7));
/* 1339 */               subTable.setFont(nextRow, 4, new Font("Arial", 1, 7));
/* 1340 */               subTable.setFont(nextRow, 5, new Font("Arial", 0, 7));
/* 1341 */               subTable.setFont(nextRow, 6, new Font("Arial", 1, 7));
/* 1342 */               subTable.setFont(nextRow, 7, new Font("Arial", 0, 7));
/* 1343 */               subTable.setFont(nextRow, 8, new Font("Arial", 1, 7));
/* 1344 */               subTable.setFont(nextRow, 9, new Font("Arial", 0, 7));
/* 1345 */               subTable.setFont(nextRow, 10, new Font("Arial", 1, 7));
/* 1346 */               subTable.setFont(nextRow, 11, new Font("Arial", 0, 7));
/* 1347 */               subTable.setFont(nextRow, 12, new Font("Arial", 1, 7));
/* 1348 */               subTable.setFont(nextRow, 13, new Font("Arial", 0, 7));
/*      */ 
/*      */               
/* 1351 */               setColBorders(nextRow, -1, NUM_COLS - 1, subTable, 0);
/* 1352 */               setColBorderColors(nextRow, -1, NUM_COLS - 1, subTable, Color.white);
/* 1353 */               subTable.setRowBorder(nextRow, 0);
/*      */               
/* 1355 */               body = new SectionBand(report);
/* 1356 */               body.setHeight(1.0F);
/*      */               
/* 1358 */               body.addTable(subTable, new Rectangle(800, 800));
/* 1359 */               body.setBottomBorder(0);
/* 1360 */               body.setTopBorder(0);
/* 1361 */               body.setShrinkToFit(true);
/* 1362 */               body.setVisible(true);
/*      */               
/* 1364 */               group = new DefaultSectionLens(null, group, body);
/*      */               
/* 1366 */               nextRow = 0;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1375 */       group = new DefaultSectionLens(hbandHeader, group, null);
/* 1376 */       report.addSection(group, rowCountTable);
/* 1377 */       group = null;
/*      */     
/*      */     }
/* 1380 */     catch (Exception e) {
/* 1381 */       System.out.println(">>>>>>>>errors");
/* 1382 */       System.out.println(">>>>>>>>ReportHandler.fillUmlNewReleaseMasterScheduleSubHandler(): exception: " + e.getMessage());
/* 1383 */       System.out.println(">>>>>>>>ReportHandler.fillUmlNewReleaseMasterScheduleSubHandler(): exception: " + e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void setColBorderColors(int rowNum, int start, int end, DefaultTableLens table, Color color) {
/* 1392 */     end++;
/*      */     
/* 1394 */     for (int i = start; i < end; i++)
/*      */     {
/* 1396 */       table.setColBorderColor(rowNum, i, Color.white);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void setRowBorderColors(int rowNum, int start, int end, DefaultTableLens table, Color color) {
/* 1405 */     end++;
/*      */     
/* 1407 */     for (int i = start; i < end; i++)
/*      */     {
/* 1409 */       table.setRowBorderColor(rowNum, i, Color.white);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void setColBorders(int rowNum, int start, int end, DefaultTableLens table, int size) {
/* 1418 */     end++;
/*      */     
/* 1420 */     for (int i = start; i < end; i++)
/*      */     {
/* 1422 */       table.setColBorder(rowNum, i, size);
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
/*      */   private static Hashtable buildPlantHash() {
/* 1435 */     plantNames = new Hashtable();
/* 1436 */     String plantQuery = " SELECT det_value, description FROM vi_Lookup_SubDetail WHERE field_id = 22";
/* 1437 */     JdbcConnector connector = MilestoneHelper.getConnector(plantQuery);
/* 1438 */     connector.setForwardOnly(false);
/* 1439 */     connector.runQuery();
/*      */ 
/*      */     
/* 1442 */     while (connector.more()) {
/*      */       
/* 1444 */       String plantId = connector.getField("det_value");
/* 1445 */       String plantDescription = connector.getField("description");
/*      */       
/* 1447 */       if (plantId != null && !plantId.equals("") && plantDescription != null && !plantDescription.equals("")) {
/* 1448 */         plantNames.put(plantId, plantDescription);
/*      */       }
/*      */       
/* 1451 */       connector.next();
/*      */     } 
/* 1453 */     connector.close();
/* 1454 */     return plantNames;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Hashtable buildTotals(Hashtable namesHash) {
/* 1464 */     Enumeration keysEnum = namesHash.keys();
/* 1465 */     Hashtable plantTotals = new Hashtable();
/* 1466 */     while (keysEnum.hasMoreElements())
/* 1467 */       plantTotals.put((String)keysEnum.nextElement(), "0"); 
/* 1468 */     return plantTotals;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean totalsExist(Hashtable plantTotals) {
/* 1477 */     Enumeration keysEnum = plantTotals.keys();
/* 1478 */     while (keysEnum.hasMoreElements()) {
/* 1479 */       int total = Integer.parseInt((String)plantTotals.get(keysEnum.nextElement()));
/* 1480 */       if (total > 0) {
/* 1481 */         return true;
/*      */       }
/*      */     } 
/* 1484 */     return false;
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
/*      */   private static void printTotals(Hashtable plantTotals, Hashtable plantNames, DefaultTableLens subTable, int nextRow) {
/* 1496 */     Enumeration keysEnum = plantTotals.keys();
/* 1497 */     Vector keys = new Vector();
/* 1498 */     int columnCount = 0;
/* 1499 */     while (keysEnum.hasMoreElements()) {
/* 1500 */       keys.add(keysEnum.nextElement());
/*      */     }
/*      */     
/* 1503 */     int maxCol = 14;
/*      */     
/* 1505 */     for (int i = 0; i < keys.size(); i++) {
/*      */       
/* 1507 */       String idString = (plantTotals.get(keys.get(i)) != null) ? (String)plantTotals.get(keys.get(i)) : "0";
/* 1508 */       int total = Integer.parseInt(idString);
/* 1509 */       String plantName = (plantNames.get(keys.get(i)) != null) ? (String)plantNames.get(keys.get(i)) : "";
/*      */       
/* 1511 */       if (total > 0 && columnCount < 14) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1516 */         subTable.setObject(nextRow, columnCount, String.valueOf(plantName) + ":\n" + MilestoneHelper.formatQuantityWithCommas(String.valueOf(total)));
/* 1517 */         columnCount++;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\UmlNewReleaseMasterScheduleSubHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */