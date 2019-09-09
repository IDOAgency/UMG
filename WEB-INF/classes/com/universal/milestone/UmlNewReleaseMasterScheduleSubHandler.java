/*      */ package WEB-INF.classes.com.universal.milestone;
/*      */ import com.techempower.gemini.Context;
/*      */ import com.techempower.gemini.GeminiApplication;
/*      */ import com.universal.milestone.Form;
/*      */ import com.universal.milestone.JdbcConnector;
/*      */ import com.universal.milestone.MilestoneHelper;
/*      */ import com.universal.milestone.Plant;
/*      */ import com.universal.milestone.Schedule;
/*      */ import com.universal.milestone.ScheduledTask;
/*      */ import com.universal.milestone.Selection;
/*      */ import com.universal.milestone.SelectionManager;
/*      */ import com.universal.milestone.StringComparator;
/*      */ import com.universal.milestone.UmlNewReleaseMasterScheduleSubHandler;
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
/*      */ import java.util.Calendar;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Vector;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ 
/*      */ public class UmlNewReleaseMasterScheduleSubHandler extends SecureHandler {
/*   34 */   public static int NUM_COLS = 22; public static final String COMPONENT_CODE = "hUsu"; public GeminiApplication application;
/*      */   public ComponentLog log;
/*      */   
/*      */   public UmlNewReleaseMasterScheduleSubHandler(GeminiApplication application) {
/*   38 */     this.application = application;
/*   39 */     this.log = application.getLog("hUsu");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*   44 */   public String getDescription() { return "Sub Report"; }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean tryParseInt(String value) {
/*      */     try {
/*   51 */       Integer.parseInt(value);
/*   52 */       return true;
/*      */     }
/*   54 */     catch (NumberFormatException numberFormatException) {
/*   55 */       return false;
/*      */     } 
/*      */   }
/*      */   
/*      */   protected static void fillUmlNewReleaseMasterScheduleForPrint(XStyleSheet report, Context context) {
/*   60 */     report.setMargin(new Margin(1.0D, 0.1D, 0.1D, 0.1D));
/*   61 */     report.setFooterFromEdge(0.5D);
/*   62 */     report.setHeaderFromEdge(0.1D);
/*      */     
/*   64 */     SectionBand hbandHeader = new SectionBand(report);
/*   65 */     SectionBand body = new SectionBand(report);
/*   66 */     SectionBand footer = new SectionBand(report);
/*      */     
/*   68 */     DefaultSectionLens group = null;
/*      */     
/*   70 */     DefaultTableLens table_contents = null;
/*   71 */     DefaultTableLens rowCountTable = null;
/*   72 */     DefaultTableLens subTable = null;
/*      */     
/*   74 */     rowCountTable = new DefaultTableLens(2, 10000);
/*      */     
/*   76 */     Color SHADED_AREA_COLOR = Color.lightGray;
/*   77 */     String theConfig = "";
/*      */     
/*      */     try {
/*   80 */       HttpServletResponse sresponse = context.getResponse();
/*   81 */       context.putDelivery("status", new String("start_gathering"));
/*   82 */       context.includeJSP("status.jsp", "hiddenFrame");
/*   83 */       sresponse.setContentType("text/plain");
/*   84 */       sresponse.flushBuffer();
/*      */     }
/*   86 */     catch (Exception exception) {}
/*   87 */     Vector selections = MilestoneHelper.getSelectionsForReport(context);
/*      */     
/*   89 */     Hashtable plantIdandName = buildPlantHash();
/*      */     
/*      */     try {
/*   92 */       HttpServletResponse sresponse = context.getResponse();
/*   93 */       context.putDelivery("status", new String("start_report"));
/*   94 */       context.putDelivery("percent", new String("10"));
/*   95 */       context.includeJSP("status.jsp", "hiddenFrame");
/*   96 */       sresponse.setContentType("text/plain");
/*   97 */       sresponse.flushBuffer();
/*      */     }
/*   99 */     catch (Exception exception) {}
/*      */     
/*      */     try {
/*  102 */       Form reportForm = (Form)context.getSessionValue("reportForm");
/*      */       
/*  104 */       Calendar beginStDate = (reportForm.getStringValue("beginDate") != null && 
/*  105 */         reportForm.getStringValue("beginDate").length() > 0) ? 
/*  106 */         MilestoneHelper.getDate(reportForm.getStringValue("beginDate")) : null;
/*      */       
/*  108 */       Calendar endStDate = (reportForm.getStringValue("endDate") != null && 
/*  109 */         reportForm.getStringValue("endDate").length() > 0) ? 
/*  110 */         MilestoneHelper.getDate(reportForm.getStringValue("endDate")) : null;
/*      */       
/*  112 */       report.setElement("crs_startdate", MilestoneHelper.getFormatedDate(beginStDate));
/*  113 */       report.setElement("crs_enddate", MilestoneHelper.getFormatedDate(endStDate));
/*      */       
/*  115 */       SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
/*  116 */       String todayLong = formatter.format(new Date());
/*  117 */       report.setElement("crs_bottomdate", todayLong);
/*      */       
/*  119 */       Hashtable selTable = MilestoneHelper.groupSelectionsByConfigAndStreetDate(selections);
/*      */       
/*  121 */       Enumeration streetDates = selTable.keys();
/*  122 */       Vector streetDatesVector = new Vector();
/*  123 */       while (streetDates.hasMoreElements()) {
/*  124 */         streetDatesVector.addElement(streetDates.nextElement());
/*      */       }
/*  126 */       int numConfigs = 0;
/*  127 */       for (int i = 0; i < streetDatesVector.size(); i++) {
/*      */         
/*  129 */         String streetDateName = (streetDatesVector.elementAt(i) != null) ? (String)streetDatesVector.elementAt(i) : "";
/*  130 */         Hashtable streetDateTable = (Hashtable)selTable.get(streetDateName);
/*  131 */         if (streetDateTable != null) {
/*  132 */           numConfigs += streetDateTable.size();
/*      */         }
/*      */       } 
/*  135 */       int numExtraRows = 2 + streetDatesVector.size() * 10 + numConfigs;
/*      */       
/*  137 */       int numSelections = selections.size() * 2;
/*  138 */       int numRows = numSelections + numExtraRows;
/*      */       
/*  140 */       numRows -= streetDatesVector.size() * 2 - 1;
/*      */       
/*  142 */       table_contents = new DefaultTableLens(2, NUM_COLS);
/*      */       
/*  144 */       table_contents.setHeaderRowCount(1);
/*  145 */       table_contents.setColWidth(0, 140);
/*  146 */       table_contents.setColWidth(1, 150);
/*  147 */       table_contents.setColWidth(2, 10);
/*  148 */       table_contents.setColWidth(3, 110);
/*  149 */       table_contents.setColWidth(4, 25);
/*  150 */       table_contents.setColWidth(5, 246);
/*  151 */       table_contents.setColWidth(6, 140);
/*  152 */       table_contents.setColWidth(7, 140);
/*  153 */       table_contents.setColWidth(8, 100);
/*  154 */       table_contents.setColWidth(9, 45);
/*  155 */       table_contents.setColWidth(10, 110);
/*  156 */       table_contents.setColWidth(11, 80);
/*  157 */       table_contents.setColWidth(12, 85);
/*  158 */       table_contents.setColWidth(13, 85);
/*  159 */       table_contents.setColWidth(14, 85);
/*  160 */       table_contents.setColWidth(15, 85);
/*  161 */       table_contents.setColWidth(16, 85);
/*  162 */       table_contents.setColWidth(17, 85);
/*  163 */       table_contents.setColWidth(18, 85);
/*  164 */       table_contents.setColWidth(19, 85);
/*  165 */       table_contents.setColWidth(20, 85);
/*  166 */       table_contents.setColWidth(21, 89);
/*      */       
/*  168 */       table_contents.setRowBorder(0, 4097);
/*  169 */       table_contents.setRowBorderColor(-1, SHADED_AREA_COLOR);
/*  170 */       table_contents.setRowBorderColor(0, SHADED_AREA_COLOR);
/*      */       
/*  172 */       table_contents.setAlignment(0, 0, 34);
/*  173 */       table_contents.setAlignment(0, 1, 34);
/*  174 */       table_contents.setAlignment(0, 2, 34);
/*  175 */       table_contents.setAlignment(0, 3, 34);
/*  176 */       table_contents.setAlignment(0, 4, 34);
/*  177 */       table_contents.setAlignment(0, 5, 34);
/*  178 */       table_contents.setAlignment(0, 6, 36);
/*  179 */       table_contents.setAlignment(0, 7, 33);
/*  180 */       table_contents.setAlignment(0, 8, 34);
/*  181 */       table_contents.setAlignment(0, 9, 34);
/*  182 */       table_contents.setAlignment(0, 10, 34);
/*  183 */       table_contents.setAlignment(0, 11, 34);
/*  184 */       table_contents.setAlignment(0, 12, 34);
/*  185 */       table_contents.setAlignment(0, 13, 34);
/*  186 */       table_contents.setAlignment(0, 14, 34);
/*  187 */       table_contents.setAlignment(0, 15, 34);
/*  188 */       table_contents.setAlignment(0, 16, 34);
/*  189 */       table_contents.setAlignment(0, 17, 34);
/*  190 */       table_contents.setAlignment(0, 18, 34);
/*  191 */       table_contents.setAlignment(0, 19, 34);
/*  192 */       table_contents.setAlignment(0, 20, 34);
/*  193 */       table_contents.setAlignment(0, 21, 34);
/*      */       
/*  195 */       table_contents.setInsets(new Insets(-1, 0, 0, 0));
/*      */       
/*  197 */       table_contents.setObject(0, 0, "P&D(*)\nLocal Prod #");
/*  198 */       table_contents.setObject(0, 1, "Rls Family - Label");
/*  199 */       table_contents.setSpan(0, 1, new Dimension(2, 1));
/*  200 */       table_contents.setObject(0, 3, "Plant");
/*  201 */       table_contents.setObject(0, 4, "H\nD");
/*  202 */       table_contents.setObject(0, 5, "Artist");
/*  203 */       table_contents.setObject(0, 6, "Title  /");
/*  204 */       table_contents.setObject(0, 7, "Comments");
/*  205 */       table_contents.setObject(0, 8, "P.O.\nQty");
/*  206 */       table_contents.setObject(0, 9, "Unit");
/*  207 */       table_contents.setObject(0, 10, "Exploded\nTotal");
/*  208 */       table_contents.setObject(0, 11, "Qty\nDone");
/*  209 */       table_contents.setObject(0, 12, "F/M");
/*  210 */       table_contents.setObject(0, 13, "BOM");
/*  211 */       table_contents.setObject(0, 15, "PRQ");
/*  212 */       table_contents.setObject(0, 16, "TAPE");
/*  213 */       table_contents.setObject(0, 17, "FILM");
/*  214 */       table_contents.setObject(0, 20, "PAP");
/*  215 */       table_contents.setObject(0, 18, "STIC");
/*  216 */       table_contents.setObject(0, 19, "PPR");
/*  217 */       table_contents.setObject(0, 14, "FAP");
/*  218 */       table_contents.setObject(0, 21, "PSD\nComp\nDue");
/*      */       
/*  220 */       table_contents.setRowFont(0, new Font("Arial", 1, 7));
/*  221 */       table_contents.setFont(0, 6, new Font("Arial", 3, 7));
/*      */       
/*  223 */       table_contents.setColBorderColor(0, -1, new Color(208, 206, 206, 0));
/*  224 */       table_contents.setColBorderColor(0, 0, new Color(208, 206, 206, 0));
/*  225 */       table_contents.setColBorderColor(0, 1, Color.white);
/*  226 */       table_contents.setColBorderColor(0, 2, new Color(208, 206, 206, 0));
/*  227 */       table_contents.setColBorderColor(0, 3, new Color(208, 206, 206, 0));
/*  228 */       table_contents.setColBorderColor(0, 4, new Color(208, 206, 206, 0));
/*  229 */       table_contents.setColBorderColor(0, 5, new Color(208, 206, 206, 0));
/*  230 */       table_contents.setColBorderColor(0, 6, Color.white);
/*  231 */       table_contents.setColBorderColor(0, 7, new Color(208, 206, 206, 0));
/*  232 */       table_contents.setColBorderColor(0, 8, new Color(208, 206, 206, 0));
/*  233 */       table_contents.setColBorderColor(0, 9, new Color(208, 206, 206, 0));
/*  234 */       table_contents.setColBorderColor(0, 10, new Color(208, 206, 206, 0));
/*  235 */       table_contents.setColBorderColor(0, 11, new Color(208, 206, 206, 0));
/*  236 */       table_contents.setColBorderColor(0, 12, new Color(208, 206, 206, 0));
/*  237 */       table_contents.setColBorderColor(0, 13, new Color(208, 206, 206, 0));
/*  238 */       table_contents.setColBorderColor(0, 14, new Color(208, 206, 206, 0));
/*  239 */       table_contents.setColBorderColor(0, 15, new Color(208, 206, 206, 0));
/*  240 */       table_contents.setColBorderColor(0, 16, new Color(208, 206, 206, 0));
/*  241 */       table_contents.setColBorderColor(0, 17, new Color(208, 206, 206, 0));
/*  242 */       table_contents.setColBorderColor(0, 18, new Color(208, 206, 206, 0));
/*  243 */       table_contents.setColBorderColor(0, 19, new Color(208, 206, 206, 0));
/*  244 */       table_contents.setColBorderColor(0, 20, new Color(208, 206, 206, 0));
/*  245 */       table_contents.setColBorderColor(0, 21, new Color(208, 206, 206, 0));
/*      */       
/*  247 */       table_contents.setSpan(1, 0, new Dimension(22, 1));
/*  248 */       table_contents.setRowHeight(1, 1);
/*  249 */       table_contents.setRowBackground(1, Color.white);
/*  250 */       table_contents.setRowForeground(1, Color.black);
/*  251 */       table_contents.setRowBorderColor(1, SHADED_AREA_COLOR);
/*  252 */       setColBorderColors(1, -1, NUM_COLS - 1, table_contents, new Color(208, 206, 206, 0));
/*  253 */       setColBorders(1, -1, NUM_COLS - 1, table_contents, 0);
/*      */       
/*  255 */       int nextRow = 0;
/*      */       
/*  257 */       Vector sortedStreetDatesVector = MilestoneHelper.sortDates(streetDatesVector);
/*      */       
/*  259 */       hbandHeader.setHeight(1.0F);
/*  260 */       hbandHeader.setShrinkToFit(true);
/*  261 */       hbandHeader.setVisible(true);
/*  262 */       hbandHeader.setBottomBorder(0);
/*      */       
/*  264 */       hbandHeader.addTable(table_contents, new Rectangle(800, 800));
/*      */       
/*  266 */       int totalCount = 0;
/*  267 */       int tenth = 1;
/*  268 */       for (int a = 0; a < sortedStreetDatesVector.size(); a++) {
/*      */         
/*  270 */         totalCount++;
/*  271 */         String datesC = (String)sortedStreetDatesVector.get(a);
/*  272 */         Hashtable configTableC = (Hashtable)selTable.get(datesC);
/*  273 */         if (configTableC != null) {
/*      */           
/*  275 */           Enumeration monthsC = configTableC.keys();
/*  276 */           Vector monthVectorC = new Vector();
/*  277 */           while (monthsC.hasMoreElements()) {
/*  278 */             monthVectorC.add((String)monthsC.nextElement());
/*      */           }
/*  280 */           Object[] configsArrayC = null;
/*  281 */           configsArrayC = monthVectorC.toArray();
/*  282 */           for (int b = 0; b < configsArrayC.length; b++) {
/*      */             
/*  284 */             totalCount++;
/*  285 */             String monthNameC = (String)configsArrayC[b];
/*      */             
/*  287 */             Vector selectionsC = (Vector)configTableC.get(monthNameC);
/*  288 */             if (selectionsC == null) {
/*  289 */               selectionsC = new Vector();
/*      */             }
/*  291 */             totalCount += selectionsC.size();
/*      */           } 
/*      */         } 
/*      */       } 
/*  295 */       tenth = (totalCount > 10) ? (totalCount / 10) : 1;
/*      */       
/*  297 */       HttpServletResponse sresponse = context.getResponse();
/*  298 */       context.putDelivery("status", new String("start_report"));
/*  299 */       context.putDelivery("percent", new String("10"));
/*  300 */       context.includeJSP("status.jsp", "hiddenFrame");
/*  301 */       sresponse.setContentType("text/plain");
/*      */       
/*      */       try {
/*  304 */         sresponse.flushBuffer();
/*      */       }
/*  306 */       catch (IOException iOException) {}
/*  307 */       int recordCount = 0;
/*  308 */       int count = 0;
/*  309 */       for (int n = 0; n < sortedStreetDatesVector.size(); n++) {
/*      */         
/*  311 */         if (count < recordCount / tenth) {
/*      */           
/*  313 */           count = recordCount / tenth;
/*  314 */           sresponse = context.getResponse();
/*  315 */           context.putDelivery("status", new String("start_report"));
/*  316 */           context.putDelivery("percent", new String(String.valueOf(count * 10)));
/*  317 */           context.includeJSP("status.jsp", "hiddenFrame");
/*  318 */           sresponse.setContentType("text/plain");
/*      */           
/*      */           try {
/*  321 */             sresponse.flushBuffer();
/*      */           }
/*  323 */           catch (IOException iOException) {}
/*      */         } 
/*  325 */         recordCount++;
/*      */         
/*  327 */         int piTotal = 0;
/*  328 */         int kmTotal = 0;
/*  329 */         int haTotal = 0;
/*  330 */         int glTotal = 0;
/*  331 */         int ciTotal = 0;
/*  332 */         int paTotal = 0;
/*  333 */         int civTotal = 0;
/*      */         
/*  335 */         Hashtable plantTotals = buildTotals(plantIdandName);
/*      */         
/*  337 */         String theStreetDate = (String)sortedStreetDatesVector.elementAt(n);
/*  338 */         String theStreetDateText = !theStreetDate.trim().equals("") ? theStreetDate : "Other";
/*      */         
/*  340 */         footer.setVisible(true);
/*  341 */         footer.setHeight(0.05F);
/*  342 */         footer.setShrinkToFit(true);
/*  343 */         footer.setBottomBorder(0);
/*  344 */         nextRow = 0;
/*      */         
/*  346 */         Hashtable configTable = (Hashtable)selTable.get(theStreetDate);
/*  347 */         if (configTable != null) {
/*      */           
/*  349 */           Enumeration configsEnum = configTable.keys();
/*      */           
/*  351 */           Vector configsVector = new Vector();
/*  352 */           while (configsEnum.hasMoreElements()) {
/*  353 */             configsVector.add((String)configsEnum.nextElement());
/*      */           }
/*  355 */           Object[] configsList = configsVector.toArray();
/*      */           
/*  357 */           Arrays.sort(configsList, new StringComparator());
/*  358 */           for (int x = 0; x < configsList.length; x++) {
/*      */             
/*  360 */             nextRow = 0;
/*      */             
/*  362 */             theConfig = (String)configsList[x];
/*  363 */             if (count < recordCount / tenth) {
/*      */               
/*  365 */               count = recordCount / tenth;
/*  366 */               sresponse = context.getResponse();
/*  367 */               context.putDelivery("status", new String("start_report"));
/*  368 */               context.putDelivery("percent", new String(String.valueOf(count * 10)));
/*  369 */               context.includeJSP("status.jsp", "hiddenFrame");
/*  370 */               sresponse.setContentType("text/plain");
/*      */               
/*      */               try {
/*  373 */                 sresponse.flushBuffer();
/*      */               }
/*  375 */               catch (IOException iOException) {}
/*      */             } 
/*  377 */             recordCount++;
/*      */             
/*  379 */             footer.setVisible(true);
/*  380 */             footer.setHeight(0.05F);
/*  381 */             footer.setShrinkToFit(true);
/*  382 */             footer.setBottomBorder(0);
/*      */             
/*  384 */             selections = (Vector)configTable.get(theConfig);
/*  385 */             if (selections == null) {
/*  386 */               selections = new Vector();
/*      */             }
/*  388 */             MilestoneHelper.setSelectionSorting(selections, 15);
/*  389 */             Collections.sort(selections);
/*      */             
/*  391 */             MilestoneHelper.setSelectionSorting(selections, 14);
/*  392 */             Collections.sort(selections);
/*      */             
/*  394 */             MilestoneHelper.setSelectionSorting(selections, 24);
/*  395 */             Collections.sort(selections);
/*      */             
/*  397 */             MilestoneHelper.applyManufacturingToSelections(selections);
/*  398 */             for (int i = 0; i < selections.size(); i++) {
/*      */               
/*  400 */               Selection sel = (Selection)selections.elementAt(i);
/*      */               
/*  402 */               Vector plants = sel.getManufacturingPlants();
/*      */               
/*  404 */               int plantSize = 1;
/*  405 */               if (plants != null && plants.size() > 0) {
/*  406 */                 plantSize = plants.size();
/*      */               }
/*  408 */               for (int plantCount = 0; plantCount < plantSize; plantCount++) {
/*      */                 
/*  410 */                 if (i == 0 && plantCount == 0) {
/*  411 */                   subTable = new DefaultTableLens(8, NUM_COLS);
/*      */                 } else {
/*  413 */                   subTable = new DefaultTableLens(2, NUM_COLS);
/*      */                 } 
/*  415 */                 Plant p = null;
/*  416 */                 if (plants != null && plants.size() > 0) {
/*  417 */                   p = (Plant)plants.get(plantCount);
/*      */                 }
/*  419 */                 nextRow = 0;
/*      */                 
/*  421 */                 subTable.setColWidth(0, 140);
/*  422 */                 subTable.setColWidth(1, 150);
/*  423 */                 subTable.setColWidth(2, 10);
/*  424 */                 subTable.setColWidth(3, 110);
/*  425 */                 subTable.setColWidth(4, 25);
/*  426 */                 subTable.setColWidth(5, 247);
/*  427 */                 subTable.setColWidth(6, 140);
/*  428 */                 subTable.setColWidth(7, 140);
/*  429 */                 subTable.setColWidth(8, 100);
/*  430 */                 subTable.setColWidth(9, 45);
/*  431 */                 subTable.setColWidth(10, 110);
/*  432 */                 subTable.setColWidth(11, 80);
/*  433 */                 subTable.setColWidth(12, 85);
/*  434 */                 subTable.setColWidth(13, 85);
/*  435 */                 subTable.setColWidth(14, 85);
/*  436 */                 subTable.setColWidth(15, 85);
/*  437 */                 subTable.setColWidth(16, 85);
/*  438 */                 subTable.setColWidth(17, 85);
/*  439 */                 subTable.setColWidth(18, 85);
/*  440 */                 subTable.setColWidth(19, 85);
/*  441 */                 subTable.setColWidth(20, 85);
/*  442 */                 subTable.setColWidth(21, 88);
/*      */                 
/*  444 */                 subTable.setRowBorder(0, 4097);
/*      */                 
/*  446 */                 subTable.setLineWrap(0, 0, false);
/*  447 */                 subTable.setLineWrap(0, 1, false);
/*  448 */                 subTable.setLineWrap(0, 2, false);
/*  449 */                 subTable.setLineWrap(0, 3, false);
/*  450 */                 subTable.setLineWrap(0, 4, false);
/*  451 */                 subTable.setLineWrap(0, 5, false);
/*  452 */                 subTable.setLineWrap(0, 6, false);
/*  453 */                 subTable.setLineWrap(0, 7, false);
/*  454 */                 subTable.setLineWrap(0, 8, false);
/*  455 */                 subTable.setLineWrap(0, 9, false);
/*  456 */                 subTable.setLineWrap(0, 10, false);
/*  457 */                 subTable.setLineWrap(0, 11, false);
/*  458 */                 subTable.setLineWrap(0, 12, false);
/*  459 */                 subTable.setLineWrap(0, 13, false);
/*  460 */                 subTable.setLineWrap(0, 14, false);
/*  461 */                 subTable.setLineWrap(0, 15, false);
/*  462 */                 subTable.setLineWrap(0, 16, false);
/*  463 */                 subTable.setLineWrap(0, 17, false);
/*  464 */                 subTable.setLineWrap(0, 18, false);
/*  465 */                 subTable.setLineWrap(0, 19, false);
/*  466 */                 subTable.setLineWrap(0, 20, false);
/*  467 */                 subTable.setLineWrap(0, 21, false);
/*      */                 
/*  469 */                 subTable.setAlignment(0, 0, 34);
/*  470 */                 subTable.setAlignment(0, 1, 34);
/*  471 */                 subTable.setAlignment(0, 2, 34);
/*  472 */                 subTable.setAlignment(0, 3, 34);
/*  473 */                 subTable.setAlignment(0, 4, 34);
/*  474 */                 subTable.setAlignment(0, 5, 36);
/*  475 */                 subTable.setAlignment(0, 6, 33);
/*  476 */                 subTable.setAlignment(0, 7, 34);
/*  477 */                 subTable.setAlignment(0, 8, 34);
/*  478 */                 subTable.setAlignment(0, 9, 34);
/*  479 */                 subTable.setAlignment(0, 10, 34);
/*  480 */                 subTable.setAlignment(0, 11, 34);
/*  481 */                 subTable.setAlignment(0, 12, 34);
/*  482 */                 subTable.setAlignment(0, 13, 34);
/*  483 */                 subTable.setAlignment(0, 14, 34);
/*  484 */                 subTable.setAlignment(0, 15, 34);
/*  485 */                 subTable.setAlignment(0, 16, 34);
/*  486 */                 subTable.setAlignment(0, 17, 34);
/*  487 */                 subTable.setAlignment(0, 18, 34);
/*  488 */                 subTable.setAlignment(0, 19, 34);
/*  489 */                 subTable.setAlignment(0, 20, 34);
/*  490 */                 subTable.setAlignment(0, 21, 34);
/*      */                 
/*  492 */                 int artistLength = 0, labelLength = 0, titleLength = 0, idLength = 0;
/*  493 */                 if (count < recordCount / tenth) {
/*      */                   
/*  495 */                   count = recordCount / tenth;
/*  496 */                   sresponse = context.getResponse();
/*  497 */                   context.putDelivery("status", new String("start_report"));
/*  498 */                   context.putDelivery("percent", new String(String.valueOf(count * 10)));
/*  499 */                   context.includeJSP("status.jsp", "hiddenFrame");
/*  500 */                   sresponse.setContentType("text/plain");
/*      */                   
/*      */                   try {
/*  503 */                     sresponse.flushBuffer();
/*      */                   }
/*  505 */                   catch (IOException iOException) {}
/*      */                 } 
/*  507 */                 recordCount++;
/*      */                 
/*  509 */                 String selectionNo = SelectionManager.getLookupObjectValue(sel.getPrefixID());
/*  510 */                 if (selectionNo == null) {
/*  511 */                   selectionNo = "";
/*      */                 }
/*  513 */                 if (sel != null) {
/*      */                   
/*  515 */                   selectionNo = String.valueOf(selectionNo) + sel.getSelectionNo().trim();
/*      */                   
/*  517 */                   idLength = selectionNo.length();
/*      */                 } 
/*  519 */                 String pd = "";
/*  520 */                 if (sel != null && sel.getPressAndDistribution()) {
/*  521 */                   pd = "* ";
/*      */                 }
/*  523 */                 String selDistribution = SelectionManager.getLookupObjectValue(sel.getDistribution());
/*      */                 
/*  525 */                 String artist = "";
/*  526 */                 if (sel != null) {
/*  527 */                   artist = sel.getArtist();
/*      */                 } else {
/*  529 */                   artist = null;
/*      */                 } 
/*  531 */                 if (artist != null) {
/*  532 */                   artistLength = artist.length();
/*      */                 }
/*  534 */                 String comment = "";
/*      */                 
/*      */                 try {
/*  537 */                   comment = (sel.getSelectionComments() != null) ? sel.getSelectionComments().trim() : "";
/*      */                 }
/*  539 */                 catch (Exception ex) {
/*      */                   
/*  541 */                   System.out.print("comment:" + ex);
/*      */                 } 
/*  543 */                 String mfgComment = "";
/*      */                 
/*      */                 try {
/*  546 */                   mfgComment = (sel.getManufacturingComments() != null) ? sel.getManufacturingComments().trim() : "";
/*      */                 }
/*  548 */                 catch (Exception ed) {
/*      */                   
/*  550 */                   System.out.print("mfgcoment:" + ed);
/*      */                 } 
/*  552 */                 String releasingFamily = "";
/*  553 */                 if (sel != null && sel.getReleaseFamilyId() > 0) {
/*  554 */                   releasingFamily = ReleasingFamily.getName(sel.getReleaseFamilyId());
/*      */                 }
/*  556 */                 String label = "";
/*  557 */                 if (sel != null && sel.getImprint() != null) {
/*  558 */                   label = sel.getImprint();
/*      */                 }
/*      */                 
/*      */                 try {
/*  562 */                   labelLength = label.length() + releasingFamily.length();
/*      */                 }
/*  564 */                 catch (Exception ef) {
/*      */                   
/*  566 */                   System.out.print("labellengh:" + ef);
/*      */                 } 
/*  568 */                 String titleComments = "";
/*      */                 
/*      */                 try {
/*  571 */                   titleComments = sel.getTitle();
/*      */                 }
/*  573 */                 catch (Exception rg) {
/*      */                   
/*  575 */                   System.out.print("title coments:" + rg);
/*      */                 } 
/*  577 */                 if (titleComments != null) {
/*  578 */                   titleLength = titleComments.length();
/*      */                 }
/*  580 */                 String poQty = "0";
/*      */                 
/*      */                 try {
/*  583 */                   poQty = (p != null && p.getOrderQty() > 0) ? String.valueOf(p.getOrderQty()) : "0";
/*      */                 }
/*  585 */                 catch (Exception ex) {
/*      */                   
/*  587 */                   System.out.print("poQty:" + ex);
/*      */                 } 
/*  589 */                 String units = "";
/*      */                 
/*      */                 try {
/*  592 */                   units = (sel.getNumberOfUnits() > 0) ? String.valueOf(sel.getNumberOfUnits()) : "";
/*      */                 }
/*  594 */                 catch (Exception ex) {
/*      */                   
/*  596 */                   System.out.print("Units:" + ex);
/*      */                 } 
/*  598 */                 int poQtyNum = 0;
/*      */                 
/*      */                 try {
/*  601 */                   poQtyNum = Integer.parseInt(poQty);
/*      */                 }
/*  603 */                 catch (Exception e) {
/*      */                   
/*  605 */                   System.out.print("poQtyNum:" + e);
/*      */                 } 
/*  607 */                 int explodedTotal = 0;
/*      */                 
/*      */                 try {
/*  610 */                   if (poQtyNum > 0 && sel != null && sel.getNumberOfUnits() > 0) {
/*  611 */                     explodedTotal = poQtyNum * sel.getNumberOfUnits();
/*      */                   }
/*      */                 }
/*  614 */                 catch (Exception g) {
/*      */                   
/*  616 */                   System.out.print("poQtyNum:" + g);
/*      */                 } 
/*  618 */                 String plant = "";
/*  619 */                 String plantText = "";
/*  620 */                 if (p != null && p.getPlant() != null) {
/*      */                   
/*  622 */                   String plantNo = (p.getPlant().getName() != null) ? p.getPlant().getName() : "";
/*  623 */                   plant = (p.getPlant().getAbbreviation() != null) ? p.getPlant().getAbbreviation() : "";
/*      */                   
/*  625 */                   plantText = (p.getPlant().getName() != null) ? p.getPlant().getName() : "";
/*      */                   
/*  627 */                   String plantId = p.getPlant().getAbbreviation();
/*  628 */                   if (plantIdandName != null && plantIdandName.get(plantId) != null) {
/*  629 */                     plant = (String)plantIdandName.get(plantId);
/*      */                   } else {
/*  631 */                     plant = "";
/*      */                   } 
/*  633 */                   int currentTotal = 0;
/*  634 */                   if (tryParseInt((String)plantTotals.get(plantId))) {
/*  635 */                     currentTotal = Integer.parseInt((String)plantTotals.get(plantId));
/*      */                   }
/*  637 */                   int newTotal = currentTotal + explodedTotal;
/*  638 */                   plantTotals.put(plantId, String.valueOf(newTotal));
/*      */                 } 
/*  640 */                 String compQty = "0";
/*  641 */                 if (p != null && p.getCompletedQty() > 0) {
/*      */                   
/*      */                   try {
/*  644 */                     compQty = MilestoneHelper.formatQuantityWithCommas(String.valueOf(p.getCompletedQty()));
/*      */                   }
/*  646 */                   catch (Exception f) {
/*      */                     
/*  648 */                     System.out.print("compQty:" + f);
/*      */                   } 
/*      */                 }
/*  651 */                 Schedule schedule = (sel.getSchedule() != null) ? sel.getSchedule() : null;
/*      */                 
/*  653 */                 Vector tasks = (schedule != null) ? schedule.getTasks() : null;
/*  654 */                 ScheduledTask task = null;
/*      */                 
/*  656 */                 String FM = "";
/*  657 */                 String BOM = "";
/*  658 */                 String PRQ = "";
/*  659 */                 String TAPE = "";
/*  660 */                 String FILM = "";
/*  661 */                 String PAP = "";
/*  662 */                 String STIC = "";
/*  663 */                 String MC = "";
/*  664 */                 String FAP = "";
/*  665 */                 String PSD = "";
/*  666 */                 String dueDateHolidayFlg = "";
/*      */                 
/*  668 */                 String MCvend = "";
/*      */                 
/*  670 */                 boolean hasPPRtask = false;
/*  671 */                 if (tasks != null) {
/*      */                   
/*  673 */                   PSD = "N/A";
/*  674 */                   for (int j = 0; j < tasks.size(); j++) {
/*      */                     
/*  676 */                     task = (ScheduledTask)tasks.get(j);
/*      */                     
/*  678 */                     String taskAbbrev = MilestoneHelper.getTaskAbbreivationNameById(task.getTaskAbbreviationID());
/*      */                     
/*  680 */                     String taskVendor = (task.getVendor() != null) ? task.getVendor() : "";
/*  681 */                     taskVendor = taskVendor.equals("\n") ? "" : taskVendor;
/*  682 */                     if (taskAbbrev.equalsIgnoreCase("F/M")) {
/*      */                       
/*  684 */                       FM = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*  685 */                       if (task.getScheduledTaskStatus() != null && 
/*  686 */                         task.getScheduledTaskStatus().equals("N/A")) {
/*  687 */                         FM = "N/A";
/*      */                       }
/*      */                     }
/*  690 */                     else if (taskAbbrev.equalsIgnoreCase("BOM")) {
/*      */                       
/*  692 */                       BOM = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*  693 */                       if (task.getScheduledTaskStatus() != null && 
/*  694 */                         task.getScheduledTaskStatus().equals("N/A")) {
/*  695 */                         BOM = "N/A";
/*      */                       }
/*      */                     }
/*  698 */                     else if (taskAbbrev.equalsIgnoreCase("PRQ")) {
/*      */                       
/*  700 */                       PRQ = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*  701 */                       if (task.getScheduledTaskStatus() != null && 
/*  702 */                         task.getScheduledTaskStatus().equals("N/A")) {
/*  703 */                         PRQ = "N/A";
/*      */                       }
/*      */                     }
/*  706 */                     else if (taskAbbrev.equalsIgnoreCase("TAPE")) {
/*      */                       
/*  708 */                       TAPE = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*  709 */                       if (task.getScheduledTaskStatus() != null && 
/*  710 */                         task.getScheduledTaskStatus().equals("N/A")) {
/*  711 */                         TAPE = "N/A";
/*      */                       }
/*      */                     }
/*  714 */                     else if (taskAbbrev.equalsIgnoreCase("FILM")) {
/*      */                       
/*  716 */                       FILM = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*  717 */                       if (task.getScheduledTaskStatus() != null && 
/*  718 */                         task.getScheduledTaskStatus().equals("N/A")) {
/*  719 */                         FILM = "N/A";
/*      */                       }
/*      */                     }
/*  722 */                     else if (taskAbbrev.equalsIgnoreCase("PAP")) {
/*      */                       
/*  724 */                       PAP = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*  725 */                       if (task.getScheduledTaskStatus() != null && 
/*  726 */                         task.getScheduledTaskStatus().equals("N/A")) {
/*  727 */                         PAP = "N/A";
/*      */                       }
/*      */                     }
/*  730 */                     else if (taskAbbrev.equalsIgnoreCase("STIC")) {
/*      */                       
/*  732 */                       STIC = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*  733 */                       if (task.getScheduledTaskStatus() != null && 
/*  734 */                         task.getScheduledTaskStatus().equals("N/A")) {
/*  735 */                         STIC = "N/A";
/*      */                       }
/*      */                     }
/*  738 */                     else if (taskAbbrev.equalsIgnoreCase("M/C")) {
/*      */                       
/*  740 */                       MC = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*  741 */                       if (task.getScheduledTaskStatus() != null && 
/*  742 */                         task.getScheduledTaskStatus().equals("N/A")) {
/*  743 */                         MC = "N/A";
/*      */                       }
/*  745 */                       MCvend = taskVendor;
/*  746 */                       hasPPRtask = true;
/*      */                     }
/*  748 */                     else if (taskAbbrev.equalsIgnoreCase("FAP")) {
/*      */                       
/*  750 */                       FAP = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*  751 */                       if (task.getScheduledTaskStatus() != null && 
/*  752 */                         task.getScheduledTaskStatus().equals("N/A")) {
/*  753 */                         FAP = "N/A";
/*      */                       }
/*      */                     }
/*  756 */                     else if (taskAbbrev.equalsIgnoreCase("PSD")) {
/*      */                       
/*  758 */                       if (task.getScheduledTaskStatus() != null && 
/*  759 */                         task.getScheduledTaskStatus().equals("N/A")) {
/*      */                         
/*  761 */                         PSD = "N/A";
/*      */                       }
/*      */                       else {
/*      */                         
/*  765 */                         dueDateHolidayFlg = MilestoneHelper.getDayType(sel.getCalendarGroup(), task.getDueDate());
/*  766 */                         String PsdCompDt = (task.getCompletionDate() == null) ? "" : MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*  767 */                         String PsdDueDt = (task.getDueDate() == null) ? "" : MilestoneHelper.getFormatedDate(task.getDueDate());
/*  768 */                         PSD = String.valueOf(PsdCompDt) + "\n" + PsdDueDt + " " + dueDateHolidayFlg;
/*      */                       } 
/*      */                     } 
/*  771 */                     task = null;
/*      */                   } 
/*      */                 } 
/*  774 */                 nextRow = 0;
/*  775 */                 if (i == 0 && plantCount == 0) {
/*      */                   
/*  777 */                   nextRow = ReportHandler.insertLightGrayHeader(subTable, theStreetDateText, nextRow, 22);
/*      */                   
/*  779 */                   subTable.setRowBorder(nextRow, 4097);
/*  780 */                   subTable.setRowBorder(nextRow + 1, 4097);
/*  781 */                   setColBorders(nextRow, -1, NUM_COLS - 1, subTable, 0);
/*  782 */                   subTable.setColBorderColor(nextRow, Color.white);
/*  783 */                   setColBorderColors(nextRow, -1, NUM_COLS - 1, subTable, Color.white);
/*  784 */                   subTable.setSpan(nextRow, 0, new Dimension(22, 1));
/*  785 */                   subTable.setObject(nextRow, 0, theConfig);
/*  786 */                   subTable.setAlignment(0, 0, 33);
/*  787 */                   subTable.setRowFont(nextRow, new Font("Arial", 1, 9));
/*      */                   
/*  789 */                   subTable.setRowBorderColor(-1, Color.lightGray);
/*  790 */                   subTable.setRowBorderColor(Color.lightGray);
/*      */                   
/*  792 */                   nextRow++;
/*      */                   
/*  794 */                   subTable.setHeaderRowCount(0);
/*  795 */                   subTable.setSpan(nextRow, 0, new Dimension(22, 1));
/*  796 */                   subTable.setRowHeight(nextRow, 2);
/*  797 */                   subTable.setRowBackground(nextRow, Color.white);
/*  798 */                   subTable.setRowForeground(nextRow, Color.black);
/*  799 */                   setColBorderColors(nextRow, -1, NUM_COLS - 1, subTable, new Color(208, 206, 206, 0));
/*  800 */                   setColBorders(nextRow, -1, NUM_COLS - 1, subTable, 0);
/*      */                   
/*  802 */                   nextRow++;
/*      */                 } 
/*  804 */                 subTable.setRowInsets(nextRow, new Insets(0, 0, 5, 0));
/*  805 */                 if (sel.getSpecialPackaging()) {
/*      */                   
/*  807 */                   if (BOM.length() > 0) {
/*      */                     
/*  809 */                     BOM = String.valueOf(BOM) + "\n";
/*  810 */                     subTable.setRowHeight(nextRow, 15);
/*      */                   } 
/*  812 */                   BOM = String.valueOf(BOM) + "sp. pkg";
/*      */                 } 
/*  814 */                 if (plantText.length() > 13 || labelLength > 17 || artistLength > 27) {
/*      */                   
/*  816 */                   subTable.setRowHeight(nextRow, 15);
/*  817 */                   subTable.setRowHeight(nextRow + 1, 1);
/*      */                 } 
/*  819 */                 subTable.setRowInsets(nextRow, new Insets(0, 0, 5, 0));
/*      */                 
/*  821 */                 subTable.setSpan(nextRow, 0, new Dimension(1, 2));
/*  822 */                 subTable.setSpan(nextRow, 1, new Dimension(2, 2));
/*  823 */                 subTable.setSpan(nextRow, 2, new Dimension(1, 2));
/*  824 */                 subTable.setSpan(nextRow, 3, new Dimension(1, 2));
/*  825 */                 subTable.setSpan(nextRow, 4, new Dimension(1, 2));
/*  826 */                 subTable.setSpan(nextRow, 5, new Dimension(1, 2));
/*  827 */                 subTable.setSpan(nextRow, 6, new Dimension(2, 2));
/*  828 */                 subTable.setSpan(nextRow, 7, new Dimension(1, 2));
/*  829 */                 subTable.setSpan(nextRow, 8, new Dimension(1, 2));
/*  830 */                 subTable.setSpan(nextRow, 9, new Dimension(1, 2));
/*  831 */                 subTable.setSpan(nextRow, 10, new Dimension(1, 2));
/*  832 */                 subTable.setSpan(nextRow, 11, new Dimension(1, 2));
/*  833 */                 subTable.setSpan(nextRow, 12, new Dimension(1, 2));
/*  834 */                 subTable.setSpan(nextRow, 13, new Dimension(1, 2));
/*  835 */                 subTable.setSpan(nextRow, 14, new Dimension(1, 2));
/*  836 */                 subTable.setSpan(nextRow, 15, new Dimension(1, 2));
/*  837 */                 subTable.setSpan(nextRow, 16, new Dimension(1, 2));
/*  838 */                 subTable.setSpan(nextRow, 17, new Dimension(1, 2));
/*  839 */                 subTable.setSpan(nextRow, 18, new Dimension(1, 2));
/*  840 */                 subTable.setSpan(nextRow, 19, new Dimension(1, 2));
/*  841 */                 subTable.setSpan(nextRow, 20, new Dimension(1, 2));
/*  842 */                 subTable.setSpan(nextRow, 21, new Dimension(1, 2));
/*  843 */                 if (idLength == 13 && pd.trim().equals("*")) {
/*  844 */                   pd = "*";
/*      */                 }
/*  846 */                 subTable.setObject(nextRow, 0, String.valueOf(pd) + selectionNo);
/*  847 */                 subTable.setObject(nextRow, 1, String.valueOf(releasingFamily) + " - " + label);
/*  848 */                 subTable.setObject(nextRow, 3, plantText);
/*      */                 
/*  850 */                 subTable.setObject(nextRow, 4, selDistribution);
/*  851 */                 subTable.setObject(nextRow, 5, artist);
/*      */                 
/*  853 */                 subTable.setSpan(nextRow, 6, new Dimension(2, 1));
/*  854 */                 subTable.setSpan(nextRow + 1, 6, new Dimension(2, 1));
/*      */                 
/*  856 */                 subTable.setObject(nextRow, 6, titleComments);
/*  857 */                 subTable.setObject(nextRow, 8, MilestoneHelper.formatQuantityWithCommas(poQty));
/*  858 */                 subTable.setObject(nextRow, 9, units);
/*  859 */                 subTable.setObject(nextRow, 10, MilestoneHelper.formatQuantityWithCommas(String.valueOf(explodedTotal)));
/*  860 */                 subTable.setObject(nextRow, 11, MilestoneHelper.formatQuantityWithCommas(compQty));
/*      */                 
/*  862 */                 subTable.setObject(nextRow, 12, FM);
/*  863 */                 subTable.setObject(nextRow, 13, BOM);
/*  864 */                 subTable.setObject(nextRow, 15, PRQ);
/*  865 */                 subTable.setObject(nextRow, 16, TAPE);
/*  866 */                 subTable.setObject(nextRow, 17, FILM);
/*  867 */                 subTable.setObject(nextRow, 20, PAP);
/*  868 */                 subTable.setObject(nextRow, 18, STIC);
/*  869 */                 if (tasks == null) {
/*  870 */                   subTable.setObject(nextRow, 19, "");
/*  871 */                 } else if (!hasPPRtask) {
/*  872 */                   subTable.setObject(nextRow, 19, "N/A");
/*  873 */                 } else if (MCvend != null && !MCvend.equals("")) {
/*  874 */                   subTable.setObject(nextRow, 19, String.valueOf(MC) + "\n" + MCvend);
/*      */                 } else {
/*  876 */                   subTable.setObject(nextRow, 19, MC);
/*      */                 } 
/*  878 */                 subTable.setObject(nextRow, 14, FAP);
/*  879 */                 subTable.setObject(nextRow, 21, PSD);
/*      */                 
/*  881 */                 subTable.setAlignment(nextRow, 0, 4);
/*      */                 
/*  883 */                 subTable.setLineWrap(nextRow, 1, true);
/*  884 */                 subTable.setLineWrap(nextRow, 2, true);
/*  885 */                 subTable.setLineWrap(nextRow, 3, true);
/*  886 */                 subTable.setLineWrap(nextRow, 4, true);
/*  887 */                 subTable.setLineWrap(nextRow, 5, true);
/*  888 */                 subTable.setLineWrap(nextRow, 6, true);
/*  889 */                 subTable.setLineWrap(nextRow, 7, true);
/*  890 */                 subTable.setLineWrap(nextRow, 8, true);
/*  891 */                 subTable.setLineWrap(nextRow, 9, true);
/*  892 */                 subTable.setLineWrap(nextRow, 10, true);
/*  893 */                 subTable.setLineWrap(nextRow, 11, true);
/*  894 */                 subTable.setLineWrap(nextRow, 12, true);
/*  895 */                 subTable.setLineWrap(nextRow, 13, true);
/*  896 */                 subTable.setLineWrap(nextRow, 14, true);
/*  897 */                 subTable.setLineWrap(nextRow, 15, true);
/*  898 */                 subTable.setLineWrap(nextRow, 16, true);
/*  899 */                 subTable.setLineWrap(nextRow, 17, true);
/*  900 */                 subTable.setLineWrap(nextRow, 18, true);
/*  901 */                 subTable.setLineWrap(nextRow, 19, true);
/*  902 */                 subTable.setLineWrap(nextRow, 20, true);
/*  903 */                 subTable.setLineWrap(nextRow, 21, true);
/*      */                 
/*  905 */                 subTable.setAlignment(nextRow, 0, 8);
/*  906 */                 subTable.setAlignment(nextRow, 1, 8);
/*  907 */                 subTable.setAlignment(nextRow, 2, 8);
/*  908 */                 subTable.setAlignment(nextRow, 3, 8);
/*  909 */                 subTable.setAlignment(nextRow, 4, 8);
/*  910 */                 subTable.setAlignment(nextRow, 5, 8);
/*  911 */                 subTable.setAlignment(nextRow, 6, 8);
/*  912 */                 subTable.setAlignment(nextRow, 7, 12);
/*  913 */                 subTable.setAlignment(nextRow, 8, 12);
/*  914 */                 subTable.setAlignment(nextRow, 9, 12);
/*  915 */                 subTable.setAlignment(nextRow, 10, 12);
/*  916 */                 subTable.setAlignment(nextRow, 11, 12);
/*  917 */                 subTable.setAlignment(nextRow, 12, 12);
/*  918 */                 subTable.setAlignment(nextRow, 13, 12);
/*  919 */                 subTable.setAlignment(nextRow, 14, 12);
/*  920 */                 subTable.setAlignment(nextRow, 15, 12);
/*  921 */                 subTable.setAlignment(nextRow, 16, 12);
/*  922 */                 subTable.setAlignment(nextRow, 17, 12);
/*  923 */                 subTable.setAlignment(nextRow, 18, 12);
/*  924 */                 subTable.setAlignment(nextRow, 19, 12);
/*  925 */                 subTable.setAlignment(nextRow, 20, 12);
/*  926 */                 subTable.setAlignment(nextRow, 21, 12);
/*      */                 
/*  928 */                 subTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
/*  929 */                 subTable.setRowFont(nextRow, new Font("Arial", 0, 6));
/*  930 */                 if (dueDateHolidayFlg != "") {
/*  931 */                   subTable.setFont(nextRow, 21, new Font("Arial", 3, 6));
/*      */                 }
/*  933 */                 subTable.setColBorderColor(nextRow, -1, new Color(208, 206, 206, 0));
/*  934 */                 subTable.setColBorderColor(nextRow, 0, new Color(208, 206, 206, 0));
/*  935 */                 subTable.setColBorderColor(nextRow, 1, new Color(208, 206, 206, 0));
/*  936 */                 subTable.setColBorderColor(nextRow, 2, new Color(208, 206, 206, 0));
/*  937 */                 subTable.setColBorderColor(nextRow, 3, new Color(208, 206, 206, 0));
/*  938 */                 subTable.setColBorderColor(nextRow, 4, new Color(208, 206, 206, 0));
/*  939 */                 subTable.setColBorderColor(nextRow, 5, new Color(208, 206, 206, 0));
/*  940 */                 subTable.setColBorderColor(nextRow, 6, new Color(208, 206, 206, 0));
/*  941 */                 subTable.setColBorderColor(nextRow, 7, new Color(208, 206, 206, 0));
/*  942 */                 subTable.setColBorderColor(nextRow, 8, new Color(208, 206, 206, 0));
/*  943 */                 subTable.setColBorderColor(nextRow, 9, new Color(208, 206, 206, 0));
/*  944 */                 subTable.setColBorderColor(nextRow, 10, new Color(208, 206, 206, 0));
/*  945 */                 subTable.setColBorderColor(nextRow, 11, new Color(208, 206, 206, 0));
/*  946 */                 subTable.setColBorderColor(nextRow, 12, new Color(208, 206, 206, 0));
/*  947 */                 subTable.setColBorderColor(nextRow, 13, new Color(208, 206, 206, 0));
/*  948 */                 subTable.setColBorderColor(nextRow, 14, new Color(208, 206, 206, 0));
/*  949 */                 subTable.setColBorderColor(nextRow, 15, new Color(208, 206, 206, 0));
/*  950 */                 subTable.setColBorderColor(nextRow, 16, new Color(208, 206, 206, 0));
/*  951 */                 subTable.setColBorderColor(nextRow, 17, new Color(208, 206, 206, 0));
/*  952 */                 subTable.setColBorderColor(nextRow, 18, new Color(208, 206, 206, 0));
/*  953 */                 subTable.setColBorderColor(nextRow, 19, new Color(208, 206, 206, 0));
/*  954 */                 subTable.setColBorderColor(nextRow, 20, new Color(208, 206, 206, 0));
/*  955 */                 subTable.setColBorderColor(nextRow, 21, new Color(208, 206, 206, 0));
/*      */                 
/*  957 */                 subTable.setRowBorder(nextRow, 4097);
/*      */                 
/*  959 */                 nextRow++;
/*      */                 
/*  961 */                 subTable.setRowBorderColor(nextRow - 2, Color.lightGray);
/*  962 */                 subTable.setRowBorderColor(nextRow - 1, Color.white);
/*  963 */                 subTable.setRowBorderColor(nextRow, Color.lightGray);
/*      */                 
/*  965 */                 subTable.setRowBorderColor(nextRow, 0, Color.lightGray);
/*  966 */                 subTable.setRowBorderColor(nextRow, 6, Color.lightGray);
/*      */                 
/*  968 */                 subTable.setColBorderColor(nextRow, -1, new Color(208, 206, 206, 0));
/*  969 */                 subTable.setColBorderColor(nextRow, 0, new Color(208, 206, 206, 0));
/*  970 */                 subTable.setColBorderColor(nextRow, 1, new Color(208, 206, 206, 0));
/*  971 */                 subTable.setColBorderColor(nextRow, 2, new Color(208, 206, 206, 0));
/*  972 */                 subTable.setColBorderColor(nextRow, 3, new Color(208, 206, 206, 0));
/*  973 */                 subTable.setColBorderColor(nextRow, 4, new Color(208, 206, 206, 0));
/*  974 */                 subTable.setColBorderColor(nextRow, 5, new Color(208, 206, 206, 0));
/*  975 */                 subTable.setColBorderColor(nextRow, 6, new Color(208, 206, 206, 0));
/*  976 */                 subTable.setColBorderColor(nextRow, 7, new Color(208, 206, 206, 0));
/*  977 */                 subTable.setColBorderColor(nextRow, 8, new Color(208, 206, 206, 0));
/*  978 */                 subTable.setColBorderColor(nextRow, 9, new Color(208, 206, 206, 0));
/*  979 */                 subTable.setColBorderColor(nextRow, 10, new Color(208, 206, 206, 0));
/*  980 */                 subTable.setColBorderColor(nextRow, 11, new Color(208, 206, 206, 0));
/*  981 */                 subTable.setColBorderColor(nextRow, 12, new Color(208, 206, 206, 0));
/*  982 */                 subTable.setColBorderColor(nextRow, 13, new Color(208, 206, 206, 0));
/*  983 */                 subTable.setColBorderColor(nextRow, 14, new Color(208, 206, 206, 0));
/*  984 */                 subTable.setColBorderColor(nextRow, 15, new Color(208, 206, 206, 0));
/*  985 */                 subTable.setColBorderColor(nextRow, 16, new Color(208, 206, 206, 0));
/*  986 */                 subTable.setColBorderColor(nextRow, 17, new Color(208, 206, 206, 0));
/*  987 */                 subTable.setColBorderColor(nextRow, 18, new Color(208, 206, 206, 0));
/*  988 */                 subTable.setColBorderColor(nextRow, 19, new Color(208, 206, 206, 0));
/*  989 */                 subTable.setColBorderColor(nextRow, 20, new Color(208, 206, 206, 0));
/*  990 */                 subTable.setColBorderColor(nextRow, 21, new Color(208, 206, 206, 0));
/*      */                 
/*  992 */                 subTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
/*  993 */                 if (!MCvend.equals("")) {
/*  994 */                   subTable.setRowHeight(nextRow, 15);
/*      */                 }
/*  996 */                 if (mfgComment != null && mfgComment.trim().length() > 0) {
/*      */                   
/*  998 */                   subTable.setRowAutoSize(true);
/*  999 */                   subTable.setObject(nextRow, 6, mfgComment);
/* 1000 */                   subTable.setFont(nextRow, 6, new Font("Arial", 2, 6));
/* 1001 */                   if (mfgComment.length() > 40) {
/* 1002 */                     subTable.setRowHeight(nextRow, 60);
/* 1003 */                   } else if (mfgComment.length() > 10) {
/* 1004 */                     subTable.setRowHeight(nextRow, 15);
/*      */                   } else {
/* 1006 */                     subTable.setRowHeight(nextRow, 15);
/*      */                   }
/*      */                 
/* 1009 */                 } else if (artistLength < 26 && labelLength < 21 && idLength + pd.length() < 15) {
/*      */                   
/* 1011 */                   subTable.setRowHeight(nextRow, 3);
/*      */                 }
/* 1013 */                 else if (labelLength > 30) {
/*      */                   
/* 1015 */                   subTable.setRowHeight(nextRow, 8);
/*      */                 } 
/* 1017 */                 body = new SectionBand(report);
/* 1018 */                 if (mfgComment != null && mfgComment.trim().length() > 0) {
/*      */                   
/* 1020 */                   double lfLineCount = 1.5D;
/* 1021 */                   if (mfgComment.trim().length() > 100) {
/* 1022 */                     lfLineCount = 8.0D;
/*      */                   }
/* 1024 */                   if (mfgComment.trim().length() > 40) {
/* 1025 */                     lfLineCount = 4.0D;
/*      */                   }
/* 1027 */                   body.setHeight((float)lfLineCount);
/*      */                 }
/*      */                 else {
/*      */                   
/* 1031 */                   body.setHeight(1.5F);
/*      */                 } 
/* 1033 */                 body.addTable(subTable, new Rectangle(800, 800));
/* 1034 */                 body.setBottomBorder(0);
/* 1035 */                 body.setTopBorder(0);
/* 1036 */                 body.setShrinkToFit(true);
/* 1037 */                 body.setVisible(true);
/* 1038 */                 group = new DefaultSectionLens(null, group, body);
/*      */                 
/* 1040 */                 nextRow = 0;
/*      */               } 
/*      */             } 
/* 1043 */             if (theConfig.equals("Compact Disc") && totalsExist(plantTotals)) {
/*      */               
/* 1045 */               nextRow = 0;
/*      */               
/* 1047 */               subTable = new DefaultTableLens(1, 14);
/*      */               
/* 1049 */               subTable.setColWidth(0, 90);
/* 1050 */               subTable.setColWidth(1, 90);
/* 1051 */               subTable.setColWidth(2, 90);
/* 1052 */               subTable.setColWidth(3, 90);
/* 1053 */               subTable.setColWidth(4, 90);
/* 1054 */               subTable.setColWidth(5, 90);
/* 1055 */               subTable.setColWidth(6, 90);
/* 1056 */               subTable.setColWidth(7, 90);
/* 1057 */               subTable.setColWidth(8, 90);
/* 1058 */               subTable.setColWidth(9, 90);
/* 1059 */               subTable.setColWidth(10, 90);
/* 1060 */               subTable.setColWidth(11, 90);
/* 1061 */               subTable.setColWidth(12, 90);
/* 1062 */               subTable.setColWidth(13, 90);
/*      */               
/* 1064 */               subTable.setRowBorder(0);
/*      */               
/* 1066 */               printTotals(plantTotals, plantIdandName, subTable, nextRow);
/*      */               
/* 1068 */               subTable.setFont(nextRow, 0, new Font("Arial", 1, 7));
/* 1069 */               subTable.setFont(nextRow, 1, new Font("Arial", 0, 7));
/* 1070 */               subTable.setFont(nextRow, 2, new Font("Arial", 1, 7));
/* 1071 */               subTable.setFont(nextRow, 3, new Font("Arial", 0, 7));
/* 1072 */               subTable.setFont(nextRow, 4, new Font("Arial", 1, 7));
/* 1073 */               subTable.setFont(nextRow, 5, new Font("Arial", 0, 7));
/* 1074 */               subTable.setFont(nextRow, 6, new Font("Arial", 1, 7));
/* 1075 */               subTable.setFont(nextRow, 7, new Font("Arial", 0, 7));
/* 1076 */               subTable.setFont(nextRow, 8, new Font("Arial", 1, 7));
/* 1077 */               subTable.setFont(nextRow, 9, new Font("Arial", 0, 7));
/* 1078 */               subTable.setFont(nextRow, 10, new Font("Arial", 1, 7));
/* 1079 */               subTable.setFont(nextRow, 11, new Font("Arial", 0, 7));
/* 1080 */               subTable.setFont(nextRow, 12, new Font("Arial", 1, 7));
/* 1081 */               subTable.setFont(nextRow, 13, new Font("Arial", 0, 7));
/*      */               
/* 1083 */               setColBorders(nextRow, -1, NUM_COLS - 1, subTable, 0);
/* 1084 */               setColBorderColors(nextRow, -1, NUM_COLS - 1, subTable, Color.white);
/* 1085 */               subTable.setRowBorder(nextRow, 0);
/*      */               
/* 1087 */               body = new SectionBand(report);
/* 1088 */               body.setHeight(1.0F);
/*      */               
/* 1090 */               body.addTable(subTable, new Rectangle(800, 800));
/* 1091 */               body.setBottomBorder(0);
/* 1092 */               body.setTopBorder(0);
/* 1093 */               body.setShrinkToFit(true);
/* 1094 */               body.setVisible(true);
/*      */               
/* 1096 */               group = new DefaultSectionLens(null, group, body);
/*      */               
/* 1098 */               nextRow = 0;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/* 1103 */       group = new DefaultSectionLens(hbandHeader, group, null);
/* 1104 */       report.addSection(group, rowCountTable);
/* 1105 */       group = null;
/*      */     }
/* 1107 */     catch (Exception e) {
/*      */       
/* 1109 */       System.out.println(">>>>>>>>errors");
/* 1110 */       System.out.println(">>>>>>>>ReportHandler.fillUmlNewReleaseMasterScheduleSubHandler(): exception: " + e.getMessage());
/* 1111 */       System.out.println(">>>>>>>>ReportHandler.fillUmlNewReleaseMasterScheduleSubHandler(): exception: " + e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void setColBorderColors(int rowNum, int start, int end, DefaultTableLens table, Color color) {
/* 1118 */     for (int i = start; i < end; i++) {
/* 1119 */       table.setColBorderColor(rowNum, i, Color.white);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void setRowBorderColors(int rowNum, int start, int end, DefaultTableLens table, Color color) {
/* 1126 */     for (int i = start; i < end; i++) {
/* 1127 */       table.setRowBorderColor(rowNum, i, Color.white);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void setColBorders(int rowNum, int start, int end, DefaultTableLens table, int size) {
/* 1134 */     for (int i = start; i < end; i++) {
/* 1135 */       table.setColBorder(rowNum, i, size);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static Hashtable buildPlantHash() {
/* 1141 */     plantNames = new Hashtable();
/* 1142 */     String plantQuery = " SELECT det_value, description FROM vi_Lookup_SubDetail WHERE field_id = 22";
/* 1143 */     JdbcConnector connector = MilestoneHelper.getConnector(plantQuery);
/* 1144 */     connector.setForwardOnly(false);
/* 1145 */     connector.runQuery();
/* 1146 */     while (connector.more()) {
/*      */       
/* 1148 */       String plantId = connector.getField("det_value");
/* 1149 */       String plantDescription = connector.getField("description");
/* 1150 */       if (plantId != null && !plantId.equals("") && plantDescription != null && !plantDescription.equals("")) {
/* 1151 */         plantNames.put(plantId, plantDescription);
/*      */       }
/* 1153 */       connector.next();
/*      */     } 
/* 1155 */     connector.close();
/* 1156 */     return plantNames;
/*      */   }
/*      */ 
/*      */   
/*      */   private static Hashtable buildTotals(Hashtable namesHash) {
/* 1161 */     Enumeration keysEnum = namesHash.keys();
/* 1162 */     Hashtable plantTotals = new Hashtable();
/* 1163 */     while (keysEnum.hasMoreElements()) {
/* 1164 */       plantTotals.put((String)keysEnum.nextElement(), "0");
/*      */     }
/* 1166 */     return plantTotals;
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean totalsExist(Hashtable plantTotals) {
/* 1171 */     Enumeration keysEnum = plantTotals.keys();
/* 1172 */     while (keysEnum.hasMoreElements()) {
/*      */       
/* 1174 */       int total = Integer.parseInt((String)plantTotals.get(keysEnum.nextElement()));
/* 1175 */       if (total > 0) {
/* 1176 */         return true;
/*      */       }
/*      */     } 
/* 1179 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   private static void printTotals(Hashtable plantTotals, Hashtable plantNames, DefaultTableLens subTable, int nextRow) {
/* 1184 */     Enumeration keysEnum = plantTotals.keys();
/* 1185 */     Vector keys = new Vector();
/* 1186 */     int columnCount = 0;
/* 1187 */     while (keysEnum.hasMoreElements()) {
/* 1188 */       keys.add(keysEnum.nextElement());
/*      */     }
/* 1190 */     int maxCol = 14;
/* 1191 */     for (int i = 0; i < keys.size(); i++) {
/*      */       
/* 1193 */       String idString = (plantTotals.get(keys.get(i)) != null) ? (String)plantTotals.get(keys.get(i)) : "0";
/* 1194 */       int total = Integer.parseInt(idString);
/* 1195 */       String plantName = (plantNames.get(keys.get(i)) != null) ? (String)plantNames.get(keys.get(i)) : "";
/* 1196 */       if (total > 0 && columnCount < 14) {
/*      */         
/* 1198 */         subTable.setObject(nextRow, columnCount, String.valueOf(plantName) + ":\n" + MilestoneHelper.formatQuantityWithCommas(String.valueOf(total)));
/* 1199 */         columnCount++;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\UmlNewReleaseMasterScheduleSubHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */