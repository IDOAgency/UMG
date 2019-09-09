/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.ComponentLog;
/*     */ import com.techempower.gemini.Context;
/*     */ import com.techempower.gemini.GeminiApplication;
/*     */ import com.universal.milestone.Company;
/*     */ import com.universal.milestone.DatePeriod;
/*     */ import com.universal.milestone.Family;
/*     */ import com.universal.milestone.Form;
/*     */ import com.universal.milestone.ImpactDate;
/*     */ import com.universal.milestone.IntegerComparator;
/*     */ import com.universal.milestone.MCACustomImpactScheduleForPrintSubHandler;
/*     */ import com.universal.milestone.MilestoneHelper;
/*     */ import com.universal.milestone.MilestoneHelper_2;
/*     */ import com.universal.milestone.ProductCategory;
/*     */ import com.universal.milestone.ReleaseType;
/*     */ import com.universal.milestone.SecureHandler;
/*     */ import com.universal.milestone.Selection;
/*     */ import com.universal.milestone.SelectionArtistComparator;
/*     */ import com.universal.milestone.SelectionImpactDateComparator;
/*     */ import com.universal.milestone.SelectionManager;
/*     */ import com.universal.milestone.SelectionSelectionNumberComparator;
/*     */ import com.universal.milestone.SelectionStreetDateComparator;
/*     */ import com.universal.milestone.SelectionSubConfiguration;
/*     */ import com.universal.milestone.SelectionTitleComparator;
/*     */ import com.universal.milestone.StringComparator;
/*     */ import com.universal.milestone.StringReverseComparator;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCACustomImpactScheduleForPrintSubHandler
/*     */   extends SecureHandler
/*     */ {
/*     */   public static final String COMPONENT_CODE = "hCProd";
/*     */   public GeminiApplication application;
/*     */   public ComponentLog log;
/*     */   public static Context m_context;
/*     */   public static Calendar beginStDate;
/*     */   public static Calendar endStDate;
/*     */   
/*     */   public void MCACustomImpactScheduleForPrintSubHandler(GeminiApplication application) {
/*  89 */     this.application = application;
/*  90 */     this.log = application.getLog("hCProd");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  98 */   public String getDescription() { return "Sub Report"; }
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
/*     */   protected static void fillMCACustomImpactScheduleForPrint(XStyleSheet report, Context context) {
/* 110 */     Color SHADED_AREA_COLOR = Color.lightGray;
/* 111 */     int COL_LINE_STYLE = 4097;
/* 112 */     int HEADER_FONT_SIZE = 12;
/* 113 */     int NUM_COLUMNS = 8;
/*     */     
/* 115 */     double ldLineVal = 0.3D;
/*     */     
/* 117 */     SectionBand hbandType = new SectionBand(report);
/* 118 */     SectionBand hbandCategory = new SectionBand(report);
/* 119 */     SectionBand hbandDate = new SectionBand(report);
/* 120 */     SectionBand body = new SectionBand(report);
/* 121 */     SectionBand footer = new SectionBand(report);
/* 122 */     SectionBand spacer = new SectionBand(report);
/* 123 */     SectionBand selectionSpacer = new SectionBand(report);
/* 124 */     DefaultSectionLens group = null;
/* 125 */     m_context = context;
/*     */     
/* 127 */     footer.setVisible(true);
/* 128 */     footer.setHeight(0.1F);
/* 129 */     footer.setShrinkToFit(false);
/* 130 */     footer.setBottomBorder(0);
/*     */     
/* 132 */     spacer.setVisible(true);
/* 133 */     spacer.setHeight(0.05F);
/* 134 */     spacer.setShrinkToFit(false);
/* 135 */     spacer.setBottomBorder(0);
/*     */     
/* 137 */     selectionSpacer.setVisible(true);
/* 138 */     selectionSpacer.setHeight(0.03F);
/* 139 */     selectionSpacer.setShrinkToFit(false);
/* 140 */     selectionSpacer.setBottomBorder(0);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 145 */       HttpServletResponse sresponse = context.getResponse();
/* 146 */       context.putDelivery("status", new String("start_gathering"));
/* 147 */       context.includeJSP("status.jsp", "hiddenFrame");
/* 148 */       sresponse.setContentType("text/plain");
/* 149 */       sresponse.flushBuffer();
/*     */     }
/* 151 */     catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 156 */     Vector initialSelections = MilestoneHelper.getSelectionsForReport(context);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 161 */       HttpServletResponse sresponse = context.getResponse();
/* 162 */       context.putDelivery("status", new String("start_report"));
/* 163 */       context.putDelivery("percent", new String("10"));
/* 164 */       context.includeJSP("status.jsp", "hiddenFrame");
/* 165 */       sresponse.setContentType("text/plain");
/* 166 */       sresponse.flushBuffer();
/*     */     }
/* 168 */     catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 176 */       DefaultTableLens table_contents = null;
/* 177 */       DefaultTableLens rowCountTable = null;
/* 178 */       DefaultTableLens columnHeaderTable = null;
/* 179 */       DefaultTableLens subTable = null;
/* 180 */       DefaultTableLens monthTableLens = null;
/* 181 */       DefaultTableLens configTableLens = null;
/* 182 */       DefaultTableLens dateTableLens = null;
/*     */       
/* 184 */       rowCountTable = new DefaultTableLens(2, 10000);
/*     */ 
/*     */       
/* 187 */       int nextRow = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 192 */       Form reportForm = (Form)context.getSessionValue("reportForm");
/*     */       
/* 194 */       beginStDate = (reportForm.getStringValue("beginDate") != null && 
/* 195 */         reportForm.getStringValue("beginDate").length() > 0) ? 
/* 196 */         MilestoneHelper.getDate(reportForm.getStringValue("beginDate")) : null;
/*     */       
/* 198 */       endStDate = (reportForm.getStringValue("endDate") != null && 
/* 199 */         reportForm.getStringValue("endDate").length() > 0) ? 
/* 200 */         MilestoneHelper.getDate(reportForm.getStringValue("endDate")) : null;
/*     */       
/* 202 */       report.setElement("crs_startdate", MilestoneHelper.getFormatedDate(beginStDate));
/* 203 */       report.setElement("crs_enddate", MilestoneHelper.getFormatedDate(endStDate));
/*     */       
/* 205 */       SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
/* 206 */       String todayLong = formatter.format(new Date());
/* 207 */       report.setElement("crs_bottomdate", todayLong);
/*     */       
/* 209 */       Vector selections = constructSelectionsWithImpactDates(initialSelections);
/*     */ 
/*     */       
/* 212 */       Hashtable selTable = groupSelectionsByHeaderType(selections);
/*     */       
/* 214 */       Enumeration CompanyEnum = selTable.keys();
/* 215 */       Vector CompanyVector = new Vector();
/*     */       
/* 217 */       while (CompanyEnum.hasMoreElements()) {
/* 218 */         CompanyVector.addElement(CompanyEnum.nextElement());
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 223 */       int numSelections = 0;
/*     */ 
/*     */       
/* 226 */       for (int i = 0; i < CompanyVector.size(); i++) {
/*     */ 
/*     */ 
/*     */         
/* 230 */         String aCompany = (CompanyVector.elementAt(i) != null) ? (String)CompanyVector.elementAt(i) : "";
/*     */         
/* 232 */         Hashtable subconfigTable = (Hashtable)selTable.get(aCompany);
/* 233 */         Enumeration releasesEnum = subconfigTable.keys();
/* 234 */         Vector releasesVector = new Vector();
/* 235 */         while (releasesEnum.hasMoreElements()) {
/* 236 */           releasesVector.addElement(releasesEnum.nextElement());
/*     */         }
/*     */ 
/*     */         
/* 240 */         for (int k = 0; k < releasesVector.size(); k++) {
/*     */           
/* 242 */           String aRelease = (String)releasesVector.elementAt(k);
/*     */           
/* 244 */           Hashtable months = (Hashtable)subconfigTable.get(aRelease);
/* 245 */           Enumeration monthEnum = months.keys();
/* 246 */           Vector monthVector = new Vector();
/* 247 */           while (monthEnum.hasMoreElements()) {
/* 248 */             monthVector.addElement(monthEnum.nextElement());
/*     */           }
/*     */ 
/*     */           
/* 252 */           for (int j = 0; j < monthVector.size(); j++) {
/*     */ 
/*     */             
/* 255 */             String aMonth = (String)monthVector.elementAt(j);
/*     */             
/* 257 */             Vector selectVector = (Vector)months.get(aMonth);
/*     */ 
/*     */             
/* 260 */             numSelections += selectVector.size();
/*     */           } 
/*     */         } 
/*     */       } 
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
/* 285 */       int numColumns = 8;
/*     */ 
/*     */       
/* 288 */       nextRow = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 294 */       Object[] CompanyArray = CompanyVector.toArray();
/* 295 */       Arrays.sort(CompanyArray, new StringComparator());
/*     */       
/* 297 */       for (int n = 0; n < CompanyArray.length; n++) {
/*     */         
/* 299 */         String company = (String)CompanyArray[n];
/* 300 */         String companyHeaderText = !company.trim().equals("") ? company : "Other";
/*     */ 
/*     */         
/* 303 */         Hashtable subconfigTable = (Hashtable)selTable.get(company);
/* 304 */         if (subconfigTable != null) {
/*     */           
/* 306 */           Enumeration subconfigs = subconfigTable.keys();
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 311 */           Vector subconfigVector = new Vector();
/*     */           
/* 313 */           while (subconfigs.hasMoreElements()) {
/* 314 */             subconfigVector.add((String)subconfigs.nextElement());
/*     */           }
/* 316 */           Object[] subconfigsArray = subconfigVector.toArray();
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 321 */           Arrays.sort(subconfigsArray, new StringReverseComparator());
/*     */ 
/*     */           
/* 324 */           for (int scIndex = 0; scIndex < subconfigsArray.length; scIndex++) {
/*     */             
/* 326 */             String subconfig = (String)subconfigsArray[scIndex];
/*     */             
/* 328 */             nextRow = 0;
/*     */             
/* 330 */             columnHeaderTable = new DefaultTableLens(1, 8);
/*     */ 
/*     */             
/* 333 */             if (!subconfig.equalsIgnoreCase("Discard")) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 339 */               if (subconfig.equalsIgnoreCase("Release with Impact Dates")) {
/* 340 */                 columnHeaderTable.setColWidth(0, 50);
/* 341 */                 columnHeaderTable.setColWidth(1, 130);
/* 342 */                 columnHeaderTable.setColWidth(2, 50);
/* 343 */                 columnHeaderTable.setColWidth(3, 120);
/* 344 */                 columnHeaderTable.setColWidth(4, 70);
/* 345 */                 columnHeaderTable.setColWidth(5, 50);
/* 346 */                 columnHeaderTable.setColWidth(6, 50);
/* 347 */                 columnHeaderTable.setColWidth(7, 50);
/*     */               } else {
/* 349 */                 columnHeaderTable.setColWidth(0, 60);
/* 350 */                 columnHeaderTable.setColWidth(1, 160);
/* 351 */                 columnHeaderTable.setColWidth(2, 60);
/* 352 */                 columnHeaderTable.setColWidth(3, 80);
/* 353 */                 columnHeaderTable.setColWidth(4, 10);
/* 354 */                 columnHeaderTable.setColWidth(5, 30);
/* 355 */                 columnHeaderTable.setColWidth(6, 60);
/* 356 */                 columnHeaderTable.setColWidth(7, 50);
/*     */               } 
/*     */               
/* 359 */               columnHeaderTable.setColBorder(0);
/* 360 */               columnHeaderTable.setRowBorder(nextRow - 1, 0);
/* 361 */               columnHeaderTable.setRowBorder(nextRow, 4097);
/* 362 */               columnHeaderTable.setRowBorderColor(nextRow, Color.black);
/*     */ 
/*     */ 
/*     */               
/* 366 */               columnHeaderTable.setAlignment(nextRow, 0, 16);
/* 367 */               columnHeaderTable.setAlignment(nextRow, 1, 16);
/* 368 */               columnHeaderTable.setAlignment(nextRow, 3, 18);
/* 369 */               if (subconfig.equalsIgnoreCase("Release with Impact Dates")) {
/* 370 */                 columnHeaderTable.setAlignment(nextRow, 4, 18);
/*     */               }
/*     */               
/* 373 */               columnHeaderTable.setObject(nextRow, 0, "\nArtist");
/* 374 */               columnHeaderTable.setObject(nextRow, 1, "\nTitle");
/* 375 */               columnHeaderTable.setObject(nextRow, 2, "Configuration");
/* 376 */               columnHeaderTable.setObject(nextRow, 3, "Local Prod #\nUPC");
/* 377 */               if (subconfig.equalsIgnoreCase("Release with Impact Dates")) {
/* 378 */                 columnHeaderTable.setObject(nextRow, 4, "Impact\nDate");
/* 379 */                 columnHeaderTable.setObject(nextRow, 5, "Format");
/*     */               } else {
/* 381 */                 columnHeaderTable.setObject(nextRow, 4, "");
/* 382 */                 columnHeaderTable.setObject(nextRow, 5, "");
/*     */               } 
/*     */               
/* 385 */               if (subconfig.equalsIgnoreCase("Release with Impact Dates")) {
/* 386 */                 columnHeaderTable.setObject(nextRow, 6, "In House/\nStreet Date");
/* 387 */               } else if (subconfig.equalsIgnoreCase("Promo without Impact Dates")) {
/* 388 */                 columnHeaderTable.setObject(nextRow, 6, "In House");
/* 389 */               } else if (subconfig.equalsIgnoreCase("Commercial Singles")) {
/* 390 */                 columnHeaderTable.setObject(nextRow, 6, "Street Date");
/*     */               } 
/*     */               
/* 393 */               columnHeaderTable.setObject(nextRow, 7, "Release\nWeek");
/*     */               
/* 395 */               columnHeaderTable.setRowFont(nextRow, new Font("Arial", 3, 10));
/* 396 */               columnHeaderTable.setRowBorder(nextRow, 4097);
/* 397 */               columnHeaderTable.setRowBorderColor(nextRow, Color.black);
/*     */               
/* 399 */               footer.setVisible(true);
/* 400 */               footer.setHeight(0.1F);
/* 401 */               footer.setShrinkToFit(false);
/* 402 */               footer.setBottomBorder(0);
/*     */ 
/*     */               
/* 405 */               configTableLens = new DefaultTableLens(1, 8);
/*     */ 
/*     */               
/* 408 */               Hashtable monthTable = (Hashtable)subconfigTable.get(subconfig);
/*     */               
/* 410 */               if (monthTable != null)
/*     */               {
/* 412 */                 Enumeration monthSort = monthTable.keys();
/*     */                 
/* 414 */                 Vector monthVector = new Vector();
/*     */ 
/*     */                 
/* 417 */                 while (monthSort.hasMoreElements()) {
/* 418 */                   monthVector.add((String)monthSort.nextElement());
/*     */                 }
/* 420 */                 Object[] monthArray = (Object[])null;
/*     */                 
/* 422 */                 monthArray = monthVector.toArray();
/*     */                 
/* 424 */                 Arrays.sort(monthArray, new IntegerComparator());
/*     */                 
/* 426 */                 for (int mIndex = 0; mIndex < monthArray.length; mIndex++)
/*     */                 {
/* 428 */                   String monthName = (String)monthArray[mIndex];
/* 429 */                   String monthNameString = monthName;
/*     */ 
/*     */                   
/*     */                   try {
/* 433 */                     monthNameString = MONTHS[Integer.parseInt(monthName) - 1];
/*     */                   }
/* 435 */                   catch (Exception e) {
/*     */                     
/* 437 */                     monthNameString = "No street date";
/*     */                   } 
/*     */ 
/*     */ 
/*     */                   
/* 442 */                   nextRow = 0;
/*     */                   
/* 444 */                   configTableLens.setAlignment(nextRow, 0, 2);
/* 445 */                   configTableLens.setSpan(nextRow, 0, new Dimension(8, 1));
/* 446 */                   configTableLens.setObject(nextRow, 0, subconfig);
/* 447 */                   configTableLens.setRowFont(nextRow, new Font("Arial", 3, 12));
/*     */                   
/* 449 */                   monthTableLens = new DefaultTableLens(1, 8);
/* 450 */                   monthTableLens.setSpan(nextRow, 0, new Dimension(8, 1));
/* 451 */                   monthTableLens.setObject(nextRow, 0, monthNameString);
/* 452 */                   monthTableLens.setRowHeight(nextRow, 14);
/* 453 */                   monthTableLens.setRowFont(nextRow, new Font("Arial", 1, 12));
/* 454 */                   monthTableLens.setRowBackground(nextRow, SHADED_AREA_COLOR);
/* 455 */                   monthTableLens.setRowForeground(nextRow, Color.black);
/* 456 */                   monthTableLens.setRowBorderColor(nextRow, Color.white);
/* 457 */                   monthTableLens.setRowBorderColor(nextRow - 1, Color.white);
/* 458 */                   monthTableLens.setColBorderColor(nextRow, -1, Color.white);
/* 459 */                   monthTableLens.setColBorderColor(nextRow, 0, Color.white);
/* 460 */                   monthTableLens.setColBorderColor(nextRow, 14, Color.white);
/*     */ 
/*     */ 
/*     */                   
/* 464 */                   hbandType = new SectionBand(report);
/* 465 */                   hbandType.setHeight(1.23F);
/* 466 */                   hbandType.setShrinkToFit(true);
/* 467 */                   hbandType.setVisible(true);
/*     */ 
/*     */ 
/*     */                   
/* 471 */                   hbandType.addTable(columnHeaderTable, new Rectangle(0, 20, 800, 25));
/*     */                   
/* 473 */                   hbandType.addTable(configTableLens, new Rectangle(0, 50, 800, 25));
/*     */ 
/*     */                   
/* 476 */                   hbandType.addTable(monthTableLens, new Rectangle(0, 70, 800, 25));
/*     */ 
/*     */ 
/*     */ 
/*     */                   
/* 481 */                   selections = (Vector)monthTable.get(monthName);
/*     */                   
/* 483 */                   if (selections == null) {
/* 484 */                     selections = new Vector();
/*     */                   }
/*     */                   
/* 487 */                   Object[] selectionsArray = selections.toArray();
/*     */                   
/* 489 */                   if (subconfig.equalsIgnoreCase("Promo without Impact Dates") || subconfig.equalsIgnoreCase("Commercial Singles")) {
/*     */ 
/*     */ 
/*     */                     
/* 493 */                     Arrays.sort(selectionsArray, new SelectionArtistComparator());
/*     */                     
/* 495 */                     Arrays.sort(selectionsArray, new SelectionStreetDateComparator());
/*     */ 
/*     */                   
/*     */                   }
/*     */                   else {
/*     */ 
/*     */                     
/* 502 */                     Arrays.sort(selectionsArray, new SelectionSelectionNumberComparator());
/*     */                     
/* 504 */                     Arrays.sort(selectionsArray, new SelectionTitleComparator());
/*     */                     
/* 506 */                     Arrays.sort(selectionsArray, new SelectionArtistComparator());
/*     */                     
/* 508 */                     Arrays.sort(selectionsArray, new SelectionImpactDateComparator());
/*     */                   } 
/*     */ 
/*     */ 
/*     */                   
/* 513 */                   int count = 2;
/* 514 */                   int numRec = selections.size();
/* 515 */                   int chunkSize = numRec / 10;
/*     */ 
/*     */                   
/* 518 */                   for (int i = 0; i < selectionsArray.length; i++) {
/*     */                     String selectionNumber;
/*     */ 
/*     */                     
/*     */                     try {
/* 523 */                       int myPercent = i / chunkSize;
/* 524 */                       if (myPercent > 1 && myPercent < 10)
/* 525 */                         count = myPercent; 
/* 526 */                       selectionNumber = context.getResponse();
/* 527 */                       context.putDelivery("status", new String("start_report"));
/* 528 */                       context.putDelivery("percent", new String(String.valueOf(count * 10)));
/* 529 */                       context.includeJSP("status.jsp", "hiddenFrame");
/* 530 */                       selectionNumber.setContentType("text/plain");
/* 531 */                       selectionNumber.flushBuffer();
/*     */                     }
/* 533 */                     catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */ 
/*     */                     
/* 538 */                     Selection sel = (Selection)selectionsArray[i];
/*     */                     
/* 540 */                     nextRow = 0;
/* 541 */                     subTable = new DefaultTableLens(2, 8);
/* 542 */                     subTable.setRowBorderColor(Color.lightGray);
/*     */ 
/*     */                     
/* 545 */                     subTable.setColWidth(0, 50);
/* 546 */                     subTable.setColWidth(1, 150);
/* 547 */                     subTable.setColWidth(2, 75);
/* 548 */                     subTable.setColWidth(3, 75);
/* 549 */                     if (subconfig.equalsIgnoreCase("Release with Impact Dates")) {
/* 550 */                       subTable.setColWidth(4, 50);
/* 551 */                       subTable.setColWidth(5, 50);
/*     */                     } else {
/* 553 */                       subTable.setColWidth(4, 1);
/* 554 */                       subTable.setColWidth(5, 1);
/*     */                     } 
/* 556 */                     subTable.setColWidth(6, 50);
/* 557 */                     subTable.setColWidth(7, 50);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                     
/* 563 */                     if (SelectionManager.getLookupObjectValue(sel.getPrefixID()).equals("")) {
/* 564 */                       selectionNumber = sel.getSelectionNo();
/*     */                     } else {
/* 566 */                       selectionNumber = String.valueOf(SelectionManager.getLookupObjectValue(sel.getPrefixID())) + "-" + sel.getSelectionNo();
/*     */                     } 
/*     */                     
/* 569 */                     String upc = (sel.getUpc() != null) ? sel.getUpc() : " ";
/*     */ 
/*     */                     
/* 572 */                     upc = MilestoneHelper_2.getRMSReportFormat(upc, "UPC", sel.getIsDigital());
/*     */ 
/*     */                     
/* 575 */                     String price = "0.00";
/* 576 */                     if (sel.getPriceCode() != null && 
/* 577 */                       sel.getPriceCode().getTotalCost() > 0.0F) {
/* 578 */                       price = MilestoneHelper.formatDollarPrice(sel.getPriceCode().getTotalCost());
/*     */                     }
/*     */                     
/* 581 */                     String streetDate = MilestoneHelper.getFormatedDate(sel.getStreetDate());
/*     */                     
/* 583 */                     String status = (sel.getSelectionStatus() != null && sel.getSelectionStatus().getName() != null) ? 
/* 584 */                       sel.getSelectionStatus().getName() : "";
/*     */                     
/* 586 */                     if (status.equalsIgnoreCase("TBS")) {
/* 587 */                       streetDate = "TBS " + streetDate;
/*     */                     }
/* 589 */                     else if (status.equalsIgnoreCase("In The Works")) {
/* 590 */                       streetDate = "ITW " + streetDate;
/*     */                     } 
/*     */                     
/* 593 */                     String comment = (sel.getSelectionComments() != null) ? sel.getSelectionComments() : "";
/*     */ 
/*     */                     
/* 596 */                     DatePeriod dp = MilestoneHelper.getReleaseWeek(sel);
/* 597 */                     String selReleaseWeek = (dp != null) ? dp.getName() : "";
/*     */ 
/*     */                     
/* 600 */                     subTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
/*     */                     
/* 602 */                     subTable.setFont(nextRow, 0, new Font("Arial", 1, 8));
/* 603 */                     subTable.setObject(nextRow, 0, sel.getArtist());
/* 604 */                     subTable.setObject(nextRow, 1, sel.getTitle());
/* 605 */                     subTable.setObject(nextRow, 2, sel.getSelectionConfig().getSelectionConfigurationAbbreviation());
/* 606 */                     subTable.setObject(nextRow, 3, String.valueOf(selectionNumber) + "\n" + upc);
/*     */                     
/* 608 */                     if (sel.getImpactDates().size() != 0) {
/*     */                       
/* 610 */                       ImpactDate theImpactDate = sel.getImpactDateObject();
/* 611 */                       Calendar cals = theImpactDate.getImpactDate();
/* 612 */                       if (!theImpactDate.getTbi()) {
/* 613 */                         subTable.setObject(nextRow, 4, MilestoneHelper.getCustomFormatedDate(cals, "MM/dd"));
/*     */                       } else {
/* 615 */                         subTable.setObject(nextRow, 4, "TBI");
/*     */                       } 
/* 617 */                       subTable.setObject(nextRow, 5, theImpactDate.getFormatDescription());
/*     */                     } else {
/*     */                       
/* 620 */                       subTable.setObject(nextRow, 4, "");
/* 621 */                       subTable.setObject(nextRow, 5, "");
/*     */                     } 
/*     */                     
/* 624 */                     subTable.setObject(nextRow, 6, MilestoneHelper.getCustomFormatedDate(sel.getStreetDate(), "MM/dd"));
/* 625 */                     subTable.setObject(nextRow, 7, selReleaseWeek);
/*     */                     
/* 627 */                     subTable.setColAlignment(0, 9);
/* 628 */                     subTable.setSpan(nextRow, 0, new Dimension(1, 2));
/* 629 */                     subTable.setColAlignment(1, 33);
/* 630 */                     subTable.setColAlignment(2, 9);
/* 631 */                     subTable.setSpan(nextRow, 2, new Dimension(1, 2));
/* 632 */                     subTable.setColAlignment(3, 9);
/* 633 */                     subTable.setSpan(nextRow, 3, new Dimension(1, 2));
/* 634 */                     subTable.setColAlignment(4, 10);
/* 635 */                     subTable.setSpan(nextRow, 4, new Dimension(1, 2));
/* 636 */                     subTable.setColAlignment(5, 10);
/* 637 */                     subTable.setSpan(nextRow, 5, new Dimension(1, 2));
/* 638 */                     subTable.setColAlignment(6, 10);
/* 639 */                     subTable.setSpan(nextRow, 6, new Dimension(1, 2));
/* 640 */                     subTable.setColAlignment(7, 10);
/* 641 */                     subTable.setSpan(nextRow, 7, new Dimension(1, 2));
/*     */                     
/* 643 */                     subTable.setRowBorder(nextRow, 1, 0);
/* 644 */                     subTable.setRowBorder(nextRow - 1, 0);
/* 645 */                     subTable.setRowFont(nextRow, new Font("Arial", 0, 8));
/*     */                     
/* 647 */                     subTable.setRowAutoSize(true);
/*     */                     
/* 649 */                     nextRow++;
/*     */                     
/* 651 */                     subTable.setAlignment(nextRow, 1, 9);
/*     */                     
/* 653 */                     String[] checkStrings = { sel.getArtist(), sel.getTitle() };
/* 654 */                     int[] checkStringsLength = { 20, 40 };
/* 655 */                     int addExtraLines = MilestoneHelper.lineCount(checkStrings, checkStringsLength);
/*     */ 
/*     */                     
/* 658 */                     String adder = "";
/* 659 */                     for (int z = 0; z < addExtraLines - 1; z++) {
/* 660 */                       adder = String.valueOf(adder) + "\n";
/*     */                     }
/* 662 */                     subTable.setObject(nextRow, 0, "");
/* 663 */                     subTable.setObject(nextRow, 1, adder);
/* 664 */                     subTable.setObject(nextRow, 2, "");
/* 665 */                     subTable.setObject(nextRow, 3, "");
/* 666 */                     subTable.setObject(nextRow, 4, "");
/* 667 */                     subTable.setObject(nextRow, 5, "");
/* 668 */                     subTable.setObject(nextRow, 6, "");
/* 669 */                     subTable.setObject(nextRow, 7, "");
/*     */                     
/* 671 */                     subTable.setRowFont(nextRow, new Font("Arial", 2, 8));
/* 672 */                     subTable.setColAlignment(1, 9);
/* 673 */                     subTable.setRowBorderColor(nextRow, Color.lightGray);
/* 674 */                     subTable.setRowBorderColor(nextRow - 1, Color.lightGray);
/* 675 */                     subTable.setRowBorderColor(nextRow + 1, Color.lightGray);
/* 676 */                     subTable.setColBorder(0);
/*     */                     
/* 678 */                     body = new SectionBand(report);
/*     */                     
/* 680 */                     double lfLineCount = 1.5D;
/*     */                     
/* 682 */                     if (addExtraLines > 0) {
/*     */                       
/* 684 */                       body.setHeight(1.5F);
/*     */                     }
/*     */                     else {
/*     */                       
/* 688 */                       body.setHeight(0.8F);
/*     */                     } 
/*     */                     
/* 691 */                     if (addExtraLines > 3) {
/*     */                       
/* 693 */                       if (lfLineCount < addExtraLines * 0.3D) {
/* 694 */                         lfLineCount = addExtraLines * 0.3D;
/*     */                       }
/* 696 */                       body.setHeight((float)lfLineCount);
/*     */                     }
/*     */                     else {
/*     */                       
/* 700 */                       body.setHeight(0.8F);
/*     */                     } 
/*     */                     
/* 703 */                     body.addTable(subTable, new Rectangle(800, 800));
/* 704 */                     body.setBottomBorder(0);
/* 705 */                     body.setTopBorder(0);
/* 706 */                     body.setShrinkToFit(true);
/* 707 */                     body.setVisible(true);
/* 708 */                     group = new DefaultSectionLens(null, group, body);
/*     */                   } 
/*     */ 
/*     */ 
/*     */                   
/* 713 */                   group = new DefaultSectionLens(hbandType, group, null);
/* 714 */                   report.addSection(group, rowCountTable);
/* 715 */                   report.addPageBreak();
/* 716 */                   group = null;
/*     */                 }
/*     */               
/*     */               }
/*     */             
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 725 */     } catch (Exception e) {
/* 726 */       System.out.println(">>>>>>>>ReportHandler.fillMCACustomImpactScheduleForPrint(): exception: " + e);
/*     */     } 
/*     */   }
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
/*     */   public static Hashtable groupSelectionsByHeaderType(Vector selections) {
/* 745 */     Hashtable groupedByHeaderTypes = new Hashtable();
/*     */     
/* 747 */     if (selections == null) {
/* 748 */       return groupedByHeaderTypes;
/*     */     }
/* 750 */     Form reportForm = (Form)m_context.getSessionValue("reportForm");
/*     */ 
/*     */ 
/*     */     
/* 754 */     for (int i = 0; i < selections.size(); i++) {
/*     */       
/* 756 */       Selection sel = (Selection)selections.elementAt(i);
/* 757 */       if (sel != null) {
/*     */ 
/*     */         
/* 760 */         String ReleaseTypeInd = "";
/*     */         
/* 762 */         String familyName = "";
/* 763 */         String companyName = "";
/*     */         
/* 765 */         String productCatString = "";
/* 766 */         String subConfigString = "";
/* 767 */         String releaseTypeString = "";
/* 768 */         String configString = "";
/*     */         
/* 770 */         Family family = sel.getFamily();
/* 771 */         Company company = sel.getCompany();
/*     */         
/* 773 */         ProductCategory productCat = sel.getProductCategory();
/* 774 */         SelectionSubConfiguration subConfig = sel.getSelectionSubConfig();
/* 775 */         ReleaseType releaseType = sel.getReleaseType();
/*     */         
/* 777 */         if (family != null)
/* 778 */           familyName = (family.getName() == null) ? "" : family.getName(); 
/* 779 */         if (company != null) {
/* 780 */           companyName = (company.getName() == null) ? "" : company.getName();
/*     */         }
/* 782 */         productCatString = productCat.getName();
/* 783 */         releaseTypeString = releaseType.getName();
/* 784 */         subConfigString = subConfig.getSelectionSubConfigurationName();
/* 785 */         configString = (sel.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation() != null) ? sel.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation() : "";
/*     */         
/* 787 */         if (productCatString.equals("New Release") && sel.getImpactDates().size() != 0) {
/*     */ 
/*     */           
/* 790 */           ReleaseTypeInd = "Release with Impact Dates";
/*     */         }
/* 792 */         else if (releaseTypeString.equals("Promotional") && sel.getImpactDates().size() == 0) {
/*     */ 
/*     */           
/* 795 */           ReleaseTypeInd = "Promo without Impact Dates";
/*     */         }
/* 797 */         else if (releaseTypeString.equals("Commercial") && sel.getImpactDates().size() == 0) {
/*     */ 
/*     */           
/* 800 */           ReleaseTypeInd = "Commercial Singles";
/*     */         } 
/*     */         
/* 803 */         if (productCatString.equals("Advances") && sel.getImpactDates().size() != 0)
/*     */         {
/*     */           
/* 806 */           ReleaseTypeInd = "Release with Impact Dates";
/*     */         }
/*     */ 
/*     */         
/* 810 */         if ((subConfigString.equals("Full Length") || subConfigString.equalsIgnoreCase("DUALDISC")) && sel.getImpactDates().size() == 0) {
/* 811 */           ReleaseTypeInd = "Discard";
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 816 */         if (configString.equals("DVDVID") || configString.equals("DVDAUD") || 
/* 817 */           configString.equals("VIDEO") || configString.equals("SACD")) {
/* 818 */           ReleaseTypeInd = "Discard";
/*     */         }
/*     */ 
/*     */         
/* 822 */         if (sel != null && sel.getSelectionConfig() != null && 
/* 823 */           sel.getSelectionConfig().getSelectionConfigurationName().equals("SACD"))
/*     */         {
/* 825 */           ReleaseTypeInd = "Discard";
/*     */         }
/*     */ 
/*     */         
/* 829 */         if (sel != null && sel.getSelectionConfig() != null && 
/* 830 */           sel.getSelectionConfig().getSelectionConfigurationAbbreviation().equals("DP"))
/*     */         {
/* 832 */           ReleaseTypeInd = "Discard";
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 840 */         String dateString = "";
/* 841 */         String monthString = "";
/*     */         
/* 843 */         if (sel.getImpactDates().size() == 0) {
/*     */           
/* 845 */           Calendar calendarDate = sel.getStreetDate();
/* 846 */           dateString = MilestoneHelper.getFormatedDate(calendarDate);
/* 847 */           if (calendarDate != null) {
/*     */             
/* 849 */             SimpleDateFormat formatter = new SimpleDateFormat("MM");
/* 850 */             monthString = formatter.format(calendarDate.getTime());
/*     */           } 
/*     */         } else {
/*     */           
/* 854 */           ImpactDate theImpactDate = sel.getImpactDateObject();
/* 855 */           Calendar calendarDate = theImpactDate.getImpactDate();
/* 856 */           dateString = MilestoneHelper.getFormatedDate(calendarDate);
/*     */           
/* 858 */           if (calendarDate != null) {
/*     */             
/* 860 */             SimpleDateFormat formatter = new SimpleDateFormat("MM");
/* 861 */             monthString = formatter.format(calendarDate.getTime());
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 867 */         Hashtable releaseTypeSubTable = (Hashtable)groupedByHeaderTypes.get(companyName);
/* 868 */         if (releaseTypeSubTable == null) {
/*     */ 
/*     */           
/* 871 */           releaseTypeSubTable = new Hashtable();
/* 872 */           groupedByHeaderTypes.put(companyName, releaseTypeSubTable);
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 877 */         Hashtable selectionsForSubconfig = (Hashtable)releaseTypeSubTable.get(ReleaseTypeInd);
/* 878 */         if (selectionsForSubconfig == null) {
/*     */ 
/*     */           
/* 881 */           selectionsForSubconfig = new Hashtable();
/* 882 */           releaseTypeSubTable.put(ReleaseTypeInd, selectionsForSubconfig);
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/* 890 */           Integer.parseInt(monthString);
/*     */         }
/* 892 */         catch (Exception e) {
/*     */           
/* 894 */           monthString = "52";
/*     */         } 
/* 896 */         Vector monthsTable = (Vector)selectionsForSubconfig.get(monthString);
/* 897 */         if (monthsTable == null) {
/*     */ 
/*     */           
/* 900 */           monthsTable = new Vector();
/* 901 */           selectionsForSubconfig.put(monthString, monthsTable);
/*     */         } 
/*     */ 
/*     */         
/* 905 */         monthsTable.addElement(sel);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 910 */     return groupedByHeaderTypes;
/*     */   }
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
/*     */   private static Vector constructSelectionsWithImpactDates(Vector vectorToOrder) {
/* 923 */     Vector finalVector = new Vector();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 930 */     for (int count = 0; count < vectorToOrder.size(); count++) {
/*     */       
/* 932 */       Selection temp = (Selection)vectorToOrder.elementAt(count);
/*     */ 
/*     */ 
/*     */       
/* 936 */       if (temp.getImpactDates().size() == 0) {
/*     */         
/* 938 */         finalVector.add(temp);
/*     */       } else {
/*     */         
/* 941 */         for (int i = 0; i < temp.getImpactDates().size(); i++) {
/*     */           
/* 943 */           ImpactDate iDate = (ImpactDate)temp.getImpactDates().elementAt(i);
/*     */           
/* 945 */           Selection tempSelection = new Selection();
/*     */           try {
/* 947 */             tempSelection = (Selection)temp.clone();
/* 948 */           } catch (Exception e) {
/* 949 */             System.out.println("Cloning Error in groupSelectionsByHeaderType");
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 955 */           Calendar selectionStartDate = tempSelection.getStreetDate();
/* 956 */           Calendar selectionImpactCal = ((ImpactDate)temp.getImpactDates().elementAt(i)).getImpactDate();
/* 957 */           boolean discardSelectionImpact = false;
/*     */           
/* 959 */           if (selectionStartDate != null && ((beginStDate != null && selectionStartDate.before(beginStDate)) || (
/* 960 */             endStDate != null && selectionStartDate.after(endStDate))))
/*     */           {
/*     */ 
/*     */             
/* 964 */             if (selectionImpactCal != null && ((beginStDate != null && selectionImpactCal.before(beginStDate)) || (
/* 965 */               endStDate != null && selectionImpactCal.after(endStDate))))
/*     */             {
/* 967 */               discardSelectionImpact = true;
/*     */             }
/*     */           }
/*     */ 
/*     */           
/* 972 */           if (!discardSelectionImpact) {
/* 973 */             tempSelection.setImpactDateObject(iDate);
/* 974 */             finalVector.add(tempSelection);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 982 */     return finalVector;
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\MCACustomImpactScheduleForPrintSubHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */