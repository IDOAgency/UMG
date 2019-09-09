/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.ComponentLog;
/*     */ import com.techempower.gemini.Context;
/*     */ import com.techempower.gemini.GeminiApplication;
/*     */ import com.universal.milestone.DreamWksNashProdSchForPrintSubHandler;
/*     */ import com.universal.milestone.Form;
/*     */ import com.universal.milestone.IntegerComparator;
/*     */ import com.universal.milestone.MilestoneHelper;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DreamWksNashProdSchForPrintSubHandler
/*     */   extends SecureHandler
/*     */ {
/*     */   public static final String COMPONENT_CODE = "hDWProd";
/*     */   public GeminiApplication application;
/*     */   public ComponentLog log;
/*     */   
/*     */   public DreamWksNashProdSchForPrintSubHandler(GeminiApplication application) {
/*  72 */     this.application = application;
/*  73 */     this.log = application.getLog("hDWProd");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  81 */   public String getDescription() { return "Sub Report"; }
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
/*     */   protected static void fillDreamWksNashProdSchForPrint2(XStyleSheet report, Context context) {
/*  93 */     Color SHADED_AREA_COLOR = Color.lightGray;
/*  94 */     int COL_LINE_STYLE = 4097;
/*  95 */     int HEADER_FONT_SIZE = 12;
/*  96 */     int NUM_COLUMNS = 12;
/*     */     
/*  98 */     double ldLineVal = 0.3D;
/*     */     
/* 100 */     SectionBand hbandType = new SectionBand(report);
/* 101 */     SectionBand hbandCategory = new SectionBand(report);
/* 102 */     SectionBand hbandDate = new SectionBand(report);
/* 103 */     SectionBand body = new SectionBand(report);
/* 104 */     SectionBand footer = new SectionBand(report);
/* 105 */     SectionBand spacer = new SectionBand(report);
/* 106 */     DefaultSectionLens group = null;
/*     */     
/* 108 */     footer.setVisible(true);
/* 109 */     footer.setHeight(0.1F);
/* 110 */     footer.setShrinkToFit(false);
/* 111 */     footer.setBottomBorder(0);
/*     */     
/* 113 */     spacer.setVisible(true);
/* 114 */     spacer.setHeight(0.05F);
/* 115 */     spacer.setShrinkToFit(false);
/* 116 */     spacer.setBottomBorder(0);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 121 */       HttpServletResponse sresponse = context.getResponse();
/* 122 */       context.putDelivery("status", new String("start_gathering"));
/* 123 */       context.includeJSP("status.jsp", "hiddenFrame");
/* 124 */       sresponse.setContentType("text/plain");
/* 125 */       sresponse.flushBuffer();
/*     */     }
/* 127 */     catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 132 */     Vector selections = MilestoneHelper.getSelectionsForReport(context);
/*     */ 
/*     */     
/*     */     try {
/* 136 */       HttpServletResponse sresponse = context.getResponse();
/* 137 */       context.putDelivery("status", new String("start_report"));
/* 138 */       context.putDelivery("percent", new String("10"));
/* 139 */       context.includeJSP("status.jsp", "hiddenFrame");
/* 140 */       sresponse.setContentType("text/plain");
/* 141 */       sresponse.flushBuffer();
/*     */     }
/* 143 */     catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 148 */     int DATA_FONT_SIZE = 7;
/* 149 */     int SUB_HEADER_FONT_SIZE = 8;
/* 150 */     int ROW_LINE_STYLE = 4097;
/* 151 */     Color COL_ROW_LINE_COLOR = new Color(208, 206, 206, 0);
/* 152 */     Color SHADED_ROW_COLOR = new Color(208, 206, 206, 0);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 157 */     Hashtable selTable = MilestoneHelper.groupSelectionsByCompanyAndSubconfigAndStreetDate(selections);
/*     */     
/* 159 */     Enumeration companies = selTable.keys();
/* 160 */     Vector companyVector = new Vector();
/*     */     
/* 162 */     while (companies.hasMoreElements()) {
/* 163 */       companyVector.addElement(companies.nextElement());
/*     */     }
/* 165 */     int numCompanies = companyVector.size();
/*     */ 
/*     */     
/*     */     try {
/* 169 */       DefaultTableLens table_contents = null;
/* 170 */       DefaultTableLens rowCountTable = null;
/* 171 */       DefaultTableLens columnHeaderTable = null;
/* 172 */       DefaultTableLens subTable = null;
/* 173 */       DefaultTableLens monthTableLens = null;
/* 174 */       DefaultTableLens dateTableLens = null;
/* 175 */       DefaultTableLens subconfigTableLens = null;
/*     */ 
/*     */       
/* 178 */       rowCountTable = new DefaultTableLens(2, 10000);
/*     */       
/* 180 */       Object[] companyArray = companyVector.toArray();
/* 181 */       Arrays.sort(companyArray, new StringComparator());
/*     */       
/* 183 */       for (int n = 0; n < companyArray.length; n++) {
/*     */         
/* 185 */         String company = (String)companyArray[n];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 192 */         Form reportForm = (Form)context.getSessionValue("reportForm");
/*     */         
/* 194 */         Calendar beginStDate = (reportForm.getStringValue("beginDate") != null && 
/* 195 */           reportForm.getStringValue("beginDate").length() > 0) ? 
/* 196 */           MilestoneHelper.getDate(reportForm.getStringValue("beginDate")) : null;
/*     */         
/* 198 */         Calendar endStDate = (reportForm.getStringValue("endDate") != null && 
/* 199 */           reportForm.getStringValue("endDate").length() > 0) ? 
/* 200 */           MilestoneHelper.getDate(reportForm.getStringValue("endDate")) : null;
/*     */         
/* 202 */         String startDate = MilestoneHelper.getFormatedDate(beginStDate);
/* 203 */         String endDate = MilestoneHelper.getFormatedDate(endStDate);
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
/* 215 */         report.setElement("crs_startdate", startDate);
/* 216 */         report.setElement("crs_enddate", endDate);
/*     */         
/* 218 */         SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
/*     */         
/* 220 */         String todayLong = formatter.format(new Date());
/*     */         
/* 222 */         report.setElement("crs_bottomdate", todayLong);
/*     */ 
/*     */         
/* 225 */         int numSubconfigs = 0;
/* 226 */         int numMonths = 0;
/* 227 */         int numDates = 0;
/* 228 */         int numSelections = 0;
/* 229 */         int totalCount = 0;
/* 230 */         int tenth = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 236 */         Hashtable subconfigTable = (Hashtable)selTable.get(company);
/*     */         
/* 238 */         if (subconfigTable != null) {
/*     */           
/* 240 */           Enumeration subconfigs = subconfigTable.keys();
/*     */           
/* 242 */           while (subconfigs.hasMoreElements()) {
/*     */             
/* 244 */             totalCount++;
/* 245 */             numSubconfigs++;
/*     */             
/* 247 */             String subconfig = (String)subconfigs.nextElement();
/*     */             
/* 249 */             Hashtable monthTable = (Hashtable)subconfigTable.get(subconfig);
/* 250 */             if (monthTable != null) {
/*     */               
/* 252 */               Enumeration months = monthTable.keys();
/* 253 */               while (months.hasMoreElements()) {
/*     */                 
/* 255 */                 String monthName = (String)months.nextElement();
/*     */                 
/* 257 */                 numMonths++;
/* 258 */                 totalCount += monthTable.size();
/*     */                 
/* 260 */                 Hashtable dateTable = (Hashtable)monthTable.get(monthName);
/* 261 */                 if (dateTable != null) {
/*     */                   
/* 263 */                   Enumeration dates = dateTable.keys();
/* 264 */                   while (dates.hasMoreElements()) {
/*     */                     
/* 266 */                     String dateName = (String)dates.nextElement();
/*     */                     
/* 268 */                     numDates++;
/*     */                     
/* 270 */                     selections = (Vector)dateTable.get(dateName);
/* 271 */                     if (selections != null) {
/* 272 */                       numSelections += selections.size();
/*     */                     }
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 282 */         tenth = (totalCount > 10) ? (totalCount / 10) : 1;
/*     */         
/* 284 */         HttpServletResponse sresponse = context.getResponse();
/* 285 */         context.putDelivery("status", new String("start_report"));
/* 286 */         context.putDelivery("percent", new String("20"));
/* 287 */         context.includeJSP("status.jsp", "hiddenFrame");
/* 288 */         sresponse.setContentType("text/plain");
/* 289 */         sresponse.flushBuffer();
/*     */         
/* 291 */         int recordCount = 0;
/* 292 */         int count = 0;
/* 293 */         int numRows = 0;
/*     */ 
/*     */ 
/*     */         
/* 297 */         numRows += numSubconfigs * 4;
/* 298 */         numRows += numMonths * 2;
/* 299 */         numRows += numDates * 3;
/* 300 */         numRows += numSelections * 2;
/*     */         
/* 302 */         numRows += 3;
/*     */ 
/*     */ 
/*     */         
/* 306 */         table_contents = new DefaultTableLens(1, 9);
/*     */         
/* 308 */         String companyHeaderText = !company.trim().equals("") ? company : "Other";
/*     */         
/* 310 */         table_contents.setColWidth(0, 146);
/* 311 */         table_contents.setColWidth(1, 58);
/* 312 */         table_contents.setColWidth(2, 45);
/* 313 */         table_contents.setColWidth(3, 48);
/* 314 */         table_contents.setColWidth(4, 45);
/* 315 */         table_contents.setColWidth(5, 45);
/* 316 */         table_contents.setColWidth(6, 50);
/* 317 */         table_contents.setColWidth(7, 38);
/* 318 */         table_contents.setColWidth(8, 105);
/*     */ 
/*     */ 
/*     */         
/* 322 */         int nextRow = 0;
/*     */ 
/*     */         
/* 325 */         table_contents.setObject(nextRow, 0, companyHeaderText);
/* 326 */         table_contents.setSpan(nextRow, 0, new Dimension(9, 1));
/* 327 */         table_contents.setRowBackground(nextRow, Color.black);
/* 328 */         table_contents.setRowForeground(nextRow, Color.white);
/* 329 */         table_contents.setRowFont(nextRow, new Font("Arial", 1, 12));
/*     */         
/* 331 */         table_contents.setRowBorderColor(nextRow, Color.white);
/* 332 */         table_contents.setRowBorder(nextRow, 266240);
/*     */ 
/*     */ 
/*     */         
/* 336 */         if (subconfigTable != null) {
/*     */           
/* 338 */           Enumeration subconfigs = subconfigTable.keys();
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 343 */           Vector subconfigVector = new Vector();
/*     */           
/* 345 */           while (subconfigs.hasMoreElements()) {
/* 346 */             subconfigVector.add((String)subconfigs.nextElement());
/*     */           }
/* 348 */           Object[] subconfigsArray = subconfigVector.toArray();
/* 349 */           Arrays.sort(subconfigsArray, new StringComparator());
/*     */           
/* 351 */           for (int scIndex = 0; scIndex < subconfigsArray.length; scIndex++)
/*     */           {
/* 353 */             String subconfig = (String)subconfigsArray[scIndex];
/*     */             
/* 355 */             boolean isFullLength = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 361 */             if (subconfig.equalsIgnoreCase("full length")) {
/* 362 */               isFullLength = true;
/*     */             }
/* 364 */             nextRow = 0;
/*     */             
/* 366 */             subconfigTableLens = new DefaultTableLens(1, 9);
/*     */ 
/*     */             
/* 369 */             subconfigTableLens.setSpan(nextRow, 0, new Dimension(9, 1));
/* 370 */             subconfigTableLens.setAlignment(nextRow, 0, 2);
/* 371 */             subconfigTableLens.setObject(nextRow, 0, subconfig);
/* 372 */             subconfigTableLens.setRowHeight(nextRow, 15);
/*     */             
/* 374 */             subconfigTableLens.setRowBorderColor(nextRow, Color.black);
/* 375 */             subconfigTableLens.setRowBorder(nextRow, 0, 266240);
/*     */             
/* 377 */             subconfigTableLens.setRowFont(nextRow, new Font("Arial", 3, 12));
/* 378 */             subconfigTableLens.setRowBackground(nextRow, Color.white);
/* 379 */             subconfigTableLens.setRowForeground(nextRow, Color.black);
/*     */             
/* 381 */             subconfigTableLens.setRowBorder(nextRow - 1, 266240);
/* 382 */             subconfigTableLens.setColBorder(nextRow, -1, 266240);
/* 383 */             subconfigTableLens.setColBorder(nextRow, 0, 266240);
/* 384 */             subconfigTableLens.setColBorder(nextRow, 9, 266240);
/* 385 */             subconfigTableLens.setColBorder(nextRow, 8, 266240);
/* 386 */             subconfigTableLens.setRowBorder(nextRow, 266240);
/*     */             
/* 388 */             subconfigTableLens.setRowBorderColor(nextRow - 1, Color.black);
/* 389 */             subconfigTableLens.setColBorderColor(nextRow, -1, Color.black);
/* 390 */             subconfigTableLens.setColBorderColor(nextRow, 0, Color.black);
/* 391 */             subconfigTableLens.setColBorderColor(nextRow, 9, Color.black);
/* 392 */             subconfigTableLens.setColBorderColor(nextRow, 8, Color.black);
/* 393 */             subconfigTableLens.setRowBorderColor(nextRow, Color.black);
/*     */             
/* 395 */             hbandType = new SectionBand(report);
/* 396 */             hbandType.setHeight(0.7F);
/* 397 */             hbandType.setShrinkToFit(false);
/* 398 */             hbandType.setVisible(true);
/* 399 */             hbandType.addTable(table_contents, new Rectangle(0, 0, 800, 25));
/* 400 */             hbandType.addTable(subconfigTableLens, new Rectangle(0, 25, 800, 30));
/*     */             
/* 402 */             Hashtable monthTable = (Hashtable)subconfigTable.get(subconfig);
/*     */             
/* 404 */             if (monthTable != null) {
/*     */               
/* 406 */               Enumeration monthSort = monthTable.keys();
/*     */               
/* 408 */               Vector monthVector = new Vector();
/*     */ 
/*     */               
/* 411 */               while (monthSort.hasMoreElements()) {
/* 412 */                 monthVector.add((String)monthSort.nextElement());
/*     */               }
/* 414 */               Object[] monthArray = (Object[])null;
/*     */               
/* 416 */               monthArray = monthVector.toArray();
/*     */               
/* 418 */               Arrays.sort(monthArray, new IntegerComparator());
/*     */               
/* 420 */               for (int mIndex = 0; mIndex < monthArray.length; mIndex++) {
/*     */                 
/* 422 */                 String monthName = (String)monthArray[mIndex];
/* 423 */                 String monthNameString = monthName;
/*     */ 
/*     */                 
/*     */                 try {
/* 427 */                   monthNameString = MONTHS[Integer.parseInt(monthName) - 1];
/*     */                 }
/* 429 */                 catch (Exception e) {
/*     */                   
/* 431 */                   if (monthName.equals("13")) {
/* 432 */                     monthNameString = "TBS";
/* 433 */                   } else if (monthName.equals("26")) {
/* 434 */                     monthNameString = "ITW";
/*     */                   } else {
/* 436 */                     monthNameString = "No street date";
/*     */                   } 
/*     */                 } 
/* 439 */                 monthTableLens = new DefaultTableLens(1, 9);
/* 440 */                 hbandCategory = new SectionBand(report);
/* 441 */                 hbandCategory.setHeight(0.25F);
/* 442 */                 hbandCategory.setShrinkToFit(false);
/* 443 */                 hbandCategory.setVisible(true);
/* 444 */                 hbandCategory.setBottomBorder(0);
/* 445 */                 hbandCategory.setLeftBorder(0);
/* 446 */                 hbandCategory.setRightBorder(0);
/* 447 */                 hbandCategory.setTopBorder(0);
/*     */                 
/* 449 */                 nextRow = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 454 */                 monthTableLens.setSpan(nextRow, 0, new Dimension(9, 1));
/* 455 */                 monthTableLens.setObject(nextRow, 0, monthNameString);
/* 456 */                 monthTableLens.setRowHeight(nextRow, 15);
/* 457 */                 monthTableLens.setRowFont(nextRow, new Font("Arial", 1, 12));
/* 458 */                 monthTableLens.setRowBackground(nextRow, SHADED_AREA_COLOR);
/* 459 */                 monthTableLens.setRowForeground(nextRow, Color.black);
/* 460 */                 monthTableLens.setRowBorderColor(nextRow, Color.white);
/* 461 */                 monthTableLens.setRowBorderColor(nextRow - 1, Color.white);
/* 462 */                 monthTableLens.setColBorderColor(nextRow, -1, Color.white);
/* 463 */                 monthTableLens.setColBorderColor(nextRow, 0, Color.white);
/* 464 */                 monthTableLens.setColBorderColor(nextRow, 8, Color.white);
/*     */                 
/* 466 */                 hbandCategory.addTable(monthTableLens, new Rectangle(800, 800));
/*     */                 
/* 468 */                 footer.setVisible(true);
/* 469 */                 footer.setHeight(0.1F);
/* 470 */                 footer.setShrinkToFit(false);
/* 471 */                 footer.setBottomBorder(0);
/*     */ 
/*     */                 
/* 474 */                 group = new DefaultSectionLens(null, group, hbandCategory);
/*     */ 
/*     */                 
/* 477 */                 Hashtable dateTable = (Hashtable)monthTable.get(monthName);
/* 478 */                 if (dateTable != null) {
/*     */                   
/* 480 */                   Enumeration dateSort = dateTable.keys();
/*     */                   
/* 482 */                   Vector dateVector = new Vector();
/*     */ 
/*     */ 
/*     */                   
/* 486 */                   while (dateSort.hasMoreElements()) {
/* 487 */                     dateVector.add((String)dateSort.nextElement());
/*     */                   }
/* 489 */                   Object[] dateArray = (Object[])null;
/*     */                   
/* 491 */                   dateArray = dateVector.toArray();
/* 492 */                   Arrays.sort(dateArray, new StringDateComparator());
/*     */                   
/* 494 */                   for (int dIndex = 0; dIndex < dateArray.length; dIndex++) {
/*     */                     
/* 496 */                     String dateName = (String)dateArray[dIndex];
/* 497 */                     String dateNameText = dateName;
/*     */                     
/* 499 */                     if (monthNameString.equalsIgnoreCase("TBS")) {
/*     */                       
/* 501 */                       dateNameText = "TBS " + dateName;
/*     */                     }
/* 503 */                     else if (monthNameString.equalsIgnoreCase("ITW")) {
/*     */                       
/* 505 */                       dateNameText = "ITW " + dateName;
/*     */                     } 
/*     */                     
/* 508 */                     hbandDate = new SectionBand(report);
/* 509 */                     hbandDate.setHeight(0.65F);
/* 510 */                     hbandDate.setShrinkToFit(false);
/* 511 */                     hbandDate.setVisible(true);
/* 512 */                     hbandDate.setBottomBorder(0);
/* 513 */                     hbandDate.setLeftBorder(0);
/* 514 */                     hbandDate.setRightBorder(0);
/* 515 */                     hbandDate.setTopBorder(0);
/*     */                     
/* 517 */                     dateTableLens = new DefaultTableLens(1, 9);
/*     */                     
/* 519 */                     nextRow = 0;
/*     */                     
/* 521 */                     dateTableLens.setSpan(nextRow, 0, new Dimension(9, 1));
/* 522 */                     dateTableLens.setObject(nextRow, 0, dateNameText);
/* 523 */                     dateTableLens.setRowHeight(nextRow, 14);
/* 524 */                     dateTableLens.setRowFont(nextRow, new Font("Arial", 1, 12));
/* 525 */                     dateTableLens.setRowForeground(nextRow, Color.black);
/* 526 */                     dateTableLens.setRowBorderColor(nextRow, Color.white);
/* 527 */                     dateTableLens.setRowBorderColor(nextRow - 1, Color.white);
/* 528 */                     dateTableLens.setColBorderColor(nextRow, -1, Color.white);
/* 529 */                     dateTableLens.setColBorderColor(nextRow, 0, Color.white);
/* 530 */                     dateTableLens.setColBorderColor(nextRow, 8, Color.white);
/* 531 */                     dateTableLens.setRowBackground(nextRow, Color.white);
/*     */                     
/* 533 */                     hbandDate.addTable(dateTableLens, new Rectangle(0, 0, 800, 20));
/* 534 */                     hbandDate.setBottomBorder(0);
/*     */                     
/* 536 */                     group = new DefaultSectionLens(null, group, hbandDate);
/*     */                     
/* 538 */                     nextRow = 0;
/*     */                     
/* 540 */                     columnHeaderTable = new DefaultTableLens(1, 9);
/*     */                     
/* 542 */                     columnHeaderTable.setColWidth(0, 146);
/* 543 */                     columnHeaderTable.setColWidth(1, 58);
/* 544 */                     columnHeaderTable.setColWidth(2, 45);
/* 545 */                     columnHeaderTable.setColWidth(3, 48);
/* 546 */                     columnHeaderTable.setColWidth(4, 45);
/* 547 */                     columnHeaderTable.setColWidth(5, 45);
/* 548 */                     columnHeaderTable.setColWidth(6, 50);
/* 549 */                     columnHeaderTable.setColWidth(7, 38);
/* 550 */                     columnHeaderTable.setColWidth(8, 105);
/*     */ 
/*     */                     
/* 553 */                     columnHeaderTable.setAlignment(nextRow, 0, 1);
/* 554 */                     columnHeaderTable.setObject(nextRow, 0, "Artist/Title");
/* 555 */                     columnHeaderTable.setAlignment(nextRow, 1, 2);
/* 556 */                     columnHeaderTable.setObject(nextRow, 1, "Selection\nPrice");
/* 557 */                     columnHeaderTable.setAlignment(nextRow, 2, 2);
/* 558 */                     columnHeaderTable.setObject(nextRow, 2, "Package\nCopy");
/* 559 */                     columnHeaderTable.setAlignment(nextRow, 3, 2);
/* 560 */                     columnHeaderTable.setObject(nextRow, 3, "Masters\nApproved");
/* 561 */                     columnHeaderTable.setAlignment(nextRow, 4, 2);
/* 562 */                     columnHeaderTable.setObject(nextRow, 4, "Cover\nArt");
/* 563 */                     columnHeaderTable.setAlignment(nextRow, 5, 2);
/* 564 */                     columnHeaderTable.setObject(nextRow, 5, "Film");
/* 565 */                     columnHeaderTable.setAlignment(nextRow, 6, 2);
/*     */ 
/*     */ 
/*     */ 
/*     */                     
/* 570 */                     columnHeaderTable.setColBorderColor(COL_ROW_LINE_COLOR);
/* 571 */                     for (int col = -1; col < 9; col++) {
/* 572 */                       columnHeaderTable.setColBorder(nextRow, col, 4097);
/*     */                     }
/* 574 */                     columnHeaderTable.setRowBorderColor(nextRow - 1, COL_ROW_LINE_COLOR);
/* 575 */                     columnHeaderTable.setRowBorderColor(nextRow, COL_ROW_LINE_COLOR);
/* 576 */                     columnHeaderTable.setRowBorder(nextRow, 4097);
/*     */                     
/* 578 */                     if (isFullLength) {
/*     */                       
/* 580 */                       columnHeaderTable.setObject(nextRow, 6, "Solicitation");
/*     */                     }
/*     */                     else {
/*     */                       
/* 584 */                       columnHeaderTable.setObject(nextRow, 6, "Add Date");
/*     */                     } 
/*     */                     
/* 587 */                     columnHeaderTable.setAlignment(nextRow, 7, 2);
/* 588 */                     columnHeaderTable.setObject(nextRow, 7, "Depot");
/* 589 */                     columnHeaderTable.setAlignment(nextRow, 8, 2);
/* 590 */                     columnHeaderTable.setObject(nextRow, 8, "Comments");
/* 591 */                     columnHeaderTable.setAlignment(nextRow, 1, 2);
/*     */                     
/* 593 */                     columnHeaderTable.setRowFont(nextRow, new Font("Arial", 1, 8));
/*     */                     
/* 595 */                     hbandDate.addTable(columnHeaderTable, new Rectangle(0, 20, 800, 65));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                     
/* 601 */                     selections = (Vector)dateTable.get(dateName);
/* 602 */                     if (selections == null) {
/* 603 */                       selections = new Vector();
/*     */                     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                     
/* 610 */                     Object[] selectionsArray = selections.toArray();
/*     */                     
/* 612 */                     Arrays.sort(selectionsArray, new SelectionSelectionNumberComparator());
/*     */                     
/* 614 */                     Arrays.sort(selectionsArray, new SelectionTitleComparator());
/*     */                     
/* 616 */                     Arrays.sort(selectionsArray, new SelectionArtistComparator());
/*     */ 
/*     */                     
/* 619 */                     for (int i = 0; i < selectionsArray.length; i++) {
/*     */ 
/*     */ 
/*     */                       
/* 623 */                       if (count < recordCount / tenth) {
/*     */                         
/* 625 */                         count = recordCount / tenth;
/* 626 */                         sresponse = context.getResponse();
/* 627 */                         context.putDelivery("status", new String("start_report"));
/* 628 */                         int myPercent = count * 10;
/* 629 */                         if (myPercent > 90)
/* 630 */                           myPercent = 90; 
/* 631 */                         context.putDelivery("percent", new String(String.valueOf(myPercent)));
/* 632 */                         context.includeJSP("status.jsp", "hiddenFrame");
/* 633 */                         sresponse.setContentType("text/plain");
/* 634 */                         sresponse.flushBuffer();
/*     */                       } 
/*     */                       
/* 637 */                       recordCount++;
/*     */                       
/* 639 */                       Selection sel = (Selection)selectionsArray[i];
/*     */                       
/* 641 */                       nextRow = 0;
/* 642 */                       subTable = new DefaultTableLens(2, 9);
/*     */                       
/* 644 */                       subTable.setColWidth(0, 146);
/* 645 */                       subTable.setColWidth(1, 58);
/* 646 */                       subTable.setColWidth(2, 45);
/* 647 */                       subTable.setColWidth(3, 48);
/* 648 */                       subTable.setColWidth(4, 45);
/* 649 */                       subTable.setColWidth(5, 45);
/* 650 */                       subTable.setColWidth(6, 50);
/* 651 */                       subTable.setColWidth(7, 38);
/* 652 */                       subTable.setColWidth(8, 105);
/*     */ 
/*     */                       
/* 655 */                       String selectionNo = SelectionManager.getLookupObjectValue(sel.getPrefixID());
/* 656 */                       if (selectionNo == null)
/* 657 */                         selectionNo = ""; 
/* 658 */                       selectionNo = String.valueOf(selectionNo) + sel.getSelectionNo();
/*     */ 
/*     */                       
/* 661 */                       String price = "$0.00";
/* 662 */                       if (sel.getPriceCode() != null && 
/* 663 */                         sel.getPriceCode().getTotalCost() > 0.0F) {
/* 664 */                         price = "$" + MilestoneHelper.formatDollarPrice(sel.getPriceCode().getTotalCost());
/*     */                       }
/*     */                       
/* 667 */                       String comment = (sel.getSelectionComments() != null) ? sel.getSelectionComments() : "";
/*     */ 
/*     */                       
/* 670 */                       Schedule schedule = sel.getSchedule();
/*     */                       
/* 672 */                       String packageCopy = "";
/* 673 */                       String packageCopyDue = "";
/* 674 */                       String packageCopyComment = "";
/* 675 */                       String mastersApproved = "";
/* 676 */                       String mastersApprovedDue = "";
/* 677 */                       String mastersApprovedComment = "";
/* 678 */                       String coverArt = "";
/* 679 */                       String coverArtDue = "";
/* 680 */                       String coverArtComment = "";
/* 681 */                       String film = "";
/* 682 */                       String filmDue = "";
/* 683 */                       String filmComment = "";
/* 684 */                       String addDate = "";
/* 685 */                       String addDateDue = "";
/* 686 */                       String addDateComment = "";
/* 687 */                       String depot = "";
/* 688 */                       String depotDue = "";
/* 689 */                       String depotComment = "";
/* 690 */                       String solicitation = "";
/* 691 */                       String solicitationDue = "";
/* 692 */                       String solicitationComment = "";
/*     */                       
/* 694 */                       if (schedule != null) {
/*     */                         
/* 696 */                         Vector tasks = schedule.getTasks();
/*     */                         
/* 698 */                         if (tasks != null && tasks.size() > 0)
/*     */                         {
/* 700 */                           for (int j = 0; j < schedule.getTasks().size(); j++) {
/*     */                             
/* 702 */                             ScheduledTask task = (ScheduledTask)tasks.get(j);
/*     */                             
/* 704 */                             if (task != null && task.getDueDate() != null) {
/*     */                               
/* 706 */                               SimpleDateFormat dueDateFormatter = new SimpleDateFormat("M/d");
/* 707 */                               String dueDate = dueDateFormatter.format(task.getDueDate().getTime());
/* 708 */                               String completionDate = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*     */ 
/*     */                               
/* 711 */                               if (task.getScheduledTaskStatus().equals("N/A"))
/*     */                               {
/* 713 */                                 completionDate = task.getScheduledTaskStatus();
/*     */                               }
/*     */                               
/* 716 */                               String taskAbbrev = MilestoneHelper.getTaskAbbreivationNameById(task.getTaskAbbreviationID());
/* 717 */                               String taskComment = "";
/*     */                               
/* 719 */                               if (task.getComments() != null) {
/* 720 */                                 taskComment = task.getComments();
/*     */                               }
/* 722 */                               if (taskAbbrev.equalsIgnoreCase("FPC")) {
/*     */                                 
/* 724 */                                 packageCopy = completionDate;
/* 725 */                                 packageCopyDue = dueDate;
/* 726 */                                 packageCopyComment = taskComment;
/*     */                               }
/* 728 */                               else if (taskAbbrev.equalsIgnoreCase("MA")) {
/*     */                                 
/* 730 */                                 mastersApproved = completionDate;
/* 731 */                                 mastersApprovedDue = dueDate;
/* 732 */                                 mastersApprovedComment = taskComment;
/*     */                               }
/* 734 */                               else if (taskAbbrev.equalsIgnoreCase("CAA")) {
/*     */                                 
/* 736 */                                 coverArt = completionDate;
/* 737 */                                 coverArtDue = dueDate;
/* 738 */                                 coverArtComment = taskComment;
/*     */                               }
/* 740 */                               else if (taskAbbrev.equalsIgnoreCase("PFS")) {
/*     */                                 
/* 742 */                                 film = completionDate;
/* 743 */                                 filmDue = dueDate;
/* 744 */                                 filmComment = taskComment;
/*     */                               }
/* 746 */                               else if (taskAbbrev.equalsIgnoreCase("SOL")) {
/*     */                                 
/* 748 */                                 solicitation = completionDate;
/* 749 */                                 solicitationDue = dueDate;
/* 750 */                                 solicitationComment = taskComment;
/*     */                               }
/* 752 */                               else if (taskAbbrev.equalsIgnoreCase("DEPO")) {
/*     */                                 
/* 754 */                                 depot = completionDate;
/* 755 */                                 depotDue = dueDate;
/* 756 */                                 depotComment = taskComment;
/*     */                               }
/* 758 */                               else if (taskAbbrev.equalsIgnoreCase("ADD")) {
/*     */                                 
/* 760 */                                 addDate = completionDate;
/* 761 */                                 addDateDue = dueDate;
/* 762 */                                 addDateComment = taskComment;
/*     */                               } 
/*     */                             } 
/*     */                           } 
/*     */                         }
/*     */                       } 
/*     */ 
/*     */ 
/*     */ 
/*     */                       
/* 772 */                       String seventhColumn = "";
/* 773 */                       String seventhColumnDue = "";
/* 774 */                       String seventhColumnComment = "";
/*     */ 
/*     */                       
/* 777 */                       if (isFullLength) {
/*     */                         
/* 779 */                         seventhColumn = solicitation;
/* 780 */                         seventhColumnDue = solicitationDue;
/* 781 */                         seventhColumnComment = solicitationComment;
/*     */                       }
/*     */                       else {
/*     */                         
/* 785 */                         seventhColumn = addDate;
/* 786 */                         seventhColumnDue = addDateDue;
/* 787 */                         seventhColumnComment = addDateComment;
/*     */                       } 
/*     */                       
/* 790 */                       String[] checkStrings = { null, ((new String[2][0] = comment.trim()).valueOf(sel.getFlArtist()) + "\n" + sel.getTitle()).trim() };
/* 791 */                       int[] checkStringsLength = { 20, 40 };
/*     */                       
/* 793 */                       int addExtraLines = MilestoneHelper.lineCount(checkStrings, checkStringsLength);
/* 794 */                       String selectionNoPrice = String.valueOf(selectionNo) + "\n" + price;
/* 795 */                       for (int z = 0; z < addExtraLines; z++) {
/* 796 */                         selectionNoPrice = String.valueOf(selectionNoPrice) + "\n";
/*     */                       }
/*     */                       
/* 799 */                       subTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
/* 800 */                       subTable.setObject(nextRow, 0, String.valueOf(sel.getFlArtist()) + "\n" + sel.getTitle());
/* 801 */                       subTable.setSpan(nextRow, 0, new Dimension(1, 2));
/*     */                       
/* 803 */                       subTable.setObject(nextRow, 1, "Due Dates");
/* 804 */                       subTable.setFont(nextRow, 1, new Font("Arial", 1, 9));
/*     */                       
/* 806 */                       subTable.setObject(nextRow, 2, packageCopyDue);
/* 807 */                       subTable.setObject(nextRow, 3, mastersApprovedDue);
/* 808 */                       subTable.setObject(nextRow, 4, coverArtDue);
/* 809 */                       subTable.setObject(nextRow, 5, filmDue);
/* 810 */                       subTable.setObject(nextRow, 6, seventhColumnDue);
/* 811 */                       subTable.setObject(nextRow, 7, depotDue);
/* 812 */                       subTable.setObject(nextRow, 8, comment);
/*     */                       
/* 814 */                       subTable.setRowBorderColor(nextRow, Color.white);
/* 815 */                       subTable.setRowBorder(nextRow, 4097);
/*     */                       
/* 817 */                       subTable.setRowHeight(nextRow, 10);
/* 818 */                       subTable.setColAutoSize(true);
/*     */                       
/* 820 */                       subTable.setRowFont(nextRow, new Font("Arial", 1, 7));
/*     */ 
/*     */ 
/*     */ 
/*     */                       
/* 825 */                       subTable.setColBorderColor(COL_ROW_LINE_COLOR);
/* 826 */                       subTable.setColBorder(nextRow, -1, 4097);
/* 827 */                       subTable.setColBorder(nextRow, 0, 4097);
/* 828 */                       subTable.setColBorder(nextRow, 1, 4097);
/* 829 */                       subTable.setColBorder(nextRow, 2, 4097);
/* 830 */                       subTable.setColBorder(nextRow, 3, 4097);
/* 831 */                       subTable.setColBorder(nextRow, 4, 4097);
/* 832 */                       subTable.setColBorder(nextRow, 5, 4097);
/* 833 */                       subTable.setColBorder(nextRow, 6, 4097);
/* 834 */                       subTable.setColBorder(nextRow, 7, 4097);
/* 835 */                       subTable.setColBorder(nextRow, 8, 4097);
/*     */                       
/* 837 */                       subTable.setBackground(nextRow, 1, SHADED_ROW_COLOR);
/* 838 */                       subTable.setBackground(nextRow, 2, SHADED_ROW_COLOR);
/* 839 */                       subTable.setBackground(nextRow, 3, SHADED_ROW_COLOR);
/* 840 */                       subTable.setBackground(nextRow, 4, SHADED_ROW_COLOR);
/* 841 */                       subTable.setBackground(nextRow, 5, SHADED_ROW_COLOR);
/* 842 */                       subTable.setBackground(nextRow, 6, SHADED_ROW_COLOR);
/* 843 */                       subTable.setBackground(nextRow, 7, SHADED_ROW_COLOR);
/*     */                       
/* 845 */                       subTable.setAlignment(nextRow, 0, 8);
/* 846 */                       subTable.setAlignment(nextRow, 1, 10);
/* 847 */                       subTable.setAlignment(nextRow, 2, 10);
/* 848 */                       subTable.setAlignment(nextRow, 3, 10);
/* 849 */                       subTable.setAlignment(nextRow, 4, 10);
/* 850 */                       subTable.setAlignment(nextRow, 5, 10);
/* 851 */                       subTable.setAlignment(nextRow, 6, 10);
/* 852 */                       subTable.setAlignment(nextRow, 7, 10);
/* 853 */                       subTable.setAlignment(nextRow, 8, 10);
/*     */                       
/* 855 */                       subTable.setObject(nextRow + 1, 1, selectionNoPrice);
/* 856 */                       subTable.setObject(nextRow + 1, 2, String.valueOf(packageCopy) + "\n" + packageCopyComment);
/* 857 */                       subTable.setObject(nextRow + 1, 3, String.valueOf(mastersApproved) + "\n" + mastersApprovedComment);
/* 858 */                       subTable.setObject(nextRow + 1, 4, String.valueOf(coverArt) + "\n" + coverArtComment);
/* 859 */                       subTable.setObject(nextRow + 1, 5, String.valueOf(film) + "\n" + filmComment);
/* 860 */                       subTable.setObject(nextRow + 1, 6, String.valueOf(seventhColumn) + "\n" + seventhColumnComment);
/* 861 */                       subTable.setObject(nextRow + 1, 7, String.valueOf(depot) + "\n" + depotComment);
/* 862 */                       subTable.setRowFont(nextRow + 1, new Font("Arial", 0, 7));
/*     */                       
/* 864 */                       subTable.setFont(nextRow, 0, new Font("Arial", 0, 7));
/* 865 */                       subTable.setFont(nextRow, 8, new Font("Arial", 0, 7));
/*     */                       
/* 867 */                       subTable.setAlignment(nextRow + 1, 1, 10);
/* 868 */                       subTable.setAlignment(nextRow + 1, 2, 10);
/* 869 */                       subTable.setAlignment(nextRow + 1, 3, 10);
/* 870 */                       subTable.setAlignment(nextRow + 1, 4, 10);
/* 871 */                       subTable.setAlignment(nextRow + 1, 5, 10);
/* 872 */                       subTable.setAlignment(nextRow + 1, 6, 10);
/* 873 */                       subTable.setAlignment(nextRow + 1, 7, 10);
/*     */ 
/*     */ 
/*     */ 
/*     */                       
/* 878 */                       subTable.setColBorderColor(COL_ROW_LINE_COLOR);
/* 879 */                       subTable.setColBorder(nextRow + 1, -1, 4097);
/* 880 */                       subTable.setColBorder(nextRow + 1, 0, 4097);
/* 881 */                       subTable.setColBorder(nextRow + 1, 1, 4097);
/* 882 */                       subTable.setColBorder(nextRow + 1, 2, 4097);
/* 883 */                       subTable.setColBorder(nextRow + 1, 3, 4097);
/* 884 */                       subTable.setColBorder(nextRow + 1, 4, 4097);
/* 885 */                       subTable.setColBorder(nextRow + 1, 5, 4097);
/* 886 */                       subTable.setColBorder(nextRow + 1, 6, 4097);
/* 887 */                       subTable.setColBorder(nextRow + 1, 7, 4097);
/* 888 */                       subTable.setColBorder(nextRow + 1, 8, 4097);
/* 889 */                       subTable.setSpan(nextRow, 8, new Dimension(1, 2));
/*     */                       
/* 891 */                       subTable.setRowBorderColor(nextRow - 1, COL_ROW_LINE_COLOR);
/* 892 */                       subTable.setRowBorderColor(nextRow, COL_ROW_LINE_COLOR);
/* 893 */                       subTable.setRowBorderColor(nextRow + 1, COL_ROW_LINE_COLOR);
/* 894 */                       subTable.setRowBorder(nextRow + 1, 4097);
/*     */                       
/* 896 */                       body = new SectionBand(report);
/* 897 */                       body.addTable(subTable, new Rectangle(800, 800));
/* 898 */                       body.setHeight(2.0F);
/* 899 */                       body.setBottomBorder(0);
/* 900 */                       body.setTopBorder(0);
/* 901 */                       body.setShrinkToFit(true);
/* 902 */                       body.setVisible(true);
/*     */                       
/* 904 */                       group = new DefaultSectionLens(null, group, body);
/*     */                     } 
/* 906 */                     group = new DefaultSectionLens(null, group, spacer);
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */             } 
/* 911 */             group = new DefaultSectionLens(hbandType, group, null);
/* 912 */             report.addSection(group, rowCountTable);
/* 913 */             report.addPageBreak();
/* 914 */             group = null;
/*     */           }
/*     */         
/*     */         } 
/*     */       } 
/* 919 */     } catch (Exception e) {
/*     */       
/* 921 */       System.out.println(">>>>>>>>ReportHandler.fillDreamWksNashProdScheduleForPrint(): exception: " + e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\DreamWksNashProdSchForPrintSubHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */