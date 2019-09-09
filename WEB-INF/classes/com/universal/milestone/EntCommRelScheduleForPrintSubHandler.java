/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.ComponentLog;
/*     */ import com.techempower.gemini.Context;
/*     */ import com.techempower.gemini.GeminiApplication;
/*     */ import com.universal.milestone.Cache;
/*     */ import com.universal.milestone.EntCommRelScheduleForPrintSubHandler;
/*     */ import com.universal.milestone.Form;
/*     */ import com.universal.milestone.LookupObject;
/*     */ import com.universal.milestone.MilestoneHelper;
/*     */ import com.universal.milestone.MilestoneHelper_2;
/*     */ import com.universal.milestone.SecureHandler;
/*     */ import com.universal.milestone.Selection;
/*     */ import com.universal.milestone.SelectionManager;
/*     */ import com.universal.milestone.StatusJSPupdate;
/*     */ import inetsoft.report.SectionBand;
/*     */ import inetsoft.report.SeparatorElement;
/*     */ import inetsoft.report.XStyleSheet;
/*     */ import inetsoft.report.lens.DefaultSectionLens;
/*     */ import inetsoft.report.lens.DefaultTableLens;
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Calendar;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
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
/*     */ public class EntCommRelScheduleForPrintSubHandler
/*     */   extends SecureHandler
/*     */ {
/*     */   public static final String COMPONENT_CODE = "hCRel";
/*     */   public GeminiApplication application;
/*     */   public ComponentLog log;
/*     */   
/*     */   public EntCommRelScheduleForPrintSubHandler(GeminiApplication application) {
/*  70 */     this.application = application;
/*  71 */     this.log = application.getLog("hCRel");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  79 */   public String getDescription() { return "Sub Report"; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void fillEntCommRelScheduleForPrint(XStyleSheet report, Context context) {
/*  90 */     Color SHADED_AREA_COLOR = Color.lightGray;
/*  91 */     int COL_LINE_STYLE = 4097;
/*  92 */     int HEADER_FONT_SIZE = 12;
/*  93 */     int NUM_COLUMNS = 12;
/*     */     
/*  95 */     double ldLineVal = 0.3D;
/*     */     
/*  97 */     SectionBand hbandType = new SectionBand(report);
/*  98 */     SectionBand hbandCategory = new SectionBand(report);
/*  99 */     SectionBand hbandDate = new SectionBand(report);
/* 100 */     SectionBand body = new SectionBand(report);
/* 101 */     SectionBand footer = new SectionBand(report);
/* 102 */     SectionBand spacer = new SectionBand(report);
/* 103 */     DefaultSectionLens group = null;
/*     */     
/* 105 */     footer.setVisible(true);
/* 106 */     footer.setHeight(0.1F);
/* 107 */     footer.setShrinkToFit(false);
/* 108 */     footer.setBottomBorder(0);
/*     */     
/* 110 */     spacer.setVisible(true);
/* 111 */     spacer.setHeight(0.05F);
/* 112 */     spacer.setShrinkToFit(false);
/* 113 */     spacer.setBottomBorder(0);
/*     */ 
/*     */     
/* 116 */     int separatorLineStyle = 266240;
/* 117 */     Color separatorLineColor = Color.black;
/*     */ 
/*     */     
/* 120 */     int monthLineStyle = 4097;
/* 121 */     Color monthLineColor = Color.black;
/*     */     
/* 123 */     StatusJSPupdate aStatusUpdate = new StatusJSPupdate(context);
/* 124 */     aStatusUpdate.setInternalCounter(true);
/*     */ 
/*     */ 
/*     */     
/* 128 */     aStatusUpdate.updateStatus(0, 0, "start_gathering", 0);
/*     */ 
/*     */ 
/*     */     
/* 132 */     Vector selections = MilestoneHelper.getSelectionsForReport(context);
/*     */ 
/*     */     
/* 135 */     aStatusUpdate.updateStatus(0, 0, "start_report", 10);
/*     */ 
/*     */     
/* 138 */     MilestoneHelper.setSelectionSorting(selections, 5);
/* 139 */     Collections.sort(selections);
/*     */ 
/*     */     
/* 142 */     MilestoneHelper.setSelectionSorting(selections, 4);
/* 143 */     Collections.sort(selections);
/*     */ 
/*     */     
/* 146 */     MilestoneHelper.setSelectionSorting(selections, 3);
/* 147 */     Collections.sort(selections);
/*     */ 
/*     */     
/* 150 */     MilestoneHelper.setSelectionSorting(selections, 2);
/* 151 */     Collections.sort(selections);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 158 */     MilestoneHelper.setSelectionSorting(selections, 1);
/* 159 */     Collections.sort(selections);
/*     */ 
/*     */     
/* 162 */     MilestoneHelper.setSelectionSorting(selections, 16);
/* 163 */     Collections.sort(selections);
/*     */ 
/*     */     
/*     */     try {
/* 167 */       DefaultTableLens table_contents = null;
/* 168 */       DefaultTableLens rowCountTable = null;
/* 169 */       DefaultTableLens columnHeaderTable = null;
/* 170 */       DefaultTableLens subTable = null;
/* 171 */       DefaultTableLens monthTableLens = null;
/* 172 */       DefaultTableLens dateTableLens = null;
/*     */       
/* 174 */       rowCountTable = new DefaultTableLens(2, 10000);
/*     */ 
/*     */       
/* 177 */       int nextRow = 0;
/*     */ 
/*     */       
/* 180 */       SeparatorElement topSeparator = (SeparatorElement)report.getElement("separator_top");
/* 181 */       SeparatorElement bottomHeaderSeparator = (SeparatorElement)report.getElement("separator_bottom_header");
/* 182 */       SeparatorElement bottomSeparator = (SeparatorElement)report.getElement("separator_bottom");
/* 183 */       if (topSeparator != null) {
/*     */         
/* 185 */         topSeparator.setStyle(separatorLineStyle);
/* 186 */         topSeparator.setForeground(separatorLineColor);
/*     */       } 
/* 188 */       if (bottomHeaderSeparator != null) {
/*     */         
/* 190 */         bottomHeaderSeparator.setStyle(separatorLineStyle);
/* 191 */         bottomHeaderSeparator.setForeground(separatorLineColor);
/*     */       } 
/* 193 */       if (bottomSeparator != null) {
/*     */         
/* 195 */         bottomSeparator.setStyle(separatorLineStyle);
/* 196 */         bottomSeparator.setForeground(separatorLineColor);
/*     */       } 
/*     */ 
/*     */       
/* 200 */       Form reportForm = (Form)context.getSessionValue("reportForm");
/*     */       
/* 202 */       Calendar beginStDate = (reportForm.getStringValue("beginDate") != null && 
/* 203 */         reportForm.getStringValue("beginDate").length() > 0) ? 
/* 204 */         MilestoneHelper.getDate(reportForm.getStringValue("beginDate")) : null;
/*     */       
/* 206 */       Calendar endStDate = (reportForm.getStringValue("endDate") != null && 
/* 207 */         reportForm.getStringValue("endDate").length() > 0) ? 
/* 208 */         MilestoneHelper.getDate(reportForm.getStringValue("endDate")) : null;
/*     */       
/* 210 */       report.setElement("crs_startdate", MilestoneHelper.getFormatedDate(beginStDate));
/* 211 */       report.setElement("crs_enddate", MilestoneHelper.getFormatedDate(endStDate));
/*     */       
/* 213 */       SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
/* 214 */       String todayLong = formatter.format(new Date());
/* 215 */       report.setElement("crs_bottomdate", todayLong);
/*     */ 
/*     */       
/* 218 */       Vector groupedByMonth = MilestoneHelper.groupSelectionsByReleaseMonth(selections);
/*     */ 
/*     */       
/* 221 */       int numExtraRows = groupedByMonth.size() * 4;
/*     */ 
/*     */       
/* 224 */       int numSelections = selections.size();
/* 225 */       int numRows = numSelections + numExtraRows;
/*     */       
/* 227 */       int numColumns = 10;
/*     */       
/* 229 */       nextRow = 0;
/*     */       
/* 231 */       hbandType = new SectionBand(report);
/* 232 */       hbandType.setHeight(0.5F);
/* 233 */       hbandType.setShrinkToFit(false);
/* 234 */       hbandType.setVisible(true);
/*     */ 
/*     */       
/* 237 */       table_contents = new DefaultTableLens(1, numColumns);
/*     */       
/* 239 */       table_contents.setRowBorder(0);
/*     */       
/* 241 */       table_contents.setColWidth(0, 20);
/* 242 */       table_contents.setColWidth(1, 41);
/* 243 */       table_contents.setColWidth(2, 100);
/* 244 */       table_contents.setColWidth(3, 100);
/* 245 */       table_contents.setColWidth(4, 60);
/* 246 */       table_contents.setColWidth(5, 30);
/* 247 */       table_contents.setColWidth(6, 60);
/* 248 */       table_contents.setColWidth(7, 50);
/* 249 */       table_contents.setColWidth(8, 50);
/* 250 */       table_contents.setColWidth(9, 50);
/*     */       
/* 252 */       table_contents.setColBorder(0);
/* 253 */       table_contents.setRowBorder(0);
/*     */       
/* 255 */       table_contents.setColAlignment(0, 2);
/* 256 */       table_contents.setColAlignment(1, 2);
/* 257 */       table_contents.setColAlignment(5, 2);
/*     */ 
/*     */       
/* 260 */       table_contents.setRowAlignment(nextRow, 32);
/* 261 */       table_contents.setRowHeight(nextRow, 55);
/*     */       
/* 263 */       table_contents.setObject(nextRow, 0, "Int'l\nDate");
/* 264 */       table_contents.setAlignment(nextRow, 0, 2);
/* 265 */       table_contents.setObject(nextRow, 1, "Street\nDate");
/* 266 */       table_contents.setAlignment(nextRow, 1, 2);
/* 267 */       table_contents.setObject(nextRow, 2, "Artist");
/* 268 */       table_contents.setObject(nextRow, 3, "Title");
/* 269 */       table_contents.setObject(nextRow, 4, "Product\nCategory");
/* 270 */       table_contents.setAlignment(nextRow, 5, 36);
/* 271 */       table_contents.setObject(nextRow, 5, "Price");
/* 272 */       table_contents.setObject(nextRow, 6, "Label");
/* 273 */       table_contents.setObject(nextRow, 7, "Local Product #");
/* 274 */       table_contents.setObject(nextRow, 8, "UPC");
/* 275 */       table_contents.setObject(nextRow, 9, "Project #");
/*     */       
/* 277 */       table_contents.setRowFont(nextRow, new Font("Arial", 3, 10));
/*     */       
/* 279 */       hbandType.addTable(table_contents, new Rectangle(0, 0, 800, 30));
/*     */       
/* 281 */       table_contents = new DefaultTableLens(1, numColumns);
/*     */       
/* 283 */       nextRow = 0;
/* 284 */       table_contents.setRowBackground(nextRow, Color.white);
/* 285 */       table_contents.setRowBorderColor(nextRow - 1, Color.black);
/*     */       
/* 287 */       table_contents.setRowBorder(nextRow, 0);
/* 288 */       table_contents.setColBorder(0);
/* 289 */       table_contents.setRowHeight(nextRow, 1);
/*     */       
/* 291 */       hbandType.addTable(table_contents, new Rectangle(0, 32, 800, 5));
/*     */ 
/*     */ 
/*     */       
/* 295 */       int statusBarCounter = 0;
/* 296 */       for (int n = 0; n < groupedByMonth.size(); n++) {
/*     */         
/* 298 */         Vector selectionsInMonth = (Vector)groupedByMonth.elementAt(n);
/* 299 */         statusBarCounter += selectionsInMonth.size();
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 305 */       for (int n = 0; n < groupedByMonth.size(); n++) {
/*     */ 
/*     */         
/* 308 */         Vector selectionsInMonth = (Vector)groupedByMonth.elementAt(n);
/* 309 */         if (selectionsInMonth == null) {
/* 310 */           selectionsInMonth = new Vector();
/*     */         }
/* 312 */         monthTableLens = new DefaultTableLens(2, numColumns);
/* 313 */         hbandCategory = new SectionBand(report);
/* 314 */         hbandCategory.setHeight(0.23F);
/* 315 */         hbandCategory.setShrinkToFit(false);
/* 316 */         hbandCategory.setVisible(true);
/* 317 */         hbandCategory.setBottomBorder(0);
/* 318 */         hbandCategory.setLeftBorder(0);
/* 319 */         hbandCategory.setRightBorder(0);
/* 320 */         hbandCategory.setTopBorder(0);
/*     */         
/* 322 */         nextRow = 0;
/*     */         
/* 324 */         monthTableLens.setRowBorderColor(nextRow - 1, Color.white);
/* 325 */         monthTableLens.setRowBorderColor(nextRow, Color.white);
/*     */         
/* 327 */         monthTableLens.setColWidth(0, 20);
/* 328 */         monthTableLens.setColWidth(1, 41);
/* 329 */         monthTableLens.setColWidth(2, 100);
/* 330 */         monthTableLens.setColWidth(3, 100);
/* 331 */         monthTableLens.setColWidth(4, 60);
/* 332 */         monthTableLens.setColWidth(5, 30);
/* 333 */         monthTableLens.setColWidth(6, 60);
/*     */         
/* 335 */         monthTableLens.setColBorder(0);
/*     */         
/* 337 */         monthTableLens.setColAlignment(0, 2);
/* 338 */         monthTableLens.setColAlignment(1, 2);
/* 339 */         monthTableLens.setColAlignment(5, 2);
/*     */ 
/*     */         
/* 342 */         String monthName = MilestoneHelper.getMonthNameForUmeReport(selectionsInMonth);
/*     */         
/* 344 */         monthTableLens.setRowAlignment(nextRow, 1);
/* 345 */         monthTableLens.setSpan(nextRow, 0, new Dimension(numColumns, 1));
/* 346 */         monthTableLens.setObject(nextRow, 0, monthName);
/*     */         
/* 348 */         monthTableLens.setRowFont(nextRow, new Font("Arial", 1, 10));
/* 349 */         monthTableLens.setRowBackground(nextRow, Color.lightGray);
/*     */         
/* 351 */         hbandCategory.addTable(monthTableLens, new Rectangle(0, 0, 800, 20));
/*     */         
/* 353 */         footer.setVisible(true);
/* 354 */         footer.setHeight(0.1F);
/* 355 */         footer.setShrinkToFit(false);
/* 356 */         footer.setBottomBorder(0);
/*     */         
/* 358 */         group = new DefaultSectionLens(null, group, spacer);
/* 359 */         group = new DefaultSectionLens(null, group, hbandCategory);
/* 360 */         group = new DefaultSectionLens(null, group, spacer);
/*     */ 
/*     */         
/* 363 */         MilestoneHelper.setSelectionSorting(selectionsInMonth, 5);
/* 364 */         Collections.sort(selectionsInMonth);
/*     */ 
/*     */         
/* 367 */         MilestoneHelper.setSelectionSorting(selectionsInMonth, 4);
/* 368 */         Collections.sort(selectionsInMonth);
/*     */ 
/*     */         
/* 371 */         MilestoneHelper.setSelectionSorting(selectionsInMonth, 3);
/* 372 */         Collections.sort(selectionsInMonth);
/*     */ 
/*     */         
/* 375 */         MilestoneHelper.setSelectionSorting(selectionsInMonth, 2);
/* 376 */         Collections.sort(selectionsInMonth);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 383 */         MilestoneHelper.setSelectionSorting(selectionsInMonth, 1);
/* 384 */         Collections.sort(selectionsInMonth);
/*     */ 
/*     */         
/* 387 */         MilestoneHelper.setSelectionSorting(selections, 16);
/* 388 */         Collections.sort(selections);
/*     */ 
/*     */         
/* 391 */         for (int i = 0; i < selectionsInMonth.size(); i++) {
/*     */ 
/*     */           
/* 394 */           aStatusUpdate.updateStatus(statusBarCounter, i, "start_report", 0);
/*     */ 
/*     */           
/* 397 */           Selection sel = (Selection)selectionsInMonth.elementAt(i);
/*     */           
/* 399 */           nextRow = 0;
/* 400 */           subTable = new DefaultTableLens(1, numColumns);
/*     */           
/* 402 */           subTable.setColWidth(0, 20);
/* 403 */           subTable.setColWidth(1, 41);
/* 404 */           subTable.setColWidth(2, 100);
/* 405 */           subTable.setColWidth(3, 100);
/* 406 */           subTable.setColWidth(4, 60);
/* 407 */           subTable.setColWidth(5, 30);
/* 408 */           subTable.setColWidth(6, 60);
/* 409 */           subTable.setColWidth(7, 50);
/* 410 */           subTable.setColWidth(8, 50);
/* 411 */           subTable.setColWidth(9, 50);
/*     */ 
/*     */ 
/*     */           
/* 415 */           String labelName = sel.getImprint();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 422 */           String price = "0.00";
/* 423 */           if (sel.getPriceCode() != null && 
/* 424 */             sel.getPriceCode().getTotalCost() > 0.0F) {
/* 425 */             price = MilestoneHelper.formatDollarPrice(sel.getPriceCode().getTotalCost());
/*     */           }
/*     */           
/* 428 */           LookupObject prodCategory = SelectionManager.getLookupObject(sel.getProductCategory().getAbbreviation(), 
/* 429 */               Cache.getProductCategories());
/* 430 */           String prodCategoryName = "";
/* 431 */           if (prodCategory != null) {
/* 432 */             prodCategoryName = prodCategory.getName();
/*     */           }
/*     */           
/* 435 */           subTable.setRowInsets(nextRow, new Insets(0, 0, 5, 0));
/*     */           
/* 437 */           String intlDate = "  " + MilestoneHelper.getFormatedDate(sel.getInternationalDate());
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 442 */           if (intlDate.length() < 8) {
/*     */             
/* 444 */             intlDate = String.valueOf(intlDate) + " ";
/*     */             
/* 446 */             if (intlDate.length() < 7) {
/* 447 */               intlDate = String.valueOf(intlDate) + " ";
/*     */             }
/*     */           } 
/* 450 */           String streetDate = "  " + MilestoneHelper.getFormatedDate(sel.getStreetDate());
/*     */           
/* 452 */           String status = (sel.getSelectionStatus() != null && sel.getSelectionStatus().getName() != null) ? 
/* 453 */             sel.getSelectionStatus().getName() : "";
/*     */ 
/*     */           
/* 456 */           if (status.equalsIgnoreCase("TBS")) {
/* 457 */             streetDate = "TBS " + streetDate;
/*     */           }
/* 459 */           else if (status.equalsIgnoreCase("In The Works")) {
/* 460 */             streetDate = "ITW " + streetDate;
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 465 */           if (streetDate.length() < 8) {
/*     */             
/* 467 */             streetDate = String.valueOf(streetDate) + " ";
/*     */             
/* 469 */             if (streetDate.length() < 7) {
/* 470 */               streetDate = String.valueOf(streetDate) + " ";
/*     */             }
/*     */           } 
/* 473 */           subTable.setObject(nextRow, 0, intlDate);
/* 474 */           subTable.setAlignment(nextRow, 0, 12);
/* 475 */           subTable.setObject(nextRow, 1, streetDate);
/* 476 */           subTable.setAlignment(nextRow, 1, 12);
/* 477 */           subTable.setObject(nextRow, 2, sel.getArtist().trim());
/* 478 */           subTable.setAlignment(nextRow, 2, 9);
/* 479 */           subTable.setObject(nextRow, 3, sel.getTitle());
/* 480 */           subTable.setAlignment(nextRow, 3, 9);
/* 481 */           subTable.setObject(nextRow, 4, prodCategoryName);
/* 482 */           subTable.setAlignment(nextRow, 4, 9);
/* 483 */           subTable.setObject(nextRow, 5, "$" + price);
/* 484 */           subTable.setAlignment(nextRow, 5, 12);
/* 485 */           subTable.setObject(nextRow, 6, labelName);
/* 486 */           subTable.setAlignment(nextRow, 6, 9);
/*     */ 
/*     */           
/* 489 */           String selId = "";
/* 490 */           if (SelectionManager.getLookupObjectValue(sel.getPrefixID()).equals("")) {
/* 491 */             selId = sel.getSelectionNo();
/*     */           } else {
/* 493 */             selId = String.valueOf(SelectionManager.getLookupObjectValue(sel.getPrefixID())) + sel.getSelectionNo();
/*     */           } 
/*     */           
/* 496 */           subTable.setObject(nextRow, 7, selId);
/* 497 */           subTable.setAlignment(nextRow, 7, 9);
/* 498 */           subTable.setObject(nextRow, 9, sel.getProjectID());
/* 499 */           subTable.setAlignment(nextRow, 9, 9);
/*     */ 
/*     */           
/* 502 */           String upc = sel.getUpc();
/* 503 */           upc = MilestoneHelper_2.getRMSReportFormat(upc, "UPC", sel.getIsDigital());
/*     */           
/* 505 */           subTable.setObject(nextRow, 8, upc);
/* 506 */           subTable.setAlignment(nextRow, 8, 9);
/*     */           
/* 508 */           subTable.setColBorder(0);
/* 509 */           subTable.setRowBorderColor(nextRow, Color.white);
/* 510 */           subTable.setRowBorder(nextRow - 1, 0);
/* 511 */           subTable.setRowBorder(nextRow, 4097);
/* 512 */           subTable.setRowBorderColor(nextRow, Color.lightGray);
/* 513 */           subTable.setRowFont(nextRow, new Font("Arial", 0, 8));
/*     */           
/* 515 */           body = new SectionBand(report);
/*     */           
/* 517 */           double lfLineCount = 1.5D;
/*     */           
/* 519 */           body.setHeight(1.0F);
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
/* 534 */           body.addTable(subTable, new Rectangle(800, 800));
/* 535 */           body.setBottomBorder(0);
/* 536 */           body.setTopBorder(0);
/* 537 */           body.setShrinkToFit(true);
/* 538 */           body.setVisible(true);
/*     */           
/* 540 */           group = new DefaultSectionLens(null, group, body);
/*     */         } 
/*     */       } 
/* 543 */       group = new DefaultSectionLens(hbandType, group, null);
/* 544 */       report.addSection(group, rowCountTable);
/* 545 */       group = null;
/*     */ 
/*     */       
/* 548 */       HttpServletResponse sresponse = context.getResponse();
/* 549 */       context.putDelivery("percent", new String("100"));
/* 550 */       context.includeJSP("status.jsp", "hiddenFrame");
/* 551 */       sresponse.setContentType("text/plain");
/* 552 */       sresponse.flushBuffer();
/*     */     }
/* 554 */     catch (Exception e) {
/*     */       
/* 556 */       System.out.println(">>>>>>>>ReportHandler.fillEntCommRelScheduleForPrint(): exception: " + e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\EntCommRelScheduleForPrintSubHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */