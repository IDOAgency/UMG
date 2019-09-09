/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.ComponentLog;
/*     */ import com.techempower.gemini.Context;
/*     */ import com.techempower.gemini.GeminiApplication;
/*     */ import com.universal.milestone.DreamWorksRelScheduleForPrintSubHandler;
/*     */ import com.universal.milestone.Form;
/*     */ import com.universal.milestone.MilestoneHelper;
/*     */ import com.universal.milestone.MilestoneHelper_2;
/*     */ import com.universal.milestone.Schedule;
/*     */ import com.universal.milestone.ScheduledTask;
/*     */ import com.universal.milestone.SecureHandler;
/*     */ import com.universal.milestone.Selection;
/*     */ import com.universal.milestone.SelectionManager;
/*     */ import com.universal.milestone.StatusJSPupdate;
/*     */ import com.universal.milestone.StringComparator;
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
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Vector;
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
/*     */ public class DreamWorksRelScheduleForPrintSubHandler
/*     */   extends SecureHandler
/*     */ {
/*     */   public static final String COMPONENT_CODE = "hDWProd";
/*     */   public GeminiApplication application;
/*     */   public ComponentLog log;
/*     */   
/*     */   public DreamWorksRelScheduleForPrintSubHandler(GeminiApplication application) {
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
/*     */ 
/*     */   
/*     */   protected static void fillDreamWorksRelScheduleForPrint(XStyleSheet report, Context context) {
/*  95 */     Color SHADED_AREA_COLOR = Color.lightGray;
/*  96 */     int COL_LINE_STYLE = 4097;
/*  97 */     int HEADER_FONT_SIZE = 12;
/*  98 */     int NUM_COLUMNS = 8;
/*     */     
/* 100 */     double ldLineVal = 0.3D;
/* 101 */     ComponentLog log = context.getApplication().getLog("hDWProd");
/*     */ 
/*     */     
/* 104 */     SectionBand hbandType = new SectionBand(report);
/* 105 */     SectionBand hbandCategory = new SectionBand(report);
/* 106 */     SectionBand hbandDate = new SectionBand(report);
/* 107 */     SectionBand body = new SectionBand(report);
/* 108 */     SectionBand footer = new SectionBand(report);
/* 109 */     SectionBand spacer = new SectionBand(report);
/* 110 */     SectionBand selectionSpacer = new SectionBand(report);
/* 111 */     DefaultSectionLens group = null;
/*     */     
/* 113 */     footer.setVisible(true);
/* 114 */     footer.setHeight(0.1F);
/* 115 */     footer.setShrinkToFit(false);
/* 116 */     footer.setBottomBorder(0);
/*     */     
/* 118 */     spacer.setVisible(true);
/* 119 */     spacer.setHeight(0.05F);
/* 120 */     spacer.setShrinkToFit(false);
/* 121 */     spacer.setBottomBorder(0);
/*     */     
/* 123 */     selectionSpacer.setVisible(true);
/* 124 */     selectionSpacer.setHeight(0.03F);
/* 125 */     selectionSpacer.setShrinkToFit(false);
/* 126 */     selectionSpacer.setBottomBorder(0);
/*     */ 
/*     */     
/* 129 */     StatusJSPupdate statusJSPupdate = new StatusJSPupdate(context);
/*     */ 
/*     */     
/* 132 */     statusJSPupdate.setInternalCounter(true);
/*     */ 
/*     */     
/* 135 */     statusJSPupdate.updateStatus(0, 0, "start_gathering", 0);
/*     */ 
/*     */     
/* 138 */     Vector selections = MilestoneHelper.getSelectionsForReport(context);
/*     */ 
/*     */     
/* 141 */     statusJSPupdate.updateStatus(0, 0, "start_report", 0);
/*     */ 
/*     */     
/* 144 */     MilestoneHelper.setSelectionSorting(selections, 12);
/* 145 */     Collections.sort(selections);
/*     */ 
/*     */     
/* 148 */     MilestoneHelper.setSelectionSorting(selections, 14);
/* 149 */     Collections.sort(selections);
/*     */ 
/*     */     
/* 152 */     MilestoneHelper.setSelectionSorting(selections, 4);
/* 153 */     Collections.sort(selections);
/*     */ 
/*     */     
/* 156 */     MilestoneHelper.setSelectionSorting(selections, 3);
/* 157 */     Collections.sort(selections);
/*     */ 
/*     */     
/* 160 */     MilestoneHelper.setSelectionSorting(selections, 1);
/* 161 */     Collections.sort(selections);
/*     */ 
/*     */     
/* 164 */     MilestoneHelper.setSelectionSorting(selections, 10);
/* 165 */     Collections.sort(selections);
/*     */ 
/*     */     
/*     */     try {
/* 169 */       DefaultTableLens table_contents = null;
/* 170 */       DefaultTableLens rowCountTable = null;
/* 171 */       DefaultTableLens columnHeaderTable = null;
/* 172 */       DefaultTableLens subTable = null;
/* 173 */       DefaultTableLens monthTableLens = null;
/* 174 */       DefaultTableLens configTableLens = null;
/* 175 */       DefaultTableLens dateTableLens = null;
/*     */       
/* 177 */       rowCountTable = new DefaultTableLens(2, 10000);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 184 */       Form reportForm = (Form)context.getSessionValue("reportForm");
/*     */       
/* 186 */       Calendar beginStDate = (reportForm.getStringValue("beginDate") != null && 
/* 187 */         reportForm.getStringValue("beginDate").length() > 0) ? 
/* 188 */         MilestoneHelper.getDate(reportForm.getStringValue("beginDate")) : null;
/*     */       
/* 190 */       Calendar endStDate = (reportForm.getStringValue("endDate") != null && 
/* 191 */         reportForm.getStringValue("endDate").length() > 0) ? 
/* 192 */         MilestoneHelper.getDate(reportForm.getStringValue("endDate")) : null;
/*     */       
/* 194 */       report.setElement("crs_startdate", MilestoneHelper.getFormatedDate(beginStDate));
/* 195 */       report.setElement("crs_enddate", MilestoneHelper.getFormatedDate(endStDate));
/*     */       
/* 197 */       SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
/* 198 */       String todayLong = formatter.format(new Date());
/* 199 */       report.setElement("crs_bottomdate", todayLong);
/*     */ 
/*     */       
/* 202 */       Hashtable selTable = MilestoneHelper.groupSelectionsByCompanyAndSubconfig(selections);
/*     */       
/* 204 */       Enumeration companies = selTable.keys();
/* 205 */       Vector companyVector = new Vector();
/* 206 */       while (companies.hasMoreElements()) {
/* 207 */         companyVector.addElement(companies.nextElement());
/*     */       }
/*     */       
/* 210 */       int numSubconfigs = 0;
/* 211 */       for (int i = 0; i < companyVector.size(); i++) {
/*     */         
/* 213 */         String companyName = (companyVector.elementAt(i) != null) ? (String)companyVector.elementAt(i) : "";
/* 214 */         Hashtable subconfigTable = (Hashtable)selTable.get(companyName);
/* 215 */         if (subconfigTable != null)
/*     */         {
/* 217 */           numSubconfigs += subconfigTable.size();
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 222 */       int numExtraRows = companyVector.size() * 2 - 1 + numSubconfigs * 6;
/*     */ 
/*     */       
/* 225 */       int numSelections = selections.size() * 2;
/* 226 */       int numRows = numSelections + numExtraRows;
/*     */ 
/*     */       
/* 229 */       int numColumns = 8;
/*     */ 
/*     */       
/* 232 */       int nextRow = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 238 */       Object[] companyArray = companyVector.toArray();
/* 239 */       Arrays.sort(companyArray, new StringComparator());
/*     */       
/* 241 */       for (int n = 0; n < companyArray.length; n++)
/*     */       {
/* 243 */         String company = (String)companyArray[n];
/* 244 */         String companyHeaderText = !company.trim().equals("") ? company : "Other";
/*     */         
/* 246 */         hbandType = new SectionBand(report);
/* 247 */         hbandType.setHeight(0.95F);
/* 248 */         hbandType.setShrinkToFit(true);
/* 249 */         hbandType.setVisible(true);
/*     */         
/* 251 */         nextRow = 0;
/*     */ 
/*     */         
/* 254 */         table_contents = new DefaultTableLens(1, numColumns);
/*     */ 
/*     */         
/* 257 */         table_contents.setColWidth(0, 260);
/* 258 */         table_contents.setColWidth(1, 200);
/* 259 */         table_contents.setColWidth(2, 110);
/* 260 */         table_contents.setColWidth(3, 90);
/* 261 */         table_contents.setColWidth(4, 80);
/* 262 */         table_contents.setColWidth(5, 60);
/* 263 */         table_contents.setColWidth(6, 60);
/* 264 */         table_contents.setColWidth(7, 140);
/*     */ 
/*     */         
/* 267 */         table_contents.setSpan(nextRow, 0, new Dimension(numColumns, 1));
/* 268 */         table_contents.setObject(nextRow, 0, companyHeaderText);
/* 269 */         table_contents.setRowBorder(nextRow, 0);
/* 270 */         table_contents.setRowBackground(nextRow, Color.black);
/* 271 */         table_contents.setRowForeground(nextRow, Color.white);
/* 272 */         table_contents.setRowFont(nextRow, new Font("Arial", 3, 16));
/*     */         
/* 274 */         hbandType.addTable(table_contents, new Rectangle(0, 0, 800, 30));
/*     */         
/* 276 */         nextRow = 0;
/*     */         
/* 278 */         columnHeaderTable = new DefaultTableLens(1, 8);
/*     */ 
/*     */         
/* 281 */         columnHeaderTable.setColWidth(0, 260);
/* 282 */         columnHeaderTable.setColWidth(1, 200);
/* 283 */         columnHeaderTable.setColWidth(2, 95);
/* 284 */         columnHeaderTable.setColWidth(3, 100);
/* 285 */         columnHeaderTable.setColWidth(4, 80);
/* 286 */         columnHeaderTable.setColWidth(5, 60);
/* 287 */         columnHeaderTable.setColWidth(6, 60);
/* 288 */         columnHeaderTable.setColWidth(7, 140);
/*     */         
/* 290 */         columnHeaderTable.setAlignment(nextRow, 0, 16);
/* 291 */         columnHeaderTable.setObject(nextRow, 0, "\nArtist");
/*     */         
/* 293 */         columnHeaderTable.setAlignment(nextRow, 1, 16);
/* 294 */         columnHeaderTable.setObject(nextRow, 1, "\nTitle/B Side");
/* 295 */         columnHeaderTable.setObject(nextRow, 2, "\nUPC/Selection");
/* 296 */         columnHeaderTable.setAlignment(nextRow, 3, 18);
/* 297 */         columnHeaderTable.setObject(nextRow, 3, "\nPrice");
/* 298 */         columnHeaderTable.setAlignment(nextRow, 4, 18);
/* 299 */         columnHeaderTable.setObject(nextRow, 4, "Street\nDate");
/* 300 */         columnHeaderTable.setAlignment(nextRow, 5, 18);
/* 301 */         columnHeaderTable.setObject(nextRow, 5, "Ship\nDate");
/* 302 */         columnHeaderTable.setObject(nextRow, 6, "Impact\nDate");
/* 303 */         columnHeaderTable.setAlignment(nextRow, 6, 18);
/* 304 */         columnHeaderTable.setObject(nextRow, 7, "\nComments");
/*     */         
/* 306 */         columnHeaderTable.setRowFont(nextRow, new Font("Arial", 3, 10));
/*     */         
/* 308 */         columnHeaderTable.setRowBorderColor(nextRow - 1, Color.white);
/*     */         
/* 310 */         for (int k = -1; k < 7; k++) {
/* 311 */           columnHeaderTable.setColBorderColor(k, Color.white);
/*     */         }
/* 313 */         columnHeaderTable.setRowBorder(nextRow, 4097);
/* 314 */         columnHeaderTable.setRowBorderColor(nextRow, Color.black);
/*     */         
/* 316 */         hbandType.addTable(columnHeaderTable, new Rectangle(0, 30, 800, 35));
/* 317 */         hbandType.setBottomBorder(0);
/*     */ 
/*     */         
/* 320 */         Hashtable subconfigTable = (Hashtable)selTable.get(company);
/* 321 */         if (subconfigTable != null) {
/*     */           
/* 323 */           Enumeration subconfigs = subconfigTable.keys();
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 328 */           Vector subconfigVector = new Vector();
/*     */           
/* 330 */           while (subconfigs.hasMoreElements()) {
/* 331 */             subconfigVector.add((String)subconfigs.nextElement());
/*     */           }
/* 333 */           Object[] subconfigsArray = subconfigVector.toArray();
/* 334 */           Arrays.sort(subconfigsArray, new StringComparator());
/*     */           
/* 336 */           for (int scIndex = 0; scIndex < subconfigsArray.length; scIndex++) {
/*     */             
/* 338 */             String subconfig = (String)subconfigsArray[scIndex];
/*     */             
/* 340 */             configTableLens = new DefaultTableLens(1, 7);
/* 341 */             hbandCategory = new SectionBand(report);
/* 342 */             hbandCategory.setHeight(0.25F);
/* 343 */             hbandCategory.setShrinkToFit(true);
/* 344 */             hbandCategory.setVisible(true);
/* 345 */             hbandCategory.setBottomBorder(0);
/* 346 */             hbandCategory.setLeftBorder(0);
/* 347 */             hbandCategory.setRightBorder(0);
/* 348 */             hbandCategory.setTopBorder(0);
/*     */             
/* 350 */             nextRow = 0;
/*     */ 
/*     */             
/* 353 */             configTableLens.setAlignment(nextRow, 0, 2);
/* 354 */             configTableLens.setSpan(nextRow, 0, new Dimension(7, 1));
/* 355 */             configTableLens.setObject(nextRow, 0, subconfig);
/* 356 */             configTableLens.setRowFont(nextRow, new Font("Arial", 3, 12));
/*     */             
/* 358 */             hbandCategory.addTable(configTableLens, new Rectangle(800, 800));
/*     */             
/* 360 */             footer.setVisible(true);
/* 361 */             footer.setHeight(0.1F);
/* 362 */             footer.setShrinkToFit(false);
/* 363 */             footer.setBottomBorder(0);
/*     */             
/* 365 */             group = new DefaultSectionLens(null, group, spacer);
/* 366 */             group = new DefaultSectionLens(null, group, hbandCategory);
/* 367 */             group = new DefaultSectionLens(null, group, spacer);
/*     */ 
/*     */             
/* 370 */             selections = (Vector)subconfigTable.get(subconfig);
/* 371 */             if (selections == null) {
/* 372 */               selections = new Vector();
/*     */             }
/*     */             
/* 375 */             for (int i = 0; i < selections.size(); i++) {
/*     */ 
/*     */ 
/*     */               
/* 379 */               statusJSPupdate.updateStatus(numSelections, i, "start_report", 0);
/*     */               
/* 381 */               Selection sel = (Selection)selections.elementAt(i);
/*     */ 
/*     */               
/* 384 */               sel = SelectionManager.getInstance().getSelectionAndSchedule(sel.getSelectionID());
/*     */               
/* 386 */               nextRow = 0;
/* 387 */               subTable = new DefaultTableLens(2, 8);
/*     */ 
/*     */               
/* 390 */               subTable.setColWidth(0, 270);
/* 391 */               subTable.setColWidth(1, 240);
/* 392 */               subTable.setColWidth(2, 90);
/* 393 */               subTable.setColWidth(3, 90);
/* 394 */               subTable.setColWidth(4, 140);
/* 395 */               subTable.setColWidth(5, 60);
/* 396 */               subTable.setColWidth(6, 60);
/* 397 */               subTable.setColWidth(7, 140);
/*     */ 
/*     */               
/* 400 */               String bSide = (sel.getBSide() != null && !sel.getBSide().trim().equals("")) ? (
/* 401 */                 "B Side:  " + sel.getBSide()) : "";
/*     */ 
/*     */               
/* 404 */               String upc = sel.getUpc();
/*     */ 
/*     */               
/* 407 */               upc = MilestoneHelper_2.getRMSReportFormat(upc, "UPC", sel.getIsDigital());
/*     */ 
/*     */               
/* 410 */               if (upc == null || upc.trim().equals("")) {
/*     */                 
/* 412 */                 upc = SelectionManager.getLookupObjectValue(sel.getPrefixID());
/* 413 */                 if (upc == null)
/* 414 */                   upc = ""; 
/* 415 */                 if (sel.getSelectionNo() != null) {
/* 416 */                   upc = String.valueOf(upc) + sel.getSelectionNo();
/*     */                 }
/*     */               } 
/*     */               
/* 420 */               String price = "0.00";
/* 421 */               if (sel.getPriceCode() != null && 
/* 422 */                 sel.getPriceCode().getTotalCost() > 0.0F) {
/* 423 */                 price = MilestoneHelper.formatDollarPrice(sel.getPriceCode().getTotalCost());
/*     */               }
/*     */               
/* 426 */               String streetDate = MilestoneHelper.getFormatedDate(sel.getStreetDate());
/*     */               
/* 428 */               String status = (sel.getSelectionStatus() != null && sel.getSelectionStatus().getName() != null) ? 
/* 429 */                 sel.getSelectionStatus().getName() : "";
/*     */               
/* 431 */               if (status.equalsIgnoreCase("TBS")) {
/* 432 */                 streetDate = "TBS " + streetDate;
/*     */               }
/* 434 */               else if (status.equalsIgnoreCase("In The Works")) {
/* 435 */                 streetDate = "ITW " + streetDate;
/*     */               } 
/*     */               
/* 438 */               String comment = (sel.getSelectionComments() != null) ? sel.getSelectionComments() : "";
/*     */               
/* 440 */               Schedule schedule = sel.getSchedule();
/*     */               
/* 442 */               Vector tasks = (schedule != null) ? schedule.getTasks() : null;
/*     */               
/* 444 */               ScheduledTask task = null;
/*     */               
/* 446 */               String SHIP = "";
/*     */               
/* 448 */               if (tasks != null)
/*     */               {
/*     */                 
/* 451 */                 for (int j = 0; j < tasks.size(); j++) {
/*     */ 
/*     */                   
/* 454 */                   task = (ScheduledTask)tasks.get(j);
/*     */                   
/* 456 */                   if (task != null && task.getDueDate() != null) {
/*     */                     
/* 458 */                     String dueDate = MilestoneHelper.getFormatedDate(task.getDueDate());
/* 459 */                     String taskAbbrev = MilestoneHelper.getTaskAbbreivationNameById(task.getTaskAbbreviationID());
/*     */                     
/* 461 */                     if (taskAbbrev.equalsIgnoreCase("PSD")) {
/*     */                       
/* 463 */                       SHIP = dueDate;
/*     */                       
/*     */                       break;
/*     */                     } 
/* 467 */                     task = null;
/*     */                   } 
/*     */                 } 
/*     */               }
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 475 */               subTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
/* 476 */               subTable.setRowHeight(nextRow, 11);
/*     */ 
/*     */               
/* 479 */               subTable.setObject(nextRow, 0, sel.getFlArtist());
/* 480 */               subTable.setObject(nextRow, 1, sel.getTitle());
/* 481 */               subTable.setObject(nextRow, 2, upc);
/* 482 */               subTable.setObject(nextRow, 3, "$" + price);
/* 483 */               subTable.setColLineWrap(4, false);
/* 484 */               subTable.setObject(nextRow, 4, streetDate);
/* 485 */               subTable.setAlignment(1);
/*     */               
/* 487 */               subTable.setObject(nextRow, 6, MilestoneHelper.getFormatedDate(sel.getImpactDate()));
/* 488 */               subTable.setObject(nextRow, 5, SHIP);
/* 489 */               subTable.setObject(nextRow, 7, comment);
/*     */               
/* 491 */               subTable.setColAlignment(0, 9);
/* 492 */               subTable.setSpan(nextRow, 0, new Dimension(1, 2));
/* 493 */               subTable.setColAlignment(1, 33);
/* 494 */               subTable.setColAlignment(2, 9);
/* 495 */               subTable.setSpan(nextRow, 2, new Dimension(1, 2));
/* 496 */               subTable.setColAlignment(3, 12);
/* 497 */               subTable.setSpan(nextRow, 3, new Dimension(1, 2));
/* 498 */               subTable.setColAlignment(4, 10);
/* 499 */               subTable.setSpan(nextRow, 4, new Dimension(1, 2));
/* 500 */               subTable.setColAlignment(6, 10);
/* 501 */               subTable.setSpan(nextRow, 6, new Dimension(1, 2));
/* 502 */               subTable.setColAlignment(5, 9);
/* 503 */               subTable.setSpan(nextRow, 5, new Dimension(1, 2));
/* 504 */               subTable.setColAlignment(7, 9);
/* 505 */               subTable.setSpan(nextRow, 7, new Dimension(1, 2));
/*     */               
/* 507 */               subTable.setRowBorder(nextRow, 1, 0);
/*     */               
/* 509 */               subTable.setRowBorder(nextRow - 1, 0);
/*     */               
/* 511 */               subTable.setRowFont(nextRow, new Font("Arial", 0, 8));
/*     */               
/* 513 */               nextRow++;
/* 514 */               subTable.setAlignment(nextRow, 1, 9);
/*     */               
/* 516 */               String[] checkStrings = { comment.trim(), sel.getFlArtist().trim(), sel.getTitle().trim() };
/* 517 */               int[] checkStringsLength = { 20, 40, 60 };
/*     */               
/* 519 */               if (bSide.equals("") && comment.equals(""))
/*     */               {
/* 521 */                 subTable.setRowHeight(nextRow, 1);
/*     */               }
/*     */               
/* 524 */               int addExtraLines = MilestoneHelper.lineCount(checkStrings, checkStringsLength);
/*     */               
/* 526 */               if (addExtraLines > 0 && addExtraLines < 2)
/*     */               {
/* 528 */                 addExtraLines--;
/*     */               }
/* 530 */               for (int z = 0; z < addExtraLines; z++) {
/* 531 */                 bSide = String.valueOf(bSide) + "\n";
/*     */               }
/* 533 */               for (int k = -1; k < 7; k++) {
/* 534 */                 subTable.setColBorder(k, 0);
/*     */               }
/* 536 */               subTable.setObject(nextRow, 0, "");
/* 537 */               subTable.setObject(nextRow, 1, bSide);
/* 538 */               subTable.setObject(nextRow, 2, "");
/* 539 */               subTable.setObject(nextRow, 3, "");
/* 540 */               subTable.setObject(nextRow, 4, "");
/* 541 */               subTable.setObject(nextRow, 5, "");
/* 542 */               subTable.setObject(nextRow, 6, "");
/* 543 */               subTable.setObject(nextRow, 7, "");
/*     */               
/* 545 */               subTable.setRowFont(nextRow, new Font("Arial", 2, 8));
/* 546 */               subTable.setColAlignment(1, 9);
/*     */ 
/*     */               
/* 549 */               subTable.setRowBorderColor(nextRow, Color.lightGray);
/*     */               
/* 551 */               body = new SectionBand(report);
/*     */               
/* 553 */               double lfLineCount = 1.5D;
/*     */               
/* 555 */               if (addExtraLines > 0) {
/*     */                 
/* 557 */                 body.setHeight(1.5F);
/*     */               }
/*     */               else {
/*     */                 
/* 561 */                 body.setHeight(0.8F);
/*     */               } 
/*     */               
/* 564 */               if (addExtraLines > 3) {
/*     */                 
/* 566 */                 if (lfLineCount < addExtraLines * 0.3D) {
/* 567 */                   lfLineCount = addExtraLines * 0.3D;
/*     */                 }
/* 569 */                 body.setHeight((float)lfLineCount);
/*     */               }
/*     */               else {
/*     */                 
/* 573 */                 body.setHeight(0.8F);
/*     */               } 
/*     */               
/* 576 */               body.addTable(subTable, new Rectangle(800, 800));
/* 577 */               body.setBottomBorder(0);
/* 578 */               body.setTopBorder(0);
/* 579 */               body.setShrinkToFit(true);
/* 580 */               body.setVisible(true);
/*     */ 
/*     */               
/* 583 */               group = new DefaultSectionLens(null, group, body);
/*     */             } 
/*     */           } 
/*     */         } 
/* 587 */         group = new DefaultSectionLens(hbandType, group, null);
/* 588 */         report.addSection(group, rowCountTable);
/* 589 */         report.addPageBreak();
/* 590 */         group = null;
/*     */       }
/*     */     
/* 593 */     } catch (Exception e) {
/*     */       
/* 595 */       System.out.println(">>>>>>>>fillDreamWorksRelScheduleForPrint(): exception: " + e);
/*     */     } 
/*     */ 
/*     */     
/* 599 */     statusJSPupdate = null;
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\DreamWorksRelScheduleForPrintSubHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */