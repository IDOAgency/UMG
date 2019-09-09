/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.ComponentLog;
/*     */ import com.techempower.gemini.Context;
/*     */ import com.techempower.gemini.GeminiApplication;
/*     */ import com.universal.milestone.Form;
/*     */ import com.universal.milestone.IntegerComparator;
/*     */ import com.universal.milestone.MilestoneHelper;
/*     */ import com.universal.milestone.NashProdScheduleForPrintSubHandler;
/*     */ import com.universal.milestone.Schedule;
/*     */ import com.universal.milestone.ScheduledTask;
/*     */ import com.universal.milestone.SecureHandler;
/*     */ import com.universal.milestone.Selection;
/*     */ import com.universal.milestone.SelectionArtistComparator;
/*     */ import com.universal.milestone.SelectionManager;
/*     */ import com.universal.milestone.SelectionSelectionNumberComparator;
/*     */ import com.universal.milestone.SelectionTitleComparator;
/*     */ import com.universal.milestone.StringComparator;
/*     */ import com.universal.milestone.StringDateComparator;
/*     */ import inetsoft.report.SectionBand;
/*     */ import inetsoft.report.XStyleSheet;
/*     */ import inetsoft.report.lens.DefaultSectionLens;
/*     */ import inetsoft.report.lens.DefaultTableLens;
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Arrays;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NashProdScheduleForPrintSubHandler
/*     */   extends SecureHandler
/*     */ {
/*     */   public static final String COMPONENT_CODE = "hNashProd";
/*     */   public GeminiApplication application;
/*     */   public ComponentLog log;
/*     */   
/*     */   public NashProdScheduleForPrintSubHandler(GeminiApplication application) {
/*  67 */     this.application = application;
/*  68 */     this.log = application.getLog("hNashProd");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  76 */   public String getDescription() { return "Sub Report"; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void fillNashProdScheduleForPrint2(XStyleSheet report, Context context) {
/*  88 */     Color SHADED_AREA_COLOR = Color.lightGray;
/*  89 */     int COL_LINE_STYLE = 4097;
/*  90 */     int HEADER_FONT_SIZE = 12;
/*  91 */     int NUM_COLUMNS = 12;
/*     */     
/*  93 */     double ldLineVal = 0.3D;
/*     */     
/*  95 */     SectionBand hbandType = new SectionBand(report);
/*  96 */     SectionBand hbandCategory = new SectionBand(report);
/*  97 */     SectionBand hbandDate = new SectionBand(report);
/*  98 */     SectionBand body = new SectionBand(report);
/*  99 */     SectionBand footer = new SectionBand(report);
/* 100 */     SectionBand spacer = new SectionBand(report);
/* 101 */     DefaultSectionLens group = null;
/*     */     
/* 103 */     footer.setVisible(true);
/* 104 */     footer.setHeight(0.1F);
/* 105 */     footer.setShrinkToFit(false);
/* 106 */     footer.setBottomBorder(0);
/*     */     
/* 108 */     spacer.setVisible(true);
/* 109 */     spacer.setHeight(0.05F);
/* 110 */     spacer.setShrinkToFit(false);
/* 111 */     spacer.setBottomBorder(0);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 116 */       HttpServletResponse sresponse = context.getResponse();
/* 117 */       context.putDelivery("status", new String("start_gathering"));
/* 118 */       context.includeJSP("status.jsp", "hiddenFrame");
/* 119 */       sresponse.setContentType("text/plain");
/* 120 */       sresponse.flushBuffer();
/*     */     }
/* 122 */     catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 127 */     Vector selections = MilestoneHelper.getSelectionsForReport(context);
/*     */ 
/*     */     
/*     */     try {
/* 131 */       HttpServletResponse sresponse = context.getResponse();
/* 132 */       context.putDelivery("status", new String("start_report"));
/* 133 */       context.putDelivery("percent", new String("10"));
/* 134 */       context.includeJSP("status.jsp", "hiddenFrame");
/* 135 */       sresponse.setContentType("text/plain");
/* 136 */       sresponse.flushBuffer();
/*     */     }
/* 138 */     catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 143 */     int DATA_FONT_SIZE = 7;
/* 144 */     int SUB_HEADER_FONT_SIZE = 8;
/* 145 */     int ROW_LINE_STYLE = 4097;
/* 146 */     Color COL_ROW_LINE_COLOR = new Color(208, 206, 206, 0);
/* 147 */     Color SHADED_ROW_COLOR = new Color(208, 206, 206, 0);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 152 */     Hashtable selTable = MilestoneHelper.groupSelectionsByCompanyAndSubconfigAndStreetDate(selections);
/* 153 */     Enumeration companies = selTable.keys();
/* 154 */     Vector companyVector = new Vector();
/*     */     
/* 156 */     while (companies.hasMoreElements()) {
/* 157 */       companyVector.addElement(companies.nextElement());
/*     */     }
/* 159 */     int numCompanies = companyVector.size();
/*     */ 
/*     */     
/*     */     try {
/* 163 */       DefaultTableLens table_contents = null;
/* 164 */       DefaultTableLens rowCountTable = null;
/* 165 */       DefaultTableLens columnHeaderTable = null;
/* 166 */       DefaultTableLens subTable = null;
/* 167 */       DefaultTableLens monthTableLens = null;
/* 168 */       DefaultTableLens dateTableLens = null;
/* 169 */       DefaultTableLens subconfigTableLens = null;
/*     */ 
/*     */       
/* 172 */       rowCountTable = new DefaultTableLens(2, 10000);
/*     */       
/* 174 */       Object[] companyArray = companyVector.toArray();
/* 175 */       Arrays.sort(companyArray, new StringComparator());
/*     */       
/* 177 */       for (int n = 0; n < companyArray.length; n++) {
/*     */         
/* 179 */         String company = (String)companyArray[n];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 186 */         Form reportForm = (Form)context.getSessionValue("reportForm");
/*     */         
/* 188 */         Calendar beginStDate = (reportForm.getStringValue("beginDate") != null && 
/* 189 */           reportForm.getStringValue("beginDate").length() > 0) ? 
/* 190 */           MilestoneHelper.getDate(reportForm.getStringValue("beginDate")) : null;
/*     */         
/* 192 */         Calendar endStDate = (reportForm.getStringValue("endDate") != null && 
/* 193 */           reportForm.getStringValue("endDate").length() > 0) ? 
/* 194 */           MilestoneHelper.getDate(reportForm.getStringValue("endDate")) : null;
/*     */         
/* 196 */         String startDate = MilestoneHelper.getFormatedDate(beginStDate);
/* 197 */         String endDate = MilestoneHelper.getFormatedDate(endStDate);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 209 */         report.setElement("crs_startdate", startDate);
/* 210 */         report.setElement("crs_enddate", endDate);
/*     */         
/* 212 */         SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
/*     */         
/* 214 */         String todayLong = formatter.format(new Date());
/*     */         
/* 216 */         report.setElement("crs_bottomdate", todayLong);
/*     */ 
/*     */         
/* 219 */         int numSubconfigs = 0;
/* 220 */         int numMonths = 0;
/* 221 */         int numDates = 0;
/* 222 */         int numSelections = 0;
/* 223 */         int totalCount = 0;
/* 224 */         int tenth = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 230 */         Hashtable subconfigTable = (Hashtable)selTable.get(company);
/*     */         
/* 232 */         if (subconfigTable != null) {
/*     */           
/* 234 */           Enumeration subconfigs = subconfigTable.keys();
/*     */           
/* 236 */           while (subconfigs.hasMoreElements()) {
/*     */             
/* 238 */             totalCount++;
/* 239 */             numSubconfigs++;
/*     */             
/* 241 */             String subconfig = (String)subconfigs.nextElement();
/*     */             
/* 243 */             Hashtable monthTable = (Hashtable)subconfigTable.get(subconfig);
/* 244 */             if (monthTable != null) {
/*     */               
/* 246 */               Enumeration months = monthTable.keys();
/* 247 */               while (months.hasMoreElements()) {
/*     */                 
/* 249 */                 String monthName = (String)months.nextElement();
/*     */                 
/* 251 */                 numMonths++;
/* 252 */                 totalCount += monthTable.size();
/*     */                 
/* 254 */                 Hashtable dateTable = (Hashtable)monthTable.get(monthName);
/* 255 */                 if (dateTable != null) {
/*     */                   
/* 257 */                   Enumeration dates = dateTable.keys();
/* 258 */                   while (dates.hasMoreElements()) {
/*     */                     
/* 260 */                     String dateName = (String)dates.nextElement();
/*     */                     
/* 262 */                     numDates++;
/*     */                     
/* 264 */                     selections = (Vector)dateTable.get(dateName);
/* 265 */                     if (selections != null) {
/* 266 */                       numSelections += selections.size();
/*     */                     }
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 276 */         tenth = (totalCount > 10) ? (totalCount / 10) : 1;
/*     */         
/* 278 */         HttpServletResponse sresponse = context.getResponse();
/* 279 */         context.putDelivery("status", new String("start_report"));
/* 280 */         context.putDelivery("percent", new String("20"));
/* 281 */         context.includeJSP("status.jsp", "hiddenFrame");
/* 282 */         sresponse.setContentType("text/plain");
/* 283 */         sresponse.flushBuffer();
/*     */         
/* 285 */         int recordCount = 0;
/* 286 */         int count = 0;
/* 287 */         int numRows = 0;
/*     */ 
/*     */ 
/*     */         
/* 291 */         numRows += numSubconfigs * 4;
/* 292 */         numRows += numMonths * 2;
/* 293 */         numRows += numDates * 3;
/* 294 */         numRows += numSelections * 2;
/*     */         
/* 296 */         numRows += 3;
/*     */ 
/*     */ 
/*     */         
/* 300 */         table_contents = new DefaultTableLens(1, 9);
/*     */         
/* 302 */         String companyHeaderText = !company.trim().equals("") ? company : "Other";
/*     */         
/* 304 */         table_contents.setColWidth(0, 146);
/* 305 */         table_contents.setColWidth(1, 58);
/* 306 */         table_contents.setColWidth(2, 45);
/* 307 */         table_contents.setColWidth(3, 48);
/* 308 */         table_contents.setColWidth(4, 45);
/* 309 */         table_contents.setColWidth(5, 45);
/* 310 */         table_contents.setColWidth(6, 50);
/* 311 */         table_contents.setColWidth(7, 38);
/* 312 */         table_contents.setColWidth(8, 105);
/*     */ 
/*     */ 
/*     */         
/* 316 */         int nextRow = 0;
/*     */ 
/*     */         
/* 319 */         table_contents.setObject(nextRow, 0, companyHeaderText);
/* 320 */         table_contents.setSpan(nextRow, 0, new Dimension(9, 1));
/* 321 */         table_contents.setRowBackground(nextRow, Color.black);
/* 322 */         table_contents.setRowForeground(nextRow, Color.white);
/* 323 */         table_contents.setRowFont(nextRow, new Font("Arial", 1, 12));
/*     */         
/* 325 */         table_contents.setRowBorderColor(nextRow, Color.white);
/* 326 */         table_contents.setRowBorder(nextRow, 266240);
/*     */ 
/*     */ 
/*     */         
/* 330 */         if (subconfigTable != null) {
/*     */           
/* 332 */           Enumeration subconfigs = subconfigTable.keys();
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 337 */           Vector subconfigVector = new Vector();
/*     */           
/* 339 */           while (subconfigs.hasMoreElements()) {
/* 340 */             subconfigVector.add((String)subconfigs.nextElement());
/*     */           }
/* 342 */           Object[] subconfigsArray = subconfigVector.toArray();
/* 343 */           Arrays.sort(subconfigsArray, new StringComparator());
/*     */           
/* 345 */           for (int scIndex = 0; scIndex < subconfigsArray.length; scIndex++)
/*     */           {
/* 347 */             String subconfig = (String)subconfigsArray[scIndex];
/*     */             
/* 349 */             boolean isFullLength = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 355 */             if (subconfig.equalsIgnoreCase("full length")) {
/* 356 */               isFullLength = true;
/*     */             }
/* 358 */             nextRow = 0;
/*     */             
/* 360 */             subconfigTableLens = new DefaultTableLens(1, 9);
/*     */ 
/*     */             
/* 363 */             subconfigTableLens.setSpan(nextRow, 0, new Dimension(9, 1));
/* 364 */             subconfigTableLens.setAlignment(nextRow, 0, 2);
/* 365 */             subconfigTableLens.setObject(nextRow, 0, subconfig);
/* 366 */             subconfigTableLens.setRowHeight(nextRow, 15);
/*     */             
/* 368 */             subconfigTableLens.setRowBorderColor(nextRow, Color.black);
/* 369 */             subconfigTableLens.setRowBorder(nextRow, 0, 266240);
/*     */             
/* 371 */             subconfigTableLens.setRowFont(nextRow, new Font("Arial", 3, 12));
/* 372 */             subconfigTableLens.setRowBackground(nextRow, Color.white);
/* 373 */             subconfigTableLens.setRowForeground(nextRow, Color.black);
/*     */             
/* 375 */             subconfigTableLens.setRowBorder(nextRow - 1, 266240);
/* 376 */             subconfigTableLens.setColBorder(nextRow, -1, 266240);
/* 377 */             subconfigTableLens.setColBorder(nextRow, 0, 266240);
/* 378 */             subconfigTableLens.setColBorder(nextRow, 9, 266240);
/* 379 */             subconfigTableLens.setColBorder(nextRow, 8, 266240);
/* 380 */             subconfigTableLens.setRowBorder(nextRow, 266240);
/*     */             
/* 382 */             subconfigTableLens.setRowBorderColor(nextRow - 1, Color.black);
/* 383 */             subconfigTableLens.setColBorderColor(nextRow, -1, Color.black);
/* 384 */             subconfigTableLens.setColBorderColor(nextRow, 0, Color.black);
/* 385 */             subconfigTableLens.setColBorderColor(nextRow, 9, Color.black);
/* 386 */             subconfigTableLens.setColBorderColor(nextRow, 8, Color.black);
/* 387 */             subconfigTableLens.setRowBorderColor(nextRow, Color.black);
/*     */             
/* 389 */             hbandType = new SectionBand(report);
/* 390 */             hbandType.setHeight(0.7F);
/* 391 */             hbandType.setShrinkToFit(false);
/* 392 */             hbandType.setVisible(true);
/* 393 */             hbandType.addTable(table_contents, new Rectangle(0, 0, 800, 25));
/* 394 */             hbandType.addTable(subconfigTableLens, new Rectangle(0, 25, 800, 30));
/*     */             
/* 396 */             Hashtable monthTable = (Hashtable)subconfigTable.get(subconfig);
/*     */             
/* 398 */             if (monthTable != null) {
/*     */               
/* 400 */               Enumeration monthSort = monthTable.keys();
/*     */               
/* 402 */               Vector monthVector = new Vector();
/*     */ 
/*     */               
/* 405 */               while (monthSort.hasMoreElements()) {
/* 406 */                 monthVector.add((String)monthSort.nextElement());
/*     */               }
/* 408 */               Object[] monthArray = null;
/*     */               
/* 410 */               monthArray = monthVector.toArray();
/*     */               
/* 412 */               Arrays.sort(monthArray, new IntegerComparator());
/*     */               
/* 414 */               for (int mIndex = 0; mIndex < monthArray.length; mIndex++) {
/*     */                 
/* 416 */                 String monthName = (String)monthArray[mIndex];
/* 417 */                 String monthNameString = monthName;
/*     */ 
/*     */                 
/*     */                 try {
/* 421 */                   monthNameString = MONTHS[Integer.parseInt(monthName) - 1];
/*     */                 }
/* 423 */                 catch (Exception e) {
/*     */                   
/* 425 */                   if (monthName.equals("13")) {
/* 426 */                     monthNameString = "TBS";
/* 427 */                   } else if (monthName.equals("26")) {
/* 428 */                     monthNameString = "ITW";
/*     */                   } else {
/* 430 */                     monthNameString = "No street date";
/*     */                   } 
/*     */                 } 
/* 433 */                 monthTableLens = new DefaultTableLens(1, 9);
/* 434 */                 hbandCategory = new SectionBand(report);
/* 435 */                 hbandCategory.setHeight(0.25F);
/* 436 */                 hbandCategory.setShrinkToFit(false);
/* 437 */                 hbandCategory.setVisible(true);
/* 438 */                 hbandCategory.setBottomBorder(0);
/* 439 */                 hbandCategory.setLeftBorder(0);
/* 440 */                 hbandCategory.setRightBorder(0);
/* 441 */                 hbandCategory.setTopBorder(0);
/*     */                 
/* 443 */                 nextRow = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 448 */                 monthTableLens.setSpan(nextRow, 0, new Dimension(9, 1));
/* 449 */                 monthTableLens.setObject(nextRow, 0, monthNameString);
/* 450 */                 monthTableLens.setRowHeight(nextRow, 15);
/* 451 */                 monthTableLens.setRowFont(nextRow, new Font("Arial", 1, 12));
/* 452 */                 monthTableLens.setRowBackground(nextRow, SHADED_AREA_COLOR);
/* 453 */                 monthTableLens.setRowForeground(nextRow, Color.black);
/* 454 */                 monthTableLens.setRowBorderColor(nextRow, Color.white);
/* 455 */                 monthTableLens.setRowBorderColor(nextRow - 1, Color.white);
/* 456 */                 monthTableLens.setColBorderColor(nextRow, -1, Color.white);
/* 457 */                 monthTableLens.setColBorderColor(nextRow, 0, Color.white);
/* 458 */                 monthTableLens.setColBorderColor(nextRow, 8, Color.white);
/*     */                 
/* 460 */                 hbandCategory.addTable(monthTableLens, new Rectangle(800, 800));
/*     */                 
/* 462 */                 footer.setVisible(true);
/* 463 */                 footer.setHeight(0.1F);
/* 464 */                 footer.setShrinkToFit(false);
/* 465 */                 footer.setBottomBorder(0);
/*     */ 
/*     */                 
/* 468 */                 group = new DefaultSectionLens(null, group, hbandCategory);
/*     */ 
/*     */                 
/* 471 */                 Hashtable dateTable = (Hashtable)monthTable.get(monthName);
/* 472 */                 if (dateTable != null) {
/*     */                   
/* 474 */                   Enumeration dateSort = dateTable.keys();
/*     */                   
/* 476 */                   Vector dateVector = new Vector();
/*     */ 
/*     */ 
/*     */                   
/* 480 */                   while (dateSort.hasMoreElements()) {
/* 481 */                     dateVector.add((String)dateSort.nextElement());
/*     */                   }
/* 483 */                   Object[] dateArray = null;
/*     */                   
/* 485 */                   dateArray = dateVector.toArray();
/* 486 */                   Arrays.sort(dateArray, new StringDateComparator());
/*     */                   
/* 488 */                   for (int dIndex = 0; dIndex < dateArray.length; dIndex++) {
/*     */                     
/* 490 */                     String dateName = (String)dateArray[dIndex];
/* 491 */                     String dateNameText = dateName;
/*     */                     
/* 493 */                     if (monthNameString.equalsIgnoreCase("TBS")) {
/*     */                       
/* 495 */                       dateNameText = "TBS " + dateName;
/*     */                     }
/* 497 */                     else if (monthNameString.equalsIgnoreCase("ITW")) {
/*     */                       
/* 499 */                       dateNameText = "ITW " + dateName;
/*     */                     } 
/*     */                     
/* 502 */                     hbandDate = new SectionBand(report);
/* 503 */                     hbandDate.setHeight(0.65F);
/* 504 */                     hbandDate.setShrinkToFit(false);
/* 505 */                     hbandDate.setVisible(true);
/* 506 */                     hbandDate.setBottomBorder(0);
/* 507 */                     hbandDate.setLeftBorder(0);
/* 508 */                     hbandDate.setRightBorder(0);
/* 509 */                     hbandDate.setTopBorder(0);
/*     */                     
/* 511 */                     dateTableLens = new DefaultTableLens(1, 9);
/*     */                     
/* 513 */                     nextRow = 0;
/*     */                     
/* 515 */                     dateTableLens.setSpan(nextRow, 0, new Dimension(9, 1));
/* 516 */                     dateTableLens.setObject(nextRow, 0, dateNameText);
/* 517 */                     dateTableLens.setRowHeight(nextRow, 14);
/* 518 */                     dateTableLens.setRowFont(nextRow, new Font("Arial", 1, 12));
/* 519 */                     dateTableLens.setRowForeground(nextRow, Color.black);
/* 520 */                     dateTableLens.setRowBorderColor(nextRow, Color.white);
/* 521 */                     dateTableLens.setRowBorderColor(nextRow - 1, Color.white);
/* 522 */                     dateTableLens.setColBorderColor(nextRow, -1, Color.white);
/* 523 */                     dateTableLens.setColBorderColor(nextRow, 0, Color.white);
/* 524 */                     dateTableLens.setColBorderColor(nextRow, 8, Color.white);
/* 525 */                     dateTableLens.setRowBackground(nextRow, Color.white);
/*     */                     
/* 527 */                     hbandDate.addTable(dateTableLens, new Rectangle(0, 0, 800, 20));
/* 528 */                     hbandDate.setBottomBorder(0);
/*     */                     
/* 530 */                     group = new DefaultSectionLens(null, group, hbandDate);
/*     */                     
/* 532 */                     nextRow = 0;
/*     */                     
/* 534 */                     columnHeaderTable = new DefaultTableLens(1, 9);
/*     */                     
/* 536 */                     columnHeaderTable.setColWidth(0, 146);
/* 537 */                     columnHeaderTable.setColWidth(1, 58);
/* 538 */                     columnHeaderTable.setColWidth(2, 45);
/* 539 */                     columnHeaderTable.setColWidth(3, 48);
/* 540 */                     columnHeaderTable.setColWidth(4, 45);
/* 541 */                     columnHeaderTable.setColWidth(5, 45);
/* 542 */                     columnHeaderTable.setColWidth(6, 50);
/* 543 */                     columnHeaderTable.setColWidth(7, 38);
/* 544 */                     columnHeaderTable.setColWidth(8, 105);
/*     */ 
/*     */                     
/* 547 */                     columnHeaderTable.setAlignment(nextRow, 0, 1);
/* 548 */                     columnHeaderTable.setObject(nextRow, 0, "Artist/Title");
/* 549 */                     columnHeaderTable.setAlignment(nextRow, 1, 2);
/* 550 */                     columnHeaderTable.setObject(nextRow, 1, "Selection\nPrice");
/* 551 */                     columnHeaderTable.setAlignment(nextRow, 2, 2);
/* 552 */                     columnHeaderTable.setObject(nextRow, 2, "Package\nCopy");
/* 553 */                     columnHeaderTable.setAlignment(nextRow, 3, 2);
/* 554 */                     columnHeaderTable.setObject(nextRow, 3, "Masters\nApproved");
/* 555 */                     columnHeaderTable.setAlignment(nextRow, 4, 2);
/* 556 */                     columnHeaderTable.setObject(nextRow, 4, "Cover\nArt");
/* 557 */                     columnHeaderTable.setAlignment(nextRow, 5, 2);
/* 558 */                     columnHeaderTable.setObject(nextRow, 5, "Film");
/* 559 */                     columnHeaderTable.setAlignment(nextRow, 6, 2);
/*     */ 
/*     */ 
/*     */ 
/*     */                     
/* 564 */                     columnHeaderTable.setColBorderColor(COL_ROW_LINE_COLOR);
/* 565 */                     for (int col = -1; col < 9; col++) {
/* 566 */                       columnHeaderTable.setColBorder(nextRow, col, 4097);
/*     */                     }
/* 568 */                     columnHeaderTable.setRowBorderColor(nextRow - 1, COL_ROW_LINE_COLOR);
/* 569 */                     columnHeaderTable.setRowBorderColor(nextRow, COL_ROW_LINE_COLOR);
/* 570 */                     columnHeaderTable.setRowBorder(nextRow, 4097);
/*     */                     
/* 572 */                     if (isFullLength) {
/*     */                       
/* 574 */                       columnHeaderTable.setObject(nextRow, 6, "Solicitation");
/*     */                     }
/*     */                     else {
/*     */                       
/* 578 */                       columnHeaderTable.setObject(nextRow, 6, "Add Date");
/*     */                     } 
/*     */                     
/* 581 */                     columnHeaderTable.setAlignment(nextRow, 7, 2);
/* 582 */                     columnHeaderTable.setObject(nextRow, 7, "Depot");
/* 583 */                     columnHeaderTable.setAlignment(nextRow, 8, 2);
/* 584 */                     columnHeaderTable.setObject(nextRow, 8, "Comments");
/* 585 */                     columnHeaderTable.setAlignment(nextRow, 1, 2);
/*     */                     
/* 587 */                     columnHeaderTable.setRowFont(nextRow, new Font("Arial", 1, 8));
/*     */                     
/* 589 */                     hbandDate.addTable(columnHeaderTable, new Rectangle(0, 20, 800, 65));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                     
/* 595 */                     selections = (Vector)dateTable.get(dateName);
/* 596 */                     if (selections == null) {
/* 597 */                       selections = new Vector();
/*     */                     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                     
/* 604 */                     Object[] selectionsArray = selections.toArray();
/*     */                     
/* 606 */                     Arrays.sort(selectionsArray, new SelectionSelectionNumberComparator());
/*     */                     
/* 608 */                     Arrays.sort(selectionsArray, new SelectionTitleComparator());
/*     */                     
/* 610 */                     Arrays.sort(selectionsArray, new SelectionArtistComparator());
/*     */ 
/*     */                     
/* 613 */                     for (int i = 0; i < selectionsArray.length; i++) {
/*     */ 
/*     */ 
/*     */                       
/* 617 */                       if (count < recordCount / tenth) {
/*     */                         
/* 619 */                         count = recordCount / tenth;
/* 620 */                         sresponse = context.getResponse();
/* 621 */                         context.putDelivery("status", new String("start_report"));
/* 622 */                         int myPercent = count * 10;
/* 623 */                         if (myPercent > 90)
/* 624 */                           myPercent = 90; 
/* 625 */                         context.putDelivery("percent", new String(String.valueOf(myPercent)));
/* 626 */                         context.includeJSP("status.jsp", "hiddenFrame");
/* 627 */                         sresponse.setContentType("text/plain");
/* 628 */                         sresponse.flushBuffer();
/*     */                       } 
/*     */                       
/* 631 */                       recordCount++;
/*     */                       
/* 633 */                       Selection sel = (Selection)selectionsArray[i];
/*     */                       
/* 635 */                       nextRow = 0;
/* 636 */                       subTable = new DefaultTableLens(2, 9);
/*     */                       
/* 638 */                       subTable.setColWidth(0, 146);
/* 639 */                       subTable.setColWidth(1, 58);
/* 640 */                       subTable.setColWidth(2, 45);
/* 641 */                       subTable.setColWidth(3, 48);
/* 642 */                       subTable.setColWidth(4, 45);
/* 643 */                       subTable.setColWidth(5, 45);
/* 644 */                       subTable.setColWidth(6, 50);
/* 645 */                       subTable.setColWidth(7, 38);
/* 646 */                       subTable.setColWidth(8, 105);
/*     */ 
/*     */                       
/* 649 */                       String selectionNo = SelectionManager.getLookupObjectValue(sel.getPrefixID());
/* 650 */                       if (selectionNo == null)
/* 651 */                         selectionNo = ""; 
/* 652 */                       selectionNo = String.valueOf(selectionNo) + sel.getSelectionNo();
/*     */ 
/*     */                       
/* 655 */                       String price = "$0.00";
/* 656 */                       if (sel.getPriceCode() != null && 
/* 657 */                         sel.getPriceCode().getTotalCost() > 0.0F) {
/* 658 */                         price = "$" + MilestoneHelper.formatDollarPrice(sel.getPriceCode().getTotalCost());
/*     */                       }
/*     */                       
/* 661 */                       String comment = (sel.getSelectionComments() != null) ? sel.getSelectionComments() : "";
/*     */ 
/*     */                       
/* 664 */                       Schedule schedule = sel.getSchedule();
/*     */                       
/* 666 */                       String packageCopy = "";
/* 667 */                       String packageCopyDue = "";
/* 668 */                       String packageCopyComment = "";
/* 669 */                       String mastersApproved = "";
/* 670 */                       String mastersApprovedDue = "";
/* 671 */                       String mastersApprovedComment = "";
/* 672 */                       String coverArt = "";
/* 673 */                       String coverArtDue = "";
/* 674 */                       String coverArtComment = "";
/* 675 */                       String film = "";
/* 676 */                       String filmDue = "";
/* 677 */                       String filmComment = "";
/* 678 */                       String addDate = "";
/* 679 */                       String addDateDue = "";
/* 680 */                       String addDateComment = "";
/* 681 */                       String depot = "";
/* 682 */                       String depotDue = "";
/* 683 */                       String depotComment = "";
/* 684 */                       String solicitation = "";
/* 685 */                       String solicitationDue = "";
/* 686 */                       String solicitationComment = "";
/*     */                       
/* 688 */                       if (schedule != null) {
/*     */                         
/* 690 */                         Vector tasks = schedule.getTasks();
/*     */                         
/* 692 */                         if (tasks != null && tasks.size() > 0)
/*     */                         {
/* 694 */                           for (int j = 0; j < schedule.getTasks().size(); j++) {
/*     */                             
/* 696 */                             ScheduledTask task = (ScheduledTask)tasks.get(j);
/*     */                             
/* 698 */                             if (task != null && task.getDueDate() != null) {
/*     */                               
/* 700 */                               SimpleDateFormat dueDateFormatter = new SimpleDateFormat("M/d");
/* 701 */                               String dueDate = dueDateFormatter.format(task.getDueDate().getTime());
/* 702 */                               String completionDate = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*     */ 
/*     */                               
/* 705 */                               if (task.getScheduledTaskStatus().equals("N/A"))
/*     */                               {
/* 707 */                                 completionDate = task.getScheduledTaskStatus();
/*     */                               }
/*     */                               
/* 710 */                               String taskAbbrev = MilestoneHelper.getTaskAbbreivationNameById(task.getTaskAbbreviationID());
/* 711 */                               String taskComment = "";
/*     */                               
/* 713 */                               if (task.getComments() != null) {
/* 714 */                                 taskComment = task.getComments();
/*     */                               }
/* 716 */                               if (taskAbbrev.equalsIgnoreCase("FPC")) {
/*     */                                 
/* 718 */                                 packageCopy = completionDate;
/* 719 */                                 packageCopyDue = dueDate;
/* 720 */                                 packageCopyComment = taskComment;
/*     */                               }
/* 722 */                               else if (taskAbbrev.equalsIgnoreCase("MA")) {
/*     */                                 
/* 724 */                                 mastersApproved = completionDate;
/* 725 */                                 mastersApprovedDue = dueDate;
/* 726 */                                 mastersApprovedComment = taskComment;
/*     */                               }
/* 728 */                               else if (taskAbbrev.equalsIgnoreCase("CAA")) {
/*     */                                 
/* 730 */                                 coverArt = completionDate;
/* 731 */                                 coverArtDue = dueDate;
/* 732 */                                 coverArtComment = taskComment;
/*     */                               }
/* 734 */                               else if (taskAbbrev.equalsIgnoreCase("PFS")) {
/*     */                                 
/* 736 */                                 film = completionDate;
/* 737 */                                 filmDue = dueDate;
/* 738 */                                 filmComment = taskComment;
/*     */                               }
/* 740 */                               else if (taskAbbrev.equalsIgnoreCase("SOL")) {
/*     */                                 
/* 742 */                                 solicitation = completionDate;
/* 743 */                                 solicitationDue = dueDate;
/* 744 */                                 solicitationComment = taskComment;
/*     */                               }
/* 746 */                               else if (taskAbbrev.equalsIgnoreCase("DEPO")) {
/*     */                                 
/* 748 */                                 depot = completionDate;
/* 749 */                                 depotDue = dueDate;
/* 750 */                                 depotComment = taskComment;
/*     */                               }
/* 752 */                               else if (taskAbbrev.equalsIgnoreCase("ADD")) {
/*     */                                 
/* 754 */                                 addDate = completionDate;
/* 755 */                                 addDateDue = dueDate;
/* 756 */                                 addDateComment = taskComment;
/*     */                               } 
/*     */                             } 
/*     */                           } 
/*     */                         }
/*     */                       } 
/*     */ 
/*     */ 
/*     */ 
/*     */                       
/* 766 */                       String seventhColumn = "";
/* 767 */                       String seventhColumnDue = "";
/* 768 */                       String seventhColumnComment = "";
/*     */ 
/*     */                       
/* 771 */                       if (isFullLength) {
/*     */                         
/* 773 */                         seventhColumn = solicitation;
/* 774 */                         seventhColumnDue = solicitationDue;
/* 775 */                         seventhColumnComment = solicitationComment;
/*     */                       }
/*     */                       else {
/*     */                         
/* 779 */                         seventhColumn = addDate;
/* 780 */                         seventhColumnDue = addDateDue;
/* 781 */                         seventhColumnComment = addDateComment;
/*     */                       } 
/*     */ 
/*     */                       
/* 785 */                       String[] checkStrings = { null, ((new String[2][0] = comment.trim()).valueOf(sel.getFlArtist()) + "\n" + sel.getTitle()).trim() };
/* 786 */                       int[] checkStringsLength = { 60, 60 };
/*     */                       
/* 788 */                       int addExtraLines = MilestoneHelper.lineCount(checkStrings, checkStringsLength);
/* 789 */                       String selectionNoPrice = String.valueOf(selectionNo) + "\n" + price;
/* 790 */                       for (int z = 0; z < addExtraLines; z++) {
/* 791 */                         selectionNoPrice = String.valueOf(selectionNoPrice) + "\n";
/*     */                       }
/*     */                       
/* 794 */                       subTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
/*     */                       
/* 796 */                       subTable.setObject(nextRow, 0, String.valueOf(sel.getFlArtist()) + "\n" + sel.getTitle());
/* 797 */                       subTable.setSpan(nextRow, 0, new Dimension(1, 2));
/*     */                       
/* 799 */                       subTable.setObject(nextRow, 1, "Due Dates");
/* 800 */                       subTable.setFont(nextRow, 1, new Font("Arial", 1, 9));
/*     */                       
/* 802 */                       subTable.setObject(nextRow, 2, packageCopyDue);
/* 803 */                       subTable.setObject(nextRow, 3, mastersApprovedDue);
/* 804 */                       subTable.setObject(nextRow, 4, coverArtDue);
/* 805 */                       subTable.setObject(nextRow, 5, filmDue);
/* 806 */                       subTable.setObject(nextRow, 6, seventhColumnDue);
/* 807 */                       subTable.setObject(nextRow, 7, depotDue);
/* 808 */                       subTable.setObject(nextRow, 8, comment);
/*     */                       
/* 810 */                       subTable.setRowBorderColor(nextRow, Color.white);
/* 811 */                       subTable.setRowBorder(nextRow, 4097);
/*     */                       
/* 813 */                       subTable.setRowHeight(nextRow, 10);
/* 814 */                       subTable.setColAutoSize(true);
/*     */                       
/* 816 */                       subTable.setRowFont(nextRow, new Font("Arial", 1, 7));
/*     */ 
/*     */ 
/*     */ 
/*     */                       
/* 821 */                       subTable.setColBorderColor(COL_ROW_LINE_COLOR);
/* 822 */                       subTable.setColBorder(nextRow, -1, 4097);
/* 823 */                       subTable.setColBorder(nextRow, 0, 4097);
/* 824 */                       subTable.setColBorder(nextRow, 1, 4097);
/* 825 */                       subTable.setColBorder(nextRow, 2, 4097);
/* 826 */                       subTable.setColBorder(nextRow, 3, 4097);
/* 827 */                       subTable.setColBorder(nextRow, 4, 4097);
/* 828 */                       subTable.setColBorder(nextRow, 5, 4097);
/* 829 */                       subTable.setColBorder(nextRow, 6, 4097);
/* 830 */                       subTable.setColBorder(nextRow, 7, 4097);
/* 831 */                       subTable.setColBorder(nextRow, 8, 4097);
/*     */                       
/* 833 */                       subTable.setBackground(nextRow, 1, SHADED_ROW_COLOR);
/* 834 */                       subTable.setBackground(nextRow, 2, SHADED_ROW_COLOR);
/* 835 */                       subTable.setBackground(nextRow, 3, SHADED_ROW_COLOR);
/* 836 */                       subTable.setBackground(nextRow, 4, SHADED_ROW_COLOR);
/* 837 */                       subTable.setBackground(nextRow, 5, SHADED_ROW_COLOR);
/* 838 */                       subTable.setBackground(nextRow, 6, SHADED_ROW_COLOR);
/* 839 */                       subTable.setBackground(nextRow, 7, SHADED_ROW_COLOR);
/*     */                       
/* 841 */                       subTable.setAlignment(nextRow, 0, 8);
/* 842 */                       subTable.setAlignment(nextRow, 1, 10);
/* 843 */                       subTable.setAlignment(nextRow, 2, 10);
/* 844 */                       subTable.setAlignment(nextRow, 3, 10);
/* 845 */                       subTable.setAlignment(nextRow, 4, 10);
/* 846 */                       subTable.setAlignment(nextRow, 5, 10);
/* 847 */                       subTable.setAlignment(nextRow, 6, 10);
/* 848 */                       subTable.setAlignment(nextRow, 7, 10);
/* 849 */                       subTable.setAlignment(nextRow, 8, 10);
/*     */                       
/* 851 */                       subTable.setObject(nextRow + 1, 1, selectionNoPrice);
/* 852 */                       subTable.setObject(nextRow + 1, 2, String.valueOf(packageCopy) + "\n" + packageCopyComment);
/* 853 */                       subTable.setObject(nextRow + 1, 3, String.valueOf(mastersApproved) + "\n" + mastersApprovedComment);
/* 854 */                       subTable.setObject(nextRow + 1, 4, String.valueOf(coverArt) + "\n" + coverArtComment);
/* 855 */                       subTable.setObject(nextRow + 1, 5, String.valueOf(film) + "\n" + filmComment);
/* 856 */                       subTable.setObject(nextRow + 1, 6, String.valueOf(seventhColumn) + "\n" + seventhColumnComment);
/* 857 */                       subTable.setObject(nextRow + 1, 7, String.valueOf(depot) + "\n" + depotComment);
/* 858 */                       subTable.setRowFont(nextRow + 1, new Font("Arial", 0, 7));
/*     */                       
/* 860 */                       subTable.setFont(nextRow, 0, new Font("Arial", 0, 7));
/* 861 */                       subTable.setFont(nextRow, 8, new Font("Arial", 0, 7));
/*     */                       
/* 863 */                       subTable.setAlignment(nextRow + 1, 1, 10);
/* 864 */                       subTable.setAlignment(nextRow + 1, 2, 10);
/* 865 */                       subTable.setAlignment(nextRow + 1, 3, 10);
/* 866 */                       subTable.setAlignment(nextRow + 1, 4, 10);
/* 867 */                       subTable.setAlignment(nextRow + 1, 5, 10);
/* 868 */                       subTable.setAlignment(nextRow + 1, 6, 10);
/* 869 */                       subTable.setAlignment(nextRow + 1, 7, 10);
/*     */ 
/*     */ 
/*     */ 
/*     */                       
/* 874 */                       subTable.setColBorderColor(COL_ROW_LINE_COLOR);
/* 875 */                       subTable.setColBorder(nextRow + 1, -1, 4097);
/* 876 */                       subTable.setColBorder(nextRow + 1, 0, 4097);
/* 877 */                       subTable.setColBorder(nextRow + 1, 1, 4097);
/* 878 */                       subTable.setColBorder(nextRow + 1, 2, 4097);
/* 879 */                       subTable.setColBorder(nextRow + 1, 3, 4097);
/* 880 */                       subTable.setColBorder(nextRow + 1, 4, 4097);
/* 881 */                       subTable.setColBorder(nextRow + 1, 5, 4097);
/* 882 */                       subTable.setColBorder(nextRow + 1, 6, 4097);
/* 883 */                       subTable.setColBorder(nextRow + 1, 7, 4097);
/* 884 */                       subTable.setColBorder(nextRow + 1, 8, 4097);
/* 885 */                       subTable.setSpan(nextRow, 8, new Dimension(1, 2));
/*     */                       
/* 887 */                       subTable.setRowBorderColor(nextRow - 1, COL_ROW_LINE_COLOR);
/* 888 */                       subTable.setRowBorderColor(nextRow, COL_ROW_LINE_COLOR);
/* 889 */                       subTable.setRowBorderColor(nextRow + 1, COL_ROW_LINE_COLOR);
/* 890 */                       subTable.setRowBorder(nextRow + 1, 4097);
/*     */                       
/* 892 */                       body = new SectionBand(report);
/* 893 */                       body.addTable(subTable, new Rectangle(800, 800));
/* 894 */                       body.setHeight(2.0F);
/* 895 */                       body.setBottomBorder(0);
/* 896 */                       body.setTopBorder(0);
/* 897 */                       body.setShrinkToFit(true);
/* 898 */                       body.setVisible(true);
/*     */                       
/* 900 */                       group = new DefaultSectionLens(null, group, body);
/*     */                     } 
/* 902 */                     group = new DefaultSectionLens(null, group, spacer);
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */             } 
/* 907 */             group = new DefaultSectionLens(hbandType, group, null);
/* 908 */             report.addSection(group, rowCountTable);
/* 909 */             report.addPageBreak();
/* 910 */             group = null;
/*     */           }
/*     */         
/*     */         } 
/*     */       } 
/* 915 */     } catch (Exception e) {
/*     */       
/* 917 */       System.out.println(">>>>>>>>ReportHandler.fillNashProdScheduleForPrint(): exception: " + e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\NashProdScheduleForPrintSubHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */