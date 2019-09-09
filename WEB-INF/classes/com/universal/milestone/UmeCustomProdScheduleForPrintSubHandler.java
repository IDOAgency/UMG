/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.ComponentLog;
/*     */ import com.techempower.gemini.Context;
/*     */ import com.techempower.gemini.GeminiApplication;
/*     */ import com.universal.milestone.Form;
/*     */ import com.universal.milestone.MilestoneHelper;
/*     */ import com.universal.milestone.MilestoneHelper_2;
/*     */ import com.universal.milestone.MonthYearComparator;
/*     */ import com.universal.milestone.ScheduledTask;
/*     */ import com.universal.milestone.SecureHandler;
/*     */ import com.universal.milestone.Selection;
/*     */ import com.universal.milestone.StatusJSPupdate;
/*     */ import com.universal.milestone.UmeCustomProdScheduleForPrintSubHandler;
/*     */ import inetsoft.report.SectionBand;
/*     */ import inetsoft.report.XStyleSheet;
/*     */ import inetsoft.report.lens.DefaultSectionLens;
/*     */ import inetsoft.report.lens.DefaultTableLens;
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UmeCustomProdScheduleForPrintSubHandler
/*     */   extends SecureHandler
/*     */ {
/*     */   public static final String COMPONENT_CODE = "hUsu";
/*     */   public GeminiApplication application;
/*     */   public ComponentLog log;
/*     */   
/*     */   public UmeCustomProdScheduleForPrintSubHandler(GeminiApplication application) {
/*  73 */     this.application = application;
/*  74 */     this.log = application.getLog("hUsu");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  82 */   public String getDescription() { return "Sub Report"; }
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
/*     */   protected static void fillUmeCustomProdScheduleForPrint(XStyleSheet report, Context context) {
/*  95 */     int COL_LINE_STYLE = 4097;
/*  96 */     int HEADER_FONT_SIZE = 12;
/*     */     
/*  98 */     double ldLineVal = 0.2D;
/*     */ 
/*     */     
/* 101 */     StatusJSPupdate statusJSPupdate = new StatusJSPupdate(context);
/*     */ 
/*     */     
/* 104 */     statusJSPupdate.updateStatus(0, 0, "start_gathering", 0);
/*     */ 
/*     */     
/* 107 */     statusJSPupdate.setInternalCounter(true);
/*     */ 
/*     */     
/* 110 */     Vector selections = MilestoneHelper.getSelectionsForReport(context);
/*     */ 
/*     */     
/* 113 */     statusJSPupdate.updateStatus(0, 0, "start_report", 0);
/*     */ 
/*     */     
/* 116 */     MilestoneHelper.setSelectionSorting(selections, 12);
/* 117 */     Collections.sort(selections);
/*     */ 
/*     */     
/* 120 */     MilestoneHelper.setSelectionSorting(selections, 4);
/* 121 */     Collections.sort(selections);
/*     */ 
/*     */     
/* 124 */     MilestoneHelper.setSelectionSorting(selections, 22);
/* 125 */     Collections.sort(selections);
/*     */ 
/*     */     
/* 128 */     MilestoneHelper.setSelectionSorting(selections, 10);
/* 129 */     Collections.sort(selections);
/*     */ 
/*     */     
/* 132 */     MilestoneHelper.setSelectionSorting(selections, 1);
/* 133 */     Collections.sort(selections);
/*     */ 
/*     */     
/* 136 */     Hashtable selTable = MilestoneHelper.groupSelectionsByTypeAndStreetDate(selections);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 143 */       Form reportForm = (Form)context.getSessionValue("reportForm");
/*     */       
/* 145 */       Calendar beginStDate = (reportForm.getStringValue("beginDate") != null && 
/* 146 */         reportForm.getStringValue("beginDate").length() > 0) ? 
/* 147 */         MilestoneHelper.getDate(reportForm.getStringValue("beginDate")) : null;
/*     */       
/* 149 */       Calendar endStDate = (reportForm.getStringValue("endDate") != null && 
/* 150 */         reportForm.getStringValue("endDate").length() > 0) ? 
/* 151 */         MilestoneHelper.getDate(reportForm.getStringValue("endDate")) : null;
/*     */       
/* 153 */       report.setElement("startdate", MilestoneHelper.getFormatedDate(beginStDate));
/* 154 */       report.setElement("enddate", MilestoneHelper.getFormatedDate(endStDate));
/*     */       
/* 156 */       SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
/* 157 */       String todayLong = formatter.format(new Date());
/* 158 */       report.setElement("bottomdate", todayLong);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 164 */       int numMonths = 0;
/* 165 */       int numConfigs = 0;
/* 166 */       int numSelections = 0;
/*     */ 
/*     */ 
/*     */       
/* 170 */       Enumeration configEnums = selTable.keys();
/* 171 */       while (configEnums.hasMoreElements()) {
/*     */         
/* 173 */         String config = (String)configEnums.nextElement();
/* 174 */         numConfigs++;
/*     */         
/* 176 */         Hashtable monthTable = (Hashtable)selTable.get(config);
/* 177 */         if (monthTable != null) {
/*     */           
/* 179 */           Enumeration months = monthTable.keys();
/* 180 */           while (months.hasMoreElements()) {
/*     */             
/* 182 */             String monthName = (String)months.nextElement();
/* 183 */             numMonths++;
/*     */             
/* 185 */             Vector selectionCount = (Vector)monthTable.get(monthName);
/* 186 */             if (selectionCount != null)
/* 187 */               numSelections += selectionCount.size(); 
/*     */           } 
/*     */         } 
/*     */       } 
/* 191 */       int numRows = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 196 */       numRows += numMonths * 4;
/* 197 */       numRows += numConfigs * 3;
/* 198 */       numRows += numSelections * 2;
/*     */       
/* 200 */       numRows--;
/*     */ 
/*     */       
/* 203 */       int numColumns = 15;
/* 204 */       DefaultTableLens table_contents = null;
/* 205 */       DefaultTableLens columnHeaderTable = null;
/* 206 */       DefaultTableLens rowCountTable = null;
/* 207 */       DefaultTableLens subTable = null;
/*     */       
/* 209 */       SectionBand hbandType = new SectionBand(report);
/* 210 */       SectionBand hbandMonth = new SectionBand(report);
/* 211 */       SectionBand body = new SectionBand(report);
/* 212 */       SectionBand footer = new SectionBand(report);
/* 213 */       DefaultSectionLens group = null;
/*     */       
/* 215 */       Enumeration configs = selTable.keys();
/* 216 */       Vector configVector = new Vector();
/*     */       
/* 218 */       while (configs.hasMoreElements()) {
/* 219 */         configVector.addElement(configs.nextElement());
/*     */       }
/* 221 */       Collections.sort(configVector);
/* 222 */       Vector sortedConfigVector = MilestoneHelper.sortStrings(configVector);
/*     */ 
/*     */       
/* 225 */       int nextRow = 0;
/*     */       
/* 227 */       for (int n = 0; n < sortedConfigVector.size(); n++)
/*     */       {
/* 229 */         String config = (String)sortedConfigVector.get(n);
/* 230 */         Hashtable monthTable = (Hashtable)selTable.get(config);
/*     */ 
/*     */         
/* 233 */         rowCountTable = new DefaultTableLens(2, 10000);
/* 234 */         table_contents = new DefaultTableLens(1, 15);
/*     */         
/* 236 */         table_contents.setColWidth(0, 110);
/* 237 */         table_contents.setColWidth(1, 80);
/* 238 */         table_contents.setColWidth(2, 80);
/* 239 */         table_contents.setColWidth(3, 80);
/* 240 */         table_contents.setColWidth(4, 190);
/* 241 */         table_contents.setColWidth(5, 65);
/* 242 */         table_contents.setColWidth(6, 80);
/* 243 */         table_contents.setColWidth(7, 85);
/* 244 */         table_contents.setColWidth(8, 85);
/* 245 */         table_contents.setColWidth(9, 80);
/* 246 */         table_contents.setColWidth(10, 80);
/* 247 */         table_contents.setColWidth(11, 75);
/* 248 */         table_contents.setColWidth(12, 80);
/* 249 */         table_contents.setColWidth(13, 80);
/* 250 */         table_contents.setColWidth(14, 220);
/*     */ 
/*     */         
/* 253 */         nextRow = 0;
/* 254 */         hbandType = new SectionBand(report);
/* 255 */         hbandType.setHeight(0.9F);
/* 256 */         hbandType.setShrinkToFit(false);
/* 257 */         hbandType.setVisible(true);
/*     */         
/* 259 */         int cols = 15;
/*     */ 
/*     */         
/* 262 */         table_contents.setObject(nextRow, 0, "");
/* 263 */         table_contents.setSpan(nextRow, 0, new Dimension(cols, 1));
/* 264 */         table_contents.setRowHeight(nextRow, 15);
/* 265 */         table_contents.setRowBackground(nextRow, Color.white);
/*     */         
/* 267 */         for (int col = -1; col < cols; col++) {
/*     */           
/* 269 */           table_contents.setColBorderColor(nextRow, col, Color.black);
/* 270 */           table_contents.setColBorder(nextRow, col, 4097);
/*     */         } 
/*     */         
/* 273 */         table_contents.setRowBorderColor(nextRow, Color.black);
/* 274 */         table_contents.setRowBorder(nextRow, 266240);
/*     */         
/* 276 */         table_contents.setAlignment(nextRow, 0, 2);
/* 277 */         table_contents.setObject(nextRow, 0, config);
/* 278 */         table_contents.setRowFont(nextRow, new Font("Arial", 3, 12));
/*     */         
/* 280 */         nextRow++;
/*     */ 
/*     */ 
/*     */         
/* 284 */         if (monthTable != null) {
/*     */           
/* 286 */           Enumeration months = monthTable.keys();
/*     */           
/* 288 */           Vector monthVector = new Vector();
/*     */           
/* 290 */           while (months.hasMoreElements())
/*     */           {
/* 292 */             monthVector.add((String)months.nextElement());
/*     */           }
/*     */ 
/*     */           
/* 296 */           Object[] monthArray = null;
/* 297 */           monthArray = monthVector.toArray();
/* 298 */           Arrays.sort(monthArray, new MonthYearComparator());
/*     */           
/* 300 */           for (int x = 0; x < monthArray.length; x++) {
/*     */             
/* 302 */             String monthName = (String)monthArray[x];
/* 303 */             String monthNameString = monthName;
/*     */             
/* 305 */             hbandMonth = new SectionBand(report);
/* 306 */             hbandMonth.setHeight(0.35F);
/* 307 */             hbandMonth.setShrinkToFit(true);
/* 308 */             hbandMonth.setVisible(true);
/* 309 */             hbandMonth.setBottomBorder(0);
/* 310 */             hbandMonth.setLeftBorder(0);
/* 311 */             hbandMonth.setRightBorder(0);
/* 312 */             hbandMonth.setTopBorder(0);
/*     */ 
/*     */ 
/*     */             
/*     */             try {
/* 317 */               Calendar currentDate = MilestoneHelper.getMYDate(monthName);
/* 318 */               SimpleDateFormat monthFormatter = new SimpleDateFormat("MMMMM");
/* 319 */               monthNameString = monthFormatter.format(currentDate.getTime());
/*     */             }
/* 321 */             catch (Exception e) {
/*     */               
/* 323 */               if (monthName.equals("13")) {
/* 324 */                 monthNameString = "TBS";
/* 325 */               } else if (monthName.equals("26")) {
/* 326 */                 monthNameString = "ITW";
/*     */               } else {
/* 328 */                 monthNameString = "No street date";
/*     */               } 
/*     */             } 
/* 331 */             selections = (Vector)monthTable.get(monthName);
/* 332 */             if (selections == null) {
/* 333 */               selections = new Vector();
/*     */             }
/*     */ 
/*     */             
/* 337 */             MilestoneHelper.setSelectionSorting(selections, 5);
/* 338 */             Collections.sort(selections);
/*     */ 
/*     */             
/* 341 */             MilestoneHelper.setSelectionSorting(selections, 2);
/* 342 */             Collections.sort(selections);
/*     */ 
/*     */             
/* 345 */             MilestoneHelper.setSelectionSorting(selections, 13);
/* 346 */             Collections.sort(selections);
/*     */ 
/*     */             
/* 349 */             MilestoneHelper.setSelectionSorting(selections, 4);
/* 350 */             Collections.sort(selections);
/*     */ 
/*     */             
/* 353 */             MilestoneHelper.setSelectionSorting(selections, 22);
/* 354 */             Collections.sort(selections);
/*     */ 
/*     */             
/* 357 */             MilestoneHelper.setSelectionSorting(selections, 1);
/* 358 */             Collections.sort(selections);
/*     */             
/* 360 */             columnHeaderTable = new DefaultTableLens(1, 15);
/*     */             
/* 362 */             nextRow = 0;
/*     */             
/* 364 */             columnHeaderTable.setColWidth(0, 110);
/* 365 */             columnHeaderTable.setColWidth(1, 80);
/* 366 */             columnHeaderTable.setColWidth(2, 80);
/* 367 */             columnHeaderTable.setColWidth(3, 80);
/* 368 */             columnHeaderTable.setColWidth(4, 190);
/* 369 */             columnHeaderTable.setColWidth(5, 65);
/* 370 */             columnHeaderTable.setColWidth(6, 80);
/* 371 */             columnHeaderTable.setColWidth(7, 85);
/* 372 */             columnHeaderTable.setColWidth(8, 85);
/* 373 */             columnHeaderTable.setColWidth(9, 80);
/* 374 */             columnHeaderTable.setColWidth(10, 80);
/* 375 */             columnHeaderTable.setColWidth(11, 75);
/* 376 */             columnHeaderTable.setColWidth(12, 80);
/* 377 */             columnHeaderTable.setColWidth(13, 80);
/* 378 */             columnHeaderTable.setColWidth(14, 220);
/*     */ 
/*     */             
/* 381 */             columnHeaderTable.setRowAlignment(nextRow, 2);
/* 382 */             columnHeaderTable.setObject(nextRow, 0, "Rls Date\nProj No.");
/*     */             
/* 384 */             columnHeaderTable.setObject(nextRow, 1, "\nArtist/Title");
/* 385 */             columnHeaderTable.setSpan(nextRow, 1, new Dimension(3, 1));
/*     */             
/* 387 */             columnHeaderTable.setObject(nextRow, 4, "Selection\nUPC");
/* 388 */             columnHeaderTable.setObject(nextRow, 5, "\nClear");
/*     */             
/* 390 */             columnHeaderTable.setObject(nextRow, 6, "Art\nSpecs\nClients");
/* 391 */             columnHeaderTable.setObject(nextRow, 7, "Label Copy to\nClient");
/* 392 */             columnHeaderTable.setObject(nextRow, 8, "\nAcct #");
/* 393 */             columnHeaderTable.setObject(nextRow, 9, "\nMaster\nrecvd");
/* 394 */             columnHeaderTable.setObject(nextRow, 10, "P.O.\nFrom Client");
/* 395 */             columnHeaderTable.setObject(nextRow, 11, "Graphic\nFilm");
/*     */             
/* 397 */             columnHeaderTable.setObject(nextRow, 12, "Place\nInitial\nOrder");
/* 398 */             columnHeaderTable.setObject(nextRow, 13, "Print\nat\nPlant");
/*     */             
/* 400 */             columnHeaderTable.setObject(nextRow, 14, "\n\nComments");
/*     */             
/* 402 */             columnHeaderTable.setRowFont(nextRow, new Font("Arial", 1, 9));
/* 403 */             columnHeaderTable.setRowBorderColor(nextRow, Color.lightGray);
/* 404 */             columnHeaderTable.setRowBorderColor(nextRow - 1, Color.lightGray);
/*     */             
/* 406 */             columnHeaderTable.setColBorderColor(nextRow, -1, Color.lightGray);
/* 407 */             columnHeaderTable.setColBorderColor(nextRow, 0, Color.lightGray);
/* 408 */             columnHeaderTable.setColBorderColor(nextRow, 1, Color.lightGray);
/* 409 */             columnHeaderTable.setColBorderColor(nextRow, 2, Color.lightGray);
/* 410 */             columnHeaderTable.setColBorderColor(nextRow, 3, Color.lightGray);
/* 411 */             columnHeaderTable.setColBorderColor(nextRow, 4, Color.lightGray);
/* 412 */             columnHeaderTable.setColBorderColor(nextRow, 5, Color.lightGray);
/* 413 */             columnHeaderTable.setColBorderColor(nextRow, 6, Color.lightGray);
/* 414 */             columnHeaderTable.setColBorderColor(nextRow, 7, Color.lightGray);
/* 415 */             columnHeaderTable.setColBorderColor(nextRow, 8, Color.lightGray);
/* 416 */             columnHeaderTable.setColBorderColor(nextRow, 9, Color.lightGray);
/* 417 */             columnHeaderTable.setColBorderColor(nextRow, 10, Color.lightGray);
/* 418 */             columnHeaderTable.setColBorderColor(nextRow, 11, Color.lightGray);
/* 419 */             columnHeaderTable.setColBorderColor(nextRow, 12, Color.lightGray);
/* 420 */             columnHeaderTable.setColBorderColor(nextRow, 13, Color.lightGray);
/* 421 */             columnHeaderTable.setColBorderColor(nextRow, 14, Color.lightGray);
/*     */             
/* 423 */             columnHeaderTable.setRowBorderColor(nextRow, Color.lightGray);
/*     */             
/* 425 */             hbandType.addTable(columnHeaderTable, new Rectangle(0, 25, 800, 40));
/*     */             
/* 427 */             nextRow = 0;
/*     */             
/* 429 */             DefaultTableLens monthTableLens = new DefaultTableLens(1, 15);
/*     */             
/* 431 */             nextRow = 0;
/*     */ 
/*     */             
/* 434 */             monthTableLens.setObject(nextRow, 0, monthNameString);
/* 435 */             monthTableLens.setSpan(nextRow, 0, new Dimension(15, 1));
/* 436 */             monthTableLens.setRowAlignment(nextRow, 1);
/* 437 */             monthTableLens.setRowFont(nextRow, new Font("Arial", 1, 12));
/* 438 */             monthTableLens.setRowHeight(nextRow, 14);
/* 439 */             monthTableLens.setColBorderColor(nextRow, -1, Color.white);
/* 440 */             monthTableLens.setColBorderColor(Color.white);
/* 441 */             monthTableLens.setRowBorderColor(nextRow, Color.white);
/* 442 */             monthTableLens.setRowBorderColor(nextRow - 1, Color.white);
/* 443 */             monthTableLens.setRowBackground(nextRow, Color.lightGray);
/*     */             
/* 445 */             hbandMonth.addTable(monthTableLens, new Rectangle(800, 800));
/* 446 */             hbandMonth.setBottomBorder(0);
/*     */             
/* 448 */             footer.setVisible(true);
/* 449 */             footer.setHeight(0.05F);
/* 450 */             footer.setShrinkToFit(true);
/* 451 */             footer.setBottomBorder(0);
/*     */             
/* 453 */             group = new DefaultSectionLens(null, group, footer);
/* 454 */             group = new DefaultSectionLens(null, group, hbandMonth);
/* 455 */             group = new DefaultSectionLens(null, group, footer);
/*     */ 
/*     */             
/* 458 */             for (int j = 0; j < selections.size(); j++) {
/*     */ 
/*     */ 
/*     */               
/* 462 */               statusJSPupdate.updateStatus(numSelections, j, "start_report", 0);
/*     */ 
/*     */               
/* 465 */               Selection sel = (Selection)selections.elementAt(j);
/* 466 */               String releaseDate = MilestoneHelper.getFormatedDate(sel.getStreetDate());
/*     */               
/* 468 */               String status = (sel.getSelectionStatus() != null && sel.getSelectionStatus().getName() != null) ? 
/* 469 */                 sel.getSelectionStatus().getName() : "";
/*     */               
/* 471 */               if (status.equalsIgnoreCase("TBS")) {
/* 472 */                 releaseDate = "TBS " + releaseDate;
/*     */               }
/* 474 */               else if (status.equalsIgnoreCase("In The Works")) {
/* 475 */                 releaseDate = "ITW " + releaseDate;
/*     */               } 
/* 477 */               String releaseWeek = MilestoneHelper.getReleaseWeekString(sel);
/* 478 */               if (releaseWeek != null) {
/*     */                 
/* 480 */                 int slashIndex = releaseWeek.indexOf("/");
/* 481 */                 if (slashIndex != -1) {
/* 482 */                   releaseWeek = releaseWeek.substring(0, slashIndex).trim();
/*     */                 }
/*     */               } 
/* 485 */               String projectNo = sel.getProjectID();
/* 486 */               String labelContact = "";
/* 487 */               if (sel.getLabelContact() != null && sel.getLabelContact().getName() != null) {
/* 488 */                 labelContact = sel.getLabelContact().getName();
/*     */               }
/* 490 */               String sellCode = (sel.getSellCode() != null) ? sel.getSellCode() : "   ";
/* 491 */               if (sellCode != null && sellCode.startsWith("-1")) {
/* 492 */                 sellCode = "";
/*     */               }
/* 494 */               String retailCode = "";
/* 495 */               if (sel.getPriceCode() != null && sel.getPriceCode().getRetailCode() != null) {
/* 496 */                 retailCode = sel.getPriceCode().getRetailCode();
/*     */               }
/* 498 */               String price = "";
/* 499 */               if (sel.getPriceCode() != null && sel.getPriceCode().getTotalCost() > 0.0F) {
/* 500 */                 price = "$" + MilestoneHelper.formatDollarPrice(sel.getPriceCode().getTotalCost());
/*     */               }
/* 502 */               String label = (sel.getLabel() != null && sel.getLabel().getName() != null) ? 
/* 503 */                 sel.getLabel().getName() : "";
/*     */               
/* 505 */               String selection = (sel.getSelectionNo() != null) ? sel.getSelectionNo() : "";
/*     */               
/* 507 */               String upc = (sel.getUpc() != null) ? sel.getUpc() : "";
/*     */ 
/*     */               
/* 510 */               upc = MilestoneHelper_2.getRMSReportFormat(upc, "UPC", sel.getIsDigital());
/*     */               
/* 512 */               String comments = (sel.getSelectionComments() != null) ? sel.getSelectionComments() : "";
/*     */               
/* 514 */               String[] checkStrings = { comments, labelContact };
/* 515 */               int[] checkStringLengths = { 20, 10 };
/*     */               
/* 517 */               int extraLines = MilestoneHelper.lineCount(checkStrings, checkStringLengths);
/* 518 */               if (extraLines > 3) {
/* 519 */                 extraLines -= 4;
/*     */               }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 526 */               Vector tasks = MilestoneHelper.getTasksForUmeCustomProdScheduleReport(sel);
/*     */ 
/*     */               
/* 529 */               nextRow = 0;
/*     */               
/* 531 */               subTable = new DefaultTableLens(2, 15);
/*     */               
/* 533 */               subTable.setColWidth(0, 110);
/* 534 */               subTable.setColWidth(1, 80);
/* 535 */               subTable.setColWidth(2, 80);
/* 536 */               subTable.setColWidth(3, 80);
/* 537 */               subTable.setColWidth(4, 190);
/* 538 */               subTable.setColWidth(5, 75);
/* 539 */               subTable.setColWidth(6, 80);
/* 540 */               subTable.setColWidth(7, 85);
/* 541 */               subTable.setColWidth(8, 85);
/* 542 */               subTable.setColWidth(9, 80);
/* 543 */               subTable.setColWidth(10, 80);
/* 544 */               subTable.setColWidth(11, 75);
/* 545 */               subTable.setColWidth(12, 80);
/* 546 */               subTable.setColWidth(13, 80);
/* 547 */               subTable.setColWidth(14, 210);
/*     */ 
/*     */               
/* 550 */               subTable.setRowHeight(nextRow, 9);
/* 551 */               subTable.setRowAlignment(nextRow, 10);
/* 552 */               subTable.setRowFont(nextRow, new Font("Arial", 0, 8));
/* 553 */               subTable.setRowFont(nextRow + 1, new Font("Arial", 0, 8));
/* 554 */               subTable.setRowBorderColor(nextRow, Color.lightGray);
/* 555 */               subTable.setRowBorderColor(nextRow - 1, Color.lightGray);
/*     */ 
/*     */               
/* 558 */               subTable.setSpan(nextRow, 0, new Dimension(1, 2));
/*     */               
/* 560 */               subTable.setObject(nextRow, 0, 
/* 561 */                   String.valueOf(releaseDate) + "\n" + projectNo + "\n" + labelContact);
/*     */ 
/*     */               
/* 564 */               subTable.setBackground(nextRow, 1, Color.lightGray);
/* 565 */               subTable.setBackground(nextRow, 2, Color.lightGray);
/* 566 */               subTable.setBackground(nextRow, 3, Color.lightGray);
/*     */               
/* 568 */               subTable.setRowAlignment(nextRow + 1, 10);
/* 569 */               subTable.setRowBorderColor(nextRow + 1, Color.lightGray);
/* 570 */               for (int z = -1; z <= numColumns; z++) {
/*     */                 
/* 572 */                 subTable.setColBorder(nextRow, z, 4097);
/* 573 */                 subTable.setColBorderColor(nextRow, z, Color.lightGray);
/*     */               } 
/*     */               
/* 576 */               subTable.setSpan(nextRow + 1, 1, new Dimension(3, 1));
/* 577 */               subTable.setObject(nextRow + 1, 1, String.valueOf(sel.getFlArtist()) + "\n" + sel.getTitle());
/*     */ 
/*     */               
/* 580 */               subTable.setFont(nextRow, 4, new Font("Arial", 1, 9));
/* 581 */               subTable.setObject(nextRow, 4, "Due Dates");
/* 582 */               subTable.setBackground(nextRow, 4, Color.lightGray);
/*     */               
/* 584 */               for (int y = 0; y < extraLines; y++) {
/* 585 */                 upc = String.valueOf(upc) + "\n";
/*     */               }
/* 587 */               subTable.setObject(nextRow + 1, 4, String.valueOf(selection) + "\n" + upc);
/*     */               
/* 589 */               String CLR = "";
/* 590 */               String ART = "";
/* 591 */               String LCC = "";
/* 592 */               String ACC = "";
/* 593 */               String RCM = "";
/* 594 */               String PO = "";
/* 595 */               String GFR = "";
/* 596 */               String PIO = "";
/* 597 */               String PAP = "";
/*     */ 
/*     */               
/* 600 */               String CLRcom = "";
/* 601 */               String ARTcom = "";
/* 602 */               String LCCcom = "";
/* 603 */               String ACCcom = "";
/* 604 */               String RCMcom = "";
/* 605 */               String POcom = "";
/* 606 */               String GFRcom = "";
/* 607 */               String PIOcom = "";
/* 608 */               String PAPcom = "";
/*     */ 
/*     */               
/* 611 */               String taskDueDate = "", taskCompletionDate = "", taskVendor = "";
/* 612 */               ScheduledTask task = null;
/*     */               
/* 614 */               if (tasks != null)
/*     */               {
/* 616 */                 for (int z = 0; z < tasks.size(); z++) {
/*     */                   
/* 618 */                   task = (ScheduledTask)tasks.get(z);
/*     */                   
/* 620 */                   if (task != null && task.getDueDate() != null) {
/*     */                     
/* 622 */                     String taskStatus = task.getScheduledTaskStatus();
/* 623 */                     SimpleDateFormat dueDateFormatter = new SimpleDateFormat("M/dd");
/*     */ 
/*     */                     
/* 626 */                     String dueDate = String.valueOf(dueDateFormatter.format(task.getDueDate().getTime())) + 
/* 627 */                       " " + MilestoneHelper.getDayType(sel.getCalendarGroup(), task.getDueDate());
/* 628 */                     String completionDate = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/* 629 */                     taskVendor = (task.getVendor() != null) ? (String.valueOf(task.getVendor()) + "\n") : "\n";
/*     */                     
/* 631 */                     if (!completionDate.equals("")) {
/* 632 */                       completionDate = String.valueOf(completionDate) + "\n";
/*     */                     }
/* 634 */                     if (taskStatus.equalsIgnoreCase("N/A")) {
/* 635 */                       completionDate = "N/A\n";
/*     */                     }
/* 637 */                     String taskAbbrev = MilestoneHelper.getTaskAbbreivationNameById(task.getTaskAbbreviationID());
/* 638 */                     String taskComment = "";
/*     */                     
/* 640 */                     if (task.getComments() != null && !task.getComments().equals("")) {
/* 641 */                       taskComment = task.getComments();
/*     */                     }
/* 643 */                     if (taskAbbrev.equalsIgnoreCase("CLR")) {
/*     */                       
/* 645 */                       CLR = dueDate;
/* 646 */                       CLRcom = String.valueOf(completionDate) + taskVendor;
/*     */                     }
/* 648 */                     else if (taskAbbrev.equalsIgnoreCase("ART")) {
/*     */                       
/* 650 */                       ART = dueDate;
/* 651 */                       ARTcom = String.valueOf(completionDate) + taskVendor;
/*     */                     }
/* 653 */                     else if (taskAbbrev.equalsIgnoreCase("LCC")) {
/*     */                       
/* 655 */                       LCC = dueDate;
/* 656 */                       LCCcom = String.valueOf(completionDate) + taskVendor;
/*     */                     }
/* 658 */                     else if (taskAbbrev.equalsIgnoreCase("ACC")) {
/*     */                       
/* 660 */                       ACC = dueDate;
/* 661 */                       ACCcom = String.valueOf(completionDate) + taskVendor;
/*     */                     }
/* 663 */                     else if (taskAbbrev.equalsIgnoreCase("RCM")) {
/*     */                       
/* 665 */                       RCM = dueDate;
/* 666 */                       RCMcom = String.valueOf(completionDate) + taskVendor;
/*     */                     }
/* 668 */                     else if (taskAbbrev.equalsIgnoreCase("PO")) {
/*     */                       
/* 670 */                       PO = dueDate;
/* 671 */                       POcom = String.valueOf(completionDate) + taskVendor;
/*     */                     }
/* 673 */                     else if (taskAbbrev.equalsIgnoreCase("GFR")) {
/*     */                       
/* 675 */                       GFR = dueDate;
/* 676 */                       GFRcom = String.valueOf(completionDate) + taskVendor;
/*     */                     }
/* 678 */                     else if (taskAbbrev.equalsIgnoreCase("PIO")) {
/*     */                       
/* 680 */                       PIO = dueDate;
/* 681 */                       PIOcom = String.valueOf(completionDate) + taskVendor;
/*     */                     }
/* 683 */                     else if (taskAbbrev.equalsIgnoreCase("PAP")) {
/*     */                       
/* 685 */                       PAP = dueDate;
/* 686 */                       PAPcom = String.valueOf(completionDate) + taskVendor;
/*     */                     } 
/*     */                     
/* 689 */                     task = null;
/*     */                   } 
/*     */                 } 
/*     */               }
/*     */ 
/*     */               
/* 695 */               subTable.setRowBorderColor(nextRow, Color.lightGray);
/* 696 */               subTable.setRowBorderColor(nextRow + 1, Color.lightGray);
/*     */               
/* 698 */               for (int a = -1; a <= numColumns; a++) {
/*     */                 
/* 700 */                 subTable.setColBorder(nextRow, a, 4097);
/* 701 */                 subTable.setColBorderColor(nextRow, a, Color.lightGray);
/* 702 */                 subTable.setColBorder(nextRow + 1, a, 4097);
/* 703 */                 subTable.setColBorderColor(nextRow + 1, a, Color.lightGray);
/*     */               } 
/*     */ 
/*     */               
/* 707 */               subTable.setObject(nextRow, 5, CLR);
/* 708 */               subTable.setBackground(nextRow, 5, Color.lightGray);
/* 709 */               subTable.setObject(nextRow + 1, 5, CLRcom);
/*     */ 
/*     */               
/* 712 */               subTable.setObject(nextRow, 6, ART);
/* 713 */               subTable.setBackground(nextRow, 6, Color.lightGray);
/* 714 */               subTable.setObject(nextRow + 1, 6, ARTcom);
/*     */ 
/*     */               
/* 717 */               subTable.setObject(nextRow, 7, LCC);
/* 718 */               subTable.setBackground(nextRow, 7, Color.lightGray);
/* 719 */               subTable.setObject(nextRow + 1, 7, LCCcom);
/*     */ 
/*     */               
/* 722 */               subTable.setObject(nextRow, 8, ACC);
/* 723 */               subTable.setBackground(nextRow, 8, Color.lightGray);
/* 724 */               subTable.setObject(nextRow + 1, 8, ACCcom);
/*     */ 
/*     */               
/* 727 */               subTable.setObject(nextRow, 9, RCM);
/* 728 */               subTable.setBackground(nextRow, 9, Color.lightGray);
/* 729 */               subTable.setObject(nextRow + 1, 9, RCMcom);
/*     */ 
/*     */               
/* 732 */               subTable.setObject(nextRow, 10, PO);
/* 733 */               subTable.setBackground(nextRow, 10, Color.lightGray);
/* 734 */               subTable.setObject(nextRow + 1, 10, POcom);
/*     */ 
/*     */               
/* 737 */               subTable.setObject(nextRow, 11, GFR);
/* 738 */               subTable.setBackground(nextRow, 11, Color.lightGray);
/* 739 */               subTable.setObject(nextRow + 1, 11, GFRcom);
/*     */ 
/*     */               
/* 742 */               subTable.setObject(nextRow, 12, PIO);
/* 743 */               subTable.setBackground(nextRow, 12, Color.lightGray);
/* 744 */               subTable.setObject(nextRow + 1, 12, PIOcom);
/*     */ 
/*     */               
/* 747 */               subTable.setObject(nextRow, 13, PAP);
/* 748 */               subTable.setBackground(nextRow, 13, Color.lightGray);
/* 749 */               subTable.setObject(nextRow + 1, 13, PAPcom);
/*     */ 
/*     */ 
/*     */               
/* 753 */               subTable.setAlignment(nextRow, numColumns - 1, 1);
/* 754 */               subTable.setAlignment(nextRow, numColumns - 1, 8);
/* 755 */               subTable.setSpan(nextRow, numColumns - 1, new Dimension(1, 2));
/* 756 */               subTable.setObject(nextRow, numColumns - 1, comments);
/*     */ 
/*     */ 
/*     */               
/* 760 */               Font holidayFont = new Font("Arial", 3, 9);
/* 761 */               Font nonHolidayFont = new Font("Arial", 1, 9);
/* 762 */               for (int colIdx = 5; colIdx <= 13; colIdx++) {
/* 763 */                 String dueDate = subTable.getObject(nextRow, colIdx).toString();
/* 764 */                 if (dueDate != null && dueDate.length() > 0) {
/* 765 */                   char lastChar = dueDate.charAt(dueDate.length() - 1);
/* 766 */                   if (Character.isLetter(lastChar)) {
/* 767 */                     subTable.setFont(nextRow, colIdx, holidayFont);
/*     */                   } else {
/*     */                     
/* 770 */                     subTable.setFont(nextRow, colIdx, nonHolidayFont);
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */               
/* 775 */               nextRow += 2;
/*     */               
/* 777 */               body = new SectionBand(report);
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 782 */               double lfLineCount = 1.5D;
/*     */               
/* 784 */               if (extraLines > 0 || label.length() > 17 || sel.getTitle().length() > 20) {
/*     */                 
/* 786 */                 body.setHeight(1.5F);
/*     */               }
/*     */               else {
/*     */                 
/* 790 */                 body.setHeight(1.0F);
/*     */               } 
/*     */               
/* 793 */               if (extraLines > 0 || label.length() > 17 || sel.getTitle().length() > 20 || 
/* 794 */                 CLRcom.length() > 25 || ARTcom.length() > 25 || LCCcom.length() > 25 || 
/* 795 */                 ACCcom.length() > 25 || 
/* 796 */                 GFRcom.length() > 25 || RCMcom.length() > 25 || 
/* 797 */                 POcom.length() > 25 || 
/* 798 */                 PAPcom.length() > 25 || 
/* 799 */                 PIOcom.length() > 25) {
/*     */                 
/* 801 */                 if (lfLineCount < extraLines * 0.2D) {
/* 802 */                   lfLineCount = extraLines * 0.2D;
/*     */                 }
/* 804 */                 if (lfLineCount < (CLRcom.length() / 7) * 0.2D) {
/* 805 */                   lfLineCount = (CLRcom.length() / 7) * 0.2D;
/*     */                 }
/* 807 */                 if (lfLineCount < (ARTcom.length() / 8) * 0.2D) {
/* 808 */                   lfLineCount = (ARTcom.length() / 8) * 0.2D;
/*     */                 }
/* 810 */                 if (lfLineCount < (LCCcom.length() / 8) * 0.2D) {
/* 811 */                   lfLineCount = (LCCcom.length() / 8) * 0.2D;
/*     */                 }
/* 813 */                 if (lfLineCount < (ACCcom.length() / 8) * 0.2D) {
/* 814 */                   lfLineCount = (ACCcom.length() / 8) * 0.2D;
/*     */                 }
/* 816 */                 if (lfLineCount < (sel.getTitle().length() / 8) * 0.2D) {
/* 817 */                   lfLineCount = (sel.getTitle().length() / 8) * 0.2D;
/*     */                 }
/*     */                 
/* 820 */                 if (lfLineCount < (RCMcom.length() / 7) * 0.2D) {
/* 821 */                   lfLineCount = (RCMcom.length() / 7) * 0.2D;
/*     */                 }
/* 823 */                 if (lfLineCount < (POcom.length() / 7) * 0.2D) {
/* 824 */                   lfLineCount = (POcom.length() / 7) * 0.2D;
/*     */                 }
/* 826 */                 if (lfLineCount < (GFRcom.length() / 7) * 0.2D) {
/* 827 */                   lfLineCount = (GFRcom.length() / 7) * 0.2D;
/*     */                 }
/* 829 */                 if (lfLineCount < (PAPcom.length() / 7) * 0.2D) {
/* 830 */                   lfLineCount = (PAPcom.length() / 7) * 0.2D;
/*     */                 }
/* 832 */                 if (lfLineCount < (PIOcom.length() / 7) * 0.2D) {
/* 833 */                   lfLineCount = (PIOcom.length() / 7) * 0.2D;
/*     */                 }
/* 835 */                 body.setHeight((float)lfLineCount);
/*     */               }
/* 837 */               else if (extraLines > 0 || label.length() > 17 || sel.getTitle().length() > 20 || 
/* 838 */                 CLRcom.length() > 5 || ARTcom.length() > 5 || LCCcom.length() > 5 || 
/* 839 */                 ACCcom.length() > 5 || POcom.length() > 5 || 
/* 840 */                 GFRcom.length() > 5 || RCMcom.length() > 5 || 
/* 841 */                 PIOcom.length() > 5 || 
/* 842 */                 PAPcom.length() > 5) {
/*     */ 
/*     */ 
/*     */                 
/* 846 */                 if (lfLineCount < extraLines * 0.2D) {
/* 847 */                   lfLineCount = extraLines * 0.2D;
/*     */                 }
/* 849 */                 if (lfLineCount < (CLRcom.length() / 7) * 0.2D) {
/* 850 */                   lfLineCount = (CLRcom.length() / 7) * 0.2D;
/*     */                 }
/* 852 */                 if (lfLineCount < (ARTcom.length() / 8) * 0.2D) {
/* 853 */                   lfLineCount = (ARTcom.length() / 8) * 0.2D;
/*     */                 }
/* 855 */                 if (lfLineCount < (LCCcom.length() / 8) * 0.2D) {
/* 856 */                   lfLineCount = (LCCcom.length() / 8) * 0.2D;
/*     */                 }
/* 858 */                 if (lfLineCount < (ACCcom.length() / 8) * 0.2D) {
/* 859 */                   lfLineCount = (ACCcom.length() / 8) * 0.2D;
/*     */                 }
/* 861 */                 if (lfLineCount < (sel.getTitle().length() / 8) * 0.2D) {
/* 862 */                   lfLineCount = (sel.getTitle().length() / 8) * 0.2D;
/*     */                 }
/*     */                 
/* 865 */                 if (lfLineCount < (RCMcom.length() / 7) * 0.2D) {
/* 866 */                   lfLineCount = (RCMcom.length() / 7) * 0.2D;
/*     */                 }
/* 868 */                 if (lfLineCount < (POcom.length() / 7) * 0.2D) {
/* 869 */                   lfLineCount = (POcom.length() / 7) * 0.2D;
/*     */                 }
/* 871 */                 if (lfLineCount < (GFRcom.length() / 7) * 0.2D) {
/* 872 */                   lfLineCount = (GFRcom.length() / 7) * 0.2D;
/*     */                 }
/* 874 */                 if (lfLineCount < (PIOcom.length() / 7) * 0.2D) {
/* 875 */                   lfLineCount = (PIOcom.length() / 7) * 0.2D;
/*     */                 }
/*     */                 
/* 878 */                 if (lfLineCount < (PAPcom.length() / 7) * 0.2D) {
/* 879 */                   lfLineCount = (PAPcom.length() / 7) * 0.2D;
/*     */                 }
/*     */                 
/* 882 */                 body.setHeight((float)lfLineCount);
/*     */               
/*     */               }
/*     */               else {
/*     */                 
/* 887 */                 body.setHeight(1.0F);
/*     */               } 
/*     */               
/* 890 */               body.addTable(subTable, new Rectangle(800, 800));
/* 891 */               body.setBottomBorder(0);
/* 892 */               body.setTopBorder(0);
/* 893 */               body.setShrinkToFit(true);
/* 894 */               body.setVisible(true);
/*     */               
/* 896 */               group = new DefaultSectionLens(null, group, body);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 901 */         group = new DefaultSectionLens(hbandType, group, null);
/*     */         
/* 903 */         report.addSection(group, rowCountTable);
/* 904 */         report.addPageBreak();
/* 905 */         group = null;
/*     */       }
/*     */     
/*     */     }
/* 909 */     catch (Exception exception) {}
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
/*     */   public static int insertConfigHeader(DefaultTableLens table_contents, String title, int nextRow, int cols) {
/* 926 */     int COL_LINE_STYLE = 4097;
/* 927 */     int HEADER_FONT_SIZE = 12;
/*     */     
/* 929 */     table_contents.setObject(nextRow, 0, "");
/* 930 */     table_contents.setSpan(nextRow, 0, new Dimension(cols, 1));
/* 931 */     table_contents.setRowHeight(nextRow, 1);
/* 932 */     table_contents.setRowBackground(nextRow, Color.white);
/*     */     
/* 934 */     table_contents.setColBorderColor(nextRow, -1, Color.black);
/* 935 */     table_contents.setColBorder(nextRow, -1, 4097);
/* 936 */     table_contents.setColBorderColor(nextRow, cols - 1, Color.black);
/* 937 */     table_contents.setColBorder(nextRow, cols - 1, 4097);
/*     */     
/* 939 */     table_contents.setRowBorderColor(nextRow, Color.white);
/* 940 */     table_contents.setRowBorder(nextRow, 266240);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 945 */     table_contents.setColBorderColor(nextRow + 1, -1, Color.black);
/* 946 */     table_contents.setColBorder(nextRow + 1, -1, 4097);
/* 947 */     table_contents.setColBorderColor(nextRow + 1, cols - 1, Color.black);
/* 948 */     table_contents.setColBorder(nextRow + 1, cols - 1, 4097);
/*     */ 
/*     */     
/* 951 */     table_contents.setAlignment(nextRow + 1, 0, 2);
/* 952 */     table_contents.setSpan(nextRow + 1, 0, new Dimension(cols, 1));
/* 953 */     table_contents.setObject(nextRow + 1, 0, title);
/* 954 */     table_contents.setRowFont(nextRow + 1, new Font("Arial", 3, 12));
/*     */     
/* 956 */     nextRow += 2;
/*     */     
/* 958 */     table_contents.setObject(nextRow, 0, "");
/* 959 */     table_contents.setSpan(nextRow, 0, new Dimension(cols, 1));
/* 960 */     table_contents.setRowHeight(nextRow, 1);
/* 961 */     table_contents.setRowBackground(nextRow, Color.white);
/*     */     
/* 963 */     table_contents.setColBorderColor(nextRow, -1, Color.white);
/* 964 */     table_contents.setColBorder(nextRow, -1, 4097);
/* 965 */     table_contents.setColBorderColor(nextRow, cols, Color.black);
/* 966 */     table_contents.setColBorder(nextRow, cols, 4097);
/*     */     
/* 968 */     table_contents.setRowBorderColor(nextRow, Color.white);
/* 969 */     table_contents.setRowBorder(nextRow, 266240);
/*     */     
/* 971 */     return nextRow;
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\UmeCustomProdScheduleForPrintSubHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */