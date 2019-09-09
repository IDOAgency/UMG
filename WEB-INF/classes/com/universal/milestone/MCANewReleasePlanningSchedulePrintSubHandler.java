/*      */ package WEB-INF.classes.com.universal.milestone;
/*      */ 
/*      */ import com.techempower.ComponentLog;
/*      */ import com.techempower.gemini.Context;
/*      */ import com.techempower.gemini.GeminiApplication;
/*      */ import com.universal.milestone.Form;
/*      */ import com.universal.milestone.MCANewReleasePlanningSchedulePrintSubHandler;
/*      */ import com.universal.milestone.MilestoneHelper;
/*      */ import com.universal.milestone.MonthYearComparator;
/*      */ import com.universal.milestone.MultOtherContact;
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
/*      */ public class MCANewReleasePlanningSchedulePrintSubHandler
/*      */   extends SecureHandler
/*      */ {
/*      */   public static final String COMPONENT_CODE = "hMCAProd";
/*      */   public GeminiApplication application;
/*      */   public ComponentLog log;
/*      */   
/*      */   public void MCANewReleasePlanningSchedulePrintSubHandler(GeminiApplication application) {
/*   66 */     this.application = application;
/*   67 */     this.log = application.getLog("hMCAProd");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   75 */   public String getDescription() { return "Sub Report"; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static void fillMCANewReleasePlanningScheduleForPrint(XStyleSheet report, Context context) {
/*   87 */     int COL_LINE_STYLE = 4097;
/*   88 */     int HEADER_FONT_SIZE = 12;
/*      */     
/*   90 */     double ldLineVal = 0.3D;
/*      */     
/*      */     try {
/*   93 */       HttpServletResponse sresponse = context.getResponse();
/*   94 */       context.putDelivery("status", new String("start_gathering"));
/*   95 */       context.includeJSP("status.jsp", "hiddenFrame");
/*   96 */       sresponse.setContentType("text/plain");
/*   97 */       sresponse.flushBuffer();
/*      */     }
/*   99 */     catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  104 */     Vector selections = MilestoneHelper.getSelectionsForReport(context);
/*      */     
/*      */     try {
/*  107 */       HttpServletResponse sresponse = context.getResponse();
/*  108 */       context.putDelivery("status", new String("start_report"));
/*  109 */       context.putDelivery("percent", new String("10"));
/*  110 */       context.includeJSP("status.jsp", "hiddenFrame");
/*  111 */       sresponse.setContentType("text/plain");
/*  112 */       sresponse.flushBuffer();
/*      */     }
/*  114 */     catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  119 */     int DATA_FONT_SIZE = 7;
/*  120 */     int SMALL_HEADER_FONT_SIZE = 8;
/*  121 */     int NUM_COLUMNS = 24;
/*  122 */     Color SHADED_AREA_COLOR = Color.lightGray;
/*      */ 
/*      */     
/*  125 */     SectionBand hbandType = new SectionBand(report);
/*  126 */     SectionBand hbandCategory = new SectionBand(report);
/*  127 */     SectionBand hbandDate = new SectionBand(report);
/*  128 */     SectionBand hbandRelWeek = new SectionBand(report);
/*  129 */     SectionBand body = new SectionBand(report);
/*  130 */     SectionBand footer = new SectionBand(report);
/*  131 */     SectionBand spacer = new SectionBand(report);
/*  132 */     DefaultSectionLens group = null;
/*      */     
/*  134 */     footer.setVisible(true);
/*  135 */     footer.setHeight(0.1F);
/*  136 */     footer.setShrinkToFit(false);
/*  137 */     footer.setBottomBorder(0);
/*      */     
/*  139 */     spacer.setVisible(true);
/*  140 */     spacer.setHeight(0.05F);
/*  141 */     spacer.setShrinkToFit(false);
/*  142 */     spacer.setBottomBorder(0);
/*      */ 
/*      */     
/*  145 */     Hashtable selTable = MilestoneHelper.groupSelectionsForMcaByTypeConfigAndStreetDate(selections);
/*  146 */     Enumeration configs = selTable.keys();
/*  147 */     Vector configVector = new Vector();
/*      */     
/*  149 */     while (configs.hasMoreElements()) {
/*  150 */       configVector.addElement(configs.nextElement());
/*      */     }
/*  152 */     int numConfigs = configVector.size();
/*      */ 
/*      */     
/*      */     try {
/*  156 */       Collections.sort(configVector);
/*  157 */       Vector sortedConfigVector = MilestoneHelper.sortStrings(configVector);
/*      */ 
/*      */       
/*  160 */       DefaultTableLens table_contents = null;
/*  161 */       DefaultTableLens rowCountTable = null;
/*  162 */       DefaultTableLens columnHeaderTable = null;
/*  163 */       DefaultTableLens subTable = null;
/*  164 */       DefaultTableLens monthTableLens = null;
/*  165 */       DefaultTableLens dateTableLens = null;
/*  166 */       DefaultTableLens relWeekLens = null;
/*      */       
/*  168 */       rowCountTable = new DefaultTableLens(2, 10000);
/*      */       
/*  170 */       int totalCount = 0;
/*  171 */       int tenth = 1;
/*      */ 
/*      */       
/*  174 */       for (int n = 0; n < sortedConfigVector.size(); n++) {
/*      */         
/*  176 */         String monthName = (sortedConfigVector.elementAt(n) != null) ? (String)sortedConfigVector.elementAt(n) : "";
/*  177 */         Vector selectionsForMonth = (Vector)selTable.get(monthName);
/*  178 */         totalCount += selectionsForMonth.size();
/*      */       } 
/*      */ 
/*      */       
/*  182 */       tenth = (totalCount > 10) ? (totalCount / 10) : 1;
/*      */       
/*  184 */       HttpServletResponse sresponse = context.getResponse();
/*  185 */       context.putDelivery("status", new String("start_report"));
/*  186 */       context.putDelivery("percent", new String("20"));
/*  187 */       context.includeJSP("status.jsp", "hiddenFrame");
/*  188 */       sresponse.setContentType("text/plain");
/*  189 */       sresponse.flushBuffer();
/*      */       
/*  191 */       int recordCount = 0;
/*  192 */       int count = 0;
/*      */ 
/*      */       
/*  195 */       Object[] monthArray = (Object[])null;
/*  196 */       monthArray = sortedConfigVector.toArray();
/*  197 */       Arrays.sort(monthArray, new MonthYearComparator());
/*      */       
/*  199 */       for (int x = 0; x < monthArray.length; x++)
/*      */       {
/*      */ 
/*      */ 
/*      */         
/*  204 */         Form reportForm = (Form)context.getSessionValue("reportForm");
/*      */         
/*  206 */         Calendar beginStDate = (reportForm.getStringValue("beginDate") != null && 
/*  207 */           reportForm.getStringValue("beginDate").length() > 0) ? 
/*  208 */           MilestoneHelper.getDate(reportForm.getStringValue("beginDate")) : null;
/*      */         
/*  210 */         Calendar endStDate = (reportForm.getStringValue("endDate") != null && 
/*  211 */           reportForm.getStringValue("endDate").length() > 0) ? 
/*  212 */           MilestoneHelper.getDate(reportForm.getStringValue("endDate")) : null;
/*      */         
/*  214 */         report.setElement("crs_startdate", MilestoneHelper.getFormatedDate(beginStDate));
/*  215 */         report.setElement("crs_enddate", MilestoneHelper.getFormatedDate(endStDate));
/*      */         
/*  217 */         SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy, h:mm a");
/*  218 */         String todayLong = formatter.format(new Date());
/*  219 */         report.setElement("crs_bottomdate", todayLong);
/*      */         
/*  221 */         String monthName = (String)monthArray[x];
/*  222 */         Vector selectionsVector = (Vector)selTable.get(monthName);
/*      */         
/*  224 */         String previousReleaseWeek = "";
/*  225 */         boolean newCycle = false;
/*      */ 
/*      */         
/*  228 */         int numMonths = 0;
/*  229 */         int numDates = 0;
/*  230 */         int numSelections = 0;
/*      */         
/*  232 */         if (selectionsVector != null) {
/*      */           
/*  234 */           numMonths++;
/*  235 */           numSelections += selectionsVector.size();
/*      */         } 
/*      */         
/*  238 */         int numRows = 0;
/*      */ 
/*      */         
/*  241 */         numRows += numMonths * 3;
/*  242 */         numRows += numDates * 2;
/*  243 */         numRows += numSelections * 2;
/*      */         
/*  245 */         numRows += 5;
/*      */ 
/*      */         
/*  248 */         hbandType = new SectionBand(report);
/*  249 */         hbandType.setHeight(2.5F);
/*  250 */         hbandType.setShrinkToFit(true);
/*  251 */         hbandType.setVisible(true);
/*      */ 
/*      */         
/*  254 */         int nextRow = 0;
/*      */         
/*  256 */         columnHeaderTable = new DefaultTableLens(2, 24);
/*      */         
/*  258 */         columnHeaderTable.setHeaderRowCount(1);
/*  259 */         columnHeaderTable.setColBorderColor(Color.lightGray);
/*  260 */         columnHeaderTable.setColWidth(0, 100);
/*  261 */         columnHeaderTable.setColWidth(1, 100);
/*  262 */         columnHeaderTable.setColWidth(2, 130);
/*  263 */         columnHeaderTable.setColWidth(3, 130);
/*  264 */         columnHeaderTable.setColWidth(4, 130);
/*  265 */         columnHeaderTable.setColWidth(5, 80);
/*  266 */         columnHeaderTable.setColWidth(6, 90);
/*  267 */         columnHeaderTable.setColWidth(7, 80);
/*  268 */         columnHeaderTable.setColWidth(8, 100);
/*  269 */         columnHeaderTable.setColWidth(9, 80);
/*  270 */         columnHeaderTable.setColWidth(10, 100);
/*  271 */         columnHeaderTable.setColWidth(11, 60);
/*  272 */         columnHeaderTable.setColWidth(12, 65);
/*  273 */         columnHeaderTable.setColWidth(13, 80);
/*  274 */         columnHeaderTable.setColWidth(14, 80);
/*  275 */         columnHeaderTable.setColWidth(15, 60);
/*  276 */         columnHeaderTable.setColWidth(16, 70);
/*  277 */         columnHeaderTable.setColWidth(17, 80);
/*  278 */         columnHeaderTable.setColWidth(18, 70);
/*  279 */         columnHeaderTable.setColWidth(19, 70);
/*  280 */         columnHeaderTable.setColWidth(20, 75);
/*  281 */         columnHeaderTable.setColWidth(21, 60);
/*  282 */         columnHeaderTable.setColWidth(22, 100);
/*  283 */         columnHeaderTable.setColWidth(23, 60);
/*      */         
/*  285 */         columnHeaderTable.setAlignment(nextRow, 0, 33);
/*  286 */         columnHeaderTable.setAlignment(nextRow, 1, 34);
/*  287 */         columnHeaderTable.setAlignment(nextRow, 2, 34);
/*  288 */         columnHeaderTable.setAlignment(nextRow, 3, 34);
/*  289 */         columnHeaderTable.setAlignment(nextRow, 4, 34);
/*  290 */         columnHeaderTable.setAlignment(nextRow, 5, 34);
/*  291 */         columnHeaderTable.setAlignment(nextRow, 6, 34);
/*  292 */         columnHeaderTable.setAlignment(nextRow, 7, 34);
/*  293 */         columnHeaderTable.setAlignment(nextRow, 8, 34);
/*  294 */         columnHeaderTable.setAlignment(nextRow, 9, 34);
/*  295 */         columnHeaderTable.setAlignment(nextRow, 10, 34);
/*  296 */         columnHeaderTable.setAlignment(nextRow, 11, 34);
/*  297 */         columnHeaderTable.setAlignment(nextRow, 12, 34);
/*  298 */         columnHeaderTable.setAlignment(nextRow, 13, 34);
/*  299 */         columnHeaderTable.setAlignment(nextRow, 14, 34);
/*  300 */         columnHeaderTable.setAlignment(nextRow, 15, 34);
/*  301 */         columnHeaderTable.setAlignment(nextRow, 16, 34);
/*  302 */         columnHeaderTable.setAlignment(nextRow, 17, 34);
/*  303 */         columnHeaderTable.setAlignment(nextRow, 18, 34);
/*  304 */         columnHeaderTable.setAlignment(nextRow, 19, 34);
/*  305 */         columnHeaderTable.setAlignment(nextRow, 20, 34);
/*  306 */         columnHeaderTable.setAlignment(nextRow, 21, 34);
/*  307 */         columnHeaderTable.setAlignment(nextRow, 22, 34);
/*  308 */         columnHeaderTable.setAlignment(nextRow, 23, 34);
/*      */ 
/*      */         
/*  311 */         columnHeaderTable.setRowFont(nextRow, new Font("Arial", 0, 6));
/*  312 */         columnHeaderTable.setObject(nextRow, 0, "Project/\nAlbum Title");
/*  313 */         columnHeaderTable.setObject(nextRow, 1, "Street Date");
/*  314 */         columnHeaderTable.setObject(nextRow, 2, "Marketing\nDirector");
/*  315 */         columnHeaderTable.setObject(nextRow, 3, "Art Director");
/*  316 */         columnHeaderTable.setObject(nextRow, 4, "A&R Contact");
/*  317 */         columnHeaderTable.setObject(nextRow, 5, "Image meeting");
/*  318 */         columnHeaderTable.setObject(nextRow, 6, "photoshoot");
/*  319 */         columnHeaderTable.setObject(nextRow, 7, "pick slot");
/*  320 */         columnHeaderTable.setObject(nextRow, 8, "cd pro\n cover approved");
/*  321 */         columnHeaderTable.setObject(nextRow, 9, "planning mtg");
/*  322 */         columnHeaderTable.setObject(nextRow, 10, "album cover approved");
/*  323 */         columnHeaderTable.setObject(nextRow, 11, "cd pro in house");
/*  324 */         columnHeaderTable.setObject(nextRow, 12, "cdr's to publicity");
/*  325 */         columnHeaderTable.setObject(nextRow, 13, "solicitation content due");
/*  326 */         columnHeaderTable.setObject(nextRow, 14, "send Marketing Brief");
/*  327 */         columnHeaderTable.setObject(nextRow, 15, "completed credits\nto Creative");
/*  328 */         columnHeaderTable.setObject(nextRow, 16, "impact date");
/*  329 */         columnHeaderTable.setObject(nextRow, 17, "UMVD advances in house");
/*  330 */         columnHeaderTable.setObject(nextRow, 18, "Mechanical\nbegins\nrouting");
/*  331 */         columnHeaderTable.setObject(nextRow, 19, "Comm. Single in stores");
/*  332 */         columnHeaderTable.setObject(nextRow, 20, "send Marketing Plan");
/*  333 */         columnHeaderTable.setObject(nextRow, 21, "Sticker copy due\nto Production");
/*  334 */         columnHeaderTable.setObject(nextRow, 22, "Mechanical\nto\nSeparations");
/*  335 */         columnHeaderTable.setObject(nextRow, 23, "Package film\nships to\nPrinter");
/*      */         
/*  337 */         columnHeaderTable.setRowBorderColor(nextRow - 1, SHADED_AREA_COLOR);
/*  338 */         columnHeaderTable.setRowBorderColor(nextRow, SHADED_AREA_COLOR);
/*  339 */         columnHeaderTable.setRowBackground(nextRow, Color.white);
/*  340 */         columnHeaderTable.setRowForeground(nextRow, Color.black);
/*      */ 
/*      */         
/*  343 */         nextRow = 1;
/*  344 */         columnHeaderTable.setRowBorderColor(nextRow - 1, SHADED_AREA_COLOR);
/*      */         
/*  346 */         columnHeaderTable.setRowBorderColor(nextRow, SHADED_AREA_COLOR);
/*  347 */         columnHeaderTable.setRowBackground(nextRow, Color.white);
/*  348 */         columnHeaderTable.setRowForeground(nextRow, Color.black);
/*  349 */         columnHeaderTable.setRowFont(nextRow, new Font("Arial", 0, 6));
/*      */         
/*  351 */         columnHeaderTable.setSpan(nextRow, 0, new Dimension(5, 1));
/*  352 */         columnHeaderTable.setObject(nextRow, 0, "Weeks Prior to Release:\nDay of Week:");
/*  353 */         columnHeaderTable.setObject(nextRow, 5, "30\nMonday");
/*  354 */         columnHeaderTable.setObject(nextRow, 6, "26\nFriday");
/*  355 */         columnHeaderTable.setObject(nextRow, 7, "24\nTuesday");
/*  356 */         columnHeaderTable.setObject(nextRow, 8, "19\nFriday");
/*  357 */         columnHeaderTable.setObject(nextRow, 9, "18\nThursday");
/*  358 */         columnHeaderTable.setObject(nextRow, 10, "16\nFriday");
/*  359 */         columnHeaderTable.setObject(nextRow, 11, "14\nMonday");
/*  360 */         columnHeaderTable.setObject(nextRow, 12, "13\nMonday");
/*  361 */         columnHeaderTable.setObject(nextRow, 13, "12\nMonday");
/*  362 */         columnHeaderTable.setObject(nextRow, 14, "12\nMonday");
/*  363 */         columnHeaderTable.setObject(nextRow, 15, "11\nFriday");
/*  364 */         columnHeaderTable.setObject(nextRow, 16, "10\nTuesday");
/*  365 */         columnHeaderTable.setObject(nextRow, 17, "9\nMonday");
/*  366 */         columnHeaderTable.setObject(nextRow, 18, "8\nFriday");
/*  367 */         columnHeaderTable.setObject(nextRow, 19, "8\nTuesday");
/*  368 */         columnHeaderTable.setObject(nextRow, 20, "8\nTuesday");
/*  369 */         columnHeaderTable.setObject(nextRow, 21, "8\nFriday");
/*  370 */         columnHeaderTable.setObject(nextRow, 22, "6\nTuesday");
/*  371 */         columnHeaderTable.setObject(nextRow, 23, "6\nFriday");
/*  372 */         columnHeaderTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
/*  373 */         columnHeaderTable.setRowHeight(nextRow, 20);
/*      */         
/*  375 */         columnHeaderTable.setAlignment(nextRow, 0, 36);
/*  376 */         columnHeaderTable.setAlignment(nextRow, 1, 34);
/*  377 */         columnHeaderTable.setAlignment(nextRow, 2, 34);
/*  378 */         columnHeaderTable.setAlignment(nextRow, 3, 34);
/*  379 */         columnHeaderTable.setAlignment(nextRow, 4, 34);
/*  380 */         columnHeaderTable.setAlignment(nextRow, 5, 34);
/*  381 */         columnHeaderTable.setAlignment(nextRow, 6, 34);
/*  382 */         columnHeaderTable.setAlignment(nextRow, 7, 34);
/*  383 */         columnHeaderTable.setAlignment(nextRow, 8, 34);
/*  384 */         columnHeaderTable.setAlignment(nextRow, 9, 34);
/*  385 */         columnHeaderTable.setAlignment(nextRow, 10, 34);
/*  386 */         columnHeaderTable.setAlignment(nextRow, 11, 34);
/*  387 */         columnHeaderTable.setAlignment(nextRow, 12, 34);
/*  388 */         columnHeaderTable.setAlignment(nextRow, 13, 34);
/*  389 */         columnHeaderTable.setAlignment(nextRow, 14, 34);
/*  390 */         columnHeaderTable.setAlignment(nextRow, 15, 34);
/*  391 */         columnHeaderTable.setAlignment(nextRow, 16, 34);
/*  392 */         columnHeaderTable.setAlignment(nextRow, 17, 34);
/*  393 */         columnHeaderTable.setAlignment(nextRow, 18, 34);
/*  394 */         columnHeaderTable.setAlignment(nextRow, 19, 34);
/*  395 */         columnHeaderTable.setAlignment(nextRow, 20, 34);
/*  396 */         columnHeaderTable.setAlignment(nextRow, 21, 34);
/*  397 */         columnHeaderTable.setAlignment(nextRow, 22, 34);
/*  398 */         columnHeaderTable.setAlignment(nextRow, 23, 34);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  457 */         hbandType.addTable(columnHeaderTable, new Rectangle(0, 24, 800, 150));
/*  458 */         hbandType.setBottomBorder(0);
/*      */ 
/*      */         
/*  461 */         if (selTable != null) {
/*      */ 
/*      */           
/*  464 */           monthName = (String)monthArray[x];
/*  465 */           String monthNameString = monthName;
/*      */ 
/*      */           
/*      */           try {
/*  469 */             monthNameString = String.valueOf(MONTHS[Integer.parseInt(monthName.substring(0, 2)) - 1]) + " " + Integer.parseInt(monthName.substring(3, 7));
/*      */           }
/*  471 */           catch (Exception e) {
/*      */             
/*  473 */             monthNameString = "No street date";
/*      */           } 
/*  475 */           monthTableLens = new DefaultTableLens(1, 24);
/*  476 */           hbandCategory = new SectionBand(report);
/*  477 */           hbandCategory.setHeight(0.25F);
/*  478 */           hbandCategory.setShrinkToFit(true);
/*  479 */           hbandCategory.setVisible(true);
/*  480 */           hbandCategory.setBottomBorder(0);
/*  481 */           hbandCategory.setLeftBorder(0);
/*  482 */           hbandCategory.setRightBorder(0);
/*  483 */           hbandCategory.setTopBorder(0);
/*      */           
/*  485 */           nextRow = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  490 */           monthTableLens.setRowFont(nextRow, new Font("Arial", 1, 9));
/*  491 */           monthTableLens.setColBorderColor(SHADED_AREA_COLOR);
/*  492 */           monthTableLens.setRowHeight(nextRow, 14);
/*      */           
/*  494 */           monthTableLens.setBackground(0, 0, SHADED_AREA_COLOR);
/*  495 */           monthTableLens.setBackground(0, 1, SHADED_AREA_COLOR);
/*  496 */           monthTableLens.setSpan(nextRow, 0, new Dimension(2, 1));
/*  497 */           monthTableLens.setObject(nextRow, 0, monthNameString);
/*  498 */           monthTableLens.setRowForeground(nextRow, Color.black);
/*      */           
/*  500 */           monthTableLens.setColWidth(0, 100);
/*  501 */           monthTableLens.setColWidth(1, 100);
/*  502 */           monthTableLens.setColWidth(2, 130);
/*  503 */           monthTableLens.setColWidth(3, 130);
/*  504 */           monthTableLens.setColWidth(4, 130);
/*  505 */           monthTableLens.setColWidth(5, 80);
/*  506 */           monthTableLens.setColWidth(6, 90);
/*  507 */           monthTableLens.setColWidth(7, 80);
/*  508 */           monthTableLens.setColWidth(8, 100);
/*  509 */           monthTableLens.setColWidth(9, 80);
/*  510 */           monthTableLens.setColWidth(10, 100);
/*  511 */           monthTableLens.setColWidth(11, 60);
/*  512 */           monthTableLens.setColWidth(12, 65);
/*  513 */           monthTableLens.setColWidth(13, 80);
/*  514 */           monthTableLens.setColWidth(14, 80);
/*  515 */           monthTableLens.setColWidth(15, 60);
/*  516 */           monthTableLens.setColWidth(16, 70);
/*  517 */           monthTableLens.setColWidth(17, 80);
/*  518 */           monthTableLens.setColWidth(18, 70);
/*  519 */           monthTableLens.setColWidth(19, 70);
/*  520 */           monthTableLens.setColWidth(20, 75);
/*  521 */           monthTableLens.setColWidth(21, 60);
/*  522 */           monthTableLens.setColWidth(22, 100);
/*  523 */           monthTableLens.setColWidth(23, 60);
/*      */           
/*  525 */           monthTableLens.setSpan(nextRow, 2, new Dimension(22, 1));
/*  526 */           monthTableLens.setBackground(0, 2, SHADED_AREA_COLOR);
/*  527 */           monthTableLens.setRowBorderColor(nextRow, SHADED_AREA_COLOR);
/*  528 */           monthTableLens.setRowBorderColor(nextRow - 1, SHADED_AREA_COLOR);
/*  529 */           monthTableLens.setColBorderColor(nextRow, -1, SHADED_AREA_COLOR);
/*  530 */           monthTableLens.setColBorderColor(nextRow, 0, SHADED_AREA_COLOR);
/*  531 */           monthTableLens.setColBorderColor(nextRow, 14, SHADED_AREA_COLOR);
/*      */           
/*  533 */           monthTableLens.setRowBorderColor(nextRow + 1, SHADED_AREA_COLOR);
/*      */           
/*  535 */           hbandCategory.addTable(monthTableLens, new Rectangle(800, 800));
/*      */           
/*  537 */           footer.setVisible(true);
/*  538 */           footer.setHeight(0.1F);
/*  539 */           footer.setShrinkToFit(false);
/*  540 */           footer.setBottomBorder(0);
/*      */           
/*  542 */           group = new DefaultSectionLens(null, group, spacer);
/*  543 */           group = new DefaultSectionLens(null, group, hbandCategory);
/*  544 */           group = new DefaultSectionLens(null, group, spacer);
/*      */           
/*  546 */           selections = (Vector)selTable.get(monthName);
/*      */ 
/*      */           
/*  549 */           MilestoneHelper.setSelectionSorting(selections, 12);
/*  550 */           Collections.sort(selections);
/*      */ 
/*      */           
/*  553 */           MilestoneHelper.setSelectionSorting(selections, 14);
/*  554 */           Collections.sort(selections);
/*      */ 
/*      */           
/*  557 */           MilestoneHelper.setSelectionSorting(selections, 4);
/*  558 */           Collections.sort(selections);
/*      */ 
/*      */           
/*  561 */           MilestoneHelper.setSelectionSorting(selections, 3);
/*  562 */           Collections.sort(selections);
/*      */ 
/*      */           
/*  565 */           MilestoneHelper.setSelectionSorting(selections, 1);
/*  566 */           Collections.sort(selections);
/*      */ 
/*      */           
/*  569 */           for (int i = 0; i < selections.size(); i++) {
/*      */             
/*  571 */             Selection sel = (Selection)selections.elementAt(i);
/*      */             
/*  573 */             if (count < recordCount / tenth) {
/*      */               
/*  575 */               count = recordCount / tenth;
/*  576 */               sresponse = context.getResponse();
/*  577 */               context.putDelivery("status", new String("start_report"));
/*  578 */               int myPercent = count * 10;
/*  579 */               if (myPercent > 90)
/*  580 */                 myPercent = 90; 
/*  581 */               context.putDelivery("percent", new String(String.valueOf(myPercent)));
/*  582 */               context.includeJSP("status.jsp", "hiddenFrame");
/*  583 */               sresponse.setContentType("text/plain");
/*  584 */               sresponse.flushBuffer();
/*      */             } 
/*  586 */             recordCount++;
/*      */ 
/*      */ 
/*      */             
/*  590 */             String dateNameText = MilestoneHelper.getFormatedDate(sel.getStreetDate());
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  595 */             String titleId = "";
/*  596 */             titleId = sel.getTitleID();
/*      */             
/*  598 */             String filler = "";
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  603 */             String artist = "";
/*  604 */             artist = sel.getArtist().trim();
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  609 */             String title = "";
/*  610 */             if (sel.getTitle() != null) {
/*  611 */               title = sel.getTitle();
/*      */             }
/*      */ 
/*      */ 
/*      */             
/*  616 */             Schedule schedule = sel.getSchedule();
/*      */             
/*  618 */             Vector tasks = (schedule != null) ? schedule.getTasks() : null;
/*  619 */             ScheduledTask task = null;
/*      */             
/*  621 */             String IMG = "";
/*  622 */             String PHO = "";
/*  623 */             String SLT = "";
/*  624 */             String PRO = "";
/*  625 */             String PLN = "";
/*  626 */             String ALB = "";
/*  627 */             String PIH = "";
/*  628 */             String CRDA = "";
/*  629 */             String CDRP = "";
/*  630 */             String CNT = "";
/*  631 */             String MKB = "";
/*  632 */             String IMP = "";
/*  633 */             String ADIH = "";
/*  634 */             String MBR = "";
/*  635 */             String SGL = "";
/*  636 */             String MKP = "";
/*  637 */             String STD = "";
/*  638 */             String PFS = "";
/*  639 */             String SEPS = "";
/*      */             
/*  641 */             String IMGcom = "";
/*  642 */             String PHOcom = "";
/*  643 */             String SLTcom = "";
/*  644 */             String PROcom = "";
/*  645 */             String PLNcom = "";
/*  646 */             String ALBcom = "";
/*  647 */             String PIHcom = "";
/*  648 */             String CRDAcom = "";
/*  649 */             String CDRPcom = "";
/*  650 */             String CNTcom = "";
/*  651 */             String MKBcom = "";
/*  652 */             String IMPcom = "";
/*  653 */             String ADIHcom = "";
/*  654 */             String MBRcom = "";
/*  655 */             String SGLcom = "";
/*  656 */             String MKPcom = "";
/*  657 */             String STDcom = "";
/*  658 */             String PFScom = "";
/*  659 */             String SEPScom = "";
/*      */             
/*  661 */             String taskVendor = "";
/*      */             
/*  663 */             if (tasks != null)
/*      */             {
/*  665 */               for (int j = 0; j < tasks.size(); j++) {
/*      */                 
/*  667 */                 task = (ScheduledTask)tasks.get(j);
/*      */                 
/*  669 */                 taskVendor = (task.getVendor() != null) ? task.getVendor() : "";
/*  670 */                 taskVendor = taskVendor.equals("\n") ? "" : taskVendor;
/*  671 */                 taskVendor = "";
/*      */                 
/*  673 */                 if (task != null) {
/*      */ 
/*      */                   
/*  676 */                   String dueDate = "";
/*  677 */                   if (task.getDueDate() != null) {
/*  678 */                     SimpleDateFormat dueDateFormatter = new SimpleDateFormat("M/d");
/*  679 */                     dueDate = String.valueOf(dueDateFormatter.format(task.getDueDate().getTime())) + " " + MilestoneHelper.getDayType(task.getDueDate());
/*      */                   } 
/*      */                   
/*  682 */                   String completionDate = "";
/*  683 */                   if (task.getCompletionDate() != null) {
/*  684 */                     SimpleDateFormat completionDateFormatter = new SimpleDateFormat("M/d");
/*  685 */                     completionDate = completionDateFormatter.format(task.getCompletionDate().getTime());
/*      */                   } 
/*      */ 
/*      */ 
/*      */                   
/*  690 */                   if (task.getScheduledTaskStatus().equals("N/A"))
/*      */                   {
/*  692 */                     completionDate = task.getScheduledTaskStatus();
/*      */                   }
/*      */                   
/*  695 */                   String taskAbbrev = MilestoneHelper.getTaskAbbreivationNameById(task.getTaskAbbreviationID());
/*      */                   
/*  697 */                   if (taskAbbrev.equalsIgnoreCase("IMG")) {
/*      */                     
/*  699 */                     IMG = dueDate;
/*  700 */                     if (task.getScheduledTaskStatus().equals("N/A")) {
/*  701 */                       IMGcom = "n/a";
/*      */                     }
/*  703 */                     else if (task.getScheduledTaskStatus().equals("Done")) {
/*  704 */                       IMGcom = "Done";
/*      */                     } else {
/*  706 */                       IMGcom = completionDate;
/*      */                     }
/*      */                   
/*  709 */                   } else if (taskAbbrev.equalsIgnoreCase("PHO")) {
/*      */                     
/*  711 */                     PHO = dueDate;
/*  712 */                     if (task.getScheduledTaskStatus().equals("N/A")) {
/*  713 */                       PHOcom = "n/a";
/*      */                     }
/*  715 */                     else if (task.getScheduledTaskStatus().equals("Done")) {
/*  716 */                       PHOcom = "Done";
/*      */                     } else {
/*  718 */                       PHOcom = completionDate;
/*      */                     }
/*      */                   
/*  721 */                   } else if (taskAbbrev.equalsIgnoreCase("SLT")) {
/*      */                     
/*  723 */                     SLT = dueDate;
/*  724 */                     if (task.getScheduledTaskStatus().equals("N/A")) {
/*  725 */                       SLTcom = "n/a";
/*      */                     }
/*  727 */                     else if (task.getScheduledTaskStatus().equals("Done")) {
/*  728 */                       SLTcom = "Done";
/*      */                     } else {
/*  730 */                       SLTcom = completionDate;
/*      */                     }
/*      */                   
/*  733 */                   } else if (taskAbbrev.equalsIgnoreCase("PRO")) {
/*      */                     
/*  735 */                     PRO = dueDate;
/*  736 */                     if (task.getScheduledTaskStatus().equals("N/A")) {
/*  737 */                       PROcom = "n/a";
/*      */                     }
/*  739 */                     else if (task.getScheduledTaskStatus().equals("Done")) {
/*  740 */                       PROcom = "Done";
/*      */                     } else {
/*  742 */                       PROcom = completionDate;
/*      */                     }
/*      */                   
/*  745 */                   } else if (taskAbbrev.equalsIgnoreCase("PLN")) {
/*      */                     
/*  747 */                     PLN = dueDate;
/*  748 */                     if (task.getScheduledTaskStatus().equals("N/A")) {
/*  749 */                       PLNcom = "n/a";
/*      */                     }
/*  751 */                     else if (task.getScheduledTaskStatus().equals("Done")) {
/*  752 */                       PLNcom = "Done";
/*      */                     } else {
/*  754 */                       PLNcom = completionDate;
/*      */                     }
/*      */                   
/*  757 */                   } else if (taskAbbrev.equalsIgnoreCase("ALB")) {
/*      */                     
/*  759 */                     ALB = dueDate;
/*  760 */                     if (task.getScheduledTaskStatus().equals("N/A")) {
/*  761 */                       ALBcom = "n/a";
/*      */                     }
/*  763 */                     else if (task.getScheduledTaskStatus().equals("Done")) {
/*  764 */                       ALBcom = "Done";
/*      */                     } else {
/*  766 */                       ALBcom = completionDate;
/*      */                     }
/*      */                   
/*  769 */                   } else if (taskAbbrev.equalsIgnoreCase("PIH")) {
/*      */                     
/*  771 */                     PIH = dueDate;
/*  772 */                     if (task.getScheduledTaskStatus().equals("N/A")) {
/*  773 */                       PIHcom = "n/a";
/*      */                     }
/*  775 */                     else if (task.getScheduledTaskStatus().equals("Done")) {
/*  776 */                       PIHcom = "Done";
/*      */                     } else {
/*  778 */                       PIHcom = completionDate;
/*      */                     }
/*      */                   
/*  781 */                   } else if (taskAbbrev.equalsIgnoreCase("CRDA")) {
/*      */                     
/*  783 */                     CRDA = dueDate;
/*  784 */                     if (task.getScheduledTaskStatus().equals("N/A")) {
/*  785 */                       CRDAcom = "n/a";
/*      */                     }
/*  787 */                     else if (task.getScheduledTaskStatus().equals("Done")) {
/*  788 */                       CRDAcom = "Done";
/*      */                     } else {
/*  790 */                       CRDAcom = completionDate;
/*      */                     }
/*      */                   
/*  793 */                   } else if (taskAbbrev.equalsIgnoreCase("CDRP")) {
/*      */                     
/*  795 */                     CDRP = dueDate;
/*  796 */                     if (task.getScheduledTaskStatus().equals("N/A")) {
/*  797 */                       CDRPcom = "n/a";
/*      */                     }
/*  799 */                     else if (task.getScheduledTaskStatus().equals("Done")) {
/*  800 */                       CDRPcom = "Done";
/*      */                     } else {
/*  802 */                       CDRPcom = completionDate;
/*      */                     }
/*      */                   
/*  805 */                   } else if (taskAbbrev.equalsIgnoreCase("CNT")) {
/*      */                     
/*  807 */                     CNT = dueDate;
/*  808 */                     if (task.getScheduledTaskStatus().equals("N/A")) {
/*  809 */                       CNTcom = "n/a";
/*      */                     }
/*  811 */                     else if (task.getScheduledTaskStatus().equals("Done")) {
/*  812 */                       CNTcom = "Done";
/*      */                     } else {
/*  814 */                       CNTcom = completionDate;
/*      */                     }
/*      */                   
/*  817 */                   } else if (taskAbbrev.equalsIgnoreCase("MKB")) {
/*      */                     
/*  819 */                     MKB = dueDate;
/*  820 */                     if (task.getScheduledTaskStatus().equals("N/A")) {
/*  821 */                       MKBcom = "n/a";
/*      */                     }
/*  823 */                     else if (task.getScheduledTaskStatus().equals("Done")) {
/*  824 */                       MKBcom = "Done";
/*      */                     } else {
/*  826 */                       MKBcom = completionDate;
/*      */                     }
/*      */                   
/*  829 */                   } else if (taskAbbrev.equalsIgnoreCase("IMP")) {
/*      */                     
/*  831 */                     IMP = dueDate;
/*  832 */                     if (task.getScheduledTaskStatus().equals("N/A")) {
/*  833 */                       IMPcom = "n/a";
/*      */                     }
/*  835 */                     else if (task.getScheduledTaskStatus().equals("Done")) {
/*  836 */                       IMPcom = "Done";
/*      */                     } else {
/*  838 */                       IMPcom = completionDate;
/*      */                     }
/*      */                   
/*  841 */                   } else if (taskAbbrev.equalsIgnoreCase("ADIH")) {
/*      */                     
/*  843 */                     ADIH = dueDate;
/*  844 */                     if (task.getScheduledTaskStatus().equals("N/A")) {
/*  845 */                       ADIHcom = "n/a";
/*      */                     }
/*  847 */                     else if (task.getScheduledTaskStatus().equals("Done")) {
/*  848 */                       ADIHcom = "Done";
/*      */                     } else {
/*  850 */                       ADIHcom = completionDate;
/*      */                     }
/*      */                   
/*  853 */                   } else if (taskAbbrev.equalsIgnoreCase("MBR")) {
/*      */                     
/*  855 */                     MBR = dueDate;
/*  856 */                     if (task.getScheduledTaskStatus().equals("N/A")) {
/*  857 */                       MBRcom = "n/a";
/*      */                     }
/*  859 */                     else if (task.getScheduledTaskStatus().equals("Done")) {
/*  860 */                       MBRcom = "Done";
/*      */                     } else {
/*  862 */                       MBRcom = completionDate;
/*      */                     }
/*      */                   
/*  865 */                   } else if (taskAbbrev.equalsIgnoreCase("SGL")) {
/*      */                     
/*  867 */                     SGL = dueDate;
/*  868 */                     if (task.getScheduledTaskStatus().equals("N/A")) {
/*  869 */                       SGLcom = "n/a";
/*      */                     }
/*  871 */                     else if (task.getScheduledTaskStatus().equals("Done")) {
/*  872 */                       SGLcom = "Done";
/*      */                     } else {
/*  874 */                       SGLcom = completionDate;
/*      */                     }
/*      */                   
/*  877 */                   } else if (taskAbbrev.equalsIgnoreCase("MKP")) {
/*      */                     
/*  879 */                     MKP = dueDate;
/*  880 */                     if (task.getScheduledTaskStatus().equals("N/A")) {
/*  881 */                       MKPcom = "n/a";
/*      */                     }
/*  883 */                     else if (task.getScheduledTaskStatus().equals("Done")) {
/*  884 */                       MKPcom = "Done";
/*      */                     } else {
/*  886 */                       MKPcom = completionDate;
/*      */                     }
/*      */                   
/*  889 */                   } else if (taskAbbrev.equalsIgnoreCase("STD")) {
/*      */                     
/*  891 */                     STD = dueDate;
/*  892 */                     if (task.getScheduledTaskStatus().equals("N/A")) {
/*  893 */                       STDcom = "n/a";
/*      */                     }
/*  895 */                     else if (task.getScheduledTaskStatus().equals("Done")) {
/*  896 */                       STDcom = "Done";
/*      */                     } else {
/*  898 */                       STDcom = completionDate;
/*      */                     }
/*      */                   
/*  901 */                   } else if (taskAbbrev.equalsIgnoreCase("SEPS")) {
/*      */                     
/*  903 */                     SEPS = dueDate;
/*  904 */                     if (task.getScheduledTaskStatus().equals("N/A")) {
/*  905 */                       SEPScom = "n/a";
/*      */                     }
/*  907 */                     else if (task.getScheduledTaskStatus().equals("Done")) {
/*  908 */                       SEPScom = "Done";
/*      */                     } else {
/*  910 */                       SEPScom = completionDate;
/*      */                     }
/*      */                   
/*  913 */                   } else if (taskAbbrev.equalsIgnoreCase("PFS")) {
/*      */                     
/*  915 */                     PFS = dueDate;
/*  916 */                     if (task.getScheduledTaskStatus().equals("N/A")) {
/*  917 */                       PFScom = "n/a";
/*      */                     }
/*  919 */                     else if (task.getScheduledTaskStatus().equals("Done")) {
/*  920 */                       PFScom = "Done";
/*      */                     } else {
/*  922 */                       PFScom = completionDate;
/*      */                     } 
/*      */                   } 
/*      */                   
/*  926 */                   task = null;
/*      */                 } 
/*      */               } 
/*      */             }
/*      */ 
/*      */ 
/*      */             
/*  933 */             nextRow = 0;
/*  934 */             subTable = new DefaultTableLens(2, 24);
/*      */ 
/*      */             
/*  937 */             subTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
/*      */             
/*  939 */             subTable.setHeaderRowCount(0);
/*  940 */             subTable.setColBorderColor(Color.lightGray);
/*  941 */             subTable.setColWidth(0, 100);
/*  942 */             subTable.setColWidth(1, 100);
/*  943 */             subTable.setColWidth(2, 130);
/*  944 */             subTable.setColWidth(3, 130);
/*  945 */             subTable.setColWidth(4, 130);
/*  946 */             subTable.setColWidth(5, 80);
/*  947 */             subTable.setColWidth(6, 90);
/*  948 */             subTable.setColWidth(7, 80);
/*  949 */             subTable.setColWidth(8, 100);
/*  950 */             subTable.setColWidth(9, 70);
/*  951 */             subTable.setColWidth(10, 100);
/*  952 */             subTable.setColWidth(11, 60);
/*  953 */             subTable.setColWidth(12, 65);
/*  954 */             subTable.setColWidth(13, 80);
/*  955 */             subTable.setColWidth(14, 80);
/*  956 */             subTable.setColWidth(15, 60);
/*  957 */             subTable.setColWidth(16, 70);
/*  958 */             subTable.setColWidth(17, 80);
/*  959 */             subTable.setColWidth(18, 70);
/*  960 */             subTable.setColWidth(19, 70);
/*  961 */             subTable.setColWidth(20, 75);
/*  962 */             subTable.setColWidth(21, 60);
/*  963 */             subTable.setColWidth(22, 100);
/*  964 */             subTable.setColWidth(23, 60);
/*  965 */             subTable.setRowBorderColor(nextRow - 1, Color.lightGray);
/*      */             
/*  967 */             subTable.setBackground(nextRow, 0, Color.white);
/*  968 */             subTable.setAlignment(nextRow, 0, 9);
/*  969 */             subTable.setFont(nextRow, 0, new Font("Arial", 1, 6));
/*  970 */             subTable.setObject(nextRow, 0, artist);
/*      */             
/*  972 */             subTable.setFont(nextRow + 1, 0, new Font("Arial", 0, 6));
/*  973 */             subTable.setAlignment(nextRow + 1, 0, 9);
/*  974 */             subTable.setObject(nextRow + 1, 0, title);
/*      */             
/*  976 */             subTable.setFont(nextRow, 1, new Font("Arial", 1, 7));
/*  977 */             subTable.setFont(nextRow, 2, new Font("Arial", 0, 7));
/*  978 */             subTable.setFont(nextRow, 3, new Font("Arial", 0, 7));
/*  979 */             subTable.setFont(nextRow, 4, new Font("Arial", 0, 7));
/*      */             
/*  981 */             subTable.setBackground(nextRow, 1, Color.white);
/*  982 */             subTable.setObject(nextRow, 1, dateNameText);
/*  983 */             subTable.setAlignment(nextRow, 2, 10);
/*      */ 
/*      */             
/*  986 */             String[] check2a = { artist };
/*  987 */             int[] check2 = { 35 };
/*  988 */             String[] check3a = { title };
/*  989 */             int[] check3 = { 35 };
/*  990 */             int extraLines = MilestoneHelper.lineCountWCR(check2a, check2) + MilestoneHelper.lineCountWCR(check3a, check3);
/*      */             
/*  992 */             if (extraLines == 10) {
/*  993 */               extraLines = 9;
/*      */             }
/*  995 */             else if (extraLines > 10) {
/*  996 */               extraLines = 7;
/*      */             } 
/*      */             
/*  999 */             for (int z = 0; z < extraLines; z++) {
/* 1000 */               filler = String.valueOf(filler) + "\n";
/*      */             }
/*      */ 
/*      */             
/* 1004 */             Vector multContacts = sel.getMultOtherContacts();
/* 1005 */             int numberOfContacts = multContacts.size();
/* 1006 */             String marketingDirector = "";
/* 1007 */             String artDirector = "";
/* 1008 */             String AandRContact = "";
/*      */             
/* 1010 */             switch (numberOfContacts) {
/*      */               case 1:
/* 1012 */                 marketingDirector = ((MultOtherContact)multContacts.get(0)).getName();
/*      */                 break;
/*      */               case 2:
/* 1015 */                 marketingDirector = ((MultOtherContact)multContacts.get(0)).getName();
/* 1016 */                 artDirector = ((MultOtherContact)multContacts.get(1)).getName();
/*      */                 break;
/*      */               case 3:
/* 1019 */                 marketingDirector = ((MultOtherContact)multContacts.get(0)).getName();
/* 1020 */                 artDirector = ((MultOtherContact)multContacts.get(1)).getName();
/* 1021 */                 AandRContact = ((MultOtherContact)multContacts.get(2)).getName();
/*      */                 break;
/*      */             } 
/*      */ 
/*      */ 
/*      */             
/* 1027 */             subTable.setObject(nextRow, 2, marketingDirector);
/* 1028 */             subTable.setObject(nextRow, 3, artDirector);
/* 1029 */             subTable.setObject(nextRow, 4, AandRContact);
/*      */             
/* 1031 */             subTable.setAlignment(nextRow, 1, 10);
/* 1032 */             subTable.setAlignment(nextRow, 2, 9);
/* 1033 */             subTable.setBackground(nextRow, 2, Color.white);
/* 1034 */             subTable.setAlignment(nextRow, 3, 9);
/* 1035 */             subTable.setBackground(nextRow, 3, Color.white);
/* 1036 */             subTable.setAlignment(nextRow, 4, 9);
/* 1037 */             subTable.setBackground(nextRow, 4, Color.white);
/*      */             
/* 1039 */             subTable.setRowBorderColor(nextRow, SHADED_AREA_COLOR);
/*      */             
/* 1041 */             subTable.setRowBorderColor(nextRow, 0, Color.white);
/* 1042 */             subTable.setRowBorderColor(nextRow, 1, Color.white);
/* 1043 */             subTable.setRowBorderColor(nextRow, 2, Color.white);
/* 1044 */             subTable.setRowBorderColor(nextRow, 3, Color.white);
/* 1045 */             subTable.setRowBorderColor(nextRow, 4, Color.white);
/*      */             
/* 1047 */             subTable.setRowBorderColor(nextRow + 1, SHADED_AREA_COLOR);
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1052 */             subTable.setColBorderColor(nextRow, 3, SHADED_AREA_COLOR);
/* 1053 */             subTable.setColBorderColor(nextRow, 4, SHADED_AREA_COLOR);
/*      */             
/* 1055 */             subTable.setObject(nextRow, 5, IMGcom);
/* 1056 */             subTable.setColBorderColor(nextRow, 5, SHADED_AREA_COLOR);
/*      */             
/* 1058 */             subTable.setColBorder(nextRow, 5, 266240);
/*      */             
/* 1060 */             subTable.setObject(nextRow, 6, PHOcom);
/* 1061 */             subTable.setColBorderColor(nextRow, 6, SHADED_AREA_COLOR);
/*      */             
/* 1063 */             subTable.setColBorder(nextRow, 6, 266240);
/*      */             
/* 1065 */             subTable.setObject(nextRow, 7, SLTcom);
/* 1066 */             subTable.setColBorderColor(nextRow, 7, SHADED_AREA_COLOR);
/*      */             
/* 1068 */             subTable.setColBorder(nextRow, 7, 266240);
/*      */             
/* 1070 */             subTable.setObject(nextRow, 8, PROcom);
/* 1071 */             subTable.setColBorderColor(nextRow, 8, SHADED_AREA_COLOR);
/*      */             
/* 1073 */             subTable.setColBorder(nextRow, 8, 266240);
/*      */             
/* 1075 */             subTable.setObject(nextRow, 9, PLNcom);
/* 1076 */             subTable.setColBorderColor(nextRow, 9, SHADED_AREA_COLOR);
/*      */             
/* 1078 */             subTable.setColBorder(nextRow, 9, 266240);
/*      */             
/* 1080 */             subTable.setObject(nextRow, 10, ALBcom);
/* 1081 */             subTable.setColBorderColor(nextRow, 10, SHADED_AREA_COLOR);
/*      */             
/* 1083 */             subTable.setColBorder(nextRow, 10, 266240);
/*      */             
/* 1085 */             subTable.setObject(nextRow, 11, PIHcom);
/* 1086 */             subTable.setColBorderColor(nextRow, 11, SHADED_AREA_COLOR);
/*      */             
/* 1088 */             subTable.setColBorder(nextRow, 11, 266240);
/*      */             
/* 1090 */             subTable.setObject(nextRow, 12, CDRPcom);
/* 1091 */             subTable.setColBorderColor(nextRow, 12, SHADED_AREA_COLOR);
/* 1092 */             subTable.setColBorder(nextRow, 12, 266240);
/*      */             
/* 1094 */             subTable.setObject(nextRow, 13, CNTcom);
/* 1095 */             subTable.setColBorderColor(nextRow, 13, SHADED_AREA_COLOR);
/* 1096 */             subTable.setColBorder(nextRow, 13, 266240);
/*      */             
/* 1098 */             subTable.setObject(nextRow, 14, MKBcom);
/* 1099 */             subTable.setColBorderColor(nextRow, 14, SHADED_AREA_COLOR);
/* 1100 */             subTable.setColBorder(nextRow, 14, 266240);
/*      */             
/* 1102 */             subTable.setObject(nextRow, 15, CRDAcom);
/* 1103 */             subTable.setColBorderColor(nextRow, 15, SHADED_AREA_COLOR);
/* 1104 */             subTable.setColBorder(nextRow, 15, 266240);
/*      */             
/* 1106 */             subTable.setObject(nextRow, 16, IMPcom);
/* 1107 */             subTable.setColBorderColor(nextRow, 16, SHADED_AREA_COLOR);
/* 1108 */             subTable.setColBorder(nextRow, 16, 266240);
/*      */             
/* 1110 */             subTable.setObject(nextRow, 17, ADIHcom);
/* 1111 */             subTable.setColBorderColor(nextRow, 17, SHADED_AREA_COLOR);
/* 1112 */             subTable.setColBorder(nextRow, 17, 266240);
/*      */             
/* 1114 */             subTable.setObject(nextRow, 18, MBRcom);
/* 1115 */             subTable.setColBorderColor(nextRow, 18, SHADED_AREA_COLOR);
/* 1116 */             subTable.setColBorder(nextRow, 18, 266240);
/*      */             
/* 1118 */             subTable.setObject(nextRow, 19, SGLcom);
/* 1119 */             subTable.setColBorderColor(nextRow, 19, SHADED_AREA_COLOR);
/* 1120 */             subTable.setColBorder(nextRow, 19, 266240);
/*      */             
/* 1122 */             subTable.setObject(nextRow, 20, MKPcom);
/* 1123 */             subTable.setColBorderColor(nextRow, 20, SHADED_AREA_COLOR);
/* 1124 */             subTable.setColBorder(nextRow, 20, 266240);
/*      */             
/* 1126 */             subTable.setObject(nextRow, 21, STDcom);
/* 1127 */             subTable.setColBorderColor(nextRow, 21, SHADED_AREA_COLOR);
/* 1128 */             subTable.setColBorder(nextRow, 21, 266240);
/*      */             
/* 1130 */             subTable.setObject(nextRow, 22, SEPScom);
/* 1131 */             subTable.setColBorderColor(nextRow, 22, SHADED_AREA_COLOR);
/* 1132 */             subTable.setColBorder(nextRow, 22, 266240);
/*      */             
/* 1134 */             subTable.setObject(nextRow, 23, PFScom);
/* 1135 */             subTable.setColBorderColor(nextRow, 23, SHADED_AREA_COLOR);
/* 1136 */             subTable.setColBorder(nextRow, 23, 266240);
/*      */             
/* 1138 */             subTable.setFont(nextRow, 3, new Font("Arial", 0, 7));
/* 1139 */             subTable.setFont(nextRow, 4, new Font("Arial", 0, 7));
/* 1140 */             subTable.setFont(nextRow, 5, new Font("Arial", 1, 7));
/* 1141 */             subTable.setFont(nextRow, 6, new Font("Arial", 1, 7));
/* 1142 */             subTable.setFont(nextRow, 7, new Font("Arial", 1, 7));
/* 1143 */             subTable.setFont(nextRow, 8, new Font("Arial", 1, 7));
/* 1144 */             subTable.setFont(nextRow, 9, new Font("Arial", 1, 7));
/* 1145 */             subTable.setFont(nextRow, 10, new Font("Arial", 1, 7));
/* 1146 */             subTable.setFont(nextRow, 11, new Font("Arial", 1, 7));
/* 1147 */             subTable.setFont(nextRow, 12, new Font("Arial", 1, 7));
/* 1148 */             subTable.setFont(nextRow, 13, new Font("Arial", 1, 7));
/* 1149 */             subTable.setFont(nextRow, 14, new Font("Arial", 1, 7));
/* 1150 */             subTable.setFont(nextRow, 15, new Font("Arial", 1, 7));
/* 1151 */             subTable.setFont(nextRow, 16, new Font("Arial", 1, 7));
/* 1152 */             subTable.setFont(nextRow, 17, new Font("Arial", 1, 7));
/* 1153 */             subTable.setFont(nextRow, 18, new Font("Arial", 1, 7));
/* 1154 */             subTable.setFont(nextRow, 19, new Font("Arial", 1, 7));
/* 1155 */             subTable.setFont(nextRow, 20, new Font("Arial", 1, 7));
/* 1156 */             subTable.setFont(nextRow, 21, new Font("Arial", 1, 7));
/* 1157 */             subTable.setFont(nextRow, 22, new Font("Arial", 1, 7));
/* 1158 */             subTable.setFont(nextRow, 23, new Font("Arial", 1, 7));
/*      */             
/* 1160 */             subTable.setAlignment(nextRow, 4, 2);
/* 1161 */             subTable.setAlignment(nextRow, 5, 2);
/* 1162 */             subTable.setAlignment(nextRow, 6, 2);
/* 1163 */             subTable.setAlignment(nextRow, 7, 2);
/* 1164 */             subTable.setAlignment(nextRow, 8, 2);
/* 1165 */             subTable.setAlignment(nextRow, 9, 2);
/* 1166 */             subTable.setAlignment(nextRow, 10, 2);
/* 1167 */             subTable.setAlignment(nextRow, 11, 2);
/* 1168 */             subTable.setAlignment(nextRow, 12, 2);
/* 1169 */             subTable.setAlignment(nextRow, 13, 2);
/* 1170 */             subTable.setAlignment(nextRow, 14, 2);
/* 1171 */             subTable.setAlignment(nextRow, 15, 2);
/* 1172 */             subTable.setAlignment(nextRow, 16, 2);
/* 1173 */             subTable.setAlignment(nextRow, 17, 2);
/* 1174 */             subTable.setAlignment(nextRow, 18, 2);
/* 1175 */             subTable.setAlignment(nextRow, 19, 2);
/* 1176 */             subTable.setAlignment(nextRow, 20, 2);
/* 1177 */             subTable.setAlignment(nextRow, 21, 2);
/* 1178 */             subTable.setAlignment(nextRow, 22, 2);
/* 1179 */             subTable.setAlignment(nextRow, 23, 2);
/*      */ 
/*      */             
/* 1182 */             for (int k = 5; k < 24; k++) {
/* 1183 */               subTable.setBackground(nextRow, k, SHADED_AREA_COLOR);
/*      */             }
/* 1185 */             subTable.setColBorderColor(nextRow + 1, 4, SHADED_AREA_COLOR);
/* 1186 */             subTable.setObject(nextRow + 1, 5, IMG);
/* 1187 */             subTable.setBackground(nextRow + 1, 5, Color.white);
/* 1188 */             subTable.setColBorderColor(nextRow + 1, 5, SHADED_AREA_COLOR);
/* 1189 */             subTable.setObject(nextRow + 1, 6, PHO);
/* 1190 */             subTable.setBackground(nextRow + 1, 6, Color.white);
/* 1191 */             subTable.setColBorderColor(nextRow + 1, 6, SHADED_AREA_COLOR);
/* 1192 */             subTable.setObject(nextRow + 1, 7, SLT);
/* 1193 */             subTable.setBackground(nextRow + 1, 7, Color.white);
/* 1194 */             subTable.setColBorderColor(nextRow + 1, 7, SHADED_AREA_COLOR);
/* 1195 */             subTable.setObject(nextRow + 1, 8, PRO);
/* 1196 */             subTable.setBackground(nextRow + 1, 8, Color.white);
/* 1197 */             subTable.setColBorderColor(nextRow + 1, 8, SHADED_AREA_COLOR);
/* 1198 */             subTable.setObject(nextRow + 1, 9, PLN);
/* 1199 */             subTable.setBackground(nextRow + 1, 9, Color.white);
/* 1200 */             subTable.setColBorderColor(nextRow + 1, 9, SHADED_AREA_COLOR);
/* 1201 */             subTable.setObject(nextRow + 1, 10, ALB);
/* 1202 */             subTable.setBackground(nextRow + 1, 10, Color.white);
/* 1203 */             subTable.setColBorderColor(nextRow + 1, 10, SHADED_AREA_COLOR);
/* 1204 */             subTable.setObject(nextRow + 1, 11, PIH);
/* 1205 */             subTable.setBackground(nextRow + 1, 11, Color.white);
/* 1206 */             subTable.setColBorderColor(nextRow + 1, 11, SHADED_AREA_COLOR);
/* 1207 */             subTable.setObject(nextRow + 1, 12, CDRP);
/* 1208 */             subTable.setBackground(nextRow + 1, 12, Color.white);
/* 1209 */             subTable.setColBorderColor(nextRow + 1, 12, SHADED_AREA_COLOR);
/* 1210 */             subTable.setObject(nextRow + 1, 13, CNT);
/* 1211 */             subTable.setBackground(nextRow + 1, 13, Color.white);
/* 1212 */             subTable.setColBorderColor(nextRow + 1, 13, SHADED_AREA_COLOR);
/* 1213 */             subTable.setObject(nextRow + 1, 14, MKB);
/* 1214 */             subTable.setBackground(nextRow + 1, 14, Color.white);
/* 1215 */             subTable.setColBorderColor(nextRow + 1, 14, SHADED_AREA_COLOR);
/* 1216 */             subTable.setObject(nextRow + 1, 15, CRDA);
/* 1217 */             subTable.setBackground(nextRow + 1, 15, Color.white);
/* 1218 */             subTable.setColBorderColor(nextRow + 1, 15, SHADED_AREA_COLOR);
/* 1219 */             subTable.setObject(nextRow + 1, 16, IMP);
/* 1220 */             subTable.setBackground(nextRow + 1, 16, Color.white);
/* 1221 */             subTable.setColBorderColor(nextRow + 1, 16, SHADED_AREA_COLOR);
/* 1222 */             subTable.setObject(nextRow + 1, 17, ADIH);
/* 1223 */             subTable.setBackground(nextRow + 1, 17, Color.white);
/* 1224 */             subTable.setColBorderColor(nextRow + 1, 17, SHADED_AREA_COLOR);
/* 1225 */             subTable.setObject(nextRow + 1, 18, MBR);
/* 1226 */             subTable.setBackground(nextRow + 1, 18, Color.white);
/* 1227 */             subTable.setColBorderColor(nextRow + 1, 18, SHADED_AREA_COLOR);
/* 1228 */             subTable.setObject(nextRow + 1, 19, SGL);
/* 1229 */             subTable.setBackground(nextRow + 1, 19, Color.white);
/* 1230 */             subTable.setColBorderColor(nextRow + 1, 19, SHADED_AREA_COLOR);
/* 1231 */             subTable.setObject(nextRow + 1, 20, MKP);
/* 1232 */             subTable.setBackground(nextRow + 1, 20, Color.white);
/* 1233 */             subTable.setColBorderColor(nextRow + 1, 20, SHADED_AREA_COLOR);
/* 1234 */             subTable.setObject(nextRow + 1, 21, STD);
/* 1235 */             subTable.setBackground(nextRow + 1, 21, Color.white);
/* 1236 */             subTable.setColBorderColor(nextRow + 1, 21, SHADED_AREA_COLOR);
/* 1237 */             subTable.setObject(nextRow + 1, 22, SEPS);
/* 1238 */             subTable.setBackground(nextRow + 1, 22, Color.white);
/* 1239 */             subTable.setColBorderColor(nextRow + 1, 22, SHADED_AREA_COLOR);
/* 1240 */             subTable.setObject(nextRow + 1, 23, PFS);
/* 1241 */             subTable.setBackground(nextRow + 1, 23, Color.white);
/* 1242 */             subTable.setColBorderColor(nextRow + 1, 23, SHADED_AREA_COLOR);
/*      */             
/* 1244 */             subTable.setAlignment(nextRow + 1, 3, 10);
/* 1245 */             subTable.setAlignment(nextRow + 1, 4, 10);
/* 1246 */             subTable.setAlignment(nextRow + 1, 5, 10);
/* 1247 */             subTable.setAlignment(nextRow + 1, 6, 10);
/* 1248 */             subTable.setAlignment(nextRow + 1, 7, 10);
/* 1249 */             subTable.setAlignment(nextRow + 1, 8, 10);
/* 1250 */             subTable.setAlignment(nextRow + 1, 9, 10);
/* 1251 */             subTable.setAlignment(nextRow + 1, 10, 10);
/* 1252 */             subTable.setAlignment(nextRow + 1, 11, 10);
/* 1253 */             subTable.setAlignment(nextRow + 1, 12, 10);
/* 1254 */             subTable.setAlignment(nextRow + 1, 13, 10);
/* 1255 */             subTable.setAlignment(nextRow + 1, 14, 10);
/* 1256 */             subTable.setAlignment(nextRow + 1, 15, 10);
/* 1257 */             subTable.setAlignment(nextRow + 1, 16, 10);
/* 1258 */             subTable.setAlignment(nextRow + 1, 17, 10);
/* 1259 */             subTable.setAlignment(nextRow + 1, 18, 10);
/* 1260 */             subTable.setAlignment(nextRow + 1, 19, 10);
/* 1261 */             subTable.setAlignment(nextRow + 1, 20, 10);
/* 1262 */             subTable.setAlignment(nextRow + 1, 21, 10);
/* 1263 */             subTable.setAlignment(nextRow + 1, 22, 10);
/* 1264 */             subTable.setAlignment(nextRow + 1, 23, 10);
/*      */ 
/*      */             
/* 1267 */             subTable.setRowFont(nextRow + 1, new Font("Arial", 0, 7));
/*      */             
/* 1269 */             subTable.setRowForeground(nextRow, Color.black);
/*      */ 
/*      */             
/* 1272 */             subTable.setRowFont(nextRow, new Font("Arial", 0, 7));
/*      */             
/* 1274 */             body = new SectionBand(report);
/*      */             
/* 1276 */             double lfLineCount = 1.5D;
/*      */             
/* 1278 */             body.setHeight(3.0F);
/*      */             
/* 1280 */             body.addTable(subTable, new Rectangle(800, 800));
/* 1281 */             body.setBottomBorder(0);
/* 1282 */             body.setTopBorder(0);
/* 1283 */             body.setShrinkToFit(true);
/* 1284 */             body.setVisible(true);
/*      */             
/* 1286 */             if (newCycle) {
/* 1287 */               group = new DefaultSectionLens(null, group, spacer);
/*      */             }
/*      */             
/* 1290 */             group = new DefaultSectionLens(null, group, body);
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/* 1295 */         group = new DefaultSectionLens(hbandType, group, null);
/* 1296 */         report.addSection(group, rowCountTable);
/* 1297 */         report.addPageBreak();
/* 1298 */         group = null;
/*      */       
/*      */       }
/*      */ 
/*      */     
/*      */     }
/* 1304 */     catch (Exception e) {
/*      */       
/* 1306 */       System.out.println(">>>>>>>>McaProductionScheduleForPrintSubHandler(): exception: " + e);
/*      */     } 
/*      */     
/* 1309 */     System.out.println("done");
/*      */   }
/*      */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\MCANewReleasePlanningSchedulePrintSubHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */